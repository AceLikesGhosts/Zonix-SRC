package net.minecraft.inventory;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.enchantment.*;
import java.util.*;

public class ContainerEnchantment extends Container
{
    public IInventory tableInventory;
    private World worldPointer;
    private int posX;
    private int posY;
    private int posZ;
    private Random rand;
    public long nameSeed;
    public int[] enchantLevels;
    private static final String __OBFID = "CL_00001745";
    
    public ContainerEnchantment(final InventoryPlayer p_i1811_1_, final World p_i1811_2_, final int p_i1811_3_, final int p_i1811_4_, final int p_i1811_5_) {
        this.tableInventory = new InventoryBasic("Enchant", true, 1) {
            private static final String __OBFID = "CL_00001746";
            
            @Override
            public int getInventoryStackLimit() {
                return 1;
            }
            
            @Override
            public void onInventoryChanged() {
                super.onInventoryChanged();
                ContainerEnchantment.this.onCraftMatrixChanged(this);
            }
        };
        this.rand = new Random();
        this.enchantLevels = new int[3];
        this.worldPointer = p_i1811_2_;
        this.posX = p_i1811_3_;
        this.posY = p_i1811_4_;
        this.posZ = p_i1811_5_;
        this.addSlotToContainer(new Slot(this.tableInventory, 0, 25, 47) {
            private static final String __OBFID = "CL_00001747";
            
            @Override
            public boolean isItemValid(final ItemStack p_75214_1_) {
                return true;
            }
        });
        for (int var6 = 0; var6 < 3; ++var6) {
            for (int var7 = 0; var7 < 9; ++var7) {
                this.addSlotToContainer(new Slot(p_i1811_1_, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
            }
        }
        for (int var6 = 0; var6 < 9; ++var6) {
            this.addSlotToContainer(new Slot(p_i1811_1_, var6, 8 + var6 * 18, 142));
        }
    }
    
    @Override
    public void addCraftingToCrafters(final ICrafting p_75132_1_) {
        super.addCraftingToCrafters(p_75132_1_);
        p_75132_1_.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
        p_75132_1_.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
        p_75132_1_.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
    }
    
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int var1 = 0; var1 < this.crafters.size(); ++var1) {
            final ICrafting var2 = this.crafters.get(var1);
            var2.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
            var2.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
            var2.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
        }
    }
    
    @Override
    public void updateProgressBar(final int p_75137_1_, final int p_75137_2_) {
        if (p_75137_1_ >= 0 && p_75137_1_ <= 2) {
            this.enchantLevels[p_75137_1_] = p_75137_2_;
        }
        else {
            super.updateProgressBar(p_75137_1_, p_75137_2_);
        }
    }
    
    @Override
    public void onCraftMatrixChanged(final IInventory p_75130_1_) {
        if (p_75130_1_ == this.tableInventory) {
            final ItemStack var2 = p_75130_1_.getStackInSlot(0);
            if (var2 != null && var2.isItemEnchantable()) {
                this.nameSeed = this.rand.nextLong();
                if (!this.worldPointer.isClient) {
                    int var3 = 0;
                    for (int var4 = -1; var4 <= 1; ++var4) {
                        for (int var5 = -1; var5 <= 1; ++var5) {
                            if ((var4 != 0 || var5 != 0) && this.worldPointer.isAirBlock(this.posX + var5, this.posY, this.posZ + var4) && this.worldPointer.isAirBlock(this.posX + var5, this.posY + 1, this.posZ + var4)) {
                                if (this.worldPointer.getBlock(this.posX + var5 * 2, this.posY, this.posZ + var4 * 2) == Blocks.bookshelf) {
                                    ++var3;
                                }
                                if (this.worldPointer.getBlock(this.posX + var5 * 2, this.posY + 1, this.posZ + var4 * 2) == Blocks.bookshelf) {
                                    ++var3;
                                }
                                if (var5 != 0 && var4 != 0) {
                                    if (this.worldPointer.getBlock(this.posX + var5 * 2, this.posY, this.posZ + var4) == Blocks.bookshelf) {
                                        ++var3;
                                    }
                                    if (this.worldPointer.getBlock(this.posX + var5 * 2, this.posY + 1, this.posZ + var4) == Blocks.bookshelf) {
                                        ++var3;
                                    }
                                    if (this.worldPointer.getBlock(this.posX + var5, this.posY, this.posZ + var4 * 2) == Blocks.bookshelf) {
                                        ++var3;
                                    }
                                    if (this.worldPointer.getBlock(this.posX + var5, this.posY + 1, this.posZ + var4 * 2) == Blocks.bookshelf) {
                                        ++var3;
                                    }
                                }
                            }
                        }
                    }
                    for (int var4 = 0; var4 < 3; ++var4) {
                        this.enchantLevels[var4] = EnchantmentHelper.calcItemStackEnchantability(this.rand, var4, var3, var2);
                    }
                    this.detectAndSendChanges();
                }
            }
            else {
                for (int var3 = 0; var3 < 3; ++var3) {
                    this.enchantLevels[var3] = 0;
                }
            }
        }
    }
    
