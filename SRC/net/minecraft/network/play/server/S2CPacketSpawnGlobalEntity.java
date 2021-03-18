package net.minecraft.network.play.server;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.entity.effect.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S2CPacketSpawnGlobalEntity extends Packet
{
    private int field_149059_a;
    private int field_149057_b;
    private int field_149058_c;
    private int field_149055_d;
    private int field_149056_e;
    private static final String __OBFID = "CL_00001278";
    
    public S2CPacketSpawnGlobalEntity() {
    }
    
    public S2CPacketSpawnGlobalEntity(final Entity p_i45191_1_) {
        this.field_149059_a = p_i45191_1_.getEntityId();
        this.field_149057_b = MathHelper.floor_double(p_i45191_1_.posX * 32.0);
        this.field_149058_c = MathHelper.floor_double(p_i45191_1_.posY * 32.0);
        this.field_149055_d = MathHelper.floor_double(p_i45191_1_.posZ * 32.0);
        if (p_i45191_1_ instanceof EntityLightningBolt) {
            this.field_149056_e = 1;
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149059_a = p_148837_1_.readVarIntFromBuffer();
        this.field_149056_e = p_148837_1_.readByte();
        this.field_149057_b = p_148837_1_.readInt();
        this.field_149058_c = p_148837_1_.readInt();
        this.field_149055_d = p_148837_1_.readInt();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeVarIntToBuffer(this.field_149059_a);
        p_148840_1_.writeByte(this.field_149056_e);
        p_148840_1_.writeInt(this.field_149057_b);
        p_148840_1_.writeInt(this.field_149058_c);
        p_148840_1_.writeInt(this.field_149055_d);
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleSpawnGlobalEntity(this);
    }
    
    @Override
    public String serialize() {
        return String.format("id=%d, type=%d, x=%.2f, y=%.2f, z=%.2f", this.field_149059_a, this.field_149056_e, this.field_149057_b / 32.0f, this.field_149058_c / 32.0f, this.field_149055_d / 32.0f);
    }
    
    public int func_149052_c() {
        return this.field_149059_a;
    }
    
    public int func_149051_d() {
        return this.field_149057_b;
    }
    
    public int func_149050_e() {
        return this.field_149058_c;
    }
    
    public int func_149049_f() {
        return this.field_149055_d;
    }
    
    public int func_149053_g() {
        return this.field_149056_e;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
