package net.minecraft.entity.projectile;

import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;

public abstract class EntityThrowable extends Entity implements IProjectile
{
    private int field_145788_c;
    private int field_145786_d;
    private int field_145787_e;
    private Block field_145785_f;
    protected boolean inGround;
    public int throwableShake;
    private EntityLivingBase thrower;
    private String throwerName;
    private int ticksInGround;
    private int ticksInAir;
    private static final String __OBFID = "CL_00001723";
    
    public EntityThrowable(final World p_i1776_1_) {
        super(p_i1776_1_);
        this.field_145788_c = -1;
        this.field_145786_d = -1;
        this.field_145787_e = -1;
        this.setSize(0.25f, 0.25f);
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    public boolean isInRangeToRenderDist(final double p_70112_1_) {
        double var3 = this.boundingBox.getAverageEdgeLength() * 4.0;
        var3 *= 64.0;
        return p_70112_1_ < var3 * var3;
    }
    
    public EntityThrowable(final World p_i1777_1_, final EntityLivingBase p_i1777_2_) {
        super(p_i1777_1_);
        this.field_145788_c = -1;
        this.field_145786_d = -1;
        this.field_145787_e = -1;
        this.thrower = p_i1777_2_;
        this.setSize(0.25f, 0.25f);
        this.setLocationAndAngles(p_i1777_2_.posX, p_i1777_2_.posY + p_i1777_2_.getEyeHeight(), p_i1777_2_.posZ, p_i1777_2_.rotationYaw, p_i1777_2_.rotationPitch);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.posY -= 0.10000000149011612;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0f;
        final float var3 = 0.4f;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * var3;
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * var3;
        this.motionY = -MathHelper.sin((this.rotationPitch + this.func_70183_g()) / 180.0f * 3.1415927f) * var3;
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, this.func_70182_d(), 1.0f);
    }
    
    public EntityThrowable(final World p_i1778_1_, final double p_i1778_2_, final double p_i1778_4_, final double p_i1778_6_) {
        super(p_i1778_1_);
        this.field_145788_c = -1;
        this.field_145786_d = -1;
        this.field_145787_e = -1;
        this.ticksInGround = 0;
        this.setSize(0.25f, 0.25f);
        this.setPosition(p_i1778_2_, p_i1778_4_, p_i1778_6_);
        this.yOffset = 0.0f;
    }
    
    protected float func_70182_d() {
        return 1.5f;
    }
    
    protected float func_70183_g() {
        return 0.0f;
    }
    
