package net.minecraft.client.particle;

import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;

public class EntityDiggingFX extends EntityFX
{
    private Block field_145784_a;
    private static final String __OBFID = "CL_00000932";
    
    public EntityDiggingFX(final World p_i1234_1_, final double p_i1234_2_, final double p_i1234_4_, final double p_i1234_6_, final double p_i1234_8_, final double p_i1234_10_, final double p_i1234_12_, final Block p_i1234_14_, final int p_i1234_15_) {
        super(p_i1234_1_, p_i1234_2_, p_i1234_4_, p_i1234_6_, p_i1234_8_, p_i1234_10_, p_i1234_12_);
        this.field_145784_a = p_i1234_14_;
        this.setParticleIcon(p_i1234_14_.getIcon(0, p_i1234_15_));
        this.particleGravity = p_i1234_14_.blockParticleGravity;
        final float particleRed = 0.6f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleScale /= 2.0f;
    }
    
    public EntityDiggingFX applyColourMultiplier(final int p_70596_1_, final int p_70596_2_, final int p_70596_3_) {
        if (this.field_145784_a == Blocks.grass) {
            return this;
        }
        final int var4 = this.field_145784_a.colorMultiplier(this.worldObj, p_70596_1_, p_70596_2_, p_70596_3_);
        this.particleRed *= (var4 >> 16 & 0xFF) / 255.0f;
        this.particleGreen *= (var4 >> 8 & 0xFF) / 255.0f;
        this.particleBlue *= (var4 & 0xFF) / 255.0f;
        return this;
    }
    
    public EntityDiggingFX applyRenderColor(final int p_90019_1_) {
        if (this.field_145784_a == Blocks.grass) {
            return this;
        }
        final int var2 = this.field_145784_a.getRenderColor(p_90019_1_);
        this.particleRed *= (var2 >> 16 & 0xFF) / 255.0f;
        this.particleGreen *= (var2 >> 8 & 0xFF) / 255.0f;
        this.particleBlue *= (var2 & 0xFF) / 255.0f;
        return this;
    }
    
    @Override
    public int getFXLayer() {
        return 1;
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
        final float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * p_70539_2_ - EntityDiggingFX.interpPosX);
        final float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * p_70539_2_ - EntityDiggingFX.interpPosY);
        final float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * p_70539_2_ - EntityDiggingFX.interpPosZ);
        p_70539_1_.setColorOpaque_F(this.particleRed, this.particleGreen, this.particleBlue);
        p_70539_1_.addVertexWithUV(var13 - p_70539_3_ * var12 - p_70539_6_ * var12, var14 - p_70539_4_ * var12, var15 - p_70539_5_ * var12 - p_70539_7_ * var12, var8, var11);
        p_70539_1_.addVertexWithUV(var13 - p_70539_3_ * var12 + p_70539_6_ * var12, var14 + p_70539_4_ * var12, var15 - p_70539_5_ * var12 + p_70539_7_ * var12, var8, var10);
        p_70539_1_.addVertexWithUV(var13 + p_70539_3_ * var12 + p_70539_6_ * var12, var14 + p_70539_4_ * var12, var15 + p_70539_5_ * var12 + p_70539_7_ * var12, var9, var10);
        p_70539_1_.addVertexWithUV(var13 + p_70539_3_ * var12 - p_70539_6_ * var12, var14 - p_70539_4_ * var12, var15 + p_70539_5_ * var12 - p_70539_7_ * var12, var9, var11);
    }
}
