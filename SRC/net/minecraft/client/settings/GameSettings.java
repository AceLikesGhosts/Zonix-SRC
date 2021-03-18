package net.minecraft.client.settings;

import com.google.gson.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.*;
import us.zonix.client.module.impl.*;
import net.minecraft.client.audio.*;
import com.google.common.collect.*;
import org.apache.commons.lang3.*;
import net.minecraft.client.resources.*;
import org.lwjgl.input.*;
import net.minecraft.client.renderer.*;
import com.google.common.primitives.*;
import java.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import java.lang.reflect.*;
import java.io.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.world.*;
import net.minecraft.src.*;
import org.apache.logging.log4j.*;
import net.minecraft.util.*;

public class GameSettings
{
    private static final Logger logger;
    private static final Gson gson;
    private static final ParameterizedType typeListString;
    private static final String[] GUISCALES;
    private static final String[] PARTICLES;
    private static final String[] AMBIENT_OCCLUSIONS;
    private static final String[] field_152391_aS;
    private static final String[] field_152392_aT;
    private static final String[] field_152393_aU;
    private static final String[] field_152394_aV;
    public float mouseSensitivity;
    public boolean invertMouse;
    public int renderDistanceChunks;
    public boolean viewBobbing;
    public boolean anaglyph;
    public boolean advancedOpengl;
    public boolean fboEnable;
    public int limitFramerate;
    public boolean fancyGraphics;
    public int ambientOcclusion;
    public int ofFogType;
    public float ofFogStart;
    public int ofMipmapType;
    public boolean ofLoadFar;
    public boolean ofOcclusionFancy;
    public boolean ofSmoothWorld;
    public boolean ofLazyChunkLoading;
    public float ofAoLevel;
    public int ofAaLevel;
    public int ofClouds;
    public float ofCloudsHeight;
    public int ofTrees;
    public int ofGrass;
    public int ofRain;
    public int ofWater;
    public int ofDroppedItems;
    public int ofBetterGrass;
    public int ofAutoSaveTicks;
    public boolean ofLagometer;
    public boolean ofProfiler;
    public boolean ofShowFps;
    public boolean ofSky;
    public boolean ofStars;
    public boolean ofSunMoon;
    public int ofVignette;
    public int ofChunkLoading;
    public int ofTime;
    public boolean ofClearWater;
    public boolean ofDepthFog;
    public boolean ofBetterSnow;
    public String ofFullscreenMode;
    public boolean ofSwampColors;
    public boolean ofRandomMobs;
    public boolean ofSmoothBiomes;
    public boolean ofCustomFonts;
    public boolean ofCustomColors;
    public boolean ofCustomSky;
    public boolean ofShowCapes;
    public int ofConnectedTextures;
    public boolean ofNaturalTextures;
    public int ofTranslucentBlocks;
    public boolean ofDynamicFov;
    public int ofDynamicLights;
    public int ofAnimatedWater;
    public int ofAnimatedLava;
    public boolean ofAnimatedFire;
    public boolean ofAnimatedPortal;
    public boolean ofAnimatedRedstone;
    public boolean ofAnimatedExplosion;
    public boolean ofAnimatedFlame;
    public boolean ofAnimatedSmoke;
    public boolean ofVoidParticles;
    public boolean ofWaterParticles;
    public boolean ofRainSplash;
    public boolean ofPortalParticles;
    public boolean ofPotionParticles;
    public boolean ofDrippingWaterLava;
    public boolean ofAnimatedTerrain;
    public boolean ofAnimatedItems;
    public boolean ofAnimatedTextures;
    public static final int DEFAULT = 0;
    public static final int FAST = 1;
    public static final int FANCY = 2;
    public static final int OFF = 3;
    public static final int ANIM_ON = 0;
    public static final int ANIM_GENERATED = 1;
    public static final int ANIM_OFF = 2;
    public static final int CL_DEFAULT = 0;
    public static final int CL_SMOOTH = 1;
    public static final int CL_THREADED = 2;
    public static final String DEFAULT_STR = "Default";
    public KeyBinding ofKeyBindZoom;
    public File optionsFileOF;
    public boolean clouds;
    public List resourcePacks;
    public EntityPlayer.EnumChatVisibility chatVisibility;
    public EntityPlayer.EnumChatVisibility lastChatVisibility;
    public boolean chatColours;
    public boolean chatLinks;
    public boolean chatLinksPrompt;
    public float chatOpacity;
    public boolean snooperEnabled;
    public boolean fullScreen;
    public boolean enableVsync;
    public boolean hideServerAddress;
    public boolean advancedItemTooltips;
    public boolean pauseOnLostFocus;
    public boolean showCape;
    public boolean touchscreen;
    public int overrideWidth;
    public int overrideHeight;
    public boolean heldItemTooltips;
    public float chatScale;
    public float chatWidth;
    public float chatHeightUnfocused;
    public float chatHeightFocused;
    public boolean showInventoryAchievementHint;
    public int mipmapLevels;
    public int anisotropicFiltering;
    private Map mapSoundLevels;
    public float field_152400_J;
    public float field_152401_K;
    public float field_152402_L;
    public float field_152403_M;
    public float field_152404_N;
    public int field_152405_O;
    public boolean field_152406_P;
    public String field_152407_Q;
    public int field_152408_R;
    public int field_152409_S;
    public int field_152410_T;
    public KeyBinding keyBindForward;
    public KeyBinding keyBindLeft;
    public KeyBinding keyBindBack;
    public KeyBinding keyBindRight;
    public KeyBinding keyBindJump;
    public KeyBinding keyBindSneak;
    public KeyBinding keyBindInventory;
    public KeyBinding keyBindUseItem;
    public KeyBinding keyBindDrop;
    public KeyBinding keyBindAttack;
    public KeyBinding keyBindPickBlock;
    public KeyBinding keyBindSprint;
    public KeyBinding keyBindWalk;
    public KeyBinding keyBindChat;
    public KeyBinding keyBindPlayerList;
    public KeyBinding keyBindCommand;
    public KeyBinding keyBindScreenshot;
    public KeyBinding keyBindTogglePerspective;
    public KeyBinding keyBindSmoothCamera;
    public KeyBinding keyBindToggleChat;
    public KeyBinding keyBindPerspectiveToggle;
    public KeyBinding keyBindPerspective;
    public KeyBinding field_152395_am;
    public KeyBinding[] keyBindsHotbar;
    public KeyBinding[] keyBindings;
    protected Minecraft mc;
    public File optionsFile;
    public EnumDifficulty difficulty;
    public boolean hideGUI;
    public boolean cinematicMode;
    public int thirdPersonView;
    public boolean showDebugInfo;
    public boolean showDebugProfilerChart;
    public String lastServer;
    public boolean noclip;
    public boolean smoothCamera;
    public boolean debugCamEnable;
    public float noclipRate;
    public float debugCamRate;
    public float fovSetting;
    private float gammaSetting;
    public float saturation;
    public int guiScale;
    public int particleSetting;
    public String language;
    public boolean forceUnicodeFont;
    public KeyBinding keyBindOpenUi;
    private boolean init;
    
    public float getGammaSetting() {
        return FPSBoost.LIGHTING.getValue() ? this.gammaSetting : 100.0f;
    }
    
    public GameSettings(final Minecraft par1Minecraft, final File par2File) {
        this.mouseSensitivity = 0.5f;
        this.renderDistanceChunks = -1;
        this.viewBobbing = true;
        this.fboEnable = true;
        this.limitFramerate = 120;
        this.fancyGraphics = true;
        this.ambientOcclusion = 2;
        this.ofFogType = 1;
        this.ofFogStart = 0.8f;
        this.ofMipmapType = 0;
        this.ofLoadFar = false;
        this.ofOcclusionFancy = false;
        this.ofSmoothWorld = Config.isSingleProcessor();
        this.ofLazyChunkLoading = Config.isSingleProcessor();
        this.ofAoLevel = 1.0f;
        this.ofAaLevel = 0;
        this.ofClouds = 0;
        this.ofCloudsHeight = 0.0f;
        this.ofTrees = 0;
        this.ofGrass = 0;
        this.ofRain = 0;
        this.ofWater = 0;
        this.ofDroppedItems = 0;
        this.ofBetterGrass = 3;
        this.ofAutoSaveTicks = 4000;
        this.ofLagometer = false;
        this.ofProfiler = false;
        this.ofShowFps = false;
        this.ofSky = true;
        this.ofStars = true;
        this.ofSunMoon = true;
        this.ofVignette = 0;
        this.ofChunkLoading = 0;
        this.ofTime = 0;
        this.ofClearWater = false;
        this.ofDepthFog = true;
        this.ofBetterSnow = false;
        this.ofFullscreenMode = "Default";
        this.ofSwampColors = true;
        this.ofRandomMobs = true;
        this.ofSmoothBiomes = true;
        this.ofCustomFonts = true;
        this.ofCustomColors = true;
        this.ofCustomSky = true;
        this.ofShowCapes = true;
        this.ofConnectedTextures = 2;
        this.ofNaturalTextures = false;
        this.ofTranslucentBlocks = 2;
        this.ofDynamicFov = true;
        this.ofDynamicLights = 3;
        this.ofAnimatedWater = 0;
        this.ofAnimatedLava = 0;
        this.ofAnimatedFire = true;
        this.ofAnimatedPortal = true;
        this.ofAnimatedRedstone = true;
        this.ofAnimatedExplosion = true;
        this.ofAnimatedFlame = true;
        this.ofAnimatedSmoke = true;
        this.ofVoidParticles = true;
        this.ofWaterParticles = true;
        this.ofRainSplash = true;
        this.ofPortalParticles = true;
        this.ofPotionParticles = true;
        this.ofDrippingWaterLava = true;
        this.ofAnimatedTerrain = true;
        this.ofAnimatedItems = true;
        this.ofAnimatedTextures = true;
        this.clouds = true;
        this.resourcePacks = new ArrayList();
        this.keyBindOpenUi = new KeyBinding("Mod Menu", 54, "Zonix");
        this.chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
        this.chatColours = true;
        this.chatLinks = true;
        this.chatLinksPrompt = true;
        this.chatOpacity = 1.0f;
        this.snooperEnabled = true;
        this.enableVsync = true;
        this.pauseOnLostFocus = true;
        this.showCape = true;
        this.heldItemTooltips = true;
        this.chatScale = 1.0f;
        this.chatWidth = 1.0f;
        this.chatHeightUnfocused = 0.44366196f;
        this.chatHeightFocused = 1.0f;
        this.showInventoryAchievementHint = true;
        this.mipmapLevels = 4;
        this.anisotropicFiltering = 1;
        this.mapSoundLevels = Maps.newEnumMap((Class)SoundCategory.class);
        this.field_152400_J = 0.5f;
        this.field_152401_K = 1.0f;
        this.field_152402_L = 1.0f;
        this.field_152403_M = 0.5412844f;
        this.field_152404_N = 0.31690142f;
        this.field_152405_O = 1;
        this.field_152406_P = true;
        this.field_152407_Q = "";
        this.field_152408_R = 0;
        this.field_152409_S = 0;
        this.field_152410_T = 0;
        this.keyBindForward = new KeyBinding("key.forward", 17, "key.categories.movement");
        this.keyBindLeft = new KeyBinding("key.left", 30, "key.categories.movement");
        this.keyBindBack = new KeyBinding("key.back", 31, "key.categories.movement");
        this.keyBindRight = new KeyBinding("key.right", 32, "key.categories.movement");
        this.keyBindJump = new KeyBinding("key.jump", 57, "key.categories.movement");
        this.keyBindSneak = new KeyBinding("key.sneak", 42, "key.categories.movement");
        this.keyBindInventory = new KeyBinding("key.inventory", 18, "key.categories.inventory");
        this.keyBindUseItem = new KeyBinding("key.use", -99, "key.categories.gameplay");
        this.keyBindDrop = new KeyBinding("key.drop", 16, "key.categories.gameplay");
        this.keyBindAttack = new KeyBinding("key.attack", -100, "key.categories.gameplay");
        this.keyBindPickBlock = new KeyBinding("key.pickItem", -98, "key.categories.gameplay");
        this.keyBindSprint = new KeyBinding("key.sprint", 29, "key.categories.gameplay");
        this.keyBindWalk = new KeyBinding("key.walk", 29, "key.categories.gameplay");
        this.keyBindChat = new KeyBinding("key.chat", 20, "key.categories.multiplayer");
        this.keyBindPlayerList = new KeyBinding("key.playerlist", 15, "key.categories.multiplayer");
        this.keyBindCommand = new KeyBinding("key.command", 53, "key.categories.multiplayer");
        this.keyBindScreenshot = new KeyBinding("key.screenshot", 60, "key.categories.misc");
        this.keyBindTogglePerspective = new KeyBinding("key.togglePerspective", 63, "key.categories.misc");
        this.keyBindSmoothCamera = new KeyBinding("key.smoothCamera", 0, "key.categories.misc");
        this.keyBindToggleChat = new KeyBinding("Toggle Chat", 0, "key.categories.misc");
        this.keyBindPerspective = new KeyBinding("Alt Perspective", 56, "Zonix");
        this.keyBindPerspectiveToggle = new KeyBinding("Alt Perspective Toggle", 0, "Zonix");
        this.field_152395_am = new KeyBinding("key.fullscreen", 87, "key.categories.misc");
        this.keyBindsHotbar = new KeyBinding[] { new KeyBinding("key.hotbar.1", 2, "key.categories.inventory"), new KeyBinding("key.hotbar.2", 3, "key.categories.inventory"), new KeyBinding("key.hotbar.3", 4, "key.categories.inventory"), new KeyBinding("key.hotbar.4", 5, "key.categories.inventory"), new KeyBinding("key.hotbar.5", 6, "key.categories.inventory"), new KeyBinding("key.hotbar.6", 7, "key.categories.inventory"), new KeyBinding("key.hotbar.7", 8, "key.categories.inventory"), new KeyBinding("key.hotbar.8", 9, "key.categories.inventory"), new KeyBinding("key.hotbar.9", 10, "key.categories.inventory") };
        this.keyBindings = (KeyBinding[])ArrayUtils.addAll((Object[])new KeyBinding[] { this.keyBindAttack, this.keyBindUseItem, this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindPlayerList, this.keyBindPickBlock, this.keyBindCommand, this.keyBindScreenshot, this.keyBindTogglePerspective, this.keyBindSmoothCamera, this.keyBindSprint, this.keyBindToggleChat, this.keyBindPerspectiveToggle, this.keyBindPerspective, this.field_152395_am, this.keyBindOpenUi }, (Object[])this.keyBindsHotbar);
        this.difficulty = EnumDifficulty.NORMAL;
        this.lastServer = "";
        this.noclipRate = 1.0f;
        this.debugCamRate = 1.0f;
        this.fovSetting = 70.0f;
        this.language = "en_US";
        this.forceUnicodeFont = false;
        this.mc = par1Minecraft;
        this.optionsFile = new File(par2File, "options.txt");
        this.optionsFileOF = new File(par2File, "optionsof.txt");
        this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
        this.ofKeyBindZoom = new KeyBinding("Zoom", 29, "key.categories.misc");
        this.keyBindings = (KeyBinding[])ArrayUtils.add((Object[])this.keyBindings, (Object)this.ofKeyBindZoom);
        Options.RENDER_DISTANCE.setValueMax(32.0f);
        this.renderDistanceChunks = (par1Minecraft.isJava64bit() ? 12 : 8);
        this.loadOptions();
        Config.initGameSettings(this);
    }
    
    public void tick() {
        if (this.init) {
            return;
        }
        this.init = true;
        if (this.ofCustomColors) {
            CustomColorizer.update();
            this.mc.renderGlobal.loadRenderers();
        }
        if (this.ofCustomSky) {
            CustomSky.update();
        }
        if (this.ofCustomFonts) {
            this.mc.fontRenderer.onResourceManagerReload(Config.getResourceManager());
            this.mc.standardGalacticFontRenderer.onResourceManagerReload(Config.getResourceManager());
        }
    }
    
