package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class C11PacketEnchantItem extends Packet
{
    private int field_149541_a;
    private int field_149540_b;
    private static final String __OBFID = "CL_00001352";
    
    public C11PacketEnchantItem() {
    }
    
    public C11PacketEnchantItem(final int p_i45245_1_, final int p_i45245_2_) {
        this.field_149541_a = p_i45245_1_;
        this.field_149540_b = p_i45245_2_;
    }
    
    public void processPacket(final INetHandlerPlayServer p_148833_1_) {
        p_148833_1_.processEnchantItem(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149541_a = p_148837_1_.readByte();
        this.field_149540_b = p_148837_1_.readByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeByte(this.field_149541_a);
        p_148840_1_.writeByte(this.field_149540_b);
    }
    
    @Override
    public String serialize() {
        return String.format("id=%d, button=%d", this.field_149541_a, this.field_149540_b);
    }
    
    public int func_149539_c() {
        return this.field_149541_a;
    }
    
    public int func_149537_d() {
        return this.field_149540_b;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayServer)p_148833_1_);
    }
}
