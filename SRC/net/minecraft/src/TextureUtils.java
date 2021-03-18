package net.minecraft.src;

import java.nio.*;
import java.awt.image.*;
import java.awt.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.texture.*;
import java.io.*;
import javax.imageio.*;
import org.apache.commons.io.*;
import net.minecraft.client.renderer.*;

public class TextureUtils
{
    public static final String texGrassTop = "grass_top";
    public static final String texStone = "stone";
    public static final String texDirt = "dirt";
    public static final String texGrassSide = "grass_side";
    public static final String texStoneslabSide = "stone_slab_side";
    public static final String texStoneslabTop = "stone_slab_top";
    public static final String texBedrock = "bedrock";
    public static final String texSand = "sand";
    public static final String texGravel = "gravel";
    public static final String texLogOak = "log_oak";
    public static final String texLogOakTop = "log_oak_top";
    public static final String texGoldOre = "gold_ore";
    public static final String texIronOre = "iron_ore";
    public static final String texCoalOre = "coal_ore";
    public static final String texObsidian = "obsidian";
    public static final String texGrassSideOverlay = "grass_side_overlay";
    public static final String texSnow = "snow";
    public static final String texGrassSideSnowed = "grass_side_snowed";
    public static final String texMyceliumSide = "mycelium_side";
    public static final String texMyceliumTop = "mycelium_top";
    public static final String texDiamondOre = "diamond_ore";
    public static final String texRedstoneOre = "redstone_ore";
    public static final String texLapisOre = "lapis_ore";
    public static final String texLeavesOak = "leaves_oak";
    public static final String texLeavesOakOpaque = "leaves_oak_opaque";
    public static final String texLeavesJungle = "leaves_jungle";
    public static final String texLeavesJungleOpaque = "leaves_jungle_opaque";
    public static final String texCactusSide = "cactus_side";
    public static final String texClay = "clay";
    public static final String texFarmlandWet = "farmland_wet";
    public static final String texFarmlandDry = "farmland_dry";
    public static final String texNetherrack = "netherrack";
    public static final String texSoulSand = "soul_sand";
    public static final String texGlowstone = "glowstone";
    public static final String texLogSpruce = "log_spruce";
    public static final String texLogBirch = "log_birch";
    public static final String texLeavesSpruce = "leaves_spruce";
    public static final String texLeavesSpruceOpaque = "leaves_spruce_opaque";
    public static final String texLogJungle = "log_jungle";
    public static final String texEndStone = "end_stone";
    public static final String texSandstoneTop = "sandstone_top";
    public static final String texSandstoneBottom = "sandstone_bottom";
    public static final String texRedstoneLampOff = "redstone_lamp_off";
    public static final String texRedstoneLampOn = "redstone_lamp_on";
    public static final String texWaterStill = "water_still";
    public static final String texWaterFlow = "water_flow";
    public static final String texLavaStill = "lava_still";
    public static final String texLavaFlow = "lava_flow";
    public static final String texFireLayer0 = "fire_layer_0";
    public static final String texFireLayer1 = "fire_layer_1";
    public static final String texPortal = "portal";
    public static final String texGlass = "glass";
    public static final String texGlassPaneTop = "glass_pane_top";
    public static final String texCompass = "compass";
    public static final String texClock = "clock";
    public static IIcon iconGrassTop;
    public static IIcon iconGrassSide;
    public static IIcon iconGrassSideOverlay;
    public static IIcon iconSnow;
    public static IIcon iconGrassSideSnowed;
    public static IIcon iconMyceliumSide;
    public static IIcon iconMyceliumTop;
    public static IIcon iconWaterStill;
    public static IIcon iconWaterFlow;
    public static IIcon iconLavaStill;
    public static IIcon iconLavaFlow;
    public static IIcon iconPortal;
    public static IIcon iconFireLayer0;
    public static IIcon iconFireLayer1;
    public static IIcon iconGlass;
    public static IIcon iconGlassPaneTop;
    public static IIcon iconCompass;
    public static IIcon iconClock;
    private static IntBuffer staticBuffer;
    
    public static void update() {
        final TextureMap mapBlocks = TextureMap.textureMapBlocks;
        if (mapBlocks != null) {
            TextureUtils.iconGrassTop = mapBlocks.getIconSafe("grass_top");
            TextureUtils.iconGrassSide = mapBlocks.getIconSafe("grass_side");
            TextureUtils.iconGrassSideOverlay = mapBlocks.getIconSafe("grass_side_overlay");
            TextureUtils.iconSnow = mapBlocks.getIconSafe("snow");
            TextureUtils.iconGrassSideSnowed = mapBlocks.getIconSafe("grass_side_snowed");
            TextureUtils.iconMyceliumSide = mapBlocks.getIconSafe("mycelium_side");
            TextureUtils.iconMyceliumTop = mapBlocks.getIconSafe("mycelium_top");
            TextureUtils.iconWaterStill = mapBlocks.getIconSafe("water_still");
            TextureUtils.iconWaterFlow = mapBlocks.getIconSafe("water_flow");
            TextureUtils.iconLavaStill = mapBlocks.getIconSafe("lava_still");
            TextureUtils.iconLavaFlow = mapBlocks.getIconSafe("lava_flow");
            TextureUtils.iconFireLayer0 = mapBlocks.getIconSafe("fire_layer_0");
            TextureUtils.iconFireLayer1 = mapBlocks.getIconSafe("fire_layer_1");
            TextureUtils.iconPortal = mapBlocks.getIconSafe("portal");
            TextureUtils.iconGlass = mapBlocks.getIconSafe("glass");
            TextureUtils.iconGlassPaneTop = mapBlocks.getIconSafe("glass_pane_top");
            final TextureMap mapItems = TextureMap.textureMapItems;
            if (mapItems != null) {
                TextureUtils.iconCompass = mapItems.getIconSafe("compass");
                TextureUtils.iconClock = mapItems.getIconSafe("clock");
            }
        }
    }
    
