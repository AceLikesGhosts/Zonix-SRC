package net.minecraft.network.play.server;

import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.world.*;
import net.minecraft.network.*;

public class S19PacketEntityStatus extends Packet
{
    private int field_149164_a;
    private byte field_149163_b;
    private static final String __OBFID = "CL_00001299";
    
    public S19PacketEntityStatus() {
    }
    
    public S19PacketEntityStatus(final Entity p_i46335_1_, final byte p_i46335_2_) {
        this.field_149164_a = p_i46335_1_.getEntityId();
        this.field_149163_b = p_i46335_2_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149164_a = p_148837_1_.readInt();
        this.field_149163_b = p_148837_1_.readByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_149164_a);
        p_148840_1_.writeByte(this.field_149163_b);
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleEntityStatus(this);
    }
    
    public Entity func_149161_a(final World p_149161_1_) {
        return p_149161_1_.getEntityByID(this.field_149164_a);
    }
    
    public byte func_149160_c() {
        return this.field_149163_b;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
