package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S32PacketConfirmTransaction extends Packet
{
    private int field_148894_a;
    private short field_148892_b;
    private boolean field_148893_c;
    private static final String __OBFID = "CL_00001291";
    
    public S32PacketConfirmTransaction() {
    }
    
    public S32PacketConfirmTransaction(final int p_i45182_1_, final short p_i45182_2_, final boolean p_i45182_3_) {
        this.field_148894_a = p_i45182_1_;
        this.field_148892_b = p_i45182_2_;
        this.field_148893_c = p_i45182_3_;
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleConfirmTransaction(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_148894_a = p_148837_1_.readUnsignedByte();
        this.field_148892_b = p_148837_1_.readShort();
        this.field_148893_c = p_148837_1_.readBoolean();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeByte(this.field_148894_a);
        p_148840_1_.writeShort(this.field_148892_b);
        p_148840_1_.writeBoolean(this.field_148893_c);
    }
    
    @Override
    public String serialize() {
        return String.format("id=%d, uid=%d, accepted=%b", this.field_148894_a, this.field_148892_b, this.field_148893_c);
    }
    
    public int func_148889_c() {
        return this.field_148894_a;
    }
    
    public short func_148890_d() {
        return this.field_148892_b;
    }
    
    public boolean func_148888_e() {
        return this.field_148893_c;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
