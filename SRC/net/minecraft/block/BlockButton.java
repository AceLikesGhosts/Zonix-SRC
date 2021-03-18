package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;

public abstract class BlockButton extends Block
{
    private final boolean field_150047_a;
    private static final String __OBFID = "CL_00000209";
    
    protected BlockButton(final boolean p_i45396_1_) {
        super(Material.circuits);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.field_150047_a = p_i45396_1_;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World p_149668_1_, final int p_149668_2_, final int p_149668_3_, final int p_149668_4_) {
        return null;
    }
    
    @Override
    public int func_149738_a(final World p_149738_1_) {
        return this.field_150047_a ? 30 : 20;
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
    public boolean canPlaceBlockOnSide(final World p_149707_1_, final int p_149707_2_, final int p_149707_3_, final int p_149707_4_, final int p_149707_5_) {
        return (p_149707_5_ == 2 && p_149707_1_.getBlock(p_149707_2_, p_149707_3_, p_149707_4_ + 1).isNormalCube()) || (p_149707_5_ == 3 && p_149707_1_.getBlock(p_149707_2_, p_149707_3_, p_149707_4_ - 1).isNormalCube()) || (p_149707_5_ == 4 && p_149707_1_.getBlock(p_149707_2_ + 1, p_149707_3_, p_149707_4_).isNormalCube()) || (p_149707_5_ == 5 && p_149707_1_.getBlock(p_149707_2_ - 1, p_149707_3_, p_149707_4_).isNormalCube());
    }
    
    @Override
    public boolean canPlaceBlockAt(final World p_149742_1_, final int p_149742_2_, final int p_149742_3_, final int p_149742_4_) {
        return p_149742_1_.getBlock(p_149742_2_ - 1, p_149742_3_, p_149742_4_).isNormalCube() || p_149742_1_.getBlock(p_149742_2_ + 1, p_149742_3_, p_149742_4_).isNormalCube() || p_149742_1_.getBlock(p_149742_2_, p_149742_3_, p_149742_4_ - 1).isNormalCube() || p_149742_1_.getBlock(p_149742_2_, p_149742_3_, p_149742_4_ + 1).isNormalCube();
    }
    
    @Override
    public int onBlockPlaced(final World p_149660_1_, final int p_149660_2_, final int p_149660_3_, final int p_149660_4_, final int p_149660_5_, final float p_149660_6_, final float p_149660_7_, final float p_149660_8_, final int p_149660_9_) {
        int var10 = p_149660_1_.getBlockMetadata(p_149660_2_, p_149660_3_, p_149660_4_);
        final int var11 = var10 & 0x8;
        var10 &= 0x7;
        if (p_149660_5_ == 2 && p_149660_1_.getBlock(p_149660_2_, p_149660_3_, p_149660_4_ + 1).isNormalCube()) {
            var10 = 4;
        }
        else if (p_149660_5_ == 3 && p_149660_1_.getBlock(p_149660_2_, p_149660_3_, p_149660_4_ - 1).isNormalCube()) {
            var10 = 3;
        }
        else if (p_149660_5_ == 4 && p_149660_1_.getBlock(p_149660_2_ + 1, p_149660_3_, p_149660_4_).isNormalCube()) {
            var10 = 2;
        }
        else if (p_149660_5_ == 5 && p_149660_1_.getBlock(p_149660_2_ - 1, p_149660_3_, p_149660_4_).isNormalCube()) {
            var10 = 1;
        }
        else {
            var10 = this.func_150045_e(p_149660_1_, p_149660_2_, p_149660_3_, p_149660_4_);
        }
        return var10 + var11;
    }
    
    private int func_150045_e(final World p_150045_1_, final int p_150045_2_, final int p_150045_3_, final int p_150045_4_) {
        return p_150045_1_.getBlock(p_150045_2_ - 1, p_150045_3_, p_150045_4_).isNormalCube() ? 1 : (p_150045_1_.getBlock(p_150045_2_ + 1, p_150045_3_, p_150045_4_).isNormalCube() ? 2 : (p_150045_1_.getBlock(p_150045_2_, p_150045_3_, p_150045_4_ - 1).isNormalCube() ? 3 : (p_150045_1_.getBlock(p_150045_2_, p_150045_3_, p_150045_4_ + 1).isNormalCube() ? 4 : 1)));
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        if (this.func_150044_m(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_)) {
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
            if (var7) {
                this.dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_), 0);
                p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
            }
        }
    }
    
    private boolean func_150044_m(final World p_150044_1_, final int p_150044_2_, final int p_150044_3_, final int p_150044_4_) {
        if (!this.canPlaceBlockAt(p_150044_1_, p_150044_2_, p_150044_3_, p_150044_4_)) {
            this.dropBlockAsItem(p_150044_1_, p_150044_2_, p_150044_3_, p_150044_4_, p_150044_1_.getBlockMetadata(p_150044_2_, p_150044_3_, p_150044_4_), 0);
            p_150044_1_.setBlockToAir(p_150044_2_, p_150044_3_, p_150044_4_);
            return false;
        }
        return true;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        final int var5 = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
        this.func_150043_b(var5);
    }
    
    private void func_150043_b(final int p_150043_1_) {
        final int var2 = p_150043_1_ & 0x7;
        final boolean var3 = (p_150043_1_ & 0x8) > 0;
        final float var4 = 0.375f;
        final float var5 = 0.625f;
        final float var6 = 0.1875f;
        float var7 = 0.125f;
        if (var3) {
            var7 = 0.0625f;
        }
        if (var2 == 1) {
            this.setBlockBounds(0.0f, var4, 0.5f - var6, var7, var5, 0.5f + var6);
        }
        else if (var2 == 2) {
            this.setBlockBounds(1.0f - var7, var4, 0.5f - var6, 1.0f, var5, 0.5f + var6);
        }
        else if (var2 == 3) {
            this.setBlockBounds(0.5f - var6, var4, 0.0f, 0.5f + var6, var5, var7);
        }
        else if (var2 == 4) {
            this.setBlockBounds(0.5f - var6, var4, 1.0f - var7, 0.5f + var6, var5, 1.0f);
        }
    }
    
    @Override
    public void onBlockClicked(final World p_149699_1_, final int p_149699_2_, final int p_149699_3_, final int p_149699_4_, final EntityPlayer p_149699_5_) {
    }
    
    @Override
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        final int var10 = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);
        final int var11 = var10 & 0x7;
        final int var12 = 8 - (var10 & 0x8);
        if (var12 == 0) {
            return true;
        }
        p_149727_1_.setBlockMetadataWithNotify(p_149727_2_, p_149727_3_, p_149727_4_, var11 + var12, 3);
        p_149727_1_.markBlockRangeForRenderUpdate(p_149727_2_, p_149727_3_, p_149727_4_, p_149727_2_, p_149727_3_, p_149727_4_);
        p_149727_1_.playSoundEffect(p_149727_2_ + 0.5, p_149727_3_ + 0.5, p_149727_4_ + 0.5, "random.click", 0.3f, 0.6f);
        this.func_150042_a(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, var11);
        p_149727_1_.scheduleBlockUpdate(p_149727_2_, p_149727_3_, p_149727_4_, this, this.func_149738_a(p_149727_1_));
        return true;
    }
    
    @Override
    public void breakBlock(final World p_149749_1_, final int p_149749_2_, final int p_149749_3_, final int p_149749_4_, final Block p_149749_5_, final int p_149749_6_) {
        if ((p_149749_6_ & 0x8) > 0) {
            final int var7 = p_149749_6_ & 0x7;
            this.func_150042_a(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, var7);
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
        return (var7 == 5 && p_149748_5_ == 1) ? 15 : ((var7 == 4 && p_149748_5_ == 2) ? 15 : ((var7 == 3 && p_149748_5_ == 3) ? 15 : ((var7 == 2 && p_149748_5_ == 4) ? 15 : ((var7 == 1 && p_149748_5_ == 5) ? 15 : 0))));
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    @Override
    public void updateTick(final World p_149674_1_, final int p_149674_2_, final int p_149674_3_, final int p_149674_4_, final Random p_149674_5_) {
        if (!p_149674_1_.isClient) {
            final int var6 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
            if ((var6 & 0x8) != 0x0) {
                if (this.field_150047_a) {
                    this.func_150046_n(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);
                }
                else {
                    p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, var6 & 0x7, 3);
                    final int var7 = var6 & 0x7;
                    this.func_150042_a(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, var7);
                    p_149674_1_.playSoundEffect(p_149674_2_ + 0.5, p_149674_3_ + 0.5, p_149674_4_ + 0.5, "random.click", 0.3f, 0.5f);
                    p_149674_1_.markBlockRangeForRenderUpdate(p_149674_2_, p_149674_3_, p_149674_4_, p_149674_2_, p_149674_3_, p_149674_4_);
                }
            }
        }
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        final float var1 = 0.1875f;
        final float var2 = 0.125f;
        final float var3 = 0.125f;
        this.setBlockBounds(0.5f - var1, 0.5f - var2, 0.5f - var3, 0.5f + var1, 0.5f + var2, 0.5f + var3);
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World p_149670_1_, final int p_149670_2_, final int p_149670_3_, final int p_149670_4_, final Entity p_149670_5_) {
        if (!p_149670_1_.isClient && this.field_150047_a && (p_149670_1_.getBlockMetadata(p_149670_2_, p_149670_3_, p_149670_4_) & 0x8) == 0x0) {
            this.func_150046_n(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_);
        }
    }
    
    private void func_150046_n(final World p_150046_1_, final int p_150046_2_, final int p_150046_3_, final int p_150046_4_) {
        final int var5 = p_150046_1_.getBlockMetadata(p_150046_2_, p_150046_3_, p_150046_4_);
        final int var6 = var5 & 0x7;
        final boolean var7 = (var5 & 0x8) != 0x0;
        this.func_150043_b(var5);
        final List var8 = p_150046_1_.getEntitiesWithinAABB(EntityArrow.class, AxisAlignedBB.getBoundingBox(p_150046_2_ + this.field_149759_B, p_150046_3_ + this.field_149760_C, p_150046_4_ + this.field_149754_D, p_150046_2_ + this.field_149755_E, p_150046_3_ + this.field_149756_F, p_150046_4_ + this.field_149757_G));
        final boolean var9 = !var8.isEmpty();
        if (var9 && !var7) {
            p_150046_1_.setBlockMetadataWithNotify(p_150046_2_, p_150046_3_, p_150046_4_, var6 | 0x8, 3);
            this.func_150042_a(p_150046_1_, p_150046_2_, p_150046_3_, p_150046_4_, var6);
            p_150046_1_.markBlockRangeForRenderUpdate(p_150046_2_, p_150046_3_, p_150046_4_, p_150046_2_, p_150046_3_, p_150046_4_);
            p_150046_1_.playSoundEffect(p_150046_2_ + 0.5, p_150046_3_ + 0.5, p_150046_4_ + 0.5, "random.click", 0.3f, 0.6f);
        }
        if (!var9 && var7) {
            p_150046_1_.setBlockMetadataWithNotify(p_150046_2_, p_150046_3_, p_150046_4_, var6, 3);
            this.func_150042_a(p_150046_1_, p_150046_2_, p_150046_3_, p_150046_4_, var6);
            p_150046_1_.markBlockRangeForRenderUpdate(p_150046_2_, p_150046_3_, p_150046_4_, p_150046_2_, p_150046_3_, p_150046_4_);
            p_150046_1_.playSoundEffect(p_150046_2_ + 0.5, p_150046_3_ + 0.5, p_150046_4_ + 0.5, "random.click", 0.3f, 0.5f);
        }
        if (var9) {
            p_150046_1_.scheduleBlockUpdate(p_150046_2_, p_150046_3_, p_150046_4_, this, this.func_149738_a(p_150046_1_));
        }
    }
    
    private void func_150042_a(final World p_150042_1_, final int p_150042_2_, final int p_150042_3_, final int p_150042_4_, final int p_150042_5_) {
        p_150042_1_.notifyBlocksOfNeighborChange(p_150042_2_, p_150042_3_, p_150042_4_, this);
        if (p_150042_5_ == 1) {
            p_150042_1_.notifyBlocksOfNeighborChange(p_150042_2_ - 1, p_150042_3_, p_150042_4_, this);
        }
        else if (p_150042_5_ == 2) {
            p_150042_1_.notifyBlocksOfNeighborChange(p_150042_2_ + 1, p_150042_3_, p_150042_4_, this);
        }
        else if (p_150042_5_ == 3) {
            p_150042_1_.notifyBlocksOfNeighborChange(p_150042_2_, p_150042_3_, p_150042_4_ - 1, this);
        }
        else if (p_150042_5_ == 4) {
            p_150042_1_.notifyBlocksOfNeighborChange(p_150042_2_, p_150042_3_, p_150042_4_ + 1, this);
        }
        else {
            p_150042_1_.notifyBlocksOfNeighborChange(p_150042_2_, p_150042_3_ - 1, p_150042_4_, this);
        }
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
    }
}
