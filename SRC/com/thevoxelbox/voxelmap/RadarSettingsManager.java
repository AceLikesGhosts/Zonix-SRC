package com.thevoxelbox.voxelmap;

import net.minecraft.client.*;
import java.io.*;
import java.util.*;
import com.thevoxelbox.voxelmap.gui.overridden.*;
import com.thevoxelbox.voxelmap.util.*;

public class RadarSettingsManager
{
    public Minecraft game;
    public boolean show;
    public boolean showHostiles;
    public boolean showPlayers;
    public boolean showNeutrals;
    public boolean showPlayerNames;
    public boolean outlines;
    public boolean filtering;
    public boolean showHelmetsPlayers;
    public boolean showHelmetsMobs;
    public boolean randomobs;
    float fontScale;
    private boolean somethingChanged;
    
    public RadarSettingsManager() {
        this.show = true;
        this.showHostiles = true;
        this.showPlayers = true;
        this.showNeutrals = false;
        this.showPlayerNames = true;
        this.outlines = true;
        this.filtering = true;
        this.showHelmetsPlayers = true;
        this.showHelmetsMobs = true;
        this.randomobs = true;
        this.fontScale = 1.0f;
        this.game = Minecraft.getMinecraft();
    }
    
    public void loadSettings(final File settingsFile) {
        try {
            final BufferedReader in = new BufferedReader(new FileReader(settingsFile));
            String sCurrentLine;
            while ((sCurrentLine = in.readLine()) != null) {
                final String[] curLine = sCurrentLine.split(":");
                if (curLine[0].equals("Show Radar")) {
                    this.show = Boolean.parseBoolean(curLine[1]);
                }
                else if (curLine[0].equals("Show Hostiles")) {
                    this.showHostiles = Boolean.parseBoolean(curLine[1]);
                }
                else if (curLine[0].equals("Show Players")) {
                    this.showPlayers = Boolean.parseBoolean(curLine[1]);
                }
                else if (curLine[0].equals("Show Neutrals")) {
                    this.showNeutrals = Boolean.parseBoolean(curLine[1]);
                }
                else if (curLine[0].equals("Filter Mob Icons")) {
                    this.filtering = Boolean.parseBoolean(curLine[1]);
                }
                else if (curLine[0].equals("Outline Mob Icons")) {
                    this.outlines = Boolean.parseBoolean(curLine[1]);
                }
                else if (curLine[0].equals("Show Player Helmets")) {
                    this.showHelmetsPlayers = Boolean.parseBoolean(curLine[1]);
                }
                else if (curLine[0].equals("Show Mob Helmets")) {
                    this.showHelmetsMobs = Boolean.parseBoolean(curLine[1]);
                }
                else if (curLine[0].equals("Show Player Names")) {
                    this.showPlayerNames = Boolean.parseBoolean(curLine[1]);
                }
                else if (curLine[0].equals("Font Scale")) {
                    this.fontScale = Float.parseFloat(curLine[1]);
                }
                else if (curLine[0].equals("Randomobs")) {
                    this.randomobs = Boolean.parseBoolean(curLine[1]);
                }
                else {
                    if (!curLine[0].equals("Hidden Mobs")) {
                        continue;
                    }
                    this.applyHiddenMobSettings(curLine[1]);
                }
            }
            in.close();
        }
        catch (Exception ex) {}
    }
    
    private void applyHiddenMobSettings(final String hiddenMobs) {
        final String[] mobs = hiddenMobs.split(",");
        for (int t = 0; t < mobs.length; ++t) {
            final EnumMobs mob = EnumMobs.getMobByName(mobs[t]);
            if (mob != null) {
                mob.enabled = false;
            }
            else {
                CustomMobsManager.add(mobs[t], false);
            }
        }
    }
    
