package net.minecraft.inventory;

import net.minecraft.tileentity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;

public class InventoryEnderChest extends InventoryBasic
{
    private TileEntityEnderChest associatedChest;
    private static final String __OBFID = "CL_00001759";
    
    public InventoryEnderChest() {
        super("container.enderchest", false, 27);
    }
    
    public void func_146031_a(final TileEntityEnderChest p_146031_1_) {
        this.associatedChest = p_146031_1_;
    }
    
    public void loadInventoryFromNBT(final NBTTagList p_70486_1_) {
        for (int var2 = 0; var2 < this.getSizeInventory(); ++var2) {
            this.setInventorySlotContents(var2, null);
        }
        for (int var2 = 0; var2 < p_70486_1_.tagCount(); ++var2) {
            final NBTTagCompound var3 = p_70486_1_.getCompoundTagAt(var2);
            final int var4 = var3.getByte("Slot") & 0xFF;
            if (var4 >= 0 && var4 < this.getSizeInventory()) {
                this.setInventorySlotContents(var4, ItemStack.loadItemStackFromNBT(var3));
            }
        }
    }
    
    public NBTTagList saveInventoryToNBT() {
        final NBTTagList var1 = new NBTTagList();
        for (int var2 = 0; var2 < this.getSizeInventory(); ++var2) {
            final ItemStack var3 = this.getStackInSlot(var2);
            if (var3 != null) {
                final NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var2);
                var3.writeToNBT(var4);
                var1.appendTag(var4);
            }
        }
        return var1;
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer p_70300_1_) {
        return (this.associatedChest == null || this.associatedChest.func_145971_a(p_70300_1_)) && super.isUseableByPlayer(p_70300_1_);
    }
    
    @Override
    public void openInventory() {
        if (this.associatedChest != null) {
            this.associatedChest.func_145969_a();
        }
        super.openInventory();
    }
    
    @Override
    public void closeInventory() {
        if (this.associatedChest != null) {
            this.associatedChest.func_145970_b();
        }
        super.closeInventory();
        this.associatedChest = null;
    }
}
