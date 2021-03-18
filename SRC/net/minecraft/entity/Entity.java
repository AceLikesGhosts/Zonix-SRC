package net.minecraft.entity;

import net.minecraft.block.material.*;
import net.minecraft.server.*;
import net.minecraft.enchantment.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.crash.*;
import net.minecraft.block.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.item.*;
import net.minecraft.item.*;
import net.minecraft.entity.effect.*;
import net.minecraft.world.*;
import java.util.concurrent.*;
import net.minecraft.util.*;

public abstract class Entity
{
    private static int nextEntityID;
    private int field_145783_c;
    public double renderDistanceWeight;
    public boolean preventEntitySpawning;
    public Entity riddenByEntity;
    public Entity ridingEntity;
    public boolean forceSpawn;
    public World worldObj;
    public double prevPosX;
    public double prevPosY;
    public double prevPosZ;
    public double posX;
    public double posY;
    public double posZ;
    public double motionX;
    public double motionY;
    public double motionZ;
    public float rotationYaw;
    public float rotationPitch;
    public float prevRotationYaw;
    public float prevRotationPitch;
    public final AxisAlignedBB boundingBox;
    public boolean onGround;
    public boolean isCollidedHorizontally;
    public boolean isCollidedVertically;
    public boolean isCollided;
    public boolean velocityChanged;
    protected boolean isInWeb;
    public boolean field_70135_K;
    public boolean isDead;
    public float yOffset;
    public float width;
    public float height;
    public float prevDistanceWalkedModified;
    public float distanceWalkedModified;
    public float distanceWalkedOnStepModified;
    public float fallDistance;
    private int nextStepDistance;
    public double lastTickPosX;
    public double lastTickPosY;
    public double lastTickPosZ;
    public float ySize;
    public float stepHeight;
    public boolean noClip;
    public float entityCollisionReduction;
    protected Random rand;
    public int ticksExisted;
    public int fireResistance;
    private int fire;
    protected boolean inWater;
    public int hurtResistantTime;
    private boolean firstUpdate;
    protected boolean isImmuneToFire;
    protected DataWatcher dataWatcher;
    private double entityRiderPitchDelta;
    private double entityRiderYawDelta;
    public boolean addedToChunk;
    public int chunkCoordX;
    public int chunkCoordY;
    public int chunkCoordZ;
    public int serverPosX;
    public int serverPosY;
    public int serverPosZ;
    public boolean ignoreFrustumCheck;
    public boolean isAirBorne;
    public int timeUntilPortal;
    protected boolean inPortal;
    protected int portalCounter;
    public int dimension;
    protected int teleportDirection;
    private boolean invulnerable;
    protected UUID entityUniqueID;
    public EnumEntitySize myEntitySize;
    private static final String __OBFID = "CL_00001533";
    
    public int getEntityId() {
        return this.field_145783_c;
    }
    
    public void setEntityId(final int p_145769_1_) {
        this.field_145783_c = p_145769_1_;
    }
    
    public Entity(final World p_i1582_1_) {
        this.field_145783_c = Entity.nextEntityID++;
        this.renderDistanceWeight = 1.0;
        this.boundingBox = AxisAlignedBB.getBoundingBox(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        this.field_70135_K = true;
        this.width = 0.6f;
        this.height = 1.8f;
        this.nextStepDistance = 1;
        this.rand = new Random();
        this.fireResistance = 1;
        this.firstUpdate = true;
        this.entityUniqueID = UUID.randomUUID();
        this.myEntitySize = EnumEntitySize.SIZE_2;
        this.worldObj = p_i1582_1_;
        this.setPosition(0.0, 0.0, 0.0);
        if (p_i1582_1_ != null) {
            this.dimension = p_i1582_1_.provider.dimensionId;
        }
        (this.dataWatcher = new DataWatcher(this)).addObject(0, 0);
        this.dataWatcher.addObject(1, 300);
        this.entityInit();
    }
    
    protected abstract void entityInit();
    
    public DataWatcher getDataWatcher() {
        return this.dataWatcher;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        return p_equals_1_ instanceof Entity && ((Entity)p_equals_1_).field_145783_c == this.field_145783_c;
    }
    
    @Override
    public int hashCode() {
        return this.field_145783_c;
    }
    
    protected void preparePlayerToSpawn() {
        if (this.worldObj != null) {
            while (this.posY > 0.0) {
                this.setPosition(this.posX, this.posY, this.posZ);
                if (this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty()) {
                    break;
                }
                ++this.posY;
            }
            final double motionX = 0.0;
            this.motionZ = motionX;
            this.motionY = motionX;
            this.motionX = motionX;
            this.rotationPitch = 0.0f;
        }
    }
    
    public void setDead() {
        this.isDead = true;
    }
    
    protected void setSize(final float p_70105_1_, final float p_70105_2_) {
        if (p_70105_1_ != this.width || p_70105_2_ != this.height) {
            final float var3 = this.width;
            this.width = p_70105_1_;
            this.height = p_70105_2_;
            this.boundingBox.maxX = this.boundingBox.minX + this.width;
            this.boundingBox.maxZ = this.boundingBox.minZ + this.width;
            this.boundingBox.maxY = this.boundingBox.minY + this.height;
            if (this.width > var3 && !this.firstUpdate && !this.worldObj.isClient) {
                this.moveEntity(var3 - this.width, 0.0, var3 - this.width);
            }
        }
        final float var3 = p_70105_1_ % 2.0f;
        if (var3 < 0.375) {
            this.myEntitySize = EnumEntitySize.SIZE_1;
        }
        else if (var3 < 0.75) {
            this.myEntitySize = EnumEntitySize.SIZE_2;
        }
        else if (var3 < 1.0) {
            this.myEntitySize = EnumEntitySize.SIZE_3;
        }
        else if (var3 < 1.375) {
            this.myEntitySize = EnumEntitySize.SIZE_4;
        }
        else if (var3 < 1.75) {
            this.myEntitySize = EnumEntitySize.SIZE_5;
        }
        else {
            this.myEntitySize = EnumEntitySize.SIZE_6;
        }
    }
    
    protected void setRotation(final float p_70101_1_, final float p_70101_2_) {
        this.rotationYaw = p_70101_1_ % 360.0f;
        this.rotationPitch = p_70101_2_ % 360.0f;
    }
    
    public void setPosition(final double p_70107_1_, final double p_70107_3_, final double p_70107_5_) {
        this.posX = p_70107_1_;
        this.posY = p_70107_3_;
        this.posZ = p_70107_5_;
        final float var7 = this.width / 2.0f;
        final float var8 = this.height;
        this.boundingBox.setBounds(p_70107_1_ - var7, p_70107_3_ - this.yOffset + this.ySize, p_70107_5_ - var7, p_70107_1_ + var7, p_70107_3_ - this.yOffset + this.ySize + var8, p_70107_5_ + var7);
    }
    
    public void setAngles(final float p_70082_1_, final float p_70082_2_) {
        final float var3 = this.rotationPitch;
        final float var4 = this.rotationYaw;
        this.rotationYaw += (float)(p_70082_1_ * 0.15);
        this.rotationPitch -= (float)(p_70082_2_ * 0.15);
        if (this.rotationPitch < -90.0f) {
            this.rotationPitch = -90.0f;
        }
        if (this.rotationPitch > 90.0f) {
            this.rotationPitch = 90.0f;
        }
        this.prevRotationPitch += this.rotationPitch - var3;
        this.prevRotationYaw += this.rotationYaw - var4;
    }
    
    public void onUpdate() {
        this.onEntityUpdate();
    }
    
    public void onEntityUpdate() {
        this.worldObj.theProfiler.startSection("entityBaseTick");
        if (this.ridingEntity != null && this.ridingEntity.isDead) {
            this.ridingEntity = null;
        }
        this.prevDistanceWalkedModified = this.distanceWalkedModified;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.prevRotationPitch = this.rotationPitch;
        this.prevRotationYaw = this.rotationYaw;
        if (!this.worldObj.isClient && this.worldObj instanceof WorldServer) {
            this.worldObj.theProfiler.startSection("portal");
            final MinecraftServer var1 = ((WorldServer)this.worldObj).func_73046_m();
            final int var2 = this.getMaxInPortalTime();
            if (this.inPortal) {
                if (var1.getAllowNether()) {
                    if (this.ridingEntity == null && this.portalCounter++ >= var2) {
                        this.portalCounter = var2;
                        this.timeUntilPortal = this.getPortalCooldown();
                        byte var3;
                        if (this.worldObj.provider.dimensionId == -1) {
                            var3 = 0;
                        }
                        else {
                            var3 = -1;
                        }
                        this.travelToDimension(var3);
                    }
                    this.inPortal = false;
                }
            }
            else {
                if (this.portalCounter > 0) {
                    this.portalCounter -= 4;
                }
                if (this.portalCounter < 0) {
                    this.portalCounter = 0;
                }
            }
            if (this.timeUntilPortal > 0) {
                --this.timeUntilPortal;
            }
            this.worldObj.theProfiler.endSection();
        }
        if (this.isSprinting() && !this.isInWater()) {
            final int var4 = MathHelper.floor_double(this.posX);
            final int var2 = MathHelper.floor_double(this.posY - 0.20000000298023224 - this.yOffset);
            final int var5 = MathHelper.floor_double(this.posZ);
            final Block var6 = this.worldObj.getBlock(var4, var2, var5);
            if (var6.getMaterial() != Material.air) {
                this.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(var6) + "_" + this.worldObj.getBlockMetadata(var4, var2, var5), this.posX + (this.rand.nextFloat() - 0.5) * this.width, this.boundingBox.minY + 0.1, this.posZ + (this.rand.nextFloat() - 0.5) * this.width, -this.motionX * 4.0, 1.5, -this.motionZ * 4.0);
            }
        }
        this.handleWaterMovement();
        if (this.worldObj.isClient) {
            this.fire = 0;
        }
        else if (this.fire > 0) {
            if (this.isImmuneToFire) {
                this.fire -= 4;
                if (this.fire < 0) {
                    this.fire = 0;
                }
            }
            else {
                if (this.fire % 20 == 0) {
                    this.attackEntityFrom(DamageSource.onFire, 1.0f);
                }
                --this.fire;
            }
        }
        if (this.handleLavaMovement()) {
            this.setOnFireFromLava();
            this.fallDistance *= 0.5f;
        }
        if (this.posY < -64.0) {
            this.kill();
        }
        if (!this.worldObj.isClient) {
            this.setFlag(0, this.fire > 0);
        }
        this.firstUpdate = false;
        this.worldObj.theProfiler.endSection();
    }
    
