package net.minecraft.network.play.server;

import net.minecraft.world.*;
import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S07PacketRespawn extends Packet
{
    private int field_149088_a;
    private EnumDifficulty field_149086_b;
    private WorldSettings.GameType field_149087_c;
    private WorldType field_149085_d;
    private static final String __OBFID = "CL_00001322";
    
    public S07PacketRespawn() {
    }
    
    public S07PacketRespawn(final int p_i45213_1_, final EnumDifficulty p_i45213_2_, final WorldType p_i45213_3_, final WorldSettings.GameType p_i45213_4_) {
        this.field_149088_a = p_i45213_1_;
        this.field_149086_b = p_i45213_2_;
        this.field_149087_c = p_i45213_4_;
        this.field_149085_d = p_i45213_3_;
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleRespawn(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149088_a = p_148837_1_.readInt();
        this.field_149086_b = EnumDifficulty.getDifficultyEnum(p_148837_1_.readUnsignedByte());
        this.field_149087_c = WorldSettings.GameType.getByID(p_148837_1_.readUnsignedByte());
        this.field_149085_d = WorldType.parseWorldType(p_148837_1_.readStringFromBuffer(16));
        if (this.field_149085_d == null) {
            this.field_149085_d = WorldType.DEFAULT;
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_149088_a);
        p_148840_1_.writeByte(this.field_149086_b.getDifficultyId());
        p_148840_1_.writeByte(this.field_149087_c.getID());
        p_148840_1_.writeStringToBuffer(this.field_149085_d.getWorldTypeName());
    }
    
    public int func_149082_c() {
        return this.field_149088_a;
    }
    
    public EnumDifficulty func_149081_d() {
        return this.field_149086_b;
    }
    
    public WorldSettings.GameType func_149083_e() {
        return this.field_149087_c;
    }
    
    public WorldType func_149080_f() {
        return this.field_149085_d;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
