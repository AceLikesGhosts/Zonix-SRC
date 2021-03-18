package net.minecraft.server.management;

import java.io.*;
import java.text.*;
import net.minecraft.server.*;
import net.minecraft.world.storage.*;
import net.minecraft.world.*;
import net.minecraft.network.*;
import com.google.common.base.*;
import net.minecraft.potion.*;
import com.mojang.authlib.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import java.net.*;
import com.google.common.collect.*;
import net.minecraft.world.demo.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.scoreboard.*;
import net.minecraft.network.play.server.*;
import org.apache.logging.log4j.*;

public abstract class ServerConfigurationManager
{
    public static final File field_152613_a;
    public static final File field_152614_b;
    public static final File field_152615_c;
    public static final File field_152616_d;
    private static final Logger logger;
    private static final SimpleDateFormat dateFormat;
    private final MinecraftServer mcServer;
    public final List playerEntityList;
    private final UserListBans bannedPlayers;
    private final BanList bannedIPs;
    private final UserListOps ops;
    private final UserListWhitelist whiteListedPlayers;
    private final Map field_148547_k;
    private IPlayerFileData playerNBTManagerObj;
    private boolean whiteListEnforced;
    protected int maxPlayers;
    private int viewDistance;
    private WorldSettings.GameType gameType;
    private boolean commandsAllowedForAll;
    private int playerPingIndex;
    private static final String __OBFID = "CL_00001423";
    
    public ServerConfigurationManager(final MinecraftServer p_i1500_1_) {
        this.playerEntityList = new ArrayList();
        this.bannedPlayers = new UserListBans(ServerConfigurationManager.field_152613_a);
        this.bannedIPs = new BanList(ServerConfigurationManager.field_152614_b);
        this.ops = new UserListOps(ServerConfigurationManager.field_152615_c);
        this.whiteListedPlayers = new UserListWhitelist(ServerConfigurationManager.field_152616_d);
        this.field_148547_k = Maps.newHashMap();
        this.mcServer = p_i1500_1_;
        this.bannedPlayers.func_152686_a(false);
        this.bannedIPs.func_152686_a(false);
        this.maxPlayers = 8;
    }
    
    public void initializeConnectionToPlayer(final NetworkManager p_72355_1_, final EntityPlayerMP p_72355_2_) {
        final GameProfile var3 = p_72355_2_.getGameProfile();
        final PlayerProfileCache var4 = this.mcServer.func_152358_ax();
        final GameProfile var5 = var4.func_152652_a(var3.getId());
        final String var6 = (var5 == null) ? var3.getName() : var5.getName();
        var4.func_152649_a(var3);
        final NBTTagCompound var7 = this.readPlayerDataFromFile(p_72355_2_);
        p_72355_2_.setWorld(this.mcServer.worldServerForDimension(p_72355_2_.dimension));
        p_72355_2_.theItemInWorldManager.setWorld((WorldServer)p_72355_2_.worldObj);
        String var8 = "local";
        if (p_72355_1_.getSocketAddress() != null) {
            var8 = p_72355_1_.getSocketAddress().toString();
        }
        ServerConfigurationManager.logger.info(p_72355_2_.getCommandSenderName() + "[" + var8 + "] logged in with entity id " + p_72355_2_.getEntityId() + " at (" + p_72355_2_.posX + ", " + p_72355_2_.posY + ", " + p_72355_2_.posZ + ")");
        final WorldServer var9 = this.mcServer.worldServerForDimension(p_72355_2_.dimension);
        final ChunkCoordinates var10 = var9.getSpawnPoint();
        this.func_72381_a(p_72355_2_, null, var9);
        final NetHandlerPlayServer var11 = new NetHandlerPlayServer(this.mcServer, p_72355_1_, p_72355_2_);
        var11.sendPacket(new S01PacketJoinGame(p_72355_2_.getEntityId(), p_72355_2_.theItemInWorldManager.getGameType(), var9.getWorldInfo().isHardcoreModeEnabled(), var9.provider.dimensionId, var9.difficultySetting, this.getMaxPlayers(), var9.getWorldInfo().getTerrainType()));
        var11.sendPacket(new S3FPacketCustomPayload("MC|Brand", this.getServerInstance().getServerModName().getBytes(Charsets.UTF_8)));
        var11.sendPacket(new S05PacketSpawnPosition(var10.posX, var10.posY, var10.posZ));
        var11.sendPacket(new S39PacketPlayerAbilities(p_72355_2_.capabilities));
        var11.sendPacket(new S09PacketHeldItemChange(p_72355_2_.inventory.currentItem));
        p_72355_2_.func_147099_x().func_150877_d();
        p_72355_2_.func_147099_x().func_150884_b(p_72355_2_);
        this.func_96456_a((ServerScoreboard)var9.getScoreboard(), p_72355_2_);
        this.mcServer.func_147132_au();
        ChatComponentTranslation var12;
        if (!p_72355_2_.getCommandSenderName().equalsIgnoreCase(var6)) {
            var12 = new ChatComponentTranslation("multiplayer.player.joined.renamed", new Object[] { p_72355_2_.func_145748_c_(), var6 });
        }
        else {
            var12 = new ChatComponentTranslation("multiplayer.player.joined", new Object[] { p_72355_2_.func_145748_c_() });
        }
        var12.getChatStyle().setColor(EnumChatFormatting.YELLOW);
        this.func_148539_a(var12);
        this.playerLoggedIn(p_72355_2_);
        var11.setPlayerLocation(p_72355_2_.posX, p_72355_2_.posY, p_72355_2_.posZ, p_72355_2_.rotationYaw, p_72355_2_.rotationPitch);
        this.updateTimeAndWeatherForPlayer(p_72355_2_, var9);
        if (this.mcServer.func_147133_T().length() > 0) {
            p_72355_2_.func_147095_a(this.mcServer.func_147133_T());
        }
        for (final PotionEffect var14 : p_72355_2_.getActivePotionEffects()) {
            var11.sendPacket(new S1DPacketEntityEffect(p_72355_2_.getEntityId(), var14));
        }
        p_72355_2_.addSelfToInternalCraftingInventory();
        if (var7 != null && var7.func_150297_b("Riding", 10)) {
            final Entity var15 = EntityList.createEntityFromNBT(var7.getCompoundTag("Riding"), var9);
            if (var15 != null) {
                var15.forceSpawn = true;
                var9.spawnEntityInWorld(var15);
                p_72355_2_.mountEntity(var15);
                var15.forceSpawn = false;
            }
        }
    }
    
