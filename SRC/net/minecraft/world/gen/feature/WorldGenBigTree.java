package net.minecraft.world.gen.feature;

import java.util.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;

public class WorldGenBigTree extends WorldGenAbstractTree
{
    static final byte[] otherCoordPairs;
    Random rand;
    World worldObj;
    int[] basePos;
    int heightLimit;
    int height;
    double heightAttenuation;
    double branchDensity;
    double branchSlope;
    double scaleWidth;
    double leafDensity;
    int trunkSize;
    int heightLimitLimit;
    int leafDistanceLimit;
    int[][] leafNodes;
    private static final String __OBFID = "CL_00000400";
    
    public WorldGenBigTree(final boolean p_i2008_1_) {
        super(p_i2008_1_);
        this.rand = new Random();
        this.basePos = new int[] { 0, 0, 0 };
        this.heightAttenuation = 0.618;
        this.branchDensity = 1.0;
        this.branchSlope = 0.381;
        this.scaleWidth = 1.0;
        this.leafDensity = 1.0;
        this.trunkSize = 1;
        this.heightLimitLimit = 12;
        this.leafDistanceLimit = 4;
    }
    
    void generateLeafNodeList() {
        this.height = (int)(this.heightLimit * this.heightAttenuation);
        if (this.height >= this.heightLimit) {
            this.height = this.heightLimit - 1;
        }
        int var1 = (int)(1.382 + Math.pow(this.leafDensity * this.heightLimit / 13.0, 2.0));
        if (var1 < 1) {
            var1 = 1;
        }
        final int[][] var2 = new int[var1 * this.heightLimit][4];
        int var3 = this.basePos[1] + this.heightLimit - this.leafDistanceLimit;
        int var4 = 1;
        final int var5 = this.basePos[1] + this.height;
        int var6 = var3 - this.basePos[1];
        var2[0][0] = this.basePos[0];
        var2[0][1] = var3;
        var2[0][2] = this.basePos[2];
        var2[0][3] = var5;
        --var3;
        while (var6 >= 0) {
            int var7 = 0;
            final float var8 = this.layerSize(var6);
            if (var8 < 0.0f) {
                --var3;
                --var6;
            }
            else {
                final double var9 = 0.5;
                while (var7 < var1) {
                    final double var10 = this.scaleWidth * var8 * (this.rand.nextFloat() + 0.328);
                    final double var11 = this.rand.nextFloat() * 2.0 * 3.141592653589793;
                    final int var12 = MathHelper.floor_double(var10 * Math.sin(var11) + this.basePos[0] + var9);
                    final int var13 = MathHelper.floor_double(var10 * Math.cos(var11) + this.basePos[2] + var9);
                    final int[] var14 = { var12, var3, var13 };
                    final int[] var15 = { var12, var3 + this.leafDistanceLimit, var13 };
                    if (this.checkBlockLine(var14, var15) == -1) {
                        final int[] var16 = { this.basePos[0], this.basePos[1], this.basePos[2] };
                        final double var17 = Math.sqrt(Math.pow(Math.abs(this.basePos[0] - var14[0]), 2.0) + Math.pow(Math.abs(this.basePos[2] - var14[2]), 2.0));
                        final double var18 = var17 * this.branchSlope;
                        if (var14[1] - var18 > var5) {
                            var16[1] = var5;
                        }
                        else {
                            var16[1] = (int)(var14[1] - var18);
                        }
                        if (this.checkBlockLine(var16, var14) == -1) {
                            var2[var4][0] = var12;
                            var2[var4][1] = var3;
                            var2[var4][2] = var13;
                            var2[var4][3] = var16[1];
                            ++var4;
                        }
                    }
                    ++var7;
                }
                --var3;
                --var6;
            }
        }
        System.arraycopy(var2, 0, this.leafNodes = new int[var4][4], 0, var4);
    }
    
    void func_150529_a(final int p_150529_1_, final int p_150529_2_, final int p_150529_3_, final float p_150529_4_, final byte p_150529_5_, final Block p_150529_6_) {
        final int var7 = (int)(p_150529_4_ + 0.618);
        final byte var8 = WorldGenBigTree.otherCoordPairs[p_150529_5_];
        final byte var9 = WorldGenBigTree.otherCoordPairs[p_150529_5_ + 3];
        final int[] var10 = { p_150529_1_, p_150529_2_, p_150529_3_ };
        final int[] var11 = { 0, 0, 0 };
        int var12 = -var7;
        int var13 = -var7;
        var11[p_150529_5_] = var10[p_150529_5_];
        while (var12 <= var7) {
            var11[var8] = var10[var8] + var12;
            for (var13 = -var7; var13 <= var7; ++var13) {
                final double var14 = Math.pow(Math.abs(var12) + 0.5, 2.0) + Math.pow(Math.abs(var13) + 0.5, 2.0);
                if (var14 <= p_150529_4_ * p_150529_4_) {
                    var11[var9] = var10[var9] + var13;
                    final Block var15 = this.worldObj.getBlock(var11[0], var11[1], var11[2]);
                    if (var15.getMaterial() == Material.air || var15.getMaterial() == Material.leaves) {
                        this.func_150516_a(this.worldObj, var11[0], var11[1], var11[2], p_150529_6_, 0);
                    }
                }
            }
            ++var12;
        }
    }
    
