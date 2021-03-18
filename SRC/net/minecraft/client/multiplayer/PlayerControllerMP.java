package net.minecraft.client.multiplayer;

import net.minecraft.client.*;
import net.minecraft.client.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.network.*;
import net.minecraft.client.audio.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.stats.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.passive.*;

public class PlayerControllerMP
{
    private final Minecraft mc;
    private final NetHandlerPlayClient netClientHandler;
    private int currentBlockX;
    private int currentBlockY;
    private int currentblockZ;
    private ItemStack currentItemHittingBlock;
    private float curBlockDamageMP;
    private float stepSoundTickCounter;
    private int blockHitDelay;
    private boolean isHittingBlock;
    private WorldSettings.GameType currentGameType;
    private int currentPlayerItem;
    private static final String __OBFID = "CL_00000881";
    
    public PlayerControllerMP(final Minecraft p_i45062_1_, final NetHandlerPlayClient p_i45062_2_) {
        this.currentBlockX = -1;
        this.currentBlockY = -1;
        this.currentblockZ = -1;
        this.currentGameType = WorldSettings.GameType.SURVIVAL;
        this.mc = p_i45062_1_;
        this.netClientHandler = p_i45062_2_;
    }
    
    public static void clickBlockCreative(final Minecraft p_78744_0_, final PlayerControllerMP p_78744_1_, final int p_78744_2_, final int p_78744_3_, final int p_78744_4_, final int p_78744_5_) {
        if (!p_78744_0_.theWorld.extinguishFire(p_78744_0_.thePlayer, p_78744_2_, p_78744_3_, p_78744_4_, p_78744_5_)) {
            p_78744_1_.onPlayerDestroyBlock(p_78744_2_, p_78744_3_, p_78744_4_, p_78744_5_);
        }
    }
    
    public void setPlayerCapabilities(final EntityPlayer p_78748_1_) {
        this.currentGameType.configurePlayerCapabilities(p_78748_1_.capabilities);
    }
    
    public boolean enableEverythingIsScrewedUpMode() {
        return false;
    }
    
    public void setGameType(final WorldSettings.GameType p_78746_1_) {
        (this.currentGameType = p_78746_1_).configurePlayerCapabilities(this.mc.thePlayer.capabilities);
    }
    
    public void flipPlayer(final EntityPlayer p_78745_1_) {
        p_78745_1_.rotationYaw = -180.0f;
    }
    
    public boolean shouldDrawHUD() {
        return this.currentGameType.isSurvivalOrAdventure();
    }
    
    public boolean onPlayerDestroyBlock(final int p_78751_1_, final int p_78751_2_, final int p_78751_3_, final int p_78751_4_) {
        if (this.currentGameType.isAdventure() && !this.mc.thePlayer.isCurrentToolAdventureModeExempt(p_78751_1_, p_78751_2_, p_78751_3_)) {
            return false;
        }
        if (this.currentGameType.isCreative() && this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
            return false;
        }
        final WorldClient var5 = this.mc.theWorld;
        final Block var6 = var5.getBlock(p_78751_1_, p_78751_2_, p_78751_3_);
        if (var6.getMaterial() == Material.air) {
            return false;
        }
        var5.playAuxSFX(2001, p_78751_1_, p_78751_2_, p_78751_3_, Block.getIdFromBlock(var6) + (var5.getBlockMetadata(p_78751_1_, p_78751_2_, p_78751_3_) << 12));
        final int var7 = var5.getBlockMetadata(p_78751_1_, p_78751_2_, p_78751_3_);
        final boolean var8 = var5.setBlockToAir(p_78751_1_, p_78751_2_, p_78751_3_);
        if (var8) {
            var6.onBlockDestroyedByPlayer(var5, p_78751_1_, p_78751_2_, p_78751_3_, var7);
        }
        this.currentBlockY = -1;
        if (!this.currentGameType.isCreative()) {
            final ItemStack var9 = this.mc.thePlayer.getCurrentEquippedItem();
            if (var9 != null) {
                var9.func_150999_a(var5, var6, p_78751_1_, p_78751_2_, p_78751_3_, this.mc.thePlayer);
                if (var9.stackSize == 0) {
                    this.mc.thePlayer.destroyCurrentEquippedItem();
                }
            }
        }
        return var8;
    }
    
