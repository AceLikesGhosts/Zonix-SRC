package net.minecraft.network;

import net.minecraft.nbt.*;
import net.minecraft.item.*;
import com.google.common.base.*;
import java.nio.*;
import java.io.*;
import java.nio.channels.*;
import io.netty.buffer.*;
import java.nio.charset.*;
import io.netty.util.*;

public class PacketBuffer extends ByteBuf
{
    private final ByteBuf field_150794_a;
    private static final String __OBFID = "CL_00001251";
    
    public PacketBuffer(final ByteBuf p_i45154_1_) {
        this.field_150794_a = p_i45154_1_;
    }
    
    public static int getVarIntSize(final int p_150790_0_) {
        return ((p_150790_0_ & 0xFFFFFF80) == 0x0) ? 1 : (((p_150790_0_ & 0xFFFFC000) == 0x0) ? 2 : (((p_150790_0_ & 0xFFE00000) == 0x0) ? 3 : (((p_150790_0_ & 0xF0000000) == 0x0) ? 4 : 5)));
    }
    
    public int readVarIntFromBuffer() {
        int var1 = 0;
        int var2 = 0;
        byte var3;
        do {
            var3 = this.readByte();
            var1 |= (var3 & 0x7F) << var2++ * 7;
            if (var2 > 5) {
                throw new RuntimeException("VarInt too big");
            }
        } while ((var3 & 0x80) == 0x80);
        return var1;
    }
    
    public void writeVarIntToBuffer(int p_150787_1_) {
        while ((p_150787_1_ & 0xFFFFFF80) != 0x0) {
            this.writeByte((p_150787_1_ & 0x7F) | 0x80);
            p_150787_1_ >>>= 7;
        }
        this.writeByte(p_150787_1_);
    }
    
    public void writeNBTTagCompoundToBuffer(final NBTTagCompound p_150786_1_) throws IOException {
        if (p_150786_1_ == null) {
            this.writeShort(-1);
        }
        else {
            final byte[] var2 = CompressedStreamTools.compress(p_150786_1_);
            this.writeShort((short)var2.length);
            this.writeBytes(var2);
        }
    }
    
    public NBTTagCompound readNBTTagCompoundFromBuffer() throws IOException {
        final short var1 = this.readShort();
        if (var1 < 0) {
            return null;
        }
        final byte[] var2 = new byte[var1];
        this.readBytes(var2);
        return CompressedStreamTools.func_152457_a(var2, new NBTSizeTracker(2097152L));
    }
    
    public void writeItemStackToBuffer(final ItemStack p_150788_1_) throws IOException {
        if (p_150788_1_ == null) {
            this.writeShort(-1);
        }
        else {
            this.writeShort(Item.getIdFromItem(p_150788_1_.getItem()));
            this.writeByte(p_150788_1_.stackSize);
            this.writeShort(p_150788_1_.getItemDamage());
            NBTTagCompound var2 = null;
            if (p_150788_1_.getItem().isDamageable() || p_150788_1_.getItem().getShareTag()) {
                var2 = p_150788_1_.stackTagCompound;
            }
            this.writeNBTTagCompoundToBuffer(var2);
        }
    }
    
    public ItemStack readItemStackFromBuffer() throws IOException {
        ItemStack var1 = null;
        final short var2 = this.readShort();
        if (var2 >= 0) {
            final byte var3 = this.readByte();
            final short var4 = this.readShort();
            var1 = new ItemStack(Item.getItemById(var2), var3, var4);
            var1.stackTagCompound = this.readNBTTagCompoundFromBuffer();
        }
        return var1;
    }
    
    public String readStringFromBuffer(final int p_150789_1_) throws IOException {
        final int var2 = this.readVarIntFromBuffer();
        if (var2 > p_150789_1_ * 4) {
            throw new IOException("The received encoded string buffer length is longer than maximum allowed (" + var2 + " > " + p_150789_1_ * 4 + ")");
        }
        if (var2 < 0) {
            throw new IOException("The received encoded string buffer length is less than zero! Weird string!");
        }
        final String var3 = new String(this.readBytes(var2).array(), Charsets.UTF_8);
        if (var3.length() > p_150789_1_) {
            throw new IOException("The received string length is longer than maximum allowed (" + var2 + " > " + p_150789_1_ + ")");
        }
        return var3;
    }
    
