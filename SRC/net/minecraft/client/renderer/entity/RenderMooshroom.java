package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.entity.passive.*;
import net.minecraft.client.renderer.texture.*;
import org.lwjgl.opengl.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;

public class RenderMooshroom extends RenderLiving
{
    private static final ResourceLocation mooshroomTextures;
    private static final String __OBFID = "CL_00001016";
    
    public RenderMooshroom(final ModelBase p_i1263_1_, final float p_i1263_2_) {
        super(p_i1263_1_, p_i1263_2_);
    }
    
    public void doRender(final EntityMooshroom p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    public ResourceLocation getEntityTexture(final EntityMooshroom p_110775_1_) {
        return RenderMooshroom.mooshroomTextures;
    }
    
    protected void renderEquippedItems(final EntityMooshroom p_77029_1_, final float p_77029_2_) {
        super.renderEquippedItems(p_77029_1_, p_77029_2_);
        if (!p_77029_1_.isChild()) {
            this.bindTexture(TextureMap.locationBlocksTexture);
            GL11.glEnable(2884);
            GL11.glPushMatrix();
            GL11.glScalef(1.0f, -1.0f, 1.0f);
            GL11.glTranslatef(0.2f, 0.4f, 0.5f);
            GL11.glRotatef(42.0f, 0.0f, 1.0f, 0.0f);
            this.field_147909_c.renderBlockAsItem(Blocks.red_mushroom, 0, 1.0f);
            GL11.glTranslatef(0.1f, 0.0f, -0.6f);
            GL11.glRotatef(42.0f, 0.0f, 1.0f, 0.0f);
            this.field_147909_c.renderBlockAsItem(Blocks.red_mushroom, 0, 1.0f);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            ((ModelQuadruped)this.mainModel).head.postRender(0.0625f);
            GL11.glScalef(1.0f, -1.0f, 1.0f);
            GL11.glTranslatef(0.0f, 0.75f, -0.2f);
            GL11.glRotatef(12.0f, 0.0f, 1.0f, 0.0f);
            this.field_147909_c.renderBlockAsItem(Blocks.red_mushroom, 0, 1.0f);
            GL11.glPopMatrix();
            GL11.glDisable(2884);
        }
    }
    
    @Override
    public void doRender(final EntityLiving p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityMooshroom)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    protected void renderEquippedItems(final EntityLivingBase p_77029_1_, final float p_77029_2_) {
        this.renderEquippedItems((EntityMooshroom)p_77029_1_, p_77029_2_);
    }
    
    @Override
    public void doRender(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityMooshroom)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityMooshroom)p_110775_1_);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityMooshroom)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    static {
        mooshroomTextures = new ResourceLocation("textures/entity/cow/mooshroom.png");
    }
}
