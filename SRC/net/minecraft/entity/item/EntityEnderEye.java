package net.minecraft.entity.item;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

public class EntityEnderEye extends Entity
{
    private double targetX;
    private double targetY;
    private double targetZ;
    private int despawnTimer;
    private boolean shatterOrDrop;
    private static final String __OBFID = "CL_00001716";
    
    public EntityEnderEye(final World p_i1757_1_) {
        super(p_i1757_1_);
        this.setSize(0.25f, 0.25f);
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    public boolean isInRangeToRenderDist(final double p_70112_1_) {
        double var3 = this.boundingBox.getAverageEdgeLength() * 4.0;
        var3 *= 64.0;
        return p_70112_1_ < var3 * var3;
    }
    
    public EntityEnderEye(final World p_i1758_1_, final double p_i1758_2_, final double p_i1758_4_, final double p_i1758_6_) {
        super(p_i1758_1_);
        this.despawnTimer = 0;
        this.setSize(0.25f, 0.25f);
        this.setPosition(p_i1758_2_, p_i1758_4_, p_i1758_6_);
        this.yOffset = 0.0f;
    }
    
    public void moveTowards(final double p_70220_1_, final int p_70220_3_, final double p_70220_4_) {
        final double var6 = p_70220_1_ - this.posX;
        final double var7 = p_70220_4_ - this.posZ;
        final float var8 = MathHelper.sqrt_double(var6 * var6 + var7 * var7);
        if (var8 > 12.0f) {
            this.targetX = this.posX + var6 / var8 * 12.0;
            this.targetZ = this.posZ + var7 / var8 * 12.0;
            this.targetY = this.posY + 8.0;
        }
        else {
            this.targetX = p_70220_1_;
            this.targetY = p_70220_3_;
            this.targetZ = p_70220_4_;
        }
        this.despawnTimer = 0;
        this.shatterOrDrop = (this.rand.nextInt(5) > 0);
    }
    
    @Override
    public void setVelocity(final double p_70016_1_, final double p_70016_3_, final double p_70016_5_) {
        this.motionX = p_70016_1_;
        this.motionY = p_70016_3_;
        this.motionZ = p_70016_5_;
        if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            final float var7 = MathHelper.sqrt_double(p_70016_1_ * p_70016_1_ + p_70016_5_ * p_70016_5_);
            final float n = (float)(Math.atan2(p_70016_1_, p_70016_5_) * 180.0 / 3.141592653589793);
            this.rotationYaw = n;
            this.prevRotationYaw = n;
            final float n2 = (float)(Math.atan2(p_70016_3_, var7) * 180.0 / 3.141592653589793);
            this.rotationPitch = n2;
            this.prevRotationPitch = n2;
        }
    }
    
    @Override
    public void onUpdate() {
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        super.onUpdate();
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        final float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
        this.rotationPitch = (float)(Math.atan2(this.motionY, var1) * 180.0 / 3.141592653589793);
        while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
            this.prevRotationPitch -= 360.0f;
        }
        while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
            this.prevRotationPitch += 360.0f;
        }
        while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
            this.prevRotationYaw -= 360.0f;
        }
        while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
            this.prevRotationYaw += 360.0f;
        }
        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2f;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2f;
        if (!this.worldObj.isClient) {
            final double var2 = this.targetX - this.posX;
            final double var3 = this.targetZ - this.posZ;
            final float var4 = (float)Math.sqrt(var2 * var2 + var3 * var3);
            final float var5 = (float)Math.atan2(var3, var2);
            double var6 = var1 + (var4 - var1) * 0.0025;
            if (var4 < 1.0f) {
                var6 *= 0.8;
                this.motionY *= 0.8;
            }
            this.motionX = Math.cos(var5) * var6;
            this.motionZ = Math.sin(var5) * var6;
            if (this.posY < this.targetY) {
                this.motionY += (1.0 - this.motionY) * 0.014999999664723873;
            }
            else {
                this.motionY += (-1.0 - this.motionY) * 0.014999999664723873;
            }
        }
        final float var7 = 0.25f;
        if (this.isInWater()) {
            for (int var8 = 0; var8 < 4; ++var8) {
                this.worldObj.spawnParticle("bubble", this.posX - this.motionX * var7, this.posY - this.motionY * var7, this.posZ - this.motionZ * var7, this.motionX, this.motionY, this.motionZ);
            }
        }
        else {
            this.worldObj.spawnParticle("portal", this.posX - this.motionX * var7 + this.rand.nextDouble() * 0.6 - 0.3, this.posY - this.motionY * var7 - 0.5, this.posZ - this.motionZ * var7 + this.rand.nextDouble() * 0.6 - 0.3, this.motionX, this.motionY, this.motionZ);
        }
        if (!this.worldObj.isClient) {
            this.setPosition(this.posX, this.posY, this.posZ);
            ++this.despawnTimer;
            if (this.despawnTimer > 80 && !this.worldObj.isClient) {
                this.setDead();
                if (this.shatterOrDrop) {
                    this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.ender_eye)));
                }
                else {
                    this.worldObj.playAuxSFX(2003, (int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ), 0);
                }
            }
        }
    }
    
    public void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
    }
    
    public void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
    }
    
    @Override
    public float getShadowSize() {
        return 0.0f;
    }
    
    @Override
    public float getBrightness(final float p_70013_1_) {
        return 1.0f;
    }
    
    @Override
    public int getBrightnessForRender(final float p_70070_1_) {
        return 15728880;
    }
    
    @Override
    public boolean canAttackWithItem() {
        return false;
    }
}
