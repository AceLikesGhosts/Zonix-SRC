package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import us.zonix.client.module.impl.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class GuiNewChat extends Gui
{
    private static final Logger logger;
    private final Minecraft field_146247_f;
    private final List field_146248_g;
    private final List field_146252_h;
    private final List field_146253_i;
    private int field_146250_j;
    private boolean field_146251_k;
    private static final String __OBFID = "CL_00000669";
    
    public GuiNewChat(final Minecraft p_i1022_1_) {
        this.field_146248_g = new ArrayList();
        this.field_146252_h = new ArrayList();
        this.field_146253_i = new ArrayList();
        this.field_146247_f = p_i1022_1_;
    }
    
    public void func_146230_a(final int p_146230_1_) {
        if (this.field_146247_f.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
            final int var2 = this.func_146232_i();
            boolean var3 = false;
            int var4 = 0;
            final int var5 = this.field_146253_i.size();
            final float var6 = this.field_146247_f.gameSettings.chatOpacity * 0.9f + 0.1f;
            if (var5 > 0) {
                if (this.func_146241_e()) {
                    var3 = true;
                }
                final float var7 = this.func_146244_h();
                final int var8 = MathHelper.ceiling_float_int(this.func_146228_f() / var7);
                GL11.glPushMatrix();
                GL11.glTranslatef(2.0f, 20.0f, 0.0f);
                GL11.glScalef(var7, var7, 1.0f);
                for (int var9 = 0; var9 + this.field_146250_j < this.field_146253_i.size() && var9 < var2; ++var9) {
                    final ChatLine var10 = this.field_146253_i.get(var9 + this.field_146250_j);
                    if (var10 != null) {
                        final int var11 = p_146230_1_ - var10.getUpdatedCounter();
                        if (var11 < 200 || var3) {
                            double var12 = var11 / 200.0;
                            var12 = 1.0 - var12;
                            var12 *= 10.0;
                            if (var12 < 0.0) {
                                var12 = 0.0;
                            }
                            if (var12 > 1.0) {
                                var12 = 1.0;
                            }
                            var12 *= var12;
                            int var13 = (int)(255.0 * var12);
                            if (var3) {
                                var13 = 255;
                            }
                            var13 *= (int)var6;
                            ++var4;
                            if (var13 > 3) {
                                final byte var14 = 0;
                                final int var15 = -var9 * 9;
                                if (!FPSBoost.FAST_CHAT.getValue()) {
                                    Gui.drawRect(var14, var15 - 9, var14 + var8 + 4, var15, var13 / 2 << 24);
                                }
                                final String var16 = var10.func_151461_a().getFormattedText();
                                if (!FPSBoost.CHAT_SHADOW.getValue()) {
                                    this.field_146247_f.fontRenderer.drawString(var16, var14, var15 - 8, 16777215 + (var13 << 24));
                                }
                                else {
                                    this.field_146247_f.fontRenderer.drawStringWithShadow(var16, var14, var15 - 8, 16777215 + (var13 << 24));
                                }
                                GL11.glDisable(3008);
                            }
                        }
                    }
                }
                if (var3) {
                    final int var9 = this.field_146247_f.fontRenderer.FONT_HEIGHT;
                    GL11.glTranslatef(-3.0f, 0.0f, 0.0f);
                    final int var17 = var5 * var9 + var5;
                    final int var11 = var4 * var9 + var4;
                    final int var18 = this.field_146250_j * var11 / var5;
                    final int var19 = var11 * var11 / var17;
                    if (!FPSBoost.FAST_CHAT.getValue() && var17 != var11) {
                        final int var13 = (var18 > 0) ? 170 : 96;
                        final int var20 = this.field_146251_k ? 13382451 : 3355562;
                        Gui.drawRect(0, -var18, 2, -var18 - var19, var20 + (var13 << 24));
                        Gui.drawRect(2, -var18, 1, -var18 - var19, 13421772 + (var13 << 24));
                    }
                }
                GL11.glPopMatrix();
            }
        }
    }
    
    public void func_146231_a() {
        this.field_146253_i.clear();
        this.field_146252_h.clear();
        this.field_146248_g.clear();
    }
    
    public void func_146227_a(final IChatComponent p_146227_1_) {
        this.func_146234_a(p_146227_1_, 0);
    }
    
    public void func_146234_a(final IChatComponent p_146234_1_, final int p_146234_2_) {
        this.func_146237_a(p_146234_1_, p_146234_2_, this.field_146247_f.ingameGUI.getUpdateCounter(), false);
        GuiNewChat.logger.info("[CHAT] " + p_146234_1_.getUnformattedText());
    }
    
    private String func_146235_b(final String p_146235_1_) {
        return Minecraft.getMinecraft().gameSettings.chatColours ? p_146235_1_ : EnumChatFormatting.getTextWithoutFormattingCodes(p_146235_1_);
    }
    
    private void func_146237_a(final IChatComponent p_146237_1_, final int p_146237_2_, final int p_146237_3_, final boolean p_146237_4_) {
        if (p_146237_2_ != 0) {
            this.func_146242_c(p_146237_2_);
        }
        final int var5 = MathHelper.floor_float(this.func_146228_f() / this.func_146244_h());
        int var6 = 0;
        ChatComponentText var7 = new ChatComponentText("");
        final ArrayList var8 = Lists.newArrayList();
        final ArrayList var9 = Lists.newArrayList((Iterable)p_146237_1_);
        for (int var10 = 0; var10 < var9.size(); ++var10) {
            final IChatComponent var11 = var9.get(var10);
            final String var12 = this.func_146235_b(var11.getChatStyle().getFormattingCode() + var11.getUnformattedTextForChat());
            int var13 = this.field_146247_f.fontRenderer.getStringWidth(var12);
            ChatComponentText var14 = new ChatComponentText(var12);
            var14.setChatStyle(var11.getChatStyle().createShallowCopy());
            boolean var15 = false;
            if (var6 + var13 > var5) {
                String var16 = this.field_146247_f.fontRenderer.trimStringToWidth(var12, var5 - var6, false);
                String var17 = (var16.length() < var12.length()) ? var12.substring(var16.length()) : null;
                if (var17 != null && var17.length() > 0) {
                    final int var18 = var16.lastIndexOf(" ");
                    if (var18 >= 0 && this.field_146247_f.fontRenderer.getStringWidth(var12.substring(0, var18)) > 0) {
                        var16 = var12.substring(0, var18);
                        var17 = var12.substring(var18);
                    }
                    final ChatComponentText var19 = new ChatComponentText(var17);
                    var19.setChatStyle(var11.getChatStyle().createShallowCopy());
                    var9.add(var10 + 1, var19);
                }
                var13 = this.field_146247_f.fontRenderer.getStringWidth(var16);
                var14 = new ChatComponentText(var16);
                var14.setChatStyle(var11.getChatStyle().createShallowCopy());
                var15 = true;
            }
            if (var6 + var13 <= var5) {
                var6 += var13;
                var7.appendSibling(var14);
            }
            else {
                var15 = true;
            }
            if (var15) {
                var8.add(var7);
                var6 = 0;
                var7 = new ChatComponentText("");
            }
        }
        var8.add(var7);
        final boolean var20 = this.func_146241_e();
        for (final IChatComponent var22 : var8) {
            if (var20 && this.field_146250_j > 0) {
                this.field_146251_k = true;
                this.func_146229_b(1);
            }
            this.field_146253_i.add(0, new ChatLine(p_146237_3_, var22, p_146237_2_));
        }
        while (this.field_146253_i.size() > 100) {
            this.field_146253_i.remove(this.field_146253_i.size() - 1);
        }
        if (!p_146237_4_) {
            this.field_146252_h.add(0, new ChatLine(p_146237_3_, p_146237_1_, p_146237_2_));
            while (this.field_146252_h.size() > 100) {
                this.field_146252_h.remove(this.field_146252_h.size() - 1);
            }
        }
    }
    
    public void func_146245_b() {
        this.field_146253_i.clear();
        this.resetScroll();
        for (int var1 = this.field_146252_h.size() - 1; var1 >= 0; --var1) {
            final ChatLine var2 = this.field_146252_h.get(var1);
            this.func_146237_a(var2.func_151461_a(), var2.getChatLineID(), var2.getUpdatedCounter(), true);
        }
    }
    
    public List func_146238_c() {
        return this.field_146248_g;
    }
    
    public void func_146239_a(final String p_146239_1_) {
        if (this.field_146248_g.isEmpty() || !this.field_146248_g.get(this.field_146248_g.size() - 1).equals(p_146239_1_)) {
            this.field_146248_g.add(p_146239_1_);
        }
    }
    
    public void resetScroll() {
        this.field_146250_j = 0;
        this.field_146251_k = false;
    }
    
    public void func_146229_b(final int p_146229_1_) {
        this.field_146250_j += p_146229_1_;
        final int var2 = this.field_146253_i.size();
        if (this.field_146250_j > var2 - this.func_146232_i()) {
            this.field_146250_j = var2 - this.func_146232_i();
        }
        if (this.field_146250_j <= 0) {
            this.field_146250_j = 0;
            this.field_146251_k = false;
        }
    }
    
    public IChatComponent func_146236_a(final int p_146236_1_, final int p_146236_2_) {
        if (!this.func_146241_e()) {
            return null;
        }
        final ScaledResolution var3 = new ScaledResolution(this.field_146247_f, this.field_146247_f.displayWidth, this.field_146247_f.displayHeight);
        final int var4 = var3.getScaleFactor();
        final float var5 = this.func_146244_h();
        int var6 = p_146236_1_ / var4 - 3;
        int var7 = p_146236_2_ / var4 - 27;
        var6 = MathHelper.floor_float(var6 / var5);
        var7 = MathHelper.floor_float(var7 / var5);
        if (var6 < 0 || var7 < 0) {
            return null;
        }
        final int var8 = Math.min(this.func_146232_i(), this.field_146253_i.size());
        if (var6 <= MathHelper.floor_float(this.func_146228_f() / this.func_146244_h()) && var7 < this.field_146247_f.fontRenderer.FONT_HEIGHT * var8 + var8) {
            final int var9 = var7 / this.field_146247_f.fontRenderer.FONT_HEIGHT + this.field_146250_j;
            if (var9 >= 0 && var9 < this.field_146253_i.size()) {
                final ChatLine var10 = this.field_146253_i.get(var9);
                int var11 = 0;
                for (final IChatComponent var13 : var10.func_151461_a()) {
                    if (var13 instanceof ChatComponentText) {
                        var11 += this.field_146247_f.fontRenderer.getStringWidth(this.func_146235_b(((ChatComponentText)var13).getChatComponentText_TextValue()));
                        if (var11 > var6) {
                            return var13;
                        }
                        continue;
                    }
                }
            }
            return null;
        }
        return null;
    }
    
    public boolean func_146241_e() {
        return this.field_146247_f.currentScreen instanceof GuiChat;
    }
    
    public void func_146242_c(final int p_146242_1_) {
        Iterator var2 = this.field_146253_i.iterator();
        while (var2.hasNext()) {
            final ChatLine var3 = var2.next();
            if (var3.getChatLineID() == p_146242_1_) {
                var2.remove();
            }
        }
        var2 = this.field_146252_h.iterator();
        while (var2.hasNext()) {
            final ChatLine var3 = var2.next();
            if (var3.getChatLineID() == p_146242_1_) {
                var2.remove();
                break;
            }
        }
    }
    
    public int func_146228_f() {
        return func_146233_a(this.field_146247_f.gameSettings.chatWidth);
    }
    
    public int func_146246_g() {
        return func_146243_b(this.func_146241_e() ? this.field_146247_f.gameSettings.chatHeightFocused : this.field_146247_f.gameSettings.chatHeightUnfocused);
    }
    
    public float func_146244_h() {
        return this.field_146247_f.gameSettings.chatScale;
    }
    
    public static int func_146233_a(final float p_146233_0_) {
        final short var1 = 320;
        final byte var2 = 40;
        return MathHelper.floor_float(p_146233_0_ * (var1 - var2) + var2);
    }
    
    public static int func_146243_b(final float p_146243_0_) {
        final short var1 = 180;
        final byte var2 = 20;
        return MathHelper.floor_float(p_146243_0_ * (var1 - var2) + var2);
    }
    
    public int func_146232_i() {
        return this.func_146246_g() / 9;
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
