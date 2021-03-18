package net.minecraft.item;

import java.util.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import com.google.common.collect.*;

public class ItemSpade extends ItemTool
{
    private static final Set field_150916_c;
    private static final String __OBFID = "CL_00000063";
    
    public ItemSpade(final ToolMaterial p_i45353_1_) {
        super(1.0f, p_i45353_1_, ItemSpade.field_150916_c);
    }
    
    @Override
    public boolean func_150897_b(final Block p_150897_1_) {
        return p_150897_1_ == Blocks.snow_layer || p_150897_1_ == Blocks.snow;
    }
    
    static {
        field_150916_c = Sets.newHashSet((Object[])new Block[] { Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow_layer, Blocks.snow, Blocks.clay, Blocks.farmland, Blocks.soul_sand, Blocks.mycelium });
    }
}
