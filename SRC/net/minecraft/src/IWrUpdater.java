package net.minecraft.src;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public interface IWrUpdater
{
    void initialize();
    
    WorldRenderer makeWorldRenderer(final World p0, final List p1, final int p2, final int p3, final int p4, final int p5);
    
    void preRender(final RenderGlobal p0, final EntityLivingBase p1);
    
    void postRender();
    
    boolean updateRenderers(final RenderGlobal p0, final EntityLivingBase p1, final boolean p2);
    
    void resumeBackgroundUpdates();
    
    void pauseBackgroundUpdates();
    
    void finishCurrentUpdate();
    
    void clearAllUpdates();
    
    void terminate();
}
