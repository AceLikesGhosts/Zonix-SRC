package us.zonix.client.setting.impl;

import us.zonix.client.setting.*;

public final class FloatSetting implements ISetting<Float>
{
    private final String name;
    private final Float min;
    private final Float max;
    private Float value;
    
    public FloatSetting(final String name, final Float min, final Float max, final Float value) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.value = value;
    }
    
    @Override
    public void setValue(final Float value) {
        this.value = Math.max(this.min, Math.min(this.max, value));
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public Float getValue() {
        return this.value;
    }
    
    public Float getMin() {
        return this.min;
    }
    
    public Float getMax() {
        return this.max;
    }
}
