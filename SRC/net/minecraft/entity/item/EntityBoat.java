package net.minecraft.entity.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.nbt.*;

public class EntityBoat extends Entity
{
    private boolean isBoatEmpty;
    private double speedMultiplier;
    private int boatPosRotationIncrements;
    private double boatX;
    private double boatY;
    private double boatZ;
    private double boatYaw;
    private double boatPitch;
    private double velocityX;
    private double velocityY;
    private double velocityZ;
    private static final String __OBFID = "CL_00001667";
    
    public EntityBoat(final World p_i1704_1_) {
        super(p_i1704_1_);
        this.isBoatEmpty = true;
        this.speedMultiplier = 0.07;
        this.preventEntitySpawning = true;
        this.setSize(1.5f, 0.6f);
        this.yOffset = this.height / 2.0f;
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
    }
    
    @Override
    public AxisAlignedBB getCollisionBox(final Entity p_70114_1_) {
        return p_70114_1_.boundingBox;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }
    
    @Override
    public boolean canBePushed() {
        return true;
    }
    
    public EntityBoat(final World p_i1705_1_, final double p_i1705_2_, final double p_i1705_4_, final double p_i1705_6_) {
        this(p_i1705_1_);
        this.setPosition(p_i1705_2_, p_i1705_4_ + this.yOffset, p_i1705_6_);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = p_i1705_2_;
        this.prevPosY = p_i1705_4_;
        this.prevPosZ = p_i1705_6_;
    }
    
