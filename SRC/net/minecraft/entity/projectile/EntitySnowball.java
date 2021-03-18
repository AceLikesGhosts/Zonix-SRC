package net.minecraft.entity.projectile;

import net.minecraft.world.*;
import net.minecraft.entity.monster.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class EntitySnowball extends EntityThrowable
{
    private static final String __OBFID = "CL_00001722";
    
    public EntitySnowball(final World p_i1773_1_) {
        super(p_i1773_1_);
    }
    
    public EntitySnowball(final World p_i1774_1_, final EntityLivingBase p_i1774_2_) {
        super(p_i1774_1_, p_i1774_2_);
    }
    
    public EntitySnowball(final World p_i1775_1_, final double p_i1775_2_, final double p_i1775_4_, final double p_i1775_6_) {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition p_70184_1_) {
        if (p_70184_1_.entityHit != null) {
            byte var2 = 0;
            if (p_70184_1_.entityHit instanceof EntityBlaze) {
                var2 = 3;
            }
            p_70184_1_.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), var2);
        }
        for (int var3 = 0; var3 < 8; ++var3) {
            this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
        }
        if (!this.worldObj.isClient) {
            this.setDead();
        }
    }
}
