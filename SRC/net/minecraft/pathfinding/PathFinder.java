package net.minecraft.pathfinding;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;

public class PathFinder
{
    private IBlockAccess worldMap;
    private Path path;
    private IntHashMap pointMap;
    private PathPoint[] pathOptions;
    private boolean isWoddenDoorAllowed;
    private boolean isMovementBlockAllowed;
    private boolean isPathingInWater;
    private boolean canEntityDrown;
    private static final String __OBFID = "CL_00000576";
    
    public PathFinder(final IBlockAccess p_i2137_1_, final boolean p_i2137_2_, final boolean p_i2137_3_, final boolean p_i2137_4_, final boolean p_i2137_5_) {
        this.path = new Path();
        this.pointMap = new IntHashMap();
        this.pathOptions = new PathPoint[32];
        this.worldMap = p_i2137_1_;
        this.isWoddenDoorAllowed = p_i2137_2_;
        this.isMovementBlockAllowed = p_i2137_3_;
        this.isPathingInWater = p_i2137_4_;
        this.canEntityDrown = p_i2137_5_;
    }
    
    public PathEntity createEntityPathTo(final Entity p_75856_1_, final Entity p_75856_2_, final float p_75856_3_) {
        return this.createEntityPathTo(p_75856_1_, p_75856_2_.posX, p_75856_2_.boundingBox.minY, p_75856_2_.posZ, p_75856_3_);
    }
    
    public PathEntity createEntityPathTo(final Entity p_75859_1_, final int p_75859_2_, final int p_75859_3_, final int p_75859_4_, final float p_75859_5_) {
        return this.createEntityPathTo(p_75859_1_, p_75859_2_ + 0.5f, p_75859_3_ + 0.5f, p_75859_4_ + 0.5f, p_75859_5_);
    }
    
    private PathEntity createEntityPathTo(final Entity p_75857_1_, final double p_75857_2_, final double p_75857_4_, final double p_75857_6_, final float p_75857_8_) {
        this.path.clearPath();
        this.pointMap.clearMap();
        boolean var9 = this.isPathingInWater;
        int var10 = MathHelper.floor_double(p_75857_1_.boundingBox.minY + 0.5);
        if (this.canEntityDrown && p_75857_1_.isInWater()) {
            var10 = (int)p_75857_1_.boundingBox.minY;
            for (Block var11 = this.worldMap.getBlock(MathHelper.floor_double(p_75857_1_.posX), var10, MathHelper.floor_double(p_75857_1_.posZ)); var11 == Blocks.flowing_water || var11 == Blocks.water; var11 = this.worldMap.getBlock(MathHelper.floor_double(p_75857_1_.posX), var10, MathHelper.floor_double(p_75857_1_.posZ))) {
                ++var10;
            }
            var9 = this.isPathingInWater;
            this.isPathingInWater = false;
        }
        else {
            var10 = MathHelper.floor_double(p_75857_1_.boundingBox.minY + 0.5);
        }
        final PathPoint var12 = this.openPoint(MathHelper.floor_double(p_75857_1_.boundingBox.minX), var10, MathHelper.floor_double(p_75857_1_.boundingBox.minZ));
        final PathPoint var13 = this.openPoint(MathHelper.floor_double(p_75857_2_ - p_75857_1_.width / 2.0f), MathHelper.floor_double(p_75857_4_), MathHelper.floor_double(p_75857_6_ - p_75857_1_.width / 2.0f));
        final PathPoint var14 = new PathPoint(MathHelper.floor_float(p_75857_1_.width + 1.0f), MathHelper.floor_float(p_75857_1_.height + 1.0f), MathHelper.floor_float(p_75857_1_.width + 1.0f));
        final PathEntity var15 = this.addToPath(p_75857_1_, var12, var13, var14, p_75857_8_);
        this.isPathingInWater = var9;
        return var15;
    }
    
