package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.entity.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.*;
import net.minecraft.init.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.scoreboard.*;
import net.minecraft.entity.player.*;

public class RenderPlayer extends RendererLivingEntity
{
    private static final ResourceLocation steveTextures;
    private ModelBiped modelBipedMain;
    private ModelBiped modelArmorChestplate;
    private ModelBiped modelArmor;
    private static final String __OBFID = "CL_00001020";
    
    public RenderPlayer() {
        super(new ModelBiped(0.0f), 0.5f);
        this.modelBipedMain = (ModelBiped)this.mainModel;
        this.modelArmorChestplate = new ModelBiped(1.0f);
        this.modelArmor = new ModelBiped(0.5f);
    }
    
    public int shouldRenderPass(final AbstractClientPlayer p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        final ItemStack var4 = p_77032_1_.inventory.armorItemInSlot(3 - p_77032_2_);
        if (var4 != null) {
            final Item var5 = var4.getItem();
            if (var5 instanceof ItemArmor) {
                final ItemArmor var6 = (ItemArmor)var5;
                this.bindTexture(RenderBiped.func_110857_a(var6, p_77032_2_));
                final ModelBiped var7 = (p_77032_2_ == 2) ? this.modelArmor : this.modelArmorChestplate;
                var7.bipedHead.showModel = (p_77032_2_ == 0);
                var7.bipedHeadwear.showModel = (p_77032_2_ == 0);
                var7.bipedBody.showModel = (p_77032_2_ == 1 || p_77032_2_ == 2);
                var7.bipedRightArm.showModel = (p_77032_2_ == 1);
                var7.bipedLeftArm.showModel = (p_77032_2_ == 1);
                var7.bipedRightLeg.showModel = (p_77032_2_ == 2 || p_77032_2_ == 3);
                var7.bipedLeftLeg.showModel = (p_77032_2_ == 2 || p_77032_2_ == 3);
                this.setRenderPassModel(var7);
                var7.onGround = this.mainModel.onGround;
                var7.isRiding = this.mainModel.isRiding;
                var7.isChild = this.mainModel.isChild;
                if (var6.getArmorMaterial() == ItemArmor.ArmorMaterial.CLOTH) {
                    final int var8 = var6.getColor(var4);
                    final float var9 = (var8 >> 16 & 0xFF) / 255.0f;
                    final float var10 = (var8 >> 8 & 0xFF) / 255.0f;
                    final float var11 = (var8 & 0xFF) / 255.0f;
                    GL11.glColor3f(var9, var10, var11);
                    if (var4.isItemEnchanted()) {
                        return 31;
                    }
                    return 16;
                }
                else {
                    GL11.glColor3f(1.0f, 1.0f, 1.0f);
                    if (var4.isItemEnchanted()) {
                        return 15;
                    }
                    return 1;
                }
            }
        }
        return -1;
    }
    
    protected void func_82408_c(final AbstractClientPlayer p_82408_1_, final int p_82408_2_, final float p_82408_3_) {
        final ItemStack var4 = p_82408_1_.inventory.armorItemInSlot(3 - p_82408_2_);
        if (var4 != null) {
            final Item var5 = var4.getItem();
            if (var5 instanceof ItemArmor) {
                this.bindTexture(RenderBiped.func_110858_a((ItemArmor)var5, p_82408_2_, "overlay"));
                GL11.glColor3f(1.0f, 1.0f, 1.0f);
            }
        }
    }
    
