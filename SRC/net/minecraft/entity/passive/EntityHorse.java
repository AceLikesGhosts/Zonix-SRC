package net.minecraft.entity.passive;

import net.minecraft.command.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import java.util.*;
import net.minecraft.inventory.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;

public class EntityHorse extends EntityAnimal implements IInvBasic
{
    private static final IEntitySelector horseBreedingSelector;
    private static final IAttribute horseJumpStrength;
    private static final String[] horseArmorTextures;
    private static final String[] field_110273_bx;
    private static final int[] armorValues;
    private static final String[] horseTextures;
    private static final String[] field_110269_bA;
    private static final String[] horseMarkingTextures;
    private static final String[] field_110292_bC;
    private int eatingHaystackCounter;
    private int openMouthCounter;
    private int jumpRearingCounter;
    public int field_110278_bp;
    public int field_110279_bq;
    protected boolean horseJumping;
    private AnimalChest horseChest;
    private boolean hasReproduced;
    protected int temper;
    protected float jumpPower;
    private boolean field_110294_bI;
    private float headLean;
    private float prevHeadLean;
    private float rearingAmount;
    private float prevRearingAmount;
    private float mouthOpenness;
    private float prevMouthOpenness;
    private int field_110285_bP;
    private String field_110286_bQ;
    private String[] field_110280_bR;
    private static final String __OBFID = "CL_00001641";
    
