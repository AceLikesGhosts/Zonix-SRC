package net.minecraft.nbt;

import java.io.*;

public class NBTTagByte extends NBTPrimitive
{
    private byte data;
    private static final String __OBFID = "CL_00001214";
    
    NBTTagByte() {
    }
    
    public NBTTagByte(final byte p_i45129_1_) {
        this.data = p_i45129_1_;
    }
    
    @Override
    void write(final DataOutput p_74734_1_) throws IOException {
        p_74734_1_.writeByte(this.data);
    }
    
    @Override
    void func_152446_a(final DataInput p_152446_1_, final int p_152446_2_, final NBTSizeTracker p_152446_3_) throws IOException {
        p_152446_3_.func_152450_a(8L);
        this.data = p_152446_1_.readByte();
    }
    
    @Override
    public byte getId() {
        return 1;
    }
    
    @Override
    public String toString() {
        return "" + this.data + "b";
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagByte(this.data);
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (super.equals(p_equals_1_)) {
            final NBTTagByte var2 = (NBTTagByte)p_equals_1_;
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
        return this.data;
    }
    
    @Override
    public byte func_150290_f() {
        return this.data;
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
