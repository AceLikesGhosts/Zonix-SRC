package net.minecraft.network.login.server;

import com.mojang.authlib.*;
import java.util.*;
import java.io.*;
import net.minecraft.network.login.*;
import net.minecraft.network.*;

public class S02PacketLoginSuccess extends Packet
{
    private GameProfile field_149602_a;
    private static final String __OBFID = "CL_00001375";
    
    public S02PacketLoginSuccess() {
    }
    
    public S02PacketLoginSuccess(final GameProfile p_i45267_1_) {
        this.field_149602_a = p_i45267_1_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        final String var2 = p_148837_1_.readStringFromBuffer(36);
        final String var3 = p_148837_1_.readStringFromBuffer(16);
        final UUID var4 = UUID.fromString(var2);
        this.field_149602_a = new GameProfile(var4, var3);
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        final UUID var2 = this.field_149602_a.getId();
        p_148840_1_.writeStringToBuffer((var2 == null) ? "" : var2.toString());
        p_148840_1_.writeStringToBuffer(this.field_149602_a.getName());
    }
    
    public void processPacket(final INetHandlerLoginClient p_148833_1_) {
        p_148833_1_.handleLoginSuccess(this);
    }
    
    @Override
    public boolean hasPriority() {
        return true;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerLoginClient)p_148833_1_);
    }
}
