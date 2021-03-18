package com.thevoxelbox.voxelmap.util;

import net.minecraft.client.resources.*;
import java.util.*;
import com.google.common.collect.*;

public class AddonDefaultResourcePack extends DefaultResourcePack
{
    public static Set defaultResourceDomains;
    private final Map mapResourceFiles;
    
    public AddonDefaultResourcePack(final Map map, final String domain) {
        super(map);
        AddonDefaultResourcePack.defaultResourceDomains = (Set)ImmutableSet.of((Object)domain);
        this.mapResourceFiles = map;
    }
    
    static {
        AddonDefaultResourcePack.defaultResourceDomains = null;
    }
}