    public void saveAll(final PrintWriter out) {
        out.println("Show Radar:" + Boolean.toString(this.show));
        out.println("Show Hostiles:" + Boolean.toString(this.showHostiles));
        out.println("Show Players:" + Boolean.toString(this.showPlayers));
        out.println("Show Neutrals:" + Boolean.toString(this.showNeutrals));
        out.println("Filter Mob Icons:" + Boolean.toString(this.filtering));
        out.println("Outline Mob Icons:" + Boolean.toString(this.outlines));
        out.println("Show Player Helmets:" + Boolean.toString(this.showHelmetsPlayers));
        out.println("Show Mob Helmets:" + Boolean.toString(this.showHelmetsMobs));
        out.println("Show Player Names:" + Boolean.toString(this.showPlayerNames));
        out.println("Font Scale:" + Float.toString(this.fontScale));
        out.println("Randomobs:" + Boolean.toString(this.randomobs));
        out.print("Hidden Mobs:");
        for (final EnumMobs mob : EnumMobs.values()) {
            if (mob.isTopLevelUnit && !mob.enabled) {
                out.print(mob.name + ",");
            }
        }
        for (final CustomMob mob2 : CustomMobsManager.mobs) {
            if (!mob2.enabled) {
                out.print(mob2.name + ",");
            }
        }
        out.println();
    }
    
    public String getKeyText(final EnumOptionsMinimap par1EnumOptions) {
        final String s = I18nUtils.getString(par1EnumOptions.getEnumString()) + ": ";
        if (!par1EnumOptions.getEnumBoolean()) {
            return s;
        }
        final boolean flag = this.getOptionBooleanValue(par1EnumOptions);
        if (flag) {
            return s + I18nUtils.getString("options.on");
        }
        return s + I18nUtils.getString("options.off");
    }
    
    public boolean getOptionBooleanValue(final EnumOptionsMinimap par1EnumOptions) {
        switch (par1EnumOptions) {
            case SHOWRADAR: {
                return this.show;
            }
            case SHOWHOSTILES: {
                return this.showHostiles;
            }
            case SHOWPLAYERS: {
                return this.showPlayers;
            }
            case SHOWNEUTRALS: {
                return this.showNeutrals;
            }
            case SHOWPLAYERHELMETS: {
                return this.showHelmetsPlayers;
            }
            case SHOWMOBHELMETS: {
                return this.showHelmetsMobs;
            }
            case SHOWPLAYERNAMES: {
                return this.showPlayerNames;
            }
            case RADAROUTLINES: {
                return this.outlines;
            }
            case RADARFILTERING: {
                return this.filtering;
            }
            case RANDOMOBS: {
                return this.randomobs;
            }
            default: {
                throw new IllegalArgumentException("Add code to handle EnumOptionMinimap: " + par1EnumOptions.getEnumString() + ". (possibly not a boolean)");
            }
        }
    }
    
    public void setOptionValue(final EnumOptionsMinimap par1EnumOptions, final int i) {
        switch (par1EnumOptions) {
            case SHOWRADAR: {
                this.show = !this.show;
                break;
            }
            case SHOWHOSTILES: {
                this.showHostiles = !this.showHostiles;
                break;
            }
            case SHOWPLAYERS: {
                this.showPlayers = !this.showPlayers;
                break;
            }
            case SHOWNEUTRALS: {
                this.showNeutrals = !this.showNeutrals;
                break;
            }
            case SHOWPLAYERHELMETS: {
                this.showHelmetsPlayers = !this.showHelmetsPlayers;
                break;
            }
            case SHOWMOBHELMETS: {
                this.showHelmetsMobs = !this.showHelmetsMobs;
                break;
            }
            case SHOWPLAYERNAMES: {
                this.showPlayerNames = !this.showPlayerNames;
                break;
            }
            case RADAROUTLINES: {
                this.outlines = !this.outlines;
                break;
            }
            case RADARFILTERING: {
                this.filtering = !this.filtering;
                break;
            }
            case RANDOMOBS: {
                this.randomobs = !this.randomobs;
                break;
            }
            default: {
                throw new IllegalArgumentException("Add code to handle EnumOptionMinimap: " + par1EnumOptions.getEnumString());
            }
        }
        this.somethingChanged = true;
    }
    
    public boolean isChanged() {
        if (this.somethingChanged) {
            this.somethingChanged = false;
            return true;
        }
        return false;
    }
}
