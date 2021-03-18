package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.monster.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;

public class RenderGiantZombie extends RenderLiving
{
    private static final ResourceLocation zombieTextures;
    private float scale;
    private static final String __OBFID = "CL_00000998";
    
    public RenderGiantZombie(final ModelBase p_i1255_1_, final float p_i1255_2_, final float p_i1255_3_) {
        super(p_i1255_1_, p_i1255_2_ * p_i1255_3_);
        this.scale = p_i1255_3_;
    }
    
    public void preRenderCallback(final EntityGiantZombie p_77041_1_, final float p_77041_2_) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }
    
    public ResourceLocation getEntityTexture(final EntityGiantZombie p_110775_1_) {
        return RenderGiantZombie.zombieTextures;
    }
    
    @Override
    public void preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.preRenderCallback((EntityGiantZombie)p_77041_1_, p_77041_2_);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityGiantZombie)p_110775_1_);
    }
    
    static {
        zombieTextures = new ResourceLocation("textures/entity/zombie/zombie.png");
    }
}
