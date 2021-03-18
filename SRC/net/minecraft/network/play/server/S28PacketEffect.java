package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S28PacketEffect extends Packet
{
    private int field_149251_a;
    private int field_149249_b;
    private int field_149250_c;
    private int field_149247_d;
    private int field_149248_e;
    private boolean field_149246_f;
    private static final String __OBFID = "CL_00001307";
    
    public S28PacketEffect() {
    }
    
    public S28PacketEffect(final int p_i45198_1_, final int p_i45198_2_, final int p_i45198_3_, final int p_i45198_4_, final int p_i45198_5_, final boolean p_i45198_6_) {
        this.field_149251_a = p_i45198_1_;
        this.field_149250_c = p_i45198_2_;
        this.field_149247_d = p_i45198_3_;
        this.field_149248_e = p_i45198_4_;
        this.field_149249_b = p_i45198_5_;
        this.field_149246_f = p_i45198_6_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149251_a = p_148837_1_.readInt();
        this.field_149250_c = p_148837_1_.readInt();
        this.field_149247_d = (p_148837_1_.readByte() & 0xFF);
        this.field_149248_e = p_148837_1_.readInt();
        this.field_149249_b = p_148837_1_.readInt();
        this.field_149246_f = p_148837_1_.readBoolean();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_149251_a);
        p_148840_1_.writeInt(this.field_149250_c);
        p_148840_1_.writeByte(this.field_149247_d & 0xFF);
        p_148840_1_.writeInt(this.field_149248_e);
        p_148840_1_.writeInt(this.field_149249_b);
        p_148840_1_.writeBoolean(this.field_149246_f);
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleEffect(this);
    }
    
    public boolean func_149244_c() {
        return this.field_149246_f;
    }
    
    public int func_149242_d() {
        return this.field_149251_a;
    }
    
    public int func_149241_e() {
        return this.field_149249_b;
    }
    
    public int func_149240_f() {
        return this.field_149250_c;
    }
    
    public int func_149243_g() {
        return this.field_149247_d;
    }
    
    public int func_149239_h() {
        return this.field_149248_e;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
