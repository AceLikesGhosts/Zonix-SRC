package net.minecraft.network.play.server;

import java.util.*;
import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S1CPacketEntityMetadata extends Packet
{
    private int field_149379_a;
    private List field_149378_b;
    private static final String __OBFID = "CL_00001326";
    
    public S1CPacketEntityMetadata() {
    }
    
    public S1CPacketEntityMetadata(final int p_i45217_1_, final DataWatcher p_i45217_2_, final boolean p_i45217_3_) {
        this.field_149379_a = p_i45217_1_;
        if (p_i45217_3_) {
            this.field_149378_b = p_i45217_2_.getAllWatched();
        }
        else {
            this.field_149378_b = p_i45217_2_.getChanged();
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149379_a = p_148837_1_.readInt();
        this.field_149378_b = DataWatcher.readWatchedListFromPacketBuffer(p_148837_1_);
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_149379_a);
        DataWatcher.writeWatchedListToPacketBuffer(this.field_149378_b, p_148840_1_);
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleEntityMetadata(this);
    }
    
    public List func_149376_c() {
        return this.field_149378_b;
    }
    
    public int func_149375_d() {
        return this.field_149379_a;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
}
