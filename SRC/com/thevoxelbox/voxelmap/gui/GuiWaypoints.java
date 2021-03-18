package com.thevoxelbox.voxelmap.gui;

import com.thevoxelbox.voxelmap.gui.overridden.*;
import com.thevoxelbox.voxelmap.*;
import com.thevoxelbox.voxelmap.interfaces.*;
import net.minecraft.client.gui.*;
import java.util.*;
import com.thevoxelbox.voxelmap.util.*;
import net.minecraft.server.*;

public class GuiWaypoints extends GuiScreenMinimap implements GuiYesNoCallback
{
    protected final MapSettingsManager options;
    protected final IWaypointManager waypointManager;
    private final GuiScreen parentScreen;
    protected String screenTitle;
    protected boolean editClicked;
    protected Waypoint selectedWaypoint;
    protected Waypoint newWaypoint;
    private IVoxelMap master;
    private GuiSlotWaypoints waypointList;
    private GuiButton buttonEdit;
    private GuiButton buttonDelete;
    private boolean deleteClicked;
    private GuiButton buttonTeleport;
    private GuiButton buttonSortName;
    private GuiButton buttonSortCreated;
    private GuiButton buttonSortDistance;
    private GuiButton buttonSortColor;
    private boolean addClicked;
    private String tooltip;
    private Random generator;
    private boolean changedSort;
    
    public GuiWaypoints(final GuiScreen parentScreen, final IVoxelMap master) {
        this.screenTitle = "Waypoints";
        this.editClicked = false;
        this.selectedWaypoint = null;
        this.newWaypoint = null;
        this.deleteClicked = false;
        this.addClicked = false;
        this.tooltip = null;
        this.generator = new Random();
        this.changedSort = false;
        this.master = master;
        this.parentScreen = parentScreen;
        this.options = master.getMapOptions();
        this.waypointManager = master.getWaypointManager();
    }
    
    static String setTooltip(final GuiWaypoints par0GuiWaypoints, final String par1Str) {
        return par0GuiWaypoints.tooltip = par1Str;
    }
    
    static GuiButton getButtonEdit(final GuiWaypoints par0GuiWaypoints) {
        return par0GuiWaypoints.buttonEdit;
    }
    
    static GuiButton getButtonDelete(final GuiWaypoints par0GuiWaypoints) {
        return par0GuiWaypoints.buttonDelete;
    }
    
    static GuiButton getButtonTeleport(final GuiWaypoints par0GuiWaypoints) {
        return par0GuiWaypoints.buttonTeleport;
    }
    
    @Override
    public void initGui() {
        final int var2 = 0;
        this.screenTitle = I18nUtils.getString("minimap.waypoints.title");
        (this.waypointList = new GuiSlotWaypoints(this)).func_148134_d(7, 8);
        this.options.getClass();
        this.getButtonList().add(this.buttonSortName = new GuiButton(2, this.getWidth() / 2 - 154, 34, 77, 20, I18nUtils.getString("minimap.waypoints.sortbyname")));
        this.options.getClass();
        this.getButtonList().add(this.buttonSortDistance = new GuiButton(3, this.getWidth() / 2 - 77, 34, 77, 20, I18nUtils.getString("minimap.waypoints.sortbydistance")));
        this.options.getClass();
        this.getButtonList().add(this.buttonSortCreated = new GuiButton(1, this.getWidth() / 2, 34, 77, 20, I18nUtils.getString("minimap.waypoints.sortbycreated")));
        this.options.getClass();
        this.getButtonList().add(this.buttonSortColor = new GuiButton(4, this.getWidth() / 2 + 77, 34, 77, 20, I18nUtils.getString("minimap.waypoints.sortbycolor")));
        this.getButtonList().add(this.buttonEdit = new GuiButton(-1, this.getWidth() / 2 - 154, this.getHeight() - 52, 100, 20, I18nUtils.getString("selectServer.edit")));
        this.getButtonList().add(this.buttonDelete = new GuiButton(-2, this.getWidth() / 2 - 50, this.getHeight() - 52, 100, 20, I18nUtils.getString("selectServer.delete")));
        this.getButtonList().add(this.buttonTeleport = new GuiButton(-3, this.getWidth() / 2 + 4 + 50, this.getHeight() - 52, 100, 20, I18nUtils.getString("minimap.waypoints.teleportto")));
        this.getButtonList().add(new GuiButton(-4, this.getWidth() / 2 - 154, this.getHeight() - 28, 100, 20, I18nUtils.getString("minimap.waypoints.newwaypoint")));
        this.getButtonList().add(new GuiButton(-5, this.getWidth() / 2 - 50, this.getHeight() - 28, 100, 20, I18nUtils.getString("menu.options")));
        this.getButtonList().add(new GuiButton(65336, this.getWidth() / 2 + 4 + 50, this.getHeight() - 28, 100, 20, I18nUtils.getString("gui.done")));
        final boolean isSomethingSelected = this.selectedWaypoint != null;
        this.buttonEdit.enabled = isSomethingSelected;
        this.buttonDelete.enabled = isSomethingSelected;
        this.buttonTeleport.enabled = (isSomethingSelected && this.canTeleport());
        this.sort();
    }
    
