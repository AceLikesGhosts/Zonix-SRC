package net.minecraft.src;

import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.biome.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.client.particle.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import us.zonix.client.module.impl.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;

public class CustomColorizer
{
    private static int[] grassColors;
    private static int[] waterColors;
    private static int[] foliageColors;
    private static int[] foliagePineColors;
    private static int[] foliageBirchColors;
    private static int[] swampFoliageColors;
    private static int[] swampGrassColors;
    private static int[][] blockPalettes;
    private static int[][] paletteColors;
    private static int[] skyColors;
    private static int[] fogColors;
    private static int[] underwaterColors;
    private static float[][][] lightMapsColorsRgb;
    private static int[] lightMapsHeight;
    private static float[][] sunRgbs;
    private static float[][] torchRgbs;
    private static int[] redstoneColors;
    private static int[] stemColors;
    private static int[] myceliumParticleColors;
    private static boolean useDefaultColorMultiplier;
    private static int particleWaterColor;
    private static int particlePortalColor;
    private static int lilyPadColor;
    private static Vec3 fogColorNether;
    private static Vec3 fogColorEnd;
    private static Vec3 skyColorEnd;
    private static int[] textColors;
    private static final int TYPE_NONE = 0;
    private static final int TYPE_GRASS = 1;
    private static final int TYPE_FOLIAGE = 2;
    private static Random random;
    
    public static void update() {
        CustomColorizer.grassColors = null;
        CustomColorizer.waterColors = null;
        CustomColorizer.foliageColors = null;
        CustomColorizer.foliageBirchColors = null;
        CustomColorizer.foliagePineColors = null;
        CustomColorizer.swampGrassColors = null;
        CustomColorizer.swampFoliageColors = null;
        CustomColorizer.skyColors = null;
        CustomColorizer.fogColors = null;
        CustomColorizer.underwaterColors = null;
        CustomColorizer.redstoneColors = null;
        CustomColorizer.stemColors = null;
        CustomColorizer.myceliumParticleColors = null;
        CustomColorizer.lightMapsColorsRgb = null;
        CustomColorizer.lightMapsHeight = null;
        CustomColorizer.lilyPadColor = -1;
        CustomColorizer.particleWaterColor = -1;
        CustomColorizer.particlePortalColor = -1;
        CustomColorizer.fogColorNether = null;
        CustomColorizer.fogColorEnd = null;
        CustomColorizer.skyColorEnd = null;
        CustomColorizer.textColors = null;
        CustomColorizer.blockPalettes = null;
        CustomColorizer.paletteColors = null;
        CustomColorizer.useDefaultColorMultiplier = true;
        final String mcpColormap = "mcpatcher/colormap/";
        CustomColorizer.grassColors = getCustomColors("textures/colormap/grass.png", 65536);
        CustomColorizer.foliageColors = getCustomColors("textures/colormap/foliage.png", 65536);
        final String[] waterPaths = { "water.png", "watercolorX.png" };
        CustomColorizer.waterColors = getCustomColors(mcpColormap, waterPaths, 65536);
        if (Config.isCustomColors()) {
            final String[] pinePaths = { "pine.png", "pinecolor.png" };
            CustomColorizer.foliagePineColors = getCustomColors(mcpColormap, pinePaths, 65536);
            final String[] birchPaths = { "birch.png", "birchcolor.png" };
            CustomColorizer.foliageBirchColors = getCustomColors(mcpColormap, birchPaths, 65536);
            final String[] swampGrassPaths = { "swampgrass.png", "swampgrasscolor.png" };
            CustomColorizer.swampGrassColors = getCustomColors(mcpColormap, swampGrassPaths, 65536);
            final String[] swampFoliagePaths = { "swampfoliage.png", "swampfoliagecolor.png" };
            CustomColorizer.swampFoliageColors = getCustomColors(mcpColormap, swampFoliagePaths, 65536);
            final String[] sky0Paths = { "sky0.png", "skycolor0.png" };
            CustomColorizer.skyColors = getCustomColors(mcpColormap, sky0Paths, 65536);
            final String[] fog0Paths = { "fog0.png", "fogcolor0.png" };
            CustomColorizer.fogColors = getCustomColors(mcpColormap, fog0Paths, 65536);
            final String[] underwaterPaths = { "underwater.png", "underwatercolor.png" };
            CustomColorizer.underwaterColors = getCustomColors(mcpColormap, underwaterPaths, 65536);
            final String[] redstonePaths = { "redstone.png", "redstonecolor.png" };
            CustomColorizer.redstoneColors = getCustomColors(mcpColormap, redstonePaths, 16);
            final String[] stemPaths = { "stem.png", "stemcolor.png" };
            CustomColorizer.stemColors = getCustomColors(mcpColormap, stemPaths, 8);
            final String[] myceliumPaths = { "myceliumparticle.png", "myceliumparticlecolor.png" };
            CustomColorizer.myceliumParticleColors = getCustomColors(mcpColormap, myceliumPaths, -1);
            final int[][] lightMapsColors = new int[3][];
            CustomColorizer.lightMapsColorsRgb = new float[3][][];
            CustomColorizer.lightMapsHeight = new int[3];
            for (int i = 0; i < lightMapsColors.length; ++i) {
                final String path = "mcpatcher/lightmap/world" + (i - 1) + ".png";
                lightMapsColors[i] = getCustomColors(path, -1);
                if (lightMapsColors[i] != null) {
                    CustomColorizer.lightMapsColorsRgb[i] = toRgb(lightMapsColors[i]);
                }
                CustomColorizer.lightMapsHeight[i] = getTextureHeight(path, 32);
            }
            readColorProperties("mcpatcher/color.properties");
            updateUseDefaultColorMultiplier();
        }
    }
    