    public GameSettings() {
        this.mouseSensitivity = 0.5f;
        this.renderDistanceChunks = -1;
        this.viewBobbing = true;
        this.fboEnable = true;
        this.limitFramerate = 120;
        this.fancyGraphics = true;
        this.ambientOcclusion = 2;
        this.ofFogType = 1;
        this.ofFogStart = 0.8f;
        this.ofMipmapType = 0;
        this.ofLoadFar = false;
        this.ofOcclusionFancy = false;
        this.ofSmoothWorld = Config.isSingleProcessor();
        this.ofLazyChunkLoading = Config.isSingleProcessor();
        this.ofAoLevel = 1.0f;
        this.ofAaLevel = 0;
        this.ofClouds = 0;
        this.ofCloudsHeight = 0.0f;
        this.ofTrees = 0;
        this.ofGrass = 0;
        this.ofRain = 0;
        this.ofWater = 0;
        this.ofDroppedItems = 0;
        this.ofBetterGrass = 3;
        this.ofAutoSaveTicks = 4000;
        this.ofLagometer = false;
        this.ofProfiler = false;
        this.ofShowFps = false;
        this.ofSky = true;
        this.ofStars = true;
        this.ofSunMoon = true;
        this.ofVignette = 0;
        this.ofChunkLoading = 0;
        this.ofTime = 0;
        this.ofClearWater = false;
        this.ofDepthFog = true;
        this.ofBetterSnow = false;
        this.ofFullscreenMode = "Default";
        this.ofSwampColors = true;
        this.ofRandomMobs = true;
        this.ofSmoothBiomes = true;
        this.ofCustomFonts = true;
        this.ofCustomColors = true;
        this.ofCustomSky = true;
        this.ofShowCapes = true;
        this.ofConnectedTextures = 2;
        this.ofNaturalTextures = false;
        this.ofTranslucentBlocks = 2;
        this.ofDynamicFov = true;
        this.ofDynamicLights = 3;
        this.ofAnimatedWater = 0;
        this.ofAnimatedLava = 0;
        this.ofAnimatedFire = true;
        this.ofAnimatedPortal = true;
        this.ofAnimatedRedstone = true;
        this.ofAnimatedExplosion = true;
        this.ofAnimatedFlame = true;
        this.ofAnimatedSmoke = true;
        this.ofVoidParticles = true;
        this.ofWaterParticles = true;
        this.ofRainSplash = true;
        this.ofPortalParticles = true;
        this.ofPotionParticles = true;
        this.ofDrippingWaterLava = true;
        this.ofAnimatedTerrain = true;
        this.ofAnimatedItems = true;
        this.ofAnimatedTextures = true;
        this.clouds = true;
        this.resourcePacks = new ArrayList();
        this.keyBindOpenUi = new KeyBinding("Mod Menu", 54, "Zonix");
        this.chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
        this.chatColours = true;
        this.chatLinks = true;
        this.chatLinksPrompt = true;
        this.chatOpacity = 1.0f;
        this.snooperEnabled = true;
        this.enableVsync = true;
        this.pauseOnLostFocus = true;
        this.showCape = true;
        this.heldItemTooltips = true;
        this.chatScale = 1.0f;
        this.chatWidth = 1.0f;
        this.chatHeightUnfocused = 0.44366196f;
        this.chatHeightFocused = 1.0f;
        this.showInventoryAchievementHint = true;
        this.mipmapLevels = 4;
        this.anisotropicFiltering = 1;
        this.mapSoundLevels = Maps.newEnumMap((Class)SoundCategory.class);
        this.field_152400_J = 0.5f;
        this.field_152401_K = 1.0f;
        this.field_152402_L = 1.0f;
        this.field_152403_M = 0.5412844f;
        this.field_152404_N = 0.31690142f;
        this.field_152405_O = 1;
        this.field_152406_P = true;
        this.field_152407_Q = "";
        this.field_152408_R = 0;
        this.field_152409_S = 0;
        this.field_152410_T = 0;
        this.keyBindForward = new KeyBinding("key.forward", 17, "key.categories.movement");
        this.keyBindLeft = new KeyBinding("key.left", 30, "key.categories.movement");
        this.keyBindBack = new KeyBinding("key.back", 31, "key.categories.movement");
        this.keyBindRight = new KeyBinding("key.right", 32, "key.categories.movement");
        this.keyBindJump = new KeyBinding("key.jump", 57, "key.categories.movement");
        this.keyBindSneak = new KeyBinding("key.sneak", 42, "key.categories.movement");
        this.keyBindInventory = new KeyBinding("key.inventory", 18, "key.categories.inventory");
        this.keyBindUseItem = new KeyBinding("key.use", -99, "key.categories.gameplay");
        this.keyBindDrop = new KeyBinding("key.drop", 16, "key.categories.gameplay");
        this.keyBindAttack = new KeyBinding("key.attack", -100, "key.categories.gameplay");
        this.keyBindPickBlock = new KeyBinding("key.pickItem", -98, "key.categories.gameplay");
        this.keyBindSprint = new KeyBinding("key.sprint", 29, "key.categories.gameplay");
        this.keyBindChat = new KeyBinding("key.chat", 20, "key.categories.multiplayer");
        this.keyBindPlayerList = new KeyBinding("key.playerlist", 15, "key.categories.multiplayer");
        this.keyBindCommand = new KeyBinding("key.command", 53, "key.categories.multiplayer");
        this.keyBindScreenshot = new KeyBinding("key.screenshot", 60, "key.categories.misc");
        this.keyBindTogglePerspective = new KeyBinding("key.togglePerspective", 63, "key.categories.misc");
        this.keyBindSmoothCamera = new KeyBinding("key.smoothCamera", 0, "key.categories.misc");
        this.field_152395_am = new KeyBinding("key.fullscreen", 87, "key.categories.misc");
        this.keyBindsHotbar = new KeyBinding[] { new KeyBinding("key.hotbar.1", 2, "key.categories.inventory"), new KeyBinding("key.hotbar.2", 3, "key.categories.inventory"), new KeyBinding("key.hotbar.3", 4, "key.categories.inventory"), new KeyBinding("key.hotbar.4", 5, "key.categories.inventory"), new KeyBinding("key.hotbar.5", 6, "key.categories.inventory"), new KeyBinding("key.hotbar.6", 7, "key.categories.inventory"), new KeyBinding("key.hotbar.7", 8, "key.categories.inventory"), new KeyBinding("key.hotbar.8", 9, "key.categories.inventory"), new KeyBinding("key.hotbar.9", 10, "key.categories.inventory") };
        this.keyBindings = (KeyBinding[])ArrayUtils.addAll((Object[])new KeyBinding[] { this.keyBindAttack, this.keyBindUseItem, this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindPlayerList, this.keyBindPickBlock, this.keyBindCommand, this.keyBindScreenshot, this.keyBindTogglePerspective, this.keyBindSmoothCamera, this.keyBindSprint, this.field_152395_am }, (Object[])this.keyBindsHotbar);
        this.difficulty = EnumDifficulty.NORMAL;
        this.lastServer = "";
        this.noclipRate = 1.0f;
        this.debugCamRate = 1.0f;
        this.fovSetting = 70.0f;
        this.language = "en_US";
        this.forceUnicodeFont = false;
        this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
        this.ofKeyBindZoom = new KeyBinding("Zoom", 29, "key.categories.misc");
        this.keyBindings = (KeyBinding[])ArrayUtils.add((Object[])this.keyBindings, (Object)this.ofKeyBindZoom);
    }
    
    public static String getKeyDisplayString(final int par0) {
        return (par0 < 0) ? I18n.format("key.mouseButton", par0 + 101) : (Objects.equals(Keyboard.getKeyName(par0), "LMENU") ? "ALT" : Keyboard.getKeyName(par0));
    }
    
    public static boolean isKeyDown(final KeyBinding par0KeyBinding) {
        return par0KeyBinding.getKeyCode() != 0 && ((par0KeyBinding.getKeyCode() < 0) ? Mouse.isButtonDown(par0KeyBinding.getKeyCode() + 100) : Keyboard.isKeyDown(par0KeyBinding.getKeyCode()));
    }
    
    public void setKeyCodeSave(final KeyBinding p_151440_1_, final int p_151440_2_) {
        p_151440_1_.setKeyCode(p_151440_2_);
        this.saveOptions();
    }
    
    public void setOptionFloatValue(final Options par1EnumOptions, final float par2) {
        if (par1EnumOptions == Options.CLOUD_HEIGHT) {
            this.ofCloudsHeight = par2;
        }
        if (par1EnumOptions == Options.AO_LEVEL) {
            this.ofAoLevel = par2;
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.SENSITIVITY) {
            this.mouseSensitivity = par2;
        }
        if (par1EnumOptions == Options.FOV) {
            this.fovSetting = par2;
        }
        if (par1EnumOptions == Options.GAMMA) {
            this.gammaSetting = par2;
        }
        if (par1EnumOptions == Options.FRAMERATE_LIMIT) {
            this.limitFramerate = (int)par2;
            this.enableVsync = false;
            if (this.limitFramerate <= 0) {
                this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
                this.enableVsync = true;
            }
            this.updateVSync();
        }
        if (par1EnumOptions == Options.CHAT_OPACITY) {
            this.chatOpacity = par2;
            this.mc.ingameGUI.getChatGUI().func_146245_b();
        }
        if (par1EnumOptions == Options.CHAT_HEIGHT_FOCUSED) {
            this.chatHeightFocused = par2;
            this.mc.ingameGUI.getChatGUI().func_146245_b();
        }
        if (par1EnumOptions == Options.CHAT_HEIGHT_UNFOCUSED) {
            this.chatHeightUnfocused = par2;
            this.mc.ingameGUI.getChatGUI().func_146245_b();
        }
        if (par1EnumOptions == Options.CHAT_WIDTH) {
            System.out.println(par2);
            this.chatWidth = par2;
            this.mc.ingameGUI.getChatGUI().func_146245_b();
        }
        if (par1EnumOptions == Options.CHAT_SCALE) {
            this.chatScale = par2;
            this.mc.ingameGUI.getChatGUI().func_146245_b();
        }
        if (par1EnumOptions == Options.ANISOTROPIC_FILTERING) {
            final int var3 = this.anisotropicFiltering;
            this.anisotropicFiltering = (int)par2;
            if (var3 != par2) {
                this.mc.getTextureMapBlocks().func_147632_b(this.anisotropicFiltering);
                this.mc.scheduleResourcesRefresh();
            }
        }
        if (par1EnumOptions == Options.MIPMAP_LEVELS) {
            final int var3 = this.mipmapLevels;
            this.mipmapLevels = (int)par2;
            if (var3 != par2) {
                this.mc.getTextureMapBlocks().func_147633_a(this.mipmapLevels);
                this.mc.scheduleResourcesRefresh();
            }
        }
        if (par1EnumOptions == Options.RENDER_DISTANCE) {
            this.renderDistanceChunks = (int)par2;
        }
    }
    
