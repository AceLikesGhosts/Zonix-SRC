package net.minecraft.tileentity;

import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import java.util.*;

public class TileEntityChest extends TileEntity implements IInventory
{
    private ItemStack[] field_145985_p;
    public boolean field_145984_a;
    public TileEntityChest field_145992_i;
    public TileEntityChest field_145990_j;
    public TileEntityChest field_145991_k;
    public TileEntityChest field_145988_l;
    public float field_145989_m;
    public float field_145986_n;
    public int field_145987_o;
    private int field_145983_q;
    private int field_145982_r;
    private String field_145981_s;
    private static final String __OBFID = "CL_00000346";
    
    public TileEntityChest() {
        this.field_145985_p = new ItemStack[36];
        this.field_145982_r = -1;
    }
    
    public TileEntityChest(final int p_i2350_1_) {
        this.field_145985_p = new ItemStack[36];
        this.field_145982_r = p_i2350_1_;
    }
    
    @Override
    public int getSizeInventory() {
        return 27;
    }
    
    @Override
    public ItemStack getStackInSlot(final int p_70301_1_) {
        return this.field_145985_p[p_70301_1_];
    }
    
    @Override
    public ItemStack decrStackSize(final int p_70298_1_, final int p_70298_2_) {
        if (this.field_145985_p[p_70298_1_] == null) {
            return null;
        }
        if (this.field_145985_p[p_70298_1_].stackSize <= p_70298_2_) {
            final ItemStack var3 = this.field_145985_p[p_70298_1_];
            this.field_145985_p[p_70298_1_] = null;
            this.onInventoryChanged();
            return var3;
        }
        final ItemStack var3 = this.field_145985_p[p_70298_1_].splitStack(p_70298_2_);
        if (this.field_145985_p[p_70298_1_].stackSize == 0) {
            this.field_145985_p[p_70298_1_] = null;
        }
        this.onInventoryChanged();
        return var3;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int p_70304_1_) {
        if (this.field_145985_p[p_70304_1_] != null) {
            final ItemStack var2 = this.field_145985_p[p_70304_1_];
            this.field_145985_p[p_70304_1_] = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(final int p_70299_1_, final ItemStack p_70299_2_) {
        this.field_145985_p[p_70299_1_] = p_70299_2_;
        if (p_70299_2_ != null && p_70299_2_.stackSize > this.getInventoryStackLimit()) {
            p_70299_2_.stackSize = this.getInventoryStackLimit();
        }
        this.onInventoryChanged();
    }
    
    @Override
    public String getInventoryName() {
        return this.isInventoryNameLocalized() ? this.field_145981_s : "container.chest";
    }
    
    @Override
    public boolean isInventoryNameLocalized() {
        return this.field_145981_s != null && this.field_145981_s.length() > 0;
    }
    
    public void func_145976_a(final String p_145976_1_) {
        this.field_145981_s = p_145976_1_;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
        final NBTTagList var2 = p_145839_1_.getTagList("Items", 10);
        this.field_145985_p = new ItemStack[this.getSizeInventory()];
        if (p_145839_1_.func_150297_b("CustomName", 8)) {
            this.field_145981_s = p_145839_1_.getString("CustomName");
        }
        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            final NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            final int var5 = var4.getByte("Slot") & 0xFF;
            if (var5 >= 0 && var5 < this.field_145985_p.length) {
                this.field_145985_p[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
        final NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < this.field_145985_p.length; ++var3) {
            if (this.field_145985_p[var3] != null) {
                final NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.field_145985_p[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }
        p_145841_1_.setTag("Items", var2);
        if (this.isInventoryNameLocalized()) {
            p_145841_1_.setString("CustomName", this.field_145981_s);
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
    public void updateContainingBlockInfo() {
        super.updateContainingBlockInfo();
        this.field_145984_a = false;
    }
    
    private void func_145978_a(final TileEntityChest p_145978_1_, final int p_145978_2_) {
        if (p_145978_1_.isInvalid()) {
            this.field_145984_a = false;
        }
        else if (this.field_145984_a) {
            switch (p_145978_2_) {
                case 0: {
                    if (this.field_145988_l != p_145978_1_) {
                        this.field_145984_a = false;
                        break;
                    }
                    break;
                }
                case 1: {
                    if (this.field_145991_k != p_145978_1_) {
                        this.field_145984_a = false;
                        break;
                    }
                    break;
                }
                case 2: {
                    if (this.field_145992_i != p_145978_1_) {
                        this.field_145984_a = false;
                        break;
                    }
                    break;
                }
                case 3: {
                    if (this.field_145990_j != p_145978_1_) {
                        this.field_145984_a = false;
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    public void func_145979_i() {
        if (!this.field_145984_a) {
            this.field_145984_a = true;
            this.field_145992_i = null;
            this.field_145990_j = null;
            this.field_145991_k = null;
            this.field_145988_l = null;
            if (this.func_145977_a(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e)) {
                this.field_145991_k = (TileEntityChest)this.worldObj.getTileEntity(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e);
            }
            if (this.func_145977_a(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e)) {
                this.field_145990_j = (TileEntityChest)this.worldObj.getTileEntity(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e);
            }
            if (this.func_145977_a(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1)) {
                this.field_145992_i = (TileEntityChest)this.worldObj.getTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1);
            }
            if (this.func_145977_a(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1)) {
                this.field_145988_l = (TileEntityChest)this.worldObj.getTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1);
            }
            if (this.field_145992_i != null) {
                this.field_145992_i.func_145978_a(this, 0);
            }
            if (this.field_145988_l != null) {
                this.field_145988_l.func_145978_a(this, 2);
            }
            if (this.field_145990_j != null) {
                this.field_145990_j.func_145978_a(this, 1);
            }
            if (this.field_145991_k != null) {
                this.field_145991_k.func_145978_a(this, 3);
            }
        }
    }
    
    private boolean func_145977_a(final int p_145977_1_, final int p_145977_2_, final int p_145977_3_) {
        if (this.worldObj == null) {
            return false;
        }
        final Block var4 = this.worldObj.getBlock(p_145977_1_, p_145977_2_, p_145977_3_);
        return var4 instanceof BlockChest && ((BlockChest)var4).field_149956_a == this.func_145980_j();
    }
    
    @Override
    public void updateEntity() {
        super.updateEntity();
        this.func_145979_i();
        ++this.field_145983_q;
        if (!this.worldObj.isClient && this.field_145987_o != 0 && (this.field_145983_q + this.field_145851_c + this.field_145848_d + this.field_145849_e) % 200 == 0) {
            this.field_145987_o = 0;
            final float var1 = 5.0f;
            final List var2 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(this.field_145851_c - var1, this.field_145848_d - var1, this.field_145849_e - var1, this.field_145851_c + 1 + var1, this.field_145848_d + 1 + var1, this.field_145849_e + 1 + var1));
            for (final EntityPlayer var4 : var2) {
                if (var4.openContainer instanceof ContainerChest) {
                    final IInventory var5 = ((ContainerChest)var4.openContainer).getLowerChestInventory();
                    if (var5 != this && (!(var5 instanceof InventoryLargeChest) || !((InventoryLargeChest)var5).isPartOfLargeChest(this))) {
                        continue;
                    }
                    ++this.field_145987_o;
                }
            }
        }
        this.field_145986_n = this.field_145989_m;
        final float var1 = 0.1f;
        if (this.field_145987_o > 0 && this.field_145989_m == 0.0f && this.field_145992_i == null && this.field_145991_k == null) {
            double var6 = this.field_145851_c + 0.5;
            double var7 = this.field_145849_e + 0.5;
            if (this.field_145988_l != null) {
                var7 += 0.5;
            }
            if (this.field_145990_j != null) {
                var6 += 0.5;
            }
            this.worldObj.playSoundEffect(var6, this.field_145848_d + 0.5, var7, "random.chestopen", 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
        }
        if ((this.field_145987_o == 0 && this.field_145989_m > 0.0f) || (this.field_145987_o > 0 && this.field_145989_m < 1.0f)) {
            final float var8 = this.field_145989_m;
            if (this.field_145987_o > 0) {
                this.field_145989_m += var1;
            }
            else {
                this.field_145989_m -= var1;
            }
            if (this.field_145989_m > 1.0f) {
                this.field_145989_m = 1.0f;
            }
            final float var9 = 0.5f;
            if (this.field_145989_m < var9 && var8 >= var9 && this.field_145992_i == null && this.field_145991_k == null) {
                double var7 = this.field_145851_c + 0.5;
                double var10 = this.field_145849_e + 0.5;
                if (this.field_145988_l != null) {
                    var10 += 0.5;
                }
                if (this.field_145990_j != null) {
                    var7 += 0.5;
                }
                this.worldObj.playSoundEffect(var7, this.field_145848_d + 0.5, var10, "random.chestclosed", 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
            }
            if (this.field_145989_m < 0.0f) {
                this.field_145989_m = 0.0f;
            }
        }
    }
    
    @Override
    public boolean receiveClientEvent(final int p_145842_1_, final int p_145842_2_) {
        if (p_145842_1_ == 1) {
            this.field_145987_o = p_145842_2_;
            return true;
        }
        return super.receiveClientEvent(p_145842_1_, p_145842_2_);
    }
    
    @Override
    public void openInventory() {
        if (this.field_145987_o < 0) {
            this.field_145987_o = 0;
        }
        ++this.field_145987_o;
        this.worldObj.func_147452_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.getBlockType(), 1, this.field_145987_o);
        this.worldObj.notifyBlocksOfNeighborChange(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.getBlockType());
        this.worldObj.notifyBlocksOfNeighborChange(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e, this.getBlockType());
    }
    
    @Override
    public void closeInventory() {
        if (this.getBlockType() instanceof BlockChest) {
            --this.field_145987_o;
            this.worldObj.func_147452_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.getBlockType(), 1, this.field_145987_o);
            this.worldObj.notifyBlocksOfNeighborChange(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.getBlockType());
            this.worldObj.notifyBlocksOfNeighborChange(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e, this.getBlockType());
        }
    }
    
    @Override
    public boolean isItemValidForSlot(final int p_94041_1_, final ItemStack p_94041_2_) {
        return true;
    }
    
    @Override
    public void invalidate() {
        super.invalidate();
        this.updateContainingBlockInfo();
        this.func_145979_i();
    }
    
    public int func_145980_j() {
        if (this.field_145982_r == -1) {
            if (this.worldObj == null || !(this.getBlockType() instanceof BlockChest)) {
                return 0;
            }
            this.field_145982_r = ((BlockChest)this.getBlockType()).field_149956_a;
        }
        return this.field_145982_r;
    }
}
