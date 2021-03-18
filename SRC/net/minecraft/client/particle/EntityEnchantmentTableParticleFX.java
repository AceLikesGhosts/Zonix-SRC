package net.minecraft.client.particle;

import net.minecraft.world.*;

public class EntityEnchantmentTableParticleFX extends EntityFX
{
    private float field_70565_a;
    private double field_70568_aq;
    private double field_70567_ar;
    private double field_70566_as;
    private static final String __OBFID = "CL_00000902";
    
    public EntityEnchantmentTableParticleFX(final World p_i1204_1_, final double p_i1204_2_, final double p_i1204_4_, final double p_i1204_6_, final double p_i1204_8_, final double p_i1204_10_, final double p_i1204_12_) {
        super(p_i1204_1_, p_i1204_2_, p_i1204_4_, p_i1204_6_, p_i1204_8_, p_i1204_10_, p_i1204_12_);
        this.motionX = p_i1204_8_;
        this.motionY = p_i1204_10_;
        this.motionZ = p_i1204_12_;
        this.posX = p_i1204_2_;
        this.field_70568_aq = p_i1204_2_;
        this.posY = p_i1204_4_;
        this.field_70567_ar = p_i1204_4_;
        this.posZ = p_i1204_6_;
        this.field_70566_as = p_i1204_6_;
        final float var14 = this.rand.nextFloat() * 0.6f + 0.4f;
        final float n = this.rand.nextFloat() * 0.5f + 0.2f;
        this.particleScale = n;
        this.field_70565_a = n;
        final float particleRed = 1.0f * var14;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleGreen *= 0.9f;
        this.particleRed *= 0.9f;
        this.particleMaxAge = (int)(Math.random() * 10.0) + 30;
        this.noClip = true;
        this.setParticleTextureIndex((int)(Math.random() * 26.0 + 1.0 + 224.0));
    }
    
    @Override
    public int getBrightnessForRender(final float p_70070_1_) {
        final int var2 = super.getBrightnessForRender(p_70070_1_);
        float var3 = this.particleAge / (float)this.particleMaxAge;
        var3 *= var3;
        var3 *= var3;
        final int var4 = var2 & 0xFF;
        int var5 = var2 >> 16 & 0xFF;
        var5 += (int)(var3 * 15.0f * 16.0f);
        if (var5 > 240) {
            var5 = 240;
        }
        return var4 | var5 << 16;
    }
    
    @Override
    public float getBrightness(final float p_70013_1_) {
        final float var2 = super.getBrightness(p_70013_1_);
        float var3 = this.particleAge / (float)this.particleMaxAge;
        var3 *= var3;
        var3 *= var3;
        return var2 * (1.0f - var3) + var3;
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        float var1 = this.particleAge / (float)this.particleMaxAge;
        var1 = 1.0f - var1;
        float var2 = 1.0f - var1;
        var2 *= var2;
        var2 *= var2;
        this.posX = this.field_70568_aq + this.motionX * var1;
        this.posY = this.field_70567_ar + this.motionY * var1 - var2 * 1.2f;
        this.posZ = this.field_70566_as + this.motionZ * var1;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
    }
}
