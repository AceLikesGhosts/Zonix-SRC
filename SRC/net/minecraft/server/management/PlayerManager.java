package net.minecraft.server.management;

import net.minecraft.entity.player.*;
import net.minecraft.src.*;
import net.minecraft.util.*;
import java.util.*;
import org.apache.logging.log4j.*;
import net.minecraft.network.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import net.minecraft.network.play.server.*;

public class PlayerManager
{
    private static final Logger field_152627_a;
    private final WorldServer theWorldServer;
    private final List players;
    private final LongHashMap playerInstances;
    private final List chunkWatcherWithPlayers;
    private final List playerInstanceList;
    private int playerViewRadius;
    private long previousTotalWorldTime;
    private final int[][] xzDirectionsConst;
    private static final String __OBFID = "CL_00001434";
    
    public PlayerManager(final WorldServer p_i1176_1_) {
        this.players = new ArrayList();
        this.playerInstances = new LongHashMap();
        this.chunkWatcherWithPlayers = new ArrayList();
        this.playerInstanceList = new ArrayList();
        this.xzDirectionsConst = new int[][] { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
        this.theWorldServer = p_i1176_1_;
        this.func_152622_a(p_i1176_1_.func_73046_m().getConfigurationManager().getViewDistance());
    }
    
    public WorldServer getWorldServer() {
        return this.theWorldServer;
    }
    
    public void updatePlayerInstances() {
        final long var1 = this.theWorldServer.getTotalWorldTime();
        if (var1 - this.previousTotalWorldTime > 8000L) {
            this.previousTotalWorldTime = var1;
            for (int var2 = 0; var2 < this.playerInstanceList.size(); ++var2) {
                final PlayerInstance var3 = this.playerInstanceList.get(var2);
                var3.sendChunkUpdate();
                var3.processChunk();
            }
        }
        else {
            for (int var2 = 0; var2 < this.chunkWatcherWithPlayers.size(); ++var2) {
                final PlayerInstance var3 = this.chunkWatcherWithPlayers.get(var2);
                var3.sendChunkUpdate();
            }
        }
        this.chunkWatcherWithPlayers.clear();
        if (this.players.isEmpty()) {
            final WorldProvider var4 = this.theWorldServer.provider;
            if (!var4.canRespawnHere()) {
                this.theWorldServer.theChunkProviderServer.unloadAllChunks();
            }
        }
    }
    
    public boolean func_152621_a(final int p_152621_1_, final int p_152621_2_) {
        final long var3 = p_152621_1_ + 2147483647L | p_152621_2_ + 2147483647L << 32;
        return this.playerInstances.getValueByKey(var3) != null;
    }
    
    private PlayerInstance getOrCreateChunkWatcher(final int p_72690_1_, final int p_72690_2_, final boolean p_72690_3_) {
        final long var4 = p_72690_1_ + 2147483647L | p_72690_2_ + 2147483647L << 32;
        PlayerInstance var5 = (PlayerInstance)this.playerInstances.getValueByKey(var4);
        if (var5 == null && p_72690_3_) {
            var5 = new PlayerInstance(p_72690_1_, p_72690_2_);
            this.playerInstances.add(var4, var5);
            this.playerInstanceList.add(var5);
        }
        return var5;
    }
    
    public void func_151250_a(final int p_151250_1_, final int p_151250_2_, final int p_151250_3_) {
        final int var4 = p_151250_1_ >> 4;
        final int var5 = p_151250_3_ >> 4;
        final PlayerInstance var6 = this.getOrCreateChunkWatcher(var4, var5, false);
        if (var6 != null) {
            var6.func_151253_a(p_151250_1_ & 0xF, p_151250_2_, p_151250_3_ & 0xF);
        }
    }
    
    public void addPlayer(final EntityPlayerMP p_72683_1_) {
        final int var2 = (int)p_72683_1_.posX >> 4;
        final int var3 = (int)p_72683_1_.posZ >> 4;
        p_72683_1_.managedPosX = p_72683_1_.posX;
        p_72683_1_.managedPosZ = p_72683_1_.posZ;
        for (int var4 = var2 - this.playerViewRadius; var4 <= var2 + this.playerViewRadius; ++var4) {
            for (int var5 = var3 - this.playerViewRadius; var5 <= var3 + this.playerViewRadius; ++var5) {
                this.getOrCreateChunkWatcher(var4, var5, true).addPlayer(p_72683_1_);
            }
        }
        this.players.add(p_72683_1_);
        this.filterChunkLoadQueue(p_72683_1_);
    }
    
    public void filterChunkLoadQueue(final EntityPlayerMP p_72691_1_) {
        final ArrayList var2 = new ArrayList(p_72691_1_.loadedChunks);
        int var3 = 0;
        final int var4 = this.playerViewRadius;
        final int var5 = (int)p_72691_1_.posX >> 4;
        final int var6 = (int)p_72691_1_.posZ >> 4;
        int var7 = 0;
        int var8 = 0;
        ChunkCoordIntPair var9 = this.getOrCreateChunkWatcher(var5, var6, true).chunkLocation;
        p_72691_1_.loadedChunks.clear();
        if (var2.contains(var9)) {
            p_72691_1_.loadedChunks.add(var9);
        }
        for (int var10 = 1; var10 <= var4 * 2; ++var10) {
            for (int var11 = 0; var11 < 2; ++var11) {
                final int[] var12 = this.xzDirectionsConst[var3++ % 4];
                for (int var13 = 0; var13 < var10; ++var13) {
                    var7 += var12[0];
                    var8 += var12[1];
                    var9 = this.getOrCreateChunkWatcher(var5 + var7, var6 + var8, true).chunkLocation;
                    if (var2.contains(var9)) {
                        p_72691_1_.loadedChunks.add(var9);
                    }
                }
            }
        }
        var3 %= 4;
        for (int var10 = 0; var10 < var4 * 2; ++var10) {
            var7 += this.xzDirectionsConst[var3][0];
            var8 += this.xzDirectionsConst[var3][1];
            var9 = this.getOrCreateChunkWatcher(var5 + var7, var6 + var8, true).chunkLocation;
            if (var2.contains(var9)) {
                p_72691_1_.loadedChunks.add(var9);
            }
        }
    }
    
    public void removePlayer(final EntityPlayerMP p_72695_1_) {
        final int var2 = (int)p_72695_1_.managedPosX >> 4;
        final int var3 = (int)p_72695_1_.managedPosZ >> 4;
        for (int var4 = var2 - this.playerViewRadius; var4 <= var2 + this.playerViewRadius; ++var4) {
            for (int var5 = var3 - this.playerViewRadius; var5 <= var3 + this.playerViewRadius; ++var5) {
                final PlayerInstance var6 = this.getOrCreateChunkWatcher(var4, var5, false);
                if (var6 != null) {
                    var6.removePlayer(p_72695_1_);
                }
            }
        }
        this.players.remove(p_72695_1_);
    }
    
    private boolean overlaps(final int p_72684_1_, final int p_72684_2_, final int p_72684_3_, final int p_72684_4_, final int p_72684_5_) {
        final int var6 = p_72684_1_ - p_72684_3_;
        final int var7 = p_72684_2_ - p_72684_4_;
        return var6 >= -p_72684_5_ && var6 <= p_72684_5_ && (var7 >= -p_72684_5_ && var7 <= p_72684_5_);
    }
    
    public void updateMountedMovingPlayer(final EntityPlayerMP p_72685_1_) {
        final int var2 = (int)p_72685_1_.posX >> 4;
        final int var3 = (int)p_72685_1_.posZ >> 4;
        final double var4 = p_72685_1_.managedPosX - p_72685_1_.posX;
        final double var5 = p_72685_1_.managedPosZ - p_72685_1_.posZ;
        final double var6 = var4 * var4 + var5 * var5;
        if (var6 >= 64.0) {
            final int var7 = (int)p_72685_1_.managedPosX >> 4;
            final int var8 = (int)p_72685_1_.managedPosZ >> 4;
            final int var9 = this.playerViewRadius;
            final int var10 = var2 - var7;
            final int var11 = var3 - var8;
            if (var10 != 0 || var11 != 0) {
                WorldServerOF worldServerOf = null;
                if (this.theWorldServer instanceof WorldServerOF) {
                    worldServerOf = (WorldServerOF)this.theWorldServer;
                }
                for (int var12 = var2 - var9; var12 <= var2 + var9; ++var12) {
                    for (int var13 = var3 - var9; var13 <= var3 + var9; ++var13) {
                        if (!this.overlaps(var12, var13, var7, var8, var9)) {
                            this.getOrCreateChunkWatcher(var12, var13, true).addPlayer(p_72685_1_);
                            if (worldServerOf != null) {
                                worldServerOf.addChunkToTickOnce(var12, var13);
                            }
                        }
                        if (!this.overlaps(var12 - var10, var13 - var11, var2, var3, var9)) {
                            final PlayerInstance var14 = this.getOrCreateChunkWatcher(var12 - var10, var13 - var11, false);
                            if (var14 != null) {
                                var14.removePlayer(p_72685_1_);
                            }
                        }
                    }
                }
                this.filterChunkLoadQueue(p_72685_1_);
                p_72685_1_.managedPosX = p_72685_1_.posX;
                p_72685_1_.managedPosZ = p_72685_1_.posZ;
            }
        }
    }
    
    public boolean isPlayerWatchingChunk(final EntityPlayerMP p_72694_1_, final int p_72694_2_, final int p_72694_3_) {
        final PlayerInstance var4 = this.getOrCreateChunkWatcher(p_72694_2_, p_72694_3_, false);
        return var4 != null && var4.playersWatchingChunk.contains(p_72694_1_) && !p_72694_1_.loadedChunks.contains(var4.chunkLocation);
    }
    
    public void func_152622_a(int p_152622_1_) {
        p_152622_1_ = MathHelper.clamp_int(p_152622_1_, 3, 20);
        if (p_152622_1_ != this.playerViewRadius) {
            final int var2 = p_152622_1_ - this.playerViewRadius;
            for (final EntityPlayerMP var4 : this.players) {
                final int var5 = (int)var4.posX >> 4;
                final int var6 = (int)var4.posZ >> 4;
                if (var2 > 0) {
                    for (int var7 = var5 - p_152622_1_; var7 <= var5 + p_152622_1_; ++var7) {
                        for (int var8 = var6 - p_152622_1_; var8 <= var6 + p_152622_1_; ++var8) {
                            final PlayerInstance var9 = this.getOrCreateChunkWatcher(var7, var8, true);
                            if (!var9.playersWatchingChunk.contains(var4)) {
                                var9.addPlayer(var4);
                            }
                        }
                    }
                }
                else {
                    for (int var7 = var5 - this.playerViewRadius; var7 <= var5 + this.playerViewRadius; ++var7) {
                        for (int var8 = var6 - this.playerViewRadius; var8 <= var6 + this.playerViewRadius; ++var8) {
                            if (!this.overlaps(var7, var8, var5, var6, p_152622_1_)) {
                                this.getOrCreateChunkWatcher(var7, var8, true).removePlayer(var4);
                            }
                        }
                    }
                }
            }
            this.playerViewRadius = p_152622_1_;
        }
    }
    
    public static int getFurthestViewableBlock(final int p_72686_0_) {
        return p_72686_0_ * 16 - 16;
    }
    
    static {
        field_152627_a = LogManager.getLogger();
    }
    
    class PlayerInstance
    {
        private final List playersWatchingChunk;
        private final ChunkCoordIntPair chunkLocation;
        private short[] field_151254_d;
        private int numberOfTilesToUpdate;
        private int flagsYAreasToUpdate;
        private long previousWorldTime;
        private static final String __OBFID = "CL_00001435";
        
        public PlayerInstance(final int p_i1518_2_, final int p_i1518_3_) {
            this.playersWatchingChunk = new ArrayList();
            this.field_151254_d = new short[64];
            this.chunkLocation = new ChunkCoordIntPair(p_i1518_2_, p_i1518_3_);
            PlayerManager.this.getWorldServer().theChunkProviderServer.loadChunk(p_i1518_2_, p_i1518_3_);
        }
        
        public void addPlayer(final EntityPlayerMP p_73255_1_) {
            if (this.playersWatchingChunk.contains(p_73255_1_)) {
                PlayerManager.field_152627_a.debug("Failed to add player. {} already is in chunk {}, {}", new Object[] { p_73255_1_, this.chunkLocation.chunkXPos, this.chunkLocation.chunkZPos });
            }
            else {
                if (this.playersWatchingChunk.isEmpty()) {
                    this.previousWorldTime = PlayerManager.this.theWorldServer.getTotalWorldTime();
                }
                this.playersWatchingChunk.add(p_73255_1_);
                p_73255_1_.loadedChunks.add(this.chunkLocation);
            }
        }
        
        public void removePlayer(final EntityPlayerMP p_73252_1_) {
            if (this.playersWatchingChunk.contains(p_73252_1_)) {
                final Chunk var2 = PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkLocation.chunkXPos, this.chunkLocation.chunkZPos);
                if (var2.func_150802_k()) {
                    p_73252_1_.playerNetServerHandler.sendPacket(new S21PacketChunkData(var2, true, 0));
                }
                this.playersWatchingChunk.remove(p_73252_1_);
                p_73252_1_.loadedChunks.remove(this.chunkLocation);
                if (this.playersWatchingChunk.isEmpty()) {
                    final long var3 = this.chunkLocation.chunkXPos + 2147483647L | this.chunkLocation.chunkZPos + 2147483647L << 32;
                    this.increaseInhabitedTime(var2);
                    PlayerManager.this.playerInstances.remove(var3);
                    PlayerManager.this.playerInstanceList.remove(this);
                    if (this.numberOfTilesToUpdate > 0) {
                        PlayerManager.this.chunkWatcherWithPlayers.remove(this);
                    }
                    PlayerManager.this.getWorldServer().theChunkProviderServer.unloadChunksIfNotNearSpawn(this.chunkLocation.chunkXPos, this.chunkLocation.chunkZPos);
                }
            }
        }
        
        public void processChunk() {
            this.increaseInhabitedTime(PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkLocation.chunkXPos, this.chunkLocation.chunkZPos));
        }
        