    private static int getTextureHeight(final String path, final int defHeight) {
        try {
            final InputStream e = Config.getResourceStream(new ResourceLocation(path));
            if (e == null) {
                return defHeight;
            }
            final BufferedImage bi = ImageIO.read(e);
            return (bi == null) ? defHeight : bi.getHeight();
        }
        catch (IOException var4) {
            return defHeight;
        }
    }
    
    private static float[][] toRgb(final int[] cols) {
        final float[][] colsRgb = new float[cols.length][3];
        for (int i = 0; i < cols.length; ++i) {
            final int col = cols[i];
            final float rf = (col >> 16 & 0xFF) / 255.0f;
            final float gf = (col >> 8 & 0xFF) / 255.0f;
            final float bf = (col & 0xFF) / 255.0f;
            final float[] colRgb = colsRgb[i];
            colRgb[0] = rf;
            colRgb[1] = gf;
            colRgb[2] = bf;
        }
        return colsRgb;
    }
    
    private static void readColorProperties(final String fileName) {
        try {
            final ResourceLocation e = new ResourceLocation(fileName);
            final InputStream in = Config.getResourceStream(e);
            if (in == null) {
                return;
            }
            Config.log("Loading " + fileName);
            final Properties props = new Properties();
            props.load(in);
            CustomColorizer.lilyPadColor = readColor(props, "lilypad");
            CustomColorizer.particleWaterColor = readColor(props, new String[] { "particle.water", "drop.water" });
            CustomColorizer.particlePortalColor = readColor(props, "particle.portal");
            CustomColorizer.fogColorNether = readColorVec3(props, "fog.nether");
            CustomColorizer.fogColorEnd = readColorVec3(props, "fog.end");
            CustomColorizer.skyColorEnd = readColorVec3(props, "sky.end");
            CustomColorizer.textColors = readTextColors(props, fileName, "text.code.", "Text");
            readCustomPalettes(props, fileName);
        }
        catch (FileNotFoundException var6) {}
        catch (IOException var5) {
            var5.printStackTrace();
        }
    }
    
    private static void readCustomPalettes(final Properties props, final String fileName) {
        CustomColorizer.blockPalettes = new int[256][1];
        for (int palettePrefix = 0; palettePrefix < 256; ++palettePrefix) {
            CustomColorizer.blockPalettes[palettePrefix][0] = -1;
        }
        final String var18 = "palette.block.";
        final HashMap map = new HashMap();
        final Set keys = props.keySet();
        for (final String i : keys) {
            final String name = props.getProperty(i);
            if (i.startsWith(var18)) {
                map.put(i, name);
            }
        }
        final String[] var19 = (String[])map.keySet().toArray(new String[map.size()]);
        CustomColorizer.paletteColors = new int[var19.length][];
        for (int var20 = 0; var20 < var19.length; ++var20) {
            final String name = var19[var20];
            final String value = props.getProperty(name);
            Config.log("Block palette: " + name + " = " + value);
            String path = name.substring(var18.length());
            final String basePath = TextureUtils.getBasePath(fileName);
            path = TextureUtils.fixResourcePath(path, basePath);
            final int[] colors = getCustomColors(path, 65536);
            CustomColorizer.paletteColors[var20] = colors;
            final String[] indexStrs = Config.tokenize(value, " ,;");
            for (int ix = 0; ix < indexStrs.length; ++ix) {
                String blockStr = indexStrs[ix];
                int metadata = -1;
                if (blockStr.contains(":")) {
                    final String[] blockIndex = Config.tokenize(blockStr, ":");
                    blockStr = blockIndex[0];
                    final String metadataStr = blockIndex[1];
                    metadata = Config.parseInt(metadataStr, -1);
                    if (metadata < 0 || metadata > 15) {
                        Config.log("Invalid block metadata: " + blockStr + " in palette: " + name);
                        continue;
                    }
                }
                final int var21 = Config.parseInt(blockStr, -1);
                if (var21 >= 0 && var21 <= 255) {
                    if (var21 != Block.getIdFromBlock(Blocks.grass) && var21 != Block.getIdFromBlock(Blocks.tallgrass) && var21 != Block.getIdFromBlock(Blocks.leaves) && var21 != Block.getIdFromBlock(Blocks.vine)) {
                        if (metadata == -1) {
                            CustomColorizer.blockPalettes[var21][0] = var20;
                        }
                        else {
                            if (CustomColorizer.blockPalettes[var21].length < 16) {
                                Arrays.fill(CustomColorizer.blockPalettes[var21] = new int[16], -1);
                            }
                            CustomColorizer.blockPalettes[var21][metadata] = var20;
                        }
                    }
                }
                else {
                    Config.log("Invalid block index: " + var21 + " in palette: " + name);
                }
            }
        }
    }
    
