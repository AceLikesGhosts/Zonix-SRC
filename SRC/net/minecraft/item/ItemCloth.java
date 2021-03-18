package net.minecraft.item;

import net.minecraft.util.*;
import net.minecraft.block.*;

public class ItemCloth extends ItemBlock
{
    private static final String __OBFID = "CL_00000075";
    
    public ItemCloth(final Block p_i45358_1_) {
        super(p_i45358_1_);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    @Override
    public IIcon getIconFromDamage(final int p_77617_1_) {
        return this.field_150939_a.func_149735_b(2, BlockColored.func_150032_b(p_77617_1_));
    }
    
    @Override
    public int getMetadata(final int p_77647_1_) {
        return p_77647_1_;
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack p_77667_1_) {
        return super.getUnlocalizedName() + "." + ItemDye.field_150923_a[BlockColored.func_150032_b(p_77667_1_.getItemDamage())];
    }
}
