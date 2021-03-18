package net.minecraft.client.renderer.tileentity;

import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;

public class TileEntityMobSpawnerRenderer extends TileEntitySpecialRenderer
{
    private static final String __OBFID = "CL_00000968";
    
    public void renderTileEntityAt(final TileEntityMobSpawner p_147500_1_, final double p_147500_2_, final double p_147500_4_, final double p_147500_6_, final float p_147500_8_) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_147500_2_ + 0.5f, (float)p_147500_4_, (float)p_147500_6_ + 0.5f);
        func_147517_a(p_147500_1_.func_145881_a(), p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
        GL11.glPopMatrix();
    }
    
    public static void func_147517_a(final MobSpawnerBaseLogic p_147517_0_, final double p_147517_1_, final double p_147517_3_, final double p_147517_5_, final float p_147517_7_) {
        final Entity var8 = p_147517_0_.func_98281_h();
        if (var8 != null) {
            var8.setWorld(p_147517_0_.getSpawnerWorld());
            final float var9 = 0.4375f;
            GL11.glTranslatef(0.0f, 0.4f, 0.0f);
            GL11.glRotatef((float)(p_147517_0_.field_98284_d + (p_147517_0_.field_98287_c - p_147517_0_.field_98284_d) * p_147517_7_) * 10.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-30.0f, 1.0f, 0.0f, 0.0f);
            GL11.glTranslatef(0.0f, -0.4f, 0.0f);
            GL11.glScalef(var9, var9, var9);
            var8.setLocationAndAngles(p_147517_1_, p_147517_3_, p_147517_5_, 0.0f, 0.0f);
            RenderManager.instance.func_147940_a(var8, 0.0, 0.0, 0.0, 0.0f, p_147517_7_);
        }
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity p_147500_1_, final double p_147500_2_, final double p_147500_4_, final double p_147500_6_, final float p_147500_8_) {
        this.renderTileEntityAt((TileEntityMobSpawner)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
    }
}
