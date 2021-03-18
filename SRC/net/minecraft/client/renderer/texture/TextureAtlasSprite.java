package net.minecraft.client.renderer.texture;

import java.nio.*;
import net.minecraft.src.*;
import com.google.common.collect.*;
import java.awt.image.*;
import net.minecraft.client.resources.data.*;
import java.util.*;
import java.util.concurrent.*;
import net.minecraft.crash.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;

public class TextureAtlasSprite implements IIcon
{
    private final String iconName;
    protected List framesTextureData;
    private AnimationMetadataSection animationMetadata;
    protected boolean rotated;
    private boolean field_147966_k;
    protected int originX;
    protected int originY;
    protected int width;
    protected int height;
    private float minU;
    private float maxU;
    private float minV;
    private float maxV;
    protected int frameCounter;
    protected int tickCounter;
    private int indexInMap;
    public float baseU;
    public float baseV;
    public int sheetWidth;
    public int sheetHeight;
    private boolean mipmapActive;
    public int glOwnTextureId;
    private int uploadedFrameIndex;
    private int uploadedOwnFrameIndex;
    public IntBuffer[] frameBuffers;
    public Mipmaps[] frameMipmaps;
    private boolean hasTransparency;
    
    protected TextureAtlasSprite(final String par1Str) {
        this.framesTextureData = Lists.newArrayList();
        this.indexInMap = -1;
        this.mipmapActive = false;
        this.glOwnTextureId = -1;
        this.uploadedFrameIndex = -1;
        this.uploadedOwnFrameIndex = -1;
        this.hasTransparency = false;
        this.iconName = par1Str;
    }
    
    public void initSprite(final int par1, final int par2, final int par3, final int par4, final boolean par5) {
        this.originX = par3;
        this.originY = par4;
        this.rotated = par5;
        final float var6 = (float)(0.009999999776482582 / par1);
        final float var7 = (float)(0.009999999776482582 / par2);
        this.minU = par3 / (float)par1 + var6;
        this.maxU = (par3 + this.width) / (float)par1 - var6;
        this.minV = par4 / (float)par2 + var7;
        this.maxV = (par4 + this.height) / (float)par2 - var7;
        if (this.field_147966_k) {
            final float var8 = 8.0f / par1;
            final float var9 = 8.0f / par2;
            this.minU += var8;
            this.maxU -= var8;
            this.minV += var9;
            this.maxV -= var9;
        }
        this.baseU = Math.min(this.minU, this.maxU);
        this.baseV = Math.min(this.minV, this.maxV);
    }
    
    public void copyFrom(final TextureAtlasSprite par1TextureAtlasSprite) {
        this.originX = par1TextureAtlasSprite.originX;
        this.originY = par1TextureAtlasSprite.originY;
        this.width = par1TextureAtlasSprite.width;
        this.height = par1TextureAtlasSprite.height;
        this.rotated = par1TextureAtlasSprite.rotated;
        this.minU = par1TextureAtlasSprite.minU;
        this.maxU = par1TextureAtlasSprite.maxU;
        this.minV = par1TextureAtlasSprite.minV;
        this.maxV = par1TextureAtlasSprite.maxV;
        this.baseU = Math.min(this.minU, this.maxU);
        this.baseV = Math.min(this.minV, this.maxV);
    }
    
    public int getOriginX() {
        return this.originX;
    }
    
    public int getOriginY() {
        return this.originY;
    }
    
    @Override
    public int getIconWidth() {
        return this.width;
    }
    
    @Override
    public int getIconHeight() {
        return this.height;
    }
    
    @Override
    public float getMinU() {
        return this.minU;
    }
    
    @Override
    public float getMaxU() {
        return this.maxU;
    }
    
    @Override
    public float getInterpolatedU(final double par1) {
        final float var3 = this.maxU - this.minU;
        return this.minU + var3 * (float)par1 / 16.0f;
    }
    
    @Override
    public float getMinV() {
        return this.minV;
    }
    
    @Override
    public float getMaxV() {
        return this.maxV;
    }
    
