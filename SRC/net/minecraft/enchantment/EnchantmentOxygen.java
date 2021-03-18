package net.minecraft.enchantment;

public class EnchantmentOxygen extends Enchantment
{
    private static final String __OBFID = "CL_00000120";
    
    public EnchantmentOxygen(final int p_i1935_1_, final int p_i1935_2_) {
        super(p_i1935_1_, p_i1935_2_, EnumEnchantmentType.armor_head);
        this.setName("oxygen");
    }
    
    @Override
    public int getMinEnchantability(final int p_77321_1_) {
        return 10 * p_77321_1_;
    }
    
    @Override
    public int getMaxEnchantability(final int p_77317_1_) {
        return this.getMinEnchantability(p_77317_1_) + 30;
    }
    
    @Override
    public int getMaxLevel() {
        return 3;
    }
}
