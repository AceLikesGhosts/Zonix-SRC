package net.minecraft.entity;

import net.minecraft.world.*;
import net.minecraft.util.*;

public abstract class EntityFlying extends EntityLiving
{
    private static final String __OBFID = "CL_00001545";
    
    public EntityFlying(final World p_i1587_1_) {
        super(p_i1587_1_);
    }
    
    @Override
    protected void fall(final float p_70069_1_) {
    }
    
    @Override
    protected void updateFallState(final double p_70064_1_, final boolean p_70064_3_) {
    }
    
    @Override
    public void moveEntityWithHeading(final float p_70612_1_, final float p_70612_2_) {
        if (this.isInWater()) {
            this.moveFlying(p_70612_1_, p_70612_2_, 0.02f);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.800000011920929;
            this.motionY *= 0.800000011920929;
            this.motionZ *= 0.800000011920929;
        }
        else if (this.handleLavaMovement()) {
            this.moveFlying(p_70612_1_, p_70612_2_, 0.02f);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.5;
            this.motionY *= 0.5;
            this.motionZ *= 0.5;
        }
        else {
            float var3 = 0.91f;
            if (this.onGround) {
                var3 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91f;
            }
            final float var4 = 0.16277136f / (var3 * var3 * var3);
            this.moveFlying(p_70612_1_, p_70612_2_, this.onGround ? (0.1f * var4) : 0.02f);
            var3 = 0.91f;
            if (this.onGround) {
                var3 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91f;
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= var3;
            this.motionY *= var3;
            this.motionZ *= var3;
        }
        this.prevLimbSwingAmount = this.limbSwingAmount;
        final double var5 = this.posX - this.prevPosX;
        final double var6 = this.posZ - this.prevPosZ;
        float var7 = MathHelper.sqrt_double(var5 * var5 + var6 * var6) * 4.0f;
        if (var7 > 1.0f) {
            var7 = 1.0f;
        }
        this.limbSwingAmount += (var7 - this.limbSwingAmount) * 0.4f;
        this.limbSwing += this.limbSwingAmount;
    }
    
    @Override
    public boolean isOnLadder() {
        return false;
    }
}
