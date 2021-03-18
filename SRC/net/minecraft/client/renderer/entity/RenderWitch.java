package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.monster.*;
import org.lwjgl.opengl.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;

public class RenderWitch extends RenderLiving
{
    private static final ResourceLocation witchTextures;
    private final ModelWitch witchModel;
    private static final String __OBFID = "CL_00001033";
    
    public RenderWitch() {
        super(new ModelWitch(0.0f), 0.5f);
        this.witchModel = (ModelWitch)this.mainModel;
    }
    
    public void doRender(final EntityWitch p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        final ItemStack var10 = p_76986_1_.getHeldItem();
        this.witchModel.field_82900_g = (var10 != null);
        super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    public ResourceLocation getEntityTexture(final EntityWitch p_110775_1_) {
        return RenderWitch.witchTextures;
    }
    
    protected void renderEquippedItems(final EntityWitch p_77029_1_, final float p_77029_2_) {
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        super.renderEquippedItems(p_77029_1_, p_77029_2_);
        final ItemStack var3 = p_77029_1_.getHeldItem();
        if (var3 != null) {
            GL11.glPushMatrix();
            if (this.mainModel.isChild) {
                final float var4 = 0.5f;
                GL11.glTranslatef(0.0f, 0.625f, 0.0f);
                GL11.glRotatef(-20.0f, -1.0f, 0.0f, 0.0f);
                GL11.glScalef(var4, var4, var4);
            }
            this.witchModel.villagerNose.postRender(0.0625f);
            GL11.glTranslatef(-0.0625f, 0.53125f, 0.21875f);
            if (var3.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(var3.getItem()).getRenderType())) {
                float var4 = 0.5f;
                GL11.glTranslatef(0.0f, 0.1875f, -0.3125f);
                var4 *= 0.75f;
                GL11.glRotatef(20.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
                GL11.glScalef(var4, -var4, var4);
            }
            else if (var3.getItem() == Items.bow) {
                final float var4 = 0.625f;
                GL11.glTranslatef(0.0f, 0.125f, 0.3125f);
                GL11.glRotatef(-20.0f, 0.0f, 1.0f, 0.0f);
                GL11.glScalef(var4, -var4, var4);
                GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            }
            else if (var3.getItem().isFull3D()) {
                final float var4 = 0.625f;
                if (var3.getItem().shouldRotateAroundWhenRendering()) {
                    GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
                    GL11.glTranslatef(0.0f, -0.125f, 0.0f);
                }
                this.func_82410_b();
                GL11.glScalef(var4, -var4, var4);
                GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            }
            else {
                final float var4 = 0.375f;
                GL11.glTranslatef(0.25f, 0.1875f, -0.1875f);
                GL11.glScalef(var4, var4, var4);
                GL11.glRotatef(60.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(20.0f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glRotatef(-15.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(40.0f, 0.0f, 0.0f, 1.0f);
            this.renderManager.itemRenderer.renderItem(p_77029_1_, var3, 0);
            if (var3.getItem().requiresMultipleRenderPasses()) {
                this.renderManager.itemRenderer.renderItem(p_77029_1_, var3, 1);
            }
            GL11.glPopMatrix();
        }
    }
    
    protected void func_82410_b() {
        GL11.glTranslatef(0.0f, 0.1875f, 0.0f);
    }
    
    public void preRenderCallback(final EntityWitch p_77041_1_, final float p_77041_2_) {
        final float var3 = 0.9375f;
        GL11.glScalef(var3, var3, var3);
    }
    
    @Override
    public void doRender(final EntityLiving p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityWitch)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    public void preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.preRenderCallback((EntityWitch)p_77041_1_, p_77041_2_);
    }
    
    @Override
    protected void renderEquippedItems(final EntityLivingBase p_77029_1_, final float p_77029_2_) {
        this.renderEquippedItems((EntityWitch)p_77029_1_, p_77029_2_);
    }
    
    @Override
    public void doRender(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityWitch)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityWitch)p_110775_1_);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityWitch)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    static {
        witchTextures = new ResourceLocation("textures/entity/witch.png");
    }
}
