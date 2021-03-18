package net.minecraft.enchantment;

import net.minecraft.item.*;
import net.minecraft.init.*;

public class EnchantmentDigging extends Enchantment
{
    private static final String __OBFID = "CL_00000104";
    
    protected EnchantmentDigging(final int p_i1925_1_, final int p_i1925_2_) {
        super(p_i1925_1_, p_i1925_2_, EnumEnchantmentType.digger);
        this.setName("digging");
    }
    
    @Override
    public int getMinEnchantability(final int p_77321_1_) {
        return 1 + 10 * (p_77321_1_ - 1);
    }
    
    @Override
    public int getMaxEnchantability(final int p_77317_1_) {
        return super.getMinEnchantability(p_77317_1_) + 50;
    }
    
    @Override
    public int getMaxLevel() {
        return 5;
    }
    
    @Override
    public boolean canApply(final ItemStack p_92089_1_) {
        return p_92089_1_.getItem() == Items.shears || super.canApply(p_92089_1_);
    }
}
