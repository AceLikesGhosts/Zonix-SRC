package net.minecraft.server;

import net.minecraft.profiler.*;
import java.net.*;
import java.security.*;
import com.mojang.authlib.yggdrasil.*;
import com.mojang.authlib.minecraft.*;
import net.minecraft.server.management.*;
import net.minecraft.world.chunk.storage.*;
import net.minecraft.world.demo.*;
import net.minecraft.world.storage.*;
import net.minecraft.crash.*;
import java.text.*;
import javax.imageio.*;
import org.apache.commons.lang3.*;
import java.io.*;
import io.netty.handler.codec.base64.*;
import com.google.common.base.*;
import io.netty.buffer.*;
import java.awt.image.*;
import com.mojang.authlib.*;
import net.minecraft.util.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.server.gui.*;
import java.util.concurrent.*;
import net.minecraft.command.*;
import java.util.*;
import java.awt.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import org.apache.logging.log4j.*;

public abstract class MinecraftServer implements ICommandSender, Runnable, IPlayerUsage
{
    private static final Logger logger;
    public static final File field_152367_a;
    private static MinecraftServer mcServer;
    private final ISaveFormat anvilConverterForAnvilFile;
    private final PlayerUsageSnooper usageSnooper;
    private final File anvilFile;
    private final List tickables;
    private final ICommandManager commandManager;
    public final Profiler theProfiler;
    private final NetworkSystem field_147144_o;
    private final ServerStatusResponse field_147147_p;
    private final Random field_147146_q;
    private int serverPort;
    public WorldServer[] worldServers;
    private ServerConfigurationManager serverConfigManager;
    private boolean serverRunning;
    private boolean serverStopped;
    private int tickCounter;
    protected final Proxy serverProxy;
    public String currentTask;
    public int percentDone;
    private boolean onlineMode;
    private boolean canSpawnAnimals;
    private boolean canSpawnNPCs;
    private boolean pvpEnabled;
    private boolean allowFlight;
    private String motd;
    private int buildLimit;
    private int field_143008_E;
    public final long[] tickTimeArray;
    public long[][] timeOfLastDimensionTick;
    private KeyPair serverKeyPair;
    private String serverOwner;
    private String folderName;
    private String worldName;
    private boolean isDemo;
    private boolean enableBonusChest;
    private boolean worldIsBeingDeleted;
    private String field_147141_M;
    private boolean serverIsRunning;
    private long timeOfLastWarning;
    private String userMessage;
    private boolean startProfiling;
    private boolean isGamemodeForced;
    private final YggdrasilAuthenticationService field_152364_T;
    private final MinecraftSessionService field_147143_S;
    private long field_147142_T;
    private final GameProfileRepository field_152365_W;
    private final PlayerProfileCache field_152366_X;
    private static final String __OBFID = "CL_00001462";
    
    public MinecraftServer(final File p_i46400_1_, final Proxy p_i46400_2_) {
        this.usageSnooper = new PlayerUsageSnooper("server", this, getSystemTimeMillis());
        this.tickables = new ArrayList();
        this.theProfiler = new Profiler();
        this.field_147147_p = new ServerStatusResponse();
        this.field_147146_q = new Random();
        this.serverPort = -1;
        this.serverRunning = true;
        this.field_143008_E = 0;
        this.tickTimeArray = new long[100];
        this.field_147141_M = "";
        this.field_147142_T = 0L;
        this.field_152366_X = new PlayerProfileCache(this, MinecraftServer.field_152367_a);
        MinecraftServer.mcServer = this;
        this.serverProxy = p_i46400_2_;
        this.anvilFile = p_i46400_1_;
        this.field_147144_o = new NetworkSystem(this);
        this.commandManager = new ServerCommandManager();
        this.anvilConverterForAnvilFile = new AnvilSaveConverter(p_i46400_1_);
        this.field_152364_T = new YggdrasilAuthenticationService(p_i46400_2_, UUID.randomUUID().toString());
        this.field_147143_S = this.field_152364_T.createMinecraftSessionService();
        this.field_152365_W = this.field_152364_T.createProfileRepository();
    }
    
    protected abstract boolean startServer() throws IOException;
    
