package net.minecraft.item;

import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;

public class ItemLilyPad extends ItemColored
{
    private static final String __OBFID = "CL_00000074";
    
    public ItemLilyPad(final Block p_i45357_1_) {
        super(p_i45357_1_, false);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack p_77659_1_, final World p_77659_2_, final EntityPlayer p_77659_3_) {
        final MovingObjectPosition var4 = this.getMovingObjectPositionFromPlayer(p_77659_2_, p_77659_3_, true);
        if (var4 == null) {
            return p_77659_1_;
        }
        if (var4.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final int var5 = var4.blockX;
            final int var6 = var4.blockY;
            final int var7 = var4.blockZ;
            if (!p_77659_2_.canMineBlock(p_77659_3_, var5, var6, var7)) {
                return p_77659_1_;
            }
            if (!p_77659_3_.canPlayerEdit(var5, var6, var7, var4.sideHit, p_77659_1_)) {
                return p_77659_1_;
            }
            if (p_77659_2_.getBlock(var5, var6, var7).getMaterial() == Material.water && p_77659_2_.getBlockMetadata(var5, var6, var7) == 0 && p_77659_2_.isAirBlock(var5, var6 + 1, var7)) {
                p_77659_2_.setBlock(var5, var6 + 1, var7, Blocks.waterlily);
                if (!p_77659_3_.capabilities.isCreativeMode) {
                    --p_77659_1_.stackSize;
                }
            }
        }
        return p_77659_1_;
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack p_82790_1_, final int p_82790_2_) {
        return Blocks.waterlily.getRenderColor(p_82790_1_.getItemDamage());
    }
}
