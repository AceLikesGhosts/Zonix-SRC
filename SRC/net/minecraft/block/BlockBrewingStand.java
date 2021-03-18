package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.client.renderer.texture.*;

public class BlockBrewingStand extends BlockContainer
{
    private Random field_149961_a;
    private IIcon field_149960_b;
    private static final String __OBFID = "CL_00000207";
    
    public BlockBrewingStand() {
        super(Material.iron);
        this.field_149961_a = new Random();
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return 25;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World p_149915_1_, final int p_149915_2_) {
        return new TileEntityBrewingStand();
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public void addCollisionBoxesToList(final World p_149743_1_, final int p_149743_2_, final int p_149743_3_, final int p_149743_4_, final AxisAlignedBB p_149743_5_, final List p_149743_6_, final Entity p_149743_7_) {
        this.setBlockBounds(0.4375f, 0.0f, 0.4375f, 0.5625f, 0.875f, 0.5625f);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        this.setBlockBoundsForItemRender();
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
    }
    
    @Override
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        if (p_149727_1_.isClient) {
            return true;
        }
        final TileEntityBrewingStand var10 = (TileEntityBrewingStand)p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_);
        if (var10 != null) {
            p_149727_5_.func_146098_a(var10);
        }
        return true;
    }
    
    @Override
    public void onBlockPlacedBy(final World p_149689_1_, final int p_149689_2_, final int p_149689_3_, final int p_149689_4_, final EntityLivingBase p_149689_5_, final ItemStack p_149689_6_) {
        if (p_149689_6_.hasDisplayName()) {
            ((TileEntityBrewingStand)p_149689_1_.getTileEntity(p_149689_2_, p_149689_3_, p_149689_4_)).func_145937_a(p_149689_6_.getDisplayName());
        }
    }
    
    @Override
    public void randomDisplayTick(final World p_149734_1_, final int p_149734_2_, final int p_149734_3_, final int p_149734_4_, final Random p_149734_5_) {
        final double var6 = p_149734_2_ + 0.4f + p_149734_5_.nextFloat() * 0.2f;
        final double var7 = p_149734_3_ + 0.7f + p_149734_5_.nextFloat() * 0.3f;
        final double var8 = p_149734_4_ + 0.4f + p_149734_5_.nextFloat() * 0.2f;
        p_149734_1_.spawnParticle("smoke", var6, var7, var8, 0.0, 0.0, 0.0);
    }
    
    @Override
    public void breakBlock(final World p_149749_1_, final int p_149749_2_, final int p_149749_3_, final int p_149749_4_, final Block p_149749_5_, final int p_149749_6_) {
        final TileEntity var7 = p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
        if (var7 instanceof TileEntityBrewingStand) {
            final TileEntityBrewingStand var8 = (TileEntityBrewingStand)var7;
            for (int var9 = 0; var9 < var8.getSizeInventory(); ++var9) {
                final ItemStack var10 = var8.getStackInSlot(var9);
                if (var10 != null) {
                    final float var11 = this.field_149961_a.nextFloat() * 0.8f + 0.1f;
                    final float var12 = this.field_149961_a.nextFloat() * 0.8f + 0.1f;
                    final float var13 = this.field_149961_a.nextFloat() * 0.8f + 0.1f;
                    while (var10.stackSize > 0) {
                        int var14 = this.field_149961_a.nextInt(21) + 10;
                        if (var14 > var10.stackSize) {
                            var14 = var10.stackSize;
                        }
                        final ItemStack itemStack = var10;
                        itemStack.stackSize -= var14;
                        final EntityItem var15 = new EntityItem(p_149749_1_, p_149749_2_ + var11, p_149749_3_ + var12, p_149749_4_ + var13, new ItemStack(var10.getItem(), var14, var10.getItemDamage()));
                        final float var16 = 0.05f;
                        var15.motionX = (float)this.field_149961_a.nextGaussian() * var16;
                        var15.motionY = (float)this.field_149961_a.nextGaussian() * var16 + 0.2f;
                        var15.motionZ = (float)this.field_149961_a.nextGaussian() * var16;
                        p_149749_1_.spawnEntityInWorld(var15);
                    }
                }
            }
        }
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return Items.brewing_stand;
    }
    
    @Override
    public Item getItem(final World p_149694_1_, final int p_149694_2_, final int p_149694_3_, final int p_149694_4_) {
        return Items.brewing_stand;
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(final World p_149736_1_, final int p_149736_2_, final int p_149736_3_, final int p_149736_4_, final int p_149736_5_) {
        return Container.calcRedstoneFromInventory((IInventory)p_149736_1_.getTileEntity(p_149736_2_, p_149736_3_, p_149736_4_));
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        super.registerBlockIcons(p_149651_1_);
        this.field_149960_b = p_149651_1_.registerIcon(this.getTextureName() + "_base");
    }
    
    public IIcon func_149959_e() {
        return this.field_149960_b;
    }
}
