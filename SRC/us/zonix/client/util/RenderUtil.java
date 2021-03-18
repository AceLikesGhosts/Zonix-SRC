package us.zonix.client.util;

import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import us.zonix.client.*;
import us.zonix.client.util.font.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.gui.*;

public final class RenderUtil
{
    public static void drawCircle(final double x, final double y, final double r) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        final Tessellator tes = Tessellator.instance;
        tes.startDrawing(6);
        tes.addVertex(x, y, 0.0);
        for (double end = 6.283185307179586, increment = end / 30.0, theta = -increment; theta < end; theta += increment) {
            tes.addVertex(x + r * Math.cos(-theta), y + r * Math.sin(-theta), 0.0);
        }
        tes.draw();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
    
    public static void drawSquareTexture(final ResourceLocation resourceLocation, final float size, final float x, final float y) {
        final float height = size * 2.0f;
        final float width = size * 2.0f;
        final float u = 0.0f;
        final float v = 0.0f;
        GL11.glEnable(3042);
        Minecraft.getMinecraft().renderEngine.bindTexture(resourceLocation);
        GL11.glBegin(7);
        GL11.glTexCoord2d((double)(u / size), (double)(v / size));
        GL11.glVertex2d((double)x, (double)y);
        GL11.glTexCoord2d((double)(u / size), (double)((v + size) / size));
        GL11.glVertex2d((double)x, (double)(y + height));
        GL11.glTexCoord2d((double)((u + size) / size), (double)((v + size) / size));
        GL11.glVertex2d((double)(x + width), (double)(y + height));
        GL11.glTexCoord2d((double)((u + size) / size), (double)(v / size));
        GL11.glVertex2d((double)(x + width), (double)y);
        GL11.glEnd();
        GL11.glDisable(3042);
    }
    
    public static void drawTexturedModalRect(final float x, final float y, final int width, final int height, final int u, final int v) {
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        final float scaleX = 0.00390625f;
        final float scaleY = 0.00390625f;
        tessellator.addVertexWithUV(x + 0.0f, y + height, 0.0, u * scaleX, (v + height) * scaleY);
        tessellator.addVertexWithUV(x + width, y + height, 0.0, (u + width) * scaleX, (v + height) * scaleY);
        tessellator.addVertexWithUV(x + width, y + 0.0f, 0.0, (u + width) * scaleX, v * scaleY);
        tessellator.addVertexWithUV(x + 0.0f, y + 0.0f, 0.0, u * scaleX, v * scaleY);
        tessellator.draw();
    }
    
    public static void startScissorBox(final float minY, final float maxY, final float minX, final float maxX) {
        GL11.glPushMatrix();
        GL11.glEnable(3089);
        final float width = maxX - minX;
        final float height = maxY - minY;
        final Minecraft mc = Minecraft.getMinecraft();
        final float scale = (float)new ScaledResolution(mc).getScaleFactor();
        GL11.glScissor((int)(minX * scale), (int)(mc.displayHeight - (minY + height) * scale), (int)(width * scale), (int)(height * scale));
    }
    
    public static void endScissorBox() {
        GL11.glDisable(3089);
        GL11.glPopMatrix();
    }
    
