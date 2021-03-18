package net.minecraft.world.gen.feature;

import net.minecraft.block.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.world.biome.*;

public class WorldGenLakes extends WorldGenerator
{
    private Block field_150556_a;
    private static final String __OBFID = "CL_00000418";
    
    public WorldGenLakes(final Block p_i45455_1_) {
        this.field_150556_a = p_i45455_1_;
    }
    
    @Override
    public boolean generate(final World p_76484_1_, final Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_) {
        for (p_76484_3_ -= 8, p_76484_5_ -= 8; p_76484_4_ > 5 && p_76484_1_.isAirBlock(p_76484_3_, p_76484_4_, p_76484_5_); --p_76484_4_) {}
        if (p_76484_4_ <= 4) {
            return false;
        }
        p_76484_4_ -= 4;
        final boolean[] var6 = new boolean[2048];
        for (int var7 = p_76484_2_.nextInt(4) + 4, var8 = 0; var8 < var7; ++var8) {
            final double var9 = p_76484_2_.nextDouble() * 6.0 + 3.0;
            final double var10 = p_76484_2_.nextDouble() * 4.0 + 2.0;
            final double var11 = p_76484_2_.nextDouble() * 6.0 + 3.0;
            final double var12 = p_76484_2_.nextDouble() * (16.0 - var9 - 2.0) + 1.0 + var9 / 2.0;
            final double var13 = p_76484_2_.nextDouble() * (8.0 - var10 - 4.0) + 2.0 + var10 / 2.0;
            final double var14 = p_76484_2_.nextDouble() * (16.0 - var11 - 2.0) + 1.0 + var11 / 2.0;
            for (int var15 = 1; var15 < 15; ++var15) {
                for (int var16 = 1; var16 < 15; ++var16) {
                    for (int var17 = 1; var17 < 7; ++var17) {
                        final double var18 = (var15 - var12) / (var9 / 2.0);
                        final double var19 = (var17 - var13) / (var10 / 2.0);
                        final double var20 = (var16 - var14) / (var11 / 2.0);
                        final double var21 = var18 * var18 + var19 * var19 + var20 * var20;
                        if (var21 < 1.0) {
                            var6[(var15 * 16 + var16) * 8 + var17] = true;
                        }
                    }
                }
            }
        }
        for (int var8 = 0; var8 < 16; ++var8) {
            for (int var22 = 0; var22 < 16; ++var22) {
                for (int var23 = 0; var23 < 8; ++var23) {
                    final boolean var24 = !var6[(var8 * 16 + var22) * 8 + var23] && ((var8 < 15 && var6[((var8 + 1) * 16 + var22) * 8 + var23]) || (var8 > 0 && var6[((var8 - 1) * 16 + var22) * 8 + var23]) || (var22 < 15 && var6[(var8 * 16 + var22 + 1) * 8 + var23]) || (var22 > 0 && var6[(var8 * 16 + (var22 - 1)) * 8 + var23]) || (var23 < 7 && var6[(var8 * 16 + var22) * 8 + var23 + 1]) || (var23 > 0 && var6[(var8 * 16 + var22) * 8 + (var23 - 1)]));
                    if (var24) {
                        final Material var25 = p_76484_1_.getBlock(p_76484_3_ + var8, p_76484_4_ + var23, p_76484_5_ + var22).getMaterial();
                        if (var23 >= 4 && var25.isLiquid()) {
                            return false;
                        }
                        if (var23 < 4 && !var25.isSolid() && p_76484_1_.getBlock(p_76484_3_ + var8, p_76484_4_ + var23, p_76484_5_ + var22) != this.field_150556_a) {
                            return false;
                        }
                    }
                }
            }
        }
        for (int var8 = 0; var8 < 16; ++var8) {
            for (int var22 = 0; var22 < 16; ++var22) {
                for (int var23 = 0; var23 < 8; ++var23) {
                    if (var6[(var8 * 16 + var22) * 8 + var23]) {
                        p_76484_1_.setBlock(p_76484_3_ + var8, p_76484_4_ + var23, p_76484_5_ + var22, (var23 >= 4) ? Blocks.air : this.field_150556_a, 0, 2);
                    }
                }
            }
        }
        for (int var8 = 0; var8 < 16; ++var8) {
            for (int var22 = 0; var22 < 16; ++var22) {
                for (int var23 = 4; var23 < 8; ++var23) {
                    if (var6[(var8 * 16 + var22) * 8 + var23] && p_76484_1_.getBlock(p_76484_3_ + var8, p_76484_4_ + var23 - 1, p_76484_5_ + var22) == Blocks.dirt && p_76484_1_.getSavedLightValue(EnumSkyBlock.Sky, p_76484_3_ + var8, p_76484_4_ + var23, p_76484_5_ + var22) > 0) {
                        final BiomeGenBase var26 = p_76484_1_.getBiomeGenForCoords(p_76484_3_ + var8, p_76484_5_ + var22);
                        if (var26.topBlock == Blocks.mycelium) {
                            p_76484_1_.setBlock(p_76484_3_ + var8, p_76484_4_ + var23 - 1, p_76484_5_ + var22, Blocks.mycelium, 0, 2);
                        }
                        else {
                            p_76484_1_.setBlock(p_76484_3_ + var8, p_76484_4_ + var23 - 1, p_76484_5_ + var22, Blocks.grass, 0, 2);
                        }
                    }
                }
            }
        }
        if (this.field_150556_a.getMaterial() == Material.lava) {
            for (int var8 = 0; var8 < 16; ++var8) {
                for (int var22 = 0; var22 < 16; ++var22) {
                    for (int var23 = 0; var23 < 8; ++var23) {
                        final boolean var24 = !var6[(var8 * 16 + var22) * 8 + var23] && ((var8 < 15 && var6[((var8 + 1) * 16 + var22) * 8 + var23]) || (var8 > 0 && var6[((var8 - 1) * 16 + var22) * 8 + var23]) || (var22 < 15 && var6[(var8 * 16 + var22 + 1) * 8 + var23]) || (var22 > 0 && var6[(var8 * 16 + (var22 - 1)) * 8 + var23]) || (var23 < 7 && var6[(var8 * 16 + var22) * 8 + var23 + 1]) || (var23 > 0 && var6[(var8 * 16 + var22) * 8 + (var23 - 1)]));
                        if (var24 && (var23 < 4 || p_76484_2_.nextInt(2) != 0) && p_76484_1_.getBlock(p_76484_3_ + var8, p_76484_4_ + var23, p_76484_5_ + var22).getMaterial().isSolid()) {
                            p_76484_1_.setBlock(p_76484_3_ + var8, p_76484_4_ + var23, p_76484_5_ + var22, Blocks.stone, 0, 2);
                        }
                    }
                }
            }
        }
        if (this.field_150556_a.getMaterial() == Material.water) {
            for (int var8 = 0; var8 < 16; ++var8) {
                for (int var22 = 0; var22 < 16; ++var22) {
                    final byte var27 = 4;
                    if (p_76484_1_.isBlockFreezable(p_76484_3_ + var8, p_76484_4_ + var27, p_76484_5_ + var22)) {
                        p_76484_1_.setBlock(p_76484_3_ + var8, p_76484_4_ + var27, p_76484_5_ + var22, Blocks.ice, 0, 2);
                    }
                }
            }
        }
        return true;
    }
}
