package com.thevoxelbox.voxelmap.util;

import java.util.*;

public class CustomMobsManager
{
    public static ArrayList<CustomMob> mobs;
    
    public static void add(final String name, final boolean enabled) {
        final CustomMob mob = getCustomMobByName(name);
        if (mob != null) {
            mob.enabled = enabled;
        }
        else {
            CustomMobsManager.mobs.add(new CustomMob(name, enabled));
        }
    }
    
    public static void add(final String name, final boolean isHostile, final boolean isNeutral) {
        final CustomMob mob = getCustomMobByName(name);
        if (mob != null) {
            mob.isHostile = isHostile;
            mob.isNeutral = isNeutral;
        }
        else {
            CustomMobsManager.mobs.add(new CustomMob(name, isHostile, isNeutral));
        }
    }
    
    public static CustomMob getCustomMobByName(final String name) {
        for (final CustomMob mob : CustomMobsManager.mobs) {
            if (mob.name.equals(name)) {
                return mob;
            }
        }
        return null;
    }
    
    static {
        CustomMobsManager.mobs = new ArrayList<CustomMob>();
    }
}
