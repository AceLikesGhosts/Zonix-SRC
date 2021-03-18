package net.minecraft.world.gen;

import net.minecraft.world.chunk.storage.*;
import java.util.concurrent.*;
import net.minecraft.world.chunk.*;
import java.util.*;
import net.minecraft.crash.*;
import java.io.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import org.apache.logging.log4j.*;

public class ChunkProviderServer implements IChunkProvider
{
    private static final Logger logger;
    private Set chunksToUnload;
    private Chunk defaultEmptyChunk;
    private IChunkProvider currentChunkProvider;
    private IChunkLoader currentChunkLoader;
    public boolean loadChunkOnProvideRequest;
    private LongHashMap loadedChunkHashMap;
    private List loadedChunks;
    private WorldServer worldObj;
    private static final String __OBFID = "CL_00001436";
    
    public ChunkProviderServer(final WorldServer p_i1520_1_, final IChunkLoader p_i1520_2_, final IChunkProvider p_i1520_3_) {
        this.chunksToUnload = Collections.newSetFromMap(new ConcurrentHashMap<Object, Boolean>());
        this.loadChunkOnProvideRequest = true;
        this.loadedChunkHashMap = new LongHashMap();
        this.loadedChunks = new ArrayList();
        this.defaultEmptyChunk = new EmptyChunk(p_i1520_1_, 0, 0);
        this.worldObj = p_i1520_1_;
        this.currentChunkLoader = p_i1520_2_;
        this.currentChunkProvider = p_i1520_3_;
    }
    
    @Override
    public boolean chunkExists(final int p_73149_1_, final int p_73149_2_) {
        return this.loadedChunkHashMap.containsItem(ChunkCoordIntPair.chunkXZ2Int(p_73149_1_, p_73149_2_));
    }
    
    public List func_152380_a() {
        return this.loadedChunks;
    }
    
    public void unloadChunksIfNotNearSpawn(final int p_73241_1_, final int p_73241_2_) {
        if (this.worldObj.provider.canRespawnHere()) {
            final ChunkCoordinates var3 = this.worldObj.getSpawnPoint();
            final int var4 = p_73241_1_ * 16 + 8 - var3.posX;
            final int var5 = p_73241_2_ * 16 + 8 - var3.posZ;
            final short var6 = 128;
            if (var4 < -var6 || var4 > var6 || var5 < -var6 || var5 > var6) {
                this.chunksToUnload.add(ChunkCoordIntPair.chunkXZ2Int(p_73241_1_, p_73241_2_));
            }
        }
        else {
            this.chunksToUnload.add(ChunkCoordIntPair.chunkXZ2Int(p_73241_1_, p_73241_2_));
        }
    }
    
    public void unloadAllChunks() {
        for (final Chunk var2 : this.loadedChunks) {
            this.unloadChunksIfNotNearSpawn(var2.xPosition, var2.zPosition);
        }
    }
    
    @Override
    public Chunk loadChunk(final int p_73158_1_, final int p_73158_2_) {
        final long var3 = ChunkCoordIntPair.chunkXZ2Int(p_73158_1_, p_73158_2_);
        this.chunksToUnload.remove(var3);
        Chunk var4 = (Chunk)this.loadedChunkHashMap.getValueByKey(var3);
        if (var4 == null) {
            var4 = this.safeLoadChunk(p_73158_1_, p_73158_2_);
            if (var4 == null) {
                if (this.currentChunkProvider == null) {
                    var4 = this.defaultEmptyChunk;
                }
                else {
                    try {
                        var4 = this.currentChunkProvider.provideChunk(p_73158_1_, p_73158_2_);
                    }
                    catch (Throwable var6) {
                        final CrashReport var5 = CrashReport.makeCrashReport(var6, "Exception generating new chunk");
                        final CrashReportCategory var7 = var5.makeCategory("Chunk to be generated");
                        var7.addCrashSection("Location", String.format("%d,%d", p_73158_1_, p_73158_2_));
                        var7.addCrashSection("Position hash", var3);
                        var7.addCrashSection("Generator", this.currentChunkProvider.makeString());
                        throw new ReportedException(var5);
                    }
                }
            }
            this.loadedChunkHashMap.add(var3, var4);
            this.loadedChunks.add(var4);
            var4.onChunkLoad();
            var4.populateChunk(this, this, p_73158_1_, p_73158_2_);
        }
        return var4;
    }
    
