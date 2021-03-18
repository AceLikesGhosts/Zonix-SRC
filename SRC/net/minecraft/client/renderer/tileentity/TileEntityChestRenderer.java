package net.minecraft.client.renderer.tileentity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import java.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;

public class TileEntityChestRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation field_147507_b;
    private static final ResourceLocation field_147508_c;
    private static final ResourceLocation field_147505_d;
    private static final ResourceLocation field_147506_e;
    private static final ResourceLocation field_147503_f;
    private static final ResourceLocation field_147504_g;
    private ModelChest field_147510_h;
    private ModelChest field_147511_i;
    private boolean field_147509_j;
    private static final String __OBFID = "CL_00000965";
    
    public TileEntityChestRenderer() {
        this.field_147510_h = new ModelChest();
        this.field_147511_i = new ModelLargeChest();
        final Calendar var1 = Calendar.getInstance();
        if (var1.get(2) + 1 == 12 && var1.get(5) >= 24 && var1.get(5) <= 26) {
            this.field_147509_j = true;
        }
    }
    
    public void renderTileEntityAt(final TileEntityChest p_147500_1_, final double p_147500_2_, final double p_147500_4_, final double p_147500_6_, final float p_147500_8_) {
        int var9;
        if (!p_147500_1_.hasWorldObj()) {
            var9 = 0;
        }
        else {
            final Block var10 = p_147500_1_.getBlockType();
            var9 = p_147500_1_.getBlockMetadata();
            if (var10 instanceof BlockChest && var9 == 0) {
                ((BlockChest)var10).func_149954_e(p_147500_1_.getWorldObj(), p_147500_1_.field_145851_c, p_147500_1_.field_145848_d, p_147500_1_.field_145849_e);
                var9 = p_147500_1_.getBlockMetadata();
            }
            p_147500_1_.func_145979_i();
        }
        if (p_147500_1_.field_145992_i == null && p_147500_1_.field_145991_k == null) {
            ModelChest var11;
            if (p_147500_1_.field_145990_j == null && p_147500_1_.field_145988_l == null) {
                var11 = this.field_147510_h;
                if (p_147500_1_.func_145980_j() == 1) {
                    this.bindTexture(TileEntityChestRenderer.field_147506_e);
                }
                else if (this.field_147509_j) {
                    this.bindTexture(TileEntityChestRenderer.field_147503_f);
                }
                else {
                    this.bindTexture(TileEntityChestRenderer.field_147504_g);
                }
            }
            else {
                var11 = this.field_147511_i;
                if (p_147500_1_.func_145980_j() == 1) {
                    this.bindTexture(TileEntityChestRenderer.field_147507_b);
                }
                else if (this.field_147509_j) {
                    this.bindTexture(TileEntityChestRenderer.field_147508_c);
                }
                else {
                    this.bindTexture(TileEntityChestRenderer.field_147505_d);
                }
            }
            GL11.glPushMatrix();
            GL11.glEnable(32826);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glTranslatef((float)p_147500_2_, (float)p_147500_4_ + 1.0f, (float)p_147500_6_ + 1.0f);
            GL11.glScalef(1.0f, -1.0f, -1.0f);
            GL11.glTranslatef(0.5f, 0.5f, 0.5f);
            short var12 = 0;
            if (var9 == 2) {
                var12 = 180;
            }
            if (var9 == 3) {
                var12 = 0;
            }
            if (var9 == 4) {
                var12 = 90;
            }
            if (var9 == 5) {
                var12 = -90;
            }
            if (var9 == 2 && p_147500_1_.field_145990_j != null) {
                GL11.glTranslatef(1.0f, 0.0f, 0.0f);
            }
            if (var9 == 5 && p_147500_1_.field_145988_l != null) {
                GL11.glTranslatef(0.0f, 0.0f, -1.0f);
            }
            GL11.glRotatef((float)var12, 0.0f, 1.0f, 0.0f);
            GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
            float var13 = p_147500_1_.field_145986_n + (p_147500_1_.field_145989_m - p_147500_1_.field_145986_n) * p_147500_8_;
            if (p_147500_1_.field_145992_i != null) {
                final float var14 = p_147500_1_.field_145992_i.field_145986_n + (p_147500_1_.field_145992_i.field_145989_m - p_147500_1_.field_145992_i.field_145986_n) * p_147500_8_;
                if (var14 > var13) {
                    var13 = var14;
                }
            }
            if (p_147500_1_.field_145991_k != null) {
                final float var14 = p_147500_1_.field_145991_k.field_145986_n + (p_147500_1_.field_145991_k.field_145989_m - p_147500_1_.field_145991_k.field_145986_n) * p_147500_8_;
                if (var14 > var13) {
                    var13 = var14;
                }
            }
            var13 = 1.0f - var13;
            var13 = 1.0f - var13 * var13 * var13;
            var11.chestLid.rotateAngleX = -(var13 * 3.1415927f / 2.0f);
            var11.renderAll();
            GL11.glDisable(32826);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity p_147500_1_, final double p_147500_2_, final double p_147500_4_, final double p_147500_6_, final float p_147500_8_) {
        this.renderTileEntityAt((TileEntityChest)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
    }
    
    static {
        field_147507_b = new ResourceLocation("textures/entity/chest/trapped_double.png");
        field_147508_c = new ResourceLocation("textures/entity/chest/christmas_double.png");
        field_147505_d = new ResourceLocation("textures/entity/chest/normal_double.png");
        field_147506_e = new ResourceLocation("textures/entity/chest/trapped.png");
        field_147503_f = new ResourceLocation("textures/entity/chest/christmas.png");
        field_147504_g = new ResourceLocation("textures/entity/chest/normal.png");
    }
}
