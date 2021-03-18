package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

public class EntityCloudFX extends EntityFX
{
    float field_70569_a;
    private static final String __OBFID = "CL_00000920";
    
    public EntityCloudFX(final World p_i1221_1_, final double p_i1221_2_, final double p_i1221_4_, final double p_i1221_6_, final double p_i1221_8_, final double p_i1221_10_, final double p_i1221_12_) {
        super(p_i1221_1_, p_i1221_2_, p_i1221_4_, p_i1221_6_, 0.0, 0.0, 0.0);
        final float var14 = 2.5f;
        this.motionX *= 0.10000000149011612;
        this.motionY *= 0.10000000149011612;
        this.motionZ *= 0.10000000149011612;
        this.motionX += p_i1221_8_;
        this.motionY += p_i1221_10_;
        this.motionZ += p_i1221_12_;
        final float particleRed = 1.0f - (float)(Math.random() * 0.30000001192092896);
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleScale *= 0.75f;
        this.particleScale *= var14;
        this.field_70569_a = this.particleScale;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.3));
        this.particleMaxAge *= (int)var14;
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
        this.particleScale = this.field_70569_a * var8;
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
        this.motionX *= 0.9599999785423279;
        this.motionY *= 0.9599999785423279;
        this.motionZ *= 0.9599999785423279;
        final EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, 2.0);
        if (var1 != null && this.posY > var1.boundingBox.minY) {
            this.posY += (var1.boundingBox.minY - this.posY) * 0.2;
            this.motionY += (var1.motionY - this.motionY) * 0.2;
            this.setPosition(this.posX, this.posY, this.posZ);
        }
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }
}
