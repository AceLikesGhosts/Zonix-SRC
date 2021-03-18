package net.minecraft.util;

import java.util.*;
import com.google.common.base.*;
import com.google.common.collect.*;

public class ObjectIntIdentityMap implements IObjectIntIterable
{
    private IdentityHashMap field_148749_a;
    private List field_148748_b;
    private static final String __OBFID = "CL_00001203";
    
    public ObjectIntIdentityMap() {
        this.field_148749_a = new IdentityHashMap(512);
        this.field_148748_b = Lists.newArrayList();
    }
    
    public void func_148746_a(final Object p_148746_1_, final int p_148746_2_) {
        this.field_148749_a.put(p_148746_1_, p_148746_2_);
        while (this.field_148748_b.size() <= p_148746_2_) {
            this.field_148748_b.add(null);
        }
        this.field_148748_b.set(p_148746_2_, p_148746_1_);
    }
    
    public int func_148747_b(final Object p_148747_1_) {
        final Integer var2 = this.field_148749_a.get(p_148747_1_);
        return (var2 == null) ? -1 : var2;
    }
    
    public Object func_148745_a(final int p_148745_1_) {
        return (p_148745_1_ >= 0 && p_148745_1_ < this.field_148748_b.size()) ? this.field_148748_b.get(p_148745_1_) : null;
    }
    
    @Override
    public Iterator iterator() {
        return (Iterator)Iterators.filter((Iterator)this.field_148748_b.iterator(), Predicates.notNull());
    }
    
    public boolean func_148744_b(final int p_148744_1_) {
        return this.func_148745_a(p_148744_1_) != null;
    }
}
