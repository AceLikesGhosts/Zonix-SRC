package net.minecraft.entity;

import net.minecraft.world.*;
import net.minecraft.block.material.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;

public abstract class EntityHanging extends Entity
{
    private int tickCounter1;
    public int hangingDirection;
    public int field_146063_b;
    public int field_146064_c;
    public int field_146062_d;
    private static final String __OBFID = "CL_00001546";
    
    public EntityHanging(final World p_i1588_1_) {
        super(p_i1588_1_);
        this.yOffset = 0.0f;
        this.setSize(0.5f, 0.5f);
    }
    
    public EntityHanging(final World p_i1589_1_, final int p_i1589_2_, final int p_i1589_3_, final int p_i1589_4_, final int p_i1589_5_) {
        this(p_i1589_1_);
        this.field_146063_b = p_i1589_2_;
        this.field_146064_c = p_i1589_3_;
        this.field_146062_d = p_i1589_4_;
    }
    
    @Override
    protected void entityInit() {
    }
    
    public void setDirection(final int p_82328_1_) {
        this.hangingDirection = p_82328_1_;
        final float n = (float)(p_82328_1_ * 90);
        this.rotationYaw = n;
        this.prevRotationYaw = n;
        float var2 = (float)this.getWidthPixels();
        float var3 = (float)this.getHeightPixels();
        float var4 = (float)this.getWidthPixels();
        if (p_82328_1_ != 2 && p_82328_1_ != 0) {
            var2 = 0.5f;
        }
        else {
            var4 = 0.5f;
            final float n2 = (float)(Direction.rotateOpposite[p_82328_1_] * 90);
            this.prevRotationYaw = n2;
            this.rotationYaw = n2;
        }
        var2 /= 32.0f;
        var3 /= 32.0f;
        var4 /= 32.0f;
        float var5 = this.field_146063_b + 0.5f;
        float var6 = this.field_146064_c + 0.5f;
        float var7 = this.field_146062_d + 0.5f;
        final float var8 = 0.5625f;
        if (p_82328_1_ == 2) {
            var7 -= var8;
        }
        if (p_82328_1_ == 1) {
            var5 -= var8;
        }
        if (p_82328_1_ == 0) {
            var7 += var8;
        }
        if (p_82328_1_ == 3) {
            var5 += var8;
        }
        if (p_82328_1_ == 2) {
            var5 -= this.func_70517_b(this.getWidthPixels());
        }
        if (p_82328_1_ == 1) {
            var7 += this.func_70517_b(this.getWidthPixels());
        }
        if (p_82328_1_ == 0) {
            var5 += this.func_70517_b(this.getWidthPixels());
        }
        if (p_82328_1_ == 3) {
            var7 -= this.func_70517_b(this.getWidthPixels());
        }
        var6 += this.func_70517_b(this.getHeightPixels());
        this.setPosition(var5, var6, var7);
        final float var9 = -0.03125f;
        this.boundingBox.setBounds(var5 - var2 - var9, var6 - var3 - var9, var7 - var4 - var9, var5 + var2 + var9, var6 + var3 + var9, var7 + var4 + var9);
    }
    
