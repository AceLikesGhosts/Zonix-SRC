package net.minecraft.client.renderer;

import java.nio.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;

public class RenderHelper
{
    private static FloatBuffer colorBuffer;
    private static final Vec3 field_82884_b;
    private static final Vec3 field_82885_c;
    private static final String __OBFID = "CL_00000629";
    
    public static void disableStandardItemLighting() {
        GL11.glDisable(2896);
        GL11.glDisable(16384);
        GL11.glDisable(16385);
        GL11.glDisable(2903);
    }
    
    public static void enableStandardItemLighting() {
        GL11.glEnable(2896);
        GL11.glEnable(16384);
        GL11.glEnable(16385);
        GL11.glEnable(2903);
        GL11.glColorMaterial(1032, 5634);
        final float var0 = 0.4f;
        final float var2 = 0.6f;
        final float var3 = 0.0f;
        GL11.glLight(16384, 4611, setColorBuffer(RenderHelper.field_82884_b.xCoord, RenderHelper.field_82884_b.yCoord, RenderHelper.field_82884_b.zCoord, 0.0));
        GL11.glLight(16384, 4609, setColorBuffer(var2, var2, var2, 1.0f));
        GL11.glLight(16384, 4608, setColorBuffer(0.0f, 0.0f, 0.0f, 1.0f));
        GL11.glLight(16384, 4610, setColorBuffer(var3, var3, var3, 1.0f));
        GL11.glLight(16385, 4611, setColorBuffer(RenderHelper.field_82885_c.xCoord, RenderHelper.field_82885_c.yCoord, RenderHelper.field_82885_c.zCoord, 0.0));
        GL11.glLight(16385, 4609, setColorBuffer(var2, var2, var2, 1.0f));
        GL11.glLight(16385, 4608, setColorBuffer(0.0f, 0.0f, 0.0f, 1.0f));
        GL11.glLight(16385, 4610, setColorBuffer(var3, var3, var3, 1.0f));
        GL11.glShadeModel(7424);
        GL11.glLightModel(2899, setColorBuffer(var0, var0, var0, 1.0f));
    }
    
    private static FloatBuffer setColorBuffer(final double p_74517_0_, final double p_74517_2_, final double p_74517_4_, final double p_74517_6_) {
        return setColorBuffer((float)p_74517_0_, (float)p_74517_2_, (float)p_74517_4_, (float)p_74517_6_);
    }
    
    private static FloatBuffer setColorBuffer(final float p_74521_0_, final float p_74521_1_, final float p_74521_2_, final float p_74521_3_) {
        RenderHelper.colorBuffer.clear();
        RenderHelper.colorBuffer.put(p_74521_0_).put(p_74521_1_).put(p_74521_2_).put(p_74521_3_);
        RenderHelper.colorBuffer.flip();
        return RenderHelper.colorBuffer;
    }
    
    public static void enableGUIStandardItemLighting() {
        GL11.glPushMatrix();
        GL11.glRotatef(-30.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(165.0f, 1.0f, 0.0f, 0.0f);
        enableStandardItemLighting();
        GL11.glPopMatrix();
    }
    
    static {
        RenderHelper.colorBuffer = GLAllocation.createDirectFloatBuffer(16);
        field_82884_b = Vec3.createVectorHelper(0.20000000298023224, 1.0, -0.699999988079071).normalize();
        field_82885_c = Vec3.createVectorHelper(-0.20000000298023224, 1.0, 0.699999988079071).normalize();
    }
}
