package net.minecraft.world.gen;

import net.minecraft.world.biome.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.world.chunk.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.world.*;

public class ChunkProviderEnd implements IChunkProvider
{
    private Random endRNG;
    private NoiseGeneratorOctaves noiseGen1;
    private NoiseGeneratorOctaves noiseGen2;
    private NoiseGeneratorOctaves noiseGen3;
    public NoiseGeneratorOctaves noiseGen4;
    public NoiseGeneratorOctaves noiseGen5;
    private World endWorld;
    private double[] densities;
    private BiomeGenBase[] biomesForGeneration;
    double[] noiseData1;
    double[] noiseData2;
    double[] noiseData3;
    double[] noiseData4;
    double[] noiseData5;
    int[][] field_73203_h;
    private static final String __OBFID = "CL_00000397";
    
    public ChunkProviderEnd(final World p_i2007_1_, final long p_i2007_2_) {
        this.field_73203_h = new int[32][32];
        this.endWorld = p_i2007_1_;
        this.endRNG = new Random(p_i2007_2_);
        this.noiseGen1 = new NoiseGeneratorOctaves(this.endRNG, 16);
        this.noiseGen2 = new NoiseGeneratorOctaves(this.endRNG, 16);
        this.noiseGen3 = new NoiseGeneratorOctaves(this.endRNG, 8);
        this.noiseGen4 = new NoiseGeneratorOctaves(this.endRNG, 10);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.endRNG, 16);
    }
    
    public void func_147420_a(final int p_147420_1_, final int p_147420_2_, final Block[] p_147420_3_, final BiomeGenBase[] p_147420_4_) {
        final byte var5 = 2;
        final int var6 = var5 + 1;
        final byte var7 = 33;
        final int var8 = var5 + 1;
        this.densities = this.initializeNoiseField(this.densities, p_147420_1_ * var5, 0, p_147420_2_ * var5, var6, var7, var8);
        for (int var9 = 0; var9 < var5; ++var9) {
            for (int var10 = 0; var10 < var5; ++var10) {
                for (int var11 = 0; var11 < 32; ++var11) {
                    final double var12 = 0.25;
                    double var13 = this.densities[((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 0];
                    double var14 = this.densities[((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 0];
                    double var15 = this.densities[((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 0];
                    double var16 = this.densities[((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 0];
                    final double var17 = (this.densities[((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 1] - var13) * var12;
                    final double var18 = (this.densities[((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 1] - var14) * var12;
                    final double var19 = (this.densities[((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 1] - var15) * var12;
                    final double var20 = (this.densities[((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 1] - var16) * var12;
                    for (int var21 = 0; var21 < 4; ++var21) {
                        final double var22 = 0.125;
                        double var23 = var13;
                        double var24 = var14;
                        final double var25 = (var15 - var13) * var22;
                        final double var26 = (var16 - var14) * var22;
                        for (int var27 = 0; var27 < 8; ++var27) {
                            int var28 = var27 + var9 * 8 << 11 | 0 + var10 * 8 << 7 | var11 * 4 + var21;
                            final short var29 = 128;
                            final double var30 = 0.125;
                            double var31 = var23;
                            final double var32 = (var24 - var23) * var30;
                            for (int var33 = 0; var33 < 8; ++var33) {
                                Block var34 = null;
                                if (var31 > 0.0) {
                                    var34 = Blocks.end_stone;
                                }
                                p_147420_3_[var28] = var34;
                                var28 += var29;
                                var31 += var32;
                            }
                            var23 += var25;
                            var24 += var26;
                        }
                        var13 += var17;
                        var14 += var18;
                        var15 += var19;
                        var16 += var20;
                    }
                }
            }
        }
    }
    
    public void func_147421_b(final int p_147421_1_, final int p_147421_2_, final Block[] p_147421_3_, final BiomeGenBase[] p_147421_4_) {
        for (int var5 = 0; var5 < 16; ++var5) {
            for (int var6 = 0; var6 < 16; ++var6) {
                final byte var7 = 1;
                int var8 = -1;
                Block var9 = Blocks.end_stone;
                Block var10 = Blocks.end_stone;
                for (int var11 = 127; var11 >= 0; --var11) {
                    final int var12 = (var6 * 16 + var5) * 128 + var11;
                    final Block var13 = p_147421_3_[var12];
                    if (var13 != null && var13.getMaterial() != Material.air) {
                        if (var13 == Blocks.stone) {
                            if (var8 == -1) {
                                if (var7 <= 0) {
                                    var9 = null;
                                    var10 = Blocks.end_stone;
                                }
                                var8 = var7;
                                if (var11 >= 0) {
                                    p_147421_3_[var12] = var9;
                                }
                                else {
                                    p_147421_3_[var12] = var10;
                                }
                            }
                            else if (var8 > 0) {
                                --var8;
                                p_147421_3_[var12] = var10;
                            }
                        }
                    }
                    else {
                        var8 = -1;
                    }
                }
            }
        }
    }
    
    @Override
    public Chunk loadChunk(final int p_73158_1_, final int p_73158_2_) {
        return this.provideChunk(p_73158_1_, p_73158_2_);
    }
    
    @Override
    public Chunk provideChunk(final int p_73154_1_, final int p_73154_2_) {
        this.endRNG.setSeed(p_73154_1_ * 341873128712L + p_73154_2_ * 132897987541L);
        final Block[] var3 = new Block[32768];
        this.func_147420_a(p_73154_1_, p_73154_2_, var3, this.biomesForGeneration = this.endWorld.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, p_73154_1_ * 16, p_73154_2_ * 16, 16, 16));
        this.func_147421_b(p_73154_1_, p_73154_2_, var3, this.biomesForGeneration);
        final Chunk var4 = new Chunk(this.endWorld, var3, p_73154_1_, p_73154_2_);
        final byte[] var5 = var4.getBiomeArray();
        for (int var6 = 0; var6 < var5.length; ++var6) {
            var5[var6] = (byte)this.biomesForGeneration[var6].biomeID;
        }
        var4.generateSkylightMap();
        return var4;
    }
    
    private double[] initializeNoiseField(double[] p_73187_1_, final int p_73187_2_, final int p_73187_3_, final int p_73187_4_, final int p_73187_5_, final int p_73187_6_, final int p_73187_7_) {
        if (p_73187_1_ == null) {
            p_73187_1_ = new double[p_73187_5_ * p_73187_6_ * p_73187_7_];
        }
        double var8 = 684.412;
        final double var9 = 684.412;
        this.noiseData4 = this.noiseGen4.generateNoiseOctaves(this.noiseData4, p_73187_2_, p_73187_4_, p_73187_5_, p_73187_7_, 1.121, 1.121, 0.5);
        this.noiseData5 = this.noiseGen5.generateNoiseOctaves(this.noiseData5, p_73187_2_, p_73187_4_, p_73187_5_, p_73187_7_, 200.0, 200.0, 0.5);
        var8 *= 2.0;
        this.noiseData1 = this.noiseGen3.generateNoiseOctaves(this.noiseData1, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_, var8 / 80.0, var9 / 160.0, var8 / 80.0);
        this.noiseData2 = this.noiseGen1.generateNoiseOctaves(this.noiseData2, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_, var8, var9, var8);
        this.noiseData3 = this.noiseGen2.generateNoiseOctaves(this.noiseData3, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_, var8, var9, var8);
        int var10 = 0;
        int var11 = 0;
        for (int var12 = 0; var12 < p_73187_5_; ++var12) {
            for (int var13 = 0; var13 < p_73187_7_; ++var13) {
                double var14 = (this.noiseData4[var11] + 256.0) / 512.0;
                if (var14 > 1.0) {
                    var14 = 1.0;
                }
                double var15 = this.noiseData5[var11] / 8000.0;
                if (var15 < 0.0) {
                    var15 = -var15 * 0.3;
                }
                var15 = var15 * 3.0 - 2.0;
                final float var16 = (var12 + p_73187_2_ - 0) / 1.0f;
                final float var17 = (var13 + p_73187_4_ - 0) / 1.0f;
                float var18 = 100.0f - MathHelper.sqrt_float(var16 * var16 + var17 * var17) * 8.0f;
                if (var18 > 80.0f) {
                    var18 = 80.0f;
                }
                if (var18 < -100.0f) {
                    var18 = -100.0f;
                }
                if (var15 > 1.0) {
                    var15 = 1.0;
                }
                var15 /= 8.0;
                var15 = 0.0;
                if (var14 < 0.0) {
                    var14 = 0.0;
                }
                var14 += 0.5;
                var15 = var15 * p_73187_6_ / 16.0;
                ++var11;
                final double var19 = p_73187_6_ / 2.0;
                for (int var20 = 0; var20 < p_73187_6_; ++var20) {
                    double var21 = 0.0;
                    double var22 = (var20 - var19) * 8.0 / var14;
                    if (var22 < 0.0) {
                        var22 *= -1.0;
                    }
                    final double var23 = this.noiseData2[var10] / 512.0;
                    final double var24 = this.noiseData3[var10] / 512.0;
                    final double var25 = (this.noiseData1[var10] / 10.0 + 1.0) / 2.0;
                    if (var25 < 0.0) {
                        var21 = var23;
                    }
                    else if (var25 > 1.0) {
                        var21 = var24;
                    }
                    else {
                        var21 = var23 + (var24 - var23) * var25;
                    }
                    var21 -= 8.0;
                    var21 += var18;
                    byte var26 = 2;
                    if (var20 > p_73187_6_ / 2 - var26) {
                        double var27 = (var20 - (p_73187_6_ / 2 - var26)) / 64.0f;
                        if (var27 < 0.0) {
                            var27 = 0.0;
                        }
                        if (var27 > 1.0) {
                            var27 = 1.0;
                        }
                        var21 = var21 * (1.0 - var27) + -3000.0 * var27;
                    }
                    var26 = 8;
                    if (var20 < var26) {
                        final double var27 = (var26 - var20) / (var26 - 1.0f);
                        var21 = var21 * (1.0 - var27) + -30.0 * var27;
                    }
                    p_73187_1_[var10] = var21;
                    ++var10;
                }
            }
        }
        return p_73187_1_;
    }
    
    @Override
    public boolean chunkExists(final int p_73149_1_, final int p_73149_2_) {
        return true;
    }
    
    @Override
    public void populate(final IChunkProvider p_73153_1_, final int p_73153_2_, final int p_73153_3_) {
        BlockFalling.field_149832_M = true;
        final int var4 = p_73153_2_ * 16;
        final int var5 = p_73153_3_ * 16;
        final BiomeGenBase var6 = this.endWorld.getBiomeGenForCoords(var4 + 16, var5 + 16);
        var6.decorate(this.endWorld, this.endWorld.rand, var4, var5);
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
        final BiomeGenBase var5 = this.endWorld.getBiomeGenForCoords(p_73155_2_, p_73155_4_);
        return var5.getSpawnableList(p_73155_1_);
    }
    
    @Override
    public ChunkPosition func_147416_a(final World p_147416_1_, final String p_147416_2_, final int p_147416_3_, final int p_147416_4_, final int p_147416_5_) {
        return null;
    }
    
    @Override
    public int getLoadedChunkCount() {
        return 0;
    }
    
    @Override
    public void recreateStructures(final int p_82695_1_, final int p_82695_2_) {
    }
}
