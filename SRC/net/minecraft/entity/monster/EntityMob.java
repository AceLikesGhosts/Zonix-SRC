package net.minecraft.entity.monster;

import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.enchantment.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public abstract class EntityMob extends EntityCreature implements IMob
{
    private static final String __OBFID = "CL_00001692";
    
    public EntityMob(final World p_i1738_1_) {
        super(p_i1738_1_);
        this.experienceValue = 5;
    }
    
    @Override
    public void onLivingUpdate() {
        this.updateArmSwingProgress();
        final float var1 = this.getBrightness(1.0f);
        if (var1 > 0.5f) {
            this.entityAge += 2;
        }
        super.onLivingUpdate();
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isClient && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL) {
            this.setDead();
        }
    }
    
    @Override
    protected String getSwimSound() {
        return "game.hostile.swim";
    }
    
    @Override
    protected String getSplashSound() {
        return "game.hostile.swim.splash";
    }
    
    @Override
    protected Entity findPlayerToAttack() {
        final EntityPlayer var1 = this.worldObj.getClosestVulnerablePlayerToEntity(this, 16.0);
        return (var1 != null && this.canEntityBeSeen(var1)) ? var1 : null;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource p_70097_1_, final float p_70097_2_) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (!super.attackEntityFrom(p_70097_1_, p_70097_2_)) {
            return false;
        }
        final Entity var3 = p_70097_1_.getEntity();
        if (this.riddenByEntity != var3 && this.ridingEntity != var3) {
            if (var3 != this) {
                this.entityToAttack = var3;
            }
            return true;
        }
        return true;
    }
    
    @Override
    protected String getHurtSound() {
        return "game.hostile.hurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "game.hostile.die";
    }
    
    @Override
    protected String func_146067_o(final int p_146067_1_) {
        return (p_146067_1_ > 4) ? "game.hostile.hurt.fall.big" : "game.hostile.hurt.fall.small";
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity p_70652_1_) {
        float var2 = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        int var3 = 0;
        if (p_70652_1_ instanceof EntityLivingBase) {
            var2 += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase)p_70652_1_);
            var3 += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase)p_70652_1_);
        }
        final boolean var4 = p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), var2);
        if (var4) {
            if (var3 > 0) {
                p_70652_1_.addVelocity(-MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f) * var3 * 0.5f, 0.1, MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f) * var3 * 0.5f);
                this.motionX *= 0.6;
                this.motionZ *= 0.6;
            }
            final int var5 = EnchantmentHelper.getFireAspectModifier(this);
            if (var5 > 0) {
                p_70652_1_.setFire(var5 * 4);
            }
            if (p_70652_1_ instanceof EntityLivingBase) {
                EnchantmentHelper.func_151384_a((EntityLivingBase)p_70652_1_, this);
            }
            EnchantmentHelper.func_151385_b(this, p_70652_1_);
        }
        return var4;
    }
    
    @Override
    protected void attackEntity(final Entity p_70785_1_, final float p_70785_2_) {
        if (this.attackTime <= 0 && p_70785_2_ < 2.0f && p_70785_1_.boundingBox.maxY > this.boundingBox.minY && p_70785_1_.boundingBox.minY < this.boundingBox.maxY) {
            this.attackTime = 20;
            this.attackEntityAsMob(p_70785_1_);
        }
    }
    
    @Override
    public float getBlockPathWeight(final int p_70783_1_, final int p_70783_2_, final int p_70783_3_) {
        return 0.5f - this.worldObj.getLightBrightness(p_70783_1_, p_70783_2_, p_70783_3_);
    }
    
    protected boolean isValidLightLevel() {
        final int var1 = MathHelper.floor_double(this.posX);
        final int var2 = MathHelper.floor_double(this.boundingBox.minY);
        final int var3 = MathHelper.floor_double(this.posZ);
        if (this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, var1, var2, var3) > this.rand.nextInt(32)) {
            return false;
        }
        int var4 = this.worldObj.getBlockLightValue(var1, var2, var3);
        if (this.worldObj.isThundering()) {
            final int var5 = this.worldObj.skylightSubtracted;
            this.worldObj.skylightSubtracted = 10;
            var4 = this.worldObj.getBlockLightValue(var1, var2, var3);
            this.worldObj.skylightSubtracted = var5;
        }
        return var4 <= this.rand.nextInt(8);
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && this.isValidLightLevel() && super.getCanSpawnHere();
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
    }
    
    @Override
    protected boolean func_146066_aG() {
        return true;
    }
}
