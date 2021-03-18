package net.minecraft.client.main;

import java.io.*;
import net.minecraft.client.*;
import com.google.gson.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import joptsimple.*;
import java.net.*;
import java.lang.reflect.*;
import java.util.*;

public class Main
{
    private static final Type field_152370_a;
    private static final String __OBFID = "CL_00001461";
    
    public static void main(final String[] p_main_0_) {
        System.setProperty("java.net.preferIPv4Stack", "true");
        final OptionParser var1 = new OptionParser();
        var1.allowsUnrecognizedOptions();
        var1.accepts("demo");
        var1.accepts("fullscreen");
        final ArgumentAcceptingOptionSpec var2 = var1.accepts("server").withRequiredArg();
        final ArgumentAcceptingOptionSpec var3 = var1.accepts("port").withRequiredArg().ofType((Class)Integer.class).defaultsTo((Object)25565, (Object[])new Integer[0]);
        final ArgumentAcceptingOptionSpec var4 = var1.accepts("gameDir").withRequiredArg().ofType((Class)File.class).defaultsTo((Object)new File("."), (Object[])new File[0]);
        final ArgumentAcceptingOptionSpec var5 = var1.accepts("assetsDir").withRequiredArg().ofType((Class)File.class);
        final ArgumentAcceptingOptionSpec var6 = var1.accepts("resourcePackDir").withRequiredArg().ofType((Class)File.class);
        final ArgumentAcceptingOptionSpec var7 = var1.accepts("proxyHost").withRequiredArg();
        final ArgumentAcceptingOptionSpec var8 = var1.accepts("proxyPort").withRequiredArg().defaultsTo((Object)"8080", (Object[])new String[0]).ofType((Class)Integer.class);
        final ArgumentAcceptingOptionSpec var9 = var1.accepts("proxyUser").withRequiredArg();
        final ArgumentAcceptingOptionSpec var10 = var1.accepts("proxyPass").withRequiredArg();
        final ArgumentAcceptingOptionSpec var11 = var1.accepts("username").withRequiredArg().defaultsTo((Object)("Player" + Minecraft.getSystemTime() % 1000L), (Object[])new String[0]);
        final ArgumentAcceptingOptionSpec var12 = var1.accepts("uuid").withRequiredArg();
        final ArgumentAcceptingOptionSpec var13 = var1.accepts("accessToken").withRequiredArg().required();
        final ArgumentAcceptingOptionSpec var14 = var1.accepts("version").withRequiredArg().required();
        final ArgumentAcceptingOptionSpec var15 = var1.accepts("width").withRequiredArg().ofType((Class)Integer.class).defaultsTo((Object)1280, (Object[])new Integer[0]);
        final ArgumentAcceptingOptionSpec var16 = var1.accepts("height").withRequiredArg().ofType((Class)Integer.class).defaultsTo((Object)720, (Object[])new Integer[0]);
        final ArgumentAcceptingOptionSpec var17 = var1.accepts("userProperties").withRequiredArg().required();
        final ArgumentAcceptingOptionSpec var18 = var1.accepts("assetIndex").withRequiredArg();
        final ArgumentAcceptingOptionSpec var19 = var1.accepts("userType").withRequiredArg().defaultsTo((Object)"legacy", (Object[])new String[0]);
        final NonOptionArgumentSpec var20 = var1.nonOptions();
        final OptionSet var21 = var1.parse(p_main_0_);
        final List var22 = var21.valuesOf((OptionSpec)var20);
        final String var23 = (String)var21.valueOf((OptionSpec)var7);
        Proxy var24 = Proxy.NO_PROXY;
        if (var23 != null) {
            try {
                var24 = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(var23, (int)var21.valueOf((OptionSpec)var8)));
            }
            catch (Exception ex) {}
        }
        final String var25 = (String)var21.valueOf((OptionSpec)var9);
        final String var26 = (String)var21.valueOf((OptionSpec)var10);
        if (!var24.equals(Proxy.NO_PROXY) && func_110121_a(var25) && func_110121_a(var26)) {
            Authenticator.setDefault(new MCAuthenticator(var25, var26));
        }
        final int var27 = (int)var21.valueOf((OptionSpec)var15);
        final int var28 = (int)var21.valueOf((OptionSpec)var16);
        final boolean var29 = var21.has("fullscreen");
        final boolean var30 = var21.has("demo");
        final String var31 = (String)var21.valueOf((OptionSpec)var14);
        final HashMultimap var32 = HashMultimap.create();
        for (final Map.Entry var34 : ((Map)new Gson().fromJson((String)var21.valueOf((OptionSpec)var17), Main.field_152370_a)).entrySet()) {
            var32.putAll(var34.getKey(), (Iterable)var34.getValue());
        }
        final File var35 = (File)var21.valueOf((OptionSpec)var4);
        final File var36 = (File)(var21.has((OptionSpec)var5) ? var21.valueOf((OptionSpec)var5) : new File(var35, "assets/"));
        final File var37 = (File)(var21.has((OptionSpec)var6) ? var21.valueOf((OptionSpec)var6) : new File(var35, "resourcepacks/"));
        final String var38 = (String)(var21.has((OptionSpec)var12) ? var12.value(var21) : ((String)var11.value(var21)));
        final String var39 = var21.has((OptionSpec)var18) ? ((String)var18.value(var21)) : null;
        final Session var40 = new Session((String)var11.value(var21), var38, (String)var13.value(var21), (String)var19.value(var21));
        final Minecraft var41 = new Minecraft(var40, var27, var28, var29, var30, var35, var36, var37, var24, var31, (Multimap)var32, var39);
        final String var42 = (String)var21.valueOf((OptionSpec)var2);
        if (var42 != null) {
            var41.setServer(var42, (int)var21.valueOf((OptionSpec)var3));
        }
        Runtime.getRuntime().addShutdownHook(new MCSShutdownHook("Client Shutdown Thread"));
        if (!var22.isEmpty()) {
            System.out.println("Completely ignored arguments: " + var22);
        }
        Thread.currentThread().setName("Client thread");
        var41.run();
    }
    
    private static boolean func_110121_a(final String p_110121_0_) {
        return p_110121_0_ != null && !p_110121_0_.isEmpty();
    }
    
    static {
        field_152370_a = new MCType();
    }
    
    public static class MCAuthenticator extends Authenticator
    {
        private static final String __OBFID = "CL_00000829";
        private final String var25;
        private final String var26;
        
        public MCAuthenticator(final String var25, final String var26) {
            this.var25 = var25;
            this.var26 = var26;
        }
        
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(this.var25, this.var26.toCharArray());
        }
    }
    
    public static class MCSShutdownHook extends Thread
    {
        private static final String __OBFID = "CL_00001835";
        
        public MCSShutdownHook(final String msg) {
            super(msg);
        }
        
        @Override
        public void run() {
            Minecraft.stopIntegratedServer();
        }
    }
    
    public static class MCType implements ParameterizedType
    {
        private static final String __OBFID = "CL_00000828";
        
        @Override
        public Type[] getActualTypeArguments() {
            return new Type[] { String.class, new ParameterizedType() {
                    private static final String __OBFID = "CL_00001836";
                    
                    @Override
                    public Type[] getActualTypeArguments() {
                        return new Type[] { String.class };
                    }
                    
                    @Override
                    public Type getRawType() {
                        return Collection.class;
                    }
                    
                    @Override
                    public Type getOwnerType() {
                        return null;
                    }
                } };
        }
        
        @Override
        public Type getRawType() {
            return Map.class;
        }
        
        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}
