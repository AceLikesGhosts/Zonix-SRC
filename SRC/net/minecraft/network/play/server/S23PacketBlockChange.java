package net.minecraft.network.play.server;

import net.minecraft.block.*;
import net.minecraft.world.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S23PacketBlockChange extends Packet
{
    private int field_148887_a;
    private int field_148885_b;
    private int field_148886_c;
    private Block field_148883_d;
    private int field_148884_e;
    private static final String __OBFID = "CL_00001287";
    
    public S23PacketBlockChange() {
    }
    
    public S23PacketBlockChange(final int p_i45177_1_, final int p_i45177_2_, final int p_i45177_3_, final World p_i45177_4_) {
        this.field_148887_a = p_i45177_1_;
        this.field_148885_b = p_i45177_2_;
        this.field_148886_c = p_i45177_3_;
        this.field_148883_d = p_i45177_4_.getBlock(p_i45177_1_, p_i45177_2_, p_i45177_3_);
        this.field_148884_e = p_i45177_4_.getBlockMetadata(p_i45177_1_, p_i45177_2_, p_i45177_3_);
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_148887_a = p_148837_1_.readInt();
        this.field_148885_b = p_148837_1_.readUnsignedByte();
        this.field_148886_c = p_148837_1_.readInt();
        this.field_148883_d = Block.getBlockById(p_148837_1_.readVarIntFromBuffer());
        this.field_148884_e = p_148837_1_.readUnsignedByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_148887_a);
        p_148840_1_.writeByte(this.field_148885_b);
        p_148840_1_.writeInt(this.field_148886_c);
        p_148840_1_.writeVarIntToBuffer(Block.getIdFromBlock(this.field_148883_d));
        p_148840_1_.writeByte(this.field_148884_e);
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleBlockChange(this);
    }
    
    @Override
    public String serialize() {
        return String.format("type=%d, data=%d, x=%d, y=%d, z=%d", Block.getIdFromBlock(this.field_148883_d), this.field_148884_e, this.field_148887_a, this.field_148885_b, this.field_148886_c);
    }
    
    public Block func_148880_c() {
        return this.field_148883_d;
    }
    
    public int func_148879_d() {
        return this.field_148887_a;
    }
    
    public int func_148878_e() {
        return this.field_148885_b;
    }
    
    public int func_148877_f() {
        return this.field_148886_c;
    }
    
    public int func_148881_g() {
        return this.field_148884_e;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
