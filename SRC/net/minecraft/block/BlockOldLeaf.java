package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;

public class BlockOldLeaf extends BlockLeaves
{
    public static final String[][] field_150130_N;
    public static final String[] field_150131_O;
    private static final String __OBFID = "CL_00000280";
    
    @Override
    public int getRenderColor(final int p_149741_1_) {
        return ((p_149741_1_ & 0x3) == 0x1) ? ColorizerFoliage.getFoliageColorPine() : (((p_149741_1_ & 0x3) == 0x2) ? ColorizerFoliage.getFoliageColorBirch() : super.getRenderColor(p_149741_1_));
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess p_149720_1_, final int p_149720_2_, final int p_149720_3_, final int p_149720_4_) {
        final int var5 = p_149720_1_.getBlockMetadata(p_149720_2_, p_149720_3_, p_149720_4_);
        return ((var5 & 0x3) == 0x1) ? ColorizerFoliage.getFoliageColorPine() : (((var5 & 0x3) == 0x2) ? ColorizerFoliage.getFoliageColorBirch() : super.colorMultiplier(p_149720_1_, p_149720_2_, p_149720_3_, p_149720_4_));
    }
    
    @Override
    protected void func_150124_c(final World p_150124_1_, final int p_150124_2_, final int p_150124_3_, final int p_150124_4_, final int p_150124_5_, final int p_150124_6_) {
        if ((p_150124_5_ & 0x3) == 0x0 && p_150124_1_.rand.nextInt(p_150124_6_) == 0) {
            this.dropBlockAsItem_do(p_150124_1_, p_150124_2_, p_150124_3_, p_150124_4_, new ItemStack(Items.apple, 1, 0));
        }
    }
    
    @Override
    protected int func_150123_b(final int p_150123_1_) {
        int var2 = super.func_150123_b(p_150123_1_);
        if ((p_150123_1_ & 0x3) == 0x3) {
            var2 = 40;
        }
        return var2;
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return ((p_149691_2_ & 0x3) == 0x1) ? this.field_150129_M[this.field_150127_b][1] : (((p_149691_2_ & 0x3) == 0x3) ? this.field_150129_M[this.field_150127_b][3] : (((p_149691_2_ & 0x3) == 0x2) ? this.field_150129_M[this.field_150127_b][2] : this.field_150129_M[this.field_150127_b][0]));
    }
    
    @Override
    public void getSubBlocks(final Item p_149666_1_, final CreativeTabs p_149666_2_, final List p_149666_3_) {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 2));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 3));
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        for (int var2 = 0; var2 < BlockOldLeaf.field_150130_N.length; ++var2) {
            this.field_150129_M[var2] = new IIcon[BlockOldLeaf.field_150130_N[var2].length];
            for (int var3 = 0; var3 < BlockOldLeaf.field_150130_N[var2].length; ++var3) {
                this.field_150129_M[var2][var3] = p_149651_1_.registerIcon(BlockOldLeaf.field_150130_N[var2][var3]);
            }
        }
    }
    
    @Override
    public String[] func_150125_e() {
        return BlockOldLeaf.field_150131_O;
    }
    
    static {
        field_150130_N = new String[][] { { "leaves_oak", "leaves_spruce", "leaves_birch", "leaves_jungle" }, { "leaves_oak_opaque", "leaves_spruce_opaque", "leaves_birch_opaque", "leaves_jungle_opaque" } };
        field_150131_O = new String[] { "oak", "spruce", "birch", "jungle" };
    }
}
