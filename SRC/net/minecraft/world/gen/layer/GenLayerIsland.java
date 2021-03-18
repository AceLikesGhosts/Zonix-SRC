package net.minecraft.world.gen.layer;

public class GenLayerIsland extends GenLayer
{
    private static final String __OBFID = "CL_00000558";
    
    public GenLayerIsland(final long p_i2124_1_) {
        super(p_i2124_1_);
    }
    
    @Override
    public int[] getInts(final int p_75904_1_, final int p_75904_2_, final int p_75904_3_, final int p_75904_4_) {
        final int[] var5 = IntCache.getIntCache(p_75904_3_ * p_75904_4_);
        for (int var6 = 0; var6 < p_75904_4_; ++var6) {
            for (int var7 = 0; var7 < p_75904_3_; ++var7) {
                this.initChunkSeed(p_75904_1_ + var7, p_75904_2_ + var6);
                var5[var7 + var6 * p_75904_3_] = ((this.nextInt(10) == 0) ? 1 : 0);
            }
        }
        if (p_75904_1_ > -p_75904_3_ && p_75904_1_ <= 0 && p_75904_2_ > -p_75904_4_ && p_75904_2_ <= 0) {
            var5[-p_75904_1_ + -p_75904_2_ * p_75904_3_] = 1;
        }
        return var5;
    }
}
