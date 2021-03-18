package com.thevoxelbox.voxelmap;

import net.minecraft.client.*;
import net.minecraft.server.*;
import com.thevoxelbox.voxelmap.interfaces.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.entity.*;
import com.thevoxelbox.voxelmap.gui.*;
import com.thevoxelbox.voxelmap.gui.overridden.*;
import org.lwjgl.input.*;
import net.minecraft.client.renderer.*;
import net.minecraft.potion.*;
import org.lwjgl.*;
import net.minecraft.client.settings.*;
import net.minecraft.entity.*;
import java.nio.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.command.*;
import java.lang.reflect.*;
import java.net.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.gui.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.biome.*;
import net.minecraft.block.material.*;
import com.thevoxelbox.voxelmap.util.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import us.zonix.client.module.impl.*;
import org.lwjgl.opengl.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.*;
import java.util.*;

public class Map implements Runnable, IMap
{
    private final float[] lastLightBrightnessTable;
    private final int[] wbi;
    private final Object coordinateLock;
    public Minecraft game;
    public String zmodver;
    public MapSettingsManager options;
    public IRadar radar;
    public LayoutVariables layoutVariables;
    public IColorManager colorManager;
    public IWaypointManager waypointManager;
    public IDimensionManager dimensionManager;
    public Random generator;
    public int iMenu;
    public boolean fullscreenMap;
    public boolean active;
    public int zoom;
    public int mapX;
    public int mapY;
    public boolean doFullRender;
    public int lastX;
    public int lastZ;
    public int scScale;
    public float percentX;
    public float percentY;
    public boolean lastPercentXOver;
    public boolean lastPercentYOver;
    public boolean lastSquareMap;
    public int northRotate;
    public Thread zCalc;
    public boolean threading;
    boolean needSkyColor;
    int scWidth;
    int scHeight;
    MinecraftServer server;
    Long newServerTime;
    boolean checkMOTD;
    ChatLine mostRecentLine;
    private IVoxelMap master;
    private World world;
    private int worldHeight;
    private boolean haveRenderManager;
    private int availableProcessors;
    public boolean multicore;
    private MapData[] mapData;
    private MapChunkCache[] chunkCache;
    private GLBufferedImage[] map;
    private GLBufferedImage roundImage;
    private boolean imageChanged;
    private DynamicTexture lightmapTexture;
    private boolean needLight;
    private float lastGamma;
    private float lastSunBrightness;
    private float lastLightning;
    private float lastPotion;
    private int[] lastLightmapValues;
    private boolean lastBeneathRendering;
    private boolean lastAboveHorizon;
    private int lastBiome;
    private int lastSkyColor;
    private GuiScreen lastGuiScreen;
    private boolean enabled;
    private String error;
    private String[] sMenu;
    private int ztimer;
    private int heightMapFudge;
    private int timer;
    private boolean zoomChanged;
    private int lastY;
    private int lastImageX;
    private int lastImageZ;
    private boolean lastFullscreen;
    private float direction;
    private String worldName;
    private int heightMapResetHeight;
    private int heightMapResetTime;
    private FontRenderer fontRenderer;
    private int[] lightmapColors;
    private boolean worldDownloaderExists;
    private boolean lastWorldDownloading;
    private boolean tf;
    
    public Map(final IVoxelMap master) {
        this.lastLightBrightnessTable = new float[16];
        this.wbi = new int[] { "minecraftxteria".toLowerCase().hashCode(), "jacoboom100".toLowerCase().hashCode(), "Laserpigofdoom".toLowerCase().hashCode(), "DesignVenomz".toLowerCase().hashCode(), "ElectronTowel".toLowerCase().hashCode(), "Fighterbear12".toLowerCase().hashCode(), "KillmurCS".toLowerCase().hashCode() };
        this.coordinateLock = new Object();
        this.zmodver = "v1.2.0";
        this.options = null;
        this.radar = null;
        this.layoutVariables = null;
        this.colorManager = null;
        this.waypointManager = null;
        this.dimensionManager = null;
        this.generator = new Random();
        this.iMenu = 1;
        this.fullscreenMap = false;
        this.active = false;
        this.zoom = 2;
        this.mapX = 37;
        this.mapY = 37;
        this.doFullRender = true;
        this.lastX = 0;
        this.lastZ = 0;
        this.scScale = 0;
        this.lastPercentXOver = false;
        this.lastPercentYOver = false;
        this.lastSquareMap = false;
        this.northRotate = 0;
        this.zCalc = new Thread(this, "Voxelmap Map Calculation Thread");
        this.threading = this.multicore;
        this.needSkyColor = false;
        this.newServerTime = 0L;
        this.checkMOTD = false;
        this.mostRecentLine = null;
        this.world = null;
        this.worldHeight = 256;
        this.haveRenderManager = false;
        this.availableProcessors = Runtime.getRuntime().availableProcessors();
        this.multicore = (this.availableProcessors > 0);
        this.mapData = new MapData[4];
        this.chunkCache = new MapChunkCache[4];
        this.map = new GLBufferedImage[4];
        this.imageChanged = true;
        this.lightmapTexture = null;
        this.needLight = true;
        this.lastGamma = 0.0f;
        this.lastSunBrightness = 0.0f;
        this.lastLightning = 0.0f;
        this.lastPotion = 0.0f;
        this.lastLightmapValues = new int[] { -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216 };
        this.lastBeneathRendering = false;
        this.lastAboveHorizon = true;
        this.lastBiome = 0;
        this.lastSkyColor = 0;
        this.lastGuiScreen = null;
        this.enabled = true;
        this.error = "";
        this.sMenu = new String[8];
        this.ztimer = 0;
        this.heightMapFudge = 0;
        this.timer = 0;
        this.lastY = 0;
        this.lastImageX = 0;
        this.lastImageZ = 0;
        this.lastFullscreen = false;
        this.direction = 0.0f;
        this.worldName = "";
        this.heightMapResetHeight = (this.multicore ? 2 : 5);
        this.heightMapResetTime = (this.multicore ? 300 : 3000);
        this.lightmapColors = new int[256];
        this.worldDownloaderExists = false;
        this.lastWorldDownloading = false;
        this.tf = false;
        this.master = master;
        this.game = GameVariableAccessShim.getMinecraft();
        this.options = master.getMapOptions();
        this.radar = master.getRadar();
        this.colorManager = master.getColorManager();
        this.waypointManager = master.getWaypointManager();
        this.dimensionManager = master.getDimensionManager();
        this.layoutVariables = new LayoutVariables();
        try {
            NetworkUtils.enumerateInterfaces();
        }
        catch (SocketException e) {
            System.err.println("could not get network interface addresses");
            e.printStackTrace();
        }
        final ArrayList<KeyBinding> tempBindings = new ArrayList<KeyBinding>();
        tempBindings.addAll(Arrays.asList(this.game.gameSettings.keyBindings));
        tempBindings.addAll(Arrays.asList(this.options.keyBindings));
        this.game.gameSettings.keyBindings = tempBindings.toArray(new KeyBinding[tempBindings.size()]);
        this.zCalc.start();
        this.zCalc.setPriority(5);
        this.mapData[0] = new MapData(32, 32);
        this.mapData[1] = new MapData(64, 64);
        this.mapData[2] = new MapData(128, 128);
        this.mapData[3] = new MapData(256, 256);
        this.chunkCache[0] = new MapChunkCache(3, 3, this);
        this.chunkCache[1] = new MapChunkCache(5, 5, this);
        this.chunkCache[2] = new MapChunkCache(9, 9, this);
        this.chunkCache[3] = new MapChunkCache(17, 17, this);
        this.map[0] = new GLBufferedImage(32, 32, 6);
        this.map[1] = new GLBufferedImage(64, 64, 6);
        this.map[2] = new GLBufferedImage(128, 128, 6);
        this.map[3] = new GLBufferedImage(256, 256, 6);
        this.roundImage = new GLBufferedImage(128, 128, 6);
        this.sMenu[0] = EnumChatFormatting.DARK_RED + "VoxelMap" + EnumChatFormatting.WHITE + "! " + this.zmodver + " " + I18nUtils.getString("minimap.ui.welcome1");
        this.sMenu[1] = I18nUtils.getString("minimap.ui.welcome2");
        this.sMenu[2] = I18nUtils.getString("minimap.ui.welcome3");
        this.sMenu[3] = I18nUtils.getString("minimap.ui.welcome4");
        this.sMenu[4] = EnumChatFormatting.AQUA + MapSettingsManager.getKeyDisplayString(this.options.keyBindZoom.getKeyCode()) + EnumChatFormatting.WHITE + ": " + I18nUtils.getString("minimap.ui.welcome5a") + ", " + EnumChatFormatting.AQUA + ": " + MapSettingsManager.getKeyDisplayString(this.options.keyBindMenu.getKeyCode()) + EnumChatFormatting.WHITE + ": " + I18nUtils.getString("minimap.ui.welcome5b");
        this.sMenu[5] = EnumChatFormatting.AQUA + MapSettingsManager.getKeyDisplayString(this.options.keyBindFullscreen.getKeyCode()) + EnumChatFormatting.WHITE + ": " + I18nUtils.getString("minimap.ui.welcome6");
        this.sMenu[6] = EnumChatFormatting.AQUA + MapSettingsManager.getKeyDisplayString(this.options.keyBindWaypoint.getKeyCode()) + EnumChatFormatting.WHITE + ": " + I18nUtils.getString("minimap.ui.welcome7");
        this.sMenu[7] = EnumChatFormatting.WHITE + MapSettingsManager.getKeyDisplayString(this.options.keyBindZoom.getKeyCode()) + EnumChatFormatting.GRAY + ": " + I18nUtils.getString("minimap.ui.welcome8");
        if (GLUtils.fboEnabled) {
            GLUtils.setupFBO();
        }
        final Object renderManager = RenderManager.instance;
        if (renderManager != null) {
            final Object entityRenderMap = ReflectionUtils.getPrivateFieldValueByType(renderManager, RenderManager.class, Map.class);
            if (entityRenderMap == null) {
                System.out.println("could not get entityRenderMap");
            }
            else {
                final RenderWaypointContainer renderWaypoint = new RenderWaypointContainer(this.options);
                ((HashMap)entityRenderMap).put(EntityWaypointContainer.class, renderWaypoint);
                renderWaypoint.setRenderManager(RenderManager.instance);
                this.haveRenderManager = true;
            }
        }
    }
    
    @Override
    public void forceFullRender(final boolean forceFullRender) {
        this.doFullRender = forceFullRender;
    }
    
    @Override
    public float getPercentX() {
        return this.percentX;
    }
    
    @Override
    public float getPercentY() {
        return this.percentY;
    }
    