    @Override
    public Chunk provideChunk(final int p_73154_1_, final int p_73154_2_) {
        final Chunk var3 = (Chunk)this.loadedChunkHashMap.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(p_73154_1_, p_73154_2_));
        return (var3 == null) ? ((!this.worldObj.findingSpawnPoint && !this.loadChunkOnProvideRequest) ? this.defaultEmptyChunk : this.loadChunk(p_73154_1_, p_73154_2_)) : var3;
    }
    
    private Chunk safeLoadChunk(final int p_73239_1_, final int p_73239_2_) {
        if (this.currentChunkLoader == null) {
            return null;
        }
        try {
            final Chunk var3 = this.currentChunkLoader.loadChunk(this.worldObj, p_73239_1_, p_73239_2_);
            if (var3 != null) {
                var3.lastSaveTime = this.worldObj.getTotalWorldTime();
                if (this.currentChunkProvider != null) {
                    this.currentChunkProvider.recreateStructures(p_73239_1_, p_73239_2_);
                }
            }
            return var3;
        }
        catch (Exception var4) {
            ChunkProviderServer.logger.error("Couldn't load chunk", (Throwable)var4);
            return null;
        }
    }
    
    private void safeSaveExtraChunkData(final Chunk p_73243_1_) {
        if (this.currentChunkLoader != null) {
            try {
                this.currentChunkLoader.saveExtraChunkData(this.worldObj, p_73243_1_);
            }
            catch (Exception var3) {
                ChunkProviderServer.logger.error("Couldn't save entities", (Throwable)var3);
            }
        }
    }
    
    private void safeSaveChunk(final Chunk p_73242_1_) {
        if (this.currentChunkLoader != null) {
            try {
                p_73242_1_.lastSaveTime = this.worldObj.getTotalWorldTime();
                this.currentChunkLoader.saveChunk(this.worldObj, p_73242_1_);
            }
            catch (IOException var3) {
                ChunkProviderServer.logger.error("Couldn't save chunk", (Throwable)var3);
            }
            catch (MinecraftException var4) {
                ChunkProviderServer.logger.error("Couldn't save chunk; already in use by another instance of Minecraft?", (Throwable)var4);
            }
        }
    }
    
    @Override
    public void populate(final IChunkProvider p_73153_1_, final int p_73153_2_, final int p_73153_3_) {
        final Chunk var4 = this.provideChunk(p_73153_2_, p_73153_3_);
        if (!var4.isTerrainPopulated) {
            var4.func_150809_p();
            if (this.currentChunkProvider != null) {
                this.currentChunkProvider.populate(p_73153_1_, p_73153_2_, p_73153_3_);
                var4.setChunkModified();
            }
        }
    }
    
    @Override
    public boolean saveChunks(final boolean p_73151_1_, final IProgressUpdate p_73151_2_) {
        int var3 = 0;
        final ArrayList var4 = Lists.newArrayList((Iterable)this.loadedChunks);
        for (int var5 = 0; var5 < var4.size(); ++var5) {
            final Chunk var6 = var4.get(var5);
            if (p_73151_1_) {
                this.safeSaveExtraChunkData(var6);
            }
            if (var6.needsSaving(p_73151_1_)) {
                this.safeSaveChunk(var6);
                var6.isModified = false;
                if (++var3 == 24 && !p_73151_1_) {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    public void saveExtraData() {
        if (this.currentChunkLoader != null) {
            this.currentChunkLoader.saveExtraData();
        }
    }
    
    @Override
    public boolean unloadQueuedChunks() {
        if (!this.worldObj.levelSaving) {
            for (int var1 = 0; var1 < 100; ++var1) {
                if (!this.chunksToUnload.isEmpty()) {
                    final Long var2 = this.chunksToUnload.iterator().next();
                    final Chunk var3 = (Chunk)this.loadedChunkHashMap.getValueByKey(var2);
                    if (var3 != null) {
                        var3.onChunkUnload();
                        this.safeSaveChunk(var3);
                        this.safeSaveExtraChunkData(var3);
                        this.loadedChunks.remove(var3);
                    }
                    this.chunksToUnload.remove(var2);
                    this.loadedChunkHashMap.remove(var2);
                }
            }
            if (this.currentChunkLoader != null) {
                this.currentChunkLoader.chunkTick();
            }
        }
        return this.currentChunkProvider.unloadQueuedChunks();
    }
    
    @Override
    public boolean canSave() {
        return !this.worldObj.levelSaving;
    }
    
    @Override
    public String makeString() {
        return "ServerChunkCache: " + this.loadedChunkHashMap.getNumHashElements() + " Drop: " + this.chunksToUnload.size();
    }
    
    @Override
    public List getPossibleCreatures(final EnumCreatureType p_73155_1_, final int p_73155_2_, final int p_73155_3_, final int p_73155_4_) {
        return this.currentChunkProvider.getPossibleCreatures(p_73155_1_, p_73155_2_, p_73155_3_, p_73155_4_);
    }
    
    @Override
    public ChunkPosition func_147416_a(final World p_147416_1_, final String p_147416_2_, final int p_147416_3_, final int p_147416_4_, final int p_147416_5_) {
        return this.currentChunkProvider.func_147416_a(p_147416_1_, p_147416_2_, p_147416_3_, p_147416_4_, p_147416_5_);
    }
    
    @Override
    public int getLoadedChunkCount() {
        return this.loadedChunkHashMap.getNumHashElements();
    }
    
    @Override
    public void recreateStructures(final int p_82695_1_, final int p_82695_2_) {
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
