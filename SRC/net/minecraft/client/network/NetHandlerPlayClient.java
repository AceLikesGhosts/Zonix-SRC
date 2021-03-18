package net.minecraft.client.network;

import net.minecraft.network.play.*;
import net.minecraft.client.*;
import com.google.common.base.*;
import io.netty.util.concurrent.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.block.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import us.zonix.client.*;
import com.mojang.authlib.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.entity.*;
import java.io.*;
import net.minecraft.world.chunk.*;
import net.minecraft.realms.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.creativetab.*;
import net.minecraft.inventory.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.world.storage.*;
import net.minecraft.client.stream.*;
import net.minecraft.stats.*;
import java.util.*;
import net.minecraft.potion.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.village.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.*;
import io.netty.buffer.*;
import net.minecraft.scoreboard.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.ai.attributes.*;
import org.apache.logging.log4j.*;

public class NetHandlerPlayClient implements INetHandlerPlayClient
{
    private static final Logger logger;
    private final NetworkManager netManager;
    private Minecraft gameController;
    private WorldClient clientWorldController;
    private boolean doneLoadingTerrain;
    public MapStorage mapStorageOrigin;
    private Map playerInfoMap;
    public List playerInfoList;
    public int currentServerMaxPlayers;
    private GuiScreen guiScreenServer;
    private boolean field_147308_k;
    private Random avRandomizer;
    private static final String __OBFID = "CL_00000878";
    
    public NetHandlerPlayClient(final Minecraft p_i45061_1_, final GuiScreen p_i45061_2_, final NetworkManager p_i45061_3_) {
        this.mapStorageOrigin = new MapStorage(null);
        this.playerInfoMap = new HashMap();
        this.playerInfoList = new ArrayList();
        this.currentServerMaxPlayers = 20;
        this.field_147308_k = false;
        this.avRandomizer = new Random();
        this.gameController = p_i45061_1_;
        this.guiScreenServer = p_i45061_2_;
        this.netManager = p_i45061_3_;
    }
    
    public void cleanup() {
        this.clientWorldController = null;
    }
    
    @Override
    public void onNetworkTick() {
    }
    
    @Override
    public void handleJoinGame(final S01PacketJoinGame p_147282_1_) {
        this.gameController.playerController = new PlayerControllerMP(this.gameController, this);
        this.clientWorldController = new WorldClient(this, new WorldSettings(0L, p_147282_1_.func_149198_e(), false, p_147282_1_.func_149195_d(), p_147282_1_.func_149196_i()), p_147282_1_.func_149194_f(), p_147282_1_.func_149192_g(), this.gameController.mcProfiler);
        this.clientWorldController.isClient = true;
        this.gameController.loadWorld(this.clientWorldController);
        this.gameController.thePlayer.dimension = p_147282_1_.func_149194_f();
        this.gameController.displayGuiScreen(new GuiDownloadTerrain(this));
        this.gameController.thePlayer.setEntityId(p_147282_1_.func_149197_c());
        this.currentServerMaxPlayers = p_147282_1_.func_149193_h();
        this.gameController.playerController.setGameType(p_147282_1_.func_149198_e());
        this.gameController.gameSettings.sendSettingsToServer();
        this.netManager.scheduleOutboundPacket(new C17PacketCustomPayload("MC|Brand", ClientBrandRetriever.getClientModName().getBytes(Charsets.UTF_8)), new GenericFutureListener[0]);
    }
    
    @Override
    public void handleSpawnObject(final S0EPacketSpawnObject p_147235_1_) {
        final double var2 = p_147235_1_.func_148997_d() / 32.0;
        final double var3 = p_147235_1_.func_148998_e() / 32.0;
        final double var4 = p_147235_1_.func_148994_f() / 32.0;
        Object var5 = null;
        if (p_147235_1_.func_148993_l() == 10) {
            var5 = EntityMinecart.createMinecart(this.clientWorldController, var2, var3, var4, p_147235_1_.func_149009_m());
        }
        else if (p_147235_1_.func_148993_l() == 90) {
            final Entity var6 = this.clientWorldController.getEntityByID(p_147235_1_.func_149009_m());
            if (var6 instanceof EntityPlayer) {
                var5 = new EntityFishHook(this.clientWorldController, var2, var3, var4, (EntityPlayer)var6);
            }
            p_147235_1_.func_149002_g(0);
        }
        else if (p_147235_1_.func_148993_l() == 60) {
            var5 = new EntityArrow(this.clientWorldController, var2, var3, var4);
        }
        else if (p_147235_1_.func_148993_l() == 61) {
            var5 = new EntitySnowball(this.clientWorldController, var2, var3, var4);
        }
        else if (p_147235_1_.func_148993_l() == 71) {
            var5 = new EntityItemFrame(this.clientWorldController, (int)var2, (int)var3, (int)var4, p_147235_1_.func_149009_m());
            p_147235_1_.func_149002_g(0);
        }
        else if (p_147235_1_.func_148993_l() == 77) {
            var5 = new EntityLeashKnot(this.clientWorldController, (int)var2, (int)var3, (int)var4);
            p_147235_1_.func_149002_g(0);
        }
        else if (p_147235_1_.func_148993_l() == 65) {
            var5 = new EntityEnderPearl(this.clientWorldController, var2, var3, var4);
        }
        else if (p_147235_1_.func_148993_l() == 72) {
            var5 = new EntityEnderEye(this.clientWorldController, var2, var3, var4);
        }
        else if (p_147235_1_.func_148993_l() == 76) {
            var5 = new EntityFireworkRocket(this.clientWorldController, var2, var3, var4, null);
        }
        else if (p_147235_1_.func_148993_l() == 63) {
            var5 = new EntityLargeFireball(this.clientWorldController, var2, var3, var4, p_147235_1_.func_149010_g() / 8000.0, p_147235_1_.func_149004_h() / 8000.0, p_147235_1_.func_148999_i() / 8000.0);
            p_147235_1_.func_149002_g(0);
        }
        else if (p_147235_1_.func_148993_l() == 64) {
            var5 = new EntitySmallFireball(this.clientWorldController, var2, var3, var4, p_147235_1_.func_149010_g() / 8000.0, p_147235_1_.func_149004_h() / 8000.0, p_147235_1_.func_148999_i() / 8000.0);
            p_147235_1_.func_149002_g(0);
        }
        else if (p_147235_1_.func_148993_l() == 66) {
            var5 = new EntityWitherSkull(this.clientWorldController, var2, var3, var4, p_147235_1_.func_149010_g() / 8000.0, p_147235_1_.func_149004_h() / 8000.0, p_147235_1_.func_148999_i() / 8000.0);
            p_147235_1_.func_149002_g(0);
        }
        else if (p_147235_1_.func_148993_l() == 62) {
            var5 = new EntityEgg(this.clientWorldController, var2, var3, var4);
        }
        else if (p_147235_1_.func_148993_l() == 73) {
            var5 = new EntityPotion(this.clientWorldController, var2, var3, var4, p_147235_1_.func_149009_m());
            p_147235_1_.func_149002_g(0);
        }
        else if (p_147235_1_.func_148993_l() == 75) {
            var5 = new EntityExpBottle(this.clientWorldController, var2, var3, var4);
            p_147235_1_.func_149002_g(0);
        }
        else if (p_147235_1_.func_148993_l() == 1) {
            var5 = new EntityBoat(this.clientWorldController, var2, var3, var4);
        }
        else if (p_147235_1_.func_148993_l() == 50) {
            var5 = new EntityTNTPrimed(this.clientWorldController, var2, var3, var4, null);
        }
        else if (p_147235_1_.func_148993_l() == 51) {
            var5 = new EntityEnderCrystal(this.clientWorldController, var2, var3, var4);
        }
        else if (p_147235_1_.func_148993_l() == 2) {
            var5 = new EntityItem(this.clientWorldController, var2, var3, var4);
        }
        else if (p_147235_1_.func_148993_l() == 70) {
            var5 = new EntityFallingBlock(this.clientWorldController, var2, var3, var4, Block.getBlockById(p_147235_1_.func_149009_m() & 0xFFFF), p_147235_1_.func_149009_m() >> 16);
            p_147235_1_.func_149002_g(0);
        }
        if (var5 != null) {
            ((Entity)var5).serverPosX = p_147235_1_.func_148997_d();
            ((Entity)var5).serverPosY = p_147235_1_.func_148998_e();
            ((Entity)var5).serverPosZ = p_147235_1_.func_148994_f();
            ((Entity)var5).rotationPitch = p_147235_1_.func_149008_j() * 360 / 256.0f;
            ((Entity)var5).rotationYaw = p_147235_1_.func_149006_k() * 360 / 256.0f;
            final Entity[] var7 = ((Entity)var5).getParts();
            if (var7 != null) {
                final int var8 = p_147235_1_.func_149001_c() - ((Entity)var5).getEntityId();
                for (int var9 = 0; var9 < var7.length; ++var9) {
                    var7[var9].setEntityId(var7[var9].getEntityId() + var8);
                }
            }
            ((Entity)var5).setEntityId(p_147235_1_.func_149001_c());
            this.clientWorldController.addEntityToWorld(p_147235_1_.func_149001_c(), (Entity)var5);
            if (p_147235_1_.func_149009_m() > 0) {
                if (p_147235_1_.func_148993_l() == 60) {
                    final Entity var10 = this.clientWorldController.getEntityByID(p_147235_1_.func_149009_m());
                    if (var10 instanceof EntityLivingBase) {
                        final EntityArrow var11 = (EntityArrow)var5;
                        var11.shootingEntity = var10;
                    }
                }
                ((Entity)var5).setVelocity(p_147235_1_.func_149010_g() / 8000.0, p_147235_1_.func_149004_h() / 8000.0, p_147235_1_.func_148999_i() / 8000.0);
            }
        }
    }
    
