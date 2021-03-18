package net.minecraft.entity;

import java.util.concurrent.locks.*;
import net.minecraft.item.*;
import net.minecraft.crash.*;
import org.apache.commons.lang3.*;
import net.minecraft.network.*;
import java.io.*;
import java.util.*;
import net.minecraft.util.*;

public class DataWatcher
{
    private final Entity field_151511_a;
    private boolean isBlank;
    private static final HashMap dataTypes;
    private final Map watchedObjects;
    private boolean objectChanged;
    private ReadWriteLock lock;
    private static final String __OBFID = "CL_00001559";
    
    public DataWatcher(final Entity p_i45313_1_) {
        this.isBlank = true;
        this.watchedObjects = new HashMap();
        this.lock = new ReentrantReadWriteLock();
        this.field_151511_a = p_i45313_1_;
    }
    
    public void addObject(final int p_75682_1_, final Object p_75682_2_) {
        final Integer var3 = DataWatcher.dataTypes.get(p_75682_2_.getClass());
        if (var3 == null) {
            throw new IllegalArgumentException("Unknown data type: " + p_75682_2_.getClass());
        }
        if (p_75682_1_ > 31) {
            throw new IllegalArgumentException("Data value id is too big with " + p_75682_1_ + "! (Max is " + 31 + ")");
        }
        if (this.watchedObjects.containsKey(p_75682_1_)) {
            throw new IllegalArgumentException("Duplicate id value for " + p_75682_1_ + "!");
        }
        final WatchableObject var4 = new WatchableObject(var3, p_75682_1_, p_75682_2_);
        this.lock.writeLock().lock();
        this.watchedObjects.put(p_75682_1_, var4);
        this.lock.writeLock().unlock();
        this.isBlank = false;
    }
    
    public void addObjectByDataType(final int p_82709_1_, final int p_82709_2_) {
        final WatchableObject var3 = new WatchableObject(p_82709_2_, p_82709_1_, null);
        this.lock.writeLock().lock();
        this.watchedObjects.put(p_82709_1_, var3);
        this.lock.writeLock().unlock();
        this.isBlank = false;
    }
    
    public byte getWatchableObjectByte(final int p_75683_1_) {
        return (byte)this.getWatchedObject(p_75683_1_).getObject();
    }
    
    public short getWatchableObjectShort(final int p_75693_1_) {
        return (short)this.getWatchedObject(p_75693_1_).getObject();
    }
    
    public int getWatchableObjectInt(final int p_75679_1_) {
        return (int)this.getWatchedObject(p_75679_1_).getObject();
    }
    
    public float getWatchableObjectFloat(final int p_111145_1_) {
        return (float)this.getWatchedObject(p_111145_1_).getObject();
    }
    
    public String getWatchableObjectString(final int p_75681_1_) {
        return (String)this.getWatchedObject(p_75681_1_).getObject();
    }
    
    public ItemStack getWatchableObjectItemStack(final int p_82710_1_) {
        return (ItemStack)this.getWatchedObject(p_82710_1_).getObject();
    }
    
    private WatchableObject getWatchedObject(final int p_75691_1_) {
        this.lock.readLock().lock();
        WatchableObject var2;
        try {
            var2 = this.watchedObjects.get(p_75691_1_);
        }
        catch (Throwable var4) {
            final CrashReport var3 = CrashReport.makeCrashReport(var4, "Getting synched entity data");
            final CrashReportCategory var5 = var3.makeCategory("Synched entity data");
            var5.addCrashSection("Data ID", p_75691_1_);
            throw new ReportedException(var3);
        }
        this.lock.readLock().unlock();
        return var2;
    }
    
    public void updateObject(final int p_75692_1_, final Object p_75692_2_) {
        final WatchableObject var3 = this.getWatchedObject(p_75692_1_);
        if (ObjectUtils.notEqual(p_75692_2_, var3.getObject())) {
            var3.setObject(p_75692_2_);
            this.field_151511_a.func_145781_i(p_75692_1_);
            var3.setWatched(true);
            this.objectChanged = true;
        }
    }
    
    public void setObjectWatched(final int p_82708_1_) {
        this.getWatchedObject(p_82708_1_).watched = true;
        this.objectChanged = true;
    }
    
    public boolean hasChanges() {
        return this.objectChanged;
    }
    
    public static void writeWatchedListToPacketBuffer(final List p_151507_0_, final PacketBuffer p_151507_1_) throws IOException {
        if (p_151507_0_ != null) {
            for (final WatchableObject var3 : p_151507_0_) {
                writeWatchableObjectToPacketBuffer(p_151507_1_, var3);
            }
        }
        p_151507_1_.writeByte(127);
    }
    
    public List getChanged() {
        ArrayList var1 = null;
        if (this.objectChanged) {
            this.lock.readLock().lock();
            for (final WatchableObject var3 : this.watchedObjects.values()) {
                if (var3.isWatched()) {
                    var3.setWatched(false);
                    if (var1 == null) {
                        var1 = new ArrayList();
                    }
                    var1.add(var3);
                }
            }
            this.lock.readLock().unlock();
        }
        this.objectChanged = false;
        return var1;
    }
    
    public void func_151509_a(final PacketBuffer p_151509_1_) throws IOException {
        this.lock.readLock().lock();
        for (final WatchableObject var3 : this.watchedObjects.values()) {
            writeWatchableObjectToPacketBuffer(p_151509_1_, var3);
        }
        this.lock.readLock().unlock();
        p_151509_1_.writeByte(127);
    }
    
