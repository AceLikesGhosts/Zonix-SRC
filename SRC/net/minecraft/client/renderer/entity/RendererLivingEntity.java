package net.minecraft.client.renderer.entity;

import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.entity.projectile.*;
import java.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import org.apache.logging.log4j.*;

public abstract class RendererLivingEntity extends Render
{
    private static final Logger logger;
    private static final ResourceLocation RES_ITEM_GLINT;
    public ModelBase mainModel;
    public ModelBase renderPassModel;
    private static final String __OBFID = "CL_00001012";
    
    public RendererLivingEntity(final ModelBase p_i1261_1_, final float p_i1261_2_) {
        this.mainModel = p_i1261_1_;
        this.shadowSize = p_i1261_2_;
    }
    
    public void setRenderPassModel(final ModelBase p_77042_1_) {
        this.renderPassModel = p_77042_1_;
    }
    
    private float interpolateRotation(final float p_77034_1_, final float p_77034_2_, final float p_77034_3_) {
        float var4;
        for (var4 = p_77034_2_ - p_77034_1_; var4 < -180.0f; var4 += 360.0f) {}
        while (var4 >= 180.0f) {
            var4 -= 360.0f;
        }
        return p_77034_1_ + p_77034_3_ * var4;
    }
    
    public void doRender(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        this.mainModel.onGround = this.renderSwingProgress(p_76986_1_, p_76986_9_);
        if (this.renderPassModel != null) {
            this.renderPassModel.onGround = this.mainModel.onGround;
        }
        this.mainModel.isRiding = p_76986_1_.isRiding();
        if (this.renderPassModel != null) {
            this.renderPassModel.isRiding = this.mainModel.isRiding;
        }
        this.mainModel.isChild = p_76986_1_.isChild();
        if (this.renderPassModel != null) {
            this.renderPassModel.isChild = this.mainModel.isChild;
        }
        try {
            float var10 = this.interpolateRotation(p_76986_1_.prevRenderYawOffset, p_76986_1_.renderYawOffset, p_76986_9_);
            final float var11 = this.interpolateRotation(p_76986_1_.prevRotationYawHead, p_76986_1_.rotationYawHead, p_76986_9_);
            if (p_76986_1_.isRiding() && p_76986_1_.ridingEntity instanceof EntityLivingBase) {
                final EntityLivingBase var12 = (EntityLivingBase)p_76986_1_.ridingEntity;
                var10 = this.interpolateRotation(var12.prevRenderYawOffset, var12.renderYawOffset, p_76986_9_);
                float var13 = MathHelper.wrapAngleTo180_float(var11 - var10);
                if (var13 < -85.0f) {
                    var13 = -85.0f;
                }
                if (var13 >= 85.0f) {
                    var13 = 85.0f;
                }
                var10 = var11 - var13;
                if (var13 * var13 > 2500.0f) {
                    var10 += var13 * 0.2f;
                }
            }
            final float var14 = p_76986_1_.prevRotationPitch + (p_76986_1_.rotationPitch - p_76986_1_.prevRotationPitch) * p_76986_9_;
            this.renderLivingAt(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_);
            float var13 = this.handleRotationFloat(p_76986_1_, p_76986_9_);
            this.rotateCorpse(p_76986_1_, var13, var10, p_76986_9_);
            final float var15 = 0.0625f;
            GL11.glEnable(32826);
            GL11.glScalef(-1.0f, -1.0f, 1.0f);
            this.preRenderCallback(p_76986_1_, p_76986_9_);
            GL11.glTranslatef(0.0f, -24.0f * var15 - 0.0078125f, 0.0f);
            float var16 = p_76986_1_.prevLimbSwingAmount + (p_76986_1_.limbSwingAmount - p_76986_1_.prevLimbSwingAmount) * p_76986_9_;
            float var17 = p_76986_1_.limbSwing - p_76986_1_.limbSwingAmount * (1.0f - p_76986_9_);
            if (p_76986_1_.isChild()) {
                var17 *= 3.0f;
            }
            if (var16 > 1.0f) {
                var16 = 1.0f;
            }
            GL11.glEnable(3008);
            this.mainModel.setLivingAnimations(p_76986_1_, var17, var16, p_76986_9_);
            this.renderModel(p_76986_1_, var17, var16, var13, var11 - var10, var14, var15);
            for (int var18 = 0; var18 < 4; ++var18) {
                final int var19 = this.shouldRenderPass(p_76986_1_, var18, p_76986_9_);
                if (var19 > 0) {
                    this.renderPassModel.setLivingAnimations(p_76986_1_, var17, var16, p_76986_9_);
                    this.renderPassModel.render(p_76986_1_, var17, var16, var13, var11 - var10, var14, var15);
                    if ((var19 & 0xF0) == 0x10) {
                        this.func_82408_c(p_76986_1_, var18, p_76986_9_);
                        this.renderPassModel.render(p_76986_1_, var17, var16, var13, var11 - var10, var14, var15);
                    }
                    if ((var19 & 0xF) == 0xF) {
                        final float var20 = p_76986_1_.ticksExisted + p_76986_9_;
                        this.bindTexture(RendererLivingEntity.RES_ITEM_GLINT);
                        GL11.glEnable(3042);
                        final float var21 = 0.5f;
                        GL11.glColor4f(var21, var21, var21, 1.0f);
                        GL11.glDepthFunc(514);
                        GL11.glDepthMask(false);
                        for (int var22 = 0; var22 < 2; ++var22) {
                            GL11.glDisable(2896);
                            final float var23 = 0.76f;
                            GL11.glColor4f(0.5f * var23, 0.25f * var23, 0.8f * var23, 1.0f);
                            GL11.glBlendFunc(768, 1);
                            GL11.glMatrixMode(5890);
                            GL11.glLoadIdentity();
                            final float var24 = var20 * (0.001f + var22 * 0.003f) * 20.0f;
                            final float var25 = 0.33333334f;
                            GL11.glScalef(var25, var25, var25);
                            GL11.glRotatef(30.0f - var22 * 60.0f, 0.0f, 0.0f, 1.0f);
                            GL11.glTranslatef(0.0f, var24, 0.0f);
                            GL11.glMatrixMode(5888);
                            this.renderPassModel.render(p_76986_1_, var17, var16, var13, var11 - var10, var14, var15);
                        }
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        GL11.glMatrixMode(5890);
                        GL11.glDepthMask(true);
                        GL11.glLoadIdentity();
                        GL11.glMatrixMode(5888);
                        GL11.glEnable(2896);
                        GL11.glDisable(3042);
                        GL11.glDepthFunc(515);
                    }
                    GL11.glDisable(3042);
                    GL11.glEnable(3008);
                }
            }
            GL11.glDepthMask(true);
            this.renderEquippedItems(p_76986_1_, p_76986_9_);
            final float var26 = p_76986_1_.getBrightness(p_76986_9_);
            final int var19 = this.getColorMultiplier(p_76986_1_, var26, p_76986_9_);
            OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GL11.glDisable(3553);
            OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
            if ((var19 >> 24 & 0xFF) > 0 || p_76986_1_.hurtTime > 0 || p_76986_1_.deathTime > 0) {
                GL11.glDisable(3553);
                GL11.glDisable(3008);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glDepthFunc(514);
                if (p_76986_1_.hurtTime > 0 || p_76986_1_.deathTime > 0) {
                    GL11.glColor4f(var26, 0.0f, 0.0f, 0.4f);
                    this.mainModel.render(p_76986_1_, var17, var16, var13, var11 - var10, var14, var15);
                    for (int var27 = 0; var27 < 4; ++var27) {
                        if (this.inheritRenderPass(p_76986_1_, var27, p_76986_9_) >= 0) {
                            GL11.glColor4f(var26, 0.0f, 0.0f, 0.4f);
                            this.renderPassModel.render(p_76986_1_, var17, var16, var13, var11 - var10, var14, var15);
                        }
                    }
                }
                if ((var19 >> 24 & 0xFF) > 0) {
                    final float var20 = (var19 >> 16 & 0xFF) / 255.0f;
                    final float var21 = (var19 >> 8 & 0xFF) / 255.0f;
                    final float var28 = (var19 & 0xFF) / 255.0f;
                    final float var23 = (var19 >> 24 & 0xFF) / 255.0f;
                    GL11.glColor4f(var20, var21, var28, var23);
                    this.mainModel.render(p_76986_1_, var17, var16, var13, var11 - var10, var14, var15);
                    for (int var29 = 0; var29 < 4; ++var29) {
                        if (this.inheritRenderPass(p_76986_1_, var29, p_76986_9_) >= 0) {
                            GL11.glColor4f(var20, var21, var28, var23);
                            this.renderPassModel.render(p_76986_1_, var17, var16, var13, var11 - var10, var14, var15);
                        }
                    }
                }
                GL11.glDepthFunc(515);
                GL11.glDisable(3042);
                GL11.glEnable(3008);
                GL11.glEnable(3553);
            }
            GL11.glDisable(32826);
        }
        catch (Exception var30) {
            RendererLivingEntity.logger.error("Couldn't render entity", (Throwable)var30);
        }
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glEnable(3553);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GL11.glEnable(2884);
        GL11.glPopMatrix();
        this.passSpecialRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_);
    }
    
    protected void renderModel(final EntityLivingBase p_77036_1_, final float p_77036_2_, final float p_77036_3_, final float p_77036_4_, final float p_77036_5_, final float p_77036_6_, final float p_77036_7_) {
        this.bindEntityTexture(p_77036_1_);
        if (!p_77036_1_.isInvisible()) {
            this.mainModel.render(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
        }
        else if (!p_77036_1_.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer)) {
            GL11.glPushMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.15f);
            GL11.glDepthMask(false);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glAlphaFunc(516, 0.003921569f);
            this.mainModel.render(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
            GL11.glDisable(3042);
            GL11.glAlphaFunc(516, 0.1f);
            GL11.glPopMatrix();
            GL11.glDepthMask(true);
        }
        else {
            this.mainModel.setRotationAngles(p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_, p_77036_1_);
        }
    }
    
    protected void renderLivingAt(final EntityLivingBase p_77039_1_, final double p_77039_2_, final double p_77039_4_, final double p_77039_6_) {
        GL11.glTranslatef((float)p_77039_2_, (float)p_77039_4_, (float)p_77039_6_);
    }
    
    protected void rotateCorpse(final EntityLivingBase p_77043_1_, final float p_77043_2_, final float p_77043_3_, final float p_77043_4_) {
        GL11.glRotatef(180.0f - p_77043_3_, 0.0f, 1.0f, 0.0f);
        if (p_77043_1_.deathTime > 0) {
            float var5 = (p_77043_1_.deathTime + p_77043_4_ - 1.0f) / 20.0f * 1.6f;
            var5 = MathHelper.sqrt_float(var5);
            if (var5 > 1.0f) {
                var5 = 1.0f;
            }
            GL11.glRotatef(var5 * this.getDeathMaxRotation(p_77043_1_), 0.0f, 0.0f, 1.0f);
        }
        else {
            final String var6 = EnumChatFormatting.getTextWithoutFormattingCodes(p_77043_1_.getCommandSenderName());
            if ((var6.equals("Dinnerbone") || var6.equals("Grumm")) && (!(p_77043_1_ instanceof EntityPlayer) || !((EntityPlayer)p_77043_1_).getHideCape())) {
                GL11.glTranslatef(0.0f, p_77043_1_.height + 0.1f, 0.0f);
                GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
            }
        }
    }
    
    protected float renderSwingProgress(final EntityLivingBase p_77040_1_, final float p_77040_2_) {
        return p_77040_1_.getSwingProgress(p_77040_2_);
    }
    
    protected float handleRotationFloat(final EntityLivingBase p_77044_1_, final float p_77044_2_) {
        return p_77044_1_.ticksExisted + p_77044_2_;
    }
    
    protected void renderEquippedItems(final EntityLivingBase p_77029_1_, final float p_77029_2_) {
    }
    
    protected void renderArrowsStuckInEntity(final EntityLivingBase p_85093_1_, final float p_85093_2_) {
        final int var3 = p_85093_1_.getArrowCountInEntity();
        if (var3 > 0) {
            final EntityArrow var4 = new EntityArrow(p_85093_1_.worldObj, p_85093_1_.posX, p_85093_1_.posY, p_85093_1_.posZ);
            final Random var5 = new Random(p_85093_1_.getEntityId());
            RenderHelper.disableStandardItemLighting();
            for (int var6 = 0; var6 < var3; ++var6) {
                GL11.glPushMatrix();
                final ModelRenderer var7 = this.mainModel.getRandomModelBox(var5);
                final ModelBox var8 = var7.cubeList.get(var5.nextInt(var7.cubeList.size()));
                var7.postRender(0.0625f);
                float var9 = var5.nextFloat();
                float var10 = var5.nextFloat();
                float var11 = var5.nextFloat();
                final float var12 = (var8.posX1 + (var8.posX2 - var8.posX1) * var9) / 16.0f;
                final float var13 = (var8.posY1 + (var8.posY2 - var8.posY1) * var10) / 16.0f;
                final float var14 = (var8.posZ1 + (var8.posZ2 - var8.posZ1) * var11) / 16.0f;
                GL11.glTranslatef(var12, var13, var14);
                var9 = var9 * 2.0f - 1.0f;
                var10 = var10 * 2.0f - 1.0f;
                var11 = var11 * 2.0f - 1.0f;
                var9 *= -1.0f;
                var10 *= -1.0f;
                var11 *= -1.0f;
                final float var15 = MathHelper.sqrt_float(var9 * var9 + var11 * var11);
                final EntityArrow entityArrow = var4;
                final EntityArrow entityArrow2 = var4;
                final float n = (float)(Math.atan2(var9, var11) * 180.0 / 3.141592653589793);
                entityArrow2.rotationYaw = n;
                entityArrow.prevRotationYaw = n;
                final EntityArrow entityArrow3 = var4;
                final EntityArrow entityArrow4 = var4;
                final float n2 = (float)(Math.atan2(var10, var15) * 180.0 / 3.141592653589793);
                entityArrow4.rotationPitch = n2;
                entityArrow3.prevRotationPitch = n2;
                final double var16 = 0.0;
                final double var17 = 0.0;
                final double var18 = 0.0;
                final float var19 = 0.0f;
                this.renderManager.func_147940_a(var4, var16, var17, var18, var19, p_85093_2_);
                GL11.glPopMatrix();
            }
            RenderHelper.enableStandardItemLighting();
        }
    }
    
    protected int inheritRenderPass(final EntityLivingBase p_77035_1_, final int p_77035_2_, final float p_77035_3_) {
        return this.shouldRenderPass(p_77035_1_, p_77035_2_, p_77035_3_);
    }
    
    public int shouldRenderPass(final EntityLivingBase p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        return -1;
    }
    
    protected void func_82408_c(final EntityLivingBase p_82408_1_, final int p_82408_2_, final float p_82408_3_) {
    }
    
    protected float getDeathMaxRotation(final EntityLivingBase p_77037_1_) {
        return 90.0f;
    }
    
    protected int getColorMultiplier(final EntityLivingBase p_77030_1_, final float p_77030_2_, final float p_77030_3_) {
        return 0;
    }
    
    public void preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
    }
    
    protected void passSpecialRender(final EntityLivingBase p_77033_1_, final double p_77033_2_, final double p_77033_4_, final double p_77033_6_) {
        GL11.glAlphaFunc(516, 0.1f);
        if (this.func_110813_b(p_77033_1_)) {
            final float var8 = 1.6f;
            final float var9 = 0.016666668f * var8;
            final double var10 = p_77033_1_.getDistanceSqToEntity(this.renderManager.livingPlayer);
            final float var11 = p_77033_1_.isSneaking() ? 32.0f : 64.0f;
            if (var10 < var11 * var11) {
                final String var12 = p_77033_1_.func_145748_c_().getFormattedText();
                if (p_77033_1_.isSneaking()) {
                    final FontRenderer var13 = this.getFontRendererFromRenderManager();
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float)p_77033_2_ + 0.0f, (float)p_77033_4_ + p_77033_1_.height + 0.5f, (float)p_77033_6_);
                    GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(-this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
                    GL11.glScalef(-var9, -var9, var9);
                    GL11.glDisable(2896);
                    GL11.glTranslatef(0.0f, 0.25f / var9, 0.0f);
                    GL11.glDepthMask(false);
                    GL11.glEnable(3042);
                    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                    final Tessellator var14 = Tessellator.instance;
                    GL11.glDisable(3553);
                    var14.startDrawingQuads();
                    final int var15 = var13.getStringWidth(var12) / 2;
                    var14.setColorRGBA_F(0.0f, 0.0f, 0.0f, 0.25f);
                    var14.addVertex(-var15 - 1, -1.0, 0.0);
                    var14.addVertex(-var15 - 1, 8.0, 0.0);
                    var14.addVertex(var15 + 1, 8.0, 0.0);
                    var14.addVertex(var15 + 1, -1.0, 0.0);
                    var14.draw();
                    GL11.glEnable(3553);
                    GL11.glDepthMask(true);
                    var13.drawString(var12, -var13.getStringWidth(var12) / 2, 0, 553648127);
                    GL11.glEnable(2896);
                    GL11.glDisable(3042);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    GL11.glPopMatrix();
                }
                else {
                    this.func_96449_a(p_77033_1_, p_77033_2_, p_77033_4_, p_77033_6_, var12, var9, var10);
                }
            }
        }
    }
    
    protected boolean func_110813_b(final EntityLivingBase p_110813_1_) {
        return Minecraft.isGuiEnabled() && p_110813_1_ != this.renderManager.livingPlayer && !p_110813_1_.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer) && p_110813_1_.riddenByEntity == null;
    }
    
    protected void func_96449_a(final EntityLivingBase p_96449_1_, final double p_96449_2_, final double p_96449_4_, final double p_96449_6_, final String p_96449_8_, final float p_96449_9_, final double p_96449_10_) {
        if (p_96449_1_.isPlayerSleeping()) {
            this.func_147906_a(p_96449_1_, p_96449_8_, p_96449_2_, p_96449_4_ - 1.5, p_96449_6_, 64);
        }
        else {
            this.func_147906_a(p_96449_1_, p_96449_8_, p_96449_2_, p_96449_4_, p_96449_6_, 64);
        }
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityLivingBase)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    static {
        logger = LogManager.getLogger();
        RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    }
}
