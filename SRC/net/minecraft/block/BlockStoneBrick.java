package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.texture.*;

public class BlockStoneBrick extends Block
{
    public static final String[] field_150142_a;
    public static final String[] field_150141_b;
    private IIcon[] field_150143_M;
    private static final String __OBFID = "CL_00000318";
    
    public BlockStoneBrick() {
        super(Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, int p_149691_2_) {
        if (p_149691_2_ < 0 || p_149691_2_ >= BlockStoneBrick.field_150141_b.length) {
            p_149691_2_ = 0;
        }
        return this.field_150143_M[p_149691_2_];
    }
    
    @Override
    public int damageDropped(final int p_149692_1_) {
        return p_149692_1_;
    }
    
    @Override
    public void getSubBlocks(final Item p_149666_1_, final CreativeTabs p_149666_2_, final List p_149666_3_) {
        for (int var4 = 0; var4 < 4; ++var4) {
            p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
        }
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.field_150143_M = new IIcon[BlockStoneBrick.field_150141_b.length];
        for (int var2 = 0; var2 < this.field_150143_M.length; ++var2) {
            String var3 = this.getTextureName();
            if (BlockStoneBrick.field_150141_b[var2] != null) {
                var3 = var3 + "_" + BlockStoneBrick.field_150141_b[var2];
            }
            this.field_150143_M[var2] = p_149651_1_.registerIcon(var3);
        }
    }
    
    static {
        field_150142_a = new String[] { "default", "mossy", "cracked", "chiseled" };
        field_150141_b = new String[] { null, "mossy", "cracked", "carved" };
    }
}
