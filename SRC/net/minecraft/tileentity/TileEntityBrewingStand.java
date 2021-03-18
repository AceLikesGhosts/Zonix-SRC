package net.minecraft.tileentity;

import net.minecraft.inventory.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.potion.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;

public class TileEntityBrewingStand extends TileEntity implements ISidedInventory
{
    private static final int[] field_145941_a;
    private static final int[] field_145947_i;
    private ItemStack[] field_145945_j;
    private int field_145946_k;
    private int field_145943_l;
    private Item field_145944_m;
    private String field_145942_n;
    private static final String __OBFID = "CL_00000345";
    
    public TileEntityBrewingStand() {
        this.field_145945_j = new ItemStack[4];
    }
    
    @Override
    public String getInventoryName() {
        return this.isInventoryNameLocalized() ? this.field_145942_n : "container.brewing";
    }
    
    @Override
    public boolean isInventoryNameLocalized() {
        return this.field_145942_n != null && this.field_145942_n.length() > 0;
    }
    
    public void func_145937_a(final String p_145937_1_) {
        this.field_145942_n = p_145937_1_;
    }
    
    @Override
    public int getSizeInventory() {
        return this.field_145945_j.length;
    }
    
    @Override
    public void updateEntity() {
        if (this.field_145946_k > 0) {
            --this.field_145946_k;
            if (this.field_145946_k == 0) {
                this.func_145940_l();
                this.onInventoryChanged();
            }
            else if (!this.func_145934_k()) {
                this.field_145946_k = 0;
                this.onInventoryChanged();
            }
            else if (this.field_145944_m != this.field_145945_j[3].getItem()) {
                this.field_145946_k = 0;
                this.onInventoryChanged();
            }
        }
        else if (this.func_145934_k()) {
            this.field_145946_k = 400;
            this.field_145944_m = this.field_145945_j[3].getItem();
        }
        final int var1 = this.func_145939_j();
        if (var1 != this.field_145943_l) {
            this.field_145943_l = var1;
            this.worldObj.setBlockMetadataWithNotify(this.field_145851_c, this.field_145848_d, this.field_145849_e, var1, 2);
        }
        super.updateEntity();
    }
    
    public int func_145935_i() {
        return this.field_145946_k;
    }
    
    private boolean func_145934_k() {
        if (this.field_145945_j[3] == null || this.field_145945_j[3].stackSize <= 0) {
            return false;
        }
        final ItemStack var1 = this.field_145945_j[3];
        if (!var1.getItem().isPotionIngredient(var1)) {
            return false;
        }
        boolean var2 = false;
        for (int var3 = 0; var3 < 3; ++var3) {
            if (this.field_145945_j[var3] != null && this.field_145945_j[var3].getItem() == Items.potionitem) {
                final int var4 = this.field_145945_j[var3].getItemDamage();
                final int var5 = this.func_145936_c(var4, var1);
                if (!ItemPotion.isSplash(var4) && ItemPotion.isSplash(var5)) {
                    var2 = true;
                    break;
                }
                final List var6 = Items.potionitem.getEffects(var4);
                final List var7 = Items.potionitem.getEffects(var5);
                if ((var4 <= 0 || var6 != var7) && (var6 == null || (!var6.equals(var7) && var7 != null)) && var4 != var5) {
                    var2 = true;
                    break;
                }
            }
        }
        return var2;
    }
    
    private void func_145940_l() {
        if (this.func_145934_k()) {
            final ItemStack var1 = this.field_145945_j[3];
            for (int var2 = 0; var2 < 3; ++var2) {
                if (this.field_145945_j[var2] != null && this.field_145945_j[var2].getItem() == Items.potionitem) {
                    final int var3 = this.field_145945_j[var2].getItemDamage();
                    final int var4 = this.func_145936_c(var3, var1);
                    final List var5 = Items.potionitem.getEffects(var3);
                    final List var6 = Items.potionitem.getEffects(var4);
                    if ((var3 <= 0 || var5 != var6) && (var5 == null || (!var5.equals(var6) && var6 != null))) {
                        if (var3 != var4) {
                            this.field_145945_j[var2].setItemDamage(var4);
                        }
                    }
                    else if (!ItemPotion.isSplash(var3) && ItemPotion.isSplash(var4)) {
                        this.field_145945_j[var2].setItemDamage(var4);
                    }
                }
            }
            if (var1.getItem().hasContainerItem()) {
                this.field_145945_j[3] = new ItemStack(var1.getItem().getContainerItem());
            }
            else {
                final ItemStack itemStack = this.field_145945_j[3];
                --itemStack.stackSize;
                if (this.field_145945_j[3].stackSize <= 0) {
                    this.field_145945_j[3] = null;
                }
            }
        }
    }
    
