package net.minecraft.tileentity;

import net.minecraft.inventory.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;

public class TileEntityDispenser extends TileEntity implements IInventory
{
    private ItemStack[] field_146022_i;
    private Random field_146021_j;
    protected String field_146020_a;
    private static final String __OBFID = "CL_00000352";
    
    public TileEntityDispenser() {
        this.field_146022_i = new ItemStack[9];
        this.field_146021_j = new Random();
    }
    
    @Override
    public int getSizeInventory() {
        return 9;
    }
    
    @Override
    public ItemStack getStackInSlot(final int p_70301_1_) {
        return this.field_146022_i[p_70301_1_];
    }
    
    @Override
    public ItemStack decrStackSize(final int p_70298_1_, final int p_70298_2_) {
        if (this.field_146022_i[p_70298_1_] == null) {
            return null;
        }
        if (this.field_146022_i[p_70298_1_].stackSize <= p_70298_2_) {
            final ItemStack var3 = this.field_146022_i[p_70298_1_];
            this.field_146022_i[p_70298_1_] = null;
            this.onInventoryChanged();
            return var3;
        }
        final ItemStack var3 = this.field_146022_i[p_70298_1_].splitStack(p_70298_2_);
        if (this.field_146022_i[p_70298_1_].stackSize == 0) {
            this.field_146022_i[p_70298_1_] = null;
        }
        this.onInventoryChanged();
        return var3;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int p_70304_1_) {
        if (this.field_146022_i[p_70304_1_] != null) {
            final ItemStack var2 = this.field_146022_i[p_70304_1_];
            this.field_146022_i[p_70304_1_] = null;
            return var2;
        }
        return null;
    }
    
    public int func_146017_i() {
        int var1 = -1;
        int var2 = 1;
        for (int var3 = 0; var3 < this.field_146022_i.length; ++var3) {
            if (this.field_146022_i[var3] != null && this.field_146021_j.nextInt(var2++) == 0) {
                var1 = var3;
            }
        }
        return var1;
    }
    
    @Override
    public void setInventorySlotContents(final int p_70299_1_, final ItemStack p_70299_2_) {
        this.field_146022_i[p_70299_1_] = p_70299_2_;
        if (p_70299_2_ != null && p_70299_2_.stackSize > this.getInventoryStackLimit()) {
            p_70299_2_.stackSize = this.getInventoryStackLimit();
        }
        this.onInventoryChanged();
    }
    
    public int func_146019_a(final ItemStack p_146019_1_) {
        for (int var2 = 0; var2 < this.field_146022_i.length; ++var2) {
            if (this.field_146022_i[var2] == null || this.field_146022_i[var2].getItem() == null) {
                this.setInventorySlotContents(var2, p_146019_1_);
                return var2;
            }
        }
        return -1;
    }
    
    @Override
    public String getInventoryName() {
        return this.isInventoryNameLocalized() ? this.field_146020_a : "container.dispenser";
    }
    
    public void func_146018_a(final String p_146018_1_) {
        this.field_146020_a = p_146018_1_;
    }
    
    @Override
    public boolean isInventoryNameLocalized() {
        return this.field_146020_a != null;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
        final NBTTagList var2 = p_145839_1_.getTagList("Items", 10);
        this.field_146022_i = new ItemStack[this.getSizeInventory()];
        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            final NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            final int var5 = var4.getByte("Slot") & 0xFF;
            if (var5 >= 0 && var5 < this.field_146022_i.length) {
                this.field_146022_i[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
        if (p_145839_1_.func_150297_b("CustomName", 8)) {
            this.field_146020_a = p_145839_1_.getString("CustomName");
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
        final NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < this.field_146022_i.length; ++var3) {
            if (this.field_146022_i[var3] != null) {
                final NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.field_146022_i[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }
        p_145841_1_.setTag("Items", var2);
        if (this.isInventoryNameLocalized()) {
            p_145841_1_.setString("CustomName", this.field_146020_a);
        }
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer p_70300_1_) {
        return this.worldObj.getTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e) == this && p_70300_1_.getDistanceSq(this.field_145851_c + 0.5, this.field_145848_d + 0.5, this.field_145849_e + 0.5) <= 64.0;
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
}
