package net.minecraft.network.play.server;

import net.minecraft.util.*;
import net.minecraft.world.*;
import java.io.*;
import java.util.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S27PacketExplosion extends Packet
{
    private double field_149158_a;
    private double field_149156_b;
    private double field_149157_c;
    private float field_149154_d;
    private List field_149155_e;
    private float field_149152_f;
    private float field_149153_g;
    private float field_149159_h;
    private static final String __OBFID = "CL_00001300";
    
    public S27PacketExplosion() {
    }
    
    public S27PacketExplosion(final double p_i45193_1_, final double p_i45193_3_, final double p_i45193_5_, final float p_i45193_7_, final List p_i45193_8_, final Vec3 p_i45193_9_) {
        this.field_149158_a = p_i45193_1_;
        this.field_149156_b = p_i45193_3_;
        this.field_149157_c = p_i45193_5_;
        this.field_149154_d = p_i45193_7_;
        this.field_149155_e = new ArrayList(p_i45193_8_);
        if (p_i45193_9_ != null) {
            this.field_149152_f = (float)p_i45193_9_.xCoord;
            this.field_149153_g = (float)p_i45193_9_.yCoord;
            this.field_149159_h = (float)p_i45193_9_.zCoord;
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149158_a = p_148837_1_.readFloat();
        this.field_149156_b = p_148837_1_.readFloat();
        this.field_149157_c = p_148837_1_.readFloat();
        this.field_149154_d = p_148837_1_.readFloat();
        final int var2 = p_148837_1_.readInt();
        this.field_149155_e = new ArrayList(var2);
        final int var3 = (int)this.field_149158_a;
        final int var4 = (int)this.field_149156_b;
        final int var5 = (int)this.field_149157_c;
        for (int var6 = 0; var6 < var2; ++var6) {
            final int var7 = p_148837_1_.readByte() + var3;
            final int var8 = p_148837_1_.readByte() + var4;
            final int var9 = p_148837_1_.readByte() + var5;
            this.field_149155_e.add(new ChunkPosition(var7, var8, var9));
        }
        this.field_149152_f = p_148837_1_.readFloat();
        this.field_149153_g = p_148837_1_.readFloat();
        this.field_149159_h = p_148837_1_.readFloat();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeFloat((float)this.field_149158_a);
        p_148840_1_.writeFloat((float)this.field_149156_b);
        p_148840_1_.writeFloat((float)this.field_149157_c);
        p_148840_1_.writeFloat(this.field_149154_d);
        p_148840_1_.writeInt(this.field_149155_e.size());
        final int var2 = (int)this.field_149158_a;
        final int var3 = (int)this.field_149156_b;
        final int var4 = (int)this.field_149157_c;
        for (final ChunkPosition var6 : this.field_149155_e) {
            final int var7 = var6.field_151329_a - var2;
            final int var8 = var6.field_151327_b - var3;
            final int var9 = var6.field_151328_c - var4;
            p_148840_1_.writeByte(var7);
            p_148840_1_.writeByte(var8);
            p_148840_1_.writeByte(var9);
        }
        p_148840_1_.writeFloat(this.field_149152_f);
        p_148840_1_.writeFloat(this.field_149153_g);
        p_148840_1_.writeFloat(this.field_149159_h);
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleExplosion(this);
    }
    
    public float func_149149_c() {
        return this.field_149152_f;
    }
    
    public float func_149144_d() {
        return this.field_149153_g;
    }
    
    public float func_149147_e() {
        return this.field_149159_h;
    }
    
    public double func_149148_f() {
        return this.field_149158_a;
    }
    
    public double func_149143_g() {
        return this.field_149156_b;
    }
    
    public double func_149145_h() {
        return this.field_149157_c;
    }
    
    public float func_149146_i() {
        return this.field_149154_d;
    }
    
    public List func_149150_j() {
        return this.field_149155_e;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
