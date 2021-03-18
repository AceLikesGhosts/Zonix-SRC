package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.texture.*;

public class BlockFenceGate extends BlockDirectional
{
    private static final String __OBFID = "CL_00000243";
    
    public BlockFenceGate() {
        super(Material.wood);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return Blocks.planks.getBlockTextureFromSide(p_149691_1_);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World p_149742_1_, final int p_149742_2_, final int p_149742_3_, final int p_149742_4_) {
        return p_149742_1_.getBlock(p_149742_2_, p_149742_3_ - 1, p_149742_4_).getMaterial().isSolid() && super.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World p_149668_1_, final int p_149668_2_, final int p_149668_3_, final int p_149668_4_) {
        final int var5 = p_149668_1_.getBlockMetadata(p_149668_2_, p_149668_3_, p_149668_4_);
        return isFenceGateOpen(var5) ? null : ((var5 != 2 && var5 != 0) ? AxisAlignedBB.getBoundingBox(p_149668_2_ + 0.375f, p_149668_3_, p_149668_4_, p_149668_2_ + 0.625f, p_149668_3_ + 1.5f, p_149668_4_ + 1) : AxisAlignedBB.getBoundingBox(p_149668_2_, p_149668_3_, p_149668_4_ + 0.375f, p_149668_2_ + 1, p_149668_3_ + 1.5f, p_149668_4_ + 0.625f));
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        final int var5 = BlockDirectional.func_149895_l(p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_));
        if (var5 != 2 && var5 != 0) {
            this.setBlockBounds(0.375f, 0.0f, 0.0f, 0.625f, 1.0f, 1.0f);
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.375f, 1.0f, 1.0f, 0.625f);
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
    public boolean getBlocksMovement(final IBlockAccess p_149655_1_, final int p_149655_2_, final int p_149655_3_, final int p_149655_4_) {
        return isFenceGateOpen(p_149655_1_.getBlockMetadata(p_149655_2_, p_149655_3_, p_149655_4_));
    }
    
    @Override
    public int getRenderType() {
        return 21;
    }
    
    @Override
    public void onBlockPlacedBy(final World p_149689_1_, final int p_149689_2_, final int p_149689_3_, final int p_149689_4_, final EntityLivingBase p_149689_5_, final ItemStack p_149689_6_) {
        final int var7 = (MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3) % 4;
        p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var7, 2);
    }
    
    @Override
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        int var10 = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);
        if (isFenceGateOpen(var10)) {
            p_149727_1_.setBlockMetadataWithNotify(p_149727_2_, p_149727_3_, p_149727_4_, var10 & 0xFFFFFFFB, 2);
        }
        else {
            final int var11 = (MathHelper.floor_double(p_149727_5_.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3) % 4;
            final int var12 = BlockDirectional.func_149895_l(var10);
            if (var12 == (var11 + 2) % 4) {
                var10 = var11;
            }
            p_149727_1_.setBlockMetadataWithNotify(p_149727_2_, p_149727_3_, p_149727_4_, var10 | 0x4, 2);
        }
        p_149727_1_.playAuxSFXAtEntity(p_149727_5_, 1003, p_149727_2_, p_149727_3_, p_149727_4_, 0);
        return true;
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        if (!p_149695_1_.isClient) {
            final int var6 = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_);
            final boolean var7 = p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_, p_149695_4_);
            if (var7 || p_149695_5_.canProvidePower()) {
                if (var7 && !isFenceGateOpen(var6)) {
                    p_149695_1_.setBlockMetadataWithNotify(p_149695_2_, p_149695_3_, p_149695_4_, var6 | 0x4, 2);
                    p_149695_1_.playAuxSFXAtEntity(null, 1003, p_149695_2_, p_149695_3_, p_149695_4_, 0);
                }
                else if (!var7 && isFenceGateOpen(var6)) {
                    p_149695_1_.setBlockMetadataWithNotify(p_149695_2_, p_149695_3_, p_149695_4_, var6 & 0xFFFFFFFB, 2);
                    p_149695_1_.playAuxSFXAtEntity(null, 1003, p_149695_2_, p_149695_3_, p_149695_4_, 0);
                }
            }
        }
    }
    
    public static boolean isFenceGateOpen(final int p_149896_0_) {
        return (p_149896_0_ & 0x4) != 0x0;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess p_149646_1_, final int p_149646_2_, final int p_149646_3_, final int p_149646_4_, final int p_149646_5_) {
        return true;
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
    }
}
