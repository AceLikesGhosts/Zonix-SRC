package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import com.google.common.collect.*;
import net.minecraft.stats.*;
import java.io.*;
import java.util.*;
import net.minecraft.network.*;

public class S37PacketStatistics extends Packet
{
    private Map field_148976_a;
    private static final String __OBFID = "CL_00001283";
    
    public S37PacketStatistics() {
    }
    
    public S37PacketStatistics(final Map p_i45173_1_) {
        this.field_148976_a = p_i45173_1_;
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleStatistics(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        final int var2 = p_148837_1_.readVarIntFromBuffer();
        this.field_148976_a = Maps.newHashMap();
        for (int var3 = 0; var3 < var2; ++var3) {
            final StatBase var4 = StatList.func_151177_a(p_148837_1_.readStringFromBuffer(32767));
            final int var5 = p_148837_1_.readVarIntFromBuffer();
            if (var4 != null) {
                this.field_148976_a.put(var4, var5);
            }
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeVarIntToBuffer(this.field_148976_a.size());
        for (final Map.Entry var3 : this.field_148976_a.entrySet()) {
            p_148840_1_.writeStringToBuffer(var3.getKey().statId);
            p_148840_1_.writeVarIntToBuffer(var3.getValue());
        }
    }
    
    @Override
    public String serialize() {
        return String.format("count=%d", this.field_148976_a.size());
    }
    
    public Map func_148974_c() {
        return this.field_148976_a;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
