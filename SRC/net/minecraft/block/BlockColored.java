package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;

public class BlockColored extends Block
{
    private IIcon[] field_150033_a;
    private static final String __OBFID = "CL_00000217";
    
    public BlockColored(final Material p_i45398_1_) {
        super(p_i45398_1_);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return this.field_150033_a[p_149691_2_ % this.field_150033_a.length];
    }
    
    @Override
    public int damageDropped(final int p_149692_1_) {
        return p_149692_1_;
    }
    
    public static int func_150032_b(final int p_150032_0_) {
        return func_150031_c(p_150032_0_);
    }
    
    public static int func_150031_c(final int p_150031_0_) {
        return ~p_150031_0_ & 0xF;
    }
    
    @Override
    public void getSubBlocks(final Item p_149666_1_, final CreativeTabs p_149666_2_, final List p_149666_3_) {
        for (int var4 = 0; var4 < 16; ++var4) {
            p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
        }
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.field_150033_a = new IIcon[16];
        for (int var2 = 0; var2 < this.field_150033_a.length; ++var2) {
            this.field_150033_a[var2] = p_149651_1_.registerIcon(this.getTextureName() + "_" + ItemDye.field_150921_b[func_150031_c(var2)]);
        }
    }
    
    @Override
    public MapColor getMapColor(final int p_149728_1_) {
        return MapColor.func_151644_a(p_149728_1_);
    }
}
