package net.minecraft.world.biome;

import net.minecraft.world.gen.*;
import net.minecraft.init.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import java.util.*;

public class BiomeGenMesa extends BiomeGenBase
{
    private byte[] field_150621_aC;
    private long field_150622_aD;
    private NoiseGeneratorPerlin field_150623_aE;
    private NoiseGeneratorPerlin field_150624_aF;
    private NoiseGeneratorPerlin field_150625_aG;
    private boolean field_150626_aH;
    private boolean field_150620_aI;
    private static final String __OBFID = "CL_00000176";
    
    public BiomeGenMesa(final int p_i45380_1_, final boolean p_i45380_2_, final boolean p_i45380_3_) {
        super(p_i45380_1_);
        this.field_150626_aH = p_i45380_2_;
        this.field_150620_aI = p_i45380_3_;
        this.setDisableRain();
        this.setTemperatureRainfall(2.0f, 0.0f);
        this.spawnableCreatureList.clear();
        this.topBlock = Blocks.sand;
        this.field_150604_aj = 1;
        this.fillerBlock = Blocks.stained_hardened_clay;
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.deadBushPerChunk = 20;
        this.theBiomeDecorator.reedsPerChunk = 3;
        this.theBiomeDecorator.cactiPerChunk = 5;
        this.theBiomeDecorator.flowersPerChunk = 0;
        this.spawnableCreatureList.clear();
        if (p_i45380_3_) {
            this.theBiomeDecorator.treesPerChunk = 5;
        }
    }
    
    @Override
    public WorldGenAbstractTree func_150567_a(final Random p_150567_1_) {
        return this.worldGeneratorTrees;
    }
    
    @Override
    public int getBiomeFoliageColor(final int p_150571_1_, final int p_150571_2_, final int p_150571_3_) {
        return 10387789;
    }
    
    @Override
    public int getBiomeGrassColor(final int p_150558_1_, final int p_150558_2_, final int p_150558_3_) {
        return 9470285;
    }
    
    @Override
    public void decorate(final World p_76728_1_, final Random p_76728_2_, final int p_76728_3_, final int p_76728_4_) {
        super.decorate(p_76728_1_, p_76728_2_, p_76728_3_, p_76728_4_);
    }
    
    @Override
    public void func_150573_a(final World p_150573_1_, final Random p_150573_2_, final Block[] p_150573_3_, final byte[] p_150573_4_, final int p_150573_5_, final int p_150573_6_, final double p_150573_7_) {
        if (this.field_150621_aC == null || this.field_150622_aD != p_150573_1_.getSeed()) {
            this.func_150619_a(p_150573_1_.getSeed());
        }
        if (this.field_150623_aE == null || this.field_150624_aF == null || this.field_150622_aD != p_150573_1_.getSeed()) {
            final Random var9 = new Random(this.field_150622_aD);
            this.field_150623_aE = new NoiseGeneratorPerlin(var9, 4);
            this.field_150624_aF = new NoiseGeneratorPerlin(var9, 1);
        }
        this.field_150622_aD = p_150573_1_.getSeed();
        double var10 = 0.0;
        if (this.field_150626_aH) {
            final int var11 = (p_150573_5_ & 0xFFFFFFF0) + (p_150573_6_ & 0xF);
            final int var12 = (p_150573_6_ & 0xFFFFFFF0) + (p_150573_5_ & 0xF);
            final double var13 = Math.min(Math.abs(p_150573_7_), this.field_150623_aE.func_151601_a(var11 * 0.25, var12 * 0.25));
            if (var13 > 0.0) {
                final double var14 = 0.001953125;
                final double var15 = Math.abs(this.field_150624_aF.func_151601_a(var11 * var14, var12 * var14));
                var10 = var13 * var13 * 2.5;
                final double var16 = Math.ceil(var15 * 50.0) + 14.0;
                if (var10 > var16) {
                    var10 = var16;
                }
                var10 += 64.0;
            }
        }
        final int var11 = p_150573_5_ & 0xF;
        final int var12 = p_150573_6_ & 0xF;
        final boolean var17 = true;
        Block var18 = Blocks.stained_hardened_clay;
        Block var19 = this.fillerBlock;
        final int var20 = (int)(p_150573_7_ / 3.0 + 3.0 + p_150573_2_.nextDouble() * 0.25);
        final boolean var21 = Math.cos(p_150573_7_ / 3.0 * 3.141592653589793) > 0.0;
        int var22 = -1;
        boolean var23 = false;
        final int var24 = p_150573_3_.length / 256;
        for (int var25 = 255; var25 >= 0; --var25) {
            final int var26 = (var12 * 16 + var11) * var24 + var25;
            if ((p_150573_3_[var26] == null || p_150573_3_[var26].getMaterial() == Material.air) && var25 < (int)var10) {
                p_150573_3_[var26] = Blocks.stone;
            }
            if (var25 <= 0 + p_150573_2_.nextInt(5)) {
                p_150573_3_[var26] = Blocks.bedrock;
            }
            else {
                final Block var27 = p_150573_3_[var26];
                if (var27 != null && var27.getMaterial() != Material.air) {
                    if (var27 == Blocks.stone) {
                        if (var22 == -1) {
                            var23 = false;
                            if (var20 <= 0) {
                                var18 = null;
                                var19 = Blocks.stone;
                            }
                            else if (var25 >= 59 && var25 <= 64) {
                                var18 = Blocks.stained_hardened_clay;
                                var19 = this.fillerBlock;
                            }
                            if (var25 < 63 && (var18 == null || var18.getMaterial() == Material.air)) {
                                var18 = Blocks.water;
                            }
                            var22 = var20 + Math.max(0, var25 - 63);
                            if (var25 >= 62) {
                                if (this.field_150620_aI && var25 > 86 + var20 * 2) {
                                    if (var21) {
                                        p_150573_3_[var26] = Blocks.dirt;
                                        p_150573_4_[var26] = 1;
                                    }
                                    else {
                                        p_150573_3_[var26] = Blocks.grass;
                                    }
                                }
                                else if (var25 > 66 + var20) {
                                    byte var28 = 16;
                                    if (var25 >= 64 && var25 <= 127) {
                                        if (!var21) {
                                            var28 = this.func_150618_d(p_150573_5_, var25, p_150573_6_);
                                        }
                                    }
                                    else {
                                        var28 = 1;
                                    }
                                    if (var28 < 16) {
                                        p_150573_3_[var26] = Blocks.stained_hardened_clay;
                                        p_150573_4_[var26] = var28;
                                    }
                                    else {
                                        p_150573_3_[var26] = Blocks.hardened_clay;
                                    }
                                }
                                else {
                                    p_150573_3_[var26] = this.topBlock;
                                    p_150573_4_[var26] = (byte)this.field_150604_aj;
                                    var23 = true;
                                }
                            }
                            else if ((p_150573_3_[var26] = var19) == Blocks.stained_hardened_clay) {
                                p_150573_4_[var26] = 1;
                            }
                        }
                        else if (var22 > 0) {
                            --var22;
                            if (var23) {
                                p_150573_3_[var26] = Blocks.stained_hardened_clay;
                                p_150573_4_[var26] = 1;
                            }
                            else {
                                final byte var28 = this.func_150618_d(p_150573_5_, var25, p_150573_6_);
                                if (var28 < 16) {
                                    p_150573_3_[var26] = Blocks.stained_hardened_clay;
                                    p_150573_4_[var26] = var28;
                                }
                                else {
                                    p_150573_3_[var26] = Blocks.hardened_clay;
                                }
                            }
                        }
                    }
                }
                else {
                    var22 = -1;
                }
            }
        }
    }
    
