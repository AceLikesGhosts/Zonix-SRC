package net.minecraft.client;

import net.minecraft.client.entity.*;
import net.minecraft.client.particle.*;
import net.minecraft.server.integrated.*;
import net.minecraft.client.gui.achievement.*;
import net.minecraft.profiler.*;
import net.minecraft.client.shader.*;
import net.minecraft.client.audio.*;
import com.mojang.authlib.minecraft.*;
import net.minecraft.server.*;
import com.mojang.authlib.yggdrasil.*;
import javax.imageio.*;
import java.nio.*;
import org.lwjgl.*;
import com.google.common.collect.*;
import net.minecraft.client.stream.*;
import net.minecraft.client.resources.data.*;
import net.minecraft.world.chunk.storage.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.multiplayer.*;
import java.io.*;
import java.awt.image.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;
import us.zonix.client.*;
import us.zonix.client.gui.*;
import org.lwjgl.util.glu.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.*;
import java.text.*;
import us.zonix.client.util.*;
import net.minecraft.client.settings.*;
import net.minecraft.block.material.*;
import org.lwjgl.input.*;
import net.minecraft.network.play.client.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.crash.*;
import net.minecraft.client.resources.*;
import net.minecraft.network.*;
import net.minecraft.network.handshake.client.*;
import io.netty.util.concurrent.*;
import net.minecraft.network.login.client.*;
import net.minecraft.world.storage.*;
import java.net.*;
import net.minecraft.stats.*;
import net.minecraft.client.network.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import org.lwjgl.opengl.*;
import net.minecraft.world.*;
import net.minecraft.entity.boss.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import org.apache.commons.lang3.*;
import com.google.common.util.concurrent.*;
import java.util.concurrent.*;
import org.apache.logging.log4j.*;

public class Minecraft implements IPlayerUsage
{
    private static final Logger logger;
    private static final ResourceLocation locationMojangPng;
    public static final boolean isRunningOnMac;
    public static byte[] memoryReserve;
    private static final List macDisplayModes;
    private final File fileResourcepacks;
    private final Multimap field_152356_J;
    private ServerData currentServerData;
    public TextureManager renderEngine;
    private static Minecraft theMinecraft;
    public PlayerControllerMP playerController;
    private boolean fullscreen;
    private boolean hasCrashed;
    private CrashReport crashReporter;
    public int displayWidth;
    public int displayHeight;
    private Timer timer;
    private PlayerUsageSnooper usageSnooper;
    public WorldClient theWorld;
    public RenderGlobal renderGlobal;
    public EntityClientPlayerMP thePlayer;
    public EntityLivingBase renderViewEntity;
    public Entity pointedEntity;
    public EffectRenderer effectRenderer;
    private final Session session;
    private boolean isGamePaused;
    public FontRenderer fontRenderer;
    public FontRenderer standardGalacticFontRenderer;
    public GuiScreen currentScreen;
    public LoadingScreenRenderer loadingScreen;
    public EntityRenderer entityRenderer;
    private int leftClickCounter;
    private int tempDisplayWidth;
    private int tempDisplayHeight;
    private IntegratedServer theIntegratedServer;
    public GuiAchievement guiAchievement;
    public GuiIngame ingameGUI;
    public boolean skipRenderWorld;
    public MovingObjectPosition objectMouseOver;
    public GameSettings gameSettings;
    public MouseHelper mouseHelper;
    public final File mcDataDir;
    private final File fileAssets;
    private final String launchedVersion;
    private final Proxy proxy;
    private ISaveFormat saveLoader;
    public static int debugFPS;
    private int rightClickDelayTimer;
    private boolean refreshTexturePacksScheduled;
    private String serverName;
    private int serverPort;
    public boolean inGameHasFocus;
    long systemTime;
    private int joinPlayerCounter;
    private final boolean jvm64bit;
    private final boolean isDemo;
    private NetworkManager myNetworkManager;
    private boolean integratedServerIsRunning;
    public final Profiler mcProfiler;
    private long field_83002_am;
    private IReloadableResourceManager mcResourceManager;
    private final IMetadataSerializer metadataSerializer_;
    private List defaultResourcePacks;
    private DefaultResourcePack mcDefaultResourcePack;
    private ResourcePackRepository mcResourcePackRepository;
    private LanguageManager mcLanguageManager;
    private IStream field_152353_at;
    private Framebuffer mcFramebuffer;
    private TextureMap textureMapBlocks;
    private SoundHandler mcSoundHandler;
    private MusicTicker mcMusicTicker;
    private ResourceLocation field_152354_ay;
    private final MinecraftSessionService field_152355_az;
    private SkinManager field_152350_aA;
    private final Queue field_152351_aB;
    private final Thread field_152352_aC;
    volatile boolean running;
    public String debug;
    long debugUpdateTime;
    int fpsCounter;
    long prevFrameTime;
    private String debugProfilerName;
    private static final String __OBFID = "CL_00000631";
    private boolean initialised;
    public boolean displayActive;
    private Queue<Long> tickTimes;
    private double tickTime;
    
    public Minecraft(final Session p_i1103_1_, final int p_i1103_2_, final int p_i1103_3_, final boolean p_i1103_4_, final boolean p_i1103_5_, final File p_i1103_6_, final File p_i1103_7_, final File p_i1103_8_, final Proxy p_i1103_9_, final String p_i1103_10_, final Multimap p_i1103_11_, final String p_i1103_12_) {
        this.timer = new Timer(20.0f);
        this.usageSnooper = new PlayerUsageSnooper("client", this, MinecraftServer.getSystemTimeMillis());
        this.systemTime = getSystemTime();
        this.mcProfiler = new Profiler();
        this.field_83002_am = -1L;
        this.metadataSerializer_ = new IMetadataSerializer();
        this.defaultResourcePacks = Lists.newArrayList();
        this.field_152351_aB = Queues.newArrayDeque();
        this.field_152352_aC = Thread.currentThread();
        this.running = true;
        this.debug = "";
        this.debugUpdateTime = getSystemTime();
        this.prevFrameTime = -1L;
        this.debugProfilerName = "root";
        this.tickTimes = new ArrayDeque<Long>();
        Minecraft.theMinecraft = this;
        this.mcDataDir = p_i1103_6_;
        this.fileAssets = p_i1103_7_;
        this.fileResourcepacks = p_i1103_8_;
        this.launchedVersion = p_i1103_10_;
        this.field_152356_J = p_i1103_11_;
        this.mcDefaultResourcePack = new DefaultResourcePack(new ResourceIndex(p_i1103_7_, p_i1103_12_).func_152782_a());
        this.addDefaultResourcePack();
        this.proxy = ((p_i1103_9_ == null) ? Proxy.NO_PROXY : p_i1103_9_);
        this.field_152355_az = new YggdrasilAuthenticationService(p_i1103_9_, UUID.randomUUID().toString()).createMinecraftSessionService();
        this.startTimerHackThread();
        this.session = p_i1103_1_;
        Minecraft.logger.info("Setting user: " + p_i1103_1_.getUsername());
        Minecraft.logger.info("(Session ID is " + p_i1103_1_.getSessionID() + ")");
        this.isDemo = p_i1103_5_;
        this.displayWidth = p_i1103_2_;
        this.displayHeight = p_i1103_3_;
        this.tempDisplayWidth = p_i1103_2_;
        this.tempDisplayHeight = p_i1103_3_;
        this.fullscreen = p_i1103_4_;
        this.jvm64bit = isJvm64bit();
        ImageIO.setUseCache(false);
        Bootstrap.func_151354_b();
    }
    
