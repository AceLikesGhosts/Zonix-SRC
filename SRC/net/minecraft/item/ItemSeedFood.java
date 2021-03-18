package net.minecraft.item;

import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;

public class ItemSeedFood extends ItemFood
{
    private Block field_150908_b;
    private Block soilId;
    private static final String __OBFID = "CL_00000060";
    
    public ItemSeedFood(final int p_i45351_1_, final float p_i45351_2_, final Block p_i45351_3_, final Block p_i45351_4_) {
        super(p_i45351_1_, p_i45351_2_, false);
        this.field_150908_b = p_i45351_3_;
        this.soilId = p_i45351_4_;
    }
    
    @Override
    public boolean onItemUse(final ItemStack p_77648_1_, final EntityPlayer p_77648_2_, final World p_77648_3_, final int p_77648_4_, final int p_77648_5_, final int p_77648_6_, final int p_77648_7_, final float p_77648_8_, final float p_77648_9_, final float p_77648_10_) {
        if (p_77648_7_ != 1) {
            return false;
        }
        if (!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_) || !p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_ + 1, p_77648_6_, p_77648_7_, p_77648_1_)) {
            return false;
        }
        if (p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_) == this.soilId && p_77648_3_.isAirBlock(p_77648_4_, p_77648_5_ + 1, p_77648_6_)) {
            p_77648_3_.setBlock(p_77648_4_, p_77648_5_ + 1, p_77648_6_, this.field_150908_b);
            --p_77648_1_.stackSize;
            return true;
        }
        return false;
    }
}