    private void func_150619_a(final long p_150619_1_) {
        Arrays.fill(this.field_150621_aC = new byte[64], (byte)16);
        final Random var3 = new Random(p_150619_1_);
        this.field_150625_aG = new NoiseGeneratorPerlin(var3, 1);
        for (int var4 = 0; var4 < 64; ++var4) {
            var4 += var3.nextInt(5) + 1;
            if (var4 < 64) {
                this.field_150621_aC[var4] = 1;
            }
        }
        for (int var4 = var3.nextInt(4) + 2, var5 = 0; var5 < var4; ++var5) {
            for (int var6 = var3.nextInt(3) + 1, var7 = var3.nextInt(64), var8 = 0; var7 + var8 < 64 && var8 < var6; ++var8) {
                this.field_150621_aC[var7 + var8] = 4;
            }
        }
        for (int var5 = var3.nextInt(4) + 2, var6 = 0; var6 < var5; ++var6) {
            for (int var7 = var3.nextInt(3) + 2, var8 = var3.nextInt(64), var9 = 0; var8 + var9 < 64 && var9 < var7; ++var9) {
                this.field_150621_aC[var8 + var9] = 12;
            }
        }
        for (int var6 = var3.nextInt(4) + 2, var7 = 0; var7 < var6; ++var7) {
            for (int var8 = var3.nextInt(3) + 1, var9 = var3.nextInt(64), var10 = 0; var9 + var10 < 64 && var10 < var8; ++var10) {
                this.field_150621_aC[var9 + var10] = 14;
            }
        }
        int var7 = var3.nextInt(3) + 3;
        int var8 = 0;
        for (int var9 = 0; var9 < var7; ++var9) {
            final byte var11 = 1;
            var8 += var3.nextInt(16) + 4;
            for (int var12 = 0; var8 + var12 < 64 && var12 < var11; ++var12) {
                this.field_150621_aC[var8 + var12] = 0;
                if (var8 + var12 > 1 && var3.nextBoolean()) {
                    this.field_150621_aC[var8 + var12 - 1] = 8;
                }
                if (var8 + var12 < 63 && var3.nextBoolean()) {
                    this.field_150621_aC[var8 + var12 + 1] = 8;
                }
            }
        }
    }
    
    private byte func_150618_d(final int p_150618_1_, final int p_150618_2_, final int p_150618_3_) {
        final int var4 = (int)Math.round(this.field_150625_aG.func_151601_a(p_150618_1_ * 1.0 / 512.0, p_150618_1_ * 1.0 / 512.0) * 2.0);
        return this.field_150621_aC[(p_150618_2_ + var4 + 64) % 64];
    }
    
    @Override
    protected BiomeGenBase func_150566_k() {
        final boolean var1 = this.biomeID == BiomeGenBase.field_150589_Z.biomeID;
        final BiomeGenMesa var2 = new BiomeGenMesa(this.biomeID + 128, var1, this.field_150620_aI);
        if (!var1) {
            var2.func_150570_a(BiomeGenMesa.field_150591_g);
            var2.setBiomeName(this.biomeName + " M");
        }
        else {
            var2.setBiomeName(this.biomeName + " (Bryce)");
        }
        var2.func_150557_a(this.color, true);
        return var2;
    }
}
