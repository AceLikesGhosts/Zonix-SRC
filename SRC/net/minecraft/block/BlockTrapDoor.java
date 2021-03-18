package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.init.*;

public class BlockTrapDoor extends Block
{
    private static final String __OBFID = "CL_00000327";
    
    protected BlockTrapDoor(final Material p_i45434_1_) {
        super(p_i45434_1_);
        final float var2 = 0.5f;
        final float var3 = 1.0f;
        this.setBlockBounds(0.5f - var2, 0.0f, 0.5f - var2, 0.5f + var2, var3, 0.5f + var2);
        this.setCreativeTab(CreativeTabs.tabRedstone);
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
        return !func_150118_d(p_149655_1_.getBlockMetadata(p_149655_2_, p_149655_3_, p_149655_4_));
    }
    
    @Override
    public int getRenderType() {
        return 0;
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(final World p_149633_1_, final int p_149633_2_, final int p_149633_3_, final int p_149633_4_) {
        this.setBlockBoundsBasedOnState(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
        return super.getSelectedBoundingBoxFromPool(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World p_149668_1_, final int p_149668_2_, final int p_149668_3_, final int p_149668_4_) {
        this.setBlockBoundsBasedOnState(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
        return super.getCollisionBoundingBoxFromPool(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        this.func_150117_b(p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_));
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        final float var1 = 0.1875f;
        this.setBlockBounds(0.0f, 0.5f - var1 / 2.0f, 0.0f, 1.0f, 0.5f + var1 / 2.0f, 1.0f);
    }
    
    public void func_150117_b(final int p_150117_1_) {
        final float var2 = 0.1875f;
        if ((p_150117_1_ & 0x8) != 0x0) {
            this.setBlockBounds(0.0f, 1.0f - var2, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, var2, 1.0f);
        }
        if (func_150118_d(p_150117_1_)) {
            if ((p_150117_1_ & 0x3) == 0x0) {
                this.setBlockBounds(0.0f, 0.0f, 1.0f - var2, 1.0f, 1.0f, 1.0f);
            }
            if ((p_150117_1_ & 0x3) == 0x1) {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, var2);
            }
            if ((p_150117_1_ & 0x3) == 0x2) {
                this.setBlockBounds(1.0f - var2, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            }
            if ((p_150117_1_ & 0x3) == 0x3) {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, var2, 1.0f, 1.0f);
            }
        }
    }
    
    @Override
    public void onBlockClicked(final World p_149699_1_, final int p_149699_2_, final int p_149699_3_, final int p_149699_4_, final EntityPlayer p_149699_5_) {
    }
    
    @Override
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        if (this.blockMaterial == Material.iron) {
            return true;
        }
        final int var10 = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);
        p_149727_1_.setBlockMetadataWithNotify(p_149727_2_, p_149727_3_, p_149727_4_, var10 ^ 0x4, 2);
        p_149727_1_.playAuxSFXAtEntity(p_149727_5_, 1003, p_149727_2_, p_149727_3_, p_149727_4_, 0);
        return true;
    }
    
    public void func_150120_a(final World p_150120_1_, final int p_150120_2_, final int p_150120_3_, final int p_150120_4_, final boolean p_150120_5_) {
        final int var6 = p_150120_1_.getBlockMetadata(p_150120_2_, p_150120_3_, p_150120_4_);
        final boolean var7 = (var6 & 0x4) > 0;
        if (var7 != p_150120_5_) {
            p_150120_1_.setBlockMetadataWithNotify(p_150120_2_, p_150120_3_, p_150120_4_, var6 ^ 0x4, 2);
            p_150120_1_.playAuxSFXAtEntity(null, 1003, p_150120_2_, p_150120_3_, p_150120_4_, 0);
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        if (!p_149695_1_.isClient) {
            final int var6 = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_);
            int var7 = p_149695_2_;
            int var8 = p_149695_4_;
            if ((var6 & 0x3) == 0x0) {
                var8 = p_149695_4_ + 1;
            }
            if ((var6 & 0x3) == 0x1) {
                --var8;
            }
            if ((var6 & 0x3) == 0x2) {
                var7 = p_149695_2_ + 1;
            }
            if ((var6 & 0x3) == 0x3) {
                --var7;
            }
            if (!func_150119_a(p_149695_1_.getBlock(var7, p_149695_3_, var8))) {
                p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
                this.dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, var6, 0);
            }
            final boolean var9 = p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_, p_149695_4_);
            if (var9 || p_149695_5_.canProvidePower()) {
                this.func_150120_a(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, var9);
            }
        }
    }
    
    @Override
    public MovingObjectPosition collisionRayTrace(final World p_149731_1_, final int p_149731_2_, final int p_149731_3_, final int p_149731_4_, final Vec3 p_149731_5_, final Vec3 p_149731_6_) {
        this.setBlockBoundsBasedOnState(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_);
        return super.collisionRayTrace(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_, p_149731_5_, p_149731_6_);
    }
    
    @Override
    public int onBlockPlaced(final World p_149660_1_, final int p_149660_2_, final int p_149660_3_, final int p_149660_4_, final int p_149660_5_, final float p_149660_6_, final float p_149660_7_, final float p_149660_8_, final int p_149660_9_) {
        int var10 = 0;
        if (p_149660_5_ == 2) {
            var10 = 0;
        }
        if (p_149660_5_ == 3) {
            var10 = 1;
        }
        if (p_149660_5_ == 4) {
            var10 = 2;
        }
        if (p_149660_5_ == 5) {
            var10 = 3;
        }
        if (p_149660_5_ != 1 && p_149660_5_ != 0 && p_149660_7_ > 0.5f) {
            var10 |= 0x8;
        }
        return var10;
    }
    
    @Override
    public boolean canPlaceBlockOnSide(final World p_149707_1_, int p_149707_2_, final int p_149707_3_, int p_149707_4_, final int p_149707_5_) {
        if (p_149707_5_ == 0) {
            return false;
        }
        if (p_149707_5_ == 1) {
            return false;
        }
        if (p_149707_5_ == 2) {
            ++p_149707_4_;
        }
        if (p_149707_5_ == 3) {
            --p_149707_4_;
        }
        if (p_149707_5_ == 4) {
            ++p_149707_2_;
        }
        if (p_149707_5_ == 5) {
            --p_149707_2_;
        }
        return func_150119_a(p_149707_1_.getBlock(p_149707_2_, p_149707_3_, p_149707_4_));
    }
    
    public static boolean func_150118_d(final int p_150118_0_) {
        return (p_150118_0_ & 0x4) != 0x0;
    }
    
    private static boolean func_150119_a(final Block p_150119_0_) {
        return (p_150119_0_.blockMaterial.isOpaque() && p_150119_0_.renderAsNormalBlock()) || p_150119_0_ == Blocks.glowstone || p_150119_0_ instanceof BlockSlab || p_150119_0_ instanceof BlockStairs;
    }
}
