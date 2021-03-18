package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class WorldGenVines extends WorldGenerator
{
    private static final String __OBFID = "CL_00000439";
    
    @Override
    public boolean generate(final World p_76484_1_, final Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_) {
        final int var6 = p_76484_3_;
        final int var7 = p_76484_5_;
        while (p_76484_4_ < 128) {
            if (p_76484_1_.isAirBlock(p_76484_3_, p_76484_4_, p_76484_5_)) {
                for (int var8 = 2; var8 <= 5; ++var8) {
                    if (Blocks.vine.canPlaceBlockOnSide(p_76484_1_, p_76484_3_, p_76484_4_, p_76484_5_, var8)) {
                        p_76484_1_.setBlock(p_76484_3_, p_76484_4_, p_76484_5_, Blocks.vine, 1 << Direction.facingToDirection[Facing.oppositeSide[var8]], 2);
                        break;
                    }
                }
            }
            else {
                p_76484_3_ = var6 + p_76484_2_.nextInt(4) - p_76484_2_.nextInt(4);
                p_76484_5_ = var7 + p_76484_2_.nextInt(4) - p_76484_2_.nextInt(4);
            }
            ++p_76484_4_;
        }
        return true;
    }
}
