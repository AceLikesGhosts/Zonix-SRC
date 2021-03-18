package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.monster.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderSpider extends RenderLiving
{
    private static final ResourceLocation spiderEyesTextures;
    private static final ResourceLocation spiderTextures;
    private static final String __OBFID = "CL_00001027";
    
    public RenderSpider() {
        super(new ModelSpider(), 1.0f);
        this.setRenderPassModel(new ModelSpider());
    }
    
    protected float getDeathMaxRotation(final EntitySpider p_77037_1_) {
        return 180.0f;
    }
    
    public int shouldRenderPass(final EntitySpider p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        if (p_77032_2_ != 0) {
            return -1;
        }
        this.bindTexture(RenderSpider.spiderEyesTextures);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glBlendFunc(1, 1);
        if (p_77032_1_.isInvisible()) {
            GL11.glDepthMask(false);
        }
        else {
            GL11.glDepthMask(true);
        }
        final char var4 = '\uf0f0';
        final int var5 = var4 % 65536;
        final int var6 = var4 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var5 / 1.0f, var6 / 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        return 1;
    }
    
    public ResourceLocation getEntityTexture(final EntitySpider p_110775_1_) {
        return RenderSpider.spiderTextures;
    }
    
    @Override
    protected float getDeathMaxRotation(final EntityLivingBase p_77037_1_) {
        return this.getDeathMaxRotation((EntitySpider)p_77037_1_);
    }
    
    @Override
    public int shouldRenderPass(final EntityLivingBase p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        return this.shouldRenderPass((EntitySpider)p_77032_1_, p_77032_2_, p_77032_3_);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntitySpider)p_110775_1_);
    }
    
    static {
        spiderEyesTextures = new ResourceLocation("textures/entity/spider_eyes.png");
        spiderTextures = new ResourceLocation("textures/entity/spider/spider.png");
    }
}
