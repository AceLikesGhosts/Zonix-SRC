package net.minecraft.entity.boss;

import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class EntityDragonPart extends Entity
{
    public final IEntityMultiPart entityDragonObj;
    public final String field_146032_b;
    private static final String __OBFID = "CL_00001657";
    
    public EntityDragonPart(final IEntityMultiPart p_i1697_1_, final String p_i1697_2_, final float p_i1697_3_, final float p_i1697_4_) {
        super(p_i1697_1_.func_82194_d());
        this.setSize(p_i1697_3_, p_i1697_4_);
        this.entityDragonObj = p_i1697_1_;
        this.field_146032_b = p_i1697_2_;
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return true;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource p_70097_1_, final float p_70097_2_) {
        return !this.isEntityInvulnerable() && this.entityDragonObj.attackEntityFromPart(this, p_70097_1_, p_70097_2_);
    }
    
    @Override
    public boolean isEntityEqual(final Entity p_70028_1_) {
        return this == p_70028_1_ || this.entityDragonObj == p_70028_1_;
    }
}
