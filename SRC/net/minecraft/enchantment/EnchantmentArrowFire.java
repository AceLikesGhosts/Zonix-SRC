package net.minecraft.enchantment;

public class EnchantmentArrowFire extends Enchantment
{
    private static final String __OBFID = "CL_00000099";
    
    public EnchantmentArrowFire(final int p_i1920_1_, final int p_i1920_2_) {
        super(p_i1920_1_, p_i1920_2_, EnumEnchantmentType.bow);
        this.setName("arrowFire");
    }
    
    @Override
    public int getMinEnchantability(final int p_77321_1_) {
        return 20;
    }
    
    @Override
    public int getMaxEnchantability(final int p_77317_1_) {
        return 50;
    }
    
    @Override
    public int getMaxLevel() {
        return 1;
    }
}
