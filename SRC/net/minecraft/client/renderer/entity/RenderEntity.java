package net.minecraft.client.renderer.entity;

import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;

public class RenderEntity extends Render
{
    private static final String __OBFID = "CL_00000986";
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        GL11.glPushMatrix();
        Render.renderOffsetAABB(p_76986_1_.boundingBox, p_76986_2_ - p_76986_1_.lastTickPosX, p_76986_4_ - p_76986_1_.lastTickPosY, p_76986_6_ - p_76986_1_.lastTickPosZ);
        GL11.glPopMatrix();
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return null;
    }
}
