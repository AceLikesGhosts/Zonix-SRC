package net.minecraft.client.renderer.tileentity;

import net.minecraft.util.*;
import java.util.*;
import java.nio.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.tileentity.*;

public class RenderEndPortal extends TileEntitySpecialRenderer
{
    private static final ResourceLocation field_147529_c;
    private static final ResourceLocation field_147526_d;
    private static final Random field_147527_e;
    FloatBuffer field_147528_b;
    private static final String __OBFID = "CL_00000972";
    
    public RenderEndPortal() {
        this.field_147528_b = GLAllocation.createDirectFloatBuffer(16);
    }
    
    public void renderTileEntityAt(final TileEntityEndPortal p_147500_1_, final double p_147500_2_, final double p_147500_4_, final double p_147500_6_, final float p_147500_8_) {
        final float var9 = (float)this.field_147501_a.field_147560_j;
        final float var10 = (float)this.field_147501_a.field_147561_k;
        final float var11 = (float)this.field_147501_a.field_147558_l;
        GL11.glDisable(2896);
        RenderEndPortal.field_147527_e.setSeed(31100L);
        final float var12 = 0.75f;
        for (int var13 = 0; var13 < 16; ++var13) {
            GL11.glPushMatrix();
            float var14 = (float)(16 - var13);
            float var15 = 0.0625f;
            float var16 = 1.0f / (var14 + 1.0f);
            if (var13 == 0) {
                this.bindTexture(RenderEndPortal.field_147529_c);
                var16 = 0.1f;
                var14 = 65.0f;
                var15 = 0.125f;
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
            }
            if (var13 == 1) {
                this.bindTexture(RenderEndPortal.field_147526_d);
                GL11.glEnable(3042);
                GL11.glBlendFunc(1, 1);
                var15 = 0.5f;
            }
            final float var17 = (float)(-(p_147500_4_ + var12));
            float var18 = var17 + ActiveRenderInfo.objectY;
            final float var19 = var17 + var14 + ActiveRenderInfo.objectY;
            float var20 = var18 / var19;
            var20 += (float)(p_147500_4_ + var12);
            GL11.glTranslatef(var9, var20, var11);
            GL11.glTexGeni(8192, 9472, 9217);
            GL11.glTexGeni(8193, 9472, 9217);
            GL11.glTexGeni(8194, 9472, 9217);
            GL11.glTexGeni(8195, 9472, 9216);
            GL11.glTexGen(8192, 9473, this.func_147525_a(1.0f, 0.0f, 0.0f, 0.0f));
            GL11.glTexGen(8193, 9473, this.func_147525_a(0.0f, 0.0f, 1.0f, 0.0f));
            GL11.glTexGen(8194, 9473, this.func_147525_a(0.0f, 0.0f, 0.0f, 1.0f));
            GL11.glTexGen(8195, 9474, this.func_147525_a(0.0f, 1.0f, 0.0f, 0.0f));
            GL11.glEnable(3168);
            GL11.glEnable(3169);
            GL11.glEnable(3170);
            GL11.glEnable(3171);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5890);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0f, Minecraft.getSystemTime() % 700000L / 700000.0f, 0.0f);
            GL11.glScalef(var15, var15, var15);
            GL11.glTranslatef(0.5f, 0.5f, 0.0f);
            GL11.glRotatef((var13 * var13 * 4321 + var13 * 9) * 2.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(-0.5f, -0.5f, 0.0f);
            GL11.glTranslatef(-var9, -var11, -var10);
            var18 = var17 + ActiveRenderInfo.objectY;
            GL11.glTranslatef(ActiveRenderInfo.objectX * var14 / var18, ActiveRenderInfo.objectZ * var14 / var18, -var10);
            final Tessellator var21 = Tessellator.instance;
            var21.startDrawingQuads();
            var20 = RenderEndPortal.field_147527_e.nextFloat() * 0.5f + 0.1f;
            float var22 = RenderEndPortal.field_147527_e.nextFloat() * 0.5f + 0.4f;
            float var23 = RenderEndPortal.field_147527_e.nextFloat() * 0.5f + 0.5f;
            if (var13 == 0) {
                var23 = 1.0f;
                var22 = 1.0f;
                var20 = 1.0f;
            }
            var21.setColorRGBA_F(var20 * var16, var22 * var16, var23 * var16, 1.0f);
            var21.addVertex(p_147500_2_, p_147500_4_ + var12, p_147500_6_);
            var21.addVertex(p_147500_2_, p_147500_4_ + var12, p_147500_6_ + 1.0);
            var21.addVertex(p_147500_2_ + 1.0, p_147500_4_ + var12, p_147500_6_ + 1.0);
            var21.addVertex(p_147500_2_ + 1.0, p_147500_4_ + var12, p_147500_6_);
            var21.draw();
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
        }
        GL11.glDisable(3042);
        GL11.glDisable(3168);
        GL11.glDisable(3169);
        GL11.glDisable(3170);
        GL11.glDisable(3171);
        GL11.glEnable(2896);
    }
    
    private FloatBuffer func_147525_a(final float p_147525_1_, final float p_147525_2_, final float p_147525_3_, final float p_147525_4_) {
        this.field_147528_b.clear();
        this.field_147528_b.put(p_147525_1_).put(p_147525_2_).put(p_147525_3_).put(p_147525_4_);
        this.field_147528_b.flip();
        return this.field_147528_b;
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity p_147500_1_, final double p_147500_2_, final double p_147500_4_, final double p_147500_6_, final float p_147500_8_) {
        this.renderTileEntityAt((TileEntityEndPortal)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
    }
    
    static {
        field_147529_c = new ResourceLocation("textures/environment/end_sky.png");
        field_147526_d = new ResourceLocation("textures/entity/end_portal.png");
        field_147527_e = new Random(31100L);
    }
}
