package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;

public class ItemSword extends Item
{
    private float field_150934_a;
    private final ToolMaterial field_150933_b;
    private static final String __OBFID = "CL_00000072";
    
    public ItemSword(final ToolMaterial p_i45356_1_) {
        this.field_150933_b = p_i45356_1_;
        this.maxStackSize = 1;
        this.setMaxDamage(p_i45356_1_.getMaxUses());
        this.setCreativeTab(CreativeTabs.tabCombat);
        this.field_150934_a = 4.0f + p_i45356_1_.getDamageVsEntity();
    }
    
    public float func_150931_i() {
        return this.field_150933_b.getDamageVsEntity();
    }
    
    @Override
    public float func_150893_a(final ItemStack p_150893_1_, final Block p_150893_2_) {
        if (p_150893_2_ == Blocks.web) {
            return 15.0f;
        }
        final Material var3 = p_150893_2_.getMaterial();
        return (var3 != Material.plants && var3 != Material.vine && var3 != Material.coral && var3 != Material.leaves && var3 != Material.field_151572_C) ? 1.0f : 1.5f;
    }
    
    @Override
    public boolean hitEntity(final ItemStack p_77644_1_, final EntityLivingBase p_77644_2_, final EntityLivingBase p_77644_3_) {
        p_77644_1_.damageItem(1, p_77644_3_);
        return true;
    }
    
    @Override
    public boolean onBlockDestroyed(final ItemStack p_150894_1_, final World p_150894_2_, final Block p_150894_3_, final int p_150894_4_, final int p_150894_5_, final int p_150894_6_, final EntityLivingBase p_150894_7_) {
        if (p_150894_3_.getBlockHardness(p_150894_2_, p_150894_4_, p_150894_5_, p_150894_6_) != 0.0) {
            p_150894_1_.damageItem(2, p_150894_7_);
        }
        return true;
    }
    
    @Override
    public boolean isFull3D() {
        return true;
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack p_77661_1_) {
        return EnumAction.block;
    }
    
    @Override
    public int getMaxItemUseDuration(final ItemStack p_77626_1_) {
        return 72000;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack p_77659_1_, final World p_77659_2_, final EntityPlayer p_77659_3_) {
        p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
        return p_77659_1_;
    }
    
    @Override
    public boolean func_150897_b(final Block p_150897_1_) {
        return p_150897_1_ == Blocks.web;
    }
    
    @Override
    public int getItemEnchantability() {
        return this.field_150933_b.getEnchantability();
    }
    
    public String func_150932_j() {
        return this.field_150933_b.toString();
    }
    
    @Override
    public boolean getIsRepairable(final ItemStack p_82789_1_, final ItemStack p_82789_2_) {
        return this.field_150933_b.func_150995_f() == p_82789_2_.getItem() || super.getIsRepairable(p_82789_1_, p_82789_2_);
    }
    
    @Override
    public Multimap getItemAttributeModifiers() {
        final Multimap var1 = super.getItemAttributeModifiers();
        var1.put((Object)SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), (Object)new AttributeModifier(ItemSword.field_111210_e, "Weapon modifier", this.field_150934_a, 0));
        return var1;
    }
}
