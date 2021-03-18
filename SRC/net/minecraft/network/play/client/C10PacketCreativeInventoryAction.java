package net.minecraft.network.play.client;

import net.minecraft.item.*;
import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class C10PacketCreativeInventoryAction extends Packet
{
    private int field_149629_a;
    private ItemStack field_149628_b;
    private static final String __OBFID = "CL_00001369";
    
    public C10PacketCreativeInventoryAction() {
    }
    
    public C10PacketCreativeInventoryAction(final int p_i45263_1_, final ItemStack p_i45263_2_) {
        this.field_149629_a = p_i45263_1_;
        this.field_149628_b = ((p_i45263_2_ != null) ? p_i45263_2_.copy() : null);
    }
    
    public void processPacket(final INetHandlerPlayServer p_148833_1_) {
        p_148833_1_.processCreativeInventoryAction(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149629_a = p_148837_1_.readShort();
        this.field_149628_b = p_148837_1_.readItemStackFromBuffer();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeShort(this.field_149629_a);
        p_148840_1_.writeItemStackToBuffer(this.field_149628_b);
    }
    
    public int func_149627_c() {
        return this.field_149629_a;
    }
    
    public ItemStack func_149625_d() {
        return this.field_149628_b;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayServer)p_148833_1_);
    }
}
