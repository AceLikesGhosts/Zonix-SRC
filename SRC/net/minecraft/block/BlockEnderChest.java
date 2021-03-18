package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.tileentity.*;
import net.minecraft.client.renderer.texture.*;

public class BlockEnderChest extends BlockContainer
{
    private static final String __OBFID = "CL_00000238";
    
    protected BlockEnderChest() {
        super(Material.rock);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return 22;
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return Item.getItemFromBlock(Blocks.obsidian);
    }
    
    @Override
    public int quantityDropped(final Random p_149745_1_) {
        return 8;
    }
    
    @Override
    protected boolean canSilkHarvest() {
        return true;
    }
    
    @Override
    public void onBlockPlacedBy(final World p_149689_1_, final int p_149689_2_, final int p_149689_3_, final int p_149689_4_, final EntityLivingBase p_149689_5_, final ItemStack p_149689_6_) {
        byte var7 = 0;
        final int var8 = MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        if (var8 == 0) {
            var7 = 2;
        }
        if (var8 == 1) {
            var7 = 5;
        }
        if (var8 == 2) {
            var7 = 3;
        }
        if (var8 == 3) {
            var7 = 4;
        }
        p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var7, 2);
    }
    
    @Override
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        final InventoryEnderChest var10 = p_149727_5_.getInventoryEnderChest();
        final TileEntityEnderChest var11 = (TileEntityEnderChest)p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_);
        if (var10 == null || var11 == null) {
            return true;
        }
        if (p_149727_1_.getBlock(p_149727_2_, p_149727_3_ + 1, p_149727_4_).isNormalCube()) {
            return true;
        }
        if (p_149727_1_.isClient) {
            return true;
        }
        var10.func_146031_a(var11);
        p_149727_5_.displayGUIChest(var10);
        return true;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World p_149915_1_, final int p_149915_2_) {
        return new TileEntityEnderChest();
    }
    
    @Override
    public void randomDisplayTick(final World p_149734_1_, final int p_149734_2_, final int p_149734_3_, final int p_149734_4_, final Random p_149734_5_) {
        for (int var6 = 0; var6 < 3; ++var6) {
            double var7 = p_149734_2_ + p_149734_5_.nextFloat();
            final double var8 = p_149734_3_ + p_149734_5_.nextFloat();
            var7 = p_149734_4_ + p_149734_5_.nextFloat();
            double var9 = 0.0;
            double var10 = 0.0;
            double var11 = 0.0;
            final int var12 = p_149734_5_.nextInt(2) * 2 - 1;
            final int var13 = p_149734_5_.nextInt(2) * 2 - 1;
            var9 = (p_149734_5_.nextFloat() - 0.5) * 0.125;
            var10 = (p_149734_5_.nextFloat() - 0.5) * 0.125;
            var11 = (p_149734_5_.nextFloat() - 0.5) * 0.125;
            final double var14 = p_149734_4_ + 0.5 + 0.25 * var13;
            var11 = p_149734_5_.nextFloat() * 1.0f * var13;
            final double var15 = p_149734_2_ + 0.5 + 0.25 * var12;
            var9 = p_149734_5_.nextFloat() * 1.0f * var12;
            p_149734_1_.spawnParticle("portal", var15, var8, var14, var9, var10, var11);
        }
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon("obsidian");
    }
}