    protected void convertMapIfNeeded(final String p_71237_1_) {
        if (this.getActiveAnvilConverter().isOldMapFormat(p_71237_1_)) {
            MinecraftServer.logger.info("Converting map!");
            this.setUserMessage("menu.convertingLevel");
            this.getActiveAnvilConverter().convertMapFormat(p_71237_1_, new IProgressUpdate() {
                private long field_96245_b = System.currentTimeMillis();
                private static final String __OBFID = "CL_00001417";
                
                @Override
                public void displayProgressMessage(final String p_73720_1_) {
                }
                
                @Override
                public void resetProgressAndMessage(final String p_73721_1_) {
                }
                
                @Override
                public void setLoadingProgress(final int p_73718_1_) {
                    if (System.currentTimeMillis() - this.field_96245_b >= 1000L) {
                        this.field_96245_b = System.currentTimeMillis();
                        MinecraftServer.logger.info("Converting... " + p_73718_1_ + "%");
                    }
                }
                
                @Override
                public void func_146586_a() {
                }
                
                @Override
                public void resetProgresAndWorkingMessage(final String p_73719_1_) {
                }
            });
        }
    }
    
    protected synchronized void setUserMessage(final String p_71192_1_) {
        this.userMessage = p_71192_1_;
    }
    
    public synchronized String getUserMessage() {
        return this.userMessage;
    }
    
    protected void loadAllWorlds(final String p_71247_1_, final String p_71247_2_, final long p_71247_3_, final WorldType p_71247_5_, final String p_71247_6_) {
        this.convertMapIfNeeded(p_71247_1_);
        this.setUserMessage("menu.loadingLevel");
        this.worldServers = new WorldServer[3];
        this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
        final ISaveHandler var7 = this.anvilConverterForAnvilFile.getSaveLoader(p_71247_1_, true);
        final WorldInfo var8 = var7.loadWorldInfo();
        WorldSettings var9;
        if (var8 == null) {
            var9 = new WorldSettings(p_71247_3_, this.getGameType(), this.canStructuresSpawn(), this.isHardcore(), p_71247_5_);
            var9.func_82750_a(p_71247_6_);
        }
        else {
            var9 = new WorldSettings(var8);
        }
        if (this.enableBonusChest) {
            var9.enableBonusChest();
        }
        for (int var10 = 0; var10 < this.worldServers.length; ++var10) {
            byte var11 = 0;
            if (var10 == 1) {
                var11 = -1;
            }
            if (var10 == 2) {
                var11 = 1;
            }
            if (var10 == 0) {
                if (this.isDemo()) {
                    this.worldServers[var10] = new DemoWorldServer(this, var7, p_71247_2_, var11, this.theProfiler);
                }
                else {
                    this.worldServers[var10] = new WorldServer(this, var7, p_71247_2_, var11, var9, this.theProfiler);
                }
            }
            else {
                this.worldServers[var10] = new WorldServerMulti(this, var7, p_71247_2_, var11, var9, this.worldServers[0], this.theProfiler);
            }
            this.worldServers[var10].addWorldAccess(new WorldManager(this, this.worldServers[var10]));
            if (!this.isSinglePlayer()) {
                this.worldServers[var10].getWorldInfo().setGameType(this.getGameType());
            }
            this.serverConfigManager.setPlayerManager(this.worldServers);
        }
        this.func_147139_a(this.func_147135_j());
        this.initialWorldChunkLoad();
    }
    
    protected void initialWorldChunkLoad() {
        final boolean var1 = true;
        final boolean var2 = true;
        final boolean var3 = true;
        final boolean var4 = true;
        int var5 = 0;
        this.setUserMessage("menu.generatingTerrain");
        final byte var6 = 0;
        MinecraftServer.logger.info("Preparing start region for level " + var6);
        final WorldServer var7 = this.worldServers[var6];
        final ChunkCoordinates var8 = var7.getSpawnPoint();
        long var9 = getSystemTimeMillis();
        for (int var10 = -192; var10 <= 192 && this.isServerRunning(); var10 += 16) {
            for (int var11 = -192; var11 <= 192 && this.isServerRunning(); var11 += 16) {
                final long var12 = getSystemTimeMillis();
                if (var12 - var9 > 1000L) {
                    this.outputPercentRemaining("Preparing spawn area", var5 * 100 / 625);
                    var9 = var12;
                }
                ++var5;
                var7.theChunkProviderServer.loadChunk(var8.posX + var10 >> 4, var8.posZ + var11 >> 4);
            }
        }
        this.clearCurrentTask();
    }
    
