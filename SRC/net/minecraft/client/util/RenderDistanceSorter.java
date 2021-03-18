package net.minecraft.client.util;

import java.util.*;
import net.minecraft.client.renderer.*;
import com.google.common.collect.*;

public class RenderDistanceSorter implements Comparator
{
    int field_152632_a;
    int field_152633_b;
    private static final String __OBFID = "CL_00000945";
    
    public RenderDistanceSorter(final int p_i1051_1_, final int p_i1051_2_) {
        this.field_152632_a = p_i1051_1_;
        this.field_152633_b = p_i1051_2_;
    }
    
    public int compare(final RenderList p_compare_1_, final RenderList p_compare_2_) {
        final int var3 = p_compare_1_.renderChunkX - this.field_152632_a;
        final int var4 = p_compare_1_.renderChunkZ - this.field_152633_b;
        final int var5 = p_compare_2_.renderChunkX - this.field_152632_a;
        final int var6 = p_compare_2_.renderChunkZ - this.field_152633_b;
        final int var7 = var3 * var3 + var4 * var4;
        final int var8 = var5 * var5 + var6 * var6;
        return ComparisonChain.start().compare(var8, var7).result();
    }
    
    @Override
    public int compare(final Object p_compare_1_, final Object p_compare_2_) {
        return this.compare((RenderList)p_compare_1_, (RenderList)p_compare_2_);
    }
}
