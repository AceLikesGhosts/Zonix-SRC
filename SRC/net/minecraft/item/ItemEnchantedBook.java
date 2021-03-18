package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;

public class ItemEnchantedBook extends Item
{
    private static final String __OBFID = "CL_00000025";
    
    @Override
    public boolean hasEffect(final ItemStack p_77636_1_) {
        return true;
    }
    
    @Override
    public boolean isItemTool(final ItemStack p_77616_1_) {
        return false;
    }
    
    @Override
    public EnumRarity getRarity(final ItemStack p_77613_1_) {
        return (this.func_92110_g(p_77613_1_).tagCount() > 0) ? EnumRarity.uncommon : super.getRarity(p_77613_1_);
    }
    
    public NBTTagList func_92110_g(final ItemStack p_92110_1_) {
        return (NBTTagList)((p_92110_1_.stackTagCompound != null && p_92110_1_.stackTagCompound.func_150297_b("StoredEnchantments", 9)) ? p_92110_1_.stackTagCompound.getTag("StoredEnchantments") : new NBTTagList());
    }
    
    @Override
    public void addInformation(final ItemStack p_77624_1_, final EntityPlayer p_77624_2_, final List p_77624_3_, final boolean p_77624_4_) {
        super.addInformation(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
        final NBTTagList var5 = this.func_92110_g(p_77624_1_);
        if (var5 != null) {
            for (int var6 = 0; var6 < var5.tagCount(); ++var6) {
                final short var7 = var5.getCompoundTagAt(var6).getShort("id");
                final short var8 = var5.getCompoundTagAt(var6).getShort("lvl");
                if (Enchantment.enchantmentsList[var7] != null) {
                    p_77624_3_.add(Enchantment.enchantmentsList[var7].getTranslatedName(var8));
                }
            }
        }
    }
    
    public void addEnchantment(final ItemStack p_92115_1_, final EnchantmentData p_92115_2_) {
        final NBTTagList var3 = this.func_92110_g(p_92115_1_);
        boolean var4 = true;
        for (int var5 = 0; var5 < var3.tagCount(); ++var5) {
            final NBTTagCompound var6 = var3.getCompoundTagAt(var5);
            if (var6.getShort("id") == p_92115_2_.enchantmentobj.effectId) {
                if (var6.getShort("lvl") < p_92115_2_.enchantmentLevel) {
                    var6.setShort("lvl", (short)p_92115_2_.enchantmentLevel);
                }
                var4 = false;
                break;
            }
        }
        if (var4) {
            final NBTTagCompound var7 = new NBTTagCompound();
            var7.setShort("id", (short)p_92115_2_.enchantmentobj.effectId);
            var7.setShort("lvl", (short)p_92115_2_.enchantmentLevel);
            var3.appendTag(var7);
        }
        if (!p_92115_1_.hasTagCompound()) {
            p_92115_1_.setTagCompound(new NBTTagCompound());
        }
        p_92115_1_.getTagCompound().setTag("StoredEnchantments", var3);
    }
    
    public ItemStack getEnchantedItemStack(final EnchantmentData p_92111_1_) {
        final ItemStack var2 = new ItemStack(this);
        this.addEnchantment(var2, p_92111_1_);
        return var2;
    }
    
    public void func_92113_a(final Enchantment p_92113_1_, final List p_92113_2_) {
        for (int var3 = p_92113_1_.getMinLevel(); var3 <= p_92113_1_.getMaxLevel(); ++var3) {
            p_92113_2_.add(this.getEnchantedItemStack(new EnchantmentData(p_92113_1_, var3)));
        }
    }
    
    public WeightedRandomChestContent func_92114_b(final Random p_92114_1_) {
        return this.func_92112_a(p_92114_1_, 1, 1, 1);
    }
    
    public WeightedRandomChestContent func_92112_a(final Random p_92112_1_, final int p_92112_2_, final int p_92112_3_, final int p_92112_4_) {
        final ItemStack var5 = new ItemStack(Items.book, 1, 0);
        EnchantmentHelper.addRandomEnchantment(p_92112_1_, var5, 30);
        return new WeightedRandomChestContent(var5, p_92112_2_, p_92112_3_, p_92112_4_);
    }
}