    protected void func_96456_a(final ServerScoreboard p_96456_1_, final EntityPlayerMP p_96456_2_) {
        final HashSet var3 = new HashSet();
        for (final ScorePlayerTeam var5 : p_96456_1_.getTeams()) {
            p_96456_2_.playerNetServerHandler.sendPacket(new S3EPacketTeams(var5, 0));
        }
        for (int var6 = 0; var6 < 3; ++var6) {
            final ScoreObjective var7 = p_96456_1_.func_96539_a(var6);
            if (var7 != null && !var3.contains(var7)) {
                final List var8 = p_96456_1_.func_96550_d(var7);
                for (final Packet var10 : var8) {
                    p_96456_2_.playerNetServerHandler.sendPacket(var10);
                }
                var3.add(var7);
            }
        }
    }
    
    public void setPlayerManager(final WorldServer[] p_72364_1_) {
        this.playerNBTManagerObj = p_72364_1_[0].getSaveHandler().getSaveHandler();
    }
    
    public void func_72375_a(final EntityPlayerMP p_72375_1_, final WorldServer p_72375_2_) {
        final WorldServer var3 = p_72375_1_.getServerForPlayer();
        if (p_72375_2_ != null) {
            p_72375_2_.getPlayerManager().removePlayer(p_72375_1_);
        }
        var3.getPlayerManager().addPlayer(p_72375_1_);
        var3.theChunkProviderServer.loadChunk((int)p_72375_1_.posX >> 4, (int)p_72375_1_.posZ >> 4);
    }
    
    public int getEntityViewDistance() {
        return PlayerManager.getFurthestViewableBlock(this.getViewDistance());
    }
    
    public NBTTagCompound readPlayerDataFromFile(final EntityPlayerMP p_72380_1_) {
        final NBTTagCompound var2 = this.mcServer.worldServers[0].getWorldInfo().getPlayerNBTTagCompound();
        NBTTagCompound var3;
        if (p_72380_1_.getCommandSenderName().equals(this.mcServer.getServerOwner()) && var2 != null) {
            p_72380_1_.readFromNBT(var2);
            var3 = var2;
            ServerConfigurationManager.logger.debug("loading single player");
        }
        else {
            var3 = this.playerNBTManagerObj.readPlayerData(p_72380_1_);
        }
        return var3;
    }
    
    protected void writePlayerData(final EntityPlayerMP p_72391_1_) {
        this.playerNBTManagerObj.writePlayerData(p_72391_1_);
        final StatisticsFile var2 = this.field_148547_k.get(p_72391_1_.getUniqueID());
        if (var2 != null) {
            var2.func_150883_b();
        }
    }
    
