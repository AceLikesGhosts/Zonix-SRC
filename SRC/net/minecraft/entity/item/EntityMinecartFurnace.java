package net.minecraft.entity.item;

import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;

public class EntityMinecartFurnace extends EntityMinecart
{
    private int fuel;
    public double pushX;
    public double pushZ;
    private static final String __OBFID = "CL_00001675";
    
    public EntityMinecartFurnace(final World p_i1718_1_) {
        super(p_i1718_1_);
    }
    
    public EntityMinecartFurnace(final World p_i1719_1_, final double p_i1719_2_, final double p_i1719_4_, final double p_i1719_6_) {
        super(p_i1719_1_, p_i1719_2_, p_i1719_4_, p_i1719_6_);
    }
    
    @Override
    public int getMinecartType() {
        return 2;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.fuel > 0) {
            --this.fuel;
        }
        if (this.fuel <= 0) {
            final double n = 0.0;
            this.pushZ = n;
            this.pushX = n;
        }
        this.setMinecartPowered(this.fuel > 0);
        if (this.isMinecartPowered() && this.rand.nextInt(4) == 0) {
            this.worldObj.spawnParticle("largesmoke", this.posX, this.posY + 0.8, this.posZ, 0.0, 0.0, 0.0);
        }
    }
    
    @Override
    public void killMinecart(final DamageSource p_94095_1_) {
        super.killMinecart(p_94095_1_);
        if (!p_94095_1_.isExplosion()) {
            this.entityDropItem(new ItemStack(Blocks.furnace, 1), 0.0f);
        }
    }
    
    @Override
    protected void func_145821_a(final int p_145821_1_, final int p_145821_2_, final int p_145821_3_, final double p_145821_4_, final double p_145821_6_, final Block p_145821_8_, final int p_145821_9_) {
        super.func_145821_a(p_145821_1_, p_145821_2_, p_145821_3_, p_145821_4_, p_145821_6_, p_145821_8_, p_145821_9_);
        double var10 = this.pushX * this.pushX + this.pushZ * this.pushZ;
        if (var10 > 1.0E-4 && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.001) {
            var10 = MathHelper.sqrt_double(var10);
            this.pushX /= var10;
            this.pushZ /= var10;
            if (this.pushX * this.motionX + this.pushZ * this.motionZ < 0.0) {
                this.pushX = 0.0;
                this.pushZ = 0.0;
            }
            else {
                this.pushX = this.motionX;
                this.pushZ = this.motionZ;
            }
        }
    }
    
    @Override
    protected void applyDrag() {
        double var1 = this.pushX * this.pushX + this.pushZ * this.pushZ;
        if (var1 > 1.0E-4) {
            var1 = MathHelper.sqrt_double(var1);
            this.pushX /= var1;
            this.pushZ /= var1;
            final double var2 = 0.05;
            this.motionX *= 0.800000011920929;
            this.motionY *= 0.0;
            this.motionZ *= 0.800000011920929;
            this.motionX += this.pushX * var2;
            this.motionZ += this.pushZ * var2;
        }
        else {
            this.motionX *= 0.9800000190734863;
            this.motionY *= 0.0;
            this.motionZ *= 0.9800000190734863;
        }
        super.applyDrag();
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer p_130002_1_) {
        final ItemStack var2 = p_130002_1_.inventory.getCurrentItem();
        if (var2 != null && var2.getItem() == Items.coal) {
            if (!p_130002_1_.capabilities.isCreativeMode) {
                final ItemStack itemStack = var2;
                if (--itemStack.stackSize == 0) {
                    p_130002_1_.inventory.setInventorySlotContents(p_130002_1_.inventory.currentItem, null);
                }
            }
            this.fuel += 3600;
        }
        this.pushX = this.posX - p_130002_1_.posX;
        this.pushZ = this.posZ - p_130002_1_.posZ;
        return true;
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
        p_70014_1_.setDouble("PushX", this.pushX);
        p_70014_1_.setDouble("PushZ", this.pushZ);
        p_70014_1_.setShort("Fuel", (short)this.fuel);
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
        this.pushX = p_70037_1_.getDouble("PushX");
        this.pushZ = p_70037_1_.getDouble("PushZ");
        this.fuel = p_70037_1_.getShort("Fuel");
    }
    
    protected boolean isMinecartPowered() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0x0;
    }
    
    protected void setMinecartPowered(final boolean p_94107_1_) {
        if (p_94107_1_) {
            this.dataWatcher.updateObject(16, (byte)(this.dataWatcher.getWatchableObjectByte(16) | 0x1));
        }
        else {
            this.dataWatcher.updateObject(16, (byte)(this.dataWatcher.getWatchableObjectByte(16) & 0xFFFFFFFE));
        }
    }
    
    @Override
    public Block func_145817_o() {
        return Blocks.lit_furnace;
    }
    
    @Override
    public int getDefaultDisplayTileData() {
        return 2;
    }
}
