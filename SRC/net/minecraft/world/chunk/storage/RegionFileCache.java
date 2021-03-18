package net.minecraft.world.chunk.storage;

import java.io.*;
import java.util.*;

public class RegionFileCache
{
    private static final Map regionsByFilename;
    private static final String __OBFID = "CL_00000383";
    
    public static synchronized RegionFile createOrLoadRegionFile(final File p_76550_0_, final int p_76550_1_, final int p_76550_2_) {
        final File var3 = new File(p_76550_0_, "region");
        final File var4 = new File(var3, "r." + (p_76550_1_ >> 5) + "." + (p_76550_2_ >> 5) + ".mca");
        final RegionFile var5 = RegionFileCache.regionsByFilename.get(var4);
        if (var5 != null) {
            return var5;
        }
        if (!var3.exists()) {
            var3.mkdirs();
        }
        if (RegionFileCache.regionsByFilename.size() >= 256) {
            clearRegionFileReferences();
        }
        final RegionFile var6 = new RegionFile(var4);
        RegionFileCache.regionsByFilename.put(var4, var6);
        return var6;
    }
    
    public static synchronized void clearRegionFileReferences() {
        for (final RegionFile var2 : RegionFileCache.regionsByFilename.values()) {
            try {
                if (var2 == null) {
                    continue;
                }
                var2.close();
            }
            catch (IOException var3) {
                var3.printStackTrace();
            }
        }
        RegionFileCache.regionsByFilename.clear();
    }
    
    public static DataInputStream getChunkInputStream(final File p_76549_0_, final int p_76549_1_, final int p_76549_2_) {
        final RegionFile var3 = createOrLoadRegionFile(p_76549_0_, p_76549_1_, p_76549_2_);
        return var3.getChunkDataInputStream(p_76549_1_ & 0x1F, p_76549_2_ & 0x1F);
    }
    
    public static DataOutputStream getChunkOutputStream(final File p_76552_0_, final int p_76552_1_, final int p_76552_2_) {
        final RegionFile var3 = createOrLoadRegionFile(p_76552_0_, p_76552_1_, p_76552_2_);
        return var3.getChunkDataOutputStream(p_76552_1_ & 0x1F, p_76552_2_ & 0x1F);
    }
    
    static {
        regionsByFilename = new HashMap();
    }
}
