package net.minecraft.entity.ai.attributes;

public abstract class BaseAttribute implements IAttribute
{
    private final String unlocalizedName;
    private final double defaultValue;
    private boolean shouldWatch;
    private static final String __OBFID = "CL_00001565";
    
    protected BaseAttribute(final String p_i1607_1_, final double p_i1607_2_) {
        this.unlocalizedName = p_i1607_1_;
        this.defaultValue = p_i1607_2_;
        if (p_i1607_1_ == null) {
            throw new IllegalArgumentException("Name cannot be null!");
        }
    }
    
    @Override
    public String getAttributeUnlocalizedName() {
        return this.unlocalizedName;
    }
    
    @Override
    public double getDefaultValue() {
        return this.defaultValue;
    }
    
    @Override
    public boolean getShouldWatch() {
        return this.shouldWatch;
    }
    
    public BaseAttribute setShouldWatch(final boolean p_111112_1_) {
        this.shouldWatch = p_111112_1_;
        return this;
    }
    
    @Override
    public int hashCode() {
        return this.unlocalizedName.hashCode();
    }
}
