package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class EntityFireworkOverlayFX extends EntityFX
{
    private static final String __OBFID = "CL_00000904";
    
    protected EntityFireworkOverlayFX(final World p_i46357_1_, final double p_i46357_2_, final double p_i46357_4_, final double p_i46357_6_) {
        super(p_i46357_1_, p_i46357_2_, p_i46357_4_, p_i46357_6_);
        this.particleMaxAge = 4;
    }
    
    @Override
    public void renderParticle(final Tessellator p_70539_1_, final float p_70539_2_, final float p_70539_3_, final float p_70539_4_, final float p_70539_5_, final float p_70539_6_, final float p_70539_7_) {
        final float var8 = 0.25f;
        final float var9 = var8 + 0.25f;
        final float var10 = 0.125f;
        final float var11 = var10 + 0.25f;
        final float var12 = 7.1f * MathHelper.sin((this.particleAge + p_70539_2_ - 1.0f) * 0.25f * 3.1415927f);
        this.particleAlpha = 0.6f - (this.particleAge + p_70539_2_ - 1.0f) * 0.25f * 0.5f;
        final float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * p_70539_2_ - EntityFireworkOverlayFX.interpPosX);
        final float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * p_70539_2_ - EntityFireworkOverlayFX.interpPosY);
        final float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * p_70539_2_ - EntityFireworkOverlayFX.interpPosZ);
        p_70539_1_.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
        p_70539_1_.addVertexWithUV(var13 - p_70539_3_ * var12 - p_70539_6_ * var12, var14 - p_70539_4_ * var12, var15 - p_70539_5_ * var12 - p_70539_7_ * var12, var9, var11);
        p_70539_1_.addVertexWithUV(var13 - p_70539_3_ * var12 + p_70539_6_ * var12, var14 + p_70539_4_ * var12, var15 - p_70539_5_ * var12 + p_70539_7_ * var12, var9, var10);
        p_70539_1_.addVertexWithUV(var13 + p_70539_3_ * var12 + p_70539_6_ * var12, var14 + p_70539_4_ * var12, var15 + p_70539_5_ * var12 + p_70539_7_ * var12, var8, var10);
        p_70539_1_.addVertexWithUV(var13 + p_70539_3_ * var12 - p_70539_6_ * var12, var14 - p_70539_4_ * var12, var15 + p_70539_5_ * var12 - p_70539_7_ * var12, var8, var11);
    }
}