    private static boolean isJvm64bit() {
        final String[] var2;
        final String[] var0 = var2 = new String[] { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" };
        for (int var3 = var0.length, var4 = 0; var4 < var3; ++var4) {
            final String var5 = var2[var4];
            final String var6 = System.getProperty(var5);
            if (var6 != null && var6.contains("64")) {
                return true;
            }
        }
        return false;
    }
    
    public Framebuffer getFramebuffer() {
        return this.mcFramebuffer;
    }
    
    private void startTimerHackThread() {
        final Thread var1 = new Thread("Timer hack thread") {
            private static final String __OBFID = "CL_00000632";
            
            @Override
            public void run() {
                while (Minecraft.this.running) {
                    try {
                        Thread.sleep(2147483647L);
                    }
                    catch (InterruptedException ex) {}
                }
            }
        };
        var1.setDaemon(true);
        var1.start();
    }
    
    public void crashed(final CrashReport p_71404_1_) {
        this.hasCrashed = true;
        this.crashReporter = p_71404_1_;
    }
    
    public void displayCrashReport(final CrashReport p_71377_1_) {
        final File var2 = new File(getMinecraft().mcDataDir, "crash-reports");
        final File var3 = new File(var2, "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-client.txt");
        System.out.println(p_71377_1_.getCompleteReport());
        if (p_71377_1_.getFile() != null) {
            System.out.println("#@!@# Game crashed! Crash report saved to: #@!@# " + p_71377_1_.getFile());
            System.exit(-1);
        }
        else if (p_71377_1_.saveToFile(var3)) {
            System.out.println("#@!@# Game crashed! Crash report saved to: #@!@# " + var3.getAbsolutePath());
            System.exit(-1);
        }
        else {
            System.out.println("#@?@# Game crashed! Crash report could not be saved. #@?@#");
            System.exit(-2);
        }
    }
    
    public void setServer(final String p_71367_1_, final int p_71367_2_) {
        this.serverName = p_71367_1_;
        this.serverPort = p_71367_2_;
    }
    
    private void startGame() throws LWJGLException {
        this.gameSettings = new GameSettings(this, this.mcDataDir);
        if (this.gameSettings.overrideHeight > 0 && this.gameSettings.overrideWidth > 0) {
            this.displayWidth = this.gameSettings.overrideWidth;
            this.displayHeight = this.gameSettings.overrideHeight;
        }
        if (this.fullscreen) {
            Display.setFullscreen(true);
            this.displayWidth = Display.getDisplayMode().getWidth();
            this.displayHeight = Display.getDisplayMode().getHeight();
            if (this.displayWidth <= 0) {
                this.displayWidth = 1;
            }
            if (this.displayHeight <= 0) {
                this.displayHeight = 1;
            }
        }
        else {
            Display.setDisplayMode(new DisplayMode(this.displayWidth, this.displayHeight));
        }
        Display.setResizable(true);
        Display.setTitle("Minecraft 1.7.10");
        Minecraft.logger.info("LWJGL Version: " + Sys.getVersion());
        final Util.EnumOS var1 = Util.getOSType();
        if (var1 != Util.EnumOS.OSX) {
            try {
                final InputStream var2 = this.mcDefaultResourcePack.func_152780_c(new ResourceLocation("icons/icon_16x16.png"));
                final InputStream var3 = this.mcDefaultResourcePack.func_152780_c(new ResourceLocation("icons/icon_32x32.png"));
                if (var2 != null && var3 != null) {
                    Display.setIcon(new ByteBuffer[] { this.func_152340_a(var2), this.func_152340_a(var3) });
                }
            }
            catch (IOException var4) {
                Minecraft.logger.error("Couldn't set icon", (Throwable)var4);
            }
        }
        try {
            Display.create(new PixelFormat().withDepthBits(24));
        }
        catch (LWJGLException var5) {
            Minecraft.logger.error("Couldn't set pixel format", (Throwable)var5);
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException ex) {}
            if (this.fullscreen) {
                this.updateDisplayMode();
            }
            Display.create();
        }
        OpenGlHelper.initializeTextures();
        try {
            this.field_152353_at = new TwitchStream(this, (String)Iterables.getFirst((Iterable)this.field_152356_J.get((Object)"twitch_access_token"), (Object)null));
        }
        catch (Throwable var6) {
            this.field_152353_at = new NullStream(var6);
            Minecraft.logger.error("Couldn't initialize twitch stream");
        }
        (this.mcFramebuffer = new Framebuffer(this.displayWidth, this.displayHeight, true)).setFramebufferColor(0.0f, 0.0f, 0.0f, 0.0f);
        this.guiAchievement = new GuiAchievement(this);
        this.metadataSerializer_.registerMetadataSectionType(new TextureMetadataSectionSerializer(), TextureMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(new FontMetadataSectionSerializer(), FontMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(new AnimationMetadataSectionSerializer(), AnimationMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(new PackMetadataSectionSerializer(), PackMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(new LanguageMetadataSectionSerializer(), LanguageMetadataSection.class);
        this.saveLoader = new AnvilSaveConverter(new File(this.mcDataDir, "saves"));
        this.mcResourcePackRepository = new ResourcePackRepository(this.fileResourcepacks, new File(this.mcDataDir, "server-resource-packs"), this.mcDefaultResourcePack, this.metadataSerializer_, this.gameSettings);
        this.mcResourceManager = new SimpleReloadableResourceManager(this.metadataSerializer_);
        this.mcLanguageManager = new LanguageManager(this.metadataSerializer_, this.gameSettings.language);
        this.mcResourceManager.registerReloadListener(this.mcLanguageManager);
        this.refreshResources();
        this.renderEngine = new TextureManager(this.mcResourceManager);
        this.mcResourceManager.registerReloadListener(this.renderEngine);
        this.field_152350_aA = new SkinManager(this.renderEngine, new File(this.fileAssets, "skins"), this.field_152355_az);
        this.loadScreen();
        this.mcSoundHandler = new SoundHandler(this.mcResourceManager, this.gameSettings);
        this.mcResourceManager.registerReloadListener(this.mcSoundHandler);
        this.mcMusicTicker = new MusicTicker(this);
        this.fontRenderer = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii.png"), this.renderEngine, false);
        if (this.gameSettings.language != null) {
            this.fontRenderer.setUnicodeFlag(this.func_152349_b());
            this.fontRenderer.setBidiFlag(this.mcLanguageManager.isCurrentLanguageBidirectional());
        }
        this.standardGalacticFontRenderer = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii_sga.png"), this.renderEngine, false);
        this.mcResourceManager.registerReloadListener(this.fontRenderer);
        this.mcResourceManager.registerReloadListener(this.standardGalacticFontRenderer);
        this.mcResourceManager.registerReloadListener(new GrassColorReloadListener());
        this.mcResourceManager.registerReloadListener(new FoliageColorReloadListener());
        RenderManager.instance.itemRenderer = new ItemRenderer(this);
        this.entityRenderer = new EntityRenderer(this, this.mcResourceManager);
        this.mcResourceManager.registerReloadListener(this.entityRenderer);
        AchievementList.openInventory.setStatStringFormatter(new IStatStringFormat() {
            private static final String __OBFID = "CL_00000639";
            
            @Override
            public String formatString(final String p_74535_1_) {
                try {
                    return String.format(p_74535_1_, GameSettings.getKeyDisplayString(Minecraft.this.gameSettings.keyBindInventory.getKeyCode()));
                }
                catch (Exception var3) {
                    return "Error: " + var3.getLocalizedMessage();
                }
            }
        });
        this.mouseHelper = new MouseHelper();
        this.checkGLError("Pre startup");
        GL11.glEnable(3553);
        GL11.glShadeModel(7425);
        GL11.glClearDepth(1.0);
        GL11.glEnable(2929);
        GL11.glDepthFunc(515);
        GL11.glEnable(3008);
        GL11.glAlphaFunc(516, 0.1f);
        GL11.glCullFace(1029);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(5888);
        this.checkGLError("Startup");
        this.renderGlobal = new RenderGlobal(this);
        (this.textureMapBlocks = new TextureMap(0, "textures/blocks")).func_147632_b(this.gameSettings.anisotropicFiltering);
        this.textureMapBlocks.func_147633_a(this.gameSettings.mipmapLevels);
        this.renderEngine.loadTextureMap(TextureMap.locationBlocksTexture, this.textureMapBlocks);
        this.renderEngine.loadTextureMap(TextureMap.locationItemsTexture, new TextureMap(1, "textures/items"));
        GL11.glViewport(0, 0, this.displayWidth, this.displayHeight);
        this.effectRenderer = new EffectRenderer(this.theWorld, this.renderEngine);
        this.checkGLError("Post startup");
        this.ingameGUI = new GuiIngame(this);
        if (this.serverName != null) {
            this.displayGuiScreen(new GuiConnecting(new GuiMainMenu(), this, this.serverName, this.serverPort));
        }
        else {
            this.displayGuiScreen(new GuiMainMenu());
        }
        this.renderEngine.func_147645_c(this.field_152354_ay);
        this.field_152354_ay = null;
        this.loadingScreen = new LoadingScreenRenderer(this);
        if (this.gameSettings.fullScreen && !this.fullscreen) {
            this.toggleFullscreen();
        }
        try {
            Display.setVSyncEnabled(this.gameSettings.enableVsync);
        }
        catch (OpenGLException var7) {
            this.gameSettings.enableVsync = false;
            this.gameSettings.saveOptions();
        }
    }
    
    public boolean func_152349_b() {
        return this.mcLanguageManager != null && !this.gameSettings.forceUnicodeFont && (this.mcLanguageManager.isCurrentLocaleUnicode() || this.gameSettings.forceUnicodeFont);
    }
    
    public void refreshResources() {
        final ArrayList var1 = Lists.newArrayList((Iterable)this.defaultResourcePacks);
        for (final ResourcePackRepository.Entry var3 : this.mcResourcePackRepository.getRepositoryEntries()) {
            var1.add(var3.getResourcePack());
        }
        if (this.mcResourcePackRepository.func_148530_e() != null) {
            var1.add(this.mcResourcePackRepository.func_148530_e());
        }
        try {
            this.mcResourceManager.reloadResources(var1);
        }
        catch (RuntimeException var4) {
            Minecraft.logger.info("Caught error stitching, removing all assigned resourcepacks", (Throwable)var4);
            var1.clear();
            var1.addAll(this.defaultResourcePacks);
            this.mcResourcePackRepository.func_148527_a(Collections.emptyList());
            this.mcResourceManager.reloadResources(var1);
            this.gameSettings.resourcePacks.clear();
            this.gameSettings.saveOptions();
        }
        this.mcLanguageManager.parseLanguageMetadata(var1);
        if (this.renderGlobal != null) {
            this.renderGlobal.loadRenderers();
        }
    }
    
    private void addDefaultResourcePack() {
        this.defaultResourcePacks.add(this.mcDefaultResourcePack);
    }
    
    private ByteBuffer func_152340_a(final InputStream p_152340_1_) throws IOException {
        final BufferedImage var2 = ImageIO.read(p_152340_1_);
        final int[] var3 = var2.getRGB(0, 0, var2.getWidth(), var2.getHeight(), null, 0, var2.getWidth());
        final ByteBuffer var4 = ByteBuffer.allocate(4 * var3.length);
        final int[] var5 = var3;
        for (int var6 = var3.length, var7 = 0; var7 < var6; ++var7) {
            final int var8 = var5[var7];
            var4.putInt(var8 << 8 | (var8 >> 24 & 0xFF));
        }
        var4.flip();
        return var4;
    }
    
    private void updateDisplayMode() throws LWJGLException {
        final HashSet var1 = new HashSet();
        Collections.addAll(var1, Display.getAvailableDisplayModes());
        DisplayMode var2 = Display.getDesktopDisplayMode();
        if (!var1.contains(var2) && Util.getOSType() == Util.EnumOS.OSX) {
            for (final DisplayMode var4 : Minecraft.macDisplayModes) {
                boolean var5 = true;
                for (final DisplayMode var7 : var1) {
                    if (var7.getBitsPerPixel() == 32 && var7.getWidth() == var4.getWidth() && var7.getHeight() == var4.getHeight()) {
                        var5 = false;
                        break;
                    }
                }
                if (!var5) {
                    for (final DisplayMode var7 : var1) {
                        if (var7.getBitsPerPixel() == 32 && var7.getWidth() == var4.getWidth() / 2 && var7.getHeight() == var4.getHeight() / 2) {
                            var2 = var7;
                            break;
                        }
                    }
                }
            }
        }
        Display.setDisplayMode(var2);
        this.displayWidth = var2.getWidth();
        this.displayHeight = var2.getHeight();
    }
    
    private void loadScreen() throws LWJGLException {
        final ScaledResolution var1 = new ScaledResolution(this, this.displayWidth, this.displayHeight);
        final int var2 = var1.getScaleFactor();
        final Framebuffer var3 = new Framebuffer(var1.getScaledWidth() * var2, var1.getScaledHeight() * var2, true);
        var3.bindFramebuffer(false);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, (double)var1.getScaledWidth(), (double)var1.getScaledHeight(), 0.0, 1000.0, 3000.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
        GL11.glDisable(2896);
        GL11.glDisable(2912);
        GL11.glDisable(2929);
        GL11.glEnable(3553);
        try {
            this.field_152354_ay = this.renderEngine.getDynamicTextureLocation("logo", new DynamicTexture(ImageIO.read(this.mcDefaultResourcePack.getInputStream(Minecraft.locationMojangPng))));
            this.renderEngine.bindTexture(this.field_152354_ay);
        }
        catch (IOException var4) {
            Minecraft.logger.error("Unable to load logo: " + Minecraft.locationMojangPng, (Throwable)var4);
        }
        final Tessellator var5 = Tessellator.instance;
        var5.startDrawingQuads();
        var5.setColorOpaque_I(16777215);
        var5.addVertexWithUV(0.0, this.displayHeight, 0.0, 0.0, 0.0);
        var5.addVertexWithUV(this.displayWidth, this.displayHeight, 0.0, 0.0, 0.0);
        var5.addVertexWithUV(this.displayWidth, 0.0, 0.0, 0.0, 0.0);
        var5.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
        var5.draw();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        var5.setColorOpaque_I(16777215);
        final short var6 = 256;
        final short var7 = 256;
        this.scaledTessellator((var1.getScaledWidth() - var6) / 2, (var1.getScaledHeight() - var7) / 2, 0, 0, var6, var7);
        GL11.glDisable(2896);
        GL11.glDisable(2912);
        var3.unbindFramebuffer();
        var3.framebufferRender(var1.getScaledWidth() * var2, var1.getScaledHeight() * var2);
        GL11.glEnable(3008);
        GL11.glAlphaFunc(516, 0.1f);
        GL11.glFlush();
        this.func_147120_f();
    }
    
    public void scaledTessellator(final int p_71392_1_, final int p_71392_2_, final int p_71392_3_, final int p_71392_4_, final int p_71392_5_, final int p_71392_6_) {
        final float var7 = 0.00390625f;
        final float var8 = 0.00390625f;
        final Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        var9.addVertexWithUV(p_71392_1_ + 0, p_71392_2_ + p_71392_6_, 0.0, (p_71392_3_ + 0) * var7, (p_71392_4_ + p_71392_6_) * var8);
        var9.addVertexWithUV(p_71392_1_ + p_71392_5_, p_71392_2_ + p_71392_6_, 0.0, (p_71392_3_ + p_71392_5_) * var7, (p_71392_4_ + p_71392_6_) * var8);
        var9.addVertexWithUV(p_71392_1_ + p_71392_5_, p_71392_2_ + 0, 0.0, (p_71392_3_ + p_71392_5_) * var7, (p_71392_4_ + 0) * var8);
        var9.addVertexWithUV(p_71392_1_ + 0, p_71392_2_ + 0, 0.0, (p_71392_3_ + 0) * var7, (p_71392_4_ + 0) * var8);
        var9.draw();
    }
    
    public ISaveFormat getSaveLoader() {
        return this.saveLoader;
    }
    
    public void displayGuiScreen(GuiScreen p_147108_1_) {
        if (p_147108_1_ == null && this.currentScreen instanceof ModScreen) {
            Client.getInstance().getProfileManager().saveConfig();
        }
        if (this.currentScreen != null) {
            this.currentScreen.onGuiClosed();
        }
        if (p_147108_1_ == null && this.theWorld == null) {
            p_147108_1_ = new GuiMainMenu();
        }
        else if (p_147108_1_ == null && this.thePlayer.getHealth() <= 0.0f) {
            p_147108_1_ = new GuiGameOver();
        }
        if (p_147108_1_ instanceof GuiMainMenu) {
            p_147108_1_ = new MainMenuScreen();
            this.gameSettings.showDebugInfo = false;
            this.ingameGUI.getChatGUI().func_146231_a();
        }
        if ((this.currentScreen = p_147108_1_) != null) {
            this.setIngameNotInFocus();
            final ScaledResolution var2 = new ScaledResolution(this, this.displayWidth, this.displayHeight);
            final int var3 = var2.getScaledWidth();
            final int var4 = var2.getScaledHeight();
            p_147108_1_.setWorldAndResolution(this, var3, var4);
            this.skipRenderWorld = false;
        }
        else {
            this.mcSoundHandler.func_147687_e();
            this.setIngameFocus();
        }
        if (this.currentScreen == null && this.entityRenderer.isShaderActive()) {
            this.entityRenderer.deactivateShader();
        }
        else if (this.currentScreen != null && !this.entityRenderer.isShaderActive() && !(this.currentScreen instanceof GuiChat) && this.initialised) {
            this.entityRenderer.setBlur(true);
        }
    }
    
    private void checkGLError(final String p_71361_1_) {
        final int var2 = GL11.glGetError();
        if (var2 != 0) {
            final String var3 = GLU.gluErrorString(var2);
            Minecraft.logger.error("########## GL ERROR ##########");
            Minecraft.logger.error("@ " + p_71361_1_);
            Minecraft.logger.error(var2 + ": " + var3);
        }
    }
    
    public void shutdownMinecraftApplet() {
        try {
            this.field_152353_at.func_152923_i();
            Minecraft.logger.info("Stopping!");
            try {
                this.loadWorld(null);
            }
            catch (Throwable t) {}
            try {
                GLAllocation.deleteTexturesAndDisplayLists();
            }
            catch (Throwable t2) {}
            this.mcSoundHandler.func_147685_d();
        }
        finally {
            Display.destroy();
            if (!this.hasCrashed) {
                System.exit(0);
            }
        }
        System.gc();
    }
    
    public void run() {
        this.running = true;
        try {
            this.startGame();
        }
        catch (Throwable var3) {
            final CrashReport var2 = CrashReport.makeCrashReport(var3, "Initializing game");
            var2.makeCategory("Initialization");
            this.displayCrashReport(this.addGraphicsAndWorldToCrashReport(var2));
            return;
        }
        new Client();
        try {
            while (this.running) {
                if (this.hasCrashed) {
                    if (this.crashReporter != null) {
                        this.displayCrashReport(this.crashReporter);
                        return;
                    }
                }
                try {
                    this.runGameLoop();
                }
                catch (OutOfMemoryError var6) {
                    this.freeMemory();
                    this.displayGuiScreen(new GuiMemoryErrorScreen());
                    System.gc();
                }
            }
        }
        catch (MinecraftError minecraftError) {}
        catch (ReportedException var4) {
            this.addGraphicsAndWorldToCrashReport(var4.getCrashReport());
            this.freeMemory();
            Minecraft.logger.fatal("Reported exception thrown!", (Throwable)var4);
            this.displayCrashReport(var4.getCrashReport());
        }
        catch (Throwable var5) {
            final CrashReport var2 = this.addGraphicsAndWorldToCrashReport(new CrashReport("Unexpected error", var5));
            this.freeMemory();
            Minecraft.logger.fatal("Unreported exception thrown!", var5);
            this.displayCrashReport(var2);
        }
        finally {
            this.shutdownMinecraftApplet();
        }
    }
    
    private void runGameLoop() {
        this.mcProfiler.startSection("root");
        if (Display.isCreated() && Display.isCloseRequested()) {
            this.shutdown();
        }
        this.displayActive = Display.isActive();
        if (this.isGamePaused && this.theWorld != null) {
            final float var1 = this.timer.renderPartialTicks;
            this.timer.updateTimer();
            this.timer.renderPartialTicks = var1;
        }
        else {
            this.timer.updateTimer();
        }
        if ((this.theWorld == null || this.currentScreen == null) && this.refreshTexturePacksScheduled) {
            this.refreshTexturePacksScheduled = false;
            this.refreshResources();
        }
        final long var2 = System.nanoTime();
        this.mcProfiler.startSection("tick");
        if (this.gameSettings != null) {
            this.gameSettings.tick();
        }
        for (int var3 = 0; var3 < this.timer.elapsedTicks; ++var3) {
            this.runTick();
        }
        this.mcProfiler.endStartSection("preRenderErrors");
        final long var4 = System.nanoTime() - var2;
        this.checkGLError("Pre render");
        RenderBlocks.fancyGrass = this.gameSettings.fancyGraphics;
        this.mcProfiler.endStartSection("sound");
        this.mcSoundHandler.func_147691_a(this.thePlayer, this.timer.renderPartialTicks);
        this.mcProfiler.endSection();
        this.mcProfiler.startSection("render");
        GL11.glPushMatrix();
        GL11.glClear(16640);
        this.mcFramebuffer.bindFramebuffer(true);
        this.mcProfiler.startSection("display");
        GL11.glEnable(3553);
        if (this.thePlayer != null && this.thePlayer.isEntityInsideOpaqueBlock()) {
            this.gameSettings.thirdPersonView = 0;
        }
        this.mcProfiler.endSection();
        if (!this.skipRenderWorld) {
            this.mcProfiler.endStartSection("gameRenderer");
            this.entityRenderer.updateCameraAndRender(this.timer.renderPartialTicks);
            this.mcProfiler.endSection();
        }
        GL11.glFlush();
        this.mcProfiler.endSection();
        if (!this.displayActive && this.fullscreen) {
            this.toggleFullscreen();
        }
        if (this.gameSettings.showDebugInfo && this.gameSettings.showDebugProfilerChart) {
            if (!this.mcProfiler.profilingEnabled) {
                this.mcProfiler.clearProfiling();
            }
            this.mcProfiler.profilingEnabled = true;
            this.displayDebugInfo(var4);
        }
        else {
            this.mcProfiler.profilingEnabled = false;
            this.prevFrameTime = System.nanoTime();
            if (!this.tickTimes.isEmpty()) {
                this.tickTimes.clear();
            }
        }
        this.guiAchievement.func_146254_a();
        this.mcFramebuffer.unbindFramebuffer();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.mcFramebuffer.framebufferRender(this.displayWidth, this.displayHeight);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.entityRenderer.func_152430_c(this.timer.renderPartialTicks);
        GL11.glPopMatrix();
        this.mcProfiler.startSection("root");
        this.func_147120_f();
        Thread.yield();
        this.mcProfiler.startSection("stream");
        this.mcProfiler.startSection("update");
        this.field_152353_at.func_152935_j();
        this.mcProfiler.endStartSection("submit");
        this.field_152353_at.func_152922_k();
        this.mcProfiler.endSection();
        this.mcProfiler.endSection();
        this.checkGLError("Post render");
        ++this.fpsCounter;
        this.isGamePaused = (this.isSingleplayer() && this.currentScreen != null && this.currentScreen.doesGuiPauseGame() && !this.theIntegratedServer.getPublic());
        while (getSystemTime() >= this.debugUpdateTime + 1000L) {
            Minecraft.debugFPS = this.fpsCounter;
            this.debug = Minecraft.debugFPS + " fps, " + WorldRenderer.chunksUpdated + " chunk updates";
            WorldRenderer.chunksUpdated = 0;
            this.debugUpdateTime += 1000L;
            this.fpsCounter = 0;
            this.usageSnooper.addMemoryStatsToSnooper();
            if (!this.usageSnooper.isSnooperRunning()) {
                this.usageSnooper.startSnooper();
            }
        }
        this.mcProfiler.endSection();
        if (this.isFramerateLimitBelowMax()) {
            Display.sync(this.getLimitFramerate());
        }
    }
    
    public void func_147120_f() {
        Display.update();
        if (!this.fullscreen && Display.wasResized()) {
            final int var1 = this.displayWidth;
            final int var2 = this.displayHeight;
            this.displayWidth = Display.getWidth();
            this.displayHeight = Display.getHeight();
            if (this.displayWidth != var1 || this.displayHeight != var2) {
                if (this.displayWidth <= 0) {
                    this.displayWidth = 1;
                }
                if (this.displayHeight <= 0) {
                    this.displayHeight = 1;
                }
                this.resize(this.displayWidth, this.displayHeight);
            }
        }
    }
    
    public int getLimitFramerate() {
        if (!this.displayActive) {
            return 30;
        }
        return (this.theWorld == null && this.currentScreen != null) ? 30 : this.gameSettings.limitFramerate;
    }
    
    public boolean isFramerateLimitBelowMax() {
        return this.getLimitFramerate() < GameSettings.Options.FRAMERATE_LIMIT.getValueMax();
    }
    
    public void freeMemory() {
        try {
            Minecraft.memoryReserve = new byte[0];
            this.renderGlobal.deleteAllDisplayLists();
        }
        catch (Throwable t) {}
        try {
            System.gc();
        }
        catch (Throwable t2) {}
        try {
            System.gc();
            this.loadWorld(null);
        }
        catch (Throwable t3) {}
        System.gc();
    }
    
    private void updateDebugProfilerName(int p_71383_1_) {
        final List var2 = this.mcProfiler.getProfilingData(this.debugProfilerName);
        if (var2 != null && !var2.isEmpty()) {
            final Profiler.Result var3 = var2.remove(0);
            if (p_71383_1_ == 0) {
                if (var3.field_76331_c.length() > 0) {
                    final int var4 = this.debugProfilerName.lastIndexOf(".");
                    if (var4 >= 0) {
                        this.debugProfilerName = this.debugProfilerName.substring(0, var4);
                    }
                }
            }
            else if (--p_71383_1_ < var2.size() && !var2.get(p_71383_1_).field_76331_c.equals("unspecified")) {
                if (this.debugProfilerName.length() > 0) {
                    this.debugProfilerName += ".";
                }
                this.debugProfilerName += var2.get(p_71383_1_).field_76331_c;
            }
        }
    }
    
    private void displayDebugInfo(final long p_71366_1_) {
        if (this.mcProfiler.profilingEnabled) {
            final List var3 = this.mcProfiler.getProfilingData(this.debugProfilerName);
            final Profiler.Result var4 = var3.remove(0);
            GL11.glClear(256);
            GL11.glMatrixMode(5889);
            GL11.glEnable(2903);
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0, (double)this.displayWidth, (double)this.displayHeight, 0.0, 1000.0, 3000.0);
            GL11.glMatrixMode(5888);
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
            GL11.glLineWidth(1.0f);
            GL11.glDisable(3553);
            final Tessellator var5 = Tessellator.instance;
            final short var6 = 160;
            final int var7 = this.displayWidth - var6 - 10;
            final int var8 = this.displayHeight - var6 * 2;
            GL11.glEnable(3042);
            var5.startDrawingQuads();
            var5.setColorRGBA_I(0, 200);
            var5.addVertex(var7 - var6 * 1.1f, var8 - var6 * 0.6f - 16.0f, 0.0);
            var5.addVertex(var7 - var6 * 1.1f, var8 + var6 * 2, 0.0);
            var5.addVertex(var7 + var6 * 1.1f, var8 + var6 * 2, 0.0);
            var5.addVertex(var7 + var6 * 1.1f, var8 - var6 * 0.6f - 16.0f, 0.0);
            var5.draw();
            GL11.glDisable(3042);
            double var9 = 0.0;
            for (int var10 = 0; var10 < var3.size(); ++var10) {
                final Profiler.Result var11 = var3.get(var10);
                final int var12 = MathHelper.floor_double(var11.field_76332_a / 4.0) + 1;
                var5.startDrawing(6);
                var5.setColorOpaque_I(var11.func_76329_a());
                var5.addVertex(var7, var8, 0.0);
                for (int var13 = var12; var13 >= 0; --var13) {
                    final float var14 = (float)((var9 + var11.field_76332_a * var13 / var12) * 3.141592653589793 * 2.0 / 100.0);
                    final float var15 = MathHelper.sin(var14) * var6;
                    final float var16 = MathHelper.cos(var14) * var6 * 0.5f;
                    var5.addVertex(var7 + var15, var8 - var16, 0.0);
                }
                var5.draw();
                var5.startDrawing(5);
                var5.setColorOpaque_I((var11.func_76329_a() & 0xFEFEFE) >> 1);
                for (int var13 = var12; var13 >= 0; --var13) {
                    final float var14 = (float)((var9 + var11.field_76332_a * var13 / var12) * 3.141592653589793 * 2.0 / 100.0);
                    final float var15 = MathHelper.sin(var14) * var6;
                    final float var16 = MathHelper.cos(var14) * var6 * 0.5f;
                    var5.addVertex(var7 + var15, var8 - var16, 0.0);
                    var5.addVertex(var7 + var15, var8 - var16 + 10.0f, 0.0);
                }
                var5.draw();
                var9 += var11.field_76332_a;
            }
            final DecimalFormat var17 = new DecimalFormat("##0.00");
            GL11.glEnable(3553);
            String var18 = "";
            if (!var4.field_76331_c.equals("unspecified")) {
                var18 += "[0] ";
            }
            if (var4.field_76331_c.length() == 0) {
                var18 += "ROOT ";
            }
            else {
                var18 = var18 + var4.field_76331_c + " ";
            }
            final int var12 = 16777215;
            this.fontRenderer.drawStringWithShadow(var18, var7 - var6, var8 - var6 / 2 - 16, var12);
            this.fontRenderer.drawStringWithShadow(var18 = var17.format(var4.field_76330_b) + "%", var7 + var6 - this.fontRenderer.getStringWidth(var18), var8 - var6 / 2 - 16, var12);
            for (int var19 = 0; var19 < var3.size(); ++var19) {
                final Profiler.Result var20 = var3.get(var19);
                String var21 = "";
                if (var20.field_76331_c.equals("unspecified")) {
                    var21 += "[?] ";
                }
                else {
                    var21 = var21 + "[" + (var19 + 1) + "] ";
                }
                var21 += var20.field_76331_c;
                this.fontRenderer.drawStringWithShadow(var21, var7 - var6, var8 + var6 / 2 + var19 * 8 + 20, var20.func_76329_a());
                this.fontRenderer.drawStringWithShadow(var21 = var17.format(var20.field_76332_a) + "%", var7 + var6 - 50 - this.fontRenderer.getStringWidth(var21), var8 + var6 / 2 + var19 * 8 + 20, var20.func_76329_a());
                this.fontRenderer.drawStringWithShadow(var21 = var17.format(var20.field_76330_b) + "%", var7 + var6 - this.fontRenderer.getStringWidth(var21), var8 + var6 / 2 + var19 * 8 + 20, var20.func_76329_a());
            }
        }
        this.tickTimes.add(p_71366_1_);
        if (this.tickTimes.size() == 200) {
            this.tickTimes.poll();
            double time = 0.0;
            for (final Long l : this.tickTimes) {
                time += l / 1000000.0;
            }
            time /= this.tickTimes.size();
            this.tickTime = time;
        }
        final String display = String.format("Debug time: %.2f", this.tickTime);
        final ScaledResolution resolution = new ScaledResolution(this);
        RenderUtil.drawString(Client.getInstance().getMediumFontRenderer(), display, 2.0f, (float)(resolution.getScaledHeight() * 2 - 15), -1, false);
    }
    
    public void shutdown() {
        this.running = false;
    }
    
    public void setIngameFocus() {
        if (this.displayActive && !this.inGameHasFocus) {
            this.inGameHasFocus = true;
            this.mouseHelper.grabMouseCursor();
            this.displayGuiScreen(null);
            this.leftClickCounter = 10000;
        }
    }
    
    public void setIngameNotInFocus() {
        if (this.inGameHasFocus) {
            KeyBinding.unPressAllKeys();
            this.inGameHasFocus = false;
            this.mouseHelper.ungrabMouseCursor();
        }
    }
    
    public void displayInGameMenu() {
        if (this.currentScreen == null) {
            this.displayGuiScreen(new GuiIngameMenu());
            if (this.isSingleplayer() && !this.theIntegratedServer.getPublic()) {
                this.mcSoundHandler.func_147689_b();
            }
        }
    }
    
    private void func_147115_a(final boolean p_147115_1_) {
        if (!p_147115_1_) {
            this.leftClickCounter = 0;
        }
        if (this.leftClickCounter <= 0) {
            if (p_147115_1_ && this.objectMouseOver != null && this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                final int var2 = this.objectMouseOver.blockX;
                final int var3 = this.objectMouseOver.blockY;
                final int var4 = this.objectMouseOver.blockZ;
                if (this.theWorld.getBlock(var2, var3, var4).getMaterial() != Material.air) {
                    this.playerController.onPlayerDamageBlock(var2, var3, var4, this.objectMouseOver.sideHit);
                    if (this.thePlayer.isCurrentToolAdventureModeExempt(var2, var3, var4)) {
                        this.effectRenderer.addBlockHitEffects(var2, var3, var4, this.objectMouseOver.sideHit);
                        this.thePlayer.swingItem();
                    }
                }
            }
            else {
                this.playerController.resetBlockRemoving();
            }
        }
    }
    
    private void func_147116_af() {
        if (this.leftClickCounter <= 0) {
            this.thePlayer.swingItem();
            if (this.objectMouseOver == null) {
                Minecraft.logger.error("Null returned as 'hitResult', this shouldn't happen!");
                if (this.playerController.isNotCreative()) {
                    this.leftClickCounter = 10;
                }
            }
            else {
                switch (SwitchMovingObjectType.field_152390_a[this.objectMouseOver.typeOfHit.ordinal()]) {
                    case 1: {
                        this.playerController.attackEntity(this.thePlayer, this.objectMouseOver.entityHit);
                        break;
                    }
                    case 2: {
                        final int var1 = this.objectMouseOver.blockX;
                        final int var2 = this.objectMouseOver.blockY;
                        final int var3 = this.objectMouseOver.blockZ;
                        if (this.theWorld.getBlock(var1, var2, var3).getMaterial() != Material.air) {
                            this.playerController.clickBlock(var1, var2, var3, this.objectMouseOver.sideHit);
                            break;
                        }
                        if (this.playerController.isNotCreative()) {
                            this.leftClickCounter = 10;
                            break;
                        }
                        break;
                    }
                }
            }
        }
    }
    
    private void func_147121_ag() {
        this.rightClickDelayTimer = 4;
        boolean var1 = true;
        final ItemStack var2 = this.thePlayer.inventory.getCurrentItem();
        if (this.objectMouseOver == null) {
            Minecraft.logger.warn("Null returned as 'hitResult', this shouldn't happen!");
        }
        else {
            switch (SwitchMovingObjectType.field_152390_a[this.objectMouseOver.typeOfHit.ordinal()]) {
                case 1: {
                    if (this.playerController.interactWithEntitySendPacket(this.thePlayer, this.objectMouseOver.entityHit)) {
                        var1 = false;
                        break;
                    }
                    break;
                }
                case 2: {
                    final int var3 = this.objectMouseOver.blockX;
                    final int var4 = this.objectMouseOver.blockY;
                    final int var5 = this.objectMouseOver.blockZ;
                    if (this.theWorld.getBlock(var3, var4, var5).getMaterial() == Material.air) {
                        break;
                    }
                    final int var6 = (var2 != null) ? var2.stackSize : 0;
                    if (this.playerController.onPlayerRightClick(this.thePlayer, this.theWorld, var2, var3, var4, var5, this.objectMouseOver.sideHit, this.objectMouseOver.hitVec)) {
                        var1 = false;
                        this.thePlayer.swingItem();
                    }
                    if (var2 == null) {
                        return;
                    }
                    if (var2.stackSize == 0) {
                        this.thePlayer.inventory.mainInventory[this.thePlayer.inventory.currentItem] = null;
                        break;
                    }
                    if (var2.stackSize != var6 || this.playerController.isInCreativeMode()) {
                        this.entityRenderer.itemRenderer.resetEquippedProgress();
                        break;
                    }
                    break;
                }
            }
        }
        if (var1) {
            final ItemStack var7 = this.thePlayer.inventory.getCurrentItem();
            if (var7 != null && this.playerController.sendUseItem(this.thePlayer, this.theWorld, var7)) {
                this.entityRenderer.itemRenderer.resetEquippedProgress2();
            }
        }
    }
    
    public void toggleFullscreen() {
        try {
            this.fullscreen = !this.fullscreen;
            if (this.fullscreen) {
                this.updateDisplayMode();
                this.displayWidth = Display.getDisplayMode().getWidth();
                this.displayHeight = Display.getDisplayMode().getHeight();
                if (this.displayWidth <= 0) {
                    this.displayWidth = 1;
                }
                if (this.displayHeight <= 0) {
                    this.displayHeight = 1;
                }
            }
            else {
                Display.setDisplayMode(new DisplayMode(this.tempDisplayWidth, this.tempDisplayHeight));
                this.displayWidth = this.tempDisplayWidth;
                this.displayHeight = this.tempDisplayHeight;
                if (this.displayWidth <= 0) {
                    this.displayWidth = 1;
                }
                if (this.displayHeight <= 0) {
                    this.displayHeight = 1;
                }
            }
            if (this.currentScreen != null) {
                this.resize(this.displayWidth, this.displayHeight);
            }
            else {
                this.updateFramebufferSize();
            }
            Display.setFullscreen(this.fullscreen);
            Display.setVSyncEnabled(this.gameSettings.enableVsync);
            this.func_147120_f();
        }
        catch (Exception var2) {
            Minecraft.logger.error("Couldn't toggle fullscreen", (Throwable)var2);
        }
    }
    
    private void resize(final int p_71370_1_, final int p_71370_2_) {
        this.displayWidth = ((p_71370_1_ <= 0) ? 1 : p_71370_1_);
        this.displayHeight = ((p_71370_2_ <= 0) ? 1 : p_71370_2_);
        if (this.currentScreen != null) {
            final ScaledResolution var3 = new ScaledResolution(this, p_71370_1_, p_71370_2_);
            final int var4 = var3.getScaledWidth();
            final int var5 = var3.getScaledHeight();
            this.currentScreen.setWorldAndResolution(this, var4, var5);
        }
        this.loadingScreen = new LoadingScreenRenderer(this);
        this.updateFramebufferSize();
    }
    
    private void updateFramebufferSize() {
        this.mcFramebuffer.createBindFramebuffer(this.displayWidth, this.displayHeight);
        if (this.entityRenderer != null) {
            this.entityRenderer.updateShaderGroupSize(this.displayWidth, this.displayHeight);
        }
    }
    
    public void runTick() {
        this.mcProfiler.startSection("scheduledExecutables");
        final Queue var1 = this.field_152351_aB;
        synchronized (this.field_152351_aB) {
            while (!this.field_152351_aB.isEmpty()) {
                this.field_152351_aB.poll().run();
            }
        }
        this.mcProfiler.endSection();
        if (this.rightClickDelayTimer > 0) {
            --this.rightClickDelayTimer;
        }
        this.mcProfiler.startSection("gui");
        if (!this.isGamePaused) {
            this.ingameGUI.updateTick();
        }
        this.mcProfiler.endStartSection("pick");
        this.entityRenderer.getMouseOver(1.0f);
        this.mcProfiler.endStartSection("gameMode");
        if (!this.isGamePaused && this.theWorld != null) {
            this.playerController.updateController();
        }
        this.mcProfiler.endStartSection("textures");
        if (!this.isGamePaused) {
            this.renderEngine.tick();
        }
        if (this.currentScreen == null && this.thePlayer != null) {
            if (this.thePlayer.getHealth() <= 0.0f) {
                this.displayGuiScreen(null);
            }
            else if (this.thePlayer.isPlayerSleeping() && this.theWorld != null) {
                this.displayGuiScreen(new GuiSleepMP());
            }
        }
        else if (this.currentScreen != null && this.currentScreen instanceof GuiSleepMP && !this.thePlayer.isPlayerSleeping()) {
            this.displayGuiScreen(null);
        }
        if (this.currentScreen != null) {
            this.leftClickCounter = 10000;
        }
        if (this.currentScreen != null) {
            try {
                this.currentScreen.handleInput();
            }
            catch (Throwable var3) {
                final CrashReport var2 = CrashReport.makeCrashReport(var3, "Updating screen events");
                final CrashReportCategory var4 = var2.makeCategory("Affected screen");
                var4.addCrashSectionCallable("Screen name", new Callable() {
                    private static final String __OBFID = "CL_00000640";
                    
                    @Override
                    public String call() {
                        return Minecraft.this.currentScreen.getClass().getCanonicalName();
                    }
                });
                throw new ReportedException(var2);
            }
            if (this.currentScreen != null) {
                try {
                    this.currentScreen.updateScreen();
                    this.initialised = true;
                }
                catch (Throwable var5) {
                    final CrashReport var2 = CrashReport.makeCrashReport(var5, "Ticking screen");
                    final CrashReportCategory var4 = var2.makeCategory("Affected screen");
                    var4.addCrashSectionCallable("Screen name", new Callable() {
                        private static final String __OBFID = "CL_00000642";
                        
                        @Override
                        public String call() {
                            return Minecraft.this.currentScreen.getClass().getCanonicalName();
                        }
                    });
                    throw new ReportedException(var2);
                }
            }
        }
        if (this.currentScreen == null || this.currentScreen.field_146291_p) {
            this.mcProfiler.endStartSection("mouse");
            while (Mouse.next()) {
                final int var6 = Mouse.getEventButton();
                KeyBinding.setKeyBindState(var6 - 100, Mouse.getEventButtonState());
                if (Mouse.getEventButtonState()) {
                    KeyBinding.onTick(var6 - 100);
                }
                final long var7 = getSystemTime() - this.systemTime;
                if (var7 <= 200L) {
                    int var8 = Mouse.getEventDWheel();
                    if (var8 != 0) {
                        this.thePlayer.inventory.changeCurrentItem(var8);
                        if (this.gameSettings.noclip) {
                            if (var8 > 0) {
                                var8 = 1;
                            }
                            if (var8 < 0) {
                                var8 = -1;
                            }
                            final GameSettings gameSettings = this.gameSettings;
                            gameSettings.noclipRate += var8 * 0.25f;
                        }
                    }
                    if (this.currentScreen == null) {
                        if (this.inGameHasFocus || !Mouse.getEventButtonState()) {
                            continue;
                        }
                        this.setIngameFocus();
                    }
                    else {
                        if (this.currentScreen == null) {
                            continue;
                        }
                        this.currentScreen.handleMouseInput();
                    }
                }
            }
            if (this.leftClickCounter > 0) {
                --this.leftClickCounter;
            }
            this.mcProfiler.endStartSection("keyboard");
            while (Keyboard.next()) {
                KeyBinding.setKeyBindState(Keyboard.getEventKey(), Keyboard.getEventKeyState());
                if (Keyboard.getEventKeyState()) {
                    KeyBinding.onTick(Keyboard.getEventKey());
                }
                if (this.field_83002_am > 0L) {
                    if (getSystemTime() - this.field_83002_am >= 6000L) {
                        throw new ReportedException(new CrashReport("Manually triggered debug crash", new Throwable()));
                    }
                    if (!Keyboard.isKeyDown(46) || !Keyboard.isKeyDown(61)) {
                        this.field_83002_am = -1L;
                    }
                }
                else if (Keyboard.isKeyDown(46) && Keyboard.isKeyDown(61)) {
                    this.field_83002_am = getSystemTime();
                }
                this.func_152348_aa();
                if (Keyboard.getEventKeyState()) {
                    if (Keyboard.getEventKey() == 62 && this.entityRenderer != null) {
                        this.entityRenderer.deactivateShader();
                    }
                    if (this.currentScreen != null) {
                        this.currentScreen.handleKeyboardInput();
                    }
                    else {
                        if (Keyboard.getEventKey() == 1) {
                            this.displayInGameMenu();
                        }
                        if (Keyboard.getEventKey() == 33 && Keyboard.isKeyDown(61)) {
                            final boolean var9 = Keyboard.isKeyDown(42) | Keyboard.isKeyDown(54);
                            this.gameSettings.setOptionValue(GameSettings.Options.RENDER_DISTANCE, var9 ? -1 : 1);
                        }
                        if (Keyboard.getEventKey() == 35 && Keyboard.isKeyDown(61)) {
                            this.gameSettings.advancedItemTooltips = !this.gameSettings.advancedItemTooltips;
                            this.gameSettings.saveOptions();
                        }
                        if (Keyboard.getEventKey() == 48 && Keyboard.isKeyDown(61)) {
                            RenderManager.field_85095_o = !RenderManager.field_85095_o;
                        }
                        if (Keyboard.getEventKey() == 25 && Keyboard.isKeyDown(61)) {
                            this.gameSettings.pauseOnLostFocus = !this.gameSettings.pauseOnLostFocus;
                            this.gameSettings.saveOptions();
                        }
                        if (Keyboard.getEventKey() == 59 || Keyboard.getEventKey() == 64) {
                            this.gameSettings.hideGUI = !this.gameSettings.hideGUI;
                            this.gameSettings.cinematicMode = (Keyboard.getEventKey() == 64 && this.gameSettings.hideGUI);
                        }
                        if (Keyboard.getEventKey() == 61) {
                            this.gameSettings.showDebugInfo = !this.gameSettings.showDebugInfo;
                            this.gameSettings.showDebugProfilerChart = GuiScreen.isShiftKeyDown();
                        }
                        if (this.gameSettings.keyBindTogglePerspective.isPressed()) {
                            final GameSettings gameSettings2 = this.gameSettings;
                            ++gameSettings2.thirdPersonView;
                            if (this.gameSettings.thirdPersonView > 2) {
                                this.gameSettings.thirdPersonView = 0;
                            }
                        }
                        if (this.gameSettings.keyBindSmoothCamera.isPressed()) {
                            this.gameSettings.smoothCamera = !this.gameSettings.smoothCamera;
                        }
                        if (this.gameSettings.keyBindOpenUi.isPressed()) {
                            this.displayGuiScreen(new ModScreen());
                        }
                    }
                    if (!this.gameSettings.showDebugInfo || !this.gameSettings.showDebugProfilerChart) {
                        continue;
                    }
                    if (Keyboard.getEventKey() == 11) {
                        this.updateDebugProfilerName(0);
                    }
                    for (int var6 = 0; var6 < 9; ++var6) {
                        if (Keyboard.getEventKey() == 2 + var6) {
                            this.updateDebugProfilerName(var6 + 1);
                        }
                    }
                }
            }
            for (int var6 = 0; var6 < 9; ++var6) {
                if (this.gameSettings.keyBindsHotbar[var6].isPressed()) {
                    this.thePlayer.inventory.currentItem = var6;
                }
            }
            final boolean var9 = this.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN;
            while (this.gameSettings.keyBindInventory.isPressed()) {
                if (this.playerController.func_110738_j()) {
                    this.thePlayer.func_110322_i();
                }
                else {
                    this.getNetHandler().addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                    this.displayGuiScreen(new GuiInventory(this.thePlayer));
                }
            }
            while (this.gameSettings.keyBindDrop.isPressed()) {
                this.thePlayer.dropOneItem(GuiScreen.isCtrlKeyDown());
            }
            final boolean alt = this.gameSettings.keyBindPerspective.getIsKeyPressed();
            if (this.entityRenderer != null && (!this.entityRenderer.altPerspectiveToggled || alt)) {
                final boolean before = this.entityRenderer.altPerspective;
                this.entityRenderer.altPerspectiveToggled = false;
                if (before != (this.entityRenderer.altPerspective = alt)) {
                    this.entityRenderer.altYaw = this.thePlayer.rotationYaw;
                    this.entityRenderer.altPitch = this.thePlayer.rotationPitch;
                    if (this.entityRenderer.altPerspective) {
                        this.gameSettings.thirdPersonView = 1;
                    }
                    else {
                        this.gameSettings.thirdPersonView = 0;
                    }
                }
            }
            while (this.gameSettings.keyBindPerspectiveToggle.isPressed()) {
                if (this.entityRenderer != null) {
                    this.entityRenderer.altPerspectiveToggled = true;
                    this.entityRenderer.altPerspective = !this.entityRenderer.altPerspective;
                    this.entityRenderer.altPitch = this.thePlayer.rotationPitch;
                    this.entityRenderer.altYaw = this.thePlayer.rotationYaw;
                    if (this.entityRenderer.altPerspective) {
                        this.gameSettings.thirdPersonView = 1;
                    }
                    else {
                        this.gameSettings.thirdPersonView = 0;
                    }
                }
            }
            while (this.gameSettings.keyBindToggleChat.isPressed()) {
                final EntityPlayer.EnumChatVisibility visibility = this.gameSettings.chatVisibility;
                if (visibility == EntityPlayer.EnumChatVisibility.HIDDEN) {
                    if (this.gameSettings.lastChatVisibility == null) {
                        this.gameSettings.chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
                    }
                    else {
                        this.gameSettings.chatVisibility = this.gameSettings.lastChatVisibility;
                    }
                }
                else {
                    this.gameSettings.chatVisibility = EntityPlayer.EnumChatVisibility.HIDDEN;
                }
                this.gameSettings.lastChatVisibility = visibility;
            }
            while (this.gameSettings.keyBindChat.isPressed() && var9) {
                this.displayGuiScreen(new GuiChat());
            }
            if (this.currentScreen == null && this.gameSettings.keyBindCommand.isPressed() && var9) {
                this.displayGuiScreen(new GuiChat("/"));
            }
            if (this.thePlayer.isUsingItem()) {
                if (!this.gameSettings.keyBindUseItem.getIsKeyPressed()) {
                    this.playerController.onStoppedUsingItem(this.thePlayer);
                }
                while (this.gameSettings.keyBindAttack.isPressed()) {}
                while (this.gameSettings.keyBindUseItem.isPressed()) {}
                while (this.gameSettings.keyBindPickBlock.isPressed()) {}
            }
            else {
                while (this.gameSettings.keyBindAttack.isPressed()) {
                    this.func_147116_af();
                }
                while (this.gameSettings.keyBindUseItem.isPressed()) {
                    this.func_147121_ag();
                }
                while (this.gameSettings.keyBindPickBlock.isPressed()) {
                    this.func_147112_ai();
                }
            }
            if (this.gameSettings.keyBindUseItem.getIsKeyPressed() && this.rightClickDelayTimer == 0 && !this.thePlayer.isUsingItem()) {
                this.func_147121_ag();
            }
            this.func_147115_a(this.currentScreen == null && this.gameSettings.keyBindAttack.getIsKeyPressed() && this.inGameHasFocus);
        }
        if (this.theWorld != null) {
            if (this.thePlayer != null) {
                ++this.joinPlayerCounter;
                if (this.joinPlayerCounter == 30) {
                    this.joinPlayerCounter = 0;
                    this.theWorld.joinEntityInSurroundings(this.thePlayer);
                }
            }
            this.mcProfiler.endStartSection("gameRenderer");
            if (!this.isGamePaused) {
                this.entityRenderer.updateRenderer();
            }
            this.mcProfiler.endStartSection("levelRenderer");
            if (!this.isGamePaused) {
                this.renderGlobal.updateClouds();
            }
            this.mcProfiler.endStartSection("level");
            if (!this.isGamePaused) {
                if (this.theWorld.lastLightningBolt > 0) {
                    final WorldClient theWorld = this.theWorld;
                    --theWorld.lastLightningBolt;
                }
                this.theWorld.updateEntities();
            }
        }
        if (!this.isGamePaused) {
            this.mcMusicTicker.update();
            this.mcSoundHandler.update();
        }
        if (this.theWorld != null) {
            if (!this.isGamePaused) {
                this.theWorld.setAllowedSpawnTypes(this.theWorld.difficultySetting != EnumDifficulty.PEACEFUL, true);
                try {
                    this.theWorld.tick();
                }
                catch (Throwable var10) {
                    final CrashReport var2 = CrashReport.makeCrashReport(var10, "Exception in world tick");
                    if (this.theWorld == null) {
                        final CrashReportCategory var4 = var2.makeCategory("Affected level");
                        var4.addCrashSection("Problem", "Level is null!");
                    }
                    else {
                        this.theWorld.addWorldInfoToCrashReport(var2);
                    }
                    throw new ReportedException(var2);
                }
            }
            this.mcProfiler.endStartSection("animateTick");
            if (!this.isGamePaused && this.theWorld != null) {
                this.theWorld.doVoidFogParticles(MathHelper.floor_double(this.thePlayer.posX), MathHelper.floor_double(this.thePlayer.posY), MathHelper.floor_double(this.thePlayer.posZ));
            }
            this.mcProfiler.endStartSection("particles");
            if (!this.isGamePaused) {
                this.effectRenderer.updateEffects();
            }
        }
        else if (this.myNetworkManager != null) {
            this.mcProfiler.endStartSection("pendingConnection");
            this.myNetworkManager.processReceivedPackets();
        }
        this.mcProfiler.endSection();
        this.systemTime = getSystemTime();
    }
    
    public void launchIntegratedServer(final String p_71371_1_, final String p_71371_2_, WorldSettings p_71371_3_) {
        this.loadWorld(null);
        System.gc();
        final ISaveHandler var4 = this.saveLoader.getSaveLoader(p_71371_1_, false);
        WorldInfo var5 = var4.loadWorldInfo();
        if (var5 == null && p_71371_3_ != null) {
            var5 = new WorldInfo(p_71371_3_, p_71371_1_);
            var4.saveWorldInfo(var5);
        }
        if (p_71371_3_ == null) {
            p_71371_3_ = new WorldSettings(var5);
        }
        try {
            (this.theIntegratedServer = new IntegratedServer(this, p_71371_1_, p_71371_2_, p_71371_3_)).startServerThread();
            this.integratedServerIsRunning = true;
        }
        catch (Throwable var7) {
            final CrashReport var6 = CrashReport.makeCrashReport(var7, "Starting integrated server");
            final CrashReportCategory var8 = var6.makeCategory("Starting integrated server");
            var8.addCrashSection("Level ID", p_71371_1_);
            var8.addCrashSection("Level Name", p_71371_2_);
            throw new ReportedException(var6);
        }
        this.loadingScreen.displayProgressMessage(I18n.format("menu.loadingLevel", new Object[0]));
        while (!this.theIntegratedServer.serverIsInRunLoop()) {
            final String var9 = this.theIntegratedServer.getUserMessage();
            if (var9 != null) {
                this.loadingScreen.resetProgresAndWorkingMessage(I18n.format(var9, new Object[0]));
            }
            else {
                this.loadingScreen.resetProgresAndWorkingMessage("");
            }
            try {
                Thread.sleep(200L);
            }
            catch (InterruptedException ex) {}
        }
        this.displayGuiScreen(null);
        final SocketAddress var10 = this.theIntegratedServer.func_147137_ag().addLocalEndpoint();
        final NetworkManager var11 = NetworkManager.provideLocalClient(var10);
        var11.setNetHandler(new NetHandlerLoginClient(var11, this, null));
        var11.scheduleOutboundPacket(new C00Handshake(5, var10.toString(), 0, EnumConnectionState.LOGIN), new GenericFutureListener[0]);
        var11.scheduleOutboundPacket(new C00PacketLoginStart(this.getSession().func_148256_e()), new GenericFutureListener[0]);
        this.myNetworkManager = var11;
    }
    
    public void loadWorld(final WorldClient p_71403_1_) {
        this.loadWorld(p_71403_1_, "");
    }
    
    public void loadWorld(final WorldClient p_71353_1_, final String p_71353_2_) {
        if (p_71353_1_ == null) {
            final NetHandlerPlayClient var3 = this.getNetHandler();
            if (var3 != null) {
                var3.cleanup();
            }
            if (this.theIntegratedServer != null) {
                this.theIntegratedServer.initiateShutdown();
            }
            this.theIntegratedServer = null;
            this.guiAchievement.func_146257_b();
            this.entityRenderer.getMapItemRenderer().func_148249_a();
        }
        this.renderViewEntity = null;
        this.myNetworkManager = null;
        if (this.loadingScreen != null) {
            this.loadingScreen.resetProgressAndMessage(p_71353_2_);
            this.loadingScreen.resetProgresAndWorkingMessage("");
        }
        if (p_71353_1_ == null && this.theWorld != null) {
            if (this.mcResourcePackRepository.func_148530_e() != null) {
                this.scheduleResourcesRefresh();
            }
            this.mcResourcePackRepository.func_148529_f();
            this.setServerData(null);
            this.integratedServerIsRunning = false;
        }
        this.mcSoundHandler.func_147690_c();
        if ((this.theWorld = p_71353_1_) != null) {
            if (this.renderGlobal != null) {
                this.renderGlobal.setWorldAndLoadRenderers(p_71353_1_);
            }
            if (this.effectRenderer != null) {
                this.effectRenderer.clearEffects(p_71353_1_);
            }
            if (this.thePlayer == null) {
                this.thePlayer = this.playerController.func_147493_a(p_71353_1_, new StatFileWriter());
                this.playerController.flipPlayer(this.thePlayer);
            }
            this.thePlayer.preparePlayerToSpawn();
            p_71353_1_.spawnEntityInWorld(this.thePlayer);
            this.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
            this.playerController.setPlayerCapabilities(this.thePlayer);
            this.renderViewEntity = this.thePlayer;
        }
        else {
            this.saveLoader.flushCache();
            this.thePlayer = null;
        }
        System.gc();
        this.systemTime = 0L;
    }
    
    public String debugInfoRenders() {
        return this.renderGlobal.getDebugInfoRenders();
    }
    
    public String getEntityDebug() {
        return this.renderGlobal.getDebugInfoEntities();
    }
    
    public String getWorldProviderName() {
        return this.theWorld.getProviderName();
    }
    
    public String debugInfoEntities() {
        return "P: " + this.effectRenderer.getStatistics() + ". T: " + this.theWorld.getDebugLoadedEntities();
    }
    
    public void setDimensionAndSpawnPlayer(final int p_71354_1_) {
        this.theWorld.setSpawnLocation();
        this.theWorld.removeAllEntities();
        int var2 = 0;
        String var3 = null;
        if (this.thePlayer != null) {
            var2 = this.thePlayer.getEntityId();
            this.theWorld.removeEntity(this.thePlayer);
            var3 = this.thePlayer.func_142021_k();
        }
        this.renderViewEntity = null;
        this.thePlayer = this.playerController.func_147493_a(this.theWorld, (this.thePlayer == null) ? new StatFileWriter() : this.thePlayer.func_146107_m());
        this.thePlayer.dimension = p_71354_1_;
        this.renderViewEntity = this.thePlayer;
        this.thePlayer.preparePlayerToSpawn();
        this.thePlayer.func_142020_c(var3);
        this.theWorld.spawnEntityInWorld(this.thePlayer);
        this.playerController.flipPlayer(this.thePlayer);
        this.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
        this.thePlayer.setEntityId(var2);
        this.playerController.setPlayerCapabilities(this.thePlayer);
        if (this.currentScreen instanceof GuiGameOver) {
            this.displayGuiScreen(null);
        }
    }
    
    public final boolean isDemo() {
        return this.isDemo;
    }
    
    public NetHandlerPlayClient getNetHandler() {
        return (this.thePlayer != null) ? this.thePlayer.sendQueue : null;
    }
    
    public static boolean isGuiEnabled() {
        return Minecraft.theMinecraft == null || !Minecraft.theMinecraft.gameSettings.hideGUI;
    }
    
    public static boolean isFancyGraphicsEnabled() {
        return Minecraft.theMinecraft != null && Minecraft.theMinecraft.gameSettings.fancyGraphics;
    }
    
    public static boolean isAmbientOcclusionEnabled() {
        return Minecraft.theMinecraft != null && Minecraft.theMinecraft.gameSettings.ambientOcclusion != 0;
    }
    
    private void func_147112_ai() {
        if (this.objectMouseOver != null) {
            final boolean var1 = this.thePlayer.capabilities.isCreativeMode;
            int var2 = 0;
            boolean var3 = false;
            Item var8;
            if (this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                final int var4 = this.objectMouseOver.blockX;
                final int var5 = this.objectMouseOver.blockY;
                final int var6 = this.objectMouseOver.blockZ;
                final Block var7 = this.theWorld.getBlock(var4, var5, var6);
                if (var7.getMaterial() == Material.air) {
                    return;
                }
                var8 = var7.getItem(this.theWorld, var4, var5, var6);
                if (var8 == null) {
                    return;
                }
                var3 = var8.getHasSubtypes();
                final Block var9 = (var8 instanceof ItemBlock && !var7.isFlowerPot()) ? Block.getBlockFromItem(var8) : var7;
                var2 = var9.getDamageValue(this.theWorld, var4, var5, var6);
            }
            else {
                if (this.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY || this.objectMouseOver.entityHit == null || !var1) {
                    return;
                }
                if (this.objectMouseOver.entityHit instanceof EntityPainting) {
                    var8 = Items.painting;
                }
                else if (this.objectMouseOver.entityHit instanceof EntityLeashKnot) {
                    var8 = Items.lead;
                }
                else if (this.objectMouseOver.entityHit instanceof EntityItemFrame) {
                    final EntityItemFrame var10 = (EntityItemFrame)this.objectMouseOver.entityHit;
                    final ItemStack var11 = var10.getDisplayedItem();
                    if (var11 == null) {
                        var8 = Items.item_frame;
                    }
                    else {
                        var8 = var11.getItem();
                        var2 = var11.getItemDamage();
                        var3 = true;
                    }
                }
                else if (this.objectMouseOver.entityHit instanceof EntityMinecart) {
                    final EntityMinecart var12 = (EntityMinecart)this.objectMouseOver.entityHit;
                    if (var12.getMinecartType() == 2) {
                        var8 = Items.furnace_minecart;
                    }
                    else if (var12.getMinecartType() == 1) {
                        var8 = Items.chest_minecart;
                    }
                    else if (var12.getMinecartType() == 3) {
                        var8 = Items.tnt_minecart;
                    }
                    else if (var12.getMinecartType() == 5) {
                        var8 = Items.hopper_minecart;
                    }
                    else if (var12.getMinecartType() == 6) {
                        var8 = Items.command_block_minecart;
                    }
                    else {
                        var8 = Items.minecart;
                    }
                }
                else if (this.objectMouseOver.entityHit instanceof EntityBoat) {
                    var8 = Items.boat;
                }
                else {
                    var8 = Items.spawn_egg;
                    var2 = EntityList.getEntityID(this.objectMouseOver.entityHit);
                    var3 = true;
                    if (var2 <= 0 || !EntityList.entityEggs.containsKey(var2)) {
                        return;
                    }
                }
            }
            this.thePlayer.inventory.func_146030_a(var8, var2, var3, var1);
            if (var1) {
                final int var4 = this.thePlayer.inventoryContainer.inventorySlots.size() - 9 + this.thePlayer.inventory.currentItem;
                this.playerController.sendSlotPacket(this.thePlayer.inventory.getStackInSlot(this.thePlayer.inventory.currentItem), var4);
            }
        }
    }
    
    public CrashReport addGraphicsAndWorldToCrashReport(final CrashReport p_71396_1_) {
        p_71396_1_.getCategory().addCrashSectionCallable("Launched Version", new Callable() {
            private static final String __OBFID = "CL_00000643";
            
            @Override
            public String call() {
                return Minecraft.this.launchedVersion;
            }
        });
        p_71396_1_.getCategory().addCrashSectionCallable("LWJGL", new Callable() {
            private static final String __OBFID = "CL_00000644";
            
            @Override
            public String call() {
                return Sys.getVersion();
            }
        });
        p_71396_1_.getCategory().addCrashSectionCallable("OpenGL", new Callable() {
            private static final String __OBFID = "CL_00000645";
            
            @Override
            public String call() {
                return GL11.glGetString(7937) + " GL version " + GL11.glGetString(7938) + ", " + GL11.glGetString(7936);
            }
        });
        p_71396_1_.getCategory().addCrashSectionCallable("GL Caps", new Callable() {
            private static final String __OBFID = "CL_00000646";
            
            @Override
            public String call() {
                return OpenGlHelper.func_153172_c();
            }
        });
        p_71396_1_.getCategory().addCrashSectionCallable("Is Modded", new Callable() {
            private static final String __OBFID = "CL_00000647";
            
            @Override
            public String call() {
                final String var1 = ClientBrandRetriever.getClientModName();
                return var1.equals("vanilla") ? ((Minecraft.class.getSigners() == null) ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and client brand is untouched.") : ("Definitely; Client brand changed to '" + var1 + "'");
            }
        });
        p_71396_1_.getCategory().addCrashSectionCallable("Type", new Callable() {
            private static final String __OBFID = "CL_00000633";
            
            @Override
            public String call() {
                return "Client (map_client.txt)";
            }
        });
        p_71396_1_.getCategory().addCrashSectionCallable("Resource Packs", new Callable() {
            private static final String __OBFID = "CL_00000634";
            
            @Override
            public String call() {
                return Minecraft.this.gameSettings.resourcePacks.toString();
            }
        });
        p_71396_1_.getCategory().addCrashSectionCallable("Current Language", new Callable() {
            private static final String __OBFID = "CL_00000635";
            
            @Override
            public String call() {
                return Minecraft.this.mcLanguageManager.getCurrentLanguage().toString();
            }
        });
        p_71396_1_.getCategory().addCrashSectionCallable("Profiler Position", new Callable() {
            private static final String __OBFID = "CL_00000636";
            
            @Override
            public String call() {
                return Minecraft.this.mcProfiler.profilingEnabled ? Minecraft.this.mcProfiler.getNameOfLastSection() : "N/A (disabled)";
            }
        });
        p_71396_1_.getCategory().addCrashSectionCallable("Vec3 Pool Size", new Callable() {
            private static final String __OBFID = "CL_00000637";
            
            @Override
            public String call() {
                final byte var1 = 0;
                final int var2 = 56 * var1;
                final int var3 = var2 / 1024 / 1024;
                final byte var4 = 0;
                final int var5 = 56 * var4;
                final int var6 = var5 / 1024 / 1024;
                return var1 + " (" + var2 + " bytes; " + var3 + " MB) allocated, " + var4 + " (" + var5 + " bytes; " + var6 + " MB) used";
            }
        });
        p_71396_1_.getCategory().addCrashSectionCallable("Anisotropic Filtering", new Callable() {
            private static final String __OBFID = "CL_00001853";
            
            public String func_152388_a() {
                return (Minecraft.this.gameSettings.anisotropicFiltering == 1) ? "Off (1)" : ("On (" + Minecraft.this.gameSettings.anisotropicFiltering + ")");
            }
            
            @Override
            public Object call() {
                return this.func_152388_a();
            }
        });
        if (this.theWorld != null) {
            this.theWorld.addWorldInfoToCrashReport(p_71396_1_);
        }
        return p_71396_1_;
    }
    
    public static Minecraft getMinecraft() {
        return Minecraft.theMinecraft;
    }
    
    public void scheduleResourcesRefresh() {
        this.refreshTexturePacksScheduled = true;
    }
    
    @Override
    public void addServerStatsToSnooper(final PlayerUsageSnooper p_70000_1_) {
        p_70000_1_.func_152768_a("fps", Minecraft.debugFPS);
        p_70000_1_.func_152768_a("vsync_enabled", this.gameSettings.enableVsync);
        p_70000_1_.func_152768_a("display_frequency", Display.getDisplayMode().getFrequency());
        p_70000_1_.func_152768_a("display_type", this.fullscreen ? "fullscreen" : "windowed");
        p_70000_1_.func_152768_a("run_time", (MinecraftServer.getSystemTimeMillis() - p_70000_1_.getMinecraftStartTimeMillis()) / 60L * 1000L);
        p_70000_1_.func_152768_a("resource_packs", this.mcResourcePackRepository.getRepositoryEntries().size());
        int var2 = 0;
        for (final ResourcePackRepository.Entry var4 : this.mcResourcePackRepository.getRepositoryEntries()) {
            p_70000_1_.func_152768_a("resource_pack[" + var2++ + "]", var4.getResourcePackName());
        }
        if (this.theIntegratedServer != null && this.theIntegratedServer.getPlayerUsageSnooper() != null) {
            p_70000_1_.func_152768_a("snooper_partner", this.theIntegratedServer.getPlayerUsageSnooper().getUniqueID());
        }
    }
    
    @Override
    public void addServerTypeToSnooper(final PlayerUsageSnooper p_70001_1_) {
        p_70001_1_.func_152767_b("opengl_version", GL11.glGetString(7938));
        p_70001_1_.func_152767_b("opengl_vendor", GL11.glGetString(7936));
        p_70001_1_.func_152767_b("client_brand", ClientBrandRetriever.getClientModName());
        p_70001_1_.func_152767_b("launched_version", this.launchedVersion);
        final ContextCapabilities var2 = GLContext.getCapabilities();
        p_70001_1_.func_152767_b("gl_caps[ARB_arrays_of_arrays]", var2.GL_ARB_arrays_of_arrays);
        p_70001_1_.func_152767_b("gl_caps[ARB_base_instance]", var2.GL_ARB_base_instance);
        p_70001_1_.func_152767_b("gl_caps[ARB_blend_func_extended]", var2.GL_ARB_blend_func_extended);
        p_70001_1_.func_152767_b("gl_caps[ARB_clear_buffer_object]", var2.GL_ARB_clear_buffer_object);
        p_70001_1_.func_152767_b("gl_caps[ARB_color_buffer_float]", var2.GL_ARB_color_buffer_float);
        p_70001_1_.func_152767_b("gl_caps[ARB_compatibility]", var2.GL_ARB_compatibility);
        p_70001_1_.func_152767_b("gl_caps[ARB_compressed_texture_pixel_storage]", var2.GL_ARB_compressed_texture_pixel_storage);
        p_70001_1_.func_152767_b("gl_caps[ARB_compute_shader]", var2.GL_ARB_compute_shader);
        p_70001_1_.func_152767_b("gl_caps[ARB_copy_buffer]", var2.GL_ARB_copy_buffer);
        p_70001_1_.func_152767_b("gl_caps[ARB_copy_image]", var2.GL_ARB_copy_image);
        p_70001_1_.func_152767_b("gl_caps[ARB_depth_buffer_float]", var2.GL_ARB_depth_buffer_float);
        p_70001_1_.func_152767_b("gl_caps[ARB_compute_shader]", var2.GL_ARB_compute_shader);
        p_70001_1_.func_152767_b("gl_caps[ARB_copy_buffer]", var2.GL_ARB_copy_buffer);
        p_70001_1_.func_152767_b("gl_caps[ARB_copy_image]", var2.GL_ARB_copy_image);
        p_70001_1_.func_152767_b("gl_caps[ARB_depth_buffer_float]", var2.GL_ARB_depth_buffer_float);
        p_70001_1_.func_152767_b("gl_caps[ARB_depth_clamp]", var2.GL_ARB_depth_clamp);
        p_70001_1_.func_152767_b("gl_caps[ARB_depth_texture]", var2.GL_ARB_depth_texture);
        p_70001_1_.func_152767_b("gl_caps[ARB_draw_buffers]", var2.GL_ARB_draw_buffers);
        p_70001_1_.func_152767_b("gl_caps[ARB_draw_buffers_blend]", var2.GL_ARB_draw_buffers_blend);
        p_70001_1_.func_152767_b("gl_caps[ARB_draw_elements_base_vertex]", var2.GL_ARB_draw_elements_base_vertex);
        p_70001_1_.func_152767_b("gl_caps[ARB_draw_indirect]", var2.GL_ARB_draw_indirect);
        p_70001_1_.func_152767_b("gl_caps[ARB_draw_instanced]", var2.GL_ARB_draw_instanced);
        p_70001_1_.func_152767_b("gl_caps[ARB_explicit_attrib_location]", var2.GL_ARB_explicit_attrib_location);
        p_70001_1_.func_152767_b("gl_caps[ARB_explicit_uniform_location]", var2.GL_ARB_explicit_uniform_location);
        p_70001_1_.func_152767_b("gl_caps[ARB_fragment_layer_viewport]", var2.GL_ARB_fragment_layer_viewport);
        p_70001_1_.func_152767_b("gl_caps[ARB_fragment_program]", var2.GL_ARB_fragment_program);
        p_70001_1_.func_152767_b("gl_caps[ARB_fragment_shader]", var2.GL_ARB_fragment_shader);
        p_70001_1_.func_152767_b("gl_caps[ARB_fragment_program_shadow]", var2.GL_ARB_fragment_program_shadow);
        p_70001_1_.func_152767_b("gl_caps[ARB_framebuffer_object]", var2.GL_ARB_framebuffer_object);
        p_70001_1_.func_152767_b("gl_caps[ARB_framebuffer_sRGB]", var2.GL_ARB_framebuffer_sRGB);
        p_70001_1_.func_152767_b("gl_caps[ARB_geometry_shader4]", var2.GL_ARB_geometry_shader4);
        p_70001_1_.func_152767_b("gl_caps[ARB_gpu_shader5]", var2.GL_ARB_gpu_shader5);
        p_70001_1_.func_152767_b("gl_caps[ARB_half_float_pixel]", var2.GL_ARB_half_float_pixel);
        p_70001_1_.func_152767_b("gl_caps[ARB_half_float_vertex]", var2.GL_ARB_half_float_vertex);
        p_70001_1_.func_152767_b("gl_caps[ARB_instanced_arrays]", var2.GL_ARB_instanced_arrays);
        p_70001_1_.func_152767_b("gl_caps[ARB_map_buffer_alignment]", var2.GL_ARB_map_buffer_alignment);
        p_70001_1_.func_152767_b("gl_caps[ARB_map_buffer_range]", var2.GL_ARB_map_buffer_range);
        p_70001_1_.func_152767_b("gl_caps[ARB_multisample]", var2.GL_ARB_multisample);
        p_70001_1_.func_152767_b("gl_caps[ARB_multitexture]", var2.GL_ARB_multitexture);
        p_70001_1_.func_152767_b("gl_caps[ARB_occlusion_query2]", var2.GL_ARB_occlusion_query2);
        p_70001_1_.func_152767_b("gl_caps[ARB_pixel_buffer_object]", var2.GL_ARB_pixel_buffer_object);
        p_70001_1_.func_152767_b("gl_caps[ARB_seamless_cube_map]", var2.GL_ARB_seamless_cube_map);
        p_70001_1_.func_152767_b("gl_caps[ARB_shader_objects]", var2.GL_ARB_shader_objects);
        p_70001_1_.func_152767_b("gl_caps[ARB_shader_stencil_export]", var2.GL_ARB_shader_stencil_export);
        p_70001_1_.func_152767_b("gl_caps[ARB_shader_texture_lod]", var2.GL_ARB_shader_texture_lod);
        p_70001_1_.func_152767_b("gl_caps[ARB_shadow]", var2.GL_ARB_shadow);
        p_70001_1_.func_152767_b("gl_caps[ARB_shadow_ambient]", var2.GL_ARB_shadow_ambient);
        p_70001_1_.func_152767_b("gl_caps[ARB_stencil_texturing]", var2.GL_ARB_stencil_texturing);
        p_70001_1_.func_152767_b("gl_caps[ARB_sync]", var2.GL_ARB_sync);
        p_70001_1_.func_152767_b("gl_caps[ARB_tessellation_shader]", var2.GL_ARB_tessellation_shader);
        p_70001_1_.func_152767_b("gl_caps[ARB_texture_border_clamp]", var2.GL_ARB_texture_border_clamp);
        p_70001_1_.func_152767_b("gl_caps[ARB_texture_buffer_object]", var2.GL_ARB_texture_buffer_object);
        p_70001_1_.func_152767_b("gl_caps[ARB_texture_cube_map]", var2.GL_ARB_texture_cube_map);
        p_70001_1_.func_152767_b("gl_caps[ARB_texture_cube_map_array]", var2.GL_ARB_texture_cube_map_array);
        p_70001_1_.func_152767_b("gl_caps[ARB_texture_non_power_of_two]", var2.GL_ARB_texture_non_power_of_two);
        p_70001_1_.func_152767_b("gl_caps[ARB_uniform_buffer_object]", var2.GL_ARB_uniform_buffer_object);
        p_70001_1_.func_152767_b("gl_caps[ARB_vertex_blend]", var2.GL_ARB_vertex_blend);
        p_70001_1_.func_152767_b("gl_caps[ARB_vertex_buffer_object]", var2.GL_ARB_vertex_buffer_object);
        p_70001_1_.func_152767_b("gl_caps[ARB_vertex_program]", var2.GL_ARB_vertex_program);
        p_70001_1_.func_152767_b("gl_caps[ARB_vertex_shader]", var2.GL_ARB_vertex_shader);
        p_70001_1_.func_152767_b("gl_caps[EXT_bindable_uniform]", var2.GL_EXT_bindable_uniform);
        p_70001_1_.func_152767_b("gl_caps[EXT_blend_equation_separate]", var2.GL_EXT_blend_equation_separate);
        p_70001_1_.func_152767_b("gl_caps[EXT_blend_func_separate]", var2.GL_EXT_blend_func_separate);
        p_70001_1_.func_152767_b("gl_caps[EXT_blend_minmax]", var2.GL_EXT_blend_minmax);
        p_70001_1_.func_152767_b("gl_caps[EXT_blend_subtract]", var2.GL_EXT_blend_subtract);
        p_70001_1_.func_152767_b("gl_caps[EXT_draw_instanced]", var2.GL_EXT_draw_instanced);
        p_70001_1_.func_152767_b("gl_caps[EXT_framebuffer_multisample]", var2.GL_EXT_framebuffer_multisample);
        p_70001_1_.func_152767_b("gl_caps[EXT_framebuffer_object]", var2.GL_EXT_framebuffer_object);
        p_70001_1_.func_152767_b("gl_caps[EXT_framebuffer_sRGB]", var2.GL_EXT_framebuffer_sRGB);
        p_70001_1_.func_152767_b("gl_caps[EXT_geometry_shader4]", var2.GL_EXT_geometry_shader4);
        p_70001_1_.func_152767_b("gl_caps[EXT_gpu_program_parameters]", var2.GL_EXT_gpu_program_parameters);
        p_70001_1_.func_152767_b("gl_caps[EXT_gpu_shader4]", var2.GL_EXT_gpu_shader4);
        p_70001_1_.func_152767_b("gl_caps[EXT_multi_draw_arrays]", var2.GL_EXT_multi_draw_arrays);
        p_70001_1_.func_152767_b("gl_caps[EXT_packed_depth_stencil]", var2.GL_EXT_packed_depth_stencil);
        p_70001_1_.func_152767_b("gl_caps[EXT_paletted_texture]", var2.GL_EXT_paletted_texture);
        p_70001_1_.func_152767_b("gl_caps[EXT_rescale_normal]", var2.GL_EXT_rescale_normal);
        p_70001_1_.func_152767_b("gl_caps[EXT_separate_shader_objects]", var2.GL_EXT_separate_shader_objects);
        p_70001_1_.func_152767_b("gl_caps[EXT_shader_image_load_store]", var2.GL_EXT_shader_image_load_store);
        p_70001_1_.func_152767_b("gl_caps[EXT_shadow_funcs]", var2.GL_EXT_shadow_funcs);
        p_70001_1_.func_152767_b("gl_caps[EXT_shared_texture_palette]", var2.GL_EXT_shared_texture_palette);
        p_70001_1_.func_152767_b("gl_caps[EXT_stencil_clear_tag]", var2.GL_EXT_stencil_clear_tag);
        p_70001_1_.func_152767_b("gl_caps[EXT_stencil_two_side]", var2.GL_EXT_stencil_two_side);
        p_70001_1_.func_152767_b("gl_caps[EXT_stencil_wrap]", var2.GL_EXT_stencil_wrap);
        p_70001_1_.func_152767_b("gl_caps[EXT_texture_3d]", var2.GL_EXT_texture_3d);
        p_70001_1_.func_152767_b("gl_caps[EXT_texture_array]", var2.GL_EXT_texture_array);
        p_70001_1_.func_152767_b("gl_caps[EXT_texture_buffer_object]", var2.GL_EXT_texture_buffer_object);
        p_70001_1_.func_152767_b("gl_caps[EXT_texture_filter_anisotropic]", var2.GL_EXT_texture_filter_anisotropic);
        p_70001_1_.func_152767_b("gl_caps[EXT_texture_integer]", var2.GL_EXT_texture_integer);
        p_70001_1_.func_152767_b("gl_caps[EXT_texture_lod_bias]", var2.GL_EXT_texture_lod_bias);
        p_70001_1_.func_152767_b("gl_caps[EXT_texture_sRGB]", var2.GL_EXT_texture_sRGB);
        p_70001_1_.func_152767_b("gl_caps[EXT_vertex_shader]", var2.GL_EXT_vertex_shader);
        p_70001_1_.func_152767_b("gl_caps[EXT_vertex_weighting]", var2.GL_EXT_vertex_weighting);
        p_70001_1_.func_152767_b("gl_caps[gl_max_vertex_uniforms]", GL11.glGetInteger(35658));
        GL11.glGetError();
        p_70001_1_.func_152767_b("gl_caps[gl_max_fragment_uniforms]", GL11.glGetInteger(35657));
        GL11.glGetError();
        p_70001_1_.func_152767_b("gl_caps[gl_max_vertex_attribs]", GL11.glGetInteger(34921));
        GL11.glGetError();
        p_70001_1_.func_152767_b("gl_caps[gl_max_vertex_texture_image_units]", GL11.glGetInteger(35660));
        GL11.glGetError();
        p_70001_1_.func_152767_b("gl_caps[gl_max_texture_image_units]", GL11.glGetInteger(34930));
        GL11.glGetError();
        p_70001_1_.func_152767_b("gl_caps[gl_max_texture_image_units]", GL11.glGetInteger(35071));
        GL11.glGetError();
        p_70001_1_.func_152767_b("gl_max_texture_size", getGLMaximumTextureSize());
    }
    
    public static int getGLMaximumTextureSize() {
        for (int var0 = 16384; var0 > 0; var0 >>= 1) {
            GL11.glTexImage2D(32868, 0, 6408, var0, var0, 0, 6408, 5121, (ByteBuffer)null);
            final int var2 = GL11.glGetTexLevelParameteri(32868, 0, 4096);
            if (var2 != 0) {
                return var0;
            }
        }
        return -1;
    }
    
    @Override
    public boolean isSnooperEnabled() {
        return this.gameSettings.snooperEnabled;
    }
    
    public void setServerData(final ServerData p_71351_1_) {
        this.currentServerData = p_71351_1_;
    }
    
    public ServerData func_147104_D() {
        return this.currentServerData;
    }
    
    public boolean isIntegratedServerRunning() {
        return this.integratedServerIsRunning;
    }
    
    public boolean isSingleplayer() {
        return this.integratedServerIsRunning && this.theIntegratedServer != null;
    }
    
    public IntegratedServer getIntegratedServer() {
        return this.theIntegratedServer;
    }
    
    public static void stopIntegratedServer() {
        if (Minecraft.theMinecraft != null) {
            final IntegratedServer var0 = Minecraft.theMinecraft.getIntegratedServer();
            if (var0 != null) {
                var0.stopServer();
            }
        }
    }
    
    public PlayerUsageSnooper getPlayerUsageSnooper() {
        return this.usageSnooper;
    }
    
    public static long getSystemTime() {
        return Sys.getTime() * 1000L / Sys.getTimerResolution();
    }
    
    public boolean isFullScreen() {
        return this.fullscreen;
    }
    
    public Session getSession() {
        return this.session;
    }
    
    public Multimap func_152341_N() {
        return this.field_152356_J;
    }
    
    public Proxy getProxy() {
        return this.proxy;
    }
    
    public TextureManager getTextureManager() {
        return this.renderEngine;
    }
    
    public IResourceManager getResourceManager() {
        return this.mcResourceManager;
    }
    
    public ResourcePackRepository getResourcePackRepository() {
        return this.mcResourcePackRepository;
    }
    
    public LanguageManager getLanguageManager() {
        return this.mcLanguageManager;
    }
    
    public TextureMap getTextureMapBlocks() {
        return this.textureMapBlocks;
    }
    
    public boolean isJava64bit() {
        return this.jvm64bit;
    }
    
    public boolean func_147113_T() {
        return this.isGamePaused;
    }
    
    public SoundHandler getSoundHandler() {
        return this.mcSoundHandler;
    }
    
    public MusicTicker.MusicType func_147109_W() {
        return (this.currentScreen instanceof GuiWinGame) ? MusicTicker.MusicType.CREDITS : ((this.thePlayer != null) ? ((this.thePlayer.worldObj.provider instanceof WorldProviderHell) ? MusicTicker.MusicType.NETHER : ((this.thePlayer.worldObj.provider instanceof WorldProviderEnd) ? ((BossStatus.bossName != null && BossStatus.statusBarTime > 0) ? MusicTicker.MusicType.END_BOSS : MusicTicker.MusicType.END) : ((this.thePlayer.capabilities.isCreativeMode && this.thePlayer.capabilities.allowFlying) ? MusicTicker.MusicType.CREATIVE : MusicTicker.MusicType.GAME))) : MusicTicker.MusicType.MENU);
    }
    
    public IStream func_152346_Z() {
        return this.field_152353_at;
    }
    
    public void func_152348_aa() {
        final int var1 = Keyboard.getEventKey();
        if (var1 != 0 && !Keyboard.isRepeatEvent() && (!(this.currentScreen instanceof GuiControls) || ((GuiControls)this.currentScreen).field_152177_g <= getSystemTime() - 20L) && Keyboard.getEventKeyState()) {
            if (var1 == this.gameSettings.field_152395_am.getKeyCode()) {
                this.toggleFullscreen();
            }
            else if (var1 == this.gameSettings.keyBindScreenshot.getKeyCode()) {
                this.ingameGUI.getChatGUI().func_146227_a(ScreenShotHelper.saveScreenshot(this.mcDataDir, this.displayWidth, this.displayHeight, this.mcFramebuffer));
            }
        }
    }
    
    public ListenableFuture func_152343_a(final Callable p_152343_1_) {
        Validate.notNull((Object)p_152343_1_);
        if (!this.func_152345_ab()) {
            final ListenableFutureTask var2 = ListenableFutureTask.create(p_152343_1_);
            final Queue var3 = this.field_152351_aB;
            synchronized (this.field_152351_aB) {
                this.field_152351_aB.add(var2);
                return (ListenableFuture)var2;
            }
        }
        try {
            return Futures.immediateFuture(p_152343_1_.call());
        }
        catch (Exception var4) {
            return (ListenableFuture)Futures.immediateFailedCheckedFuture(var4);
        }
    }
    
    public ListenableFuture func_152344_a(final Runnable p_152344_1_) {
        Validate.notNull((Object)p_152344_1_);
        return this.func_152343_a(Executors.callable(p_152344_1_));
    }
    
    public boolean func_152345_ab() {
        return Thread.currentThread() == this.field_152352_aC;
    }
    
    public MinecraftSessionService func_152347_ac() {
        return this.field_152355_az;
    }
    
    public SkinManager func_152342_ad() {
        return this.field_152350_aA;
    }
    
    static {
        logger = LogManager.getLogger();
        locationMojangPng = new ResourceLocation("textures/gui/title/mojang.png");
        isRunningOnMac = (Util.getOSType() == Util.EnumOS.OSX);
        Minecraft.memoryReserve = new byte[10485760];
        macDisplayModes = Lists.newArrayList((Object[])new DisplayMode[] { new DisplayMode(2560, 1600), new DisplayMode(2880, 1800) });
    }
    
    static final class SwitchMovingObjectType
    {
        static final int[] field_152390_a;
        private static final String __OBFID = "CL_00000638";
        
        static {
            field_152390_a = new int[MovingObjectPosition.MovingObjectType.values().length];
            try {
                SwitchMovingObjectType.field_152390_a[MovingObjectPosition.MovingObjectType.ENTITY.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchMovingObjectType.field_152390_a[MovingObjectPosition.MovingObjectType.BLOCK.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
        }
    }
}
