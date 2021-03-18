package net.minecraft.entity.ai;

import net.minecraft.profiler.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class EntityAITasks
{
    private static final Logger logger;
    private List taskEntries;
    private List executingTaskEntries;
    private final Profiler theProfiler;
    private int tickCount;
    private int tickRate;
    private static final String __OBFID = "CL_00001588";
    
    public EntityAITasks(final Profiler p_i1628_1_) {
        this.taskEntries = new ArrayList();
        this.executingTaskEntries = new ArrayList();
        this.tickRate = 3;
        this.theProfiler = p_i1628_1_;
    }
    
    public void addTask(final int p_75776_1_, final EntityAIBase p_75776_2_) {
        this.taskEntries.add(new EntityAITaskEntry(p_75776_1_, p_75776_2_));
    }
    
    public void removeTask(final EntityAIBase p_85156_1_) {
        final Iterator var2 = this.taskEntries.iterator();
        while (var2.hasNext()) {
            final EntityAITaskEntry var3 = var2.next();
            final EntityAIBase var4 = var3.action;
            if (var4 == p_85156_1_) {
                if (this.executingTaskEntries.contains(var3)) {
                    var4.resetTask();
                    this.executingTaskEntries.remove(var3);
                }
                var2.remove();
            }
        }
    }
    
    public void onUpdateTasks() {
        final ArrayList var1 = new ArrayList();
        if (this.tickCount++ % this.tickRate == 0) {
            for (final EntityAITaskEntry var3 : this.taskEntries) {
                final boolean var4 = this.executingTaskEntries.contains(var3);
                if (var4) {
                    if (this.canUse(var3) && this.canContinue(var3)) {
                        continue;
                    }
                    var3.action.resetTask();
                    this.executingTaskEntries.remove(var3);
                }
                if (this.canUse(var3) && var3.action.shouldExecute()) {
                    var1.add(var3);
                    this.executingTaskEntries.add(var3);
                }
            }
        }
        else {
            final Iterator var2 = this.executingTaskEntries.iterator();
            while (var2.hasNext()) {
                final EntityAITaskEntry var3 = var2.next();
                if (!var3.action.continueExecuting()) {
                    var3.action.resetTask();
                    var2.remove();
                }
            }
        }
        this.theProfiler.startSection("goalStart");
        Iterator var2 = var1.iterator();
        while (var2.hasNext()) {
            final EntityAITaskEntry var3 = var2.next();
            this.theProfiler.startSection(var3.action.getClass().getSimpleName());
            var3.action.startExecuting();
            this.theProfiler.endSection();
        }
        this.theProfiler.endSection();
        this.theProfiler.startSection("goalTick");
        var2 = this.executingTaskEntries.iterator();
        while (var2.hasNext()) {
            final EntityAITaskEntry var3 = var2.next();
            var3.action.updateTask();
        }
        this.theProfiler.endSection();
    }
    
    private boolean canContinue(final EntityAITaskEntry p_75773_1_) {
        this.theProfiler.startSection("canContinue");
        final boolean var2 = p_75773_1_.action.continueExecuting();
        this.theProfiler.endSection();
        return var2;
    }
    
    private boolean canUse(final EntityAITaskEntry p_75775_1_) {
        this.theProfiler.startSection("canUse");
        for (final EntityAITaskEntry var3 : this.taskEntries) {
            if (var3 != p_75775_1_) {
                if (p_75775_1_.priority >= var3.priority) {
                    if (this.executingTaskEntries.contains(var3) && !this.areTasksCompatible(p_75775_1_, var3)) {
                        this.theProfiler.endSection();
                        return false;
                    }
                    continue;
                }
                else {
                    if (this.executingTaskEntries.contains(var3) && !var3.action.isInterruptible()) {
                        this.theProfiler.endSection();
                        return false;
                    }
                    continue;
                }
            }
        }
        this.theProfiler.endSection();
        return true;
    }
    
    private boolean areTasksCompatible(final EntityAITaskEntry p_75777_1_, final EntityAITaskEntry p_75777_2_) {
        return (p_75777_1_.action.getMutexBits() & p_75777_2_.action.getMutexBits()) == 0x0;
    }
    
    static {
        logger = LogManager.getLogger();
    }
    
    class EntityAITaskEntry
    {
        public EntityAIBase action;
        public int priority;
        private static final String __OBFID = "CL_00001589";
        
        public EntityAITaskEntry(final int p_i1627_2_, final EntityAIBase p_i1627_3_) {
            this.priority = p_i1627_2_;
            this.action = p_i1627_3_;
        }
    }
}
