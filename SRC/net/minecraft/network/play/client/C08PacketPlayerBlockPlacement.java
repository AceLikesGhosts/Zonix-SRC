package net.minecraft.network.play.client;

import net.minecraft.item.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class C08PacketPlayerBlockPlacement extends Packet
{
    private int field_149583_a;
    private int field_149581_b;
    private int field_149582_c;
    private int field_149579_d;
    private ItemStack field_149580_e;
    private float field_149577_f;
    private float field_149578_g;
    private float field_149584_h;
    private static final String __OBFID = "CL_00001371";
    
    public C08PacketPlayerBlockPlacement() {
    }
    
    public C08PacketPlayerBlockPlacement(final int p_i45265_1_, final int p_i45265_2_, final int p_i45265_3_, final int p_i45265_4_, final ItemStack p_i45265_5_, final float p_i45265_6_, final float p_i45265_7_, final float p_i45265_8_) {
        this.field_149583_a = p_i45265_1_;
        this.field_149581_b = p_i45265_2_;
        this.field_149582_c = p_i45265_3_;
        this.field_149579_d = p_i45265_4_;
        this.field_149580_e = ((p_i45265_5_ != null) ? p_i45265_5_.copy() : null);
        this.field_149577_f = p_i45265_6_;
        this.field_149578_g = p_i45265_7_;
        this.field_149584_h = p_i45265_8_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149583_a = p_148837_1_.readInt();
        this.field_149581_b = p_148837_1_.readUnsignedByte();
        this.field_149582_c = p_148837_1_.readInt();
        this.field_149579_d = p_148837_1_.readUnsignedByte();
        this.field_149580_e = p_148837_1_.readItemStackFromBuffer();
        this.field_149577_f = p_148837_1_.readUnsignedByte() / 16.0f;
        this.field_149578_g = p_148837_1_.readUnsignedByte() / 16.0f;
        this.field_149584_h = p_148837_1_.readUnsignedByte() / 16.0f;
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_149583_a);
        p_148840_1_.writeByte(this.field_149581_b);
        p_148840_1_.writeInt(this.field_149582_c);
        p_148840_1_.writeByte(this.field_149579_d);
        p_148840_1_.writeItemStackToBuffer(this.field_149580_e);
        p_148840_1_.writeByte((int)(this.field_149577_f * 16.0f));
        p_148840_1_.writeByte((int)(this.field_149578_g * 16.0f));
        p_148840_1_.writeByte((int)(this.field_149584_h * 16.0f));
    }
    
    public void processPacket(final INetHandlerPlayServer p_148833_1_) {
        p_148833_1_.processPlayerBlockPlacement(this);
    }
    
    public int func_149576_c() {
        return this.field_149583_a;
    }
    
    public int func_149571_d() {
        return this.field_149581_b;
    }
    
    public int func_149570_e() {
        return this.field_149582_c;
    }
    
    public int func_149568_f() {
        return this.field_149579_d;
    }
    
    public ItemStack func_149574_g() {
        return this.field_149580_e;
    }
    
    public float func_149573_h() {
        return this.field_149577_f;
    }
    
    public float func_149569_i() {
        return this.field_149578_g;
    }
    
    public float func_149575_j() {
        return this.field_149584_h;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayServer)p_148833_1_);
    }
}
