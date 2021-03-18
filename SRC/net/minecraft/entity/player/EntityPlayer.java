package net.minecraft.entity.player;

import net.minecraft.command.*;
import com.mojang.authlib.*;
import net.minecraft.item.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.enchantment.*;
import net.minecraft.potion.*;
import net.minecraft.block.material.*;
import net.minecraft.nbt.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.command.server.*;
import net.minecraft.tileentity.*;
import net.minecraft.stats.*;
import net.minecraft.entity.boss.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.world.chunk.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.scoreboard.*;
import net.minecraft.util.*;
import net.minecraft.event.*;
import java.util.*;
import com.google.common.base.*;

public abstract class EntityPlayer extends EntityLivingBase implements ICommandSender
{
    public InventoryPlayer inventory;
    private InventoryEnderChest theInventoryEnderChest;
    public Container inventoryContainer;
    public Container openContainer;
    protected FoodStats foodStats;
    protected int flyToggleTimer;
    public float prevCameraYaw;
    public float cameraYaw;
    public int xpCooldown;
    public double field_71091_bM;
    public double field_71096_bN;
    public double field_71097_bO;
    public double field_71094_bP;
    public double field_71095_bQ;
    public double field_71085_bR;
    protected boolean sleeping;
    public ChunkCoordinates playerLocation;
    private int sleepTimer;
    public float field_71079_bU;
    public float field_71082_cx;
    public float field_71089_bV;
    private ChunkCoordinates spawnChunk;
    private boolean spawnForced;
    private ChunkCoordinates startMinecartRidingCoordinate;
    public PlayerCapabilities capabilities;
    public int experienceLevel;
    public int experienceTotal;
    public float experience;
    private ItemStack itemInUse;
    private int itemInUseCount;
    protected float speedOnGround;
    protected float speedInAir;
    private int field_82249_h;
    private final GameProfile field_146106_i;
    public EntityFishHook fishEntity;
    private static final String __OBFID = "CL_00001711";
    
