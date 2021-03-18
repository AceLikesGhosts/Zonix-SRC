package net.minecraft.entity.ai;

import net.minecraft.command.*;
import net.minecraft.entity.*;
import java.util.*;

public class EntityAINearestAttackableTarget extends EntityAITarget
{
    private final Class targetClass;
    private final int targetChance;
    private final Sorter theNearestAttackableTargetSorter;
    private final IEntitySelector targetEntitySelector;
    private EntityLivingBase targetEntity;
    private static final String __OBFID = "CL_00001620";
    
    public EntityAINearestAttackableTarget(final EntityCreature p_i1663_1_, final Class p_i1663_2_, final int p_i1663_3_, final boolean p_i1663_4_) {
        this(p_i1663_1_, p_i1663_2_, p_i1663_3_, p_i1663_4_, false);
    }
    
    public EntityAINearestAttackableTarget(final EntityCreature p_i1664_1_, final Class p_i1664_2_, final int p_i1664_3_, final boolean p_i1664_4_, final boolean p_i1664_5_) {
        this(p_i1664_1_, p_i1664_2_, p_i1664_3_, p_i1664_4_, p_i1664_5_, null);
    }
    
    public EntityAINearestAttackableTarget(final EntityCreature p_i1665_1_, final Class p_i1665_2_, final int p_i1665_3_, final boolean p_i1665_4_, final boolean p_i1665_5_, final IEntitySelector p_i1665_6_) {
        super(p_i1665_1_, p_i1665_4_, p_i1665_5_);
        this.targetClass = p_i1665_2_;
        this.targetChance = p_i1665_3_;
        this.theNearestAttackableTargetSorter = new Sorter(p_i1665_1_);
        this.setMutexBits(1);
        this.targetEntitySelector = new IEntitySelector() {
            private static final String __OBFID = "CL_00001621";
            
            @Override
            public boolean isEntityApplicable(final Entity p_82704_1_) {
                return p_82704_1_ instanceof EntityLivingBase && (p_i1665_6_ == null || p_i1665_6_.isEntityApplicable(p_82704_1_)) && EntityAINearestAttackableTarget.this.isSuitableTarget((EntityLivingBase)p_82704_1_, false);
            }
        };
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0) {
            return false;
        }
        final double var1 = this.getTargetDistance();
        final List var2 = this.taskOwner.worldObj.selectEntitiesWithinAABB(this.targetClass, this.taskOwner.boundingBox.expand(var1, 4.0, var1), this.targetEntitySelector);
        Collections.sort((List<Object>)var2, this.theNearestAttackableTargetSorter);
        if (var2.isEmpty()) {
            return false;
        }
        this.targetEntity = var2.get(0);
        return true;
    }
    
    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.targetEntity);
        super.startExecuting();
    }
    
    public static class Sorter implements Comparator
    {
        private final Entity theEntity;
        private static final String __OBFID = "CL_00001622";
        
        public Sorter(final Entity p_i1662_1_) {
            this.theEntity = p_i1662_1_;
        }
        
        public int compare(final Entity p_compare_1_, final Entity p_compare_2_) {
            final double var3 = this.theEntity.getDistanceSqToEntity(p_compare_1_);
            final double var4 = this.theEntity.getDistanceSqToEntity(p_compare_2_);
            return (var3 < var4) ? -1 : ((var3 > var4) ? 1 : 0);
        }
        
        @Override
        public int compare(final Object p_compare_1_, final Object p_compare_2_) {
            return this.compare((Entity)p_compare_1_, (Entity)p_compare_2_);
        }
    }
}
