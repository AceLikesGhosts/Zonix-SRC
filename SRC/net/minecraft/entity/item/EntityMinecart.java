package net.minecraft.entity.item;

import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.server.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.monster.*;

public abstract class EntityMinecart extends Entity
{
    private boolean isInReverse;
    private String entityName;
    private static final int[][][] matrix;
    private int turnProgress;
    private double minecartX;
    private double minecartY;
    private double minecartZ;
    private double minecartYaw;
    private double minecartPitch;
    private double velocityX;
    private double velocityY;
    private double velocityZ;
    private static final String __OBFID = "CL_00001670";
    
    public EntityMinecart(final World p_i1712_1_) {
        super(p_i1712_1_);
        this.preventEntitySpawning = true;
        this.setSize(0.98f, 0.7f);
        this.yOffset = this.height / 2.0f;
    }
    
    public static EntityMinecart createMinecart(final World p_94090_0_, final double p_94090_1_, final double p_94090_3_, final double p_94090_5_, final int p_94090_7_) {
        switch (p_94090_7_) {
            case 1: {
                return new EntityMinecartChest(p_94090_0_, p_94090_1_, p_94090_3_, p_94090_5_);
            }
            case 2: {
                return new EntityMinecartFurnace(p_94090_0_, p_94090_1_, p_94090_3_, p_94090_5_);
            }
            case 3: {
                return new EntityMinecartTNT(p_94090_0_, p_94090_1_, p_94090_3_, p_94090_5_);
            }
            case 4: {
                return new EntityMinecartMobSpawner(p_94090_0_, p_94090_1_, p_94090_3_, p_94090_5_);
            }
            case 5: {
                return new EntityMinecartHopper(p_94090_0_, p_94090_1_, p_94090_3_, p_94090_5_);
            }
            case 6: {
                return new EntityMinecartCommandBlock(p_94090_0_, p_94090_1_, p_94090_3_, p_94090_5_);
            }
            default: {
                return new EntityMinecartEmpty(p_94090_0_, p_94090_1_, p_94090_3_, p_94090_5_);
            }
        }
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(17, new Integer(0));
        this.dataWatcher.addObject(18, new Integer(1));
        this.dataWatcher.addObject(19, new Float(0.0f));
        this.dataWatcher.addObject(20, new Integer(0));
        this.dataWatcher.addObject(21, new Integer(6));
        this.dataWatcher.addObject(22, 0);
    }
    
