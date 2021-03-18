package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.client.renderer.*;

public class RenderDragon extends RenderLiving
{
    private static final ResourceLocation enderDragonExplodingTextures;
    private static final ResourceLocation enderDragonCrystalBeamTextures;
    private static final ResourceLocation enderDragonEyesTextures;
    private static final ResourceLocation enderDragonTextures;
    protected ModelDragon modelDragon;
    private static final String __OBFID = "CL_00000988";
    
    public RenderDragon() {
        super(new ModelDragon(0.0f), 0.5f);
        this.modelDragon = (ModelDragon)this.mainModel;
        this.setRenderPassModel(this.mainModel);
    }
    
    protected void rotateCorpse(final EntityDragon p_77043_1_, final float p_77043_2_, final float p_77043_3_, final float p_77043_4_) {
        final float var5 = (float)p_77043_1_.getMovementOffsets(7, p_77043_4_)[0];
        final float var6 = (float)(p_77043_1_.getMovementOffsets(5, p_77043_4_)[1] - p_77043_1_.getMovementOffsets(10, p_77043_4_)[1]);
        GL11.glRotatef(-var5, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(var6 * 10.0f, 1.0f, 0.0f, 0.0f);
        GL11.glTranslatef(0.0f, 0.0f, 1.0f);
        if (p_77043_1_.deathTime > 0) {
            float var7 = (p_77043_1_.deathTime + p_77043_4_ - 1.0f) / 20.0f * 1.6f;
            var7 = MathHelper.sqrt_float(var7);
            if (var7 > 1.0f) {
                var7 = 1.0f;
            }
            GL11.glRotatef(var7 * this.getDeathMaxRotation(p_77043_1_), 0.0f, 0.0f, 1.0f);
        }
    }
    
    protected void renderModel(final EntityDragon p_77036_1_, final float p_77036_2_, final float p_77036_3_, final float p_77036_4_, final float p_77036_5_, final float p_77036_6_, final float p_77036_7_) {
        if (p_77036_1_.deathTicks > 0) {
            final float var8 = p_77036_1_.deathTicks / 200.0f;
            GL11.glDepthFunc(515);
            GL11.glEnable(3008);
            GL11.glAlphaFunc(516, var8);
            this.bindTexture(RenderDragon.enderDragonExplodingTextures);
            this.mainModel.render(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
            GL11.glAlphaFunc(516, 0.1f);
            GL11.glDepthFunc(514);
        }
        this.bindEntityTexture(p_77036_1_);
        this.mainModel.render(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
        if (p_77036_1_.hurtTime > 0) {
            GL11.glDepthFunc(514);
            GL11.glDisable(3553);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0f, 0.0f, 0.0f, 0.5f);
            this.mainModel.render(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
            GL11.glEnable(3553);
            GL11.glDisable(3042);
            GL11.glDepthFunc(515);
        }
    }
    
    public void doRender(final EntityDragon p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        BossStatus.setBossStatus(p_76986_1_, false);
        super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
        if (p_76986_1_.healingEnderCrystal != null) {
            final float var10 = p_76986_1_.healingEnderCrystal.innerRotation + p_76986_9_;
            float var11 = MathHelper.sin(var10 * 0.2f) / 2.0f + 0.5f;
            var11 = (var11 * var11 + var11) * 0.2f;
            final float var12 = (float)(p_76986_1_.healingEnderCrystal.posX - p_76986_1_.posX - (p_76986_1_.prevPosX - p_76986_1_.posX) * (1.0f - p_76986_9_));
            final float var13 = (float)(var11 + p_76986_1_.healingEnderCrystal.posY - 1.0 - p_76986_1_.posY - (p_76986_1_.prevPosY - p_76986_1_.posY) * (1.0f - p_76986_9_));
            final float var14 = (float)(p_76986_1_.healingEnderCrystal.posZ - p_76986_1_.posZ - (p_76986_1_.prevPosZ - p_76986_1_.posZ) * (1.0f - p_76986_9_));
            final float var15 = MathHelper.sqrt_float(var12 * var12 + var14 * var14);
            final float var16 = MathHelper.sqrt_float(var12 * var12 + var13 * var13 + var14 * var14);
            GL11.glPushMatrix();
            GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_ + 2.0f, (float)p_76986_6_);
            GL11.glRotatef((float)(-Math.atan2(var14, var12)) * 180.0f / 3.1415927f - 90.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef((float)(-Math.atan2(var15, var13)) * 180.0f / 3.1415927f - 90.0f, 1.0f, 0.0f, 0.0f);
            final Tessellator var17 = Tessellator.instance;
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(2884);
            this.bindTexture(RenderDragon.enderDragonCrystalBeamTextures);
            GL11.glShadeModel(7425);
            final float var18 = 0.0f - (p_76986_1_.ticksExisted + p_76986_9_) * 0.01f;
            final float var19 = MathHelper.sqrt_float(var12 * var12 + var13 * var13 + var14 * var14) / 32.0f - (p_76986_1_.ticksExisted + p_76986_9_) * 0.01f;
            var17.startDrawing(5);
            final byte var20 = 8;
            for (int var21 = 0; var21 <= var20; ++var21) {
                final float var22 = MathHelper.sin(var21 % var20 * 3.1415927f * 2.0f / var20) * 0.75f;
                final float var23 = MathHelper.cos(var21 % var20 * 3.1415927f * 2.0f / var20) * 0.75f;
                final float var24 = var21 % var20 * 1.0f / var20;
                var17.setColorOpaque_I(0);
                var17.addVertexWithUV(var22 * 0.2f, var23 * 0.2f, 0.0, var24, var19);
                var17.setColorOpaque_I(16777215);
                var17.addVertexWithUV(var22, var23, var16, var24, var18);
            }
            var17.draw();
            GL11.glEnable(2884);
            GL11.glShadeModel(7424);
            RenderHelper.enableStandardItemLighting();
            GL11.glPopMatrix();
        }
    }
    
    public ResourceLocation getEntityTexture(final EntityDragon p_110775_1_) {
        return RenderDragon.enderDragonTextures;
    }
    
    protected void renderEquippedItems(final EntityDragon p_77029_1_, final float p_77029_2_) {
        super.renderEquippedItems(p_77029_1_, p_77029_2_);
        final Tessellator var3 = Tessellator.instance;
        if (p_77029_1_.deathTicks > 0) {
            RenderHelper.disableStandardItemLighting();
            final float var4 = (p_77029_1_.deathTicks + p_77029_2_) / 200.0f;
            float var5 = 0.0f;
            if (var4 > 0.8f) {
                var5 = (var4 - 0.8f) / 0.2f;
            }
            final Random var6 = new Random(432L);
            GL11.glDisable(3553);
            GL11.glShadeModel(7425);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 1);
            GL11.glDisable(3008);
            GL11.glEnable(2884);
            GL11.glDepthMask(false);
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, -1.0f, -2.0f);
            for (int var7 = 0; var7 < (var4 + var4 * var4) / 2.0f * 60.0f; ++var7) {
                GL11.glRotatef(var6.nextFloat() * 360.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(var6.nextFloat() * 360.0f, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(var6.nextFloat() * 360.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(var6.nextFloat() * 360.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(var6.nextFloat() * 360.0f, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(var6.nextFloat() * 360.0f + var4 * 90.0f, 0.0f, 0.0f, 1.0f);
                var3.startDrawing(6);
                final float var8 = var6.nextFloat() * 20.0f + 5.0f + var5 * 10.0f;
                final float var9 = var6.nextFloat() * 2.0f + 1.0f + var5 * 2.0f;
                var3.setColorRGBA_I(16777215, (int)(255.0f * (1.0f - var5)));
                var3.addVertex(0.0, 0.0, 0.0);
                var3.setColorRGBA_I(16711935, 0);
                var3.addVertex(-0.866 * var9, var8, -0.5f * var9);
                var3.addVertex(0.866 * var9, var8, -0.5f * var9);
                var3.addVertex(0.0, var8, 1.0f * var9);
                var3.addVertex(-0.866 * var9, var8, -0.5f * var9);
                var3.draw();
            }
            GL11.glPopMatrix();
            GL11.glDepthMask(true);
            GL11.glDisable(2884);
            GL11.glDisable(3042);
            GL11.glShadeModel(7424);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glEnable(3553);
            GL11.glEnable(3008);
            RenderHelper.enableStandardItemLighting();
        }
    }
    
    public int shouldRenderPass(final EntityDragon p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        if (p_77032_2_ == 1) {
            GL11.glDepthFunc(515);
        }
        if (p_77032_2_ != 0) {
            return -1;
        }
        this.bindTexture(RenderDragon.enderDragonEyesTextures);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glBlendFunc(1, 1);
        GL11.glDisable(2896);
        GL11.glDepthFunc(514);
        final char var4 = '\uf0f0';
        final int var5 = var4 % 65536;
        final int var6 = var4 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var5 / 1.0f, var6 / 1.0f);
        GL11.glEnable(2896);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        return 1;
    }
    
    @Override
    public void doRender(final EntityLiving p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityDragon)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    public int shouldRenderPass(final EntityLivingBase p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        return this.shouldRenderPass((EntityDragon)p_77032_1_, p_77032_2_, p_77032_3_);
    }
    
    @Override
    protected void renderEquippedItems(final EntityLivingBase p_77029_1_, final float p_77029_2_) {
        this.renderEquippedItems((EntityDragon)p_77029_1_, p_77029_2_);
    }
    
    @Override
    protected void rotateCorpse(final EntityLivingBase p_77043_1_, final float p_77043_2_, final float p_77043_3_, final float p_77043_4_) {
        this.rotateCorpse((EntityDragon)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }
    
    @Override
    protected void renderModel(final EntityLivingBase p_77036_1_, final float p_77036_2_, final float p_77036_3_, final float p_77036_4_, final float p_77036_5_, final float p_77036_6_, final float p_77036_7_) {
        this.renderModel((EntityDragon)p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
    }
    
    @Override
    public void doRender(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityDragon)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityDragon)p_110775_1_);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityDragon)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    static {
        enderDragonExplodingTextures = new ResourceLocation("textures/entity/enderdragon/dragon_exploding.png");
        enderDragonCrystalBeamTextures = new ResourceLocation("textures/entity/endercrystal/endercrystal_beam.png");
        enderDragonEyesTextures = new ResourceLocation("textures/entity/enderdragon/dragon_eyes.png");
        enderDragonTextures = new ResourceLocation("textures/entity/enderdragon/dragon.png");
    }
}
