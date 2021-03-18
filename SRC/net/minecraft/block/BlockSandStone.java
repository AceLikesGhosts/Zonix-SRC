package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.texture.*;

public class BlockSandStone extends Block
{
    public static final String[] field_150157_a;
    private static final String[] field_150156_b;
    private IIcon[] field_150158_M;
    private IIcon field_150159_N;
    private IIcon field_150160_O;
    private static final String __OBFID = "CL_00000304";
    
    public BlockSandStone() {
        super(Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, int p_149691_2_) {
        if (p_149691_1_ == 1 || (p_149691_1_ == 0 && (p_149691_2_ == 1 || p_149691_2_ == 2))) {
            return this.field_150159_N;
        }
        if (p_149691_1_ == 0) {
            return this.field_150160_O;
        }
        if (p_149691_2_ < 0 || p_149691_2_ >= this.field_150158_M.length) {
            p_149691_2_ = 0;
        }
        return this.field_150158_M[p_149691_2_];
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
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.field_150158_M = new IIcon[BlockSandStone.field_150156_b.length];
        for (int var2 = 0; var2 < this.field_150158_M.length; ++var2) {
            this.field_150158_M[var2] = p_149651_1_.registerIcon(this.getTextureName() + "_" + BlockSandStone.field_150156_b[var2]);
        }
        this.field_150159_N = p_149651_1_.registerIcon(this.getTextureName() + "_top");
        this.field_150160_O = p_149651_1_.registerIcon(this.getTextureName() + "_bottom");
    }
    
    static {
        field_150157_a = new String[] { "default", "chiseled", "smooth" };
        field_150156_b = new String[] { "normal", "carved", "smooth" };
    }
}
