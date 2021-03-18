package us.zonix.client.setting.impl;

import us.zonix.client.setting.*;
import java.awt.*;

public final class ColorSetting implements ISetting<Integer>
{
    private final String name;
    private final Integer defaultValue;
    private int[] pickerLocation;
    private boolean draggingAlpha;
    private boolean draggingAll;
    private boolean draggingHue;
    private boolean picking;
    private boolean chroma;
    private float brightness;
    private float saturation;
    private float alpha;
    private float hue;
    private int state;
    private int value;
    
    public ColorSetting(final String name, final int defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = this.defaultValue;
        final Color color = new Color(this.defaultValue);
        this.alpha = (float)color.getAlpha();
        final float[] floats = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        this.saturation = floats[1];
        this.brightness = floats[2];
        this.hue = floats[0];
        if (this.name.equals("Background")) {
            this.saturation = 1.0f;
            this.state = 2;
        }
    }
    
    @Override
    public Integer getValue() {
        final int color = this.value;
        if (this.name.equals("Background")) {
            return (color & 0xFFFFFF) | 0x6F000000;
        }
        if (this.chroma) {
            return Color.HSBtoRGB(System.currentTimeMillis() % 1000L / 1000.0f, 0.8f, 0.8f);
        }
        return color;
    }
    
    @Override
    public void setValue(final Integer integer) {
        this.value = integer;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public Integer getDefaultValue() {
        return this.defaultValue;
    }
    
    public boolean isDraggingAlpha() {
        return this.draggingAlpha;
    }
    
    public boolean isDraggingAll() {
        return this.draggingAll;
    }
    
    public boolean isDraggingHue() {
        return this.draggingHue;
    }
    
    public boolean isPicking() {
        return this.picking;
    }
    
    public boolean isChroma() {
        return this.chroma;
    }
    
    public float getBrightness() {
        return this.brightness;
    }
    
    public float getSaturation() {
        return this.saturation;
    }
    
    public float getAlpha() {
        return this.alpha;
    }
    
    public float getHue() {
        return this.hue;
    }
    
    public int getState() {
        return this.state;
    }
    
    public int[] getPickerLocation() {
        return this.pickerLocation;
    }
    
    public void setPickerLocation(final int[] pickerLocation) {
        this.pickerLocation = pickerLocation;
    }
    
    public void setDraggingAlpha(final boolean draggingAlpha) {
        this.draggingAlpha = draggingAlpha;
    }
    
    public void setDraggingAll(final boolean draggingAll) {
        this.draggingAll = draggingAll;
    }
    
    public void setDraggingHue(final boolean draggingHue) {
        this.draggingHue = draggingHue;
    }
    
    public void setPicking(final boolean picking) {
        this.picking = picking;
    }
    
    public void setChroma(final boolean chroma) {
        this.chroma = chroma;
    }
    
    public void setBrightness(final float brightness) {
        this.brightness = brightness;
    }
    
    public void setSaturation(final float saturation) {
        this.saturation = saturation;
    }
    
    public void setAlpha(final float alpha) {
        this.alpha = alpha;
    }
    
    public void setHue(final float hue) {
        this.hue = hue;
    }
    
    public void setState(final int state) {
        this.state = state;
    }
}
