package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import org.apache.commons.lang3.*;
import net.minecraft.network.*;

public class S3APacketTabComplete extends Packet
{
    private String[] field_149632_a;
    private static final String __OBFID = "CL_00001288";
    
    public S3APacketTabComplete() {
    }
    
    public S3APacketTabComplete(final String[] p_i45178_1_) {
        this.field_149632_a = p_i45178_1_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149632_a = new String[p_148837_1_.readVarIntFromBuffer()];
        for (int var2 = 0; var2 < this.field_149632_a.length; ++var2) {
            this.field_149632_a[var2] = p_148837_1_.readStringFromBuffer(32767);
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeVarIntToBuffer(this.field_149632_a.length);
        for (final String var5 : this.field_149632_a) {
            p_148840_1_.writeStringToBuffer(var5);
        }
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleTabComplete(this);
    }
    
    public String[] func_149630_c() {
        return this.field_149632_a;
    }
    
    @Override
    public String serialize() {
        return String.format("candidates='%s'", ArrayUtils.toString((Object)this.field_149632_a));
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