    public void setOptionValue(final Options par1EnumOptions, final int par2) {
        if (par1EnumOptions == Options.FOG_FANCY) {
            switch (this.ofFogType) {
                case 1: {
                    this.ofFogType = 2;
                    if (!Config.isFancyFogAvailable()) {
                        this.ofFogType = 3;
                        break;
                    }
                    break;
                }
                case 2: {
                    this.ofFogType = 3;
                    break;
                }
                case 3: {
                    this.ofFogType = 1;
                    break;
                }
                default: {
                    this.ofFogType = 1;
                    break;
                }
            }
        }
        if (par1EnumOptions == Options.FOG_START) {
            this.ofFogStart += 0.2f;
            if (this.ofFogStart > 0.81f) {
                this.ofFogStart = 0.2f;
            }
        }
        if (par1EnumOptions == Options.MIPMAP_TYPE) {
            ++this.ofMipmapType;
            if (this.ofMipmapType > 3) {
                this.ofMipmapType = 0;
            }
            TextureUtils.refreshBlockTextures();
        }
        if (par1EnumOptions == Options.LOAD_FAR) {
            this.ofLoadFar = !this.ofLoadFar;
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.SMOOTH_WORLD) {
            this.ofSmoothWorld = !this.ofSmoothWorld;
            Config.updateAvailableProcessors();
            if (!Config.isSingleProcessor()) {
                this.ofSmoothWorld = false;
            }
            Config.updateThreadPriorities();
        }
        if (par1EnumOptions == Options.CLOUDS) {
            ++this.ofClouds;
            if (this.ofClouds > 3) {
                this.ofClouds = 0;
            }
        }
        if (par1EnumOptions == Options.TREES) {
            ++this.ofTrees;
            if (this.ofTrees > 2) {
                this.ofTrees = 0;
            }
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.GRASS) {
            ++this.ofGrass;
            if (this.ofGrass > 2) {
                this.ofGrass = 0;
            }
            RenderBlocks.fancyGrass = Config.isGrassFancy();
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.DROPPED_ITEMS) {
            ++this.ofDroppedItems;
            if (this.ofDroppedItems > 2) {
                this.ofDroppedItems = 0;
            }
        }
        if (par1EnumOptions == Options.RAIN) {
            ++this.ofRain;
            if (this.ofRain > 3) {
                this.ofRain = 0;
            }
        }
        if (par1EnumOptions == Options.WATER) {
            ++this.ofWater;
            if (this.ofWater > 2) {
                this.ofWater = 0;
            }
        }
        if (par1EnumOptions == Options.ANIMATED_WATER) {
            ++this.ofAnimatedWater;
            if (this.ofAnimatedWater > 2) {
                this.ofAnimatedWater = 0;
            }
        }
        if (par1EnumOptions == Options.ANIMATED_LAVA) {
            ++this.ofAnimatedLava;
            if (this.ofAnimatedLava > 2) {
                this.ofAnimatedLava = 0;
            }
        }
        if (par1EnumOptions == Options.ANIMATED_FIRE) {
            this.ofAnimatedFire = !this.ofAnimatedFire;
        }
        if (par1EnumOptions == Options.ANIMATED_PORTAL) {
            this.ofAnimatedPortal = !this.ofAnimatedPortal;
        }
        if (par1EnumOptions == Options.ANIMATED_REDSTONE) {
            this.ofAnimatedRedstone = !this.ofAnimatedRedstone;
        }
        if (par1EnumOptions == Options.ANIMATED_EXPLOSION) {
            this.ofAnimatedExplosion = !this.ofAnimatedExplosion;
        }
        if (par1EnumOptions == Options.ANIMATED_FLAME) {
            this.ofAnimatedFlame = !this.ofAnimatedFlame;
        }
        if (par1EnumOptions == Options.ANIMATED_SMOKE) {
            this.ofAnimatedSmoke = !this.ofAnimatedSmoke;
        }
        if (par1EnumOptions == Options.VOID_PARTICLES) {
            this.ofVoidParticles = !this.ofVoidParticles;
        }
        if (par1EnumOptions == Options.WATER_PARTICLES) {
            this.ofWaterParticles = !this.ofWaterParticles;
        }
        if (par1EnumOptions == Options.PORTAL_PARTICLES) {
            this.ofPortalParticles = !this.ofPortalParticles;
        }
        if (par1EnumOptions == Options.POTION_PARTICLES) {
            this.ofPotionParticles = !this.ofPotionParticles;
        }
        if (par1EnumOptions == Options.DRIPPING_WATER_LAVA) {
            this.ofDrippingWaterLava = !this.ofDrippingWaterLava;
        }
        if (par1EnumOptions == Options.ANIMATED_TERRAIN) {
            this.ofAnimatedTerrain = !this.ofAnimatedTerrain;
        }
        if (par1EnumOptions == Options.ANIMATED_TEXTURES) {
            this.ofAnimatedTextures = !this.ofAnimatedTextures;
        }
        if (par1EnumOptions == Options.ANIMATED_ITEMS) {
            this.ofAnimatedItems = !this.ofAnimatedItems;
        }
        if (par1EnumOptions == Options.RAIN_SPLASH) {
            this.ofRainSplash = !this.ofRainSplash;
        }
        if (par1EnumOptions == Options.LAGOMETER) {
            this.ofLagometer = !this.ofLagometer;
        }
        if (par1EnumOptions == Options.SHOW_FPS) {
            this.ofShowFps = !this.ofShowFps;
        }
        if (par1EnumOptions == Options.AUTOSAVE_TICKS) {
            this.ofAutoSaveTicks *= 10;
            if (this.ofAutoSaveTicks > 40000) {
                this.ofAutoSaveTicks = 40;
            }
        }
        if (par1EnumOptions == Options.BETTER_GRASS) {
            ++this.ofBetterGrass;
            if (this.ofBetterGrass > 3) {
                this.ofBetterGrass = 1;
            }
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.CONNECTED_TEXTURES) {
            ++this.ofConnectedTextures;
            if (this.ofConnectedTextures > 3) {
                this.ofConnectedTextures = 1;
            }
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.WEATHER) {
            FPSBoost.WEATHER.setValue(Boolean.valueOf(!FPSBoost.WEATHER.getValue()));
        }
        if (par1EnumOptions == Options.SKY) {
            this.ofSky = !this.ofSky;
        }
        if (par1EnumOptions == Options.STARS) {
            this.ofStars = !this.ofStars;
        }
        if (par1EnumOptions == Options.SUN_MOON) {
            this.ofSunMoon = !this.ofSunMoon;
        }
        if (par1EnumOptions == Options.VIGNETTE) {
            ++this.ofVignette;
            if (this.ofVignette > 2) {
                this.ofVignette = 0;
            }
        }
        if (par1EnumOptions == Options.TIME) {
            ++this.ofTime;
            if (this.ofTime > 3) {
                this.ofTime = 0;
            }
        }
        if (par1EnumOptions == Options.CLEAR_WATER) {
            this.ofClearWater = !this.ofClearWater;
            this.updateWaterOpacity();
        }
        if (par1EnumOptions == Options.DEPTH_FOG) {
            this.ofDepthFog = !this.ofDepthFog;
        }
        if (par1EnumOptions == Options.AA_LEVEL) {
            final int[] aaLevels = { 0, 2, 4, 6, 8, 12, 16 };
            final int old = this.ofAaLevel;
            if (old >= 16) {
                this.ofAaLevel = 0;
            }
            else {
                this.ofAaLevel = aaLevels[Ints.indexOf(aaLevels, old) + 1];
            }
            this.ofAaLevel = Config.limit(this.ofAaLevel, 0, 16);
        }
        if (par1EnumOptions == Options.PROFILER) {
            this.ofProfiler = !this.ofProfiler;
        }
        if (par1EnumOptions == Options.BETTER_SNOW) {
            this.ofBetterSnow = !this.ofBetterSnow;
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.SWAMP_COLORS) {
            this.ofSwampColors = !this.ofSwampColors;
            CustomColorizer.updateUseDefaultColorMultiplier();
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.RANDOM_MOBS) {
            this.ofRandomMobs = !this.ofRandomMobs;
        }
        if (par1EnumOptions == Options.SMOOTH_BIOMES) {
            this.ofSmoothBiomes = !this.ofSmoothBiomes;
            CustomColorizer.updateUseDefaultColorMultiplier();
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.CUSTOM_FONTS) {
            this.ofCustomFonts = !this.ofCustomFonts;
            this.mc.fontRenderer.onResourceManagerReload(Config.getResourceManager());
            this.mc.standardGalacticFontRenderer.onResourceManagerReload(Config.getResourceManager());
        }
        if (par1EnumOptions == Options.CUSTOM_COLORS) {
            this.ofCustomColors = !this.ofCustomColors;
            CustomColorizer.update();
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.CUSTOM_SKY) {
            this.ofCustomSky = !this.ofCustomSky;
            CustomSky.update();
        }
        if (par1EnumOptions == Options.SHOW_CAPES) {
            this.ofShowCapes = !this.ofShowCapes;
            this.mc.renderGlobal.updateCapes();
        }
        if (par1EnumOptions == Options.NATURAL_TEXTURES) {
            this.ofNaturalTextures = !this.ofNaturalTextures;
            NaturalTextures.update();
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.TRANSLUCENT_BLOCKS) {
            if (this.ofTranslucentBlocks == 1) {
                this.ofTranslucentBlocks = 2;
            }
            else {
                this.ofTranslucentBlocks = 1;
            }
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.LAZY_CHUNK_LOADING) {
            this.ofLazyChunkLoading = !this.ofLazyChunkLoading;
            Config.updateAvailableProcessors();
            if (!Config.isSingleProcessor()) {
                this.ofLazyChunkLoading = false;
            }
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.FULLSCREEN_MODE) {
            final List modeList = Arrays.asList(Config.getFullscreenModes());
            if (this.ofFullscreenMode.equals("Default")) {
                final String fullsc = modeList.get(0);
                if (fullsc != null) {
                    this.ofFullscreenMode = fullsc;
                }
            }
            else {
                int index = modeList.indexOf(this.ofFullscreenMode);
                if (index < 0) {
                    this.ofFullscreenMode = "Default";
                }
                else if (++index >= modeList.size()) {
                    this.ofFullscreenMode = "Default";
                }
                else {
                    this.ofFullscreenMode = modeList.get(index);
                }
            }
        }
        if (par1EnumOptions == Options.DYNAMIC_FOV) {
            this.ofDynamicFov = !this.ofDynamicFov;
        }
        if (par1EnumOptions == Options.DYNAMIC_LIGHTS) {
            switch (this.ofDynamicLights) {
                case 1: {
                    this.ofDynamicLights = 2;
                    break;
                }
                case 3: {
                    this.ofDynamicLights = 1;
                    break;
                }
                default: {
                    this.ofDynamicLights = 3;
                    break;
                }
            }
            DynamicLights.removeLights(this.mc.renderGlobal);
        }
        if (par1EnumOptions == Options.HELD_ITEM_TOOLTIPS) {
            this.heldItemTooltips = !this.heldItemTooltips;
        }
        if (par1EnumOptions == Options.INVERT_MOUSE) {
            this.invertMouse = !this.invertMouse;
        }
        if (par1EnumOptions == Options.GUI_SCALE) {
            this.guiScale = (this.guiScale + par2 & 0x3);
        }
        if (par1EnumOptions == Options.PARTICLES) {
            this.particleSetting = (this.particleSetting + par2) % 3;
        }
        if (par1EnumOptions == Options.VIEW_BOBBING) {
            this.viewBobbing = !this.viewBobbing;
        }
        if (par1EnumOptions == Options.RENDER_CLOUDS) {
            this.clouds = !this.clouds;
        }
        if (par1EnumOptions == Options.FORCE_UNICODE_FONT) {
            this.forceUnicodeFont = !this.forceUnicodeFont;
            this.mc.fontRenderer.setUnicodeFlag(this.mc.getLanguageManager().isCurrentLocaleUnicode() || this.forceUnicodeFont);
        }
        if (par1EnumOptions == Options.ADVANCED_OPENGL) {
            if (!Config.isOcclusionAvailable()) {
                this.ofOcclusionFancy = false;
                this.advancedOpengl = false;
            }
            else if (!this.advancedOpengl) {
                this.advancedOpengl = true;
                this.ofOcclusionFancy = false;
            }
            else if (!this.ofOcclusionFancy) {
                this.ofOcclusionFancy = true;
            }
            else {
                this.ofOcclusionFancy = false;
                this.advancedOpengl = false;
            }
            this.mc.renderGlobal.setAllRenderersVisible();
        }
        if (par1EnumOptions == Options.FBO_ENABLE) {
            this.fboEnable = !this.fboEnable;
        }
        if (par1EnumOptions == Options.DIFFICULTY) {
            this.difficulty = EnumDifficulty.getDifficultyEnum(this.difficulty.getDifficultyId() + par2 & 0x3);
        }
        if (par1EnumOptions == Options.GRAPHICS) {
            this.fancyGraphics = !this.fancyGraphics;
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.AMBIENT_OCCLUSION) {
            this.ambientOcclusion = (this.ambientOcclusion + par2) % 3;
            this.mc.renderGlobal.loadRenderers();
        }
        if (par1EnumOptions == Options.CHAT_VISIBILITY) {
            this.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility((this.chatVisibility.getChatVisibility() + par2) % 3);
        }
        if (par1EnumOptions == Options.STREAM_COMPRESSION) {
            this.field_152405_O = (this.field_152405_O + par2) % 3;
        }
        if (par1EnumOptions == Options.STREAM_SEND_METADATA) {
            this.field_152406_P = !this.field_152406_P;
        }
        if (par1EnumOptions == Options.STREAM_CHAT_ENABLED) {
            this.field_152408_R = (this.field_152408_R + par2) % 3;
        }
        if (par1EnumOptions == Options.STREAM_CHAT_USER_FILTER) {
            this.field_152409_S = (this.field_152409_S + par2) % 3;
        }
        if (par1EnumOptions == Options.STREAM_MIC_TOGGLE_BEHAVIOR) {
            this.field_152410_T = (this.field_152410_T + par2) % 2;
        }
        if (par1EnumOptions == Options.CHAT_COLOR) {
            this.chatColours = !this.chatColours;
        }
        if (par1EnumOptions == Options.CHAT_LINKS) {
            this.chatLinks = !this.chatLinks;
        }
        if (par1EnumOptions == Options.CHAT_LINKS_PROMPT) {
            this.chatLinksPrompt = !this.chatLinksPrompt;
        }
        if (par1EnumOptions == Options.SNOOPER_ENABLED) {
            this.snooperEnabled = !this.snooperEnabled;
        }
        if (par1EnumOptions == Options.SHOW_CAPE) {
            this.showCape = !this.showCape;
        }
        if (par1EnumOptions == Options.TOUCHSCREEN) {
            this.touchscreen = !this.touchscreen;
        }
        if (par1EnumOptions == Options.USE_FULLSCREEN) {
            this.fullScreen = !this.fullScreen;
            if (this.mc.isFullScreen() != this.fullScreen) {
                this.mc.toggleFullscreen();
            }
        }
        if (par1EnumOptions == Options.ENABLE_VSYNC) {
            Display.setVSyncEnabled(this.enableVsync = !this.enableVsync);
        }
        this.saveOptions();
    }
    
    public float getOptionFloatValue(final Options par1EnumOptions) {
        return (par1EnumOptions == Options.CLOUD_HEIGHT) ? this.ofCloudsHeight : ((par1EnumOptions == Options.AO_LEVEL) ? this.ofAoLevel : ((par1EnumOptions == Options.FRAMERATE_LIMIT) ? ((this.limitFramerate == Options.FRAMERATE_LIMIT.getValueMax() && this.enableVsync) ? 0.0f : ((float)this.limitFramerate)) : ((par1EnumOptions == Options.FOV) ? this.fovSetting : ((par1EnumOptions == Options.GAMMA) ? this.gammaSetting : ((par1EnumOptions == Options.SATURATION) ? this.saturation : ((par1EnumOptions == Options.SENSITIVITY) ? this.mouseSensitivity : ((par1EnumOptions == Options.CHAT_OPACITY) ? this.chatOpacity : ((par1EnumOptions == Options.CHAT_HEIGHT_FOCUSED) ? this.chatHeightFocused : ((par1EnumOptions == Options.CHAT_HEIGHT_UNFOCUSED) ? this.chatHeightUnfocused : ((par1EnumOptions == Options.CHAT_SCALE) ? this.chatScale : ((par1EnumOptions == Options.CHAT_WIDTH) ? this.chatWidth : ((par1EnumOptions == Options.FRAMERATE_LIMIT) ? ((float)this.limitFramerate) : ((par1EnumOptions == Options.ANISOTROPIC_FILTERING) ? ((float)this.anisotropicFiltering) : ((par1EnumOptions == Options.MIPMAP_LEVELS) ? ((float)this.mipmapLevels) : ((par1EnumOptions == Options.RENDER_DISTANCE) ? ((float)this.renderDistanceChunks) : ((par1EnumOptions == Options.STREAM_BYTES_PER_PIXEL) ? this.field_152400_J : ((par1EnumOptions == Options.STREAM_VOLUME_MIC) ? this.field_152401_K : ((par1EnumOptions == Options.STREAM_VOLUME_SYSTEM) ? this.field_152402_L : ((par1EnumOptions == Options.STREAM_KBPS) ? this.field_152403_M : ((par1EnumOptions == Options.STREAM_FPS) ? this.field_152404_N : 0.0f))))))))))))))))))));
    }
    
    public boolean getOptionOrdinalValue(final Options par1EnumOptions) {
        switch (SwitchOptions.optionIds[par1EnumOptions.ordinal()]) {
            case 1: {
                return this.invertMouse;
            }
            case 2: {
                return this.viewBobbing;
            }
            case 3: {
                return this.anaglyph;
            }
            case 4: {
                return this.advancedOpengl;
            }
            case 5: {
                return this.fboEnable;
            }
            case 6: {
                return this.clouds;
            }
            case 7: {
                return this.chatColours;
            }
            case 8: {
                return this.chatLinks;
            }
            case 9: {
                return this.chatLinksPrompt;
            }
            case 10: {
                return this.snooperEnabled;
            }
            case 11: {
                return this.fullScreen;
            }
            case 12: {
                return this.enableVsync;
            }
            case 13: {
                return this.showCape;
            }
            case 14: {
                return this.touchscreen;
            }
            case 15: {
                return this.field_152406_P;
            }
            case 16: {
                return this.forceUnicodeFont;
            }
            default: {
                return false;
            }
        }
    }
    
    private static String getTranslation(final String[] par0ArrayOfStr, int par1) {
        if (par1 < 0 || par1 >= par0ArrayOfStr.length) {
            par1 = 0;
        }
        return I18n.format(par0ArrayOfStr[par1], new Object[0]);
    }
    
