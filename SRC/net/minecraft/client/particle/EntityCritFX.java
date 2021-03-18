package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.client.renderer.*;

public class EntityCritFX extends EntityFX
{
    float initialParticleScale;
    private static final String __OBFID = "CL_00000900";
    
    public EntityCritFX(final World p_i1201_1_, final double p_i1201_2_, final double p_i1201_4_, final double p_i1201_6_, final double p_i1201_8_, final double p_i1201_10_, final double p_i1201_12_) {
        this(p_i1201_1_, p_i1201_2_, p_i1201_4_, p_i1201_6_, p_i1201_8_, p_i1201_10_, p_i1201_12_, 1.0f);
    }
    
    public EntityCritFX(final World p_i1202_1_, final double p_i1202_2_, final double p_i1202_4_, final double p_i1202_6_, final double p_i1202_8_, final double p_i1202_10_, final double p_i1202_12_, final float p_i1202_14_) {
        super(p_i1202_1_, p_i1202_2_, p_i1202_4_, p_i1202_6_, 0.0, 0.0, 0.0);
        this.motionX *= 0.10000000149011612;
        this.motionY *= 0.10000000149011612;
        this.motionZ *= 0.10000000149011612;
        this.motionX += p_i1202_8_ * 0.4;
        this.motionY += p_i1202_10_ * 0.4;
        this.motionZ += p_i1202_12_ * 0.4;
        final float particleRed = (float)(Math.random() * 0.30000001192092896 + 0.6000000238418579);
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleScale *= 0.75f;
        this.particleScale *= p_i1202_14_;
        this.initialParticleScale = this.particleScale;
        this.particleMaxAge = (int)(6.0 / (Math.random() * 0.8 + 0.6));
        this.particleMaxAge *= (int)p_i1202_14_;
        this.noClip = false;
        this.setParticleTextureIndex(65);
        this.onUpdate();
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
        this.particleScale = this.initialParticleScale * var8;
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
        this.particleGreen *= (float)0.96;
        this.particleBlue *= (float)0.9;
        this.motionX *= 0.699999988079071;
        this.motionY *= 0.699999988079071;
        this.motionZ *= 0.699999988079071;
        this.motionY -= 0.019999999552965164;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }
}
