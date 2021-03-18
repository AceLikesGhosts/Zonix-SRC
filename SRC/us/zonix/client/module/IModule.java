package us.zonix.client.module;

import us.zonix.client.setting.*;
import java.util.*;

public interface IModule
{
    Map<String, ISetting> getSettingMap();
    
    List<ISetting> getSortedSettings();
    
    String getName();
    
    boolean isEnabled();
    
    void setEnabled(final boolean p0);
    
    int getWidth();
    
    int getHeight();
    
    float getX();
    
    float getY();
    
    void setX(final float p0);
    
    void setY(final float p0);
    
    default void renderPreview() {
        this.renderReal();
    }
    
    default void renderReal() {
    }
    
    default void onPostPlayerUpdate() {
    }
    
    default void onPrePlayerUpdate() {
    }
}
