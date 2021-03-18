package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public class WorldGenSwamp extends WorldGenAbstractTree
{
    private static final String __OBFID = "CL_00000436";
    
    public WorldGenSwamp() {
        super(false);
    }
    
    @Override
    public boolean generate(final World p_76484_1_, final Random p_76484_2_, final int p_76484_3_, int p_76484_4_, final int p_76484_5_) {
        final int var6 = p_76484_2_.nextInt(4) + 5;
        while (p_76484_1_.getBlock(p_76484_3_, p_76484_4_ - 1, p_76484_5_).getMaterial() == Material.water) {
            --p_76484_4_;
        }
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
                var9 = 3;
            }
            for (int var10 = p_76484_3_ - var9; var10 <= p_76484_3_ + var9 && var7; ++var10) {
                for (int var11 = p_76484_5_ - var9; var11 <= p_76484_5_ + var9 && var7; ++var11) {
                    if (var8 >= 0 && var8 < 256) {
                        final Block var12 = p_76484_1_.getBlock(var10, var8, var11);
                        if (var12.getMaterial() != Material.air && var12.getMaterial() != Material.leaves) {
                            if (var12 != Blocks.water && var12 != Blocks.flowing_water) {
                                var7 = false;
                            }
                            else if (var8 > p_76484_4_) {
                                var7 = false;
                            }
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
            for (int var14 = p_76484_4_ - 3 + var6; var14 <= p_76484_4_ + var6; ++var14) {
                final int var10 = var14 - (p_76484_4_ + var6);
                for (int var11 = 2 - var10 / 2, var15 = p_76484_3_ - var11; var15 <= p_76484_3_ + var11; ++var15) {
                    final int var16 = var15 - p_76484_3_;
                    for (int var17 = p_76484_5_ - var11; var17 <= p_76484_5_ + var11; ++var17) {
                        final int var18 = var17 - p_76484_5_;
                        if ((Math.abs(var16) != var11 || Math.abs(var18) != var11 || (p_76484_2_.nextInt(2) != 0 && var10 != 0)) && !p_76484_1_.getBlock(var15, var14, var17).func_149730_j()) {
                            this.func_150515_a(p_76484_1_, var15, var14, var17, Blocks.leaves);
                        }
                    }
                }
            }
            for (int var14 = 0; var14 < var6; ++var14) {
                final Block var19 = p_76484_1_.getBlock(p_76484_3_, p_76484_4_ + var14, p_76484_5_);
                if (var19.getMaterial() == Material.air || var19.getMaterial() == Material.leaves || var19 == Blocks.flowing_water || var19 == Blocks.water) {
                    this.func_150515_a(p_76484_1_, p_76484_3_, p_76484_4_ + var14, p_76484_5_, Blocks.log);
                }
            }
            for (int var14 = p_76484_4_ - 3 + var6; var14 <= p_76484_4_ + var6; ++var14) {
                final int var10 = var14 - (p_76484_4_ + var6);
                for (int var11 = 2 - var10 / 2, var15 = p_76484_3_ - var11; var15 <= p_76484_3_ + var11; ++var15) {
                    for (int var16 = p_76484_5_ - var11; var16 <= p_76484_5_ + var11; ++var16) {
                        if (p_76484_1_.getBlock(var15, var14, var16).getMaterial() == Material.leaves) {
                            if (p_76484_2_.nextInt(4) == 0 && p_76484_1_.getBlock(var15 - 1, var14, var16).getMaterial() == Material.air) {
                                this.generateVines(p_76484_1_, var15 - 1, var14, var16, 8);
                            }
                            if (p_76484_2_.nextInt(4) == 0 && p_76484_1_.getBlock(var15 + 1, var14, var16).getMaterial() == Material.air) {
                                this.generateVines(p_76484_1_, var15 + 1, var14, var16, 2);
                            }
                            if (p_76484_2_.nextInt(4) == 0 && p_76484_1_.getBlock(var15, var14, var16 - 1).getMaterial() == Material.air) {
                                this.generateVines(p_76484_1_, var15, var14, var16 - 1, 1);
                            }
                            if (p_76484_2_.nextInt(4) == 0 && p_76484_1_.getBlock(var15, var14, var16 + 1).getMaterial() == Material.air) {
                                this.generateVines(p_76484_1_, var15, var14, var16 + 1, 4);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    private void generateVines(final World p_76536_1_, final int p_76536_2_, int p_76536_3_, final int p_76536_4_, final int p_76536_5_) {
        this.func_150516_a(p_76536_1_, p_76536_2_, p_76536_3_, p_76536_4_, Blocks.vine, p_76536_5_);
        int var6 = 4;
        while (true) {
            --p_76536_3_;
            if (p_76536_1_.getBlock(p_76536_2_, p_76536_3_, p_76536_4_).getMaterial() != Material.air || var6 <= 0) {
                break;
            }
            this.func_150516_a(p_76536_1_, p_76536_2_, p_76536_3_, p_76536_4_, Blocks.vine, p_76536_5_);
            --var6;
        }
    }
}
