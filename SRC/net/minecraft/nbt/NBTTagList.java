package net.minecraft.nbt;

import java.io.*;
import java.util.*;

public class NBTTagList extends NBTBase
{
    private List tagList;
    private byte tagType;
    private static final String __OBFID = "CL_00001224";
    
    public NBTTagList() {
        this.tagList = new ArrayList();
        this.tagType = 0;
    }
    
    @Override
    void write(final DataOutput p_74734_1_) throws IOException {
        if (!this.tagList.isEmpty()) {
            this.tagType = this.tagList.get(0).getId();
        }
        else {
            this.tagType = 0;
        }
        p_74734_1_.writeByte(this.tagType);
        p_74734_1_.writeInt(this.tagList.size());
        for (int var2 = 0; var2 < this.tagList.size(); ++var2) {
            this.tagList.get(var2).write(p_74734_1_);
        }
    }
    
    @Override
    void func_152446_a(final DataInput p_152446_1_, final int p_152446_2_, final NBTSizeTracker p_152446_3_) throws IOException {
        if (p_152446_2_ > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
        }
        p_152446_3_.func_152450_a(8L);
        this.tagType = p_152446_1_.readByte();
        final int var4 = p_152446_1_.readInt();
        this.tagList = new ArrayList();
        for (int var5 = 0; var5 < var4; ++var5) {
            final NBTBase var6 = NBTBase.func_150284_a(this.tagType);
            var6.func_152446_a(p_152446_1_, p_152446_2_ + 1, p_152446_3_);
            this.tagList.add(var6);
        }
    }
    
    @Override
    public byte getId() {
        return 9;
    }
    
    @Override
    public String toString() {
        String var1 = "[";
        int var2 = 0;
        for (final NBTBase var4 : this.tagList) {
            var1 = var1 + "" + var2 + ':' + var4 + ',';
            ++var2;
        }
        return var1 + "]";
    }
    
    public void appendTag(final NBTBase p_74742_1_) {
        if (this.tagType == 0) {
            this.tagType = p_74742_1_.getId();
        }
        else if (this.tagType != p_74742_1_.getId()) {
            System.err.println("WARNING: Adding mismatching tag types to tag list");
            return;
        }
        this.tagList.add(p_74742_1_);
    }
    
    public void func_150304_a(final int p_150304_1_, final NBTBase p_150304_2_) {
        if (p_150304_1_ >= 0 && p_150304_1_ < this.tagList.size()) {
            if (this.tagType == 0) {
                this.tagType = p_150304_2_.getId();
            }
            else if (this.tagType != p_150304_2_.getId()) {
                System.err.println("WARNING: Adding mismatching tag types to tag list");
                return;
            }
            this.tagList.set(p_150304_1_, p_150304_2_);
        }
        else {
            System.err.println("WARNING: index out of bounds to set tag in tag list");
        }
    }
    
    public NBTBase removeTag(final int p_74744_1_) {
        return this.tagList.remove(p_74744_1_);
    }
    
    public NBTTagCompound getCompoundTagAt(final int p_150305_1_) {
        if (p_150305_1_ >= 0 && p_150305_1_ < this.tagList.size()) {
            final NBTBase var2 = this.tagList.get(p_150305_1_);
            return (NBTTagCompound)((var2.getId() == 10) ? var2 : new NBTTagCompound());
        }
        return new NBTTagCompound();
    }
    
    public int[] func_150306_c(final int p_150306_1_) {
        if (p_150306_1_ >= 0 && p_150306_1_ < this.tagList.size()) {
            final NBTBase var2 = this.tagList.get(p_150306_1_);
            return (var2.getId() == 11) ? ((NBTTagIntArray)var2).func_150302_c() : new int[0];
        }
        return new int[0];
    }
    
    public double func_150309_d(final int p_150309_1_) {
        if (p_150309_1_ >= 0 && p_150309_1_ < this.tagList.size()) {
            final NBTBase var2 = this.tagList.get(p_150309_1_);
            return (var2.getId() == 6) ? ((NBTTagDouble)var2).func_150286_g() : 0.0;
        }
        return 0.0;
    }
    
    public float func_150308_e(final int p_150308_1_) {
        if (p_150308_1_ >= 0 && p_150308_1_ < this.tagList.size()) {
            final NBTBase var2 = this.tagList.get(p_150308_1_);
            return (var2.getId() == 5) ? ((NBTTagFloat)var2).func_150288_h() : 0.0f;
        }
        return 0.0f;
    }
    
    public String getStringTagAt(final int p_150307_1_) {
        if (p_150307_1_ >= 0 && p_150307_1_ < this.tagList.size()) {
            final NBTBase var2 = this.tagList.get(p_150307_1_);
            return (var2.getId() == 8) ? var2.func_150285_a_() : var2.toString();
        }
        return "";
    }
    
    public int tagCount() {
        return this.tagList.size();
    }
    
    @Override
    public NBTBase copy() {
        final NBTTagList var1 = new NBTTagList();
        var1.tagType = this.tagType;
        for (final NBTBase var3 : this.tagList) {
            final NBTBase var4 = var3.copy();
            var1.tagList.add(var4);
        }
        return var1;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (super.equals(p_equals_1_)) {
            final NBTTagList var2 = (NBTTagList)p_equals_1_;
            if (this.tagType == var2.tagType) {
                return this.tagList.equals(var2.tagList);
            }
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ this.tagList.hashCode();
    }
    
    public int func_150303_d() {
        return this.tagType;
    }
}
