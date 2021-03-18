package us.zonix.client.util;

import net.minecraft.util.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public final class HUDUtils
{
    private static int[] colorCodes;
    
    public static int getColorCode(final char c, final boolean isLighter) {
        return HUDUtils.colorCodes[isLighter ? "0123456789abcdef".indexOf(c) : ("0123456789abcdef".indexOf(c) + 16)];
    }
    
    public static void drawContinuousTexturedBox(final int x, final int y, final int u, final int v, final int width, final int height, final int textureWidth, final int textureHeight, final int borderSize, final float zLevel) {
        drawContinuousTexturedBox(x, y, u, v, width, height, textureWidth, textureHeight, borderSize, borderSize, borderSize, borderSize, zLevel);
    }
    
    public static void drawContinuousTexturedBox(final ResourceLocation res, final int x, final int y, final int u, final int v, final int width, final int height, final int textureWidth, final int textureHeight, final int borderSize, final float zLevel) {
        drawContinuousTexturedBox(res, x, y, u, v, width, height, textureWidth, textureHeight, borderSize, borderSize, borderSize, borderSize, zLevel);
    }
    
    public static void drawContinuousTexturedBox(final ResourceLocation res, final int x, final int y, final int u, final int v, final int width, final int height, final int textureWidth, final int textureHeight, final int topBorder, final int bottomBorder, final int leftBorder, final int rightBorder, final float zLevel) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(res);
        drawContinuousTexturedBox(x, y, u, v, width, height, textureWidth, textureHeight, topBorder, bottomBorder, leftBorder, rightBorder, zLevel);
    }
    
    public static void drawContinuousTexturedBox(final int x, final int y, final int u, final int v, final int width, final int height, final int textureWidth, final int textureHeight, final int topBorder, final int bottomBorder, final int leftBorder, final int rightBorder, final float zLevel) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(3042);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(770, 771);
        final int fillerWidth = textureWidth - leftBorder - rightBorder;
        final int fillerHeight = textureHeight - topBorder - bottomBorder;
        final int canvasWidth = width - leftBorder - rightBorder;
        final int canvasHeight = height - topBorder - bottomBorder;
        final int xPasses = canvasWidth / fillerWidth;
        final int remainderWidth = canvasWidth % fillerWidth;
        final int yPasses = canvasHeight / fillerHeight;
        final int remainderHeight = canvasHeight % fillerHeight;
        drawTexturedModalRect(x, y, u, v, leftBorder, topBorder, zLevel);
        drawTexturedModalRect(x + leftBorder + canvasWidth, y, u + leftBorder + fillerWidth, v, rightBorder, topBorder, zLevel);
        drawTexturedModalRect(x, y + topBorder + canvasHeight, u, v + topBorder + fillerHeight, leftBorder, bottomBorder, zLevel);
        drawTexturedModalRect(x + leftBorder + canvasWidth, y + topBorder + canvasHeight, u + leftBorder + fillerWidth, v + topBorder + fillerHeight, rightBorder, bottomBorder, zLevel);
        for (int i = 0; i < xPasses + ((remainderWidth > 0) ? 1 : 0); ++i) {
            drawTexturedModalRect(x + leftBorder + i * fillerWidth, y, u + leftBorder, v, (i == xPasses) ? remainderWidth : fillerWidth, topBorder, zLevel);
            drawTexturedModalRect(x + leftBorder + i * fillerWidth, y + topBorder + canvasHeight, u + leftBorder, v + topBorder + fillerHeight, (i == xPasses) ? remainderWidth : fillerWidth, bottomBorder, zLevel);
            for (int j = 0; j < yPasses + ((remainderHeight > 0) ? 1 : 0); ++j) {
                drawTexturedModalRect(x + leftBorder + i * fillerWidth, y + topBorder + j * fillerHeight, u + leftBorder, v + topBorder, (i == xPasses) ? remainderWidth : fillerWidth, (j == yPasses) ? remainderHeight : fillerHeight, zLevel);
            }
        }
        for (int k = 0; k < yPasses + ((remainderHeight > 0) ? 1 : 0); ++k) {
            drawTexturedModalRect(x, y + topBorder + k * fillerHeight, u, v + topBorder, leftBorder, (k == yPasses) ? remainderHeight : fillerHeight, zLevel);
            drawTexturedModalRect(x + leftBorder + canvasWidth, y + topBorder + k * fillerHeight, u + leftBorder + fillerWidth, v + topBorder, rightBorder, (k == yPasses) ? remainderHeight : fillerHeight, zLevel);
        }
    }
    
    public static void drawTexturedModalRect(final int x, final int y, final int u, final int v, final int width, final int height, final float zLevel) {
        final float var7 = 0.00390625f;
        final float var8 = 0.00390625f;
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + 0, y + height, zLevel, (u + 0) * var7, (v + height) * var8);
        tessellator.addVertexWithUV(x + width, y + height, zLevel, (u + width) * var7, (v + height) * var8);
        tessellator.addVertexWithUV(x + width, y + 0, zLevel, (u + width) * var7, (v + 0) * var8);
        tessellator.addVertexWithUV(x + 0, y + 0, zLevel, (u + 0) * var7, (v + 0) * var8);
        tessellator.draw();
    }
    
    public static void renderItemOverlayIntoGUI(final FontRenderer fontRenderer, final ItemStack itemStack, final int x, final int y) {
        renderItemOverlayIntoGUI(fontRenderer, itemStack, x, y, true, true);
    }
    
    public static void renderItemOverlayIntoGUI(final FontRenderer fontRenderer, final ItemStack itemStack, final int x, final int y, final boolean showDamageBar, final boolean showCount) {
        if (itemStack != null && (showDamageBar || showCount)) {
            if (itemStack.isItemDamaged() && showDamageBar) {
                final int var11 = (int)Math.round(13.0 - itemStack.getItemDamageForDisplay() * 13.0 / itemStack.getMaxDamage());
                final int var12 = (int)Math.round(255.0 - itemStack.getItemDamageForDisplay() * 255.0 / itemStack.getMaxDamage());
                GL11.glDisable(2896);
                GL11.glDisable(2929);
                GL11.glDisable(3553);
                final Tessellator var13 = Tessellator.instance;
                final int var14 = 255 - var12 << 16 | var12 << 8;
                final int var15 = (255 - var12) / 4 << 16 | 0x3F00;
                renderQuad(var13, x + 2, y + 13, 13, 2, 0);
                renderQuad(var13, x + 2, y + 13, 12, 1, var15);
                renderQuad(var13, x + 2, y + 13, var11, 1, var14);
                GL11.glEnable(3553);
                GL11.glEnable(2896);
                GL11.glEnable(2929);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            }
            if (showCount) {
                int count = 0;
                if (itemStack.getMaxStackSize() > 1) {
                    count = countInInventory(Minecraft.getMinecraft().thePlayer, itemStack.getItem(), itemStack.getItemDamage());
                }
                else if (itemStack.getItem().equals(Items.bow)) {
                    count = countInInventory(Minecraft.getMinecraft().thePlayer, Items.arrow);
                }
                if (count > 1) {
                    final String var16 = "" + count;
                    GL11.glDisable(2896);
                    GL11.glDisable(2929);
                    fontRenderer.drawStringWithShadow(var16, x + 19 - 2 - fontRenderer.getStringWidth(var16), y + 6 + 3, 16777215);
                    GL11.glEnable(2896);
                    GL11.glEnable(2929);
                }
            }
        }
    }
    
    public static void renderQuad(final Tessellator tessellator, final int x, final int y, final int width, final int height, final int color) {
        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_I(color);
        tessellator.addVertex(x + 0, y + 0, 0.0);
        tessellator.addVertex(x + 0, y + height, 0.0);
        tessellator.addVertex(x + width, y + height, 0.0);
        tessellator.addVertex(x + width, y + 0, 0.0);
        tessellator.draw();
    }
    
    public static int countInInventory(final EntityPlayer player, final Item item) {
        return countInInventory(player, item, -1);
    }
    
    public static int countInInventory(final EntityPlayer player, final Item item, final int md) {
        int count = 0;
        for (int i = 0; i < player.inventory.mainInventory.length; ++i) {
            if (player.inventory.mainInventory[i] != null && item.equals(player.inventory.mainInventory[i].getItem()) && (md == -1 || player.inventory.mainInventory[i].getItemDamage() == md)) {
                count += player.inventory.mainInventory[i].stackSize;
            }
        }
        return count;
    }
    
    static {
        HUDUtils.colorCodes = new int[] { 0, 170, 43520, 43690, 11141120, 11141290, 16755200, 11184810, 5592405, 5592575, 5635925, 5636095, 16733525, 16733695, 16777045, 16777215, 0, 42, 10752, 10794, 2752512, 2752554, 2763264, 2763306, 1381653, 1381695, 1392405, 1392447, 4134165, 4134207, 4144917, 4144959 };
    }
}
