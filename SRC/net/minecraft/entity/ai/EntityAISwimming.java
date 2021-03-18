package net.minecraft.entity.ai;

import net.minecraft.entity.*;

public class EntityAISwimming extends EntityAIBase
{
    private EntityLiving theEntity;
    private static final String __OBFID = "CL_00001584";
    
    public EntityAISwimming(final EntityLiving p_i1624_1_) {
        this.theEntity = p_i1624_1_;
        this.setMutexBits(4);
        p_i1624_1_.getNavigator().setCanSwim(true);
    }
    
    @Override
    public boolean shouldExecute() {
        return this.theEntity.isInWater() || this.theEntity.handleLavaMovement();
    }
    
    @Override
    public void updateTask() {
        if (this.theEntity.getRNG().nextFloat() < 0.8f) {
            this.theEntity.getJumpHelper().setJumping();
        }
    }
}
