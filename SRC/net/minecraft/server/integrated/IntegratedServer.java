package net.minecraft.server.integrated;

import net.minecraft.server.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.server.management.*;
import net.minecraft.world.demo.*;
import net.minecraft.src.*;
import net.minecraft.world.storage.*;
import java.io.*;
import net.minecraft.world.*;
import net.minecraft.crash.*;
import java.util.concurrent.*;
import net.minecraft.client.*;
import net.minecraft.profiler.*;
import net.minecraft.util.*;
import java.net.*;
import org.apache.logging.log4j.*;

public class IntegratedServer extends MinecraftServer
{
    private static final Logger logger;
    private final Minecraft mc;
    private final WorldSettings theWorldSettings;
    private boolean isGamePaused;
    private boolean isPublic;
    private ThreadLanServerPing lanServerPing;
    private static final String __OBFID = "CL_00001129";
    
    public IntegratedServer(final Minecraft p_i1317_1_, final String p_i1317_2_, final String p_i1317_3_, final WorldSettings p_i1317_4_) {
        super(new File(p_i1317_1_.mcDataDir, "saves"), p_i1317_1_.getProxy());
        this.setServerOwner(p_i1317_1_.getSession().getUsername());
        this.setFolderName(p_i1317_2_);
        this.setWorldName(p_i1317_3_);
        this.setDemo(p_i1317_1_.isDemo());
        this.canCreateBonusChest(p_i1317_4_.isBonusChestEnabled());
        this.setBuildLimit(256);
        this.func_152361_a(new IntegratedPlayerList(this));
        this.mc = p_i1317_1_;
        this.theWorldSettings = p_i1317_4_;
    }
    
    @Override
    protected void loadAllWorlds(final String p_71247_1_, final String p_71247_2_, final long p_71247_3_, final WorldType p_71247_5_, final String p_71247_6_) {
        this.convertMapIfNeeded(p_71247_1_);
        this.worldServers = new WorldServer[3];
        this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
        final ISaveHandler var7 = this.getActiveAnvilConverter().getSaveLoader(p_71247_1_, true);
        for (int var8 = 0; var8 < this.worldServers.length; ++var8) {
            byte var9 = 0;
            if (var8 == 1) {
                var9 = -1;
            }
            if (var8 == 2) {
                var9 = 1;
            }
            if (var8 == 0) {
                if (this.isDemo()) {
                    this.worldServers[var8] = new DemoWorldServer(this, var7, p_71247_2_, var9, this.theProfiler);
                }
                else {
                    this.worldServers[var8] = new WorldServerOF(this, var7, p_71247_2_, var9, this.theWorldSettings, this.theProfiler);
                }
            }
            else {
                this.worldServers[var8] = new WorldServerMultiOF(this, var7, p_71247_2_, var9, this.theWorldSettings, this.worldServers[0], this.theProfiler);
            }
            this.worldServers[var8].addWorldAccess(new WorldManager(this, this.worldServers[var8]));
            this.getConfigurationManager().setPlayerManager(this.worldServers);
        }
        this.func_147139_a(this.func_147135_j());
        this.initialWorldChunkLoad();
    }
    
    @Override
    protected boolean startServer() throws IOException {
        IntegratedServer.logger.info("Starting integrated minecraft server version 1.7.10");
        this.setOnlineMode(true);
        this.setCanSpawnAnimals(true);
        this.setCanSpawnNPCs(true);
        this.setAllowPvp(true);
        this.setAllowFlight(true);
        IntegratedServer.logger.info("Generating keypair");
        this.setKeyPair(CryptManager.createNewKeyPair());
        this.loadAllWorlds(this.getFolderName(), this.getWorldName(), this.theWorldSettings.getSeed(), this.theWorldSettings.getTerrainType(), this.theWorldSettings.func_82749_j());
        this.setMOTD(this.getServerOwner() + " - " + this.worldServers[0].getWorldInfo().getWorldName());
        return true;
    }
    
    @Override
    public void tick() {
        final boolean var1 = this.isGamePaused;
        this.isGamePaused = (Minecraft.getMinecraft().getNetHandler() != null && Minecraft.getMinecraft().func_147113_T());
        if (!var1 && this.isGamePaused) {
            IntegratedServer.logger.info("Saving and pausing game...");
            this.getConfigurationManager().saveAllPlayerData();
            this.saveAllWorlds(false);
        }
        if (!this.isGamePaused) {
            super.tick();
            if (this.mc.gameSettings.renderDistanceChunks != this.getConfigurationManager().getViewDistance()) {
                IntegratedServer.logger.info("Changing view distance to {}, from {}", new Object[] { this.mc.gameSettings.renderDistanceChunks, this.getConfigurationManager().getViewDistance() });
                this.getConfigurationManager().func_152611_a(this.mc.gameSettings.renderDistanceChunks);
            }
        }
    }
    
