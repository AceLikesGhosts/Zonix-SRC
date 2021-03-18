package net.minecraft.entity.passive;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;

public abstract class EntityAmbientCreature extends EntityLiving implements IAnimals
{
    private static final String __OBFID = "CL_00001636";
    
    public EntityAmbientCreature(final World p_i1679_1_) {
        super(p_i1679_1_);
    }
    
    @Override
    public boolean allowLeashing() {
        return false;
    }
    
    @Override
    protected boolean interact(final EntityPlayer p_70085_1_) {
        return false;
    }
}
