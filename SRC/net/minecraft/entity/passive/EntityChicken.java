package net.minecraft.entity.passive;

import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class EntityChicken extends EntityAnimal
{
    public float field_70886_e;
    public float destPos;
    public float field_70884_g;
    public float field_70888_h;
    public float field_70889_i;
    public int timeUntilNextEgg;
    public boolean field_152118_bv;
    private static final String __OBFID = "CL_00001639";
    
    public EntityChicken(final World p_i1682_1_) {
        super(p_i1682_1_);
        this.field_70889_i = 1.0f;
        this.setSize(0.3f, 0.7f);
        this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.4));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0));
        this.tasks.addTask(3, new EntityAITempt(this, 1.0, Items.wheat_seeds, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.field_70888_h = this.field_70886_e;
        this.field_70884_g = this.destPos;
        this.destPos += (float)((this.onGround ? -1 : 4) * 0.3);
        if (this.destPos < 0.0f) {
            this.destPos = 0.0f;
        }
        if (this.destPos > 1.0f) {
            this.destPos = 1.0f;
        }
        if (!this.onGround && this.field_70889_i < 1.0f) {
            this.field_70889_i = 1.0f;
        }
        this.field_70889_i *= (float)0.9;
        if (!this.onGround && this.motionY < 0.0) {
            this.motionY *= 0.6;
        }
        this.field_70886_e += this.field_70889_i * 2.0f;
        if (!this.worldObj.isClient && !this.isChild() && !this.func_152116_bZ() && --this.timeUntilNextEgg <= 0) {
            this.playSound("mob.chicken.plop", 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            this.func_145779_a(Items.egg, 1);
            this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
        }
    }
    
    @Override
    protected void fall(final float p_70069_1_) {
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.chicken.say";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.chicken.hurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.chicken.hurt";
    }
    
    @Override
    protected void func_145780_a(final int p_145780_1_, final int p_145780_2_, final int p_145780_3_, final Block p_145780_4_) {
        this.playSound("mob.chicken.step", 0.15f, 1.0f);
    }
    
    @Override
    protected Item func_146068_u() {
        return Items.feather;
    }
    
    @Override
    protected void dropFewItems(final boolean p_70628_1_, final int p_70628_2_) {
        for (int var3 = this.rand.nextInt(3) + this.rand.nextInt(1 + p_70628_2_), var4 = 0; var4 < var3; ++var4) {
            this.func_145779_a(Items.feather, 1);
        }
        if (this.isBurning()) {
            this.func_145779_a(Items.cooked_chicken, 1);
        }
        else {
            this.func_145779_a(Items.chicken, 1);
        }
    }
    
    @Override
    public EntityChicken createChild(final EntityAgeable p_90011_1_) {
        return new EntityChicken(this.worldObj);
    }
    
    @Override
    public boolean isBreedingItem(final ItemStack p_70877_1_) {
        return p_70877_1_ != null && p_70877_1_.getItem() instanceof ItemSeeds;
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
        this.field_152118_bv = p_70037_1_.getBoolean("IsChickenJockey");
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer p_70693_1_) {
        return this.func_152116_bZ() ? 10 : super.getExperiencePoints(p_70693_1_);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
        p_70014_1_.setBoolean("IsChickenJockey", this.field_152118_bv);
    }
    
    @Override
    protected boolean canDespawn() {
        return this.func_152116_bZ() && this.riddenByEntity == null;
    }
    
    @Override
    public void updateRiderPosition() {
        super.updateRiderPosition();
        final float var1 = MathHelper.sin(this.renderYawOffset * 3.1415927f / 180.0f);
        final float var2 = MathHelper.cos(this.renderYawOffset * 3.1415927f / 180.0f);
        final float var3 = 0.1f;
        final float var4 = 0.0f;
        this.riddenByEntity.setPosition(this.posX + var3 * var1, this.posY + this.height * 0.5f + this.riddenByEntity.getYOffset() + var4, this.posZ - var3 * var2);
        if (this.riddenByEntity instanceof EntityLivingBase) {
            ((EntityLivingBase)this.riddenByEntity).renderYawOffset = this.renderYawOffset;
        }
    }
    
    public boolean func_152116_bZ() {
        return this.field_152118_bv;
    }
    
    public void func_152117_i(final boolean p_152117_1_) {
        this.field_152118_bv = p_152117_1_;
    }
}
