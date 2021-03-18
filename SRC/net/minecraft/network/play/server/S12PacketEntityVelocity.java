package net.minecraft.network.play.server;

import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S12PacketEntityVelocity extends Packet
{
    private int field_149417_a;
    private int field_149415_b;
    private int field_149416_c;
    private int field_149414_d;
    private static final String __OBFID = "CL_00001328";
    
    public S12PacketEntityVelocity() {
    }
    
    public S12PacketEntityVelocity(final Entity p_i45219_1_) {
        this(p_i45219_1_.getEntityId(), p_i45219_1_.motionX, p_i45219_1_.motionY, p_i45219_1_.motionZ);
    }
    
    public S12PacketEntityVelocity(final int p_i45220_1_, double p_i45220_2_, double p_i45220_4_, double p_i45220_6_) {
        this.field_149417_a = p_i45220_1_;
        final double var8 = 3.9;
        if (p_i45220_2_ < -var8) {
            p_i45220_2_ = -var8;
        }
        if (p_i45220_4_ < -var8) {
            p_i45220_4_ = -var8;
        }
        if (p_i45220_6_ < -var8) {
            p_i45220_6_ = -var8;
        }
        if (p_i45220_2_ > var8) {
            p_i45220_2_ = var8;
        }
        if (p_i45220_4_ > var8) {
            p_i45220_4_ = var8;
        }
        if (p_i45220_6_ > var8) {
            p_i45220_6_ = var8;
        }
        this.field_149415_b = (int)(p_i45220_2_ * 8000.0);
        this.field_149416_c = (int)(p_i45220_4_ * 8000.0);
        this.field_149414_d = (int)(p_i45220_6_ * 8000.0);
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149417_a = p_148837_1_.readInt();
        this.field_149415_b = p_148837_1_.readShort();
        this.field_149416_c = p_148837_1_.readShort();
        this.field_149414_d = p_148837_1_.readShort();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_149417_a);
        p_148840_1_.writeShort(this.field_149415_b);
        p_148840_1_.writeShort(this.field_149416_c);
        p_148840_1_.writeShort(this.field_149414_d);
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleEntityVelocity(this);
    }
    
    @Override
    public String serialize() {
        return String.format("id=%d, x=%.2f, y=%.2f, z=%.2f", this.field_149417_a, this.field_149415_b / 8000.0f, this.field_149416_c / 8000.0f, this.field_149414_d / 8000.0f);
    }
    
    public int func_149412_c() {
        return this.field_149417_a;
    }
    
    public int func_149411_d() {
        return this.field_149415_b;
    }
    
    public int func_149410_e() {
        return this.field_149416_c;
    }
    
    public int func_149409_f() {
        return this.field_149414_d;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
