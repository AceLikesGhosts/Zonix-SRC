package net.minecraft.network.play.server;

import net.minecraft.scoreboard.*;
import java.io.*;
import java.util.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S3EPacketTeams extends Packet
{
    private String field_149320_a;
    private String field_149318_b;
    private String field_149319_c;
    private String field_149316_d;
    private Collection field_149317_e;
    private int field_149314_f;
    private int field_149315_g;
    private static final String __OBFID = "CL_00001334";
    
    public S3EPacketTeams() {
        this.field_149320_a = "";
        this.field_149318_b = "";
        this.field_149319_c = "";
        this.field_149316_d = "";
        this.field_149317_e = new ArrayList();
    }
    
    public S3EPacketTeams(final ScorePlayerTeam p_i45225_1_, final int p_i45225_2_) {
        this.field_149320_a = "";
        this.field_149318_b = "";
        this.field_149319_c = "";
        this.field_149316_d = "";
        this.field_149317_e = new ArrayList();
        this.field_149320_a = p_i45225_1_.getRegisteredName();
        this.field_149314_f = p_i45225_2_;
        if (p_i45225_2_ == 0 || p_i45225_2_ == 2) {
            this.field_149318_b = p_i45225_1_.func_96669_c();
            this.field_149319_c = p_i45225_1_.getColorPrefix();
            this.field_149316_d = p_i45225_1_.getColorSuffix();
            this.field_149315_g = p_i45225_1_.func_98299_i();
        }
        if (p_i45225_2_ == 0) {
            this.field_149317_e.addAll(p_i45225_1_.getMembershipCollection());
        }
    }
    
    public S3EPacketTeams(final ScorePlayerTeam p_i45226_1_, final Collection p_i45226_2_, final int p_i45226_3_) {
        this.field_149320_a = "";
        this.field_149318_b = "";
        this.field_149319_c = "";
        this.field_149316_d = "";
        this.field_149317_e = new ArrayList();
        if (p_i45226_3_ != 3 && p_i45226_3_ != 4) {
            throw new IllegalArgumentException("Method must be join or leave for player constructor");
        }
        if (p_i45226_2_ != null && !p_i45226_2_.isEmpty()) {
            this.field_149314_f = p_i45226_3_;
            this.field_149320_a = p_i45226_1_.getRegisteredName();
            this.field_149317_e.addAll(p_i45226_2_);
            return;
        }
        throw new IllegalArgumentException("Players cannot be null/empty");
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149320_a = p_148837_1_.readStringFromBuffer(16);
        this.field_149314_f = p_148837_1_.readByte();
        if (this.field_149314_f == 0 || this.field_149314_f == 2) {
            this.field_149318_b = p_148837_1_.readStringFromBuffer(32);
            this.field_149319_c = p_148837_1_.readStringFromBuffer(16);
            this.field_149316_d = p_148837_1_.readStringFromBuffer(16);
            this.field_149315_g = p_148837_1_.readByte();
        }
        if (this.field_149314_f == 0 || this.field_149314_f == 3 || this.field_149314_f == 4) {
            final short var2 = p_148837_1_.readShort();
            for (int var3 = 0; var3 < var2; ++var3) {
                this.field_149317_e.add(p_148837_1_.readStringFromBuffer(40));
            }
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeStringToBuffer(this.field_149320_a);
        p_148840_1_.writeByte(this.field_149314_f);
        if (this.field_149314_f == 0 || this.field_149314_f == 2) {
            p_148840_1_.writeStringToBuffer(this.field_149318_b);
            p_148840_1_.writeStringToBuffer(this.field_149319_c);
            p_148840_1_.writeStringToBuffer(this.field_149316_d);
            p_148840_1_.writeByte(this.field_149315_g);
        }
        if (this.field_149314_f == 0 || this.field_149314_f == 3 || this.field_149314_f == 4) {
            p_148840_1_.writeShort(this.field_149317_e.size());
            for (final String var3 : this.field_149317_e) {
                p_148840_1_.writeStringToBuffer(var3);
            }
        }
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleTeams(this);
    }
    
    public String func_149312_c() {
        return this.field_149320_a;
    }
    
    public String func_149306_d() {
        return this.field_149318_b;
    }
    
    public String func_149311_e() {
        return this.field_149319_c;
    }
    
    public String func_149309_f() {
        return this.field_149316_d;
    }
    
    public Collection func_149310_g() {
        return this.field_149317_e;
    }
    
    public int func_149307_h() {
        return this.field_149314_f;
    }
    
    public int func_149308_i() {
        return this.field_149315_g;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
