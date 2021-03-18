package com.thevoxelbox.voxelmap.gui;

import com.thevoxelbox.voxelmap.*;
import com.thevoxelbox.voxelmap.interfaces.*;
import com.thevoxelbox.voxelmap.util.*;
import com.thevoxelbox.voxelmap.gui.overridden.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class GuiRadarOptions extends GuiScreenMinimap
{
    private static final EnumOptionsMinimap[] relevantOptions;
    private final GuiScreen parent;
    private final RadarSettingsManager options;
    protected String screenTitle;
    private IVoxelMap master;
    
    public GuiRadarOptions(final GuiScreen parent, final IVoxelMap master) {
        this.screenTitle = "Radar Options";
        this.parent = parent;
        this.options = master.getRadarOptions();
    }
    
    @Override
    public void initGui() {
        int var2 = 0;
        this.screenTitle = I18nUtils.getString("options.minimap.radar.title");
        for (int t = 0; t < GuiRadarOptions.relevantOptions.length; ++t) {
            final EnumOptionsMinimap option = GuiRadarOptions.relevantOptions[t];
            final GuiOptionButtonMinimap var3 = new GuiOptionButtonMinimap(option.returnEnumOrdinal(), this.getWidth() / 2 - 155 + var2 % 2 * 160, this.getHeight() / 6 + 24 * (var2 >> 1), option, this.options.getKeyText(option));
            this.getButtonList().add(var3);
            ++var2;
        }
        for (final Object buttonObj : this.getButtonList()) {
            if (buttonObj instanceof GuiOptionButtonMinimap) {
                final GuiOptionButtonMinimap button = (GuiOptionButtonMinimap)buttonObj;
                if (!button.returnEnumOptions().equals(EnumOptionsMinimap.SHOWRADAR)) {
                    button.enabled = this.options.show;
                }
                if (button.returnEnumOptions().equals(EnumOptionsMinimap.SHOWPLAYERHELMETS) || button.returnEnumOptions().equals(EnumOptionsMinimap.SHOWPLAYERNAMES)) {
                    button.enabled = (button.enabled && this.options.showPlayers);
                }
                else {
                    if (!button.returnEnumOptions().equals(EnumOptionsMinimap.SHOWMOBHELMETS)) {
                        continue;
                    }
                    button.enabled = (button.enabled && (this.options.showNeutrals || this.options.showHostiles));
                }
            }
        }
        this.getButtonList().add(new GuiButton(101, this.getWidth() / 2 - 155, this.getHeight() / 6 + 144 - 6, 150, 20, I18nUtils.getString("options.minimap.radar.selectmobs")));
        this.getButtonList().add(new GuiButton(200, this.getWidth() / 2 - 100, this.getHeight() / 6 + 168, I18nUtils.getString("gui.done")));
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id < 100 && par1GuiButton instanceof GuiOptionButtonMinimap) {
                this.options.setOptionValue(((GuiOptionButtonMinimap)par1GuiButton).returnEnumOptions(), 1);
                par1GuiButton.displayString = this.options.getKeyText(EnumOptionsMinimap.getEnumOptions(par1GuiButton.id));
                for (final Object buttonObj : this.getButtonList()) {
                    if (buttonObj instanceof GuiOptionButtonMinimap) {
                        final GuiOptionButtonMinimap button = (GuiOptionButtonMinimap)buttonObj;
                        if (!button.returnEnumOptions().equals(EnumOptionsMinimap.SHOWRADAR)) {
                            button.enabled = this.options.show;
                        }
                        if (button.returnEnumOptions() == EnumOptionsMinimap.SHOWPLAYERHELMETS || button.returnEnumOptions() == EnumOptionsMinimap.SHOWPLAYERNAMES) {
                            button.enabled = (button.enabled && this.options.showPlayers);
                        }
                        else {
                            if (!button.returnEnumOptions().equals(EnumOptionsMinimap.SHOWMOBHELMETS)) {
                                continue;
                            }
                            button.enabled = (button.enabled && (this.options.showNeutrals || this.options.showHostiles));
                        }
                    }
                    else {
                        if (!(buttonObj instanceof GuiButton)) {
                            continue;
                        }
                        final GuiButton button2 = (GuiButton)buttonObj;
                        if (button2.id != 101) {
                            continue;
                        }
                        button2.enabled = this.options.show;
                    }
                }
            }
            if (par1GuiButton.id == 101) {
                this.getMinecraft().displayGuiScreen(new GuiMobs(this, this.options));
            }
            if (par1GuiButton.id == 200) {
                this.getMinecraft().displayGuiScreen(this.parent);
            }
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        super.drawMap();
        this.drawDefaultBackground();
        this.drawCenteredString(this.getFontRenderer(), this.screenTitle, this.getWidth() / 2, 20, 16777215);
        super.drawScreen(par1, par2, par3);
    }
    
    static {
        relevantOptions = new EnumOptionsMinimap[] { EnumOptionsMinimap.SHOWRADAR, EnumOptionsMinimap.RANDOMOBS, EnumOptionsMinimap.SHOWHOSTILES, EnumOptionsMinimap.SHOWNEUTRALS, EnumOptionsMinimap.SHOWPLAYERS, EnumOptionsMinimap.SHOWPLAYERNAMES, EnumOptionsMinimap.SHOWPLAYERHELMETS, EnumOptionsMinimap.SHOWMOBHELMETS, EnumOptionsMinimap.RADARFILTERING, EnumOptionsMinimap.RADAROUTLINES };
    }
}
