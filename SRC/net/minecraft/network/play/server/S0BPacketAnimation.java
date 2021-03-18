package net.minecraft.network.play.server;

import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S0BPacketAnimation extends Packet
{
    private int field_148981_a;
    private int field_148980_b;
    private static final String __OBFID = "CL_00001282";
    
    public S0BPacketAnimation() {
    }
    
    public S0BPacketAnimation(final Entity p_i45172_1_, final int p_i45172_2_) {
        this.field_148981_a = p_i45172_1_.getEntityId();
        this.field_148980_b = p_i45172_2_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_148981_a = p_148837_1_.readVarIntFromBuffer();
        this.field_148980_b = p_148837_1_.readUnsignedByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeVarIntToBuffer(this.field_148981_a);
        p_148840_1_.writeByte(this.field_148980_b);
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleAnimation(this);
    }
    
    @Override
    public String serialize() {
        return String.format("id=%d, type=%d", this.field_148981_a, this.field_148980_b);
    }
    
    public int func_148978_c() {
        return this.field_148981_a;
    }
    
    public int func_148977_d() {
        return this.field_148980_b;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