    private static int readColor(final Properties props, final String[] names) {
        for (int i = 0; i < names.length; ++i) {
            final String name = names[i];
            final int col = readColor(props, name);
            if (col >= 0) {
                return col;
            }
        }
        return -1;
    }
    
    private static int readColor(final Properties props, final String name) {
        final String str = props.getProperty(name);
        if (str == null) {
            return -1;
        }
        try {
            final int e = Integer.parseInt(str, 16) & 0xFFFFFF;
            Config.log("Custom color: " + name + " = " + str);
            return e;
        }
        catch (NumberFormatException var4) {
            Config.log("Invalid custom color: " + name + " = " + str);
            return -1;
        }
    }
    
    private static Vec3 readColorVec3(final Properties props, final String name) {
        final int col = readColor(props, name);
        if (col < 0) {
            return null;
        }
        final int red = col >> 16 & 0xFF;
        final int green = col >> 8 & 0xFF;
        final int blue = col & 0xFF;
        final float redF = red / 255.0f;
        final float greenF = green / 255.0f;
        final float blueF = blue / 255.0f;
        return Vec3.createVectorHelper(redF, greenF, blueF);
    }
    
    private static int[] getCustomColors(final String basePath, final String[] paths, final int length) {
        for (int i = 0; i < paths.length; ++i) {
            String path = paths[i];
            path = basePath + path;
            final int[] cols = getCustomColors(path, length);
            if (cols != null) {
                return cols;
            }
        }
        return null;
    }
    
    private static int[] getCustomColors(final String path, final int length) {
        try {
            final ResourceLocation e = new ResourceLocation(path);
            final InputStream in = Config.getResourceStream(e);
            if (in == null) {
                return null;
            }
            final int[] colors = TextureUtil.readImageData(Config.getResourceManager(), e);
            if (colors == null) {
                return null;
            }
            if (length > 0 && colors.length != length) {
                Config.log("Invalid custom colors length: " + colors.length + ", path: " + path);
                return null;
            }
            Config.log("Loading custom colors: " + path);
            return colors;
        }
        catch (FileNotFoundException var7) {
            return null;
        }
        catch (IOException var6) {
            var6.printStackTrace();
            return null;
        }
    }
    
    public static void updateUseDefaultColorMultiplier() {
        CustomColorizer.useDefaultColorMultiplier = (CustomColorizer.foliageBirchColors == null && CustomColorizer.foliagePineColors == null && CustomColorizer.swampGrassColors == null && CustomColorizer.swampFoliageColors == null && CustomColorizer.blockPalettes == null && Config.isSwampColors() && Config.isSmoothBiomes());
    }
    
