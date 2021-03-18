package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.block.*;

public class ItemBed extends Item
{
    private static final String __OBFID = "CL_00001771";
    
    public ItemBed() {
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public boolean onItemUse(final ItemStack p_77648_1_, final EntityPlayer p_77648_2_, final World p_77648_3_, final int p_77648_4_, int p_77648_5_, final int p_77648_6_, final int p_77648_7_, final float p_77648_8_, final float p_77648_9_, final float p_77648_10_) {
        if (p_77648_3_.isClient) {
            return true;
        }
        if (p_77648_7_ != 1) {
            return false;
        }
        ++p_77648_5_;
        final BlockBed var11 = (BlockBed)Blocks.bed;
        final int var12 = MathHelper.floor_double(p_77648_2_.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        byte var13 = 0;
        byte var14 = 0;
        if (var12 == 0) {
            var14 = 1;
        }
        if (var12 == 1) {
            var13 = -1;
        }
        if (var12 == 2) {
            var14 = -1;
        }
        if (var12 == 3) {
            var13 = 1;
        }
        if (!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_) || !p_77648_2_.canPlayerEdit(p_77648_4_ + var13, p_77648_5_, p_77648_6_ + var14, p_77648_7_, p_77648_1_)) {
            return false;
        }
        if (p_77648_3_.isAirBlock(p_77648_4_, p_77648_5_, p_77648_6_) && p_77648_3_.isAirBlock(p_77648_4_ + var13, p_77648_5_, p_77648_6_ + var14) && World.doesBlockHaveSolidTopSurface(p_77648_3_, p_77648_4_, p_77648_5_ - 1, p_77648_6_) && World.doesBlockHaveSolidTopSurface(p_77648_3_, p_77648_4_ + var13, p_77648_5_ - 1, p_77648_6_ + var14)) {
            p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, var11, var12, 3);
            if (p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_) == var11) {
                p_77648_3_.setBlock(p_77648_4_ + var13, p_77648_5_, p_77648_6_ + var14, var11, var12 + 8, 3);
            }
            --p_77648_1_.stackSize;
            return true;
        }
        return false;
    }
}
