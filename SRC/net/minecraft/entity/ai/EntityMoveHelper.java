package net.minecraft.entity.ai;

import net.minecraft.util.*;
import net.minecraft.entity.*;

public class EntityMoveHelper
{
    private EntityLiving entity;
    private double posX;
    private double posY;
    private double posZ;
    private double speed;
    private boolean update;
    private static final String __OBFID = "CL_00001573";
    
    public EntityMoveHelper(final EntityLiving p_i1614_1_) {
        this.entity = p_i1614_1_;
        this.posX = p_i1614_1_.posX;
        this.posY = p_i1614_1_.posY;
        this.posZ = p_i1614_1_.posZ;
    }
    
    public boolean isUpdating() {
        return this.update;
    }
    
    public double getSpeed() {
        return this.speed;
    }
    
    public void setMoveTo(final double p_75642_1_, final double p_75642_3_, final double p_75642_5_, final double p_75642_7_) {
        this.posX = p_75642_1_;
        this.posY = p_75642_3_;
        this.posZ = p_75642_5_;
        this.speed = p_75642_7_;
        this.update = true;
    }
    
    public void onUpdateMoveHelper() {
        this.entity.setMoveForward(0.0f);
        if (this.update) {
            this.update = false;
            final int var1 = MathHelper.floor_double(this.entity.boundingBox.minY + 0.5);
            final double var2 = this.posX - this.entity.posX;
            final double var3 = this.posZ - this.entity.posZ;
            final double var4 = this.posY - var1;
            final double var5 = var2 * var2 + var4 * var4 + var3 * var3;
            if (var5 >= 2.500000277905201E-7) {
                final float var6 = (float)(Math.atan2(var3, var2) * 180.0 / 3.141592653589793) - 90.0f;
                this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, var6, 30.0f);
                this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
                if (var4 > 0.0 && var2 * var2 + var3 * var3 < 1.0) {
                    this.entity.getJumpHelper().setJumping();
                }
            }
        }
    }
    
    private float limitAngle(final float p_75639_1_, final float p_75639_2_, final float p_75639_3_) {
        float var4 = MathHelper.wrapAngleTo180_float(p_75639_2_ - p_75639_1_);
        if (var4 > p_75639_3_) {
            var4 = p_75639_3_;
        }
        if (var4 < -p_75639_3_) {
            var4 = -p_75639_3_;
        }
        return p_75639_1_ + var4;
    }
}
