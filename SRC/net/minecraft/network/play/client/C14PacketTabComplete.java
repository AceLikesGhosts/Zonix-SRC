package net.minecraft.network.play.client;

import java.io.*;
import org.apache.commons.lang3.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class C14PacketTabComplete extends Packet
{
    private String field_149420_a;
    private static final String __OBFID = "CL_00001346";
    
    public C14PacketTabComplete() {
    }
    
    public C14PacketTabComplete(final String p_i45239_1_) {
        this.field_149420_a = p_i45239_1_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149420_a = p_148837_1_.readStringFromBuffer(32767);
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeStringToBuffer(StringUtils.substring(this.field_149420_a, 0, 32767));
    }
    
    public void processPacket(final INetHandlerPlayServer p_148833_1_) {
        p_148833_1_.processTabComplete(this);
    }
    
    public String func_149419_c() {
        return this.field_149420_a;
    }
    
    @Override
    public String serialize() {
        return String.format("message='%s'", this.field_149420_a);
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayServer)p_148833_1_);
    }
}