    public static void drawTexture(final ResourceLocation resourceLocation, final float size, final float x, final float y) {
        GL11.glPushMatrix();
        final float squareSize = size * 2.0f;
        GL11.glEnable(3042);
        GL11.glEnable(3553);
        bindTexture(resourceLocation);
        GL11.glBegin(7);
        GL11.glTexCoord2d(0.0, 0.0);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glTexCoord2d(0.0, 1.0);
        GL11.glVertex2d((double)x, (double)(y + squareSize));
        GL11.glTexCoord2d(1.0, 1.0);
        GL11.glVertex2d((double)(x + squareSize), (double)(y + squareSize));
        GL11.glTexCoord2d(1.0, 0.0);
        GL11.glVertex2d((double)(x + squareSize), (double)y);
        GL11.glEnd();
        GL11.glDisable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void drawSquareTexture(final ResourceLocation resourceLocation, final float width, final float height, final float x, final float y) {
        final float size = width / 2.0f;
        final float u = 0.0f;
        final float v = 0.0f;
        GL11.glEnable(3042);
        Minecraft.getMinecraft().renderEngine.bindTexture(resourceLocation);
        GL11.glBegin(7);
        GL11.glTexCoord2d((double)(u / size), (double)(v / size));
        GL11.glVertex2d((double)x, (double)y);
        GL11.glTexCoord2d((double)(u / size), (double)((v + size) / size));
        GL11.glVertex2d((double)x, (double)(y + height));
        GL11.glTexCoord2d((double)((u + size) / size), (double)((v + size) / size));
        GL11.glVertex2d((double)(x + width), (double)(y + height));
        GL11.glTexCoord2d((double)((u + size) / size), (double)(v / size));
        GL11.glVertex2d((double)(x + width), (double)y);
        GL11.glEnd();
        GL11.glDisable(3042);
    }
    
    public static void scaleAtPoint(final float centerX, final float centerY, final float scale) {
        GL11.glTexParameteri(3553, 10240, 9728);
        GL11.glTexParameteri(3553, 10241, 9728);
        GL11.glTranslatef(centerX, centerY, 0.0f);
        GL11.glScalef(scale, scale, 0.0f);
        GL11.glTranslatef(centerX * -1.0f, centerY * -1.0f, 0.0f);
    }
    
    public static void drawCenteredString(final String text, final int x, final int y, final int color) {
        final ZFontRenderer fontRenderer = Client.getInstance().getRegularFontRenderer();
        drawCenteredString(fontRenderer, text, (float)x, (float)y, color);
    }
    
    public static void drawCenteredStringWithIcon(final ResourceLocation resourceLocation, final float width, final ZFontRenderer fontRenderer, final String text, final int x, final int y, final int color) {
        final int textX = (int)(x + width * 1.5f + 1.5f);
        drawCenteredString(fontRenderer, text, (float)textX, (float)(y + fontRenderer.getHeight()), color);
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.6f);
        final float iconX = x - width * 3.0f - 4.5f;
        drawSquareTexture(resourceLocation, width, iconX, y + width / 4.0f);
        GL11.glPopMatrix();
    }
    
    public static void drawCenteredString(final ZFontRenderer fontRenderer, final String text, final float x, final float y, final int color) {
        drawCenteredString(fontRenderer, text, x, y, color, true);
    }
    
    public static void drawCenteredString(final ZFontRenderer fontRenderer, final String text, final float x, final float y, final int color, final boolean shadow) {
        final int width = fontRenderer.getStringWidth(text);
        final int height = fontRenderer.getHeight();
        final float dX = x - width / 2;
        final float dY = y - height / 2;
        if (shadow) {
            fontRenderer.drawStringWithShadow(text, dX, dY, color);
        }
        else {
            fontRenderer.drawString(text, dX, dY, color);
        }
    }
    
    public static void drawSmallString(final String text, final float x, final float y, final int color) {
        final ZFontRenderer fontRenderer = Client.getInstance().getSmallFontRenderer();
        fontRenderer.drawStringWithShadow(text, (int)x, (int)y, color);
    }
    
    public static void drawString(final String text, final float x, final float y, final int color) {
        final ZFontRenderer fontRenderer = Client.getInstance().getRegularFontRenderer();
        fontRenderer.drawStringWithShadow(text, (int)x, (int)y, color);
    }
    
    public static void drawString(final ZFontRenderer fontRenderer, final String text, final float x, final float y, final int color, final boolean shadow) {
        if (shadow) {
            fontRenderer.drawStringWithShadow(text, (int)x, (int)y, color);
        }
        else {
            fontRenderer.drawString(text, (float)(int)x, (float)(int)y, color);
        }
    }
    
    public static void drawString(final ZFontRenderer fontRenderer, final String text, final float x, final float y, final int color) {
        fontRenderer.drawStringWithShadow(text, (int)x, (int)y, color);
    }
    
    public static void drawString(final String text, final int x, final int y, final int color) {
        Client.getInstance().getRegularFontRenderer().drawStringWithShadow(text, x, y, color);
    }
    
