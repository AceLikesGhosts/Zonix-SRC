package net.minecraft.nbt;

import java.io.*;

public class NBTTagString extends NBTBase
{
    private String data;
    private static final String __OBFID = "CL_00001228";
    
    public NBTTagString() {
        this.data = "";
    }
    
    public NBTTagString(final String p_i1389_1_) {
        this.data = p_i1389_1_;
        if (p_i1389_1_ == null) {
            throw new IllegalArgumentException("Empty string not allowed");
        }
    }
    
    @Override
    void write(final DataOutput p_74734_1_) throws IOException {
        p_74734_1_.writeUTF(this.data);
    }
    
    @Override
    void func_152446_a(final DataInput p_152446_1_, final int p_152446_2_, final NBTSizeTracker p_152446_3_) throws IOException {
        this.data = p_152446_1_.readUTF();
        p_152446_3_.func_152450_a(16 * this.data.length());
    }
    
    @Override
    public byte getId() {
        return 8;
    }
    
    @Override
    public String toString() {
        return "\"" + this.data + "\"";
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagString(this.data);
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (!super.equals(p_equals_1_)) {
            return false;
        }
        final NBTTagString var2 = (NBTTagString)p_equals_1_;
        return (this.data == null && var2.data == null) || (this.data != null && this.data.equals(var2.data));
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ this.data.hashCode();
    }
    
    public String func_150285_a_() {
        return this.data;
    }
}
