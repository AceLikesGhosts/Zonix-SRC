package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.monster.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;

public class RenderMagmaCube extends RenderLiving
{
    private static final ResourceLocation magmaCubeTextures;
    private static final String __OBFID = "CL_00001009";
    
    public RenderMagmaCube() {
        super(new ModelMagmaCube(), 0.25f);
    }
    
    public ResourceLocation getEntityTexture(final EntityMagmaCube p_110775_1_) {
        return RenderMagmaCube.magmaCubeTextures;
    }
    
    public void preRenderCallback(final EntityMagmaCube p_77041_1_, final float p_77041_2_) {
        final int var3 = p_77041_1_.getSlimeSize();
        final float var4 = (p_77041_1_.prevSquishFactor + (p_77041_1_.squishFactor - p_77041_1_.prevSquishFactor) * p_77041_2_) / (var3 * 0.5f + 1.0f);
        final float var5 = 1.0f / (var4 + 1.0f);
        final float var6 = (float)var3;
        GL11.glScalef(var5 * var6, 1.0f / var5 * var6, var5 * var6);
    }
    
    @Override
    public void preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.preRenderCallback((EntityMagmaCube)p_77041_1_, p_77041_2_);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityMagmaCube)p_110775_1_);
    }
    
    static {
        magmaCubeTextures = new ResourceLocation("textures/entity/slime/magmacube.png");
    }
}