    public void writeStringToBuffer(final String p_150785_1_) throws IOException {
        final byte[] var2 = p_150785_1_.getBytes(Charsets.UTF_8);
        if (var2.length > 32767) {
            throw new IOException("String too big (was " + p_150785_1_.length() + " bytes encoded, max " + 32767 + ")");
        }
        this.writeVarIntToBuffer(var2.length);
        this.writeBytes(var2);
    }
    
    public int capacity() {
        return this.field_150794_a.capacity();
    }
    
    public ByteBuf capacity(final int p_capacity_1_) {
        return this.field_150794_a.capacity(p_capacity_1_);
    }
    
    public int maxCapacity() {
        return this.field_150794_a.maxCapacity();
    }
    
    public ByteBufAllocator alloc() {
        return this.field_150794_a.alloc();
    }
    
    public ByteOrder order() {
        return this.field_150794_a.order();
    }
    
    public ByteBuf order(final ByteOrder p_order_1_) {
        return this.field_150794_a.order(p_order_1_);
    }
    
    public ByteBuf unwrap() {
        return this.field_150794_a.unwrap();
    }
    
    public boolean isDirect() {
        return this.field_150794_a.isDirect();
    }
    
    public int readerIndex() {
        return this.field_150794_a.readerIndex();
    }
    
    public ByteBuf readerIndex(final int p_readerIndex_1_) {
        return this.field_150794_a.readerIndex(p_readerIndex_1_);
    }
    
    public int writerIndex() {
        return this.field_150794_a.writerIndex();
    }
    
    public ByteBuf writerIndex(final int p_writerIndex_1_) {
        return this.field_150794_a.writerIndex(p_writerIndex_1_);
    }
    
    public ByteBuf setIndex(final int p_setIndex_1_, final int p_setIndex_2_) {
        return this.field_150794_a.setIndex(p_setIndex_1_, p_setIndex_2_);
    }
    
    public int readableBytes() {
        return this.field_150794_a.readableBytes();
    }
    
    public int writableBytes() {
        return this.field_150794_a.writableBytes();
    }
    
    public int maxWritableBytes() {
        return this.field_150794_a.maxWritableBytes();
    }
    
    public boolean isReadable() {
        return this.field_150794_a.isReadable();
    }
    
    public boolean isReadable(final int p_isReadable_1_) {
        return this.field_150794_a.isReadable(p_isReadable_1_);
    }
    
    public boolean isWritable() {
        return this.field_150794_a.isWritable();
    }
    
    public boolean isWritable(final int p_isWritable_1_) {
        return this.field_150794_a.isWritable(p_isWritable_1_);
    }
    
    public ByteBuf clear() {
        return this.field_150794_a.clear();
    }
    
    public ByteBuf markReaderIndex() {
        return this.field_150794_a.markReaderIndex();
    }
    
    public ByteBuf resetReaderIndex() {
        return this.field_150794_a.resetReaderIndex();
    }
    
    public ByteBuf markWriterIndex() {
        return this.field_150794_a.markWriterIndex();
    }
    
    public ByteBuf resetWriterIndex() {
        return this.field_150794_a.resetWriterIndex();
    }
    
    public ByteBuf discardReadBytes() {
        return this.field_150794_a.discardReadBytes();
    }
    
    public ByteBuf discardSomeReadBytes() {
        return this.field_150794_a.discardSomeReadBytes();
    }
    
    public ByteBuf ensureWritable(final int p_ensureWritable_1_) {
        return this.field_150794_a.ensureWritable(p_ensureWritable_1_);
    }
    
    public int ensureWritable(final int p_ensureWritable_1_, final boolean p_ensureWritable_2_) {
        return this.field_150794_a.ensureWritable(p_ensureWritable_1_, p_ensureWritable_2_);
    }
    
    public boolean getBoolean(final int p_getBoolean_1_) {
        return this.field_150794_a.getBoolean(p_getBoolean_1_);
    }
    
    public byte getByte(final int p_getByte_1_) {
        return this.field_150794_a.getByte(p_getByte_1_);
    }
    
    public short getUnsignedByte(final int p_getUnsignedByte_1_) {
        return this.field_150794_a.getUnsignedByte(p_getUnsignedByte_1_);
    }
    
    public short getShort(final int p_getShort_1_) {
        return this.field_150794_a.getShort(p_getShort_1_);
    }
    
    public int getUnsignedShort(final int p_getUnsignedShort_1_) {
        return this.field_150794_a.getUnsignedShort(p_getUnsignedShort_1_);
    }
    