    public static int getColorMultiplier(final Block block, final IBlockAccess blockAccess, final int x, final int y, final int z) {
        if (CustomColorizer.useDefaultColorMultiplier) {
            return block.colorMultiplier(blockAccess, x, y, z);
        }
        int[] colors = null;
        int[] swampColors = null;
        if (CustomColorizer.blockPalettes != null) {
            final int useSwampColors = Block.getIdFromBlock(block);
            if (useSwampColors >= 0 && useSwampColors < 256) {
                final int[] smoothColors = CustomColorizer.blockPalettes[useSwampColors];
                final boolean type = true;
                int type2;
                if (smoothColors.length > 1) {
                    final int metadata = blockAccess.getBlockMetadata(x, y, z);
                    type2 = smoothColors[metadata];
                }
                else {
                    type2 = smoothColors[0];
                }
                if (type2 >= 0) {
                    colors = CustomColorizer.paletteColors[type2];
                }
            }
            if (colors != null) {
                if (Config.isSmoothBiomes()) {
                    return getSmoothColorMultiplier(block, blockAccess, x, y, z, colors, colors, 0, 0);
                }
                return getCustomColor(colors, blockAccess, x, y, z);
            }
        }
        final boolean useSwampColors2 = Config.isSwampColors();
        boolean smoothColors2 = false;
        byte type3 = 0;
        int metadata = 0;
        if (block != Blocks.grass && block != Blocks.tallgrass) {
            if (block == Blocks.leaves) {
                type3 = 2;
                smoothColors2 = Config.isSmoothBiomes();
                metadata = blockAccess.getBlockMetadata(x, y, z);
                if ((metadata & 0x3) == 0x1) {
                    colors = CustomColorizer.foliagePineColors;
                }
                else if ((metadata & 0x3) == 0x2) {
                    colors = CustomColorizer.foliageBirchColors;
                }
                else {
                    colors = CustomColorizer.foliageColors;
                    if (useSwampColors2) {
                        swampColors = CustomColorizer.swampFoliageColors;
                    }
                    else {
                        swampColors = colors;
                    }
                }
            }
            else if (block == Blocks.vine) {
                type3 = 2;
                smoothColors2 = Config.isSmoothBiomes();
                colors = CustomColorizer.foliageColors;
                if (useSwampColors2) {
                    swampColors = CustomColorizer.swampFoliageColors;
                }
                else {
                    swampColors = colors;
                }
            }
        }
        else {
            type3 = 1;
            smoothColors2 = Config.isSmoothBiomes();
            colors = CustomColorizer.grassColors;
            if (useSwampColors2) {
                swampColors = CustomColorizer.swampGrassColors;
            }
            else {
                swampColors = colors;
            }
        }
        if (smoothColors2) {
            return getSmoothColorMultiplier(block, blockAccess, x, y, z, colors, swampColors, type3, metadata);
        }
        if (swampColors != colors && blockAccess.getBiomeGenForCoords(x, z) == BiomeGenBase.swampland) {
            colors = swampColors;
        }
        return (colors != null) ? getCustomColor(colors, blockAccess, x, y, z) : block.colorMultiplier(blockAccess, x, y, z);
    }
    
    private static int getSmoothColorMultiplier(final Block block, final IBlockAccess blockAccess, final int x, final int y, final int z, final int[] colors, final int[] swampColors, final int type, final int metadata) {
        int sumRed = 0;
        int sumGreen = 0;
        int sumBlue = 0;
        for (int r = x - 1; r <= x + 1; ++r) {
            for (int g = z - 1; g <= z + 1; ++g) {
                int[] b = colors;
                if (swampColors != colors && blockAccess.getBiomeGenForCoords(r, g) == BiomeGenBase.swampland) {
                    b = swampColors;
                }
                final boolean col = false;
                int var17 = 0;
                if (b == null) {
                    switch (type) {
                        case 1: {
                            var17 = blockAccess.getBiomeGenForCoords(r, g).getBiomeGrassColor(x, y, z);
                            break;
                        }
                        case 2: {
                            if ((metadata & 0x3) == 0x1) {
                                var17 = ColorizerFoliage.getFoliageColorPine();
                                break;
                            }
                            if ((metadata & 0x3) == 0x2) {
                                var17 = ColorizerFoliage.getFoliageColorBirch();
                                break;
                            }
                            var17 = blockAccess.getBiomeGenForCoords(r, g).getBiomeFoliageColor(x, y, z);
                            break;
                        }
                        default: {
                            var17 = block.colorMultiplier(blockAccess, r, y, g);
                            break;
                        }
                    }
                }
                else {
                    var17 = getCustomColor(b, blockAccess, r, y, g);
                }
                sumRed += (var17 >> 16 & 0xFF);
                sumGreen += (var17 >> 8 & 0xFF);
                sumBlue += (var17 & 0xFF);
            }
        }
        int r = sumRed / 9;
        int g = sumGreen / 9;
        final int var18 = sumBlue / 9;
        return r << 16 | g << 8 | var18;
    }
    
    public static int getFluidColor(final Block block, final IBlockAccess blockAccess, final int x, final int y, final int z) {
        return (block.getMaterial() != Material.water) ? block.colorMultiplier(blockAccess, x, y, z) : ((CustomColorizer.waterColors != null) ? (Config.isSmoothBiomes() ? getSmoothColor(CustomColorizer.waterColors, blockAccess, x, y, z, 3, 1) : getCustomColor(CustomColorizer.waterColors, blockAccess, x, y, z)) : (Config.isSwampColors() ? block.colorMultiplier(blockAccess, x, y, z) : 16777215));
    }
    
