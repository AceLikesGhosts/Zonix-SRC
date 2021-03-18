package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.item.*;
import java.util.*;

public class BlockStainedGlass extends BlockBreakable
{
    private static final IIcon[] field_149998_a;
    private static final String __OBFID = "CL_00000312";
    
    public BlockStainedGlass(final Material p_i45427_1_) {
        super("glass", p_i45427_1_, false);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return BlockStainedGlass.field_149998_a[p_149691_2_ % BlockStainedGlass.field_149998_a.length];
    }
    
    @Override
    public int damageDropped(final int p_149692_1_) {
        return p_149692_1_;
    }
    
    public static int func_149997_b(final int p_149997_0_) {
        return ~p_149997_0_ & 0xF;
    }
    
    @Override
    public void getSubBlocks(final Item p_149666_1_, final CreativeTabs p_149666_2_, final List p_149666_3_) {
        for (int var4 = 0; var4 < BlockStainedGlass.field_149998_a.length; ++var4) {
            p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
        }
    }
    
    @Override
    public int getRenderBlockPass() {
        return 1;
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        for (int var2 = 0; var2 < BlockStainedGlass.field_149998_a.length; ++var2) {
            BlockStainedGlass.field_149998_a[var2] = p_149651_1_.registerIcon(this.getTextureName() + "_" + ItemDye.field_150921_b[func_149997_b(var2)]);
        }
    }
    
    @Override
    public int quantityDropped(final Random p_149745_1_) {
        return 0;
    }
    
    @Override
    protected boolean canSilkHarvest() {
        return true;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    static {
        field_149998_a = new IIcon[16];
    }
}