    float layerSize(final int p_76490_1_) {
        if (p_76490_1_ < (float)this.heightLimit * 0.3) {
            return -1.618f;
        }
        final float var2 = this.heightLimit / 2.0f;
        final float var3 = this.heightLimit / 2.0f - p_76490_1_;
        float var4;
        if (var3 == 0.0f) {
            var4 = var2;
        }
        else if (Math.abs(var3) >= var2) {
            var4 = 0.0f;
        }
        else {
            var4 = (float)Math.sqrt(Math.pow(Math.abs(var2), 2.0) - Math.pow(Math.abs(var3), 2.0));
        }
        var4 *= 0.5f;
        return var4;
    }
    
    float leafSize(final int p_76495_1_) {
        return (p_76495_1_ >= 0 && p_76495_1_ < this.leafDistanceLimit) ? ((p_76495_1_ != 0 && p_76495_1_ != this.leafDistanceLimit - 1) ? 3.0f : 2.0f) : -1.0f;
    }
    
    void generateLeafNode(final int p_76491_1_, final int p_76491_2_, final int p_76491_3_) {
        for (int var4 = p_76491_2_, var5 = p_76491_2_ + this.leafDistanceLimit; var4 < var5; ++var4) {
            final float var6 = this.leafSize(var4 - p_76491_2_);
            this.func_150529_a(p_76491_1_, var4, p_76491_3_, var6, (byte)1, Blocks.leaves);
        }
    }
    
    void func_150530_a(final int[] p_150530_1_, final int[] p_150530_2_, final Block p_150530_3_) {
        final int[] var4 = { 0, 0, 0 };
        byte var5 = 0;
        byte var6 = 0;
        while (var5 < 3) {
            var4[var5] = p_150530_2_[var5] - p_150530_1_[var5];
            if (Math.abs(var4[var5]) > Math.abs(var4[var6])) {
                var6 = var5;
            }
            ++var5;
        }
        if (var4[var6] != 0) {
            final byte var7 = WorldGenBigTree.otherCoordPairs[var6];
            final byte var8 = WorldGenBigTree.otherCoordPairs[var6 + 3];
            byte var9;
            if (var4[var6] > 0) {
                var9 = 1;
            }
            else {
                var9 = -1;
            }
            final double var10 = var4[var7] / (double)var4[var6];
            final double var11 = var4[var8] / (double)var4[var6];
            final int[] var12 = { 0, 0, 0 };
            for (int var13 = 0, var14 = var4[var6] + var9; var13 != var14; var13 += var9) {
                var12[var6] = MathHelper.floor_double(p_150530_1_[var6] + var13 + 0.5);
                var12[var7] = MathHelper.floor_double(p_150530_1_[var7] + var13 * var10 + 0.5);
                var12[var8] = MathHelper.floor_double(p_150530_1_[var8] + var13 * var11 + 0.5);
                byte var15 = 0;
                final int var16 = Math.abs(var12[0] - p_150530_1_[0]);
                final int var17 = Math.abs(var12[2] - p_150530_1_[2]);
                final int var18 = Math.max(var16, var17);
                if (var18 > 0) {
                    if (var16 == var18) {
                        var15 = 4;
                    }
                    else if (var17 == var18) {
                        var15 = 8;
                    }
                }
                this.func_150516_a(this.worldObj, var12[0], var12[1], var12[2], p_150530_3_, var15);
            }
        }
    }
    
    void generateLeaves() {
        for (int var1 = 0, var2 = this.leafNodes.length; var1 < var2; ++var1) {
            final int var3 = this.leafNodes[var1][0];
            final int var4 = this.leafNodes[var1][1];
            final int var5 = this.leafNodes[var1][2];
            this.generateLeafNode(var3, var4, var5);
        }
    }
    
    boolean leafNodeNeedsBase(final int p_76493_1_) {
        return p_76493_1_ >= this.heightLimit * 0.2;
    }
    