    public void doRender(final AbstractClientPlayer p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        final ItemStack var10 = p_76986_1_.inventory.getCurrentItem();
        final ModelBiped modelArmorChestplate = this.modelArmorChestplate;
        final ModelBiped modelArmor = this.modelArmor;
        final ModelBiped modelBipedMain = this.modelBipedMain;
        final int heldItemRight;
        final int n = heldItemRight = ((var10 != null) ? 1 : 0);
        modelBipedMain.heldItemRight = n;
        modelArmor.heldItemRight = n;
        modelArmorChestplate.heldItemRight = heldItemRight;
        if (var10 != null && p_76986_1_.getItemInUseCount() > 0) {
            final EnumAction var11 = var10.getItemUseAction();
            if (var11 == EnumAction.block) {
                final ModelBiped modelArmorChestplate2 = this.modelArmorChestplate;
                final ModelBiped modelArmor2 = this.modelArmor;
                final ModelBiped modelBipedMain2 = this.modelBipedMain;
                final int heldItemRight2 = 3;
                modelBipedMain2.heldItemRight = heldItemRight2;
                modelArmor2.heldItemRight = heldItemRight2;
                modelArmorChestplate2.heldItemRight = heldItemRight2;
            }
            else if (var11 == EnumAction.bow) {
                final ModelBiped modelArmorChestplate3 = this.modelArmorChestplate;
                final ModelBiped modelArmor3 = this.modelArmor;
                final ModelBiped modelBipedMain3 = this.modelBipedMain;
                final boolean aimedBow = true;
                modelBipedMain3.aimedBow = aimedBow;
                modelArmor3.aimedBow = aimedBow;
                modelArmorChestplate3.aimedBow = aimedBow;
            }
        }
        final ModelBiped modelArmorChestplate4 = this.modelArmorChestplate;
        final ModelBiped modelArmor4 = this.modelArmor;
        final ModelBiped modelBipedMain4 = this.modelBipedMain;
        final boolean sneaking = p_76986_1_.isSneaking();
        modelBipedMain4.isSneak = sneaking;
        modelArmor4.isSneak = sneaking;
        modelArmorChestplate4.isSneak = sneaking;
        double var12 = p_76986_4_ - p_76986_1_.yOffset;
        if (p_76986_1_.isSneaking() && !(p_76986_1_ instanceof EntityPlayerSP)) {
            var12 -= 0.125;
        }
        super.doRender(p_76986_1_, p_76986_2_, var12, p_76986_6_, p_76986_8_, p_76986_9_);
        final ModelBiped modelArmorChestplate5 = this.modelArmorChestplate;
        final ModelBiped modelArmor5 = this.modelArmor;
        final ModelBiped modelBipedMain5 = this.modelBipedMain;
        final boolean aimedBow2 = false;
        modelBipedMain5.aimedBow = aimedBow2;
        modelArmor5.aimedBow = aimedBow2;
        modelArmorChestplate5.aimedBow = aimedBow2;
        final ModelBiped modelArmorChestplate6 = this.modelArmorChestplate;
        final ModelBiped modelArmor6 = this.modelArmor;
        final ModelBiped modelBipedMain6 = this.modelBipedMain;
        final boolean isSneak = false;
        modelBipedMain6.isSneak = isSneak;
        modelArmor6.isSneak = isSneak;
        modelArmorChestplate6.isSneak = isSneak;
        final ModelBiped modelArmorChestplate7 = this.modelArmorChestplate;
        final ModelBiped modelArmor7 = this.modelArmor;
        final ModelBiped modelBipedMain7 = this.modelBipedMain;
        final int heldItemRight3 = 0;
        modelBipedMain7.heldItemRight = heldItemRight3;
        modelArmor7.heldItemRight = heldItemRight3;
        modelArmorChestplate7.heldItemRight = heldItemRight3;
    }
    
    public ResourceLocation getEntityTexture(final AbstractClientPlayer p_110775_1_) {
        return p_110775_1_.getLocationSkin();
    }
    
