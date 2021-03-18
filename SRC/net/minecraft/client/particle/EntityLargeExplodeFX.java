package net.minecraft.client.particle;

import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;

public class EntityLargeExplodeFX extends EntityFX
{
    private static final ResourceLocation field_110127_a;
    private int field_70581_a;
    private int field_70584_aq;
    private TextureManager theRenderEngine;
    private float field_70582_as;
    private static final String __OBFID = "CL_00000910";
    
    public EntityLargeExplodeFX(final TextureManager p_i1213_1_, final World p_i1213_2_, final double p_i1213_3_, final double p_i1213_5_, final double p_i1213_7_, final double p_i1213_9_, final double p_i1213_11_, final double p_i1213_13_) {
        super(p_i1213_2_, p_i1213_3_, p_i1213_5_, p_i1213_7_, 0.0, 0.0, 0.0);
        this.theRenderEngine = p_i1213_1_;
        this.field_70584_aq = 6 + this.rand.nextInt(4);
        final float particleRed = this.rand.nextFloat() * 0.6f + 0.4f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.field_70582_as = 1.0f - (float)p_i1213_9_ * 0.5f;
    }
    
    @Override
    public void renderParticle(final Tessellator p_70539_1_, final float p_70539_2_, final float p_70539_3_, final float p_70539_4_, final float p_70539_5_, final float p_70539_6_, final float p_70539_7_) {
        final int var8 = (int)((this.field_70581_a + p_70539_2_) * 15.0f / this.field_70584_aq);
        if (var8 <= 15) {
            this.theRenderEngine.bindTexture(EntityLargeExplodeFX.field_110127_a);
            final float var9 = var8 % 4 / 4.0f;
            final float var10 = var9 + 0.24975f;
            final float var11 = var8 / 4 / 4.0f;
            final float var12 = var11 + 0.24975f;
            final float var13 = 2.0f * this.field_70582_as;
            final float var14 = (float)(this.prevPosX + (this.posX - this.prevPosX) * p_70539_2_ - EntityLargeExplodeFX.interpPosX);
            final float var15 = (float)(this.prevPosY + (this.posY - this.prevPosY) * p_70539_2_ - EntityLargeExplodeFX.interpPosY);
            final float var16 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * p_70539_2_ - EntityLargeExplodeFX.interpPosZ);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glDisable(2896);
            RenderHelper.disableStandardItemLighting();
            p_70539_1_.startDrawingQuads();
            p_70539_1_.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, 1.0f);
            p_70539_1_.setNormal(0.0f, 1.0f, 0.0f);
            p_70539_1_.setBrightness(240);
            p_70539_1_.addVertexWithUV(var14 - p_70539_3_ * var13 - p_70539_6_ * var13, var15 - p_70539_4_ * var13, var16 - p_70539_5_ * var13 - p_70539_7_ * var13, var10, var12);
            p_70539_1_.addVertexWithUV(var14 - p_70539_3_ * var13 + p_70539_6_ * var13, var15 + p_70539_4_ * var13, var16 - p_70539_5_ * var13 + p_70539_7_ * var13, var10, var11);
            p_70539_1_.addVertexWithUV(var14 + p_70539_3_ * var13 + p_70539_6_ * var13, var15 + p_70539_4_ * var13, var16 + p_70539_5_ * var13 + p_70539_7_ * var13, var9, var11);
            p_70539_1_.addVertexWithUV(var14 + p_70539_3_ * var13 - p_70539_6_ * var13, var15 - p_70539_4_ * var13, var16 + p_70539_5_ * var13 - p_70539_7_ * var13, var9, var12);
            p_70539_1_.draw();
            GL11.glPolygonOffset(0.0f, 0.0f);
            GL11.glEnable(2896);
        }
    }
    
    @Override
    public int getBrightnessForRender(final float p_70070_1_) {
        return 61680;
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        ++this.field_70581_a;
        if (this.field_70581_a == this.field_70584_aq) {
            this.setDead();
        }
    }
    
    @Override
    public int getFXLayer() {
        return 3;
    }
    
    static {
        field_110127_a = new ResourceLocation("textures/entity/explosion.png");
    }
}
