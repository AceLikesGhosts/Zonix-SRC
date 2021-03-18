package net.minecraft.inventory;

import net.minecraft.item.*;
import net.minecraft.entity.player.*;

public class InventoryCrafting implements IInventory
{
    private ItemStack[] stackList;
    private int inventoryWidth;
    private Container eventHandler;
    private static final String __OBFID = "CL_00001743";
    
    public InventoryCrafting(final Container p_i1807_1_, final int p_i1807_2_, final int p_i1807_3_) {
        final int var4 = p_i1807_2_ * p_i1807_3_;
        this.stackList = new ItemStack[var4];
        this.eventHandler = p_i1807_1_;
        this.inventoryWidth = p_i1807_2_;
    }
    
    @Override
    public int getSizeInventory() {
        return this.stackList.length;
    }
    
    @Override
    public ItemStack getStackInSlot(final int p_70301_1_) {
        return (p_70301_1_ >= this.getSizeInventory()) ? null : this.stackList[p_70301_1_];
    }
    
    public ItemStack getStackInRowAndColumn(final int p_70463_1_, final int p_70463_2_) {
        if (p_70463_1_ >= 0 && p_70463_1_ < this.inventoryWidth) {
            final int var3 = p_70463_1_ + p_70463_2_ * this.inventoryWidth;
            return this.getStackInSlot(var3);
        }
        return null;
    }
    
    @Override
    public String getInventoryName() {
        return "container.crafting";
    }
    
    @Override
    public boolean isInventoryNameLocalized() {
        return false;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int p_70304_1_) {
        if (this.stackList[p_70304_1_] != null) {
            final ItemStack var2 = this.stackList[p_70304_1_];
            this.stackList[p_70304_1_] = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public ItemStack decrStackSize(final int p_70298_1_, final int p_70298_2_) {
        if (this.stackList[p_70298_1_] == null) {
            return null;
        }
        if (this.stackList[p_70298_1_].stackSize <= p_70298_2_) {
            final ItemStack var3 = this.stackList[p_70298_1_];
            this.stackList[p_70298_1_] = null;
            this.eventHandler.onCraftMatrixChanged(this);
            return var3;
        }
        final ItemStack var3 = this.stackList[p_70298_1_].splitStack(p_70298_2_);
        if (this.stackList[p_70298_1_].stackSize == 0) {
            this.stackList[p_70298_1_] = null;
        }
        this.eventHandler.onCraftMatrixChanged(this);
        return var3;
    }
    
    @Override
    public void setInventorySlotContents(final int p_70299_1_, final ItemStack p_70299_2_) {
        this.stackList[p_70299_1_] = p_70299_2_;
        this.eventHandler.onCraftMatrixChanged(this);
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public void onInventoryChanged() {
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer p_70300_1_) {
        return true;
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
}