    @Override
    public float getInterpolatedV(final double par1) {
        final float var3 = this.maxV - this.minV;
        return this.minV + var3 * ((float)par1 / 16.0f);
    }
    
    @Override
    public String getIconName() {
        return this.iconName;
    }
    
    public void updateAnimation() {
        ++this.tickCounter;
        if (this.tickCounter >= this.animationMetadata.getFrameTimeSingle(this.frameCounter)) {
            final int var1 = this.animationMetadata.getFrameIndex(this.frameCounter);
            final int var2 = (this.animationMetadata.getFrameCount() == 0) ? this.framesTextureData.size() : this.animationMetadata.getFrameCount();
            this.frameCounter = (this.frameCounter + 1) % var2;
            this.tickCounter = 0;
            final int var3 = this.animationMetadata.getFrameIndex(this.frameCounter);
            if (var1 != var3 && var3 >= 0 && var3 < this.framesTextureData.size()) {
                TextureUtil.func_147955_a(this.framesTextureData.get(var3), this.width, this.height, this.originX, this.originY, false, false);
                this.uploadedFrameIndex = var3;
            }
        }
    }
    
    public int[][] func_147965_a(final int p_147965_1_) {
        return this.framesTextureData.get(p_147965_1_);
    }
    
    public int getFrameCount() {
        return this.framesTextureData.size();
    }
    
    public void setIconWidth(final int par1) {
        this.width = par1;
    }
    
    public void setIconHeight(final int par1) {
        this.height = par1;
    }
    
    public void func_147964_a(final BufferedImage[] p_147964_1_, final AnimationMetadataSection p_147964_2_, final boolean p_147964_3_) {
        this.resetSprite();
        this.field_147966_k = p_147964_3_;
        final int var4 = p_147964_1_[0].getWidth();
        final int var5 = p_147964_1_[0].getHeight();
        this.width = var4;
        this.height = var5;
        if (p_147964_3_) {
            this.width += 16;
            this.height += 16;
        }
        final int[][] var6 = new int[p_147964_1_.length][];
        for (int var7 = 0; var7 < p_147964_1_.length; ++var7) {
            final BufferedImage var8 = p_147964_1_[var7];
            if (var8 != null) {
                if (var7 > 0 && (var8.getWidth() != var4 >> var7 || var8.getHeight() != var5 >> var7)) {
                    throw new RuntimeException(String.format("Unable to load miplevel: %d, image is size: %dx%d, expected %dx%d", var7, var8.getWidth(), var8.getHeight(), var4 >> var7, var5 >> var7));
                }
                var6[var7] = new int[var8.getWidth() * var8.getHeight()];
                var8.getRGB(0, 0, var8.getWidth(), var8.getHeight(), var6[var7], 0, var8.getWidth());
            }
        }
        if (p_147964_2_ == null) {
            if (var5 != var4) {
                throw new RuntimeException("broken aspect ratio and not an animation");
            }
            this.func_147961_a(var6);
            this.framesTextureData.add(this.func_147960_a(var6, var4, var5));
        }
        else {
            final int var7 = var5 / var4;
            final int var9 = var4;
            final int var10 = var4;
            this.height = this.width;
            if (p_147964_2_.getFrameCount() > 0) {
                for (final int var12 : p_147964_2_.getFrameIndexSet()) {
                    if (var12 >= var7) {
                        throw new RuntimeException("invalid frameindex " + var12);
                    }
                    this.allocateFrameTextureData(var12);
                    this.framesTextureData.set(var12, this.func_147960_a(func_147962_a(var6, var9, var10, var12), var9, var10));
                }
                this.animationMetadata = p_147964_2_;
            }
            else {
                final ArrayList var13 = Lists.newArrayList();
                for (int var12 = 0; var12 < var7; ++var12) {
                    this.framesTextureData.add(this.func_147960_a(func_147962_a(var6, var9, var10, var12), var9, var10));
                    var13.add(new AnimationFrame(var12, -1));
                }
                this.animationMetadata = new AnimationMetadataSection(var13, this.width, this.height, p_147964_2_.getFrameTime());
            }
        }
    }
    
