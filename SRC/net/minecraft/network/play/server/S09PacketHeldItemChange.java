package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S09PacketHeldItemChange extends Packet
{
    private int field_149387_a;
    private static final String __OBFID = "CL_00001324";
    
    public S09PacketHeldItemChange() {
    }
    
    public S09PacketHeldItemChange(final int p_i45215_1_) {
        this.field_149387_a = p_i45215_1_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149387_a = p_148837_1_.readByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeByte(this.field_149387_a);
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleHeldItemChange(this);
    }
    
    public int func_149385_c() {
        return this.field_149387_a;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
