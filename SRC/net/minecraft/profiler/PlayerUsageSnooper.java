package net.minecraft.profiler;

import com.google.common.collect.*;
import java.net.*;
import net.minecraft.util.*;
import java.lang.management.*;
import java.util.*;

public class PlayerUsageSnooper
{
    private final Map field_152773_a;
    private final Map field_152774_b;
    private final String uniqueID;
    private final URL serverUrl;
    private final IPlayerUsage playerStatsCollector;
    private final Timer threadTrigger;
    private final Object syncLock;
    private final long minecraftStartTimeMilis;
    private boolean isRunning;
    private int selfCounter;
    private static final String __OBFID = "CL_00001515";
    
    public PlayerUsageSnooper(final String p_i1563_1_, final IPlayerUsage p_i1563_2_, final long p_i1563_3_) {
        this.field_152773_a = Maps.newHashMap();
        this.field_152774_b = Maps.newHashMap();
        this.uniqueID = UUID.randomUUID().toString();
        this.threadTrigger = new Timer("Snooper Timer", true);
        this.syncLock = new Object();
        try {
            this.serverUrl = new URL("http://snoop.minecraft.net/" + p_i1563_1_ + "?version=" + 2);
        }
        catch (MalformedURLException var6) {
            throw new IllegalArgumentException();
        }
        this.playerStatsCollector = p_i1563_2_;
        this.minecraftStartTimeMilis = p_i1563_3_;
    }
    
    public void startSnooper() {
        if (!this.isRunning) {
            this.isRunning = true;
            this.func_152766_h();
            this.threadTrigger.schedule(new TimerTask() {
                private static final String __OBFID = "CL_00001516";
                
                @Override
                public void run() {
                    if (PlayerUsageSnooper.this.playerStatsCollector.isSnooperEnabled()) {
                        final HashMap var1;
                        synchronized (PlayerUsageSnooper.this.syncLock) {
                            var1 = new HashMap(PlayerUsageSnooper.this.field_152774_b);
                            if (PlayerUsageSnooper.this.selfCounter == 0) {
                                var1.putAll(PlayerUsageSnooper.this.field_152773_a);
                            }
                            var1.put("snooper_count", PlayerUsageSnooper.access$308(PlayerUsageSnooper.this));
                            var1.put("snooper_token", PlayerUsageSnooper.this.uniqueID);
                        }
                        HttpUtil.func_151226_a(PlayerUsageSnooper.this.serverUrl, var1, true);
                    }
                }
            }, 0L, 900000L);
        }
    }
    
    private void func_152766_h() {
        this.addJvmArgsToSnooper();
        this.func_152768_a("snooper_token", this.uniqueID);
        this.func_152767_b("snooper_token", this.uniqueID);
        this.func_152767_b("os_name", System.getProperty("os.name"));
        this.func_152767_b("os_version", System.getProperty("os.version"));
        this.func_152767_b("os_architecture", System.getProperty("os.arch"));
        this.func_152767_b("java_version", System.getProperty("java.version"));
        this.func_152767_b("version", "1.7.10");
        this.playerStatsCollector.addServerTypeToSnooper(this);
    }
    
    private void addJvmArgsToSnooper() {
        final RuntimeMXBean var1 = ManagementFactory.getRuntimeMXBean();
        final List var2 = var1.getInputArguments();
        int var3 = 0;
        for (final String var5 : var2) {
            if (var5.startsWith("-X")) {
                this.func_152768_a("jvm_arg[" + var3++ + "]", var5);
            }
        }
        this.func_152768_a("jvm_args", var3);
    }
    
    public void addMemoryStatsToSnooper() {
        this.func_152767_b("memory_total", Runtime.getRuntime().totalMemory());
        this.func_152767_b("memory_max", Runtime.getRuntime().maxMemory());
        this.func_152767_b("memory_free", Runtime.getRuntime().freeMemory());
        this.func_152767_b("cpu_cores", Runtime.getRuntime().availableProcessors());
        this.playerStatsCollector.addServerStatsToSnooper(this);
    }
    
    public void func_152768_a(final String p_152768_1_, final Object p_152768_2_) {
        final Object var3 = this.syncLock;
        synchronized (this.syncLock) {
            this.field_152774_b.put(p_152768_1_, p_152768_2_);
        }
    }
    
    public void func_152767_b(final String p_152767_1_, final Object p_152767_2_) {
        final Object var3 = this.syncLock;
        synchronized (this.syncLock) {
            this.field_152773_a.put(p_152767_1_, p_152767_2_);
        }
    }
    
    public Map getCurrentStats() {
        final LinkedHashMap var1 = new LinkedHashMap();
        final Object var2 = this.syncLock;
        synchronized (this.syncLock) {
            this.addMemoryStatsToSnooper();
            for (final Map.Entry var4 : this.field_152773_a.entrySet()) {
                var1.put(var4.getKey(), var4.getValue().toString());
            }
            for (final Map.Entry var4 : this.field_152774_b.entrySet()) {
                var1.put(var4.getKey(), var4.getValue().toString());
            }
            return var1;
        }
    }
    
    public boolean isSnooperRunning() {
        return this.isRunning;
    }
    
    public void stopSnooper() {
        this.threadTrigger.cancel();
    }
    
    public String getUniqueID() {
        return this.uniqueID;
    }
    
    public long getMinecraftStartTimeMillis() {
        return this.minecraftStartTimeMilis;
    }
    
    static int access$308(final PlayerUsageSnooper p_access$308_0_) {
        return p_access$308_0_.selfCounter++;
    }
}
