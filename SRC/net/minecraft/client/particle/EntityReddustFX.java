package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.client.renderer.*;

public class EntityReddustFX extends EntityFX
{
    float reddustParticleScale;
    private static final String __OBFID = "CL_00000923";
    
    public EntityReddustFX(final World p_i46349_1_, final double p_i46349_2_, final double p_i46349_4_, final double p_i46349_6_, final float p_i46349_8_, final float p_i46349_9_, final float p_i46349_10_) {
        this(p_i46349_1_, p_i46349_2_, p_i46349_4_, p_i46349_6_, 1.0f, p_i46349_8_, p_i46349_9_, p_i46349_10_);
    }
    
    public EntityReddustFX(final World p_i46350_1_, final double p_i46350_2_, final double p_i46350_4_, final double p_i46350_6_, final float p_i46350_8_, float p_i46350_9_, final float p_i46350_10_, final float p_i46350_11_) {
        super(p_i46350_1_, p_i46350_2_, p_i46350_4_, p_i46350_6_, 0.0, 0.0, 0.0);
        this.motionX *= 0.10000000149011612;
        this.motionY *= 0.10000000149011612;
        this.motionZ *= 0.10000000149011612;
        if (p_i46350_9_ == 0.0f) {
            p_i46350_9_ = 1.0f;
        }
        final float var12 = (float)Math.random() * 0.4f + 0.6f;
        this.particleRed = ((float)(Math.random() * 0.20000000298023224) + 0.8f) * p_i46350_9_ * var12;
        this.particleGreen = ((float)(Math.random() * 0.20000000298023224) + 0.8f) * p_i46350_10_ * var12;
        this.particleBlue = ((float)(Math.random() * 0.20000000298023224) + 0.8f) * p_i46350_11_ * var12;
        this.particleScale *= 0.75f;
        this.particleScale *= p_i46350_8_;
        this.reddustParticleScale = this.particleScale;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
        this.particleMaxAge *= (int)p_i46350_8_;
        this.noClip = false;
    }
    
    @Override
    public void renderParticle(final Tessellator p_70539_1_, final float p_70539_2_, final float p_70539_3_, final float p_70539_4_, final float p_70539_5_, final float p_70539_6_, final float p_70539_7_) {
        float var8 = (this.particleAge + p_70539_2_) / this.particleMaxAge * 32.0f;
        if (var8 < 0.0f) {
            var8 = 0.0f;
        }
        if (var8 > 1.0f) {
            var8 = 1.0f;
        }
        this.particleScale = this.reddustParticleScale * var8;
        super.renderParticle(p_70539_1_, p_70539_2_, p_70539_3_, p_70539_4_, p_70539_5_, p_70539_6_, p_70539_7_);
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
        this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (this.posY == this.prevPosY) {
            this.motionX *= 1.1;
            this.motionZ *= 1.1;
        }
        this.motionX *= 0.9599999785423279;
        this.motionY *= 0.9599999785423279;
        this.motionZ *= 0.9599999785423279;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }
}
