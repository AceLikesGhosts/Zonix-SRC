package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.client.model.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;

public class RenderEnchantmentTable extends TileEntitySpecialRenderer
{
    private static final ResourceLocation field_147540_b;
    private ModelBook field_147541_c;
    private static final String __OBFID = "CL_00000966";
    
    public RenderEnchantmentTable() {
        this.field_147541_c = new ModelBook();
    }
    
    public void renderTileEntityAt(final TileEntityEnchantmentTable p_147500_1_, final double p_147500_2_, final double p_147500_4_, final double p_147500_6_, final float p_147500_8_) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_147500_2_ + 0.5f, (float)p_147500_4_ + 0.75f, (float)p_147500_6_ + 0.5f);
        final float var9 = p_147500_1_.field_145926_a + p_147500_8_;
        GL11.glTranslatef(0.0f, 0.1f + MathHelper.sin(var9 * 0.1f) * 0.01f, 0.0f);
        float var10;
        for (var10 = p_147500_1_.field_145928_o - p_147500_1_.field_145925_p; var10 >= 3.1415927f; var10 -= 6.2831855f) {}
        while (var10 < -3.1415927f) {
            var10 += 6.2831855f;
        }
        final float var11 = p_147500_1_.field_145925_p + var10 * p_147500_8_;
        GL11.glRotatef(-var11 * 180.0f / 3.1415927f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(80.0f, 0.0f, 0.0f, 1.0f);
        this.bindTexture(RenderEnchantmentTable.field_147540_b);
        float var12 = p_147500_1_.field_145931_j + (p_147500_1_.field_145933_i - p_147500_1_.field_145931_j) * p_147500_8_ + 0.25f;
        float var13 = p_147500_1_.field_145931_j + (p_147500_1_.field_145933_i - p_147500_1_.field_145931_j) * p_147500_8_ + 0.75f;
        var12 = (var12 - MathHelper.truncateDoubleToInt(var12)) * 1.6f - 0.3f;
        var13 = (var13 - MathHelper.truncateDoubleToInt(var13)) * 1.6f - 0.3f;
        if (var12 < 0.0f) {
            var12 = 0.0f;
        }
        if (var13 < 0.0f) {
            var13 = 0.0f;
        }
        if (var12 > 1.0f) {
            var12 = 1.0f;
        }
        if (var13 > 1.0f) {
            var13 = 1.0f;
        }
        final float var14 = p_147500_1_.field_145927_n + (p_147500_1_.field_145930_m - p_147500_1_.field_145927_n) * p_147500_8_;
        GL11.glEnable(2884);
        this.field_147541_c.render(null, var9, var12, var13, var14, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity p_147500_1_, final double p_147500_2_, final double p_147500_4_, final double p_147500_6_, final float p_147500_8_) {
        this.renderTileEntityAt((TileEntityEnchantmentTable)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
    }
    
    static {
        field_147540_b = new ResourceLocation("textures/entity/enchanting_table_book.png");
    }
}
