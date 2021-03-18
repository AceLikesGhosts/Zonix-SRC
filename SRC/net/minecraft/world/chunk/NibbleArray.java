package net.minecraft.world.chunk;

public class NibbleArray
{
    public final byte[] data;
    private final int depthBits;
    private final int depthBitsPlusFour;
    private static final String __OBFID = "CL_00000371";
    
    public NibbleArray(final int p_i1992_1_, final int p_i1992_2_) {
        this.data = new byte[p_i1992_1_ >> 1];
        this.depthBits = p_i1992_2_;
        this.depthBitsPlusFour = p_i1992_2_ + 4;
    }
    
    public NibbleArray(final byte[] p_i1993_1_, final int p_i1993_2_) {
        this.data = p_i1993_1_;
        this.depthBits = p_i1993_2_;
        this.depthBitsPlusFour = p_i1993_2_ + 4;
    }
    
    public int get(final int p_76582_1_, final int p_76582_2_, final int p_76582_3_) {
        final int var4 = p_76582_2_ << this.depthBitsPlusFour | p_76582_3_ << this.depthBits | p_76582_1_;
        final int var5 = var4 >> 1;
        final int var6 = var4 & 0x1;
        return (var6 == 0) ? (this.data[var5] & 0xF) : (this.data[var5] >> 4 & 0xF);
    }
    
    public void set(final int p_76581_1_, final int p_76581_2_, final int p_76581_3_, final int p_76581_4_) {
        final int var5 = p_76581_2_ << this.depthBitsPlusFour | p_76581_3_ << this.depthBits | p_76581_1_;
        final int var6 = var5 >> 1;
        final int var7 = var5 & 0x1;
        if (var7 == 0) {
            this.data[var6] = (byte)((this.data[var6] & 0xF0) | (p_76581_4_ & 0xF));
        }
        else {
            this.data[var6] = (byte)((this.data[var6] & 0xF) | (p_76581_4_ & 0xF) << 4);
        }
    }
}
