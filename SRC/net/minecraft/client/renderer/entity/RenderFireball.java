package net.minecraft.client.renderer.entity;

import net.minecraft.entity.projectile.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;

public class RenderFireball extends Render
{
    private float field_77002_a;
    private static final String __OBFID = "CL_00000995";
    
    public RenderFireball(final float p_i1254_1_) {
        this.field_77002_a = p_i1254_1_;
    }
    
    public void doRender(final EntityFireball p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        GL11.glPushMatrix();
        this.bindEntityTexture(p_76986_1_);
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GL11.glEnable(32826);
        final float var10 = this.field_77002_a;
        GL11.glScalef(var10 / 1.0f, var10 / 1.0f, var10 / 1.0f);
        final IIcon var11 = Items.fire_charge.getIconFromDamage(0);
        final Tessellator var12 = Tessellator.instance;
        final float var13 = var11.getMinU();
        final float var14 = var11.getMaxU();
        final float var15 = var11.getMinV();
        final float var16 = var11.getMaxV();
        final float var17 = 1.0f;
        final float var18 = 0.5f;
        final float var19 = 0.25f;
        GL11.glRotatef(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        var12.startDrawingQuads();
        var12.setNormal(0.0f, 1.0f, 0.0f);
        var12.addVertexWithUV(0.0f - var18, 0.0f - var19, 0.0, var13, var16);
        var12.addVertexWithUV(var17 - var18, 0.0f - var19, 0.0, var14, var16);
        var12.addVertexWithUV(var17 - var18, 1.0f - var19, 0.0, var14, var15);
        var12.addVertexWithUV(0.0f - var18, 1.0f - var19, 0.0, var13, var15);
        var12.draw();
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
    
    public ResourceLocation getEntityTexture(final EntityFireball p_110775_1_) {
        return TextureMap.locationItemsTexture;
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityFireball)p_110775_1_);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityFireball)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
