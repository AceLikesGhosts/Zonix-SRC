package net.minecraft.world.gen.feature;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import java.util.*;

public abstract class WorldGenAbstractTree extends WorldGenerator
{
    private static final String __OBFID = "CL_00000399";
    
    public WorldGenAbstractTree(final boolean p_i45448_1_) {
        super(p_i45448_1_);
    }
    
    protected boolean func_150523_a(final Block p_150523_1_) {
        return p_150523_1_.getMaterial() == Material.air || p_150523_1_.getMaterial() == Material.leaves || p_150523_1_ == Blocks.grass || p_150523_1_ == Blocks.dirt || p_150523_1_ == Blocks.log || p_150523_1_ == Blocks.log2 || p_150523_1_ == Blocks.sapling || p_150523_1_ == Blocks.vine;
    }
    
    public void func_150524_b(final World p_150524_1_, final Random p_150524_2_, final int p_150524_3_, final int p_150524_4_, final int p_150524_5_) {
    }
}
