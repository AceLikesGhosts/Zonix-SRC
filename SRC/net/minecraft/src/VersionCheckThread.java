package net.minecraft.src;

import java.net.*;
import net.minecraft.client.*;
import java.io.*;

public class VersionCheckThread extends Thread
{
    @Override
    public void run() {
        HttpURLConnection conn = null;
        try {
            Config.dbg("Checking for new version");
            final URL e = new URL("http://optifine.net/version/1.7.10/HD_U.txt");
            conn = (HttpURLConnection)e.openConnection();
            if (Config.getGameSettings().snooperEnabled) {
                conn.setRequestProperty("OF-MC-Version", "1.7.10");
                conn.setRequestProperty("OF-MC-Brand", "" + ClientBrandRetriever.getClientModName());
                conn.setRequestProperty("OF-Edition", "HD_U");
                conn.setRequestProperty("OF-Release", "D6");
                conn.setRequestProperty("OF-Java-Version", "" + System.getProperty("java.version"));
                conn.setRequestProperty("OF-CpuCount", "" + Config.getAvailableProcessors());
                conn.setRequestProperty("OF-OpenGL-Version", "" + Config.openGlVersion);
                conn.setRequestProperty("OF-OpenGL-Vendor", "" + Config.openGlVendor);
            }
            conn.setDoInput(true);
            conn.setDoOutput(false);
            conn.connect();
            try {
                final InputStream in = conn.getInputStream();
                final String verStr = Config.readInputStream(in);
                in.close();
                final String[] verLines = Config.tokenize(verStr, "\n\r");
                if (verLines.length < 1) {
                    return;
                }
                final String newVer = verLines[0].trim();
                Config.dbg("Version found: " + newVer);
                if (Config.compareRelease(newVer, "D6") > 0) {
                    Config.setNewRelease(newVer);
                }
            }
            finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
        catch (Exception var11) {
            Config.dbg(var11.getClass().getName() + ": " + var11.getMessage());
        }
    }
}
