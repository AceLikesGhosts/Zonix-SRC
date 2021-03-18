package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.monster.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import org.lwjgl.opengl.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderSnowMan extends RenderLiving
{
    private static final ResourceLocation snowManTextures;
    private ModelSnowMan snowmanModel;
    private static final String __OBFID = "CL_00001025";
    
    public RenderSnowMan() {
        super(new ModelSnowMan(), 0.5f);
        this.setRenderPassModel(this.snowmanModel = (ModelSnowMan)super.mainModel);
    }
    
    protected void renderEquippedItems(final EntitySnowman p_77029_1_, final float p_77029_2_) {
        super.renderEquippedItems(p_77029_1_, p_77029_2_);
        final ItemStack var3 = new ItemStack(Blocks.pumpkin, 1);
        if (var3.getItem() instanceof ItemBlock) {
            GL11.glPushMatrix();
            this.snowmanModel.head.postRender(0.0625f);
            if (RenderBlocks.renderItemIn3d(Block.getBlockFromItem(var3.getItem()).getRenderType())) {
                final float var4 = 0.625f;
                GL11.glTranslatef(0.0f, -0.34375f, 0.0f);
                GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                GL11.glScalef(var4, -var4, var4);
            }
            this.renderManager.itemRenderer.renderItem(p_77029_1_, var3, 0);
            GL11.glPopMatrix();
        }
    }
    
    public ResourceLocation getEntityTexture(final EntitySnowman p_110775_1_) {
        return RenderSnowMan.snowManTextures;
    }
    
    @Override
    protected void renderEquippedItems(final EntityLivingBase p_77029_1_, final float p_77029_2_) {
        this.renderEquippedItems((EntitySnowman)p_77029_1_, p_77029_2_);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntitySnowman)p_110775_1_);
    }
    
    static {
        snowManTextures = new ResourceLocation("textures/entity/snowman.png");
    }
}
