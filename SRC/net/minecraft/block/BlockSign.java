package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.texture.*;

public class BlockSign extends BlockContainer
{
    private Class field_149968_a;
    private boolean field_149967_b;
    private static final String __OBFID = "CL_00000306";
    
    protected BlockSign(final Class p_i45426_1_, final boolean p_i45426_2_) {
        super(Material.wood);
        this.field_149967_b = p_i45426_2_;
        this.field_149968_a = p_i45426_1_;
        final float var3 = 0.25f;
        final float var4 = 1.0f;
        this.setBlockBounds(0.5f - var3, 0.0f, 0.5f - var3, 0.5f + var3, var4, 0.5f + var3);
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return Blocks.planks.getBlockTextureFromSide(p_149691_1_);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World p_149668_1_, final int p_149668_2_, final int p_149668_3_, final int p_149668_4_) {
        return null;
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(final World p_149633_1_, final int p_149633_2_, final int p_149633_3_, final int p_149633_4_) {
        this.setBlockBoundsBasedOnState(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
        return super.getSelectedBoundingBoxFromPool(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        if (!this.field_149967_b) {
            final int var5 = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
            final float var6 = 0.28125f;
            final float var7 = 0.78125f;
            final float var8 = 0.0f;
            final float var9 = 1.0f;
            final float var10 = 0.125f;
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            if (var5 == 2) {
                this.setBlockBounds(var8, var6, 1.0f - var10, var9, var7, 1.0f);
            }
            if (var5 == 3) {
                this.setBlockBounds(var8, var6, 0.0f, var9, var7, var10);
            }
            if (var5 == 4) {
                this.setBlockBounds(1.0f - var10, var6, var8, 1.0f, var7, var9);
            }
            if (var5 == 5) {
                this.setBlockBounds(0.0f, var6, var8, var10, var7, var9);
            }
        }
    }
    
    @Override
    public int getRenderType() {
        return -1;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public boolean getBlocksMovement(final IBlockAccess p_149655_1_, final int p_149655_2_, final int p_149655_3_, final int p_149655_4_) {
        return true;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World p_149915_1_, final int p_149915_2_) {
        try {
            return this.field_149968_a.newInstance();
        }
        catch (Exception var4) {
            throw new RuntimeException(var4);
        }
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return Items.sign;
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        boolean var6 = false;
        if (this.field_149967_b) {
            if (!p_149695_1_.getBlock(p_149695_2_, p_149695_3_ - 1, p_149695_4_).getMaterial().isSolid()) {
                var6 = true;
            }
        }
        else {
            final int var7 = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_);
            var6 = true;
            if (var7 == 2 && p_149695_1_.getBlock(p_149695_2_, p_149695_3_, p_149695_4_ + 1).getMaterial().isSolid()) {
                var6 = false;
            }
            if (var7 == 3 && p_149695_1_.getBlock(p_149695_2_, p_149695_3_, p_149695_4_ - 1).getMaterial().isSolid()) {
                var6 = false;
            }
            if (var7 == 4 && p_149695_1_.getBlock(p_149695_2_ + 1, p_149695_3_, p_149695_4_).getMaterial().isSolid()) {
                var6 = false;
            }
            if (var7 == 5 && p_149695_1_.getBlock(p_149695_2_ - 1, p_149695_3_, p_149695_4_).getMaterial().isSolid()) {
                var6 = false;
            }
        }
        if (var6) {
            this.dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_), 0);
            p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
        }
        super.onNeighborBlockChange(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
    }
    
    @Override
    public Item getItem(final World p_149694_1_, final int p_149694_2_, final int p_149694_3_, final int p_149694_4_) {
        return Items.sign;
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
    }
}
