package net.minecraft.client.gui;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.client.resources.*;
import net.minecraft.world.*;
import us.zonix.client.*;
import us.zonix.client.module.*;
import java.awt.*;
import us.zonix.client.module.impl.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.chunk.*;
import net.minecraft.client.network.*;
import net.minecraft.scoreboard.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.*;
import net.minecraft.entity.boss.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;

public class GuiIngame extends Gui
{
    private static final ResourceLocation vignetteTexPath;
    private static final ResourceLocation widgetsTexPath;
    private static final ResourceLocation pumpkinBlurTexPath;
    private static final RenderItem itemRenderer;
    private final Random rand;
    private final Minecraft mc;
    private final GuiNewChat persistantChatGUI;
    private final GuiStreamIndicator field_152127_m;
    private int updateCounter;
    private String recordPlaying;
    private int recordPlayingUpFor;
    private boolean recordIsPlaying;
    public float prevVignetteBrightness;
    private int remainingHighlightTicks;
    private ItemStack highlightingItemStack;
    private static final String __OBFID = "CL_00000661";
    
    public GuiIngame(final Minecraft p_i46379_1_) {
        this.rand = new Random();
        this.recordPlaying = "";
        this.prevVignetteBrightness = 1.0f;
        this.mc = p_i46379_1_;
        this.persistantChatGUI = new GuiNewChat(p_i46379_1_);
        this.field_152127_m = new GuiStreamIndicator(this.mc);
    }
    
