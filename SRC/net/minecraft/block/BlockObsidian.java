package net.minecraft.block;

import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;

public class BlockObsidian extends BlockStone
{
    private static final String __OBFID = "CL_00000279";
    
    @Override
    public int quantityDropped(final Random p_149745_1_) {
        return 1;
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return Item.getItemFromBlock(Blocks.obsidian);
    }
    
    @Override
    public MapColor getMapColor(final int p_149728_1_) {
        return MapColor.field_151654_J;
    }
}