    @Override
    public void handleSpawnExperienceOrb(final S11PacketSpawnExperienceOrb p_147286_1_) {
        final EntityXPOrb var2 = new EntityXPOrb(this.clientWorldController, p_147286_1_.func_148984_d(), p_147286_1_.func_148983_e(), p_147286_1_.func_148982_f(), p_147286_1_.func_148986_g());
        var2.serverPosX = p_147286_1_.func_148984_d();
        var2.serverPosY = p_147286_1_.func_148983_e();
        var2.serverPosZ = p_147286_1_.func_148982_f();
        var2.rotationYaw = 0.0f;
        var2.rotationPitch = 0.0f;
        var2.setEntityId(p_147286_1_.func_148985_c());
        this.clientWorldController.addEntityToWorld(p_147286_1_.func_148985_c(), var2);
    }
    
    @Override
    public void handleSpawnGlobalEntity(final S2CPacketSpawnGlobalEntity p_147292_1_) {
        final double var2 = p_147292_1_.func_149051_d() / 32.0;
        final double var3 = p_147292_1_.func_149050_e() / 32.0;
        final double var4 = p_147292_1_.func_149049_f() / 32.0;
        EntityLightningBolt var5 = null;
        if (p_147292_1_.func_149053_g() == 1) {
            var5 = new EntityLightningBolt(this.clientWorldController, var2, var3, var4);
        }
        if (var5 != null) {
            var5.serverPosX = p_147292_1_.func_149051_d();
            var5.serverPosY = p_147292_1_.func_149050_e();
            var5.serverPosZ = p_147292_1_.func_149049_f();
            var5.rotationYaw = 0.0f;
            var5.rotationPitch = 0.0f;
            var5.setEntityId(p_147292_1_.func_149052_c());
            this.clientWorldController.addWeatherEffect(var5);
        }
    }
    
    @Override
    public void handleSpawnPainting(final S10PacketSpawnPainting p_147288_1_) {
        final EntityPainting var2 = new EntityPainting(this.clientWorldController, p_147288_1_.func_148964_d(), p_147288_1_.func_148963_e(), p_147288_1_.func_148962_f(), p_147288_1_.func_148966_g(), p_147288_1_.func_148961_h());
        this.clientWorldController.addEntityToWorld(p_147288_1_.func_148965_c(), var2);
    }
    
    @Override
    public void handleEntityVelocity(final S12PacketEntityVelocity p_147244_1_) {
        final Entity var2 = this.clientWorldController.getEntityByID(p_147244_1_.func_149412_c());
        if (var2 != null) {
            var2.setVelocity(p_147244_1_.func_149411_d() / 8000.0, p_147244_1_.func_149410_e() / 8000.0, p_147244_1_.func_149409_f() / 8000.0);
        }
    }
    
    @Override
    public void handleEntityMetadata(final S1CPacketEntityMetadata p_147284_1_) {
        final Entity var2 = this.clientWorldController.getEntityByID(p_147284_1_.func_149375_d());
        if (var2 != null && p_147284_1_.func_149376_c() != null) {
            var2.getDataWatcher().updateWatchedObjectsFromList(p_147284_1_.func_149376_c());
        }
    }
    
    @Override
    public void handleSpawnPlayer(final S0CPacketSpawnPlayer p_147237_1_) {
        final double var2 = p_147237_1_.func_148942_f() / 32.0;
        final double var3 = p_147237_1_.func_148949_g() / 32.0;
        final double var4 = p_147237_1_.func_148946_h() / 32.0;
        final float var5 = p_147237_1_.func_148941_i() * 360 / 256.0f;
        final float var6 = p_147237_1_.func_148945_j() * 360 / 256.0f;
        final GameProfile var7 = p_147237_1_.func_148948_e();
        final EntityOtherPlayerMP entityOtherPlayerMP3;
        final EntityOtherPlayerMP entityOtherPlayerMP2;
        final EntityOtherPlayerMP entityOtherPlayerMP;
        final EntityOtherPlayerMP var8 = entityOtherPlayerMP = (entityOtherPlayerMP2 = (entityOtherPlayerMP3 = new EntityOtherPlayerMP(this.gameController.theWorld, p_147237_1_.func_148948_e())));
        final int func_148942_f = p_147237_1_.func_148942_f();
        entityOtherPlayerMP.serverPosX = func_148942_f;
        final double n = func_148942_f;
        entityOtherPlayerMP2.lastTickPosX = n;
        entityOtherPlayerMP3.prevPosX = n;
        final EntityOtherPlayerMP entityOtherPlayerMP4 = var8;
        final EntityOtherPlayerMP entityOtherPlayerMP5 = var8;
        final EntityOtherPlayerMP entityOtherPlayerMP6 = var8;
        final int func_148949_g = p_147237_1_.func_148949_g();
        entityOtherPlayerMP6.serverPosY = func_148949_g;
        final double n2 = func_148949_g;
        entityOtherPlayerMP5.lastTickPosY = n2;
        entityOtherPlayerMP4.prevPosY = n2;
        final EntityOtherPlayerMP entityOtherPlayerMP7 = var8;
        final EntityOtherPlayerMP entityOtherPlayerMP8 = var8;
        final EntityOtherPlayerMP entityOtherPlayerMP9 = var8;
        final int func_148946_h = p_147237_1_.func_148946_h();
        entityOtherPlayerMP9.serverPosZ = func_148946_h;
        final double n3 = func_148946_h;
        entityOtherPlayerMP8.lastTickPosZ = n3;
        entityOtherPlayerMP7.prevPosZ = n3;
        final int var9 = p_147237_1_.func_148947_k();
        if (var9 == 0) {
            var8.inventory.mainInventory[var8.inventory.currentItem] = null;
        }
        else {
            var8.inventory.mainInventory[var8.inventory.currentItem] = new ItemStack(Item.getItemById(var9), 1, 0);
        }
        var8.setPositionAndRotation(var2, var3, var4, var5, var6);
        this.clientWorldController.addEntityToWorld(p_147237_1_.func_148943_d(), var8);
        final List var10 = p_147237_1_.func_148944_c();
        if (var10 != null) {
            var8.getDataWatcher().updateWatchedObjectsFromList(var10);
        }
        Client.getInstance().getCapeManager().onSpawn(p_147237_1_.func_148948_e().getId());
    }
    
    @Override
    public void handleEntityTeleport(final S18PacketEntityTeleport p_147275_1_) {
        final Entity var2 = this.clientWorldController.getEntityByID(p_147275_1_.func_149451_c());
        if (var2 != null) {
            var2.serverPosX = p_147275_1_.func_149449_d();
            var2.serverPosY = p_147275_1_.func_149448_e();
            var2.serverPosZ = p_147275_1_.func_149446_f();
            final double var3 = var2.serverPosX / 32.0;
            final double var4 = var2.serverPosY / 32.0 + 0.015625;
            final double var5 = var2.serverPosZ / 32.0;
            final float var6 = p_147275_1_.func_149450_g() * 360 / 256.0f;
            final float var7 = p_147275_1_.func_149447_h() * 360 / 256.0f;
            var2.setPositionAndRotation2(var3, var4, var5, var6, var7, 3);
        }
    }
    
