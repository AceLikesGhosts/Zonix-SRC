package net.minecraft.nbt;

import java.io.*;

public class NBTTagInt extends NBTPrimitive
{
    private int data;
    private static final String __OBFID = "CL_00001223";
    
    NBTTagInt() {
    }
    
    public NBTTagInt(final int p_i45133_1_) {
        this.data = p_i45133_1_;
    }
    
    @Override
    void write(final DataOutput p_74734_1_) throws IOException {
        p_74734_1_.writeInt(this.data);
    }
    
    @Override
    void func_152446_a(final DataInput p_152446_1_, final int p_152446_2_, final NBTSizeTracker p_152446_3_) throws IOException {
        p_152446_3_.func_152450_a(32L);
        this.data = p_152446_1_.readInt();
    }
    
    @Override
    public byte getId() {
        return 3;
    }
    
    @Override
    public String toString() {
        return "" + this.data;
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagInt(this.data);
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (super.equals(p_equals_1_)) {
            final NBTTagInt var2 = (NBTTagInt)p_equals_1_;
            return this.data == var2.data;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ this.data;
    }
    
    @Override
    public long func_150291_c() {
        return this.data;
    }
    
    @Override
    public int func_150287_d() {
        return this.data;
    }
    
    @Override
    public short func_150289_e() {
        return (short)(this.data & 0xFFFF);
    }
    
    @Override
    public byte func_150290_f() {
        return (byte)(this.data & 0xFF);
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
