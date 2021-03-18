package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.entity.item.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.inventory.*;
import net.minecraft.client.renderer.texture.*;

public class BlockChest extends BlockContainer
{
    private final Random field_149955_b;
    public final int field_149956_a;
    private static final String __OBFID = "CL_00000214";
    
    protected BlockChest(final int p_i45397_1_) {
        super(Material.wood);
        this.field_149955_b = new Random();
        this.field_149956_a = p_i45397_1_;
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
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        if (p_149719_1_.getBlock(p_149719_2_, p_149719_3_, p_149719_4_ - 1) == this) {
            this.setBlockBounds(0.0625f, 0.0f, 0.0f, 0.9375f, 0.875f, 0.9375f);
        }
        else if (p_149719_1_.getBlock(p_149719_2_, p_149719_3_, p_149719_4_ + 1) == this) {
            this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 1.0f);
        }
        else if (p_149719_1_.getBlock(p_149719_2_ - 1, p_149719_3_, p_149719_4_) == this) {
            this.setBlockBounds(0.0f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
        }
        else if (p_149719_1_.getBlock(p_149719_2_ + 1, p_149719_3_, p_149719_4_) == this) {
            this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 1.0f, 0.875f, 0.9375f);
        }
        else {
            this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
        }
    }
    
    @Override
    public void onBlockAdded(final World p_149726_1_, final int p_149726_2_, final int p_149726_3_, final int p_149726_4_) {
        super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
        this.func_149954_e(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
        final Block var5 = p_149726_1_.getBlock(p_149726_2_, p_149726_3_, p_149726_4_ - 1);
        final Block var6 = p_149726_1_.getBlock(p_149726_2_, p_149726_3_, p_149726_4_ + 1);
        final Block var7 = p_149726_1_.getBlock(p_149726_2_ - 1, p_149726_3_, p_149726_4_);
        final Block var8 = p_149726_1_.getBlock(p_149726_2_ + 1, p_149726_3_, p_149726_4_);
        if (var5 == this) {
            this.func_149954_e(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_ - 1);
        }
        if (var6 == this) {
            this.func_149954_e(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_ + 1);
        }
        if (var7 == this) {
            this.func_149954_e(p_149726_1_, p_149726_2_ - 1, p_149726_3_, p_149726_4_);
        }
        if (var8 == this) {
            this.func_149954_e(p_149726_1_, p_149726_2_ + 1, p_149726_3_, p_149726_4_);
        }
    }
    
    @Override
    public void onBlockPlacedBy(final World p_149689_1_, final int p_149689_2_, final int p_149689_3_, final int p_149689_4_, final EntityLivingBase p_149689_5_, final ItemStack p_149689_6_) {
        final Block var7 = p_149689_1_.getBlock(p_149689_2_, p_149689_3_, p_149689_4_ - 1);
        final Block var8 = p_149689_1_.getBlock(p_149689_2_, p_149689_3_, p_149689_4_ + 1);
        final Block var9 = p_149689_1_.getBlock(p_149689_2_ - 1, p_149689_3_, p_149689_4_);
        final Block var10 = p_149689_1_.getBlock(p_149689_2_ + 1, p_149689_3_, p_149689_4_);
        byte var11 = 0;
        final int var12 = MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        if (var12 == 0) {
            var11 = 2;
        }
        if (var12 == 1) {
            var11 = 5;
        }
        if (var12 == 2) {
            var11 = 3;
        }
        if (var12 == 3) {
            var11 = 4;
        }
        if (var7 != this && var8 != this && var9 != this && var10 != this) {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var11, 3);
        }
        else {
            if ((var7 == this || var8 == this) && (var11 == 4 || var11 == 5)) {
                if (var7 == this) {
                    p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_ - 1, var11, 3);
                }
                else {
                    p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_ + 1, var11, 3);
                }
                p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var11, 3);
            }
            if ((var9 == this || var10 == this) && (var11 == 2 || var11 == 3)) {
                if (var9 == this) {
                    p_149689_1_.setBlockMetadataWithNotify(p_149689_2_ - 1, p_149689_3_, p_149689_4_, var11, 3);
                }
                else {
                    p_149689_1_.setBlockMetadataWithNotify(p_149689_2_ + 1, p_149689_3_, p_149689_4_, var11, 3);
                }
                p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var11, 3);
            }
        }
        if (p_149689_6_.hasDisplayName()) {
            ((TileEntityChest)p_149689_1_.getTileEntity(p_149689_2_, p_149689_3_, p_149689_4_)).func_145976_a(p_149689_6_.getDisplayName());
        }
    }
    
    public void func_149954_e(final World p_149954_1_, final int p_149954_2_, final int p_149954_3_, final int p_149954_4_) {
        if (!p_149954_1_.isClient) {
            final Block var5 = p_149954_1_.getBlock(p_149954_2_, p_149954_3_, p_149954_4_ - 1);
            final Block var6 = p_149954_1_.getBlock(p_149954_2_, p_149954_3_, p_149954_4_ + 1);
            final Block var7 = p_149954_1_.getBlock(p_149954_2_ - 1, p_149954_3_, p_149954_4_);
            final Block var8 = p_149954_1_.getBlock(p_149954_2_ + 1, p_149954_3_, p_149954_4_);
            final boolean var9 = true;
            byte var10;
            if (var5 != this && var6 != this) {
                if (var7 != this && var8 != this) {
                    var10 = 3;
                    if (var5.func_149730_j() && !var6.func_149730_j()) {
                        var10 = 3;
                    }
                    if (var6.func_149730_j() && !var5.func_149730_j()) {
                        var10 = 2;
                    }
                    if (var7.func_149730_j() && !var8.func_149730_j()) {
                        var10 = 5;
                    }
                    if (var8.func_149730_j() && !var7.func_149730_j()) {
                        var10 = 4;
                    }
                }
                else {
                    final int var11 = (var7 == this) ? (p_149954_2_ - 1) : (p_149954_2_ + 1);
                    final Block var12 = p_149954_1_.getBlock(var11, p_149954_3_, p_149954_4_ - 1);
                    final int var13 = (var7 == this) ? (p_149954_2_ - 1) : (p_149954_2_ + 1);
                    final Block var14 = p_149954_1_.getBlock(var13, p_149954_3_, p_149954_4_ + 1);
                    var10 = 3;
                    final boolean var15 = true;
                    int var16;
                    if (var7 == this) {
                        var16 = p_149954_1_.getBlockMetadata(p_149954_2_ - 1, p_149954_3_, p_149954_4_);
                    }
                    else {
                        var16 = p_149954_1_.getBlockMetadata(p_149954_2_ + 1, p_149954_3_, p_149954_4_);
                    }
                    if (var16 == 2) {
                        var10 = 2;
                    }
                    if ((var5.func_149730_j() || var12.func_149730_j()) && !var6.func_149730_j() && !var14.func_149730_j()) {
                        var10 = 3;
                    }
                    if ((var6.func_149730_j() || var14.func_149730_j()) && !var5.func_149730_j() && !var12.func_149730_j()) {
                        var10 = 2;
                    }
                }
            }
            else {
                final int var11 = (var5 == this) ? (p_149954_4_ - 1) : (p_149954_4_ + 1);
                final Block var12 = p_149954_1_.getBlock(p_149954_2_ - 1, p_149954_3_, var11);
                final int var13 = (var5 == this) ? (p_149954_4_ - 1) : (p_149954_4_ + 1);
                final Block var14 = p_149954_1_.getBlock(p_149954_2_ + 1, p_149954_3_, var13);
                var10 = 5;
                final boolean var15 = true;
                int var16;
                if (var5 == this) {
                    var16 = p_149954_1_.getBlockMetadata(p_149954_2_, p_149954_3_, p_149954_4_ - 1);
                }
                else {
                    var16 = p_149954_1_.getBlockMetadata(p_149954_2_, p_149954_3_, p_149954_4_ + 1);
                }
                if (var16 == 4) {
                    var10 = 4;
                }
                if ((var7.func_149730_j() || var12.func_149730_j()) && !var8.func_149730_j() && !var14.func_149730_j()) {
                    var10 = 5;
                }
                if ((var8.func_149730_j() || var14.func_149730_j()) && !var7.func_149730_j() && !var12.func_149730_j()) {
                    var10 = 4;
                }
            }
            p_149954_1_.setBlockMetadataWithNotify(p_149954_2_, p_149954_3_, p_149954_4_, var10, 3);
        }
    }
    
    @Override
    public boolean canPlaceBlockAt(final World p_149742_1_, final int p_149742_2_, final int p_149742_3_, final int p_149742_4_) {
        int var5 = 0;
        if (p_149742_1_.getBlock(p_149742_2_ - 1, p_149742_3_, p_149742_4_) == this) {
            ++var5;
        }
        if (p_149742_1_.getBlock(p_149742_2_ + 1, p_149742_3_, p_149742_4_) == this) {
            ++var5;
        }
        if (p_149742_1_.getBlock(p_149742_2_, p_149742_3_, p_149742_4_ - 1) == this) {
            ++var5;
        }
        if (p_149742_1_.getBlock(p_149742_2_, p_149742_3_, p_149742_4_ + 1) == this) {
            ++var5;
        }
        return var5 <= 1 && !this.func_149952_n(p_149742_1_, p_149742_2_ - 1, p_149742_3_, p_149742_4_) && !this.func_149952_n(p_149742_1_, p_149742_2_ + 1, p_149742_3_, p_149742_4_) && !this.func_149952_n(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_ - 1) && !this.func_149952_n(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_ + 1);
    }
    
    private boolean func_149952_n(final World p_149952_1_, final int p_149952_2_, final int p_149952_3_, final int p_149952_4_) {
        return p_149952_1_.getBlock(p_149952_2_, p_149952_3_, p_149952_4_) == this && (p_149952_1_.getBlock(p_149952_2_ - 1, p_149952_3_, p_149952_4_) == this || p_149952_1_.getBlock(p_149952_2_ + 1, p_149952_3_, p_149952_4_) == this || p_149952_1_.getBlock(p_149952_2_, p_149952_3_, p_149952_4_ - 1) == this || p_149952_1_.getBlock(p_149952_2_, p_149952_3_, p_149952_4_ + 1) == this);
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        super.onNeighborBlockChange(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
        final TileEntityChest var6 = (TileEntityChest)p_149695_1_.getTileEntity(p_149695_2_, p_149695_3_, p_149695_4_);
        if (var6 != null) {
            var6.updateContainingBlockInfo();
        }
    }
    
    @Override
    public void breakBlock(final World p_149749_1_, final int p_149749_2_, final int p_149749_3_, final int p_149749_4_, final Block p_149749_5_, final int p_149749_6_) {
        final TileEntityChest var7 = (TileEntityChest)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
        if (var7 != null) {
            for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8) {
                final ItemStack var9 = var7.getStackInSlot(var8);
                if (var9 != null) {
                    final float var10 = this.field_149955_b.nextFloat() * 0.8f + 0.1f;
                    final float var11 = this.field_149955_b.nextFloat() * 0.8f + 0.1f;
                    final float var12 = this.field_149955_b.nextFloat() * 0.8f + 0.1f;
                    while (var9.stackSize > 0) {
                        int var13 = this.field_149955_b.nextInt(21) + 10;
                        if (var13 > var9.stackSize) {
                            var13 = var9.stackSize;
                        }
                        final ItemStack itemStack = var9;
                        itemStack.stackSize -= var13;
                        final EntityItem var14 = new EntityItem(p_149749_1_, p_149749_2_ + var10, p_149749_3_ + var11, p_149749_4_ + var12, new ItemStack(var9.getItem(), var13, var9.getItemDamage()));
                        final float var15 = 0.05f;
                        var14.motionX = (float)this.field_149955_b.nextGaussian() * var15;
                        var14.motionY = (float)this.field_149955_b.nextGaussian() * var15 + 0.2f;
                        var14.motionZ = (float)this.field_149955_b.nextGaussian() * var15;
                        if (var9.hasTagCompound()) {
                            var14.getEntityItem().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
                        }
                        p_149749_1_.spawnEntityInWorld(var14);
                    }
                }
            }
            p_149749_1_.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
        }
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }
    
    @Override
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        if (p_149727_1_.isClient) {
            return true;
        }
        final IInventory var10 = this.func_149951_m(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);
        if (var10 != null) {
            p_149727_5_.displayGUIChest(var10);
        }
        return true;
    }
    
    public IInventory func_149951_m(final World p_149951_1_, final int p_149951_2_, final int p_149951_3_, final int p_149951_4_) {
        Object var5 = p_149951_1_.getTileEntity(p_149951_2_, p_149951_3_, p_149951_4_);
        if (var5 == null) {
            return null;
        }
        if (p_149951_1_.getBlock(p_149951_2_, p_149951_3_ + 1, p_149951_4_).isNormalCube()) {
            return null;
        }
        if (func_149953_o(p_149951_1_, p_149951_2_, p_149951_3_, p_149951_4_)) {
            return null;
        }
        if (p_149951_1_.getBlock(p_149951_2_ - 1, p_149951_3_, p_149951_4_) == this && (p_149951_1_.getBlock(p_149951_2_ - 1, p_149951_3_ + 1, p_149951_4_).isNormalCube() || func_149953_o(p_149951_1_, p_149951_2_ - 1, p_149951_3_, p_149951_4_))) {
            return null;
        }
        if (p_149951_1_.getBlock(p_149951_2_ + 1, p_149951_3_, p_149951_4_) == this && (p_149951_1_.getBlock(p_149951_2_ + 1, p_149951_3_ + 1, p_149951_4_).isNormalCube() || func_149953_o(p_149951_1_, p_149951_2_ + 1, p_149951_3_, p_149951_4_))) {
            return null;
        }
        if (p_149951_1_.getBlock(p_149951_2_, p_149951_3_, p_149951_4_ - 1) == this && (p_149951_1_.getBlock(p_149951_2_, p_149951_3_ + 1, p_149951_4_ - 1).isNormalCube() || func_149953_o(p_149951_1_, p_149951_2_, p_149951_3_, p_149951_4_ - 1))) {
            return null;
        }
        if (p_149951_1_.getBlock(p_149951_2_, p_149951_3_, p_149951_4_ + 1) == this && (p_149951_1_.getBlock(p_149951_2_, p_149951_3_ + 1, p_149951_4_ + 1).isNormalCube() || func_149953_o(p_149951_1_, p_149951_2_, p_149951_3_, p_149951_4_ + 1))) {
            return null;
        }
        if (p_149951_1_.getBlock(p_149951_2_ - 1, p_149951_3_, p_149951_4_) == this) {
            var5 = new InventoryLargeChest("container.chestDouble", (IInventory)p_149951_1_.getTileEntity(p_149951_2_ - 1, p_149951_3_, p_149951_4_), (IInventory)var5);
        }
        if (p_149951_1_.getBlock(p_149951_2_ + 1, p_149951_3_, p_149951_4_) == this) {
            var5 = new InventoryLargeChest("container.chestDouble", (IInventory)var5, (IInventory)p_149951_1_.getTileEntity(p_149951_2_ + 1, p_149951_3_, p_149951_4_));
        }
        if (p_149951_1_.getBlock(p_149951_2_, p_149951_3_, p_149951_4_ - 1) == this) {
            var5 = new InventoryLargeChest("container.chestDouble", (IInventory)p_149951_1_.getTileEntity(p_149951_2_, p_149951_3_, p_149951_4_ - 1), (IInventory)var5);
        }
        if (p_149951_1_.getBlock(p_149951_2_, p_149951_3_, p_149951_4_ + 1) == this) {
            var5 = new InventoryLargeChest("container.chestDouble", (IInventory)var5, (IInventory)p_149951_1_.getTileEntity(p_149951_2_, p_149951_3_, p_149951_4_ + 1));
        }
        return (IInventory)var5;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World p_149915_1_, final int p_149915_2_) {
        final TileEntityChest var3 = new TileEntityChest();
        return var3;
    }
    
    @Override
    public boolean canProvidePower() {
        return this.field_149956_a == 1;
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess p_149709_1_, final int p_149709_2_, final int p_149709_3_, final int p_149709_4_, final int p_149709_5_) {
        if (!this.canProvidePower()) {
            return 0;
        }
        final int var6 = ((TileEntityChest)p_149709_1_.getTileEntity(p_149709_2_, p_149709_3_, p_149709_4_)).field_145987_o;
        return MathHelper.clamp_int(var6, 0, 15);
    }
    
    @Override
    public int isProvidingStrongPower(final IBlockAccess p_149748_1_, final int p_149748_2_, final int p_149748_3_, final int p_149748_4_, final int p_149748_5_) {
        return (p_149748_5_ == 1) ? this.isProvidingWeakPower(p_149748_1_, p_149748_2_, p_149748_3_, p_149748_4_, p_149748_5_) : 0;
    }
    
    private static boolean func_149953_o(final World p_149953_0_, final int p_149953_1_, final int p_149953_2_, final int p_149953_3_) {
        for (final Entity var5 : p_149953_0_.getEntitiesWithinAABB(EntityOcelot.class, AxisAlignedBB.getBoundingBox(p_149953_1_, p_149953_2_ + 1, p_149953_3_, p_149953_1_ + 1, p_149953_2_ + 2, p_149953_3_ + 1))) {
            final EntityOcelot var6 = (EntityOcelot)var5;
            if (var6.isSitting()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(final World p_149736_1_, final int p_149736_2_, final int p_149736_3_, final int p_149736_4_, final int p_149736_5_) {
        return Container.calcRedstoneFromInventory(this.func_149951_m(p_149736_1_, p_149736_2_, p_149736_3_, p_149736_4_));
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon("planks_oak");
    }
}
