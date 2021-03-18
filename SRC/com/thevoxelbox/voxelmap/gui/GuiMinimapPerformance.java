package com.thevoxelbox.voxelmap.gui;

import com.thevoxelbox.voxelmap.interfaces.*;
import com.thevoxelbox.voxelmap.*;
import com.thevoxelbox.voxelmap.util.*;
import com.thevoxelbox.voxelmap.gui.overridden.*;
import net.minecraft.client.gui.*;

public class GuiMinimapPerformance extends GuiScreenMinimap
{
    private static final EnumOptionsMinimap[] relevantOptions;
    protected String screenTitle;
    private IVoxelMap master;
    private GuiScreen parentScreen;
    private MapSettingsManager options;
    private int buttonId;
    
    public GuiMinimapPerformance(final GuiScreen par1GuiScreen, final IVoxelMap master) {
        this.screenTitle = "Details / Performance";
        this.buttonId = -1;
        this.parentScreen = par1GuiScreen;
        this.options = master.getMapOptions();
    }
    
    private int getLeftBorder() {
        return this.getWidth() / 2 - 155;
    }
    
    @Override
    public void initGui() {
        this.screenTitle = I18nUtils.getString("options.minimap.detailsperformance");
        final int var1 = this.getLeftBorder();
        int var2 = 0;
        for (int t = 0; t < GuiMinimapPerformance.relevantOptions.length; ++t) {
            final EnumOptionsMinimap option = GuiMinimapPerformance.relevantOptions[t];
            String text = this.options.getKeyText(option);
            if ((option == EnumOptionsMinimap.WATERTRANSPARENCY || option == EnumOptionsMinimap.BLOCKTRANSPARENCY || option == EnumOptionsMinimap.BIOMES) && !this.options.multicore && this.options.getOptionBooleanValue(option)) {
                text = "§c" + text;
            }
            final GuiOptionButtonMinimap var3 = new GuiOptionButtonMinimap(option.returnEnumOrdinal(), var1 + var2 % 2 * 160, this.getHeight() / 6 + 24 * (var2 >> 1), option, text);
            this.buttonList.add(var3);
            ++var2;
        }
        this.buttonList.add(new GuiButton(200, this.getWidth() / 2 - 100, this.getHeight() / 6 + 168, I18nUtils.getString("gui.done")));
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.id < 100 && par1GuiButton instanceof GuiOptionButtonMinimap) {
            this.options.setOptionValue(((GuiOptionButtonMinimap)par1GuiButton).returnEnumOptions(), 1);
            String perfBomb = "";
            if ((par1GuiButton.id == EnumOptionsMinimap.WATERTRANSPARENCY.ordinal() || par1GuiButton.id == EnumOptionsMinimap.BLOCKTRANSPARENCY.ordinal() || par1GuiButton.id == EnumOptionsMinimap.BIOMES.ordinal()) && !this.options.multicore && this.options.getOptionBooleanValue(EnumOptionsMinimap.getEnumOptions(par1GuiButton.id))) {
                perfBomb = "§c";
            }
            par1GuiButton.displayString = perfBomb + this.options.getKeyText(EnumOptionsMinimap.getEnumOptions(par1GuiButton.id));
        }
        if (par1GuiButton.id == 200) {
            this.getMinecraft().displayGuiScreen(this.parentScreen);
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
        relevantOptions = new EnumOptionsMinimap[] { EnumOptionsMinimap.LIGHTING, EnumOptionsMinimap.TERRAIN, EnumOptionsMinimap.WATERTRANSPARENCY, EnumOptionsMinimap.BLOCKTRANSPARENCY, EnumOptionsMinimap.BIOMES, EnumOptionsMinimap.FILTERING, EnumOptionsMinimap.CHUNKGRID, EnumOptionsMinimap.BIOMEOVERLAY };
    }
}
