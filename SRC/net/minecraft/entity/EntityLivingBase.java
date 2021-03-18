package net.minecraft.entity;

import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.enchantment.*;
import net.minecraft.nbt.*;
import net.minecraft.potion.*;
import net.minecraft.entity.passive.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.network.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.util.*;
import net.minecraft.scoreboard.*;

public abstract class EntityLivingBase extends Entity
{
    private static final UUID sprintingSpeedBoostModifierUUID;
    private static final AttributeModifier sprintingSpeedBoostModifier;
    private BaseAttributeMap attributeMap;
    private final CombatTracker _combatTracker;
    private final HashMap activePotionsMap;
    private final ItemStack[] previousEquipment;
    public boolean isSwingInProgress;
    public int swingProgressInt;
    public int arrowHitTimer;
    public float prevHealth;
    public int hurtTime;
    public int maxHurtTime;
    public float attackedAtYaw;
    public int deathTime;
    public int attackTime;
    public float prevSwingProgress;
    public float swingProgress;
    public float prevLimbSwingAmount;
    public float limbSwingAmount;
    public float limbSwing;
    public int maxHurtResistantTime;
    public float prevCameraPitch;
    public float cameraPitch;
    public float field_70769_ao;
    public float field_70770_ap;
    public float renderYawOffset;
    public float prevRenderYawOffset;
    public float rotationYawHead;
    public float prevRotationYawHead;
    public float jumpMovementFactor;
    protected EntityPlayer attackingPlayer;
    protected int recentlyHit;
    protected boolean dead;
    public int entityAge;
    protected float field_70768_au;
    protected float field_110154_aX;
    protected float field_70764_aw;
    protected float field_70763_ax;
    protected float field_70741_aB;
    protected int scoreValue;
    protected float lastDamage;
    protected boolean isJumping;
    public float moveStrafing;
    public float moveForward;
    protected float randomYawVelocity;
    protected int newPosRotationIncrements;
    protected double newPosX;
    protected double newPosY;
    protected double newPosZ;
    protected double newRotationYaw;
    protected double newRotationPitch;
    private boolean potionsNeedUpdate;
    private EntityLivingBase entityLivingToAttack;
    private int revengeTimer;
    private EntityLivingBase lastAttacker;
    private int lastAttackerTime;
    private float landMovementFactor;
    private int jumpTicks;
    private float field_110151_bq;
    private static final String __OBFID = "CL_00001549";
    
    public EntityLivingBase(final World p_i1594_1_) {
        super(p_i1594_1_);
        this._combatTracker = new CombatTracker(this);
        this.activePotionsMap = new HashMap();
        this.previousEquipment = new ItemStack[5];
        this.maxHurtResistantTime = 20;
        this.jumpMovementFactor = 0.02f;
        this.potionsNeedUpdate = true;
        this.applyEntityAttributes();
        this.setHealth(this.getMaxHealth());
        this.preventEntitySpawning = true;
        this.field_70770_ap = (float)(Math.random() + 1.0) * 0.01f;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.field_70769_ao = (float)Math.random() * 12398.0f;
        this.rotationYaw = (float)(Math.random() * 3.141592653589793 * 2.0);
        this.rotationYawHead = this.rotationYaw;
        this.stepHeight = 0.5f;
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(7, 0);
        this.dataWatcher.addObject(8, 0);
        this.dataWatcher.addObject(9, 0);
        this.dataWatcher.addObject(6, 1.0f);
    }
    
