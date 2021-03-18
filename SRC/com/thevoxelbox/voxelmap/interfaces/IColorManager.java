package com.thevoxelbox.voxelmap.interfaces;

import java.awt.image.*;
import java.util.*;

public interface IColorManager
{
    public static final int COLOR_FAILED_LOAD = -65025;
    
    BufferedImage getColorPicker();
    
    BufferedImage getBlockImage(final int p0, final int p1);
    
    boolean checkForChanges();
    
    int colorAdder(final int p0, final int p1);
    
    int colorMultiplier(final int p0, final int p1);
    
    int getBlockColorWithDefaultTint(final int p0, final int p1, final int p2);
    
    int getBlockColor(final int p0, final int p1, final int p2);
    
    void setSkyColor(final int p0);
    
    int getMapImageInt();
    
    Set<Integer> getBiomeTintsAvailable();
    
    boolean isOptifineInstalled();
    
    HashMap<String, Integer[]> getBlockTintTables();
    
    int getAirColor();
}
