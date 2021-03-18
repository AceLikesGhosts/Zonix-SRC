package net.minecraft.network.play.client;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class C07PacketPlayerDigging extends Packet
{
    private int field_149511_a;
    private int field_149509_b;
    private int field_149510_c;
    private int field_149507_d;
    private int field_149508_e;
    private static final String __OBFID = "CL_00001365";
    
    public C07PacketPlayerDigging() {
    }
    
    public C07PacketPlayerDigging(final int p_i45258_1_, final int p_i45258_2_, final int p_i45258_3_, final int p_i45258_4_, final int p_i45258_5_) {
        this.field_149508_e = p_i45258_1_;
        this.field_149511_a = p_i45258_2_;
        this.field_149509_b = p_i45258_3_;
        this.field_149510_c = p_i45258_4_;
        this.field_149507_d = p_i45258_5_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149508_e = p_148837_1_.readUnsignedByte();
        this.field_149511_a = p_148837_1_.readInt();
        this.field_149509_b = p_148837_1_.readUnsignedByte();
        this.field_149510_c = p_148837_1_.readInt();
        this.field_149507_d = p_148837_1_.readUnsignedByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeByte(this.field_149508_e);
        p_148840_1_.writeInt(this.field_149511_a);
        p_148840_1_.writeByte(this.field_149509_b);
        p_148840_1_.writeInt(this.field_149510_c);
        p_148840_1_.writeByte(this.field_149507_d);
    }
    
    public void processPacket(final INetHandlerPlayServer p_148833_1_) {
        p_148833_1_.processPlayerDigging(this);
    }
    
    public int func_149505_c() {
        return this.field_149511_a;
    }
    
    public int func_149503_d() {
        return this.field_149509_b;
    }
    
    public int func_149502_e() {
        return this.field_149510_c;
    }
    
    public int func_149501_f() {
        return this.field_149507_d;
    }
    
    public int func_149506_g() {
        return this.field_149508_e;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayServer)p_148833_1_);
    }
}
