package net.minecraft.network.play.server;

import net.minecraft.potion.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S1EPacketRemoveEntityEffect extends Packet
{
    private int field_149079_a;
    private int field_149078_b;
    private static final String __OBFID = "CL_00001321";
    
    public S1EPacketRemoveEntityEffect() {
    }
    
    public S1EPacketRemoveEntityEffect(final int p_i45212_1_, final PotionEffect p_i45212_2_) {
        this.field_149079_a = p_i45212_1_;
        this.field_149078_b = p_i45212_2_.getPotionID();
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149079_a = p_148837_1_.readInt();
        this.field_149078_b = p_148837_1_.readUnsignedByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_149079_a);
        p_148840_1_.writeByte(this.field_149078_b);
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleRemoveEntityEffect(this);
    }
    
    public int func_149076_c() {
        return this.field_149079_a;
    }
    
    public int func_149075_d() {
        return this.field_149078_b;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