    public int getMedium(final int p_getMedium_1_) {
        return this.field_150794_a.getMedium(p_getMedium_1_);
    }
    
    public int getUnsignedMedium(final int p_getUnsignedMedium_1_) {
        return this.field_150794_a.getUnsignedMedium(p_getUnsignedMedium_1_);
    }
    
    public int getInt(final int p_getInt_1_) {
        return this.field_150794_a.getInt(p_getInt_1_);
    }
    
    public long getUnsignedInt(final int p_getUnsignedInt_1_) {
        return this.field_150794_a.getUnsignedInt(p_getUnsignedInt_1_);
    }
    
    public long getLong(final int p_getLong_1_) {
        return this.field_150794_a.getLong(p_getLong_1_);
    }
    
    public char getChar(final int p_getChar_1_) {
        return this.field_150794_a.getChar(p_getChar_1_);
    }
    
    public float getFloat(final int p_getFloat_1_) {
        return this.field_150794_a.getFloat(p_getFloat_1_);
    }
    
    public double getDouble(final int p_getDouble_1_) {
        return this.field_150794_a.getDouble(p_getDouble_1_);
    }
    
    public ByteBuf getBytes(final int p_getBytes_1_, final ByteBuf p_getBytes_2_) {
        return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_);
    }
    
    public ByteBuf getBytes(final int p_getBytes_1_, final ByteBuf p_getBytes_2_, final int p_getBytes_3_) {
        return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
    }
    
    public ByteBuf getBytes(final int p_getBytes_1_, final ByteBuf p_getBytes_2_, final int p_getBytes_3_, final int p_getBytes_4_) {
        return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_, p_getBytes_4_);
    }
    
    public ByteBuf getBytes(final int p_getBytes_1_, final byte[] p_getBytes_2_) {
        return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_);
    }
    
    public ByteBuf getBytes(final int p_getBytes_1_, final byte[] p_getBytes_2_, final int p_getBytes_3_, final int p_getBytes_4_) {
        return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_, p_getBytes_4_);
    }
    
    public ByteBuf getBytes(final int p_getBytes_1_, final ByteBuffer p_getBytes_2_) {
        return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_);
    }
    
    public ByteBuf getBytes(final int p_getBytes_1_, final OutputStream p_getBytes_2_, final int p_getBytes_3_) throws IOException {
        return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
    }
    
    public int getBytes(final int p_getBytes_1_, final GatheringByteChannel p_getBytes_2_, final int p_getBytes_3_) throws IOException {
        return this.field_150794_a.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
    }
    
    public ByteBuf setBoolean(final int p_setBoolean_1_, final boolean p_setBoolean_2_) {
        return this.field_150794_a.setBoolean(p_setBoolean_1_, p_setBoolean_2_);
    }
    
    public ByteBuf setByte(final int p_setByte_1_, final int p_setByte_2_) {
        return this.field_150794_a.setByte(p_setByte_1_, p_setByte_2_);
    }
    
    public ByteBuf setShort(final int p_setShort_1_, final int p_setShort_2_) {
        return this.field_150794_a.setShort(p_setShort_1_, p_setShort_2_);
    }
    
    public ByteBuf setMedium(final int p_setMedium_1_, final int p_setMedium_2_) {
        return this.field_150794_a.setMedium(p_setMedium_1_, p_setMedium_2_);
    }
    
    public ByteBuf setInt(final int p_setInt_1_, final int p_setInt_2_) {
        return this.field_150794_a.setInt(p_setInt_1_, p_setInt_2_);
    }
    
    public ByteBuf setLong(final int p_setLong_1_, final long p_setLong_2_) {
        return this.field_150794_a.setLong(p_setLong_1_, p_setLong_2_);
    }
    
    public ByteBuf setChar(final int p_setChar_1_, final int p_setChar_2_) {
        return this.field_150794_a.setChar(p_setChar_1_, p_setChar_2_);
    }
    
    public ByteBuf setFloat(final int p_setFloat_1_, final float p_setFloat_2_) {
        return this.field_150794_a.setFloat(p_setFloat_1_, p_setFloat_2_);
    }
    
    public ByteBuf setDouble(final int p_setDouble_1_, final double p_setDouble_2_) {
        return this.field_150794_a.setDouble(p_setDouble_1_, p_setDouble_2_);
    }
    
    public ByteBuf setBytes(final int p_setBytes_1_, final ByteBuf p_setBytes_2_) {
        return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_);
    }
    
    public ByteBuf setBytes(final int p_setBytes_1_, final ByteBuf p_setBytes_2_, final int p_setBytes_3_) {
        return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
    }
    
    public ByteBuf setBytes(final int p_setBytes_1_, final ByteBuf p_setBytes_2_, final int p_setBytes_3_, final int p_setBytes_4_) {
        return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_, p_setBytes_4_);
    }
    
    public ByteBuf setBytes(final int p_setBytes_1_, final byte[] p_setBytes_2_) {
        return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_);
    }
    
    public ByteBuf setBytes(final int p_setBytes_1_, final byte[] p_setBytes_2_, final int p_setBytes_3_, final int p_setBytes_4_) {
        return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_, p_setBytes_4_);
    }
    
    public ByteBuf setBytes(final int p_setBytes_1_, final ByteBuffer p_setBytes_2_) {
        return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_);
    }
    
    public int setBytes(final int p_setBytes_1_, final InputStream p_setBytes_2_, final int p_setBytes_3_) throws IOException {
        return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
    }
    
    public int setBytes(final int p_setBytes_1_, final ScatteringByteChannel p_setBytes_2_, final int p_setBytes_3_) throws IOException {
        return this.field_150794_a.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
    }
    
    public ByteBuf setZero(final int p_setZero_1_, final int p_setZero_2_) {
        return this.field_150794_a.setZero(p_setZero_1_, p_setZero_2_);
    }
    
    public boolean readBoolean() {
        return this.field_150794_a.readBoolean();
    }
    
    public byte readByte() {
        return this.field_150794_a.readByte();
    }
    
    public short readUnsignedByte() {
        return this.field_150794_a.readUnsignedByte();
    }
    
    public short readShort() {
        return this.field_150794_a.readShort();
    }
    
    public int readUnsignedShort() {
        return this.field_150794_a.readUnsignedShort();
    }
    
    public int readMedium() {
        return this.field_150794_a.readMedium();
    }
    
    public int readUnsignedMedium() {
        return this.field_150794_a.readUnsignedMedium();
    }
    
    public int readInt() {
        return this.field_150794_a.readInt();
    }
    
    public long readUnsignedInt() {
        return this.field_150794_a.readUnsignedInt();
    }
    
    public long readLong() {
        return this.field_150794_a.readLong();
    }
    
    public char readChar() {
        return this.field_150794_a.readChar();
    }
    
    public float readFloat() {
        return this.field_150794_a.readFloat();
    }
    
    public double readDouble() {
        return this.field_150794_a.readDouble();
    }
    
    public ByteBuf readBytes(final int p_readBytes_1_) {
        return this.field_150794_a.readBytes(p_readBytes_1_);
    }
    
    public ByteBuf readSlice(final int p_readSlice_1_) {
        return this.field_150794_a.readSlice(p_readSlice_1_);
    }
    
    public ByteBuf readBytes(final ByteBuf p_readBytes_1_) {
        return this.field_150794_a.readBytes(p_readBytes_1_);
    }
    
    public ByteBuf readBytes(final ByteBuf p_readBytes_1_, final int p_readBytes_2_) {
        return this.field_150794_a.readBytes(p_readBytes_1_, p_readBytes_2_);
    }
    
    public ByteBuf readBytes(final ByteBuf p_readBytes_1_, final int p_readBytes_2_, final int p_readBytes_3_) {
        return this.field_150794_a.readBytes(p_readBytes_1_, p_readBytes_2_, p_readBytes_3_);
    }
    
    public ByteBuf readBytes(final byte[] p_readBytes_1_) {
        return this.field_150794_a.readBytes(p_readBytes_1_);
    }
    
    public ByteBuf readBytes(final byte[] p_readBytes_1_, final int p_readBytes_2_, final int p_readBytes_3_) {
        return this.field_150794_a.readBytes(p_readBytes_1_, p_readBytes_2_, p_readBytes_3_);
    }
    
    public ByteBuf readBytes(final ByteBuffer p_readBytes_1_) {
        return this.field_150794_a.readBytes(p_readBytes_1_);
    }
    
    public ByteBuf readBytes(final OutputStream p_readBytes_1_, final int p_readBytes_2_) throws IOException {
        return this.field_150794_a.readBytes(p_readBytes_1_, p_readBytes_2_);
    }
    
    public int readBytes(final GatheringByteChannel p_readBytes_1_, final int p_readBytes_2_) throws IOException {
        return this.field_150794_a.readBytes(p_readBytes_1_, p_readBytes_2_);
    }
    
    public ByteBuf skipBytes(final int p_skipBytes_1_) {
        return this.field_150794_a.skipBytes(p_skipBytes_1_);
    }
    
    public ByteBuf writeBoolean(final boolean p_writeBoolean_1_) {
        return this.field_150794_a.writeBoolean(p_writeBoolean_1_);
    }
    
    public ByteBuf writeByte(final int p_writeByte_1_) {
        return this.field_150794_a.writeByte(p_writeByte_1_);
    }
    
    public ByteBuf writeShort(final int p_writeShort_1_) {
        return this.field_150794_a.writeShort(p_writeShort_1_);
    }
    
    public ByteBuf writeMedium(final int p_writeMedium_1_) {
        return this.field_150794_a.writeMedium(p_writeMedium_1_);
    }
    
    public ByteBuf writeInt(final int p_writeInt_1_) {
        return this.field_150794_a.writeInt(p_writeInt_1_);
    }
    
    public ByteBuf writeLong(final long p_writeLong_1_) {
        return this.field_150794_a.writeLong(p_writeLong_1_);
    }
    
    public ByteBuf writeChar(final int p_writeChar_1_) {
        return this.field_150794_a.writeChar(p_writeChar_1_);
    }
    
    public ByteBuf writeFloat(final float p_writeFloat_1_) {
        return this.field_150794_a.writeFloat(p_writeFloat_1_);
    }
    
    public ByteBuf writeDouble(final double p_writeDouble_1_) {
        return this.field_150794_a.writeDouble(p_writeDouble_1_);
    }
    
    public ByteBuf writeBytes(final ByteBuf p_writeBytes_1_) {
        return this.field_150794_a.writeBytes(p_writeBytes_1_);
    }
    
    public ByteBuf writeBytes(final ByteBuf p_writeBytes_1_, final int p_writeBytes_2_) {
        return this.field_150794_a.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
    }
    
    public ByteBuf writeBytes(final ByteBuf p_writeBytes_1_, final int p_writeBytes_2_, final int p_writeBytes_3_) {
        return this.field_150794_a.writeBytes(p_writeBytes_1_, p_writeBytes_2_, p_writeBytes_3_);
    }
    
    public ByteBuf writeBytes(final byte[] p_writeBytes_1_) {
        return this.field_150794_a.writeBytes(p_writeBytes_1_);
    }
    
    public ByteBuf writeBytes(final byte[] p_writeBytes_1_, final int p_writeBytes_2_, final int p_writeBytes_3_) {
        return this.field_150794_a.writeBytes(p_writeBytes_1_, p_writeBytes_2_, p_writeBytes_3_);
    }
    
    public ByteBuf writeBytes(final ByteBuffer p_writeBytes_1_) {
        return this.field_150794_a.writeBytes(p_writeBytes_1_);
    }
    
    public int writeBytes(final InputStream p_writeBytes_1_, final int p_writeBytes_2_) throws IOException {
        return this.field_150794_a.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
    }
    
    public int writeBytes(final ScatteringByteChannel p_writeBytes_1_, final int p_writeBytes_2_) throws IOException {
        return this.field_150794_a.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
    }
    
    public ByteBuf writeZero(final int p_writeZero_1_) {
        return this.field_150794_a.writeZero(p_writeZero_1_);
    }
    
    public int indexOf(final int p_indexOf_1_, final int p_indexOf_2_, final byte p_indexOf_3_) {
        return this.field_150794_a.indexOf(p_indexOf_1_, p_indexOf_2_, p_indexOf_3_);
    }
    
    public int bytesBefore(final byte p_bytesBefore_1_) {
        return this.field_150794_a.bytesBefore(p_bytesBefore_1_);
    }
    
    public int bytesBefore(final int p_bytesBefore_1_, final byte p_bytesBefore_2_) {
        return this.field_150794_a.bytesBefore(p_bytesBefore_1_, p_bytesBefore_2_);
    }
    
    public int bytesBefore(final int p_bytesBefore_1_, final int p_bytesBefore_2_, final byte p_bytesBefore_3_) {
        return this.field_150794_a.bytesBefore(p_bytesBefore_1_, p_bytesBefore_2_, p_bytesBefore_3_);
    }
    
    public int forEachByte(final ByteBufProcessor p_forEachByte_1_) {
        return this.field_150794_a.forEachByte(p_forEachByte_1_);
    }
    
    public int forEachByte(final int p_forEachByte_1_, final int p_forEachByte_2_, final ByteBufProcessor p_forEachByte_3_) {
        return this.field_150794_a.forEachByte(p_forEachByte_1_, p_forEachByte_2_, p_forEachByte_3_);
    }
    
    public int forEachByteDesc(final ByteBufProcessor p_forEachByteDesc_1_) {
        return this.field_150794_a.forEachByteDesc(p_forEachByteDesc_1_);
    }
    
    public int forEachByteDesc(final int p_forEachByteDesc_1_, final int p_forEachByteDesc_2_, final ByteBufProcessor p_forEachByteDesc_3_) {
        return this.field_150794_a.forEachByteDesc(p_forEachByteDesc_1_, p_forEachByteDesc_2_, p_forEachByteDesc_3_);
    }
    
    public ByteBuf copy() {
        return this.field_150794_a.copy();
    }
    
    public ByteBuf copy(final int p_copy_1_, final int p_copy_2_) {
        return this.field_150794_a.copy(p_copy_1_, p_copy_2_);
    }
    
    public ByteBuf slice() {
        return this.field_150794_a.slice();
    }
    
    public ByteBuf slice(final int p_slice_1_, final int p_slice_2_) {
        return this.field_150794_a.slice(p_slice_1_, p_slice_2_);
    }
    
    public ByteBuf duplicate() {
        return this.field_150794_a.duplicate();
    }
    
    public int nioBufferCount() {
        return this.field_150794_a.nioBufferCount();
    }
    
    public ByteBuffer nioBuffer() {
        return this.field_150794_a.nioBuffer();
    }
    
    public ByteBuffer nioBuffer(final int p_nioBuffer_1_, final int p_nioBuffer_2_) {
        return this.field_150794_a.nioBuffer(p_nioBuffer_1_, p_nioBuffer_2_);
    }
    
    public ByteBuffer internalNioBuffer(final int p_internalNioBuffer_1_, final int p_internalNioBuffer_2_) {
        return this.field_150794_a.internalNioBuffer(p_internalNioBuffer_1_, p_internalNioBuffer_2_);
    }
    
    public ByteBuffer[] nioBuffers() {
        return this.field_150794_a.nioBuffers();
    }
    
    public ByteBuffer[] nioBuffers(final int p_nioBuffers_1_, final int p_nioBuffers_2_) {
        return this.field_150794_a.nioBuffers(p_nioBuffers_1_, p_nioBuffers_2_);
    }
    
    public boolean hasArray() {
        return this.field_150794_a.hasArray();
    }
    
    public byte[] array() {
        return this.field_150794_a.array();
    }
    
    public int arrayOffset() {
        return this.field_150794_a.arrayOffset();
    }
    
    public boolean hasMemoryAddress() {
        return this.field_150794_a.hasMemoryAddress();
    }
    
    public long memoryAddress() {
        return this.field_150794_a.memoryAddress();
    }
    
    public String toString(final Charset p_toString_1_) {
        return this.field_150794_a.toString(p_toString_1_);
    }
    
    public String toString(final int p_toString_1_, final int p_toString_2_, final Charset p_toString_3_) {
        return this.field_150794_a.toString(p_toString_1_, p_toString_2_, p_toString_3_);
    }
    
    public int hashCode() {
        return this.field_150794_a.hashCode();
    }
    
    public boolean equals(final Object p_equals_1_) {
        return this.field_150794_a.equals(p_equals_1_);
    }
    
    public int compareTo(final ByteBuf p_compareTo_1_) {
        return this.field_150794_a.compareTo(p_compareTo_1_);
    }
    
    public String toString() {
        return this.field_150794_a.toString();
    }
    
    public ByteBuf retain(final int p_retain_1_) {
        return this.field_150794_a.retain(p_retain_1_);
    }
    
    public ByteBuf retain() {
        return this.field_150794_a.retain();
    }
    
    public int refCnt() {
        return this.field_150794_a.refCnt();
    }
    
    public boolean release() {
        return this.field_150794_a.release();
    }
    
    public boolean release(final int p_release_1_) {
        return this.field_150794_a.release(p_release_1_);
    }
}
