package net.minecraft.network.play.server;

import net.minecraft.item.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S04PacketEntityEquipment extends Packet
{
    private int field_149394_a;
    private int field_149392_b;
    private ItemStack field_149393_c;
    private static final String __OBFID = "CL_00001330";
    
    public S04PacketEntityEquipment() {
    }
    
    public S04PacketEntityEquipment(final int p_i45221_1_, final int p_i45221_2_, final ItemStack p_i45221_3_) {
        this.field_149394_a = p_i45221_1_;
        this.field_149392_b = p_i45221_2_;
        this.field_149393_c = ((p_i45221_3_ == null) ? null : p_i45221_3_.copy());
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149394_a = p_148837_1_.readInt();
        this.field_149392_b = p_148837_1_.readShort();
        this.field_149393_c = p_148837_1_.readItemStackFromBuffer();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_149394_a);
        p_148840_1_.writeShort(this.field_149392_b);
        p_148840_1_.writeItemStackToBuffer(this.field_149393_c);
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleEntityEquipment(this);
    }
    
    public ItemStack func_149390_c() {
        return this.field_149393_c;
    }
    
    @Override
    public String serialize() {
        return String.format("entity=%d, slot=%d, item=%s", this.field_149394_a, this.field_149392_b, this.field_149393_c);
    }
    
    public int func_149389_d() {
        return this.field_149394_a;
    }
    
    public int func_149388_e() {
        return this.field_149392_b;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
