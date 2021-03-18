package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.projectile.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;

public class RenderWitherSkull extends Render
{
    private static final ResourceLocation invulnerableWitherTextures;
    private static final ResourceLocation witherTextures;
    private final ModelSkeletonHead skeletonHeadModel;
    private static final String __OBFID = "CL_00001035";
    
    public RenderWitherSkull() {
        this.skeletonHeadModel = new ModelSkeletonHead();
    }
    
    private float func_82400_a(final float p_82400_1_, final float p_82400_2_, final float p_82400_3_) {
        float var4;
        for (var4 = p_82400_2_ - p_82400_1_; var4 < -180.0f; var4 += 360.0f) {}
        while (var4 >= 180.0f) {
            var4 -= 360.0f;
        }
        return p_82400_1_ + p_82400_3_ * var4;
    }
    
    public void doRender(final EntityWitherSkull p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        final float var10 = this.func_82400_a(p_76986_1_.prevRotationYaw, p_76986_1_.rotationYaw, p_76986_9_);
        final float var11 = p_76986_1_.prevRotationPitch + (p_76986_1_.rotationPitch - p_76986_1_.prevRotationPitch) * p_76986_9_;
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        final float var12 = 0.0625f;
        GL11.glEnable(32826);
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        GL11.glEnable(3008);
        this.bindEntityTexture(p_76986_1_);
        this.skeletonHeadModel.render(p_76986_1_, 0.0f, 0.0f, 0.0f, var10, var11, var12);
        GL11.glPopMatrix();
    }
    
    public ResourceLocation getEntityTexture(final EntityWitherSkull p_110775_1_) {
        return p_110775_1_.isInvulnerable() ? RenderWitherSkull.invulnerableWitherTextures : RenderWitherSkull.witherTextures;
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityWitherSkull)p_110775_1_);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityWitherSkull)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    static {
        invulnerableWitherTextures = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
        witherTextures = new ResourceLocation("textures/entity/wither/wither.png");
    }
}