    @Override
    public void setThrowableHeading(double p_70186_1_, double p_70186_3_, double p_70186_5_, final float p_70186_7_, final float p_70186_8_) {
        final float var9 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_3_ * p_70186_3_ + p_70186_5_ * p_70186_5_);
        p_70186_1_ /= var9;
        p_70186_3_ /= var9;
        p_70186_5_ /= var9;
        p_70186_1_ += this.rand.nextGaussian() * 0.007499999832361937 * p_70186_8_;
        p_70186_3_ += this.rand.nextGaussian() * 0.007499999832361937 * p_70186_8_;
        p_70186_5_ += this.rand.nextGaussian() * 0.007499999832361937 * p_70186_8_;
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
        }
    }
    
    @Override
    public void onUpdate() {
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        super.onUpdate();
        if (this.throwableShake > 0) {
            --this.throwableShake;
        }
        if (this.inGround) {
            if (this.worldObj.getBlock(this.field_145788_c, this.field_145786_d, this.field_145787_e) == this.field_145785_f) {
                ++this.ticksInGround;
                if (this.ticksInGround == 1200) {
                    this.setDead();
                }
                return;
            }
            this.inGround = false;
            this.motionX *= this.rand.nextFloat() * 0.2f;
            this.motionY *= this.rand.nextFloat() * 0.2f;
            this.motionZ *= this.rand.nextFloat() * 0.2f;
            this.ticksInGround = 0;
            this.ticksInAir = 0;
        }
        else {
            ++this.ticksInAir;
        }
        Vec3 var1 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        Vec3 var2 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        MovingObjectPosition var3 = this.worldObj.rayTraceBlocks(var1, var2);
        var1 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        var2 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        if (var3 != null) {
            var2 = Vec3.createVectorHelper(var3.hitVec.xCoord, var3.hitVec.yCoord, var3.hitVec.zCoord);
        }
        if (!this.worldObj.isClient) {
            Entity var4 = null;
            final List var5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
            double var6 = 0.0;
            final EntityLivingBase var7 = this.getThrower();
            for (int var8 = 0; var8 < var5.size(); ++var8) {
                final Entity var9 = var5.get(var8);
                if (var9.canBeCollidedWith() && (var9 != var7 || this.ticksInAir >= 5)) {
                    final float var10 = 0.3f;
                    final AxisAlignedBB var11 = var9.boundingBox.expand(var10, var10, var10);
                    final MovingObjectPosition var12 = var11.calculateIntercept(var1, var2);
                    if (var12 != null) {
                        final double var13 = var1.distanceTo(var12.hitVec);
                        if (var13 < var6 || var6 == 0.0) {
                            var4 = var9;
                            var6 = var13;
                        }
                    }
                }
            }
            if (var4 != null) {
                var3 = new MovingObjectPosition(var4);
            }
        }
        if (var3 != null) {
            if (var3.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.worldObj.getBlock(var3.blockX, var3.blockY, var3.blockZ) == Blocks.portal) {
                this.setInPortal();
            }
            else {
                this.onImpact(var3);
            }
        }
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        final float var14 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
        this.rotationPitch = (float)(Math.atan2(this.motionY, var14) * 180.0 / 3.141592653589793);
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
        float var15 = 0.99f;
        final float var16 = this.getGravityVelocity();
        if (this.isInWater()) {
            for (int var17 = 0; var17 < 4; ++var17) {
                final float var18 = 0.25f;
                this.worldObj.spawnParticle("bubble", this.posX - this.motionX * var18, this.posY - this.motionY * var18, this.posZ - this.motionZ * var18, this.motionX, this.motionY, this.motionZ);
            }
            var15 = 0.8f;
        }
        this.motionX *= var15;
        this.motionY *= var15;
        this.motionZ *= var15;
        this.motionY -= var16;
        this.setPosition(this.posX, this.posY, this.posZ);
    }
    
    protected float getGravityVelocity() {
        return 0.03f;
    }
    
    protected abstract void onImpact(final MovingObjectPosition p0);
    
    public void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        p_70014_1_.setShort("xTile", (short)this.field_145788_c);
        p_70014_1_.setShort("yTile", (short)this.field_145786_d);
        p_70014_1_.setShort("zTile", (short)this.field_145787_e);
        p_70014_1_.setByte("inTile", (byte)Block.getIdFromBlock(this.field_145785_f));
        p_70014_1_.setByte("shake", (byte)this.throwableShake);
        p_70014_1_.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        if ((this.throwerName == null || this.throwerName.length() == 0) && this.thrower != null && this.thrower instanceof EntityPlayer) {
            this.throwerName = this.thrower.getCommandSenderName();
        }
        p_70014_1_.setString("ownerName", (this.throwerName == null) ? "" : this.throwerName);
    }
    
    public void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        this.field_145788_c = p_70037_1_.getShort("xTile");
        this.field_145786_d = p_70037_1_.getShort("yTile");
        this.field_145787_e = p_70037_1_.getShort("zTile");
        this.field_145785_f = Block.getBlockById(p_70037_1_.getByte("inTile") & 0xFF);
        this.throwableShake = (p_70037_1_.getByte("shake") & 0xFF);
        this.inGround = (p_70037_1_.getByte("inGround") == 1);
        this.throwerName = p_70037_1_.getString("ownerName");
        if (this.throwerName != null && this.throwerName.length() == 0) {
            this.throwerName = null;
        }
    }
    
    @Override
    public float getShadowSize() {
        return 0.0f;
    }
    
    public EntityLivingBase getThrower() {
        if (this.thrower == null && this.throwerName != null && this.throwerName.length() > 0) {
            this.thrower = this.worldObj.getPlayerEntityByName(this.throwerName);
        }
        return this.thrower;
    }
}