    @Override
    public AxisAlignedBB getCollisionBox(final Entity p_70114_1_) {
        return p_70114_1_.canBePushed() ? p_70114_1_.boundingBox : null;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox() {
        return null;
    }
    
    @Override
    public boolean canBePushed() {
        return true;
    }
    
    public EntityMinecart(final World p_i1713_1_, final double p_i1713_2_, final double p_i1713_4_, final double p_i1713_6_) {
        this(p_i1713_1_);
        this.setPosition(p_i1713_2_, p_i1713_4_, p_i1713_6_);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = p_i1713_2_;
        this.prevPosY = p_i1713_4_;
        this.prevPosZ = p_i1713_6_;
    }
    
    @Override
    public double getMountedYOffset() {
        return this.height * 0.0 - 0.30000001192092896;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource p_70097_1_, final float p_70097_2_) {
        if (this.worldObj.isClient || this.isDead) {
            return true;
        }
        if (this.isEntityInvulnerable()) {
            return false;
        }
        this.setRollingDirection(-this.getRollingDirection());
        this.setRollingAmplitude(10);
        this.setBeenAttacked();
        this.setDamage(this.getDamage() + p_70097_2_ * 10.0f);
        final boolean var3 = p_70097_1_.getEntity() instanceof EntityPlayer && ((EntityPlayer)p_70097_1_.getEntity()).capabilities.isCreativeMode;
        if (var3 || this.getDamage() > 40.0f) {
            if (this.riddenByEntity != null) {
                this.riddenByEntity.mountEntity(this);
            }
            if (var3 && !this.isInventoryNameLocalized()) {
                this.setDead();
            }
            else {
                this.killMinecart(p_70097_1_);
            }
        }
        return true;
    }
    
    public void killMinecart(final DamageSource p_94095_1_) {
        this.setDead();
        final ItemStack var2 = new ItemStack(Items.minecart, 1);
        if (this.entityName != null) {
            var2.setStackDisplayName(this.entityName);
        }
        this.entityDropItem(var2, 0.0f);
    }
    
    @Override
    public void performHurtAnimation() {
        this.setRollingDirection(-this.getRollingDirection());
        this.setRollingAmplitude(10);
        this.setDamage(this.getDamage() + this.getDamage() * 10.0f);
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }
    
    @Override
    public void setDead() {
        super.setDead();
    }
    
    @Override
    public void onUpdate() {
        if (this.getRollingAmplitude() > 0) {
            this.setRollingAmplitude(this.getRollingAmplitude() - 1);
        }
        if (this.getDamage() > 0.0f) {
            this.setDamage(this.getDamage() - 1.0f);
        }
        if (this.posY < -64.0) {
            this.kill();
        }
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
        if (this.worldObj.isClient) {
            if (this.turnProgress > 0) {
                final double var4 = this.posX + (this.minecartX - this.posX) / this.turnProgress;
                final double var5 = this.posY + (this.minecartY - this.posY) / this.turnProgress;
                final double var6 = this.posZ + (this.minecartZ - this.posZ) / this.turnProgress;
                final double var7 = MathHelper.wrapAngleTo180_double(this.minecartYaw - this.rotationYaw);
                this.rotationYaw += (float)(var7 / this.turnProgress);
                this.rotationPitch += (float)((this.minecartPitch - this.rotationPitch) / this.turnProgress);
                --this.turnProgress;
                this.setPosition(var4, var5, var6);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            }
            else {
                this.setPosition(this.posX, this.posY, this.posZ);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            }
        }
        else {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            this.motionY -= 0.03999999910593033;
            final int var8 = MathHelper.floor_double(this.posX);
            int var2 = MathHelper.floor_double(this.posY);
            final int var9 = MathHelper.floor_double(this.posZ);
            if (BlockRailBase.func_150049_b_(this.worldObj, var8, var2 - 1, var9)) {
                --var2;
            }
            final double var10 = 0.4;
            final double var11 = 0.0078125;
            final Block var12 = this.worldObj.getBlock(var8, var2, var9);
            if (BlockRailBase.func_150051_a(var12)) {
                final int var13 = this.worldObj.getBlockMetadata(var8, var2, var9);
                this.func_145821_a(var8, var2, var9, var10, var11, var12, var13);
                if (var12 == Blocks.activator_rail) {
                    this.onActivatorRailPass(var8, var2, var9, (var13 & 0x8) != 0x0);
                }
            }
            else {
                this.func_94088_b(var10);
            }
            this.func_145775_I();
            this.rotationPitch = 0.0f;
            final double var14 = this.prevPosX - this.posX;
            final double var15 = this.prevPosZ - this.posZ;
            if (var14 * var14 + var15 * var15 > 0.001) {
                this.rotationYaw = (float)(Math.atan2(var15, var14) * 180.0 / 3.141592653589793);
                if (this.isInReverse) {
                    this.rotationYaw += 180.0f;
                }
            }
            final double var16 = MathHelper.wrapAngleTo180_float(this.rotationYaw - this.prevRotationYaw);
            if (var16 < -170.0 || var16 >= 170.0) {
                this.rotationYaw += 180.0f;
                this.isInReverse = !this.isInReverse;
            }
            this.setRotation(this.rotationYaw, this.rotationPitch);
            final List var17 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.20000000298023224, 0.0, 0.20000000298023224));
            if (var17 != null && !var17.isEmpty()) {
                for (int var18 = 0; var18 < var17.size(); ++var18) {
                    final Entity var19 = var17.get(var18);
                    if (var19 != this.riddenByEntity && var19.canBePushed() && var19 instanceof EntityMinecart) {
                        var19.applyEntityCollision(this);
                    }
                }
            }
            if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
                if (this.riddenByEntity.ridingEntity == this) {
                    this.riddenByEntity.ridingEntity = null;
                }
                this.riddenByEntity = null;
            }
        }
    }
    
    public void onActivatorRailPass(final int p_96095_1_, final int p_96095_2_, final int p_96095_3_, final boolean p_96095_4_) {
    }
    
    protected void func_94088_b(final double p_94088_1_) {
        if (this.motionX < -p_94088_1_) {
            this.motionX = -p_94088_1_;
        }
        if (this.motionX > p_94088_1_) {
            this.motionX = p_94088_1_;
        }
        if (this.motionZ < -p_94088_1_) {
            this.motionZ = -p_94088_1_;
        }
        if (this.motionZ > p_94088_1_) {
            this.motionZ = p_94088_1_;
        }
        if (this.onGround) {
            this.motionX *= 0.5;
            this.motionY *= 0.5;
            this.motionZ *= 0.5;
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (!this.onGround) {
            this.motionX *= 0.949999988079071;
            this.motionY *= 0.949999988079071;
            this.motionZ *= 0.949999988079071;
        }
    }
    
    protected void func_145821_a(final int p_145821_1_, final int p_145821_2_, final int p_145821_3_, final double p_145821_4_, final double p_145821_6_, final Block p_145821_8_, int p_145821_9_) {
        this.fallDistance = 0.0f;
        final Vec3 var10 = this.func_70489_a(this.posX, this.posY, this.posZ);
        this.posY = p_145821_2_;
        boolean var11 = false;
        boolean var12 = false;
        if (p_145821_8_ == Blocks.golden_rail) {
            var11 = ((p_145821_9_ & 0x8) != 0x0);
            var12 = !var11;
        }
        if (((BlockRailBase)p_145821_8_).func_150050_e()) {
            p_145821_9_ &= 0x7;
        }
        if (p_145821_9_ >= 2 && p_145821_9_ <= 5) {
            this.posY = p_145821_2_ + 1;
        }
        if (p_145821_9_ == 2) {
            this.motionX -= p_145821_6_;
        }
        if (p_145821_9_ == 3) {
            this.motionX += p_145821_6_;
        }
        if (p_145821_9_ == 4) {
            this.motionZ += p_145821_6_;
        }
        if (p_145821_9_ == 5) {
            this.motionZ -= p_145821_6_;
        }
        final int[][] var13 = EntityMinecart.matrix[p_145821_9_];
        double var14 = var13[1][0] - var13[0][0];
        double var15 = var13[1][2] - var13[0][2];
        final double var16 = Math.sqrt(var14 * var14 + var15 * var15);
        final double var17 = this.motionX * var14 + this.motionZ * var15;
        if (var17 < 0.0) {
            var14 = -var14;
            var15 = -var15;
        }
        double var18 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        if (var18 > 2.0) {
            var18 = 2.0;
        }
        this.motionX = var18 * var14 / var16;
        this.motionZ = var18 * var15 / var16;
        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase) {
            final double var19 = ((EntityLivingBase)this.riddenByEntity).moveForward;
            if (var19 > 0.0) {
                final double var20 = -Math.sin(this.riddenByEntity.rotationYaw * 3.1415927f / 180.0f);
                final double var21 = Math.cos(this.riddenByEntity.rotationYaw * 3.1415927f / 180.0f);
                final double var22 = this.motionX * this.motionX + this.motionZ * this.motionZ;
                if (var22 < 0.01) {
                    this.motionX += var20 * 0.1;
                    this.motionZ += var21 * 0.1;
                    var12 = false;
                }
            }
        }
        if (var12) {
            final double var19 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (var19 < 0.03) {
                this.motionX *= 0.0;
                this.motionY *= 0.0;
                this.motionZ *= 0.0;
            }
            else {
                this.motionX *= 0.5;
                this.motionY *= 0.0;
                this.motionZ *= 0.5;
            }
        }
        double var19 = 0.0;
        final double var20 = p_145821_1_ + 0.5 + var13[0][0] * 0.5;
        final double var21 = p_145821_3_ + 0.5 + var13[0][2] * 0.5;
        final double var22 = p_145821_1_ + 0.5 + var13[1][0] * 0.5;
        final double var23 = p_145821_3_ + 0.5 + var13[1][2] * 0.5;
        var14 = var22 - var20;
        var15 = var23 - var21;
        if (var14 == 0.0) {
            this.posX = p_145821_1_ + 0.5;
            var19 = this.posZ - p_145821_3_;
        }
        else if (var15 == 0.0) {
            this.posZ = p_145821_3_ + 0.5;
            var19 = this.posX - p_145821_1_;
        }
        else {
            final double var24 = this.posX - var20;
            final double var25 = this.posZ - var21;
            var19 = (var24 * var14 + var25 * var15) * 2.0;
        }
        this.posX = var20 + var14 * var19;
        this.posZ = var21 + var15 * var19;
        this.setPosition(this.posX, this.posY + this.yOffset, this.posZ);
        double var24 = this.motionX;
        double var25 = this.motionZ;
        if (this.riddenByEntity != null) {
            var24 *= 0.75;
            var25 *= 0.75;
        }
        if (var24 < -p_145821_4_) {
            var24 = -p_145821_4_;
        }
        if (var24 > p_145821_4_) {
            var24 = p_145821_4_;
        }
        if (var25 < -p_145821_4_) {
            var25 = -p_145821_4_;
        }
        if (var25 > p_145821_4_) {
            var25 = p_145821_4_;
        }
        this.moveEntity(var24, 0.0, var25);
        if (var13[0][1] != 0 && MathHelper.floor_double(this.posX) - p_145821_1_ == var13[0][0] && MathHelper.floor_double(this.posZ) - p_145821_3_ == var13[0][2]) {
            this.setPosition(this.posX, this.posY + var13[0][1], this.posZ);
        }
        else if (var13[1][1] != 0 && MathHelper.floor_double(this.posX) - p_145821_1_ == var13[1][0] && MathHelper.floor_double(this.posZ) - p_145821_3_ == var13[1][2]) {
            this.setPosition(this.posX, this.posY + var13[1][1], this.posZ);
        }
        this.applyDrag();
        final Vec3 var26 = this.func_70489_a(this.posX, this.posY, this.posZ);
        if (var26 != null && var10 != null) {
            final double var27 = (var10.yCoord - var26.yCoord) * 0.05;
            var18 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (var18 > 0.0) {
                this.motionX = this.motionX / var18 * (var18 + var27);
                this.motionZ = this.motionZ / var18 * (var18 + var27);
            }
            this.setPosition(this.posX, var26.yCoord, this.posZ);
        }
        final int var28 = MathHelper.floor_double(this.posX);
        final int var29 = MathHelper.floor_double(this.posZ);
        if (var28 != p_145821_1_ || var29 != p_145821_3_) {
            var18 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.motionX = var18 * (var28 - p_145821_1_);
            this.motionZ = var18 * (var29 - p_145821_3_);
        }
        if (var11) {
            final double var30 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (var30 > 0.01) {
                final double var31 = 0.06;
                this.motionX += this.motionX / var30 * var31;
                this.motionZ += this.motionZ / var30 * var31;
            }
            else if (p_145821_9_ == 1) {
                if (this.worldObj.getBlock(p_145821_1_ - 1, p_145821_2_, p_145821_3_).isNormalCube()) {
                    this.motionX = 0.02;
                }
                else if (this.worldObj.getBlock(p_145821_1_ + 1, p_145821_2_, p_145821_3_).isNormalCube()) {
                    this.motionX = -0.02;
                }
            }
            else if (p_145821_9_ == 0) {
                if (this.worldObj.getBlock(p_145821_1_, p_145821_2_, p_145821_3_ - 1).isNormalCube()) {
                    this.motionZ = 0.02;
                }
                else if (this.worldObj.getBlock(p_145821_1_, p_145821_2_, p_145821_3_ + 1).isNormalCube()) {
                    this.motionZ = -0.02;
                }
            }
        }
    }
    
    protected void applyDrag() {
        if (this.riddenByEntity != null) {
            this.motionX *= 0.996999979019165;
            this.motionY *= 0.0;
            this.motionZ *= 0.996999979019165;
        }
        else {
            this.motionX *= 0.9599999785423279;
            this.motionY *= 0.0;
            this.motionZ *= 0.9599999785423279;
        }
    }
    
    public Vec3 func_70495_a(double p_70495_1_, double p_70495_3_, double p_70495_5_, final double p_70495_7_) {
        final int var9 = MathHelper.floor_double(p_70495_1_);
        int var10 = MathHelper.floor_double(p_70495_3_);
        final int var11 = MathHelper.floor_double(p_70495_5_);
        if (BlockRailBase.func_150049_b_(this.worldObj, var9, var10 - 1, var11)) {
            --var10;
        }
        final Block var12 = this.worldObj.getBlock(var9, var10, var11);
        if (!BlockRailBase.func_150051_a(var12)) {
            return null;
        }
        int var13 = this.worldObj.getBlockMetadata(var9, var10, var11);
        if (((BlockRailBase)var12).func_150050_e()) {
            var13 &= 0x7;
        }
        p_70495_3_ = var10;
        if (var13 >= 2 && var13 <= 5) {
            p_70495_3_ = var10 + 1;
        }
        final int[][] var14 = EntityMinecart.matrix[var13];
        double var15 = var14[1][0] - var14[0][0];
        double var16 = var14[1][2] - var14[0][2];
        final double var17 = Math.sqrt(var15 * var15 + var16 * var16);
        var15 /= var17;
        var16 /= var17;
        p_70495_1_ += var15 * p_70495_7_;
        p_70495_5_ += var16 * p_70495_7_;
        if (var14[0][1] != 0 && MathHelper.floor_double(p_70495_1_) - var9 == var14[0][0] && MathHelper.floor_double(p_70495_5_) - var11 == var14[0][2]) {
            p_70495_3_ += var14[0][1];
        }
        else if (var14[1][1] != 0 && MathHelper.floor_double(p_70495_1_) - var9 == var14[1][0] && MathHelper.floor_double(p_70495_5_) - var11 == var14[1][2]) {
            p_70495_3_ += var14[1][1];
        }
        return this.func_70489_a(p_70495_1_, p_70495_3_, p_70495_5_);
    }
    
    public Vec3 func_70489_a(double p_70489_1_, double p_70489_3_, double p_70489_5_) {
        final int var7 = MathHelper.floor_double(p_70489_1_);
        int var8 = MathHelper.floor_double(p_70489_3_);
        final int var9 = MathHelper.floor_double(p_70489_5_);
        if (BlockRailBase.func_150049_b_(this.worldObj, var7, var8 - 1, var9)) {
            --var8;
        }
        final Block var10 = this.worldObj.getBlock(var7, var8, var9);
        if (BlockRailBase.func_150051_a(var10)) {
            int var11 = this.worldObj.getBlockMetadata(var7, var8, var9);
            p_70489_3_ = var8;
            if (((BlockRailBase)var10).func_150050_e()) {
                var11 &= 0x7;
            }
            if (var11 >= 2 && var11 <= 5) {
                p_70489_3_ = var8 + 1;
            }
            final int[][] var12 = EntityMinecart.matrix[var11];
            double var13 = 0.0;
            final double var14 = var7 + 0.5 + var12[0][0] * 0.5;
            final double var15 = var8 + 0.5 + var12[0][1] * 0.5;
            final double var16 = var9 + 0.5 + var12[0][2] * 0.5;
            final double var17 = var7 + 0.5 + var12[1][0] * 0.5;
            final double var18 = var8 + 0.5 + var12[1][1] * 0.5;
            final double var19 = var9 + 0.5 + var12[1][2] * 0.5;
            final double var20 = var17 - var14;
            final double var21 = (var18 - var15) * 2.0;
            final double var22 = var19 - var16;
            if (var20 == 0.0) {
                p_70489_1_ = var7 + 0.5;
                var13 = p_70489_5_ - var9;
            }
            else if (var22 == 0.0) {
                p_70489_5_ = var9 + 0.5;
                var13 = p_70489_1_ - var7;
            }
            else {
                final double var23 = p_70489_1_ - var14;
                final double var24 = p_70489_5_ - var16;
                var13 = (var23 * var20 + var24 * var22) * 2.0;
            }
            p_70489_1_ = var14 + var20 * var13;
            p_70489_3_ = var15 + var21 * var13;
            p_70489_5_ = var16 + var22 * var13;
            if (var21 < 0.0) {
                ++p_70489_3_;
            }
            if (var21 > 0.0) {
                p_70489_3_ += 0.5;
            }
            return Vec3.createVectorHelper(p_70489_1_, p_70489_3_, p_70489_5_);
        }
        return null;
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        if (p_70037_1_.getBoolean("CustomDisplayTile")) {
            this.func_145819_k(p_70037_1_.getInteger("DisplayTile"));
            this.setDisplayTileData(p_70037_1_.getInteger("DisplayData"));
            this.setDisplayTileOffset(p_70037_1_.getInteger("DisplayOffset"));
        }
        if (p_70037_1_.func_150297_b("CustomName", 8) && p_70037_1_.getString("CustomName").length() > 0) {
            this.entityName = p_70037_1_.getString("CustomName");
        }
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        if (this.hasDisplayTile()) {
            p_70014_1_.setBoolean("CustomDisplayTile", true);
            p_70014_1_.setInteger("DisplayTile", (this.func_145820_n().getMaterial() == Material.air) ? 0 : Block.getIdFromBlock(this.func_145820_n()));
            p_70014_1_.setInteger("DisplayData", this.getDisplayTileData());
            p_70014_1_.setInteger("DisplayOffset", this.getDisplayTileOffset());
        }
        if (this.entityName != null && this.entityName.length() > 0) {
            p_70014_1_.setString("CustomName", this.entityName);
        }
    }
    
    @Override
    public float getShadowSize() {
        return 0.0f;
    }
    
    @Override
    public void applyEntityCollision(final Entity p_70108_1_) {
        if (!this.worldObj.isClient && p_70108_1_ != this.riddenByEntity) {
            if (p_70108_1_ instanceof EntityLivingBase && !(p_70108_1_ instanceof EntityPlayer) && !(p_70108_1_ instanceof EntityIronGolem) && this.getMinecartType() == 0 && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.01 && this.riddenByEntity == null && p_70108_1_.ridingEntity == null) {
                p_70108_1_.mountEntity(this);
            }
            double var2 = p_70108_1_.posX - this.posX;
            double var3 = p_70108_1_.posZ - this.posZ;
            double var4 = var2 * var2 + var3 * var3;
            if (var4 >= 9.999999747378752E-5) {
                var4 = MathHelper.sqrt_double(var4);
                var2 /= var4;
                var3 /= var4;
                double var5 = 1.0 / var4;
                if (var5 > 1.0) {
                    var5 = 1.0;
                }
                var2 *= var5;
                var3 *= var5;
                var2 *= 0.10000000149011612;
                var3 *= 0.10000000149011612;
                var2 *= 1.0f - this.entityCollisionReduction;
                var3 *= 1.0f - this.entityCollisionReduction;
                var2 *= 0.5;
                var3 *= 0.5;
                if (p_70108_1_ instanceof EntityMinecart) {
                    final double var6 = p_70108_1_.posX - this.posX;
                    final double var7 = p_70108_1_.posZ - this.posZ;
                    final Vec3 var8 = Vec3.createVectorHelper(var6, 0.0, var7).normalize();
                    final Vec3 var9 = Vec3.createVectorHelper(MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f), 0.0, MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f)).normalize();
                    final double var10 = Math.abs(var8.dotProduct(var9));
                    if (var10 < 0.800000011920929) {
                        return;
                    }
                    double var11 = p_70108_1_.motionX + this.motionX;
                    double var12 = p_70108_1_.motionZ + this.motionZ;
                    if (((EntityMinecart)p_70108_1_).getMinecartType() == 2 && this.getMinecartType() != 2) {
                        this.motionX *= 0.20000000298023224;
                        this.motionZ *= 0.20000000298023224;
                        this.addVelocity(p_70108_1_.motionX - var2, 0.0, p_70108_1_.motionZ - var3);
                        p_70108_1_.motionX *= 0.949999988079071;
                        p_70108_1_.motionZ *= 0.949999988079071;
                    }
                    else if (((EntityMinecart)p_70108_1_).getMinecartType() != 2 && this.getMinecartType() == 2) {
                        p_70108_1_.motionX *= 0.20000000298023224;
                        p_70108_1_.motionZ *= 0.20000000298023224;
                        p_70108_1_.addVelocity(this.motionX + var2, 0.0, this.motionZ + var3);
                        this.motionX *= 0.949999988079071;
                        this.motionZ *= 0.949999988079071;
                    }
                    else {
                        var11 /= 2.0;
                        var12 /= 2.0;
                        this.motionX *= 0.20000000298023224;
                        this.motionZ *= 0.20000000298023224;
                        this.addVelocity(var11 - var2, 0.0, var12 - var3);
                        p_70108_1_.motionX *= 0.20000000298023224;
                        p_70108_1_.motionZ *= 0.20000000298023224;
                        p_70108_1_.addVelocity(var11 + var2, 0.0, var12 + var3);
                    }
                }
                else {
                    this.addVelocity(-var2, 0.0, -var3);
                    p_70108_1_.addVelocity(var2 / 4.0, 0.0, var3 / 4.0);
                }
            }
        }
    }
    
    @Override
    public void setPositionAndRotation2(final double p_70056_1_, final double p_70056_3_, final double p_70056_5_, final float p_70056_7_, final float p_70056_8_, final int p_70056_9_) {
        this.minecartX = p_70056_1_;
        this.minecartY = p_70056_3_;
        this.minecartZ = p_70056_5_;
        this.minecartYaw = p_70056_7_;
        this.minecartPitch = p_70056_8_;
        this.turnProgress = p_70056_9_ + 2;
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }
    
    @Override
    public void setVelocity(final double p_70016_1_, final double p_70016_3_, final double p_70016_5_) {
        this.motionX = p_70016_1_;
        this.velocityX = p_70016_1_;
        this.motionY = p_70016_3_;
        this.velocityY = p_70016_3_;
        this.motionZ = p_70016_5_;
        this.velocityZ = p_70016_5_;
    }
    
    public void setDamage(final float p_70492_1_) {
        this.dataWatcher.updateObject(19, p_70492_1_);
    }
    
    public float getDamage() {
        return this.dataWatcher.getWatchableObjectFloat(19);
    }
    
    public void setRollingAmplitude(final int p_70497_1_) {
        this.dataWatcher.updateObject(17, p_70497_1_);
    }
    
    public int getRollingAmplitude() {
        return this.dataWatcher.getWatchableObjectInt(17);
    }
    
    public void setRollingDirection(final int p_70494_1_) {
        this.dataWatcher.updateObject(18, p_70494_1_);
    }
    
    public int getRollingDirection() {
        return this.dataWatcher.getWatchableObjectInt(18);
    }
    
    public abstract int getMinecartType();
    
    public Block func_145820_n() {
        if (!this.hasDisplayTile()) {
            return this.func_145817_o();
        }
        final int var1 = this.getDataWatcher().getWatchableObjectInt(20) & 0xFFFF;
        return Block.getBlockById(var1);
    }
    
    public Block func_145817_o() {
        return Blocks.air;
    }
    
    public int getDisplayTileData() {
        return this.hasDisplayTile() ? (this.getDataWatcher().getWatchableObjectInt(20) >> 16) : this.getDefaultDisplayTileData();
    }
    
    public int getDefaultDisplayTileData() {
        return 0;
    }
    
    public int getDisplayTileOffset() {
        return this.hasDisplayTile() ? this.getDataWatcher().getWatchableObjectInt(21) : this.getDefaultDisplayTileOffset();
    }
    
    public int getDefaultDisplayTileOffset() {
        return 6;
    }
    
    public void func_145819_k(final int p_145819_1_) {
        this.getDataWatcher().updateObject(20, (p_145819_1_ & 0xFFFF) | this.getDisplayTileData() << 16);
        this.setHasDisplayTile(true);
    }
    
    public void setDisplayTileData(final int p_94092_1_) {
        this.getDataWatcher().updateObject(20, (Block.getIdFromBlock(this.func_145820_n()) & 0xFFFF) | p_94092_1_ << 16);
        this.setHasDisplayTile(true);
    }
    
    public void setDisplayTileOffset(final int p_94086_1_) {
        this.getDataWatcher().updateObject(21, p_94086_1_);
        this.setHasDisplayTile(true);
    }
    
    public boolean hasDisplayTile() {
        return this.getDataWatcher().getWatchableObjectByte(22) == 1;
    }
    
    public void setHasDisplayTile(final boolean p_94096_1_) {
        this.getDataWatcher().updateObject(22, (byte)(byte)(p_94096_1_ ? 1 : 0));
    }
    
    public void setMinecartName(final String p_96094_1_) {
        this.entityName = p_96094_1_;
    }
    
    @Override
    public String getCommandSenderName() {
        return (this.entityName != null) ? this.entityName : super.getCommandSenderName();
    }
    
    public boolean isInventoryNameLocalized() {
        return this.entityName != null;
    }
    
    public String func_95999_t() {
        return this.entityName;
    }
    
    static {
        matrix = new int[][][] { { { 0, 0, -1 }, { 0, 0, 1 } }, { { -1, 0, 0 }, { 1, 0, 0 } }, { { -1, -1, 0 }, { 1, 0, 0 } }, { { -1, 0, 0 }, { 1, -1, 0 } }, { { 0, 0, -1 }, { 0, -1, 1 } }, { { 0, -1, -1 }, { 0, 0, 1 } }, { { 0, 0, 1 }, { 1, 0, 0 } }, { { 0, 0, 1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { 1, 0, 0 } } };
    }
}
