package net.minecraft.entity;

import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import java.util.*;

public class EntityLeashKnot extends EntityHanging
{
    private static final String __OBFID = "CL_00001548";
    
    public EntityLeashKnot(final World p_i1592_1_) {
        super(p_i1592_1_);
    }
    
    public EntityLeashKnot(final World p_i1593_1_, final int p_i1593_2_, final int p_i1593_3_, final int p_i1593_4_) {
        super(p_i1593_1_, p_i1593_2_, p_i1593_3_, p_i1593_4_, 0);
        this.setPosition(p_i1593_2_ + 0.5, p_i1593_3_ + 0.5, p_i1593_4_ + 0.5);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
    }
    
    @Override
    public void setDirection(final int p_82328_1_) {
    }
    
    @Override
    public int getWidthPixels() {
        return 9;
    }
    
    @Override
    public int getHeightPixels() {
        return 9;
    }
    
    @Override
    public boolean isInRangeToRenderDist(final double p_70112_1_) {
        return p_70112_1_ < 1024.0;
    }
    
    @Override
    public void onBroken(final Entity p_110128_1_) {
    }
    
    @Override
    public boolean writeToNBTOptional(final NBTTagCompound p_70039_1_) {
        return false;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer p_130002_1_) {
        final ItemStack var2 = p_130002_1_.getHeldItem();
        boolean var3 = false;
        if (var2 != null && var2.getItem() == Items.lead && !this.worldObj.isClient) {
            final double var4 = 7.0;
            final List var5 = this.worldObj.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(this.posX - var4, this.posY - var4, this.posZ - var4, this.posX + var4, this.posY + var4, this.posZ + var4));
            if (var5 != null) {
                for (final EntityLiving var7 : var5) {
                    if (var7.getLeashed() && var7.getLeashedToEntity() == p_130002_1_) {
                        var7.setLeashedToEntity(this, true);
                        var3 = true;
                    }
                }
            }
        }
        if (!this.worldObj.isClient && !var3) {
            this.setDead();
            if (p_130002_1_.capabilities.isCreativeMode) {
                final double var4 = 7.0;
                final List var5 = this.worldObj.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(this.posX - var4, this.posY - var4, this.posZ - var4, this.posX + var4, this.posY + var4, this.posZ + var4));
                if (var5 != null) {
                    for (final EntityLiving var7 : var5) {
                        if (var7.getLeashed() && var7.getLeashedToEntity() == this) {
                            var7.clearLeashed(true, false);
                        }
                    }
                }
            }
        }
        return true;
    }
    
    @Override
    public boolean onValidSurface() {
        return this.worldObj.getBlock(this.field_146063_b, this.field_146064_c, this.field_146062_d).getRenderType() == 11;
    }
    
    public static EntityLeashKnot func_110129_a(final World p_110129_0_, final int p_110129_1_, final int p_110129_2_, final int p_110129_3_) {
        final EntityLeashKnot var4 = new EntityLeashKnot(p_110129_0_, p_110129_1_, p_110129_2_, p_110129_3_);
        var4.forceSpawn = true;
        p_110129_0_.spawnEntityInWorld(var4);
        return var4;
    }
    
    public static EntityLeashKnot getKnotForBlock(final World p_110130_0_, final int p_110130_1_, final int p_110130_2_, final int p_110130_3_) {
        final List var4 = p_110130_0_.getEntitiesWithinAABB(EntityLeashKnot.class, AxisAlignedBB.getBoundingBox(p_110130_1_ - 1.0, p_110130_2_ - 1.0, p_110130_3_ - 1.0, p_110130_1_ + 1.0, p_110130_2_ + 1.0, p_110130_3_ + 1.0));
        if (var4 != null) {
            for (final EntityLeashKnot var6 : var4) {
                if (var6.field_146063_b == p_110130_1_ && var6.field_146064_c == p_110130_2_ && var6.field_146062_d == p_110130_3_) {
                    return var6;
                }
            }
        }
        return null;
    }
}
