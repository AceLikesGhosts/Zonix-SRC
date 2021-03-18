package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.*;

public class GenLayerShore extends GenLayer
{
    private static final String __OBFID = "CL_00000568";
    
    public GenLayerShore(final long p_i2130_1_, final GenLayer p_i2130_3_) {
        super(p_i2130_1_);
        this.parent = p_i2130_3_;
    }
    
    @Override
    public int[] getInts(final int p_75904_1_, final int p_75904_2_, final int p_75904_3_, final int p_75904_4_) {
        final int[] var5 = this.parent.getInts(p_75904_1_ - 1, p_75904_2_ - 1, p_75904_3_ + 2, p_75904_4_ + 2);
        final int[] var6 = IntCache.getIntCache(p_75904_3_ * p_75904_4_);
        for (int var7 = 0; var7 < p_75904_4_; ++var7) {
            for (int var8 = 0; var8 < p_75904_3_; ++var8) {
                this.initChunkSeed(var8 + p_75904_1_, var7 + p_75904_2_);
                final int var9 = var5[var8 + 1 + (var7 + 1) * (p_75904_3_ + 2)];
                final BiomeGenBase var10 = BiomeGenBase.func_150568_d(var9);
                if (var9 == BiomeGenBase.mushroomIsland.biomeID) {
                    final int var11 = var5[var8 + 1 + (var7 + 1 - 1) * (p_75904_3_ + 2)];
                    final int var12 = var5[var8 + 1 + 1 + (var7 + 1) * (p_75904_3_ + 2)];
                    final int var13 = var5[var8 + 1 - 1 + (var7 + 1) * (p_75904_3_ + 2)];
                    final int var14 = var5[var8 + 1 + (var7 + 1 + 1) * (p_75904_3_ + 2)];
                    if (var11 != BiomeGenBase.ocean.biomeID && var12 != BiomeGenBase.ocean.biomeID && var13 != BiomeGenBase.ocean.biomeID && var14 != BiomeGenBase.ocean.biomeID) {
                        var6[var8 + var7 * p_75904_3_] = var9;
                    }
                    else {
                        var6[var8 + var7 * p_75904_3_] = BiomeGenBase.mushroomIslandShore.biomeID;
                    }
                }
                else if (var10 != null && var10.func_150562_l() == BiomeGenJungle.class) {
                    final int var11 = var5[var8 + 1 + (var7 + 1 - 1) * (p_75904_3_ + 2)];
                    final int var12 = var5[var8 + 1 + 1 + (var7 + 1) * (p_75904_3_ + 2)];
                    final int var13 = var5[var8 + 1 - 1 + (var7 + 1) * (p_75904_3_ + 2)];
                    final int var14 = var5[var8 + 1 + (var7 + 1 + 1) * (p_75904_3_ + 2)];
                    if (this.func_151631_c(var11) && this.func_151631_c(var12) && this.func_151631_c(var13) && this.func_151631_c(var14)) {
                        if (!GenLayer.func_151618_b(var11) && !GenLayer.func_151618_b(var12) && !GenLayer.func_151618_b(var13) && !GenLayer.func_151618_b(var14)) {
                            var6[var8 + var7 * p_75904_3_] = var9;
                        }
                        else {
                            var6[var8 + var7 * p_75904_3_] = BiomeGenBase.beach.biomeID;
                        }
                    }
                    else {
                        var6[var8 + var7 * p_75904_3_] = BiomeGenBase.field_150574_L.biomeID;
                    }
                }
                else if (var9 != BiomeGenBase.extremeHills.biomeID && var9 != BiomeGenBase.field_150580_W.biomeID && var9 != BiomeGenBase.extremeHillsEdge.biomeID) {
                    if (var10 != null && var10.func_150559_j()) {
                        this.func_151632_a(var5, var6, var8, var7, p_75904_3_, var9, BiomeGenBase.field_150577_O.biomeID);
                    }
                    else if (var9 != BiomeGenBase.field_150589_Z.biomeID && var9 != BiomeGenBase.field_150607_aa.biomeID) {
                        if (var9 != BiomeGenBase.ocean.biomeID && var9 != BiomeGenBase.field_150575_M.biomeID && var9 != BiomeGenBase.river.biomeID && var9 != BiomeGenBase.swampland.biomeID) {
                            final int var11 = var5[var8 + 1 + (var7 + 1 - 1) * (p_75904_3_ + 2)];
                            final int var12 = var5[var8 + 1 + 1 + (var7 + 1) * (p_75904_3_ + 2)];
                            final int var13 = var5[var8 + 1 - 1 + (var7 + 1) * (p_75904_3_ + 2)];
                            final int var14 = var5[var8 + 1 + (var7 + 1 + 1) * (p_75904_3_ + 2)];
                            if (!GenLayer.func_151618_b(var11) && !GenLayer.func_151618_b(var12) && !GenLayer.func_151618_b(var13) && !GenLayer.func_151618_b(var14)) {
                                var6[var8 + var7 * p_75904_3_] = var9;
                            }
                            else {
                                var6[var8 + var7 * p_75904_3_] = BiomeGenBase.beach.biomeID;
                            }
                        }
                        else {
                            var6[var8 + var7 * p_75904_3_] = var9;
                        }
                    }
                    else {
                        final int var11 = var5[var8 + 1 + (var7 + 1 - 1) * (p_75904_3_ + 2)];
                        final int var12 = var5[var8 + 1 + 1 + (var7 + 1) * (p_75904_3_ + 2)];
                        final int var13 = var5[var8 + 1 - 1 + (var7 + 1) * (p_75904_3_ + 2)];
                        final int var14 = var5[var8 + 1 + (var7 + 1 + 1) * (p_75904_3_ + 2)];
                        if (!GenLayer.func_151618_b(var11) && !GenLayer.func_151618_b(var12) && !GenLayer.func_151618_b(var13) && !GenLayer.func_151618_b(var14)) {
                            if (this.func_151633_d(var11) && this.func_151633_d(var12) && this.func_151633_d(var13) && this.func_151633_d(var14)) {
                                var6[var8 + var7 * p_75904_3_] = var9;
                            }
                            else {
                                var6[var8 + var7 * p_75904_3_] = BiomeGenBase.desert.biomeID;
                            }
                        }
                        else {
                            var6[var8 + var7 * p_75904_3_] = var9;
                        }
                    }
                }
                else {
                    this.func_151632_a(var5, var6, var8, var7, p_75904_3_, var9, BiomeGenBase.field_150576_N.biomeID);
                }
            }
        }
        return var6;
    }
    
