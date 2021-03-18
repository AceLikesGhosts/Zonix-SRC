package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class BlockClay extends Block
{
    private static final String __OBFID = "CL_00000215";
    
    public BlockClay() {
        super(Material.field_151571_B);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return Items.clay_ball;
    }
    
    @Override
    public int quantityDropped(final Random p_149745_1_) {
        return 4;
    }
}
