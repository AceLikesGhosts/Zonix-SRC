package net.minecraft.client.renderer.texture;

import com.google.common.collect.*;
import net.minecraft.client.*;
import javax.imageio.*;
import net.minecraft.client.resources.data.*;
import java.util.concurrent.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.*;
import net.minecraft.crash.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.item.*;
import net.minecraft.src.*;
import net.minecraft.util.*;
import java.util.*;
import java.awt.*;
import java.io.*;
import java.awt.image.*;
import org.apache.logging.log4j.*;

public class TextureMap extends AbstractTexture implements ITickableTextureObject, IIconRegister
{
    private static final Logger logger;
    public static final ResourceLocation locationBlocksTexture;
    public static final ResourceLocation locationItemsTexture;
    private final List listAnimatedSprites;
    private final Map mapRegisteredSprites;
    private final Map mapUploadedSprites;
    public final int textureType;
    public final String basePath;
    private int field_147636_j;
    private int field_147637_k;
    private final TextureAtlasSprite missingImage;
    public static TextureMap textureMapBlocks;
    public static TextureMap textureMapItems;
    private TextureAtlasSprite[] iconGrid;
    private int iconGridSize;
    private int iconGridCountX;
    private int iconGridCountY;
    private double iconGridSizeU;
    private double iconGridSizeV;
    private static final boolean ENABLE_SKIP;
    private boolean skipFirst;
    private String[] cancelPack;
    private String[] blackList;
    
    public TextureMap(final int par1, final String par2Str) {
        this(par1, par2Str, false);
    }
    
    public TextureMap(final int par1, final String par2Str, final boolean skipFirst) {
        this.cancelPack = new String[] { "cobblestone", "stone", "grass_side", "grass_top" };
        this.blackList = new String[] { "bedrock", "bookshelf", "brick", "clay", "coal_block", "coal_ore", "cobblestone", "cobblestone_mossy", "command_block", "crafting_table_front", "crafting_table_side", "crafting_table_top", " diamond_block", "diamond_ore", "dirt", "dirt_podzol_side", "dirt_podzol_top", "dispenser_front_horizontal", "dispenser_front_vertical", "dropper_front_horizontal", "dropper_front_vertical", "emerald_block", "emerald_ore", "end_stone", "furnace_front_off", " furnace_front_on", "furnace_side", "furnace_top", "glowstone", "gold_block", "gold_ore", "grass_side", "grass_side_snowed", "grass_top", "hardened_clay", "hardened_clay_stained_black", "hardened_clay_stained_blue", "hardened_clay_stained_brown", "hardened_clay_stained_cyan", "hardened_clay_stained_gray", "hardened_clay_stained_green", "hardened_clay_stained_light_blue", "hardened_clay_stained_lime", "hardened_clay_stained_magenta", "hardened_clay_stained_orange", "hardened_clay_stained_pink", "hardened_clay_stained_purple", "hardened_clay_stained_red", "hardened_clay_stained_silver", "hardened_clay_stained_white", "hardened_clay_stained_yellow", "hay_block_top", "hay_block_side", "ice_packed", "iron_block", "iron_ore", "jukebox_side", "jukebox_top", "lapis_block", "lapis_ore", "leaves_acacia_opaque", "leaves_big_oak_opaque", "leaves_birch_opaque", "leaves_jungle_opaque", "leaves_oak_opaque", "leaves_spruce_opaque", "log_acacia", "log_big_oak", "log_birch", "log_jugle", "log_oak", "log_spruce", "log_acacia_top", "log_big_oak_top", "log_birch_top", "log_jugle_top", "log_oak_top", "log_spruce_top", "melon_side", "melon_top", "mushroom_block_inside", "mushroom_block_skin_brown", "mushroom_block_skin_red", "mushroom_block_skin_stem", "mycelium_side", "mycelium_top", "nether_brick", "netherrack", "noteblock", "obsidian", "planks_acacia", "planks_big_oak", "planks_birch", "planks_jungle", "planks_oak", "planks_spruce", "pumpkin_side", "pumpkin_top", "quartz_block_bottom", "quartz_block_chiseled", "quartz_block_chiseled_top", "quartz_block_lines", "quartz_block_lines_top", "quartz_block_side", "quartz_block_top", "quartz_ore", "red_sand", "redstone_block", "redstone_lamp_off", "redstone_lamp_on", "redstone_ore", "sand", "sandstone_bottom", "sandstone_carved", "sandstone_normal", "sandstone_top", "snow", "soul_sand", "sponge", "stone", "stone_slab_side", "stone_slab_top", "stonebrick", "stonebrick_carved", "stonebrick_cracked", "stonebrick_mossy", "tnt_bottom", "tnt_side", "tnt_top", "wool_colored_black", "wool_colored_blue", "wool_colored_brown", "wool_colored_cyan", "wool_colored_gray", "wool_colored_green", "wool_colored_light_blue", "wool_colored_lime", "wool_colored_magenta", "wool_colored_orange", "wool_colored_pink", "wool_colored_purple", "wool_colored_red", "wool_colored_silver", "wool_colored_white", "wool_colored_yellow" };
        this.listAnimatedSprites = Lists.newArrayList();
        this.mapRegisteredSprites = Maps.newHashMap();
        this.mapUploadedSprites = Maps.newHashMap();
        this.field_147637_k = 1;
        this.missingImage = new TextureAtlasSprite("missingno");
        this.iconGrid = null;
        this.iconGridSize = -1;
        this.iconGridCountX = -1;
        this.iconGridCountY = -1;
        this.iconGridSizeU = -1.0;
        this.iconGridSizeV = -1.0;
        this.skipFirst = false;
        this.textureType = par1;
        this.basePath = par2Str;
        if (this.textureType == 0) {
            TextureMap.textureMapBlocks = this;
        }
        if (this.textureType == 1) {
            TextureMap.textureMapItems = this;
        }
        this.registerIcons();
        this.skipFirst = (skipFirst && TextureMap.ENABLE_SKIP);
    }
    
