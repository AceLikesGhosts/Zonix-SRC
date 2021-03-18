package net.minecraft.entity.item;

import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.inventory.*;

public abstract class EntityMinecartContainer extends EntityMinecart implements IInventory
{
    private ItemStack[] minecartContainerItems;
    private boolean dropContentsWhenDead;
    private static final String __OBFID = "CL_00001674";
    
    public EntityMinecartContainer(final World p_i1716_1_) {
        super(p_i1716_1_);
        this.minecartContainerItems = new ItemStack[36];
        this.dropContentsWhenDead = true;
    }
    
    public EntityMinecartContainer(final World p_i1717_1_, final double p_i1717_2_, final double p_i1717_4_, final double p_i1717_6_) {
        super(p_i1717_1_, p_i1717_2_, p_i1717_4_, p_i1717_6_);
        this.minecartContainerItems = new ItemStack[36];
        this.dropContentsWhenDead = true;
    }
    
    @Override
    public void killMinecart(final DamageSource p_94095_1_) {
        super.killMinecart(p_94095_1_);
        for (int var2 = 0; var2 < this.getSizeInventory(); ++var2) {
            final ItemStack var3 = this.getStackInSlot(var2);
            if (var3 != null) {
                final float var4 = this.rand.nextFloat() * 0.8f + 0.1f;
                final float var5 = this.rand.nextFloat() * 0.8f + 0.1f;
                final float var6 = this.rand.nextFloat() * 0.8f + 0.1f;
                while (var3.stackSize > 0) {
                    int var7 = this.rand.nextInt(21) + 10;
                    if (var7 > var3.stackSize) {
                        var7 = var3.stackSize;
                    }
                    final ItemStack itemStack = var3;
                    itemStack.stackSize -= var7;
                    final EntityItem var8 = new EntityItem(this.worldObj, this.posX + var4, this.posY + var5, this.posZ + var6, new ItemStack(var3.getItem(), var7, var3.getItemDamage()));
                    final float var9 = 0.05f;
                    var8.motionX = (float)this.rand.nextGaussian() * var9;
                    var8.motionY = (float)this.rand.nextGaussian() * var9 + 0.2f;
                    var8.motionZ = (float)this.rand.nextGaussian() * var9;
                    this.worldObj.spawnEntityInWorld(var8);
                }
            }
        }
    }
    
    @Override
    public ItemStack getStackInSlot(final int p_70301_1_) {
        return this.minecartContainerItems[p_70301_1_];
    }
    
    @Override
    public ItemStack decrStackSize(final int p_70298_1_, final int p_70298_2_) {
        if (this.minecartContainerItems[p_70298_1_] == null) {
            return null;
        }
        if (this.minecartContainerItems[p_70298_1_].stackSize <= p_70298_2_) {
            final ItemStack var3 = this.minecartContainerItems[p_70298_1_];
            this.minecartContainerItems[p_70298_1_] = null;
            return var3;
        }
        final ItemStack var3 = this.minecartContainerItems[p_70298_1_].splitStack(p_70298_2_);
        if (this.minecartContainerItems[p_70298_1_].stackSize == 0) {
            this.minecartContainerItems[p_70298_1_] = null;
        }
        return var3;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int p_70304_1_) {
        if (this.minecartContainerItems[p_70304_1_] != null) {
            final ItemStack var2 = this.minecartContainerItems[p_70304_1_];
            this.minecartContainerItems[p_70304_1_] = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(final int p_70299_1_, final ItemStack p_70299_2_) {
        this.minecartContainerItems[p_70299_1_] = p_70299_2_;
        if (p_70299_2_ != null && p_70299_2_.stackSize > this.getInventoryStackLimit()) {
            p_70299_2_.stackSize = this.getInventoryStackLimit();
        }
    }
    
    @Override
    public void onInventoryChanged() {
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer p_70300_1_) {
        return !this.isDead && p_70300_1_.getDistanceSqToEntity(this) <= 64.0;
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
    
    @Override
    public String getInventoryName() {
        return this.isInventoryNameLocalized() ? this.func_95999_t() : "container.minecart";
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public void travelToDimension(final int p_71027_1_) {
        this.dropContentsWhenDead = false;
        super.travelToDimension(p_71027_1_);
    }
    
    @Override
    public void setDead() {
        if (this.dropContentsWhenDead) {
            for (int var1 = 0; var1 < this.getSizeInventory(); ++var1) {
                final ItemStack var2 = this.getStackInSlot(var1);
                if (var2 != null) {
                    final float var3 = this.rand.nextFloat() * 0.8f + 0.1f;
                    final float var4 = this.rand.nextFloat() * 0.8f + 0.1f;
                    final float var5 = this.rand.nextFloat() * 0.8f + 0.1f;
                    while (var2.stackSize > 0) {
                        int var6 = this.rand.nextInt(21) + 10;
                        if (var6 > var2.stackSize) {
                            var6 = var2.stackSize;
                        }
                        final ItemStack itemStack = var2;
                        itemStack.stackSize -= var6;
                        final EntityItem var7 = new EntityItem(this.worldObj, this.posX + var3, this.posY + var4, this.posZ + var5, new ItemStack(var2.getItem(), var6, var2.getItemDamage()));
                        if (var2.hasTagCompound()) {
                            var7.getEntityItem().setTagCompound((NBTTagCompound)var2.getTagCompound().copy());
                        }
                        final float var8 = 0.05f;
                        var7.motionX = (float)this.rand.nextGaussian() * var8;
                        var7.motionY = (float)this.rand.nextGaussian() * var8 + 0.2f;
                        var7.motionZ = (float)this.rand.nextGaussian() * var8;
                        this.worldObj.spawnEntityInWorld(var7);
                    }
                }
            }
        }
        super.setDead();
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
        final NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < this.minecartContainerItems.length; ++var3) {
            if (this.minecartContainerItems[var3] != null) {
                final NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.minecartContainerItems[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }
        p_70014_1_.setTag("Items", var2);
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
        final NBTTagList var2 = p_70037_1_.getTagList("Items", 10);
        this.minecartContainerItems = new ItemStack[this.getSizeInventory()];
        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            final NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            final int var5 = var4.getByte("Slot") & 0xFF;
            if (var5 >= 0 && var5 < this.minecartContainerItems.length) {
                this.minecartContainerItems[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer p_130002_1_) {
        if (!this.worldObj.isClient) {
            p_130002_1_.displayGUIChest(this);
        }
        return true;
    }
    
    @Override
    protected void applyDrag() {
        final int var1 = 15 - Container.calcRedstoneFromInventory(this);
        final float var2 = 0.98f + var1 * 0.001f;
        this.motionX *= var2;
        this.motionY *= 0.0;
        this.motionZ *= var2;
    }
}