    public void playerLoggedIn(final EntityPlayerMP p_72377_1_) {
        this.func_148540_a(new S38PacketPlayerListItem(p_72377_1_.getCommandSenderName(), true, 1000));
        this.playerEntityList.add(p_72377_1_);
        final WorldServer var2 = this.mcServer.worldServerForDimension(p_72377_1_.dimension);
        var2.spawnEntityInWorld(p_72377_1_);
        this.func_72375_a(p_72377_1_, null);
        for (int var3 = 0; var3 < this.playerEntityList.size(); ++var3) {
            final EntityPlayerMP var4 = this.playerEntityList.get(var3);
            p_72377_1_.playerNetServerHandler.sendPacket(new S38PacketPlayerListItem(var4.getCommandSenderName(), true, var4.ping));
        }
    }
    
    public void serverUpdateMountedMovingPlayer(final EntityPlayerMP p_72358_1_) {
        p_72358_1_.getServerForPlayer().getPlayerManager().updateMountedMovingPlayer(p_72358_1_);
    }
    
    public void playerLoggedOut(final EntityPlayerMP p_72367_1_) {
        p_72367_1_.triggerAchievement(StatList.leaveGameStat);
        this.writePlayerData(p_72367_1_);
        final WorldServer var2 = p_72367_1_.getServerForPlayer();
        if (p_72367_1_.ridingEntity != null) {
            var2.removePlayerEntityDangerously(p_72367_1_.ridingEntity);
            ServerConfigurationManager.logger.debug("removing player mount");
        }
        var2.removeEntity(p_72367_1_);
        var2.getPlayerManager().removePlayer(p_72367_1_);
        this.playerEntityList.remove(p_72367_1_);
        this.field_148547_k.remove(p_72367_1_.getUniqueID());
        this.func_148540_a(new S38PacketPlayerListItem(p_72367_1_.getCommandSenderName(), false, 9999));
    }
    
    public String func_148542_a(final SocketAddress p_148542_1_, final GameProfile p_148542_2_) {
        if (this.bannedPlayers.func_152702_a(p_148542_2_)) {
            final UserListBansEntry var5 = (UserListBansEntry)this.bannedPlayers.func_152683_b(p_148542_2_);
            String var6 = "You are banned from this server!\nReason: " + var5.getBanReason();
            if (var5.getBanEndDate() != null) {
                var6 = var6 + "\nYour ban will be removed on " + ServerConfigurationManager.dateFormat.format(var5.getBanEndDate());
            }
            return var6;
        }
        if (!this.func_152607_e(p_148542_2_)) {
            return "You are not white-listed on this server!";
        }
        if (this.bannedIPs.func_152708_a(p_148542_1_)) {
            final IPBanEntry var7 = this.bannedIPs.func_152709_b(p_148542_1_);
            String var6 = "Your IP address is banned from this server!\nReason: " + var7.getBanReason();
            if (var7.getBanEndDate() != null) {
                var6 = var6 + "\nYour ban will be removed on " + ServerConfigurationManager.dateFormat.format(var7.getBanEndDate());
            }
            return var6;
        }
        return (this.playerEntityList.size() >= this.maxPlayers) ? "The server is full!" : null;
    }
    
    public EntityPlayerMP func_148545_a(final GameProfile p_148545_1_) {
        final UUID var2 = EntityPlayer.func_146094_a(p_148545_1_);
        final ArrayList var3 = Lists.newArrayList();
        for (int var4 = 0; var4 < this.playerEntityList.size(); ++var4) {
            final EntityPlayerMP var5 = this.playerEntityList.get(var4);
            if (var5.getUniqueID().equals(var2)) {
                var3.add(var5);
            }
        }
        final Iterator var6 = var3.iterator();
        while (var6.hasNext()) {
            final EntityPlayerMP var5 = var6.next();
            var5.playerNetServerHandler.kickPlayerFromServer("You logged in from another location");
        }
        Object var7;
        if (this.mcServer.isDemo()) {
            var7 = new DemoWorldManager(this.mcServer.worldServerForDimension(0));
        }
        else {
            var7 = new ItemInWorldManager(this.mcServer.worldServerForDimension(0));
        }
        return new EntityPlayerMP(this.mcServer, this.mcServer.worldServerForDimension(0), p_148545_1_, (ItemInWorldManager)var7);
    }
    
