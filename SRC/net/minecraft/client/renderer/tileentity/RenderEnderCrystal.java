package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.item.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class RenderEnderCrystal extends Render
{
    private static final ResourceLocation enderCrystalTextures;
    private ModelBase field_76995_b;
    private static final String __OBFID = "CL_00000987";
    
    public RenderEnderCrystal() {
        this.shadowSize = 0.5f;
        this.field_76995_b = new ModelEnderCrystal(0.0f, true);
    }
    
    public void doRender(final EntityEnderCrystal p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        final float var10 = p_76986_1_.innerRotation + p_76986_9_;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        this.bindTexture(RenderEnderCrystal.enderCrystalTextures);
        float var11 = MathHelper.sin(var10 * 0.2f) / 2.0f + 0.5f;
        var11 += var11 * var11;
        this.field_76995_b.render(p_76986_1_, 0.0f, var10 * 3.0f, var11 * 0.2f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }
    
    public ResourceLocation getEntityTexture(final EntityEnderCrystal p_110775_1_) {
        return RenderEnderCrystal.enderCrystalTextures;
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityEnderCrystal)p_110775_1_);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityEnderCrystal)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    static {
        enderCrystalTextures = new ResourceLocation("textures/entity/endercrystal/endercrystal.png");
    }
}
