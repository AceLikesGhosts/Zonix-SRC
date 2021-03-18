package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S08PacketPlayerPosLook extends Packet
{
    private double field_148940_a;
    private double field_148938_b;
    private double field_148939_c;
    private float field_148936_d;
    private float field_148937_e;
    private boolean field_148935_f;
    private static final String __OBFID = "CL_00001273";
    
    public S08PacketPlayerPosLook() {
    }
    
    public S08PacketPlayerPosLook(final double p_i45164_1_, final double p_i45164_3_, final double p_i45164_5_, final float p_i45164_7_, final float p_i45164_8_, final boolean p_i45164_9_) {
        this.field_148940_a = p_i45164_1_;
        this.field_148938_b = p_i45164_3_;
        this.field_148939_c = p_i45164_5_;
        this.field_148936_d = p_i45164_7_;
        this.field_148937_e = p_i45164_8_;
        this.field_148935_f = p_i45164_9_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_148940_a = p_148837_1_.readDouble();
        this.field_148938_b = p_148837_1_.readDouble();
        this.field_148939_c = p_148837_1_.readDouble();
        this.field_148936_d = p_148837_1_.readFloat();
        this.field_148937_e = p_148837_1_.readFloat();
        this.field_148935_f = p_148837_1_.readBoolean();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeDouble(this.field_148940_a);
        p_148840_1_.writeDouble(this.field_148938_b);
        p_148840_1_.writeDouble(this.field_148939_c);
        p_148840_1_.writeFloat(this.field_148936_d);
        p_148840_1_.writeFloat(this.field_148937_e);
        p_148840_1_.writeBoolean(this.field_148935_f);
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handlePlayerPosLook(this);
    }
    
    public double func_148932_c() {
        return this.field_148940_a;
    }
    
    public double func_148928_d() {
        return this.field_148938_b;
    }
    
    public double func_148933_e() {
        return this.field_148939_c;
    }
    
    public float func_148931_f() {
        return this.field_148936_d;
    }
    
    public float func_148930_g() {
        return this.field_148937_e;
    }
    
    public boolean func_148929_h() {
        return this.field_148935_f;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
