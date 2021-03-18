package net.minecraft.client.multiplayer;

import net.minecraft.client.network.*;
import net.minecraft.client.*;
import net.minecraft.profiler.*;
import net.minecraft.world.storage.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;
import net.minecraft.nbt.*;
import net.minecraft.client.particle.*;
import net.minecraft.scoreboard.*;

public class WorldClient extends World
{
    private NetHandlerPlayClient sendQueue;
    private ChunkProviderClient clientChunkProvider;
    private IntHashMap entityHashSet;
    private Set entityList;
    private Set entitySpawnQueue;
    private final Minecraft mc;
    private final Set previousActiveChunkSet;
    public boolean renderItemInFirstPerson;
    private static final String __OBFID = "CL_00000882";
    
    public WorldClient(final NetHandlerPlayClient p_i45063_1_, final WorldSettings p_i45063_2_, final int p_i45063_3_, final EnumDifficulty p_i45063_4_, final Profiler p_i45063_5_) {
        super(new SaveHandlerMP(), "MpServer", WorldProvider.getProviderForDimension(p_i45063_3_), p_i45063_2_, p_i45063_5_);
        this.entityHashSet = new IntHashMap();
        this.entityList = new HashSet();
        this.entitySpawnQueue = new HashSet();
        this.mc = Minecraft.getMinecraft();
        this.previousActiveChunkSet = new HashSet();
        this.renderItemInFirstPerson = false;
        this.sendQueue = p_i45063_1_;
        this.difficultySetting = p_i45063_4_;
        this.setSpawnLocation(8, 64, 8);
        this.mapStorage = p_i45063_1_.mapStorageOrigin;
    }
    
    @Override
    public void tick() {
        super.tick();
        this.func_82738_a(this.getTotalWorldTime() + 1L);
        if (this.getGameRules().getGameRuleBooleanValue("doDaylightCycle")) {
            this.setWorldTime(this.getWorldTime() + 1L);
        }
        this.theProfiler.startSection("reEntryProcessing");
        for (int var1 = 0; var1 < 10 && !this.entitySpawnQueue.isEmpty(); ++var1) {
            final Entity var2 = this.entitySpawnQueue.iterator().next();
            this.entitySpawnQueue.remove(var2);
            if (!this.loadedEntityList.contains(var2)) {
                this.spawnEntityInWorld(var2);
            }
        }
        this.theProfiler.endStartSection("connection");
        this.sendQueue.onNetworkTick();
        this.theProfiler.endStartSection("chunkCache");
        this.clientChunkProvider.unloadQueuedChunks();
        this.theProfiler.endStartSection("blocks");
        this.func_147456_g();
        this.theProfiler.endSection();
    }
    
    public void invalidateBlockReceiveRegion(final int p_73031_1_, final int p_73031_2_, final int p_73031_3_, final int p_73031_4_, final int p_73031_5_, final int p_73031_6_) {
    }
    
    @Override
    protected IChunkProvider createChunkProvider() {
        return this.clientChunkProvider = new ChunkProviderClient(this);
    }
    
    @Override
    protected void func_147456_g() {
        super.func_147456_g();
        this.previousActiveChunkSet.retainAll(this.activeChunkSet);
        if (this.previousActiveChunkSet.size() == this.activeChunkSet.size()) {
            this.previousActiveChunkSet.clear();
        }
        int var1 = 0;
        for (final ChunkCoordIntPair var3 : this.activeChunkSet) {
            if (!this.previousActiveChunkSet.contains(var3)) {
                final int var4 = var3.chunkXPos * 16;
                final int var5 = var3.chunkZPos * 16;
                this.theProfiler.startSection("getChunk");
                final Chunk var6 = this.getChunkFromChunkCoords(var3.chunkXPos, var3.chunkZPos);
                this.func_147467_a(var4, var5, var6);
                this.theProfiler.endSection();
                this.previousActiveChunkSet.add(var3);
                if (++var1 >= 10) {
                    return;
                }
                continue;
            }
        }
    }
    
    public void doPreChunk(final int p_73025_1_, final int p_73025_2_, final boolean p_73025_3_) {
        if (p_73025_3_) {
            this.clientChunkProvider.loadChunk(p_73025_1_, p_73025_2_);
        }
        else {
            this.clientChunkProvider.unloadChunk(p_73025_1_, p_73025_2_);
        }
        if (!p_73025_3_) {
            this.markBlockRangeForRenderUpdate(p_73025_1_ * 16, 0, p_73025_2_ * 16, p_73025_1_ * 16 + 15, 256, p_73025_2_ * 16 + 15);
        }
    }
    
