package net.minecraft.src;

import net.minecraft.world.*;
import java.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class WrUpdaterThreaded implements IWrUpdater
{
    private WrUpdateThread updateThread;
    private float timePerUpdateMs;
    private long updateStartTimeNs;
    private boolean firstUpdate;
    private int updateTargetNum;
    
    public WrUpdaterThreaded() {
        this.updateThread = null;
        this.timePerUpdateMs = 10.0f;
        this.updateStartTimeNs = 0L;
        this.firstUpdate = true;
        this.updateTargetNum = 0;
    }
    
    @Override
    public void terminate() {
        if (this.updateThread != null) {
            this.updateThread.terminate();
            this.updateThread.unpauseToEndOfUpdate();
        }
    }
    
    @Override
    public void initialize() {
    }
    
    private void delayedInit() {
        if (this.updateThread == null) {
            this.createUpdateThread(Display.getDrawable());
        }
    }
    
    @Override
    public WorldRenderer makeWorldRenderer(final World worldObj, final List tileEntities, final int x, final int y, final int z, final int glRenderListBase) {
        return new WorldRendererThreaded(worldObj, tileEntities, x, y, z, glRenderListBase);
    }
    
    public WrUpdateThread createUpdateThread(final Drawable displayDrawable) {
        if (this.updateThread != null) {
            throw new IllegalStateException("UpdateThread is already existing");
        }
        try {
            final Pbuffer e = new Pbuffer(1, 1, new PixelFormat(), displayDrawable);
            (this.updateThread = new WrUpdateThread(e)).setPriority(1);
            this.updateThread.start();
            this.updateThread.pause();
            return this.updateThread;
        }
        catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }
    
    public boolean isUpdateThread() {
        return Thread.currentThread() == this.updateThread;
    }
    
    public static boolean isBackgroundChunkLoading() {
        return true;
    }
    
    @Override
    public void preRender(final RenderGlobal rg, final EntityLivingBase player) {
        this.updateTargetNum = 0;
        if (this.updateThread != null) {
            if (this.updateStartTimeNs == 0L) {
                this.updateStartTimeNs = System.nanoTime();
            }
            if (this.updateThread.hasWorkToDo()) {
                this.updateTargetNum = Config.getUpdatesPerFrame();
                if (Config.isDynamicUpdates() && !rg.isMoving(player)) {
                    this.updateTargetNum *= 3;
                }
                this.updateTargetNum = Math.min(this.updateTargetNum, this.updateThread.getPendingUpdatesCount());
                if (this.updateTargetNum > 0) {
                    this.updateThread.unpause();
                }
            }
        }
    }
    
    @Override
    public void postRender() {
        if (this.updateThread != null) {
            final float sleepTimeMs = 0.0f;
            if (this.updateTargetNum > 0) {
                final long deltaTime = System.nanoTime() - this.updateStartTimeNs;
                final float targetRunTime = this.timePerUpdateMs * (1.0f + (this.updateTargetNum - 1) / 2.0f);
                if (targetRunTime > 0.0f) {
                    final int sleepTimeMsInt = (int)targetRunTime;
                    Config.sleep(sleepTimeMsInt);
                }
                this.updateThread.pause();
            }
            final float deltaTime2 = 0.2f;
            if (this.updateTargetNum > 0) {
                final int updateCount = this.updateThread.resetUpdateCount();
                if (updateCount < this.updateTargetNum) {
                    this.timePerUpdateMs += deltaTime2;
                }
                if (updateCount > this.updateTargetNum) {
                    this.timePerUpdateMs -= deltaTime2;
                }
                if (updateCount == this.updateTargetNum) {
                    this.timePerUpdateMs -= deltaTime2;
                }
            }
            else {
                this.timePerUpdateMs -= deltaTime2 / 5.0f;
            }
            if (this.timePerUpdateMs < 0.0f) {
                this.timePerUpdateMs = 0.0f;
            }
            this.updateStartTimeNs = System.nanoTime();
        }
    }
    
    @Override
    public boolean updateRenderers(final RenderGlobal rg, final EntityLivingBase entityliving, final boolean flag) {
        this.delayedInit();
        if (rg.worldRenderersToUpdate.size() <= 0) {
            return true;
        }
        int num = 0;
        final byte NOT_IN_FRUSTRUM_MUL = 4;
        int numValid = 0;
        WorldRenderer wrBest = null;
        float distSqBest = Float.MAX_VALUE;
        int indexBest = -1;
        for (int maxUpdateNum = 0; maxUpdateNum < rg.worldRenderersToUpdate.size(); ++maxUpdateNum) {
            final WorldRenderer turboMode = (WorldRenderer)rg.worldRenderersToUpdate.get(maxUpdateNum);
            if (turboMode != null) {
                ++numValid;
                if (!turboMode.isUpdating) {
                    if (!turboMode.needsUpdate) {
                        rg.worldRenderersToUpdate.set(maxUpdateNum, null);
                    }
                    else {
                        float dstIndex = turboMode.distanceToEntitySquared(entityliving);
                        if (dstIndex < 512.0f) {
                            if ((dstIndex < 256.0f && rg.isActingNow() && turboMode.isInFrustum) || this.firstUpdate) {
                                if (this.updateThread != null) {
                                    this.updateThread.unpauseToEndOfUpdate();
                                }
                                turboMode.updateRenderer(entityliving);
                                turboMode.needsUpdate = false;
                                rg.worldRenderersToUpdate.set(maxUpdateNum, null);
                                ++num;
                                continue;
                            }
                            if (this.updateThread != null) {
                                this.updateThread.addRendererToUpdate(turboMode, true);
                                turboMode.needsUpdate = false;
                                rg.worldRenderersToUpdate.set(maxUpdateNum, null);
                                ++num;
                                continue;
                            }
                        }
                        if (!turboMode.isInFrustum) {
                            dstIndex *= NOT_IN_FRUSTRUM_MUL;
                        }
                        if (wrBest == null) {
                            wrBest = turboMode;
                            distSqBest = dstIndex;
                            indexBest = maxUpdateNum;
                        }
                        else if (dstIndex < distSqBest) {
                            wrBest = turboMode;
                            distSqBest = dstIndex;
                            indexBest = maxUpdateNum;
                        }
                    }
                }
            }
        }
        int maxUpdateNum = Config.getUpdatesPerFrame();
        boolean var17 = false;
        if (Config.isDynamicUpdates() && !rg.isMoving(entityliving)) {
            maxUpdateNum *= 3;
            var17 = true;
        }
        if (this.updateThread != null) {
            maxUpdateNum = this.updateThread.getUpdateCapacity();
            if (maxUpdateNum <= 0) {
                return true;
            }
        }
        if (wrBest != null) {
            this.updateRenderer(wrBest, entityliving);
            rg.worldRenderersToUpdate.set(indexBest, null);
            ++num;
            final float dstIndex = distSqBest / 5.0f;
            for (int i = 0; i < rg.worldRenderersToUpdate.size() && num < maxUpdateNum; ++i) {
                final WorldRenderer wr = (WorldRenderer)rg.worldRenderersToUpdate.get(i);
                if (wr != null && !wr.isUpdating) {
                    float distSq = wr.distanceToEntitySquared(entityliving);
                    if (!wr.isInFrustum) {
                        distSq *= NOT_IN_FRUSTRUM_MUL;
                    }
                    final float diffDistSq = Math.abs(distSq - distSqBest);
                    if (diffDistSq < dstIndex) {
                        this.updateRenderer(wr, entityliving);
                        rg.worldRenderersToUpdate.set(i, null);
                        ++num;
                    }
                }
            }
        }
        if (numValid == 0) {
            rg.worldRenderersToUpdate.clear();
        }
        if (rg.worldRenderersToUpdate.size() > 100 && numValid < rg.worldRenderersToUpdate.size() * 4 / 5) {
            int var18 = 0;
            for (int i = 0; i < rg.worldRenderersToUpdate.size(); ++i) {
                final Object var19 = rg.worldRenderersToUpdate.get(i);
                if (var19 != null) {
                    if (i != var18) {
                        rg.worldRenderersToUpdate.set(var18, var19);
                    }
                    ++var18;
                }
            }
            for (int i = rg.worldRenderersToUpdate.size() - 1; i >= var18; --i) {
                rg.worldRenderersToUpdate.remove(i);
            }
        }
        this.firstUpdate = false;
        return true;
    }
    
    private void updateRenderer(final WorldRenderer wr, final EntityLivingBase entityLiving) {
        final WrUpdateThread ut = this.updateThread;
        if (ut != null) {
            ut.addRendererToUpdate(wr, false);
            wr.needsUpdate = false;
        }
        else {
            wr.updateRenderer(entityLiving);
            wr.needsUpdate = false;
            wr.isUpdating = false;
        }
    }
    
    @Override
    public void finishCurrentUpdate() {
        if (this.updateThread != null) {
            this.updateThread.unpauseToEndOfUpdate();
        }
    }
    
    @Override
    public void resumeBackgroundUpdates() {
        if (this.updateThread != null) {
            this.updateThread.unpause();
        }
    }
    
    @Override
    public void pauseBackgroundUpdates() {
        if (this.updateThread != null) {
            this.updateThread.pause();
        }
    }
    
    @Override
    public void clearAllUpdates() {
        if (this.updateThread != null) {
            this.updateThread.clearAllUpdates();
        }
        this.firstUpdate = true;
    }
}
