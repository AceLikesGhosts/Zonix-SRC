package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.init.*;

public class BlockButtonStone extends BlockButton
{
    private static final String __OBFID = "CL_00000319";
    
    protected BlockButtonStone() {
        super(false);
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return Blocks.stone.getBlockTextureFromSide(1);
    }
}
