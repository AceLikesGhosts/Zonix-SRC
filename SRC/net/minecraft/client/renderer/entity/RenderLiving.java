package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;

public abstract class RenderLiving extends RendererLivingEntity
{
    private static final String __OBFID = "CL_00001015";
    
    public RenderLiving(final ModelBase p_i1262_1_, final float p_i1262_2_) {
        super(p_i1262_1_, p_i1262_2_);
    }
    
    protected boolean func_110813_b(final EntityLiving p_110813_1_) {
        return super.func_110813_b(p_110813_1_) && (p_110813_1_.getAlwaysRenderNameTagForRender() || (p_110813_1_.hasCustomNameTag() && p_110813_1_ == this.renderManager.field_147941_i));
    }
    
    public void doRender(final EntityLiving p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
        this.func_110827_b(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    private double func_110828_a(final double p_110828_1_, final double p_110828_3_, final double p_110828_5_) {
        return p_110828_1_ + (p_110828_3_ - p_110828_1_) * p_110828_5_;
    }
    
    protected void func_110827_b(final EntityLiving p_110827_1_, double p_110827_2_, double p_110827_4_, double p_110827_6_, final float p_110827_8_, final float p_110827_9_) {
        final Entity var10 = p_110827_1_.getLeashedToEntity();
        if (var10 != null) {
            p_110827_4_ -= (1.6 - p_110827_1_.height) * 0.5;
            final Tessellator var11 = Tessellator.instance;
            final double var12 = this.func_110828_a(var10.prevRotationYaw, var10.rotationYaw, p_110827_9_ * 0.5f) * 0.01745329238474369;
            final double var13 = this.func_110828_a(var10.prevRotationPitch, var10.rotationPitch, p_110827_9_ * 0.5f) * 0.01745329238474369;
            double var14 = Math.cos(var12);
            double var15 = Math.sin(var12);
            double var16 = Math.sin(var13);
            if (var10 instanceof EntityHanging) {
                var14 = 0.0;
                var15 = 0.0;
                var16 = -1.0;
            }
            final double var17 = Math.cos(var13);
            final double var18 = this.func_110828_a(var10.prevPosX, var10.posX, p_110827_9_) - var14 * 0.7 - var15 * 0.5 * var17;
            final double var19 = this.func_110828_a(var10.prevPosY + var10.getEyeHeight() * 0.7, var10.posY + var10.getEyeHeight() * 0.7, p_110827_9_) - var16 * 0.5 - 0.25;
            final double var20 = this.func_110828_a(var10.prevPosZ, var10.posZ, p_110827_9_) - var15 * 0.7 + var14 * 0.5 * var17;
            final double var21 = this.func_110828_a(p_110827_1_.prevRenderYawOffset, p_110827_1_.renderYawOffset, p_110827_9_) * 0.01745329238474369 + 1.5707963267948966;
            var14 = Math.cos(var21) * p_110827_1_.width * 0.4;
            var15 = Math.sin(var21) * p_110827_1_.width * 0.4;
            final double var22 = this.func_110828_a(p_110827_1_.prevPosX, p_110827_1_.posX, p_110827_9_) + var14;
            final double var23 = this.func_110828_a(p_110827_1_.prevPosY, p_110827_1_.posY, p_110827_9_);
            final double var24 = this.func_110828_a(p_110827_1_.prevPosZ, p_110827_1_.posZ, p_110827_9_) + var15;
            p_110827_2_ += var14;
            p_110827_6_ += var15;
            final double var25 = (float)(var18 - var22);
            final double var26 = (float)(var19 - var23);
            final double var27 = (float)(var20 - var24);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glDisable(2884);
            final boolean var28 = true;
            final double var29 = 0.025;
            var11.startDrawing(5);
            for (int var30 = 0; var30 <= 24; ++var30) {
                if (var30 % 2 == 0) {
                    var11.setColorRGBA_F(0.5f, 0.4f, 0.3f, 1.0f);
                }
                else {
                    var11.setColorRGBA_F(0.35f, 0.28f, 0.21000001f, 1.0f);
                }
                final float var31 = var30 / 24.0f;
                var11.addVertex(p_110827_2_ + var25 * var31 + 0.0, p_110827_4_ + var26 * (var31 * var31 + var31) * 0.5 + ((24.0f - var30) / 18.0f + 0.125f), p_110827_6_ + var27 * var31);
                var11.addVertex(p_110827_2_ + var25 * var31 + 0.025, p_110827_4_ + var26 * (var31 * var31 + var31) * 0.5 + ((24.0f - var30) / 18.0f + 0.125f) + 0.025, p_110827_6_ + var27 * var31);
            }
            var11.draw();
            var11.startDrawing(5);
            for (int var30 = 0; var30 <= 24; ++var30) {
                if (var30 % 2 == 0) {
                    var11.setColorRGBA_F(0.5f, 0.4f, 0.3f, 1.0f);
                }
                else {
                    var11.setColorRGBA_F(0.35f, 0.28f, 0.21000001f, 1.0f);
                }
                final float var31 = var30 / 24.0f;
                var11.addVertex(p_110827_2_ + var25 * var31 + 0.0, p_110827_4_ + var26 * (var31 * var31 + var31) * 0.5 + ((24.0f - var30) / 18.0f + 0.125f) + 0.025, p_110827_6_ + var27 * var31);
                var11.addVertex(p_110827_2_ + var25 * var31 + 0.025, p_110827_4_ + var26 * (var31 * var31 + var31) * 0.5 + ((24.0f - var30) / 18.0f + 0.125f), p_110827_6_ + var27 * var31 + 0.025);
            }
            var11.draw();
            GL11.glEnable(2896);
            GL11.glEnable(3553);
            GL11.glEnable(2884);
        }
    }
    
    @Override
    protected boolean func_110813_b(final EntityLivingBase p_110813_1_) {
        return this.func_110813_b((EntityLiving)p_110813_1_);
    }
    
    @Override
    public void doRender(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityLiving)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityLiving)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
