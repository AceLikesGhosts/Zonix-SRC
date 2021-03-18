package net.minecraft.block;

import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class BlockGravel extends BlockFalling
{
    private static final String __OBFID = "CL_00000252";
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, int p_149650_3_) {
        if (p_149650_3_ > 3) {
            p_149650_3_ = 3;
        }
        return (p_149650_2_.nextInt(10 - p_149650_3_ * 3) == 0) ? Items.flint : Item.getItemFromBlock(this);
    }
}
