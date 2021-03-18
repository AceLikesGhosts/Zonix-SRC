package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import java.util.*;

public class BlockGlass extends BlockBreakable
{
    private static final String __OBFID = "CL_00000249";
    
    public BlockGlass(final Material p_i45408_1_, final boolean p_i45408_2_) {
        super("glass", p_i45408_1_, p_i45408_2_);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int quantityDropped(final Random p_149745_1_) {
        return 0;
    }
    
    @Override
    public int getRenderBlockPass() {
        return 0;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    protected boolean canSilkHarvest() {
        return true;
    }
}
