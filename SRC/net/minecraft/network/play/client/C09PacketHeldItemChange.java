package net.minecraft.network.play.client;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class C09PacketHeldItemChange extends Packet
{
    private int field_149615_a;
    private static final String __OBFID = "CL_00001368";
    
    public C09PacketHeldItemChange() {
    }
    
    public C09PacketHeldItemChange(final int p_i45262_1_) {
        this.field_149615_a = p_i45262_1_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149615_a = p_148837_1_.readShort();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeShort(this.field_149615_a);
    }
    
    public void processPacket(final INetHandlerPlayServer p_148833_1_) {
        p_148833_1_.processHeldItemChange(this);
    }
    
    public int func_149614_c() {
        return this.field_149615_a;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayServer)p_148833_1_);
    }
}
