package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S05PacketSpawnPosition extends Packet
{
    private int field_149364_a;
    private int field_149362_b;
    private int field_149363_c;
    private static final String __OBFID = "CL_00001336";
    
    public S05PacketSpawnPosition() {
    }
    
    public S05PacketSpawnPosition(final int p_i45229_1_, final int p_i45229_2_, final int p_i45229_3_) {
        this.field_149364_a = p_i45229_1_;
        this.field_149362_b = p_i45229_2_;
        this.field_149363_c = p_i45229_3_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149364_a = p_148837_1_.readInt();
        this.field_149362_b = p_148837_1_.readInt();
        this.field_149363_c = p_148837_1_.readInt();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_149364_a);
        p_148840_1_.writeInt(this.field_149362_b);
        p_148840_1_.writeInt(this.field_149363_c);
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleSpawnPosition(this);
    }
    
    @Override
    public boolean hasPriority() {
        return false;
    }
    
    @Override
    public String serialize() {
        return String.format("x=%d, y=%d, z=%d", this.field_149364_a, this.field_149362_b, this.field_149363_c);
    }
    
    public int func_149360_c() {
        return this.field_149364_a;
    }
    
    public int func_149359_d() {
        return this.field_149362_b;
    }
    
    public int func_149358_e() {
        return this.field_149363_c;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
