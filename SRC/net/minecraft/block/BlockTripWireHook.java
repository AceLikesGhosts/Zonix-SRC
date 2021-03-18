package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import java.util.*;

public class BlockTripWireHook extends Block
{
    private static final String __OBFID = "CL_00000329";
    
    public BlockTripWireHook() {
        super(Material.circuits);
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setTickRandomly(true);
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
        return 29;
    }
    
    @Override
    public int func_149738_a(final World p_149738_1_) {
        return 10;
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
        byte var10 = 0;
        if (p_149660_5_ == 2 && p_149660_1_.isBlockNormalCubeDefault(p_149660_2_, p_149660_3_, p_149660_4_ + 1, true)) {
            var10 = 2;
        }
        if (p_149660_5_ == 3 && p_149660_1_.isBlockNormalCubeDefault(p_149660_2_, p_149660_3_, p_149660_4_ - 1, true)) {
            var10 = 0;
        }
        if (p_149660_5_ == 4 && p_149660_1_.isBlockNormalCubeDefault(p_149660_2_ + 1, p_149660_3_, p_149660_4_, true)) {
            var10 = 1;
        }
        if (p_149660_5_ == 5 && p_149660_1_.isBlockNormalCubeDefault(p_149660_2_ - 1, p_149660_3_, p_149660_4_, true)) {
            var10 = 3;
        }
        return var10;
    }
    