    private void sort() {
        final int sortKey = Math.abs(this.options.sort);
        final boolean ascending = this.options.sort > 0;
        this.waypointList.sortBy(sortKey, ascending);
        final String arrow = ascending ? "\u2191" : "\u2193";
        this.options.getClass();
        if (sortKey == 2) {
            this.buttonSortName.displayString = arrow + " " + I18nUtils.getString("mco.configure.world.name") + " " + arrow;
        }
        else {
            this.buttonSortName.displayString = I18nUtils.getString("mco.configure.world.name");
        }
        this.options.getClass();
        if (sortKey == 3) {
            this.buttonSortDistance.displayString = arrow + " " + I18nUtils.getString("minimap.waypoints.sortbydistance") + " " + arrow;
        }
        else {
            this.buttonSortDistance.displayString = I18nUtils.getString("minimap.waypoints.sortbydistance");
        }
        this.options.getClass();
        if (sortKey == 1) {
            this.buttonSortCreated.displayString = arrow + " " + I18nUtils.getString("minimap.waypoints.sortbycreated") + " " + arrow;
        }
        else {
            this.buttonSortCreated.displayString = I18nUtils.getString("minimap.waypoints.sortbycreated");
        }
        this.options.getClass();
        if (sortKey == 4) {
            this.buttonSortColor.displayString = arrow + " " + I18nUtils.getString("minimap.waypoints.sortbycolor") + " " + arrow;
        }
        else {
            this.buttonSortColor.displayString = I18nUtils.getString("minimap.waypoints.sortbycolor");
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id > 0) {
                this.options.setSort(par1GuiButton.id);
                this.changedSort = true;
                this.sort();
            }
            if (par1GuiButton.id == -1) {
                this.editWaypoint(this.selectedWaypoint);
            }
            if (par1GuiButton.id == -2) {
                final String var2 = this.selectedWaypoint.name;
                if (var2 != null) {
                    this.deleteClicked = true;
                    final String var3 = I18nUtils.getString("minimap.waypoints.deleteconfirm");
                    final String var4 = "'" + var2 + "' " + I18nUtils.getString("selectServer.deleteWarning");
                    final String var5 = I18nUtils.getString("selectServer.deleteButton");
                    final String var6 = I18nUtils.getString("gui.cancel");
                    final GuiYesNo var7 = new GuiYesNo(this, var3, var4, var5, var6, this.waypointManager.getWaypoints().indexOf(this.selectedWaypoint));
                    this.getMinecraft().displayGuiScreen(var7);
                }
            }
            if (par1GuiButton.id == -3) {
                if (this.options.game.isIntegratedServerRunning()) {
                    this.options.game.thePlayer.sendChatMessage("/ztp " + this.selectedWaypoint.name);
                    this.getMinecraft().displayGuiScreen(null);
                }
                else {
                    if (this.selectedWaypoint.getY() > 0) {
                        this.options.game.thePlayer.sendChatMessage("/tp " + this.options.game.thePlayer.getCommandSenderName() + " " + this.selectedWaypoint.getX() + " " + this.selectedWaypoint.getY() + " " + this.selectedWaypoint.getZ());
                        this.options.game.thePlayer.sendChatMessage("/tppos " + this.selectedWaypoint.getX() + " " + this.selectedWaypoint.getY() + " " + this.selectedWaypoint.getZ());
                    }
                    else {
                        this.options.game.thePlayer.sendChatMessage("/tp " + this.options.game.thePlayer.getCommandSenderName() + " " + this.selectedWaypoint.getX() + " " + ((this.options.game.thePlayer.dimension != -1) ? "128" : "64") + " " + this.selectedWaypoint.getZ());
                        this.options.game.thePlayer.sendChatMessage("/tppos " + this.selectedWaypoint.getX() + " " + ((this.options.game.thePlayer.dimension != -1) ? "256" : "64") + " " + this.selectedWaypoint.getZ());
                    }
                    this.getMinecraft().displayGuiScreen(null);
                }
            }
            if (par1GuiButton.id == -4) {
                this.addWaypoint();
            }
            if (par1GuiButton.id == -5) {
                this.getMinecraft().displayGuiScreen(new GuiWaypointsOptions(this, this.options));
            }
            if (par1GuiButton.id == 65336) {
                this.getMinecraft().displayGuiScreen(this.parentScreen);
            }
        }
    }
    
    @Override
    public void confirmClicked(final boolean par1, final int par2) {
        if (this.deleteClicked) {
            this.deleteClicked = false;
            if (par1) {
                this.waypointManager.deleteWaypoint(this.selectedWaypoint);
                this.selectedWaypoint = null;
            }
            this.getMinecraft().displayGuiScreen(this);
        }
        if (this.editClicked) {
            this.editClicked = false;
            if (par1) {
                this.waypointManager.saveWaypoints();
            }
            this.getMinecraft().displayGuiScreen(this);
        }
        if (this.addClicked) {
            this.addClicked = false;
            if (par1) {
                this.waypointManager.addWaypoint(this.newWaypoint);
                this.setSelectedWaypoint(this.newWaypoint);
            }
            this.getMinecraft().displayGuiScreen(this);
        }
    }
    
    protected void setSelectedWaypoint(final Waypoint waypoint) {
        this.selectedWaypoint = waypoint;
        final boolean isSomethingSelected = this.selectedWaypoint != null;
        this.buttonEdit.enabled = isSomethingSelected;
        this.buttonDelete.enabled = isSomethingSelected;
        this.buttonTeleport.enabled = (isSomethingSelected && this.canTeleport());
    }
    
    protected void editWaypoint(final Waypoint waypoint) {
        this.editClicked = true;
        this.getMinecraft().displayGuiScreen(new GuiScreenAddWaypoint(this.master, this, waypoint));
    }
    
    protected void addWaypoint() {
        this.addClicked = true;
        float r;
        float g;
        float b;
        if (this.waypointManager.getWaypoints().size() == 0) {
            r = 0.0f;
            g = 1.0f;
            b = 0.0f;
        }
        else {
            r = this.generator.nextFloat();
            g = this.generator.nextFloat();
            b = this.generator.nextFloat();
        }
        final TreeSet<Integer> dimensions = new TreeSet<Integer>();
        dimensions.add(this.options.game.thePlayer.dimension);
        this.newWaypoint = new Waypoint("", (this.options.game.thePlayer.dimension != -1) ? GameVariableAccessShim.xCoord() : (GameVariableAccessShim.xCoord() * 8), (this.options.game.thePlayer.dimension != -1) ? GameVariableAccessShim.zCoord() : (GameVariableAccessShim.zCoord() * 8), GameVariableAccessShim.yCoord() - 1, true, r, g, b, "", this.master.getWaypointManager().getCurrentSubworldDescriptor(), dimensions);
        this.getMinecraft().displayGuiScreen(new GuiScreenAddWaypoint(this.master, this, this.newWaypoint));
    }
    
    protected void toggleWaypointVisibility() {
        this.selectedWaypoint.enabled = !this.selectedWaypoint.enabled;
        this.waypointManager.saveWaypoints();
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        super.drawMap();
        this.tooltip = null;
        this.waypointList.func_148128_a(par1, par2, par3);
        this.drawCenteredString(this.getFontRenderer(), this.screenTitle, this.getWidth() / 2, 20, 16777215);
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
    
    public boolean canTeleport() {
        final boolean singlePlayer = this.options.game.isIntegratedServerRunning();
        return !singlePlayer || MinecraftServer.getServer().worldServers[0].getWorldInfo().areCommandsAllowed();
    }
    
    @Override
    public void onGuiClosed() {
        if (this.changedSort) {
            super.onGuiClosed();
        }
    }
}
