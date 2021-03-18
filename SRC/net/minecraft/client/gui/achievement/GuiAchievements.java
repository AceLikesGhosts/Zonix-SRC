package net.minecraft.client.gui.achievement;

import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.stats.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class GuiAchievements extends GuiScreen implements IProgressMeter
{
    private static final int field_146572_y;
    private static final int field_146571_z;
    private static final int field_146559_A;
    private static final int field_146560_B;
    private static final ResourceLocation field_146561_C;
    protected GuiScreen field_146562_a;
    protected int field_146555_f;
    protected int field_146557_g;
    protected int field_146563_h;
    protected int field_146564_i;
    protected float field_146570_r;
    protected double field_146569_s;
    protected double field_146568_t;
    protected double field_146567_u;
    protected double field_146566_v;
    protected double field_146565_w;
    protected double field_146573_x;
    private int field_146554_D;
    private StatFileWriter field_146556_E;
    private boolean field_146558_F;
    private static final String __OBFID = "CL_00000722";
    
    public GuiAchievements(final GuiScreen p_i45026_1_, final StatFileWriter p_i45026_2_) {
        this.field_146555_f = 256;
        this.field_146557_g = 202;
        this.field_146570_r = 1.0f;
        this.field_146558_F = true;
        this.field_146562_a = p_i45026_1_;
        this.field_146556_E = p_i45026_2_;
        final short var3 = 141;
        final short var4 = 141;
        final double field_146569_s = AchievementList.openInventory.displayColumn * 24 - var3 / 2 - 12;
        this.field_146565_w = field_146569_s;
        this.field_146567_u = field_146569_s;
        this.field_146569_s = field_146569_s;
        final double field_146568_t = AchievementList.openInventory.displayRow * 24 - var4 / 2;
        this.field_146573_x = field_146568_t;
        this.field_146566_v = field_146568_t;
        this.field_146568_t = field_146568_t;
    }
    
    @Override
    public void initGui() {
        this.mc.getNetHandler().addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.REQUEST_STATS));
        this.buttonList.clear();
        this.buttonList.add(new GuiOptionButton(1, this.width / 2 + 24, this.height / 2 + 74, 80, 20, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton p_146284_1_) {
        if (!this.field_146558_F && p_146284_1_.id == 1) {
            this.mc.displayGuiScreen(this.field_146562_a);
        }
    }
    
    @Override
    protected void keyTyped(final char p_73869_1_, final int p_73869_2_) {
        if (p_73869_2_ == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
        }
        else {
            super.keyTyped(p_73869_1_, p_73869_2_);
        }
    }
    
    @Override
    public void drawScreen(final int p_73863_1_, final int p_73863_2_, final float p_73863_3_) {
        if (this.field_146558_F) {
            this.drawDefaultBackground();
            this.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingStats", new Object[0]), this.width / 2, this.height / 2, 16777215);
            this.drawCenteredString(this.fontRendererObj, GuiAchievements.field_146510_b_[(int)(Minecraft.getSystemTime() / 150L % GuiAchievements.field_146510_b_.length)], this.width / 2, this.height / 2 + this.fontRendererObj.FONT_HEIGHT * 2, 16777215);
        }
        else {
            if (Mouse.isButtonDown(0)) {
                final int var4 = (this.width - this.field_146555_f) / 2;
                final int var5 = (this.height - this.field_146557_g) / 2;
                final int var6 = var4 + 8;
                final int var7 = var5 + 17;
                if ((this.field_146554_D == 0 || this.field_146554_D == 1) && p_73863_1_ >= var6 && p_73863_1_ < var6 + 224 && p_73863_2_ >= var7 && p_73863_2_ < var7 + 155) {
                    if (this.field_146554_D == 0) {
                        this.field_146554_D = 1;
                    }
                    else {
                        this.field_146567_u -= (p_73863_1_ - this.field_146563_h) * this.field_146570_r;
                        this.field_146566_v -= (p_73863_2_ - this.field_146564_i) * this.field_146570_r;
                        final double field_146567_u = this.field_146567_u;
                        this.field_146569_s = field_146567_u;
                        this.field_146565_w = field_146567_u;
                        final double field_146566_v = this.field_146566_v;
                        this.field_146568_t = field_146566_v;
                        this.field_146573_x = field_146566_v;
                    }
                    this.field_146563_h = p_73863_1_;
                    this.field_146564_i = p_73863_2_;
                }
            }
            else {
                this.field_146554_D = 0;
            }
            final int var4 = Mouse.getDWheel();
            final float var8 = this.field_146570_r;
            if (var4 < 0) {
                this.field_146570_r += 0.25f;
            }
            else if (var4 > 0) {
                this.field_146570_r -= 0.25f;
            }
            this.field_146570_r = MathHelper.clamp_float(this.field_146570_r, 1.0f, 2.0f);
            if (this.field_146570_r != var8) {
                final float var9 = var8 - this.field_146570_r;
                final float var10 = var8 * this.field_146555_f;
                final float var11 = var8 * this.field_146557_g;
                final float var12 = this.field_146570_r * this.field_146555_f;
                final float var13 = this.field_146570_r * this.field_146557_g;
                this.field_146567_u -= (var12 - var10) * 0.5f;
                this.field_146566_v -= (var13 - var11) * 0.5f;
                final double field_146567_u2 = this.field_146567_u;
                this.field_146569_s = field_146567_u2;
                this.field_146565_w = field_146567_u2;
                final double field_146566_v2 = this.field_146566_v;
                this.field_146568_t = field_146566_v2;
                this.field_146573_x = field_146566_v2;
            }
            if (this.field_146565_w < GuiAchievements.field_146572_y) {
                this.field_146565_w = GuiAchievements.field_146572_y;
            }
            if (this.field_146573_x < GuiAchievements.field_146571_z) {
                this.field_146573_x = GuiAchievements.field_146571_z;
            }
            if (this.field_146565_w >= GuiAchievements.field_146559_A) {
                this.field_146565_w = GuiAchievements.field_146559_A - 1;
            }
            if (this.field_146573_x >= GuiAchievements.field_146560_B) {
                this.field_146573_x = GuiAchievements.field_146560_B - 1;
            }
            this.drawDefaultBackground();
            this.func_146552_b(p_73863_1_, p_73863_2_, p_73863_3_);
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            this.func_146553_h();
            GL11.glEnable(2896);
            GL11.glEnable(2929);
        }
    }
    
    @Override
    public void func_146509_g() {
        if (this.field_146558_F) {
            this.field_146558_F = false;
        }
    }
    
    @Override
    public void updateScreen() {
        if (!this.field_146558_F) {
            this.field_146569_s = this.field_146567_u;
            this.field_146568_t = this.field_146566_v;
            final double var1 = this.field_146565_w - this.field_146567_u;
            final double var2 = this.field_146573_x - this.field_146566_v;
            if (var1 * var1 + var2 * var2 < 4.0) {
                this.field_146567_u += var1;
                this.field_146566_v += var2;
            }
            else {
                this.field_146567_u += var1 * 0.85;
                this.field_146566_v += var2 * 0.85;
            }
        }
    }
    
    protected void func_146553_h() {
        final int var1 = (this.width - this.field_146555_f) / 2;
        final int var2 = (this.height - this.field_146557_g) / 2;
        this.fontRendererObj.drawString(I18n.format("gui.achievements", new Object[0]), var1 + 15, var2 + 5, 4210752);
    }
    
    protected void func_146552_b(final int p_146552_1_, final int p_146552_2_, final float p_146552_3_) {
        int var4 = MathHelper.floor_double(this.field_146569_s + (this.field_146567_u - this.field_146569_s) * p_146552_3_);
        int var5 = MathHelper.floor_double(this.field_146568_t + (this.field_146566_v - this.field_146568_t) * p_146552_3_);
        if (var4 < GuiAchievements.field_146572_y) {
            var4 = GuiAchievements.field_146572_y;
        }
        if (var5 < GuiAchievements.field_146571_z) {
            var5 = GuiAchievements.field_146571_z;
        }
        if (var4 >= GuiAchievements.field_146559_A) {
            var4 = GuiAchievements.field_146559_A - 1;
        }
        if (var5 >= GuiAchievements.field_146560_B) {
            var5 = GuiAchievements.field_146560_B - 1;
        }
        final int var6 = (this.width - this.field_146555_f) / 2;
        final int var7 = (this.height - this.field_146557_g) / 2;
        final int var8 = var6 + 16;
        final int var9 = var7 + 17;
        this.zLevel = 0.0f;
        GL11.glDepthFunc(518);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)var8, (float)var9, -200.0f);
        GL11.glScalef(1.0f / this.field_146570_r, 1.0f / this.field_146570_r, 0.0f);
        GL11.glEnable(3553);
        GL11.glDisable(2896);
        GL11.glEnable(32826);
        GL11.glEnable(2903);
        final int var10 = var4 + 288 >> 4;
        final int var11 = var5 + 288 >> 4;
        final int var12 = (var4 + 288) % 16;
        final int var13 = (var5 + 288) % 16;
        final boolean var14 = true;
        final boolean var15 = true;
        final boolean var16 = true;
        final boolean var17 = true;
        final boolean var18 = true;
        final Random var19 = new Random();
        final float var20 = 16.0f / this.field_146570_r;
        final float var21 = 16.0f / this.field_146570_r;
        for (int var22 = 0; var22 * var20 - var13 < 155.0f; ++var22) {
            final float var23 = 0.6f - (var11 + var22) / 25.0f * 0.3f;
            GL11.glColor4f(var23, var23, var23, 1.0f);
            for (int var24 = 0; var24 * var21 - var12 < 224.0f; ++var24) {
                var19.setSeed(this.mc.getSession().getPlayerID().hashCode() + var10 + var24 + (var11 + var22) * 16);
                final int var25 = var19.nextInt(1 + var11 + var22) + (var11 + var22) / 2;
                IIcon var26 = Blocks.sand.getIcon(0, 0);
                if (var25 <= 37 && var11 + var22 != 35) {
                    if (var25 == 22) {
                        if (var19.nextInt(2) == 0) {
                            var26 = Blocks.diamond_ore.getIcon(0, 0);
                        }
                        else {
                            var26 = Blocks.redstone_ore.getIcon(0, 0);
                        }
                    }
                    else if (var25 == 10) {
                        var26 = Blocks.iron_ore.getIcon(0, 0);
                    }
                    else if (var25 == 8) {
                        var26 = Blocks.coal_ore.getIcon(0, 0);
                    }
                    else if (var25 > 4) {
                        var26 = Blocks.stone.getIcon(0, 0);
                    }
                    else if (var25 > 0) {
                        var26 = Blocks.dirt.getIcon(0, 0);
                    }
                }
                else {
                    var26 = Blocks.bedrock.getIcon(0, 0);
                }
                this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
                this.drawTexturedModelRectFromIcon(var24 * 16 - var12, var22 * 16 - var13, var26, 16, 16);
            }
        }
        GL11.glEnable(2929);
        GL11.glDepthFunc(515);
        this.mc.getTextureManager().bindTexture(GuiAchievements.field_146561_C);
        for (int var22 = 0; var22 < AchievementList.achievementList.size(); ++var22) {
            final Achievement var27 = AchievementList.achievementList.get(var22);
            if (var27.parentAchievement != null) {
                final int var24 = var27.displayColumn * 24 - var4 + 11;
                final int var25 = var27.displayRow * 24 - var5 + 11;
                final int var28 = var27.parentAchievement.displayColumn * 24 - var4 + 11;
                final int var29 = var27.parentAchievement.displayRow * 24 - var5 + 11;
                final boolean var30 = this.field_146556_E.hasAchievementUnlocked(var27);
                final boolean var31 = this.field_146556_E.canUnlockAchievement(var27);
                final int var32 = this.field_146556_E.func_150874_c(var27);
                if (var32 <= 4) {
                    int var33 = -16777216;
                    if (var30) {
                        var33 = -6250336;
                    }
                    else if (var31) {
                        var33 = -16711936;
                    }
                    this.drawHorizontalLine(var24, var28, var25, var33);
                    this.drawVerticalLine(var28, var25, var29, var33);
                    if (var24 > var28) {
                        this.drawTexturedModalRect(var24 - 11 - 7, var25 - 5, 114, 234, 7, 11);
                    }
                    else if (var24 < var28) {
                        this.drawTexturedModalRect(var24 + 11, var25 - 5, 107, 234, 7, 11);
                    }
                    else if (var25 > var29) {
                        this.drawTexturedModalRect(var24 - 5, var25 - 11 - 7, 96, 234, 11, 7);
                    }
                    else if (var25 < var29) {
                        this.drawTexturedModalRect(var24 - 5, var25 + 11, 96, 241, 11, 7);
                    }
                }
            }
        }
        Achievement var34 = null;
        final RenderItem var35 = new RenderItem();
        final float var36 = (p_146552_1_ - var8) * this.field_146570_r;
        final float var37 = (p_146552_2_ - var9) * this.field_146570_r;
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable(2896);
        GL11.glEnable(32826);
        GL11.glEnable(2903);
        for (int var28 = 0; var28 < AchievementList.achievementList.size(); ++var28) {
            final Achievement var38 = AchievementList.achievementList.get(var28);
            final int var39 = var38.displayColumn * 24 - var4;
            final int var40 = var38.displayRow * 24 - var5;
            if (var39 >= -24 && var40 >= -24 && var39 <= 224.0f * this.field_146570_r && var40 <= 155.0f * this.field_146570_r) {
                final int var32 = this.field_146556_E.func_150874_c(var38);
                if (this.field_146556_E.hasAchievementUnlocked(var38)) {
                    final float var41 = 0.75f;
                    GL11.glColor4f(var41, var41, var41, 1.0f);
                }
                else if (this.field_146556_E.canUnlockAchievement(var38)) {
                    final float var41 = 1.0f;
                    GL11.glColor4f(var41, var41, var41, 1.0f);
                }
                else if (var32 < 3) {
                    final float var41 = 0.3f;
                    GL11.glColor4f(var41, var41, var41, 1.0f);
                }
                else if (var32 == 3) {
                    final float var41 = 0.2f;
                    GL11.glColor4f(var41, var41, var41, 1.0f);
                }
                else {
                    if (var32 != 4) {
                        continue;
                    }
                    final float var41 = 0.1f;
                    GL11.glColor4f(var41, var41, var41, 1.0f);
                }
                this.mc.getTextureManager().bindTexture(GuiAchievements.field_146561_C);
                if (var38.getSpecial()) {
                    this.drawTexturedModalRect(var39 - 2, var40 - 2, 26, 202, 26, 26);
                }
                else {
                    this.drawTexturedModalRect(var39 - 2, var40 - 2, 0, 202, 26, 26);
                }
                if (!this.field_146556_E.canUnlockAchievement(var38)) {
                    final float var41 = 0.1f;
                    GL11.glColor4f(var41, var41, var41, 1.0f);
                    var35.renderWithColor = false;
                }
                GL11.glEnable(2896);
                GL11.glEnable(2884);
                var35.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), var38.theItemStack, var39 + 3, var40 + 3);
                GL11.glBlendFunc(770, 771);
                GL11.glDisable(2896);
                if (!this.field_146556_E.canUnlockAchievement(var38)) {
                    var35.renderWithColor = true;
                }
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                if (var36 >= var39 && var36 <= var39 + 22 && var37 >= var40 && var37 <= var40 + 22) {
                    var34 = var38;
                }
            }
        }
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiAchievements.field_146561_C);
        this.drawTexturedModalRect(var6, var7, 0, 0, this.field_146555_f, this.field_146557_g);
        this.zLevel = 0.0f;
        GL11.glDepthFunc(515);
        GL11.glDisable(2929);
        GL11.glEnable(3553);
        super.drawScreen(p_146552_1_, p_146552_2_, p_146552_3_);
        if (var34 != null) {
            String var42 = var34.func_150951_e().getUnformattedText();
            final String var43 = var34.getDescription();
            final int var39 = p_146552_1_ + 12;
            final int var40 = p_146552_2_ - 4;
            final int var32 = this.field_146556_E.func_150874_c(var34);
            if (!this.field_146556_E.canUnlockAchievement(var34)) {
                if (var32 == 3) {
                    var42 = I18n.format("achievement.unknown", new Object[0]);
                    final int var33 = Math.max(this.fontRendererObj.getStringWidth(var42), 120);
                    final String var44 = new ChatComponentTranslation("achievement.requires", new Object[] { var34.parentAchievement.func_150951_e() }).getUnformattedText();
                    final int var45 = this.fontRendererObj.splitStringWidth(var44, var33);
                    this.drawGradientRect(var39 - 3, var40 - 3, var39 + var33 + 3, var40 + var45 + 12 + 3, -1073741824, -1073741824);
                    this.fontRendererObj.drawSplitString(var44, var39, var40 + 12, var33, -9416624);
                }
                else if (var32 < 3) {
                    final int var33 = Math.max(this.fontRendererObj.getStringWidth(var42), 120);
                    final String var44 = new ChatComponentTranslation("achievement.requires", new Object[] { var34.parentAchievement.func_150951_e() }).getUnformattedText();
                    final int var45 = this.fontRendererObj.splitStringWidth(var44, var33);
                    this.drawGradientRect(var39 - 3, var40 - 3, var39 + var33 + 3, var40 + var45 + 12 + 3, -1073741824, -1073741824);
                    this.fontRendererObj.drawSplitString(var44, var39, var40 + 12, var33, -9416624);
                }
                else {
                    var42 = null;
                }
            }
            else {
                final int var33 = Math.max(this.fontRendererObj.getStringWidth(var42), 120);
                int var46 = this.fontRendererObj.splitStringWidth(var43, var33);
                if (this.field_146556_E.hasAchievementUnlocked(var34)) {
                    var46 += 12;
                }
                this.drawGradientRect(var39 - 3, var40 - 3, var39 + var33 + 3, var40 + var46 + 3 + 12, -1073741824, -1073741824);
                this.fontRendererObj.drawSplitString(var43, var39, var40 + 12, var33, -6250336);
                if (this.field_146556_E.hasAchievementUnlocked(var34)) {
                    this.fontRendererObj.drawStringWithShadow(I18n.format("achievement.taken", new Object[0]), var39, var40 + var46 + 4, -7302913);
                }
            }
            if (var42 != null) {
                this.fontRendererObj.drawStringWithShadow(var42, var39, var40, this.field_146556_E.canUnlockAchievement(var34) ? (var34.getSpecial() ? -128 : -1) : (var34.getSpecial() ? -8355776 : -8355712));
            }
        }
        GL11.glEnable(2929);
        GL11.glEnable(2896);
        RenderHelper.disableStandardItemLighting();
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return !this.field_146558_F;
    }
    
    static {
        field_146572_y = AchievementList.minDisplayColumn * 24 - 112;
        field_146571_z = AchievementList.minDisplayRow * 24 - 112;
        field_146559_A = AchievementList.maxDisplayColumn * 24 - 77;
        field_146560_B = AchievementList.maxDisplayRow * 24 - 77;
        field_146561_C = new ResourceLocation("textures/gui/achievement/achievement_background.png");
    }
}
