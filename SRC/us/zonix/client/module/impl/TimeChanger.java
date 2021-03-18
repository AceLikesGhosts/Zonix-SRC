package us.zonix.client.module.impl;

import us.zonix.client.module.modules.*;
import us.zonix.client.setting.impl.*;

public final class TimeChanger extends AbstractModule
{
    public static final StringSetting TIME_SCALE;
    
    public TimeChanger() {
        super("TimeChanger");
        this.addSetting(new LabelSetting("General Settings"));
        this.addSetting(TimeChanger.TIME_SCALE);
    }
    
    static {
        TIME_SCALE = new StringSetting("Time", new String[] { "Natural", "Night Only", "Day Only" });
    }
}