    @Override
    public boolean spawnEntityInWorld(final Entity p_72838_1_) {
        final boolean var2 = super.spawnEntityInWorld(p_72838_1_);
        this.entityList.add(p_72838_1_);
        if (!var2) {
            this.entitySpawnQueue.add(p_72838_1_);
        }
        else if (p_72838_1_ instanceof EntityMinecart) {
            this.mc.getSoundHandler().playSound(new MovingSoundMinecart((EntityMinecart)p_72838_1_));
        }
        return var2;
    }
    
    @Override
    public void removeEntity(final Entity p_72900_1_) {
        super.removeEntity(p_72900_1_);
        this.entityList.remove(p_72900_1_);
    }
    
    @Override
    protected void onEntityAdded(final Entity p_72923_1_) {
        super.onEntityAdded(p_72923_1_);
        if (this.entitySpawnQueue.contains(p_72923_1_)) {
            this.entitySpawnQueue.remove(p_72923_1_);
        }
    }
    
    @Override
    protected void onEntityRemoved(final Entity p_72847_1_) {
        super.onEntityRemoved(p_72847_1_);
        boolean var2 = false;
        if (this.entityList.contains(p_72847_1_)) {
            if (p_72847_1_.isEntityAlive()) {
                this.entitySpawnQueue.add(p_72847_1_);
                var2 = true;
            }
            else {
                this.entityList.remove(p_72847_1_);
            }
        }
        if (RenderManager.instance.getEntityRenderObject(p_72847_1_).func_147905_a() && !var2) {
            this.mc.renderGlobal.onStaticEntitiesChanged();
        }
    }
    
    public void addEntityToWorld(final int p_73027_1_, final Entity p_73027_2_) {
        final Entity var3 = this.getEntityByID(p_73027_1_);
        if (var3 != null) {
            this.removeEntity(var3);
        }
        this.entityList.add(p_73027_2_);
        p_73027_2_.setEntityId(p_73027_1_);
        if (!this.spawnEntityInWorld(p_73027_2_)) {
            this.entitySpawnQueue.add(p_73027_2_);
        }
        this.entityHashSet.addKey(p_73027_1_, p_73027_2_);
        if (RenderManager.instance.getEntityRenderObject(p_73027_2_).func_147905_a()) {
            this.mc.renderGlobal.onStaticEntitiesChanged();
        }
    }
    
    @Override
    public Entity getEntityByID(final int p_73045_1_) {
        return (p_73045_1_ == this.mc.thePlayer.getEntityId()) ? this.mc.thePlayer : ((Entity)this.entityHashSet.lookup(p_73045_1_));
    }
    
    public Entity removeEntityFromWorld(final int p_73028_1_) {
        final Entity var2 = (Entity)this.entityHashSet.removeObject(p_73028_1_);
        if (var2 != null) {
            this.entityList.remove(var2);
            this.removeEntity(var2);
        }
        return var2;
    }
    
    public boolean func_147492_c(final int p_147492_1_, final int p_147492_2_, final int p_147492_3_, final Block p_147492_4_, final int p_147492_5_) {
        this.invalidateBlockReceiveRegion(p_147492_1_, p_147492_2_, p_147492_3_, p_147492_1_, p_147492_2_, p_147492_3_);
        return super.setBlock(p_147492_1_, p_147492_2_, p_147492_3_, p_147492_4_, p_147492_5_, 3);
    }
    
    @Override
    public void sendQuittingDisconnectingPacket() {
        this.sendQueue.getNetworkManager().closeChannel(new ChatComponentText("Quitting"));
    }
    
    @Override
    protected void updateWeather() {
        if (!this.provider.hasNoSky) {}
    }
    
    @Override
    protected int func_152379_p() {
        return this.mc.gameSettings.renderDistanceChunks;
    }
    
    public void doVoidFogParticles(final int p_73029_1_, final int p_73029_2_, final int p_73029_3_) {
        final byte var4 = 16;
        final Random var5 = new Random();
        for (int var6 = 0; var6 < 1000; ++var6) {
            final int var7 = p_73029_1_ + this.rand.nextInt(var4) - this.rand.nextInt(var4);
            final int var8 = p_73029_2_ + this.rand.nextInt(var4) - this.rand.nextInt(var4);
            final int var9 = p_73029_3_ + this.rand.nextInt(var4) - this.rand.nextInt(var4);
            final Block var10 = this.getBlock(var7, var8, var9);
            if (var10.getMaterial() == Material.air) {
                if (this.rand.nextInt(8) > var8 && this.provider.getWorldHasVoidParticles()) {
                    this.spawnParticle("depthsuspend", var7 + this.rand.nextFloat(), var8 + this.rand.nextFloat(), var9 + this.rand.nextFloat(), 0.0, 0.0, 0.0);
                }
            }
            else {
                var10.randomDisplayTick(this, var7, var8, var9, var5);
            }
        }
    }
    
