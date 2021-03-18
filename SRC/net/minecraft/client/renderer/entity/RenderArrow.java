package net.minecraft.client.renderer.entity;

import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class RenderArrow extends Render
{
    private static final ResourceLocation arrowTextures;
    private static final String __OBFID = "CL_00000978";
    
    public void doRender(final EntityArrow p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.bindEntityTexture(p_76986_1_);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GL11.glRotatef(p_76986_1_.prevRotationYaw + (p_76986_1_.rotationYaw - p_76986_1_.prevRotationYaw) * p_76986_9_ - 90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(p_76986_1_.prevRotationPitch + (p_76986_1_.rotationPitch - p_76986_1_.prevRotationPitch) * p_76986_9_, 0.0f, 0.0f, 1.0f);
        final Tessellator var10 = Tessellator.instance;
        final byte var11 = 0;
        final float var12 = 0.0f;
        final float var13 = 0.5f;
        final float var14 = (0 + var11 * 10) / 32.0f;
        final float var15 = (5 + var11 * 10) / 32.0f;
        final float var16 = 0.0f;
        final float var17 = 0.15625f;
        final float var18 = (5 + var11 * 10) / 32.0f;
        final float var19 = (10 + var11 * 10) / 32.0f;
        final float var20 = 0.05625f;
        GL11.glEnable(32826);
        final float var21 = p_76986_1_.arrowShake - p_76986_9_;
        if (var21 > 0.0f) {
            final float var22 = -MathHelper.sin(var21 * 3.0f) * var21;
            GL11.glRotatef(var22, 0.0f, 0.0f, 1.0f);
        }
        GL11.glRotatef(45.0f, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(var20, var20, var20);
        GL11.glTranslatef(-4.0f, 0.0f, 0.0f);
        GL11.glNormal3f(var20, 0.0f, 0.0f);
        var10.startDrawingQuads();
        var10.addVertexWithUV(-7.0, -2.0, -2.0, var16, var18);
        var10.addVertexWithUV(-7.0, -2.0, 2.0, var17, var18);
        var10.addVertexWithUV(-7.0, 2.0, 2.0, var17, var19);
        var10.addVertexWithUV(-7.0, 2.0, -2.0, var16, var19);
        var10.draw();
        GL11.glNormal3f(-var20, 0.0f, 0.0f);
        var10.startDrawingQuads();
        var10.addVertexWithUV(-7.0, 2.0, -2.0, var16, var18);
        var10.addVertexWithUV(-7.0, 2.0, 2.0, var17, var18);
        var10.addVertexWithUV(-7.0, -2.0, 2.0, var17, var19);
        var10.addVertexWithUV(-7.0, -2.0, -2.0, var16, var19);
        var10.draw();
        for (int var23 = 0; var23 < 4; ++var23) {
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GL11.glNormal3f(0.0f, 0.0f, var20);
            var10.startDrawingQuads();
            var10.addVertexWithUV(-8.0, -2.0, 0.0, var12, var14);
            var10.addVertexWithUV(8.0, -2.0, 0.0, var13, var14);
            var10.addVertexWithUV(8.0, 2.0, 0.0, var13, var15);
            var10.addVertexWithUV(-8.0, 2.0, 0.0, var12, var15);
            var10.draw();
        }
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
    
    public ResourceLocation getEntityTexture(final EntityArrow p_110775_1_) {
        return RenderArrow.arrowTextures;
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityArrow)p_110775_1_);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityArrow)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    static {
        arrowTextures = new ResourceLocation("textures/entity/arrow.png");
    }
}