    private static int getCustomColor(final int[] colors, final IBlockAccess blockAccess, final int x, final int y, final int z) {
        final BiomeGenBase bgb = blockAccess.getBiomeGenForCoords(x, z);
        final double temperature = MathHelper.clamp_float(bgb.getFloatTemperature(x, y, z), 0.0f, 1.0f);
        double rainfall = MathHelper.clamp_float(bgb.getFloatRainfall(), 0.0f, 1.0f);
        rainfall *= temperature;
        final int cx = (int)((1.0 - temperature) * 255.0);
        final int cy = (int)((1.0 - rainfall) * 255.0);
        return colors[cy << 8 | cx] & 0xFFFFFF;
    }
    
    public static void updatePortalFX(final EntityFX fx) {
        if (CustomColorizer.particlePortalColor >= 0) {
            final int col = CustomColorizer.particlePortalColor;
            final int red = col >> 16 & 0xFF;
            final int green = col >> 8 & 0xFF;
            final int blue = col & 0xFF;
            final float redF = red / 255.0f;
            final float greenF = green / 255.0f;
            final float blueF = blue / 255.0f;
            fx.setRBGColorF(redF, greenF, blueF);
        }
    }
    
    public static void updateMyceliumFX(final EntityFX fx) {
        if (CustomColorizer.myceliumParticleColors != null) {
            final int col = CustomColorizer.myceliumParticleColors[CustomColorizer.random.nextInt(CustomColorizer.myceliumParticleColors.length)];
            final int red = col >> 16 & 0xFF;
            final int green = col >> 8 & 0xFF;
            final int blue = col & 0xFF;
            final float redF = red / 255.0f;
            final float greenF = green / 255.0f;
            final float blueF = blue / 255.0f;
            fx.setRBGColorF(redF, greenF, blueF);
        }
    }
    
    public static void updateReddustFX(final EntityFX fx, final IBlockAccess blockAccess, final double x, final double y, final double z) {
        if (CustomColorizer.redstoneColors != null) {
            final int level = blockAccess.getBlockMetadata((int)x, (int)y, (int)z);
            final int col = getRedstoneColor(level);
            if (col != -1) {
                final int red = col >> 16 & 0xFF;
                final int green = col >> 8 & 0xFF;
                final int blue = col & 0xFF;
                final float redF = red / 255.0f;
                final float greenF = green / 255.0f;
                final float blueF = blue / 255.0f;
                fx.setRBGColorF(redF, greenF, blueF);
            }
        }
    }
    
    public static int getRedstoneColor(final int level) {
        return (CustomColorizer.redstoneColors == null) ? -1 : ((level >= 0 && level <= 15) ? (CustomColorizer.redstoneColors[level] & 0xFFFFFF) : -1);
    }
    
    public static void updateWaterFX(final EntityFX fx, final IBlockAccess blockAccess) {
        if (CustomColorizer.waterColors != null) {
            final int x = (int)fx.posX;
            final int y = (int)fx.posY;
            final int z = (int)fx.posZ;
            final int col = getFluidColor(Blocks.water, blockAccess, x, y, z);
            final int red = col >> 16 & 0xFF;
            final int green = col >> 8 & 0xFF;
            final int blue = col & 0xFF;
            float redF = red / 255.0f;
            float greenF = green / 255.0f;
            float blueF = blue / 255.0f;
            if (CustomColorizer.particleWaterColor >= 0) {
                final int redDrop = CustomColorizer.particleWaterColor >> 16 & 0xFF;
                final int greenDrop = CustomColorizer.particleWaterColor >> 8 & 0xFF;
                final int blueDrop = CustomColorizer.particleWaterColor & 0xFF;
                redF *= redDrop / 255.0f;
                greenF *= greenDrop / 255.0f;
                blueF *= blueDrop / 255.0f;
            }
            fx.setRBGColorF(redF, greenF, blueF);
        }
    }
    
    public static int getLilypadColor() {
        return (CustomColorizer.lilyPadColor < 0) ? Blocks.waterlily.getBlockColor() : CustomColorizer.lilyPadColor;
    }
    
    public static Vec3 getFogColorNether(final Vec3 col) {
        return (CustomColorizer.fogColorNether == null) ? col : CustomColorizer.fogColorNether;
    }
    
    public static Vec3 getFogColorEnd(final Vec3 col) {
        return (CustomColorizer.fogColorEnd == null) ? col : CustomColorizer.fogColorEnd;
    }
    
