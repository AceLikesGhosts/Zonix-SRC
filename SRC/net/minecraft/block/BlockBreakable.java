package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;

public class BlockBreakable extends Block
{
    private boolean field_149996_a;
    private String field_149995_b;
    private static final String __OBFID = "CL_00000254";
    
    protected BlockBreakable(final String p_i45411_1_, final Material p_i45411_2_, final boolean p_i45411_3_) {
        super(p_i45411_2_);
        this.field_149996_a = p_i45411_3_;
        this.field_149995_b = p_i45411_1_;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess p_149646_1_, final int p_149646_2_, final int p_149646_3_, final int p_149646_4_, final int p_149646_5_) {
        final Block var6 = p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_);
        if (this == Blocks.glass || this == Blocks.stained_glass) {
            if (p_149646_1_.getBlockMetadata(p_149646_2_, p_149646_3_, p_149646_4_) != p_149646_1_.getBlockMetadata(p_149646_2_ - Facing.offsetsXForSide[p_149646_5_], p_149646_3_ - Facing.offsetsYForSide[p_149646_5_], p_149646_4_ - Facing.offsetsZForSide[p_149646_5_])) {
                return true;
            }
            if (var6 == this) {
                return false;
            }
        }
        return (this.field_149996_a || var6 != this) && super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon(this.field_149995_b);
    }
}
