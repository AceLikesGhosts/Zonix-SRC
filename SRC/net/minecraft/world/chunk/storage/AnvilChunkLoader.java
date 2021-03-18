package net.minecraft.world.chunk.storage;

import net.minecraft.world.storage.*;
import java.io.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.world.chunk.*;
import net.minecraft.entity.*;
import org.apache.logging.log4j.*;

public class AnvilChunkLoader implements IChunkLoader, IThreadedFileIO
{
    private static final Logger logger;
    private List chunksToRemove;
    private Set pendingAnvilChunksCoordinates;
    private Object syncLockObject;
    private final File chunkSaveLocation;
    private static final String __OBFID = "CL_00000384";
    
    public AnvilChunkLoader(final File p_i2003_1_) {
        this.chunksToRemove = new ArrayList();
        this.pendingAnvilChunksCoordinates = new HashSet();
        this.syncLockObject = new Object();
        this.chunkSaveLocation = p_i2003_1_;
    }
    
    @Override
    public Chunk loadChunk(final World p_75815_1_, final int p_75815_2_, final int p_75815_3_) throws IOException {
        NBTTagCompound var4 = null;
        final ChunkCoordIntPair var5 = new ChunkCoordIntPair(p_75815_2_, p_75815_3_);
        final Object var6 = this.syncLockObject;
        synchronized (this.syncLockObject) {
            if (this.pendingAnvilChunksCoordinates.contains(var5)) {
                for (int var7 = 0; var7 < this.chunksToRemove.size(); ++var7) {
                    if (this.chunksToRemove.get(var7).chunkCoordinate.equals(var5)) {
                        var4 = this.chunksToRemove.get(var7).nbtTags;
                        break;
                    }
                }
            }
        }
        if (var4 == null) {
            final DataInputStream var8 = RegionFileCache.getChunkInputStream(this.chunkSaveLocation, p_75815_2_, p_75815_3_);
            if (var8 == null) {
                return null;
            }
            var4 = CompressedStreamTools.read(var8);
        }
        return this.checkedReadChunkFromNBT(p_75815_1_, p_75815_2_, p_75815_3_, var4);
    }
    
    protected Chunk checkedReadChunkFromNBT(final World p_75822_1_, final int p_75822_2_, final int p_75822_3_, final NBTTagCompound p_75822_4_) {
        if (!p_75822_4_.func_150297_b("Level", 10)) {
            AnvilChunkLoader.logger.error("Chunk file at " + p_75822_2_ + "," + p_75822_3_ + " is missing level data, skipping");
            return null;
        }
        if (!p_75822_4_.getCompoundTag("Level").func_150297_b("Sections", 9)) {
            AnvilChunkLoader.logger.error("Chunk file at " + p_75822_2_ + "," + p_75822_3_ + " is missing block data, skipping");
            return null;
        }
        Chunk var5 = this.readChunkFromNBT(p_75822_1_, p_75822_4_.getCompoundTag("Level"));
        if (!var5.isAtLocation(p_75822_2_, p_75822_3_)) {
            AnvilChunkLoader.logger.error("Chunk file at " + p_75822_2_ + "," + p_75822_3_ + " is in the wrong location; relocating. (Expected " + p_75822_2_ + ", " + p_75822_3_ + ", got " + var5.xPosition + ", " + var5.zPosition + ")");
            p_75822_4_.setInteger("xPos", p_75822_2_);
            p_75822_4_.setInteger("zPos", p_75822_3_);
            var5 = this.readChunkFromNBT(p_75822_1_, p_75822_4_.getCompoundTag("Level"));
        }
        return var5;
    }
    
    @Override
    public void saveChunk(final World p_75816_1_, final Chunk p_75816_2_) throws MinecraftException, IOException {
        p_75816_1_.checkSessionLock();
        try {
            final NBTTagCompound var3 = new NBTTagCompound();
            final NBTTagCompound var4 = new NBTTagCompound();
            var3.setTag("Level", var4);
            this.writeChunkToNBT(p_75816_2_, p_75816_1_, var4);
            this.addChunkToPending(p_75816_2_.getChunkCoordIntPair(), var3);
        }
        catch (Exception var5) {
            var5.printStackTrace();
        }
    }
    
    protected void addChunkToPending(final ChunkCoordIntPair p_75824_1_, final NBTTagCompound p_75824_2_) {
        final Object var3 = this.syncLockObject;
        synchronized (this.syncLockObject) {
            if (this.pendingAnvilChunksCoordinates.contains(p_75824_1_)) {
                for (int var4 = 0; var4 < this.chunksToRemove.size(); ++var4) {
                    if (this.chunksToRemove.get(var4).chunkCoordinate.equals(p_75824_1_)) {
                        this.chunksToRemove.set(var4, new PendingChunk(p_75824_1_, p_75824_2_));
                        return;
                    }
                }
            }
            this.chunksToRemove.add(new PendingChunk(p_75824_1_, p_75824_2_));
            this.pendingAnvilChunksCoordinates.add(p_75824_1_);
            ThreadedFileIOBase.threadedIOInstance.queueIO(this);
        }
    }
    
