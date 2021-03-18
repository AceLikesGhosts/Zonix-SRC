package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;

public class WorldGenTaiga1 extends WorldGenAbstractTree
{
    private static final String __OBFID = "CL_00000427";
    
    public WorldGenTaiga1() {
        super(false);
    }
    
    @Override
    public boolean generate(final World p_76484_1_, final Random p_76484_2_, final int p_76484_3_, final int p_76484_4_, final int p_76484_5_) {
        final int var6 = p_76484_2_.nextInt(5) + 7;
        final int var7 = var6 - p_76484_2_.nextInt(2) - 3;
        final int var8 = var6 - var7;
        final int var9 = 1 + p_76484_2_.nextInt(var8 + 1);
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
                        if (!this.func_150523_a(var16)) {
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
        if ((var17 == Blocks.grass || var17 == Blocks.dirt) && p_76484_4_ < 256 - var6 - 1) {
            this.func_150515_a(p_76484_1_, p_76484_3_, p_76484_4_ - 1, p_76484_5_, Blocks.dirt);
            int var13 = 0;
            for (int var14 = p_76484_4_ + var6; var14 >= p_76484_4_ + var7; --var14) {
                for (int var15 = p_76484_3_ - var13; var15 <= p_76484_3_ + var13; ++var15) {
                    final int var18 = var15 - p_76484_3_;
                    for (int var19 = p_76484_5_ - var13; var19 <= p_76484_5_ + var13; ++var19) {
                        final int var20 = var19 - p_76484_5_;
                        if ((Math.abs(var18) != var13 || Math.abs(var20) != var13 || var13 <= 0) && !p_76484_1_.getBlock(var15, var14, var19).func_149730_j()) {
                            this.func_150516_a(p_76484_1_, var15, var14, var19, Blocks.leaves, 1);
                        }
                    }
                }
                if (var13 >= 1 && var14 == p_76484_4_ + var7 + 1) {
                    --var13;
                }
                else if (var13 < var9) {
                    ++var13;
                }
            }
            for (int var14 = 0; var14 < var6 - 1; ++var14) {
                final Block var21 = p_76484_1_.getBlock(p_76484_3_, p_76484_4_ + var14, p_76484_5_);
                if (var21.getMaterial() == Material.air || var21.getMaterial() == Material.leaves) {
                    this.func_150516_a(p_76484_1_, p_76484_3_, p_76484_4_ + var14, p_76484_5_, Blocks.log, 1);
                }
            }
            return true;
        }
        return false;
    }
}
