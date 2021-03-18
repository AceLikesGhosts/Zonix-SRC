package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public class WorldGenFire extends WorldGenerator
{
    private static final String __OBFID = "CL_00000412";
    
    @Override
    public boolean generate(final World p_76484_1_, final Random p_76484_2_, final int p_76484_3_, final int p_76484_4_, final int p_76484_5_) {
        for (int var6 = 0; var6 < 64; ++var6) {
            final int var7 = p_76484_3_ + p_76484_2_.nextInt(8) - p_76484_2_.nextInt(8);
            final int var8 = p_76484_4_ + p_76484_2_.nextInt(4) - p_76484_2_.nextInt(4);
            final int var9 = p_76484_5_ + p_76484_2_.nextInt(8) - p_76484_2_.nextInt(8);
            if (p_76484_1_.isAirBlock(var7, var8, var9) && p_76484_1_.getBlock(var7, var8 - 1, var9) == Blocks.netherrack) {
                p_76484_1_.setBlock(var7, var8, var9, Blocks.fire, 0, 2);
            }
        }
        return true;
    }
}
