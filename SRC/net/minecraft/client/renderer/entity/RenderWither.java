package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.*;
import net.minecraft.entity.boss.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class RenderWither extends RenderLiving
{
    private static final ResourceLocation invulnerableWitherTextures;
    private static final ResourceLocation witherTextures;
    private int field_82419_a;
    private static final String __OBFID = "CL_00001034";
    
    public RenderWither() {
        super(new ModelWither(), 1.0f);
        this.field_82419_a = ((ModelWither)this.mainModel).func_82903_a();
    }
    
    public void doRender(final EntityWither p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        BossStatus.setBossStatus(p_76986_1_, true);
        final int var10 = ((ModelWither)this.mainModel).func_82903_a();
        if (var10 != this.field_82419_a) {
            this.field_82419_a = var10;
            this.mainModel = new ModelWither();
        }
        super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    public ResourceLocation getEntityTexture(final EntityWither p_110775_1_) {
        final int var2 = p_110775_1_.func_82212_n();
        return (var2 > 0 && (var2 > 80 || var2 / 5 % 2 != 1)) ? RenderWither.invulnerableWitherTextures : RenderWither.witherTextures;
    }
    
    public void preRenderCallback(final EntityWither p_77041_1_, final float p_77041_2_) {
        final int var3 = p_77041_1_.func_82212_n();
        if (var3 > 0) {
            final float var4 = 2.0f - (var3 - p_77041_2_) / 220.0f * 0.5f;
            GL11.glScalef(var4, var4, var4);
        }
        else {
            GL11.glScalef(2.0f, 2.0f, 2.0f);
        }
    }
    
    public int shouldRenderPass(final EntityWither p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        if (p_77032_1_.isArmored()) {
            if (p_77032_1_.isInvisible()) {
                GL11.glDepthMask(false);
            }
            else {
                GL11.glDepthMask(true);
            }
            if (p_77032_2_ == 1) {
                final float var4 = p_77032_1_.ticksExisted + p_77032_3_;
                this.bindTexture(RenderWither.invulnerableWitherTextures);
                GL11.glMatrixMode(5890);
                GL11.glLoadIdentity();
                final float var5 = MathHelper.cos(var4 * 0.02f) * 3.0f;
                final float var6 = var4 * 0.01f;
                GL11.glTranslatef(var5, var6, 0.0f);
                this.setRenderPassModel(this.mainModel);
                GL11.glMatrixMode(5888);
                GL11.glEnable(3042);
                final float var7 = 0.5f;
                GL11.glColor4f(var7, var7, var7, 1.0f);
                GL11.glDisable(2896);
                GL11.glBlendFunc(1, 1);
                GL11.glTranslatef(0.0f, -0.01f, 0.0f);
                GL11.glScalef(1.1f, 1.1f, 1.1f);
                return 1;
            }
            if (p_77032_2_ == 2) {
                GL11.glMatrixMode(5890);
                GL11.glLoadIdentity();
                GL11.glMatrixMode(5888);
                GL11.glEnable(2896);
                GL11.glDisable(3042);
            }
        }
        return -1;
    }
    
    protected int inheritRenderPass(final EntityWither p_77035_1_, final int p_77035_2_, final float p_77035_3_) {
        return -1;
    }
    
    @Override
    public void doRender(final EntityLiving p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityWither)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    public void preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.preRenderCallback((EntityWither)p_77041_1_, p_77041_2_);
    }
    
    @Override
    public int shouldRenderPass(final EntityLivingBase p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        return this.shouldRenderPass((EntityWither)p_77032_1_, p_77032_2_, p_77032_3_);
    }
    
    @Override
    protected int inheritRenderPass(final EntityLivingBase p_77035_1_, final int p_77035_2_, final float p_77035_3_) {
        return this.inheritRenderPass((EntityWither)p_77035_1_, p_77035_2_, p_77035_3_);
    }
    
    @Override
    public void doRender(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityWither)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityWither)p_110775_1_);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityWither)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    static {
        invulnerableWitherTextures = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
        witherTextures = new ResourceLocation("textures/entity/wither/wither.png");
    }
}
