package net.minecraft.util;

import java.util.concurrent.atomic.*;
import java.util.*;
import net.minecraft.server.*;
import java.io.*;
import java.net.*;
import org.apache.logging.log4j.*;

public class HttpUtil
{
    private static final AtomicInteger downloadThreadsStarted;
    private static final Logger logger;
    private static final String __OBFID = "CL_00001485";
    
    public static String buildPostString(final Map p_76179_0_) {
        final StringBuilder var1 = new StringBuilder();
        for (final Map.Entry var3 : p_76179_0_.entrySet()) {
            if (var1.length() > 0) {
                var1.append('&');
            }
            try {
                var1.append(URLEncoder.encode(var3.getKey(), "UTF-8"));
            }
            catch (UnsupportedEncodingException var4) {
                var4.printStackTrace();
            }
            if (var3.getValue() != null) {
                var1.append('=');
                try {
                    var1.append(URLEncoder.encode(var3.getValue().toString(), "UTF-8"));
                }
                catch (UnsupportedEncodingException var5) {
                    var5.printStackTrace();
                }
            }
        }
        return var1.toString();
    }
    
    public static String func_151226_a(final URL p_151226_0_, final Map p_151226_1_, final boolean p_151226_2_) {
        return func_151225_a(p_151226_0_, buildPostString(p_151226_1_), p_151226_2_);
    }
    
    private static String func_151225_a(final URL p_151225_0_, final String p_151225_1_, final boolean p_151225_2_) {
        try {
            Proxy var3 = (MinecraftServer.getServer() == null) ? null : MinecraftServer.getServer().getServerProxy();
            if (var3 == null) {
                var3 = Proxy.NO_PROXY;
            }
            final HttpURLConnection var4 = (HttpURLConnection)p_151225_0_.openConnection(var3);
            var4.setRequestMethod("POST");
            var4.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            var4.setRequestProperty("Content-Length", "" + p_151225_1_.getBytes().length);
            var4.setRequestProperty("Content-Language", "en-US");
            var4.setUseCaches(false);
            var4.setDoInput(true);
            var4.setDoOutput(true);
            final DataOutputStream var5 = new DataOutputStream(var4.getOutputStream());
            var5.writeBytes(p_151225_1_);
            var5.flush();
            var5.close();
            final BufferedReader var6 = new BufferedReader(new InputStreamReader(var4.getInputStream()));
            final StringBuffer var7 = new StringBuffer();
            String var8;
            while ((var8 = var6.readLine()) != null) {
                var7.append(var8);
                var7.append('\r');
            }
            var6.close();
            return var7.toString();
        }
        catch (Exception var9) {
            if (!p_151225_2_) {
                HttpUtil.logger.error("Could not post to " + p_151225_0_, (Throwable)var9);
            }
            return "";
        }
    }
    
