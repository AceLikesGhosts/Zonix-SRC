package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.block.*;

public class EntityAIBreakDoor extends EntityAIDoorInteract
{
    private int breakingTime;
    private int field_75358_j;
    private static final String __OBFID = "CL_00001577";
    
    public EntityAIBreakDoor(final EntityLiving p_i1618_1_) {
        super(p_i1618_1_);
        this.field_75358_j = -1;
    }
    
    @Override
    public boolean shouldExecute() {
        return super.shouldExecute() && this.theEntity.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing") && !this.field_151504_e.func_150015_f(this.theEntity.worldObj, this.entityPosX, this.entityPosY, this.entityPosZ);
    }
    
    @Override
    public void startExecuting() {
        super.startExecuting();
        this.breakingTime = 0;
    }
    
    @Override
    public boolean continueExecuting() {
        final double var1 = this.theEntity.getDistanceSq(this.entityPosX, this.entityPosY, this.entityPosZ);
        return this.breakingTime <= 240 && !this.field_151504_e.func_150015_f(this.theEntity.worldObj, this.entityPosX, this.entityPosY, this.entityPosZ) && var1 < 4.0;
    }
    
    @Override
    public void resetTask() {
        super.resetTask();
        this.theEntity.worldObj.destroyBlockInWorldPartially(this.theEntity.getEntityId(), this.entityPosX, this.entityPosY, this.entityPosZ, -1);
    }
    
    @Override
    public void updateTask() {
        super.updateTask();
        if (this.theEntity.getRNG().nextInt(20) == 0) {
            this.theEntity.worldObj.playAuxSFX(1010, this.entityPosX, this.entityPosY, this.entityPosZ, 0);
        }
        ++this.breakingTime;
        final int var1 = (int)(this.breakingTime / 240.0f * 10.0f);
        if (var1 != this.field_75358_j) {
            this.theEntity.worldObj.destroyBlockInWorldPartially(this.theEntity.getEntityId(), this.entityPosX, this.entityPosY, this.entityPosZ, var1);
            this.field_75358_j = var1;
        }
        if (this.breakingTime == 240 && this.theEntity.worldObj.difficultySetting == EnumDifficulty.HARD) {
            this.theEntity.worldObj.setBlockToAir(this.entityPosX, this.entityPosY, this.entityPosZ);
            this.theEntity.worldObj.playAuxSFX(1012, this.entityPosX, this.entityPosY, this.entityPosZ, 0);
            this.theEntity.worldObj.playAuxSFX(2001, this.entityPosX, this.entityPosY, this.entityPosZ, Block.getIdFromBlock(this.field_151504_e));
        }
    }
}
