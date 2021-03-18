package net.minecraft.client.renderer.entity;

import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;

public class RenderSnowball extends Render
{
    private Item field_94151_a;
    private int field_94150_f;
    private static final String __OBFID = "CL_00001008";
    
    public RenderSnowball(final Item p_i1259_1_, final int p_i1259_2_) {
        this.field_94151_a = p_i1259_1_;
        this.field_94150_f = p_i1259_2_;
    }
    
    public RenderSnowball(final Item p_i1260_1_) {
        this(p_i1260_1_, 0);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        final IIcon var10 = this.field_94151_a.getIconFromDamage(this.field_94150_f);
        if (var10 != null) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
            GL11.glEnable(32826);
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            this.bindEntityTexture(p_76986_1_);
            final Tessellator var11 = Tessellator.instance;
            if (var10 == ItemPotion.func_94589_d("bottle_splash")) {
                final int var12 = PotionHelper.func_77915_a(((EntityPotion)p_76986_1_).getPotionDamage(), false);
                final float var13 = (var12 >> 16 & 0xFF) / 255.0f;
                final float var14 = (var12 >> 8 & 0xFF) / 255.0f;
                final float var15 = (var12 & 0xFF) / 255.0f;
                GL11.glColor3f(var13, var14, var15);
                GL11.glPushMatrix();
                this.func_77026_a(var11, ItemPotion.func_94589_d("overlay"));
                GL11.glPopMatrix();
                GL11.glColor3f(1.0f, 1.0f, 1.0f);
            }
            this.func_77026_a(var11, var10);
            GL11.glDisable(32826);
            GL11.glPopMatrix();
        }
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return TextureMap.locationItemsTexture;
    }
    
    private void func_77026_a(final Tessellator p_77026_1_, final IIcon p_77026_2_) {
        final float var3 = p_77026_2_.getMinU();
        final float var4 = p_77026_2_.getMaxU();
        final float var5 = p_77026_2_.getMinV();
        final float var6 = p_77026_2_.getMaxV();
        final float var7 = 1.0f;
        final float var8 = 0.5f;
        final float var9 = 0.25f;
        GL11.glRotatef(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        p_77026_1_.startDrawingQuads();
        p_77026_1_.setNormal(0.0f, 1.0f, 0.0f);
        p_77026_1_.addVertexWithUV(0.0f - var8, 0.0f - var9, 0.0, var3, var6);
        p_77026_1_.addVertexWithUV(var7 - var8, 0.0f - var9, 0.0, var4, var6);
        p_77026_1_.addVertexWithUV(var7 - var8, var7 - var9, 0.0, var4, var5);
        p_77026_1_.addVertexWithUV(0.0f - var8, var7 - var9, 0.0, var3, var5);
        p_77026_1_.draw();
    }
}
