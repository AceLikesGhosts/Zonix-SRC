package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.entity.item.*;

public class BlockAnvil extends BlockFalling
{
    public static final String[] field_149834_a;
    private static final String[] field_149835_N;
    public int field_149833_b;
    private IIcon[] field_149836_O;
    private static final String __OBFID = "CL_00000192";
    
    protected BlockAnvil() {
        super(Material.anvil);
        this.setLightOpacity(0);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        if (this.field_149833_b == 3 && p_149691_1_ == 1) {
            final int var3 = (p_149691_2_ >> 2) % this.field_149836_O.length;
            return this.field_149836_O[var3];
        }
        return this.blockIcon;
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon("anvil_base");
        this.field_149836_O = new IIcon[BlockAnvil.field_149835_N.length];
        for (int var2 = 0; var2 < this.field_149836_O.length; ++var2) {
            this.field_149836_O[var2] = p_149651_1_.registerIcon(BlockAnvil.field_149835_N[var2]);
        }
    }
    
    @Override
    public void onBlockPlacedBy(final World p_149689_1_, final int p_149689_2_, final int p_149689_3_, final int p_149689_4_, final EntityLivingBase p_149689_5_, final ItemStack p_149689_6_) {
        int var7 = MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        final int var8 = p_149689_1_.getBlockMetadata(p_149689_2_, p_149689_3_, p_149689_4_) >> 2;
        var7 = ++var7 % 4;
        if (var7 == 0) {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 0x2 | var8 << 2, 2);
        }
        if (var7 == 1) {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 0x3 | var8 << 2, 2);
        }
        if (var7 == 2) {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 0x0 | var8 << 2, 2);
        }
        if (var7 == 3) {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 0x1 | var8 << 2, 2);
        }
    }
    
    @Override
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        if (p_149727_1_.isClient) {
            return true;
        }
        p_149727_5_.displayGUIAnvil(p_149727_2_, p_149727_3_, p_149727_4_);
        return true;
    }
    
    @Override
    public int getRenderType() {
        return 35;
    }
    
    @Override
    public int damageDropped(final int p_149692_1_) {
        return p_149692_1_ >> 2;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        final int var5 = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_) & 0x3;
        if (var5 != 3 && var5 != 1) {
            this.setBlockBounds(0.125f, 0.0f, 0.0f, 0.875f, 1.0f, 1.0f);
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.125f, 1.0f, 1.0f, 0.875f);
        }
    }
    
    @Override
    public void getSubBlocks(final Item p_149666_1_, final CreativeTabs p_149666_2_, final List p_149666_3_) {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 2));
    }
    
    @Override
    protected void func_149829_a(final EntityFallingBlock p_149829_1_) {
        p_149829_1_.func_145806_a(true);
    }
    
    @Override
    public void func_149828_a(final World p_149828_1_, final int p_149828_2_, final int p_149828_3_, final int p_149828_4_, final int p_149828_5_) {
        p_149828_1_.playAuxSFX(1022, p_149828_2_, p_149828_3_, p_149828_4_, 0);
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess p_149646_1_, final int p_149646_2_, final int p_149646_3_, final int p_149646_4_, final int p_149646_5_) {
        return true;
    }
    
    static {
        field_149834_a = new String[] { "intact", "slightlyDamaged", "veryDamaged" };
        field_149835_N = new String[] { "anvil_top_damaged_0", "anvil_top_damaged_1", "anvil_top_damaged_2" };
    }
}