    public static BufferedImage fixTextureDimensions(final String name, final BufferedImage bi) {
        if (name.startsWith("/mob/zombie") || name.startsWith("/mob/pigzombie")) {
            final int width = bi.getWidth();
            final int height = bi.getHeight();
            if (width == height * 2) {
                final BufferedImage scaledImage = new BufferedImage(width, height * 2, 2);
                final Graphics2D gr = scaledImage.createGraphics();
                gr.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                gr.drawImage(bi, 0, 0, width, height, null);
                return scaledImage;
            }
        }
        return bi;
    }
    
    public static TextureAtlasSprite getTextureAtlasSprite(final IIcon icon) {
        return (icon instanceof TextureAtlasSprite) ? ((TextureAtlasSprite)icon) : null;
    }
    
    public static int ceilPowerOfTwo(final int val) {
        int i;
        for (i = 1; i < val; i *= 2) {}
        return i;
    }
    
    public static int getPowerOfTwo(final int val) {
        int i;
        int po2;
        for (i = 1, po2 = 0; i < val; i *= 2, ++po2) {}
        return po2;
    }
    
    public static int twoToPower(final int power) {
        int val = 1;
        for (int i = 0; i < power; ++i) {
            val *= 2;
        }
        return val;
    }
    
    public static void refreshBlockTextures() {
        Config.dbg("*** Reloading block textures ***");
        WrUpdates.finishCurrentUpdate();
        TextureMap.textureMapBlocks.loadTextureSafe(Config.getResourceManager());
        update();
        NaturalTextures.update();
        TextureMap.textureMapBlocks.updateAnimations();
    }
    
    public static ITextureObject getTexture(final String path) {
        return getTexture(new ResourceLocation(path));
    }
    
    public static ITextureObject getTexture(final ResourceLocation loc) {
        final ITextureObject tex = Config.getTextureManager().getTexture(loc);
        if (tex != null) {
            return tex;
        }
        if (!Config.hasResource(loc)) {
            return null;
        }
        final SimpleTexture tex2 = new SimpleTexture(loc);
        Config.getTextureManager().loadTexture(loc, tex2);
        return tex2;
    }
    
    public static void resourcesReloaded(final IResourceManager rm) {
        if (TextureMap.textureMapBlocks != null) {
            Config.dbg("*** Reloading custom textures ***");
            CustomSky.reset();
            TextureAnimations.reset();
            WrUpdates.finishCurrentUpdate();
            update();
            NaturalTextures.update();
            TextureAnimations.update();
            CustomColorizer.update();
            CustomSky.update();
            Config.updateTexturePackClouds();
            Config.getTextureManager().tick();
        }
    }
    
    public static void refreshTextureMaps(final IResourceManager rm) {
        TextureMap.textureMapBlocks.loadTextureSafe(rm);
        TextureMap.textureMapItems.loadTextureSafe(rm);
        update();
        NaturalTextures.update();
    }
    
    public static void registerResourceListener() {
        final IResourceManager rm = Config.getResourceManager();
        if (rm instanceof IReloadableResourceManager) {
            final IReloadableResourceManager tto = (IReloadableResourceManager)rm;
            final IResourceManagerReloadListener ttol = new IResourceManagerReloadListener() {
                @Override
                public void onResourceManagerReload(final IResourceManager var1) {
                    TextureUtils.resourcesReloaded(var1);
                }
            };
            tto.registerReloadListener(ttol);
        }
        final ITickableTextureObject tto2 = new ITickableTextureObject() {
            @Override
            public void tick() {
                TextureAnimations.updateCustomAnimations();
            }
            
            @Override
            public void loadTexture(final IResourceManager var1) throws IOException {
            }
            
            @Override
            public int getGlTextureId() {
                return 0;
            }
        };
        final ResourceLocation ttol2 = new ResourceLocation("optifine/TickableTextures");
        Config.getTextureManager().loadTickableTexture(ttol2, tto2);
    }
    
    public static String fixResourcePath(String path, String basePath) {
        final String strAssMc = "assets/minecraft/";
        if (path.startsWith(strAssMc)) {
            path = path.substring(strAssMc.length());
            return path;
        }
        if (path.startsWith("./")) {
            path = path.substring(2);
            if (!basePath.endsWith("/")) {
                basePath += "/";
            }
            path = basePath + path;
            return path;
        }
        if (path.startsWith("/~")) {
            path = path.substring(1);
        }
        final String strMcpatcher = "mcpatcher/";
        if (path.startsWith("~/")) {
            path = path.substring(2);
            path = strMcpatcher + path;
            return path;
        }
        if (path.startsWith("/")) {
            path = strMcpatcher + path.substring(1);
            return path;
        }
        return path;
    }
    
    public static String getBasePath(final String path) {
        final int pos = path.lastIndexOf(47);
        return (pos < 0) ? "" : path.substring(0, pos);
    }
    
    public static BufferedImage readBufferedImage(final InputStream is) throws IOException {
        BufferedImage var1;
        try {
            var1 = ImageIO.read(is);
        }
        finally {
            IOUtils.closeQuietly(is);
        }
        return var1;
    }
    
    static {
        TextureUtils.staticBuffer = GLAllocation.createDirectIntBuffer(256);
    }
}
