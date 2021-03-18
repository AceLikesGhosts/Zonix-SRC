package net.minecraft.src;

import org.lwjgl.opengl.*;
import java.util.*;
import net.minecraft.client.renderer.*;

public class WrUpdateThread extends Thread
{
    private Pbuffer pbuffer;
    private Object lock;
    private List updateList;
    private List updatedList;
    private int updateCount;
    private Tessellator mainTessellator;
    private Tessellator threadTessellator;
    private boolean working;
    private WorldRendererThreaded currentRenderer;
    private boolean canWork;
    private boolean canWorkToEndOfUpdate;
    private boolean terminated;
    private static final int MAX_UPDATE_CAPACITY = 10;
    
    public WrUpdateThread(final Pbuffer pbuffer) {
        super("WrUpdateThread");
        this.pbuffer = null;
        this.lock = new Object();
        this.updateList = new LinkedList();
        this.updatedList = new LinkedList();
        this.updateCount = 0;
        this.mainTessellator = Tessellator.instance;
        this.threadTessellator = new Tessellator(2097152);
        this.working = false;
        this.currentRenderer = null;
        this.canWork = false;
        this.canWorkToEndOfUpdate = false;
        this.terminated = false;
        this.pbuffer = pbuffer;
    }
    
    @Override
    public void run() {
        try {
            this.pbuffer.makeCurrent();
        }
        catch (Exception var8) {
            var8.printStackTrace();
        }
        final ThreadUpdateListener updateListener = new ThreadUpdateListener(null);
        while (!Thread.interrupted() && !this.terminated) {
            try {
                final WorldRendererThreaded e = this.getRendererToUpdate();
                if (e == null) {
                    return;
                }
                this.checkCanWork(null);
                try {
                    this.currentRenderer = e;
                    Tessellator.instance = this.threadTessellator;
                    e.updateRenderer(updateListener);
                }
                finally {
                    Tessellator.instance = this.mainTessellator;
                }
                this.rendererUpdated(e);
            }
            catch (Exception var9) {
                var9.printStackTrace();
                if (this.currentRenderer != null) {
                    this.currentRenderer.isUpdating = false;
                    this.currentRenderer.needsUpdate = true;
                }
                this.currentRenderer = null;
                this.working = false;
            }
        }
    }
    
    public void addRendererToUpdate(final WorldRenderer wr, final boolean first) {
        final Object var3 = this.lock;
        synchronized (this.lock) {
            if (wr.isUpdating) {
                throw new IllegalArgumentException("Renderer already updating");
            }
            if (first) {
                this.updateList.add(0, wr);
            }
            else {
                this.updateList.add(wr);
            }
            wr.isUpdating = true;
            this.lock.notifyAll();
        }
    }
    
    private WorldRendererThreaded getRendererToUpdate() {
        final Object var1 = this.lock;
        synchronized (this.lock) {
            while (this.updateList.size() <= 0) {
                try {
                    this.lock.wait(2000L);
                    if (this.terminated) {
                        final Object var2 = null;
                        return (WorldRendererThreaded)var2;
                    }
                    continue;
                }
                catch (InterruptedException ex) {}
            }
            final WorldRendererThreaded wrt = this.updateList.remove(0);
            this.lock.notifyAll();
            return wrt;
        }
    }
    
    public boolean hasWorkToDo() {
        final Object var1 = this.lock;
        synchronized (this.lock) {
            return this.updateList.size() > 0 || this.currentRenderer != null || this.working;
        }
    }
    
    public int getUpdateCapacity() {
        final Object var1 = this.lock;
        synchronized (this.lock) {
            return (this.updateList.size() > 10) ? 0 : (10 - this.updateList.size());
        }
    }
    
    private void rendererUpdated(final WorldRenderer wr) {
        final Object var2 = this.lock;
        synchronized (this.lock) {
            this.updatedList.add(wr);
            ++this.updateCount;
            this.currentRenderer = null;
            this.working = false;
            this.lock.notifyAll();
        }
    }
    
    private void finishUpdatedRenderers() {
        final Object var1 = this.lock;
        synchronized (this.lock) {
            for (int i = 0; i < this.updatedList.size(); ++i) {
                final WorldRendererThreaded wr = this.updatedList.get(i);
                wr.finishUpdate();
                wr.isUpdating = false;
            }
            this.updatedList.clear();
        }
    }
    
