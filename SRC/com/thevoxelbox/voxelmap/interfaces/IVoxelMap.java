package com.thevoxelbox.voxelmap.interfaces;

import com.thevoxelbox.voxelmap.*;

public interface IVoxelMap
{
    IObservableChunkChangeNotifier getNotifier();
    
    MapSettingsManager getMapOptions();
    
    RadarSettingsManager getRadarOptions();
    
    IMap getMap();
    
    IRadar getRadar();
    
    IColorManager getColorManager();
    
    IWaypointManager getWaypointManager();
    
    IDimensionManager getDimensionManager();
    
    void setPermissions(final boolean p0, final boolean p1);
    
    void newSubWorldName(final String p0);
    
    void newSubWorldHash(final String p0);
}
