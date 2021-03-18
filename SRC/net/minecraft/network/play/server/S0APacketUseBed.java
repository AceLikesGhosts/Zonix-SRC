package net.minecraft.network.play.server;

import net.minecraft.entity.player.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.world.*;
import net.minecraft.network.*;

public class S0APacketUseBed extends Packet
{
    private int field_149097_a;
    private int field_149095_b;
    private int field_149096_c;
    private int field_149094_d;
    private static final String __OBFID = "CL_00001319";
    
    public S0APacketUseBed() {
    }
    
    public S0APacketUseBed(final EntityPlayer p_i45210_1_, final int p_i45210_2_, final int p_i45210_3_, final int p_i45210_4_) {
        this.field_149095_b = p_i45210_2_;
        this.field_149096_c = p_i45210_3_;
        this.field_149094_d = p_i45210_4_;
        this.field_149097_a = p_i45210_1_.getEntityId();
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149097_a = p_148837_1_.readInt();
        this.field_149095_b = p_148837_1_.readInt();
        this.field_149096_c = p_148837_1_.readByte();
        this.field_149094_d = p_148837_1_.readInt();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_149097_a);
        p_148840_1_.writeInt(this.field_149095_b);
        p_148840_1_.writeByte(this.field_149096_c);
        p_148840_1_.writeInt(this.field_149094_d);
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleUseBed(this);
    }
    
    public EntityPlayer func_149091_a(final World p_149091_1_) {
        return (EntityPlayer)p_149091_1_.getEntityByID(this.field_149097_a);
    }
    
    public int func_149092_c() {
        return this.field_149095_b;
    }
    
    public int func_149090_d() {
        return this.field_149096_c;
    }
    
    public int func_149089_e() {
        return this.field_149094_d;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
