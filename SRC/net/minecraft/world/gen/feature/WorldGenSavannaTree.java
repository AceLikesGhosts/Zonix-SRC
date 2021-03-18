package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;

public class WorldGenSavannaTree extends WorldGenAbstractTree
{
    private static final String __OBFID = "CL_00000432";
    
    public WorldGenSavannaTree(final boolean p_i45463_1_) {
        super(p_i45463_1_);
    }
    
    @Override
    public boolean generate(final World p_76484_1_, final Random p_76484_2_, final int p_76484_3_, final int p_76484_4_, final int p_76484_5_) {
        final int var6 = p_76484_2_.nextInt(3) + p_76484_2_.nextInt(3) + 5;
        boolean var7 = true;
        if (p_76484_4_ < 1 || p_76484_4_ + var6 + 1 > 256) {
            return false;
        }
        for (int var8 = p_76484_4_; var8 <= p_76484_4_ + 1 + var6; ++var8) {
            byte var9 = 1;
            if (var8 == p_76484_4_) {
                var9 = 0;
            }
            if (var8 >= p_76484_4_ + 1 + var6 - 2) {
                var9 = 2;
            }
            for (int var10 = p_76484_3_ - var9; var10 <= p_76484_3_ + var9 && var7; ++var10) {
                for (int var11 = p_76484_5_ - var9; var11 <= p_76484_5_ + var9 && var7; ++var11) {
                    if (var8 >= 0 && var8 < 256) {
                        final Block var12 = p_76484_1_.getBlock(var10, var8, var11);
                        if (!this.func_150523_a(var12)) {
                            var7 = false;
                        }
                    }
                    else {
                        var7 = false;
                    }
                }
            }
        }
        if (!var7) {
            return false;
        }
        final Block var13 = p_76484_1_.getBlock(p_76484_3_, p_76484_4_ - 1, p_76484_5_);
        if ((var13 == Blocks.grass || var13 == Blocks.dirt) && p_76484_4_ < 256 - var6 - 1) {
            this.func_150515_a(p_76484_1_, p_76484_3_, p_76484_4_ - 1, p_76484_5_, Blocks.dirt);
            final int var14 = p_76484_2_.nextInt(4);
            final int var10 = var6 - p_76484_2_.nextInt(4) - 1;
            int var11 = 3 - p_76484_2_.nextInt(3);
            int var15 = p_76484_3_;
            int var16 = p_76484_5_;
            int var17 = 0;
            for (int var18 = 0; var18 < var6; ++var18) {
                final int var19 = p_76484_4_ + var18;
                if (var18 >= var10 && var11 > 0) {
                    var15 += Direction.offsetX[var14];
                    var16 += Direction.offsetZ[var14];
                    --var11;
                }
                final Block var20 = p_76484_1_.getBlock(var15, var19, var16);
                if (var20.getMaterial() == Material.air || var20.getMaterial() == Material.leaves) {
                    this.func_150516_a(p_76484_1_, var15, var19, var16, Blocks.log2, 0);
                    var17 = var19;
                }
            }
            for (int var18 = -1; var18 <= 1; ++var18) {
                for (int var19 = -1; var19 <= 1; ++var19) {
                    this.func_150525_a(p_76484_1_, var15 + var18, var17 + 1, var16 + var19);
                }
            }
            this.func_150525_a(p_76484_1_, var15 + 2, var17 + 1, var16);
            this.func_150525_a(p_76484_1_, var15 - 2, var17 + 1, var16);
            this.func_150525_a(p_76484_1_, var15, var17 + 1, var16 + 2);
            this.func_150525_a(p_76484_1_, var15, var17 + 1, var16 - 2);
            for (int var18 = -3; var18 <= 3; ++var18) {
                for (int var19 = -3; var19 <= 3; ++var19) {
                    if (Math.abs(var18) != 3 || Math.abs(var19) != 3) {
                        this.func_150525_a(p_76484_1_, var15 + var18, var17, var16 + var19);
                    }
                }
            }
            var15 = p_76484_3_;
            var16 = p_76484_5_;
            int var18 = p_76484_2_.nextInt(4);
            if (var18 != var14) {
                final int var19 = var10 - p_76484_2_.nextInt(2) - 1;
                int var21 = 1 + p_76484_2_.nextInt(3);
                var17 = 0;
                for (int var22 = var19; var22 < var6 && var21 > 0; ++var22, --var21) {
                    if (var22 >= 1) {
                        final int var23 = p_76484_4_ + var22;
                        var15 += Direction.offsetX[var18];
                        var16 += Direction.offsetZ[var18];
                        final Block var24 = p_76484_1_.getBlock(var15, var23, var16);
                        if (var24.getMaterial() == Material.air || var24.getMaterial() == Material.leaves) {
                            this.func_150516_a(p_76484_1_, var15, var23, var16, Blocks.log2, 0);
                            var17 = var23;
                        }
                    }
                }
                if (var17 > 0) {
                    for (int var22 = -1; var22 <= 1; ++var22) {
                        for (int var23 = -1; var23 <= 1; ++var23) {
                            this.func_150525_a(p_76484_1_, var15 + var22, var17 + 1, var16 + var23);
                        }
                    }
                    for (int var22 = -2; var22 <= 2; ++var22) {
                        for (int var23 = -2; var23 <= 2; ++var23) {
                            if (Math.abs(var22) != 2 || Math.abs(var23) != 2) {
                                this.func_150525_a(p_76484_1_, var15 + var22, var17, var16 + var23);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    private void func_150525_a(final World p_150525_1_, final int p_150525_2_, final int p_150525_3_, final int p_150525_4_) {
        final Block var5 = p_150525_1_.getBlock(p_150525_2_, p_150525_3_, p_150525_4_);
        if (var5.getMaterial() == Material.air || var5.getMaterial() == Material.leaves) {
            this.func_150516_a(p_150525_1_, p_150525_2_, p_150525_3_, p_150525_4_, Blocks.leaves2, 0);
        }
    }
}
