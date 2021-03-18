package net.minecraft.nbt;

import java.util.zip.*;
import java.io.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;

public class CompressedStreamTools
{
    private static final String __OBFID = "CL_00001226";
    
    public static NBTTagCompound readCompressed(final InputStream p_74796_0_) throws IOException {
        final DataInputStream var1 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(p_74796_0_)));
        NBTTagCompound var2;
        try {
            var2 = func_152456_a(var1, NBTSizeTracker.field_152451_a);
        }
        finally {
            var1.close();
        }
        return var2;
    }
    
    public static void writeCompressed(final NBTTagCompound p_74799_0_, final OutputStream p_74799_1_) throws IOException {
        final DataOutputStream var2 = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(p_74799_1_)));
        try {
            write(p_74799_0_, var2);
        }
        finally {
            var2.close();
        }
    }
    
    public static NBTTagCompound func_152457_a(final byte[] p_152457_0_, final NBTSizeTracker p_152457_1_) throws IOException {
        final DataInputStream var2 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(p_152457_0_))));
        NBTTagCompound var3;
        try {
            var3 = func_152456_a(var2, p_152457_1_);
        }
        finally {
            var2.close();
        }
        return var3;
    }
    
    public static byte[] compress(final NBTTagCompound p_74798_0_) throws IOException {
        final ByteArrayOutputStream var1 = new ByteArrayOutputStream();
        final DataOutputStream var2 = new DataOutputStream(new GZIPOutputStream(var1));
        try {
            write(p_74798_0_, var2);
        }
        finally {
            var2.close();
        }
        return var1.toByteArray();
    }
    
    public static void safeWrite(final NBTTagCompound p_74793_0_, final File p_74793_1_) throws IOException {
        final File var2 = new File(p_74793_1_.getAbsolutePath() + "_tmp");
        if (var2.exists()) {
            var2.delete();
        }
        write(p_74793_0_, var2);
        if (p_74793_1_.exists()) {
            p_74793_1_.delete();
        }
        if (p_74793_1_.exists()) {
            throw new IOException("Failed to delete " + p_74793_1_);
        }
        var2.renameTo(p_74793_1_);
    }
    
    public static void write(final NBTTagCompound p_74795_0_, final File p_74795_1_) throws IOException {
        final DataOutputStream var2 = new DataOutputStream(new FileOutputStream(p_74795_1_));
        try {
            write(p_74795_0_, var2);
        }
        finally {
            var2.close();
        }
    }
    
    public static NBTTagCompound read(final File p_74797_0_) throws IOException {
        return func_152458_a(p_74797_0_, NBTSizeTracker.field_152451_a);
    }
    
    public static NBTTagCompound func_152458_a(final File p_152458_0_, final NBTSizeTracker p_152458_1_) throws IOException {
        if (!p_152458_0_.exists()) {
            return null;
        }
        final DataInputStream var2 = new DataInputStream(new FileInputStream(p_152458_0_));
        NBTTagCompound var3;
        try {
            var3 = func_152456_a(var2, p_152458_1_);
        }
        finally {
            var2.close();
        }
        return var3;
    }
    
    public static NBTTagCompound read(final DataInputStream p_74794_0_) throws IOException {
        return func_152456_a(p_74794_0_, NBTSizeTracker.field_152451_a);
    }
    
    public static NBTTagCompound func_152456_a(final DataInput p_152456_0_, final NBTSizeTracker p_152456_1_) throws IOException {
        final NBTBase var2 = func_152455_a(p_152456_0_, 0, p_152456_1_);
        if (var2 instanceof NBTTagCompound) {
            return (NBTTagCompound)var2;
        }
        throw new IOException("Root tag must be a named compound tag");
    }
    
    public static void write(final NBTTagCompound p_74800_0_, final DataOutput p_74800_1_) throws IOException {
        func_150663_a(p_74800_0_, p_74800_1_);
    }
    
    private static void func_150663_a(final NBTBase p_150663_0_, final DataOutput p_150663_1_) throws IOException {
        p_150663_1_.writeByte(p_150663_0_.getId());
        if (p_150663_0_.getId() != 0) {
            p_150663_1_.writeUTF("");
            p_150663_0_.write(p_150663_1_);
        }
    }
    
    private static NBTBase func_152455_a(final DataInput p_152455_0_, final int p_152455_1_, final NBTSizeTracker p_152455_2_) throws IOException {
        final byte var3 = p_152455_0_.readByte();
        if (var3 == 0) {
            return new NBTTagEnd();
        }
        p_152455_0_.readUTF();
        final NBTBase var4 = NBTBase.func_150284_a(var3);
        try {
            var4.func_152446_a(p_152455_0_, p_152455_1_, p_152455_2_);
            return var4;
        }
        catch (IOException var6) {
            final CrashReport var5 = CrashReport.makeCrashReport(var6, "Loading NBT data");
            final CrashReportCategory var7 = var5.makeCategory("NBT Tag");
            var7.addCrashSection("Tag name", "[UNNAMED TAG]");
            var7.addCrashSection("Tag type", var3);
            throw new ReportedException(var5);
        }
    }
}
