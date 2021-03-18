package net.minecraft.entity.item;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class EntityEnderCrystal extends Entity
{
    public int innerRotation;
    public int health;
    private static final String __OBFID = "CL_00001658";
    
    public EntityEnderCrystal(final World p_i1698_1_) {
        super(p_i1698_1_);
        this.preventEntitySpawning = true;
        this.setSize(2.0f, 2.0f);
        this.yOffset = this.height / 2.0f;
        this.health = 5;
        this.innerRotation = this.rand.nextInt(100000);
    }
    
    public EntityEnderCrystal(final World p_i1699_1_, final double p_i1699_2_, final double p_i1699_4_, final double p_i1699_6_) {
        this(p_i1699_1_);
        this.setPosition(p_i1699_2_, p_i1699_4_, p_i1699_6_);
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(8, this.health);
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        ++this.innerRotation;
        this.dataWatcher.updateObject(8, this.health);
        final int var1 = MathHelper.floor_double(this.posX);
        final int var2 = MathHelper.floor_double(this.posY);
        final int var3 = MathHelper.floor_double(this.posZ);
        if (this.worldObj.provider instanceof WorldProviderEnd && this.worldObj.getBlock(var1, var2, var3) != Blocks.fire) {
            this.worldObj.setBlock(var1, var2, var3, Blocks.fire);
        }
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
    }
    
    @Override
    public float getShadowSize() {
        return 0.0f;
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return true;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource p_70097_1_, final float p_70097_2_) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (!this.isDead && !this.worldObj.isClient) {
            this.health = 0;
            if (this.health <= 0) {
                this.setDead();
                if (!this.worldObj.isClient) {
                    this.worldObj.createExplosion(null, this.posX, this.posY, this.posZ, 6.0f, true);
                }
            }
        }
        return true;
    }
}
