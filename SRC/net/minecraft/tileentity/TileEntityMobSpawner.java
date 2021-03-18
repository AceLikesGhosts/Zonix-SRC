package net.minecraft.tileentity;

import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;

public class TileEntityMobSpawner extends TileEntity
{
    private final MobSpawnerBaseLogic field_145882_a;
    private static final String __OBFID = "CL_00000360";
    
    public TileEntityMobSpawner() {
        this.field_145882_a = new MobSpawnerBaseLogic() {
            private static final String __OBFID = "CL_00000361";
            
            @Override
            public void func_98267_a(final int p_98267_1_) {
                TileEntityMobSpawner.this.worldObj.func_147452_c(TileEntityMobSpawner.this.field_145851_c, TileEntityMobSpawner.this.field_145848_d, TileEntityMobSpawner.this.field_145849_e, Blocks.mob_spawner, p_98267_1_, 0);
            }
            
            @Override
            public World getSpawnerWorld() {
                return TileEntityMobSpawner.this.worldObj;
            }
            
            @Override
            public int getSpawnerX() {
                return TileEntityMobSpawner.this.field_145851_c;
            }
            
            @Override
            public int getSpawnerY() {
                return TileEntityMobSpawner.this.field_145848_d;
            }
            
            @Override
            public int getSpawnerZ() {
                return TileEntityMobSpawner.this.field_145849_e;
            }
            
            @Override
            public void setRandomMinecart(final WeightedRandomMinecart p_98277_1_) {
                super.setRandomMinecart(p_98277_1_);
                if (this.getSpawnerWorld() != null) {
                    this.getSpawnerWorld().func_147471_g(TileEntityMobSpawner.this.field_145851_c, TileEntityMobSpawner.this.field_145848_d, TileEntityMobSpawner.this.field_145849_e);
                }
            }
        };
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
        this.field_145882_a.readFromNBT(p_145839_1_);
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
        this.field_145882_a.writeToNBT(p_145841_1_);
    }
    
    @Override
    public void updateEntity() {
        this.field_145882_a.updateSpawner();
        super.updateEntity();
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        var1.removeTag("SpawnPotentials");
        return new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 1, var1);
    }
    
    @Override
    public boolean receiveClientEvent(final int p_145842_1_, final int p_145842_2_) {
        return this.field_145882_a.setDelayToMin(p_145842_1_) || super.receiveClientEvent(p_145842_1_, p_145842_2_);
    }
    
    public MobSpawnerBaseLogic func_145881_a() {
        return this.field_145882_a;
    }
}