    public int getMaxInPortalTime() {
        return 0;
    }
    
    protected void setOnFireFromLava() {
        if (!this.isImmuneToFire) {
            this.attackEntityFrom(DamageSource.lava, 4.0f);
            this.setFire(15);
        }
    }
    
    public void setFire(final int p_70015_1_) {
        int var2 = p_70015_1_ * 20;
        var2 = EnchantmentProtection.getFireTimeForEntity(this, var2);
        if (this.fire < var2) {
            this.fire = var2;
        }
    }
    
    public void extinguish() {
        this.fire = 0;
    }
    
    protected void kill() {
        this.setDead();
    }
    
    public boolean isOffsetPositionInLiquid(final double p_70038_1_, final double p_70038_3_, final double p_70038_5_) {
        final AxisAlignedBB var7 = this.boundingBox.getOffsetBoundingBox(p_70038_1_, p_70038_3_, p_70038_5_);
        final List var8 = this.worldObj.getCollidingBoundingBoxes(this, var7);
        return var8.isEmpty() && !this.worldObj.isAnyLiquid(var7);
    }
    
    public void moveEntity(double p_70091_1_, double p_70091_3_, double p_70091_5_) {
        if (this.noClip) {
            this.boundingBox.offset(p_70091_1_, p_70091_3_, p_70091_5_);
            this.posX = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0;
            this.posY = this.boundingBox.minY + this.yOffset - this.ySize;
            this.posZ = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0;
        }
        else {
            this.worldObj.theProfiler.startSection("move");
            this.ySize *= 0.4f;
            final double var7 = this.posX;
            final double var8 = this.posY;
            final double var9 = this.posZ;
            if (this.isInWeb) {
                this.isInWeb = false;
                p_70091_1_ *= 0.25;
                p_70091_3_ *= 0.05000000074505806;
                p_70091_5_ *= 0.25;
                this.motionX = 0.0;
                this.motionY = 0.0;
                this.motionZ = 0.0;
            }
            double var10 = p_70091_1_;
            final double var11 = p_70091_3_;
            double var12 = p_70091_5_;
            final AxisAlignedBB var13 = this.boundingBox.copy();
            final boolean var14 = this.onGround && this.isSneaking() && this instanceof EntityPlayer;
            if (var14) {
                final double var15 = 0.05;
                while (p_70091_1_ != 0.0 && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.getOffsetBoundingBox(p_70091_1_, -1.0, 0.0)).isEmpty()) {
                    if (p_70091_1_ < var15 && p_70091_1_ >= -var15) {
                        p_70091_1_ = 0.0;
                    }
                    else if (p_70091_1_ > 0.0) {
                        p_70091_1_ -= var15;
                    }
                    else {
                        p_70091_1_ += var15;
                    }
                    var10 = p_70091_1_;
                }
                while (p_70091_5_ != 0.0 && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.getOffsetBoundingBox(0.0, -1.0, p_70091_5_)).isEmpty()) {
                    if (p_70091_5_ < var15 && p_70091_5_ >= -var15) {
                        p_70091_5_ = 0.0;
                    }
                    else if (p_70091_5_ > 0.0) {
                        p_70091_5_ -= var15;
                    }
                    else {
                        p_70091_5_ += var15;
                    }
                    var12 = p_70091_5_;
                }
                while (p_70091_1_ != 0.0 && p_70091_5_ != 0.0 && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.getOffsetBoundingBox(p_70091_1_, -1.0, p_70091_5_)).isEmpty()) {
                    if (p_70091_1_ < var15 && p_70091_1_ >= -var15) {
                        p_70091_1_ = 0.0;
                    }
                    else if (p_70091_1_ > 0.0) {
                        p_70091_1_ -= var15;
                    }
                    else {
                        p_70091_1_ += var15;
                    }
                    if (p_70091_5_ < var15 && p_70091_5_ >= -var15) {
                        p_70091_5_ = 0.0;
                    }
                    else if (p_70091_5_ > 0.0) {
                        p_70091_5_ -= var15;
                    }
                    else {
                        p_70091_5_ += var15;
                    }
                    var10 = p_70091_1_;
                    var12 = p_70091_5_;
                }
            }
            List var16 = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(p_70091_1_, p_70091_3_, p_70091_5_));
            for (int var17 = 0; var17 < var16.size(); ++var17) {
                p_70091_3_ = var16.get(var17).calculateYOffset(this.boundingBox, p_70091_3_);
            }
            this.boundingBox.offset(0.0, p_70091_3_, 0.0);
            if (!this.field_70135_K && var11 != p_70091_3_) {
                p_70091_5_ = 0.0;
                p_70091_3_ = 0.0;
                p_70091_1_ = 0.0;
            }
            final boolean var18 = this.onGround || (var11 != p_70091_3_ && var11 < 0.0);
            for (int var19 = 0; var19 < var16.size(); ++var19) {
                p_70091_1_ = var16.get(var19).calculateXOffset(this.boundingBox, p_70091_1_);
            }
            this.boundingBox.offset(p_70091_1_, 0.0, 0.0);
            if (!this.field_70135_K && var10 != p_70091_1_) {
                p_70091_5_ = 0.0;
                p_70091_3_ = 0.0;
                p_70091_1_ = 0.0;
            }
            for (int var19 = 0; var19 < var16.size(); ++var19) {
                p_70091_5_ = var16.get(var19).calculateZOffset(this.boundingBox, p_70091_5_);
            }
            this.boundingBox.offset(0.0, 0.0, p_70091_5_);
            if (!this.field_70135_K && var12 != p_70091_5_) {
                p_70091_5_ = 0.0;
                p_70091_3_ = 0.0;
                p_70091_1_ = 0.0;
            }
            if (this.stepHeight > 0.0f && var18 && (var14 || this.ySize < 0.05f) && (var10 != p_70091_1_ || var12 != p_70091_5_)) {
                final double var20 = p_70091_1_;
                final double var21 = p_70091_3_;
                final double var22 = p_70091_5_;
                p_70091_1_ = var10;
                p_70091_3_ = this.stepHeight;
                p_70091_5_ = var12;
                final AxisAlignedBB var23 = this.boundingBox.copy();
                this.boundingBox.setBB(var13);
                var16 = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(var10, p_70091_3_, var12));
                for (int var24 = 0; var24 < var16.size(); ++var24) {
                    p_70091_3_ = var16.get(var24).calculateYOffset(this.boundingBox, p_70091_3_);
                }
                this.boundingBox.offset(0.0, p_70091_3_, 0.0);
                if (!this.field_70135_K && var11 != p_70091_3_) {
                    p_70091_5_ = 0.0;
                    p_70091_3_ = 0.0;
                    p_70091_1_ = 0.0;
                }
                for (int var24 = 0; var24 < var16.size(); ++var24) {
                    p_70091_1_ = var16.get(var24).calculateXOffset(this.boundingBox, p_70091_1_);
                }
                this.boundingBox.offset(p_70091_1_, 0.0, 0.0);
                if (!this.field_70135_K && var10 != p_70091_1_) {
                    p_70091_5_ = 0.0;
                    p_70091_3_ = 0.0;
                    p_70091_1_ = 0.0;
                }
                for (int var24 = 0; var24 < var16.size(); ++var24) {
                    p_70091_5_ = var16.get(var24).calculateZOffset(this.boundingBox, p_70091_5_);
                }
                this.boundingBox.offset(0.0, 0.0, p_70091_5_);
                if (!this.field_70135_K && var12 != p_70091_5_) {
                    p_70091_5_ = 0.0;
                    p_70091_3_ = 0.0;
                    p_70091_1_ = 0.0;
                }
                if (!this.field_70135_K && var11 != p_70091_3_) {
                    p_70091_5_ = 0.0;
                    p_70091_3_ = 0.0;
                    p_70091_1_ = 0.0;
                }
                else {
                    p_70091_3_ = -this.stepHeight;
                    for (int var24 = 0; var24 < var16.size(); ++var24) {
                        p_70091_3_ = var16.get(var24).calculateYOffset(this.boundingBox, p_70091_3_);
                    }
                    this.boundingBox.offset(0.0, p_70091_3_, 0.0);
                }
                if (var20 * var20 + var22 * var22 >= p_70091_1_ * p_70091_1_ + p_70091_5_ * p_70091_5_) {
                    p_70091_1_ = var20;
                    p_70091_3_ = var21;
                    p_70091_5_ = var22;
                    this.boundingBox.setBB(var23);
                }
            }
            this.worldObj.theProfiler.endSection();
            this.worldObj.theProfiler.startSection("rest");
            this.posX = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0;
            this.posY = this.boundingBox.minY + this.yOffset - this.ySize;
            this.posZ = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0;
            this.isCollidedHorizontally = (var10 != p_70091_1_ || var12 != p_70091_5_);
            this.isCollidedVertically = (var11 != p_70091_3_);
            this.onGround = (var11 != p_70091_3_ && var11 < 0.0);
            this.isCollided = (this.isCollidedHorizontally || this.isCollidedVertically);
            this.updateFallState(p_70091_3_, this.onGround);
            if (var10 != p_70091_1_) {
                this.motionX = 0.0;
            }
            if (var11 != p_70091_3_) {
                this.motionY = 0.0;
            }
            if (var12 != p_70091_5_) {
                this.motionZ = 0.0;
            }
            final double var20 = this.posX - var7;
            double var21 = this.posY - var8;
            final double var22 = this.posZ - var9;
            if (this.canTriggerWalking() && !var14 && this.ridingEntity == null) {
                final int var25 = MathHelper.floor_double(this.posX);
                final int var24 = MathHelper.floor_double(this.posY - 0.20000000298023224 - this.yOffset);
                final int var26 = MathHelper.floor_double(this.posZ);
                Block var27 = this.worldObj.getBlock(var25, var24, var26);
                final int var28 = this.worldObj.getBlock(var25, var24 - 1, var26).getRenderType();
                if (var28 == 11 || var28 == 32 || var28 == 21) {
                    var27 = this.worldObj.getBlock(var25, var24 - 1, var26);
                }
                if (var27 != Blocks.ladder) {
                    var21 = 0.0;
                }
                this.distanceWalkedModified += (float)(MathHelper.sqrt_double(var20 * var20 + var22 * var22) * 0.6);
                this.distanceWalkedOnStepModified += (float)(MathHelper.sqrt_double(var20 * var20 + var21 * var21 + var22 * var22) * 0.6);
                if (this.distanceWalkedOnStepModified > this.nextStepDistance && var27.getMaterial() != Material.air) {
                    this.nextStepDistance = (int)this.distanceWalkedOnStepModified + 1;
                    if (this.isInWater()) {
                        float var29 = MathHelper.sqrt_double(this.motionX * this.motionX * 0.20000000298023224 + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.20000000298023224) * 0.35f;
                        if (var29 > 1.0f) {
                            var29 = 1.0f;
                        }
                        this.playSound(this.getSwimSound(), var29, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                    }
                    this.func_145780_a(var25, var24, var26, var27);
                    var27.onEntityWalking(this.worldObj, var25, var24, var26, this);
                }
            }
            try {
                this.func_145775_I();
            }
            catch (Throwable var31) {
                final CrashReport var30 = CrashReport.makeCrashReport(var31, "Checking entity block collision");
                final CrashReportCategory var32 = var30.makeCategory("Entity being checked for collision");
                this.addEntityCrashInfo(var32);
                throw new ReportedException(var30);
            }
            final boolean var33 = this.isWet();
            if (this.worldObj.func_147470_e(this.boundingBox.contract(0.001, 0.001, 0.001))) {
                this.dealFireDamage(1);
                if (!var33) {
                    ++this.fire;
                    if (this.fire == 0) {
                        this.setFire(8);
                    }
                }
            }
            else if (this.fire <= 0) {
                this.fire = -this.fireResistance;
            }
            if (var33 && this.fire > 0) {
                this.playSound("random.fizz", 0.7f, 1.6f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                this.fire = -this.fireResistance;
            }
            this.worldObj.theProfiler.endSection();
        }
    }
    
    protected String getSwimSound() {
        return "game.neutral.swim";
    }
    
    protected void func_145775_I() {
        final int var1 = MathHelper.floor_double(this.boundingBox.minX + 0.001);
        final int var2 = MathHelper.floor_double(this.boundingBox.minY + 0.001);
        final int var3 = MathHelper.floor_double(this.boundingBox.minZ + 0.001);
        final int var4 = MathHelper.floor_double(this.boundingBox.maxX - 0.001);
        final int var5 = MathHelper.floor_double(this.boundingBox.maxY - 0.001);
        final int var6 = MathHelper.floor_double(this.boundingBox.maxZ - 0.001);
        if (this.worldObj.checkChunksExist(var1, var2, var3, var4, var5, var6)) {
            for (int var7 = var1; var7 <= var4; ++var7) {
                for (int var8 = var2; var8 <= var5; ++var8) {
                    for (int var9 = var3; var9 <= var6; ++var9) {
                        final Block var10 = this.worldObj.getBlock(var7, var8, var9);
                        try {
                            var10.onEntityCollidedWithBlock(this.worldObj, var7, var8, var9, this);
                        }
                        catch (Throwable var12) {
                            final CrashReport var11 = CrashReport.makeCrashReport(var12, "Colliding entity with block");
                            final CrashReportCategory var13 = var11.makeCategory("Block being collided with");
                            CrashReportCategory.func_147153_a(var13, var7, var8, var9, var10, this.worldObj.getBlockMetadata(var7, var8, var9));
                            throw new ReportedException(var11);
                        }
                    }
                }
            }
        }
    }
    
    protected void func_145780_a(final int p_145780_1_, final int p_145780_2_, final int p_145780_3_, final Block p_145780_4_) {
        Block.SoundType var5 = p_145780_4_.stepSound;
        if (this.worldObj.getBlock(p_145780_1_, p_145780_2_ + 1, p_145780_3_) == Blocks.snow_layer) {
            var5 = Blocks.snow_layer.stepSound;
            this.playSound(var5.func_150498_e(), var5.func_150497_c() * 0.15f, var5.func_150494_d());
        }
        else if (!p_145780_4_.getMaterial().isLiquid()) {
            this.playSound(var5.func_150498_e(), var5.func_150497_c() * 0.15f, var5.func_150494_d());
        }
    }
    
    public void playSound(final String p_85030_1_, final float p_85030_2_, final float p_85030_3_) {
        this.worldObj.playSoundAtEntity(this, p_85030_1_, p_85030_2_, p_85030_3_);
    }
    
    protected boolean canTriggerWalking() {
        return true;
    }
    
    protected void updateFallState(final double p_70064_1_, final boolean p_70064_3_) {
        if (p_70064_3_) {
            if (this.fallDistance > 0.0f) {
                this.fall(this.fallDistance);
                this.fallDistance = 0.0f;
            }
        }
        else if (p_70064_1_ < 0.0) {
            this.fallDistance -= (float)p_70064_1_;
        }
    }
    
    public AxisAlignedBB getBoundingBox() {
        return null;
    }
    
    protected void dealFireDamage(final int p_70081_1_) {
        if (!this.isImmuneToFire) {
            this.attackEntityFrom(DamageSource.inFire, (float)p_70081_1_);
        }
    }
    
    public final boolean isImmuneToFire() {
        return this.isImmuneToFire;
    }
    
    protected void fall(final float p_70069_1_) {
        if (this.riddenByEntity != null) {
            this.riddenByEntity.fall(p_70069_1_);
        }
    }
    
    public boolean isWet() {
        return this.inWater || this.worldObj.canLightningStrikeAt(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) || this.worldObj.canLightningStrikeAt(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY + this.height), MathHelper.floor_double(this.posZ));
    }
    
    public boolean isInWater() {
        return this.inWater;
    }
    
    public boolean handleWaterMovement() {
        if (this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0, -0.4000000059604645, 0.0).contract(0.001, 0.001, 0.001), Material.water, this)) {
            if (!this.inWater && !this.firstUpdate) {
                float var1 = MathHelper.sqrt_double(this.motionX * this.motionX * 0.20000000298023224 + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.20000000298023224) * 0.2f;
                if (var1 > 1.0f) {
                    var1 = 1.0f;
                }
                this.playSound(this.getSplashSound(), var1, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                final float var2 = (float)MathHelper.floor_double(this.boundingBox.minY);
                for (int var3 = 0; var3 < 1.0f + this.width * 20.0f; ++var3) {
                    final float var4 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                    final float var5 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                    this.worldObj.spawnParticle("bubble", this.posX + var4, var2 + 1.0f, this.posZ + var5, this.motionX, this.motionY - this.rand.nextFloat() * 0.2f, this.motionZ);
                }
                for (int var3 = 0; var3 < 1.0f + this.width * 20.0f; ++var3) {
                    final float var4 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                    final float var5 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                    this.worldObj.spawnParticle("splash", this.posX + var4, var2 + 1.0f, this.posZ + var5, this.motionX, this.motionY, this.motionZ);
                }
            }
            this.fallDistance = 0.0f;
            this.inWater = true;
            this.fire = 0;
        }
        else {
            this.inWater = false;
        }
        return this.inWater;
    }
    
    protected String getSplashSound() {
        return "game.neutral.swim.splash";
    }
    
    public boolean isInsideOfMaterial(final Material p_70055_1_) {
        final double var2 = this.posY + this.getEyeHeight();
        final int var3 = MathHelper.floor_double(this.posX);
        final int var4 = MathHelper.floor_float((float)MathHelper.floor_double(var2));
        final int var5 = MathHelper.floor_double(this.posZ);
        final Block var6 = this.worldObj.getBlock(var3, var4, var5);
        if (var6.getMaterial() == p_70055_1_) {
            final float var7 = BlockLiquid.func_149801_b(this.worldObj.getBlockMetadata(var3, var4, var5)) - 0.11111111f;
            final float var8 = var4 + 1 - var7;
            return var2 < var8;
        }
        return false;
    }
    
    public float getEyeHeight() {
        return 0.0f;
    }
    
    public boolean handleLavaMovement() {
        return this.worldObj.isMaterialInBB(this.boundingBox.expand(-0.10000000149011612, -0.4000000059604645, -0.10000000149011612), Material.lava);
    }
    
    public void moveFlying(float p_70060_1_, float p_70060_2_, final float p_70060_3_) {
        float var4 = p_70060_1_ * p_70060_1_ + p_70060_2_ * p_70060_2_;
        if (var4 >= 1.0E-4f) {
            var4 = MathHelper.sqrt_float(var4);
            if (var4 < 1.0f) {
                var4 = 1.0f;
            }
            var4 = p_70060_3_ / var4;
            p_70060_1_ *= var4;
            p_70060_2_ *= var4;
            final float var5 = MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f);
            final float var6 = MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f);
            this.motionX += p_70060_1_ * var6 - p_70060_2_ * var5;
            this.motionZ += p_70060_2_ * var6 + p_70060_1_ * var5;
        }
    }
    
    public int getBrightnessForRender(final float p_70070_1_) {
        final int var2 = MathHelper.floor_double(this.posX);
        final int var3 = MathHelper.floor_double(this.posZ);
        if (this.worldObj.blockExists(var2, 0, var3)) {
            final double var4 = (this.boundingBox.maxY - this.boundingBox.minY) * 0.66;
            final int var5 = MathHelper.floor_double(this.posY - this.yOffset + var4);
            return this.worldObj.getLightBrightnessForSkyBlocks(var2, var5, var3, 0);
        }
        return 0;
    }
    
    public float getBrightness(final float p_70013_1_) {
        final int var2 = MathHelper.floor_double(this.posX);
        final int var3 = MathHelper.floor_double(this.posZ);
        if (this.worldObj.blockExists(var2, 0, var3)) {
            final double var4 = (this.boundingBox.maxY - this.boundingBox.minY) * 0.66;
            final int var5 = MathHelper.floor_double(this.posY - this.yOffset + var4);
            return this.worldObj.getLightBrightness(var2, var5, var3);
        }
        return 0.0f;
    }
    
    public void setWorld(final World p_70029_1_) {
        this.worldObj = p_70029_1_;
    }
    
    public void setPositionAndRotation(final double p_70080_1_, final double p_70080_3_, final double p_70080_5_, final float p_70080_7_, final float p_70080_8_) {
        this.posX = p_70080_1_;
        this.prevPosX = p_70080_1_;
        this.posY = p_70080_3_;
        this.prevPosY = p_70080_3_;
        this.posZ = p_70080_5_;
        this.prevPosZ = p_70080_5_;
        this.rotationYaw = p_70080_7_;
        this.prevRotationYaw = p_70080_7_;
        this.rotationPitch = p_70080_8_;
        this.prevRotationPitch = p_70080_8_;
        this.ySize = 0.0f;
        final double var9 = this.prevRotationYaw - p_70080_7_;
        if (var9 < -180.0) {
            this.prevRotationYaw += 360.0f;
        }
        if (var9 >= 180.0) {
            this.prevRotationYaw -= 360.0f;
        }
        this.setPosition(this.posX, this.posY, this.posZ);
        this.setRotation(p_70080_7_, p_70080_8_);
    }
    
    public void setLocationAndAngles(final double p_70012_1_, final double p_70012_3_, final double p_70012_5_, final float p_70012_7_, final float p_70012_8_) {
        this.posX = p_70012_1_;
        this.prevPosX = p_70012_1_;
        this.lastTickPosX = p_70012_1_;
        final double lastTickPosY = p_70012_3_ + this.yOffset;
        this.posY = lastTickPosY;
        this.prevPosY = lastTickPosY;
        this.lastTickPosY = lastTickPosY;
        this.posZ = p_70012_5_;
        this.prevPosZ = p_70012_5_;
        this.lastTickPosZ = p_70012_5_;
        this.rotationYaw = p_70012_7_;
        this.rotationPitch = p_70012_8_;
        this.setPosition(this.posX, this.posY, this.posZ);
    }
    
    public float getDistanceToEntity(final Entity p_70032_1_) {
        final float var2 = (float)(this.posX - p_70032_1_.posX);
        final float var3 = (float)(this.posY - p_70032_1_.posY);
        final float var4 = (float)(this.posZ - p_70032_1_.posZ);
        return MathHelper.sqrt_float(var2 * var2 + var3 * var3 + var4 * var4);
    }
    
    public double getDistanceSq(final double p_70092_1_, final double p_70092_3_, final double p_70092_5_) {
        final double var7 = this.posX - p_70092_1_;
        final double var8 = this.posY - p_70092_3_;
        final double var9 = this.posZ - p_70092_5_;
        return var7 * var7 + var8 * var8 + var9 * var9;
    }
    
    public double getDistance(final double p_70011_1_, final double p_70011_3_, final double p_70011_5_) {
        final double var7 = this.posX - p_70011_1_;
        final double var8 = this.posY - p_70011_3_;
        final double var9 = this.posZ - p_70011_5_;
        return MathHelper.sqrt_double(var7 * var7 + var8 * var8 + var9 * var9);
    }
    
    public double getDistanceSqToEntity(final Entity p_70068_1_) {
        final double var2 = this.posX - p_70068_1_.posX;
        final double var3 = this.posY - p_70068_1_.posY;
        final double var4 = this.posZ - p_70068_1_.posZ;
        return var2 * var2 + var3 * var3 + var4 * var4;
    }
    
    public void onCollideWithPlayer(final EntityPlayer p_70100_1_) {
    }
    
    public void applyEntityCollision(final Entity p_70108_1_) {
        if (p_70108_1_.riddenByEntity != this && p_70108_1_.ridingEntity != this) {
            double var2 = p_70108_1_.posX - this.posX;
            double var3 = p_70108_1_.posZ - this.posZ;
            double var4 = MathHelper.abs_max(var2, var3);
            if (var4 >= 0.009999999776482582) {
                var4 = MathHelper.sqrt_double(var4);
                var2 /= var4;
                var3 /= var4;
                double var5 = 1.0 / var4;
                if (var5 > 1.0) {
                    var5 = 1.0;
                }
                var2 *= var5;
                var3 *= var5;
                var2 *= 0.05000000074505806;
                var3 *= 0.05000000074505806;
                var2 *= 1.0f - this.entityCollisionReduction;
                var3 *= 1.0f - this.entityCollisionReduction;
                this.addVelocity(-var2, 0.0, -var3);
                p_70108_1_.addVelocity(var2, 0.0, var3);
            }
        }
    }
    
    public void addVelocity(final double p_70024_1_, final double p_70024_3_, final double p_70024_5_) {
        this.motionX += p_70024_1_;
        this.motionY += p_70024_3_;
        this.motionZ += p_70024_5_;
        this.isAirBorne = true;
    }
    
    protected void setBeenAttacked() {
        this.velocityChanged = true;
    }
    
    public boolean attackEntityFrom(final DamageSource p_70097_1_, final float p_70097_2_) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        this.setBeenAttacked();
        return false;
    }
    
    public boolean canBeCollidedWith() {
        return false;
    }
    
    public boolean canBePushed() {
        return false;
    }
    
    public void addToPlayerScore(final Entity p_70084_1_, final int p_70084_2_) {
    }
    
    public boolean isInRangeToRender3d(final double p_145770_1_, final double p_145770_3_, final double p_145770_5_) {
        final double var7 = this.posX - p_145770_1_;
        final double var8 = this.posY - p_145770_3_;
        final double var9 = this.posZ - p_145770_5_;
        final double var10 = var7 * var7 + var8 * var8 + var9 * var9;
        return this.isInRangeToRenderDist(var10);
    }
    
    public boolean isInRangeToRenderDist(final double p_70112_1_) {
        double var3 = this.boundingBox.getAverageEdgeLength();
        var3 *= 64.0 * this.renderDistanceWeight;
        return p_70112_1_ < var3 * var3;
    }
    
    public boolean writeMountToNBT(final NBTTagCompound p_98035_1_) {
        final String var2 = this.getEntityString();
        if (!this.isDead && var2 != null) {
            p_98035_1_.setString("id", var2);
            this.writeToNBT(p_98035_1_);
            return true;
        }
        return false;
    }
    
    public boolean writeToNBTOptional(final NBTTagCompound p_70039_1_) {
        final String var2 = this.getEntityString();
        if (!this.isDead && var2 != null && this.riddenByEntity == null) {
            p_70039_1_.setString("id", var2);
            this.writeToNBT(p_70039_1_);
            return true;
        }
        return false;
    }
    
    public void writeToNBT(final NBTTagCompound p_70109_1_) {
        try {
            p_70109_1_.setTag("Pos", this.newDoubleNBTList(this.posX, this.posY + this.ySize, this.posZ));
            p_70109_1_.setTag("Motion", this.newDoubleNBTList(this.motionX, this.motionY, this.motionZ));
            p_70109_1_.setTag("Rotation", this.newFloatNBTList(this.rotationYaw, this.rotationPitch));
            p_70109_1_.setFloat("FallDistance", this.fallDistance);
            p_70109_1_.setShort("Fire", (short)this.fire);
            p_70109_1_.setShort("Air", (short)this.getAir());
            p_70109_1_.setBoolean("OnGround", this.onGround);
            p_70109_1_.setInteger("Dimension", this.dimension);
            p_70109_1_.setBoolean("Invulnerable", this.invulnerable);
            p_70109_1_.setInteger("PortalCooldown", this.timeUntilPortal);
            p_70109_1_.setLong("UUIDMost", this.getUniqueID().getMostSignificantBits());
            p_70109_1_.setLong("UUIDLeast", this.getUniqueID().getLeastSignificantBits());
            this.writeEntityToNBT(p_70109_1_);
            if (this.ridingEntity != null) {
                final NBTTagCompound var2 = new NBTTagCompound();
                if (this.ridingEntity.writeMountToNBT(var2)) {
                    p_70109_1_.setTag("Riding", var2);
                }
            }
        }
        catch (Throwable var4) {
            final CrashReport var3 = CrashReport.makeCrashReport(var4, "Saving entity NBT");
            final CrashReportCategory var5 = var3.makeCategory("Entity being saved");
            this.addEntityCrashInfo(var5);
            throw new ReportedException(var3);
        }
    }
    
    public void readFromNBT(final NBTTagCompound p_70020_1_) {
        try {
            final NBTTagList var2 = p_70020_1_.getTagList("Pos", 6);
            final NBTTagList var3 = p_70020_1_.getTagList("Motion", 6);
            final NBTTagList var4 = p_70020_1_.getTagList("Rotation", 5);
            this.motionX = var3.func_150309_d(0);
            this.motionY = var3.func_150309_d(1);
            this.motionZ = var3.func_150309_d(2);
            if (Math.abs(this.motionX) > 10.0) {
                this.motionX = 0.0;
            }
            if (Math.abs(this.motionY) > 10.0) {
                this.motionY = 0.0;
            }
            if (Math.abs(this.motionZ) > 10.0) {
                this.motionZ = 0.0;
            }
            final double func_150309_d = var2.func_150309_d(0);
            this.posX = func_150309_d;
            this.lastTickPosX = func_150309_d;
            this.prevPosX = func_150309_d;
            final double func_150309_d2 = var2.func_150309_d(1);
            this.posY = func_150309_d2;
            this.lastTickPosY = func_150309_d2;
            this.prevPosY = func_150309_d2;
            final double func_150309_d3 = var2.func_150309_d(2);
            this.posZ = func_150309_d3;
            this.lastTickPosZ = func_150309_d3;
            this.prevPosZ = func_150309_d3;
            final float func_150308_e = var4.func_150308_e(0);
            this.rotationYaw = func_150308_e;
            this.prevRotationYaw = func_150308_e;
            final float func_150308_e2 = var4.func_150308_e(1);
            this.rotationPitch = func_150308_e2;
            this.prevRotationPitch = func_150308_e2;
            this.fallDistance = p_70020_1_.getFloat("FallDistance");
            this.fire = p_70020_1_.getShort("Fire");
            this.setAir(p_70020_1_.getShort("Air"));
            this.onGround = p_70020_1_.getBoolean("OnGround");
            this.dimension = p_70020_1_.getInteger("Dimension");
            this.invulnerable = p_70020_1_.getBoolean("Invulnerable");
            this.timeUntilPortal = p_70020_1_.getInteger("PortalCooldown");
            if (p_70020_1_.func_150297_b("UUIDMost", 4) && p_70020_1_.func_150297_b("UUIDLeast", 4)) {
                this.entityUniqueID = new UUID(p_70020_1_.getLong("UUIDMost"), p_70020_1_.getLong("UUIDLeast"));
            }
            this.setPosition(this.posX, this.posY, this.posZ);
            this.setRotation(this.rotationYaw, this.rotationPitch);
            this.readEntityFromNBT(p_70020_1_);
            if (this.shouldSetPosAfterLoading()) {
                this.setPosition(this.posX, this.posY, this.posZ);
            }
        }
        catch (Throwable var6) {
            final CrashReport var5 = CrashReport.makeCrashReport(var6, "Loading entity NBT");
            final CrashReportCategory var7 = var5.makeCategory("Entity being loaded");
            this.addEntityCrashInfo(var7);
            throw new ReportedException(var5);
        }
    }
    
    protected boolean shouldSetPosAfterLoading() {
        return true;
    }
    
    protected final String getEntityString() {
        return EntityList.getEntityString(this);
    }
    
    protected abstract void readEntityFromNBT(final NBTTagCompound p0);
    
    protected abstract void writeEntityToNBT(final NBTTagCompound p0);
    
    public void onChunkLoad() {
    }
    
    protected NBTTagList newDoubleNBTList(final double... p_70087_1_) {
        final NBTTagList var2 = new NBTTagList();
        final double[] var3 = p_70087_1_;
        for (int var4 = p_70087_1_.length, var5 = 0; var5 < var4; ++var5) {
            final double var6 = var3[var5];
            var2.appendTag(new NBTTagDouble(var6));
        }
        return var2;
    }
    
    protected NBTTagList newFloatNBTList(final float... p_70049_1_) {
        final NBTTagList var2 = new NBTTagList();
        final float[] var3 = p_70049_1_;
        for (int var4 = p_70049_1_.length, var5 = 0; var5 < var4; ++var5) {
            final float var6 = var3[var5];
            var2.appendTag(new NBTTagFloat(var6));
        }
        return var2;
    }
    
    public float getShadowSize() {
        return this.height / 2.0f;
    }
    
    public EntityItem func_145779_a(final Item p_145779_1_, final int p_145779_2_) {
        return this.func_145778_a(p_145779_1_, p_145779_2_, 0.0f);
    }
    
    public EntityItem func_145778_a(final Item p_145778_1_, final int p_145778_2_, final float p_145778_3_) {
        return this.entityDropItem(new ItemStack(p_145778_1_, p_145778_2_, 0), p_145778_3_);
    }
    
    public EntityItem entityDropItem(final ItemStack p_70099_1_, final float p_70099_2_) {
        if (p_70099_1_.stackSize != 0 && p_70099_1_.getItem() != null) {
            final EntityItem var3 = new EntityItem(this.worldObj, this.posX, this.posY + p_70099_2_, this.posZ, p_70099_1_);
            var3.delayBeforeCanPickup = 10;
            this.worldObj.spawnEntityInWorld(var3);
            return var3;
        }
        return null;
    }
    
    public boolean isEntityAlive() {
        return !this.isDead;
    }
    
    public boolean isEntityInsideOpaqueBlock() {
        for (int var1 = 0; var1 < 8; ++var1) {
            final float var2 = ((var1 >> 0) % 2 - 0.5f) * this.width * 0.8f;
            final float var3 = ((var1 >> 1) % 2 - 0.5f) * 0.1f;
            final float var4 = ((var1 >> 2) % 2 - 0.5f) * this.width * 0.8f;
            final int var5 = MathHelper.floor_double(this.posX + var2);
            final int var6 = MathHelper.floor_double(this.posY + this.getEyeHeight() + var3);
            final int var7 = MathHelper.floor_double(this.posZ + var4);
            if (this.worldObj.getBlock(var5, var6, var7).isNormalCube()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean interactFirst(final EntityPlayer p_130002_1_) {
        return false;
    }
    
    public AxisAlignedBB getCollisionBox(final Entity p_70114_1_) {
        return null;
    }
    
    public void updateRidden() {
        if (this.ridingEntity.isDead) {
            this.ridingEntity = null;
        }
        else {
            this.motionX = 0.0;
            this.motionY = 0.0;
            this.motionZ = 0.0;
            this.onUpdate();
            if (this.ridingEntity != null) {
                this.ridingEntity.updateRiderPosition();
                this.entityRiderYawDelta += this.ridingEntity.rotationYaw - this.ridingEntity.prevRotationYaw;
                this.entityRiderPitchDelta += this.ridingEntity.rotationPitch - this.ridingEntity.prevRotationPitch;
                while (this.entityRiderYawDelta >= 180.0) {
                    this.entityRiderYawDelta -= 360.0;
                }
                while (this.entityRiderYawDelta < -180.0) {
                    this.entityRiderYawDelta += 360.0;
                }
                while (this.entityRiderPitchDelta >= 180.0) {
                    this.entityRiderPitchDelta -= 360.0;
                }
                while (this.entityRiderPitchDelta < -180.0) {
                    this.entityRiderPitchDelta += 360.0;
                }
                double var1 = this.entityRiderYawDelta * 0.5;
                double var2 = this.entityRiderPitchDelta * 0.5;
                final float var3 = 10.0f;
                if (var1 > var3) {
                    var1 = var3;
                }
                if (var1 < -var3) {
                    var1 = -var3;
                }
                if (var2 > var3) {
                    var2 = var3;
                }
                if (var2 < -var3) {
                    var2 = -var3;
                }
                this.entityRiderYawDelta -= var1;
                this.entityRiderPitchDelta -= var2;
            }
        }
    }
    
    public void updateRiderPosition() {
        if (this.riddenByEntity != null) {
            this.riddenByEntity.setPosition(this.posX, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ);
        }
    }
    
    public double getYOffset() {
        return this.yOffset;
    }
    
    public double getMountedYOffset() {
        return this.height * 0.75;
    }
    
    public void mountEntity(final Entity p_70078_1_) {
        this.entityRiderPitchDelta = 0.0;
        this.entityRiderYawDelta = 0.0;
        if (p_70078_1_ == null) {
            if (this.ridingEntity != null) {
                this.setLocationAndAngles(this.ridingEntity.posX, this.ridingEntity.boundingBox.minY + this.ridingEntity.height, this.ridingEntity.posZ, this.rotationYaw, this.rotationPitch);
                this.ridingEntity.riddenByEntity = null;
            }
            this.ridingEntity = null;
        }
        else {
            if (this.ridingEntity != null) {
                this.ridingEntity.riddenByEntity = null;
            }
            if (p_70078_1_ != null) {
                for (Entity var2 = p_70078_1_.ridingEntity; var2 != null; var2 = var2.ridingEntity) {
                    if (var2 == this) {
                        return;
                    }
                }
            }
            this.ridingEntity = p_70078_1_;
            p_70078_1_.riddenByEntity = this;
        }
    }
    
    public void setPositionAndRotation2(final double p_70056_1_, double p_70056_3_, final double p_70056_5_, final float p_70056_7_, final float p_70056_8_, final int p_70056_9_) {
        this.setPosition(p_70056_1_, p_70056_3_, p_70056_5_);
        this.setRotation(p_70056_7_, p_70056_8_);
        final List var10 = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.contract(0.03125, 0.0, 0.03125));
        if (!var10.isEmpty()) {
            double var11 = 0.0;
            for (int var12 = 0; var12 < var10.size(); ++var12) {
                final AxisAlignedBB var13 = var10.get(var12);
                if (var13.maxY > var11) {
                    var11 = var13.maxY;
                }
            }
            p_70056_3_ += var11 - this.boundingBox.minY;
            this.setPosition(p_70056_1_, p_70056_3_, p_70056_5_);
        }
    }
    
    public float getCollisionBorderSize() {
        return 0.1f;
    }
    
    public Vec3 getLookVec() {
        return null;
    }
    
    public void setInPortal() {
        if (this.timeUntilPortal > 0) {
            this.timeUntilPortal = this.getPortalCooldown();
        }
        else {
            final double var1 = this.prevPosX - this.posX;
            final double var2 = this.prevPosZ - this.posZ;
            if (!this.worldObj.isClient && !this.inPortal) {
                this.teleportDirection = Direction.getMovementDirection(var1, var2);
            }
            this.inPortal = true;
        }
    }
    
    public int getPortalCooldown() {
        return 300;
    }
    
    public void setVelocity(final double p_70016_1_, final double p_70016_3_, final double p_70016_5_) {
        this.motionX = p_70016_1_;
        this.motionY = p_70016_3_;
        this.motionZ = p_70016_5_;
    }
    
    public void handleHealthUpdate(final byte p_70103_1_) {
    }
    
    public void performHurtAnimation() {
    }
    
    public ItemStack[] getLastActiveItems() {
        return null;
    }
    
    public void setCurrentItemOrArmor(final int p_70062_1_, final ItemStack p_70062_2_) {
    }
    
    public boolean isBurning() {
        final boolean var1 = this.worldObj != null && this.worldObj.isClient;
        return !this.isImmuneToFire && (this.fire > 0 || (var1 && this.getFlag(0)));
    }
    
    public boolean isRiding() {
        return this.ridingEntity != null;
    }
    
    public boolean isSneaking() {
        return this.getFlag(1);
    }
    
    public void setSneaking(final boolean p_70095_1_) {
        this.setFlag(1, p_70095_1_);
    }
    
    public boolean isSprinting() {
        return this.getFlag(3);
    }
    
    public void setSprinting(final boolean p_70031_1_) {
        this.setFlag(3, p_70031_1_);
    }
    
    public boolean isInvisible() {
        return this.getFlag(5);
    }
    
    public boolean isInvisibleToPlayer(final EntityPlayer p_98034_1_) {
        return this.isInvisible();
    }
    
    public void setInvisible(final boolean p_82142_1_) {
        this.setFlag(5, p_82142_1_);
    }
    
    public boolean isEating() {
        return this.getFlag(4);
    }
    
    public void setEating(final boolean p_70019_1_) {
        this.setFlag(4, p_70019_1_);
    }
    
    protected boolean getFlag(final int p_70083_1_) {
        return (this.dataWatcher.getWatchableObjectByte(0) & 1 << p_70083_1_) != 0x0;
    }
    
    protected void setFlag(final int p_70052_1_, final boolean p_70052_2_) {
        final byte var3 = this.dataWatcher.getWatchableObjectByte(0);
        if (p_70052_2_) {
            this.dataWatcher.updateObject(0, (byte)(var3 | 1 << p_70052_1_));
        }
        else {
            this.dataWatcher.updateObject(0, (byte)(var3 & ~(1 << p_70052_1_)));
        }
    }
    
    public int getAir() {
        return this.dataWatcher.getWatchableObjectShort(1);
    }
    
    public void setAir(final int p_70050_1_) {
        this.dataWatcher.updateObject(1, (short)p_70050_1_);
    }
    
    public void onStruckByLightning(final EntityLightningBolt p_70077_1_) {
        this.dealFireDamage(5);
        ++this.fire;
        if (this.fire == 0) {
            this.setFire(8);
        }
    }
    
    public void onKillEntity(final EntityLivingBase p_70074_1_) {
    }
    
    protected boolean func_145771_j(final double p_145771_1_, final double p_145771_3_, final double p_145771_5_) {
        final int var7 = MathHelper.floor_double(p_145771_1_);
        final int var8 = MathHelper.floor_double(p_145771_3_);
        final int var9 = MathHelper.floor_double(p_145771_5_);
        final double var10 = p_145771_1_ - var7;
        final double var11 = p_145771_3_ - var8;
        final double var12 = p_145771_5_ - var9;
        final List var13 = this.worldObj.func_147461_a(this.boundingBox);
        if (var13.isEmpty() && !this.worldObj.func_147469_q(var7, var8, var9)) {
            return false;
        }
        final boolean var14 = !this.worldObj.func_147469_q(var7 - 1, var8, var9);
        final boolean var15 = !this.worldObj.func_147469_q(var7 + 1, var8, var9);
        final boolean var16 = !this.worldObj.func_147469_q(var7, var8 - 1, var9);
        final boolean var17 = !this.worldObj.func_147469_q(var7, var8 + 1, var9);
        final boolean var18 = !this.worldObj.func_147469_q(var7, var8, var9 - 1);
        final boolean var19 = !this.worldObj.func_147469_q(var7, var8, var9 + 1);
        byte var20 = 3;
        double var21 = 9999.0;
        if (var14 && var10 < var21) {
            var21 = var10;
            var20 = 0;
        }
        if (var15 && 1.0 - var10 < var21) {
            var21 = 1.0 - var10;
            var20 = 1;
        }
        if (var17 && 1.0 - var11 < var21) {
            var21 = 1.0 - var11;
            var20 = 3;
        }
        if (var18 && var12 < var21) {
            var21 = var12;
            var20 = 4;
        }
        if (var19 && 1.0 - var12 < var21) {
            var21 = 1.0 - var12;
            var20 = 5;
        }
        final float var22 = this.rand.nextFloat() * 0.2f + 0.1f;
        if (var20 == 0) {
            this.motionX = -var22;
        }
        if (var20 == 1) {
            this.motionX = var22;
        }
        if (var20 == 2) {
            this.motionY = -var22;
        }
        if (var20 == 3) {
            this.motionY = var22;
        }
        if (var20 == 4) {
            this.motionZ = -var22;
        }
        if (var20 == 5) {
            this.motionZ = var22;
        }
        return true;
    }
    
    public void setInWeb() {
        this.isInWeb = true;
        this.fallDistance = 0.0f;
    }
    
    public String getCommandSenderName() {
        String var1 = EntityList.getEntityString(this);
        if (var1 == null) {
            var1 = "generic";
        }
        return StatCollector.translateToLocal("entity." + var1 + ".name");
    }
    
    public Entity[] getParts() {
        return null;
    }
    
    public boolean isEntityEqual(final Entity p_70028_1_) {
        return this == p_70028_1_;
    }
    
    public float getRotationYawHead() {
        return 0.0f;
    }
    
    public void setRotationYawHead(final float p_70034_1_) {
    }
    
    public boolean canAttackWithItem() {
        return true;
    }
    
    public boolean hitByEntity(final Entity p_85031_1_) {
        return false;
    }
    
    @Override
    public String toString() {
        return String.format("%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]", this.getClass().getSimpleName(), this.getCommandSenderName(), this.field_145783_c, (this.worldObj == null) ? "~NULL~" : this.worldObj.getWorldInfo().getWorldName(), this.posX, this.posY, this.posZ);
    }
    
    public boolean isEntityInvulnerable() {
        return this.invulnerable;
    }
    
    public void copyLocationAndAnglesFrom(final Entity p_82149_1_) {
        this.setLocationAndAngles(p_82149_1_.posX, p_82149_1_.posY, p_82149_1_.posZ, p_82149_1_.rotationYaw, p_82149_1_.rotationPitch);
    }
    
    public void copyDataFrom(final Entity p_82141_1_, final boolean p_82141_2_) {
        final NBTTagCompound var3 = new NBTTagCompound();
        p_82141_1_.writeToNBT(var3);
        this.readFromNBT(var3);
        this.timeUntilPortal = p_82141_1_.timeUntilPortal;
        this.teleportDirection = p_82141_1_.teleportDirection;
    }
    
    public void travelToDimension(final int p_71027_1_) {
        if (!this.worldObj.isClient && !this.isDead) {
            this.worldObj.theProfiler.startSection("changeDimension");
            final MinecraftServer var2 = MinecraftServer.getServer();
            final int var3 = this.dimension;
            final WorldServer var4 = var2.worldServerForDimension(var3);
            WorldServer var5 = var2.worldServerForDimension(p_71027_1_);
            this.dimension = p_71027_1_;
            if (var3 == 1 && p_71027_1_ == 1) {
                var5 = var2.worldServerForDimension(0);
                this.dimension = 0;
            }
            this.worldObj.removeEntity(this);
            this.isDead = false;
            this.worldObj.theProfiler.startSection("reposition");
            var2.getConfigurationManager().transferEntityToWorld(this, var3, var4, var5);
            this.worldObj.theProfiler.endStartSection("reloading");
            final Entity var6 = EntityList.createEntityByName(EntityList.getEntityString(this), var5);
            if (var6 != null) {
                var6.copyDataFrom(this, true);
                if (var3 == 1 && p_71027_1_ == 1) {
                    final ChunkCoordinates var7 = var5.getSpawnPoint();
                    var7.posY = this.worldObj.getTopSolidOrLiquidBlock(var7.posX, var7.posZ);
                    var6.setLocationAndAngles(var7.posX, var7.posY, var7.posZ, var6.rotationYaw, var6.rotationPitch);
                }
                var5.spawnEntityInWorld(var6);
            }
            this.isDead = true;
            this.worldObj.theProfiler.endSection();
            var4.resetUpdateEntityTick();
            var5.resetUpdateEntityTick();
            this.worldObj.theProfiler.endSection();
        }
    }
    
    public float func_145772_a(final Explosion p_145772_1_, final World p_145772_2_, final int p_145772_3_, final int p_145772_4_, final int p_145772_5_, final Block p_145772_6_) {
        return p_145772_6_.getExplosionResistance(this);
    }
    
    public boolean func_145774_a(final Explosion p_145774_1_, final World p_145774_2_, final int p_145774_3_, final int p_145774_4_, final int p_145774_5_, final Block p_145774_6_, final float p_145774_7_) {
        return true;
    }
    
    public int getMaxSafePointTries() {
        return 3;
    }
    
    public int getTeleportDirection() {
        return this.teleportDirection;
    }
    
    public boolean doesEntityNotTriggerPressurePlate() {
        return false;
    }
    
    public void addEntityCrashInfo(final CrashReportCategory p_85029_1_) {
        p_85029_1_.addCrashSectionCallable("Entity Type", new Callable() {
            private static final String __OBFID = "CL_00001534";
            
            @Override
            public String call() {
                return EntityList.getEntityString(Entity.this) + " (" + Entity.this.getClass().getCanonicalName() + ")";
            }
        });
        p_85029_1_.addCrashSection("Entity ID", this.field_145783_c);
        p_85029_1_.addCrashSectionCallable("Entity Name", new Callable() {
            private static final String __OBFID = "CL_00001535";
            
            @Override
            public String call() {
                return Entity.this.getCommandSenderName();
            }
        });
        p_85029_1_.addCrashSection("Entity's Exact location", String.format("%.2f, %.2f, %.2f", this.posX, this.posY, this.posZ));
        p_85029_1_.addCrashSection("Entity's Block location", CrashReportCategory.getLocationInfo(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)));
        p_85029_1_.addCrashSection("Entity's Momentum", String.format("%.2f, %.2f, %.2f", this.motionX, this.motionY, this.motionZ));
    }
    
    public boolean canRenderOnFire() {
        return this.isBurning();
    }
    
    public UUID getUniqueID() {
        return this.entityUniqueID;
    }
    
    public boolean isPushedByWater() {
        return true;
    }
    
    public IChatComponent func_145748_c_() {
        return new ChatComponentText(this.getCommandSenderName());
    }
    
    public void func_145781_i(final int p_145781_1_) {
    }
    
    public enum EnumEntitySize
    {
        SIZE_1("SIZE_1", 0), 
        SIZE_2("SIZE_2", 1), 
        SIZE_3("SIZE_3", 2), 
        SIZE_4("SIZE_4", 3), 
        SIZE_5("SIZE_5", 4), 
        SIZE_6("SIZE_6", 5);
        
        private static final EnumEntitySize[] $VALUES;
        private static final String __OBFID = "CL_00001537";
        
        private EnumEntitySize(final String p_i1581_1_, final int p_i1581_2_) {
        }
        
        public int multiplyBy32AndRound(final double p_75630_1_) {
            final double var3 = p_75630_1_ - (MathHelper.floor_double(p_75630_1_) + 0.5);
            switch (SwitchEnumEntitySize.field_96565_a[this.ordinal()]) {
                case 1: {
                    if (var3 < 0.0) {
                        if (var3 < -0.3125) {
                            return MathHelper.ceiling_double_int(p_75630_1_ * 32.0);
                        }
                    }
                    else if (var3 < 0.3125) {
                        return MathHelper.ceiling_double_int(p_75630_1_ * 32.0);
                    }
                    return MathHelper.floor_double(p_75630_1_ * 32.0);
                }
                case 2: {
                    if (var3 < 0.0) {
                        if (var3 < -0.3125) {
                            return MathHelper.floor_double(p_75630_1_ * 32.0);
                        }
                    }
                    else if (var3 < 0.3125) {
                        return MathHelper.floor_double(p_75630_1_ * 32.0);
                    }
                    return MathHelper.ceiling_double_int(p_75630_1_ * 32.0);
                }
                case 3: {
                    if (var3 > 0.0) {
                        return MathHelper.floor_double(p_75630_1_ * 32.0);
                    }
                    return MathHelper.ceiling_double_int(p_75630_1_ * 32.0);
                }
                case 4: {
                    if (var3 < 0.0) {
                        if (var3 < -0.1875) {
                            return MathHelper.ceiling_double_int(p_75630_1_ * 32.0);
                        }
                    }
                    else if (var3 < 0.1875) {
                        return MathHelper.ceiling_double_int(p_75630_1_ * 32.0);
                    }
                    return MathHelper.floor_double(p_75630_1_ * 32.0);
                }
                case 5: {
                    if (var3 < 0.0) {
                        if (var3 < -0.1875) {
                            return MathHelper.floor_double(p_75630_1_ * 32.0);
                        }
                    }
                    else if (var3 < 0.1875) {
                        return MathHelper.floor_double(p_75630_1_ * 32.0);
                    }
                    return MathHelper.ceiling_double_int(p_75630_1_ * 32.0);
                }
                default: {
                    if (var3 > 0.0) {
                        return MathHelper.ceiling_double_int(p_75630_1_ * 32.0);
                    }
                    return MathHelper.floor_double(p_75630_1_ * 32.0);
                }
            }
        }
        
        static {
            $VALUES = new EnumEntitySize[] { EnumEntitySize.SIZE_1, EnumEntitySize.SIZE_2, EnumEntitySize.SIZE_3, EnumEntitySize.SIZE_4, EnumEntitySize.SIZE_5, EnumEntitySize.SIZE_6 };
        }
    }
    
    static final class SwitchEnumEntitySize
    {
        static final int[] field_96565_a;
        private static final String __OBFID = "CL_00001536";
        
        static {
            field_96565_a = new int[EnumEntitySize.values().length];
            try {
                SwitchEnumEntitySize.field_96565_a[EnumEntitySize.SIZE_1.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumEntitySize.field_96565_a[EnumEntitySize.SIZE_2.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumEntitySize.field_96565_a[EnumEntitySize.SIZE_3.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumEntitySize.field_96565_a[EnumEntitySize.SIZE_4.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchEnumEntitySize.field_96565_a[EnumEntitySize.SIZE_5.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                SwitchEnumEntitySize.field_96565_a[EnumEntitySize.SIZE_6.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
        }
    }
}
