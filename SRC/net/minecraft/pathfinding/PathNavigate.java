package net.minecraft.pathfinding;

import net.minecraft.entity.ai.attributes.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;

public class PathNavigate
{
    private EntityLiving theEntity;
    private World worldObj;
    private PathEntity currentPath;
    private double speed;
    private IAttributeInstance pathSearchRange;
    private boolean noSunPathfind;
    private int totalTicks;
    private int ticksAtLastPos;
    private Vec3 lastPosCheck;
    private boolean canPassOpenWoodenDoors;
    private boolean canPassClosedWoodenDoors;
    private boolean avoidsWater;
    private boolean canSwim;
    private static final String __OBFID = "CL_00001627";
    
    public PathNavigate(final EntityLiving p_i1671_1_, final World p_i1671_2_) {
        this.lastPosCheck = Vec3.createVectorHelper(0.0, 0.0, 0.0);
        this.canPassOpenWoodenDoors = true;
        this.theEntity = p_i1671_1_;
        this.worldObj = p_i1671_2_;
        this.pathSearchRange = p_i1671_1_.getEntityAttribute(SharedMonsterAttributes.followRange);
    }
    
    public void setAvoidsWater(final boolean p_75491_1_) {
        this.avoidsWater = p_75491_1_;
    }
    
    public boolean getAvoidsWater() {
        return this.avoidsWater;
    }
    
    public void setBreakDoors(final boolean p_75498_1_) {
        this.canPassClosedWoodenDoors = p_75498_1_;
    }
    
    public void setEnterDoors(final boolean p_75490_1_) {
        this.canPassOpenWoodenDoors = p_75490_1_;
    }
    
    public boolean getCanBreakDoors() {
        return this.canPassClosedWoodenDoors;
    }
    
    public void setAvoidSun(final boolean p_75504_1_) {
        this.noSunPathfind = p_75504_1_;
    }
    
    public void setSpeed(final double p_75489_1_) {
        this.speed = p_75489_1_;
    }
    
    public void setCanSwim(final boolean p_75495_1_) {
        this.canSwim = p_75495_1_;
    }
    
    public float func_111269_d() {
        return (float)this.pathSearchRange.getAttributeValue();
    }
    
    public PathEntity getPathToXYZ(final double p_75488_1_, final double p_75488_3_, final double p_75488_5_) {
        return this.canNavigate() ? this.worldObj.getEntityPathToXYZ(this.theEntity, MathHelper.floor_double(p_75488_1_), (int)p_75488_3_, MathHelper.floor_double(p_75488_5_), this.func_111269_d(), this.canPassOpenWoodenDoors, this.canPassClosedWoodenDoors, this.avoidsWater, this.canSwim) : null;
    }
    
    public boolean tryMoveToXYZ(final double p_75492_1_, final double p_75492_3_, final double p_75492_5_, final double p_75492_7_) {
        final PathEntity var9 = this.getPathToXYZ(MathHelper.floor_double(p_75492_1_), (int)p_75492_3_, MathHelper.floor_double(p_75492_5_));
        return this.setPath(var9, p_75492_7_);
    }
    
    public PathEntity getPathToEntityLiving(final Entity p_75494_1_) {
        return this.canNavigate() ? this.worldObj.getPathEntityToEntity(this.theEntity, p_75494_1_, this.func_111269_d(), this.canPassOpenWoodenDoors, this.canPassClosedWoodenDoors, this.avoidsWater, this.canSwim) : null;
    }
    
    public boolean tryMoveToEntityLiving(final Entity p_75497_1_, final double p_75497_2_) {
        final PathEntity var4 = this.getPathToEntityLiving(p_75497_1_);
        return var4 != null && this.setPath(var4, p_75497_2_);
    }
    
    public boolean setPath(final PathEntity p_75484_1_, final double p_75484_2_) {
        if (p_75484_1_ == null) {
            this.currentPath = null;
            return false;
        }
        if (!p_75484_1_.isSamePath(this.currentPath)) {
            this.currentPath = p_75484_1_;
        }
        if (this.noSunPathfind) {
            this.removeSunnyPath();
        }
        if (this.currentPath.getCurrentPathLength() == 0) {
            return false;
        }
        this.speed = p_75484_2_;
        final Vec3 var4 = this.getEntityPosition();
        this.ticksAtLastPos = this.totalTicks;
        this.lastPosCheck.xCoord = var4.xCoord;
        this.lastPosCheck.yCoord = var4.yCoord;
        this.lastPosCheck.zCoord = var4.zCoord;
        return true;
    }
    
    public PathEntity getPath() {
        return this.currentPath;
    }
    
