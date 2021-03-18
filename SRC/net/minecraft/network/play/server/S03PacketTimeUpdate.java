package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S03PacketTimeUpdate extends Packet
{
    private long field_149369_a;
    private long field_149368_b;
    private static final String __OBFID = "CL_00001337";
    
    public S03PacketTimeUpdate() {
    }
    
    public S03PacketTimeUpdate(final long p_i45230_1_, final long p_i45230_3_, final boolean p_i45230_5_) {
        this.field_149369_a = p_i45230_1_;
        this.field_149368_b = p_i45230_3_;
        if (!p_i45230_5_) {
            this.field_149368_b = -this.field_149368_b;
            if (this.field_149368_b == 0L) {
                this.field_149368_b = -1L;
            }
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149369_a = p_148837_1_.readLong();
        this.field_149368_b = p_148837_1_.readLong();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeLong(this.field_149369_a);
        p_148840_1_.writeLong(this.field_149368_b);
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleTimeUpdate(this);
    }
    
    @Override
    public String serialize() {
        return String.format("time=%d,dtime=%d", this.field_149369_a, this.field_149368_b);
    }
    
    public long func_149366_c() {
        return this.field_149369_a;
    }
    
    public long func_149365_d() {
        return this.field_149368_b;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
