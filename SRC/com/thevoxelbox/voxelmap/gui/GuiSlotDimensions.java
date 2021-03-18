package com.thevoxelbox.voxelmap.gui;

import com.thevoxelbox.voxelmap.gui.overridden.*;
import com.thevoxelbox.voxelmap.interfaces.*;
import net.minecraft.client.*;
import org.lwjgl.input.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import com.thevoxelbox.voxelmap.util.*;

class GuiSlotDimensions extends GuiSlotMinimap
{
    final GuiScreenAddWaypoint parentGui;
    private IDimensionManager dimensionManager;
    
    public GuiSlotDimensions(final GuiScreenAddWaypoint par1GuiWaypoints) {
        super(Minecraft.getMinecraft(), par1GuiWaypoints.getWidth(), par1GuiWaypoints.getHeight(), par1GuiWaypoints.getHeight() / 6 + 123 - 14, par1GuiWaypoints.getHeight() / 6 + 164 + 3, 18);
        this.parentGui = par1GuiWaypoints;
        this.setSlotWidth(175);
        this.setSlotXBoundsFromLeft((this.parentGui.getWidth() - this.slotWidth) / 2);
        this.setShowSelectionBox(false);
        this.setShowTopBottomBG(false);
        this.setShowSlotBG(false);
        this.dimensionManager = this.parentGui.master.getDimensionManager();
        this.scrollBy(this.dimensionManager.getDimensions().indexOf(this.dimensionManager.getDimensionByID(this.parentGui.waypoint.dimensions.first())) * this.slotHeight);
    }
    
    @Override
    protected int getSize() {
        return this.dimensionManager.getDimensions().size();
    }
    
    @Override
    protected void elementClicked(final int par1, final boolean par2, final int x, final int y) {
        this.parentGui.setSelectedDimension(this.dimensionManager.getDimensions().get(par1));
        final int leftEdge = this.parentGui.getWidth() / 2 - this.slotWidth / 2;
        final byte padding = 4;
        final byte iconWidth = 16;
        final int width = this.slotWidth;
        if (this.mouseX >= leftEdge + width - iconWidth - padding && this.mouseX <= leftEdge + width) {
            this.parentGui.toggleDimensionSelected();
        }
        else if (par2) {
            Mouse.next();
            this.parentGui.toggleDimensionSelected();
        }
    }
    
    @Override
    protected boolean isSelected(final int par1) {
        return this.dimensionManager.getDimensions().get(par1).equals(this.parentGui.selectedDimension);
    }
    
    @Override
    protected int getContentHeight() {
        return this.getSize() * 18;
    }
    
    @Override
    protected void drawBackground() {
    }
    
    @Override
    protected void overlayBackground(final int par1, final int par2, final int par3, final int par4) {
    }
    
    @Override
    protected void drawSlot(final int par1, int par2, final int par3, final int par4, final Tessellator par5Tessellator, final int x, final int y) {
        final Dimension dim = this.dimensionManager.getDimensions().get(par1);
        String name = dim.name;
        if (name.equals("notLoaded") || name.equals("failedToLoad")) {
            name = "dimension " + dim.ID + "(" + Minecraft.getMinecraft().theWorld.provider.getClass().getSimpleName() + ")";
        }
        this.parentGui.drawCenteredString(this.parentGui.getFontRenderer(), dim.name, this.parentGui.getWidth() / 2, par3 + 3, 16777215);
        final byte padding = 4;
        final byte iconWidth = 16;
        par2 = this.parentGui.getWidth() / 2 - this.slotWidth / 2;
        final int width = this.slotWidth;
        if (this.mouseX >= par2 + padding && this.mouseY >= par3 && this.mouseX <= par2 + width + padding && this.mouseY <= par3 + this.slotHeight) {
            String tooltip = null;
            if (this.mouseX >= par2 + width - iconWidth - padding && this.mouseX <= par2 + width) {
                tooltip = (this.parentGui.waypoint.dimensions.contains(dim.ID) ? I18nUtils.getString("minimap.waypoints.dimension.applies") : I18nUtils.getString("minimap.waypoints.dimension.notapplies"));
            }
            else {
                tooltip = null;
            }
            GuiScreenAddWaypoint.setTooltip(this.parentGui, tooltip);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GLUtils.img("textures/gui/container/beacon.png");
        final int xOffset = this.parentGui.waypoint.dimensions.contains(dim.ID) ? 91 : 113;
        final int yOffset = 222;
        this.parentGui.drawTexturedModalRect(par2 + width - iconWidth, par3 - 2, xOffset, yOffset, 16, 16);
    }
}
