package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.monster.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;

public class RenderGhast extends RenderLiving
{
    private static final ResourceLocation ghastTextures;
    private static final ResourceLocation ghastShootingTextures;
    private static final String __OBFID = "CL_00000997";
    
    public RenderGhast() {
        super(new ModelGhast(), 0.5f);
    }
    
    public ResourceLocation getEntityTexture(final EntityGhast p_110775_1_) {
        return p_110775_1_.func_110182_bF() ? RenderGhast.ghastShootingTextures : RenderGhast.ghastTextures;
    }
    
    public void preRenderCallback(final EntityGhast p_77041_1_, final float p_77041_2_) {
        float var4 = (p_77041_1_.prevAttackCounter + (p_77041_1_.attackCounter - p_77041_1_.prevAttackCounter) * p_77041_2_) / 20.0f;
        if (var4 < 0.0f) {
            var4 = 0.0f;
        }
        var4 = 1.0f / (var4 * var4 * var4 * var4 * var4 * 2.0f + 1.0f);
        final float var5 = (8.0f + var4) / 2.0f;
        final float var6 = (8.0f + 1.0f / var4) / 2.0f;
        GL11.glScalef(var6, var5, var6);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.preRenderCallback((EntityGhast)p_77041_1_, p_77041_2_);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityGhast)p_110775_1_);
    }
    
    static {
        ghastTextures = new ResourceLocation("textures/entity/ghast/ghast.png");
        ghastShootingTextures = new ResourceLocation("textures/entity/ghast/ghast_shooting.png");
    }
}
