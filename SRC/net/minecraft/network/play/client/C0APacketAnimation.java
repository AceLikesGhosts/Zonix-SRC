package net.minecraft.network.play.client;

import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class C0APacketAnimation extends Packet
{
    private int field_149424_a;
    private int field_149423_b;
    private static final String __OBFID = "CL_00001345";
    
    public C0APacketAnimation() {
    }
    
    public C0APacketAnimation(final Entity p_i45238_1_, final int p_i45238_2_) {
        this.field_149424_a = p_i45238_1_.getEntityId();
        this.field_149423_b = p_i45238_2_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149424_a = p_148837_1_.readInt();
        this.field_149423_b = p_148837_1_.readByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_149424_a);
        p_148840_1_.writeByte(this.field_149423_b);
    }
    
    public void processPacket(final INetHandlerPlayServer p_148833_1_) {
        p_148833_1_.processAnimation(this);
    }
    
    @Override
    public String serialize() {
        return String.format("id=%d, type=%d", this.field_149424_a, this.field_149423_b);
    }
    
    public int func_149421_d() {
        return this.field_149423_b;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayServer)p_148833_1_);
    }
}
