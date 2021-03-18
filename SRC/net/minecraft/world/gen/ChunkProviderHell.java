package net.minecraft.world.gen;

import net.minecraft.world.gen.structure.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.biome.*;
import net.minecraft.block.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.world.*;

public class ChunkProviderHell implements IChunkProvider
{
    private Random hellRNG;
    private NoiseGeneratorOctaves netherNoiseGen1;
    private NoiseGeneratorOctaves netherNoiseGen2;
    private NoiseGeneratorOctaves netherNoiseGen3;
    private NoiseGeneratorOctaves slowsandGravelNoiseGen;
    private NoiseGeneratorOctaves netherrackExculsivityNoiseGen;
    public NoiseGeneratorOctaves netherNoiseGen6;
    public NoiseGeneratorOctaves netherNoiseGen7;
    private World worldObj;
    private double[] noiseField;
    public MapGenNetherBridge genNetherBridge;
    private double[] slowsandNoise;
    private double[] gravelNoise;
    private double[] netherrackExclusivityNoise;
    private MapGenBase netherCaveGenerator;
    double[] noiseData1;
    double[] noiseData2;
    double[] noiseData3;
    double[] noiseData4;
    double[] noiseData5;
    private static final String __OBFID = "CL_00000392";
    
    public ChunkProviderHell(final World p_i2005_1_, final long p_i2005_2_) {
        this.genNetherBridge = new MapGenNetherBridge();
        this.slowsandNoise = new double[256];
        this.gravelNoise = new double[256];
        this.netherrackExclusivityNoise = new double[256];
        this.netherCaveGenerator = new MapGenCavesHell();
        this.worldObj = p_i2005_1_;
        this.hellRNG = new Random(p_i2005_2_);
        this.netherNoiseGen1 = new NoiseGeneratorOctaves(this.hellRNG, 16);
        this.netherNoiseGen2 = new NoiseGeneratorOctaves(this.hellRNG, 16);
        this.netherNoiseGen3 = new NoiseGeneratorOctaves(this.hellRNG, 8);
        this.slowsandGravelNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
        this.netherrackExculsivityNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
        this.netherNoiseGen6 = new NoiseGeneratorOctaves(this.hellRNG, 10);
        this.netherNoiseGen7 = new NoiseGeneratorOctaves(this.hellRNG, 16);
    }
    
