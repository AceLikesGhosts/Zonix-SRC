package net.minecraft.client.entity;

import net.minecraft.client.network.*;
import net.minecraft.client.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.audio.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.entity.item.*;
import net.minecraft.item.*;
import net.minecraft.stats.*;
import net.minecraft.network.play.client.*;

public class EntityClientPlayerMP extends EntityPlayerSP
{
    public final NetHandlerPlayClient sendQueue;
    private final StatFileWriter field_146108_bO;
    private double oldPosX;
    private double oldMinY;
    private double oldPosY;
    private double oldPosZ;
    private float oldRotationYaw;
    private float oldRotationPitch;
    private boolean wasOnGround;
    private boolean shouldStopSneaking;
    private boolean wasSneaking;
    private int ticksSinceMovePacket;
    private boolean hasSetHealth;
    private String field_142022_ce;
    private static final String __OBFID = "CL_00000887";
    public long lastAttackTime;
    public long lastComboTime;
    public int lastAttacked;
    public int lastCombo;
    public int combo;
    
    public EntityClientPlayerMP(final Minecraft p_i45064_1_, final World p_i45064_2_, final Session p_i45064_3_, final NetHandlerPlayClient p_i45064_4_, final StatFileWriter p_i45064_5_) {
        super(p_i45064_1_, p_i45064_2_, p_i45064_3_, 0);
        this.lastAttacked = -1;
        this.sendQueue = p_i45064_4_;
        this.field_146108_bO = p_i45064_5_;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource p_70097_1_, final float p_70097_2_) {
        return false;
    }
    
    @Override
    public void heal(final float p_70691_1_) {
    }
    
    @Override
    public void mountEntity(final Entity p_70078_1_) {
        super.mountEntity(p_70078_1_);
        if (p_70078_1_ instanceof EntityMinecart) {
            this.mc.getSoundHandler().playSound(new MovingSoundMinecartRiding(this, (EntityMinecart)p_70078_1_));
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.worldObj.blockExists(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ))) {
            super.onUpdate();
            if (this.isRiding()) {
                this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
                this.sendQueue.addToSendQueue(new C0CPacketInput(this.moveStrafing, this.moveForward, this.movementInput.jump, this.movementInput.sneak));
            }
            else {
                this.sendMotionUpdates();
            }
        }
    }
    
    public void sendMotionUpdates() {
        final boolean var1 = this.isSprinting();
        if (var1 != this.wasSneaking) {
            if (var1) {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, 4));
            }
            else {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, 5));
            }
            this.wasSneaking = var1;
        }
        final boolean var2 = this.isSneaking();
        if (var2 != this.shouldStopSneaking) {
            if (var2) {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, 1));
            }
            else {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, 2));
            }
            this.shouldStopSneaking = var2;
        }
        final double var3 = this.posX - this.oldPosX;
        final double var4 = this.boundingBox.minY - this.oldMinY;
        final double var5 = this.posZ - this.oldPosZ;
        final double var6 = this.rotationYaw - this.oldRotationYaw;
        final double var7 = this.rotationPitch - this.oldRotationPitch;
        boolean var8 = var3 * var3 + var4 * var4 + var5 * var5 > 9.0E-4 || this.ticksSinceMovePacket >= 20;
        final boolean var9 = var6 != 0.0 || var7 != 0.0;
        if (this.ridingEntity != null) {
            this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.motionX, -999.0, -999.0, this.motionZ, this.rotationYaw, this.rotationPitch, this.onGround));
            var8 = false;
        }
        else if (var8 && var9) {
            this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.posX, this.boundingBox.minY, this.posY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround));
        }
        else if (var8) {
            this.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.posX, this.boundingBox.minY, this.posY, this.posZ, this.onGround));
        }
        else if (var9) {
            this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
        }
        else {
            this.sendQueue.addToSendQueue(new C03PacketPlayer(this.onGround));
        }
        ++this.ticksSinceMovePacket;
        this.wasOnGround = this.onGround;
        if (var8) {
            this.oldPosX = this.posX;
            this.oldMinY = this.boundingBox.minY;
            this.oldPosY = this.posY;
            this.oldPosZ = this.posZ;
            this.ticksSinceMovePacket = 0;
        }
        if (var9) {
            this.oldRotationYaw = this.rotationYaw;
            this.oldRotationPitch = this.rotationPitch;
        }
    }
    
    @Override
    public EntityItem dropOneItem(final boolean p_71040_1_) {
        final int var2 = p_71040_1_ ? 3 : 4;
        this.sendQueue.addToSendQueue(new C07PacketPlayerDigging(var2, 0, 0, 0, 0));
        return null;
    }
    
    @Override
    protected void joinEntityItemWithWorld(final EntityItem p_71012_1_) {
    }
    
    public void sendChatMessage(final String p_71165_1_) {
        this.sendQueue.addToSendQueue(new C01PacketChatMessage(p_71165_1_));
    }
    
    @Override
    public void swingItem() {
        super.swingItem();
        this.sendQueue.addToSendQueue(new C0APacketAnimation(this, 1));
    }
    
    @Override
    public void respawnPlayer() {
        this.sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
    }
    
    @Override
    protected void damageEntity(final DamageSource p_70665_1_, final float p_70665_2_) {
        if (!this.isEntityInvulnerable()) {
            this.setHealth(this.getHealth() - p_70665_2_);
        }
    }
    
    @Override
    public void closeScreen() {
        this.sendQueue.addToSendQueue(new C0DPacketCloseWindow(this.openContainer.windowId));
        this.closeScreenNoPacket();
    }
    
    public void closeScreenNoPacket() {
        this.inventory.setItemStack(null);
        super.closeScreen();
    }
    
    @Override
    public void setPlayerSPHealth(final float p_71150_1_) {
        if (this.hasSetHealth) {
            super.setPlayerSPHealth(p_71150_1_);
        }
        else {
            this.setHealth(p_71150_1_);
            this.hasSetHealth = true;
        }
    }
    
    @Override
    public void addStat(final StatBase p_71064_1_, final int p_71064_2_) {
        if (p_71064_1_ != null && p_71064_1_.isIndependent) {
            super.addStat(p_71064_1_, p_71064_2_);
        }
    }
    
    @Override
    public void sendPlayerAbilities() {
        this.sendQueue.addToSendQueue(new C13PacketPlayerAbilities(this.capabilities));
    }
    
    @Override
    protected void func_110318_g() {
        this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, 6, (int)(this.getHorseJumpPower() * 100.0f)));
    }
    
    public void func_110322_i() {
        this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, 7));
    }
    
    public void func_142020_c(final String p_142020_1_) {
        this.field_142022_ce = p_142020_1_;
    }
    
    public String func_142021_k() {
        return this.field_142022_ce;
    }
    
    public StatFileWriter func_146107_m() {
        return this.field_146108_bO;
    }
}
