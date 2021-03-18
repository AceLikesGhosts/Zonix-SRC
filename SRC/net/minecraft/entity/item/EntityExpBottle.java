package net.minecraft.entity.item;

import net.minecraft.entity.projectile.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class EntityExpBottle extends EntityThrowable
{
    private static final String __OBFID = "CL_00001726";
    
    public EntityExpBottle(final World p_i1785_1_) {
        super(p_i1785_1_);
    }
    
    public EntityExpBottle(final World p_i1786_1_, final EntityLivingBase p_i1786_2_) {
        super(p_i1786_1_, p_i1786_2_);
    }
    
    public EntityExpBottle(final World p_i1787_1_, final double p_i1787_2_, final double p_i1787_4_, final double p_i1787_6_) {
        super(p_i1787_1_, p_i1787_2_, p_i1787_4_, p_i1787_6_);
    }
    
    @Override
    protected float getGravityVelocity() {
        return 0.07f;
    }
    
    @Override
    protected float func_70182_d() {
        return 0.7f;
    }
    
    @Override
    protected float func_70183_g() {
        return -20.0f;
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition p_70184_1_) {
        if (!this.worldObj.isClient) {
            this.worldObj.playAuxSFX(2002, (int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ), 0);
            int var2 = 3 + this.worldObj.rand.nextInt(5) + this.worldObj.rand.nextInt(5);
            while (var2 > 0) {
                final int var3 = EntityXPOrb.getXPSplit(var2);
                var2 -= var3;
                this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var3));
            }
            this.setDead();
        }
    }
}
