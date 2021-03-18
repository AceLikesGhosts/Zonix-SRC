package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.*;

public class EntityBreakingFX extends EntityFX
{
    private static final String __OBFID = "CL_00000897";
    
    public EntityBreakingFX(final World p_i1195_1_, final double p_i1195_2_, final double p_i1195_4_, final double p_i1195_6_, final Item p_i1195_8_) {
        this(p_i1195_1_, p_i1195_2_, p_i1195_4_, p_i1195_6_, p_i1195_8_, 0);
    }
    
    public EntityBreakingFX(final World p_i1196_1_, final double p_i1196_2_, final double p_i1196_4_, final double p_i1196_6_, final Item p_i1196_8_, final int p_i1196_9_) {
        super(p_i1196_1_, p_i1196_2_, p_i1196_4_, p_i1196_6_, 0.0, 0.0, 0.0);
        this.setParticleIcon(p_i1196_8_.getIconFromDamage(p_i1196_9_));
        final float particleRed = 1.0f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleGravity = Blocks.snow.blockParticleGravity;
        this.particleScale /= 2.0f;
    }
    
    public EntityBreakingFX(final World p_i1197_1_, final double p_i1197_2_, final double p_i1197_4_, final double p_i1197_6_, final double p_i1197_8_, final double p_i1197_10_, final double p_i1197_12_, final Item p_i1197_14_, final int p_i1197_15_) {
        this(p_i1197_1_, p_i1197_2_, p_i1197_4_, p_i1197_6_, p_i1197_14_, p_i1197_15_);
        this.motionX *= 0.10000000149011612;
        this.motionY *= 0.10000000149011612;
        this.motionZ *= 0.10000000149011612;
        this.motionX += p_i1197_8_;
        this.motionY += p_i1197_10_;
        this.motionZ += p_i1197_12_;
    }
    
    @Override
    public int getFXLayer() {
        return 2;
    }
    
    @Override
    public void renderParticle(final Tessellator p_70539_1_, final float p_70539_2_, final float p_70539_3_, final float p_70539_4_, final float p_70539_5_, final float p_70539_6_, final float p_70539_7_) {
        float var8 = (this.particleTextureIndexX + this.particleTextureJitterX / 4.0f) / 16.0f;
        float var9 = var8 + 0.015609375f;
        float var10 = (this.particleTextureIndexY + this.particleTextureJitterY / 4.0f) / 16.0f;
        float var11 = var10 + 0.015609375f;
        final float var12 = 0.1f * this.particleScale;
        if (this.particleIcon != null) {
            var8 = this.particleIcon.getInterpolatedU(this.particleTextureJitterX / 4.0f * 16.0f);
            var9 = this.particleIcon.getInterpolatedU((this.particleTextureJitterX + 1.0f) / 4.0f * 16.0f);
            var10 = this.particleIcon.getInterpolatedV(this.particleTextureJitterY / 4.0f * 16.0f);
            var11 = this.particleIcon.getInterpolatedV((this.particleTextureJitterY + 1.0f) / 4.0f * 16.0f);
        }
        final float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * p_70539_2_ - EntityBreakingFX.interpPosX);
        final float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * p_70539_2_ - EntityBreakingFX.interpPosY);
        final float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * p_70539_2_ - EntityBreakingFX.interpPosZ);
        p_70539_1_.setColorOpaque_F(this.particleRed, this.particleGreen, this.particleBlue);
        p_70539_1_.addVertexWithUV(var13 - p_70539_3_ * var12 - p_70539_6_ * var12, var14 - p_70539_4_ * var12, var15 - p_70539_5_ * var12 - p_70539_7_ * var12, var8, var11);
        p_70539_1_.addVertexWithUV(var13 - p_70539_3_ * var12 + p_70539_6_ * var12, var14 + p_70539_4_ * var12, var15 - p_70539_5_ * var12 + p_70539_7_ * var12, var8, var10);
        p_70539_1_.addVertexWithUV(var13 + p_70539_3_ * var12 + p_70539_6_ * var12, var14 + p_70539_4_ * var12, var15 + p_70539_5_ * var12 + p_70539_7_ * var12, var9, var10);
        p_70539_1_.addVertexWithUV(var13 + p_70539_3_ * var12 - p_70539_6_ * var12, var14 - p_70539_4_ * var12, var15 + p_70539_5_ * var12 - p_70539_7_ * var12, var9, var11);
    }
}