        private void increaseInhabitedTime(final Chunk p_111196_1_) {
            p_111196_1_.inhabitedTime += PlayerManager.this.theWorldServer.getTotalWorldTime() - this.previousWorldTime;
            this.previousWorldTime = PlayerManager.this.theWorldServer.getTotalWorldTime();
        }
        
        public void func_151253_a(final int p_151253_1_, final int p_151253_2_, final int p_151253_3_) {
            if (this.numberOfTilesToUpdate == 0) {
                PlayerManager.this.chunkWatcherWithPlayers.add(this);
            }
            this.flagsYAreasToUpdate |= 1 << (p_151253_2_ >> 4);
            if (this.numberOfTilesToUpdate < 64) {
                final short var4 = (short)(p_151253_1_ << 12 | p_151253_3_ << 8 | p_151253_2_);
                for (int var5 = 0; var5 < this.numberOfTilesToUpdate; ++var5) {
                    if (this.field_151254_d[var5] == var4) {
                        return;
                    }
                }
                this.field_151254_d[this.numberOfTilesToUpdate++] = var4;
            }
        }
        
        public void func_151251_a(final Packet p_151251_1_) {
            for (int var2 = 0; var2 < this.playersWatchingChunk.size(); ++var2) {
                final EntityPlayerMP var3 = this.playersWatchingChunk.get(var2);
                if (!var3.loadedChunks.contains(this.chunkLocation)) {
                    var3.playerNetServerHandler.sendPacket(p_151251_1_);
                }
            }
        }
        
