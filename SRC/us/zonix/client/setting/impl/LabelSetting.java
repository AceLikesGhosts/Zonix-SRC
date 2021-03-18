package us.zonix.client.setting.impl;

import us.zonix.client.setting.*;

public final class LabelSetting implements ISetting<String>
{
    private final String name;
    private final String value;
    
    public LabelSetting(final String name) {
        this.value = name;
        this.name = name;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    @Override
    public void setValue(final String value) {
    }
}
