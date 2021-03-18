package net.minecraft.nbt;

import java.io.*;
import java.util.*;
import net.minecraft.util.*;
import java.util.concurrent.*;
import net.minecraft.crash.*;
import org.apache.logging.log4j.*;

public class NBTTagCompound extends NBTBase
{
    private static final Logger logger;
    private Map tagMap;
    private static final String __OBFID = "CL_00001215";
    
    public NBTTagCompound() {
        this.tagMap = new HashMap();
    }
    
    @Override
    void write(final DataOutput p_74734_1_) throws IOException {
        for (final String var3 : this.tagMap.keySet()) {
            final NBTBase var4 = this.tagMap.get(var3);
            func_150298_a(var3, var4, p_74734_1_);
        }
        p_74734_1_.writeByte(0);
    }
    
    @Override
    void func_152446_a(final DataInput p_152446_1_, final int p_152446_2_, final NBTSizeTracker p_152446_3_) throws IOException {
        if (p_152446_2_ > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
        }
        this.tagMap.clear();
        byte var4;
        while ((var4 = func_152447_a(p_152446_1_, p_152446_3_)) != 0) {
            final String var5 = func_152448_b(p_152446_1_, p_152446_3_);
            p_152446_3_.func_152450_a(16 * var5.length());
            final NBTBase var6 = func_152449_a(var4, var5, p_152446_1_, p_152446_2_ + 1, p_152446_3_);
            this.tagMap.put(var5, var6);
        }
    }
    
    public Set func_150296_c() {
        return this.tagMap.keySet();
    }
    
    @Override
    public byte getId() {
        return 10;
    }
    
    public void setTag(final String p_74782_1_, final NBTBase p_74782_2_) {
        this.tagMap.put(p_74782_1_, p_74782_2_);
    }
    
    public void setByte(final String p_74774_1_, final byte p_74774_2_) {
        this.tagMap.put(p_74774_1_, new NBTTagByte(p_74774_2_));
    }
    
    public void setShort(final String p_74777_1_, final short p_74777_2_) {
        this.tagMap.put(p_74777_1_, new NBTTagShort(p_74777_2_));
    }
    
    public void setInteger(final String p_74768_1_, final int p_74768_2_) {
        this.tagMap.put(p_74768_1_, new NBTTagInt(p_74768_2_));
    }
    
    public void setLong(final String p_74772_1_, final long p_74772_2_) {
        this.tagMap.put(p_74772_1_, new NBTTagLong(p_74772_2_));
    }
    
    public void setFloat(final String p_74776_1_, final float p_74776_2_) {
        this.tagMap.put(p_74776_1_, new NBTTagFloat(p_74776_2_));
    }
    
    public void setDouble(final String p_74780_1_, final double p_74780_2_) {
        this.tagMap.put(p_74780_1_, new NBTTagDouble(p_74780_2_));
    }
    
    public void setString(final String p_74778_1_, final String p_74778_2_) {
        this.tagMap.put(p_74778_1_, new NBTTagString(p_74778_2_));
    }
    
    public void setByteArray(final String p_74773_1_, final byte[] p_74773_2_) {
        this.tagMap.put(p_74773_1_, new NBTTagByteArray(p_74773_2_));
    }
    
    public void setIntArray(final String p_74783_1_, final int[] p_74783_2_) {
        this.tagMap.put(p_74783_1_, new NBTTagIntArray(p_74783_2_));
    }
    
    public void setBoolean(final String p_74757_1_, final boolean p_74757_2_) {
        this.setByte(p_74757_1_, (byte)(p_74757_2_ ? 1 : 0));
    }
    
    public NBTBase getTag(final String p_74781_1_) {
        return this.tagMap.get(p_74781_1_);
    }
    
    public byte func_150299_b(final String p_150299_1_) {
        final NBTBase var2 = this.tagMap.get(p_150299_1_);
        return (byte)((var2 != null) ? var2.getId() : 0);
    }
    
    public boolean hasKey(final String p_74764_1_) {
        return this.tagMap.containsKey(p_74764_1_);
    }
    
    public boolean func_150297_b(final String p_150297_1_, final int p_150297_2_) {
        final byte var3 = this.func_150299_b(p_150297_1_);
        return var3 == p_150297_2_ || (p_150297_2_ == 99 && (var3 == 1 || var3 == 2 || var3 == 3 || var3 == 4 || var3 == 5 || var3 == 6));
    }
    
    public byte getByte(final String p_74771_1_) {
        try {
            return (byte)(this.tagMap.containsKey(p_74771_1_) ? this.tagMap.get(p_74771_1_).func_150290_f() : 0);
        }
        catch (ClassCastException var3) {
            return 0;
        }
    }
    
    public short getShort(final String p_74765_1_) {
        try {
            return (short)(this.tagMap.containsKey(p_74765_1_) ? this.tagMap.get(p_74765_1_).func_150289_e() : 0);
        }
        catch (ClassCastException var3) {
            return 0;
        }
    }
    
    public int getInteger(final String p_74762_1_) {
        try {
            return this.tagMap.containsKey(p_74762_1_) ? this.tagMap.get(p_74762_1_).func_150287_d() : 0;
        }
        catch (ClassCastException var3) {
            return 0;
        }
    }
    
    public long getLong(final String p_74763_1_) {
        try {
            return this.tagMap.containsKey(p_74763_1_) ? this.tagMap.get(p_74763_1_).func_150291_c() : 0L;
        }
        catch (ClassCastException var3) {
            return 0L;
        }
    }
    
    public float getFloat(final String p_74760_1_) {
        try {
            return this.tagMap.containsKey(p_74760_1_) ? this.tagMap.get(p_74760_1_).func_150288_h() : 0.0f;
        }
        catch (ClassCastException var3) {
            return 0.0f;
        }
    }
    
