package net.minecraft.nbt;

import java.io.*;

public abstract class NBTBase
{
    public static final String[] NBTTypes;
    private static final String __OBFID = "CL_00001229";
    
    abstract void write(final DataOutput p0) throws IOException;
    
    abstract void func_152446_a(final DataInput p0, final int p1, final NBTSizeTracker p2) throws IOException;
    
    @Override
    public abstract String toString();
    
    public abstract byte getId();
    
    protected static NBTBase func_150284_a(final byte p_150284_0_) {
        switch (p_150284_0_) {
            case 0: {
                return new NBTTagEnd();
            }
            case 1: {
                return new NBTTagByte();
            }
            case 2: {
                return new NBTTagShort();
            }
            case 3: {
                return new NBTTagInt();
            }
            case 4: {
                return new NBTTagLong();
            }
            case 5: {
                return new NBTTagFloat();
            }
            case 6: {
                return new NBTTagDouble();
            }
            case 7: {
                return new NBTTagByteArray();
            }
            case 8: {
                return new NBTTagString();
            }
            case 9: {
                return new NBTTagList();
            }
            case 10: {
                return new NBTTagCompound();
            }
            case 11: {
                return new NBTTagIntArray();
            }
            default: {
                return null;
            }
        }
    }
    
    public abstract NBTBase copy();
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (!(p_equals_1_ instanceof NBTBase)) {
            return false;
        }
        final NBTBase var2 = (NBTBase)p_equals_1_;
        return this.getId() == var2.getId();
    }
    
    @Override
    public int hashCode() {
        return this.getId();
    }
    
    protected String func_150285_a_() {
        return this.toString();
    }
    
    static {
        NBTTypes = new String[] { "END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]" };
    }
    
    public abstract static class NBTPrimitive extends NBTBase
    {
        private static final String __OBFID = "CL_00001230";
        
        public abstract long func_150291_c();
        
        public abstract int func_150287_d();
        
        public abstract short func_150289_e();
        
        public abstract byte func_150290_f();
        
        public abstract double func_150286_g();
        
        public abstract float func_150288_h();
    }
}
