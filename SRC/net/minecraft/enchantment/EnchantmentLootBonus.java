package net.minecraft.enchantment;

public class EnchantmentLootBonus extends Enchantment
{
    private static final String __OBFID = "CL_00000119";
    
    protected EnchantmentLootBonus(final int p_i1934_1_, final int p_i1934_2_, final EnumEnchantmentType p_i1934_3_) {
        super(p_i1934_1_, p_i1934_2_, p_i1934_3_);
        if (p_i1934_3_ == EnumEnchantmentType.digger) {
            this.setName("lootBonusDigger");
        }
        else if (p_i1934_3_ == EnumEnchantmentType.fishing_rod) {
            this.setName("lootBonusFishing");
        }
        else {
            this.setName("lootBonus");
        }
    }
    
    @Override
    public int getMinEnchantability(final int p_77321_1_) {
        return 15 + (p_77321_1_ - 1) * 9;
    }
    
    @Override
    public int getMaxEnchantability(final int p_77317_1_) {
        return super.getMinEnchantability(p_77317_1_) + 50;
    }
    
    @Override
    public int getMaxLevel() {
        return 3;
    }
    
    @Override
    public boolean canApplyTogether(final Enchantment p_77326_1_) {
        return super.canApplyTogether(p_77326_1_) && p_77326_1_.effectId != EnchantmentLootBonus.silkTouch.effectId;
    }
}
