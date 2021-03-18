package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.potion.*;
import net.minecraft.creativetab.*;
import java.util.*;

public class ItemAppleGold extends ItemFood
{
    private static final String __OBFID = "CL_00000037";
    
    public ItemAppleGold(final int p_i45341_1_, final float p_i45341_2_, final boolean p_i45341_3_) {
        super(p_i45341_1_, p_i45341_2_, p_i45341_3_);
        this.setHasSubtypes(true);
    }
    
    @Override
    public boolean hasEffect(final ItemStack p_77636_1_) {
        return p_77636_1_.getItemDamage() > 0;
    }
    
    @Override
    public EnumRarity getRarity(final ItemStack p_77613_1_) {
        return (p_77613_1_.getItemDamage() == 0) ? EnumRarity.rare : EnumRarity.epic;
    }
    
    @Override
    protected void onFoodEaten(final ItemStack p_77849_1_, final World p_77849_2_, final EntityPlayer p_77849_3_) {
        if (!p_77849_2_.isClient) {
            p_77849_3_.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 2400, 0));
        }
        if (p_77849_1_.getItemDamage() > 0) {
            if (!p_77849_2_.isClient) {
                p_77849_3_.addPotionEffect(new PotionEffect(Potion.regeneration.id, 600, 4));
                p_77849_3_.addPotionEffect(new PotionEffect(Potion.resistance.id, 6000, 0));
                p_77849_3_.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 6000, 0));
            }
        }
        else {
            super.onFoodEaten(p_77849_1_, p_77849_2_, p_77849_3_);
        }
    }
    
    @Override
    public void getSubItems(final Item p_150895_1_, final CreativeTabs p_150895_2_, final List p_150895_3_) {
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, 0));
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, 1));
    }
}
