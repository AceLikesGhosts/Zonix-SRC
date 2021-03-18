package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import java.util.*;

public class BlockLadder extends Block
{
    private static final String __OBFID = "CL_00000262";
    
    protected BlockLadder() {
        super(Material.circuits);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World p_149668_1_, final int p_149668_2_, final int p_149668_3_, final int p_149668_4_) {
        this.setBlockBoundsBasedOnState(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
        return super.getCollisionBoundingBoxFromPool(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(final World p_149633_1_, final int p_149633_2_, final int p_149633_3_, final int p_149633_4_) {
        this.setBlockBoundsBasedOnState(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
        return super.getSelectedBoundingBoxFromPool(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        this.func_149797_b(p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_));
    }
    
    public void func_149797_b(final int p_149797_1_) {
        final float var3 = 0.125f;
        if (p_149797_1_ == 2) {
            this.setBlockBounds(0.0f, 0.0f, 1.0f - var3, 1.0f, 1.0f, 1.0f);
        }
        if (p_149797_1_ == 3) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, var3);
        }
        if (p_149797_1_ == 4) {
            this.setBlockBounds(1.0f - var3, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        if (p_149797_1_ == 5) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, var3, 1.0f, 1.0f);
        }
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
    public int getRenderType() {
        return 8;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World p_149742_1_, final int p_149742_2_, final int p_149742_3_, final int p_149742_4_) {
        return p_149742_1_.getBlock(p_149742_2_ - 1, p_149742_3_, p_149742_4_).isNormalCube() || p_149742_1_.getBlock(p_149742_2_ + 1, p_149742_3_, p_149742_4_).isNormalCube() || p_149742_1_.getBlock(p_149742_2_, p_149742_3_, p_149742_4_ - 1).isNormalCube() || p_149742_1_.getBlock(p_149742_2_, p_149742_3_, p_149742_4_ + 1).isNormalCube();
    }
    
    @Override
    public int onBlockPlaced(final World p_149660_1_, final int p_149660_2_, final int p_149660_3_, final int p_149660_4_, final int p_149660_5_, final float p_149660_6_, final float p_149660_7_, final float p_149660_8_, final int p_149660_9_) {
        int var10 = p_149660_9_;
        if ((p_149660_9_ == 0 || p_149660_5_ == 2) && p_149660_1_.getBlock(p_149660_2_, p_149660_3_, p_149660_4_ + 1).isNormalCube()) {
            var10 = 2;
        }
        if ((var10 == 0 || p_149660_5_ == 3) && p_149660_1_.getBlock(p_149660_2_, p_149660_3_, p_149660_4_ - 1).isNormalCube()) {
            var10 = 3;
        }
        if ((var10 == 0 || p_149660_5_ == 4) && p_149660_1_.getBlock(p_149660_2_ + 1, p_149660_3_, p_149660_4_).isNormalCube()) {
            var10 = 4;
        }
        if ((var10 == 0 || p_149660_5_ == 5) && p_149660_1_.getBlock(p_149660_2_ - 1, p_149660_3_, p_149660_4_).isNormalCube()) {
            var10 = 5;
        }
        return var10;
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        final int var6 = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_);
        boolean var7 = false;
        if (var6 == 2 && p_149695_1_.getBlock(p_149695_2_, p_149695_3_, p_149695_4_ + 1).isNormalCube()) {
            var7 = true;
        }
        if (var6 == 3 && p_149695_1_.getBlock(p_149695_2_, p_149695_3_, p_149695_4_ - 1).isNormalCube()) {
            var7 = true;
        }
        if (var6 == 4 && p_149695_1_.getBlock(p_149695_2_ + 1, p_149695_3_, p_149695_4_).isNormalCube()) {
            var7 = true;
        }
        if (var6 == 5 && p_149695_1_.getBlock(p_149695_2_ - 1, p_149695_3_, p_149695_4_).isNormalCube()) {
            var7 = true;
        }
        if (!var7) {
            this.dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, var6, 0);
            p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
        }
        super.onNeighborBlockChange(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
    }
    
    @Override
    public int quantityDropped(final Random p_149745_1_) {
        return 1;
    }
}
