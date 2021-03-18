package net.minecraft.network.login.client;

import com.mojang.authlib.*;
import java.util.*;
import java.io.*;
import net.minecraft.network.login.*;
import net.minecraft.network.*;

public class C00PacketLoginStart extends Packet
{
    private GameProfile field_149305_a;
    private static final String __OBFID = "CL_00001379";
    
    public C00PacketLoginStart() {
    }
    
    public C00PacketLoginStart(final GameProfile p_i45270_1_) {
        this.field_149305_a = p_i45270_1_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149305_a = new GameProfile((UUID)null, p_148837_1_.readStringFromBuffer(16));
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeStringToBuffer(this.field_149305_a.getName());
    }
    
    public void processPacket(final INetHandlerLoginServer p_148833_1_) {
        p_148833_1_.processLoginStart(this);
    }
    
    public GameProfile func_149304_c() {
        return this.field_149305_a;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerLoginServer)p_148833_1_);
    }
}
