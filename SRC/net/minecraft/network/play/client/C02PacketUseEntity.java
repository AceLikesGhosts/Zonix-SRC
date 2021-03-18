package net.minecraft.network.play.client;

import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.world.*;
import net.minecraft.network.*;

public class C02PacketUseEntity extends Packet
{
    private int field_149567_a;
    private Action field_149566_b;
    private static final String __OBFID = "CL_00001357";
    
    public C02PacketUseEntity() {
    }
    
    public C02PacketUseEntity(final Entity p_i45251_1_, final Action p_i45251_2_) {
        this.field_149567_a = p_i45251_1_.getEntityId();
        this.field_149566_b = p_i45251_2_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149567_a = p_148837_1_.readInt();
        this.field_149566_b = Action.field_151421_c[p_148837_1_.readByte() % Action.field_151421_c.length];
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_149567_a);
        p_148840_1_.writeByte(this.field_149566_b.field_151418_d);
    }
    
    public void processPacket(final INetHandlerPlayServer p_148833_1_) {
        p_148833_1_.processUseEntity(this);
    }
    
    public Entity func_149564_a(final World p_149564_1_) {
        return p_149564_1_.getEntityByID(this.field_149567_a);
    }
    
    public Action func_149565_c() {
        return this.field_149566_b;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayServer)p_148833_1_);
    }
    
    public enum Action
    {
        INTERACT("INTERACT", 0, 0), 
        ATTACK("ATTACK", 1, 1);
        
        private static final Action[] field_151421_c;
        private final int field_151418_d;
        private static final Action[] $VALUES;
        private static final String __OBFID = "CL_00001358";
        
        private Action(final String p_i45250_1_, final int p_i45250_2_, final int p_i45250_3_) {
            this.field_151418_d = p_i45250_3_;
        }
        
        static {
            field_151421_c = new Action[values().length];
            $VALUES = new Action[] { Action.INTERACT, Action.ATTACK };
            for (final Action var4 : values()) {
                Action.field_151421_c[var4.field_151418_d] = var4;
            }
        }
    }
}
