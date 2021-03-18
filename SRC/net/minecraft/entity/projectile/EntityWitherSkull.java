package net.minecraft.entity.projectile;

import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.potion.*;
import net.minecraft.entity.*;

public class EntityWitherSkull extends EntityFireball
{
    private static final String __OBFID = "CL_00001728";
    
    public EntityWitherSkull(final World p_i1793_1_) {
        super(p_i1793_1_);
        this.setSize(0.3125f, 0.3125f);
    }
    
    public EntityWitherSkull(final World p_i1794_1_, final EntityLivingBase p_i1794_2_, final double p_i1794_3_, final double p_i1794_5_, final double p_i1794_7_) {
        super(p_i1794_1_, p_i1794_2_, p_i1794_3_, p_i1794_5_, p_i1794_7_);
        this.setSize(0.3125f, 0.3125f);
    }
    
    @Override
    protected float getMotionFactor() {
        return this.isInvulnerable() ? 0.73f : super.getMotionFactor();
    }
    
    public EntityWitherSkull(final World p_i1795_1_, final double p_i1795_2_, final double p_i1795_4_, final double p_i1795_6_, final double p_i1795_8_, final double p_i1795_10_, final double p_i1795_12_) {
        super(p_i1795_1_, p_i1795_2_, p_i1795_4_, p_i1795_6_, p_i1795_8_, p_i1795_10_, p_i1795_12_);
        this.setSize(0.3125f, 0.3125f);
    }
    
    @Override
    public boolean isBurning() {
        return false;
    }
    
    @Override
    public float func_145772_a(final Explosion p_145772_1_, final World p_145772_2_, final int p_145772_3_, final int p_145772_4_, final int p_145772_5_, final Block p_145772_6_) {
        float var7 = super.func_145772_a(p_145772_1_, p_145772_2_, p_145772_3_, p_145772_4_, p_145772_5_, p_145772_6_);
        if (this.isInvulnerable() && p_145772_6_ != Blocks.bedrock && p_145772_6_ != Blocks.end_portal && p_145772_6_ != Blocks.end_portal_frame && p_145772_6_ != Blocks.command_block) {
            var7 = Math.min(0.8f, var7);
        }
        return var7;
    }
    
    @Override
    protected void onImpact(final MovingObjectPosition p_70227_1_) {
        if (!this.worldObj.isClient) {
            if (p_70227_1_.entityHit != null) {
                if (this.shootingEntity != null) {
                    if (p_70227_1_.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), 8.0f) && !p_70227_1_.entityHit.isEntityAlive()) {
                        this.shootingEntity.heal(5.0f);
                    }
                }
                else {
                    p_70227_1_.entityHit.attackEntityFrom(DamageSource.magic, 5.0f);
                }
                if (p_70227_1_.entityHit instanceof EntityLivingBase) {
                    byte var2 = 0;
                    if (this.worldObj.difficultySetting == EnumDifficulty.NORMAL) {
                        var2 = 10;
                    }
                    else if (this.worldObj.difficultySetting == EnumDifficulty.HARD) {
                        var2 = 40;
                    }
                    if (var2 > 0) {
                        ((EntityLivingBase)p_70227_1_.entityHit).addPotionEffect(new PotionEffect(Potion.wither.id, 20 * var2, 1));
                    }
                }
            }
            this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 1.0f, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
            this.setDead();
        }
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return false;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource p_70097_1_, final float p_70097_2_) {
        return false;
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(10, 0);
    }
    
    public boolean isInvulnerable() {
        return this.dataWatcher.getWatchableObjectByte(10) == 1;
    }
    
    public void setInvulnerable(final boolean p_82343_1_) {
        this.dataWatcher.updateObject(10, (byte)(byte)(p_82343_1_ ? 1 : 0));
    }
}