    public void renderGameOverlay(final float p_73830_1_, final boolean p_73830_2_, final int p_73830_3_, final int p_73830_4_) {
        final ScaledResolution var5 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        final int var6 = var5.getScaledWidth();
        final int var7 = var5.getScaledHeight();
        final FontRenderer var8 = this.mc.fontRenderer;
        this.mc.entityRenderer.setupOverlayRendering();
        GL11.glEnable(3042);
        if (Minecraft.isFancyGraphicsEnabled()) {
            this.renderVignette(this.mc.thePlayer.getBrightness(p_73830_1_), var6, var7);
        }
        else {
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        }
        final ItemStack var9 = this.mc.thePlayer.inventory.armorItemInSlot(3);
        if (this.mc.gameSettings.thirdPersonView == 0 && var9 != null && var9.getItem() == Item.getItemFromBlock(Blocks.pumpkin)) {
            this.renderPumpkinBlur(var6, var7);
        }
        if (!this.mc.thePlayer.isPotionActive(Potion.confusion)) {
            final float var10 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * p_73830_1_;
            if (var10 > 0.0f) {
                this.func_130015_b(var10, var6, var7);
            }
        }
        if (!this.mc.playerController.enableEverythingIsScrewedUpMode()) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(GuiIngame.widgetsTexPath);
            final InventoryPlayer var11 = this.mc.thePlayer.inventory;
            this.zLevel = -90.0f;
            this.drawTexturedModalRect(var6 / 2 - 91, var7 - 22, 0, 0, 182, 22);
            this.drawTexturedModalRect(var6 / 2 - 91 - 1 + var11.currentItem * 20, var7 - 22 - 1, 0, 22, 24, 22);
            this.mc.getTextureManager().bindTexture(GuiIngame.icons);
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(775, 769, 1, 0);
            this.drawTexturedModalRect(var6 / 2 - 7, var7 / 2 - 7, 0, 0, 16, 16);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            this.mc.mcProfiler.startSection("bossHealth");
            this.renderBossHealth();
            this.mc.mcProfiler.endSection();
            if (this.mc.playerController.shouldDrawHUD()) {
                this.func_110327_a(var6, var7);
            }
            this.mc.mcProfiler.startSection("actionBar");
            GL11.glEnable(32826);
            RenderHelper.enableGUIStandardItemLighting();
            for (int var12 = 0; var12 < 9; ++var12) {
                final int var13 = var6 / 2 - 90 + var12 * 20 + 2;
                final int var14 = var7 - 16 - 3;
                this.renderInventorySlot(var12, var13, var14, p_73830_1_);
            }
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(32826);
            this.mc.mcProfiler.endSection();
            GL11.glDisable(3042);
        }
        if (this.mc.thePlayer.getSleepTimer() > 0) {
            this.mc.mcProfiler.startSection("sleep");
            GL11.glDisable(2929);
            GL11.glDisable(3008);
            final int var15 = this.mc.thePlayer.getSleepTimer();
            float var16 = var15 / 100.0f;
            if (var16 > 1.0f) {
                var16 = 1.0f - (var15 - 100) / 10.0f;
            }
            final int var13 = (int)(220.0f * var16) << 24 | 0x101020;
            Gui.drawRect(0, 0, var6, var7, var13);
            GL11.glEnable(3008);
            GL11.glEnable(2929);
            this.mc.mcProfiler.endSection();
        }
        final int var15 = 16777215;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        int var12 = var6 / 2 - 91;
        if (this.mc.thePlayer.isRidingHorse()) {
            this.mc.mcProfiler.startSection("jumpBar");
            this.mc.getTextureManager().bindTexture(Gui.icons);
            final float var17 = this.mc.thePlayer.getHorseJumpPower();
            final short var18 = 182;
            final int var19 = (int)(var17 * (var18 + 1));
            final int var20 = var7 - 32 + 3;
            this.drawTexturedModalRect(var12, var20, 0, 84, var18, 5);
            if (var19 > 0) {
                this.drawTexturedModalRect(var12, var20, 0, 89, var19, 5);
            }
            this.mc.mcProfiler.endSection();
        }
        else if (this.mc.playerController.gameIsSurvivalOrAdventure()) {
            this.mc.mcProfiler.startSection("expBar");
            this.mc.getTextureManager().bindTexture(Gui.icons);
            final int var13 = this.mc.thePlayer.xpBarCap();
            if (var13 > 0) {
                final short var18 = 182;
                final int var19 = (int)(this.mc.thePlayer.experience * (var18 + 1));
                final int var20 = var7 - 32 + 3;
                this.drawTexturedModalRect(var12, var20, 0, 64, var18, 5);
                if (var19 > 0) {
                    this.drawTexturedModalRect(var12, var20, 0, 69, var19, 5);
                }
            }
            this.mc.mcProfiler.endSection();
            if (this.mc.thePlayer.experienceLevel > 0) {
                this.mc.mcProfiler.startSection("expLevel");
                final boolean var21 = false;
                final int var19 = var21 ? 16777215 : 8453920;
                final String var22 = "" + this.mc.thePlayer.experienceLevel;
                final int var23 = (var6 - var8.getStringWidth(var22)) / 2;
                final int var24 = var7 - 31 - 4;
                final boolean var25 = false;
                var8.drawString(var22, var23 + 1, var24, 0);
                var8.drawString(var22, var23 - 1, var24, 0);
                var8.drawString(var22, var23, var24 + 1, 0);
                var8.drawString(var22, var23, var24 - 1, 0);
                var8.drawString(var22, var23, var24, var19);
                this.mc.mcProfiler.endSection();
            }
        }
        if (this.mc.gameSettings.heldItemTooltips) {
            this.mc.mcProfiler.startSection("toolHighlight");
            if (this.remainingHighlightTicks > 0 && this.highlightingItemStack != null) {
                final String var26 = this.highlightingItemStack.getDisplayName();
                final int var14 = (var6 - var8.getStringWidth(var26)) / 2;
                int var19 = var7 - 59;
                if (!this.mc.playerController.shouldDrawHUD()) {
                    var19 += 14;
                }
                int var20 = (int)(this.remainingHighlightTicks * 256.0f / 10.0f);
                if (var20 > 255) {
                    var20 = 255;
                }
                if (var20 > 0) {
                    GL11.glPushMatrix();
                    GL11.glEnable(3042);
                    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                    var8.drawStringWithShadow(var26, var14, var19, 16777215 + (var20 << 24));
                    GL11.glDisable(3042);
                    GL11.glPopMatrix();
                }
            }
            this.mc.mcProfiler.endSection();
        }
        if (this.mc.isDemo()) {
            this.mc.mcProfiler.startSection("demo");
            String var26 = "";
            if (this.mc.theWorld.getTotalWorldTime() >= 120500L) {
                var26 = I18n.format("demo.demoExpired", new Object[0]);
            }
            else {
                var26 = I18n.format("demo.remainingTime", StringUtils.ticksToElapsedTime((int)(120500L - this.mc.theWorld.getTotalWorldTime())));
            }
            final int var14 = var8.getStringWidth(var26);
            var8.drawStringWithShadow(var26, var6 - var14 - 10, 5, 16777215);
            this.mc.mcProfiler.endSection();
        }
        if (this.mc.gameSettings.showDebugInfo) {
            this.mc.mcProfiler.startSection("debug");
            GL11.glPushMatrix();
            var8.drawStringWithShadow("Minecraft 1.7.10 (" + this.mc.debug + ")", 2, 2, 16777215);
            var8.drawStringWithShadow(this.mc.debugInfoRenders(), 2, 12, 16777215);
            var8.drawStringWithShadow(this.mc.getEntityDebug(), 2, 22, 16777215);
            var8.drawStringWithShadow(this.mc.debugInfoEntities(), 2, 32, 16777215);
            var8.drawStringWithShadow(this.mc.getWorldProviderName(), 2, 42, 16777215);
            final long var27 = Runtime.getRuntime().maxMemory();
            final long var28 = Runtime.getRuntime().totalMemory();
            final long var29 = Runtime.getRuntime().freeMemory();
            final long var30 = var28 - var29;
            String var31 = "Used memory: " + var30 * 100L / var27 + "% (" + var30 / 1024L / 1024L + "MB) of " + var27 / 1024L / 1024L + "MB";
            final int var32 = 14737632;
            this.drawString(var8, var31, var6 - var8.getStringWidth(var31) - 2, 2, 14737632);
            var31 = "Allocated memory: " + var28 * 100L / var27 + "% (" + var28 / 1024L / 1024L + "MB)";
            this.drawString(var8, var31, var6 - var8.getStringWidth(var31) - 2, 12, 14737632);
            final int var33 = MathHelper.floor_double(this.mc.thePlayer.posX);
            final int var34 = MathHelper.floor_double(this.mc.thePlayer.posY);
            final int var35 = MathHelper.floor_double(this.mc.thePlayer.posZ);
            this.drawString(var8, String.format("x: %.5f (%d) // c: %d (%d)", this.mc.thePlayer.posX, var33, var33 >> 4, var33 & 0xF), 2, 64, 14737632);
            this.drawString(var8, String.format("y: %.3f (feet pos, %.3f eyes pos)", this.mc.thePlayer.boundingBox.minY, this.mc.thePlayer.posY), 2, 72, 14737632);
            this.drawString(var8, String.format("z: %.5f (%d) // c: %d (%d)", this.mc.thePlayer.posZ, var35, var35 >> 4, var35 & 0xF), 2, 80, 14737632);
            final int var36 = MathHelper.floor_double(this.mc.thePlayer.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
            this.drawString(var8, "f: " + var36 + " (" + Direction.directions[var36] + ") / " + MathHelper.wrapAngleTo180_float(this.mc.thePlayer.rotationYaw), 2, 88, 14737632);
            if (this.mc.theWorld != null && this.mc.theWorld.blockExists(var33, var34, var35)) {
                final Chunk var37 = this.mc.theWorld.getChunkFromBlockCoords(var33, var35);
                this.drawString(var8, "lc: " + (var37.getTopFilledSegment() + 15) + " b: " + var37.getBiomeGenForWorldCoords(var33 & 0xF, var35 & 0xF, this.mc.theWorld.getWorldChunkManager()).biomeName + " bl: " + var37.getSavedLightValue(EnumSkyBlock.Block, var33 & 0xF, var34, var35 & 0xF) + " sl: " + var37.getSavedLightValue(EnumSkyBlock.Sky, var33 & 0xF, var34, var35 & 0xF) + " rl: " + var37.getBlockLightValue(var33 & 0xF, var34, var35 & 0xF, 0), 2, 96, 14737632);
            }
            this.drawString(var8, String.format("ws: %.3f, fs: %.3f, g: %b, fl: %d", this.mc.thePlayer.capabilities.getWalkSpeed(), this.mc.thePlayer.capabilities.getFlySpeed(), this.mc.thePlayer.onGround, this.mc.theWorld.getHeightValue(var33, var35)), 2, 104, 14737632);
            if (this.mc.entityRenderer != null && this.mc.entityRenderer.isShaderActive()) {
                this.drawString(var8, String.format("shader: %s", this.mc.entityRenderer.getShaderGroup().getShaderGroupName()), 2, 112, 14737632);
            }
            GL11.glPopMatrix();
            this.mc.mcProfiler.endSection();
        }
        if (!this.mc.gameSettings.showDebugInfo && (this.mc.currentScreen == null || this.mc.currentScreen instanceof GuiChat)) {
            for (final IModule module : Client.getInstance().getModuleManager().getEnabledModules()) {
                module.renderReal();
            }
        }
        if (this.recordPlayingUpFor > 0) {
            this.mc.mcProfiler.startSection("overlayMessage");
            final float var17 = this.recordPlayingUpFor - p_73830_1_;
            int var14 = (int)(var17 * 255.0f / 20.0f);
            if (var14 > 255) {
                var14 = 255;
            }
            if (var14 > 8) {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)(var6 / 2), (float)(var7 - 68), 0.0f);
                GL11.glEnable(3042);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                int var19 = 16777215;
                if (this.recordIsPlaying) {
                    var19 = (Color.HSBtoRGB(var17 / 50.0f, 0.7f, 0.6f) & 0xFFFFFF);
                }
                var8.drawString(this.recordPlaying, -var8.getStringWidth(this.recordPlaying) / 2, -4, var19 + (var14 << 24 & 0xFF000000));
                GL11.glDisable(3042);
                GL11.glPopMatrix();
            }
            this.mc.mcProfiler.endSection();
        }
        ScoreObjective var38 = this.mc.theWorld.getScoreboard().func_96539_a(1);
        final Scoreboard scoreboard = Client.getInstance().getModuleManager().getModule("Scoreboard");
        if (scoreboard.isEnabled() && !(this.mc.currentScreen instanceof GuiIngameMenu)) {
            scoreboard.render(var38, var7, var6, var8);
        }
        GL11.glEnable(3042);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glDisable(3008);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, (float)(var7 - 48), 0.0f);
        this.mc.mcProfiler.startSection("chat");
        if (!(this.mc.currentScreen instanceof GuiIngameMenu)) {
            this.persistantChatGUI.func_146230_a(this.updateCounter);
        }
        this.mc.mcProfiler.endSection();
        GL11.glPopMatrix();
        var38 = this.mc.theWorld.getScoreboard().func_96539_a(0);
        if (this.mc.gameSettings.keyBindPlayerList.getIsKeyPressed() && (!this.mc.isIntegratedServerRunning() || this.mc.thePlayer.sendQueue.playerInfoList.size() > 1 || var38 != null)) {
            this.mc.mcProfiler.startSection("playerList");
            final NetHandlerPlayClient var39 = this.mc.thePlayer.sendQueue;
            final List var40 = var39.playerInfoList;
            int var20;
            int var23;
            int var24;
            for (var20 = (var23 = var39.currentServerMaxPlayers), var24 = 1; var23 > 20; var23 = (var20 + var24 - 1) / var24) {
                ++var24;
            }
            int var41 = 300 / var24;
            if (var41 > 150) {
                var41 = 150;
            }
            final int var42 = (var6 - var24 * var41) / 2;
            final byte var43 = 10;
            Gui.drawRect(var42 - 1, var43 - 1, var42 + var41 * var24, var43 + 9 * var23, Integer.MIN_VALUE);
            for (int var32 = 0; var32 < var20; ++var32) {
                final int var33 = var42 + var32 % var24 * var41;
                final int var34 = var43 + var32 / var24 * 9;
                Gui.drawRect(var33, var34, var33 + var41 - 1, var34 + 8, 553648127);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glEnable(3008);
                if (var32 < var40.size()) {
                    final GuiPlayerInfo var44 = var40.get(var32);
                    final ScorePlayerTeam var45 = this.mc.theWorld.getScoreboard().getPlayersTeam(var44.name);
                    final String var46 = ScorePlayerTeam.formatPlayerName(var45, var44.name);
                    var8.drawStringWithShadow(var46, var33, var34, 16777215);
                    if (var38 != null) {
                        final int var47 = var33 + var8.getStringWidth(var46) + 5;
                        final int var48 = var33 + var41 - 12 - 5;
                        if (var48 - var47 > 5) {
                            final Score var49 = var38.getScoreboard().func_96529_a(var44.name, var38);
                            final String var50 = EnumChatFormatting.YELLOW + "" + var49.getScorePoints();
                            var8.drawStringWithShadow(var50, var48 - var8.getStringWidth(var50), var34, 16777215);
                        }
                    }
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    this.mc.getTextureManager().bindTexture(GuiIngame.icons);
                    final byte var51 = 0;
                    final boolean var52 = false;
                    byte var53;
                    if (var44.responseTime < 0) {
                        var53 = 5;
                    }
                    else if (var44.responseTime < 150) {
                        var53 = 0;
                    }
                    else if (var44.responseTime < 300) {
                        var53 = 1;
                    }
                    else if (var44.responseTime < 600) {
                        var53 = 2;
                    }
                    else if (var44.responseTime < 1000) {
                        var53 = 3;
                    }
                    else {
                        var53 = 4;
                    }
                    this.zLevel += 100.0f;
                    this.drawTexturedModalRect(var33 + var41 - 12, var34, 0 + var51 * 10, 176 + var53 * 8, 10, 8);
                    this.zLevel -= 100.0f;
                }
            }
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(2896);
        GL11.glEnable(3008);
    }
    
    public void func_152126_a(final float p_152126_1_, final float p_152126_2_) {
        this.field_152127_m.func_152437_a((int)(p_152126_1_ - 10.0f), 10);
    }
    
    private void func_96136_a(final ScoreObjective p_96136_1_, final int p_96136_2_, final int p_96136_3_, final FontRenderer p_96136_4_) {
        final net.minecraft.scoreboard.Scoreboard var5 = p_96136_1_.getScoreboard();
        final Collection var6 = var5.func_96534_i(p_96136_1_);
        if (var6.size() <= 15) {
            int var7 = p_96136_4_.getStringWidth(p_96136_1_.getDisplayName());
            for (final Score var9 : var6) {
                final ScorePlayerTeam var10 = var5.getPlayersTeam(var9.getPlayerName());
                final String prefix = ScorePlayerTeam.formatPlayerName(var10, var9.getPlayerName());
                final String suffix = ": " + EnumChatFormatting.RED + var9.getScorePoints();
                final String var11 = prefix + suffix;
                var7 = Math.max(var7, p_96136_4_.getStringWidth(var11));
            }
            final int var12 = var6.size() * p_96136_4_.FONT_HEIGHT;
            final int var13 = p_96136_2_ / 2 + var12 / 3;
            final byte var14 = 3;
            final int var15 = p_96136_3_ - var7 - var14;
            int var16 = 0;
            for (final Score var18 : var6) {
                ++var16;
                final ScorePlayerTeam var19 = var5.getPlayersTeam(var18.getPlayerName());
                final String var20 = ScorePlayerTeam.formatPlayerName(var19, var18.getPlayerName());
                final String var21 = EnumChatFormatting.RED + "" + var18.getScorePoints();
                final int var22 = var13 - var16 * p_96136_4_.FONT_HEIGHT;
                final int var23 = p_96136_3_ - var14 + 2;
                Gui.drawRect(var15 - 2, var22, var23, var22 + p_96136_4_.FONT_HEIGHT, 1342177280);
                p_96136_4_.drawString(var20, var15, var22, 553648127);
                p_96136_4_.drawString(var21, var23 - p_96136_4_.getStringWidth(var21), var22, 553648127);
                if (var16 == var6.size()) {
                    final String var24 = p_96136_1_.getDisplayName();
                    Gui.drawRect(var15 - 2, var22 - p_96136_4_.FONT_HEIGHT - 1, var23, var22 - 1, 1610612736);
                    Gui.drawRect(var15 - 2, var22 - 1, var23, var22, 1342177280);
                    p_96136_4_.drawString(var24, var15 + var7 / 2 - p_96136_4_.getStringWidth(var24) / 2, var22 - p_96136_4_.FONT_HEIGHT, 553648127);
                }
            }
        }
    }
    
    private void func_110327_a(final int p_110327_1_, final int p_110327_2_) {
        boolean var3 = this.mc.thePlayer.hurtResistantTime / 3 % 2 == 1;
        if (this.mc.thePlayer.hurtResistantTime < 10) {
            var3 = false;
        }
        final int var4 = MathHelper.ceiling_float_int(this.mc.thePlayer.getHealth());
        final int var5 = MathHelper.ceiling_float_int(this.mc.thePlayer.prevHealth);
        this.rand.setSeed(this.updateCounter * 312871);
        final boolean var6 = false;
        final FoodStats var7 = this.mc.thePlayer.getFoodStats();
        final int var8 = var7.getFoodLevel();
        final int var9 = var7.getPrevFoodLevel();
        final IAttributeInstance var10 = this.mc.thePlayer.getEntityAttribute(SharedMonsterAttributes.maxHealth);
        final int var11 = p_110327_1_ / 2 - 91;
        final int var12 = p_110327_1_ / 2 + 91;
        final int var13 = p_110327_2_ - 39;
        final float var14 = (float)var10.getAttributeValue();
        final float var15 = this.mc.thePlayer.getAbsorptionAmount();
        final int var16 = MathHelper.ceiling_float_int((var14 + var15) / 2.0f / 10.0f);
        final int var17 = Math.max(10 - (var16 - 2), 3);
        final int var18 = var13 - (var16 - 1) * var17 - 10;
        float var19 = var15;
        final int var20 = this.mc.thePlayer.getTotalArmorValue();
        int var21 = -1;
        if (this.mc.thePlayer.isPotionActive(Potion.regeneration)) {
            var21 = this.updateCounter % MathHelper.ceiling_float_int(var14 + 5.0f);
        }
        this.mc.mcProfiler.startSection("armor");
        for (int var22 = 0; var22 < 10; ++var22) {
            if (var20 > 0) {
                final int var23 = var11 + var22 * 8;
                if (var22 * 2 + 1 < var20) {
                    this.drawTexturedModalRect(var23, var18, 34, 9, 9, 9);
                }
                if (var22 * 2 + 1 == var20) {
                    this.drawTexturedModalRect(var23, var18, 25, 9, 9, 9);
                }
                if (var22 * 2 + 1 > var20) {
                    this.drawTexturedModalRect(var23, var18, 16, 9, 9, 9);
                }
            }
        }
        this.mc.mcProfiler.endStartSection("health");
        for (int var22 = MathHelper.ceiling_float_int((var14 + var15) / 2.0f) - 1; var22 >= 0; --var22) {
            int var23 = 16;
            if (this.mc.thePlayer.isPotionActive(Potion.poison)) {
                var23 += 36;
            }
            else if (this.mc.thePlayer.isPotionActive(Potion.wither)) {
                var23 += 72;
            }
            byte var24 = 0;
            if (var3) {
                var24 = 1;
            }
            final int var25 = MathHelper.ceiling_float_int((var22 + 1) / 10.0f) - 1;
            final int var26 = var11 + var22 % 10 * 8;
            int var27 = var13 - var25 * var17;
            if (var4 <= 4) {
                var27 += this.rand.nextInt(2);
            }
            if (var22 == var21) {
                var27 -= 2;
            }
            byte var28 = 0;
            if (this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
                var28 = 5;
            }
            this.drawTexturedModalRect(var26, var27, 16 + var24 * 9, 9 * var28, 9, 9);
            if (var3) {
                if (var22 * 2 + 1 < var5) {
                    this.drawTexturedModalRect(var26, var27, var23 + 54, 9 * var28, 9, 9);
                }
                if (var22 * 2 + 1 == var5) {
                    this.drawTexturedModalRect(var26, var27, var23 + 63, 9 * var28, 9, 9);
                }
            }
            if (var19 > 0.0f) {
                if (var19 == var15 && var15 % 2.0f == 1.0f) {
                    this.drawTexturedModalRect(var26, var27, var23 + 153, 9 * var28, 9, 9);
                }
                else {
                    this.drawTexturedModalRect(var26, var27, var23 + 144, 9 * var28, 9, 9);
                }
                var19 -= 2.0f;
            }
            else {
                if (var22 * 2 + 1 < var4) {
                    this.drawTexturedModalRect(var26, var27, var23 + 36, 9 * var28, 9, 9);
                }
                if (var22 * 2 + 1 == var4) {
                    this.drawTexturedModalRect(var26, var27, var23 + 45, 9 * var28, 9, 9);
                }
            }
        }
        final Entity var29 = this.mc.thePlayer.ridingEntity;
        if (var29 == null) {
            this.mc.mcProfiler.endStartSection("food");
            for (int var23 = 0; var23 < 10; ++var23) {
                int var30 = var13;
                int var25 = 16;
                byte var31 = 0;
                if (this.mc.thePlayer.isPotionActive(Potion.hunger)) {
                    var25 += 36;
                    var31 = 13;
                }
                if (this.mc.thePlayer.getFoodStats().getSaturationLevel() <= 0.0f && this.updateCounter % (var8 * 3 + 1) == 0) {
                    var30 = var13 + (this.rand.nextInt(3) - 1);
                }
                if (var6) {
                    var31 = 1;
                }
                final int var27 = var12 - var23 * 8 - 9;
                this.drawTexturedModalRect(var27, var30, 16 + var31 * 9, 27, 9, 9);
                if (var6) {
                    if (var23 * 2 + 1 < var9) {
                        this.drawTexturedModalRect(var27, var30, var25 + 54, 27, 9, 9);
                    }
                    if (var23 * 2 + 1 == var9) {
                        this.drawTexturedModalRect(var27, var30, var25 + 63, 27, 9, 9);
                    }
                }
                if (var23 * 2 + 1 < var8) {
                    this.drawTexturedModalRect(var27, var30, var25 + 36, 27, 9, 9);
                }
                if (var23 * 2 + 1 == var8) {
                    this.drawTexturedModalRect(var27, var30, var25 + 45, 27, 9, 9);
                }
            }
        }
        else if (var29 instanceof EntityLivingBase) {
            this.mc.mcProfiler.endStartSection("mountHealth");
            final EntityLivingBase var32 = (EntityLivingBase)var29;
            final int var30 = (int)Math.ceil(var32.getHealth());
            final float var33 = var32.getMaxHealth();
            int var26 = (int)(var33 + 0.5f) / 2;
            if (var26 > 30) {
                var26 = 30;
            }
            int var27 = var13;
            int var34 = 0;
            while (var26 > 0) {
                final int var35 = Math.min(var26, 10);
                var26 -= var35;
                for (int var36 = 0; var36 < var35; ++var36) {
                    final byte var37 = 52;
                    byte var38 = 0;
                    if (var6) {
                        var38 = 1;
                    }
                    final int var39 = var12 - var36 * 8 - 9;
                    this.drawTexturedModalRect(var39, var27, var37 + var38 * 9, 9, 9, 9);
                    if (var36 * 2 + 1 + var34 < var30) {
                        this.drawTexturedModalRect(var39, var27, var37 + 36, 9, 9, 9);
                    }
                    if (var36 * 2 + 1 + var34 == var30) {
                        this.drawTexturedModalRect(var39, var27, var37 + 45, 9, 9, 9);
                    }
                }
                var27 -= 10;
                var34 += 20;
            }
        }
        this.mc.mcProfiler.endStartSection("air");
        if (this.mc.thePlayer.isInsideOfMaterial(Material.water)) {
            final int var23 = this.mc.thePlayer.getAir();
            for (int var30 = MathHelper.ceiling_double_int((var23 - 2) * 10.0 / 300.0), var25 = MathHelper.ceiling_double_int(var23 * 10.0 / 300.0) - var30, var26 = 0; var26 < var30 + var25; ++var26) {
                if (var26 < var30) {
                    this.drawTexturedModalRect(var12 - var26 * 8 - 9, var18, 16, 18, 9, 9);
                }
                else {
                    this.drawTexturedModalRect(var12 - var26 * 8 - 9, var18, 25, 18, 9, 9);
                }
            }
        }
        this.mc.mcProfiler.endSection();
    }
    
    private void renderBossHealth() {
        if (BossStatus.bossName != null && BossStatus.statusBarTime > 0) {
            --BossStatus.statusBarTime;
            final FontRenderer var1 = this.mc.fontRenderer;
            final ScaledResolution var2 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
            final int var3 = var2.getScaledWidth();
            final short var4 = 182;
            final int var5 = var3 / 2 - var4 / 2;
            final int var6 = (int)(BossStatus.healthScale * (var4 + 1));
            final byte var7 = 12;
            this.drawTexturedModalRect(var5, var7, 0, 74, var4, 5);
            this.drawTexturedModalRect(var5, var7, 0, 74, var4, 5);
            if (var6 > 0) {
                this.drawTexturedModalRect(var5, var7, 0, 79, var6, 5);
            }
            final String var8 = BossStatus.bossName;
            var1.drawStringWithShadow(var8, var3 / 2 - var1.getStringWidth(var8) / 2, var7 - 10, 16777215);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(GuiIngame.icons);
        }
    }
    
    private void renderPumpkinBlur(final int p_73836_1_, final int p_73836_2_) {
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3008);
        this.mc.getTextureManager().bindTexture(GuiIngame.pumpkinBlurTexPath);
        final Tessellator var3 = Tessellator.instance;
        var3.startDrawingQuads();
        var3.addVertexWithUV(0.0, p_73836_2_, -90.0, 0.0, 1.0);
        var3.addVertexWithUV(p_73836_1_, p_73836_2_, -90.0, 1.0, 1.0);
        var3.addVertexWithUV(p_73836_1_, 0.0, -90.0, 1.0, 0.0);
        var3.addVertexWithUV(0.0, 0.0, -90.0, 0.0, 0.0);
        var3.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glEnable(3008);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private void renderVignette(float p_73829_1_, final int p_73829_2_, final int p_73829_3_) {
        p_73829_1_ = 1.0f - p_73829_1_;
        if (p_73829_1_ < 0.0f) {
            p_73829_1_ = 0.0f;
        }
        if (p_73829_1_ > 1.0f) {
            p_73829_1_ = 1.0f;
        }
        this.prevVignetteBrightness += (float)((p_73829_1_ - this.prevVignetteBrightness) * 0.01);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(0, 769, 1, 0);
        GL11.glColor4f(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiIngame.vignetteTexPath);
        final Tessellator var4 = Tessellator.instance;
        var4.startDrawingQuads();
        var4.addVertexWithUV(0.0, p_73829_3_, -90.0, 0.0, 1.0);
        var4.addVertexWithUV(p_73829_2_, p_73829_3_, -90.0, 1.0, 1.0);
        var4.addVertexWithUV(p_73829_2_, 0.0, -90.0, 1.0, 0.0);
        var4.addVertexWithUV(0.0, 0.0, -90.0, 0.0, 0.0);
        var4.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
    }
    
    private void func_130015_b(float p_130015_1_, final int p_130015_2_, final int p_130015_3_) {
        if (p_130015_1_ < 1.0f) {
            p_130015_1_ *= p_130015_1_;
            p_130015_1_ *= p_130015_1_;
            p_130015_1_ = p_130015_1_ * 0.8f + 0.2f;
        }
        GL11.glDisable(3008);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, p_130015_1_);
        final IIcon var4 = Blocks.portal.getBlockTextureFromSide(1);
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        final float var5 = var4.getMinU();
        final float var6 = var4.getMinV();
        final float var7 = var4.getMaxU();
        final float var8 = var4.getMaxV();
        final Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        var9.addVertexWithUV(0.0, p_130015_3_, -90.0, var5, var8);
        var9.addVertexWithUV(p_130015_2_, p_130015_3_, -90.0, var7, var8);
        var9.addVertexWithUV(p_130015_2_, 0.0, -90.0, var7, var6);
        var9.addVertexWithUV(0.0, 0.0, -90.0, var5, var6);
        var9.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glEnable(3008);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private void renderInventorySlot(final int p_73832_1_, final int p_73832_2_, final int p_73832_3_, final float p_73832_4_) {
        final ItemStack var5 = this.mc.thePlayer.inventory.mainInventory[p_73832_1_];
        if (var5 != null) {
            final float var6 = var5.animationsToGo - p_73832_4_;
            if (var6 > 0.0f) {
                GL11.glPushMatrix();
                final float var7 = 1.0f + var6 / 5.0f;
                GL11.glTranslatef((float)(p_73832_2_ + 8), (float)(p_73832_3_ + 12), 0.0f);
                GL11.glScalef(1.0f / var7, (var7 + 1.0f) / 2.0f, 1.0f);
                GL11.glTranslatef((float)(-(p_73832_2_ + 8)), (float)(-(p_73832_3_ + 12)), 0.0f);
            }
            GuiIngame.itemRenderer.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), var5, p_73832_2_, p_73832_3_);
            if (var6 > 0.0f) {
                GL11.glPopMatrix();
            }
            GuiIngame.itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), var5, p_73832_2_, p_73832_3_);
        }
    }
    
    public void updateTick() {
        if (this.recordPlayingUpFor > 0) {
            --this.recordPlayingUpFor;
        }
        ++this.updateCounter;
        this.field_152127_m.func_152439_a();
        if (this.mc.thePlayer != null) {
            final ItemStack var1 = this.mc.thePlayer.inventory.getCurrentItem();
            if (var1 == null) {
                this.remainingHighlightTicks = 0;
            }
            else if (this.highlightingItemStack != null && var1.getItem() == this.highlightingItemStack.getItem() && ItemStack.areItemStackTagsEqual(var1, this.highlightingItemStack) && (var1.isItemStackDamageable() || var1.getItemDamage() == this.highlightingItemStack.getItemDamage())) {
                if (this.remainingHighlightTicks > 0) {
                    --this.remainingHighlightTicks;
                }
            }
            else {
                this.remainingHighlightTicks = 40;
            }
            this.highlightingItemStack = var1;
        }
    }
    
    public void setRecordPlayingMessage(final String p_73833_1_) {
        this.func_110326_a(I18n.format("record.nowPlaying", p_73833_1_), true);
    }
    
    public void func_110326_a(final String p_110326_1_, final boolean p_110326_2_) {
        this.recordPlaying = p_110326_1_;
        this.recordPlayingUpFor = 60;
        this.recordIsPlaying = p_110326_2_;
    }
    
    public GuiNewChat getChatGUI() {
        return this.persistantChatGUI;
    }
    
    public int getUpdateCounter() {
        return this.updateCounter;
    }
    
    static {
        vignetteTexPath = new ResourceLocation("textures/misc/vignette.png");
        widgetsTexPath = new ResourceLocation("textures/gui/widgets.png");
        pumpkinBlurTexPath = new ResourceLocation("textures/misc/pumpkinblur.png");
        itemRenderer = new RenderItem();
    }
}
