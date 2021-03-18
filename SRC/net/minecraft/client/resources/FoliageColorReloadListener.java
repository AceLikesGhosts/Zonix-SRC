package net.minecraft.client.resources;

import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import java.io.*;

public class FoliageColorReloadListener implements IResourceManagerReloadListener
{
    private static final ResourceLocation field_130079_a;
    private static final String __OBFID = "CL_00001077";
    
    @Override
    public void onResourceManagerReload(final IResourceManager p_110549_1_) {
        try {
            ColorizerFoliage.setFoliageBiomeColorizer(TextureUtil.readImageData(p_110549_1_, FoliageColorReloadListener.field_130079_a));
        }
        catch (IOException ex) {}
    }
    
    static {
        field_130079_a = new ResourceLocation("textures/colormap/foliage.png");
    }
}
