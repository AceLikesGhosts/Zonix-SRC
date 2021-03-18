package net.minecraft.world.gen;

import net.minecraft.world.gen.structure.*;
import net.minecraft.world.biome.*;
import net.minecraft.init.*;
import net.minecraft.world.chunk.*;
import net.minecraft.block.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.world.*;

public class ChunkProviderGenerate implements IChunkProvider
{
    private Random rand;
    private NoiseGeneratorOctaves field_147431_j;
    private NoiseGeneratorOctaves field_147432_k;
    private NoiseGeneratorOctaves field_147429_l;
    private NoiseGeneratorPerlin field_147430_m;
    public NoiseGeneratorOctaves noiseGen5;
    public NoiseGeneratorOctaves noiseGen6;
    public NoiseGeneratorOctaves mobSpawnerNoise;
    private World worldObj;
    private final boolean mapFeaturesEnabled;
    private WorldType field_147435_p;
    private final double[] field_147434_q;
    private final float[] parabolicField;
    private double[] stoneNoise;
    private MapGenBase caveGenerator;
    private MapGenStronghold strongholdGenerator;
    private MapGenVillage villageGenerator;
    private MapGenMineshaft mineshaftGenerator;
    private MapGenScatteredFeature scatteredFeatureGenerator;
    private MapGenBase ravineGenerator;
    private BiomeGenBase[] biomesForGeneration;
    double[] field_147427_d;
    double[] field_147428_e;
    double[] field_147425_f;
    double[] field_147426_g;
    int[][] field_73219_j;
    private static final String __OBFID = "CL_00000396";
    
