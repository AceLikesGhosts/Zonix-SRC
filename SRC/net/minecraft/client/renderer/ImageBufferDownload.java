package net.minecraft.client.renderer;

import java.awt.image.*;
import java.awt.*;

public class ImageBufferDownload implements IImageBuffer
{
    private int[] imageData;
    private int imageWidth;
    private int imageHeight;
    private static final String __OBFID = "CL_00000956";
    
    @Override
    public BufferedImage parseUserSkin(final BufferedImage p_78432_1_) {
        if (p_78432_1_ == null) {
            return null;
        }
        this.imageWidth = 64;
        this.imageHeight = 32;
        final BufferedImage var2 = new BufferedImage(this.imageWidth, this.imageHeight, 2);
        final Graphics var3 = var2.getGraphics();
        var3.drawImage(p_78432_1_, 0, 0, null);
        var3.dispose();
        this.imageData = ((DataBufferInt)var2.getRaster().getDataBuffer()).getData();
        this.setAreaOpaque(0, 0, 32, 16);
        this.setAreaTransparent(32, 0, 64, 32);
        this.setAreaOpaque(0, 16, 64, 32);
        return var2;
    }
    
    @Override
    public void func_152634_a() {
    }
    
    private void setAreaTransparent(final int p_78434_1_, final int p_78434_2_, final int p_78434_3_, final int p_78434_4_) {
        if (!this.hasTransparency(p_78434_1_, p_78434_2_, p_78434_3_, p_78434_4_)) {
            for (int var5 = p_78434_1_; var5 < p_78434_3_; ++var5) {
                for (int var6 = p_78434_2_; var6 < p_78434_4_; ++var6) {
                    final int[] imageData = this.imageData;
                    final int n = var5 + var6 * this.imageWidth;
                    imageData[n] &= 0xFFFFFF;
                }
            }
        }
    }
    
    private void setAreaOpaque(final int p_78433_1_, final int p_78433_2_, final int p_78433_3_, final int p_78433_4_) {
        for (int var5 = p_78433_1_; var5 < p_78433_3_; ++var5) {
            for (int var6 = p_78433_2_; var6 < p_78433_4_; ++var6) {
                final int[] imageData = this.imageData;
                final int n = var5 + var6 * this.imageWidth;
                imageData[n] |= 0xFF000000;
            }
        }
    }
    
    private boolean hasTransparency(final int p_78435_1_, final int p_78435_2_, final int p_78435_3_, final int p_78435_4_) {
        for (int var5 = p_78435_1_; var5 < p_78435_3_; ++var5) {
            for (int var6 = p_78435_2_; var6 < p_78435_4_; ++var6) {
                final int var7 = this.imageData[var5 + var6 * this.imageWidth];
                if ((var7 >> 24 & 0xFF) < 128) {
                    return true;
                }
            }
        }
        return false;
    }
}
