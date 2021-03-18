package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;

public class S14PacketEntity extends Packet
{
    protected int field_149074_a;
    protected byte field_149072_b;
    protected byte field_149073_c;
    protected byte field_149070_d;
    protected byte field_149071_e;
    protected byte field_149068_f;
    protected boolean field_149069_g;
    private static final String __OBFID = "CL_00001312";
    
    public S14PacketEntity() {
    }
    
    public S14PacketEntity(final int p_i45206_1_) {
        this.field_149074_a = p_i45206_1_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149074_a = p_148837_1_.readInt();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_149074_a);
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleEntityMovement(this);
    }
    
    @Override
    public String serialize() {
        return String.format("id=%d", this.field_149074_a);
    }
    
    @Override
    public String toString() {
        return "Entity_" + super.toString();
    }
    
    public Entity func_149065_a(final World p_149065_1_) {
        return p_149065_1_.getEntityByID(this.field_149074_a);
    }
    
    public byte func_149062_c() {
        return this.field_149072_b;
    }
    
    public byte func_149061_d() {
        return this.field_149073_c;
    }
    
    public byte func_149064_e() {
        return this.field_149070_d;
    }
    
    public byte func_149066_f() {
        return this.field_149071_e;
    }
    
    public byte func_149063_g() {
        return this.field_149068_f;
    }
    
    public boolean func_149060_h() {
        return this.field_149069_g;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
    
    public static class S15PacketEntityRelMove extends S14PacketEntity
    {
        private static final String __OBFID = "CL_00001313";
        
        public S15PacketEntityRelMove() {
        }
        
        public S15PacketEntityRelMove(final int p_i45203_1_, final byte p_i45203_2_, final byte p_i45203_3_, final byte p_i45203_4_) {
            super(p_i45203_1_);
            this.field_149072_b = p_i45203_2_;
            this.field_149073_c = p_i45203_3_;
            this.field_149070_d = p_i45203_4_;
        }
        
        @Override
        public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
            super.readPacketData(p_148837_1_);
            this.field_149072_b = p_148837_1_.readByte();
            this.field_149073_c = p_148837_1_.readByte();
            this.field_149070_d = p_148837_1_.readByte();
        }
        
        @Override
        public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
            super.writePacketData(p_148840_1_);
            p_148840_1_.writeByte(this.field_149072_b);
            p_148840_1_.writeByte(this.field_149073_c);
            p_148840_1_.writeByte(this.field_149070_d);
        }
        
        @Override
        public String serialize() {
            return super.serialize() + String.format(", xa=%d, ya=%d, za=%d", this.field_149072_b, this.field_149073_c, this.field_149070_d);
        }
        
        @Override
        public void processPacket(final INetHandler p_148833_1_) {
            super.processPacket((INetHandlerPlayClient)p_148833_1_);
        }
    }
    
    public static class S16PacketEntityLook extends S14PacketEntity
    {
        private static final String __OBFID = "CL_00001315";
        
        public S16PacketEntityLook() {
            this.field_149069_g = true;
        }
        
        public S16PacketEntityLook(final int p_i45205_1_, final byte p_i45205_2_, final byte p_i45205_3_) {
            super(p_i45205_1_);
            this.field_149071_e = p_i45205_2_;
            this.field_149068_f = p_i45205_3_;
            this.field_149069_g = true;
        }
        
        @Override
        public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
            super.readPacketData(p_148837_1_);
            this.field_149071_e = p_148837_1_.readByte();
            this.field_149068_f = p_148837_1_.readByte();
        }
        
        @Override
        public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
            super.writePacketData(p_148840_1_);
            p_148840_1_.writeByte(this.field_149071_e);
            p_148840_1_.writeByte(this.field_149068_f);
        }
        
        @Override
        public String serialize() {
            return super.serialize() + String.format(", yRot=%d, xRot=%d", this.field_149071_e, this.field_149068_f);
        }
        
        @Override
        public void processPacket(final INetHandler p_148833_1_) {
            super.processPacket((INetHandlerPlayClient)p_148833_1_);
        }
    }
    
    public static class S17PacketEntityLookMove extends S14PacketEntity
    {
        private static final String __OBFID = "CL_00001314";
        
        public S17PacketEntityLookMove() {
            this.field_149069_g = true;
        }
        
        public S17PacketEntityLookMove(final int p_i45204_1_, final byte p_i45204_2_, final byte p_i45204_3_, final byte p_i45204_4_, final byte p_i45204_5_, final byte p_i45204_6_) {
            super(p_i45204_1_);
            this.field_149072_b = p_i45204_2_;
            this.field_149073_c = p_i45204_3_;
            this.field_149070_d = p_i45204_4_;
            this.field_149071_e = p_i45204_5_;
            this.field_149068_f = p_i45204_6_;
            this.field_149069_g = true;
        }
        
        @Override
        public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
            super.readPacketData(p_148837_1_);
            this.field_149072_b = p_148837_1_.readByte();
            this.field_149073_c = p_148837_1_.readByte();
            this.field_149070_d = p_148837_1_.readByte();
            this.field_149071_e = p_148837_1_.readByte();
            this.field_149068_f = p_148837_1_.readByte();
        }
        
        @Override
        public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
            super.writePacketData(p_148840_1_);
            p_148840_1_.writeByte(this.field_149072_b);
            p_148840_1_.writeByte(this.field_149073_c);
            p_148840_1_.writeByte(this.field_149070_d);
            p_148840_1_.writeByte(this.field_149071_e);
            p_148840_1_.writeByte(this.field_149068_f);
        }
        
        @Override
        public String serialize() {
            return super.serialize() + String.format(", xa=%d, ya=%d, za=%d, yRot=%d, xRot=%d", this.field_149072_b, this.field_149073_c, this.field_149070_d, this.field_149071_e, this.field_149068_f);
        }
        
        @Override
        public void processPacket(final INetHandler p_148833_1_) {
            super.processPacket((INetHandlerPlayClient)p_148833_1_);
        }
    }
}