    public EntityPlayerMP respawnPlayer(final EntityPlayerMP p_72368_1_, final int p_72368_2_, final boolean p_72368_3_) {
        p_72368_1_.getServerForPlayer().getEntityTracker().removePlayerFromTrackers(p_72368_1_);
        p_72368_1_.getServerForPlayer().getEntityTracker().removeEntityFromAllTrackingPlayers(p_72368_1_);
        p_72368_1_.getServerForPlayer().getPlayerManager().removePlayer(p_72368_1_);
        this.playerEntityList.remove(p_72368_1_);
        this.mcServer.worldServerForDimension(p_72368_1_.dimension).removePlayerEntityDangerously(p_72368_1_);
        final ChunkCoordinates var4 = p_72368_1_.getBedLocation();
        final boolean var5 = p_72368_1_.isSpawnForced();
        p_72368_1_.dimension = p_72368_2_;
        Object var6;
        if (this.mcServer.isDemo()) {
            var6 = new DemoWorldManager(this.mcServer.worldServerForDimension(p_72368_1_.dimension));
        }
        else {
            var6 = new ItemInWorldManager(this.mcServer.worldServerForDimension(p_72368_1_.dimension));
        }
        final EntityPlayerMP var7 = new EntityPlayerMP(this.mcServer, this.mcServer.worldServerForDimension(p_72368_1_.dimension), p_72368_1_.getGameProfile(), (ItemInWorldManager)var6);
        var7.playerNetServerHandler = p_72368_1_.playerNetServerHandler;
        var7.clonePlayer(p_72368_1_, p_72368_3_);
        var7.setEntityId(p_72368_1_.getEntityId());
        final WorldServer var8 = this.mcServer.worldServerForDimension(p_72368_1_.dimension);
        this.func_72381_a(var7, p_72368_1_, var8);
        if (var4 != null) {
            final ChunkCoordinates var9 = EntityPlayer.verifyRespawnCoordinates(this.mcServer.worldServerForDimension(p_72368_1_.dimension), var4, var5);
            if (var9 != null) {
                var7.setLocationAndAngles(var9.posX + 0.5f, var9.posY + 0.1f, var9.posZ + 0.5f, 0.0f, 0.0f);
                var7.setSpawnChunk(var4, var5);
            }
            else {
                var7.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(0, 0.0f));
            }
        }
        var8.theChunkProviderServer.loadChunk((int)var7.posX >> 4, (int)var7.posZ >> 4);
        while (!var8.getCollidingBoundingBoxes(var7, var7.boundingBox).isEmpty()) {
            var7.setPosition(var7.posX, var7.posY + 1.0, var7.posZ);
        }
        var7.playerNetServerHandler.sendPacket(new S07PacketRespawn(var7.dimension, var7.worldObj.difficultySetting, var7.worldObj.getWorldInfo().getTerrainType(), var7.theItemInWorldManager.getGameType()));
        final ChunkCoordinates var9 = var8.getSpawnPoint();
        var7.playerNetServerHandler.setPlayerLocation(var7.posX, var7.posY, var7.posZ, var7.rotationYaw, var7.rotationPitch);
        var7.playerNetServerHandler.sendPacket(new S05PacketSpawnPosition(var9.posX, var9.posY, var9.posZ));
        var7.playerNetServerHandler.sendPacket(new S1FPacketSetExperience(var7.experience, var7.experienceTotal, var7.experienceLevel));
        this.updateTimeAndWeatherForPlayer(var7, var8);
        var8.getPlayerManager().addPlayer(var7);
        var8.spawnEntityInWorld(var7);
        this.playerEntityList.add(var7);
        var7.addSelfToInternalCraftingInventory();
        var7.setHealth(var7.getHealth());
        return var7;
    }
    
    public void transferPlayerToDimension(final EntityPlayerMP p_72356_1_, final int p_72356_2_) {
        final int var3 = p_72356_1_.dimension;
        final WorldServer var4 = this.mcServer.worldServerForDimension(p_72356_1_.dimension);
        p_72356_1_.dimension = p_72356_2_;
        final WorldServer var5 = this.mcServer.worldServerForDimension(p_72356_1_.dimension);
        p_72356_1_.playerNetServerHandler.sendPacket(new S07PacketRespawn(p_72356_1_.dimension, p_72356_1_.worldObj.difficultySetting, p_72356_1_.worldObj.getWorldInfo().getTerrainType(), p_72356_1_.theItemInWorldManager.getGameType()));
        var4.removePlayerEntityDangerously(p_72356_1_);
        p_72356_1_.isDead = false;
        this.transferEntityToWorld(p_72356_1_, var3, var4, var5);
        this.func_72375_a(p_72356_1_, var4);
        p_72356_1_.playerNetServerHandler.setPlayerLocation(p_72356_1_.posX, p_72356_1_.posY, p_72356_1_.posZ, p_72356_1_.rotationYaw, p_72356_1_.rotationPitch);
        p_72356_1_.theItemInWorldManager.setWorld(var5);
        this.updateTimeAndWeatherForPlayer(p_72356_1_, var5);
        this.syncPlayerInventory(p_72356_1_);
        for (final PotionEffect var7 : p_72356_1_.getActivePotionEffects()) {
            p_72356_1_.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(p_72356_1_.getEntityId(), var7));
        }
    }
    
    public void transferEntityToWorld(final Entity p_82448_1_, final int p_82448_2_, final WorldServer p_82448_3_, final WorldServer p_82448_4_) {
        double var5 = p_82448_1_.posX;
        double var6 = p_82448_1_.posZ;
        final double var7 = 8.0;
        final double var8 = p_82448_1_.posX;
        final double var9 = p_82448_1_.posY;
        final double var10 = p_82448_1_.posZ;
        final float var11 = p_82448_1_.rotationYaw;
        p_82448_3_.theProfiler.startSection("moving");
        if (p_82448_1_.dimension == -1) {
            var5 /= var7;
            var6 /= var7;
            p_82448_1_.setLocationAndAngles(var5, p_82448_1_.posY, var6, p_82448_1_.rotationYaw, p_82448_1_.rotationPitch);
            if (p_82448_1_.isEntityAlive()) {
                p_82448_3_.updateEntityWithOptionalForce(p_82448_1_, false);
            }
        }
        else if (p_82448_1_.dimension == 0) {
            var5 *= var7;
            var6 *= var7;
            p_82448_1_.setLocationAndAngles(var5, p_82448_1_.posY, var6, p_82448_1_.rotationYaw, p_82448_1_.rotationPitch);
            if (p_82448_1_.isEntityAlive()) {
                p_82448_3_.updateEntityWithOptionalForce(p_82448_1_, false);
            }
        }
        else {
            ChunkCoordinates var12;
            if (p_82448_2_ == 1) {
                var12 = p_82448_4_.getSpawnPoint();
            }
            else {
                var12 = p_82448_4_.getEntrancePortalLocation();
            }
            var5 = var12.posX;
            p_82448_1_.posY = var12.posY;
            var6 = var12.posZ;
            p_82448_1_.setLocationAndAngles(var5, p_82448_1_.posY, var6, 90.0f, 0.0f);
            if (p_82448_1_.isEntityAlive()) {
                p_82448_3_.updateEntityWithOptionalForce(p_82448_1_, false);
            }
        }
        p_82448_3_.theProfiler.endSection();
        if (p_82448_2_ != 1) {
            p_82448_3_.theProfiler.startSection("placing");
            var5 = MathHelper.clamp_int((int)var5, -29999872, 29999872);
            var6 = MathHelper.clamp_int((int)var6, -29999872, 29999872);
            if (p_82448_1_.isEntityAlive()) {
                p_82448_1_.setLocationAndAngles(var5, p_82448_1_.posY, var6, p_82448_1_.rotationYaw, p_82448_1_.rotationPitch);
                p_82448_4_.getDefaultTeleporter().placeInPortal(p_82448_1_, var8, var9, var10, var11);
                p_82448_4_.spawnEntityInWorld(p_82448_1_);
                p_82448_4_.updateEntityWithOptionalForce(p_82448_1_, false);
            }
            p_82448_3_.theProfiler.endSection();
        }
        p_82448_1_.setWorld(p_82448_4_);
    }
    
    public void sendPlayerInfoToAllPlayers() {
        if (++this.playerPingIndex > 600) {
            this.playerPingIndex = 0;
        }
        if (this.playerPingIndex < this.playerEntityList.size()) {
            final EntityPlayerMP var1 = this.playerEntityList.get(this.playerPingIndex);
            this.func_148540_a(new S38PacketPlayerListItem(var1.getCommandSenderName(), true, var1.ping));
        }
    }
    
    public void func_148540_a(final Packet p_148540_1_) {
        for (int var2 = 0; var2 < this.playerEntityList.size(); ++var2) {
            this.playerEntityList.get(var2).playerNetServerHandler.sendPacket(p_148540_1_);
        }
    }
    
    public void func_148537_a(final Packet p_148537_1_, final int p_148537_2_) {
        for (int var3 = 0; var3 < this.playerEntityList.size(); ++var3) {
            final EntityPlayerMP var4 = this.playerEntityList.get(var3);
            if (var4.dimension == p_148537_2_) {
                var4.playerNetServerHandler.sendPacket(p_148537_1_);
            }
        }
    }
    
    public String func_152609_b(final boolean p_152609_1_) {
        String var2 = "";
        final ArrayList var3 = Lists.newArrayList((Iterable)this.playerEntityList);
        for (int var4 = 0; var4 < var3.size(); ++var4) {
            if (var4 > 0) {
                var2 += ", ";
            }
            var2 += var3.get(var4).getCommandSenderName();
            if (p_152609_1_) {
                var2 = var2 + " (" + var3.get(var4).getUniqueID().toString() + ")";
            }
        }
        return var2;
    }
    
    public String[] getAllUsernames() {
        final String[] var1 = new String[this.playerEntityList.size()];
        for (int var2 = 0; var2 < this.playerEntityList.size(); ++var2) {
            var1[var2] = this.playerEntityList.get(var2).getCommandSenderName();
        }
        return var1;
    }
    
    public GameProfile[] func_152600_g() {
        final GameProfile[] var1 = new GameProfile[this.playerEntityList.size()];
        for (int var2 = 0; var2 < this.playerEntityList.size(); ++var2) {
            var1[var2] = this.playerEntityList.get(var2).getGameProfile();
        }
        return var1;
    }
    
    public UserListBans func_152608_h() {
        return this.bannedPlayers;
    }
    
    public BanList getBannedIPs() {
        return this.bannedIPs;
    }
    
    public void func_152605_a(final GameProfile p_152605_1_) {
        this.ops.func_152687_a(new UserListOpsEntry(p_152605_1_, this.mcServer.func_110455_j()));
    }
    
    public void func_152610_b(final GameProfile p_152610_1_) {
        this.ops.func_152684_c(p_152610_1_);
    }
    
    public boolean func_152607_e(final GameProfile p_152607_1_) {
        return !this.whiteListEnforced || this.ops.func_152692_d(p_152607_1_) || this.whiteListedPlayers.func_152692_d(p_152607_1_);
    }
    
    public boolean func_152596_g(final GameProfile p_152596_1_) {
        return this.ops.func_152692_d(p_152596_1_) || (this.mcServer.isSinglePlayer() && this.mcServer.worldServers[0].getWorldInfo().areCommandsAllowed() && this.mcServer.getServerOwner().equalsIgnoreCase(p_152596_1_.getName())) || this.commandsAllowedForAll;
    }
    
    public EntityPlayerMP func_152612_a(final String p_152612_1_) {
        for (final EntityPlayerMP var3 : this.playerEntityList) {
            if (var3.getCommandSenderName().equalsIgnoreCase(p_152612_1_)) {
                return var3;
            }
        }
        return null;
    }
    
    public List findPlayers(final ChunkCoordinates p_82449_1_, final int p_82449_2_, final int p_82449_3_, int p_82449_4_, final int p_82449_5_, final int p_82449_6_, final int p_82449_7_, final Map p_82449_8_, String p_82449_9_, String p_82449_10_, final World p_82449_11_) {
        if (this.playerEntityList.isEmpty()) {
            return Collections.emptyList();
        }
        Object var12 = new ArrayList();
        final boolean var13 = p_82449_4_ < 0;
        final boolean var14 = p_82449_9_ != null && p_82449_9_.startsWith("!");
        final boolean var15 = p_82449_10_ != null && p_82449_10_.startsWith("!");
        final int var16 = p_82449_2_ * p_82449_2_;
        final int var17 = p_82449_3_ * p_82449_3_;
        p_82449_4_ = MathHelper.abs_int(p_82449_4_);
        if (var14) {
            p_82449_9_ = p_82449_9_.substring(1);
        }
        if (var15) {
            p_82449_10_ = p_82449_10_.substring(1);
        }
        for (int var18 = 0; var18 < this.playerEntityList.size(); ++var18) {
            final EntityPlayerMP var19 = this.playerEntityList.get(var18);
            if ((p_82449_11_ == null || var19.worldObj == p_82449_11_) && (p_82449_9_ == null || var14 != p_82449_9_.equalsIgnoreCase(var19.getCommandSenderName()))) {
                if (p_82449_10_ != null) {
                    final Team var20 = var19.getTeam();
                    final String var21 = (var20 == null) ? "" : var20.getRegisteredName();
                    if (var15 == p_82449_10_.equalsIgnoreCase(var21)) {
                        continue;
                    }
                }
                if (p_82449_1_ != null && (p_82449_2_ > 0 || p_82449_3_ > 0)) {
                    final float var22 = p_82449_1_.getDistanceSquaredToChunkCoordinates(var19.getPlayerCoordinates());
                    if (p_82449_2_ > 0 && var22 < var16) {
                        continue;
                    }
                    if (p_82449_3_ > 0 && var22 > var17) {
                        continue;
                    }
                }
                if (this.func_96457_a(var19, p_82449_8_) && (p_82449_5_ == WorldSettings.GameType.NOT_SET.getID() || p_82449_5_ == var19.theItemInWorldManager.getGameType().getID()) && (p_82449_6_ <= 0 || var19.experienceLevel >= p_82449_6_) && var19.experienceLevel <= p_82449_7_) {
                    ((List)var12).add(var19);
                }
            }
        }
        if (p_82449_1_ != null) {
            Collections.sort((List<Object>)var12, new PlayerPositionComparator(p_82449_1_));
        }
        if (var13) {
            Collections.reverse((List<?>)var12);
        }
        if (p_82449_4_ > 0) {
            var12 = ((List)var12).subList(0, Math.min(p_82449_4_, ((List)var12).size()));
        }
        return (List)var12;
    }
    
    private boolean func_96457_a(final EntityPlayer p_96457_1_, final Map p_96457_2_) {
        if (p_96457_2_ != null && p_96457_2_.size() != 0) {
            for (final Map.Entry var4 : p_96457_2_.entrySet()) {
                String var5 = var4.getKey();
                boolean var6 = false;
                if (var5.endsWith("_min") && var5.length() > 4) {
                    var6 = true;
                    var5 = var5.substring(0, var5.length() - 4);
                }
                final Scoreboard var7 = p_96457_1_.getWorldScoreboard();
                final ScoreObjective var8 = var7.getObjective(var5);
                if (var8 == null) {
                    return false;
                }
                final Score var9 = p_96457_1_.getWorldScoreboard().func_96529_a(p_96457_1_.getCommandSenderName(), var8);
                final int var10 = var9.getScorePoints();
                if (var10 < var4.getValue() && var6) {
                    return false;
                }
                if (var10 > var4.getValue() && !var6) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }
    
    public void func_148541_a(final double p_148541_1_, final double p_148541_3_, final double p_148541_5_, final double p_148541_7_, final int p_148541_9_, final Packet p_148541_10_) {
        this.func_148543_a(null, p_148541_1_, p_148541_3_, p_148541_5_, p_148541_7_, p_148541_9_, p_148541_10_);
    }
    
    public void func_148543_a(final EntityPlayer p_148543_1_, final double p_148543_2_, final double p_148543_4_, final double p_148543_6_, final double p_148543_8_, final int p_148543_10_, final Packet p_148543_11_) {
        for (int var12 = 0; var12 < this.playerEntityList.size(); ++var12) {
            final EntityPlayerMP var13 = this.playerEntityList.get(var12);
            if (var13 != p_148543_1_ && var13.dimension == p_148543_10_) {
                final double var14 = p_148543_2_ - var13.posX;
                final double var15 = p_148543_4_ - var13.posY;
                final double var16 = p_148543_6_ - var13.posZ;
                if (var14 * var14 + var15 * var15 + var16 * var16 < p_148543_8_ * p_148543_8_) {
                    var13.playerNetServerHandler.sendPacket(p_148543_11_);
                }
            }
        }
    }
    
    public void saveAllPlayerData() {
        for (int var1 = 0; var1 < this.playerEntityList.size(); ++var1) {
            this.writePlayerData(this.playerEntityList.get(var1));
        }
    }
    
    public void func_152601_d(final GameProfile p_152601_1_) {
        this.whiteListedPlayers.func_152687_a(new UserListWhitelistEntry(p_152601_1_));
    }
    
    public void func_152597_c(final GameProfile p_152597_1_) {
        this.whiteListedPlayers.func_152684_c(p_152597_1_);
    }
    
    public UserListWhitelist func_152599_k() {
        return this.whiteListedPlayers;
    }
    
    public String[] func_152598_l() {
        return this.whiteListedPlayers.func_152685_a();
    }
    
    public UserListOps func_152603_m() {
        return this.ops;
    }
    
    public String[] func_152606_n() {
        return this.ops.func_152685_a();
    }
    
    public void loadWhiteList() {
    }
    
    public void updateTimeAndWeatherForPlayer(final EntityPlayerMP p_72354_1_, final WorldServer p_72354_2_) {
        p_72354_1_.playerNetServerHandler.sendPacket(new S03PacketTimeUpdate(p_72354_2_.getTotalWorldTime(), p_72354_2_.getWorldTime(), p_72354_2_.getGameRules().getGameRuleBooleanValue("doDaylightCycle")));
        if (p_72354_2_.isRaining()) {
            p_72354_1_.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(1, 0.0f));
            p_72354_1_.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(7, p_72354_2_.getRainStrength(1.0f)));
            p_72354_1_.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(8, p_72354_2_.getWeightedThunderStrength(1.0f)));
        }
    }
    
    public void syncPlayerInventory(final EntityPlayerMP p_72385_1_) {
        p_72385_1_.sendContainerToPlayer(p_72385_1_.inventoryContainer);
        p_72385_1_.setPlayerHealthUpdated();
        p_72385_1_.playerNetServerHandler.sendPacket(new S09PacketHeldItemChange(p_72385_1_.inventory.currentItem));
    }
    
    public int getCurrentPlayerCount() {
        return this.playerEntityList.size();
    }
    
    public int getMaxPlayers() {
        return this.maxPlayers;
    }
    
    public String[] getAvailablePlayerDat() {
        return this.mcServer.worldServers[0].getSaveHandler().getSaveHandler().getAvailablePlayerDat();
    }
    
    public void setWhiteListEnabled(final boolean p_72371_1_) {
        this.whiteListEnforced = p_72371_1_;
    }
    
    public List getPlayerList(final String p_72382_1_) {
        final ArrayList var2 = new ArrayList();
        for (final EntityPlayerMP var4 : this.playerEntityList) {
            if (var4.getPlayerIP().equals(p_72382_1_)) {
                var2.add(var4);
            }
        }
        return var2;
    }
    
    public int getViewDistance() {
        return this.viewDistance;
    }
    
    public MinecraftServer getServerInstance() {
        return this.mcServer;
    }
    
    public NBTTagCompound getHostPlayerData() {
        return null;
    }
    
    public void func_152604_a(final WorldSettings.GameType p_152604_1_) {
        this.gameType = p_152604_1_;
    }
    
    private void func_72381_a(final EntityPlayerMP p_72381_1_, final EntityPlayerMP p_72381_2_, final World p_72381_3_) {
        if (p_72381_2_ != null) {
            p_72381_1_.theItemInWorldManager.setGameType(p_72381_2_.theItemInWorldManager.getGameType());
        }
        else if (this.gameType != null) {
            p_72381_1_.theItemInWorldManager.setGameType(this.gameType);
        }
        p_72381_1_.theItemInWorldManager.initializeGameType(p_72381_3_.getWorldInfo().getGameType());
    }
    
    public void setCommandsAllowedForAll(final boolean p_72387_1_) {
        this.commandsAllowedForAll = p_72387_1_;
    }
    
    public void removeAllPlayers() {
        for (int var1 = 0; var1 < this.playerEntityList.size(); ++var1) {
            this.playerEntityList.get(var1).playerNetServerHandler.kickPlayerFromServer("Server closed");
        }
    }
    
    public void func_148544_a(final IChatComponent p_148544_1_, final boolean p_148544_2_) {
        this.mcServer.addChatMessage(p_148544_1_);
        this.func_148540_a(new S02PacketChat(p_148544_1_, p_148544_2_));
    }
    
    public void func_148539_a(final IChatComponent p_148539_1_) {
        this.func_148544_a(p_148539_1_, true);
    }
    
    public StatisticsFile func_152602_a(final EntityPlayer p_152602_1_) {
        final UUID var2 = p_152602_1_.getUniqueID();
        StatisticsFile var3 = (var2 == null) ? null : this.field_148547_k.get(var2);
        if (var3 == null) {
            final File var4 = new File(this.mcServer.worldServerForDimension(0).getSaveHandler().getWorldDirectory(), "stats");
            final File var5 = new File(var4, var2.toString() + ".json");
            if (!var5.exists()) {
                final File var6 = new File(var4, p_152602_1_.getCommandSenderName() + ".json");
                if (var6.exists() && var6.isFile()) {
                    var6.renameTo(var5);
                }
            }
            var3 = new StatisticsFile(this.mcServer, var5);
            var3.func_150882_a();
            this.field_148547_k.put(var2, var3);
        }
        return var3;
    }
    
    public void func_152611_a(final int p_152611_1_) {
        this.viewDistance = p_152611_1_;
        if (this.mcServer.worldServers != null) {
            for (final WorldServer var5 : this.mcServer.worldServers) {
                if (var5 != null) {
                    var5.getPlayerManager().func_152622_a(p_152611_1_);
                }
            }
        }
    }
    
    static {
        field_152613_a = new File("banned-players.json");
        field_152614_b = new File("banned-ips.json");
        field_152615_c = new File("ops.json");
        field_152616_d = new File("whitelist.json");
        logger = LogManager.getLogger();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    }
}
