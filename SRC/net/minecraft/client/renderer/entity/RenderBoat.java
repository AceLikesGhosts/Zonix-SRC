package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.*;
import net.minecraft.entity.item.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class RenderBoat extends Render
{
    private static final ResourceLocation boatTextures;
    protected ModelBase modelBoat;
    private static final String __OBFID = "CL_00000981";
    
    public RenderBoat() {
        this.shadowSize = 0.5f;
        this.modelBoat = new ModelBoat();
    }
    
    public void doRender(final EntityBoat p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GL11.glRotatef(180.0f - p_76986_8_, 0.0f, 1.0f, 0.0f);
        final float var10 = p_76986_1_.getTimeSinceHit() - p_76986_9_;
        float var11 = p_76986_1_.getDamageTaken() - p_76986_9_;
        if (var11 < 0.0f) {
            var11 = 0.0f;
        }
        if (var10 > 0.0f) {
            GL11.glRotatef(MathHelper.sin(var10) * var10 * var11 / 10.0f * p_76986_1_.getForwardDirection(), 1.0f, 0.0f, 0.0f);
        }
        final float var12 = 0.75f;
        GL11.glScalef(var12, var12, var12);
        GL11.glScalef(1.0f / var12, 1.0f / var12, 1.0f / var12);
        this.bindEntityTexture(p_76986_1_);
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        this.modelBoat.render(p_76986_1_, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }
    
    public ResourceLocation getEntityTexture(final EntityBoat p_110775_1_) {
        return RenderBoat.boatTextures;
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityBoat)p_110775_1_);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityBoat)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    static {
        boatTextures = new ResourceLocation("textures/entity/boat.png");
    }
}
