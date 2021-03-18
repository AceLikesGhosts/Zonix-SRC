package net.minecraft.client.gui;

import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import org.apache.commons.io.*;
import java.io.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import org.apache.logging.log4j.*;

public class GuiWinGame extends GuiScreen
{
    private static final Logger logger;
    private static final ResourceLocation field_146576_f;
    private static final ResourceLocation field_146577_g;
    private int field_146581_h;
    private List field_146582_i;
    private int field_146579_r;
    private float field_146578_s;
    private static final String __OBFID = "CL_00000719";
    
    public GuiWinGame() {
        this.field_146578_s = 0.5f;
    }
    
    @Override
    public void updateScreen() {
        ++this.field_146581_h;
        final float var1 = (this.field_146579_r + this.height + this.height + 24) / this.field_146578_s;
        if (this.field_146581_h > var1) {
            this.func_146574_g();
        }
    }
    
    @Override
    protected void keyTyped(final char p_73869_1_, final int p_73869_2_) {
        if (p_73869_2_ == 1) {
            this.func_146574_g();
        }
    }
    
    private void func_146574_g() {
        this.mc.thePlayer.sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
        this.mc.displayGuiScreen(null);
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
    
    @Override
    public void initGui() {
        if (this.field_146582_i == null) {
            this.field_146582_i = new ArrayList();
            try {
                String var1 = "";
                final String var2 = "" + EnumChatFormatting.WHITE + EnumChatFormatting.OBFUSCATED + EnumChatFormatting.GREEN + EnumChatFormatting.AQUA;
                final short var3 = 274;
                BufferedReader var4 = new BufferedReader(new InputStreamReader(this.mc.getResourceManager().getResource(new ResourceLocation("texts/end.txt")).getInputStream(), Charsets.UTF_8));
                final Random var5 = new Random(8124371L);
                while ((var1 = var4.readLine()) != null) {
                    String var7;
                    String var8;
                    for (var1 = var1.replaceAll("PLAYERNAME", this.mc.getSession().getUsername()); var1.contains(var2); var1 = var7 + EnumChatFormatting.WHITE + EnumChatFormatting.OBFUSCATED + "XXXXXXXX".substring(0, var5.nextInt(4) + 3) + var8) {
                        final int var6 = var1.indexOf(var2);
                        var7 = var1.substring(0, var6);
                        var8 = var1.substring(var6 + var2.length());
                    }
                    this.field_146582_i.addAll(this.mc.fontRenderer.listFormattedStringToWidth(var1, var3));
                    this.field_146582_i.add("");
                }
                for (int var6 = 0; var6 < 8; ++var6) {
                    this.field_146582_i.add("");
                }
                var4 = new BufferedReader(new InputStreamReader(this.mc.getResourceManager().getResource(new ResourceLocation("texts/credits.txt")).getInputStream(), Charsets.UTF_8));
                while ((var1 = var4.readLine()) != null) {
                    var1 = var1.replaceAll("PLAYERNAME", this.mc.getSession().getUsername());
                    var1 = var1.replaceAll("\t", "    ");
                    this.field_146582_i.addAll(this.mc.fontRenderer.listFormattedStringToWidth(var1, var3));
                    this.field_146582_i.add("");
                }
                this.field_146579_r = this.field_146582_i.size() * 12;
            }
            catch (Exception var9) {
                GuiWinGame.logger.error("Couldn't load credits", (Throwable)var9);
            }
        }
    }
    
    private void func_146575_b(final int p_146575_1_, final int p_146575_2_, final float p_146575_3_) {
        final Tessellator var4 = Tessellator.instance;
        this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
        var4.startDrawingQuads();
        var4.setColorRGBA_F(1.0f, 1.0f, 1.0f, 1.0f);
        final int var5 = this.width;
        final float var6 = 0.0f - (this.field_146581_h + p_146575_3_) * 0.5f * this.field_146578_s;
        final float var7 = this.height - (this.field_146581_h + p_146575_3_) * 0.5f * this.field_146578_s;
        final float var8 = 0.015625f;
        float var9 = (this.field_146581_h + p_146575_3_ - 0.0f) * 0.02f;
        final float var10 = (this.field_146579_r + this.height + this.height + 24) / this.field_146578_s;
        final float var11 = (var10 - 20.0f - (this.field_146581_h + p_146575_3_)) * 0.005f;
        if (var11 < var9) {
            var9 = var11;
        }
        if (var9 > 1.0f) {
            var9 = 1.0f;
        }
        var9 *= var9;
        var9 = var9 * 96.0f / 255.0f;
        var4.setColorOpaque_F(var9, var9, var9);
        var4.addVertexWithUV(0.0, this.height, this.zLevel, 0.0, var6 * var8);
        var4.addVertexWithUV(var5, this.height, this.zLevel, var5 * var8, var6 * var8);
        var4.addVertexWithUV(var5, 0.0, this.zLevel, var5 * var8, var7 * var8);
        var4.addVertexWithUV(0.0, 0.0, this.zLevel, 0.0, var7 * var8);
        var4.draw();
    }
    
    @Override
    public void drawScreen(final int p_73863_1_, final int p_73863_2_, final float p_73863_3_) {
        this.func_146575_b(p_73863_1_, p_73863_2_, p_73863_3_);
        final Tessellator var4 = Tessellator.instance;
        final short var5 = 274;
        final int var6 = this.width / 2 - var5 / 2;
        final int var7 = this.height + 50;
        final float var8 = -(this.field_146581_h + p_73863_3_) * this.field_146578_s;
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, var8, 0.0f);
        this.mc.getTextureManager().bindTexture(GuiWinGame.field_146576_f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.drawTexturedModalRect(var6, var7, 0, 0, 155, 44);
        this.drawTexturedModalRect(var6 + 155, var7, 0, 45, 155, 44);
        var4.setColorOpaque_I(16777215);
        int var9 = var7 + 200;
        for (int var10 = 0; var10 < this.field_146582_i.size(); ++var10) {
            if (var10 == this.field_146582_i.size() - 1) {
                final float var11 = var9 + var8 - (this.height / 2 - 6);
                if (var11 < 0.0f) {
                    GL11.glTranslatef(0.0f, -var11, 0.0f);
                }
            }
            if (var9 + var8 + 12.0f + 8.0f > 0.0f && var9 + var8 < this.height) {
                final String var12 = this.field_146582_i.get(var10);
                if (var12.startsWith("[C]")) {
                    this.fontRendererObj.drawStringWithShadow(var12.substring(3), var6 + (var5 - this.fontRendererObj.getStringWidth(var12.substring(3))) / 2, var9, 16777215);
                }
                else {
                    this.fontRendererObj.fontRandom.setSeed(var10 * 4238972211L + this.field_146581_h / 4);
                    this.fontRendererObj.drawStringWithShadow(var12, var6, var9, 16777215);
                }
            }
            var9 += 12;
        }
        GL11.glPopMatrix();
        this.mc.getTextureManager().bindTexture(GuiWinGame.field_146577_g);
        GL11.glEnable(3042);
        GL11.glBlendFunc(0, 769);
        var4.startDrawingQuads();
        var4.setColorRGBA_F(1.0f, 1.0f, 1.0f, 1.0f);
        int var10 = this.width;
        final int var13 = this.height;
        var4.addVertexWithUV(0.0, var13, this.zLevel, 0.0, 1.0);
        var4.addVertexWithUV(var10, var13, this.zLevel, 1.0, 1.0);
        var4.addVertexWithUV(var10, 0.0, this.zLevel, 1.0, 0.0);
        var4.addVertexWithUV(0.0, 0.0, this.zLevel, 0.0, 0.0);
        var4.draw();
        GL11.glDisable(3042);
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
    
    static {
        logger = LogManager.getLogger();
        field_146576_f = new ResourceLocation("textures/gui/title/minecraft.png");
        field_146577_g = new ResourceLocation("textures/misc/vignette.png");
    }
}