    private void func_151632_a(final int[] p_151632_1_, final int[] p_151632_2_, final int p_151632_3_, final int p_151632_4_, final int p_151632_5_, final int p_151632_6_, final int p_151632_7_) {
        if (GenLayer.func_151618_b(p_151632_6_)) {
            p_151632_2_[p_151632_3_ + p_151632_4_ * p_151632_5_] = p_151632_6_;
        }
        else {
            final int var8 = p_151632_1_[p_151632_3_ + 1 + (p_151632_4_ + 1 - 1) * (p_151632_5_ + 2)];
            final int var9 = p_151632_1_[p_151632_3_ + 1 + 1 + (p_151632_4_ + 1) * (p_151632_5_ + 2)];
            final int var10 = p_151632_1_[p_151632_3_ + 1 - 1 + (p_151632_4_ + 1) * (p_151632_5_ + 2)];
            final int var11 = p_151632_1_[p_151632_3_ + 1 + (p_151632_4_ + 1 + 1) * (p_151632_5_ + 2)];
            if (!GenLayer.func_151618_b(var8) && !GenLayer.func_151618_b(var9) && !GenLayer.func_151618_b(var10) && !GenLayer.func_151618_b(var11)) {
                p_151632_2_[p_151632_3_ + p_151632_4_ * p_151632_5_] = p_151632_6_;
            }
            else {
                p_151632_2_[p_151632_3_ + p_151632_4_ * p_151632_5_] = p_151632_7_;
            }
        }
    }
    
    private boolean func_151631_c(final int p_151631_1_) {
        return (BiomeGenBase.func_150568_d(p_151631_1_) != null && BiomeGenBase.func_150568_d(p_151631_1_).func_150562_l() == BiomeGenJungle.class) || (p_151631_1_ == BiomeGenBase.field_150574_L.biomeID || p_151631_1_ == BiomeGenBase.jungle.biomeID || p_151631_1_ == BiomeGenBase.jungleHills.biomeID || p_151631_1_ == BiomeGenBase.forest.biomeID || p_151631_1_ == BiomeGenBase.taiga.biomeID || GenLayer.func_151618_b(p_151631_1_));
    }
    
    private boolean func_151633_d(final int p_151633_1_) {
        return BiomeGenBase.func_150568_d(p_151633_1_) != null && BiomeGenBase.func_150568_d(p_151633_1_) instanceof BiomeGenMesa;
    }
}
