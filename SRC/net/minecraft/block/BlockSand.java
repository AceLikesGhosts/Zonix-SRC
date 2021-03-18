package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;

public class BlockSand extends BlockFalling
{
    public static final String[] field_149838_a;
    private static IIcon field_149837_b;
    private static IIcon field_149839_N;
    private static final String __OBFID = "CL_00000303";
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return (p_149691_2_ == 1) ? BlockSand.field_149839_N : BlockSand.field_149837_b;
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        BlockSand.field_149837_b = p_149651_1_.registerIcon("sand");
        BlockSand.field_149839_N = p_149651_1_.registerIcon("red_sand");
    }
    
    @Override
    public int damageDropped(final int p_149692_1_) {
        return p_149692_1_;
    }
    
    @Override
    public void getSubBlocks(final Item p_149666_1_, final CreativeTabs p_149666_2_, final List p_149666_3_) {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
    }
    
    @Override
    public MapColor getMapColor(final int p_149728_1_) {
        return (p_149728_1_ == 1) ? MapColor.field_151664_l : MapColor.field_151658_d;
    }
    
    static {
        field_149838_a = new String[] { "default", "red" };
    }
}