    @Override
    public void handleHeldItemChange(final S09PacketHeldItemChange p_147257_1_) {
        if (p_147257_1_.func_149385_c() >= 0 && p_147257_1_.func_149385_c() < InventoryPlayer.getHotbarSize()) {
            this.gameController.thePlayer.inventory.currentItem = p_147257_1_.func_149385_c();
        }
    }
    
    @Override
    public void handleEntityMovement(final S14PacketEntity p_147259_1_) {
        final Entity var2 = p_147259_1_.func_149065_a(this.clientWorldController);
        if (var2 != null) {
            final Entity entity = var2;
            entity.serverPosX += p_147259_1_.func_149062_c();
            final Entity entity2 = var2;
            entity2.serverPosY += p_147259_1_.func_149061_d();
            final Entity entity3 = var2;
            entity3.serverPosZ += p_147259_1_.func_149064_e();
            final double var3 = var2.serverPosX / 32.0;
            final double var4 = var2.serverPosY / 32.0;
            final double var5 = var2.serverPosZ / 32.0;
            final float var6 = p_147259_1_.func_149060_h() ? (p_147259_1_.func_149066_f() * 360 / 256.0f) : var2.rotationYaw;
            final float var7 = p_147259_1_.func_149060_h() ? (p_147259_1_.func_149063_g() * 360 / 256.0f) : var2.rotationPitch;
            var2.setPositionAndRotation2(var3, var4, var5, var6, var7, 3);
        }
    }
    
    @Override
    public void handleEntityHeadLook(final S19PacketEntityHeadLook p_147267_1_) {
        final Entity var2 = p_147267_1_.func_149381_a(this.clientWorldController);
        if (var2 != null) {
            final float var3 = p_147267_1_.func_149380_c() * 360 / 256.0f;
            var2.setRotationYawHead(var3);
        }
    }
    
    @Override
    public void handleDestroyEntities(final S13PacketDestroyEntities p_147238_1_) {
        for (int var2 = 0; var2 < p_147238_1_.func_149098_c().length; ++var2) {
            final int id = p_147238_1_.func_149098_c()[var2];
            final Entity entity = this.clientWorldController.getEntityByID(id);
            if (entity instanceof EntityPlayer) {
                Client.getInstance().getCapeManager().onDestroy(entity.getUniqueID());
            }
            this.clientWorldController.removeEntityFromWorld(id);
        }
    }
    
    @Override
    public void handlePlayerPosLook(final S08PacketPlayerPosLook p_147258_1_) {
        final EntityClientPlayerMP var2 = this.gameController.thePlayer;
        final double var3 = p_147258_1_.func_148932_c();
        final double var4 = p_147258_1_.func_148928_d();
        final double var5 = p_147258_1_.func_148933_e();
        final float var6 = p_147258_1_.func_148931_f();
        final float var7 = p_147258_1_.func_148930_g();
        var2.ySize = 0.0f;
        final EntityClientPlayerMP entityClientPlayerMP = var2;
        final EntityClientPlayerMP entityClientPlayerMP2 = var2;
        final EntityClientPlayerMP entityClientPlayerMP3 = var2;
        final double motionX = 0.0;
        entityClientPlayerMP3.motionZ = motionX;
        entityClientPlayerMP2.motionY = motionX;
        entityClientPlayerMP.motionX = motionX;
        var2.setPositionAndRotation(var3, var4, var5, var6, var7);
        this.netManager.scheduleOutboundPacket(new C03PacketPlayer.C06PacketPlayerPosLook(var2.posX, var2.boundingBox.minY, var2.posY, var2.posZ, p_147258_1_.func_148931_f(), p_147258_1_.func_148930_g(), p_147258_1_.func_148929_h()), new GenericFutureListener[0]);
        if (!this.doneLoadingTerrain) {
            this.gameController.thePlayer.prevPosX = this.gameController.thePlayer.posX;
            this.gameController.thePlayer.prevPosY = this.gameController.thePlayer.posY;
            this.gameController.thePlayer.prevPosZ = this.gameController.thePlayer.posZ;
            this.doneLoadingTerrain = true;
            this.gameController.displayGuiScreen(null);
        }
    }
    
    @Override
    public void handleMultiBlockChange(final S22PacketMultiBlockChange p_147287_1_) {
        final int var2 = p_147287_1_.func_148920_c().chunkXPos * 16;
        final int var3 = p_147287_1_.func_148920_c().chunkZPos * 16;
        if (p_147287_1_.func_148921_d() != null) {
            final DataInputStream var4 = new DataInputStream(new ByteArrayInputStream(p_147287_1_.func_148921_d()));
            try {
                for (int var5 = 0; var5 < p_147287_1_.func_148922_e(); ++var5) {
                    final short var6 = var4.readShort();
                    final short var7 = var4.readShort();
                    final int var8 = var7 >> 4 & 0xFFF;
                    final int var9 = var7 & 0xF;
                    final int var10 = var6 >> 12 & 0xF;
                    final int var11 = var6 >> 8 & 0xF;
                    final int var12 = var6 & 0xFF;
                    this.clientWorldController.func_147492_c(var10 + var2, var12, var11 + var3, Block.getBlockById(var8), var9);
                }
            }
            catch (IOException ex) {}
        }
    }
    
    @Override
    public void handleChunkData(final S21PacketChunkData p_147263_1_) {
        if (p_147263_1_.func_149274_i()) {
            if (p_147263_1_.func_149276_g() == 0) {
                this.clientWorldController.doPreChunk(p_147263_1_.func_149273_e(), p_147263_1_.func_149271_f(), false);
                return;
            }
            this.clientWorldController.doPreChunk(p_147263_1_.func_149273_e(), p_147263_1_.func_149271_f(), true);
        }
        this.clientWorldController.invalidateBlockReceiveRegion(p_147263_1_.func_149273_e() << 4, 0, p_147263_1_.func_149271_f() << 4, (p_147263_1_.func_149273_e() << 4) + 15, 256, (p_147263_1_.func_149271_f() << 4) + 15);
        final Chunk var2 = this.clientWorldController.getChunkFromChunkCoords(p_147263_1_.func_149273_e(), p_147263_1_.func_149271_f());
        var2.fillChunk(p_147263_1_.func_149272_d(), p_147263_1_.func_149276_g(), p_147263_1_.func_149270_h(), p_147263_1_.func_149274_i());
        this.clientWorldController.markBlockRangeForRenderUpdate(p_147263_1_.func_149273_e() << 4, 0, p_147263_1_.func_149271_f() << 4, (p_147263_1_.func_149273_e() << 4) + 15, 256, (p_147263_1_.func_149271_f() << 4) + 15);
        if (!p_147263_1_.func_149274_i() || !(this.clientWorldController.provider instanceof WorldProviderSurface)) {
            var2.resetRelightChecks();
        }
    }
    
    @Override
    public void handleBlockChange(final S23PacketBlockChange p_147234_1_) {
        this.clientWorldController.func_147492_c(p_147234_1_.func_148879_d(), p_147234_1_.func_148878_e(), p_147234_1_.func_148877_f(), p_147234_1_.func_148880_c(), p_147234_1_.func_148881_g());
    }
    
    @Override
    public void handleDisconnect(final S40PacketDisconnect p_147253_1_) {
        this.netManager.closeChannel(p_147253_1_.func_149165_c());
    }
    
    @Override
    public void onDisconnect(final IChatComponent p_147231_1_) {
        this.gameController.loadWorld(null);
        if (this.guiScreenServer != null) {
            if (this.guiScreenServer instanceof GuiScreenRealmsProxy) {
                this.gameController.displayGuiScreen(new DisconnectedOnlineScreen(((GuiScreenRealmsProxy)this.guiScreenServer).func_154321_a(), "disconnect.lost", p_147231_1_).getProxy());
            }
            else {
                this.gameController.displayGuiScreen(new GuiDisconnected(this.guiScreenServer, "disconnect.lost", p_147231_1_));
            }
        }
        else {
            this.gameController.displayGuiScreen(new GuiDisconnected(new GuiMultiplayer(new GuiMainMenu()), "disconnect.lost", p_147231_1_));
        }
    }
    
    public void addToSendQueue(final Packet packet) {
        if (packet instanceof C02PacketUseEntity) {
            final Entity entity = ((C02PacketUseEntity)packet).func_149564_a(Minecraft.getMinecraft().theWorld);
            if (entity != null && ((C02PacketUseEntity)packet).func_149565_c() == C02PacketUseEntity.Action.ATTACK) {
                Minecraft.getMinecraft().thePlayer.lastAttacked = entity.getEntityId();
                Minecraft.getMinecraft().thePlayer.lastAttackTime = System.currentTimeMillis();
            }
        }
        this.netManager.scheduleOutboundPacket(packet, new GenericFutureListener[0]);
    }
    