    public abstract boolean canStructuresSpawn();
    
    public abstract WorldSettings.GameType getGameType();
    
    public abstract EnumDifficulty func_147135_j();
    
    public abstract boolean isHardcore();
    
    public abstract int func_110455_j();
    
    public abstract boolean func_152363_m();
    
    protected void outputPercentRemaining(final String p_71216_1_, final int p_71216_2_) {
        this.currentTask = p_71216_1_;
        this.percentDone = p_71216_2_;
        MinecraftServer.logger.info(p_71216_1_ + ": " + p_71216_2_ + "%");
    }
    
    protected void clearCurrentTask() {
        this.currentTask = null;
        this.percentDone = 0;
    }
    
    protected void saveAllWorlds(final boolean p_71267_1_) {
        if (!this.worldIsBeingDeleted) {
            for (final WorldServer var5 : this.worldServers) {
                if (var5 != null) {
                    if (!p_71267_1_) {
                        MinecraftServer.logger.info("Saving chunks for level '" + var5.getWorldInfo().getWorldName() + "'/" + var5.provider.getDimensionName());
                    }
                    try {
                        var5.saveAllChunks(true, null);
                    }
                    catch (MinecraftException var6) {
                        MinecraftServer.logger.warn(var6.getMessage());
                    }
                }
            }
        }
    }
    
    public void stopServer() {
        if (!this.worldIsBeingDeleted) {
            MinecraftServer.logger.info("Stopping server");
            if (this.func_147137_ag() != null) {
                this.func_147137_ag().terminateEndpoints();
            }
            if (this.serverConfigManager != null) {
                MinecraftServer.logger.info("Saving players");
                this.serverConfigManager.saveAllPlayerData();
                this.serverConfigManager.removeAllPlayers();
            }
            if (this.worldServers != null) {
                MinecraftServer.logger.info("Saving worlds");
                this.saveAllWorlds(false);
                for (int var1 = 0; var1 < this.worldServers.length; ++var1) {
                    final WorldServer var2 = this.worldServers[var1];
                    var2.flush();
                }
            }
            if (this.usageSnooper.isSnooperRunning()) {
                this.usageSnooper.stopSnooper();
            }
        }
    }
    
    public boolean isServerRunning() {
        return this.serverRunning;
    }
    
    public void initiateShutdown() {
        this.serverRunning = false;
    }
    
    @Override
    public void run() {
        try {
            if (this.startServer()) {
                long var1 = getSystemTimeMillis();
                long var2 = 0L;
                this.field_147147_p.func_151315_a(new ChatComponentText(this.motd));
                this.field_147147_p.func_151321_a(new ServerStatusResponse.MinecraftProtocolVersionIdentifier("1.7.10", 5));
                this.func_147138_a(this.field_147147_p);
                while (this.serverRunning) {
                    final long var3 = getSystemTimeMillis();
                    long var4 = var3 - var1;
                    if (var4 > 2000L && var1 - this.timeOfLastWarning >= 15000L) {
                        MinecraftServer.logger.warn("Can't keep up! Did the system time change, or is the server overloaded? Running {}ms behind, skipping {} tick(s)", new Object[] { var4, var4 / 50L });
                        var4 = 2000L;
                        this.timeOfLastWarning = var1;
                    }
                    if (var4 < 0L) {
                        MinecraftServer.logger.warn("Time ran backwards! Did the system time change?");
                        var4 = 0L;
                    }
                    var2 += var4;
                    var1 = var3;
                    if (this.worldServers[0].areAllPlayersAsleep()) {
                        this.tick();
                        var2 = 0L;
                    }
                    else {
                        while (var2 > 50L) {
                            var2 -= 50L;
                            this.tick();
                        }
                    }
                    Thread.sleep(Math.max(1L, 50L - var2));
                    this.serverIsRunning = true;
                }
            }
            else {
                this.finalTick(null);
            }
        }
        catch (Throwable var5) {
            MinecraftServer.logger.error("Encountered an unexpected exception", var5);
            CrashReport var6 = null;
            if (var5 instanceof ReportedException) {
                var6 = this.addServerInfoToCrashReport(((ReportedException)var5).getCrashReport());
            }
            else {
                var6 = this.addServerInfoToCrashReport(new CrashReport("Exception in server tick loop", var5));
            }
            final File var7 = new File(new File(this.getDataDirectory(), "crash-reports"), "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-server.txt");
            if (var6.saveToFile(var7)) {
                MinecraftServer.logger.error("This crash report has been saved to: " + var7.getAbsolutePath());
            }
            else {
                MinecraftServer.logger.error("We were unable to save this crash report to disk.");
            }
            this.finalTick(var6);
            try {
                this.stopServer();
                this.serverStopped = true;
            }
            catch (Throwable var8) {
                MinecraftServer.logger.error("Exception stopping the server", var8);
            }
            finally {
                this.systemExitNow();
            }
        }
        finally {
            try {
                this.stopServer();
                this.serverStopped = true;
            }
            catch (Throwable var9) {
                MinecraftServer.logger.error("Exception stopping the server", var9);
                this.systemExitNow();
            }
            finally {
                this.systemExitNow();
            }
        }
    }
    
