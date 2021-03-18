package net.minecraft.inventory;

import net.minecraft.item.*;
import net.minecraft.entity.player.*;

public interface IInventory
{
    int getSizeInventory();
    
    ItemStack getStackInSlot(final int p0);
    
    ItemStack decrStackSize(final int p0, final int p1);
    
    ItemStack getStackInSlotOnClosing(final int p0);
    
    void setInventorySlotContents(final int p0, final ItemStack p1);
    
    String getInventoryName();
    
    boolean isInventoryNameLocalized();
    
    int getInventoryStackLimit();
    
    void onInventoryChanged();
    
    boolean isUseableByPlayer(final EntityPlayer p0);
    
    void openInventory();
    
    void closeInventory();
    
    boolean isItemValidForSlot(final int p0, final ItemStack p1);
}
