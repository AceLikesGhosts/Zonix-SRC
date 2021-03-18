package net.minecraft.block;

import net.minecraft.creativetab.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.texture.*;

public class BlockWall extends Block
{
    public static final String[] field_150092_a;
    private static final String __OBFID = "CL_00000331";
    
    public BlockWall(final Block p_i45435_1_) {
        super(p_i45435_1_.blockMaterial);
        this.setHardness(p_i45435_1_.blockHardness);
        this.setResistance(p_i45435_1_.blockResistance / 3.0f);
        this.setStepSound(p_i45435_1_.stepSound);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return (p_149691_2_ == 1) ? Blocks.mossy_cobblestone.getBlockTextureFromSide(p_149691_1_) : Blocks.cobblestone.getBlockTextureFromSide(p_149691_1_);
    }
    
    @Override
    public int getRenderType() {
        return 32;
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
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        final boolean var5 = this.func_150091_e(p_149719_1_, p_149719_2_, p_149719_3_, p_149719_4_ - 1);
        final boolean var6 = this.func_150091_e(p_149719_1_, p_149719_2_, p_149719_3_, p_149719_4_ + 1);
        final boolean var7 = this.func_150091_e(p_149719_1_, p_149719_2_ - 1, p_149719_3_, p_149719_4_);
        final boolean var8 = this.func_150091_e(p_149719_1_, p_149719_2_ + 1, p_149719_3_, p_149719_4_);
        float var9 = 0.25f;
        float var10 = 0.75f;
        float var11 = 0.25f;
        float var12 = 0.75f;
        float var13 = 1.0f;
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
        if (var5 && var6 && !var7 && !var8) {
            var13 = 0.8125f;
            var9 = 0.3125f;
            var10 = 0.6875f;
        }
        else if (!var5 && !var6 && var7 && var8) {
            var13 = 0.8125f;
            var11 = 0.3125f;
            var12 = 0.6875f;
        }
        this.setBlockBounds(var9, 0.0f, var11, var10, var13, var12);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World p_149668_1_, final int p_149668_2_, final int p_149668_3_, final int p_149668_4_) {
        this.setBlockBoundsBasedOnState(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
        this.field_149756_F = 1.5;
        return super.getCollisionBoundingBoxFromPool(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
    }
    
    public boolean func_150091_e(final IBlockAccess p_150091_1_, final int p_150091_2_, final int p_150091_3_, final int p_150091_4_) {
        final Block var5 = p_150091_1_.getBlock(p_150091_2_, p_150091_3_, p_150091_4_);
        return var5 == this || var5 == Blocks.fence_gate || (var5.blockMaterial.isOpaque() && var5.renderAsNormalBlock() && var5.blockMaterial != Material.field_151572_C);
    }
    
    @Override
    public void getSubBlocks(final Item p_149666_1_, final CreativeTabs p_149666_2_, final List p_149666_3_) {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
    }
    
    @Override
    public int damageDropped(final int p_149692_1_) {
        return p_149692_1_;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess p_149646_1_, final int p_149646_2_, final int p_149646_3_, final int p_149646_4_, final int p_149646_5_) {
        return p_149646_5_ != 0 || super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
    }
    
    static {
        field_150092_a = new String[] { "normal", "mossy" };
    }
}
