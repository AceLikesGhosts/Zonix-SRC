package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;

public class BlockLever extends Block
{
    private static final String __OBFID = "CL_00000264";
    
    protected BlockLever() {
        super(Material.circuits);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World p_149668_1_, final int p_149668_2_, final int p_149668_3_, final int p_149668_4_) {
        return null;
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
        return 12;
    }
    
    @Override
    public boolean canPlaceBlockOnSide(final World p_149707_1_, final int p_149707_2_, final int p_149707_3_, final int p_149707_4_, final int p_149707_5_) {
        return (p_149707_5_ == 0 && p_149707_1_.getBlock(p_149707_2_, p_149707_3_ + 1, p_149707_4_).isNormalCube()) || (p_149707_5_ == 1 && World.doesBlockHaveSolidTopSurface(p_149707_1_, p_149707_2_, p_149707_3_ - 1, p_149707_4_)) || (p_149707_5_ == 2 && p_149707_1_.getBlock(p_149707_2_, p_149707_3_, p_149707_4_ + 1).isNormalCube()) || (p_149707_5_ == 3 && p_149707_1_.getBlock(p_149707_2_, p_149707_3_, p_149707_4_ - 1).isNormalCube()) || (p_149707_5_ == 4 && p_149707_1_.getBlock(p_149707_2_ + 1, p_149707_3_, p_149707_4_).isNormalCube()) || (p_149707_5_ == 5 && p_149707_1_.getBlock(p_149707_2_ - 1, p_149707_3_, p_149707_4_).isNormalCube());
    }
    
    @Override
    public boolean canPlaceBlockAt(final World p_149742_1_, final int p_149742_2_, final int p_149742_3_, final int p_149742_4_) {
        return p_149742_1_.getBlock(p_149742_2_ - 1, p_149742_3_, p_149742_4_).isNormalCube() || p_149742_1_.getBlock(p_149742_2_ + 1, p_149742_3_, p_149742_4_).isNormalCube() || p_149742_1_.getBlock(p_149742_2_, p_149742_3_, p_149742_4_ - 1).isNormalCube() || p_149742_1_.getBlock(p_149742_2_, p_149742_3_, p_149742_4_ + 1).isNormalCube() || World.doesBlockHaveSolidTopSurface(p_149742_1_, p_149742_2_, p_149742_3_ - 1, p_149742_4_) || p_149742_1_.getBlock(p_149742_2_, p_149742_3_ + 1, p_149742_4_).isNormalCube();
    }
    
    @Override
    public int onBlockPlaced(final World p_149660_1_, final int p_149660_2_, final int p_149660_3_, final int p_149660_4_, final int p_149660_5_, final float p_149660_6_, final float p_149660_7_, final float p_149660_8_, final int p_149660_9_) {
        final int var11 = p_149660_9_ & 0x8;
        final int var12 = p_149660_9_ & 0x7;
        byte var13 = -1;
        if (p_149660_5_ == 0 && p_149660_1_.getBlock(p_149660_2_, p_149660_3_ + 1, p_149660_4_).isNormalCube()) {
            var13 = 0;
        }
        if (p_149660_5_ == 1 && World.doesBlockHaveSolidTopSurface(p_149660_1_, p_149660_2_, p_149660_3_ - 1, p_149660_4_)) {
            var13 = 5;
        }
        if (p_149660_5_ == 2 && p_149660_1_.getBlock(p_149660_2_, p_149660_3_, p_149660_4_ + 1).isNormalCube()) {
            var13 = 4;
        }
        if (p_149660_5_ == 3 && p_149660_1_.getBlock(p_149660_2_, p_149660_3_, p_149660_4_ - 1).isNormalCube()) {
            var13 = 3;
        }
        if (p_149660_5_ == 4 && p_149660_1_.getBlock(p_149660_2_ + 1, p_149660_3_, p_149660_4_).isNormalCube()) {
            var13 = 2;
        }
        if (p_149660_5_ == 5 && p_149660_1_.getBlock(p_149660_2_ - 1, p_149660_3_, p_149660_4_).isNormalCube()) {
            var13 = 1;
        }
        return var13 + var11;
    }
    
