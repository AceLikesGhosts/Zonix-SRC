package net.minecraft.inventory;

import net.minecraft.item.*;
import net.minecraft.entity.player.*;

public class InventoryCraftResult implements IInventory
{
    private ItemStack[] stackResult;
    private static final String __OBFID = "CL_00001760";
    
    public InventoryCraftResult() {
        this.stackResult = new ItemStack[1];
    }
    
    @Override
    public int getSizeInventory() {
        return 1;
    }
    
    @Override
    public ItemStack getStackInSlot(final int p_70301_1_) {
        return this.stackResult[0];
    }
    
    @Override
    public String getInventoryName() {
        return "Result";
    }
    
    @Override
    public boolean isInventoryNameLocalized() {
        return false;
    }
    
    @Override
    public ItemStack decrStackSize(final int p_70298_1_, final int p_70298_2_) {
        if (this.stackResult[0] != null) {
            final ItemStack var3 = this.stackResult[0];
            this.stackResult[0] = null;
            return var3;
        }
        return null;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int p_70304_1_) {
        if (this.stackResult[0] != null) {
            final ItemStack var2 = this.stackResult[0];
            this.stackResult[0] = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(final int p_70299_1_, final ItemStack p_70299_2_) {
        this.stackResult[0] = p_70299_2_;
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