    public void removeAllEntities() {
        this.loadedEntityList.removeAll(this.unloadedEntityList);
        for (int var1 = 0; var1 < this.unloadedEntityList.size(); ++var1) {
            final Entity var2 = this.unloadedEntityList.get(var1);
            final int var3 = var2.chunkCoordX;
            final int var4 = var2.chunkCoordZ;
            if (var2.addedToChunk && this.chunkExists(var3, var4)) {
                this.getChunkFromChunkCoords(var3, var4).removeEntity(var2);
            }
        }
        for (int var1 = 0; var1 < this.unloadedEntityList.size(); ++var1) {
            this.onEntityRemoved(this.unloadedEntityList.get(var1));
        }
        this.unloadedEntityList.clear();
        for (int var1 = 0; var1 < this.loadedEntityList.size(); ++var1) {
            final Entity var2 = this.loadedEntityList.get(var1);
            if (var2.ridingEntity != null) {
                if (!var2.ridingEntity.isDead && var2.ridingEntity.riddenByEntity == var2) {
                    continue;
                }
                var2.ridingEntity.riddenByEntity = null;
                var2.ridingEntity = null;
            }
            if (var2.isDead) {
                final int var3 = var2.chunkCoordX;
                final int var4 = var2.chunkCoordZ;
                if (var2.addedToChunk && this.chunkExists(var3, var4)) {
                    this.getChunkFromChunkCoords(var3, var4).removeEntity(var2);
                }
                this.loadedEntityList.remove(var1--);
                this.onEntityRemoved(var2);
            }
        }
    }
    
    @Override
    public CrashReportCategory addWorldInfoToCrashReport(final CrashReport p_72914_1_) {
        final CrashReportCategory var2 = super.addWorldInfoToCrashReport(p_72914_1_);
        var2.addCrashSectionCallable("Forced entities", new Callable() {
            private static final String __OBFID = "CL_00000883";
            
            @Override
            public String call() {
                return WorldClient.this.entityList.size() + " total; " + WorldClient.this.entityList.toString();
            }
        });
        var2.addCrashSectionCallable("Retry entities", new Callable() {
            private static final String __OBFID = "CL_00000884";
            
            @Override
            public String call() {
                return WorldClient.this.entitySpawnQueue.size() + " total; " + WorldClient.this.entitySpawnQueue.toString();
            }
        });
        var2.addCrashSectionCallable("Server brand", new Callable() {
            private static final String __OBFID = "CL_00000885";
            
            @Override
            public String call() {
                return WorldClient.this.mc.thePlayer.func_142021_k();
            }
        });
        var2.addCrashSectionCallable("Server type", new Callable() {
            private static final String __OBFID = "CL_00000886";
            
            @Override
            public String call() {
                return (WorldClient.this.mc.getIntegratedServer() == null) ? "Non-integrated multiplayer server" : "Integrated singleplayer server";
            }
        });
        return var2;
    }
    
    @Override
    public void playSound(final double p_72980_1_, final double p_72980_3_, final double p_72980_5_, final String p_72980_7_, final float p_72980_8_, final float p_72980_9_, final boolean p_72980_10_) {
        final double var11 = this.mc.renderViewEntity.getDistanceSq(p_72980_1_, p_72980_3_, p_72980_5_);
        final PositionedSoundRecord var12 = new PositionedSoundRecord(new ResourceLocation(p_72980_7_), p_72980_8_, p_72980_9_, (float)p_72980_1_, (float)p_72980_3_, (float)p_72980_5_);
        if (p_72980_10_ && var11 > 100.0) {
            final double var13 = Math.sqrt(var11) / 40.0;
            this.mc.getSoundHandler().playDelayedSound(var12, (int)(var13 * 20.0));
        }
        else {
            this.mc.getSoundHandler().playSound(var12);
        }
    }
    
    @Override
    public void makeFireworks(final double p_92088_1_, final double p_92088_3_, final double p_92088_5_, final double p_92088_7_, final double p_92088_9_, final double p_92088_11_, final NBTTagCompound p_92088_13_) {
        this.mc.effectRenderer.addEffect(new EntityFireworkStarterFX(this, p_92088_1_, p_92088_3_, p_92088_5_, p_92088_7_, p_92088_9_, p_92088_11_, this.mc.effectRenderer, p_92088_13_));
    }
    
    public void setWorldScoreboard(final Scoreboard p_96443_1_) {
        this.worldScoreboard = p_96443_1_;
    }
    
    @Override
    public void setWorldTime(long p_72877_1_) {
        if (p_72877_1_ < 0L) {
            p_72877_1_ = -p_72877_1_;
            this.getGameRules().setOrCreateGameRule("doDaylightCycle", "false");
        }
        else {
            this.getGameRules().setOrCreateGameRule("doDaylightCycle", "true");
        }
        super.setWorldTime(p_72877_1_);
    }
}
