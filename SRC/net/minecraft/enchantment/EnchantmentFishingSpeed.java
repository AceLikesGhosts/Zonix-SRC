package net.minecraft.enchantment;

public class EnchantmentFishingSpeed extends Enchantment
{
    private static final String __OBFID = "CL_00000117";
    
    protected EnchantmentFishingSpeed(final int p_i45361_1_, final int p_i45361_2_, final EnumEnchantmentType p_i45361_3_) {
        super(p_i45361_1_, p_i45361_2_, p_i45361_3_);
        this.setName("fishingSpeed");
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
}
