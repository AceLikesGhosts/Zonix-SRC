package com.thevoxelbox.voxelmap.gui;

import com.thevoxelbox.voxelmap.gui.overridden.*;
import com.thevoxelbox.voxelmap.interfaces.*;
import java.util.*;
import org.lwjgl.input.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import com.thevoxelbox.voxelmap.util.*;
import net.minecraft.util.*;
import net.minecraft.client.*;

public class GuiScreenAddWaypoint extends GuiScreenMinimap
{
    protected Dimension selectedDimension;
    protected Waypoint waypoint;
    IVoxelMap master;
    IWaypointManager waypointManager;
    IColorManager colorManager;
    private GuiWaypoints parentGui;
    private GuiSlotDimensions dimensionList;
    private String tooltip;
    private GuiTextField waypointName;
    private GuiTextField waypointX;
    private GuiTextField waypointZ;
    private GuiTextField waypointY;
    private GuiButton buttonEnabled;
    private boolean choosingColor;
    private float red;
    private float green;
    private float blue;
    private boolean enabled;
    private Random generator;
    
    public GuiScreenAddWaypoint(final IVoxelMap master, final GuiWaypoints par1GuiScreen, final Waypoint par2Waypoint) {
        this.selectedDimension = null;
        this.tooltip = null;
        this.choosingColor = false;
        this.generator = new Random();
        this.master = master;
        this.waypointManager = master.getWaypointManager();
        this.colorManager = master.getColorManager();
        this.parentGui = par1GuiScreen;
        this.waypoint = par2Waypoint;
        this.red = this.waypoint.red;
        this.green = this.waypoint.green;
        this.blue = this.waypoint.blue;
        this.enabled = this.waypoint.enabled;
    }
    
    static String setTooltip(final GuiScreenAddWaypoint par0GuiWaypoint, final String par1Str) {
        return par0GuiWaypoint.tooltip = par1Str;
    }
    
