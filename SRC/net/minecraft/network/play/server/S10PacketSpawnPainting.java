package net.minecraft.network.play.server;

import net.minecraft.entity.item.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S10PacketSpawnPainting extends Packet
{
    private int field_148973_a;
    private int field_148971_b;
    private int field_148972_c;
    private int field_148969_d;
    private int field_148970_e;
    private String field_148968_f;
    private static final String __OBFID = "CL_00001280";
    
    public S10PacketSpawnPainting() {
    }
    
    public S10PacketSpawnPainting(final EntityPainting p_i45170_1_) {
        this.field_148973_a = p_i45170_1_.getEntityId();
        this.field_148971_b = p_i45170_1_.field_146063_b;
        this.field_148972_c = p_i45170_1_.field_146064_c;
        this.field_148969_d = p_i45170_1_.field_146062_d;
        this.field_148970_e = p_i45170_1_.hangingDirection;
        this.field_148968_f = p_i45170_1_.art.title;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_148973_a = p_148837_1_.readVarIntFromBuffer();
        this.field_148968_f = p_148837_1_.readStringFromBuffer(EntityPainting.EnumArt.maxArtTitleLength);
        this.field_148971_b = p_148837_1_.readInt();
        this.field_148972_c = p_148837_1_.readInt();
        this.field_148969_d = p_148837_1_.readInt();
        this.field_148970_e = p_148837_1_.readInt();
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeVarIntToBuffer(this.field_148973_a);
        p_148840_1_.writeStringToBuffer(this.field_148968_f);
        p_148840_1_.writeInt(this.field_148971_b);
        p_148840_1_.writeInt(this.field_148972_c);
        p_148840_1_.writeInt(this.field_148969_d);
        p_148840_1_.writeInt(this.field_148970_e);
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleSpawnPainting(this);
    }
    
    @Override
    public String serialize() {
        return String.format("id=%d, type=%s, x=%d, y=%d, z=%d", this.field_148973_a, this.field_148968_f, this.field_148971_b, this.field_148972_c, this.field_148969_d);
    }
    
    public int func_148965_c() {
        return this.field_148973_a;
    }
    
    public int func_148964_d() {
        return this.field_148971_b;
    }
    
    public int func_148963_e() {
        return this.field_148972_c;
    }
    
    public int func_148962_f() {
        return this.field_148969_d;
    }
    
    public int func_148966_g() {
        return this.field_148970_e;
    }
    
    public String func_148961_h() {
        return this.field_148968_f;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
