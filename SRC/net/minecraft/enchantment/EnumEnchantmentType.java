package net.minecraft.enchantment;

import net.minecraft.item.*;

public enum EnumEnchantmentType
{
    all, 
    armor, 
    armor_feet, 
    armor_legs, 
    armor_torso, 
    armor_head, 
    weapon, 
    digger, 
    fishing_rod, 
    breakable, 
    bow;
    
    private static final String __OBFID = "CL_00000106";
    
    public boolean canEnchantItem(final Item p_77557_1_) {
        if (this == EnumEnchantmentType.all) {
            return true;
        }
        if (this == EnumEnchantmentType.breakable && p_77557_1_.isDamageable()) {
            return true;
        }
        if (!(p_77557_1_ instanceof ItemArmor)) {
            return (p_77557_1_ instanceof ItemSword) ? (this == EnumEnchantmentType.weapon) : ((p_77557_1_ instanceof ItemTool) ? (this == EnumEnchantmentType.digger) : ((p_77557_1_ instanceof ItemBow) ? (this == EnumEnchantmentType.bow) : (p_77557_1_ instanceof ItemFishingRod && this == EnumEnchantmentType.fishing_rod)));
        }
        if (this == EnumEnchantmentType.armor) {
            return true;
        }
        final ItemArmor var2 = (ItemArmor)p_77557_1_;
        return (var2.armorType == 0) ? (this == EnumEnchantmentType.armor_head) : ((var2.armorType == 2) ? (this == EnumEnchantmentType.armor_legs) : ((var2.armorType == 1) ? (this == EnumEnchantmentType.armor_torso) : (var2.armorType == 3 && this == EnumEnchantmentType.armor_feet)));
    }
}