    private void func_147138_a(final ServerStatusResponse p_147138_1_) {
        final File var2 = this.getFile("server-icon.png");
        if (var2.isFile()) {
            final ByteBuf var3 = Unpooled.buffer();
            try {
                final BufferedImage var4 = ImageIO.read(var2);
                Validate.validState(var4.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
                Validate.validState(var4.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
                ImageIO.write(var4, "PNG", (OutputStream)new ByteBufOutputStream(var3));
                final ByteBuf var5 = Base64.encode(var3);
                p_147138_1_.func_151320_a("data:image/png;base64," + var5.toString(Charsets.UTF_8));
            }
            catch (Exception var6) {
                MinecraftServer.logger.error("Couldn't load server icon", (Throwable)var6);
            }
            finally {
                var3.release();
            }
        }
    }
    
    protected File getDataDirectory() {
        return new File(".");
    }
    
    protected void finalTick(final CrashReport p_71228_1_) {
    }
    
    protected void systemExitNow() {
    }
    
    public void tick() {
        final long var1 = System.nanoTime();
        ++this.tickCounter;
        if (this.startProfiling) {
            this.startProfiling = false;
            this.theProfiler.profilingEnabled = true;
            this.theProfiler.clearProfiling();
        }
        this.theProfiler.startSection("root");
        this.updateTimeLightAndEntities();
        if (var1 - this.field_147142_T >= 5000000000L) {
            this.field_147142_T = var1;
            this.field_147147_p.func_151319_a(new ServerStatusResponse.PlayerCountData(this.getMaxPlayers(), this.getCurrentPlayerCount()));
            final GameProfile[] var2 = new GameProfile[Math.min(this.getCurrentPlayerCount(), 12)];
            final int var3 = MathHelper.getRandomIntegerInRange(this.field_147146_q, 0, this.getCurrentPlayerCount() - var2.length);
            for (int var4 = 0; var4 < var2.length; ++var4) {
                var2[var4] = this.serverConfigManager.playerEntityList.get(var3 + var4).getGameProfile();
            }
            Collections.shuffle(Arrays.asList(var2));
            this.field_147147_p.func_151318_b().func_151330_a(var2);
        }
        if (this.tickCounter % 900 == 0) {
            this.theProfiler.startSection("save");
            this.serverConfigManager.saveAllPlayerData();
            this.saveAllWorlds(true);
            this.theProfiler.endSection();
        }
        this.theProfiler.startSection("tallying");
        this.tickTimeArray[this.tickCounter % 100] = System.nanoTime() - var1;
        this.theProfiler.endSection();
        this.theProfiler.startSection("snooper");
        if (!this.usageSnooper.isSnooperRunning() && this.tickCounter > 100) {
            this.usageSnooper.startSnooper();
        }
        if (this.tickCounter % 6000 == 0) {
            this.usageSnooper.addMemoryStatsToSnooper();
        }
        this.theProfiler.endSection();
        this.theProfiler.endSection();
    }
    
    public void updateTimeLightAndEntities() {
        this.theProfiler.startSection("levels");
        for (int var1 = 0; var1 < this.worldServers.length; ++var1) {
            final long var2 = System.nanoTime();
            if (var1 == 0 || this.getAllowNether()) {
                final WorldServer var3 = this.worldServers[var1];
                this.theProfiler.startSection(var3.getWorldInfo().getWorldName());
                this.theProfiler.startSection("pools");
                this.theProfiler.endSection();
                if (this.tickCounter % 20 == 0) {
                    this.theProfiler.startSection("timeSync");
                    this.serverConfigManager.func_148537_a(new S03PacketTimeUpdate(var3.getTotalWorldTime(), var3.getWorldTime(), var3.getGameRules().getGameRuleBooleanValue("doDaylightCycle")), var3.provider.dimensionId);
                    this.theProfiler.endSection();
                }
                this.theProfiler.startSection("tick");
                try {
                    var3.tick();
                }
                catch (Throwable var5) {
                    final CrashReport var4 = CrashReport.makeCrashReport(var5, "Exception ticking world");
                    var3.addWorldInfoToCrashReport(var4);
                    throw new ReportedException(var4);
                }
                try {
                    var3.updateEntities();
                }
                catch (Throwable var6) {
                    final CrashReport var4 = CrashReport.makeCrashReport(var6, "Exception ticking world entities");
                    var3.addWorldInfoToCrashReport(var4);
                    throw new ReportedException(var4);
                }
                this.theProfiler.endSection();
                this.theProfiler.startSection("tracker");
                var3.getEntityTracker().updateTrackedEntities();
                this.theProfiler.endSection();
                this.theProfiler.endSection();
            }
            this.timeOfLastDimensionTick[var1][this.tickCounter % 100] = System.nanoTime() - var2;
        }
        this.theProfiler.endStartSection("connection");
        this.func_147137_ag().networkTick();
        this.theProfiler.endStartSection("players");
        this.serverConfigManager.sendPlayerInfoToAllPlayers();
        this.theProfiler.endStartSection("tickables");
        for (int var1 = 0; var1 < this.tickables.size(); ++var1) {
            this.tickables.get(var1).update();
        }
        this.theProfiler.endSection();
    }
    
    public boolean getAllowNether() {
        return true;
    }
    
    public void startServerThread() {
        new ServerThread("Server Thread").start();
    }
    
    public File getFile(final String p_71209_1_) {
        return new File(this.getDataDirectory(), p_71209_1_);
    }
    
    public void logWarning(final String p_71236_1_) {
        MinecraftServer.logger.warn(p_71236_1_);
    }
    
    public WorldServer worldServerForDimension(final int p_71218_1_) {
        return (p_71218_1_ == -1) ? this.worldServers[1] : ((p_71218_1_ == 1) ? this.worldServers[2] : this.worldServers[0]);
    }
    
    public String getMinecraftVersion() {
        return "1.7.10";
    }
    
    public int getCurrentPlayerCount() {
        return this.serverConfigManager.getCurrentPlayerCount();
    }
    
    public int getMaxPlayers() {
        return this.serverConfigManager.getMaxPlayers();
    }
    
    public String[] getAllUsernames() {
        return this.serverConfigManager.getAllUsernames();
    }
    
    public GameProfile[] func_152357_F() {
        return this.serverConfigManager.func_152600_g();
    }
    
    public String getServerModName() {
        return "vanilla";
    }
    
    public CrashReport addServerInfoToCrashReport(final CrashReport p_71230_1_) {
        p_71230_1_.getCategory().addCrashSectionCallable("Profiler Position", new Callable() {
            private static final String __OBFID = "CL_00001419";
            
            @Override
            public String call() {
                return MinecraftServer.this.theProfiler.profilingEnabled ? MinecraftServer.this.theProfiler.getNameOfLastSection() : "N/A (disabled)";
            }
        });
        if (this.worldServers != null && this.worldServers.length > 0 && this.worldServers[0] != null) {
            p_71230_1_.getCategory().addCrashSectionCallable("Vec3 Pool Size", new Callable() {
                private static final String __OBFID = "CL_00001420";
                
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
        }
        if (this.serverConfigManager != null) {
            p_71230_1_.getCategory().addCrashSectionCallable("Player Count", new Callable() {
                private static final String __OBFID = "CL_00001780";
                
                @Override
                public String call() {
                    return MinecraftServer.this.serverConfigManager.getCurrentPlayerCount() + " / " + MinecraftServer.this.serverConfigManager.getMaxPlayers() + "; " + MinecraftServer.this.serverConfigManager.playerEntityList;
                }
            });
        }
        return p_71230_1_;
    }
    
    public List getPossibleCompletions(final ICommandSender p_71248_1_, String p_71248_2_) {
        final ArrayList var3 = new ArrayList();
        if (p_71248_2_.startsWith("/")) {
            p_71248_2_ = p_71248_2_.substring(1);
            final boolean var4 = !p_71248_2_.contains(" ");
            final List var5 = this.commandManager.getPossibleCommands(p_71248_1_, p_71248_2_);
            if (var5 != null) {
                for (final String var7 : var5) {
                    if (var4) {
                        var3.add("/" + var7);
                    }
                    else {
                        var3.add(var7);
                    }
                }
            }
            return var3;
        }
        final String[] var8 = p_71248_2_.split(" ", -1);
        final String var9 = var8[var8.length - 1];
        for (final String var13 : this.serverConfigManager.getAllUsernames()) {
            if (CommandBase.doesStringStartWith(var9, var13)) {
                var3.add(var13);
            }
        }
        return var3;
    }
    
    public static MinecraftServer getServer() {
        return MinecraftServer.mcServer;
    }
    
    @Override
    public String getCommandSenderName() {
        return "Server";
    }
    
    @Override
    public void addChatMessage(final IChatComponent p_145747_1_) {
        MinecraftServer.logger.info(p_145747_1_.getUnformattedText());
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int p_70003_1_, final String p_70003_2_) {
        return true;
    }
    
    public ICommandManager getCommandManager() {
        return this.commandManager;
    }
    
    public KeyPair getKeyPair() {
        return this.serverKeyPair;
    }
    
    public String getServerOwner() {
        return this.serverOwner;
    }
    
    public void setServerOwner(final String p_71224_1_) {
        this.serverOwner = p_71224_1_;
    }
    
    public boolean isSinglePlayer() {
        return this.serverOwner != null;
    }
    
    public String getFolderName() {
        return this.folderName;
    }
    
    public void setFolderName(final String p_71261_1_) {
        this.folderName = p_71261_1_;
    }
    
    public void setWorldName(final String p_71246_1_) {
        this.worldName = p_71246_1_;
    }
    
    public String getWorldName() {
        return this.worldName;
    }
    
    public void setKeyPair(final KeyPair p_71253_1_) {
        this.serverKeyPair = p_71253_1_;
    }
    
    public void func_147139_a(final EnumDifficulty p_147139_1_) {
        for (int var2 = 0; var2 < this.worldServers.length; ++var2) {
            final WorldServer var3 = this.worldServers[var2];
            if (var3 != null) {
                if (var3.getWorldInfo().isHardcoreModeEnabled()) {
                    var3.difficultySetting = EnumDifficulty.HARD;
                    var3.setAllowedSpawnTypes(true, true);
                }
                else if (this.isSinglePlayer()) {
                    var3.difficultySetting = p_147139_1_;
                    var3.setAllowedSpawnTypes(var3.difficultySetting != EnumDifficulty.PEACEFUL, true);
                }
                else {
                    var3.difficultySetting = p_147139_1_;
                    var3.setAllowedSpawnTypes(this.allowSpawnMonsters(), this.canSpawnAnimals);
                }
            }
        }
    }
    
    protected boolean allowSpawnMonsters() {
        return true;
    }
    
    public boolean isDemo() {
        return this.isDemo;
    }
    
    public void setDemo(final boolean p_71204_1_) {
        this.isDemo = p_71204_1_;
    }
    
    public void canCreateBonusChest(final boolean p_71194_1_) {
        this.enableBonusChest = p_71194_1_;
    }
    
    public ISaveFormat getActiveAnvilConverter() {
        return this.anvilConverterForAnvilFile;
    }
    
    public void deleteWorldAndStopServer() {
        this.worldIsBeingDeleted = true;
        this.getActiveAnvilConverter().flushCache();
        for (int var1 = 0; var1 < this.worldServers.length; ++var1) {
            final WorldServer var2 = this.worldServers[var1];
            if (var2 != null) {
                var2.flush();
            }
        }
        this.getActiveAnvilConverter().deleteWorldDirectory(this.worldServers[0].getSaveHandler().getWorldDirectoryName());
        this.initiateShutdown();
    }
    
    public String func_147133_T() {
        return this.field_147141_M;
    }
    
    @Override
    public void addServerStatsToSnooper(final PlayerUsageSnooper p_70000_1_) {
        p_70000_1_.func_152768_a("whitelist_enabled", false);
        p_70000_1_.func_152768_a("whitelist_count", 0);
        p_70000_1_.func_152768_a("players_current", this.getCurrentPlayerCount());
        p_70000_1_.func_152768_a("players_max", this.getMaxPlayers());
        p_70000_1_.func_152768_a("players_seen", this.serverConfigManager.getAvailablePlayerDat().length);
        p_70000_1_.func_152768_a("uses_auth", this.onlineMode);
        p_70000_1_.func_152768_a("gui_state", this.getGuiEnabled() ? "enabled" : "disabled");
        p_70000_1_.func_152768_a("run_time", (getSystemTimeMillis() - p_70000_1_.getMinecraftStartTimeMillis()) / 60L * 1000L);
        p_70000_1_.func_152768_a("avg_tick_ms", (int)(MathHelper.average(this.tickTimeArray) * 1.0E-6));
        int var2 = 0;
        for (int var3 = 0; var3 < this.worldServers.length; ++var3) {
            if (this.worldServers[var3] != null) {
                final WorldServer var4 = this.worldServers[var3];
                final WorldInfo var5 = var4.getWorldInfo();
                p_70000_1_.func_152768_a("world[" + var2 + "][dimension]", var4.provider.dimensionId);
                p_70000_1_.func_152768_a("world[" + var2 + "][mode]", var5.getGameType());
                p_70000_1_.func_152768_a("world[" + var2 + "][difficulty]", var4.difficultySetting);
                p_70000_1_.func_152768_a("world[" + var2 + "][hardcore]", var5.isHardcoreModeEnabled());
                p_70000_1_.func_152768_a("world[" + var2 + "][generator_name]", var5.getTerrainType().getWorldTypeName());
                p_70000_1_.func_152768_a("world[" + var2 + "][generator_version]", var5.getTerrainType().getGeneratorVersion());
                p_70000_1_.func_152768_a("world[" + var2 + "][height]", this.buildLimit);
                p_70000_1_.func_152768_a("world[" + var2 + "][chunks_loaded]", var4.getChunkProvider().getLoadedChunkCount());
                ++var2;
            }
        }
        p_70000_1_.func_152768_a("worlds", var2);
    }
    
    @Override
    public void addServerTypeToSnooper(final PlayerUsageSnooper p_70001_1_) {
        p_70001_1_.func_152767_b("singleplayer", this.isSinglePlayer());
        p_70001_1_.func_152767_b("server_brand", this.getServerModName());
        p_70001_1_.func_152767_b("gui_supported", GraphicsEnvironment.isHeadless() ? "headless" : "supported");
        p_70001_1_.func_152767_b("dedicated", this.isDedicatedServer());
    }
    
    @Override
    public boolean isSnooperEnabled() {
        return true;
    }
    
    public abstract boolean isDedicatedServer();
    
    public boolean isServerInOnlineMode() {
        return this.onlineMode;
    }
    
    public void setOnlineMode(final boolean p_71229_1_) {
        this.onlineMode = p_71229_1_;
    }
    
    public boolean getCanSpawnAnimals() {
        return this.canSpawnAnimals;
    }
    
    public void setCanSpawnAnimals(final boolean p_71251_1_) {
        this.canSpawnAnimals = p_71251_1_;
    }
    
    public boolean getCanSpawnNPCs() {
        return this.canSpawnNPCs;
    }
    
    public void setCanSpawnNPCs(final boolean p_71257_1_) {
        this.canSpawnNPCs = p_71257_1_;
    }
    
    public boolean isPVPEnabled() {
        return this.pvpEnabled;
    }
    
    public void setAllowPvp(final boolean p_71188_1_) {
        this.pvpEnabled = p_71188_1_;
    }
    
    public boolean isFlightAllowed() {
        return this.allowFlight;
    }
    
    public void setAllowFlight(final boolean p_71245_1_) {
        this.allowFlight = p_71245_1_;
    }
    
    public abstract boolean isCommandBlockEnabled();
    
    public String getMOTD() {
        return this.motd;
    }
    
    public void setMOTD(final String p_71205_1_) {
        this.motd = p_71205_1_;
    }
    
    public int getBuildLimit() {
        return this.buildLimit;
    }
    
    public void setBuildLimit(final int p_71191_1_) {
        this.buildLimit = p_71191_1_;
    }
    
    public ServerConfigurationManager getConfigurationManager() {
        return this.serverConfigManager;
    }
    
    public void func_152361_a(final ServerConfigurationManager p_152361_1_) {
        this.serverConfigManager = p_152361_1_;
    }
    
    public void setGameType(final WorldSettings.GameType p_71235_1_) {
        for (int var2 = 0; var2 < this.worldServers.length; ++var2) {
            getServer().worldServers[var2].getWorldInfo().setGameType(p_71235_1_);
        }
    }
    
    public NetworkSystem func_147137_ag() {
        return this.field_147144_o;
    }
    
    public boolean serverIsInRunLoop() {
        return this.serverIsRunning;
    }
    
    public boolean getGuiEnabled() {
        return false;
    }
    
    public abstract String shareToLAN(final WorldSettings.GameType p0, final boolean p1);
    
    public int getTickCounter() {
        return this.tickCounter;
    }
    
    public void enableProfiling() {
        this.startProfiling = true;
    }
    
    public PlayerUsageSnooper getPlayerUsageSnooper() {
        return this.usageSnooper;
    }
    
    @Override
    public ChunkCoordinates getPlayerCoordinates() {
        return new ChunkCoordinates(0, 0, 0);
    }
    
    @Override
    public World getEntityWorld() {
        return this.worldServers[0];
    }
    
    public int getSpawnProtectionSize() {
        return 16;
    }
    
    public boolean isBlockProtected(final World p_96290_1_, final int p_96290_2_, final int p_96290_3_, final int p_96290_4_, final EntityPlayer p_96290_5_) {
        return false;
    }
    
    public boolean getForceGamemode() {
        return this.isGamemodeForced;
    }
    
    public Proxy getServerProxy() {
        return this.serverProxy;
    }
    
    public static long getSystemTimeMillis() {
        return System.currentTimeMillis();
    }
    
    public int func_143007_ar() {
        return this.field_143008_E;
    }
    
    public void func_143006_e(final int p_143006_1_) {
        this.field_143008_E = p_143006_1_;
    }
    
    @Override
    public IChatComponent func_145748_c_() {
        return new ChatComponentText(this.getCommandSenderName());
    }
    
    public boolean func_147136_ar() {
        return true;
    }
    
    public MinecraftSessionService func_147130_as() {
        return this.field_147143_S;
    }
    
    public GameProfileRepository func_152359_aw() {
        return this.field_152365_W;
    }
    
    public PlayerProfileCache func_152358_ax() {
        return this.field_152366_X;
    }
    
    public ServerStatusResponse func_147134_at() {
        return this.field_147147_p;
    }
    
    public void func_147132_au() {
        this.field_147142_T = 0L;
    }
    
    static {
        logger = LogManager.getLogger();
        field_152367_a = new File("usercache.json");
    }
    
    public static class ServerThread extends Thread
    {
        private static final String __OBFID = "CL_00001418";
        
        public ServerThread(final String thread) {
            super(thread);
        }
        
        @Override
        public void run() {
            MinecraftServer.getServer().run();
        }
    }
}
