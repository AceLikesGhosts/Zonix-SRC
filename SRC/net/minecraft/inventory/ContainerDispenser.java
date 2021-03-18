package net.minecraft.inventory;

import net.minecraft.tileentity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class ContainerDispenser extends Container
{
    private TileEntityDispenser tileEntityDispenser;
    private static final String __OBFID = "CL_00001763";
    
    public ContainerDispenser(final IInventory p_i1825_1_, final TileEntityDispenser p_i1825_2_) {
        this.tileEntityDispenser = p_i1825_2_;
        for (int var3 = 0; var3 < 3; ++var3) {
            for (int var4 = 0; var4 < 3; ++var4) {
                this.addSlotToContainer(new Slot(p_i1825_2_, var4 + var3 * 3, 62 + var4 * 18, 17 + var3 * 18));
            }
        }
        for (int var3 = 0; var3 < 3; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                this.addSlotToContainer(new Slot(p_i1825_1_, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }
        for (int var3 = 0; var3 < 9; ++var3) {
            this.addSlotToContainer(new Slot(p_i1825_1_, var3, 8 + var3 * 18, 142));
        }
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer p_75145_1_) {
        return this.tileEntityDispenser.isUseableByPlayer(p_75145_1_);
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer p_82846_1_, final int p_82846_2_) {
        ItemStack var3 = null;
        final Slot var4 = this.inventorySlots.get(p_82846_2_);
        if (var4 != null && var4.getHasStack()) {
            final ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            if (p_82846_2_ < 9) {
                if (!this.mergeItemStack(var5, 9, 45, true)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var5, 0, 9, false)) {
                return null;
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
}