    public void pause() {
        final Object var1 = this.lock;
        synchronized (this.lock) {
            this.canWork = false;
            this.canWorkToEndOfUpdate = false;
            this.lock.notifyAll();
            while (this.working) {
                try {
                    this.lock.wait();
                }
                catch (InterruptedException ex) {}
            }
            this.finishUpdatedRenderers();
        }
    }
    
    public void unpause() {
        final Object var1 = this.lock;
        synchronized (this.lock) {
            if (this.working) {
                Config.warn("UpdateThread still working in unpause()!!!");
            }
            this.canWork = true;
            this.canWorkToEndOfUpdate = false;
            this.lock.notifyAll();
        }
    }
    
    public void unpauseToEndOfUpdate() {
        final Object var1 = this.lock;
        synchronized (this.lock) {
            if (this.working) {
                Config.warn("UpdateThread still working in unpause()!!!");
            }
            if (this.currentRenderer != null) {
                while (this.currentRenderer != null) {
                    this.canWork = false;
                    this.canWorkToEndOfUpdate = true;
                    this.lock.notifyAll();
                    try {
                        this.lock.wait();
                    }
                    catch (InterruptedException ex) {}
                }
                this.pause();
            }
        }
    }
    
    private void checkCanWork(final IWrUpdateControl uc) {
        Thread.yield();
        final Object var2 = this.lock;
        synchronized (this.lock) {
            while (!this.canWork && (!this.canWorkToEndOfUpdate || this.currentRenderer == null)) {
                if (uc != null) {
                    uc.pause();
                }
                this.working = false;
                this.lock.notifyAll();
                try {
                    this.lock.wait();
                }
                catch (InterruptedException ex) {}
            }
            this.working = true;
            if (uc != null) {
                uc.resume();
            }
            this.lock.notifyAll();
        }
    }
    
    public void clearAllUpdates() {
        final Object var1 = this.lock;
        synchronized (this.lock) {
            this.unpauseToEndOfUpdate();
            for (int i = 0; i < this.updateList.size(); ++i) {
                final WorldRenderer wr = this.updateList.get(i);
                wr.needsUpdate = true;
                wr.isUpdating = false;
            }
            this.updateList.clear();
            this.lock.notifyAll();
        }
    }
    
    public int getPendingUpdatesCount() {
        final Object var1 = this.lock;
        synchronized (this.lock) {
            int count = this.updateList.size();
            if (this.currentRenderer != null) {
                ++count;
            }
            return count;
        }
    }
    
    public int resetUpdateCount() {
        final Object var1 = this.lock;
        synchronized (this.lock) {
            final int count = this.updateCount;
            this.updateCount = 0;
            return count;
        }
    }
    
    public void terminate() {
        this.terminated = true;
    }
    
    static class NamelessClass1621800805
    {
    }
    
    private class ThreadUpdateControl implements IWrUpdateControl
    {
        private IWrUpdateControl updateControl;
        private boolean paused;
        
        private ThreadUpdateControl() {
            this.updateControl = null;
            this.paused = false;
        }
        
        @Override
        public void pause() {
            if (!this.paused) {
                this.paused = true;
                this.updateControl.pause();
                Tessellator.instance = WrUpdateThread.this.mainTessellator;
            }
        }
        
        @Override
        public void resume() {
            if (this.paused) {
                this.paused = false;
                Tessellator.instance = WrUpdateThread.this.threadTessellator;
                this.updateControl.resume();
            }
        }
        
        public void setUpdateControl(final IWrUpdateControl updateControl) {
            this.updateControl = updateControl;
        }
        
        ThreadUpdateControl(final WrUpdateThread wrUpdateThread, final NamelessClass1621800805 x1) {
            this(wrUpdateThread);
        }
    }
    
    private class ThreadUpdateListener implements IWrUpdateListener
    {
        private ThreadUpdateControl tuc;
        
        private ThreadUpdateListener() {
            this.tuc = new ThreadUpdateControl(null);
        }
        
        @Override
        public void updating(final IWrUpdateControl uc) {
            this.tuc.setUpdateControl(uc);
            WrUpdateThread.this.checkCanWork(this.tuc);
        }
        
        ThreadUpdateListener(final WrUpdateThread wrUpdateThread, final NamelessClass1621800805 x1) {
            this(wrUpdateThread);
        }
    }
}
