package net.minecraft.client.renderer.entity;

import net.minecraft.entity.effect.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class RenderLightningBolt extends Render
{
    private static final String __OBFID = "CL_00001011";
    
    public void doRender(final EntityLightningBolt p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        final Tessellator var10 = Tessellator.instance;
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        final double[] var11 = new double[8];
        final double[] var12 = new double[8];
        double var13 = 0.0;
        double var14 = 0.0;
        final Random var15 = new Random(p_76986_1_.boltVertex);
        for (int var16 = 7; var16 >= 0; --var16) {
            var11[var16] = var13;
            var12[var16] = var14;
            var13 += var15.nextInt(11) - 5;
            var14 += var15.nextInt(11) - 5;
        }
        for (int var17 = 0; var17 < 4; ++var17) {
            final Random var18 = new Random(p_76986_1_.boltVertex);
            for (int var19 = 0; var19 < 3; ++var19) {
                int var20 = 7;
                int var21 = 0;
                if (var19 > 0) {
                    var20 = 7 - var19;
                }
                if (var19 > 0) {
                    var21 = var20 - 2;
                }
                double var22 = var11[var20] - var13;
                double var23 = var12[var20] - var14;
                for (int var24 = var20; var24 >= var21; --var24) {
                    final double var25 = var22;
                    final double var26 = var23;
                    if (var19 == 0) {
                        var22 += var18.nextInt(11) - 5;
                        var23 += var18.nextInt(11) - 5;
                    }
                    else {
                        var22 += var18.nextInt(31) - 15;
                        var23 += var18.nextInt(31) - 15;
                    }
                    var10.startDrawing(5);
                    final float var27 = 0.5f;
                    var10.setColorRGBA_F(0.9f * var27, 0.9f * var27, 1.0f * var27, 0.3f);
                    double var28 = 0.1 + var17 * 0.2;
                    if (var19 == 0) {
                        var28 *= var24 * 0.1 + 1.0;
                    }
                    double var29 = 0.1 + var17 * 0.2;
                    if (var19 == 0) {
                        var29 *= (var24 - 1) * 0.1 + 1.0;
                    }
                    for (int var30 = 0; var30 < 5; ++var30) {
                        double var31 = p_76986_2_ + 0.5 - var28;
                        double var32 = p_76986_6_ + 0.5 - var28;
                        if (var30 == 1 || var30 == 2) {
                            var31 += var28 * 2.0;
                        }
                        if (var30 == 2 || var30 == 3) {
                            var32 += var28 * 2.0;
                        }
                        double var33 = p_76986_2_ + 0.5 - var29;
                        double var34 = p_76986_6_ + 0.5 - var29;
                        if (var30 == 1 || var30 == 2) {
                            var33 += var29 * 2.0;
                        }
                        if (var30 == 2 || var30 == 3) {
                            var34 += var29 * 2.0;
                        }
                        var10.addVertex(var33 + var22, p_76986_4_ + var24 * 16, var34 + var23);
                        var10.addVertex(var31 + var25, p_76986_4_ + (var24 + 1) * 16, var32 + var26);
                    }
                    var10.draw();
                }
            }
        }
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
    }
    
    public ResourceLocation getEntityTexture(final EntityLightningBolt p_110775_1_) {
        return null;
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityLightningBolt)p_110775_1_);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityLightningBolt)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