    public static Vec3 getSkyColorEnd(final Vec3 col) {
        return (CustomColorizer.skyColorEnd == null) ? col : CustomColorizer.skyColorEnd;
    }
    
    public static Vec3 getSkyColor(final Vec3 skyColor3d, final IBlockAccess blockAccess, final double x, final double y, final double z) {
        if (CustomColorizer.skyColors == null) {
            return skyColor3d;
        }
        final int col = getSmoothColor(CustomColorizer.skyColors, blockAccess, x, y, z, 10, 1);
        final int red = col >> 16 & 0xFF;
        final int green = col >> 8 & 0xFF;
        final int blue = col & 0xFF;
        float redF = red / 255.0f;
        float greenF = green / 255.0f;
        float blueF = blue / 255.0f;
        final float cRed = (float)skyColor3d.xCoord / 0.5f;
        final float cGreen = (float)skyColor3d.yCoord / 0.66275f;
        final float cBlue = (float)skyColor3d.zCoord;
        redF *= cRed;
        greenF *= cGreen;
        blueF *= cBlue;
        return Vec3.createVectorHelper(redF, greenF, blueF);
    }
    
    public static Vec3 getFogColor(final Vec3 fogColor3d, final IBlockAccess blockAccess, final double x, final double y, final double z) {
        if (CustomColorizer.fogColors == null) {
            return fogColor3d;
        }
        final int col = getSmoothColor(CustomColorizer.fogColors, blockAccess, x, y, z, 10, 1);
        final int red = col >> 16 & 0xFF;
        final int green = col >> 8 & 0xFF;
        final int blue = col & 0xFF;
        float redF = red / 255.0f;
        float greenF = green / 255.0f;
        float blueF = blue / 255.0f;
        final float cRed = (float)fogColor3d.xCoord / 0.753f;
        final float cGreen = (float)fogColor3d.yCoord / 0.8471f;
        final float cBlue = (float)fogColor3d.zCoord;
        redF *= cRed;
        greenF *= cGreen;
        blueF *= cBlue;
        return Vec3.createVectorHelper(redF, greenF, blueF);
    }
    
    private static int[] readTextColors(final Properties props, final String fileName, final String prefix, final String logName) {
        final int[] colors = new int[32];
        Arrays.fill(colors, -1);
        int countColors = 0;
        final Set keys = props.keySet();
        for (final String key : keys) {
            final String value = props.getProperty(key);
            if (key.startsWith(prefix)) {
                final String name = StrUtils.removePrefix(key, prefix);
                final int code = Config.parseInt(name, -1);
                final int color = parseColor(value);
                if (code >= 0 && code < colors.length && color >= 0) {
                    colors[code] = color;
                    ++countColors;
                }
                else {
                    warn("Invalid color: " + key + " = " + value);
                }
            }
        }
        if (countColors <= 0) {
            return null;
        }
        dbg(logName + " colors: " + countColors);
        return colors;
    }
    
    public static int getTextColor(final int index, final int color) {
        if (CustomColorizer.textColors == null) {
            return color;
        }
        if (index >= 0 && index < CustomColorizer.textColors.length) {
            final int customColor = CustomColorizer.textColors[index];
            return (customColor < 0) ? color : customColor;
        }
        return color;
    }
    
    private static int parseColor(String str) {
        if (str == null) {
            return -1;
        }
        str = str.trim();
        try {
            final int e = Integer.parseInt(str, 16) & 0xFFFFFF;
            return e;
        }
        catch (NumberFormatException var2) {
            return -1;
        }
    }
    
    public static Vec3 getUnderwaterColor(final IBlockAccess blockAccess, final double x, final double y, final double z) {
        if (CustomColorizer.underwaterColors == null) {
            return null;
        }
        final int col = getSmoothColor(CustomColorizer.underwaterColors, blockAccess, x, y, z, 10, 1);
        final int red = col >> 16 & 0xFF;
        final int green = col >> 8 & 0xFF;
        final int blue = col & 0xFF;
        final float redF = red / 255.0f;
        final float greenF = green / 255.0f;
        final float blueF = blue / 255.0f;
        return Vec3.createVectorHelper(redF, greenF, blueF);
    }
    
    public static int getSmoothColor(final int[] colors, final IBlockAccess blockAccess, final double x, final double y, final double z, final int samples, final int step) {
        if (colors == null) {
            return -1;
        }
        final int x2 = (int)Math.floor(x);
        final int y2 = (int)Math.floor(y);
        final int z2 = (int)Math.floor(z);
        final int n = samples * step / 2;
        int sumRed = 0;
        int sumGreen = 0;
        int sumBlue = 0;
        int count = 0;
        for (int r = x2 - n; r <= x2 + n; r += step) {
            for (int g = z2 - n; g <= z2 + n; g += step) {
                final int b = getCustomColor(colors, blockAccess, r, y2, g);
                sumRed += (b >> 16 & 0xFF);
                sumGreen += (b >> 8 & 0xFF);
                sumBlue += (b & 0xFF);
                ++count;
            }
        }
        int r = sumRed / count;
        int g = sumGreen / count;
        final int b = sumBlue / count;
        return r << 16 | g << 8 | b;
    }
    
