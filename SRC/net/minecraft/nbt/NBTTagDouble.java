package net.minecraft.nbt;

import java.io.*;
import net.minecraft.util.*;

public class NBTTagDouble extends NBTPrimitive
{
    private double data;
    private static final String __OBFID = "CL_00001218";
    
    NBTTagDouble() {
    }
    
    public NBTTagDouble(final double p_i45130_1_) {
        this.data = p_i45130_1_;
    }
    
    @Override
    void write(final DataOutput p_74734_1_) throws IOException {
        p_74734_1_.writeDouble(this.data);
    }
    
    @Override
    void func_152446_a(final DataInput p_152446_1_, final int p_152446_2_, final NBTSizeTracker p_152446_3_) throws IOException {
        p_152446_3_.func_152450_a(64L);
        this.data = p_152446_1_.readDouble();
    }
    
    @Override
    public byte getId() {
        return 6;
    }
    
    @Override
    public String toString() {
        return "" + this.data + "d";
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagDouble(this.data);
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (super.equals(p_equals_1_)) {
            final NBTTagDouble var2 = (NBTTagDouble)p_equals_1_;
            return this.data == var2.data;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        final long var1 = Double.doubleToLongBits(this.data);
        return super.hashCode() ^ (int)(var1 ^ var1 >>> 32);
    }
    
    @Override
    public long func_150291_c() {
        return (long)Math.floor(this.data);
    }
    
    @Override
    public int func_150287_d() {
        return MathHelper.floor_double(this.data);
    }
    
    @Override
    public short func_150289_e() {
        return (short)(MathHelper.floor_double(this.data) & 0xFFFF);
    }
    
    @Override
    public byte func_150290_f() {
        return (byte)(MathHelper.floor_double(this.data) & 0xFF);
    }
    
    @Override
    public double func_150286_g() {
        return this.data;
    }
    
    @Override
    public float func_150288_h() {
        return (float)this.data;
    }
}
