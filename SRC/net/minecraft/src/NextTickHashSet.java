package net.minecraft.src;

import net.minecraft.util.*;
import net.minecraft.world.*;
import java.util.*;

public class NextTickHashSet extends AbstractSet
{
    private LongHashMap longHashMap;
    private int size;
    private HashSet emptySet;
    
    public NextTickHashSet(final Set oldSet) {
        this.longHashMap = new LongHashMap();
        this.size = 0;
        this.emptySet = new HashSet();
        this.addAll(oldSet);
    }
    
    @Override
    public int size() {
        return this.size;
    }
    
    @Override
    public boolean contains(final Object obj) {
        if (!(obj instanceof NextTickListEntry)) {
            return false;
        }
        final NextTickListEntry entry = (NextTickListEntry)obj;
        if (entry == null) {
            return false;
        }
        final long key = ChunkCoordIntPair.chunkXZ2Int(entry.xCoord >> 4, entry.zCoord >> 4);
        final HashSet set = (HashSet)this.longHashMap.getValueByKey(key);
        return set != null && set.contains(entry);
    }
    
    @Override
    public boolean add(final Object obj) {
        if (!(obj instanceof NextTickListEntry)) {
            return false;
        }
        final NextTickListEntry entry = (NextTickListEntry)obj;
        if (entry == null) {
            return false;
        }
        final long key = ChunkCoordIntPair.chunkXZ2Int(entry.xCoord >> 4, entry.zCoord >> 4);
        HashSet set = (HashSet)this.longHashMap.getValueByKey(key);
        if (set == null) {
            set = new HashSet();
            this.longHashMap.add(key, set);
        }
        final boolean added = set.add(entry);
        if (added) {
            ++this.size;
        }
        return added;
    }
    
    @Override
    public boolean remove(final Object obj) {
        if (!(obj instanceof NextTickListEntry)) {
            return false;
        }
        final NextTickListEntry entry = (NextTickListEntry)obj;
        if (entry == null) {
            return false;
        }
        final long key = ChunkCoordIntPair.chunkXZ2Int(entry.xCoord >> 4, entry.zCoord >> 4);
        final HashSet set = (HashSet)this.longHashMap.getValueByKey(key);
        if (set == null) {
            return false;
        }
        final boolean removed = set.remove(entry);
        if (removed) {
            --this.size;
        }
        return removed;
    }
    
    public Iterator getNextTickEntries(final int chunkX, final int chunkZ) {
        final HashSet set = this.getNextTickEntriesSet(chunkX, chunkZ);
        return set.iterator();
    }
    
    public HashSet getNextTickEntriesSet(final int chunkX, final int chunkZ) {
        final long key = ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ);
        HashSet set = (HashSet)this.longHashMap.getValueByKey(key);
        if (set == null) {
            set = this.emptySet;
        }
        return set;
    }
    
    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException("Not implemented");
    }
}
