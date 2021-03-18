package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S2BPacketChangeGameState extends Packet
{
    public static final String[] field_149142_a;
    private int field_149140_b;
    private float field_149141_c;
    private static final String __OBFID = "CL_00001301";
    
    public S2BPacketChangeGameState() {
    }
    
    public S2BPacketChangeGameState(final int p_i45194_1_, final float p_i45194_2_) {
        this.field_149140_b = p_i45194_1_;
        this.field_149141_c = p_i45194_2_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149140_b = p_148837_1_.readUnsignedByte();
        this.field_149141_c = p_148837_1_.readFloat();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeByte(this.field_149140_b);
        p_148840_1_.writeFloat(this.field_149141_c);
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleChangeGameState(this);
    }
    
    public int func_149138_c() {
        return this.field_149140_b;
    }
    
    public float func_149137_d() {
        return this.field_149141_c;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
    
    static {
        field_149142_a = new String[] { "tile.bed.notValid", null, null, "gameMode.changed" };
    }
}
