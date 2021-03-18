package com.thevoxelbox.voxelmap.util;

public class CustomMob
{
    public String name;
    public boolean enabled;
    public boolean isHostile;
    public boolean isNeutral;
    
    public CustomMob(final String name, final boolean enabled) {
        this.name = "notLoaded";
        this.enabled = true;
        this.isHostile = false;
        this.isNeutral = false;
        this.name = name;
        this.enabled = enabled;
    }
    
    public CustomMob(final String name, final boolean isHostile, final boolean isNeutral) {
        this.name = "notLoaded";
        this.enabled = true;
        this.isHostile = false;
        this.isNeutral = false;
        this.name = name;
        this.isHostile = isHostile;
        this.isNeutral = isNeutral;
    }
}
