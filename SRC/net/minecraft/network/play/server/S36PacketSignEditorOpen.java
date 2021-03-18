package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S36PacketSignEditorOpen extends Packet
{
    private int field_149133_a;
    private int field_149131_b;
    private int field_149132_c;
    private static final String __OBFID = "CL_00001316";
    
    public S36PacketSignEditorOpen() {
    }
    
    public S36PacketSignEditorOpen(final int p_i45207_1_, final int p_i45207_2_, final int p_i45207_3_) {
        this.field_149133_a = p_i45207_1_;
        this.field_149131_b = p_i45207_2_;
        this.field_149132_c = p_i45207_3_;
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleSignEditorOpen(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149133_a = p_148837_1_.readInt();
        this.field_149131_b = p_148837_1_.readInt();
        this.field_149132_c = p_148837_1_.readInt();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_149133_a);
        p_148840_1_.writeInt(this.field_149131_b);
        p_148840_1_.writeInt(this.field_149132_c);
    }
    
    public int func_149129_c() {
        return this.field_149133_a;
    }
    
    public int func_149128_d() {
        return this.field_149131_b;
    }
    
    public int func_149127_e() {
        return this.field_149132_c;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
