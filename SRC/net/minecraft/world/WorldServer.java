package net.minecraft.world;

import net.minecraft.server.*;
import net.minecraft.server.management.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.storage.*;
import net.minecraft.profiler.*;
import net.minecraft.scoreboard.*;
import net.minecraft.src.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.effect.*;
import net.minecraft.crash.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.chunk.storage.*;
import net.minecraft.tileentity.*;
import java.util.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.feature.*;
import com.google.common.collect.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.network.play.server.*;
import org.apache.logging.log4j.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class WorldServer extends World
{
    private static final Logger logger;
    private final MinecraftServer mcServer;
    private final EntityTracker theEntityTracker;
    private final PlayerManager thePlayerManager;
    private Set pendingTickListEntriesHashSet;
    private TreeSet pendingTickListEntriesTreeSet;
    public ChunkProviderServer theChunkProviderServer;
    public boolean levelSaving;
    private boolean allPlayersSleeping;
    private int updateEntityTick;
    private final Teleporter worldTeleporter;
    private final SpawnerAnimals animalSpawner;
    private ServerBlockEventList[] field_147490_S;
    private int field_147489_T;
    private static final WeightedRandomChestContent[] bonusChestContent;
    private List pendingTickListEntriesThisTick;
    private IntHashMap entityIdMap;
    private static final String __OBFID = "CL_00001437";
    
    public WorldServer(final MinecraftServer p_i45284_1_, final ISaveHandler p_i45284_2_, final String p_i45284_3_, final int p_i45284_4_, final WorldSettings p_i45284_5_, final Profiler p_i45284_6_) {
        super(p_i45284_2_, p_i45284_3_, p_i45284_5_, WorldProvider.getProviderForDimension(p_i45284_4_), p_i45284_6_);
        this.animalSpawner = new SpawnerAnimals();
        this.field_147490_S = new ServerBlockEventList[] { new ServerBlockEventList((Object)null), new ServerBlockEventList((Object)null) };
        this.pendingTickListEntriesThisTick = new ArrayList();
        this.mcServer = p_i45284_1_;
        this.theEntityTracker = new EntityTracker(this);
        this.thePlayerManager = new PlayerManager(this);
        if (this.entityIdMap == null) {
            this.entityIdMap = new IntHashMap();
        }
        if (this.pendingTickListEntriesHashSet == null) {
            this.pendingTickListEntriesHashSet = new HashSet();
        }
        if (this.pendingTickListEntriesTreeSet == null) {
            this.pendingTickListEntriesTreeSet = new TreeSet();
        }
        this.worldTeleporter = new Teleporter(this);
        this.worldScoreboard = new ServerScoreboard(p_i45284_1_);
        ScoreboardSaveData var7 = (ScoreboardSaveData)this.mapStorage.loadData(ScoreboardSaveData.class, "scoreboard");
        if (var7 == null) {
            var7 = new ScoreboardSaveData();
            this.mapStorage.setData("scoreboard", var7);
        }
        var7.func_96499_a(this.worldScoreboard);
        ((ServerScoreboard)this.worldScoreboard).func_96547_a(var7);
    }
    
    private void fixWorldTime() {
        final long time = this.getWorldTime();
        final long timeOfDay = time % 24000L;
        if (Config.isTimeDayOnly()) {
            if (timeOfDay <= 1000L) {
                this.setWorldTime(time - timeOfDay + 1001L);
            }
            if (timeOfDay >= 11000L) {
                this.setWorldTime(time - timeOfDay + 24001L);
            }
        }
        if (Config.isTimeNightOnly()) {
            if (timeOfDay <= 14000L) {
                this.setWorldTime(time - timeOfDay + 14001L);
            }
            if (timeOfDay >= 22000L) {
                this.setWorldTime(time - timeOfDay + 24000L + 14001L);
            }
        }
    }
    
    @Override
    public void tick() {
        super.tick();
        if (!Config.isTimeDefault()) {
            this.fixWorldTime();
        }
        if (this.getWorldInfo().isHardcoreModeEnabled() && this.difficultySetting != EnumDifficulty.HARD) {
            this.difficultySetting = EnumDifficulty.HARD;
        }
        this.provider.worldChunkMgr.cleanupCache();
        if (this.areAllPlayersAsleep()) {
            if (this.getGameRules().getGameRuleBooleanValue("doDaylightCycle")) {
                final long var1 = this.worldInfo.getWorldTime() + 24000L;
                this.worldInfo.setWorldTime(var1 - var1 % 24000L);
            }
            this.wakeAllPlayers();
        }
        this.theProfiler.startSection("mobSpawner");
        if (this.getGameRules().getGameRuleBooleanValue("doMobSpawning")) {
            this.animalSpawner.findChunksForSpawning(this, this.spawnHostileMobs, this.spawnPeacefulMobs, this.worldInfo.getWorldTotalTime() % 400L == 0L);
        }
        this.theProfiler.endStartSection("chunkSource");
        this.chunkProvider.unloadQueuedChunks();
        final int var2 = this.calculateSkylightSubtracted(1.0f);
        if (var2 != this.skylightSubtracted) {
            this.skylightSubtracted = var2;
        }
        this.worldInfo.incrementTotalWorldTime(this.worldInfo.getWorldTotalTime() + 1L);
        if (this.getGameRules().getGameRuleBooleanValue("doDaylightCycle")) {
            this.worldInfo.setWorldTime(this.worldInfo.getWorldTime() + 1L);
        }
        this.theProfiler.endStartSection("tickPending");
        this.tickUpdates(false);
        this.theProfiler.endStartSection("tickBlocks");
        this.func_147456_g();
        this.theProfiler.endStartSection("chunkMap");
        this.thePlayerManager.updatePlayerInstances();
        this.theProfiler.endStartSection("village");
        this.villageCollectionObj.tick();
        this.villageSiegeObj.tick();
        this.theProfiler.endStartSection("portalForcer");
        this.worldTeleporter.removeStalePortalLocations(this.getTotalWorldTime());
        this.theProfiler.endSection();
        this.func_147488_Z();
    }
    
    public BiomeGenBase.SpawnListEntry spawnRandomCreature(final EnumCreatureType p_73057_1_, final int p_73057_2_, final int p_73057_3_, final int p_73057_4_) {
        final List var5 = this.getChunkProvider().getPossibleCreatures(p_73057_1_, p_73057_2_, p_73057_3_, p_73057_4_);
        return (var5 != null && !var5.isEmpty()) ? ((BiomeGenBase.SpawnListEntry)WeightedRandom.getRandomItem(this.rand, var5)) : null;
    }
    
    @Override
    public void updateAllPlayersSleepingFlag() {
        this.allPlayersSleeping = !this.playerEntities.isEmpty();
        for (final EntityPlayer var2 : this.playerEntities) {
            if (!var2.isPlayerSleeping()) {
                this.allPlayersSleeping = false;
                break;
            }
        }
    }
    
    protected void wakeAllPlayers() {
        this.allPlayersSleeping = false;
        for (final EntityPlayer var2 : this.playerEntities) {
            if (var2.isPlayerSleeping()) {
                var2.wakeUpPlayer(false, false, true);
            }
        }
        this.resetRainAndThunder();
    }
    
    private void resetRainAndThunder() {
        this.worldInfo.setRainTime(0);
        this.worldInfo.setRaining(false);
        this.worldInfo.setThunderTime(0);
        this.worldInfo.setThundering(false);
    }
    
    public boolean areAllPlayersAsleep() {
        if (this.allPlayersSleeping && !this.isClient) {
            for (final EntityPlayer var2 : this.playerEntities) {
                if (!var2.isPlayerFullyAsleep()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void setSpawnLocation() {
        if (this.worldInfo.getSpawnY() <= 0) {
            this.worldInfo.setSpawnY(64);
        }
        int var1 = this.worldInfo.getSpawnX();
        int var2 = this.worldInfo.getSpawnZ();
        int var3 = 0;
        while (this.getTopBlock(var1, var2).getMaterial() == Material.air) {
            var1 += this.rand.nextInt(8) - this.rand.nextInt(8);
            var2 += this.rand.nextInt(8) - this.rand.nextInt(8);
            if (++var3 == 10000) {
                break;
            }
        }
        this.worldInfo.setSpawnX(var1);
        this.worldInfo.setSpawnZ(var2);
    }
    
    @Override
    protected void func_147456_g() {
        super.func_147456_g();
        int var1 = 0;
        int var2 = 0;
        for (final ChunkCoordIntPair var4 : this.activeChunkSet) {
            final int var5 = var4.chunkXPos * 16;
            final int var6 = var4.chunkZPos * 16;
            this.theProfiler.startSection("getChunk");
            final Chunk var7 = this.getChunkFromChunkCoords(var4.chunkXPos, var4.chunkZPos);
            this.func_147467_a(var5, var6, var7);
            this.theProfiler.endStartSection("tickChunk");
            var7.func_150804_b(false);
            this.theProfiler.endStartSection("thunder");
            if (this.rand.nextInt(100000) == 0 && this.isRaining() && this.isThundering()) {
                this.updateLCG = this.updateLCG * 3 + 1013904223;
                final int var8 = this.updateLCG >> 2;
                final int var9 = var5 + (var8 & 0xF);
                final int var10 = var6 + (var8 >> 8 & 0xF);
                final int var11 = this.getPrecipitationHeight(var9, var10);
                if (this.canLightningStrikeAt(var9, var11, var10)) {
                    this.addWeatherEffect(new EntityLightningBolt(this, var9, var11, var10));
                }
            }
            this.theProfiler.endStartSection("iceandsnow");
            if (this.rand.nextInt(16) == 0) {
                this.updateLCG = this.updateLCG * 3 + 1013904223;
                final int var8 = this.updateLCG >> 2;
                final int var9 = var8 & 0xF;
                final int var10 = var8 >> 8 & 0xF;
                final int var11 = this.getPrecipitationHeight(var9 + var5, var10 + var6);
                if (this.isBlockFreezableNaturally(var9 + var5, var11 - 1, var10 + var6)) {
                    this.setBlock(var9 + var5, var11 - 1, var10 + var6, Blocks.ice);
                }
                if (this.isRaining() && this.func_147478_e(var9 + var5, var11, var10 + var6, true)) {
                    this.setBlock(var9 + var5, var11, var10 + var6, Blocks.snow_layer);
                }
                if (this.isRaining()) {
                    final BiomeGenBase var12 = this.getBiomeGenForCoords(var9 + var5, var10 + var6);
                    if (var12.canSpawnLightningBolt()) {
                        this.getBlock(var9 + var5, var11 - 1, var10 + var6).fillWithRain(this, var9 + var5, var11 - 1, var10 + var6);
                    }
                }
            }
            this.theProfiler.endStartSection("tickBlocks");
            for (final ExtendedBlockStorage var14 : var7.getBlockStorageArray()) {
                if (var14 != null && var14.getNeedsRandomTick()) {
                    for (int var15 = 0; var15 < 3; ++var15) {
                        this.updateLCG = this.updateLCG * 3 + 1013904223;
                        final int var16 = this.updateLCG >> 2;
                        final int var17 = var16 & 0xF;
                        final int var18 = var16 >> 8 & 0xF;
                        final int var19 = var16 >> 16 & 0xF;
                        ++var2;
                        final Block var20 = var14.func_150819_a(var17, var19, var18);
                        if (var20.getTickRandomly()) {
                            ++var1;
                            var20.updateTick(this, var17 + var5, var19 + var14.getYLocation(), var18 + var6, this.rand);
                        }
                    }
                }
            }
            this.theProfiler.endSection();
        }
    }
    
    @Override
    public boolean func_147477_a(final int p_147477_1_, final int p_147477_2_, final int p_147477_3_, final Block p_147477_4_) {
        final NextTickListEntry var5 = new NextTickListEntry(p_147477_1_, p_147477_2_, p_147477_3_, p_147477_4_);
        return this.pendingTickListEntriesThisTick.contains(var5);
    }
    
    @Override
    public void scheduleBlockUpdate(final int p_147464_1_, final int p_147464_2_, final int p_147464_3_, final Block p_147464_4_, final int p_147464_5_) {
        this.func_147454_a(p_147464_1_, p_147464_2_, p_147464_3_, p_147464_4_, p_147464_5_, 0);
    }
    
    @Override
    public void func_147454_a(final int p_147454_1_, final int p_147454_2_, final int p_147454_3_, final Block p_147454_4_, int p_147454_5_, final int p_147454_6_) {
        final NextTickListEntry var7 = new NextTickListEntry(p_147454_1_, p_147454_2_, p_147454_3_, p_147454_4_);
        byte var8 = 0;
        if (this.scheduledUpdatesAreImmediate && p_147454_4_.getMaterial() != Material.air) {
            if (p_147454_4_.func_149698_L()) {
                var8 = 8;
                if (this.checkChunksExist(var7.xCoord - var8, var7.yCoord - var8, var7.zCoord - var8, var7.xCoord + var8, var7.yCoord + var8, var7.zCoord + var8)) {
                    final Block var9 = this.getBlock(var7.xCoord, var7.yCoord, var7.zCoord);
                    if (var9.getMaterial() != Material.air && var9 == var7.func_151351_a()) {
                        var9.updateTick(this, var7.xCoord, var7.yCoord, var7.zCoord, this.rand);
                    }
                }
                return;
            }
            p_147454_5_ = 1;
        }
        if (this.checkChunksExist(p_147454_1_ - var8, p_147454_2_ - var8, p_147454_3_ - var8, p_147454_1_ + var8, p_147454_2_ + var8, p_147454_3_ + var8)) {
            if (p_147454_4_.getMaterial() != Material.air) {
                var7.setScheduledTime(p_147454_5_ + this.worldInfo.getWorldTotalTime());
                var7.setPriority(p_147454_6_);
            }
            if (!this.pendingTickListEntriesHashSet.contains(var7)) {
                this.pendingTickListEntriesHashSet.add(var7);
                this.pendingTickListEntriesTreeSet.add(var7);
            }
        }
    }
    
    @Override
    public void func_147446_b(final int p_147446_1_, final int p_147446_2_, final int p_147446_3_, final Block p_147446_4_, final int p_147446_5_, final int p_147446_6_) {
        final NextTickListEntry var7 = new NextTickListEntry(p_147446_1_, p_147446_2_, p_147446_3_, p_147446_4_);
        var7.setPriority(p_147446_6_);
        if (p_147446_4_.getMaterial() != Material.air) {
            var7.setScheduledTime(p_147446_5_ + this.worldInfo.getWorldTotalTime());
        }
        if (!this.pendingTickListEntriesHashSet.contains(var7)) {
            this.pendingTickListEntriesHashSet.add(var7);
            this.pendingTickListEntriesTreeSet.add(var7);
        }
    }
    
    @Override
    public void updateEntities() {
        if (this.playerEntities.isEmpty()) {
            if (this.updateEntityTick++ >= 1200) {
                return;
            }
        }
        else {
            this.resetUpdateEntityTick();
        }
        super.updateEntities();
    }
    
    public void resetUpdateEntityTick() {
        this.updateEntityTick = 0;
    }
    
    @Override
    public boolean tickUpdates(final boolean p_72955_1_) {
        int var2 = this.pendingTickListEntriesTreeSet.size();
        if (var2 != this.pendingTickListEntriesHashSet.size()) {
            throw new IllegalStateException("TickNextTick list out of synch");
        }
        if (var2 > 1000) {
            var2 = 1000;
        }
        this.theProfiler.startSection("cleaning");
        for (int var3 = 0; var3 < var2; ++var3) {
            final NextTickListEntry var4 = this.pendingTickListEntriesTreeSet.first();
            if (!p_72955_1_ && var4.scheduledTime > this.worldInfo.getWorldTotalTime()) {
                break;
            }
            this.pendingTickListEntriesTreeSet.remove(var4);
            this.pendingTickListEntriesHashSet.remove(var4);
            this.pendingTickListEntriesThisTick.add(var4);
        }
        this.theProfiler.endSection();
        this.theProfiler.startSection("ticking");
        final Iterator var5 = this.pendingTickListEntriesThisTick.iterator();
        while (var5.hasNext()) {
            final NextTickListEntry var4 = var5.next();
            var5.remove();
            final byte var6 = 0;
            if (this.checkChunksExist(var4.xCoord - var6, var4.yCoord - var6, var4.zCoord - var6, var4.xCoord + var6, var4.yCoord + var6, var4.zCoord + var6)) {
                final Block var7 = this.getBlock(var4.xCoord, var4.yCoord, var4.zCoord);
                if (var7.getMaterial() == Material.air || !Block.isEqualTo(var7, var4.func_151351_a())) {
                    continue;
                }
                try {
                    var7.updateTick(this, var4.xCoord, var4.yCoord, var4.zCoord, this.rand);
                }
                catch (Throwable var9) {
                    final CrashReport var8 = CrashReport.makeCrashReport(var9, "Exception while ticking a block");
                    final CrashReportCategory var10 = var8.makeCategory("Block being ticked");
                    int var11;
                    try {
                        var11 = this.getBlockMetadata(var4.xCoord, var4.yCoord, var4.zCoord);
                    }
                    catch (Throwable var12) {
                        var11 = -1;
                    }
                    CrashReportCategory.func_147153_a(var10, var4.xCoord, var4.yCoord, var4.zCoord, var7, var11);
                    throw new ReportedException(var8);
                }
            }
            else {
                this.scheduleBlockUpdate(var4.xCoord, var4.yCoord, var4.zCoord, var4.func_151351_a(), 0);
            }
        }
        this.theProfiler.endSection();
        this.pendingTickListEntriesThisTick.clear();
        return !this.pendingTickListEntriesTreeSet.isEmpty();
    }
    
    @Override
    public List getPendingBlockUpdates(final Chunk p_72920_1_, final boolean p_72920_2_) {
        ArrayList var3 = null;
        final ChunkCoordIntPair var4 = p_72920_1_.getChunkCoordIntPair();
        final int var5 = (var4.chunkXPos << 4) - 2;
        final int var6 = var5 + 16 + 2;
        final int var7 = (var4.chunkZPos << 4) - 2;
        final int var8 = var7 + 16 + 2;
        for (int var9 = 0; var9 < 2; ++var9) {
            Iterator var10;
            if (var9 == 0) {
                var10 = this.pendingTickListEntriesTreeSet.iterator();
            }
            else {
                var10 = this.pendingTickListEntriesThisTick.iterator();
                if (!this.pendingTickListEntriesThisTick.isEmpty()) {
                    WorldServer.logger.debug("toBeTicked = " + this.pendingTickListEntriesThisTick.size());
                }
            }
            while (var10.hasNext()) {
                final NextTickListEntry var11 = var10.next();
                if (var11.xCoord >= var5 && var11.xCoord < var6 && var11.zCoord >= var7 && var11.zCoord < var8) {
                    if (p_72920_2_) {
                        this.pendingTickListEntriesHashSet.remove(var11);
                        var10.remove();
                    }
                    if (var3 == null) {
                        var3 = new ArrayList();
                    }
                    var3.add(var11);
                }
            }
        }
        return var3;
    }
    
    @Override
    public void updateEntityWithOptionalForce(final Entity p_72866_1_, final boolean p_72866_2_) {
        if (!this.mcServer.getCanSpawnAnimals() && (p_72866_1_ instanceof EntityAnimal || p_72866_1_ instanceof EntityWaterMob)) {
            p_72866_1_.setDead();
        }
        if (!this.mcServer.getCanSpawnNPCs() && p_72866_1_ instanceof INpc) {
            p_72866_1_.setDead();
        }
        super.updateEntityWithOptionalForce(p_72866_1_, p_72866_2_);
    }
    
    @Override
    protected IChunkProvider createChunkProvider() {
        final IChunkLoader var1 = this.saveHandler.getChunkLoader(this.provider);
        return this.theChunkProviderServer = new ChunkProviderServer(this, var1, this.provider.createChunkGenerator());
    }
    
    public List func_147486_a(final int p_147486_1_, final int p_147486_2_, final int p_147486_3_, final int p_147486_4_, final int p_147486_5_, final int p_147486_6_) {
        final ArrayList var7 = new ArrayList();
        for (int var8 = 0; var8 < this.field_147482_g.size(); ++var8) {
            final TileEntity var9 = this.field_147482_g.get(var8);
            if (var9.field_145851_c >= p_147486_1_ && var9.field_145848_d >= p_147486_2_ && var9.field_145849_e >= p_147486_3_ && var9.field_145851_c < p_147486_4_ && var9.field_145848_d < p_147486_5_ && var9.field_145849_e < p_147486_6_) {
                var7.add(var9);
            }
        }
        return var7;
    }
    
    @Override
    public boolean canMineBlock(final EntityPlayer p_72962_1_, final int p_72962_2_, final int p_72962_3_, final int p_72962_4_) {
        return !this.mcServer.isBlockProtected(this, p_72962_2_, p_72962_3_, p_72962_4_, p_72962_1_);
    }
    
    @Override
    protected void initialize(final WorldSettings p_72963_1_) {
        if (this.entityIdMap == null) {
            this.entityIdMap = new IntHashMap();
        }
        if (this.pendingTickListEntriesHashSet == null) {
            this.pendingTickListEntriesHashSet = new HashSet();
        }
        if (this.pendingTickListEntriesTreeSet == null) {
            this.pendingTickListEntriesTreeSet = new TreeSet();
        }
        this.createSpawnPosition(p_72963_1_);
        super.initialize(p_72963_1_);
    }
    
    protected void createSpawnPosition(final WorldSettings p_73052_1_) {
        if (!this.provider.canRespawnHere()) {
            this.worldInfo.setSpawnPosition(0, this.provider.getAverageGroundLevel(), 0);
        }
        else {
            this.findingSpawnPoint = true;
            final WorldChunkManager var2 = this.provider.worldChunkMgr;
            final List var3 = var2.getBiomesToSpawnIn();
            final Random var4 = new Random(this.getSeed());
            final ChunkPosition var5 = var2.func_150795_a(0, 0, 256, var3, var4);
            int var6 = 0;
            final int var7 = this.provider.getAverageGroundLevel();
            int var8 = 0;
            if (var5 != null) {
                var6 = var5.field_151329_a;
                var8 = var5.field_151328_c;
            }
            else {
                WorldServer.logger.warn("Unable to find spawn biome");
            }
            int var9 = 0;
            while (!this.provider.canCoordinateBeSpawn(var6, var8)) {
                var6 += var4.nextInt(64) - var4.nextInt(64);
                var8 += var4.nextInt(64) - var4.nextInt(64);
                if (++var9 == 1000) {
                    break;
                }
            }
            this.worldInfo.setSpawnPosition(var6, var7, var8);
            this.findingSpawnPoint = false;
            if (p_73052_1_.isBonusChestEnabled()) {
                this.createBonusChest();
            }
        }
    }
    
    protected void createBonusChest() {
        final WorldGeneratorBonusChest var1 = new WorldGeneratorBonusChest(WorldServer.bonusChestContent, 10);
        for (int var2 = 0; var2 < 10; ++var2) {
            final int var3 = this.worldInfo.getSpawnX() + this.rand.nextInt(6) - this.rand.nextInt(6);
            final int var4 = this.worldInfo.getSpawnZ() + this.rand.nextInt(6) - this.rand.nextInt(6);
            final int var5 = this.getTopSolidOrLiquidBlock(var3, var4) + 1;
            if (var1.generate(this, this.rand, var3, var5, var4)) {
                break;
            }
        }
    }
    
    public ChunkCoordinates getEntrancePortalLocation() {
        return this.provider.getEntrancePortalLocation();
    }
    
    public void saveAllChunks(final boolean p_73044_1_, final IProgressUpdate p_73044_2_) throws MinecraftException {
        if (this.chunkProvider.canSave()) {
            if (p_73044_2_ != null) {
                p_73044_2_.displayProgressMessage("Saving level");
            }
            this.saveLevel();
            if (p_73044_2_ != null) {
                p_73044_2_.resetProgresAndWorkingMessage("Saving chunks");
            }
            this.chunkProvider.saveChunks(p_73044_1_, p_73044_2_);
            final ArrayList var3 = Lists.newArrayList((Iterable)this.theChunkProviderServer.func_152380_a());
            for (final Chunk var5 : var3) {
                if (var5 != null && !this.thePlayerManager.func_152621_a(var5.xPosition, var5.zPosition)) {
                    this.theChunkProviderServer.unloadChunksIfNotNearSpawn(var5.xPosition, var5.zPosition);
                }
            }
        }
    }
    
    public void saveChunkData() {
        if (this.chunkProvider.canSave()) {
            this.chunkProvider.saveExtraData();
        }
    }
    
    protected void saveLevel() throws MinecraftException {
        this.checkSessionLock();
        this.saveHandler.saveWorldInfoWithPlayer(this.worldInfo, this.mcServer.getConfigurationManager().getHostPlayerData());
        this.mapStorage.saveAllData();
    }
    
    @Override
    protected void onEntityAdded(final Entity p_72923_1_) {
        super.onEntityAdded(p_72923_1_);
        this.entityIdMap.addKey(p_72923_1_.getEntityId(), p_72923_1_);
        final Entity[] var2 = p_72923_1_.getParts();
        if (var2 != null) {
            for (int var3 = 0; var3 < var2.length; ++var3) {
                this.entityIdMap.addKey(var2[var3].getEntityId(), var2[var3]);
            }
        }
    }
    
    @Override
    protected void onEntityRemoved(final Entity p_72847_1_) {
        super.onEntityRemoved(p_72847_1_);
        this.entityIdMap.removeObject(p_72847_1_.getEntityId());
        final Entity[] var2 = p_72847_1_.getParts();
        if (var2 != null) {
            for (int var3 = 0; var3 < var2.length; ++var3) {
                this.entityIdMap.removeObject(var2[var3].getEntityId());
            }
        }
    }
    
    @Override
    public Entity getEntityByID(final int p_73045_1_) {
        return (Entity)this.entityIdMap.lookup(p_73045_1_);
    }
    
    @Override
    public boolean addWeatherEffect(final Entity p_72942_1_) {
        if (super.addWeatherEffect(p_72942_1_)) {
            this.mcServer.getConfigurationManager().func_148541_a(p_72942_1_.posX, p_72942_1_.posY, p_72942_1_.posZ, 512.0, this.provider.dimensionId, new S2CPacketSpawnGlobalEntity(p_72942_1_));
            return true;
        }
        return false;
    }
    
    @Override
    public void setEntityState(final Entity p_72960_1_, final byte p_72960_2_) {
        this.getEntityTracker().func_151248_b(p_72960_1_, new S19PacketEntityStatus(p_72960_1_, p_72960_2_));
    }
    
    @Override
    public Explosion newExplosion(final Entity p_72885_1_, final double p_72885_2_, final double p_72885_4_, final double p_72885_6_, final float p_72885_8_, final boolean p_72885_9_, final boolean p_72885_10_) {
        final Explosion var11 = new Explosion(this, p_72885_1_, p_72885_2_, p_72885_4_, p_72885_6_, p_72885_8_);
        var11.isFlaming = p_72885_9_;
        var11.isSmoking = p_72885_10_;
        var11.doExplosionA();
        var11.doExplosionB(false);
        if (!p_72885_10_) {
            var11.affectedBlockPositions.clear();
        }
        for (final EntityPlayer var13 : this.playerEntities) {
            if (var13.getDistanceSq(p_72885_2_, p_72885_4_, p_72885_6_) < 4096.0) {
                ((EntityPlayerMP)var13).playerNetServerHandler.sendPacket(new S27PacketExplosion(p_72885_2_, p_72885_4_, p_72885_6_, p_72885_8_, var11.affectedBlockPositions, var11.func_77277_b().get(var13)));
            }
        }
        return var11;
    }
    
    @Override
    public void func_147452_c(final int p_147452_1_, final int p_147452_2_, final int p_147452_3_, final Block p_147452_4_, final int p_147452_5_, final int p_147452_6_) {
        final BlockEventData var7 = new BlockEventData(p_147452_1_, p_147452_2_, p_147452_3_, p_147452_4_, p_147452_5_, p_147452_6_);
        for (final BlockEventData var9 : this.field_147490_S[this.field_147489_T]) {
            if (var9.equals(var7)) {
                return;
            }
        }
        this.field_147490_S[this.field_147489_T].add(var7);
    }
    
    private void func_147488_Z() {
        while (!this.field_147490_S[this.field_147489_T].isEmpty()) {
            final int var1 = this.field_147489_T;
            this.field_147489_T ^= 0x1;
            for (final BlockEventData var3 : this.field_147490_S[var1]) {
                if (this.func_147485_a(var3)) {
                    this.mcServer.getConfigurationManager().func_148541_a(var3.func_151340_a(), var3.func_151342_b(), var3.func_151341_c(), 64.0, this.provider.dimensionId, new S24PacketBlockAction(var3.func_151340_a(), var3.func_151342_b(), var3.func_151341_c(), var3.getBlock(), var3.getEventID(), var3.getEventParameter()));
                }
            }
            this.field_147490_S[var1].clear();
        }
    }
    
    private boolean func_147485_a(final BlockEventData p_147485_1_) {
        final Block var2 = this.getBlock(p_147485_1_.func_151340_a(), p_147485_1_.func_151342_b(), p_147485_1_.func_151341_c());
        return var2 == p_147485_1_.getBlock() && var2.onBlockEventReceived(this, p_147485_1_.func_151340_a(), p_147485_1_.func_151342_b(), p_147485_1_.func_151341_c(), p_147485_1_.getEventID(), p_147485_1_.getEventParameter());
    }
    
    public void flush() {
        this.saveHandler.flush();
    }
    
    protected void fixWorldWeather() {
        if (this.worldInfo.isRaining() || this.worldInfo.isThundering()) {
            this.worldInfo.setRainTime(0);
            this.worldInfo.setRaining(false);
            this.setRainStrength(0.0f);
            this.worldInfo.setThunderTime(0);
            this.worldInfo.setThundering(false);
            this.setThunderStrength(0.0f);
            this.func_73046_m().getConfigurationManager().func_148540_a(new S2BPacketChangeGameState(2, 0.0f));
            this.func_73046_m().getConfigurationManager().func_148540_a(new S2BPacketChangeGameState(7, 0.0f));
            this.func_73046_m().getConfigurationManager().func_148540_a(new S2BPacketChangeGameState(8, 0.0f));
        }
    }
    
    @Override
    protected void updateWeather() {
        if (!Config.isWeatherEnabled()) {
            this.fixWorldWeather();
        }
        final boolean var1 = this.isRaining();
        super.updateWeather();
        if (this.prevRainingStrength != this.rainingStrength) {
            this.mcServer.getConfigurationManager().func_148537_a(new S2BPacketChangeGameState(7, this.rainingStrength), this.provider.dimensionId);
        }
        if (this.prevThunderingStrength != this.thunderingStrength) {
            this.mcServer.getConfigurationManager().func_148537_a(new S2BPacketChangeGameState(8, this.thunderingStrength), this.provider.dimensionId);
        }
        if (var1 != this.isRaining()) {
            if (var1) {
                this.mcServer.getConfigurationManager().func_148540_a(new S2BPacketChangeGameState(2, 0.0f));
            }
            else {
                this.mcServer.getConfigurationManager().func_148540_a(new S2BPacketChangeGameState(1, 0.0f));
            }
            this.mcServer.getConfigurationManager().func_148540_a(new S2BPacketChangeGameState(7, this.rainingStrength));
            this.mcServer.getConfigurationManager().func_148540_a(new S2BPacketChangeGameState(8, this.thunderingStrength));
        }
    }
    
    @Override
    protected int func_152379_p() {
        return this.mcServer.getConfigurationManager().getViewDistance();
    }
    
    public MinecraftServer func_73046_m() {
        return this.mcServer;
    }
    
    public EntityTracker getEntityTracker() {
        return this.theEntityTracker;
    }
    
    public PlayerManager getPlayerManager() {
        return this.thePlayerManager;
    }
    
    public Teleporter getDefaultTeleporter() {
        return this.worldTeleporter;
    }
    
    public void func_147487_a(final String p_147487_1_, final double p_147487_2_, final double p_147487_4_, final double p_147487_6_, final int p_147487_8_, final double p_147487_9_, final double p_147487_11_, final double p_147487_13_, final double p_147487_15_) {
        final S2APacketParticles var17 = new S2APacketParticles(p_147487_1_, (float)p_147487_2_, (float)p_147487_4_, (float)p_147487_6_, (float)p_147487_9_, (float)p_147487_11_, (float)p_147487_13_, (float)p_147487_15_, p_147487_8_);
        for (int var18 = 0; var18 < this.playerEntities.size(); ++var18) {
            final EntityPlayerMP var19 = this.playerEntities.get(var18);
            final ChunkCoordinates var20 = var19.getPlayerCoordinates();
            final double var21 = p_147487_2_ - var20.posX;
            final double var22 = p_147487_4_ - var20.posY;
            final double var23 = p_147487_6_ - var20.posZ;
            final double var24 = var21 * var21 + var22 * var22 + var23 * var23;
            if (var24 <= 256.0) {
                var19.playerNetServerHandler.sendPacket(var17);
            }
        }
    }
    
    static {
        logger = LogManager.getLogger();
        bonusChestContent = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.stick, 0, 1, 3, 10), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.planks), 0, 1, 3, 10), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.log), 0, 1, 3, 10), new WeightedRandomChestContent(Items.stone_axe, 0, 1, 1, 3), new WeightedRandomChestContent(Items.wooden_axe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.stone_pickaxe, 0, 1, 1, 3), new WeightedRandomChestContent(Items.wooden_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.apple, 0, 2, 3, 5), new WeightedRandomChestContent(Items.bread, 0, 2, 3, 3), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.log2), 0, 1, 3, 10) };
    }
    
    static class ServerBlockEventList extends ArrayList
    {
        private static final String __OBFID = "CL_00001439";
        
        private ServerBlockEventList() {
        }
        
        ServerBlockEventList(final Object p_i1521_1_) {
            this();
        }
    }
}
