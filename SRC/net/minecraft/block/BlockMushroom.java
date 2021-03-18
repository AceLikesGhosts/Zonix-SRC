package net.minecraft.block;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.world.gen.feature.*;

public class BlockMushroom extends BlockBush implements IGrowable
{
    private static final String __OBFID = "CL_00000272";
    
    protected BlockMushroom() {
        final float var1 = 0.2f;
        this.setBlockBounds(0.5f - var1, 0.0f, 0.5f - var1, 0.5f + var1, var1 * 2.0f, 0.5f + var1);
        this.setTickRandomly(true);
    }
    
    @Override
    public void updateTick(final World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, final Random p_149674_5_) {
        if (p_149674_5_.nextInt(25) == 0) {
            final byte var6 = 4;
            int var7 = 5;
            for (int var8 = p_149674_2_ - var6; var8 <= p_149674_2_ + var6; ++var8) {
                for (int var9 = p_149674_4_ - var6; var9 <= p_149674_4_ + var6; ++var9) {
                    for (int var10 = p_149674_3_ - 1; var10 <= p_149674_3_ + 1; ++var10) {
                        if (p_149674_1_.getBlock(var8, var10, var9) == this && --var7 <= 0) {
                            return;
                        }
                    }
                }
            }
            int var8 = p_149674_2_ + p_149674_5_.nextInt(3) - 1;
            int var9 = p_149674_3_ + p_149674_5_.nextInt(2) - p_149674_5_.nextInt(2);
            int var10 = p_149674_4_ + p_149674_5_.nextInt(3) - 1;
            for (int var11 = 0; var11 < 4; ++var11) {
                if (p_149674_1_.isAirBlock(var8, var9, var10) && this.canBlockStay(p_149674_1_, var8, var9, var10)) {
                    p_149674_2_ = var8;
                    p_149674_3_ = var9;
                    p_149674_4_ = var10;
                }
                var8 = p_149674_2_ + p_149674_5_.nextInt(3) - 1;
                var9 = p_149674_3_ + p_149674_5_.nextInt(2) - p_149674_5_.nextInt(2);
                var10 = p_149674_4_ + p_149674_5_.nextInt(3) - 1;
            }
            if (p_149674_1_.isAirBlock(var8, var9, var10) && this.canBlockStay(p_149674_1_, var8, var9, var10)) {
                p_149674_1_.setBlock(var8, var9, var10, this, 0, 2);
            }
        }
    }
    
    @Override
    public boolean canPlaceBlockAt(final World p_149742_1_, final int p_149742_2_, final int p_149742_3_, final int p_149742_4_) {
        return super.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_) && this.canBlockStay(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_);
    }
    
    @Override
    protected boolean func_149854_a(final Block p_149854_1_) {
        return p_149854_1_.func_149730_j();
    }
    
    @Override
    public boolean canBlockStay(final World p_149718_1_, final int p_149718_2_, final int p_149718_3_, final int p_149718_4_) {
        if (p_149718_3_ >= 0 && p_149718_3_ < 256) {
            final Block var5 = p_149718_1_.getBlock(p_149718_2_, p_149718_3_ - 1, p_149718_4_);
            return var5 == Blocks.mycelium || (var5 == Blocks.dirt && p_149718_1_.getBlockMetadata(p_149718_2_, p_149718_3_ - 1, p_149718_4_) == 2) || (p_149718_1_.getFullBlockLightValue(p_149718_2_, p_149718_3_, p_149718_4_) < 13 && this.func_149854_a(var5));
        }
        return false;
    }
    
    public boolean func_149884_c(final World p_149884_1_, final int p_149884_2_, final int p_149884_3_, final int p_149884_4_, final Random p_149884_5_) {
        final int var6 = p_149884_1_.getBlockMetadata(p_149884_2_, p_149884_3_, p_149884_4_);
        p_149884_1_.setBlockToAir(p_149884_2_, p_149884_3_, p_149884_4_);
        WorldGenBigMushroom var7 = null;
        if (this == Blocks.brown_mushroom) {
            var7 = new WorldGenBigMushroom(0);
        }
        else if (this == Blocks.red_mushroom) {
            var7 = new WorldGenBigMushroom(1);
        }
        if (var7 != null && var7.generate(p_149884_1_, p_149884_5_, p_149884_2_, p_149884_3_, p_149884_4_)) {
            return true;
        }
        p_149884_1_.setBlock(p_149884_2_, p_149884_3_, p_149884_4_, this, var6, 3);
        return false;
    }
    
    @Override
    public boolean func_149851_a(final World p_149851_1_, final int p_149851_2_, final int p_149851_3_, final int p_149851_4_, final boolean p_149851_5_) {
        return true;
    }
    
    @Override
    public boolean func_149852_a(final World p_149852_1_, final Random p_149852_2_, final int p_149852_3_, final int p_149852_4_, final int p_149852_5_) {
        return p_149852_2_.nextFloat() < 0.4;
    }
    
    @Override
    public void func_149853_b(final World p_149853_1_, final Random p_149853_2_, final int p_149853_3_, final int p_149853_4_, final int p_149853_5_) {
        this.func_149884_c(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_, p_149853_2_);
    }
}
