package net.minecraft.network.status.client;

import java.io.*;
import net.minecraft.network.status.*;
import net.minecraft.network.*;

public class C01PacketPing extends Packet
{
    private long field_149290_a;
    private static final String __OBFID = "CL_00001392";
    
    public C01PacketPing() {
    }
    
    public C01PacketPing(final long p_i45276_1_) {
        this.field_149290_a = p_i45276_1_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149290_a = p_148837_1_.readLong();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeLong(this.field_149290_a);
    }
    
    public void processPacket(final INetHandlerStatusServer p_148833_1_) {
        p_148833_1_.processPing(this);
    }
    
    @Override
    public boolean hasPriority() {
        return true;
    }
    
    public long func_149289_c() {
        return this.field_149290_a;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerStatusServer)p_148833_1_);
    }
}
