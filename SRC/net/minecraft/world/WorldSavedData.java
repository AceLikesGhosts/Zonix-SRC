package net.minecraft.world;

import net.minecraft.nbt.*;

public abstract class WorldSavedData
{
    public final String mapName;
    private boolean dirty;
    private static final String __OBFID = "CL_00000580";
    
    public WorldSavedData(final String p_i2141_1_) {
        this.mapName = p_i2141_1_;
    }
    
    public abstract void readFromNBT(final NBTTagCompound p0);
    
    public abstract void writeToNBT(final NBTTagCompound p0);
    
    public void markDirty() {
        this.setDirty(true);
    }
    
    public void setDirty(final boolean p_76186_1_) {
        this.dirty = p_76186_1_;
    }
    
    public boolean isDirty() {
        return this.dirty;
    }
}
