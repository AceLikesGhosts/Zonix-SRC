package net.minecraft.client.multiplayer;

import net.minecraft.world.chunk.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import org.apache.logging.log4j.*;

public class ChunkProviderClient implements IChunkProvider
{
    private static final Logger logger;
    private Chunk blankChunk;
    private LongHashMap chunkMapping;
    private List chunkListing;
    private World worldObj;
    private static final String __OBFID = "CL_00000880";
    
    public ChunkProviderClient(final World p_i1184_1_) {
        this.chunkMapping = new LongHashMap();
        this.chunkListing = new ArrayList();
        this.blankChunk = new EmptyChunk(p_i1184_1_, 0, 0);
        this.worldObj = p_i1184_1_;
    }
    
    @Override
    public boolean chunkExists(final int p_73149_1_, final int p_73149_2_) {
        return true;
    }
    
    public void unloadChunk(final int p_73234_1_, final int p_73234_2_) {
        final Chunk var3 = this.provideChunk(p_73234_1_, p_73234_2_);
        if (!var3.isEmpty()) {
            var3.onChunkUnload();
        }
        this.chunkMapping.remove(ChunkCoordIntPair.chunkXZ2Int(p_73234_1_, p_73234_2_));
        this.chunkListing.remove(var3);
    }
    
    @Override
    public Chunk loadChunk(final int p_73158_1_, final int p_73158_2_) {
        final Chunk var3 = new Chunk(this.worldObj, p_73158_1_, p_73158_2_);
        this.chunkMapping.add(ChunkCoordIntPair.chunkXZ2Int(p_73158_1_, p_73158_2_), var3);
        this.chunkListing.add(var3);
        var3.isChunkLoaded = true;
        return var3;
    }
    
    @Override
    public Chunk provideChunk(final int p_73154_1_, final int p_73154_2_) {
        final Chunk var3 = (Chunk)this.chunkMapping.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(p_73154_1_, p_73154_2_));
        return (var3 == null) ? this.blankChunk : var3;
    }
    
    @Override
    public boolean saveChunks(final boolean p_73151_1_, final IProgressUpdate p_73151_2_) {
        return true;
    }
    
    @Override
    public void saveExtraData() {
    }
    
    @Override
    public boolean unloadQueuedChunks() {
        final long var1 = System.currentTimeMillis();
        for (final Chunk var3 : this.chunkListing) {
            var3.func_150804_b(System.currentTimeMillis() - var1 > 5L);
        }
        if (System.currentTimeMillis() - var1 > 100L) {
            ChunkProviderClient.logger.info("Warning: Clientside chunk ticking took {} ms", new Object[] { System.currentTimeMillis() - var1 });
        }
        return false;
    }
    
    @Override
    public boolean canSave() {
        return false;
    }
    
    @Override
    public void populate(final IChunkProvider p_73153_1_, final int p_73153_2_, final int p_73153_3_) {
    }
    
    @Override
    public String makeString() {
        return "MultiplayerChunkCache: " + this.chunkMapping.getNumHashElements() + ", " + this.chunkListing.size();
    }
    
    @Override
    public List getPossibleCreatures(final EnumCreatureType p_73155_1_, final int p_73155_2_, final int p_73155_3_, final int p_73155_4_) {
        return null;
    }
    
    @Override
    public ChunkPosition func_147416_a(final World p_147416_1_, final String p_147416_2_, final int p_147416_3_, final int p_147416_4_, final int p_147416_5_) {
        return null;
    }
    
    @Override
    public int getLoadedChunkCount() {
        return this.chunkListing.size();
    }
    
    @Override
    public void recreateStructures(final int p_82695_1_, final int p_82695_2_) {
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
