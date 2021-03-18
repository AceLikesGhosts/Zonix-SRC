package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;

public class BlockNewLeaf extends BlockLeaves
{
    public static final String[][] field_150132_N;
    public static final String[] field_150133_O;
    private static final String __OBFID = "CL_00000276";
    
    @Override
    protected void func_150124_c(final World p_150124_1_, final int p_150124_2_, final int p_150124_3_, final int p_150124_4_, final int p_150124_5_, final int p_150124_6_) {
        if ((p_150124_5_ & 0x3) == 0x1 && p_150124_1_.rand.nextInt(p_150124_6_) == 0) {
            this.dropBlockAsItem_do(p_150124_1_, p_150124_2_, p_150124_3_, p_150124_4_, new ItemStack(Items.apple, 1, 0));
        }
    }
    
    @Override
    public int damageDropped(final int p_149692_1_) {
        return super.damageDropped(p_149692_1_) + 4;
    }
    
    @Override
    public int getDamageValue(final World p_149643_1_, final int p_149643_2_, final int p_149643_3_, final int p_149643_4_) {
        return p_149643_1_.getBlockMetadata(p_149643_2_, p_149643_3_, p_149643_4_) & 0x3;
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return ((p_149691_2_ & 0x3) == 0x1) ? this.field_150129_M[this.field_150127_b][1] : this.field_150129_M[this.field_150127_b][0];
    }
    
    @Override
    public void getSubBlocks(final Item p_149666_1_, final CreativeTabs p_149666_2_, final List p_149666_3_) {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        for (int var2 = 0; var2 < BlockNewLeaf.field_150132_N.length; ++var2) {
            this.field_150129_M[var2] = new IIcon[BlockNewLeaf.field_150132_N[var2].length];
            for (int var3 = 0; var3 < BlockNewLeaf.field_150132_N[var2].length; ++var3) {
                this.field_150129_M[var2][var3] = p_149651_1_.registerIcon(BlockNewLeaf.field_150132_N[var2][var3]);
            }
        }
    }
    
    @Override
    public String[] func_150125_e() {
        return BlockNewLeaf.field_150133_O;
    }
    
    static {
        field_150132_N = new String[][] { { "leaves_acacia", "leaves_big_oak" }, { "leaves_acacia_opaque", "leaves_big_oak_opaque" } };
        field_150133_O = new String[] { "acacia", "big_oak" };
    }
}
