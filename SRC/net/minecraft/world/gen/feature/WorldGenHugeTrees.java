package net.minecraft.world.gen.feature;

import java.util.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;

public abstract class WorldGenHugeTrees extends WorldGenAbstractTree
{
    protected final int baseHeight;
    protected final int woodMetadata;
    protected final int leavesMetadata;
    protected int field_150538_d;
    private static final String __OBFID = "CL_00000423";
    
    public WorldGenHugeTrees(final boolean p_i45458_1_, final int p_i45458_2_, final int p_i45458_3_, final int p_i45458_4_, final int p_i45458_5_) {
        super(p_i45458_1_);
        this.baseHeight = p_i45458_2_;
        this.field_150538_d = p_i45458_3_;
        this.woodMetadata = p_i45458_4_;
        this.leavesMetadata = p_i45458_5_;
    }
    
    protected int func_150533_a(final Random p_150533_1_) {
        int var2 = p_150533_1_.nextInt(3) + this.baseHeight;
        if (this.field_150538_d > 1) {
            var2 += p_150533_1_.nextInt(this.field_150538_d);
        }
        return var2;
    }
    
    private boolean func_150536_b(final World p_150536_1_, final Random p_150536_2_, final int p_150536_3_, final int p_150536_4_, final int p_150536_5_, final int p_150536_6_) {
        boolean var7 = true;
        if (p_150536_4_ >= 1 && p_150536_4_ + p_150536_6_ + 1 <= 256) {
            for (int var8 = p_150536_4_; var8 <= p_150536_4_ + 1 + p_150536_6_; ++var8) {
                byte var9 = 2;
                if (var8 == p_150536_4_) {
                    var9 = 1;
                }
                if (var8 >= p_150536_4_ + 1 + p_150536_6_ - 2) {
                    var9 = 2;
                }
                for (int var10 = p_150536_3_ - var9; var10 <= p_150536_3_ + var9 && var7; ++var10) {
                    for (int var11 = p_150536_5_ - var9; var11 <= p_150536_5_ + var9 && var7; ++var11) {
                        if (var8 >= 0 && var8 < 256) {
                            final Block var12 = p_150536_1_.getBlock(var10, var8, var11);
                            if (!this.func_150523_a(var12)) {
                                var7 = false;
                            }
                        }
                        else {
                            var7 = false;
                        }
                    }
                }
            }
            return var7;
        }
        return false;
    }
    
    private boolean func_150532_c(final World p_150532_1_, final Random p_150532_2_, final int p_150532_3_, final int p_150532_4_, final int p_150532_5_) {
        final Block var6 = p_150532_1_.getBlock(p_150532_3_, p_150532_4_ - 1, p_150532_5_);
        if ((var6 == Blocks.grass || var6 == Blocks.dirt) && p_150532_4_ >= 2) {
            p_150532_1_.setBlock(p_150532_3_, p_150532_4_ - 1, p_150532_5_, Blocks.dirt, 0, 2);
            p_150532_1_.setBlock(p_150532_3_ + 1, p_150532_4_ - 1, p_150532_5_, Blocks.dirt, 0, 2);
            p_150532_1_.setBlock(p_150532_3_, p_150532_4_ - 1, p_150532_5_ + 1, Blocks.dirt, 0, 2);
            p_150532_1_.setBlock(p_150532_3_ + 1, p_150532_4_ - 1, p_150532_5_ + 1, Blocks.dirt, 0, 2);
            return true;
        }
        return false;
    }
    
    protected boolean func_150537_a(final World p_150537_1_, final Random p_150537_2_, final int p_150537_3_, final int p_150537_4_, final int p_150537_5_, final int p_150537_6_) {
        return this.func_150536_b(p_150537_1_, p_150537_2_, p_150537_3_, p_150537_4_, p_150537_5_, p_150537_6_) && this.func_150532_c(p_150537_1_, p_150537_2_, p_150537_3_, p_150537_4_, p_150537_5_);
    }
    
    protected void func_150535_a(final World p_150535_1_, final int p_150535_2_, final int p_150535_3_, final int p_150535_4_, final int p_150535_5_, final Random p_150535_6_) {
        final int var7 = p_150535_5_ * p_150535_5_;
        for (int var8 = p_150535_2_ - p_150535_5_; var8 <= p_150535_2_ + p_150535_5_ + 1; ++var8) {
            final int var9 = var8 - p_150535_2_;
            for (int var10 = p_150535_4_ - p_150535_5_; var10 <= p_150535_4_ + p_150535_5_ + 1; ++var10) {
                final int var11 = var10 - p_150535_4_;
                final int var12 = var9 - 1;
                final int var13 = var11 - 1;
                if (var9 * var9 + var11 * var11 <= var7 || var12 * var12 + var13 * var13 <= var7 || var9 * var9 + var13 * var13 <= var7 || var12 * var12 + var11 * var11 <= var7) {
                    final Block var14 = p_150535_1_.getBlock(var8, p_150535_3_, var10);
                    if (var14.getMaterial() == Material.air || var14.getMaterial() == Material.leaves) {
                        this.func_150516_a(p_150535_1_, var8, p_150535_3_, var10, Blocks.leaves, this.leavesMetadata);
                    }
                }
            }
        }
    }
    
    protected void func_150534_b(final World p_150534_1_, final int p_150534_2_, final int p_150534_3_, final int p_150534_4_, final int p_150534_5_, final Random p_150534_6_) {
        final int var7 = p_150534_5_ * p_150534_5_;
        for (int var8 = p_150534_2_ - p_150534_5_; var8 <= p_150534_2_ + p_150534_5_; ++var8) {
            final int var9 = var8 - p_150534_2_;
            for (int var10 = p_150534_4_ - p_150534_5_; var10 <= p_150534_4_ + p_150534_5_; ++var10) {
                final int var11 = var10 - p_150534_4_;
                if (var9 * var9 + var11 * var11 <= var7) {
                    final Block var12 = p_150534_1_.getBlock(var8, p_150534_3_, var10);
                    if (var12.getMaterial() == Material.air || var12.getMaterial() == Material.leaves) {
                        this.func_150516_a(p_150534_1_, var8, p_150534_3_, var10, Blocks.leaves, this.leavesMetadata);
                    }
                }
            }
        }
    }
}