    public String getKeyBinding(final Options par1EnumOptions) {
        String var2 = I18n.format(par1EnumOptions.getEnumString(), new Object[0]) + ": ";
        if (var2 == null) {
            var2 = par1EnumOptions.getEnumString();
        }
        if (par1EnumOptions == Options.RENDER_DISTANCE) {
            final int var3 = (int)this.getOptionFloatValue(par1EnumOptions);
            String var4 = "Tiny";
            byte baseDist = 2;
            if (var3 >= 4) {
                var4 = "Short";
                baseDist = 4;
            }
            if (var3 >= 8) {
                var4 = "Normal";
                baseDist = 8;
            }
            if (var3 >= 16) {
                var4 = "Far";
                baseDist = 16;
            }
            if (var3 >= 32) {
                var4 = "Extreme";
                baseDist = 32;
            }
            final int diff = this.renderDistanceChunks - baseDist;
            String descr = var4;
            if (diff > 0) {
                descr = var4 + "+";
            }
            return var2 + var3 + " " + descr + "";
        }
        if (par1EnumOptions == Options.ADVANCED_OPENGL) {
            return this.advancedOpengl ? (this.ofOcclusionFancy ? (var2 + "Fancy") : (var2 + "Fast")) : (var2 + "OFF");
        }
        if (par1EnumOptions == Options.FOG_FANCY) {
            switch (this.ofFogType) {
                case 1: {
                    return var2 + "Fast";
                }
                case 2: {
                    return var2 + "Fancy";
                }
                case 3: {
                    return var2 + "OFF";
                }
                default: {
                    return var2 + "OFF";
                }
            }
        }
        else {
            if (par1EnumOptions == Options.FOG_START) {
                return var2 + this.ofFogStart;
            }
            if (par1EnumOptions == Options.MIPMAP_TYPE) {
                switch (this.ofMipmapType) {
                    case 0: {
                        return var2 + "Nearest";
                    }
                    case 1: {
                        return var2 + "Linear";
                    }
                    case 2: {
                        return var2 + "Bilinear";
                    }
                    case 3: {
                        return var2 + "Trilinear";
                    }
                    default: {
                        return var2 + "Nearest";
                    }
                }
            }
            else {
                if (par1EnumOptions == Options.LOAD_FAR) {
                    return this.ofLoadFar ? (var2 + "ON") : (var2 + "OFF");
                }
                if (par1EnumOptions == Options.PRELOADED_CHUNKS) {
                    return (FPSBoost.SMART_PERFORMANCE.getValue() < 10.0f) ? (var2 + "OFF") : (var2 + FPSBoost.SMART_PERFORMANCE.getValue() / 10.0f);
                }
                if (par1EnumOptions == Options.SMOOTH_FPS) {
                    return (FPSBoost.SMART_PERFORMANCE.getValue() >= 20.0f) ? (var2 + "ON") : (var2 + "OFF");
                }
                if (par1EnumOptions == Options.SMOOTH_WORLD) {
                    return this.ofSmoothWorld ? (var2 + "ON") : (var2 + "OFF");
                }
                if (par1EnumOptions == Options.CLOUDS) {
                    switch (this.ofClouds) {
                        case 1: {
                            return var2 + "Fast";
                        }
                        case 2: {
                            return var2 + "Fancy";
                        }
                        case 3: {
                            return var2 + "OFF";
                        }
                        default: {
                            return var2 + "Default";
                        }
                    }
                }
                else if (par1EnumOptions == Options.TREES) {
                    switch (this.ofTrees) {
                        case 1: {
                            return var2 + "Fast";
                        }
                        case 2: {
                            return var2 + "Fancy";
                        }
                        default: {
                            return var2 + "Default";
                        }
                    }
                }
                else if (par1EnumOptions == Options.GRASS) {
                    switch (this.ofGrass) {
                        case 1: {
                            return var2 + "Fast";
                        }
                        case 2: {
                            return var2 + "Fancy";
                        }
                        default: {
                            return var2 + "Default";
                        }
                    }
                }
                else if (par1EnumOptions == Options.DROPPED_ITEMS) {
                    switch (this.ofDroppedItems) {
                        case 1: {
                            return var2 + "Fast";
                        }
                        case 2: {
                            return var2 + "Fancy";
                        }
                        default: {
                            return var2 + "Default";
                        }
                    }
                }
                else if (par1EnumOptions == Options.RAIN) {
                    switch (this.ofRain) {
                        case 1: {
                            return var2 + "Fast";
                        }
                        case 2: {
                            return var2 + "Fancy";
                        }
                        case 3: {
                            return var2 + "OFF";
                        }
                        default: {
                            return var2 + "Default";
                        }
                    }
                }
                else if (par1EnumOptions == Options.WATER) {
                    switch (this.ofWater) {
                        case 1: {
                            return var2 + "Fast";
                        }
                        case 2: {
                            return var2 + "Fancy";
                        }
                        case 3: {
                            return var2 + "OFF";
                        }
                        default: {
                            return var2 + "Default";
                        }
                    }
                }
                else if (par1EnumOptions == Options.ANIMATED_WATER) {
                    switch (this.ofAnimatedWater) {
                        case 1: {
                            return var2 + "Dynamic";
                        }
                        case 2: {
                            return var2 + "OFF";
                        }
                        default: {
                            return var2 + "ON";
                        }
                    }
                }
                else if (par1EnumOptions == Options.ANIMATED_LAVA) {
                    switch (this.ofAnimatedLava) {
                        case 1: {
                            return var2 + "Dynamic";
                        }
                        case 2: {
                            return var2 + "OFF";
                        }
                        default: {
                            return var2 + "ON";
                        }
                    }
                }
                else {
                    if (par1EnumOptions == Options.ANIMATED_FIRE) {
                        return this.ofAnimatedFire ? (var2 + "ON") : (var2 + "OFF");
                    }
                    if (par1EnumOptions == Options.ANIMATED_PORTAL) {
                        return this.ofAnimatedPortal ? (var2 + "ON") : (var2 + "OFF");
                    }
                    if (par1EnumOptions == Options.ANIMATED_REDSTONE) {
                        return this.ofAnimatedRedstone ? (var2 + "ON") : (var2 + "OFF");
                    }
                    if (par1EnumOptions == Options.ANIMATED_EXPLOSION) {
                        return this.ofAnimatedExplosion ? (var2 + "ON") : (var2 + "OFF");
                    }
                    if (par1EnumOptions == Options.ANIMATED_FLAME) {
                        return this.ofAnimatedFlame ? (var2 + "ON") : (var2 + "OFF");
                    }
                    if (par1EnumOptions == Options.ANIMATED_SMOKE) {
                        return this.ofAnimatedSmoke ? (var2 + "ON") : (var2 + "OFF");
                    }
                    if (par1EnumOptions == Options.VOID_PARTICLES) {
                        return this.ofVoidParticles ? (var2 + "ON") : (var2 + "OFF");
                    }
                    if (par1EnumOptions == Options.WATER_PARTICLES) {
                        return this.ofWaterParticles ? (var2 + "ON") : (var2 + "OFF");
                    }
                    if (par1EnumOptions == Options.PORTAL_PARTICLES) {
                        return this.ofPortalParticles ? (var2 + "ON") : (var2 + "OFF");
                    }
                    if (par1EnumOptions == Options.POTION_PARTICLES) {
                        return this.ofPotionParticles ? (var2 + "ON") : (var2 + "OFF");
                    }
                    if (par1EnumOptions == Options.DRIPPING_WATER_LAVA) {
                        return this.ofDrippingWaterLava ? (var2 + "ON") : (var2 + "OFF");
                    }
                    if (par1EnumOptions == Options.ANIMATED_TERRAIN) {
                        return this.ofAnimatedTerrain ? (var2 + "ON") : (var2 + "OFF");
                    }
                    if (par1EnumOptions == Options.ANIMATED_TEXTURES) {
                        return this.ofAnimatedTextures ? (var2 + "ON") : (var2 + "OFF");
                    }
                    if (par1EnumOptions == Options.ANIMATED_ITEMS) {
                        return this.ofAnimatedItems ? (var2 + "ON") : (var2 + "OFF");
                    }
                    if (par1EnumOptions == Options.RAIN_SPLASH) {
                        return this.ofRainSplash ? (var2 + "ON") : (var2 + "OFF");
                    }
                    if (par1EnumOptions == Options.LAGOMETER) {
                        return this.ofLagometer ? (var2 + "ON") : (var2 + "OFF");
                    }
                    if (par1EnumOptions == Options.SHOW_FPS) {
                        return this.ofShowFps ? (var2 + "ON") : (var2 + "OFF");
                    }
                    if (par1EnumOptions == Options.AUTOSAVE_TICKS) {
                        return (this.ofAutoSaveTicks <= 40) ? (var2 + "Default (2s)") : ((this.ofAutoSaveTicks <= 400) ? (var2 + "20s") : ((this.ofAutoSaveTicks <= 4000) ? (var2 + "3min") : (var2 + "30min")));
                    }
                    if (par1EnumOptions == Options.BETTER_GRASS) {
                        switch (this.ofBetterGrass) {
                            case 1: {
                                return var2 + "Fast";
                            }
                            case 2: {
                                return var2 + "Fancy";
                            }
                            default: {
                                return var2 + "OFF";
                            }
                        }
                    }
                    else if (par1EnumOptions == Options.CONNECTED_TEXTURES) {
                        switch (this.ofConnectedTextures) {
                            case 1: {
                                return var2 + "Fast";
                            }
                            case 2: {
                                return var2 + "Fancy";
                            }
                            default: {
                                return var2 + "OFF";
                            }
                        }
                    }
                    else {
                        if (par1EnumOptions == Options.WEATHER) {
                            return FPSBoost.WEATHER.getValue() ? (var2 + "ON") : (var2 + "OFF");
                        }
                        if (par1EnumOptions == Options.SKY) {
                            return this.ofSky ? (var2 + "ON") : (var2 + "OFF");
                        }
                        if (par1EnumOptions == Options.STARS) {
                            return this.ofStars ? (var2 + "ON") : (var2 + "OFF");
                        }
                        if (par1EnumOptions == Options.SUN_MOON) {
                            return this.ofSunMoon ? (var2 + "ON") : (var2 + "OFF");
                        }
                        if (par1EnumOptions == Options.VIGNETTE) {
                            switch (this.ofVignette) {
                                case 1: {
                                    return var2 + "Fast";
                                }
                                case 2: {
                                    return var2 + "Fancy";
                                }
                                default: {
                                    return var2 + "Default";
                                }
                            }
                        }
                        else {
                            if (par1EnumOptions == Options.CHUNK_UPDATES) {
                                return var2 + FPSBoost.SMART_PERFORMANCE.getValue() / 20.0f;
                            }
                            if (par1EnumOptions == Options.CHUNK_UPDATES_DYNAMIC) {
                                return (FPSBoost.SMART_PERFORMANCE.getValue() >= 30.0f) ? (var2 + "ON") : (var2 + "OFF");
                            }
                            if (par1EnumOptions == Options.TIME) {
                                return (this.ofTime == 1) ? (var2 + "Day Only") : ((this.ofTime == 3) ? (var2 + "Night Only") : (var2 + "Default"));
                            }
                            if (par1EnumOptions == Options.CLEAR_WATER) {
                                return this.ofClearWater ? (var2 + "ON") : (var2 + "OFF");
                            }
                            if (par1EnumOptions == Options.DEPTH_FOG) {
                                return this.ofDepthFog ? (var2 + "ON") : (var2 + "OFF");
                            }
                            if (par1EnumOptions == Options.AA_LEVEL) {
                                return (this.ofAaLevel == 0) ? (var2 + "OFF") : (var2 + this.ofAaLevel);
                            }
                            if (par1EnumOptions == Options.PROFILER) {
                                return this.ofProfiler ? (var2 + "ON") : (var2 + "OFF");
                            }
                            if (par1EnumOptions == Options.BETTER_SNOW) {
                                return this.ofBetterSnow ? (var2 + "ON") : (var2 + "OFF");
                            }
                            if (par1EnumOptions == Options.SWAMP_COLORS) {
                                return this.ofSwampColors ? (var2 + "ON") : (var2 + "OFF");
                            }
                            if (par1EnumOptions == Options.RANDOM_MOBS) {
                                return this.ofRandomMobs ? (var2 + "ON") : (var2 + "OFF");
                            }
                            if (par1EnumOptions == Options.SMOOTH_BIOMES) {
                                return this.ofSmoothBiomes ? (var2 + "ON") : (var2 + "OFF");
                            }
                            if (par1EnumOptions == Options.CUSTOM_FONTS) {
                                return this.ofCustomFonts ? (var2 + "ON") : (var2 + "OFF");
                            }
                            if (par1EnumOptions == Options.CUSTOM_COLORS) {
                                return this.ofCustomColors ? (var2 + "ON") : (var2 + "OFF");
                            }
                            if (par1EnumOptions == Options.CUSTOM_SKY) {
                                return this.ofCustomSky ? (var2 + "ON") : (var2 + "OFF");
                            }
                            if (par1EnumOptions == Options.SHOW_CAPES) {
                                return this.ofShowCapes ? (var2 + "ON") : (var2 + "OFF");
                            }
                            if (par1EnumOptions == Options.NATURAL_TEXTURES) {
                                return this.ofNaturalTextures ? (var2 + "ON") : (var2 + "OFF");
                            }
                            if (par1EnumOptions == Options.FAST_MATH) {
                                return (FPSBoost.SMART_PERFORMANCE.getValue() >= 20.0f) ? (var2 + "ON") : (var2 + "OFF");
                            }
                            if (par1EnumOptions == Options.TRANSLUCENT_BLOCKS) {
                                return (this.ofTranslucentBlocks == 1) ? (var2 + "Fast") : (var2 + "Fancy");
                            }
                            if (par1EnumOptions == Options.LAZY_CHUNK_LOADING) {
                                return this.ofLazyChunkLoading ? (var2 + "ON") : (var2 + "OFF");
                            }
                            if (par1EnumOptions == Options.DYNAMIC_FOV) {
                                return this.ofDynamicFov ? (var2 + "ON") : (var2 + "OFF");
                            }
                            if (par1EnumOptions == Options.DYNAMIC_LIGHTS) {
                                return (this.ofDynamicLights == 1) ? (var2 + "Fast") : ((this.ofDynamicLights == 2) ? (var2 + "Fancy") : (var2 + "OFF"));
                            }
                            if (par1EnumOptions == Options.FULLSCREEN_MODE) {
                                return var2 + this.ofFullscreenMode;
                            }
                            if (par1EnumOptions == Options.HELD_ITEM_TOOLTIPS) {
                                return this.heldItemTooltips ? (var2 + "ON") : (var2 + "OFF");
                            }
                            if (par1EnumOptions == Options.FRAMERATE_LIMIT) {
                                final float var5 = this.getOptionFloatValue(par1EnumOptions);
                                return (var5 == 0.0f) ? (var2 + "VSync") : ((var5 == par1EnumOptions.valueMax) ? (var2 + I18n.format("options.framerateLimit.max", new Object[0])) : (var2 + (int)var5 + " fps"));
                            }
                            if (par1EnumOptions.getEnumFloat()) {
                                final float var5 = this.getOptionFloatValue(par1EnumOptions);
                                final float var6 = par1EnumOptions.normalizeValue(var5);
                                return (par1EnumOptions == Options.SENSITIVITY) ? ((var6 == 0.0f) ? (var2 + I18n.format("options.sensitivity.min", new Object[0])) : ((var6 == 1.0f) ? (var2 + I18n.format("options.sensitivity.max", new Object[0])) : (var2 + (int)(var6 * 200.0f) + "%"))) : ((par1EnumOptions == Options.FOV) ? ((var5 == 70.0f) ? (var2 + I18n.format("options.fov.min", new Object[0])) : ((var5 == 110.0f) ? (var2 + I18n.format("options.fov.max", new Object[0])) : (var2 + (int)var5))) : ((par1EnumOptions == Options.FRAMERATE_LIMIT) ? ((var5 == par1EnumOptions.valueMax) ? (var2 + I18n.format("options.framerateLimit.max", new Object[0])) : (var2 + (int)var5 + " fps")) : ((par1EnumOptions == Options.GAMMA) ? ((var6 == 0.0f) ? (var2 + I18n.format("options.gamma.min", new Object[0])) : ((var6 == 1.0f) ? (var2 + I18n.format("options.gamma.max", new Object[0])) : (var2 + "+" + (int)(var6 * 100.0f) + "%"))) : ((par1EnumOptions == Options.SATURATION) ? (var2 + (int)(var6 * 400.0f) + "%") : ((par1EnumOptions == Options.CHAT_OPACITY) ? (var2 + (int)(var6 * 90.0f + 10.0f) + "%") : ((par1EnumOptions == Options.CHAT_HEIGHT_UNFOCUSED) ? (var2 + GuiNewChat.func_146243_b(var6) + "px") : ((par1EnumOptions == Options.CHAT_HEIGHT_FOCUSED) ? (var2 + GuiNewChat.func_146243_b(var6) + "px") : ((par1EnumOptions == Options.CHAT_WIDTH) ? (var2 + GuiNewChat.func_146233_a(var6) + "px") : ((par1EnumOptions == Options.RENDER_DISTANCE) ? (var2 + (int)var5 + " chunks") : ((par1EnumOptions == Options.ANISOTROPIC_FILTERING) ? ((var5 == 1.0f) ? (var2 + I18n.format("options.off", new Object[0])) : (var2 + (int)var5)) : ((par1EnumOptions == Options.MIPMAP_LEVELS) ? ((var5 == 0.0f) ? (var2 + I18n.format("options.off", new Object[0])) : (var2 + (int)var5)) : ((par1EnumOptions == Options.STREAM_FPS) ? (var2 + " fps") : ((par1EnumOptions == Options.STREAM_KBPS) ? (var2 + " Kbps") : ((par1EnumOptions == Options.STREAM_BYTES_PER_PIXEL) ? var2 : ((var6 == 0.0f) ? (var2 + I18n.format("options.off", new Object[0])) : (var2 + (int)(var6 * 100.0f) + "%"))))))))))))))));
                            }
                            if (par1EnumOptions.getEnumBoolean()) {
                                final boolean var7 = this.getOptionOrdinalValue(par1EnumOptions);
                                return var7 ? (var2 + I18n.format("options.on", new Object[0])) : (var2 + I18n.format("options.off", new Object[0]));
                            }
                            if (par1EnumOptions == Options.DIFFICULTY) {
                                return var2 + I18n.format(this.difficulty.getDifficultyResourceKey(), new Object[0]);
                            }
                            if (par1EnumOptions == Options.GUI_SCALE) {
                                return var2 + getTranslation(GameSettings.GUISCALES, this.guiScale);
                            }
                            if (par1EnumOptions == Options.CHAT_VISIBILITY) {
                                return var2 + I18n.format(this.chatVisibility.getResourceKey(), new Object[0]);
                            }
                            if (par1EnumOptions == Options.PARTICLES) {
                                return var2 + getTranslation(GameSettings.PARTICLES, this.particleSetting);
                            }
                            if (par1EnumOptions == Options.AMBIENT_OCCLUSION) {
                                return var2 + getTranslation(GameSettings.AMBIENT_OCCLUSIONS, this.ambientOcclusion);
                            }
                            if (par1EnumOptions == Options.STREAM_COMPRESSION) {
                                return var2 + getTranslation(GameSettings.field_152391_aS, this.field_152405_O);
                            }
                            if (par1EnumOptions == Options.STREAM_CHAT_ENABLED) {
                                return var2 + getTranslation(GameSettings.field_152392_aT, this.field_152408_R);
                            }
                            if (par1EnumOptions == Options.STREAM_CHAT_USER_FILTER) {
                                return var2 + getTranslation(GameSettings.field_152393_aU, this.field_152409_S);
                            }
                            if (par1EnumOptions == Options.STREAM_MIC_TOGGLE_BEHAVIOR) {
                                return var2 + getTranslation(GameSettings.field_152394_aV, this.field_152410_T);
                            }
                            if (par1EnumOptions != Options.GRAPHICS) {
                                return var2;
                            }
                            if (this.fancyGraphics) {
                                return var2 + I18n.format("options.graphics.fancy", new Object[0]);
                            }
                            final String var8 = "options.graphics.fast";
                            return var2 + I18n.format("options.graphics.fast", new Object[0]);
                        }
                    }
                }
            }
        }
    }
    
