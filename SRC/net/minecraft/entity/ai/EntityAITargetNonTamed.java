package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class EntityAITargetNonTamed extends EntityAINearestAttackableTarget
{
    private EntityTameable theTameable;
    private static final String __OBFID = "CL_00001623";
    
    public EntityAITargetNonTamed(final EntityTameable p_i1666_1_, final Class p_i1666_2_, final int p_i1666_3_, final boolean p_i1666_4_) {
        super(p_i1666_1_, p_i1666_2_, p_i1666_3_, p_i1666_4_);
        this.theTameable = p_i1666_1_;
    }
    
    @Override
    public boolean shouldExecute() {
        return !this.theTameable.isTamed() && super.shouldExecute();
    }
}
