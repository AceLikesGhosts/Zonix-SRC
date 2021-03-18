package com.thevoxelbox.voxelmap.util;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import java.util.*;

public class EntityWaypointContainer extends Entity
{
    public ArrayList<Waypoint> wayPts;
    private Waypoint waypoint;
    private boolean inNether;
    
    public EntityWaypointContainer(final World par1World) {
        super(par1World);
        this.wayPts = new ArrayList<Waypoint>();
        this.inNether = false;
        this.ignoreFrustumCheck = true;
    }
    
    @Override
    public void onUpdate() {
        this.sortWaypoints();
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
    }
    
    @Override
    public boolean isInRangeToRender3d(final double x, final double y, final double z) {
        return true;
    }
    
    @Override
    public int getBrightnessForRender(final float par1) {
        return 15728880;
    }
    
    @Override
    public float getBrightness(final float par1) {
        return 1.0f;
    }
    
    public void addWaypoint(final Waypoint newWaypoint) {
        this.wayPts.add(newWaypoint);
    }
    
    public void removeWaypoint(final Waypoint point) {
        this.wayPts.remove(point);
    }
    
    public void sortWaypoints() {
        Collections.sort(this.wayPts, Collections.reverseOrder());
    }
}