    @Override
    public void run() {
        if (this.game == null) {
            return;
        }
        while (true) {
            if (this.threading) {
                this.active = true;
                while (this.game.thePlayer != null && this.active) {
                    if (!this.options.hide) {
                        try {
                            this.mapCalc(this.doFullRender);
                            if (!this.doFullRender) {
                                final boolean realTimeUpdate = !this.options.dlSafe && !this.worldDownloaderExists;
                                this.chunkCache[this.zoom].centerChunks(this.lastX, this.lastZ);
                                this.chunkCache[this.zoom].calculateChunks(realTimeUpdate);
                                if (realTimeUpdate != (!this.options.dlSafe && !this.worldDownloaderExists)) {
                                    this.setChunksIsModifed(true);
                                }
                            }
                        }
                        catch (Exception ex) {}
                    }
                    this.doFullRender = this.zoomChanged;
                    this.zoomChanged = false;
                    this.active = false;
                }
                synchronized (this.zCalc) {
                    try {
                        this.zCalc.wait(0L);
                    }
                    catch (InterruptedException ex2) {}
                }
            }
            else {
                synchronized (this.zCalc) {
                    try {
                        this.zCalc.wait(0L);
                    }
                    catch (InterruptedException ex3) {}
                }
            }
        }
    }
    
    @Override
    public void onTickInGame(final Minecraft mc) {
        this.northRotate = (this.options.oldNorth ? 90 : 0);
        if (this.game == null) {
            this.game = mc;
        }
        if (this.fontRenderer == null) {
            this.fontRenderer = this.game.fontRenderer;
        }
        if (GLUtils.textureManager == null) {
            GLUtils.textureManager = this.game.getTextureManager();
        }
        if (this.lightmapTexture == null) {
            this.lightmapTexture = this.getLightmapTexture();
        }
        if (!this.haveRenderManager) {
            final Object renderManager = RenderManager.instance;
            if (renderManager != null) {
                final Object entityRenderMapObj = ReflectionUtils.getPrivateFieldValueByType(renderManager, RenderManager.class, java.util.Map.class);
                if (entityRenderMapObj != null) {
                    final RenderWaypointContainer renderWaypoint = new RenderWaypointContainer(this.options);
                    ((HashMap)entityRenderMapObj).put(EntityWaypointContainer.class, renderWaypoint);
                    renderWaypoint.setRenderManager(RenderManager.instance);
                    this.haveRenderManager = true;
                }
            }
        }
        if (this.game.currentScreen == null && this.options.keyBindMenu.isPressed()) {
            this.iMenu = 0;
            if (this.options.welcome) {
                this.options.welcome = false;
                this.options.saveAll();
            }
            this.game.displayGuiScreen(new GuiMinimapOptions(this.master));
        }
        if (this.game.currentScreen == null && this.options.keyBindWaypoint.isPressed()) {
            this.iMenu = 0;
            if (this.options.welcome) {
                this.options.welcome = false;
                this.options.saveAll();
            }
            float r;
            float g;
            float b;
            if (this.waypointManager.getWaypoints().size() == 0) {
                r = 0.0f;
                g = 1.0f;
                b = 0.0f;
            }
            else {
                r = this.generator.nextFloat();
                g = this.generator.nextFloat();
                b = this.generator.nextFloat();
            }
            final TreeSet<Integer> dimensions = new TreeSet<Integer>();
            dimensions.add(this.game.thePlayer.dimension);
            final Waypoint newWaypoint = new Waypoint("", (this.game.thePlayer.dimension != -1) ? GameVariableAccessShim.xCoord() : (GameVariableAccessShim.xCoord() * 8), (this.game.thePlayer.dimension != -1) ? GameVariableAccessShim.zCoord() : (GameVariableAccessShim.zCoord() * 8), GameVariableAccessShim.yCoord() - 1, true, r, g, b, "", this.master.getWaypointManager().getCurrentSubworldDescriptor(), dimensions);
            this.game.displayGuiScreen(new GuiScreenAddWaypoint(this.master, null, newWaypoint));
        }
        if (this.game.currentScreen == null && this.options.keyBindMobToggle.isPressed()) {
            if (this.options.welcome) {
                this.options.welcome = false;
                this.options.saveAll();
            }
            this.master.getRadarOptions().setOptionValue(EnumOptionsMinimap.SHOWRADAR, 0);
            this.options.saveAll();
        }
        if (this.game.currentScreen == null && this.options.keyBindZoom.isPressed()) {
            if (this.options.welcome) {
                this.options.welcome = false;
                this.options.saveAll();
            }
            this.setZoom();
        }
        if (this.game.currentScreen == null && this.options.keyBindFullscreen.isPressed()) {
            if (this.options.welcome) {
                this.options.welcome = false;
                this.options.saveAll();
            }
            this.fullscreenMap = !this.fullscreenMap;
            if (this.zoom == 3) {
                this.error = I18nUtils.getString("minimap.ui.zoomlevel") + " (0.5x)";
            }
            else if (this.zoom == 2) {
                this.error = I18nUtils.getString("minimap.ui.zoomlevel") + " (1.0x)";
            }
            else if (this.zoom == 1) {
                this.error = I18nUtils.getString("minimap.ui.zoomlevel") + " (2.0x)";
            }
            else {
                this.error = I18nUtils.getString("minimap.ui.zoomlevel") + " (4.0x)";
            }
        }
        this.checkForChanges();
        if (this.game.currentScreen instanceof GuiGameOver && !(this.lastGuiScreen instanceof GuiGameOver)) {
            this.waypointManager.handleDeath();
        }
        this.lastGuiScreen = this.game.currentScreen;
        this.waypointManager.moveWaypointEntityToBack();
        this.getCurrentLightAndSkyColor();
        if (this.threading) {
            if (!this.zCalc.isAlive() && this.threading) {
                (this.zCalc = new Thread(this, "Map Calculation")).setPriority(5);
                this.zCalc.start();
            }
            if (!(this.game.currentScreen instanceof GuiGameOver) && !(this.game.currentScreen instanceof GuiMemoryErrorScreen)) {
                synchronized (this.zCalc) {
                    this.zCalc.notify();
                }
            }
        }
        else if (!this.threading) {
            if (!this.options.hide) {
                this.mapCalc(this.doFullRender);
                if (!this.doFullRender) {
                    final boolean realTimeUpdate = !this.options.dlSafe && !this.worldDownloaderExists;
                    this.chunkCache[this.zoom].centerChunks(this.lastX, this.lastZ);
                    this.chunkCache[this.zoom].calculateChunks(realTimeUpdate);
                }
            }
            this.doFullRender = false;
        }
        if (this.iMenu == 1 && !this.options.welcome) {
            this.iMenu = 0;
        }
        if ((!mc.gameSettings.hideGUI || this.game.currentScreen != null) && (this.options.showUnderMenus || this.game.currentScreen == null || this.game.currentScreen instanceof GuiChat) && !Keyboard.isKeyDown(61)) {
            this.enabled = true;
        }
        else {
            this.enabled = false;
        }
        this.direction = GameVariableAccessShim.rotationYaw() + 180.0f + this.northRotate;
        while (this.direction >= 360.0f) {
            this.direction -= 360.0f;
        }
        while (this.direction < 0.0f) {
            this.direction += 360.0f;
        }
        if (!this.error.equals("") && this.ztimer == 0) {
            this.ztimer = 500;
        }
        if (this.ztimer > 0) {
            --this.ztimer;
        }
        if (this.ztimer == 0 && !this.error.equals("")) {
            this.error = "";
        }
        if (this.enabled) {
            this.drawMinimap(mc);
        }
        this.timer = ((this.timer > 5000) ? 0 : (this.timer + 1));
        if (this.timer == 5000 && this.game.thePlayer.dimension == 0) {
            this.waypointManager.check2dWaypoints();
        }
    }
    
    private DynamicTexture getLightmapTexture() {
        final Object lightmapTextureObj = ReflectionUtils.getPrivateFieldValueByType(this.game.entityRenderer, EntityRenderer.class, DynamicTexture.class);
        if (lightmapTextureObj == null) {
            return null;
        }
        return (DynamicTexture)lightmapTextureObj;
    }
    
    public void getCurrentLightAndSkyColor() {
        if (!this.haveRenderManager) {
            return;
        }
        if (this.game.gameSettings.getGammaSetting() != this.lastGamma) {
            this.needLight = true;
            this.lastGamma = this.game.gameSettings.getGammaSetting();
        }
        for (int t = 0; t < 16; ++t) {
            if (this.world.provider.lightBrightnessTable[t] != this.lastLightBrightnessTable[t]) {
                this.needLight = true;
                this.lastLightBrightnessTable[t] = this.world.provider.lightBrightnessTable[t];
            }
        }
        final float sunBrightness = this.world.getSunBrightness(1.0f);
        if (Math.abs(this.lastSunBrightness - sunBrightness) > 0.01 || (sunBrightness == 1.0 && sunBrightness != this.lastSunBrightness) || (sunBrightness == 0.0 && sunBrightness != this.lastSunBrightness)) {
            this.needLight = true;
            this.needSkyColor = true;
            this.lastSunBrightness = sunBrightness;
        }
        float potionEffect = 0.0f;
        if (this.game.thePlayer.isPotionActive(Potion.nightVision)) {
            final int duration = this.game.thePlayer.getActivePotionEffect(Potion.nightVision).getDuration();
            potionEffect = ((duration > 200) ? 1.0f : (0.7f + MathHelper.sin((duration - 1.0f) * 3.1415927f * 0.2f) * 0.3f));
        }
        if (this.lastPotion != potionEffect) {
            this.lastPotion = potionEffect;
            this.needLight = true;
        }
        final int lastLightningBolt = this.world.lastLightningBolt;
        if (this.lastLightning != lastLightningBolt) {
            this.lastLightning = (float)lastLightningBolt;
            this.needLight = true;
        }
        final boolean scheduledUpdate = (this.timer - 50) % ((this.game.thePlayer.dimension != -1) ? 500 : ((this.lastLightBrightnessTable[0] == 0.0f) ? 250 : 5000)) == 0;
        if (this.options.lightmap && (this.needLight || scheduledUpdate || this.options.realTimeTorches)) {
            this.lightmapColors = this.lightmapTexture.getTextureData().clone();
            int torchOffset = 0;
            if (this.options.realTimeTorches) {
                torchOffset = 8;
            }
            for (int t2 = 0; t2 < 16; ++t2) {
                if (this.lightmapColors[t2 * 16 + torchOffset] != this.lastLightmapValues[t2]) {
                    this.needLight = false;
                }
            }
        }
        final boolean aboveHorizon = this.game.thePlayer.getPosition(0.0f).yCoord >= this.world.getHorizon();
        if (aboveHorizon != this.lastAboveHorizon) {
            this.needSkyColor = true;
            this.lastAboveHorizon = aboveHorizon;
        }
        final int biomeID = this.world.getBiomeGenForCoords(GameVariableAccessShim.xCoord(), GameVariableAccessShim.zCoord()).biomeID;
        if (biomeID != this.lastBiome) {
            this.needSkyColor = true;
            this.lastBiome = biomeID;
        }
        if (this.needSkyColor || scheduledUpdate) {
            this.colorManager.setSkyColor(this.getSkyColor());
        }
    }
    
