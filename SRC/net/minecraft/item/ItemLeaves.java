package net.minecraft.item;

import net.minecraft.block.*;
import net.minecraft.util.*;

public class ItemLeaves extends ItemBlock
{
    private final BlockLeaves field_150940_b;
    private static final String __OBFID = "CL_00000046";
    
    public ItemLeaves(final BlockLeaves p_i45344_1_) {
        super(p_i45344_1_);
        this.field_150940_b = p_i45344_1_;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    @Override
    public int getMetadata(final int p_77647_1_) {
        return p_77647_1_ | 0x4;
    }
    
    @Override
    public IIcon getIconFromDamage(final int p_77617_1_) {
        return this.field_150940_b.getIcon(0, p_77617_1_);
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack p_82790_1_, final int p_82790_2_) {
        return this.field_150940_b.getRenderColor(p_82790_1_.getItemDamage());
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack p_77667_1_) {
        int var2 = p_77667_1_.getItemDamage();
        if (var2 < 0 || var2 >= this.field_150940_b.func_150125_e().length) {
            var2 = 0;
        }
        return super.getUnlocalizedName() + "." + this.field_150940_b.func_150125_e()[var2];
    }
}