    public void func_147963_d(final int p_147963_1_) {
        final ArrayList var2 = Lists.newArrayList();
        for (int var3 = 0; var3 < this.framesTextureData.size(); ++var3) {
            final int[][] var4 = this.framesTextureData.get(var3);
            if (var4 != null) {
                try {
                    var2.add(TextureUtil.func_147949_a(p_147963_1_, this.width, var4));
                }
                catch (Throwable var6) {
                    final CrashReport var5 = CrashReport.makeCrashReport(var6, "Generating mipmaps for frame");
                    final CrashReportCategory var7 = var5.makeCategory("Frame being iterated");
                    var7.addCrashSection("Frame index", var3);
                    var7.addCrashSectionCallable("Frame sizes", new Callable() {
                        @Override
                        public String call() {
                            final StringBuilder var1 = new StringBuilder();
                            for (final int[] var4 : var4) {
                                if (var1.length() > 0) {
                                    var1.append(", ");
                                }
                                var1.append((var4 == null) ? "null" : Integer.valueOf(var4.length));
                            }
                            return var1.toString();
                        }
                    });
                    throw new ReportedException(var5);
                }
            }
        }
        this.setFramesTextureData(var2);
    }
    
    private void func_147961_a(final int[][] p_147961_1_) {
        final int[] var2 = p_147961_1_[0];
        int var3 = 0;
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;
        for (int var7 = 0; var7 < var2.length; ++var7) {
            if ((var2[var7] & 0xFF000000) != 0x0) {
                var4 += (var2[var7] >> 16 & 0xFF);
                var5 += (var2[var7] >> 8 & 0xFF);
                var6 += (var2[var7] >> 0 & 0xFF);
                ++var3;
            }
        }
        if (var3 != 0) {
            var4 /= var3;
            var5 /= var3;
            var6 /= var3;
            for (int var7 = 0; var7 < var2.length; ++var7) {
                if ((var2[var7] & 0xFF000000) == 0x0) {
                    var2[var7] = (var4 << 16 | var5 << 8 | var6);
                }
            }
        }
    }
    
    private int[][] func_147960_a(final int[][] p_147960_1_, final int p_147960_2_, final int p_147960_3_) {
        if (!this.field_147966_k) {
            return p_147960_1_;
        }
        final int[][] var4 = new int[p_147960_1_.length][];
        for (int var5 = 0; var5 < p_147960_1_.length; ++var5) {
            final int[] var6 = p_147960_1_[var5];
            if (var6 != null) {
                final int[] var7 = new int[(p_147960_2_ + 16 >> var5) * (p_147960_3_ + 16 >> var5)];
                System.arraycopy(var6, 0, var7, 0, var6.length);
                var4[var5] = TextureUtil.func_147948_a(var7, p_147960_2_ >> var5, p_147960_3_ >> var5, 8 >> var5);
            }
        }
        return var4;
    }
    
    private void allocateFrameTextureData(final int par1) {
        if (this.framesTextureData.size() <= par1) {
            for (int var2 = this.framesTextureData.size(); var2 <= par1; ++var2) {
                this.framesTextureData.add(null);
            }
        }
    }
    
    private static int[][] func_147962_a(final int[][] p_147962_0_, final int p_147962_1_, final int p_147962_2_, final int p_147962_3_) {
        final int[][] var4 = new int[p_147962_0_.length][];
        for (int var5 = 0; var5 < p_147962_0_.length; ++var5) {
            final int[] var6 = p_147962_0_[var5];
            if (var6 != null) {
                var4[var5] = new int[(p_147962_1_ >> var5) * (p_147962_2_ >> var5)];
                System.arraycopy(var6, p_147962_3_ * var4[var5].length, var4[var5], 0, var4[var5].length);
            }
        }
        return var4;
    }
    
    public void clearFramesTextureData() {
        this.framesTextureData.clear();
    }
    
    public boolean hasAnimationMetadata() {
        return this.animationMetadata != null;
    }
    
