package net.minecraft.world.gen;

import net.minecraft.block.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.chunk.storage.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.structure.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;

public class ChunkProviderFlat implements IChunkProvider
{
    private World worldObj;
    private Random random;
    private final Block[] cachedBlockIDs;
    private final byte[] cachedBlockMetadata;
    private final FlatGeneratorInfo flatWorldGenInfo;
    private final List structureGenerators;
    private final boolean hasDecoration;
    private final boolean hasDungeons;
    private WorldGenLakes waterLakeGenerator;
    private WorldGenLakes lavaLakeGenerator;
    private static final String __OBFID = "CL_00000391";
    
    public ChunkProviderFlat(final World p_i2004_1_, final long p_i2004_2_, final boolean p_i2004_4_, final String p_i2004_5_) {
        this.cachedBlockIDs = new Block[256];
        this.cachedBlockMetadata = new byte[256];
        this.structureGenerators = new ArrayList();
        this.worldObj = p_i2004_1_;
        this.random = new Random(p_i2004_2_);
        this.flatWorldGenInfo = FlatGeneratorInfo.createFlatGeneratorFromString(p_i2004_5_);
        if (p_i2004_4_) {
            final Map var6 = this.flatWorldGenInfo.getWorldFeatures();
            if (var6.containsKey("village")) {
                final Map var7 = var6.get("village");
                if (!var7.containsKey("size")) {
                    var7.put("size", "1");
                }
                this.structureGenerators.add(new MapGenVillage(var7));
            }
            if (var6.containsKey("biome_1")) {
                this.structureGenerators.add(new MapGenScatteredFeature(var6.get("biome_1")));
            }
            if (var6.containsKey("mineshaft")) {
                this.structureGenerators.add(new MapGenMineshaft(var6.get("mineshaft")));
            }
            if (var6.containsKey("stronghold")) {
                this.structureGenerators.add(new MapGenStronghold(var6.get("stronghold")));
            }
        }
        this.hasDecoration = this.flatWorldGenInfo.getWorldFeatures().containsKey("decoration");
        if (this.flatWorldGenInfo.getWorldFeatures().containsKey("lake")) {
            this.waterLakeGenerator = new WorldGenLakes(Blocks.water);
        }
        if (this.flatWorldGenInfo.getWorldFeatures().containsKey("lava_lake")) {
            this.lavaLakeGenerator = new WorldGenLakes(Blocks.lava);
        }
        this.hasDungeons = this.flatWorldGenInfo.getWorldFeatures().containsKey("dungeon");
        for (final FlatLayerInfo var9 : this.flatWorldGenInfo.getFlatLayers()) {
            for (int var10 = var9.getMinY(); var10 < var9.getMinY() + var9.getLayerCount(); ++var10) {
                this.cachedBlockIDs[var10] = var9.func_151536_b();
                this.cachedBlockMetadata[var10] = (byte)var9.getFillBlockMeta();
            }
        }
    }
    
    @Override
    public Chunk loadChunk(final int p_73158_1_, final int p_73158_2_) {
        return this.provideChunk(p_73158_1_, p_73158_2_);
    }
    
    @Override
    public Chunk provideChunk(final int p_73154_1_, final int p_73154_2_) {
        final Chunk var3 = new Chunk(this.worldObj, p_73154_1_, p_73154_2_);
        for (int var4 = 0; var4 < this.cachedBlockIDs.length; ++var4) {
            final Block var5 = this.cachedBlockIDs[var4];
            if (var5 != null) {
                final int var6 = var4 >> 4;
                ExtendedBlockStorage var7 = var3.getBlockStorageArray()[var6];
                if (var7 == null) {
                    var7 = new ExtendedBlockStorage(var4, !this.worldObj.provider.hasNoSky);
                    var3.getBlockStorageArray()[var6] = var7;
                }
                for (int var8 = 0; var8 < 16; ++var8) {
                    for (int var9 = 0; var9 < 16; ++var9) {
                        var7.func_150818_a(var8, var4 & 0xF, var9, var5);
                        var7.setExtBlockMetadata(var8, var4 & 0xF, var9, this.cachedBlockMetadata[var4]);
                    }
                }
            }
        }
        var3.generateSkylightMap();
        final BiomeGenBase[] var10 = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(null, p_73154_1_ * 16, p_73154_2_ * 16, 16, 16);
        int var6;
        byte[] var11;
        for (var11 = var3.getBiomeArray(), var6 = 0; var6 < var11.length; ++var6) {
            var11[var6] = (byte)var10[var6].biomeID;
        }
        for (final MapGenBase var13 : this.structureGenerators) {
            var13.func_151539_a(this, this.worldObj, p_73154_1_, p_73154_2_, null);
        }
        var3.generateSkylightMap();
        return var3;
    }
    