    private void initMissingImage() {
        int[] var8;
        if (this.field_147637_k > 1.0f) {
            final boolean var5 = true;
            final boolean var6 = true;
            final boolean var7 = true;
            this.missingImage.setIconWidth(32);
            this.missingImage.setIconHeight(32);
            var8 = new int[1024];
            System.arraycopy(TextureUtil.missingTextureData, 0, var8, 0, TextureUtil.missingTextureData.length);
            TextureUtil.func_147948_a(var8, 16, 16, 8);
        }
        else {
            var8 = TextureUtil.missingTextureData;
            this.missingImage.setIconWidth(16);
            this.missingImage.setIconHeight(16);
        }
        final int[][] var9 = new int[this.field_147636_j + 1][];
        var9[0] = var8;
        this.missingImage.setFramesTextureData(Lists.newArrayList((Object[])new int[][][] { var9 }));
        this.missingImage.setIndexInMap(0);
    }
    
    @Override
    public void loadTexture(final IResourceManager par1ResourceManager) throws IOException {
        this.initMissingImage();
        this.func_147631_c();
        this.loadTextureAtlas(par1ResourceManager);
    }
    
    private boolean check(final String blockname) {
        for (final String s : this.blackList) {
            if (blockname.equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean checkCancel(final String blockName) {
        for (final String s : this.cancelPack) {
            if (blockName.equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean hasTransparency(final BufferedImage img) {
        final int width = img.getWidth();
        final int height = img.getHeight();
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                final int rgb = img.getRGB(x, y);
                final int alpha = rgb >> 24 & 0xFF;
                if (alpha != 255) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void loadTextureAtlas(final IResourceManager par1ResourceManager) {
        Config.dbg("Loading texture map: " + this.basePath);
        WrUpdates.finishCurrentUpdate();
        this.registerIcons();
        final int var2 = Minecraft.getGLMaximumTextureSize();
        final Stitcher var3 = new Stitcher(var2, var2, true, 0, this.field_147636_j);
        this.mapUploadedSprites.clear();
        this.listAnimatedSprites.clear();
        int var4 = Integer.MAX_VALUE;
        final Iterator var5 = this.mapRegisteredSprites.entrySet().iterator();
        while (var5.hasNext() && !this.skipFirst) {
            final Map.Entry var6 = var5.next();
            final ResourceLocation var7 = new ResourceLocation(var6.getKey());
            final TextureAtlasSprite var8 = var6.getValue();
            final ResourceLocation sheetWidth = this.func_147634_a(var7, 0);
            if (var8.hasCustomLoader(par1ResourceManager, var7)) {
                if (!var8.load(par1ResourceManager, var7)) {
                    var4 = Math.min(var4, Math.min(var8.getIconWidth(), var8.getIconHeight()));
                    var3.addSprite(var8);
                }
                Config.dbg("Custom loader: " + var8);
            }
            else {
                try {
                    final IResource sheetHeight = par1ResourceManager.getResource(sheetWidth);
                    final BufferedImage[] debugImage = new BufferedImage[1 + this.field_147636_j];
                    debugImage[0] = ImageIO.read(sheetHeight.getInputStream());
                    final boolean transparency = hasTransparency(debugImage[0]);
                    var8.setHasTransparency(transparency);
                    if (transparency && this.check(var6.getKey())) {
                        final int width = debugImage[0].getWidth();
                        final int height = debugImage[0].getHeight();
                        final boolean checkCancel = this.checkCancel(var6.getKey());
                        for (int x = 0; x < width; ++x) {
                            for (int y = 0; y < height; ++y) {
                                final int rgb = debugImage[0].getRGB(x, y);
                                final int alpha = rgb >> 24 & 0xFF;
                                if (alpha != 255) {
                                    if (checkCancel) {
                                        final Minecraft mc = Minecraft.getMinecraft();
                                        mc.getResourcePackRepository().func_148527_a(Lists.newArrayList());
                                        mc.gameSettings.resourcePacks.clear();
                                        mc.gameSettings.saveOptions();
                                        mc.refreshResources();
                                        return;
                                    }
                                    debugImage[0].setRGB(x, y, new Color(0, 0, 0).getRGB());
                                }
                            }
                        }
                    }
                    final TextureMetadataSection var9 = (TextureMetadataSection)sheetHeight.getMetadata("texture");
                    if (var9 != null) {
                        final List var10 = var9.func_148535_c();
                        if (!var10.isEmpty()) {
                            final int var11 = debugImage[0].getWidth();
                            final int var12 = debugImage[0].getHeight();
                            if (MathHelper.roundUpToPowerOfTwo(var11) != var11 || MathHelper.roundUpToPowerOfTwo(var12) != var12) {
                                throw new RuntimeException("Unable to load extra miplevels, source-texture is not power of two");
                            }
                        }
                        final Iterator var13 = var10.iterator();
                        while (var13.hasNext()) {
                            final int var12 = var13.next();
                            if (var12 > 0 && var12 < debugImage.length - 1 && debugImage[var12] == null) {
                                final ResourceLocation var14 = this.func_147634_a(var7, var12);
                                try {
                                    debugImage[var12] = ImageIO.read(par1ResourceManager.getResource(var14).getInputStream());
                                }
                                catch (IOException var15) {
                                    TextureMap.logger.error("Unable to load miplevel {} from: {}", new Object[] { var12, var14, var15 });
                                }
                            }
                        }
                    }
                    final AnimationMetadataSection var16 = (AnimationMetadataSection)sheetHeight.getMetadata("animation");
                    var8.func_147964_a(debugImage, var16, this.field_147637_k > 1.0f);
                }
                catch (RuntimeException var17) {
                    TextureMap.logger.error("Unable to parse metadata from " + sheetWidth, (Throwable)var17);
                    continue;
                }
                catch (IOException var18) {
                    TextureMap.logger.error("Using missing texture, unable to load " + sheetWidth + ", " + var18.getClass().getName());
                    continue;
                }
                var4 = Math.min(var4, Math.min(var8.getIconWidth(), var8.getIconHeight()));
                var3.addSprite(var8);
            }
        }
        int var19 = MathHelper.calculateLogBaseTwo(var4);
        if (var19 < 0) {
            var19 = 0;
        }
        if (var19 < this.field_147636_j) {
            TextureMap.logger.info("{}: dropping miplevel from {} to {}, because of minTexel: {}", new Object[] { this.basePath, this.field_147636_j, var19, var4 });
            this.field_147636_j = var19;
        }
        final Iterator var20 = this.mapRegisteredSprites.values().iterator();
        while (var20.hasNext() && !this.skipFirst) {
            final TextureAtlasSprite sheetWidth2 = var20.next();
            try {
                sheetWidth2.func_147963_d(this.field_147636_j);
            }
            catch (Throwable var21) {
                final CrashReport debugImage2 = CrashReport.makeCrashReport(var21, "Applying mipmap");
                final CrashReportCategory var22 = debugImage2.makeCategory("Sprite being mipmapped");
                var22.addCrashSectionCallable("Sprite name", new Callable() {
                    @Override
                    public String call() {
                        return sheetWidth2.getIconName();
                    }
                });
                var22.addCrashSectionCallable("Sprite size", new Callable() {
                    @Override
                    public String call() {
                        return sheetWidth2.getIconWidth() + " x " + sheetWidth2.getIconHeight();
                    }
                });
                var22.addCrashSectionCallable("Sprite frames", new Callable() {
                    @Override
                    public String call() {
                        return sheetWidth2.getFrameCount() + " frames";
                    }
                });
                var22.addCrashSection("Mipmap levels", this.field_147636_j);
                throw new ReportedException(debugImage2);
            }
        }
        this.missingImage.func_147963_d(this.field_147636_j);
        var3.addSprite(this.missingImage);
        this.skipFirst = false;
        try {
            var3.doStitch();
        }
        catch (StitcherException var23) {
            throw var23;
        }
        Config.dbg("Texture size: " + this.basePath + ", " + var3.getCurrentWidth() + "x" + var3.getCurrentHeight());
        final int sheetWidth3 = var3.getCurrentWidth();
        final int sheetHeight2 = var3.getCurrentHeight();
        BufferedImage debugImage3 = null;
        if (System.getProperty("saveTextureMap", "false").equalsIgnoreCase("true")) {
            debugImage3 = this.makeDebugImage(sheetWidth3, sheetHeight2);
        }
        TextureMap.logger.info("Created: {}x{} {}-atlas", new Object[] { var3.getCurrentWidth(), var3.getCurrentHeight(), this.basePath });
        TextureUtil.func_147946_a(this.getGlTextureId(), this.field_147636_j, var3.getCurrentWidth(), var3.getCurrentHeight(), (float)this.field_147637_k);
        final HashMap var24 = Maps.newHashMap(this.mapRegisteredSprites);
        Iterator var25 = var3.getStichSlots().iterator();
        while (var25.hasNext()) {
            final TextureAtlasSprite var8 = var25.next();
            final String var26 = var8.getIconName();
            var24.remove(var26);
            this.mapUploadedSprites.put(var26, var8);
            try {
                TextureUtil.func_147955_a(var8.func_147965_a(0), var8.getIconWidth(), var8.getIconHeight(), var8.getOriginX(), var8.getOriginY(), false, false);
                if (debugImage3 != null) {
                    this.addDebugSprite(var8, debugImage3);
                }
            }
            catch (Throwable var28) {
                final CrashReport var27 = CrashReport.makeCrashReport(var28, "Stitching texture atlas");
                final CrashReportCategory var29 = var27.makeCategory("Texture being stitched together");
                var29.addCrashSection("Atlas path", this.basePath);
                var29.addCrashSection("Sprite", var8);
                throw new ReportedException(var27);
            }
            if (var8.hasAnimationMetadata()) {
                this.listAnimatedSprites.add(var8);
            }
            else {
                var8.clearFramesTextureData();
            }
        }
        var25 = var24.values().iterator();
        while (var25.hasNext()) {
            final TextureAtlasSprite var8 = var25.next();
            var8.copyFrom(this.missingImage);
        }
        if (debugImage3 != null) {
            this.writeDebugImage(debugImage3, "debug_" + this.basePath.replace('/', '_') + ".png");
        }
    }
    
    private ResourceLocation func_147634_a(final ResourceLocation p_147634_1_, final int p_147634_2_) {
        return this.isAbsoluteLocation(p_147634_1_) ? ((p_147634_2_ == 0) ? new ResourceLocation(p_147634_1_.getResourceDomain(), p_147634_1_.getResourcePath() + ".png") : new ResourceLocation(p_147634_1_.getResourceDomain(), p_147634_1_.getResourcePath() + "mipmap" + p_147634_2_ + ".png")) : ((p_147634_2_ == 0) ? new ResourceLocation(p_147634_1_.getResourceDomain(), String.format("%s/%s%s", this.basePath, p_147634_1_.getResourcePath(), ".png")) : new ResourceLocation(p_147634_1_.getResourceDomain(), String.format("%s/mipmaps/%s.%d%s", this.basePath, p_147634_1_.getResourcePath(), p_147634_2_, ".png")));
    }
    
    private void registerIcons() {
        this.mapRegisteredSprites.clear();
        if (this.textureType == 0) {
            for (final Block var2 : Block.blockRegistry) {
                if (var2.getMaterial() != Material.air) {
                    var2.registerBlockIcons(this);
                }
            }
            Minecraft.getMinecraft().renderGlobal.registerDestroyBlockIcons(this);
            RenderManager.instance.updateIcons(this);
            ConnectedTextures.updateIcons(this);
        }
        if (this.textureType == 1) {
            CustomItems.updateIcons(this);
        }
        for (final Item var3 : Item.itemRegistry) {
            if (var3 != null && var3.getSpriteNumber() == this.textureType) {
                var3.registerIcons(this);
            }
        }
    }
    
    public TextureAtlasSprite getAtlasSprite(final String par1Str) {
        TextureAtlasSprite var2 = this.mapUploadedSprites.get(par1Str);
        if (var2 == null) {
            var2 = this.missingImage;
        }
        return var2;
    }
    
    public void updateAnimations() {
        TextureUtil.bindTexture(this.getGlTextureId());
        for (final TextureAtlasSprite var2 : this.listAnimatedSprites) {
            if (this.textureType == 0) {
                if (!this.isTerrainAnimationActive(var2)) {
                    continue;
                }
            }
            else if (this.textureType == 1 && !this.isItemAnimationActive(var2)) {
                continue;
            }
            var2.updateAnimation();
        }
    }
    
    private boolean isItemAnimationActive(final TextureAtlasSprite ts) {
        return ts == TextureUtils.iconClock || ts == TextureUtils.iconCompass || Config.isAnimatedItems();
    }
    
    @Override
    public IIcon registerIcon(final String par1Str) {
        if (par1Str == null) {
            throw new IllegalArgumentException("Name cannot be null!");
        }
        if (par1Str.indexOf(92) != -1 && !this.isAbsoluteLocationPath(par1Str)) {
            throw new IllegalArgumentException("Name cannot contain slashes!");
        }
        Object var2 = this.mapRegisteredSprites.get(par1Str);
        if (var2 == null) {
            if (this.textureType == 1) {
                if ("clock".equals(par1Str)) {
                    var2 = new TextureClock(par1Str);
                }
                else if ("compass".equals(par1Str)) {
                    var2 = new TextureCompass(par1Str);
                }
                else {
                    var2 = new TextureAtlasSprite(par1Str);
                }
            }
            else {
                var2 = new TextureAtlasSprite(par1Str);
            }
            this.mapRegisteredSprites.put(par1Str, var2);
            if (var2 instanceof TextureAtlasSprite) {
                final TextureAtlasSprite tas = (TextureAtlasSprite)var2;
                tas.setIndexInMap(this.mapRegisteredSprites.size());
            }
        }
        return (IIcon)var2;
    }
    
    public int getTextureType() {
        return this.textureType;
    }
    
    @Override
    public void tick() {
        this.updateAnimations();
    }
    
    public void func_147633_a(final int p_147633_1_) {
        this.field_147636_j = p_147633_1_;
    }
    
    public void func_147632_b(final int p_147632_1_) {
        this.field_147637_k = p_147632_1_;
    }
    
    public TextureAtlasSprite getTextureExtry(final String name) {
        return this.mapRegisteredSprites.get(name);
    }
    
    public boolean setTextureEntry(final String name, final TextureAtlasSprite entry) {
        if (!this.mapRegisteredSprites.containsKey(name)) {
            this.mapRegisteredSprites.put(name, entry);
            entry.setIndexInMap(this.mapRegisteredSprites.size());
            return true;
        }
        return false;
    }
    
    private boolean isAbsoluteLocation(final ResourceLocation loc) {
        final String path = loc.getResourcePath();
        return this.isAbsoluteLocationPath(path);
    }
    
    private boolean isAbsoluteLocationPath(final String resPath) {
        final String path = resPath.toLowerCase();
        return path.startsWith("mcpatcher/") || path.startsWith("optifine/");
    }
    
    public TextureAtlasSprite getIconSafe(final String name) {
        return this.mapRegisteredSprites.get(name);
    }
    
    private int getStandardTileSize(final Collection icons) {
        final int[] sizeCounts = new int[16];
        for (final TextureAtlasSprite mostUsedCount : icons) {
            if (mostUsedCount != null) {
                final int value = TextureUtils.getPowerOfTwo(mostUsedCount.getWidth());
                final int count = TextureUtils.getPowerOfTwo(mostUsedCount.getHeight());
                final int po2 = Math.max(value, count);
                if (po2 >= sizeCounts.length) {
                    continue;
                }
                final int[] array = sizeCounts;
                final int n = po2;
                ++array[n];
            }
        }
        int var8 = 4;
        int var9 = 0;
        for (int value = 0; value < sizeCounts.length; ++value) {
            final int count = sizeCounts[value];
            if (count > var9) {
                var8 = value;
                var9 = count;
            }
        }
        if (var8 < 4) {
            var8 = 4;
        }
        int value = TextureUtils.twoToPower(var8);
        return value;
    }
    
    private void updateIconGrid(final int sheetWidth, final int sheetHeight) {
        this.iconGridCountX = -1;
        this.iconGridCountY = -1;
        this.iconGrid = null;
        if (this.iconGridSize > 0) {
            this.iconGridCountX = sheetWidth / this.iconGridSize;
            this.iconGridCountY = sheetHeight / this.iconGridSize;
            this.iconGrid = new TextureAtlasSprite[this.iconGridCountX * this.iconGridCountY];
            this.iconGridSizeU = 1.0 / this.iconGridCountX;
            this.iconGridSizeV = 1.0 / this.iconGridCountY;
            for (final TextureAtlasSprite ts : this.mapUploadedSprites.values()) {
                final double deltaU = 0.5 / sheetWidth;
                final double deltaV = 0.5 / sheetHeight;
                final double uMin = Math.min(ts.getMinU(), ts.getMaxU()) + deltaU;
                final double vMin = Math.min(ts.getMinV(), ts.getMaxV()) + deltaV;
                final double uMax = Math.max(ts.getMinU(), ts.getMaxU()) - deltaU;
                final double vMax = Math.max(ts.getMinV(), ts.getMaxV()) - deltaV;
                final int iuMin = (int)(uMin / this.iconGridSizeU);
                final int ivMin = (int)(vMin / this.iconGridSizeV);
                final int iuMax = (int)(uMax / this.iconGridSizeU);
                final int ivMax = (int)(vMax / this.iconGridSizeV);
                for (int iu = iuMin; iu <= iuMax; ++iu) {
                    if (iu >= 0 && iu < this.iconGridCountX) {
                        for (int iv = ivMin; iv <= ivMax; ++iv) {
                            if (iv >= 0 && iv < this.iconGridCountX) {
                                final int index = iv * this.iconGridCountX + iu;
                                this.iconGrid[index] = ts;
                            }
                            else {
                                Config.warn("Invalid grid V: " + iv + ", icon: " + ts.getIconName());
                            }
                        }
                    }
                    else {
                        Config.warn("Invalid grid U: " + iu + ", icon: " + ts.getIconName());
                    }
                }
            }
        }
    }
    
    public TextureAtlasSprite getIconByUV(final double u, final double v) {
        if (this.iconGrid == null) {
            return null;
        }
        final int iu = (int)(u / this.iconGridSizeU);
        final int iv = (int)(v / this.iconGridSizeV);
        final int index = iv * this.iconGridCountX + iu;
        return (index >= 0 && index <= this.iconGrid.length) ? this.iconGrid[index] : null;
    }
    
    public TextureAtlasSprite getMissingSprite() {
        return this.missingImage;
    }
    
    public int getMaxTextureIndex() {
        return this.mapRegisteredSprites.size();
    }
    
    private boolean isTerrainAnimationActive(final TextureAtlasSprite ts) {
        return (ts != TextureUtils.iconWaterStill && ts != TextureUtils.iconWaterFlow) ? ((ts != TextureUtils.iconLavaStill && ts != TextureUtils.iconLavaFlow) ? ((ts != TextureUtils.iconFireLayer0 && ts != TextureUtils.iconFireLayer1) ? ((ts == TextureUtils.iconPortal) ? Config.isAnimatedPortal() : Config.isAnimatedTerrain()) : Config.isAnimatedFire()) : Config.isAnimatedLava()) : Config.isAnimatedWater();
    }
    
    public void loadTextureSafe(final IResourceManager rm) {
        try {
            this.loadTexture(rm);
        }
        catch (IOException var3) {
            Config.warn("Error loading texture map: " + this.basePath);
            var3.printStackTrace();
        }
    }
    
    private BufferedImage makeDebugImage(final int sheetWidth, final int sheetHeight) {
        final BufferedImage image = new BufferedImage(sheetWidth, sheetHeight, 2);
        final Graphics2D g = image.createGraphics();
        g.setPaint(new Color(255, 255, 0));
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        return image;
    }
    
    private void addDebugSprite(final TextureAtlasSprite ts, final BufferedImage image) {
        if (ts.getFrameCount() < 1) {
            Config.warn("Debug sprite has no data: " + ts.getIconName());
        }
        else {
            final int[] data = ts.func_147965_a(0)[0];
            image.setRGB(ts.getOriginX(), ts.getOriginY(), ts.getIconWidth(), ts.getIconHeight(), data, 0, ts.getIconWidth());
        }
    }
    
    private void writeDebugImage(final BufferedImage image, final String pngPath) {
        try {
            ImageIO.write(image, "png", new File(Config.getMinecraft().mcDataDir, pngPath));
        }
        catch (Exception var4) {
            var4.printStackTrace();
        }
    }
    
    static {
        logger = LogManager.getLogger();
        locationBlocksTexture = new ResourceLocation("textures/atlas/blocks.png");
        locationItemsTexture = new ResourceLocation("textures/atlas/items.png");
        TextureMap.textureMapBlocks = null;
        TextureMap.textureMapItems = null;
        ENABLE_SKIP = Boolean.parseBoolean(System.getProperty("fml.skipFirstTextureLoad", "true"));
    }
}
