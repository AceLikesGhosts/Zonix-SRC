package net.minecraft.network;

import net.minecraft.network.play.*;
import net.minecraft.server.*;
import io.netty.util.concurrent.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import java.util.concurrent.*;
import net.minecraft.crash.*;
import net.minecraft.entity.player.*;
import org.apache.commons.lang3.*;
import net.minecraft.util.*;
import net.minecraft.command.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.server.management.*;
import net.minecraft.stats.*;
import com.google.common.collect.*;
import net.minecraft.network.play.server.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import io.netty.buffer.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import java.io.*;
import net.minecraft.entity.*;
import com.google.common.base.*;
import net.minecraft.inventory.*;
import net.minecraft.command.server.*;
import net.minecraft.tileentity.*;
import org.apache.logging.log4j.*;

public class NetHandlerPlayServer implements INetHandlerPlayServer
{
    private static final Logger logger;
    public final NetworkManager netManager;
    private final MinecraftServer serverController;
    public EntityPlayerMP playerEntity;
    private int networkTickCount;
    private int floatingTickCount;
    private boolean field_147366_g;
    private int field_147378_h;
    private long field_147379_i;
    private static Random field_147376_j;
    private long field_147377_k;
    private int chatSpamThresholdCount;
    private int field_147375_m;
    private IntHashMap field_147372_n;
    private double lastPosX;
    private double lastPosY;
    private double lastPosZ;
    private boolean hasMoved;
    private static final String __OBFID = "CL_00001452";
    
    public NetHandlerPlayServer(final MinecraftServer p_i1530_1_, final NetworkManager p_i1530_2_, final EntityPlayerMP p_i1530_3_) {
        this.field_147372_n = new IntHashMap();
        this.hasMoved = true;
        this.serverController = p_i1530_1_;
        (this.netManager = p_i1530_2_).setNetHandler(this);
        this.playerEntity = p_i1530_3_;
        p_i1530_3_.playerNetServerHandler = this;
    }
    
    @Override
    public void onNetworkTick() {
        this.field_147366_g = false;
        ++this.networkTickCount;
        this.serverController.theProfiler.startSection("keepAlive");
        if (this.networkTickCount - this.field_147377_k > 40L) {
            this.field_147377_k = this.networkTickCount;
            this.field_147379_i = this.func_147363_d();
            this.field_147378_h = (int)this.field_147379_i;
            this.sendPacket(new S00PacketKeepAlive(this.field_147378_h));
        }
        if (this.chatSpamThresholdCount > 0) {
            --this.chatSpamThresholdCount;
        }
        if (this.field_147375_m > 0) {
            --this.field_147375_m;
        }
        if (this.playerEntity.func_154331_x() > 0L && this.serverController.func_143007_ar() > 0 && MinecraftServer.getSystemTimeMillis() - this.playerEntity.func_154331_x() > this.serverController.func_143007_ar() * 1000 * 60) {
            this.kickPlayerFromServer("You have been idle for too long!");
        }
    }
    
    public NetworkManager func_147362_b() {
        return this.netManager;
    }
    
    public void kickPlayerFromServer(final String p_147360_1_) {
        final ChatComponentText var2 = new ChatComponentText(p_147360_1_);
        this.netManager.scheduleOutboundPacket(new S40PacketDisconnect(var2), (GenericFutureListener)new GenericFutureListener() {
            private static final String __OBFID = "CL_00001453";
            
            public void operationComplete(final Future p_operationComplete_1_) {
                NetHandlerPlayServer.this.netManager.closeChannel(var2);
            }
        });
        this.netManager.disableAutoRead();
    }
    
    @Override
    public void processInput(final C0CPacketInput p_147358_1_) {
        this.playerEntity.setEntityActionState(p_147358_1_.func_149620_c(), p_147358_1_.func_149616_d(), p_147358_1_.func_149618_e(), p_147358_1_.func_149617_f());
    }
    
