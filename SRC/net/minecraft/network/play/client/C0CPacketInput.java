package net.minecraft.network.play.client;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class C0CPacketInput extends Packet
{
    private float field_149624_a;
    private float field_149622_b;
    private boolean field_149623_c;
    private boolean field_149621_d;
    private static final String __OBFID = "CL_00001367";
    
    public C0CPacketInput() {
    }
    
    public C0CPacketInput(final float p_i45261_1_, final float p_i45261_2_, final boolean p_i45261_3_, final boolean p_i45261_4_) {
        this.field_149624_a = p_i45261_1_;
        this.field_149622_b = p_i45261_2_;
        this.field_149623_c = p_i45261_3_;
        this.field_149621_d = p_i45261_4_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149624_a = p_148837_1_.readFloat();
        this.field_149622_b = p_148837_1_.readFloat();
        this.field_149623_c = p_148837_1_.readBoolean();
        this.field_149621_d = p_148837_1_.readBoolean();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeFloat(this.field_149624_a);
        p_148840_1_.writeFloat(this.field_149622_b);
        p_148840_1_.writeBoolean(this.field_149623_c);
        p_148840_1_.writeBoolean(this.field_149621_d);
    }
    
    public void processPacket(final INetHandlerPlayServer p_148833_1_) {
        p_148833_1_.processInput(this);
    }
    
    public float func_149620_c() {
        return this.field_149624_a;
    }
    
    public float func_149616_d() {
        return this.field_149622_b;
    }
    
    public boolean func_149618_e() {
        return this.field_149623_c;
    }
    
    public boolean func_149617_f() {
        return this.field_149621_d;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayServer)p_148833_1_);
    }
}