    public static void func_151223_a(final File p_151223_0_, final String p_151223_1_, final DownloadListener p_151223_2_, final Map p_151223_3_, final int p_151223_4_, final IProgressUpdate p_151223_5_, final Proxy p_151223_6_) {
        final Thread var7 = new Thread(new Runnable() {
            private static final String __OBFID = "CL_00001486";
            
            @Override
            public void run() {
                URLConnection var1 = null;
                InputStream var2 = null;
                DataOutputStream var3 = null;
                while (true) {
                    if (p_151223_5_ != null) {
                        p_151223_5_.resetProgressAndMessage("Downloading Texture Pack");
                        p_151223_5_.resetProgresAndWorkingMessage("Making Request...");
                        try {
                            final byte[] var4 = new byte[4096];
                            final URL var5 = new URL(p_151223_1_);
                            var1 = var5.openConnection(p_151223_6_);
                            float var6 = 0.0f;
                            float var7 = (float)p_151223_3_.entrySet().size();
                            for (final Map.Entry var9 : p_151223_3_.entrySet()) {
                                var1.setRequestProperty(var9.getKey(), var9.getValue());
                                if (p_151223_5_ != null) {
                                    p_151223_5_.setLoadingProgress((int)(++var6 / var7 * 100.0f));
                                }
                            }
                            var2 = var1.getInputStream();
                            var7 = (float)var1.getContentLength();
                            final int var10 = var1.getContentLength();
                            if (p_151223_5_ != null) {
                                p_151223_5_.resetProgresAndWorkingMessage(String.format("Downloading file (%.2f MB)...", var7 / 1000.0f / 1000.0f));
                            }
                            if (p_151223_0_.exists()) {
                                final long var11 = p_151223_0_.length();
                                if (var11 == var10) {
                                    p_151223_2_.func_148522_a(p_151223_0_);
                                    if (p_151223_5_ != null) {
                                        p_151223_5_.func_146586_a();
                                    }
                                    return;
                                }
                                HttpUtil.logger.warn("Deleting " + p_151223_0_ + " as it does not match what we currently have (" + var10 + " vs our " + var11 + ").");
                                p_151223_0_.delete();
                            }
                            else if (p_151223_0_.getParentFile() != null) {
                                p_151223_0_.getParentFile().mkdirs();
                            }
                            var3 = new DataOutputStream(new FileOutputStream(p_151223_0_));
                            if (p_151223_4_ > 0 && var7 > p_151223_4_) {
                                if (p_151223_5_ != null) {
                                    p_151223_5_.func_146586_a();
                                }
                                throw new IOException("Filesize is bigger than maximum allowed (file is " + var6 + ", limit is " + p_151223_4_ + ")");
                            }
                            final boolean var12 = false;
                            int var13;
                            while ((var13 = var2.read(var4)) >= 0) {
                                var6 += var13;
                                if (p_151223_5_ != null) {
                                    p_151223_5_.setLoadingProgress((int)(var6 / var7 * 100.0f));
                                }
                                if (p_151223_4_ > 0 && var6 > p_151223_4_) {
                                    if (p_151223_5_ != null) {
                                        p_151223_5_.func_146586_a();
                                    }
                                    throw new IOException("Filesize was bigger than maximum allowed (got >= " + var6 + ", limit was " + p_151223_4_ + ")");
                                }
                                var3.write(var4, 0, var13);
                            }
                            p_151223_2_.func_148522_a(p_151223_0_);
                            if (p_151223_5_ != null) {
                                p_151223_5_.func_146586_a();
                                return;
                            }
                        }
                        catch (Throwable var14) {
                            var14.printStackTrace();
                        }
                        finally {
                            try {
                                if (var2 != null) {
                                    var2.close();
                                }
                            }
                            catch (IOException ex) {}
                            try {
                                if (var3 != null) {
                                    var3.close();
                                }
                            }
                            catch (IOException ex2) {}
                        }
                        return;
                    }
                    continue;
                }
            }
        }, "File Downloader #" + HttpUtil.downloadThreadsStarted.incrementAndGet());
        var7.setDaemon(true);
        var7.start();
    }
    
    public static int func_76181_a() throws IOException {
        ServerSocket var0 = null;
        final boolean var2 = true;
        int var3;
        try {
            var0 = new ServerSocket(0);
            var3 = var0.getLocalPort();
        }
        finally {
            try {
                if (var0 != null) {
                    var0.close();
                }
            }
            catch (IOException ex) {}
        }
        return var3;
    }
    
    public static String func_152755_a(final URL p_152755_0_) throws IOException {
        final HttpURLConnection var1 = (HttpURLConnection)p_152755_0_.openConnection();
        var1.setRequestMethod("GET");
        final BufferedReader var2 = new BufferedReader(new InputStreamReader(var1.getInputStream()));
        final StringBuilder var3 = new StringBuilder();
        String var4;
        while ((var4 = var2.readLine()) != null) {
            var3.append(var4);
            var3.append('\r');
        }
        var2.close();
        return var3.toString();
    }
    
    static {
        downloadThreadsStarted = new AtomicInteger(0);
        logger = LogManager.getLogger();
    }
    
    public interface DownloadListener
    {
        void func_148522_a(final File p0);
    }
}
