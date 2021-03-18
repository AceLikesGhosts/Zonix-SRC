package net.minecraft.entity.player;

import net.minecraft.inventory.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;
import net.minecraft.block.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;

public class InventoryPlayer implements IInventory
{
    public ItemStack[] mainInventory;
    public ItemStack[] armorInventory;
    public int currentItem;
    private ItemStack currentItemStack;
    public EntityPlayer player;
    private ItemStack itemStack;
    public boolean inventoryChanged;
    private static final String __OBFID = "CL_00001709";
    
    public InventoryPlayer(final EntityPlayer p_i1750_1_) {
        this.mainInventory = new ItemStack[36];
        this.armorInventory = new ItemStack[4];
        this.player = p_i1750_1_;
    }
    
    public ItemStack getCurrentItem() {
        return (this.currentItem < 9 && this.currentItem >= 0) ? this.mainInventory[this.currentItem] : null;
    }
    
    public static int getHotbarSize() {
        return 9;
    }
    
    private int func_146029_c(final Item p_146029_1_) {
        for (int var2 = 0; var2 < this.mainInventory.length; ++var2) {
            if (this.mainInventory[var2] != null && this.mainInventory[var2].getItem() == p_146029_1_) {
                return var2;
            }
        }
        return -1;
    }
    
    private int func_146024_c(final Item p_146024_1_, final int p_146024_2_) {
        for (int var3 = 0; var3 < this.mainInventory.length; ++var3) {
            if (this.mainInventory[var3] != null && this.mainInventory[var3].getItem() == p_146024_1_ && this.mainInventory[var3].getItemDamage() == p_146024_2_) {
                return var3;
            }
        }
        return -1;
    }
    
    private int storeItemStack(final ItemStack p_70432_1_) {
        for (int var2 = 0; var2 < this.mainInventory.length; ++var2) {
            if (this.mainInventory[var2] != null && this.mainInventory[var2].getItem() == p_70432_1_.getItem() && this.mainInventory[var2].isStackable() && this.mainInventory[var2].stackSize < this.mainInventory[var2].getMaxStackSize() && this.mainInventory[var2].stackSize < this.getInventoryStackLimit() && (!this.mainInventory[var2].getHasSubtypes() || this.mainInventory[var2].getItemDamage() == p_70432_1_.getItemDamage()) && ItemStack.areItemStackTagsEqual(this.mainInventory[var2], p_70432_1_)) {
                return var2;
            }
        }
        return -1;
    }
    
    public int getFirstEmptyStack() {
        for (int var1 = 0; var1 < this.mainInventory.length; ++var1) {
            if (this.mainInventory[var1] == null) {
                return var1;
            }
        }
        return -1;
    }
    
    public void func_146030_a(final Item p_146030_1_, final int p_146030_2_, final boolean p_146030_3_, final boolean p_146030_4_) {
        final boolean var5 = true;
        this.currentItemStack = this.getCurrentItem();
        int var6;
        if (p_146030_3_) {
            var6 = this.func_146024_c(p_146030_1_, p_146030_2_);
        }
        else {
            var6 = this.func_146029_c(p_146030_1_);
        }
        if (var6 >= 0 && var6 < 9) {
            this.currentItem = var6;
        }
        else if (p_146030_4_ && p_146030_1_ != null) {
            final int var7 = this.getFirstEmptyStack();
            if (var7 >= 0 && var7 < 9) {
                this.currentItem = var7;
            }
            this.func_70439_a(p_146030_1_, p_146030_2_);
        }
    }
    
    public void changeCurrentItem(int p_70453_1_) {
        if (p_70453_1_ > 0) {
            p_70453_1_ = 1;
        }
        if (p_70453_1_ < 0) {
            p_70453_1_ = -1;
        }
        this.currentItem -= p_70453_1_;
        while (this.currentItem < 0) {
            this.currentItem += 9;
        }
        while (this.currentItem >= 9) {
            this.currentItem -= 9;
        }
    }
    
