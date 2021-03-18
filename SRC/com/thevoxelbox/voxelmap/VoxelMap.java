package com.thevoxelbox.voxelmap;

import net.minecraft.client.*;
import java.util.*;
import com.thevoxelbox.voxelmap.util.*;
import com.thevoxelbox.voxelmap.interfaces.*;

public class VoxelMap extends AbstractVoxelMap
{
    private MapSettingsManager mapOptions;
    private RadarSettingsManager radarOptions;
    private IMap map;
    private IRadar radar;
    private IColorManager colorManager;
    private IWaypointManager waypointManager;
    private IDimensionManager dimensionManager;
    private ObservableChunkChangeNotifier chunkChangeNotifier;
    
    public VoxelMap(final boolean showUnderMenus, final boolean isFair) {
        this.mapOptions = null;
        this.radarOptions = null;
        this.map = null;
        this.radar = null;
        this.colorManager = null;
        this.waypointManager = null;
        this.dimensionManager = null;
        this.chunkChangeNotifier = new ObservableChunkChangeNotifier();
        AbstractVoxelMap.instance = this;
        if (!Minecraft.getMinecraft().getResourceManager().getResourceDomains().contains("voxelmap")) {
            final AddonResourcePack voxelMapResourcePack = new AddonResourcePack("voxelmap");
            final List defaultResourcePacks = (List)ReflectionUtils.getPrivateFieldValueByType(Minecraft.getMinecraft(), Minecraft.class, List.class, 1);
            defaultResourcePacks.add(voxelMapResourcePack);
            Minecraft.getMinecraft().refreshResources();
        }
        this.mapOptions = new MapSettingsManager();
        this.mapOptions.showUnderMenus = showUnderMenus;
        this.radarOptions = new RadarSettingsManager();
        this.mapOptions.setRadarSettings(this.radarOptions);
        this.colorManager = new ColorManager(this);
        this.waypointManager = new WaypointManager(this);
        this.dimensionManager = new DimensionManager(this);
        this.map = new Map(this);
        this.mapOptions.loadAll();
    }
    
    public void onTickInGame(final Minecraft mc) {
        this.map.onTickInGame(mc);
    }
    
    @Override
    public IObservableChunkChangeNotifier getNotifier() {
        return this.chunkChangeNotifier;
    }
    
    @Override
    public MapSettingsManager getMapOptions() {
        return this.mapOptions;
    }
    
    @Override
    public RadarSettingsManager getRadarOptions() {
        return this.radarOptions;
    }
    
    @Override
    public IMap getMap() {
        return this.map;
    }
    
    @Override
    public IRadar getRadar() {
        return this.radar;
    }
    
    @Override
    public IColorManager getColorManager() {
        return this.colorManager;
    }
    
    @Override
    public IWaypointManager getWaypointManager() {
        return this.waypointManager;
    }
    
    @Override
    public IDimensionManager getDimensionManager() {
        return this.dimensionManager;
    }
    
    @Override
    public void setPermissions(final boolean hasRadarPermission, final boolean hasCavemodePermission) {
        this.map.setPermissions(hasRadarPermission, hasCavemodePermission);
    }
    
    @Override
    public void newSubWorldName(final String name) {
        this.waypointManager.newSubWorldName(name);
    }
    
    @Override
    public void newSubWorldHash(final String hash) {
        this.waypointManager.newSubWorldHash(hash);
    }
}