    @Override
    public boolean writeNextIO() {
        PendingChunk var1 = null;
        final Object var2 = this.syncLockObject;
        synchronized (this.syncLockObject) {
            if (this.chunksToRemove.isEmpty()) {
                return false;
            }
            var1 = this.chunksToRemove.remove(0);
            this.pendingAnvilChunksCoordinates.remove(var1.chunkCoordinate);
        }
        if (var1 != null) {
            try {
                this.writeChunkNBTTags(var1);
            }
            catch (Exception var3) {
                var3.printStackTrace();
            }
        }
        return true;
    }
    
    private void writeChunkNBTTags(final PendingChunk p_75821_1_) throws IOException {
        final DataOutputStream var2 = RegionFileCache.getChunkOutputStream(this.chunkSaveLocation, p_75821_1_.chunkCoordinate.chunkXPos, p_75821_1_.chunkCoordinate.chunkZPos);
        CompressedStreamTools.write(p_75821_1_.nbtTags, var2);
        var2.close();
    }
    
    @Override
    public void saveExtraChunkData(final World p_75819_1_, final Chunk p_75819_2_) {
    }
    
    @Override
    public void chunkTick() {
    }
    
    @Override
    public void saveExtraData() {
        while (this.writeNextIO()) {}
    }
    
    private void writeChunkToNBT(final Chunk p_75820_1_, final World p_75820_2_, final NBTTagCompound p_75820_3_) {
        p_75820_3_.setByte("V", (byte)1);
        p_75820_3_.setInteger("xPos", p_75820_1_.xPosition);
        p_75820_3_.setInteger("zPos", p_75820_1_.zPosition);
        p_75820_3_.setLong("LastUpdate", p_75820_2_.getTotalWorldTime());
        p_75820_3_.setIntArray("HeightMap", p_75820_1_.heightMap);
        p_75820_3_.setBoolean("TerrainPopulated", p_75820_1_.isTerrainPopulated);
        p_75820_3_.setBoolean("LightPopulated", p_75820_1_.isLightPopulated);
        p_75820_3_.setLong("InhabitedTime", p_75820_1_.inhabitedTime);
        final ExtendedBlockStorage[] var4 = p_75820_1_.getBlockStorageArray();
        final NBTTagList var5 = new NBTTagList();
        final boolean var6 = !p_75820_2_.provider.hasNoSky;
        final ExtendedBlockStorage[] var7 = var4;
        for (int var8 = var4.length, var9 = 0; var9 < var8; ++var9) {
            final ExtendedBlockStorage var10 = var7[var9];
            if (var10 != null) {
                final NBTTagCompound var11 = new NBTTagCompound();
                var11.setByte("Y", (byte)(var10.getYLocation() >> 4 & 0xFF));
                var11.setByteArray("Blocks", var10.getBlockLSBArray());
                if (var10.getBlockMSBArray() != null) {
                    var11.setByteArray("Add", var10.getBlockMSBArray().data);
                }
                var11.setByteArray("Data", var10.getMetadataArray().data);
                var11.setByteArray("BlockLight", var10.getBlocklightArray().data);
                if (var6) {
                    var11.setByteArray("SkyLight", var10.getSkylightArray().data);
                }
                else {
                    var11.setByteArray("SkyLight", new byte[var10.getBlocklightArray().data.length]);
                }
                var5.appendTag(var11);
            }
        }
        p_75820_3_.setTag("Sections", var5);
        p_75820_3_.setByteArray("Biomes", p_75820_1_.getBiomeArray());
        p_75820_1_.hasEntities = false;
        final NBTTagList var12 = new NBTTagList();
        for (int var8 = 0; var8 < p_75820_1_.entityLists.length; ++var8) {
            for (final Entity var14 : p_75820_1_.entityLists[var8]) {
                final NBTTagCompound var11 = new NBTTagCompound();
                if (var14.writeToNBTOptional(var11)) {
                    p_75820_1_.hasEntities = true;
                    var12.appendTag(var11);
                }
            }
        }
        p_75820_3_.setTag("Entities", var12);
        final NBTTagList var15 = new NBTTagList();
        for (final TileEntity var16 : p_75820_1_.chunkTileEntityMap.values()) {
            final NBTTagCompound var11 = new NBTTagCompound();
            var16.writeToNBT(var11);
            var15.appendTag(var11);
        }
        p_75820_3_.setTag("TileEntities", var15);
        final List var17 = p_75820_2_.getPendingBlockUpdates(p_75820_1_, false);
        if (var17 != null) {
            final long var18 = p_75820_2_.getTotalWorldTime();
            final NBTTagList var19 = new NBTTagList();
            for (final NextTickListEntry var21 : var17) {
                final NBTTagCompound var22 = new NBTTagCompound();
                var22.setInteger("i", Block.getIdFromBlock(var21.func_151351_a()));
                var22.setInteger("x", var21.xCoord);
                var22.setInteger("y", var21.yCoord);
                var22.setInteger("z", var21.zCoord);
                var22.setInteger("t", (int)(var21.scheduledTime - var18));
                var22.setInteger("p", var21.priority);
                var19.appendTag(var22);
            }
            p_75820_3_.setTag("TileTicks", var19);
        }
    }
    
