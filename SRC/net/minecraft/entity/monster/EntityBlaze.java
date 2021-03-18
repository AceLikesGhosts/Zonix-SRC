package net.minecraft.entity.monster;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class EntityBlaze extends EntityMob
{
    private float heightOffset;
    private int heightOffsetUpdateTime;
    private int field_70846_g;
    private static final String __OBFID = "CL_00001682";
    
    public EntityBlaze(final World p_i1731_1_) {
        super(p_i1731_1_);
        this.heightOffset = 0.5f;
        this.isImmuneToFire = true;
        this.experienceValue = 10;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.blaze.breathe";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.blaze.hit";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.blaze.death";
    }
    
    @Override
    public int getBrightnessForRender(final float p_70070_1_) {
        return 15728880;
    }
    
    @Override
    public float getBrightness(final float p_70013_1_) {
        return 1.0f;
    }
    
    @Override
    public void onLivingUpdate() {
        if (!this.worldObj.isClient) {
            if (this.isWet()) {
                this.attackEntityFrom(DamageSource.drown, 1.0f);
            }
            --this.heightOffsetUpdateTime;
            if (this.heightOffsetUpdateTime <= 0) {
                this.heightOffsetUpdateTime = 100;
                this.heightOffset = 0.5f + (float)this.rand.nextGaussian() * 3.0f;
            }
            if (this.getEntityToAttack() != null && this.getEntityToAttack().posY + this.getEntityToAttack().getEyeHeight() > this.posY + this.getEyeHeight() + this.heightOffset) {
                this.motionY += (0.30000001192092896 - this.motionY) * 0.30000001192092896;
            }
        }
        if (this.rand.nextInt(24) == 0) {
            this.worldObj.playSoundEffect(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, "fire.fire", 1.0f + this.rand.nextFloat(), this.rand.nextFloat() * 0.7f + 0.3f);
        }
        if (!this.onGround && this.motionY < 0.0) {
            this.motionY *= 0.6;
        }
        for (int var1 = 0; var1 < 2; ++var1) {
            this.worldObj.spawnParticle("largesmoke", this.posX + (this.rand.nextDouble() - 0.5) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5) * this.width, 0.0, 0.0, 0.0);
        }
        super.onLivingUpdate();
    }
    
    @Override
    protected void attackEntity(final Entity p_70785_1_, final float p_70785_2_) {
        if (this.attackTime <= 0 && p_70785_2_ < 2.0f && p_70785_1_.boundingBox.maxY > this.boundingBox.minY && p_70785_1_.boundingBox.minY < this.boundingBox.maxY) {
            this.attackTime = 20;
            this.attackEntityAsMob(p_70785_1_);
        }
        else if (p_70785_2_ < 30.0f) {
            final double var3 = p_70785_1_.posX - this.posX;
            final double var4 = p_70785_1_.boundingBox.minY + p_70785_1_.height / 2.0f - (this.posY + this.height / 2.0f);
            final double var5 = p_70785_1_.posZ - this.posZ;
            if (this.attackTime == 0) {
                ++this.field_70846_g;
                if (this.field_70846_g == 1) {
                    this.attackTime = 60;
                    this.func_70844_e(true);
                }
                else if (this.field_70846_g <= 4) {
                    this.attackTime = 6;
                }
                else {
                    this.attackTime = 100;
                    this.field_70846_g = 0;
                    this.func_70844_e(false);
                }
                if (this.field_70846_g > 1) {
                    final float var6 = MathHelper.sqrt_float(p_70785_2_) * 0.5f;
                    this.worldObj.playAuxSFXAtEntity(null, 1009, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
                    for (int var7 = 0; var7 < 1; ++var7) {
                        final EntitySmallFireball var8 = new EntitySmallFireball(this.worldObj, this, var3 + this.rand.nextGaussian() * var6, var4, var5 + this.rand.nextGaussian() * var6);
                        var8.posY = this.posY + this.height / 2.0f + 0.5;
                        this.worldObj.spawnEntityInWorld(var8);
                    }
                }
            }
            this.rotationYaw = (float)(Math.atan2(var5, var3) * 180.0 / 3.141592653589793) - 90.0f;
            this.hasAttacked = true;
        }
    }
    
    @Override
    protected void fall(final float p_70069_1_) {
    }
    
    @Override
    protected Item func_146068_u() {
        return Items.blaze_rod;
    }
    
    @Override
    public boolean isBurning() {
        return this.func_70845_n();
    }
    
    @Override
    protected void dropFewItems(final boolean p_70628_1_, final int p_70628_2_) {
        if (p_70628_1_) {
            for (int var3 = this.rand.nextInt(2 + p_70628_2_), var4 = 0; var4 < var3; ++var4) {
                this.func_145779_a(Items.blaze_rod, 1);
            }
        }
    }
    
    public boolean func_70845_n() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0x0;
    }
    
    public void func_70844_e(final boolean p_70844_1_) {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        if (p_70844_1_) {
            var2 |= 0x1;
        }
        else {
            var2 &= 0xFFFFFFFE;
        }
        this.dataWatcher.updateObject(16, var2);
    }
    
    @Override
    protected boolean isValidLightLevel() {
        return true;
    }
}