    private float func_70517_b(final int p_70517_1_) {
        return (p_70517_1_ == 32) ? 0.5f : ((p_70517_1_ == 64) ? 0.5f : 0.0f);
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.tickCounter1++ == 100 && !this.worldObj.isClient) {
            this.tickCounter1 = 0;
            if (!this.isDead && !this.onValidSurface()) {
                this.setDead();
                this.onBroken(null);
            }
        }
    }
    
    public boolean onValidSurface() {
        if (!this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty()) {
            return false;
        }
        final int var1 = Math.max(1, this.getWidthPixels() / 16);
        final int var2 = Math.max(1, this.getHeightPixels() / 16);
        int var3 = this.field_146063_b;
        int var4 = this.field_146064_c;
        int var5 = this.field_146062_d;
        if (this.hangingDirection == 2) {
            var3 = MathHelper.floor_double(this.posX - this.getWidthPixels() / 32.0f);
        }
        if (this.hangingDirection == 1) {
            var5 = MathHelper.floor_double(this.posZ - this.getWidthPixels() / 32.0f);
        }
        if (this.hangingDirection == 0) {
            var3 = MathHelper.floor_double(this.posX - this.getWidthPixels() / 32.0f);
        }
        if (this.hangingDirection == 3) {
            var5 = MathHelper.floor_double(this.posZ - this.getWidthPixels() / 32.0f);
        }
        var4 = MathHelper.floor_double(this.posY - this.getHeightPixels() / 32.0f);
        for (int var6 = 0; var6 < var1; ++var6) {
            for (int var7 = 0; var7 < var2; ++var7) {
                Material var8;
                if (this.hangingDirection != 2 && this.hangingDirection != 0) {
                    var8 = this.worldObj.getBlock(this.field_146063_b, var4 + var7, var5 + var6).getMaterial();
                }
                else {
                    var8 = this.worldObj.getBlock(var3 + var6, var4 + var7, this.field_146062_d).getMaterial();
                }
                if (!var8.isSolid()) {
                    return false;
                }
            }
        }
        final List var9 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox);
        for (final Entity var11 : var9) {
            if (var11 instanceof EntityHanging) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return true;
    }
    
    @Override
    public boolean hitByEntity(final Entity p_85031_1_) {
        return p_85031_1_ instanceof EntityPlayer && this.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)p_85031_1_), 0.0f);
    }
    
    @Override
    public void func_145781_i(final int p_145781_1_) {
        this.worldObj.func_147450_X();
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource p_70097_1_, final float p_70097_2_) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (!this.isDead && !this.worldObj.isClient) {
            this.setDead();
            this.setBeenAttacked();
            this.onBroken(p_70097_1_.getEntity());
        }
        return true;
    }
    
    @Override
    public void moveEntity(final double p_70091_1_, final double p_70091_3_, final double p_70091_5_) {
        if (!this.worldObj.isClient && !this.isDead && p_70091_1_ * p_70091_1_ + p_70091_3_ * p_70091_3_ + p_70091_5_ * p_70091_5_ > 0.0) {
            this.setDead();
            this.onBroken(null);
        }
    }
    
    @Override
    public void addVelocity(final double p_70024_1_, final double p_70024_3_, final double p_70024_5_) {
        if (!this.worldObj.isClient && !this.isDead && p_70024_1_ * p_70024_1_ + p_70024_3_ * p_70024_3_ + p_70024_5_ * p_70024_5_ > 0.0) {
            this.setDead();
            this.onBroken(null);
        }
    }
    
    public void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        p_70014_1_.setByte("Direction", (byte)this.hangingDirection);
        p_70014_1_.setInteger("TileX", this.field_146063_b);
        p_70014_1_.setInteger("TileY", this.field_146064_c);
        p_70014_1_.setInteger("TileZ", this.field_146062_d);
        switch (this.hangingDirection) {
            case 0: {
                p_70014_1_.setByte("Dir", (byte)2);
                break;
            }
            case 1: {
                p_70014_1_.setByte("Dir", (byte)1);
                break;
            }
            case 2: {
                p_70014_1_.setByte("Dir", (byte)0);
                break;
            }
            case 3: {
                p_70014_1_.setByte("Dir", (byte)3);
                break;
            }
        }
    }
    
    public void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        if (p_70037_1_.func_150297_b("Direction", 99)) {
            this.hangingDirection = p_70037_1_.getByte("Direction");
        }
        else {
            switch (p_70037_1_.getByte("Dir")) {
                case 0: {
                    this.hangingDirection = 2;
                    break;
                }
                case 1: {
                    this.hangingDirection = 1;
                    break;
                }
                case 2: {
                    this.hangingDirection = 0;
                    break;
                }
                case 3: {
                    this.hangingDirection = 3;
                    break;
                }
            }
        }
        this.field_146063_b = p_70037_1_.getInteger("TileX");
        this.field_146064_c = p_70037_1_.getInteger("TileY");
        this.field_146062_d = p_70037_1_.getInteger("TileZ");
        this.setDirection(this.hangingDirection);
    }
    
    public abstract int getWidthPixels();
    
    public abstract int getHeightPixels();
    
    public abstract void onBroken(final Entity p0);
    
    @Override
    protected boolean shouldSetPosAfterLoading() {
        return false;
    }
}
