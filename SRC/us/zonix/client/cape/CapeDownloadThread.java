package us.zonix.client.cape;

import java.util.*;
import java.beans.*;
import net.minecraft.client.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;

public class CapeDownloadThread implements Runnable
{
    private final UUID uniqueId;
    private final CapeCallback callback;
    
    @ConstructorProperties({ "uniqueId", "callback" })
    public CapeDownloadThread(final UUID uniqueId, final CapeCallback callback) {
        this.uniqueId = uniqueId;
        this.callback = callback;
    }
    
    @Override
    public void run() {
        final File dir = new File(Minecraft.getMinecraft().mcDataDir, "capes");
        dir.mkdirs();
        final String cape = "kitten";
        final File capeFile = new File(dir, cape + ".png");
        if (!capeFile.exists()) {
            try {
                final BufferedImage image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("assets/minecraft/capes/" + cape + ".png"));
                ImageIO.write(image, "png", capeFile);
                image.flush();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            this.callback.callback(cape);
            return;
        }
        this.callback.callback(cape);
    }
    
    public interface CapeCallback
    {
        void callback(final String p0);
    }
}
