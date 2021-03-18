package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;

public class WorldGenReed extends WorldGenerator
{
    private static final String __OBFID = "CL_00000429";
    
    @Override
    public boolean generate(final World p_76484_1_, final Random p_76484_2_, final int p_76484_3_, final int p_76484_4_, final int p_76484_5_) {
        for (int var6 = 0; var6 < 20; ++var6) {
            final int var7 = p_76484_3_ + p_76484_2_.nextInt(4) - p_76484_2_.nextInt(4);
            final int var8 = p_76484_4_;
            final int var9 = p_76484_5_ + p_76484_2_.nextInt(4) - p_76484_2_.nextInt(4);
            if (p_76484_1_.isAirBlock(var7, p_76484_4_, var9) && (p_76484_1_.getBlock(var7 - 1, p_76484_4_ - 1, var9).getMaterial() == Material.water || p_76484_1_.getBlock(var7 + 1, p_76484_4_ - 1, var9).getMaterial() == Material.water || p_76484_1_.getBlock(var7, p_76484_4_ - 1, var9 - 1).getMaterial() == Material.water || p_76484_1_.getBlock(var7, p_76484_4_ - 1, var9 + 1).getMaterial() == Material.water)) {
                for (int var10 = 2 + p_76484_2_.nextInt(p_76484_2_.nextInt(3) + 1), var11 = 0; var11 < var10; ++var11) {
                    if (Blocks.reeds.canBlockStay(p_76484_1_, var7, var8 + var11, var9)) {
                        p_76484_1_.setBlock(var7, var8 + var11, var9, Blocks.reeds, 0, 2);
                    }
                }
            }
        }
        return true;
    }
}
