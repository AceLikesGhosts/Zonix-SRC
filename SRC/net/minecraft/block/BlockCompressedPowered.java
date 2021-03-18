package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;

public class BlockCompressedPowered extends BlockCompressed
{
    private static final String __OBFID = "CL_00000287";
    
    public BlockCompressedPowered(final MapColor p_i45416_1_) {
        super(p_i45416_1_);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess p_149709_1_, final int p_149709_2_, final int p_149709_3_, final int p_149709_4_, final int p_149709_5_) {
        return 15;
    }
}
