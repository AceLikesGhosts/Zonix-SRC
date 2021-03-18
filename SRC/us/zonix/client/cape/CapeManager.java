package us.zonix.client.cape;

import java.util.*;
import net.minecraft.client.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.texture.*;
import com.mojang.authlib.minecraft.*;
import java.awt.image.*;
import java.awt.*;
import java.lang.ref.*;

public class CapeManager
{
    private final Map<UUID, String> capeCache;
    
    public CapeManager() {
        this.capeCache = new HashMap<UUID, String>();
    }
    
    private void giveCape(final UUID uuid, final String cape) {
        final EntityPlayer player = Minecraft.getMinecraft().theWorld.getPlayerEntityByUUID(uuid);
        if (!(player instanceof AbstractClientPlayer)) {
            return;
        }
        final AbstractClientPlayer clientPlayer = (AbstractClientPlayer)player;
        final ResourceLocation resourceLocation = new ResourceLocation("capes/" + cape);
        final TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
        final CapeImageBuffer buffer = new CapeImageBuffer(clientPlayer, resourceLocation);
        final ThreadDownloadImageData textureCape = new ThreadDownloadImageData(null, "https://zonix.us/api/client/cape/download/" + uuid.toString(), null, buffer);
        textureManager.loadTexture(resourceLocation, textureCape);
    }
    
    private void setCape(final AbstractClientPlayer player, final ResourceLocation location) {
        if (location == null) {
            return;
        }
        player.func_152121_a(MinecraftProfileTexture.Type.CAPE, location);
    }
    
    public void onSpawn(final UUID uuid) {
        final String cape = this.capeCache.get(uuid);
        if (cape == null) {
            new Thread(new CapeDownloadThread(uuid, new CapeDownloadThread.CapeCallback() {
                @Override
                public void callback(final String name) {
                    CapeManager.this.capeCache.put(uuid, name);
                    CapeManager.this.giveCape(uuid, name);
                }
            })).start();
            return;
        }
        this.giveCape(uuid, cape);
    }
    
    public void onDestroy(final UUID uuid) {
        this.capeCache.remove(uuid);
    }
    
    private static BufferedImage parseCape(final BufferedImage img) {
        int imageWidth = 64;
        int imageHeight = 32;
        for (int srcWidth = img.getWidth(), srcHeight = img.getHeight(); imageWidth < srcWidth || imageHeight < srcHeight; imageWidth *= 2, imageHeight *= 2) {}
        final BufferedImage image = new BufferedImage(imageWidth, imageHeight, 2);
        final Graphics graphics = image.getGraphics();
        graphics.drawImage(img, 0, 0, null);
        graphics.dispose();
        return image;
    }
    
    private class CapeImageBuffer implements IImageBuffer
    {
        private final WeakReference<AbstractClientPlayer> playerWeakReference;
        private final ResourceLocation location;
        
        CapeImageBuffer(final AbstractClientPlayer player, final ResourceLocation location) {
            this.playerWeakReference = new WeakReference<AbstractClientPlayer>(player);
            this.location = location;
        }
        
        @Override
        public BufferedImage parseUserSkin(final BufferedImage bufferedImage) {
            return parseCape(bufferedImage);
        }
        
        @Override
        public void func_152634_a() {
            CapeManager.this.setCape(this.playerWeakReference.get(), this.location);
        }
    }
}
