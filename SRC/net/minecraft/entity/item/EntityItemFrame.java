package net.minecraft.entity.item;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.world.storage.*;
import net.minecraft.nbt.*;

public class EntityItemFrame extends EntityHanging
{
    private float itemDropChance;
    private static final String __OBFID = "CL_00001547";
    
    public EntityItemFrame(final World p_i1590_1_) {
        super(p_i1590_1_);
        this.itemDropChance = 1.0f;
    }
    
    public EntityItemFrame(final World p_i1591_1_, final int p_i1591_2_, final int p_i1591_3_, final int p_i1591_4_, final int p_i1591_5_) {
        super(p_i1591_1_, p_i1591_2_, p_i1591_3_, p_i1591_4_, p_i1591_5_);
        this.itemDropChance = 1.0f;
        this.setDirection(p_i1591_5_);
    }
    
    @Override
    protected void entityInit() {
        this.getDataWatcher().addObjectByDataType(2, 5);
        this.getDataWatcher().addObject(3, 0);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource p_70097_1_, final float p_70097_2_) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (this.getDisplayedItem() != null) {
            if (!this.worldObj.isClient) {
                this.func_146065_b(p_70097_1_.getEntity(), false);
                this.setDisplayedItem(null);
            }
            return true;
        }
        return super.attackEntityFrom(p_70097_1_, p_70097_2_);
    }
    
    @Override
    public int getWidthPixels() {
        return 9;
    }
    
    @Override
    public int getHeightPixels() {
        return 9;
    }
    
    @Override
    public boolean isInRangeToRenderDist(final double p_70112_1_) {
        double var3 = 16.0;
        var3 *= 64.0 * this.renderDistanceWeight;
        return p_70112_1_ < var3 * var3;
    }
    
    @Override
    public void onBroken(final Entity p_110128_1_) {
        this.func_146065_b(p_110128_1_, true);
    }
    
    public void func_146065_b(final Entity p_146065_1_, final boolean p_146065_2_) {
        ItemStack var3 = this.getDisplayedItem();
        if (p_146065_1_ instanceof EntityPlayer) {
            final EntityPlayer var4 = (EntityPlayer)p_146065_1_;
            if (var4.capabilities.isCreativeMode) {
                this.removeFrameFromMap(var3);
                return;
            }
        }
        if (p_146065_2_) {
            this.entityDropItem(new ItemStack(Items.item_frame), 0.0f);
        }
        if (var3 != null && this.rand.nextFloat() < this.itemDropChance) {
            var3 = var3.copy();
            this.removeFrameFromMap(var3);
            this.entityDropItem(var3, 0.0f);
        }
    }
    
    private void removeFrameFromMap(final ItemStack p_110131_1_) {
        if (p_110131_1_ != null) {
            if (p_110131_1_.getItem() == Items.filled_map) {
                final MapData var2 = ((ItemMap)p_110131_1_.getItem()).getMapData(p_110131_1_, this.worldObj);
                var2.playersVisibleOnMap.remove("frame-" + this.getEntityId());
            }
            p_110131_1_.setItemFrame(null);
        }
    }
    
    public ItemStack getDisplayedItem() {
        return this.getDataWatcher().getWatchableObjectItemStack(2);
    }
    
    public void setDisplayedItem(ItemStack p_82334_1_) {
        if (p_82334_1_ != null) {
            p_82334_1_ = p_82334_1_.copy();
            p_82334_1_.stackSize = 1;
            p_82334_1_.setItemFrame(this);
        }
        this.getDataWatcher().updateObject(2, p_82334_1_);
        this.getDataWatcher().setObjectWatched(2);
    }
    
    public int getRotation() {
        return this.getDataWatcher().getWatchableObjectByte(3);
    }
    
    public void setItemRotation(final int p_82336_1_) {
        this.getDataWatcher().updateObject(3, (byte)(p_82336_1_ % 4));
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        if (this.getDisplayedItem() != null) {
            p_70014_1_.setTag("Item", this.getDisplayedItem().writeToNBT(new NBTTagCompound()));
            p_70014_1_.setByte("ItemRotation", (byte)this.getRotation());
            p_70014_1_.setFloat("ItemDropChance", this.itemDropChance);
        }
        super.writeEntityToNBT(p_70014_1_);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        final NBTTagCompound var2 = p_70037_1_.getCompoundTag("Item");
        if (var2 != null && !var2.hasNoTags()) {
            this.setDisplayedItem(ItemStack.loadItemStackFromNBT(var2));
            this.setItemRotation(p_70037_1_.getByte("ItemRotation"));
            if (p_70037_1_.func_150297_b("ItemDropChance", 99)) {
                this.itemDropChance = p_70037_1_.getFloat("ItemDropChance");
            }
        }
        super.readEntityFromNBT(p_70037_1_);
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer p_130002_1_) {
        if (this.getDisplayedItem() == null) {
            final ItemStack var2 = p_130002_1_.getHeldItem();
            if (var2 != null && !this.worldObj.isClient) {
                this.setDisplayedItem(var2);
                if (!p_130002_1_.capabilities.isCreativeMode) {
                    final ItemStack itemStack = var2;
                    if (--itemStack.stackSize <= 0) {
                        p_130002_1_.inventory.setInventorySlotContents(p_130002_1_.inventory.currentItem, null);
                    }
                }
            }
        }
        else if (!this.worldObj.isClient) {
            this.setItemRotation(this.getRotation() + 1);
        }
        return true;
    }
}
