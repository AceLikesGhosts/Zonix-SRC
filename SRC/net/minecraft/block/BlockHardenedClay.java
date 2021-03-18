package net.minecraft.block;

import net.minecraft.creativetab.*;
import net.minecraft.block.material.*;

public class BlockHardenedClay extends Block
{
    private static final String __OBFID = "CL_00000255";
    
    public BlockHardenedClay() {
        super(Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public MapColor getMapColor(final int p_149728_1_) {
        return MapColor.field_151676_q;
    }
}