    public void loadOptions() {
        try {
            if (!this.optionsFile.exists()) {
                return;
            }
            final BufferedReader var9 = new BufferedReader(new FileReader(this.optionsFile));
            String var10 = "";
            this.mapSoundLevels.clear();
            while ((var10 = var9.readLine()) != null) {
                try {
                    final String[] var11 = var10.split(":");
                    if (var11[0].equals("mouseSensitivity")) {
                        this.mouseSensitivity = this.parseFloat(var11[1]);
                    }
                    if (var11[0].equals("invertYMouse")) {
                        this.invertMouse = var11[1].equals("true");
                    }
                    if (var11[0].equals("fov")) {
                        this.fovSetting = this.parseFloat(var11[1]);
                    }
                    if (var11[0].equals("gamma")) {
                        this.gammaSetting = this.parseFloat(var11[1]);
                    }
                    if (var11[0].equals("saturation")) {
                        this.saturation = this.parseFloat(var11[1]);
                    }
                    if (var11[0].equals("fov")) {
                        this.fovSetting = this.parseFloat(var11[1]) * 40.0f + 70.0f;
                    }
                    if (var11[0].equals("renderDistance")) {
                        this.renderDistanceChunks = Integer.parseInt(var11[1]);
                    }
                    if (var11[0].equals("guiScale")) {
                        this.guiScale = Integer.parseInt(var11[1]);
                    }
                    if (var11[0].equals("particles")) {
                        this.particleSetting = Integer.parseInt(var11[1]);
                    }
                    if (var11[0].equals("bobView")) {
                        this.viewBobbing = var11[1].equals("true");
                    }
                    if (var11[0].equals("anaglyph3d")) {
                        this.anaglyph = var11[1].equals("true");
                    }
                    if (var11[0].equals("advancedOpengl")) {
                        this.advancedOpengl = var11[1].equals("true");
                    }
                    if (var11[0].equals("maxFps")) {
                        this.limitFramerate = Integer.parseInt(var11[1]);
                        this.enableVsync = false;
                        if (this.limitFramerate <= 0) {
                            this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
                            this.enableVsync = true;
                        }
                        this.updateVSync();
                    }
                    if (var11[0].equals("fboEnable")) {
                        this.fboEnable = var11[1].equals("true");
                    }
                    if (var11[0].equals("difficulty")) {
                        this.difficulty = EnumDifficulty.getDifficultyEnum(Integer.parseInt(var11[1]));
                    }
                    if (var11[0].equals("fancyGraphics")) {
                        this.fancyGraphics = var11[1].equals("true");
                    }
                    if (var11[0].equals("ao")) {
                        if (var11[1].equals("true")) {
                            this.ambientOcclusion = 2;
                        }
                        else if (var11[1].equals("false")) {
                            this.ambientOcclusion = 0;
                        }
                        else {
                            this.ambientOcclusion = Integer.parseInt(var11[1]);
                        }
                    }
                    if (var11[0].equals("clouds")) {
                        this.clouds = var11[1].equals("true");
                    }
                    if (var11[0].equals("resourcePacks")) {
                        this.resourcePacks = (List)GameSettings.gson.fromJson(var10.substring(var10.indexOf(58) + 1), (Type)GameSettings.typeListString);
                        if (this.resourcePacks == null) {
                            this.resourcePacks = new ArrayList();
                        }
                    }
                    if (var11[0].equals("lastServer") && var11.length >= 2) {
                        this.lastServer = var10.substring(var10.indexOf(58) + 1);
                    }
                    if (var11[0].equals("lang") && var11.length >= 2) {
                        this.language = var11[1];
                    }
                    if (var11[0].equals("chatVisibility")) {
                        this.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility(Integer.parseInt(var11[1]));
                    }
                    if (var11[0].equals("chatColors")) {
                        this.chatColours = var11[1].equals("true");
                    }
                    if (var11[0].equals("chatLinks")) {
                        this.chatLinks = var11[1].equals("true");
                    }
                    if (var11[0].equals("chatLinksPrompt")) {
                        this.chatLinksPrompt = var11[1].equals("true");
                    }
                    if (var11[0].equals("chatOpacity")) {
                        this.chatOpacity = this.parseFloat(var11[1]);
                    }
                    if (var11[0].equals("snooperEnabled")) {
                        this.snooperEnabled = var11[1].equals("true");
                    }
                    if (var11[0].equals("fullscreen")) {
                        this.fullScreen = var11[1].equals("true");
                    }
                    if (var11[0].equals("enableVsync")) {
                        this.enableVsync = var11[1].equals("true");
                        this.updateVSync();
                    }
                    if (var11[0].equals("hideServerAddress")) {
                        this.hideServerAddress = var11[1].equals("true");
                    }
                    if (var11[0].equals("advancedItemTooltips")) {
                        this.advancedItemTooltips = var11[1].equals("true");
                    }
                    if (var11[0].equals("pauseOnLostFocus")) {
                        this.pauseOnLostFocus = var11[1].equals("true");
                    }
                    if (var11[0].equals("showCape")) {
                        this.showCape = var11[1].equals("true");
                    }
                    if (var11[0].equals("touchscreen")) {
                        this.touchscreen = var11[1].equals("true");
                    }
                    if (var11[0].equals("overrideHeight")) {
                        this.overrideHeight = Integer.parseInt(var11[1]);
                    }
                    if (var11[0].equals("overrideWidth")) {
                        this.overrideWidth = Integer.parseInt(var11[1]);
                    }
                    if (var11[0].equals("heldItemTooltips")) {
                        this.heldItemTooltips = var11[1].equals("true");
                    }
                    if (var11[0].equals("chatHeightFocused")) {
                        this.chatHeightFocused = this.parseFloat(var11[1]);
                    }
                    if (var11[0].equals("chatHeightUnfocused")) {
                        this.chatHeightUnfocused = this.parseFloat(var11[1]);
                    }
                    if (var11[0].equals("chatScale")) {
                        this.chatScale = this.parseFloat(var11[1]);
                    }
                    if (var11[0].equals("chatWidth")) {
                        this.chatWidth = this.parseFloat(var11[1]);
                    }
                    if (var11[0].equals("showInventoryAchievementHint")) {
                        this.showInventoryAchievementHint = var11[1].equals("true");
                    }
                    if (var11[0].equals("mipmapLevels")) {
                        this.mipmapLevels = Integer.parseInt(var11[1]);
                    }
                    if (var11[0].equals("anisotropicFiltering")) {
                        this.anisotropicFiltering = Integer.parseInt(var11[1]);
                    }
                    if (var11[0].equals("streamBytesPerPixel")) {
                        this.field_152400_J = this.parseFloat(var11[1]);
                    }
                    if (var11[0].equals("streamMicVolume")) {
                        this.field_152401_K = this.parseFloat(var11[1]);
                    }
                    if (var11[0].equals("streamSystemVolume")) {
                        this.field_152402_L = this.parseFloat(var11[1]);
                    }
                    if (var11[0].equals("streamKbps")) {
                        this.field_152403_M = this.parseFloat(var11[1]);
                    }
                    if (var11[0].equals("streamFps")) {
                        this.field_152404_N = this.parseFloat(var11[1]);
                    }
                    if (var11[0].equals("streamCompression")) {
                        this.field_152405_O = Integer.parseInt(var11[1]);
                    }
                    if (var11[0].equals("streamSendMetadata")) {
                        this.field_152406_P = var11[1].equals("true");
                    }
                    if (var11[0].equals("streamPreferredServer") && var11.length >= 2) {
                        this.field_152407_Q = var10.substring(var10.indexOf(58) + 1);
                    }
                    if (var11[0].equals("streamChatEnabled")) {
                        this.field_152408_R = Integer.parseInt(var11[1]);
                    }
                    if (var11[0].equals("streamChatUserFilter")) {
                        this.field_152409_S = Integer.parseInt(var11[1]);
                    }
                    if (var11[0].equals("streamMicToggleBehavior")) {
                        this.field_152410_T = Integer.parseInt(var11[1]);
                    }
                    if (var11[0].equals("forceUnicodeFont")) {
                        this.forceUnicodeFont = var11[1].equals("true");
                    }
                    for (final KeyBinding var15 : this.keyBindings) {
                        Label_1696: {
                            if (var11[0].equals("key_" + var15.getKeyDescription())) {
                                final int keyCode = Integer.parseInt(var11[1]);
                                if (var15 == this.keyBindAttack || var15 == this.keyBindUseItem) {
                                    if (keyCode != -100 && keyCode != -99) {
                                        break Label_1696;
                                    }
                                }
                                else if (var15 == this.keyBindSprint) {
                                    if (keyCode == this.keyBindSneak.getKeyCode()) {
                                        this.keyBindSneak.setKeyCode(0);
                                    }
                                }
                                else if (var15 == this.keyBindSneak && keyCode == this.keyBindSprint.getKeyCode()) {
                                    this.keyBindSprint.setKeyCode(0);
                                }
                                var15.setKeyCode(keyCode);
                            }
                        }
                    }
                    for (final SoundCategory var17 : SoundCategory.values()) {
                        if (var11[0].equals("soundCategory_" + var17.getCategoryName())) {
                            this.mapSoundLevels.put(var17, this.parseFloat(var11[1]));
                        }
                    }
                }
                catch (Exception var18) {
                    GameSettings.logger.warn("Skipping bad option: " + var10);
                    var18.printStackTrace();
                }
            }
            KeyBinding.resetKeyBindingArrayAndHash();
            var9.close();
        }
        catch (Exception var19) {
            GameSettings.logger.error("Failed to load options", (Throwable)var19);
        }
        this.loadOfOptions();
    }
    
    private float parseFloat(final String par1Str) {
        return par1Str.equals("true") ? 1.0f : (par1Str.equals("false") ? 0.0f : Float.parseFloat(par1Str));
    }
    
    public void saveOptions() {
        try {
            final PrintWriter var81 = new PrintWriter(new FileWriter(this.optionsFile));
            var81.println("invertYMouse:" + this.invertMouse);
            var81.println("mouseSensitivity:" + this.mouseSensitivity);
            var81.println("fov:" + (this.fovSetting - 70.0f) / 40.0f);
            var81.println("gamma:" + this.gammaSetting);
            var81.println("saturation:" + this.saturation);
            var81.println("renderDistance:" + Config.limit(this.renderDistanceChunks, 2, 16));
            var81.println("guiScale:" + this.guiScale);
            var81.println("particles:" + this.particleSetting);
            var81.println("bobView:" + this.viewBobbing);
            var81.println("anaglyph3d:" + this.anaglyph);
            var81.println("advancedOpengl:" + this.advancedOpengl);
            var81.println("maxFps:" + this.limitFramerate);
            var81.println("fboEnable:" + this.fboEnable);
            var81.println("difficulty:" + this.difficulty.getDifficultyId());
            var81.println("fancyGraphics:" + this.fancyGraphics);
            var81.println("ao:" + this.ambientOcclusion);
            var81.println("clouds:" + this.clouds);
            var81.println("resourcePacks:" + GameSettings.gson.toJson((Object)this.resourcePacks));
            var81.println("lastServer:" + this.lastServer);
            var81.println("lang:" + this.language);
            var81.println("chatVisibility:" + this.chatVisibility.getChatVisibility());
            var81.println("chatColors:" + this.chatColours);
            var81.println("chatLinks:" + this.chatLinks);
            var81.println("chatLinksPrompt:" + this.chatLinksPrompt);
            var81.println("chatOpacity:" + this.chatOpacity);
            var81.println("snooperEnabled:" + this.snooperEnabled);
            var81.println("fullscreen:" + this.fullScreen);
            var81.println("enableVsync:" + this.enableVsync);
            var81.println("hideServerAddress:" + this.hideServerAddress);
            var81.println("advancedItemTooltips:" + this.advancedItemTooltips);
            var81.println("pauseOnLostFocus:" + this.pauseOnLostFocus);
            var81.println("showCape:" + this.showCape);
            var81.println("touchscreen:" + this.touchscreen);
            var81.println("overrideWidth:" + this.overrideWidth);
            var81.println("overrideHeight:" + this.overrideHeight);
            var81.println("heldItemTooltips:" + this.heldItemTooltips);
            var81.println("chatHeightFocused:" + this.chatHeightFocused);
            var81.println("chatHeightUnfocused:" + this.chatHeightUnfocused);
            var81.println("chatScale:" + this.chatScale);
            var81.println("chatWidth:" + this.chatWidth);
            var81.println("showInventoryAchievementHint:" + this.showInventoryAchievementHint);
            var81.println("mipmapLevels:" + this.mipmapLevels);
            var81.println("anisotropicFiltering:" + this.anisotropicFiltering);
            var81.println("streamBytesPerPixel:" + this.field_152400_J);
            var81.println("streamMicVolume:" + this.field_152401_K);
            var81.println("streamSystemVolume:" + this.field_152402_L);
            var81.println("streamKbps:" + this.field_152403_M);
            var81.println("streamFps:" + this.field_152404_N);
            var81.println("streamCompression:" + this.field_152405_O);
            var81.println("streamSendMetadata:" + this.field_152406_P);
            var81.println("streamPreferredServer:" + this.field_152407_Q);
            var81.println("streamChatEnabled:" + this.field_152408_R);
            var81.println("streamChatUserFilter:" + this.field_152409_S);
            var81.println("streamMicToggleBehavior:" + this.field_152410_T);
            var81.println("forceUnicodeFont:" + this.forceUnicodeFont);
            for (final KeyBinding var85 : this.keyBindings) {
                var81.println("key_" + var85.getKeyDescription() + ":" + var85.getKeyCode());
            }
            for (final SoundCategory var87 : SoundCategory.values()) {
                var81.println("soundCategory_" + var87.getCategoryName() + ":" + this.getSoundLevel(var87));
            }
            var81.close();
        }
        catch (Exception var88) {
            GameSettings.logger.error("Failed to save options", (Throwable)var88);
        }
        this.saveOfOptions();
        this.sendSettingsToServer();
    }
    
