package net.minecraft.entity.monster;

import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.block.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.stats.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.enchantment.*;
import net.minecraft.nbt.*;

public class EntitySkeleton extends EntityMob implements IRangedAttackMob
{
    private EntityAIArrowAttack aiArrowAttack;
    private EntityAIAttackOnCollide aiAttackOnCollide;
    private static final String __OBFID = "CL_00001697";
    
    public EntitySkeleton(final World p_i1741_1_) {
        super(p_i1741_1_);
        this.aiArrowAttack = new EntityAIArrowAttack(this, 1.0, 20, 60, 15.0f);
        this.aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2, false);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIRestrictSun(this));
        this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        if (p_i1741_1_ != null && !p_i1741_1_.isClient) {
            this.setCombatTask();
        }
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(13, new Byte((byte)0));
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.skeleton.say";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.skeleton.hurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.skeleton.death";
    }
    
    @Override
    protected void func_145780_a(final int p_145780_1_, final int p_145780_2_, final int p_145780_3_, final Block p_145780_4_) {
        this.playSound("mob.skeleton.step", 0.15f, 1.0f);
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity p_70652_1_) {
        if (super.attackEntityAsMob(p_70652_1_)) {
            if (this.getSkeletonType() == 1 && p_70652_1_ instanceof EntityLivingBase) {
                ((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(Potion.wither.id, 200));
            }
            return true;
        }
        return false;
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.worldObj.isDaytime() && !this.worldObj.isClient) {
            final float var1 = this.getBrightness(1.0f);
            if (var1 > 0.5f && this.rand.nextFloat() * 30.0f < (var1 - 0.4f) * 2.0f && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ))) {
                boolean var2 = true;
                final ItemStack var3 = this.getEquipmentInSlot(4);
                if (var3 != null) {
                    if (var3.isItemStackDamageable()) {
                        var3.setItemDamage(var3.getItemDamageForDisplay() + this.rand.nextInt(2));
                        if (var3.getItemDamageForDisplay() >= var3.getMaxDamage()) {
                            this.renderBrokenItemStack(var3);
                            this.setCurrentItemOrArmor(4, null);
                        }
                    }
                    var2 = false;
                }
                if (var2) {
                    this.setFire(8);
                }
            }
        }
        if (this.worldObj.isClient && this.getSkeletonType() == 1) {
            this.setSize(0.72f, 2.34f);
        }
        super.onLivingUpdate();
    }
    
    @Override
    public void updateRidden() {
        super.updateRidden();
        if (this.ridingEntity instanceof EntityCreature) {
            final EntityCreature var1 = (EntityCreature)this.ridingEntity;
            this.renderYawOffset = var1.renderYawOffset;
        }
    }
    
    @Override
    public void onDeath(final DamageSource p_70645_1_) {
        super.onDeath(p_70645_1_);
        if (p_70645_1_.getSourceOfDamage() instanceof EntityArrow && p_70645_1_.getEntity() instanceof EntityPlayer) {
            final EntityPlayer var2 = (EntityPlayer)p_70645_1_.getEntity();
            final double var3 = var2.posX - this.posX;
            final double var4 = var2.posZ - this.posZ;
            if (var3 * var3 + var4 * var4 >= 2500.0) {
                var2.triggerAchievement(AchievementList.snipeSkeleton);
            }
        }
    }
    
    @Override
    protected Item func_146068_u() {
        return Items.arrow;
    }
    
    @Override
    protected void dropFewItems(final boolean p_70628_1_, final int p_70628_2_) {
        if (this.getSkeletonType() == 1) {
            for (int var3 = this.rand.nextInt(3 + p_70628_2_) - 1, var4 = 0; var4 < var3; ++var4) {
                this.func_145779_a(Items.coal, 1);
            }
        }
        else {
            for (int var3 = this.rand.nextInt(3 + p_70628_2_), var4 = 0; var4 < var3; ++var4) {
                this.func_145779_a(Items.arrow, 1);
            }
        }
        for (int var3 = this.rand.nextInt(3 + p_70628_2_), var4 = 0; var4 < var3; ++var4) {
            this.func_145779_a(Items.bone, 1);
        }
    }
    
    @Override
    protected void dropRareDrop(final int p_70600_1_) {
        if (this.getSkeletonType() == 1) {
            this.entityDropItem(new ItemStack(Items.skull, 1, 1), 0.0f);
        }
    }
    
    @Override
    protected void addRandomArmor() {
        super.addRandomArmor();
        this.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_) {
        p_110161_1_ = super.onSpawnWithEgg(p_110161_1_);
        if (this.worldObj.provider instanceof WorldProviderHell && this.getRNG().nextInt(5) > 0) {
            this.tasks.addTask(4, this.aiAttackOnCollide);
            this.setSkeletonType(1);
            this.setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword));
            this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0);
        }
        else {
            this.tasks.addTask(4, this.aiArrowAttack);
            this.addRandomArmor();
            this.enchantEquipment();
        }
        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55f * this.worldObj.func_147462_b(this.posX, this.posY, this.posZ));
        if (this.getEquipmentInSlot(4) == null) {
            final Calendar var2 = this.worldObj.getCurrentDate();
            if (var2.get(2) + 1 == 10 && var2.get(5) == 31 && this.rand.nextFloat() < 0.25f) {
                this.setCurrentItemOrArmor(4, new ItemStack((this.rand.nextFloat() < 0.1f) ? Blocks.lit_pumpkin : Blocks.pumpkin));
                this.equipmentDropChances[4] = 0.0f;
            }
        }
        return p_110161_1_;
    }
    
    public void setCombatTask() {
        this.tasks.removeTask(this.aiAttackOnCollide);
        this.tasks.removeTask(this.aiArrowAttack);
        final ItemStack var1 = this.getHeldItem();
        if (var1 != null && var1.getItem() == Items.bow) {
            this.tasks.addTask(4, this.aiArrowAttack);
        }
        else {
            this.tasks.addTask(4, this.aiAttackOnCollide);
        }
    }
    
    @Override
    public void attackEntityWithRangedAttack(final EntityLivingBase p_82196_1_, final float p_82196_2_) {
        final EntityArrow var3 = new EntityArrow(this.worldObj, this, p_82196_1_, 1.6f, (float)(14 - this.worldObj.difficultySetting.getDifficultyId() * 4));
        final int var4 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItem());
        final int var5 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItem());
        var3.setDamage(p_82196_2_ * 2.0f + this.rand.nextGaussian() * 0.25 + this.worldObj.difficultySetting.getDifficultyId() * 0.11f);
        if (var4 > 0) {
            var3.setDamage(var3.getDamage() + var4 * 0.5 + 0.5);
        }
        if (var5 > 0) {
            var3.setKnockbackStrength(var5);
        }
        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItem()) > 0 || this.getSkeletonType() == 1) {
            var3.setFire(100);
        }
        this.playSound("random.bow", 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
        this.worldObj.spawnEntityInWorld(var3);
    }
    
    public int getSkeletonType() {
        return this.dataWatcher.getWatchableObjectByte(13);
    }
    
    public void setSkeletonType(final int p_82201_1_) {
        this.dataWatcher.updateObject(13, (byte)p_82201_1_);
        this.isImmuneToFire = (p_82201_1_ == 1);
        if (p_82201_1_ == 1) {
            this.setSize(0.72f, 2.34f);
        }
        else {
            this.setSize(0.6f, 1.8f);
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
        if (p_70037_1_.func_150297_b("SkeletonType", 99)) {
            final byte var2 = p_70037_1_.getByte("SkeletonType");
            this.setSkeletonType(var2);
        }
        this.setCombatTask();
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
        p_70014_1_.setByte("SkeletonType", (byte)this.getSkeletonType());
    }
    
    @Override
    public void setCurrentItemOrArmor(final int p_70062_1_, final ItemStack p_70062_2_) {
        super.setCurrentItemOrArmor(p_70062_1_, p_70062_2_);
        if (!this.worldObj.isClient && p_70062_1_ == 0) {
            this.setCombatTask();
        }
    }
    
    @Override
    public double getYOffset() {
        return super.getYOffset() - 0.5;
    }
}
