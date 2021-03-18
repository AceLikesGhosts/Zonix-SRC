package net.minecraft.inventory;

import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.village.*;

public class InventoryMerchant implements IInventory
{
    private final IMerchant theMerchant;
    private ItemStack[] theInventory;
    private final EntityPlayer thePlayer;
    private MerchantRecipe currentRecipe;
    private int currentRecipeIndex;
    private static final String __OBFID = "CL_00001756";
    
    public InventoryMerchant(final EntityPlayer p_i1820_1_, final IMerchant p_i1820_2_) {
        this.theInventory = new ItemStack[3];
        this.thePlayer = p_i1820_1_;
        this.theMerchant = p_i1820_2_;
    }
    
    @Override
    public int getSizeInventory() {
        return this.theInventory.length;
    }
    
    @Override
    public ItemStack getStackInSlot(final int p_70301_1_) {
        return this.theInventory[p_70301_1_];
    }
    
    @Override
    public ItemStack decrStackSize(final int p_70298_1_, final int p_70298_2_) {
        if (this.theInventory[p_70298_1_] == null) {
            return null;
        }
        if (p_70298_1_ == 2) {
            final ItemStack var3 = this.theInventory[p_70298_1_];
            this.theInventory[p_70298_1_] = null;
            return var3;
        }
        if (this.theInventory[p_70298_1_].stackSize <= p_70298_2_) {
            final ItemStack var3 = this.theInventory[p_70298_1_];
            this.theInventory[p_70298_1_] = null;
            if (this.inventoryResetNeededOnSlotChange(p_70298_1_)) {
                this.resetRecipeAndSlots();
            }
            return var3;
        }
        final ItemStack var3 = this.theInventory[p_70298_1_].splitStack(p_70298_2_);
        if (this.theInventory[p_70298_1_].stackSize == 0) {
            this.theInventory[p_70298_1_] = null;
        }
        if (this.inventoryResetNeededOnSlotChange(p_70298_1_)) {
            this.resetRecipeAndSlots();
        }
        return var3;
    }
    
    private boolean inventoryResetNeededOnSlotChange(final int p_70469_1_) {
        return p_70469_1_ == 0 || p_70469_1_ == 1;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int p_70304_1_) {
        if (this.theInventory[p_70304_1_] != null) {
            final ItemStack var2 = this.theInventory[p_70304_1_];
            this.theInventory[p_70304_1_] = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(final int p_70299_1_, final ItemStack p_70299_2_) {
        this.theInventory[p_70299_1_] = p_70299_2_;
        if (p_70299_2_ != null && p_70299_2_.stackSize > this.getInventoryStackLimit()) {
            p_70299_2_.stackSize = this.getInventoryStackLimit();
        }
        if (this.inventoryResetNeededOnSlotChange(p_70299_1_)) {
            this.resetRecipeAndSlots();
        }
    }
    
    @Override
    public String getInventoryName() {
        return "mob.villager";
    }
    
    @Override
    public boolean isInventoryNameLocalized() {
        return false;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer p_70300_1_) {
        return this.theMerchant.getCustomer() == p_70300_1_;
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
    public void onInventoryChanged() {
        this.resetRecipeAndSlots();
    }
    
    public void resetRecipeAndSlots() {
        this.currentRecipe = null;
        ItemStack var1 = this.theInventory[0];
        ItemStack var2 = this.theInventory[1];
        if (var1 == null) {
            var1 = var2;
            var2 = null;
        }
        if (var1 == null) {
            this.setInventorySlotContents(2, null);
        }
        else {
            final MerchantRecipeList var3 = this.theMerchant.getRecipes(this.thePlayer);
            if (var3 != null) {
                MerchantRecipe var4 = var3.canRecipeBeUsed(var1, var2, this.currentRecipeIndex);
                if (var4 != null && !var4.isRecipeDisabled()) {
                    this.currentRecipe = var4;
                    this.setInventorySlotContents(2, var4.getItemToSell().copy());
                }
                else if (var2 != null) {
                    var4 = var3.canRecipeBeUsed(var2, var1, this.currentRecipeIndex);
                    if (var4 != null && !var4.isRecipeDisabled()) {
                        this.currentRecipe = var4;
                        this.setInventorySlotContents(2, var4.getItemToSell().copy());
                    }
                    else {
                        this.setInventorySlotContents(2, null);
                    }
                }
                else {
                    this.setInventorySlotContents(2, null);
                }
            }
        }
        this.theMerchant.func_110297_a_(this.getStackInSlot(2));
    }
    
    public MerchantRecipe getCurrentRecipe() {
        return this.currentRecipe;
    }
    
    public void setCurrentRecipeIndex(final int p_70471_1_) {
        this.currentRecipeIndex = p_70471_1_;
        this.resetRecipeAndSlots();
    }
}
