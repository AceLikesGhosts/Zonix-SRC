package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.block.material.*;

public class BlockQuartz extends Block
{
    public static final String[] field_150191_a;
    private static final String[] field_150189_b;
    private IIcon[] field_150192_M;
    private IIcon field_150193_N;
    private IIcon field_150194_O;
    private IIcon field_150190_P;
    private IIcon field_150188_Q;
    private static final String __OBFID = "CL_00000292";
    
    public BlockQuartz() {
        super(Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, int p_149691_2_) {
        if (p_149691_2_ == 2 || p_149691_2_ == 3 || p_149691_2_ == 4) {
            return (p_149691_2_ == 2 && (p_149691_1_ == 1 || p_149691_1_ == 0)) ? this.field_150194_O : ((p_149691_2_ == 3 && (p_149691_1_ == 5 || p_149691_1_ == 4)) ? this.field_150194_O : ((p_149691_2_ == 4 && (p_149691_1_ == 2 || p_149691_1_ == 3)) ? this.field_150194_O : this.field_150192_M[p_149691_2_]));
        }
        if (p_149691_1_ == 1 || (p_149691_1_ == 0 && p_149691_2_ == 1)) {
            return (p_149691_2_ == 1) ? this.field_150193_N : this.field_150190_P;
        }
        if (p_149691_1_ == 0) {
            return this.field_150188_Q;
        }
        if (p_149691_2_ < 0 || p_149691_2_ >= this.field_150192_M.length) {
            p_149691_2_ = 0;
        }
        return this.field_150192_M[p_149691_2_];
    }
    
    @Override
    public int onBlockPlaced(final World p_149660_1_, final int p_149660_2_, final int p_149660_3_, final int p_149660_4_, final int p_149660_5_, final float p_149660_6_, final float p_149660_7_, final float p_149660_8_, int p_149660_9_) {
        if (p_149660_9_ == 2) {
            switch (p_149660_5_) {
                case 0:
                case 1: {
                    p_149660_9_ = 2;
                    break;
                }
                case 2:
                case 3: {
                    p_149660_9_ = 4;
                    break;
                }
                case 4:
                case 5: {
                    p_149660_9_ = 3;
                    break;
                }
            }
        }
        return p_149660_9_;
    }
    
    @Override
    public int damageDropped(final int p_149692_1_) {
        return (p_149692_1_ != 3 && p_149692_1_ != 4) ? p_149692_1_ : 2;
    }
    
    @Override
    protected ItemStack createStackedBlock(final int p_149644_1_) {
        return (p_149644_1_ != 3 && p_149644_1_ != 4) ? super.createStackedBlock(p_149644_1_) : new ItemStack(Item.getItemFromBlock(this), 1, 2);
    }
    
    @Override
    public int getRenderType() {
        return 39;
    }
    
    @Override
    public void getSubBlocks(final Item p_149666_1_, final CreativeTabs p_149666_2_, final List p_149666_3_) {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 2));
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.field_150192_M = new IIcon[BlockQuartz.field_150189_b.length];
        for (int var2 = 0; var2 < this.field_150192_M.length; ++var2) {
            if (BlockQuartz.field_150189_b[var2] == null) {
                this.field_150192_M[var2] = this.field_150192_M[var2 - 1];
            }
            else {
                this.field_150192_M[var2] = p_149651_1_.registerIcon(this.getTextureName() + "_" + BlockQuartz.field_150189_b[var2]);
            }
        }
        this.field_150190_P = p_149651_1_.registerIcon(this.getTextureName() + "_top");
        this.field_150193_N = p_149651_1_.registerIcon(this.getTextureName() + "_chiseled_top");
        this.field_150194_O = p_149651_1_.registerIcon(this.getTextureName() + "_lines_top");
        this.field_150188_Q = p_149651_1_.registerIcon(this.getTextureName() + "_bottom");
    }
    
    @Override
    public MapColor getMapColor(final int p_149728_1_) {
        return MapColor.field_151677_p;
    }
    
    static {
        field_150191_a = new String[] { "default", "chiseled", "lines" };
        field_150189_b = new String[] { "side", "chiseled", "lines", null, null };
    }
}
