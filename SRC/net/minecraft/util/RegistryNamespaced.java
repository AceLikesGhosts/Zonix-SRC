package net.minecraft.util;

import com.google.common.collect.*;
import java.util.*;

public class RegistryNamespaced extends RegistrySimple implements IObjectIntIterable
{
    protected final ObjectIntIdentityMap underlyingIntegerMap;
    protected final Map field_148758_b;
    private static final String __OBFID = "CL_00001206";
    
    public RegistryNamespaced() {
        this.underlyingIntegerMap = new ObjectIntIdentityMap();
        this.field_148758_b = (Map)((BiMap)this.registryObjects).inverse();
    }
    
    public void addObject(final int p_148756_1_, final String p_148756_2_, final Object p_148756_3_) {
        this.underlyingIntegerMap.func_148746_a(p_148756_3_, p_148756_1_);
        this.putObject(ensureNamespaced(p_148756_2_), p_148756_3_);
    }
    
    @Override
    protected Map createUnderlyingMap() {
        return (Map)HashBiMap.create();
    }
    
    public Object getObject(final String p_82594_1_) {
        return super.getObject(ensureNamespaced(p_82594_1_));
    }
    
    public String getNameForObject(final Object p_148750_1_) {
        return this.field_148758_b.get(p_148750_1_);
    }
    
    public boolean containsKey(final String p_148741_1_) {
        return super.containsKey(ensureNamespaced(p_148741_1_));
    }
    
    public int getIDForObject(final Object p_148757_1_) {
        return this.underlyingIntegerMap.func_148747_b(p_148757_1_);
    }
    
    public Object getObjectForID(final int p_148754_1_) {
        return this.underlyingIntegerMap.func_148745_a(p_148754_1_);
    }
    
    @Override
    public Iterator iterator() {
        return this.underlyingIntegerMap.iterator();
    }
    
    public boolean containsID(final int p_148753_1_) {
        return this.underlyingIntegerMap.func_148744_b(p_148753_1_);
    }
    
    private static String ensureNamespaced(final String p_148755_0_) {
        return (p_148755_0_.indexOf(58) == -1) ? ("minecraft:" + p_148755_0_) : p_148755_0_;
    }
    
    @Override
    public boolean containsKey(final Object p_148741_1_) {
        return this.containsKey((String)p_148741_1_);
    }
    
    @Override
    public Object getObject(final Object p_82594_1_) {
        return this.getObject((String)p_82594_1_);
    }
}
