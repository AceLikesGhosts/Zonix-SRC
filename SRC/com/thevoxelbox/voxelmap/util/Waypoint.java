package com.thevoxelbox.voxelmap.util;

import java.io.*;
import java.util.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;

public class Waypoint implements Serializable, Comparable
{
    private static final long serialVersionUID = 8136790917447997951L;
    public String name;
    public String imageSuffix;
    public String world;
    public boolean isAutomated;
    public TreeSet<Integer> dimensions;
    public int x;
    public int z;
    public int y;
    public boolean enabled;
    public boolean inWorld;
    public boolean inDimension;
    public float red;
    public float green;
    public float blue;
    
    public Waypoint(final String name, final int x, final int z, final int y, final boolean enabled, final float red, final float green, final float blue, final String suffix, final String world, final TreeSet<Integer> dimensions, final boolean isAutomated) {
        this(name, x, z, y, enabled, red, green, blue, suffix, world, dimensions);
        this.isAutomated = isAutomated;
        if (!dimensions.contains(Minecraft.getMinecraft().thePlayer.dimension)) {
            this.inDimension = false;
        }
    }
    
    public Waypoint(final String name, final int x, final int z, final int y, final boolean enabled, final float red, final float green, final float blue, final String suffix, final String world, final TreeSet<Integer> dimensions) {
        this.imageSuffix = "";
        this.world = "";
        this.isAutomated = false;
        this.dimensions = new TreeSet<Integer>();
        this.inWorld = true;
        this.inDimension = true;
        this.red = 0.0f;
        this.green = 1.0f;
        this.blue = 0.0f;
        this.name = name;
        this.x = x;
        this.z = z;
        this.y = y;
        this.enabled = enabled;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.imageSuffix = suffix;
        this.world = world;
        this.dimensions = dimensions;
    }
    
    public int getUnified() {
        return -16777216 + ((int)(this.red * 255.0f) << 16) + ((int)(this.green * 255.0f) << 8) + (int)(this.blue * 255.0f);
    }
    
    public boolean isActive() {
        return this.enabled && this.inWorld && this.inDimension;
    }
    
    public int getX() {
        return (Minecraft.getMinecraft().thePlayer.dimension == -1) ? (this.x / 8) : this.x;
    }
    
    public void setX(final int x) {
        this.x = ((Minecraft.getMinecraft().thePlayer.dimension == -1) ? (x * 8) : x);
    }
    
    public int getZ() {
        return (Minecraft.getMinecraft().thePlayer.dimension == -1) ? (this.z / 8) : this.z;
    }
    
    public void setZ(final int z) {
        this.z = ((Minecraft.getMinecraft().thePlayer.dimension == -1) ? (z * 8) : z);
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    @Override
    public int compareTo(final Object arg0) {
        final double myDistance = this.getDistanceSqToEntity(Minecraft.getMinecraft().thePlayer);
        final double comparedDistance = ((Waypoint)arg0).getDistanceSqToEntity(Minecraft.getMinecraft().thePlayer);
        return Double.compare(myDistance, comparedDistance);
    }
    
    public double getDistanceSqToEntity(final Entity par1Entity) {
        final double var2 = this.getX() - par1Entity.posX;
        final double var3 = this.getY() - par1Entity.posY;
        final double var4 = this.getZ() - par1Entity.posZ;
        return var2 * var2 + var3 * var3 + var4 * var4;
    }
}
