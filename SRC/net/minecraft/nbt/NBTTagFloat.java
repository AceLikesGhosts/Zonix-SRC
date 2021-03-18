package net.minecraft.nbt;

import java.io.*;
import net.minecraft.util.*;

public class NBTTagFloat extends NBTPrimitive
{
    private float data;
    private static final String __OBFID = "CL_00001220";
    
    NBTTagFloat() {
    }
    
    public NBTTagFloat(final float p_i45131_1_) {
        this.data = p_i45131_1_;
    }
    
    @Override
    void write(final DataOutput p_74734_1_) throws IOException {
        p_74734_1_.writeFloat(this.data);
    }
    
    @Override
    void func_152446_a(final DataInput p_152446_1_, final int p_152446_2_, final NBTSizeTracker p_152446_3_) throws IOException {
        p_152446_3_.func_152450_a(32L);
        this.data = p_152446_1_.readFloat();
    }
    
    @Override
    public byte getId() {
        return 5;
    }
    
    @Override
    public String toString() {
        return "" + this.data + "f";
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagFloat(this.data);
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (super.equals(p_equals_1_)) {
            final NBTTagFloat var2 = (NBTTagFloat)p_equals_1_;
            return this.data == var2.data;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ Float.floatToIntBits(this.data);
    }
    
    @Override
    public long func_150291_c() {
        return (long)this.data;
    }
    
    @Override
    public int func_150287_d() {
        return MathHelper.floor_float(this.data);
    }
    
    @Override
    public short func_150289_e() {
        return (short)(MathHelper.floor_float(this.data) & 0xFFFF);
    }
    
    @Override
    public byte func_150290_f() {
        return (byte)(MathHelper.floor_float(this.data) & 0xFF);
    }
    
    @Override
    public double func_150286_g() {
        return this.data;
    }
    
    @Override
    public float func_150288_h() {
        return this.data;
    }
}
