package net.minecraft.src;

import org.lwjgl.opengl.*;

public class Blender
{
    public static final int BLEND_ALPHA = 0;
    public static final int BLEND_ADD = 1;
    public static final int BLEND_SUBSTRACT = 2;
    public static final int BLEND_MULTIPLY = 3;
    public static final int BLEND_DODGE = 4;
    public static final int BLEND_BURN = 5;
    public static final int BLEND_SCREEN = 6;
    public static final int BLEND_OVERLAY = 7;
    public static final int BLEND_REPLACE = 8;
    public static final int BLEND_DEFAULT = 1;
    
    public static int parseBlend(String str) {
        if (str == null) {
            return 1;
        }
        str = str.toLowerCase().trim();
        if (str.equals("alpha")) {
            return 0;
        }
        if (str.equals("add")) {
            return 1;
        }
        if (str.equals("subtract")) {
            return 2;
        }
        if (str.equals("multiply")) {
            return 3;
        }
        if (str.equals("dodge")) {
            return 4;
        }
        if (str.equals("burn")) {
            return 5;
        }
        if (str.equals("screen")) {
            return 6;
        }
        if (str.equals("overlay")) {
            return 7;
        }
        if (str.equals("replace")) {
            return 8;
        }
        Config.warn("Unknown blend: " + str);
        return 1;
    }
    
    public static void setupBlend(final int blend, final float brightness) {
        switch (blend) {
            case 0: {
                GL11.glDisable(3008);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, brightness);
                break;
            }
            case 1: {
                GL11.glDisable(3008);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 1);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, brightness);
                break;
            }
            case 2: {
                GL11.glDisable(3008);
                GL11.glEnable(3042);
                GL11.glBlendFunc(775, 0);
                GL11.glColor4f(brightness, brightness, brightness, 1.0f);
                break;
            }
            case 3: {
                GL11.glDisable(3008);
                GL11.glEnable(3042);
                GL11.glBlendFunc(774, 771);
                GL11.glColor4f(brightness, brightness, brightness, brightness);
                break;
            }
            case 4: {
                GL11.glDisable(3008);
                GL11.glEnable(3042);
                GL11.glBlendFunc(1, 1);
                GL11.glColor4f(brightness, brightness, brightness, 1.0f);
                break;
            }
            case 5: {
                GL11.glDisable(3008);
                GL11.glEnable(3042);
                GL11.glBlendFunc(0, 769);
                GL11.glColor4f(brightness, brightness, brightness, 1.0f);
                break;
            }
            case 6: {
                GL11.glDisable(3008);
                GL11.glEnable(3042);
                GL11.glBlendFunc(1, 769);
                GL11.glColor4f(brightness, brightness, brightness, 1.0f);
                break;
            }
            case 7: {
                GL11.glDisable(3008);
                GL11.glEnable(3042);
                GL11.glBlendFunc(774, 768);
                GL11.glColor4f(brightness, brightness, brightness, 1.0f);
                break;
            }
            case 8: {
                GL11.glEnable(3008);
                GL11.glDisable(3042);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, brightness);
                break;
            }
        }
        GL11.glEnable(3553);
    }
    
    public static void clearBlend(final float rainBrightness) {
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, rainBrightness);
    }
}