    public EntityHorse(final World p_i1685_1_) {
        super(p_i1685_1_);
        this.field_110280_bR = new String[3];
        this.setSize(1.4f, 1.6f);
        this.setChested(this.isImmuneToFire = false);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.2));
        this.tasks.addTask(1, new EntityAIRunAroundLikeCrazy(this, 1.2));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.0));
        this.tasks.addTask(6, new EntityAIWander(this, 0.7));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.func_110226_cD();
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 0);
        this.dataWatcher.addObject(19, 0);
        this.dataWatcher.addObject(20, 0);
        this.dataWatcher.addObject(21, String.valueOf(""));
        this.dataWatcher.addObject(22, 0);
    }
    
    public void setHorseType(final int p_110214_1_) {
        this.dataWatcher.updateObject(19, (byte)p_110214_1_);
        this.func_110230_cF();
    }
    
    public int getHorseType() {
        return this.dataWatcher.getWatchableObjectByte(19);
    }
    
    public void setHorseVariant(final int p_110235_1_) {
        this.dataWatcher.updateObject(20, p_110235_1_);
        this.func_110230_cF();
    }
    
    public int getHorseVariant() {
        return this.dataWatcher.getWatchableObjectInt(20);
    }
    
    @Override
    public String getCommandSenderName() {
        if (this.hasCustomNameTag()) {
            return this.getCustomNameTag();
        }
        final int var1 = this.getHorseType();
        switch (var1) {
            default: {
                return StatCollector.translateToLocal("entity.horse.name");
            }
            case 1: {
                return StatCollector.translateToLocal("entity.donkey.name");
            }
            case 2: {
                return StatCollector.translateToLocal("entity.mule.name");
            }
            case 3: {
                return StatCollector.translateToLocal("entity.zombiehorse.name");
            }
            case 4: {
                return StatCollector.translateToLocal("entity.skeletonhorse.name");
            }
        }
    }
    
    private boolean getHorseWatchableBoolean(final int p_110233_1_) {
        return (this.dataWatcher.getWatchableObjectInt(16) & p_110233_1_) != 0x0;
    }
    
    private void setHorseWatchableBoolean(final int p_110208_1_, final boolean p_110208_2_) {
        final int var3 = this.dataWatcher.getWatchableObjectInt(16);
        if (p_110208_2_) {
            this.dataWatcher.updateObject(16, var3 | p_110208_1_);
        }
        else {
            this.dataWatcher.updateObject(16, var3 & ~p_110208_1_);
        }
    }
    
    public boolean isAdultHorse() {
        return !this.isChild();
    }
    
    public boolean isTame() {
        return this.getHorseWatchableBoolean(2);
    }
    
    public boolean func_110253_bW() {
        return this.isAdultHorse();
    }
    
    public String func_152119_ch() {
        return this.dataWatcher.getWatchableObjectString(21);
    }
    
    public void func_152120_b(final String p_152120_1_) {
        this.dataWatcher.updateObject(21, p_152120_1_);
    }
    
    public float getHorseSize() {
        final int var1 = this.getGrowingAge();
        return (var1 >= 0) ? 1.0f : (0.5f + (-24000 - var1) / -24000.0f * 0.5f);
    }
    
    @Override
    public void setScaleForAge(final boolean p_98054_1_) {
        if (p_98054_1_) {
            this.setScale(this.getHorseSize());
        }
        else {
            this.setScale(1.0f);
        }
    }
    
    public boolean isHorseJumping() {
        return this.horseJumping;
    }
    
    public void setHorseTamed(final boolean p_110234_1_) {
        this.setHorseWatchableBoolean(2, p_110234_1_);
    }
    
    public void setHorseJumping(final boolean p_110255_1_) {
        this.horseJumping = p_110255_1_;
    }
    
    @Override
    public boolean allowLeashing() {
        return !this.func_110256_cu() && super.allowLeashing();
    }
    
    @Override
    protected void func_142017_o(final float p_142017_1_) {
        if (p_142017_1_ > 6.0f && this.isEatingHaystack()) {
            this.setEatingHaystack(false);
        }
    }
    
    public boolean isChested() {
        return this.getHorseWatchableBoolean(8);
    }
    
    public int func_110241_cb() {
        return this.dataWatcher.getWatchableObjectInt(22);
    }
    
    private int getHorseArmorIndex(final ItemStack p_110260_1_) {
        if (p_110260_1_ == null) {
            return 0;
        }
        final Item var2 = p_110260_1_.getItem();
        return (var2 == Items.iron_horse_armor) ? 1 : ((var2 == Items.golden_horse_armor) ? 2 : ((var2 == Items.diamond_horse_armor) ? 3 : 0));
    }
    
    public boolean isEatingHaystack() {
        return this.getHorseWatchableBoolean(32);
    }
    
    public boolean isRearing() {
        return this.getHorseWatchableBoolean(64);
    }
    
    public boolean func_110205_ce() {
        return this.getHorseWatchableBoolean(16);
    }
    
    public boolean getHasReproduced() {
        return this.hasReproduced;
    }
    
    public void func_146086_d(final ItemStack p_146086_1_) {
        this.dataWatcher.updateObject(22, this.getHorseArmorIndex(p_146086_1_));
        this.func_110230_cF();
    }
    
    public void func_110242_l(final boolean p_110242_1_) {
        this.setHorseWatchableBoolean(16, p_110242_1_);
    }
    
    public void setChested(final boolean p_110207_1_) {
        this.setHorseWatchableBoolean(8, p_110207_1_);
    }
    
    public void setHasReproduced(final boolean p_110221_1_) {
        this.hasReproduced = p_110221_1_;
    }
    
    public void setHorseSaddled(final boolean p_110251_1_) {
        this.setHorseWatchableBoolean(4, p_110251_1_);
    }
    
    public int getTemper() {
        return this.temper;
    }
    
    public void setTemper(final int p_110238_1_) {
        this.temper = p_110238_1_;
    }
    
    public int increaseTemper(final int p_110198_1_) {
        final int var2 = MathHelper.clamp_int(this.getTemper() + p_110198_1_, 0, this.getMaxTemper());
        this.setTemper(var2);
        return var2;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource p_70097_1_, final float p_70097_2_) {
        final Entity var3 = p_70097_1_.getEntity();
        return (this.riddenByEntity == null || !this.riddenByEntity.equals(var3)) && super.attackEntityFrom(p_70097_1_, p_70097_2_);
    }
    
    @Override
    public int getTotalArmorValue() {
        return EntityHorse.armorValues[this.func_110241_cb()];
    }
    
    @Override
    public boolean canBePushed() {
        return this.riddenByEntity == null;
    }
    
    public boolean prepareChunkForSpawn() {
        final int var1 = MathHelper.floor_double(this.posX);
        final int var2 = MathHelper.floor_double(this.posZ);
        this.worldObj.getBiomeGenForCoords(var1, var2);
        return true;
    }
    
    public void dropChests() {
        if (!this.worldObj.isClient && this.isChested()) {
            this.func_145779_a(Item.getItemFromBlock(Blocks.chest), 1);
            this.setChested(false);
        }
    }
    
    private void func_110266_cB() {
        this.openHorseMouth();
        this.worldObj.playSoundAtEntity(this, "eating", 1.0f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f);
    }
    
    @Override
    protected void fall(final float p_70069_1_) {
        if (p_70069_1_ > 1.0f) {
            this.playSound("mob.horse.land", 0.4f, 1.0f);
        }
        final int var2 = MathHelper.ceiling_float_int(p_70069_1_ * 0.5f - 3.0f);
        if (var2 > 0) {
            this.attackEntityFrom(DamageSource.fall, (float)var2);
            if (this.riddenByEntity != null) {
                this.riddenByEntity.attackEntityFrom(DamageSource.fall, (float)var2);
            }
            final Block var3 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 0.2 - this.prevRotationYaw), MathHelper.floor_double(this.posZ));
            if (var3.getMaterial() != Material.air) {
                final Block.SoundType var4 = var3.stepSound;
                this.worldObj.playSoundAtEntity(this, var4.func_150498_e(), var4.func_150497_c() * 0.5f, var4.func_150494_d() * 0.75f);
            }
        }
    }
    
    private int func_110225_cC() {
        final int var1 = this.getHorseType();
        return (this.isChested() && (var1 == 1 || var1 == 2)) ? 17 : 2;
    }
    
    private void func_110226_cD() {
        AnimalChest var1 = this.horseChest;
        (this.horseChest = new AnimalChest("HorseChest", this.func_110225_cC())).func_110133_a(this.getCommandSenderName());
        if (var1 != null) {
            var1.func_110132_b(this);
            for (int var2 = Math.min(var1.getSizeInventory(), this.horseChest.getSizeInventory()), var3 = 0; var3 < var2; ++var3) {
                final ItemStack var4 = var1.getStackInSlot(var3);
                if (var4 != null) {
                    this.horseChest.setInventorySlotContents(var3, var4.copy());
                }
            }
            var1 = null;
        }
        this.horseChest.func_110134_a(this);
        this.func_110232_cE();
    }
    
    private void func_110232_cE() {
        if (!this.worldObj.isClient) {
            this.setHorseSaddled(this.horseChest.getStackInSlot(0) != null);
            if (this.func_110259_cr()) {
                this.func_146086_d(this.horseChest.getStackInSlot(1));
            }
        }
    }
    
    @Override
    public void onInventoryChanged(final InventoryBasic p_76316_1_) {
        final int var2 = this.func_110241_cb();
        final boolean var3 = this.isHorseSaddled();
        this.func_110232_cE();
        if (this.ticksExisted > 20) {
            if (var2 == 0 && var2 != this.func_110241_cb()) {
                this.playSound("mob.horse.armor", 0.5f, 1.0f);
            }
            else if (var2 != this.func_110241_cb()) {
                this.playSound("mob.horse.armor", 0.5f, 1.0f);
            }
            if (!var3 && this.isHorseSaddled()) {
                this.playSound("mob.horse.leather", 0.5f, 1.0f);
            }
        }
    }
    
    @Override
    public boolean getCanSpawnHere() {
        this.prepareChunkForSpawn();
        return super.getCanSpawnHere();
    }
    
    protected EntityHorse getClosestHorse(final Entity p_110250_1_, final double p_110250_2_) {
        double var4 = Double.MAX_VALUE;
        Entity var5 = null;
        final List var6 = this.worldObj.getEntitiesWithinAABBExcludingEntity(p_110250_1_, p_110250_1_.boundingBox.addCoord(p_110250_2_, p_110250_2_, p_110250_2_), EntityHorse.horseBreedingSelector);
        for (final Entity var8 : var6) {
            final double var9 = var8.getDistanceSq(p_110250_1_.posX, p_110250_1_.posY, p_110250_1_.posZ);
            if (var9 < var4) {
                var5 = var8;
                var4 = var9;
            }
        }
        return (EntityHorse)var5;
    }
    
    public double getHorseJumpStrength() {
        return this.getEntityAttribute(EntityHorse.horseJumpStrength).getAttributeValue();
    }
    
    @Override
    protected String getDeathSound() {
        this.openHorseMouth();
        final int var1 = this.getHorseType();
        return (var1 == 3) ? "mob.horse.zombie.death" : ((var1 == 4) ? "mob.horse.skeleton.death" : ((var1 != 1 && var1 != 2) ? "mob.horse.death" : "mob.horse.donkey.death"));
    }
    
    @Override
    protected Item func_146068_u() {
        final boolean var1 = this.rand.nextInt(4) == 0;
        final int var2 = this.getHorseType();
        return (var2 == 4) ? Items.bone : ((var2 == 3) ? (var1 ? Item.getItemById(0) : Items.rotten_flesh) : Items.leather);
    }
    
    @Override
    protected String getHurtSound() {
        this.openHorseMouth();
        if (this.rand.nextInt(3) == 0) {
            this.makeHorseRear();
        }
        final int var1 = this.getHorseType();
        return (var1 == 3) ? "mob.horse.zombie.hit" : ((var1 == 4) ? "mob.horse.skeleton.hit" : ((var1 != 1 && var1 != 2) ? "mob.horse.hit" : "mob.horse.donkey.hit"));
    }
    
    public boolean isHorseSaddled() {
        return this.getHorseWatchableBoolean(4);
    }
    
    @Override
    protected String getLivingSound() {
        this.openHorseMouth();
        if (this.rand.nextInt(10) == 0 && !this.isMovementBlocked()) {
            this.makeHorseRear();
        }
        final int var1 = this.getHorseType();
        return (var1 == 3) ? "mob.horse.zombie.idle" : ((var1 == 4) ? "mob.horse.skeleton.idle" : ((var1 != 1 && var1 != 2) ? "mob.horse.idle" : "mob.horse.donkey.idle"));
    }
    
    protected String getAngrySoundName() {
        this.openHorseMouth();
        this.makeHorseRear();
        final int var1 = this.getHorseType();
        return (var1 != 3 && var1 != 4) ? ((var1 != 1 && var1 != 2) ? "mob.horse.angry" : "mob.horse.donkey.angry") : null;
    }
    
    @Override
    protected void func_145780_a(final int p_145780_1_, final int p_145780_2_, final int p_145780_3_, final Block p_145780_4_) {
        Block.SoundType var5 = p_145780_4_.stepSound;
        if (this.worldObj.getBlock(p_145780_1_, p_145780_2_ + 1, p_145780_3_) == Blocks.snow_layer) {
            var5 = Blocks.snow_layer.stepSound;
        }
        if (!p_145780_4_.getMaterial().isLiquid()) {
            final int var6 = this.getHorseType();
            if (this.riddenByEntity != null && var6 != 1 && var6 != 2) {
                ++this.field_110285_bP;
                if (this.field_110285_bP > 5 && this.field_110285_bP % 3 == 0) {
                    this.playSound("mob.horse.gallop", var5.func_150497_c() * 0.15f, var5.func_150494_d());
                    if (var6 == 0 && this.rand.nextInt(10) == 0) {
                        this.playSound("mob.horse.breathe", var5.func_150497_c() * 0.6f, var5.func_150494_d());
                    }
                }
                else if (this.field_110285_bP <= 5) {
                    this.playSound("mob.horse.wood", var5.func_150497_c() * 0.15f, var5.func_150494_d());
                }
            }
            else if (var5 == Block.soundTypeWood) {
                this.playSound("mob.horse.wood", var5.func_150497_c() * 0.15f, var5.func_150494_d());
            }
            else {
                this.playSound("mob.horse.soft", var5.func_150497_c() * 0.15f, var5.func_150494_d());
            }
        }
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(EntityHorse.horseJumpStrength);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(53.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22499999403953552);
    }
    
    @Override
    public int getMaxSpawnedInChunk() {
        return 6;
    }
    
    public int getMaxTemper() {
        return 100;
    }
    
    @Override
    protected float getSoundVolume() {
        return 0.8f;
    }
    
    @Override
    public int getTalkInterval() {
        return 400;
    }
    
    public boolean func_110239_cn() {
        return this.getHorseType() == 0 || this.func_110241_cb() > 0;
    }
    
    private void func_110230_cF() {
        this.field_110286_bQ = null;
    }
    
    private void setHorseTexturePaths() {
        this.field_110286_bQ = "horse/";
        this.field_110280_bR[0] = null;
        this.field_110280_bR[1] = null;
        this.field_110280_bR[2] = null;
        final int var1 = this.getHorseType();
        final int var2 = this.getHorseVariant();
        if (var1 == 0) {
            final int var3 = var2 & 0xFF;
            final int var4 = (var2 & 0xFF00) >> 8;
            this.field_110280_bR[0] = EntityHorse.horseTextures[var3];
            this.field_110286_bQ += EntityHorse.field_110269_bA[var3];
            this.field_110280_bR[1] = EntityHorse.horseMarkingTextures[var4];
            this.field_110286_bQ += EntityHorse.field_110292_bC[var4];
        }
        else {
            this.field_110280_bR[0] = "";
            this.field_110286_bQ = this.field_110286_bQ + "_" + var1 + "_";
        }
        final int var3 = this.func_110241_cb();
        this.field_110280_bR[2] = EntityHorse.horseArmorTextures[var3];
        this.field_110286_bQ += EntityHorse.field_110273_bx[var3];
    }
    
    public String getHorseTexture() {
        if (this.field_110286_bQ == null) {
            this.setHorseTexturePaths();
        }
        return this.field_110286_bQ;
    }
    
    public String[] getVariantTexturePaths() {
        if (this.field_110286_bQ == null) {
            this.setHorseTexturePaths();
        }
        return this.field_110280_bR;
    }
    
    public void openGUI(final EntityPlayer p_110199_1_) {
        if (!this.worldObj.isClient && (this.riddenByEntity == null || this.riddenByEntity == p_110199_1_) && this.isTame()) {
            this.horseChest.func_110133_a(this.getCommandSenderName());
            p_110199_1_.displayGUIHorse(this, this.horseChest);
        }
    }
    
    @Override
    public boolean interact(final EntityPlayer p_70085_1_) {
        final ItemStack var2 = p_70085_1_.inventory.getCurrentItem();
        if (var2 != null && var2.getItem() == Items.spawn_egg) {
            return super.interact(p_70085_1_);
        }
        if (!this.isTame() && this.func_110256_cu()) {
            return false;
        }
        if (this.isTame() && this.isAdultHorse() && p_70085_1_.isSneaking()) {
            this.openGUI(p_70085_1_);
            return true;
        }
        if (this.func_110253_bW() && this.riddenByEntity != null) {
            return super.interact(p_70085_1_);
        }
        if (var2 != null) {
            boolean var3 = false;
            if (this.func_110259_cr()) {
                byte var4 = -1;
                if (var2.getItem() == Items.iron_horse_armor) {
                    var4 = 1;
                }
                else if (var2.getItem() == Items.golden_horse_armor) {
                    var4 = 2;
                }
                else if (var2.getItem() == Items.diamond_horse_armor) {
                    var4 = 3;
                }
                if (var4 >= 0) {
                    if (!this.isTame()) {
                        this.makeHorseRearWithSound();
                        return true;
                    }
                    this.openGUI(p_70085_1_);
                    return true;
                }
            }
            if (!var3 && !this.func_110256_cu()) {
                float var5 = 0.0f;
                short var6 = 0;
                byte var7 = 0;
                if (var2.getItem() == Items.wheat) {
                    var5 = 2.0f;
                    var6 = 60;
                    var7 = 3;
                }
                else if (var2.getItem() == Items.sugar) {
                    var5 = 1.0f;
                    var6 = 30;
                    var7 = 3;
                }
                else if (var2.getItem() == Items.bread) {
                    var5 = 7.0f;
                    var6 = 180;
                    var7 = 3;
                }
                else if (Block.getBlockFromItem(var2.getItem()) == Blocks.hay_block) {
                    var5 = 20.0f;
                    var6 = 180;
                }
                else if (var2.getItem() == Items.apple) {
                    var5 = 3.0f;
                    var6 = 60;
                    var7 = 3;
                }
                else if (var2.getItem() == Items.golden_carrot) {
                    var5 = 4.0f;
                    var6 = 60;
                    var7 = 5;
                    if (this.isTame() && this.getGrowingAge() == 0) {
                        var3 = true;
                        this.func_146082_f(p_70085_1_);
                    }
                }
                else if (var2.getItem() == Items.golden_apple) {
                    var5 = 10.0f;
                    var6 = 240;
                    var7 = 10;
                    if (this.isTame() && this.getGrowingAge() == 0) {
                        var3 = true;
                        this.func_146082_f(p_70085_1_);
                    }
                }
                if (this.getHealth() < this.getMaxHealth() && var5 > 0.0f) {
                    this.heal(var5);
                    var3 = true;
                }
                if (!this.isAdultHorse() && var6 > 0) {
                    this.addGrowth(var6);
                    var3 = true;
                }
                if (var7 > 0 && (var3 || !this.isTame()) && var7 < this.getMaxTemper()) {
                    var3 = true;
                    this.increaseTemper(var7);
                }
                if (var3) {
                    this.func_110266_cB();
                }
            }
            if (!this.isTame() && !var3) {
                if (var2 != null && var2.interactWithEntity(p_70085_1_, this)) {
                    return true;
                }
                this.makeHorseRearWithSound();
                return true;
            }
            else {
                if (!var3 && this.func_110229_cs() && !this.isChested() && var2.getItem() == Item.getItemFromBlock(Blocks.chest)) {
                    this.setChested(true);
                    this.playSound("mob.chickenplop", 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
                    var3 = true;
                    this.func_110226_cD();
                }
                if (!var3 && this.func_110253_bW() && !this.isHorseSaddled() && var2.getItem() == Items.saddle) {
                    this.openGUI(p_70085_1_);
                    return true;
                }
                if (var3) {
                    if (!p_70085_1_.capabilities.isCreativeMode) {
                        final ItemStack itemStack = var2;
                        if (--itemStack.stackSize == 0) {
                            p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem, null);
                        }
                    }
                    return true;
                }
            }
        }
        if (!this.func_110253_bW() || this.riddenByEntity != null) {
            return super.interact(p_70085_1_);
        }
        if (var2 != null && var2.interactWithEntity(p_70085_1_, this)) {
            return true;
        }
        this.func_110237_h(p_70085_1_);
        return true;
    }
    
    private void func_110237_h(final EntityPlayer p_110237_1_) {
        p_110237_1_.rotationYaw = this.rotationYaw;
        p_110237_1_.rotationPitch = this.rotationPitch;
        this.setEatingHaystack(false);
        this.setRearing(false);
        if (!this.worldObj.isClient) {
            p_110237_1_.mountEntity(this);
        }
    }
    
    public boolean func_110259_cr() {
        return this.getHorseType() == 0;
    }
    
    public boolean func_110229_cs() {
        final int var1 = this.getHorseType();
        return var1 == 2 || var1 == 1;
    }
    
    @Override
    protected boolean isMovementBlocked() {
        return (this.riddenByEntity != null && this.isHorseSaddled()) || (this.isEatingHaystack() || this.isRearing());
    }
    
    public boolean func_110256_cu() {
        final int var1 = this.getHorseType();
        return var1 == 3 || var1 == 4;
    }
    
    public boolean func_110222_cv() {
        return this.func_110256_cu() || this.getHorseType() == 2;
    }
    
    @Override
    public boolean isBreedingItem(final ItemStack p_70877_1_) {
        return false;
    }
    
    private void func_110210_cH() {
        this.field_110278_bp = 1;
    }
    
    @Override
    public void onDeath(final DamageSource p_70645_1_) {
        super.onDeath(p_70645_1_);
        if (!this.worldObj.isClient) {
            this.dropChestItems();
        }
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.rand.nextInt(200) == 0) {
            this.func_110210_cH();
        }
        super.onLivingUpdate();
        if (!this.worldObj.isClient) {
            if (this.rand.nextInt(900) == 0 && this.deathTime == 0) {
                this.heal(1.0f);
            }
            if (!this.isEatingHaystack() && this.riddenByEntity == null && this.rand.nextInt(300) == 0 && this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) - 1, MathHelper.floor_double(this.posZ)) == Blocks.grass) {
                this.setEatingHaystack(true);
            }
            if (this.isEatingHaystack() && ++this.eatingHaystackCounter > 50) {
                this.eatingHaystackCounter = 0;
                this.setEatingHaystack(false);
            }
            if (this.func_110205_ce() && !this.isAdultHorse() && !this.isEatingHaystack()) {
                final EntityHorse var1 = this.getClosestHorse(this, 16.0);
                if (var1 != null && this.getDistanceSqToEntity(var1) > 4.0) {
                    final PathEntity var2 = this.worldObj.getPathEntityToEntity(this, var1, 16.0f, true, false, false, true);
                    this.setPathToEntity(var2);
                }
            }
        }
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.worldObj.isClient && this.dataWatcher.hasChanges()) {
            this.dataWatcher.func_111144_e();
            this.func_110230_cF();
        }
        if (this.openMouthCounter > 0 && ++this.openMouthCounter > 30) {
            this.openMouthCounter = 0;
            this.setHorseWatchableBoolean(128, false);
        }
        if (!this.worldObj.isClient && this.jumpRearingCounter > 0 && ++this.jumpRearingCounter > 20) {
            this.jumpRearingCounter = 0;
            this.setRearing(false);
        }
        if (this.field_110278_bp > 0 && ++this.field_110278_bp > 8) {
            this.field_110278_bp = 0;
        }
        if (this.field_110279_bq > 0) {
            ++this.field_110279_bq;
            if (this.field_110279_bq > 300) {
                this.field_110279_bq = 0;
            }
        }
        this.prevHeadLean = this.headLean;
        if (this.isEatingHaystack()) {
            this.headLean += (1.0f - this.headLean) * 0.4f + 0.05f;
            if (this.headLean > 1.0f) {
                this.headLean = 1.0f;
            }
        }
        else {
            this.headLean += (0.0f - this.headLean) * 0.4f - 0.05f;
            if (this.headLean < 0.0f) {
                this.headLean = 0.0f;
            }
        }
        this.prevRearingAmount = this.rearingAmount;
        if (this.isRearing()) {
            final float n = 0.0f;
            this.headLean = n;
            this.prevHeadLean = n;
            this.rearingAmount += (1.0f - this.rearingAmount) * 0.4f + 0.05f;
            if (this.rearingAmount > 1.0f) {
                this.rearingAmount = 1.0f;
            }
        }
        else {
            this.field_110294_bI = false;
            this.rearingAmount += (0.8f * this.rearingAmount * this.rearingAmount * this.rearingAmount - this.rearingAmount) * 0.6f - 0.05f;
            if (this.rearingAmount < 0.0f) {
                this.rearingAmount = 0.0f;
            }
        }
        this.prevMouthOpenness = this.mouthOpenness;
        if (this.getHorseWatchableBoolean(128)) {
            this.mouthOpenness += (1.0f - this.mouthOpenness) * 0.7f + 0.05f;
            if (this.mouthOpenness > 1.0f) {
                this.mouthOpenness = 1.0f;
            }
        }
        else {
            this.mouthOpenness += (0.0f - this.mouthOpenness) * 0.7f - 0.05f;
            if (this.mouthOpenness < 0.0f) {
                this.mouthOpenness = 0.0f;
            }
        }
    }
    
    private void openHorseMouth() {
        if (!this.worldObj.isClient) {
            this.openMouthCounter = 1;
            this.setHorseWatchableBoolean(128, true);
        }
    }
    
    private boolean func_110200_cJ() {
        return this.riddenByEntity == null && this.ridingEntity == null && this.isTame() && this.isAdultHorse() && !this.func_110222_cv() && this.getHealth() >= this.getMaxHealth();
    }
    
    @Override
    public void setEating(final boolean p_70019_1_) {
        this.setHorseWatchableBoolean(32, p_70019_1_);
    }
    
    public void setEatingHaystack(final boolean p_110227_1_) {
        this.setEating(p_110227_1_);
    }
    
    public void setRearing(final boolean p_110219_1_) {
        if (p_110219_1_) {
            this.setEatingHaystack(false);
        }
        this.setHorseWatchableBoolean(64, p_110219_1_);
    }
    
    private void makeHorseRear() {
        if (!this.worldObj.isClient) {
            this.jumpRearingCounter = 1;
            this.setRearing(true);
        }
    }
    
    public void makeHorseRearWithSound() {
        this.makeHorseRear();
        final String var1 = this.getAngrySoundName();
        if (var1 != null) {
            this.playSound(var1, this.getSoundVolume(), this.getSoundPitch());
        }
    }
    
    public void dropChestItems() {
        this.dropItemsInChest(this, this.horseChest);
        this.dropChests();
    }
    
    private void dropItemsInChest(final Entity p_110240_1_, final AnimalChest p_110240_2_) {
        if (p_110240_2_ != null && !this.worldObj.isClient) {
            for (int var3 = 0; var3 < p_110240_2_.getSizeInventory(); ++var3) {
                final ItemStack var4 = p_110240_2_.getStackInSlot(var3);
                if (var4 != null) {
                    this.entityDropItem(var4, 0.0f);
                }
            }
        }
    }
    
    public boolean setTamedBy(final EntityPlayer p_110263_1_) {
        this.func_152120_b(p_110263_1_.getUniqueID().toString());
        this.setHorseTamed(true);
        return true;
    }
    
    @Override
    public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_) {
        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase && this.isHorseSaddled()) {
            final float rotationYaw = this.riddenByEntity.rotationYaw;
            this.rotationYaw = rotationYaw;
            this.prevRotationYaw = rotationYaw;
            this.rotationPitch = this.riddenByEntity.rotationPitch * 0.5f;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            final float rotationYaw2 = this.rotationYaw;
            this.renderYawOffset = rotationYaw2;
            this.rotationYawHead = rotationYaw2;
            p_70612_1_ = ((EntityLivingBase)this.riddenByEntity).moveStrafing * 0.5f;
            p_70612_2_ = ((EntityLivingBase)this.riddenByEntity).moveForward;
            if (p_70612_2_ <= 0.0f) {
                p_70612_2_ *= 0.25f;
                this.field_110285_bP = 0;
            }
            if (this.onGround && this.jumpPower == 0.0f && this.isRearing() && !this.field_110294_bI) {
                p_70612_1_ = 0.0f;
                p_70612_2_ = 0.0f;
            }
            if (this.jumpPower > 0.0f && !this.isHorseJumping() && this.onGround) {
                this.motionY = this.getHorseJumpStrength() * this.jumpPower;
                if (this.isPotionActive(Potion.jump)) {
                    this.motionY += (this.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1f;
                }
                this.setHorseJumping(true);
                this.isAirBorne = true;
                if (p_70612_2_ > 0.0f) {
                    final float var3 = MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f);
                    final float var4 = MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f);
                    this.motionX += -0.4f * var3 * this.jumpPower;
                    this.motionZ += 0.4f * var4 * this.jumpPower;
                    this.playSound("mob.horse.jump", 0.4f, 1.0f);
                }
                this.jumpPower = 0.0f;
            }
            this.stepHeight = 1.0f;
            this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1f;
            if (!this.worldObj.isClient) {
                this.setAIMoveSpeed((float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
                super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
            }
            if (this.onGround) {
                this.jumpPower = 0.0f;
                this.setHorseJumping(false);
            }
            this.prevLimbSwingAmount = this.limbSwingAmount;
            final double var5 = this.posX - this.prevPosX;
            final double var6 = this.posZ - this.prevPosZ;
            float var7 = MathHelper.sqrt_double(var5 * var5 + var6 * var6) * 4.0f;
            if (var7 > 1.0f) {
                var7 = 1.0f;
            }
            this.limbSwingAmount += (var7 - this.limbSwingAmount) * 0.4f;
            this.limbSwing += this.limbSwingAmount;
        }
        else {
            this.stepHeight = 0.5f;
            this.jumpMovementFactor = 0.02f;
            super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
        p_70014_1_.setBoolean("EatingHaystack", this.isEatingHaystack());
        p_70014_1_.setBoolean("ChestedHorse", this.isChested());
        p_70014_1_.setBoolean("HasReproduced", this.getHasReproduced());
        p_70014_1_.setBoolean("Bred", this.func_110205_ce());
        p_70014_1_.setInteger("Type", this.getHorseType());
        p_70014_1_.setInteger("Variant", this.getHorseVariant());
        p_70014_1_.setInteger("Temper", this.getTemper());
        p_70014_1_.setBoolean("Tame", this.isTame());
        p_70014_1_.setString("OwnerUUID", this.func_152119_ch());
        if (this.isChested()) {
            final NBTTagList var2 = new NBTTagList();
            for (int var3 = 2; var3 < this.horseChest.getSizeInventory(); ++var3) {
                final ItemStack var4 = this.horseChest.getStackInSlot(var3);
                if (var4 != null) {
                    final NBTTagCompound var5 = new NBTTagCompound();
                    var5.setByte("Slot", (byte)var3);
                    var4.writeToNBT(var5);
                    var2.appendTag(var5);
                }
            }
            p_70014_1_.setTag("Items", var2);
        }
        if (this.horseChest.getStackInSlot(1) != null) {
            p_70014_1_.setTag("ArmorItem", this.horseChest.getStackInSlot(1).writeToNBT(new NBTTagCompound()));
        }
        if (this.horseChest.getStackInSlot(0) != null) {
            p_70014_1_.setTag("SaddleItem", this.horseChest.getStackInSlot(0).writeToNBT(new NBTTagCompound()));
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
        this.setEatingHaystack(p_70037_1_.getBoolean("EatingHaystack"));
        this.func_110242_l(p_70037_1_.getBoolean("Bred"));
        this.setChested(p_70037_1_.getBoolean("ChestedHorse"));
        this.setHasReproduced(p_70037_1_.getBoolean("HasReproduced"));
        this.setHorseType(p_70037_1_.getInteger("Type"));
        this.setHorseVariant(p_70037_1_.getInteger("Variant"));
        this.setTemper(p_70037_1_.getInteger("Temper"));
        this.setHorseTamed(p_70037_1_.getBoolean("Tame"));
        if (p_70037_1_.func_150297_b("OwnerUUID", 8)) {
            this.func_152120_b(p_70037_1_.getString("OwnerUUID"));
        }
        final IAttributeInstance var2 = this.getAttributeMap().getAttributeInstanceByName("Speed");
        if (var2 != null) {
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(var2.getBaseValue() * 0.25);
        }
        if (this.isChested()) {
            final NBTTagList var3 = p_70037_1_.getTagList("Items", 10);
            this.func_110226_cD();
            for (int var4 = 0; var4 < var3.tagCount(); ++var4) {
                final NBTTagCompound var5 = var3.getCompoundTagAt(var4);
                final int var6 = var5.getByte("Slot") & 0xFF;
                if (var6 >= 2 && var6 < this.horseChest.getSizeInventory()) {
                    this.horseChest.setInventorySlotContents(var6, ItemStack.loadItemStackFromNBT(var5));
                }
            }
        }
        if (p_70037_1_.func_150297_b("ArmorItem", 10)) {
            final ItemStack var7 = ItemStack.loadItemStackFromNBT(p_70037_1_.getCompoundTag("ArmorItem"));
            if (var7 != null && func_146085_a(var7.getItem())) {
                this.horseChest.setInventorySlotContents(1, var7);
            }
        }
        if (p_70037_1_.func_150297_b("SaddleItem", 10)) {
            final ItemStack var7 = ItemStack.loadItemStackFromNBT(p_70037_1_.getCompoundTag("SaddleItem"));
            if (var7 != null && var7.getItem() == Items.saddle) {
                this.horseChest.setInventorySlotContents(0, var7);
            }
        }
        else if (p_70037_1_.getBoolean("Saddle")) {
            this.horseChest.setInventorySlotContents(0, new ItemStack(Items.saddle));
        }
        this.func_110232_cE();
    }
    
    @Override
    public boolean canMateWith(final EntityAnimal p_70878_1_) {
        if (p_70878_1_ == this) {
            return false;
        }
        if (p_70878_1_.getClass() != this.getClass()) {
            return false;
        }
        final EntityHorse var2 = (EntityHorse)p_70878_1_;
        if (this.func_110200_cJ() && var2.func_110200_cJ()) {
            final int var3 = this.getHorseType();
            final int var4 = var2.getHorseType();
            return var3 == var4 || (var3 == 0 && var4 == 1) || (var3 == 1 && var4 == 0);
        }
        return false;
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable p_90011_1_) {
        final EntityHorse var2 = (EntityHorse)p_90011_1_;
        final EntityHorse var3 = new EntityHorse(this.worldObj);
        final int var4 = this.getHorseType();
        final int var5 = var2.getHorseType();
        int var6 = 0;
        if (var4 == var5) {
            var6 = var4;
        }
        else if ((var4 == 0 && var5 == 1) || (var4 == 1 && var5 == 0)) {
            var6 = 2;
        }
        if (var6 == 0) {
            final int var7 = this.rand.nextInt(9);
            int var8;
            if (var7 < 4) {
                var8 = (this.getHorseVariant() & 0xFF);
            }
            else if (var7 < 8) {
                var8 = (var2.getHorseVariant() & 0xFF);
            }
            else {
                var8 = this.rand.nextInt(7);
            }
            final int var9 = this.rand.nextInt(5);
            if (var9 < 2) {
                var8 |= (this.getHorseVariant() & 0xFF00);
            }
            else if (var9 < 4) {
                var8 |= (var2.getHorseVariant() & 0xFF00);
            }
            else {
                var8 |= (this.rand.nextInt(5) << 8 & 0xFF00);
            }
            var3.setHorseVariant(var8);
        }
        var3.setHorseType(var6);
        final double var10 = this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() + p_90011_1_.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() + this.func_110267_cL();
        var3.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(var10 / 3.0);
        final double var11 = this.getEntityAttribute(EntityHorse.horseJumpStrength).getBaseValue() + p_90011_1_.getEntityAttribute(EntityHorse.horseJumpStrength).getBaseValue() + this.func_110245_cM();
        var3.getEntityAttribute(EntityHorse.horseJumpStrength).setBaseValue(var11 / 3.0);
        final double var12 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() + p_90011_1_.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() + this.func_110203_cN();
        var3.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(var12 / 3.0);
        return var3;
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(final IEntityLivingData p_110161_1_) {
        Object p_110161_1_2 = super.onSpawnWithEgg(p_110161_1_);
        final boolean var2 = false;
        int var3 = 0;
        int var4;
        if (p_110161_1_2 instanceof GroupData) {
            var4 = ((GroupData)p_110161_1_2).field_111107_a;
            var3 = ((((GroupData)p_110161_1_2).field_111106_b & 0xFF) | this.rand.nextInt(5) << 8);
        }
        else {
            if (this.rand.nextInt(10) == 0) {
                var4 = 1;
            }
            else {
                final int var5 = this.rand.nextInt(7);
                final int var6 = this.rand.nextInt(5);
                var4 = 0;
                var3 = (var5 | var6 << 8);
            }
            p_110161_1_2 = new GroupData(var4, var3);
        }
        this.setHorseType(var4);
        this.setHorseVariant(var3);
        if (this.rand.nextInt(5) == 0) {
            this.setGrowingAge(-24000);
        }
        if (var4 != 4 && var4 != 3) {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.func_110267_cL());
            if (var4 == 0) {
                this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(this.func_110203_cN());
            }
            else {
                this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.17499999701976776);
            }
        }
        else {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0);
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224);
        }
        if (var4 != 2 && var4 != 1) {
            this.getEntityAttribute(EntityHorse.horseJumpStrength).setBaseValue(this.func_110245_cM());
        }
        else {
            this.getEntityAttribute(EntityHorse.horseJumpStrength).setBaseValue(0.5);
        }
        this.setHealth(this.getMaxHealth());
        return (IEntityLivingData)p_110161_1_2;
    }
    
    public float getGrassEatingAmount(final float p_110258_1_) {
        return this.prevHeadLean + (this.headLean - this.prevHeadLean) * p_110258_1_;
    }
    
    public float getRearingAmount(final float p_110223_1_) {
        return this.prevRearingAmount + (this.rearingAmount - this.prevRearingAmount) * p_110223_1_;
    }
    
    public float func_110201_q(final float p_110201_1_) {
        return this.prevMouthOpenness + (this.mouthOpenness - this.prevMouthOpenness) * p_110201_1_;
    }
    
    @Override
    protected boolean isAIEnabled() {
        return true;
    }
    
    public void setJumpPower(int p_110206_1_) {
        if (this.isHorseSaddled()) {
            if (p_110206_1_ < 0) {
                p_110206_1_ = 0;
            }
            else {
                this.field_110294_bI = true;
                this.makeHorseRear();
            }
            if (p_110206_1_ >= 90) {
                this.jumpPower = 1.0f;
            }
            else {
                this.jumpPower = 0.4f + 0.4f * p_110206_1_ / 90.0f;
            }
        }
    }
    
    protected void spawnHorseParticles(final boolean p_110216_1_) {
        final String var2 = p_110216_1_ ? "heart" : "smoke";
        for (int var3 = 0; var3 < 7; ++var3) {
            final double var4 = this.rand.nextGaussian() * 0.02;
            final double var5 = this.rand.nextGaussian() * 0.02;
            final double var6 = this.rand.nextGaussian() * 0.02;
            this.worldObj.spawnParticle(var2, this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 0.5 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, var4, var5, var6);
        }
    }
    
    @Override
    public void handleHealthUpdate(final byte p_70103_1_) {
        if (p_70103_1_ == 7) {
            this.spawnHorseParticles(true);
        }
        else if (p_70103_1_ == 6) {
            this.spawnHorseParticles(false);
        }
        else {
            super.handleHealthUpdate(p_70103_1_);
        }
    }
    
    @Override
    public void updateRiderPosition() {
        super.updateRiderPosition();
        if (this.prevRearingAmount > 0.0f) {
            final float var1 = MathHelper.sin(this.renderYawOffset * 3.1415927f / 180.0f);
            final float var2 = MathHelper.cos(this.renderYawOffset * 3.1415927f / 180.0f);
            final float var3 = 0.7f * this.prevRearingAmount;
            final float var4 = 0.15f * this.prevRearingAmount;
            this.riddenByEntity.setPosition(this.posX + var3 * var1, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset() + var4, this.posZ - var3 * var2);
            if (this.riddenByEntity instanceof EntityLivingBase) {
                ((EntityLivingBase)this.riddenByEntity).renderYawOffset = this.renderYawOffset;
            }
        }
    }
    
    private float func_110267_cL() {
        return 15.0f + this.rand.nextInt(8) + this.rand.nextInt(9);
    }
    
    private double func_110245_cM() {
        return 0.4000000059604645 + this.rand.nextDouble() * 0.2 + this.rand.nextDouble() * 0.2 + this.rand.nextDouble() * 0.2;
    }
    
    private double func_110203_cN() {
        return (0.44999998807907104 + this.rand.nextDouble() * 0.3 + this.rand.nextDouble() * 0.3 + this.rand.nextDouble() * 0.3) * 0.25;
    }
    
    public static boolean func_146085_a(final Item p_146085_0_) {
        return p_146085_0_ == Items.iron_horse_armor || p_146085_0_ == Items.golden_horse_armor || p_146085_0_ == Items.diamond_horse_armor;
    }
    
    @Override
    public boolean isOnLadder() {
        return false;
    }
    
    static {
        horseBreedingSelector = new IEntitySelector() {
            private static final String __OBFID = "CL_00001642";
            
            @Override
            public boolean isEntityApplicable(final Entity p_82704_1_) {
                return p_82704_1_ instanceof EntityHorse && ((EntityHorse)p_82704_1_).func_110205_ce();
            }
        };
        horseJumpStrength = new RangedAttribute("horse.jumpStrength", 0.7, 0.0, 2.0).setDescription("Jump Strength").setShouldWatch(true);
        horseArmorTextures = new String[] { null, "textures/entity/horse/armor/horse_armor_iron.png", "textures/entity/horse/armor/horse_armor_gold.png", "textures/entity/horse/armor/horse_armor_diamond.png" };
        field_110273_bx = new String[] { "", "meo", "goo", "dio" };
        armorValues = new int[] { 0, 5, 7, 11 };
        horseTextures = new String[] { "textures/entity/horse/horse_white.png", "textures/entity/horse/horse_creamy.png", "textures/entity/horse/horse_chestnut.png", "textures/entity/horse/horse_brown.png", "textures/entity/horse/horse_black.png", "textures/entity/horse/horse_gray.png", "textures/entity/horse/horse_darkbrown.png" };
        field_110269_bA = new String[] { "hwh", "hcr", "hch", "hbr", "hbl", "hgr", "hdb" };
        horseMarkingTextures = new String[] { null, "textures/entity/horse/horse_markings_white.png", "textures/entity/horse/horse_markings_whitefield.png", "textures/entity/horse/horse_markings_whitedots.png", "textures/entity/horse/horse_markings_blackdots.png" };
        field_110292_bC = new String[] { "", "wo_", "wmo", "wdo", "bdo" };
    }
    
    public static class GroupData implements IEntityLivingData
    {
        public int field_111107_a;
        public int field_111106_b;
        private static final String __OBFID = "CL_00001643";
        
        public GroupData(final int p_i1684_1_, final int p_i1684_2_) {
            this.field_111107_a = p_i1684_1_;
            this.field_111106_b = p_i1684_2_;
        }
    }
}
