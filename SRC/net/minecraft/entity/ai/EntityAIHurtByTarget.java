package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;

public class EntityAIHurtByTarget extends EntityAITarget
{
    boolean entityCallsForHelp;
    private int field_142052_b;
    private static final String __OBFID = "CL_00001619";
    
    public EntityAIHurtByTarget(final EntityCreature p_i1660_1_, final boolean p_i1660_2_) {
        super(p_i1660_1_, false);
        this.entityCallsForHelp = p_i1660_2_;
        this.setMutexBits(1);
    }
    
    @Override
    public boolean shouldExecute() {
        final int var1 = this.taskOwner.func_142015_aE();
        return var1 != this.field_142052_b && this.isSuitableTarget(this.taskOwner.getAITarget(), false);
    }
    
    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.taskOwner.getAITarget());
        this.field_142052_b = this.taskOwner.func_142015_aE();
        if (this.entityCallsForHelp) {
            final double var1 = this.getTargetDistance();
            final List var2 = this.taskOwner.worldObj.getEntitiesWithinAABB(this.taskOwner.getClass(), AxisAlignedBB.getBoundingBox(this.taskOwner.posX, this.taskOwner.posY, this.taskOwner.posZ, this.taskOwner.posX + 1.0, this.taskOwner.posY + 1.0, this.taskOwner.posZ + 1.0).expand(var1, 10.0, var1));
            for (final EntityCreature var4 : var2) {
                if (this.taskOwner != var4 && var4.getAttackTarget() == null && !var4.isOnSameTeam(this.taskOwner.getAITarget())) {
                    var4.setAttackTarget(this.taskOwner.getAITarget());
                }
            }
        }
        super.startExecuting();
    }
}
