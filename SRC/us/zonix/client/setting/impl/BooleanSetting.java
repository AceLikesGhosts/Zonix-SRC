package us.zonix.client.setting.impl;

import us.zonix.client.setting.*;
import java.beans.*;

public final class BooleanSetting implements ISetting<Boolean>
{
    private final String name;
    private Boolean value;
    
    public BooleanSetting(final String name, final Boolean value) {
        this.value = false;
        this.name = name;
        this.value = value;
    }
    
    @ConstructorProperties({ "name" })
    public BooleanSetting(final String name) {
        this.value = false;
        this.name = name;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public Boolean getValue() {
        return this.value;
    }
    
    @Override
    public void setValue(final Boolean value) {
        this.value = value;
    }
}
