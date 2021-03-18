package net.minecraft.network.play.server;

import net.minecraft.scoreboard.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S3DPacketDisplayScoreboard extends Packet
{
    private int field_149374_a;
    private String field_149373_b;
    private static final String __OBFID = "CL_00001325";
    
    public S3DPacketDisplayScoreboard() {
    }
    
    public S3DPacketDisplayScoreboard(final int p_i45216_1_, final ScoreObjective p_i45216_2_) {
        this.field_149374_a = p_i45216_1_;
        if (p_i45216_2_ == null) {
            this.field_149373_b = "";
        }
        else {
            this.field_149373_b = p_i45216_2_.getName();
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149374_a = p_148837_1_.readByte();
        this.field_149373_b = p_148837_1_.readStringFromBuffer(16);
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeByte(this.field_149374_a);
        p_148840_1_.writeStringToBuffer(this.field_149373_b);
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleDisplayScoreboard(this);
    }
    
    public int func_149371_c() {
        return this.field_149374_a;
    }
    
    public String func_149370_d() {
        return this.field_149373_b;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