    protected void renderEquippedItems(final AbstractClientPlayer p_77029_1_, final float p_77029_2_) {
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        super.renderEquippedItems(p_77029_1_, p_77029_2_);
        super.renderArrowsStuckInEntity(p_77029_1_, p_77029_2_);
        final ItemStack var3 = p_77029_1_.inventory.armorItemInSlot(3);
        if (var3 != null) {
            GL11.glPushMatrix();
            this.modelBipedMain.bipedHead.postRender(0.0625f);
            if (var3.getItem() instanceof ItemBlock) {
                if (RenderBlocks.renderItemIn3d(Block.getBlockFromItem(var3.getItem()).getRenderType())) {
                    final float var4 = 0.625f;
                    GL11.glTranslatef(0.0f, -0.25f, 0.0f);
                    GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                    GL11.glScalef(var4, -var4, -var4);
                }
                this.renderManager.itemRenderer.renderItem(p_77029_1_, var3, 0);
            }
            else if (var3.getItem() == Items.skull) {
                final float var4 = 1.0625f;
                GL11.glScalef(var4, -var4, -var4);
                GameProfile var5 = null;
                if (var3.hasTagCompound()) {
                    final NBTTagCompound var6 = var3.getTagCompound();
                    if (var6.func_150297_b("SkullOwner", 10)) {
                        var5 = NBTUtil.func_152459_a(var6.getCompoundTag("SkullOwner"));
                    }
                    else if (var6.func_150297_b("SkullOwner", 8) && !StringUtils.isNullOrEmpty(var6.getString("SkullOwner"))) {
                        var5 = new GameProfile((UUID)null, var6.getString("SkullOwner"));
                    }
                }
                TileEntitySkullRenderer.field_147536_b.func_152674_a(-0.5f, 0.0f, -0.5f, 1, 180.0f, var3.getItemDamage(), var5);
            }
            GL11.glPopMatrix();
        }
        if (p_77029_1_.getCommandSenderName().equals("deadmau5") && p_77029_1_.func_152123_o()) {
            this.bindTexture(p_77029_1_.getLocationSkin());
            for (int var7 = 0; var7 < 2; ++var7) {
                final float var8 = p_77029_1_.prevRotationYaw + (p_77029_1_.rotationYaw - p_77029_1_.prevRotationYaw) * p_77029_2_ - (p_77029_1_.prevRenderYawOffset + (p_77029_1_.renderYawOffset - p_77029_1_.prevRenderYawOffset) * p_77029_2_);
                final float var9 = p_77029_1_.prevRotationPitch + (p_77029_1_.rotationPitch - p_77029_1_.prevRotationPitch) * p_77029_2_;
                GL11.glPushMatrix();
                GL11.glRotatef(var8, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(var9, 1.0f, 0.0f, 0.0f);
                GL11.glTranslatef(0.375f * (var7 * 2 - 1), 0.0f, 0.0f);
                GL11.glTranslatef(0.0f, -0.375f, 0.0f);
                GL11.glRotatef(-var9, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(-var8, 0.0f, 1.0f, 0.0f);
                final float var10 = 1.3333334f;
                GL11.glScalef(var10, var10, var10);
                this.modelBipedMain.renderEars(0.0625f);
                GL11.glPopMatrix();
            }
        }
        final boolean var11 = p_77029_1_.func_152122_n();
        if (var11 && !p_77029_1_.isInvisible() && !p_77029_1_.getHideCape()) {
            this.bindTexture(p_77029_1_.getLocationCape());
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, 0.0f, 0.125f);
            final double var12 = p_77029_1_.field_71091_bM + (p_77029_1_.field_71094_bP - p_77029_1_.field_71091_bM) * p_77029_2_ - (p_77029_1_.prevPosX + (p_77029_1_.posX - p_77029_1_.prevPosX) * p_77029_2_);
            final double var13 = p_77029_1_.field_71096_bN + (p_77029_1_.field_71095_bQ - p_77029_1_.field_71096_bN) * p_77029_2_ - (p_77029_1_.prevPosY + (p_77029_1_.posY - p_77029_1_.prevPosY) * p_77029_2_);
            final double var14 = p_77029_1_.field_71097_bO + (p_77029_1_.field_71085_bR - p_77029_1_.field_71097_bO) * p_77029_2_ - (p_77029_1_.prevPosZ + (p_77029_1_.posZ - p_77029_1_.prevPosZ) * p_77029_2_);
            final float var15 = p_77029_1_.prevRenderYawOffset + (p_77029_1_.renderYawOffset - p_77029_1_.prevRenderYawOffset) * p_77029_2_;
            final double var16 = MathHelper.sin(var15 * 3.1415927f / 180.0f);
            final double var17 = -MathHelper.cos(var15 * 3.1415927f / 180.0f);
            float var18 = (float)var13 * 10.0f;
            if (var18 < -6.0f) {
                var18 = -6.0f;
            }
            if (var18 > 32.0f) {
                var18 = 32.0f;
            }
            float var19 = (float)(var12 * var16 + var14 * var17) * 100.0f;
            final float var20 = (float)(var12 * var17 - var14 * var16) * 100.0f;
            if (var19 < 0.0f) {
                var19 = 0.0f;
            }
            final float var21 = p_77029_1_.prevCameraYaw + (p_77029_1_.cameraYaw - p_77029_1_.prevCameraYaw) * p_77029_2_;
            var18 += MathHelper.sin((p_77029_1_.prevDistanceWalkedModified + (p_77029_1_.distanceWalkedModified - p_77029_1_.prevDistanceWalkedModified) * p_77029_2_) * 6.0f) * 32.0f * var21;
            if (p_77029_1_.isSneaking()) {
                var18 += 25.0f;
            }
            GL11.glRotatef(6.0f + var19 / 2.0f + var18, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(var20 / 2.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(-var20 / 2.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
            this.modelBipedMain.renderCloak(0.0625f);
            GL11.glPopMatrix();
        }
        ItemStack var22 = p_77029_1_.inventory.getCurrentItem();
        if (var22 != null) {
            GL11.glPushMatrix();
            this.modelBipedMain.bipedRightArm.postRender(0.0625f);
            GL11.glTranslatef(-0.0625f, 0.4375f, 0.0625f);
            if (p_77029_1_.fishEntity != null) {
                var22 = new ItemStack(Items.stick);
            }
            EnumAction var23 = null;
            if (p_77029_1_.getItemInUseCount() > 0) {
                var23 = var22.getItemUseAction();
            }
            if (var22.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(var22.getItem()).getRenderType())) {
                float var10 = 0.5f;
                GL11.glTranslatef(0.0f, 0.1875f, -0.3125f);
                var10 *= 0.75f;
                GL11.glRotatef(20.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
                GL11.glScalef(-var10, -var10, var10);
            }
            else if (var22.getItem() == Items.bow) {
                final float var10 = 0.625f;
                GL11.glTranslatef(0.0f, 0.125f, 0.3125f);
                GL11.glRotatef(-20.0f, 0.0f, 1.0f, 0.0f);
                GL11.glScalef(var10, -var10, var10);
                GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            }
            else if (var22.getItem().isFull3D()) {
                final float var10 = 0.625f;
                if (var22.getItem().shouldRotateAroundWhenRendering()) {
                    GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
                    GL11.glTranslatef(0.0f, -0.125f, 0.0f);
                }
                if (p_77029_1_.getItemInUseCount() > 0 && var23 == EnumAction.block) {
                    GL11.glTranslatef(0.05f, 0.0f, -0.1f);
                    GL11.glRotatef(-50.0f, 0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(-10.0f, 1.0f, 0.0f, 0.0f);
                    GL11.glRotatef(-60.0f, 0.0f, 0.0f, 1.0f);
                }
                GL11.glTranslatef(0.0f, 0.1875f, 0.0f);
                GL11.glScalef(var10, -var10, var10);
                GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            }
            else {
                final float var10 = 0.375f;
                GL11.glTranslatef(0.25f, 0.1875f, -0.1875f);
                GL11.glScalef(var10, var10, var10);
                GL11.glRotatef(60.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(20.0f, 0.0f, 0.0f, 1.0f);
            }
            if (var22.getItem().requiresMultipleRenderPasses()) {
                for (int var24 = 0; var24 <= 1; ++var24) {
                    final int var25 = var22.getItem().getColorFromItemStack(var22, var24);
                    final float var26 = (var25 >> 16 & 0xFF) / 255.0f;
                    final float var27 = (var25 >> 8 & 0xFF) / 255.0f;
                    final float var15 = (var25 & 0xFF) / 255.0f;
                    GL11.glColor4f(var26, var27, var15, 1.0f);
                    this.renderManager.itemRenderer.renderItem(p_77029_1_, var22, var24);
                }
            }
            else {
                final int var24 = var22.getItem().getColorFromItemStack(var22, 0);
                final float var28 = (var24 >> 16 & 0xFF) / 255.0f;
                final float var26 = (var24 >> 8 & 0xFF) / 255.0f;
                final float var27 = (var24 & 0xFF) / 255.0f;
                GL11.glColor4f(var28, var26, var27, 1.0f);
                this.renderManager.itemRenderer.renderItem(p_77029_1_, var22, 0);
            }
            GL11.glPopMatrix();
        }
    }
    
    public void preRenderCallback(final AbstractClientPlayer p_77041_1_, final float p_77041_2_) {
        final float var3 = 0.9375f;
        GL11.glScalef(var3, var3, var3);
    }
    
    protected void func_96449_a(final AbstractClientPlayer p_96449_1_, final double p_96449_2_, double p_96449_4_, final double p_96449_6_, final String p_96449_8_, final float p_96449_9_, final double p_96449_10_) {
        if (p_96449_10_ < 100.0) {
            final Scoreboard var12 = p_96449_1_.getWorldScoreboard();
            final ScoreObjective var13 = var12.func_96539_a(2);
            if (var13 != null) {
                final Score var14 = var12.func_96529_a(p_96449_1_.getCommandSenderName(), var13);
                if (p_96449_1_.isPlayerSleeping()) {
                    this.func_147906_a(p_96449_1_, var14.getScorePoints() + " " + var13.getDisplayName(), p_96449_2_, p_96449_4_ - 1.5, p_96449_6_, 64);
                }
                else {
                    this.func_147906_a(p_96449_1_, var14.getScorePoints() + " " + var13.getDisplayName(), p_96449_2_, p_96449_4_, p_96449_6_, 64);
                }
                p_96449_4_ += this.getFontRendererFromRenderManager().FONT_HEIGHT * 1.15f * p_96449_9_;
            }
        }
        super.func_96449_a(p_96449_1_, p_96449_2_, p_96449_4_, p_96449_6_, p_96449_8_, p_96449_9_, p_96449_10_);
    }
    
    public void renderFirstPersonArm(final EntityPlayer p_82441_1_) {
        final float var2 = 1.0f;
        GL11.glColor3f(var2, var2, var2);
        this.modelBipedMain.onGround = 0.0f;
        this.modelBipedMain.setRotationAngles(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f, p_82441_1_);
        this.modelBipedMain.bipedRightArm.render(0.0625f);
    }
    
    protected void renderLivingAt(final AbstractClientPlayer p_77039_1_, final double p_77039_2_, final double p_77039_4_, final double p_77039_6_) {
        if (p_77039_1_.isEntityAlive() && p_77039_1_.isPlayerSleeping()) {
            super.renderLivingAt(p_77039_1_, p_77039_2_ + p_77039_1_.field_71079_bU, p_77039_4_ + p_77039_1_.field_71082_cx, p_77039_6_ + p_77039_1_.field_71089_bV);
        }
        else {
            super.renderLivingAt(p_77039_1_, p_77039_2_, p_77039_4_, p_77039_6_);
        }
    }
    
    protected void rotateCorpse(final AbstractClientPlayer p_77043_1_, final float p_77043_2_, final float p_77043_3_, final float p_77043_4_) {
        if (p_77043_1_.isEntityAlive() && p_77043_1_.isPlayerSleeping()) {
            GL11.glRotatef(p_77043_1_.getBedOrientationInDegrees(), 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(this.getDeathMaxRotation(p_77043_1_), 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
        }
        else {
            super.rotateCorpse(p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
        }
    }
    
    @Override
    protected void func_96449_a(final EntityLivingBase p_96449_1_, final double p_96449_2_, final double p_96449_4_, final double p_96449_6_, final String p_96449_8_, final float p_96449_9_, final double p_96449_10_) {
        this.func_96449_a((AbstractClientPlayer)p_96449_1_, p_96449_2_, p_96449_4_, p_96449_6_, p_96449_8_, p_96449_9_, p_96449_10_);
    }
    
    @Override
    public void preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.preRenderCallback((AbstractClientPlayer)p_77041_1_, p_77041_2_);
    }
    
    @Override
    protected void func_82408_c(final EntityLivingBase p_82408_1_, final int p_82408_2_, final float p_82408_3_) {
        this.func_82408_c((AbstractClientPlayer)p_82408_1_, p_82408_2_, p_82408_3_);
    }
    
    @Override
    public int shouldRenderPass(final EntityLivingBase p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        return this.shouldRenderPass((AbstractClientPlayer)p_77032_1_, p_77032_2_, p_77032_3_);
    }
    
    @Override
    protected void renderEquippedItems(final EntityLivingBase p_77029_1_, final float p_77029_2_) {
        this.renderEquippedItems((AbstractClientPlayer)p_77029_1_, p_77029_2_);
    }
    
    @Override
    protected void rotateCorpse(final EntityLivingBase p_77043_1_, final float p_77043_2_, final float p_77043_3_, final float p_77043_4_) {
        this.rotateCorpse((AbstractClientPlayer)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }
    
    @Override
    protected void renderLivingAt(final EntityLivingBase p_77039_1_, final double p_77039_2_, final double p_77039_4_, final double p_77039_6_) {
        this.renderLivingAt((AbstractClientPlayer)p_77039_1_, p_77039_2_, p_77039_4_, p_77039_6_);
    }
    
    @Override
    public void doRender(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((AbstractClientPlayer)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((AbstractClientPlayer)p_110775_1_);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((AbstractClientPlayer)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    static {
        steveTextures = new ResourceLocation("textures/entity/steve.png");
    }
}