    @Override
    public boolean enchantItem(final EntityPlayer p_75140_1_, final int p_75140_2_) {
        final ItemStack var3 = this.tableInventory.getStackInSlot(0);
        if (this.enchantLevels[p_75140_2_] > 0 && var3 != null && (p_75140_1_.experienceLevel >= this.enchantLevels[p_75140_2_] || p_75140_1_.capabilities.isCreativeMode)) {
            if (!this.worldPointer.isClient) {
                final List var4 = EnchantmentHelper.buildEnchantmentList(this.rand, var3, this.enchantLevels[p_75140_2_]);
                final boolean var5 = var3.getItem() == Items.book;
                if (var4 != null) {
                    p_75140_1_.addExperienceLevel(-this.enchantLevels[p_75140_2_]);
                    if (var5) {
                        var3.func_150996_a(Items.enchanted_book);
                    }
                    final int var6 = (var5 && var4.size() > 1) ? this.rand.nextInt(var4.size()) : -1;
                    for (int var7 = 0; var7 < var4.size(); ++var7) {
                        final EnchantmentData var8 = var4.get(var7);
                        if (!var5 || var7 != var6) {
                            if (var5) {
                                Items.enchanted_book.addEnchantment(var3, var8);
                            }
                            else {
                                var3.addEnchantment(var8.enchantmentobj, var8.enchantmentLevel);
                            }
                        }
                    }
                    this.onCraftMatrixChanged(this.tableInventory);
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void onContainerClosed(final EntityPlayer p_75134_1_) {
        super.onContainerClosed(p_75134_1_);
        if (!this.worldPointer.isClient) {
            final ItemStack var2 = this.tableInventory.getStackInSlotOnClosing(0);
            if (var2 != null) {
                p_75134_1_.dropPlayerItemWithRandomChoice(var2, false);
            }
        }
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer p_75145_1_) {
        return this.worldPointer.getBlock(this.posX, this.posY, this.posZ) == Blocks.enchanting_table && p_75145_1_.getDistanceSq(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5) <= 64.0;
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer p_82846_1_, final int p_82846_2_) {
        ItemStack var3 = null;
        final Slot var4 = this.inventorySlots.get(p_82846_2_);
        if (var4 != null && var4.getHasStack()) {
            final ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            if (p_82846_2_ == 0) {
                if (!this.mergeItemStack(var5, 1, 37, true)) {
                    return null;
                }
            }
            else {
                if (this.inventorySlots.get(0).getHasStack() || !this.inventorySlots.get(0).isItemValid(var5)) {
                    return null;
                }
                if (var5.hasTagCompound() && var5.stackSize == 1) {
                    this.inventorySlots.get(0).putStack(var5.copy());
                    var5.stackSize = 0;
                }
                else if (var5.stackSize >= 1) {
                    this.inventorySlots.get(0).putStack(new ItemStack(var5.getItem(), 1, var5.getItemDamage()));
                    final ItemStack itemStack = var5;
                    --itemStack.stackSize;
                }
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
