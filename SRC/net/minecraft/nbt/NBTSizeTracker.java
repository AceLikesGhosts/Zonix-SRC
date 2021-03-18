package net.minecraft.nbt;

public class NBTSizeTracker
{
    public static final NBTSizeTracker field_152451_a;
    private final long field_152452_b;
    private long field_152453_c;
    private static final String __OBFID = "CL_00001903";
    
    public NBTSizeTracker(final long p_i46342_1_) {
        this.field_152452_b = p_i46342_1_;
    }
    
    public void func_152450_a(final long p_152450_1_) {
        this.field_152453_c += p_152450_1_ / 8L;
        if (this.field_152453_c > this.field_152452_b) {
            throw new RuntimeException("Tried to read NBT tag that was too big; tried to allocate: " + this.field_152453_c + "bytes where max allowed: " + this.field_152452_b);
        }
    }
    
    static {
        field_152451_a = new NBTSizeTracker(0L) {
            private static final String __OBFID = "CL_00001902";
            
            @Override
            public void func_152450_a(final long p_152450_1_) {
            }
        };
    }
}
