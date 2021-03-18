package net.minecraft.client.renderer;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.src.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.shader.*;
import net.minecraft.client.util.*;
import java.util.*;
import java.nio.*;

public class Tessellator
{
    private ByteBuffer byteBuffer;
    private IntBuffer intBuffer;
    private FloatBuffer floatBuffer;
    private ShortBuffer shortBuffer;
    private int[] rawBuffer;
    private int vertexCount;
    private double textureU;
    private double textureV;
    private int brightness;
    private int color;
    private boolean hasColor;
    private boolean hasTexture;
    private boolean hasBrightness;
    private boolean hasNormals;
    private int rawBufferIndex;
    private int addedVertices;
    private boolean isColorDisabled;
    public int drawMode;
    public double xOffset;
    public double yOffset;
    public double zOffset;
    private int normal;
    public static Tessellator instance;
    public boolean isDrawing;
    private int bufferSize;
    private boolean renderingChunk;
    private static boolean littleEndianByteOrder;
    public static boolean renderingWorldRenderer;
    public boolean defaultTexture;
    public int textureID;
    public boolean autoGrow;
    private VertexData[] vertexDatas;
    private boolean[] drawnIcons;
    private TextureAtlasSprite[] vertexQuadIcons;
    
    public Tessellator() {
        this(65536);
        this.defaultTexture = false;
    }
    
    public Tessellator(final int par1) {
        this.renderingChunk = false;
        this.defaultTexture = true;
        this.textureID = 0;
        this.autoGrow = true;
        this.vertexDatas = null;
        this.drawnIcons = new boolean[256];
        this.vertexQuadIcons = null;
        this.bufferSize = par1;
        this.byteBuffer = GLAllocation.createDirectByteBuffer(par1 * 4);
        this.intBuffer = this.byteBuffer.asIntBuffer();
        this.floatBuffer = this.byteBuffer.asFloatBuffer();
        this.shortBuffer = this.byteBuffer.asShortBuffer();
        this.rawBuffer = new int[par1];
        this.vertexDatas = new VertexData[4];
        for (int ix = 0; ix < this.vertexDatas.length; ++ix) {
            this.vertexDatas[ix] = new VertexData();
        }
    }
    
    public int draw() {
        if (!this.isDrawing) {
            throw new IllegalStateException("Not tesselating!");
        }
        this.isDrawing = false;
        if (this.vertexCount > 0 && (!this.renderingChunk || !Config.isMultiTexture())) {
            this.intBuffer.clear();
            this.intBuffer.put(this.rawBuffer, 0, this.rawBufferIndex);
            this.byteBuffer.position(0);
            this.byteBuffer.limit(this.rawBufferIndex * 4);
            if (this.hasTexture) {
                this.floatBuffer.position(3);
                GL11.glTexCoordPointer(2, 32, this.floatBuffer);
                GL11.glEnableClientState(32888);
            }
            if (this.hasBrightness) {
                OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
                this.shortBuffer.position(14);
                GL11.glTexCoordPointer(2, 32, this.shortBuffer);
                GL11.glEnableClientState(32888);
                OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
            }
            if (this.hasColor) {
                this.byteBuffer.position(20);
                GL11.glColorPointer(4, true, 32, this.byteBuffer);
                GL11.glEnableClientState(32886);
            }
            if (this.hasNormals) {
                this.byteBuffer.position(24);
                GL11.glNormalPointer(32, this.byteBuffer);
                GL11.glEnableClientState(32885);
            }
            this.floatBuffer.position(0);
            GL11.glVertexPointer(3, 32, this.floatBuffer);
            GL11.glEnableClientState(32884);
            GL11.glDrawArrays(this.drawMode, 0, this.vertexCount);
            GL11.glDisableClientState(32884);
            if (this.hasTexture) {
                GL11.glDisableClientState(32888);
            }
            if (this.hasBrightness) {
                OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
                GL11.glDisableClientState(32888);
                OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
            }
            if (this.hasColor) {
                GL11.glDisableClientState(32886);
            }
            if (this.hasNormals) {
                GL11.glDisableClientState(32885);
            }
        }
        final int var1 = this.rawBufferIndex * 4;
        this.reset();
        return var1;
    }
    
    public TesselatorVertexState getVertexState(final float p_147564_1_, final float p_147564_2_, final float p_147564_3_) {
        if (this.rawBufferIndex < 1) {
            return null;
        }
        final int[] var4 = new int[this.rawBufferIndex];
        final PriorityQueue var5 = new PriorityQueue(this.rawBufferIndex, new QuadComparator(this.rawBuffer, p_147564_1_ + (float)this.xOffset, p_147564_2_ + (float)this.yOffset, p_147564_3_ + (float)this.zOffset));
        final byte var6 = 32;
        for (int var7 = 0; var7 < this.rawBufferIndex; var7 += var6) {
            var5.add(var7);
        }
        int var7 = 0;
        while (!var5.isEmpty()) {
            final int var8 = (int)var5.remove();
            for (int var9 = 0; var9 < var6; ++var9) {
                var4[var7 + var9] = this.rawBuffer[var8 + var9];
            }
            var7 += var6;
        }
        System.arraycopy(var4, 0, this.rawBuffer, 0, var4.length);
        return new TesselatorVertexState(var4, this.rawBufferIndex, this.vertexCount, this.hasTexture, this.hasBrightness, this.hasNormals, this.hasColor);
    }
    
