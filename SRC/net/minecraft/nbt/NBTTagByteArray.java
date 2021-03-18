package net.minecraft.nbt;

import java.io.*;
import java.util.*;

public class NBTTagByteArray extends NBTBase
{
    private byte[] byteArray;
    private static final String __OBFID = "CL_00001213";
    
    NBTTagByteArray() {
    }
    
    public NBTTagByteArray(final byte[] p_i45128_1_) {
        this.byteArray = p_i45128_1_;
    }
    
    @Override
    void write(final DataOutput p_74734_1_) throws IOException {
        p_74734_1_.writeInt(this.byteArray.length);
        p_74734_1_.write(this.byteArray);
    }
    
    @Override
    void func_152446_a(final DataInput p_152446_1_, final int p_152446_2_, final NBTSizeTracker p_152446_3_) throws IOException {
        final int var4 = p_152446_1_.readInt();
        p_152446_3_.func_152450_a(8 * var4);
        p_152446_1_.readFully(this.byteArray = new byte[var4]);
    }
    
    @Override
    public byte getId() {
        return 7;
    }
    
    @Override
    public String toString() {
        return "[" + this.byteArray.length + " bytes]";
    }
    
    @Override
    public NBTBase copy() {
        final byte[] var1 = new byte[this.byteArray.length];
        System.arraycopy(this.byteArray, 0, var1, 0, this.byteArray.length);
        return new NBTTagByteArray(var1);
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        return super.equals(p_equals_1_) && Arrays.equals(this.byteArray, ((NBTTagByteArray)p_equals_1_).byteArray);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.byteArray);
    }
    
    public byte[] func_150292_c() {
        return this.byteArray;
    }
}
