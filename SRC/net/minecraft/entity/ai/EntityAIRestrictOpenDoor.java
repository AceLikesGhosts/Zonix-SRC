package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;

public class EntityAIRestrictOpenDoor extends EntityAIBase
{
    private EntityCreature entityObj;
    private VillageDoorInfo frontDoor;
    private static final String __OBFID = "CL_00001610";
    
    public EntityAIRestrictOpenDoor(final EntityCreature p_i1651_1_) {
        this.entityObj = p_i1651_1_;
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.entityObj.worldObj.isDaytime()) {
            return false;
        }
        final Village var1 = this.entityObj.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(this.entityObj.posX), MathHelper.floor_double(this.entityObj.posY), MathHelper.floor_double(this.entityObj.posZ), 16);
        if (var1 == null) {
            return false;
        }
        this.frontDoor = var1.findNearestDoor(MathHelper.floor_double(this.entityObj.posX), MathHelper.floor_double(this.entityObj.posY), MathHelper.floor_double(this.entityObj.posZ));
        return this.frontDoor != null && this.frontDoor.getInsideDistanceSquare(MathHelper.floor_double(this.entityObj.posX), MathHelper.floor_double(this.entityObj.posY), MathHelper.floor_double(this.entityObj.posZ)) < 2.25;
    }
    
    @Override
    public boolean continueExecuting() {
        return !this.entityObj.worldObj.isDaytime() && (!this.frontDoor.isDetachedFromVillageFlag && this.frontDoor.isInside(MathHelper.floor_double(this.entityObj.posX), MathHelper.floor_double(this.entityObj.posZ)));
    }
    
    @Override
    public void startExecuting() {
        this.entityObj.getNavigator().setBreakDoors(false);
        this.entityObj.getNavigator().setEnterDoors(false);
    }
    
    @Override
    public void resetTask() {
        this.entityObj.getNavigator().setBreakDoors(true);
        this.entityObj.getNavigator().setEnterDoors(true);
        this.frontDoor = null;
    }
    
    @Override
    public void updateTask() {
        this.frontDoor.incrementDoorOpeningRestrictionCounter();
    }
}