    public ChunkProviderGenerate(final World p_i2006_1_, final long p_i2006_2_, final boolean p_i2006_4_) {
        this.stoneNoise = new double[256];
        this.caveGenerator = new MapGenCaves();
        this.strongholdGenerator = new MapGenStronghold();
        this.villageGenerator = new MapGenVillage();
        this.mineshaftGenerator = new MapGenMineshaft();
        this.scatteredFeatureGenerator = new MapGenScatteredFeature();
        this.ravineGenerator = new MapGenRavine();
        this.field_73219_j = new int[32][32];
        this.worldObj = p_i2006_1_;
        this.mapFeaturesEnabled = p_i2006_4_;
        this.field_147435_p = p_i2006_1_.getWorldInfo().getTerrainType();
        this.rand = new Random(p_i2006_2_);
        this.field_147431_j = new NoiseGeneratorOctaves(this.rand, 16);
        this.field_147432_k = new NoiseGeneratorOctaves(this.rand, 16);
        this.field_147429_l = new NoiseGeneratorOctaves(this.rand, 8);
        this.field_147430_m = new NoiseGeneratorPerlin(this.rand, 4);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
        this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
        this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);
        this.field_147434_q = new double[825];
        this.parabolicField = new float[25];
        for (int var5 = -2; var5 <= 2; ++var5) {
            for (int var6 = -2; var6 <= 2; ++var6) {
                final float var7 = 10.0f / MathHelper.sqrt_float(var5 * var5 + var6 * var6 + 0.2f);
                this.parabolicField[var5 + 2 + (var6 + 2) * 5] = var7;
            }
        }
    }
    
    public void func_147424_a(final int p_147424_1_, final int p_147424_2_, final Block[] p_147424_3_) {
        final byte var4 = 63;
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, p_147424_1_ * 4 - 2, p_147424_2_ * 4 - 2, 10, 10);
        this.func_147423_a(p_147424_1_ * 4, 0, p_147424_2_ * 4);
        for (int var5 = 0; var5 < 4; ++var5) {
            final int var6 = var5 * 5;
            final int var7 = (var5 + 1) * 5;
            for (int var8 = 0; var8 < 4; ++var8) {
                final int var9 = (var6 + var8) * 33;
                final int var10 = (var6 + var8 + 1) * 33;
                final int var11 = (var7 + var8) * 33;
                final int var12 = (var7 + var8 + 1) * 33;
                for (int var13 = 0; var13 < 32; ++var13) {
                    final double var14 = 0.125;
                    double var15 = this.field_147434_q[var9 + var13];
                    double var16 = this.field_147434_q[var10 + var13];
                    double var17 = this.field_147434_q[var11 + var13];
                    double var18 = this.field_147434_q[var12 + var13];
                    final double var19 = (this.field_147434_q[var9 + var13 + 1] - var15) * var14;
                    final double var20 = (this.field_147434_q[var10 + var13 + 1] - var16) * var14;
                    final double var21 = (this.field_147434_q[var11 + var13 + 1] - var17) * var14;
                    final double var22 = (this.field_147434_q[var12 + var13 + 1] - var18) * var14;
                    for (int var23 = 0; var23 < 8; ++var23) {
                        final double var24 = 0.25;
                        double var25 = var15;
                        double var26 = var16;
                        final double var27 = (var17 - var15) * var24;
                        final double var28 = (var18 - var16) * var24;
                        for (int var29 = 0; var29 < 4; ++var29) {
                            int var30 = var29 + var5 * 4 << 12 | 0 + var8 * 4 << 8 | var13 * 8 + var23;
                            final short var31 = 256;
                            var30 -= var31;
                            final double var32 = 0.25;
                            final double var33 = (var26 - var25) * var32;
                            double var34 = var25 - var33;
                            for (int var35 = 0; var35 < 4; ++var35) {
                                if ((var34 += var33) > 0.0) {
                                    p_147424_3_[var30 += var31] = Blocks.stone;
                                }
                                else if (var13 * 8 + var23 < var4) {
                                    p_147424_3_[var30 += var31] = Blocks.water;
                                }
                                else {
                                    p_147424_3_[var30 += var31] = null;
                                }
                            }
                            var25 += var27;
                            var26 += var28;
                        }
                        var15 += var19;
                        var16 += var20;
                        var17 += var21;
                        var18 += var22;
                    }
                }
            }
        }
    }
    
    public void func_147422_a(final int p_147422_1_, final int p_147422_2_, final Block[] p_147422_3_, final byte[] p_147422_4_, final BiomeGenBase[] p_147422_5_) {
        final double var6 = 0.03125;
        this.stoneNoise = this.field_147430_m.func_151599_a(this.stoneNoise, p_147422_1_ * 16, p_147422_2_ * 16, 16, 16, var6 * 2.0, var6 * 2.0, 1.0);
        for (int var7 = 0; var7 < 16; ++var7) {
            for (int var8 = 0; var8 < 16; ++var8) {
                final BiomeGenBase var9 = p_147422_5_[var8 + var7 * 16];
                var9.func_150573_a(this.worldObj, this.rand, p_147422_3_, p_147422_4_, p_147422_1_ * 16 + var7, p_147422_2_ * 16 + var8, this.stoneNoise[var8 + var7 * 16]);
            }
        }
    }
    
    @Override
    public Chunk loadChunk(final int p_73158_1_, final int p_73158_2_) {
        return this.provideChunk(p_73158_1_, p_73158_2_);
    }
    
    @Override
    public Chunk provideChunk(final int p_73154_1_, final int p_73154_2_) {
        this.rand.setSeed(p_73154_1_ * 341873128712L + p_73154_2_ * 132897987541L);
        final Block[] var3 = new Block[65536];
        final byte[] var4 = new byte[65536];
        this.func_147424_a(p_73154_1_, p_73154_2_, var3);
        this.func_147422_a(p_73154_1_, p_73154_2_, var3, var4, this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, p_73154_1_ * 16, p_73154_2_ * 16, 16, 16));
        this.caveGenerator.func_151539_a(this, this.worldObj, p_73154_1_, p_73154_2_, var3);
        this.ravineGenerator.func_151539_a(this, this.worldObj, p_73154_1_, p_73154_2_, var3);
        if (this.mapFeaturesEnabled) {
            this.mineshaftGenerator.func_151539_a(this, this.worldObj, p_73154_1_, p_73154_2_, var3);
            this.villageGenerator.func_151539_a(this, this.worldObj, p_73154_1_, p_73154_2_, var3);
            this.strongholdGenerator.func_151539_a(this, this.worldObj, p_73154_1_, p_73154_2_, var3);
            this.scatteredFeatureGenerator.func_151539_a(this, this.worldObj, p_73154_1_, p_73154_2_, var3);
        }
        final Chunk var5 = new Chunk(this.worldObj, var3, var4, p_73154_1_, p_73154_2_);
        final byte[] var6 = var5.getBiomeArray();
        for (int var7 = 0; var7 < var6.length; ++var7) {
            var6[var7] = (byte)this.biomesForGeneration[var7].biomeID;
        }
        var5.generateSkylightMap();
        return var5;
    }
    
    private void func_147423_a(final int p_147423_1_, final int p_147423_2_, final int p_147423_3_) {
        final double var4 = 684.412;
        final double var5 = 684.412;
        final double var6 = 512.0;
        final double var7 = 512.0;
        this.field_147426_g = this.noiseGen6.generateNoiseOctaves(this.field_147426_g, p_147423_1_, p_147423_3_, 5, 5, 200.0, 200.0, 0.5);
        this.field_147427_d = this.field_147429_l.generateNoiseOctaves(this.field_147427_d, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 8.555150000000001, 4.277575000000001, 8.555150000000001);
        this.field_147428_e = this.field_147431_j.generateNoiseOctaves(this.field_147428_e, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 684.412, 684.412, 684.412);
        this.field_147425_f = this.field_147432_k.generateNoiseOctaves(this.field_147425_f, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 684.412, 684.412, 684.412);
        final boolean var8 = false;
        final boolean var9 = false;
        int var10 = 0;
        int var11 = 0;
        final double var12 = 8.5;
        for (int var13 = 0; var13 < 5; ++var13) {
            for (int var14 = 0; var14 < 5; ++var14) {
                float var15 = 0.0f;
                float var16 = 0.0f;
                float var17 = 0.0f;
                final byte var18 = 2;
                final BiomeGenBase var19 = this.biomesForGeneration[var13 + 2 + (var14 + 2) * 10];
                for (int var20 = -var18; var20 <= var18; ++var20) {
                    for (int var21 = -var18; var21 <= var18; ++var21) {
                        final BiomeGenBase var22 = this.biomesForGeneration[var13 + var20 + 2 + (var14 + var21 + 2) * 10];
                        float var23 = var22.minHeight;
                        float var24 = var22.maxHeight;
                        if (this.field_147435_p == WorldType.field_151360_e && var23 > 0.0f) {
                            var23 = 1.0f + var23 * 2.0f;
                            var24 = 1.0f + var24 * 4.0f;
                        }
                        float var25 = this.parabolicField[var20 + 2 + (var21 + 2) * 5] / (var23 + 2.0f);
                        if (var22.minHeight > var19.minHeight) {
                            var25 /= 2.0f;
                        }
                        var15 += var24 * var25;
                        var16 += var23 * var25;
                        var17 += var25;
                    }
                }
                var15 /= var17;
                var16 /= var17;
                var15 = var15 * 0.9f + 0.1f;
                var16 = (var16 * 4.0f - 1.0f) / 8.0f;
                double var26 = this.field_147426_g[var11] / 8000.0;
                if (var26 < 0.0) {
                    var26 = -var26 * 0.3;
                }
                var26 = var26 * 3.0 - 2.0;
                if (var26 < 0.0) {
                    var26 /= 2.0;
                    if (var26 < -1.0) {
                        var26 = -1.0;
                    }
                    var26 /= 1.4;
                    var26 /= 2.0;
                }
                else {
                    if (var26 > 1.0) {
                        var26 = 1.0;
                    }
                    var26 /= 8.0;
                }
                ++var11;
                double var27 = var16;
                final double var28 = var15;
                var27 += var26 * 0.2;
                var27 = var27 * 8.5 / 8.0;
                final double var29 = 8.5 + var27 * 4.0;
                for (int var30 = 0; var30 < 33; ++var30) {
                    double var31 = (var30 - var29) * 12.0 * 128.0 / 256.0 / var28;
                    if (var31 < 0.0) {
                        var31 *= 4.0;
                    }
                    final double var32 = this.field_147428_e[var10] / 512.0;
                    final double var33 = this.field_147425_f[var10] / 512.0;
                    final double var34 = (this.field_147427_d[var10] / 10.0 + 1.0) / 2.0;
                    double var35 = MathHelper.denormalizeClamp(var32, var33, var34) - var31;
                    if (var30 > 29) {
                        final double var36 = (var30 - 29) / 3.0f;
                        var35 = var35 * (1.0 - var36) + -10.0 * var36;
                    }
                    this.field_147434_q[var10] = var35;
                    ++var10;
                }
            }
        }
    }
    
    @Override
    public boolean chunkExists(final int p_73149_1_, final int p_73149_2_) {
        return true;
    }
    
    @Override
    public void populate(final IChunkProvider p_73153_1_, final int p_73153_2_, final int p_73153_3_) {
        BlockFalling.field_149832_M = true;
        int var4 = p_73153_2_ * 16;
        int var5 = p_73153_3_ * 16;
        final BiomeGenBase var6 = this.worldObj.getBiomeGenForCoords(var4 + 16, var5 + 16);
        this.rand.setSeed(this.worldObj.getSeed());
        final long var7 = this.rand.nextLong() / 2L * 2L + 1L;
        final long var8 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed(p_73153_2_ * var7 + p_73153_3_ * var8 ^ this.worldObj.getSeed());
        boolean var9 = false;
        if (this.mapFeaturesEnabled) {
            this.mineshaftGenerator.generateStructuresInChunk(this.worldObj, this.rand, p_73153_2_, p_73153_3_);
            var9 = this.villageGenerator.generateStructuresInChunk(this.worldObj, this.rand, p_73153_2_, p_73153_3_);
            this.strongholdGenerator.generateStructuresInChunk(this.worldObj, this.rand, p_73153_2_, p_73153_3_);
            this.scatteredFeatureGenerator.generateStructuresInChunk(this.worldObj, this.rand, p_73153_2_, p_73153_3_);
        }
        if (var6 != BiomeGenBase.desert && var6 != BiomeGenBase.desertHills && !var9 && this.rand.nextInt(4) == 0) {
            final int var10 = var4 + this.rand.nextInt(16) + 8;
            final int var11 = this.rand.nextInt(256);
            final int var12 = var5 + this.rand.nextInt(16) + 8;
            new WorldGenLakes(Blocks.water).generate(this.worldObj, this.rand, var10, var11, var12);
        }
        if (!var9 && this.rand.nextInt(8) == 0) {
            final int var10 = var4 + this.rand.nextInt(16) + 8;
            final int var11 = this.rand.nextInt(this.rand.nextInt(248) + 8);
            final int var12 = var5 + this.rand.nextInt(16) + 8;
            if (var11 < 63 || this.rand.nextInt(10) == 0) {
                new WorldGenLakes(Blocks.lava).generate(this.worldObj, this.rand, var10, var11, var12);
            }
        }
        for (int var10 = 0; var10 < 8; ++var10) {
            final int var11 = var4 + this.rand.nextInt(16) + 8;
            final int var12 = this.rand.nextInt(256);
            final int var13 = var5 + this.rand.nextInt(16) + 8;
            new WorldGenDungeons().generate(this.worldObj, this.rand, var11, var12, var13);
        }
        var6.decorate(this.worldObj, this.rand, var4, var5);
        SpawnerAnimals.performWorldGenSpawning(this.worldObj, var6, var4 + 8, var5 + 8, 16, 16, this.rand);
        var4 += 8;
        var5 += 8;
        for (int var10 = 0; var10 < 16; ++var10) {
            for (int var11 = 0; var11 < 16; ++var11) {
                final int var12 = this.worldObj.getPrecipitationHeight(var4 + var10, var5 + var11);
                if (this.worldObj.isBlockFreezable(var10 + var4, var12 - 1, var11 + var5)) {
                    this.worldObj.setBlock(var10 + var4, var12 - 1, var11 + var5, Blocks.ice, 0, 2);
                }
                if (this.worldObj.func_147478_e(var10 + var4, var12, var11 + var5, true)) {
                    this.worldObj.setBlock(var10 + var4, var12, var11 + var5, Blocks.snow_layer, 0, 2);
                }
            }
        }
        BlockFalling.field_149832_M = false;
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
        return "RandomLevelSource";
    }
    
    @Override
    public List getPossibleCreatures(final EnumCreatureType p_73155_1_, final int p_73155_2_, final int p_73155_3_, final int p_73155_4_) {
        final BiomeGenBase var5 = this.worldObj.getBiomeGenForCoords(p_73155_2_, p_73155_4_);
        return (p_73155_1_ == EnumCreatureType.monster && this.scatteredFeatureGenerator.func_143030_a(p_73155_2_, p_73155_3_, p_73155_4_)) ? this.scatteredFeatureGenerator.getScatteredFeatureSpawnList() : var5.getSpawnableList(p_73155_1_);
    }
    
    @Override
    public ChunkPosition func_147416_a(final World p_147416_1_, final String p_147416_2_, final int p_147416_3_, final int p_147416_4_, final int p_147416_5_) {
        return ("Stronghold".equals(p_147416_2_) && this.strongholdGenerator != null) ? this.strongholdGenerator.func_151545_a(p_147416_1_, p_147416_3_, p_147416_4_, p_147416_5_) : null;
    }
    
    @Override
    public int getLoadedChunkCount() {
        return 0;
    }
    
    @Override
    public void recreateStructures(final int p_82695_1_, final int p_82695_2_) {
        if (this.mapFeaturesEnabled) {
            this.mineshaftGenerator.func_151539_a(this, this.worldObj, p_82695_1_, p_82695_2_, null);
            this.villageGenerator.func_151539_a(this, this.worldObj, p_82695_1_, p_82695_2_, null);
            this.strongholdGenerator.func_151539_a(this, this.worldObj, p_82695_1_, p_82695_2_, null);
            this.scatteredFeatureGenerator.func_151539_a(this, this.worldObj, p_82695_1_, p_82695_2_, null);
        }
    }
}
