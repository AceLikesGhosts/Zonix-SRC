package net.minecraft.entity.projectile;

import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.item.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.item.*;

public class EntityFishHook extends Entity
{
    private static final List field_146039_d;
    private static final List field_146041_e;
    private static final List field_146036_f;
    private int field_146037_g;
    private int field_146048_h;
    private int field_146050_i;
    private Block field_146046_j;
    private boolean field_146051_au;
    public int field_146044_a;
    public EntityPlayer field_146042_b;
    private int field_146049_av;
    private int field_146047_aw;
    private int field_146045_ax;
    private int field_146040_ay;
    private int field_146038_az;
    private float field_146054_aA;
    public Entity field_146043_c;
    private int field_146055_aB;
    private double field_146056_aC;
    private double field_146057_aD;
    private double field_146058_aE;
    private double field_146059_aF;
    private double field_146060_aG;
    private double field_146061_aH;
    private double field_146052_aI;
    private double field_146053_aJ;
    private static final String __OBFID = "CL_00001663";
    
    public EntityFishHook(final World p_i1764_1_) {
        super(p_i1764_1_);
        this.field_146037_g = -1;
        this.field_146048_h = -1;
        this.field_146050_i = -1;
        this.setSize(0.25f, 0.25f);
        this.ignoreFrustumCheck = true;
    }
    
    public EntityFishHook(final World p_i1765_1_, final double p_i1765_2_, final double p_i1765_4_, final double p_i1765_6_, final EntityPlayer p_i1765_8_) {
        this(p_i1765_1_);
        this.setPosition(p_i1765_2_, p_i1765_4_, p_i1765_6_);
        this.ignoreFrustumCheck = true;
        this.field_146042_b = p_i1765_8_;
        p_i1765_8_.fishEntity = this;
    }
    
