package net.minecraft.client.gui;

import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class Gui
{
    public static final ResourceLocation optionsBackground;
    public static final ResourceLocation statIcons;
    public static final ResourceLocation icons;
    protected float zLevel;
    private static final String __OBFID = "CL_00000662";
    
    protected void drawHorizontalLine(int p_73730_1_, int p_73730_2_, final int p_73730_3_, final int p_73730_4_) {
        if (p_73730_2_ < p_73730_1_) {
            final int var5 = p_73730_1_;
            p_73730_1_ = p_73730_2_;
            p_73730_2_ = var5;
        }
        drawRect(p_73730_1_, p_73730_3_, p_73730_2_ + 1, p_73730_3_ + 1, p_73730_4_);
    }
    
    protected void drawVerticalLine(final int p_73728_1_, int p_73728_2_, int p_73728_3_, final int p_73728_4_) {
        if (p_73728_3_ < p_73728_2_) {
            final int var5 = p_73728_2_;
            p_73728_2_ = p_73728_3_;
            p_73728_3_ = var5;
        }
        drawRect(p_73728_1_, p_73728_2_ + 1, p_73728_1_ + 1, p_73728_3_, p_73728_4_);
    }
    
    public static void drawRect(int p_73734_0_, int p_73734_1_, int p_73734_2_, int p_73734_3_, final int p_73734_4_) {
        if (p_73734_0_ < p_73734_2_) {
            final int var5 = p_73734_0_;
            p_73734_0_ = p_73734_2_;
            p_73734_2_ = var5;
        }
        if (p_73734_1_ < p_73734_3_) {
            final int var5 = p_73734_1_;
            p_73734_1_ = p_73734_3_;
            p_73734_3_ = var5;
        }
        final float var6 = (p_73734_4_ >> 24 & 0xFF) / 255.0f;
        final float var7 = (p_73734_4_ >> 16 & 0xFF) / 255.0f;
        final float var8 = (p_73734_4_ >> 8 & 0xFF) / 255.0f;
        final float var9 = (p_73734_4_ & 0xFF) / 255.0f;
        final Tessellator var10 = Tessellator.instance;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(var7, var8, var9, var6);
        var10.startDrawingQuads();
        var10.addVertex(p_73734_0_, p_73734_3_, 0.0);
        var10.addVertex(p_73734_2_, p_73734_3_, 0.0);
        var10.addVertex(p_73734_2_, p_73734_1_, 0.0);
        var10.addVertex(p_73734_0_, p_73734_1_, 0.0);
        var10.draw();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
    
    protected void drawGradientRect(final int p_73733_1_, final int p_73733_2_, final int p_73733_3_, final int p_73733_4_, final int p_73733_5_, final int p_73733_6_) {
        final float var7 = (p_73733_5_ >> 24 & 0xFF) / 255.0f;
        final float var8 = (p_73733_5_ >> 16 & 0xFF) / 255.0f;
        final float var9 = (p_73733_5_ >> 8 & 0xFF) / 255.0f;
        final float var10 = (p_73733_5_ & 0xFF) / 255.0f;
        final float var11 = (p_73733_6_ >> 24 & 0xFF) / 255.0f;
        final float var12 = (p_73733_6_ >> 16 & 0xFF) / 255.0f;
        final float var13 = (p_73733_6_ >> 8 & 0xFF) / 255.0f;
        final float var14 = (p_73733_6_ & 0xFF) / 255.0f;
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glShadeModel(7425);
        final Tessellator var15 = Tessellator.instance;
        var15.startDrawingQuads();
        var15.setColorRGBA_F(var8, var9, var10, var7);
        var15.addVertex(p_73733_3_, p_73733_2_, this.zLevel);
        var15.addVertex(p_73733_1_, p_73733_2_, this.zLevel);
        var15.setColorRGBA_F(var12, var13, var14, var11);
        var15.addVertex(p_73733_1_, p_73733_4_, this.zLevel);
        var15.addVertex(p_73733_3_, p_73733_4_, this.zLevel);
        var15.draw();
        GL11.glShadeModel(7424);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glEnable(3553);
    }
    
    public void drawCenteredString(final FontRenderer p_73732_1_, final String p_73732_2_, final int p_73732_3_, final int p_73732_4_, final int p_73732_5_) {
        p_73732_1_.drawStringWithShadow(p_73732_2_, p_73732_3_ - p_73732_1_.getStringWidth(p_73732_2_) / 2, p_73732_4_, p_73732_5_);
    }
    
    public void drawString(final FontRenderer p_73731_1_, final String p_73731_2_, final int p_73731_3_, final int p_73731_4_, final int p_73731_5_) {
        p_73731_1_.drawStringWithShadow(p_73731_2_, p_73731_3_, p_73731_4_, p_73731_5_);
    }
    
    public void drawTexturedModalRect(final int p_73729_1_, final int p_73729_2_, final int p_73729_3_, final int p_73729_4_, final int p_73729_5_, final int p_73729_6_) {
        final float var7 = 0.00390625f;
        final float var8 = 0.00390625f;
        final Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        var9.addVertexWithUV(p_73729_1_ + 0, p_73729_2_ + p_73729_6_, this.zLevel, (p_73729_3_ + 0) * var7, (p_73729_4_ + p_73729_6_) * var8);
        var9.addVertexWithUV(p_73729_1_ + p_73729_5_, p_73729_2_ + p_73729_6_, this.zLevel, (p_73729_3_ + p_73729_5_) * var7, (p_73729_4_ + p_73729_6_) * var8);
        var9.addVertexWithUV(p_73729_1_ + p_73729_5_, p_73729_2_ + 0, this.zLevel, (p_73729_3_ + p_73729_5_) * var7, (p_73729_4_ + 0) * var8);
        var9.addVertexWithUV(p_73729_1_ + 0, p_73729_2_ + 0, this.zLevel, (p_73729_3_ + 0) * var7, (p_73729_4_ + 0) * var8);
        var9.draw();
    }
    
    public void drawTexturedModelRectFromIcon(final int p_94065_1_, final int p_94065_2_, final IIcon p_94065_3_, final int p_94065_4_, final int p_94065_5_) {
        final Tessellator var6 = Tessellator.instance;
        var6.startDrawingQuads();
        var6.addVertexWithUV(p_94065_1_ + 0, p_94065_2_ + p_94065_5_, this.zLevel, p_94065_3_.getMinU(), p_94065_3_.getMaxV());
        var6.addVertexWithUV(p_94065_1_ + p_94065_4_, p_94065_2_ + p_94065_5_, this.zLevel, p_94065_3_.getMaxU(), p_94065_3_.getMaxV());
        var6.addVertexWithUV(p_94065_1_ + p_94065_4_, p_94065_2_ + 0, this.zLevel, p_94065_3_.getMaxU(), p_94065_3_.getMinV());
        var6.addVertexWithUV(p_94065_1_ + 0, p_94065_2_ + 0, this.zLevel, p_94065_3_.getMinU(), p_94065_3_.getMinV());
        var6.draw();
    }
    
    public static void func_146110_a(final int p_146110_0_, final int p_146110_1_, final float p_146110_2_, final float p_146110_3_, final int p_146110_4_, final int p_146110_5_, final float p_146110_6_, final float p_146110_7_) {
        final float var8 = 1.0f / p_146110_6_;
        final float var9 = 1.0f / p_146110_7_;
        final Tessellator var10 = Tessellator.instance;
        var10.startDrawingQuads();
        var10.addVertexWithUV(p_146110_0_, p_146110_1_ + p_146110_5_, 0.0, p_146110_2_ * var8, (p_146110_3_ + p_146110_5_) * var9);
        var10.addVertexWithUV(p_146110_0_ + p_146110_4_, p_146110_1_ + p_146110_5_, 0.0, (p_146110_2_ + p_146110_4_) * var8, (p_146110_3_ + p_146110_5_) * var9);
        var10.addVertexWithUV(p_146110_0_ + p_146110_4_, p_146110_1_, 0.0, (p_146110_2_ + p_146110_4_) * var8, p_146110_3_ * var9);
        var10.addVertexWithUV(p_146110_0_, p_146110_1_, 0.0, p_146110_2_ * var8, p_146110_3_ * var9);
        var10.draw();
    }
    
    public static void func_152125_a(final int p_152125_0_, final int p_152125_1_, final float p_152125_2_, final float p_152125_3_, final int p_152125_4_, final int p_152125_5_, final int p_152125_6_, final int p_152125_7_, final float p_152125_8_, final float p_152125_9_) {
        final float var10 = 1.0f / p_152125_8_;
        final float var11 = 1.0f / p_152125_9_;
        final Tessellator var12 = Tessellator.instance;
        var12.startDrawingQuads();
        var12.addVertexWithUV(p_152125_0_, p_152125_1_ + p_152125_7_, 0.0, p_152125_2_ * var10, (p_152125_3_ + p_152125_5_) * var11);
        var12.addVertexWithUV(p_152125_0_ + p_152125_6_, p_152125_1_ + p_152125_7_, 0.0, (p_152125_2_ + p_152125_4_) * var10, (p_152125_3_ + p_152125_5_) * var11);
        var12.addVertexWithUV(p_152125_0_ + p_152125_6_, p_152125_1_, 0.0, (p_152125_2_ + p_152125_4_) * var10, p_152125_3_ * var11);
        var12.addVertexWithUV(p_152125_0_, p_152125_1_, 0.0, p_152125_2_ * var10, p_152125_3_ * var11);
        var12.draw();
    }
    
    static {
        optionsBackground = new ResourceLocation("textures/gui/options_background.png");
        statIcons = new ResourceLocation("textures/gui/container/stats_icons.png");
        icons = new ResourceLocation("textures/gui/icons.png");
    }
}