    @Override
    public void handleCollectItem(final S0DPacketCollectItem p_147246_1_) {
        final Entity var2 = this.clientWorldController.getEntityByID(p_147246_1_.func_149354_c());
        Object var3 = this.clientWorldController.getEntityByID(p_147246_1_.func_149353_d());
        if (var3 == null) {
            var3 = this.gameController.thePlayer;
        }
        if (var2 != null) {
            if (var2 instanceof EntityXPOrb) {
                this.clientWorldController.playSoundAtEntity(var2, "random.orb", 0.2f, ((this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            }
            else {
                this.clientWorldController.playSoundAtEntity(var2, "random.pop", 0.2f, ((this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            }
            this.gameController.effectRenderer.addEffect(new EntityPickupFX(this.gameController.theWorld, var2, (Entity)var3, -0.5f));
            this.clientWorldController.removeEntityFromWorld(p_147246_1_.func_149354_c());
        }
    }
    
    @Override
    public void handleChat(final S02PacketChat p_147251_1_) {
        this.gameController.ingameGUI.getChatGUI().func_146227_a(p_147251_1_.func_148915_c());
    }
    
    @Override
    public void handleAnimation(final S0BPacketAnimation p_147279_1_) {
        final Entity var2 = this.clientWorldController.getEntityByID(p_147279_1_.func_148978_c());
        if (var2 != null) {
            if (p_147279_1_.func_148977_d() == 0) {
                final EntityLivingBase var3 = (EntityLivingBase)var2;
                var3.swingItem();
            }
            else if (p_147279_1_.func_148977_d() == 1) {
                var2.performHurtAnimation();
            }
            else if (p_147279_1_.func_148977_d() == 2) {
                final EntityPlayer var4 = (EntityPlayer)var2;
                var4.wakeUpPlayer(false, false, false);
            }
            else if (p_147279_1_.func_148977_d() == 4) {
                this.gameController.effectRenderer.addEffect(new EntityCrit2FX(this.gameController.theWorld, var2));
            }
            else if (p_147279_1_.func_148977_d() == 5) {
                final EntityCrit2FX var5 = new EntityCrit2FX(this.gameController.theWorld, var2, "magicCrit");
                this.gameController.effectRenderer.addEffect(var5);
            }
        }
    }
    
    @Override
    public void handleUseBed(final S0APacketUseBed p_147278_1_) {
        p_147278_1_.func_149091_a(this.clientWorldController).sleepInBedAt(p_147278_1_.func_149092_c(), p_147278_1_.func_149090_d(), p_147278_1_.func_149089_e());
    }
    
    @Override
    public void handleSpawnMob(final S0FPacketSpawnMob p_147281_1_) {
        final double var2 = p_147281_1_.func_149023_f() / 32.0;
        final double var3 = p_147281_1_.func_149034_g() / 32.0;
        final double var4 = p_147281_1_.func_149029_h() / 32.0;
        final float var5 = p_147281_1_.func_149028_l() * 360 / 256.0f;
        final float var6 = p_147281_1_.func_149030_m() * 360 / 256.0f;
        final EntityLivingBase var7 = (EntityLivingBase)EntityList.createEntityByID(p_147281_1_.func_149025_e(), this.gameController.theWorld);
        var7.serverPosX = p_147281_1_.func_149023_f();
        var7.serverPosY = p_147281_1_.func_149034_g();
        var7.serverPosZ = p_147281_1_.func_149029_h();
        var7.rotationYawHead = p_147281_1_.func_149032_n() * 360 / 256.0f;
        final Entity[] var8 = var7.getParts();
        if (var8 != null) {
            final int var9 = p_147281_1_.func_149024_d() - var7.getEntityId();
            for (int var10 = 0; var10 < var8.length; ++var10) {
                var8[var10].setEntityId(var8[var10].getEntityId() + var9);
            }
        }
        var7.setEntityId(p_147281_1_.func_149024_d());
        var7.setPositionAndRotation(var2, var3, var4, var5, var6);
        var7.motionX = p_147281_1_.func_149026_i() / 8000.0f;
        var7.motionY = p_147281_1_.func_149033_j() / 8000.0f;
        var7.motionZ = p_147281_1_.func_149031_k() / 8000.0f;
        this.clientWorldController.addEntityToWorld(p_147281_1_.func_149024_d(), var7);
        final List var11 = p_147281_1_.func_149027_c();
        if (var11 != null) {
            var7.getDataWatcher().updateWatchedObjectsFromList(var11);
        }
    }
    
    @Override
    public void handleTimeUpdate(final S03PacketTimeUpdate p_147285_1_) {
        this.gameController.theWorld.func_82738_a(p_147285_1_.func_149366_c());
        this.gameController.theWorld.setWorldTime(p_147285_1_.func_149365_d());
    }
    
    @Override
    public void handleSpawnPosition(final S05PacketSpawnPosition p_147271_1_) {
        this.gameController.thePlayer.setSpawnChunk(new ChunkCoordinates(p_147271_1_.func_149360_c(), p_147271_1_.func_149359_d(), p_147271_1_.func_149358_e()), true);
        this.gameController.theWorld.getWorldInfo().setSpawnPosition(p_147271_1_.func_149360_c(), p_147271_1_.func_149359_d(), p_147271_1_.func_149358_e());
    }
    
    @Override
    public void handleEntityAttach(final S1BPacketEntityAttach p_147243_1_) {
        Object var2 = this.clientWorldController.getEntityByID(p_147243_1_.func_149403_d());
        final Entity var3 = this.clientWorldController.getEntityByID(p_147243_1_.func_149402_e());
        if (p_147243_1_.func_149404_c() == 0) {
            boolean var4 = false;
            if (p_147243_1_.func_149403_d() == this.gameController.thePlayer.getEntityId()) {
                var2 = this.gameController.thePlayer;
                if (var3 instanceof EntityBoat) {
                    ((EntityBoat)var3).setIsBoatEmpty(false);
                }
                var4 = (((Entity)var2).ridingEntity == null && var3 != null);
            }
            else if (var3 instanceof EntityBoat) {
                ((EntityBoat)var3).setIsBoatEmpty(true);
            }
            if (var2 == null) {
                return;
            }
            ((Entity)var2).mountEntity(var3);
            if (var4) {
                final GameSettings var5 = this.gameController.gameSettings;
                this.gameController.ingameGUI.func_110326_a(I18n.format("mount.onboard", GameSettings.getKeyDisplayString(var5.keyBindSneak.getKeyCode())), false);
            }
        }
        else if (p_147243_1_.func_149404_c() == 1 && var2 != null && var2 instanceof EntityLiving) {
            if (var3 != null) {
                ((EntityLiving)var2).setLeashedToEntity(var3, false);
            }
            else {
                ((EntityLiving)var2).clearLeashed(false, false);
            }
        }
    }
    
    @Override
    public void handleEntityStatus(final S19PacketEntityStatus packet) {
        final Entity var2 = packet.func_149161_a(this.clientWorldController);
        if (var2 != null) {
            var2.handleHealthUpdate(packet.func_149160_c());
            if (packet.func_149160_c() != 2) {
                return;
            }
            final Minecraft mc = Minecraft.getMinecraft();
            final EntityClientPlayerMP player = mc.thePlayer;
            if (player.lastAttacked == var2.getEntityId()) {
                player.lastAttacked = -1;
                if (System.currentTimeMillis() - player.lastAttackTime > 2000L) {
                    player.lastAttackTime = 0L;
                    player.combo = 0;
                    return;
                }
                if (player.lastCombo == var2.getEntityId()) {
                    final EntityClientPlayerMP entityClientPlayerMP = player;
                    ++entityClientPlayerMP.combo;
                }
                else {
                    player.combo = 1;
                }
                player.lastComboTime = System.currentTimeMillis();
                player.lastCombo = var2.getEntityId();
            }
            else if (var2.getEntityId() == player.getEntityId()) {
                player.combo = 0;
            }
        }
    }
    
    @Override
    public void handleUpdateHealth(final S06PacketUpdateHealth p_147249_1_) {
        this.gameController.thePlayer.setPlayerSPHealth(p_147249_1_.func_149332_c());
        this.gameController.thePlayer.getFoodStats().setFoodLevel(p_147249_1_.func_149330_d());
        this.gameController.thePlayer.getFoodStats().setFoodSaturationLevel(p_147249_1_.func_149331_e());
    }
    
    @Override
    public void handleSetExperience(final S1FPacketSetExperience p_147295_1_) {
        this.gameController.thePlayer.setXPStats(p_147295_1_.func_149397_c(), p_147295_1_.func_149396_d(), p_147295_1_.func_149395_e());
    }
    
    @Override
    public void handleRespawn(final S07PacketRespawn p_147280_1_) {
        if (p_147280_1_.func_149082_c() != this.gameController.thePlayer.dimension) {
            this.doneLoadingTerrain = false;
            final Scoreboard var2 = this.clientWorldController.getScoreboard();
            (this.clientWorldController = new WorldClient(this, new WorldSettings(0L, p_147280_1_.func_149083_e(), false, this.gameController.theWorld.getWorldInfo().isHardcoreModeEnabled(), p_147280_1_.func_149080_f()), p_147280_1_.func_149082_c(), p_147280_1_.func_149081_d(), this.gameController.mcProfiler)).setWorldScoreboard(var2);
            this.clientWorldController.isClient = true;
            this.gameController.loadWorld(this.clientWorldController);
            this.gameController.thePlayer.dimension = p_147280_1_.func_149082_c();
            this.gameController.displayGuiScreen(new GuiDownloadTerrain(this));
        }
        this.gameController.setDimensionAndSpawnPlayer(p_147280_1_.func_149082_c());
        this.gameController.playerController.setGameType(p_147280_1_.func_149083_e());
    }
    
    @Override
    public void handleExplosion(final S27PacketExplosion p_147283_1_) {
        final Explosion var2 = new Explosion(this.gameController.theWorld, null, p_147283_1_.func_149148_f(), p_147283_1_.func_149143_g(), p_147283_1_.func_149145_h(), p_147283_1_.func_149146_i());
        var2.affectedBlockPositions = p_147283_1_.func_149150_j();
        var2.doExplosionB(true);
        final EntityClientPlayerMP thePlayer = this.gameController.thePlayer;
        thePlayer.motionX += p_147283_1_.func_149149_c();
        final EntityClientPlayerMP thePlayer2 = this.gameController.thePlayer;
        thePlayer2.motionY += p_147283_1_.func_149144_d();
        final EntityClientPlayerMP thePlayer3 = this.gameController.thePlayer;
        thePlayer3.motionZ += p_147283_1_.func_149147_e();
    }
    
    @Override
    public void handleOpenWindow(final S2DPacketOpenWindow p_147265_1_) {
        final EntityClientPlayerMP var2 = this.gameController.thePlayer;
        switch (p_147265_1_.func_148899_d()) {
            case 0: {
                var2.displayGUIChest(new InventoryBasic(p_147265_1_.func_148902_e(), p_147265_1_.func_148900_g(), p_147265_1_.func_148898_f()));
                var2.openContainer.windowId = p_147265_1_.func_148901_c();
                break;
            }
            case 1: {
                var2.displayGUIWorkbench(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ));
                var2.openContainer.windowId = p_147265_1_.func_148901_c();
                break;
            }
            case 2: {
                final TileEntityFurnace var3 = new TileEntityFurnace();
                if (p_147265_1_.func_148900_g()) {
                    var3.func_145951_a(p_147265_1_.func_148902_e());
                }
                var2.func_146101_a(var3);
                var2.openContainer.windowId = p_147265_1_.func_148901_c();
                break;
            }
            case 3: {
                final TileEntityDispenser var4 = new TileEntityDispenser();
                if (p_147265_1_.func_148900_g()) {
                    var4.func_146018_a(p_147265_1_.func_148902_e());
                }
                var2.func_146102_a(var4);
                var2.openContainer.windowId = p_147265_1_.func_148901_c();
                break;
            }
            case 4: {
                var2.displayGUIEnchantment(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ), p_147265_1_.func_148900_g() ? p_147265_1_.func_148902_e() : null);
                var2.openContainer.windowId = p_147265_1_.func_148901_c();
                break;
            }
            case 5: {
                final TileEntityBrewingStand var5 = new TileEntityBrewingStand();
                if (p_147265_1_.func_148900_g()) {
                    var5.func_145937_a(p_147265_1_.func_148902_e());
                }
                var2.func_146098_a(var5);
                var2.openContainer.windowId = p_147265_1_.func_148901_c();
                break;
            }
            case 6: {
                var2.displayGUIMerchant(new NpcMerchant(var2), p_147265_1_.func_148900_g() ? p_147265_1_.func_148902_e() : null);
                var2.openContainer.windowId = p_147265_1_.func_148901_c();
                break;
            }
            case 7: {
                final TileEntityBeacon var6 = new TileEntityBeacon();
                var2.func_146104_a(var6);
                if (p_147265_1_.func_148900_g()) {
                    var6.func_145999_a(p_147265_1_.func_148902_e());
                }
                var2.openContainer.windowId = p_147265_1_.func_148901_c();
                break;
            }
            case 8: {
                var2.displayGUIAnvil(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ));
                var2.openContainer.windowId = p_147265_1_.func_148901_c();
                break;
            }
            case 9: {
                final TileEntityHopper var7 = new TileEntityHopper();
                if (p_147265_1_.func_148900_g()) {
                    var7.func_145886_a(p_147265_1_.func_148902_e());
                }
                var2.func_146093_a(var7);
                var2.openContainer.windowId = p_147265_1_.func_148901_c();
                break;
            }
            case 10: {
                final TileEntityDropper var8 = new TileEntityDropper();
                if (p_147265_1_.func_148900_g()) {
                    var8.func_146018_a(p_147265_1_.func_148902_e());
                }
                var2.func_146102_a(var8);
                var2.openContainer.windowId = p_147265_1_.func_148901_c();
                break;
            }
            case 11: {
                final Entity var9 = this.clientWorldController.getEntityByID(p_147265_1_.func_148897_h());
                if (var9 != null && var9 instanceof EntityHorse) {
                    var2.displayGUIHorse((EntityHorse)var9, new AnimalChest(p_147265_1_.func_148902_e(), p_147265_1_.func_148900_g(), p_147265_1_.func_148898_f()));
                    var2.openContainer.windowId = p_147265_1_.func_148901_c();
                    break;
                }
                break;
            }
        }
    }
    
    @Override
    public void handleSetSlot(final S2FPacketSetSlot p_147266_1_) {
        final EntityClientPlayerMP var2 = this.gameController.thePlayer;
        if (p_147266_1_.func_149175_c() == -1) {
            var2.inventory.setItemStack(p_147266_1_.func_149174_e());
        }
        else {
            boolean var3 = false;
            if (this.gameController.currentScreen instanceof GuiContainerCreative) {
                final GuiContainerCreative var4 = (GuiContainerCreative)this.gameController.currentScreen;
                var3 = (var4.func_147056_g() != CreativeTabs.tabInventory.getTabIndex());
            }
            if (p_147266_1_.func_149175_c() == 0 && p_147266_1_.func_149173_d() >= 36 && p_147266_1_.func_149173_d() < 45) {
                final ItemStack var5 = var2.inventoryContainer.getSlot(p_147266_1_.func_149173_d()).getStack();
                if (p_147266_1_.func_149174_e() != null && (var5 == null || var5.stackSize < p_147266_1_.func_149174_e().stackSize)) {
                    p_147266_1_.func_149174_e().animationsToGo = 5;
                }
                var2.inventoryContainer.putStackInSlot(p_147266_1_.func_149173_d(), p_147266_1_.func_149174_e());
            }
            else if (p_147266_1_.func_149175_c() == var2.openContainer.windowId && (p_147266_1_.func_149175_c() != 0 || !var3)) {
                var2.openContainer.putStackInSlot(p_147266_1_.func_149173_d(), p_147266_1_.func_149174_e());
            }
        }
    }
    
    @Override
    public void handleConfirmTransaction(final S32PacketConfirmTransaction p_147239_1_) {
        Container var2 = null;
        final EntityClientPlayerMP var3 = this.gameController.thePlayer;
        if (p_147239_1_.func_148889_c() == 0) {
            var2 = var3.inventoryContainer;
        }
        else if (p_147239_1_.func_148889_c() == var3.openContainer.windowId) {
            var2 = var3.openContainer;
        }
        if (var2 != null && !p_147239_1_.func_148888_e()) {
            this.addToSendQueue(new C0FPacketConfirmTransaction(p_147239_1_.func_148889_c(), p_147239_1_.func_148890_d(), true));
        }
    }
    
    @Override
    public void handleWindowItems(final S30PacketWindowItems p_147241_1_) {
        final EntityClientPlayerMP var2 = this.gameController.thePlayer;
        if (p_147241_1_.func_148911_c() == 0) {
            var2.inventoryContainer.putStacksInSlots(p_147241_1_.func_148910_d());
        }
        else if (p_147241_1_.func_148911_c() == var2.openContainer.windowId) {
            var2.openContainer.putStacksInSlots(p_147241_1_.func_148910_d());
        }
    }
    
    @Override
    public void handleSignEditorOpen(final S36PacketSignEditorOpen p_147268_1_) {
        Object var2 = this.clientWorldController.getTileEntity(p_147268_1_.func_149129_c(), p_147268_1_.func_149128_d(), p_147268_1_.func_149127_e());
        if (var2 == null) {
            var2 = new TileEntitySign();
            ((TileEntity)var2).setWorldObj(this.clientWorldController);
            ((TileEntity)var2).field_145851_c = p_147268_1_.func_149129_c();
            ((TileEntity)var2).field_145848_d = p_147268_1_.func_149128_d();
            ((TileEntity)var2).field_145849_e = p_147268_1_.func_149127_e();
        }
        this.gameController.thePlayer.func_146100_a((TileEntity)var2);
    }
    
    @Override
    public void handleUpdateSign(final S33PacketUpdateSign p_147248_1_) {
        boolean var2 = false;
        if (this.gameController.theWorld.blockExists(p_147248_1_.func_149346_c(), p_147248_1_.func_149345_d(), p_147248_1_.func_149344_e())) {
            final TileEntity var3 = this.gameController.theWorld.getTileEntity(p_147248_1_.func_149346_c(), p_147248_1_.func_149345_d(), p_147248_1_.func_149344_e());
            if (var3 instanceof TileEntitySign) {
                final TileEntitySign var4 = (TileEntitySign)var3;
                if (var4.func_145914_a()) {
                    for (int var5 = 0; var5 < 4; ++var5) {
                        var4.field_145915_a[var5] = p_147248_1_.func_149347_f()[var5];
                    }
                    var4.onInventoryChanged();
                }
                var2 = true;
            }
        }
        if (!var2 && this.gameController.thePlayer != null) {
            this.gameController.thePlayer.addChatMessage(new ChatComponentText("Unable to locate sign at " + p_147248_1_.func_149346_c() + ", " + p_147248_1_.func_149345_d() + ", " + p_147248_1_.func_149344_e()));
        }
    }
    
    @Override
    public void handleUpdateTileEntity(final S35PacketUpdateTileEntity p_147273_1_) {
        if (this.gameController.theWorld.blockExists(p_147273_1_.func_148856_c(), p_147273_1_.func_148855_d(), p_147273_1_.func_148854_e())) {
            final TileEntity var2 = this.gameController.theWorld.getTileEntity(p_147273_1_.func_148856_c(), p_147273_1_.func_148855_d(), p_147273_1_.func_148854_e());
            if (var2 != null) {
                if (p_147273_1_.func_148853_f() == 1 && var2 instanceof TileEntityMobSpawner) {
                    var2.readFromNBT(p_147273_1_.func_148857_g());
                }
                else if (p_147273_1_.func_148853_f() == 2 && var2 instanceof TileEntityCommandBlock) {
                    var2.readFromNBT(p_147273_1_.func_148857_g());
                }
                else if (p_147273_1_.func_148853_f() == 3 && var2 instanceof TileEntityBeacon) {
                    var2.readFromNBT(p_147273_1_.func_148857_g());
                }
                else if (p_147273_1_.func_148853_f() == 4 && var2 instanceof TileEntitySkull) {
                    var2.readFromNBT(p_147273_1_.func_148857_g());
                }
                else if (p_147273_1_.func_148853_f() == 5 && var2 instanceof TileEntityFlowerPot) {
                    var2.readFromNBT(p_147273_1_.func_148857_g());
                }
            }
        }
    }
    
    @Override
    public void handleWindowProperty(final S31PacketWindowProperty p_147245_1_) {
        final EntityClientPlayerMP var2 = this.gameController.thePlayer;
        if (var2.openContainer != null && var2.openContainer.windowId == p_147245_1_.func_149182_c()) {
            var2.openContainer.updateProgressBar(p_147245_1_.func_149181_d(), p_147245_1_.func_149180_e());
        }
    }
    
    @Override
    public void handleEntityEquipment(final S04PacketEntityEquipment p_147242_1_) {
        final Entity var2 = this.clientWorldController.getEntityByID(p_147242_1_.func_149389_d());
        if (var2 != null) {
            var2.setCurrentItemOrArmor(p_147242_1_.func_149388_e(), p_147242_1_.func_149390_c());
        }
    }
    
    @Override
    public void handleCloseWindow(final S2EPacketCloseWindow p_147276_1_) {
        this.gameController.thePlayer.closeScreenNoPacket();
    }
    
    @Override
    public void handleBlockAction(final S24PacketBlockAction p_147261_1_) {
        this.gameController.theWorld.func_147452_c(p_147261_1_.func_148867_d(), p_147261_1_.func_148866_e(), p_147261_1_.func_148865_f(), p_147261_1_.func_148868_c(), p_147261_1_.func_148869_g(), p_147261_1_.func_148864_h());
    }
    
    @Override
    public void handleBlockBreakAnim(final S25PacketBlockBreakAnim p_147294_1_) {
        this.gameController.theWorld.destroyBlockInWorldPartially(p_147294_1_.func_148845_c(), p_147294_1_.func_148844_d(), p_147294_1_.func_148843_e(), p_147294_1_.func_148842_f(), p_147294_1_.func_148846_g());
    }
    
    @Override
    public void handleMapChunkBulk(final S26PacketMapChunkBulk p_147269_1_) {
        for (int var2 = 0; var2 < p_147269_1_.func_149254_d(); ++var2) {
            final int var3 = p_147269_1_.func_149255_a(var2);
            final int var4 = p_147269_1_.func_149253_b(var2);
            this.clientWorldController.doPreChunk(var3, var4, true);
            this.clientWorldController.invalidateBlockReceiveRegion(var3 << 4, 0, var4 << 4, (var3 << 4) + 15, 256, (var4 << 4) + 15);
            final Chunk var5 = this.clientWorldController.getChunkFromChunkCoords(var3, var4);
            var5.fillChunk(p_147269_1_.func_149256_c(var2), p_147269_1_.func_149252_e()[var2], p_147269_1_.func_149257_f()[var2], true);
            this.clientWorldController.markBlockRangeForRenderUpdate(var3 << 4, 0, var4 << 4, (var3 << 4) + 15, 256, (var4 << 4) + 15);
            if (!(this.clientWorldController.provider instanceof WorldProviderSurface)) {
                var5.resetRelightChecks();
            }
        }
    }
    
    @Override
    public void handleChangeGameState(final S2BPacketChangeGameState p_147252_1_) {
        final EntityClientPlayerMP var2 = this.gameController.thePlayer;
        final int var3 = p_147252_1_.func_149138_c();
        final float var4 = p_147252_1_.func_149137_d();
        final int var5 = MathHelper.floor_float(var4 + 0.5f);
        if (var3 >= 0 && var3 < S2BPacketChangeGameState.field_149142_a.length && S2BPacketChangeGameState.field_149142_a[var3] != null) {
            var2.addChatComponentMessage(new ChatComponentTranslation(S2BPacketChangeGameState.field_149142_a[var3], new Object[0]));
        }
        if (var3 == 1) {
            this.clientWorldController.getWorldInfo().setRaining(true);
            this.clientWorldController.setRainStrength(0.0f);
        }
        else if (var3 == 2) {
            this.clientWorldController.getWorldInfo().setRaining(false);
            this.clientWorldController.setRainStrength(1.0f);
        }
        else if (var3 == 3) {
            this.gameController.playerController.setGameType(WorldSettings.GameType.getByID(var5));
        }
        else if (var3 == 4) {
            this.gameController.displayGuiScreen(new GuiWinGame());
        }
        else if (var3 == 5) {
            final GameSettings var6 = this.gameController.gameSettings;
            if (var4 == 0.0f) {
                this.gameController.displayGuiScreen(new GuiScreenDemo());
            }
            else if (var4 == 101.0f) {
                this.gameController.ingameGUI.getChatGUI().func_146227_a(new ChatComponentTranslation("demo.help.movement", new Object[] { GameSettings.getKeyDisplayString(var6.keyBindForward.getKeyCode()), GameSettings.getKeyDisplayString(var6.keyBindLeft.getKeyCode()), GameSettings.getKeyDisplayString(var6.keyBindBack.getKeyCode()), GameSettings.getKeyDisplayString(var6.keyBindRight.getKeyCode()) }));
            }
            else if (var4 == 102.0f) {
                this.gameController.ingameGUI.getChatGUI().func_146227_a(new ChatComponentTranslation("demo.help.jump", new Object[] { GameSettings.getKeyDisplayString(var6.keyBindJump.getKeyCode()) }));
            }
            else if (var4 == 103.0f) {
                this.gameController.ingameGUI.getChatGUI().func_146227_a(new ChatComponentTranslation("demo.help.inventory", new Object[] { GameSettings.getKeyDisplayString(var6.keyBindInventory.getKeyCode()) }));
            }
        }
        else if (var3 == 6) {
            this.clientWorldController.playSound(var2.posX, var2.posY + var2.getEyeHeight(), var2.posZ, "random.successful_hit", 0.18f, 0.45f, false);
        }
        else if (var3 == 7) {
            this.clientWorldController.setRainStrength(var4);
        }
        else if (var3 == 8) {
            this.clientWorldController.setThunderStrength(var4);
        }
    }
    
    @Override
    public void handleMaps(final S34PacketMaps p_147264_1_) {
        final MapData var2 = ItemMap.func_150912_a(p_147264_1_.func_149188_c(), this.gameController.theWorld);
        var2.updateMPMapData(p_147264_1_.func_149187_d());
        this.gameController.entityRenderer.getMapItemRenderer().func_148246_a(var2);
    }
    
    @Override
    public void handleEffect(final S28PacketEffect p_147277_1_) {
        if (p_147277_1_.func_149244_c()) {
            this.gameController.theWorld.playBroadcastSound(p_147277_1_.func_149242_d(), p_147277_1_.func_149240_f(), p_147277_1_.func_149243_g(), p_147277_1_.func_149239_h(), p_147277_1_.func_149241_e());
        }
        else {
            this.gameController.theWorld.playAuxSFX(p_147277_1_.func_149242_d(), p_147277_1_.func_149240_f(), p_147277_1_.func_149243_g(), p_147277_1_.func_149239_h(), p_147277_1_.func_149241_e());
        }
    }
    
    @Override
    public void handleStatistics(final S37PacketStatistics p_147293_1_) {
        boolean var2 = false;
        for (final Map.Entry var4 : p_147293_1_.func_148974_c().entrySet()) {
            final StatBase var5 = var4.getKey();
            final int var6 = var4.getValue();
            if (var5.isAchievement() && var6 > 0) {
                if (this.field_147308_k && this.gameController.thePlayer.func_146107_m().writeStat(var5) == 0) {
                    final Achievement var7 = (Achievement)var5;
                    this.gameController.guiAchievement.func_146256_a(var7);
                    this.gameController.func_152346_Z().func_152911_a(new MetadataAchievement(var7), 0L);
                    if (var5 == AchievementList.openInventory) {
                        this.gameController.gameSettings.showInventoryAchievementHint = false;
                        this.gameController.gameSettings.saveOptions();
                    }
                }
                var2 = true;
            }
            this.gameController.thePlayer.func_146107_m().func_150873_a(this.gameController.thePlayer, var5, var6);
        }
        if (!this.field_147308_k && !var2 && this.gameController.gameSettings.showInventoryAchievementHint) {
            this.gameController.guiAchievement.func_146255_b(AchievementList.openInventory);
        }
        this.field_147308_k = true;
        if (this.gameController.currentScreen instanceof IProgressMeter) {
            ((IProgressMeter)this.gameController.currentScreen).func_146509_g();
        }
    }
    
    @Override
    public void handleEntityEffect(final S1DPacketEntityEffect p_147260_1_) {
        final Entity var2 = this.clientWorldController.getEntityByID(p_147260_1_.func_149426_d());
        if (var2 instanceof EntityLivingBase) {
            final PotionEffect var3 = new PotionEffect(p_147260_1_.func_149427_e(), p_147260_1_.func_149425_g(), p_147260_1_.func_149428_f());
            var3.setPotionDurationMax(p_147260_1_.func_149429_c());
            ((EntityLivingBase)var2).addPotionEffect(var3);
        }
    }
    
    @Override
    public void handleRemoveEntityEffect(final S1EPacketRemoveEntityEffect p_147262_1_) {
        final Entity var2 = this.clientWorldController.getEntityByID(p_147262_1_.func_149076_c());
        if (var2 instanceof EntityLivingBase) {
            ((EntityLivingBase)var2).removePotionEffectClient(p_147262_1_.func_149075_d());
        }
    }
    
    public GuiPlayerInfo getPlayerInfo(final String uuid) {
        return this.playerInfoMap.get(uuid);
    }
    
    @Override
    public void handlePlayerListItem(final S38PacketPlayerListItem p_147256_1_) {
        GuiPlayerInfo var2 = this.playerInfoMap.get(p_147256_1_.func_149122_c());
        if (var2 == null && p_147256_1_.func_149121_d()) {
            var2 = new GuiPlayerInfo(p_147256_1_.func_149122_c());
            this.playerInfoMap.put(p_147256_1_.func_149122_c(), var2);
            this.playerInfoList.add(var2);
        }
        if (var2 != null && !p_147256_1_.func_149121_d()) {
            this.playerInfoMap.remove(p_147256_1_.func_149122_c());
            this.playerInfoList.remove(var2);
        }
        if (var2 != null && p_147256_1_.func_149121_d()) {
            var2.responseTime = p_147256_1_.func_149120_e();
        }
    }
    
    @Override
    public void handleKeepAlive(final S00PacketKeepAlive p_147272_1_) {
        this.addToSendQueue(new C00PacketKeepAlive(p_147272_1_.func_149134_c()));
    }
    
    @Override
    public void onConnectionStateTransition(final EnumConnectionState p_147232_1_, final EnumConnectionState p_147232_2_) {
        throw new IllegalStateException("Unexpected protocol change!");
    }
    
    @Override
    public void handlePlayerAbilities(final S39PacketPlayerAbilities p_147270_1_) {
        final EntityClientPlayerMP var2 = this.gameController.thePlayer;
        var2.capabilities.isFlying = p_147270_1_.func_149106_d();
        var2.capabilities.isCreativeMode = p_147270_1_.func_149103_f();
        var2.capabilities.disableDamage = p_147270_1_.func_149112_c();
        var2.capabilities.allowFlying = p_147270_1_.func_149105_e();
        var2.capabilities.setFlySpeed(p_147270_1_.func_149101_g());
        var2.capabilities.setPlayerWalkSpeed(p_147270_1_.func_149107_h());
    }
    
    @Override
    public void handleTabComplete(final S3APacketTabComplete p_147274_1_) {
        final String[] var2 = p_147274_1_.func_149630_c();
        if (this.gameController.currentScreen instanceof GuiChat) {
            final GuiChat var3 = (GuiChat)this.gameController.currentScreen;
            var3.func_146406_a(var2);
        }
    }
    
    @Override
    public void handleSoundEffect(final S29PacketSoundEffect p_147255_1_) {
        this.gameController.theWorld.playSound(p_147255_1_.func_149207_d(), p_147255_1_.func_149211_e(), p_147255_1_.func_149210_f(), p_147255_1_.func_149212_c(), p_147255_1_.func_149208_g(), p_147255_1_.func_149209_h(), false);
    }
    
    @Override
    public void handleCustomPayload(final S3FPacketCustomPayload p_147240_1_) {
        if ("MC|TrList".equals(p_147240_1_.func_149169_c())) {
            final ByteBuf var2 = Unpooled.wrappedBuffer(p_147240_1_.func_149168_d());
            try {
                final int var3 = var2.readInt();
                final GuiScreen var4 = this.gameController.currentScreen;
                if (var4 != null && var4 instanceof GuiMerchant && var3 == this.gameController.thePlayer.openContainer.windowId) {
                    final IMerchant var5 = ((GuiMerchant)var4).func_147035_g();
                    final MerchantRecipeList var6 = MerchantRecipeList.func_151390_b(new PacketBuffer(var2));
                    var5.setRecipes(var6);
                }
            }
            catch (IOException var7) {
                NetHandlerPlayClient.logger.error("Couldn't load trade info", (Throwable)var7);
            }
            finally {
                var2.release();
            }
        }
        else if ("MC|Brand".equals(p_147240_1_.func_149169_c())) {
            this.gameController.thePlayer.func_142020_c(new String(p_147240_1_.func_149168_d(), Charsets.UTF_8));
        }
        else if ("MC|RPack".equals(p_147240_1_.func_149169_c())) {
            final String var8 = new String(p_147240_1_.func_149168_d(), Charsets.UTF_8);
            if (this.gameController.func_147104_D() != null && this.gameController.func_147104_D().func_152586_b() == ServerData.ServerResourceMode.ENABLED) {
                this.gameController.getResourcePackRepository().func_148526_a(var8);
            }
            else if (this.gameController.func_147104_D() == null || this.gameController.func_147104_D().func_152586_b() == ServerData.ServerResourceMode.PROMPT) {
                this.gameController.displayGuiScreen(new GuiYesNo(new GuiYesNoCallback() {
                    private static final String __OBFID = "CL_00000879";
                    
                    @Override
                    public void confirmClicked(final boolean p_73878_1_, final int p_73878_2_) {
                        NetHandlerPlayClient.this.gameController = Minecraft.getMinecraft();
                        if (NetHandlerPlayClient.this.gameController.func_147104_D() != null) {
                            NetHandlerPlayClient.this.gameController.func_147104_D().func_152584_a(ServerData.ServerResourceMode.ENABLED);
                            ServerList.func_147414_b(NetHandlerPlayClient.this.gameController.func_147104_D());
                        }
                        if (p_73878_1_) {
                            NetHandlerPlayClient.this.gameController.getResourcePackRepository().func_148526_a(var8);
                        }
                        NetHandlerPlayClient.this.gameController.displayGuiScreen(null);
                    }
                }, I18n.format("multiplayer.texturePrompt.line1", new Object[0]), I18n.format("multiplayer.texturePrompt.line2", new Object[0]), 0));
            }
        }
        else if ("ZonixMC".equals(p_147240_1_.func_149169_c())) {
            try {
                final String payload = new String(p_147240_1_.func_149168_d(), Charsets.UTF_8);
                final String[] split = payload.split(":");
                final String lowerCase = split[0].toLowerCase();
                switch (lowerCase) {
                    case "transfer": {
                        final String ip = split[1];
                        final int port = Integer.parseInt(split[2]);
                        final Minecraft mc = Minecraft.getMinecraft();
                        if (mc.theWorld != null) {
                            mc.theWorld.sendQuittingDisconnectingPacket();
                        }
                        mc.loadWorld(null);
                        mc.displayGuiScreen(new GuiConnecting(mc.currentScreen, mc, ip, port));
                        break;
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void handleScoreboardObjective(final S3BPacketScoreboardObjective p_147291_1_) {
        final Scoreboard var2 = this.clientWorldController.getScoreboard();
        if (p_147291_1_.func_149338_e() == 0) {
            final ScoreObjective var3 = var2.addScoreObjective(p_147291_1_.func_149339_c(), IScoreObjectiveCriteria.field_96641_b);
            var3.setDisplayName(p_147291_1_.func_149337_d());
        }
        else {
            final ScoreObjective var3 = var2.getObjective(p_147291_1_.func_149339_c());
            if (p_147291_1_.func_149338_e() == 1) {
                var2.func_96519_k(var3);
            }
            else if (p_147291_1_.func_149338_e() == 2) {
                var3.setDisplayName(p_147291_1_.func_149337_d());
            }
        }
    }
    
    @Override
    public void handleUpdateScore(final S3CPacketUpdateScore p_147250_1_) {
        final Scoreboard var2 = this.clientWorldController.getScoreboard();
        final ScoreObjective var3 = var2.getObjective(p_147250_1_.func_149321_d());
        if (p_147250_1_.func_149322_f() == 0) {
            final Score var4 = var2.func_96529_a(p_147250_1_.func_149324_c(), var3);
            var4.func_96647_c(p_147250_1_.func_149323_e());
        }
        else if (p_147250_1_.func_149322_f() == 1) {
            var2.func_96515_c(p_147250_1_.func_149324_c());
        }
    }
    
    @Override
    public void handleDisplayScoreboard(final S3DPacketDisplayScoreboard p_147254_1_) {
        final Scoreboard var2 = this.clientWorldController.getScoreboard();
        if (p_147254_1_.func_149370_d().length() == 0) {
            var2.func_96530_a(p_147254_1_.func_149371_c(), null);
        }
        else {
            final ScoreObjective var3 = var2.getObjective(p_147254_1_.func_149370_d());
            var2.func_96530_a(p_147254_1_.func_149371_c(), var3);
        }
    }
    
    @Override
    public void handleTeams(final S3EPacketTeams p_147247_1_) {
        final Scoreboard var2 = this.clientWorldController.getScoreboard();
        ScorePlayerTeam var3;
        if (p_147247_1_.func_149307_h() == 0) {
            var3 = var2.createTeam(p_147247_1_.func_149312_c());
        }
        else {
            var3 = var2.getTeam(p_147247_1_.func_149312_c());
        }
        if (p_147247_1_.func_149307_h() == 0 || p_147247_1_.func_149307_h() == 2) {
            var3.setTeamName(p_147247_1_.func_149306_d());
            var3.setNamePrefix(p_147247_1_.func_149311_e());
            var3.setNameSuffix(p_147247_1_.func_149309_f());
            var3.func_98298_a(p_147247_1_.func_149308_i());
        }
        if (p_147247_1_.func_149307_h() == 0 || p_147247_1_.func_149307_h() == 3) {
            for (final String var5 : p_147247_1_.func_149310_g()) {
                var2.func_151392_a(var5, p_147247_1_.func_149312_c());
            }
        }
        if (p_147247_1_.func_149307_h() == 4) {
            for (final String var5 : p_147247_1_.func_149310_g()) {
                var2.removePlayerFromTeam(var5, var3);
            }
        }
        if (p_147247_1_.func_149307_h() == 1) {
            var2.removeTeam(var3);
        }
    }
    
    @Override
    public void handleParticles(final S2APacketParticles p_147289_1_) {
        if (p_147289_1_.func_149222_k() == 0) {
            final double var2 = p_147289_1_.func_149227_j() * p_147289_1_.func_149221_g();
            final double var3 = p_147289_1_.func_149227_j() * p_147289_1_.func_149224_h();
            final double var4 = p_147289_1_.func_149227_j() * p_147289_1_.func_149223_i();
            this.clientWorldController.spawnParticle(p_147289_1_.func_149228_c(), p_147289_1_.func_149220_d(), p_147289_1_.func_149226_e(), p_147289_1_.func_149225_f(), var2, var3, var4);
        }
        else {
            for (int var5 = 0; var5 < p_147289_1_.func_149222_k(); ++var5) {
                final double var6 = this.avRandomizer.nextGaussian() * p_147289_1_.func_149221_g();
                final double var7 = this.avRandomizer.nextGaussian() * p_147289_1_.func_149224_h();
                final double var8 = this.avRandomizer.nextGaussian() * p_147289_1_.func_149223_i();
                final double var9 = this.avRandomizer.nextGaussian() * p_147289_1_.func_149227_j();
                final double var10 = this.avRandomizer.nextGaussian() * p_147289_1_.func_149227_j();
                final double var11 = this.avRandomizer.nextGaussian() * p_147289_1_.func_149227_j();
                this.clientWorldController.spawnParticle(p_147289_1_.func_149228_c(), p_147289_1_.func_149220_d() + var6, p_147289_1_.func_149226_e() + var7, p_147289_1_.func_149225_f() + var8, var9, var10, var11);
            }
        }
    }
    
    @Override
    public void handleEntityProperties(final S20PacketEntityProperties p_147290_1_) {
        final Entity var2 = this.clientWorldController.getEntityByID(p_147290_1_.func_149442_c());
        if (var2 != null) {
            if (!(var2 instanceof EntityLivingBase)) {
                throw new IllegalStateException("Server tried to update attributes of a non-living entity (actually: " + var2 + ")");
            }
            final BaseAttributeMap var3 = ((EntityLivingBase)var2).getAttributeMap();
            for (final S20PacketEntityProperties.Snapshot var5 : p_147290_1_.func_149441_d()) {
                IAttributeInstance var6 = var3.getAttributeInstanceByName(var5.func_151409_a());
                if (var6 == null) {
                    var6 = var3.registerAttribute(new RangedAttribute(var5.func_151409_a(), 0.0, Double.MIN_NORMAL, Double.MAX_VALUE));
                }
                var6.setBaseValue(var5.func_151410_b());
                var6.removeAllModifiers();
                for (final AttributeModifier var8 : var5.func_151408_c()) {
                    var6.applyModifier(var8);
                }
            }
        }
    }
    
    public NetworkManager getNetworkManager() {
        return this.netManager;
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
