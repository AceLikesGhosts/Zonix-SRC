package us.zonix.client.module.modules;

import us.zonix.client.module.*;
import us.zonix.client.setting.*;
import net.minecraft.client.*;
import java.util.*;
import us.zonix.client.setting.impl.*;

public abstract class AbstractModule implements IModule
{
    private final Map<String, ISetting> settingMap;
    private final List<ISetting> sortedSettings;
    protected final Minecraft mc;
    private final String name;
    protected float x;
    protected float y;
    private int height;
    private int width;
    
    protected AbstractModule(final String name) {
        this.settingMap = new HashMap<String, ISetting>();
        this.sortedSettings = new LinkedList<ISetting>();
        this.mc = Minecraft.getMinecraft();
        this.x = 2.0f;
        this.y = 2.0f;
        this.name = name;
        this.addSetting(new BooleanSetting("Enabled", true));
    }
    
    @Override
    public boolean isEnabled() {
        return this.getBooleanSetting("Enabled").getValue();
    }
    
    @Override
    public void setEnabled(final boolean enabled) {
        this.getBooleanSetting("Enabled").setValue(Boolean.valueOf(enabled));
    }
    
    protected <T extends ISetting> void addSetting(final T t) {
        this.settingMap.put(t.getName().toLowerCase(), t);
        this.sortedSettings.add(t);
    }
    
    private <T extends ISetting> T getSetting(final String name) {
        return (T)this.settingMap.get(name.toLowerCase());
    }
    
    protected <T extends BooleanSetting> T getBooleanSetting(final String name) {
        return this.getSetting(name);
    }
    
    protected <T extends FloatSetting> T getFloatSetting(final String name) {
        return this.getSetting(name);
    }
    
    protected <T extends ColorSetting> T getColorSetting(final String name) {
        return this.getSetting(name);
    }
    
    @Override
    public Map<String, ISetting> getSettingMap() {
        return this.settingMap;
    }
    
    @Override
    public List<ISetting> getSortedSettings() {
        return this.sortedSettings;
    }
    
    public Minecraft getMc() {
        return this.mc;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public float getX() {
        return this.x;
    }
    
    @Override
    public float getY() {
        return this.y;
    }
    
    @Override
    public int getHeight() {
        return this.height;
    }
    
    @Override
    public int getWidth() {
        return this.width;
    }
    
    @Override
    public void setX(final float x) {
        this.x = x;
    }
    
    @Override
    public void setY(final float y) {
        this.y = y;
    }
    
    public void setHeight(final int height) {
        this.height = height;
    }
    
    public void setWidth(final int width) {
        this.width = width;
    }
}
