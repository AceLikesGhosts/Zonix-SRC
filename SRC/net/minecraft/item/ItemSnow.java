package net.minecraft.item;

import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.init.*;

public class ItemSnow extends ItemBlockWithMetadata
{
    private static final String __OBFID = "CL_00000068";
    
    public ItemSnow(final Block p_i45354_1_, final Block p_i45354_2_) {
        super(p_i45354_1_, p_i45354_2_);
    }
    
    @Override
    public boolean onItemUse(final ItemStack p_77648_1_, final EntityPlayer p_77648_2_, final World p_77648_3_, final int p_77648_4_, final int p_77648_5_, final int p_77648_6_, final int p_77648_7_, final float p_77648_8_, final float p_77648_9_, final float p_77648_10_) {
        if (p_77648_1_.stackSize == 0) {
            return false;
        }
        if (!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_)) {
            return false;
        }
        final Block var11 = p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);
        if (var11 == Blocks.snow_layer) {
            final int var12 = p_77648_3_.getBlockMetadata(p_77648_4_, p_77648_5_, p_77648_6_);
            final int var13 = var12 & 0x7;
            if (var13 <= 6 && p_77648_3_.checkNoEntityCollision(this.field_150939_a.getCollisionBoundingBoxFromPool(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_)) && p_77648_3_.setBlockMetadataWithNotify(p_77648_4_, p_77648_5_, p_77648_6_, var13 + 1 | (var12 & 0xFFFFFFF8), 2)) {
                p_77648_3_.playSoundEffect(p_77648_4_ + 0.5f, p_77648_5_ + 0.5f, p_77648_6_ + 0.5f, this.field_150939_a.stepSound.func_150496_b(), (this.field_150939_a.stepSound.func_150497_c() + 1.0f) / 2.0f, this.field_150939_a.stepSound.func_150494_d() * 0.8f);
                --p_77648_1_.stackSize;
                return true;
            }
        }
        return super.onItemUse(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
    }
}
