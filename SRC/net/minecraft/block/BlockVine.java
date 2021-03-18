package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class BlockVine extends Block
{
    private static final String __OBFID = "CL_00000330";
    
    public BlockVine() {
        super(Material.vine);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public int getRenderType() {
        return 20;
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
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        final float var5 = 0.0625f;
        final int var6 = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
        float var7 = 1.0f;
        float var8 = 1.0f;
        float var9 = 1.0f;
        float var10 = 0.0f;
        float var11 = 0.0f;
        float var12 = 0.0f;
        boolean var13 = var6 > 0;
        if ((var6 & 0x2) != 0x0) {
            var10 = Math.max(var10, 0.0625f);
            var7 = 0.0f;
            var8 = 0.0f;
            var11 = 1.0f;
            var9 = 0.0f;
            var12 = 1.0f;
            var13 = true;
        }
        if ((var6 & 0x8) != 0x0) {
            var7 = Math.min(var7, 0.9375f);
            var10 = 1.0f;
            var8 = 0.0f;
            var11 = 1.0f;
            var9 = 0.0f;
            var12 = 1.0f;
            var13 = true;
        }
        if ((var6 & 0x4) != 0x0) {
            var12 = Math.max(var12, 0.0625f);
            var9 = 0.0f;
            var7 = 0.0f;
            var10 = 1.0f;
            var8 = 0.0f;
            var11 = 1.0f;
            var13 = true;
        }
        if ((var6 & 0x1) != 0x0) {
            var9 = Math.min(var9, 0.9375f);
            var12 = 1.0f;
            var7 = 0.0f;
            var10 = 1.0f;
            var8 = 0.0f;
            var11 = 1.0f;
            var13 = true;
        }
        if (!var13 && this.func_150093_a(p_149719_1_.getBlock(p_149719_2_, p_149719_3_ + 1, p_149719_4_))) {
            var8 = Math.min(var8, 0.9375f);
            var11 = 1.0f;
            var7 = 0.0f;
            var10 = 1.0f;
            var9 = 0.0f;
            var12 = 1.0f;
        }
        this.setBlockBounds(var7, var8, var9, var10, var11, var12);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World p_149668_1_, final int p_149668_2_, final int p_149668_3_, final int p_149668_4_) {
        return null;
    }
    
    @Override
    public boolean canPlaceBlockOnSide(final World p_149707_1_, final int p_149707_2_, final int p_149707_3_, final int p_149707_4_, final int p_149707_5_) {
        switch (p_149707_5_) {
            case 1: {
                return this.func_150093_a(p_149707_1_.getBlock(p_149707_2_, p_149707_3_ + 1, p_149707_4_));
            }
            case 2: {
                return this.func_150093_a(p_149707_1_.getBlock(p_149707_2_, p_149707_3_, p_149707_4_ + 1));
            }
            case 3: {
                return this.func_150093_a(p_149707_1_.getBlock(p_149707_2_, p_149707_3_, p_149707_4_ - 1));
            }
            case 4: {
                return this.func_150093_a(p_149707_1_.getBlock(p_149707_2_ + 1, p_149707_3_, p_149707_4_));
            }
            case 5: {
                return this.func_150093_a(p_149707_1_.getBlock(p_149707_2_ - 1, p_149707_3_, p_149707_4_));
            }
            default: {
                return false;
            }
        }
    }
    
    private boolean func_150093_a(final Block p_150093_1_) {
        return p_150093_1_.renderAsNormalBlock() && p_150093_1_.blockMaterial.blocksMovement();
    }
    
    private boolean func_150094_e(final World p_150094_1_, final int p_150094_2_, final int p_150094_3_, final int p_150094_4_) {
        int var6;
        final int var5 = var6 = p_150094_1_.getBlockMetadata(p_150094_2_, p_150094_3_, p_150094_4_);
        if (var5 > 0) {
            for (int var7 = 0; var7 <= 3; ++var7) {
                final int var8 = 1 << var7;
                if ((var5 & var8) != 0x0 && !this.func_150093_a(p_150094_1_.getBlock(p_150094_2_ + Direction.offsetX[var7], p_150094_3_, p_150094_4_ + Direction.offsetZ[var7])) && (p_150094_1_.getBlock(p_150094_2_, p_150094_3_ + 1, p_150094_4_) != this || (p_150094_1_.getBlockMetadata(p_150094_2_, p_150094_3_ + 1, p_150094_4_) & var8) == 0x0)) {
                    var6 &= ~var8;
                }
            }
        }
        if (var6 == 0 && !this.func_150093_a(p_150094_1_.getBlock(p_150094_2_, p_150094_3_ + 1, p_150094_4_))) {
            return false;
        }
        if (var6 != var5) {
            p_150094_1_.setBlockMetadataWithNotify(p_150094_2_, p_150094_3_, p_150094_4_, var6, 2);
        }
        return true;
    }
    
    @Override
    public int getBlockColor() {
        return ColorizerFoliage.getFoliageColorBasic();
    }
    
    @Override
    public int getRenderColor(final int p_149741_1_) {
        return ColorizerFoliage.getFoliageColorBasic();
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess p_149720_1_, final int p_149720_2_, final int p_149720_3_, final int p_149720_4_) {
        return p_149720_1_.getBiomeGenForCoords(p_149720_2_, p_149720_4_).getBiomeFoliageColor(p_149720_2_, p_149720_3_, p_149720_4_);
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        if (!p_149695_1_.isClient && !this.func_150094_e(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_)) {
            this.dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_), 0);
            p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
        }
    }
    
    @Override
    public void updateTick(final World p_149674_1_, final int p_149674_2_, final int p_149674_3_, final int p_149674_4_, final Random p_149674_5_) {
        if (!p_149674_1_.isClient && p_149674_1_.rand.nextInt(4) == 0) {
            final byte var6 = 4;
            int var7 = 5;
            boolean var8 = false;
        Label_0118:
            for (int var9 = p_149674_2_ - var6; var9 <= p_149674_2_ + var6; ++var9) {
                for (int var10 = p_149674_4_ - var6; var10 <= p_149674_4_ + var6; ++var10) {
                    for (int var11 = p_149674_3_ - 1; var11 <= p_149674_3_ + 1; ++var11) {
                        if (p_149674_1_.getBlock(var9, var11, var10) == this && --var7 <= 0) {
                            var8 = true;
                            break Label_0118;
                        }
                    }
                }
            }
            int var9 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
            int var10 = p_149674_1_.rand.nextInt(6);
            int var11 = Direction.facingToDirection[var10];
            if (var10 == 1 && p_149674_3_ < 255 && p_149674_1_.isAirBlock(p_149674_2_, p_149674_3_ + 1, p_149674_4_)) {
                if (var8) {
                    return;
                }
                int var12 = p_149674_1_.rand.nextInt(16) & var9;
                if (var12 > 0) {
                    for (int var13 = 0; var13 <= 3; ++var13) {
                        if (!this.func_150093_a(p_149674_1_.getBlock(p_149674_2_ + Direction.offsetX[var13], p_149674_3_ + 1, p_149674_4_ + Direction.offsetZ[var13]))) {
                            var12 &= ~(1 << var13);
                        }
                    }
                    if (var12 > 0) {
                        p_149674_1_.setBlock(p_149674_2_, p_149674_3_ + 1, p_149674_4_, this, var12, 2);
                    }
                }
            }
            else if (var10 >= 2 && var10 <= 5 && (var9 & 1 << var11) == 0x0) {
                if (var8) {
                    return;
                }
                final Block var14 = p_149674_1_.getBlock(p_149674_2_ + Direction.offsetX[var11], p_149674_3_, p_149674_4_ + Direction.offsetZ[var11]);
                if (var14.blockMaterial == Material.air) {
                    final int var13 = var11 + 1 & 0x3;
                    final int var15 = var11 + 3 & 0x3;
                    if ((var9 & 1 << var13) != 0x0 && this.func_150093_a(p_149674_1_.getBlock(p_149674_2_ + Direction.offsetX[var11] + Direction.offsetX[var13], p_149674_3_, p_149674_4_ + Direction.offsetZ[var11] + Direction.offsetZ[var13]))) {
                        p_149674_1_.setBlock(p_149674_2_ + Direction.offsetX[var11], p_149674_3_, p_149674_4_ + Direction.offsetZ[var11], this, 1 << var13, 2);
                    }
                    else if ((var9 & 1 << var15) != 0x0 && this.func_150093_a(p_149674_1_.getBlock(p_149674_2_ + Direction.offsetX[var11] + Direction.offsetX[var15], p_149674_3_, p_149674_4_ + Direction.offsetZ[var11] + Direction.offsetZ[var15]))) {
                        p_149674_1_.setBlock(p_149674_2_ + Direction.offsetX[var11], p_149674_3_, p_149674_4_ + Direction.offsetZ[var11], this, 1 << var15, 2);
                    }
                    else if ((var9 & 1 << var13) != 0x0 && p_149674_1_.isAirBlock(p_149674_2_ + Direction.offsetX[var11] + Direction.offsetX[var13], p_149674_3_, p_149674_4_ + Direction.offsetZ[var11] + Direction.offsetZ[var13]) && this.func_150093_a(p_149674_1_.getBlock(p_149674_2_ + Direction.offsetX[var13], p_149674_3_, p_149674_4_ + Direction.offsetZ[var13]))) {
                        p_149674_1_.setBlock(p_149674_2_ + Direction.offsetX[var11] + Direction.offsetX[var13], p_149674_3_, p_149674_4_ + Direction.offsetZ[var11] + Direction.offsetZ[var13], this, 1 << (var11 + 2 & 0x3), 2);
                    }
                    else if ((var9 & 1 << var15) != 0x0 && p_149674_1_.isAirBlock(p_149674_2_ + Direction.offsetX[var11] + Direction.offsetX[var15], p_149674_3_, p_149674_4_ + Direction.offsetZ[var11] + Direction.offsetZ[var15]) && this.func_150093_a(p_149674_1_.getBlock(p_149674_2_ + Direction.offsetX[var15], p_149674_3_, p_149674_4_ + Direction.offsetZ[var15]))) {
                        p_149674_1_.setBlock(p_149674_2_ + Direction.offsetX[var11] + Direction.offsetX[var15], p_149674_3_, p_149674_4_ + Direction.offsetZ[var11] + Direction.offsetZ[var15], this, 1 << (var11 + 2 & 0x3), 2);
                    }
                    else if (this.func_150093_a(p_149674_1_.getBlock(p_149674_2_ + Direction.offsetX[var11], p_149674_3_ + 1, p_149674_4_ + Direction.offsetZ[var11]))) {
                        p_149674_1_.setBlock(p_149674_2_ + Direction.offsetX[var11], p_149674_3_, p_149674_4_ + Direction.offsetZ[var11], this, 0, 2);
                    }
                }
                else if (var14.blockMaterial.isOpaque() && var14.renderAsNormalBlock()) {
                    p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, var9 | 1 << var11, 2);
                }
            }
            else if (p_149674_3_ > 1) {
                final Block var14 = p_149674_1_.getBlock(p_149674_2_, p_149674_3_ - 1, p_149674_4_);
                if (var14.blockMaterial == Material.air) {
                    final int var13 = p_149674_1_.rand.nextInt(16) & var9;
                    if (var13 > 0) {
                        p_149674_1_.setBlock(p_149674_2_, p_149674_3_ - 1, p_149674_4_, this, var13, 2);
                    }
                }
                else if (var14 == this) {
                    final int var13 = p_149674_1_.rand.nextInt(16) & var9;
                    final int var15 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_ - 1, p_149674_4_);
                    if (var15 != (var15 | var13)) {
                        p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_ - 1, p_149674_4_, var15 | var13, 2);
                    }
                }
            }
        }
    }
    
    @Override
    public int onBlockPlaced(final World p_149660_1_, final int p_149660_2_, final int p_149660_3_, final int p_149660_4_, final int p_149660_5_, final float p_149660_6_, final float p_149660_7_, final float p_149660_8_, final int p_149660_9_) {
        byte var10 = 0;
        switch (p_149660_5_) {
            case 2: {
                var10 = 1;
                break;
            }
            case 3: {
                var10 = 4;
                break;
            }
            case 4: {
                var10 = 8;
                break;
            }
            case 5: {
                var10 = 2;
                break;
            }
        }
        return (var10 != 0) ? var10 : p_149660_9_;
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return null;
    }
    
    @Override
    public int quantityDropped(final Random p_149745_1_) {
        return 0;
    }
    
    @Override
    public void harvestBlock(final World p_149636_1_, final EntityPlayer p_149636_2_, final int p_149636_3_, final int p_149636_4_, final int p_149636_5_, final int p_149636_6_) {
        if (!p_149636_1_.isClient && p_149636_2_.getCurrentEquippedItem() != null && p_149636_2_.getCurrentEquippedItem().getItem() == Items.shears) {
            p_149636_2_.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(this)], 1);
            this.dropBlockAsItem_do(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, new ItemStack(Blocks.vine, 1, 0));
        }
        else {
            super.harvestBlock(p_149636_1_, p_149636_2_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_);
        }
    }
}
