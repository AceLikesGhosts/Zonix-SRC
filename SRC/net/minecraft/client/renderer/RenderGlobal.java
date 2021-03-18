package net.minecraft.client.renderer;

import net.minecraft.client.multiplayer.*;
import net.minecraft.client.*;
import java.nio.*;
import com.google.common.collect.*;
import org.lwjgl.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.*;
import net.minecraft.block.*;
import us.zonix.client.module.impl.*;
import net.minecraft.profiler.*;
import net.minecraft.client.util.*;
import java.util.*;
import net.minecraft.src.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.client.audio.*;
import java.util.concurrent.*;
import net.minecraft.crash.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.client.particle.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.texture.*;
import org.lwjgl.input.*;
import org.apache.logging.log4j.*;

public class RenderGlobal implements IWorldAccess
{
    private static final Logger logger;
    private static final ResourceLocation locationMoonPhasesPng;
    private static final ResourceLocation locationSunPng;
    private static final ResourceLocation locationCloudsPng;
    private static final ResourceLocation locationEndSkyPng;
    public List tileEntities;
    public WorldClient theWorld;
    public final TextureManager renderEngine;
    public CompactArrayList worldRenderersToUpdate;
    private WorldRenderer[] sortedWorldRenderers;
    private WorldRenderer[] worldRenderers;
    private int renderChunksWide;
    private int renderChunksTall;
    private int renderChunksDeep;
    public int glRenderListBase;
    public Minecraft mc;
    public RenderBlocks renderBlocksRg;
    private IntBuffer glOcclusionQueryBase;
    private boolean occlusionEnabled;
    private int cloudTickCounter;
    private int starGLCallList;
    private int glSkyList;
    private int glSkyList2;
    private int minBlockX;
    private int minBlockY;
    private int minBlockZ;
    private int maxBlockX;
    private int maxBlockY;
    private int maxBlockZ;
    public final Map damagedBlocks;
    private final Map mapSoundPositions;
    private IIcon[] destroyBlockIcons;
    private boolean displayListEntitiesDirty;
    private int displayListEntities;
    private int renderDistanceChunks;
    private int renderEntitiesStartupCounter;
    private int countEntitiesTotal;
    private int countEntitiesRendered;
    private int countEntitiesHidden;
    IntBuffer occlusionResult;
    private int renderersLoaded;
    private int renderersBeingClipped;
    private int renderersBeingOccluded;
    private int renderersBeingRendered;
    private int renderersSkippingRenderPass;
    private int dummyRenderInt;
    private int worldRenderersCheckIndex;
    private List glRenderLists;
    private RenderList[] allRenderLists;
    double prevSortX;
    double prevSortY;
    double prevSortZ;
    double prevRenderSortX;
    double prevRenderSortY;
    double prevRenderSortZ;
    int prevChunkSortX;
    int prevChunkSortY;
    int prevChunkSortZ;
    int frustumCheckOffset;
    private IntBuffer glListBuffer;
    double prevReposX;
    double prevReposY;
    double prevReposZ;
    public Entity renderedEntity;
    private int glListClouds;
    private boolean isFancyGlListClouds;
    private int cloudTickCounterGlList;
    private double cloudPlayerX;
    private double cloudPlayerY;
    private double cloudPlayerZ;
    private int countSortedWorldRenderers;
    private int effectivePreloadedChunks;
    private int vertexResortCounter;
    public WrDisplayListAllocator displayListAllocator;
    public EntityLivingBase renderViewEntity;
    private int countTileEntitiesRendered;
    private long lastMovedTime;
    private long lastActionTime;
    private static AxisAlignedBB AABB_INFINITE;
    
    public RenderGlobal(final Minecraft par1Minecraft) {
        this.tileEntities = new ArrayList();
        this.worldRenderersToUpdate = new CompactArrayList(100, 0.8f);
        this.damagedBlocks = new HashMap();
        this.mapSoundPositions = Maps.newHashMap();
        this.renderDistanceChunks = -1;
        this.renderEntitiesStartupCounter = 2;
        this.occlusionResult = GLAllocation.createDirectIntBuffer(64);
        this.glRenderLists = new ArrayList();
        this.allRenderLists = new RenderList[] { new RenderList(), new RenderList(), new RenderList(), new RenderList() };
        this.prevSortX = -9999.0;
        this.prevSortY = -9999.0;
        this.prevSortZ = -9999.0;
        this.prevRenderSortX = -9999.0;
        this.prevRenderSortY = -9999.0;
        this.prevRenderSortZ = -9999.0;
        this.prevChunkSortX = -999;
        this.prevChunkSortY = -999;
        this.prevChunkSortZ = -999;
        this.glListBuffer = BufferUtils.createIntBuffer(65536);
        this.glListClouds = -1;
        this.isFancyGlListClouds = false;
        this.cloudTickCounterGlList = -99999;
        this.cloudPlayerX = 0.0;
        this.cloudPlayerY = 0.0;
        this.cloudPlayerZ = 0.0;
        this.countSortedWorldRenderers = 0;
        this.effectivePreloadedChunks = 0;
        this.vertexResortCounter = 30;
        this.displayListAllocator = new WrDisplayListAllocator();
        this.lastMovedTime = System.currentTimeMillis();
        this.lastActionTime = System.currentTimeMillis();
        this.glListClouds = GLAllocation.generateDisplayLists(1);
        this.mc = par1Minecraft;
        this.renderEngine = par1Minecraft.getTextureManager();
        final byte maxChunkDim = 65;
        final byte maxChunkHeight = 16;
        final int countWorldRenderers = maxChunkDim * maxChunkDim * maxChunkHeight;
        final int countStandardRenderLists = countWorldRenderers * 3;
        this.glRenderListBase = GLAllocation.generateDisplayLists(countStandardRenderLists);
        this.displayListEntitiesDirty = false;
        this.displayListEntities = GLAllocation.generateDisplayLists(1);
        this.occlusionEnabled = OpenGlCapsChecker.checkARBOcclusion();
        if (this.occlusionEnabled) {
            this.occlusionResult.clear();
            (this.glOcclusionQueryBase = GLAllocation.createDirectIntBuffer(maxChunkDim * maxChunkDim * maxChunkHeight)).clear();
            this.glOcclusionQueryBase.position(0);
            this.glOcclusionQueryBase.limit(maxChunkDim * maxChunkDim * maxChunkHeight);
            ARBOcclusionQuery.glGenQueriesARB(this.glOcclusionQueryBase);
        }
        this.starGLCallList = GLAllocation.generateDisplayLists(3);
        GL11.glPushMatrix();
        GL11.glNewList(this.starGLCallList, 4864);
        this.renderStars();
        GL11.glEndList();
        GL11.glPopMatrix();
        final Tessellator var4 = Tessellator.instance;
        GL11.glNewList(this.glSkyList = this.starGLCallList + 1, 4864);
        final byte var5 = 64;
        final int var6 = 256 / var5 + 2;
        float var7 = 16.0f;
        for (int var8 = -var5 * var6; var8 <= var5 * var6; var8 += var5) {
            for (int var9 = -var5 * var6; var9 <= var5 * var6; var9 += var5) {
                var4.startDrawingQuads();
                var4.addVertex(var8 + 0, var7, var9 + 0);
                var4.addVertex(var8 + var5, var7, var9 + 0);
                var4.addVertex(var8 + var5, var7, var9 + var5);
                var4.addVertex(var8 + 0, var7, var9 + var5);
                var4.draw();
            }
        }
        GL11.glEndList();
        GL11.glNewList(this.glSkyList2 = this.starGLCallList + 2, 4864);
        var7 = -16.0f;
        var4.startDrawingQuads();
        for (int var8 = -var5 * var6; var8 <= var5 * var6; var8 += var5) {
            for (int var9 = -var5 * var6; var9 <= var5 * var6; var9 += var5) {
                var4.addVertex(var8 + var5, var7, var9 + 0);
                var4.addVertex(var8 + 0, var7, var9 + 0);
                var4.addVertex(var8 + 0, var7, var9 + var5);
                var4.addVertex(var8 + var5, var7, var9 + var5);
            }
        }
        var4.draw();
        GL11.glEndList();
    }
    
    private void renderStars() {
        final Random var1 = new Random(10842L);
        final Tessellator var2 = Tessellator.instance;
        var2.startDrawingQuads();
        for (int var3 = 0; var3 < 1500; ++var3) {
            double var4 = var1.nextFloat() * 2.0f - 1.0f;
            double var5 = var1.nextFloat() * 2.0f - 1.0f;
            double var6 = var1.nextFloat() * 2.0f - 1.0f;
            final double var7 = 0.15f + var1.nextFloat() * 0.1f;
            double var8 = var4 * var4 + var5 * var5 + var6 * var6;
            if (var8 < 1.0 && var8 > 0.01) {
                var8 = 1.0 / Math.sqrt(var8);
                var4 *= var8;
                var5 *= var8;
                var6 *= var8;
                final double var9 = var4 * 100.0;
                final double var10 = var5 * 100.0;
                final double var11 = var6 * 100.0;
                final double var12 = Math.atan2(var4, var6);
                final double var13 = Math.sin(var12);
                final double var14 = Math.cos(var12);
                final double var15 = Math.atan2(Math.sqrt(var4 * var4 + var6 * var6), var5);
                final double var16 = Math.sin(var15);
                final double var17 = Math.cos(var15);
                final double var18 = var1.nextDouble() * 3.141592653589793 * 2.0;
                final double var19 = Math.sin(var18);
                final double var20 = Math.cos(var18);
                for (int var21 = 0; var21 < 4; ++var21) {
                    final double var22 = 0.0;
                    final double var23 = ((var21 & 0x2) - 1) * var7;
                    final double var24 = ((var21 + 1 & 0x2) - 1) * var7;
                    final double var25 = var23 * var20 - var24 * var19;
                    final double var26 = var24 * var20 + var23 * var19;
                    final double var27 = var25 * var16 + var22 * var17;
                    final double var28 = var22 * var16 - var25 * var17;
                    final double var29 = var28 * var13 - var26 * var14;
                    final double var30 = var26 * var13 + var28 * var14;
                    var2.addVertex(var9 + var29, var10 + var27, var11 + var30);
                }
            }
        }
        var2.draw();
    }
    
    public void setWorldAndLoadRenderers(final WorldClient par1WorldClient) {
        if (this.theWorld != null) {
            this.theWorld.removeWorldAccess(this);
        }
        this.prevSortX = -9999.0;
        this.prevSortY = -9999.0;
        this.prevSortZ = -9999.0;
        this.prevRenderSortX = -9999.0;
        this.prevRenderSortY = -9999.0;
        this.prevRenderSortZ = -9999.0;
        this.prevChunkSortX = -9999;
        this.prevChunkSortY = -9999;
        this.prevChunkSortZ = -9999;
        RenderManager.instance.set(par1WorldClient);
        this.theWorld = par1WorldClient;
        this.renderBlocksRg = new RenderBlocks(par1WorldClient);
        if (Config.isDynamicLights()) {
            DynamicLights.clear();
        }
        if (par1WorldClient != null) {
            par1WorldClient.addWorldAccess(this);
            this.loadRenderers();
        }
    }
    
    public void loadRenderers() {
        if (this.theWorld != null) {
            Blocks.leaves.func_150122_b(Config.isTreesFancy());
            Blocks.leaves2.func_150122_b(Config.isTreesFancy());
            this.renderDistanceChunks = this.mc.gameSettings.renderDistanceChunks;
            WrUpdates.clearAllUpdates();
            if (this.worldRenderers != null) {
                for (int numChunks = 0; numChunks < this.worldRenderers.length; ++numChunks) {
                    this.worldRenderers[numChunks].stopRendering();
                }
            }
            if (Config.isDynamicLights()) {
                DynamicLights.clear();
            }
            int numChunks = this.mc.gameSettings.renderDistanceChunks;
            final byte numChunksFar = 16;
            if (Config.isLoadChunksFar() && numChunks < numChunksFar) {
                numChunks = numChunksFar;
            }
            final int maxPreloadedChunks = Config.limit(numChunksFar - numChunks, 0, 8);
            this.effectivePreloadedChunks = Config.limit(Config.getPreloadedChunks(), 0, maxPreloadedChunks);
            numChunks += this.effectivePreloadedChunks;
            final byte limit = 32;
            if (numChunks > limit) {
                numChunks = limit;
            }
            this.prevReposX = -9999.0;
            this.prevReposY = -9999.0;
            this.prevReposZ = -9999.0;
            final int var1 = numChunks * 2 + 1;
            this.renderChunksWide = var1;
            this.renderChunksTall = 16;
            this.renderChunksDeep = var1;
            this.worldRenderers = new WorldRenderer[this.renderChunksWide * this.renderChunksTall * this.renderChunksDeep];
            this.sortedWorldRenderers = new WorldRenderer[this.renderChunksWide * this.renderChunksTall * this.renderChunksDeep];
            this.countSortedWorldRenderers = 0;
            this.displayListAllocator.resetAllocatedLists();
            int var2 = 0;
            int var3 = 0;
            this.minBlockX = 0;
            this.minBlockY = 0;
            this.minBlockZ = 0;
            this.maxBlockX = this.renderChunksWide;
            this.maxBlockY = this.renderChunksTall;
            this.maxBlockZ = this.renderChunksDeep;
            for (int var4 = 0; var4 < this.worldRenderersToUpdate.size(); ++var4) {
                final WorldRenderer esf = (WorldRenderer)this.worldRenderersToUpdate.get(var4);
                if (esf != null) {
                    esf.needsUpdate = false;
                }
            }
            this.worldRenderersToUpdate.clear();
            this.tileEntities.clear();
            this.onStaticEntitiesChanged();
            for (int var4 = 0; var4 < this.renderChunksWide; ++var4) {
                for (int var5 = 0; var5 < this.renderChunksTall; ++var5) {
                    for (int cz = 0; cz < this.renderChunksDeep; ++cz) {
                        final int wri = (cz * this.renderChunksTall + var5) * this.renderChunksWide + var4;
                        this.worldRenderers[wri] = WrUpdates.makeWorldRenderer(this.theWorld, this.tileEntities, var4 * 16, var5 * 16, cz * 16, this.glRenderListBase + var2);
                        if (this.occlusionEnabled) {
                            this.worldRenderers[wri].glOcclusionQuery = this.glOcclusionQueryBase.get(var3);
                        }
                        this.worldRenderers[wri].isWaitingOnOcclusionQuery = false;
                        this.worldRenderers[wri].isVisible = true;
                        this.worldRenderers[wri].isInFrustum = false;
                        this.worldRenderers[wri].chunkIndex = var3++;
                        if (this.theWorld.blockExists(var4 << 4, 0, cz << 4)) {
                            this.worldRenderers[wri].markDirty();
                            this.worldRenderersToUpdate.add(this.worldRenderers[wri]);
                        }
                        var2 += 3;
                    }
                }
            }
            if (this.theWorld != null) {
                Object var6 = this.mc.renderViewEntity;
                if (var6 == null) {
                    var6 = this.mc.thePlayer;
                }
                if (var6 != null) {
                    this.markRenderersForNewPosition(MathHelper.floor_double(((EntityLivingBase)var6).posX), MathHelper.floor_double(((EntityLivingBase)var6).posY), MathHelper.floor_double(((EntityLivingBase)var6).posZ));
                    final EntitySorterFast var7 = new EntitySorterFast((Entity)var6);
                    var7.prepareToSort(this.sortedWorldRenderers, this.countSortedWorldRenderers);
                    Arrays.sort(this.sortedWorldRenderers, 0, this.countSortedWorldRenderers, var7);
                }
            }
            this.renderEntitiesStartupCounter = 2;
        }
    }
    
