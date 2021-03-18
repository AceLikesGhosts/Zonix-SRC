package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;

public class WorldGenForest extends WorldGenAbstractTree
{
    private boolean field_150531_a;
    private static final String __OBFID = "CL_00000401";
    
    public WorldGenForest(final boolean p_i45449_1_, final boolean p_i45449_2_) {
        super(p_i45449_1_);
        this.field_150531_a = p_i45449_2_;
    }
    
    @Override
    public boolean generate(final World p_76484_1_, final Random p_76484_2_, final int p_76484_3_, final int p_76484_4_, final int p_76484_5_) {
        int var6 = p_76484_2_.nextInt(3) + 5;
        if (this.field_150531_a) {
            var6 += p_76484_2_.nextInt(7);
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
            for (int var14 = p_76484_4_ - 3 + var6; var14 <= p_76484_4_ + var6; ++var14) {
                final int var10 = var14 - (p_76484_4_ + var6);
                for (int var11 = 1 - var10 / 2, var15 = p_76484_3_ - var11; var15 <= p_76484_3_ + var11; ++var15) {
                    final int var16 = var15 - p_76484_3_;
                    for (int var17 = p_76484_5_ - var11; var17 <= p_76484_5_ + var11; ++var17) {
                        final int var18 = var17 - p_76484_5_;
                        if (Math.abs(var16) != var11 || Math.abs(var18) != var11 || (p_76484_2_.nextInt(2) != 0 && var10 != 0)) {
                            final Block var19 = p_76484_1_.getBlock(var15, var14, var17);
                            if (var19.getMaterial() == Material.air || var19.getMaterial() == Material.leaves) {
                                this.func_150516_a(p_76484_1_, var15, var14, var17, Blocks.leaves, 2);
                            }
                        }
                    }
                }
            }
            for (int var14 = 0; var14 < var6; ++var14) {
                final Block var20 = p_76484_1_.getBlock(p_76484_3_, p_76484_4_ + var14, p_76484_5_);
                if (var20.getMaterial() == Material.air || var20.getMaterial() == Material.leaves) {
                    this.func_150516_a(p_76484_1_, p_76484_3_, p_76484_4_ + var14, p_76484_5_, Blocks.log, 2);
                }
            }
            return true;
        }
        return false;
    }
}
