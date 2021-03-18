package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.texture.*;

public class BlockWood extends Block
{
    public static final String[] field_150096_a;
    private IIcon[] field_150095_b;
    private static final String __OBFID = "CL_00000335";
    
    public BlockWood() {
        super(Material.wood);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, int p_149691_2_) {
        if (p_149691_2_ < 0 || p_149691_2_ >= this.field_150095_b.length) {
            p_149691_2_ = 0;
        }
        return this.field_150095_b[p_149691_2_];
    }
    
    @Override
    public int damageDropped(final int p_149692_1_) {
        return p_149692_1_;
    }
    
    @Override
    public void getSubBlocks(final Item p_149666_1_, final CreativeTabs p_149666_2_, final List p_149666_3_) {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 2));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 3));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 4));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 5));
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.field_150095_b = new IIcon[BlockWood.field_150096_a.length];
        for (int var2 = 0; var2 < this.field_150095_b.length; ++var2) {
            this.field_150095_b[var2] = p_149651_1_.registerIcon(this.getTextureName() + "_" + BlockWood.field_150096_a[var2]);
        }
    }
    
    static {
        field_150096_a = new String[] { "oak", "spruce", "birch", "jungle", "acacia", "big_oak" };
    }
}
