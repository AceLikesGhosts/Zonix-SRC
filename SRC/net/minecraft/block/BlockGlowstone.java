package net.minecraft.block;

import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;

public class BlockGlowstone extends Block
{
    private static final String __OBFID = "CL_00000250";
    
    public BlockGlowstone(final Material p_i45409_1_) {
        super(p_i45409_1_);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int quantityDroppedWithBonus(final int p_149679_1_, final Random p_149679_2_) {
        return MathHelper.clamp_int(this.quantityDropped(p_149679_2_) + p_149679_2_.nextInt(p_149679_1_ + 1), 1, 4);
    }
    
    @Override
    public int quantityDropped(final Random p_149745_1_) {
        return 2 + p_149745_1_.nextInt(3);
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return Items.glowstone_dust;
    }
    
    @Override
    public MapColor getMapColor(final int p_149728_1_) {
        return MapColor.field_151658_d;
    }
}
