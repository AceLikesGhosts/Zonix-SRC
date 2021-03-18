package net.minecraft.world.gen.feature;

import net.minecraft.block.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.init.*;

public class WorldGenBlockBlob extends WorldGenerator
{
    private Block field_150545_a;
    private int field_150544_b;
    private static final String __OBFID = "CL_00000402";
    
    public WorldGenBlockBlob(final Block p_i45450_1_, final int p_i45450_2_) {
        super(false);
        this.field_150545_a = p_i45450_1_;
        this.field_150544_b = p_i45450_2_;
    }
    
    @Override
    public boolean generate(final World p_76484_1_, final Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_) {
        while (p_76484_4_ > 3) {
            if (!p_76484_1_.isAirBlock(p_76484_3_, p_76484_4_ - 1, p_76484_5_)) {
                final Block var6 = p_76484_1_.getBlock(p_76484_3_, p_76484_4_ - 1, p_76484_5_);
                if (var6 == Blocks.grass || var6 == Blocks.dirt) {
                    break;
                }
                if (var6 == Blocks.stone) {
                    break;
                }
            }
            --p_76484_4_;
        }
        if (p_76484_4_ <= 3) {
            return false;
        }
        for (int var7 = this.field_150544_b, var8 = 0; var7 >= 0 && var8 < 3; ++var8) {
            final int var9 = var7 + p_76484_2_.nextInt(2);
            final int var10 = var7 + p_76484_2_.nextInt(2);
            final int var11 = var7 + p_76484_2_.nextInt(2);
            final float var12 = (var9 + var10 + var11) * 0.333f + 0.5f;
            for (int var13 = p_76484_3_ - var9; var13 <= p_76484_3_ + var9; ++var13) {
                for (int var14 = p_76484_5_ - var11; var14 <= p_76484_5_ + var11; ++var14) {
                    for (int var15 = p_76484_4_ - var10; var15 <= p_76484_4_ + var10; ++var15) {
                        final float var16 = (float)(var13 - p_76484_3_);
                        final float var17 = (float)(var14 - p_76484_5_);
                        final float var18 = (float)(var15 - p_76484_4_);
                        if (var16 * var16 + var17 * var17 + var18 * var18 <= var12 * var12) {
                            p_76484_1_.setBlock(var13, var15, var14, this.field_150545_a, 0, 4);
                        }
                    }
                }
            }
            p_76484_3_ += -(var7 + 1) + p_76484_2_.nextInt(2 + var7 * 2);
            p_76484_5_ += -(var7 + 1) + p_76484_2_.nextInt(2 + var7 * 2);
            p_76484_4_ += 0 - p_76484_2_.nextInt(2);
        }
        return true;
    }
}
