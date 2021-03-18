package net.minecraft.client.renderer.tileentity;

import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;

public class TileEntityBeaconRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation field_147523_b;
    private static final String __OBFID = "CL_00000962";
    
    public void renderTileEntityAt(final TileEntityBeacon p_147500_1_, final double p_147500_2_, final double p_147500_4_, final double p_147500_6_, final float p_147500_8_) {
        final float var9 = p_147500_1_.func_146002_i();
        GL11.glAlphaFunc(516, 0.1f);
        if (var9 > 0.0f) {
            final Tessellator var10 = Tessellator.instance;
            this.bindTexture(TileEntityBeaconRenderer.field_147523_b);
            GL11.glTexParameterf(3553, 10242, 10497.0f);
            GL11.glTexParameterf(3553, 10243, 10497.0f);
            GL11.glDisable(2896);
            GL11.glDisable(2884);
            GL11.glDisable(3042);
            GL11.glDepthMask(true);
            OpenGlHelper.glBlendFunc(770, 1, 1, 0);
            final float var11 = p_147500_1_.getWorldObj().getTotalWorldTime() + p_147500_8_;
            final float var12 = -var11 * 0.2f - MathHelper.floor_float(-var11 * 0.1f);
            final byte var13 = 1;
            final double var14 = var11 * 0.025 * (1.0 - (var13 & 0x1) * 2.5);
            var10.startDrawingQuads();
            var10.setColorRGBA(255, 255, 255, 32);
            final double var15 = var13 * 0.2;
            final double var16 = 0.5 + Math.cos(var14 + 2.356194490192345) * var15;
            final double var17 = 0.5 + Math.sin(var14 + 2.356194490192345) * var15;
            final double var18 = 0.5 + Math.cos(var14 + 0.7853981633974483) * var15;
            final double var19 = 0.5 + Math.sin(var14 + 0.7853981633974483) * var15;
            final double var20 = 0.5 + Math.cos(var14 + 3.9269908169872414) * var15;
            final double var21 = 0.5 + Math.sin(var14 + 3.9269908169872414) * var15;
            final double var22 = 0.5 + Math.cos(var14 + 5.497787143782138) * var15;
            final double var23 = 0.5 + Math.sin(var14 + 5.497787143782138) * var15;
            final double var24 = 256.0f * var9;
            final double var25 = 0.0;
            final double var26 = 1.0;
            final double var27 = -1.0f + var12;
            final double var28 = 256.0f * var9 * (0.5 / var15) + var27;
            var10.addVertexWithUV(p_147500_2_ + var16, p_147500_4_ + var24, p_147500_6_ + var17, var26, var28);
            var10.addVertexWithUV(p_147500_2_ + var16, p_147500_4_, p_147500_6_ + var17, var26, var27);
            var10.addVertexWithUV(p_147500_2_ + var18, p_147500_4_, p_147500_6_ + var19, var25, var27);
            var10.addVertexWithUV(p_147500_2_ + var18, p_147500_4_ + var24, p_147500_6_ + var19, var25, var28);
            var10.addVertexWithUV(p_147500_2_ + var22, p_147500_4_ + var24, p_147500_6_ + var23, var26, var28);
            var10.addVertexWithUV(p_147500_2_ + var22, p_147500_4_, p_147500_6_ + var23, var26, var27);
            var10.addVertexWithUV(p_147500_2_ + var20, p_147500_4_, p_147500_6_ + var21, var25, var27);
            var10.addVertexWithUV(p_147500_2_ + var20, p_147500_4_ + var24, p_147500_6_ + var21, var25, var28);
            var10.addVertexWithUV(p_147500_2_ + var18, p_147500_4_ + var24, p_147500_6_ + var19, var26, var28);
            var10.addVertexWithUV(p_147500_2_ + var18, p_147500_4_, p_147500_6_ + var19, var26, var27);
            var10.addVertexWithUV(p_147500_2_ + var22, p_147500_4_, p_147500_6_ + var23, var25, var27);
            var10.addVertexWithUV(p_147500_2_ + var22, p_147500_4_ + var24, p_147500_6_ + var23, var25, var28);
            var10.addVertexWithUV(p_147500_2_ + var20, p_147500_4_ + var24, p_147500_6_ + var21, var26, var28);
            var10.addVertexWithUV(p_147500_2_ + var20, p_147500_4_, p_147500_6_ + var21, var26, var27);
            var10.addVertexWithUV(p_147500_2_ + var16, p_147500_4_, p_147500_6_ + var17, var25, var27);
            var10.addVertexWithUV(p_147500_2_ + var16, p_147500_4_ + var24, p_147500_6_ + var17, var25, var28);
            var10.draw();
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glDepthMask(false);
            var10.startDrawingQuads();
            var10.setColorRGBA(255, 255, 255, 32);
            final double var29 = 0.2;
            final double var30 = 0.2;
            final double var31 = 0.8;
            final double var32 = 0.2;
            final double var33 = 0.2;
            final double var34 = 0.8;
            final double var35 = 0.8;
            final double var36 = 0.8;
            final double var37 = 256.0f * var9;
            final double var38 = 0.0;
            final double var39 = 1.0;
            final double var40 = -1.0f + var12;
            final double var41 = 256.0f * var9 + var40;
            var10.addVertexWithUV(p_147500_2_ + var29, p_147500_4_ + var37, p_147500_6_ + var30, var39, var41);
            var10.addVertexWithUV(p_147500_2_ + var29, p_147500_4_, p_147500_6_ + var30, var39, var40);
            var10.addVertexWithUV(p_147500_2_ + var31, p_147500_4_, p_147500_6_ + var32, var38, var40);
            var10.addVertexWithUV(p_147500_2_ + var31, p_147500_4_ + var37, p_147500_6_ + var32, var38, var41);
            var10.addVertexWithUV(p_147500_2_ + var35, p_147500_4_ + var37, p_147500_6_ + var36, var39, var41);
            var10.addVertexWithUV(p_147500_2_ + var35, p_147500_4_, p_147500_6_ + var36, var39, var40);
            var10.addVertexWithUV(p_147500_2_ + var33, p_147500_4_, p_147500_6_ + var34, var38, var40);
            var10.addVertexWithUV(p_147500_2_ + var33, p_147500_4_ + var37, p_147500_6_ + var34, var38, var41);
            var10.addVertexWithUV(p_147500_2_ + var31, p_147500_4_ + var37, p_147500_6_ + var32, var39, var41);
            var10.addVertexWithUV(p_147500_2_ + var31, p_147500_4_, p_147500_6_ + var32, var39, var40);
            var10.addVertexWithUV(p_147500_2_ + var35, p_147500_4_, p_147500_6_ + var36, var38, var40);
            var10.addVertexWithUV(p_147500_2_ + var35, p_147500_4_ + var37, p_147500_6_ + var36, var38, var41);
            var10.addVertexWithUV(p_147500_2_ + var33, p_147500_4_ + var37, p_147500_6_ + var34, var39, var41);
            var10.addVertexWithUV(p_147500_2_ + var33, p_147500_4_, p_147500_6_ + var34, var39, var40);
            var10.addVertexWithUV(p_147500_2_ + var29, p_147500_4_, p_147500_6_ + var30, var38, var40);
            var10.addVertexWithUV(p_147500_2_ + var29, p_147500_4_ + var37, p_147500_6_ + var30, var38, var41);
            var10.draw();
            GL11.glEnable(2896);
            GL11.glEnable(3553);
            GL11.glDepthMask(true);
        }
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity p_147500_1_, final double p_147500_2_, final double p_147500_4_, final double p_147500_6_, final float p_147500_8_) {
        this.renderTileEntityAt((TileEntityBeacon)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
    }
    
    static {
        field_147523_b = new ResourceLocation("textures/entity/beacon_beam.png");
    }
}
