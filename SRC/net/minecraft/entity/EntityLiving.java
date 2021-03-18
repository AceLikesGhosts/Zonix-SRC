package net.minecraft.entity;

import net.minecraft.pathfinding.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.item.*;
import net.minecraft.item.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.passive.*;
import net.minecraft.world.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.entity.monster.*;
import java.util.*;

public abstract class EntityLiving extends EntityLivingBase
{
    public int livingSoundTime;
    protected int experienceValue;
    private EntityLookHelper lookHelper;
    private EntityMoveHelper moveHelper;
    private EntityJumpHelper jumpHelper;
    private EntityBodyHelper bodyHelper;
    private PathNavigate navigator;
    protected final EntityAITasks tasks;
    protected final EntityAITasks targetTasks;
    private EntityLivingBase attackTarget;
    private EntitySenses senses;
    private ItemStack[] equipment;
    protected float[] equipmentDropChances;
    private boolean canPickUpLoot;
    private boolean persistenceRequired;
    protected float defaultPitch;
    private Entity currentTarget;
    protected int numTicksToChaseTarget;
    private boolean isLeashed;
    private Entity leashedToEntity;
    private NBTTagCompound field_110170_bx;
    private static final String __OBFID = "CL_00001550";
    
    public EntityLiving(final World p_i1595_1_) {
        super(p_i1595_1_);
        this.equipment = new ItemStack[5];
        this.equipmentDropChances = new float[5];
        this.tasks = new EntityAITasks((p_i1595_1_ != null && p_i1595_1_.theProfiler != null) ? p_i1595_1_.theProfiler : null);
        this.targetTasks = new EntityAITasks((p_i1595_1_ != null && p_i1595_1_.theProfiler != null) ? p_i1595_1_.theProfiler : null);
        this.lookHelper = new EntityLookHelper(this);
        this.moveHelper = new EntityMoveHelper(this);
        this.jumpHelper = new EntityJumpHelper(this);
        this.bodyHelper = new EntityBodyHelper(this);
        this.navigator = new PathNavigate(this, p_i1595_1_);
        this.senses = new EntitySenses(this);
        for (int var2 = 0; var2 < this.equipmentDropChances.length; ++var2) {
            this.equipmentDropChances[var2] = 0.085f;
        }
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0);
    }
    
    public EntityLookHelper getLookHelper() {
        return this.lookHelper;
    }
    
    public EntityMoveHelper getMoveHelper() {
        return this.moveHelper;
    }
    
    public EntityJumpHelper getJumpHelper() {
        return this.jumpHelper;
    }
    
    public PathNavigate getNavigator() {
        return this.navigator;
    }
    
    public EntitySenses getEntitySenses() {
        return this.senses;
    }
    
    public EntityLivingBase getAttackTarget() {
        return this.attackTarget;
    }
    
    public void setAttackTarget(final EntityLivingBase p_70624_1_) {
        this.attackTarget = p_70624_1_;
    }
    
    public boolean canAttackClass(final Class p_70686_1_) {
        return EntityCreeper.class != p_70686_1_ && EntityGhast.class != p_70686_1_;
    }
    
    public void eatGrassBonus() {
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(11, 0);
        this.dataWatcher.addObject(10, "");
    }
    
    public int getTalkInterval() {
        return 80;
    }
    
    public void playLivingSound() {
        final String var1 = this.getLivingSound();
        if (var1 != null) {
            this.playSound(var1, this.getSoundVolume(), this.getSoundPitch());
        }
    }
    
    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        this.worldObj.theProfiler.startSection("mobBaseTick");
        if (this.isEntityAlive() && this.rand.nextInt(1000) < this.livingSoundTime++) {
            this.livingSoundTime = -this.getTalkInterval();
            this.playLivingSound();
        }
        this.worldObj.theProfiler.endSection();
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer p_70693_1_) {
        if (this.experienceValue > 0) {
            int var2 = this.experienceValue;
            final ItemStack[] var3 = this.getLastActiveItems();
            for (int var4 = 0; var4 < var3.length; ++var4) {
                if (var3[var4] != null && this.equipmentDropChances[var4] <= 1.0f) {
                    var2 += 1 + this.rand.nextInt(3);
                }
            }
            return var2;
        }
        return this.experienceValue;
    }
    
    public void spawnExplosionParticle() {
        for (int var1 = 0; var1 < 20; ++var1) {
            final double var2 = this.rand.nextGaussian() * 0.02;
            final double var3 = this.rand.nextGaussian() * 0.02;
            final double var4 = this.rand.nextGaussian() * 0.02;
            final double var5 = 10.0;
            this.worldObj.spawnParticle("explode", this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width - var2 * var5, this.posY + this.rand.nextFloat() * this.height - var3 * var5, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width - var4 * var5, var2, var3, var4);
        }
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isClient) {
            this.updateLeashedState();
        }
    }
    
    @Override
    protected float func_110146_f(final float p_110146_1_, final float p_110146_2_) {
        if (this.isAIEnabled()) {
            this.bodyHelper.func_75664_a();
            return p_110146_2_;
        }
        return super.func_110146_f(p_110146_1_, p_110146_2_);
    }
    
    protected String getLivingSound() {
        return null;
    }
    
    protected Item func_146068_u() {
        return Item.getItemById(0);
    }
    
    @Override
    protected void dropFewItems(final boolean p_70628_1_, final int p_70628_2_) {
        final Item var3 = this.func_146068_u();
        if (var3 != null) {
            int var4 = this.rand.nextInt(3);
            if (p_70628_2_ > 0) {
                var4 += this.rand.nextInt(p_70628_2_ + 1);
            }
            for (int var5 = 0; var5 < var4; ++var5) {
                this.func_145779_a(var3, 1);
            }
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
        p_70014_1_.setBoolean("CanPickUpLoot", this.canPickUpLoot());
        p_70014_1_.setBoolean("PersistenceRequired", this.persistenceRequired);
        final NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < this.equipment.length; ++var3) {
            final NBTTagCompound var4 = new NBTTagCompound();
            if (this.equipment[var3] != null) {
                this.equipment[var3].writeToNBT(var4);
            }
            var2.appendTag(var4);
        }
        p_70014_1_.setTag("Equipment", var2);
        final NBTTagList var5 = new NBTTagList();
        for (int var6 = 0; var6 < this.equipmentDropChances.length; ++var6) {
            var5.appendTag(new NBTTagFloat(this.equipmentDropChances[var6]));
        }
        p_70014_1_.setTag("DropChances", var5);
        p_70014_1_.setString("CustomName", this.getCustomNameTag());
        p_70014_1_.setBoolean("CustomNameVisible", this.getAlwaysRenderNameTag());
        p_70014_1_.setBoolean("Leashed", this.isLeashed);
        if (this.leashedToEntity != null) {
            final NBTTagCompound var4 = new NBTTagCompound();
            if (this.leashedToEntity instanceof EntityLivingBase) {
                var4.setLong("UUIDMost", this.leashedToEntity.getUniqueID().getMostSignificantBits());
                var4.setLong("UUIDLeast", this.leashedToEntity.getUniqueID().getLeastSignificantBits());
            }
            else if (this.leashedToEntity instanceof EntityHanging) {
                final EntityHanging var7 = (EntityHanging)this.leashedToEntity;
                var4.setInteger("X", var7.field_146063_b);
                var4.setInteger("Y", var7.field_146064_c);
                var4.setInteger("Z", var7.field_146062_d);
            }
            p_70014_1_.setTag("Leash", var4);
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
        this.setCanPickUpLoot(p_70037_1_.getBoolean("CanPickUpLoot"));
        this.persistenceRequired = p_70037_1_.getBoolean("PersistenceRequired");
        if (p_70037_1_.func_150297_b("CustomName", 8) && p_70037_1_.getString("CustomName").length() > 0) {
            this.setCustomNameTag(p_70037_1_.getString("CustomName"));
        }
        this.setAlwaysRenderNameTag(p_70037_1_.getBoolean("CustomNameVisible"));
        if (p_70037_1_.func_150297_b("Equipment", 9)) {
            final NBTTagList var2 = p_70037_1_.getTagList("Equipment", 10);
            for (int var3 = 0; var3 < this.equipment.length; ++var3) {
                this.equipment[var3] = ItemStack.loadItemStackFromNBT(var2.getCompoundTagAt(var3));
            }
        }
        if (p_70037_1_.func_150297_b("DropChances", 9)) {
            final NBTTagList var2 = p_70037_1_.getTagList("DropChances", 5);
            for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
                this.equipmentDropChances[var3] = var2.func_150308_e(var3);
            }
        }
        this.isLeashed = p_70037_1_.getBoolean("Leashed");
        if (this.isLeashed && p_70037_1_.func_150297_b("Leash", 10)) {
            this.field_110170_bx = p_70037_1_.getCompoundTag("Leash");
        }
    }
    
    public void setMoveForward(final float p_70657_1_) {
        this.moveForward = p_70657_1_;
    }
    
    @Override
    public void setAIMoveSpeed(final float p_70659_1_) {
        super.setAIMoveSpeed(p_70659_1_);
        this.setMoveForward(p_70659_1_);
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.worldObj.theProfiler.startSection("looting");
        if (!this.worldObj.isClient && this.canPickUpLoot() && !this.dead && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
            final List var1 = this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.boundingBox.expand(1.0, 0.0, 1.0));
            for (final EntityItem var3 : var1) {
                if (!var3.isDead && var3.getEntityItem() != null) {
                    final ItemStack var4 = var3.getEntityItem();
                    final int var5 = getArmorPosition(var4);
                    if (var5 <= -1) {
                        continue;
                    }
                    boolean var6 = true;
                    final ItemStack var7 = this.getEquipmentInSlot(var5);
                    if (var7 != null) {
                        if (var5 == 0) {
                            if (var4.getItem() instanceof ItemSword && !(var7.getItem() instanceof ItemSword)) {
                                var6 = true;
                            }
                            else if (var4.getItem() instanceof ItemSword && var7.getItem() instanceof ItemSword) {
                                final ItemSword var8 = (ItemSword)var4.getItem();
                                final ItemSword var9 = (ItemSword)var7.getItem();
                                if (var8.func_150931_i() == var9.func_150931_i()) {
                                    var6 = (var4.getItemDamage() > var7.getItemDamage() || (var4.hasTagCompound() && !var7.hasTagCompound()));
                                }
                                else {
                                    var6 = (var8.func_150931_i() > var9.func_150931_i());
                                }
                            }
                            else {
                                var6 = false;
                            }
                        }
                        else if (var4.getItem() instanceof ItemArmor && !(var7.getItem() instanceof ItemArmor)) {
                            var6 = true;
                        }
                        else if (var4.getItem() instanceof ItemArmor && var7.getItem() instanceof ItemArmor) {
                            final ItemArmor var10 = (ItemArmor)var4.getItem();
                            final ItemArmor var11 = (ItemArmor)var7.getItem();
                            if (var10.damageReduceAmount == var11.damageReduceAmount) {
                                var6 = (var4.getItemDamage() > var7.getItemDamage() || (var4.hasTagCompound() && !var7.hasTagCompound()));
                            }
                            else {
                                var6 = (var10.damageReduceAmount > var11.damageReduceAmount);
                            }
                        }
                        else {
                            var6 = false;
                        }
                    }
                    if (!var6) {
                        continue;
                    }
                    if (var7 != null && this.rand.nextFloat() - 0.1f < this.equipmentDropChances[var5]) {
                        this.entityDropItem(var7, 0.0f);
                    }
                    if (var4.getItem() == Items.diamond && var3.func_145800_j() != null) {
                        final EntityPlayer var12 = this.worldObj.getPlayerEntityByName(var3.func_145800_j());
                        if (var12 != null) {
                            var12.triggerAchievement(AchievementList.field_150966_x);
                        }
                    }
                    this.setCurrentItemOrArmor(var5, var4);
                    this.equipmentDropChances[var5] = 2.0f;
                    this.persistenceRequired = true;
                    this.onItemPickup(var3, 1);
                    var3.setDead();
                }
            }
        }
        this.worldObj.theProfiler.endSection();
    }
    
    @Override
    protected boolean isAIEnabled() {
        return false;
    }
    
    protected boolean canDespawn() {
        return true;
    }
    
    public void despawnEntity() {
        if (this.persistenceRequired) {
            this.entityAge = 0;
        }
        else {
            final EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, -1.0);
            if (var1 != null) {
                final double var2 = var1.posX - this.posX;
                final double var3 = var1.posY - this.posY;
                final double var4 = var1.posZ - this.posZ;
                final double var5 = var2 * var2 + var3 * var3 + var4 * var4;
                if (this.canDespawn() && var5 > 16384.0) {
                    this.setDead();
                }
                if (this.entityAge > 600 && this.rand.nextInt(800) == 0 && var5 > 1024.0 && this.canDespawn()) {
                    this.setDead();
                }
                else if (var5 < 1024.0) {
                    this.entityAge = 0;
                }
            }
        }
    }
    
    @Override
    protected void updateAITasks() {
        ++this.entityAge;
        this.worldObj.theProfiler.startSection("checkDespawn");
        this.despawnEntity();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("sensing");
        this.senses.clearSensingCache();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("targetSelector");
        this.targetTasks.onUpdateTasks();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("goalSelector");
        this.tasks.onUpdateTasks();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("navigation");
        this.navigator.onUpdateNavigation();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("mob tick");
        this.updateAITick();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("controls");
        this.worldObj.theProfiler.startSection("move");
        this.moveHelper.onUpdateMoveHelper();
        this.worldObj.theProfiler.endStartSection("look");
        this.lookHelper.onUpdateLook();
        this.worldObj.theProfiler.endStartSection("jump");
        this.jumpHelper.doJump();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.endSection();
    }
    
    @Override
    protected void updateEntityActionState() {
        super.updateEntityActionState();
        this.moveStrafing = 0.0f;
        this.moveForward = 0.0f;
        this.despawnEntity();
        final float var1 = 8.0f;
        if (this.rand.nextFloat() < 0.02f) {
            final EntityPlayer var2 = this.worldObj.getClosestPlayerToEntity(this, var1);
            if (var2 != null) {
                this.currentTarget = var2;
                this.numTicksToChaseTarget = 10 + this.rand.nextInt(20);
            }
            else {
                this.randomYawVelocity = (this.rand.nextFloat() - 0.5f) * 20.0f;
            }
        }
        if (this.currentTarget != null) {
            this.faceEntity(this.currentTarget, 10.0f, (float)this.getVerticalFaceSpeed());
            if (this.numTicksToChaseTarget-- <= 0 || this.currentTarget.isDead || this.currentTarget.getDistanceSqToEntity(this) > var1 * var1) {
                this.currentTarget = null;
            }
        }
        else {
            if (this.rand.nextFloat() < 0.05f) {
                this.randomYawVelocity = (this.rand.nextFloat() - 0.5f) * 20.0f;
            }
            this.rotationYaw += this.randomYawVelocity;
            this.rotationPitch = this.defaultPitch;
        }
        final boolean var3 = this.isInWater();
        final boolean var4 = this.handleLavaMovement();
        if (var3 || var4) {
            this.isJumping = (this.rand.nextFloat() < 0.8f);
        }
    }
    
    public int getVerticalFaceSpeed() {
        return 40;
    }
    
    public void faceEntity(final Entity p_70625_1_, final float p_70625_2_, final float p_70625_3_) {
        final double var4 = p_70625_1_.posX - this.posX;
        final double var5 = p_70625_1_.posZ - this.posZ;
        double var7;
        if (p_70625_1_ instanceof EntityLivingBase) {
            final EntityLivingBase var6 = (EntityLivingBase)p_70625_1_;
            var7 = var6.posY + var6.getEyeHeight() - (this.posY + this.getEyeHeight());
        }
        else {
            var7 = (p_70625_1_.boundingBox.minY + p_70625_1_.boundingBox.maxY) / 2.0 - (this.posY + this.getEyeHeight());
        }
        final double var8 = MathHelper.sqrt_double(var4 * var4 + var5 * var5);
        final float var9 = (float)(Math.atan2(var5, var4) * 180.0 / 3.141592653589793) - 90.0f;
        final float var10 = (float)(-(Math.atan2(var7, var8) * 180.0 / 3.141592653589793));
        this.rotationPitch = this.updateRotation(this.rotationPitch, var10, p_70625_3_);
        this.rotationYaw = this.updateRotation(this.rotationYaw, var9, p_70625_2_);
    }
    
    private float updateRotation(final float p_70663_1_, final float p_70663_2_, final float p_70663_3_) {
        float var4 = MathHelper.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);
        if (var4 > p_70663_3_) {
            var4 = p_70663_3_;
        }
        if (var4 < -p_70663_3_) {
            var4 = -p_70663_3_;
        }
        return p_70663_1_ + var4;
    }
    
    public boolean getCanSpawnHere() {
        return this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox);
    }
    
    public float getRenderSizeModifier() {
        return 1.0f;
    }
    
    public int getMaxSpawnedInChunk() {
        return 4;
    }
    
    @Override
    public int getMaxSafePointTries() {
        if (this.getAttackTarget() == null) {
            return 3;
        }
        int var1 = (int)(this.getHealth() - this.getMaxHealth() * 0.33f);
        var1 -= (3 - this.worldObj.difficultySetting.getDifficultyId()) * 4;
        if (var1 < 0) {
            var1 = 0;
        }
        return var1 + 3;
    }
    
    @Override
    public ItemStack getHeldItem() {
        return this.equipment[0];
    }
    
    @Override
    public ItemStack getEquipmentInSlot(final int p_71124_1_) {
        return this.equipment[p_71124_1_];
    }
    
    public ItemStack func_130225_q(final int p_130225_1_) {
        return this.equipment[p_130225_1_ + 1];
    }
    
    @Override
    public void setCurrentItemOrArmor(final int p_70062_1_, final ItemStack p_70062_2_) {
        this.equipment[p_70062_1_] = p_70062_2_;
    }
    
    @Override
    public ItemStack[] getLastActiveItems() {
        return this.equipment;
    }
    
    @Override
    protected void dropEquipment(final boolean p_82160_1_, final int p_82160_2_) {
        for (int var3 = 0; var3 < this.getLastActiveItems().length; ++var3) {
            final ItemStack var4 = this.getEquipmentInSlot(var3);
            final boolean var5 = this.equipmentDropChances[var3] > 1.0f;
            if (var4 != null && (p_82160_1_ || var5) && this.rand.nextFloat() - p_82160_2_ * 0.01f < this.equipmentDropChances[var3]) {
                if (!var5 && var4.isItemStackDamageable()) {
                    final int var6 = Math.max(var4.getMaxDamage() - 25, 1);
                    int var7 = var4.getMaxDamage() - this.rand.nextInt(this.rand.nextInt(var6) + 1);
                    if (var7 > var6) {
                        var7 = var6;
                    }
                    if (var7 < 1) {
                        var7 = 1;
                    }
                    var4.setItemDamage(var7);
                }
                this.entityDropItem(var4, 0.0f);
            }
        }
    }
    
    protected void addRandomArmor() {
        if (this.rand.nextFloat() < 0.15f * this.worldObj.func_147462_b(this.posX, this.posY, this.posZ)) {
            int var1 = this.rand.nextInt(2);
            final float var2 = (this.worldObj.difficultySetting == EnumDifficulty.HARD) ? 0.1f : 0.25f;
            if (this.rand.nextFloat() < 0.095f) {
                ++var1;
            }
            if (this.rand.nextFloat() < 0.095f) {
                ++var1;
            }
            if (this.rand.nextFloat() < 0.095f) {
                ++var1;
            }
            for (int var3 = 3; var3 >= 0; --var3) {
                final ItemStack var4 = this.func_130225_q(var3);
                if (var3 < 3 && this.rand.nextFloat() < var2) {
                    break;
                }
                if (var4 == null) {
                    final Item var5 = getArmorItemForSlot(var3 + 1, var1);
                    if (var5 != null) {
                        this.setCurrentItemOrArmor(var3 + 1, new ItemStack(var5));
                    }
                }
            }
        }
    }
    
    public static int getArmorPosition(final ItemStack p_82159_0_) {
        if (p_82159_0_.getItem() != Item.getItemFromBlock(Blocks.pumpkin) && p_82159_0_.getItem() != Items.skull) {
            if (p_82159_0_.getItem() instanceof ItemArmor) {
                switch (((ItemArmor)p_82159_0_.getItem()).armorType) {
                    case 0: {
                        return 4;
                    }
                    case 1: {
                        return 3;
                    }
                    case 2: {
                        return 2;
                    }
                    case 3: {
                        return 1;
                    }
                }
            }
            return 0;
        }
        return 4;
    }
    
    public static Item getArmorItemForSlot(final int p_82161_0_, final int p_82161_1_) {
        switch (p_82161_0_) {
            case 4: {
                if (p_82161_1_ == 0) {
                    return Items.leather_helmet;
                }
                if (p_82161_1_ == 1) {
                    return Items.golden_helmet;
                }
                if (p_82161_1_ == 2) {
                    return Items.chainmail_helmet;
                }
                if (p_82161_1_ == 3) {
                    return Items.iron_helmet;
                }
                if (p_82161_1_ == 4) {
                    return Items.diamond_helmet;
                }
            }
            case 3: {
                if (p_82161_1_ == 0) {
                    return Items.leather_chestplate;
                }
                if (p_82161_1_ == 1) {
                    return Items.golden_chestplate;
                }
                if (p_82161_1_ == 2) {
                    return Items.chainmail_chestplate;
                }
                if (p_82161_1_ == 3) {
                    return Items.iron_chestplate;
                }
                if (p_82161_1_ == 4) {
                    return Items.diamond_chestplate;
                }
            }
            case 2: {
                if (p_82161_1_ == 0) {
                    return Items.leather_leggings;
                }
                if (p_82161_1_ == 1) {
                    return Items.golden_leggings;
                }
                if (p_82161_1_ == 2) {
                    return Items.chainmail_leggings;
                }
                if (p_82161_1_ == 3) {
                    return Items.iron_leggings;
                }
                if (p_82161_1_ == 4) {
                    return Items.diamond_leggings;
                }
            }
            case 1: {
                if (p_82161_1_ == 0) {
                    return Items.leather_boots;
                }
                if (p_82161_1_ == 1) {
                    return Items.golden_boots;
                }
                if (p_82161_1_ == 2) {
                    return Items.chainmail_boots;
                }
                if (p_82161_1_ == 3) {
                    return Items.iron_boots;
                }
                if (p_82161_1_ == 4) {
                    return Items.diamond_boots;
                }
                break;
            }
        }
        return null;
    }
    
    protected void enchantEquipment() {
        final float var1 = this.worldObj.func_147462_b(this.posX, this.posY, this.posZ);
        if (this.getHeldItem() != null && this.rand.nextFloat() < 0.25f * var1) {
            EnchantmentHelper.addRandomEnchantment(this.rand, this.getHeldItem(), (int)(5.0f + var1 * this.rand.nextInt(18)));
        }
        for (int var2 = 0; var2 < 4; ++var2) {
            final ItemStack var3 = this.func_130225_q(var2);
            if (var3 != null && this.rand.nextFloat() < 0.5f * var1) {
                EnchantmentHelper.addRandomEnchantment(this.rand, var3, (int)(5.0f + var1 * this.rand.nextInt(18)));
            }
        }
    }
    
    public IEntityLivingData onSpawnWithEgg(final IEntityLivingData p_110161_1_) {
        this.getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextGaussian() * 0.05, 1));
        return p_110161_1_;
    }
    
    public boolean canBeSteered() {
        return false;
    }
    
    @Override
    public String getCommandSenderName() {
        return this.hasCustomNameTag() ? this.getCustomNameTag() : super.getCommandSenderName();
    }
    
    public void func_110163_bv() {
        this.persistenceRequired = true;
    }
    
    public void setCustomNameTag(final String p_94058_1_) {
        this.dataWatcher.updateObject(10, p_94058_1_);
    }
    
    public String getCustomNameTag() {
        return this.dataWatcher.getWatchableObjectString(10);
    }
    
    public boolean hasCustomNameTag() {
        return this.dataWatcher.getWatchableObjectString(10).length() > 0;
    }
    
    public void setAlwaysRenderNameTag(final boolean p_94061_1_) {
        this.dataWatcher.updateObject(11, (byte)(byte)(p_94061_1_ ? 1 : 0));
    }
    
    public boolean getAlwaysRenderNameTag() {
        return this.dataWatcher.getWatchableObjectByte(11) == 1;
    }
    
    @Override
    public boolean getAlwaysRenderNameTagForRender() {
        return this.getAlwaysRenderNameTag();
    }
    
    public void setEquipmentDropChance(final int p_96120_1_, final float p_96120_2_) {
        this.equipmentDropChances[p_96120_1_] = p_96120_2_;
    }
    
    public boolean canPickUpLoot() {
        return this.canPickUpLoot;
    }
    
    public void setCanPickUpLoot(final boolean p_98053_1_) {
        this.canPickUpLoot = p_98053_1_;
    }
    
    public boolean isNoDespawnRequired() {
        return this.persistenceRequired;
    }
    
    @Override
    public final boolean interactFirst(final EntityPlayer p_130002_1_) {
        if (this.getLeashed() && this.getLeashedToEntity() == p_130002_1_) {
            this.clearLeashed(true, !p_130002_1_.capabilities.isCreativeMode);
            return true;
        }
        final ItemStack var2 = p_130002_1_.inventory.getCurrentItem();
        if (var2 != null && var2.getItem() == Items.lead && this.allowLeashing()) {
            if (!(this instanceof EntityTameable) || !((EntityTameable)this).isTamed()) {
                this.setLeashedToEntity(p_130002_1_, true);
                final ItemStack itemStack = var2;
                --itemStack.stackSize;
                return true;
            }
            if (((EntityTameable)this).func_152114_e(p_130002_1_)) {
                this.setLeashedToEntity(p_130002_1_, true);
                final ItemStack itemStack2 = var2;
                --itemStack2.stackSize;
                return true;
            }
        }
        return this.interact(p_130002_1_) || super.interactFirst(p_130002_1_);
    }
    
    protected boolean interact(final EntityPlayer p_70085_1_) {
        return false;
    }
    
    protected void updateLeashedState() {
        if (this.field_110170_bx != null) {
            this.recreateLeash();
        }
        if (this.isLeashed && (this.leashedToEntity == null || this.leashedToEntity.isDead)) {
            this.clearLeashed(true, true);
        }
    }
    
    public void clearLeashed(final boolean p_110160_1_, final boolean p_110160_2_) {
        if (this.isLeashed) {
            this.isLeashed = false;
            this.leashedToEntity = null;
            if (!this.worldObj.isClient && p_110160_2_) {
                this.func_145779_a(Items.lead, 1);
            }
            if (!this.worldObj.isClient && p_110160_1_ && this.worldObj instanceof WorldServer) {
                ((WorldServer)this.worldObj).getEntityTracker().func_151247_a(this, new S1BPacketEntityAttach(1, this, null));
            }
        }
    }
    
    public boolean allowLeashing() {
        return !this.getLeashed() && !(this instanceof IMob);
    }
    
    public boolean getLeashed() {
        return this.isLeashed;
    }
    
    public Entity getLeashedToEntity() {
        return this.leashedToEntity;
    }
    
    public void setLeashedToEntity(final Entity p_110162_1_, final boolean p_110162_2_) {
        this.isLeashed = true;
        this.leashedToEntity = p_110162_1_;
        if (!this.worldObj.isClient && p_110162_2_ && this.worldObj instanceof WorldServer) {
            ((WorldServer)this.worldObj).getEntityTracker().func_151247_a(this, new S1BPacketEntityAttach(1, this, this.leashedToEntity));
        }
    }
    
    private void recreateLeash() {
        if (this.isLeashed && this.field_110170_bx != null) {
            if (this.field_110170_bx.func_150297_b("UUIDMost", 4) && this.field_110170_bx.func_150297_b("UUIDLeast", 4)) {
                final UUID var5 = new UUID(this.field_110170_bx.getLong("UUIDMost"), this.field_110170_bx.getLong("UUIDLeast"));
                final List var6 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(10.0, 10.0, 10.0));
                for (final EntityLivingBase var8 : var6) {
                    if (var8.getUniqueID().equals(var5)) {
                        this.leashedToEntity = var8;
                        break;
                    }
                }
            }
            else if (this.field_110170_bx.func_150297_b("X", 99) && this.field_110170_bx.func_150297_b("Y", 99) && this.field_110170_bx.func_150297_b("Z", 99)) {
                final int var9 = this.field_110170_bx.getInteger("X");
                final int var10 = this.field_110170_bx.getInteger("Y");
                final int var11 = this.field_110170_bx.getInteger("Z");
                EntityLeashKnot var12 = EntityLeashKnot.getKnotForBlock(this.worldObj, var9, var10, var11);
                if (var12 == null) {
                    var12 = EntityLeashKnot.func_110129_a(this.worldObj, var9, var10, var11);
                }
                this.leashedToEntity = var12;
            }
            else {
                this.clearLeashed(false, true);
            }
        }
        this.field_110170_bx = null;
    }
}
