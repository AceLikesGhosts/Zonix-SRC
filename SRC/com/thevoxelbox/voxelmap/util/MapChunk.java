package com.thevoxelbox.voxelmap.util;

import net.minecraft.world.chunk.*;
import net.minecraft.client.*;
import com.thevoxelbox.voxelmap.interfaces.*;

public class MapChunk
{
    private int x;
    private int z;
    private int width;
    private int height;
    private Chunk chunk;
    private boolean isLoaded;
    
    public MapChunk(final int x, final int z) {
        this.x = 0;
        this.z = 0;
        this.isLoaded = false;
        this.x = x;
        this.z = z;
        this.chunk = Minecraft.getMinecraft().theWorld.getChunkFromChunkCoords(x, z);
        this.isLoaded = this.chunk.isChunkLoaded;
    }
    
    public void calculateChunk(final IMap minimap, final boolean realTimeUpdate) {
        if (this.hasChunkLoadedOrUnloaded() || (realTimeUpdate && this.hasChunkChanged())) {
            minimap.chunkCalc(this.chunk);
            if (realTimeUpdate) {
                this.chunk.isModified = false;
            }
        }
    }
    
    private boolean hasChunkLoadedOrUnloaded() {
        boolean hasChanged = false;
        if (!this.isLoaded) {
            this.chunk = Minecraft.getMinecraft().theWorld.getChunkFromChunkCoords(this.x, this.z);
            if (this.chunk.isChunkLoaded) {
                this.isLoaded = true;
                hasChanged = true;
            }
        }
        else if (this.isLoaded && !this.chunk.isChunkLoaded) {
            this.isLoaded = false;
            hasChanged = true;
        }
        return hasChanged;
    }
    
    private boolean hasChunkChanged() {
        boolean hasChanged = false;
        if (this.chunk.isChunkLoaded && this.chunk.isModified) {
            hasChanged = true;
        }
        return hasChanged;
    }
}
