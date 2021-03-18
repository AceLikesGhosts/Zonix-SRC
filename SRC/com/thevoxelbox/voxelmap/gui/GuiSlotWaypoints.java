package com.thevoxelbox.voxelmap.gui;

import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import com.thevoxelbox.voxelmap.util.*;
import java.util.*;
import java.text.*;
import java.awt.*;

class GuiSlotWaypoints extends GuiSlot
{
    final GuiWaypoints parentGui;
    private ArrayList<Waypoint> waypoints;
    
    public GuiSlotWaypoints(final GuiWaypoints par1GuiWaypoints) {
        super(par1GuiWaypoints.options.game, par1GuiWaypoints.getWidth(), par1GuiWaypoints.getHeight(), 54, par1GuiWaypoints.getHeight() - 65 + 4, 18);
        this.parentGui = par1GuiWaypoints;
        this.waypoints = new ArrayList<Waypoint>();
        for (final Waypoint pt : this.parentGui.waypointManager.getWaypoints()) {
            if (pt.inWorld && pt.inDimension) {
                this.waypoints.add(pt);
            }
        }
    }
    
    @Override
    protected int getSize() {
        return this.waypoints.size();
    }
    
    @Override
    protected void elementClicked(final int par1, final boolean par2, final int par3, final int par4) {
        this.parentGui.setSelectedWaypoint(this.waypoints.get(par1));
        final int leftEdge = this.parentGui.getWidth() / 2 - 92 - 16;
        final byte padding = 3;
        final int width = 215;
        if (this.field_148150_g >= leftEdge + width - 16 - padding && this.field_148150_g <= leftEdge + width + padding) {
            this.parentGui.toggleWaypointVisibility();
        }
        else if (par2) {
            Mouse.next();
            this.parentGui.editWaypoint(this.parentGui.selectedWaypoint);
        }
    }
    
    @Override
    protected boolean isSelected(final int par1) {
        return this.waypoints.get(par1).equals(this.parentGui.selectedWaypoint);
    }
    
    @Override
    protected int func_148138_e() {
        return this.getSize() * 18;
    }
    
    @Override
    protected void drawBackground() {
        this.parentGui.drawDefaultBackground();
    }
    
    @Override
    protected void drawSlot(final int par1, final int par2, final int par3, final int par4, final Tessellator par5Tessellator, final int par6, final int par7) {
        final Waypoint waypoint = this.waypoints.get(par1);
        this.parentGui.drawCenteredString(this.parentGui.getFontRenderer(), waypoint.isAutomated ? (EnumChatFormatting.GRAY + "(Server) " + EnumChatFormatting.RESET + waypoint.name) : waypoint.name, this.parentGui.getWidth() / 2, par3 + 3, waypoint.getUnified());
        final byte padding = 3;
        if (this.field_148150_g >= par2 - padding && this.field_148162_h >= par3 && this.field_148150_g <= par2 + 215 + padding && this.field_148162_h <= par3 + this.field_148149_f) {
            String tooltip;
            if (this.field_148150_g >= par2 + 215 - 16 - padding && this.field_148150_g <= par2 + 215 + padding) {
                tooltip = (waypoint.enabled ? I18nUtils.getString("minimap.waypoints.disable") : I18nUtils.getString("minimap.waypoints.enable"));
            }
            else {
                tooltip = "X: " + waypoint.getX() + " Z: " + waypoint.getZ();
                if (waypoint.getY() > 0) {
                    tooltip = tooltip + " Y: " + waypoint.getY();
                }
            }
            GuiWaypoints.setTooltip(this.parentGui, tooltip);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GLUtils.img("textures/gui/container/inventory.png");
        final int xOffset = waypoint.enabled ? 72 : 90;
        final int yOffset = 216;
        this.parentGui.drawTexturedModalRect(par2 + 198, par3 - 2, xOffset, yOffset, 16, 16);
    }
    
    protected void sortBy(final int sortKey, final boolean ascending) {
        final int order = ascending ? 1 : -1;
        this.parentGui.options.getClass();
        if (sortKey == 1) {
            final ArrayList<Waypoint> masterWaypointsList = this.parentGui.waypointManager.getWaypoints();
            Collections.sort(this.waypoints, new Comparator<Waypoint>() {
                @Override
                public int compare(final Waypoint waypoint1, final Waypoint waypoint2) {
                    return Double.compare(masterWaypointsList.indexOf(waypoint1), masterWaypointsList.indexOf(waypoint2)) * order;
                }
            });
        }
        else {
            this.parentGui.options.getClass();
            if (sortKey == 3) {
                if (ascending) {
                    Collections.sort(this.waypoints);
                }
                else {
                    Collections.sort(this.waypoints, Collections.reverseOrder());
                }
            }
            else {
                this.parentGui.options.getClass();
                if (sortKey == 2) {
                    final Collator collator = I18nUtils.getLocaleAwareCollator();
                    Collections.sort(this.waypoints, new Comparator<Waypoint>() {
                        @Override
                        public int compare(final Waypoint waypoint1, final Waypoint waypoint2) {
                            return collator.compare(waypoint1.name, waypoint2.name) * order;
                        }
                    });
                }
                else {
                    this.parentGui.options.getClass();
                    if (sortKey == 4) {
                        Collections.sort(this.waypoints, new Comparator<Waypoint>() {
                            @Override
                            public int compare(final Waypoint waypoint1, final Waypoint waypoint2) {
                                final float hue1 = Color.RGBtoHSB((int)(waypoint1.red * 255.0f), (int)(waypoint1.green * 255.0f), (int)(waypoint1.blue * 255.0f), null)[0];
                                final float hue2 = Color.RGBtoHSB((int)(waypoint2.red * 255.0f), (int)(waypoint2.green * 255.0f), (int)(waypoint2.blue * 255.0f), null)[0];
                                return Double.compare(hue1, hue2) * order;
                            }
                        });
                    }
                }
            }
        }
    }
}
