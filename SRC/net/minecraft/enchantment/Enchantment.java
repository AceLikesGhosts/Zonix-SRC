package net.minecraft.enchantment;

import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import java.util.*;

public abstract class Enchantment
{
    public static final Enchantment[] enchantmentsList;
    public static final Enchantment[] enchantmentsBookList;
    public static final Enchantment protection;
    public static final Enchantment fireProtection;
    public static final Enchantment featherFalling;
    public static final Enchantment blastProtection;
    public static final Enchantment projectileProtection;
    public static final Enchantment respiration;
    public static final Enchantment aquaAffinity;
    public static final Enchantment thorns;
    public static final Enchantment sharpness;
    public static final Enchantment smite;
    public static final Enchantment baneOfArthropods;
    public static final Enchantment knockback;
    public static final Enchantment fireAspect;
    public static final Enchantment looting;
    public static final Enchantment efficiency;
    public static final Enchantment silkTouch;
    public static final Enchantment unbreaking;
    public static final Enchantment fortune;
    public static final Enchantment power;
    public static final Enchantment punch;
    public static final Enchantment flame;
    public static final Enchantment infinity;
    public static final Enchantment field_151370_z;
    public static final Enchantment field_151369_A;
    public final int effectId;
    private final int weight;
    public EnumEnchantmentType type;
    protected String name;
    private static final String __OBFID = "CL_00000105";
    
    protected Enchantment(final int p_i1926_1_, final int p_i1926_2_, final EnumEnchantmentType p_i1926_3_) {
        this.effectId = p_i1926_1_;
        this.weight = p_i1926_2_;
        this.type = p_i1926_3_;
        if (Enchantment.enchantmentsList[p_i1926_1_] != null) {
            throw new IllegalArgumentException("Duplicate enchantment id!");
        }
        Enchantment.enchantmentsList[p_i1926_1_] = this;
    }
    
    public int getWeight() {
        return this.weight;
    }
    
    public int getMinLevel() {
        return 1;
    }
    
    public int getMaxLevel() {
        return 1;
    }
    
    public int getMinEnchantability(final int p_77321_1_) {
        return 1 + p_77321_1_ * 10;
    }
    
    public int getMaxEnchantability(final int p_77317_1_) {
        return this.getMinEnchantability(p_77317_1_) + 5;
    }
    
    public int calcModifierDamage(final int p_77318_1_, final DamageSource p_77318_2_) {
        return 0;
    }
    
    public float func_152376_a(final int p_152376_1_, final EnumCreatureAttribute p_152376_2_) {
        return 0.0f;
    }
    
    public boolean canApplyTogether(final Enchantment p_77326_1_) {
        return this != p_77326_1_;
    }
    
    public Enchantment setName(final String p_77322_1_) {
        this.name = p_77322_1_;
        return this;
    }
    
    public String getName() {
        return "enchantment." + this.name;
    }
    
    public String getTranslatedName(final int p_77316_1_) {
        final String var2 = StatCollector.translateToLocal(this.getName());
        return var2 + " " + StatCollector.translateToLocal("enchantment.level." + p_77316_1_);
    }
    
    public boolean canApply(final ItemStack p_92089_1_) {
        return this.type.canEnchantItem(p_92089_1_.getItem());
    }
    
    public void func_151368_a(final EntityLivingBase p_151368_1_, final Entity p_151368_2_, final int p_151368_3_) {
    }
    
    public void func_151367_b(final EntityLivingBase p_151367_1_, final Entity p_151367_2_, final int p_151367_3_) {
    }
    
    static {
        enchantmentsList = new Enchantment[256];
        protection = new EnchantmentProtection(0, 10, 0);
        fireProtection = new EnchantmentProtection(1, 5, 1);
        featherFalling = new EnchantmentProtection(2, 5, 2);
        blastProtection = new EnchantmentProtection(3, 2, 3);
        projectileProtection = new EnchantmentProtection(4, 5, 4);
        respiration = new EnchantmentOxygen(5, 2);
        aquaAffinity = new EnchantmentWaterWorker(6, 2);
        thorns = new EnchantmentThorns(7, 1);
        sharpness = new EnchantmentDamage(16, 10, 0);
        smite = new EnchantmentDamage(17, 5, 1);
        baneOfArthropods = new EnchantmentDamage(18, 5, 2);
        knockback = new EnchantmentKnockback(19, 5);
        fireAspect = new EnchantmentFireAspect(20, 2);
        looting = new EnchantmentLootBonus(21, 2, EnumEnchantmentType.weapon);
        efficiency = new EnchantmentDigging(32, 10);
        silkTouch = new EnchantmentUntouching(33, 1);
        unbreaking = new EnchantmentDurability(34, 5);
        fortune = new EnchantmentLootBonus(35, 2, EnumEnchantmentType.digger);
        power = new EnchantmentArrowDamage(48, 10);
        punch = new EnchantmentArrowKnockback(49, 2);
        flame = new EnchantmentArrowFire(50, 2);
        infinity = new EnchantmentArrowInfinite(51, 1);
        field_151370_z = new EnchantmentLootBonus(61, 2, EnumEnchantmentType.fishing_rod);
        field_151369_A = new EnchantmentFishingSpeed(62, 2, EnumEnchantmentType.fishing_rod);
        final ArrayList var0 = new ArrayList();
        for (final Enchantment var5 : Enchantment.enchantmentsList) {
            if (var5 != null) {
                var0.add(var5);
            }
        }
        enchantmentsBookList = var0.toArray(new Enchantment[0]);
    }
}
