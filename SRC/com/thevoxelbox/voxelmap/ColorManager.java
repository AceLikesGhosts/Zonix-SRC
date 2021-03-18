package com.thevoxelbox.voxelmap;

import net.minecraft.client.*;
import com.thevoxelbox.voxelmap.interfaces.*;
import net.minecraft.client.settings.*;
import java.lang.reflect.*;
import net.minecraft.block.*;
import javax.imageio.*;
import java.awt.image.*;
import java.awt.*;
import com.thevoxelbox.voxelmap.util.*;
import net.minecraft.client.renderer.texture.*;
import com.thevoxelbox.minecraft.src.*;
import java.io.*;
import net.minecraft.world.biome.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import java.util.regex.*;
import net.minecraft.client.resources.*;
import java.util.zip.*;
import java.util.*;

public class ColorManager implements IColorManager
{
    private static int COLOR_NOT_LOADED;
    private final Object tpLoadLock;
    Minecraft game;
    private IVoxelMap master;
    private MapSettingsManager options;
    private List packs;
    private BufferedImage terrainBuff;
    private BufferedImage colorPicker;
    private int mapImageInt;
    private int[] blockColors;
    private int[] blockColorsWithDefaultTint;
    private Set<Integer> biomeTintsAvailable;
    private boolean hdInstalled;
    private boolean optifineInstalled;
    private HashMap<String, Integer[]> blockTintTables;
    private Set<Integer> biomeTextureAvailable;
    private HashMap<String, Integer> blockBiomeSpecificColors;
    private String renderPassThreeBlendMode;
    
    public ColorManager(final IVoxelMap master) {
        this.tpLoadLock = new Object();
        this.game = null;
        this.options = null;
        this.packs = null;
        this.terrainBuff = null;
        this.mapImageInt = -1;
        this.blockColors = new int[86016];
        this.blockColorsWithDefaultTint = new int[86016];
        this.biomeTintsAvailable = new HashSet<Integer>();
        this.hdInstalled = false;
        this.optifineInstalled = false;
        this.blockTintTables = new HashMap<String, Integer[]>();
        this.biomeTextureAvailable = new HashSet<Integer>();
        this.blockBiomeSpecificColors = new HashMap<String, Integer>();
        this.master = master;
        this.options = master.getMapOptions();
        this.game = Minecraft.getMinecraft();
        this.optifineInstalled = false;
        Field ofProfiler = null;
        try {
            ofProfiler = GameSettings.class.getDeclaredField("ofProfiler");
        }
        catch (SecurityException ex) {}
        catch (NoSuchFieldException ex2) {}
        finally {
            if (ofProfiler != null) {
                this.optifineInstalled = true;
            }
        }
        this.hdInstalled = ReflectionUtils.classExists("com.prupe.mcpatcher.ctm.CTMUtils");
    }
    
    private static void findResourcesDirectory(final File base, final String namespace, final String directory, final String suffix, final boolean recursive, final boolean directories, final Collection<ResourceLocation> resources) {
        final File subdirectory = new File(base, directory);
        final String[] list = subdirectory.list();
        if (list != null) {
            final String pathComponent = directory + "/";
            for (final String s : list) {
                final File entry = new File(subdirectory, s);
                final String resourceName = pathComponent + s;
                if (entry.isDirectory()) {
                    if (directories && s.endsWith(suffix)) {
                        resources.add(new ResourceLocation(namespace, resourceName));
                    }
                    if (recursive) {
                        findResourcesDirectory(base, namespace, pathComponent + s, suffix, recursive, directories, resources);
                    }
                }
                else if (s.endsWith(suffix) && !directories) {
                    resources.add(new ResourceLocation(namespace, resourceName));
                }
            }
        }
    }
    
    @Override
    public int getMapImageInt() {
        return this.mapImageInt;
    }
    
    @Override
    public int getAirColor() {
        return this.blockColors[BlockIDRepository.airID];
    }
    
    @Override
    public Set<Integer> getBiomeTintsAvailable() {
        return this.biomeTintsAvailable;
    }
    
    @Override
    public boolean isOptifineInstalled() {
        return this.optifineInstalled;
    }
    
    @Override
    public HashMap<String, Integer[]> getBlockTintTables() {
        return this.blockTintTables;
    }
    
    @Override
    public BufferedImage getColorPicker() {
        return this.colorPicker;
    }
    
    @Override
    public boolean checkForChanges() {
        if (this.packs == null || !this.packs.equals(this.game.gameSettings.resourcePacks)) {
            this.loadColors();
            return true;
        }
        return false;
    }
    
