package net.minecraft.client.particle;

import net.minecraft.world.*;

public class EntityExplodeFX extends EntityFX
{
    private static final String __OBFID = "CL_00000903";
    
    public EntityExplodeFX(final World p_i1205_1_, final double p_i1205_2_, final double p_i1205_4_, final double p_i1205_6_, final double p_i1205_8_, final double p_i1205_10_, final double p_i1205_12_) {
        super(p_i1205_1_, p_i1205_2_, p_i1205_4_, p_i1205_6_, p_i1205_8_, p_i1205_10_, p_i1205_12_);
        this.motionX = p_i1205_8_ + (float)(Math.random() * 2.0 - 1.0) * 0.05f;
        this.motionY = p_i1205_10_ + (float)(Math.random() * 2.0 - 1.0) * 0.05f;
        this.motionZ = p_i1205_12_ + (float)(Math.random() * 2.0 - 1.0) * 0.05f;
        final float particleRed = this.rand.nextFloat() * 0.3f + 0.7f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleScale = this.rand.nextFloat() * this.rand.nextFloat() * 6.0f + 1.0f;
        this.particleMaxAge = (int)(16.0 / (this.rand.nextFloat() * 0.8 + 0.2)) + 2;
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
        this.motionY += 0.004;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.8999999761581421;
        this.motionY *= 0.8999999761581421;
        this.motionZ *= 0.8999999761581421;
        if (this.onGround) {
            this.motionX *= 0.699999988079071;
            this.motionZ *= 0.699999988079071;
        }
    }
}
