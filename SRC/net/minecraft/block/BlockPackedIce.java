package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import java.util.*;

public class BlockPackedIce extends Block
{
    private static final String __OBFID = "CL_00000283";
    
    public BlockPackedIce() {
        super(Material.field_151598_x);
        this.slipperiness = 0.98f;
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int quantityDropped(final Random p_149745_1_) {
        return 0;
    }
}
