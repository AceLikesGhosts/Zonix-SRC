package com.thevoxelbox.voxelmap.util;

public class LayoutVariables
{
    public int scScale;
    public int mapX;
    public int mapY;
    public int zoom;
    
    public LayoutVariables() {
        this.scScale = 0;
        this.mapX = 0;
        this.mapY = 0;
        this.zoom = 0;
    }
    
    public void updateVars(final int scScale, final int mapX, final int mapY, final int zoom) {
        this.scScale = scScale;
        this.mapX = mapX;
        this.mapY = mapY;
        this.zoom = zoom;
    }
}