    public double getDouble(final String p_74769_1_) {
        try {
            return this.tagMap.containsKey(p_74769_1_) ? this.tagMap.get(p_74769_1_).func_150286_g() : 0.0;
        }
        catch (ClassCastException var3) {
            return 0.0;
        }
    }
    
    public String getString(final String p_74779_1_) {
        try {
            return this.tagMap.containsKey(p_74779_1_) ? this.tagMap.get(p_74779_1_).func_150285_a_() : "";
        }
        catch (ClassCastException var3) {
            return "";
        }
    }
    
    public byte[] getByteArray(final String p_74770_1_) {
        try {
            return this.tagMap.containsKey(p_74770_1_) ? this.tagMap.get(p_74770_1_).func_150292_c() : new byte[0];
        }
        catch (ClassCastException var3) {
            throw new ReportedException(this.createCrashReport(p_74770_1_, 7, var3));
        }
    }
    
    public int[] getIntArray(final String p_74759_1_) {
        try {
            return this.tagMap.containsKey(p_74759_1_) ? this.tagMap.get(p_74759_1_).func_150302_c() : new int[0];
        }
        catch (ClassCastException var3) {
            throw new ReportedException(this.createCrashReport(p_74759_1_, 11, var3));
        }
    }
    
    public NBTTagCompound getCompoundTag(final String p_74775_1_) {
        try {
            return this.tagMap.containsKey(p_74775_1_) ? this.tagMap.get(p_74775_1_) : new NBTTagCompound();
        }
        catch (ClassCastException var3) {
            throw new ReportedException(this.createCrashReport(p_74775_1_, 10, var3));
        }
    }
    
    public NBTTagList getTagList(final String p_150295_1_, final int p_150295_2_) {
        try {
            if (this.func_150299_b(p_150295_1_) != 9) {
                return new NBTTagList();
            }
            final NBTTagList var3 = this.tagMap.get(p_150295_1_);
            return (var3.tagCount() > 0 && var3.func_150303_d() != p_150295_2_) ? new NBTTagList() : var3;
        }
        catch (ClassCastException var4) {
            throw new ReportedException(this.createCrashReport(p_150295_1_, 9, var4));
        }
    }
    
    public boolean getBoolean(final String p_74767_1_) {
        return this.getByte(p_74767_1_) != 0;
    }
    
    public void removeTag(final String p_82580_1_) {
        this.tagMap.remove(p_82580_1_);
    }
    
    @Override
    public String toString() {
        String var1 = "{";
        for (final String var3 : this.tagMap.keySet()) {
            var1 = var1 + var3 + ':' + this.tagMap.get(var3) + ',';
        }
        return var1 + "}";
    }
    
    public boolean hasNoTags() {
        return this.tagMap.isEmpty();
    }
    
    private CrashReport createCrashReport(final String p_82581_1_, final int p_82581_2_, final ClassCastException p_82581_3_) {
        final CrashReport var4 = CrashReport.makeCrashReport(p_82581_3_, "Reading NBT data");
        final CrashReportCategory var5 = var4.makeCategoryDepth("Corrupt NBT tag", 1);
        var5.addCrashSectionCallable("Tag type found", new Callable() {
            private static final String __OBFID = "CL_00001216";
            
            @Override
            public String call() {
                return NBTBase.NBTTypes[NBTTagCompound.this.tagMap.get(p_82581_1_).getId()];
            }
        });
        var5.addCrashSectionCallable("Tag type expected", new Callable() {
            private static final String __OBFID = "CL_00001217";
            
            @Override
            public String call() {
                return NBTBase.NBTTypes[p_82581_2_];
            }
        });
        var5.addCrashSection("Tag name", p_82581_1_);
        return var4;
    }
    
    @Override
    public NBTBase copy() {
        final NBTTagCompound var1 = new NBTTagCompound();
        for (final String var3 : this.tagMap.keySet()) {
            var1.setTag(var3, this.tagMap.get(var3).copy());
        }
        return var1;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (super.equals(p_equals_1_)) {
            final NBTTagCompound var2 = (NBTTagCompound)p_equals_1_;
            return this.tagMap.entrySet().equals(var2.tagMap.entrySet());
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ this.tagMap.hashCode();
    }
    
    private static void func_150298_a(final String p_150298_0_, final NBTBase p_150298_1_, final DataOutput p_150298_2_) throws IOException {
        p_150298_2_.writeByte(p_150298_1_.getId());
        if (p_150298_1_.getId() != 0) {
            p_150298_2_.writeUTF(p_150298_0_);
            p_150298_1_.write(p_150298_2_);
        }
    }
    
    private static byte func_152447_a(final DataInput p_152447_0_, final NBTSizeTracker p_152447_1_) throws IOException {
        return p_152447_0_.readByte();
    }
    
    private static String func_152448_b(final DataInput p_152448_0_, final NBTSizeTracker p_152448_1_) throws IOException {
        return p_152448_0_.readUTF();
    }
    
    static NBTBase func_152449_a(final byte p_152449_0_, final String p_152449_1_, final DataInput p_152449_2_, final int p_152449_3_, final NBTSizeTracker p_152449_4_) {
        final NBTBase var5 = NBTBase.func_150284_a(p_152449_0_);
        try {
            var5.func_152446_a(p_152449_2_, p_152449_3_, p_152449_4_);
            return var5;
        }
        catch (IOException var7) {
            final CrashReport var6 = CrashReport.makeCrashReport(var7, "Loading NBT data");
            final CrashReportCategory var8 = var6.makeCategory("NBT Tag");
            var8.addCrashSection("Tag name", p_152449_1_);
            var8.addCrashSection("Tag type", p_152449_0_);
            throw new ReportedException(var6);
        }
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
