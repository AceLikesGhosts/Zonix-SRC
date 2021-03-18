package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S0DPacketCollectItem extends Packet
{
    private int field_149357_a;
    private int field_149356_b;
    private static final String __OBFID = "CL_00001339";
    
    public S0DPacketCollectItem() {
    }
    
    public S0DPacketCollectItem(final int p_i45232_1_, final int p_i45232_2_) {
        this.field_149357_a = p_i45232_1_;
        this.field_149356_b = p_i45232_2_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149357_a = p_148837_1_.readInt();
        this.field_149356_b = p_148837_1_.readInt();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_149357_a);
        p_148840_1_.writeInt(this.field_149356_b);
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleCollectItem(this);
    }
    
    public int func_149354_c() {
        return this.field_149357_a;
    }
    
    public int func_149353_d() {
        return this.field_149356_b;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
