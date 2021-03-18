package net.minecraft.network.play.server;

import java.io.*;
import java.util.zip.*;
import net.minecraft.network.play.*;
import net.minecraft.world.chunk.storage.*;
import net.minecraft.world.chunk.*;
import net.minecraft.network.*;

public class S21PacketChunkData extends Packet
{
    private int field_149284_a;
    private int field_149282_b;
    private int field_149283_c;
    private int field_149280_d;
    private byte[] field_149281_e;
    private byte[] field_149278_f;
    private boolean field_149279_g;
    private int field_149285_h;
    private static byte[] field_149286_i;
    private static final String __OBFID = "CL_00001304";
    
    public S21PacketChunkData() {
    }
    
    public S21PacketChunkData(final Chunk p_i45196_1_, final boolean p_i45196_2_, final int p_i45196_3_) {
        this.field_149284_a = p_i45196_1_.xPosition;
        this.field_149282_b = p_i45196_1_.zPosition;
        this.field_149279_g = p_i45196_2_;
        final Extracted var4 = func_149269_a(p_i45196_1_, p_i45196_2_, p_i45196_3_);
        final Deflater var5 = new Deflater(-1);
        this.field_149280_d = var4.field_150281_c;
        this.field_149283_c = var4.field_150280_b;
        try {
            this.field_149278_f = var4.field_150282_a;
            var5.setInput(var4.field_150282_a, 0, var4.field_150282_a.length);
            var5.finish();
            this.field_149281_e = new byte[var4.field_150282_a.length];
            this.field_149285_h = var5.deflate(this.field_149281_e);
        }
        finally {
            var5.end();
        }
    }
    
