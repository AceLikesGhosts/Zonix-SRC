package net.minecraft.inventory;

import net.minecraft.item.*;
import net.minecraft.entity.player.*;

public class InventoryLargeChest implements IInventory
{
    private String name;
    private IInventory upperChest;
    private IInventory lowerChest;
    private static final String __OBFID = "CL_00001507";
    
    public InventoryLargeChest(final String p_i1559_1_, IInventory p_i1559_2_, IInventory p_i1559_3_) {
        this.name = p_i1559_1_;
        if (p_i1559_2_ == null) {
            p_i1559_2_ = p_i1559_3_;
        }
        if (p_i1559_3_ == null) {
            p_i1559_3_ = p_i1559_2_;
        }
        this.upperChest = p_i1559_2_;
        this.lowerChest = p_i1559_3_;
    }
    
    @Override
    public int getSizeInventory() {
        return this.upperChest.getSizeInventory() + this.lowerChest.getSizeInventory();
    }
    
    public boolean isPartOfLargeChest(final IInventory p_90010_1_) {
        return this.upperChest == p_90010_1_ || this.lowerChest == p_90010_1_;
    }
    
    @Override
    public String getInventoryName() {
        return this.upperChest.isInventoryNameLocalized() ? this.upperChest.getInventoryName() : (this.lowerChest.isInventoryNameLocalized() ? this.lowerChest.getInventoryName() : this.name);
    }
    
    @Override
    public boolean isInventoryNameLocalized() {
        return this.upperChest.isInventoryNameLocalized() || this.lowerChest.isInventoryNameLocalized();
    }
    
    @Override
    public ItemStack getStackInSlot(final int p_70301_1_) {
        return (p_70301_1_ >= this.upperChest.getSizeInventory()) ? this.lowerChest.getStackInSlot(p_70301_1_ - this.upperChest.getSizeInventory()) : this.upperChest.getStackInSlot(p_70301_1_);
    }
    
    @Override
    public ItemStack decrStackSize(final int p_70298_1_, final int p_70298_2_) {
        return (p_70298_1_ >= this.upperChest.getSizeInventory()) ? this.lowerChest.decrStackSize(p_70298_1_ - this.upperChest.getSizeInventory(), p_70298_2_) : this.upperChest.decrStackSize(p_70298_1_, p_70298_2_);
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int p_70304_1_) {
        return (p_70304_1_ >= this.upperChest.getSizeInventory()) ? this.lowerChest.getStackInSlotOnClosing(p_70304_1_ - this.upperChest.getSizeInventory()) : this.upperChest.getStackInSlotOnClosing(p_70304_1_);
    }
    
    @Override
    public void setInventorySlotContents(final int p_70299_1_, final ItemStack p_70299_2_) {
        if (p_70299_1_ >= this.upperChest.getSizeInventory()) {
            this.lowerChest.setInventorySlotContents(p_70299_1_ - this.upperChest.getSizeInventory(), p_70299_2_);
        }
        else {
            this.upperChest.setInventorySlotContents(p_70299_1_, p_70299_2_);
        }
    }
    
    @Override
    public int getInventoryStackLimit() {
        return this.upperChest.getInventoryStackLimit();
    }
    
    @Override
    public void onInventoryChanged() {
        this.upperChest.onInventoryChanged();
        this.lowerChest.onInventoryChanged();
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer p_70300_1_) {
        return this.upperChest.isUseableByPlayer(p_70300_1_) && this.lowerChest.isUseableByPlayer(p_70300_1_);
    }
    
    @Override
    public void openInventory() {
        this.upperChest.openInventory();
        this.lowerChest.openInventory();
    }
    
    @Override
    public void closeInventory() {
        this.upperChest.closeInventory();
        this.lowerChest.closeInventory();
    }
    
    @Override
    public boolean isItemValidForSlot(final int p_94041_1_, final ItemStack p_94041_2_) {
        return true;
    }
}
