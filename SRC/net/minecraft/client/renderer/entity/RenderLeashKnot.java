package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;

public class RenderLeashKnot extends Render
{
    private static final ResourceLocation leashKnotTextures;
    private ModelLeashKnot leashKnotModel;
    private static final String __OBFID = "CL_00001010";
    
    public RenderLeashKnot() {
        this.leashKnotModel = new ModelLeashKnot();
    }
    
    public void doRender(final EntityLeashKnot p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        final float var10 = 0.0625f;
        GL11.glEnable(32826);
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        GL11.glEnable(3008);
        this.bindEntityTexture(p_76986_1_);
        this.leashKnotModel.render(p_76986_1_, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, var10);
        GL11.glPopMatrix();
    }
    
    public ResourceLocation getEntityTexture(final EntityLeashKnot p_110775_1_) {
        return RenderLeashKnot.leashKnotTextures;
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityLeashKnot)p_110775_1_);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityLeashKnot)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    static {
        leashKnotTextures = new ResourceLocation("textures/entity/lead_knot.png");
    }
}
