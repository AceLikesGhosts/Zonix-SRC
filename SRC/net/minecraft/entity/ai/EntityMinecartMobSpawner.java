package net.minecraft.entity.ai;

import net.minecraft.entity.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;

public class EntityMinecartMobSpawner extends EntityMinecart
{
    private final MobSpawnerBaseLogic mobSpawnerLogic;
    private static final String __OBFID = "CL_00001678";
    
    public EntityMinecartMobSpawner(final World p_i1725_1_) {
        super(p_i1725_1_);
        this.mobSpawnerLogic = new MobSpawnerBaseLogic() {
            private static final String __OBFID = "CL_00001679";
            
            @Override
            public void func_98267_a(final int p_98267_1_) {
                EntityMinecartMobSpawner.this.worldObj.setEntityState(EntityMinecartMobSpawner.this, (byte)p_98267_1_);
            }
            
            @Override
            public World getSpawnerWorld() {
                return EntityMinecartMobSpawner.this.worldObj;
            }
            
            @Override
            public int getSpawnerX() {
                return MathHelper.floor_double(EntityMinecartMobSpawner.this.posX);
            }
            
            @Override
            public int getSpawnerY() {
                return MathHelper.floor_double(EntityMinecartMobSpawner.this.posY);
            }
            
            @Override
            public int getSpawnerZ() {
                return MathHelper.floor_double(EntityMinecartMobSpawner.this.posZ);
            }
        };
    }
    
    public EntityMinecartMobSpawner(final World p_i1726_1_, final double p_i1726_2_, final double p_i1726_4_, final double p_i1726_6_) {
        super(p_i1726_1_, p_i1726_2_, p_i1726_4_, p_i1726_6_);
        this.mobSpawnerLogic = new MobSpawnerBaseLogic() {
            private static final String __OBFID = "CL_00001679";
            
            @Override
            public void func_98267_a(final int p_98267_1_) {
                EntityMinecartMobSpawner.this.worldObj.setEntityState(EntityMinecartMobSpawner.this, (byte)p_98267_1_);
            }
            
            @Override
            public World getSpawnerWorld() {
                return EntityMinecartMobSpawner.this.worldObj;
            }
            
            @Override
            public int getSpawnerX() {
                return MathHelper.floor_double(EntityMinecartMobSpawner.this.posX);
            }
            
            @Override
            public int getSpawnerY() {
                return MathHelper.floor_double(EntityMinecartMobSpawner.this.posY);
            }
            
            @Override
            public int getSpawnerZ() {
                return MathHelper.floor_double(EntityMinecartMobSpawner.this.posZ);
            }
        };
    }
    
    @Override
    public int getMinecartType() {
        return 4;
    }
    
    @Override
    public Block func_145817_o() {
        return Blocks.mob_spawner;
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
        this.mobSpawnerLogic.readFromNBT(p_70037_1_);
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
        this.mobSpawnerLogic.writeToNBT(p_70014_1_);
    }
    
    @Override
    public void handleHealthUpdate(final byte p_70103_1_) {
        this.mobSpawnerLogic.setDelayToMin(p_70103_1_);
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        this.mobSpawnerLogic.updateSpawner();
    }
    
    public MobSpawnerBaseLogic func_98039_d() {
        return this.mobSpawnerLogic;
    }
}
