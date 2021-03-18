package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;

public class BlockDirt extends Block
{
    public static final String[] field_150009_a;
    private IIcon field_150008_b;
    private IIcon field_150010_M;
    private static final String __OBFID = "CL_00000228";
    
    protected BlockDirt() {
        super(Material.ground);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        if (p_149691_2_ == 2) {
            if (p_149691_1_ == 1) {
                return this.field_150008_b;
            }
            if (p_149691_1_ != 0) {
                return this.field_150010_M;
            }
        }
        return this.blockIcon;
    }
    
    @Override
    public IIcon getIcon(final IBlockAccess p_149673_1_, final int p_149673_2_, final int p_149673_3_, final int p_149673_4_, final int p_149673_5_) {
        final int var6 = p_149673_1_.getBlockMetadata(p_149673_2_, p_149673_3_, p_149673_4_);
        if (var6 == 2) {
            if (p_149673_5_ == 1) {
                return this.field_150008_b;
            }
            if (p_149673_5_ != 0) {
                final Material var7 = p_149673_1_.getBlock(p_149673_2_, p_149673_3_ + 1, p_149673_4_).getMaterial();
                if (var7 == Material.field_151597_y || var7 == Material.craftedSnow) {
                    return Blocks.grass.getIcon(p_149673_1_, p_149673_2_, p_149673_3_, p_149673_4_, p_149673_5_);
                }
                final Block var8 = p_149673_1_.getBlock(p_149673_2_, p_149673_3_ + 1, p_149673_4_);
                if (var8 != Blocks.dirt && var8 != Blocks.grass) {
                    return this.field_150010_M;
                }
            }
        }
        return this.blockIcon;
    }
    
    @Override
    public int damageDropped(final int p_149692_1_) {
        return 0;
    }
    
    @Override
    protected ItemStack createStackedBlock(int p_149644_1_) {
        if (p_149644_1_ == 1) {
            p_149644_1_ = 0;
        }
        return super.createStackedBlock(p_149644_1_);
    }
    
    @Override
    public void getSubBlocks(final Item p_149666_1_, final CreativeTabs p_149666_2_, final List p_149666_3_) {
        p_149666_3_.add(new ItemStack(this, 1, 0));
        p_149666_3_.add(new ItemStack(this, 1, 2));
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        super.registerBlockIcons(p_149651_1_);
        this.field_150008_b = p_149651_1_.registerIcon(this.getTextureName() + "_podzol_top");
        this.field_150010_M = p_149651_1_.registerIcon(this.getTextureName() + "_podzol_side");
    }
    
    @Override
    public int getDamageValue(final World p_149643_1_, final int p_149643_2_, final int p_149643_3_, final int p_149643_4_) {
        int var5 = p_149643_1_.getBlockMetadata(p_149643_2_, p_149643_3_, p_149643_4_);
        if (var5 == 1) {
            var5 = 0;
        }
        return var5;
    }
    
    static {
        field_150009_a = new String[] { "default", "default", "podzol" };
    }
}
