package net.minecraft.network.status.client;

import java.io.*;
import net.minecraft.network.status.*;
import net.minecraft.network.*;

public class C00PacketServerQuery extends Packet
{
    private static final String __OBFID = "CL_00001393";
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
    }
    
    public void processPacket(final INetHandlerStatusServer p_148833_1_) {
        p_148833_1_.processServerQuery(this);
    }
    
    @Override
    public boolean hasPriority() {
        return true;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerStatusServer)p_148833_1_);
    }
}
