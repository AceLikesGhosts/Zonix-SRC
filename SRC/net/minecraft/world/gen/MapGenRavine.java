package net.minecraft.world.gen;

import net.minecraft.block.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.world.*;

public class MapGenRavine extends MapGenBase
{
    private float[] field_75046_d;
    private static final String __OBFID = "CL_00000390";
    
    public MapGenRavine() {
        this.field_75046_d = new float[1024];
    }
    
    protected void func_151540_a(final long p_151540_1_, final int p_151540_3_, final int p_151540_4_, final Block[] p_151540_5_, double p_151540_6_, double p_151540_8_, double p_151540_10_, final float p_151540_12_, float p_151540_13_, float p_151540_14_, int p_151540_15_, int p_151540_16_, final double p_151540_17_) {
        final Random var19 = new Random(p_151540_1_);
        final double var20 = p_151540_3_ * 16 + 8;
        final double var21 = p_151540_4_ * 16 + 8;
        float var22 = 0.0f;
        float var23 = 0.0f;
        if (p_151540_16_ <= 0) {
            final int var24 = this.range * 16 - 16;
            p_151540_16_ = var24 - var19.nextInt(var24 / 4);
        }
        boolean var25 = false;
        if (p_151540_15_ == -1) {
            p_151540_15_ = p_151540_16_ / 2;
            var25 = true;
        }
        float var26 = 1.0f;
        for (int var27 = 0; var27 < 256; ++var27) {
            if (var27 == 0 || var19.nextInt(3) == 0) {
                var26 = 1.0f + var19.nextFloat() * var19.nextFloat() * 1.0f;
            }
            this.field_75046_d[var27] = var26 * var26;
        }
        while (p_151540_15_ < p_151540_16_) {
            double var28 = 1.5 + MathHelper.sin(p_151540_15_ * 3.1415927f / p_151540_16_) * p_151540_12_ * 1.0f;
            double var29 = var28 * p_151540_17_;
            var28 *= var19.nextFloat() * 0.25 + 0.75;
            var29 *= var19.nextFloat() * 0.25 + 0.75;
            final float var30 = MathHelper.cos(p_151540_14_);
            final float var31 = MathHelper.sin(p_151540_14_);
            p_151540_6_ += MathHelper.cos(p_151540_13_) * var30;
            p_151540_8_ += var31;
            p_151540_10_ += MathHelper.sin(p_151540_13_) * var30;
            p_151540_14_ *= 0.7f;
            p_151540_14_ += var23 * 0.05f;
            p_151540_13_ += var22 * 0.05f;
            var23 *= 0.8f;
            var22 *= 0.5f;
            var23 += (var19.nextFloat() - var19.nextFloat()) * var19.nextFloat() * 2.0f;
            var22 += (var19.nextFloat() - var19.nextFloat()) * var19.nextFloat() * 4.0f;
            if (var25 || var19.nextInt(4) != 0) {
                final double var32 = p_151540_6_ - var20;
                final double var33 = p_151540_10_ - var21;
                final double var34 = p_151540_16_ - p_151540_15_;
                final double var35 = p_151540_12_ + 2.0f + 16.0f;
                if (var32 * var32 + var33 * var33 - var34 * var34 > var35 * var35) {
                    return;
                }
                if (p_151540_6_ >= var20 - 16.0 - var28 * 2.0 && p_151540_10_ >= var21 - 16.0 - var28 * 2.0 && p_151540_6_ <= var20 + 16.0 + var28 * 2.0 && p_151540_10_ <= var21 + 16.0 + var28 * 2.0) {
                    int var36 = MathHelper.floor_double(p_151540_6_ - var28) - p_151540_3_ * 16 - 1;
                    int var37 = MathHelper.floor_double(p_151540_6_ + var28) - p_151540_3_ * 16 + 1;
                    int var38 = MathHelper.floor_double(p_151540_8_ - var29) - 1;
                    int var39 = MathHelper.floor_double(p_151540_8_ + var29) + 1;
                    int var40 = MathHelper.floor_double(p_151540_10_ - var28) - p_151540_4_ * 16 - 1;
                    int var41 = MathHelper.floor_double(p_151540_10_ + var28) - p_151540_4_ * 16 + 1;
                    if (var36 < 0) {
                        var36 = 0;
                    }
                    if (var37 > 16) {
                        var37 = 16;
                    }
                    if (var38 < 1) {
                        var38 = 1;
                    }
                    if (var39 > 248) {
                        var39 = 248;
                    }
                    if (var40 < 0) {
                        var40 = 0;
                    }
                    if (var41 > 16) {
                        var41 = 16;
                    }
                    boolean var42 = false;
                    for (int var43 = var36; !var42 && var43 < var37; ++var43) {
                        for (int var44 = var40; !var42 && var44 < var41; ++var44) {
                            for (int var45 = var39 + 1; !var42 && var45 >= var38 - 1; --var45) {
                                final int var46 = (var43 * 16 + var44) * 256 + var45;
                                if (var45 >= 0 && var45 < 256) {
                                    final Block var47 = p_151540_5_[var46];
                                    if (var47 == Blocks.flowing_water || var47 == Blocks.water) {
                                        var42 = true;
                                    }
                                    if (var45 != var38 - 1 && var43 != var36 && var43 != var37 - 1 && var44 != var40 && var44 != var41 - 1) {
                                        var45 = var38;
                                    }
                                }
                            }
                        }
                    }
                    if (!var42) {
                        for (int var43 = var36; var43 < var37; ++var43) {
                            final double var48 = (var43 + p_151540_3_ * 16 + 0.5 - p_151540_6_) / var28;
                            for (int var46 = var40; var46 < var41; ++var46) {
                                final double var49 = (var46 + p_151540_4_ * 16 + 0.5 - p_151540_10_) / var28;
                                int var50 = (var43 * 16 + var46) * 256 + var39;
                                boolean var51 = false;
                                if (var48 * var48 + var49 * var49 < 1.0) {
                                    for (int var52 = var39 - 1; var52 >= var38; --var52) {
                                        final double var53 = (var52 + 0.5 - p_151540_8_) / var29;
                                        if ((var48 * var48 + var49 * var49) * this.field_75046_d[var52] + var53 * var53 / 6.0 < 1.0) {
                                            final Block var54 = p_151540_5_[var50];
                                            if (var54 == Blocks.grass) {
                                                var51 = true;
                                            }
                                            if (var54 == Blocks.stone || var54 == Blocks.dirt || var54 == Blocks.grass) {
                                                if (var52 < 10) {
                                                    p_151540_5_[var50] = Blocks.flowing_lava;
                                                }
                                                else {
                                                    p_151540_5_[var50] = null;
                                                    if (var51 && p_151540_5_[var50 - 1] == Blocks.dirt) {
                                                        p_151540_5_[var50 - 1] = this.worldObj.getBiomeGenForCoords(var43 + p_151540_3_ * 16, var46 + p_151540_4_ * 16).topBlock;
                                                    }
                                                }
                                            }
                                        }
                                        --var50;
                                    }
                                }
                            }
                        }
                        if (var25) {
                            break;
                        }
                    }
                }
            }
            ++p_151540_15_;
        }
    }
    
    @Override
    protected void func_151538_a(final World p_151538_1_, final int p_151538_2_, final int p_151538_3_, final int p_151538_4_, final int p_151538_5_, final Block[] p_151538_6_) {
        if (this.rand.nextInt(50) == 0) {
            final double var7 = p_151538_2_ * 16 + this.rand.nextInt(16);
            final double var8 = this.rand.nextInt(this.rand.nextInt(40) + 8) + 20;
            final double var9 = p_151538_3_ * 16 + this.rand.nextInt(16);
            final byte var10 = 1;
            for (int var11 = 0; var11 < var10; ++var11) {
                final float var12 = this.rand.nextFloat() * 3.1415927f * 2.0f;
                final float var13 = (this.rand.nextFloat() - 0.5f) * 2.0f / 8.0f;
                final float var14 = (this.rand.nextFloat() * 2.0f + this.rand.nextFloat()) * 2.0f;
                this.func_151540_a(this.rand.nextLong(), p_151538_4_, p_151538_5_, p_151538_6_, var7, var8, var9, var14, var12, var13, 0, 0, 3.0);
            }
        }
    }
}
