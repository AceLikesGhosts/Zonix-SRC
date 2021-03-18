package net.minecraft.enchantment;

import net.minecraft.item.*;
import net.minecraft.init.*;

public class EnchantmentUntouching extends Enchantment
{
    private static final String __OBFID = "CL_00000123";
    
    protected EnchantmentUntouching(final int p_i1938_1_, final int p_i1938_2_) {
        super(p_i1938_1_, p_i1938_2_, EnumEnchantmentType.digger);
        this.setName("untouching");
    }
    
    @Override
    public int getMinEnchantability(final int p_77321_1_) {
        return 15;
    }
    
    @Override
    public int getMaxEnchantability(final int p_77317_1_) {
        return super.getMinEnchantability(p_77317_1_) + 50;
    }
    
    @Override
    public int getMaxLevel() {
        return 1;
    }
    
    @Override
    public boolean canApplyTogether(final Enchantment p_77326_1_) {
        return super.canApplyTogether(p_77326_1_) && p_77326_1_.effectId != EnchantmentUntouching.fortune.effectId;
    }
    
    @Override
    public boolean canApply(final ItemStack p_92089_1_) {
        return p_92089_1_.getItem() == Items.shears || super.canApply(p_92089_1_);
    }
}
