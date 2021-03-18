package net.minecraft.world.gen;

import net.minecraft.block.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.world.*;

public class MapGenCavesHell extends MapGenBase
{
    private static final String __OBFID = "CL_00000395";
    
    protected void func_151544_a(final long p_151544_1_, final int p_151544_3_, final int p_151544_4_, final Block[] p_151544_5_, final double p_151544_6_, final double p_151544_8_, final double p_151544_10_) {
        this.func_151543_a(p_151544_1_, p_151544_3_, p_151544_4_, p_151544_5_, p_151544_6_, p_151544_8_, p_151544_10_, 1.0f + this.rand.nextFloat() * 6.0f, 0.0f, 0.0f, -1, -1, 0.5);
    }
    
    protected void func_151543_a(final long p_151543_1_, final int p_151543_3_, final int p_151543_4_, final Block[] p_151543_5_, double p_151543_6_, double p_151543_8_, double p_151543_10_, final float p_151543_12_, float p_151543_13_, float p_151543_14_, int p_151543_15_, int p_151543_16_, final double p_151543_17_) {
        final double var19 = p_151543_3_ * 16 + 8;
        final double var20 = p_151543_4_ * 16 + 8;
        float var21 = 0.0f;
        float var22 = 0.0f;
        final Random var23 = new Random(p_151543_1_);
        if (p_151543_16_ <= 0) {
            final int var24 = this.range * 16 - 16;
            p_151543_16_ = var24 - var23.nextInt(var24 / 4);
        }
        boolean var25 = false;
        if (p_151543_15_ == -1) {
            p_151543_15_ = p_151543_16_ / 2;
            var25 = true;
        }
        final int var26 = var23.nextInt(p_151543_16_ / 2) + p_151543_16_ / 4;
        final boolean var27 = var23.nextInt(6) == 0;
        while (p_151543_15_ < p_151543_16_) {
            final double var28 = 1.5 + MathHelper.sin(p_151543_15_ * 3.1415927f / p_151543_16_) * p_151543_12_ * 1.0f;
            final double var29 = var28 * p_151543_17_;
            final float var30 = MathHelper.cos(p_151543_14_);
            final float var31 = MathHelper.sin(p_151543_14_);
            p_151543_6_ += MathHelper.cos(p_151543_13_) * var30;
            p_151543_8_ += var31;
            p_151543_10_ += MathHelper.sin(p_151543_13_) * var30;
            if (var27) {
                p_151543_14_ *= 0.92f;
            }
            else {
                p_151543_14_ *= 0.7f;
            }
            p_151543_14_ += var22 * 0.1f;
            p_151543_13_ += var21 * 0.1f;
            var22 *= 0.9f;
            var21 *= 0.75f;
            var22 += (var23.nextFloat() - var23.nextFloat()) * var23.nextFloat() * 2.0f;
            var21 += (var23.nextFloat() - var23.nextFloat()) * var23.nextFloat() * 4.0f;
            if (!var25 && p_151543_15_ == var26 && p_151543_12_ > 1.0f) {
                this.func_151543_a(var23.nextLong(), p_151543_3_, p_151543_4_, p_151543_5_, p_151543_6_, p_151543_8_, p_151543_10_, var23.nextFloat() * 0.5f + 0.5f, p_151543_13_ - 1.5707964f, p_151543_14_ / 3.0f, p_151543_15_, p_151543_16_, 1.0);
                this.func_151543_a(var23.nextLong(), p_151543_3_, p_151543_4_, p_151543_5_, p_151543_6_, p_151543_8_, p_151543_10_, var23.nextFloat() * 0.5f + 0.5f, p_151543_13_ + 1.5707964f, p_151543_14_ / 3.0f, p_151543_15_, p_151543_16_, 1.0);
                return;
            }
            if (var25 || var23.nextInt(4) != 0) {
                final double var32 = p_151543_6_ - var19;
                final double var33 = p_151543_10_ - var20;
                final double var34 = p_151543_16_ - p_151543_15_;
                final double var35 = p_151543_12_ + 2.0f + 16.0f;
                if (var32 * var32 + var33 * var33 - var34 * var34 > var35 * var35) {
                    return;
                }
                if (p_151543_6_ >= var19 - 16.0 - var28 * 2.0 && p_151543_10_ >= var20 - 16.0 - var28 * 2.0 && p_151543_6_ <= var19 + 16.0 + var28 * 2.0 && p_151543_10_ <= var20 + 16.0 + var28 * 2.0) {
                    int var36 = MathHelper.floor_double(p_151543_6_ - var28) - p_151543_3_ * 16 - 1;
                    int var37 = MathHelper.floor_double(p_151543_6_ + var28) - p_151543_3_ * 16 + 1;
                    int var38 = MathHelper.floor_double(p_151543_8_ - var29) - 1;
                    int var39 = MathHelper.floor_double(p_151543_8_ + var29) + 1;
                    int var40 = MathHelper.floor_double(p_151543_10_ - var28) - p_151543_4_ * 16 - 1;
                    int var41 = MathHelper.floor_double(p_151543_10_ + var28) - p_151543_4_ * 16 + 1;
                    if (var36 < 0) {
                        var36 = 0;
                    }
                    if (var37 > 16) {
                        var37 = 16;
                    }
                    if (var38 < 1) {
                        var38 = 1;
                    }
                    if (var39 > 120) {
                        var39 = 120;
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
                                final int var46 = (var43 * 16 + var44) * 128 + var45;
                                if (var45 >= 0 && var45 < 128) {
                                    final Block var47 = p_151543_5_[var46];
                                    if (var47 == Blocks.flowing_lava || var47 == Blocks.lava) {
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
                            final double var48 = (var43 + p_151543_3_ * 16 + 0.5 - p_151543_6_) / var28;
                            for (int var46 = var40; var46 < var41; ++var46) {
                                final double var49 = (var46 + p_151543_4_ * 16 + 0.5 - p_151543_10_) / var28;
                                int var50 = (var43 * 16 + var46) * 128 + var39;
                                for (int var51 = var39 - 1; var51 >= var38; --var51) {
                                    final double var52 = (var51 + 0.5 - p_151543_8_) / var29;
                                    if (var52 > -0.7 && var48 * var48 + var52 * var52 + var49 * var49 < 1.0) {
                                        final Block var53 = p_151543_5_[var50];
                                        if (var53 == Blocks.netherrack || var53 == Blocks.dirt || var53 == Blocks.grass) {
                                            p_151543_5_[var50] = null;
                                        }
                                    }
                                    --var50;
                                }
                            }
                        }
                        if (var25) {
                            break;
                        }
                    }
                }
            }
            ++p_151543_15_;
        }
    }
    
    @Override
    protected void func_151538_a(final World p_151538_1_, final int p_151538_2_, final int p_151538_3_, final int p_151538_4_, final int p_151538_5_, final Block[] p_151538_6_) {
        int var7 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(10) + 1) + 1);
        if (this.rand.nextInt(5) != 0) {
            var7 = 0;
        }
        for (int var8 = 0; var8 < var7; ++var8) {
            final double var9 = p_151538_2_ * 16 + this.rand.nextInt(16);
            final double var10 = this.rand.nextInt(128);
            final double var11 = p_151538_3_ * 16 + this.rand.nextInt(16);
            int var12 = 1;
            if (this.rand.nextInt(4) == 0) {
                this.func_151544_a(this.rand.nextLong(), p_151538_4_, p_151538_5_, p_151538_6_, var9, var10, var11);
                var12 += this.rand.nextInt(4);
            }
            for (int var13 = 0; var13 < var12; ++var13) {
                final float var14 = this.rand.nextFloat() * 3.1415927f * 2.0f;
                final float var15 = (this.rand.nextFloat() - 0.5f) * 2.0f / 8.0f;
                final float var16 = this.rand.nextFloat() * 2.0f + this.rand.nextFloat();
                this.func_151543_a(this.rand.nextLong(), p_151538_4_, p_151538_5_, p_151538_6_, var9, var10, var11, var16 * 2.0f, var14, var15, 0, 0, 0.5);
            }
        }
    }
}