    public void onUpdateNavigation() {
        ++this.totalTicks;
        if (!this.noPath()) {
            if (this.canNavigate()) {
                this.pathFollow();
            }
            if (!this.noPath()) {
                final Vec3 var1 = this.currentPath.getPosition(this.theEntity);
                if (var1 != null) {
                    this.theEntity.getMoveHelper().setMoveTo(var1.xCoord, var1.yCoord, var1.zCoord, this.speed);
                }
            }
        }
    }
    
    private void pathFollow() {
        final Vec3 var1 = this.getEntityPosition();
        int var2 = this.currentPath.getCurrentPathLength();
        for (int var3 = this.currentPath.getCurrentPathIndex(); var3 < this.currentPath.getCurrentPathLength(); ++var3) {
            if (this.currentPath.getPathPointFromIndex(var3).yCoord != (int)var1.yCoord) {
                var2 = var3;
                break;
            }
        }
        final float var4 = this.theEntity.width * this.theEntity.width;
        for (int var5 = this.currentPath.getCurrentPathIndex(); var5 < var2; ++var5) {
            if (var1.squareDistanceTo(this.currentPath.getVectorFromIndex(this.theEntity, var5)) < var4) {
                this.currentPath.setCurrentPathIndex(var5 + 1);
            }
        }
        int var5 = MathHelper.ceiling_float_int(this.theEntity.width);
        final int var6 = (int)this.theEntity.height + 1;
        final int var7 = var5;
        for (int var8 = var2 - 1; var8 >= this.currentPath.getCurrentPathIndex(); --var8) {
            if (this.isDirectPathBetweenPoints(var1, this.currentPath.getVectorFromIndex(this.theEntity, var8), var5, var6, var7)) {
                this.currentPath.setCurrentPathIndex(var8);
                break;
            }
        }
        if (this.totalTicks - this.ticksAtLastPos > 100) {
            if (var1.squareDistanceTo(this.lastPosCheck) < 2.25) {
                this.clearPathEntity();
            }
            this.ticksAtLastPos = this.totalTicks;
            this.lastPosCheck.xCoord = var1.xCoord;
            this.lastPosCheck.yCoord = var1.yCoord;
            this.lastPosCheck.zCoord = var1.zCoord;
        }
    }
    
    public boolean noPath() {
        return this.currentPath == null || this.currentPath.isFinished();
    }
    
    public void clearPathEntity() {
        this.currentPath = null;
    }
    
    private Vec3 getEntityPosition() {
        return Vec3.createVectorHelper(this.theEntity.posX, this.getPathableYPos(), this.theEntity.posZ);
    }
    
    private int getPathableYPos() {
        if (this.theEntity.isInWater() && this.canSwim) {
            int var1 = (int)this.theEntity.boundingBox.minY;
            Block var2 = this.worldObj.getBlock(MathHelper.floor_double(this.theEntity.posX), var1, MathHelper.floor_double(this.theEntity.posZ));
            int var3 = 0;
            while (var2 == Blocks.flowing_water || var2 == Blocks.water) {
                ++var1;
                var2 = this.worldObj.getBlock(MathHelper.floor_double(this.theEntity.posX), var1, MathHelper.floor_double(this.theEntity.posZ));
                if (++var3 > 16) {
                    return (int)this.theEntity.boundingBox.minY;
                }
            }
            return var1;
        }
        return (int)(this.theEntity.boundingBox.minY + 0.5);
    }
    
    private boolean canNavigate() {
        return this.theEntity.onGround || (this.canSwim && this.isInFluid()) || (this.theEntity.isRiding() && this.theEntity instanceof EntityZombie && this.theEntity.ridingEntity instanceof EntityChicken);
    }
    
    private boolean isInFluid() {
        return this.theEntity.isInWater() || this.theEntity.handleLavaMovement();
    }
    
