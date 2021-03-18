package net.minecraft.src;

import net.minecraft.client.resources.*;
import java.io.*;

public class ResourceUtils
{
    private static boolean directAccessValid;
    
    public static File getResourcePackFile(final AbstractResourcePack arp) {
        return arp.resourcePackFile;
    }
    
    static {
        ResourceUtils.directAccessValid = true;
    }
}
