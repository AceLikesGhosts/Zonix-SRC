package net.minecraft.world;

import net.minecraft.world.storage.*;
import net.minecraft.village.*;
import net.minecraft.profiler.*;
import net.minecraft.scoreboard.*;
import java.util.concurrent.*;
import net.minecraft.crash.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.biome.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.tileentity.*;
import java.util.*;
import net.minecraft.block.*;
import us.zonix.client.module.impl.*;
import net.minecraft.command.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.pathfinding.*;
import net.minecraft.server.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public abstract class World implements IBlockAccess
{
    public boolean scheduledUpdatesAreImmediate;
    public List loadedEntityList;
    protected List unloadedEntityList;
    public List field_147482_g;
    private List field_147484_a;
    private List field_147483_b;
    public List playerEntities;
    public List weatherEffects;
    private long cloudColour;
    public int skylightSubtracted;
    protected int updateLCG;
    protected final int DIST_HASH_MAGIC = 1013904223;
    protected float prevRainingStrength;
    protected float rainingStrength;
    protected float prevThunderingStrength;
    protected float thunderingStrength;
    public int lastLightningBolt;
    public EnumDifficulty difficultySetting;
    public Random rand;
    public final WorldProvider provider;
    protected List worldAccesses;
    protected IChunkProvider chunkProvider;
    protected final ISaveHandler saveHandler;
    protected WorldInfo worldInfo;
    public boolean findingSpawnPoint;
    public MapStorage mapStorage;
    public final VillageCollection villageCollectionObj;
    protected final VillageSiege villageSiegeObj;
    public final Profiler theProfiler;
    private final Calendar theCalendar;
    protected Scoreboard worldScoreboard;
    public boolean isClient;
    protected Set activeChunkSet;
    private int ambientTickCountdown;
    protected boolean spawnHostileMobs;
    protected boolean spawnPeacefulMobs;
    private ArrayList collidingBoundingBoxes;
    private boolean field_147481_N;
    int[] lightUpdateBlockList;
    private static final String __OBFID = "CL_00000140";
    
    @Override
    public BiomeGenBase getBiomeGenForCoords(final int p_72807_1_, final int p_72807_2_) {
        if (this.blockExists(p_72807_1_, 0, p_72807_2_)) {
            final Chunk var3 = this.getChunkFromBlockCoords(p_72807_1_, p_72807_2_);
            try {
                return var3.getBiomeGenForWorldCoords(p_72807_1_ & 0xF, p_72807_2_ & 0xF, this.provider.worldChunkMgr);
            }
            catch (Throwable var5) {
                final CrashReport var4 = CrashReport.makeCrashReport(var5, "Getting biome");
                final CrashReportCategory var6 = var4.makeCategory("Coordinates of biome request");
                var6.addCrashSectionCallable("Location", new Callable() {
                    private static final String __OBFID = "CL_00000141";
                    
                    @Override
                    public String call() {
                        return CrashReportCategory.getLocationInfo(p_72807_1_, 0, p_72807_2_);
                    }
                });
                throw new ReportedException(var4);
            }
        }
        return this.provider.worldChunkMgr.getBiomeGenAt(p_72807_1_, p_72807_2_);
    }
    
    public WorldChunkManager getWorldChunkManager() {
        return this.provider.worldChunkMgr;
    }
    
    public World(final ISaveHandler p_i45368_1_, final String p_i45368_2_, final WorldProvider p_i45368_3_, final WorldSettings p_i45368_4_, final Profiler p_i45368_5_) {
        this.loadedEntityList = new ArrayList();
        this.unloadedEntityList = new ArrayList();
        this.field_147482_g = new ArrayList();
        this.field_147484_a = new ArrayList();
        this.field_147483_b = new ArrayList();
        this.playerEntities = new ArrayList();
        this.weatherEffects = new ArrayList();
        this.cloudColour = 16777215L;
        this.updateLCG = new Random().nextInt();
        this.rand = new Random();
        this.worldAccesses = new ArrayList();
        this.villageSiegeObj = new VillageSiege(this);
        this.theCalendar = Calendar.getInstance();
        this.worldScoreboard = new Scoreboard();
        this.activeChunkSet = new HashSet();
        this.ambientTickCountdown = this.rand.nextInt(12000);
        this.spawnHostileMobs = true;
        this.spawnPeacefulMobs = true;
        this.collidingBoundingBoxes = new ArrayList();
        this.lightUpdateBlockList = new int[32768];
        this.saveHandler = p_i45368_1_;
        this.theProfiler = p_i45368_5_;
        this.worldInfo = new WorldInfo(p_i45368_4_, p_i45368_2_);
        this.provider = p_i45368_3_;
        this.mapStorage = new MapStorage(p_i45368_1_);
        final VillageCollection var6 = (VillageCollection)this.mapStorage.loadData(VillageCollection.class, "villages");
        if (var6 == null) {
            this.villageCollectionObj = new VillageCollection(this);
            this.mapStorage.setData("villages", this.villageCollectionObj);
        }
        else {
            (this.villageCollectionObj = var6).func_82566_a(this);
        }
        p_i45368_3_.registerWorld(this);
        this.chunkProvider = this.createChunkProvider();
        this.calculateInitialSkylight();
        this.calculateInitialWeather();
    }
    
    public EntityPlayer getPlayerEntityByUUID(final UUID uuid) {
        for (int i = 0; i < this.playerEntities.size(); ++i) {
            final EntityPlayer entityplayer = this.playerEntities.get(i);
            if (uuid.equals(entityplayer.getUniqueID())) {
                return entityplayer;
            }
        }
        return null;
    }
    
    public World(final ISaveHandler p_i45369_1_, final String p_i45369_2_, final WorldSettings p_i45369_3_, final WorldProvider p_i45369_4_, final Profiler p_i45369_5_) {
        this.loadedEntityList = new ArrayList();
        this.unloadedEntityList = new ArrayList();
        this.field_147482_g = new ArrayList();
        this.field_147484_a = new ArrayList();
        this.field_147483_b = new ArrayList();
        this.playerEntities = new ArrayList();
        this.weatherEffects = new ArrayList();
        this.cloudColour = 16777215L;
        this.updateLCG = new Random().nextInt();
        this.rand = new Random();
        this.worldAccesses = new ArrayList();
        this.villageSiegeObj = new VillageSiege(this);
        this.theCalendar = Calendar.getInstance();
        this.worldScoreboard = new Scoreboard();
        this.activeChunkSet = new HashSet();
        this.ambientTickCountdown = this.rand.nextInt(12000);
        this.spawnHostileMobs = true;
        this.spawnPeacefulMobs = true;
        this.collidingBoundingBoxes = new ArrayList();
        this.lightUpdateBlockList = new int[32768];
        this.saveHandler = p_i45369_1_;
        this.theProfiler = p_i45369_5_;
        this.mapStorage = new MapStorage(p_i45369_1_);
        this.worldInfo = p_i45369_1_.loadWorldInfo();
        if (p_i45369_4_ != null) {
            this.provider = p_i45369_4_;
        }
        else if (this.worldInfo != null && this.worldInfo.getVanillaDimension() != 0) {
            this.provider = WorldProvider.getProviderForDimension(this.worldInfo.getVanillaDimension());
        }
        else {
            this.provider = WorldProvider.getProviderForDimension(0);
        }
        if (this.worldInfo == null) {
            this.worldInfo = new WorldInfo(p_i45369_3_, p_i45369_2_);
        }
        else {
            this.worldInfo.setWorldName(p_i45369_2_);
        }
        this.provider.registerWorld(this);
        this.chunkProvider = this.createChunkProvider();
        if (!this.worldInfo.isInitialized()) {
            try {
                this.initialize(p_i45369_3_);
            }
            catch (Throwable var8) {
                final CrashReport var7 = CrashReport.makeCrashReport(var8, "Exception initializing level");
                try {
                    this.addWorldInfoToCrashReport(var7);
                }
                catch (Throwable t) {}
                throw new ReportedException(var7);
            }
            this.worldInfo.setServerInitialized(true);
        }
        final VillageCollection var9 = (VillageCollection)this.mapStorage.loadData(VillageCollection.class, "villages");
        if (var9 == null) {
            this.villageCollectionObj = new VillageCollection(this);
            this.mapStorage.setData("villages", this.villageCollectionObj);
        }
        else {
            (this.villageCollectionObj = var9).func_82566_a(this);
        }
        this.calculateInitialSkylight();
        this.calculateInitialWeather();
    }
    
    protected abstract IChunkProvider createChunkProvider();
    
    protected void initialize(final WorldSettings p_72963_1_) {
        this.worldInfo.setServerInitialized(true);
    }
    
    public void setSpawnLocation() {
        this.setSpawnLocation(8, 64, 8);
    }
    
    public Block getTopBlock(final int p_147474_1_, final int p_147474_2_) {
        int var3;
        for (var3 = 63; !this.isAirBlock(p_147474_1_, var3 + 1, p_147474_2_); ++var3) {}
        return this.getBlock(p_147474_1_, var3, p_147474_2_);
    }
    
    @Override
    public Block getBlock(final int p_147439_1_, final int p_147439_2_, final int p_147439_3_) {
        if (p_147439_1_ >= -30000000 && p_147439_3_ >= -30000000 && p_147439_1_ < 30000000 && p_147439_3_ < 30000000 && p_147439_2_ >= 0 && p_147439_2_ < 256) {
            Chunk var4 = null;
            try {
                var4 = this.getChunkFromChunkCoords(p_147439_1_ >> 4, p_147439_3_ >> 4);
                return var4.func_150810_a(p_147439_1_ & 0xF, p_147439_2_, p_147439_3_ & 0xF);
            }
            catch (Throwable var6) {
                final CrashReport var5 = CrashReport.makeCrashReport(var6, "Exception getting block type in world");
                final CrashReportCategory var7 = var5.makeCategory("Requested block coordinates");
                var7.addCrashSection("Found chunk", var4 == null);
                var7.addCrashSection("Location", CrashReportCategory.getLocationInfo(p_147439_1_, p_147439_2_, p_147439_3_));
                throw new ReportedException(var5);
            }
        }
        return Blocks.air;
    }
    
    @Override
    public boolean isAirBlock(final int p_147437_1_, final int p_147437_2_, final int p_147437_3_) {
        return this.getBlock(p_147437_1_, p_147437_2_, p_147437_3_).getMaterial() == Material.air;
    }
    
    public boolean blockExists(final int p_72899_1_, final int p_72899_2_, final int p_72899_3_) {
        return p_72899_2_ >= 0 && p_72899_2_ < 256 && this.chunkExists(p_72899_1_ >> 4, p_72899_3_ >> 4);
    }
    
    public boolean doChunksNearChunkExist(final int p_72873_1_, final int p_72873_2_, final int p_72873_3_, final int p_72873_4_) {
        return this.checkChunksExist(p_72873_1_ - p_72873_4_, p_72873_2_ - p_72873_4_, p_72873_3_ - p_72873_4_, p_72873_1_ + p_72873_4_, p_72873_2_ + p_72873_4_, p_72873_3_ + p_72873_4_);
    }
    
    public boolean checkChunksExist(int p_72904_1_, final int p_72904_2_, int p_72904_3_, int p_72904_4_, final int p_72904_5_, int p_72904_6_) {
        if (p_72904_5_ >= 0 && p_72904_2_ < 256) {
            p_72904_1_ >>= 4;
            p_72904_3_ >>= 4;
            p_72904_4_ >>= 4;
            p_72904_6_ >>= 4;
            for (int var7 = p_72904_1_; var7 <= p_72904_4_; ++var7) {
                for (int var8 = p_72904_3_; var8 <= p_72904_6_; ++var8) {
                    if (!this.chunkExists(var7, var8)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    protected boolean chunkExists(final int p_72916_1_, final int p_72916_2_) {
        return this.chunkProvider.chunkExists(p_72916_1_, p_72916_2_);
    }
    
    public Chunk getChunkFromBlockCoords(final int p_72938_1_, final int p_72938_2_) {
        return this.getChunkFromChunkCoords(p_72938_1_ >> 4, p_72938_2_ >> 4);
    }
    
    public Chunk getChunkFromChunkCoords(final int p_72964_1_, final int p_72964_2_) {
        return this.chunkProvider.provideChunk(p_72964_1_, p_72964_2_);
    }
    
    public boolean setBlock(final int p_147465_1_, final int p_147465_2_, final int p_147465_3_, final Block p_147465_4_, final int p_147465_5_, final int p_147465_6_) {
        if (p_147465_1_ < -30000000 || p_147465_3_ < -30000000 || p_147465_1_ >= 30000000 || p_147465_3_ >= 30000000) {
            return false;
        }
        if (p_147465_2_ < 0) {
            return false;
        }
        if (p_147465_2_ >= 256) {
            return false;
        }
        final Chunk var7 = this.getChunkFromChunkCoords(p_147465_1_ >> 4, p_147465_3_ >> 4);
        Block var8 = null;
        if ((p_147465_6_ & 0x1) != 0x0) {
            var8 = var7.func_150810_a(p_147465_1_ & 0xF, p_147465_2_, p_147465_3_ & 0xF);
        }
        final boolean var9 = var7.func_150807_a(p_147465_1_ & 0xF, p_147465_2_, p_147465_3_ & 0xF, p_147465_4_, p_147465_5_);
        this.theProfiler.startSection("checkLight");
        this.func_147451_t(p_147465_1_, p_147465_2_, p_147465_3_);
        this.theProfiler.endSection();
        if (var9) {
            if ((p_147465_6_ & 0x2) != 0x0 && (!this.isClient || (p_147465_6_ & 0x4) == 0x0) && var7.func_150802_k()) {
                this.func_147471_g(p_147465_1_, p_147465_2_, p_147465_3_);
            }
            if (!this.isClient && (p_147465_6_ & 0x1) != 0x0) {
                this.notifyBlockChange(p_147465_1_, p_147465_2_, p_147465_3_, var8);
                if (p_147465_4_.hasComparatorInputOverride()) {
                    this.func_147453_f(p_147465_1_, p_147465_2_, p_147465_3_, p_147465_4_);
                }
            }
        }
        return var9;
    }
    
    @Override
    public int getBlockMetadata(int p_72805_1_, final int p_72805_2_, int p_72805_3_) {
        if (p_72805_1_ < -30000000 || p_72805_3_ < -30000000 || p_72805_1_ >= 30000000 || p_72805_3_ >= 30000000) {
            return 0;
        }
        if (p_72805_2_ < 0) {
            return 0;
        }
        if (p_72805_2_ >= 256) {
            return 0;
        }
        final Chunk var4 = this.getChunkFromChunkCoords(p_72805_1_ >> 4, p_72805_3_ >> 4);
        p_72805_1_ &= 0xF;
        p_72805_3_ &= 0xF;
        return var4.getBlockMetadata(p_72805_1_, p_72805_2_, p_72805_3_);
    }
    
    public boolean setBlockMetadataWithNotify(final int p_72921_1_, final int p_72921_2_, final int p_72921_3_, final int p_72921_4_, final int p_72921_5_) {
        if (p_72921_1_ < -30000000 || p_72921_3_ < -30000000 || p_72921_1_ >= 30000000 || p_72921_3_ >= 30000000) {
            return false;
        }
        if (p_72921_2_ < 0) {
            return false;
        }
        if (p_72921_2_ >= 256) {
            return false;
        }
        final Chunk var6 = this.getChunkFromChunkCoords(p_72921_1_ >> 4, p_72921_3_ >> 4);
        final int var7 = p_72921_1_ & 0xF;
        final int var8 = p_72921_3_ & 0xF;
        final boolean var9 = var6.setBlockMetadata(var7, p_72921_2_, var8, p_72921_4_);
        if (var9) {
            final Block var10 = var6.func_150810_a(var7, p_72921_2_, var8);
            if ((p_72921_5_ & 0x2) != 0x0 && (!this.isClient || (p_72921_5_ & 0x4) == 0x0) && var6.func_150802_k()) {
                this.func_147471_g(p_72921_1_, p_72921_2_, p_72921_3_);
            }
            if (!this.isClient && (p_72921_5_ & 0x1) != 0x0) {
                this.notifyBlockChange(p_72921_1_, p_72921_2_, p_72921_3_, var10);
                if (var10.hasComparatorInputOverride()) {
                    this.func_147453_f(p_72921_1_, p_72921_2_, p_72921_3_, var10);
                }
            }
        }
        return var9;
    }
    
    public boolean setBlockToAir(final int p_147468_1_, final int p_147468_2_, final int p_147468_3_) {
        return this.setBlock(p_147468_1_, p_147468_2_, p_147468_3_, Blocks.air, 0, 3);
    }
    
    public boolean func_147480_a(final int p_147480_1_, final int p_147480_2_, final int p_147480_3_, final boolean p_147480_4_) {
        final Block var5 = this.getBlock(p_147480_1_, p_147480_2_, p_147480_3_);
        if (var5.getMaterial() == Material.air) {
            return false;
        }
        final int var6 = this.getBlockMetadata(p_147480_1_, p_147480_2_, p_147480_3_);
        this.playAuxSFX(2001, p_147480_1_, p_147480_2_, p_147480_3_, Block.getIdFromBlock(var5) + (var6 << 12));
        if (p_147480_4_) {
            var5.dropBlockAsItem(this, p_147480_1_, p_147480_2_, p_147480_3_, var6, 0);
        }
        return this.setBlock(p_147480_1_, p_147480_2_, p_147480_3_, Blocks.air, 0, 3);
    }
    
    public boolean setBlock(final int p_147449_1_, final int p_147449_2_, final int p_147449_3_, final Block p_147449_4_) {
        return this.setBlock(p_147449_1_, p_147449_2_, p_147449_3_, p_147449_4_, 0, 3);
    }
    
    public void func_147471_g(final int p_147471_1_, final int p_147471_2_, final int p_147471_3_) {
        for (int var4 = 0; var4 < this.worldAccesses.size(); ++var4) {
            this.worldAccesses.get(var4).markBlockForUpdate(p_147471_1_, p_147471_2_, p_147471_3_);
        }
    }
    
    public void notifyBlockChange(final int p_147444_1_, final int p_147444_2_, final int p_147444_3_, final Block p_147444_4_) {
        this.notifyBlocksOfNeighborChange(p_147444_1_, p_147444_2_, p_147444_3_, p_147444_4_);
    }
    
    public void markBlocksDirtyVertical(final int p_72975_1_, final int p_72975_2_, int p_72975_3_, int p_72975_4_) {
        if (p_72975_3_ > p_72975_4_) {
            final int var5 = p_72975_4_;
            p_72975_4_ = p_72975_3_;
            p_72975_3_ = var5;
        }
        if (!this.provider.hasNoSky) {
            for (int var5 = p_72975_3_; var5 <= p_72975_4_; ++var5) {
                this.updateLightByType(EnumSkyBlock.Sky, p_72975_1_, var5, p_72975_2_);
            }
        }
        this.markBlockRangeForRenderUpdate(p_72975_1_, p_72975_3_, p_72975_2_, p_72975_1_, p_72975_4_, p_72975_2_);
    }
    
    public void markBlockRangeForRenderUpdate(final int p_147458_1_, final int p_147458_2_, final int p_147458_3_, final int p_147458_4_, final int p_147458_5_, final int p_147458_6_) {
        for (int var7 = 0; var7 < this.worldAccesses.size(); ++var7) {
            this.worldAccesses.get(var7).markBlockRangeForRenderUpdate(p_147458_1_, p_147458_2_, p_147458_3_, p_147458_4_, p_147458_5_, p_147458_6_);
        }
    }
    
    public void notifyBlocksOfNeighborChange(final int p_147459_1_, final int p_147459_2_, final int p_147459_3_, final Block p_147459_4_) {
        this.func_147460_e(p_147459_1_ - 1, p_147459_2_, p_147459_3_, p_147459_4_);
        this.func_147460_e(p_147459_1_ + 1, p_147459_2_, p_147459_3_, p_147459_4_);
        this.func_147460_e(p_147459_1_, p_147459_2_ - 1, p_147459_3_, p_147459_4_);
        this.func_147460_e(p_147459_1_, p_147459_2_ + 1, p_147459_3_, p_147459_4_);
        this.func_147460_e(p_147459_1_, p_147459_2_, p_147459_3_ - 1, p_147459_4_);
        this.func_147460_e(p_147459_1_, p_147459_2_, p_147459_3_ + 1, p_147459_4_);
    }
    
    public void func_147441_b(final int p_147441_1_, final int p_147441_2_, final int p_147441_3_, final Block p_147441_4_, final int p_147441_5_) {
        if (p_147441_5_ != 4) {
            this.func_147460_e(p_147441_1_ - 1, p_147441_2_, p_147441_3_, p_147441_4_);
        }
        if (p_147441_5_ != 5) {
            this.func_147460_e(p_147441_1_ + 1, p_147441_2_, p_147441_3_, p_147441_4_);
        }
        if (p_147441_5_ != 0) {
            this.func_147460_e(p_147441_1_, p_147441_2_ - 1, p_147441_3_, p_147441_4_);
        }
        if (p_147441_5_ != 1) {
            this.func_147460_e(p_147441_1_, p_147441_2_ + 1, p_147441_3_, p_147441_4_);
        }
        if (p_147441_5_ != 2) {
            this.func_147460_e(p_147441_1_, p_147441_2_, p_147441_3_ - 1, p_147441_4_);
        }
        if (p_147441_5_ != 3) {
            this.func_147460_e(p_147441_1_, p_147441_2_, p_147441_3_ + 1, p_147441_4_);
        }
    }
    
    public void func_147460_e(final int p_147460_1_, final int p_147460_2_, final int p_147460_3_, final Block p_147460_4_) {
        if (!this.isClient) {
            final Block var5 = this.getBlock(p_147460_1_, p_147460_2_, p_147460_3_);
            try {
                var5.onNeighborBlockChange(this, p_147460_1_, p_147460_2_, p_147460_3_, p_147460_4_);
            }
            catch (Throwable var7) {
                final CrashReport var6 = CrashReport.makeCrashReport(var7, "Exception while updating neighbours");
                final CrashReportCategory var8 = var6.makeCategory("Block being updated");
                int var9;
                try {
                    var9 = this.getBlockMetadata(p_147460_1_, p_147460_2_, p_147460_3_);
                }
                catch (Throwable var10) {
                    var9 = -1;
                }
                var8.addCrashSectionCallable("Source block type", new Callable() {
                    private static final String __OBFID = "CL_00000142";
                    
                    @Override
                    public String call() {
                        try {
                            return String.format("ID #%d (%s // %s)", Block.getIdFromBlock(p_147460_4_), p_147460_4_.getUnlocalizedName(), p_147460_4_.getClass().getCanonicalName());
                        }
                        catch (Throwable var2) {
                            return "ID #" + Block.getIdFromBlock(p_147460_4_);
                        }
                    }
                });
                CrashReportCategory.func_147153_a(var8, p_147460_1_, p_147460_2_, p_147460_3_, var5, var9);
                throw new ReportedException(var6);
            }
        }
    }
    
    public boolean func_147477_a(final int p_147477_1_, final int p_147477_2_, final int p_147477_3_, final Block p_147477_4_) {
        return false;
    }
    
    public boolean canBlockSeeTheSky(final int p_72937_1_, final int p_72937_2_, final int p_72937_3_) {
        return this.getChunkFromChunkCoords(p_72937_1_ >> 4, p_72937_3_ >> 4).canBlockSeeTheSky(p_72937_1_ & 0xF, p_72937_2_, p_72937_3_ & 0xF);
    }
    
    public int getFullBlockLightValue(final int p_72883_1_, int p_72883_2_, final int p_72883_3_) {
        if (p_72883_2_ < 0) {
            return 0;
        }
        if (p_72883_2_ >= 256) {
            p_72883_2_ = 255;
        }
        return this.getChunkFromChunkCoords(p_72883_1_ >> 4, p_72883_3_ >> 4).getBlockLightValue(p_72883_1_ & 0xF, p_72883_2_, p_72883_3_ & 0xF, 0);
    }
    
    public int getBlockLightValue(final int p_72957_1_, final int p_72957_2_, final int p_72957_3_) {
        return this.getBlockLightValue_do(p_72957_1_, p_72957_2_, p_72957_3_, true);
    }
    
    public int getBlockLightValue_do(int p_72849_1_, int p_72849_2_, int p_72849_3_, final boolean p_72849_4_) {
        if (p_72849_1_ < -30000000 || p_72849_3_ < -30000000 || p_72849_1_ >= 30000000 || p_72849_3_ >= 30000000) {
            return 15;
        }
        if (p_72849_4_ && this.getBlock(p_72849_1_, p_72849_2_, p_72849_3_).func_149710_n()) {
            int var10 = this.getBlockLightValue_do(p_72849_1_, p_72849_2_ + 1, p_72849_3_, false);
            final int var11 = this.getBlockLightValue_do(p_72849_1_ + 1, p_72849_2_, p_72849_3_, false);
            final int var12 = this.getBlockLightValue_do(p_72849_1_ - 1, p_72849_2_, p_72849_3_, false);
            final int var13 = this.getBlockLightValue_do(p_72849_1_, p_72849_2_, p_72849_3_ + 1, false);
            final int var14 = this.getBlockLightValue_do(p_72849_1_, p_72849_2_, p_72849_3_ - 1, false);
            if (var11 > var10) {
                var10 = var11;
            }
            if (var12 > var10) {
                var10 = var12;
            }
            if (var13 > var10) {
                var10 = var13;
            }
            if (var14 > var10) {
                var10 = var14;
            }
            return var10;
        }
        if (p_72849_2_ < 0) {
            return 0;
        }
        if (p_72849_2_ >= 256) {
            p_72849_2_ = 255;
        }
        final Chunk var15 = this.getChunkFromChunkCoords(p_72849_1_ >> 4, p_72849_3_ >> 4);
        p_72849_1_ &= 0xF;
        p_72849_3_ &= 0xF;
        return var15.getBlockLightValue(p_72849_1_, p_72849_2_, p_72849_3_, this.skylightSubtracted);
    }
    
    public int getHeightValue(final int p_72976_1_, final int p_72976_2_) {
        if (p_72976_1_ < -30000000 || p_72976_2_ < -30000000 || p_72976_1_ >= 30000000 || p_72976_2_ >= 30000000) {
            return 64;
        }
        if (!this.chunkExists(p_72976_1_ >> 4, p_72976_2_ >> 4)) {
            return 0;
        }
        final Chunk var3 = this.getChunkFromChunkCoords(p_72976_1_ >> 4, p_72976_2_ >> 4);
        return var3.getHeightValue(p_72976_1_ & 0xF, p_72976_2_ & 0xF);
    }
    
    public int getChunkHeightMapMinimum(final int p_82734_1_, final int p_82734_2_) {
        if (p_82734_1_ < -30000000 || p_82734_2_ < -30000000 || p_82734_1_ >= 30000000 || p_82734_2_ >= 30000000) {
            return 64;
        }
        if (!this.chunkExists(p_82734_1_ >> 4, p_82734_2_ >> 4)) {
            return 0;
        }
        final Chunk var3 = this.getChunkFromChunkCoords(p_82734_1_ >> 4, p_82734_2_ >> 4);
        return var3.heightMapMinimum;
    }
    
    public int getSkyBlockTypeBrightness(final EnumSkyBlock p_72925_1_, final int p_72925_2_, int p_72925_3_, final int p_72925_4_) {
        if (this.provider.hasNoSky && p_72925_1_ == EnumSkyBlock.Sky) {
            return 0;
        }
        if (p_72925_3_ < 0) {
            p_72925_3_ = 0;
        }
        if (p_72925_3_ >= 256) {
            return p_72925_1_.defaultLightValue;
        }
        if (p_72925_2_ < -30000000 || p_72925_4_ < -30000000 || p_72925_2_ >= 30000000 || p_72925_4_ >= 30000000) {
            return p_72925_1_.defaultLightValue;
        }
        final int var5 = p_72925_2_ >> 4;
        final int var6 = p_72925_4_ >> 4;
        if (!this.chunkExists(var5, var6)) {
            return p_72925_1_.defaultLightValue;
        }
        if (this.getBlock(p_72925_2_, p_72925_3_, p_72925_4_).func_149710_n()) {
            int var7 = this.getSavedLightValue(p_72925_1_, p_72925_2_, p_72925_3_ + 1, p_72925_4_);
            final int var8 = this.getSavedLightValue(p_72925_1_, p_72925_2_ + 1, p_72925_3_, p_72925_4_);
            final int var9 = this.getSavedLightValue(p_72925_1_, p_72925_2_ - 1, p_72925_3_, p_72925_4_);
            final int var10 = this.getSavedLightValue(p_72925_1_, p_72925_2_, p_72925_3_, p_72925_4_ + 1);
            final int var11 = this.getSavedLightValue(p_72925_1_, p_72925_2_, p_72925_3_, p_72925_4_ - 1);
            if (var8 > var7) {
                var7 = var8;
            }
            if (var9 > var7) {
                var7 = var9;
            }
            if (var10 > var7) {
                var7 = var10;
            }
            if (var11 > var7) {
                var7 = var11;
            }
            return var7;
        }
        final Chunk var12 = this.getChunkFromChunkCoords(var5, var6);
        return var12.getSavedLightValue(p_72925_1_, p_72925_2_ & 0xF, p_72925_3_, p_72925_4_ & 0xF);
    }
    
    public int getSavedLightValue(final EnumSkyBlock p_72972_1_, final int p_72972_2_, int p_72972_3_, final int p_72972_4_) {
        if (p_72972_3_ < 0) {
            p_72972_3_ = 0;
        }
        if (p_72972_3_ >= 256) {
            p_72972_3_ = 255;
        }
        if (p_72972_2_ < -30000000 || p_72972_4_ < -30000000 || p_72972_2_ >= 30000000 || p_72972_4_ >= 30000000) {
            return p_72972_1_.defaultLightValue;
        }
        final int var5 = p_72972_2_ >> 4;
        final int var6 = p_72972_4_ >> 4;
        if (!this.chunkExists(var5, var6)) {
            return p_72972_1_.defaultLightValue;
        }
        final Chunk var7 = this.getChunkFromChunkCoords(var5, var6);
        return var7.getSavedLightValue(p_72972_1_, p_72972_2_ & 0xF, p_72972_3_, p_72972_4_ & 0xF);
    }
    
    public void setLightValue(final EnumSkyBlock p_72915_1_, final int p_72915_2_, final int p_72915_3_, final int p_72915_4_, final int p_72915_5_) {
        if (p_72915_2_ >= -30000000 && p_72915_4_ >= -30000000 && p_72915_2_ < 30000000 && p_72915_4_ < 30000000 && p_72915_3_ >= 0 && p_72915_3_ < 256 && this.chunkExists(p_72915_2_ >> 4, p_72915_4_ >> 4)) {
            final Chunk var6 = this.getChunkFromChunkCoords(p_72915_2_ >> 4, p_72915_4_ >> 4);
            var6.setLightValue(p_72915_1_, p_72915_2_ & 0xF, p_72915_3_, p_72915_4_ & 0xF, p_72915_5_);
            for (int var7 = 0; var7 < this.worldAccesses.size(); ++var7) {
                this.worldAccesses.get(var7).markBlockForRenderUpdate(p_72915_2_, p_72915_3_, p_72915_4_);
            }
        }
    }
    
    public void func_147479_m(final int p_147479_1_, final int p_147479_2_, final int p_147479_3_) {
        for (int var4 = 0; var4 < this.worldAccesses.size(); ++var4) {
            this.worldAccesses.get(var4).markBlockForRenderUpdate(p_147479_1_, p_147479_2_, p_147479_3_);
        }
    }
    
    @Override
    public int getLightBrightnessForSkyBlocks(final int p_72802_1_, final int p_72802_2_, final int p_72802_3_, final int p_72802_4_) {
        final int var5 = this.getSkyBlockTypeBrightness(EnumSkyBlock.Sky, p_72802_1_, p_72802_2_, p_72802_3_);
        int var6 = this.getSkyBlockTypeBrightness(EnumSkyBlock.Block, p_72802_1_, p_72802_2_, p_72802_3_);
        if (var6 < p_72802_4_) {
            var6 = p_72802_4_;
        }
        return var5 << 20 | var6 << 4;
    }
    
    public float getLightBrightness(final int p_72801_1_, final int p_72801_2_, final int p_72801_3_) {
        return this.provider.lightBrightnessTable[this.getBlockLightValue(p_72801_1_, p_72801_2_, p_72801_3_)];
    }
    
    public boolean isDaytime() {
        return this.skylightSubtracted < 4;
    }
    
    public MovingObjectPosition rayTraceBlocks(final Vec3 p_72933_1_, final Vec3 p_72933_2_) {
        return this.func_147447_a(p_72933_1_, p_72933_2_, false, false, false);
    }
    
    public MovingObjectPosition rayTraceBlocks(final Vec3 p_72901_1_, final Vec3 p_72901_2_, final boolean p_72901_3_) {
        return this.func_147447_a(p_72901_1_, p_72901_2_, p_72901_3_, false, false);
    }
    
    public MovingObjectPosition func_147447_a(final Vec3 p_147447_1_, final Vec3 p_147447_2_, final boolean p_147447_3_, final boolean p_147447_4_, final boolean p_147447_5_) {
        if (Double.isNaN(p_147447_1_.xCoord) || Double.isNaN(p_147447_1_.yCoord) || Double.isNaN(p_147447_1_.zCoord)) {
            return null;
        }
        if (!Double.isNaN(p_147447_2_.xCoord) && !Double.isNaN(p_147447_2_.yCoord) && !Double.isNaN(p_147447_2_.zCoord)) {
            final int var6 = MathHelper.floor_double(p_147447_2_.xCoord);
            final int var7 = MathHelper.floor_double(p_147447_2_.yCoord);
            final int var8 = MathHelper.floor_double(p_147447_2_.zCoord);
            int var9 = MathHelper.floor_double(p_147447_1_.xCoord);
            int var10 = MathHelper.floor_double(p_147447_1_.yCoord);
            int var11 = MathHelper.floor_double(p_147447_1_.zCoord);
            final Block var12 = this.getBlock(var9, var10, var11);
            int var13 = this.getBlockMetadata(var9, var10, var11);
            if ((!p_147447_4_ || var12.getCollisionBoundingBoxFromPool(this, var9, var10, var11) != null) && var12.canCollideCheck(var13, p_147447_3_)) {
                final MovingObjectPosition var14 = var12.collisionRayTrace(this, var9, var10, var11, p_147447_1_, p_147447_2_);
                if (var14 != null) {
                    return var14;
                }
            }
            MovingObjectPosition var15 = null;
            var13 = 200;
            while (var13-- >= 0) {
                if (Double.isNaN(p_147447_1_.xCoord) || Double.isNaN(p_147447_1_.yCoord) || Double.isNaN(p_147447_1_.zCoord)) {
                    return null;
                }
                if (var9 == var6 && var10 == var7 && var11 == var8) {
                    return p_147447_5_ ? var15 : null;
                }
                boolean var16 = true;
                boolean var17 = true;
                boolean var18 = true;
                double var19 = 999.0;
                double var20 = 999.0;
                double var21 = 999.0;
                if (var6 > var9) {
                    var19 = var9 + 1.0;
                }
                else if (var6 < var9) {
                    var19 = var9 + 0.0;
                }
                else {
                    var16 = false;
                }
                if (var7 > var10) {
                    var20 = var10 + 1.0;
                }
                else if (var7 < var10) {
                    var20 = var10 + 0.0;
                }
                else {
                    var17 = false;
                }
                if (var8 > var11) {
                    var21 = var11 + 1.0;
                }
                else if (var8 < var11) {
                    var21 = var11 + 0.0;
                }
                else {
                    var18 = false;
                }
                double var22 = 999.0;
                double var23 = 999.0;
                double var24 = 999.0;
                final double var25 = p_147447_2_.xCoord - p_147447_1_.xCoord;
                final double var26 = p_147447_2_.yCoord - p_147447_1_.yCoord;
                final double var27 = p_147447_2_.zCoord - p_147447_1_.zCoord;
                if (var16) {
                    var22 = (var19 - p_147447_1_.xCoord) / var25;
                }
                if (var17) {
                    var23 = (var20 - p_147447_1_.yCoord) / var26;
                }
                if (var18) {
                    var24 = (var21 - p_147447_1_.zCoord) / var27;
                }
                final boolean var28 = false;
                byte var29;
                if (var22 < var23 && var22 < var24) {
                    if (var6 > var9) {
                        var29 = 4;
                    }
                    else {
                        var29 = 5;
                    }
                    p_147447_1_.xCoord = var19;
                    p_147447_1_.yCoord += var26 * var22;
                    p_147447_1_.zCoord += var27 * var22;
                }
                else if (var23 < var24) {
                    if (var7 > var10) {
                        var29 = 0;
                    }
                    else {
                        var29 = 1;
                    }
                    p_147447_1_.xCoord += var25 * var23;
                    p_147447_1_.yCoord = var20;
                    p_147447_1_.zCoord += var27 * var23;
                }
                else {
                    if (var8 > var11) {
                        var29 = 2;
                    }
                    else {
                        var29 = 3;
                    }
                    p_147447_1_.xCoord += var25 * var24;
                    p_147447_1_.yCoord += var26 * var24;
                    p_147447_1_.zCoord = var21;
                }
                final Vec3 vectorHelper;
                final Vec3 var30 = vectorHelper = Vec3.createVectorHelper(p_147447_1_.xCoord, p_147447_1_.yCoord, p_147447_1_.zCoord);
                final double xCoord = MathHelper.floor_double(p_147447_1_.xCoord);
                vectorHelper.xCoord = xCoord;
                var9 = (int)xCoord;
                if (var29 == 5) {
                    --var9;
                    final Vec3 vec3 = var30;
                    ++vec3.xCoord;
                }
                final Vec3 vec4 = var30;
                final double yCoord = MathHelper.floor_double(p_147447_1_.yCoord);
                vec4.yCoord = yCoord;
                var10 = (int)yCoord;
                if (var29 == 1) {
                    --var10;
                    final Vec3 vec5 = var30;
                    ++vec5.yCoord;
                }
                final Vec3 vec6 = var30;
                final double zCoord = MathHelper.floor_double(p_147447_1_.zCoord);
                vec6.zCoord = zCoord;
                var11 = (int)zCoord;
                if (var29 == 3) {
                    --var11;
                    final Vec3 vec7 = var30;
                    ++vec7.zCoord;
                }
                final Block var31 = this.getBlock(var9, var10, var11);
                final int var32 = this.getBlockMetadata(var9, var10, var11);
                if (p_147447_4_ && var31.getCollisionBoundingBoxFromPool(this, var9, var10, var11) == null) {
                    continue;
                }
                if (var31.canCollideCheck(var32, p_147447_3_)) {
                    final MovingObjectPosition var33 = var31.collisionRayTrace(this, var9, var10, var11, p_147447_1_, p_147447_2_);
                    if (var33 != null) {
                        return var33;
                    }
                    continue;
                }
                else {
                    var15 = new MovingObjectPosition(var9, var10, var11, var29, p_147447_1_, false);
                }
            }
            return p_147447_5_ ? var15 : null;
        }
        return null;
    }
    
    public void playSoundAtEntity(final Entity p_72956_1_, final String p_72956_2_, final float p_72956_3_, final float p_72956_4_) {
        for (int var5 = 0; var5 < this.worldAccesses.size(); ++var5) {
            this.worldAccesses.get(var5).playSound(p_72956_2_, p_72956_1_.posX, p_72956_1_.posY - p_72956_1_.yOffset, p_72956_1_.posZ, p_72956_3_, p_72956_4_);
        }
    }
    
    public void playSoundToNearExcept(final EntityPlayer p_85173_1_, final String p_85173_2_, final float p_85173_3_, final float p_85173_4_) {
        for (int var5 = 0; var5 < this.worldAccesses.size(); ++var5) {
            this.worldAccesses.get(var5).playSoundToNearExcept(p_85173_1_, p_85173_2_, p_85173_1_.posX, p_85173_1_.posY - p_85173_1_.yOffset, p_85173_1_.posZ, p_85173_3_, p_85173_4_);
        }
    }
    
    public void playSoundEffect(final double p_72908_1_, final double p_72908_3_, final double p_72908_5_, final String p_72908_7_, final float p_72908_8_, final float p_72908_9_) {
        for (int var10 = 0; var10 < this.worldAccesses.size(); ++var10) {
            this.worldAccesses.get(var10).playSound(p_72908_7_, p_72908_1_, p_72908_3_, p_72908_5_, p_72908_8_, p_72908_9_);
        }
    }
    
    public void playSound(final double p_72980_1_, final double p_72980_3_, final double p_72980_5_, final String p_72980_7_, final float p_72980_8_, final float p_72980_9_, final boolean p_72980_10_) {
    }
    
    public void playRecord(final String p_72934_1_, final int p_72934_2_, final int p_72934_3_, final int p_72934_4_) {
        for (int var5 = 0; var5 < this.worldAccesses.size(); ++var5) {
            this.worldAccesses.get(var5).playRecord(p_72934_1_, p_72934_2_, p_72934_3_, p_72934_4_);
        }
    }
    
    public void spawnParticle(final String p_72869_1_, final double p_72869_2_, final double p_72869_4_, final double p_72869_6_, final double p_72869_8_, final double p_72869_10_, final double p_72869_12_) {
        for (int var14 = 0; var14 < this.worldAccesses.size(); ++var14) {
            this.worldAccesses.get(var14).spawnParticle(p_72869_1_, p_72869_2_, p_72869_4_, p_72869_6_, p_72869_8_, p_72869_10_, p_72869_12_);
        }
    }
    
    public boolean addWeatherEffect(final Entity p_72942_1_) {
        this.weatherEffects.add(p_72942_1_);
        return true;
    }
    
    public boolean spawnEntityInWorld(final Entity p_72838_1_) {
        final int var2 = MathHelper.floor_double(p_72838_1_.posX / 16.0);
        final int var3 = MathHelper.floor_double(p_72838_1_.posZ / 16.0);
        boolean var4 = p_72838_1_.forceSpawn;
        if (p_72838_1_ instanceof EntityPlayer) {
            var4 = true;
        }
        if (!var4 && !this.chunkExists(var2, var3)) {
            return false;
        }
        if (p_72838_1_ instanceof EntityPlayer) {
            final EntityPlayer var5 = (EntityPlayer)p_72838_1_;
            this.playerEntities.add(var5);
            this.updateAllPlayersSleepingFlag();
        }
        this.getChunkFromChunkCoords(var2, var3).addEntity(p_72838_1_);
        this.loadedEntityList.add(p_72838_1_);
        this.onEntityAdded(p_72838_1_);
        return true;
    }
    
    protected void onEntityAdded(final Entity p_72923_1_) {
        for (int var2 = 0; var2 < this.worldAccesses.size(); ++var2) {
            this.worldAccesses.get(var2).onEntityCreate(p_72923_1_);
        }
    }
    
    protected void onEntityRemoved(final Entity p_72847_1_) {
        for (int var2 = 0; var2 < this.worldAccesses.size(); ++var2) {
            this.worldAccesses.get(var2).onEntityDestroy(p_72847_1_);
        }
    }
    
    public void removeEntity(final Entity p_72900_1_) {
        if (p_72900_1_.riddenByEntity != null) {
            p_72900_1_.riddenByEntity.mountEntity(null);
        }
        if (p_72900_1_.ridingEntity != null) {
            p_72900_1_.mountEntity(null);
        }
        p_72900_1_.setDead();
        if (p_72900_1_ instanceof EntityPlayer) {
            this.playerEntities.remove(p_72900_1_);
            this.updateAllPlayersSleepingFlag();
            this.onEntityRemoved(p_72900_1_);
        }
    }
    
    public void removePlayerEntityDangerously(final Entity p_72973_1_) {
        p_72973_1_.setDead();
        if (p_72973_1_ instanceof EntityPlayer) {
            this.playerEntities.remove(p_72973_1_);
            this.updateAllPlayersSleepingFlag();
        }
        final int var2 = p_72973_1_.chunkCoordX;
        final int var3 = p_72973_1_.chunkCoordZ;
        if (p_72973_1_.addedToChunk && this.chunkExists(var2, var3)) {
            this.getChunkFromChunkCoords(var2, var3).removeEntity(p_72973_1_);
        }
        this.loadedEntityList.remove(p_72973_1_);
        this.onEntityRemoved(p_72973_1_);
    }
    
    public void addWorldAccess(final IWorldAccess p_72954_1_) {
        this.worldAccesses.add(p_72954_1_);
    }
    
    public void removeWorldAccess(final IWorldAccess p_72848_1_) {
        this.worldAccesses.remove(p_72848_1_);
    }
    
    public List getCollidingBoundingBoxes(final Entity p_72945_1_, final AxisAlignedBB p_72945_2_) {
        this.collidingBoundingBoxes.clear();
        final int var3 = MathHelper.floor_double(p_72945_2_.minX);
        final int var4 = MathHelper.floor_double(p_72945_2_.maxX + 1.0);
        final int var5 = MathHelper.floor_double(p_72945_2_.minY);
        final int var6 = MathHelper.floor_double(p_72945_2_.maxY + 1.0);
        final int var7 = MathHelper.floor_double(p_72945_2_.minZ);
        final int var8 = MathHelper.floor_double(p_72945_2_.maxZ + 1.0);
        for (int var9 = var3; var9 < var4; ++var9) {
            for (int var10 = var7; var10 < var8; ++var10) {
                if (this.blockExists(var9, 64, var10)) {
                    for (int var11 = var5 - 1; var11 < var6; ++var11) {
                        Block var12;
                        if (var9 >= -30000000 && var9 < 30000000 && var10 >= -30000000 && var10 < 30000000) {
                            var12 = this.getBlock(var9, var11, var10);
                        }
                        else {
                            var12 = Blocks.stone;
                        }
                        var12.addCollisionBoxesToList(this, var9, var11, var10, p_72945_2_, this.collidingBoundingBoxes, p_72945_1_);
                    }
                }
            }
        }
        final double var13 = 0.25;
        final List var14 = this.getEntitiesWithinAABBExcludingEntity(p_72945_1_, p_72945_2_.expand(var13, var13, var13));
        for (int var15 = 0; var15 < var14.size(); ++var15) {
            AxisAlignedBB var16 = var14.get(var15).getBoundingBox();
            if (var16 != null && var16.intersectsWith(p_72945_2_)) {
                this.collidingBoundingBoxes.add(var16);
            }
            var16 = p_72945_1_.getCollisionBox(var14.get(var15));
            if (var16 != null && var16.intersectsWith(p_72945_2_)) {
                this.collidingBoundingBoxes.add(var16);
            }
        }
        return this.collidingBoundingBoxes;
    }
    
    public List func_147461_a(final AxisAlignedBB p_147461_1_) {
        this.collidingBoundingBoxes.clear();
        final int var2 = MathHelper.floor_double(p_147461_1_.minX);
        final int var3 = MathHelper.floor_double(p_147461_1_.maxX + 1.0);
        final int var4 = MathHelper.floor_double(p_147461_1_.minY);
        final int var5 = MathHelper.floor_double(p_147461_1_.maxY + 1.0);
        final int var6 = MathHelper.floor_double(p_147461_1_.minZ);
        final int var7 = MathHelper.floor_double(p_147461_1_.maxZ + 1.0);
        for (int var8 = var2; var8 < var3; ++var8) {
            for (int var9 = var6; var9 < var7; ++var9) {
                if (this.blockExists(var8, 64, var9)) {
                    for (int var10 = var4 - 1; var10 < var5; ++var10) {
                        Block var11;
                        if (var8 >= -30000000 && var8 < 30000000 && var9 >= -30000000 && var9 < 30000000) {
                            var11 = this.getBlock(var8, var10, var9);
                        }
                        else {
                            var11 = Blocks.bedrock;
                        }
                        var11.addCollisionBoxesToList(this, var8, var10, var9, p_147461_1_, this.collidingBoundingBoxes, null);
                    }
                }
            }
        }
        return this.collidingBoundingBoxes;
    }
    
    public int calculateSkylightSubtracted(final float p_72967_1_) {
        final float var2 = this.getCelestialAngle(p_72967_1_);
        float var3 = 1.0f - (MathHelper.cos(var2 * 3.1415927f * 2.0f) * 2.0f + 0.5f);
        if (var3 < 0.0f) {
            var3 = 0.0f;
        }
        if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        var3 = 1.0f - var3;
        var3 *= (float)(1.0 - this.getRainStrength(p_72967_1_) * 5.0f / 16.0);
        var3 *= (float)(1.0 - this.getWeightedThunderStrength(p_72967_1_) * 5.0f / 16.0);
        var3 = 1.0f - var3;
        return (int)(var3 * 11.0f);
    }
    
    public float getSunBrightness(final float p_72971_1_) {
        final float var2 = this.getCelestialAngle(p_72971_1_);
        float var3 = 1.0f - (MathHelper.cos(var2 * 3.1415927f * 2.0f) * 2.0f + 0.2f);
        if (var3 < 0.0f) {
            var3 = 0.0f;
        }
        if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        var3 = 1.0f - var3;
        var3 *= (float)(1.0 - this.getRainStrength(p_72971_1_) * 5.0f / 16.0);
        var3 *= (float)(1.0 - this.getWeightedThunderStrength(p_72971_1_) * 5.0f / 16.0);
        return var3 * 0.8f + 0.2f;
    }
    
    public Vec3 getSkyColor(final Entity p_72833_1_, final float p_72833_2_) {
        final float var3 = this.getCelestialAngle(p_72833_2_);
        float var4 = MathHelper.cos(var3 * 3.1415927f * 2.0f) * 2.0f + 0.5f;
        if (var4 < 0.0f) {
            var4 = 0.0f;
        }
        if (var4 > 1.0f) {
            var4 = 1.0f;
        }
        final int var5 = MathHelper.floor_double(p_72833_1_.posX);
        final int var6 = MathHelper.floor_double(p_72833_1_.posY);
        final int var7 = MathHelper.floor_double(p_72833_1_.posZ);
        final BiomeGenBase var8 = this.getBiomeGenForCoords(var5, var7);
        final float var9 = var8.getFloatTemperature(var5, var6, var7);
        final int var10 = var8.getSkyColorByTemp(var9);
        float var11 = (var10 >> 16 & 0xFF) / 255.0f;
        float var12 = (var10 >> 8 & 0xFF) / 255.0f;
        float var13 = (var10 & 0xFF) / 255.0f;
        var11 *= var4;
        var12 *= var4;
        var13 *= var4;
        final float var14 = this.getRainStrength(p_72833_2_);
        if (var14 > 0.0f) {
            final float var15 = (var11 * 0.3f + var12 * 0.59f + var13 * 0.11f) * 0.6f;
            final float var16 = 1.0f - var14 * 0.75f;
            var11 = var11 * var16 + var15 * (1.0f - var16);
            var12 = var12 * var16 + var15 * (1.0f - var16);
            var13 = var13 * var16 + var15 * (1.0f - var16);
        }
        final float var15 = this.getWeightedThunderStrength(p_72833_2_);
        if (var15 > 0.0f) {
            final float var16 = (var11 * 0.3f + var12 * 0.59f + var13 * 0.11f) * 0.2f;
            final float var17 = 1.0f - var15 * 0.75f;
            var11 = var11 * var17 + var16 * (1.0f - var17);
            var12 = var12 * var17 + var16 * (1.0f - var17);
            var13 = var13 * var17 + var16 * (1.0f - var17);
        }
        if (this.lastLightningBolt > 0) {
            float var16 = this.lastLightningBolt - p_72833_2_;
            if (var16 > 1.0f) {
                var16 = 1.0f;
            }
            var16 *= 0.45f;
            var11 = var11 * (1.0f - var16) + 0.8f * var16;
            var12 = var12 * (1.0f - var16) + 0.8f * var16;
            var13 = var13 * (1.0f - var16) + 1.0f * var16;
        }
        return Vec3.createVectorHelper(var11, var12, var13);
    }
    
    public float getCelestialAngle(final float p_72826_1_) {
        return this.provider.calculateCelestialAngle(this.worldInfo.getWorldTime(), p_72826_1_);
    }
    
    public int getMoonPhase() {
        return this.provider.getMoonPhase(this.worldInfo.getWorldTime());
    }
    
    public float getCurrentMoonPhaseFactor() {
        return WorldProvider.moonPhaseFactors[this.provider.getMoonPhase(this.worldInfo.getWorldTime())];
    }
    
    public float getCelestialAngleRadians(final float p_72929_1_) {
        final float var2 = this.getCelestialAngle(p_72929_1_);
        return var2 * 3.1415927f * 2.0f;
    }
    
    public Vec3 getCloudColour(final float p_72824_1_) {
        final float var2 = this.getCelestialAngle(p_72824_1_);
        float var3 = MathHelper.cos(var2 * 3.1415927f * 2.0f) * 2.0f + 0.5f;
        if (var3 < 0.0f) {
            var3 = 0.0f;
        }
        if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        float var4 = (this.cloudColour >> 16 & 0xFFL) / 255.0f;
        float var5 = (this.cloudColour >> 8 & 0xFFL) / 255.0f;
        float var6 = (this.cloudColour & 0xFFL) / 255.0f;
        final float var7 = this.getRainStrength(p_72824_1_);
        if (var7 > 0.0f) {
            final float var8 = (var4 * 0.3f + var5 * 0.59f + var6 * 0.11f) * 0.6f;
            final float var9 = 1.0f - var7 * 0.95f;
            var4 = var4 * var9 + var8 * (1.0f - var9);
            var5 = var5 * var9 + var8 * (1.0f - var9);
            var6 = var6 * var9 + var8 * (1.0f - var9);
        }
        var4 *= var3 * 0.9f + 0.1f;
        var5 *= var3 * 0.9f + 0.1f;
        var6 *= var3 * 0.85f + 0.15f;
        final float var8 = this.getWeightedThunderStrength(p_72824_1_);
        if (var8 > 0.0f) {
            final float var9 = (var4 * 0.3f + var5 * 0.59f + var6 * 0.11f) * 0.2f;
            final float var10 = 1.0f - var8 * 0.95f;
            var4 = var4 * var10 + var9 * (1.0f - var10);
            var5 = var5 * var10 + var9 * (1.0f - var10);
            var6 = var6 * var10 + var9 * (1.0f - var10);
        }
        return Vec3.createVectorHelper(var4, var5, var6);
    }
    
    public Vec3 getFogColor(final float p_72948_1_) {
        final float var2 = this.getCelestialAngle(p_72948_1_);
        return this.provider.getFogColor(var2, p_72948_1_);
    }
    
    public int getPrecipitationHeight(final int p_72874_1_, final int p_72874_2_) {
        return this.getChunkFromBlockCoords(p_72874_1_, p_72874_2_).getPrecipitationHeight(p_72874_1_ & 0xF, p_72874_2_ & 0xF);
    }
    
    public int getTopSolidOrLiquidBlock(int p_72825_1_, int p_72825_2_) {
        final Chunk var3 = this.getChunkFromBlockCoords(p_72825_1_, p_72825_2_);
        int var4 = var3.getTopFilledSegment() + 15;
        p_72825_1_ &= 0xF;
        p_72825_2_ &= 0xF;
        while (var4 > 0) {
            final Block var5 = var3.func_150810_a(p_72825_1_, var4, p_72825_2_);
            if (var5.getMaterial().blocksMovement() && var5.getMaterial() != Material.leaves) {
                return var4 + 1;
            }
            --var4;
        }
        return -1;
    }
    
    public float getStarBrightness(final float p_72880_1_) {
        final float var2 = this.getCelestialAngle(p_72880_1_);
        float var3 = 1.0f - (MathHelper.cos(var2 * 3.1415927f * 2.0f) * 2.0f + 0.25f);
        if (var3 < 0.0f) {
            var3 = 0.0f;
        }
        if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        return var3 * var3 * 0.5f;
    }
    
    public void scheduleBlockUpdate(final int p_147464_1_, final int p_147464_2_, final int p_147464_3_, final Block p_147464_4_, final int p_147464_5_) {
    }
    
    public void func_147454_a(final int p_147454_1_, final int p_147454_2_, final int p_147454_3_, final Block p_147454_4_, final int p_147454_5_, final int p_147454_6_) {
    }
    
    public void func_147446_b(final int p_147446_1_, final int p_147446_2_, final int p_147446_3_, final Block p_147446_4_, final int p_147446_5_, final int p_147446_6_) {
    }
    
    public void updateEntities() {
        this.theProfiler.startSection("entities");
        this.theProfiler.startSection("global");
        for (int var1 = 0; var1 < this.weatherEffects.size(); ++var1) {
            final Entity var2 = this.weatherEffects.get(var1);
            try {
                final Entity entity = var2;
                ++entity.ticksExisted;
                var2.onUpdate();
            }
            catch (Throwable var4) {
                final CrashReport var3 = CrashReport.makeCrashReport(var4, "Ticking entity");
                final CrashReportCategory var5 = var3.makeCategory("Entity being ticked");
                if (var2 == null) {
                    var5.addCrashSection("Entity", "~~NULL~~");
                }
                else {
                    var2.addEntityCrashInfo(var5);
                }
                throw new ReportedException(var3);
            }
            if (var2.isDead) {
                this.weatherEffects.remove(var1--);
            }
        }
        this.theProfiler.endStartSection("remove");
        this.loadedEntityList.removeAll(this.unloadedEntityList);
        for (int var1 = 0; var1 < this.unloadedEntityList.size(); ++var1) {
            final Entity var2 = this.unloadedEntityList.get(var1);
            final int var6 = var2.chunkCoordX;
            final int var7 = var2.chunkCoordZ;
            if (var2.addedToChunk && this.chunkExists(var6, var7)) {
                this.getChunkFromChunkCoords(var6, var7).removeEntity(var2);
            }
        }
        for (int var1 = 0; var1 < this.unloadedEntityList.size(); ++var1) {
            this.onEntityRemoved(this.unloadedEntityList.get(var1));
        }
        this.unloadedEntityList.clear();
        this.theProfiler.endStartSection("regular");
        for (int var1 = 0; var1 < this.loadedEntityList.size(); ++var1) {
            final Entity var2 = this.loadedEntityList.get(var1);
            if (var2.ridingEntity != null) {
                if (!var2.ridingEntity.isDead && var2.ridingEntity.riddenByEntity == var2) {
                    continue;
                }
                var2.ridingEntity.riddenByEntity = null;
                var2.ridingEntity = null;
            }
            this.theProfiler.startSection("tick");
            if (!var2.isDead) {
                try {
                    this.updateEntity(var2);
                }
                catch (Throwable var8) {
                    final CrashReport var3 = CrashReport.makeCrashReport(var8, "Ticking entity");
                    final CrashReportCategory var5 = var3.makeCategory("Entity being ticked");
                    var2.addEntityCrashInfo(var5);
                    throw new ReportedException(var3);
                }
            }
            this.theProfiler.endSection();
            this.theProfiler.startSection("remove");
            if (var2.isDead) {
                final int var6 = var2.chunkCoordX;
                final int var7 = var2.chunkCoordZ;
                if (var2.addedToChunk && this.chunkExists(var6, var7)) {
                    this.getChunkFromChunkCoords(var6, var7).removeEntity(var2);
                }
                this.loadedEntityList.remove(var1--);
                this.onEntityRemoved(var2);
            }
            this.theProfiler.endSection();
        }
        this.theProfiler.endStartSection("blockEntities");
        this.field_147481_N = true;
        final Iterator var9 = this.field_147482_g.iterator();
        while (var9.hasNext()) {
            final TileEntity var10 = var9.next();
            if (!var10.isInvalid() && var10.hasWorldObj() && this.blockExists(var10.field_145851_c, var10.field_145848_d, var10.field_145849_e)) {
                try {
                    var10.updateEntity();
                }
                catch (Throwable var11) {
                    final CrashReport var3 = CrashReport.makeCrashReport(var11, "Ticking block entity");
                    final CrashReportCategory var5 = var3.makeCategory("Block entity being ticked");
                    var10.func_145828_a(var5);
                    throw new ReportedException(var3);
                }
            }
            if (var10.isInvalid()) {
                var9.remove();
                if (!this.chunkExists(var10.field_145851_c >> 4, var10.field_145849_e >> 4)) {
                    continue;
                }
                final Chunk var12 = this.getChunkFromChunkCoords(var10.field_145851_c >> 4, var10.field_145849_e >> 4);
                if (var12 == null) {
                    continue;
                }
                var12.removeTileEntity(var10.field_145851_c & 0xF, var10.field_145848_d, var10.field_145849_e & 0xF);
            }
        }
        this.field_147481_N = false;
        if (!this.field_147483_b.isEmpty()) {
            this.field_147482_g.removeAll(this.field_147483_b);
            this.field_147483_b.clear();
        }
        this.theProfiler.endStartSection("pendingBlockEntities");
        if (!this.field_147484_a.isEmpty()) {
            for (int var13 = 0; var13 < this.field_147484_a.size(); ++var13) {
                final TileEntity var14 = this.field_147484_a.get(var13);
                if (!var14.isInvalid()) {
                    if (!this.field_147482_g.contains(var14)) {
                        this.field_147482_g.add(var14);
                    }
                    if (this.chunkExists(var14.field_145851_c >> 4, var14.field_145849_e >> 4)) {
                        final Chunk var15 = this.getChunkFromChunkCoords(var14.field_145851_c >> 4, var14.field_145849_e >> 4);
                        if (var15 != null) {
                            var15.func_150812_a(var14.field_145851_c & 0xF, var14.field_145848_d, var14.field_145849_e & 0xF, var14);
                        }
                    }
                    this.func_147471_g(var14.field_145851_c, var14.field_145848_d, var14.field_145849_e);
                }
            }
            this.field_147484_a.clear();
        }
        this.theProfiler.endSection();
        this.theProfiler.endSection();
    }
    
    public void func_147448_a(final Collection p_147448_1_) {
        if (this.field_147481_N) {
            this.field_147484_a.addAll(p_147448_1_);
        }
        else {
            this.field_147482_g.addAll(p_147448_1_);
        }
    }
    
    public void updateEntity(final Entity p_72870_1_) {
        this.updateEntityWithOptionalForce(p_72870_1_, true);
    }
    
    public void updateEntityWithOptionalForce(final Entity p_72866_1_, final boolean p_72866_2_) {
        final int var3 = MathHelper.floor_double(p_72866_1_.posX);
        final int var4 = MathHelper.floor_double(p_72866_1_.posZ);
        final byte var5 = 32;
        if (!p_72866_2_ || this.checkChunksExist(var3 - var5, 0, var4 - var5, var3 + var5, 0, var4 + var5)) {
            p_72866_1_.lastTickPosX = p_72866_1_.posX;
            p_72866_1_.lastTickPosY = p_72866_1_.posY;
            p_72866_1_.lastTickPosZ = p_72866_1_.posZ;
            p_72866_1_.prevRotationYaw = p_72866_1_.rotationYaw;
            p_72866_1_.prevRotationPitch = p_72866_1_.rotationPitch;
            if (p_72866_2_ && p_72866_1_.addedToChunk) {
                ++p_72866_1_.ticksExisted;
                if (p_72866_1_.ridingEntity != null) {
                    p_72866_1_.updateRidden();
                }
                else {
                    p_72866_1_.onUpdate();
                }
            }
            this.theProfiler.startSection("chunkCheck");
            if (Double.isNaN(p_72866_1_.posX) || Double.isInfinite(p_72866_1_.posX)) {
                p_72866_1_.posX = p_72866_1_.lastTickPosX;
            }
            if (Double.isNaN(p_72866_1_.posY) || Double.isInfinite(p_72866_1_.posY)) {
                p_72866_1_.posY = p_72866_1_.lastTickPosY;
            }
            if (Double.isNaN(p_72866_1_.posZ) || Double.isInfinite(p_72866_1_.posZ)) {
                p_72866_1_.posZ = p_72866_1_.lastTickPosZ;
            }
            if (Double.isNaN(p_72866_1_.rotationPitch) || Double.isInfinite(p_72866_1_.rotationPitch)) {
                p_72866_1_.rotationPitch = p_72866_1_.prevRotationPitch;
            }
            if (Double.isNaN(p_72866_1_.rotationYaw) || Double.isInfinite(p_72866_1_.rotationYaw)) {
                p_72866_1_.rotationYaw = p_72866_1_.prevRotationYaw;
            }
            final int var6 = MathHelper.floor_double(p_72866_1_.posX / 16.0);
            final int var7 = MathHelper.floor_double(p_72866_1_.posY / 16.0);
            final int var8 = MathHelper.floor_double(p_72866_1_.posZ / 16.0);
            if (!p_72866_1_.addedToChunk || p_72866_1_.chunkCoordX != var6 || p_72866_1_.chunkCoordY != var7 || p_72866_1_.chunkCoordZ != var8) {
                if (p_72866_1_.addedToChunk && this.chunkExists(p_72866_1_.chunkCoordX, p_72866_1_.chunkCoordZ)) {
                    this.getChunkFromChunkCoords(p_72866_1_.chunkCoordX, p_72866_1_.chunkCoordZ).removeEntityAtIndex(p_72866_1_, p_72866_1_.chunkCoordY);
                }
                if (this.chunkExists(var6, var8)) {
                    p_72866_1_.addedToChunk = true;
                    this.getChunkFromChunkCoords(var6, var8).addEntity(p_72866_1_);
                }
                else {
                    p_72866_1_.addedToChunk = false;
                }
            }
            this.theProfiler.endSection();
            if (p_72866_2_ && p_72866_1_.addedToChunk && p_72866_1_.riddenByEntity != null) {
                if (!p_72866_1_.riddenByEntity.isDead && p_72866_1_.riddenByEntity.ridingEntity == p_72866_1_) {
                    this.updateEntity(p_72866_1_.riddenByEntity);
                }
                else {
                    p_72866_1_.riddenByEntity.ridingEntity = null;
                    p_72866_1_.riddenByEntity = null;
                }
            }
        }
    }
    
    public boolean checkNoEntityCollision(final AxisAlignedBB p_72855_1_) {
        return this.checkNoEntityCollision(p_72855_1_, null);
    }
    
    public boolean checkNoEntityCollision(final AxisAlignedBB p_72917_1_, final Entity p_72917_2_) {
        final List var3 = this.getEntitiesWithinAABBExcludingEntity(null, p_72917_1_);
        for (int var4 = 0; var4 < var3.size(); ++var4) {
            final Entity var5 = var3.get(var4);
            if (!var5.isDead && var5.preventEntitySpawning && var5 != p_72917_2_) {
                return false;
            }
        }
        return true;
    }
    
    public boolean checkBlockCollision(final AxisAlignedBB p_72829_1_) {
        int var2 = MathHelper.floor_double(p_72829_1_.minX);
        final int var3 = MathHelper.floor_double(p_72829_1_.maxX + 1.0);
        int var4 = MathHelper.floor_double(p_72829_1_.minY);
        final int var5 = MathHelper.floor_double(p_72829_1_.maxY + 1.0);
        int var6 = MathHelper.floor_double(p_72829_1_.minZ);
        final int var7 = MathHelper.floor_double(p_72829_1_.maxZ + 1.0);
        if (p_72829_1_.minX < 0.0) {
            --var2;
        }
        if (p_72829_1_.minY < 0.0) {
            --var4;
        }
        if (p_72829_1_.minZ < 0.0) {
            --var6;
        }
        for (int var8 = var2; var8 < var3; ++var8) {
            for (int var9 = var4; var9 < var5; ++var9) {
                for (int var10 = var6; var10 < var7; ++var10) {
                    final Block var11 = this.getBlock(var8, var9, var10);
                    if (var11.getMaterial() != Material.air) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean isAnyLiquid(final AxisAlignedBB p_72953_1_) {
        int var2 = MathHelper.floor_double(p_72953_1_.minX);
        final int var3 = MathHelper.floor_double(p_72953_1_.maxX + 1.0);
        int var4 = MathHelper.floor_double(p_72953_1_.minY);
        final int var5 = MathHelper.floor_double(p_72953_1_.maxY + 1.0);
        int var6 = MathHelper.floor_double(p_72953_1_.minZ);
        final int var7 = MathHelper.floor_double(p_72953_1_.maxZ + 1.0);
        if (p_72953_1_.minX < 0.0) {
            --var2;
        }
        if (p_72953_1_.minY < 0.0) {
            --var4;
        }
        if (p_72953_1_.minZ < 0.0) {
            --var6;
        }
        for (int var8 = var2; var8 < var3; ++var8) {
            for (int var9 = var4; var9 < var5; ++var9) {
                for (int var10 = var6; var10 < var7; ++var10) {
                    final Block var11 = this.getBlock(var8, var9, var10);
                    if (var11.getMaterial().isLiquid()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean func_147470_e(final AxisAlignedBB p_147470_1_) {
        final int var2 = MathHelper.floor_double(p_147470_1_.minX);
        final int var3 = MathHelper.floor_double(p_147470_1_.maxX + 1.0);
        final int var4 = MathHelper.floor_double(p_147470_1_.minY);
        final int var5 = MathHelper.floor_double(p_147470_1_.maxY + 1.0);
        final int var6 = MathHelper.floor_double(p_147470_1_.minZ);
        final int var7 = MathHelper.floor_double(p_147470_1_.maxZ + 1.0);
        if (this.checkChunksExist(var2, var4, var6, var3, var5, var7)) {
            for (int var8 = var2; var8 < var3; ++var8) {
                for (int var9 = var4; var9 < var5; ++var9) {
                    for (int var10 = var6; var10 < var7; ++var10) {
                        final Block var11 = this.getBlock(var8, var9, var10);
                        if (var11 == Blocks.fire || var11 == Blocks.flowing_lava || var11 == Blocks.lava) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public boolean handleMaterialAcceleration(final AxisAlignedBB p_72918_1_, final Material p_72918_2_, final Entity p_72918_3_) {
        final int var4 = MathHelper.floor_double(p_72918_1_.minX);
        final int var5 = MathHelper.floor_double(p_72918_1_.maxX + 1.0);
        final int var6 = MathHelper.floor_double(p_72918_1_.minY);
        final int var7 = MathHelper.floor_double(p_72918_1_.maxY + 1.0);
        final int var8 = MathHelper.floor_double(p_72918_1_.minZ);
        final int var9 = MathHelper.floor_double(p_72918_1_.maxZ + 1.0);
        if (!this.checkChunksExist(var4, var6, var8, var5, var7, var9)) {
            return false;
        }
        boolean var10 = false;
        Vec3 var11 = Vec3.createVectorHelper(0.0, 0.0, 0.0);
        for (int var12 = var4; var12 < var5; ++var12) {
            for (int var13 = var6; var13 < var7; ++var13) {
                for (int var14 = var8; var14 < var9; ++var14) {
                    final Block var15 = this.getBlock(var12, var13, var14);
                    if (var15.getMaterial() == p_72918_2_) {
                        final double var16 = var13 + 1 - BlockLiquid.func_149801_b(this.getBlockMetadata(var12, var13, var14));
                        if (var7 >= var16) {
                            var10 = true;
                            var15.velocityToAddToEntity(this, var12, var13, var14, p_72918_3_, var11);
                        }
                    }
                }
            }
        }
        if (var11.lengthVector() > 0.0 && p_72918_3_.isPushedByWater()) {
            var11 = var11.normalize();
            final double var17 = 0.014;
            p_72918_3_.motionX += var11.xCoord * var17;
            p_72918_3_.motionY += var11.yCoord * var17;
            p_72918_3_.motionZ += var11.zCoord * var17;
        }
        return var10;
    }
    
    public boolean isMaterialInBB(final AxisAlignedBB p_72875_1_, final Material p_72875_2_) {
        final int var3 = MathHelper.floor_double(p_72875_1_.minX);
        final int var4 = MathHelper.floor_double(p_72875_1_.maxX + 1.0);
        final int var5 = MathHelper.floor_double(p_72875_1_.minY);
        final int var6 = MathHelper.floor_double(p_72875_1_.maxY + 1.0);
        final int var7 = MathHelper.floor_double(p_72875_1_.minZ);
        final int var8 = MathHelper.floor_double(p_72875_1_.maxZ + 1.0);
        for (int var9 = var3; var9 < var4; ++var9) {
            for (int var10 = var5; var10 < var6; ++var10) {
                for (int var11 = var7; var11 < var8; ++var11) {
                    if (this.getBlock(var9, var10, var11).getMaterial() == p_72875_2_) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean isAABBInMaterial(final AxisAlignedBB p_72830_1_, final Material p_72830_2_) {
        final int var3 = MathHelper.floor_double(p_72830_1_.minX);
        final int var4 = MathHelper.floor_double(p_72830_1_.maxX + 1.0);
        final int var5 = MathHelper.floor_double(p_72830_1_.minY);
        final int var6 = MathHelper.floor_double(p_72830_1_.maxY + 1.0);
        final int var7 = MathHelper.floor_double(p_72830_1_.minZ);
        final int var8 = MathHelper.floor_double(p_72830_1_.maxZ + 1.0);
        for (int var9 = var3; var9 < var4; ++var9) {
            for (int var10 = var5; var10 < var6; ++var10) {
                for (int var11 = var7; var11 < var8; ++var11) {
                    final Block var12 = this.getBlock(var9, var10, var11);
                    if (var12.getMaterial() == p_72830_2_) {
                        final int var13 = this.getBlockMetadata(var9, var10, var11);
                        double var14 = var10 + 1;
                        if (var13 < 8) {
                            var14 = var10 + 1 - var13 / 8.0;
                        }
                        if (var14 >= p_72830_1_.minY) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public Explosion createExplosion(final Entity p_72876_1_, final double p_72876_2_, final double p_72876_4_, final double p_72876_6_, final float p_72876_8_, final boolean p_72876_9_) {
        return this.newExplosion(p_72876_1_, p_72876_2_, p_72876_4_, p_72876_6_, p_72876_8_, false, p_72876_9_);
    }
    
    public Explosion newExplosion(final Entity p_72885_1_, final double p_72885_2_, final double p_72885_4_, final double p_72885_6_, final float p_72885_8_, final boolean p_72885_9_, final boolean p_72885_10_) {
        final Explosion var11 = new Explosion(this, p_72885_1_, p_72885_2_, p_72885_4_, p_72885_6_, p_72885_8_);
        var11.isFlaming = p_72885_9_;
        var11.isSmoking = p_72885_10_;
        var11.doExplosionA();
        var11.doExplosionB(true);
        return var11;
    }
    
    public float getBlockDensity(final Vec3 p_72842_1_, final AxisAlignedBB p_72842_2_) {
        final double var3 = 1.0 / ((p_72842_2_.maxX - p_72842_2_.minX) * 2.0 + 1.0);
        final double var4 = 1.0 / ((p_72842_2_.maxY - p_72842_2_.minY) * 2.0 + 1.0);
        final double var5 = 1.0 / ((p_72842_2_.maxZ - p_72842_2_.minZ) * 2.0 + 1.0);
        if (var3 >= 0.0 && var4 >= 0.0 && var5 >= 0.0) {
            int var6 = 0;
            int var7 = 0;
            for (float var8 = 0.0f; var8 <= 1.0f; var8 += (float)var3) {
                for (float var9 = 0.0f; var9 <= 1.0f; var9 += (float)var4) {
                    for (float var10 = 0.0f; var10 <= 1.0f; var10 += (float)var5) {
                        final double var11 = p_72842_2_.minX + (p_72842_2_.maxX - p_72842_2_.minX) * var8;
                        final double var12 = p_72842_2_.minY + (p_72842_2_.maxY - p_72842_2_.minY) * var9;
                        final double var13 = p_72842_2_.minZ + (p_72842_2_.maxZ - p_72842_2_.minZ) * var10;
                        if (this.rayTraceBlocks(Vec3.createVectorHelper(var11, var12, var13), p_72842_1_) == null) {
                            ++var6;
                        }
                        ++var7;
                    }
                }
            }
            return var6 / (float)var7;
        }
        return 0.0f;
    }
    
    public boolean extinguishFire(final EntityPlayer p_72886_1_, int p_72886_2_, int p_72886_3_, int p_72886_4_, final int p_72886_5_) {
        if (p_72886_5_ == 0) {
            --p_72886_3_;
        }
        if (p_72886_5_ == 1) {
            ++p_72886_3_;
        }
        if (p_72886_5_ == 2) {
            --p_72886_4_;
        }
        if (p_72886_5_ == 3) {
            ++p_72886_4_;
        }
        if (p_72886_5_ == 4) {
            --p_72886_2_;
        }
        if (p_72886_5_ == 5) {
            ++p_72886_2_;
        }
        if (this.getBlock(p_72886_2_, p_72886_3_, p_72886_4_) == Blocks.fire) {
            this.playAuxSFXAtEntity(p_72886_1_, 1004, p_72886_2_, p_72886_3_, p_72886_4_, 0);
            this.setBlockToAir(p_72886_2_, p_72886_3_, p_72886_4_);
            return true;
        }
        return false;
    }
    
    public String getDebugLoadedEntities() {
        return "All: " + this.loadedEntityList.size();
    }
    
    public String getProviderName() {
        return this.chunkProvider.makeString();
    }
    
    @Override
    public TileEntity getTileEntity(final int p_147438_1_, final int p_147438_2_, final int p_147438_3_) {
        if (p_147438_2_ >= 0 && p_147438_2_ < 256) {
            TileEntity var4 = null;
            if (this.field_147481_N) {
                for (int var5 = 0; var5 < this.field_147484_a.size(); ++var5) {
                    final TileEntity var6 = this.field_147484_a.get(var5);
                    if (!var6.isInvalid() && var6.field_145851_c == p_147438_1_ && var6.field_145848_d == p_147438_2_ && var6.field_145849_e == p_147438_3_) {
                        var4 = var6;
                        break;
                    }
                }
            }
            if (var4 == null) {
                final Chunk var7 = this.getChunkFromChunkCoords(p_147438_1_ >> 4, p_147438_3_ >> 4);
                if (var7 != null) {
                    var4 = var7.func_150806_e(p_147438_1_ & 0xF, p_147438_2_, p_147438_3_ & 0xF);
                }
            }
            if (var4 == null) {
                for (int var5 = 0; var5 < this.field_147484_a.size(); ++var5) {
                    final TileEntity var6 = this.field_147484_a.get(var5);
                    if (!var6.isInvalid() && var6.field_145851_c == p_147438_1_ && var6.field_145848_d == p_147438_2_ && var6.field_145849_e == p_147438_3_) {
                        var4 = var6;
                        break;
                    }
                }
            }
            return var4;
        }
        return null;
    }
    
    public void setTileEntity(final int p_147455_1_, final int p_147455_2_, final int p_147455_3_, final TileEntity p_147455_4_) {
        if (p_147455_4_ != null && !p_147455_4_.isInvalid()) {
            if (this.field_147481_N) {
                p_147455_4_.field_145851_c = p_147455_1_;
                p_147455_4_.field_145848_d = p_147455_2_;
                p_147455_4_.field_145849_e = p_147455_3_;
                final Iterator var5 = this.field_147484_a.iterator();
                while (var5.hasNext()) {
                    final TileEntity var6 = var5.next();
                    if (var6.field_145851_c == p_147455_1_ && var6.field_145848_d == p_147455_2_ && var6.field_145849_e == p_147455_3_) {
                        var6.invalidate();
                        var5.remove();
                    }
                }
                this.field_147484_a.add(p_147455_4_);
            }
            else {
                this.field_147482_g.add(p_147455_4_);
                final Chunk var7 = this.getChunkFromChunkCoords(p_147455_1_ >> 4, p_147455_3_ >> 4);
                if (var7 != null) {
                    var7.func_150812_a(p_147455_1_ & 0xF, p_147455_2_, p_147455_3_ & 0xF, p_147455_4_);
                }
            }
        }
    }
    
    public void removeTileEntity(final int p_147475_1_, final int p_147475_2_, final int p_147475_3_) {
        final TileEntity var4 = this.getTileEntity(p_147475_1_, p_147475_2_, p_147475_3_);
        if (var4 != null && this.field_147481_N) {
            var4.invalidate();
            this.field_147484_a.remove(var4);
        }
        else {
            if (var4 != null) {
                this.field_147484_a.remove(var4);
                this.field_147482_g.remove(var4);
            }
            final Chunk var5 = this.getChunkFromChunkCoords(p_147475_1_ >> 4, p_147475_3_ >> 4);
            if (var5 != null) {
                var5.removeTileEntity(p_147475_1_ & 0xF, p_147475_2_, p_147475_3_ & 0xF);
            }
        }
    }
    
    public void func_147457_a(final TileEntity p_147457_1_) {
        this.field_147483_b.add(p_147457_1_);
    }
    
    public boolean func_147469_q(final int p_147469_1_, final int p_147469_2_, final int p_147469_3_) {
        final AxisAlignedBB var4 = this.getBlock(p_147469_1_, p_147469_2_, p_147469_3_).getCollisionBoundingBoxFromPool(this, p_147469_1_, p_147469_2_, p_147469_3_);
        return var4 != null && var4.getAverageEdgeLength() >= 1.0;
    }
    
    public static boolean doesBlockHaveSolidTopSurface(final IBlockAccess p_147466_0_, final int p_147466_1_, final int p_147466_2_, final int p_147466_3_) {
        final Block var4 = p_147466_0_.getBlock(p_147466_1_, p_147466_2_, p_147466_3_);
        final int var5 = p_147466_0_.getBlockMetadata(p_147466_1_, p_147466_2_, p_147466_3_);
        return (var4.getMaterial().isOpaque() && var4.renderAsNormalBlock()) || ((var4 instanceof BlockStairs) ? ((var5 & 0x4) == 0x4) : ((var4 instanceof BlockSlab) ? ((var5 & 0x8) == 0x8) : (var4 instanceof BlockHopper || (var4 instanceof BlockSnow && (var5 & 0x7) == 0x7))));
    }
    
    public boolean isBlockNormalCubeDefault(final int p_147445_1_, final int p_147445_2_, final int p_147445_3_, final boolean p_147445_4_) {
        if (p_147445_1_ < -30000000 || p_147445_3_ < -30000000 || p_147445_1_ >= 30000000 || p_147445_3_ >= 30000000) {
            return p_147445_4_;
        }
        final Chunk var5 = this.chunkProvider.provideChunk(p_147445_1_ >> 4, p_147445_3_ >> 4);
        if (var5 != null && !var5.isEmpty()) {
            final Block var6 = this.getBlock(p_147445_1_, p_147445_2_, p_147445_3_);
            return var6.getMaterial().isOpaque() && var6.renderAsNormalBlock();
        }
        return p_147445_4_;
    }
    
    public void calculateInitialSkylight() {
        final int var1 = this.calculateSkylightSubtracted(1.0f);
        if (var1 != this.skylightSubtracted) {
            this.skylightSubtracted = var1;
        }
    }
    
    public void setAllowedSpawnTypes(final boolean p_72891_1_, final boolean p_72891_2_) {
        this.spawnHostileMobs = p_72891_1_;
        this.spawnPeacefulMobs = p_72891_2_;
    }
    
    public void tick() {
        this.updateWeather();
    }
    
    private void calculateInitialWeather() {
        if (this.worldInfo.isRaining()) {
            this.rainingStrength = 1.0f;
            if (this.worldInfo.isThundering()) {
                this.thunderingStrength = 1.0f;
            }
        }
    }
    
    protected void updateWeather() {
        if (!this.provider.hasNoSky && !this.isClient) {
            int var1 = this.worldInfo.getThunderTime();
            if (var1 <= 0) {
                if (this.worldInfo.isThundering()) {
                    this.worldInfo.setThunderTime(this.rand.nextInt(12000) + 3600);
                }
                else {
                    this.worldInfo.setThunderTime(this.rand.nextInt(168000) + 12000);
                }
            }
            else {
                --var1;
                this.worldInfo.setThunderTime(var1);
                if (var1 <= 0) {
                    this.worldInfo.setThundering(!this.worldInfo.isThundering());
                }
            }
            this.prevThunderingStrength = this.thunderingStrength;
            if (this.worldInfo.isThundering()) {
                this.thunderingStrength += (float)0.01;
            }
            else {
                this.thunderingStrength -= (float)0.01;
            }
            this.thunderingStrength = MathHelper.clamp_float(this.thunderingStrength, 0.0f, 1.0f);
            int var2 = this.worldInfo.getRainTime();
            if (var2 <= 0) {
                if (this.worldInfo.isRaining()) {
                    this.worldInfo.setRainTime(this.rand.nextInt(12000) + 12000);
                }
                else {
                    this.worldInfo.setRainTime(this.rand.nextInt(168000) + 12000);
                }
            }
            else {
                --var2;
                this.worldInfo.setRainTime(var2);
                if (var2 <= 0) {
                    this.worldInfo.setRaining(!this.worldInfo.isRaining());
                }
            }
            this.prevRainingStrength = this.rainingStrength;
            if (this.worldInfo.isRaining()) {
                this.rainingStrength += (float)0.01;
            }
            else {
                this.rainingStrength -= (float)0.01;
            }
            this.rainingStrength = MathHelper.clamp_float(this.rainingStrength, 0.0f, 1.0f);
        }
    }
    
    protected void setActivePlayerChunksAndCheckLight() {
        this.activeChunkSet.clear();
        this.theProfiler.startSection("buildList");
        for (int var1 = 0; var1 < this.playerEntities.size(); ++var1) {
            final EntityPlayer var2 = this.playerEntities.get(var1);
            final int var3 = MathHelper.floor_double(var2.posX / 16.0);
            final int var4 = MathHelper.floor_double(var2.posZ / 16.0);
            for (int var5 = this.func_152379_p(), var6 = -var5; var6 <= var5; ++var6) {
                for (int var7 = -var5; var7 <= var5; ++var7) {
                    this.activeChunkSet.add(new ChunkCoordIntPair(var6 + var3, var7 + var4));
                }
            }
        }
        this.theProfiler.endSection();
        if (this.ambientTickCountdown > 0) {
            --this.ambientTickCountdown;
        }
        this.theProfiler.startSection("playerCheckLight");
        if (!this.playerEntities.isEmpty()) {
            final int var1 = this.rand.nextInt(this.playerEntities.size());
            final EntityPlayer var2 = this.playerEntities.get(var1);
            final int var3 = MathHelper.floor_double(var2.posX) + this.rand.nextInt(11) - 5;
            final int var4 = MathHelper.floor_double(var2.posY) + this.rand.nextInt(11) - 5;
            final int var5 = MathHelper.floor_double(var2.posZ) + this.rand.nextInt(11) - 5;
            this.func_147451_t(var3, var4, var5);
        }
        this.theProfiler.endSection();
    }
    
    protected abstract int func_152379_p();
    
    protected void func_147467_a(final int p_147467_1_, final int p_147467_2_, final Chunk p_147467_3_) {
        this.theProfiler.endStartSection("moodSound");
        if (this.ambientTickCountdown == 0 && !this.isClient) {
            this.updateLCG = this.updateLCG * 3 + 1013904223;
            final int var4 = this.updateLCG >> 2;
            int var5 = var4 & 0xF;
            int var6 = var4 >> 8 & 0xF;
            final int var7 = var4 >> 16 & 0xFF;
            final Block var8 = p_147467_3_.func_150810_a(var5, var7, var6);
            var5 += p_147467_1_;
            var6 += p_147467_2_;
            if (var8.getMaterial() == Material.air && this.getFullBlockLightValue(var5, var7, var6) <= this.rand.nextInt(8) && this.getSavedLightValue(EnumSkyBlock.Sky, var5, var7, var6) <= 0) {
                final EntityPlayer var9 = this.getClosestPlayer(var5 + 0.5, var7 + 0.5, var6 + 0.5, 8.0);
                if (var9 != null && var9.getDistanceSq(var5 + 0.5, var7 + 0.5, var6 + 0.5) > 4.0) {
                    this.playSoundEffect(var5 + 0.5, var7 + 0.5, var6 + 0.5, "ambient.cave.cave", 0.7f, 0.8f + this.rand.nextFloat() * 0.2f);
                    this.ambientTickCountdown = this.rand.nextInt(12000) + 6000;
                }
            }
        }
        this.theProfiler.endStartSection("checkLight");
        p_147467_3_.enqueueRelightChecks();
    }
    
    protected void func_147456_g() {
        this.setActivePlayerChunksAndCheckLight();
    }
    
    public boolean isBlockFreezable(final int p_72884_1_, final int p_72884_2_, final int p_72884_3_) {
        return this.canBlockFreeze(p_72884_1_, p_72884_2_, p_72884_3_, false);
    }
    
    public boolean isBlockFreezableNaturally(final int p_72850_1_, final int p_72850_2_, final int p_72850_3_) {
        return this.canBlockFreeze(p_72850_1_, p_72850_2_, p_72850_3_, true);
    }
    
    public boolean canBlockFreeze(final int p_72834_1_, final int p_72834_2_, final int p_72834_3_, final boolean p_72834_4_) {
        final BiomeGenBase var5 = this.getBiomeGenForCoords(p_72834_1_, p_72834_3_);
        final float var6 = var5.getFloatTemperature(p_72834_1_, p_72834_2_, p_72834_3_);
        if (var6 > 0.15f) {
            return false;
        }
        if (p_72834_2_ >= 0 && p_72834_2_ < 256 && this.getSavedLightValue(EnumSkyBlock.Block, p_72834_1_, p_72834_2_, p_72834_3_) < 10) {
            final Block var7 = this.getBlock(p_72834_1_, p_72834_2_, p_72834_3_);
            if ((var7 == Blocks.water || var7 == Blocks.flowing_water) && this.getBlockMetadata(p_72834_1_, p_72834_2_, p_72834_3_) == 0) {
                if (!p_72834_4_) {
                    return true;
                }
                boolean var8 = true;
                if (var8 && this.getBlock(p_72834_1_ - 1, p_72834_2_, p_72834_3_).getMaterial() != Material.water) {
                    var8 = false;
                }
                if (var8 && this.getBlock(p_72834_1_ + 1, p_72834_2_, p_72834_3_).getMaterial() != Material.water) {
                    var8 = false;
                }
                if (var8 && this.getBlock(p_72834_1_, p_72834_2_, p_72834_3_ - 1).getMaterial() != Material.water) {
                    var8 = false;
                }
                if (var8 && this.getBlock(p_72834_1_, p_72834_2_, p_72834_3_ + 1).getMaterial() != Material.water) {
                    var8 = false;
                }
                if (!var8) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean func_147478_e(final int p_147478_1_, final int p_147478_2_, final int p_147478_3_, final boolean p_147478_4_) {
        final BiomeGenBase var5 = this.getBiomeGenForCoords(p_147478_1_, p_147478_3_);
        final float var6 = var5.getFloatTemperature(p_147478_1_, p_147478_2_, p_147478_3_);
        if (var6 > 0.15f) {
            return false;
        }
        if (!p_147478_4_) {
            return true;
        }
        if (p_147478_2_ >= 0 && p_147478_2_ < 256 && this.getSavedLightValue(EnumSkyBlock.Block, p_147478_1_, p_147478_2_, p_147478_3_) < 10) {
            final Block var7 = this.getBlock(p_147478_1_, p_147478_2_, p_147478_3_);
            if (var7.getMaterial() == Material.air && Blocks.snow_layer.canPlaceBlockAt(this, p_147478_1_, p_147478_2_, p_147478_3_)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean func_147451_t(final int p_147451_1_, final int p_147451_2_, final int p_147451_3_) {
        boolean var4 = false;
        if (!this.provider.hasNoSky) {
            var4 |= this.updateLightByType(EnumSkyBlock.Sky, p_147451_1_, p_147451_2_, p_147451_3_);
        }
        var4 |= this.updateLightByType(EnumSkyBlock.Block, p_147451_1_, p_147451_2_, p_147451_3_);
        return var4;
    }
    
    private int computeLightValue(final int p_98179_1_, final int p_98179_2_, final int p_98179_3_, final EnumSkyBlock p_98179_4_) {
        if (p_98179_4_ == EnumSkyBlock.Sky && this.canBlockSeeTheSky(p_98179_1_, p_98179_2_, p_98179_3_)) {
            return 15;
        }
        final Block var5 = this.getBlock(p_98179_1_, p_98179_2_, p_98179_3_);
        int var6 = (p_98179_4_ == EnumSkyBlock.Sky) ? 0 : var5.getLightValue();
        int var7 = var5.getLightOpacity();
        if (var7 >= 15 && var5.getLightValue() > 0) {
            var7 = 1;
        }
        if (var7 < 1) {
            var7 = 1;
        }
        if (var7 >= 15) {
            return 0;
        }
        if (var6 >= 14) {
            return var6;
        }
        for (int var8 = 0; var8 < 6; ++var8) {
            final int var9 = p_98179_1_ + Facing.offsetsXForSide[var8];
            final int var10 = p_98179_2_ + Facing.offsetsYForSide[var8];
            final int var11 = p_98179_3_ + Facing.offsetsZForSide[var8];
            final int var12 = this.getSavedLightValue(p_98179_4_, var9, var10, var11) - var7;
            if (var12 > var6) {
                var6 = var12;
            }
            if (var6 >= 14) {
                return var6;
            }
        }
        return var6;
    }
    
    public boolean updateLightByType(final EnumSkyBlock p_147463_1_, final int p_147463_2_, final int p_147463_3_, final int p_147463_4_) {
        if (!FPSBoost.LIGHTING.getValue()) {
            return true;
        }
        if (!this.doChunksNearChunkExist(p_147463_2_, p_147463_3_, p_147463_4_, 17)) {
            return false;
        }
        int var5 = 0;
        int var6 = 0;
        this.theProfiler.startSection("getBrightness");
        final int var7 = this.getSavedLightValue(p_147463_1_, p_147463_2_, p_147463_3_, p_147463_4_);
        final int var8 = this.computeLightValue(p_147463_2_, p_147463_3_, p_147463_4_, p_147463_1_);
        if (var8 > var7) {
            this.lightUpdateBlockList[var6++] = 133152;
        }
        else if (var8 < var7) {
            this.lightUpdateBlockList[var6++] = (0x20820 | var7 << 18);
            while (var5 < var6) {
                final int var9 = this.lightUpdateBlockList[var5++];
                final int var10 = (var9 & 0x3F) - 32 + p_147463_2_;
                final int var11 = (var9 >> 6 & 0x3F) - 32 + p_147463_3_;
                final int var12 = (var9 >> 12 & 0x3F) - 32 + p_147463_4_;
                final int var13 = var9 >> 18 & 0xF;
                int var14 = this.getSavedLightValue(p_147463_1_, var10, var11, var12);
                if (var14 == var13) {
                    this.setLightValue(p_147463_1_, var10, var11, var12, 0);
                    if (var13 <= 0) {
                        continue;
                    }
                    final int var15 = MathHelper.abs_int(var10 - p_147463_2_);
                    final int var16 = MathHelper.abs_int(var11 - p_147463_3_);
                    final int var17 = MathHelper.abs_int(var12 - p_147463_4_);
                    if (var15 + var16 + var17 >= 17) {
                        continue;
                    }
                    for (int var18 = 0; var18 < 6; ++var18) {
                        final int var19 = var10 + Facing.offsetsXForSide[var18];
                        final int var20 = var11 + Facing.offsetsYForSide[var18];
                        final int var21 = var12 + Facing.offsetsZForSide[var18];
                        final int var22 = Math.max(1, this.getBlock(var19, var20, var21).getLightOpacity());
                        var14 = this.getSavedLightValue(p_147463_1_, var19, var20, var21);
                        if (var14 == var13 - var22 && var6 < this.lightUpdateBlockList.length) {
                            this.lightUpdateBlockList[var6++] = (var19 - p_147463_2_ + 32 | var20 - p_147463_3_ + 32 << 6 | var21 - p_147463_4_ + 32 << 12 | var13 - var22 << 18);
                        }
                    }
                }
            }
            var5 = 0;
        }
        this.theProfiler.endSection();
        this.theProfiler.startSection("checkedPosition < toCheckCount");
        while (var5 < var6) {
            final int var9 = this.lightUpdateBlockList[var5++];
            final int var10 = (var9 & 0x3F) - 32 + p_147463_2_;
            final int var11 = (var9 >> 6 & 0x3F) - 32 + p_147463_3_;
            final int var12 = (var9 >> 12 & 0x3F) - 32 + p_147463_4_;
            final int var13 = this.getSavedLightValue(p_147463_1_, var10, var11, var12);
            final int var14 = this.computeLightValue(var10, var11, var12, p_147463_1_);
            if (var14 != var13) {
                this.setLightValue(p_147463_1_, var10, var11, var12, var14);
                if (var14 <= var13) {
                    continue;
                }
                final int var15 = Math.abs(var10 - p_147463_2_);
                final int var16 = Math.abs(var11 - p_147463_3_);
                final int var17 = Math.abs(var12 - p_147463_4_);
                final boolean var23 = var6 < this.lightUpdateBlockList.length - 6;
                if (var15 + var16 + var17 >= 17 || !var23) {
                    continue;
                }
                if (this.getSavedLightValue(p_147463_1_, var10 - 1, var11, var12) < var14) {
                    this.lightUpdateBlockList[var6++] = var10 - 1 - p_147463_2_ + 32 + (var11 - p_147463_3_ + 32 << 6) + (var12 - p_147463_4_ + 32 << 12);
                }
                if (this.getSavedLightValue(p_147463_1_, var10 + 1, var11, var12) < var14) {
                    this.lightUpdateBlockList[var6++] = var10 + 1 - p_147463_2_ + 32 + (var11 - p_147463_3_ + 32 << 6) + (var12 - p_147463_4_ + 32 << 12);
                }
                if (this.getSavedLightValue(p_147463_1_, var10, var11 - 1, var12) < var14) {
                    this.lightUpdateBlockList[var6++] = var10 - p_147463_2_ + 32 + (var11 - 1 - p_147463_3_ + 32 << 6) + (var12 - p_147463_4_ + 32 << 12);
                }
                if (this.getSavedLightValue(p_147463_1_, var10, var11 + 1, var12) < var14) {
                    this.lightUpdateBlockList[var6++] = var10 - p_147463_2_ + 32 + (var11 + 1 - p_147463_3_ + 32 << 6) + (var12 - p_147463_4_ + 32 << 12);
                }
                if (this.getSavedLightValue(p_147463_1_, var10, var11, var12 - 1) < var14) {
                    this.lightUpdateBlockList[var6++] = var10 - p_147463_2_ + 32 + (var11 - p_147463_3_ + 32 << 6) + (var12 - 1 - p_147463_4_ + 32 << 12);
                }
                if (this.getSavedLightValue(p_147463_1_, var10, var11, var12 + 1) >= var14) {
                    continue;
                }
                this.lightUpdateBlockList[var6++] = var10 - p_147463_2_ + 32 + (var11 - p_147463_3_ + 32 << 6) + (var12 + 1 - p_147463_4_ + 32 << 12);
            }
        }
        this.theProfiler.endSection();
        return true;
    }
    
    public boolean tickUpdates(final boolean p_72955_1_) {
        return false;
    }
    
    public List getPendingBlockUpdates(final Chunk p_72920_1_, final boolean p_72920_2_) {
        return null;
    }
    
    public List getEntitiesWithinAABBExcludingEntity(final Entity p_72839_1_, final AxisAlignedBB p_72839_2_) {
        return this.getEntitiesWithinAABBExcludingEntity(p_72839_1_, p_72839_2_, null);
    }
    
    public List getEntitiesWithinAABBExcludingEntity(final Entity p_94576_1_, final AxisAlignedBB p_94576_2_, final IEntitySelector p_94576_3_) {
        final ArrayList var4 = new ArrayList();
        final int var5 = MathHelper.floor_double((p_94576_2_.minX - 2.0) / 16.0);
        final int var6 = MathHelper.floor_double((p_94576_2_.maxX + 2.0) / 16.0);
        final int var7 = MathHelper.floor_double((p_94576_2_.minZ - 2.0) / 16.0);
        final int var8 = MathHelper.floor_double((p_94576_2_.maxZ + 2.0) / 16.0);
        for (int var9 = var5; var9 <= var6; ++var9) {
            for (int var10 = var7; var10 <= var8; ++var10) {
                if (this.chunkExists(var9, var10)) {
                    this.getChunkFromChunkCoords(var9, var10).getEntitiesWithinAABBForEntity(p_94576_1_, p_94576_2_, var4, p_94576_3_);
                }
            }
        }
        return var4;
    }
    
    public List getEntitiesWithinAABB(final Class p_72872_1_, final AxisAlignedBB p_72872_2_) {
        return this.selectEntitiesWithinAABB(p_72872_1_, p_72872_2_, null);
    }
    
    public List selectEntitiesWithinAABB(final Class p_82733_1_, final AxisAlignedBB p_82733_2_, final IEntitySelector p_82733_3_) {
        final int var4 = MathHelper.floor_double((p_82733_2_.minX - 2.0) / 16.0);
        final int var5 = MathHelper.floor_double((p_82733_2_.maxX + 2.0) / 16.0);
        final int var6 = MathHelper.floor_double((p_82733_2_.minZ - 2.0) / 16.0);
        final int var7 = MathHelper.floor_double((p_82733_2_.maxZ + 2.0) / 16.0);
        final ArrayList var8 = new ArrayList();
        for (int var9 = var4; var9 <= var5; ++var9) {
            for (int var10 = var6; var10 <= var7; ++var10) {
                if (this.chunkExists(var9, var10)) {
                    this.getChunkFromChunkCoords(var9, var10).getEntitiesOfTypeWithinAAAB(p_82733_1_, p_82733_2_, var8, p_82733_3_);
                }
            }
        }
        return var8;
    }
    
    public Entity findNearestEntityWithinAABB(final Class p_72857_1_, final AxisAlignedBB p_72857_2_, final Entity p_72857_3_) {
        final List var4 = this.getEntitiesWithinAABB(p_72857_1_, p_72857_2_);
        Entity var5 = null;
        double var6 = Double.MAX_VALUE;
        for (int var7 = 0; var7 < var4.size(); ++var7) {
            final Entity var8 = var4.get(var7);
            if (var8 != p_72857_3_) {
                final double var9 = p_72857_3_.getDistanceSqToEntity(var8);
                if (var9 <= var6) {
                    var5 = var8;
                    var6 = var9;
                }
            }
        }
        return var5;
    }
    
    public abstract Entity getEntityByID(final int p0);
    
    public List getLoadedEntityList() {
        return this.loadedEntityList;
    }
    
    public void func_147476_b(final int p_147476_1_, final int p_147476_2_, final int p_147476_3_, final TileEntity p_147476_4_) {
        if (this.blockExists(p_147476_1_, p_147476_2_, p_147476_3_)) {
            this.getChunkFromBlockCoords(p_147476_1_, p_147476_3_).setChunkModified();
        }
    }
    
    public int countEntities(final Class p_72907_1_) {
        int var2 = 0;
        for (int var3 = 0; var3 < this.loadedEntityList.size(); ++var3) {
            final Entity var4 = this.loadedEntityList.get(var3);
            if ((!(var4 instanceof EntityLiving) || !((EntityLiving)var4).isNoDespawnRequired()) && p_72907_1_.isAssignableFrom(var4.getClass())) {
                ++var2;
            }
        }
        return var2;
    }
    
    public void addLoadedEntities(final List p_72868_1_) {
        this.loadedEntityList.addAll(p_72868_1_);
        for (int var2 = 0; var2 < p_72868_1_.size(); ++var2) {
            this.onEntityAdded(p_72868_1_.get(var2));
        }
    }
    
    public void unloadEntities(final List p_72828_1_) {
        this.unloadedEntityList.addAll(p_72828_1_);
    }
    
    public boolean canPlaceEntityOnSide(final Block p_147472_1_, final int p_147472_2_, final int p_147472_3_, final int p_147472_4_, final boolean p_147472_5_, final int p_147472_6_, final Entity p_147472_7_, final ItemStack p_147472_8_) {
        final Block var9 = this.getBlock(p_147472_2_, p_147472_3_, p_147472_4_);
        final AxisAlignedBB var10 = p_147472_5_ ? null : p_147472_1_.getCollisionBoundingBoxFromPool(this, p_147472_2_, p_147472_3_, p_147472_4_);
        return (var10 == null || this.checkNoEntityCollision(var10, p_147472_7_)) && ((var9.getMaterial() == Material.circuits && p_147472_1_ == Blocks.anvil) || (var9.getMaterial().isReplaceable() && p_147472_1_.canReplace(this, p_147472_2_, p_147472_3_, p_147472_4_, p_147472_6_, p_147472_8_)));
    }
    
    public PathEntity getPathEntityToEntity(final Entity p_72865_1_, final Entity p_72865_2_, final float p_72865_3_, final boolean p_72865_4_, final boolean p_72865_5_, final boolean p_72865_6_, final boolean p_72865_7_) {
        this.theProfiler.startSection("pathfind");
        final int var8 = MathHelper.floor_double(p_72865_1_.posX);
        final int var9 = MathHelper.floor_double(p_72865_1_.posY + 1.0);
        final int var10 = MathHelper.floor_double(p_72865_1_.posZ);
        final int var11 = (int)(p_72865_3_ + 16.0f);
        final int var12 = var8 - var11;
        final int var13 = var9 - var11;
        final int var14 = var10 - var11;
        final int var15 = var8 + var11;
        final int var16 = var9 + var11;
        final int var17 = var10 + var11;
        final ChunkCache var18 = new ChunkCache(this, var12, var13, var14, var15, var16, var17, 0);
        final PathEntity var19 = new PathFinder(var18, p_72865_4_, p_72865_5_, p_72865_6_, p_72865_7_).createEntityPathTo(p_72865_1_, p_72865_2_, p_72865_3_);
        this.theProfiler.endSection();
        return var19;
    }
    
    public PathEntity getEntityPathToXYZ(final Entity p_72844_1_, final int p_72844_2_, final int p_72844_3_, final int p_72844_4_, final float p_72844_5_, final boolean p_72844_6_, final boolean p_72844_7_, final boolean p_72844_8_, final boolean p_72844_9_) {
        this.theProfiler.startSection("pathfind");
        final int var10 = MathHelper.floor_double(p_72844_1_.posX);
        final int var11 = MathHelper.floor_double(p_72844_1_.posY);
        final int var12 = MathHelper.floor_double(p_72844_1_.posZ);
        final int var13 = (int)(p_72844_5_ + 8.0f);
        final int var14 = var10 - var13;
        final int var15 = var11 - var13;
        final int var16 = var12 - var13;
        final int var17 = var10 + var13;
        final int var18 = var11 + var13;
        final int var19 = var12 + var13;
        final ChunkCache var20 = new ChunkCache(this, var14, var15, var16, var17, var18, var19, 0);
        final PathEntity var21 = new PathFinder(var20, p_72844_6_, p_72844_7_, p_72844_8_, p_72844_9_).createEntityPathTo(p_72844_1_, p_72844_2_, p_72844_3_, p_72844_4_, p_72844_5_);
        this.theProfiler.endSection();
        return var21;
    }
    
    @Override
    public int isBlockProvidingPowerTo(final int p_72879_1_, final int p_72879_2_, final int p_72879_3_, final int p_72879_4_) {
        return this.getBlock(p_72879_1_, p_72879_2_, p_72879_3_).isProvidingStrongPower(this, p_72879_1_, p_72879_2_, p_72879_3_, p_72879_4_);
    }
    
    public int getBlockPowerInput(final int p_94577_1_, final int p_94577_2_, final int p_94577_3_) {
        final byte var4 = 0;
        int var5 = Math.max(var4, this.isBlockProvidingPowerTo(p_94577_1_, p_94577_2_ - 1, p_94577_3_, 0));
        if (var5 >= 15) {
            return var5;
        }
        var5 = Math.max(var5, this.isBlockProvidingPowerTo(p_94577_1_, p_94577_2_ + 1, p_94577_3_, 1));
        if (var5 >= 15) {
            return var5;
        }
        var5 = Math.max(var5, this.isBlockProvidingPowerTo(p_94577_1_, p_94577_2_, p_94577_3_ - 1, 2));
        if (var5 >= 15) {
            return var5;
        }
        var5 = Math.max(var5, this.isBlockProvidingPowerTo(p_94577_1_, p_94577_2_, p_94577_3_ + 1, 3));
        if (var5 >= 15) {
            return var5;
        }
        var5 = Math.max(var5, this.isBlockProvidingPowerTo(p_94577_1_ - 1, p_94577_2_, p_94577_3_, 4));
        if (var5 >= 15) {
            return var5;
        }
        var5 = Math.max(var5, this.isBlockProvidingPowerTo(p_94577_1_ + 1, p_94577_2_, p_94577_3_, 5));
        return (var5 >= 15) ? var5 : var5;
    }
    
    public boolean getIndirectPowerOutput(final int p_94574_1_, final int p_94574_2_, final int p_94574_3_, final int p_94574_4_) {
        return this.getIndirectPowerLevelTo(p_94574_1_, p_94574_2_, p_94574_3_, p_94574_4_) > 0;
    }
    
    public int getIndirectPowerLevelTo(final int p_72878_1_, final int p_72878_2_, final int p_72878_3_, final int p_72878_4_) {
        return this.getBlock(p_72878_1_, p_72878_2_, p_72878_3_).isNormalCube() ? this.getBlockPowerInput(p_72878_1_, p_72878_2_, p_72878_3_) : this.getBlock(p_72878_1_, p_72878_2_, p_72878_3_).isProvidingWeakPower(this, p_72878_1_, p_72878_2_, p_72878_3_, p_72878_4_);
    }
    
    public boolean isBlockIndirectlyGettingPowered(final int p_72864_1_, final int p_72864_2_, final int p_72864_3_) {
        return this.getIndirectPowerLevelTo(p_72864_1_, p_72864_2_ - 1, p_72864_3_, 0) > 0 || this.getIndirectPowerLevelTo(p_72864_1_, p_72864_2_ + 1, p_72864_3_, 1) > 0 || this.getIndirectPowerLevelTo(p_72864_1_, p_72864_2_, p_72864_3_ - 1, 2) > 0 || this.getIndirectPowerLevelTo(p_72864_1_, p_72864_2_, p_72864_3_ + 1, 3) > 0 || this.getIndirectPowerLevelTo(p_72864_1_ - 1, p_72864_2_, p_72864_3_, 4) > 0 || this.getIndirectPowerLevelTo(p_72864_1_ + 1, p_72864_2_, p_72864_3_, 5) > 0;
    }
    
    public int getStrongestIndirectPower(final int p_94572_1_, final int p_94572_2_, final int p_94572_3_) {
        int var4 = 0;
        for (int var5 = 0; var5 < 6; ++var5) {
            final int var6 = this.getIndirectPowerLevelTo(p_94572_1_ + Facing.offsetsXForSide[var5], p_94572_2_ + Facing.offsetsYForSide[var5], p_94572_3_ + Facing.offsetsZForSide[var5], var5);
            if (var6 >= 15) {
                return 15;
            }
            if (var6 > var4) {
                var4 = var6;
            }
        }
        return var4;
    }
    
    public EntityPlayer getClosestPlayerToEntity(final Entity p_72890_1_, final double p_72890_2_) {
        return this.getClosestPlayer(p_72890_1_.posX, p_72890_1_.posY, p_72890_1_.posZ, p_72890_2_);
    }
    
    public EntityPlayer getClosestPlayer(final double p_72977_1_, final double p_72977_3_, final double p_72977_5_, final double p_72977_7_) {
        double var9 = -1.0;
        EntityPlayer var10 = null;
        for (int var11 = 0; var11 < this.playerEntities.size(); ++var11) {
            final EntityPlayer var12 = this.playerEntities.get(var11);
            final double var13 = var12.getDistanceSq(p_72977_1_, p_72977_3_, p_72977_5_);
            if ((p_72977_7_ < 0.0 || var13 < p_72977_7_ * p_72977_7_) && (var9 == -1.0 || var13 < var9)) {
                var9 = var13;
                var10 = var12;
            }
        }
        return var10;
    }
    
    public EntityPlayer getClosestVulnerablePlayerToEntity(final Entity p_72856_1_, final double p_72856_2_) {
        return this.getClosestVulnerablePlayer(p_72856_1_.posX, p_72856_1_.posY, p_72856_1_.posZ, p_72856_2_);
    }
    
    public EntityPlayer getClosestVulnerablePlayer(final double p_72846_1_, final double p_72846_3_, final double p_72846_5_, final double p_72846_7_) {
        double var9 = -1.0;
        EntityPlayer var10 = null;
        for (int var11 = 0; var11 < this.playerEntities.size(); ++var11) {
            final EntityPlayer var12 = this.playerEntities.get(var11);
            if (!var12.capabilities.disableDamage && var12.isEntityAlive()) {
                final double var13 = var12.getDistanceSq(p_72846_1_, p_72846_3_, p_72846_5_);
                double var14 = p_72846_7_;
                if (var12.isSneaking()) {
                    var14 = p_72846_7_ * 0.800000011920929;
                }
                if (var12.isInvisible()) {
                    float var15 = var12.getArmorVisibility();
                    if (var15 < 0.1f) {
                        var15 = 0.1f;
                    }
                    var14 *= 0.7f * var15;
                }
                if ((p_72846_7_ < 0.0 || var13 < var14 * var14) && (var9 == -1.0 || var13 < var9)) {
                    var9 = var13;
                    var10 = var12;
                }
            }
        }
        return var10;
    }
    
    public EntityPlayer getPlayerEntityByName(final String p_72924_1_) {
        for (int var2 = 0; var2 < this.playerEntities.size(); ++var2) {
            final EntityPlayer var3 = this.playerEntities.get(var2);
            if (p_72924_1_.equals(var3.getCommandSenderName())) {
                return var3;
            }
        }
        return null;
    }
    
    public EntityPlayer func_152378_a(final UUID p_152378_1_) {
        for (int var2 = 0; var2 < this.playerEntities.size(); ++var2) {
            final EntityPlayer var3 = this.playerEntities.get(var2);
            if (p_152378_1_.equals(var3.getUniqueID())) {
                return var3;
            }
        }
        return null;
    }
    
    public void sendQuittingDisconnectingPacket() {
    }
    
    public void checkSessionLock() throws MinecraftException {
        this.saveHandler.checkSessionLock();
    }
    
    public void func_82738_a(final long p_82738_1_) {
        this.worldInfo.incrementTotalWorldTime(p_82738_1_);
    }
    
    public long getSeed() {
        return this.worldInfo.getSeed();
    }
    
    public long getTotalWorldTime() {
        return this.worldInfo.getWorldTotalTime();
    }
    
    public long getWorldTime() {
        return this.worldInfo.getWorldTime();
    }
    
    public void setWorldTime(final long p_72877_1_) {
        this.worldInfo.setWorldTime(p_72877_1_);
    }
    
    public ChunkCoordinates getSpawnPoint() {
        return new ChunkCoordinates(this.worldInfo.getSpawnX(), this.worldInfo.getSpawnY(), this.worldInfo.getSpawnZ());
    }
    
    public void setSpawnLocation(final int p_72950_1_, final int p_72950_2_, final int p_72950_3_) {
        this.worldInfo.setSpawnPosition(p_72950_1_, p_72950_2_, p_72950_3_);
    }
    
    public void joinEntityInSurroundings(final Entity p_72897_1_) {
        final int var2 = MathHelper.floor_double(p_72897_1_.posX / 16.0);
        final int var3 = MathHelper.floor_double(p_72897_1_.posZ / 16.0);
        final byte var4 = 2;
        for (int var5 = var2 - var4; var5 <= var2 + var4; ++var5) {
            for (int var6 = var3 - var4; var6 <= var3 + var4; ++var6) {
                this.getChunkFromChunkCoords(var5, var6);
            }
        }
        if (!this.loadedEntityList.contains(p_72897_1_)) {
            this.loadedEntityList.add(p_72897_1_);
        }
    }
    
    public boolean canMineBlock(final EntityPlayer p_72962_1_, final int p_72962_2_, final int p_72962_3_, final int p_72962_4_) {
        return true;
    }
    
    public void setEntityState(final Entity p_72960_1_, final byte p_72960_2_) {
    }
    
    public IChunkProvider getChunkProvider() {
        return this.chunkProvider;
    }
    
    public void func_147452_c(final int p_147452_1_, final int p_147452_2_, final int p_147452_3_, final Block p_147452_4_, final int p_147452_5_, final int p_147452_6_) {
        p_147452_4_.onBlockEventReceived(this, p_147452_1_, p_147452_2_, p_147452_3_, p_147452_5_, p_147452_6_);
    }
    
    public ISaveHandler getSaveHandler() {
        return this.saveHandler;
    }
    
    public WorldInfo getWorldInfo() {
        return this.worldInfo;
    }
    
    public GameRules getGameRules() {
        return this.worldInfo.getGameRulesInstance();
    }
    
    public void updateAllPlayersSleepingFlag() {
    }
    
    public float getWeightedThunderStrength(final float p_72819_1_) {
        return (this.prevThunderingStrength + (this.thunderingStrength - this.prevThunderingStrength) * p_72819_1_) * this.getRainStrength(p_72819_1_);
    }
    
    public void setThunderStrength(final float p_147442_1_) {
        this.prevThunderingStrength = p_147442_1_;
        this.thunderingStrength = p_147442_1_;
    }
    
    public float getRainStrength(final float p_72867_1_) {
        return this.prevRainingStrength + (this.rainingStrength - this.prevRainingStrength) * p_72867_1_;
    }
    
    public void setRainStrength(final float p_72894_1_) {
        this.prevRainingStrength = p_72894_1_;
        this.rainingStrength = p_72894_1_;
    }
    
    public boolean isThundering() {
        return this.getWeightedThunderStrength(1.0f) > 0.9;
    }
    
    public boolean isRaining() {
        return this.getRainStrength(1.0f) > 0.2;
    }
    
    public boolean canLightningStrikeAt(final int p_72951_1_, final int p_72951_2_, final int p_72951_3_) {
        if (!this.isRaining()) {
            return false;
        }
        if (!this.canBlockSeeTheSky(p_72951_1_, p_72951_2_, p_72951_3_)) {
            return false;
        }
        if (this.getPrecipitationHeight(p_72951_1_, p_72951_3_) > p_72951_2_) {
            return false;
        }
        final BiomeGenBase var4 = this.getBiomeGenForCoords(p_72951_1_, p_72951_3_);
        return !var4.getEnableSnow() && !this.func_147478_e(p_72951_1_, p_72951_2_, p_72951_3_, false) && var4.canSpawnLightningBolt();
    }
    
    public boolean isBlockHighHumidity(final int p_72958_1_, final int p_72958_2_, final int p_72958_3_) {
        final BiomeGenBase var4 = this.getBiomeGenForCoords(p_72958_1_, p_72958_3_);
        return var4.isHighHumidity();
    }
    
    public void setItemData(final String p_72823_1_, final WorldSavedData p_72823_2_) {
        this.mapStorage.setData(p_72823_1_, p_72823_2_);
    }
    
    public WorldSavedData loadItemData(final Class p_72943_1_, final String p_72943_2_) {
        return this.mapStorage.loadData(p_72943_1_, p_72943_2_);
    }
    
    public int getUniqueDataId(final String p_72841_1_) {
        return this.mapStorage.getUniqueDataId(p_72841_1_);
    }
    
    public void playBroadcastSound(final int p_82739_1_, final int p_82739_2_, final int p_82739_3_, final int p_82739_4_, final int p_82739_5_) {
        for (int var6 = 0; var6 < this.worldAccesses.size(); ++var6) {
            this.worldAccesses.get(var6).broadcastSound(p_82739_1_, p_82739_2_, p_82739_3_, p_82739_4_, p_82739_5_);
        }
    }
    
    public void playAuxSFX(final int p_72926_1_, final int p_72926_2_, final int p_72926_3_, final int p_72926_4_, final int p_72926_5_) {
        this.playAuxSFXAtEntity(null, p_72926_1_, p_72926_2_, p_72926_3_, p_72926_4_, p_72926_5_);
    }
    
    public void playAuxSFXAtEntity(final EntityPlayer p_72889_1_, final int p_72889_2_, final int p_72889_3_, final int p_72889_4_, final int p_72889_5_, final int p_72889_6_) {
        try {
            for (int var7 = 0; var7 < this.worldAccesses.size(); ++var7) {
                this.worldAccesses.get(var7).playAuxSFX(p_72889_1_, p_72889_2_, p_72889_3_, p_72889_4_, p_72889_5_, p_72889_6_);
            }
        }
        catch (Throwable var9) {
            final CrashReport var8 = CrashReport.makeCrashReport(var9, "Playing level event");
            final CrashReportCategory var10 = var8.makeCategory("Level event being played");
            var10.addCrashSection("Block coordinates", CrashReportCategory.getLocationInfo(p_72889_3_, p_72889_4_, p_72889_5_));
            var10.addCrashSection("Event source", p_72889_1_);
            var10.addCrashSection("Event type", p_72889_2_);
            var10.addCrashSection("Event data", p_72889_6_);
            throw new ReportedException(var8);
        }
    }
    
    @Override
    public int getHeight() {
        return 256;
    }
    
    public int getActualHeight() {
        return this.provider.hasNoSky ? 128 : 256;
    }
    
    public Random setRandomSeed(final int p_72843_1_, final int p_72843_2_, final int p_72843_3_) {
        final long var4 = p_72843_1_ * 341873128712L + p_72843_2_ * 132897987541L + this.getWorldInfo().getSeed() + p_72843_3_;
        this.rand.setSeed(var4);
        return this.rand;
    }
    
    public ChunkPosition findClosestStructure(final String p_147440_1_, final int p_147440_2_, final int p_147440_3_, final int p_147440_4_) {
        return this.getChunkProvider().func_147416_a(this, p_147440_1_, p_147440_2_, p_147440_3_, p_147440_4_);
    }
    
    @Override
    public boolean extendedLevelsInChunkCache() {
        return false;
    }
    
    public double getHorizon() {
        return 0.0;
    }
    
    public CrashReportCategory addWorldInfoToCrashReport(final CrashReport p_72914_1_) {
        final CrashReportCategory var2 = p_72914_1_.makeCategoryDepth("Affected level", 1);
        var2.addCrashSection("Level name", (this.worldInfo == null) ? "????" : this.worldInfo.getWorldName());
        var2.addCrashSectionCallable("All players", new Callable() {
            private static final String __OBFID = "CL_00000143";
            
            @Override
            public String call() {
                return World.this.playerEntities.size() + " total; " + World.this.playerEntities.toString();
            }
        });
        var2.addCrashSectionCallable("Chunk stats", new Callable() {
            private static final String __OBFID = "CL_00000144";
            
            @Override
            public String call() {
                return World.this.chunkProvider.makeString();
            }
        });
        try {
            this.worldInfo.addToCrashReport(var2);
        }
        catch (Throwable var3) {
            var2.addCrashSectionThrowable("Level Data Unobtainable", var3);
        }
        return var2;
    }
    
    public void destroyBlockInWorldPartially(final int p_147443_1_, final int p_147443_2_, final int p_147443_3_, final int p_147443_4_, final int p_147443_5_) {
        for (int var6 = 0; var6 < this.worldAccesses.size(); ++var6) {
            final IWorldAccess var7 = this.worldAccesses.get(var6);
            var7.destroyBlockPartially(p_147443_1_, p_147443_2_, p_147443_3_, p_147443_4_, p_147443_5_);
        }
    }
    
    public Calendar getCurrentDate() {
        if (this.getTotalWorldTime() % 600L == 0L) {
            this.theCalendar.setTimeInMillis(MinecraftServer.getSystemTimeMillis());
        }
        return this.theCalendar;
    }
    
    public void makeFireworks(final double p_92088_1_, final double p_92088_3_, final double p_92088_5_, final double p_92088_7_, final double p_92088_9_, final double p_92088_11_, final NBTTagCompound p_92088_13_) {
    }
    
    public Scoreboard getScoreboard() {
        return this.worldScoreboard;
    }
    
    public void func_147453_f(final int p_147453_1_, final int p_147453_2_, final int p_147453_3_, final Block p_147453_4_) {
        for (int var5 = 0; var5 < 4; ++var5) {
            int var6 = p_147453_1_ + Direction.offsetX[var5];
            int var7 = p_147453_3_ + Direction.offsetZ[var5];
            final Block var8 = this.getBlock(var6, p_147453_2_, var7);
            if (Blocks.unpowered_comparator.func_149907_e(var8)) {
                var8.onNeighborBlockChange(this, var6, p_147453_2_, var7, p_147453_4_);
            }
            else if (var8.isNormalCube()) {
                var6 += Direction.offsetX[var5];
                var7 += Direction.offsetZ[var5];
                final Block var9 = this.getBlock(var6, p_147453_2_, var7);
                if (Blocks.unpowered_comparator.func_149907_e(var9)) {
                    var9.onNeighborBlockChange(this, var6, p_147453_2_, var7, p_147453_4_);
                }
            }
        }
    }
    
    public float func_147462_b(final double p_147462_1_, final double p_147462_3_, final double p_147462_5_) {
        return this.func_147473_B(MathHelper.floor_double(p_147462_1_), MathHelper.floor_double(p_147462_3_), MathHelper.floor_double(p_147462_5_));
    }
    
    public float func_147473_B(final int p_147473_1_, final int p_147473_2_, final int p_147473_3_) {
        float var4 = 0.0f;
        final boolean var5 = this.difficultySetting == EnumDifficulty.HARD;
        if (this.blockExists(p_147473_1_, p_147473_2_, p_147473_3_)) {
            final float var6 = this.getCurrentMoonPhaseFactor();
            var4 += MathHelper.clamp_float(this.getChunkFromBlockCoords(p_147473_1_, p_147473_3_).inhabitedTime / 3600000.0f, 0.0f, 1.0f) * (var5 ? 1.0f : 0.75f);
            var4 += var6 * 0.25f;
        }
        if (this.difficultySetting == EnumDifficulty.EASY || this.difficultySetting == EnumDifficulty.PEACEFUL) {
            var4 *= this.difficultySetting.getDifficultyId() / 2.0f;
        }
        return MathHelper.clamp_float(var4, 0.0f, var5 ? 1.5f : 1.0f);
    }
    
    public void func_147450_X() {
        for (final IWorldAccess var2 : this.worldAccesses) {
            var2.onStaticEntitiesChanged();
        }
    }
}
