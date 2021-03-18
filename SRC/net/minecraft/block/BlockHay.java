package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;

public class BlockHay extends BlockRotatedPillar
{
    private static final String __OBFID = "CL_00000256";
    
    public BlockHay() {
        super(Material.grass);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    protected IIcon func_150163_b(final int p_150163_1_) {
        return this.blockIcon;
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.field_150164_N = p_149651_1_.registerIcon(this.getTextureName() + "_top");
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
    }
}
