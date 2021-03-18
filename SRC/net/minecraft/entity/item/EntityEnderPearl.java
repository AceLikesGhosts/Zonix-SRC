package net.minecraft.entity.item;

import net.minecraft.entity.projectile.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

public class EntityEnderPearl extends EntityThrowable
{
    private static final String __OBFID = "CL_00001725";
    
    public EntityEnderPearl(final World p_i1782_1_) {
        super(p_i1782_1_);
    }
    
    public EntityEnderPearl(final World p_i1783_1_, final EntityLivingBase p_i1783_2_) {
        super(p_i1783_1_, p_i1783_2_);
    }
    
    public EntityEnderPearl(final World p_i1784_1_, final double p_i1784_2_, final double p_i1784_4_, final double p_i1784_6_) {
        super(p_i1784_1_, p_i1784_2_, p_i1784_4_, p_i1784_6_);
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition p_70184_1_) {
        if (p_70184_1_.entityHit != null) {
            p_70184_1_.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0f);
        }
        for (int var2 = 0; var2 < 32; ++var2) {
            this.worldObj.spawnParticle("portal", this.posX, this.posY + this.rand.nextDouble() * 2.0, this.posZ, this.rand.nextGaussian(), 0.0, this.rand.nextGaussian());
        }
        if (!this.worldObj.isClient) {
            if (this.getThrower() != null && this.getThrower() instanceof EntityPlayerMP) {
                final EntityPlayerMP var3 = (EntityPlayerMP)this.getThrower();
                if (var3.playerNetServerHandler.func_147362_b().isChannelOpen() && var3.worldObj == this.worldObj) {
                    if (this.getThrower().isRiding()) {
                        this.getThrower().mountEntity(null);
                    }
                    this.getThrower().setPositionAndUpdate(this.posX, this.posY, this.posZ);
                    this.getThrower().fallDistance = 0.0f;
                    this.getThrower().attackEntityFrom(DamageSource.fall, 5.0f);
                }
            }
            this.setDead();
        }
    }
}
