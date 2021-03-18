package net.minecraft.entity;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

public abstract class EntityAgeable extends EntityCreature
{
    private float field_98056_d;
    private float field_98057_e;
    private static final String __OBFID = "CL_00001530";
    
    public EntityAgeable(final World p_i1578_1_) {
        super(p_i1578_1_);
        this.field_98056_d = -1.0f;
    }
    
    public abstract EntityAgeable createChild(final EntityAgeable p0);
    
    public boolean interact(final EntityPlayer p_70085_1_) {
        final ItemStack var2 = p_70085_1_.inventory.getCurrentItem();
        if (var2 != null && var2.getItem() == Items.spawn_egg) {
            if (!this.worldObj.isClient) {
                final Class var3 = EntityList.getClassFromID(var2.getItemDamage());
                if (var3 != null && var3.isAssignableFrom(this.getClass())) {
                    final EntityAgeable var4 = this.createChild(this);
                    if (var4 != null) {
                        var4.setGrowingAge(-24000);
                        var4.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0f, 0.0f);
                        this.worldObj.spawnEntityInWorld(var4);
                        if (var2.hasDisplayName()) {
                            var4.setCustomNameTag(var2.getDisplayName());
                        }
                        if (!p_70085_1_.capabilities.isCreativeMode) {
                            final ItemStack itemStack = var2;
                            --itemStack.stackSize;
                            if (var2.stackSize <= 0) {
                                p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem, null);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(12, new Integer(0));
    }
    
    public int getGrowingAge() {
        return this.dataWatcher.getWatchableObjectInt(12);
    }
    
    public void addGrowth(final int p_110195_1_) {
        int var2 = this.getGrowingAge();
        var2 += p_110195_1_ * 20;
        if (var2 > 0) {
            var2 = 0;
        }
        this.setGrowingAge(var2);
    }
    
    public void setGrowingAge(final int p_70873_1_) {
        this.dataWatcher.updateObject(12, p_70873_1_);
        this.setScaleForAge(this.isChild());
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
        p_70014_1_.setInteger("Age", this.getGrowingAge());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
        this.setGrowingAge(p_70037_1_.getInteger("Age"));
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.worldObj.isClient) {
            this.setScaleForAge(this.isChild());
        }
        else {
            int var1 = this.getGrowingAge();
            if (var1 < 0) {
                ++var1;
                this.setGrowingAge(var1);
            }
            else if (var1 > 0) {
                --var1;
                this.setGrowingAge(var1);
            }
        }
    }
    
    @Override
    public boolean isChild() {
        return this.getGrowingAge() < 0;
    }
    
    public void setScaleForAge(final boolean p_98054_1_) {
        this.setScale(p_98054_1_ ? 0.5f : 1.0f);
    }
    
    @Override
    protected final void setSize(final float p_70105_1_, final float p_70105_2_) {
        final boolean var3 = this.field_98056_d > 0.0f;
        this.field_98056_d = p_70105_1_;
        this.field_98057_e = p_70105_2_;
        if (!var3) {
            this.setScale(1.0f);
        }
    }
    
    protected final void setScale(final float p_98055_1_) {
        super.setSize(this.field_98056_d * p_98055_1_, this.field_98057_e * p_98055_1_);
    }
}
