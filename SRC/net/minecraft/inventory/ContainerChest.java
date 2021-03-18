package net.minecraft.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class ContainerChest extends Container
{
    private IInventory lowerChestInventory;
    private int numRows;
    private static final String __OBFID = "CL_00001742";
    
    public ContainerChest(final IInventory p_i1806_1_, final IInventory p_i1806_2_) {
        this.lowerChestInventory = p_i1806_2_;
        this.numRows = p_i1806_2_.getSizeInventory() / 9;
        p_i1806_2_.openInventory();
        final int var3 = (this.numRows - 4) * 18;
        for (int var4 = 0; var4 < this.numRows; ++var4) {
            for (int var5 = 0; var5 < 9; ++var5) {
                this.addSlotToContainer(new Slot(p_i1806_2_, var5 + var4 * 9, 8 + var5 * 18, 18 + var4 * 18));
            }
        }
        for (int var4 = 0; var4 < 3; ++var4) {
            for (int var5 = 0; var5 < 9; ++var5) {
                this.addSlotToContainer(new Slot(p_i1806_1_, var5 + var4 * 9 + 9, 8 + var5 * 18, 103 + var4 * 18 + var3));
            }
        }
        for (int var4 = 0; var4 < 9; ++var4) {
            this.addSlotToContainer(new Slot(p_i1806_1_, var4, 8 + var4 * 18, 161 + var3));
        }
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer p_75145_1_) {
        return this.lowerChestInventory.isUseableByPlayer(p_75145_1_);
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer p_82846_1_, final int p_82846_2_) {
        ItemStack var3 = null;
        final Slot var4 = this.inventorySlots.get(p_82846_2_);
        if (var4 != null && var4.getHasStack()) {
            final ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            if (p_82846_2_ < this.numRows * 9) {
                if (!this.mergeItemStack(var5, this.numRows * 9, this.inventorySlots.size(), true)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var5, 0, this.numRows * 9, false)) {
                return null;
            }
            if (var5.stackSize == 0) {
                var4.putStack(null);
            }
            else {
                var4.onSlotChanged();
            }
        }
        return var3;
    }
    
    @Override
    public void onContainerClosed(final EntityPlayer p_75134_1_) {
        super.onContainerClosed(p_75134_1_);
        this.lowerChestInventory.closeInventory();
    }
    
    public IInventory getLowerChestInventory() {
        return this.lowerChestInventory;
    }
}
