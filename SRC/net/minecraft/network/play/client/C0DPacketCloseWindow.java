package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class C0DPacketCloseWindow extends Packet
{
    private int field_149556_a;
    private static final String __OBFID = "CL_00001354";
    
    public C0DPacketCloseWindow() {
    }
    
    public C0DPacketCloseWindow(final int p_i45247_1_) {
        this.field_149556_a = p_i45247_1_;
    }
    
    public void processPacket(final INetHandlerPlayServer p_148833_1_) {
        p_148833_1_.processCloseWindow(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149556_a = p_148837_1_.readByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeByte(this.field_149556_a);
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayServer)p_148833_1_);
    }
}
