package net.minecraft.world.gen.feature;

import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;

public class WorldGenMinable extends WorldGenerator
{
    private Block field_150519_a;
    private int numberOfBlocks;
    private Block field_150518_c;
    private static final String __OBFID = "CL_00000426";
    
    public WorldGenMinable(final Block p_i45459_1_, final int p_i45459_2_) {
        this(p_i45459_1_, p_i45459_2_, Blocks.stone);
    }
    
    public WorldGenMinable(final Block p_i45460_1_, final int p_i45460_2_, final Block p_i45460_3_) {
        this.field_150519_a = p_i45460_1_;
        this.numberOfBlocks = p_i45460_2_;
        this.field_150518_c = p_i45460_3_;
    }
    
    @Override
    public boolean generate(final World p_76484_1_, final Random p_76484_2_, final int p_76484_3_, final int p_76484_4_, final int p_76484_5_) {
        final float var6 = p_76484_2_.nextFloat() * 3.1415927f;
        final double var7 = p_76484_3_ + 8 + MathHelper.sin(var6) * this.numberOfBlocks / 8.0f;
        final double var8 = p_76484_3_ + 8 - MathHelper.sin(var6) * this.numberOfBlocks / 8.0f;
        final double var9 = p_76484_5_ + 8 + MathHelper.cos(var6) * this.numberOfBlocks / 8.0f;
        final double var10 = p_76484_5_ + 8 - MathHelper.cos(var6) * this.numberOfBlocks / 8.0f;
        final double var11 = p_76484_4_ + p_76484_2_.nextInt(3) - 2;
        final double var12 = p_76484_4_ + p_76484_2_.nextInt(3) - 2;
        for (int var13 = 0; var13 <= this.numberOfBlocks; ++var13) {
            final double var14 = var7 + (var8 - var7) * var13 / this.numberOfBlocks;
            final double var15 = var11 + (var12 - var11) * var13 / this.numberOfBlocks;
            final double var16 = var9 + (var10 - var9) * var13 / this.numberOfBlocks;
            final double var17 = p_76484_2_.nextDouble() * this.numberOfBlocks / 16.0;
            final double var18 = (MathHelper.sin(var13 * 3.1415927f / this.numberOfBlocks) + 1.0f) * var17 + 1.0;
            final double var19 = (MathHelper.sin(var13 * 3.1415927f / this.numberOfBlocks) + 1.0f) * var17 + 1.0;
            final int var20 = MathHelper.floor_double(var14 - var18 / 2.0);
            final int var21 = MathHelper.floor_double(var15 - var19 / 2.0);
            final int var22 = MathHelper.floor_double(var16 - var18 / 2.0);
            final int var23 = MathHelper.floor_double(var14 + var18 / 2.0);
            final int var24 = MathHelper.floor_double(var15 + var19 / 2.0);
            final int var25 = MathHelper.floor_double(var16 + var18 / 2.0);
            for (int var26 = var20; var26 <= var23; ++var26) {
                final double var27 = (var26 + 0.5 - var14) / (var18 / 2.0);
                if (var27 * var27 < 1.0) {
                    for (int var28 = var21; var28 <= var24; ++var28) {
                        final double var29 = (var28 + 0.5 - var15) / (var19 / 2.0);
                        if (var27 * var27 + var29 * var29 < 1.0) {
                            for (int var30 = var22; var30 <= var25; ++var30) {
                                final double var31 = (var30 + 0.5 - var16) / (var18 / 2.0);
                                if (var27 * var27 + var29 * var29 + var31 * var31 < 1.0 && p_76484_1_.getBlock(var26, var28, var30) == this.field_150518_c) {
                                    p_76484_1_.setBlock(var26, var28, var30, this.field_150519_a, 0, 2);
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
