package net.minecraft.item;

import java.util.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;

public class ItemTool extends Item
{
    private Set field_150914_c;
    protected float efficiencyOnProperMaterial;
    private float damageVsEntity;
    protected ToolMaterial toolMaterial;
    private static final String __OBFID = "CL_00000019";
    
    protected ItemTool(final float p_i45333_1_, final ToolMaterial p_i45333_2_, final Set p_i45333_3_) {
        this.efficiencyOnProperMaterial = 4.0f;
        this.toolMaterial = p_i45333_2_;
        this.field_150914_c = p_i45333_3_;
        this.maxStackSize = 1;
        this.setMaxDamage(p_i45333_2_.getMaxUses());
        this.efficiencyOnProperMaterial = p_i45333_2_.getEfficiencyOnProperMaterial();
        this.damageVsEntity = p_i45333_1_ + p_i45333_2_.getDamageVsEntity();
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public float func_150893_a(final ItemStack p_150893_1_, final Block p_150893_2_) {
        return this.field_150914_c.contains(p_150893_2_) ? this.efficiencyOnProperMaterial : 1.0f;
    }
    
    @Override
    public boolean hitEntity(final ItemStack p_77644_1_, final EntityLivingBase p_77644_2_, final EntityLivingBase p_77644_3_) {
        p_77644_1_.damageItem(2, p_77644_3_);
        return true;
    }
    
    @Override
    public boolean onBlockDestroyed(final ItemStack p_150894_1_, final World p_150894_2_, final Block p_150894_3_, final int p_150894_4_, final int p_150894_5_, final int p_150894_6_, final EntityLivingBase p_150894_7_) {
        if (p_150894_3_.getBlockHardness(p_150894_2_, p_150894_4_, p_150894_5_, p_150894_6_) != 0.0) {
            p_150894_1_.damageItem(1, p_150894_7_);
        }
        return true;
    }
    
    @Override
    public boolean isFull3D() {
        return true;
    }
    
    public ToolMaterial func_150913_i() {
        return this.toolMaterial;
    }
    
    @Override
    public int getItemEnchantability() {
        return this.toolMaterial.getEnchantability();
    }
    
    public String getToolMaterialName() {
        return this.toolMaterial.toString();
    }
    
    @Override
    public boolean getIsRepairable(final ItemStack p_82789_1_, final ItemStack p_82789_2_) {
        return this.toolMaterial.func_150995_f() == p_82789_2_.getItem() || super.getIsRepairable(p_82789_1_, p_82789_2_);
    }
    
    @Override
    public Multimap getItemAttributeModifiers() {
        final Multimap var1 = super.getItemAttributeModifiers();
        var1.put((Object)SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), (Object)new AttributeModifier(ItemTool.field_111210_e, "Tool modifier", this.damageVsEntity, 0));
        return var1;
    }
}