    @Override
    public void onBlockPlacedBy(final World p_149689_1_, final int p_149689_2_, final int p_149689_3_, final int p_149689_4_, final EntityLivingBase p_149689_5_, final ItemStack p_149689_6_) {
        final int var7 = p_149689_1_.getBlockMetadata(p_149689_2_, p_149689_3_, p_149689_4_);
        final int var8 = var7 & 0x7;
        final int var9 = var7 & 0x8;
        if (var8 == func_149819_b(1)) {
            if ((MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0f / 360.0f + 0.5) & 0x1) == 0x0) {
                p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 0x5 | var9, 2);
            }
            else {
                p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 0x6 | var9, 2);
            }
        }
        else if (var8 == func_149819_b(0)) {
            if ((MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0f / 360.0f + 0.5) & 0x1) == 0x0) {
                p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 0x7 | var9, 2);
            }
            else {
                p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 0x0 | var9, 2);
            }
        }
    }
    
    public static int func_149819_b(final int p_149819_0_) {
        switch (p_149819_0_) {
            case 0: {
                return 0;
            }
            case 1: {
                return 5;
            }
            case 2: {
                return 4;
            }
            case 3: {
                return 3;
            }
            case 4: {
                return 2;
            }
            case 5: {
                return 1;
            }
            default: {
                return -1;
            }
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        if (this.func_149820_e(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_)) {
            final int var6 = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_) & 0x7;
            boolean var7 = false;
            if (!p_149695_1_.getBlock(p_149695_2_ - 1, p_149695_3_, p_149695_4_).isNormalCube() && var6 == 1) {
                var7 = true;
            }
            if (!p_149695_1_.getBlock(p_149695_2_ + 1, p_149695_3_, p_149695_4_).isNormalCube() && var6 == 2) {
                var7 = true;
            }
            if (!p_149695_1_.getBlock(p_149695_2_, p_149695_3_, p_149695_4_ - 1).isNormalCube() && var6 == 3) {
                var7 = true;
            }
            if (!p_149695_1_.getBlock(p_149695_2_, p_149695_3_, p_149695_4_ + 1).isNormalCube() && var6 == 4) {
                var7 = true;
            }
            if (!World.doesBlockHaveSolidTopSurface(p_149695_1_, p_149695_2_, p_149695_3_ - 1, p_149695_4_) && var6 == 5) {
                var7 = true;
            }
            if (!World.doesBlockHaveSolidTopSurface(p_149695_1_, p_149695_2_, p_149695_3_ - 1, p_149695_4_) && var6 == 6) {
                var7 = true;
            }
            if (!p_149695_1_.getBlock(p_149695_2_, p_149695_3_ + 1, p_149695_4_).isNormalCube() && var6 == 0) {
                var7 = true;
            }
            if (!p_149695_1_.getBlock(p_149695_2_, p_149695_3_ + 1, p_149695_4_).isNormalCube() && var6 == 7) {
                var7 = true;
            }
            if (var7) {
                this.dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_), 0);
                p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
            }
        }
    }
    
    private boolean func_149820_e(final World p_149820_1_, final int p_149820_2_, final int p_149820_3_, final int p_149820_4_) {
        if (!this.canPlaceBlockAt(p_149820_1_, p_149820_2_, p_149820_3_, p_149820_4_)) {
            this.dropBlockAsItem(p_149820_1_, p_149820_2_, p_149820_3_, p_149820_4_, p_149820_1_.getBlockMetadata(p_149820_2_, p_149820_3_, p_149820_4_), 0);
            p_149820_1_.setBlockToAir(p_149820_2_, p_149820_3_, p_149820_4_);
            return false;
        }
        return true;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        final int var5 = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_) & 0x7;
        float var6 = 0.1875f;
        if (var5 == 1) {
            this.setBlockBounds(0.0f, 0.2f, 0.5f - var6, var6 * 2.0f, 0.8f, 0.5f + var6);
        }
        else if (var5 == 2) {
            this.setBlockBounds(1.0f - var6 * 2.0f, 0.2f, 0.5f - var6, 1.0f, 0.8f, 0.5f + var6);
        }
        else if (var5 == 3) {
            this.setBlockBounds(0.5f - var6, 0.2f, 0.0f, 0.5f + var6, 0.8f, var6 * 2.0f);
        }
        else if (var5 == 4) {
            this.setBlockBounds(0.5f - var6, 0.2f, 1.0f - var6 * 2.0f, 0.5f + var6, 0.8f, 1.0f);
        }
        else if (var5 != 5 && var5 != 6) {
            if (var5 == 0 || var5 == 7) {
                var6 = 0.25f;
                this.setBlockBounds(0.5f - var6, 0.4f, 0.5f - var6, 0.5f + var6, 1.0f, 0.5f + var6);
            }
        }
        else {
            var6 = 0.25f;
            this.setBlockBounds(0.5f - var6, 0.0f, 0.5f - var6, 0.5f + var6, 0.6f, 0.5f + var6);
        }
    }
    
    @Override
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        if (p_149727_1_.isClient) {
            return true;
        }
        final int var10 = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);
        final int var11 = var10 & 0x7;
        final int var12 = 8 - (var10 & 0x8);
        p_149727_1_.setBlockMetadataWithNotify(p_149727_2_, p_149727_3_, p_149727_4_, var11 + var12, 3);
        p_149727_1_.playSoundEffect(p_149727_2_ + 0.5, p_149727_3_ + 0.5, p_149727_4_ + 0.5, "random.click", 0.3f, (var12 > 0) ? 0.6f : 0.5f);
        p_149727_1_.notifyBlocksOfNeighborChange(p_149727_2_, p_149727_3_, p_149727_4_, this);
        if (var11 == 1) {
            p_149727_1_.notifyBlocksOfNeighborChange(p_149727_2_ - 1, p_149727_3_, p_149727_4_, this);
        }
        else if (var11 == 2) {
            p_149727_1_.notifyBlocksOfNeighborChange(p_149727_2_ + 1, p_149727_3_, p_149727_4_, this);
        }
        else if (var11 == 3) {
            p_149727_1_.notifyBlocksOfNeighborChange(p_149727_2_, p_149727_3_, p_149727_4_ - 1, this);
        }
        else if (var11 == 4) {
            p_149727_1_.notifyBlocksOfNeighborChange(p_149727_2_, p_149727_3_, p_149727_4_ + 1, this);
        }
        else if (var11 != 5 && var11 != 6) {
            if (var11 == 0 || var11 == 7) {
                p_149727_1_.notifyBlocksOfNeighborChange(p_149727_2_, p_149727_3_ + 1, p_149727_4_, this);
            }
        }
        else {
            p_149727_1_.notifyBlocksOfNeighborChange(p_149727_2_, p_149727_3_ - 1, p_149727_4_, this);
        }
        return true;
    }
    
    @Override
    public void breakBlock(final World p_149749_1_, final int p_149749_2_, final int p_149749_3_, final int p_149749_4_, final Block p_149749_5_, final int p_149749_6_) {
        if ((p_149749_6_ & 0x8) > 0) {
            p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_, p_149749_4_, this);
            final int var7 = p_149749_6_ & 0x7;
            if (var7 == 1) {
                p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_ - 1, p_149749_3_, p_149749_4_, this);
            }
            else if (var7 == 2) {
                p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_ + 1, p_149749_3_, p_149749_4_, this);
            }
            else if (var7 == 3) {
                p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_, p_149749_4_ - 1, this);
            }
            else if (var7 == 4) {
                p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_, p_149749_4_ + 1, this);
            }
            else if (var7 != 5 && var7 != 6) {
                if (var7 == 0 || var7 == 7) {
                    p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_ + 1, p_149749_4_, this);
                }
            }
            else {
                p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_ - 1, p_149749_4_, this);
            }
        }
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess p_149709_1_, final int p_149709_2_, final int p_149709_3_, final int p_149709_4_, final int p_149709_5_) {
        return ((p_149709_1_.getBlockMetadata(p_149709_2_, p_149709_3_, p_149709_4_) & 0x8) > 0) ? 15 : 0;
    }
    
    @Override
    public int isProvidingStrongPower(final IBlockAccess p_149748_1_, final int p_149748_2_, final int p_149748_3_, final int p_149748_4_, final int p_149748_5_) {
        final int var6 = p_149748_1_.getBlockMetadata(p_149748_2_, p_149748_3_, p_149748_4_);
        if ((var6 & 0x8) == 0x0) {
            return 0;
        }
        final int var7 = var6 & 0x7;
        return (var7 == 0 && p_149748_5_ == 0) ? 15 : ((var7 == 7 && p_149748_5_ == 0) ? 15 : ((var7 == 6 && p_149748_5_ == 1) ? 15 : ((var7 == 5 && p_149748_5_ == 1) ? 15 : ((var7 == 4 && p_149748_5_ == 2) ? 15 : ((var7 == 3 && p_149748_5_ == 3) ? 15 : ((var7 == 2 && p_149748_5_ == 4) ? 15 : ((var7 == 1 && p_149748_5_ == 5) ? 15 : 0)))))));
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
}
