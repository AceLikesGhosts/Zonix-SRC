package net.minecraft.item;

import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class ItemDoublePlant extends ItemMultiTexture
{
    private static final String __OBFID = "CL_00000021";
    
    public ItemDoublePlant(final Block p_i45335_1_, final BlockDoublePlant p_i45335_2_, final String[] p_i45335_3_) {
        super(p_i45335_1_, p_i45335_2_, p_i45335_3_);
    }
    
    @Override
    public IIcon getIconFromDamage(final int p_77617_1_) {
        return (BlockDoublePlant.func_149890_d(p_77617_1_) == 0) ? ((BlockDoublePlant)this.field_150941_b).field_149891_b[0] : ((BlockDoublePlant)this.field_150941_b).func_149888_a(true, p_77617_1_);
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack p_82790_1_, final int p_82790_2_) {
        final int var3 = BlockDoublePlant.func_149890_d(p_82790_1_.getItemDamage());
        return (var3 != 2 && var3 != 3) ? super.getColorFromItemStack(p_82790_1_, p_82790_2_) : ColorizerGrass.getGrassColor(0.5, 1.0);
    }
}
