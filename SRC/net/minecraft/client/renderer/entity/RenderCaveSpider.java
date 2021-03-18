package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class RenderCaveSpider extends RenderSpider
{
    private static final ResourceLocation caveSpiderTextures;
    private static final String __OBFID = "CL_00000982";
    
    public RenderCaveSpider() {
        this.shadowSize *= 0.7f;
    }
    
    public void preRenderCallback(final EntityCaveSpider p_77041_1_, final float p_77041_2_) {
        GL11.glScalef(0.7f, 0.7f, 0.7f);
    }
    
    public ResourceLocation getEntityTexture(final EntityCaveSpider p_110775_1_) {
        return RenderCaveSpider.caveSpiderTextures;
    }
    
    @Override
    public ResourceLocation getEntityTexture(final EntitySpider p_110775_1_) {
        return this.getEntityTexture((EntityCaveSpider)p_110775_1_);
    }
    
    @Override
    public void preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.preRenderCallback((EntityCaveSpider)p_77041_1_, p_77041_2_);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityCaveSpider)p_110775_1_);
    }
    
    static {
        caveSpiderTextures = new ResourceLocation("textures/entity/spider/cave_spider.png");
    }
}
