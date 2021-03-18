package net.minecraft.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.village.*;

public class SlotMerchantResult extends Slot
{
    private final InventoryMerchant theMerchantInventory;
    private EntityPlayer thePlayer;
    private int field_75231_g;
    private final IMerchant theMerchant;
    private static final String __OBFID = "CL_00001758";
    
    public SlotMerchantResult(final EntityPlayer p_i1822_1_, final IMerchant p_i1822_2_, final InventoryMerchant p_i1822_3_, final int p_i1822_4_, final int p_i1822_5_, final int p_i1822_6_) {
        super(p_i1822_3_, p_i1822_4_, p_i1822_5_, p_i1822_6_);
        this.thePlayer = p_i1822_1_;
        this.theMerchant = p_i1822_2_;
        this.theMerchantInventory = p_i1822_3_;
    }
    
    @Override
    public boolean isItemValid(final ItemStack p_75214_1_) {
        return false;
    }
    
    @Override
    public ItemStack decrStackSize(final int p_75209_1_) {
        if (this.getHasStack()) {
            this.field_75231_g += Math.min(p_75209_1_, this.getStack().stackSize);
        }
        return super.decrStackSize(p_75209_1_);
    }
    
    @Override
    protected void onCrafting(final ItemStack p_75210_1_, final int p_75210_2_) {
        this.field_75231_g += p_75210_2_;
        this.onCrafting(p_75210_1_);
    }
    
    @Override
    protected void onCrafting(final ItemStack p_75208_1_) {
        p_75208_1_.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.field_75231_g);
        this.field_75231_g = 0;
    }
    
    @Override
    public void onPickupFromSlot(final EntityPlayer p_82870_1_, final ItemStack p_82870_2_) {
        this.onCrafting(p_82870_2_);
        final MerchantRecipe var3 = this.theMerchantInventory.getCurrentRecipe();
        if (var3 != null) {
            ItemStack var4 = this.theMerchantInventory.getStackInSlot(0);
            ItemStack var5 = this.theMerchantInventory.getStackInSlot(1);
            if (this.func_75230_a(var3, var4, var5) || this.func_75230_a(var3, var5, var4)) {
                this.theMerchant.useRecipe(var3);
                if (var4 != null && var4.stackSize <= 0) {
                    var4 = null;
                }
                if (var5 != null && var5.stackSize <= 0) {
                    var5 = null;
                }
                this.theMerchantInventory.setInventorySlotContents(0, var4);
                this.theMerchantInventory.setInventorySlotContents(1, var5);
            }
        }
    }
    
    private boolean func_75230_a(final MerchantRecipe p_75230_1_, final ItemStack p_75230_2_, final ItemStack p_75230_3_) {
        final ItemStack var4 = p_75230_1_.getItemToBuy();
        final ItemStack var5 = p_75230_1_.getSecondItemToBuy();
        if (p_75230_2_ != null && p_75230_2_.getItem() == var4.getItem()) {
            if (var5 != null && p_75230_3_ != null && var5.getItem() == p_75230_3_.getItem()) {
                p_75230_2_.stackSize -= var4.stackSize;
                p_75230_3_.stackSize -= var5.stackSize;
                return true;
            }
            if (var5 == null && p_75230_3_ == null) {
                p_75230_2_.stackSize -= var4.stackSize;
                return true;
            }
        }
        return false;
    }
}