    void generateTrunk() {
        final int var1 = this.basePos[0];
        final int var2 = this.basePos[1];
        final int var3 = this.basePos[1] + this.height;
        final int var4 = this.basePos[2];
        final int[] var5 = { var1, var2, var4 };
        final int[] var6 = { var1, var3, var4 };
        this.func_150530_a(var5, var6, Blocks.log);
        if (this.trunkSize == 2) {
            final int[] array = var5;
            final int n = 0;
            ++array[n];
            final int[] array2 = var6;
            final int n2 = 0;
            ++array2[n2];
            this.func_150530_a(var5, var6, Blocks.log);
            final int[] array3 = var5;
            final int n3 = 2;
            ++array3[n3];
            final int[] array4 = var6;
            final int n4 = 2;
            ++array4[n4];
            this.func_150530_a(var5, var6, Blocks.log);
            final int[] array5 = var5;
            final int n5 = 0;
            --array5[n5];
            final int[] array6 = var6;
            final int n6 = 0;
            --array6[n6];
            this.func_150530_a(var5, var6, Blocks.log);
        }
    }
    
    void generateLeafNodeBases() {
        int var1 = 0;
        final int var2 = this.leafNodes.length;
        final int[] var3 = { this.basePos[0], this.basePos[1], this.basePos[2] };
        while (var1 < var2) {
            final int[] var4 = this.leafNodes[var1];
            final int[] var5 = { var4[0], var4[1], var4[2] };
            var3[1] = var4[3];
            final int var6 = var3[1] - this.basePos[1];
            if (this.leafNodeNeedsBase(var6)) {
                this.func_150530_a(var3, var5, Blocks.log);
            }
            ++var1;
        }
    }
    
    int checkBlockLine(final int[] p_76496_1_, final int[] p_76496_2_) {
        final int[] var3 = { 0, 0, 0 };
        byte var4 = 0;
        byte var5 = 0;
        while (var4 < 3) {
            var3[var4] = p_76496_2_[var4] - p_76496_1_[var4];
            if (Math.abs(var3[var4]) > Math.abs(var3[var5])) {
                var5 = var4;
            }
            ++var4;
        }
        if (var3[var5] == 0) {
            return -1;
        }
        final byte var6 = WorldGenBigTree.otherCoordPairs[var5];
        final byte var7 = WorldGenBigTree.otherCoordPairs[var5 + 3];
        byte var8;
        if (var3[var5] > 0) {
            var8 = 1;
        }
        else {
            var8 = -1;
        }
        final double var9 = var3[var6] / (double)var3[var5];
        final double var10 = var3[var7] / (double)var3[var5];
        final int[] var11 = { 0, 0, 0 };
        int var12;
        int var13;
        for (var12 = 0, var13 = var3[var5] + var8; var12 != var13; var12 += var8) {
            var11[var5] = p_76496_1_[var5] + var12;
            var11[var6] = MathHelper.floor_double(p_76496_1_[var6] + var12 * var9);
            var11[var7] = MathHelper.floor_double(p_76496_1_[var7] + var12 * var10);
            final Block var14 = this.worldObj.getBlock(var11[0], var11[1], var11[2]);
            if (!this.func_150523_a(var14)) {
                break;
            }
        }
        return (var12 == var13) ? -1 : Math.abs(var12);
    }
    
    boolean validTreeLocation() {
        final int[] var1 = { this.basePos[0], this.basePos[1], this.basePos[2] };
        final int[] var2 = { this.basePos[0], this.basePos[1] + this.heightLimit - 1, this.basePos[2] };
        final Block var3 = this.worldObj.getBlock(this.basePos[0], this.basePos[1] - 1, this.basePos[2]);
        if (var3 != Blocks.dirt && var3 != Blocks.grass && var3 != Blocks.farmland) {
            return false;
        }
        final int var4 = this.checkBlockLine(var1, var2);
        if (var4 == -1) {
            return true;
        }
        if (var4 < 6) {
            return false;
        }
        this.heightLimit = var4;
        return true;
    }
    
    @Override
    public void setScale(final double p_76487_1_, final double p_76487_3_, final double p_76487_5_) {
        this.heightLimitLimit = (int)(p_76487_1_ * 12.0);
        if (p_76487_1_ > 0.5) {
            this.leafDistanceLimit = 5;
        }
        this.scaleWidth = p_76487_3_;
        this.leafDensity = p_76487_5_;
    }
    
    @Override
    public boolean generate(final World p_76484_1_, final Random p_76484_2_, final int p_76484_3_, final int p_76484_4_, final int p_76484_5_) {
        this.worldObj = p_76484_1_;
        final long var6 = p_76484_2_.nextLong();
        this.rand.setSeed(var6);
        this.basePos[0] = p_76484_3_;
        this.basePos[1] = p_76484_4_;
        this.basePos[2] = p_76484_5_;
        if (this.heightLimit == 0) {
            this.heightLimit = 5 + this.rand.nextInt(this.heightLimitLimit);
        }
        if (!this.validTreeLocation()) {
            return false;
        }
        this.generateLeafNodeList();
        this.generateLeaves();
        this.generateTrunk();
        this.generateLeafNodeBases();
        return true;
    }
    
    static {
        otherCoordPairs = new byte[] { 2, 0, 0, 1, 2, 1 };
    }
}