    public static int mixColors(final int c1, final int c2, final float w1) {
        if (w1 <= 0.0f) {
            return c2;
        }
        if (w1 >= 1.0f) {
            return c1;
        }
        final float w2 = 1.0f - w1;
        final int r1 = c1 >> 16 & 0xFF;
        final int g1 = c1 >> 8 & 0xFF;
        final int b1 = c1 & 0xFF;
        final int r2 = c2 >> 16 & 0xFF;
        final int g2 = c2 >> 8 & 0xFF;
        final int b2 = c2 & 0xFF;
        final int r3 = (int)(r1 * w1 + r2 * w2);
        final int g3 = (int)(g1 * w1 + g2 * w2);
        final int b3 = (int)(b1 * w1 + b2 * w2);
        return r3 << 16 | g3 << 8 | b3;
    }
    
    private static int averageColor(final int c1, final int c2) {
        final int r1 = c1 >> 16 & 0xFF;
        final int g1 = c1 >> 8 & 0xFF;
        final int b1 = c1 & 0xFF;
        final int r2 = c2 >> 16 & 0xFF;
        final int g2 = c2 >> 8 & 0xFF;
        final int b2 = c2 & 0xFF;
        final int r3 = (r1 + r2) / 2;
        final int g3 = (g1 + g2) / 2;
        final int b3 = (b1 + b2) / 2;
        return r3 << 16 | g3 << 8 | b3;
    }
    
    public static int getStemColorMultiplier(final BlockStem blockStem, final IBlockAccess blockAccess, final int x, final int y, final int z) {
        if (CustomColorizer.stemColors == null) {
            return blockStem.colorMultiplier(blockAccess, x, y, z);
        }
        int level = blockAccess.getBlockMetadata(x, y, z);
        if (level < 0) {
            level = 0;
        }
        if (level >= CustomColorizer.stemColors.length) {
            level = CustomColorizer.stemColors.length - 1;
        }
        return CustomColorizer.stemColors[level];
    }
    
    public static boolean updateLightmap(final World world, final float torchFlickerX, final int[] lmColors, final boolean nightvision) {
        if (world == null) {
            return false;
        }
        if (CustomColorizer.lightMapsColorsRgb == null) {
            return false;
        }
        if (!FPSBoost.LIGHTING.getValue()) {
            return false;
        }
        if (!Config.isCustomColors()) {
            return false;
        }
        final int worldType = world.provider.dimensionId;
        if (worldType < -1 || worldType > 1) {
            return false;
        }
        final int lightMapIndex = worldType + 1;
        final float[][] lightMapRgb = CustomColorizer.lightMapsColorsRgb[lightMapIndex];
        if (lightMapRgb == null) {
            return false;
        }
        final int height = CustomColorizer.lightMapsHeight[lightMapIndex];
        if (nightvision && height < 64) {
            return false;
        }
        final int width = lightMapRgb.length / height;
        if (width < 16) {
            Config.warn("Invalid lightmap width: " + width + " for: /environment/lightmap" + worldType + ".png");
            CustomColorizer.lightMapsColorsRgb[lightMapIndex] = null;
            return false;
        }
        int startIndex = 0;
        if (nightvision) {
            startIndex = width * 16 * 2;
        }
        float sun = 1.1666666f * (world.getSunBrightness(1.0f) - 0.2f);
        if (world.lastLightningBolt > 0) {
            sun = 1.0f;
        }
        sun = Config.limitTo1(sun);
        final float sunX = sun * (width - 1);
        final float torchX = Config.limitTo1(torchFlickerX + 0.5f) * (width - 1);
        final float gamma = Config.limitTo1(Config.getGameSettings().getGammaSetting());
        final boolean hasGamma = gamma > 1.0E-4f;
        getLightMapColumn(lightMapRgb, sunX, startIndex, width, CustomColorizer.sunRgbs);
        getLightMapColumn(lightMapRgb, torchX, startIndex + 16 * width, width, CustomColorizer.torchRgbs);
        final float[] rgb = new float[3];
        for (int is = 0; is < 16; ++is) {
            for (int it = 0; it < 16; ++it) {
                for (int r = 0; r < 3; ++r) {
                    float g = Config.limitTo1(CustomColorizer.sunRgbs[is][r] + CustomColorizer.torchRgbs[it][r]);
                    if (hasGamma) {
                        float b = 1.0f - g;
                        b = 1.0f - b * b * b * b;
                        g = gamma * b + (1.0f - gamma) * g;
                    }
                    rgb[r] = g;
                }
                int r = (int)(rgb[0] * 255.0f);
                final int var21 = (int)(rgb[1] * 255.0f);
                final int var22 = (int)(rgb[2] * 255.0f);
                lmColors[is * 16 + it] = (0xFF000000 | r << 16 | var21 << 8 | var22);
            }
        }
        return true;
    }
    