    private PathEntity addToPath(final Entity p_75861_1_, final PathPoint p_75861_2_, final PathPoint p_75861_3_, final PathPoint p_75861_4_, final float p_75861_5_) {
        p_75861_2_.totalPathDistance = 0.0f;
        p_75861_2_.distanceToNext = p_75861_2_.distanceToSquared(p_75861_3_);
        p_75861_2_.distanceToTarget = p_75861_2_.distanceToNext;
        this.path.clearPath();
        this.path.addPoint(p_75861_2_);
        PathPoint var6 = p_75861_2_;
        while (!this.path.isPathEmpty()) {
            final PathPoint var7 = this.path.dequeue();
            if (var7.equals(p_75861_3_)) {
                return this.createEntityPath(p_75861_2_, p_75861_3_);
            }
            if (var7.distanceToSquared(p_75861_3_) < var6.distanceToSquared(p_75861_3_)) {
                var6 = var7;
            }
            var7.isFirst = true;
            for (int var8 = this.findPathOptions(p_75861_1_, var7, p_75861_4_, p_75861_3_, p_75861_5_), var9 = 0; var9 < var8; ++var9) {
                final PathPoint var10 = this.pathOptions[var9];
                final float var11 = var7.totalPathDistance + var7.distanceToSquared(var10);
                if (!var10.isAssigned() || var11 < var10.totalPathDistance) {
                    var10.previous = var7;
                    var10.totalPathDistance = var11;
                    var10.distanceToNext = var10.distanceToSquared(p_75861_3_);
                    if (var10.isAssigned()) {
                        this.path.changeDistance(var10, var10.totalPathDistance + var10.distanceToNext);
                    }
                    else {
                        var10.distanceToTarget = var10.totalPathDistance + var10.distanceToNext;
                        this.path.addPoint(var10);
                    }
                }
            }
        }
        if (var6 == p_75861_2_) {
            return null;
        }
        return this.createEntityPath(p_75861_2_, var6);
    }
    
    private int findPathOptions(final Entity p_75860_1_, final PathPoint p_75860_2_, final PathPoint p_75860_3_, final PathPoint p_75860_4_, final float p_75860_5_) {
        int var6 = 0;
        byte var7 = 0;
        if (this.getVerticalOffset(p_75860_1_, p_75860_2_.xCoord, p_75860_2_.yCoord + 1, p_75860_2_.zCoord, p_75860_3_) == 1) {
            var7 = 1;
        }
        final PathPoint var8 = this.getSafePoint(p_75860_1_, p_75860_2_.xCoord, p_75860_2_.yCoord, p_75860_2_.zCoord + 1, p_75860_3_, var7);
        final PathPoint var9 = this.getSafePoint(p_75860_1_, p_75860_2_.xCoord - 1, p_75860_2_.yCoord, p_75860_2_.zCoord, p_75860_3_, var7);
        final PathPoint var10 = this.getSafePoint(p_75860_1_, p_75860_2_.xCoord + 1, p_75860_2_.yCoord, p_75860_2_.zCoord, p_75860_3_, var7);
        final PathPoint var11 = this.getSafePoint(p_75860_1_, p_75860_2_.xCoord, p_75860_2_.yCoord, p_75860_2_.zCoord - 1, p_75860_3_, var7);
        if (var8 != null && !var8.isFirst && var8.distanceTo(p_75860_4_) < p_75860_5_) {
            this.pathOptions[var6++] = var8;
        }
        if (var9 != null && !var9.isFirst && var9.distanceTo(p_75860_4_) < p_75860_5_) {
            this.pathOptions[var6++] = var9;
        }
        if (var10 != null && !var10.isFirst && var10.distanceTo(p_75860_4_) < p_75860_5_) {
            this.pathOptions[var6++] = var10;
        }
        if (var11 != null && !var11.isFirst && var11.distanceTo(p_75860_4_) < p_75860_5_) {
            this.pathOptions[var6++] = var11;
        }
        return var6;
    }
    
    private PathPoint getSafePoint(final Entity p_75858_1_, final int p_75858_2_, int p_75858_3_, final int p_75858_4_, final PathPoint p_75858_5_, final int p_75858_6_) {
        PathPoint var7 = null;
        final int var8 = this.getVerticalOffset(p_75858_1_, p_75858_2_, p_75858_3_, p_75858_4_, p_75858_5_);
        if (var8 == 2) {
            return this.openPoint(p_75858_2_, p_75858_3_, p_75858_4_);
        }
        if (var8 == 1) {
            var7 = this.openPoint(p_75858_2_, p_75858_3_, p_75858_4_);
        }
        if (var7 == null && p_75858_6_ > 0 && var8 != -3 && var8 != -4 && this.getVerticalOffset(p_75858_1_, p_75858_2_, p_75858_3_ + p_75858_6_, p_75858_4_, p_75858_5_) == 1) {
            var7 = this.openPoint(p_75858_2_, p_75858_3_ + p_75858_6_, p_75858_4_);
            p_75858_3_ += p_75858_6_;
        }
        if (var7 != null) {
            int var9 = 0;
            int var10 = 0;
            while (p_75858_3_ > 0) {
                var10 = this.getVerticalOffset(p_75858_1_, p_75858_2_, p_75858_3_ - 1, p_75858_4_, p_75858_5_);
                if (this.isPathingInWater && var10 == -1) {
                    return null;
                }
                if (var10 != 1) {
                    break;
                }
                if (var9++ >= p_75858_1_.getMaxSafePointTries()) {
                    return null;
                }
                if (--p_75858_3_ <= 0) {
                    continue;
                }
                var7 = this.openPoint(p_75858_2_, p_75858_3_, p_75858_4_);
            }
            if (var10 == -2) {
                return null;
            }
        }
        return var7;
    }
    