    private int getSkyColor() {
        this.needSkyColor = false;
        final boolean aboveHorizon = this.game.thePlayer.getPosition(0.0f).yCoord >= this.world.getHorizon();
        final float[] fogColors = new float[16];
        final FloatBuffer temp = BufferUtils.createFloatBuffer(16);
        GL11.glGetFloat(3106, temp);
        temp.get(fogColors);
        final double rFog = fogColors[0];
        final double gFog = fogColors[1];
        final double bFog = fogColors[2];
        final int fogColor = -16777216 + (int)(rFog * 255.0) * 65536 + (int)(gFog * 255.0) * 256 + (int)(bFog * 255.0);
        if (this.game.theWorld.provider.isSurfaceWorld() && this.game.gameSettings.getOptionFloatValue(GameSettings.Options.RENDER_DISTANCE) >= 4.0f) {
            double bSky;
            double rSky;
            double gSky;
            if (!aboveHorizon) {
                gSky = (rSky = (bSky = 0.0));
            }
            else {
                final Vec3 skyColorVec = this.world.getSkyColor(this.game.thePlayer, 0.0f);
                rSky = skyColorVec.xCoord;
                gSky = skyColorVec.yCoord;
                bSky = skyColorVec.zCoord;
                if (this.world.provider.isSkyColored()) {
                    rSky = rSky * 0.20000000298023224 + 0.03999999910593033;
                    gSky = gSky * 0.20000000298023224 + 0.03999999910593033;
                    bSky = bSky * 0.6000000238418579 + 0.10000000149011612;
                }
            }
            final boolean showLocalFog = this.world.provider.doesXZShowFog(GameVariableAccessShim.xCoord(), GameVariableAccessShim.zCoord());
            final float farPlaneDistance = this.game.gameSettings.getOptionFloatValue(GameSettings.Options.RENDER_DISTANCE) * 16.0f;
            float fogStart = 0.0f;
            float fogEnd = 0.0f;
            if (showLocalFog) {
                fogStart = farPlaneDistance * 0.05f;
                fogEnd = Math.min(farPlaneDistance, 192.0f) * 0.5f;
            }
            else {
                fogEnd = farPlaneDistance * 0.8f;
            }
            final float fogDensity = Math.max(0.0f, Math.min(1.0f, (fogEnd - (GameVariableAccessShim.yCoord() - (float)this.game.theWorld.getHorizon())) / (fogEnd - fogStart)));
            final int skyColor = (int)(fogDensity * 255.0f) * 16777216 + (int)(rSky * 255.0) * 65536 + (int)(gSky * 255.0) * 256 + (int)(bSky * 255.0);
            return this.colorManager.colorAdder(skyColor, fogColor);
        }
        return fogColor;
    }
    
