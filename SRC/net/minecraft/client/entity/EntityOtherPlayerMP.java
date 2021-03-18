package net.minecraft.client.entity;

import net.minecraft.world.*;
import com.mojang.authlib.*;
import net.minecraft.item.*;
import net.minecraft.client.*;
import net.minecraft.util.*;

public class EntityOtherPlayerMP extends AbstractClientPlayer
{
    private boolean isItemInUse;
    private int otherPlayerMPPosRotationIncrements;
    private double otherPlayerMPX;
    private double otherPlayerMPY;
    private double otherPlayerMPZ;
    private double otherPlayerMPYaw;
    private double otherPlayerMPPitch;
    private static final String __OBFID = "CL_00000939";
    
    public EntityOtherPlayerMP(final World p_i45075_1_, final GameProfile p_i45075_2_) {
        super(p_i45075_1_, p_i45075_2_);
        this.yOffset = 0.0f;
        this.stepHeight = 0.0f;
        this.noClip = true;
        this.field_71082_cx = 0.25f;
        this.renderDistanceWeight = 10.0;
    }
    
    @Override
    protected void resetHeight() {
        this.yOffset = 0.0f;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource p_70097_1_, final float p_70097_2_) {
        return true;
    }
    
    @Override
    public void setPositionAndRotation2(final double p_70056_1_, final double p_70056_3_, final double p_70056_5_, final float p_70056_7_, final float p_70056_8_, final int p_70056_9_) {
        this.otherPlayerMPX = p_70056_1_;
        this.otherPlayerMPY = p_70056_3_;
        this.otherPlayerMPZ = p_70056_5_;
        this.otherPlayerMPYaw = p_70056_7_;
        this.otherPlayerMPPitch = p_70056_8_;
        this.otherPlayerMPPosRotationIncrements = p_70056_9_;
    }
    
    @Override
    public void onUpdate() {
        this.field_71082_cx = 0.0f;
        super.onUpdate();
        this.prevLimbSwingAmount = this.limbSwingAmount;
        final double var1 = this.posX - this.prevPosX;
        final double var2 = this.posZ - this.prevPosZ;
        float var3 = MathHelper.sqrt_double(var1 * var1 + var2 * var2) * 4.0f;
        if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        this.limbSwingAmount += (var3 - this.limbSwingAmount) * 0.4f;
        this.limbSwing += this.limbSwingAmount;
        if (!this.isItemInUse && this.isEating() && this.inventory.mainInventory[this.inventory.currentItem] != null) {
            final ItemStack var4 = this.inventory.mainInventory[this.inventory.currentItem];
            this.setItemInUse(this.inventory.mainInventory[this.inventory.currentItem], var4.getItem().getMaxItemUseDuration(var4));
            this.isItemInUse = true;
        }
        else if (this.isItemInUse && !this.isEating()) {
            this.clearItemInUse();
            this.isItemInUse = false;
        }
    }
    
    @Override
    public float getShadowSize() {
        return 0.0f;
    }
    
    @Override
    public void onLivingUpdate() {
        super.updateEntityActionState();
        if (this.otherPlayerMPPosRotationIncrements > 0) {
            final double var1 = this.posX + (this.otherPlayerMPX - this.posX) / this.otherPlayerMPPosRotationIncrements;
            final double var2 = this.posY + (this.otherPlayerMPY - this.posY) / this.otherPlayerMPPosRotationIncrements;
            final double var3 = this.posZ + (this.otherPlayerMPZ - this.posZ) / this.otherPlayerMPPosRotationIncrements;
            double var4;
            for (var4 = this.otherPlayerMPYaw - this.rotationYaw; var4 < -180.0; var4 += 360.0) {}
            while (var4 >= 180.0) {
                var4 -= 360.0;
            }
            this.rotationYaw += (float)(var4 / this.otherPlayerMPPosRotationIncrements);
            this.rotationPitch += (float)((this.otherPlayerMPPitch - this.rotationPitch) / this.otherPlayerMPPosRotationIncrements);
            --this.otherPlayerMPPosRotationIncrements;
            this.setPosition(var1, var2, var3);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
        this.prevCameraYaw = this.cameraYaw;
        float var5 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        float var6 = (float)Math.atan(-this.motionY * 0.20000000298023224) * 15.0f;
        if (var5 > 0.1f) {
            var5 = 0.1f;
        }
        if (!this.onGround || this.getHealth() <= 0.0f) {
            var5 = 0.0f;
        }
        if (this.onGround || this.getHealth() <= 0.0f) {
            var6 = 0.0f;
        }
        this.cameraYaw += (var5 - this.cameraYaw) * 0.4f;
        this.cameraPitch += (var6 - this.cameraPitch) * 0.8f;
    }
    
    @Override
    public void setCurrentItemOrArmor(final int p_70062_1_, final ItemStack p_70062_2_) {
        if (p_70062_1_ == 0) {
            this.inventory.mainInventory[this.inventory.currentItem] = p_70062_2_;
        }
        else {
            this.inventory.armorInventory[p_70062_1_ - 1] = p_70062_2_;
        }
    }
    
    @Override
    public float getEyeHeight() {
        return 1.82f;
    }
    
    @Override
    public void addChatMessage(final IChatComponent p_145747_1_) {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146227_a(p_145747_1_);
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int p_70003_1_, final String p_70003_2_) {
        return false;
    }
    
    @Override
    public ChunkCoordinates getPlayerCoordinates() {
        return new ChunkCoordinates(MathHelper.floor_double(this.posX + 0.5), MathHelper.floor_double(this.posY + 0.5), MathHelper.floor_double(this.posZ + 0.5));
    }
}