    public void setVertexState(final TesselatorVertexState p_147565_1_) {
        System.arraycopy(p_147565_1_.getRawBuffer(), 0, this.rawBuffer, 0, p_147565_1_.getRawBuffer().length);
        this.rawBufferIndex = p_147565_1_.getRawBufferIndex();
        this.vertexCount = p_147565_1_.getVertexCount();
        this.hasTexture = p_147565_1_.getHasTexture();
        this.hasBrightness = p_147565_1_.getHasBrightness();
        this.hasColor = p_147565_1_.getHasColor();
        this.hasNormals = p_147565_1_.getHasNormals();
    }
    
    private void reset() {
        this.vertexCount = 0;
        this.byteBuffer.clear();
        this.rawBufferIndex = 0;
        this.addedVertices = 0;
    }
    
    public void startDrawingQuads() {
        this.startDrawing(7);
    }
    
    public void startDrawing(final int par1) {
        if (this.isDrawing) {
            throw new IllegalStateException("Already tesselating!");
        }
        this.isDrawing = true;
        this.reset();
        this.drawMode = par1;
        this.hasNormals = false;
        this.hasColor = false;
        this.hasTexture = false;
        this.hasBrightness = false;
        this.isColorDisabled = false;
    }
    
    public void setTextureUV(final double par1, final double par3) {
        this.hasTexture = true;
        this.textureU = par1;
        this.textureV = par3;
    }
    
    public void setBrightness(final int par1) {
        this.hasBrightness = true;
        this.brightness = par1;
    }
    
    public void setColorOpaque_F(final float par1, final float par2, final float par3) {
        this.setColorOpaque((int)(par1 * 255.0f), (int)(par2 * 255.0f), (int)(par3 * 255.0f));
    }
    
    public void setColorRGBA_F(final float par1, final float par2, final float par3, final float par4) {
        this.setColorRGBA((int)(par1 * 255.0f), (int)(par2 * 255.0f), (int)(par3 * 255.0f), (int)(par4 * 255.0f));
    }
    
    public void setColorOpaque(final int par1, final int par2, final int par3) {
        this.setColorRGBA(par1, par2, par3, 255);
    }
    
    public void setColorRGBA(int par1, int par2, int par3, int par4) {
        if (!this.isColorDisabled) {
            if (par1 > 255) {
                par1 = 255;
            }
            if (par2 > 255) {
                par2 = 255;
            }
            if (par3 > 255) {
                par3 = 255;
            }
            if (par4 > 255) {
                par4 = 255;
            }
            if (par1 < 0) {
                par1 = 0;
            }
            if (par2 < 0) {
                par2 = 0;
            }
            if (par3 < 0) {
                par3 = 0;
            }
            if (par4 < 0) {
                par4 = 0;
            }
            this.hasColor = true;
            if (Tessellator.littleEndianByteOrder) {
                this.color = (par4 << 24 | par3 << 16 | par2 << 8 | par1);
            }
            else {
                this.color = (par1 << 24 | par2 << 16 | par3 << 8 | par4);
            }
        }
    }
    
    public void func_154352_a(final byte p_154352_1_, final byte p_154352_2_, final byte p_154352_3_) {
        this.setColorOpaque(p_154352_1_ & 0xFF, p_154352_2_ & 0xFF, p_154352_3_ & 0xFF);
    }
    
    public void addVertexWithUV(final double x, final double y, final double z, final double u, final double v) {
        this.setTextureUV(u, v);
        this.addVertex(x, y, z);
    }
    