    public int clearInventory(final Item p_146027_1_, final int p_146027_2_) {
        int var3 = 0;
        for (int var4 = 0; var4 < this.mainInventory.length; ++var4) {
            final ItemStack var5 = this.mainInventory[var4];
            if (var5 != null && (p_146027_1_ == null || var5.getItem() == p_146027_1_) && (p_146027_2_ <= -1 || var5.getItemDamage() == p_146027_2_)) {
                var3 += var5.stackSize;
                this.mainInventory[var4] = null;
            }
        }
        for (int var4 = 0; var4 < this.armorInventory.length; ++var4) {
            final ItemStack var5 = this.armorInventory[var4];
            if (var5 != null && (p_146027_1_ == null || var5.getItem() == p_146027_1_) && (p_146027_2_ <= -1 || var5.getItemDamage() == p_146027_2_)) {
                var3 += var5.stackSize;
                this.armorInventory[var4] = null;
            }
        }
        if (this.itemStack != null) {
            if (p_146027_1_ != null && this.itemStack.getItem() != p_146027_1_) {
                return var3;
            }
            if (p_146027_2_ > -1 && this.itemStack.getItemDamage() != p_146027_2_) {
                return var3;
            }
            var3 += this.itemStack.stackSize;
            this.setItemStack(null);
        }
        return var3;
    }
    
    public void func_70439_a(final Item p_70439_1_, final int p_70439_2_) {
        if (p_70439_1_ != null) {
            if (this.currentItemStack != null && this.currentItemStack.isItemEnchantable() && this.func_146024_c(this.currentItemStack.getItem(), this.currentItemStack.getItemDamageForDisplay()) == this.currentItem) {
                return;
            }
            final int var3 = this.func_146024_c(p_70439_1_, p_70439_2_);
            if (var3 >= 0) {
                final int var4 = this.mainInventory[var3].stackSize;
                this.mainInventory[var3] = this.mainInventory[this.currentItem];
                this.mainInventory[this.currentItem] = new ItemStack(p_70439_1_, var4, p_70439_2_);
            }
            else {
                this.mainInventory[this.currentItem] = new ItemStack(p_70439_1_, 1, p_70439_2_);
            }
        }
    }
    
    private int storePartialItemStack(final ItemStack p_70452_1_) {
        final Item var2 = p_70452_1_.getItem();
        int var3 = p_70452_1_.stackSize;
        if (p_70452_1_.getMaxStackSize() == 1) {
            final int var4 = this.getFirstEmptyStack();
            if (var4 < 0) {
                return var3;
            }
            if (this.mainInventory[var4] == null) {
                this.mainInventory[var4] = ItemStack.copyItemStack(p_70452_1_);
            }
            return 0;
        }
        else {
            int var4 = this.storeItemStack(p_70452_1_);
            if (var4 < 0) {
                var4 = this.getFirstEmptyStack();
            }
            if (var4 < 0) {
                return var3;
            }
            if (this.mainInventory[var4] == null) {
                this.mainInventory[var4] = new ItemStack(var2, 0, p_70452_1_.getItemDamage());
                if (p_70452_1_.hasTagCompound()) {
                    this.mainInventory[var4].setTagCompound((NBTTagCompound)p_70452_1_.getTagCompound().copy());
                }
            }
            int var5;
            if ((var5 = var3) > this.mainInventory[var4].getMaxStackSize() - this.mainInventory[var4].stackSize) {
                var5 = this.mainInventory[var4].getMaxStackSize() - this.mainInventory[var4].stackSize;
            }
            if (var5 > this.getInventoryStackLimit() - this.mainInventory[var4].stackSize) {
                var5 = this.getInventoryStackLimit() - this.mainInventory[var4].stackSize;
            }
            if (var5 == 0) {
                return var3;
            }
            var3 -= var5;
            final ItemStack itemStack = this.mainInventory[var4];
            itemStack.stackSize += var5;
            this.mainInventory[var4].animationsToGo = 5;
            return var3;
        }
    }
    
    public void decrementAnimations() {
        for (int var1 = 0; var1 < this.mainInventory.length; ++var1) {
            if (this.mainInventory[var1] != null) {
                this.mainInventory[var1].updateAnimation(this.player.worldObj, this.player, var1, this.currentItem == var1);
            }
        }
    }
    
    public boolean consumeInventoryItem(final Item p_146026_1_) {
        final int var2 = this.func_146029_c(p_146026_1_);
        if (var2 < 0) {
            return false;
        }
        final ItemStack itemStack = this.mainInventory[var2];
        if (--itemStack.stackSize <= 0) {
            this.mainInventory[var2] = null;
        }
        return true;
    }
    
    public boolean hasItem(final Item p_146028_1_) {
        final int var2 = this.func_146029_c(p_146028_1_);
        return var2 >= 0;
    }
    
