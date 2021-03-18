package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.init.*;

public class BlockButtonWood extends BlockButton
{
    private static final String __OBFID = "CL_00000336";
    
    protected BlockButtonWood() {
        super(true);
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return Blocks.planks.getBlockTextureFromSide(1);
    }
}
