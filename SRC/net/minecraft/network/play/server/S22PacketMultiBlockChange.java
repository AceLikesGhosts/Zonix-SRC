package net.minecraft.network.play.server;

import net.minecraft.world.*;
import net.minecraft.world.chunk.*;
import net.minecraft.block.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;
import org.apache.logging.log4j.*;

public class S22PacketMultiBlockChange extends Packet
{
    private static final Logger logger;
    private ChunkCoordIntPair field_148925_b;
    private byte[] field_148926_c;
    private int field_148924_d;
    private static final String __OBFID = "CL_00001290";
    
    public S22PacketMultiBlockChange() {
    }
    
    public S22PacketMultiBlockChange(final int p_i45181_1_, final short[] p_i45181_2_, final Chunk p_i45181_3_) {
        this.field_148925_b = new ChunkCoordIntPair(p_i45181_3_.xPosition, p_i45181_3_.zPosition);
        this.field_148924_d = p_i45181_1_;
        final int var4 = 4 * p_i45181_1_;
        try {
            final ByteArrayOutputStream var5 = new ByteArrayOutputStream(var4);
            final DataOutputStream var6 = new DataOutputStream(var5);
            for (int var7 = 0; var7 < p_i45181_1_; ++var7) {
                final int var8 = p_i45181_2_[var7] >> 12 & 0xF;
                final int var9 = p_i45181_2_[var7] >> 8 & 0xF;
                final int var10 = p_i45181_2_[var7] & 0xFF;
                var6.writeShort(p_i45181_2_[var7]);
                var6.writeShort((short)((Block.getIdFromBlock(p_i45181_3_.func_150810_a(var8, var10, var9)) & 0xFFF) << 4 | (p_i45181_3_.getBlockMetadata(var8, var10, var9) & 0xF)));
            }
            this.field_148926_c = var5.toByteArray();
            if (this.field_148926_c.length != var4) {
                throw new RuntimeException("Expected length " + var4 + " doesn't match received length " + this.field_148926_c.length);
            }
        }
        catch (IOException var11) {
            S22PacketMultiBlockChange.logger.error("Couldn't create bulk block update packet", (Throwable)var11);
            this.field_148926_c = null;
        }
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_148925_b = new ChunkCoordIntPair(p_148837_1_.readInt(), p_148837_1_.readInt());
        this.field_148924_d = (p_148837_1_.readShort() & 0xFFFF);
        final int var2 = p_148837_1_.readInt();
        if (var2 > 0) {
            p_148837_1_.readBytes(this.field_148926_c = new byte[var2]);
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_148925_b.chunkXPos);
        p_148840_1_.writeInt(this.field_148925_b.chunkZPos);
        p_148840_1_.writeShort((short)this.field_148924_d);
        if (this.field_148926_c != null) {
            p_148840_1_.writeInt(this.field_148926_c.length);
            p_148840_1_.writeBytes(this.field_148926_c);
        }
        else {
            p_148840_1_.writeInt(0);
        }
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleMultiBlockChange(this);
    }
    
    @Override
    public String serialize() {
        return String.format("xc=%d, zc=%d, count=%d", this.field_148925_b.chunkXPos, this.field_148925_b.chunkZPos, this.field_148924_d);
    }
    
    public ChunkCoordIntPair func_148920_c() {
        return this.field_148925_b;
    }
    
    public byte[] func_148921_d() {
        return this.field_148926_c;
    }
    
    public int func_148922_e() {
        return this.field_148924_d;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
