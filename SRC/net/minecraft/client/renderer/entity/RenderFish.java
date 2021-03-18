package net.minecraft.client.renderer.entity;

import net.minecraft.entity.projectile.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.client.*;

public class RenderFish extends Render
{
    private static final ResourceLocation field_110792_a;
    private static final String __OBFID = "CL_00000996";
    
    public void doRender(final EntityFishHook p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GL11.glEnable(32826);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        this.bindEntityTexture(p_76986_1_);
        final Tessellator var10 = Tessellator.instance;
        final byte var11 = 1;
        final byte var12 = 2;
        final float var13 = (var11 * 8 + 0) / 128.0f;
        final float var14 = (var11 * 8 + 8) / 128.0f;
        final float var15 = (var12 * 8 + 0) / 128.0f;
        final float var16 = (var12 * 8 + 8) / 128.0f;
        final float var17 = 1.0f;
        final float var18 = 0.5f;
        final float var19 = 0.5f;
        GL11.glRotatef(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        var10.startDrawingQuads();
        var10.setNormal(0.0f, 1.0f, 0.0f);
        var10.addVertexWithUV(0.0f - var18, 0.0f - var19, 0.0, var13, var16);
        var10.addVertexWithUV(var17 - var18, 0.0f - var19, 0.0, var14, var16);
        var10.addVertexWithUV(var17 - var18, 1.0f - var19, 0.0, var14, var15);
        var10.addVertexWithUV(0.0f - var18, 1.0f - var19, 0.0, var13, var15);
        var10.draw();
        GL11.glDisable(32826);
        GL11.glPopMatrix();
        if (p_76986_1_.field_146042_b != null) {
            final float var20 = p_76986_1_.field_146042_b.getSwingProgress(p_76986_9_);
            final float var21 = MathHelper.sin(MathHelper.sqrt_float(var20) * 3.1415927f);
            final Vec3 var22 = Vec3.createVectorHelper(-0.5, 0.03, 0.8);
            var22.rotateAroundX(-(p_76986_1_.field_146042_b.prevRotationPitch + (p_76986_1_.field_146042_b.rotationPitch - p_76986_1_.field_146042_b.prevRotationPitch) * p_76986_9_) * 3.1415927f / 180.0f);
            var22.rotateAroundY(-(p_76986_1_.field_146042_b.prevRotationYaw + (p_76986_1_.field_146042_b.rotationYaw - p_76986_1_.field_146042_b.prevRotationYaw) * p_76986_9_) * 3.1415927f / 180.0f);
            var22.rotateAroundY(var21 * 0.5f);
            var22.rotateAroundX(-var21 * 0.7f);
            double var23 = p_76986_1_.field_146042_b.prevPosX + (p_76986_1_.field_146042_b.posX - p_76986_1_.field_146042_b.prevPosX) * p_76986_9_ + var22.xCoord;
            double var24 = p_76986_1_.field_146042_b.prevPosY + (p_76986_1_.field_146042_b.posY - p_76986_1_.field_146042_b.prevPosY) * p_76986_9_ + var22.yCoord;
            double var25 = p_76986_1_.field_146042_b.prevPosZ + (p_76986_1_.field_146042_b.posZ - p_76986_1_.field_146042_b.prevPosZ) * p_76986_9_ + var22.zCoord;
            final double var26 = (p_76986_1_.field_146042_b == Minecraft.getMinecraft().thePlayer) ? 0.0 : p_76986_1_.field_146042_b.getEyeHeight();
            if (this.renderManager.options.thirdPersonView > 0 || p_76986_1_.field_146042_b != Minecraft.getMinecraft().thePlayer) {
                final float var27 = (p_76986_1_.field_146042_b.prevRenderYawOffset + (p_76986_1_.field_146042_b.renderYawOffset - p_76986_1_.field_146042_b.prevRenderYawOffset) * p_76986_9_) * 3.1415927f / 180.0f;
                final double var28 = MathHelper.sin(var27);
                final double var29 = MathHelper.cos(var27);
                var23 = p_76986_1_.field_146042_b.prevPosX + (p_76986_1_.field_146042_b.posX - p_76986_1_.field_146042_b.prevPosX) * p_76986_9_ - var29 * 0.35 - var28 * 0.85;
                var24 = p_76986_1_.field_146042_b.prevPosY + var26 + (p_76986_1_.field_146042_b.posY - p_76986_1_.field_146042_b.prevPosY) * p_76986_9_ - 0.45;
                var25 = p_76986_1_.field_146042_b.prevPosZ + (p_76986_1_.field_146042_b.posZ - p_76986_1_.field_146042_b.prevPosZ) * p_76986_9_ - var28 * 0.35 + var29 * 0.85;
            }
            final double var30 = p_76986_1_.prevPosX + (p_76986_1_.posX - p_76986_1_.prevPosX) * p_76986_9_;
            final double var31 = p_76986_1_.prevPosY + (p_76986_1_.posY - p_76986_1_.prevPosY) * p_76986_9_ + 0.25;
            final double var32 = p_76986_1_.prevPosZ + (p_76986_1_.posZ - p_76986_1_.prevPosZ) * p_76986_9_;
            final double var33 = (float)(var23 - var30);
            final double var34 = (float)(var24 - var31);
            final double var35 = (float)(var25 - var32);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            var10.startDrawing(3);
            var10.setColorOpaque_I(0);
            final byte var36 = 16;
            for (int var37 = 0; var37 <= var36; ++var37) {
                final float var38 = var37 / (float)var36;
                var10.addVertex(p_76986_2_ + var33 * var38, p_76986_4_ + var34 * (var38 * var38 + var38) * 0.5 + 0.25, p_76986_6_ + var35 * var38);
            }
            var10.draw();
            GL11.glEnable(2896);
            GL11.glEnable(3553);
        }
    }
    
    public ResourceLocation getEntityTexture(final EntityFishHook p_110775_1_) {
        return RenderFish.field_110792_a;
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityFishHook)p_110775_1_);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityFishHook)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    static {
        field_110792_a = new ResourceLocation("textures/particle/particles.png");
    }
}
