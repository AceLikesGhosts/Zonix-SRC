package net.minecraft.item;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.block.*;

public class ItemDoor extends Item
{
    private Material doorMaterial;
    private static final String __OBFID = "CL_00000020";
    
    public ItemDoor(final Material p_i45334_1_) {
        this.doorMaterial = p_i45334_1_;
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public boolean onItemUse(final ItemStack p_77648_1_, final EntityPlayer p_77648_2_, final World p_77648_3_, final int p_77648_4_, int p_77648_5_, final int p_77648_6_, final int p_77648_7_, final float p_77648_8_, final float p_77648_9_, final float p_77648_10_) {
        if (p_77648_7_ != 1) {
            return false;
        }
        ++p_77648_5_;
        Block var11;
        if (this.doorMaterial == Material.wood) {
            var11 = Blocks.wooden_door;
        }
        else {
            var11 = Blocks.iron_door;
        }
        if (!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_) || !p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_ + 1, p_77648_6_, p_77648_7_, p_77648_1_)) {
            return false;
        }
        if (!var11.canPlaceBlockAt(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_)) {
            return false;
        }
        final int var12 = MathHelper.floor_double((p_77648_2_.rotationYaw + 180.0f) * 4.0f / 360.0f - 0.5) & 0x3;
        func_150924_a(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, var12, var11);
        --p_77648_1_.stackSize;
        return true;
    }
    
    public static void func_150924_a(final World p_150924_0_, final int p_150924_1_, final int p_150924_2_, final int p_150924_3_, final int p_150924_4_, final Block p_150924_5_) {
        byte var6 = 0;
        byte var7 = 0;
        if (p_150924_4_ == 0) {
            var7 = 1;
        }
        if (p_150924_4_ == 1) {
            var6 = -1;
        }
        if (p_150924_4_ == 2) {
            var7 = -1;
        }
        if (p_150924_4_ == 3) {
            var6 = 1;
        }
        final int var8 = (p_150924_0_.getBlock(p_150924_1_ - var6, p_150924_2_, p_150924_3_ - var7).isNormalCube() + p_150924_0_.getBlock(p_150924_1_ - var6, p_150924_2_ + 1, p_150924_3_ - var7).isNormalCube()) ? 1 : 0;
        final int var9 = (p_150924_0_.getBlock(p_150924_1_ + var6, p_150924_2_, p_150924_3_ + var7).isNormalCube() + p_150924_0_.getBlock(p_150924_1_ + var6, p_150924_2_ + 1, p_150924_3_ + var7).isNormalCube()) ? 1 : 0;
        final boolean var10 = p_150924_0_.getBlock(p_150924_1_ - var6, p_150924_2_, p_150924_3_ - var7) == p_150924_5_ || p_150924_0_.getBlock(p_150924_1_ - var6, p_150924_2_ + 1, p_150924_3_ - var7) == p_150924_5_;
        final boolean var11 = p_150924_0_.getBlock(p_150924_1_ + var6, p_150924_2_, p_150924_3_ + var7) == p_150924_5_ || p_150924_0_.getBlock(p_150924_1_ + var6, p_150924_2_ + 1, p_150924_3_ + var7) == p_150924_5_;
        boolean var12 = false;
        if (var10 && !var11) {
            var12 = true;
        }
        else if (var9 > var8) {
            var12 = true;
        }
        p_150924_0_.setBlock(p_150924_1_, p_150924_2_, p_150924_3_, p_150924_5_, p_150924_4_, 2);
        p_150924_0_.setBlock(p_150924_1_, p_150924_2_ + 1, p_150924_3_, p_150924_5_, 0x8 | (var12 ? 1 : 0), 2);
        p_150924_0_.notifyBlocksOfNeighborChange(p_150924_1_, p_150924_2_, p_150924_3_, p_150924_5_);
        p_150924_0_.notifyBlocksOfNeighborChange(p_150924_1_, p_150924_2_ + 1, p_150924_3_, p_150924_5_);
    }
}