    public boolean addItemStackToInventory(final ItemStack p_70441_1_) {
        if (p_70441_1_ != null && p_70441_1_.stackSize != 0 && p_70441_1_.getItem() != null) {
            try {
                if (p_70441_1_.isItemDamaged()) {
                    final int var2 = this.getFirstEmptyStack();
                    if (var2 >= 0) {
                        this.mainInventory[var2] = ItemStack.copyItemStack(p_70441_1_);
                        this.mainInventory[var2].animationsToGo = 5;
                        p_70441_1_.stackSize = 0;
                        return true;
                    }
                    if (this.player.capabilities.isCreativeMode) {
                        p_70441_1_.stackSize = 0;
                        return true;
                    }
                    return false;
                }
                else {
                    int var2;
                    do {
                        var2 = p_70441_1_.stackSize;
                        p_70441_1_.stackSize = this.storePartialItemStack(p_70441_1_);
                    } while (p_70441_1_.stackSize > 0 && p_70441_1_.stackSize < var2);
                    if (p_70441_1_.stackSize == var2 && this.player.capabilities.isCreativeMode) {
                        p_70441_1_.stackSize = 0;
                        return true;
                    }
                    return p_70441_1_.stackSize < var2;
                }
            }
            catch (Throwable var4) {
                final CrashReport var3 = CrashReport.makeCrashReport(var4, "Adding item to inventory");
                final CrashReportCategory var5 = var3.makeCategory("Item being added");
                var5.addCrashSection("Item ID", Item.getIdFromItem(p_70441_1_.getItem()));
                var5.addCrashSection("Item data", p_70441_1_.getItemDamage());
                var5.addCrashSectionCallable("Item name", new Callable() {
                    private static final String __OBFID = "CL_00001710";
                    
                    @Override
                    public String call() {
                        return p_70441_1_.getDisplayName();
                    }
                });
                throw new ReportedException(var3);
            }
        }
        return false;
    }
    
