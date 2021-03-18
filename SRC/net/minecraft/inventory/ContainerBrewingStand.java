package net.minecraft.inventory;

import net.minecraft.tileentity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.stats.*;

public class ContainerBrewingStand extends Container
{
    private TileEntityBrewingStand tileBrewingStand;
    private final Slot theSlot;
    private int brewTime;
    private static final String __OBFID = "CL_00001737";
    
    public ContainerBrewingStand(final InventoryPlayer p_i1805_1_, final TileEntityBrewingStand p_i1805_2_) {
        this.tileBrewingStand = p_i1805_2_;
        this.addSlotToContainer(new Potion(p_i1805_1_.player, p_i1805_2_, 0, 56, 46));
        this.addSlotToContainer(new Potion(p_i1805_1_.player, p_i1805_2_, 1, 79, 53));
        this.addSlotToContainer(new Potion(p_i1805_1_.player, p_i1805_2_, 2, 102, 46));
        this.theSlot = this.addSlotToContainer(new Ingredient(p_i1805_2_, 3, 79, 17));
        for (int var3 = 0; var3 < 3; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                this.addSlotToContainer(new Slot(p_i1805_1_, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }
        for (int var3 = 0; var3 < 9; ++var3) {
            this.addSlotToContainer(new Slot(p_i1805_1_, var3, 8 + var3 * 18, 142));
        }
    }
    
    @Override
    public void addCraftingToCrafters(final ICrafting p_75132_1_) {
        super.addCraftingToCrafters(p_75132_1_);
        p_75132_1_.sendProgressBarUpdate(this, 0, this.tileBrewingStand.func_145935_i());
    }
    
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int var1 = 0; var1 < this.crafters.size(); ++var1) {
            final ICrafting var2 = this.crafters.get(var1);
            if (this.brewTime != this.tileBrewingStand.func_145935_i()) {
                var2.sendProgressBarUpdate(this, 0, this.tileBrewingStand.func_145935_i());
            }
        }
        this.brewTime = this.tileBrewingStand.func_145935_i();
    }
    
    @Override
    public void updateProgressBar(final int p_75137_1_, final int p_75137_2_) {
        if (p_75137_1_ == 0) {
            this.tileBrewingStand.func_145938_d(p_75137_2_);
        }
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer p_75145_1_) {
        return this.tileBrewingStand.isUseableByPlayer(p_75145_1_);
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer p_82846_1_, final int p_82846_2_) {
        ItemStack var3 = null;
        final Slot var4 = this.inventorySlots.get(p_82846_2_);
        if (var4 != null && var4.getHasStack()) {
            final ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            if ((p_82846_2_ < 0 || p_82846_2_ > 2) && p_82846_2_ != 3) {
                if (!this.theSlot.getHasStack() && this.theSlot.isItemValid(var5)) {
                    if (!this.mergeItemStack(var5, 3, 4, false)) {
                        return null;
                    }
                }
                else if (Potion.canHoldPotion(var3)) {
                    if (!this.mergeItemStack(var5, 0, 3, false)) {
                        return null;
                    }
                }
                else if (p_82846_2_ >= 4 && p_82846_2_ < 31) {
                    if (!this.mergeItemStack(var5, 31, 40, false)) {
                        return null;
                    }
                }
                else if (p_82846_2_ >= 31 && p_82846_2_ < 40) {
                    if (!this.mergeItemStack(var5, 4, 31, false)) {
                        return null;
                    }
                }
                else if (!this.mergeItemStack(var5, 4, 40, false)) {
                    return null;
                }
            }
            else {
                if (!this.mergeItemStack(var5, 4, 40, true)) {
                    return null;
                }
                var4.onSlotChange(var5, var3);
            }
            if (var5.stackSize == 0) {
                var4.putStack(null);
            }
            else {
                var4.onSlotChanged();
            }
            if (var5.stackSize == var3.stackSize) {
                return null;
            }
            var4.onPickupFromSlot(p_82846_1_, var5);
        }
        return var3;
    }
    
    class Ingredient extends Slot
    {
        private static final String __OBFID = "CL_00001738";
        
        public Ingredient(final IInventory p_i1803_2_, final int p_i1803_3_, final int p_i1803_4_, final int p_i1803_5_) {
            super(p_i1803_2_, p_i1803_3_, p_i1803_4_, p_i1803_5_);
        }
        
        @Override
        public boolean isItemValid(final ItemStack p_75214_1_) {
            return p_75214_1_ != null && p_75214_1_.getItem().isPotionIngredient(p_75214_1_);
        }
        
        @Override
        public int getSlotStackLimit() {
            return 64;
        }
    }
    
    static class Potion extends Slot
    {
        private EntityPlayer player;
        private static final String __OBFID = "CL_00001740";
        
        public Potion(final EntityPlayer p_i1804_1_, final IInventory p_i1804_2_, final int p_i1804_3_, final int p_i1804_4_, final int p_i1804_5_) {
            super(p_i1804_2_, p_i1804_3_, p_i1804_4_, p_i1804_5_);
            this.player = p_i1804_1_;
        }
        
        @Override
        public boolean isItemValid(final ItemStack p_75214_1_) {
            return canHoldPotion(p_75214_1_);
        }
        
        @Override
        public int getSlotStackLimit() {
            return 1;
        }
        
        @Override
        public void onPickupFromSlot(final EntityPlayer p_82870_1_, final ItemStack p_82870_2_) {
            if (p_82870_2_.getItem() == Items.potionitem && p_82870_2_.getItemDamage() > 0) {
                this.player.addStat(AchievementList.potion, 1);
            }
            super.onPickupFromSlot(p_82870_1_, p_82870_2_);
        }
        
        public static boolean canHoldPotion(final ItemStack p_75243_0_) {
            return p_75243_0_ != null && (p_75243_0_.getItem() == Items.potionitem || p_75243_0_.getItem() == Items.glass_bottle);
        }
    }
}
