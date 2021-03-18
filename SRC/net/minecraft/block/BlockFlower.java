package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class BlockFlower extends BlockBush
{
    private static final String[][] field_149860_M;
    public static final String[] field_149859_a;
    public static final String[] field_149858_b;
    private IIcon[] field_149861_N;
    private int field_149862_O;
    private static final String __OBFID = "CL_00000246";
    
    protected BlockFlower(final int p_i2173_1_) {
        super(Material.plants);
        this.field_149862_O = p_i2173_1_;
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, int p_149691_2_) {
        if (p_149691_2_ >= this.field_149861_N.length) {
            p_149691_2_ = 0;
        }
        return this.field_149861_N[p_149691_2_];
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.field_149861_N = new IIcon[BlockFlower.field_149860_M[this.field_149862_O].length];
        for (int var2 = 0; var2 < this.field_149861_N.length; ++var2) {
            this.field_149861_N[var2] = p_149651_1_.registerIcon(BlockFlower.field_149860_M[this.field_149862_O][var2]);
        }
    }
    
    @Override
    public int damageDropped(final int p_149692_1_) {
        return p_149692_1_;
    }
    
    @Override
    public void getSubBlocks(final Item p_149666_1_, final CreativeTabs p_149666_2_, final List p_149666_3_) {
        for (int var4 = 0; var4 < this.field_149861_N.length; ++var4) {
            p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
        }
    }
    
    public static BlockFlower func_149857_e(final String p_149857_0_) {
        for (final String var4 : BlockFlower.field_149858_b) {
            if (var4.equals(p_149857_0_)) {
                return Blocks.yellow_flower;
            }
        }
        for (final String var4 : BlockFlower.field_149859_a) {
            if (var4.equals(p_149857_0_)) {
                return Blocks.red_flower;
            }
        }
        return null;
    }
    
    public static int func_149856_f(final String p_149856_0_) {
        for (int var1 = 0; var1 < BlockFlower.field_149858_b.length; ++var1) {
            if (BlockFlower.field_149858_b[var1].equals(p_149856_0_)) {
                return var1;
            }
        }
        for (int var1 = 0; var1 < BlockFlower.field_149859_a.length; ++var1) {
            if (BlockFlower.field_149859_a[var1].equals(p_149856_0_)) {
                return var1;
            }
        }
        return 0;
    }
    
    static {
        field_149860_M = new String[][] { { "flower_dandelion" }, { "flower_rose", "flower_blue_orchid", "flower_allium", "flower_houstonia", "flower_tulip_red", "flower_tulip_orange", "flower_tulip_white", "flower_tulip_pink", "flower_oxeye_daisy" } };
        field_149859_a = new String[] { "poppy", "blueOrchid", "allium", "houstonia", "tulipRed", "tulipOrange", "tulipWhite", "tulipPink", "oxeyeDaisy" };
        field_149858_b = new String[] { "dandelion" };
    }
}
