package net.minecraft.world.gen.feature;

import net.minecraft.util.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.material.*;

public class WorldGenDungeons extends WorldGenerator
{
    private static final WeightedRandomChestContent[] field_111189_a;
    private static final String __OBFID = "CL_00000425";
    
    @Override
    public boolean generate(final World p_76484_1_, final Random p_76484_2_, final int p_76484_3_, final int p_76484_4_, final int p_76484_5_) {
        final byte var6 = 3;
        final int var7 = p_76484_2_.nextInt(2) + 2;
        final int var8 = p_76484_2_.nextInt(2) + 2;
        int var9 = 0;
        for (int var10 = p_76484_3_ - var7 - 1; var10 <= p_76484_3_ + var7 + 1; ++var10) {
            for (int var11 = p_76484_4_ - 1; var11 <= p_76484_4_ + var6 + 1; ++var11) {
                for (int var12 = p_76484_5_ - var8 - 1; var12 <= p_76484_5_ + var8 + 1; ++var12) {
                    final Material var13 = p_76484_1_.getBlock(var10, var11, var12).getMaterial();
                    if (var11 == p_76484_4_ - 1 && !var13.isSolid()) {
                        return false;
                    }
                    if (var11 == p_76484_4_ + var6 + 1 && !var13.isSolid()) {
                        return false;
                    }
                    if ((var10 == p_76484_3_ - var7 - 1 || var10 == p_76484_3_ + var7 + 1 || var12 == p_76484_5_ - var8 - 1 || var12 == p_76484_5_ + var8 + 1) && var11 == p_76484_4_ && p_76484_1_.isAirBlock(var10, var11, var12) && p_76484_1_.isAirBlock(var10, var11 + 1, var12)) {
                        ++var9;
                    }
                }
            }
        }
        if (var9 >= 1 && var9 <= 5) {
            for (int var10 = p_76484_3_ - var7 - 1; var10 <= p_76484_3_ + var7 + 1; ++var10) {
                for (int var11 = p_76484_4_ + var6; var11 >= p_76484_4_ - 1; --var11) {
                    for (int var12 = p_76484_5_ - var8 - 1; var12 <= p_76484_5_ + var8 + 1; ++var12) {
                        if (var10 != p_76484_3_ - var7 - 1 && var11 != p_76484_4_ - 1 && var12 != p_76484_5_ - var8 - 1 && var10 != p_76484_3_ + var7 + 1 && var11 != p_76484_4_ + var6 + 1 && var12 != p_76484_5_ + var8 + 1) {
                            p_76484_1_.setBlockToAir(var10, var11, var12);
                        }
                        else if (var11 >= 0 && !p_76484_1_.getBlock(var10, var11 - 1, var12).getMaterial().isSolid()) {
                            p_76484_1_.setBlockToAir(var10, var11, var12);
                        }
                        else if (p_76484_1_.getBlock(var10, var11, var12).getMaterial().isSolid()) {
                            if (var11 == p_76484_4_ - 1 && p_76484_2_.nextInt(4) != 0) {
                                p_76484_1_.setBlock(var10, var11, var12, Blocks.mossy_cobblestone, 0, 2);
                            }
                            else {
                                p_76484_1_.setBlock(var10, var11, var12, Blocks.cobblestone, 0, 2);
                            }
                        }
                    }
                }
            }
            for (int var10 = 0; var10 < 2; ++var10) {
                for (int var11 = 0; var11 < 3; ++var11) {
                    final int var12 = p_76484_3_ + p_76484_2_.nextInt(var7 * 2 + 1) - var7;
                    final int var14 = p_76484_5_ + p_76484_2_.nextInt(var8 * 2 + 1) - var8;
                    if (p_76484_1_.isAirBlock(var12, p_76484_4_, var14)) {
                        int var15 = 0;
                        if (p_76484_1_.getBlock(var12 - 1, p_76484_4_, var14).getMaterial().isSolid()) {
                            ++var15;
                        }
                        if (p_76484_1_.getBlock(var12 + 1, p_76484_4_, var14).getMaterial().isSolid()) {
                            ++var15;
                        }
                        if (p_76484_1_.getBlock(var12, p_76484_4_, var14 - 1).getMaterial().isSolid()) {
                            ++var15;
                        }
                        if (p_76484_1_.getBlock(var12, p_76484_4_, var14 + 1).getMaterial().isSolid()) {
                            ++var15;
                        }
                        if (var15 == 1) {
                            p_76484_1_.setBlock(var12, p_76484_4_, var14, Blocks.chest, 0, 2);
                            final WeightedRandomChestContent[] var16 = WeightedRandomChestContent.func_92080_a(WorldGenDungeons.field_111189_a, Items.enchanted_book.func_92114_b(p_76484_2_));
                            final TileEntityChest var17 = (TileEntityChest)p_76484_1_.getTileEntity(var12, p_76484_4_, var14);
                            if (var17 != null) {
                                WeightedRandomChestContent.generateChestContents(p_76484_2_, var16, var17, 8);
                                break;
                            }
                            break;
                        }
                    }
                }
            }
            p_76484_1_.setBlock(p_76484_3_, p_76484_4_, p_76484_5_, Blocks.mob_spawner, 0, 2);
            final TileEntityMobSpawner var18 = (TileEntityMobSpawner)p_76484_1_.getTileEntity(p_76484_3_, p_76484_4_, p_76484_5_);
            if (var18 != null) {
                var18.func_145881_a().setMobID(this.pickMobSpawner(p_76484_2_));
            }
            else {
                System.err.println("Failed to fetch mob spawner entity at (" + p_76484_3_ + ", " + p_76484_4_ + ", " + p_76484_5_ + ")");
            }
            return true;
        }
        return false;
    }
    
    private String pickMobSpawner(final Random p_76543_1_) {
        final int var2 = p_76543_1_.nextInt(4);
        return (var2 == 0) ? "Skeleton" : ((var2 == 1) ? "Zombie" : ((var2 == 2) ? "Zombie" : ((var2 == 3) ? "Spider" : "")));
    }
    
    static {
        field_111189_a = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 10), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 4, 10), new WeightedRandomChestContent(Items.bread, 0, 1, 1, 10), new WeightedRandomChestContent(Items.wheat, 0, 1, 4, 10), new WeightedRandomChestContent(Items.gunpowder, 0, 1, 4, 10), new WeightedRandomChestContent(Items.string, 0, 1, 4, 10), new WeightedRandomChestContent(Items.bucket, 0, 1, 1, 10), new WeightedRandomChestContent(Items.golden_apple, 0, 1, 1, 1), new WeightedRandomChestContent(Items.redstone, 0, 1, 4, 10), new WeightedRandomChestContent(Items.record_13, 0, 1, 1, 10), new WeightedRandomChestContent(Items.record_cat, 0, 1, 1, 10), new WeightedRandomChestContent(Items.name_tag, 0, 1, 1, 10), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 2), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 5), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1) };
    }
}