    @Override
    public void onPostBlockPlaced(final World p_149714_1_, final int p_149714_2_, final int p_149714_3_, final int p_149714_4_, final int p_149714_5_) {
        this.func_150136_a(p_149714_1_, p_149714_2_, p_149714_3_, p_149714_4_, false, p_149714_5_, false, -1, 0);
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        if (p_149695_5_ != this && this.func_150137_e(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_)) {
            final int var6 = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_);
            final int var7 = var6 & 0x3;
            boolean var8 = false;
            if (!p_149695_1_.getBlock(p_149695_2_ - 1, p_149695_3_, p_149695_4_).isNormalCube() && var7 == 3) {
                var8 = true;
            }
            if (!p_149695_1_.getBlock(p_149695_2_ + 1, p_149695_3_, p_149695_4_).isNormalCube() && var7 == 1) {
                var8 = true;
            }
            if (!p_149695_1_.getBlock(p_149695_2_, p_149695_3_, p_149695_4_ - 1).isNormalCube() && var7 == 0) {
                var8 = true;
            }
            if (!p_149695_1_.getBlock(p_149695_2_, p_149695_3_, p_149695_4_ + 1).isNormalCube() && var7 == 2) {
                var8 = true;
            }
            if (var8) {
                this.dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, var6, 0);
                p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
            }
        }
    }
    
    public void func_150136_a(final World p_150136_1_, final int p_150136_2_, final int p_150136_3_, final int p_150136_4_, final boolean p_150136_5_, int p_150136_6_, final boolean p_150136_7_, final int p_150136_8_, final int p_150136_9_) {
        final int var10 = p_150136_6_ & 0x3;
        final boolean var11 = (p_150136_6_ & 0x4) == 0x4;
        final boolean var12 = (p_150136_6_ & 0x8) == 0x8;
        boolean var13 = !p_150136_5_;
        boolean var14 = false;
        final boolean var15 = !World.doesBlockHaveSolidTopSurface(p_150136_1_, p_150136_2_, p_150136_3_ - 1, p_150136_4_);
        final int var16 = Direction.offsetX[var10];
        final int var17 = Direction.offsetZ[var10];
        int var18 = 0;
        final int[] var19 = new int[42];
        int var20 = 1;
        while (var20 < 42) {
            final int var21 = p_150136_2_ + var16 * var20;
            final int var22 = p_150136_4_ + var17 * var20;
            final Block var23 = p_150136_1_.getBlock(var21, p_150136_3_, var22);
            if (var23 == Blocks.tripwire_hook) {
                final int var24 = p_150136_1_.getBlockMetadata(var21, p_150136_3_, var22);
                if ((var24 & 0x3) == Direction.rotateOpposite[var10]) {
                    var18 = var20;
                    break;
                }
                break;
            }
            else {
                if (var23 != Blocks.tripwire && var20 != p_150136_8_) {
                    var19[var20] = -1;
                    var13 = false;
                }
                else {
                    final int var24 = (var20 == p_150136_8_) ? p_150136_9_ : p_150136_1_.getBlockMetadata(var21, p_150136_3_, var22);
                    final boolean var25 = (var24 & 0x8) != 0x8;
                    final boolean var26 = (var24 & 0x1) == 0x1;
                    final boolean var27 = (var24 & 0x2) == 0x2;
                    var13 &= (var27 == var15);
                    var14 |= (var25 && var26);
                    var19[var20] = var24;
                    if (var20 == p_150136_8_) {
                        p_150136_1_.scheduleBlockUpdate(p_150136_2_, p_150136_3_, p_150136_4_, this, this.func_149738_a(p_150136_1_));
                        var13 &= var25;
                    }
                }
                ++var20;
            }
        }
        var13 &= (var18 > 1);
        var14 &= var13;
        var20 = ((var13 ? 4 : 0) | (var14 ? 8 : 0));
        p_150136_6_ = (var10 | var20);
        if (var18 > 0) {
            final int var21 = p_150136_2_ + var16 * var18;
            final int var22 = p_150136_4_ + var17 * var18;
            final int var28 = Direction.rotateOpposite[var10];
            p_150136_1_.setBlockMetadataWithNotify(var21, p_150136_3_, var22, var28 | var20, 3);
            this.func_150134_a(p_150136_1_, var21, p_150136_3_, var22, var28);
            this.func_150135_a(p_150136_1_, var21, p_150136_3_, var22, var13, var14, var11, var12);
        }
        this.func_150135_a(p_150136_1_, p_150136_2_, p_150136_3_, p_150136_4_, var13, var14, var11, var12);
        if (!p_150136_5_) {
            p_150136_1_.setBlockMetadataWithNotify(p_150136_2_, p_150136_3_, p_150136_4_, p_150136_6_, 3);
            if (p_150136_7_) {
                this.func_150134_a(p_150136_1_, p_150136_2_, p_150136_3_, p_150136_4_, var10);
            }
        }
        if (var11 != var13) {
            for (int var21 = 1; var21 < var18; ++var21) {
                final int var22 = p_150136_2_ + var16 * var21;
                final int var28 = p_150136_4_ + var17 * var21;
                int var24 = var19[var21];
                if (var24 >= 0) {
                    if (var13) {
                        var24 |= 0x4;
                    }
                    else {
                        var24 &= 0xFFFFFFFB;
                    }
                    p_150136_1_.setBlockMetadataWithNotify(var22, p_150136_3_, var28, var24, 3);
                }
            }
        }
    }
    
    @Override
    public void updateTick(final World p_149674_1_, final int p_149674_2_, final int p_149674_3_, final int p_149674_4_, final Random p_149674_5_) {
        this.func_150136_a(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, false, p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_), true, -1, 0);
    }
    
    private void func_150135_a(final World p_150135_1_, final int p_150135_2_, final int p_150135_3_, final int p_150135_4_, final boolean p_150135_5_, final boolean p_150135_6_, final boolean p_150135_7_, final boolean p_150135_8_) {
        if (p_150135_6_ && !p_150135_8_) {
            p_150135_1_.playSoundEffect(p_150135_2_ + 0.5, p_150135_3_ + 0.1, p_150135_4_ + 0.5, "random.click", 0.4f, 0.6f);
        }
        else if (!p_150135_6_ && p_150135_8_) {
            p_150135_1_.playSoundEffect(p_150135_2_ + 0.5, p_150135_3_ + 0.1, p_150135_4_ + 0.5, "random.click", 0.4f, 0.5f);
        }
        else if (p_150135_5_ && !p_150135_7_) {
            p_150135_1_.playSoundEffect(p_150135_2_ + 0.5, p_150135_3_ + 0.1, p_150135_4_ + 0.5, "random.click", 0.4f, 0.7f);
        }
        else if (!p_150135_5_ && p_150135_7_) {
            p_150135_1_.playSoundEffect(p_150135_2_ + 0.5, p_150135_3_ + 0.1, p_150135_4_ + 0.5, "random.bowhit", 0.4f, 1.2f / (p_150135_1_.rand.nextFloat() * 0.2f + 0.9f));
        }
    }
    
    private void func_150134_a(final World p_150134_1_, final int p_150134_2_, final int p_150134_3_, final int p_150134_4_, final int p_150134_5_) {
        p_150134_1_.notifyBlocksOfNeighborChange(p_150134_2_, p_150134_3_, p_150134_4_, this);
        if (p_150134_5_ == 3) {
            p_150134_1_.notifyBlocksOfNeighborChange(p_150134_2_ - 1, p_150134_3_, p_150134_4_, this);
        }
        else if (p_150134_5_ == 1) {
            p_150134_1_.notifyBlocksOfNeighborChange(p_150134_2_ + 1, p_150134_3_, p_150134_4_, this);
        }
        else if (p_150134_5_ == 0) {
            p_150134_1_.notifyBlocksOfNeighborChange(p_150134_2_, p_150134_3_, p_150134_4_ - 1, this);
        }
        else if (p_150134_5_ == 2) {
            p_150134_1_.notifyBlocksOfNeighborChange(p_150134_2_, p_150134_3_, p_150134_4_ + 1, this);
        }
    }
    
    private boolean func_150137_e(final World p_150137_1_, final int p_150137_2_, final int p_150137_3_, final int p_150137_4_) {
        if (!this.canPlaceBlockAt(p_150137_1_, p_150137_2_, p_150137_3_, p_150137_4_)) {
            this.dropBlockAsItem(p_150137_1_, p_150137_2_, p_150137_3_, p_150137_4_, p_150137_1_.getBlockMetadata(p_150137_2_, p_150137_3_, p_150137_4_), 0);
            p_150137_1_.setBlockToAir(p_150137_2_, p_150137_3_, p_150137_4_);
            return false;
        }
        return true;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        final int var5 = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_) & 0x3;
        final float var6 = 0.1875f;
        if (var5 == 3) {
            this.setBlockBounds(0.0f, 0.2f, 0.5f - var6, var6 * 2.0f, 0.8f, 0.5f + var6);
        }
        else if (var5 == 1) {
            this.setBlockBounds(1.0f - var6 * 2.0f, 0.2f, 0.5f - var6, 1.0f, 0.8f, 0.5f + var6);
        }
        else if (var5 == 0) {
            this.setBlockBounds(0.5f - var6, 0.2f, 0.0f, 0.5f + var6, 0.8f, var6 * 2.0f);
        }
        else if (var5 == 2) {
            this.setBlockBounds(0.5f - var6, 0.2f, 1.0f - var6 * 2.0f, 0.5f + var6, 0.8f, 1.0f);
        }
    }
    
    @Override
    public void breakBlock(final World p_149749_1_, final int p_149749_2_, final int p_149749_3_, final int p_149749_4_, final Block p_149749_5_, final int p_149749_6_) {
        final boolean var7 = (p_149749_6_ & 0x4) == 0x4;
        final boolean var8 = (p_149749_6_ & 0x8) == 0x8;
        if (var7 || var8) {
            this.func_150136_a(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, true, p_149749_6_, false, -1, 0);
        }
        if (var8) {
            p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_, p_149749_4_, this);
            final int var9 = p_149749_6_ & 0x3;
            if (var9 == 3) {
                p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_ - 1, p_149749_3_, p_149749_4_, this);
            }
            else if (var9 == 1) {
                p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_ + 1, p_149749_3_, p_149749_4_, this);
            }
            else if (var9 == 0) {
                p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_, p_149749_4_ - 1, this);
            }
            else if (var9 == 2) {
                p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_, p_149749_4_ + 1, this);
            }
        }
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess p_149709_1_, final int p_149709_2_, final int p_149709_3_, final int p_149709_4_, final int p_149709_5_) {
        return ((p_149709_1_.getBlockMetadata(p_149709_2_, p_149709_3_, p_149709_4_) & 0x8) == 0x8) ? 15 : 0;
    }
    
    @Override
    public int isProvidingStrongPower(final IBlockAccess p_149748_1_, final int p_149748_2_, final int p_149748_3_, final int p_149748_4_, final int p_149748_5_) {
        final int var6 = p_149748_1_.getBlockMetadata(p_149748_2_, p_149748_3_, p_149748_4_);
        if ((var6 & 0x8) != 0x8) {
            return 0;
        }
        final int var7 = var6 & 0x3;
        return (var7 == 2 && p_149748_5_ == 2) ? 15 : ((var7 == 0 && p_149748_5_ == 3) ? 15 : ((var7 == 1 && p_149748_5_ == 4) ? 15 : ((var7 == 3 && p_149748_5_ == 5) ? 15 : 0)));
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
}
