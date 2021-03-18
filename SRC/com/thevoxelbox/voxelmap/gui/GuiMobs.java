package com.thevoxelbox.voxelmap.gui;

import com.thevoxelbox.voxelmap.gui.overridden.*;
import com.thevoxelbox.voxelmap.*;
import net.minecraft.client.gui.*;
import com.thevoxelbox.voxelmap.util.*;
import java.util.*;

public class GuiMobs extends GuiScreenMinimap
{
    protected final RadarSettingsManager options;
    private final GuiScreen parentScreen;
    protected String screenTitle;
    protected String selectedMobName;
    private GuiSlotMobs mobsList;
    private GuiButton buttonEnable;
    private GuiButton buttonDisable;
    private String tooltip;
    
    public GuiMobs(final GuiScreen parentScreen, final RadarSettingsManager options) {
        this.screenTitle = "Select Mobs";
        this.selectedMobName = null;
        this.tooltip = null;
        this.parentScreen = parentScreen;
        this.options = options;
    }
    
    static String setTooltip(final GuiMobs par0GuiWaypoints, final String par1Str) {
        return par0GuiWaypoints.tooltip = par1Str;
    }
    
    @Override
    public void initGui() {
        final int var2 = 0;
        this.screenTitle = I18nUtils.getString("options.minimap.mobs.title");
        (this.mobsList = new GuiSlotMobs(this)).func_148134_d(7, 8);
        this.getButtonList().add(this.buttonEnable = new GuiButton(-1, this.getWidth() / 2 - 154, this.getHeight() - 28, 100, 20, I18nUtils.getString("options.minimap.mobs.enable")));
        this.getButtonList().add(this.buttonDisable = new GuiButton(-2, this.getWidth() / 2 - 50, this.getHeight() - 28, 100, 20, I18nUtils.getString("options.minimap.mobs.disable")));
        this.getButtonList().add(new GuiButton(65336, this.getWidth() / 2 + 4 + 50, this.getHeight() - 28, 100, 20, I18nUtils.getString("gui.done")));
        final boolean isSomethingSelected = this.selectedMobName != null;
        this.buttonEnable.enabled = isSomethingSelected;
        this.buttonDisable.enabled = isSomethingSelected;
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id == -1) {
                this.setMobEnabled(this.selectedMobName, true);
            }
            if (par1GuiButton.id == -2) {
                this.setMobEnabled(this.selectedMobName, false);
            }
            if (par1GuiButton.id == 65336) {
                this.getMinecraft().displayGuiScreen(this.parentScreen);
            }
        }
    }
    
    protected void setSelectedMob(final String mob) {
        this.selectedMobName = mob;
    }
    
    private boolean isMobEnabled(final String selectedMobName2) {
        final EnumMobs mob = EnumMobs.getMobByName(this.selectedMobName);
        if (mob != null) {
            return mob.enabled;
        }
        final CustomMob customMob = CustomMobsManager.getCustomMobByName(this.selectedMobName);
        return customMob != null && customMob.enabled;
    }
    
    private void setMobEnabled(final String selectedMobName, final boolean enabled) {
        for (final EnumMobs mob : EnumMobs.values()) {
            if (mob.name.equals(selectedMobName)) {
                mob.enabled = enabled;
            }
        }
        for (final CustomMob mob2 : CustomMobsManager.mobs) {
            if (mob2.name.equals(selectedMobName)) {
                mob2.enabled = enabled;
            }
        }
    }
    
    protected void toggleMobVisibility() {
        final EnumMobs mob = EnumMobs.getMobByName(this.selectedMobName);
        if (mob != null) {
            this.setMobEnabled(this.selectedMobName, !mob.enabled);
        }
        else {
            final CustomMob customMob = CustomMobsManager.getCustomMobByName(this.selectedMobName);
            if (customMob != null) {
                this.setMobEnabled(this.selectedMobName, !customMob.enabled);
            }
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        super.drawMap();
        this.tooltip = null;
        this.mobsList.func_148128_a(par1, par2, par3);
        this.drawCenteredString(this.getFontRenderer(), this.screenTitle, this.getWidth() / 2, 20, 16777215);
        final boolean isSomethingSelected = this.selectedMobName != null;
        this.buttonEnable.enabled = (isSomethingSelected && !this.isMobEnabled(this.selectedMobName));
        this.buttonDisable.enabled = (isSomethingSelected && this.isMobEnabled(this.selectedMobName));
        super.drawScreen(par1, par2, par3);
        if (this.tooltip != null) {
            this.drawTooltip(this.tooltip, par1, par2);
        }
    }
    
    protected void drawTooltip(final String par1Str, final int par2, final int par3) {
        if (par1Str != null) {
            final int var4 = par2 + 12;
            final int var5 = par3 - 12;
            final int var6 = this.getFontRenderer().getStringWidth(par1Str);
            this.drawGradientRect(var4 - 3, var5 - 3, var4 + var6 + 3, var5 + 8 + 3, -1073741824, -1073741824);
            this.getFontRenderer().drawStringWithShadow(par1Str, var4, var5, -1);
        }
    }
}
