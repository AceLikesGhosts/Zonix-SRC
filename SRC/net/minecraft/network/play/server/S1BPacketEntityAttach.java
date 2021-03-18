package net.minecraft.network.play.server;

import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S1BPacketEntityAttach extends Packet
{
    private int field_149408_a;
    private int field_149406_b;
    private int field_149407_c;
    private static final String __OBFID = "CL_00001327";
    
    public S1BPacketEntityAttach() {
    }
    
    public S1BPacketEntityAttach(final int p_i45218_1_, final Entity p_i45218_2_, final Entity p_i45218_3_) {
        this.field_149408_a = p_i45218_1_;
        this.field_149406_b = p_i45218_2_.getEntityId();
        this.field_149407_c = ((p_i45218_3_ != null) ? p_i45218_3_.getEntityId() : -1);
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149406_b = p_148837_1_.readInt();
        this.field_149407_c = p_148837_1_.readInt();
        this.field_149408_a = p_148837_1_.readUnsignedByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_149406_b);
        p_148840_1_.writeInt(this.field_149407_c);
        p_148840_1_.writeByte(this.field_149408_a);
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleEntityAttach(this);
    }
    
    public int func_149404_c() {
        return this.field_149408_a;
    }
    
    public int func_149403_d() {
        return this.field_149406_b;
    }
    
    public int func_149402_e() {
        return this.field_149407_c;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