    public static void bindTexture(final ResourceLocation resourceLocation) {
        ITextureObject texture = Minecraft.getMinecraft().renderEngine.getTexture(resourceLocation);
        if (texture == null) {
            texture = new SimpleTexture(resourceLocation);
            Minecraft.getMinecraft().renderEngine.loadTexture(resourceLocation, texture);
        }
        GL11.glBindTexture(3553, texture.getGlTextureId());
    }
    
    public static void drawTexture(final ResourceLocation resourceLocation, final float x, final float y, final float width, final float height) {
        final float size = width / 2.0f;
        final float u = 0.0f;
        final float v = 0.0f;
        GL11.glEnable(3042);
        bindTexture(resourceLocation);
        GL11.glBegin(7);
        GL11.glTexCoord2d((double)(u / size), (double)(v / size));
        GL11.glVertex2d((double)x, (double)y);
        GL11.glTexCoord2d((double)(u / size), (double)((v + size) / size));
        GL11.glVertex2d((double)x, (double)(y + height));
        GL11.glTexCoord2d((double)((u + size) / size), (double)((v + size) / size));
        GL11.glVertex2d((double)(x + width), (double)(y + height));
        GL11.glTexCoord2d((double)((u + size) / size), (double)(v / size));
        GL11.glVertex2d((double)(x + width), (double)y);
        GL11.glEnd();
        GL11.glDisable(3042);
    }
    
    public static void drawBorderedRoundedRect(final float x, final float y, final float x1, final float y1, final float borderSize, final int borderC, final int insideC) {
        drawRoundedRect(x, y, x1, y1, borderSize, borderC);
        drawRoundedRect(x + 0.5f, y + 0.5f, x1 - 0.5f, y1 - 0.5f, borderSize, insideC);
    }
    
    public static void drawBorderedRoundedRect(final float x, final float y, final float x1, final float y1, final float radius, final float borderSize, final int borderC, final int insideC) {
        drawRoundedRect(x, y, x1, y1, radius, borderC);
        drawRoundedRect(x + borderSize, y + borderSize, x1 - borderSize, y1 - borderSize, radius, insideC);
    }
    
