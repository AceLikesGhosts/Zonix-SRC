package net.minecraft.network.play.server;

import net.minecraft.item.*;
import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S2FPacketSetSlot extends Packet
{
    private int field_149179_a;
    private int field_149177_b;
    private ItemStack field_149178_c;
    private static final String __OBFID = "CL_00001296";
    
    public S2FPacketSetSlot() {
    }
    
    public S2FPacketSetSlot(final int p_i45188_1_, final int p_i45188_2_, final ItemStack p_i45188_3_) {
        this.field_149179_a = p_i45188_1_;
        this.field_149177_b = p_i45188_2_;
        this.field_149178_c = ((p_i45188_3_ == null) ? null : p_i45188_3_.copy());
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleSetSlot(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149179_a = p_148837_1_.readByte();
        this.field_149177_b = p_148837_1_.readShort();
        this.field_149178_c = p_148837_1_.readItemStackFromBuffer();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeByte(this.field_149179_a);
        p_148840_1_.writeShort(this.field_149177_b);
        p_148840_1_.writeItemStackToBuffer(this.field_149178_c);
    }
    
    public int func_149175_c() {
        return this.field_149179_a;
    }
    
    public int func_149173_d() {
        return this.field_149177_b;
    }
    
    public ItemStack func_149174_e() {
        return this.field_149178_c;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
