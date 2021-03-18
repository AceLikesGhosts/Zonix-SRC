package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;

public class ItemSign extends Item
{
    private static final String __OBFID = "CL_00000064";
    
    public ItemSign() {
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public boolean onItemUse(final ItemStack p_77648_1_, final EntityPlayer p_77648_2_, final World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, final int p_77648_7_, final float p_77648_8_, final float p_77648_9_, final float p_77648_10_) {
        if (p_77648_7_ == 0) {
            return false;
        }
        if (!p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_).getMaterial().isSolid()) {
            return false;
        }
        if (p_77648_7_ == 1) {
            ++p_77648_5_;
        }
        if (p_77648_7_ == 2) {
            --p_77648_6_;
        }
        if (p_77648_7_ == 3) {
            ++p_77648_6_;
        }
        if (p_77648_7_ == 4) {
            --p_77648_4_;
        }
        if (p_77648_7_ == 5) {
            ++p_77648_4_;
        }
        if (!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_)) {
            return false;
        }
        if (!Blocks.standing_sign.canPlaceBlockAt(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_)) {
            return false;
        }
        if (p_77648_3_.isClient) {
            return true;
        }
        if (p_77648_7_ == 1) {
            final int var11 = MathHelper.floor_double((p_77648_2_.rotationYaw + 180.0f) * 16.0f / 360.0f + 0.5) & 0xF;
            p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, Blocks.standing_sign, var11, 3);
        }
        else {
            p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, Blocks.wall_sign, p_77648_7_, 3);
        }
        --p_77648_1_.stackSize;
        final TileEntitySign var12 = (TileEntitySign)p_77648_3_.getTileEntity(p_77648_4_, p_77648_5_, p_77648_6_);
        if (var12 != null) {
            p_77648_2_.func_146100_a(var12);
        }
        return true;
    }
}
