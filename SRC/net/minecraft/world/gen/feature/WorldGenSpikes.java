package net.minecraft.world.gen.feature;

import net.minecraft.block.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;

public class WorldGenSpikes extends WorldGenerator
{
    private Block field_150520_a;
    private static final String __OBFID = "CL_00000433";
    
    public WorldGenSpikes(final Block p_i45464_1_) {
        this.field_150520_a = p_i45464_1_;
    }
    
    @Override
    public boolean generate(final World p_76484_1_, final Random p_76484_2_, final int p_76484_3_, final int p_76484_4_, final int p_76484_5_) {
        if (p_76484_1_.isAirBlock(p_76484_3_, p_76484_4_, p_76484_5_) && p_76484_1_.getBlock(p_76484_3_, p_76484_4_ - 1, p_76484_5_) == this.field_150520_a) {
            final int var6 = p_76484_2_.nextInt(32) + 6;
            final int var7 = p_76484_2_.nextInt(4) + 1;
            for (int var8 = p_76484_3_ - var7; var8 <= p_76484_3_ + var7; ++var8) {
                for (int var9 = p_76484_5_ - var7; var9 <= p_76484_5_ + var7; ++var9) {
                    final int var10 = var8 - p_76484_3_;
                    final int var11 = var9 - p_76484_5_;
                    if (var10 * var10 + var11 * var11 <= var7 * var7 + 1 && p_76484_1_.getBlock(var8, p_76484_4_ - 1, var9) != this.field_150520_a) {
                        return false;
                    }
                }
            }
            for (int var8 = p_76484_4_; var8 < p_76484_4_ + var6 && var8 < 256; ++var8) {
                for (int var9 = p_76484_3_ - var7; var9 <= p_76484_3_ + var7; ++var9) {
                    for (int var10 = p_76484_5_ - var7; var10 <= p_76484_5_ + var7; ++var10) {
                        final int var11 = var9 - p_76484_3_;
                        final int var12 = var10 - p_76484_5_;
                        if (var11 * var11 + var12 * var12 <= var7 * var7 + 1) {
                            p_76484_1_.setBlock(var9, var8, var10, Blocks.obsidian, 0, 2);
                        }
                    }
                }
            }
            final EntityEnderCrystal var13 = new EntityEnderCrystal(p_76484_1_);
            var13.setLocationAndAngles(p_76484_3_ + 0.5f, p_76484_4_ + var6, p_76484_5_ + 0.5f, p_76484_2_.nextFloat() * 360.0f, 0.0f);
            p_76484_1_.spawnEntityInWorld(var13);
            p_76484_1_.setBlock(p_76484_3_, p_76484_4_ + var6, p_76484_5_, Blocks.bedrock, 0, 2);
            return true;
        }
        return false;
    }
}
