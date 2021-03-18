package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public class WorldGenBigMushroom extends WorldGenerator
{
    private int mushroomType;
    private static final String __OBFID = "CL_00000415";
    
    public WorldGenBigMushroom(final int p_i2017_1_) {
        super(true);
        this.mushroomType = -1;
        this.mushroomType = p_i2017_1_;
    }
    
    public WorldGenBigMushroom() {
        super(false);
        this.mushroomType = -1;
    }
    
    @Override
    public boolean generate(final World p_76484_1_, final Random p_76484_2_, final int p_76484_3_, final int p_76484_4_, final int p_76484_5_) {
        int var6 = p_76484_2_.nextInt(2);
        if (this.mushroomType >= 0) {
            var6 = this.mushroomType;
        }
        final int var7 = p_76484_2_.nextInt(3) + 4;
        boolean var8 = true;
        if (p_76484_4_ < 1 || p_76484_4_ + var7 + 1 >= 256) {
            return false;
        }
        for (int var9 = p_76484_4_; var9 <= p_76484_4_ + 1 + var7; ++var9) {
            byte var10 = 3;
            if (var9 <= p_76484_4_ + 3) {
                var10 = 0;
            }
            for (int var11 = p_76484_3_ - var10; var11 <= p_76484_3_ + var10 && var8; ++var11) {
                for (int var12 = p_76484_5_ - var10; var12 <= p_76484_5_ + var10 && var8; ++var12) {
                    if (var9 >= 0 && var9 < 256) {
                        final Block var13 = p_76484_1_.getBlock(var11, var9, var12);
                        if (var13.getMaterial() != Material.air && var13.getMaterial() != Material.leaves) {
                            var8 = false;
                        }
                    }
                    else {
                        var8 = false;
                    }
                }
            }
        }
        if (!var8) {
            return false;
        }
        final Block var14 = p_76484_1_.getBlock(p_76484_3_, p_76484_4_ - 1, p_76484_5_);
        if (var14 != Blocks.dirt && var14 != Blocks.grass && var14 != Blocks.mycelium) {
            return false;
        }
        int var15 = p_76484_4_ + var7;
        if (var6 == 1) {
            var15 = p_76484_4_ + var7 - 3;
        }
        for (int var11 = var15; var11 <= p_76484_4_ + var7; ++var11) {
            int var12 = 1;
            if (var11 < p_76484_4_ + var7) {
                ++var12;
            }
            if (var6 == 0) {
                var12 = 3;
            }
            for (int var16 = p_76484_3_ - var12; var16 <= p_76484_3_ + var12; ++var16) {
                for (int var17 = p_76484_5_ - var12; var17 <= p_76484_5_ + var12; ++var17) {
                    int var18 = 5;
                    if (var16 == p_76484_3_ - var12) {
                        --var18;
                    }
                    if (var16 == p_76484_3_ + var12) {
                        ++var18;
                    }
                    if (var17 == p_76484_5_ - var12) {
                        var18 -= 3;
                    }
                    if (var17 == p_76484_5_ + var12) {
                        var18 += 3;
                    }
                    if (var6 == 0 || var11 < p_76484_4_ + var7) {
                        if (var16 == p_76484_3_ - var12 || var16 == p_76484_3_ + var12) {
                            if (var17 == p_76484_5_ - var12) {
                                continue;
                            }
                            if (var17 == p_76484_5_ + var12) {
                                continue;
                            }
                        }
                        if (var16 == p_76484_3_ - (var12 - 1) && var17 == p_76484_5_ - var12) {
                            var18 = 1;
                        }
                        if (var16 == p_76484_3_ - var12 && var17 == p_76484_5_ - (var12 - 1)) {
                            var18 = 1;
                        }
                        if (var16 == p_76484_3_ + (var12 - 1) && var17 == p_76484_5_ - var12) {
                            var18 = 3;
                        }
                        if (var16 == p_76484_3_ + var12 && var17 == p_76484_5_ - (var12 - 1)) {
                            var18 = 3;
                        }
                        if (var16 == p_76484_3_ - (var12 - 1) && var17 == p_76484_5_ + var12) {
                            var18 = 7;
                        }
                        if (var16 == p_76484_3_ - var12 && var17 == p_76484_5_ + (var12 - 1)) {
                            var18 = 7;
                        }
                        if (var16 == p_76484_3_ + (var12 - 1) && var17 == p_76484_5_ + var12) {
                            var18 = 9;
                        }
                        if (var16 == p_76484_3_ + var12 && var17 == p_76484_5_ + (var12 - 1)) {
                            var18 = 9;
                        }
                    }
                    if (var18 == 5 && var11 < p_76484_4_ + var7) {
                        var18 = 0;
                    }
                    if ((var18 != 0 || p_76484_4_ >= p_76484_4_ + var7 - 1) && !p_76484_1_.getBlock(var16, var11, var17).func_149730_j()) {
                        this.func_150516_a(p_76484_1_, var16, var11, var17, Block.getBlockById(Block.getIdFromBlock(Blocks.brown_mushroom_block) + var6), var18);
                    }
                }
            }
        }
        for (int var11 = 0; var11 < var7; ++var11) {
            final Block var19 = p_76484_1_.getBlock(p_76484_3_, p_76484_4_ + var11, p_76484_5_);
            if (!var19.func_149730_j()) {
                this.func_150516_a(p_76484_1_, p_76484_3_, p_76484_4_ + var11, p_76484_5_, Block.getBlockById(Block.getIdFromBlock(Blocks.brown_mushroom_block) + var6), 10);
            }
        }
        return true;
    }
}
