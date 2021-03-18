package net.minecraft.network.status.server;

import java.io.*;
import net.minecraft.network.status.*;
import net.minecraft.network.*;

public class S01PacketPong extends Packet
{
    private long field_149293_a;
    private static final String __OBFID = "CL_00001383";
    
    public S01PacketPong() {
    }
    
    public S01PacketPong(final long p_i45272_1_) {
        this.field_149293_a = p_i45272_1_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149293_a = p_148837_1_.readLong();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeLong(this.field_149293_a);
    }
    
    public void processPacket(final INetHandlerStatusClient p_148833_1_) {
        p_148833_1_.handlePong(this);
    }
    
    @Override
    public boolean hasPriority() {
        return true;
    }
    
    public long func_149292_c() {
        return this.field_149293_a;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerStatusClient)p_148833_1_);
    }
}
