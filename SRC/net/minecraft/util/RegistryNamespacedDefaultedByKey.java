package net.minecraft.util;

public class RegistryNamespacedDefaultedByKey extends RegistryNamespaced
{
    private final String field_148760_d;
    private Object field_148761_e;
    private static final String __OBFID = "CL_00001196";
    
    public RegistryNamespacedDefaultedByKey(final String p_i45127_1_) {
        this.field_148760_d = p_i45127_1_;
    }
    
    @Override
    public void addObject(final int p_148756_1_, final String p_148756_2_, final Object p_148756_3_) {
        if (this.field_148760_d.equals(p_148756_2_)) {
            this.field_148761_e = p_148756_3_;
        }
        super.addObject(p_148756_1_, p_148756_2_, p_148756_3_);
    }
    
    @Override
    public Object getObject(final String p_82594_1_) {
        final Object var2 = super.getObject(p_82594_1_);
        return (var2 == null) ? this.field_148761_e : var2;
    }
    
    @Override
    public Object getObjectForID(final int p_148754_1_) {
        final Object var2 = super.getObjectForID(p_148754_1_);
        return (var2 == null) ? this.field_148761_e : var2;
    }
    
    @Override
    public Object getObject(final Object p_82594_1_) {
        return this.getObject((String)p_82594_1_);
    }
}