    @Override
    public ItemStack decrStackSize(int p_70298_1_, final int p_70298_2_) {
        ItemStack[] var3 = this.mainInventory;
        if (p_70298_1_ >= this.mainInventory.length) {
            var3 = this.armorInventory;
            p_70298_1_ -= this.mainInventory.length;
        }
        if (var3[p_70298_1_] == null) {
            return null;
        }
        if (var3[p_70298_1_].stackSize <= p_70298_2_) {
            final ItemStack var4 = var3[p_70298_1_];
            var3[p_70298_1_] = null;
            return var4;
        }
        final ItemStack var4 = var3[p_70298_1_].splitStack(p_70298_2_);
        if (var3[p_70298_1_].stackSize == 0) {
            var3[p_70298_1_] = null;
        }
        return var4;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        ItemStack[] var2 = this.mainInventory;
        if (p_70304_1_ >= this.mainInventory.length) {
            var2 = this.armorInventory;
            p_70304_1_ -= this.mainInventory.length;
        }
        if (var2[p_70304_1_] != null) {
            final ItemStack var3 = var2[p_70304_1_];
            var2[p_70304_1_] = null;
            return var3;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(int p_70299_1_, final ItemStack p_70299_2_) {
        ItemStack[] var3 = this.mainInventory;
        if (p_70299_1_ >= var3.length) {
            p_70299_1_ -= var3.length;
            var3 = this.armorInventory;
        }
        var3[p_70299_1_] = p_70299_2_;
    }
    
    public float func_146023_a(final Block p_146023_1_) {
        float var2 = 1.0f;
        if (this.mainInventory[this.currentItem] != null) {
            var2 *= this.mainInventory[this.currentItem].func_150997_a(p_146023_1_);
        }
        return var2;
    }
    
    public NBTTagList writeToNBT(final NBTTagList p_70442_1_) {
        for (int var2 = 0; var2 < this.mainInventory.length; ++var2) {
            if (this.mainInventory[var2] != null) {
                final NBTTagCompound var3 = new NBTTagCompound();
                var3.setByte("Slot", (byte)var2);
                this.mainInventory[var2].writeToNBT(var3);
                p_70442_1_.appendTag(var3);
            }
        }
        for (int var2 = 0; var2 < this.armorInventory.length; ++var2) {
            if (this.armorInventory[var2] != null) {
                final NBTTagCompound var3 = new NBTTagCompound();
                var3.setByte("Slot", (byte)(var2 + 100));
                this.armorInventory[var2].writeToNBT(var3);
                p_70442_1_.appendTag(var3);
            }
        }
        return p_70442_1_;
    }
    
    public void readFromNBT(final NBTTagList p_70443_1_) {
        this.mainInventory = new ItemStack[36];
        this.armorInventory = new ItemStack[4];
        for (int var2 = 0; var2 < p_70443_1_.tagCount(); ++var2) {
            final NBTTagCompound var3 = p_70443_1_.getCompoundTagAt(var2);
            final int var4 = var3.getByte("Slot") & 0xFF;
            final ItemStack var5 = ItemStack.loadItemStackFromNBT(var3);
            if (var5 != null) {
                if (var4 >= 0 && var4 < this.mainInventory.length) {
                    this.mainInventory[var4] = var5;
                }
                if (var4 >= 100 && var4 < this.armorInventory.length + 100) {
                    this.armorInventory[var4 - 100] = var5;
                }
            }
        }
    }
    
    @Override
    public int getSizeInventory() {
        return this.mainInventory.length + 4;
    }
    
    @Override
    public ItemStack getStackInSlot(int p_70301_1_) {
        ItemStack[] var2 = this.mainInventory;
        if (p_70301_1_ >= var2.length) {
            p_70301_1_ -= var2.length;
            var2 = this.armorInventory;
        }
        return var2[p_70301_1_];
    }
    
    @Override
    public String getInventoryName() {
        return "container.inventory";
    }
    
    @Override
    public boolean isInventoryNameLocalized() {
        return false;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    public boolean func_146025_b(final Block p_146025_1_) {
        if (p_146025_1_.getMaterial().isToolNotRequired()) {
            return true;
        }
        final ItemStack var2 = this.getStackInSlot(this.currentItem);
        return var2 != null && var2.func_150998_b(p_146025_1_);
    }
    
    public ItemStack armorItemInSlot(final int p_70440_1_) {
        return this.armorInventory[p_70440_1_];
    }
    
    public int getTotalArmorValue() {
        int var1 = 0;
        for (int var2 = 0; var2 < this.armorInventory.length; ++var2) {
            if (this.armorInventory[var2] != null && this.armorInventory[var2].getItem() instanceof ItemArmor) {
                final int var3 = ((ItemArmor)this.armorInventory[var2].getItem()).damageReduceAmount;
                var1 += var3;
            }
        }
        return var1;
    }
    
    public void damageArmor(float p_70449_1_) {
        p_70449_1_ /= 4.0f;
        if (p_70449_1_ < 1.0f) {
            p_70449_1_ = 1.0f;
        }
        for (int var2 = 0; var2 < this.armorInventory.length; ++var2) {
            if (this.armorInventory[var2] != null && this.armorInventory[var2].getItem() instanceof ItemArmor) {
                this.armorInventory[var2].damageItem((int)p_70449_1_, this.player);
                if (this.armorInventory[var2].stackSize == 0) {
                    this.armorInventory[var2] = null;
                }
            }
        }
    }
    
    public void dropAllItems() {
        for (int var1 = 0; var1 < this.mainInventory.length; ++var1) {
            if (this.mainInventory[var1] != null) {
                this.player.func_146097_a(this.mainInventory[var1], true, false);
                this.mainInventory[var1] = null;
            }
        }
        for (int var1 = 0; var1 < this.armorInventory.length; ++var1) {
            if (this.armorInventory[var1] != null) {
                this.player.func_146097_a(this.armorInventory[var1], true, false);
                this.armorInventory[var1] = null;
            }
        }
    }
    
    @Override
    public void onInventoryChanged() {
        this.inventoryChanged = true;
    }
    
    public void setItemStack(final ItemStack p_70437_1_) {
        this.itemStack = p_70437_1_;
    }
    
    public ItemStack getItemStack() {
        return this.itemStack;
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer p_70300_1_) {
        return !this.player.isDead && p_70300_1_.getDistanceSqToEntity(this.player) <= 64.0;
    }
    
    public boolean hasItemStack(final ItemStack p_70431_1_) {
        for (int var2 = 0; var2 < this.armorInventory.length; ++var2) {
            if (this.armorInventory[var2] != null && this.armorInventory[var2].isItemEqual(p_70431_1_)) {
                return true;
            }
        }
        for (int var2 = 0; var2 < this.mainInventory.length; ++var2) {
            if (this.mainInventory[var2] != null && this.mainInventory[var2].isItemEqual(p_70431_1_)) {
                return true;
            }
        }
        return false;
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
    
    public void copyInventory(final InventoryPlayer p_70455_1_) {
        for (int var2 = 0; var2 < this.mainInventory.length; ++var2) {
            this.mainInventory[var2] = ItemStack.copyItemStack(p_70455_1_.mainInventory[var2]);
        }
        for (int var2 = 0; var2 < this.armorInventory.length; ++var2) {
            this.armorInventory[var2] = ItemStack.copyItemStack(p_70455_1_.armorInventory[var2]);
        }
        this.currentItem = p_70455_1_.currentItem;
    }
}
