package net.minecraft.src;

import net.minecraft.client.shader.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.world.chunk.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.block.material.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;

public class WorldRendererSmooth extends WorldRenderer
{
    private WrUpdateState updateState;
    public int activeSet;
    public int[] activeListIndex;
    public int[][][] glWorkLists;
    public boolean[] tempSkipRenderPass;
    public TesselatorVertexState tempVertexState;
    
    public WorldRendererSmooth(final World par1World, final List par2List, final int par3, final int par4, final int par5, final int par6) {
        super(par1World, par2List, par3, par4, par5, par6);
        this.updateState = new WrUpdateState();
        this.activeSet = 0;
        this.activeListIndex = new int[] { 0, 0 };
        this.glWorkLists = new int[2][2][16];
        this.tempSkipRenderPass = new boolean[2];
    }
    
    private void checkGlWorkLists() {
        if (this.glWorkLists[0][0][0] <= 0) {
            final int glWorkBase = this.renderGlobal.displayListAllocator.allocateDisplayLists(64);
            for (int set = 0; set < 2; ++set) {
                final int setBase = glWorkBase + set * 2 * 16;
                for (int pass = 0; pass < 2; ++pass) {
                    final int passBase = setBase + pass * 16;
                    for (int t = 0; t < 16; ++t) {
                        this.glWorkLists[set][pass][t] = passBase + t;
                    }
                }
            }
        }
    }
    
    @Override
    public void setPosition(final int px, final int py, final int pz) {
        if (this.isUpdating) {
            WrUpdates.finishCurrentUpdate();
        }
        super.setPosition(px, py, pz);
    }
    
    public void updateRenderer() {
        if (this.worldObj != null) {
            this.updateRenderer(0L);
            this.finishUpdate();
        }
    }
    
