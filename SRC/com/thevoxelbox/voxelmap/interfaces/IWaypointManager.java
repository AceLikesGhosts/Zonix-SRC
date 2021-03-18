package com.thevoxelbox.voxelmap.interfaces;

import java.util.*;
import com.thevoxelbox.voxelmap.util.*;

public interface IWaypointManager
{
    ArrayList<Waypoint> getWaypoints();
    
    void deleteWaypoint(final Waypoint p0);
    
    void saveWaypoints();
    
    void addWaypoint(final Waypoint p0);
    
    void check2dWaypoints();
    
    void handleDeath();
    
    void loadWaypoints();
    
    void moveWaypointEntityToBack();
    
    void newSubWorldName(final String p0);
    
    void newSubWorldHash(final String p0);
    
    void newWorld(final int p0);
    
    String getCurrentSubworldDescriptor();
}