    public void func_147419_a(final int p_147419_1_, final int p_147419_2_, final Block[] p_147419_3_) {
        final byte var4 = 4;
        final byte var5 = 32;
        final int var6 = var4 + 1;
        final byte var7 = 17;
        final int var8 = var4 + 1;
        this.noiseField = this.initializeNoiseField(this.noiseField, p_147419_1_ * var4, 0, p_147419_2_ * var4, var6, var7, var8);
        for (int var9 = 0; var9 < var4; ++var9) {
            for (int var10 = 0; var10 < var4; ++var10) {
                for (int var11 = 0; var11 < 16; ++var11) {
                    final double var12 = 0.125;
                    double var13 = this.noiseField[((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 0];
                    double var14 = this.noiseField[((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 0];
                    double var15 = this.noiseField[((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 0];
                    double var16 = this.noiseField[((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 0];
                    final double var17 = (this.noiseField[((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 1] - var13) * var12;
                    final double var18 = (this.noiseField[((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 1] - var14) * var12;
                    final double var19 = (this.noiseField[((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 1] - var15) * var12;
                    final double var20 = (this.noiseField[((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 1] - var16) * var12;
                    for (int var21 = 0; var21 < 8; ++var21) {
                        final double var22 = 0.25;
                        double var23 = var13;
                        double var24 = var14;
                        final double var25 = (var15 - var13) * var22;
                        final double var26 = (var16 - var14) * var22;
                        for (int var27 = 0; var27 < 4; ++var27) {
                            int var28 = var27 + var9 * 4 << 11 | 0 + var10 * 4 << 7 | var11 * 8 + var21;
                            final short var29 = 128;
                            final double var30 = 0.25;
                            double var31 = var23;
                            final double var32 = (var24 - var23) * var30;
                            for (int var33 = 0; var33 < 4; ++var33) {
                                Block var34 = null;
                                if (var11 * 8 + var21 < var5) {
                                    var34 = Blocks.lava;
                                }
                                if (var31 > 0.0) {
                                    var34 = Blocks.netherrack;
                                }
                                p_147419_3_[var28] = var34;
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
    
    public void func_147418_b(final int p_147418_1_, final int p_147418_2_, final Block[] p_147418_3_) {
        final byte var4 = 64;
        final double var5 = 0.03125;
        this.slowsandNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.slowsandNoise, p_147418_1_ * 16, p_147418_2_ * 16, 0, 16, 16, 1, var5, var5, 1.0);
        this.gravelNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.gravelNoise, p_147418_1_ * 16, 109, p_147418_2_ * 16, 16, 1, 16, var5, 1.0, var5);
        this.netherrackExclusivityNoise = this.netherrackExculsivityNoiseGen.generateNoiseOctaves(this.netherrackExclusivityNoise, p_147418_1_ * 16, p_147418_2_ * 16, 0, 16, 16, 1, var5 * 2.0, var5 * 2.0, var5 * 2.0);
        for (int var6 = 0; var6 < 16; ++var6) {
            for (int var7 = 0; var7 < 16; ++var7) {
                final boolean var8 = this.slowsandNoise[var6 + var7 * 16] + this.hellRNG.nextDouble() * 0.2 > 0.0;
                final boolean var9 = this.gravelNoise[var6 + var7 * 16] + this.hellRNG.nextDouble() * 0.2 > 0.0;
                final int var10 = (int)(this.netherrackExclusivityNoise[var6 + var7 * 16] / 3.0 + 3.0 + this.hellRNG.nextDouble() * 0.25);
                int var11 = -1;
                Block var12 = Blocks.netherrack;
                Block var13 = Blocks.netherrack;
                for (int var14 = 127; var14 >= 0; --var14) {
                    final int var15 = (var7 * 16 + var6) * 128 + var14;
                    if (var14 < 127 - this.hellRNG.nextInt(5) && var14 > 0 + this.hellRNG.nextInt(5)) {
                        final Block var16 = p_147418_3_[var15];
                        if (var16 != null && var16.getMaterial() != Material.air) {
                            if (var16 == Blocks.netherrack) {
                                if (var11 == -1) {
                                    if (var10 <= 0) {
                                        var12 = null;
                                        var13 = Blocks.netherrack;
                                    }
                                    else if (var14 >= var4 - 4 && var14 <= var4 + 1) {
                                        var12 = Blocks.netherrack;
                                        var13 = Blocks.netherrack;
                                        if (var9) {
                                            var12 = Blocks.gravel;
                                            var13 = Blocks.netherrack;
                                        }
                                        if (var8) {
                                            var12 = Blocks.soul_sand;
                                            var13 = Blocks.soul_sand;
                                        }
                                    }
                                    if (var14 < var4 && (var12 == null || var12.getMaterial() == Material.air)) {
                                        var12 = Blocks.lava;
                                    }
                                    var11 = var10;
                                    if (var14 >= var4 - 1) {
                                        p_147418_3_[var15] = var12;
                                    }
                                    else {
                                        p_147418_3_[var15] = var13;
                                    }
                                }
                                else if (var11 > 0) {
                                    --var11;
                                    p_147418_3_[var15] = var13;
                                }
                            }
                        }
                        else {
                            var11 = -1;
                        }
                    }
                    else {
                        p_147418_3_[var15] = Blocks.bedrock;
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
        this.hellRNG.setSeed(p_73154_1_ * 341873128712L + p_73154_2_ * 132897987541L);
        final Block[] var3 = new Block[32768];
        this.func_147419_a(p_73154_1_, p_73154_2_, var3);
        this.func_147418_b(p_73154_1_, p_73154_2_, var3);
        this.netherCaveGenerator.func_151539_a(this, this.worldObj, p_73154_1_, p_73154_2_, var3);
        this.genNetherBridge.func_151539_a(this, this.worldObj, p_73154_1_, p_73154_2_, var3);
        final Chunk var4 = new Chunk(this.worldObj, var3, p_73154_1_, p_73154_2_);
        final BiomeGenBase[] var5 = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(null, p_73154_1_ * 16, p_73154_2_ * 16, 16, 16);
        final byte[] var6 = var4.getBiomeArray();
        for (int var7 = 0; var7 < var6.length; ++var7) {
            var6[var7] = (byte)var5[var7].biomeID;
        }
        var4.resetRelightChecks();
        return var4;
    }
    
    private double[] initializeNoiseField(double[] p_73164_1_, final int p_73164_2_, final int p_73164_3_, final int p_73164_4_, final int p_73164_5_, final int p_73164_6_, final int p_73164_7_) {
        if (p_73164_1_ == null) {
            p_73164_1_ = new double[p_73164_5_ * p_73164_6_ * p_73164_7_];
        }
        final double var8 = 684.412;
        final double var9 = 2053.236;
        this.noiseData4 = this.netherNoiseGen6.generateNoiseOctaves(this.noiseData4, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, 1, p_73164_7_, 1.0, 0.0, 1.0);
        this.noiseData5 = this.netherNoiseGen7.generateNoiseOctaves(this.noiseData5, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, 1, p_73164_7_, 100.0, 0.0, 100.0);
        this.noiseData1 = this.netherNoiseGen3.generateNoiseOctaves(this.noiseData1, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, p_73164_6_, p_73164_7_, var8 / 80.0, var9 / 60.0, var8 / 80.0);
        this.noiseData2 = this.netherNoiseGen1.generateNoiseOctaves(this.noiseData2, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, p_73164_6_, p_73164_7_, var8, var9, var8);
        this.noiseData3 = this.netherNoiseGen2.generateNoiseOctaves(this.noiseData3, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, p_73164_6_, p_73164_7_, var8, var9, var8);
        int var10 = 0;
        int var11 = 0;
        final double[] var12 = new double[p_73164_6_];
        for (int var13 = 0; var13 < p_73164_6_; ++var13) {
            var12[var13] = Math.cos(var13 * 3.141592653589793 * 6.0 / p_73164_6_) * 2.0;
            double var14 = var13;
            if (var13 > p_73164_6_ / 2) {
                var14 = p_73164_6_ - 1 - var13;
            }
            if (var14 < 4.0) {
                var14 = 4.0 - var14;
                final double[] array = var12;
                final int n = var13;
                array[n] -= var14 * var14 * var14 * 10.0;
            }
        }
        for (int var13 = 0; var13 < p_73164_5_; ++var13) {
            for (int var15 = 0; var15 < p_73164_7_; ++var15) {
                double var16 = (this.noiseData4[var11] + 256.0) / 512.0;
                if (var16 > 1.0) {
                    var16 = 1.0;
                }
                final double var17 = 0.0;
                double var18 = this.noiseData5[var11] / 8000.0;
                if (var18 < 0.0) {
                    var18 = -var18;
                }
                var18 = var18 * 3.0 - 3.0;
                if (var18 < 0.0) {
                    var18 /= 2.0;
                    if (var18 < -1.0) {
                        var18 = -1.0;
                    }
                    var18 /= 1.4;
                    var18 /= 2.0;
                    var16 = 0.0;
                }
                else {
                    if (var18 > 1.0) {
                        var18 = 1.0;
                    }
                    var18 /= 6.0;
                }
                var16 += 0.5;
                var18 = var18 * p_73164_6_ / 16.0;
                ++var11;
                for (int var19 = 0; var19 < p_73164_6_; ++var19) {
                    double var20 = 0.0;
                    final double var21 = var12[var19];
                    final double var22 = this.noiseData2[var10] / 512.0;
                    final double var23 = this.noiseData3[var10] / 512.0;
                    final double var24 = (this.noiseData1[var10] / 10.0 + 1.0) / 2.0;
                    if (var24 < 0.0) {
                        var20 = var22;
                    }
                    else if (var24 > 1.0) {
                        var20 = var23;
                    }
                    else {
                        var20 = var22 + (var23 - var22) * var24;
                    }
                    var20 -= var21;
                    if (var19 > p_73164_6_ - 4) {
                        final double var25 = (var19 - (p_73164_6_ - 4)) / 3.0f;
                        var20 = var20 * (1.0 - var25) + -10.0 * var25;
                    }
                    if (var19 < var17) {
                        double var25 = (var17 - var19) / 4.0;
                        if (var25 < 0.0) {
                            var25 = 0.0;
                        }
                        if (var25 > 1.0) {
                            var25 = 1.0;
                        }
                        var20 = var20 * (1.0 - var25) + -10.0 * var25;
                    }
                    p_73164_1_[var10] = var20;
                    ++var10;
                }
            }
        }
        return p_73164_1_;
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
        this.genNetherBridge.generateStructuresInChunk(this.worldObj, this.hellRNG, p_73153_2_, p_73153_3_);
        for (int var6 = 0; var6 < 8; ++var6) {
            final int var7 = var4 + this.hellRNG.nextInt(16) + 8;
            final int var8 = this.hellRNG.nextInt(120) + 4;
            final int var9 = var5 + this.hellRNG.nextInt(16) + 8;
            new WorldGenHellLava(Blocks.flowing_lava, false).generate(this.worldObj, this.hellRNG, var7, var8, var9);
        }
        int var6;
        for (var6 = this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1) + 1, int var7 = 0; var7 < var6; ++var7) {
            final int var8 = var4 + this.hellRNG.nextInt(16) + 8;
            final int var9 = this.hellRNG.nextInt(120) + 4;
            final int var10 = var5 + this.hellRNG.nextInt(16) + 8;
            new WorldGenFire().generate(this.worldObj, this.hellRNG, var8, var9, var10);
        }
        for (var6 = this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1), int var7 = 0; var7 < var6; ++var7) {
            final int var8 = var4 + this.hellRNG.nextInt(16) + 8;
            final int var9 = this.hellRNG.nextInt(120) + 4;
            final int var10 = var5 + this.hellRNG.nextInt(16) + 8;
            new WorldGenGlowStone1().generate(this.worldObj, this.hellRNG, var8, var9, var10);
        }
        for (int var7 = 0; var7 < 10; ++var7) {
            final int var8 = var4 + this.hellRNG.nextInt(16) + 8;
            final int var9 = this.hellRNG.nextInt(128);
            final int var10 = var5 + this.hellRNG.nextInt(16) + 8;
            new WorldGenGlowStone2().generate(this.worldObj, this.hellRNG, var8, var9, var10);
        }
        if (this.hellRNG.nextInt(1) == 0) {
            final int var7 = var4 + this.hellRNG.nextInt(16) + 8;
            final int var8 = this.hellRNG.nextInt(128);
            final int var9 = var5 + this.hellRNG.nextInt(16) + 8;
            new WorldGenFlowers(Blocks.brown_mushroom).generate(this.worldObj, this.hellRNG, var7, var8, var9);
        }
        if (this.hellRNG.nextInt(1) == 0) {
            final int var7 = var4 + this.hellRNG.nextInt(16) + 8;
            final int var8 = this.hellRNG.nextInt(128);
            final int var9 = var5 + this.hellRNG.nextInt(16) + 8;
            new WorldGenFlowers(Blocks.red_mushroom).generate(this.worldObj, this.hellRNG, var7, var8, var9);
        }
        final WorldGenMinable var11 = new WorldGenMinable(Blocks.quartz_ore, 13, Blocks.netherrack);
        for (int var8 = 0; var8 < 16; ++var8) {
            final int var9 = var4 + this.hellRNG.nextInt(16);
            final int var10 = this.hellRNG.nextInt(108) + 10;
            final int var12 = var5 + this.hellRNG.nextInt(16);
            var11.generate(this.worldObj, this.hellRNG, var9, var10, var12);
        }
        for (int var8 = 0; var8 < 16; ++var8) {
            final int var9 = var4 + this.hellRNG.nextInt(16);
            final int var10 = this.hellRNG.nextInt(108) + 10;
            final int var12 = var5 + this.hellRNG.nextInt(16);
            new WorldGenHellLava(Blocks.flowing_lava, true).generate(this.worldObj, this.hellRNG, var9, var10, var12);
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
        return "HellRandomLevelSource";
    }
    
    @Override
    public List getPossibleCreatures(final EnumCreatureType p_73155_1_, final int p_73155_2_, final int p_73155_3_, final int p_73155_4_) {
        if (p_73155_1_ == EnumCreatureType.monster) {
            if (this.genNetherBridge.hasStructureAt(p_73155_2_, p_73155_3_, p_73155_4_)) {
                return this.genNetherBridge.getSpawnList();
            }
            if (this.genNetherBridge.func_142038_b(p_73155_2_, p_73155_3_, p_73155_4_) && this.worldObj.getBlock(p_73155_2_, p_73155_3_ - 1, p_73155_4_) == Blocks.nether_brick) {
                return this.genNetherBridge.getSpawnList();
            }
        }
        final BiomeGenBase var5 = this.worldObj.getBiomeGenForCoords(p_73155_2_, p_73155_4_);
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
        this.genNetherBridge.func_151539_a(this, this.worldObj, p_82695_1_, p_82695_2_, null);
    }
}