    public static void drawTexturedRect(final float x, final float y, final int width, final int height, final int u, final int v) {
        final float scale = 0.00390625f;
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + v, 0.0, width * scale, (height + (float)v) * scale);
        tessellator.addVertexWithUV(x + u, y + v, 0.0, (width + (float)u) * scale, (height + (float)v) * scale);
        tessellator.addVertexWithUV(x + u, y, 0.0, (width + (float)u) * scale, height * scale);
        tessellator.addVertexWithUV(x, y, 0.0, width * scale, height * scale);
        tessellator.draw();
    }
    
    public static void setColor(final int color) {
        final float r = (color >> 24 & 0xFF) / 255.0f;
        final float g = (color >> 16 & 0xFF) / 255.0f;
        final float b = (color >> 8 & 0xFF) / 255.0f;
        final float a = (color & 0xFF) / 255.0f;
        GL11.glColor4f(r, g, b, a);
    }
    
    public static void drawRect(float minX, float minY, float maxX, float maxY, final int color) {
        if (minX < maxX) {
            final float bounds = minX;
            minX = maxX;
            maxX = bounds;
        }
        if (minY < maxY) {
            final float bounds = minY;
            minY = maxY;
            maxY = bounds;
        }
        final float r = (color >> 24 & 0xFF) / 255.0f;
        final float g = (color >> 16 & 0xFF) / 255.0f;
        final float b = (color >> 8 & 0xFF) / 255.0f;
        final float a = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(g, b, a, r);
        tessellator.startDrawingQuads();
        tessellator.addVertex(minX, maxY, 0.0);
        tessellator.addVertex(maxX, maxY, 0.0);
        tessellator.addVertex(maxX, minY, 0.0);
        tessellator.addVertex(minX, minY, 0.0);
        tessellator.draw();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
    
    public static void drawBorderedRect(final float x, final float y, final float x2, final float y2, final float border, final int bColor, final int color) {
        drawRect(x + border, y + border, x2 - border, y2 - border, color);
        drawRect(x, y + border, x + border, y2 - border, bColor);
        drawRect(x2 - border, y + border, x2, y2 - border, bColor);
        drawRect(x, y, x2, y + border, bColor);
        drawRect(x, y2 - border, x2, y2, bColor);
    }
    
    public static void drawHollowRect(final float x, final float y, final float x2, final float y2, final float border, final int bColor) {
        drawRect(x, y + border, x + border, y2 - border, bColor);
        drawRect(x2 - border, y + border, x2, y2 - border, bColor);
        drawRect(x, y, x2, y + border, bColor);
        drawRect(x, y2 - border, x2, y2, bColor);
    }
    
    public static void drawBorderedRect(final int x, final int y, final int x2, final int y2, final int border, final int bColor, final int color) {
        Gui.drawRect(x + border, y + border, x2 - border, y2 - border, color);
        Gui.drawRect(x, y + border, x + border, y2 - border, bColor);
        Gui.drawRect(x2 - border, y + border, x2, y2 - border, bColor);
        Gui.drawRect(x, y, x2, y + border, bColor);
        Gui.drawRect(x, y2 - border, x2, y2, bColor);
    }
    
    public static void drawRoundedRect(double x, double y, double x1, double y1, final double radius, final int color) {
        final float f = (color >> 24 & 0xFF) / 255.0f;
        final float f2 = (color >> 16 & 0xFF) / 255.0f;
        final float f3 = (color >> 8 & 0xFF) / 255.0f;
        final float f4 = (color & 0xFF) / 255.0f;
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5, 0.5, 0.5);
        x *= 2.0;
        y *= 2.0;
        x1 *= 2.0;
        y1 *= 2.0;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glEnable(2848);
        GL11.glBegin(9);
        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x + radius + Math.sin(i * 3.141592653589793 / 180.0) * (radius * -1.0), y + radius + Math.cos(i * 3.141592653589793 / 180.0) * (radius * -1.0));
        }
        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x + radius + Math.sin(i * 3.141592653589793 / 180.0) * (radius * -1.0), y1 - radius + Math.cos(i * 3.141592653589793 / 180.0) * (radius * -1.0));
        }
        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x1 - radius + Math.sin(i * 3.141592653589793 / 180.0) * radius, y1 - radius + Math.cos(i * 3.141592653589793 / 180.0) * radius);
        }
        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x1 - radius + Math.sin(i * 3.141592653589793 / 180.0) * radius, y + radius + Math.cos(i * 3.141592653589793 / 180.0) * radius);
        }
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glScaled(2.0, 2.0, 2.0);
        GL11.glPopAttrib();
    }
    
    public static void drawRoundedTexturedRect(final ResourceLocation resourceLocation, double x, double y, double x1, double y1, final double radius, final int color) {
        final float f = (color >> 24 & 0xFF) / 255.0f;
        final float f2 = (color >> 16 & 0xFF) / 255.0f;
        final float f3 = (color >> 8 & 0xFF) / 255.0f;
        final float f4 = (color & 0xFF) / 255.0f;
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5, 0.5, 0.5);
        x *= 2.0;
        y *= 2.0;
        x1 *= 2.0;
        y1 *= 2.0;
        GL11.glEnable(3042);
        GL11.glEnable(3553);
        GL11.glColor4f(f2, f3, f4, f);
        bindTexture(resourceLocation);
        GL11.glEnable(2848);
        GL11.glBegin(9);
        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x + radius + Math.sin(i * 3.141592653589793 / 180.0) * (radius * -1.0), y + radius + Math.cos(i * 3.141592653589793 / 180.0) * (radius * -1.0));
        }
        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x + radius + Math.sin(i * 3.141592653589793 / 180.0) * (radius * -1.0), y1 - radius + Math.cos(i * 3.141592653589793 / 180.0) * (radius * -1.0));
        }
        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x1 - radius + Math.sin(i * 3.141592653589793 / 180.0) * radius, y1 - radius + Math.cos(i * 3.141592653589793 / 180.0) * radius);
        }
        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x1 - radius + Math.sin(i * 3.141592653589793 / 180.0) * radius, y + radius + Math.cos(i * 3.141592653589793 / 180.0) * radius);
        }
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glScaled(2.0, 2.0, 2.0);
        GL11.glPopAttrib();
    }
}