    private int func_145936_c(final int p_145936_1_, final ItemStack p_145936_2_) {
        return (p_145936_2_ == null) ? p_145936_1_ : (p_145936_2_.getItem().isPotionIngredient(p_145936_2_) ? PotionHelper.applyIngredient(p_145936_1_, p_145936_2_.getItem().getPotionEffect(p_145936_2_)) : p_145936_1_);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
        final NBTTagList var2 = p_145839_1_.getTagList("Items", 10);
        this.field_145945_j = new ItemStack[this.getSizeInventory()];
        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            final NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            final byte var5 = var4.getByte("Slot");
            if (var5 >= 0 && var5 < this.field_145945_j.length) {
                this.field_145945_j[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
        this.field_145946_k = p_145839_1_.getShort("BrewTime");
        if (p_145839_1_.func_150297_b("CustomName", 8)) {
            this.field_145942_n = p_145839_1_.getString("CustomName");
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setShort("BrewTime", (short)this.field_145946_k);
        final NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < this.field_145945_j.length; ++var3) {
            if (this.field_145945_j[var3] != null) {
                final NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.field_145945_j[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }
        p_145841_1_.setTag("Items", var2);
        if (this.isInventoryNameLocalized()) {
            p_145841_1_.setString("CustomName", this.field_145942_n);
        }
    }
    
    @Override
    public ItemStack getStackInSlot(final int p_70301_1_) {
        return (p_70301_1_ >= 0 && p_70301_1_ < this.field_145945_j.length) ? this.field_145945_j[p_70301_1_] : null;
    }
    
    @Override
    public ItemStack decrStackSize(final int p_70298_1_, final int p_70298_2_) {
        if (p_70298_1_ >= 0 && p_70298_1_ < this.field_145945_j.length) {
            final ItemStack var3 = this.field_145945_j[p_70298_1_];
            this.field_145945_j[p_70298_1_] = null;
            return var3;
        }
        return null;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int p_70304_1_) {
        if (p_70304_1_ >= 0 && p_70304_1_ < this.field_145945_j.length) {
            final ItemStack var2 = this.field_145945_j[p_70304_1_];
            this.field_145945_j[p_70304_1_] = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(final int p_70299_1_, final ItemStack p_70299_2_) {
        if (p_70299_1_ >= 0 && p_70299_1_ < this.field_145945_j.length) {
            this.field_145945_j[p_70299_1_] = p_70299_2_;
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
        return (p_94041_1_ == 3) ? p_94041_2_.getItem().isPotionIngredient(p_94041_2_) : (p_94041_2_.getItem() == Items.potionitem || p_94041_2_.getItem() == Items.glass_bottle);
    }
    
    public void func_145938_d(final int p_145938_1_) {
        this.field_145946_k = p_145938_1_;
    }
    
    public int func_145939_j() {
        int var1 = 0;
        for (int var2 = 0; var2 < 3; ++var2) {
            if (this.field_145945_j[var2] != null) {
                var1 |= 1 << var2;
            }
        }
        return var1;
    }
    
    @Override
    public int[] getAccessibleSlotsFromSide(final int p_94128_1_) {
        return (p_94128_1_ == 1) ? TileEntityBrewingStand.field_145941_a : TileEntityBrewingStand.field_145947_i;
    }
    
    @Override
    public boolean canInsertItem(final int p_102007_1_, final ItemStack p_102007_2_, final int p_102007_3_) {
        return this.isItemValidForSlot(p_102007_1_, p_102007_2_);
    }
    
    @Override
    public boolean canExtractItem(final int p_102008_1_, final ItemStack p_102008_2_, final int p_102008_3_) {
        return true;
    }
    
    static {
        field_145941_a = new int[] { 3 };
        field_145947_i = new int[] { 0, 1, 2 };
    }
}
