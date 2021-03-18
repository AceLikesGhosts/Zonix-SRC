package us.zonix.client.module.impl;

import us.zonix.client.module.modules.*;
import us.zonix.client.setting.impl.*;

public final class FPSBoost extends AbstractModule
{
    public static FloatSetting SMART_PERFORMANCE;
    public static FloatSetting CHUNK_LOADING;
    public static BooleanSetting LIGHTING;
    public static BooleanSetting CHAT_SHADOW;
    public static BooleanSetting CLEAR_GLASS;
    public static BooleanSetting SHINY_POTS;
    public static BooleanSetting ITEM_GLINT;
    public static BooleanSetting FAST_CHAT;
    public static BooleanSetting WEATHER;
    
    public FPSBoost() {
        super("FPS Boost");
        this.addSetting(new LabelSetting("General Settings"));
        this.addSetting(FPSBoost.SMART_PERFORMANCE);
        this.addSetting(FPSBoost.CHUNK_LOADING);
        this.addSetting(FPSBoost.CLEAR_GLASS);
        this.addSetting(FPSBoost.CHAT_SHADOW);
        this.addSetting(FPSBoost.ITEM_GLINT);
        this.addSetting(FPSBoost.SHINY_POTS);
        this.addSetting(FPSBoost.FAST_CHAT);
        this.addSetting(FPSBoost.LIGHTING);
        this.addSetting(FPSBoost.WEATHER);
    }
    
    @Override
    public void renderReal() {
    }
    
    @Override
    public void setEnabled(final boolean enabled) {
        super.setEnabled(true);
    }
    
    static {
        FPSBoost.SMART_PERFORMANCE = new FloatSetting("Smart Performance", 0.0f, 100.0f, 100.0f);
        FPSBoost.CHUNK_LOADING = new FloatSetting("Chunk Loading", 1.0f, 100.0f, 100.0f);
        FPSBoost.LIGHTING = new BooleanSetting("Lighting Updates", true);
        FPSBoost.CHAT_SHADOW = new BooleanSetting("Chat Shadow", false);
        FPSBoost.CLEAR_GLASS = new BooleanSetting("Clear Glass", false);
        FPSBoost.SHINY_POTS = new BooleanSetting("Shiny Pots", false);
        FPSBoost.ITEM_GLINT = new BooleanSetting("Item Glint", true);
        FPSBoost.FAST_CHAT = new BooleanSetting("Fast Chat", false);
        FPSBoost.WEATHER = new BooleanSetting("Weather", true);
    }
}
