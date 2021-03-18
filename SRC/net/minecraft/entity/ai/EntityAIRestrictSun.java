package net.minecraft.entity.ai;

import net.minecraft.entity.*;

public class EntityAIRestrictSun extends EntityAIBase
{
    private EntityCreature theEntity;
    private static final String __OBFID = "CL_00001611";
    
    public EntityAIRestrictSun(final EntityCreature p_i1652_1_) {
        this.theEntity = p_i1652_1_;
    }
    
    @Override
    public boolean shouldExecute() {
        return this.theEntity.worldObj.isDaytime();
    }
    
    @Override
    public void startExecuting() {
        this.theEntity.getNavigator().setAvoidSun(true);
    }
    
    @Override
    public void resetTask() {
        this.theEntity.getNavigator().setAvoidSun(false);
    }
}