    public EntityFishHook(final World p_i1766_1_, final EntityPlayer p_i1766_2_) {
        super(p_i1766_1_);
        this.field_146037_g = -1;
        this.field_146048_h = -1;
        this.field_146050_i = -1;
        this.ignoreFrustumCheck = true;
        this.field_146042_b = p_i1766_2_;
        (this.field_146042_b.fishEntity = this).setSize(0.25f, 0.25f);
        this.setLocationAndAngles(p_i1766_2_.posX, p_i1766_2_.posY + 1.62 - p_i1766_2_.yOffset, p_i1766_2_.posZ, p_i1766_2_.rotationYaw, p_i1766_2_.rotationPitch);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.posY -= 0.10000000149011612;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0f;
        final float var3 = 0.4f;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * var3;
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * var3;
        this.motionY = -MathHelper.sin(this.rotationPitch / 180.0f * 3.1415927f) * var3;
        this.func_146035_c(this.motionX, this.motionY, this.motionZ, 1.5f, 1.0f);
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
    
    public void func_146035_c(double p_146035_1_, double p_146035_3_, double p_146035_5_, final float p_146035_7_, final float p_146035_8_) {
        final float var9 = MathHelper.sqrt_double(p_146035_1_ * p_146035_1_ + p_146035_3_ * p_146035_3_ + p_146035_5_ * p_146035_5_);
        p_146035_1_ /= var9;
        p_146035_3_ /= var9;
        p_146035_5_ /= var9;
        p_146035_1_ += this.rand.nextGaussian() * 0.007499999832361937 * p_146035_8_;
        p_146035_3_ += this.rand.nextGaussian() * 0.007499999832361937 * p_146035_8_;
        p_146035_5_ += this.rand.nextGaussian() * 0.007499999832361937 * p_146035_8_;
        p_146035_1_ *= p_146035_7_;
        p_146035_3_ *= p_146035_7_;
        p_146035_5_ *= p_146035_7_;
        this.motionX = p_146035_1_;
        this.motionY = p_146035_3_;
        this.motionZ = p_146035_5_;
        final float var10 = MathHelper.sqrt_double(p_146035_1_ * p_146035_1_ + p_146035_5_ * p_146035_5_);
        final float n = (float)(Math.atan2(p_146035_1_, p_146035_5_) * 180.0 / 3.141592653589793);
        this.rotationYaw = n;
        this.prevRotationYaw = n;
        final float n2 = (float)(Math.atan2(p_146035_3_, var10) * 180.0 / 3.141592653589793);
        this.rotationPitch = n2;
        this.prevRotationPitch = n2;
        this.field_146049_av = 0;
    }
    
    @Override
    public void setPositionAndRotation2(final double p_70056_1_, final double p_70056_3_, final double p_70056_5_, final float p_70056_7_, final float p_70056_8_, final int p_70056_9_) {
        this.field_146056_aC = p_70056_1_;
        this.field_146057_aD = p_70056_3_;
        this.field_146058_aE = p_70056_5_;
        this.field_146059_aF = p_70056_7_;
        this.field_146060_aG = p_70056_8_;
        this.field_146055_aB = p_70056_9_;
        this.motionX = this.field_146061_aH;
        this.motionY = this.field_146052_aI;
        this.motionZ = this.field_146053_aJ;
    }
    
    @Override
    public void setVelocity(final double p_70016_1_, final double p_70016_3_, final double p_70016_5_) {
        this.motionX = p_70016_1_;
        this.field_146061_aH = p_70016_1_;
        this.motionY = p_70016_3_;
        this.field_146052_aI = p_70016_3_;
        this.motionZ = p_70016_5_;
        this.field_146053_aJ = p_70016_5_;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.field_146055_aB > 0) {
            final double var27 = this.posX + (this.field_146056_aC - this.posX) / this.field_146055_aB;
            final double var28 = this.posY + (this.field_146057_aD - this.posY) / this.field_146055_aB;
            final double var29 = this.posZ + (this.field_146058_aE - this.posZ) / this.field_146055_aB;
            final double var30 = MathHelper.wrapAngleTo180_double(this.field_146059_aF - this.rotationYaw);
            this.rotationYaw += (float)(var30 / this.field_146055_aB);
            this.rotationPitch += (float)((this.field_146060_aG - this.rotationPitch) / this.field_146055_aB);
            --this.field_146055_aB;
            this.setPosition(var27, var28, var29);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
        else {
            if (!this.worldObj.isClient) {
                final ItemStack var31 = this.field_146042_b.getCurrentEquippedItem();
                if (this.field_146042_b.isDead || !this.field_146042_b.isEntityAlive() || var31 == null || var31.getItem() != Items.fishing_rod || this.getDistanceSqToEntity(this.field_146042_b) > 1024.0) {
                    this.setDead();
                    this.field_146042_b.fishEntity = null;
                    return;
                }
                if (this.field_146043_c != null) {
                    if (!this.field_146043_c.isDead) {
                        this.posX = this.field_146043_c.posX;
                        this.posY = this.field_146043_c.boundingBox.minY + this.field_146043_c.height * 0.8;
                        this.posZ = this.field_146043_c.posZ;
                        return;
                    }
                    this.field_146043_c = null;
                }
            }
            if (this.field_146044_a > 0) {
                --this.field_146044_a;
            }
            if (this.field_146051_au) {
                if (this.worldObj.getBlock(this.field_146037_g, this.field_146048_h, this.field_146050_i) == this.field_146046_j) {
                    ++this.field_146049_av;
                    if (this.field_146049_av == 1200) {
                        this.setDead();
                    }
                    return;
                }
                this.field_146051_au = false;
                this.motionX *= this.rand.nextFloat() * 0.2f;
                this.motionY *= this.rand.nextFloat() * 0.2f;
                this.motionZ *= this.rand.nextFloat() * 0.2f;
                this.field_146049_av = 0;
                this.field_146047_aw = 0;
            }
            else {
                ++this.field_146047_aw;
            }
            Vec3 var32 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            Vec3 var33 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            MovingObjectPosition var34 = this.worldObj.rayTraceBlocks(var32, var33);
            var32 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            var33 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            if (var34 != null) {
                var33 = Vec3.createVectorHelper(var34.hitVec.xCoord, var34.hitVec.yCoord, var34.hitVec.zCoord);
            }
            Entity var35 = null;
            final List var36 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
            double var37 = 0.0;
            for (int var38 = 0; var38 < var36.size(); ++var38) {
                final Entity var39 = var36.get(var38);
                if (var39.canBeCollidedWith() && (var39 != this.field_146042_b || this.field_146047_aw >= 5)) {
                    final float var40 = 0.3f;
                    final AxisAlignedBB var41 = var39.boundingBox.expand(var40, var40, var40);
                    final MovingObjectPosition var42 = var41.calculateIntercept(var32, var33);
                    if (var42 != null) {
                        final double var43 = var32.distanceTo(var42.hitVec);
                        if (var43 < var37 || var37 == 0.0) {
                            var35 = var39;
                            var37 = var43;
                        }
                    }
                }
            }
            if (var35 != null) {
                var34 = new MovingObjectPosition(var35);
            }
            if (var34 != null) {
                if (var34.entityHit != null) {
                    if (var34.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.field_146042_b), 0.0f)) {
                        this.field_146043_c = var34.entityHit;
                    }
                }
                else {
                    this.field_146051_au = true;
                }
            }
            if (!this.field_146051_au) {
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                final float var44 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
                this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
                this.rotationPitch = (float)(Math.atan2(this.motionY, var44) * 180.0 / 3.141592653589793);
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
                float var45 = 0.92f;
                if (this.onGround || this.isCollidedHorizontally) {
                    var45 = 0.5f;
                }
                final byte var46 = 5;
                double var47 = 0.0;
                for (int var48 = 0; var48 < var46; ++var48) {
                    final double var49 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (var48 + 0) / var46 - 0.125 + 0.125;
                    final double var50 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (var48 + 1) / var46 - 0.125 + 0.125;
                    final AxisAlignedBB var51 = AxisAlignedBB.getBoundingBox(this.boundingBox.minX, var49, this.boundingBox.minZ, this.boundingBox.maxX, var50, this.boundingBox.maxZ);
                    if (this.worldObj.isAABBInMaterial(var51, Material.water)) {
                        var47 += 1.0 / var46;
                    }
                }
                if (!this.worldObj.isClient && var47 > 0.0) {
                    final WorldServer var52 = (WorldServer)this.worldObj;
                    int var53 = 1;
                    if (this.rand.nextFloat() < 0.25f && this.worldObj.canLightningStrikeAt(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) + 1, MathHelper.floor_double(this.posZ))) {
                        var53 = 2;
                    }
                    if (this.rand.nextFloat() < 0.5f && !this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) + 1, MathHelper.floor_double(this.posZ))) {
                        --var53;
                    }
                    if (this.field_146045_ax > 0) {
                        --this.field_146045_ax;
                        if (this.field_146045_ax <= 0) {
                            this.field_146040_ay = 0;
                            this.field_146038_az = 0;
                        }
                    }
                    else if (this.field_146038_az > 0) {
                        this.field_146038_az -= var53;
                        if (this.field_146038_az <= 0) {
                            this.motionY -= 0.20000000298023224;
                            this.playSound("random.splash", 0.25f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                            final float var54 = (float)MathHelper.floor_double(this.boundingBox.minY);
                            var52.func_147487_a("bubble", this.posX, var54 + 1.0f, this.posZ, (int)(1.0f + this.width * 20.0f), this.width, 0.0, this.width, 0.20000000298023224);
                            var52.func_147487_a("wake", this.posX, var54 + 1.0f, this.posZ, (int)(1.0f + this.width * 20.0f), this.width, 0.0, this.width, 0.20000000298023224);
                            this.field_146045_ax = MathHelper.getRandomIntegerInRange(this.rand, 10, 30);
                        }
                        else {
                            this.field_146054_aA += (float)(this.rand.nextGaussian() * 4.0);
                            final float var54 = this.field_146054_aA * 0.017453292f;
                            final float var55 = MathHelper.sin(var54);
                            final float var56 = MathHelper.cos(var54);
                            final double var57 = this.posX + var55 * this.field_146038_az * 0.1f;
                            final double var58 = MathHelper.floor_double(this.boundingBox.minY) + 1.0f;
                            final double var59 = this.posZ + var56 * this.field_146038_az * 0.1f;
                            if (this.rand.nextFloat() < 0.15f) {
                                var52.func_147487_a("bubble", var57, var58 - 0.10000000149011612, var59, 1, var55, 0.1, var56, 0.0);
                            }
                            final float var60 = var55 * 0.04f;
                            final float var61 = var56 * 0.04f;
                            var52.func_147487_a("wake", var57, var58, var59, 0, var61, 0.01, -var60, 1.0);
                            var52.func_147487_a("wake", var57, var58, var59, 0, -var61, 0.01, var60, 1.0);
                        }
                    }
                    else if (this.field_146040_ay > 0) {
                        this.field_146040_ay -= var53;
                        float var54 = 0.15f;
                        if (this.field_146040_ay < 20) {
                            var54 += (float)((20 - this.field_146040_ay) * 0.05);
                        }
                        else if (this.field_146040_ay < 40) {
                            var54 += (float)((40 - this.field_146040_ay) * 0.02);
                        }
                        else if (this.field_146040_ay < 60) {
                            var54 += (float)((60 - this.field_146040_ay) * 0.01);
                        }
                        if (this.rand.nextFloat() < var54) {
                            final float var55 = MathHelper.randomFloatClamp(this.rand, 0.0f, 360.0f) * 0.017453292f;
                            final float var56 = MathHelper.randomFloatClamp(this.rand, 25.0f, 60.0f);
                            final double var57 = this.posX + MathHelper.sin(var55) * var56 * 0.1f;
                            final double var58 = MathHelper.floor_double(this.boundingBox.minY) + 1.0f;
                            final double var59 = this.posZ + MathHelper.cos(var55) * var56 * 0.1f;
                            var52.func_147487_a("splash", var57, var58, var59, 2 + this.rand.nextInt(2), 0.10000000149011612, 0.0, 0.10000000149011612, 0.0);
                        }
                        if (this.field_146040_ay <= 0) {
                            this.field_146054_aA = MathHelper.randomFloatClamp(this.rand, 0.0f, 360.0f);
                            this.field_146038_az = MathHelper.getRandomIntegerInRange(this.rand, 20, 80);
                        }
                    }
                    else {
                        this.field_146040_ay = MathHelper.getRandomIntegerInRange(this.rand, 100, 900);
                        this.field_146040_ay -= EnchantmentHelper.func_151387_h(this.field_146042_b) * 20 * 5;
                    }
                    if (this.field_146045_ax > 0) {
                        this.motionY -= this.rand.nextFloat() * this.rand.nextFloat() * this.rand.nextFloat() * 0.2;
                    }
                }
                final double var43 = var47 * 2.0 - 1.0;
                this.motionY += 0.03999999910593033 * var43;
                if (var47 > 0.0) {
                    var45 *= (float)0.9;
                    this.motionY *= 0.8;
                }
                this.motionX *= var45;
                this.motionY *= var45;
                this.motionZ *= var45;
                this.setPosition(this.posX, this.posY, this.posZ);
            }
        }
    }
    
    public void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        p_70014_1_.setShort("xTile", (short)this.field_146037_g);
        p_70014_1_.setShort("yTile", (short)this.field_146048_h);
        p_70014_1_.setShort("zTile", (short)this.field_146050_i);
        p_70014_1_.setByte("inTile", (byte)Block.getIdFromBlock(this.field_146046_j));
        p_70014_1_.setByte("shake", (byte)this.field_146044_a);
        p_70014_1_.setByte("inGround", (byte)(this.field_146051_au ? 1 : 0));
    }
    
    public void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        this.field_146037_g = p_70037_1_.getShort("xTile");
        this.field_146048_h = p_70037_1_.getShort("yTile");
        this.field_146050_i = p_70037_1_.getShort("zTile");
        this.field_146046_j = Block.getBlockById(p_70037_1_.getByte("inTile") & 0xFF);
        this.field_146044_a = (p_70037_1_.getByte("shake") & 0xFF);
        this.field_146051_au = (p_70037_1_.getByte("inGround") == 1);
    }
    
    @Override
    public float getShadowSize() {
        return 0.0f;
    }
    
    public int func_146034_e() {
        if (this.worldObj.isClient) {
            return 0;
        }
        byte var1 = 0;
        if (this.field_146043_c != null) {
            final double var2 = this.field_146042_b.posX - this.posX;
            final double var3 = this.field_146042_b.posY - this.posY;
            final double var4 = this.field_146042_b.posZ - this.posZ;
            final double var5 = MathHelper.sqrt_double(var2 * var2 + var3 * var3 + var4 * var4);
            final double var6 = 0.1;
            final Entity field_146043_c = this.field_146043_c;
            field_146043_c.motionX += var2 * var6;
            final Entity field_146043_c2 = this.field_146043_c;
            field_146043_c2.motionY += var3 * var6 + MathHelper.sqrt_double(var5) * 0.08;
            final Entity field_146043_c3 = this.field_146043_c;
            field_146043_c3.motionZ += var4 * var6;
            var1 = 3;
        }
        else if (this.field_146045_ax > 0) {
            final EntityItem var7 = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, this.func_146033_f());
            final double var8 = this.field_146042_b.posX - this.posX;
            final double var9 = this.field_146042_b.posY - this.posY;
            final double var10 = this.field_146042_b.posZ - this.posZ;
            final double var11 = MathHelper.sqrt_double(var8 * var8 + var9 * var9 + var10 * var10);
            final double var12 = 0.1;
            var7.motionX = var8 * var12;
            var7.motionY = var9 * var12 + MathHelper.sqrt_double(var11) * 0.08;
            var7.motionZ = var10 * var12;
            this.worldObj.spawnEntityInWorld(var7);
            this.field_146042_b.worldObj.spawnEntityInWorld(new EntityXPOrb(this.field_146042_b.worldObj, this.field_146042_b.posX, this.field_146042_b.posY + 0.5, this.field_146042_b.posZ + 0.5, this.rand.nextInt(6) + 1));
            var1 = 1;
        }
        if (this.field_146051_au) {
            var1 = 2;
        }
        this.setDead();
        this.field_146042_b.fishEntity = null;
        return var1;
    }
    
    private ItemStack func_146033_f() {
        float var1 = this.worldObj.rand.nextFloat();
        final int var2 = EnchantmentHelper.func_151386_g(this.field_146042_b);
        final int var3 = EnchantmentHelper.func_151387_h(this.field_146042_b);
        float var4 = 0.1f - var2 * 0.025f - var3 * 0.01f;
        float var5 = 0.05f + var2 * 0.01f - var3 * 0.01f;
        var4 = MathHelper.clamp_float(var4, 0.0f, 1.0f);
        var5 = MathHelper.clamp_float(var5, 0.0f, 1.0f);
        if (var1 < var4) {
            this.field_146042_b.addStat(StatList.field_151183_A, 1);
            return ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, EntityFishHook.field_146039_d)).func_150708_a(this.rand);
        }
        var1 -= var4;
        if (var1 < var5) {
            this.field_146042_b.addStat(StatList.field_151184_B, 1);
            return ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, EntityFishHook.field_146041_e)).func_150708_a(this.rand);
        }
        final float var6 = var1 - var5;
        this.field_146042_b.addStat(StatList.fishCaughtStat, 1);
        return ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, EntityFishHook.field_146036_f)).func_150708_a(this.rand);
    }
    
    @Override
    public void setDead() {
        super.setDead();
        if (this.field_146042_b != null) {
            this.field_146042_b.fishEntity = null;
        }
    }
    
    static {
        field_146039_d = Arrays.asList(new WeightedRandomFishable(new ItemStack(Items.leather_boots), 10).func_150709_a(0.9f), new WeightedRandomFishable(new ItemStack(Items.leather), 10), new WeightedRandomFishable(new ItemStack(Items.bone), 10), new WeightedRandomFishable(new ItemStack(Items.potionitem), 10), new WeightedRandomFishable(new ItemStack(Items.string), 5), new WeightedRandomFishable(new ItemStack(Items.fishing_rod), 2).func_150709_a(0.9f), new WeightedRandomFishable(new ItemStack(Items.bowl), 10), new WeightedRandomFishable(new ItemStack(Items.stick), 5), new WeightedRandomFishable(new ItemStack(Items.dye, 10, 0), 1), new WeightedRandomFishable(new ItemStack(Blocks.tripwire_hook), 10), new WeightedRandomFishable(new ItemStack(Items.rotten_flesh), 10));
        field_146041_e = Arrays.asList(new WeightedRandomFishable(new ItemStack(Blocks.waterlily), 1), new WeightedRandomFishable(new ItemStack(Items.name_tag), 1), new WeightedRandomFishable(new ItemStack(Items.saddle), 1), new WeightedRandomFishable(new ItemStack(Items.bow), 1).func_150709_a(0.25f).func_150707_a(), new WeightedRandomFishable(new ItemStack(Items.fishing_rod), 1).func_150709_a(0.25f).func_150707_a(), new WeightedRandomFishable(new ItemStack(Items.book), 1).func_150707_a());
        field_146036_f = Arrays.asList(new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.COD.func_150976_a()), 60), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.SALMON.func_150976_a()), 25), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.CLOWNFISH.func_150976_a()), 2), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.PUFFERFISH.func_150976_a()), 13));
    }
}