    public boolean updateRenderer(final long finishTime) {
        if (this.worldObj == null) {
            return true;
        }
        this.needsUpdate = false;
        if (!this.isUpdating) {
            if (this.needsBoxUpdate) {
                GL11.glNewList(this.glRenderList + 2, 4864);
                Render.renderAABB(AxisAlignedBB.getBoundingBox(this.posXClip, this.posYClip, this.posZClip, this.posXClip + 16, this.posYClip + 16, this.posZClip + 16));
                GL11.glEndList();
                this.needsBoxUpdate = false;
            }
            Chunk.isLit = false;
        }
        final int var27 = this.posX;
        final int yMin = this.posY;
        final int zMin = this.posZ;
        final int xMax = this.posX + 16;
        final int yMax = this.posY + 16;
        final int zMax = this.posZ + 16;
        ChunkCacheOF chunkcache = null;
        RenderBlocks renderblocks = null;
        HashSet setOldEntityRenders = null;
        int viewEntityPosX = 0;
        int viewEntityPosY = 0;
        int viewEntityPosZ = 0;
        if (!this.isUpdating) {
            for (int setNewEntityRenderers = 0; setNewEntityRenderers < 2; ++setNewEntityRenderers) {
                this.tempSkipRenderPass[setNewEntityRenderers] = true;
            }
            this.tempVertexState = null;
            final Minecraft var28 = Minecraft.getMinecraft();
            final EntityLivingBase renderPass = var28.renderViewEntity;
            viewEntityPosX = MathHelper.floor_double(renderPass.posX);
            viewEntityPosY = MathHelper.floor_double(renderPass.posY);
            viewEntityPosZ = MathHelper.floor_double(renderPass.posZ);
            final byte renderNextPass = 1;
            chunkcache = new ChunkCacheOF(this.worldObj, var27 - renderNextPass, yMin - renderNextPass, zMin - renderNextPass, xMax + renderNextPass, yMax + renderNextPass, zMax + renderNextPass, renderNextPass);
            renderblocks = new RenderBlocks(chunkcache);
            setOldEntityRenders = new HashSet();
            setOldEntityRenders.addAll(this.tileEntityRenderers);
            this.tileEntityRenderers.clear();
        }
        if (this.isUpdating || !chunkcache.extendedLevelsInChunkCache()) {
            this.bytesDrawn = 0;
            if (!this.isUpdating) {
                chunkcache.renderStart();
            }
            this.tessellator = Tessellator.instance;
            this.checkGlWorkLists();
            for (int var29 = 0; var29 < 2; ++var29) {
                boolean var30 = false;
                boolean hasRenderedBlocks = false;
                boolean hasGlList = false;
                for (int y = yMin; y < yMax; ++y) {
                    if (this.isUpdating) {
                        this.isUpdating = false;
                        chunkcache = this.updateState.chunkcache;
                        renderblocks = this.updateState.renderblocks;
                        setOldEntityRenders = this.updateState.setOldEntityRenders;
                        viewEntityPosX = this.updateState.viewEntityPosX;
                        viewEntityPosY = this.updateState.viewEntityPosY;
                        viewEntityPosZ = this.updateState.viewEntityPosZ;
                        var29 = this.updateState.renderPass;
                        y = this.updateState.y;
                        var30 = this.updateState.flag;
                        hasRenderedBlocks = this.updateState.hasRenderedBlocks;
                        hasGlList = this.updateState.hasGlList;
                        if (hasGlList) {
                            this.preRenderBlocksSmooth(var29);
                        }
                    }
                    else if (hasGlList && finishTime != 0L && System.nanoTime() - finishTime > 0L && this.activeListIndex[var29] < 15) {
                        if (hasRenderedBlocks) {
                            this.tempSkipRenderPass[var29] = false;
                        }
                        this.postRenderBlocksSmooth(var29, this.renderGlobal.renderViewEntity, false);
                        final int[] activeListIndex = this.activeListIndex;
                        final int n = var29;
                        ++activeListIndex[n];
                        this.updateState.chunkcache = chunkcache;
                        this.updateState.renderblocks = renderblocks;
                        this.updateState.setOldEntityRenders = setOldEntityRenders;
                        this.updateState.viewEntityPosX = viewEntityPosX;
                        this.updateState.viewEntityPosY = viewEntityPosY;
                        this.updateState.viewEntityPosZ = viewEntityPosZ;
                        this.updateState.renderPass = var29;
                        this.updateState.y = y;
                        this.updateState.flag = var30;
                        this.updateState.hasRenderedBlocks = hasRenderedBlocks;
                        this.updateState.hasGlList = hasGlList;
                        this.isUpdating = true;
                        return false;
                    }
                    for (int z = zMin; z < zMax; ++z) {
                        for (int x = var27; x < xMax; ++x) {
                            final Block block = chunkcache.getBlock(x, y, z);
                            if (block.getMaterial() != Material.air) {
                                if (!hasGlList) {
                                    hasGlList = true;
                                    this.preRenderBlocksSmooth(var29);
                                }
                                final boolean hasTileEntity = block.hasTileEntity();
                                if (var29 == 0 && hasTileEntity) {
                                    final TileEntity blockPass = chunkcache.getTileEntity(x, y, z);
                                    if (TileEntityRendererDispatcher.instance.hasSpecialRenderer(blockPass)) {
                                        this.tileEntityRenderers.add(blockPass);
                                    }
                                }
                                final int var31 = block.getRenderBlockPass();
                                if (var31 > var29) {
                                    var30 = true;
                                }
                                final boolean canRender = var31 == var29;
                                if (canRender) {
                                    hasRenderedBlocks |= renderblocks.renderBlockByRenderType(block, x, y, z);
                                    if (block.getRenderType() == 0 && x == viewEntityPosX && y == viewEntityPosY && z == viewEntityPosZ) {
                                        renderblocks.setRenderFromInside(true);
                                        renderblocks.setRenderAllFaces(true);
                                        renderblocks.renderBlockByRenderType(block, x, y, z);
                                        renderblocks.setRenderFromInside(false);
                                        renderblocks.setRenderAllFaces(false);
                                    }
                                }
                            }
                        }
                    }
                }
                if (hasRenderedBlocks) {
                    this.tempSkipRenderPass[var29] = false;
                }
                if (hasGlList) {
                    this.postRenderBlocksSmooth(var29, this.renderGlobal.renderViewEntity, true);
                }
                else {
                    hasRenderedBlocks = false;
                }
                if (!var30) {
                    break;
                }
            }
            chunkcache.renderFinish();
        }
        final HashSet var32 = new HashSet();
        var32.addAll(this.tileEntityRenderers);
        var32.removeAll(setOldEntityRenders);
        this.tileEntities.addAll(var32);
        setOldEntityRenders.removeAll(this.tileEntityRenderers);
        this.tileEntities.removeAll(setOldEntityRenders);
        this.isChunkLit = Chunk.isLit;
        this.isInitialized = true;
        ++WorldRendererSmooth.chunksUpdated;
        this.isVisible = true;
        this.isVisibleFromPosition = false;
        this.skipRenderPass[0] = this.tempSkipRenderPass[0];
        this.skipRenderPass[1] = this.tempSkipRenderPass[1];
        this.skipAllRenderPasses = (this.skipRenderPass[0] && this.skipRenderPass[1]);
        this.vertexState = this.tempVertexState;
        this.isUpdating = false;
        this.updateFinished();
        return true;
    }
    