    public void addVertex(final double par1, final double par3, final double par5) {
        if (this.autoGrow && this.rawBufferIndex >= this.bufferSize - 32) {
            Config.dbg("Expand tessellator buffer, old: " + this.bufferSize + ", new: " + this.bufferSize * 2);
            this.bufferSize *= 2;
            final int[] newRawBuffer = new int[this.bufferSize];
            System.arraycopy(this.rawBuffer, 0, newRawBuffer, 0, this.rawBuffer.length);
            this.rawBuffer = newRawBuffer;
            this.byteBuffer = GLAllocation.createDirectByteBuffer(this.bufferSize * 4);
            this.intBuffer = this.byteBuffer.asIntBuffer();
            this.floatBuffer = this.byteBuffer.asFloatBuffer();
            this.shortBuffer = this.byteBuffer.asShortBuffer();
            if (this.vertexQuadIcons != null) {
                final TextureAtlasSprite[] newVertexQuadIcons = new TextureAtlasSprite[this.bufferSize / 4];
                System.arraycopy(this.vertexQuadIcons, 0, newVertexQuadIcons, 0, this.vertexQuadIcons.length);
                this.vertexQuadIcons = newVertexQuadIcons;
            }
        }
        ++this.addedVertices;
        if (this.hasTexture) {
            this.rawBuffer[this.rawBufferIndex + 3] = Float.floatToRawIntBits((float)this.textureU);
            this.rawBuffer[this.rawBufferIndex + 4] = Float.floatToRawIntBits((float)this.textureV);
        }
        if (this.hasBrightness) {
            this.rawBuffer[this.rawBufferIndex + 7] = this.brightness;
        }
        if (this.hasColor) {
            this.rawBuffer[this.rawBufferIndex + 5] = this.color;
        }
        if (this.hasNormals) {
            this.rawBuffer[this.rawBufferIndex + 6] = this.normal;
        }
        this.rawBuffer[this.rawBufferIndex + 0] = Float.floatToRawIntBits((float)(par1 + this.xOffset));
        this.rawBuffer[this.rawBufferIndex + 1] = Float.floatToRawIntBits((float)(par3 + this.yOffset));
        this.rawBuffer[this.rawBufferIndex + 2] = Float.floatToRawIntBits((float)(par5 + this.zOffset));
        this.rawBufferIndex += 8;
        ++this.vertexCount;
        if (!this.autoGrow && this.addedVertices % 4 == 0 && this.rawBufferIndex >= this.bufferSize - 32) {
            this.draw();
            this.isDrawing = true;
        }
    }
    
    public void setColorOpaque_I(final int par1) {
        final int var2 = par1 >> 16 & 0xFF;
        final int var3 = par1 >> 8 & 0xFF;
        final int var4 = par1 & 0xFF;
        this.setColorOpaque(var2, var3, var4);
    }
    
    public void setColorRGBA_I(final int par1, final int par2) {
        final int var3 = par1 >> 16 & 0xFF;
        final int var4 = par1 >> 8 & 0xFF;
        final int var5 = par1 & 0xFF;
        this.setColorRGBA(var3, var4, var5, par2);
    }
    
    public void disableColor() {
        this.isColorDisabled = true;
    }
    
    public void setNormal(final float par1, final float par2, final float par3) {
        this.hasNormals = true;
        final byte var4 = (byte)(par1 * 127.0f);
        final byte var5 = (byte)(par2 * 127.0f);
        final byte var6 = (byte)(par3 * 127.0f);
        this.normal = ((var4 & 0xFF) | (var5 & 0xFF) << 8 | (var6 & 0xFF) << 16);
    }
    
    public void setTranslation(final double par1, final double par3, final double par5) {
        this.xOffset = par1;
        this.yOffset = par3;
        this.zOffset = par5;
    }
    
    public void addTranslation(final float par1, final float par2, final float par3) {
        this.xOffset += par1;
        this.yOffset += par2;
        this.zOffset += par3;
    }
    
    public boolean isRenderingChunk() {
        return this.renderingChunk;
    }
    
    public void setRenderingChunk(final boolean renderingChunk) {
        this.renderingChunk = renderingChunk;
    }
    
    private void draw(final int startQuadVertex, final int endQuadVertex) {
        final int vxQuadCount = endQuadVertex - startQuadVertex;
        if (vxQuadCount > 0) {
            final int startVertex = startQuadVertex * 4;
            final int vxCount = vxQuadCount * 4;
            this.floatBuffer.position(3);
            GL11.glTexCoordPointer(2, 32, this.floatBuffer);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
            this.shortBuffer.position(14);
            GL11.glTexCoordPointer(2, 32, this.shortBuffer);
            GL11.glEnableClientState(32888);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
            this.byteBuffer.position(20);
            GL11.glColorPointer(4, true, 32, this.byteBuffer);
            this.floatBuffer.position(0);
            GL11.glVertexPointer(3, 32, this.floatBuffer);
            GL11.glDrawArrays(this.drawMode, startVertex, vxCount);
        }
    }
    
    private int drawForIcon(final TextureAtlasSprite icon, final int startQuadPos) {
        icon.bindOwnTexture();
        int firstRegionEnd = -1;
        int lastPos = -1;
        final int numQuads = this.addedVertices / 4;
        for (int i = startQuadPos; i < numQuads; ++i) {
            final TextureAtlasSprite ts = this.vertexQuadIcons[i];
            if (ts == icon) {
                if (lastPos < 0) {
                    lastPos = i;
                }
            }
            else if (lastPos >= 0) {
                this.draw(lastPos, i);
                lastPos = -1;
                if (firstRegionEnd < 0) {
                    firstRegionEnd = i;
                }
            }
        }
        if (lastPos >= 0) {
            this.draw(lastPos, numQuads);
        }
        if (firstRegionEnd < 0) {
            firstRegionEnd = numQuads;
        }
        return firstRegionEnd;
    }
    
    static {
        Tessellator.instance = new Tessellator(524288);
        Tessellator.littleEndianByteOrder = (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN);
        Tessellator.renderingWorldRenderer = false;
    }
}
