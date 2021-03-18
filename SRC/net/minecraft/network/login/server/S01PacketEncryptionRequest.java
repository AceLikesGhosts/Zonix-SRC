package net.minecraft.network.login.server;

import java.security.*;
import io.netty.buffer.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.network.login.*;
import net.minecraft.network.*;

public class S01PacketEncryptionRequest extends Packet
{
    private String field_149612_a;
    private PublicKey field_149610_b;
    private byte[] field_149611_c;
    private static final String __OBFID = "CL_00001376";
    
    public S01PacketEncryptionRequest() {
    }
    
    public S01PacketEncryptionRequest(final String p_i45268_1_, final PublicKey p_i45268_2_, final byte[] p_i45268_3_) {
        this.field_149612_a = p_i45268_1_;
        this.field_149610_b = p_i45268_2_;
        this.field_149611_c = p_i45268_3_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149612_a = p_148837_1_.readStringFromBuffer(20);
        this.field_149610_b = CryptManager.decodePublicKey(Packet.readBlob(p_148837_1_));
        this.field_149611_c = Packet.readBlob(p_148837_1_);
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeStringToBuffer(this.field_149612_a);
        Packet.writeBlob(p_148840_1_, this.field_149610_b.getEncoded());
        Packet.writeBlob(p_148840_1_, this.field_149611_c);
    }
    
    public void processPacket(final INetHandlerLoginClient p_148833_1_) {
        p_148833_1_.handleEncryptionRequest(this);
    }
    
    public String func_149609_c() {
        return this.field_149612_a;
    }
    
    public PublicKey func_149608_d() {
        return this.field_149610_b;
    }
    
    public byte[] func_149607_e() {
        return this.field_149611_c;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerLoginClient)p_148833_1_);
    }
}