    private Chunk readChunkFromNBT(final World p_75823_1_, final NBTTagCompound p_75823_2_) {
        final int var3 = p_75823_2_.getInteger("xPos");
        final int var4 = p_75823_2_.getInteger("zPos");
        final Chunk var5 = new Chunk(p_75823_1_, var3, var4);
        var5.heightMap = p_75823_2_.getIntArray("HeightMap");
        var5.isTerrainPopulated = p_75823_2_.getBoolean("TerrainPopulated");
        var5.isLightPopulated = p_75823_2_.getBoolean("LightPopulated");
        var5.inhabitedTime = p_75823_2_.getLong("InhabitedTime");
        final NBTTagList var6 = p_75823_2_.getTagList("Sections", 10);
        final byte var7 = 16;
        final ExtendedBlockStorage[] var8 = new ExtendedBlockStorage[var7];
        final boolean var9 = !p_75823_1_.provider.hasNoSky;
        for (int var10 = 0; var10 < var6.tagCount(); ++var10) {
            final NBTTagCompound var11 = var6.getCompoundTagAt(var10);
            final byte var12 = var11.getByte("Y");
            final ExtendedBlockStorage var13 = new ExtendedBlockStorage(var12 << 4, var9);
            var13.setBlockLSBArray(var11.getByteArray("Blocks"));
            if (var11.func_150297_b("Add", 7)) {
                var13.setBlockMSBArray(new NibbleArray(var11.getByteArray("Add"), 4));
            }
            var13.setBlockMetadataArray(new NibbleArray(var11.getByteArray("Data"), 4));
            var13.setBlocklightArray(new NibbleArray(var11.getByteArray("BlockLight"), 4));
            if (var9) {
                var13.setSkylightArray(new NibbleArray(var11.getByteArray("SkyLight"), 4));
            }
            var13.removeInvalidBlocks();
            var8[var12] = var13;
        }
        var5.setStorageArrays(var8);
        if (p_75823_2_.func_150297_b("Biomes", 7)) {
            var5.setBiomeArray(p_75823_2_.getByteArray("Biomes"));
        }
        final NBTTagList var14 = p_75823_2_.getTagList("Entities", 10);
        if (var14 != null) {
            for (int var15 = 0; var15 < var14.tagCount(); ++var15) {
                final NBTTagCompound var16 = var14.getCompoundTagAt(var15);
                final Entity var17 = EntityList.createEntityFromNBT(var16, p_75823_1_);
                var5.hasEntities = true;
                if (var17 != null) {
                    var5.addEntity(var17);
                    Entity var18 = var17;
                    for (NBTTagCompound var19 = var16; var19.func_150297_b("Riding", 10); var19 = var19.getCompoundTag("Riding")) {
                        final Entity var20 = EntityList.createEntityFromNBT(var19.getCompoundTag("Riding"), p_75823_1_);
                        if (var20 != null) {
                            var5.addEntity(var20);
                            var18.mountEntity(var20);
                        }
                        var18 = var20;
                    }
                }
            }
        }
        final NBTTagList var21 = p_75823_2_.getTagList("TileEntities", 10);
        if (var21 != null) {
            for (int var22 = 0; var22 < var21.tagCount(); ++var22) {
                final NBTTagCompound var23 = var21.getCompoundTagAt(var22);
                final TileEntity var24 = TileEntity.createAndLoadEntity(var23);
                if (var24 != null) {
                    var5.addTileEntity(var24);
                }
            }
        }
        if (p_75823_2_.func_150297_b("TileTicks", 9)) {
            final NBTTagList var25 = p_75823_2_.getTagList("TileTicks", 10);
            if (var25 != null) {
                for (int var26 = 0; var26 < var25.tagCount(); ++var26) {
                    final NBTTagCompound var27 = var25.getCompoundTagAt(var26);
                    p_75823_1_.func_147446_b(var27.getInteger("x"), var27.getInteger("y"), var27.getInteger("z"), Block.getBlockById(var27.getInteger("i")), var27.getInteger("t"), var27.getInteger("p"));
                }
            }
        }
        return var5;
    }
    
    static {
        logger = LogManager.getLogger();
    }
    
    static class PendingChunk
    {
        public final ChunkCoordIntPair chunkCoordinate;
        public final NBTTagCompound nbtTags;
        private static final String __OBFID = "CL_00000385";
        
        public PendingChunk(final ChunkCoordIntPair p_i2002_1_, final NBTTagCompound p_i2002_2_) {
            this.chunkCoordinate = p_i2002_1_;
            this.nbtTags = p_i2002_2_;
        }
    }
}
