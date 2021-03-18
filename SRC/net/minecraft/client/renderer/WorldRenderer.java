package net.minecraft.client.renderer;

import net.minecraft.client.shader.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.world.chunk.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import net.minecraft.src.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.culling.*;

public class WorldRenderer
{
    protected TesselatorVertexState vertexState;
    public World worldObj;
    protected int glRenderList;
    protected Tessellator tessellator;
    public static volatile int chunksUpdated;
    public int posX;
    public int posY;
    public int posZ;
    public int posXMinus;
    public int posYMinus;
    public int posZMinus;
    public int posXClip;
    public int posYClip;
    public int posZClip;
    public boolean isInFrustum;
    public boolean[] skipRenderPass;
    public int posXPlus;
    public int posYPlus;
    public int posZPlus;
    public volatile boolean needsUpdate;
    public AxisAlignedBB rendererBoundingBox;
    public int chunkIndex;
    public boolean isVisible;
    public boolean isWaitingOnOcclusionQuery;
    public int glOcclusionQuery;
    public boolean isChunkLit;
    protected boolean isInitialized;
    public List tileEntityRenderers;
    protected List tileEntities;
    protected int bytesDrawn;
    public boolean isVisibleFromPosition;
    public double visibleFromX;
    public double visibleFromY;
    public double visibleFromZ;
    public boolean isInFrustrumFully;
    protected boolean needsBoxUpdate;
    public volatile boolean isUpdating;
    public float sortDistanceToEntitySquared;
    public double distanceToEntityXzSq;
    protected boolean skipAllRenderPasses;
    public boolean inSortedWorldRenderers;
    public boolean needVertexStateResort;
    public RenderGlobal renderGlobal;
    public static int globalChunkOffsetX;
    public static int globalChunkOffsetZ;
    
    public WorldRenderer(final World par1World, final List par2List, final int par3, final int par4, final int par5, final int par6) {
        this.tessellator = Tessellator.instance;
        this.skipRenderPass = new boolean[] { true, true };
        this.tileEntityRenderers = new ArrayList();
        this.isVisibleFromPosition = false;
        this.isInFrustrumFully = false;
        this.needsBoxUpdate = false;
        this.isUpdating = false;
        this.skipAllRenderPasses = true;
        this.renderGlobal = Minecraft.getMinecraft().renderGlobal;
        this.glRenderList = -1;
        this.isInFrustum = false;
        this.isVisible = true;
        this.isInitialized = false;
        this.worldObj = par1World;
        this.vertexState = null;
        this.tileEntities = par2List;
        this.glRenderList = par6;
        this.posX = -999;
        this.setPosition(par3, par4, par5);
        this.needsUpdate = false;
    }
    
    public void setPosition(final int par1, final int par2, final int par3) {
        if (par1 != this.posX || par2 != this.posY || par3 != this.posZ) {
            this.setDontDraw();
            this.posX = par1;
            this.posY = par2;
            this.posZ = par3;
            this.posXPlus = par1 + 8;
            this.posYPlus = par2 + 8;
            this.posZPlus = par3 + 8;
            this.posXClip = (par1 & 0x3FF);
            this.posYClip = par2;
            this.posZClip = (par3 & 0x3FF);
            this.posXMinus = par1 - this.posXClip;
            this.posYMinus = par2 - this.posYClip;
            this.posZMinus = par3 - this.posZClip;
            this.rendererBoundingBox = AxisAlignedBB.getBoundingBox(par1, par2, par3, par1 + 16, par2 + 16, par3 + 16);
            this.needsBoxUpdate = true;
            this.markDirty();
            this.isVisibleFromPosition = false;
        }
    }
    
    protected void setupGLTranslation() {
        GL11.glTranslatef((float)this.posXClip, (float)this.posYClip, (float)this.posZClip);
    }
    