    public void renderEntities(final EntityLivingBase p_147589_1_, final ICamera p_147589_2_, final float p_147589_3_) {
        final int pass = 0;
        if (this.renderEntitiesStartupCounter > 0) {
            if (pass > 0) {
                return;
            }
            --this.renderEntitiesStartupCounter;
        }
        else {
            final double var4 = p_147589_1_.prevPosX + (p_147589_1_.posX - p_147589_1_.prevPosX) * p_147589_3_;
            final double var5 = p_147589_1_.prevPosY + (p_147589_1_.posY - p_147589_1_.prevPosY) * p_147589_3_;
            final double var6 = p_147589_1_.prevPosZ + (p_147589_1_.posZ - p_147589_1_.prevPosZ) * p_147589_3_;
            this.theWorld.theProfiler.startSection("prepare");
            TileEntityRendererDispatcher.instance.func_147542_a(this.theWorld, this.mc.getTextureManager(), this.mc.fontRenderer, this.mc.renderViewEntity, p_147589_3_);
            RenderManager.instance.func_147938_a(this.theWorld, this.mc.getTextureManager(), this.mc.fontRenderer, this.mc.renderViewEntity, this.mc.pointedEntity, this.mc.gameSettings, p_147589_3_);
            if (pass == 0) {
                this.countEntitiesTotal = 0;
                this.countEntitiesRendered = 0;
                this.countEntitiesHidden = 0;
                this.countTileEntitiesRendered = 0;
                final EntityLivingBase var7 = this.mc.renderViewEntity;
                final double var8 = var7.lastTickPosX + (var7.posX - var7.lastTickPosX) * p_147589_3_;
                final double oldFancyGraphics = var7.lastTickPosY + (var7.posY - var7.lastTickPosY) * p_147589_3_;
                final double aabb = var7.lastTickPosZ + (var7.posZ - var7.lastTickPosZ) * p_147589_3_;
                TileEntityRendererDispatcher.staticPlayerX = var8;
                TileEntityRendererDispatcher.staticPlayerY = oldFancyGraphics;
                TileEntityRendererDispatcher.staticPlayerZ = aabb;
                this.theWorld.theProfiler.endStartSection("staticentities");
                if (this.displayListEntitiesDirty) {
                    RenderManager.renderPosX = 0.0;
                    RenderManager.renderPosY = 0.0;
                    RenderManager.renderPosZ = 0.0;
                    this.rebuildDisplayListEntities();
                }
                GL11.glMatrixMode(5888);
                GL11.glPushMatrix();
                GL11.glTranslated(-var8, -oldFancyGraphics, -aabb);
                GL11.glCallList(this.displayListEntities);
                GL11.glPopMatrix();
                RenderManager.renderPosX = var8;
                RenderManager.renderPosY = oldFancyGraphics;
                RenderManager.renderPosZ = aabb;
            }
            this.mc.entityRenderer.enableLightmap(p_147589_3_);
            this.theWorld.theProfiler.endStartSection("global");
            final List var9 = this.theWorld.getLoadedEntityList();
            if (pass == 0) {
                this.countEntitiesTotal = var9.size();
            }
            if (Config.isFogOff() && this.mc.entityRenderer.fogStandard) {
                GL11.glDisable(2912);
            }
            for (int var10 = 0; var10 < this.theWorld.weatherEffects.size(); ++var10) {
                final Entity var11 = this.theWorld.weatherEffects.get(var10);
                ++this.countEntitiesRendered;
                if (var11.isInRangeToRender3d(var4, var5, var6)) {
                    RenderManager.instance.func_147937_a(var11, p_147589_3_);
                }
            }
            this.theWorld.theProfiler.endStartSection("entities");
            final boolean var12 = this.mc.gameSettings.fancyGraphics;
            this.mc.gameSettings.fancyGraphics = Config.isDroppedItemsFancy();
            for (int var10 = 0; var10 < var9.size(); ++var10) {
                final Entity var11 = var9.get(var10);
                boolean te = var11.isInRangeToRender3d(var4, var5, var6) && (var11.ignoreFrustumCheck || p_147589_2_.isBoundingBoxInFrustum(var11.boundingBox) || var11.riddenByEntity == this.mc.thePlayer);
                if (!te && var11 instanceof EntityLiving) {
                    final EntityLiving var13 = (EntityLiving)var11;
                    if (var13.getLeashed() && var13.getLeashedToEntity() != null) {
                        final Entity teClass = var13.getLeashedToEntity();
                        te = p_147589_2_.isBoundingBoxInFrustum(teClass.boundingBox);
                    }
                }
                if (te && (var11 != this.mc.renderViewEntity || this.mc.gameSettings.thirdPersonView != 0 || this.mc.renderViewEntity.isPlayerSleeping()) && this.theWorld.blockExists(MathHelper.floor_double(var11.posX), 0, MathHelper.floor_double(var11.posZ))) {
                    ++this.countEntitiesRendered;
                    if (var11.getClass() == EntityItemFrame.class) {
                        var11.renderDistanceWeight = 0.06;
                    }
                    this.renderedEntity = var11;
                    RenderManager.instance.func_147937_a(var11, p_147589_3_);
                    this.renderedEntity = null;
                }
            }
            this.mc.gameSettings.fancyGraphics = var12;
            this.theWorld.theProfiler.endStartSection("blockentities");
            RenderHelper.enableStandardItemLighting();
            for (int var10 = 0; var10 < this.tileEntities.size(); ++var10) {
                final TileEntity var14 = this.tileEntities.get(var10);
                final AxisAlignedBB var15 = this.getTileEntityBoundingBox(var14);
                if (var15 == RenderGlobal.AABB_INFINITE || p_147589_2_.isBoundingBoxInFrustum(var15)) {
                    final Class var16 = var14.getClass();
                    if (var16 == TileEntitySign.class && !Config.zoomMode) {
                        final EntityClientPlayerMP block = this.mc.thePlayer;
                        final double distSq = var14.getDistanceFrom(block.posX, block.posY, block.posZ);
                        if (distSq > 256.0) {
                            final FontRenderer fr = TileEntityRendererDispatcher.instance.func_147548_a();
                            fr.enabled = false;
                            TileEntityRendererDispatcher.instance.func_147544_a(var14, p_147589_3_);
                            ++this.countTileEntitiesRendered;
                            fr.enabled = true;
                            continue;
                        }
                    }
                    if (var16 == TileEntityChest.class) {
                        final Block var17 = this.theWorld.getBlock(var14.field_145851_c, var14.field_145848_d, var14.field_145849_e);
                        if (!(var17 instanceof BlockChest)) {
                            continue;
                        }
                    }
                    TileEntityRendererDispatcher.instance.func_147544_a(var14, p_147589_3_);
                    ++this.countTileEntitiesRendered;
                }
            }
            this.mc.entityRenderer.disableLightmap(p_147589_3_);
            this.theWorld.theProfiler.endSection();
        }
    }
    
    public String getDebugInfoRenders() {
        return "C: " + this.renderersBeingRendered + "/" + this.renderersLoaded + ". F: " + this.renderersBeingClipped + ", O: " + this.renderersBeingOccluded + ", E: " + this.renderersSkippingRenderPass;
    }
    
    public String getDebugInfoEntities() {
        return "E: " + this.countEntitiesRendered + "/" + this.countEntitiesTotal + ". B: " + this.countEntitiesHidden + ", I: " + (this.countEntitiesTotal - this.countEntitiesHidden - this.countEntitiesRendered) + ", " + Config.getVersionDebug();
    }
    
    @Override
    public void onStaticEntitiesChanged() {
        this.displayListEntitiesDirty = true;
    }
    
    public void rebuildDisplayListEntities() {
        this.theWorld.theProfiler.startSection("staticentityrebuild");
        GL11.glPushMatrix();
        GL11.glNewList(this.displayListEntities, 4864);
        final List var1 = this.theWorld.getLoadedEntityList();
        this.displayListEntitiesDirty = false;
        for (int var2 = 0; var2 < var1.size(); ++var2) {
            final Entity var3 = var1.get(var2);
            if (RenderManager.instance.getEntityRenderObject(var3).func_147905_a()) {
                this.displayListEntitiesDirty = (this.displayListEntitiesDirty || !RenderManager.instance.func_147936_a(var3, 0.0f, true));
            }
        }
        GL11.glEndList();
        GL11.glPopMatrix();
        this.theWorld.theProfiler.endSection();
    }
    
    private void markRenderersForNewPosition(int x, int y, int z) {
        x -= 8;
        y -= 8;
        z -= 8;
        this.minBlockX = Integer.MAX_VALUE;
        this.minBlockY = Integer.MAX_VALUE;
        this.minBlockZ = Integer.MAX_VALUE;
        this.maxBlockX = Integer.MIN_VALUE;
        this.maxBlockY = Integer.MIN_VALUE;
        this.maxBlockZ = Integer.MIN_VALUE;
        final int blocksWide = this.renderChunksWide * 16;
        final int blocksWide2 = blocksWide / 2;
        for (int ix = 0; ix < this.renderChunksWide; ++ix) {
            int blockX = ix * 16;
            int blockXAbs = blockX + blocksWide2 - x;
            if (blockXAbs < 0) {
                blockXAbs -= blocksWide - 1;
            }
            blockXAbs /= blocksWide;
            blockX -= blockXAbs * blocksWide;
            if (blockX < this.minBlockX) {
                this.minBlockX = blockX;
            }
            if (blockX > this.maxBlockX) {
                this.maxBlockX = blockX;
            }
            for (int iz = 0; iz < this.renderChunksDeep; ++iz) {
                int blockZ = iz * 16;
                int blockZAbs = blockZ + blocksWide2 - z;
                if (blockZAbs < 0) {
                    blockZAbs -= blocksWide - 1;
                }
                blockZAbs /= blocksWide;
                blockZ -= blockZAbs * blocksWide;
                if (blockZ < this.minBlockZ) {
                    this.minBlockZ = blockZ;
                }
                if (blockZ > this.maxBlockZ) {
                    this.maxBlockZ = blockZ;
                }
                for (int iy = 0; iy < this.renderChunksTall; ++iy) {
                    final int blockY = iy * 16;
                    if (blockY < this.minBlockY) {
                        this.minBlockY = blockY;
                    }
                    if (blockY > this.maxBlockY) {
                        this.maxBlockY = blockY;
                    }
                    final WorldRenderer worldrenderer = this.worldRenderers[(iz * this.renderChunksTall + iy) * this.renderChunksWide + ix];
                    final boolean wasNeedingUpdate = worldrenderer.needsUpdate;
                    worldrenderer.setPosition(blockX, blockY, blockZ);
                    if (!wasNeedingUpdate && worldrenderer.needsUpdate) {
                        this.worldRenderersToUpdate.add(worldrenderer);
                    }
                }
            }
        }
    }
    