    public List getAllWatched() {
        ArrayList var1 = null;
        this.lock.readLock().lock();
        for (final WatchableObject var3 : this.watchedObjects.values()) {
            if (var1 == null) {
                var1 = new ArrayList();
            }
            var1.add(var3);
        }
        this.lock.readLock().unlock();
        return var1;
    }
    
    private static void writeWatchableObjectToPacketBuffer(final PacketBuffer p_151510_0_, final WatchableObject p_151510_1_) throws IOException {
        final int var2 = (p_151510_1_.getObjectType() << 5 | (p_151510_1_.getDataValueId() & 0x1F)) & 0xFF;
        p_151510_0_.writeByte(var2);
        switch (p_151510_1_.getObjectType()) {
            case 0: {
                p_151510_0_.writeByte((byte)p_151510_1_.getObject());
                break;
            }
            case 1: {
                p_151510_0_.writeShort((short)p_151510_1_.getObject());
                break;
            }
            case 2: {
                p_151510_0_.writeInt((int)p_151510_1_.getObject());
                break;
            }
            case 3: {
                p_151510_0_.writeFloat((float)p_151510_1_.getObject());
                break;
            }
            case 4: {
                p_151510_0_.writeStringToBuffer((String)p_151510_1_.getObject());
                break;
            }
            case 5: {
                final ItemStack var3 = (ItemStack)p_151510_1_.getObject();
                p_151510_0_.writeItemStackToBuffer(var3);
                break;
            }
            case 6: {
                final ChunkCoordinates var4 = (ChunkCoordinates)p_151510_1_.getObject();
                p_151510_0_.writeInt(var4.posX);
                p_151510_0_.writeInt(var4.posY);
                p_151510_0_.writeInt(var4.posZ);
                break;
            }
        }
    }
    
    public static List readWatchedListFromPacketBuffer(final PacketBuffer p_151508_0_) throws IOException {
        ArrayList var1 = null;
        for (byte var2 = p_151508_0_.readByte(); var2 != 127; var2 = p_151508_0_.readByte()) {
            if (var1 == null) {
                var1 = new ArrayList();
            }
            final int var3 = (var2 & 0xE0) >> 5;
            final int var4 = var2 & 0x1F;
            WatchableObject var5 = null;
            switch (var3) {
                case 0: {
                    var5 = new WatchableObject(var3, var4, p_151508_0_.readByte());
                    break;
                }
                case 1: {
                    var5 = new WatchableObject(var3, var4, p_151508_0_.readShort());
                    break;
                }
                case 2: {
                    var5 = new WatchableObject(var3, var4, p_151508_0_.readInt());
                    break;
                }
                case 3: {
                    var5 = new WatchableObject(var3, var4, p_151508_0_.readFloat());
                    break;
                }
                case 4: {
                    var5 = new WatchableObject(var3, var4, p_151508_0_.readStringFromBuffer(32767));
                    break;
                }
                case 5: {
                    var5 = new WatchableObject(var3, var4, p_151508_0_.readItemStackFromBuffer());
                    break;
                }
                case 6: {
                    final int var6 = p_151508_0_.readInt();
                    final int var7 = p_151508_0_.readInt();
                    final int var8 = p_151508_0_.readInt();
                    var5 = new WatchableObject(var3, var4, new ChunkCoordinates(var6, var7, var8));
                    break;
                }
            }
            var1.add(var5);
        }
        return var1;
    }
    
    public void updateWatchedObjectsFromList(final List p_75687_1_) {
        this.lock.writeLock().lock();
        for (final WatchableObject var3 : p_75687_1_) {
            final WatchableObject var4 = this.watchedObjects.get(var3.getDataValueId());
            if (var4 != null) {
                var4.setObject(var3.getObject());
                this.field_151511_a.func_145781_i(var3.getDataValueId());
            }
        }
        this.lock.writeLock().unlock();
        this.objectChanged = true;
    }
    
    public boolean getIsBlank() {
        return this.isBlank;
    }
    
    public void func_111144_e() {
        this.objectChanged = false;
    }
    
    static {
        (dataTypes = new HashMap()).put(Byte.class, 0);
        DataWatcher.dataTypes.put(Short.class, 1);
        DataWatcher.dataTypes.put(Integer.class, 2);
        DataWatcher.dataTypes.put(Float.class, 3);
        DataWatcher.dataTypes.put(String.class, 4);
        DataWatcher.dataTypes.put(ItemStack.class, 5);
        DataWatcher.dataTypes.put(ChunkCoordinates.class, 6);
    }
    
    public static class WatchableObject
    {
        private final int objectType;
        private final int dataValueId;
        private Object watchedObject;
        private boolean watched;
        private static final String __OBFID = "CL_00001560";
        
        public WatchableObject(final int p_i1603_1_, final int p_i1603_2_, final Object p_i1603_3_) {
            this.dataValueId = p_i1603_2_;
            this.watchedObject = p_i1603_3_;
            this.objectType = p_i1603_1_;
            this.watched = true;
        }
        
        public int getDataValueId() {
            return this.dataValueId;
        }
        
        public void setObject(final Object p_75673_1_) {
            this.watchedObject = p_75673_1_;
        }
        
        public Object getObject() {
            return this.watchedObject;
        }
        
        public int getObjectType() {
            return this.objectType;
        }
        
        public boolean isWatched() {
            return this.watched;
        }
        
        public void setWatched(final boolean p_75671_1_) {
            this.watched = p_75671_1_;
        }
    }
}