        public void sendChunkUpdate() {
            if (this.numberOfTilesToUpdate != 0) {
                if (this.numberOfTilesToUpdate == 1) {
                    final int var1 = this.chunkLocation.chunkXPos * 16 + (this.field_151254_d[0] >> 12 & 0xF);
                    final int var2 = this.field_151254_d[0] & 0xFF;
                    final int var3 = this.chunkLocation.chunkZPos * 16 + (this.field_151254_d[0] >> 8 & 0xF);
                    this.func_151251_a(new S23PacketBlockChange(var1, var2, var3, PlayerManager.this.theWorldServer));
                    if (PlayerManager.this.theWorldServer.getBlock(var1, var2, var3).hasTileEntity()) {
                        this.func_151252_a(PlayerManager.this.theWorldServer.getTileEntity(var1, var2, var3));
                    }
                }
                else if (this.numberOfTilesToUpdate == 64) {
                    final int var1 = this.chunkLocation.chunkXPos * 16;
                    final int var2 = this.chunkLocation.chunkZPos * 16;
                    this.func_151251_a(new S21PacketChunkData(PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkLocation.chunkXPos, this.chunkLocation.chunkZPos), false, this.flagsYAreasToUpdate));
                    for (int var3 = 0; var3 < 16; ++var3) {
                        if ((this.flagsYAreasToUpdate & 1 << var3) != 0x0) {
                            final int var4 = var3 << 4;
                            final List var5 = PlayerManager.this.theWorldServer.func_147486_a(var1, var4, var2, var1 + 16, var4 + 16, var2 + 16);
                            for (int var6 = 0; var6 < var5.size(); ++var6) {
                                this.func_151252_a(var5.get(var6));
                            }
                        }
                    }
                }
                else {
                    this.func_151251_a(new S22PacketMultiBlockChange(this.numberOfTilesToUpdate, this.field_151254_d, PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkLocation.chunkXPos, this.chunkLocation.chunkZPos)));
                    for (int var1 = 0; var1 < this.numberOfTilesToUpdate; ++var1) {
                        final int var2 = this.chunkLocation.chunkXPos * 16 + (this.field_151254_d[var1] >> 12 & 0xF);
                        final int var3 = this.field_151254_d[var1] & 0xFF;
                        final int var4 = this.chunkLocation.chunkZPos * 16 + (this.field_151254_d[var1] >> 8 & 0xF);
                        if (PlayerManager.this.theWorldServer.getBlock(var2, var3, var4).hasTileEntity()) {
                            this.func_151252_a(PlayerManager.this.theWorldServer.getTileEntity(var2, var3, var4));
                        }
                    }
                }
                this.numberOfTilesToUpdate = 0;
                this.flagsYAreasToUpdate = 0;
            }
        }
        
        private void func_151252_a(final TileEntity p_151252_1_) {
            if (p_151252_1_ != null) {
                final Packet var2 = p_151252_1_.getDescriptionPacket();
                if (var2 != null) {
                    this.func_151251_a(var2);
                }
            }
        }
    }
}
