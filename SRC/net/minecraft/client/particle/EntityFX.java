package net.minecraft.client.particle;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.nbt.*;

public class EntityFX extends Entity
{
    protected int particleTextureIndexX;
    protected int particleTextureIndexY;
    protected float particleTextureJitterX;
    protected float particleTextureJitterY;
    protected int particleAge;
    protected int particleMaxAge;
    protected float particleScale;
    protected float particleGravity;
    protected float particleRed;
    protected float particleGreen;
    protected float particleBlue;
    protected float particleAlpha;
    protected IIcon particleIcon;
    public static double interpPosX;
    public static double interpPosY;
    public static double interpPosZ;
    private static final String __OBFID = "CL_00000914";
    
    protected EntityFX(final World p_i46352_1_, final double p_i46352_2_, final double p_i46352_4_, final double p_i46352_6_) {
        super(p_i46352_1_);
        this.particleAlpha = 1.0f;
        this.setSize(0.2f, 0.2f);
        this.yOffset = this.height / 2.0f;
        this.setPosition(p_i46352_2_, p_i46352_4_, p_i46352_6_);
        this.lastTickPosX = p_i46352_2_;
        this.lastTickPosY = p_i46352_4_;
        this.lastTickPosZ = p_i46352_6_;
        final float particleRed = 1.0f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleTextureJitterX = this.rand.nextFloat() * 3.0f;
        this.particleTextureJitterY = this.rand.nextFloat() * 3.0f;
        this.particleScale = (this.rand.nextFloat() * 0.5f + 0.5f) * 2.0f;
        this.particleMaxAge = (int)(4.0f / (this.rand.nextFloat() * 0.9f + 0.1f));
        this.particleAge = 0;
    }
    
    public EntityFX(final World p_i1219_1_, final double p_i1219_2_, final double p_i1219_4_, final double p_i1219_6_, final double p_i1219_8_, final double p_i1219_10_, final double p_i1219_12_) {
        this(p_i1219_1_, p_i1219_2_, p_i1219_4_, p_i1219_6_);
        this.motionX = p_i1219_8_ + (float)(Math.random() * 2.0 - 1.0) * 0.4f;
        this.motionY = p_i1219_10_ + (float)(Math.random() * 2.0 - 1.0) * 0.4f;
        this.motionZ = p_i1219_12_ + (float)(Math.random() * 2.0 - 1.0) * 0.4f;
        final float var14 = (float)(Math.random() + Math.random() + 1.0) * 0.15f;
        final float var15 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        this.motionX = this.motionX / var15 * var14 * 0.4000000059604645;
        this.motionY = this.motionY / var15 * var14 * 0.4000000059604645 + 0.10000000149011612;
        this.motionZ = this.motionZ / var15 * var14 * 0.4000000059604645;
    }
    
    public EntityFX multiplyVelocity(final float p_70543_1_) {
        this.motionX *= p_70543_1_;
        this.motionY = (this.motionY - 0.10000000149011612) * p_70543_1_ + 0.10000000149011612;
        this.motionZ *= p_70543_1_;
        return this;
    }
    
    public EntityFX multipleParticleScaleBy(final float p_70541_1_) {
        this.setSize(0.2f * p_70541_1_, 0.2f * p_70541_1_);
        this.particleScale *= p_70541_1_;
        return this;
    }
    
    public void setRBGColorF(final float p_70538_1_, final float p_70538_2_, final float p_70538_3_) {
        this.particleRed = p_70538_1_;
        this.particleGreen = p_70538_2_;
        this.particleBlue = p_70538_3_;
    }
    
    public void setAlphaF(final float p_82338_1_) {
        this.particleAlpha = p_82338_1_;
    }
    
    public float getRedColorF() {
        return this.particleRed;
    }
    
    public float getGreenColorF() {
        return this.particleGreen;
    }
    
    public float getBlueColorF() {
        return this.particleBlue;
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
        this.motionY -= 0.04 * this.particleGravity;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= 0.9800000190734863;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }
    
    public void renderParticle(final Tessellator p_70539_1_, final float p_70539_2_, final float p_70539_3_, final float p_70539_4_, final float p_70539_5_, final float p_70539_6_, final float p_70539_7_) {
        float var8 = this.particleTextureIndexX / 16.0f;
        float var9 = var8 + 0.0624375f;
        float var10 = this.particleTextureIndexY / 16.0f;
        float var11 = var10 + 0.0624375f;
        final float var12 = 0.1f * this.particleScale;
        if (this.particleIcon != null) {
            var8 = this.particleIcon.getMinU();
            var9 = this.particleIcon.getMaxU();
            var10 = this.particleIcon.getMinV();
            var11 = this.particleIcon.getMaxV();
        }
        final float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * p_70539_2_ - EntityFX.interpPosX);
        final float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * p_70539_2_ - EntityFX.interpPosY);
        final float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * p_70539_2_ - EntityFX.interpPosZ);
        p_70539_1_.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
        p_70539_1_.addVertexWithUV(var13 - p_70539_3_ * var12 - p_70539_6_ * var12, var14 - p_70539_4_ * var12, var15 - p_70539_5_ * var12 - p_70539_7_ * var12, var9, var11);
        p_70539_1_.addVertexWithUV(var13 - p_70539_3_ * var12 + p_70539_6_ * var12, var14 + p_70539_4_ * var12, var15 - p_70539_5_ * var12 + p_70539_7_ * var12, var9, var10);
        p_70539_1_.addVertexWithUV(var13 + p_70539_3_ * var12 + p_70539_6_ * var12, var14 + p_70539_4_ * var12, var15 + p_70539_5_ * var12 + p_70539_7_ * var12, var8, var10);
        p_70539_1_.addVertexWithUV(var13 + p_70539_3_ * var12 - p_70539_6_ * var12, var14 - p_70539_4_ * var12, var15 + p_70539_5_ * var12 - p_70539_7_ * var12, var8, var11);
    }
    
    public int getFXLayer() {
        return 0;
    }
    
    public void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
    }
    
    public void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
    }
    
    public void setParticleIcon(final IIcon p_110125_1_) {
        if (this.getFXLayer() == 1) {
            this.particleIcon = p_110125_1_;
        }
        else {
            if (this.getFXLayer() != 2) {
                throw new RuntimeException("Invalid call to Particle.setTex, use coordinate methods");
            }
            this.particleIcon = p_110125_1_;
        }
    }
    
    public void setParticleTextureIndex(final int p_70536_1_) {
        if (this.getFXLayer() != 0) {
            throw new RuntimeException("Invalid call to Particle.setMiscTex");
        }
        this.particleTextureIndexX = p_70536_1_ % 16;
        this.particleTextureIndexY = p_70536_1_ / 16;
    }
    
    public void nextTextureIndexX() {
        ++this.particleTextureIndexX;
    }
    
    @Override
    public boolean canAttackWithItem() {
        return false;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ", Pos (" + this.posX + "," + this.posY + "," + this.posZ + "), RGBA (" + this.particleRed + "," + this.particleGreen + "," + this.particleBlue + "," + this.particleAlpha + "), Age " + this.particleAge;
    }
}
