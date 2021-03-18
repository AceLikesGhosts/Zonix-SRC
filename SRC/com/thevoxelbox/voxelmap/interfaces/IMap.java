package com.thevoxelbox.voxelmap.interfaces;

import net.minecraft.client.*;
import net.minecraft.world.chunk.*;

public interface IMap
{
    String getCurrentWorldName();
    
    void forceFullRender(final boolean p0);
    
    void drawMinimap(final Minecraft p0);
    
    void chunkCalc(final Chunk p0);
    
    float getPercentX();
    
    float getPercentY();
    
    void onTickInGame(final Minecraft p0);
    
    void setPermissions(final boolean p0, final boolean p1);
}
