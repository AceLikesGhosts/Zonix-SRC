package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;

public class BlockWoodSlab extends BlockSlab
{
    public static final String[] field_150005_b;
    private static final String __OBFID = "CL_00000337";
    
    public BlockWoodSlab(final boolean p_i45437_1_) {
        super(p_i45437_1_, Material.wood);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return Blocks.planks.getIcon(p_149691_1_, p_149691_2_ & 0x7);
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return Item.getItemFromBlock(Blocks.wooden_slab);
    }
    
    @Override
    protected ItemStack createStackedBlock(final int p_149644_1_) {
        return new ItemStack(Item.getItemFromBlock(Blocks.wooden_slab), 2, p_149644_1_ & 0x7);
    }
    
    @Override
    public String func_150002_b(int p_150002_1_) {
        if (p_150002_1_ < 0 || p_150002_1_ >= BlockWoodSlab.field_150005_b.length) {
            p_150002_1_ = 0;
        }
        return super.getUnlocalizedName() + "." + BlockWoodSlab.field_150005_b[p_150002_1_];
    }
    
    @Override
    public void getSubBlocks(final Item p_149666_1_, final CreativeTabs p_149666_2_, final List p_149666_3_) {
        if (p_149666_1_ != Item.getItemFromBlock(Blocks.double_wooden_slab)) {
            for (int var4 = 0; var4 < BlockWoodSlab.field_150005_b.length; ++var4) {
                p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
            }
        }
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
    }
    
    static {
        field_150005_b = new String[] { "oak", "spruce", "birch", "jungle", "acacia", "big_oak" };
    }
}
