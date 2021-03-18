package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;

public class WorldGenCanopyTree extends WorldGenAbstractTree
{
    private static final String __OBFID = "CL_00000430";
    
    public WorldGenCanopyTree(final boolean p_i45461_1_) {
        super(p_i45461_1_);
    }
    
    @Override
    public boolean generate(final World p_76484_1_, final Random p_76484_2_, final int p_76484_3_, final int p_76484_4_, final int p_76484_5_) {
        final int var6 = p_76484_2_.nextInt(3) + p_76484_2_.nextInt(2) + 6;
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
            this.func_150515_a(p_76484_1_, p_76484_3_ + 1, p_76484_4_ - 1, p_76484_5_, Blocks.dirt);
            this.func_150515_a(p_76484_1_, p_76484_3_ + 1, p_76484_4_ - 1, p_76484_5_ + 1, Blocks.dirt);
            this.func_150515_a(p_76484_1_, p_76484_3_, p_76484_4_ - 1, p_76484_5_ + 1, Blocks.dirt);
            final int var14 = p_76484_2_.nextInt(4);
            final int var10 = var6 - p_76484_2_.nextInt(4);
            int var11 = 2 - p_76484_2_.nextInt(3);
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
                    this.func_150516_a(p_76484_1_, var15, var19, var16, Blocks.log2, 1);
                    this.func_150516_a(p_76484_1_, var15 + 1, var19, var16, Blocks.log2, 1);
                    this.func_150516_a(p_76484_1_, var15, var19, var16 + 1, Blocks.log2, 1);
                    this.func_150516_a(p_76484_1_, var15 + 1, var19, var16 + 1, Blocks.log2, 1);
                    var17 = var19;
                }
            }
            for (int var18 = -2; var18 <= 0; ++var18) {
                for (int var19 = -2; var19 <= 0; ++var19) {
                    final byte var21 = -1;
                    this.func_150526_a(p_76484_1_, var15 + var18, var17 + var21, var16 + var19);
                    this.func_150526_a(p_76484_1_, 1 + var15 - var18, var17 + var21, var16 + var19);
                    this.func_150526_a(p_76484_1_, var15 + var18, var17 + var21, 1 + var16 - var19);
                    this.func_150526_a(p_76484_1_, 1 + var15 - var18, var17 + var21, 1 + var16 - var19);
                    if ((var18 > -2 || var19 > -1) && (var18 != -1 || var19 != -2)) {
                        final byte var22 = 1;
                        this.func_150526_a(p_76484_1_, var15 + var18, var17 + var22, var16 + var19);
                        this.func_150526_a(p_76484_1_, 1 + var15 - var18, var17 + var22, var16 + var19);
                        this.func_150526_a(p_76484_1_, var15 + var18, var17 + var22, 1 + var16 - var19);
                        this.func_150526_a(p_76484_1_, 1 + var15 - var18, var17 + var22, 1 + var16 - var19);
                    }
                }
            }
            if (p_76484_2_.nextBoolean()) {
                this.func_150526_a(p_76484_1_, var15, var17 + 2, var16);
                this.func_150526_a(p_76484_1_, var15 + 1, var17 + 2, var16);
                this.func_150526_a(p_76484_1_, var15 + 1, var17 + 2, var16 + 1);
                this.func_150526_a(p_76484_1_, var15, var17 + 2, var16 + 1);
            }
            for (int var18 = -3; var18 <= 4; ++var18) {
                for (int var19 = -3; var19 <= 4; ++var19) {
                    if ((var18 != -3 || var19 != -3) && (var18 != -3 || var19 != 4) && (var18 != 4 || var19 != -3) && (var18 != 4 || var19 != 4) && (Math.abs(var18) < 3 || Math.abs(var19) < 3)) {
                        this.func_150526_a(p_76484_1_, var15 + var18, var17, var16 + var19);
                    }
                }
            }
            for (int var18 = -1; var18 <= 2; ++var18) {
                for (int var19 = -1; var19 <= 2; ++var19) {
                    if ((var18 < 0 || var18 > 1 || var19 < 0 || var19 > 1) && p_76484_2_.nextInt(3) <= 0) {
                        for (int var23 = p_76484_2_.nextInt(3) + 2, var24 = 0; var24 < var23; ++var24) {
                            this.func_150516_a(p_76484_1_, p_76484_3_ + var18, var17 - var24 - 1, p_76484_5_ + var19, Blocks.log2, 1);
                        }
                        for (int var24 = -1; var24 <= 1; ++var24) {
                            for (int var25 = -1; var25 <= 1; ++var25) {
                                this.func_150526_a(p_76484_1_, var15 + var18 + var24, var17 - 0, var16 + var19 + var25);
                            }
                        }
                        for (int var24 = -2; var24 <= 2; ++var24) {
                            for (int var25 = -2; var25 <= 2; ++var25) {
                                if (Math.abs(var24) != 2 || Math.abs(var25) != 2) {
                                    this.func_150526_a(p_76484_1_, var15 + var18 + var24, var17 - 1, var16 + var19 + var25);
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    private void func_150526_a(final World p_150526_1_, final int p_150526_2_, final int p_150526_3_, final int p_150526_4_) {
        final Block var5 = p_150526_1_.getBlock(p_150526_2_, p_150526_3_, p_150526_4_);
        if (var5.getMaterial() == Material.air) {
            this.func_150516_a(p_150526_1_, p_150526_2_, p_150526_3_, p_150526_4_, Blocks.leaves2, 1);
        }
    }
}
