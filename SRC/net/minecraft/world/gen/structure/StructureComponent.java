package net.minecraft.world.gen.structure;

import net.minecraft.nbt.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import net.minecraft.tileentity.*;
import net.minecraft.item.*;

public abstract class StructureComponent
{
    protected StructureBoundingBox boundingBox;
    protected int coordBaseMode;
    protected int componentType;
    private static final String __OBFID = "CL_00000511";
    
    public StructureComponent() {
    }
    
    protected StructureComponent(final int p_i2091_1_) {
        this.componentType = p_i2091_1_;
        this.coordBaseMode = -1;
    }
    
    public NBTTagCompound func_143010_b() {
        final NBTTagCompound var1 = new NBTTagCompound();
        var1.setString("id", MapGenStructureIO.func_143036_a(this));
        var1.setTag("BB", this.boundingBox.func_151535_h());
        var1.setInteger("O", this.coordBaseMode);
        var1.setInteger("GD", this.componentType);
        this.func_143012_a(var1);
        return var1;
    }
    
    protected abstract void func_143012_a(final NBTTagCompound p0);
    
    public void func_143009_a(final World p_143009_1_, final NBTTagCompound p_143009_2_) {
        if (p_143009_2_.hasKey("BB")) {
            this.boundingBox = new StructureBoundingBox(p_143009_2_.getIntArray("BB"));
        }
        this.coordBaseMode = p_143009_2_.getInteger("O");
        this.componentType = p_143009_2_.getInteger("GD");
        this.func_143011_b(p_143009_2_);
    }
    
    protected abstract void func_143011_b(final NBTTagCompound p0);
    
