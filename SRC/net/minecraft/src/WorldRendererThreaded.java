package net.minecraft.src;

import net.minecraft.client.shader.*;
import net.minecraft.client.*;
import net.minecraft.world.chunk.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;

public class WorldRendererThreaded extends WorldRenderer
{
    private int glRenderListWork;
    private int glRenderListBoundingBox;
    public boolean[] tempSkipRenderPass;
    public TesselatorVertexState tempVertexState;
    private Tessellator tessellatorWork;
    
    public WorldRendererThreaded(final World par1World, final List par2List, final int par3, final int par4, final int par5, final int par6) {
        super(par1World, par2List, par3, par4, par5, par6);
        this.tempSkipRenderPass = new boolean[2];
        this.tessellatorWork = null;
        final RenderGlobal renderGlobal = Minecraft.getMinecraft().renderGlobal;
        this.glRenderListWork = renderGlobal.displayListAllocator.allocateDisplayLists(2);
        this.glRenderListBoundingBox = this.glRenderList + 2;
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
            this.updateRenderer((IWrUpdateListener)null);
            this.finishUpdate();
        }
    }
    
    public void updateRenderer(final IWrUpdateListener updateListener) {
        if (this.worldObj != null) {
            this.needsUpdate = false;
            final int xMin = this.posX;
            final int yMin = this.posY;
            final int zMin = this.posZ;
            final int xMax = this.posX + 16;
            final int yMax = this.posY + 16;
            final int zMax = this.posZ + 16;
            for (int hashset = 0; hashset < this.tempSkipRenderPass.length; ++hashset) {
                this.tempSkipRenderPass[hashset] = true;
            }
            Chunk.isLit = false;
            final HashSet var30 = new HashSet();
            var30.addAll(this.tileEntityRenderers);
            this.tileEntityRenderers.clear();
            final Minecraft var31 = Minecraft.getMinecraft();
            final EntityLivingBase var32 = var31.renderViewEntity;
            final int viewEntityPosX = MathHelper.floor_double(var32.posX);
            final int viewEntityPosY = MathHelper.floor_double(var32.posY);
            final int viewEntityPosZ = MathHelper.floor_double(var32.posZ);
            final byte one = 1;
            final ChunkCacheOF chunkcache = new ChunkCacheOF(this.worldObj, xMin - one, yMin - one, zMin - one, xMax + one, yMax + one, zMax + one, one);
            if (!chunkcache.extendedLevelsInChunkCache()) {
                ++WorldRendererThreaded.chunksUpdated;
                chunkcache.renderStart();
                final RenderBlocks hashset2 = new RenderBlocks(chunkcache);
                this.bytesDrawn = 0;
                this.tempVertexState = null;
                this.tessellator = Tessellator.instance;
                final WrUpdateControl uc = new WrUpdateControl();
                for (int renderPass = 0; renderPass < 2; ++renderPass) {
                    uc.setRenderPass(renderPass);
                    boolean renderNextPass = false;
                    boolean hasRenderedBlocks = false;
                    boolean hasGlList = false;
                    for (int y = yMin; y < yMax; ++y) {
                        if (hasRenderedBlocks && updateListener != null) {
                            updateListener.updating(uc);
                            this.tessellator = Tessellator.instance;
                        }
                        for (int z = zMin; z < zMax; ++z) {
                            for (int x = xMin; x < xMax; ++x) {
                                final Block block = chunkcache.getBlock(x, y, z);
                                if (block.getMaterial() != Material.air) {
                                    if (!hasGlList) {
                                        hasGlList = true;
                                        this.preRenderBlocksThreaded(renderPass);
                                    }
                                    boolean hasTileEntity = false;
                                    hasTileEntity = block.hasTileEntity();
                                    if (renderPass == 0 && hasTileEntity) {
                                        final TileEntity blockPass = chunkcache.getTileEntity(x, y, z);
                                        if (TileEntityRendererDispatcher.instance.hasSpecialRenderer(blockPass)) {
                                            this.tileEntityRenderers.add(blockPass);
                                        }
                                    }
                                    final int var33 = block.getRenderBlockPass();
                                    if (var33 > renderPass) {
                                        renderNextPass = true;
                                    }
                                    final boolean canRender = var33 == renderPass;
                                    if (canRender) {
                                        hasRenderedBlocks |= hashset2.renderBlockByRenderType(block, x, y, z);
                                        if (block.getRenderType() == 0 && x == viewEntityPosX && y == viewEntityPosY && z == viewEntityPosZ) {
                                            hashset2.setRenderFromInside(true);
                                            hashset2.setRenderAllFaces(true);
                                            hashset2.renderBlockByRenderType(block, x, y, z);
                                            hashset2.setRenderFromInside(false);
                                            hashset2.setRenderAllFaces(false);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (hasRenderedBlocks) {
                        this.tempSkipRenderPass[renderPass] = false;
                    }
                    if (hasGlList) {
                        if (updateListener != null) {
                            updateListener.updating(uc);
                        }
                        this.tessellator = Tessellator.instance;
                        this.postRenderBlocksThreaded(renderPass, this.renderGlobal.renderViewEntity);
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
            final HashSet var34 = new HashSet();
            var34.addAll(this.tileEntityRenderers);
            var34.removeAll(var30);
            this.tileEntities.addAll(var34);
            var30.removeAll(this.tileEntityRenderers);
            this.tileEntities.removeAll(var30);
            this.isChunkLit = Chunk.isLit;
            this.isInitialized = true;
        }
    }
    
    protected void preRenderBlocksThreaded(final int renderpass) {
        GL11.glNewList(this.glRenderListWork + renderpass, 4864);
        this.tessellator.setRenderingChunk(true);
        if (Config.isFastRender()) {
            this.tessellator.startDrawingQuads();
            this.tessellator.setTranslation(-WorldRendererThreaded.globalChunkOffsetX, 0.0, -WorldRendererThreaded.globalChunkOffsetZ);
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
    
    protected void postRenderBlocksThreaded(final int renderpass, final EntityLivingBase entityLiving) {
        if (Config.isTranslucentBlocksFancy() && renderpass == 1 && !this.tempSkipRenderPass[renderpass]) {
            this.tempVertexState = this.tessellator.getVertexState((float)entityLiving.posX, (float)entityLiving.posY, (float)entityLiving.posZ);
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
        final int temp = this.glRenderList;
        this.glRenderList = this.glRenderListWork;
        this.glRenderListWork = temp;
        for (int lightCache = 0; lightCache < 2; ++lightCache) {
            if (!this.skipRenderPass[lightCache]) {
                GL11.glNewList(this.glRenderListWork + lightCache, 4864);
                GL11.glEndList();
            }
        }
        for (int lightCache = 0; lightCache < 2; ++lightCache) {
            this.skipRenderPass[lightCache] = this.tempSkipRenderPass[lightCache];
        }
        this.skipAllRenderPasses = (this.skipRenderPass[0] && this.skipRenderPass[1]);
        if (this.needsBoxUpdate && !this.skipAllRenderPasses()) {
            GL11.glNewList(this.glRenderListBoundingBox, 4864);
            Render.renderAABB(AxisAlignedBB.getBoundingBox(this.posXClip, this.posYClip, this.posZClip, this.posXClip + 16, this.posYClip + 16, this.posZClip + 16));
            GL11.glEndList();
            this.needsBoxUpdate = false;
        }
        this.vertexState = this.tempVertexState;
        this.isVisible = true;
        this.isVisibleFromPosition = false;
        this.updateFinished();
    }
    
    @Override
    public void callOcclusionQueryList() {
        GL11.glCallList(this.glRenderListBoundingBox);
    }
}