    @Override
    public boolean canStructuresSpawn() {
        return false;
    }
    
    @Override
    public WorldSettings.GameType getGameType() {
        return this.theWorldSettings.getGameType();
    }
    
    @Override
    public EnumDifficulty func_147135_j() {
        return this.mc.gameSettings.difficulty;
    }
    
    @Override
    public boolean isHardcore() {
        return this.theWorldSettings.getHardcoreEnabled();
    }
    
    @Override
    public boolean func_152363_m() {
        return false;
    }
    
    @Override
    protected File getDataDirectory() {
        return this.mc.mcDataDir;
    }
    
    @Override
    public boolean isDedicatedServer() {
        return false;
    }
    
    @Override
    protected void finalTick(final CrashReport p_71228_1_) {
        this.mc.crashed(p_71228_1_);
    }
    
    @Override
    public CrashReport addServerInfoToCrashReport(CrashReport p_71230_1_) {
        p_71230_1_ = super.addServerInfoToCrashReport(p_71230_1_);
        p_71230_1_.getCategory().addCrashSectionCallable("Type", new Callable() {
            private static final String __OBFID = "CL_00001130";
            
            @Override
            public String call() {
                return "Integrated Server (map_client.txt)";
            }
        });
        p_71230_1_.getCategory().addCrashSectionCallable("Is Modded", new Callable() {
            private static final String __OBFID = "CL_00001131";
            
            @Override
            public String call() {
                String var1 = ClientBrandRetriever.getClientModName();
                if (!var1.equals("vanilla")) {
                    return "Definitely; Client brand changed to '" + var1 + "'";
                }
                var1 = IntegratedServer.this.getServerModName();
                return var1.equals("vanilla") ? ((Minecraft.class.getSigners() == null) ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and both client + server brands are untouched.") : ("Definitely; Server brand changed to '" + var1 + "'");
            }
        });
        return p_71230_1_;
    }
    
    @Override
    public void addServerStatsToSnooper(final PlayerUsageSnooper p_70000_1_) {
        super.addServerStatsToSnooper(p_70000_1_);
        p_70000_1_.func_152768_a("snooper_partner", this.mc.getPlayerUsageSnooper().getUniqueID());
    }
    
    @Override
    public boolean isSnooperEnabled() {
        return Minecraft.getMinecraft().isSnooperEnabled();
    }
    
    @Override
    public String shareToLAN(final WorldSettings.GameType p_71206_1_, final boolean p_71206_2_) {
        try {
            int var3 = -1;
            try {
                var3 = HttpUtil.func_76181_a();
            }
            catch (IOException ex) {}
            if (var3 <= 0) {
                var3 = 25564;
            }
            this.func_147137_ag().addLanEndpoint(null, var3);
            IntegratedServer.logger.info("Started on " + var3);
            this.isPublic = true;
            (this.lanServerPing = new ThreadLanServerPing(this.getMOTD(), var3 + "")).start();
            this.getConfigurationManager().func_152604_a(p_71206_1_);
            this.getConfigurationManager().setCommandsAllowedForAll(p_71206_2_);
            return var3 + "";
        }
        catch (IOException var4) {
            return null;
        }
    }
    
    @Override
    public void stopServer() {
        super.stopServer();
        if (this.lanServerPing != null) {
            this.lanServerPing.interrupt();
            this.lanServerPing = null;
        }
    }
    
    @Override
    public void initiateShutdown() {
        super.initiateShutdown();
        if (this.lanServerPing != null) {
            this.lanServerPing.interrupt();
            this.lanServerPing = null;
        }
    }
    
    public boolean getPublic() {
        return this.isPublic;
    }
    
    @Override
    public void setGameType(final WorldSettings.GameType p_71235_1_) {
        this.getConfigurationManager().func_152604_a(p_71235_1_);
    }
    
    @Override
    public boolean isCommandBlockEnabled() {
        return true;
    }
    
    @Override
    public int func_110455_j() {
        return 4;
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
