package net.minecraft.world.gen.layer;

public class GenLayerRemoveTooMuchOcean extends GenLayer
{
    private static final String __OBFID = "CL_00000564";
    
    public GenLayerRemoveTooMuchOcean(final long p_i45480_1_, final GenLayer p_i45480_3_) {
        super(p_i45480_1_);
        this.parent = p_i45480_3_;
    }
    
    @Override
    public int[] getInts(final int p_75904_1_, final int p_75904_2_, final int p_75904_3_, final int p_75904_4_) {
        final int var5 = p_75904_1_ - 1;
        final int var6 = p_75904_2_ - 1;
        final int var7 = p_75904_3_ + 2;
        final int var8 = p_75904_4_ + 2;
        final int[] var9 = this.parent.getInts(var5, var6, var7, var8);
        final int[] var10 = IntCache.getIntCache(p_75904_3_ * p_75904_4_);
        for (int var11 = 0; var11 < p_75904_4_; ++var11) {
            for (int var12 = 0; var12 < p_75904_3_; ++var12) {
                final int var13 = var9[var12 + 1 + (var11 + 1 - 1) * (p_75904_3_ + 2)];
                final int var14 = var9[var12 + 1 + 1 + (var11 + 1) * (p_75904_3_ + 2)];
                final int var15 = var9[var12 + 1 - 1 + (var11 + 1) * (p_75904_3_ + 2)];
                final int var16 = var9[var12 + 1 + (var11 + 1 + 1) * (p_75904_3_ + 2)];
                final int var17 = var9[var12 + 1 + (var11 + 1) * var7];
                var10[var12 + var11 * p_75904_3_] = var17;
                this.initChunkSeed(var12 + p_75904_1_, var11 + p_75904_2_);
                if (var17 == 0 && var13 == 0 && var14 == 0 && var15 == 0 && var16 == 0 && this.nextInt(2) == 0) {
                    var10[var12 + var11 * p_75904_3_] = 1;
                }
            }
        }
        return var10;
    }
}