    public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
    }
    
    public abstract boolean addComponentParts(final World p0, final Random p1, final StructureBoundingBox p2);
    
    public StructureBoundingBox getBoundingBox() {
        return this.boundingBox;
    }
    
    public int getComponentType() {
        return this.componentType;
    }
    
    public static StructureComponent findIntersecting(final List p_74883_0_, final StructureBoundingBox p_74883_1_) {
        for (final StructureComponent var3 : p_74883_0_) {
            if (var3.getBoundingBox() != null && var3.getBoundingBox().intersectsWith(p_74883_1_)) {
                return var3;
            }
        }
        return null;
    }
    
    public ChunkPosition func_151553_a() {
        return new ChunkPosition(this.boundingBox.getCenterX(), this.boundingBox.getCenterY(), this.boundingBox.getCenterZ());
    }
    
    protected boolean isLiquidInStructureBoundingBox(final World p_74860_1_, final StructureBoundingBox p_74860_2_) {
        final int var3 = Math.max(this.boundingBox.minX - 1, p_74860_2_.minX);
        final int var4 = Math.max(this.boundingBox.minY - 1, p_74860_2_.minY);
        final int var5 = Math.max(this.boundingBox.minZ - 1, p_74860_2_.minZ);
        final int var6 = Math.min(this.boundingBox.maxX + 1, p_74860_2_.maxX);
        final int var7 = Math.min(this.boundingBox.maxY + 1, p_74860_2_.maxY);
        final int var8 = Math.min(this.boundingBox.maxZ + 1, p_74860_2_.maxZ);
        for (int var9 = var3; var9 <= var6; ++var9) {
            for (int var10 = var5; var10 <= var8; ++var10) {
                if (p_74860_1_.getBlock(var9, var4, var10).getMaterial().isLiquid()) {
                    return true;
                }
                if (p_74860_1_.getBlock(var9, var7, var10).getMaterial().isLiquid()) {
                    return true;
                }
            }
        }
        for (int var9 = var3; var9 <= var6; ++var9) {
            for (int var10 = var4; var10 <= var7; ++var10) {
                if (p_74860_1_.getBlock(var9, var10, var5).getMaterial().isLiquid()) {
                    return true;
                }
                if (p_74860_1_.getBlock(var9, var10, var8).getMaterial().isLiquid()) {
                    return true;
                }
            }
        }
        for (int var9 = var5; var9 <= var8; ++var9) {
            for (int var10 = var4; var10 <= var7; ++var10) {
                if (p_74860_1_.getBlock(var3, var10, var9).getMaterial().isLiquid()) {
                    return true;
                }
                if (p_74860_1_.getBlock(var6, var10, var9).getMaterial().isLiquid()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    protected int getXWithOffset(final int p_74865_1_, final int p_74865_2_) {
        switch (this.coordBaseMode) {
            case 0:
            case 2: {
                return this.boundingBox.minX + p_74865_1_;
            }
            case 1: {
                return this.boundingBox.maxX - p_74865_2_;
            }
            case 3: {
                return this.boundingBox.minX + p_74865_2_;
            }
            default: {
                return p_74865_1_;
            }
        }
    }
    
    protected int getYWithOffset(final int p_74862_1_) {
        return (this.coordBaseMode == -1) ? p_74862_1_ : (p_74862_1_ + this.boundingBox.minY);
    }
    
    protected int getZWithOffset(final int p_74873_1_, final int p_74873_2_) {
        switch (this.coordBaseMode) {
            case 0: {
                return this.boundingBox.minZ + p_74873_2_;
            }
            case 1:
            case 3: {
                return this.boundingBox.minZ + p_74873_1_;
            }
            case 2: {
                return this.boundingBox.maxZ - p_74873_2_;
            }
            default: {
                return p_74873_2_;
            }
        }
    }
    
    protected int func_151555_a(final Block p_151555_1_, final int p_151555_2_) {
        if (p_151555_1_ == Blocks.rail) {
            if (this.coordBaseMode == 1 || this.coordBaseMode == 3) {
                if (p_151555_2_ == 1) {
                    return 0;
                }
                return 1;
            }
        }
        else if (p_151555_1_ != Blocks.wooden_door && p_151555_1_ != Blocks.iron_door) {
            if (p_151555_1_ != Blocks.stone_stairs && p_151555_1_ != Blocks.oak_stairs && p_151555_1_ != Blocks.nether_brick_stairs && p_151555_1_ != Blocks.stone_brick_stairs && p_151555_1_ != Blocks.sandstone_stairs) {
                if (p_151555_1_ == Blocks.ladder) {
                    if (this.coordBaseMode == 0) {
                        if (p_151555_2_ == 2) {
                            return 3;
                        }
                        if (p_151555_2_ == 3) {
                            return 2;
                        }
                    }
                    else if (this.coordBaseMode == 1) {
                        if (p_151555_2_ == 2) {
                            return 4;
                        }
                        if (p_151555_2_ == 3) {
                            return 5;
                        }
                        if (p_151555_2_ == 4) {
                            return 2;
                        }
                        if (p_151555_2_ == 5) {
                            return 3;
                        }
                    }
                    else if (this.coordBaseMode == 3) {
                        if (p_151555_2_ == 2) {
                            return 5;
                        }
                        if (p_151555_2_ == 3) {
                            return 4;
                        }
                        if (p_151555_2_ == 4) {
                            return 2;
                        }
                        if (p_151555_2_ == 5) {
                            return 3;
                        }
                    }
                }
                else if (p_151555_1_ == Blocks.stone_button) {
                    if (this.coordBaseMode == 0) {
                        if (p_151555_2_ == 3) {
                            return 4;
                        }
                        if (p_151555_2_ == 4) {
                            return 3;
                        }
                    }
                    else if (this.coordBaseMode == 1) {
                        if (p_151555_2_ == 3) {
                            return 1;
                        }
                        if (p_151555_2_ == 4) {
                            return 2;
                        }
                        if (p_151555_2_ == 2) {
                            return 3;
                        }
                        if (p_151555_2_ == 1) {
                            return 4;
                        }
                    }
                    else if (this.coordBaseMode == 3) {
                        if (p_151555_2_ == 3) {
                            return 2;
                        }
                        if (p_151555_2_ == 4) {
                            return 1;
                        }
                        if (p_151555_2_ == 2) {
                            return 3;
                        }
                        if (p_151555_2_ == 1) {
                            return 4;
                        }
                    }
                }
                else if (p_151555_1_ != Blocks.tripwire_hook && !(p_151555_1_ instanceof BlockDirectional)) {
                    if (p_151555_1_ == Blocks.piston || p_151555_1_ == Blocks.sticky_piston || p_151555_1_ == Blocks.lever || p_151555_1_ == Blocks.dispenser) {
                        if (this.coordBaseMode == 0) {
                            if (p_151555_2_ == 2 || p_151555_2_ == 3) {
                                return Facing.oppositeSide[p_151555_2_];
                            }
                        }
                        else if (this.coordBaseMode == 1) {
                            if (p_151555_2_ == 2) {
                                return 4;
                            }
                            if (p_151555_2_ == 3) {
                                return 5;
                            }
                            if (p_151555_2_ == 4) {
                                return 2;
                            }
                            if (p_151555_2_ == 5) {
                                return 3;
                            }
                        }
                        else if (this.coordBaseMode == 3) {
                            if (p_151555_2_ == 2) {
                                return 5;
                            }
                            if (p_151555_2_ == 3) {
                                return 4;
                            }
                            if (p_151555_2_ == 4) {
                                return 2;
                            }
                            if (p_151555_2_ == 5) {
                                return 3;
                            }
                        }
                    }
                }
                else if (this.coordBaseMode == 0) {
                    if (p_151555_2_ == 0 || p_151555_2_ == 2) {
                        return Direction.rotateOpposite[p_151555_2_];
                    }
                }
                else if (this.coordBaseMode == 1) {
                    if (p_151555_2_ == 2) {
                        return 1;
                    }
                    if (p_151555_2_ == 0) {
                        return 3;
                    }
                    if (p_151555_2_ == 1) {
                        return 2;
                    }
                    if (p_151555_2_ == 3) {
                        return 0;
                    }
                }
                else if (this.coordBaseMode == 3) {
                    if (p_151555_2_ == 2) {
                        return 3;
                    }
                    if (p_151555_2_ == 0) {
                        return 1;
                    }
                    if (p_151555_2_ == 1) {
                        return 2;
                    }
                    if (p_151555_2_ == 3) {
                        return 0;
                    }
                }
            }
            else if (this.coordBaseMode == 0) {
                if (p_151555_2_ == 2) {
                    return 3;
                }
                if (p_151555_2_ == 3) {
                    return 2;
                }
            }
            else if (this.coordBaseMode == 1) {
                if (p_151555_2_ == 0) {
                    return 2;
                }
                if (p_151555_2_ == 1) {
                    return 3;
                }
                if (p_151555_2_ == 2) {
                    return 0;
                }
                if (p_151555_2_ == 3) {
                    return 1;
                }
            }
            else if (this.coordBaseMode == 3) {
                if (p_151555_2_ == 0) {
                    return 2;
                }
                if (p_151555_2_ == 1) {
                    return 3;
                }
                if (p_151555_2_ == 2) {
                    return 1;
                }
                if (p_151555_2_ == 3) {
                    return 0;
                }
            }
        }
        else if (this.coordBaseMode == 0) {
            if (p_151555_2_ == 0) {
                return 2;
            }
            if (p_151555_2_ == 2) {
                return 0;
            }
        }
        else {
            if (this.coordBaseMode == 1) {
                return p_151555_2_ + 1 & 0x3;
            }
            if (this.coordBaseMode == 3) {
                return p_151555_2_ + 3 & 0x3;
            }
        }
        return p_151555_2_;
    }
    
    protected void func_151550_a(final World p_151550_1_, final Block p_151550_2_, final int p_151550_3_, final int p_151550_4_, final int p_151550_5_, final int p_151550_6_, final StructureBoundingBox p_151550_7_) {
        final int var8 = this.getXWithOffset(p_151550_4_, p_151550_6_);
        final int var9 = this.getYWithOffset(p_151550_5_);
        final int var10 = this.getZWithOffset(p_151550_4_, p_151550_6_);
        if (p_151550_7_.isVecInside(var8, var9, var10)) {
            p_151550_1_.setBlock(var8, var9, var10, p_151550_2_, p_151550_3_, 2);
        }
    }
    
    protected Block func_151548_a(final World p_151548_1_, final int p_151548_2_, final int p_151548_3_, final int p_151548_4_, final StructureBoundingBox p_151548_5_) {
        final int var6 = this.getXWithOffset(p_151548_2_, p_151548_4_);
        final int var7 = this.getYWithOffset(p_151548_3_);
        final int var8 = this.getZWithOffset(p_151548_2_, p_151548_4_);
        return p_151548_5_.isVecInside(var6, var7, var8) ? p_151548_1_.getBlock(var6, var7, var8) : Blocks.air;
    }
    
    protected void fillWithAir(final World p_74878_1_, final StructureBoundingBox p_74878_2_, final int p_74878_3_, final int p_74878_4_, final int p_74878_5_, final int p_74878_6_, final int p_74878_7_, final int p_74878_8_) {
        for (int var9 = p_74878_4_; var9 <= p_74878_7_; ++var9) {
            for (int var10 = p_74878_3_; var10 <= p_74878_6_; ++var10) {
                for (int var11 = p_74878_5_; var11 <= p_74878_8_; ++var11) {
                    this.func_151550_a(p_74878_1_, Blocks.air, 0, var10, var9, var11, p_74878_2_);
                }
            }
        }
    }
    
    protected void func_151549_a(final World p_151549_1_, final StructureBoundingBox p_151549_2_, final int p_151549_3_, final int p_151549_4_, final int p_151549_5_, final int p_151549_6_, final int p_151549_7_, final int p_151549_8_, final Block p_151549_9_, final Block p_151549_10_, final boolean p_151549_11_) {
        for (int var12 = p_151549_4_; var12 <= p_151549_7_; ++var12) {
            for (int var13 = p_151549_3_; var13 <= p_151549_6_; ++var13) {
                for (int var14 = p_151549_5_; var14 <= p_151549_8_; ++var14) {
                    if (!p_151549_11_ || this.func_151548_a(p_151549_1_, var13, var12, var14, p_151549_2_).getMaterial() != Material.air) {
                        if (var12 != p_151549_4_ && var12 != p_151549_7_ && var13 != p_151549_3_ && var13 != p_151549_6_ && var14 != p_151549_5_ && var14 != p_151549_8_) {
                            this.func_151550_a(p_151549_1_, p_151549_10_, 0, var13, var12, var14, p_151549_2_);
                        }
                        else {
                            this.func_151550_a(p_151549_1_, p_151549_9_, 0, var13, var12, var14, p_151549_2_);
                        }
                    }
                }
            }
        }
    }
    
    protected void func_151556_a(final World p_151556_1_, final StructureBoundingBox p_151556_2_, final int p_151556_3_, final int p_151556_4_, final int p_151556_5_, final int p_151556_6_, final int p_151556_7_, final int p_151556_8_, final Block p_151556_9_, final int p_151556_10_, final Block p_151556_11_, final int p_151556_12_, final boolean p_151556_13_) {
        for (int var14 = p_151556_4_; var14 <= p_151556_7_; ++var14) {
            for (int var15 = p_151556_3_; var15 <= p_151556_6_; ++var15) {
                for (int var16 = p_151556_5_; var16 <= p_151556_8_; ++var16) {
                    if (!p_151556_13_ || this.func_151548_a(p_151556_1_, var15, var14, var16, p_151556_2_).getMaterial() != Material.air) {
                        if (var14 != p_151556_4_ && var14 != p_151556_7_ && var15 != p_151556_3_ && var15 != p_151556_6_ && var16 != p_151556_5_ && var16 != p_151556_8_) {
                            this.func_151550_a(p_151556_1_, p_151556_11_, p_151556_12_, var15, var14, var16, p_151556_2_);
                        }
                        else {
                            this.func_151550_a(p_151556_1_, p_151556_9_, p_151556_10_, var15, var14, var16, p_151556_2_);
                        }
                    }
                }
            }
        }
    }
    
    protected void fillWithRandomizedBlocks(final World p_74882_1_, final StructureBoundingBox p_74882_2_, final int p_74882_3_, final int p_74882_4_, final int p_74882_5_, final int p_74882_6_, final int p_74882_7_, final int p_74882_8_, final boolean p_74882_9_, final Random p_74882_10_, final BlockSelector p_74882_11_) {
        for (int var12 = p_74882_4_; var12 <= p_74882_7_; ++var12) {
            for (int var13 = p_74882_3_; var13 <= p_74882_6_; ++var13) {
                for (int var14 = p_74882_5_; var14 <= p_74882_8_; ++var14) {
                    if (!p_74882_9_ || this.func_151548_a(p_74882_1_, var13, var12, var14, p_74882_2_).getMaterial() != Material.air) {
                        p_74882_11_.selectBlocks(p_74882_10_, var13, var12, var14, var12 == p_74882_4_ || var12 == p_74882_7_ || var13 == p_74882_3_ || var13 == p_74882_6_ || var14 == p_74882_5_ || var14 == p_74882_8_);
                        this.func_151550_a(p_74882_1_, p_74882_11_.func_151561_a(), p_74882_11_.getSelectedBlockMetaData(), var13, var12, var14, p_74882_2_);
                    }
                }
            }
        }
    }
    
    protected void func_151551_a(final World p_151551_1_, final StructureBoundingBox p_151551_2_, final Random p_151551_3_, final float p_151551_4_, final int p_151551_5_, final int p_151551_6_, final int p_151551_7_, final int p_151551_8_, final int p_151551_9_, final int p_151551_10_, final Block p_151551_11_, final Block p_151551_12_, final boolean p_151551_13_) {
        for (int var14 = p_151551_6_; var14 <= p_151551_9_; ++var14) {
            for (int var15 = p_151551_5_; var15 <= p_151551_8_; ++var15) {
                for (int var16 = p_151551_7_; var16 <= p_151551_10_; ++var16) {
                    if (p_151551_3_.nextFloat() <= p_151551_4_ && (!p_151551_13_ || this.func_151548_a(p_151551_1_, var15, var14, var16, p_151551_2_).getMaterial() != Material.air)) {
                        if (var14 != p_151551_6_ && var14 != p_151551_9_ && var15 != p_151551_5_ && var15 != p_151551_8_ && var16 != p_151551_7_ && var16 != p_151551_10_) {
                            this.func_151550_a(p_151551_1_, p_151551_12_, 0, var15, var14, var16, p_151551_2_);
                        }
                        else {
                            this.func_151550_a(p_151551_1_, p_151551_11_, 0, var15, var14, var16, p_151551_2_);
                        }
                    }
                }
            }
        }
    }
    
    protected void func_151552_a(final World p_151552_1_, final StructureBoundingBox p_151552_2_, final Random p_151552_3_, final float p_151552_4_, final int p_151552_5_, final int p_151552_6_, final int p_151552_7_, final Block p_151552_8_, final int p_151552_9_) {
        if (p_151552_3_.nextFloat() < p_151552_4_) {
            this.func_151550_a(p_151552_1_, p_151552_8_, p_151552_9_, p_151552_5_, p_151552_6_, p_151552_7_, p_151552_2_);
        }
    }
    
    protected void func_151547_a(final World p_151547_1_, final StructureBoundingBox p_151547_2_, final int p_151547_3_, final int p_151547_4_, final int p_151547_5_, final int p_151547_6_, final int p_151547_7_, final int p_151547_8_, final Block p_151547_9_, final boolean p_151547_10_) {
        final float var11 = (float)(p_151547_6_ - p_151547_3_ + 1);
        final float var12 = (float)(p_151547_7_ - p_151547_4_ + 1);
        final float var13 = (float)(p_151547_8_ - p_151547_5_ + 1);
        final float var14 = p_151547_3_ + var11 / 2.0f;
        final float var15 = p_151547_5_ + var13 / 2.0f;
        for (int var16 = p_151547_4_; var16 <= p_151547_7_; ++var16) {
            final float var17 = (var16 - p_151547_4_) / var12;
            for (int var18 = p_151547_3_; var18 <= p_151547_6_; ++var18) {
                final float var19 = (var18 - var14) / (var11 * 0.5f);
                for (int var20 = p_151547_5_; var20 <= p_151547_8_; ++var20) {
                    final float var21 = (var20 - var15) / (var13 * 0.5f);
                    if (!p_151547_10_ || this.func_151548_a(p_151547_1_, var18, var16, var20, p_151547_2_).getMaterial() != Material.air) {
                        final float var22 = var19 * var19 + var17 * var17 + var21 * var21;
                        if (var22 <= 1.05f) {
                            this.func_151550_a(p_151547_1_, p_151547_9_, 0, var18, var16, var20, p_151547_2_);
                        }
                    }
                }
            }
        }
    }
    
    protected void clearCurrentPositionBlocksUpwards(final World p_74871_1_, final int p_74871_2_, final int p_74871_3_, final int p_74871_4_, final StructureBoundingBox p_74871_5_) {
        final int var6 = this.getXWithOffset(p_74871_2_, p_74871_4_);
        int var7 = this.getYWithOffset(p_74871_3_);
        final int var8 = this.getZWithOffset(p_74871_2_, p_74871_4_);
        if (p_74871_5_.isVecInside(var6, var7, var8)) {
            while (!p_74871_1_.isAirBlock(var6, var7, var8) && var7 < 255) {
                p_74871_1_.setBlock(var6, var7, var8, Blocks.air, 0, 2);
                ++var7;
            }
        }
    }
    
    protected void func_151554_b(final World p_151554_1_, final Block p_151554_2_, final int p_151554_3_, final int p_151554_4_, final int p_151554_5_, final int p_151554_6_, final StructureBoundingBox p_151554_7_) {
        final int var8 = this.getXWithOffset(p_151554_4_, p_151554_6_);
        int var9 = this.getYWithOffset(p_151554_5_);
        final int var10 = this.getZWithOffset(p_151554_4_, p_151554_6_);
        if (p_151554_7_.isVecInside(var8, var9, var10)) {
            while ((p_151554_1_.isAirBlock(var8, var9, var10) || p_151554_1_.getBlock(var8, var9, var10).getMaterial().isLiquid()) && var9 > 1) {
                p_151554_1_.setBlock(var8, var9, var10, p_151554_2_, p_151554_3_, 2);
                --var9;
            }
        }
    }
    
    protected boolean generateStructureChestContents(final World p_74879_1_, final StructureBoundingBox p_74879_2_, final Random p_74879_3_, final int p_74879_4_, final int p_74879_5_, final int p_74879_6_, final WeightedRandomChestContent[] p_74879_7_, final int p_74879_8_) {
        final int var9 = this.getXWithOffset(p_74879_4_, p_74879_6_);
        final int var10 = this.getYWithOffset(p_74879_5_);
        final int var11 = this.getZWithOffset(p_74879_4_, p_74879_6_);
        if (p_74879_2_.isVecInside(var9, var10, var11) && p_74879_1_.getBlock(var9, var10, var11) != Blocks.chest) {
            p_74879_1_.setBlock(var9, var10, var11, Blocks.chest, 0, 2);
            final TileEntityChest var12 = (TileEntityChest)p_74879_1_.getTileEntity(var9, var10, var11);
            if (var12 != null) {
                WeightedRandomChestContent.generateChestContents(p_74879_3_, p_74879_7_, var12, p_74879_8_);
            }
            return true;
        }
        return false;
    }
    
    protected boolean generateStructureDispenserContents(final World p_74869_1_, final StructureBoundingBox p_74869_2_, final Random p_74869_3_, final int p_74869_4_, final int p_74869_5_, final int p_74869_6_, final int p_74869_7_, final WeightedRandomChestContent[] p_74869_8_, final int p_74869_9_) {
        final int var10 = this.getXWithOffset(p_74869_4_, p_74869_6_);
        final int var11 = this.getYWithOffset(p_74869_5_);
        final int var12 = this.getZWithOffset(p_74869_4_, p_74869_6_);
        if (p_74869_2_.isVecInside(var10, var11, var12) && p_74869_1_.getBlock(var10, var11, var12) != Blocks.dispenser) {
            p_74869_1_.setBlock(var10, var11, var12, Blocks.dispenser, this.func_151555_a(Blocks.dispenser, p_74869_7_), 2);
            final TileEntityDispenser var13 = (TileEntityDispenser)p_74869_1_.getTileEntity(var10, var11, var12);
            if (var13 != null) {
                WeightedRandomChestContent.func_150706_a(p_74869_3_, p_74869_8_, var13, p_74869_9_);
            }
            return true;
        }
        return false;
    }
    
    protected void placeDoorAtCurrentPosition(final World p_74881_1_, final StructureBoundingBox p_74881_2_, final Random p_74881_3_, final int p_74881_4_, final int p_74881_5_, final int p_74881_6_, final int p_74881_7_) {
        final int var8 = this.getXWithOffset(p_74881_4_, p_74881_6_);
        final int var9 = this.getYWithOffset(p_74881_5_);
        final int var10 = this.getZWithOffset(p_74881_4_, p_74881_6_);
        if (p_74881_2_.isVecInside(var8, var9, var10)) {
            ItemDoor.func_150924_a(p_74881_1_, var8, var9, var10, p_74881_7_, Blocks.wooden_door);
        }
    }
    
    public abstract static class BlockSelector
    {
        protected Block field_151562_a;
        protected int selectedBlockMetaData;
        private static final String __OBFID = "CL_00000512";
        
        protected BlockSelector() {
            this.field_151562_a = Blocks.air;
        }
        
        public abstract void selectBlocks(final Random p0, final int p1, final int p2, final int p3, final boolean p4);
        
        public Block func_151561_a() {
            return this.field_151562_a;
        }
        
        public int getSelectedBlockMetaData() {
            return this.selectedBlockMetaData;
        }
    }
}
