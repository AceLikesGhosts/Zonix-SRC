package us.zonix.client.module.impl;

import us.zonix.client.module.modules.*;
import us.zonix.client.setting.impl.*;
import us.zonix.client.gui.*;
import net.minecraft.util.*;
import net.minecraft.scoreboard.*;
import net.minecraft.client.gui.*;
import java.util.*;

public final class Scoreboard extends AbstractModule
{
    private static final BooleanSetting DRAW_BACKGROUND;
    
    public Scoreboard() {
        super("Scoreboard");
        this.addSetting(new LabelSetting("General Settings"));
        this.addSetting(new BooleanSetting("Show numbers", false));
        this.addSetting(Scoreboard.DRAW_BACKGROUND);
    }
    
    public void render(ScoreObjective objective, final int height, final int width, final FontRenderer fontRenderer) {
        if (this.mc.currentScreen instanceof ModScreen && objective == null) {
            final net.minecraft.scoreboard.Scoreboard scoreboard = new net.minecraft.scoreboard.Scoreboard();
            objective = new ScoreObjective(scoreboard, "Zonix", IScoreObjectiveCriteria.field_96641_b);
            objective.setDisplayName(EnumChatFormatting.RED + EnumChatFormatting.BOLD.toString() + "ZONIX " + EnumChatFormatting.GRAY + EnumChatFormatting.BOLD + "[" + EnumChatFormatting.GREEN + EnumChatFormatting.BOLD + "US" + EnumChatFormatting.GRAY + EnumChatFormatting.BOLD + "]");
            scoreboard.func_96529_a(EnumChatFormatting.RESET + EnumChatFormatting.GRAY.toString() + EnumChatFormatting.STRIKETHROUGH.toString() + "------------------", objective).func_96647_c(4);
            scoreboard.func_96529_a(EnumChatFormatting.RED + "Online" + EnumChatFormatting.DARK_GRAY + ": " + EnumChatFormatting.WHITE + "1337", objective).func_96647_c(3);
            scoreboard.func_96529_a(EnumChatFormatting.RED + "Fighting" + EnumChatFormatting.DARK_GRAY + ": " + EnumChatFormatting.WHITE + "420", objective).func_96647_c(2);
            scoreboard.func_96529_a(EnumChatFormatting.GRAY + EnumChatFormatting.STRIKETHROUGH.toString() + "------------------", objective).func_96647_c(1);
        }
        if (objective == null) {
            return;
        }
        final net.minecraft.scoreboard.Scoreboard var5 = objective.getScoreboard();
        final Collection var6 = var5.func_96534_i(objective);
        if (var6.size() <= 15) {
            int var7 = fontRenderer.getStringWidth(objective.getDisplayName());
            for (final Score var9 : var6) {
                final ScorePlayerTeam var10 = var5.getPlayersTeam(var9.getPlayerName());
                final String prefix = ScorePlayerTeam.formatPlayerName(var10, var9.getPlayerName());
                String suffix = ": " + EnumChatFormatting.RED + var9.getScorePoints();
                if (this.isEnabled() && !this.getBooleanSetting("Show numbers").getValue()) {
                    suffix = "";
                }
                final String var11 = prefix + suffix;
                var7 = Math.max(var7, fontRenderer.getStringWidth(var11));
            }
            final int var12 = var6.size() * fontRenderer.FONT_HEIGHT;
            this.setHeight(var12 + fontRenderer.FONT_HEIGHT + 2);
            this.setWidth(var7 + 5);
            final int var13 = (int)(this.y + var12 + fontRenderer.FONT_HEIGHT + 2.0f);
            final int var14 = (int)(this.x + 2.0f);
            int var15 = 0;
            for (final Object aVar6 : var6) {
                final Score var16 = (Score)aVar6;
                ++var15;
                final ScorePlayerTeam var17 = var5.getPlayersTeam(var16.getPlayerName());
                final String var18 = ScorePlayerTeam.formatPlayerName(var17, var16.getPlayerName());
                String var19 = EnumChatFormatting.RED + "" + var16.getScorePoints();
                if (this.isEnabled() && !this.getBooleanSetting("Show numbers").getValue()) {
                    var19 = "";
                }
                final int var20 = var13 - var15 * fontRenderer.FONT_HEIGHT;
                if (Scoreboard.DRAW_BACKGROUND.getValue()) {
                    Gui.drawRect(var14 - 2, var20, var14 + var7 + 2, var20 + fontRenderer.FONT_HEIGHT, 1342177280);
                }
                fontRenderer.drawString(var18, var14, var20, 553648127);
                fontRenderer.drawString(var19, var14 + var7 - 1 - fontRenderer.getStringWidth(var19), var20, 553648127);
                if (var15 == var6.size()) {
                    final String var21 = objective.getDisplayName();
                    if (Scoreboard.DRAW_BACKGROUND.getValue()) {
                        Gui.drawRect(var14 - 2, var20 - fontRenderer.FONT_HEIGHT - 1, var14 + var7 + 2, var20 - 1, 1610612736);
                        Gui.drawRect(var14 - 2, var20 - 1, var14 + var7 + 2, var20, 1342177280);
                    }
                    fontRenderer.drawString(var21, var14 + var7 / 2 - fontRenderer.getStringWidth(var21) / 2, var20 - fontRenderer.FONT_HEIGHT, 553648127);
                }
            }
        }
    }
    
    static {
        DRAW_BACKGROUND = new BooleanSetting("Draw background", true);
    }
}