    private void removeSunnyPath() {
        if (!this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.theEntity.posX), (int)(this.theEntity.boundingBox.minY + 0.5), MathHelper.floor_double(this.theEntity.posZ))) {
            for (int var1 = 0; var1 < this.currentPath.getCurrentPathLength(); ++var1) {
                final PathPoint var2 = this.currentPath.getPathPointFromIndex(var1);
                if (this.worldObj.canBlockSeeTheSky(var2.xCoord, var2.yCoord, var2.zCoord)) {
                    this.currentPath.setCurrentPathLength(var1 - 1);
                    return;
                }
            }
        }
    }
    
    private boolean isDirectPathBetweenPoints(final Vec3 p_75493_1_, final Vec3 p_75493_2_, int p_75493_3_, final int p_75493_4_, int p_75493_5_) {
        int var6 = MathHelper.floor_double(p_75493_1_.xCoord);
        int var7 = MathHelper.floor_double(p_75493_1_.zCoord);
        double var8 = p_75493_2_.xCoord - p_75493_1_.xCoord;
        double var9 = p_75493_2_.zCoord - p_75493_1_.zCoord;
        final double var10 = var8 * var8 + var9 * var9;
        if (var10 < 1.0E-8) {
            return false;
        }
        final double var11 = 1.0 / Math.sqrt(var10);
        var8 *= var11;
        var9 *= var11;
        p_75493_3_ += 2;
        p_75493_5_ += 2;
        if (!this.isSafeToStandAt(var6, (int)p_75493_1_.yCoord, var7, p_75493_3_, p_75493_4_, p_75493_5_, p_75493_1_, var8, var9)) {
            return false;
        }
        p_75493_3_ -= 2;
        p_75493_5_ -= 2;
        final double var12 = 1.0 / Math.abs(var8);
        final double var13 = 1.0 / Math.abs(var9);
        double var14 = var6 * 1 - p_75493_1_.xCoord;
        double var15 = var7 * 1 - p_75493_1_.zCoord;
        if (var8 >= 0.0) {
            ++var14;
        }
        if (var9 >= 0.0) {
            ++var15;
        }
        var14 /= var8;
        var15 /= var9;
        final int var16 = (var8 < 0.0) ? -1 : 1;
        final int var17 = (var9 < 0.0) ? -1 : 1;
        final int var18 = MathHelper.floor_double(p_75493_2_.xCoord);
        final int var19 = MathHelper.floor_double(p_75493_2_.zCoord);
        int var20 = var18 - var6;
        int var21 = var19 - var7;
        while (var20 * var16 > 0 || var21 * var17 > 0) {
            if (var14 < var15) {
                var14 += var12;
                var6 += var16;
                var20 = var18 - var6;
            }
            else {
                var15 += var13;
                var7 += var17;
                var21 = var19 - var7;
            }
            if (!this.isSafeToStandAt(var6, (int)p_75493_1_.yCoord, var7, p_75493_3_, p_75493_4_, p_75493_5_, p_75493_1_, var8, var9)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean isSafeToStandAt(final int p_75483_1_, final int p_75483_2_, final int p_75483_3_, final int p_75483_4_, final int p_75483_5_, final int p_75483_6_, final Vec3 p_75483_7_, final double p_75483_8_, final double p_75483_10_) {
        final int var12 = p_75483_1_ - p_75483_4_ / 2;
        final int var13 = p_75483_3_ - p_75483_6_ / 2;
        if (!this.isPositionClear(var12, p_75483_2_, var13, p_75483_4_, p_75483_5_, p_75483_6_, p_75483_7_, p_75483_8_, p_75483_10_)) {
            return false;
        }
        for (int var14 = var12; var14 < var12 + p_75483_4_; ++var14) {
            for (int var15 = var13; var15 < var13 + p_75483_6_; ++var15) {
                final double var16 = var14 + 0.5 - p_75483_7_.xCoord;
                final double var17 = var15 + 0.5 - p_75483_7_.zCoord;
                if (var16 * p_75483_8_ + var17 * p_75483_10_ >= 0.0) {
                    final Block var18 = this.worldObj.getBlock(var14, p_75483_2_ - 1, var15);
                    final Material var19 = var18.getMaterial();
                    if (var19 == Material.air) {
                        return false;
                    }
                    if (var19 == Material.water && !this.theEntity.isInWater()) {
                        return false;
                    }
                    if (var19 == Material.lava) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    private boolean isPositionClear(final int p_75496_1_, final int p_75496_2_, final int p_75496_3_, final int p_75496_4_, final int p_75496_5_, final int p_75496_6_, final Vec3 p_75496_7_, final double p_75496_8_, final double p_75496_10_) {
        for (int var12 = p_75496_1_; var12 < p_75496_1_ + p_75496_4_; ++var12) {
            for (int var13 = p_75496_2_; var13 < p_75496_2_ + p_75496_5_; ++var13) {
                for (int var14 = p_75496_3_; var14 < p_75496_3_ + p_75496_6_; ++var14) {
                    final double var15 = var12 + 0.5 - p_75496_7_.xCoord;
                    final double var16 = var14 + 0.5 - p_75496_7_.zCoord;
                    if (var15 * p_75496_8_ + var16 * p_75496_10_ >= 0.0) {
                        final Block var17 = this.worldObj.getBlock(var12, var13, var14);
                        if (!var17.getBlocksMovement(this.worldObj, var12, var13, var14)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
