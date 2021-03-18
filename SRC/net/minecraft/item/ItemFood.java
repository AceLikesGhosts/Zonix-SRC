package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;

public class ItemFood extends Item
{
    public final int itemUseDuration;
    private final int healAmount;
    private final float saturationModifier;
    private final boolean isWolfsFavoriteMeat;
    private boolean alwaysEdible;
    private int potionId;
    private int potionDuration;
    private int potionAmplifier;
    private float potionEffectProbability;
    private static final String __OBFID = "CL_00000036";
    
    public ItemFood(final int p_i45339_1_, final float p_i45339_2_, final boolean p_i45339_3_) {
        this.itemUseDuration = 32;
        this.healAmount = p_i45339_1_;
        this.isWolfsFavoriteMeat = p_i45339_3_;
        this.saturationModifier = p_i45339_2_;
        this.setCreativeTab(CreativeTabs.tabFood);
    }
    
    public ItemFood(final int p_i45340_1_, final boolean p_i45340_2_) {
        this(p_i45340_1_, 0.6f, p_i45340_2_);
    }
    
    @Override
    public ItemStack onEaten(final ItemStack p_77654_1_, final World p_77654_2_, final EntityPlayer p_77654_3_) {
        --p_77654_1_.stackSize;
        p_77654_3_.getFoodStats().func_151686_a(this, p_77654_1_);
        p_77654_2_.playSoundAtEntity(p_77654_3_, "random.burp", 0.5f, p_77654_2_.rand.nextFloat() * 0.1f + 0.9f);
        this.onFoodEaten(p_77654_1_, p_77654_2_, p_77654_3_);
        return p_77654_1_;
    }
    
    protected void onFoodEaten(final ItemStack p_77849_1_, final World p_77849_2_, final EntityPlayer p_77849_3_) {
        if (!p_77849_2_.isClient && this.potionId > 0 && p_77849_2_.rand.nextFloat() < this.potionEffectProbability) {
            p_77849_3_.addPotionEffect(new PotionEffect(this.potionId, this.potionDuration * 20, this.potionAmplifier));
        }
    }
    
    @Override
    public int getMaxItemUseDuration(final ItemStack p_77626_1_) {
        return 32;
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack p_77661_1_) {
        return EnumAction.eat;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack p_77659_1_, final World p_77659_2_, final EntityPlayer p_77659_3_) {
        if (p_77659_3_.canEat(this.alwaysEdible)) {
            p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
        }
        return p_77659_1_;
    }
    
    public int func_150905_g(final ItemStack p_150905_1_) {
        return this.healAmount;
    }
    
    public float func_150906_h(final ItemStack p_150906_1_) {
        return this.saturationModifier;
    }
    
    public boolean isWolfsFavoriteMeat() {
        return this.isWolfsFavoriteMeat;
    }
    
    public ItemFood setPotionEffect(final int p_77844_1_, final int p_77844_2_, final int p_77844_3_, final float p_77844_4_) {
        this.potionId = p_77844_1_;
        this.potionDuration = p_77844_2_;
        this.potionAmplifier = p_77844_3_;
        this.potionEffectProbability = p_77844_4_;
        return this;
    }
    
    public ItemFood setAlwaysEdible() {
        this.alwaysEdible = true;
        return this;
    }
}
