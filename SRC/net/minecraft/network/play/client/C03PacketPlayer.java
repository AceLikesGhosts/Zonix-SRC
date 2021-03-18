package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class C03PacketPlayer extends Packet
{
    protected double field_149479_a;
    protected double field_149477_b;
    protected double field_149478_c;
    protected double field_149475_d;
    protected float field_149476_e;
    protected float field_149473_f;
    protected boolean field_149474_g;
    protected boolean field_149480_h;
    protected boolean field_149481_i;
    private static final String __OBFID = "CL_00001360";
    
    public C03PacketPlayer() {
    }
    
    public C03PacketPlayer(final boolean p_i45256_1_) {
        this.field_149474_g = p_i45256_1_;
    }
    
    public void processPacket(final INetHandlerPlayServer p_148833_1_) {
        p_148833_1_.processPlayer(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149474_g = (p_148837_1_.readUnsignedByte() != 0);
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeByte(this.field_149474_g ? 1 : 0);
    }
    
    public double func_149464_c() {
        return this.field_149479_a;
    }
    
    public double func_149467_d() {
        return this.field_149477_b;
    }
    
    public double func_149472_e() {
        return this.field_149478_c;
    }
    
    public double func_149471_f() {
        return this.field_149475_d;
    }
    
    public float func_149462_g() {
        return this.field_149476_e;
    }
    
    public float func_149470_h() {
        return this.field_149473_f;
    }
    
    public boolean func_149465_i() {
        return this.field_149474_g;
    }
    
    public boolean func_149466_j() {
        return this.field_149480_h;
    }
    
    public boolean func_149463_k() {
        return this.field_149481_i;
    }
    
    public void func_149469_a(final boolean p_149469_1_) {
        this.field_149480_h = p_149469_1_;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayServer)p_148833_1_);
    }
    
    public static class C04PacketPlayerPosition extends C03PacketPlayer
    {
        private static final String __OBFID = "CL_00001361";
        
        public C04PacketPlayerPosition() {
            this.field_149480_h = true;
        }
        
        public C04PacketPlayerPosition(final double p_i45253_1_, final double p_i45253_3_, final double p_i45253_5_, final double p_i45253_7_, final boolean p_i45253_9_) {
            this.field_149479_a = p_i45253_1_;
            this.field_149477_b = p_i45253_3_;
            this.field_149475_d = p_i45253_5_;
            this.field_149478_c = p_i45253_7_;
            this.field_149474_g = p_i45253_9_;
            this.field_149480_h = true;
        }
        
        @Override
        public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
            this.field_149479_a = p_148837_1_.readDouble();
            this.field_149477_b = p_148837_1_.readDouble();
            this.field_149475_d = p_148837_1_.readDouble();
            this.field_149478_c = p_148837_1_.readDouble();
            super.readPacketData(p_148837_1_);
        }
        
        @Override
        public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
            p_148840_1_.writeDouble(this.field_149479_a);
            p_148840_1_.writeDouble(this.field_149477_b);
            p_148840_1_.writeDouble(this.field_149475_d);
            p_148840_1_.writeDouble(this.field_149478_c);
            super.writePacketData(p_148840_1_);
        }
        
        @Override
        public void processPacket(final INetHandler p_148833_1_) {
            super.processPacket((INetHandlerPlayServer)p_148833_1_);
        }
    }
    
    public static class C05PacketPlayerLook extends C03PacketPlayer
    {
        private static final String __OBFID = "CL_00001363";
        
        public C05PacketPlayerLook() {
            this.field_149481_i = true;
        }
        
        public C05PacketPlayerLook(final float p_i45255_1_, final float p_i45255_2_, final boolean p_i45255_3_) {
            this.field_149476_e = p_i45255_1_;
            this.field_149473_f = p_i45255_2_;
            this.field_149474_g = p_i45255_3_;
            this.field_149481_i = true;
        }
        
        @Override
        public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
            this.field_149476_e = p_148837_1_.readFloat();
            this.field_149473_f = p_148837_1_.readFloat();
            super.readPacketData(p_148837_1_);
        }
        
        @Override
        public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
            p_148840_1_.writeFloat(this.field_149476_e);
            p_148840_1_.writeFloat(this.field_149473_f);
            super.writePacketData(p_148840_1_);
        }
        
        @Override
        public void processPacket(final INetHandler p_148833_1_) {
            super.processPacket((INetHandlerPlayServer)p_148833_1_);
        }
    }
    
    public static class C06PacketPlayerPosLook extends C03PacketPlayer
    {
        private static final String __OBFID = "CL_00001362";
        
        public C06PacketPlayerPosLook() {
            this.field_149480_h = true;
            this.field_149481_i = true;
        }
        
        public C06PacketPlayerPosLook(final double p_i45254_1_, final double p_i45254_3_, final double p_i45254_5_, final double p_i45254_7_, final float p_i45254_9_, final float p_i45254_10_, final boolean p_i45254_11_) {
            this.field_149479_a = p_i45254_1_;
            this.field_149477_b = p_i45254_3_;
            this.field_149475_d = p_i45254_5_;
            this.field_149478_c = p_i45254_7_;
            this.field_149476_e = p_i45254_9_;
            this.field_149473_f = p_i45254_10_;
            this.field_149474_g = p_i45254_11_;
            this.field_149481_i = true;
            this.field_149480_h = true;
        }
        
        @Override
        public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
            this.field_149479_a = p_148837_1_.readDouble();
            this.field_149477_b = p_148837_1_.readDouble();
            this.field_149475_d = p_148837_1_.readDouble();
            this.field_149478_c = p_148837_1_.readDouble();
            this.field_149476_e = p_148837_1_.readFloat();
            this.field_149473_f = p_148837_1_.readFloat();
            super.readPacketData(p_148837_1_);
        }
        
        @Override
        public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
            p_148840_1_.writeDouble(this.field_149479_a);
            p_148840_1_.writeDouble(this.field_149477_b);
            p_148840_1_.writeDouble(this.field_149475_d);
            p_148840_1_.writeDouble(this.field_149478_c);
            p_148840_1_.writeFloat(this.field_149476_e);
            p_148840_1_.writeFloat(this.field_149473_f);
            super.writePacketData(p_148840_1_);
        }
        
        @Override
        public void processPacket(final INetHandler p_148833_1_) {
            super.processPacket((INetHandlerPlayServer)p_148833_1_);
        }
    }
}
