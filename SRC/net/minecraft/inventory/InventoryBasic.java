package net.minecraft.inventory;

import net.minecraft.item.*;
import java.util.*;
import net.minecraft.entity.player.*;

public class InventoryBasic implements IInventory
{
    private String inventoryTitle;
    private int slotsCount;
    private ItemStack[] inventoryContents;
    private List field_70480_d;
    private boolean field_94051_e;
    private static final String __OBFID = "CL_00001514";
    
    public InventoryBasic(final String p_i1561_1_, final boolean p_i1561_2_, final int p_i1561_3_) {
        this.inventoryTitle = p_i1561_1_;
        this.field_94051_e = p_i1561_2_;
        this.slotsCount = p_i1561_3_;
        this.inventoryContents = new ItemStack[p_i1561_3_];
    }
    
    public void func_110134_a(final IInvBasic p_110134_1_) {
        if (this.field_70480_d == null) {
            this.field_70480_d = new ArrayList();
        }
        this.field_70480_d.add(p_110134_1_);
    }
    
    public void func_110132_b(final IInvBasic p_110132_1_) {
        this.field_70480_d.remove(p_110132_1_);
    }
    
    @Override
    public ItemStack getStackInSlot(final int p_70301_1_) {
        return (p_70301_1_ >= 0 && p_70301_1_ < this.inventoryContents.length) ? this.inventoryContents[p_70301_1_] : null;
    }
    
    @Override
    public ItemStack decrStackSize(final int p_70298_1_, final int p_70298_2_) {
        if (this.inventoryContents[p_70298_1_] == null) {
            return null;
        }
        if (this.inventoryContents[p_70298_1_].stackSize <= p_70298_2_) {
            final ItemStack var3 = this.inventoryContents[p_70298_1_];
            this.inventoryContents[p_70298_1_] = null;
            this.onInventoryChanged();
            return var3;
        }
        final ItemStack var3 = this.inventoryContents[p_70298_1_].splitStack(p_70298_2_);
        if (this.inventoryContents[p_70298_1_].stackSize == 0) {
            this.inventoryContents[p_70298_1_] = null;
        }
        this.onInventoryChanged();
        return var3;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int p_70304_1_) {
        if (this.inventoryContents[p_70304_1_] != null) {
            final ItemStack var2 = this.inventoryContents[p_70304_1_];
            this.inventoryContents[p_70304_1_] = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(final int p_70299_1_, final ItemStack p_70299_2_) {
        this.inventoryContents[p_70299_1_] = p_70299_2_;
        if (p_70299_2_ != null && p_70299_2_.stackSize > this.getInventoryStackLimit()) {
            p_70299_2_.stackSize = this.getInventoryStackLimit();
        }
        this.onInventoryChanged();
    }
    
    @Override
    public int getSizeInventory() {
        return this.slotsCount;
    }
    
    @Override
    public String getInventoryName() {
        return this.inventoryTitle;
    }
    
    @Override
    public boolean isInventoryNameLocalized() {
        return this.field_94051_e;
    }
    
    public void func_110133_a(final String p_110133_1_) {
        this.field_94051_e = true;
        this.inventoryTitle = p_110133_1_;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public void onInventoryChanged() {
        if (this.field_70480_d != null) {
            for (int var1 = 0; var1 < this.field_70480_d.size(); ++var1) {
                this.field_70480_d.get(var1).onInventoryChanged(this);
            }
        }
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
