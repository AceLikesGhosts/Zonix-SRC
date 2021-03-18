package net.minecraft.nbt;

import java.io.*;

public class NBTTagLong extends NBTPrimitive
{
    private long data;
    private static final String __OBFID = "CL_00001225";
    
    NBTTagLong() {
    }
    
    public NBTTagLong(final long p_i45134_1_) {
        this.data = p_i45134_1_;
    }
    
    @Override
    void write(final DataOutput p_74734_1_) throws IOException {
        p_74734_1_.writeLong(this.data);
    }
    
    @Override
    void func_152446_a(final DataInput p_152446_1_, final int p_152446_2_, final NBTSizeTracker p_152446_3_) throws IOException {
        p_152446_3_.func_152450_a(64L);
        this.data = p_152446_1_.readLong();
    }
    
    @Override
    public byte getId() {
        return 4;
    }
    
    @Override
    public String toString() {
        return "" + this.data + "L";
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagLong(this.data);
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (super.equals(p_equals_1_)) {
            final NBTTagLong var2 = (NBTTagLong)p_equals_1_;
            return this.data == var2.data;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ (int)(this.data ^ this.data >>> 32);
    }
    
    @Override
    public long func_150291_c() {
        return this.data;
    }
    
    @Override
    public int func_150287_d() {
        return (int)(this.data & -1L);
    }
    
    @Override
    public short func_150289_e() {
        return (short)(this.data & 0xFFFFL);
    }
    
    @Override
    public byte func_150290_f() {
        return (byte)(this.data & 0xFFL);
    }
    
    @Override
    public double func_150286_g() {
        return (double)this.data;
    }
    
    @Override
    public float func_150288_h() {
        return (float)this.data;
    }
}
