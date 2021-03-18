package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;

public class BlockLeavesBase extends Block
{
    protected boolean field_150121_P;
    private static final String __OBFID = "CL_00000326";
    
    protected BlockLeavesBase(final Material p_i45433_1_, final boolean p_i45433_2_) {
        super(p_i45433_1_);
        this.field_150121_P = p_i45433_2_;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess p_149646_1_, final int p_149646_2_, final int p_149646_3_, final int p_149646_4_, final int p_149646_5_) {
        final Block var6 = p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_);
        return (this.field_150121_P || var6 != this) && super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
    }
}
