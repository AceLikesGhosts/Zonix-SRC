package net.minecraft.entity;

import net.minecraft.entity.item.*;
import net.minecraft.command.server.*;
import net.minecraft.world.*;
import io.netty.buffer.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;

public class EntityMinecartCommandBlock extends EntityMinecart
{
    private final CommandBlockLogic field_145824_a;
    private int field_145823_b;
    private static final String __OBFID = "CL_00001672";
    
    public EntityMinecartCommandBlock(final World p_i45321_1_) {
        super(p_i45321_1_);
        this.field_145824_a = new CommandBlockLogic() {
            private static final String __OBFID = "CL_00001673";
            
            @Override
            public void func_145756_e() {
                EntityMinecartCommandBlock.this.getDataWatcher().updateObject(23, this.func_145753_i());
                EntityMinecartCommandBlock.this.getDataWatcher().updateObject(24, IChatComponent.Serializer.func_150696_a(this.func_145749_h()));
            }
            
            @Override
            public int func_145751_f() {
                return 1;
            }
            
            @Override
            public void func_145757_a(final ByteBuf p_145757_1_) {
                p_145757_1_.writeInt(EntityMinecartCommandBlock.this.getEntityId());
            }
            
            @Override
            public ChunkCoordinates getPlayerCoordinates() {
                return new ChunkCoordinates(MathHelper.floor_double(EntityMinecartCommandBlock.this.posX), MathHelper.floor_double(EntityMinecartCommandBlock.this.posY + 0.5), MathHelper.floor_double(EntityMinecartCommandBlock.this.posZ));
            }
            
            @Override
            public World getEntityWorld() {
                return EntityMinecartCommandBlock.this.worldObj;
            }
        };
        this.field_145823_b = 0;
    }
    
    public EntityMinecartCommandBlock(final World p_i45322_1_, final double p_i45322_2_, final double p_i45322_4_, final double p_i45322_6_) {
        super(p_i45322_1_, p_i45322_2_, p_i45322_4_, p_i45322_6_);
        this.field_145824_a = new CommandBlockLogic() {
            private static final String __OBFID = "CL_00001673";
            
            @Override
            public void func_145756_e() {
                EntityMinecartCommandBlock.this.getDataWatcher().updateObject(23, this.func_145753_i());
                EntityMinecartCommandBlock.this.getDataWatcher().updateObject(24, IChatComponent.Serializer.func_150696_a(this.func_145749_h()));
            }
            
            @Override
            public int func_145751_f() {
                return 1;
            }
            
            @Override
            public void func_145757_a(final ByteBuf p_145757_1_) {
                p_145757_1_.writeInt(EntityMinecartCommandBlock.this.getEntityId());
            }
            
            @Override
            public ChunkCoordinates getPlayerCoordinates() {
                return new ChunkCoordinates(MathHelper.floor_double(EntityMinecartCommandBlock.this.posX), MathHelper.floor_double(EntityMinecartCommandBlock.this.posY + 0.5), MathHelper.floor_double(EntityMinecartCommandBlock.this.posZ));
            }
            
            @Override
            public World getEntityWorld() {
                return EntityMinecartCommandBlock.this.worldObj;
            }
        };
        this.field_145823_b = 0;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(23, "");
        this.getDataWatcher().addObject(24, "");
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
        this.field_145824_a.func_145759_b(p_70037_1_);
        this.getDataWatcher().updateObject(23, this.func_145822_e().func_145753_i());
        this.getDataWatcher().updateObject(24, IChatComponent.Serializer.func_150696_a(this.func_145822_e().func_145749_h()));
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
        this.field_145824_a.func_145758_a(p_70014_1_);
    }
    
    @Override
    public int getMinecartType() {
        return 6;
    }
    
    @Override
    public Block func_145817_o() {
        return Blocks.command_block;
    }
    
    public CommandBlockLogic func_145822_e() {
        return this.field_145824_a;
    }
    
    @Override
    public void onActivatorRailPass(final int p_96095_1_, final int p_96095_2_, final int p_96095_3_, final boolean p_96095_4_) {
        if (p_96095_4_ && this.ticksExisted - this.field_145823_b >= 4) {
            this.func_145822_e().func_145755_a(this.worldObj);
            this.field_145823_b = this.ticksExisted;
        }
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer p_130002_1_) {
        if (this.worldObj.isClient) {
            p_130002_1_.func_146095_a(this.func_145822_e());
        }
        return super.interactFirst(p_130002_1_);
    }
    
    @Override
    public void func_145781_i(final int p_145781_1_) {
        super.func_145781_i(p_145781_1_);
        if (p_145781_1_ == 24) {
            try {
                this.field_145824_a.func_145750_b(IChatComponent.Serializer.func_150699_a(this.getDataWatcher().getWatchableObjectString(24)));
            }
            catch (Throwable t) {}
        }
        else if (p_145781_1_ == 23) {
            this.field_145824_a.func_145752_a(this.getDataWatcher().getWatchableObjectString(23));
        }
    }
}
