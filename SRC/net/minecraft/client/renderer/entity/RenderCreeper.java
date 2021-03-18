package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.*;
import net.minecraft.entity.monster.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;

public class RenderCreeper extends RenderLiving
{
    private static final ResourceLocation armoredCreeperTextures;
    private static final ResourceLocation creeperTextures;
    private ModelBase creeperModel;
    private static final String __OBFID = "CL_00000985";
    
    public RenderCreeper() {
        super(new ModelCreeper(), 0.5f);
        this.creeperModel = new ModelCreeper(2.0f);
    }
    
    public void preRenderCallback(final EntityCreeper p_77041_1_, final float p_77041_2_) {
        float var3 = p_77041_1_.getCreeperFlashIntensity(p_77041_2_);
        final float var4 = 1.0f + MathHelper.sin(var3 * 100.0f) * var3 * 0.01f;
        if (var3 < 0.0f) {
            var3 = 0.0f;
        }
        if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        var3 *= var3;
        var3 *= var3;
        final float var5 = (1.0f + var3 * 0.4f) * var4;
        final float var6 = (1.0f + var3 * 0.1f) / var4;
        GL11.glScalef(var5, var6, var5);
    }
    
    protected int getColorMultiplier(final EntityCreeper p_77030_1_, final float p_77030_2_, final float p_77030_3_) {
        final float var4 = p_77030_1_.getCreeperFlashIntensity(p_77030_3_);
        if ((int)(var4 * 10.0f) % 2 == 0) {
            return 0;
        }
        int var5 = (int)(var4 * 0.2f * 255.0f);
        if (var5 < 0) {
            var5 = 0;
        }
        if (var5 > 255) {
            var5 = 255;
        }
        final short var6 = 255;
        final short var7 = 255;
        final short var8 = 255;
        return var5 << 24 | var6 << 16 | var7 << 8 | var8;
    }
    
    public int shouldRenderPass(final EntityCreeper p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        if (p_77032_1_.getPowered()) {
            if (p_77032_1_.isInvisible()) {
                GL11.glDepthMask(false);
            }
            else {
                GL11.glDepthMask(true);
            }
            if (p_77032_2_ == 1) {
                final float var4 = p_77032_1_.ticksExisted + p_77032_3_;
                this.bindTexture(RenderCreeper.armoredCreeperTextures);
                GL11.glMatrixMode(5890);
                GL11.glLoadIdentity();
                final float var5 = var4 * 0.01f;
                final float var6 = var4 * 0.01f;
                GL11.glTranslatef(var5, var6, 0.0f);
                this.setRenderPassModel(this.creeperModel);
                GL11.glMatrixMode(5888);
                GL11.glEnable(3042);
                final float var7 = 0.5f;
                GL11.glColor4f(var7, var7, var7, 1.0f);
                GL11.glDisable(2896);
                GL11.glBlendFunc(1, 1);
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
    
    protected int inheritRenderPass(final EntityCreeper p_77035_1_, final int p_77035_2_, final float p_77035_3_) {
        return -1;
    }
    
    public ResourceLocation getEntityTexture(final EntityCreeper p_110775_1_) {
        return RenderCreeper.creeperTextures;
    }
    
    @Override
    public void preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.preRenderCallback((EntityCreeper)p_77041_1_, p_77041_2_);
    }
    
    @Override
    protected int getColorMultiplier(final EntityLivingBase p_77030_1_, final float p_77030_2_, final float p_77030_3_) {
        return this.getColorMultiplier((EntityCreeper)p_77030_1_, p_77030_2_, p_77030_3_);
    }
    
    @Override
    public int shouldRenderPass(final EntityLivingBase p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        return this.shouldRenderPass((EntityCreeper)p_77032_1_, p_77032_2_, p_77032_3_);
    }
    
    @Override
    protected int inheritRenderPass(final EntityLivingBase p_77035_1_, final int p_77035_2_, final float p_77035_3_) {
        return this.inheritRenderPass((EntityCreeper)p_77035_1_, p_77035_2_, p_77035_3_);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityCreeper)p_110775_1_);
    }
    
    static {
        armoredCreeperTextures = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
        creeperTextures = new ResourceLocation("textures/entity/creeper/creeper.png");
    }
}