    private final PathPoint openPoint(final int p_75854_1_, final int p_75854_2_, final int p_75854_3_) {
        final int var4 = PathPoint.makeHash(p_75854_1_, p_75854_2_, p_75854_3_);
        PathPoint var5 = (PathPoint)this.pointMap.lookup(var4);
        if (var5 == null) {
            var5 = new PathPoint(p_75854_1_, p_75854_2_, p_75854_3_);
            this.pointMap.addKey(var4, var5);
        }
        return var5;
    }
    
    public int getVerticalOffset(final Entity p_75855_1_, final int p_75855_2_, final int p_75855_3_, final int p_75855_4_, final PathPoint p_75855_5_) {
        return func_82565_a(p_75855_1_, p_75855_2_, p_75855_3_, p_75855_4_, p_75855_5_, this.isPathingInWater, this.isMovementBlockAllowed, this.isWoddenDoorAllowed);
    }
    
    public static int func_82565_a(final Entity p_82565_0_, final int p_82565_1_, final int p_82565_2_, final int p_82565_3_, final PathPoint p_82565_4_, final boolean p_82565_5_, final boolean p_82565_6_, final boolean p_82565_7_) {
        boolean var8 = false;
        for (int var9 = p_82565_1_; var9 < p_82565_1_ + p_82565_4_.xCoord; ++var9) {
            for (int var10 = p_82565_2_; var10 < p_82565_2_ + p_82565_4_.yCoord; ++var10) {
                for (int var11 = p_82565_3_; var11 < p_82565_3_ + p_82565_4_.zCoord; ++var11) {
                    final Block var12 = p_82565_0_.worldObj.getBlock(var9, var10, var11);
                    if (var12.getMaterial() != Material.air) {
                        if (var12 == Blocks.trapdoor) {
                            var8 = true;
                        }
                        else if (var12 != Blocks.flowing_water && var12 != Blocks.water) {
                            if (!p_82565_7_ && var12 == Blocks.wooden_door) {
                                return 0;
                            }
                        }
                        else {
                            if (p_82565_5_) {
                                return -1;
                            }
                            var8 = true;
                        }
                        final int var13 = var12.getRenderType();
                        if (p_82565_0_.worldObj.getBlock(var9, var10, var11).getRenderType() == 9) {
                            final int var14 = MathHelper.floor_double(p_82565_0_.posX);
                            final int var15 = MathHelper.floor_double(p_82565_0_.posY);
                            final int var16 = MathHelper.floor_double(p_82565_0_.posZ);
                            if (p_82565_0_.worldObj.getBlock(var14, var15, var16).getRenderType() != 9 && p_82565_0_.worldObj.getBlock(var14, var15 - 1, var16).getRenderType() != 9) {
                                return -3;
                            }
                        }
                        else if (!var12.getBlocksMovement(p_82565_0_.worldObj, var9, var10, var11) && (!p_82565_6_ || var12 != Blocks.wooden_door)) {
                            if (var13 == 11 || var12 == Blocks.fence_gate || var13 == 32) {
                                return -3;
                            }
                            if (var12 == Blocks.trapdoor) {
                                return -4;
                            }
                            final Material var17 = var12.getMaterial();
                            if (var17 != Material.lava) {
                                return 0;
                            }
                            if (!p_82565_0_.handleLavaMovement()) {
                                return -2;
                            }
                        }
                    }
                }
            }
        }
        return var8 ? 2 : 1;
    }
    
    private PathEntity createEntityPath(final PathPoint p_75853_1_, final PathPoint p_75853_2_) {
        int var3 = 1;
        for (PathPoint var4 = p_75853_2_; var4.previous != null; var4 = var4.previous) {
            ++var3;
        }
        final PathPoint[] var5 = new PathPoint[var3];
        PathPoint var4 = p_75853_2_;
        --var3;
        var5[var3] = p_75853_2_;
        while (var4.previous != null) {
            var4 = var4.previous;
            --var3;
            var5[var3] = var4;
        }
        return new PathEntity(var5);
    }
}
