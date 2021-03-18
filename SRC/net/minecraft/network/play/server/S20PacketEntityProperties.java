package net.minecraft.network.play.server;

import java.util.*;
import net.minecraft.entity.ai.attributes.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S20PacketEntityProperties extends Packet
{
    private int field_149445_a;
    private final List field_149444_b;
    private static final String __OBFID = "CL_00001341";
    
    public S20PacketEntityProperties() {
        this.field_149444_b = new ArrayList();
    }
    
    public S20PacketEntityProperties(final int p_i45236_1_, final Collection p_i45236_2_) {
        this.field_149444_b = new ArrayList();
        this.field_149445_a = p_i45236_1_;
        for (final IAttributeInstance var4 : p_i45236_2_) {
            this.field_149444_b.add(new Snapshot(var4.getAttribute().getAttributeUnlocalizedName(), var4.getBaseValue(), var4.func_111122_c()));
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149445_a = p_148837_1_.readInt();
        for (int var2 = p_148837_1_.readInt(), var3 = 0; var3 < var2; ++var3) {
            final String var4 = p_148837_1_.readStringFromBuffer(64);
            final double var5 = p_148837_1_.readDouble();
            final ArrayList var6 = new ArrayList();
            final short var7 = p_148837_1_.readShort();
            for (int var8 = 0; var8 < var7; ++var8) {
                final UUID var9 = new UUID(p_148837_1_.readLong(), p_148837_1_.readLong());
                var6.add(new AttributeModifier(var9, "Unknown synced attribute modifier", p_148837_1_.readDouble(), p_148837_1_.readByte()));
            }
            this.field_149444_b.add(new Snapshot(var4, var5, var6));
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_149445_a);
        p_148840_1_.writeInt(this.field_149444_b.size());
        for (final Snapshot var3 : this.field_149444_b) {
            p_148840_1_.writeStringToBuffer(var3.func_151409_a());
            p_148840_1_.writeDouble(var3.func_151410_b());
            p_148840_1_.writeShort(var3.func_151408_c().size());
            for (final AttributeModifier var5 : var3.func_151408_c()) {
                p_148840_1_.writeLong(var5.getID().getMostSignificantBits());
                p_148840_1_.writeLong(var5.getID().getLeastSignificantBits());
                p_148840_1_.writeDouble(var5.getAmount());
                p_148840_1_.writeByte(var5.getOperation());
            }
        }
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleEntityProperties(this);
    }
    
    public int func_149442_c() {
        return this.field_149445_a;
    }
    
    public List func_149441_d() {
        return this.field_149444_b;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
    
    public class Snapshot
    {
        private final String field_151412_b;
        private final double field_151413_c;
        private final Collection field_151411_d;
        private static final String __OBFID = "CL_00001342";
        
        public Snapshot(final String p_i45235_2_, final double p_i45235_3_, final Collection p_i45235_5_) {
            this.field_151412_b = p_i45235_2_;
            this.field_151413_c = p_i45235_3_;
            this.field_151411_d = p_i45235_5_;
        }
        
        public String func_151409_a() {
            return this.field_151412_b;
        }
        
        public double func_151410_b() {
            return this.field_151413_c;
        }
        
        public Collection func_151408_c() {
            return this.field_151411_d;
        }
    }
}
