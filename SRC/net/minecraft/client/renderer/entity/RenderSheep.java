package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.passive.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;

public class RenderSheep extends RenderLiving
{
    private static final ResourceLocation sheepTextures;
    private static final ResourceLocation shearedSheepTextures;
    private static final String __OBFID = "CL_00001021";
    
    public RenderSheep(final ModelBase p_i1266_1_, final ModelBase p_i1266_2_, final float p_i1266_3_) {
        super(p_i1266_1_, p_i1266_3_);
        this.setRenderPassModel(p_i1266_2_);
    }
    
    public int shouldRenderPass(final EntitySheep p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        if (p_77032_2_ == 0 && !p_77032_1_.getSheared()) {
            this.bindTexture(RenderSheep.sheepTextures);
            if (p_77032_1_.hasCustomNameTag() && "jeb_".equals(p_77032_1_.getCustomNameTag())) {
                final boolean var9 = true;
                final int var10 = p_77032_1_.ticksExisted / 25 + p_77032_1_.getEntityId();
                final int var11 = var10 % EntitySheep.fleeceColorTable.length;
                final int var12 = (var10 + 1) % EntitySheep.fleeceColorTable.length;
                final float var13 = (p_77032_1_.ticksExisted % 25 + p_77032_3_) / 25.0f;
                GL11.glColor3f(EntitySheep.fleeceColorTable[var11][0] * (1.0f - var13) + EntitySheep.fleeceColorTable[var12][0] * var13, EntitySheep.fleeceColorTable[var11][1] * (1.0f - var13) + EntitySheep.fleeceColorTable[var12][1] * var13, EntitySheep.fleeceColorTable[var11][2] * (1.0f - var13) + EntitySheep.fleeceColorTable[var12][2] * var13);
            }
            else {
                final int var14 = p_77032_1_.getFleeceColor();
                GL11.glColor3f(EntitySheep.fleeceColorTable[var14][0], EntitySheep.fleeceColorTable[var14][1], EntitySheep.fleeceColorTable[var14][2]);
            }
            return 1;
        }
        return -1;
    }
    
    public ResourceLocation getEntityTexture(final EntitySheep p_110775_1_) {
        return RenderSheep.shearedSheepTextures;
    }
    
    @Override
    public int shouldRenderPass(final EntityLivingBase p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        return this.shouldRenderPass((EntitySheep)p_77032_1_, p_77032_2_, p_77032_3_);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntitySheep)p_110775_1_);
    }
    
    static {
        sheepTextures = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
        shearedSheepTextures = new ResourceLocation("textures/entity/sheep/sheep.png");
    }
}
