package net.minecraft.item;

import net.minecraft.util.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;

public class ItemCoal extends Item
{
    private IIcon field_111220_a;
    private static final String __OBFID = "CL_00000002";
    
    public ItemCoal() {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack p_77667_1_) {
        return (p_77667_1_.getItemDamage() == 1) ? "item.charcoal" : "item.coal";
    }
    
    @Override
    public void getSubItems(final Item p_150895_1_, final CreativeTabs p_150895_2_, final List p_150895_3_) {
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, 0));
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, 1));
    }
    
    @Override
    public IIcon getIconFromDamage(final int p_77617_1_) {
        return (p_77617_1_ == 1) ? this.field_111220_a : super.getIconFromDamage(p_77617_1_);
    }
    
    @Override
    public void registerIcons(final IIconRegister p_94581_1_) {
        super.registerIcons(p_94581_1_);
        this.field_111220_a = p_94581_1_.registerIcon("charcoal");
    }
}
