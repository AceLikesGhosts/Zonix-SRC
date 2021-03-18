package net.minecraft.item;

import net.minecraft.util.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.enchantment.*;
import net.minecraft.init.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.texture.*;

public class ItemBow extends Item
{
    public static final String[] bowPullIconNameArray;
    private IIcon[] iconArray;
    private static final String __OBFID = "CL_00001777";
    
    public ItemBow() {
        this.maxStackSize = 1;
        this.setMaxDamage(384);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }
    
    @Override
    public void onPlayerStoppedUsing(final ItemStack p_77615_1_, final World p_77615_2_, final EntityPlayer p_77615_3_, final int p_77615_4_) {
        final boolean var5 = p_77615_3_.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, p_77615_1_) > 0;
        if (var5 || p_77615_3_.inventory.hasItem(Items.arrow)) {
            final int var6 = this.getMaxItemUseDuration(p_77615_1_) - p_77615_4_;
            float var7 = var6 / 20.0f;
            var7 = (var7 * var7 + var7 * 2.0f) / 3.0f;
            if (var7 < 0.1) {
                return;
            }
            if (var7 > 1.0f) {
                var7 = 1.0f;
            }
            final EntityArrow var8 = new EntityArrow(p_77615_2_, p_77615_3_, var7 * 2.0f);
            if (var7 == 1.0f) {
                var8.setIsCritical(true);
            }
            final int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, p_77615_1_);
            if (var9 > 0) {
                var8.setDamage(var8.getDamage() + var9 * 0.5 + 0.5);
            }
            final int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, p_77615_1_);
            if (var10 > 0) {
                var8.setKnockbackStrength(var10);
            }
            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, p_77615_1_) > 0) {
                var8.setFire(100);
            }
            p_77615_1_.damageItem(1, p_77615_3_);
            p_77615_2_.playSoundAtEntity(p_77615_3_, "random.bow", 1.0f, 1.0f / (ItemBow.itemRand.nextFloat() * 0.4f + 1.2f) + var7 * 0.5f);
            if (var5) {
                var8.canBePickedUp = 2;
            }
            else {
                p_77615_3_.inventory.consumeInventoryItem(Items.arrow);
            }
            if (!p_77615_2_.isClient) {
                p_77615_2_.spawnEntityInWorld(var8);
            }
        }
    }
    
    @Override
    public ItemStack onEaten(final ItemStack p_77654_1_, final World p_77654_2_, final EntityPlayer p_77654_3_) {
        return p_77654_1_;
    }
    
    @Override
    public int getMaxItemUseDuration(final ItemStack p_77626_1_) {
        return 72000;
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack p_77661_1_) {
        return EnumAction.bow;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack p_77659_1_, final World p_77659_2_, final EntityPlayer p_77659_3_) {
        if (p_77659_3_.capabilities.isCreativeMode || p_77659_3_.inventory.hasItem(Items.arrow)) {
            p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
        }
        return p_77659_1_;
    }
    
    @Override
    public int getItemEnchantability() {
        return 1;
    }
    
    @Override
    public void registerIcons(final IIconRegister p_94581_1_) {
        this.itemIcon = p_94581_1_.registerIcon(this.getIconString() + "_standby");
        this.iconArray = new IIcon[ItemBow.bowPullIconNameArray.length];
        for (int var2 = 0; var2 < this.iconArray.length; ++var2) {
            this.iconArray[var2] = p_94581_1_.registerIcon(this.getIconString() + "_" + ItemBow.bowPullIconNameArray[var2]);
        }
    }
    
    public IIcon getItemIconForUseDuration(final int p_94599_1_) {
        return this.iconArray[p_94599_1_];
    }
    
    static {
        bowPullIconNameArray = new String[] { "pulling_0", "pulling_1", "pulling_2" };
    }
}
