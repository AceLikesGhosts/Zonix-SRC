package net.minecraft.inventory;

import net.minecraft.world.*;
import net.minecraft.item.crafting.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class ContainerWorkbench extends Container
{
    public InventoryCrafting craftMatrix;
    public IInventory craftResult;
    private World worldObj;
    private int posX;
    private int posY;
    private int posZ;
    private static final String __OBFID = "CL_00001744";
    
    public ContainerWorkbench(final InventoryPlayer p_i1808_1_, final World p_i1808_2_, final int p_i1808_3_, final int p_i1808_4_, final int p_i1808_5_) {
        this.craftMatrix = new InventoryCrafting(this, 3, 3);
        this.craftResult = new InventoryCraftResult();
        this.worldObj = p_i1808_2_;
        this.posX = p_i1808_3_;
        this.posY = p_i1808_4_;
        this.posZ = p_i1808_5_;
        this.addSlotToContainer(new SlotCrafting(p_i1808_1_.player, this.craftMatrix, this.craftResult, 0, 124, 35));
        for (int var6 = 0; var6 < 3; ++var6) {
            for (int var7 = 0; var7 < 3; ++var7) {
                this.addSlotToContainer(new Slot(this.craftMatrix, var7 + var6 * 3, 30 + var7 * 18, 17 + var6 * 18));
            }
        }
        for (int var6 = 0; var6 < 3; ++var6) {
            for (int var7 = 0; var7 < 9; ++var7) {
                this.addSlotToContainer(new Slot(p_i1808_1_, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
            }
        }
        for (int var6 = 0; var6 < 9; ++var6) {
            this.addSlotToContainer(new Slot(p_i1808_1_, var6, 8 + var6 * 18, 142));
        }
        this.onCraftMatrixChanged(this.craftMatrix);
    }
    
    @Override
    public void onCraftMatrixChanged(final IInventory p_75130_1_) {
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj));
    }
    
    @Override
    public void onContainerClosed(final EntityPlayer p_75134_1_) {
        super.onContainerClosed(p_75134_1_);
        if (!this.worldObj.isClient) {
            for (int var2 = 0; var2 < 9; ++var2) {
                final ItemStack var3 = this.craftMatrix.getStackInSlotOnClosing(var2);
                if (var3 != null) {
                    p_75134_1_.dropPlayerItemWithRandomChoice(var3, false);
                }
            }
        }
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer p_75145_1_) {
        return this.worldObj.getBlock(this.posX, this.posY, this.posZ) == Blocks.crafting_table && p_75145_1_.getDistanceSq(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5) <= 64.0;
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer p_82846_1_, final int p_82846_2_) {
        ItemStack var3 = null;
        final Slot var4 = this.inventorySlots.get(p_82846_2_);
        if (var4 != null && var4.getHasStack()) {
            final ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            if (p_82846_2_ == 0) {
                if (!this.mergeItemStack(var5, 10, 46, true)) {
                    return null;
                }
                var4.onSlotChange(var5, var3);
            }
            else if (p_82846_2_ >= 10 && p_82846_2_ < 37) {
                if (!this.mergeItemStack(var5, 37, 46, false)) {
                    return null;
                }
            }
            else if (p_82846_2_ >= 37 && p_82846_2_ < 46) {
                if (!this.mergeItemStack(var5, 10, 37, false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var5, 10, 46, false)) {
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
    
    @Override
    public boolean func_94530_a(final ItemStack p_94530_1_, final Slot p_94530_2_) {
        return p_94530_2_.inventory != this.craftResult && super.func_94530_a(p_94530_1_, p_94530_2_);
    }
}
