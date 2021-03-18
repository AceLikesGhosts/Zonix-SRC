package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.item.*;

public class BlockStainedGlassPane extends BlockPane
{
    private static final IIcon[] field_150106_a;
    private static final IIcon[] field_150105_b;
    private static final String __OBFID = "CL_00000313";
    
    public BlockStainedGlassPane() {
        super("glass", "glass_pane_top", Material.glass, false);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public IIcon func_149735_b(final int p_149735_1_, final int p_149735_2_) {
        return BlockStainedGlassPane.field_150106_a[p_149735_2_ % BlockStainedGlassPane.field_150106_a.length];
    }
    
    public IIcon func_150104_b(final int p_150104_1_) {
        return BlockStainedGlassPane.field_150105_b[~p_150104_1_ & 0xF];
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return this.func_149735_b(p_149691_1_, ~p_149691_2_ & 0xF);
    }
    
    @Override
    public int damageDropped(final int p_149692_1_) {
        return p_149692_1_;
    }
    
    public static int func_150103_c(final int p_150103_0_) {
        return p_150103_0_ & 0xF;
    }
    
    @Override
    public void getSubBlocks(final Item p_149666_1_, final CreativeTabs p_149666_2_, final List p_149666_3_) {
        for (int var4 = 0; var4 < BlockStainedGlassPane.field_150106_a.length; ++var4) {
            p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
        }
    }
    
    @Override
    public int getRenderBlockPass() {
        return 1;
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        super.registerBlockIcons(p_149651_1_);
        for (int var2 = 0; var2 < BlockStainedGlassPane.field_150106_a.length; ++var2) {
            BlockStainedGlassPane.field_150106_a[var2] = p_149651_1_.registerIcon(this.getTextureName() + "_" + ItemDye.field_150921_b[func_150103_c(var2)]);
            BlockStainedGlassPane.field_150105_b[var2] = p_149651_1_.registerIcon(this.getTextureName() + "_pane_top_" + ItemDye.field_150921_b[func_150103_c(var2)]);
        }
    }
    
    static {
        field_150106_a = new IIcon[16];
        field_150105_b = new IIcon[16];
    }
}
