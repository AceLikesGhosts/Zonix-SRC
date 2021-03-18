package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.monster.*;
import net.minecraft.block.material.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.*;

public class RenderEnderman extends RenderLiving
{
    private static final ResourceLocation endermanEyesTexture;
    private static final ResourceLocation endermanTextures;
    private ModelEnderman endermanModel;
    private Random rnd;
    private static final String __OBFID = "CL_00000989";
    
    public RenderEnderman() {
        super(new ModelEnderman(), 0.5f);
        this.rnd = new Random();
        this.setRenderPassModel(this.endermanModel = (ModelEnderman)super.mainModel);
    }
    
    public void doRender(final EntityEnderman p_76986_1_, double p_76986_2_, final double p_76986_4_, double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.endermanModel.isCarrying = (p_76986_1_.func_146080_bZ().getMaterial() != Material.air);
        this.endermanModel.isAttacking = p_76986_1_.isScreaming();
        if (p_76986_1_.isScreaming()) {
            final double var10 = 0.02;
            p_76986_2_ += this.rnd.nextGaussian() * var10;
            p_76986_6_ += this.rnd.nextGaussian() * var10;
        }
        super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    public ResourceLocation getEntityTexture(final EntityEnderman p_110775_1_) {
        return RenderEnderman.endermanTextures;
    }
    
    protected void renderEquippedItems(final EntityEnderman p_77029_1_, final float p_77029_2_) {
        super.renderEquippedItems(p_77029_1_, p_77029_2_);
        if (p_77029_1_.func_146080_bZ().getMaterial() != Material.air) {
            GL11.glEnable(32826);
            GL11.glPushMatrix();
            float var3 = 0.5f;
            GL11.glTranslatef(0.0f, 0.6875f, -0.75f);
            var3 *= 1.0f;
            GL11.glRotatef(20.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            GL11.glScalef(-var3, -var3, var3);
            final int var4 = p_77029_1_.getBrightnessForRender(p_77029_2_);
            final int var5 = var4 % 65536;
            final int var6 = var4 / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var5 / 1.0f, var6 / 1.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.bindTexture(TextureMap.locationBlocksTexture);
            this.field_147909_c.renderBlockAsItem(p_77029_1_.func_146080_bZ(), p_77029_1_.getCarryingData(), 1.0f);
            GL11.glPopMatrix();
            GL11.glDisable(32826);
        }
    }
    
    public int shouldRenderPass(final EntityEnderman p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        if (p_77032_2_ != 0) {
            return -1;
        }
        this.bindTexture(RenderEnderman.endermanEyesTexture);
        final float var4 = 1.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glBlendFunc(1, 1);
        GL11.glDisable(2896);
        if (p_77032_1_.isInvisible()) {
            GL11.glDepthMask(false);
        }
        else {
            GL11.glDepthMask(true);
        }
        final char var5 = '\uf0f0';
        final int var6 = var5 % 65536;
        final int var7 = var5 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0f, var7 / 1.0f);
        GL11.glEnable(2896);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, var4);
        return 1;
    }
    
    @Override
    public void doRender(final EntityLiving p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityEnderman)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    public int shouldRenderPass(final EntityLivingBase p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        return this.shouldRenderPass((EntityEnderman)p_77032_1_, p_77032_2_, p_77032_3_);
    }
    
    @Override
    protected void renderEquippedItems(final EntityLivingBase p_77029_1_, final float p_77029_2_) {
        this.renderEquippedItems((EntityEnderman)p_77029_1_, p_77029_2_);
    }
    
    @Override
    public void doRender(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityEnderman)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityEnderman)p_110775_1_);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityEnderman)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    static {
        endermanEyesTexture = new ResourceLocation("textures/entity/enderman/enderman_eyes.png");
        endermanTextures = new ResourceLocation("textures/entity/enderman/enderman.png");
    }
}
