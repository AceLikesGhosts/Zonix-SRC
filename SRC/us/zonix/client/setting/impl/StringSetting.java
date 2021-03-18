package us.zonix.client.setting.impl;

import us.zonix.client.setting.*;

public final class StringSetting implements ISetting<String>
{
    private final String[] options;
    private final String name;
    private String value;
    private int index;
    
    public StringSetting(final String name, final String... options) {
        this.name = name;
        this.options = options;
        this.value = options[0];
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    public void setValue(final int index) {
        this.value = this.options[index];
        this.index = index;
    }
    
    @Override
    public void setValue(final String value) {
        this.value = value;
        for (int i = 0; i < this.options.length; ++i) {
            final String item = this.options[i];
            if (item.equals(value)) {
                this.index = i;
                break;
            }
        }
    }
    
    public String[] getOptions() {
        return this.options;
    }
    
    public int getIndex() {
        return this.index;
    }
}
