package net.minecraft.client.renderer.tileentity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import org.lwjgl.opengl.*;
import net.minecraft.tileentity.*;

public class TileEntityEnderChestRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation field_147520_b;
    private ModelChest field_147521_c;
    private static final String __OBFID = "CL_00000967";
    
    public TileEntityEnderChestRenderer() {
        this.field_147521_c = new ModelChest();
    }
    
    public void renderTileEntityAt(final TileEntityEnderChest p_147500_1_, final double p_147500_2_, final double p_147500_4_, final double p_147500_6_, final float p_147500_8_) {
        int var9 = 0;
        if (p_147500_1_.hasWorldObj()) {
            var9 = p_147500_1_.getBlockMetadata();
        }
        this.bindTexture(TileEntityEnderChestRenderer.field_147520_b);
        GL11.glPushMatrix();
        GL11.glEnable(32826);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glTranslatef((float)p_147500_2_, (float)p_147500_4_ + 1.0f, (float)p_147500_6_ + 1.0f);
        GL11.glScalef(1.0f, -1.0f, -1.0f);
        GL11.glTranslatef(0.5f, 0.5f, 0.5f);
        short var10 = 0;
        if (var9 == 2) {
            var10 = 180;
        }
        if (var9 == 3) {
            var10 = 0;
        }
        if (var9 == 4) {
            var10 = 90;
        }
        if (var9 == 5) {
            var10 = -90;
        }
        GL11.glRotatef((float)var10, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
        float var11 = p_147500_1_.field_145975_i + (p_147500_1_.field_145972_a - p_147500_1_.field_145975_i) * p_147500_8_;
        var11 = 1.0f - var11;
        var11 = 1.0f - var11 * var11 * var11;
        this.field_147521_c.chestLid.rotateAngleX = -(var11 * 3.1415927f / 2.0f);
        this.field_147521_c.renderAll();
        GL11.glDisable(32826);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity p_147500_1_, final double p_147500_2_, final double p_147500_4_, final double p_147500_6_, final float p_147500_8_) {
        this.renderTileEntityAt((TileEntityEnderChest)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
    }
    
    static {
        field_147520_b = new ResourceLocation("textures/entity/chest/ender.png");
    }
}
