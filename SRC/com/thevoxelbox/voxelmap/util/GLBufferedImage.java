package com.thevoxelbox.voxelmap.util;

import java.awt.image.*;
import java.nio.*;
import org.lwjgl.opengl.*;
import com.thevoxelbox.voxelmap.*;
import com.thevoxelbox.voxelmap.interfaces.*;

public class GLBufferedImage extends BufferedImage
{
    public byte[] bytes;
    public int index;
    private ByteBuffer buffer;
    private Object bufferLock;
    
    public GLBufferedImage(final int width, final int height, final int imageType) {
        super(width, height, imageType);
        this.index = 0;
        this.bufferLock = new Object();
        this.bytes = ((DataBufferByte)this.getRaster().getDataBuffer()).getData();
        this.buffer = ByteBuffer.allocateDirect(this.bytes.length).order(ByteOrder.nativeOrder());
    }
    
    public void baleet() {
        if (this.index != 0) {
            GL11.glDeleteTextures(this.index);
        }
    }
    
    public void write() {
        if (this.index != 0) {
            GL11.glDeleteTextures(this.index);
        }
        else {
            this.index = GL11.glGenTextures();
        }
        this.buffer.clear();
        synchronized (this.bufferLock) {
            this.buffer.put(this.bytes);
        }
        this.buffer.position(0).limit(this.bytes.length);
        if (!GLUtils.hasAlphaBits && !GLUtils.fboEnabled) {
            if (MapSettingsManager.instance.squareMap) {
                for (int t = 0; t < this.getWidth(); ++t) {
                    this.buffer.put(t * 4 + 3, (byte)0);
                    this.buffer.put(t * this.getWidth() * 4 + 3, (byte)0);
                }
            }
            if (MapSettingsManager.instance.squareMap && (MapSettingsManager.instance.zoom > 0 || AbstractVoxelMap.instance.getMap().getPercentX() > 1.0f)) {
                for (int t = 0; t < this.getWidth(); ++t) {
                    this.buffer.put(t * this.getWidth() * 4 + 7, (byte)0);
                }
            }
            if (MapSettingsManager.instance.squareMap && (MapSettingsManager.instance.zoom > 0 || AbstractVoxelMap.instance.getMap().getPercentY() > 1.0f)) {
                for (int t = 0; t < this.getWidth(); ++t) {
                    this.buffer.put(t * 4 + 3 + this.getWidth() * 4, (byte)0);
                }
            }
        }
        GL11.glBindTexture(3553, this.index);
        GL11.glTexParameteri(3553, 10241, 9728);
        GL11.glTexParameteri(3553, 10240, 9728);
        GL11.glTexParameteri(3553, 10242, 33071);
        GL11.glTexParameteri(3553, 10243, 33071);
        GL11.glTexImage2D(3553, 0, 6408, this.getWidth(), this.getHeight(), 0, 32993, 5121, this.buffer);
    }
    
    public void blank() {
        for (int t = 0; t < this.bytes.length; ++t) {
            this.bytes[t] = 0;
        }
        this.write();
    }
    
    @Override
    public void setRGB(final int x, final int y, final int color24) {
        final int index = (x + y * this.getWidth()) * 4;
        synchronized (this.bufferLock) {
            final int alpha = color24 >> 24 & 0xFF;
            this.bytes[index] = (byte)((color24 >> 0 & 0xFF) * alpha / 255);
            this.bytes[index + 1] = (byte)((color24 >> 8 & 0xFF) * alpha / 255);
            this.bytes[index + 2] = (byte)((color24 >> 16 & 0xFF) * alpha / 255);
            this.bytes[index + 3] = -1;
        }
    }
    
    public void moveX(final int offset) {
        synchronized (this.bufferLock) {
            if (offset > 0) {
                System.arraycopy(this.bytes, offset * 4, this.bytes, 0, this.bytes.length - offset * 4);
            }
            else if (offset < 0) {
                System.arraycopy(this.bytes, 0, this.bytes, -offset * 4, this.bytes.length + offset * 4);
            }
        }
    }
    
    public void moveY(final int offset) {
        synchronized (this.bufferLock) {
            if (offset > 0) {
                System.arraycopy(this.bytes, offset * this.getWidth() * 4, this.bytes, 0, this.bytes.length - offset * this.getWidth() * 4);
            }
            else if (offset < 0) {
                System.arraycopy(this.bytes, 0, this.bytes, -offset * this.getWidth() * 4, this.bytes.length + offset * this.getWidth() * 4);
            }
        }
    }
}