    public void setFramesTextureData(final List par1List) {
        this.framesTextureData = par1List;
        for (int i = 0; i < this.framesTextureData.size(); ++i) {
            final int[][] datas = this.framesTextureData.get(i);
            if (datas != null && !this.iconName.startsWith("leaves_")) {
                for (int di = 0; di < datas.length; ++di) {
                    final int[] data = datas[di];
                    if (data != null) {
                        this.fixTransparentColor(data);
                    }
                }
            }
        }
    }
    
    private void resetSprite() {
        this.animationMetadata = null;
        this.setFramesTextureData(Lists.newArrayList());
        this.frameCounter = 0;
        this.tickCounter = 0;
        this.deleteOwnTexture();
        this.uploadedFrameIndex = -1;
        this.uploadedOwnFrameIndex = -1;
        this.frameBuffers = null;
        this.frameMipmaps = null;
    }
    
    @Override
    public String toString() {
        return "TextureAtlasSprite{name='" + this.iconName + '\'' + ", frameCount=" + this.framesTextureData.size() + ", rotated=" + this.rotated + ", x=" + this.originX + ", y=" + this.originY + ", height=" + this.height + ", width=" + this.width + ", u0=" + this.minU + ", u1=" + this.maxU + ", v0=" + this.minV + ", v1=" + this.maxV + '}';
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public int getIndexInMap() {
        return this.indexInMap;
    }
    
    public void setIndexInMap(final int indexInMap) {
        this.indexInMap = indexInMap;
    }
    
    public void setMipmapActive(final boolean mipmapActive) {
        this.mipmapActive = mipmapActive;
        this.frameMipmaps = null;
    }
    
    public void uploadFrameTexture() {
        this.uploadFrameTexture(this.frameCounter, this.originX, this.originY);
    }
    
    public void uploadFrameTexture(final int frameIndex, final int xPos, final int yPos) {
    }
    
    private void uploadFrameMipmaps(final int frameIndex, final int xPos, final int yPos) {
    }
    
    public void bindOwnTexture() {
    }
    
    public void bindUploadOwnTexture() {
        this.bindOwnTexture();
        this.uploadFrameTexture(this.frameCounter, 0, 0);
    }
    
    public void uploadOwnAnimation() {
        if (this.uploadedFrameIndex != this.uploadedOwnFrameIndex) {
            TextureUtil.bindTexture(this.glOwnTextureId);
            this.uploadFrameTexture(this.uploadedFrameIndex, 0, 0);
            this.uploadedOwnFrameIndex = this.uploadedFrameIndex;
        }
    }
    
    public void deleteOwnTexture() {
        if (this.glOwnTextureId >= 0) {
            GL11.glDeleteTextures(this.glOwnTextureId);
            this.glOwnTextureId = -1;
        }
    }
    
    private void fixTransparentColor(final int[] data) {
        long redSum = 0L;
        long greenSum = 0L;
        long blueSum = 0L;
        long count = 0L;
        for (int redAvg = 0; redAvg < data.length; ++redAvg) {
            final int greenAvg = data[redAvg];
            final int blueAvg = greenAvg >> 24 & 0xFF;
            if (blueAvg >= 16) {
                final int colAvg = greenAvg >> 16 & 0xFF;
                final int i = greenAvg >> 8 & 0xFF;
                final int col = greenAvg & 0xFF;
                redSum += colAvg;
                greenSum += i;
                blueSum += col;
                ++count;
            }
        }
        if (count > 0L) {
            final int redAvg = (int)(redSum / count);
            final int greenAvg = (int)(greenSum / count);
            final int blueAvg = (int)(blueSum / count);
            final int colAvg = redAvg << 16 | greenAvg << 8 | blueAvg;
            for (int i = 0; i < data.length; ++i) {
                final int col = data[i];
                final int alpha = col >> 24 & 0xFF;
                if (alpha <= 16) {
                    data[i] = colAvg;
                }
            }
        }
    }
    
    public boolean hasCustomLoader(final IResourceManager manager, final ResourceLocation location) {
        return false;
    }
    
    public boolean load(final IResourceManager manager, final ResourceLocation location) {
        return true;
    }
    
    public boolean hasTransparency() {
        return this.hasTransparency;
    }
    
    public void setHasTransparency(final boolean hasTransparency) {
        this.hasTransparency = hasTransparency;
    }
}
