package com.thevoxelbox.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;

public class VoxelMapRenderProtectedFieldsHelper
{
    public static ResourceLocation getRendersResourceLocation(final Render render, final Entity entity) {
        return render.getEntityTexture(entity);
    }
    
    public static ModelBase getRendersModel(final RendererLivingEntity render) {
        return render.mainModel;
    }
    
    public static ModelBase getRendersPassModel(final Render render, final Entity entity) {
        ((RendererLivingEntity)render).shouldRenderPass((EntityLivingBase)entity, 0, 0.0f);
        return ((RendererLivingEntity)render).renderPassModel;
    }
    
    public static void preRender(final EntityLivingBase par1EntityLivingBase, final RendererLivingEntity render) {
        render.preRenderCallback(par1EntityLivingBase, 1.0f);
    }
}
