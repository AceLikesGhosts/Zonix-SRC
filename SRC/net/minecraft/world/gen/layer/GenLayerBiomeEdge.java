package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.*;

public class GenLayerBiomeEdge extends GenLayer
{
    private static final String __OBFID = "CL_00000554";
    
    public GenLayerBiomeEdge(final long p_i45475_1_, final GenLayer p_i45475_3_) {
        super(p_i45475_1_);
        this.parent = p_i45475_3_;
    }
    
    @Override
    public int[] getInts(final int p_75904_1_, final int p_75904_2_, final int p_75904_3_, final int p_75904_4_) {
        final int[] var5 = this.parent.getInts(p_75904_1_ - 1, p_75904_2_ - 1, p_75904_3_ + 2, p_75904_4_ + 2);
        final int[] var6 = IntCache.getIntCache(p_75904_3_ * p_75904_4_);
        for (int var7 = 0; var7 < p_75904_4_; ++var7) {
            for (int var8 = 0; var8 < p_75904_3_; ++var8) {
                this.initChunkSeed(var8 + p_75904_1_, var7 + p_75904_2_);
                final int var9 = var5[var8 + 1 + (var7 + 1) * (p_75904_3_ + 2)];
                if (!this.func_151636_a(var5, var6, var8, var7, p_75904_3_, var9, BiomeGenBase.extremeHills.biomeID, BiomeGenBase.extremeHillsEdge.biomeID) && !this.func_151635_b(var5, var6, var8, var7, p_75904_3_, var9, BiomeGenBase.field_150607_aa.biomeID, BiomeGenBase.field_150589_Z.biomeID) && !this.func_151635_b(var5, var6, var8, var7, p_75904_3_, var9, BiomeGenBase.field_150608_ab.biomeID, BiomeGenBase.field_150589_Z.biomeID) && !this.func_151635_b(var5, var6, var8, var7, p_75904_3_, var9, BiomeGenBase.field_150578_U.biomeID, BiomeGenBase.taiga.biomeID)) {
                    if (var9 == BiomeGenBase.desert.biomeID) {
                        final int var10 = var5[var8 + 1 + (var7 + 1 - 1) * (p_75904_3_ + 2)];
                        final int var11 = var5[var8 + 1 + 1 + (var7 + 1) * (p_75904_3_ + 2)];
                        final int var12 = var5[var8 + 1 - 1 + (var7 + 1) * (p_75904_3_ + 2)];
                        final int var13 = var5[var8 + 1 + (var7 + 1 + 1) * (p_75904_3_ + 2)];
                        if (var10 != BiomeGenBase.icePlains.biomeID && var11 != BiomeGenBase.icePlains.biomeID && var12 != BiomeGenBase.icePlains.biomeID && var13 != BiomeGenBase.icePlains.biomeID) {
                            var6[var8 + var7 * p_75904_3_] = var9;
                        }
                        else {
                            var6[var8 + var7 * p_75904_3_] = BiomeGenBase.field_150580_W.biomeID;
                        }
                    }
                    else if (var9 == BiomeGenBase.swampland.biomeID) {
                        final int var10 = var5[var8 + 1 + (var7 + 1 - 1) * (p_75904_3_ + 2)];
                        final int var11 = var5[var8 + 1 + 1 + (var7 + 1) * (p_75904_3_ + 2)];
                        final int var12 = var5[var8 + 1 - 1 + (var7 + 1) * (p_75904_3_ + 2)];
                        final int var13 = var5[var8 + 1 + (var7 + 1 + 1) * (p_75904_3_ + 2)];
                        if (var10 != BiomeGenBase.desert.biomeID && var11 != BiomeGenBase.desert.biomeID && var12 != BiomeGenBase.desert.biomeID && var13 != BiomeGenBase.desert.biomeID && var10 != BiomeGenBase.field_150584_S.biomeID && var11 != BiomeGenBase.field_150584_S.biomeID && var12 != BiomeGenBase.field_150584_S.biomeID && var13 != BiomeGenBase.field_150584_S.biomeID && var10 != BiomeGenBase.icePlains.biomeID && var11 != BiomeGenBase.icePlains.biomeID && var12 != BiomeGenBase.icePlains.biomeID && var13 != BiomeGenBase.icePlains.biomeID) {
                            if (var10 != BiomeGenBase.jungle.biomeID && var13 != BiomeGenBase.jungle.biomeID && var11 != BiomeGenBase.jungle.biomeID && var12 != BiomeGenBase.jungle.biomeID) {
                                var6[var8 + var7 * p_75904_3_] = var9;
                            }
                            else {
                                var6[var8 + var7 * p_75904_3_] = BiomeGenBase.field_150574_L.biomeID;
                            }
                        }
                        else {
                            var6[var8 + var7 * p_75904_3_] = BiomeGenBase.plains.biomeID;
                        }
                    }
                    else {
                        var6[var8 + var7 * p_75904_3_] = var9;
                    }
                }
            }
        }
        return var6;
    }
    
