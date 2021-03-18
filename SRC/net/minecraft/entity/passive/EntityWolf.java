package net.minecraft.entity.passive;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.block.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;

public class EntityWolf extends EntityTameable
{
    private float field_70926_e;
    private float field_70924_f;
    private boolean isShaking;
    private boolean field_70928_h;
    private float timeWolfIsShaking;
    private float prevTimeWolfIsShaking;
    private static final String __OBFID = "CL_00001654";
    
    public EntityWolf(final World p_i1696_1_) {
        super(p_i1696_1_);
        this.setSize(0.6f, 0.8f);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4f));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0, true));
        this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0, 10.0f, 2.0f));
        this.tasks.addTask(6, new EntityAIMate(this, 1.0));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0));
        this.tasks.addTask(8, new EntityAIBeg(this, 8.0f));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntitySheep.class, 200, false));
        this.setTamed(false);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896);
        if (this.isTamed()) {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
        }
        else {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        }
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    @Override
    public void setAttackTarget(final EntityLivingBase p_70624_1_) {
        super.setAttackTarget(p_70624_1_);
        if (p_70624_1_ == null) {
            this.setAngry(false);
        }
        else if (!this.isTamed()) {
            this.setAngry(true);
        }
    }
    
    @Override
    protected void updateAITick() {
        this.dataWatcher.updateObject(18, this.getHealth());
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(18, new Float(this.getHealth()));
        this.dataWatcher.addObject(19, new Byte((byte)0));
        this.dataWatcher.addObject(20, new Byte((byte)BlockColored.func_150032_b(1)));
    }
    
    @Override
    protected void func_145780_a(final int p_145780_1_, final int p_145780_2_, final int p_145780_3_, final Block p_145780_4_) {
        this.playSound("mob.wolf.step", 0.15f, 1.0f);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
        p_70014_1_.setBoolean("Angry", this.isAngry());
        p_70014_1_.setByte("CollarColor", (byte)this.getCollarColor());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
        this.setAngry(p_70037_1_.getBoolean("Angry"));
        if (p_70037_1_.func_150297_b("CollarColor", 99)) {
            this.setCollarColor(p_70037_1_.getByte("CollarColor"));
        }
    }
    
    @Override
    protected String getLivingSound() {
        return this.isAngry() ? "mob.wolf.growl" : ((this.rand.nextInt(3) == 0) ? ((this.isTamed() && this.dataWatcher.getWatchableObjectFloat(18) < 10.0f) ? "mob.wolf.whine" : "mob.wolf.panting") : "mob.wolf.bark");
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.wolf.hurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.wolf.death";
    }
    
    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }
    
    @Override
    protected Item func_146068_u() {
        return Item.getItemById(-1);
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.worldObj.isClient && this.isShaking && !this.field_70928_h && !this.hasPath() && this.onGround) {
            this.field_70928_h = true;
            this.timeWolfIsShaking = 0.0f;
            this.prevTimeWolfIsShaking = 0.0f;
            this.worldObj.setEntityState(this, (byte)8);
        }
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        this.field_70924_f = this.field_70926_e;
        if (this.func_70922_bv()) {
            this.field_70926_e += (1.0f - this.field_70926_e) * 0.4f;
        }
        else {
            this.field_70926_e += (0.0f - this.field_70926_e) * 0.4f;
        }
        if (this.func_70922_bv()) {
            this.numTicksToChaseTarget = 10;
        }
        if (this.isWet()) {
            this.isShaking = true;
            this.field_70928_h = false;
            this.timeWolfIsShaking = 0.0f;
            this.prevTimeWolfIsShaking = 0.0f;
        }
        else if ((this.isShaking || this.field_70928_h) && this.field_70928_h) {
            if (this.timeWolfIsShaking == 0.0f) {
                this.playSound("mob.wolf.shake", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            }
            this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
            this.timeWolfIsShaking += 0.05f;
            if (this.prevTimeWolfIsShaking >= 2.0f) {
                this.isShaking = false;
                this.field_70928_h = false;
                this.prevTimeWolfIsShaking = 0.0f;
                this.timeWolfIsShaking = 0.0f;
            }
            if (this.timeWolfIsShaking > 0.4f) {
                final float var1 = (float)this.boundingBox.minY;
                for (int var2 = (int)(MathHelper.sin((this.timeWolfIsShaking - 0.4f) * 3.1415927f) * 7.0f), var3 = 0; var3 < var2; ++var3) {
                    final float var4 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width * 0.5f;
                    final float var5 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width * 0.5f;
                    this.worldObj.spawnParticle("splash", this.posX + var4, var1 + 0.8f, this.posZ + var5, this.motionX, this.motionY, this.motionZ);
                }
            }
        }
    }
    
    public boolean getWolfShaking() {
        return this.isShaking;
    }
    
    public float getShadingWhileShaking(final float p_70915_1_) {
        return 0.75f + (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * p_70915_1_) / 2.0f * 0.25f;
    }
    
    public float getShakeAngle(final float p_70923_1_, final float p_70923_2_) {
        float var3 = (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * p_70923_1_ + p_70923_2_) / 1.8f;
        if (var3 < 0.0f) {
            var3 = 0.0f;
        }
        else if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        return MathHelper.sin(var3 * 3.1415927f) * MathHelper.sin(var3 * 3.1415927f * 11.0f) * 0.15f * 3.1415927f;
    }
    
    public float getInterestedAngle(final float p_70917_1_) {
        return (this.field_70924_f + (this.field_70926_e - this.field_70924_f) * p_70917_1_) * 0.15f * 3.1415927f;
    }
    
    @Override
    public float getEyeHeight() {
        return this.height * 0.8f;
    }
    
    @Override
    public int getVerticalFaceSpeed() {
        return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource p_70097_1_, float p_70097_2_) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        final Entity var3 = p_70097_1_.getEntity();
        this.aiSit.setSitting(false);
        if (var3 != null && !(var3 instanceof EntityPlayer) && !(var3 instanceof EntityArrow)) {
            p_70097_2_ = (p_70097_2_ + 1.0f) / 2.0f;
        }
        return super.attackEntityFrom(p_70097_1_, p_70097_2_);
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity p_70652_1_) {
        final int var2 = this.isTamed() ? 4 : 2;
        return p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), (float)var2);
    }
    
    @Override
    public void setTamed(final boolean p_70903_1_) {
        super.setTamed(p_70903_1_);
        if (p_70903_1_) {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
        }
        else {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        }
    }
    
    @Override
    public boolean interact(final EntityPlayer p_70085_1_) {
        final ItemStack var2 = p_70085_1_.inventory.getCurrentItem();
        if (this.isTamed()) {
            if (var2 != null) {
                if (var2.getItem() instanceof ItemFood) {
                    final ItemFood var3 = (ItemFood)var2.getItem();
                    if (var3.isWolfsFavoriteMeat() && this.dataWatcher.getWatchableObjectFloat(18) < 20.0f) {
                        if (!p_70085_1_.capabilities.isCreativeMode) {
                            final ItemStack itemStack = var2;
                            --itemStack.stackSize;
                        }
                        this.heal((float)var3.func_150905_g(var2));
                        if (var2.stackSize <= 0) {
                            p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem, null);
                        }
                        return true;
                    }
                }
                else if (var2.getItem() == Items.dye) {
                    final int var4 = BlockColored.func_150032_b(var2.getItemDamage());
                    if (var4 != this.getCollarColor()) {
                        this.setCollarColor(var4);
                        if (!p_70085_1_.capabilities.isCreativeMode) {
                            final ItemStack itemStack2 = var2;
                            if (--itemStack2.stackSize <= 0) {
                                p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem, null);
                            }
                        }
                        return true;
                    }
                }
            }
            if (this.func_152114_e(p_70085_1_) && !this.worldObj.isClient && !this.isBreedingItem(var2)) {
                this.aiSit.setSitting(!this.isSitting());
                this.isJumping = false;
                this.setPathToEntity(null);
                this.setTarget(null);
                this.setAttackTarget(null);
            }
        }
        else if (var2 != null && var2.getItem() == Items.bone && !this.isAngry()) {
            if (!p_70085_1_.capabilities.isCreativeMode) {
                final ItemStack itemStack3 = var2;
                --itemStack3.stackSize;
            }
            if (var2.stackSize <= 0) {
                p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem, null);
            }
            if (!this.worldObj.isClient) {
                if (this.rand.nextInt(3) == 0) {
                    this.setTamed(true);
                    this.setPathToEntity(null);
                    this.setAttackTarget(null);
                    this.aiSit.setSitting(true);
                    this.setHealth(20.0f);
                    this.func_152115_b(p_70085_1_.getUniqueID().toString());
                    this.playTameEffect(true);
                    this.worldObj.setEntityState(this, (byte)7);
                }
                else {
                    this.playTameEffect(false);
                    this.worldObj.setEntityState(this, (byte)6);
                }
            }
            return true;
        }
        return super.interact(p_70085_1_);
    }
    
    @Override
    public void handleHealthUpdate(final byte p_70103_1_) {
        if (p_70103_1_ == 8) {
            this.field_70928_h = true;
            this.timeWolfIsShaking = 0.0f;
            this.prevTimeWolfIsShaking = 0.0f;
        }
        else {
            super.handleHealthUpdate(p_70103_1_);
        }
    }
    
    public float getTailRotation() {
        return this.isAngry() ? 1.5393804f : (this.isTamed() ? ((0.55f - (20.0f - this.dataWatcher.getWatchableObjectFloat(18)) * 0.02f) * 3.1415927f) : 0.62831855f);
    }
    
    @Override
    public boolean isBreedingItem(final ItemStack p_70877_1_) {
        return p_70877_1_ != null && p_70877_1_.getItem() instanceof ItemFood && ((ItemFood)p_70877_1_.getItem()).isWolfsFavoriteMeat();
    }
    
    @Override
    public int getMaxSpawnedInChunk() {
        return 8;
    }
    
    public boolean isAngry() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x2) != 0x0;
    }
    
    public void setAngry(final boolean p_70916_1_) {
        final byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        if (p_70916_1_) {
            this.dataWatcher.updateObject(16, (byte)(var2 | 0x2));
        }
        else {
            this.dataWatcher.updateObject(16, (byte)(var2 & 0xFFFFFFFD));
        }
    }
    
    public int getCollarColor() {
        return this.dataWatcher.getWatchableObjectByte(20) & 0xF;
    }
    
    public void setCollarColor(final int p_82185_1_) {
        this.dataWatcher.updateObject(20, (byte)(p_82185_1_ & 0xF));
    }
    
    @Override
    public EntityWolf createChild(final EntityAgeable p_90011_1_) {
        final EntityWolf var2 = new EntityWolf(this.worldObj);
        final String var3 = this.func_152113_b();
        if (var3 != null && var3.trim().length() > 0) {
            var2.func_152115_b(var3);
            var2.setTamed(true);
        }
        return var2;
    }
    
    public void func_70918_i(final boolean p_70918_1_) {
        if (p_70918_1_) {
            this.dataWatcher.updateObject(19, 1);
        }
        else {
            this.dataWatcher.updateObject(19, 0);
        }
    }
    
    @Override
    public boolean canMateWith(final EntityAnimal p_70878_1_) {
        if (p_70878_1_ == this) {
            return false;
        }
        if (!this.isTamed()) {
            return false;
        }
        if (!(p_70878_1_ instanceof EntityWolf)) {
            return false;
        }
        final EntityWolf var2 = (EntityWolf)p_70878_1_;
        return var2.isTamed() && !var2.isSitting() && (this.isInLove() && var2.isInLove());
    }
    
    public boolean func_70922_bv() {
        return this.dataWatcher.getWatchableObjectByte(19) == 1;
    }
    
    @Override
    protected boolean canDespawn() {
        return !this.isTamed() && this.ticksExisted > 2400;
    }
    
    @Override
    public boolean func_142018_a(final EntityLivingBase p_142018_1_, final EntityLivingBase p_142018_2_) {
        if (!(p_142018_1_ instanceof EntityCreeper) && !(p_142018_1_ instanceof EntityGhast)) {
            if (p_142018_1_ instanceof EntityWolf) {
                final EntityWolf var3 = (EntityWolf)p_142018_1_;
                if (var3.isTamed() && var3.getOwner() == p_142018_2_) {
                    return false;
                }
            }
            return (!(p_142018_1_ instanceof EntityPlayer) || !(p_142018_2_ instanceof EntityPlayer) || ((EntityPlayer)p_142018_2_).canAttackPlayer((EntityPlayer)p_142018_1_)) && (!(p_142018_1_ instanceof EntityHorse) || !((EntityHorse)p_142018_1_).isTame());
        }
        return false;
    }
}