    public void clickBlock(final int p_78743_1_, final int p_78743_2_, final int p_78743_3_, final int p_78743_4_) {
        if (!this.currentGameType.isAdventure() || this.mc.thePlayer.isCurrentToolAdventureModeExempt(p_78743_1_, p_78743_2_, p_78743_3_)) {
            if (this.currentGameType.isCreative()) {
                this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(0, p_78743_1_, p_78743_2_, p_78743_3_, p_78743_4_));
                clickBlockCreative(this.mc, this, p_78743_1_, p_78743_2_, p_78743_3_, p_78743_4_);
                this.blockHitDelay = 5;
            }
            else if (!this.isHittingBlock || !this.sameToolAndBlock(p_78743_1_, p_78743_2_, p_78743_3_)) {
                if (this.isHittingBlock) {
                    this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(1, this.currentBlockX, this.currentBlockY, this.currentblockZ, p_78743_4_));
                }
                this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(0, p_78743_1_, p_78743_2_, p_78743_3_, p_78743_4_));
                final Block var5 = this.mc.theWorld.getBlock(p_78743_1_, p_78743_2_, p_78743_3_);
                final boolean var6 = var5.getMaterial() != Material.air;
                if (var6 && this.curBlockDamageMP == 0.0f) {
                    var5.onBlockClicked(this.mc.theWorld, p_78743_1_, p_78743_2_, p_78743_3_, this.mc.thePlayer);
                }
                if (var6 && var5.getPlayerRelativeBlockHardness(this.mc.thePlayer, this.mc.thePlayer.worldObj, p_78743_1_, p_78743_2_, p_78743_3_) >= 1.0f) {
                    this.onPlayerDestroyBlock(p_78743_1_, p_78743_2_, p_78743_3_, p_78743_4_);
                }
                else {
                    this.isHittingBlock = true;
                    this.currentBlockX = p_78743_1_;
                    this.currentBlockY = p_78743_2_;
                    this.currentblockZ = p_78743_3_;
                    this.currentItemHittingBlock = this.mc.thePlayer.getHeldItem();
                    this.curBlockDamageMP = 0.0f;
                    this.stepSoundTickCounter = 0.0f;
                    this.mc.theWorld.destroyBlockInWorldPartially(this.mc.thePlayer.getEntityId(), this.currentBlockX, this.currentBlockY, this.currentblockZ, (int)(this.curBlockDamageMP * 10.0f) - 1);
                }
            }
        }
    }
    
    public void resetBlockRemoving() {
        if (this.isHittingBlock) {
            this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(1, this.currentBlockX, this.currentBlockY, this.currentblockZ, -1));
        }
        this.isHittingBlock = false;
        this.curBlockDamageMP = 0.0f;
        this.mc.theWorld.destroyBlockInWorldPartially(this.mc.thePlayer.getEntityId(), this.currentBlockX, this.currentBlockY, this.currentblockZ, -1);
    }
    
    public void onPlayerDamageBlock(final int p_78759_1_, final int p_78759_2_, final int p_78759_3_, final int p_78759_4_) {
        this.syncCurrentPlayItem();
        if (this.blockHitDelay > 0) {
            --this.blockHitDelay;
        }
        else if (this.currentGameType.isCreative()) {
            this.blockHitDelay = 5;
            this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(0, p_78759_1_, p_78759_2_, p_78759_3_, p_78759_4_));
            clickBlockCreative(this.mc, this, p_78759_1_, p_78759_2_, p_78759_3_, p_78759_4_);
        }
        else if (this.sameToolAndBlock(p_78759_1_, p_78759_2_, p_78759_3_)) {
            final Block var5 = this.mc.theWorld.getBlock(p_78759_1_, p_78759_2_, p_78759_3_);
            if (var5.getMaterial() == Material.air) {
                this.isHittingBlock = false;
                return;
            }
            this.curBlockDamageMP += var5.getPlayerRelativeBlockHardness(this.mc.thePlayer, this.mc.thePlayer.worldObj, p_78759_1_, p_78759_2_, p_78759_3_);
            if (this.stepSoundTickCounter % 4.0f == 0.0f) {
                this.mc.getSoundHandler().playSound(new PositionedSoundRecord(new ResourceLocation(var5.stepSound.func_150498_e()), (var5.stepSound.func_150497_c() + 1.0f) / 8.0f, var5.stepSound.func_150494_d() * 0.5f, p_78759_1_ + 0.5f, p_78759_2_ + 0.5f, p_78759_3_ + 0.5f));
            }
            ++this.stepSoundTickCounter;
            if (this.curBlockDamageMP >= 1.0f) {
                this.isHittingBlock = false;
                this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(2, p_78759_1_, p_78759_2_, p_78759_3_, p_78759_4_));
                this.onPlayerDestroyBlock(p_78759_1_, p_78759_2_, p_78759_3_, p_78759_4_);
                this.curBlockDamageMP = 0.0f;
                this.stepSoundTickCounter = 0.0f;
                this.blockHitDelay = 5;
            }
            this.mc.theWorld.destroyBlockInWorldPartially(this.mc.thePlayer.getEntityId(), this.currentBlockX, this.currentBlockY, this.currentblockZ, (int)(this.curBlockDamageMP * 10.0f) - 1);
        }
        else {
            this.clickBlock(p_78759_1_, p_78759_2_, p_78759_3_, p_78759_4_);
        }
    }
    
    public float getBlockReachDistance() {
        return this.currentGameType.isCreative() ? 5.0f : 4.5f;
    }
    
    public void updateController() {
        this.syncCurrentPlayItem();
        if (this.netClientHandler.getNetworkManager().isChannelOpen()) {
            this.netClientHandler.getNetworkManager().processReceivedPackets();
        }
        else if (this.netClientHandler.getNetworkManager().getExitMessage() != null) {
            this.netClientHandler.getNetworkManager().getNetHandler().onDisconnect(this.netClientHandler.getNetworkManager().getExitMessage());
        }
        else {
            this.netClientHandler.getNetworkManager().getNetHandler().onDisconnect(new ChatComponentText("Disconnected from server"));
        }
    }
    
    private boolean sameToolAndBlock(final int p_85182_1_, final int p_85182_2_, final int p_85182_3_) {
        final ItemStack var4 = this.mc.thePlayer.getHeldItem();
        boolean var5 = this.currentItemHittingBlock == null && var4 == null;
        if (this.currentItemHittingBlock != null && var4 != null) {
            var5 = (var4.getItem() == this.currentItemHittingBlock.getItem() && ItemStack.areItemStackTagsEqual(var4, this.currentItemHittingBlock) && (var4.isItemStackDamageable() || var4.getItemDamage() == this.currentItemHittingBlock.getItemDamage()));
        }
        return p_85182_1_ == this.currentBlockX && p_85182_2_ == this.currentBlockY && p_85182_3_ == this.currentblockZ && var5;
    }
    
    private void syncCurrentPlayItem() {
        final int var1 = this.mc.thePlayer.inventory.currentItem;
        if (var1 != this.currentPlayerItem) {
            this.currentPlayerItem = var1;
            this.netClientHandler.addToSendQueue(new C09PacketHeldItemChange(this.currentPlayerItem));
        }
    }
    
    public boolean onPlayerRightClick(final EntityPlayer p_78760_1_, final World p_78760_2_, final ItemStack p_78760_3_, final int p_78760_4_, final int p_78760_5_, final int p_78760_6_, final int p_78760_7_, final Vec3 p_78760_8_) {
        this.syncCurrentPlayItem();
        final float var9 = (float)p_78760_8_.xCoord - p_78760_4_;
        final float var10 = (float)p_78760_8_.yCoord - p_78760_5_;
        final float var11 = (float)p_78760_8_.zCoord - p_78760_6_;
        boolean var12 = false;
        if ((!p_78760_1_.isSneaking() || p_78760_1_.getHeldItem() == null) && p_78760_2_.getBlock(p_78760_4_, p_78760_5_, p_78760_6_).onBlockActivated(p_78760_2_, p_78760_4_, p_78760_5_, p_78760_6_, p_78760_1_, p_78760_7_, var9, var10, var11)) {
            var12 = true;
        }
        if (!var12 && p_78760_3_ != null && p_78760_3_.getItem() instanceof ItemBlock) {
            final ItemBlock var13 = (ItemBlock)p_78760_3_.getItem();
            if (!var13.func_150936_a(p_78760_2_, p_78760_4_, p_78760_5_, p_78760_6_, p_78760_7_, p_78760_1_, p_78760_3_)) {
                return false;
            }
        }
        this.netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(p_78760_4_, p_78760_5_, p_78760_6_, p_78760_7_, p_78760_1_.inventory.getCurrentItem(), var9, var10, var11));
        if (var12) {
            return true;
        }
        if (p_78760_3_ == null) {
            return false;
        }
        if (this.currentGameType.isCreative()) {
            final int var14 = p_78760_3_.getItemDamage();
            final int var15 = p_78760_3_.stackSize;
            final boolean var16 = p_78760_3_.tryPlaceItemIntoWorld(p_78760_1_, p_78760_2_, p_78760_4_, p_78760_5_, p_78760_6_, p_78760_7_, var9, var10, var11);
            p_78760_3_.setItemDamage(var14);
            p_78760_3_.stackSize = var15;
            return var16;
        }
        return p_78760_3_.tryPlaceItemIntoWorld(p_78760_1_, p_78760_2_, p_78760_4_, p_78760_5_, p_78760_6_, p_78760_7_, var9, var10, var11);
    }
    
    public boolean sendUseItem(final EntityPlayer p_78769_1_, final World p_78769_2_, final ItemStack p_78769_3_) {
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new C08PacketPlayerBlockPlacement(-1, -1, -1, 255, p_78769_1_.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
        final int var4 = p_78769_3_.stackSize;
        final ItemStack var5 = p_78769_3_.useItemRightClick(p_78769_2_, p_78769_1_);
        if (var5 == p_78769_3_ && (var5 == null || var5.stackSize == var4)) {
            return false;
        }
        p_78769_1_.inventory.mainInventory[p_78769_1_.inventory.currentItem] = var5;
        if (var5.stackSize == 0) {
            p_78769_1_.inventory.mainInventory[p_78769_1_.inventory.currentItem] = null;
        }
        return true;
    }
    
    public EntityClientPlayerMP func_147493_a(final World p_147493_1_, final StatFileWriter p_147493_2_) {
        return new EntityClientPlayerMP(this.mc, p_147493_1_, this.mc.getSession(), this.netClientHandler, p_147493_2_);
    }
    
    public void attackEntity(final EntityPlayer p_78764_1_, final Entity p_78764_2_) {
        if (p_78764_1_ == this.mc.thePlayer) {
            final Vec3 vec3 = this.mc.renderViewEntity.getPosition(1.0f);
            final EntityRenderer entityRenderer = this.mc.entityRenderer;
            final double distanceTo = this.mc.objectMouseOver.hitVec.distanceTo(vec3);
            entityRenderer.lastRange = distanceTo;
            if (distanceTo > 3.0) {
                return;
            }
            this.mc.entityRenderer.lastAttackTime = System.currentTimeMillis();
        }
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new C02PacketUseEntity(p_78764_2_, C02PacketUseEntity.Action.ATTACK));
        p_78764_1_.attackTargetEntityWithCurrentItem(p_78764_2_);
    }
    
    public boolean interactWithEntitySendPacket(final EntityPlayer p_78768_1_, final Entity p_78768_2_) {
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new C02PacketUseEntity(p_78768_2_, C02PacketUseEntity.Action.INTERACT));
        return p_78768_1_.interactWith(p_78768_2_);
    }
    
    public ItemStack windowClick(final int p_78753_1_, final int p_78753_2_, final int p_78753_3_, final int p_78753_4_, final EntityPlayer p_78753_5_) {
        final short var6 = p_78753_5_.openContainer.getNextTransactionID(p_78753_5_.inventory);
        final ItemStack var7 = p_78753_5_.openContainer.slotClick(p_78753_2_, p_78753_3_, p_78753_4_, p_78753_5_);
        this.netClientHandler.addToSendQueue(new C0EPacketClickWindow(p_78753_1_, p_78753_2_, p_78753_3_, p_78753_4_, var7, var6));
        return var7;
    }
    
    public void sendEnchantPacket(final int p_78756_1_, final int p_78756_2_) {
        this.netClientHandler.addToSendQueue(new C11PacketEnchantItem(p_78756_1_, p_78756_2_));
    }
    
    public void sendSlotPacket(final ItemStack p_78761_1_, final int p_78761_2_) {
        if (this.currentGameType.isCreative()) {
            this.netClientHandler.addToSendQueue(new C10PacketCreativeInventoryAction(p_78761_2_, p_78761_1_));
        }
    }
    
    public void sendPacketDropItem(final ItemStack p_78752_1_) {
        if (this.currentGameType.isCreative() && p_78752_1_ != null) {
            this.netClientHandler.addToSendQueue(new C10PacketCreativeInventoryAction(-1, p_78752_1_));
        }
    }
    
    public void onStoppedUsingItem(final EntityPlayer p_78766_1_) {
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new C07PacketPlayerDigging(5, 0, 0, 0, 255));
        p_78766_1_.stopUsingItem();
    }
    
    public boolean gameIsSurvivalOrAdventure() {
        return this.currentGameType.isSurvivalOrAdventure();
    }
    
    public boolean isNotCreative() {
        return !this.currentGameType.isCreative();
    }
    
    public boolean isInCreativeMode() {
        return this.currentGameType.isCreative();
    }
    
    public boolean extendedReach() {
        return this.currentGameType.isCreative();
    }
    
    public boolean func_110738_j() {
        return this.mc.thePlayer.isRiding() && this.mc.thePlayer.ridingEntity instanceof EntityHorse;
    }
}
