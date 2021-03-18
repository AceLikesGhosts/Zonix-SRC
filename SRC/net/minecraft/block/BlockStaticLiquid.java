package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.init.*;

public class BlockStaticLiquid extends BlockLiquid
{
    private static final String __OBFID = "CL_00000315";
    
    protected BlockStaticLiquid(final Material p_i45429_1_) {
        super(p_i45429_1_);
        this.setTickRandomly(false);
        if (p_i45429_1_ == Material.lava) {
            this.setTickRandomly(true);
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        super.onNeighborBlockChange(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
        if (p_149695_1_.getBlock(p_149695_2_, p_149695_3_, p_149695_4_) == this) {
            this.setNotStationary(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
        }
    }
    
    private void setNotStationary(final World p_149818_1_, final int p_149818_2_, final int p_149818_3_, final int p_149818_4_) {
        final int var5 = p_149818_1_.getBlockMetadata(p_149818_2_, p_149818_3_, p_149818_4_);
        p_149818_1_.setBlock(p_149818_2_, p_149818_3_, p_149818_4_, Block.getBlockById(Block.getIdFromBlock(this) - 1), var5, 2);
        p_149818_1_.scheduleBlockUpdate(p_149818_2_, p_149818_3_, p_149818_4_, Block.getBlockById(Block.getIdFromBlock(this) - 1), this.func_149738_a(p_149818_1_));
    }
    
    @Override
    public void updateTick(final World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, final Random p_149674_5_) {
        if (this.blockMaterial == Material.lava) {
            final int var6 = p_149674_5_.nextInt(3);
            for (int var7 = 0; var7 < var6; ++var7) {
                p_149674_2_ += p_149674_5_.nextInt(3) - 1;
                ++p_149674_3_;
                p_149674_4_ += p_149674_5_.nextInt(3) - 1;
                final Block var8 = p_149674_1_.getBlock(p_149674_2_, p_149674_3_, p_149674_4_);
                if (var8.blockMaterial == Material.air) {
                    if (this.isFlammable(p_149674_1_, p_149674_2_ - 1, p_149674_3_, p_149674_4_) || this.isFlammable(p_149674_1_, p_149674_2_ + 1, p_149674_3_, p_149674_4_) || this.isFlammable(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_ - 1) || this.isFlammable(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_ + 1) || this.isFlammable(p_149674_1_, p_149674_2_, p_149674_3_ - 1, p_149674_4_) || this.isFlammable(p_149674_1_, p_149674_2_, p_149674_3_ + 1, p_149674_4_)) {
                        p_149674_1_.setBlock(p_149674_2_, p_149674_3_, p_149674_4_, Blocks.fire);
                        return;
                    }
                }
                else if (var8.blockMaterial.blocksMovement()) {
                    return;
                }
            }
            if (var6 == 0) {
                final int var7 = p_149674_2_;
                final int var9 = p_149674_4_;
                for (int var10 = 0; var10 < 3; ++var10) {
                    p_149674_2_ = var7 + p_149674_5_.nextInt(3) - 1;
                    p_149674_4_ = var9 + p_149674_5_.nextInt(3) - 1;
                    if (p_149674_1_.isAirBlock(p_149674_2_, p_149674_3_ + 1, p_149674_4_) && this.isFlammable(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_)) {
                        p_149674_1_.setBlock(p_149674_2_, p_149674_3_ + 1, p_149674_4_, Blocks.fire);
                    }
                }
            }
        }
    }
    
    private boolean isFlammable(final World p_149817_1_, final int p_149817_2_, final int p_149817_3_, final int p_149817_4_) {
        return p_149817_1_.getBlock(p_149817_2_, p_149817_3_, p_149817_4_).getMaterial().getCanBurn();
    }
}