    private static void getLightMapColumn(final float[][] origMap, final float x, final int offset, final int width, final float[][] colRgb) {
        final int xLow = (int)Math.floor(x);
        final int xHigh = (int)Math.ceil(x);
        if (xLow == xHigh) {
            for (int var14 = 0; var14 < 16; ++var14) {
                final float[] var15 = origMap[offset + var14 * width + xLow];
                final float[] var16 = colRgb[var14];
                for (int var17 = 0; var17 < 3; ++var17) {
                    var16[var17] = var15[var17];
                }
            }
        }
        else {
            final float dLow = 1.0f - (x - xLow);
            final float dHigh = 1.0f - (xHigh - x);
            for (int y = 0; y < 16; ++y) {
                final float[] rgbLow = origMap[offset + y * width + xLow];
                final float[] rgbHigh = origMap[offset + y * width + xHigh];
                final float[] rgb = colRgb[y];
                for (int i = 0; i < 3; ++i) {
                    rgb[i] = rgbLow[i] * dLow + rgbHigh[i] * dHigh;
                }
            }
        }
    }
    
    public static Vec3 getWorldFogColor(Vec3 fogVec, final WorldClient world, final float partialTicks) {
        final int worldType = world.provider.dimensionId;
        switch (worldType) {
            case -1: {
                fogVec = getFogColorNether(fogVec);
                break;
            }
            case 0: {
                final Minecraft mc = Minecraft.getMinecraft();
                fogVec = getFogColor(fogVec, mc.theWorld, mc.renderViewEntity.posX, mc.renderViewEntity.posY + 1.0, mc.renderViewEntity.posZ);
                break;
            }
            case 1: {
                fogVec = getFogColorEnd(fogVec);
                break;
            }
        }
        return fogVec;
    }
    
    public static Vec3 getWorldSkyColor(Vec3 skyVec, final WorldClient world, final EntityLivingBase renderViewEntity, final float partialTicks) {
        final int worldType = world.provider.dimensionId;
        switch (worldType) {
            case 0: {
                final Minecraft mc = Minecraft.getMinecraft();
                skyVec = getSkyColor(skyVec, mc.theWorld, mc.renderViewEntity.posX, mc.renderViewEntity.posY + 1.0, mc.renderViewEntity.posZ);
                break;
            }
            case 1: {
                skyVec = getSkyColorEnd(skyVec);
                break;
            }
        }
        return skyVec;
    }
    
    private static void dbg(final String str) {
        Config.dbg("CustomColors: " + str);
    }
    
    private static void warn(final String str) {
        Config.warn("CustomColors: " + str);
    }
    
    static {
        CustomColorizer.grassColors = null;
        CustomColorizer.waterColors = null;
        CustomColorizer.foliageColors = null;
        CustomColorizer.foliagePineColors = null;
        CustomColorizer.foliageBirchColors = null;
        CustomColorizer.swampFoliageColors = null;
        CustomColorizer.swampGrassColors = null;
        CustomColorizer.blockPalettes = null;
        CustomColorizer.paletteColors = null;
        CustomColorizer.skyColors = null;
        CustomColorizer.fogColors = null;
        CustomColorizer.underwaterColors = null;
        CustomColorizer.lightMapsColorsRgb = null;
        CustomColorizer.lightMapsHeight = null;
        CustomColorizer.sunRgbs = new float[16][3];
        CustomColorizer.torchRgbs = new float[16][3];
        CustomColorizer.redstoneColors = null;
        CustomColorizer.stemColors = null;
        CustomColorizer.myceliumParticleColors = null;
        CustomColorizer.useDefaultColorMultiplier = true;
        CustomColorizer.particleWaterColor = -1;
        CustomColorizer.particlePortalColor = -1;
        CustomColorizer.lilyPadColor = -1;
        CustomColorizer.fogColorNether = null;
        CustomColorizer.fogColorEnd = null;
        CustomColorizer.skyColorEnd = null;
        CustomColorizer.textColors = null;
        CustomColorizer.random = new Random();
    }
}
