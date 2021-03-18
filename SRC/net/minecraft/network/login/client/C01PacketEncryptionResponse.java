package net.minecraft.network.login.client;

import javax.crypto.*;
import net.minecraft.util.*;
import io.netty.buffer.*;
import java.io.*;
import net.minecraft.network.login.*;
import java.security.*;
import net.minecraft.network.*;

public class C01PacketEncryptionResponse extends Packet
{
    private byte[] field_149302_a;
    private byte[] field_149301_b;
    private static final String __OBFID = "CL_00001380";
    
    public C01PacketEncryptionResponse() {
        this.field_149302_a = new byte[0];
        this.field_149301_b = new byte[0];
    }
    
    public C01PacketEncryptionResponse(final SecretKey p_i45271_1_, final PublicKey p_i45271_2_, final byte[] p_i45271_3_) {
        this.field_149302_a = new byte[0];
        this.field_149301_b = new byte[0];
        this.field_149302_a = CryptManager.encryptData(p_i45271_2_, p_i45271_1_.getEncoded());
        this.field_149301_b = CryptManager.encryptData(p_i45271_2_, p_i45271_3_);
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149302_a = Packet.readBlob(p_148837_1_);
        this.field_149301_b = Packet.readBlob(p_148837_1_);
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        Packet.writeBlob(p_148840_1_, this.field_149302_a);
        Packet.writeBlob(p_148840_1_, this.field_149301_b);
    }
    
    public void processPacket(final INetHandlerLoginServer p_148833_1_) {
        p_148833_1_.processEncryptionResponse(this);
    }
    
    public SecretKey func_149300_a(final PrivateKey p_149300_1_) {
        return CryptManager.decryptSharedKey(p_149300_1_, this.field_149302_a);
    }
    
    public byte[] func_149299_b(final PrivateKey p_149299_1_) {
        return (p_149299_1_ == null) ? this.field_149301_b : CryptManager.decryptData(p_149299_1_, this.field_149301_b);
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerLoginServer)p_148833_1_);
    }
}