    private boolean func_151636_a(final int[] p_151636_1_, final int[] p_151636_2_, final int p_151636_3_, final int p_151636_4_, final int p_151636_5_, final int p_151636_6_, final int p_151636_7_, final int p_151636_8_) {
        if (!GenLayer.func_151616_a(p_151636_6_, p_151636_7_)) {
            return false;
        }
        final int var9 = p_151636_1_[p_151636_3_ + 1 + (p_151636_4_ + 1 - 1) * (p_151636_5_ + 2)];
        final int var10 = p_151636_1_[p_151636_3_ + 1 + 1 + (p_151636_4_ + 1) * (p_151636_5_ + 2)];
        final int var11 = p_151636_1_[p_151636_3_ + 1 - 1 + (p_151636_4_ + 1) * (p_151636_5_ + 2)];
        final int var12 = p_151636_1_[p_151636_3_ + 1 + (p_151636_4_ + 1 + 1) * (p_151636_5_ + 2)];
        if (this.func_151634_b(var9, p_151636_7_) && this.func_151634_b(var10, p_151636_7_) && this.func_151634_b(var11, p_151636_7_) && this.func_151634_b(var12, p_151636_7_)) {
            p_151636_2_[p_151636_3_ + p_151636_4_ * p_151636_5_] = p_151636_6_;
        }
        else {
            p_151636_2_[p_151636_3_ + p_151636_4_ * p_151636_5_] = p_151636_8_;
        }
        return true;
    }
    
    private boolean func_151635_b(final int[] p_151635_1_, final int[] p_151635_2_, final int p_151635_3_, final int p_151635_4_, final int p_151635_5_, final int p_151635_6_, final int p_151635_7_, final int p_151635_8_) {
        if (p_151635_6_ != p_151635_7_) {
            return false;
        }
        final int var9 = p_151635_1_[p_151635_3_ + 1 + (p_151635_4_ + 1 - 1) * (p_151635_5_ + 2)];
        final int var10 = p_151635_1_[p_151635_3_ + 1 + 1 + (p_151635_4_ + 1) * (p_151635_5_ + 2)];
        final int var11 = p_151635_1_[p_151635_3_ + 1 - 1 + (p_151635_4_ + 1) * (p_151635_5_ + 2)];
        final int var12 = p_151635_1_[p_151635_3_ + 1 + (p_151635_4_ + 1 + 1) * (p_151635_5_ + 2)];
        if (GenLayer.func_151616_a(var9, p_151635_7_) && GenLayer.func_151616_a(var10, p_151635_7_) && GenLayer.func_151616_a(var11, p_151635_7_) && GenLayer.func_151616_a(var12, p_151635_7_)) {
            p_151635_2_[p_151635_3_ + p_151635_4_ * p_151635_5_] = p_151635_6_;
        }
        else {
            p_151635_2_[p_151635_3_ + p_151635_4_ * p_151635_5_] = p_151635_8_;
        }
        return true;
    }
    
    private boolean func_151634_b(final int p_151634_1_, final int p_151634_2_) {
        if (GenLayer.func_151616_a(p_151634_1_, p_151634_2_)) {
            return true;
        }
        if (BiomeGenBase.func_150568_d(p_151634_1_) != null && BiomeGenBase.func_150568_d(p_151634_2_) != null) {
            final BiomeGenBase.TempCategory var3 = BiomeGenBase.func_150568_d(p_151634_1_).func_150561_m();
            final BiomeGenBase.TempCategory var4 = BiomeGenBase.func_150568_d(p_151634_2_).func_150561_m();
            return var3 == var4 || var3 == BiomeGenBase.TempCategory.MEDIUM || var4 == BiomeGenBase.TempCategory.MEDIUM;
        }
        return false;
    }
}
