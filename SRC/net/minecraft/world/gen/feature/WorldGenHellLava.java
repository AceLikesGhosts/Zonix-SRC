package net.minecraft.world.gen.feature;

import net.minecraft.block.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;

public class WorldGenHellLava extends WorldGenerator
{
    private Block field_150553_a;
    private boolean field_94524_b;
    private static final String __OBFID = "CL_00000414";
    
    public WorldGenHellLava(final Block p_i45453_1_, final boolean p_i45453_2_) {
        this.field_150553_a = p_i45453_1_;
        this.field_94524_b = p_i45453_2_;
    }
    
    @Override
    public boolean generate(final World p_76484_1_, final Random p_76484_2_, final int p_76484_3_, final int p_76484_4_, final int p_76484_5_) {
        if (p_76484_1_.getBlock(p_76484_3_, p_76484_4_ + 1, p_76484_5_) != Blocks.netherrack) {
            return false;
        }
        if (p_76484_1_.getBlock(p_76484_3_, p_76484_4_, p_76484_5_).getMaterial() != Material.air && p_76484_1_.getBlock(p_76484_3_, p_76484_4_, p_76484_5_) != Blocks.netherrack) {
            return false;
        }
        int var6 = 0;
        if (p_76484_1_.getBlock(p_76484_3_ - 1, p_76484_4_, p_76484_5_) == Blocks.netherrack) {
            ++var6;
        }
        if (p_76484_1_.getBlock(p_76484_3_ + 1, p_76484_4_, p_76484_5_) == Blocks.netherrack) {
            ++var6;
        }
        if (p_76484_1_.getBlock(p_76484_3_, p_76484_4_, p_76484_5_ - 1) == Blocks.netherrack) {
            ++var6;
        }
        if (p_76484_1_.getBlock(p_76484_3_, p_76484_4_, p_76484_5_ + 1) == Blocks.netherrack) {
            ++var6;
        }
        if (p_76484_1_.getBlock(p_76484_3_, p_76484_4_ - 1, p_76484_5_) == Blocks.netherrack) {
            ++var6;
        }
        int var7 = 0;
        if (p_76484_1_.isAirBlock(p_76484_3_ - 1, p_76484_4_, p_76484_5_)) {
            ++var7;
        }
        if (p_76484_1_.isAirBlock(p_76484_3_ + 1, p_76484_4_, p_76484_5_)) {
            ++var7;
        }
        if (p_76484_1_.isAirBlock(p_76484_3_, p_76484_4_, p_76484_5_ - 1)) {
            ++var7;
        }
        if (p_76484_1_.isAirBlock(p_76484_3_, p_76484_4_, p_76484_5_ + 1)) {
            ++var7;
        }
        if (p_76484_1_.isAirBlock(p_76484_3_, p_76484_4_ - 1, p_76484_5_)) {
            ++var7;
        }
        if ((!this.field_94524_b && var6 == 4 && var7 == 1) || var6 == 5) {
            p_76484_1_.setBlock(p_76484_3_, p_76484_4_, p_76484_5_, this.field_150553_a, 0, 2);
            p_76484_1_.scheduledUpdatesAreImmediate = true;
            this.field_150553_a.updateTick(p_76484_1_, p_76484_3_, p_76484_4_, p_76484_5_, p_76484_2_);
            p_76484_1_.scheduledUpdatesAreImmediate = false;
        }
        return true;
    }
}
