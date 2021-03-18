package net.minecraft.network.play.client;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class C16PacketClientStatus extends Packet
{
    private EnumState field_149437_a;
    private static final String __OBFID = "CL_00001348";
    
    public C16PacketClientStatus() {
    }
    
    public C16PacketClientStatus(final EnumState p_i45242_1_) {
        this.field_149437_a = p_i45242_1_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149437_a = EnumState.field_151404_e[p_148837_1_.readByte() % EnumState.field_151404_e.length];
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeByte(this.field_149437_a.field_151403_d);
    }
    
    public void processPacket(final INetHandlerPlayServer p_148833_1_) {
        p_148833_1_.processClientStatus(this);
    }
    
    public EnumState func_149435_c() {
        return this.field_149437_a;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayServer)p_148833_1_);
    }
    
    public enum EnumState
    {
        PERFORM_RESPAWN("PERFORM_RESPAWN", 0, 0), 
        REQUEST_STATS("REQUEST_STATS", 1, 1), 
        OPEN_INVENTORY_ACHIEVEMENT("OPEN_INVENTORY_ACHIEVEMENT", 2, 2);
        
        private final int field_151403_d;
        private static final EnumState[] field_151404_e;
        private static final EnumState[] $VALUES;
        private static final String __OBFID = "CL_00001349";
        
        private EnumState(final String p_i45241_1_, final int p_i45241_2_, final int p_i45241_3_) {
            this.field_151403_d = p_i45241_3_;
        }
        
        static {
            field_151404_e = new EnumState[values().length];
            $VALUES = new EnumState[] { EnumState.PERFORM_RESPAWN, EnumState.REQUEST_STATS, EnumState.OPEN_INVENTORY_ACHIEVEMENT };
            for (final EnumState var4 : values()) {
                EnumState.field_151404_e[var4.field_151403_d] = var4;
            }
        }
    }
}
