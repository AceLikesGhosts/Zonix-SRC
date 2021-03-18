package net.minecraft.client.particle;

import net.minecraft.world.*;

public class EntityAuraFX extends EntityFX
{
    private static final String __OBFID = "CL_00000929";
    
    public EntityAuraFX(final World p_i1232_1_, final double p_i1232_2_, final double p_i1232_4_, final double p_i1232_6_, final double p_i1232_8_, final double p_i1232_10_, final double p_i1232_12_) {
        super(p_i1232_1_, p_i1232_2_, p_i1232_4_, p_i1232_6_, p_i1232_8_, p_i1232_10_, p_i1232_12_);
        final float var14 = this.rand.nextFloat() * 0.1f + 0.2f;
        this.particleRed = var14;
        this.particleGreen = var14;
        this.particleBlue = var14;
        this.setParticleTextureIndex(0);
        this.setSize(0.02f, 0.02f);
        this.particleScale *= this.rand.nextFloat() * 0.6f + 0.5f;
        this.motionX *= 0.019999999552965164;
        this.motionY *= 0.019999999552965164;
        this.motionZ *= 0.019999999552965164;
        this.particleMaxAge = (int)(20.0 / (Math.random() * 0.8 + 0.2));
        this.noClip = true;
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.99;
        this.motionY *= 0.99;
        this.motionZ *= 0.99;
        if (this.particleMaxAge-- <= 0) {
            this.setDead();
        }
    }
}
