package com.thevoxelbox.voxelmap.util;

import java.io.*;
import net.minecraft.util.*;

public class FilesystemUtils
{
    public static File getAppDir(final String par0Str, final boolean createIfNotExist) {
        final String var1 = System.getProperty("user.home", ".");
        File var2 = null;
        switch (getOs().ordinal()) {
            case 0:
            case 1: {
                var2 = new File(var1, '.' + par0Str + '/');
                break;
            }
            case 2: {
                final String var3 = System.getenv("APPDATA");
                if (var3 != null) {
                    var2 = new File(var3, "." + par0Str + '/');
                    break;
                }
                var2 = new File(var1, '.' + par0Str + '/');
                break;
            }
            case 3: {
                var2 = new File(var1, "Library/Application Support/" + par0Str);
                break;
            }
            default: {
                var2 = new File(var1, par0Str + '/');
                break;
            }
        }
        if (createIfNotExist && !var2.exists() && !var2.mkdirs()) {
            throw new RuntimeException("The working directory could not be created: " + var2);
        }
        return var2;
    }
    
    public static Util.EnumOS getOs() {
        final String var0 = System.getProperty("os.name").toLowerCase();
        return var0.contains("unix") ? Util.EnumOS.LINUX : (var0.contains("linux") ? Util.EnumOS.LINUX : (var0.contains("sunos") ? Util.EnumOS.SOLARIS : (var0.contains("solaris") ? Util.EnumOS.SOLARIS : (var0.contains("mac") ? Util.EnumOS.OSX : (var0.contains("win") ? Util.EnumOS.WINDOWS : Util.EnumOS.UNKNOWN)))));
    }
}
