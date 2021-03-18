package net.minecraft.item;

import net.minecraft.block.*;
import net.minecraft.util.*;

public class ItemBlockWithMetadata extends ItemBlock
{
    private Block field_150950_b;
    private static final String __OBFID = "CL_00001769";
    
    public ItemBlockWithMetadata(final Block p_i45326_1_, final Block p_i45326_2_) {
        super(p_i45326_1_);
        this.field_150950_b = p_i45326_2_;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    @Override
    public IIcon getIconFromDamage(final int p_77617_1_) {
        return this.field_150950_b.getIcon(2, p_77617_1_);
    }
    
    @Override
    public int getMetadata(final int p_77647_1_) {
        return p_77647_1_;
    }
}
