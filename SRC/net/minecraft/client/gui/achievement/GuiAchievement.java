package net.minecraft.client.gui.achievement;

import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.stats.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.resources.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;

public class GuiAchievement extends Gui
{
    private static final ResourceLocation field_146261_a;
    private Minecraft field_146259_f;
    private int field_146260_g;
    private int field_146267_h;
    private String field_146268_i;
    private String field_146265_j;
    private Achievement field_146266_k;
    private long field_146263_l;
    private RenderItem field_146264_m;
    private boolean field_146262_n;
    private static final String __OBFID = "CL_00000721";
    
    public GuiAchievement(final Minecraft p_i1063_1_) {
        this.field_146259_f = p_i1063_1_;
        this.field_146264_m = new RenderItem();
    }
    
    public void func_146256_a(final Achievement p_146256_1_) {
        this.field_146268_i = I18n.format("achievement.get", new Object[0]);
        this.field_146265_j = p_146256_1_.func_150951_e().getUnformattedText();
        this.field_146263_l = Minecraft.getSystemTime();
        this.field_146266_k = p_146256_1_;
        this.field_146262_n = false;
    }
    
    public void func_146255_b(final Achievement p_146255_1_) {
        this.field_146268_i = p_146255_1_.func_150951_e().getUnformattedText();
        this.field_146265_j = p_146255_1_.getDescription();
        this.field_146263_l = Minecraft.getSystemTime() + 2500L;
        this.field_146266_k = p_146255_1_;
        this.field_146262_n = true;
    }
    
    private void func_146258_c() {
        GL11.glViewport(0, 0, this.field_146259_f.displayWidth, this.field_146259_f.displayHeight);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        this.field_146260_g = this.field_146259_f.displayWidth;
        this.field_146267_h = this.field_146259_f.displayHeight;
        final ScaledResolution var1 = new ScaledResolution(this.field_146259_f, this.field_146259_f.displayWidth, this.field_146259_f.displayHeight);
        this.field_146260_g = var1.getScaledWidth();
        this.field_146267_h = var1.getScaledHeight();
        GL11.glClear(256);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, (double)this.field_146260_g, (double)this.field_146267_h, 0.0, 1000.0, 3000.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
    }
    
    public void func_146254_a() {
        if (this.field_146266_k != null && this.field_146263_l != 0L && Minecraft.getMinecraft().thePlayer != null) {
            double var1 = (Minecraft.getSystemTime() - this.field_146263_l) / 3000.0;
            if (!this.field_146262_n) {
                if (var1 < 0.0 || var1 > 1.0) {
                    this.field_146263_l = 0L;
                    return;
                }
            }
            else if (var1 > 0.5) {
                var1 = 0.5;
            }
            this.func_146258_c();
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            double var2 = var1 * 2.0;
            if (var2 > 1.0) {
                var2 = 2.0 - var2;
            }
            var2 *= 4.0;
            var2 = 1.0 - var2;
            if (var2 < 0.0) {
                var2 = 0.0;
            }
            var2 *= var2;
            var2 *= var2;
            final int var3 = this.field_146260_g - 160;
            final int var4 = 0 - (int)(var2 * 36.0);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glEnable(3553);
            this.field_146259_f.getTextureManager().bindTexture(GuiAchievement.field_146261_a);
            GL11.glDisable(2896);
            this.drawTexturedModalRect(var3, var4, 96, 202, 160, 32);
            if (this.field_146262_n) {
                this.field_146259_f.fontRenderer.drawSplitString(this.field_146265_j, var3 + 30, var4 + 7, 120, -1);
            }
            else {
                this.field_146259_f.fontRenderer.drawString(this.field_146268_i, var3 + 30, var4 + 7, -256);
                this.field_146259_f.fontRenderer.drawString(this.field_146265_j, var3 + 30, var4 + 18, -1);
            }
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glDisable(2896);
            GL11.glEnable(32826);
            GL11.glEnable(2903);
            GL11.glEnable(2896);
            this.field_146264_m.renderItemAndEffectIntoGUI(this.field_146259_f.fontRenderer, this.field_146259_f.getTextureManager(), this.field_146266_k.theItemStack, var3 + 8, var4 + 8);
            GL11.glDisable(2896);
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
        }
    }
    
    public void func_146257_b() {
        this.field_146266_k = null;
        this.field_146263_l = 0L;
    }
    
    static {
        field_146261_a = new ResourceLocation("textures/gui/achievement/achievement_background.png");
    }
}
