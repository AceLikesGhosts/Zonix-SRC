package net.minecraft.world.gen.feature;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import net.minecraft.inventory.*;

public class WorldGeneratorBonusChest extends WorldGenerator
{
    private final WeightedRandomChestContent[] theBonusChestGenerator;
    private final int itemsToGenerateInBonusChest;
    private static final String __OBFID = "CL_00000403";
    
    public WorldGeneratorBonusChest(final WeightedRandomChestContent[] p_i2010_1_, final int p_i2010_2_) {
        this.theBonusChestGenerator = p_i2010_1_;
        this.itemsToGenerateInBonusChest = p_i2010_2_;
    }
    
    @Override
    public boolean generate(final World p_76484_1_, final Random p_76484_2_, final int p_76484_3_, int p_76484_4_, final int p_76484_5_) {
        Block var6;
        while (((var6 = p_76484_1_.getBlock(p_76484_3_, p_76484_4_, p_76484_5_)).getMaterial() == Material.air || var6.getMaterial() == Material.leaves) && p_76484_4_ > 1) {
            --p_76484_4_;
        }
        if (p_76484_4_ < 1) {
            return false;
        }
        ++p_76484_4_;
        for (int var7 = 0; var7 < 4; ++var7) {
            final int var8 = p_76484_3_ + p_76484_2_.nextInt(4) - p_76484_2_.nextInt(4);
            final int var9 = p_76484_4_ + p_76484_2_.nextInt(3) - p_76484_2_.nextInt(3);
            final int var10 = p_76484_5_ + p_76484_2_.nextInt(4) - p_76484_2_.nextInt(4);
            if (p_76484_1_.isAirBlock(var8, var9, var10) && World.doesBlockHaveSolidTopSurface(p_76484_1_, var8, var9 - 1, var10)) {
                p_76484_1_.setBlock(var8, var9, var10, Blocks.chest, 0, 2);
                final TileEntityChest var11 = (TileEntityChest)p_76484_1_.getTileEntity(var8, var9, var10);
                if (var11 != null && var11 != null) {
                    WeightedRandomChestContent.generateChestContents(p_76484_2_, this.theBonusChestGenerator, var11, this.itemsToGenerateInBonusChest);
                }
                if (p_76484_1_.isAirBlock(var8 - 1, var9, var10) && World.doesBlockHaveSolidTopSurface(p_76484_1_, var8 - 1, var9 - 1, var10)) {
                    p_76484_1_.setBlock(var8 - 1, var9, var10, Blocks.torch, 0, 2);
                }
                if (p_76484_1_.isAirBlock(var8 + 1, var9, var10) && World.doesBlockHaveSolidTopSurface(p_76484_1_, var8 - 1, var9 - 1, var10)) {
                    p_76484_1_.setBlock(var8 + 1, var9, var10, Blocks.torch, 0, 2);
                }
                if (p_76484_1_.isAirBlock(var8, var9, var10 - 1) && World.doesBlockHaveSolidTopSurface(p_76484_1_, var8 - 1, var9 - 1, var10)) {
                    p_76484_1_.setBlock(var8, var9, var10 - 1, Blocks.torch, 0, 2);
                }
                if (p_76484_1_.isAirBlock(var8, var9, var10 + 1) && World.doesBlockHaveSolidTopSurface(p_76484_1_, var8 - 1, var9 - 1, var10)) {
                    p_76484_1_.setBlock(var8, var9, var10 + 1, Blocks.torch, 0, 2);
                }
                return true;
            }
        }
        return false;
    }
}
