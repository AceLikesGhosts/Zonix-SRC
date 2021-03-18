package com.thevoxelbox.voxelmap.gui;

import com.thevoxelbox.voxelmap.*;
import com.thevoxelbox.voxelmap.util.*;
import com.thevoxelbox.voxelmap.gui.overridden.*;
import net.minecraft.client.gui.*;

public class GuiWaypointsOptions extends GuiScreenMinimap
{
    private static final EnumOptionsMinimap[] relevantOptions;
    private final GuiScreen parent;
    private final MapSettingsManager options;
    protected String screenTitle;
    
    public GuiWaypointsOptions(final GuiScreen parent, final MapSettingsManager options) {
        this.screenTitle = "Radar Options";
        this.parent = parent;
        this.options = options;
    }
    
    @Override
    public void initGui() {
        int var2 = 0;
        this.screenTitle = I18nUtils.getString("options.minimap.waypoints.title");
        for (int t = 0; t < GuiWaypointsOptions.relevantOptions.length; ++t) {
            final EnumOptionsMinimap option = GuiWaypointsOptions.relevantOptions[t];
            if (option.getEnumFloat()) {
                float distance = this.options.getOptionFloatValue(option);
                if (distance < 0.0f) {
                    distance = 10001.0f;
                }
                distance = (distance - 50.0f) / 9951.0f;
                this.getButtonList().add(new GuiOptionSliderMinimap(option.returnEnumOrdinal(), this.getWidth() / 2 - 155 + var2 % 2 * 160, this.getHeight() / 6 + 24 * (var2 >> 1), option, distance, this.options));
            }
            else {
                final GuiOptionButtonMinimap var3 = new GuiOptionButtonMinimap(option.returnEnumOrdinal(), this.getWidth() / 2 - 155 + var2 % 2 * 160, this.getHeight() / 6 + 24 * (var2 >> 1), option, this.options.getKeyText(option));
                this.getButtonList().add(var3);
            }
            ++var2;
        }
        this.getButtonList().add(new GuiButton(200, this.getWidth() / 2 - 100, this.getHeight() / 6 + 168, I18nUtils.getString("gui.done")));
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id < 100 && par1GuiButton instanceof GuiOptionButtonMinimap) {
                this.options.setOptionValue(((GuiOptionButtonMinimap)par1GuiButton).returnEnumOptions(), 1);
                par1GuiButton.displayString = this.options.getKeyText(EnumOptionsMinimap.getEnumOptions(par1GuiButton.id));
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
        this.drawCenteredString(this.fontRendererObj, this.screenTitle, this.getWidth() / 2, 20, 16777215);
        super.drawScreen(par1, par2, par3);
    }
    
    static {
        relevantOptions = new EnumOptionsMinimap[] { EnumOptionsMinimap.WAYPOINTDISTANCE, EnumOptionsMinimap.DEATHPOINTS };
    }
}
