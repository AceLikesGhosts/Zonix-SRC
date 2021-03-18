package us.zonix.client.module.impl;

import us.zonix.client.module.modules.*;
import us.zonix.client.setting.impl.*;

public final class GeneralSettings extends AbstractModule
{
    private static final GeneralSettings instance;
    
    private GeneralSettings() {
        super("General");
        this.addSetting(new BooleanSetting("Show HUD in 'Focus Mode'"));
        this.addSetting(new BooleanSetting("Show Hotbar in 'Focus Mode'"));
    }
    
    public static GeneralSettings getInstance() {
        return GeneralSettings.instance;
    }
    
    static {
        instance = new GeneralSettings();
    }
}
