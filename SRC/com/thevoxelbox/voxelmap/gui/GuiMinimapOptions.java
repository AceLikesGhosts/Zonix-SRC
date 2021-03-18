package com.thevoxelbox.voxelmap.gui;

import com.thevoxelbox.voxelmap.*;
import com.thevoxelbox.voxelmap.interfaces.*;
import com.thevoxelbox.voxelmap.util.*;
import com.thevoxelbox.voxelmap.gui.overridden.*;
import net.minecraft.client.gui.*;

public class GuiMinimapOptions extends GuiScreenMinimap
{
    private static EnumOptionsMinimap[] relevantOptions;
    private final MapSettingsManager options;
    protected String screenTitle;
    private IVoxelMap master;
    
    public GuiMinimapOptions(final IVoxelMap master) {
        this.screenTitle = "Minimap Options";
        this.master = master;
        this.options = master.getMapOptions();
    }
    
    @Override
    public void initGui() {
        GuiMinimapOptions.relevantOptions = new EnumOptionsMinimap[] { EnumOptionsMinimap.COORDS, EnumOptionsMinimap.HIDE, EnumOptionsMinimap.LOCATION, EnumOptionsMinimap.SIZE, EnumOptionsMinimap.SQUARE, EnumOptionsMinimap.OLDNORTH, EnumOptionsMinimap.BEACONS, EnumOptionsMinimap.CAVEMODE };
        int var2 = 0;
        this.screenTitle = I18nUtils.getString("options.minimap.title");
        for (int t = 0; t < GuiMinimapOptions.relevantOptions.length; ++t) {
            final EnumOptionsMinimap option = GuiMinimapOptions.relevantOptions[t];
            final GuiOptionButtonMinimap var3 = new GuiOptionButtonMinimap(option.returnEnumOrdinal(), this.getWidth() / 2 - 155 + var2 % 2 * 160, this.getHeight() / 6 + 24 * (var2 >> 1), option, this.options.getKeyText(option));
            this.getButtonList().add(var3);
            if (option.equals(EnumOptionsMinimap.CAVEMODE)) {
                var3.enabled = (this.options.cavesAllowed && this.master.getRadar() != null);
            }
            ++var2;
        }
        final GuiOptionButtonMinimap radarOptionsButton = new GuiOptionButtonMinimap(101, this.getWidth() / 2 - 155, this.getHeight() / 6 + 120 - 6, 150, 20, I18nUtils.getString("options.minimap.radar"));
        radarOptionsButton.enabled = (this.options.radarAllowed && this.master.getRadar() != null);
        this.getButtonList().add(radarOptionsButton);
        this.getButtonList().add(new GuiButton(103, this.getWidth() / 2 + 5, this.getHeight() / 6 + 120 - 6, 150, 20, I18nUtils.getString("options.minimap.detailsperformance")));
        this.getButtonList().add(new GuiButton(102, this.getWidth() / 2 - 155, this.getHeight() / 6 + 144 - 6, 150, 20, I18nUtils.getString("options.controls")));
        this.getButtonList().add(new GuiButton(100, this.getWidth() / 2 + 5, this.getHeight() / 6 + 144 - 6, 150, 20, I18nUtils.getString("options.minimap.waypoints")));
        this.getButtonList().add(new GuiButton(200, this.getWidth() / 2 - 100, this.getHeight() / 6 + 168, I18nUtils.getString("menu.returnToGame")));
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id < 100 && par1GuiButton instanceof GuiOptionButtonMinimap) {
                this.options.setOptionValue(((GuiOptionButtonMinimap)par1GuiButton).returnEnumOptions(), 1);
                par1GuiButton.displayString = this.options.getKeyText(EnumOptionsMinimap.getEnumOptions(par1GuiButton.id));
            }
            if (par1GuiButton.id == 103) {
                this.getMinecraft().displayGuiScreen(new GuiMinimapPerformance(this, this.master));
            }
            if (par1GuiButton.id == 102) {
                this.getMinecraft().displayGuiScreen(new GuiMinimapControls(this, this.master));
            }
            if (par1GuiButton.id == 101) {
                this.getMinecraft().displayGuiScreen(new GuiRadarOptions(this, this.master));
            }
            if (par1GuiButton.id == 100) {
                this.getMinecraft().displayGuiScreen(new GuiWaypoints(this, this.master));
            }
            if (par1GuiButton.id == 200) {
                this.getMinecraft().displayGuiScreen(null);
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
}
