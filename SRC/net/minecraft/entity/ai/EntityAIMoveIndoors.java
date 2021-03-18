package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.village.*;
import net.minecraft.util.*;

public class EntityAIMoveIndoors extends EntityAIBase
{
    private EntityCreature entityObj;
    private VillageDoorInfo doorInfo;
    private int insidePosX;
    private int insidePosZ;
    private static final String __OBFID = "CL_00001596";
    
    public EntityAIMoveIndoors(final EntityCreature p_i1637_1_) {
        this.insidePosX = -1;
        this.insidePosZ = -1;
        this.entityObj = p_i1637_1_;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        final int var1 = MathHelper.floor_double(this.entityObj.posX);
        final int var2 = MathHelper.floor_double(this.entityObj.posY);
        final int var3 = MathHelper.floor_double(this.entityObj.posZ);
        if ((this.entityObj.worldObj.isDaytime() && !this.entityObj.worldObj.isRaining() && this.entityObj.worldObj.getBiomeGenForCoords(var1, var3).canSpawnLightningBolt()) || this.entityObj.worldObj.provider.hasNoSky) {
            return false;
        }
        if (this.entityObj.getRNG().nextInt(50) != 0) {
            return false;
        }
        if (this.insidePosX != -1 && this.entityObj.getDistanceSq(this.insidePosX, this.entityObj.posY, this.insidePosZ) < 4.0) {
            return false;
        }
        final Village var4 = this.entityObj.worldObj.villageCollectionObj.findNearestVillage(var1, var2, var3, 14);
        if (var4 == null) {
            return false;
        }
        this.doorInfo = var4.findNearestDoorUnrestricted(var1, var2, var3);
        return this.doorInfo != null;
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.entityObj.getNavigator().noPath();
    }
    
    @Override
    public void startExecuting() {
        this.insidePosX = -1;
        if (this.entityObj.getDistanceSq(this.doorInfo.getInsidePosX(), this.doorInfo.posY, this.doorInfo.getInsidePosZ()) > 256.0) {
            final Vec3 var1 = RandomPositionGenerator.findRandomTargetBlockTowards(this.entityObj, 14, 3, Vec3.createVectorHelper(this.doorInfo.getInsidePosX() + 0.5, this.doorInfo.getInsidePosY(), this.doorInfo.getInsidePosZ() + 0.5));
            if (var1 != null) {
                this.entityObj.getNavigator().tryMoveToXYZ(var1.xCoord, var1.yCoord, var1.zCoord, 1.0);
            }
        }
        else {
            this.entityObj.getNavigator().tryMoveToXYZ(this.doorInfo.getInsidePosX() + 0.5, this.doorInfo.getInsidePosY(), this.doorInfo.getInsidePosZ() + 0.5, 1.0);
        }
    }
    
    @Override
    public void resetTask() {
        this.insidePosX = this.doorInfo.getInsidePosX();
        this.insidePosZ = this.doorInfo.getInsidePosZ();
        this.doorInfo = null;
    }
}
