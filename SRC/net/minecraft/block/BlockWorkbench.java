package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;

public class BlockWorkbench extends Block
{
    private IIcon field_150035_a;
    private IIcon field_150034_b;
    private static final String __OBFID = "CL_00000221";
    
    protected BlockWorkbench() {
        super(Material.wood);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return (p_149691_1_ == 1) ? this.field_150035_a : ((p_149691_1_ == 0) ? Blocks.planks.getBlockTextureFromSide(p_149691_1_) : ((p_149691_1_ != 2 && p_149691_1_ != 4) ? this.blockIcon : this.field_150034_b));
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
        this.field_150035_a = p_149651_1_.registerIcon(this.getTextureName() + "_top");
        this.field_150034_b = p_149651_1_.registerIcon(this.getTextureName() + "_front");
    }
    
    @Override
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        if (p_149727_1_.isClient) {
            return true;
        }
        p_149727_5_.displayGUIWorkbench(p_149727_2_, p_149727_3_, p_149727_4_);
        return true;
    }
}
