package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.*;
import net.minecraft.entity.item.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;

public class RenderTNTPrimed extends Render
{
    private RenderBlocks blockRenderer;
    private static final String __OBFID = "CL_00001030";
    
    public RenderTNTPrimed() {
        this.blockRenderer = new RenderBlocks();
        this.shadowSize = 0.5f;
    }
    
    public void doRender(final EntityTNTPrimed p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        if (p_76986_1_.fuse - p_76986_9_ + 1.0f < 10.0f) {
            float var10 = 1.0f - (p_76986_1_.fuse - p_76986_9_ + 1.0f) / 10.0f;
            if (var10 < 0.0f) {
                var10 = 0.0f;
            }
            if (var10 > 1.0f) {
                var10 = 1.0f;
            }
            var10 *= var10;
            var10 *= var10;
            final float var11 = 1.0f + var10 * 0.3f;
            GL11.glScalef(var11, var11, var11);
        }
        float var10 = (1.0f - (p_76986_1_.fuse - p_76986_9_ + 1.0f) / 100.0f) * 0.8f;
        this.bindEntityTexture(p_76986_1_);
        this.blockRenderer.renderBlockAsItem(Blocks.tnt, 0, p_76986_1_.getBrightness(p_76986_9_));
        if (p_76986_1_.fuse / 5 % 2 == 0) {
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 772);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, var10);
            this.blockRenderer.renderBlockAsItem(Blocks.tnt, 0, 1.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glDisable(3042);
            GL11.glEnable(2896);
            GL11.glEnable(3553);
        }
        GL11.glPopMatrix();
    }
    
    public ResourceLocation getEntityTexture(final EntityTNTPrimed p_110775_1_) {
        return TextureMap.locationBlocksTexture;
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityTNTPrimed)p_110775_1_);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityTNTPrimed)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
