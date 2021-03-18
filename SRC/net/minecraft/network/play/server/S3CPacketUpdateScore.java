package net.minecraft.network.play.server;

import net.minecraft.scoreboard.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S3CPacketUpdateScore extends Packet
{
    private String field_149329_a;
    private String field_149327_b;
    private int field_149328_c;
    private int field_149326_d;
    private static final String __OBFID = "CL_00001335";
    
    public S3CPacketUpdateScore() {
        this.field_149329_a = "";
        this.field_149327_b = "";
    }
    
    public S3CPacketUpdateScore(final Score p_i45227_1_, final int p_i45227_2_) {
        this.field_149329_a = "";
        this.field_149327_b = "";
        this.field_149329_a = p_i45227_1_.getPlayerName();
        this.field_149327_b = p_i45227_1_.func_96645_d().getName();
        this.field_149328_c = p_i45227_1_.getScorePoints();
        this.field_149326_d = p_i45227_2_;
    }
    
    public S3CPacketUpdateScore(final String p_i45228_1_) {
        this.field_149329_a = "";
        this.field_149327_b = "";
        this.field_149329_a = p_i45228_1_;
        this.field_149327_b = "";
        this.field_149328_c = 0;
        this.field_149326_d = 1;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149329_a = p_148837_1_.readStringFromBuffer(16);
        this.field_149326_d = p_148837_1_.readByte();
        if (this.field_149326_d != 1) {
            this.field_149327_b = p_148837_1_.readStringFromBuffer(16);
            this.field_149328_c = p_148837_1_.readInt();
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeStringToBuffer(this.field_149329_a);
        p_148840_1_.writeByte(this.field_149326_d);
        if (this.field_149326_d != 1) {
            p_148840_1_.writeStringToBuffer(this.field_149327_b);
            p_148840_1_.writeInt(this.field_149328_c);
        }
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleUpdateScore(this);
    }
    
    public String func_149324_c() {
        return this.field_149329_a;
    }
    
    public String func_149321_d() {
        return this.field_149327_b;
    }
    
    public int func_149323_e() {
        return this.field_149328_c;
    }
    
    public int func_149322_f() {
        return this.field_149326_d;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
