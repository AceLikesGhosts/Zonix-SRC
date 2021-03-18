package net.minecraft.src;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class WrUpdates
{
    private static IWrUpdater wrUpdater;
    
    public static void setWrUpdater(final IWrUpdater updater) {
        if (WrUpdates.wrUpdater != null) {
            WrUpdates.wrUpdater.terminate();
        }
        WrUpdates.wrUpdater = updater;
        if (WrUpdates.wrUpdater != null) {
            try {
                WrUpdates.wrUpdater.initialize();
            }
            catch (Exception var2) {
                WrUpdates.wrUpdater = null;
                var2.printStackTrace();
            }
        }
    }
    
    public static boolean hasWrUpdater() {
        return WrUpdates.wrUpdater != null;
    }
    
    public static IWrUpdater getWrUpdater() {
        return WrUpdates.wrUpdater;
    }
    
    public static WorldRenderer makeWorldRenderer(final World worldObj, final List tileEntities, final int x, final int y, final int z, final int glRenderListBase) {
        return (WrUpdates.wrUpdater == null) ? new WorldRenderer(worldObj, tileEntities, x, y, z, glRenderListBase) : WrUpdates.wrUpdater.makeWorldRenderer(worldObj, tileEntities, x, y, z, glRenderListBase);
    }
    
    public static boolean updateRenderers(final RenderGlobal rg, final EntityLivingBase entityliving, final boolean flag) {
        try {
            return WrUpdates.wrUpdater.updateRenderers(rg, entityliving, flag);
        }
        catch (Exception var4) {
            var4.printStackTrace();
            setWrUpdater(null);
            return false;
        }
    }
    
    public static void resumeBackgroundUpdates() {
        if (WrUpdates.wrUpdater != null) {
            WrUpdates.wrUpdater.resumeBackgroundUpdates();
        }
    }
    
    public static void pauseBackgroundUpdates() {
        if (WrUpdates.wrUpdater != null) {
            WrUpdates.wrUpdater.pauseBackgroundUpdates();
        }
    }
    
    public static void finishCurrentUpdate() {
        if (WrUpdates.wrUpdater != null) {
            WrUpdates.wrUpdater.finishCurrentUpdate();
        }
    }
    
    public static void preRender(final RenderGlobal rg, final EntityLivingBase player) {
        if (WrUpdates.wrUpdater != null) {
            WrUpdates.wrUpdater.preRender(rg, player);
        }
    }
    
    public static void postRender() {
        if (WrUpdates.wrUpdater != null) {
            WrUpdates.wrUpdater.postRender();
        }
    }
    
    public static void clearAllUpdates() {
        if (WrUpdates.wrUpdater != null) {
            WrUpdates.wrUpdater.clearAllUpdates();
        }
    }
    
    static {
        WrUpdates.wrUpdater = null;
    }
}
