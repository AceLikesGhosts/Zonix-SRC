package net.minecraft.block;

import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;

public class BlockLilyPad extends BlockBush
{
    private static final String __OBFID = "CL_00000332";
    
    protected BlockLilyPad() {
        final float var1 = 0.5f;
        final float var2 = 0.015625f;
        this.setBlockBounds(0.5f - var1, 0.0f, 0.5f - var1, 0.5f + var1, var2, 0.5f + var1);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public int getRenderType() {
        return 23;
    }
    
    @Override
    public void addCollisionBoxesToList(final World p_149743_1_, final int p_149743_2_, final int p_149743_3_, final int p_149743_4_, final AxisAlignedBB p_149743_5_, final List p_149743_6_, final Entity p_149743_7_) {
        if (p_149743_7_ == null || !(p_149743_7_ instanceof EntityBoat)) {
            super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World p_149668_1_, final int p_149668_2_, final int p_149668_3_, final int p_149668_4_) {
        return AxisAlignedBB.getBoundingBox(p_149668_2_ + this.field_149759_B, p_149668_3_ + this.field_149760_C, p_149668_4_ + this.field_149754_D, p_149668_2_ + this.field_149755_E, p_149668_3_ + this.field_149756_F, p_149668_4_ + this.field_149757_G);
    }
    
    @Override
    public int getBlockColor() {
        return 2129968;
    }
    
    @Override
    public int getRenderColor(final int p_149741_1_) {
        return 2129968;
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess p_149720_1_, final int p_149720_2_, final int p_149720_3_, final int p_149720_4_) {
        return 2129968;
    }
    
    @Override
    protected boolean func_149854_a(final Block p_149854_1_) {
        return p_149854_1_ == Blocks.water;
    }
    
    @Override
    public boolean canBlockStay(final World p_149718_1_, final int p_149718_2_, final int p_149718_3_, final int p_149718_4_) {
        return p_149718_3_ >= 0 && p_149718_3_ < 256 && (p_149718_1_.getBlock(p_149718_2_, p_149718_3_ - 1, p_149718_4_).getMaterial() == Material.water && p_149718_1_.getBlockMetadata(p_149718_2_, p_149718_3_ - 1, p_149718_4_) == 0);
    }
}