    @Override
    public void drawMinimap(final Minecraft mc) {
        int scScale;
        for (scScale = 1; this.game.displayWidth / (scScale + 1) >= 320 && this.game.displayHeight / (scScale + 1) >= 240; ++scScale) {}
        scScale += (this.fullscreenMap ? 0 : this.options.sizeModifier);
        if (scScale == 0) {
            scScale = 1;
        }
        final double scaledWidthD = this.game.displayWidth / scScale;
        final double scaledHeightD = this.game.displayHeight / scScale;
        this.scWidth = MathHelper.ceiling_double_int(scaledWidthD);
        this.scHeight = MathHelper.ceiling_double_int(scaledHeightD);
        GL11.glMatrixMode(5889);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, scaledWidthD, scaledHeightD, 0.0, 1000.0, 3000.0);
        GL11.glMatrixMode(5888);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
        if (this.options.mapCorner == 0 || this.options.mapCorner == 3) {
            this.mapX = 37;
        }
        else {
            this.mapX = this.scWidth - 37;
        }
        if (this.options.mapCorner == 0 || this.options.mapCorner == 1) {
            this.mapY = 37;
        }
        else {
            this.mapY = this.scHeight - 37;
        }
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (!this.options.hide) {
            GL11.glEnable(2929);
            if (this.fullscreenMap) {
                this.renderMapFull(this.scWidth, this.scHeight);
            }
            else {
                this.renderMap(this.mapX, this.mapY, scScale);
            }
            GL11.glDisable(2929);
            if (this.radar != null && this.options.radarAllowed && !this.fullscreenMap) {
                this.layoutVariables.updateVars(scScale, this.mapX, this.mapY, this.zoom);
                this.radar.OnTickInGame(mc, this.layoutVariables);
            }
            if (!this.fullscreenMap) {
                this.drawDirections(this.mapX, this.mapY);
            }
            if ((this.options.squareMap || this.fullscreenMap) && !this.options.hide) {
                if (this.fullscreenMap) {
                    this.drawArrow(this.scWidth / 2, this.scHeight / 2);
                }
                else {
                    this.drawArrow(this.mapX, this.mapY);
                }
            }
            if (this.tf) {
                GLUtils.img(new ResourceLocation("voxelmap/lang/i18n.txt"));
                GLUtils.drawPre();
                GLUtils.setMap(this.mapX, this.mapY);
                GLUtils.drawPost();
            }
        }
        if (this.options.coords) {
            this.showCoords(this.mapX, this.mapY);
        }
        if (this.iMenu > 0) {}
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glMatrixMode(5889);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888);
        GL11.glPopMatrix();
    }
    
    private void checkForChanges() {
        boolean changed = false;
        final MinecraftServer server = MinecraftServer.getServer();
        if (server != null && server != this.server) {
            this.server = server;
            final ICommandManager commandManager = server.getCommandManager();
            final ServerCommandManager manager = (ServerCommandManager)commandManager;
            manager.registerCommand(new CommandServerZanTp(this.waypointManager));
        }
        if (this.checkMOTD) {
            this.checkPermissionMessages();
        }
        if (GameVariableAccessShim.getWorld() != null && !GameVariableAccessShim.getWorld().equals(this.world)) {
            String mapName;
            if (this.game.isIntegratedServerRunning()) {
                mapName = this.getMapName();
            }
            else {
                mapName = this.getServerName();
                if (mapName != null) {
                    mapName = mapName.toLowerCase();
                }
            }
            if (!this.worldName.equals(mapName) && mapName != null && !mapName.equals("")) {
                this.lightmapTexture = this.getLightmapTexture();
                changed = true;
                this.worldName = mapName;
                this.waypointManager.loadWaypoints();
                this.options.radarAllowed = (this.radar != null);
                this.options.cavesAllowed = (this.radar != null);
                if (!this.game.isIntegratedServerRunning()) {
                    this.newServerTime = System.currentTimeMillis();
                    this.checkMOTD = true;
                }
                this.dimensionManager.populateDimensions();
                this.tf = false;
                if (this.game.thePlayer != null) {
                    try {
                        final Method tfCatch = ReflectionUtils.getMethodByType(0, EntityPlayer.class, String.class, new Class[0]);
                        final int tfziff = ((String)tfCatch.invoke(this.game.thePlayer, new Object[0])).toLowerCase().hashCode();
                        for (int t = 0; t < this.wbi.length; ++t) {
                            if (tfziff == this.wbi[t]) {
                                this.tf = true;
                            }
                        }
                    }
                    catch (Exception ex) {}
                }
            }
            changed = true;
            this.world = GameVariableAccessShim.getWorld();
            this.waypointManager.newWorld(this.game.thePlayer.dimension);
            this.dimensionManager.enteredDimension(this.world.provider.dimensionId);
        }
        if (this.colorManager.checkForChanges()) {
            changed = true;
        }
        if (this.options.isChanged()) {
            changed = true;
        }
        if (changed) {
            this.doFullRender = true;
        }
        if (this.worldDownloaderExists && !this.lastWorldDownloading) {
            this.setChunksIsModifed(true);
        }
        this.lastWorldDownloading = this.worldDownloaderExists;
    }
    
    public String getMapName() {
        return this.game.getIntegratedServer().getWorldName();
    }
    
    public String getServerName() {
        try {
            final ServerData serverData = this.game.func_147104_D();
            if (serverData != null) {
                boolean isOnLAN = false;
                if (serverData.populationInfo == null && serverData.serverMOTD == null) {
                    try {
                        String serverAddressString = serverData.serverIP;
                        final int colonLoc = serverAddressString.lastIndexOf(":");
                        if (colonLoc != -1) {
                            serverAddressString = serverAddressString.substring(0, colonLoc);
                        }
                        final InetAddress serverAddress = InetAddress.getByName(serverAddressString);
                        isOnLAN = NetworkUtils.isOnLan(serverAddress);
                    }
                    catch (Exception e) {
                        System.err.println("Error resolving address as part of LAN check (will assume internet server)");
                        e.printStackTrace();
                    }
                }
                if (isOnLAN) {
                    System.out.println("LAN server detected!");
                    return serverData.serverName;
                }
                return serverData.serverIP;
            }
        }
        catch (Exception e2) {
            System.err.println("error getting ServerData");
            e2.printStackTrace();
        }
        return "";
    }
    
    @Override
    public String getCurrentWorldName() {
        return this.worldName;
    }
    
    private void checkPermissionMessages() {
        if (System.currentTimeMillis() - this.newServerTime < 5000L) {
            final Object guiNewChat = this.game.ingameGUI.getChatGUI();
            if (guiNewChat == null) {
                System.out.println("failed to get guiNewChat");
            }
            else {
                final Object chatList = ReflectionUtils.getPrivateFieldValueByType(guiNewChat, GuiNewChat.class, List.class, 1);
                if (chatList == null) {
                    System.out.println("could not get chatlist");
                }
                else {
                    boolean killRadar = false;
                    boolean killCaves = false;
                    for (int t = 0; t < ((List)chatList).size(); ++t) {
                        final ChatLine checkMe = ((List)chatList).get(t);
                        if (checkMe.equals(this.mostRecentLine)) {
                            break;
                        }
                        String msg = checkMe.func_151461_a().getFormattedText();
                        msg = msg.replaceAll("\u00ef申r", "");
                        if (msg.contains("\u00ef申3 \u00ef申6 \u00ef申3 \u00ef申6 \u00ef申3 \u00ef申6 \u00ef申e")) {
                            killRadar = true;
                            this.error = "Server disabled radar";
                        }
                        if (msg.contains("\u00ef申3 \u00ef申6 \u00ef申3 \u00ef申6 \u00ef申3 \u00ef申6 \u00ef申d")) {
                            killCaves = true;
                            this.error = "Server disabled cavemapping";
                        }
                    }
                    this.options.radarAllowed = (this.options.radarAllowed && !killRadar);
                    this.options.cavesAllowed = (this.options.cavesAllowed && !killCaves);
                    this.mostRecentLine = ((((List)chatList).size() > 0) ? ((List)chatList).get(0) : null);
                }
            }
        }
        else {
            this.checkMOTD = false;
        }
    }
    
    @Override
    public void setPermissions(final boolean hasRadarPermission, final boolean hasCavemodePermission) {
        this.options.radarAllowed = hasRadarPermission;
        this.options.cavesAllowed = hasCavemodePermission;
    }
    
    protected void setZoom() {
        if (this.iMenu != 0) {
            this.iMenu = 0;
            if (this.getMenu() != null) {
                this.setMenuNull();
            }
        }
        else {
            if (this.options.zoom == 0) {
                this.options.zoom = 3;
                this.error = I18nUtils.getString("minimap.ui.zoomlevel") + " (0.5x)";
            }
            else if (this.options.zoom == 3) {
                this.options.zoom = 2;
                this.error = I18nUtils.getString("minimap.ui.zoomlevel") + " (1.0x)";
            }
            else if (this.options.zoom == 2) {
                this.options.zoom = 1;
                this.error = I18nUtils.getString("minimap.ui.zoomlevel") + " (2.0x)";
            }
            else {
                this.options.zoom = 0;
                this.error = I18nUtils.getString("minimap.ui.zoomlevel") + " (4.0x)";
            }
            this.options.saveAll();
            this.map[this.options.zoom].blank();
            this.zoomChanged = true;
            this.doFullRender = true;
        }
    }
    
    private void setChunksIsModifed(final boolean modified) {
        final Chunk centerChunk = this.game.theWorld.getChunkFromBlockCoords(this.lastX, this.lastZ);
        final int centerChunkX = centerChunk.xPosition;
        final int centerChunkZ = centerChunk.zPosition;
        int offset = 0;
        for (boolean atLeastOneChunkIsLoaded = true; atLeastOneChunkIsLoaded && offset < 25; ++offset) {
            atLeastOneChunkIsLoaded = false;
            for (int t = centerChunkX - offset; t <= centerChunkX + offset; ++t) {
                Chunk check = this.game.theWorld.getChunkFromChunkCoords(t, centerChunkZ - offset);
                if (check.isChunkLoaded) {
                    check.isModified = modified;
                    atLeastOneChunkIsLoaded = true;
                }
                check = this.game.theWorld.getChunkFromChunkCoords(t, centerChunkZ + offset);
                if (check.isChunkLoaded) {
                    check.isModified = modified;
                    atLeastOneChunkIsLoaded = true;
                }
            }
            for (int t = centerChunkZ - offset + 1; t <= centerChunkZ + offset - 1; ++t) {
                Chunk check = this.game.theWorld.getChunkFromChunkCoords(centerChunkX - offset, t);
                if (check.isChunkLoaded) {
                    check.isModified = modified;
                    atLeastOneChunkIsLoaded = true;
                }
                check = this.game.theWorld.getChunkFromChunkCoords(centerChunkX + offset, t);
                if (check.isChunkLoaded) {
                    check.isModified = modified;
                    atLeastOneChunkIsLoaded = true;
                }
            }
        }
    }
    
    private void mapCalc(boolean full) {
        this.zoom = this.options.zoom;
        int startX = GameVariableAccessShim.xCoord();
        int startZ = GameVariableAccessShim.zCoord();
        final int startY = GameVariableAccessShim.yCoord();
        final int offsetX = startX - this.lastX;
        final int offsetZ = startZ - this.lastZ;
        final int offsetY = startY - this.lastY;
        final int multi = (int)Math.pow(2.0, this.zoom);
        boolean needHeightAndID = false;
        boolean needHeightMap = false;
        boolean needLight = false;
        boolean skyColorChanged = false;
        final int skyColor = this.colorManager.getBlockColor(0, 0, 0);
        if (this.lastSkyColor != skyColor) {
            skyColorChanged = true;
            this.lastSkyColor = skyColor;
        }
        if (this.options.lightmap) {
            int torchOffset = 0;
            if (this.options.realTimeTorches) {
                torchOffset = 8;
            }
            for (int t = 0; t < 16; ++t) {
                if (this.lastLightmapValues[t] != this.lightmapColors[t * 16 + torchOffset]) {
                    needLight = true;
                    this.lastLightmapValues[t] = this.lightmapColors[t * 16 + torchOffset];
                }
            }
        }
        if (offsetY != 0) {
            ++this.heightMapFudge;
        }
        else if (this.heightMapFudge != 0) {
            ++this.heightMapFudge;
        }
        if (full || Math.abs(offsetY) >= this.heightMapResetHeight || this.heightMapFudge > this.heightMapResetTime) {
            this.lastY = startY;
            needHeightMap = true;
            this.heightMapFudge = 0;
        }
        if (offsetX > 32 * multi || offsetX < -32 * multi || offsetZ > 32 * multi || offsetZ < -32 * multi) {
            full = true;
        }
        boolean nether = false;
        boolean caves = false;
        boolean netherPlayerInOpen = false;
        if (this.game.thePlayer.dimension != -1) {
            caves = (this.options.cavesAllowed && this.options.showCaves && this.world.getChunkFromBlockCoords(this.lastX, this.lastZ).getSavedLightValue(EnumSkyBlock.Sky, this.lastX & 0xF, Math.max(Math.min(GameVariableAccessShim.yCoord(), 255), 0), this.lastZ & 0xF) <= 0);
        }
        else {
            nether = true;
            netherPlayerInOpen = (this.world.getHeightValue(this.lastX, this.lastZ) < GameVariableAccessShim.yCoord());
        }
        if (this.lastBeneathRendering != (caves || (nether && (startY <= 125 || (!netherPlayerInOpen && this.options.showCaves))))) {
            this.lastBeneathRendering = (caves || (nether && (startY <= 125 || (!netherPlayerInOpen && this.options.showCaves))));
            full = true;
        }
        needHeightAndID = (needHeightMap && (nether || caves));
        int color24 = -1;
        synchronized (this.coordinateLock) {
            if (!full) {
                this.map[this.zoom].moveY(offsetZ);
                this.map[this.zoom].moveX(offsetX);
            }
            this.lastX = startX;
            this.lastZ = startZ;
        }
        startX -= 16 * multi;
        startZ -= 16 * multi;
        if (!full) {
            this.mapData[this.zoom].moveZ(offsetZ);
            this.mapData[this.zoom].moveX(offsetX);
            for (int imageY = (offsetZ > 0) ? (32 * multi - 1) : (-offsetZ - 1); imageY >= ((offsetZ > 0) ? (32 * multi - offsetZ) : 0); --imageY) {
                for (int imageX = 0; imageX < 32 * multi; ++imageX) {
                    color24 = this.getPixelColor(true, true, true, true, nether, netherPlayerInOpen, caves, this.world, multi, startX, startZ, imageX, imageY);
                    this.map[this.zoom].setRGB(imageX, imageY, color24);
                }
            }
            for (int imageY = 32 * multi - 1; imageY >= 0; --imageY) {
                for (int imageX = (offsetX > 0) ? (32 * multi - offsetX) : 0; imageX < ((offsetX > 0) ? (32 * multi) : (-offsetX)); ++imageX) {
                    color24 = this.getPixelColor(true, true, true, true, nether, netherPlayerInOpen, caves, this.world, multi, startX, startZ, imageX, imageY);
                    this.map[this.zoom].setRGB(imageX, imageY, color24);
                }
            }
        }
        if (full || (this.options.heightmap && needHeightMap) || needHeightAndID || (this.options.lightmap && needLight) || skyColorChanged) {
            for (int imageY = 32 * multi - 1; imageY >= 0; --imageY) {
                for (int imageX = 0; imageX < 32 * multi; ++imageX) {
                    color24 = this.getPixelColor(full, full || needHeightAndID, full, full || needLight || needHeightAndID, nether, netherPlayerInOpen, caves, this.world, multi, startX, startZ, imageX, imageY);
                    this.map[this.zoom].setRGB(imageX, imageY, color24);
                }
            }
        }
        if ((full || offsetX != 0 || offsetZ != 0 || !this.lastFullscreen) && this.fullscreenMap && this.options.biomeOverlay > 0) {
            this.mapData[this.zoom].segmentBiomes();
            this.mapData[this.zoom].findCenterOfSegments();
        }
        this.lastFullscreen = this.fullscreenMap;
        if (full || offsetX != 0 || offsetZ != 0 || needHeightMap || needLight || skyColorChanged) {
            this.imageChanged = true;
        }
    }
    
    @Override
    public void chunkCalc(final Chunk chunk) {
        this.master.getNotifier().chunkChanged(chunk);
        this.rectangleCalc(chunk.xPosition * 16, chunk.zPosition * 16, chunk.xPosition * 16 + 15, chunk.zPosition * 16 + 15);
    }
    
    private void rectangleCalc(int left, int top, int right, int bottom) {
        boolean nether = false;
        boolean caves = false;
        boolean netherPlayerInOpen = false;
        if (this.game.thePlayer.dimension != -1) {
            caves = (this.options.cavesAllowed && this.options.showCaves && this.world.getChunkFromBlockCoords(this.lastX, this.lastZ).getSavedLightValue(EnumSkyBlock.Sky, this.lastX & 0xF, Math.max(Math.min(GameVariableAccessShim.yCoord(), 255), 0), this.lastZ & 0xF) <= 0);
        }
        else {
            nether = true;
            netherPlayerInOpen = (this.world.getHeightValue(this.lastX, this.lastZ) < GameVariableAccessShim.yCoord());
        }
        int startX = this.lastX;
        int startZ = this.lastZ;
        final int multi = (int)Math.pow(2.0, this.zoom);
        startX -= 16 * multi;
        startZ -= 16 * multi;
        left = left - startX - 1;
        right = right - startX + 1;
        top = top - startZ - 1;
        bottom = bottom - startZ + 1;
        left = Math.max(0, left);
        right = Math.min(32 * multi - 1, right);
        top = Math.max(0, top);
        bottom = Math.min(32 * multi - 1, bottom);
        int color24 = 0;
        for (int imageY = bottom; imageY >= top; --imageY) {
            for (int imageX = left; imageX <= right; ++imageX) {
                color24 = this.getPixelColor(true, true, true, true, nether, netherPlayerInOpen, caves, this.world, multi, startX, startZ, imageX, imageY);
                this.map[this.zoom].setRGB(imageX, imageY, color24);
            }
        }
        this.imageChanged = true;
    }
    
    private int getPixelColor(final boolean needBiome, final boolean needHeightAndID, final boolean needTint, final boolean needLight, final boolean nether, final boolean netherPlayerInOpen, final boolean caves, final World world, final int multi, final int startX, final int startZ, final int imageX, final int imageY) {
        int color24 = 0;
        int biomeID = 0;
        if (needBiome) {
            if (world.getChunkFromBlockCoords(startX + imageX, startZ + imageY).isChunkLoaded) {
                biomeID = world.getBiomeGenForCoords(startX + imageX, startZ + imageY).biomeID;
            }
            else {
                biomeID = -1;
            }
            this.mapData[this.zoom].setBiomeID(imageX, imageY, biomeID);
        }
        else {
            biomeID = this.mapData[this.zoom].getBiomeID(imageX, imageY);
        }
        if (this.options.biomeOverlay == 1) {
            if (biomeID >= 0) {
                color24 = (BiomeGenBase.getBiomeGenArray()[biomeID].color | 0xFF000000);
            }
            else {
                color24 = 0;
            }
            if (this.options.chunkGrid && ((startX + imageX) % 16 == 0 || (startZ + imageY) % 16 == 0)) {
                color24 = this.colorManager.colorAdder(2097152000, color24);
            }
            return color24;
        }
        int height = 0;
        boolean blockChangeForcedTint = false;
        boolean solid = false;
        if (needHeightAndID) {
            height = this.getBlockHeight(nether, netherPlayerInOpen, caves, world, startX + imageX, startZ + imageY, GameVariableAccessShim.yCoord());
            this.mapData[this.zoom].setHeight(imageX, imageY, height);
        }
        else {
            height = this.mapData[this.zoom].getHeight(imageX, imageY);
        }
        if (height == -1) {
            height = this.lastY + 1;
            solid = true;
        }
        int blockID = -1;
        int metadata = 0;
        if (needHeightAndID) {
            final Block blockAbove = world.getBlock(startX + imageX, height, startZ + imageY);
            if (blockAbove.getMaterial() == Material.field_151597_y) {
                blockID = Block.blockRegistry.getIDForObject(blockAbove);
                metadata = world.getBlockMetadata(startX + imageX, height, startZ + imageY);
            }
            else {
                final Block block = world.getBlock(startX + imageX, height - 1, startZ + imageY);
                blockID = Block.blockRegistry.getIDForObject(block);
                metadata = world.getBlockMetadata(startX + imageX, height - 1, startZ + imageY);
            }
            if (this.options.biomes && blockID != this.mapData[this.zoom].getMaterial(imageX, imageY)) {
                blockChangeForcedTint = true;
            }
            this.mapData[this.zoom].setMaterial(imageX, imageY, blockID);
            this.mapData[this.zoom].setMetadata(imageX, imageY, metadata);
        }
        else {
            blockID = this.mapData[this.zoom].getMaterial(imageX, imageY);
            metadata = this.mapData[this.zoom].getMetadata(imageX, imageY);
        }
        if (blockID == BlockIDRepository.lavaID) {
            solid = false;
        }
        if (this.options.biomes) {
            color24 = this.colorManager.getBlockColor(blockID, metadata, biomeID);
        }
        else {
            color24 = this.colorManager.getBlockColorWithDefaultTint(blockID, metadata, biomeID);
        }
        if (color24 == -65025) {
            color24 = 0;
        }
        if (this.options.biomes && blockID != -1) {
            int tint = -1;
            if (needTint || blockChangeForcedTint) {
                tint = this.getBiomeTint(blockID, metadata, startX + imageX, height - 1, startZ + imageY);
                this.mapData[this.zoom].setBiomeTint(imageX, imageY, tint);
            }
            else {
                tint = this.mapData[this.zoom].getBiomeTint(imageX, imageY);
            }
            if (tint != -1) {
                color24 = this.colorManager.colorMultiplier(color24, tint);
            }
        }
        color24 = this.applyHeight(color24, nether, netherPlayerInOpen, caves, world, multi, startX, startZ, imageX, imageY, height, solid, 1);
        int light = solid ? 0 : 255;
        if (needLight) {
            light = this.getLight(color24, blockID, world, startX + imageX, startZ + imageY, height, solid);
            this.mapData[this.zoom].setLight(imageX, imageY, light);
        }
        else {
            light = this.mapData[this.zoom].getLight(imageX, imageY);
        }
        if (light == 0) {
            color24 = 0;
        }
        else if (light != 255) {
            color24 = this.colorManager.colorMultiplier(color24, light);
        }
        if (this.options.waterTransparency) {
            final Material material = ((Block)Block.blockRegistry.getObjectForID(blockID)).getMaterial();
            if (material == Material.water || material == Material.ice) {
                int seafloorHeight;
                if (needHeightAndID) {
                    seafloorHeight = this.getSeafloorHeight(world, startX + imageX, startZ + imageY, height);
                    this.mapData[this.zoom].setOceanFloorHeight(imageX, imageY, seafloorHeight);
                }
                else {
                    seafloorHeight = this.mapData[this.zoom].getOceanFloorHeight(imageX, imageY);
                }
                int seafloorColor = 0;
                if (needHeightAndID) {
                    final Block block2 = world.getBlock(startX + imageX, seafloorHeight - 1, startZ + imageY);
                    blockID = Block.blockRegistry.getIDForObject(block2);
                    metadata = world.getBlockMetadata(startX + imageX, seafloorHeight - 1, startZ + imageY);
                    if (block2.getMaterial() == Material.water) {
                        blockID = BlockIDRepository.airID;
                        metadata = 0;
                    }
                    if (this.options.biomes && blockID != this.mapData[this.zoom].getOceanFloorMaterial(imageX, imageY)) {
                        blockChangeForcedTint = true;
                    }
                    this.mapData[this.zoom].setOceanFloorMaterial(imageX, imageY, blockID);
                    this.mapData[this.zoom].setOceanFloorMetadata(imageX, imageY, metadata);
                }
                else {
                    blockID = this.mapData[this.zoom].getOceanFloorMaterial(imageX, imageY);
                    metadata = this.mapData[this.zoom].getOceanFloorMetadata(imageX, imageY);
                }
                if (this.options.biomes) {
                    seafloorColor = this.colorManager.getBlockColor(blockID, metadata, biomeID);
                }
                else {
                    seafloorColor = this.colorManager.getBlockColorWithDefaultTint(blockID, metadata, biomeID);
                }
                if (this.options.biomes && blockID != -1) {
                    int tint2 = -1;
                    if (needTint || blockChangeForcedTint) {
                        tint2 = this.getBiomeTint(blockID, metadata, startX + imageX, seafloorHeight - 1, startZ + imageY);
                        this.mapData[this.zoom].setOceanFloorBiomeTint(imageX, imageY, tint2);
                    }
                    else {
                        tint2 = this.mapData[this.zoom].getOceanFloorBiomeTint(imageX, imageY);
                    }
                    if (tint2 != -1) {
                        seafloorColor = this.colorManager.colorMultiplier(seafloorColor, tint2);
                    }
                }
                seafloorColor = this.applyHeight(seafloorColor, nether, netherPlayerInOpen, caves, world, multi, startX, startZ, imageX, imageY, seafloorHeight, solid, 0);
                int seafloorLight = 255;
                if (needLight) {
                    seafloorLight = this.getLight(seafloorColor, blockID, world, startX + imageX, startZ + imageY, seafloorHeight, solid);
                    if (this.options.lightmap && material == Material.ice && (seafloorHeight == height - 1 || world.getBlock(startX + imageX, seafloorHeight, startZ + imageY).getMaterial() == Material.ice)) {
                        seafloorLight = this.colorManager.colorMultiplier(seafloorLight, 5592405);
                    }
                    this.mapData[this.zoom].setOceanFloorLight(imageX, imageY, seafloorLight);
                }
                else {
                    seafloorLight = this.mapData[this.zoom].getOceanFloorLight(imageX, imageY);
                }
                if (seafloorLight == 0) {
                    seafloorColor = 0;
                }
                else if (seafloorLight != 255) {
                    seafloorColor = this.colorManager.colorMultiplier(seafloorColor, seafloorLight);
                }
                color24 = this.colorManager.colorAdder(color24, seafloorColor);
            }
        }
        if (this.options.blockTransparency) {
            int transparentHeight = -1;
            if (needHeightAndID) {
                transparentHeight = this.getTransparentHeight(nether, netherPlayerInOpen, caves, world, startX + imageX, startZ + imageY, height);
                this.mapData[this.zoom].setTransparentHeight(imageX, imageY, transparentHeight);
            }
            else {
                transparentHeight = this.mapData[this.zoom].getTransparentHeight(imageX, imageY);
            }
            if (needHeightAndID) {
                if (transparentHeight != -1 && transparentHeight > height) {
                    final Block block3 = world.getBlock(startX + imageX, transparentHeight - 1, startZ + imageY);
                    blockID = Block.blockRegistry.getIDForObject(block3);
                    metadata = world.getBlockMetadata(startX + imageX, transparentHeight - 1, startZ + imageY);
                }
                else {
                    blockID = 0;
                    metadata = 0;
                }
                if (this.options.biomes && blockID != this.mapData[this.zoom].getTransparentId(imageX, imageY)) {
                    blockChangeForcedTint = true;
                }
                this.mapData[this.zoom].setTransparentId(imageX, imageY, blockID);
                this.mapData[this.zoom].setTransparentMetadata(imageX, imageY, metadata);
            }
            else {
                blockID = this.mapData[this.zoom].getTransparentId(imageX, imageY);
                metadata = this.mapData[this.zoom].getTransparentMetadata(imageX, imageY);
            }
            if (blockID != 0) {
                int transparentColor = 0;
                if (this.options.biomes) {
                    transparentColor = this.colorManager.getBlockColor(blockID, metadata, biomeID);
                }
                else {
                    transparentColor = this.colorManager.getBlockColorWithDefaultTint(blockID, metadata, biomeID);
                }
                if (this.options.biomes) {
                    int tint3 = -1;
                    if (needTint || blockChangeForcedTint) {
                        tint3 = this.getBiomeTint(blockID, metadata, startX + imageX, height, startZ + imageY);
                        this.mapData[this.zoom].setTransparentBiomeTint(imageX, imageY, tint3);
                    }
                    else {
                        tint3 = this.mapData[this.zoom].getTransparentBiomeTint(imageX, imageY);
                    }
                    if (tint3 != -1) {
                        transparentColor = this.colorManager.colorMultiplier(transparentColor, tint3);
                    }
                }
                transparentColor = this.applyHeight(transparentColor, nether, netherPlayerInOpen, caves, world, multi, startX, startZ, imageX, imageY, transparentHeight, solid, 2);
                int transparentLight = 255;
                if (needLight) {
                    transparentLight = this.getLight(transparentColor, blockID, world, startX + imageX, startZ + imageY, transparentHeight, solid);
                    this.mapData[this.zoom].setTransparentLight(imageX, imageY, transparentLight);
                }
                else {
                    transparentLight = this.mapData[this.zoom].getTransparentLight(imageX, imageY);
                }
                if (transparentLight == 0) {
                    transparentColor = 0;
                }
                else if (transparentLight != 255) {
                    transparentColor = this.colorManager.colorMultiplier(transparentColor, transparentLight);
                }
                color24 = this.colorManager.colorAdder(transparentColor, color24);
            }
        }
        if (this.options.biomeOverlay == 2) {
            int bc = 0;
            if (biomeID >= 0) {
                bc = BiomeGenBase.getBiomeGenArray()[biomeID].color;
            }
            final int red1 = bc >> 16 & 0xFF;
            final int green1 = bc >> 8 & 0xFF;
            final int blue1 = bc >> 0 & 0xFF;
            bc = (0x7F000000 | (red1 & 0xFF) << 16 | (green1 & 0xFF) << 8 | (blue1 & 0xFF));
            color24 = this.colorManager.colorAdder(bc, color24);
        }
        if (this.options.chunkGrid && ((startX + imageX) % 16 == 0 || (startZ + imageY) % 16 == 0)) {
            color24 = this.colorManager.colorAdder(2097152000, color24);
        }
        return color24;
    }
    
    private int getBiomeTint(final int id, final int metadata, final int x, final int y, final int z) {
        int tint = -1;
        if (this.colorManager.isOptifineInstalled()) {
            try {
                final Integer[] tints = this.colorManager.getBlockTintTables().get(id + " " + metadata);
                if (tints != null) {
                    int r = 0;
                    int g = 0;
                    int b = 0;
                    for (int t = -1; t <= 1; ++t) {
                        for (int s = -1; s <= 1; ++s) {
                            final int biomeTint = tints[this.world.getBiomeGenForCoords(x + s, z + t).biomeID];
                            r += (biomeTint & 0xFF0000) >> 16;
                            g += (biomeTint & 0xFF00) >> 8;
                            b += (biomeTint & 0xFF);
                        }
                    }
                    tint = (0xFF000000 | (r / 9 & 0xFF) << 16 | (g / 9 & 0xFF) << 8 | (b / 9 & 0xFF));
                }
                else {
                    tint = this.getBuiltInBiomeTint(id, metadata, x, y, z);
                }
            }
            catch (Exception e) {
                tint = this.getBuiltInBiomeTint(id, metadata, x, y, z);
            }
        }
        else {
            tint = this.getBuiltInBiomeTint(id, metadata, x, y, z);
        }
        return tint;
    }
    
    private int getBuiltInBiomeTint(final int id, final int metadata, final int x, final int y, final int z) {
        int tint = -1;
        if (id == BlockIDRepository.grassID || id == BlockIDRepository.leavesID || id == BlockIDRepository.leaves2ID || id == BlockIDRepository.tallGrassID || id == BlockIDRepository.vineID || id == BlockIDRepository.tallFlowerID || id == BlockIDRepository.waterID || id == BlockIDRepository.flowingWaterID || this.colorManager.getBiomeTintsAvailable().contains(id)) {
            tint = (((Block)Block.blockRegistry.getObjectForID(id)).colorMultiplier(this.world, x, y, z) | 0xFF000000);
        }
        return tint;
    }
    
    private final int getBlockHeight(final boolean nether, final boolean netherPlayerInOpen, final boolean caves, final World world, final int x, final int z, final int starty) {
        int height = world.getHeightValue(x, z);
        if ((!nether && !caves) || height < starty || (nether && starty > 125 && (!this.options.showCaves || netherPlayerInOpen))) {
            final int transHeight = world.getPrecipitationHeight(x, z);
            if (transHeight != height) {
                final Block block = world.getBlock(x, transHeight - 1, z);
                if (block.getMaterial() == Material.lava) {
                    height = transHeight;
                }
            }
            for (int heightCheck = (height >> 4) * 16 + 15; heightCheck < this.worldHeight; heightCheck += 16) {
                final Block block2 = world.getBlock(x, heightCheck, z);
                if (block2.getLightOpacity() > 0) {
                    height = heightCheck + 1;
                }
            }
            return height;
        }
        int y = this.lastY;
        Block block = world.getBlock(x, y, z);
        if (block.getLightOpacity() == 0 && block.getMaterial() != Material.lava) {
            while (y > 0) {
                --y;
                block = world.getBlock(x, y, z);
                if (block.getLightOpacity() > 0 || block.getMaterial() == Material.lava) {
                    return y + 1;
                }
            }
            return y;
        }
        while (y <= starty + 10) {
            if (y < ((nether && starty < 126) ? 127 : 255)) {
                ++y;
                block = world.getBlock(x, y, z);
                if (block.getLightOpacity() == 0 && block.getMaterial() != Material.lava) {
                    return y;
                }
                continue;
            }
        }
        return -1;
    }
    
    private final int getSeafloorHeight(final World world, final int x, final int z, final int height) {
        int seafloorHeight = height;
        for (Block block = world.getBlock(x, seafloorHeight - 1, z); block.getLightOpacity() < 5 && block.getMaterial() != Material.leaves && seafloorHeight > 1; --seafloorHeight, block = world.getBlock(x, seafloorHeight - 1, z)) {}
        return seafloorHeight;
    }
    
    private final int getTransparentHeight(final boolean nether, final boolean netherPlayerInOpen, final boolean caves, final World world, final int x, final int z, final int height) {
        int transHeight = -1;
        if ((caves || nether) && (!nether || height <= 125 || (this.options.showCaves && !netherPlayerInOpen))) {
            transHeight = height + 1;
        }
        else {
            final int precipHeight = world.getPrecipitationHeight(x, z);
            if (precipHeight <= height) {
                transHeight = height + 1;
            }
            else {
                transHeight = precipHeight;
            }
        }
        final Material material = world.getBlock(x, transHeight - 1, z).getMaterial();
        if (material == Material.field_151597_y || material == Material.air) {
            transHeight = -1;
        }
        return transHeight;
    }
    
    private int applyHeight(int color24, final boolean nether, final boolean netherPlayerInOpen, final boolean caves, final World world, final int multi, final int startX, final int startZ, final int imageX, final int imageY, final int height, final boolean solid, final int layer) {
        if (color24 != this.colorManager.getAirColor() && color24 != 0) {
            int heightComp = 0;
            if ((this.options.heightmap || this.options.slopemap) && !solid) {
                int diff = 0;
                double sc = 0.0;
                if (this.options.slopemap) {
                    if (imageX > 0 && imageY < 32 * multi - 1) {
                        if (layer == 0) {
                            heightComp = this.mapData[this.zoom].getOceanFloorHeight(imageX - 1, imageY + 1);
                        }
                        if (layer == 1) {
                            heightComp = this.mapData[this.zoom].getHeight(imageX - 1, imageY + 1);
                        }
                        if (layer == 2) {
                            heightComp = this.mapData[this.zoom].getTransparentHeight(imageX - 1, imageY + 1);
                            if (heightComp == -1) {
                                final Block block = Block.getBlockById(this.mapData[this.zoom].getTransparentId(imageX, imageY));
                                if (block instanceof BlockGlass || block instanceof BlockStainedGlass) {
                                    heightComp = this.mapData[this.zoom].getHeight(imageX - 1, imageY + 1);
                                }
                            }
                        }
                    }
                    else {
                        if (layer == 0) {
                            final int baseHeight = this.getBlockHeight(nether, netherPlayerInOpen, caves, world, startX + imageX - 1, startZ + imageY + 1, this.lastY);
                            heightComp = this.getSeafloorHeight(world, startX + imageX - 1, startZ + imageY + 1, baseHeight);
                        }
                        if (layer == 1) {
                            heightComp = this.getBlockHeight(nether, netherPlayerInOpen, caves, world, startX + imageX - 1, startZ + imageY + 1, this.lastY);
                        }
                        if (layer == 2) {
                            final int baseHeight = this.getBlockHeight(nether, netherPlayerInOpen, caves, world, startX + imageX - 1, startZ + imageY + 1, this.lastY);
                            heightComp = this.getTransparentHeight(nether, netherPlayerInOpen, caves, world, startX + imageX - 1, startZ + imageY + 1, baseHeight);
                            if (heightComp == -1) {
                                final Block block2 = world.getBlock(startX + imageX, height - 1, startZ + imageY);
                                if (block2 instanceof BlockGlass || block2 instanceof BlockStainedGlass) {
                                    heightComp = baseHeight;
                                }
                            }
                        }
                    }
                    if (heightComp == -1) {
                        heightComp = height;
                    }
                    diff = heightComp - height;
                    if (diff != 0) {
                        sc = ((diff < 0) ? -1.0 : ((diff > 0) ? 1.0 : 0.0));
                        sc /= 8.0;
                    }
                    if (this.options.heightmap) {
                        diff = height - this.lastY;
                        final double heightsc = Math.log10(Math.abs(diff) / 8.0 + 1.0) / 3.0;
                        sc = ((diff > 0) ? (sc + heightsc) : (sc - heightsc));
                    }
                }
                else if (this.options.heightmap) {
                    diff = height - this.lastY;
                    sc = Math.log10(Math.abs(diff) / 8.0 + 1.0) / 1.8;
                    if (diff < 0) {
                        sc = 0.0 - sc;
                    }
                }
                final int alpha = color24 >> 24 & 0xFF;
                int r = color24 >> 16 & 0xFF;
                int g = color24 >> 8 & 0xFF;
                int b = color24 >> 0 & 0xFF;
                if (sc > 0.0) {
                    r += (int)(sc * (255 - r));
                    g += (int)(sc * (255 - g));
                    b += (int)(sc * (255 - b));
                }
                else if (sc < 0.0) {
                    sc = Math.abs(sc);
                    r -= (int)(sc * r);
                    g -= (int)(sc * g);
                    b -= (int)(sc * b);
                }
                color24 = alpha * 16777216 + r * 65536 + g * 256 + b;
            }
        }
        return color24;
    }
    
    private int getLight(final int color24, final int blockID, final World world, final int x, final int z, final int height, final boolean solid) {
        int i3 = 255;
        if (solid) {
            i3 = 0;
        }
        else if (color24 != this.colorManager.getAirColor() && color24 != 0 && this.options.lightmap && FPSBoost.LIGHTING.getValue()) {
            final Chunk chunk = world.getChunkFromBlockCoords(x, z);
            int blockLight = chunk.getSavedLightValue(EnumSkyBlock.Block, x & 0xF, Math.max(Math.min(height, 255), 0), z & 0xF);
            final int skyLight = chunk.getSavedLightValue(EnumSkyBlock.Sky, x & 0xF, Math.max(Math.min(height, 255), 0), z & 0xF);
            if (blockID == BlockIDRepository.lavaID && blockLight < 14) {
                blockLight = 14;
            }
            i3 = this.lightmapColors[blockLight + skyLight * 16];
        }
        return i3;
    }
    
    private void renderMap(final int x, final int y, final int scScale) {
        final boolean scaleChanged = this.scScale != scScale || this.options.squareMap != this.lastSquareMap;
        this.scScale = scScale;
        this.lastSquareMap = this.options.squareMap;
        if (GLUtils.hasAlphaBits) {
            GL11.glColorMask(false, false, false, true);
            GL11.glBindTexture(3553, 0);
            GL11.glBlendFunc(0, 0);
            GL11.glColor3f(0.0f, 0.0f, 255.0f);
            GL11.glBegin(7);
            GL11.glVertex2f((float)(x - 47), (float)(y + 47));
            GL11.glVertex2f((float)(x + 47), (float)(y + 47));
            GL11.glVertex2f((float)(x + 47), (float)(y - 47));
            GL11.glVertex2f((float)(x - 47), (float)(y - 47));
            GL11.glEnd();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glColorMask(true, true, true, true);
            GL11.glBlendFunc(770, 771);
            GLUtils.img(new ResourceLocation("voxelmap/" + (this.options.squareMap ? "images/square.png" : "images/circle.png")));
            GLUtils.drawPre();
            GLUtils.setMap(x, y);
            GLUtils.drawPost();
            GL11.glColorMask(true, true, true, true);
            GL11.glBlendFunc(772, 773);
            synchronized (this.coordinateLock) {
                if (this.imageChanged) {
                    this.imageChanged = false;
                    this.map[this.zoom].write();
                    this.lastImageX = this.lastX;
                    this.lastImageZ = this.lastZ;
                }
            }
            final float multi = 2.0f / (float)Math.pow(2.0, this.zoom);
            this.percentX = (float)(GameVariableAccessShim.xCoordDouble() - this.lastImageX);
            this.percentY = (float)(GameVariableAccessShim.zCoordDouble() - this.lastImageZ);
            this.percentX *= multi;
            this.percentY *= multi;
            if (this.zoom == 3) {
                GL11.glPushMatrix();
                GL11.glScalef(0.5f, 0.5f, 1.0f);
                GLUtils.disp(this.map[this.zoom].index);
                GL11.glPopMatrix();
            }
            else {
                GLUtils.disp(this.map[this.zoom].index);
            }
            GL11.glPushMatrix();
            GL11.glTranslatef((float)x, (float)y, 0.0f);
            GL11.glRotatef(this.options.squareMap ? ((float)this.northRotate) : (-this.direction + this.northRotate), 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef((float)(-x), (float)(-y), 0.0f);
            GL11.glTranslatef(-this.percentX, -this.percentY, 0.0f);
            if (this.options.filtering) {
                GL11.glTexParameteri(3553, 10241, 9729);
                GL11.glTexParameteri(3553, 10240, 9729);
            }
        }
        else if (GLUtils.fboEnabled) {
            GL11.glBindTexture(3553, 0);
            GL11.glPushAttrib(22528);
            GL11.glViewport(0, 0, 256, 256);
            GL11.glMatrixMode(5889);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0, 256.0, 256.0, 0.0, 1000.0, 3000.0);
            GL11.glMatrixMode(5888);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
            GLUtils.bindFrameBuffer();
            GL11.glDepthMask(false);
            GL11.glDisable(2929);
            if (scaleChanged) {
                GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                GL11.glClear(16384);
            }
            GL11.glBlendFunc(770, 0);
            GLUtils.img(new ResourceLocation("voxelmap/" + (this.options.squareMap ? "images/square.png" : "images/circle.png")));
            GLUtils.drawPre();
            GLUtils.ldrawthree(0.0, 256.0, 1.0, 0.0, 0.0);
            GLUtils.ldrawthree(256.0, 256.0, 1.0, 1.0, 0.0);
            GLUtils.ldrawthree(256.0, 0.0, 1.0, 1.0, 1.0);
            GLUtils.ldrawthree(0.0, 0.0, 1.0, 0.0, 1.0);
            GLUtils.drawPost();
            GL14.glBlendFuncSeparate(1, 0, 774, 0);
            synchronized (this.coordinateLock) {
                if (this.imageChanged) {
                    this.imageChanged = false;
                    this.map[this.zoom].write();
                    this.lastImageX = this.lastX;
                    this.lastImageZ = this.lastZ;
                }
            }
            final float multi = 2.0f / (float)Math.pow(2.0, this.zoom);
            this.percentX = (float)(GameVariableAccessShim.xCoordDouble() - this.lastImageX);
            this.percentY = (float)(GameVariableAccessShim.zCoordDouble() - this.lastImageZ);
            this.percentX *= multi;
            this.percentY *= multi;
            if (this.zoom == 3) {
                GL11.glPushMatrix();
                GL11.glScalef(0.5f, 0.5f, 1.0f);
                GLUtils.disp(this.map[this.zoom].index);
                GL11.glPopMatrix();
            }
            else {
                GLUtils.disp(this.map[this.zoom].index);
            }
            if (this.options.filtering) {
                GL11.glTexParameteri(3553, 10241, 9729);
                GL11.glTexParameteri(3553, 10240, 9729);
            }
            GL11.glTranslatef(128.0f, 128.0f, 0.0f);
            if (this.options.squareMap) {
                GL11.glRotatef((float)(-this.northRotate), 0.0f, 0.0f, 1.0f);
            }
            else {
                GL11.glRotatef(this.direction - this.northRotate, 0.0f, 0.0f, 1.0f);
            }
            GL11.glTranslatef(-128.0f, -128.0f, 0.0f);
            GL11.glTranslatef(-this.percentX * 4.0f, this.percentY * 4.0f, 0.0f);
            GLUtils.drawPre();
            GLUtils.ldrawthree(0.0, 256.0, 1.0, 0.0, 0.0);
            GLUtils.ldrawthree(256.0, 256.0, 1.0, 1.0, 0.0);
            GLUtils.ldrawthree(256.0, 0.0, 1.0, 1.0, 1.0);
            GLUtils.ldrawthree(0.0, 0.0, 1.0, 0.0, 1.0);
            GLUtils.drawPost();
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            GLUtils.unbindFrameBuffer();
            GL11.glMatrixMode(5889);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
            GL11.glPopMatrix();
            GL11.glPopAttrib();
            GL11.glPushMatrix();
            GL11.glBlendFunc(770, 0);
            GL11.glEnable(3008);
            GLUtils.disp(GLUtils.fboTextureID);
        }
        else {
            if (this.options.squareMap) {
                if (this.options.filtering && this.zoom == 0 && this.lastPercentXOver != this.percentX > 1.0f) {
                    this.lastPercentXOver = (this.percentX > 1.0f);
                    this.imageChanged = true;
                }
                if (this.options.filtering && this.zoom == 0 && this.lastPercentYOver != this.percentY > 1.0f) {
                    this.lastPercentYOver = (this.percentY > 1.0f);
                    this.imageChanged = true;
                }
            }
            if (this.imageChanged) {
                this.imageChanged = false;
                if (this.options.squareMap) {
                    synchronized (this.coordinateLock) {
                        this.map[this.zoom].write();
                        this.lastImageX = this.lastX;
                        this.lastImageZ = this.lastZ;
                    }
                }
                else {
                    final int diameter = this.map[this.zoom].getWidth();
                    if (this.roundImage != null) {
                        this.roundImage.baleet();
                    }
                    this.roundImage = new GLBufferedImage(diameter, diameter, 6);
                    final Ellipse2D.Double ellipse = new Ellipse2D.Double(this.zoom * 10 / 6, this.zoom * 10 / 6, diameter - this.zoom * 2, diameter - this.zoom * 2);
                    final Graphics2D gfx = this.roundImage.createGraphics();
                    gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    gfx.setClip(ellipse);
                    gfx.setColor(new Color(0.1f, 0.0f, 0.0f, 0.1f));
                    gfx.fillRect(0, 0, diameter, diameter);
                    synchronized (this.coordinateLock) {
                        gfx.drawImage(this.map[this.zoom], 0, 0, null);
                        this.lastImageX = this.lastX;
                        this.lastImageZ = this.lastZ;
                    }
                    gfx.dispose();
                    this.roundImage.write();
                }
            }
            final float multi = 2.0f / (float)Math.pow(2.0, this.zoom);
            this.percentX = (float)(GameVariableAccessShim.xCoordDouble() - this.lastImageX);
            this.percentY = (float)(GameVariableAccessShim.zCoordDouble() - this.lastImageZ);
            this.percentX *= multi;
            this.percentY *= multi;
            GL11.glBlendFunc(770, 0);
            if (this.zoom == 3) {
                GL11.glPushMatrix();
                GL11.glScalef(0.5f, 0.5f, 1.0f);
                GLUtils.disp(this.options.squareMap ? this.map[this.zoom].index : this.roundImage.index);
                GL11.glPopMatrix();
            }
            else {
                GLUtils.disp(this.options.squareMap ? this.map[this.zoom].index : this.roundImage.index);
            }
            if (this.options.filtering) {
                GL11.glTexParameteri(3553, 10241, 9729);
                GL11.glTexParameteri(3553, 10240, 9729);
            }
            GL11.glPushMatrix();
            GL11.glTranslatef((float)x, (float)y, 0.0f);
            GL11.glRotatef(this.options.squareMap ? ((float)this.northRotate) : (-this.direction + this.northRotate), 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef((float)(-x), (float)(-y), 0.0f);
            GL11.glTranslatef(-this.percentX, -this.percentY, 0.0f);
        }
        GLUtils.drawPre();
        GLUtils.setMap(x, y);
        GLUtils.drawPost();
        GL11.glPopMatrix();
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (this.options.squareMap) {
            this.drawSquareMapFrame(x, y);
        }
        else {
            this.drawRoundMapFrame(x, y);
        }
        final double lastXDouble = GameVariableAccessShim.xCoordDouble();
        final double lastZDouble = GameVariableAccessShim.zCoordDouble();
        for (final Waypoint pt : this.waypointManager.getWaypoints()) {
            if (pt.isActive()) {
                final double wayX = lastXDouble - pt.getX() - 0.5;
                final double wayY = lastZDouble - pt.getZ() - 0.5;
                float locate = (float)Math.toDegrees(Math.atan2(wayX, wayY));
                double hypot = Math.sqrt(wayX * wayX + wayY * wayY);
                boolean far = false;
                if (this.options.squareMap) {
                    far = (Math.abs(wayX) / (Math.pow(2.0, this.zoom) / 2.0) > 28.5 || Math.abs(wayY) / (Math.pow(2.0, this.zoom) / 2.0) > 28.5);
                    if (far) {
                        hypot = hypot / Math.max(Math.abs(wayX), Math.abs(wayY)) * 30.0;
                    }
                    else {
                        hypot /= Math.pow(2.0, this.zoom) / 2.0;
                    }
                }
                else {
                    locate += this.direction;
                    hypot /= Math.pow(2.0, this.zoom) / 2.0;
                    far = (hypot >= 31.0);
                    if (far) {
                        hypot = 34.0;
                    }
                }
                if (far) {
                    try {
                        GL11.glPushMatrix();
                        GL11.glColor3f(pt.red, pt.green, pt.blue);
                        if (scScale >= 3) {
                            GLUtils.img(new ResourceLocation("voxelmap/images/marker" + pt.imageSuffix + ".png"));
                        }
                        else {
                            GLUtils.img(new ResourceLocation("voxelmap/images/marker" + pt.imageSuffix + "Small.png"));
                        }
                        GL11.glTexParameteri(3553, 10241, 9729);
                        GL11.glTexParameteri(3553, 10240, 9729);
                        GL11.glTranslatef((float)x, (float)y, 0.0f);
                        GL11.glRotatef(-locate + this.northRotate, 0.0f, 0.0f, 1.0f);
                        GL11.glTranslatef((float)(-x), (float)(-y), 0.0f);
                        GL11.glTranslated(0.0, -hypot, 0.0);
                        GLUtils.drawPre();
                        GLUtils.setMap(x, (float)y, 16);
                        GLUtils.drawPost();
                    }
                    catch (Exception localException) {
                        this.error = "Error: marker overlay not found!";
                    }
                    finally {
                        GL11.glPopMatrix();
                    }
                }
                else {
                    try {
                        GL11.glPushMatrix();
                        GL11.glColor3f(pt.red, pt.green, pt.blue);
                        if (scScale >= 3) {
                            GLUtils.img(new ResourceLocation("voxelmap/images/waypoint" + pt.imageSuffix + ".png"));
                        }
                        else {
                            GLUtils.img(new ResourceLocation("voxelmap/images/waypoint" + pt.imageSuffix + "Small.png"));
                        }
                        GL11.glTexParameteri(3553, 10241, 9729);
                        GL11.glTexParameteri(3553, 10240, 9729);
                        GL11.glRotatef(-locate + this.northRotate, 0.0f, 0.0f, 1.0f);
                        GL11.glTranslated(0.0, -hypot, 0.0);
                        GL11.glRotatef(-(-locate + this.northRotate), 0.0f, 0.0f, 1.0f);
                        GLUtils.drawPre();
                        GLUtils.setMap(x, (float)y, 16);
                        GLUtils.drawPost();
                    }
                    catch (Exception localException) {
                        this.error = "Error: waypoint overlay not found!";
                    }
                    finally {
                        GL11.glPopMatrix();
                    }
                }
            }
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private void drawArrow(final int x, final int y) {
        try {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glBlendFunc(770, 771);
            GL11.glPushMatrix();
            GLUtils.img(new ResourceLocation("voxelmap/images/mmarrow.png"));
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTranslatef((float)x, (float)y, 0.0f);
            GL11.glRotatef(this.direction, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef((float)(-x), (float)(-y), 0.0f);
            GLUtils.drawPre();
            GLUtils.setMap(x, (float)y, 16);
            GLUtils.drawPost();
        }
        catch (Exception localException) {
            this.error = "Error: minimap arrow not found!";
        }
        finally {
            GL11.glPopMatrix();
        }
    }
    
    private void renderMapFull(final int scWidth, final int scHeight) {
        synchronized (this.coordinateLock) {
            if (this.imageChanged) {
                this.imageChanged = false;
                this.map[this.zoom].write();
                this.lastImageX = this.lastX;
                this.lastImageZ = this.lastZ;
            }
        }
        GLUtils.disp(this.map[this.zoom].index);
        if (this.options.filtering) {
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
        }
        GL11.glPushMatrix();
        GL11.glTranslatef(scWidth / 2.0f, scHeight / 2.0f, 0.0f);
        GL11.glRotatef((float)this.northRotate, 0.0f, 0.0f, 1.0f);
        GL11.glTranslatef(-(scWidth / 2.0f), -(scHeight / 2.0f), 0.0f);
        GLUtils.drawPre();
        final int left = scWidth / 2 - 128;
        final int top = scHeight / 2 - 128;
        GLUtils.ldrawone(left, top + 256, 67.0, 0.0, 1.0);
        GLUtils.ldrawone(left + 256, top + 256, 67.0, 1.0, 1.0);
        GLUtils.ldrawone(left + 256, top, 67.0, 1.0, 0.0);
        GLUtils.ldrawone(left, top, 67.0, 0.0, 0.0);
        GLUtils.drawPost();
        GL11.glPopMatrix();
        if (this.options.biomeOverlay > 0) {
            final int factor = (int)Math.pow(2.0, 3 - this.zoom);
            int minimumSize = (int)Math.pow(2.0, this.zoom);
            minimumSize *= minimumSize;
            final ArrayList<MapData.BiomeLabel> labels = this.mapData[this.zoom].getBiomeLabels();
            GL11.glDisable(2929);
            for (int t = 0; t < labels.size(); ++t) {
                final MapData.BiomeLabel label = labels.get(t);
                if (label.size > minimumSize) {
                    final String name = BiomeGenBase.getBiomeGenArray()[label.biomeInt].biomeName;
                    final int nameWidth = this.chkLen(name);
                    final int x = label.x * factor;
                    final int z = label.z * factor;
                    if (this.options.oldNorth) {
                        this.write(name, left + 256 - z - nameWidth / 2, top + x - 3, 16777215);
                    }
                    else {
                        this.write(name, left + x - nameWidth / 2, top + z - 3, 16777215);
                    }
                }
            }
            GL11.glEnable(2929);
        }
    }
    
    private void drawSquareMapFrame(final int x, final int y) {
        try {
            GLUtils.disp(this.colorManager.getMapImageInt());
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexParameteri(3553, 10242, 10496);
            GL11.glTexParameteri(3553, 10243, 10496);
            GLUtils.drawPre();
            GLUtils.setMap(x, y);
            GLUtils.drawPost();
        }
        catch (Exception localException) {
            this.error = "error: minimap overlay not found!";
        }
    }
    
    private void drawRoundMapFrame(final int x, final int y) {
        try {
            GLUtils.img(new ResourceLocation("voxelmap/images/roundmap.png"));
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
            GLUtils.drawPre();
            GLUtils.setMap(x, y);
            GLUtils.drawPost();
        }
        catch (Exception localException) {
            this.error = "Error: minimap overlay not found!";
        }
    }
    
    private void drawDirections(final int x, final int y) {
        float rotate;
        float distance;
        if (this.options.squareMap) {
            rotate = -90.0f;
            distance = 67.0f;
        }
        else {
            rotate = -this.direction - 90.0f;
            distance = 64.0f;
        }
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 1.0f);
        GL11.glTranslated(distance * Math.sin(Math.toRadians(-(rotate - 90.0))), distance * Math.cos(Math.toRadians(-(rotate - 90.0))), 0.0);
        this.write("N", x * 2 - 2, y * 2 - 4, 16777215);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 1.0f);
        GL11.glTranslated(distance * Math.sin(Math.toRadians(-rotate)), distance * Math.cos(Math.toRadians(-rotate)), 0.0);
        this.write("E", x * 2 - 2, y * 2 - 4, 16777215);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 1.0f);
        GL11.glTranslated(distance * Math.sin(Math.toRadians(-(rotate + 90.0))), distance * Math.cos(Math.toRadians(-(rotate + 90.0))), 0.0);
        this.write("S", x * 2 - 2, y * 2 - 4, 16777215);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 1.0f);
        GL11.glTranslated(distance * Math.sin(Math.toRadians(-(rotate + 180.0))), distance * Math.cos(Math.toRadians(-(rotate + 180.0))), 0.0);
        this.write("W", x * 2 - 2, y * 2 - 4, 16777215);
        GL11.glPopMatrix();
    }
    
    private void showCoords(final int x, final int y) {
        int textStart;
        if (y > this.scHeight - 37 - 32 - 4 - 15) {
            textStart = y - 32 - 4 - 9;
        }
        else {
            textStart = y + 32 + 4;
        }
        if (!this.options.hide && !this.fullscreenMap) {
            GL11.glPushMatrix();
            GL11.glScalef(0.5f, 0.5f, 1.0f);
            String xy = "";
            xy = this.dCoord(GameVariableAccessShim.xCoord()) + ", " + this.dCoord(GameVariableAccessShim.zCoord());
            int m = this.chkLen(xy) / 2;
            this.write(xy, x * 2 - m, textStart * 2, 16777215);
            xy = Integer.toString(GameVariableAccessShim.yCoord());
            m = this.chkLen(xy) / 2;
            this.write(xy, x * 2 - m, textStart * 2 + 10, 16777215);
            if (this.ztimer > 0) {
                m = this.chkLen(this.error) / 2;
                this.write(this.error, x * 2 - m, textStart * 2 + 19, 16777215);
            }
            GL11.glPopMatrix();
        }
        else {
            String stats = "";
            stats = "(" + this.dCoord(GameVariableAccessShim.xCoord()) + ", " + GameVariableAccessShim.yCoord() + ", " + this.dCoord(GameVariableAccessShim.zCoord()) + ") " + (int)this.direction + "'";
            int m = this.chkLen(stats) / 2;
            this.write(stats, this.scWidth / 2 - m, 5, 16777215);
            if (this.ztimer > 0) {
                m = this.chkLen(this.error) / 2;
                this.write(this.error, this.scWidth / 2 - m, 15, 16777215);
            }
        }
    }
    
    private String dCoord(final int paramInt1) {
        if (paramInt1 < 0) {
            return "-" + Math.abs(paramInt1);
        }
        if (paramInt1 > 0) {
            return "+" + paramInt1;
        }
        return " " + paramInt1;
    }
    
    private int chkLen(final String paramStr) {
        return this.fontRenderer.getStringWidth(paramStr);
    }
    
    private void write(final String paramStr, final int paramInt1, final int paramInt2, final int paramInt3) {
        this.fontRenderer.drawStringWithShadow(paramStr, paramInt1, paramInt2, paramInt3);
    }
    
    public void setMenuNull() {
        this.game.currentScreen = null;
    }
    
    public Object getMenu() {
        return this.game.currentScreen;
    }
    
    private void showMenu(final int scWidth, final int scHeight) {
        GL11.glBlendFunc(770, 771);
        int maxSize = 0;
        final int border = 2;
        final String head = this.sMenu[0];
        int height;
        for (height = 1; height < this.sMenu.length - 1; ++height) {
            if (this.chkLen(this.sMenu[height]) > maxSize) {
                maxSize = this.chkLen(this.sMenu[height]);
            }
        }
        final int title = this.chkLen(head);
        final int centerX = (int)((scWidth + 5) / 2.0);
        final int centerY = (int)((scHeight + 5) / 2.0);
        final String hide = this.sMenu[this.sMenu.length - 1];
        final int footer = this.chkLen(hide);
        GL11.glDisable(3553);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.7f);
        double leftX = centerX - title / 2.0 - border;
        double rightX = centerX + title / 2.0 + border;
        double topY = centerY - (height - 1) / 2.0 * 10.0 - border - 20.0;
        double botY = centerY - (height - 1) / 2.0 * 10.0 + border - 10.0;
        this.drawBox(leftX, rightX, topY, botY);
        leftX = centerX - maxSize / 2.0 - border;
        rightX = centerX + maxSize / 2.0 + border;
        topY = centerY - (height - 1) / 2.0 * 10.0 - border;
        botY = centerY + (height - 1) / 2.0 * 10.0 + border;
        this.drawBox(leftX, rightX, topY, botY);
        leftX = centerX - footer / 2.0 - border;
        rightX = centerX + footer / 2.0 + border;
        topY = centerY + (height - 1) / 2.0 * 10.0 - border + 10.0;
        botY = centerY + (height - 1) / 2.0 * 10.0 + border + 20.0;
        this.drawBox(leftX, rightX, topY, botY);
        GL11.glEnable(3553);
        this.write(head, centerX - title / 2, centerY - (height - 1) * 10 / 2 - 19, 16777215);
        for (int n = 1; n < height; ++n) {
            this.write(this.sMenu[n], centerX - maxSize / 2, centerY - (height - 1) * 10 / 2 + n * 10 - 9, 16777215);
        }
        this.write(hide, centerX - footer / 2, (scHeight + 5) / 2 + (height - 1) * 10 / 2 + 11, 16777215);
    }
    
    private void drawBox(final double leftX, final double rightX, final double topY, final double botY) {
        GLUtils.drawPre();
        GLUtils.ldrawtwo(leftX, botY, 0.0);
        GLUtils.ldrawtwo(rightX, botY, 0.0);
        GLUtils.ldrawtwo(rightX, topY, 0.0);
        GLUtils.ldrawtwo(leftX, topY, 0.0);
        GLUtils.drawPost();
    }
}
