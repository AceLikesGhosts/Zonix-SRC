package net.minecraft.tileentity;

import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.item.*;
import net.minecraft.world.*;
import net.minecraft.command.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;

public class TileEntityHopper extends TileEntity implements IHopper
{
    private ItemStack[] field_145900_a;
    private String field_145902_i;
    private int field_145901_j;
    private static final String __OBFID = "CL_00000359";
    
    public TileEntityHopper() {
        this.field_145900_a = new ItemStack[5];
        this.field_145901_j = -1;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
        final NBTTagList var2 = p_145839_1_.getTagList("Items", 10);
        this.field_145900_a = new ItemStack[this.getSizeInventory()];
        if (p_145839_1_.func_150297_b("CustomName", 8)) {
            this.field_145902_i = p_145839_1_.getString("CustomName");
        }
        this.field_145901_j = p_145839_1_.getInteger("TransferCooldown");
        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            final NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            final byte var5 = var4.getByte("Slot");
            if (var5 >= 0 && var5 < this.field_145900_a.length) {
                this.field_145900_a[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
        final NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < this.field_145900_a.length; ++var3) {
            if (this.field_145900_a[var3] != null) {
                final NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.field_145900_a[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }
        p_145841_1_.setTag("Items", var2);
        p_145841_1_.setInteger("TransferCooldown", this.field_145901_j);
        if (this.isInventoryNameLocalized()) {
            p_145841_1_.setString("CustomName", this.field_145902_i);
        }
    }
    
    @Override
    public void onInventoryChanged() {
        super.onInventoryChanged();
    }
    
    @Override
    public int getSizeInventory() {
        return this.field_145900_a.length;
    }
    
    @Override
    public ItemStack getStackInSlot(final int p_70301_1_) {
        return this.field_145900_a[p_70301_1_];
    }
    
    @Override
    public ItemStack decrStackSize(final int p_70298_1_, final int p_70298_2_) {
        if (this.field_145900_a[p_70298_1_] == null) {
            return null;
        }
        if (this.field_145900_a[p_70298_1_].stackSize <= p_70298_2_) {
            final ItemStack var3 = this.field_145900_a[p_70298_1_];
            this.field_145900_a[p_70298_1_] = null;
            return var3;
        }
        final ItemStack var3 = this.field_145900_a[p_70298_1_].splitStack(p_70298_2_);
        if (this.field_145900_a[p_70298_1_].stackSize == 0) {
            this.field_145900_a[p_70298_1_] = null;
        }
        return var3;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int p_70304_1_) {
        if (this.field_145900_a[p_70304_1_] != null) {
            final ItemStack var2 = this.field_145900_a[p_70304_1_];
            this.field_145900_a[p_70304_1_] = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(final int p_70299_1_, final ItemStack p_70299_2_) {
        this.field_145900_a[p_70299_1_] = p_70299_2_;
        if (p_70299_2_ != null && p_70299_2_.stackSize > this.getInventoryStackLimit()) {
            p_70299_2_.stackSize = this.getInventoryStackLimit();
        }
    }
    
    @Override
    public String getInventoryName() {
        return this.isInventoryNameLocalized() ? this.field_145902_i : "container.hopper";
    }
    
    @Override
    public boolean isInventoryNameLocalized() {
        return this.field_145902_i != null && this.field_145902_i.length() > 0;
    }
    
    public void func_145886_a(final String p_145886_1_) {
        this.field_145902_i = p_145886_1_;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer p_70300_1_) {
        return this.worldObj.getTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e) == this && p_70300_1_.getDistanceSq(this.field_145851_c + 0.5, this.field_145848_d + 0.5, this.field_145849_e + 0.5) <= 64.0;
    }
    
    @Override
    public void openInventory() {
    }
    
    @Override
    public void closeInventory() {
    }
    
    @Override
    public boolean isItemValidForSlot(final int p_94041_1_, final ItemStack p_94041_2_) {
        return true;
    }
    
    @Override
    public void updateEntity() {
        if (this.worldObj != null && !this.worldObj.isClient) {
            --this.field_145901_j;
            if (!this.func_145888_j()) {
                this.func_145896_c(0);
                this.func_145887_i();
            }
        }
    }
    
    public boolean func_145887_i() {
        if (this.worldObj != null && !this.worldObj.isClient) {
            if (!this.func_145888_j() && BlockHopper.func_149917_c(this.getBlockMetadata())) {
                boolean var1 = false;
                if (!this.func_152104_k()) {
                    var1 = this.func_145883_k();
                }
                if (!this.func_152105_l()) {
                    var1 = (func_145891_a(this) || var1);
                }
                if (var1) {
                    this.func_145896_c(8);
                    this.onInventoryChanged();
                    return true;
                }
            }
            return false;
        }
        return false;
    }
    
    private boolean func_152104_k() {
        for (final ItemStack var4 : this.field_145900_a) {
            if (var4 != null) {
                return false;
            }
        }
        return true;
    }
    
    private boolean func_152105_l() {
        for (final ItemStack var4 : this.field_145900_a) {
            if (var4 == null || var4.stackSize != var4.getMaxStackSize()) {
                return false;
            }
        }
        return true;
    }
    
    private boolean func_145883_k() {
        final IInventory var1 = this.func_145895_l();
        if (var1 == null) {
            return false;
        }
        final int var2 = Facing.oppositeSide[BlockHopper.func_149918_b(this.getBlockMetadata())];
        if (this.func_152102_a(var1, var2)) {
            return false;
        }
        for (int var3 = 0; var3 < this.getSizeInventory(); ++var3) {
            if (this.getStackInSlot(var3) != null) {
                final ItemStack var4 = this.getStackInSlot(var3).copy();
                final ItemStack var5 = func_145889_a(var1, this.decrStackSize(var3, 1), var2);
                if (var5 == null || var5.stackSize == 0) {
                    var1.onInventoryChanged();
                    return true;
                }
                this.setInventorySlotContents(var3, var4);
            }
        }
        return false;
    }
    
    private boolean func_152102_a(final IInventory p_152102_1_, final int p_152102_2_) {
        if (p_152102_1_ instanceof ISidedInventory && p_152102_2_ > -1) {
            final ISidedInventory var7 = (ISidedInventory)p_152102_1_;
            final int[] var8 = var7.getAccessibleSlotsFromSide(p_152102_2_);
            for (int var9 = 0; var9 < var8.length; ++var9) {
                final ItemStack var10 = var7.getStackInSlot(var8[var9]);
                if (var10 == null || var10.stackSize != var10.getMaxStackSize()) {
                    return false;
                }
            }
        }
        else {
            for (int var11 = p_152102_1_.getSizeInventory(), var12 = 0; var12 < var11; ++var12) {
                final ItemStack var13 = p_152102_1_.getStackInSlot(var12);
                if (var13 == null || var13.stackSize != var13.getMaxStackSize()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private static boolean func_152103_b(final IInventory p_152103_0_, final int p_152103_1_) {
        if (p_152103_0_ instanceof ISidedInventory && p_152103_1_ > -1) {
            final ISidedInventory var5 = (ISidedInventory)p_152103_0_;
            final int[] var6 = var5.getAccessibleSlotsFromSide(p_152103_1_);
            for (int var7 = 0; var7 < var6.length; ++var7) {
                if (var5.getStackInSlot(var6[var7]) != null) {
                    return false;
                }
            }
        }
        else {
            for (int var8 = p_152103_0_.getSizeInventory(), var9 = 0; var9 < var8; ++var9) {
                if (p_152103_0_.getStackInSlot(var9) != null) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static boolean func_145891_a(final IHopper p_145891_0_) {
        final IInventory var1 = func_145884_b(p_145891_0_);
        if (var1 != null) {
            final byte var2 = 0;
            if (func_152103_b(var1, var2)) {
                return false;
            }
            if (var1 instanceof ISidedInventory && var2 > -1) {
                final ISidedInventory var3 = (ISidedInventory)var1;
                final int[] var4 = var3.getAccessibleSlotsFromSide(var2);
                for (int var5 = 0; var5 < var4.length; ++var5) {
                    if (func_145892_a(p_145891_0_, var1, var4[var5], var2)) {
                        return true;
                    }
                }
            }
            else {
                for (int var6 = var1.getSizeInventory(), var7 = 0; var7 < var6; ++var7) {
                    if (func_145892_a(p_145891_0_, var1, var7, var2)) {
                        return true;
                    }
                }
            }
        }
        else {
            final EntityItem var8 = func_145897_a(p_145891_0_.getWorldObj(), p_145891_0_.getXPos(), p_145891_0_.getYPos() + 1.0, p_145891_0_.getZPos());
            if (var8 != null) {
                return func_145898_a(p_145891_0_, var8);
            }
        }
        return false;
    }
    
    private static boolean func_145892_a(final IHopper p_145892_0_, final IInventory p_145892_1_, final int p_145892_2_, final int p_145892_3_) {
        final ItemStack var4 = p_145892_1_.getStackInSlot(p_145892_2_);
        if (var4 != null && func_145890_b(p_145892_1_, var4, p_145892_2_, p_145892_3_)) {
            final ItemStack var5 = var4.copy();
            final ItemStack var6 = func_145889_a(p_145892_0_, p_145892_1_.decrStackSize(p_145892_2_, 1), -1);
            if (var6 == null || var6.stackSize == 0) {
                p_145892_1_.onInventoryChanged();
                return true;
            }
            p_145892_1_.setInventorySlotContents(p_145892_2_, var5);
        }
        return false;
    }
    
    public static boolean func_145898_a(final IInventory p_145898_0_, final EntityItem p_145898_1_) {
        boolean var2 = false;
        if (p_145898_1_ == null) {
            return false;
        }
        final ItemStack var3 = p_145898_1_.getEntityItem().copy();
        final ItemStack var4 = func_145889_a(p_145898_0_, var3, -1);
        if (var4 != null && var4.stackSize != 0) {
            p_145898_1_.setEntityItemStack(var4);
        }
        else {
            var2 = true;
            p_145898_1_.setDead();
        }
        return var2;
    }
    
    public static ItemStack func_145889_a(final IInventory p_145889_0_, ItemStack p_145889_1_, final int p_145889_2_) {
        if (p_145889_0_ instanceof ISidedInventory && p_145889_2_ > -1) {
            final ISidedInventory var6 = (ISidedInventory)p_145889_0_;
            final int[] var7 = var6.getAccessibleSlotsFromSide(p_145889_2_);
            for (int var8 = 0; var8 < var7.length && p_145889_1_ != null && p_145889_1_.stackSize > 0; p_145889_1_ = func_145899_c(p_145889_0_, p_145889_1_, var7[var8], p_145889_2_), ++var8) {}
        }
        else {
            for (int var9 = p_145889_0_.getSizeInventory(), var10 = 0; var10 < var9 && p_145889_1_ != null && p_145889_1_.stackSize > 0; p_145889_1_ = func_145899_c(p_145889_0_, p_145889_1_, var10, p_145889_2_), ++var10) {}
        }
        if (p_145889_1_ != null && p_145889_1_.stackSize == 0) {
            p_145889_1_ = null;
        }
        return p_145889_1_;
    }
    
    private static boolean func_145885_a(final IInventory p_145885_0_, final ItemStack p_145885_1_, final int p_145885_2_, final int p_145885_3_) {
        return p_145885_0_.isItemValidForSlot(p_145885_2_, p_145885_1_) && (!(p_145885_0_ instanceof ISidedInventory) || ((ISidedInventory)p_145885_0_).canInsertItem(p_145885_2_, p_145885_1_, p_145885_3_));
    }
    
    private static boolean func_145890_b(final IInventory p_145890_0_, final ItemStack p_145890_1_, final int p_145890_2_, final int p_145890_3_) {
        return !(p_145890_0_ instanceof ISidedInventory) || ((ISidedInventory)p_145890_0_).canExtractItem(p_145890_2_, p_145890_1_, p_145890_3_);
    }
    
    private static ItemStack func_145899_c(final IInventory p_145899_0_, ItemStack p_145899_1_, final int p_145899_2_, final int p_145899_3_) {
        final ItemStack var4 = p_145899_0_.getStackInSlot(p_145899_2_);
        if (func_145885_a(p_145899_0_, p_145899_1_, p_145899_2_, p_145899_3_)) {
            boolean var5 = false;
            if (var4 == null) {
                p_145899_0_.setInventorySlotContents(p_145899_2_, p_145899_1_);
                p_145899_1_ = null;
                var5 = true;
            }
            else if (func_145894_a(var4, p_145899_1_)) {
                final int var6 = p_145899_1_.getMaxStackSize() - var4.stackSize;
                final int var7 = Math.min(p_145899_1_.stackSize, var6);
                final ItemStack itemStack = p_145899_1_;
                itemStack.stackSize -= var7;
                final ItemStack itemStack2 = var4;
                itemStack2.stackSize += var7;
                var5 = (var7 > 0);
            }
            if (var5) {
                if (p_145899_0_ instanceof TileEntityHopper) {
                    ((TileEntityHopper)p_145899_0_).func_145896_c(8);
                    p_145899_0_.onInventoryChanged();
                }
                p_145899_0_.onInventoryChanged();
            }
        }
        return p_145899_1_;
    }
    
    private IInventory func_145895_l() {
        final int var1 = BlockHopper.func_149918_b(this.getBlockMetadata());
        return func_145893_b(this.getWorldObj(), this.field_145851_c + Facing.offsetsXForSide[var1], this.field_145848_d + Facing.offsetsYForSide[var1], this.field_145849_e + Facing.offsetsZForSide[var1]);
    }
    
    public static IInventory func_145884_b(final IHopper p_145884_0_) {
        return func_145893_b(p_145884_0_.getWorldObj(), p_145884_0_.getXPos(), p_145884_0_.getYPos() + 1.0, p_145884_0_.getZPos());
    }
    
    public static EntityItem func_145897_a(final World p_145897_0_, final double p_145897_1_, final double p_145897_3_, final double p_145897_5_) {
        final List var7 = p_145897_0_.selectEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(p_145897_1_, p_145897_3_, p_145897_5_, p_145897_1_ + 1.0, p_145897_3_ + 1.0, p_145897_5_ + 1.0), IEntitySelector.selectAnything);
        return (var7.size() > 0) ? var7.get(0) : null;
    }
    
    public static IInventory func_145893_b(final World p_145893_0_, final double p_145893_1_, final double p_145893_3_, final double p_145893_5_) {
        IInventory var7 = null;
        final int var8 = MathHelper.floor_double(p_145893_1_);
        final int var9 = MathHelper.floor_double(p_145893_3_);
        final int var10 = MathHelper.floor_double(p_145893_5_);
        final TileEntity var11 = p_145893_0_.getTileEntity(var8, var9, var10);
        if (var11 != null && var11 instanceof IInventory) {
            var7 = (IInventory)var11;
            if (var7 instanceof TileEntityChest) {
                final Block var12 = p_145893_0_.getBlock(var8, var9, var10);
                if (var12 instanceof BlockChest) {
                    var7 = ((BlockChest)var12).func_149951_m(p_145893_0_, var8, var9, var10);
                }
            }
        }
        if (var7 == null) {
            final List var13 = p_145893_0_.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(p_145893_1_, p_145893_3_, p_145893_5_, p_145893_1_ + 1.0, p_145893_3_ + 1.0, p_145893_5_ + 1.0), IEntitySelector.selectInventories);
            if (var13 != null && var13.size() > 0) {
                var7 = var13.get(p_145893_0_.rand.nextInt(var13.size()));
            }
        }
        return var7;
    }
    
    private static boolean func_145894_a(final ItemStack p_145894_0_, final ItemStack p_145894_1_) {
        return p_145894_0_.getItem() == p_145894_1_.getItem() && p_145894_0_.getItemDamage() == p_145894_1_.getItemDamage() && p_145894_0_.stackSize <= p_145894_0_.getMaxStackSize() && ItemStack.areItemStackTagsEqual(p_145894_0_, p_145894_1_);
    }
    
    @Override
    public double getXPos() {
        return this.field_145851_c;
    }
    
    @Override
    public double getYPos() {
        return this.field_145848_d;
    }
    
    @Override
    public double getZPos() {
        return this.field_145849_e;
    }
    
    public void func_145896_c(final int p_145896_1_) {
        this.field_145901_j = p_145896_1_;
    }
    
    public boolean func_145888_j() {
        return this.field_145901_j > 0;
    }
}
