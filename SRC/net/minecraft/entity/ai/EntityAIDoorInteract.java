package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.pathfinding.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public abstract class EntityAIDoorInteract extends EntityAIBase
{
    protected EntityLiving theEntity;
    protected int entityPosX;
    protected int entityPosY;
    protected int entityPosZ;
    protected BlockDoor field_151504_e;
    boolean hasStoppedDoorInteraction;
    float entityPositionX;
    float entityPositionZ;
    private static final String __OBFID = "CL_00001581";
    
    public EntityAIDoorInteract(final EntityLiving p_i1621_1_) {
        this.theEntity = p_i1621_1_;
    }
    
    @Override
    public boolean shouldExecute() {
        if (!this.theEntity.isCollidedHorizontally) {
            return false;
        }
        final PathNavigate var1 = this.theEntity.getNavigator();
        final PathEntity var2 = var1.getPath();
        if (var2 != null && !var2.isFinished() && var1.getCanBreakDoors()) {
            for (int var3 = 0; var3 < Math.min(var2.getCurrentPathIndex() + 2, var2.getCurrentPathLength()); ++var3) {
                final PathPoint var4 = var2.getPathPointFromIndex(var3);
                this.entityPosX = var4.xCoord;
                this.entityPosY = var4.yCoord + 1;
                this.entityPosZ = var4.zCoord;
                if (this.theEntity.getDistanceSq(this.entityPosX, this.theEntity.posY, this.entityPosZ) <= 2.25) {
                    this.field_151504_e = this.func_151503_a(this.entityPosX, this.entityPosY, this.entityPosZ);
                    if (this.field_151504_e != null) {
                        return true;
                    }
                }
            }
            this.entityPosX = MathHelper.floor_double(this.theEntity.posX);
            this.entityPosY = MathHelper.floor_double(this.theEntity.posY + 1.0);
            this.entityPosZ = MathHelper.floor_double(this.theEntity.posZ);
            this.field_151504_e = this.func_151503_a(this.entityPosX, this.entityPosY, this.entityPosZ);
            return this.field_151504_e != null;
        }
        return false;
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.hasStoppedDoorInteraction;
    }
    
    @Override
    public void startExecuting() {
        this.hasStoppedDoorInteraction = false;
        this.entityPositionX = (float)(this.entityPosX + 0.5f - this.theEntity.posX);
        this.entityPositionZ = (float)(this.entityPosZ + 0.5f - this.theEntity.posZ);
    }
    
    @Override
    public void updateTask() {
        final float var1 = (float)(this.entityPosX + 0.5f - this.theEntity.posX);
        final float var2 = (float)(this.entityPosZ + 0.5f - this.theEntity.posZ);
        final float var3 = this.entityPositionX * var1 + this.entityPositionZ * var2;
        if (var3 < 0.0f) {
            this.hasStoppedDoorInteraction = true;
        }
    }
    
    private BlockDoor func_151503_a(final int p_151503_1_, final int p_151503_2_, final int p_151503_3_) {
        final Block var4 = this.theEntity.worldObj.getBlock(p_151503_1_, p_151503_2_, p_151503_3_);
        return (var4 != Blocks.wooden_door) ? null : ((BlockDoor)var4);
    }
}
