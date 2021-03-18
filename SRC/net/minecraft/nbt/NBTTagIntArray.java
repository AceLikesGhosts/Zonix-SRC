package net.minecraft.nbt;

import java.io.*;
import java.util.*;

public class NBTTagIntArray extends NBTBase
{
    private int[] intArray;
    private static final String __OBFID = "CL_00001221";
    
    NBTTagIntArray() {
    }
    
    public NBTTagIntArray(final int[] p_i45132_1_) {
        this.intArray = p_i45132_1_;
    }
    
    @Override
    void write(final DataOutput p_74734_1_) throws IOException {
        p_74734_1_.writeInt(this.intArray.length);
        for (int var2 = 0; var2 < this.intArray.length; ++var2) {
            p_74734_1_.writeInt(this.intArray[var2]);
        }
    }
    
    @Override
    void func_152446_a(final DataInput p_152446_1_, final int p_152446_2_, final NBTSizeTracker p_152446_3_) throws IOException {
        final int var4 = p_152446_1_.readInt();
        p_152446_3_.func_152450_a(32 * var4);
        this.intArray = new int[var4];
        for (int var5 = 0; var5 < var4; ++var5) {
            this.intArray[var5] = p_152446_1_.readInt();
        }
    }
    
    @Override
    public byte getId() {
        return 11;
    }
    
    @Override
    public String toString() {
        String var1 = "[";
        for (final int var5 : this.intArray) {
            var1 = var1 + var5 + ",";
        }
        return var1 + "]";
    }
    
    @Override
    public NBTBase copy() {
        final int[] var1 = new int[this.intArray.length];
        System.arraycopy(this.intArray, 0, var1, 0, this.intArray.length);
        return new NBTTagIntArray(var1);
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        return super.equals(p_equals_1_) && Arrays.equals(this.intArray, ((NBTTagIntArray)p_equals_1_).intArray);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.intArray);
    }
    
    public int[] func_150302_c() {
        return this.intArray;
    }
}
