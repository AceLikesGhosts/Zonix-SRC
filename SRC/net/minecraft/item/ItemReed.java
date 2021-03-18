package net.minecraft.item;

import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;

public class ItemReed extends Item
{
    private Block field_150935_a;
    private static final String __OBFID = "CL_00001773";
    
    public ItemReed(final Block p_i45329_1_) {
        this.field_150935_a = p_i45329_1_;
    }
    
    @Override
    public boolean onItemUse(final ItemStack p_77648_1_, final EntityPlayer p_77648_2_, final World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, final float p_77648_8_, final float p_77648_9_, final float p_77648_10_) {
        final Block var11 = p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);
        if (var11 == Blocks.snow_layer && (p_77648_3_.getBlockMetadata(p_77648_4_, p_77648_5_, p_77648_6_) & 0x7) < 1) {
            p_77648_7_ = 1;
        }
        else if (var11 != Blocks.vine && var11 != Blocks.tallgrass && var11 != Blocks.deadbush) {
            if (p_77648_7_ == 0) {
                --p_77648_5_;
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
        }
        if (!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_)) {
            return false;
        }
        if (p_77648_1_.stackSize == 0) {
            return false;
        }
        if (p_77648_3_.canPlaceEntityOnSide(this.field_150935_a, p_77648_4_, p_77648_5_, p_77648_6_, false, p_77648_7_, null, p_77648_1_)) {
            final int var12 = this.field_150935_a.onBlockPlaced(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_, 0);
            if (p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, this.field_150935_a, var12, 3)) {
                if (p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_) == this.field_150935_a) {
                    this.field_150935_a.onBlockPlacedBy(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_2_, p_77648_1_);
                    this.field_150935_a.onPostBlockPlaced(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, var12);
                }
                p_77648_3_.playSoundEffect(p_77648_4_ + 0.5f, p_77648_5_ + 0.5f, p_77648_6_ + 0.5f, this.field_150935_a.stepSound.func_150496_b(), (this.field_150935_a.stepSound.func_150497_c() + 1.0f) / 2.0f, this.field_150935_a.stepSound.func_150494_d() * 0.8f);
                --p_77648_1_.stackSize;
            }
        }
        return true;
    }
}
