package com.thevoxelbox.voxelmap.util;

public class Dimension
{
    public String name;
    public int ID;
    
    public Dimension(final String name, final int ID) {
        this.name = "notLoaded";
        this.ID = -10;
        this.name = name;
        this.ID = ID;
    }
}
