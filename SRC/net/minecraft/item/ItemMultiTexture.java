package net.minecraft.item;

import net.minecraft.block.*;
import net.minecraft.util.*;

public class ItemMultiTexture extends ItemBlock
{
    protected final Block field_150941_b;
    protected final String[] field_150942_c;
    private static final String __OBFID = "CL_00000051";
    
    public ItemMultiTexture(final Block p_i45346_1_, final Block p_i45346_2_, final String[] p_i45346_3_) {
        super(p_i45346_1_);
        this.field_150941_b = p_i45346_2_;
        this.field_150942_c = p_i45346_3_;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    @Override
    public IIcon getIconFromDamage(final int p_77617_1_) {
        return this.field_150941_b.getIcon(2, p_77617_1_);
    }
    
    @Override
    public int getMetadata(final int p_77647_1_) {
        return p_77647_1_;
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack p_77667_1_) {
        int var2 = p_77667_1_.getItemDamage();
        if (var2 < 0 || var2 >= this.field_150942_c.length) {
            var2 = 0;
        }
        return super.getUnlocalizedName() + "." + this.field_150942_c[var2];
    }
}