    public float getSoundLevel(final SoundCategory p_151438_1_) {
        return this.mapSoundLevels.containsKey(p_151438_1_) ? this.mapSoundLevels.get(p_151438_1_) : 1.0f;
    }
    
    public void setSoundLevel(final SoundCategory p_151439_1_, final float p_151439_2_) {
        this.mc.getSoundHandler().setSoundLevel(p_151439_1_, p_151439_2_);
        this.mapSoundLevels.put(p_151439_1_, p_151439_2_);
    }
    
    public void sendSettingsToServer() {
        if (this.mc.thePlayer != null) {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C15PacketClientSettings(this.language, this.renderDistanceChunks, this.chatVisibility, this.chatColours, this.difficulty, this.showCape));
        }
    }
    
    public boolean shouldRenderClouds() {
        return this.renderDistanceChunks >= 4 && this.clouds;
    }
    
    public void loadOfOptions() {
        try {
            File exception = this.optionsFileOF;
            if (!exception.exists()) {
                exception = this.optionsFile;
            }
            if (!exception.exists()) {
                return;
            }
            final BufferedReader bufferedreader = new BufferedReader(new FileReader(exception));
            String s = "";
            while ((s = bufferedreader.readLine()) != null) {
                try {
                    final String[] exception2 = s.split(":");
                    if (exception2[0].equals("ofRenderDistanceChunks") && exception2.length >= 2) {
                        this.renderDistanceChunks = Integer.valueOf(exception2[1]);
                        this.renderDistanceChunks = Config.limit(this.renderDistanceChunks, 2, 32);
                    }
                    if (exception2[0].equals("ofFogType") && exception2.length >= 2) {
                        this.ofFogType = Integer.valueOf(exception2[1]);
                        this.ofFogType = Config.limit(this.ofFogType, 1, 3);
                    }
                    if (exception2[0].equals("ofFogStart") && exception2.length >= 2) {
                        this.ofFogStart = Float.valueOf(exception2[1]);
                        if (this.ofFogStart < 0.2f) {
                            this.ofFogStart = 0.2f;
                        }
                        if (this.ofFogStart > 0.81f) {
                            this.ofFogStart = 0.8f;
                        }
                    }
                    if (exception2[0].equals("ofMipmapType") && exception2.length >= 2) {
                        this.ofMipmapType = Integer.valueOf(exception2[1]);
                        this.ofMipmapType = Config.limit(this.ofMipmapType, 0, 3);
                    }
                    if (exception2[0].equals("ofLoadFar") && exception2.length >= 2) {
                        this.ofLoadFar = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofOcclusionFancy") && exception2.length >= 2) {
                        this.ofOcclusionFancy = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofSmoothWorld") && exception2.length >= 2) {
                        this.ofSmoothWorld = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofAoLevel") && exception2.length >= 2) {
                        this.ofAoLevel = Float.valueOf(exception2[1]);
                        this.ofAoLevel = Config.limit(this.ofAoLevel, 0.0f, 1.0f);
                    }
                    if (exception2[0].equals("ofClouds") && exception2.length >= 2) {
                        this.ofClouds = Integer.valueOf(exception2[1]);
                        this.ofClouds = Config.limit(this.ofClouds, 0, 3);
                    }
                    if (exception2[0].equals("ofCloudsHeight") && exception2.length >= 2) {
                        this.ofCloudsHeight = Float.valueOf(exception2[1]);
                        this.ofCloudsHeight = Config.limit(this.ofCloudsHeight, 0.0f, 1.0f);
                    }
                    if (exception2[0].equals("ofTrees") && exception2.length >= 2) {
                        this.ofTrees = Integer.valueOf(exception2[1]);
                        this.ofTrees = Config.limit(this.ofTrees, 0, 2);
                    }
                    if (exception2[0].equals("ofGrass") && exception2.length >= 2) {
                        this.ofGrass = Integer.valueOf(exception2[1]);
                        this.ofGrass = Config.limit(this.ofGrass, 0, 2);
                    }
                    if (exception2[0].equals("ofDroppedItems") && exception2.length >= 2) {
                        this.ofDroppedItems = Integer.valueOf(exception2[1]);
                        this.ofDroppedItems = Config.limit(this.ofDroppedItems, 0, 2);
                    }
                    if (exception2[0].equals("ofRain") && exception2.length >= 2) {
                        this.ofRain = Integer.valueOf(exception2[1]);
                        this.ofRain = Config.limit(this.ofRain, 0, 3);
                    }
                    if (exception2[0].equals("ofWater") && exception2.length >= 2) {
                        this.ofWater = Integer.valueOf(exception2[1]);
                        this.ofWater = Config.limit(this.ofWater, 0, 3);
                    }
                    if (exception2[0].equals("ofAnimatedWater") && exception2.length >= 2) {
                        this.ofAnimatedWater = Integer.valueOf(exception2[1]);
                        this.ofAnimatedWater = Config.limit(this.ofAnimatedWater, 0, 2);
                    }
                    if (exception2[0].equals("ofAnimatedLava") && exception2.length >= 2) {
                        this.ofAnimatedLava = Integer.valueOf(exception2[1]);
                        this.ofAnimatedLava = Config.limit(this.ofAnimatedLava, 0, 2);
                    }
                    if (exception2[0].equals("ofAnimatedFire") && exception2.length >= 2) {
                        this.ofAnimatedFire = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofAnimatedPortal") && exception2.length >= 2) {
                        this.ofAnimatedPortal = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofAnimatedRedstone") && exception2.length >= 2) {
                        this.ofAnimatedRedstone = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofAnimatedExplosion") && exception2.length >= 2) {
                        this.ofAnimatedExplosion = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofAnimatedFlame") && exception2.length >= 2) {
                        this.ofAnimatedFlame = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofAnimatedSmoke") && exception2.length >= 2) {
                        this.ofAnimatedSmoke = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofVoidParticles") && exception2.length >= 2) {
                        this.ofVoidParticles = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofWaterParticles") && exception2.length >= 2) {
                        this.ofWaterParticles = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofPortalParticles") && exception2.length >= 2) {
                        this.ofPortalParticles = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofPotionParticles") && exception2.length >= 2) {
                        this.ofPotionParticles = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofDrippingWaterLava") && exception2.length >= 2) {
                        this.ofDrippingWaterLava = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofAnimatedTerrain") && exception2.length >= 2) {
                        this.ofAnimatedTerrain = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofAnimatedTextures") && exception2.length >= 2) {
                        this.ofAnimatedTextures = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofAnimatedItems") && exception2.length >= 2) {
                        this.ofAnimatedItems = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofRainSplash") && exception2.length >= 2) {
                        this.ofRainSplash = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofLagometer") && exception2.length >= 2) {
                        this.ofLagometer = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofShowFps") && exception2.length >= 2) {
                        this.ofShowFps = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofAutoSaveTicks") && exception2.length >= 2) {
                        this.ofAutoSaveTicks = Integer.valueOf(exception2[1]);
                        this.ofAutoSaveTicks = Config.limit(this.ofAutoSaveTicks, 40, 40000);
                    }
                    if (exception2[0].equals("ofBetterGrass") && exception2.length >= 2) {
                        this.ofBetterGrass = Integer.valueOf(exception2[1]);
                        this.ofBetterGrass = Config.limit(this.ofBetterGrass, 1, 3);
                    }
                    if (exception2[0].equals("ofConnectedTextures") && exception2.length >= 2) {
                        this.ofConnectedTextures = Integer.valueOf(exception2[1]);
                        this.ofConnectedTextures = Config.limit(this.ofConnectedTextures, 1, 3);
                    }
                    if (exception2[0].equals("ofWeather") && exception2.length >= 2) {
                        FPSBoost.WEATHER.setValue(Boolean.valueOf((boolean)Boolean.valueOf(exception2[1])));
                    }
                    if (exception2[0].equals("ofSky") && exception2.length >= 2) {
                        this.ofSky = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofStars") && exception2.length >= 2) {
                        this.ofStars = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofSunMoon") && exception2.length >= 2) {
                        this.ofSunMoon = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofVignette") && exception2.length >= 2) {
                        this.ofVignette = Integer.valueOf(exception2[1]);
                        this.ofVignette = Config.limit(this.ofVignette, 0, 2);
                    }
                    if (exception2[0].equals("ofChunkLoading") && exception2.length >= 2) {
                        this.ofChunkLoading = Integer.valueOf(exception2[1]);
                        this.ofChunkLoading = Config.limit(this.ofChunkLoading, 0, 2);
                        this.updateChunkLoading();
                    }
                    if (exception2[0].equals("ofTime") && exception2.length >= 2) {
                        this.ofTime = Integer.valueOf(exception2[1]);
                        this.ofTime = Config.limit(this.ofTime, 0, 3);
                    }
                    if (exception2[0].equals("ofClearWater") && exception2.length >= 2) {
                        this.ofClearWater = Boolean.valueOf(exception2[1]);
                        this.updateWaterOpacity();
                    }
                    if (exception2[0].equals("ofDepthFog") && exception2.length >= 2) {
                        this.ofDepthFog = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofAaLevel") && exception2.length >= 2) {
                        this.ofAaLevel = Integer.valueOf(exception2[1]);
                        this.ofAaLevel = Config.limit(this.ofAaLevel, 0, 16);
                    }
                    if (exception2[0].equals("ofProfiler") && exception2.length >= 2) {
                        this.ofProfiler = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofBetterSnow") && exception2.length >= 2) {
                        this.ofBetterSnow = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofSwampColors") && exception2.length >= 2) {
                        this.ofSwampColors = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofRandomMobs") && exception2.length >= 2) {
                        this.ofRandomMobs = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofSmoothBiomes") && exception2.length >= 2) {
                        this.ofSmoothBiomes = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofCustomFonts") && exception2.length >= 2) {
                        this.ofCustomFonts = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofCustomColors") && exception2.length >= 2) {
                        this.ofCustomColors = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofCustomSky") && exception2.length >= 2) {
                        this.ofCustomSky = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofShowCapes") && exception2.length >= 2) {
                        this.ofShowCapes = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofNaturalTextures") && exception2.length >= 2) {
                        this.ofNaturalTextures = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofLazyChunkLoading") && exception2.length >= 2) {
                        this.ofLazyChunkLoading = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofDynamicFov") && exception2.length >= 2) {
                        this.ofDynamicFov = Boolean.valueOf(exception2[1]);
                    }
                    if (exception2[0].equals("ofDynamicLights") && exception2.length >= 2) {
                        this.ofDynamicLights = Integer.valueOf(exception2[1]);
                        this.ofDynamicLights = Config.limit(this.ofDynamicLights, 1, 3);
                    }
                    if (exception2[0].equals("ofFullscreenMode") && exception2.length >= 2) {
                        this.ofFullscreenMode = exception2[1];
                    }
                    if (!exception2[0].equals("ofTranslucentBlocks") || exception2.length < 2) {
                        continue;
                    }
                    this.ofTranslucentBlocks = Integer.valueOf(exception2[1]);
                    this.ofTranslucentBlocks = Config.limit(this.ofTranslucentBlocks, 1, 2);
                }
                catch (Exception var5) {
                    Config.dbg("Skipping bad option: " + s);
                    var5.printStackTrace();
                }
            }
            KeyBinding.resetKeyBindingArrayAndHash();
            bufferedreader.close();
        }
        catch (Exception var6) {
            Config.warn("Failed to load options");
            var6.printStackTrace();
        }
    }
    
    public void saveOfOptions() {
        try {
            final PrintWriter exception = new PrintWriter(new FileWriter(this.optionsFileOF));
            exception.println("ofRenderDistanceChunks:" + this.renderDistanceChunks);
            exception.println("ofFogType:" + this.ofFogType);
            exception.println("ofFogStart:" + this.ofFogStart);
            exception.println("ofMipmapType:" + this.ofMipmapType);
            exception.println("ofLoadFar:" + this.ofLoadFar);
            exception.println("ofPreloadedChunks:" + FPSBoost.SMART_PERFORMANCE.getValue() / 10.0f);
            exception.println("ofOcclusionFancy:" + this.ofOcclusionFancy);
            exception.println("ofSmoothFps:" + (FPSBoost.SMART_PERFORMANCE.getValue() >= 20.0f));
            exception.println("ofSmoothWorld:" + this.ofSmoothWorld);
            exception.println("ofAoLevel:" + this.ofAoLevel);
            exception.println("ofClouds:" + this.ofClouds);
            exception.println("ofCloudsHeight:" + this.ofCloudsHeight);
            exception.println("ofTrees:" + this.ofTrees);
            exception.println("ofGrass:" + this.ofGrass);
            exception.println("ofDroppedItems:" + this.ofDroppedItems);
            exception.println("ofRain:" + this.ofRain);
            exception.println("ofWater:" + this.ofWater);
            exception.println("ofAnimatedWater:" + this.ofAnimatedWater);
            exception.println("ofAnimatedLava:" + this.ofAnimatedLava);
            exception.println("ofAnimatedFire:" + this.ofAnimatedFire);
            exception.println("ofAnimatedPortal:" + this.ofAnimatedPortal);
            exception.println("ofAnimatedRedstone:" + this.ofAnimatedRedstone);
            exception.println("ofAnimatedExplosion:" + this.ofAnimatedExplosion);
            exception.println("ofAnimatedFlame:" + this.ofAnimatedFlame);
            exception.println("ofAnimatedSmoke:" + this.ofAnimatedSmoke);
            exception.println("ofVoidParticles:" + this.ofVoidParticles);
            exception.println("ofWaterParticles:" + this.ofWaterParticles);
            exception.println("ofPortalParticles:" + this.ofPortalParticles);
            exception.println("ofPotionParticles:" + this.ofPotionParticles);
            exception.println("ofDrippingWaterLava:" + this.ofDrippingWaterLava);
            exception.println("ofAnimatedTerrain:" + this.ofAnimatedTerrain);
            exception.println("ofAnimatedTextures:" + this.ofAnimatedTextures);
            exception.println("ofAnimatedItems:" + this.ofAnimatedItems);
            exception.println("ofRainSplash:" + this.ofRainSplash);
            exception.println("ofLagometer:" + this.ofLagometer);
            exception.println("ofShowFps:" + this.ofShowFps);
            exception.println("ofAutoSaveTicks:" + this.ofAutoSaveTicks);
            exception.println("ofBetterGrass:" + this.ofBetterGrass);
            exception.println("ofConnectedTextures:" + this.ofConnectedTextures);
            exception.println("ofWeather:" + FPSBoost.WEATHER.getValue());
            exception.println("ofSky:" + this.ofSky);
            exception.println("ofStars:" + this.ofStars);
            exception.println("ofSunMoon:" + this.ofSunMoon);
            exception.println("ofVignette:" + this.ofVignette);
            exception.println("ofChunkUpdates:" + FPSBoost.SMART_PERFORMANCE.getValue() / 20.0f);
            exception.println("ofChunkLoading:" + this.ofChunkLoading);
            exception.println("ofChunkUpdatesDynamic:" + (FPSBoost.SMART_PERFORMANCE.getValue() >= 30.0f));
            exception.println("ofTime:" + this.ofTime);
            exception.println("ofClearWater:" + this.ofClearWater);
            exception.println("ofDepthFog:" + this.ofDepthFog);
            exception.println("ofAaLevel:" + this.ofAaLevel);
            exception.println("ofProfiler:" + this.ofProfiler);
            exception.println("ofBetterSnow:" + this.ofBetterSnow);
            exception.println("ofSwampColors:" + this.ofSwampColors);
            exception.println("ofRandomMobs:" + this.ofRandomMobs);
            exception.println("ofSmoothBiomes:" + this.ofSmoothBiomes);
            exception.println("ofCustomFonts:" + this.ofCustomFonts);
            exception.println("ofCustomColors:" + this.ofCustomColors);
            exception.println("ofCustomSky:" + this.ofCustomSky);
            exception.println("ofShowCapes:" + this.ofShowCapes);
            exception.println("ofNaturalTextures:" + this.ofNaturalTextures);
            exception.println("ofLazyChunkLoading:" + this.ofLazyChunkLoading);
            exception.println("ofDynamicFov:" + this.ofDynamicFov);
            exception.println("ofDynamicLights:" + this.ofDynamicLights);
            exception.println("ofFullscreenMode:" + this.ofFullscreenMode);
            exception.println("ofFastMath:" + (FPSBoost.SMART_PERFORMANCE.getValue() >= 20.0f));
            exception.println("ofTranslucentBlocks:" + this.ofTranslucentBlocks);
            exception.close();
        }
        catch (Exception var2) {
            Config.warn("Failed to save options");
            var2.printStackTrace();
        }
    }
    
    public void resetSettings() {
        this.renderDistanceChunks = 8;
        this.viewBobbing = true;
        this.anaglyph = false;
        this.advancedOpengl = false;
        this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
        this.enableVsync = false;
        this.updateVSync();
        this.mipmapLevels = 4;
        this.anisotropicFiltering = 1;
        this.fancyGraphics = true;
        this.ambientOcclusion = 2;
        this.clouds = true;
        this.fovSetting = 70.0f;
        this.gammaSetting = 0.0f;
        this.guiScale = 0;
        this.particleSetting = 0;
        this.heldItemTooltips = true;
        this.ofFogType = 1;
        this.ofFogStart = 0.8f;
        this.ofMipmapType = 0;
        this.ofLoadFar = false;
        this.ofOcclusionFancy = false;
        Config.updateAvailableProcessors();
        this.ofSmoothWorld = Config.isSingleProcessor();
        this.ofLazyChunkLoading = Config.isSingleProcessor();
        this.ofTranslucentBlocks = 2;
        this.ofDynamicFov = true;
        this.ofDynamicLights = 3;
        this.ofAoLevel = 1.0f;
        this.ofAaLevel = 0;
        this.ofClouds = 0;
        this.ofCloudsHeight = 0.0f;
        this.ofTrees = 0;
        this.ofGrass = 0;
        this.ofRain = 0;
        this.ofWater = 0;
        this.ofBetterGrass = 3;
        this.ofAutoSaveTicks = 4000;
        this.ofLagometer = false;
        this.ofShowFps = false;
        this.ofProfiler = false;
        this.ofSky = true;
        this.ofStars = true;
        this.ofSunMoon = true;
        this.ofVignette = 0;
        this.ofChunkLoading = 0;
        this.ofTime = 0;
        this.ofClearWater = false;
        this.ofDepthFog = true;
        this.ofBetterSnow = false;
        this.ofFullscreenMode = "Default";
        this.ofSwampColors = true;
        this.ofRandomMobs = true;
        this.ofSmoothBiomes = true;
        this.ofCustomFonts = true;
        this.ofCustomColors = true;
        this.ofCustomSky = true;
        this.ofShowCapes = true;
        this.ofConnectedTextures = 2;
        this.ofNaturalTextures = false;
        this.ofAnimatedWater = 0;
        this.ofAnimatedLava = 0;
        this.ofAnimatedFire = true;
        this.ofAnimatedPortal = true;
        this.ofAnimatedRedstone = true;
        this.ofAnimatedExplosion = true;
        this.ofAnimatedFlame = true;
        this.ofAnimatedSmoke = true;
        this.ofVoidParticles = true;
        this.ofWaterParticles = true;
        this.ofRainSplash = true;
        this.ofPortalParticles = true;
        this.ofPotionParticles = true;
        this.ofDrippingWaterLava = true;
        this.ofAnimatedTerrain = true;
        this.ofAnimatedItems = true;
        this.ofAnimatedTextures = true;
        this.mc.renderGlobal.updateCapes();
        this.updateWaterOpacity();
        this.mc.renderGlobal.setAllRenderersVisible();
        this.mc.refreshResources();
        this.saveOptions();
    }
    
    public void updateVSync() {
        Display.setVSyncEnabled(this.enableVsync);
    }
    
    private void updateWaterOpacity() {
        if (this.mc.isIntegratedServerRunning() && this.mc.getIntegratedServer() != null) {
            Config.waterOpacityChanged = true;
        }
        ClearWater.updateWaterOpacity(this, this.mc.theWorld);
    }
    
    public void updateChunkLoading() {
        this.ofChunkLoading = 1;
        WrUpdates.setWrUpdater(new WrUpdaterSmooth());
        if (this.mc.renderGlobal != null) {
            this.mc.renderGlobal.loadRenderers();
        }
    }
    
    public void setAllAnimations(final boolean flag) {
        final int animVal = flag ? 0 : 2;
        this.ofAnimatedWater = animVal;
        this.ofAnimatedLava = animVal;
        this.ofAnimatedFire = flag;
        this.ofAnimatedPortal = flag;
        this.ofAnimatedRedstone = flag;
        this.ofAnimatedExplosion = flag;
        this.ofAnimatedFlame = flag;
        this.ofAnimatedSmoke = flag;
        this.ofVoidParticles = flag;
        this.ofWaterParticles = flag;
        this.ofRainSplash = flag;
        this.ofPortalParticles = flag;
        this.ofPotionParticles = flag;
        this.particleSetting = (flag ? 0 : 2);
        this.ofDrippingWaterLava = flag;
        this.ofAnimatedTerrain = flag;
        this.ofAnimatedItems = flag;
        this.ofAnimatedTextures = flag;
    }
    
    static {
        logger = LogManager.getLogger();
        gson = new Gson();
        typeListString = new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[] { String.class };
            }
            
            @Override
            public Type getRawType() {
                return List.class;
            }
            
            @Override
            public Type getOwnerType() {
                return null;
            }
        };
        GUISCALES = new String[] { "options.guiScale.auto", "options.guiScale.small", "options.guiScale.normal", "options.guiScale.large" };
        PARTICLES = new String[] { "options.particles.all", "options.particles.decreased", "options.particles.minimal" };
        AMBIENT_OCCLUSIONS = new String[] { "options.ao.off", "options.ao.min", "options.ao.max" };
        field_152391_aS = new String[] { "options.stream.compression.low", "options.stream.compression.medium", "options.stream.compression.high" };
        field_152392_aT = new String[] { "options.stream.chat.enabled.streaming", "options.stream.chat.enabled.always", "options.stream.chat.enabled.never" };
        field_152393_aU = new String[] { "options.stream.chat.userFilter.all", "options.stream.chat.userFilter.subs", "options.stream.chat.userFilter.mods" };
        field_152394_aV = new String[] { "options.stream.mic_toggle.mute", "options.stream.mic_toggle.talk" };
    }
    
    public enum Options
    {
        INVERT_MOUSE("INVERT_MOUSE", 0, "INVERT_MOUSE", 0, "options.invertMouse", false, true), 
        SENSITIVITY("SENSITIVITY", 1, "SENSITIVITY", 1, "options.sensitivity", true, false), 
        FOV("FOV", 2, "FOV", 2, "options.fov", true, false, 30.0f, 110.0f, 1.0f), 
        GAMMA("GAMMA", 3, "GAMMA", 3, "options.gamma", true, false), 
        SATURATION("SATURATION", 4, "SATURATION", 4, "options.saturation", true, false), 
        RENDER_DISTANCE("RENDER_DISTANCE", 5, "RENDER_DISTANCE", 5, "options.renderDistance", true, false, 2.0f, 16.0f, 1.0f), 
        VIEW_BOBBING("VIEW_BOBBING", 6, "VIEW_BOBBING", 6, "options.viewBobbing", false, true), 
        ADVANCED_OPENGL("ADVANCED_OPENGL", 8, "ADVANCED_OPENGL", 8, "options.advancedOpengl", false, true), 
        FRAMERATE_LIMIT("FRAMERATE_LIMIT", 9, "FRAMERATE_LIMIT", 9, "options.framerateLimit", true, false, 0.0f, 260.0f, 5.0f), 
        FBO_ENABLE("FBO_ENABLE", 10, "FBO_ENABLE", 10, "options.fboEnable", false, true), 
        DIFFICULTY("DIFFICULTY", 11, "DIFFICULTY", 11, "options.difficulty", false, false), 
        GRAPHICS("GRAPHICS", 12, "GRAPHICS", 12, "options.graphics", false, false), 
        AMBIENT_OCCLUSION("AMBIENT_OCCLUSION", 13, "AMBIENT_OCCLUSION", 13, "options.ao", false, false), 
        GUI_SCALE("GUI_SCALE", 14, "GUI_SCALE", 14, "options.guiScale", false, false), 
        RENDER_CLOUDS("RENDER_CLOUDS", 15, "RENDER_CLOUDS", 15, "options.renderClouds", false, true), 
        PARTICLES("PARTICLES", 16, "PARTICLES", 16, "options.particles", false, false), 
        CHAT_VISIBILITY("CHAT_VISIBILITY", 17, "CHAT_VISIBILITY", 17, "options.chat.visibility", false, false), 
        CHAT_COLOR("CHAT_COLOR", 18, "CHAT_COLOR", 18, "options.chat.color", false, true), 
        CHAT_LINKS("CHAT_LINKS", 19, "CHAT_LINKS", 19, "options.chat.links", false, true), 
        CHAT_OPACITY("CHAT_OPACITY", 20, "CHAT_OPACITY", 20, "options.chat.opacity", true, false), 
        CHAT_LINKS_PROMPT("CHAT_LINKS_PROMPT", 21, "CHAT_LINKS_PROMPT", 21, "options.chat.links.prompt", false, true), 
        SNOOPER_ENABLED("SNOOPER_ENABLED", 22, "SNOOPER_ENABLED", 22, "options.snooper", false, true), 
        USE_FULLSCREEN("USE_FULLSCREEN", 23, "USE_FULLSCREEN", 23, "options.fullscreen", false, true), 
        ENABLE_VSYNC("ENABLE_VSYNC", 24, "ENABLE_VSYNC", 24, "options.vsync", false, true), 
        SHOW_CAPE("SHOW_CAPE", 25, "SHOW_CAPE", 25, "options.showCape", false, true), 
        TOUCHSCREEN("TOUCHSCREEN", 26, "TOUCHSCREEN", 26, "options.touchscreen", false, true), 
        CHAT_SCALE("CHAT_SCALE", 27, "CHAT_SCALE", 27, "options.chat.scale", true, false), 
        CHAT_WIDTH("CHAT_WIDTH", 28, "CHAT_WIDTH", 28, "options.chat.width", true, false), 
        CHAT_HEIGHT_FOCUSED("CHAT_HEIGHT_FOCUSED", 29, "CHAT_HEIGHT_FOCUSED", 29, "options.chat.height.focused", true, false), 
        CHAT_HEIGHT_UNFOCUSED("CHAT_HEIGHT_UNFOCUSED", 30, "CHAT_HEIGHT_UNFOCUSED", 30, "options.chat.height.unfocused", true, false), 
        MIPMAP_LEVELS("MIPMAP_LEVELS", 31, "MIPMAP_LEVELS", 31, "options.mipmapLevels", true, false, 0.0f, 4.0f, 1.0f), 
        ANISOTROPIC_FILTERING("ANISOTROPIC_FILTERING", 32, "ANISOTROPIC_FILTERING", 32, "options.anisotropicFiltering", true, false, 1.0f, 16.0f, 0.0f, (Object)null, (Object)null) {
            @Override
            protected float snapToStep(final float p_148264_1_) {
                return (float)MathHelper.roundUpToPowerOfTwo((int)p_148264_1_);
            }
        }, 
        FORCE_UNICODE_FONT("FORCE_UNICODE_FONT", 33, "FORCE_UNICODE_FONT", 33, "options.forceUnicodeFont", false, true), 
        STREAM_BYTES_PER_PIXEL("STREAM_BYTES_PER_PIXEL", 34, "STREAM_BYTES_PER_PIXEL", 34, "options.stream.bytesPerPixel", true, false), 
        STREAM_VOLUME_MIC("STREAM_VOLUME_MIC", 35, "STREAM_VOLUME_MIC", 35, "options.stream.micVolumne", true, false), 
        STREAM_VOLUME_SYSTEM("STREAM_VOLUME_SYSTEM", 36, "STREAM_VOLUME_SYSTEM", 36, "options.stream.systemVolume", true, false), 
        STREAM_KBPS("STREAM_KBPS", 37, "STREAM_KBPS", 37, "options.stream.kbps", true, false), 
        STREAM_FPS("STREAM_FPS", 38, "STREAM_FPS", 38, "options.stream.fps", true, false), 
        STREAM_COMPRESSION("STREAM_COMPRESSION", 39, "STREAM_COMPRESSION", 39, "options.stream.compression", false, false), 
        STREAM_SEND_METADATA("STREAM_SEND_METADATA", 40, "STREAM_SEND_METADATA", 40, "options.stream.sendMetadata", false, true), 
        STREAM_CHAT_ENABLED("STREAM_CHAT_ENABLED", 41, "STREAM_CHAT_ENABLED", 41, "options.stream.chat.enabled", false, false), 
        STREAM_CHAT_USER_FILTER("STREAM_CHAT_USER_FILTER", 42, "STREAM_CHAT_USER_FILTER", 42, "options.stream.chat.userFilter", false, false), 
        STREAM_MIC_TOGGLE_BEHAVIOR("STREAM_MIC_TOGGLE_BEHAVIOR", 43, "STREAM_MIC_TOGGLE_BEHAVIOR", 43, "options.stream.micToggleBehavior", false, false), 
        FOG_FANCY("FOG_FANCY", 44, "FOG", 999, "Fog", false, false), 
        FOG_START("FOG_START", 45, "", 999, "Fog Start", false, false), 
        MIPMAP_TYPE("MIPMAP_TYPE", 46, "", 999, "Mipmap Type", false, false), 
        LOAD_FAR("LOAD_FAR", 47, "", 999, "Load Far", false, false), 
        PRELOADED_CHUNKS("PRELOADED_CHUNKS", 48, "", 999, "Preloaded Chunks", false, false), 
        SMOOTH_FPS("SMOOTH_FPS", 49, "", 999, "Smooth FPS", false, false), 
        CLOUDS("CLOUDS", 50, "", 999, "Clouds", false, false), 
        CLOUD_HEIGHT("CLOUD_HEIGHT", 51, "", 999, "Cloud Height", true, false), 
        TREES("TREES", 52, "", 999, "Trees", false, false), 
        GRASS("GRASS", 53, "", 999, "Grass", false, false), 
        RAIN("RAIN", 54, "", 999, "Rain & Snow", false, false), 
        WATER("WATER", 55, "", 999, "Water", false, false), 
        ANIMATED_WATER("ANIMATED_WATER", 56, "", 999, "Water Animated", false, false), 
        ANIMATED_LAVA("ANIMATED_LAVA", 57, "", 999, "Lava Animated", false, false), 
        ANIMATED_FIRE("ANIMATED_FIRE", 58, "", 999, "Fire Animated", false, false), 
        ANIMATED_PORTAL("ANIMATED_PORTAL", 59, "", 999, "Portal Animated", false, false), 
        AO_LEVEL("AO_LEVEL", 60, "", 999, "Smooth Lighting Level", true, false), 
        LAGOMETER("LAGOMETER", 61, "", 999, "Lagometer", false, false), 
        SHOW_FPS("SHOW_FPS", 62, "", 999, "Show FPS", false, false), 
        AUTOSAVE_TICKS("AUTOSAVE_TICKS", 63, "", 999, "Autosave", false, false), 
        BETTER_GRASS("BETTER_GRASS", 64, "", 999, "Better Grass", false, false), 
        ANIMATED_REDSTONE("ANIMATED_REDSTONE", 65, "", 999, "Redstone Animated", false, false), 
        ANIMATED_EXPLOSION("ANIMATED_EXPLOSION", 66, "", 999, "Explosion Animated", false, false), 
        ANIMATED_FLAME("ANIMATED_FLAME", 67, "", 999, "Flame Animated", false, false), 
        ANIMATED_SMOKE("ANIMATED_SMOKE", 68, "", 999, "Smoke Animated", false, false), 
        WEATHER("WEATHER", 69, "", 999, "Weather", false, false), 
        SKY("SKY", 70, "", 999, "Sky", false, false), 
        STARS("STARS", 71, "", 999, "Stars", false, false), 
        SUN_MOON("SUN_MOON", 72, "", 999, "Sun & Moon", false, false), 
        VIGNETTE("VIGNETTE", 73, "", 999, "Vignette", false, false), 
        CHUNK_UPDATES("CHUNK_UPDATES", 74, "", 999, "Chunk Updates", false, false), 
        CHUNK_UPDATES_DYNAMIC("CHUNK_UPDATES_DYNAMIC", 75, "", 999, "Dynamic Updates", false, false), 
        TIME("TIME", 76, "", 999, "Time", false, false), 
        CLEAR_WATER("CLEAR_WATER", 77, "", 999, "Clear Water", false, false), 
        SMOOTH_WORLD("SMOOTH_WORLD", 78, "", 999, "Smooth World", false, false), 
        DEPTH_FOG("DEPTH_FOG", 79, "", 999, "Depth Fog", false, false), 
        VOID_PARTICLES("VOID_PARTICLES", 80, "", 999, "Void Particles", false, false), 
        WATER_PARTICLES("WATER_PARTICLES", 81, "", 999, "Water Particles", false, false), 
        RAIN_SPLASH("RAIN_SPLASH", 82, "", 999, "Rain Splash", false, false), 
        PORTAL_PARTICLES("PORTAL_PARTICLES", 83, "", 999, "Portal Particles", false, false), 
        POTION_PARTICLES("POTION_PARTICLES", 84, "", 999, "Potion Particles", false, false), 
        PROFILER("PROFILER", 85, "", 999, "Debug Profiler", false, false), 
        DRIPPING_WATER_LAVA("DRIPPING_WATER_LAVA", 86, "", 999, "Dripping Water/Lava", false, false), 
        BETTER_SNOW("BETTER_SNOW", 87, "", 999, "Better Snow", false, false), 
        FULLSCREEN_MODE("FULLSCREEN_MODE", 88, "", 999, "Fullscreen Mode", false, false), 
        ANIMATED_TERRAIN("ANIMATED_TERRAIN", 89, "", 999, "Terrain Animated", false, false), 
        ANIMATED_ITEMS("ANIMATED_ITEMS", 90, "", 999, "Items Animated", false, false), 
        SWAMP_COLORS("SWAMP_COLORS", 91, "", 999, "Swamp Colors", false, false), 
        RANDOM_MOBS("RANDOM_MOBS", 92, "", 999, "Random Mobs", false, false), 
        SMOOTH_BIOMES("SMOOTH_BIOMES", 93, "", 999, "Smooth Biomes", false, false), 
        CUSTOM_FONTS("CUSTOM_FONTS", 94, "", 999, "Custom Fonts", false, false), 
        CUSTOM_COLORS("CUSTOM_COLORS", 95, "", 999, "Custom Colors", false, false), 
        SHOW_CAPES("SHOW_CAPES", 96, "", 999, "Show Capes", false, false), 
        CONNECTED_TEXTURES("CONNECTED_TEXTURES", 97, "", 999, "Connected Textures", false, false), 
        AA_LEVEL("AA_LEVEL", 98, "", 999, "Antialiasing", false, false), 
        ANIMATED_TEXTURES("ANIMATED_TEXTURES", 99, "", 999, "Textures Animated", false, false), 
        NATURAL_TEXTURES("NATURAL_TEXTURES", 100, "", 999, "Natural Textures", false, false), 
        HELD_ITEM_TOOLTIPS("HELD_ITEM_TOOLTIPS", 102, "", 999, "Held Item Tooltips", false, false), 
        DROPPED_ITEMS("DROPPED_ITEMS", 103, "", 999, "Dropped Items", false, false), 
        LAZY_CHUNK_LOADING("LAZY_CHUNK_LOADING", 104, "", 999, "Lazy Chunk Loading", false, false), 
        CUSTOM_SKY("CUSTOM_SKY", 105, "", 999, "Custom Sky", false, false), 
        FAST_MATH("FAST_MATH", 106, "", 999, "Fast Math", false, false), 
        TRANSLUCENT_BLOCKS("TRANSLUCENT_BLOCKS", 108, "", 999, "Translucent Blocks", false, false), 
        DYNAMIC_FOV("DYNAMIC_FOV", 109, "", 999, "Dynamic FOV", false, false), 
        DYNAMIC_LIGHTS("DYNAMIC_LIGHTS", 110, "", 999, "Dynamic Lights", false, false);
        
        private final boolean enumFloat;
        private final boolean enumBoolean;
        private final String enumString;
        private final float valueStep;
        private float valueMin;
        private float valueMax;
        private static final Options[] $VALUES;
        private static final Options[] $VALUES$;
        
        public static Options getEnumOptions(final int par0) {
            for (final Options var4 : values()) {
                if (var4.returnEnumOrdinal() == par0) {
                    return var4;
                }
            }
            return null;
        }
        
        private Options(final String p_i46401_1_, final int p_i46401_2_, final String par1Str, final int par2, final String par3Str, final boolean par4, final boolean par5) {
            this(p_i46401_1_, p_i46401_2_, par1Str, par2, par3Str, par4, par5, 0.0f, 1.0f, 0.0f);
        }
        
        private Options(final String p_i46402_1_, final int p_i46402_2_, final String p_i45004_1_, final int p_i45004_2_, final String p_i45004_3_, final boolean p_i45004_4_, final boolean p_i45004_5_, final float p_i45004_6_, final float p_i45004_7_, final float p_i45004_8_) {
            this.enumString = p_i45004_3_;
            this.enumFloat = p_i45004_4_;
            this.enumBoolean = p_i45004_5_;
            this.valueMin = p_i45004_6_;
            this.valueMax = p_i45004_7_;
            this.valueStep = p_i45004_8_;
        }
        
        public boolean getEnumFloat() {
            return this.enumFloat;
        }
        
        public boolean getEnumBoolean() {
            return this.enumBoolean;
        }
        
        public int returnEnumOrdinal() {
            return this.ordinal();
        }
        
        public String getEnumString() {
            return this.enumString;
        }
        
        public float getValueMax() {
            return this.valueMax;
        }
        
        public void setValueMax(final float p_148263_1_) {
            this.valueMax = p_148263_1_;
        }
        
        public float normalizeValue(final float p_148266_1_) {
            return MathHelper.clamp_float((this.snapToStepClamp(p_148266_1_) - this.valueMin) / (this.valueMax - this.valueMin), 0.0f, 1.0f);
        }
        
        public float denormalizeValue(final float p_148262_1_) {
            return this.snapToStepClamp(this.valueMin + (this.valueMax - this.valueMin) * MathHelper.clamp_float(p_148262_1_, 0.0f, 1.0f));
        }
        
        public float snapToStepClamp(float p_148268_1_) {
            p_148268_1_ = this.snapToStep(p_148268_1_);
            return MathHelper.clamp_float(p_148268_1_, this.valueMin, this.valueMax);
        }
        
        protected float snapToStep(float p_148264_1_) {
            if (this.valueStep > 0.0f) {
                p_148264_1_ = this.valueStep * Math.round(p_148264_1_ / this.valueStep);
            }
            return p_148264_1_;
        }
        
        private Options(final String p_i46403_1_, final int p_i46403_2_, final String p_i45005_1_, final int p_i45005_2_, final String p_i45005_3_, final boolean p_i45005_4_, final boolean p_i45005_5_, final float p_i45005_6_, final float p_i45005_7_, final float p_i45005_8_, final Object p_i45005_9_) {
            this(p_i46403_1_, p_i46403_2_, p_i45005_1_, p_i45005_2_, p_i45005_3_, p_i45005_4_, p_i45005_5_, p_i45005_6_, p_i45005_7_, p_i45005_8_);
        }
        
        private Options(final String x0, final int x1, final String x2, final int x3, final String x4, final boolean x5, final boolean x6, final float x7, final float x8, final float x9, final Object x10, final Object x11) {
            this(x0, x1, x2, x3, x4, x5, x6, x7, x8, x9, x10);
        }
        
        static {
            $VALUES = new Options[] { Options.INVERT_MOUSE, Options.SENSITIVITY, Options.FOV, Options.GAMMA, Options.SATURATION, Options.RENDER_DISTANCE, Options.VIEW_BOBBING, Options.ADVANCED_OPENGL, Options.FRAMERATE_LIMIT, Options.FBO_ENABLE, Options.DIFFICULTY, Options.GRAPHICS, Options.AMBIENT_OCCLUSION, Options.GUI_SCALE, Options.RENDER_CLOUDS, Options.PARTICLES, Options.CHAT_VISIBILITY, Options.CHAT_COLOR, Options.CHAT_LINKS, Options.CHAT_OPACITY, Options.CHAT_LINKS_PROMPT, Options.SNOOPER_ENABLED, Options.USE_FULLSCREEN, Options.ENABLE_VSYNC, Options.SHOW_CAPE, Options.TOUCHSCREEN, Options.CHAT_SCALE, Options.CHAT_WIDTH, Options.CHAT_HEIGHT_FOCUSED, Options.CHAT_HEIGHT_UNFOCUSED, Options.MIPMAP_LEVELS, Options.ANISOTROPIC_FILTERING, Options.FORCE_UNICODE_FONT, Options.STREAM_BYTES_PER_PIXEL, Options.STREAM_VOLUME_MIC, Options.STREAM_VOLUME_SYSTEM, Options.STREAM_KBPS, Options.STREAM_FPS, Options.STREAM_COMPRESSION, Options.STREAM_SEND_METADATA, Options.STREAM_CHAT_ENABLED, Options.STREAM_CHAT_USER_FILTER, Options.STREAM_MIC_TOGGLE_BEHAVIOR };
            $VALUES$ = new Options[] { Options.INVERT_MOUSE, Options.SENSITIVITY, Options.FOV, Options.GAMMA, Options.SATURATION, Options.RENDER_DISTANCE, Options.VIEW_BOBBING, Options.ADVANCED_OPENGL, Options.FRAMERATE_LIMIT, Options.FBO_ENABLE, Options.DIFFICULTY, Options.GRAPHICS, Options.AMBIENT_OCCLUSION, Options.GUI_SCALE, Options.RENDER_CLOUDS, Options.PARTICLES, Options.CHAT_VISIBILITY, Options.CHAT_COLOR, Options.CHAT_LINKS, Options.CHAT_OPACITY, Options.CHAT_LINKS_PROMPT, Options.SNOOPER_ENABLED, Options.USE_FULLSCREEN, Options.ENABLE_VSYNC, Options.SHOW_CAPE, Options.TOUCHSCREEN, Options.CHAT_SCALE, Options.CHAT_WIDTH, Options.CHAT_HEIGHT_FOCUSED, Options.CHAT_HEIGHT_UNFOCUSED, Options.MIPMAP_LEVELS, Options.ANISOTROPIC_FILTERING, Options.FORCE_UNICODE_FONT, Options.STREAM_BYTES_PER_PIXEL, Options.STREAM_VOLUME_MIC, Options.STREAM_VOLUME_SYSTEM, Options.STREAM_KBPS, Options.STREAM_FPS, Options.STREAM_COMPRESSION, Options.STREAM_SEND_METADATA, Options.STREAM_CHAT_ENABLED, Options.STREAM_CHAT_USER_FILTER, Options.STREAM_MIC_TOGGLE_BEHAVIOR, Options.FOG_FANCY, Options.FOG_START, Options.MIPMAP_TYPE, Options.LOAD_FAR, Options.PRELOADED_CHUNKS, Options.SMOOTH_FPS, Options.CLOUDS, Options.CLOUD_HEIGHT, Options.TREES, Options.GRASS, Options.RAIN, Options.WATER, Options.ANIMATED_WATER, Options.ANIMATED_LAVA, Options.ANIMATED_FIRE, Options.ANIMATED_PORTAL, Options.AO_LEVEL, Options.LAGOMETER, Options.SHOW_FPS, Options.AUTOSAVE_TICKS, Options.BETTER_GRASS, Options.ANIMATED_REDSTONE, Options.ANIMATED_EXPLOSION, Options.ANIMATED_FLAME, Options.ANIMATED_SMOKE, Options.WEATHER, Options.SKY, Options.STARS, Options.SUN_MOON, Options.VIGNETTE, Options.CHUNK_UPDATES, Options.CHUNK_UPDATES_DYNAMIC, Options.TIME, Options.CLEAR_WATER, Options.SMOOTH_WORLD, Options.DEPTH_FOG, Options.VOID_PARTICLES, Options.WATER_PARTICLES, Options.RAIN_SPLASH, Options.PORTAL_PARTICLES, Options.POTION_PARTICLES, Options.PROFILER, Options.DRIPPING_WATER_LAVA, Options.BETTER_SNOW, Options.FULLSCREEN_MODE, Options.ANIMATED_TERRAIN, Options.ANIMATED_ITEMS, Options.SWAMP_COLORS, Options.RANDOM_MOBS, Options.SMOOTH_BIOMES, Options.CUSTOM_FONTS, Options.CUSTOM_COLORS, Options.SHOW_CAPES, Options.CONNECTED_TEXTURES, Options.AA_LEVEL, Options.ANIMATED_TEXTURES, Options.NATURAL_TEXTURES, Options.HELD_ITEM_TOOLTIPS, Options.DROPPED_ITEMS, Options.LAZY_CHUNK_LOADING, Options.CUSTOM_SKY, Options.FAST_MATH, Options.TRANSLUCENT_BLOCKS, Options.DYNAMIC_FOV, Options.DYNAMIC_LIGHTS };
        }
    }
    
    static final class SwitchOptions
    {
        static final int[] optionIds;
        
        static {
            optionIds = new int[Options.values().length];
            try {
                SwitchOptions.optionIds[Options.INVERT_MOUSE.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchOptions.optionIds[Options.VIEW_BOBBING.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchOptions.optionIds[Options.ADVANCED_OPENGL.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchOptions.optionIds[Options.FBO_ENABLE.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchOptions.optionIds[Options.RENDER_CLOUDS.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                SwitchOptions.optionIds[Options.CHAT_COLOR.ordinal()] = 7;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                SwitchOptions.optionIds[Options.CHAT_LINKS.ordinal()] = 8;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                SwitchOptions.optionIds[Options.CHAT_LINKS_PROMPT.ordinal()] = 9;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                SwitchOptions.optionIds[Options.SNOOPER_ENABLED.ordinal()] = 10;
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
            try {
                SwitchOptions.optionIds[Options.USE_FULLSCREEN.ordinal()] = 11;
            }
            catch (NoSuchFieldError noSuchFieldError10) {}
            try {
                SwitchOptions.optionIds[Options.ENABLE_VSYNC.ordinal()] = 12;
            }
            catch (NoSuchFieldError noSuchFieldError11) {}
            try {
                SwitchOptions.optionIds[Options.SHOW_CAPE.ordinal()] = 13;
            }
            catch (NoSuchFieldError noSuchFieldError12) {}
            try {
                SwitchOptions.optionIds[Options.TOUCHSCREEN.ordinal()] = 14;
            }
            catch (NoSuchFieldError noSuchFieldError13) {}
            try {
                SwitchOptions.optionIds[Options.STREAM_SEND_METADATA.ordinal()] = 15;
            }
            catch (NoSuchFieldError noSuchFieldError14) {}
            try {
                SwitchOptions.optionIds[Options.FORCE_UNICODE_FONT.ordinal()] = 16;
            }
            catch (NoSuchFieldError noSuchFieldError15) {}
        }
    }
}
