package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S00PacketKeepAlive extends Packet
{
    private int field_149136_a;
    private static final String __OBFID = "CL_00001303";
    
    public S00PacketKeepAlive() {
    }
    
    public S00PacketKeepAlive(final int p_i45195_1_) {
        this.field_149136_a = p_i45195_1_;
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleKeepAlive(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149136_a = p_148837_1_.readInt();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_149136_a);
    }
    
    @Override
    public boolean hasPriority() {
        return true;
    }
    
    public int func_149134_c() {
        return this.field_149136_a;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