    public void loadColors() {
        this.packs = new ArrayList(this.game.gameSettings.resourcePacks);
        BlockIDRepository.getIDs();
        this.loadColorPicker();
        this.loadMapImage();
        this.loadTexturePackTerrainImage();
        synchronized (this.tpLoadLock) {
            try {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Arrays.fill(ColorManager.this.blockColors, ColorManager.COLOR_NOT_LOADED);
                        Arrays.fill(ColorManager.this.blockColorsWithDefaultTint, ColorManager.COLOR_NOT_LOADED);
                        ColorManager.this.loadSpecialColors();
                        ColorManager.this.biomeTintsAvailable.clear();
                        ColorManager.this.biomeTextureAvailable.clear();
                        ColorManager.this.blockBiomeSpecificColors.clear();
                        Label_0130: {
                            if (!ColorManager.this.hdInstalled) {
                                if (!ColorManager.this.optifineInstalled) {
                                    break Label_0130;
                                }
                            }
                            try {
                                ColorManager.this.processCTM();
                            }
                            catch (Exception e) {
                                System.err.println("error loading CTM " + e.getLocalizedMessage());
                                e.printStackTrace();
                            }
                            try {
                                ColorManager.this.loadBiomeColors(ColorManager.this.options.biomes);
                            }
                            catch (Exception e) {
                                System.err.println("error setting default biome shading " + e.getLocalizedMessage());
                            }
                        }
                        if (ColorManager.this.hdInstalled || ColorManager.this.optifineInstalled) {
                            ColorManager.this.blockTintTables.clear();
                            try {
                                ColorManager.this.processColorProperties();
                                if (ColorManager.this.optifineInstalled) {
                                    ColorManager.this.processColorProperty(new ResourceLocation("mcpatcher/colormap/water.png"), "" + BlockIDRepository.flowingWaterID + " " + BlockIDRepository.waterID);
                                    ColorManager.this.processColorProperty(new ResourceLocation("mcpatcher/colormap/watercolor.png"), "" + BlockIDRepository.flowingWaterID + " " + BlockIDRepository.waterID);
                                    ColorManager.this.processColorProperty(new ResourceLocation("mcpatcher/colormap/watercolorX.png"), "" + BlockIDRepository.flowingWaterID + " " + BlockIDRepository.waterID);
                                    ColorManager.this.processColorProperty(new ResourceLocation("mcpatcher/colormap/swampgrass.png"), "" + BlockIDRepository.grassID + " " + BlockIDRepository.tallGrassID + ":1,2 " + BlockIDRepository.tallFlowerID + ":2,3");
                                    ColorManager.this.processColorProperty(new ResourceLocation("mcpatcher/colormap/swampgrasscolor.png"), "" + BlockIDRepository.grassID + " " + BlockIDRepository.tallGrassID + ":1,2 " + BlockIDRepository.tallFlowerID + ":2,3");
                                    ColorManager.this.processColorProperty(new ResourceLocation("mcpatcher/colormap/swampfoliage.png"), "" + BlockIDRepository.leavesID + ":0,4,8,12 " + BlockIDRepository.vineID);
                                    ColorManager.this.processColorProperty(new ResourceLocation("mcpatcher/colormap/swampfoliagecolor.png"), "" + BlockIDRepository.leavesID + ":0,4,8,12 " + BlockIDRepository.vineID);
                                    ColorManager.this.processColorProperty(new ResourceLocation("mcpatcher/colormap/pine.png"), "" + BlockIDRepository.leavesID + ":1,5,9,13");
                                    ColorManager.this.processColorProperty(new ResourceLocation("mcpatcher/colormap/pinecolor.png"), "" + BlockIDRepository.leavesID + ":1,5,9,13");
                                    ColorManager.this.processColorProperty(new ResourceLocation("mcpatcher/colormap/birch.png"), "" + BlockIDRepository.leavesID + ":2,6,10,14");
                                    ColorManager.this.processColorProperty(new ResourceLocation("mcpatcher/colormap/birchcolor.png"), "" + BlockIDRepository.leavesID + ":2,6,10,14");
                                }
                            }
                            catch (Exception e) {
                                System.err.println("error loading custom color properties " + e.getLocalizedMessage());
                                e.printStackTrace();
                            }
                        }
                        ColorManager.this.master.getMap().forceFullRender(true);
                    }
                }, "Voxelmap Load Resourcepack Thread").start();
            }
            catch (Exception e) {
                System.err.println("error loading pack");
                e.printStackTrace();
            }
        }
        if (this.master.getRadar() != null) {
            this.master.getRadar().loadTexturePackIcons();
        }
    }
    
    @Override
    public final BufferedImage getBlockImage(final int blockID, final int metadata) {
        try {
            final IIcon icon = ((Block)Block.blockRegistry.getObjectForID(blockID)).getIcon(3, metadata);
            final int left = (int)(icon.getMinU() * this.terrainBuff.getWidth());
            final int right = (int)(icon.getMaxU() * this.terrainBuff.getWidth());
            final int top = (int)(icon.getMinV() * this.terrainBuff.getHeight());
            final int bottom = (int)(icon.getMaxV() * this.terrainBuff.getHeight());
            return this.terrainBuff.getSubimage(left, top, right - left, bottom - top);
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    private void loadColorPicker() {
        try {
            final InputStream is = this.game.getResourceManager().getResource(new ResourceLocation("voxelmap/images/colorPicker.png")).getInputStream();
            final Image picker = ImageIO.read(is);
            is.close();
            this.colorPicker = new BufferedImage(picker.getWidth(null), picker.getHeight(null), 2);
            final Graphics gfx = this.colorPicker.createGraphics();
            gfx.drawImage(picker, 0, 0, null);
            gfx.dispose();
        }
        catch (Exception e) {
            System.err.println("Error loading color picker: " + e.getLocalizedMessage());
        }
    }
    
    private void loadMapImage() {
        if (this.mapImageInt != -1) {
            GLUtils.glah(this.mapImageInt);
        }
        try {
            final InputStream is = this.game.getResourceManager().getResource(new ResourceLocation("voxelmap/images/squaremap.png")).getInputStream();
            final Image tpMap = ImageIO.read(is);
            final BufferedImage mapImage = new BufferedImage(tpMap.getWidth(null), tpMap.getHeight(null), 2);
            final Graphics2D gfx = mapImage.createGraphics();
            gfx.drawImage(tpMap, 0, 0, null);
            this.mapImageInt = GLUtils.tex(mapImage);
        }
        catch (Exception e) {
            try {
                final InputStream is2 = this.game.getResourceManager().getResource(new ResourceLocation("textures/map/map_background.png")).getInputStream();
                final Image tpMap2 = ImageIO.read(is2);
                is2.close();
                final BufferedImage mapImage2 = new BufferedImage(tpMap2.getWidth(null), tpMap2.getHeight(null), 2);
                final Graphics2D gfx2 = mapImage2.createGraphics();
                if (!GLUtils.fboEnabled && !GLUtils.hasAlphaBits) {
                    gfx2.setColor(Color.DARK_GRAY);
                    gfx2.fillRect(0, 0, mapImage2.getWidth(), mapImage2.getHeight());
                }
                gfx2.drawImage(tpMap2, 0, 0, null);
                final int border = mapImage2.getWidth() * 8 / 128;
                gfx2.setComposite(AlphaComposite.Clear);
                gfx2.fillRect(border, border, mapImage2.getWidth() - border * 2, mapImage2.getHeight() - border * 2);
                gfx2.dispose();
                this.mapImageInt = GLUtils.tex(mapImage2);
            }
            catch (Exception f) {
                System.err.println("Error loading texture pack's map image: " + f.getLocalizedMessage());
            }
        }
    }
    
    @Override
    public void setSkyColor(final int skyColor) {
        for (int t = 0; t < 16; ++t) {
            this.blockColors[this.blockColorID(BlockIDRepository.airID, t)] = skyColor;
        }
    }
    
    private void loadTexturePackTerrainImage() {
        try {
            final TextureManager textureManager = this.game.getTextureManager();
            textureManager.bindTexture(textureManager.getResourceLocation(0));
            final BufferedImage terrainStitched = ImageUtils.createBufferedImageFromCurrentGLImage();
            this.terrainBuff = new BufferedImage(terrainStitched.getWidth(null), terrainStitched.getHeight(null), 6);
            final Graphics gfx = this.terrainBuff.createGraphics();
            gfx.drawImage(terrainStitched, 0, 0, null);
            gfx.dispose();
        }
        catch (Exception e) {
            System.err.println("Error processing new resource pack: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
    
    private void loadSpecialColors() {
        this.blockColors[this.blockColorID(BlockIDRepository.tallGrassID, 0)] = this.colorMultiplier(this.getColor(BlockIDRepository.tallGrassID, 0), -1);
        for (int t = 0; t < 16; ++t) {
            this.blockColors[this.blockColorID(BlockIDRepository.cobwebID, t)] = this.getColor(BlockIDRepository.cobwebID, t, false);
        }
        VoxelMapProtectedFieldsHelper.setLightOpacity(Block.getBlockFromName("minecraft:flowing_lava"), 1);
        VoxelMapProtectedFieldsHelper.setLightOpacity(Block.getBlockFromName("minecraft:lava"), 1);
    }
    
    private void loadBiomeColors(final boolean biomes) {
        this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.grassID, 0)] = this.colorMultiplier(this.getBlockColor(BlockIDRepository.grassID, 0), ColorizerGrass.getGrassColor(0.7, 0.8) | 0xFF000000);
        this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.leavesID, 0)] = this.colorMultiplier(this.getBlockColor(BlockIDRepository.leavesID, 0), ColorizerFoliage.getFoliageColorBasic() | 0xFF000000);
        this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.leavesID, 1)] = this.colorMultiplier(this.getBlockColor(BlockIDRepository.leavesID, 1), ColorizerFoliage.getFoliageColorPine() | 0xFF000000);
        this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.leavesID, 2)] = this.colorMultiplier(this.getBlockColor(BlockIDRepository.leavesID, 2), ColorizerFoliage.getFoliageColorBirch() | 0xFF000000);
        this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.leavesID, 3)] = this.colorMultiplier(this.getBlockColor(BlockIDRepository.leavesID, 3), ColorizerFoliage.getFoliageColorBasic() | 0xFF000000);
        this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.leavesID, 4)] = this.colorMultiplier(this.getBlockColor(BlockIDRepository.leavesID, 4), ColorizerFoliage.getFoliageColorBasic() | 0xFF000000);
        this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.leavesID, 5)] = this.colorMultiplier(this.getBlockColor(BlockIDRepository.leavesID, 5), ColorizerFoliage.getFoliageColorPine() | 0xFF000000);
        this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.leavesID, 6)] = this.colorMultiplier(this.getBlockColor(BlockIDRepository.leavesID, 6), ColorizerFoliage.getFoliageColorBirch() | 0xFF000000);
        this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.leavesID, 7)] = this.colorMultiplier(this.getBlockColor(BlockIDRepository.leavesID, 7), ColorizerFoliage.getFoliageColorBasic() | 0xFF000000);
        this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.leavesID, 8)] = this.colorMultiplier(this.getBlockColor(BlockIDRepository.leavesID, 8), ColorizerFoliage.getFoliageColorBasic() | 0xFF000000);
        this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.leavesID, 9)] = this.colorMultiplier(this.getBlockColor(BlockIDRepository.leavesID, 9), ColorizerFoliage.getFoliageColorPine() | 0xFF000000);
        this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.leavesID, 10)] = this.colorMultiplier(this.getBlockColor(BlockIDRepository.leavesID, 10), ColorizerFoliage.getFoliageColorBirch() | 0xFF000000);
        this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.leavesID, 11)] = this.colorMultiplier(this.getBlockColor(BlockIDRepository.leavesID, 11), ColorizerFoliage.getFoliageColorBasic() | 0xFF000000);
        this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.leavesID, 12)] = this.colorMultiplier(this.getBlockColor(BlockIDRepository.leavesID, 12), ColorizerFoliage.getFoliageColorBasic() | 0xFF000000);
        this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.leavesID, 13)] = this.colorMultiplier(this.getBlockColor(BlockIDRepository.leavesID, 13), ColorizerFoliage.getFoliageColorPine() | 0xFF000000);
        this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.leavesID, 14)] = this.colorMultiplier(this.getBlockColor(BlockIDRepository.leavesID, 14), ColorizerFoliage.getFoliageColorBirch() | 0xFF000000);
        this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.leavesID, 15)] = this.colorMultiplier(this.getBlockColor(BlockIDRepository.leavesID, 15), ColorizerFoliage.getFoliageColorBasic() | 0xFF000000);
        this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.leaves2ID, 0)] = this.colorMultiplier(this.getBlockColor(BlockIDRepository.leaves2ID, 0), ColorizerFoliage.getFoliageColorBasic() | 0xFF000000);
        this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.leaves2ID, 1)] = this.colorMultiplier(this.getBlockColor(BlockIDRepository.leaves2ID, 1), ColorizerFoliage.getFoliageColorBasic() | 0xFF000000);
        this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.leaves2ID, 4)] = this.colorMultiplier(this.getBlockColor(BlockIDRepository.leaves2ID, 4), ColorizerFoliage.getFoliageColorBasic() | 0xFF000000);
        this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.leaves2ID, 5)] = this.colorMultiplier(this.getBlockColor(BlockIDRepository.leaves2ID, 5), ColorizerFoliage.getFoliageColorBasic() | 0xFF000000);
        this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.tallGrassID, 1)] = this.colorMultiplier(this.getBlockColor(BlockIDRepository.tallGrassID, 1), ColorizerGrass.getGrassColor(0.7, 0.8) | 0xFF000000);
        this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.tallGrassID, 2)] = this.colorMultiplier(this.getBlockColor(BlockIDRepository.tallGrassID, 2), ColorizerGrass.getGrassColor(0.7, 0.8) | 0xFF000000);
        this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.tallFlowerID, 2)] = this.colorMultiplier(this.getBlockColor(BlockIDRepository.tallFlowerID, 2), ColorizerGrass.getGrassColor(0.7, 0.8) | 0xFF000000);
        this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.tallFlowerID, 3)] = this.colorMultiplier(this.getBlockColor(BlockIDRepository.tallFlowerID, 3), ColorizerGrass.getGrassColor(0.7, 0.8) | 0xFF000000);
        for (int t = 0; t < 16; ++t) {
            this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.reedsID, 0)] = this.colorMultiplier(this.getBlockColor(BlockIDRepository.reedsID, 0), ColorizerGrass.getGrassColor(0.7, 0.8) | 0xFF000000);
            this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.vineID, t)] = this.colorMultiplier(this.getBlockColor(BlockIDRepository.vineID, t), ColorizerFoliage.getFoliageColor(0.7, 0.8) | 0xFF000000);
        }
        this.loadWaterColor(biomes);
    }
    
    private void loadWaterColor(final boolean biomes) {
        int waterRGB = -1;
        waterRGB = this.getBlockColor(BlockIDRepository.waterID, 0);
        InputStream is = null;
        int waterMult = -1;
        BufferedImage waterColorBuff = null;
        try {
            is = this.game.getResourceManager().getResource(new ResourceLocation("mcpatcher/colormap/water.png")).getInputStream();
        }
        catch (IOException e) {
            is = null;
        }
        if (is != null) {
            try {
                final Image waterColor = ImageIO.read(is);
                is.close();
                waterColorBuff = new BufferedImage(waterColor.getWidth(null), waterColor.getHeight(null), 1);
                final Graphics gfx = waterColorBuff.createGraphics();
                gfx.drawImage(waterColor, 0, 0, null);
                gfx.dispose();
                final BiomeGenBase genBase = BiomeGenBase.forest;
                double var1 = MathHelper.clamp_float(genBase.getFloatTemperature(0, 64, 0), 0.0f, 1.0f);
                double var2 = MathHelper.clamp_float(genBase.getFloatRainfall(), 0.0f, 1.0f);
                var2 *= var1;
                var1 = 1.0 - var1;
                var2 = 1.0 - var2;
                waterMult = (waterColorBuff.getRGB((int)((waterColorBuff.getWidth() - 1) * var1), (int)((waterColorBuff.getHeight() - 1) * var2)) & 0xFFFFFF);
            }
            catch (Exception ex) {}
        }
        if (waterMult != -1 && waterMult != 0) {
            waterRGB = this.colorMultiplier(waterRGB, waterMult | 0xFF000000);
        }
        else {
            waterRGB = this.colorMultiplier(waterRGB, BiomeGenBase.forest.waterColorMultiplier | 0xFF000000);
        }
        for (int t = 0; t < 16; ++t) {
            this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.flowingWaterID, t)] = waterRGB;
            this.blockColorsWithDefaultTint[this.blockColorID(BlockIDRepository.waterID, t)] = waterRGB;
        }
    }
    
    private final int blockColorID(final int blockid, final int meta) {
        return blockid | meta << 12;
    }
    
    @Override
    public final int getBlockColorWithDefaultTint(final int blockID, final int metadata, final int biomeID) {
        final int col = this.blockColorsWithDefaultTint[this.blockColorID(blockID, metadata)];
        if (col != ColorManager.COLOR_NOT_LOADED) {
            return col;
        }
        return this.getBlockColor(blockID, metadata);
    }
    
    @Override
    public final int getBlockColor(final int blockID, final int metadata, final int biomeID) {
        if ((this.hdInstalled || this.optifineInstalled) && this.biomeTextureAvailable.contains(blockID)) {
            final Integer col = this.blockBiomeSpecificColors.get("" + this.blockColorID(blockID, metadata) + " " + biomeID);
            if (col != null) {
                return col;
            }
        }
        return this.getBlockColor(blockID, metadata);
    }
    
    private final int getBlockColor(final int blockID, final int metadata) {
        synchronized (this.tpLoadLock) {
            try {
                if (this.blockColors[this.blockColorID(blockID, metadata)] == ColorManager.COLOR_NOT_LOADED) {
                    this.blockColors[this.blockColorID(blockID, metadata)] = this.getColor(blockID, metadata);
                }
                int col = this.blockColors[this.blockColorID(blockID, metadata)];
                if (col != -65025) {
                    return col;
                }
                if (this.blockColors[this.blockColorID(blockID, 0)] == ColorManager.COLOR_NOT_LOADED) {
                    this.blockColors[this.blockColorID(blockID, 0)] = this.getColor(blockID, 0);
                }
                col = this.blockColors[this.blockColorID(blockID, 0)];
                if (col != -65025) {
                    return col;
                }
                return 0;
            }
            catch (ArrayIndexOutOfBoundsException e) {
                return -65025;
            }
        }
    }
    
    private int getColor(final int blockID, final int metadata, final boolean retainTransparency) {
        int color = this.getColor(blockID, metadata);
        if (!retainTransparency) {
            color |= 0xFF000000;
        }
        return color;
    }
    
    private int getColor(final int blockID, final int metadata) {
        try {
            IIcon icon = null;
            if (blockID == BlockIDRepository.redstoneID) {
                return 0x19000000 | (30 + metadata * 15 & 0xFF) << 16 | 0x0 | 0x0;
            }
            icon = Block.getBlockById(blockID).getIcon(1, metadata);
            int color = this.iconToColor(icon, this.terrainBuff);
            if (Arrays.asList(BlockIDRepository.shapedIDS).contains(blockID)) {
                color = this.applyShape(blockID, metadata, color);
            }
            if ((color >> 24 & 0xFF) < 27) {
                color |= 0x1B000000;
            }
            if (blockID != BlockIDRepository.grassID && blockID != BlockIDRepository.leavesID && blockID != BlockIDRepository.leaves2ID && blockID != BlockIDRepository.tallGrassID && blockID != BlockIDRepository.reedsID && blockID != BlockIDRepository.vineID && blockID != BlockIDRepository.tallFlowerID && blockID != BlockIDRepository.waterID && blockID != BlockIDRepository.flowingWaterID) {
                final int tint = Block.getBlockById(blockID).colorMultiplier(this.game.theWorld, this.game.thePlayer.serverPosX, 78, (int)this.game.thePlayer.posZ) | 0xFF000000;
                if (tint != 16777215 && tint != -1) {
                    this.biomeTintsAvailable.add(blockID);
                    this.blockColorsWithDefaultTint[this.blockColorID(blockID, metadata)] = this.colorMultiplier(color, tint);
                }
            }
            return color;
        }
        catch (Exception e) {
            System.err.println("failed getting color: " + blockID + " " + metadata);
            e.printStackTrace();
            return -65025;
        }
    }
    
    private int iconToColor(final IIcon icon, final BufferedImage imageBuff) {
        int color = 0;
        if (icon != null) {
            final int left = (int)(icon.getMinU() * imageBuff.getWidth());
            final int right = (int)(icon.getMaxU() * imageBuff.getWidth());
            final int top = (int)(icon.getMinV() * imageBuff.getHeight());
            final int bottom = (int)(icon.getMaxV() * imageBuff.getHeight());
            final BufferedImage blockTexture = imageBuff.getSubimage(left, top, right - left, bottom - top);
            final Image singlePixel = blockTexture.getScaledInstance(1, 1, 4);
            final BufferedImage singlePixelBuff = new BufferedImage(1, 1, imageBuff.getType());
            final Graphics gfx = singlePixelBuff.createGraphics();
            gfx.drawImage(singlePixel, 0, 0, null);
            gfx.dispose();
            color = singlePixelBuff.getRGB(0, 0);
        }
        return color;
    }
    
    private int applyShape(final int blockID, final int metadata, int color) {
        int alpha = color >> 24 & 0xFF;
        final int red = color >> 16 & 0xFF;
        final int green = color >> 8 & 0xFF;
        final int blue = color >> 0 & 0xFF;
        if (blockID == BlockIDRepository.signID || blockID == BlockIDRepository.wallSignID) {
            alpha = 31;
        }
        else if (blockID == BlockIDRepository.woodDoorID || blockID == BlockIDRepository.ironDoorID) {
            alpha = 47;
        }
        else if (blockID == BlockIDRepository.ladderID || blockID == BlockIDRepository.vineID) {
            alpha = 15;
        }
        else if (blockID == BlockIDRepository.stoneButtonID || blockID == BlockIDRepository.woodButtonID) {
            alpha = 11;
        }
        else if (blockID == BlockIDRepository.fenceID || blockID == BlockIDRepository.netherFenceID) {
            alpha = 95;
        }
        else if (blockID == BlockIDRepository.fenceGateID) {
            alpha = 92;
        }
        else if (blockID == BlockIDRepository.cobbleWallID) {
            alpha = 153;
        }
        color = ((alpha & 0xFF) << 24 | (red & 0xFF) << 16 | (green & 0xFF) << 8 | (blue & 0xFF));
        return color;
    }
    
    @Override
    public int colorMultiplier(final int color1, final int color2) {
        final int alpha1 = color1 >> 24 & 0xFF;
        final int red1 = color1 >> 16 & 0xFF;
        final int green1 = color1 >> 8 & 0xFF;
        final int blue1 = color1 >> 0 & 0xFF;
        final int alpha2 = color2 >> 24 & 0xFF;
        final int red2 = color2 >> 16 & 0xFF;
        final int green2 = color2 >> 8 & 0xFF;
        final int blue2 = color2 >> 0 & 0xFF;
        final int alpha3 = alpha1 * alpha2 / 255;
        final int red3 = red1 * red2 / 255;
        final int green3 = green1 * green2 / 255;
        final int blue3 = blue1 * blue2 / 255;
        return (alpha3 & 0xFF) << 24 | (red3 & 0xFF) << 16 | (green3 & 0xFF) << 8 | (blue3 & 0xFF);
    }
    
    @Override
    public int colorAdder(final int color1, final int color2) {
        final float topAlpha = (color1 >> 24 & 0xFF) / 255.0f;
        final float red1 = (color1 >> 16 & 0xFF) * topAlpha;
        final float green1 = (color1 >> 8 & 0xFF) * topAlpha;
        final float blue1 = (color1 >> 0 & 0xFF) * topAlpha;
        final float bottomAlpha = (color2 >> 24 & 0xFF) / 255.0f;
        final float red2 = (color2 >> 16 & 0xFF) * bottomAlpha * (1.0f - topAlpha);
        final float green2 = (color2 >> 8 & 0xFF) * bottomAlpha * (1.0f - topAlpha);
        final float blue2 = (color2 >> 0 & 0xFF) * bottomAlpha * (1.0f - topAlpha);
        final float alpha = topAlpha + bottomAlpha * (1.0f - topAlpha);
        final float red3 = (red1 + red2) / alpha;
        final float green3 = (green1 + green2) / alpha;
        final float blue3 = (blue1 + blue2) / alpha;
        return ((int)(alpha * 255.0f) & 0xFF) << 24 | ((int)red3 & 0xFF) << 16 | ((int)green3 & 0xFF) << 8 | ((int)blue3 & 0xFF);
    }
    
    private void processCTM() {
        this.renderPassThreeBlendMode = "alpha";
        final Properties properties = new Properties();
        final ResourceLocation propertiesFile = new ResourceLocation("minecraft", "mcpatcher/renderpass.properties");
        try {
            final InputStream input = this.game.getResourceManager().getResource(propertiesFile).getInputStream();
            if (input != null) {
                properties.load(input);
                input.close();
                this.renderPassThreeBlendMode = properties.getProperty("blend.3");
            }
        }
        catch (IOException e) {
            this.renderPassThreeBlendMode = "alpha";
        }
        final String namespace = "minecraft";
        for (final ResourceLocation s : this.findResources(namespace, "/mcpatcher/ctm", ".properties", true, false, true)) {
            try {
                this.loadCTM(s);
            }
            catch (NumberFormatException ex) {}
            catch (IllegalArgumentException ex2) {}
        }
        for (int t = 0; t < this.blockColors.length; ++t) {
            if (this.blockColors[t] != -65025 && this.blockColors[t] != ColorManager.COLOR_NOT_LOADED && (this.blockColors[t] >> 24 & 0xFF) < 27) {
                final int[] blockColors = this.blockColors;
                final int n = t;
                blockColors[n] |= 0x1B000000;
            }
        }
    }
    
    private void loadCTM(final ResourceLocation propertiesFile) {
        if (propertiesFile == null) {
            return;
        }
        final Properties properties = new Properties();
        try {
            final InputStream input = this.game.getResourceManager().getResource(propertiesFile).getInputStream();
            if (input != null) {
                properties.load(input);
                input.close();
            }
        }
        catch (IOException e) {
            return;
        }
        final RenderBlocks renderBlocks = new RenderBlocks();
        final String filePath = propertiesFile.getResourcePath();
        final String method = properties.getProperty("method", "").trim().toLowerCase();
        final String faces = properties.getProperty("faces", "").trim().toLowerCase();
        final String matchBlocks = properties.getProperty("matchBlocks", "").trim().toLowerCase();
        final String matchTiles = properties.getProperty("matchTiles", "").trim().toLowerCase();
        final String metadata = properties.getProperty("metadata", "").trim().toLowerCase();
        final String tiles = properties.getProperty("tiles", "").trim();
        final String biomes = properties.getProperty("biomes", "").trim().toLowerCase();
        final String renderPass = properties.getProperty("renderPass", "").trim().toLowerCase();
        final String[] blockNames = this.parseStringList(matchBlocks);
        int[] blockInts = new int[blockNames.length];
        for (int t = 0; t < blockNames.length; ++t) {
            blockInts[t] = this.parseBlockName(blockNames[t]);
        }
        int[] metadataInts = this.parseIntegerList(metadata, 0, 255);
        final String directory = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        final String[] tilesParsed = this.parseStringList(tiles);
        String tilePath = directory + "0";
        if (tilesParsed.length > 0) {
            tilePath = tilesParsed[0].trim();
        }
        if (tilePath.startsWith("~")) {
            tilePath = tilePath.replace("~", "mcpatcher");
        }
        else {
            tilePath = directory + tilePath;
        }
        if (!tilePath.toLowerCase().endsWith(".png")) {
            tilePath += ".png";
        }
        final String[] biomesArray = biomes.split(" ");
        if (blockInts.length == 0) {
            int blockID = -1;
            final Pattern pattern = Pattern.compile(".*/block([\\d]+)[a-zA-Z]*.properties");
            final Matcher matcher = pattern.matcher(filePath);
            if (matcher.find()) {
                blockID = Integer.parseInt(matcher.group(1));
            }
            else {
                final String tileNameToMatch = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf(".properties"));
                for (int t2 = 0; t2 < 4096; ++t2) {
                    final Block block = (Block)Block.blockRegistry.getObjectForID(t2);
                    if (block != null) {
                        String tileNameOfBlock = "";
                        if (metadataInts.length > 0) {
                            for (int s = 0; s < metadataInts.length; ++s) {
                                try {
                                    tileNameOfBlock = renderBlocks.getBlockIconFromSideAndMetadata(block, 1, metadataInts[s]).getIconName();
                                }
                                catch (Exception e2) {
                                    tileNameOfBlock = "";
                                }
                                if (tileNameOfBlock.equals(tileNameToMatch)) {
                                    blockID = t2;
                                }
                            }
                        }
                        else {
                            final ArrayList<Integer> tmpList = new ArrayList<Integer>();
                            for (int s2 = 0; s2 < 16; ++s2) {
                                try {
                                    tileNameOfBlock = renderBlocks.getBlockIconFromSideAndMetadata(block, 1, s2).getIconName();
                                }
                                catch (Exception e3) {
                                    tileNameOfBlock = "";
                                }
                                if (tileNameOfBlock.equals(tileNameToMatch)) {
                                    blockID = t2;
                                    tmpList.add(s2);
                                }
                            }
                            metadataInts = new int[tmpList.size()];
                            for (int i = 0; i < metadataInts.length; ++i) {
                                metadataInts[i] = tmpList.get(i);
                            }
                        }
                    }
                }
            }
            if (blockID != -1) {
                blockInts = new int[] { blockID };
            }
        }
        if (metadataInts.length == 0) {
            metadataInts = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
        }
        if (blockInts.length == 0) {
            return;
        }
        if (!method.equals("horizontal")) {
            if (!method.equals("sandstone") && !method.equals("top") && !faces.contains("top") && !faces.contains("all")) {
                if (faces.length() != 0) {
                    return;
                }
            }
            try {
                for (int t3 = 0; t3 < blockInts.length; ++t3) {
                    final ResourceLocation pngResource = new ResourceLocation(propertiesFile.getResourceDomain(), tilePath);
                    final InputStream is = this.game.getResourceManager().getResource(pngResource).getInputStream();
                    Image top = ImageIO.read(is);
                    is.close();
                    top = top.getScaledInstance(1, 1, 4);
                    final BufferedImage topBuff = new BufferedImage(top.getWidth(null), top.getHeight(null), 6);
                    final Graphics gfx = topBuff.createGraphics();
                    gfx.drawImage(top, 0, 0, null);
                    gfx.dispose();
                    int topRGB = topBuff.getRGB(0, 0);
                    if (blockInts[t3] == BlockIDRepository.cobwebID) {
                        topRGB |= 0xFF000000;
                    }
                    if (renderPass.equals("3")) {
                        topRGB = this.processRenderPassThree(topRGB);
                        final int baseRGB = this.blockColors[this.blockColorID(blockInts[t3], metadataInts[0])];
                        if (baseRGB != -65025 && baseRGB != ColorManager.COLOR_NOT_LOADED) {
                            topRGB = this.colorMultiplier(baseRGB, topRGB);
                        }
                    }
                    if (Arrays.asList(BlockIDRepository.shapedIDS).contains(blockInts[t3])) {
                        topRGB = this.applyShape(blockInts[t3], metadataInts[0], topRGB);
                    }
                    for (int s = 0; s < metadataInts.length; ++s) {
                        try {
                            if (!biomes.equals("")) {
                                this.biomeTextureAvailable.add(blockInts[t3]);
                                for (int r = 0; r < biomesArray.length; ++r) {
                                    final int biomeInt = this.parseBiomeName(biomesArray[r]);
                                    if (biomeInt != -1) {
                                        this.blockBiomeSpecificColors.put("" + this.blockColorID(blockInts[t3], metadataInts[s]) + " " + biomeInt, topRGB);
                                    }
                                }
                            }
                            else {
                                this.blockColors[this.blockColorID(blockInts[t3], metadataInts[s])] = topRGB;
                            }
                        }
                        catch (Exception e2) {
                            System.err.println("blockID + metadata (" + blockInts[t3] + ", " + metadataInts[s] + ") out of range");
                        }
                    }
                }
            }
            catch (IOException e4) {
                System.err.println("error getting CTM block: " + filePath + " " + blockInts[0] + " " + metadataInts[0] + " " + tilePath);
            }
        }
    }
    
    private int processRenderPassThree(int rgb) {
        if (this.renderPassThreeBlendMode.equals("color") || this.renderPassThreeBlendMode.equals("overlay")) {
            final int alpha = rgb >> 24 & 0xFF;
            int red = rgb >> 16 & 0xFF;
            int green = rgb >> 8 & 0xFF;
            int blue = rgb >> 0 & 0xFF;
            final float colorAverage = (red + blue + green) / 3.0f;
            final float lighteningFactor = (colorAverage - 127.5f) * 2.0f;
            red += (int)(red * (lighteningFactor / 255.0f));
            blue += (int)(red * (lighteningFactor / 255.0f));
            green += (int)(red * (lighteningFactor / 255.0f));
            final int newAlpha = (int)Math.abs(lighteningFactor);
            rgb = (newAlpha << 24 | (red & 0xFF) << 16 | (green & 0xFF) << 8 | (blue & 0xFF));
        }
        return rgb;
    }
    
    private int[] parseIntegerList(final String list, final int minValue, final int maxValue) {
        final ArrayList<Integer> tmpList = new ArrayList<Integer>();
        for (String token : list.replace(',', ' ').split("\\s+")) {
            token = token.trim();
            try {
                if (token.matches("^\\d+$")) {
                    tmpList.add(Integer.parseInt(token));
                }
                else if (token.matches("^\\d+-\\d+$")) {
                    final String[] t = token.split("-");
                    final int min = Integer.parseInt(t[0]);
                    for (int max = Integer.parseInt(t[1]), i = min; i <= max; ++i) {
                        tmpList.add(i);
                    }
                }
                else if (token.matches("^\\d+:\\d+$")) {
                    final String[] t = token.split(":");
                    final int id = Integer.parseInt(t[0]);
                    final int metadata = Integer.parseInt(t[1]);
                    tmpList.add(id);
                }
            }
            catch (NumberFormatException ex) {}
        }
        if (minValue <= maxValue) {
            int j = 0;
            while (j < tmpList.size()) {
                if (tmpList.get(j) < minValue || tmpList.get(j) > maxValue) {
                    tmpList.remove(j);
                }
                else {
                    ++j;
                }
            }
        }
        final int[] a = new int[tmpList.size()];
        for (int k = 0; k < a.length; ++k) {
            a[k] = tmpList.get(k);
        }
        return a;
    }
    
    private String[] parseStringList(final String list) {
        final ArrayList<String> tmpList = new ArrayList<String>();
        for (String token : list.replace(',', ' ').split("\\s+")) {
            token = token.trim();
            try {
                if (token.matches("^\\d+$")) {
                    tmpList.add("" + Integer.parseInt(token));
                }
                else if (token.matches("^\\d+-\\d+$")) {
                    final String[] t = token.split("-");
                    final int min = Integer.parseInt(t[0]);
                    for (int max = Integer.parseInt(t[1]), i = min; i <= max; ++i) {
                        tmpList.add("" + i);
                    }
                }
                else if (token != null && token != "") {
                    tmpList.add(token);
                }
            }
            catch (NumberFormatException ex) {}
        }
        final String[] a = new String[tmpList.size()];
        for (int j = 0; j < a.length; ++j) {
            a[j] = tmpList.get(j);
        }
        return a;
    }
    
    private int parseBiomeName(final String name) {
        if (name.matches("^\\d+$")) {
            return Integer.parseInt(name);
        }
        for (int t = 0; t < BiomeGenBase.getBiomeGenArray().length; ++t) {
            if (BiomeGenBase.getBiomeGenArray()[t] != null && BiomeGenBase.getBiomeGenArray()[t].biomeName.toLowerCase().replace(" ", "").equalsIgnoreCase(name)) {
                return t;
            }
        }
        return -1;
    }
    
    private List<IResourcePack> getResourcePacks(final String namespace) {
        final List<IResourcePack> list = new ArrayList<IResourcePack>();
        final IResourceManager superResourceManager = this.game.getResourceManager();
        if (superResourceManager instanceof SimpleReloadableResourceManager) {
            Map<String, FallbackResourceManager> nameSpaceToResourceManager = null;
            final Object nameSpaceToResourceManagerObj = ReflectionUtils.getPrivateFieldValueByType(superResourceManager, SimpleReloadableResourceManager.class, Map.class);
            if (nameSpaceToResourceManagerObj == null) {
                return list;
            }
            nameSpaceToResourceManager = (Map<String, FallbackResourceManager>)nameSpaceToResourceManagerObj;
            for (final Map.Entry<String, FallbackResourceManager> entry : nameSpaceToResourceManager.entrySet()) {
                if (namespace == null || namespace.equals(entry.getKey())) {
                    final FallbackResourceManager resourceManager = entry.getValue();
                    List resourcePacks = null;
                    final Object resourcePacksObj = ReflectionUtils.getPrivateFieldValueByType(resourceManager, FallbackResourceManager.class, List.class);
                    if (resourcePacksObj == null) {
                        return list;
                    }
                    resourcePacks = (List)resourcePacksObj;
                    list.addAll(resourcePacks);
                }
            }
        }
        Collections.reverse(list);
        return list;
    }
    
    private List<ResourceLocation> findResources(final String namespace, String directory, String suffix, final boolean recursive, final boolean directories, final boolean sortByFilename) {
        if (directory == null) {
            directory = "";
        }
        if (directory.startsWith("/")) {
            directory = directory.substring(1);
        }
        if (suffix == null) {
            suffix = "";
        }
        final ArrayList<ResourceLocation> resources = new ArrayList<ResourceLocation>();
        for (final IResourcePack resourcePack : this.getResourcePacks(namespace)) {
            if (!(resourcePack instanceof DefaultResourcePack)) {
                if (resourcePack instanceof FileResourcePack) {
                    final Object zipFileObj = ReflectionUtils.getPrivateFieldValueByType(resourcePack, FileResourcePack.class, ZipFile.class);
                    if (zipFileObj == null) {
                        return resources;
                    }
                    final ZipFile zipFile = (ZipFile)zipFileObj;
                    if (zipFile == null) {
                        continue;
                    }
                    this.findResourcesZip(zipFile, namespace, "assets/" + namespace, directory, suffix, recursive, directories, resources);
                }
                else {
                    if (!(resourcePack instanceof AbstractResourcePack)) {
                        continue;
                    }
                    final Object baseObj = ReflectionUtils.getPrivateFieldValueByType(resourcePack, AbstractResourcePack.class, File.class);
                    if (baseObj == null) {
                        return resources;
                    }
                    File base = (File)baseObj;
                    if (base == null || !base.isDirectory()) {
                        continue;
                    }
                    base = new File(base, "assets/" + namespace);
                    if (!base.isDirectory()) {
                        continue;
                    }
                    findResourcesDirectory(base, namespace, directory, suffix, recursive, directories, resources);
                }
            }
        }
        if (sortByFilename) {
            Collections.sort(resources, new Comparator<ResourceLocation>() {
                @Override
                public int compare(final ResourceLocation o1, final ResourceLocation o2) {
                    final String f1 = o1.getResourcePath().replaceAll(".*/", "").replaceFirst("\\.properties", "");
                    final String f2 = o2.getResourcePath().replaceAll(".*/", "").replaceFirst("\\.properties", "");
                    final int result = f1.compareTo(f2);
                    if (result != 0) {
                        return result;
                    }
                    return o1.getResourcePath().compareTo(o2.getResourcePath());
                }
            });
        }
        else {
            Collections.sort(resources, new Comparator<ResourceLocation>() {
                @Override
                public int compare(final ResourceLocation o1, final ResourceLocation o2) {
                    return o1.getResourcePath().compareTo(o2.getResourcePath());
                }
            });
        }
        return resources;
    }
    
    private void findResourcesZip(final ZipFile zipFile, final String namespace, final String root, final String directory, final String suffix, final boolean recursive, final boolean directories, final Collection<ResourceLocation> resources) {
        final String base = root + "/" + directory;
        for (final ZipEntry entry : Collections.list(zipFile.entries())) {
            if (entry.isDirectory() == directories) {
                final String name = entry.getName().replaceFirst("^/", "");
                if (!name.startsWith(base) || !name.endsWith(suffix)) {
                    continue;
                }
                if (directory.equals("")) {
                    if (!recursive && name.contains("/")) {
                        continue;
                    }
                    resources.add(new ResourceLocation(namespace, name));
                }
                else {
                    final String subpath = name.substring(base.length());
                    if ((!subpath.equals("") && !subpath.startsWith("/")) || (!recursive && !subpath.equals("") && subpath.substring(1).contains("/"))) {
                        continue;
                    }
                    resources.add(new ResourceLocation(namespace, name.substring(root.length() + 1)));
                }
            }
        }
    }
    
    private void processColorProperties() {
        final List<ResourceLocation> unusedPNGs = new ArrayList<ResourceLocation>();
        unusedPNGs.addAll(this.findResources("minecraft", "/mcpatcher/colormap/blocks", ".png", true, false, true));
        final Properties properties = new Properties();
        try {
            final InputStream input = this.game.getResourceManager().getResource(new ResourceLocation("mcpatcher/color.properties")).getInputStream();
            if (input != null) {
                properties.load(input);
                input.close();
            }
        }
        catch (IOException ex) {}
        int lilypadMultiplier = 2129968;
        final String lilypadMultiplierString = properties.getProperty("lilypad");
        if (lilypadMultiplierString != null) {
            lilypadMultiplier = Integer.parseInt(lilypadMultiplierString, 16);
        }
        for (int t = 0; t < 16; ++t) {
            this.blockColors[this.blockColorID(BlockIDRepository.lilypadID, t)] = this.colorMultiplier(this.getBlockColor(BlockIDRepository.lilypadID, t), lilypadMultiplier | 0xFF000000);
        }
        final Enumeration e = properties.propertyNames();
        while (e.hasMoreElements()) {
            final String key = e.nextElement();
            if (key.startsWith("palette.block")) {
                String filename = key.substring("palette.block.".length());
                filename = filename.replace("~", "mcpatcher");
                this.processColorProperty(new ResourceLocation(filename), properties.getProperty(key));
            }
        }
        for (final ResourceLocation resource : this.findResources("minecraft", "/mcpatcher/colormap/blocks", ".properties", true, false, true)) {
            final Properties colorProperties = new Properties();
            try {
                final InputStream input2 = this.game.getResourceManager().getResource(resource).getInputStream();
                if (input2 != null) {
                    properties.load(input2);
                    input2.close();
                }
            }
            catch (IOException e2) {
                break;
            }
            final String names = colorProperties.getProperty("blocks");
            final ResourceLocation resourcePNG = new ResourceLocation(resource.getResourceDomain(), resource.getResourcePath().replace(".properties", ".png"));
            unusedPNGs.remove(resourcePNG);
            this.processColorProperty(resourcePNG, names);
        }
        for (final ResourceLocation resource : unusedPNGs) {
            String name = resource.getResourcePath();
            System.out.println("processing name: " + name);
            name = name.substring(name.lastIndexOf("/") + 1, name.lastIndexOf(".png"));
            System.out.println("processed name: " + name);
            this.processColorProperty(resource, "minecraft:" + name);
        }
    }
    
    private void processColorProperty(final ResourceLocation resource, final String list) {
        final Integer[] tints = new Integer[BiomeGenBase.getBiomeGenArray().length];
        final boolean swamp = resource.getResourcePath().contains("/swampgrass") || resource.getResourcePath().contains("/swampfoliage");
        Image tintColors = null;
        try {
            final InputStream is = this.game.getResourceManager().getResource(resource).getInputStream();
            tintColors = ImageIO.read(is);
            is.close();
        }
        catch (IOException e) {
            return;
        }
        for (int t = 0; t < BiomeGenBase.getBiomeGenArray().length; ++t) {
            tints[t] = -1;
        }
        final BufferedImage tintColorsBuff = new BufferedImage(tintColors.getWidth(null), tintColors.getHeight(null), 1);
        final Graphics gfx = tintColorsBuff.createGraphics();
        gfx.drawImage(tintColors, 0, 0, null);
        gfx.dispose();
        for (int t2 = 0; t2 < BiomeGenBase.getBiomeGenArray().length; ++t2) {
            if (BiomeGenBase.getBiomeGenArray()[t2] != null) {
                final BiomeGenBase genBase = BiomeGenBase.getBiomeGenArray()[t2];
                double var1 = MathHelper.clamp_float(genBase.getFloatTemperature(0, 64, 0), 0.0f, 1.0f);
                double var2 = MathHelper.clamp_float(genBase.getFloatRainfall(), 0.0f, 1.0f);
                var2 *= var1;
                var1 = 1.0 - var1;
                var2 = 1.0 - var2;
                final int tintMult = tintColorsBuff.getRGB((int)((tintColorsBuff.getWidth() - 1) * var1), (int)((tintColorsBuff.getHeight() - 1) * var2)) & 0xFFFFFF;
                if (tintMult != 0 && (!swamp || t2 == BiomeGenBase.swampland.biomeID)) {
                    tints[t2] = tintMult;
                }
            }
        }
        for (String token : list.split("\\s+")) {
            token = token.trim();
            String metadataString = "";
            int id = -1;
            int[] metadata = new int[0];
            try {
                String name;
                if (token.matches(".*:[-0-9, ]+")) {
                    final int pos = token.lastIndexOf(58);
                    metadataString = token.substring(pos + 1);
                    name = token.substring(0, pos);
                }
                else {
                    name = token;
                }
                id = this.parseBlockName(name);
                if (id > 0) {
                    this.biomeTintsAvailable.add(id);
                    metadata = this.parseIntegerList(metadataString, 0, 15);
                    if (metadata.length == 0) {
                        metadata = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
                    }
                    for (int t3 = 0; t3 < metadata.length; ++t3) {
                        Integer[] previousTints = this.blockTintTables.get(id + " " + metadata[t3]);
                        if (swamp && previousTints == null) {
                            ResourceLocation defaultResource;
                            if (resource.getResourcePath().endsWith("/swampgrass.png")) {
                                defaultResource = new ResourceLocation("textures/colormap/grass.png");
                            }
                            else {
                                defaultResource = new ResourceLocation("textures/colormap/foliage.png");
                            }
                            this.processColorProperty(defaultResource, "" + id + ":" + metadata[t3]);
                            previousTints = this.blockTintTables.get(id + " " + metadata[t3]);
                        }
                        if (previousTints != null) {
                            for (int s = 0; s < BiomeGenBase.getBiomeGenArray().length; ++s) {
                                if (tints[s] == -1) {
                                    tints[s] = previousTints[s];
                                }
                            }
                        }
                        this.blockColorsWithDefaultTint[this.blockColorID(id, metadata[t3])] = this.colorMultiplier(this.getBlockColor(id, metadata[t3]), tints[4] | 0xFF000000);
                        this.blockTintTables.put(id + " " + metadata[t3], tints);
                    }
                }
            }
            catch (NumberFormatException ex) {}
        }
    }
    
    private int parseBlockName(final String name) {
        final Block block = Block.getBlockFromName(name);
        if (block != null) {
            return Block.getIdFromBlock(block);
        }
        return -1;
    }
    
    static {
        ColorManager.COLOR_NOT_LOADED = -65281;
    }
}
