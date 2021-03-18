package net.minecraft.entity.monster;

import net.minecraft.world.*;
import net.minecraft.entity.*;

public class EntityGiantZombie extends EntityMob
{
    private static final String __OBFID = "CL_00001690";
    
    public EntityGiantZombie(final World p_i1736_1_) {
        super(p_i1736_1_);
        this.yOffset *= 6.0f;
        this.setSize(this.width * 6.0f, this.height * 6.0f);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(50.0);
    }
    
    @Override
    public float getBlockPathWeight(final int p_70783_1_, final int p_70783_2_, final int p_70783_3_) {
        return this.worldObj.getLightBrightness(p_70783_1_, p_70783_2_, p_70783_3_) - 0.5f;
    }
}
