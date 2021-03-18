package net.minecraft.src;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class WrUpdaterSmooth implements IWrUpdater
{
    private long lastUpdateStartTimeNs;
    private long updateStartTimeNs;
    private long updateTimeNs;
    private WorldRendererSmooth currentUpdateRenderer;
    private int renderersUpdated;
    private int renderersFound;
    
    public WrUpdaterSmooth() {
        this.lastUpdateStartTimeNs = 0L;
        this.updateStartTimeNs = 0L;
        this.updateTimeNs = 10000000L;
        this.currentUpdateRenderer = null;
        this.renderersUpdated = 0;
        this.renderersFound = 0;
    }
    
    @Override
    public void initialize() {
    }
    
    @Override
    public void terminate() {
    }
    
    @Override
    public WorldRenderer makeWorldRenderer(final World worldObj, final List tileEntities, final int x, final int y, final int z, final int glRenderListBase) {
        return new WorldRendererSmooth(worldObj, tileEntities, x, y, z, glRenderListBase);
    }
    
    @Override
    public boolean updateRenderers(final RenderGlobal rg, final EntityLivingBase entityliving, final boolean flag) {
        this.lastUpdateStartTimeNs = this.updateStartTimeNs;
        this.updateStartTimeNs = System.nanoTime();
        final long finishTimeNs = this.updateStartTimeNs + this.updateTimeNs;
        int maxNum = Config.getUpdatesPerFrame();
        if (Config.isDynamicUpdates() && !rg.isMoving(entityliving)) {
            maxNum *= 3;
        }
        this.renderersUpdated = 0;
        do {
            this.renderersFound = 0;
            this.updateRenderersImpl(rg, entityliving, flag);
        } while (this.renderersFound > 0 && System.nanoTime() - finishTimeNs < 0L);
        if (this.renderersFound > 0) {
            maxNum = Math.min(maxNum, this.renderersFound);
            final long diff = 400000L;
            if (this.renderersUpdated > maxNum) {
                this.updateTimeNs -= 2L * diff;
            }
            if (this.renderersUpdated < maxNum) {
                this.updateTimeNs += diff;
            }
        }
        else {
            this.updateTimeNs = 0L;
            this.updateTimeNs -= 200000L;
        }
        if (this.updateTimeNs < 0L) {
            this.updateTimeNs = 0L;
        }
        return this.renderersUpdated > 0;
    }
    
    private void updateRenderersImpl(final RenderGlobal rg, final EntityLivingBase entityliving, final boolean flag) {
        this.renderersFound = 0;
        boolean currentUpdateFinished = true;
        if (this.currentUpdateRenderer != null) {
            ++this.renderersFound;
            currentUpdateFinished = this.updateRenderer(this.currentUpdateRenderer);
            if (currentUpdateFinished) {
                ++this.renderersUpdated;
            }
        }
        if (rg.worldRenderersToUpdate.size() > 0) {
            final byte NOT_IN_FRUSTRUM_MUL = 4;
            WorldRendererSmooth wrBest = null;
            float distSqBest = Float.MAX_VALUE;
            int indexBest = -1;
            for (int dstIndex = 0; dstIndex < rg.worldRenderersToUpdate.size(); ++dstIndex) {
                final WorldRendererSmooth i = (WorldRendererSmooth)rg.worldRenderersToUpdate.get(dstIndex);
                if (i != null) {
                    ++this.renderersFound;
                    if (!i.needsUpdate) {
                        rg.worldRenderersToUpdate.set(dstIndex, null);
                    }
                    else {
                        float wr = i.distanceToEntitySquared(entityliving);
                        if (wr <= 256.0f && rg.isActingNow()) {
                            i.updateRenderer();
                            i.needsUpdate = false;
                            rg.worldRenderersToUpdate.set(dstIndex, null);
                            ++this.renderersUpdated;
                        }
                        else {
                            if (!i.isInFrustum) {
                                wr *= NOT_IN_FRUSTRUM_MUL;
                            }
                            if (wrBest == null) {
                                wrBest = i;
                                distSqBest = wr;
                                indexBest = dstIndex;
                            }
                            else if (wr < distSqBest) {
                                wrBest = i;
                                distSqBest = wr;
                                indexBest = dstIndex;
                            }
                        }
                    }
                }
            }
            if (this.currentUpdateRenderer == null || currentUpdateFinished) {
                if (wrBest != null) {
                    rg.worldRenderersToUpdate.set(indexBest, null);
                    if (!this.updateRenderer(wrBest)) {
                        return;
                    }
                    ++this.renderersUpdated;
                    if (System.nanoTime() > this.updateStartTimeNs + this.updateTimeNs) {
                        return;
                    }
                    final float var14 = distSqBest / 5.0f;
                    for (int var15 = 0; var15 < rg.worldRenderersToUpdate.size(); ++var15) {
                        final WorldRendererSmooth var16 = (WorldRendererSmooth)rg.worldRenderersToUpdate.get(var15);
                        if (var16 != null) {
                            float distSq = var16.distanceToEntitySquared(entityliving);
                            if (!var16.isInFrustum) {
                                distSq *= NOT_IN_FRUSTRUM_MUL;
                            }
                            final float diffDistSq = Math.abs(distSq - distSqBest);
                            if (diffDistSq < var14) {
                                rg.worldRenderersToUpdate.set(var15, null);
                                if (!this.updateRenderer(var16)) {
                                    return;
                                }
                                ++this.renderersUpdated;
                                if (System.nanoTime() > this.updateStartTimeNs + this.updateTimeNs) {
                                    break;
                                }
                            }
                        }
                    }
                }
                if (this.renderersFound == 0) {
                    rg.worldRenderersToUpdate.clear();
                }
                if (rg.worldRenderersToUpdate.size() > 100 && this.renderersFound < rg.worldRenderersToUpdate.size() * 4 / 5) {
                    int dstIndex = 0;
                    for (int var15 = 0; var15 < rg.worldRenderersToUpdate.size(); ++var15) {
                        final Object var17 = rg.worldRenderersToUpdate.get(var15);
                        if (var17 != null) {
                            if (var15 != dstIndex) {
                                rg.worldRenderersToUpdate.set(dstIndex, var17);
                            }
                            ++dstIndex;
                        }
                    }
                    for (int var15 = rg.worldRenderersToUpdate.size() - 1; var15 >= dstIndex; --var15) {
                        rg.worldRenderersToUpdate.remove(var15);
                    }
                }
            }
        }
    }
    
    private boolean updateRenderer(final WorldRendererSmooth wr) {
        final long finishTime = this.updateStartTimeNs + this.updateTimeNs;
        wr.needsUpdate = false;
        final boolean ready = wr.updateRenderer(finishTime);
        if (!ready) {
            this.currentUpdateRenderer = wr;
            return false;
        }
        wr.finishUpdate();
        this.currentUpdateRenderer = null;
        return true;
    }
    
    @Override
    public void finishCurrentUpdate() {
        if (this.currentUpdateRenderer != null) {
            this.currentUpdateRenderer.updateRenderer();
            this.currentUpdateRenderer = null;
        }
    }
    
    @Override
    public void resumeBackgroundUpdates() {
    }
    
    @Override
    public void pauseBackgroundUpdates() {
    }
    
    @Override
    public void preRender(final RenderGlobal rg, final EntityLivingBase player) {
    }
    
    @Override
    public void postRender() {
    }
    
    @Override
    public void clearAllUpdates() {
        this.finishCurrentUpdate();
    }
}
