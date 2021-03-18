package net.minecraft.block;

import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.inventory.*;

public class BlockFurnace extends BlockContainer
{
    private final Random field_149933_a;
    private final boolean field_149932_b;
    private static boolean field_149934_M;
    private IIcon field_149935_N;
    private IIcon field_149936_O;
    private static final String __OBFID = "CL_00000248";
    
    protected BlockFurnace(final boolean p_i45407_1_) {
        super(Material.rock);
        this.field_149933_a = new Random();
        this.field_149932_b = p_i45407_1_;
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return Item.getItemFromBlock(Blocks.furnace);
    }
    
    @Override
    public void onBlockAdded(final World p_149726_1_, final int p_149726_2_, final int p_149726_3_, final int p_149726_4_) {
        super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
        this.func_149930_e(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
    }
    
    private void func_149930_e(final World p_149930_1_, final int p_149930_2_, final int p_149930_3_, final int p_149930_4_) {
        if (!p_149930_1_.isClient) {
            final Block var5 = p_149930_1_.getBlock(p_149930_2_, p_149930_3_, p_149930_4_ - 1);
            final Block var6 = p_149930_1_.getBlock(p_149930_2_, p_149930_3_, p_149930_4_ + 1);
            final Block var7 = p_149930_1_.getBlock(p_149930_2_ - 1, p_149930_3_, p_149930_4_);
            final Block var8 = p_149930_1_.getBlock(p_149930_2_ + 1, p_149930_3_, p_149930_4_);
            byte var9 = 3;
            if (var5.func_149730_j() && !var6.func_149730_j()) {
                var9 = 3;
            }
            if (var6.func_149730_j() && !var5.func_149730_j()) {
                var9 = 2;
            }
            if (var7.func_149730_j() && !var8.func_149730_j()) {
                var9 = 5;
            }
            if (var8.func_149730_j() && !var7.func_149730_j()) {
                var9 = 4;
            }
            p_149930_1_.setBlockMetadataWithNotify(p_149930_2_, p_149930_3_, p_149930_4_, var9, 2);
        }
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return (p_149691_1_ == 1) ? this.field_149935_N : ((p_149691_1_ == 0) ? this.field_149935_N : ((p_149691_1_ != p_149691_2_) ? this.blockIcon : this.field_149936_O));
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon("furnace_side");
        this.field_149936_O = p_149651_1_.registerIcon(this.field_149932_b ? "furnace_front_on" : "furnace_front_off");
        this.field_149935_N = p_149651_1_.registerIcon("furnace_top");
    }
    
    @Override
    public void randomDisplayTick(final World p_149734_1_, final int p_149734_2_, final int p_149734_3_, final int p_149734_4_, final Random p_149734_5_) {
        if (this.field_149932_b) {
            final int var6 = p_149734_1_.getBlockMetadata(p_149734_2_, p_149734_3_, p_149734_4_);
            final float var7 = p_149734_2_ + 0.5f;
            final float var8 = p_149734_3_ + 0.0f + p_149734_5_.nextFloat() * 6.0f / 16.0f;
            final float var9 = p_149734_4_ + 0.5f;
            final float var10 = 0.52f;
            final float var11 = p_149734_5_.nextFloat() * 0.6f - 0.3f;
            if (var6 == 4) {
                p_149734_1_.spawnParticle("smoke", var7 - var10, var8, var9 + var11, 0.0, 0.0, 0.0);
                p_149734_1_.spawnParticle("flame", var7 - var10, var8, var9 + var11, 0.0, 0.0, 0.0);
            }
            else if (var6 == 5) {
                p_149734_1_.spawnParticle("smoke", var7 + var10, var8, var9 + var11, 0.0, 0.0, 0.0);
                p_149734_1_.spawnParticle("flame", var7 + var10, var8, var9 + var11, 0.0, 0.0, 0.0);
            }
            else if (var6 == 2) {
                p_149734_1_.spawnParticle("smoke", var7 + var11, var8, var9 - var10, 0.0, 0.0, 0.0);
                p_149734_1_.spawnParticle("flame", var7 + var11, var8, var9 - var10, 0.0, 0.0, 0.0);
            }
            else if (var6 == 3) {
                p_149734_1_.spawnParticle("smoke", var7 + var11, var8, var9 + var10, 0.0, 0.0, 0.0);
                p_149734_1_.spawnParticle("flame", var7 + var11, var8, var9 + var10, 0.0, 0.0, 0.0);
            }
        }
    }
    
    @Override
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        if (p_149727_1_.isClient) {
            return true;
        }
        final TileEntityFurnace var10 = (TileEntityFurnace)p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_);
        if (var10 != null) {
            p_149727_5_.func_146101_a(var10);
        }
        return true;
    }
    
    public static void func_149931_a(final boolean p_149931_0_, final World p_149931_1_, final int p_149931_2_, final int p_149931_3_, final int p_149931_4_) {
        final int var5 = p_149931_1_.getBlockMetadata(p_149931_2_, p_149931_3_, p_149931_4_);
        final TileEntity var6 = p_149931_1_.getTileEntity(p_149931_2_, p_149931_3_, p_149931_4_);
        BlockFurnace.field_149934_M = true;
        if (p_149931_0_) {
            p_149931_1_.setBlock(p_149931_2_, p_149931_3_, p_149931_4_, Blocks.lit_furnace);
        }
        else {
            p_149931_1_.setBlock(p_149931_2_, p_149931_3_, p_149931_4_, Blocks.furnace);
        }
        BlockFurnace.field_149934_M = false;
        p_149931_1_.setBlockMetadataWithNotify(p_149931_2_, p_149931_3_, p_149931_4_, var5, 2);
        if (var6 != null) {
            var6.validate();
            p_149931_1_.setTileEntity(p_149931_2_, p_149931_3_, p_149931_4_, var6);
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(final World p_149915_1_, final int p_149915_2_) {
        return new TileEntityFurnace();
    }
    
    @Override
    public void onBlockPlacedBy(final World p_149689_1_, final int p_149689_2_, final int p_149689_3_, final int p_149689_4_, final EntityLivingBase p_149689_5_, final ItemStack p_149689_6_) {
        final int var7 = MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        if (var7 == 0) {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 2, 2);
        }
        if (var7 == 1) {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 5, 2);
        }
        if (var7 == 2) {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 3, 2);
        }
        if (var7 == 3) {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 4, 2);
        }
        if (p_149689_6_.hasDisplayName()) {
            ((TileEntityFurnace)p_149689_1_.getTileEntity(p_149689_2_, p_149689_3_, p_149689_4_)).func_145951_a(p_149689_6_.getDisplayName());
        }
    }
    
    @Override
    public void breakBlock(final World p_149749_1_, final int p_149749_2_, final int p_149749_3_, final int p_149749_4_, final Block p_149749_5_, final int p_149749_6_) {
        if (!BlockFurnace.field_149934_M) {
            final TileEntityFurnace var7 = (TileEntityFurnace)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
            if (var7 != null) {
                for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8) {
                    final ItemStack var9 = var7.getStackInSlot(var8);
                    if (var9 != null) {
                        final float var10 = this.field_149933_a.nextFloat() * 0.8f + 0.1f;
                        final float var11 = this.field_149933_a.nextFloat() * 0.8f + 0.1f;
                        final float var12 = this.field_149933_a.nextFloat() * 0.8f + 0.1f;
                        while (var9.stackSize > 0) {
                            int var13 = this.field_149933_a.nextInt(21) + 10;
                            if (var13 > var9.stackSize) {
                                var13 = var9.stackSize;
                            }
                            final ItemStack itemStack = var9;
                            itemStack.stackSize -= var13;
                            final EntityItem var14 = new EntityItem(p_149749_1_, p_149749_2_ + var10, p_149749_3_ + var11, p_149749_4_ + var12, new ItemStack(var9.getItem(), var13, var9.getItemDamage()));
                            if (var9.hasTagCompound()) {
                                var14.getEntityItem().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
                            }
                            final float var15 = 0.05f;
                            var14.motionX = (float)this.field_149933_a.nextGaussian() * var15;
                            var14.motionY = (float)this.field_149933_a.nextGaussian() * var15 + 0.2f;
                            var14.motionZ = (float)this.field_149933_a.nextGaussian() * var15;
                            p_149749_1_.spawnEntityInWorld(var14);
                        }
                    }
                }
                p_149749_1_.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
            }
        }
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
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
    public Item getItem(final World p_149694_1_, final int p_149694_2_, final int p_149694_3_, final int p_149694_4_) {
        return Item.getItemFromBlock(Blocks.furnace);
    }
}
