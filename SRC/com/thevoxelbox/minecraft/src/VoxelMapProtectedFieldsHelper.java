package com.thevoxelbox.minecraft.src;

import net.minecraft.util.*;
import com.thevoxelbox.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import com.thevoxelbox.minecraft.block.*;

public class VoxelMapProtectedFieldsHelper
{
    static boolean getRendersResourceLocationDirect;
    static boolean getRendersModelDirect;
    static boolean getRendersPassModelDirect;
    static boolean preRenderDirect;
    static boolean setLightOpacityDirect;
    
    public static ResourceLocation getRendersResourceLocation(final Render render, final Entity entity) {
        if (VoxelMapProtectedFieldsHelper.preRenderDirect) {
            VoxelMapProtectedFieldsHelper.preRenderDirect = false;
            return VoxelMapRenderProtectedFieldsHelper.getRendersResourceLocation(render, entity);
        }
        return VoxelMapRenderProtectedFieldsHelper.getRendersResourceLocation(render, entity);
    }
    
    public static ModelBase getRendersModel(final RendererLivingEntity render) {
        if (VoxelMapProtectedFieldsHelper.getRendersModelDirect) {
            VoxelMapProtectedFieldsHelper.getRendersModelDirect = false;
            return VoxelMapRenderProtectedFieldsHelper.getRendersModel(render);
        }
        return VoxelMapRenderProtectedFieldsHelper.getRendersModel(render);
    }
    
    public static ModelBase getRendersPassModel(final Render render, final Entity entity) {
        if (VoxelMapProtectedFieldsHelper.getRendersPassModelDirect) {
            VoxelMapProtectedFieldsHelper.getRendersPassModelDirect = false;
            return VoxelMapRenderProtectedFieldsHelper.getRendersPassModel(render, entity);
        }
        return VoxelMapRenderProtectedFieldsHelper.getRendersPassModel(render, entity);
    }
    
    public static void preRender(final EntityLivingBase par1EntityLivingBase, final RendererLivingEntity render) {
        if (VoxelMapProtectedFieldsHelper.preRenderDirect) {
            VoxelMapProtectedFieldsHelper.preRenderDirect = false;
            VoxelMapRenderProtectedFieldsHelper.preRender(par1EntityLivingBase, render);
        }
        else {
            VoxelMapRenderProtectedFieldsHelper.preRender(par1EntityLivingBase, render);
        }
    }
    
    public static void setLightOpacity(final Block block, final int opacity) {
        if (VoxelMapProtectedFieldsHelper.setLightOpacityDirect) {
            try {
                block.setLightOpacity(opacity);
            }
            catch (IllegalAccessError e) {
                VoxelMapProtectedFieldsHelper.setLightOpacityDirect = false;
                VoxelMapBlockProtectedFieldsHelper.setLightOpacity(block, opacity);
            }
        }
        else {
            VoxelMapBlockProtectedFieldsHelper.setLightOpacity(block, opacity);
        }
    }
    
    static {
        VoxelMapProtectedFieldsHelper.getRendersResourceLocationDirect = true;
        VoxelMapProtectedFieldsHelper.getRendersModelDirect = true;
        VoxelMapProtectedFieldsHelper.getRendersPassModelDirect = true;
        VoxelMapProtectedFieldsHelper.preRenderDirect = true;
        VoxelMapProtectedFieldsHelper.setLightOpacityDirect = true;
    }
}
