package net.minecraft.tileentity;

import net.minecraft.command.server.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import io.netty.buffer.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;

public class TileEntityCommandBlock extends TileEntity
{
    private final CommandBlockLogic field_145994_a;
    private static final String __OBFID = "CL_00000347";
    
    public TileEntityCommandBlock() {
        this.field_145994_a = new CommandBlockLogic() {
            private static final String __OBFID = "CL_00000348";
            
            @Override
            public ChunkCoordinates getPlayerCoordinates() {
                return new ChunkCoordinates(TileEntityCommandBlock.this.field_145851_c, TileEntityCommandBlock.this.field_145848_d, TileEntityCommandBlock.this.field_145849_e);
            }
            
            @Override
            public World getEntityWorld() {
                return TileEntityCommandBlock.this.getWorldObj();
            }
            
            @Override
            public void func_145752_a(final String p_145752_1_) {
                super.func_145752_a(p_145752_1_);
                TileEntityCommandBlock.this.onInventoryChanged();
            }
            
            @Override
            public void func_145756_e() {
                TileEntityCommandBlock.this.getWorldObj().func_147471_g(TileEntityCommandBlock.this.field_145851_c, TileEntityCommandBlock.this.field_145848_d, TileEntityCommandBlock.this.field_145849_e);
            }
            
            @Override
            public int func_145751_f() {
                return 0;
            }
            
            @Override
            public void func_145757_a(final ByteBuf p_145757_1_) {
                p_145757_1_.writeInt(TileEntityCommandBlock.this.field_145851_c);
                p_145757_1_.writeInt(TileEntityCommandBlock.this.field_145848_d);
                p_145757_1_.writeInt(TileEntityCommandBlock.this.field_145849_e);
            }
        };
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
        this.field_145994_a.func_145758_a(p_145841_1_);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
        this.field_145994_a.func_145759_b(p_145839_1_);
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        return new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 2, var1);
    }
    
    public CommandBlockLogic func_145993_a() {
        return this.field_145994_a;
    }
}
