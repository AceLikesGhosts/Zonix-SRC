package net.minecraft.world.gen.feature;

import net.minecraft.block.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;

public class WorldGenSand extends WorldGenerator
{
    private Block field_150517_a;
    private int radius;
    private static final String __OBFID = "CL_00000431";
    
    public WorldGenSand(final Block p_i45462_1_, final int p_i45462_2_) {
        this.field_150517_a = p_i45462_1_;
        this.radius = p_i45462_2_;
    }
    
    @Override
    public boolean generate(final World p_76484_1_, final Random p_76484_2_, final int p_76484_3_, final int p_76484_4_, final int p_76484_5_) {
        if (p_76484_1_.getBlock(p_76484_3_, p_76484_4_, p_76484_5_).getMaterial() != Material.water) {
            return false;
        }
        final int var6 = p_76484_2_.nextInt(this.radius - 2) + 2;
        final byte var7 = 2;
        for (int var8 = p_76484_3_ - var6; var8 <= p_76484_3_ + var6; ++var8) {
            for (int var9 = p_76484_5_ - var6; var9 <= p_76484_5_ + var6; ++var9) {
                final int var10 = var8 - p_76484_3_;
                final int var11 = var9 - p_76484_5_;
                if (var10 * var10 + var11 * var11 <= var6 * var6) {
                    for (int var12 = p_76484_4_ - var7; var12 <= p_76484_4_ + var7; ++var12) {
                        final Block var13 = p_76484_1_.getBlock(var8, var12, var9);
                        if (var13 == Blocks.dirt || var13 == Blocks.grass) {
                            p_76484_1_.setBlock(var8, var12, var9, this.field_150517_a, 0, 2);
                        }
                    }
                }
            }
        }
        return true;
    }
}
