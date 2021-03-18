package net.minecraft.inventory;

import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public abstract class Container
{
    public List inventoryItemStacks;
    public List inventorySlots;
    public int windowId;
    private short transactionID;
    private int field_94535_f;
    private int field_94536_g;
    private final Set field_94537_h;
    protected List crafters;
    private Set playerList;
    private static final String __OBFID = "CL_00001730";
    
    public Container() {
        this.inventoryItemStacks = new ArrayList();
        this.inventorySlots = new ArrayList();
        this.field_94535_f = -1;
        this.field_94537_h = new HashSet();
        this.crafters = new ArrayList();
        this.playerList = new HashSet();
    }
    
    protected Slot addSlotToContainer(final Slot p_75146_1_) {
        p_75146_1_.slotNumber = this.inventorySlots.size();
        this.inventorySlots.add(p_75146_1_);
        this.inventoryItemStacks.add(null);
        return p_75146_1_;
    }
    
    public void addCraftingToCrafters(final ICrafting p_75132_1_) {
        if (this.crafters.contains(p_75132_1_)) {
            throw new IllegalArgumentException("Listener already listening");
        }
        this.crafters.add(p_75132_1_);
        p_75132_1_.sendContainerAndContentsToPlayer(this, this.getInventory());
        this.detectAndSendChanges();
    }
    
    public void removeCraftingFromCrafters(final ICrafting p_82847_1_) {
        this.crafters.remove(p_82847_1_);
    }
    
    public List getInventory() {
        final ArrayList var1 = new ArrayList();
        for (int var2 = 0; var2 < this.inventorySlots.size(); ++var2) {
            var1.add(this.inventorySlots.get(var2).getStack());
        }
        return var1;
    }
    
    public void detectAndSendChanges() {
        for (int var1 = 0; var1 < this.inventorySlots.size(); ++var1) {
            final ItemStack var2 = this.inventorySlots.get(var1).getStack();
            ItemStack var3 = this.inventoryItemStacks.get(var1);
            if (!ItemStack.areItemStacksEqual(var3, var2)) {
                var3 = ((var2 == null) ? null : var2.copy());
                this.inventoryItemStacks.set(var1, var3);
                for (int var4 = 0; var4 < this.crafters.size(); ++var4) {
                    this.crafters.get(var4).sendSlotContents(this, var1, var3);
                }
            }
        }
    }
    
    public boolean enchantItem(final EntityPlayer p_75140_1_, final int p_75140_2_) {
        return false;
    }
    
    public Slot getSlotFromInventory(final IInventory p_75147_1_, final int p_75147_2_) {
        for (int var3 = 0; var3 < this.inventorySlots.size(); ++var3) {
            final Slot var4 = this.inventorySlots.get(var3);
            if (var4.isSlotInInventory(p_75147_1_, p_75147_2_)) {
                return var4;
            }
        }
        return null;
    }
    
    public Slot getSlot(final int p_75139_1_) {
        return this.inventorySlots.get(p_75139_1_);
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer p_82846_1_, final int p_82846_2_) {
        final Slot var3 = this.inventorySlots.get(p_82846_2_);
        return (var3 != null) ? var3.getStack() : null;
    }
    
    public ItemStack slotClick(final int p_75144_1_, final int p_75144_2_, final int p_75144_3_, final EntityPlayer p_75144_4_) {
        ItemStack var5 = null;
        final InventoryPlayer var6 = p_75144_4_.inventory;
        if (p_75144_3_ == 5) {
            final int var7 = this.field_94536_g;
            this.field_94536_g = func_94532_c(p_75144_2_);
            if ((var7 != 1 || this.field_94536_g != 2) && var7 != this.field_94536_g) {
                this.func_94533_d();
            }
            else if (var6.getItemStack() == null) {
                this.func_94533_d();
            }
            else if (this.field_94536_g == 0) {
                this.field_94535_f = func_94529_b(p_75144_2_);
                if (func_94528_d(this.field_94535_f)) {
                    this.field_94536_g = 1;
                    this.field_94537_h.clear();
                }
                else {
                    this.func_94533_d();
                }
            }
            else if (this.field_94536_g == 1) {
                final Slot var8 = this.inventorySlots.get(p_75144_1_);
                if (var8 != null && func_94527_a(var8, var6.getItemStack(), true) && var8.isItemValid(var6.getItemStack()) && var6.getItemStack().stackSize > this.field_94537_h.size() && this.canDragIntoSlot(var8)) {
                    this.field_94537_h.add(var8);
                }
            }
            else if (this.field_94536_g == 2) {
                if (!this.field_94537_h.isEmpty()) {
                    ItemStack var9 = var6.getItemStack().copy();
                    int var10 = var6.getItemStack().stackSize;
                    for (final Slot var12 : this.field_94537_h) {
                        if (var12 != null && func_94527_a(var12, var6.getItemStack(), true) && var12.isItemValid(var6.getItemStack()) && var6.getItemStack().stackSize >= this.field_94537_h.size() && this.canDragIntoSlot(var12)) {
                            final ItemStack var13 = var9.copy();
                            final int var14 = var12.getHasStack() ? var12.getStack().stackSize : 0;
                            func_94525_a(this.field_94537_h, this.field_94535_f, var13, var14);
                            if (var13.stackSize > var13.getMaxStackSize()) {
                                var13.stackSize = var13.getMaxStackSize();
                            }
                            if (var13.stackSize > var12.getSlotStackLimit()) {
                                var13.stackSize = var12.getSlotStackLimit();
                            }
                            var10 -= var13.stackSize - var14;
                            var12.putStack(var13);
                        }
                    }
                    var9.stackSize = var10;
                    if (var9.stackSize <= 0) {
                        var9 = null;
                    }
                    var6.setItemStack(var9);
                }
                this.func_94533_d();
            }
            else {
                this.func_94533_d();
            }
        }
        else if (this.field_94536_g != 0) {
            this.func_94533_d();
        }
        else if ((p_75144_3_ == 0 || p_75144_3_ == 1) && (p_75144_2_ == 0 || p_75144_2_ == 1)) {
            if (p_75144_1_ == -999) {
                if (var6.getItemStack() != null && p_75144_1_ == -999) {
                    if (p_75144_2_ == 0) {
                        p_75144_4_.dropPlayerItemWithRandomChoice(var6.getItemStack(), true);
                        var6.setItemStack(null);
                    }
                    if (p_75144_2_ == 1) {
                        p_75144_4_.dropPlayerItemWithRandomChoice(var6.getItemStack().splitStack(1), true);
                        if (var6.getItemStack().stackSize == 0) {
                            var6.setItemStack(null);
                        }
                    }
                }
            }
            else if (p_75144_3_ == 1) {
                if (p_75144_1_ < 0) {
                    return null;
                }
                final Slot var15 = this.inventorySlots.get(p_75144_1_);
                if (var15 != null && var15.canTakeStack(p_75144_4_)) {
                    final ItemStack var9 = this.transferStackInSlot(p_75144_4_, p_75144_1_);
                    if (var9 != null) {
                        final Item var16 = var9.getItem();
                        var5 = var9.copy();
                        if (var15.getStack() != null && var15.getStack().getItem() == var16) {
                            this.retrySlotClick(p_75144_1_, p_75144_2_, true, p_75144_4_);
                        }
                    }
                }
            }
            else {
                if (p_75144_1_ < 0) {
                    return null;
                }
                final Slot var15 = this.inventorySlots.get(p_75144_1_);
                if (var15 != null) {
                    ItemStack var9 = var15.getStack();
                    final ItemStack var17 = var6.getItemStack();
                    if (var9 != null) {
                        var5 = var9.copy();
                    }
                    if (var9 == null) {
                        if (var17 != null && var15.isItemValid(var17)) {
                            int var18 = (p_75144_2_ == 0) ? var17.stackSize : 1;
                            if (var18 > var15.getSlotStackLimit()) {
                                var18 = var15.getSlotStackLimit();
                            }
                            if (var17.stackSize >= var18) {
                                var15.putStack(var17.splitStack(var18));
                            }
                            if (var17.stackSize == 0) {
                                var6.setItemStack(null);
                            }
                        }
                    }
                    else if (var15.canTakeStack(p_75144_4_)) {
                        if (var17 == null) {
                            final int var18 = (p_75144_2_ == 0) ? var9.stackSize : ((var9.stackSize + 1) / 2);
                            final ItemStack var19 = var15.decrStackSize(var18);
                            var6.setItemStack(var19);
                            if (var9.stackSize == 0) {
                                var15.putStack(null);
                            }
                            var15.onPickupFromSlot(p_75144_4_, var6.getItemStack());
                        }
                        else if (var15.isItemValid(var17)) {
                            if (var9.getItem() == var17.getItem() && var9.getItemDamage() == var17.getItemDamage() && ItemStack.areItemStackTagsEqual(var9, var17)) {
                                int var18 = (p_75144_2_ == 0) ? var17.stackSize : 1;
                                if (var18 > var15.getSlotStackLimit() - var9.stackSize) {
                                    var18 = var15.getSlotStackLimit() - var9.stackSize;
                                }
                                if (var18 > var17.getMaxStackSize() - var9.stackSize) {
                                    var18 = var17.getMaxStackSize() - var9.stackSize;
                                }
                                var17.splitStack(var18);
                                if (var17.stackSize == 0) {
                                    var6.setItemStack(null);
                                }
                                final ItemStack itemStack = var9;
                                itemStack.stackSize += var18;
                            }
                            else if (var17.stackSize <= var15.getSlotStackLimit()) {
                                var15.putStack(var17);
                                var6.setItemStack(var9);
                            }
                        }
                        else if (var9.getItem() == var17.getItem() && var17.getMaxStackSize() > 1 && (!var9.getHasSubtypes() || var9.getItemDamage() == var17.getItemDamage()) && ItemStack.areItemStackTagsEqual(var9, var17)) {
                            final int var18 = var9.stackSize;
                            if (var18 > 0 && var18 + var17.stackSize <= var17.getMaxStackSize()) {
                                final ItemStack itemStack2 = var17;
                                itemStack2.stackSize += var18;
                                var9 = var15.decrStackSize(var18);
                                if (var9.stackSize == 0) {
                                    var15.putStack(null);
                                }
                                var15.onPickupFromSlot(p_75144_4_, var6.getItemStack());
                            }
                        }
                    }
                    var15.onSlotChanged();
                }
            }
        }
        else if (p_75144_3_ == 2 && p_75144_2_ >= 0 && p_75144_2_ < 9) {
            final Slot var15 = this.inventorySlots.get(p_75144_1_);
            if (var15.canTakeStack(p_75144_4_)) {
                final ItemStack var9 = var6.getStackInSlot(p_75144_2_);
                boolean var20 = var9 == null || (var15.inventory == var6 && var15.isItemValid(var9));
                int var18 = -1;
                if (!var20) {
                    var18 = var6.getFirstEmptyStack();
                    var20 |= (var18 > -1);
                }
                if (var15.getHasStack() && var20) {
                    final ItemStack var19 = var15.getStack();
                    var6.setInventorySlotContents(p_75144_2_, var19.copy());
                    if ((var15.inventory != var6 || !var15.isItemValid(var9)) && var9 != null) {
                        if (var18 > -1) {
                            var6.addItemStackToInventory(var9);
                            var15.decrStackSize(var19.stackSize);
                            var15.putStack(null);
                            var15.onPickupFromSlot(p_75144_4_, var19);
                        }
                    }
                    else {
                        var15.decrStackSize(var19.stackSize);
                        var15.putStack(var9);
                        var15.onPickupFromSlot(p_75144_4_, var19);
                    }
                }
                else if (!var15.getHasStack() && var9 != null && var15.isItemValid(var9)) {
                    var6.setInventorySlotContents(p_75144_2_, null);
                    var15.putStack(var9);
                }
            }
        }
        else if (p_75144_3_ == 3 && p_75144_4_.capabilities.isCreativeMode && var6.getItemStack() == null && p_75144_1_ >= 0) {
            final Slot var15 = this.inventorySlots.get(p_75144_1_);
            if (var15 != null && var15.getHasStack()) {
                final ItemStack var9 = var15.getStack().copy();
                var9.stackSize = var9.getMaxStackSize();
                var6.setItemStack(var9);
            }
        }
        else if (p_75144_3_ == 4 && var6.getItemStack() == null && p_75144_1_ >= 0) {
            final Slot var15 = this.inventorySlots.get(p_75144_1_);
            if (var15 != null && var15.getHasStack() && var15.canTakeStack(p_75144_4_)) {
                final ItemStack var9 = var15.decrStackSize((p_75144_2_ == 0) ? 1 : var15.getStack().stackSize);
                var15.onPickupFromSlot(p_75144_4_, var9);
                p_75144_4_.dropPlayerItemWithRandomChoice(var9, true);
            }
        }
        else if (p_75144_3_ == 6 && p_75144_1_ >= 0) {
            final Slot var15 = this.inventorySlots.get(p_75144_1_);
            final ItemStack var9 = var6.getItemStack();
            if (var9 != null && (var15 == null || !var15.getHasStack() || !var15.canTakeStack(p_75144_4_))) {
                final int var10 = (p_75144_2_ == 0) ? 0 : (this.inventorySlots.size() - 1);
                final int var18 = (p_75144_2_ == 0) ? 1 : -1;
                for (int var21 = 0; var21 < 2; ++var21) {
                    for (int var22 = var10; var22 >= 0 && var22 < this.inventorySlots.size() && var9.stackSize < var9.getMaxStackSize(); var22 += var18) {
                        final Slot var23 = this.inventorySlots.get(var22);
                        if (var23.getHasStack() && func_94527_a(var23, var9, true) && var23.canTakeStack(p_75144_4_) && this.func_94530_a(var9, var23) && (var21 != 0 || var23.getStack().stackSize != var23.getStack().getMaxStackSize())) {
                            final int var24 = Math.min(var9.getMaxStackSize() - var9.stackSize, var23.getStack().stackSize);
                            final ItemStack var25 = var23.decrStackSize(var24);
                            final ItemStack itemStack3 = var9;
                            itemStack3.stackSize += var24;
                            if (var25.stackSize <= 0) {
                                var23.putStack(null);
                            }
                            var23.onPickupFromSlot(p_75144_4_, var25);
                        }
                    }
                }
            }
            this.detectAndSendChanges();
        }
        return var5;
    }
    
    public boolean func_94530_a(final ItemStack p_94530_1_, final Slot p_94530_2_) {
        return true;
    }
    
    protected void retrySlotClick(final int p_75133_1_, final int p_75133_2_, final boolean p_75133_3_, final EntityPlayer p_75133_4_) {
        this.slotClick(p_75133_1_, p_75133_2_, 1, p_75133_4_);
    }
    
    public void onContainerClosed(final EntityPlayer p_75134_1_) {
        final InventoryPlayer var2 = p_75134_1_.inventory;
        if (var2.getItemStack() != null) {
            p_75134_1_.dropPlayerItemWithRandomChoice(var2.getItemStack(), false);
            var2.setItemStack(null);
        }
    }
    
    public void onCraftMatrixChanged(final IInventory p_75130_1_) {
        this.detectAndSendChanges();
    }
    
    public void putStackInSlot(final int p_75141_1_, final ItemStack p_75141_2_) {
        this.getSlot(p_75141_1_).putStack(p_75141_2_);
    }
    
    public void putStacksInSlots(final ItemStack[] p_75131_1_) {
        for (int var2 = 0; var2 < p_75131_1_.length; ++var2) {
            this.getSlot(var2).putStack(p_75131_1_[var2]);
        }
    }
    
    public void updateProgressBar(final int p_75137_1_, final int p_75137_2_) {
    }
    
    public short getNextTransactionID(final InventoryPlayer p_75136_1_) {
        return (short)(++this.transactionID);
    }
    
    public boolean isPlayerNotUsingContainer(final EntityPlayer p_75129_1_) {
        return !this.playerList.contains(p_75129_1_);
    }
    
    public void setPlayerIsPresent(final EntityPlayer p_75128_1_, final boolean p_75128_2_) {
        if (p_75128_2_) {
            this.playerList.remove(p_75128_1_);
        }
        else {
            this.playerList.add(p_75128_1_);
        }
    }
    
    public abstract boolean canInteractWith(final EntityPlayer p0);
    
    protected boolean mergeItemStack(final ItemStack p_75135_1_, final int p_75135_2_, final int p_75135_3_, final boolean p_75135_4_) {
        boolean var5 = false;
        int var6 = p_75135_2_;
        if (p_75135_4_) {
            var6 = p_75135_3_ - 1;
        }
        if (p_75135_1_.isStackable()) {
            while (p_75135_1_.stackSize > 0 && ((!p_75135_4_ && var6 < p_75135_3_) || (p_75135_4_ && var6 >= p_75135_2_))) {
                final Slot var7 = this.inventorySlots.get(var6);
                final ItemStack var8 = var7.getStack();
                if (var8 != null && var8.getItem() == p_75135_1_.getItem() && (!p_75135_1_.getHasSubtypes() || p_75135_1_.getItemDamage() == var8.getItemDamage()) && ItemStack.areItemStackTagsEqual(p_75135_1_, var8)) {
                    final int var9 = var8.stackSize + p_75135_1_.stackSize;
                    if (var9 <= p_75135_1_.getMaxStackSize()) {
                        p_75135_1_.stackSize = 0;
                        var8.stackSize = var9;
                        var7.onSlotChanged();
                        var5 = true;
                    }
                    else if (var8.stackSize < p_75135_1_.getMaxStackSize()) {
                        p_75135_1_.stackSize -= p_75135_1_.getMaxStackSize() - var8.stackSize;
                        var8.stackSize = p_75135_1_.getMaxStackSize();
                        var7.onSlotChanged();
                        var5 = true;
                    }
                }
                if (p_75135_4_) {
                    --var6;
                }
                else {
                    ++var6;
                }
            }
        }
        if (p_75135_1_.stackSize > 0) {
            if (p_75135_4_) {
                var6 = p_75135_3_ - 1;
            }
            else {
                var6 = p_75135_2_;
            }
            while ((!p_75135_4_ && var6 < p_75135_3_) || (p_75135_4_ && var6 >= p_75135_2_)) {
                final Slot var7 = this.inventorySlots.get(var6);
                final ItemStack var8 = var7.getStack();
                if (var8 == null) {
                    var7.putStack(p_75135_1_.copy());
                    var7.onSlotChanged();
                    p_75135_1_.stackSize = 0;
                    var5 = true;
                    break;
                }
                if (p_75135_4_) {
                    --var6;
                }
                else {
                    ++var6;
                }
            }
        }
        return var5;
    }
    
    public static int func_94529_b(final int p_94529_0_) {
        return p_94529_0_ >> 2 & 0x3;
    }
    
    public static int func_94532_c(final int p_94532_0_) {
        return p_94532_0_ & 0x3;
    }
    
    public static int func_94534_d(final int p_94534_0_, final int p_94534_1_) {
        return (p_94534_0_ & 0x3) | (p_94534_1_ & 0x3) << 2;
    }
    
    public static boolean func_94528_d(final int p_94528_0_) {
        return p_94528_0_ == 0 || p_94528_0_ == 1;
    }
    
    protected void func_94533_d() {
        this.field_94536_g = 0;
        this.field_94537_h.clear();
    }
    
    public static boolean func_94527_a(final Slot p_94527_0_, final ItemStack p_94527_1_, final boolean p_94527_2_) {
        boolean var3 = p_94527_0_ == null || !p_94527_0_.getHasStack();
        if (p_94527_0_ != null && p_94527_0_.getHasStack() && p_94527_1_ != null && p_94527_1_.isItemEqual(p_94527_0_.getStack()) && ItemStack.areItemStackTagsEqual(p_94527_0_.getStack(), p_94527_1_)) {
            final int var4 = p_94527_2_ ? 0 : p_94527_1_.stackSize;
            var3 |= (p_94527_0_.getStack().stackSize + var4 <= p_94527_1_.getMaxStackSize());
        }
        return var3;
    }
    
    public static void func_94525_a(final Set p_94525_0_, final int p_94525_1_, final ItemStack p_94525_2_, final int p_94525_3_) {
        switch (p_94525_1_) {
            case 0: {
                p_94525_2_.stackSize = MathHelper.floor_float(p_94525_2_.stackSize / (float)p_94525_0_.size());
                break;
            }
            case 1: {
                p_94525_2_.stackSize = 1;
                break;
            }
        }
        p_94525_2_.stackSize += p_94525_3_;
    }
    
    public boolean canDragIntoSlot(final Slot p_94531_1_) {
        return true;
    }
    
    public static int calcRedstoneFromInventory(final IInventory p_94526_0_) {
        if (p_94526_0_ == null) {
            return 0;
        }
        int var1 = 0;
        float var2 = 0.0f;
        for (int var3 = 0; var3 < p_94526_0_.getSizeInventory(); ++var3) {
            final ItemStack var4 = p_94526_0_.getStackInSlot(var3);
            if (var4 != null) {
                var2 += var4.stackSize / (float)Math.min(p_94526_0_.getInventoryStackLimit(), var4.getMaxStackSize());
                ++var1;
            }
        }
        var2 /= p_94526_0_.getSizeInventory();
        return MathHelper.floor_float(var2 * 14.0f) + ((var1 > 0) ? 1 : 0);
    }
}
