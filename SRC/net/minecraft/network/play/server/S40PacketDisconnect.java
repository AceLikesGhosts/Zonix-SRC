package net.minecraft.network.play.server;

import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S40PacketDisconnect extends Packet
{
    private IChatComponent field_149167_a;
    private static final String __OBFID = "CL_00001298";
    
    public S40PacketDisconnect() {
    }
    
    public S40PacketDisconnect(final IChatComponent p_i46336_1_) {
        this.field_149167_a = p_i46336_1_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149167_a = IChatComponent.Serializer.func_150699_a(p_148837_1_.readStringFromBuffer(32767));
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeStringToBuffer(IChatComponent.Serializer.func_150696_a(this.field_149167_a));
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleDisconnect(this);
    }
    
    @Override
    public boolean hasPriority() {
        return true;
    }
    
    public IChatComponent func_149165_c() {
        return this.field_149167_a;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