    protected void preRenderBlocksSmooth(final int renderpass) {
        GL11.glNewList(this.glWorkLists[this.activeSet][renderpass][this.activeListIndex[renderpass]], 4864);
        this.tessellator.setRenderingChunk(true);
        if (Config.isFastRender()) {
            this.tessellator.startDrawingQuads();
            this.tessellator.setTranslation(-WorldRendererSmooth.globalChunkOffsetX, 0.0, -WorldRendererSmooth.globalChunkOffsetZ);
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
    
    protected void postRenderBlocksSmooth(final int renderpass, final EntityLivingBase entityLiving, final boolean updateFinished) {
        if (Config.isTranslucentBlocksFancy() && renderpass == 1 && !this.tempSkipRenderPass[renderpass]) {
            final TesselatorVertexState tsv = this.tessellator.getVertexState((float)entityLiving.posX, (float)entityLiving.posY, (float)entityLiving.posZ);
            if (this.tempVertexState == null) {
                this.tempVertexState = tsv;
            }
            else {
                this.tempVertexState.addTessellatorVertexState(tsv);
            }
        }
        this.bytesDrawn += this.tessellator.draw();
        this.tessellator.setRenderingChunk(false);
        if (!Config.isFastRender()) {
            GL11.glPopMatrix();
        }
        GL11.glEndList();
        this.tessellator.setTranslation(0.0, 0.0, 0.0);
    }
    
    public void finishUpdate() {
        for (int pass = 0; pass < 2; ++pass) {
            if (!this.skipRenderPass[pass]) {
                GL11.glNewList(this.glRenderList + pass, 4864);
                for (int i = 0; i <= this.activeListIndex[pass]; ++i) {
                    final int list = this.glWorkLists[this.activeSet][pass][i];
                    GL11.glCallList(list);
                }
                GL11.glEndList();
            }
        }
        if (this.activeSet == 0) {
            this.activeSet = 1;
        }
        else {
            this.activeSet = 0;
        }
        for (int pass = 0; pass < 2; ++pass) {
            if (!this.skipRenderPass[pass]) {
                for (int i = 0; i <= this.activeListIndex[pass]; ++i) {
                    final int list = this.glWorkLists[this.activeSet][pass][i];
                    GL11.glNewList(list, 4864);
                    GL11.glEndList();
                }
            }
        }
        for (int pass = 0; pass < 2; ++pass) {
            this.activeListIndex[pass] = 0;
        }
    }
}
