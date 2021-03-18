package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.client.renderer.*;

public class EntityHeartFX extends EntityFX
{
    float particleScaleOverTime;
    private static final String __OBFID = "CL_00000909";
    
    public EntityHeartFX(final World p_i1211_1_, final double p_i1211_2_, final double p_i1211_4_, final double p_i1211_6_, final double p_i1211_8_, final double p_i1211_10_, final double p_i1211_12_) {
        this(p_i1211_1_, p_i1211_2_, p_i1211_4_, p_i1211_6_, p_i1211_8_, p_i1211_10_, p_i1211_12_, 2.0f);
    }
    
    public EntityHeartFX(final World p_i46354_1_, final double p_i46354_2_, final double p_i46354_4_, final double p_i46354_6_, final double p_i46354_8_, final double p_i46354_10_, final double p_i46354_12_, final float p_i46354_14_) {
        super(p_i46354_1_, p_i46354_2_, p_i46354_4_, p_i46354_6_, 0.0, 0.0, 0.0);
        this.motionX *= 0.009999999776482582;
        this.motionY *= 0.009999999776482582;
        this.motionZ *= 0.009999999776482582;
        this.motionY += 0.1;
        this.particleScale *= 0.75f;
        this.particleScale *= p_i46354_14_;
        this.particleScaleOverTime = this.particleScale;
        this.particleMaxAge = 16;
        this.noClip = false;
        this.setParticleTextureIndex(80);
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
        this.particleScale = this.particleScaleOverTime * var8;
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
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (this.posY == this.prevPosY) {
            this.motionX *= 1.1;
            this.motionZ *= 1.1;
        }
        this.motionX *= 0.8600000143051147;
        this.motionY *= 0.8600000143051147;
        this.motionZ *= 0.8600000143051147;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }
}
