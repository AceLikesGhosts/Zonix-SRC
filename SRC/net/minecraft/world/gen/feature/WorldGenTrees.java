package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

public class WorldGenTrees extends WorldGenAbstractTree
{
    private final int minTreeHeight;
    private final boolean vinesGrow;
    private final int metaWood;
    private final int metaLeaves;
    private static final String __OBFID = "CL_00000438";
    
    public WorldGenTrees(final boolean p_i2027_1_) {
        this(p_i2027_1_, 4, 0, 0, false);
    }
    
    public WorldGenTrees(final boolean p_i2028_1_, final int p_i2028_2_, final int p_i2028_3_, final int p_i2028_4_, final boolean p_i2028_5_) {
        super(p_i2028_1_);
        this.minTreeHeight = p_i2028_2_;
        this.metaWood = p_i2028_3_;
        this.metaLeaves = p_i2028_4_;
        this.vinesGrow = p_i2028_5_;
    }
    
    @Override
    public boolean generate(final World p_76484_1_, final Random p_76484_2_, final int p_76484_3_, final int p_76484_4_, final int p_76484_5_) {
        final int var6 = p_76484_2_.nextInt(3) + this.minTreeHeight;
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
        if ((var13 == Blocks.grass || var13 == Blocks.dirt || var13 == Blocks.farmland) && p_76484_4_ < 256 - var6 - 1) {
            this.func_150515_a(p_76484_1_, p_76484_3_, p_76484_4_ - 1, p_76484_5_, Blocks.dirt);
            final byte var9 = 3;
            final byte var14 = 0;
            for (int var11 = p_76484_4_ - var9 + var6; var11 <= p_76484_4_ + var6; ++var11) {
                final int var15 = var11 - (p_76484_4_ + var6);
                for (int var16 = var14 + 1 - var15 / 2, var17 = p_76484_3_ - var16; var17 <= p_76484_3_ + var16; ++var17) {
                    final int var18 = var17 - p_76484_3_;
                    for (int var19 = p_76484_5_ - var16; var19 <= p_76484_5_ + var16; ++var19) {
                        final int var20 = var19 - p_76484_5_;
                        if (Math.abs(var18) != var16 || Math.abs(var20) != var16 || (p_76484_2_.nextInt(2) != 0 && var15 != 0)) {
                            final Block var21 = p_76484_1_.getBlock(var17, var11, var19);
                            if (var21.getMaterial() == Material.air || var21.getMaterial() == Material.leaves) {
                                this.func_150516_a(p_76484_1_, var17, var11, var19, Blocks.leaves, this.metaLeaves);
                            }
                        }
                    }
                }
            }
            for (int var11 = 0; var11 < var6; ++var11) {
                final Block var12 = p_76484_1_.getBlock(p_76484_3_, p_76484_4_ + var11, p_76484_5_);
                if (var12.getMaterial() == Material.air || var12.getMaterial() == Material.leaves) {
                    this.func_150516_a(p_76484_1_, p_76484_3_, p_76484_4_ + var11, p_76484_5_, Blocks.log, this.metaWood);
                    if (this.vinesGrow && var11 > 0) {
                        if (p_76484_2_.nextInt(3) > 0 && p_76484_1_.isAirBlock(p_76484_3_ - 1, p_76484_4_ + var11, p_76484_5_)) {
                            this.func_150516_a(p_76484_1_, p_76484_3_ - 1, p_76484_4_ + var11, p_76484_5_, Blocks.vine, 8);
                        }
                        if (p_76484_2_.nextInt(3) > 0 && p_76484_1_.isAirBlock(p_76484_3_ + 1, p_76484_4_ + var11, p_76484_5_)) {
                            this.func_150516_a(p_76484_1_, p_76484_3_ + 1, p_76484_4_ + var11, p_76484_5_, Blocks.vine, 2);
                        }
                        if (p_76484_2_.nextInt(3) > 0 && p_76484_1_.isAirBlock(p_76484_3_, p_76484_4_ + var11, p_76484_5_ - 1)) {
                            this.func_150516_a(p_76484_1_, p_76484_3_, p_76484_4_ + var11, p_76484_5_ - 1, Blocks.vine, 1);
                        }
                        if (p_76484_2_.nextInt(3) > 0 && p_76484_1_.isAirBlock(p_76484_3_, p_76484_4_ + var11, p_76484_5_ + 1)) {
                            this.func_150516_a(p_76484_1_, p_76484_3_, p_76484_4_ + var11, p_76484_5_ + 1, Blocks.vine, 4);
                        }
                    }
                }
            }
            if (this.vinesGrow) {
                for (int var11 = p_76484_4_ - 3 + var6; var11 <= p_76484_4_ + var6; ++var11) {
                    final int var15 = var11 - (p_76484_4_ + var6);
                    for (int var16 = 2 - var15 / 2, var17 = p_76484_3_ - var16; var17 <= p_76484_3_ + var16; ++var17) {
                        for (int var18 = p_76484_5_ - var16; var18 <= p_76484_5_ + var16; ++var18) {
                            if (p_76484_1_.getBlock(var17, var11, var18).getMaterial() == Material.leaves) {
                                if (p_76484_2_.nextInt(4) == 0 && p_76484_1_.getBlock(var17 - 1, var11, var18).getMaterial() == Material.air) {
                                    this.growVines(p_76484_1_, var17 - 1, var11, var18, 8);
                                }
                                if (p_76484_2_.nextInt(4) == 0 && p_76484_1_.getBlock(var17 + 1, var11, var18).getMaterial() == Material.air) {
                                    this.growVines(p_76484_1_, var17 + 1, var11, var18, 2);
                                }
                                if (p_76484_2_.nextInt(4) == 0 && p_76484_1_.getBlock(var17, var11, var18 - 1).getMaterial() == Material.air) {
                                    this.growVines(p_76484_1_, var17, var11, var18 - 1, 1);
                                }
                                if (p_76484_2_.nextInt(4) == 0 && p_76484_1_.getBlock(var17, var11, var18 + 1).getMaterial() == Material.air) {
                                    this.growVines(p_76484_1_, var17, var11, var18 + 1, 4);
                                }
                            }
                        }
                    }
                }
                if (p_76484_2_.nextInt(5) == 0 && var6 > 5) {
                    for (int var11 = 0; var11 < 2; ++var11) {
                        for (int var15 = 0; var15 < 4; ++var15) {
                            if (p_76484_2_.nextInt(4 - var11) == 0) {
                                final int var16 = p_76484_2_.nextInt(3);
                                this.func_150516_a(p_76484_1_, p_76484_3_ + Direction.offsetX[Direction.rotateOpposite[var15]], p_76484_4_ + var6 - 5 + var11, p_76484_5_ + Direction.offsetZ[Direction.rotateOpposite[var15]], Blocks.cocoa, var16 << 2 | var15);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    private void growVines(final World p_76529_1_, final int p_76529_2_, int p_76529_3_, final int p_76529_4_, final int p_76529_5_) {
        this.func_150516_a(p_76529_1_, p_76529_2_, p_76529_3_, p_76529_4_, Blocks.vine, p_76529_5_);
        int var6 = 4;
        while (true) {
            --p_76529_3_;
            if (p_76529_1_.getBlock(p_76529_2_, p_76529_3_, p_76529_4_).getMaterial() != Material.air || var6 <= 0) {
                break;
            }
            this.func_150516_a(p_76529_1_, p_76529_2_, p_76529_3_, p_76529_4_, Blocks.vine, p_76529_5_);
            --var6;
        }
    }
}
