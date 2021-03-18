package net.minecraft.client.renderer.entity;

import net.minecraft.block.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.item.*;
import net.minecraft.init.*;

public class RenderTntMinecart extends RenderMinecart
{
    private static final String __OBFID = "CL_00001029";
    
    protected void func_147910_a(final EntityMinecartTNT p_147910_1_, final float p_147910_2_, final Block p_147910_3_, final int p_147910_4_) {
        final int var5 = p_147910_1_.func_94104_d();
        if (var5 > -1 && var5 - p_147910_2_ + 1.0f < 10.0f) {
            float var6 = 1.0f - (var5 - p_147910_2_ + 1.0f) / 10.0f;
            if (var6 < 0.0f) {
                var6 = 0.0f;
            }
            if (var6 > 1.0f) {
                var6 = 1.0f;
            }
            var6 *= var6;
            var6 *= var6;
            final float var7 = 1.0f + var6 * 0.3f;
            GL11.glScalef(var7, var7, var7);
        }
        super.func_147910_a(p_147910_1_, p_147910_2_, p_147910_3_, p_147910_4_);
        if (var5 > -1 && var5 / 5 % 2 == 0) {
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 772);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, (1.0f - (var5 - p_147910_2_ + 1.0f) / 100.0f) * 0.8f);
            GL11.glPushMatrix();
            this.field_94145_f.renderBlockAsItem(Blocks.tnt, 0, 1.0f);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glDisable(3042);
            GL11.glEnable(2896);
            GL11.glEnable(3553);
        }
    }
    
    @Override
    protected void func_147910_a(final EntityMinecart p_147910_1_, final float p_147910_2_, final Block p_147910_3_, final int p_147910_4_) {
        this.func_147910_a((EntityMinecartTNT)p_147910_1_, p_147910_2_, p_147910_3_, p_147910_4_);
    }
}
