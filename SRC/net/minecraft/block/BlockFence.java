package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class BlockFence extends Block
{
    private final String field_149827_a;
    private static final String __OBFID = "CL_00000242";
    
    public BlockFence(final String p_i45406_1_, final Material p_i45406_2_) {
        super(p_i45406_2_);
        this.field_149827_a = p_i45406_1_;
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public void addCollisionBoxesToList(final World p_149743_1_, final int p_149743_2_, final int p_149743_3_, final int p_149743_4_, final AxisAlignedBB p_149743_5_, final List p_149743_6_, final Entity p_149743_7_) {
        final boolean var8 = this.func_149826_e(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_ - 1);
        final boolean var9 = this.func_149826_e(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_ + 1);
        final boolean var10 = this.func_149826_e(p_149743_1_, p_149743_2_ - 1, p_149743_3_, p_149743_4_);
        final boolean var11 = this.func_149826_e(p_149743_1_, p_149743_2_ + 1, p_149743_3_, p_149743_4_);
        float var12 = 0.375f;
        float var13 = 0.625f;
        float var14 = 0.375f;
        float var15 = 0.625f;
        if (var8) {
            var14 = 0.0f;
        }
        if (var9) {
            var15 = 1.0f;
        }
        if (var8 || var9) {
            this.setBlockBounds(var12, 0.0f, var14, var13, 1.5f, var15);
            super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        }
        var14 = 0.375f;
        var15 = 0.625f;
        if (var10) {
            var12 = 0.0f;
        }
        if (var11) {
            var13 = 1.0f;
        }
        if (var10 || var11 || (!var8 && !var9)) {
            this.setBlockBounds(var12, 0.0f, var14, var13, 1.5f, var15);
            super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        }
        if (var8) {
            var14 = 0.0f;
        }
        if (var9) {
            var15 = 1.0f;
        }
        this.setBlockBounds(var12, 0.0f, var14, var13, 1.0f, var15);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        final boolean var5 = this.func_149826_e(p_149719_1_, p_149719_2_, p_149719_3_, p_149719_4_ - 1);
        final boolean var6 = this.func_149826_e(p_149719_1_, p_149719_2_, p_149719_3_, p_149719_4_ + 1);
        final boolean var7 = this.func_149826_e(p_149719_1_, p_149719_2_ - 1, p_149719_3_, p_149719_4_);
        final boolean var8 = this.func_149826_e(p_149719_1_, p_149719_2_ + 1, p_149719_3_, p_149719_4_);
        float var9 = 0.375f;
        float var10 = 0.625f;
        float var11 = 0.375f;
        float var12 = 0.625f;
        if (var5) {
            var11 = 0.0f;
        }
        if (var6) {
            var12 = 1.0f;
        }
        if (var7) {
            var9 = 0.0f;
        }
        if (var8) {
            var10 = 1.0f;
        }
        this.setBlockBounds(var9, 0.0f, var11, var10, 1.0f, var12);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public boolean getBlocksMovement(final IBlockAccess p_149655_1_, final int p_149655_2_, final int p_149655_3_, final int p_149655_4_) {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return 11;
    }
    
    public boolean func_149826_e(final IBlockAccess p_149826_1_, final int p_149826_2_, final int p_149826_3_, final int p_149826_4_) {
        final Block var5 = p_149826_1_.getBlock(p_149826_2_, p_149826_3_, p_149826_4_);
        return var5 == this || var5 == Blocks.fence_gate || (var5.blockMaterial.isOpaque() && var5.renderAsNormalBlock() && var5.blockMaterial != Material.field_151572_C);
    }
    
    public static boolean func_149825_a(final Block p_149825_0_) {
        return p_149825_0_ == Blocks.fence || p_149825_0_ == Blocks.nether_brick_fence;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess p_149646_1_, final int p_149646_2_, final int p_149646_3_, final int p_149646_4_, final int p_149646_5_) {
        return true;
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon(this.field_149827_a);
    }
    
    @Override
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        return p_149727_1_.isClient || ItemLead.func_150909_a(p_149727_5_, p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);
    }
}
