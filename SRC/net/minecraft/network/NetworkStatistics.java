package net.minecraft.network;

import org.apache.logging.log4j.*;
import java.util.concurrent.atomic.*;

public class NetworkStatistics
{
    private static final Logger field_152478_a;
    private static final Marker field_152479_b;
    private Tracker field_152480_c;
    private Tracker field_152481_d;
    private static final String __OBFID = "CL_00001897";
    
    public NetworkStatistics() {
        this.field_152480_c = new Tracker();
        this.field_152481_d = new Tracker();
    }
    
    public void func_152469_a(final int p_152469_1_, final long p_152469_2_) {
        this.field_152480_c.func_152488_a(p_152469_1_, p_152469_2_);
    }
    
    public void func_152464_b(final int p_152464_1_, final long p_152464_2_) {
        this.field_152481_d.func_152488_a(p_152464_1_, p_152464_2_);
    }
    
    public long func_152465_a() {
        return this.field_152480_c.func_152485_a();
    }
    
    public long func_152471_b() {
        return this.field_152481_d.func_152485_a();
    }
    
    public long func_152472_c() {
        return this.field_152480_c.func_152489_b();
    }
    
    public long func_152473_d() {
        return this.field_152481_d.func_152489_b();
    }
    
    public PacketStat func_152477_e() {
        return this.field_152480_c.func_152484_c();
    }
    
    public PacketStat func_152467_f() {
        return this.field_152480_c.func_152486_d();
    }
    
    public PacketStat func_152475_g() {
        return this.field_152481_d.func_152484_c();
    }
    
    public PacketStat func_152470_h() {
        return this.field_152481_d.func_152486_d();
    }
    
    public PacketStat func_152466_a(final int p_152466_1_) {
        return this.field_152480_c.func_152487_a(p_152466_1_);
    }
    
    public PacketStat func_152468_b(final int p_152468_1_) {
        return this.field_152481_d.func_152487_a(p_152468_1_);
    }
    
    static {
        field_152478_a = LogManager.getLogger();
        field_152479_b = MarkerManager.getMarker("NETSTAT_MARKER", NetworkManager.field_152461_c);
    }
    
    public static class PacketStat
    {
        private final int field_152482_a;
        private final PacketStatData field_152483_b;
        private static final String __OBFID = "CL_00001895";
        
        public PacketStat(final int p_i1188_1_, final PacketStatData p_i1188_2_) {
            this.field_152482_a = p_i1188_1_;
            this.field_152483_b = p_i1188_2_;
        }
        
        @Override
        public String toString() {
            return "PacketStat(" + this.field_152482_a + ")" + this.field_152483_b;
        }
    }
    
    static class PacketStatData
    {
        private final long field_152496_a;
        private final int field_152497_b;
        private final double field_152498_c;
        private static final String __OBFID = "CL_00001893";
        
        private PacketStatData(final long p_i46399_1_, final int p_i46399_3_, final double p_i46399_4_) {
            this.field_152496_a = p_i46399_1_;
            this.field_152497_b = p_i46399_3_;
            this.field_152498_c = p_i46399_4_;
        }
        
        public PacketStatData func_152494_a(final long p_152494_1_) {
            return new PacketStatData(p_152494_1_ + this.field_152496_a, this.field_152497_b + 1, (double)((p_152494_1_ + this.field_152496_a) / (this.field_152497_b + 1)));
        }
        
        public long func_152493_a() {
            return this.field_152496_a;
        }
        
        public int func_152495_b() {
            return this.field_152497_b;
        }
        
        @Override
        public String toString() {
            return "{totalBytes=" + this.field_152496_a + ", count=" + this.field_152497_b + ", averageBytes=" + this.field_152498_c + '}';
        }
        
        PacketStatData(final long p_i1185_1_, final int p_i1185_3_, final double p_i1185_4_, final Object p_i1185_6_) {
            this(p_i1185_1_, p_i1185_3_, p_i1185_4_);
        }
    }
    
    static class Tracker
    {
        private AtomicReference[] field_152490_a;
        private static final String __OBFID = "CL_00001894";
        
        public Tracker() {
            this.field_152490_a = new AtomicReference[100];
            for (int var1 = 0; var1 < 100; ++var1) {
                this.field_152490_a[var1] = new AtomicReference((V)new PacketStatData(0L, 0, 0.0, null));
            }
        }
        
        public void func_152488_a(final int p_152488_1_, final long p_152488_2_) {
            try {
                if (p_152488_1_ < 0 || p_152488_1_ >= 100) {
                    return;
                }
                PacketStatData var4;
                PacketStatData var5;
                do {
                    var4 = this.field_152490_a[p_152488_1_].get();
                    var5 = var4.func_152494_a(p_152488_2_);
                } while (!this.field_152490_a[p_152488_1_].compareAndSet(var4, var5));
            }
            catch (Exception var6) {
                if (NetworkStatistics.field_152478_a.isDebugEnabled()) {
                    NetworkStatistics.field_152478_a.debug(NetworkStatistics.field_152479_b, "NetStat failed with packetId: " + p_152488_1_, (Throwable)var6);
                }
            }
        }
        
        public long func_152485_a() {
            long var1 = 0L;
            for (int var2 = 0; var2 < 100; ++var2) {
                var1 += this.field_152490_a[var2].get().func_152493_a();
            }
            return var1;
        }
        
        public long func_152489_b() {
            long var1 = 0L;
            for (int var2 = 0; var2 < 100; ++var2) {
                var1 += this.field_152490_a[var2].get().func_152495_b();
            }
            return var1;
        }
        
        public PacketStat func_152484_c() {
            int var1 = -1;
            PacketStatData var2 = new PacketStatData(-1L, -1, 0.0, null);
            for (int var3 = 0; var3 < 100; ++var3) {
                final PacketStatData var4 = this.field_152490_a[var3].get();
                if (var4.field_152496_a > var2.field_152496_a) {
                    var1 = var3;
                    var2 = var4;
                }
            }
            return new PacketStat(var1, var2);
        }
        
        public PacketStat func_152486_d() {
            int var1 = -1;
            PacketStatData var2 = new PacketStatData(-1L, -1, 0.0, null);
            for (int var3 = 0; var3 < 100; ++var3) {
                final PacketStatData var4 = this.field_152490_a[var3].get();
                if (var4.field_152497_b > var2.field_152497_b) {
                    var1 = var3;
                    var2 = var4;
                }
            }
            return new PacketStat(var1, var2);
        }
        
        public PacketStat func_152487_a(final int p_152487_1_) {
            return (p_152487_1_ >= 0 && p_152487_1_ < 100) ? new PacketStat(p_152487_1_, this.field_152490_a[p_152487_1_].get()) : null;
        }
    }
}
