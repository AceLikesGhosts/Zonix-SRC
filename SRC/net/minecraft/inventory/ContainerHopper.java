package net.minecraft.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class ContainerHopper extends Container
{
    private final IInventory field_94538_a;
    private static final String __OBFID = "CL_00001750";
    
    public ContainerHopper(final InventoryPlayer p_i1814_1_, final IInventory p_i1814_2_) {
        (this.field_94538_a = p_i1814_2_).openInventory();
        final byte var3 = 51;
        for (int var4 = 0; var4 < p_i1814_2_.getSizeInventory(); ++var4) {
            this.addSlotToContainer(new Slot(p_i1814_2_, var4, 44 + var4 * 18, 20));
        }
        for (int var4 = 0; var4 < 3; ++var4) {
            for (int var5 = 0; var5 < 9; ++var5) {
                this.addSlotToContainer(new Slot(p_i1814_1_, var5 + var4 * 9 + 9, 8 + var5 * 18, var4 * 18 + var3));
            }
        }
        for (int var4 = 0; var4 < 9; ++var4) {
            this.addSlotToContainer(new Slot(p_i1814_1_, var4, 8 + var4 * 18, 58 + var3));
        }
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer p_75145_1_) {
        return this.field_94538_a.isUseableByPlayer(p_75145_1_);
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer p_82846_1_, final int p_82846_2_) {
        ItemStack var3 = null;
        final Slot var4 = this.inventorySlots.get(p_82846_2_);
        if (var4 != null && var4.getHasStack()) {
            final ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            if (p_82846_2_ < this.field_94538_a.getSizeInventory()) {
                if (!this.mergeItemStack(var5, this.field_94538_a.getSizeInventory(), this.inventorySlots.size(), true)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var5, 0, this.field_94538_a.getSizeInventory(), false)) {
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
        this.field_94538_a.closeInventory();
    }
}
