package net.minecraft.entity.projectile;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;

public class EntityLargeFireball extends EntityFireball
{
    public int field_92057_e;
    private static final String __OBFID = "CL_00001719";
    
    public EntityLargeFireball(final World p_i1767_1_) {
        super(p_i1767_1_);
        this.field_92057_e = 1;
    }
    
    public EntityLargeFireball(final World p_i1768_1_, final double p_i1768_2_, final double p_i1768_4_, final double p_i1768_6_, final double p_i1768_8_, final double p_i1768_10_, final double p_i1768_12_) {
        super(p_i1768_1_, p_i1768_2_, p_i1768_4_, p_i1768_6_, p_i1768_8_, p_i1768_10_, p_i1768_12_);
        this.field_92057_e = 1;
    }
    
    public EntityLargeFireball(final World p_i1769_1_, final EntityLivingBase p_i1769_2_, final double p_i1769_3_, final double p_i1769_5_, final double p_i1769_7_) {
        super(p_i1769_1_, p_i1769_2_, p_i1769_3_, p_i1769_5_, p_i1769_7_);
        this.field_92057_e = 1;
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition p_70227_1_) {
        if (!this.worldObj.isClient) {
            if (p_70227_1_.entityHit != null) {
                p_70227_1_.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 6.0f);
            }
            this.worldObj.newExplosion(null, this.posX, this.posY, this.posZ, (float)this.field_92057_e, true, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
            this.setDead();
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
        p_70014_1_.setInteger("ExplosionPower", this.field_92057_e);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
        if (p_70037_1_.func_150297_b("ExplosionPower", 99)) {
            this.field_92057_e = p_70037_1_.getInteger("ExplosionPower");
        }
    }
}
