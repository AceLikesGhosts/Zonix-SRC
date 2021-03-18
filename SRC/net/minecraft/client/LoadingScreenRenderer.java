package net.minecraft.client;

import net.minecraft.client.shader.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;

public class LoadingScreenRenderer implements IProgressUpdate
{
    private String field_73727_a;
    private Minecraft mc;
    private String currentlyDisplayedText;
    private long field_73723_d;
    private boolean field_73724_e;
    private ScaledResolution field_146587_f;
    private Framebuffer field_146588_g;
    private static final String __OBFID = "CL_00000655";
    
    public LoadingScreenRenderer(final Minecraft p_i1017_1_) {
        this.field_73727_a = "";
        this.currentlyDisplayedText = "";
        this.field_73723_d = Minecraft.getSystemTime();
        this.mc = p_i1017_1_;
        this.field_146587_f = new ScaledResolution(p_i1017_1_, p_i1017_1_.displayWidth, p_i1017_1_.displayHeight);
        (this.field_146588_g = new Framebuffer(p_i1017_1_.displayWidth, p_i1017_1_.displayHeight, false)).setFramebufferFilter(9728);
    }
    
    @Override
    public void resetProgressAndMessage(final String p_73721_1_) {
        this.field_73724_e = false;
        this.func_73722_d(p_73721_1_);
    }
    
    @Override
    public void displayProgressMessage(final String p_73720_1_) {
        this.field_73724_e = true;
        this.func_73722_d(p_73720_1_);
    }
    
    public void func_73722_d(final String p_73722_1_) {
        this.currentlyDisplayedText = p_73722_1_;
        if (!this.mc.running) {
            if (!this.field_73724_e) {
                throw new MinecraftError();
            }
        }
        else {
            GL11.glClear(256);
            GL11.glMatrixMode(5889);
            GL11.glLoadIdentity();
            if (OpenGlHelper.isFramebufferEnabled()) {
                final int var2 = this.field_146587_f.getScaleFactor();
                GL11.glOrtho(0.0, (double)(this.field_146587_f.getScaledWidth() * var2), (double)(this.field_146587_f.getScaledHeight() * var2), 0.0, 100.0, 300.0);
            }
            else {
                final ScaledResolution var3 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
                GL11.glOrtho(0.0, var3.getScaledWidth_double(), var3.getScaledHeight_double(), 0.0, 100.0, 300.0);
            }
            GL11.glMatrixMode(5888);
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0f, 0.0f, -200.0f);
        }
    }
    
    @Override
    public void resetProgresAndWorkingMessage(final String p_73719_1_) {
        if (!this.mc.running) {
            if (!this.field_73724_e) {
                throw new MinecraftError();
            }
        }
        else {
            this.field_73723_d = 0L;
            this.field_73727_a = p_73719_1_;
            this.setLoadingProgress(-1);
            this.field_73723_d = 0L;
        }
    }
    
    @Override
    public void setLoadingProgress(final int p_73718_1_) {
        if (!this.mc.running) {
            if (!this.field_73724_e) {
                throw new MinecraftError();
            }
        }
        else {
            final long var2 = Minecraft.getSystemTime();
            if (var2 - this.field_73723_d >= 100L) {
                this.field_73723_d = var2;
                final ScaledResolution var3 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
                final int var4 = var3.getScaleFactor();
                final int var5 = var3.getScaledWidth();
                final int var6 = var3.getScaledHeight();
                if (OpenGlHelper.isFramebufferEnabled()) {
                    this.field_146588_g.framebufferClear();
                }
                else {
                    GL11.glClear(256);
                }
                this.field_146588_g.bindFramebuffer(false);
                GL11.glMatrixMode(5889);
                GL11.glLoadIdentity();
                GL11.glOrtho(0.0, var3.getScaledWidth_double(), var3.getScaledHeight_double(), 0.0, 100.0, 300.0);
                GL11.glMatrixMode(5888);
                GL11.glLoadIdentity();
                GL11.glTranslatef(0.0f, 0.0f, -200.0f);
                if (!OpenGlHelper.isFramebufferEnabled()) {
                    GL11.glClear(16640);
                }
                final Tessellator var7 = Tessellator.instance;
                this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
                final float var8 = 32.0f;
                var7.startDrawingQuads();
                var7.setColorOpaque_I(4210752);
                var7.addVertexWithUV(0.0, var6, 0.0, 0.0, var6 / var8);
                var7.addVertexWithUV(var5, var6, 0.0, var5 / var8, var6 / var8);
                var7.addVertexWithUV(var5, 0.0, 0.0, var5 / var8, 0.0);
                var7.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
                var7.draw();
                if (p_73718_1_ >= 0) {
                    final byte var9 = 100;
                    final byte var10 = 2;
                    final int var11 = var5 / 2 - var9 / 2;
                    final int var12 = var6 / 2 + 16;
                    GL11.glDisable(3553);
                    var7.startDrawingQuads();
                    var7.setColorOpaque_I(8421504);
                    var7.addVertex(var11, var12, 0.0);
                    var7.addVertex(var11, var12 + var10, 0.0);
                    var7.addVertex(var11 + var9, var12 + var10, 0.0);
                    var7.addVertex(var11 + var9, var12, 0.0);
                    var7.setColorOpaque_I(8454016);
                    var7.addVertex(var11, var12, 0.0);
                    var7.addVertex(var11, var12 + var10, 0.0);
                    var7.addVertex(var11 + p_73718_1_, var12 + var10, 0.0);
                    var7.addVertex(var11 + p_73718_1_, var12, 0.0);
                    var7.draw();
                    GL11.glEnable(3553);
                }
                GL11.glEnable(3042);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                this.mc.fontRenderer.drawStringWithShadow(this.currentlyDisplayedText, (var5 - this.mc.fontRenderer.getStringWidth(this.currentlyDisplayedText)) / 2, var6 / 2 - 4 - 16, 16777215);
                this.mc.fontRenderer.drawStringWithShadow(this.field_73727_a, (var5 - this.mc.fontRenderer.getStringWidth(this.field_73727_a)) / 2, var6 / 2 - 4 + 8, 16777215);
                this.field_146588_g.unbindFramebuffer();
                if (OpenGlHelper.isFramebufferEnabled()) {
                    this.field_146588_g.framebufferRender(var5 * var4, var6 * var4);
                }
                this.mc.func_147120_f();
                try {
                    Thread.yield();
                }
                catch (Exception ex) {}
            }
        }
    }
    
    @Override
    public void func_146586_a() {
    }
}
