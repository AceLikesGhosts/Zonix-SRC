package net.minecraft.item;

import java.util.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import com.google.common.collect.*;

public class ItemPickaxe extends ItemTool
{
    private static final Set field_150915_c;
    private static final String __OBFID = "CL_00000053";
    
    protected ItemPickaxe(final ToolMaterial p_i45347_1_) {
        super(2.0f, p_i45347_1_, ItemPickaxe.field_150915_c);
    }
    
    @Override
    public boolean func_150897_b(final Block p_150897_1_) {
        return (p_150897_1_ == Blocks.obsidian) ? (this.toolMaterial.getHarvestLevel() == 3) : ((p_150897_1_ != Blocks.diamond_block && p_150897_1_ != Blocks.diamond_ore) ? ((p_150897_1_ != Blocks.emerald_ore && p_150897_1_ != Blocks.emerald_block) ? ((p_150897_1_ != Blocks.gold_block && p_150897_1_ != Blocks.gold_ore) ? ((p_150897_1_ != Blocks.iron_block && p_150897_1_ != Blocks.iron_ore) ? ((p_150897_1_ != Blocks.lapis_block && p_150897_1_ != Blocks.lapis_ore) ? ((p_150897_1_ != Blocks.redstone_ore && p_150897_1_ != Blocks.lit_redstone_ore) ? (p_150897_1_.getMaterial() == Material.rock || p_150897_1_.getMaterial() == Material.iron || p_150897_1_.getMaterial() == Material.anvil) : (this.toolMaterial.getHarvestLevel() >= 2)) : (this.toolMaterial.getHarvestLevel() >= 1)) : (this.toolMaterial.getHarvestLevel() >= 1)) : (this.toolMaterial.getHarvestLevel() >= 2)) : (this.toolMaterial.getHarvestLevel() >= 2)) : (this.toolMaterial.getHarvestLevel() >= 2));
    }
    
    @Override
    public float func_150893_a(final ItemStack p_150893_1_, final Block p_150893_2_) {
        return (p_150893_2_.getMaterial() != Material.iron && p_150893_2_.getMaterial() != Material.anvil && p_150893_2_.getMaterial() != Material.rock) ? super.func_150893_a(p_150893_1_, p_150893_2_) : this.efficiencyOnProperMaterial;
    }
    
    static {
        field_150915_c = Sets.newHashSet((Object[])new Block[] { Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore, Blocks.diamond_block, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.activator_rail });
    }
}
