package net.minecraft.entity.passive;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.stats.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.nbt.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public abstract class EntityAnimal extends EntityAgeable implements IAnimals
{
    private int inLove;
    private int breeding;
    private EntityPlayer field_146084_br;
    private static final String __OBFID = "CL_00001638";
    
    public EntityAnimal(final World p_i1681_1_) {
        super(p_i1681_1_);
    }
    
    @Override
    protected void updateAITick() {
        if (this.getGrowingAge() != 0) {
            this.inLove = 0;
        }
        super.updateAITick();
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.getGrowingAge() != 0) {
            this.inLove = 0;
        }
        if (this.inLove > 0) {
            --this.inLove;
            final String var1 = "heart";
            if (this.inLove % 10 == 0) {
                final double var2 = this.rand.nextGaussian() * 0.02;
                final double var3 = this.rand.nextGaussian() * 0.02;
                final double var4 = this.rand.nextGaussian() * 0.02;
                this.worldObj.spawnParticle(var1, this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 0.5 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, var2, var3, var4);
            }
        }
        else {
            this.breeding = 0;
        }
    }
    
    @Override
    protected void attackEntity(final Entity p_70785_1_, final float p_70785_2_) {
        if (p_70785_1_ instanceof EntityPlayer) {
            if (p_70785_2_ < 3.0f) {
                final double var3 = p_70785_1_.posX - this.posX;
                final double var4 = p_70785_1_.posZ - this.posZ;
                this.rotationYaw = (float)(Math.atan2(var4, var3) * 180.0 / 3.141592653589793) - 90.0f;
                this.hasAttacked = true;
            }
            final EntityPlayer var5 = (EntityPlayer)p_70785_1_;
            if (var5.getCurrentEquippedItem() == null || !this.isBreedingItem(var5.getCurrentEquippedItem())) {
                this.entityToAttack = null;
            }
        }
        else if (p_70785_1_ instanceof EntityAnimal) {
            final EntityAnimal var6 = (EntityAnimal)p_70785_1_;
            if (this.getGrowingAge() > 0 && var6.getGrowingAge() < 0) {
                if (p_70785_2_ < 2.5) {
                    this.hasAttacked = true;
                }
            }
            else if (this.inLove > 0 && var6.inLove > 0) {
                if (var6.entityToAttack == null) {
                    var6.entityToAttack = this;
                }
                if (var6.entityToAttack == this && p_70785_2_ < 3.5) {
                    final EntityAnimal entityAnimal = var6;
                    ++entityAnimal.inLove;
                    ++this.inLove;
                    ++this.breeding;
                    if (this.breeding % 4 == 0) {
                        this.worldObj.spawnParticle("heart", this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 0.5 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, 0.0, 0.0, 0.0);
                    }
                    if (this.breeding == 60) {
                        this.procreate((EntityAnimal)p_70785_1_);
                    }
                }
                else {
                    this.breeding = 0;
                }
            }
            else {
                this.breeding = 0;
                this.entityToAttack = null;
            }
        }
    }
    
    private void procreate(final EntityAnimal p_70876_1_) {
        final EntityAgeable var2 = this.createChild(p_70876_1_);
        if (var2 != null) {
            if (this.field_146084_br == null && p_70876_1_.func_146083_cb() != null) {
                this.field_146084_br = p_70876_1_.func_146083_cb();
            }
            if (this.field_146084_br != null) {
                this.field_146084_br.triggerAchievement(StatList.field_151186_x);
                if (this instanceof EntityCow) {
                    this.field_146084_br.triggerAchievement(AchievementList.field_150962_H);
                }
            }
            this.setGrowingAge(6000);
            p_70876_1_.setGrowingAge(6000);
            this.inLove = 0;
            this.breeding = 0;
            this.entityToAttack = null;
            p_70876_1_.entityToAttack = null;
            p_70876_1_.breeding = 0;
            p_70876_1_.inLove = 0;
            var2.setGrowingAge(-24000);
            var2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            for (int var3 = 0; var3 < 7; ++var3) {
                final double var4 = this.rand.nextGaussian() * 0.02;
                final double var5 = this.rand.nextGaussian() * 0.02;
                final double var6 = this.rand.nextGaussian() * 0.02;
                this.worldObj.spawnParticle("heart", this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 0.5 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, var4, var5, var6);
            }
            this.worldObj.spawnEntityInWorld(var2);
        }
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource p_70097_1_, final float p_70097_2_) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        this.fleeingTick = 60;
        if (!this.isAIEnabled()) {
            final IAttributeInstance var3 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
            if (var3.getModifier(EntityAnimal.field_110179_h) == null) {
                var3.applyModifier(EntityAnimal.field_110181_i);
            }
        }
        this.entityToAttack = null;
        this.inLove = 0;
        return super.attackEntityFrom(p_70097_1_, p_70097_2_);
    }
    
    @Override
    public float getBlockPathWeight(final int p_70783_1_, final int p_70783_2_, final int p_70783_3_) {
        return (this.worldObj.getBlock(p_70783_1_, p_70783_2_ - 1, p_70783_3_) == Blocks.grass) ? 10.0f : (this.worldObj.getLightBrightness(p_70783_1_, p_70783_2_, p_70783_3_) - 0.5f);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
        p_70014_1_.setInteger("InLove", this.inLove);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
        this.inLove = p_70037_1_.getInteger("InLove");
    }
    
    @Override
    protected Entity findPlayerToAttack() {
        if (this.fleeingTick > 0) {
            return null;
        }
        final float var1 = 8.0f;
        if (this.inLove > 0) {
            final List var2 = this.worldObj.getEntitiesWithinAABB(this.getClass(), this.boundingBox.expand(var1, var1, var1));
            for (int var3 = 0; var3 < var2.size(); ++var3) {
                final EntityAnimal var4 = var2.get(var3);
                if (var4 != this && var4.inLove > 0) {
                    return var4;
                }
            }
        }
        else if (this.getGrowingAge() == 0) {
            final List var2 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(var1, var1, var1));
            for (int var3 = 0; var3 < var2.size(); ++var3) {
                final EntityPlayer var5 = var2.get(var3);
                if (var5.getCurrentEquippedItem() != null && this.isBreedingItem(var5.getCurrentEquippedItem())) {
                    return var5;
                }
            }
        }
        else if (this.getGrowingAge() > 0) {
            final List var2 = this.worldObj.getEntitiesWithinAABB(this.getClass(), this.boundingBox.expand(var1, var1, var1));
            for (int var3 = 0; var3 < var2.size(); ++var3) {
                final EntityAnimal var4 = var2.get(var3);
                if (var4 != this && var4.getGrowingAge() < 0) {
                    return var4;
                }
            }
        }
        return null;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        final int var1 = MathHelper.floor_double(this.posX);
        final int var2 = MathHelper.floor_double(this.boundingBox.minY);
        final int var3 = MathHelper.floor_double(this.posZ);
        return this.worldObj.getBlock(var1, var2 - 1, var3) == Blocks.grass && this.worldObj.getFullBlockLightValue(var1, var2, var3) > 8 && super.getCanSpawnHere();
    }
    
    @Override
    public int getTalkInterval() {
        return 120;
    }
    
    @Override
    protected boolean canDespawn() {
        return false;
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer p_70693_1_) {
        return 1 + this.worldObj.rand.nextInt(3);
    }
    
    public boolean isBreedingItem(final ItemStack p_70877_1_) {
        return p_70877_1_.getItem() == Items.wheat;
    }
    
    @Override
    public boolean interact(final EntityPlayer p_70085_1_) {
        final ItemStack var2 = p_70085_1_.inventory.getCurrentItem();
        if (var2 != null && this.isBreedingItem(var2) && this.getGrowingAge() == 0 && this.inLove <= 0) {
            if (!p_70085_1_.capabilities.isCreativeMode) {
                final ItemStack itemStack = var2;
                --itemStack.stackSize;
                if (var2.stackSize <= 0) {
                    p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem, null);
                }
            }
            this.func_146082_f(p_70085_1_);
            return true;
        }
        return super.interact(p_70085_1_);
    }
    
    public void func_146082_f(final EntityPlayer p_146082_1_) {
        this.inLove = 600;
        this.field_146084_br = p_146082_1_;
        this.entityToAttack = null;
        this.worldObj.setEntityState(this, (byte)18);
    }
    
    public EntityPlayer func_146083_cb() {
        return this.field_146084_br;
    }
    
    public boolean isInLove() {
        return this.inLove > 0;
    }
    
    public void resetInLove() {
        this.inLove = 0;
    }
    
    public boolean canMateWith(final EntityAnimal p_70878_1_) {
        return p_70878_1_ != this && p_70878_1_.getClass() == this.getClass() && (this.isInLove() && p_70878_1_.isInLove());
    }
    
    @Override
    public void handleHealthUpdate(final byte p_70103_1_) {
        if (p_70103_1_ == 18) {
            for (int var2 = 0; var2 < 7; ++var2) {
                final double var3 = this.rand.nextGaussian() * 0.02;
                final double var4 = this.rand.nextGaussian() * 0.02;
                final double var5 = this.rand.nextGaussian() * 0.02;
                this.worldObj.spawnParticle("heart", this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 0.5 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, var3, var4, var5);
            }
        }
        else {
            super.handleHealthUpdate(p_70103_1_);
        }
    }
}
