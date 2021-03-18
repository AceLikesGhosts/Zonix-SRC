package net.minecraft.item;

import net.minecraft.block.*;
import net.minecraft.util.*;

public class ItemColored extends ItemBlock
{
    private final Block field_150944_b;
    private String[] field_150945_c;
    private static final String __OBFID = "CL_00000003";
    
    public ItemColored(final Block p_i45332_1_, final boolean p_i45332_2_) {
        super(p_i45332_1_);
        this.field_150944_b = p_i45332_1_;
        if (p_i45332_2_) {
            this.setMaxDamage(0);
            this.setHasSubtypes(true);
        }
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack p_82790_1_, final int p_82790_2_) {
        return this.field_150944_b.getRenderColor(p_82790_1_.getItemDamage());
    }
    
    @Override
    public IIcon getIconFromDamage(final int p_77617_1_) {
        return this.field_150944_b.getIcon(0, p_77617_1_);
    }
    
    @Override
    public int getMetadata(final int p_77647_1_) {
        return p_77647_1_;
    }
    
    public ItemColored func_150943_a(final String[] p_150943_1_) {
        this.field_150945_c = p_150943_1_;
        return this;
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack p_77667_1_) {
        if (this.field_150945_c == null) {
            return super.getUnlocalizedName(p_77667_1_);
        }
        final int var2 = p_77667_1_.getItemDamage();
        return (var2 >= 0 && var2 < this.field_150945_c.length) ? (super.getUnlocalizedName(p_77667_1_) + "." + this.field_150945_c[var2]) : super.getUnlocalizedName(p_77667_1_);
    }
}