    @Override
    public double getMountedYOffset() {
        return this.height * 0.0 - 0.30000001192092896;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource p_70097_1_, final float p_70097_2_) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (!this.worldObj.isClient && !this.isDead) {
            this.setForwardDirection(-this.getForwardDirection());
            this.setTimeSinceHit(10);
            this.setDamageTaken(this.getDamageTaken() + p_70097_2_ * 10.0f);
            this.setBeenAttacked();
            final boolean var3 = p_70097_1_.getEntity() instanceof EntityPlayer && ((EntityPlayer)p_70097_1_.getEntity()).capabilities.isCreativeMode;
            if (var3 || this.getDamageTaken() > 40.0f) {
                if (this.riddenByEntity != null) {
                    this.riddenByEntity.mountEntity(this);
                }
                if (!var3) {
                    this.func_145778_a(Items.boat, 1, 0.0f);
                }
                this.setDead();
            }
            return true;
        }
        return true;
    }
    
    @Override
    public void performHurtAnimation() {
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamageTaken(this.getDamageTaken() * 11.0f);
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }
    
    @Override
    public void setPositionAndRotation2(final double p_70056_1_, final double p_70056_3_, final double p_70056_5_, final float p_70056_7_, final float p_70056_8_, final int p_70056_9_) {
        if (this.isBoatEmpty) {
            this.boatPosRotationIncrements = p_70056_9_ + 5;
        }
        else {
            final double var10 = p_70056_1_ - this.posX;
            final double var11 = p_70056_3_ - this.posY;
            final double var12 = p_70056_5_ - this.posZ;
            final double var13 = var10 * var10 + var11 * var11 + var12 * var12;
            if (var13 <= 1.0) {
                return;
            }
            this.boatPosRotationIncrements = 3;
        }
        this.boatX = p_70056_1_;
        this.boatY = p_70056_3_;
        this.boatZ = p_70056_5_;
        this.boatYaw = p_70056_7_;
        this.boatPitch = p_70056_8_;
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
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.getTimeSinceHit() > 0) {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }
        if (this.getDamageTaken() > 0.0f) {
            this.setDamageTaken(this.getDamageTaken() - 1.0f);
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        final byte var1 = 5;
        double var2 = 0.0;
        for (int var3 = 0; var3 < var1; ++var3) {
            final double var4 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (var3 + 0) / var1 - 0.125;
            final double var5 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (var3 + 1) / var1 - 0.125;
            final AxisAlignedBB var6 = AxisAlignedBB.getBoundingBox(this.boundingBox.minX, var4, this.boundingBox.minZ, this.boundingBox.maxX, var5, this.boundingBox.maxZ);
            if (this.worldObj.isAABBInMaterial(var6, Material.water)) {
                var2 += 1.0 / var1;
            }
        }
        final double var7 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        if (var7 > 0.26249999999999996) {
            final double var8 = Math.cos(this.rotationYaw * 3.141592653589793 / 180.0);
            final double var9 = Math.sin(this.rotationYaw * 3.141592653589793 / 180.0);
            for (int var10 = 0; var10 < 1.0 + var7 * 60.0; ++var10) {
                final double var11 = this.rand.nextFloat() * 2.0f - 1.0f;
                final double var12 = (this.rand.nextInt(2) * 2 - 1) * 0.7;
                if (this.rand.nextBoolean()) {
                    final double var13 = this.posX - var8 * var11 * 0.8 + var9 * var12;
                    final double var14 = this.posZ - var9 * var11 * 0.8 - var8 * var12;
                    this.worldObj.spawnParticle("splash", var13, this.posY - 0.125, var14, this.motionX, this.motionY, this.motionZ);
                }
                else {
                    final double var13 = this.posX + var8 + var9 * var11 * 0.7;
                    final double var14 = this.posZ + var9 - var8 * var11 * 0.7;
                    this.worldObj.spawnParticle("splash", var13, this.posY - 0.125, var14, this.motionX, this.motionY, this.motionZ);
                }
            }
        }
        if (this.worldObj.isClient && this.isBoatEmpty) {
            if (this.boatPosRotationIncrements > 0) {
                final double var8 = this.posX + (this.boatX - this.posX) / this.boatPosRotationIncrements;
                final double var9 = this.posY + (this.boatY - this.posY) / this.boatPosRotationIncrements;
                final double var15 = this.posZ + (this.boatZ - this.posZ) / this.boatPosRotationIncrements;
                final double var16 = MathHelper.wrapAngleTo180_double(this.boatYaw - this.rotationYaw);
                this.rotationYaw += (float)(var16 / this.boatPosRotationIncrements);
                this.rotationPitch += (float)((this.boatPitch - this.rotationPitch) / this.boatPosRotationIncrements);
                --this.boatPosRotationIncrements;
                this.setPosition(var8, var9, var15);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            }
            else {
                final double var8 = this.posX + this.motionX;
                final double var9 = this.posY + this.motionY;
                final double var15 = this.posZ + this.motionZ;
                this.setPosition(var8, var9, var15);
                if (this.onGround) {
                    this.motionX *= 0.5;
                    this.motionY *= 0.5;
                    this.motionZ *= 0.5;
                }
                this.motionX *= 0.9900000095367432;
                this.motionY *= 0.949999988079071;
                this.motionZ *= 0.9900000095367432;
            }
        }
        else {
            if (var2 < 1.0) {
                final double var8 = var2 * 2.0 - 1.0;
                this.motionY += 0.03999999910593033 * var8;
            }
            else {
                if (this.motionY < 0.0) {
                    this.motionY /= 2.0;
                }
                this.motionY += 0.007000000216066837;
            }
            if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase) {
                final EntityLivingBase var17 = (EntityLivingBase)this.riddenByEntity;
                final float var18 = this.riddenByEntity.rotationYaw + -var17.moveStrafing * 90.0f;
                this.motionX += -Math.sin(var18 * 3.1415927f / 180.0f) * this.speedMultiplier * var17.moveForward * 0.05000000074505806;
                this.motionZ += Math.cos(var18 * 3.1415927f / 180.0f) * this.speedMultiplier * var17.moveForward * 0.05000000074505806;
            }
            double var8 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (var8 > 0.35) {
                final double var9 = 0.35 / var8;
                this.motionX *= var9;
                this.motionZ *= var9;
                var8 = 0.35;
            }
            if (var8 > var7 && this.speedMultiplier < 0.35) {
                this.speedMultiplier += (0.35 - this.speedMultiplier) / 35.0;
                if (this.speedMultiplier > 0.35) {
                    this.speedMultiplier = 0.35;
                }
            }
            else {
                this.speedMultiplier -= (this.speedMultiplier - 0.07) / 35.0;
                if (this.speedMultiplier < 0.07) {
                    this.speedMultiplier = 0.07;
                }
            }
            for (int var19 = 0; var19 < 4; ++var19) {
                final int var20 = MathHelper.floor_double(this.posX + (var19 % 2 - 0.5) * 0.8);
                final int var10 = MathHelper.floor_double(this.posZ + (var19 / 2 - 0.5) * 0.8);
                for (int var21 = 0; var21 < 2; ++var21) {
                    final int var22 = MathHelper.floor_double(this.posY) + var21;
                    final Block var23 = this.worldObj.getBlock(var20, var22, var10);
                    if (var23 == Blocks.snow_layer) {
                        this.worldObj.setBlockToAir(var20, var22, var10);
                        this.isCollidedHorizontally = false;
                    }
                    else if (var23 == Blocks.waterlily) {
                        this.worldObj.func_147480_a(var20, var22, var10, true);
                        this.isCollidedHorizontally = false;
                    }
                }
            }
            if (this.onGround) {
                this.motionX *= 0.5;
                this.motionY *= 0.5;
                this.motionZ *= 0.5;
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            if (this.isCollidedHorizontally && var7 > 0.2) {
                if (!this.worldObj.isClient && !this.isDead) {
                    this.setDead();
                    for (int var19 = 0; var19 < 3; ++var19) {
                        this.func_145778_a(Item.getItemFromBlock(Blocks.planks), 1, 0.0f);
                    }
                    for (int var19 = 0; var19 < 2; ++var19) {
                        this.func_145778_a(Items.stick, 1, 0.0f);
                    }
                }
            }
            else {
                this.motionX *= 0.9900000095367432;
                this.motionY *= 0.949999988079071;
                this.motionZ *= 0.9900000095367432;
            }
            this.rotationPitch = 0.0f;
            double var9 = this.rotationYaw;
            final double var15 = this.prevPosX - this.posX;
            final double var16 = this.prevPosZ - this.posZ;
            if (var15 * var15 + var16 * var16 > 0.001) {
                var9 = (float)(Math.atan2(var16, var15) * 180.0 / 3.141592653589793);
            }
            double var24 = MathHelper.wrapAngleTo180_double(var9 - this.rotationYaw);
            if (var24 > 20.0) {
                var24 = 20.0;
            }
            if (var24 < -20.0) {
                var24 = -20.0;
            }
            this.setRotation(this.rotationYaw += (float)var24, this.rotationPitch);
            if (!this.worldObj.isClient) {
                final List var25 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.20000000298023224, 0.0, 0.20000000298023224));
                if (var25 != null && !var25.isEmpty()) {
                    for (int var26 = 0; var26 < var25.size(); ++var26) {
                        final Entity var27 = var25.get(var26);
                        if (var27 != this.riddenByEntity && var27.canBePushed() && var27 instanceof EntityBoat) {
                            var27.applyEntityCollision(this);
                        }
                    }
                }
                if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
                    this.riddenByEntity = null;
                }
            }
        }
    }
    
    @Override
    public void updateRiderPosition() {
        if (this.riddenByEntity != null) {
            final double var1 = Math.cos(this.rotationYaw * 3.141592653589793 / 180.0) * 0.4;
            final double var2 = Math.sin(this.rotationYaw * 3.141592653589793 / 180.0) * 0.4;
            this.riddenByEntity.setPosition(this.posX + var1, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + var2);
        }
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
    }
    
    @Override
    public float getShadowSize() {
        return 0.0f;
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer p_130002_1_) {
        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != p_130002_1_) {
            return true;
        }
        if (!this.worldObj.isClient) {
            p_130002_1_.mountEntity(this);
        }
        return true;
    }
    
    @Override
    protected void updateFallState(final double p_70064_1_, final boolean p_70064_3_) {
        final int var4 = MathHelper.floor_double(this.posX);
        final int var5 = MathHelper.floor_double(this.posY);
        final int var6 = MathHelper.floor_double(this.posZ);
        if (p_70064_3_) {
            if (this.fallDistance > 3.0f) {
                this.fall(this.fallDistance);
                if (!this.worldObj.isClient && !this.isDead) {
                    this.setDead();
                    for (int var7 = 0; var7 < 3; ++var7) {
                        this.func_145778_a(Item.getItemFromBlock(Blocks.planks), 1, 0.0f);
                    }
                    for (int var7 = 0; var7 < 2; ++var7) {
                        this.func_145778_a(Items.stick, 1, 0.0f);
                    }
                }
                this.fallDistance = 0.0f;
            }
        }
        else if (this.worldObj.getBlock(var4, var5 - 1, var6).getMaterial() != Material.water && p_70064_1_ < 0.0) {
            this.fallDistance -= (float)p_70064_1_;
        }
    }
    
    public void setDamageTaken(final float p_70266_1_) {
        this.dataWatcher.updateObject(19, p_70266_1_);
    }
    
    public float getDamageTaken() {
        return this.dataWatcher.getWatchableObjectFloat(19);
    }
    
    public void setTimeSinceHit(final int p_70265_1_) {
        this.dataWatcher.updateObject(17, p_70265_1_);
    }
    
    public int getTimeSinceHit() {
        return this.dataWatcher.getWatchableObjectInt(17);
    }
    
    public void setForwardDirection(final int p_70269_1_) {
        this.dataWatcher.updateObject(18, p_70269_1_);
    }
    
    public int getForwardDirection() {
        return this.dataWatcher.getWatchableObjectInt(18);
    }
    
    public void setIsBoatEmpty(final boolean p_70270_1_) {
        this.isBoatEmpty = p_70270_1_;
    }
}
