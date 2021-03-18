package net.minecraft.util;

import net.minecraft.item.*;
import java.util.*;
import net.minecraft.enchantment.*;

public class WeightedRandomFishable extends WeightedRandom.Item
{
    private final ItemStack field_150711_b;
    private float field_150712_c;
    private boolean field_150710_d;
    private static final String __OBFID = "CL_00001664";
    
    public WeightedRandomFishable(final ItemStack p_i45317_1_, final int p_i45317_2_) {
        super(p_i45317_2_);
        this.field_150711_b = p_i45317_1_;
    }
    
    public ItemStack func_150708_a(final Random p_150708_1_) {
        final ItemStack var2 = this.field_150711_b.copy();
        if (this.field_150712_c > 0.0f) {
            final int var3 = (int)(this.field_150712_c * this.field_150711_b.getMaxDamage());
            int var4 = var2.getMaxDamage() - p_150708_1_.nextInt(p_150708_1_.nextInt(var3) + 1);
            if (var4 > var3) {
                var4 = var3;
            }
            if (var4 < 1) {
                var4 = 1;
            }
            var2.setItemDamage(var4);
        }
        if (this.field_150710_d) {
            EnchantmentHelper.addRandomEnchantment(p_150708_1_, var2, 30);
        }
        return var2;
    }
    
    public WeightedRandomFishable func_150709_a(final float p_150709_1_) {
        this.field_150712_c = p_150709_1_;
        return this;
    }
    
    public WeightedRandomFishable func_150707_a() {
        this.field_150710_d = true;
        return this;
    }
}
