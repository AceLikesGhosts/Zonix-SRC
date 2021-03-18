package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.texture.*;

public abstract class BlockBasePressurePlate extends Block
{
    private String field_150067_a;
    private static final String __OBFID = "CL_00000194";
    
    protected BlockBasePressurePlate(final String p_i45387_1_, final Material p_i45387_2_) {
        super(p_i45387_2_);
        this.field_150067_a = p_i45387_1_;
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setTickRandomly(true);
        this.func_150063_b(this.func_150066_d(15));
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        this.func_150063_b(p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_));
    }
    
    protected void func_150063_b(final int p_150063_1_) {
        final boolean var2 = this.func_150060_c(p_150063_1_) > 0;
        final float var3 = 0.0625f;
        if (var2) {
            this.setBlockBounds(var3, 0.0f, var3, 1.0f - var3, 0.03125f, 1.0f - var3);
        }
        else {
            this.setBlockBounds(var3, 0.0f, var3, 1.0f - var3, 0.0625f, 1.0f - var3);
        }
    }
    
    @Override
    public int func_149738_a(final World p_149738_1_) {
        return 20;
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
    public boolean getBlocksMovement(final IBlockAccess p_149655_1_, final int p_149655_2_, final int p_149655_3_, final int p_149655_4_) {
        return true;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World p_149742_1_, final int p_149742_2_, final int p_149742_3_, final int p_149742_4_) {
        return World.doesBlockHaveSolidTopSurface(p_149742_1_, p_149742_2_, p_149742_3_ - 1, p_149742_4_) || BlockFence.func_149825_a(p_149742_1_.getBlock(p_149742_2_, p_149742_3_ - 1, p_149742_4_));
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        boolean var6 = false;
        if (!World.doesBlockHaveSolidTopSurface(p_149695_1_, p_149695_2_, p_149695_3_ - 1, p_149695_4_) && !BlockFence.func_149825_a(p_149695_1_.getBlock(p_149695_2_, p_149695_3_ - 1, p_149695_4_))) {
            var6 = true;
        }
        if (var6) {
            this.dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_), 0);
            p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
        }
    }
    
    @Override
    public void updateTick(final World p_149674_1_, final int p_149674_2_, final int p_149674_3_, final int p_149674_4_, final Random p_149674_5_) {
        if (!p_149674_1_.isClient) {
            final int var6 = this.func_150060_c(p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_));
            if (var6 > 0) {
                this.func_150062_a(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, var6);
            }
        }
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World p_149670_1_, final int p_149670_2_, final int p_149670_3_, final int p_149670_4_, final Entity p_149670_5_) {
        if (!p_149670_1_.isClient) {
            final int var6 = this.func_150060_c(p_149670_1_.getBlockMetadata(p_149670_2_, p_149670_3_, p_149670_4_));
            if (var6 == 0) {
                this.func_150062_a(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_, var6);
            }
        }
    }
    
    protected void func_150062_a(final World p_150062_1_, final int p_150062_2_, final int p_150062_3_, final int p_150062_4_, final int p_150062_5_) {
        final int var6 = this.func_150065_e(p_150062_1_, p_150062_2_, p_150062_3_, p_150062_4_);
        final boolean var7 = p_150062_5_ > 0;
        final boolean var8 = var6 > 0;
        if (p_150062_5_ != var6) {
            p_150062_1_.setBlockMetadataWithNotify(p_150062_2_, p_150062_3_, p_150062_4_, this.func_150066_d(var6), 2);
            this.func_150064_a_(p_150062_1_, p_150062_2_, p_150062_3_, p_150062_4_);
            p_150062_1_.markBlockRangeForRenderUpdate(p_150062_2_, p_150062_3_, p_150062_4_, p_150062_2_, p_150062_3_, p_150062_4_);
        }
        if (!var8 && var7) {
            p_150062_1_.playSoundEffect(p_150062_2_ + 0.5, p_150062_3_ + 0.1, p_150062_4_ + 0.5, "random.click", 0.3f, 0.5f);
        }
        else if (var8 && !var7) {
            p_150062_1_.playSoundEffect(p_150062_2_ + 0.5, p_150062_3_ + 0.1, p_150062_4_ + 0.5, "random.click", 0.3f, 0.6f);
        }
        if (var8) {
            p_150062_1_.scheduleBlockUpdate(p_150062_2_, p_150062_3_, p_150062_4_, this, this.func_149738_a(p_150062_1_));
        }
    }
    
    protected AxisAlignedBB func_150061_a(final int p_150061_1_, final int p_150061_2_, final int p_150061_3_) {
        final float var4 = 0.125f;
        return AxisAlignedBB.getBoundingBox(p_150061_1_ + var4, p_150061_2_, p_150061_3_ + var4, p_150061_1_ + 1 - var4, p_150061_2_ + 0.25, p_150061_3_ + 1 - var4);
    }
    
    @Override
    public void breakBlock(final World p_149749_1_, final int p_149749_2_, final int p_149749_3_, final int p_149749_4_, final Block p_149749_5_, final int p_149749_6_) {
        if (this.func_150060_c(p_149749_6_) > 0) {
            this.func_150064_a_(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_);
        }
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }
    
    protected void func_150064_a_(final World p_150064_1_, final int p_150064_2_, final int p_150064_3_, final int p_150064_4_) {
        p_150064_1_.notifyBlocksOfNeighborChange(p_150064_2_, p_150064_3_, p_150064_4_, this);
        p_150064_1_.notifyBlocksOfNeighborChange(p_150064_2_, p_150064_3_ - 1, p_150064_4_, this);
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess p_149709_1_, final int p_149709_2_, final int p_149709_3_, final int p_149709_4_, final int p_149709_5_) {
        return this.func_150060_c(p_149709_1_.getBlockMetadata(p_149709_2_, p_149709_3_, p_149709_4_));
    }
    
    @Override
    public int isProvidingStrongPower(final IBlockAccess p_149748_1_, final int p_149748_2_, final int p_149748_3_, final int p_149748_4_, final int p_149748_5_) {
        return (p_149748_5_ == 1) ? this.func_150060_c(p_149748_1_.getBlockMetadata(p_149748_2_, p_149748_3_, p_149748_4_)) : 0;
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        final float var1 = 0.5f;
        final float var2 = 0.125f;
        final float var3 = 0.5f;
        this.setBlockBounds(0.5f - var1, 0.5f - var2, 0.5f - var3, 0.5f + var1, 0.5f + var2, 0.5f + var3);
    }
    
    @Override
    public int getMobilityFlag() {
        return 1;
    }
    
    protected abstract int func_150065_e(final World p0, final int p1, final int p2, final int p3);
    
    protected abstract int func_150060_c(final int p0);
    
    protected abstract int func_150066_d(final int p0);
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon(this.field_150067_a);
    }
}
