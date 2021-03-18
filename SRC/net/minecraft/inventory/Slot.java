package net.minecraft.inventory;

import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class Slot
{
    private final int slotIndex;
    public final IInventory inventory;
    public int slotNumber;
    public int xDisplayPosition;
    public int yDisplayPosition;
    private static final String __OBFID = "CL_00001762";
    
    public Slot(final IInventory p_i1824_1_, final int p_i1824_2_, final int p_i1824_3_, final int p_i1824_4_) {
        this.inventory = p_i1824_1_;
        this.slotIndex = p_i1824_2_;
        this.xDisplayPosition = p_i1824_3_;
        this.yDisplayPosition = p_i1824_4_;
    }
    
    public void onSlotChange(final ItemStack p_75220_1_, final ItemStack p_75220_2_) {
        if (p_75220_1_ != null && p_75220_2_ != null && p_75220_1_.getItem() == p_75220_2_.getItem()) {
            final int var3 = p_75220_2_.stackSize - p_75220_1_.stackSize;
            if (var3 > 0) {
                this.onCrafting(p_75220_1_, var3);
            }
        }
    }
    
    protected void onCrafting(final ItemStack p_75210_1_, final int p_75210_2_) {
    }
    
    protected void onCrafting(final ItemStack p_75208_1_) {
    }
    
    public void onPickupFromSlot(final EntityPlayer p_82870_1_, final ItemStack p_82870_2_) {
        this.onSlotChanged();
    }
    
    public boolean isItemValid(final ItemStack p_75214_1_) {
        return true;
    }
    
    public ItemStack getStack() {
        return this.inventory.getStackInSlot(this.slotIndex);
    }
    
    public boolean getHasStack() {
        return this.getStack() != null;
    }
    
    public void putStack(final ItemStack p_75215_1_) {
        this.inventory.setInventorySlotContents(this.slotIndex, p_75215_1_);
        this.onSlotChanged();
    }
    
    public void onSlotChanged() {
        this.inventory.onInventoryChanged();
    }
    
    public int getSlotStackLimit() {
        return this.inventory.getInventoryStackLimit();
    }
    
    public IIcon getBackgroundIconIndex() {
        return null;
    }
    
    public ItemStack decrStackSize(final int p_75209_1_) {
        return this.inventory.decrStackSize(this.slotIndex, p_75209_1_);
    }
    
    public boolean isSlotInInventory(final IInventory p_75217_1_, final int p_75217_2_) {
        return p_75217_1_ == this.inventory && p_75217_2_ == this.slotIndex;
    }
    
    public boolean canTakeStack(final EntityPlayer p_82869_1_) {
        return true;
    }
    
    public boolean func_111238_b() {
        return true;
    }
}
