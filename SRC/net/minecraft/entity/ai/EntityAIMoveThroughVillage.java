package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.pathfinding.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import java.util.*;

public class EntityAIMoveThroughVillage extends EntityAIBase
{
    private EntityCreature theEntity;
    private double movementSpeed;
    private PathEntity entityPathNavigate;
    private VillageDoorInfo doorInfo;
    private boolean isNocturnal;
    private List doorList;
    private static final String __OBFID = "CL_00001597";
    
    public EntityAIMoveThroughVillage(final EntityCreature p_i1638_1_, final double p_i1638_2_, final boolean p_i1638_4_) {
        this.doorList = new ArrayList();
        this.theEntity = p_i1638_1_;
        this.movementSpeed = p_i1638_2_;
        this.isNocturnal = p_i1638_4_;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        this.func_75414_f();
        if (this.isNocturnal && this.theEntity.worldObj.isDaytime()) {
            return false;
        }
        final Village var1 = this.theEntity.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(this.theEntity.posX), MathHelper.floor_double(this.theEntity.posY), MathHelper.floor_double(this.theEntity.posZ), 0);
        if (var1 == null) {
            return false;
        }
        this.doorInfo = this.func_75412_a(var1);
        if (this.doorInfo == null) {
            return false;
        }
        final boolean var2 = this.theEntity.getNavigator().getCanBreakDoors();
        this.theEntity.getNavigator().setBreakDoors(false);
        this.entityPathNavigate = this.theEntity.getNavigator().getPathToXYZ(this.doorInfo.posX, this.doorInfo.posY, this.doorInfo.posZ);
        this.theEntity.getNavigator().setBreakDoors(var2);
        if (this.entityPathNavigate != null) {
            return true;
        }
        final Vec3 var3 = RandomPositionGenerator.findRandomTargetBlockTowards(this.theEntity, 10, 7, Vec3.createVectorHelper(this.doorInfo.posX, this.doorInfo.posY, this.doorInfo.posZ));
        if (var3 == null) {
            return false;
        }
        this.theEntity.getNavigator().setBreakDoors(false);
        this.entityPathNavigate = this.theEntity.getNavigator().getPathToXYZ(var3.xCoord, var3.yCoord, var3.zCoord);
        this.theEntity.getNavigator().setBreakDoors(var2);
        return this.entityPathNavigate != null;
    }
    
    @Override
    public boolean continueExecuting() {
        if (this.theEntity.getNavigator().noPath()) {
            return false;
        }
        final float var1 = this.theEntity.width + 4.0f;
        return this.theEntity.getDistanceSq(this.doorInfo.posX, this.doorInfo.posY, this.doorInfo.posZ) > var1 * var1;
    }
    
    @Override
    public void startExecuting() {
        this.theEntity.getNavigator().setPath(this.entityPathNavigate, this.movementSpeed);
    }
    
    @Override
    public void resetTask() {
        if (this.theEntity.getNavigator().noPath() || this.theEntity.getDistanceSq(this.doorInfo.posX, this.doorInfo.posY, this.doorInfo.posZ) < 16.0) {
            this.doorList.add(this.doorInfo);
        }
    }
    
    private VillageDoorInfo func_75412_a(final Village p_75412_1_) {
        VillageDoorInfo var2 = null;
        int var3 = Integer.MAX_VALUE;
        final List var4 = p_75412_1_.getVillageDoorInfoList();
        for (final VillageDoorInfo var6 : var4) {
            final int var7 = var6.getDistanceSquared(MathHelper.floor_double(this.theEntity.posX), MathHelper.floor_double(this.theEntity.posY), MathHelper.floor_double(this.theEntity.posZ));
            if (var7 < var3 && !this.func_75413_a(var6)) {
                var2 = var6;
                var3 = var7;
            }
        }
        return var2;
    }
    
    private boolean func_75413_a(final VillageDoorInfo p_75413_1_) {
        for (final VillageDoorInfo var3 : this.doorList) {
            if (p_75413_1_.posX == var3.posX && p_75413_1_.posY == var3.posY && p_75413_1_.posZ == var3.posZ) {
                return true;
            }
        }
        return false;
    }
    
    private void func_75414_f() {
        if (this.doorList.size() > 15) {
            this.doorList.remove(0);
        }
    }
}