    protected void applyEntityAttributes() {
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.maxHealth);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.knockbackResistance);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.movementSpeed);
        if (!this.isAIEnabled()) {
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.10000000149011612);
        }
    }
    
    @Override
    protected void updateFallState(final double p_70064_1_, final boolean p_70064_3_) {
        if (!this.isInWater()) {
            this.handleWaterMovement();
        }
        if (p_70064_3_ && this.fallDistance > 0.0f) {
            final int var4 = MathHelper.floor_double(this.posX);
            final int var5 = MathHelper.floor_double(this.posY - 0.20000000298023224 - this.yOffset);
            final int var6 = MathHelper.floor_double(this.posZ);
            Block var7 = this.worldObj.getBlock(var4, var5, var6);
            if (var7.getMaterial() == Material.air) {
                final int var8 = this.worldObj.getBlock(var4, var5 - 1, var6).getRenderType();
                if (var8 == 11 || var8 == 32 || var8 == 21) {
                    var7 = this.worldObj.getBlock(var4, var5 - 1, var6);
                }
            }
            else if (!this.worldObj.isClient && this.fallDistance > 3.0f) {
                this.worldObj.playAuxSFX(2006, var4, var5, var6, MathHelper.ceiling_float_int(this.fallDistance - 3.0f));
            }
            var7.onFallenUpon(this.worldObj, var4, var5, var6, this, this.fallDistance);
        }
        super.updateFallState(p_70064_1_, p_70064_3_);
    }
    
    public boolean canBreatheUnderwater() {
        return false;
    }
    
    @Override
    public void onEntityUpdate() {
        this.prevSwingProgress = this.swingProgress;
        super.onEntityUpdate();
        this.worldObj.theProfiler.startSection("livingEntityBaseTick");
        if (this.isEntityAlive() && this.isEntityInsideOpaqueBlock()) {
            this.attackEntityFrom(DamageSource.inWall, 1.0f);
        }
        if (this.isImmuneToFire() || this.worldObj.isClient) {
            this.extinguish();
        }
        final boolean var1 = this instanceof EntityPlayer && ((EntityPlayer)this).capabilities.disableDamage;
        if (this.isEntityAlive() && this.isInsideOfMaterial(Material.water)) {
            if (!this.canBreatheUnderwater() && !this.isPotionActive(Potion.waterBreathing.id) && !var1) {
                this.setAir(this.decreaseAirSupply(this.getAir()));
                if (this.getAir() == -20) {
                    this.setAir(0);
                    for (int var2 = 0; var2 < 8; ++var2) {
                        final float var3 = this.rand.nextFloat() - this.rand.nextFloat();
                        final float var4 = this.rand.nextFloat() - this.rand.nextFloat();
                        final float var5 = this.rand.nextFloat() - this.rand.nextFloat();
                        this.worldObj.spawnParticle("bubble", this.posX + var3, this.posY + var4, this.posZ + var5, this.motionX, this.motionY, this.motionZ);
                    }
                    this.attackEntityFrom(DamageSource.drown, 2.0f);
                }
            }
            if (!this.worldObj.isClient && this.isRiding() && this.ridingEntity instanceof EntityLivingBase) {
                this.mountEntity(null);
            }
        }
        else {
            this.setAir(300);
        }
        if (this.isEntityAlive() && this.isWet()) {
            this.extinguish();
        }
        this.prevCameraPitch = this.cameraPitch;
        if (this.attackTime > 0) {
            --this.attackTime;
        }
        if (this.hurtTime > 0) {
            --this.hurtTime;
        }
        if (this.hurtResistantTime > 0 && !(this instanceof EntityPlayerMP)) {
            --this.hurtResistantTime;
        }
        if (this.getHealth() <= 0.0f) {
            this.onDeathUpdate();
        }
        if (this.recentlyHit > 0) {
            --this.recentlyHit;
        }
        else {
            this.attackingPlayer = null;
        }
        if (this.lastAttacker != null && !this.lastAttacker.isEntityAlive()) {
            this.lastAttacker = null;
        }
        if (this.entityLivingToAttack != null) {
            if (!this.entityLivingToAttack.isEntityAlive()) {
                this.setRevengeTarget(null);
            }
            else if (this.ticksExisted - this.revengeTimer > 100) {
                this.setRevengeTarget(null);
            }
        }
        this.updatePotionEffects();
        this.field_70763_ax = this.field_70764_aw;
        this.prevRenderYawOffset = this.renderYawOffset;
        this.prevRotationYawHead = this.rotationYawHead;
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
        this.worldObj.theProfiler.endSection();
    }
    
    public boolean isChild() {
        return false;
    }
    
    protected void onDeathUpdate() {
        ++this.deathTime;
        if (this.deathTime == 20) {
            if (!this.worldObj.isClient && (this.recentlyHit > 0 || this.isPlayer()) && this.func_146066_aG() && this.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")) {
                int var1 = this.getExperiencePoints(this.attackingPlayer);
                while (var1 > 0) {
                    final int var2 = EntityXPOrb.getXPSplit(var1);
                    var1 -= var2;
                    this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var2));
                }
            }
            this.setDead();
            for (int var1 = 0; var1 < 20; ++var1) {
                final double var3 = this.rand.nextGaussian() * 0.02;
                final double var4 = this.rand.nextGaussian() * 0.02;
                final double var5 = this.rand.nextGaussian() * 0.02;
                this.worldObj.spawnParticle("explode", this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, var3, var4, var5);
            }
        }
    }
    
    protected boolean func_146066_aG() {
        return !this.isChild();
    }
    
    protected int decreaseAirSupply(final int p_70682_1_) {
        final int var2 = EnchantmentHelper.getRespiration(this);
        return (var2 > 0 && this.rand.nextInt(var2 + 1) > 0) ? p_70682_1_ : (p_70682_1_ - 1);
    }
    
    protected int getExperiencePoints(final EntityPlayer p_70693_1_) {
        return 0;
    }
    
    protected boolean isPlayer() {
        return false;
    }
    
    public Random getRNG() {
        return this.rand;
    }
    
    public EntityLivingBase getAITarget() {
        return this.entityLivingToAttack;
    }
    
    public int func_142015_aE() {
        return this.revengeTimer;
    }
    
    public void setRevengeTarget(final EntityLivingBase p_70604_1_) {
        this.entityLivingToAttack = p_70604_1_;
        this.revengeTimer = this.ticksExisted;
    }
    
    public EntityLivingBase getLastAttacker() {
        return this.lastAttacker;
    }
    
    public int getLastAttackerTime() {
        return this.lastAttackerTime;
    }
    
    public void setLastAttacker(final Entity p_130011_1_) {
        if (p_130011_1_ instanceof EntityLivingBase) {
            this.lastAttacker = (EntityLivingBase)p_130011_1_;
        }
        else {
            this.lastAttacker = null;
        }
        this.lastAttackerTime = this.ticksExisted;
    }
    
    public int getAge() {
        return this.entityAge;
    }
    
    public void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        p_70014_1_.setFloat("HealF", this.getHealth());
        p_70014_1_.setShort("Health", (short)Math.ceil(this.getHealth()));
        p_70014_1_.setShort("HurtTime", (short)this.hurtTime);
        p_70014_1_.setShort("DeathTime", (short)this.deathTime);
        p_70014_1_.setShort("AttackTime", (short)this.attackTime);
        p_70014_1_.setFloat("AbsorptionAmount", this.getAbsorptionAmount());
        for (final ItemStack var5 : this.getLastActiveItems()) {
            if (var5 != null) {
                this.attributeMap.removeAttributeModifiers(var5.getAttributeModifiers());
            }
        }
        p_70014_1_.setTag("Attributes", SharedMonsterAttributes.writeBaseAttributeMapToNBT(this.getAttributeMap()));
        for (final ItemStack var5 : this.getLastActiveItems()) {
            if (var5 != null) {
                this.attributeMap.applyAttributeModifiers(var5.getAttributeModifiers());
            }
        }
        if (!this.activePotionsMap.isEmpty()) {
            final NBTTagList var6 = new NBTTagList();
            for (final PotionEffect var8 : this.activePotionsMap.values()) {
                var6.appendTag(var8.writeCustomPotionEffectToNBT(new NBTTagCompound()));
            }
            p_70014_1_.setTag("ActiveEffects", var6);
        }
    }
    
    public void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        this.setAbsorptionAmount(p_70037_1_.getFloat("AbsorptionAmount"));
        if (p_70037_1_.func_150297_b("Attributes", 9) && this.worldObj != null && !this.worldObj.isClient) {
            SharedMonsterAttributes.func_151475_a(this.getAttributeMap(), p_70037_1_.getTagList("Attributes", 10));
        }
        if (p_70037_1_.func_150297_b("ActiveEffects", 9)) {
            final NBTTagList var2 = p_70037_1_.getTagList("ActiveEffects", 10);
            for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
                final NBTTagCompound var4 = var2.getCompoundTagAt(var3);
                final PotionEffect var5 = PotionEffect.readCustomPotionEffectFromNBT(var4);
                if (var5 != null) {
                    this.activePotionsMap.put(var5.getPotionID(), var5);
                }
            }
        }
        if (p_70037_1_.func_150297_b("HealF", 99)) {
            this.setHealth(p_70037_1_.getFloat("HealF"));
        }
        else {
            final NBTBase var6 = p_70037_1_.getTag("Health");
            if (var6 == null) {
                this.setHealth(this.getMaxHealth());
            }
            else if (var6.getId() == 5) {
                this.setHealth(((NBTTagFloat)var6).func_150288_h());
            }
            else if (var6.getId() == 2) {
                this.setHealth(((NBTTagShort)var6).func_150289_e());
            }
        }
        this.hurtTime = p_70037_1_.getShort("HurtTime");
        this.deathTime = p_70037_1_.getShort("DeathTime");
        this.attackTime = p_70037_1_.getShort("AttackTime");
    }
    
    protected void updatePotionEffects() {
        final Iterator var1 = this.activePotionsMap.keySet().iterator();
        while (var1.hasNext()) {
            final Integer var2 = var1.next();
            final PotionEffect var3 = this.activePotionsMap.get(var2);
            if (!var3.onUpdate(this)) {
                if (this.worldObj.isClient) {
                    continue;
                }
                var1.remove();
                this.onFinishedPotionEffect(var3);
            }
            else {
                if (var3.getDuration() % 600 != 0) {
                    continue;
                }
                this.onChangedPotionEffect(var3, false);
            }
        }
        if (this.potionsNeedUpdate) {
            if (!this.worldObj.isClient) {
                if (this.activePotionsMap.isEmpty()) {
                    this.dataWatcher.updateObject(8, 0);
                    this.dataWatcher.updateObject(7, 0);
                    this.setInvisible(false);
                }
                else {
                    final int var4 = PotionHelper.calcPotionLiquidColor(this.activePotionsMap.values());
                    this.dataWatcher.updateObject(8, (byte)(byte)(PotionHelper.func_82817_b(this.activePotionsMap.values()) ? 1 : 0));
                    this.dataWatcher.updateObject(7, var4);
                    this.setInvisible(this.isPotionActive(Potion.invisibility.id));
                }
            }
            this.potionsNeedUpdate = false;
        }
        final int var4 = this.dataWatcher.getWatchableObjectInt(7);
        final boolean var5 = this.dataWatcher.getWatchableObjectByte(8) > 0;
        if (var4 > 0) {
            boolean var6 = false;
            if (!this.isInvisible()) {
                var6 = this.rand.nextBoolean();
            }
            else {
                var6 = (this.rand.nextInt(15) == 0);
            }
            if (var5) {
                var6 &= (this.rand.nextInt(5) == 0);
            }
            if (var6 && var4 > 0) {
                final double var7 = (var4 >> 16 & 0xFF) / 255.0;
                final double var8 = (var4 >> 8 & 0xFF) / 255.0;
                final double var9 = (var4 >> 0 & 0xFF) / 255.0;
                this.worldObj.spawnParticle(var5 ? "mobSpellAmbient" : "mobSpell", this.posX + (this.rand.nextDouble() - 0.5) * this.width, this.posY + this.rand.nextDouble() * this.height - this.yOffset, this.posZ + (this.rand.nextDouble() - 0.5) * this.width, var7, var8, var9);
            }
        }
    }
    
    public void clearActivePotions() {
        final Iterator var1 = this.activePotionsMap.keySet().iterator();
        while (var1.hasNext()) {
            final Integer var2 = var1.next();
            final PotionEffect var3 = this.activePotionsMap.get(var2);
            if (!this.worldObj.isClient) {
                var1.remove();
                this.onFinishedPotionEffect(var3);
            }
        }
    }
    
    public Collection getActivePotionEffects() {
        return this.activePotionsMap.values();
    }
    
    public boolean isPotionActive(final int p_82165_1_) {
        return this.activePotionsMap.containsKey(p_82165_1_);
    }
    
    public boolean isPotionActive(final Potion p_70644_1_) {
        return this.activePotionsMap.containsKey(p_70644_1_.id);
    }
    
    public PotionEffect getActivePotionEffect(final Potion p_70660_1_) {
        return this.activePotionsMap.get(p_70660_1_.id);
    }
    
    public void addPotionEffect(final PotionEffect p_70690_1_) {
        if (this.isPotionApplicable(p_70690_1_)) {
            if (this.activePotionsMap.containsKey(p_70690_1_.getPotionID())) {
                this.activePotionsMap.get(p_70690_1_.getPotionID()).combine(p_70690_1_);
                this.onChangedPotionEffect(this.activePotionsMap.get(p_70690_1_.getPotionID()), true);
            }
            else {
                this.activePotionsMap.put(p_70690_1_.getPotionID(), p_70690_1_);
                this.onNewPotionEffect(p_70690_1_);
            }
        }
    }
    
    public boolean isPotionApplicable(final PotionEffect p_70687_1_) {
        if (this.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
            final int var2 = p_70687_1_.getPotionID();
            if (var2 == Potion.regeneration.id || var2 == Potion.poison.id) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isEntityUndead() {
        return this.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD;
    }
    
    public void removePotionEffectClient(final int p_70618_1_) {
        this.activePotionsMap.remove(p_70618_1_);
    }
    
    public void removePotionEffect(final int p_82170_1_) {
        final PotionEffect var2 = this.activePotionsMap.remove(p_82170_1_);
        if (var2 != null) {
            this.onFinishedPotionEffect(var2);
        }
    }
    
    protected void onNewPotionEffect(final PotionEffect p_70670_1_) {
        this.potionsNeedUpdate = true;
        if (!this.worldObj.isClient) {
            Potion.potionTypes[p_70670_1_.getPotionID()].applyAttributesModifiersToEntity(this, this.getAttributeMap(), p_70670_1_.getAmplifier());
        }
    }
    
    protected void onChangedPotionEffect(final PotionEffect p_70695_1_, final boolean p_70695_2_) {
        this.potionsNeedUpdate = true;
        if (p_70695_2_ && !this.worldObj.isClient) {
            Potion.potionTypes[p_70695_1_.getPotionID()].removeAttributesModifiersFromEntity(this, this.getAttributeMap(), p_70695_1_.getAmplifier());
            Potion.potionTypes[p_70695_1_.getPotionID()].applyAttributesModifiersToEntity(this, this.getAttributeMap(), p_70695_1_.getAmplifier());
        }
    }
    
    protected void onFinishedPotionEffect(final PotionEffect p_70688_1_) {
        this.potionsNeedUpdate = true;
        if (!this.worldObj.isClient) {
            Potion.potionTypes[p_70688_1_.getPotionID()].removeAttributesModifiersFromEntity(this, this.getAttributeMap(), p_70688_1_.getAmplifier());
        }
    }
    
    public void heal(final float p_70691_1_) {
        final float var2 = this.getHealth();
        if (var2 > 0.0f) {
            this.setHealth(var2 + p_70691_1_);
        }
    }
    
    public final float getHealth() {
        return this.dataWatcher.getWatchableObjectFloat(6);
    }
    
    public void setHealth(final float p_70606_1_) {
        this.dataWatcher.updateObject(6, MathHelper.clamp_float(p_70606_1_, 0.0f, this.getMaxHealth()));
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource p_70097_1_, float p_70097_2_) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (this.worldObj.isClient) {
            return false;
        }
        this.entityAge = 0;
        if (this.getHealth() <= 0.0f) {
            return false;
        }
        if (p_70097_1_.isFireDamage() && this.isPotionActive(Potion.fireResistance)) {
            return false;
        }
        if ((p_70097_1_ == DamageSource.anvil || p_70097_1_ == DamageSource.fallingBlock) && this.getEquipmentInSlot(4) != null) {
            this.getEquipmentInSlot(4).damageItem((int)(p_70097_2_ * 4.0f + this.rand.nextFloat() * p_70097_2_ * 2.0f), this);
            p_70097_2_ *= 0.75f;
        }
        this.limbSwingAmount = 1.5f;
        boolean var3 = true;
        if (this.hurtResistantTime > this.maxHurtResistantTime / 2.0f) {
            if (p_70097_2_ <= this.lastDamage) {
                return false;
            }
            this.damageEntity(p_70097_1_, p_70097_2_ - this.lastDamage);
            this.lastDamage = p_70097_2_;
            var3 = false;
        }
        else {
            this.lastDamage = p_70097_2_;
            this.prevHealth = this.getHealth();
            this.hurtResistantTime = this.maxHurtResistantTime;
            this.damageEntity(p_70097_1_, p_70097_2_);
            final int n = 10;
            this.maxHurtTime = n;
            this.hurtTime = n;
        }
        this.attackedAtYaw = 0.0f;
        final Entity var4 = p_70097_1_.getEntity();
        if (var4 != null) {
            if (var4 instanceof EntityLivingBase) {
                this.setRevengeTarget((EntityLivingBase)var4);
            }
            if (var4 instanceof EntityPlayer) {
                this.recentlyHit = 100;
                this.attackingPlayer = (EntityPlayer)var4;
            }
            else if (var4 instanceof EntityWolf) {
                final EntityWolf var5 = (EntityWolf)var4;
                if (var5.isTamed()) {
                    this.recentlyHit = 100;
                    this.attackingPlayer = null;
                }
            }
        }
        if (var3) {
            this.worldObj.setEntityState(this, (byte)2);
            if (p_70097_1_ != DamageSource.drown) {
                this.setBeenAttacked();
            }
            if (var4 != null) {
                double var6;
                double var7;
                for (var6 = var4.posX - this.posX, var7 = var4.posZ - this.posZ; var6 * var6 + var7 * var7 < 1.0E-4; var6 = (Math.random() - Math.random()) * 0.01, var7 = (Math.random() - Math.random()) * 0.01) {}
                this.attackedAtYaw = (float)(Math.atan2(var7, var6) * 180.0 / 3.141592653589793) - this.rotationYaw;
                this.knockBack(var4, p_70097_2_, var6, var7);
            }
            else {
                this.attackedAtYaw = (float)((int)(Math.random() * 2.0) * 180);
            }
        }
        if (this.getHealth() <= 0.0f) {
            final String var8 = this.getDeathSound();
            if (var3 && var8 != null) {
                this.playSound(var8, this.getSoundVolume(), this.getSoundPitch());
            }
            this.onDeath(p_70097_1_);
        }
        else {
            final String var8 = this.getHurtSound();
            if (var3 && var8 != null) {
                this.playSound(var8, this.getSoundVolume(), this.getSoundPitch());
            }
        }
        return true;
    }
    
    public void renderBrokenItemStack(final ItemStack p_70669_1_) {
        this.playSound("random.break", 0.8f, 0.8f + this.worldObj.rand.nextFloat() * 0.4f);
        for (int var2 = 0; var2 < 5; ++var2) {
            final Vec3 var3 = Vec3.createVectorHelper((this.rand.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
            var3.rotateAroundX(-this.rotationPitch * 3.1415927f / 180.0f);
            var3.rotateAroundY(-this.rotationYaw * 3.1415927f / 180.0f);
            Vec3 var4 = Vec3.createVectorHelper((this.rand.nextFloat() - 0.5) * 0.3, -this.rand.nextFloat() * 0.6 - 0.3, 0.6);
            var4.rotateAroundX(-this.rotationPitch * 3.1415927f / 180.0f);
            var4.rotateAroundY(-this.rotationYaw * 3.1415927f / 180.0f);
            var4 = var4.addVector(this.posX, this.posY + this.getEyeHeight(), this.posZ);
            this.worldObj.spawnParticle("iconcrack_" + Item.getIdFromItem(p_70669_1_.getItem()), var4.xCoord, var4.yCoord, var4.zCoord, var3.xCoord, var3.yCoord + 0.05, var3.zCoord);
        }
    }
    
    public void onDeath(final DamageSource p_70645_1_) {
        final Entity var2 = p_70645_1_.getEntity();
        final EntityLivingBase var3 = this.func_94060_bK();
        if (this.scoreValue >= 0 && var3 != null) {
            var3.addToPlayerScore(this, this.scoreValue);
        }
        if (var2 != null) {
            var2.onKillEntity(this);
        }
        this.dead = true;
        this.func_110142_aN().func_94549_h();
        if (!this.worldObj.isClient) {
            int var4 = 0;
            if (var2 instanceof EntityPlayer) {
                var4 = EnchantmentHelper.getLootingModifier((EntityLivingBase)var2);
            }
            if (this.func_146066_aG() && this.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")) {
                this.dropFewItems(this.recentlyHit > 0, var4);
                this.dropEquipment(this.recentlyHit > 0, var4);
                if (this.recentlyHit > 0) {
                    final int var5 = this.rand.nextInt(200) - var4;
                    if (var5 < 5) {
                        this.dropRareDrop((var5 <= 0) ? 1 : 0);
                    }
                }
            }
        }
        this.worldObj.setEntityState(this, (byte)3);
    }
    
    protected void dropEquipment(final boolean p_82160_1_, final int p_82160_2_) {
    }
    
    public void knockBack(final Entity p_70653_1_, final float p_70653_2_, final double p_70653_3_, final double p_70653_5_) {
        if (this.rand.nextDouble() >= this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue()) {
            this.isAirBorne = true;
            final float var7 = MathHelper.sqrt_double(p_70653_3_ * p_70653_3_ + p_70653_5_ * p_70653_5_);
            final float var8 = 0.4f;
            this.motionX /= 2.0;
            this.motionY /= 2.0;
            this.motionZ /= 2.0;
            this.motionX -= p_70653_3_ / var7 * var8;
            this.motionY += var8;
            this.motionZ -= p_70653_5_ / var7 * var8;
            if (this.motionY > 0.4000000059604645) {
                this.motionY = 0.4000000059604645;
            }
        }
    }
    
    protected String getHurtSound() {
        return "game.neutral.hurt";
    }
    
    protected String getDeathSound() {
        return "game.neutral.die";
    }
    
    protected void dropRareDrop(final int p_70600_1_) {
    }
    
    protected void dropFewItems(final boolean p_70628_1_, final int p_70628_2_) {
    }
    
    public boolean isOnLadder() {
        final int var1 = MathHelper.floor_double(this.posX);
        final int var2 = MathHelper.floor_double(this.boundingBox.minY);
        final int var3 = MathHelper.floor_double(this.posZ);
        final Block var4 = this.worldObj.getBlock(var1, var2, var3);
        return var4 == Blocks.ladder || var4 == Blocks.vine;
    }
    
    @Override
    public boolean isEntityAlive() {
        return !this.isDead && this.getHealth() > 0.0f;
    }
    
    @Override
    protected void fall(final float p_70069_1_) {
        super.fall(p_70069_1_);
        final PotionEffect var2 = this.getActivePotionEffect(Potion.jump);
        final float var3 = (var2 != null) ? ((float)(var2.getAmplifier() + 1)) : 0.0f;
        final int var4 = MathHelper.ceiling_float_int(p_70069_1_ - 3.0f - var3);
        if (var4 > 0) {
            this.playSound(this.func_146067_o(var4), 1.0f, 1.0f);
            this.attackEntityFrom(DamageSource.fall, (float)var4);
            final int var5 = MathHelper.floor_double(this.posX);
            final int var6 = MathHelper.floor_double(this.posY - 0.20000000298023224 - this.yOffset);
            final int var7 = MathHelper.floor_double(this.posZ);
            final Block var8 = this.worldObj.getBlock(var5, var6, var7);
            if (var8.getMaterial() != Material.air) {
                final Block.SoundType var9 = var8.stepSound;
                this.playSound(var9.func_150498_e(), var9.func_150497_c() * 0.5f, var9.func_150494_d() * 0.75f);
            }
        }
    }
    
    protected String func_146067_o(final int p_146067_1_) {
        return (p_146067_1_ > 4) ? "game.neutral.hurt.fall.big" : "game.neutral.hurt.fall.small";
    }
    
    @Override
    public void performHurtAnimation() {
        final int n = 10;
        this.maxHurtTime = n;
        this.hurtTime = n;
        this.attackedAtYaw = 0.0f;
    }
    
    public int getTotalArmorValue() {
        int var1 = 0;
        for (final ItemStack var5 : this.getLastActiveItems()) {
            if (var5 != null && var5.getItem() instanceof ItemArmor) {
                final int var6 = ((ItemArmor)var5.getItem()).damageReduceAmount;
                var1 += var6;
            }
        }
        return var1;
    }
    
    protected void damageArmor(final float p_70675_1_) {
    }
    
    protected float applyArmorCalculations(final DamageSource p_70655_1_, float p_70655_2_) {
        if (!p_70655_1_.isUnblockable()) {
            final int var3 = 25 - this.getTotalArmorValue();
            final float var4 = p_70655_2_ * var3;
            this.damageArmor(p_70655_2_);
            p_70655_2_ = var4 / 25.0f;
        }
        return p_70655_2_;
    }
    
    protected float applyPotionDamageCalculations(final DamageSource p_70672_1_, float p_70672_2_) {
        if (p_70672_1_.isDamageAbsolute()) {
            return p_70672_2_;
        }
        if (this instanceof EntityZombie) {
            p_70672_2_ = p_70672_2_;
        }
        if (this.isPotionActive(Potion.resistance) && p_70672_1_ != DamageSource.outOfWorld) {
            final int var3 = (this.getActivePotionEffect(Potion.resistance).getAmplifier() + 1) * 5;
            final int var4 = 25 - var3;
            final float var5 = p_70672_2_ * var4;
            p_70672_2_ = var5 / 25.0f;
        }
        if (p_70672_2_ <= 0.0f) {
            return 0.0f;
        }
        int var3 = EnchantmentHelper.getEnchantmentModifierDamage(this.getLastActiveItems(), p_70672_1_);
        if (var3 > 20) {
            var3 = 20;
        }
        if (var3 > 0 && var3 <= 20) {
            final int var4 = 25 - var3;
            final float var5 = p_70672_2_ * var4;
            p_70672_2_ = var5 / 25.0f;
        }
        return p_70672_2_;
    }
    
    protected void damageEntity(final DamageSource p_70665_1_, float p_70665_2_) {
        if (!this.isEntityInvulnerable()) {
            p_70665_2_ = this.applyArmorCalculations(p_70665_1_, p_70665_2_);
            final float var3;
            p_70665_2_ = (var3 = this.applyPotionDamageCalculations(p_70665_1_, p_70665_2_));
            p_70665_2_ = Math.max(p_70665_2_ - this.getAbsorptionAmount(), 0.0f);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - (var3 - p_70665_2_));
            if (p_70665_2_ != 0.0f) {
                final float var4 = this.getHealth();
                this.setHealth(var4 - p_70665_2_);
                this.func_110142_aN().func_94547_a(p_70665_1_, var4, p_70665_2_);
                this.setAbsorptionAmount(this.getAbsorptionAmount() - p_70665_2_);
            }
        }
    }
    
    public CombatTracker func_110142_aN() {
        return this._combatTracker;
    }
    
    public EntityLivingBase func_94060_bK() {
        return (this._combatTracker.func_94550_c() != null) ? this._combatTracker.func_94550_c() : ((this.attackingPlayer != null) ? this.attackingPlayer : ((this.entityLivingToAttack != null) ? this.entityLivingToAttack : null));
    }
    
    public final float getMaxHealth() {
        return (float)this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
    }
    
    public final int getArrowCountInEntity() {
        return this.dataWatcher.getWatchableObjectByte(9);
    }
    
    public final void setArrowCountInEntity(final int p_85034_1_) {
        this.dataWatcher.updateObject(9, (byte)p_85034_1_);
    }
    
    private int getArmSwingAnimationEnd() {
        return this.isPotionActive(Potion.digSpeed) ? (6 - (1 + this.getActivePotionEffect(Potion.digSpeed).getAmplifier()) * 1) : (this.isPotionActive(Potion.digSlowdown) ? (6 + (1 + this.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) * 2) : 6);
    }
    
    public void swingItem() {
        if (!this.isSwingInProgress || this.swingProgressInt >= this.getArmSwingAnimationEnd() / 2 || this.swingProgressInt < 0) {
            this.swingProgressInt = -1;
            this.isSwingInProgress = true;
            if (this.worldObj instanceof WorldServer) {
                ((WorldServer)this.worldObj).getEntityTracker().func_151247_a(this, new S0BPacketAnimation(this, 0));
            }
        }
    }
    
    @Override
    public void handleHealthUpdate(final byte p_70103_1_) {
        if (p_70103_1_ == 2) {
            this.limbSwingAmount = 1.5f;
            this.hurtResistantTime = this.maxHurtResistantTime;
            final int n = 10;
            this.maxHurtTime = n;
            this.hurtTime = n;
            this.attackedAtYaw = 0.0f;
            this.playSound(this.getHurtSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            this.attackEntityFrom(DamageSource.generic, 0.0f);
        }
        else if (p_70103_1_ == 3) {
            this.playSound(this.getDeathSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            this.setHealth(0.0f);
            this.onDeath(DamageSource.generic);
        }
        else {
            super.handleHealthUpdate(p_70103_1_);
        }
    }
    
    @Override
    protected void kill() {
        this.attackEntityFrom(DamageSource.outOfWorld, 4.0f);
    }
    
    protected void updateArmSwingProgress() {
        final int var1 = this.getArmSwingAnimationEnd();
        if (this.isSwingInProgress) {
            ++this.swingProgressInt;
            if (this.swingProgressInt >= var1) {
                this.swingProgressInt = 0;
                this.isSwingInProgress = false;
            }
        }
        else {
            this.swingProgressInt = 0;
        }
        this.swingProgress = this.swingProgressInt / (float)var1;
    }
    
    public IAttributeInstance getEntityAttribute(final IAttribute p_110148_1_) {
        return this.getAttributeMap().getAttributeInstance(p_110148_1_);
    }
    
    public BaseAttributeMap getAttributeMap() {
        if (this.attributeMap == null) {
            this.attributeMap = new ServersideAttributeMap();
        }
        return this.attributeMap;
    }
    
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEFINED;
    }
    
    public abstract ItemStack getHeldItem();
    
    public abstract ItemStack getEquipmentInSlot(final int p0);
    
    @Override
    public abstract void setCurrentItemOrArmor(final int p0, final ItemStack p1);
    
    @Override
    public void setSprinting(final boolean p_70031_1_) {
        super.setSprinting(p_70031_1_);
        final IAttributeInstance var2 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        if (var2.getModifier(EntityLivingBase.sprintingSpeedBoostModifierUUID) != null) {
            var2.removeModifier(EntityLivingBase.sprintingSpeedBoostModifier);
        }
        if (p_70031_1_) {
            var2.applyModifier(EntityLivingBase.sprintingSpeedBoostModifier);
        }
    }
    
    @Override
    public abstract ItemStack[] getLastActiveItems();
    
    protected float getSoundVolume() {
        return 1.0f;
    }
    
    protected float getSoundPitch() {
        return this.isChild() ? ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.5f) : ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
    }
    
    protected boolean isMovementBlocked() {
        return this.getHealth() <= 0.0f;
    }
    
    public void setPositionAndUpdate(final double p_70634_1_, final double p_70634_3_, final double p_70634_5_) {
        this.setLocationAndAngles(p_70634_1_, p_70634_3_, p_70634_5_, this.rotationYaw, this.rotationPitch);
    }
    
    public void dismountEntity(final Entity p_110145_1_) {
        double var3 = p_110145_1_.posX;
        double var4 = p_110145_1_.boundingBox.minY + p_110145_1_.height;
        double var5 = p_110145_1_.posZ;
        final byte var6 = 1;
        for (int var7 = -var6; var7 <= var6; ++var7) {
            for (int var8 = -var6; var8 < var6; ++var8) {
                if (var7 != 0 || var8 != 0) {
                    final int var9 = (int)(this.posX + var7);
                    final int var10 = (int)(this.posZ + var8);
                    final AxisAlignedBB var11 = this.boundingBox.getOffsetBoundingBox(var7, 1.0, var8);
                    if (this.worldObj.func_147461_a(var11).isEmpty()) {
                        if (World.doesBlockHaveSolidTopSurface(this.worldObj, var9, (int)this.posY, var10)) {
                            this.setPositionAndUpdate(this.posX + var7, this.posY + 1.0, this.posZ + var8);
                            return;
                        }
                        if (World.doesBlockHaveSolidTopSurface(this.worldObj, var9, (int)this.posY - 1, var10) || this.worldObj.getBlock(var9, (int)this.posY - 1, var10).getMaterial() == Material.water) {
                            var3 = this.posX + var7;
                            var4 = this.posY + 1.0;
                            var5 = this.posZ + var8;
                        }
                    }
                }
            }
        }
        this.setPositionAndUpdate(var3, var4, var5);
    }
    
    public boolean getAlwaysRenderNameTagForRender() {
        return false;
    }
    
    public IIcon getItemIcon(final ItemStack p_70620_1_, final int p_70620_2_) {
        return p_70620_1_.getItem().requiresMultipleRenderPasses() ? p_70620_1_.getItem().getIconFromDamageForRenderPass(p_70620_1_.getItemDamage(), p_70620_2_) : p_70620_1_.getIconIndex();
    }
    
    protected void jump() {
        this.motionY = 0.41999998688697815;
        if (this.isPotionActive(Potion.jump)) {
            this.motionY += (this.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1f;
        }
        if (this.isSprinting()) {
            final float var1 = this.rotationYaw * 0.017453292f;
            this.motionX -= MathHelper.sin(var1) * 0.2f;
            this.motionZ += MathHelper.cos(var1) * 0.2f;
        }
        this.isAirBorne = true;
    }
    
    public void moveEntityWithHeading(final float p_70612_1_, final float p_70612_2_) {
        if (this.isInWater() && (!(this instanceof EntityPlayer) || !((EntityPlayer)this).capabilities.isFlying)) {
            final double var8 = this.posY;
            this.moveFlying(p_70612_1_, p_70612_2_, this.isAIEnabled() ? 0.04f : 0.02f);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.800000011920929;
            this.motionY *= 0.800000011920929;
            this.motionZ *= 0.800000011920929;
            this.motionY -= 0.02;
            if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579 - this.posY + var8, this.motionZ)) {
                this.motionY = 0.30000001192092896;
            }
        }
        else if (this.handleLavaMovement() && (!(this instanceof EntityPlayer) || !((EntityPlayer)this).capabilities.isFlying)) {
            final double var8 = this.posY;
            this.moveFlying(p_70612_1_, p_70612_2_, 0.02f);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.5;
            this.motionY *= 0.5;
            this.motionZ *= 0.5;
            this.motionY -= 0.02;
            if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579 - this.posY + var8, this.motionZ)) {
                this.motionY = 0.30000001192092896;
            }
        }
        else {
            float var9 = 0.91f;
            if (this.onGround) {
                var9 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91f;
            }
            final float var10 = 0.16277136f / (var9 * var9 * var9);
            float var11;
            if (this.onGround) {
                var11 = this.getAIMoveSpeed() * var10;
            }
            else {
                var11 = this.jumpMovementFactor;
            }
            this.moveFlying(p_70612_1_, p_70612_2_, var11);
            var9 = 0.91f;
            if (this.onGround) {
                var9 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91f;
            }
            if (this.isOnLadder()) {
                final float var12 = 0.15f;
                if (this.motionX < -var12) {
                    this.motionX = -var12;
                }
                if (this.motionX > var12) {
                    this.motionX = var12;
                }
                if (this.motionZ < -var12) {
                    this.motionZ = -var12;
                }
                if (this.motionZ > var12) {
                    this.motionZ = var12;
                }
                this.fallDistance = 0.0f;
                if (this.motionY < -0.15) {
                    this.motionY = -0.15;
                }
                final boolean var13 = this.isSneaking() && this instanceof EntityPlayer;
                if (var13 && this.motionY < 0.0) {
                    this.motionY = 0.0;
                }
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            if (this.isCollidedHorizontally && this.isOnLadder()) {
                this.motionY = 0.2;
            }
            if (this.worldObj.isClient && (!this.worldObj.blockExists((int)this.posX, 0, (int)this.posZ) || !this.worldObj.getChunkFromBlockCoords((int)this.posX, (int)this.posZ).isChunkLoaded)) {
                if (this.posY > 0.0) {
                    this.motionY = -0.1;
                }
                else {
                    this.motionY = 0.0;
                }
            }
            else {
                this.motionY -= 0.08;
            }
            this.motionY *= 0.9800000190734863;
            this.motionX *= var9;
            this.motionZ *= var9;
        }
        this.prevLimbSwingAmount = this.limbSwingAmount;
        final double var8 = this.posX - this.prevPosX;
        final double var14 = this.posZ - this.prevPosZ;
        float var15 = MathHelper.sqrt_double(var8 * var8 + var14 * var14) * 4.0f;
        if (var15 > 1.0f) {
            var15 = 1.0f;
        }
        this.limbSwingAmount += (var15 - this.limbSwingAmount) * 0.4f;
        this.limbSwing += this.limbSwingAmount;
    }
    
    protected boolean isAIEnabled() {
        return false;
    }
    
    public float getAIMoveSpeed() {
        return this.isAIEnabled() ? this.landMovementFactor : 0.1f;
    }
    
    public void setAIMoveSpeed(final float p_70659_1_) {
        this.landMovementFactor = p_70659_1_;
    }
    
    public boolean attackEntityAsMob(final Entity p_70652_1_) {
        this.setLastAttacker(p_70652_1_);
        return false;
    }
    
    public boolean isPlayerSleeping() {
        return false;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isClient) {
            final int var1 = this.getArrowCountInEntity();
            if (var1 > 0) {
                if (this.arrowHitTimer <= 0) {
                    this.arrowHitTimer = 20 * (30 - var1);
                }
                --this.arrowHitTimer;
                if (this.arrowHitTimer <= 0) {
                    this.setArrowCountInEntity(var1 - 1);
                }
            }
            for (int var2 = 0; var2 < 5; ++var2) {
                final ItemStack var3 = this.previousEquipment[var2];
                final ItemStack var4 = this.getEquipmentInSlot(var2);
                if (!ItemStack.areItemStacksEqual(var4, var3)) {
                    ((WorldServer)this.worldObj).getEntityTracker().func_151247_a(this, new S04PacketEntityEquipment(this.getEntityId(), var2, var4));
                    if (var3 != null) {
                        this.attributeMap.removeAttributeModifiers(var3.getAttributeModifiers());
                    }
                    if (var4 != null) {
                        this.attributeMap.applyAttributeModifiers(var4.getAttributeModifiers());
                    }
                    this.previousEquipment[var2] = ((var4 == null) ? null : var4.copy());
                }
            }
            if (this.ticksExisted % 20 == 0) {
                this.func_110142_aN().func_94549_h();
            }
        }
        this.onLivingUpdate();
        final double var5 = this.posX - this.prevPosX;
        final double var6 = this.posZ - this.prevPosZ;
        final float var7 = (float)(var5 * var5 + var6 * var6);
        float var8 = this.renderYawOffset;
        float var9 = 0.0f;
        this.field_70768_au = this.field_110154_aX;
        float var10 = 0.0f;
        if (var7 > 0.0025000002f) {
            var10 = 1.0f;
            var9 = (float)Math.sqrt(var7) * 3.0f;
            var8 = (float)Math.atan2(var6, var5) * 180.0f / 3.1415927f - 90.0f;
        }
        if (this.swingProgress > 0.0f) {
            var8 = this.rotationYaw;
        }
        if (!this.onGround) {
            var10 = 0.0f;
        }
        this.field_110154_aX += (var10 - this.field_110154_aX) * 0.3f;
        this.worldObj.theProfiler.startSection("headTurn");
        var9 = this.func_110146_f(var8, var9);
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("rangeChecks");
        while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
            this.prevRotationYaw -= 360.0f;
        }
        while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
            this.prevRotationYaw += 360.0f;
        }
        while (this.renderYawOffset - this.prevRenderYawOffset < -180.0f) {
            this.prevRenderYawOffset -= 360.0f;
        }
        while (this.renderYawOffset - this.prevRenderYawOffset >= 180.0f) {
            this.prevRenderYawOffset += 360.0f;
        }
        while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
            this.prevRotationPitch -= 360.0f;
        }
        while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
            this.prevRotationPitch += 360.0f;
        }
        while (this.rotationYawHead - this.prevRotationYawHead < -180.0f) {
            this.prevRotationYawHead -= 360.0f;
        }
        while (this.rotationYawHead - this.prevRotationYawHead >= 180.0f) {
            this.prevRotationYawHead += 360.0f;
        }
        this.worldObj.theProfiler.endSection();
        this.field_70764_aw += var9;
    }
    
    protected float func_110146_f(final float p_110146_1_, float p_110146_2_) {
        final float var3 = MathHelper.wrapAngleTo180_float(p_110146_1_ - this.renderYawOffset);
        this.renderYawOffset += var3 * 0.3f;
        float var4 = MathHelper.wrapAngleTo180_float(this.rotationYaw - this.renderYawOffset);
        final boolean var5 = var4 < -90.0f || var4 >= 90.0f;
        if (var4 < -75.0f) {
            var4 = -75.0f;
        }
        if (var4 >= 75.0f) {
            var4 = 75.0f;
        }
        this.renderYawOffset = this.rotationYaw - var4;
        if (var4 * var4 > 2500.0f) {
            this.renderYawOffset += var4 * 0.2f;
        }
        if (var5) {
            p_110146_2_ *= -1.0f;
        }
        return p_110146_2_;
    }
    
    public void onLivingUpdate() {
        if (this.jumpTicks > 0) {
            --this.jumpTicks;
        }
        if (this.newPosRotationIncrements > 0) {
            final double var1 = this.posX + (this.newPosX - this.posX) / this.newPosRotationIncrements;
            final double var2 = this.posY + (this.newPosY - this.posY) / this.newPosRotationIncrements;
            final double var3 = this.posZ + (this.newPosZ - this.posZ) / this.newPosRotationIncrements;
            final double var4 = MathHelper.wrapAngleTo180_double(this.newRotationYaw - this.rotationYaw);
            this.rotationYaw += (float)(var4 / this.newPosRotationIncrements);
            this.rotationPitch += (float)((this.newRotationPitch - this.rotationPitch) / this.newPosRotationIncrements);
            --this.newPosRotationIncrements;
            this.setPosition(var1, var2, var3);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
        else if (!this.isClientWorld()) {
            this.motionX *= 0.98;
            this.motionY *= 0.98;
            this.motionZ *= 0.98;
        }
        if (Math.abs(this.motionX) < 0.005) {
            this.motionX = 0.0;
        }
        if (Math.abs(this.motionY) < 0.005) {
            this.motionY = 0.0;
        }
        if (Math.abs(this.motionZ) < 0.005) {
            this.motionZ = 0.0;
        }
        this.worldObj.theProfiler.startSection("ai");
        if (this.isMovementBlocked()) {
            this.isJumping = false;
            this.moveStrafing = 0.0f;
            this.moveForward = 0.0f;
            this.randomYawVelocity = 0.0f;
        }
        else if (this.isClientWorld()) {
            if (this.isAIEnabled()) {
                this.worldObj.theProfiler.startSection("newAi");
                this.updateAITasks();
                this.worldObj.theProfiler.endSection();
            }
            else {
                this.worldObj.theProfiler.startSection("oldAi");
                this.updateEntityActionState();
                this.worldObj.theProfiler.endSection();
                this.rotationYawHead = this.rotationYaw;
            }
        }
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("jump");
        if (this.isJumping) {
            if (!this.isInWater() && !this.handleLavaMovement()) {
                if (this.onGround && this.jumpTicks == 0) {
                    this.jump();
                    this.jumpTicks = 10;
                }
            }
            else {
                this.motionY += 0.03999999910593033;
            }
        }
        else {
            this.jumpTicks = 0;
        }
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("travel");
        this.moveStrafing *= 0.98f;
        this.moveForward *= 0.98f;
        this.randomYawVelocity *= 0.9f;
        this.moveEntityWithHeading(this.moveStrafing, this.moveForward);
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("push");
        if (!this.worldObj.isClient) {
            this.collideWithNearbyEntities();
        }
        this.worldObj.theProfiler.endSection();
    }
    
    protected void updateAITasks() {
    }
    
    protected void collideWithNearbyEntities() {
        final List var1 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.20000000298023224, 0.0, 0.20000000298023224));
        if (var1 != null && !var1.isEmpty()) {
            for (int var2 = 0; var2 < var1.size(); ++var2) {
                final Entity var3 = var1.get(var2);
                if (var3.canBePushed()) {
                    this.collideWithEntity(var3);
                }
            }
        }
    }
    
    protected void collideWithEntity(final Entity p_82167_1_) {
        p_82167_1_.applyEntityCollision(this);
    }
    
    @Override
    public void updateRidden() {
        super.updateRidden();
        this.field_70768_au = this.field_110154_aX;
        this.field_110154_aX = 0.0f;
        this.fallDistance = 0.0f;
    }
    
    @Override
    public void setPositionAndRotation2(final double p_70056_1_, final double p_70056_3_, final double p_70056_5_, final float p_70056_7_, final float p_70056_8_, final int p_70056_9_) {
        this.yOffset = 0.0f;
        this.newPosX = p_70056_1_;
        this.newPosY = p_70056_3_;
        this.newPosZ = p_70056_5_;
        this.newRotationYaw = p_70056_7_;
        this.newRotationPitch = p_70056_8_;
        this.newPosRotationIncrements = p_70056_9_;
    }
    
    protected void updateAITick() {
    }
    
    protected void updateEntityActionState() {
        ++this.entityAge;
    }
    
    public void setJumping(final boolean p_70637_1_) {
        this.isJumping = p_70637_1_;
    }
    
    public void onItemPickup(final Entity p_71001_1_, final int p_71001_2_) {
        if (!p_71001_1_.isDead && !this.worldObj.isClient) {
            final EntityTracker var3 = ((WorldServer)this.worldObj).getEntityTracker();
            if (p_71001_1_ instanceof EntityItem) {
                var3.func_151247_a(p_71001_1_, new S0DPacketCollectItem(p_71001_1_.getEntityId(), this.getEntityId()));
            }
            if (p_71001_1_ instanceof EntityArrow) {
                var3.func_151247_a(p_71001_1_, new S0DPacketCollectItem(p_71001_1_.getEntityId(), this.getEntityId()));
            }
            if (p_71001_1_ instanceof EntityXPOrb) {
                var3.func_151247_a(p_71001_1_, new S0DPacketCollectItem(p_71001_1_.getEntityId(), this.getEntityId()));
            }
        }
    }
    
    public boolean canEntityBeSeen(final Entity p_70685_1_) {
        return this.worldObj.rayTraceBlocks(Vec3.createVectorHelper(this.posX, this.posY + this.getEyeHeight(), this.posZ), Vec3.createVectorHelper(p_70685_1_.posX, p_70685_1_.posY + p_70685_1_.getEyeHeight(), p_70685_1_.posZ)) == null;
    }
    
    @Override
    public Vec3 getLookVec() {
        return this.getLook(1.0f);
    }
    
    public Vec3 getLook(final float p_70676_1_) {
        if (p_70676_1_ == 1.0f) {
            final float var2 = MathHelper.cos(-this.rotationYaw * 0.017453292f - 3.1415927f);
            final float var3 = MathHelper.sin(-this.rotationYaw * 0.017453292f - 3.1415927f);
            final float var4 = -MathHelper.cos(-this.rotationPitch * 0.017453292f);
            final float var5 = MathHelper.sin(-this.rotationPitch * 0.017453292f);
            return Vec3.createVectorHelper(var3 * var4, var5, var2 * var4);
        }
        final float var2 = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * p_70676_1_;
        final float var3 = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * p_70676_1_;
        final float var4 = MathHelper.cos(-var3 * 0.017453292f - 3.1415927f);
        final float var5 = MathHelper.sin(-var3 * 0.017453292f - 3.1415927f);
        final float var6 = -MathHelper.cos(-var2 * 0.017453292f);
        final float var7 = MathHelper.sin(-var2 * 0.017453292f);
        return Vec3.createVectorHelper(var5 * var6, var7, var4 * var6);
    }
    
    public float getSwingProgress(final float p_70678_1_) {
        float var2 = this.swingProgress - this.prevSwingProgress;
        if (var2 < 0.0f) {
            ++var2;
        }
        return this.prevSwingProgress + var2 * p_70678_1_;
    }
    
    public Vec3 getPosition(final float p_70666_1_) {
        if (p_70666_1_ == 1.0f) {
            return Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        }
        final double var2 = this.prevPosX + (this.posX - this.prevPosX) * p_70666_1_;
        final double var3 = this.prevPosY + (this.posY - this.prevPosY) * p_70666_1_;
        final double var4 = this.prevPosZ + (this.posZ - this.prevPosZ) * p_70666_1_;
        return Vec3.createVectorHelper(var2, var3, var4);
    }
    
    public MovingObjectPosition rayTrace(final double p_70614_1_, final float p_70614_3_) {
        final Vec3 var4 = this.getPosition(p_70614_3_);
        final Vec3 var5 = this.getLook(p_70614_3_);
        final Vec3 var6 = var4.addVector(var5.xCoord * p_70614_1_, var5.yCoord * p_70614_1_, var5.zCoord * p_70614_1_);
        return this.worldObj.func_147447_a(var4, var6, false, false, true);
    }
    
    public boolean isClientWorld() {
        return !this.worldObj.isClient;
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }
    
    @Override
    public boolean canBePushed() {
        return !this.isDead;
    }
    
    @Override
    public float getEyeHeight() {
        return this.height * 0.85f;
    }
    
    @Override
    protected void setBeenAttacked() {
        this.velocityChanged = (this.rand.nextDouble() >= this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue());
    }
    
    @Override
    public float getRotationYawHead() {
        return this.rotationYawHead;
    }
    
    @Override
    public void setRotationYawHead(final float p_70034_1_) {
        this.rotationYawHead = p_70034_1_;
    }
    
    public float getAbsorptionAmount() {
        return this.field_110151_bq;
    }
    
    public void setAbsorptionAmount(float p_110149_1_) {
        if (p_110149_1_ < 0.0f) {
            p_110149_1_ = 0.0f;
        }
        this.field_110151_bq = p_110149_1_;
    }
    
    public Team getTeam() {
        return null;
    }
    
    public boolean isOnSameTeam(final EntityLivingBase p_142014_1_) {
        return this.isOnTeam(p_142014_1_.getTeam());
    }
    
    public boolean isOnTeam(final Team p_142012_1_) {
        return this.getTeam() != null && this.getTeam().isSameTeam(p_142012_1_);
    }
    
    public void func_152111_bt() {
    }
    
    public void func_152112_bu() {
    }
    
    static {
        sprintingSpeedBoostModifierUUID = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
        sprintingSpeedBoostModifier = new AttributeModifier(EntityLivingBase.sprintingSpeedBoostModifierUUID, "Sprinting speed boost", 0.30000001192092896, 2).setSaved(false);
    }
}