    @Override
    public void updateScreen() {
        this.waypointName.updateCursorCounter();
        this.waypointX.updateCursorCounter();
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.getButtonList().clear();
        this.getButtonList().add(new GuiButton(0, this.getWidth() / 2 - 155, this.getHeight() / 6 + 168, 150, 20, I18nUtils.getString("addServer.add")));
        this.getButtonList().add(new GuiButton(1, this.getWidth() / 2 + 5, this.getHeight() / 6 + 168, 150, 20, I18nUtils.getString("gui.cancel")));
        (this.waypointName = new GuiTextField(this.getFontRenderer(), this.getWidth() / 2 - 100, this.getHeight() / 6 + 0 + 13, 200, 20)).setFocused(true);
        this.waypointName.setText(this.waypoint.name);
        (this.waypointX = new GuiTextField(this.getFontRenderer(), this.getWidth() / 2 - 100, this.getHeight() / 6 + 41 + 13, 56, 20)).func_146203_f(128);
        this.waypointX.setText("" + this.waypoint.getX());
        (this.waypointZ = new GuiTextField(this.getFontRenderer(), this.getWidth() / 2 - 28, this.getHeight() / 6 + 41 + 13, 56, 20)).func_146203_f(128);
        this.waypointZ.setText("" + this.waypoint.getZ());
        (this.waypointY = new GuiTextField(this.getFontRenderer(), this.getWidth() / 2 + 44, this.getHeight() / 6 + 41 + 13, 56, 20)).func_146203_f(128);
        this.waypointY.setText("" + this.waypoint.getY());
        this.getButtonList().add(this.buttonEnabled = new GuiButton(2, this.getWidth() / 2 - 101, this.getHeight() / 6 + 82 + 6, 100, 20, "Enabled: " + (this.waypoint.enabled ? "On" : "Off")));
        this.getButtonList().get(0).enabled = (this.waypointName.getText().length() > 0);
        (this.dimensionList = new GuiSlotDimensions(this)).registerScrollButtons(7, 8);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id == 2) {
                this.waypoint.enabled = !this.waypoint.enabled;
            }
            if (par1GuiButton.id == 1) {
                this.waypoint.red = this.red;
                this.waypoint.green = this.green;
                this.waypoint.blue = this.blue;
                this.waypoint.enabled = this.enabled;
                if (this.parentGui != null) {
                    this.parentGui.confirmClicked(false, 0);
                }
                else {
                    this.getMinecraft().displayGuiScreen(null);
                }
            }
            else if (par1GuiButton.id == 0) {
                this.waypoint.name = this.waypointName.getText();
                this.waypoint.setX(Integer.parseInt(this.waypointX.getText()));
                this.waypoint.setZ(Integer.parseInt(this.waypointZ.getText()));
                this.waypoint.setY(Integer.parseInt(this.waypointY.getText()));
                if (this.parentGui != null) {
                    this.parentGui.confirmClicked(true, 0);
                }
                else {
                    this.waypointManager.addWaypoint(this.waypoint);
                    this.getMinecraft().displayGuiScreen(null);
                }
            }
        }
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
        this.waypointName.textboxKeyTyped(par1, par2);
        this.waypointX.textboxKeyTyped(par1, par2);
        this.waypointZ.textboxKeyTyped(par1, par2);
        this.waypointY.textboxKeyTyped(par1, par2);
        if (par1 == '\t') {
            if (this.waypointName.isFocused()) {
                this.waypointName.setFocused(false);
                this.waypointX.setFocused(true);
                this.waypointZ.setFocused(false);
                this.waypointY.setFocused(false);
            }
            else if (this.waypointX.isFocused()) {
                this.waypointName.setFocused(false);
                this.waypointX.setFocused(false);
                this.waypointZ.setFocused(true);
                this.waypointY.setFocused(false);
            }
            else if (this.waypointZ.isFocused()) {
                this.waypointName.setFocused(false);
                this.waypointX.setFocused(false);
                this.waypointZ.setFocused(false);
                this.waypointY.setFocused(true);
            }
            else if (this.waypointY.isFocused()) {
                this.waypointName.setFocused(true);
                this.waypointX.setFocused(false);
                this.waypointZ.setFocused(false);
                this.waypointY.setFocused(false);
            }
        }
        if (par1 == '\r') {
            this.actionPerformed(this.getButtonList().get(0));
        }
        boolean acceptable = this.waypointName.getText().length() > 0;
        try {
            final int x = Integer.parseInt(this.waypointX.getText());
            acceptable = acceptable;
        }
        catch (NumberFormatException e) {
            acceptable = false;
        }
        try {
            final int z = Integer.parseInt(this.waypointZ.getText());
            acceptable = acceptable;
        }
        catch (NumberFormatException e) {
            acceptable = false;
        }
        try {
            final int y = Integer.parseInt(this.waypointY.getText());
            acceptable = acceptable;
        }
        catch (NumberFormatException e) {
            acceptable = false;
        }
        this.getButtonList().get(0).enabled = acceptable;
        if (par2 == 1) {
            this.waypoint.red = this.red;
            this.waypoint.green = this.green;
            this.waypoint.blue = this.blue;
            this.waypoint.enabled = this.enabled;
        }
        super.keyTyped(par1, par2);
    }
    
    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) {
        if (!this.choosingColor) {
            super.mouseClicked(par1, par2, par3);
            this.waypointName.mouseClicked(par1, par2, par3);
            this.waypointX.mouseClicked(par1, par2, par3);
            this.waypointZ.mouseClicked(par1, par2, par3);
            this.waypointY.mouseClicked(par1, par2, par3);
            if (par1 >= this.getWidth() / 2 + 85 && par1 <= this.getWidth() / 2 + 101 && par2 >= this.getHeight() / 6 + 82 + 11 && par2 <= this.getHeight() / 6 + 82 + 21) {
                this.choosingColor = true;
            }
        }
        else if (par1 >= this.getWidth() / 2 - 128 && par1 <= this.getWidth() / 2 + 128 && par2 >= this.getHeight() / 2 - 128 && par2 <= this.getHeight() / 2 + 128) {
            final int color = this.colorManager.getColorPicker().getRGB(par1 - (this.getWidth() / 2 - 128), par2 - (this.getHeight() / 2 - 128));
            this.waypoint.red = (color >> 16 & 0xFF) / 255.0f;
            this.waypoint.green = (color >> 8 & 0xFF) / 255.0f;
            this.waypoint.blue = (color >> 0 & 0xFF) / 255.0f;
            this.choosingColor = false;
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        super.drawMap();
        this.tooltip = null;
        this.buttonEnabled.displayString = I18nUtils.getString("minimap.waypoints.enabled") + " " + (this.waypoint.enabled ? I18nUtils.getString("options.on") : I18nUtils.getString("options.off"));
        this.drawDefaultBackground();
        this.dimensionList.drawScreen(par1, par2, par3);
        this.drawCenteredString(this.getFontRenderer(), (this.parentGui != null && this.parentGui.editClicked) ? I18nUtils.getString("minimap.waypoints.edit") : I18nUtils.getString("minimap.waypoints.new"), this.getWidth() / 2, 20, 16777215);
        this.drawString(this.getFontRenderer(), I18nUtils.getString("minimap.waypoints.name"), this.getWidth() / 2 - 100, this.getHeight() / 6 + 0, 10526880);
        this.drawString(this.getFontRenderer(), I18nUtils.getString("X"), this.getWidth() / 2 - 100, this.getHeight() / 6 + 41, 10526880);
        this.drawString(this.getFontRenderer(), I18nUtils.getString("Z"), this.getWidth() / 2 - 28, this.getHeight() / 6 + 41, 10526880);
        this.drawString(this.getFontRenderer(), I18nUtils.getString("Y"), this.getWidth() / 2 + 44, this.getHeight() / 6 + 41, 10526880);
        this.drawString(this.getFontRenderer(), I18nUtils.getString("minimap.waypoints.choosecolor"), this.getWidth() / 2 + 10, this.getHeight() / 6 + 82 + 11, 10526880);
        this.waypointName.drawTextBox();
        this.waypointX.drawTextBox();
        this.waypointZ.drawTextBox();
        this.waypointY.drawTextBox();
        GL11.glColor4f(this.waypoint.red, this.waypoint.green, this.waypoint.blue, 1.0f);
        GLUtils.disp(-1);
        this.drawTexturedModalRect(this.getWidth() / 2 + 85, this.getHeight() / 6 + 82 + 11, 0, 0, 16, 10);
        super.drawScreen(par1, par2, par3);
        if (this.choosingColor) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GLUtils.img(new ResourceLocation("voxelmap/images/colorPicker.png"));
            this.drawTexturedModalRect(this.getWidth() / 2 - 128, this.getHeight() / 2 - 128, 0, 0, 256, 256);
        }
        this.drawTooltip(this.tooltip, par1, par2);
    }
    
    public void setSelectedDimension(final Dimension dimension) {
        this.selectedDimension = dimension;
    }
    
    public void toggleDimensionSelected() {
        if (this.waypoint.dimensions.size() > 1 && this.waypoint.dimensions.contains(this.selectedDimension.ID) && this.selectedDimension.ID != Minecraft.getMinecraft().thePlayer.dimension) {
            this.waypoint.dimensions.remove(new Integer(this.selectedDimension.ID));
        }
        else if (!this.waypoint.dimensions.contains(this.selectedDimension.ID)) {
            this.waypoint.dimensions.add(new Integer(this.selectedDimension.ID));
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
