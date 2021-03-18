package net.minecraft.entity.projectile;

import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.entity.monster.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class EntityArrow extends Entity implements IProjectile
{
    private int field_145791_d;
    private int field_145792_e;
    private int field_145789_f;
    private Block field_145790_g;
    private int inData;
    private boolean inGround;
    public int canBePickedUp;
    public int arrowShake;
    public Entity shootingEntity;
    private int ticksInGround;
    private int ticksInAir;
    private double damage;
    private int knockbackStrength;
    private static final String __OBFID = "CL_00001715";
    
    public EntityArrow(final World p_i1753_1_) {
        super(p_i1753_1_);
        this.field_145791_d = -1;
        this.field_145792_e = -1;
        this.field_145789_f = -1;
        this.damage = 2.0;
        this.renderDistanceWeight = 10.0;
        this.setSize(0.5f, 0.5f);
    }
    
    public EntityArrow(final World p_i1754_1_, final double p_i1754_2_, final double p_i1754_4_, final double p_i1754_6_) {
        super(p_i1754_1_);
        this.field_145791_d = -1;
        this.field_145792_e = -1;
        this.field_145789_f = -1;
        this.damage = 2.0;
        this.renderDistanceWeight = 10.0;
        this.setSize(0.5f, 0.5f);
        this.setPosition(p_i1754_2_, p_i1754_4_, p_i1754_6_);
        this.yOffset = 0.0f;
    }
    
    public EntityArrow(final World p_i1755_1_, final EntityLivingBase p_i1755_2_, final EntityLivingBase p_i1755_3_, final float p_i1755_4_, final float p_i1755_5_) {
        super(p_i1755_1_);
        this.field_145791_d = -1;
        this.field_145792_e = -1;
        this.field_145789_f = -1;
        this.damage = 2.0;
        this.renderDistanceWeight = 10.0;
        this.shootingEntity = p_i1755_2_;
        if (p_i1755_2_ instanceof EntityPlayer) {
            this.canBePickedUp = 1;
        }
        this.posY = p_i1755_2_.posY + p_i1755_2_.getEyeHeight() - 0.10000000149011612;
        final double var6 = p_i1755_3_.posX - p_i1755_2_.posX;
        final double var7 = p_i1755_3_.boundingBox.minY + p_i1755_3_.height / 3.0f - this.posY;
        final double var8 = p_i1755_3_.posZ - p_i1755_2_.posZ;
        final double var9 = MathHelper.sqrt_double(var6 * var6 + var8 * var8);
        if (var9 >= 1.0E-7) {
            final float var10 = (float)(Math.atan2(var8, var6) * 180.0 / 3.141592653589793) - 90.0f;
            final float var11 = (float)(-(Math.atan2(var7, var9) * 180.0 / 3.141592653589793));
            final double var12 = var6 / var9;
            final double var13 = var8 / var9;
            this.setLocationAndAngles(p_i1755_2_.posX + var12, this.posY, p_i1755_2_.posZ + var13, var10, var11);
            this.yOffset = 0.0f;
            final float var14 = (float)var9 * 0.2f;
            this.setThrowableHeading(var6, var7 + var14, var8, p_i1755_4_, p_i1755_5_);
        }
    }
    
    public EntityArrow(final World p_i1756_1_, final EntityLivingBase p_i1756_2_, final float p_i1756_3_) {
        super(p_i1756_1_);
        this.field_145791_d = -1;
        this.field_145792_e = -1;
        this.field_145789_f = -1;
        this.damage = 2.0;
        this.renderDistanceWeight = 10.0;
        this.shootingEntity = p_i1756_2_;
        if (p_i1756_2_ instanceof EntityPlayer) {
            this.canBePickedUp = 1;
        }
        this.setSize(0.5f, 0.5f);
        this.setLocationAndAngles(p_i1756_2_.posX, p_i1756_2_.posY + p_i1756_2_.getEyeHeight(), p_i1756_2_.posZ, p_i1756_2_.rotationYaw, p_i1756_2_.rotationPitch);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.posY -= 0.10000000149011612;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0f;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f);
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f);
        this.motionY = -MathHelper.sin(this.rotationPitch / 180.0f * 3.1415927f);
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, p_i1756_3_ * 1.5f, 1.0f);
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(16, 0);
    }
    
    @Override
    public void setThrowableHeading(double p_70186_1_, double p_70186_3_, double p_70186_5_, final float p_70186_7_, final float p_70186_8_) {
        final float var9 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_3_ * p_70186_3_ + p_70186_5_ * p_70186_5_);
        p_70186_1_ /= var9;
        p_70186_3_ /= var9;
        p_70186_5_ /= var9;
        p_70186_1_ += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937 * p_70186_8_;
        p_70186_3_ += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937 * p_70186_8_;
        p_70186_5_ += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937 * p_70186_8_;
        p_70186_1_ *= p_70186_7_;
        p_70186_3_ *= p_70186_7_;
        p_70186_5_ *= p_70186_7_;
        this.motionX = p_70186_1_;
        this.motionY = p_70186_3_;
        this.motionZ = p_70186_5_;
        final float var10 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_5_ * p_70186_5_);
        final float n = (float)(Math.atan2(p_70186_1_, p_70186_5_) * 180.0 / 3.141592653589793);
        this.rotationYaw = n;
        this.prevRotationYaw = n;
        final float n2 = (float)(Math.atan2(p_70186_3_, var10) * 180.0 / 3.141592653589793);
        this.rotationPitch = n2;
        this.prevRotationPitch = n2;
        this.ticksInGround = 0;
    }
    
    @Override
    public void setPositionAndRotation2(final double p_70056_1_, final double p_70056_3_, final double p_70056_5_, final float p_70056_7_, final float p_70056_8_, final int p_70056_9_) {
        this.setPosition(p_70056_1_, p_70056_3_, p_70056_5_);
        this.setRotation(p_70056_7_, p_70056_8_);
    }
    
    @Override
    public void setVelocity(final double p_70016_1_, final double p_70016_3_, final double p_70016_5_) {
        this.motionX = p_70016_1_;
        this.motionY = p_70016_3_;
        this.motionZ = p_70016_5_;
        if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            final float var7 = MathHelper.sqrt_double(p_70016_1_ * p_70016_1_ + p_70016_5_ * p_70016_5_);
            final float n = (float)(Math.atan2(p_70016_1_, p_70016_5_) * 180.0 / 3.141592653589793);
            this.rotationYaw = n;
            this.prevRotationYaw = n;
            final float n2 = (float)(Math.atan2(p_70016_3_, var7) * 180.0 / 3.141592653589793);
            this.rotationPitch = n2;
            this.prevRotationPitch = n2;
            this.prevRotationPitch = this.rotationPitch;
            this.prevRotationYaw = this.rotationYaw;
            this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.ticksInGround = 0;
        }
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            final float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            final float n = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
            this.rotationYaw = n;
            this.prevRotationYaw = n;
            final float n2 = (float)(Math.atan2(this.motionY, var1) * 180.0 / 3.141592653589793);
            this.rotationPitch = n2;
            this.prevRotationPitch = n2;
        }
        final Block var2 = this.worldObj.getBlock(this.field_145791_d, this.field_145792_e, this.field_145789_f);
        if (var2.getMaterial() != Material.air) {
            var2.setBlockBoundsBasedOnState(this.worldObj, this.field_145791_d, this.field_145792_e, this.field_145789_f);
            final AxisAlignedBB var3 = var2.getCollisionBoundingBoxFromPool(this.worldObj, this.field_145791_d, this.field_145792_e, this.field_145789_f);
            if (var3 != null && var3.isVecInside(Vec3.createVectorHelper(this.posX, this.posY, this.posZ))) {
                this.inGround = true;
            }
        }
        if (this.arrowShake > 0) {
            --this.arrowShake;
        }
        if (this.inGround) {
            final int var4 = this.worldObj.getBlockMetadata(this.field_145791_d, this.field_145792_e, this.field_145789_f);
            if (var2 == this.field_145790_g && var4 == this.inData) {
                ++this.ticksInGround;
                if (this.ticksInGround == 1200) {
                    this.setDead();
                }
            }
            else {
                this.inGround = false;
                this.motionX *= this.rand.nextFloat() * 0.2f;
                this.motionY *= this.rand.nextFloat() * 0.2f;
                this.motionZ *= this.rand.nextFloat() * 0.2f;
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        }
        else {
            ++this.ticksInAir;
            Vec3 var5 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            Vec3 var6 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            MovingObjectPosition var7 = this.worldObj.func_147447_a(var5, var6, false, true, false);
            var5 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            var6 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            if (var7 != null) {
                var6 = Vec3.createVectorHelper(var7.hitVec.xCoord, var7.hitVec.yCoord, var7.hitVec.zCoord);
            }
            Entity var8 = null;
            final List var9 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
            double var10 = 0.0;
            for (int var11 = 0; var11 < var9.size(); ++var11) {
                final Entity var12 = var9.get(var11);
                if (var12.canBeCollidedWith() && (var12 != this.shootingEntity || this.ticksInAir >= 5)) {
                    final float var13 = 0.3f;
                    final AxisAlignedBB var14 = var12.boundingBox.expand(var13, var13, var13);
                    final MovingObjectPosition var15 = var14.calculateIntercept(var5, var6);
                    if (var15 != null) {
                        final double var16 = var5.distanceTo(var15.hitVec);
                        if (var16 < var10 || var10 == 0.0) {
                            var8 = var12;
                            var10 = var16;
                        }
                    }
                }
            }
            if (var8 != null) {
                var7 = new MovingObjectPosition(var8);
            }
            if (var7 != null && var7.entityHit != null && var7.entityHit instanceof EntityPlayer) {
                final EntityPlayer var17 = (EntityPlayer)var7.entityHit;
                if (var17.capabilities.disableDamage || (this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).canAttackPlayer(var17))) {
                    var7 = null;
                }
            }
            if (var7 != null) {
                if (var7.entityHit != null) {
                    final float var18 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    int var19 = MathHelper.ceiling_double_int(var18 * this.damage);
                    if (this.getIsCritical()) {
                        var19 += this.rand.nextInt(var19 / 2 + 2);
                    }
                    DamageSource var20 = null;
                    if (this.shootingEntity == null) {
                        var20 = DamageSource.causeArrowDamage(this, this);
                    }
                    else {
                        var20 = DamageSource.causeArrowDamage(this, this.shootingEntity);
                    }
                    if (this.isBurning() && !(var7.entityHit instanceof EntityEnderman)) {
                        var7.entityHit.setFire(5);
                    }
                    if (var7.entityHit.attackEntityFrom(var20, (float)var19)) {
                        if (var7.entityHit instanceof EntityLivingBase) {
                            final EntityLivingBase var21 = (EntityLivingBase)var7.entityHit;
                            if (!this.worldObj.isClient) {
                                var21.setArrowCountInEntity(var21.getArrowCountInEntity() + 1);
                            }
                            if (this.knockbackStrength > 0) {
                                final float var22 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
                                if (var22 > 0.0f) {
                                    var7.entityHit.addVelocity(this.motionX * this.knockbackStrength * 0.6000000238418579 / var22, 0.1, this.motionZ * this.knockbackStrength * 0.6000000238418579 / var22);
                                }
                            }
                            if (this.shootingEntity != null && this.shootingEntity instanceof EntityLivingBase) {
                                EnchantmentHelper.func_151384_a(var21, this.shootingEntity);
                                EnchantmentHelper.func_151385_b((EntityLivingBase)this.shootingEntity, var21);
                            }
                            if (this.shootingEntity != null && var7.entityHit != this.shootingEntity && var7.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP) {
                                ((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0f));
                            }
                        }
                        this.playSound("random.bowhit", 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
                        if (!(var7.entityHit instanceof EntityEnderman)) {
                            this.setDead();
                        }
                    }
                    else {
                        this.motionX *= -0.10000000149011612;
                        this.motionY *= -0.10000000149011612;
                        this.motionZ *= -0.10000000149011612;
                        this.rotationYaw += 180.0f;
                        this.prevRotationYaw += 180.0f;
                        this.ticksInAir = 0;
                    }
                }
                else {
                    this.field_145791_d = var7.blockX;
                    this.field_145792_e = var7.blockY;
                    this.field_145789_f = var7.blockZ;
                    this.field_145790_g = this.worldObj.getBlock(this.field_145791_d, this.field_145792_e, this.field_145789_f);
                    this.inData = this.worldObj.getBlockMetadata(this.field_145791_d, this.field_145792_e, this.field_145789_f);
                    this.motionX = (float)(var7.hitVec.xCoord - this.posX);
                    this.motionY = (float)(var7.hitVec.yCoord - this.posY);
                    this.motionZ = (float)(var7.hitVec.zCoord - this.posZ);
                    final float var18 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    this.posX -= this.motionX / var18 * 0.05000000074505806;
                    this.posY -= this.motionY / var18 * 0.05000000074505806;
                    this.posZ -= this.motionZ / var18 * 0.05000000074505806;
                    this.playSound("random.bowhit", 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
                    this.inGround = true;
                    this.arrowShake = 7;
                    this.setIsCritical(false);
                    if (this.field_145790_g.getMaterial() != Material.air) {
                        this.field_145790_g.onEntityCollidedWithBlock(this.worldObj, this.field_145791_d, this.field_145792_e, this.field_145789_f, this);
                    }
                }
            }
            if (this.getIsCritical()) {
                for (int var11 = 0; var11 < 4; ++var11) {
                    this.worldObj.spawnParticle("crit", this.posX + this.motionX * var11 / 4.0, this.posY + this.motionY * var11 / 4.0, this.posZ + this.motionZ * var11 / 4.0, -this.motionX, -this.motionY + 0.2, -this.motionZ);
                }
            }
            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            final float var18 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
            this.rotationPitch = (float)(Math.atan2(this.motionY, var18) * 180.0 / 3.141592653589793);
            while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
                this.prevRotationPitch -= 360.0f;
            }
            while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
                this.prevRotationPitch += 360.0f;
            }
            while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
                this.prevRotationYaw -= 360.0f;
            }
            while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
                this.prevRotationYaw += 360.0f;
            }
            this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2f;
            this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2f;
            float var23 = 0.99f;
            final float var13 = 0.05f;
            if (this.isInWater()) {
                for (int var24 = 0; var24 < 4; ++var24) {
                    final float var22 = 0.25f;
                    this.worldObj.spawnParticle("bubble", this.posX - this.motionX * var22, this.posY - this.motionY * var22, this.posZ - this.motionZ * var22, this.motionX, this.motionY, this.motionZ);
                }
                var23 = 0.8f;
            }
            if (this.isWet()) {
                this.extinguish();
            }
            this.motionX *= var23;
            this.motionY *= var23;
            this.motionZ *= var23;
            this.motionY -= var13;
            this.setPosition(this.posX, this.posY, this.posZ);
            this.func_145775_I();
        }
    }
    
    public void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        p_70014_1_.setShort("xTile", (short)this.field_145791_d);
        p_70014_1_.setShort("yTile", (short)this.field_145792_e);
        p_70014_1_.setShort("zTile", (short)this.field_145789_f);
        p_70014_1_.setShort("life", (short)this.ticksInGround);
        p_70014_1_.setByte("inTile", (byte)Block.getIdFromBlock(this.field_145790_g));
        p_70014_1_.setByte("inData", (byte)this.inData);
        p_70014_1_.setByte("shake", (byte)this.arrowShake);
        p_70014_1_.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        p_70014_1_.setByte("pickup", (byte)this.canBePickedUp);
        p_70014_1_.setDouble("damage", this.damage);
    }
    
    public void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        this.field_145791_d = p_70037_1_.getShort("xTile");
        this.field_145792_e = p_70037_1_.getShort("yTile");
        this.field_145789_f = p_70037_1_.getShort("zTile");
        this.ticksInGround = p_70037_1_.getShort("life");
        this.field_145790_g = Block.getBlockById(p_70037_1_.getByte("inTile") & 0xFF);
        this.inData = (p_70037_1_.getByte("inData") & 0xFF);
        this.arrowShake = (p_70037_1_.getByte("shake") & 0xFF);
        this.inGround = (p_70037_1_.getByte("inGround") == 1);
        if (p_70037_1_.func_150297_b("damage", 99)) {
            this.damage = p_70037_1_.getDouble("damage");
        }
        if (p_70037_1_.func_150297_b("pickup", 99)) {
            this.canBePickedUp = p_70037_1_.getByte("pickup");
        }
        else if (p_70037_1_.func_150297_b("player", 99)) {
            this.canBePickedUp = (p_70037_1_.getBoolean("player") ? 1 : 0);
        }
    }
    
    @Override
    public void onCollideWithPlayer(final EntityPlayer p_70100_1_) {
        if (!this.worldObj.isClient && this.inGround && this.arrowShake <= 0) {
            boolean var2 = this.canBePickedUp == 1 || (this.canBePickedUp == 2 && p_70100_1_.capabilities.isCreativeMode);
            if (this.canBePickedUp == 1 && !p_70100_1_.inventory.addItemStackToInventory(new ItemStack(Items.arrow, 1))) {
                var2 = false;
            }
            if (var2) {
                this.playSound("random.pop", 0.2f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                p_70100_1_.onItemPickup(this, 1);
                this.setDead();
            }
        }
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    public float getShadowSize() {
        return 0.0f;
    }
    
    public void setDamage(final double p_70239_1_) {
        this.damage = p_70239_1_;
    }
    
    public double getDamage() {
        return this.damage;
    }
    
    public void setKnockbackStrength(final int p_70240_1_) {
        this.knockbackStrength = p_70240_1_;
    }
    
    @Override
    public boolean canAttackWithItem() {
        return false;
    }
    
    public void setIsCritical(final boolean p_70243_1_) {
        final byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        if (p_70243_1_) {
            this.dataWatcher.updateObject(16, (byte)(var2 | 0x1));
        }
        else {
            this.dataWatcher.updateObject(16, (byte)(var2 & 0xFFFFFFFE));
        }
    }
    
    public boolean getIsCritical() {
        final byte var1 = this.dataWatcher.getWatchableObjectByte(16);
        return (var1 & 0x1) != 0x0;
    }
}
