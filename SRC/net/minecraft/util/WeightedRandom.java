package net.minecraft.util;

import java.util.*;

public class WeightedRandom
{
    private static final String __OBFID = "CL_00001503";
    
    public static int getTotalWeight(final Collection p_76272_0_) {
        int var1 = 0;
        for (final Item var3 : p_76272_0_) {
            var1 += var3.itemWeight;
        }
        return var1;
    }
    
    public static Item getRandomItem(final Random p_76273_0_, final Collection p_76273_1_, final int p_76273_2_) {
        if (p_76273_2_ <= 0) {
            throw new IllegalArgumentException();
        }
        int var3 = p_76273_0_.nextInt(p_76273_2_);
        for (final Item var5 : p_76273_1_) {
            var3 -= var5.itemWeight;
            if (var3 < 0) {
                return var5;
            }
        }
        return null;
    }
    
    public static Item getRandomItem(final Random p_76271_0_, final Collection p_76271_1_) {
        return getRandomItem(p_76271_0_, p_76271_1_, getTotalWeight(p_76271_1_));
    }
    
    public static int getTotalWeight(final Item[] p_76270_0_) {
        int var1 = 0;
        final Item[] var2 = p_76270_0_;
        for (int var3 = p_76270_0_.length, var4 = 0; var4 < var3; ++var4) {
            final Item var5 = var2[var4];
            var1 += var5.itemWeight;
        }
        return var1;
    }
    
    public static Item getRandomItem(final Random p_76269_0_, final Item[] p_76269_1_, final int p_76269_2_) {
        if (p_76269_2_ <= 0) {
            throw new IllegalArgumentException();
        }
        int var3 = p_76269_0_.nextInt(p_76269_2_);
        final Item[] var4 = p_76269_1_;
        for (int var5 = p_76269_1_.length, var6 = 0; var6 < var5; ++var6) {
            final Item var7 = var4[var6];
            var3 -= var7.itemWeight;
            if (var3 < 0) {
                return var7;
            }
        }
        return null;
    }
    
    public static Item getRandomItem(final Random p_76274_0_, final Item[] p_76274_1_) {
        return getRandomItem(p_76274_0_, p_76274_1_, getTotalWeight(p_76274_1_));
    }
    
    public static class Item
    {
        protected int itemWeight;
        private static final String __OBFID = "CL_00001504";
        
        public Item(final int p_i1556_1_) {
            this.itemWeight = p_i1556_1_;
        }
    }
}
