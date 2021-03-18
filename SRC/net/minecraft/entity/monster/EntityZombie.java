package net.minecraft.entity.monster;

import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.command.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.potion.*;
import net.minecraft.entity.ai.attributes.*;

public class EntityZombie extends EntityMob
{
    protected static final IAttribute field_110186_bp;
    private static final UUID babySpeedBoostUUID;
    private static final AttributeModifier babySpeedBoostModifier;
    private final EntityAIBreakDoor field_146075_bs;
    private int conversionTime;
    private boolean field_146076_bu;
    private float field_146074_bv;
    private float field_146073_bw;
    private static final String __OBFID = "CL_00001702";
    
    public EntityZombie(final World p_i1745_1_) {
        super(p_i1745_1_);
        this.field_146075_bs = new EntityAIBreakDoor(this);
        this.field_146076_bu = false;
        this.field_146074_bv = -1.0f;
        this.getNavigator().setBreakDoors(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0, false));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0, false));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 0, false));
        this.setSize(0.6f, 1.8f);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0);
        this.getAttributeMap().registerAttribute(EntityZombie.field_110186_bp).setBaseValue(this.rand.nextDouble() * 0.10000000149011612);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(12, 0);
        this.getDataWatcher().addObject(13, 0);
        this.getDataWatcher().addObject(14, 0);
    }
    
    @Override
    public int getTotalArmorValue() {
        int var1 = super.getTotalArmorValue() + 2;
        if (var1 > 20) {
            var1 = 20;
        }
        return var1;
    }
    
    @Override
    protected boolean isAIEnabled() {
        return true;
    }
    
    public boolean func_146072_bX() {
        return this.field_146076_bu;
    }
    
    public void func_146070_a(final boolean p_146070_1_) {
        if (this.field_146076_bu != p_146070_1_) {
            this.field_146076_bu = p_146070_1_;
            if (p_146070_1_) {
                this.tasks.addTask(1, this.field_146075_bs);
            }
            else {
                this.tasks.removeTask(this.field_146075_bs);
            }
        }
    }
    
    @Override
    public boolean isChild() {
        return this.getDataWatcher().getWatchableObjectByte(12) == 1;
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer p_70693_1_) {
        if (this.isChild()) {
            this.experienceValue *= (int)2.5f;
        }
        return super.getExperiencePoints(p_70693_1_);
    }
    
    public void setChild(final boolean p_82227_1_) {
        this.getDataWatcher().updateObject(12, (byte)(byte)(p_82227_1_ ? 1 : 0));
        if (this.worldObj != null && !this.worldObj.isClient) {
            final IAttributeInstance var2 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
            var2.removeModifier(EntityZombie.babySpeedBoostModifier);
            if (p_82227_1_) {
                var2.applyModifier(EntityZombie.babySpeedBoostModifier);
            }
        }
        this.func_146071_k(p_82227_1_);
    }
    
    public boolean isVillager() {
        return this.getDataWatcher().getWatchableObjectByte(13) == 1;
    }
    
    public void setVillager(final boolean p_82229_1_) {
        this.getDataWatcher().updateObject(13, (byte)(byte)(p_82229_1_ ? 1 : 0));
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.worldObj.isDaytime() && !this.worldObj.isClient && !this.isChild()) {
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
        if (this.isRiding() && this.getAttackTarget() != null && this.ridingEntity instanceof EntityChicken) {
            ((EntityLiving)this.ridingEntity).getNavigator().setPath(this.getNavigator().getPath(), 1.5);
        }
        super.onLivingUpdate();
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource p_70097_1_, final float p_70097_2_) {
        if (!super.attackEntityFrom(p_70097_1_, p_70097_2_)) {
            return false;
        }
        EntityLivingBase var3 = this.getAttackTarget();
        if (var3 == null && this.getEntityToAttack() instanceof EntityLivingBase) {
            var3 = (EntityLivingBase)this.getEntityToAttack();
        }
        if (var3 == null && p_70097_1_.getEntity() instanceof EntityLivingBase) {
            var3 = (EntityLivingBase)p_70097_1_.getEntity();
        }
        if (var3 != null && this.worldObj.difficultySetting == EnumDifficulty.HARD && this.rand.nextFloat() < this.getEntityAttribute(EntityZombie.field_110186_bp).getAttributeValue()) {
            final int var4 = MathHelper.floor_double(this.posX);
            final int var5 = MathHelper.floor_double(this.posY);
            final int var6 = MathHelper.floor_double(this.posZ);
            final EntityZombie var7 = new EntityZombie(this.worldObj);
            for (int var8 = 0; var8 < 50; ++var8) {
                final int var9 = var4 + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
                final int var10 = var5 + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
                final int var11 = var6 + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
                if (World.doesBlockHaveSolidTopSurface(this.worldObj, var9, var10 - 1, var11) && this.worldObj.getBlockLightValue(var9, var10, var11) < 10) {
                    var7.setPosition(var9, var10, var11);
                    if (this.worldObj.checkNoEntityCollision(var7.boundingBox) && this.worldObj.getCollidingBoundingBoxes(var7, var7.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(var7.boundingBox)) {
                        this.worldObj.spawnEntityInWorld(var7);
                        var7.setAttackTarget(var3);
                        var7.onSpawnWithEgg(null);
                        this.getEntityAttribute(EntityZombie.field_110186_bp).applyModifier(new AttributeModifier("Zombie reinforcement caller charge", -0.05000000074505806, 0));
                        var7.getEntityAttribute(EntityZombie.field_110186_bp).applyModifier(new AttributeModifier("Zombie reinforcement callee charge", -0.05000000074505806, 0));
                        break;
                    }
                }
            }
        }
        return true;
    }
    
    @Override
    public void onUpdate() {
        if (!this.worldObj.isClient && this.isConverting()) {
            final int var1 = this.getConversionTimeBoost();
            this.conversionTime -= var1;
            if (this.conversionTime <= 0) {
                this.convertToVillager();
            }
        }
        super.onUpdate();
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity p_70652_1_) {
        final boolean var2 = super.attackEntityAsMob(p_70652_1_);
        if (var2) {
            final int var3 = this.worldObj.difficultySetting.getDifficultyId();
            if (this.getHeldItem() == null && this.isBurning() && this.rand.nextFloat() < var3 * 0.3f) {
                p_70652_1_.setFire(2 * var3);
            }
        }
        return var2;
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.zombie.say";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.zombie.hurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.zombie.death";
    }
    
    @Override
    protected void func_145780_a(final int p_145780_1_, final int p_145780_2_, final int p_145780_3_, final Block p_145780_4_) {
        this.playSound("mob.zombie.step", 0.15f, 1.0f);
    }
    
    @Override
    protected Item func_146068_u() {
        return Items.rotten_flesh;
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    @Override
    protected void dropRareDrop(final int p_70600_1_) {
        switch (this.rand.nextInt(3)) {
            case 0: {
                this.func_145779_a(Items.iron_ingot, 1);
                break;
            }
            case 1: {
                this.func_145779_a(Items.carrot, 1);
                break;
            }
            case 2: {
                this.func_145779_a(Items.potato, 1);
                break;
            }
        }
    }
    
    @Override
    protected void addRandomArmor() {
        super.addRandomArmor();
        if (this.rand.nextFloat() < ((this.worldObj.difficultySetting == EnumDifficulty.HARD) ? 0.05f : 0.01f)) {
            final int var1 = this.rand.nextInt(3);
            if (var1 == 0) {
                this.setCurrentItemOrArmor(0, new ItemStack(Items.iron_sword));
            }
            else {
                this.setCurrentItemOrArmor(0, new ItemStack(Items.iron_shovel));
            }
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
        if (this.isChild()) {
            p_70014_1_.setBoolean("IsBaby", true);
        }
        if (this.isVillager()) {
            p_70014_1_.setBoolean("IsVillager", true);
        }
        p_70014_1_.setInteger("ConversionTime", this.isConverting() ? this.conversionTime : -1);
        p_70014_1_.setBoolean("CanBreakDoors", this.func_146072_bX());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
        if (p_70037_1_.getBoolean("IsBaby")) {
            this.setChild(true);
        }
        if (p_70037_1_.getBoolean("IsVillager")) {
            this.setVillager(true);
        }
        if (p_70037_1_.func_150297_b("ConversionTime", 99) && p_70037_1_.getInteger("ConversionTime") > -1) {
            this.startConversion(p_70037_1_.getInteger("ConversionTime"));
        }
        this.func_146070_a(p_70037_1_.getBoolean("CanBreakDoors"));
    }
    
    @Override
    public void onKillEntity(final EntityLivingBase p_70074_1_) {
        super.onKillEntity(p_70074_1_);
        if ((this.worldObj.difficultySetting == EnumDifficulty.NORMAL || this.worldObj.difficultySetting == EnumDifficulty.HARD) && p_70074_1_ instanceof EntityVillager) {
            if (this.worldObj.difficultySetting != EnumDifficulty.HARD && this.rand.nextBoolean()) {
                return;
            }
            final EntityZombie var2 = new EntityZombie(this.worldObj);
            var2.copyLocationAndAnglesFrom(p_70074_1_);
            this.worldObj.removeEntity(p_70074_1_);
            var2.onSpawnWithEgg(null);
            var2.setVillager(true);
            if (p_70074_1_.isChild()) {
                var2.setChild(true);
            }
            this.worldObj.spawnEntityInWorld(var2);
            this.worldObj.playAuxSFXAtEntity(null, 1016, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
        }
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(final IEntityLivingData p_110161_1_) {
        Object p_110161_1_2 = super.onSpawnWithEgg(p_110161_1_);
        final float var2 = this.worldObj.func_147462_b(this.posX, this.posY, this.posZ);
        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55f * var2);
        if (p_110161_1_2 == null) {
            p_110161_1_2 = new GroupData(this.worldObj.rand.nextFloat() < 0.05f, this.worldObj.rand.nextFloat() < 0.05f, null);
        }
        if (p_110161_1_2 instanceof GroupData) {
            final GroupData var3 = (GroupData)p_110161_1_2;
            if (var3.field_142046_b) {
                this.setVillager(true);
            }
            if (var3.field_142048_a) {
                this.setChild(true);
                if (this.worldObj.rand.nextFloat() < 0.05) {
                    final List var4 = this.worldObj.selectEntitiesWithinAABB(EntityChicken.class, this.boundingBox.expand(5.0, 3.0, 5.0), IEntitySelector.field_152785_b);
                    if (!var4.isEmpty()) {
                        final EntityChicken var5 = var4.get(0);
                        var5.func_152117_i(true);
                        this.mountEntity(var5);
                    }
                }
                else if (this.worldObj.rand.nextFloat() < 0.05) {
                    final EntityChicken var6 = new EntityChicken(this.worldObj);
                    var6.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
                    var6.onSpawnWithEgg(null);
                    var6.func_152117_i(true);
                    this.worldObj.spawnEntityInWorld(var6);
                    this.mountEntity(var6);
                }
            }
        }
        this.func_146070_a(this.rand.nextFloat() < var2 * 0.1f);
        this.addRandomArmor();
        this.enchantEquipment();
        if (this.getEquipmentInSlot(4) == null) {
            final Calendar var7 = this.worldObj.getCurrentDate();
            if (var7.get(2) + 1 == 10 && var7.get(5) == 31 && this.rand.nextFloat() < 0.25f) {
                this.setCurrentItemOrArmor(4, new ItemStack((this.rand.nextFloat() < 0.1f) ? Blocks.lit_pumpkin : Blocks.pumpkin));
                this.equipmentDropChances[4] = 0.0f;
            }
        }
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextDouble() * 0.05000000074505806, 0));
        final double var8 = this.rand.nextDouble() * 1.5 * this.worldObj.func_147462_b(this.posX, this.posY, this.posZ);
        if (var8 > 1.0) {
            this.getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random zombie-spawn bonus", var8, 2));
        }
        if (this.rand.nextFloat() < var2 * 0.05f) {
            this.getEntityAttribute(EntityZombie.field_110186_bp).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 0.25 + 0.5, 0));
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 3.0 + 1.0, 2));
            this.func_146070_a(true);
        }
        return (IEntityLivingData)p_110161_1_2;
    }
    
    public boolean interact(final EntityPlayer p_70085_1_) {
        final ItemStack var2 = p_70085_1_.getCurrentEquippedItem();
        if (var2 != null && var2.getItem() == Items.golden_apple && var2.getItemDamage() == 0 && this.isVillager() && this.isPotionActive(Potion.weakness)) {
            if (!p_70085_1_.capabilities.isCreativeMode) {
                final ItemStack itemStack = var2;
                --itemStack.stackSize;
            }
            if (var2.stackSize <= 0) {
                p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem, null);
            }
            if (!this.worldObj.isClient) {
                this.startConversion(this.rand.nextInt(2401) + 3600);
            }
            return true;
        }
        return false;
    }
    
    protected void startConversion(final int p_82228_1_) {
        this.conversionTime = p_82228_1_;
        this.getDataWatcher().updateObject(14, 1);
        this.removePotionEffect(Potion.weakness.id);
        this.addPotionEffect(new PotionEffect(Potion.damageBoost.id, p_82228_1_, Math.min(this.worldObj.difficultySetting.getDifficultyId() - 1, 0)));
        this.worldObj.setEntityState(this, (byte)16);
    }
    
    @Override
    public void handleHealthUpdate(final byte p_70103_1_) {
        if (p_70103_1_ == 16) {
            this.worldObj.playSound(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, "mob.zombie.remedy", 1.0f + this.rand.nextFloat(), this.rand.nextFloat() * 0.7f + 0.3f, false);
        }
        else {
            super.handleHealthUpdate(p_70103_1_);
        }
    }
    
    @Override
    protected boolean canDespawn() {
        return !this.isConverting();
    }
    
    public boolean isConverting() {
        return this.getDataWatcher().getWatchableObjectByte(14) == 1;
    }
    
    protected void convertToVillager() {
        final EntityVillager var1 = new EntityVillager(this.worldObj);
        var1.copyLocationAndAnglesFrom(this);
        var1.onSpawnWithEgg(null);
        var1.setLookingForHome();
        if (this.isChild()) {
            var1.setGrowingAge(-24000);
        }
        this.worldObj.removeEntity(this);
        this.worldObj.spawnEntityInWorld(var1);
        var1.addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 0));
        this.worldObj.playAuxSFXAtEntity(null, 1017, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
    }
    
    protected int getConversionTimeBoost() {
        int var1 = 1;
        if (this.rand.nextFloat() < 0.01f) {
            for (int var2 = 0, var3 = (int)this.posX - 4; var3 < (int)this.posX + 4 && var2 < 14; ++var3) {
                for (int var4 = (int)this.posY - 4; var4 < (int)this.posY + 4 && var2 < 14; ++var4) {
                    for (int var5 = (int)this.posZ - 4; var5 < (int)this.posZ + 4 && var2 < 14; ++var5) {
                        final Block var6 = this.worldObj.getBlock(var3, var4, var5);
                        if (var6 == Blocks.iron_bars || var6 == Blocks.bed) {
                            if (this.rand.nextFloat() < 0.3f) {
                                ++var1;
                            }
                            ++var2;
                        }
                    }
                }
            }
        }
        return var1;
    }
    
    public void func_146071_k(final boolean p_146071_1_) {
        this.func_146069_a(p_146071_1_ ? 0.5f : 1.0f);
    }
    
    @Override
    protected final void setSize(final float p_70105_1_, final float p_70105_2_) {
        final boolean var3 = this.field_146074_bv > 0.0f && this.field_146073_bw > 0.0f;
        this.field_146074_bv = p_70105_1_;
        this.field_146073_bw = p_70105_2_;
        if (!var3) {
            this.func_146069_a(1.0f);
        }
    }
    
    protected final void func_146069_a(final float p_146069_1_) {
        super.setSize(this.field_146074_bv * p_146069_1_, this.field_146073_bw * p_146069_1_);
    }
    
    static {
        field_110186_bp = new RangedAttribute("zombie.spawnReinforcements", 0.0, 0.0, 1.0).setDescription("Spawn Reinforcements Chance");
        babySpeedBoostUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
        babySpeedBoostModifier = new AttributeModifier(EntityZombie.babySpeedBoostUUID, "Baby speed boost", 0.5, 1);
    }
    
    class GroupData implements IEntityLivingData
    {
        public boolean field_142048_a;
        public boolean field_142046_b;
        private static final String __OBFID = "CL_00001704";
        
        private GroupData(final boolean p_i2348_2_, final boolean p_i2348_3_) {
            this.field_142048_a = false;
            this.field_142046_b = false;
            this.field_142048_a = p_i2348_2_;
            this.field_142046_b = p_i2348_3_;
        }
        
        GroupData(final EntityZombie this$0, final boolean p_i2349_2_, final boolean p_i2349_3_, final Object p_i2349_4_) {
            this(this$0, p_i2349_2_, p_i2349_3_);
        }
    }
}