    @Override
    public boolean chunkExists(final int p_73149_1_, final int p_73149_2_) {
        return true;
    }
    
    @Override
    public void populate(final IChunkProvider p_73153_1_, final int p_73153_2_, final int p_73153_3_) {
        final int var4 = p_73153_2_ * 16;
        final int var5 = p_73153_3_ * 16;
        final BiomeGenBase var6 = this.worldObj.getBiomeGenForCoords(var4 + 16, var5 + 16);
        boolean var7 = false;
        this.random.setSeed(this.worldObj.getSeed());
        final long var8 = this.random.nextLong() / 2L * 2L + 1L;
        final long var9 = this.random.nextLong() / 2L * 2L + 1L;
        this.random.setSeed(p_73153_2_ * var8 + p_73153_3_ * var9 ^ this.worldObj.getSeed());
        for (final MapGenStructure var11 : this.structureGenerators) {
            final boolean var12 = var11.generateStructuresInChunk(this.worldObj, this.random, p_73153_2_, p_73153_3_);
            if (var11 instanceof MapGenVillage) {
                var7 |= var12;
            }
        }
        if (this.waterLakeGenerator != null && !var7 && this.random.nextInt(4) == 0) {
            final int var13 = var4 + this.random.nextInt(16) + 8;
            final int var14 = this.random.nextInt(256);
            final int var15 = var5 + this.random.nextInt(16) + 8;
            this.waterLakeGenerator.generate(this.worldObj, this.random, var13, var14, var15);
        }
        if (this.lavaLakeGenerator != null && !var7 && this.random.nextInt(8) == 0) {
            final int var13 = var4 + this.random.nextInt(16) + 8;
            final int var14 = this.random.nextInt(this.random.nextInt(248) + 8);
            final int var15 = var5 + this.random.nextInt(16) + 8;
            if (var14 < 63 || this.random.nextInt(10) == 0) {
                this.lavaLakeGenerator.generate(this.worldObj, this.random, var13, var14, var15);
            }
        }
        if (this.hasDungeons) {
            for (int var13 = 0; var13 < 8; ++var13) {
                final int var14 = var4 + this.random.nextInt(16) + 8;
                final int var15 = this.random.nextInt(256);
                final int var16 = var5 + this.random.nextInt(16) + 8;
                new WorldGenDungeons().generate(this.worldObj, this.random, var14, var15, var16);
            }
        }
        if (this.hasDecoration) {
            var6.decorate(this.worldObj, this.random, var4, var5);
        }
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
        return false;
    }
    
    @Override
    public boolean canSave() {
        return true;
    }
    
    @Override
    public String makeString() {
        return "FlatLevelSource";
    }
    
    @Override
    public List getPossibleCreatures(final EnumCreatureType p_73155_1_, final int p_73155_2_, final int p_73155_3_, final int p_73155_4_) {
        final BiomeGenBase var5 = this.worldObj.getBiomeGenForCoords(p_73155_2_, p_73155_4_);
        return var5.getSpawnableList(p_73155_1_);
    }
    
    @Override
    public ChunkPosition func_147416_a(final World p_147416_1_, final String p_147416_2_, final int p_147416_3_, final int p_147416_4_, final int p_147416_5_) {
        if ("Stronghold".equals(p_147416_2_)) {
            for (final MapGenStructure var7 : this.structureGenerators) {
                if (var7 instanceof MapGenStronghold) {
                    return var7.func_151545_a(p_147416_1_, p_147416_3_, p_147416_4_, p_147416_5_);
                }
            }
        }
        return null;
    }
    
    @Override
    public int getLoadedChunkCount() {
        return 0;
    }
    
    @Override
    public void recreateStructures(final int p_82695_1_, final int p_82695_2_) {
        for (final MapGenStructure var4 : this.structureGenerators) {
            var4.func_151539_a(this, this.worldObj, p_82695_1_, p_82695_2_, null);
        }
    }
}