    public int sortAndRender(final EntityLivingBase player, final int renderPass, final double partialTicks) {
        this.renderViewEntity = player;
        if (Config.isDynamicLights()) {
            DynamicLights.update(this);
        }
        final Profiler profiler = this.theWorld.theProfiler;
        profiler.startSection("sortchunks");
        if (this.worldRenderersToUpdate.size() < 10) {
            final byte shouldSort = 10;
            for (int num = 0; num < shouldSort; ++num) {
                this.worldRenderersCheckIndex = (this.worldRenderersCheckIndex + 1) % this.worldRenderers.length;
                final WorldRenderer ocReq = this.worldRenderers[this.worldRenderersCheckIndex];
                if (ocReq.needsUpdate && !this.worldRenderersToUpdate.contains(ocReq)) {
                    this.worldRenderersToUpdate.add(ocReq);
                }
            }
        }
        if (this.mc.gameSettings.renderDistanceChunks != this.renderDistanceChunks && !Config.isLoadChunksFar()) {
            this.loadRenderers();
        }
        if (renderPass == 0) {
            this.renderersLoaded = 0;
            this.dummyRenderInt = 0;
            this.renderersBeingClipped = 0;
            this.renderersBeingOccluded = 0;
            this.renderersBeingRendered = 0;
            this.renderersSkippingRenderPass = 0;
        }
        boolean var33 = this.prevChunkSortX != player.chunkCoordX || this.prevChunkSortY != player.chunkCoordY || this.prevChunkSortZ != player.chunkCoordZ;
        if (!var33) {
            final double var34 = player.posX - this.prevSortX;
            final double partialX = player.posY - this.prevSortY;
            final double partialY = player.posZ - this.prevSortZ;
            final double partialZ = var34 * var34 + partialX * partialX + partialY * partialY;
            var33 = (partialZ > 16.0);
        }
        if (var33) {
            this.prevChunkSortX = player.chunkCoordX;
            this.prevChunkSortY = player.chunkCoordY;
            this.prevChunkSortZ = player.chunkCoordZ;
            this.prevSortX = player.posX;
            this.prevSortY = player.posY;
            this.prevSortZ = player.posZ;
            final int num = this.effectivePreloadedChunks * 16;
            final double var35 = player.posX - this.prevReposX;
            final double dReposY = player.posY - this.prevReposY;
            final double dReposZ = player.posZ - this.prevReposZ;
            final double countResort = var35 * var35 + dReposY * dReposY + dReposZ * dReposZ;
            if (countResort > num * num + 16.0) {
                this.prevReposX = player.posX;
                this.prevReposY = player.posY;
                this.prevReposZ = player.posZ;
                this.markRenderersForNewPosition(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ));
            }
            final EntitySorterFast lastIndex = new EntitySorterFast(player);
            lastIndex.prepareToSort(this.sortedWorldRenderers, this.countSortedWorldRenderers);
            Arrays.sort(this.sortedWorldRenderers, 0, this.countSortedWorldRenderers, lastIndex);
            if (Config.isFastRender()) {
                final int endIndex = (int)player.posX;
                final int stepNum = (int)player.posZ;
                final short step = 2000;
                if (Math.abs(endIndex - WorldRenderer.globalChunkOffsetX) > step || Math.abs(stepNum - WorldRenderer.globalChunkOffsetZ) > step) {
                    WorldRenderer.globalChunkOffsetX = endIndex;
                    WorldRenderer.globalChunkOffsetZ = stepNum;
                    this.loadRenderers();
                }
            }
        }
        if (Config.isTranslucentBlocksFancy()) {
            final double var34 = player.posX - this.prevRenderSortX;
            final double partialX = player.posY - this.prevRenderSortY;
            final double partialY = player.posZ - this.prevRenderSortZ;
            final int var36 = Math.min(27, this.countSortedWorldRenderers);
            if (var34 * var34 + partialX * partialX + partialY * partialY > 1.0) {
                this.prevRenderSortX = player.posX;
                this.prevRenderSortY = player.posY;
                this.prevRenderSortZ = player.posZ;
                for (int var37 = 0; var37 < var36; ++var37) {
                    final WorldRenderer firstIndex = this.sortedWorldRenderers[var37];
                    if (firstIndex.vertexState != null && firstIndex.sortDistanceToEntitySquared < 1152.0f) {
                        firstIndex.needVertexStateResort = true;
                        if (this.vertexResortCounter > var37) {
                            this.vertexResortCounter = var37;
                        }
                    }
                }
            }
            if (this.vertexResortCounter < var36) {
                while (this.vertexResortCounter < var36) {
                    final WorldRenderer firstIndex = this.sortedWorldRenderers[this.vertexResortCounter];
                    ++this.vertexResortCounter;
                    if (firstIndex.needVertexStateResort) {
                        firstIndex.updateRendererSort(player);
                        break;
                    }
                }
            }
        }
        RenderHelper.disableStandardItemLighting();
        WrUpdates.preRender(this, player);
        if (FPSBoost.SMART_PERFORMANCE.getValue() >= 20.0f && renderPass == 0) {
            GL11.glFinish();
        }
        final byte var38 = 0;
        int var39 = 0;
        int num;
        if (this.occlusionEnabled && this.mc.gameSettings.advancedOpengl && !this.mc.gameSettings.anaglyph && renderPass == 0) {
            final double partialX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
            final double partialY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
            final double partialZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
            final byte var40 = 0;
            final int var41 = Math.min(20, this.countSortedWorldRenderers);
            this.checkOcclusionQueryResult(var40, var41, player.posX, player.posY, player.posZ);
            for (int endIndex = var40; endIndex < var41; ++endIndex) {
                this.sortedWorldRenderers[endIndex].isVisible = true;
            }
            profiler.endStartSection("render");
            num = var38 + this.renderSortedRenderers(var40, var41, renderPass, partialTicks);
            int endIndex = var41;
            int stepNum = 0;
            final byte var42 = 40;
            final int switchStep = this.renderChunksWide;
            while (endIndex < this.countSortedWorldRenderers) {
                profiler.endStartSection("occ");
                final int startIndex = endIndex;
                if (stepNum < switchStep) {
                    ++stepNum;
                }
                else {
                    --stepNum;
                }
                endIndex += stepNum * var42;
                if (endIndex <= startIndex) {
                    endIndex = startIndex + 10;
                }
                if (endIndex > this.countSortedWorldRenderers) {
                    endIndex = this.countSortedWorldRenderers;
                }
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                GL11.glDisable(3008);
                GL11.glDisable(2912);
                GL11.glColorMask(false, false, false, false);
                GL11.glDepthMask(false);
                profiler.startSection("check");
                this.checkOcclusionQueryResult(startIndex, endIndex, player.posX, player.posY, player.posZ);
                profiler.endSection();
                GL11.glPushMatrix();
                float sumTX = 0.0f;
                float sumTY = 0.0f;
                float sumTZ = 0.0f;
                for (int k = startIndex; k < endIndex; ++k) {
                    final WorldRenderer wr = this.sortedWorldRenderers[k];
                    if (wr.skipAllRenderPasses()) {
                        wr.isInFrustum = false;
                    }
                    else if (!wr.isUpdating && !wr.needsBoxUpdate) {
                        if (wr.isInFrustum) {
                            if (Config.isOcclusionFancy() && !wr.isInFrustrumFully) {
                                wr.isVisible = true;
                            }
                            else if (wr.isInFrustum && !wr.isWaitingOnOcclusionQuery) {
                                if (wr.isVisibleFromPosition) {
                                    final float bbX = Math.abs((float)(wr.visibleFromX - player.posX));
                                    final float bbY = Math.abs((float)(wr.visibleFromY - player.posY));
                                    final float bbZ = Math.abs((float)(wr.visibleFromZ - player.posZ));
                                    final float tX = bbX + bbY + bbZ;
                                    if (tX < 10.0 + k / 1000.0) {
                                        wr.isVisible = true;
                                        continue;
                                    }
                                    wr.isVisibleFromPosition = false;
                                }
                                final float bbX = (float)(wr.posXMinus - partialX);
                                final float bbY = (float)(wr.posYMinus - partialY);
                                final float bbZ = (float)(wr.posZMinus - partialZ);
                                final float tX = bbX - sumTX;
                                final float tY = bbY - sumTY;
                                final float tZ = bbZ - sumTZ;
                                if (tX != 0.0f || tY != 0.0f || tZ != 0.0f) {
                                    GL11.glTranslatef(tX, tY, tZ);
                                    sumTX += tX;
                                    sumTY += tY;
                                    sumTZ += tZ;
                                }
                                profiler.startSection("bb");
                                ARBOcclusionQuery.glBeginQueryARB(35092, wr.glOcclusionQuery);
                                wr.callOcclusionQueryList();
                                ARBOcclusionQuery.glEndQueryARB(35092);
                                profiler.endSection();
                                wr.isWaitingOnOcclusionQuery = true;
                                ++var39;
                            }
                        }
                    }
                    else {
                        wr.isVisible = true;
                    }
                }
                GL11.glPopMatrix();
                if (this.mc.gameSettings.anaglyph) {
                    if (EntityRenderer.anaglyphField == 0) {
                        GL11.glColorMask(false, true, true, true);
                    }
                    else {
                        GL11.glColorMask(true, false, false, true);
                    }
                }
                else {
                    GL11.glColorMask(true, true, true, true);
                }
                GL11.glDepthMask(true);
                GL11.glEnable(3553);
                GL11.glEnable(3008);
                GL11.glEnable(2912);
                profiler.endStartSection("render");
                num += this.renderSortedRenderers(startIndex, endIndex, renderPass, partialTicks);
            }
        }
        else {
            profiler.endStartSection("render");
            num = var38 + this.renderSortedRenderers(0, this.countSortedWorldRenderers, renderPass, partialTicks);
        }
        profiler.endSection();
        WrUpdates.postRender();
        return num;
    }
    
    private void checkOcclusionQueryResult(final int startIndex, final int endIndex, final double px, final double py, final double pz) {
        for (int k = startIndex; k < endIndex; ++k) {
            final WorldRenderer wr = this.sortedWorldRenderers[k];
            if (wr.isWaitingOnOcclusionQuery) {
                this.occlusionResult.clear();
                ARBOcclusionQuery.glGetQueryObjectuARB(wr.glOcclusionQuery, 34919, this.occlusionResult);
                if (this.occlusionResult.get(0) != 0) {
                    wr.isWaitingOnOcclusionQuery = false;
                    this.occlusionResult.clear();
                    ARBOcclusionQuery.glGetQueryObjectuARB(wr.glOcclusionQuery, 34918, this.occlusionResult);
                    if (!wr.isUpdating && !wr.needsBoxUpdate) {
                        final boolean wasVisible = wr.isVisible;
                        wr.isVisible = (this.occlusionResult.get(0) > 0);
                        if (wasVisible && wr.isVisible) {
                            wr.isVisibleFromPosition = true;
                            wr.visibleFromX = px;
                            wr.visibleFromY = py;
                            wr.visibleFromZ = pz;
                        }
                    }
                    else {
                        wr.isVisible = true;
                    }
                }
            }
        }
    }
    
    private int renderSortedRenderers(final int par1, final int par2, final int par3, final double par4) {
        if (Config.isFastRender()) {
            return this.renderSortedRenderersFast(par1, par2, par3, par4);
        }
        this.glRenderLists.clear();
        int var6 = 0;
        int var7 = par1;
        int var8 = par2;
        byte var9 = 1;
        if (par3 == 1) {
            var7 = this.countSortedWorldRenderers - 1 - par1;
            var8 = this.countSortedWorldRenderers - 1 - par2;
            var9 = -1;
        }
        for (int var10 = var7; var10 != var8; var10 += var9) {
            if (par3 == 0) {
                ++this.renderersLoaded;
                if (this.sortedWorldRenderers[var10].skipRenderPass[par3]) {
                    ++this.renderersSkippingRenderPass;
                }
                else if (!this.sortedWorldRenderers[var10].isInFrustum) {
                    ++this.renderersBeingClipped;
                }
                else if (this.occlusionEnabled && !this.sortedWorldRenderers[var10].isVisible) {
                    ++this.renderersBeingOccluded;
                }
                else {
                    ++this.renderersBeingRendered;
                }
            }
            if (!this.sortedWorldRenderers[var10].skipRenderPass[par3] && this.sortedWorldRenderers[var10].isInFrustum && (!this.occlusionEnabled || this.sortedWorldRenderers[var10].isVisible)) {
                final int var11 = this.sortedWorldRenderers[var10].getGLCallListForPass(par3);
                if (var11 >= 0) {
                    this.glRenderLists.add(this.sortedWorldRenderers[var10]);
                    ++var6;
                }
            }
        }
        if (var6 == 0) {
            return 0;
        }
        final EntityLivingBase var12 = this.mc.renderViewEntity;
        final double var13 = var12.lastTickPosX + (var12.posX - var12.lastTickPosX) * par4;
        final double var14 = var12.lastTickPosY + (var12.posY - var12.lastTickPosY) * par4;
        final double var15 = var12.lastTickPosZ + (var12.posZ - var12.lastTickPosZ) * par4;
        int var16 = 0;
        for (int var17 = 0; var17 < this.allRenderLists.length; ++var17) {
            this.allRenderLists[var17].resetList();
        }
        for (int var17 = 0; var17 < this.glRenderLists.size(); ++var17) {
            final WorldRenderer var18 = this.glRenderLists.get(var17);
            int var19 = -1;
            for (int var20 = 0; var20 < var16; ++var20) {
                if (this.allRenderLists[var20].rendersChunk(var18.posXMinus, var18.posYMinus, var18.posZMinus)) {
                    var19 = var20;
                }
            }
            if (var19 < 0) {
                var19 = var16++;
                this.allRenderLists[var19].setupRenderList(var18.posXMinus, var18.posYMinus, var18.posZMinus, var13, var14, var15);
            }
            this.allRenderLists[var19].addGLRenderList(var18.getGLCallListForPass(par3));
        }
        if (Config.isFogOff() && this.mc.entityRenderer.fogStandard) {
            GL11.glDisable(2912);
        }
        int var17 = MathHelper.floor_double(var13);
        final int var21 = MathHelper.floor_double(var15);
        int var19 = var17 - (var17 & 0x3FF);
        int var20 = var21 - (var21 & 0x3FF);
        Arrays.sort(this.allRenderLists, new RenderDistanceSorter(var19, var20));
        this.renderAllRenderLists(par3, par4);
        return var6;
    }
    
    private int renderSortedRenderersFast(final int startIndex, final int endIndex, final int renderPass, final double partialTicks) {
        this.glListBuffer.clear();
        int l = 0;
        final boolean debug = this.mc.gameSettings.showDebugInfo;
        int loopStart = startIndex;
        int loopEnd = endIndex;
        byte loopInc = 1;
        if (renderPass == 1) {
            loopStart = this.countSortedWorldRenderers - 1 - startIndex;
            loopEnd = this.countSortedWorldRenderers - 1 - endIndex;
            loopInc = -1;
        }
        for (int entityliving = loopStart; entityliving != loopEnd; entityliving += loopInc) {
            final WorldRenderer partialX = this.sortedWorldRenderers[entityliving];
            if (debug && renderPass == 0) {
                ++this.renderersLoaded;
                if (partialX.skipRenderPass[renderPass]) {
                    ++this.renderersSkippingRenderPass;
                }
                else if (!partialX.isInFrustum) {
                    ++this.renderersBeingClipped;
                }
                else if (this.occlusionEnabled && !partialX.isVisible) {
                    ++this.renderersBeingOccluded;
                }
                else {
                    ++this.renderersBeingRendered;
                }
            }
            if (partialX.isInFrustum && !partialX.skipRenderPass[renderPass] && (!this.occlusionEnabled || partialX.isVisible)) {
                final int glCallList = partialX.getGLCallListForPass(renderPass);
                if (glCallList >= 0) {
                    this.glListBuffer.put(glCallList);
                    ++l;
                }
            }
        }
        if (l == 0) {
            return 0;
        }
        if (Config.isFogOff() && this.mc.entityRenderer.fogStandard) {
            GL11.glDisable(2912);
        }
        this.glListBuffer.flip();
        final EntityLivingBase var18 = this.mc.renderViewEntity;
        final double var19 = var18.lastTickPosX + (var18.posX - var18.lastTickPosX) * partialTicks - WorldRenderer.globalChunkOffsetX;
        final double partialY = var18.lastTickPosY + (var18.posY - var18.lastTickPosY) * partialTicks;
        final double partialZ = var18.lastTickPosZ + (var18.posZ - var18.lastTickPosZ) * partialTicks - WorldRenderer.globalChunkOffsetZ;
        this.mc.entityRenderer.enableLightmap(partialTicks);
        GL11.glTranslatef((float)(-var19), (float)(-partialY), (float)(-partialZ));
        GL11.glCallLists(this.glListBuffer);
        GL11.glTranslatef((float)var19, (float)partialY, (float)partialZ);
        this.mc.entityRenderer.disableLightmap(partialTicks);
        return l;
    }
    
    public void renderAllRenderLists(final int par1, final double par2) {
        this.mc.entityRenderer.enableLightmap(par2);
        for (int var4 = 0; var4 < this.allRenderLists.length; ++var4) {
            this.allRenderLists[var4].callLists();
        }
        this.mc.entityRenderer.disableLightmap(par2);
    }
    
    public void updateClouds() {
        ++this.cloudTickCounter;
        if (this.cloudTickCounter % 20 == 0) {
            final Iterator var1 = this.damagedBlocks.values().iterator();
            while (var1.hasNext()) {
                final DestroyBlockProgress var2 = var1.next();
                final int var3 = var2.getCreationCloudUpdateTick();
                if (this.cloudTickCounter - var3 > 400) {
                    var1.remove();
                }
            }
        }
    }
    
    public void renderSky(final float par1) {
        if (this.mc.theWorld.provider.dimensionId == 1) {
            if (!Config.isSkyEnabled()) {
                return;
            }
            GL11.glDisable(2912);
            GL11.glDisable(3008);
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            RenderHelper.disableStandardItemLighting();
            GL11.glDepthMask(false);
            this.renderEngine.bindTexture(RenderGlobal.locationEndSkyPng);
            final Tessellator var201 = Tessellator.instance;
            for (int var202 = 0; var202 < 6; ++var202) {
                GL11.glPushMatrix();
                if (var202 == 1) {
                    GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (var202 == 2) {
                    GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (var202 == 3) {
                    GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
                }
                if (var202 == 4) {
                    GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
                }
                if (var202 == 5) {
                    GL11.glRotatef(-90.0f, 0.0f, 0.0f, 1.0f);
                }
                var201.startDrawingQuads();
                var201.setColorOpaque_I(2631720);
                var201.addVertexWithUV(-100.0, -100.0, -100.0, 0.0, 0.0);
                var201.addVertexWithUV(-100.0, -100.0, 100.0, 0.0, 16.0);
                var201.addVertexWithUV(100.0, -100.0, 100.0, 16.0, 16.0);
                var201.addVertexWithUV(100.0, -100.0, -100.0, 16.0, 0.0);
                var201.draw();
                GL11.glPopMatrix();
            }
            GL11.glDepthMask(true);
            GL11.glEnable(3553);
            GL11.glEnable(3008);
        }
        else if (this.mc.theWorld.provider.isSurfaceWorld()) {
            GL11.glDisable(3553);
            Vec3 var203 = this.theWorld.getSkyColor(this.mc.renderViewEntity, par1);
            var203 = CustomColorizer.getSkyColor(var203, this.mc.theWorld, this.mc.renderViewEntity.posX, this.mc.renderViewEntity.posY + 1.0, this.mc.renderViewEntity.posZ);
            float var204 = (float)var203.xCoord;
            float var205 = (float)var203.yCoord;
            float var206 = (float)var203.zCoord;
            if (this.mc.gameSettings.anaglyph) {
                final float var207 = (var204 * 30.0f + var205 * 59.0f + var206 * 11.0f) / 100.0f;
                final float var208 = (var204 * 30.0f + var205 * 70.0f) / 100.0f;
                final float var209 = (var204 * 30.0f + var206 * 70.0f) / 100.0f;
                var204 = var207;
                var205 = var208;
                var206 = var209;
            }
            GL11.glColor3f(var204, var205, var206);
            final Tessellator var210 = Tessellator.instance;
            GL11.glDepthMask(false);
            GL11.glEnable(2912);
            GL11.glColor3f(var204, var205, var206);
            if (Config.isSkyEnabled()) {
                GL11.glCallList(this.glSkyList);
            }
            GL11.glDisable(2912);
            GL11.glDisable(3008);
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            RenderHelper.disableStandardItemLighting();
            final float[] var211 = this.theWorld.provider.calcSunriseSunsetColors(this.theWorld.getCelestialAngle(par1), par1);
            if (var211 != null && Config.isSunMoonEnabled()) {
                GL11.glDisable(3553);
                GL11.glShadeModel(7425);
                GL11.glPushMatrix();
                GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef((MathHelper.sin(this.theWorld.getCelestialAngleRadians(par1)) < 0.0f) ? 180.0f : 0.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
                float var209 = var211[0];
                float var212 = var211[1];
                float var213 = var211[2];
                if (this.mc.gameSettings.anaglyph) {
                    final float var214 = (var209 * 30.0f + var212 * 59.0f + var213 * 11.0f) / 100.0f;
                    final float var215 = (var209 * 30.0f + var212 * 70.0f) / 100.0f;
                    final float var216 = (var209 * 30.0f + var213 * 70.0f) / 100.0f;
                    var209 = var214;
                    var212 = var215;
                    var213 = var216;
                }
                var210.startDrawing(6);
                var210.setColorRGBA_F(var209, var212, var213, var211[3]);
                var210.addVertex(0.0, 100.0, 0.0);
                final byte var217 = 16;
                var210.setColorRGBA_F(var211[0], var211[1], var211[2], 0.0f);
                for (int var218 = 0; var218 <= var217; ++var218) {
                    final float var216 = var218 * 3.1415927f * 2.0f / var217;
                    final float var219 = MathHelper.sin(var216);
                    final float var220 = MathHelper.cos(var216);
                    var210.addVertex(var219 * 120.0f, var220 * 120.0f, -var220 * 40.0f * var211[3]);
                }
                var210.draw();
                GL11.glPopMatrix();
                GL11.glShadeModel(7424);
            }
            GL11.glEnable(3553);
            OpenGlHelper.glBlendFunc(770, 1, 1, 0);
            GL11.glPushMatrix();
            float var209 = 1.0f - this.theWorld.getRainStrength(par1);
            float var212 = 0.0f;
            float var213 = 0.0f;
            float var214 = 0.0f;
            GL11.glColor4f(1.0f, 1.0f, 1.0f, var209);
            GL11.glTranslatef(var212, var213, var214);
            GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
            CustomSky.renderSky(this.theWorld, this.renderEngine, this.theWorld.getCelestialAngle(par1), var209);
            GL11.glRotatef(this.theWorld.getCelestialAngle(par1) * 360.0f, 1.0f, 0.0f, 0.0f);
            if (Config.isSunMoonEnabled()) {
                float var215 = 30.0f;
                this.renderEngine.bindTexture(RenderGlobal.locationSunPng);
                var210.startDrawingQuads();
                var210.addVertexWithUV(-var215, 100.0, -var215, 0.0, 0.0);
                var210.addVertexWithUV(var215, 100.0, -var215, 1.0, 0.0);
                var210.addVertexWithUV(var215, 100.0, var215, 1.0, 1.0);
                var210.addVertexWithUV(-var215, 100.0, var215, 0.0, 1.0);
                var210.draw();
                var215 = 20.0f;
                this.renderEngine.bindTexture(RenderGlobal.locationMoonPhasesPng);
                final int var221 = this.theWorld.getMoonPhase();
                final int var222 = var221 % 4;
                final int var218 = var221 / 4 % 2;
                final float var219 = (var222 + 0) / 4.0f;
                final float var220 = (var218 + 0) / 2.0f;
                final float var223 = (var222 + 1) / 4.0f;
                final float var224 = (var218 + 1) / 2.0f;
                var210.startDrawingQuads();
                var210.addVertexWithUV(-var215, -100.0, var215, var223, var224);
                var210.addVertexWithUV(var215, -100.0, var215, var219, var224);
                var210.addVertexWithUV(var215, -100.0, -var215, var219, var220);
                var210.addVertexWithUV(-var215, -100.0, -var215, var223, var220);
                var210.draw();
            }
            GL11.glDisable(3553);
            final float var216 = this.theWorld.getStarBrightness(par1) * var209;
            if (var216 > 0.0f && Config.isStarsEnabled() && !CustomSky.hasSkyLayers(this.theWorld)) {
                GL11.glColor4f(var216, var216, var216, var216);
                GL11.glCallList(this.starGLCallList);
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glDisable(3042);
            GL11.glEnable(3008);
            GL11.glEnable(2912);
            GL11.glPopMatrix();
            GL11.glDisable(3553);
            GL11.glColor3f(0.0f, 0.0f, 0.0f);
            final double var225 = this.mc.thePlayer.getPosition(par1).yCoord - this.theWorld.getHorizon();
            if (var225 < 0.0) {
                GL11.glPushMatrix();
                GL11.glTranslatef(0.0f, 12.0f, 0.0f);
                GL11.glCallList(this.glSkyList2);
                GL11.glPopMatrix();
                var213 = 1.0f;
                var214 = -(float)(var225 + 65.0);
                final float var215 = -var213;
                var210.startDrawingQuads();
                var210.setColorRGBA_I(0, 255);
                var210.addVertex(-var213, var214, var213);
                var210.addVertex(var213, var214, var213);
                var210.addVertex(var213, var215, var213);
                var210.addVertex(-var213, var215, var213);
                var210.addVertex(-var213, var215, -var213);
                var210.addVertex(var213, var215, -var213);
                var210.addVertex(var213, var214, -var213);
                var210.addVertex(-var213, var214, -var213);
                var210.addVertex(var213, var215, -var213);
                var210.addVertex(var213, var215, var213);
                var210.addVertex(var213, var214, var213);
                var210.addVertex(var213, var214, -var213);
                var210.addVertex(-var213, var214, -var213);
                var210.addVertex(-var213, var214, var213);
                var210.addVertex(-var213, var215, var213);
                var210.addVertex(-var213, var215, -var213);
                var210.addVertex(-var213, var215, -var213);
                var210.addVertex(-var213, var215, var213);
                var210.addVertex(var213, var215, var213);
                var210.addVertex(var213, var215, -var213);
                var210.draw();
            }
            if (this.theWorld.provider.isSkyColored()) {
                GL11.glColor3f(var204 * 0.2f + 0.04f, var205 * 0.2f + 0.04f, var206 * 0.6f + 0.1f);
            }
            else {
                GL11.glColor3f(var204, var205, var206);
            }
            if (this.mc.gameSettings.renderDistanceChunks <= 4) {
                GL11.glColor3f(this.mc.entityRenderer.fogColorRed, this.mc.entityRenderer.fogColorGreen, this.mc.entityRenderer.fogColorBlue);
            }
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, -(float)(var225 - 16.0), 0.0f);
            if (Config.isSkyEnabled()) {
                GL11.glCallList(this.glSkyList2);
            }
            GL11.glPopMatrix();
            GL11.glEnable(3553);
            GL11.glDepthMask(true);
        }
    }
    
    public void renderClouds(float par1) {
        if (!Config.isCloudsOff() && this.mc.theWorld.provider.isSurfaceWorld()) {
            if (Config.isCloudsFancy()) {
                this.renderCloudsFancy(par1);
            }
            else {
                final float partialTicks1 = par1;
                par1 = 0.0f;
                GL11.glDisable(2884);
                float var21 = (float)(this.mc.renderViewEntity.lastTickPosY + (this.mc.renderViewEntity.posY - this.mc.renderViewEntity.lastTickPosY) * par1);
                final byte var22 = 32;
                final int var23 = 256 / var22;
                final Tessellator var24 = Tessellator.instance;
                this.renderEngine.bindTexture(RenderGlobal.locationCloudsPng);
                GL11.glEnable(3042);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                if (this.isFancyGlListClouds || this.cloudTickCounter >= this.cloudTickCounterGlList + 20) {
                    GL11.glNewList(this.glListClouds, 4864);
                    final Vec3 entityliving = this.theWorld.getCloudColour(par1);
                    float exactPlayerX = (float)entityliving.xCoord;
                    float var25 = (float)entityliving.yCoord;
                    float exactPlayerY = (float)entityliving.zCoord;
                    if (this.mc.gameSettings.anaglyph) {
                        final float var26 = (exactPlayerX * 30.0f + var25 * 59.0f + exactPlayerY * 11.0f) / 100.0f;
                        final float exactPlayerZ = (exactPlayerX * 30.0f + var25 * 70.0f) / 100.0f;
                        final float var27 = (exactPlayerX * 30.0f + exactPlayerY * 70.0f) / 100.0f;
                        exactPlayerX = var26;
                        var25 = exactPlayerZ;
                        exactPlayerY = var27;
                    }
                    final float var26 = 4.8828125E-4f;
                    final double exactPlayerZ2 = this.cloudTickCounter + par1;
                    double dc = this.mc.renderViewEntity.prevPosX + (this.mc.renderViewEntity.posX - this.mc.renderViewEntity.prevPosX) * par1 + exactPlayerZ2 * 0.029999999329447746;
                    double cdx = this.mc.renderViewEntity.prevPosZ + (this.mc.renderViewEntity.posZ - this.mc.renderViewEntity.prevPosZ) * par1;
                    final int cdz = MathHelper.floor_double(dc / 2048.0);
                    final int var28 = MathHelper.floor_double(cdx / 2048.0);
                    dc -= cdz * 2048;
                    cdx -= var28 * 2048;
                    float var29 = this.theWorld.provider.getCloudHeight() - var21 + 0.33f;
                    var29 += this.mc.gameSettings.ofCloudsHeight * 128.0f;
                    final float var30 = (float)(dc * var26);
                    var21 = (float)(cdx * var26);
                    var24.startDrawingQuads();
                    var24.setColorRGBA_F(exactPlayerX, var25, exactPlayerY, 0.8f);
                    for (int var31 = -var22 * var23; var31 < var22 * var23; var31 += var22) {
                        for (int var32 = -var22 * var23; var32 < var22 * var23; var32 += var22) {
                            var24.addVertexWithUV(var31 + 0, var29, var32 + var22, (var31 + 0) * var26 + var30, (var32 + var22) * var26 + var21);
                            var24.addVertexWithUV(var31 + var22, var29, var32 + var22, (var31 + var22) * var26 + var30, (var32 + var22) * var26 + var21);
                            var24.addVertexWithUV(var31 + var22, var29, var32 + 0, (var31 + var22) * var26 + var30, (var32 + 0) * var26 + var21);
                            var24.addVertexWithUV(var31 + 0, var29, var32 + 0, (var31 + 0) * var26 + var30, (var32 + 0) * var26 + var21);
                        }
                    }
                    var24.draw();
                    GL11.glEndList();
                    this.isFancyGlListClouds = false;
                    this.cloudTickCounterGlList = this.cloudTickCounter;
                    this.cloudPlayerX = this.mc.renderViewEntity.prevPosX;
                    this.cloudPlayerY = this.mc.renderViewEntity.prevPosY;
                    this.cloudPlayerZ = this.mc.renderViewEntity.prevPosZ;
                }
                final EntityLivingBase entityliving2 = this.mc.renderViewEntity;
                final double exactPlayerX2 = entityliving2.prevPosX + (entityliving2.posX - entityliving2.prevPosX) * partialTicks1;
                final double exactPlayerY2 = entityliving2.prevPosY + (entityliving2.posY - entityliving2.prevPosY) * partialTicks1;
                final double exactPlayerZ2 = entityliving2.prevPosZ + (entityliving2.posZ - entityliving2.prevPosZ) * partialTicks1;
                double dc = this.cloudTickCounter - this.cloudTickCounterGlList + partialTicks1;
                final float cdx2 = (float)(exactPlayerX2 - this.cloudPlayerX + dc * 0.03);
                final float cdy = (float)(exactPlayerY2 - this.cloudPlayerY);
                final float cdz2 = (float)(exactPlayerZ2 - this.cloudPlayerZ);
                GL11.glTranslatef(-cdx2, -cdy, -cdz2);
                GL11.glCallList(this.glListClouds);
                GL11.glTranslatef(cdx2, cdy, cdz2);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glDisable(3042);
                GL11.glEnable(2884);
            }
        }
    }
    
    public boolean hasCloudFog(final double par1, final double par3, final double par5, final float par7) {
        return false;
    }
    
    public void renderCloudsFancy(float par1) {
        final float partialTicks = par1;
        par1 = 0.0f;
        GL11.glDisable(2884);
        final float var2 = (float)(this.mc.renderViewEntity.lastTickPosY + (this.mc.renderViewEntity.posY - this.mc.renderViewEntity.lastTickPosY) * par1);
        final Tessellator var3 = Tessellator.instance;
        final float var4 = 12.0f;
        final float var5 = 4.0f;
        final double var6 = this.cloudTickCounter + par1;
        double var7 = (this.mc.renderViewEntity.prevPosX + (this.mc.renderViewEntity.posX - this.mc.renderViewEntity.prevPosX) * par1 + var6 * 0.029999999329447746) / var4;
        double var8 = (this.mc.renderViewEntity.prevPosZ + (this.mc.renderViewEntity.posZ - this.mc.renderViewEntity.prevPosZ) * par1) / var4 + 0.33000001311302185;
        float var9 = this.theWorld.provider.getCloudHeight() - var2 + 0.33f;
        var9 += this.mc.gameSettings.ofCloudsHeight * 128.0f;
        final int var10 = MathHelper.floor_double(var7 / 2048.0);
        final int var11 = MathHelper.floor_double(var8 / 2048.0);
        var7 -= var10 * 2048;
        var8 -= var11 * 2048;
        this.renderEngine.bindTexture(RenderGlobal.locationCloudsPng);
        GL11.glEnable(3042);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        if (!this.isFancyGlListClouds || this.cloudTickCounter >= this.cloudTickCounterGlList + 20) {
            GL11.glNewList(this.glListClouds, 4864);
            final Vec3 entityliving = this.theWorld.getCloudColour(par1);
            float exactPlayerX = (float)entityliving.xCoord;
            float var12 = (float)entityliving.yCoord;
            float exactPlayerY = (float)entityliving.zCoord;
            if (this.mc.gameSettings.anaglyph) {
                final float var13 = (exactPlayerX * 30.0f + var12 * 59.0f + exactPlayerY * 11.0f) / 100.0f;
                final float exactPlayerZ = (exactPlayerX * 30.0f + var12 * 70.0f) / 100.0f;
                final float var14 = (exactPlayerX * 30.0f + exactPlayerY * 70.0f) / 100.0f;
                exactPlayerX = var13;
                var12 = exactPlayerZ;
                exactPlayerY = var14;
            }
            float var13 = (float)(var7 * 0.0);
            float exactPlayerZ = (float)(var8 * 0.0);
            final float var14 = 0.00390625f;
            var13 = MathHelper.floor_double(var7) * var14;
            exactPlayerZ = MathHelper.floor_double(var8) * var14;
            final float dc = (float)(var7 - MathHelper.floor_double(var7));
            final float var15 = (float)(var8 - MathHelper.floor_double(var8));
            final byte cdx = 8;
            final byte cdy = 4;
            final float cdz = 9.765625E-4f;
            GL11.glScalef(var4, 1.0f, var4);
            for (int var16 = 0; var16 < 2; ++var16) {
                if (var16 == 0) {
                    GL11.glColorMask(false, false, false, false);
                }
                else if (this.mc.gameSettings.anaglyph) {
                    if (EntityRenderer.anaglyphField == 0) {
                        GL11.glColorMask(false, true, true, true);
                    }
                    else {
                        GL11.glColorMask(true, false, false, true);
                    }
                }
                else {
                    GL11.glColorMask(true, true, true, true);
                }
                for (int var17 = -cdy + 1; var17 <= cdy; ++var17) {
                    for (int var18 = -cdy + 1; var18 <= cdy; ++var18) {
                        var3.startDrawingQuads();
                        final float var19 = (float)(var17 * cdx);
                        final float var20 = (float)(var18 * cdx);
                        final float var21 = var19 - dc;
                        final float var22 = var20 - var15;
                        if (var9 > -var5 - 1.0f) {
                            var3.setColorRGBA_F(exactPlayerX * 0.7f, var12 * 0.7f, exactPlayerY * 0.7f, 0.8f);
                            var3.setNormal(0.0f, -1.0f, 0.0f);
                            var3.addVertexWithUV(var21 + 0.0f, var9 + 0.0f, var22 + cdx, (var19 + 0.0f) * var14 + var13, (var20 + cdx) * var14 + exactPlayerZ);
                            var3.addVertexWithUV(var21 + cdx, var9 + 0.0f, var22 + cdx, (var19 + cdx) * var14 + var13, (var20 + cdx) * var14 + exactPlayerZ);
                            var3.addVertexWithUV(var21 + cdx, var9 + 0.0f, var22 + 0.0f, (var19 + cdx) * var14 + var13, (var20 + 0.0f) * var14 + exactPlayerZ);
                            var3.addVertexWithUV(var21 + 0.0f, var9 + 0.0f, var22 + 0.0f, (var19 + 0.0f) * var14 + var13, (var20 + 0.0f) * var14 + exactPlayerZ);
                        }
                        if (var9 <= var5 + 1.0f) {
                            var3.setColorRGBA_F(exactPlayerX, var12, exactPlayerY, 0.8f);
                            var3.setNormal(0.0f, 1.0f, 0.0f);
                            var3.addVertexWithUV(var21 + 0.0f, var9 + var5 - cdz, var22 + cdx, (var19 + 0.0f) * var14 + var13, (var20 + cdx) * var14 + exactPlayerZ);
                            var3.addVertexWithUV(var21 + cdx, var9 + var5 - cdz, var22 + cdx, (var19 + cdx) * var14 + var13, (var20 + cdx) * var14 + exactPlayerZ);
                            var3.addVertexWithUV(var21 + cdx, var9 + var5 - cdz, var22 + 0.0f, (var19 + cdx) * var14 + var13, (var20 + 0.0f) * var14 + exactPlayerZ);
                            var3.addVertexWithUV(var21 + 0.0f, var9 + var5 - cdz, var22 + 0.0f, (var19 + 0.0f) * var14 + var13, (var20 + 0.0f) * var14 + exactPlayerZ);
                        }
                        var3.setColorRGBA_F(exactPlayerX * 0.9f, var12 * 0.9f, exactPlayerY * 0.9f, 0.8f);
                        if (var17 > -1) {
                            var3.setNormal(-1.0f, 0.0f, 0.0f);
                            for (int var23 = 0; var23 < cdx; ++var23) {
                                var3.addVertexWithUV(var21 + var23 + 0.0f, var9 + 0.0f, var22 + cdx, (var19 + var23 + 0.5f) * var14 + var13, (var20 + cdx) * var14 + exactPlayerZ);
                                var3.addVertexWithUV(var21 + var23 + 0.0f, var9 + var5, var22 + cdx, (var19 + var23 + 0.5f) * var14 + var13, (var20 + cdx) * var14 + exactPlayerZ);
                                var3.addVertexWithUV(var21 + var23 + 0.0f, var9 + var5, var22 + 0.0f, (var19 + var23 + 0.5f) * var14 + var13, (var20 + 0.0f) * var14 + exactPlayerZ);
                                var3.addVertexWithUV(var21 + var23 + 0.0f, var9 + 0.0f, var22 + 0.0f, (var19 + var23 + 0.5f) * var14 + var13, (var20 + 0.0f) * var14 + exactPlayerZ);
                            }
                        }
                        if (var17 <= 1) {
                            var3.setNormal(1.0f, 0.0f, 0.0f);
                            for (int var23 = 0; var23 < cdx; ++var23) {
                                var3.addVertexWithUV(var21 + var23 + 1.0f - cdz, var9 + 0.0f, var22 + cdx, (var19 + var23 + 0.5f) * var14 + var13, (var20 + cdx) * var14 + exactPlayerZ);
                                var3.addVertexWithUV(var21 + var23 + 1.0f - cdz, var9 + var5, var22 + cdx, (var19 + var23 + 0.5f) * var14 + var13, (var20 + cdx) * var14 + exactPlayerZ);
                                var3.addVertexWithUV(var21 + var23 + 1.0f - cdz, var9 + var5, var22 + 0.0f, (var19 + var23 + 0.5f) * var14 + var13, (var20 + 0.0f) * var14 + exactPlayerZ);
                                var3.addVertexWithUV(var21 + var23 + 1.0f - cdz, var9 + 0.0f, var22 + 0.0f, (var19 + var23 + 0.5f) * var14 + var13, (var20 + 0.0f) * var14 + exactPlayerZ);
                            }
                        }
                        var3.setColorRGBA_F(exactPlayerX * 0.8f, var12 * 0.8f, exactPlayerY * 0.8f, 0.8f);
                        if (var18 > -1) {
                            var3.setNormal(0.0f, 0.0f, -1.0f);
                            for (int var23 = 0; var23 < cdx; ++var23) {
                                var3.addVertexWithUV(var21 + 0.0f, var9 + var5, var22 + var23 + 0.0f, (var19 + 0.0f) * var14 + var13, (var20 + var23 + 0.5f) * var14 + exactPlayerZ);
                                var3.addVertexWithUV(var21 + cdx, var9 + var5, var22 + var23 + 0.0f, (var19 + cdx) * var14 + var13, (var20 + var23 + 0.5f) * var14 + exactPlayerZ);
                                var3.addVertexWithUV(var21 + cdx, var9 + 0.0f, var22 + var23 + 0.0f, (var19 + cdx) * var14 + var13, (var20 + var23 + 0.5f) * var14 + exactPlayerZ);
                                var3.addVertexWithUV(var21 + 0.0f, var9 + 0.0f, var22 + var23 + 0.0f, (var19 + 0.0f) * var14 + var13, (var20 + var23 + 0.5f) * var14 + exactPlayerZ);
                            }
                        }
                        if (var18 <= 1) {
                            var3.setNormal(0.0f, 0.0f, 1.0f);
                            for (int var23 = 0; var23 < cdx; ++var23) {
                                var3.addVertexWithUV(var21 + 0.0f, var9 + var5, var22 + var23 + 1.0f - cdz, (var19 + 0.0f) * var14 + var13, (var20 + var23 + 0.5f) * var14 + exactPlayerZ);
                                var3.addVertexWithUV(var21 + cdx, var9 + var5, var22 + var23 + 1.0f - cdz, (var19 + cdx) * var14 + var13, (var20 + var23 + 0.5f) * var14 + exactPlayerZ);
                                var3.addVertexWithUV(var21 + cdx, var9 + 0.0f, var22 + var23 + 1.0f - cdz, (var19 + cdx) * var14 + var13, (var20 + var23 + 0.5f) * var14 + exactPlayerZ);
                                var3.addVertexWithUV(var21 + 0.0f, var9 + 0.0f, var22 + var23 + 1.0f - cdz, (var19 + 0.0f) * var14 + var13, (var20 + var23 + 0.5f) * var14 + exactPlayerZ);
                            }
                        }
                        var3.draw();
                    }
                }
            }
            GL11.glEndList();
            this.isFancyGlListClouds = true;
            this.cloudTickCounterGlList = this.cloudTickCounter;
            this.cloudPlayerX = this.mc.renderViewEntity.prevPosX;
            this.cloudPlayerY = this.mc.renderViewEntity.prevPosY;
            this.cloudPlayerZ = this.mc.renderViewEntity.prevPosZ;
        }
        final EntityLivingBase var24 = this.mc.renderViewEntity;
        final double var25 = var24.prevPosX + (var24.posX - var24.prevPosX) * partialTicks;
        final double var26 = var24.prevPosY + (var24.posY - var24.prevPosY) * partialTicks;
        final double var27 = var24.prevPosZ + (var24.posZ - var24.prevPosZ) * partialTicks;
        final double var28 = this.cloudTickCounter - this.cloudTickCounterGlList + partialTicks;
        final float var29 = (float)(var25 - this.cloudPlayerX + var28 * 0.03);
        final float var30 = (float)(var26 - this.cloudPlayerY);
        final float cdz = (float)(var27 - this.cloudPlayerZ);
        GL11.glTranslatef(-var29, -var30, -cdz);
        GL11.glCallList(this.glListClouds);
        GL11.glTranslatef(var29, var30, cdz);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
        GL11.glEnable(2884);
    }
    
    public boolean updateRenderers(final EntityLivingBase entityliving, final boolean flag) {
        this.renderViewEntity = entityliving;
        if (WrUpdates.hasWrUpdater()) {
            return WrUpdates.updateRenderers(this, entityliving, flag);
        }
        if (this.worldRenderersToUpdate.size() <= 0) {
            return false;
        }
        int num = 0;
        int maxNum = Config.getUpdatesPerFrame();
        if (Config.isDynamicUpdates() && !this.isMoving(entityliving)) {
            maxNum *= 3;
        }
        final byte NOT_IN_FRUSTRUM_MUL = 4;
        int numValid = 0;
        WorldRenderer wrBest = null;
        float distSqBest = Float.MAX_VALUE;
        int indexBest = -1;
        for (int maxDiffDistSq = 0; maxDiffDistSq < this.worldRenderersToUpdate.size(); ++maxDiffDistSq) {
            final WorldRenderer i = (WorldRenderer)this.worldRenderersToUpdate.get(maxDiffDistSq);
            if (i != null) {
                ++numValid;
                if (!i.needsUpdate) {
                    this.worldRenderersToUpdate.set(maxDiffDistSq, null);
                }
                else {
                    float wr = i.distanceToEntitySquared(entityliving);
                    if (wr <= 256.0f && this.isActingNow()) {
                        i.updateRenderer(entityliving);
                        i.needsUpdate = false;
                        this.worldRenderersToUpdate.set(maxDiffDistSq, null);
                        ++num;
                    }
                    else {
                        if (wr > 256.0f && num >= maxNum) {
                            break;
                        }
                        if (!i.isInFrustum) {
                            wr *= NOT_IN_FRUSTRUM_MUL;
                        }
                        if (wrBest == null) {
                            wrBest = i;
                            distSqBest = wr;
                            indexBest = maxDiffDistSq;
                        }
                        else if (wr < distSqBest) {
                            wrBest = i;
                            distSqBest = wr;
                            indexBest = maxDiffDistSq;
                        }
                    }
                }
            }
        }
        if (wrBest != null) {
            wrBest.updateRenderer(entityliving);
            wrBest.needsUpdate = false;
            this.worldRenderersToUpdate.set(indexBest, null);
            ++num;
            final float var15 = distSqBest / 5.0f;
            for (int var16 = 0; var16 < this.worldRenderersToUpdate.size() && num < maxNum; ++var16) {
                final WorldRenderer var17 = (WorldRenderer)this.worldRenderersToUpdate.get(var16);
                if (var17 != null) {
                    float distSq = var17.distanceToEntitySquared(entityliving);
                    if (!var17.isInFrustum) {
                        distSq *= NOT_IN_FRUSTRUM_MUL;
                    }
                    final float diffDistSq = Math.abs(distSq - distSqBest);
                    if (diffDistSq < var15) {
                        var17.updateRenderer(entityliving);
                        var17.needsUpdate = false;
                        this.worldRenderersToUpdate.set(var16, null);
                        ++num;
                    }
                }
            }
        }
        if (numValid == 0) {
            this.worldRenderersToUpdate.clear();
        }
        this.worldRenderersToUpdate.compact();
        return true;
    }
    
    public void drawBlockDamageTexture(final Tessellator par1Tessellator, final EntityPlayer par2EntityPlayer, final float par3) {
        this.drawBlockDamageTexture(par1Tessellator, (EntityLivingBase)par2EntityPlayer, par3);
    }
    
    public void drawBlockDamageTexture(final Tessellator par1Tessellator, final EntityLivingBase par2EntityPlayer, final float par3) {
        final double var4 = par2EntityPlayer.lastTickPosX + (par2EntityPlayer.posX - par2EntityPlayer.lastTickPosX) * par3;
        final double var5 = par2EntityPlayer.lastTickPosY + (par2EntityPlayer.posY - par2EntityPlayer.lastTickPosY) * par3;
        final double var6 = par2EntityPlayer.lastTickPosZ + (par2EntityPlayer.posZ - par2EntityPlayer.lastTickPosZ) * par3;
        if (!this.damagedBlocks.isEmpty()) {
            OpenGlHelper.glBlendFunc(774, 768, 1, 0);
            this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
            GL11.glPushMatrix();
            GL11.glPolygonOffset(-3.0f, -3.0f);
            GL11.glEnable(32823);
            GL11.glAlphaFunc(516, 0.1f);
            GL11.glEnable(3008);
            par1Tessellator.startDrawingQuads();
            par1Tessellator.setTranslation(-var4, -var5, -var6);
            par1Tessellator.disableColor();
            final Iterator var7 = this.damagedBlocks.values().iterator();
            while (var7.hasNext()) {
                final DestroyBlockProgress var8 = var7.next();
                final double var9 = var8.getPartialBlockX() - var4;
                final double var10 = var8.getPartialBlockY() - var5;
                final double var11 = var8.getPartialBlockZ() - var6;
                if (var9 * var9 + var10 * var10 + var11 * var11 > 1024.0) {
                    var7.remove();
                }
                else {
                    final Block var12 = this.theWorld.getBlock(var8.getPartialBlockX(), var8.getPartialBlockY(), var8.getPartialBlockZ());
                    if (var12.getMaterial() == Material.air) {
                        continue;
                    }
                    this.renderBlocksRg.renderBlockUsingTexture(var12, var8.getPartialBlockX(), var8.getPartialBlockY(), var8.getPartialBlockZ(), this.destroyBlockIcons[var8.getPartialBlockDamage()]);
                }
            }
            par1Tessellator.draw();
            par1Tessellator.setTranslation(0.0, 0.0, 0.0);
            GL11.glDisable(3008);
            GL11.glPolygonOffset(0.0f, 0.0f);
            GL11.glDisable(32823);
            GL11.glEnable(3008);
            GL11.glDepthMask(true);
            GL11.glPopMatrix();
        }
    }
    
    public void drawSelectionBox(final EntityPlayer par1EntityPlayer, final MovingObjectPosition par2MovingObjectPosition, final int par3, final float par4) {
        if (par3 == 0 && par2MovingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.4f);
            GL11.glLineWidth(2.0f);
            GL11.glDisable(3553);
            GL11.glDepthMask(false);
            final float var5 = 0.002f;
            final Block var6 = this.theWorld.getBlock(par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ);
            if (var6.getMaterial() != Material.air) {
                var6.setBlockBoundsBasedOnState(this.theWorld, par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ);
                final double var7 = par1EntityPlayer.lastTickPosX + (par1EntityPlayer.posX - par1EntityPlayer.lastTickPosX) * par4;
                final double var8 = par1EntityPlayer.lastTickPosY + (par1EntityPlayer.posY - par1EntityPlayer.lastTickPosY) * par4;
                final double var9 = par1EntityPlayer.lastTickPosZ + (par1EntityPlayer.posZ - par1EntityPlayer.lastTickPosZ) * par4;
                drawOutlinedBoundingBox(var6.getSelectedBoundingBoxFromPool(this.theWorld, par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ).expand(var5, var5, var5).getOffsetBoundingBox(-var7, -var8, -var9), -1);
            }
            GL11.glDepthMask(true);
            GL11.glEnable(3553);
            GL11.glDisable(3042);
        }
    }
    
    public static void drawOutlinedBoundingBox(final AxisAlignedBB p_147590_0_, final int p_147590_1_) {
        final Tessellator var2 = Tessellator.instance;
        var2.startDrawing(3);
        if (p_147590_1_ != -1) {
            var2.setColorOpaque_I(p_147590_1_);
        }
        var2.addVertex(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.minZ);
        var2.addVertex(p_147590_0_.maxX, p_147590_0_.minY, p_147590_0_.minZ);
        var2.addVertex(p_147590_0_.maxX, p_147590_0_.minY, p_147590_0_.maxZ);
        var2.addVertex(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.maxZ);
        var2.addVertex(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.minZ);
        var2.draw();
        var2.startDrawing(3);
        if (p_147590_1_ != -1) {
            var2.setColorOpaque_I(p_147590_1_);
        }
        var2.addVertex(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.minZ);
        var2.addVertex(p_147590_0_.maxX, p_147590_0_.maxY, p_147590_0_.minZ);
        var2.addVertex(p_147590_0_.maxX, p_147590_0_.maxY, p_147590_0_.maxZ);
        var2.addVertex(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.maxZ);
        var2.addVertex(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.minZ);
        var2.draw();
        var2.startDrawing(1);
        if (p_147590_1_ != -1) {
            var2.setColorOpaque_I(p_147590_1_);
        }
        var2.addVertex(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.minZ);
        var2.addVertex(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.minZ);
        var2.addVertex(p_147590_0_.maxX, p_147590_0_.minY, p_147590_0_.minZ);
        var2.addVertex(p_147590_0_.maxX, p_147590_0_.maxY, p_147590_0_.minZ);
        var2.addVertex(p_147590_0_.maxX, p_147590_0_.minY, p_147590_0_.maxZ);
        var2.addVertex(p_147590_0_.maxX, p_147590_0_.maxY, p_147590_0_.maxZ);
        var2.addVertex(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.maxZ);
        var2.addVertex(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.maxZ);
        var2.draw();
    }
    
    public void markBlocksForUpdate(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final int var7 = MathHelper.bucketInt(par1, 16);
        final int var8 = MathHelper.bucketInt(par2, 16);
        final int var9 = MathHelper.bucketInt(par3, 16);
        final int var10 = MathHelper.bucketInt(par4, 16);
        final int var11 = MathHelper.bucketInt(par5, 16);
        final int var12 = MathHelper.bucketInt(par6, 16);
        for (int var13 = var7; var13 <= var10; ++var13) {
            int var14 = var13 % this.renderChunksWide;
            if (var14 < 0) {
                var14 += this.renderChunksWide;
            }
            for (int var15 = var8; var15 <= var11; ++var15) {
                int var16 = var15 % this.renderChunksTall;
                if (var16 < 0) {
                    var16 += this.renderChunksTall;
                }
                for (int var17 = var9; var17 <= var12; ++var17) {
                    int var18 = var17 % this.renderChunksDeep;
                    if (var18 < 0) {
                        var18 += this.renderChunksDeep;
                    }
                    final int var19 = (var18 * this.renderChunksTall + var16) * this.renderChunksWide + var14;
                    final WorldRenderer var20 = this.worldRenderers[var19];
                    if (var20 != null && !var20.needsUpdate) {
                        this.worldRenderersToUpdate.add(var20);
                        var20.markDirty();
                    }
                }
            }
        }
    }
    
    @Override
    public void markBlockForUpdate(final int p_147586_1_, final int p_147586_2_, final int p_147586_3_) {
        this.markBlocksForUpdate(p_147586_1_ - 1, p_147586_2_ - 1, p_147586_3_ - 1, p_147586_1_ + 1, p_147586_2_ + 1, p_147586_3_ + 1);
    }
    
    @Override
    public void markBlockForRenderUpdate(final int p_147588_1_, final int p_147588_2_, final int p_147588_3_) {
        this.markBlocksForUpdate(p_147588_1_ - 1, p_147588_2_ - 1, p_147588_3_ - 1, p_147588_1_ + 1, p_147588_2_ + 1, p_147588_3_ + 1);
    }
    
    @Override
    public void markBlockRangeForRenderUpdate(final int p_147585_1_, final int p_147585_2_, final int p_147585_3_, final int p_147585_4_, final int p_147585_5_, final int p_147585_6_) {
        this.markBlocksForUpdate(p_147585_1_ - 1, p_147585_2_ - 1, p_147585_3_ - 1, p_147585_4_ + 1, p_147585_5_ + 1, p_147585_6_ + 1);
    }
    
    public void clipRenderersByFrustum(final ICamera par1ICamera, final float par2) {
        final boolean checkDistanceXz = !Config.isFogOff();
        final double renderDistSq = this.renderDistanceChunks * 16 * this.renderDistanceChunks * 16;
        for (int var3 = 0; var3 < this.countSortedWorldRenderers; ++var3) {
            final WorldRenderer wr = this.sortedWorldRenderers[var3];
            if (!wr.skipAllRenderPasses()) {
                if (checkDistanceXz && wr.distanceToEntityXzSq > renderDistSq) {
                    wr.isInFrustum = false;
                }
                else {
                    wr.updateInFrustum(par1ICamera);
                }
            }
        }
    }
    
    @Override
    public void playRecord(final String par1Str, final int par2, final int par3, final int par4) {
        final ChunkCoordinates var5 = new ChunkCoordinates(par2, par3, par4);
        final ISound var6 = this.mapSoundPositions.get(var5);
        if (var6 != null) {
            this.mc.getSoundHandler().func_147683_b(var6);
            this.mapSoundPositions.remove(var5);
        }
        if (par1Str != null) {
            final ItemRecord var7 = ItemRecord.func_150926_b(par1Str);
            if (var7 != null) {
                this.mc.ingameGUI.setRecordPlayingMessage(var7.func_150927_i());
            }
            ResourceLocation resource = null;
            if (resource == null) {
                resource = new ResourceLocation(par1Str);
            }
            final PositionedSoundRecord var8 = PositionedSoundRecord.func_147675_a(resource, (float)par2, (float)par3, (float)par4);
            this.mapSoundPositions.put(var5, var8);
            this.mc.getSoundHandler().playSound(var8);
        }
    }
    
    @Override
    public void playSound(final String par1Str, final double par2, final double par4, final double par6, final float par8, final float par9) {
    }
    
    @Override
    public void playSoundToNearExcept(final EntityPlayer par1EntityPlayer, final String par2Str, final double par3, final double par5, final double par7, final float par9, final float par10) {
    }
    
    @Override
    public void spawnParticle(final String par1Str, final double par2, final double par4, final double par6, final double par8, final double par10, final double par12) {
        try {
            this.doSpawnParticle(par1Str, par2, par4, par6, par8, par10, par12);
        }
        catch (Throwable var16) {
            final CrashReport var15 = CrashReport.makeCrashReport(var16, "Exception while adding particle");
            final CrashReportCategory var17 = var15.makeCategory("Particle being added");
            var17.addCrashSection("Name", par1Str);
            var17.addCrashSectionCallable("Position", new Callable() {
                @Override
                public String call() {
                    return CrashReportCategory.func_85074_a(par2, par4, par6);
                }
            });
            throw new ReportedException(var15);
        }
    }
    
    public EntityFX doSpawnParticle(final String par1Str, final double par2, final double par4, final double par6, final double par8, final double par10, final double par12) {
        if (this.mc == null || this.mc.renderViewEntity == null || this.mc.effectRenderer == null) {
            return null;
        }
        int var14 = this.mc.gameSettings.particleSetting;
        if (var14 == 1 && this.theWorld.rand.nextInt(3) == 0) {
            var14 = 2;
        }
        final double var15 = this.mc.renderViewEntity.posX - par2;
        final double var16 = this.mc.renderViewEntity.posY - par4;
        final double var17 = this.mc.renderViewEntity.posZ - par6;
        EntityFX var18 = null;
        if (par1Str.equals("hugeexplosion")) {
            if (Config.isAnimatedExplosion()) {
                this.mc.effectRenderer.addEffect(var18 = new EntityHugeExplodeFX(this.theWorld, par2, par4, par6, par8, par10, par12));
            }
        }
        else if (par1Str.equals("largeexplode")) {
            if (Config.isAnimatedExplosion()) {
                this.mc.effectRenderer.addEffect(var18 = new EntityLargeExplodeFX(this.renderEngine, this.theWorld, par2, par4, par6, par8, par10, par12));
            }
        }
        else if (par1Str.equals("fireworksSpark")) {
            this.mc.effectRenderer.addEffect(var18 = new EntityFireworkSparkFX(this.theWorld, par2, par4, par6, par8, par10, par12, this.mc.effectRenderer));
        }
        if (var18 != null) {
            return var18;
        }
        double var19 = 16.0;
        final double d3 = 16.0;
        if (par1Str.equals("crit")) {
            var19 = 196.0;
        }
        if (var15 * var15 + var16 * var16 + var17 * var17 > var19 * var19) {
            return null;
        }
        if (var14 > 1) {
            return null;
        }
        if (par1Str.equals("bubble")) {
            var18 = new EntityBubbleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            CustomColorizer.updateWaterFX(var18, this.theWorld);
        }
        else if (par1Str.equals("suspended")) {
            if (Config.isWaterParticles()) {
                var18 = new EntitySuspendFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            }
        }
        else if (par1Str.equals("depthsuspend")) {
            if (Config.isVoidParticles()) {
                var18 = new EntityAuraFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            }
        }
        else if (par1Str.equals("townaura")) {
            var18 = new EntityAuraFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            CustomColorizer.updateMyceliumFX(var18);
        }
        else if (par1Str.equals("crit")) {
            var18 = new EntityCritFX(this.theWorld, par2, par4, par6, par8, par10, par12);
        }
        else if (par1Str.equals("magicCrit")) {
            var18 = new EntityCritFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            var18.setRBGColorF(var18.getRedColorF() * 0.3f, var18.getGreenColorF() * 0.8f, var18.getBlueColorF());
            var18.nextTextureIndexX();
        }
        else if (par1Str.equals("smoke")) {
            if (Config.isAnimatedSmoke()) {
                var18 = new EntitySmokeFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            }
        }
        else if (par1Str.equals("mobSpell")) {
            if (Config.isPotionParticles()) {
                var18 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, 0.0, 0.0, 0.0);
                var18.setRBGColorF((float)par8, (float)par10, (float)par12);
            }
        }
        else if (par1Str.equals("mobSpellAmbient")) {
            if (Config.isPotionParticles()) {
                var18 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, 0.0, 0.0, 0.0);
                var18.setAlphaF(0.15f);
                var18.setRBGColorF((float)par8, (float)par10, (float)par12);
            }
        }
        else if (par1Str.equals("spell")) {
            if (Config.isPotionParticles()) {
                var18 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            }
        }
        else if (par1Str.equals("instantSpell")) {
            if (Config.isPotionParticles()) {
                var18 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                ((EntitySpellParticleFX)var18).setBaseSpellTextureIndex(144);
            }
        }
        else if (par1Str.equals("witchMagic")) {
            if (Config.isPotionParticles()) {
                var18 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                ((EntitySpellParticleFX)var18).setBaseSpellTextureIndex(144);
                final float var20 = this.theWorld.rand.nextFloat() * 0.5f + 0.35f;
                var18.setRBGColorF(1.0f * var20, 0.0f * var20, 1.0f * var20);
            }
        }
        else if (par1Str.equals("note")) {
            var18 = new EntityNoteFX(this.theWorld, par2, par4, par6, par8, par10, par12);
        }
        else if (par1Str.equals("portal")) {
            if (Config.isPortalParticles()) {
                var18 = new EntityPortalFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                CustomColorizer.updatePortalFX(var18);
            }
        }
        else if (par1Str.equals("enchantmenttable")) {
            var18 = new EntityEnchantmentTableParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
        }
        else if (par1Str.equals("explode")) {
            if (Config.isAnimatedExplosion()) {
                var18 = new EntityExplodeFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            }
        }
        else if (par1Str.equals("flame")) {
            if (Config.isAnimatedFlame()) {
                var18 = new EntityFlameFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            }
        }
        else if (par1Str.equals("lava")) {
            var18 = new EntityLavaFX(this.theWorld, par2, par4, par6);
        }
        else if (par1Str.equals("footstep")) {
            var18 = new EntityFootStepFX(this.renderEngine, this.theWorld, par2, par4, par6);
        }
        else if (par1Str.equals("splash")) {
            var18 = new EntitySplashFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            CustomColorizer.updateWaterFX(var18, this.theWorld);
        }
        else if (par1Str.equals("wake")) {
            var18 = new EntityFishWakeFX(this.theWorld, par2, par4, par6, par8, par10, par12);
        }
        else if (par1Str.equals("largesmoke")) {
            if (Config.isAnimatedSmoke()) {
                var18 = new EntitySmokeFX(this.theWorld, par2, par4, par6, par8, par10, par12, 2.5f);
            }
        }
        else if (par1Str.equals("cloud")) {
            var18 = new EntityCloudFX(this.theWorld, par2, par4, par6, par8, par10, par12);
        }
        else if (par1Str.equals("reddust")) {
            if (Config.isAnimatedRedstone()) {
                var18 = new EntityReddustFX(this.theWorld, par2, par4, par6, (float)par8, (float)par10, (float)par12);
                CustomColorizer.updateReddustFX(var18, this.theWorld, var15, var16, var17);
            }
        }
        else if (par1Str.equals("snowballpoof")) {
            var18 = new EntityBreakingFX(this.theWorld, par2, par4, par6, Items.snowball);
        }
        else if (par1Str.equals("dripWater")) {
            if (Config.isDrippingWaterLava()) {
                var18 = new EntityDropParticleFX(this.theWorld, par2, par4, par6, Material.water);
            }
        }
        else if (par1Str.equals("dripLava")) {
            if (Config.isDrippingWaterLava()) {
                var18 = new EntityDropParticleFX(this.theWorld, par2, par4, par6, Material.lava);
            }
        }
        else if (par1Str.equals("snowshovel")) {
            var18 = new EntitySnowShovelFX(this.theWorld, par2, par4, par6, par8, par10, par12);
        }
        else if (par1Str.equals("slime")) {
            var18 = new EntityBreakingFX(this.theWorld, par2, par4, par6, Items.slime_ball);
        }
        else if (par1Str.equals("heart")) {
            var18 = new EntityHeartFX(this.theWorld, par2, par4, par6, par8, par10, par12);
        }
        else if (par1Str.equals("angryVillager")) {
            var18 = new EntityHeartFX(this.theWorld, par2, par4 + 0.5, par6, par8, par10, par12);
            var18.setParticleTextureIndex(81);
            var18.setRBGColorF(1.0f, 1.0f, 1.0f);
        }
        else if (par1Str.equals("happyVillager")) {
            var18 = new EntityAuraFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            var18.setParticleTextureIndex(82);
            var18.setRBGColorF(1.0f, 1.0f, 1.0f);
        }
        else if (par1Str.startsWith("iconcrack_")) {
            final String[] var21 = par1Str.split("_", 3);
            final int var22 = Integer.parseInt(var21[1]);
            if (var21.length > 2) {
                final int var23 = Integer.parseInt(var21[2]);
                var18 = new EntityBreakingFX(this.theWorld, par2, par4, par6, par8, par10, par12, Item.getItemById(var22), var23);
            }
            else {
                var18 = new EntityBreakingFX(this.theWorld, par2, par4, par6, par8, par10, par12, Item.getItemById(var22), 0);
            }
        }
        else if (par1Str.startsWith("blockcrack_")) {
            final String[] var21 = par1Str.split("_", 3);
            final Block var24 = Block.getBlockById(Integer.parseInt(var21[1]));
            final int var23 = Integer.parseInt(var21[2]);
            var18 = new EntityDiggingFX(this.theWorld, par2, par4, par6, par8, par10, par12, var24, var23).applyRenderColor(var23);
        }
        else if (par1Str.startsWith("blockdust_")) {
            final String[] var21 = par1Str.split("_", 3);
            final Block var24 = Block.getBlockById(Integer.parseInt(var21[1]));
            final int var23 = Integer.parseInt(var21[2]);
            var18 = new EntityBlockDustFX(this.theWorld, par2, par4, par6, par8, par10, par12, var24, var23).applyRenderColor(var23);
        }
        if (var18 != null) {
            this.mc.effectRenderer.addEffect(var18);
        }
        return var18;
    }
    
    @Override
    public void onEntityCreate(final Entity par1Entity) {
        if (Config.isDynamicLights()) {
            DynamicLights.entityAdded(par1Entity, this);
        }
    }
    
    @Override
    public void onEntityDestroy(final Entity par1Entity) {
        if (Config.isDynamicLights()) {
            DynamicLights.entityRemoved(par1Entity, this);
        }
    }
    
    public void deleteAllDisplayLists() {
        GLAllocation.deleteDisplayLists(this.glRenderListBase);
        this.displayListAllocator.deleteDisplayLists();
    }
    
    @Override
    public void broadcastSound(final int par1, final int par2, final int par3, final int par4, final int par5) {
        final Random var6 = this.theWorld.rand;
        switch (par1) {
            case 1013:
            case 1018: {
                if (this.mc.renderViewEntity == null) {
                    break;
                }
                final double var7 = par2 - this.mc.renderViewEntity.posX;
                final double var8 = par3 - this.mc.renderViewEntity.posY;
                final double var9 = par4 - this.mc.renderViewEntity.posZ;
                final double var10 = Math.sqrt(var7 * var7 + var8 * var8 + var9 * var9);
                double var11 = this.mc.renderViewEntity.posX;
                double var12 = this.mc.renderViewEntity.posY;
                double var13 = this.mc.renderViewEntity.posZ;
                if (var10 > 0.0) {
                    var11 += var7 / var10 * 2.0;
                    var12 += var8 / var10 * 2.0;
                    var13 += var9 / var10 * 2.0;
                }
                if (par1 == 1013) {
                    this.theWorld.playSound(var11, var12, var13, "mob.wither.spawn", 1.0f, 1.0f, false);
                    break;
                }
                if (par1 == 1018) {
                    this.theWorld.playSound(var11, var12, var13, "mob.enderdragon.end", 5.0f, 1.0f, false);
                    break;
                }
                break;
            }
        }
    }
    
    @Override
    public void playAuxSFX(final EntityPlayer par1EntityPlayer, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final Random var7 = this.theWorld.rand;
        Block var8 = null;
        switch (par2) {
            case 1000: {
                this.theWorld.playSound(par3, par4, par5, "random.click", 1.0f, 1.0f, false);
                break;
            }
            case 1001: {
                this.theWorld.playSound(par3, par4, par5, "random.click", 1.0f, 1.2f, false);
                break;
            }
            case 1002: {
                this.theWorld.playSound(par3, par4, par5, "random.bow", 1.0f, 1.2f, false);
                break;
            }
            case 1003: {
                if (Math.random() < 0.5) {
                    this.theWorld.playSound(par3 + 0.5, par4 + 0.5, par5 + 0.5, "random.door_open", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                    break;
                }
                this.theWorld.playSound(par3 + 0.5, par4 + 0.5, par5 + 0.5, "random.door_close", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1004: {
                this.theWorld.playSound(par3 + 0.5f, par4 + 0.5f, par5 + 0.5f, "random.fizz", 0.5f, 2.6f + (var7.nextFloat() - var7.nextFloat()) * 0.8f, false);
                break;
            }
            case 1005: {
                if (Item.getItemById(par6) instanceof ItemRecord) {
                    this.theWorld.playRecord("records." + ((ItemRecord)Item.getItemById(par6)).field_150929_a, par3, par4, par5);
                    break;
                }
                this.theWorld.playRecord(null, par3, par4, par5);
                break;
            }
            case 1007: {
                this.theWorld.playSound(par3 + 0.5, par4 + 0.5, par5 + 0.5, "mob.ghast.charge", 10.0f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1008: {
                this.theWorld.playSound(par3 + 0.5, par4 + 0.5, par5 + 0.5, "mob.ghast.fireball", 10.0f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1009: {
                this.theWorld.playSound(par3 + 0.5, par4 + 0.5, par5 + 0.5, "mob.ghast.fireball", 2.0f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1010: {
                this.theWorld.playSound(par3 + 0.5, par4 + 0.5, par5 + 0.5, "mob.zombie.wood", 2.0f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1011: {
                this.theWorld.playSound(par3 + 0.5, par4 + 0.5, par5 + 0.5, "mob.zombie.metal", 2.0f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1012: {
                this.theWorld.playSound(par3 + 0.5, par4 + 0.5, par5 + 0.5, "mob.zombie.woodbreak", 2.0f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1014: {
                this.theWorld.playSound(par3 + 0.5, par4 + 0.5, par5 + 0.5, "mob.wither.shoot", 2.0f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1015: {
                this.theWorld.playSound(par3 + 0.5, par4 + 0.5, par5 + 0.5, "mob.bat.takeoff", 0.05f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1016: {
                this.theWorld.playSound(par3 + 0.5, par4 + 0.5, par5 + 0.5, "mob.zombie.infect", 2.0f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1017: {
                this.theWorld.playSound(par3 + 0.5, par4 + 0.5, par5 + 0.5, "mob.zombie.unfect", 2.0f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1020: {
                this.theWorld.playSound(par3 + 0.5f, par4 + 0.5f, par5 + 0.5f, "random.anvil_break", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1021: {
                this.theWorld.playSound(par3 + 0.5f, par4 + 0.5f, par5 + 0.5f, "random.anvil_use", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1022: {
                this.theWorld.playSound(par3 + 0.5f, par4 + 0.5f, par5 + 0.5f, "random.anvil_land", 0.3f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 2000: {
                final int var9 = par6 % 3 - 1;
                final int var10 = par6 / 3 % 3 - 1;
                final double var11 = par3 + var9 * 0.6 + 0.5;
                final double var12 = par4 + 0.5;
                final double var13 = par5 + var10 * 0.6 + 0.5;
                for (int var14 = 0; var14 < 10; ++var14) {
                    final double var15 = var7.nextDouble() * 0.2 + 0.01;
                    final double var16 = var11 + var9 * 0.01 + (var7.nextDouble() - 0.5) * var10 * 0.5;
                    final double var17 = var12 + (var7.nextDouble() - 0.5) * 0.5;
                    final double var18 = var13 + var10 * 0.01 + (var7.nextDouble() - 0.5) * var9 * 0.5;
                    final double var19 = var9 * var15 + var7.nextGaussian() * 0.01;
                    final double var20 = -0.03 + var7.nextGaussian() * 0.01;
                    final double var21 = var10 * var15 + var7.nextGaussian() * 0.01;
                    this.spawnParticle("smoke", var16, var17, var18, var19, var20, var21);
                }
            }
            case 2001: {
                var8 = Block.getBlockById(par6 & 0xFFF);
                if (var8.getMaterial() != Material.air) {
                    this.mc.getSoundHandler().playSound(new PositionedSoundRecord(new ResourceLocation(var8.stepSound.func_150495_a()), (var8.stepSound.func_150497_c() + 1.0f) / 2.0f, var8.stepSound.func_150494_d() * 0.8f, par3 + 0.5f, par4 + 0.5f, par5 + 0.5f));
                }
                this.mc.effectRenderer.func_147215_a(par3, par4, par5, var8, par6 >> 12 & 0xFF);
                break;
            }
            case 2002: {
                final double var22 = par3;
                final double var11 = par4;
                final double var12 = par5;
                final String var23 = "iconcrack_" + Item.getIdFromItem(Items.potionitem) + "_" + par6;
                for (int var24 = 0; var24 < 8; ++var24) {
                    this.spawnParticle(var23, var22, var11, var12, var7.nextGaussian() * 0.15, var7.nextDouble() * 0.2, var7.nextGaussian() * 0.15);
                }
                int var24 = Items.potionitem.getColorFromDamage(par6);
                final float var25 = (var24 >> 16 & 0xFF) / 255.0f;
                final float var26 = (var24 >> 8 & 0xFF) / 255.0f;
                final float var27 = (var24 >> 0 & 0xFF) / 255.0f;
                String var28 = "spell";
                if (Items.potionitem.isEffectInstant(par6)) {
                    var28 = "instantSpell";
                }
                for (int var29 = 0; var29 < 100; ++var29) {
                    final double var17 = var7.nextDouble() * 4.0;
                    final double var18 = var7.nextDouble() * 3.141592653589793 * 2.0;
                    final double var19 = Math.cos(var18) * var17;
                    final double var20 = 0.01 + var7.nextDouble() * 0.5;
                    final double var21 = Math.sin(var18) * var17;
                    final EntityFX var30 = this.doSpawnParticle(var28, var22 + var19 * 0.1, var11 + 0.3, var12 + var21 * 0.1, var19, var20, var21);
                    if (var30 != null) {
                        final float var31 = 0.75f + var7.nextFloat() * 0.25f;
                        var30.setRBGColorF(var25 * var31, var26 * var31, var27 * var31);
                        var30.multiplyVelocity((float)var17);
                    }
                }
                this.theWorld.playSound(par3 + 0.5, par4 + 0.5, par5 + 0.5, "game.potion.smash", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 2003: {
                final double var22 = par3 + 0.5;
                final double var11 = par4;
                final double var12 = par5 + 0.5;
                final String var23 = "iconcrack_" + Item.getIdFromItem(Items.ender_eye);
                for (int var24 = 0; var24 < 8; ++var24) {
                    this.spawnParticle(var23, var22, var11, var12, var7.nextGaussian() * 0.15, var7.nextDouble() * 0.2, var7.nextGaussian() * 0.15);
                }
                for (double var32 = 0.0; var32 < 6.283185307179586; var32 += 0.15707963267948966) {
                    this.spawnParticle("portal", var22 + Math.cos(var32) * 5.0, var11 - 0.4, var12 + Math.sin(var32) * 5.0, Math.cos(var32) * -5.0, 0.0, Math.sin(var32) * -5.0);
                    this.spawnParticle("portal", var22 + Math.cos(var32) * 5.0, var11 - 0.4, var12 + Math.sin(var32) * 5.0, Math.cos(var32) * -7.0, 0.0, Math.sin(var32) * -7.0);
                }
            }
            case 2004: {
                for (int var29 = 0; var29 < 20; ++var29) {
                    final double var17 = par3 + 0.5 + (this.theWorld.rand.nextFloat() - 0.5) * 2.0;
                    final double var18 = par4 + 0.5 + (this.theWorld.rand.nextFloat() - 0.5) * 2.0;
                    final double var19 = par5 + 0.5 + (this.theWorld.rand.nextFloat() - 0.5) * 2.0;
                    this.theWorld.spawnParticle("smoke", var17, var18, var19, 0.0, 0.0, 0.0);
                    this.theWorld.spawnParticle("flame", var17, var18, var19, 0.0, 0.0, 0.0);
                }
            }
            case 2005: {
                ItemDye.func_150918_a(this.theWorld, par3, par4, par5, par6);
                break;
            }
            case 2006: {
                var8 = this.theWorld.getBlock(par3, par4, par5);
                if (var8.getMaterial() != Material.air) {
                    double var32 = Math.min(0.2f + par6 / 15.0f, 10.0f);
                    if (var32 > 2.5) {
                        var32 = 2.5;
                    }
                    for (int var33 = (int)(150.0 * var32), var34 = 0; var34 < var33; ++var34) {
                        final float var35 = MathHelper.randomFloatClamp(var7, 0.0f, 6.2831855f);
                        final double var19 = MathHelper.randomFloatClamp(var7, 0.75f, 1.0f);
                        final double var20 = 0.20000000298023224 + var32 / 100.0;
                        final double var21 = MathHelper.cos(var35) * 0.2f * var19 * var19 * (var32 + 0.2);
                        final double var36 = MathHelper.sin(var35) * 0.2f * var19 * var19 * (var32 + 0.2);
                        this.theWorld.spawnParticle("blockdust_" + Block.getIdFromBlock(var8) + "_" + this.theWorld.getBlockMetadata(par3, par4, par5), par3 + 0.5f, par4 + 1.0f, par5 + 0.5f, var21, var20, var36);
                    }
                    break;
                }
                break;
            }
        }
    }
    
    @Override
    public void destroyBlockPartially(final int p_147587_1_, final int p_147587_2_, final int p_147587_3_, final int p_147587_4_, final int p_147587_5_) {
        if (p_147587_5_ >= 0 && p_147587_5_ < 10) {
            DestroyBlockProgress var6 = this.damagedBlocks.get(p_147587_1_);
            if (var6 == null || var6.getPartialBlockX() != p_147587_2_ || var6.getPartialBlockY() != p_147587_3_ || var6.getPartialBlockZ() != p_147587_4_) {
                var6 = new DestroyBlockProgress(p_147587_1_, p_147587_2_, p_147587_3_, p_147587_4_);
                this.damagedBlocks.put(p_147587_1_, var6);
            }
            var6.setPartialBlockDamage(p_147587_5_);
            var6.setCloudUpdateTick(this.cloudTickCounter);
        }
        else {
            this.damagedBlocks.remove(p_147587_1_);
        }
    }
    
    public void registerDestroyBlockIcons(final IIconRegister par1IconRegister) {
        this.destroyBlockIcons = new IIcon[10];
        for (int var2 = 0; var2 < this.destroyBlockIcons.length; ++var2) {
            this.destroyBlockIcons[var2] = par1IconRegister.registerIcon("destroy_stage_" + var2);
        }
    }
    
    public void setAllRenderersVisible() {
        if (this.worldRenderers != null) {
            for (int i = 0; i < this.worldRenderers.length; ++i) {
                this.worldRenderers[i].isVisible = true;
            }
        }
    }
    
    public boolean isMoving(final EntityLivingBase entityliving) {
        final boolean moving = this.isMovingNow(entityliving);
        if (moving) {
            this.lastMovedTime = System.currentTimeMillis();
            return true;
        }
        return System.currentTimeMillis() - this.lastMovedTime < 2000L;
    }
    
    private boolean isMovingNow(final EntityLivingBase entityliving) {
        final double maxDiff = 0.001;
        return entityliving.isSneaking() || entityliving.prevSwingProgress > maxDiff || this.mc.mouseHelper.deltaX != 0 || this.mc.mouseHelper.deltaY != 0 || Math.abs(entityliving.posX - entityliving.prevPosX) > maxDiff || Math.abs(entityliving.posY - entityliving.prevPosY) > maxDiff || Math.abs(entityliving.posZ - entityliving.prevPosZ) > maxDiff;
    }
    
    public boolean isActing() {
        final boolean acting = this.isActingNow();
        if (acting) {
            this.lastActionTime = System.currentTimeMillis();
            return true;
        }
        return System.currentTimeMillis() - this.lastActionTime < 500L;
    }
    
    public boolean isActingNow() {
        return Mouse.isButtonDown(0) || Mouse.isButtonDown(1);
    }
    
    public int renderAllSortedRenderers(final int renderPass, final double partialTicks) {
        return this.renderSortedRenderers(0, this.countSortedWorldRenderers, renderPass, partialTicks);
    }
    
    public void updateCapes() {
    }
    
    public AxisAlignedBB getTileEntityBoundingBox(final TileEntity te) {
        if (!te.hasWorldObj()) {
            return RenderGlobal.AABB_INFINITE;
        }
        final Block blockType = te.getBlockType();
        if (blockType == Blocks.enchanting_table) {
            return AxisAlignedBB.getBoundingBox(te.field_145851_c, te.field_145848_d, te.field_145849_e, te.field_145851_c + 1, te.field_145848_d + 1, te.field_145849_e + 1);
        }
        if (blockType != Blocks.chest && blockType != Blocks.trapped_chest) {
            if (blockType != null && blockType != Blocks.beacon) {
                final AxisAlignedBB blockAabb = blockType.getCollisionBoundingBoxFromPool(te.getWorldObj(), te.field_145851_c, te.field_145848_d, te.field_145849_e);
                if (blockAabb != null) {
                    return blockAabb;
                }
            }
            return RenderGlobal.AABB_INFINITE;
        }
        return AxisAlignedBB.getBoundingBox(te.field_145851_c - 1, te.field_145848_d, te.field_145849_e - 1, te.field_145851_c + 2, te.field_145848_d + 2, te.field_145849_e + 2);
    }
    
    public void addToSortedWorldRenderers(final WorldRenderer wr) {
        if (!wr.inSortedWorldRenderers) {
            int pos = this.countSortedWorldRenderers;
            wr.updateDistanceToEntitySquared(this.renderViewEntity);
            final float distSq = wr.sortDistanceToEntitySquared;
            if (this.countSortedWorldRenderers > 0) {
                int countGreater = 0;
                int high = this.countSortedWorldRenderers - 1;
                int mid = (countGreater + high) / 2;
                while (countGreater <= high) {
                    mid = (countGreater + high) / 2;
                    final WorldRenderer wrMid = this.sortedWorldRenderers[mid];
                    if (distSq < wrMid.sortDistanceToEntitySquared) {
                        high = mid - 1;
                    }
                    else {
                        countGreater = mid + 1;
                    }
                }
                if (countGreater > mid) {
                    pos = mid + 1;
                }
                else {
                    pos = mid;
                }
            }
            int countGreater = this.countSortedWorldRenderers - pos;
            if (countGreater > 0) {
                System.arraycopy(this.sortedWorldRenderers, pos, this.sortedWorldRenderers, pos + 1, countGreater);
            }
            this.sortedWorldRenderers[pos] = wr;
            wr.inSortedWorldRenderers = true;
            ++this.countSortedWorldRenderers;
        }
    }
    
    public int getCountRenderers() {
        return this.renderersLoaded;
    }
    
    public int getCountActiveRenderers() {
        return this.renderersBeingRendered;
    }
    
    public int getCountEntitiesRendered() {
        return this.countEntitiesRendered;
    }
    
    public int getCountTileEntitiesRendered() {
        return this.countTileEntitiesRendered;
    }
    
    static {
        logger = LogManager.getLogger();
        locationMoonPhasesPng = new ResourceLocation("textures/environment/moon_phases.png");
        locationSunPng = new ResourceLocation("textures/environment/sun.png");
        locationCloudsPng = new ResourceLocation("textures/environment/clouds.png");
        locationEndSkyPng = new ResourceLocation("textures/environment/end_sky.png");
        RenderGlobal.AABB_INFINITE = AxisAlignedBB.getBoundingBox(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }
}
