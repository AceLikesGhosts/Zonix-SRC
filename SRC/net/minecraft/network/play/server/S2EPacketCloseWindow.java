package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S2EPacketCloseWindow extends Packet
{
    private int field_148896_a;
    private static final String __OBFID = "CL_00001292";
    
    public S2EPacketCloseWindow() {
    }
    
    public S2EPacketCloseWindow(final int p_i45183_1_) {
        this.field_148896_a = p_i45183_1_;
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleCloseWindow(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_148896_a = p_148837_1_.readUnsignedByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeByte(this.field_148896_a);
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
