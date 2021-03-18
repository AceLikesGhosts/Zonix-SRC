package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public class WorldGenTaiga2 extends WorldGenAbstractTree
{
    private static final String __OBFID = "CL_00000435";
    
    public WorldGenTaiga2(final boolean p_i2025_1_) {
        super(p_i2025_1_);
    }
    
    @Override
    public boolean generate(final World p_76484_1_, final Random p_76484_2_, final int p_76484_3_, final int p_76484_4_, final int p_76484_5_) {
        final int var6 = p_76484_2_.nextInt(4) + 6;
        final int var7 = 1 + p_76484_2_.nextInt(2);
        final int var8 = var6 - var7;
        final int var9 = 2 + p_76484_2_.nextInt(2);
        boolean var10 = true;
        if (p_76484_4_ < 1 || p_76484_4_ + var6 + 1 > 256) {
            return false;
        }
        for (int var11 = p_76484_4_; var11 <= p_76484_4_ + 1 + var6 && var10; ++var11) {
            final boolean var12 = true;
            int var13;
            if (var11 - p_76484_4_ < var7) {
                var13 = 0;
            }
            else {
                var13 = var9;
            }
            for (int var14 = p_76484_3_ - var13; var14 <= p_76484_3_ + var13 && var10; ++var14) {
                for (int var15 = p_76484_5_ - var13; var15 <= p_76484_5_ + var13 && var10; ++var15) {
                    if (var11 >= 0 && var11 < 256) {
                        final Block var16 = p_76484_1_.getBlock(var14, var11, var15);
                        if (var16.getMaterial() != Material.air && var16.getMaterial() != Material.leaves) {
                            var10 = false;
                        }
                    }
                    else {
                        var10 = false;
                    }
                }
            }
        }
        if (!var10) {
            return false;
        }
        final Block var17 = p_76484_1_.getBlock(p_76484_3_, p_76484_4_ - 1, p_76484_5_);
        if ((var17 == Blocks.grass || var17 == Blocks.dirt || var17 == Blocks.farmland) && p_76484_4_ < 256 - var6 - 1) {
            this.func_150515_a(p_76484_1_, p_76484_3_, p_76484_4_ - 1, p_76484_5_, Blocks.dirt);
            int var13 = p_76484_2_.nextInt(2);
            int var14 = 1;
            byte var18 = 0;
            for (int var19 = 0; var19 <= var8; ++var19) {
                final int var20 = p_76484_4_ + var6 - var19;
                for (int var21 = p_76484_3_ - var13; var21 <= p_76484_3_ + var13; ++var21) {
                    final int var22 = var21 - p_76484_3_;
                    for (int var23 = p_76484_5_ - var13; var23 <= p_76484_5_ + var13; ++var23) {
                        final int var24 = var23 - p_76484_5_;
                        if ((Math.abs(var22) != var13 || Math.abs(var24) != var13 || var13 <= 0) && !p_76484_1_.getBlock(var21, var20, var23).func_149730_j()) {
                            this.func_150516_a(p_76484_1_, var21, var20, var23, Blocks.leaves, 1);
                        }
                    }
                }
                if (var13 >= var14) {
                    var13 = var18;
                    var18 = 1;
                    if (++var14 > var9) {
                        var14 = var9;
                    }
                }
                else {
                    ++var13;
                }
            }
            int var19;
            for (var19 = p_76484_2_.nextInt(3), int var20 = 0; var20 < var6 - var19; ++var20) {
                final Block var25 = p_76484_1_.getBlock(p_76484_3_, p_76484_4_ + var20, p_76484_5_);
                if (var25.getMaterial() == Material.air || var25.getMaterial() == Material.leaves) {
                    this.func_150516_a(p_76484_1_, p_76484_3_, p_76484_4_ + var20, p_76484_5_, Blocks.log, 1);
                }
            }
            return true;
        }
        return false;
    }
}
