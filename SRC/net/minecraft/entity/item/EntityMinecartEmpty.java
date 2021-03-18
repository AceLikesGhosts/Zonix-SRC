package net.minecraft.entity.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

public class EntityMinecartEmpty extends EntityMinecart
{
    private static final String __OBFID = "CL_00001677";
    
    public EntityMinecartEmpty(final World p_i1722_1_) {
        super(p_i1722_1_);
    }
    
    public EntityMinecartEmpty(final World p_i1723_1_, final double p_i1723_2_, final double p_i1723_4_, final double p_i1723_6_) {
        super(p_i1723_1_, p_i1723_2_, p_i1723_4_, p_i1723_6_);
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer p_130002_1_) {
        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != p_130002_1_) {
            return true;
        }
        if (this.riddenByEntity != null && this.riddenByEntity != p_130002_1_) {
            return false;
        }
        if (!this.worldObj.isClient) {
            p_130002_1_.mountEntity(this);
        }
        return true;
    }
    
    @Override
    public int getMinecartType() {
        return 0;
    }
}
