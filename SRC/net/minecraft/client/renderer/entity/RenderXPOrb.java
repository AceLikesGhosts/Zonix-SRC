package net.minecraft.client.renderer.entity;

import net.minecraft.entity.item.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;

public class RenderXPOrb extends Render
{
    private static final ResourceLocation experienceOrbTextures;
    private static final String __OBFID = "CL_00000993";
    
    public RenderXPOrb() {
        this.shadowSize = 0.15f;
        this.shadowOpaque = 0.75f;
    }
    
    public void doRender(final EntityXPOrb p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        this.bindEntityTexture(p_76986_1_);
        final int var10 = p_76986_1_.getTextureByXP();
        final float var11 = (var10 % 4 * 16 + 0) / 64.0f;
        final float var12 = (var10 % 4 * 16 + 16) / 64.0f;
        final float var13 = (var10 / 4 * 16 + 0) / 64.0f;
        final float var14 = (var10 / 4 * 16 + 16) / 64.0f;
        final float var15 = 1.0f;
        final float var16 = 0.5f;
        final float var17 = 0.25f;
        final int var18 = p_76986_1_.getBrightnessForRender(p_76986_9_);
        final int var19 = var18 % 65536;
        int var20 = var18 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var19 / 1.0f, var20 / 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final float var21 = 255.0f;
        final float var22 = (p_76986_1_.xpColor + p_76986_9_) / 2.0f;
        var20 = (int)((MathHelper.sin(var22 + 0.0f) + 1.0f) * 0.5f * var21);
        final int var23 = (int)var21;
        final int var24 = (int)((MathHelper.sin(var22 + 4.1887903f) + 1.0f) * 0.1f * var21);
        final int var25 = var20 << 16 | var23 << 8 | var24;
        GL11.glRotatef(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        final float var26 = 0.3f;
        GL11.glScalef(var26, var26, var26);
        final Tessellator var27 = Tessellator.instance;
        var27.startDrawingQuads();
        var27.setColorRGBA_I(var25, 128);
        var27.setNormal(0.0f, 1.0f, 0.0f);
        var27.addVertexWithUV(0.0f - var16, 0.0f - var17, 0.0, var11, var14);
        var27.addVertexWithUV(var15 - var16, 0.0f - var17, 0.0, var12, var14);
        var27.addVertexWithUV(var15 - var16, 1.0f - var17, 0.0, var12, var13);
        var27.addVertexWithUV(0.0f - var16, 1.0f - var17, 0.0, var11, var13);
        var27.draw();
        GL11.glDisable(3042);
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
    
    public ResourceLocation getEntityTexture(final EntityXPOrb p_110775_1_) {
        return RenderXPOrb.experienceOrbTextures;
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityXPOrb)p_110775_1_);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityXPOrb)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    static {
        experienceOrbTextures = new ResourceLocation("textures/entity/experience_orb.png");
    }
}