    public void updateRenderer(final EntityLivingBase p_147892_1_) {
        if (this.worldObj != null) {
            if (this.needsBoxUpdate) {
                GL11.glNewList(this.glRenderList + 2, 4864);
                Render.renderAABB(AxisAlignedBB.getBoundingBox(this.posXClip, this.posYClip, this.posZClip, this.posXClip + 16, this.posYClip + 16, this.posZClip + 16));
                GL11.glEndList();
                this.needsBoxUpdate = false;
            }
            this.isVisible = true;
            this.isVisibleFromPosition = false;
            if (this.needsUpdate) {
                this.needsUpdate = false;
                final int xMin = this.posX;
                final int yMin = this.posY;
                final int zMain = this.posZ;
                final int xMax = this.posX + 16;
                final int yMax = this.posY + 16;
                final int zMax = this.posZ + 16;
                for (int var26 = 0; var26 < 2; ++var26) {
                    this.skipRenderPass[var26] = true;
                }
                this.skipAllRenderPasses = true;
                Chunk.isLit = false;
                final HashSet var27 = new HashSet();
                var27.addAll(this.tileEntityRenderers);
                this.tileEntityRenderers.clear();
                final Minecraft var28 = Minecraft.getMinecraft();
                final EntityLivingBase var29 = var28.renderViewEntity;
                final int viewEntityPosX = MathHelper.floor_double(var29.posX);
                final int viewEntityPosY = MathHelper.floor_double(var29.posY);
                final int viewEntityPosZ = MathHelper.floor_double(var29.posZ);
                final byte var30 = 1;
                final ChunkCacheOF chunkcache = new ChunkCacheOF(this.worldObj, xMin - var30, yMin - var30, zMain - var30, xMax + var30, yMax + var30, zMax + var30, var30);
                if (!chunkcache.extendedLevelsInChunkCache()) {
                    ++WorldRenderer.chunksUpdated;
                    chunkcache.renderStart();
                    final RenderBlocks var31 = new RenderBlocks(chunkcache);
                    this.bytesDrawn = 0;
                    this.vertexState = null;
                    this.tessellator = Tessellator.instance;
                    for (int renderPass = 0; renderPass < 2; ++renderPass) {
                        boolean renderNextPass = false;
                        boolean hasRenderedBlocks = false;
                        boolean hasGlList = false;
                        for (int y = yMin; y < yMax; ++y) {
                            for (int z = zMain; z < zMax; ++z) {
                                for (int x = xMin; x < xMax; ++x) {
                                    final Block block = chunkcache.getBlock(x, y, z);
                                    if (block.getMaterial() != Material.air) {
                                        if (!hasGlList) {
                                            hasGlList = true;
                                            this.preRenderBlocks(renderPass);
                                        }
                                        boolean hasTileEntity = false;
                                        hasTileEntity = block.hasTileEntity();
                                        if (renderPass == 0 && hasTileEntity) {
                                            final TileEntity blockPass = chunkcache.getTileEntity(x, y, z);
                                            if (TileEntityRendererDispatcher.instance.hasSpecialRenderer(blockPass)) {
                                                this.tileEntityRenderers.add(blockPass);
                                            }
                                        }
                                        final int var32 = block.getRenderBlockPass();
                                        if (var32 > renderPass) {
                                            renderNextPass = true;
                                        }
                                        final boolean canRender = var32 == renderPass;
                                        if (canRender) {
                                            hasRenderedBlocks |= var31.renderBlockByRenderType(block, x, y, z);
                                            if (block.getRenderType() == 0 && x == viewEntityPosX && y == viewEntityPosY && z == viewEntityPosZ) {
                                                var31.setRenderFromInside(true);
                                                var31.setRenderAllFaces(true);
                                                var31.renderBlockByRenderType(block, x, y, z);
                                                var31.setRenderFromInside(false);
                                                var31.setRenderAllFaces(false);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (hasRenderedBlocks) {
                            this.skipRenderPass[renderPass] = false;
                        }
                        if (hasGlList) {
                            this.postRenderBlocks(renderPass, p_147892_1_);
                        }
                        else {
                            hasRenderedBlocks = false;
                        }
                        if (!renderNextPass) {
                            break;
                        }
                    }
                    chunkcache.renderFinish();
                }
                final HashSet var33 = new HashSet();
                var33.addAll(this.tileEntityRenderers);
                var33.removeAll(var27);
                this.tileEntities.addAll(var33);
                var27.removeAll(this.tileEntityRenderers);
                this.tileEntities.removeAll(var27);
                this.isChunkLit = Chunk.isLit;
                this.isInitialized = true;
                this.skipAllRenderPasses = (this.skipRenderPass[0] && this.skipRenderPass[1]);
                this.updateFinished();
            }
        }
    }
    
    protected void preRenderBlocks(final int renderpass) {
        GL11.glNewList(this.glRenderList + renderpass, 4864);
        this.tessellator.setRenderingChunk(true);
        if (Config.isFastRender()) {
            this.tessellator.startDrawingQuads();
            this.tessellator.setTranslation(-WorldRenderer.globalChunkOffsetX, 0.0, -WorldRenderer.globalChunkOffsetZ);
        }
        else {
            GL11.glPushMatrix();
            this.setupGLTranslation();
            final float var2 = 1.000001f;
            GL11.glTranslatef(-8.0f, -8.0f, -8.0f);
            GL11.glScalef(var2, var2, var2);
            GL11.glTranslatef(8.0f, 8.0f, 8.0f);
            this.tessellator.startDrawingQuads();
            this.tessellator.setTranslation(-this.posX, -this.posY, -this.posZ);
        }
    }
    
    protected void postRenderBlocks(final int renderpass, final EntityLivingBase entityLiving) {
        if (Config.isTranslucentBlocksFancy() && renderpass == 1 && !this.skipRenderPass[renderpass]) {
            this.vertexState = this.tessellator.getVertexState((float)entityLiving.posX, (float)entityLiving.posY, (float)entityLiving.posZ);
        }
        this.bytesDrawn += this.tessellator.draw();
        this.tessellator.setRenderingChunk(false);
        if (!Config.isFastRender()) {
            GL11.glPopMatrix();
        }
        GL11.glEndList();
        this.tessellator.setTranslation(0.0, 0.0, 0.0);
    }
    
    public void updateRendererSort(final EntityLivingBase p_147889_1_) {
        if (this.vertexState != null && !this.skipRenderPass[1]) {
            this.tessellator = Tessellator.instance;
            this.preRenderBlocks(1);
            this.tessellator.setVertexState(this.vertexState);
            this.postRenderBlocks(1, p_147889_1_);
        }
    }
    
    public float distanceToEntitySquared(final Entity par1Entity) {
        final float var2 = (float)(par1Entity.posX - this.posXPlus);
        final float var3 = (float)(par1Entity.posY - this.posYPlus);
        final float var4 = (float)(par1Entity.posZ - this.posZPlus);
        return var2 * var2 + var3 * var3 + var4 * var4;
    }
    
    public void setDontDraw() {
        for (int var1 = 0; var1 < 2; ++var1) {
            this.skipRenderPass[var1] = true;
        }
        this.skipAllRenderPasses = true;
        this.isInFrustum = false;
        this.isInitialized = false;
        this.vertexState = null;
    }
    
    public void stopRendering() {
        this.setDontDraw();
        this.worldObj = null;
    }
    
    public int getGLCallListForPass(final int par1) {
        return this.glRenderList + par1;
    }
    
    public void updateInFrustum(final ICamera par1ICamera) {
        this.isInFrustum = par1ICamera.isBoundingBoxInFrustum(this.rendererBoundingBox);
        if (this.isInFrustum && Config.isOcclusionFancy()) {
            this.isInFrustrumFully = par1ICamera.isBoundingBoxInFrustumFully(this.rendererBoundingBox);
        }
        else {
            this.isInFrustrumFully = false;
        }
    }
    
    public void callOcclusionQueryList() {
        GL11.glCallList(this.glRenderList + 2);
    }
    
    public boolean skipAllRenderPasses() {
        return this.skipAllRenderPasses;
    }
    
    public void markDirty() {
        this.needsUpdate = true;
    }
    
    protected void updateFinished() {
        if (!this.skipAllRenderPasses && !this.inSortedWorldRenderers) {
            Minecraft.getMinecraft().renderGlobal.addToSortedWorldRenderers(this);
        }
    }
    
    public void updateDistanceToEntitySquared(final Entity entity) {
        final double dx = entity.posX - this.posXPlus;
        final double dy = entity.posY - this.posYPlus;
        final double dz = entity.posZ - this.posZPlus;
        final double dXzSq = dx * dx + dz * dz;
        this.distanceToEntityXzSq = dXzSq;
        this.sortDistanceToEntitySquared = (float)(dXzSq + dy * dy);
    }
    
    static {
        WorldRenderer.globalChunkOffsetX = 0;
        WorldRenderer.globalChunkOffsetZ = 0;
    }
}
