package net.minecraft.client.gui;

import net.minecraft.util.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;

public class GuiStreamIndicator
{
    private static final ResourceLocation field_152441_a;
    private final Minecraft field_152442_b;
    private float field_152443_c;
    private int field_152444_d;
    private static final String __OBFID = "CL_00001849";
    
    public GuiStreamIndicator(final Minecraft p_i46322_1_) {
        this.field_152443_c = 1.0f;
        this.field_152444_d = 1;
        this.field_152442_b = p_i46322_1_;
    }
    
    public void func_152437_a(final int p_152437_1_, final int p_152437_2_) {
        if (this.field_152442_b.func_152346_Z().func_152934_n()) {
            GL11.glEnable(3042);
            final int var3 = this.field_152442_b.func_152346_Z().func_152920_A();
            if (var3 > 0) {
                final String var4 = "" + var3;
                final int var5 = this.field_152442_b.fontRenderer.getStringWidth(var4);
                final boolean var6 = true;
                final int var7 = p_152437_1_ - var5 - 1;
                final int var8 = p_152437_2_ + 20 - 1;
                final int var9 = p_152437_2_ + 20 + this.field_152442_b.fontRenderer.FONT_HEIGHT - 1;
                GL11.glDisable(3553);
                final Tessellator var10 = Tessellator.instance;
                GL11.glColor4f(0.0f, 0.0f, 0.0f, (0.65f + 0.35000002f * this.field_152443_c) / 2.0f);
                var10.startDrawingQuads();
                var10.addVertex(var7, var9, 0.0);
                var10.addVertex(p_152437_1_, var9, 0.0);
                var10.addVertex(p_152437_1_, var8, 0.0);
                var10.addVertex(var7, var8, 0.0);
                var10.draw();
                GL11.glEnable(3553);
                this.field_152442_b.fontRenderer.drawString(var4, p_152437_1_ - var5, p_152437_2_ + 20, 16777215);
            }
            this.func_152436_a(p_152437_1_, p_152437_2_, this.func_152440_b(), 0);
            this.func_152436_a(p_152437_1_, p_152437_2_, this.func_152438_c(), 17);
        }
    }
    
    private void func_152436_a(final int p_152436_1_, final int p_152436_2_, final int p_152436_3_, final int p_152436_4_) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.65f + 0.35000002f * this.field_152443_c);
        this.field_152442_b.getTextureManager().bindTexture(GuiStreamIndicator.field_152441_a);
        final float var5 = 150.0f;
        final float var6 = 0.0f;
        final float var7 = p_152436_3_ * 0.015625f;
        final float var8 = 1.0f;
        final float var9 = (p_152436_3_ + 16) * 0.015625f;
        final Tessellator var10 = Tessellator.instance;
        var10.startDrawingQuads();
        var10.addVertexWithUV(p_152436_1_ - 16 - p_152436_4_, p_152436_2_ + 16, var5, var6, var9);
        var10.addVertexWithUV(p_152436_1_ - p_152436_4_, p_152436_2_ + 16, var5, var8, var9);
        var10.addVertexWithUV(p_152436_1_ - p_152436_4_, p_152436_2_ + 0, var5, var8, var7);
        var10.addVertexWithUV(p_152436_1_ - 16 - p_152436_4_, p_152436_2_ + 0, var5, var6, var7);
        var10.draw();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private int func_152440_b() {
        return this.field_152442_b.func_152346_Z().func_152919_o() ? 16 : 0;
    }
    
    private int func_152438_c() {
        return this.field_152442_b.func_152346_Z().func_152929_G() ? 48 : 32;
    }
    
    public void func_152439_a() {
        if (this.field_152442_b.func_152346_Z().func_152934_n()) {
            this.field_152443_c += 0.025f * this.field_152444_d;
            if (this.field_152443_c < 0.0f) {
                this.field_152444_d *= -1;
                this.field_152443_c = 0.0f;
            }
            else if (this.field_152443_c > 1.0f) {
                this.field_152444_d *= -1;
                this.field_152443_c = 1.0f;
            }
        }
        else {
            this.field_152443_c = 1.0f;
            this.field_152444_d = 1;
        }
    }
    
    static {
        field_152441_a = new ResourceLocation("textures/gui/stream_indicator.png");
    }
}