    public static int func_149275_c() {
        return 196864;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149284_a = p_148837_1_.readInt();
        this.field_149282_b = p_148837_1_.readInt();
        this.field_149279_g = p_148837_1_.readBoolean();
        this.field_149283_c = p_148837_1_.readShort();
        this.field_149280_d = p_148837_1_.readShort();
        this.field_149285_h = p_148837_1_.readInt();
        if (S21PacketChunkData.field_149286_i.length < this.field_149285_h) {
            S21PacketChunkData.field_149286_i = new byte[this.field_149285_h];
        }
        p_148837_1_.readBytes(S21PacketChunkData.field_149286_i, 0, this.field_149285_h);
        int var2 = 0;
        for (int var3 = 0; var3 < 16; ++var3) {
            var2 += (this.field_149283_c >> var3 & 0x1);
        }
        int var3 = 12288 * var2;
        if (this.field_149279_g) {
            var3 += 256;
        }
        this.field_149278_f = new byte[var3];
        final Inflater var4 = new Inflater();
        var4.setInput(S21PacketChunkData.field_149286_i, 0, this.field_149285_h);
        try {
            var4.inflate(this.field_149278_f);
        }
        catch (DataFormatException var5) {
            throw new IOException("Bad compressed data format");
        }
        finally {
            var4.end();
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeInt(this.field_149284_a);
        p_148840_1_.writeInt(this.field_149282_b);
        p_148840_1_.writeBoolean(this.field_149279_g);
        p_148840_1_.writeShort((short)(this.field_149283_c & 0xFFFF));
        p_148840_1_.writeShort((short)(this.field_149280_d & 0xFFFF));
        p_148840_1_.writeInt(this.field_149285_h);
        p_148840_1_.writeBytes(this.field_149281_e, 0, this.field_149285_h);
    }
    
    public void processPacket(final INetHandlerPlayClient p_148833_1_) {
        p_148833_1_.handleChunkData(this);
    }
    
    @Override
    public String serialize() {
        return String.format("x=%d, z=%d, full=%b, sects=%d, add=%d, size=%d", this.field_149284_a, this.field_149282_b, this.field_149279_g, this.field_149283_c, this.field_149280_d, this.field_149285_h);
    }
    
    public byte[] func_149272_d() {
        return this.field_149278_f;
    }
    
    public static Extracted func_149269_a(final Chunk p_149269_0_, final boolean p_149269_1_, final int p_149269_2_) {
        int var3 = 0;
        final ExtendedBlockStorage[] var4 = p_149269_0_.getBlockStorageArray();
        int var5 = 0;
        final Extracted var6 = new Extracted();
        final byte[] var7 = S21PacketChunkData.field_149286_i;
        if (p_149269_1_) {
            p_149269_0_.sendUpdates = true;
        }
        for (int var8 = 0; var8 < var4.length; ++var8) {
            if (var4[var8] != null && (!p_149269_1_ || !var4[var8].isEmpty()) && (p_149269_2_ & 1 << var8) != 0x0) {
                final Extracted extracted = var6;
                extracted.field_150280_b |= 1 << var8;
                if (var4[var8].getBlockMSBArray() != null) {
                    final Extracted extracted2 = var6;
                    extracted2.field_150281_c |= 1 << var8;
                    ++var5;
                }
            }
        }
        for (int var8 = 0; var8 < var4.length; ++var8) {
            if (var4[var8] != null && (!p_149269_1_ || !var4[var8].isEmpty()) && (p_149269_2_ & 1 << var8) != 0x0) {
                final byte[] var9 = var4[var8].getBlockLSBArray();
                System.arraycopy(var9, 0, var7, var3, var9.length);
                var3 += var9.length;
            }
        }
        for (int var8 = 0; var8 < var4.length; ++var8) {
            if (var4[var8] != null && (!p_149269_1_ || !var4[var8].isEmpty()) && (p_149269_2_ & 1 << var8) != 0x0) {
                final NibbleArray var10 = var4[var8].getMetadataArray();
                System.arraycopy(var10.data, 0, var7, var3, var10.data.length);
                var3 += var10.data.length;
            }
        }
        for (int var8 = 0; var8 < var4.length; ++var8) {
            if (var4[var8] != null && (!p_149269_1_ || !var4[var8].isEmpty()) && (p_149269_2_ & 1 << var8) != 0x0) {
                final NibbleArray var10 = var4[var8].getBlocklightArray();
                System.arraycopy(var10.data, 0, var7, var3, var10.data.length);
                var3 += var10.data.length;
            }
        }
        if (!p_149269_0_.worldObj.provider.hasNoSky) {
            for (int var8 = 0; var8 < var4.length; ++var8) {
                if (var4[var8] != null && (!p_149269_1_ || !var4[var8].isEmpty()) && (p_149269_2_ & 1 << var8) != 0x0) {
                    final NibbleArray var10 = var4[var8].getSkylightArray();
                    System.arraycopy(var10.data, 0, var7, var3, var10.data.length);
                    var3 += var10.data.length;
                }
            }
        }
        if (var5 > 0) {
            for (int var8 = 0; var8 < var4.length; ++var8) {
                if (var4[var8] != null && (!p_149269_1_ || !var4[var8].isEmpty()) && var4[var8].getBlockMSBArray() != null && (p_149269_2_ & 1 << var8) != 0x0) {
                    final NibbleArray var10 = var4[var8].getBlockMSBArray();
                    System.arraycopy(var10.data, 0, var7, var3, var10.data.length);
                    var3 += var10.data.length;
                }
            }
        }
        if (p_149269_1_) {
            final byte[] var11 = p_149269_0_.getBiomeArray();
            System.arraycopy(var11, 0, var7, var3, var11.length);
            var3 += var11.length;
        }
        System.arraycopy(var7, 0, var6.field_150282_a = new byte[var3], 0, var3);
        return var6;
    }
    
    public int func_149273_e() {
        return this.field_149284_a;
    }
    
    public int func_149271_f() {
        return this.field_149282_b;
    }
    
    public int func_149276_g() {
        return this.field_149283_c;
    }
    
    public int func_149270_h() {
        return this.field_149280_d;
    }
    
    public boolean func_149274_i() {
        return this.field_149279_g;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerPlayClient)p_148833_1_);
    }
    
    static {
        S21PacketChunkData.field_149286_i = new byte[196864];
    }
    
    public static class Extracted
    {
        public byte[] field_150282_a;
        public int field_150280_b;
        public int field_150281_c;
        private static final String __OBFID = "CL_00001305";
    }
}
