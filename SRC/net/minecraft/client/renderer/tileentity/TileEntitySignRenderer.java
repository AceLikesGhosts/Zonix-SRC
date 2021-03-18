package net.minecraft.client.renderer.tileentity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import org.lwjgl.opengl.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.client.gui.*;
import net.minecraft.tileentity.*;

public class TileEntitySignRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation field_147513_b;
    private final ModelSign field_147514_c;
    private static final String __OBFID = "CL_00000970";
    
    public TileEntitySignRenderer() {
        this.field_147514_c = new ModelSign();
    }
    
    public void renderTileEntityAt(final TileEntitySign p_147500_1_, final double p_147500_2_, final double p_147500_4_, final double p_147500_6_, final float p_147500_8_) {
        final Block var9 = p_147500_1_.getBlockType();
        GL11.glPushMatrix();
        final float var10 = 0.6666667f;
        if (var9 == Blocks.standing_sign) {
            GL11.glTranslatef((float)p_147500_2_ + 0.5f, (float)p_147500_4_ + 0.75f * var10, (float)p_147500_6_ + 0.5f);
            final float var11 = p_147500_1_.getBlockMetadata() * 360 / 16.0f;
            GL11.glRotatef(-var11, 0.0f, 1.0f, 0.0f);
            this.field_147514_c.signStick.showModel = true;
        }
        else {
            final int var12 = p_147500_1_.getBlockMetadata();
            float var13 = 0.0f;
            if (var12 == 2) {
                var13 = 180.0f;
            }
            if (var12 == 4) {
                var13 = 90.0f;
            }
            if (var12 == 5) {
                var13 = -90.0f;
            }
            GL11.glTranslatef((float)p_147500_2_ + 0.5f, (float)p_147500_4_ + 0.75f * var10, (float)p_147500_6_ + 0.5f);
            GL11.glRotatef(-var13, 0.0f, 1.0f, 0.0f);
            GL11.glTranslatef(0.0f, -0.3125f, -0.4375f);
            this.field_147514_c.signStick.showModel = false;
        }
        this.bindTexture(TileEntitySignRenderer.field_147513_b);
        GL11.glPushMatrix();
        GL11.glScalef(var10, -var10, -var10);
        this.field_147514_c.renderSign();
        GL11.glPopMatrix();
        final FontRenderer var14 = this.func_147498_b();
        float var13 = 0.016666668f * var10;
        GL11.glTranslatef(0.0f, 0.5f * var10, 0.07f * var10);
        GL11.glScalef(var13, -var13, var13);
        GL11.glNormal3f(0.0f, 0.0f, -1.0f * var13);
        GL11.glDepthMask(false);
        final byte var15 = 0;
        for (int var16 = 0; var16 < p_147500_1_.field_145915_a.length; ++var16) {
            String var17 = p_147500_1_.field_145915_a[var16];
            if (var16 == p_147500_1_.field_145918_i) {
                var17 = "> " + var17 + " <";
                var14.drawString(var17, -var14.getStringWidth(var17) / 2, var16 * 10 - p_147500_1_.field_145915_a.length * 5, var15);
            }
            else {
                var14.drawString(var17, -var14.getStringWidth(var17) / 2, var16 * 10 - p_147500_1_.field_145915_a.length * 5, var15);
            }
        }
        GL11.glDepthMask(true);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity p_147500_1_, final double p_147500_2_, final double p_147500_4_, final double p_147500_6_, final float p_147500_8_) {
        this.renderTileEntityAt((TileEntitySign)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
    }
    
    static {
        field_147513_b = new ResourceLocation("textures/entity/sign.png");
    }
}