    public EntityPlayer(final World p_i45324_1_, final GameProfile p_i45324_2_) {
        super(p_i45324_1_);
        this.inventory = new InventoryPlayer(this);
        this.theInventoryEnderChest = new InventoryEnderChest();
        this.foodStats = new FoodStats();
        this.capabilities = new PlayerCapabilities();
        this.speedOnGround = 0.1f;
        this.speedInAir = 0.02f;
        this.entityUniqueID = func_146094_a(p_i45324_2_);
        this.field_146106_i = p_i45324_2_;
        this.inventoryContainer = new ContainerPlayer(this.inventory, !p_i45324_1_.isClient, this);
        this.openContainer = this.inventoryContainer;
        this.yOffset = 1.62f;
        final ChunkCoordinates var3 = p_i45324_1_.getSpawnPoint();
        this.setLocationAndAngles(var3.posX + 0.5, var3.posY + 1, var3.posZ + 0.5, 0.0f, 0.0f);
        this.field_70741_aB = 180.0f;
        this.fireResistance = 20;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 0);
        this.dataWatcher.addObject(17, 0.0f);
        this.dataWatcher.addObject(18, 0);
    }
    
    public ItemStack getItemInUse() {
        return this.itemInUse;
    }
    
    public int getItemInUseCount() {
        return this.itemInUseCount;
    }
    
    public boolean isUsingItem() {
        return this.itemInUse != null;
    }
    
    public int getItemInUseDuration() {
        return this.isUsingItem() ? (this.itemInUse.getMaxItemUseDuration() - this.itemInUseCount) : 0;
    }
    
    public void stopUsingItem() {
        if (this.itemInUse != null) {
            this.itemInUse.onPlayerStoppedUsing(this.worldObj, this, this.itemInUseCount);
        }
        this.clearItemInUse();
    }
    
    public void clearItemInUse() {
        this.itemInUse = null;
        this.itemInUseCount = 0;
        if (!this.worldObj.isClient) {
            this.setEating(false);
        }
    }
    
    public boolean isBlocking() {
        return this.isUsingItem() && this.itemInUse.getItem().getItemUseAction(this.itemInUse) == EnumAction.block;
    }
    
    @Override
    public void onUpdate() {
        if (this.itemInUse != null) {
            final ItemStack var1 = this.inventory.getCurrentItem();
            if (var1 == this.itemInUse) {
                if (this.itemInUseCount <= 25 && this.itemInUseCount % 4 == 0) {
                    this.updateItemUse(var1, 5);
                }
                if (--this.itemInUseCount == 0 && !this.worldObj.isClient) {
                    this.onItemUseFinish();
                }
            }
            else {
                this.clearItemInUse();
            }
        }
        if (this.xpCooldown > 0) {
            --this.xpCooldown;
        }
        if (this.isPlayerSleeping()) {
            ++this.sleepTimer;
            if (this.sleepTimer > 100) {
                this.sleepTimer = 100;
            }
            if (!this.worldObj.isClient) {
                if (!this.isInBed()) {
                    this.wakeUpPlayer(true, true, false);
                }
                else if (this.worldObj.isDaytime()) {
                    this.wakeUpPlayer(false, true, true);
                }
            }
        }
        else if (this.sleepTimer > 0) {
            ++this.sleepTimer;
            if (this.sleepTimer >= 110) {
                this.sleepTimer = 0;
            }
        }
        super.onUpdate();
        if (!this.worldObj.isClient && this.openContainer != null && !this.openContainer.canInteractWith(this)) {
            this.closeScreen();
            this.openContainer = this.inventoryContainer;
        }
        if (this.isBurning() && this.capabilities.disableDamage) {
            this.extinguish();
        }
        this.field_71091_bM = this.field_71094_bP;
        this.field_71096_bN = this.field_71095_bQ;
        this.field_71097_bO = this.field_71085_bR;
        final double var2 = this.posX - this.field_71094_bP;
        final double var3 = this.posY - this.field_71095_bQ;
        final double var4 = this.posZ - this.field_71085_bR;
        final double var5 = 10.0;
        if (var2 > var5) {
            final double posX = this.posX;
            this.field_71094_bP = posX;
            this.field_71091_bM = posX;
        }
        if (var4 > var5) {
            final double posZ = this.posZ;
            this.field_71085_bR = posZ;
            this.field_71097_bO = posZ;
        }
        if (var3 > var5) {
            final double posY = this.posY;
            this.field_71095_bQ = posY;
            this.field_71096_bN = posY;
        }
        if (var2 < -var5) {
            final double posX2 = this.posX;
            this.field_71094_bP = posX2;
            this.field_71091_bM = posX2;
        }
        if (var4 < -var5) {
            final double posZ2 = this.posZ;
            this.field_71085_bR = posZ2;
            this.field_71097_bO = posZ2;
        }
        if (var3 < -var5) {
            final double posY2 = this.posY;
            this.field_71095_bQ = posY2;
            this.field_71096_bN = posY2;
        }
        this.field_71094_bP += var2 * 0.25;
        this.field_71085_bR += var4 * 0.25;
        this.field_71095_bQ += var3 * 0.25;
        if (this.ridingEntity == null) {
            this.startMinecartRidingCoordinate = null;
        }
        if (!this.worldObj.isClient) {
            this.foodStats.onUpdate(this);
            this.addStat(StatList.minutesPlayedStat, 1);
        }
    }
    
    @Override
    public int getMaxInPortalTime() {
        return this.capabilities.disableDamage ? 0 : 80;
    }
    
    @Override
    protected String getSwimSound() {
        return "game.player.swim";
    }
    
    @Override
    protected String getSplashSound() {
        return "game.player.swim.splash";
    }
    
    @Override
    public int getPortalCooldown() {
        return 10;
    }
    
    @Override
    public void playSound(final String p_85030_1_, final float p_85030_2_, final float p_85030_3_) {
        this.worldObj.playSoundToNearExcept(this, p_85030_1_, p_85030_2_, p_85030_3_);
    }
    
    protected void updateItemUse(final ItemStack p_71010_1_, final int p_71010_2_) {
        if (p_71010_1_.getItemUseAction() == EnumAction.drink) {
            this.playSound("random.drink", 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
        }
        if (p_71010_1_.getItemUseAction() == EnumAction.eat) {
            for (int var3 = 0; var3 < p_71010_2_; ++var3) {
                final Vec3 var4 = Vec3.createVectorHelper((this.rand.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
                var4.rotateAroundX(-this.rotationPitch * 3.1415927f / 180.0f);
                var4.rotateAroundY(-this.rotationYaw * 3.1415927f / 180.0f);
                Vec3 var5 = Vec3.createVectorHelper((this.rand.nextFloat() - 0.5) * 0.3, -this.rand.nextFloat() * 0.6 - 0.3, 0.6);
                var5.rotateAroundX(-this.rotationPitch * 3.1415927f / 180.0f);
                var5.rotateAroundY(-this.rotationYaw * 3.1415927f / 180.0f);
                var5 = var5.addVector(this.posX, this.posY + this.getEyeHeight(), this.posZ);
                String var6 = "iconcrack_" + Item.getIdFromItem(p_71010_1_.getItem());
                if (p_71010_1_.getHasSubtypes()) {
                    var6 = var6 + "_" + p_71010_1_.getItemDamage();
                }
                this.worldObj.spawnParticle(var6, var5.xCoord, var5.yCoord, var5.zCoord, var4.xCoord, var4.yCoord + 0.05, var4.zCoord);
            }
            this.playSound("random.eat", 0.5f + 0.5f * this.rand.nextInt(2), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
        }
    }
    
    protected void onItemUseFinish() {
        if (this.itemInUse != null) {
            this.updateItemUse(this.itemInUse, 16);
            final int var1 = this.itemInUse.stackSize;
            final ItemStack var2 = this.itemInUse.onFoodEaten(this.worldObj, this);
            if (var2 != this.itemInUse || (var2 != null && var2.stackSize != var1)) {
                this.inventory.mainInventory[this.inventory.currentItem] = var2;
                if (var2.stackSize == 0) {
                    this.inventory.mainInventory[this.inventory.currentItem] = null;
                }
            }
            this.clearItemInUse();
        }
    }
    
    @Override
    public void handleHealthUpdate(final byte p_70103_1_) {
        if (p_70103_1_ == 9) {
            this.onItemUseFinish();
        }
        else {
            super.handleHealthUpdate(p_70103_1_);
        }
    }
    
    @Override
    protected boolean isMovementBlocked() {
        return this.getHealth() <= 0.0f || this.isPlayerSleeping();
    }
    
    protected void closeScreen() {
        this.openContainer = this.inventoryContainer;
    }
    
    @Override
    public void mountEntity(final Entity p_70078_1_) {
        if (this.ridingEntity != null && p_70078_1_ == null) {
            if (!this.worldObj.isClient) {
                this.dismountEntity(this.ridingEntity);
            }
            if (this.ridingEntity != null) {
                this.ridingEntity.riddenByEntity = null;
            }
            this.ridingEntity = null;
        }
        else {
            super.mountEntity(p_70078_1_);
        }
    }
    
    @Override
    public void updateRidden() {
        if (!this.worldObj.isClient && this.isSneaking()) {
            this.mountEntity(null);
            this.setSneaking(false);
        }
        else {
            final double var1 = this.posX;
            final double var2 = this.posY;
            final double var3 = this.posZ;
            final float var4 = this.rotationYaw;
            final float var5 = this.rotationPitch;
            super.updateRidden();
            this.prevCameraYaw = this.cameraYaw;
            this.cameraYaw = 0.0f;
            this.addMountedMovementStat(this.posX - var1, this.posY - var2, this.posZ - var3);
            if (this.ridingEntity instanceof EntityPig) {
                this.rotationPitch = var5;
                this.rotationYaw = var4;
                this.renderYawOffset = ((EntityPig)this.ridingEntity).renderYawOffset;
            }
        }
    }
    
    public void preparePlayerToSpawn() {
        this.yOffset = 1.62f;
        this.setSize(0.6f, 1.8f);
        super.preparePlayerToSpawn();
        this.setHealth(this.getMaxHealth());
        this.deathTime = 0;
    }
    
    @Override
    protected void updateEntityActionState() {
        super.updateEntityActionState();
        this.updateArmSwingProgress();
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.flyToggleTimer > 0) {
            --this.flyToggleTimer;
        }
        if (this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL && this.getHealth() < this.getMaxHealth() && this.worldObj.getGameRules().getGameRuleBooleanValue("naturalRegeneration") && this.ticksExisted % 20 * 12 == 0) {
            this.heal(1.0f);
        }
        this.inventory.decrementAnimations();
        this.prevCameraYaw = this.cameraYaw;
        super.onLivingUpdate();
        final IAttributeInstance var1 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        if (!this.worldObj.isClient) {
            var1.setBaseValue(this.capabilities.getWalkSpeed());
        }
        this.jumpMovementFactor = this.speedInAir;
        if (this.isSprinting()) {
            this.jumpMovementFactor += (float)(this.speedInAir * 0.3);
        }
        this.setAIMoveSpeed((float)var1.getAttributeValue());
        float var2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        float var3 = (float)Math.atan(-this.motionY * 0.20000000298023224) * 15.0f;
        if (var2 > 0.1f) {
            var2 = 0.1f;
        }
        if (!this.onGround || this.getHealth() <= 0.0f) {
            var2 = 0.0f;
        }
        if (this.onGround || this.getHealth() <= 0.0f) {
            var3 = 0.0f;
        }
        this.cameraYaw += (var2 - this.cameraYaw) * 0.4f;
        this.cameraPitch += (var3 - this.cameraPitch) * 0.8f;
        if (this.getHealth() > 0.0f) {
            AxisAlignedBB var4 = null;
            if (this.ridingEntity != null && !this.ridingEntity.isDead) {
                var4 = this.boundingBox.func_111270_a(this.ridingEntity.boundingBox).expand(1.0, 0.0, 1.0);
            }
            else {
                var4 = this.boundingBox.expand(1.0, 0.5, 1.0);
            }
            final List var5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, var4);
            if (var5 != null) {
                for (int var6 = 0; var6 < var5.size(); ++var6) {
                    final Entity var7 = var5.get(var6);
                    if (!var7.isDead) {
                        this.collideWithPlayer(var7);
                    }
                }
            }
        }
    }
    
    private void collideWithPlayer(final Entity p_71044_1_) {
        p_71044_1_.onCollideWithPlayer(this);
    }
    
    public int getScore() {
        return this.dataWatcher.getWatchableObjectInt(18);
    }
    
    public void setScore(final int p_85040_1_) {
        this.dataWatcher.updateObject(18, p_85040_1_);
    }
    
    public void addScore(final int p_85039_1_) {
        final int var2 = this.getScore();
        this.dataWatcher.updateObject(18, var2 + p_85039_1_);
    }
    
    @Override
    public void onDeath(final DamageSource p_70645_1_) {
        super.onDeath(p_70645_1_);
        this.setSize(0.2f, 0.2f);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionY = 0.10000000149011612;
        if (this.getCommandSenderName().equals("Notch")) {
            this.func_146097_a(new ItemStack(Items.apple, 1), true, false);
        }
        if (!this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
            this.inventory.dropAllItems();
        }
        if (p_70645_1_ != null) {
            this.motionX = -MathHelper.cos((this.attackedAtYaw + this.rotationYaw) * 3.1415927f / 180.0f) * 0.1f;
            this.motionZ = -MathHelper.sin((this.attackedAtYaw + this.rotationYaw) * 3.1415927f / 180.0f) * 0.1f;
        }
        else {
            final double n = 0.0;
            this.motionZ = n;
            this.motionX = n;
        }
        this.yOffset = 0.1f;
        this.addStat(StatList.deathsStat, 1);
    }
    
    @Override
    protected String getHurtSound() {
        return "game.player.hurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "game.player.die";
    }
    
    @Override
    public void addToPlayerScore(final Entity p_70084_1_, final int p_70084_2_) {
        this.addScore(p_70084_2_);
        final Collection var3 = this.getWorldScoreboard().func_96520_a(IScoreObjectiveCriteria.totalKillCount);
        if (p_70084_1_ instanceof EntityPlayer) {
            this.addStat(StatList.playerKillsStat, 1);
            var3.addAll(this.getWorldScoreboard().func_96520_a(IScoreObjectiveCriteria.playerKillCount));
        }
        else {
            this.addStat(StatList.mobKillsStat, 1);
        }
        for (final ScoreObjective var5 : var3) {
            final Score var6 = this.getWorldScoreboard().func_96529_a(this.getCommandSenderName(), var5);
            var6.func_96648_a();
        }
    }
    
    public EntityItem dropOneItem(final boolean p_71040_1_) {
        return this.func_146097_a(this.inventory.decrStackSize(this.inventory.currentItem, (p_71040_1_ && this.inventory.getCurrentItem() != null) ? this.inventory.getCurrentItem().stackSize : 1), false, true);
    }
    
    public EntityItem dropPlayerItemWithRandomChoice(final ItemStack p_71019_1_, final boolean p_71019_2_) {
        return this.func_146097_a(p_71019_1_, false, false);
    }
    
    public EntityItem func_146097_a(final ItemStack p_146097_1_, final boolean p_146097_2_, final boolean p_146097_3_) {
        if (p_146097_1_ == null) {
            return null;
        }
        if (p_146097_1_.stackSize == 0) {
            return null;
        }
        final EntityItem var4 = new EntityItem(this.worldObj, this.posX, this.posY - 0.30000001192092896 + this.getEyeHeight(), this.posZ, p_146097_1_);
        var4.delayBeforeCanPickup = 40;
        if (p_146097_3_) {
            var4.func_145799_b(this.getCommandSenderName());
        }
        float var5 = 0.1f;
        if (p_146097_2_) {
            final float var6 = this.rand.nextFloat() * 0.5f;
            final float var7 = this.rand.nextFloat() * 3.1415927f * 2.0f;
            var4.motionX = -MathHelper.sin(var7) * var6;
            var4.motionZ = MathHelper.cos(var7) * var6;
            var4.motionY = 0.20000000298023224;
        }
        else {
            var5 = 0.3f;
            var4.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * var5;
            var4.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * var5;
            var4.motionY = -MathHelper.sin(this.rotationPitch / 180.0f * 3.1415927f) * var5 + 0.1f;
            var5 = 0.02f;
            final float var6 = this.rand.nextFloat() * 3.1415927f * 2.0f;
            var5 *= this.rand.nextFloat();
            final EntityItem entityItem = var4;
            entityItem.motionX += Math.cos(var6) * var5;
            final EntityItem entityItem2 = var4;
            entityItem2.motionY += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f;
            final EntityItem entityItem3 = var4;
            entityItem3.motionZ += Math.sin(var6) * var5;
        }
        this.joinEntityItemWithWorld(var4);
        this.addStat(StatList.dropStat, 1);
        return var4;
    }
    
    protected void joinEntityItemWithWorld(final EntityItem p_71012_1_) {
        this.worldObj.spawnEntityInWorld(p_71012_1_);
    }
    
    public float getCurrentPlayerStrVsBlock(final Block p_146096_1_, final boolean p_146096_2_) {
        float var3 = this.inventory.func_146023_a(p_146096_1_);
        if (var3 > 1.0f) {
            final int var4 = EnchantmentHelper.getEfficiencyModifier(this);
            final ItemStack var5 = this.inventory.getCurrentItem();
            if (var4 > 0 && var5 != null) {
                final float var6 = (float)(var4 * var4 + 1);
                if (!var5.func_150998_b(p_146096_1_) && var3 <= 1.0f) {
                    var3 += var6 * 0.08f;
                }
                else {
                    var3 += var6;
                }
            }
        }
        if (this.isPotionActive(Potion.digSpeed)) {
            var3 *= 1.0f + (this.getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2f;
        }
        if (this.isPotionActive(Potion.digSlowdown)) {
            var3 *= 1.0f - (this.getActivePotionEffect(Potion.digSlowdown).getAmplifier() + 1) * 0.2f;
        }
        if (this.isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(this)) {
            var3 /= 5.0f;
        }
        if (!this.onGround) {
            var3 /= 5.0f;
        }
        return var3;
    }
    
    public boolean canHarvestBlock(final Block p_146099_1_) {
        return this.inventory.func_146025_b(p_146099_1_);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
        this.entityUniqueID = func_146094_a(this.field_146106_i);
        final NBTTagList var2 = p_70037_1_.getTagList("Inventory", 10);
        this.inventory.readFromNBT(var2);
        this.inventory.currentItem = p_70037_1_.getInteger("SelectedItemSlot");
        this.sleeping = p_70037_1_.getBoolean("Sleeping");
        this.sleepTimer = p_70037_1_.getShort("SleepTimer");
        this.experience = p_70037_1_.getFloat("XpP");
        this.experienceLevel = p_70037_1_.getInteger("XpLevel");
        this.experienceTotal = p_70037_1_.getInteger("XpTotal");
        this.setScore(p_70037_1_.getInteger("Score"));
        if (this.sleeping) {
            this.playerLocation = new ChunkCoordinates(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
            this.wakeUpPlayer(true, true, false);
        }
        if (p_70037_1_.func_150297_b("SpawnX", 99) && p_70037_1_.func_150297_b("SpawnY", 99) && p_70037_1_.func_150297_b("SpawnZ", 99)) {
            this.spawnChunk = new ChunkCoordinates(p_70037_1_.getInteger("SpawnX"), p_70037_1_.getInteger("SpawnY"), p_70037_1_.getInteger("SpawnZ"));
            this.spawnForced = p_70037_1_.getBoolean("SpawnForced");
        }
        this.foodStats.readNBT(p_70037_1_);
        this.capabilities.readCapabilitiesFromNBT(p_70037_1_);
        if (p_70037_1_.func_150297_b("EnderItems", 9)) {
            final NBTTagList var3 = p_70037_1_.getTagList("EnderItems", 10);
            this.theInventoryEnderChest.loadInventoryFromNBT(var3);
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
        p_70014_1_.setTag("Inventory", this.inventory.writeToNBT(new NBTTagList()));
        p_70014_1_.setInteger("SelectedItemSlot", this.inventory.currentItem);
        p_70014_1_.setBoolean("Sleeping", this.sleeping);
        p_70014_1_.setShort("SleepTimer", (short)this.sleepTimer);
        p_70014_1_.setFloat("XpP", this.experience);
        p_70014_1_.setInteger("XpLevel", this.experienceLevel);
        p_70014_1_.setInteger("XpTotal", this.experienceTotal);
        p_70014_1_.setInteger("Score", this.getScore());
        if (this.spawnChunk != null) {
            p_70014_1_.setInteger("SpawnX", this.spawnChunk.posX);
            p_70014_1_.setInteger("SpawnY", this.spawnChunk.posY);
            p_70014_1_.setInteger("SpawnZ", this.spawnChunk.posZ);
            p_70014_1_.setBoolean("SpawnForced", this.spawnForced);
        }
        this.foodStats.writeNBT(p_70014_1_);
        this.capabilities.writeCapabilitiesToNBT(p_70014_1_);
        p_70014_1_.setTag("EnderItems", this.theInventoryEnderChest.saveInventoryToNBT());
    }
    
    public void displayGUIChest(final IInventory p_71007_1_) {
    }
    
    public void func_146093_a(final TileEntityHopper p_146093_1_) {
    }
    
    public void displayGUIHopperMinecart(final EntityMinecartHopper p_96125_1_) {
    }
    
    public void displayGUIHorse(final EntityHorse p_110298_1_, final IInventory p_110298_2_) {
    }
    
    public void displayGUIEnchantment(final int p_71002_1_, final int p_71002_2_, final int p_71002_3_, final String p_71002_4_) {
    }
    
    public void displayGUIAnvil(final int p_82244_1_, final int p_82244_2_, final int p_82244_3_) {
    }
    
    public void displayGUIWorkbench(final int p_71058_1_, final int p_71058_2_, final int p_71058_3_) {
    }
    
    @Override
    public float getEyeHeight() {
        return 0.12f;
    }
    
    protected void resetHeight() {
        this.yOffset = 1.62f;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource p_70097_1_, float p_70097_2_) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (this.capabilities.disableDamage && !p_70097_1_.canHarmInCreative()) {
            return false;
        }
        this.entityAge = 0;
        if (this.getHealth() <= 0.0f) {
            return false;
        }
        if (this.isPlayerSleeping() && !this.worldObj.isClient) {
            this.wakeUpPlayer(true, true, false);
        }
        if (p_70097_1_.isDifficultyScaled()) {
            if (this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL) {
                p_70097_2_ = 0.0f;
            }
            if (this.worldObj.difficultySetting == EnumDifficulty.EASY) {
                p_70097_2_ = p_70097_2_ / 2.0f + 1.0f;
            }
            if (this.worldObj.difficultySetting == EnumDifficulty.HARD) {
                p_70097_2_ = p_70097_2_ * 3.0f / 2.0f;
            }
        }
        if (p_70097_2_ == 0.0f) {
            return false;
        }
        Entity var3 = p_70097_1_.getEntity();
        if (var3 instanceof EntityArrow && ((EntityArrow)var3).shootingEntity != null) {
            var3 = ((EntityArrow)var3).shootingEntity;
        }
        this.addStat(StatList.damageTakenStat, Math.round(p_70097_2_ * 10.0f));
        return super.attackEntityFrom(p_70097_1_, p_70097_2_);
    }
    
    public boolean canAttackPlayer(final EntityPlayer p_96122_1_) {
        final Team var2 = this.getTeam();
        final Team var3 = p_96122_1_.getTeam();
        return var2 == null || !var2.isSameTeam(var3) || var2.getAllowFriendlyFire();
    }
    
    @Override
    protected void damageArmor(final float p_70675_1_) {
        this.inventory.damageArmor(p_70675_1_);
    }
    
    @Override
    public int getTotalArmorValue() {
        return this.inventory.getTotalArmorValue();
    }
    
    public float getArmorVisibility() {
        int var1 = 0;
        for (final ItemStack var5 : this.inventory.armorInventory) {
            if (var5 != null) {
                ++var1;
            }
        }
        return var1 / (float)this.inventory.armorInventory.length;
    }
    
    @Override
    protected void damageEntity(final DamageSource p_70665_1_, float p_70665_2_) {
        if (!this.isEntityInvulnerable()) {
            if (!p_70665_1_.isUnblockable() && this.isBlocking() && p_70665_2_ > 0.0f) {
                p_70665_2_ = (1.0f + p_70665_2_) * 0.5f;
            }
            p_70665_2_ = this.applyArmorCalculations(p_70665_1_, p_70665_2_);
            final float var3;
            p_70665_2_ = (var3 = this.applyPotionDamageCalculations(p_70665_1_, p_70665_2_));
            p_70665_2_ = Math.max(p_70665_2_ - this.getAbsorptionAmount(), 0.0f);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - (var3 - p_70665_2_));
            if (p_70665_2_ != 0.0f) {
                this.addExhaustion(p_70665_1_.getHungerDamage());
                final float var4 = this.getHealth();
                this.setHealth(this.getHealth() - p_70665_2_);
                this.func_110142_aN().func_94547_a(p_70665_1_, var4, p_70665_2_);
            }
        }
    }
    
    public void func_146101_a(final TileEntityFurnace p_146101_1_) {
    }
    
    public void func_146102_a(final TileEntityDispenser p_146102_1_) {
    }
    
    public void func_146100_a(final TileEntity p_146100_1_) {
    }
    
    public void func_146095_a(final CommandBlockLogic p_146095_1_) {
    }
    
    public void func_146098_a(final TileEntityBrewingStand p_146098_1_) {
    }
    
    public void func_146104_a(final TileEntityBeacon p_146104_1_) {
    }
    
    public void displayGUIMerchant(final IMerchant p_71030_1_, final String p_71030_2_) {
    }
    
    public void displayGUIBook(final ItemStack p_71048_1_) {
    }
    
    public boolean interactWith(final Entity p_70998_1_) {
        ItemStack var2 = this.getCurrentEquippedItem();
        final ItemStack var3 = (var2 != null) ? var2.copy() : null;
        if (!p_70998_1_.interactFirst(this)) {
            if (var2 != null && p_70998_1_ instanceof EntityLivingBase) {
                if (this.capabilities.isCreativeMode) {
                    var2 = var3;
                }
                if (var2.interactWithEntity(this, (EntityLivingBase)p_70998_1_)) {
                    if (var2.stackSize <= 0 && !this.capabilities.isCreativeMode) {
                        this.destroyCurrentEquippedItem();
                    }
                    return true;
                }
            }
            return false;
        }
        if (var2 != null && var2 == this.getCurrentEquippedItem()) {
            if (var2.stackSize <= 0 && !this.capabilities.isCreativeMode) {
                this.destroyCurrentEquippedItem();
            }
            else if (var2.stackSize < var3.stackSize && this.capabilities.isCreativeMode) {
                var2.stackSize = var3.stackSize;
            }
        }
        return true;
    }
    
    public ItemStack getCurrentEquippedItem() {
        return this.inventory.getCurrentItem();
    }
    
    public void destroyCurrentEquippedItem() {
        this.inventory.setInventorySlotContents(this.inventory.currentItem, null);
    }
    
    @Override
    public double getYOffset() {
        return this.yOffset - 0.5f;
    }
    
    public void attackTargetEntityWithCurrentItem(final Entity p_71059_1_) {
        if (p_71059_1_.canAttackWithItem() && !p_71059_1_.hitByEntity(this)) {
            float var2 = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
            int var3 = 0;
            float var4 = 0.0f;
            if (p_71059_1_ instanceof EntityLivingBase) {
                var4 = EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase)p_71059_1_);
                var3 += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase)p_71059_1_);
            }
            if (this.isSprinting()) {
                ++var3;
            }
            if (var2 > 0.0f || var4 > 0.0f) {
                final boolean var5 = this.fallDistance > 0.0f && !this.onGround && !this.isOnLadder() && !this.isInWater() && !this.isPotionActive(Potion.blindness) && this.ridingEntity == null && p_71059_1_ instanceof EntityLivingBase;
                if (var5 && var2 > 0.0f) {
                    var2 *= 1.5f;
                }
                var2 += var4;
                boolean var6 = false;
                final int var7 = EnchantmentHelper.getFireAspectModifier(this);
                if (p_71059_1_ instanceof EntityLivingBase && var7 > 0 && !p_71059_1_.isBurning()) {
                    var6 = true;
                    p_71059_1_.setFire(1);
                }
                final boolean var8 = p_71059_1_.attackEntityFrom(DamageSource.causePlayerDamage(this), var2);
                if (var8) {
                    if (var3 > 0) {
                        p_71059_1_.addVelocity(-MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f) * var3 * 0.5f, 0.1, MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f) * var3 * 0.5f);
                        this.motionX *= 0.6;
                        this.motionZ *= 0.6;
                        this.setSprinting(false);
                    }
                    if (var5) {
                        this.onCriticalHit(p_71059_1_);
                    }
                    if (var4 > 0.0f) {
                        this.onEnchantmentCritical(p_71059_1_);
                    }
                    if (var2 >= 18.0f) {
                        this.triggerAchievement(AchievementList.overkill);
                    }
                    this.setLastAttacker(p_71059_1_);
                    if (p_71059_1_ instanceof EntityLivingBase) {
                        EnchantmentHelper.func_151384_a((EntityLivingBase)p_71059_1_, this);
                    }
                    EnchantmentHelper.func_151385_b(this, p_71059_1_);
                    final ItemStack var9 = this.getCurrentEquippedItem();
                    Object var10 = p_71059_1_;
                    if (p_71059_1_ instanceof EntityDragonPart) {
                        final IEntityMultiPart var11 = ((EntityDragonPart)p_71059_1_).entityDragonObj;
                        if (var11 != null && var11 instanceof EntityLivingBase) {
                            var10 = var11;
                        }
                    }
                    if (var9 != null && var10 instanceof EntityLivingBase) {
                        var9.hitEntity((EntityLivingBase)var10, this);
                        if (var9.stackSize <= 0) {
                            this.destroyCurrentEquippedItem();
                        }
                    }
                    if (p_71059_1_ instanceof EntityLivingBase) {
                        this.addStat(StatList.damageDealtStat, Math.round(var2 * 10.0f));
                        if (var7 > 0) {
                            p_71059_1_.setFire(var7 * 4);
                        }
                    }
                    this.addExhaustion(0.3f);
                }
                else if (var6) {
                    p_71059_1_.extinguish();
                }
            }
        }
    }
    
    public void onCriticalHit(final Entity p_71009_1_) {
    }
    
    public void onEnchantmentCritical(final Entity p_71047_1_) {
    }
    
    public void respawnPlayer() {
    }
    
    @Override
    public void setDead() {
        super.setDead();
        this.inventoryContainer.onContainerClosed(this);
        if (this.openContainer != null) {
            this.openContainer.onContainerClosed(this);
        }
    }
    
    @Override
    public boolean isEntityInsideOpaqueBlock() {
        return !this.sleeping && super.isEntityInsideOpaqueBlock();
    }
    
    public GameProfile getGameProfile() {
        return this.field_146106_i;
    }
    
    public EnumStatus sleepInBedAt(final int p_71018_1_, final int p_71018_2_, final int p_71018_3_) {
        if (!this.worldObj.isClient) {
            if (this.isPlayerSleeping() || !this.isEntityAlive()) {
                return EnumStatus.OTHER_PROBLEM;
            }
            if (!this.worldObj.provider.isSurfaceWorld()) {
                return EnumStatus.NOT_POSSIBLE_HERE;
            }
            if (this.worldObj.isDaytime()) {
                return EnumStatus.NOT_POSSIBLE_NOW;
            }
            if (Math.abs(this.posX - p_71018_1_) > 3.0 || Math.abs(this.posY - p_71018_2_) > 2.0 || Math.abs(this.posZ - p_71018_3_) > 3.0) {
                return EnumStatus.TOO_FAR_AWAY;
            }
            final double var4 = 8.0;
            final double var5 = 5.0;
            final List var6 = this.worldObj.getEntitiesWithinAABB(EntityMob.class, AxisAlignedBB.getBoundingBox(p_71018_1_ - var4, p_71018_2_ - var5, p_71018_3_ - var4, p_71018_1_ + var4, p_71018_2_ + var5, p_71018_3_ + var4));
            if (!var6.isEmpty()) {
                return EnumStatus.NOT_SAFE;
            }
        }
        if (this.isRiding()) {
            this.mountEntity(null);
        }
        this.setSize(0.2f, 0.2f);
        this.yOffset = 0.2f;
        if (this.worldObj.blockExists(p_71018_1_, p_71018_2_, p_71018_3_)) {
            final int var7 = this.worldObj.getBlockMetadata(p_71018_1_, p_71018_2_, p_71018_3_);
            final int var8 = BlockDirectional.func_149895_l(var7);
            float var9 = 0.5f;
            float var10 = 0.5f;
            switch (var8) {
                case 0: {
                    var10 = 0.9f;
                    break;
                }
                case 1: {
                    var9 = 0.1f;
                    break;
                }
                case 2: {
                    var10 = 0.1f;
                    break;
                }
                case 3: {
                    var9 = 0.9f;
                    break;
                }
            }
            this.func_71013_b(var8);
            this.setPosition(p_71018_1_ + var9, p_71018_2_ + 0.9375f, p_71018_3_ + var10);
        }
        else {
            this.setPosition(p_71018_1_ + 0.5f, p_71018_2_ + 0.9375f, p_71018_3_ + 0.5f);
        }
        this.sleeping = true;
        this.sleepTimer = 0;
        this.playerLocation = new ChunkCoordinates(p_71018_1_, p_71018_2_, p_71018_3_);
        final double motionX = 0.0;
        this.motionY = motionX;
        this.motionZ = motionX;
        this.motionX = motionX;
        if (!this.worldObj.isClient) {
            this.worldObj.updateAllPlayersSleepingFlag();
        }
        return EnumStatus.OK;
    }
    
    private void func_71013_b(final int p_71013_1_) {
        this.field_71079_bU = 0.0f;
        this.field_71089_bV = 0.0f;
        switch (p_71013_1_) {
            case 0: {
                this.field_71089_bV = -1.8f;
                break;
            }
            case 1: {
                this.field_71079_bU = 1.8f;
                break;
            }
            case 2: {
                this.field_71089_bV = 1.8f;
                break;
            }
            case 3: {
                this.field_71079_bU = -1.8f;
                break;
            }
        }
    }
    
    public void wakeUpPlayer(final boolean p_70999_1_, final boolean p_70999_2_, final boolean p_70999_3_) {
        this.setSize(0.6f, 1.8f);
        this.resetHeight();
        final ChunkCoordinates var4 = this.playerLocation;
        ChunkCoordinates var5 = this.playerLocation;
        if (var4 != null && this.worldObj.getBlock(var4.posX, var4.posY, var4.posZ) == Blocks.bed) {
            BlockBed.func_149979_a(this.worldObj, var4.posX, var4.posY, var4.posZ, false);
            var5 = BlockBed.func_149977_a(this.worldObj, var4.posX, var4.posY, var4.posZ, 0);
            if (var5 == null) {
                var5 = new ChunkCoordinates(var4.posX, var4.posY + 1, var4.posZ);
            }
            this.setPosition(var5.posX + 0.5f, var5.posY + this.yOffset + 0.1f, var5.posZ + 0.5f);
        }
        this.sleeping = false;
        if (!this.worldObj.isClient && p_70999_2_) {
            this.worldObj.updateAllPlayersSleepingFlag();
        }
        if (p_70999_1_) {
            this.sleepTimer = 0;
        }
        else {
            this.sleepTimer = 100;
        }
        if (p_70999_3_) {
            this.setSpawnChunk(this.playerLocation, false);
        }
    }
    
    private boolean isInBed() {
        return this.worldObj.getBlock(this.playerLocation.posX, this.playerLocation.posY, this.playerLocation.posZ) == Blocks.bed;
    }
    
    public static ChunkCoordinates verifyRespawnCoordinates(final World p_71056_0_, final ChunkCoordinates p_71056_1_, final boolean p_71056_2_) {
        final IChunkProvider var3 = p_71056_0_.getChunkProvider();
        var3.loadChunk(p_71056_1_.posX - 3 >> 4, p_71056_1_.posZ - 3 >> 4);
        var3.loadChunk(p_71056_1_.posX + 3 >> 4, p_71056_1_.posZ - 3 >> 4);
        var3.loadChunk(p_71056_1_.posX - 3 >> 4, p_71056_1_.posZ + 3 >> 4);
        var3.loadChunk(p_71056_1_.posX + 3 >> 4, p_71056_1_.posZ + 3 >> 4);
        if (p_71056_0_.getBlock(p_71056_1_.posX, p_71056_1_.posY, p_71056_1_.posZ) == Blocks.bed) {
            final ChunkCoordinates var4 = BlockBed.func_149977_a(p_71056_0_, p_71056_1_.posX, p_71056_1_.posY, p_71056_1_.posZ, 0);
            return var4;
        }
        final Material var5 = p_71056_0_.getBlock(p_71056_1_.posX, p_71056_1_.posY, p_71056_1_.posZ).getMaterial();
        final Material var6 = p_71056_0_.getBlock(p_71056_1_.posX, p_71056_1_.posY + 1, p_71056_1_.posZ).getMaterial();
        final boolean var7 = !var5.isSolid() && !var5.isLiquid();
        final boolean var8 = !var6.isSolid() && !var6.isLiquid();
        return (p_71056_2_ && var7 && var8) ? p_71056_1_ : null;
    }
    
    public float getBedOrientationInDegrees() {
        if (this.playerLocation != null) {
            final int var1 = this.worldObj.getBlockMetadata(this.playerLocation.posX, this.playerLocation.posY, this.playerLocation.posZ);
            final int var2 = BlockDirectional.func_149895_l(var1);
            switch (var2) {
                case 0: {
                    return 90.0f;
                }
                case 1: {
                    return 0.0f;
                }
                case 2: {
                    return 270.0f;
                }
                case 3: {
                    return 180.0f;
                }
            }
        }
        return 0.0f;
    }
    
    @Override
    public boolean isPlayerSleeping() {
        return this.sleeping;
    }
    
    public boolean isPlayerFullyAsleep() {
        return this.sleeping && this.sleepTimer >= 100;
    }
    
    public int getSleepTimer() {
        return this.sleepTimer;
    }
    
    protected boolean getHideCape(final int p_82241_1_) {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1 << p_82241_1_) != 0x0;
    }
    
    protected void setHideCape(final int p_82239_1_, final boolean p_82239_2_) {
        final byte var3 = this.dataWatcher.getWatchableObjectByte(16);
        if (p_82239_2_) {
            this.dataWatcher.updateObject(16, (byte)(var3 | 1 << p_82239_1_));
        }
        else {
            this.dataWatcher.updateObject(16, (byte)(var3 & ~(1 << p_82239_1_)));
        }
    }
    
    public void addChatComponentMessage(final IChatComponent p_146105_1_) {
    }
    
    public ChunkCoordinates getBedLocation() {
        return this.spawnChunk;
    }
    
    public boolean isSpawnForced() {
        return this.spawnForced;
    }
    
    public void setSpawnChunk(final ChunkCoordinates p_71063_1_, final boolean p_71063_2_) {
        if (p_71063_1_ != null) {
            this.spawnChunk = new ChunkCoordinates(p_71063_1_);
            this.spawnForced = p_71063_2_;
        }
        else {
            this.spawnChunk = null;
            this.spawnForced = false;
        }
    }
    
    public void triggerAchievement(final StatBase p_71029_1_) {
        this.addStat(p_71029_1_, 1);
    }
    
    public void addStat(final StatBase p_71064_1_, final int p_71064_2_) {
    }
    
    public void jump() {
        super.jump();
        this.addStat(StatList.jumpStat, 1);
        if (this.isSprinting()) {
            this.addExhaustion(0.8f);
        }
        else {
            this.addExhaustion(0.2f);
        }
    }
    
    @Override
    public void moveEntityWithHeading(final float p_70612_1_, final float p_70612_2_) {
        final double var3 = this.posX;
        final double var4 = this.posY;
        final double var5 = this.posZ;
        if (this.capabilities.isFlying && this.ridingEntity == null) {
            final double var6 = this.motionY;
            final float var7 = this.jumpMovementFactor;
            this.jumpMovementFactor = this.capabilities.getFlySpeed();
            super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
            this.motionY = var6 * 0.6;
            this.jumpMovementFactor = var7;
        }
        else {
            super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
        }
        this.addMovementStat(this.posX - var3, this.posY - var4, this.posZ - var5);
    }
    
    @Override
    public float getAIMoveSpeed() {
        return (float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
    }
    
    public void addMovementStat(final double p_71000_1_, final double p_71000_3_, final double p_71000_5_) {
        if (this.ridingEntity == null) {
            if (this.isInsideOfMaterial(Material.water)) {
                final int var7 = Math.round(MathHelper.sqrt_double(p_71000_1_ * p_71000_1_ + p_71000_3_ * p_71000_3_ + p_71000_5_ * p_71000_5_) * 100.0f);
                if (var7 > 0) {
                    this.addStat(StatList.distanceDoveStat, var7);
                    this.addExhaustion(0.015f * var7 * 0.01f);
                }
            }
            else if (this.isInWater()) {
                final int var7 = Math.round(MathHelper.sqrt_double(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0f);
                if (var7 > 0) {
                    this.addStat(StatList.distanceSwumStat, var7);
                    this.addExhaustion(0.015f * var7 * 0.01f);
                }
            }
            else if (this.isOnLadder()) {
                if (p_71000_3_ > 0.0) {
                    this.addStat(StatList.distanceClimbedStat, (int)Math.round(p_71000_3_ * 100.0));
                }
            }
            else if (this.onGround) {
                final int var7 = Math.round(MathHelper.sqrt_double(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0f);
                if (var7 > 0) {
                    this.addStat(StatList.distanceWalkedStat, var7);
                    if (this.isSprinting()) {
                        this.addExhaustion(0.099999994f * var7 * 0.01f);
                    }
                    else {
                        this.addExhaustion(0.01f * var7 * 0.01f);
                    }
                }
            }
            else {
                final int var7 = Math.round(MathHelper.sqrt_double(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0f);
                if (var7 > 25) {
                    this.addStat(StatList.distanceFlownStat, var7);
                }
            }
        }
    }
    
    private void addMountedMovementStat(final double p_71015_1_, final double p_71015_3_, final double p_71015_5_) {
        if (this.ridingEntity != null) {
            final int var7 = Math.round(MathHelper.sqrt_double(p_71015_1_ * p_71015_1_ + p_71015_3_ * p_71015_3_ + p_71015_5_ * p_71015_5_) * 100.0f);
            if (var7 > 0) {
                if (this.ridingEntity instanceof EntityMinecart) {
                    this.addStat(StatList.distanceByMinecartStat, var7);
                    if (this.startMinecartRidingCoordinate == null) {
                        this.startMinecartRidingCoordinate = new ChunkCoordinates(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
                    }
                    else if (this.startMinecartRidingCoordinate.getDistanceSquared(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) >= 1000000.0) {
                        this.addStat(AchievementList.onARail, 1);
                    }
                }
                else if (this.ridingEntity instanceof EntityBoat) {
                    this.addStat(StatList.distanceByBoatStat, var7);
                }
                else if (this.ridingEntity instanceof EntityPig) {
                    this.addStat(StatList.distanceByPigStat, var7);
                }
                else if (this.ridingEntity instanceof EntityHorse) {
                    this.addStat(StatList.field_151185_q, var7);
                }
            }
        }
    }
    
    @Override
    protected void fall(final float p_70069_1_) {
        if (!this.capabilities.allowFlying) {
            if (p_70069_1_ >= 2.0f) {
                this.addStat(StatList.distanceFallenStat, (int)Math.round(p_70069_1_ * 100.0));
            }
            super.fall(p_70069_1_);
        }
    }
    
    @Override
    protected String func_146067_o(final int p_146067_1_) {
        return (p_146067_1_ > 4) ? "game.player.hurt.fall.big" : "game.player.hurt.fall.small";
    }
    
    @Override
    public void onKillEntity(final EntityLivingBase p_70074_1_) {
        if (p_70074_1_ instanceof IMob) {
            this.triggerAchievement(AchievementList.killEnemy);
        }
        final int var2 = EntityList.getEntityID(p_70074_1_);
        final EntityList.EntityEggInfo var3 = EntityList.entityEggs.get(var2);
        if (var3 != null) {
            this.addStat(var3.field_151512_d, 1);
        }
    }
    
    @Override
    public void setInWeb() {
        if (!this.capabilities.isFlying) {
            super.setInWeb();
        }
    }
    
    @Override
    public IIcon getItemIcon(final ItemStack p_70620_1_, final int p_70620_2_) {
        IIcon var3 = super.getItemIcon(p_70620_1_, p_70620_2_);
        if (p_70620_1_.getItem() == Items.fishing_rod && this.fishEntity != null) {
            var3 = Items.fishing_rod.func_94597_g();
        }
        else {
            if (p_70620_1_.getItem().requiresMultipleRenderPasses()) {
                return p_70620_1_.getItem().getIconFromDamageForRenderPass(p_70620_1_.getItemDamage(), p_70620_2_);
            }
            if (this.itemInUse != null && p_70620_1_.getItem() == Items.bow) {
                final int var4 = p_70620_1_.getMaxItemUseDuration() - this.itemInUseCount;
                if (var4 >= 18) {
                    return Items.bow.getItemIconForUseDuration(2);
                }
                if (var4 > 13) {
                    return Items.bow.getItemIconForUseDuration(1);
                }
                if (var4 > 0) {
                    return Items.bow.getItemIconForUseDuration(0);
                }
            }
        }
        return var3;
    }
    
    public ItemStack getCurrentArmor(final int p_82169_1_) {
        return this.inventory.armorItemInSlot(p_82169_1_);
    }
    
    public void addExperience(int p_71023_1_) {
        this.addScore(p_71023_1_);
        final int var2 = Integer.MAX_VALUE - this.experienceTotal;
        if (p_71023_1_ > var2) {
            p_71023_1_ = var2;
        }
        this.experience += p_71023_1_ / (float)this.xpBarCap();
        this.experienceTotal += p_71023_1_;
        while (this.experience >= 1.0f) {
            this.experience = (this.experience - 1.0f) * this.xpBarCap();
            this.addExperienceLevel(1);
            this.experience /= this.xpBarCap();
        }
    }
    
    public void addExperienceLevel(final int p_82242_1_) {
        this.experienceLevel += p_82242_1_;
        if (this.experienceLevel < 0) {
            this.experienceLevel = 0;
            this.experience = 0.0f;
            this.experienceTotal = 0;
        }
        if (p_82242_1_ > 0 && this.experienceLevel % 5 == 0 && this.field_82249_h < this.ticksExisted - 100.0f) {
            final float var2 = (this.experienceLevel > 30) ? 1.0f : (this.experienceLevel / 30.0f);
            this.worldObj.playSoundAtEntity(this, "random.levelup", var2 * 0.75f, 1.0f);
            this.field_82249_h = this.ticksExisted;
        }
    }
    
    public int xpBarCap() {
        return (this.experienceLevel >= 30) ? (62 + (this.experienceLevel - 30) * 7) : ((this.experienceLevel >= 15) ? (17 + (this.experienceLevel - 15) * 3) : 17);
    }
    
    public void addExhaustion(final float p_71020_1_) {
        if (!this.capabilities.disableDamage && !this.worldObj.isClient) {
            this.foodStats.addExhaustion(p_71020_1_);
        }
    }
    
    public FoodStats getFoodStats() {
        return this.foodStats;
    }
    
    public boolean canEat(final boolean p_71043_1_) {
        return (p_71043_1_ || this.foodStats.needFood()) && !this.capabilities.disableDamage;
    }
    
    public boolean shouldHeal() {
        return this.getHealth() > 0.0f && this.getHealth() < this.getMaxHealth();
    }
    
    public void setItemInUse(final ItemStack p_71008_1_, final int p_71008_2_) {
        if (p_71008_1_ != this.itemInUse) {
            this.itemInUse = p_71008_1_;
            this.itemInUseCount = p_71008_2_;
            if (!this.worldObj.isClient) {
                this.setEating(true);
            }
        }
    }
    
    public boolean isCurrentToolAdventureModeExempt(final int p_82246_1_, final int p_82246_2_, final int p_82246_3_) {
        if (this.capabilities.allowEdit) {
            return true;
        }
        final Block var4 = this.worldObj.getBlock(p_82246_1_, p_82246_2_, p_82246_3_);
        if (var4.getMaterial() != Material.air) {
            if (var4.getMaterial().isAdventureModeExempt()) {
                return true;
            }
            if (this.getCurrentEquippedItem() != null) {
                final ItemStack var5 = this.getCurrentEquippedItem();
                if (var5.func_150998_b(var4) || var5.func_150997_a(var4) > 1.0f) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean canPlayerEdit(final int p_82247_1_, final int p_82247_2_, final int p_82247_3_, final int p_82247_4_, final ItemStack p_82247_5_) {
        return this.capabilities.allowEdit || (p_82247_5_ != null && p_82247_5_.canEditBlocks());
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer p_70693_1_) {
        if (this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
            return 0;
        }
        final int var2 = this.experienceLevel * 7;
        return (var2 > 100) ? 100 : var2;
    }
    
    @Override
    protected boolean isPlayer() {
        return true;
    }
    
    @Override
    public boolean getAlwaysRenderNameTagForRender() {
        return true;
    }
    
    public void clonePlayer(final EntityPlayer p_71049_1_, final boolean p_71049_2_) {
        if (p_71049_2_) {
            this.inventory.copyInventory(p_71049_1_.inventory);
            this.setHealth(p_71049_1_.getHealth());
            this.foodStats = p_71049_1_.foodStats;
            this.experienceLevel = p_71049_1_.experienceLevel;
            this.experienceTotal = p_71049_1_.experienceTotal;
            this.experience = p_71049_1_.experience;
            this.setScore(p_71049_1_.getScore());
            this.teleportDirection = p_71049_1_.teleportDirection;
        }
        else if (this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
            this.inventory.copyInventory(p_71049_1_.inventory);
            this.experienceLevel = p_71049_1_.experienceLevel;
            this.experienceTotal = p_71049_1_.experienceTotal;
            this.experience = p_71049_1_.experience;
            this.setScore(p_71049_1_.getScore());
        }
        this.theInventoryEnderChest = p_71049_1_.theInventoryEnderChest;
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return !this.capabilities.isFlying;
    }
    
    public void sendPlayerAbilities() {
    }
    
    public void setGameType(final WorldSettings.GameType p_71033_1_) {
    }
    
    @Override
    public String getCommandSenderName() {
        return this.field_146106_i.getName();
    }
    
    @Override
    public World getEntityWorld() {
        return this.worldObj;
    }
    
    public InventoryEnderChest getInventoryEnderChest() {
        return this.theInventoryEnderChest;
    }
    
    @Override
    public ItemStack getEquipmentInSlot(final int p_71124_1_) {
        return (p_71124_1_ == 0) ? this.inventory.getCurrentItem() : this.inventory.armorInventory[p_71124_1_ - 1];
    }
    
    @Override
    public ItemStack getHeldItem() {
        return this.inventory.getCurrentItem();
    }
    
    @Override
    public void setCurrentItemOrArmor(final int p_70062_1_, final ItemStack p_70062_2_) {
        this.inventory.armorInventory[p_70062_1_] = p_70062_2_;
    }
    
    @Override
    public boolean isInvisibleToPlayer(final EntityPlayer p_98034_1_) {
        if (!this.isInvisible()) {
            return false;
        }
        final Team var2 = this.getTeam();
        return var2 == null || p_98034_1_ == null || p_98034_1_.getTeam() != var2 || !var2.func_98297_h();
    }
    
    @Override
    public ItemStack[] getLastActiveItems() {
        return this.inventory.armorInventory;
    }
    
    public boolean getHideCape() {
        return this.getHideCape(1);
    }
    
    @Override
    public boolean isPushedByWater() {
        return !this.capabilities.isFlying;
    }
    
    public Scoreboard getWorldScoreboard() {
        return this.worldObj.getScoreboard();
    }
    
    @Override
    public Team getTeam() {
        return this.getWorldScoreboard().getPlayersTeam(this.getCommandSenderName());
    }
    
    @Override
    public IChatComponent func_145748_c_() {
        final ChatComponentText var1 = new ChatComponentText(ScorePlayerTeam.formatPlayerName(this.getTeam(), this.getCommandSenderName()));
        var1.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + this.getCommandSenderName() + " "));
        return var1;
    }
    
    @Override
    public void setAbsorptionAmount(float p_110149_1_) {
        if (p_110149_1_ < 0.0f) {
            p_110149_1_ = 0.0f;
        }
        this.getDataWatcher().updateObject(17, p_110149_1_);
    }
    
    @Override
    public float getAbsorptionAmount() {
        return this.getDataWatcher().getWatchableObjectFloat(17);
    }
    
    public static UUID func_146094_a(final GameProfile p_146094_0_) {
        UUID var1 = p_146094_0_.getId();
        if (var1 == null) {
            var1 = UUID.nameUUIDFromBytes(("OfflinePlayer:" + p_146094_0_.getName()).getBytes(Charsets.UTF_8));
        }
        return var1;
    }
    
    public enum EnumChatVisibility
    {
        FULL("FULL", 0, 0, "options.chat.visibility.full"), 
        SYSTEM("SYSTEM", 1, 1, "options.chat.visibility.system"), 
        HIDDEN("HIDDEN", 2, 2, "options.chat.visibility.hidden");
        
        private static final EnumChatVisibility[] field_151432_d;
        private final int chatVisibility;
        private final String resourceKey;
        private static final EnumChatVisibility[] $VALUES;
        private static final String __OBFID = "CL_00001714";
        
        private EnumChatVisibility(final String p_i45323_1_, final int p_i45323_2_, final int p_i45323_3_, final String p_i45323_4_) {
            this.chatVisibility = p_i45323_3_;
            this.resourceKey = p_i45323_4_;
        }
        
        public int getChatVisibility() {
            return this.chatVisibility;
        }
        
        public static EnumChatVisibility getEnumChatVisibility(final int p_151426_0_) {
            return EnumChatVisibility.field_151432_d[p_151426_0_ % EnumChatVisibility.field_151432_d.length];
        }
        
        public String getResourceKey() {
            return this.resourceKey;
        }
        
        static {
            field_151432_d = new EnumChatVisibility[values().length];
            $VALUES = new EnumChatVisibility[] { EnumChatVisibility.FULL, EnumChatVisibility.SYSTEM, EnumChatVisibility.HIDDEN };
            for (final EnumChatVisibility var4 : values()) {
                EnumChatVisibility.field_151432_d[var4.chatVisibility] = var4;
            }
        }
    }
    
    public enum EnumStatus
    {
        OK("OK", 0), 
        NOT_POSSIBLE_HERE("NOT_POSSIBLE_HERE", 1), 
        NOT_POSSIBLE_NOW("NOT_POSSIBLE_NOW", 2), 
        TOO_FAR_AWAY("TOO_FAR_AWAY", 3), 
        OTHER_PROBLEM("OTHER_PROBLEM", 4), 
        NOT_SAFE("NOT_SAFE", 5);
        
        private static final EnumStatus[] $VALUES;
        private static final String __OBFID = "CL_00001712";
        
        private EnumStatus(final String p_i1751_1_, final int p_i1751_2_) {
        }
        
        static {
            $VALUES = new EnumStatus[] { EnumStatus.OK, EnumStatus.NOT_POSSIBLE_HERE, EnumStatus.NOT_POSSIBLE_NOW, EnumStatus.TOO_FAR_AWAY, EnumStatus.OTHER_PROBLEM, EnumStatus.NOT_SAFE };
        }
    }
}