    @Override
    public void processPlayer(final C03PacketPlayer p_147347_1_) {
        final WorldServer var2 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
        this.field_147366_g = true;
        if (!this.playerEntity.playerConqueredTheEnd) {
            if (!this.hasMoved) {
                final double var3 = p_147347_1_.func_149467_d() - this.lastPosY;
                if (p_147347_1_.func_149464_c() == this.lastPosX && var3 * var3 < 0.01 && p_147347_1_.func_149472_e() == this.lastPosZ) {
                    this.hasMoved = true;
                }
            }
            if (this.hasMoved) {
                if (this.playerEntity.ridingEntity != null) {
                    float var4 = this.playerEntity.rotationYaw;
                    float var5 = this.playerEntity.rotationPitch;
                    this.playerEntity.ridingEntity.updateRiderPosition();
                    final double var6 = this.playerEntity.posX;
                    final double var7 = this.playerEntity.posY;
                    final double var8 = this.playerEntity.posZ;
                    if (p_147347_1_.func_149463_k()) {
                        var4 = p_147347_1_.func_149462_g();
                        var5 = p_147347_1_.func_149470_h();
                    }
                    this.playerEntity.onGround = p_147347_1_.func_149465_i();
                    this.playerEntity.onUpdateEntity();
                    this.playerEntity.ySize = 0.0f;
                    this.playerEntity.setPositionAndRotation(var6, var7, var8, var4, var5);
                    if (this.playerEntity.ridingEntity != null) {
                        this.playerEntity.ridingEntity.updateRiderPosition();
                    }
                    this.serverController.getConfigurationManager().serverUpdateMountedMovingPlayer(this.playerEntity);
                    if (this.hasMoved) {
                        this.lastPosX = this.playerEntity.posX;
                        this.lastPosY = this.playerEntity.posY;
                        this.lastPosZ = this.playerEntity.posZ;
                    }
                    var2.updateEntity(this.playerEntity);
                    return;
                }
                if (this.playerEntity.isPlayerSleeping()) {
                    this.playerEntity.onUpdateEntity();
                    this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
                    var2.updateEntity(this.playerEntity);
                    return;
                }
                final double var3 = this.playerEntity.posY;
                this.lastPosX = this.playerEntity.posX;
                this.lastPosY = this.playerEntity.posY;
                this.lastPosZ = this.playerEntity.posZ;
                double var6 = this.playerEntity.posX;
                double var7 = this.playerEntity.posY;
                double var8 = this.playerEntity.posZ;
                float var9 = this.playerEntity.rotationYaw;
                float var10 = this.playerEntity.rotationPitch;
                if (p_147347_1_.func_149466_j() && p_147347_1_.func_149467_d() == -999.0 && p_147347_1_.func_149471_f() == -999.0) {
                    p_147347_1_.func_149469_a(false);
                }
                if (p_147347_1_.func_149466_j()) {
                    var6 = p_147347_1_.func_149464_c();
                    var7 = p_147347_1_.func_149467_d();
                    var8 = p_147347_1_.func_149472_e();
                    final double var11 = p_147347_1_.func_149471_f() - p_147347_1_.func_149467_d();
                    if (!this.playerEntity.isPlayerSleeping() && (var11 > 1.65 || var11 < 0.1)) {
                        this.kickPlayerFromServer("Illegal stance");
                        NetHandlerPlayServer.logger.warn(this.playerEntity.getCommandSenderName() + " had an illegal stance: " + var11);
                        return;
                    }
                    if (Math.abs(p_147347_1_.func_149464_c()) > 3.2E7 || Math.abs(p_147347_1_.func_149472_e()) > 3.2E7) {
                        this.kickPlayerFromServer("Illegal position");
                        return;
                    }
                }
                if (p_147347_1_.func_149463_k()) {
                    var9 = p_147347_1_.func_149462_g();
                    var10 = p_147347_1_.func_149470_h();
                }
                this.playerEntity.onUpdateEntity();
                this.playerEntity.ySize = 0.0f;
                this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, var9, var10);
                if (!this.hasMoved) {
                    return;
                }
                double var11 = var6 - this.playerEntity.posX;
                double var12 = var7 - this.playerEntity.posY;
                double var13 = var8 - this.playerEntity.posZ;
                final double var14 = Math.min(Math.abs(var11), Math.abs(this.playerEntity.motionX));
                final double var15 = Math.min(Math.abs(var12), Math.abs(this.playerEntity.motionY));
                final double var16 = Math.min(Math.abs(var13), Math.abs(this.playerEntity.motionZ));
                double var17 = var14 * var14 + var15 * var15 + var16 * var16;
                if (var17 > 100.0 && (!this.serverController.isSinglePlayer() || !this.serverController.getServerOwner().equals(this.playerEntity.getCommandSenderName()))) {
                    NetHandlerPlayServer.logger.warn(this.playerEntity.getCommandSenderName() + " moved too quickly! " + var11 + "," + var12 + "," + var13 + " (" + var14 + ", " + var15 + ", " + var16 + ")");
                    this.setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
                    return;
                }
                final float var18 = 0.0625f;
                final boolean var19 = var2.getCollidingBoundingBoxes(this.playerEntity, this.playerEntity.boundingBox.copy().contract(var18, var18, var18)).isEmpty();
                if (this.playerEntity.onGround && !p_147347_1_.func_149465_i() && var12 > 0.0) {
                    this.playerEntity.jump();
                }
                this.playerEntity.moveEntity(var11, var12, var13);
                this.playerEntity.onGround = p_147347_1_.func_149465_i();
                this.playerEntity.addMovementStat(var11, var12, var13);
                final double var20 = var12;
                var11 = var6 - this.playerEntity.posX;
                var12 = var7 - this.playerEntity.posY;
                if (var12 > -0.5 || var12 < 0.5) {
                    var12 = 0.0;
                }
                var13 = var8 - this.playerEntity.posZ;
                var17 = var11 * var11 + var12 * var12 + var13 * var13;
                boolean var21 = false;
                if (var17 > 0.0625 && !this.playerEntity.isPlayerSleeping() && !this.playerEntity.theItemInWorldManager.isCreative()) {
                    var21 = true;
                    NetHandlerPlayServer.logger.warn(this.playerEntity.getCommandSenderName() + " moved wrongly!");
                }
                this.playerEntity.setPositionAndRotation(var6, var7, var8, var9, var10);
                final boolean var22 = var2.getCollidingBoundingBoxes(this.playerEntity, this.playerEntity.boundingBox.copy().contract(var18, var18, var18)).isEmpty();
                if (var19 && (var21 || !var22) && !this.playerEntity.isPlayerSleeping()) {
                    this.setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, var9, var10);
                    return;
                }
                final AxisAlignedBB var23 = this.playerEntity.boundingBox.copy().expand(var18, var18, var18).addCoord(0.0, -0.55, 0.0);
                if (!this.serverController.isFlightAllowed() && !this.playerEntity.theItemInWorldManager.isCreative() && !var2.checkBlockCollision(var23)) {
                    if (var20 >= -0.03125) {
                        ++this.floatingTickCount;
                        if (this.floatingTickCount > 80) {
                            NetHandlerPlayServer.logger.warn(this.playerEntity.getCommandSenderName() + " was kicked for floating too long!");
                            this.kickPlayerFromServer("Flying is not enabled on this server");
                            return;
                        }
                    }
                }
                else {
                    this.floatingTickCount = 0;
                }
                this.playerEntity.onGround = p_147347_1_.func_149465_i();
                this.serverController.getConfigurationManager().serverUpdateMountedMovingPlayer(this.playerEntity);
                this.playerEntity.handleFalling(this.playerEntity.posY - var3, p_147347_1_.func_149465_i());
            }
            else if (this.networkTickCount % 20 == 0) {
                this.setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
            }
        }
    }
    
    public void setPlayerLocation(final double p_147364_1_, final double p_147364_3_, final double p_147364_5_, final float p_147364_7_, final float p_147364_8_) {
        this.hasMoved = false;
        this.lastPosX = p_147364_1_;
        this.lastPosY = p_147364_3_;
        this.lastPosZ = p_147364_5_;
        this.playerEntity.setPositionAndRotation(p_147364_1_, p_147364_3_, p_147364_5_, p_147364_7_, p_147364_8_);
        this.playerEntity.playerNetServerHandler.sendPacket(new S08PacketPlayerPosLook(p_147364_1_, p_147364_3_ + 1.6200000047683716, p_147364_5_, p_147364_7_, p_147364_8_, false));
    }
    
    @Override
    public void processPlayerDigging(final C07PacketPlayerDigging p_147345_1_) {
        final WorldServer var2 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
        this.playerEntity.func_143004_u();
        if (p_147345_1_.func_149506_g() == 4) {
            this.playerEntity.dropOneItem(false);
        }
        else if (p_147345_1_.func_149506_g() == 3) {
            this.playerEntity.dropOneItem(true);
        }
        else if (p_147345_1_.func_149506_g() == 5) {
            this.playerEntity.stopUsingItem();
        }
        else {
            boolean var3 = false;
            if (p_147345_1_.func_149506_g() == 0) {
                var3 = true;
            }
            if (p_147345_1_.func_149506_g() == 1) {
                var3 = true;
            }
            if (p_147345_1_.func_149506_g() == 2) {
                var3 = true;
            }
            final int var4 = p_147345_1_.func_149505_c();
            final int var5 = p_147345_1_.func_149503_d();
            final int var6 = p_147345_1_.func_149502_e();
            if (var3) {
                final double var7 = this.playerEntity.posX - (var4 + 0.5);
                final double var8 = this.playerEntity.posY - (var5 + 0.5) + 1.5;
                final double var9 = this.playerEntity.posZ - (var6 + 0.5);
                final double var10 = var7 * var7 + var8 * var8 + var9 * var9;
                if (var10 > 36.0) {
                    return;
                }
                if (var5 >= this.serverController.getBuildLimit()) {
                    return;
                }
            }
            if (p_147345_1_.func_149506_g() == 0) {
                if (!this.serverController.isBlockProtected(var2, var4, var5, var6, this.playerEntity)) {
                    this.playerEntity.theItemInWorldManager.onBlockClicked(var4, var5, var6, p_147345_1_.func_149501_f());
                }
                else {
                    this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var4, var5, var6, var2));
                }
            }
            else if (p_147345_1_.func_149506_g() == 2) {
                this.playerEntity.theItemInWorldManager.uncheckedTryHarvestBlock(var4, var5, var6);
                if (var2.getBlock(var4, var5, var6).getMaterial() != Material.air) {
                    this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var4, var5, var6, var2));
                }
            }
            else if (p_147345_1_.func_149506_g() == 1) {
                this.playerEntity.theItemInWorldManager.cancelDestroyingBlock(var4, var5, var6);
                if (var2.getBlock(var4, var5, var6).getMaterial() != Material.air) {
                    this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var4, var5, var6, var2));
                }
            }
        }
    }
    
    @Override
    public void processPlayerBlockPlacement(final C08PacketPlayerBlockPlacement p_147346_1_) {
        final WorldServer var2 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
        ItemStack var3 = this.playerEntity.inventory.getCurrentItem();
        boolean var4 = false;
        int var5 = p_147346_1_.func_149576_c();
        int var6 = p_147346_1_.func_149571_d();
        int var7 = p_147346_1_.func_149570_e();
        final int var8 = p_147346_1_.func_149568_f();
        this.playerEntity.func_143004_u();
        if (p_147346_1_.func_149568_f() == 255) {
            if (var3 == null) {
                return;
            }
            this.playerEntity.theItemInWorldManager.tryUseItem(this.playerEntity, var2, var3);
        }
        else if (p_147346_1_.func_149571_d() >= this.serverController.getBuildLimit() - 1 && (p_147346_1_.func_149568_f() == 1 || p_147346_1_.func_149571_d() >= this.serverController.getBuildLimit())) {
            final ChatComponentTranslation var9 = new ChatComponentTranslation("build.tooHigh", new Object[] { this.serverController.getBuildLimit() });
            var9.getChatStyle().setColor(EnumChatFormatting.RED);
            this.playerEntity.playerNetServerHandler.sendPacket(new S02PacketChat(var9));
            var4 = true;
        }
        else {
            if (this.hasMoved && this.playerEntity.getDistanceSq(var5 + 0.5, var6 + 0.5, var7 + 0.5) < 64.0 && !this.serverController.isBlockProtected(var2, var5, var6, var7, this.playerEntity)) {
                this.playerEntity.theItemInWorldManager.activateBlockOrUseItem(this.playerEntity, var2, var3, var5, var6, var7, var8, p_147346_1_.func_149573_h(), p_147346_1_.func_149569_i(), p_147346_1_.func_149575_j());
            }
            var4 = true;
        }
        if (var4) {
            this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var5, var6, var7, var2));
            if (var8 == 0) {
                --var6;
            }
            if (var8 == 1) {
                ++var6;
            }
            if (var8 == 2) {
                --var7;
            }
            if (var8 == 3) {
                ++var7;
            }
            if (var8 == 4) {
                --var5;
            }
            if (var8 == 5) {
                ++var5;
            }
            this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var5, var6, var7, var2));
        }
        var3 = this.playerEntity.inventory.getCurrentItem();
        if (var3 != null && var3.stackSize == 0) {
            this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = null;
            var3 = null;
        }
        if (var3 == null || var3.getMaxItemUseDuration() == 0) {
            this.playerEntity.isChangingQuantityOnly = true;
            this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = ItemStack.copyItemStack(this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem]);
            final Slot var10 = this.playerEntity.openContainer.getSlotFromInventory(this.playerEntity.inventory, this.playerEntity.inventory.currentItem);
            this.playerEntity.openContainer.detectAndSendChanges();
            this.playerEntity.isChangingQuantityOnly = false;
            if (!ItemStack.areItemStacksEqual(this.playerEntity.inventory.getCurrentItem(), p_147346_1_.func_149574_g())) {
                this.sendPacket(new S2FPacketSetSlot(this.playerEntity.openContainer.windowId, var10.slotNumber, this.playerEntity.inventory.getCurrentItem()));
            }
        }
    }
    
    @Override
    public void onDisconnect(final IChatComponent p_147231_1_) {
        NetHandlerPlayServer.logger.info(this.playerEntity.getCommandSenderName() + " lost connection: " + p_147231_1_);
        this.serverController.func_147132_au();
        final ChatComponentTranslation var2 = new ChatComponentTranslation("multiplayer.player.left", new Object[] { this.playerEntity.func_145748_c_() });
        var2.getChatStyle().setColor(EnumChatFormatting.YELLOW);
        this.serverController.getConfigurationManager().func_148539_a(var2);
        this.playerEntity.mountEntityAndWakeUp();
        this.serverController.getConfigurationManager().playerLoggedOut(this.playerEntity);
        if (this.serverController.isSinglePlayer() && this.playerEntity.getCommandSenderName().equals(this.serverController.getServerOwner())) {
            NetHandlerPlayServer.logger.info("Stopping singleplayer server as player logged out");
            this.serverController.initiateShutdown();
        }
    }
    
    public void sendPacket(final Packet p_147359_1_) {
        if (p_147359_1_ instanceof S02PacketChat) {
            final S02PacketChat var2 = (S02PacketChat)p_147359_1_;
            final EntityPlayer.EnumChatVisibility var3 = this.playerEntity.func_147096_v();
            if (var3 == EntityPlayer.EnumChatVisibility.HIDDEN) {
                return;
            }
            if (var3 == EntityPlayer.EnumChatVisibility.SYSTEM && !var2.func_148916_d()) {
                return;
            }
        }
        try {
            this.netManager.scheduleOutboundPacket(p_147359_1_, new GenericFutureListener[0]);
        }
        catch (Throwable var5) {
            final CrashReport var4 = CrashReport.makeCrashReport(var5, "Sending packet");
            final CrashReportCategory var6 = var4.makeCategory("Packet being sent");
            var6.addCrashSectionCallable("Packet class", new Callable() {
                private static final String __OBFID = "CL_00001454";
                
                @Override
                public String call() {
                    return p_147359_1_.getClass().getCanonicalName();
                }
            });
            throw new ReportedException(var4);
        }
    }
    
    @Override
    public void processHeldItemChange(final C09PacketHeldItemChange p_147355_1_) {
        if (p_147355_1_.func_149614_c() >= 0 && p_147355_1_.func_149614_c() < InventoryPlayer.getHotbarSize()) {
            this.playerEntity.inventory.currentItem = p_147355_1_.func_149614_c();
            this.playerEntity.func_143004_u();
        }
        else {
            NetHandlerPlayServer.logger.warn(this.playerEntity.getCommandSenderName() + " tried to set an invalid carried item");
        }
    }
    
    @Override
    public void processChatMessage(final C01PacketChatMessage p_147354_1_) {
        if (this.playerEntity.func_147096_v() == EntityPlayer.EnumChatVisibility.HIDDEN) {
            final ChatComponentTranslation var4 = new ChatComponentTranslation("chat.cannotSend", new Object[0]);
            var4.getChatStyle().setColor(EnumChatFormatting.RED);
            this.sendPacket(new S02PacketChat(var4));
        }
        else {
            this.playerEntity.func_143004_u();
            String var5 = p_147354_1_.func_149439_c();
            var5 = StringUtils.normalizeSpace(var5);
            for (int var6 = 0; var6 < var5.length(); ++var6) {
                if (!ChatAllowedCharacters.isAllowedCharacter(var5.charAt(var6))) {
                    this.kickPlayerFromServer("Illegal characters in chat");
                    return;
                }
            }
            if (var5.startsWith("/")) {
                this.handleSlashCommand(var5);
            }
            else {
                final ChatComponentTranslation var7 = new ChatComponentTranslation("chat.type.text", new Object[] { this.playerEntity.func_145748_c_(), var5 });
                this.serverController.getConfigurationManager().func_148544_a(var7, false);
            }
            this.chatSpamThresholdCount += 20;
            if (this.chatSpamThresholdCount > 200 && !this.serverController.getConfigurationManager().func_152596_g(this.playerEntity.getGameProfile())) {
                this.kickPlayerFromServer("disconnect.spam");
            }
        }
    }
    
    private void handleSlashCommand(final String p_147361_1_) {
        this.serverController.getCommandManager().executeCommand(this.playerEntity, p_147361_1_);
    }
    
    @Override
    public void processAnimation(final C0APacketAnimation p_147350_1_) {
        this.playerEntity.func_143004_u();
        if (p_147350_1_.func_149421_d() == 1) {
            this.playerEntity.swingItem();
        }
    }
    
    @Override
    public void processEntityAction(final C0BPacketEntityAction p_147357_1_) {
        this.playerEntity.func_143004_u();
        if (p_147357_1_.func_149513_d() == 1) {
            this.playerEntity.setSneaking(true);
        }
        else if (p_147357_1_.func_149513_d() == 2) {
            this.playerEntity.setSneaking(false);
        }
        else if (p_147357_1_.func_149513_d() == 4) {
            this.playerEntity.setSprinting(true);
        }
        else if (p_147357_1_.func_149513_d() == 5) {
            this.playerEntity.setSprinting(false);
        }
        else if (p_147357_1_.func_149513_d() == 3) {
            this.playerEntity.wakeUpPlayer(false, true, true);
            this.hasMoved = false;
        }
        else if (p_147357_1_.func_149513_d() == 6) {
            if (this.playerEntity.ridingEntity != null && this.playerEntity.ridingEntity instanceof EntityHorse) {
                ((EntityHorse)this.playerEntity.ridingEntity).setJumpPower(p_147357_1_.func_149512_e());
            }
        }
        else if (p_147357_1_.func_149513_d() == 7 && this.playerEntity.ridingEntity != null && this.playerEntity.ridingEntity instanceof EntityHorse) {
            ((EntityHorse)this.playerEntity.ridingEntity).openGUI(this.playerEntity);
        }
    }
    
    @Override
    public void processUseEntity(final C02PacketUseEntity p_147340_1_) {
        final WorldServer var2 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
        final Entity var3 = p_147340_1_.func_149564_a(var2);
        this.playerEntity.func_143004_u();
        if (var3 != null) {
            final boolean var4 = this.playerEntity.canEntityBeSeen(var3);
            double var5 = 36.0;
            if (!var4) {
                var5 = 9.0;
            }
            if (this.playerEntity.getDistanceSqToEntity(var3) < var5) {
                if (p_147340_1_.func_149565_c() == C02PacketUseEntity.Action.INTERACT) {
                    this.playerEntity.interactWith(var3);
                }
                else if (p_147340_1_.func_149565_c() == C02PacketUseEntity.Action.ATTACK) {
                    if (var3 instanceof EntityItem || var3 instanceof EntityXPOrb || var3 instanceof EntityArrow || var3 == this.playerEntity) {
                        this.kickPlayerFromServer("Attempting to attack an invalid entity");
                        this.serverController.logWarning("Player " + this.playerEntity.getCommandSenderName() + " tried to attack an invalid entity");
                        return;
                    }
                    this.playerEntity.attackTargetEntityWithCurrentItem(var3);
                }
            }
        }
    }
    
    @Override
    public void processClientStatus(final C16PacketClientStatus p_147342_1_) {
        this.playerEntity.func_143004_u();
        final C16PacketClientStatus.EnumState var2 = p_147342_1_.func_149435_c();
        switch (SwitchEnumState.field_151290_a[var2.ordinal()]) {
            case 1: {
                if (this.playerEntity.playerConqueredTheEnd) {
                    this.playerEntity = this.serverController.getConfigurationManager().respawnPlayer(this.playerEntity, 0, true);
                    break;
                }
                if (this.playerEntity.getServerForPlayer().getWorldInfo().isHardcoreModeEnabled()) {
                    if (this.serverController.isSinglePlayer() && this.playerEntity.getCommandSenderName().equals(this.serverController.getServerOwner())) {
                        this.playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it's game over!");
                        this.serverController.deleteWorldAndStopServer();
                        break;
                    }
                    final UserListBansEntry var3 = new UserListBansEntry(this.playerEntity.getGameProfile(), null, "(You just lost the game)", null, "Death in Hardcore");
                    this.serverController.getConfigurationManager().func_152608_h().func_152687_a(var3);
                    this.playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it's game over!");
                    break;
                }
                else {
                    if (this.playerEntity.getHealth() > 0.0f) {
                        return;
                    }
                    this.playerEntity = this.serverController.getConfigurationManager().respawnPlayer(this.playerEntity, 0, false);
                    break;
                }
                break;
            }
            case 2: {
                this.playerEntity.func_147099_x().func_150876_a(this.playerEntity);
                break;
            }
            case 3: {
                this.playerEntity.triggerAchievement(AchievementList.openInventory);
                break;
            }
        }
    }
    
    @Override
    public void processCloseWindow(final C0DPacketCloseWindow p_147356_1_) {
        this.playerEntity.closeContainer();
    }
    
    @Override
    public void processClickWindow(final C0EPacketClickWindow p_147351_1_) {
        this.playerEntity.func_143004_u();
        if (this.playerEntity.openContainer.windowId == p_147351_1_.func_149548_c() && this.playerEntity.openContainer.isPlayerNotUsingContainer(this.playerEntity)) {
            final ItemStack var2 = this.playerEntity.openContainer.slotClick(p_147351_1_.func_149544_d(), p_147351_1_.func_149543_e(), p_147351_1_.func_149542_h(), this.playerEntity);
            if (ItemStack.areItemStacksEqual(p_147351_1_.func_149546_g(), var2)) {
                this.playerEntity.playerNetServerHandler.sendPacket(new S32PacketConfirmTransaction(p_147351_1_.func_149548_c(), p_147351_1_.func_149547_f(), true));
                this.playerEntity.isChangingQuantityOnly = true;
                this.playerEntity.openContainer.detectAndSendChanges();
                this.playerEntity.updateHeldItem();
                this.playerEntity.isChangingQuantityOnly = false;
            }
            else {
                this.field_147372_n.addKey(this.playerEntity.openContainer.windowId, p_147351_1_.func_149547_f());
                this.playerEntity.playerNetServerHandler.sendPacket(new S32PacketConfirmTransaction(p_147351_1_.func_149548_c(), p_147351_1_.func_149547_f(), false));
                this.playerEntity.openContainer.setPlayerIsPresent(this.playerEntity, false);
                final ArrayList var3 = new ArrayList();
                for (int var4 = 0; var4 < this.playerEntity.openContainer.inventorySlots.size(); ++var4) {
                    var3.add(this.playerEntity.openContainer.inventorySlots.get(var4).getStack());
                }
                this.playerEntity.sendContainerAndContentsToPlayer(this.playerEntity.openContainer, var3);
            }
        }
    }
    
    @Override
    public void processEnchantItem(final C11PacketEnchantItem p_147338_1_) {
        this.playerEntity.func_143004_u();
        if (this.playerEntity.openContainer.windowId == p_147338_1_.func_149539_c() && this.playerEntity.openContainer.isPlayerNotUsingContainer(this.playerEntity)) {
            this.playerEntity.openContainer.enchantItem(this.playerEntity, p_147338_1_.func_149537_d());
            this.playerEntity.openContainer.detectAndSendChanges();
        }
    }
    
    @Override
    public void processCreativeInventoryAction(final C10PacketCreativeInventoryAction p_147344_1_) {
        if (this.playerEntity.theItemInWorldManager.isCreative()) {
            final boolean var2 = p_147344_1_.func_149627_c() < 0;
            final ItemStack var3 = p_147344_1_.func_149625_d();
            final boolean var4 = p_147344_1_.func_149627_c() >= 1 && p_147344_1_.func_149627_c() < 36 + InventoryPlayer.getHotbarSize();
            final boolean var5 = var3 == null || var3.getItem() != null;
            final boolean var6 = var3 == null || (var3.getItemDamage() >= 0 && var3.stackSize <= 64 && var3.stackSize > 0);
            if (var4 && var5 && var6) {
                if (var3 == null) {
                    this.playerEntity.inventoryContainer.putStackInSlot(p_147344_1_.func_149627_c(), null);
                }
                else {
                    this.playerEntity.inventoryContainer.putStackInSlot(p_147344_1_.func_149627_c(), var3);
                }
                this.playerEntity.inventoryContainer.setPlayerIsPresent(this.playerEntity, true);
            }
            else if (var2 && var5 && var6 && this.field_147375_m < 200) {
                this.field_147375_m += 20;
                final EntityItem var7 = this.playerEntity.dropPlayerItemWithRandomChoice(var3, true);
                if (var7 != null) {
                    var7.setAgeToCreativeDespawnTime();
                }
            }
        }
    }
    
    @Override
    public void processConfirmTransaction(final C0FPacketConfirmTransaction p_147339_1_) {
        final Short var2 = (Short)this.field_147372_n.lookup(this.playerEntity.openContainer.windowId);
        if (var2 != null && p_147339_1_.func_149533_d() == var2 && this.playerEntity.openContainer.windowId == p_147339_1_.func_149532_c() && !this.playerEntity.openContainer.isPlayerNotUsingContainer(this.playerEntity)) {
            this.playerEntity.openContainer.setPlayerIsPresent(this.playerEntity, true);
        }
    }
    
    @Override
    public void processUpdateSign(final C12PacketUpdateSign p_147343_1_) {
        this.playerEntity.func_143004_u();
        final WorldServer var2 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
        if (var2.blockExists(p_147343_1_.func_149588_c(), p_147343_1_.func_149586_d(), p_147343_1_.func_149585_e())) {
            final TileEntity var3 = var2.getTileEntity(p_147343_1_.func_149588_c(), p_147343_1_.func_149586_d(), p_147343_1_.func_149585_e());
            if (var3 instanceof TileEntitySign) {
                final TileEntitySign var4 = (TileEntitySign)var3;
                if (!var4.func_145914_a() || var4.func_145911_b() != this.playerEntity) {
                    this.serverController.logWarning("Player " + this.playerEntity.getCommandSenderName() + " just tried to change non-editable sign");
                    return;
                }
            }
            for (int var5 = 0; var5 < 4; ++var5) {
                boolean var6 = true;
                if (p_147343_1_.func_149589_f()[var5].length() > 15) {
                    var6 = false;
                }
                else {
                    for (int var7 = 0; var7 < p_147343_1_.func_149589_f()[var5].length(); ++var7) {
                        if (!ChatAllowedCharacters.isAllowedCharacter(p_147343_1_.func_149589_f()[var5].charAt(var7))) {
                            var6 = false;
                        }
                    }
                }
                if (!var6) {
                    p_147343_1_.func_149589_f()[var5] = "!?";
                }
            }
            if (var3 instanceof TileEntitySign) {
                final int var5 = p_147343_1_.func_149588_c();
                final int var8 = p_147343_1_.func_149586_d();
                final int var7 = p_147343_1_.func_149585_e();
                final TileEntitySign var9 = (TileEntitySign)var3;
                System.arraycopy(p_147343_1_.func_149589_f(), 0, var9.field_145915_a, 0, 4);
                var9.onInventoryChanged();
                var2.func_147471_g(var5, var8, var7);
            }
        }
    }
    
    @Override
    public void processKeepAlive(final C00PacketKeepAlive p_147353_1_) {
        if (p_147353_1_.func_149460_c() == this.field_147378_h) {
            final int var2 = (int)(this.func_147363_d() - this.field_147379_i);
            this.playerEntity.ping = (this.playerEntity.ping * 3 + var2) / 4;
        }
    }
    
    private long func_147363_d() {
        return System.nanoTime() / 1000000L;
    }
    
    @Override
    public void processPlayerAbilities(final C13PacketPlayerAbilities p_147348_1_) {
        this.playerEntity.capabilities.isFlying = (p_147348_1_.func_149488_d() && this.playerEntity.capabilities.allowFlying);
    }
    
    @Override
    public void processTabComplete(final C14PacketTabComplete p_147341_1_) {
        final ArrayList var2 = Lists.newArrayList();
        for (final String var4 : this.serverController.getPossibleCompletions(this.playerEntity, p_147341_1_.func_149419_c())) {
            var2.add(var4);
        }
        this.playerEntity.playerNetServerHandler.sendPacket(new S3APacketTabComplete(var2.toArray(new String[var2.size()])));
    }
    
    @Override
    public void processClientSettings(final C15PacketClientSettings p_147352_1_) {
        this.playerEntity.func_147100_a(p_147352_1_);
    }
    
    @Override
    public void processVanilla250Packet(final C17PacketCustomPayload p_147349_1_) {
        if ("MC|BEdit".equals(p_147349_1_.func_149559_c())) {
            final PacketBuffer var2 = new PacketBuffer(Unpooled.wrappedBuffer(p_147349_1_.func_149558_e()));
            try {
                final ItemStack var3 = var2.readItemStackFromBuffer();
                if (var3 == null) {
                    return;
                }
                if (!ItemWritableBook.func_150930_a(var3.getTagCompound())) {
                    throw new IOException("Invalid book tag!");
                }
                final ItemStack var4 = this.playerEntity.inventory.getCurrentItem();
                if (var4 != null) {
                    if (var3.getItem() == Items.writable_book && var3.getItem() == var4.getItem()) {
                        var4.setTagInfo("pages", var3.getTagCompound().getTagList("pages", 8));
                    }
                }
            }
            catch (Exception var5) {
                NetHandlerPlayServer.logger.error("Couldn't handle book info", (Throwable)var5);
            }
            finally {
                var2.release();
            }
            return;
        }
        if ("MC|BSign".equals(p_147349_1_.func_149559_c())) {
            final PacketBuffer var2 = new PacketBuffer(Unpooled.wrappedBuffer(p_147349_1_.func_149558_e()));
            try {
                final ItemStack var3 = var2.readItemStackFromBuffer();
                if (var3 != null) {
                    if (!ItemEditableBook.validBookTagContents(var3.getTagCompound())) {
                        throw new IOException("Invalid book tag!");
                    }
                    final ItemStack var4 = this.playerEntity.inventory.getCurrentItem();
                    if (var4 == null) {
                        return;
                    }
                    if (var3.getItem() == Items.written_book && var4.getItem() == Items.writable_book) {
                        var4.setTagInfo("author", new NBTTagString(this.playerEntity.getCommandSenderName()));
                        var4.setTagInfo("title", new NBTTagString(var3.getTagCompound().getString("title")));
                        var4.setTagInfo("pages", var3.getTagCompound().getTagList("pages", 8));
                        var4.func_150996_a(Items.written_book);
                    }
                }
            }
            catch (Exception var6) {
                NetHandlerPlayServer.logger.error("Couldn't sign book", (Throwable)var6);
            }
            finally {
                var2.release();
            }
            return;
        }
        if ("MC|TrSel".equals(p_147349_1_.func_149559_c())) {
            try {
                final DataInputStream var7 = new DataInputStream(new ByteArrayInputStream(p_147349_1_.func_149558_e()));
                final int var8 = var7.readInt();
                final Container var9 = this.playerEntity.openContainer;
                if (var9 instanceof ContainerMerchant) {
                    ((ContainerMerchant)var9).setCurrentRecipeIndex(var8);
                }
            }
            catch (Exception var10) {
                NetHandlerPlayServer.logger.error("Couldn't select trade", (Throwable)var10);
            }
        }
        else if ("MC|AdvCdm".equals(p_147349_1_.func_149559_c())) {
            if (!this.serverController.isCommandBlockEnabled()) {
                this.playerEntity.addChatMessage(new ChatComponentTranslation("advMode.notEnabled", new Object[0]));
            }
            else if (this.playerEntity.canCommandSenderUseCommand(2, "") && this.playerEntity.capabilities.isCreativeMode) {
                final PacketBuffer var2 = new PacketBuffer(Unpooled.wrappedBuffer(p_147349_1_.func_149558_e()));
                try {
                    final byte var11 = var2.readByte();
                    CommandBlockLogic var12 = null;
                    if (var11 == 0) {
                        final TileEntity var13 = this.playerEntity.worldObj.getTileEntity(var2.readInt(), var2.readInt(), var2.readInt());
                        if (var13 instanceof TileEntityCommandBlock) {
                            var12 = ((TileEntityCommandBlock)var13).func_145993_a();
                        }
                    }
                    else if (var11 == 1) {
                        final Entity var14 = this.playerEntity.worldObj.getEntityByID(var2.readInt());
                        if (var14 instanceof EntityMinecartCommandBlock) {
                            var12 = ((EntityMinecartCommandBlock)var14).func_145822_e();
                        }
                    }
                    final String var15 = var2.readStringFromBuffer(var2.readableBytes());
                    if (var12 != null) {
                        var12.func_145752_a(var15);
                        var12.func_145756_e();
                        this.playerEntity.addChatMessage(new ChatComponentTranslation("advMode.setCommand.success", new Object[] { var15 }));
                    }
                }
                catch (Exception var16) {
                    NetHandlerPlayServer.logger.error("Couldn't set command block", (Throwable)var16);
                }
                finally {
                    var2.release();
                }
            }
            else {
                this.playerEntity.addChatMessage(new ChatComponentTranslation("advMode.notAllowed", new Object[0]));
            }
        }
        else if ("MC|Beacon".equals(p_147349_1_.func_149559_c())) {
            if (this.playerEntity.openContainer instanceof ContainerBeacon) {
                try {
                    final DataInputStream var7 = new DataInputStream(new ByteArrayInputStream(p_147349_1_.func_149558_e()));
                    final int var8 = var7.readInt();
                    final int var17 = var7.readInt();
                    final ContainerBeacon var18 = (ContainerBeacon)this.playerEntity.openContainer;
                    final Slot var19 = var18.getSlot(0);
                    if (var19.getHasStack()) {
                        var19.decrStackSize(1);
                        final TileEntityBeacon var20 = var18.func_148327_e();
                        var20.func_146001_d(var8);
                        var20.func_146004_e(var17);
                        var20.onInventoryChanged();
                    }
                }
                catch (Exception var21) {
                    NetHandlerPlayServer.logger.error("Couldn't set beacon", (Throwable)var21);
                }
            }
        }
        else if ("MC|ItemName".equals(p_147349_1_.func_149559_c()) && this.playerEntity.openContainer instanceof ContainerRepair) {
            final ContainerRepair var22 = (ContainerRepair)this.playerEntity.openContainer;
            if (p_147349_1_.func_149558_e() != null && p_147349_1_.func_149558_e().length >= 1) {
                final String var23 = ChatAllowedCharacters.filerAllowedCharacters(new String(p_147349_1_.func_149558_e(), Charsets.UTF_8));
                if (var23.length() <= 30) {
                    var22.updateItemName(var23);
                }
            }
            else {
                var22.updateItemName("");
            }
        }
    }
    
    @Override
    public void onConnectionStateTransition(final EnumConnectionState p_147232_1_, final EnumConnectionState p_147232_2_) {
        if (p_147232_2_ != EnumConnectionState.PLAY) {
            throw new IllegalStateException("Unexpected change in protocol!");
        }
    }
    
    static {
        logger = LogManager.getLogger();
        NetHandlerPlayServer.field_147376_j = new Random();
    }
    
    static final class SwitchEnumState
    {
        static final int[] field_151290_a;
        private static final String __OBFID = "CL_00001455";
        
        static {
            field_151290_a = new int[C16PacketClientStatus.EnumState.values().length];
            try {
                SwitchEnumState.field_151290_a[C16PacketClientStatus.EnumState.PERFORM_RESPAWN.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumState.field_151290_a[C16PacketClientStatus.EnumState.REQUEST_STATS.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumState.field_151290_a[C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
        }
    }
}
