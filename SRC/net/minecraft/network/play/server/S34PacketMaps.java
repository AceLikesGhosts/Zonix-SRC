package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S34PacketMaps extends Packet
{
    private int field_149191_a;
    private byte[] field_149190_b;
    private static final String __OBFID = "CL_00001311";
    
    public S34PacketMaps() {
    }
    
    public S34PacketMaps(final int p_i45202_1_, final byte[] p_i45202_2_) {
        this.field_149191_a = p_i45202_1_;
        this.field_149190_b = p_i45202_2_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149191_a = p_148837_1_.readVarIntFromBuffer();
        p_148837_1_.readBytes(this.field_149190_b = new byte[p_148837_1_.readUnsignedShort()]);
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeVarIntToBuffer(this.field_149191_a);
        p_148840_1_.writeShort(this.field_149190_b.length);
        p_148840_1_.writeBytes(this.field_149190_b);
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleMaps(this);
    }
    
    @Override
    public String serialize() {
        return String.format("id=%d, length=%d", this.field_149191_a, this.field_149190_b.length);
    }
    
    public int func_149188_c() {
        return this.field_149191_a;
    }
    
    public byte[] func_149187_d() {
        return this.field_149190_b;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
