package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;

public class BlockPistonMoving extends BlockContainer
{
    private static final String __OBFID = "CL_00000368";
    
    public BlockPistonMoving() {
        super(Material.piston);
        this.setHardness(-1.0f);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World p_149915_1_, final int p_149915_2_) {
        return null;
    }
    
    @Override
    public void onBlockAdded(final World p_149726_1_, final int p_149726_2_, final int p_149726_3_, final int p_149726_4_) {
    }
    
    @Override
    public void breakBlock(final World p_149749_1_, final int p_149749_2_, final int p_149749_3_, final int p_149749_4_, final Block p_149749_5_, final int p_149749_6_) {
        final TileEntity var7 = p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
        if (var7 instanceof TileEntityPiston) {
            ((TileEntityPiston)var7).func_145866_f();
        }
        else {
            super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
        }
    }
    
    @Override
    public boolean canPlaceBlockAt(final World p_149742_1_, final int p_149742_2_, final int p_149742_3_, final int p_149742_4_) {
        return false;
    }
    
    @Override
    public boolean canPlaceBlockOnSide(final World p_149707_1_, final int p_149707_2_, final int p_149707_3_, final int p_149707_4_, final int p_149707_5_) {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return -1;
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
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        if (!p_149727_1_.isClient && p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_) == null) {
            p_149727_1_.setBlockToAir(p_149727_2_, p_149727_3_, p_149727_4_);
            return true;
        }
        return false;
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return null;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World p_149690_1_, final int p_149690_2_, final int p_149690_3_, final int p_149690_4_, final int p_149690_5_, final float p_149690_6_, final int p_149690_7_) {
        if (!p_149690_1_.isClient) {
            final TileEntityPiston var8 = this.func_149963_e(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_);
            if (var8 != null) {
                var8.func_145861_a().dropBlockAsItem(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, var8.getBlockMetadata(), 0);
            }
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        if (!p_149695_1_.isClient) {
            p_149695_1_.getTileEntity(p_149695_2_, p_149695_3_, p_149695_4_);
        }
    }
    
    public static TileEntity func_149962_a(final Block p_149962_0_, final int p_149962_1_, final int p_149962_2_, final boolean p_149962_3_, final boolean p_149962_4_) {
        return new TileEntityPiston(p_149962_0_, p_149962_1_, p_149962_2_, p_149962_3_, p_149962_4_);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World p_149668_1_, final int p_149668_2_, final int p_149668_3_, final int p_149668_4_) {
        final TileEntityPiston var5 = this.func_149963_e(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
        if (var5 == null) {
            return null;
        }
        float var6 = var5.func_145860_a(0.0f);
        if (var5.func_145868_b()) {
            var6 = 1.0f - var6;
        }
        return this.func_149964_a(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_, var5.func_145861_a(), var6, var5.func_145864_c());
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        final TileEntityPiston var5 = this.func_149963_e(p_149719_1_, p_149719_2_, p_149719_3_, p_149719_4_);
        if (var5 != null) {
            final Block var6 = var5.func_145861_a();
            if (var6 == this || var6.getMaterial() == Material.air) {
                return;
            }
            var6.setBlockBoundsBasedOnState(p_149719_1_, p_149719_2_, p_149719_3_, p_149719_4_);
            float var7 = var5.func_145860_a(0.0f);
            if (var5.func_145868_b()) {
                var7 = 1.0f - var7;
            }
            final int var8 = var5.func_145864_c();
            this.field_149759_B = var6.getBlockBoundsMinX() - Facing.offsetsXForSide[var8] * var7;
            this.field_149760_C = var6.getBlockBoundsMinY() - Facing.offsetsYForSide[var8] * var7;
            this.field_149754_D = var6.getBlockBoundsMinZ() - Facing.offsetsZForSide[var8] * var7;
            this.field_149755_E = var6.getBlockBoundsMaxX() - Facing.offsetsXForSide[var8] * var7;
            this.field_149756_F = var6.getBlockBoundsMaxY() - Facing.offsetsYForSide[var8] * var7;
            this.field_149757_G = var6.getBlockBoundsMaxZ() - Facing.offsetsZForSide[var8] * var7;
        }
    }
    
    public AxisAlignedBB func_149964_a(final World p_149964_1_, final int p_149964_2_, final int p_149964_3_, final int p_149964_4_, final Block p_149964_5_, final float p_149964_6_, final int p_149964_7_) {
        if (p_149964_5_ == this || p_149964_5_.getMaterial() == Material.air) {
            return null;
        }
        final AxisAlignedBB var8 = p_149964_5_.getCollisionBoundingBoxFromPool(p_149964_1_, p_149964_2_, p_149964_3_, p_149964_4_);
        if (var8 == null) {
            return null;
        }
        if (Facing.offsetsXForSide[p_149964_7_] < 0) {
            final AxisAlignedBB axisAlignedBB = var8;
            axisAlignedBB.minX -= Facing.offsetsXForSide[p_149964_7_] * p_149964_6_;
        }
        else {
            final AxisAlignedBB axisAlignedBB2 = var8;
            axisAlignedBB2.maxX -= Facing.offsetsXForSide[p_149964_7_] * p_149964_6_;
        }
        if (Facing.offsetsYForSide[p_149964_7_] < 0) {
            final AxisAlignedBB axisAlignedBB3 = var8;
            axisAlignedBB3.minY -= Facing.offsetsYForSide[p_149964_7_] * p_149964_6_;
        }
        else {
            final AxisAlignedBB axisAlignedBB4 = var8;
            axisAlignedBB4.maxY -= Facing.offsetsYForSide[p_149964_7_] * p_149964_6_;
        }
        if (Facing.offsetsZForSide[p_149964_7_] < 0) {
            final AxisAlignedBB axisAlignedBB5 = var8;
            axisAlignedBB5.minZ -= Facing.offsetsZForSide[p_149964_7_] * p_149964_6_;
        }
        else {
            final AxisAlignedBB axisAlignedBB6 = var8;
            axisAlignedBB6.maxZ -= Facing.offsetsZForSide[p_149964_7_] * p_149964_6_;
        }
        return var8;
    }
    
    private TileEntityPiston func_149963_e(final IBlockAccess p_149963_1_, final int p_149963_2_, final int p_149963_3_, final int p_149963_4_) {
        final TileEntity var5 = p_149963_1_.getTileEntity(p_149963_2_, p_149963_3_, p_149963_4_);
        return (var5 instanceof TileEntityPiston) ? ((TileEntityPiston)var5) : null;
    }
    
    @Override
    public Item getItem(final World p_149694_1_, final int p_149694_2_, final int p_149694_3_, final int p_149694_4_) {
        return Item.getItemById(0);
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon("piston_top_normal");
    }
}
