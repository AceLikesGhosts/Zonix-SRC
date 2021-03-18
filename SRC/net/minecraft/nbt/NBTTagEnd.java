package net.minecraft.nbt;

import java.io.*;

public class NBTTagEnd extends NBTBase
{
    private static final String __OBFID = "CL_00001219";
    
    @Override
    void func_152446_a(final DataInput p_152446_1_, final int p_152446_2_, final NBTSizeTracker p_152446_3_) throws IOException {
    }
    
    @Override
    void write(final DataOutput p_74734_1_) throws IOException {
    }
    
    @Override
    public byte getId() {
        return 0;
    }
    
    @Override
    public String toString() {
        return "END";
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagEnd();
    }
}
