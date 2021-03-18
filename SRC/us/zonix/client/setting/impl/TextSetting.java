package us.zonix.client.setting.impl;

import us.zonix.client.setting.*;

public final class TextSetting implements ISetting<String>
{
    private final String name;
    private final String defaultValue;
    private String value;
    private boolean editing;
    private boolean valued;
    private long valueFlipTime;
    
    public TextSetting(final String name, final String value) {
        this.name = name;
        this.value = value;
        this.defaultValue = value;
    }
    
    @Override
    public void setValue(final String value) {
        this.value = value;
        if (this.value.isEmpty()) {
            this.value = this.defaultValue;
        }
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public String getDefaultValue() {
        return this.defaultValue;
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    public boolean isEditing() {
        return this.editing;
    }
    
    public boolean isValued() {
        return this.valued;
    }
    
    public long getValueFlipTime() {
        return this.valueFlipTime;
    }
    
    public void setEditing(final boolean editing) {
        this.editing = editing;
    }
    
    public void setValued(final boolean valued) {
        this.valued = valued;
    }
    
    public void setValueFlipTime(final long valueFlipTime) {
        this.valueFlipTime = valueFlipTime;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TextSetting)) {
            return false;
        }
        final TextSetting other = (TextSetting)o;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        Label_0055: {
            if (this$name == null) {
                if (other$name == null) {
                    break Label_0055;
                }
            }
            else if (this$name.equals(other$name)) {
                break Label_0055;
            }
            return false;
        }
        final Object this$defaultValue = this.getDefaultValue();
        final Object other$defaultValue = other.getDefaultValue();
        Label_0092: {
            if (this$defaultValue == null) {
                if (other$defaultValue == null) {
                    break Label_0092;
                }
            }
            else if (this$defaultValue.equals(other$defaultValue)) {
                break Label_0092;
            }
            return false;
        }
        final Object this$value = this.getValue();
        final Object other$value = other.getValue();
        if (this$value == null) {
            if (other$value == null) {
                return this.isEditing() == other.isEditing() && this.isValued() == other.isValued() && this.getValueFlipTime() == other.getValueFlipTime();
            }
        }
        else if (this$value.equals(other$value)) {
            return this.isEditing() == other.isEditing() && this.isValued() == other.isValued() && this.getValueFlipTime() == other.getValueFlipTime();
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $name = this.getName();
        result = result * 59 + (($name == null) ? 43 : $name.hashCode());
        final Object $defaultValue = this.getDefaultValue();
        result = result * 59 + (($defaultValue == null) ? 43 : $defaultValue.hashCode());
        final Object $value = this.getValue();
        result = result * 59 + (($value == null) ? 43 : $value.hashCode());
        result = result * 59 + (this.isEditing() ? 79 : 97);
        result = result * 59 + (this.isValued() ? 79 : 97);
        final long $valueFlipTime = this.getValueFlipTime();
        result = result * 59 + (int)($valueFlipTime >>> 32 ^ $valueFlipTime);
        return result;
    }
    
    @Override
    public String toString() {
        return "TextSetting(name=" + this.getName() + ", defaultValue=" + this.getDefaultValue() + ", value=" + this.getValue() + ", editing=" + this.isEditing() + ", valued=" + this.isValued() + ", valueFlipTime=" + this.getValueFlipTime() + ")";
    }
}
