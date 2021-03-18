package net.minecraft.client.renderer.texture;

import java.nio.*;
import org.lwjgl.opengl.*;
import java.awt.image.*;
import net.minecraft.client.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import javax.imageio.*;
import java.io.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.renderer.*;

public class TextureUtil
{
    private static final Logger logger;
    private static final IntBuffer dataBuffer;
    public static final DynamicTexture missingTexture;
    public static final int[] missingTextureData;
    private static int field_147958_e;
    private static int field_147956_f;
    private static float field_152779_g;
    private static final int[] field_147957_g;
    private static final String __OBFID = "CL_00001067";
    
    public static int glGenTextures() {
        return GL11.glGenTextures();
    }
    
    public static void deleteTexture(final int p_147942_0_) {
        GL11.glDeleteTextures(p_147942_0_);
    }
    
    public static int uploadTextureImage(final int p_110987_0_, final BufferedImage p_110987_1_) {
        return uploadTextureImageAllocate(p_110987_0_, p_110987_1_, false, false);
    }
    
    public static void uploadTexture(final int p_110988_0_, final int[] p_110988_1_, final int p_110988_2_, final int p_110988_3_) {
        bindTexture(p_110988_0_);
        func_147947_a(0, p_110988_1_, p_110988_2_, p_110988_3_, 0, 0, false, false, false);
    }
    
    public static int[][] func_147949_a(final int p_147949_0_, final int p_147949_1_, final int[][] p_147949_2_) {
        final int[][] var3 = new int[p_147949_0_ + 1][];
        var3[0] = p_147949_2_[0];
        if (p_147949_0_ > 0) {
            boolean var4 = false;
            for (int var5 = 0; var5 < p_147949_2_.length; ++var5) {
                if (p_147949_2_[0][var5] >> 24 == 0) {
                    var4 = true;
                    break;
                }
            }
            for (int var5 = 1; var5 <= p_147949_0_; ++var5) {
                if (p_147949_2_[var5] != null) {
                    var3[var5] = p_147949_2_[var5];
                }
                else {
                    final int[] var6 = var3[var5 - 1];
                    final int[] var7 = new int[var6.length >> 2];
                    final int var8 = p_147949_1_ >> var5;
                    final int var9 = var7.length / var8;
                    final int var10 = var8 << 1;
                    for (int var11 = 0; var11 < var8; ++var11) {
                        for (int var12 = 0; var12 < var9; ++var12) {
                            final int var13 = 2 * (var11 + var12 * var10);
                            var7[var11 + var12 * var8] = func_147943_a(var6[var13 + 0], var6[var13 + 1], var6[var13 + 0 + var10], var6[var13 + 1 + var10], var4);
                        }
                    }
                    var3[var5] = var7;
                }
            }
        }
        return var3;
    }
    
    private static int func_147943_a(final int p_147943_0_, final int p_147943_1_, final int p_147943_2_, final int p_147943_3_, final boolean p_147943_4_) {
        if (!p_147943_4_) {
            final int var13 = func_147944_a(p_147943_0_, p_147943_1_, p_147943_2_, p_147943_3_, 24);
            final int var14 = func_147944_a(p_147943_0_, p_147943_1_, p_147943_2_, p_147943_3_, 16);
            final int var15 = func_147944_a(p_147943_0_, p_147943_1_, p_147943_2_, p_147943_3_, 8);
            final int var16 = func_147944_a(p_147943_0_, p_147943_1_, p_147943_2_, p_147943_3_, 0);
            return var13 << 24 | var14 << 16 | var15 << 8 | var16;
        }
        TextureUtil.field_147957_g[0] = p_147943_0_;
        TextureUtil.field_147957_g[1] = p_147943_1_;
        TextureUtil.field_147957_g[2] = p_147943_2_;
        TextureUtil.field_147957_g[3] = p_147943_3_;
        float var17 = 0.0f;
        float var18 = 0.0f;
        float var19 = 0.0f;
        float var20 = 0.0f;
        for (int var21 = 0; var21 < 4; ++var21) {
            if (TextureUtil.field_147957_g[var21] >> 24 != 0) {
                var17 += (float)Math.pow((TextureUtil.field_147957_g[var21] >> 24 & 0xFF) / 255.0f, 2.2);
                var18 += (float)Math.pow((TextureUtil.field_147957_g[var21] >> 16 & 0xFF) / 255.0f, 2.2);
                var19 += (float)Math.pow((TextureUtil.field_147957_g[var21] >> 8 & 0xFF) / 255.0f, 2.2);
                var20 += (float)Math.pow((TextureUtil.field_147957_g[var21] >> 0 & 0xFF) / 255.0f, 2.2);
            }
        }
        var17 /= 4.0f;
        var18 /= 4.0f;
        var19 /= 4.0f;
        var20 /= 4.0f;
        int var21 = (int)(Math.pow(var17, 0.45454545454545453) * 255.0);
        final int var22 = (int)(Math.pow(var18, 0.45454545454545453) * 255.0);
        final int var23 = (int)(Math.pow(var19, 0.45454545454545453) * 255.0);
        final int var24 = (int)(Math.pow(var20, 0.45454545454545453) * 255.0);
        if (var21 < 96) {
            var21 = 0;
        }
        return var21 << 24 | var22 << 16 | var23 << 8 | var24;
    }
    
    private static int func_147944_a(final int p_147944_0_, final int p_147944_1_, final int p_147944_2_, final int p_147944_3_, final int p_147944_4_) {
        final float var5 = (float)Math.pow((p_147944_0_ >> p_147944_4_ & 0xFF) / 255.0f, 2.2);
        final float var6 = (float)Math.pow((p_147944_1_ >> p_147944_4_ & 0xFF) / 255.0f, 2.2);
        final float var7 = (float)Math.pow((p_147944_2_ >> p_147944_4_ & 0xFF) / 255.0f, 2.2);
        final float var8 = (float)Math.pow((p_147944_3_ >> p_147944_4_ & 0xFF) / 255.0f, 2.2);
        final float var9 = (float)Math.pow((var5 + var6 + var7 + var8) * 0.25, 0.45454545454545453);
        return (int)(var9 * 255.0);
    }
    
    public static void func_147955_a(final int[][] p_147955_0_, final int p_147955_1_, final int p_147955_2_, final int p_147955_3_, final int p_147955_4_, final boolean p_147955_5_, final boolean p_147955_6_) {
        for (int var7 = 0; var7 < p_147955_0_.length; ++var7) {
            final int[] var8 = p_147955_0_[var7];
            func_147947_a(var7, var8, p_147955_1_ >> var7, p_147955_2_ >> var7, p_147955_3_ >> var7, p_147955_4_ >> var7, p_147955_5_, p_147955_6_, p_147955_0_.length > 1);
        }
    }
    
    private static void func_147947_a(final int p_147947_0_, final int[] p_147947_1_, final int p_147947_2_, final int p_147947_3_, final int p_147947_4_, final int p_147947_5_, final boolean p_147947_6_, final boolean p_147947_7_, final boolean p_147947_8_) {
        final int var9 = 4194304 / p_147947_2_;
        func_147954_b(p_147947_6_, p_147947_8_);
        setTextureClamped(p_147947_7_);
        int var12;
        for (int var10 = 0; var10 < p_147947_2_ * p_147947_3_; var10 += p_147947_2_ * var12) {
            final int var11 = var10 / p_147947_2_;
            var12 = Math.min(var9, p_147947_3_ - var11);
            final int var13 = p_147947_2_ * var12;
            copyToBufferPos(p_147947_1_, var10, var13);
            GL11.glTexSubImage2D(3553, p_147947_0_, p_147947_4_, p_147947_5_ + var11, p_147947_2_, var12, 32993, 33639, TextureUtil.dataBuffer);
        }
    }
    
    public static int uploadTextureImageAllocate(final int p_110989_0_, final BufferedImage p_110989_1_, final boolean p_110989_2_, final boolean p_110989_3_) {
        allocateTexture(p_110989_0_, p_110989_1_.getWidth(), p_110989_1_.getHeight());
        return uploadTextureImageSub(p_110989_0_, p_110989_1_, 0, 0, p_110989_2_, p_110989_3_);
    }
    
    public static void allocateTexture(final int p_110991_0_, final int p_110991_1_, final int p_110991_2_) {
        func_147946_a(p_110991_0_, 0, p_110991_1_, p_110991_2_, 1.0f);
    }
    
    public static void func_147946_a(final int p_147946_0_, final int p_147946_1_, final int p_147946_2_, final int p_147946_3_, final float p_147946_4_) {
        deleteTexture(p_147946_0_);
        bindTexture(p_147946_0_);
        if (OpenGlHelper.anisotropicFilteringSupported) {
            GL11.glTexParameterf(3553, 34046, p_147946_4_);
        }
        if (p_147946_1_ > 0) {
            GL11.glTexParameteri(3553, 33085, p_147946_1_);
            GL11.glTexParameterf(3553, 33082, 0.0f);
            GL11.glTexParameterf(3553, 33083, (float)p_147946_1_);
            GL11.glTexParameterf(3553, 34049, 0.0f);
        }
        for (int var5 = 0; var5 <= p_147946_1_; ++var5) {
            GL11.glTexImage2D(3553, var5, 6408, p_147946_2_ >> var5, p_147946_3_ >> var5, 0, 32993, 33639, (IntBuffer)null);
        }
    }
    
    public static int uploadTextureImageSub(final int p_110995_0_, final BufferedImage p_110995_1_, final int p_110995_2_, final int p_110995_3_, final boolean p_110995_4_, final boolean p_110995_5_) {
        bindTexture(p_110995_0_);
        uploadTextureImageSubImpl(p_110995_1_, p_110995_2_, p_110995_3_, p_110995_4_, p_110995_5_);
        return p_110995_0_;
    }
    
    private static void uploadTextureImageSubImpl(final BufferedImage p_110993_0_, final int p_110993_1_, final int p_110993_2_, final boolean p_110993_3_, final boolean p_110993_4_) {
        final int var5 = p_110993_0_.getWidth();
        final int var6 = p_110993_0_.getHeight();
        final int var7 = 4194304 / var5;
        final int[] var8 = new int[var7 * var5];
        func_147951_b(p_110993_3_);
        setTextureClamped(p_110993_4_);
        for (int var9 = 0; var9 < var5 * var6; var9 += var5 * var7) {
            final int var10 = var9 / var5;
            final int var11 = Math.min(var7, var6 - var10);
            final int var12 = var5 * var11;
            p_110993_0_.getRGB(0, var10, var5, var11, var8, 0, var5);
            copyToBuffer(var8, var12);
            GL11.glTexSubImage2D(3553, 0, p_110993_1_, p_110993_2_ + var10, var5, var11, 32993, 33639, TextureUtil.dataBuffer);
        }
    }
    
    private static void setTextureClamped(final boolean p_110997_0_) {
        if (p_110997_0_) {
            GL11.glTexParameteri(3553, 10242, 10496);
            GL11.glTexParameteri(3553, 10243, 10496);
        }
        else {
            GL11.glTexParameteri(3553, 10242, 10497);
            GL11.glTexParameteri(3553, 10243, 10497);
        }
    }
    
    private static void func_147951_b(final boolean p_147951_0_) {
        func_147954_b(p_147951_0_, false);
    }
    
    public static void func_152777_a(final boolean p_152777_0_, final boolean p_152777_1_, final float p_152777_2_) {
        TextureUtil.field_147958_e = GL11.glGetTexParameteri(3553, 10241);
        TextureUtil.field_147956_f = GL11.glGetTexParameteri(3553, 10240);
        TextureUtil.field_152779_g = GL11.glGetTexParameterf(3553, 34046);
        func_147954_b(p_152777_0_, p_152777_1_);
        func_152778_a(p_152777_2_);
    }
    
    public static void func_147945_b() {
        if (TextureUtil.field_147958_e >= 0 && TextureUtil.field_147956_f >= 0 && TextureUtil.field_152779_g >= 0.0f) {
            func_147952_b(TextureUtil.field_147958_e, TextureUtil.field_147956_f);
            func_152778_a(TextureUtil.field_152779_g);
            TextureUtil.field_152779_g = -1.0f;
            TextureUtil.field_147958_e = -1;
            TextureUtil.field_147956_f = -1;
        }
    }
    
    private static void func_147952_b(final int p_147952_0_, final int p_147952_1_) {
        GL11.glTexParameteri(3553, 10241, p_147952_0_);
        GL11.glTexParameteri(3553, 10240, p_147952_1_);
    }
    
    private static void func_152778_a(final float p_152778_0_) {
        GL11.glTexParameterf(3553, 34046, p_152778_0_);
    }
    
    private static void func_147954_b(final boolean p_147954_0_, final boolean p_147954_1_) {
        if (p_147954_0_) {
            GL11.glTexParameteri(3553, 10241, p_147954_1_ ? 9987 : 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
        }
        else {
            GL11.glTexParameteri(3553, 10241, p_147954_1_ ? 9986 : 9728);
            GL11.glTexParameteri(3553, 10240, 9728);
        }
    }
    
    private static void copyToBuffer(final int[] p_110990_0_, final int p_110990_1_) {
        copyToBufferPos(p_110990_0_, 0, p_110990_1_);
    }
    
    private static void copyToBufferPos(final int[] p_110994_0_, final int p_110994_1_, final int p_110994_2_) {
        int[] var3 = p_110994_0_;
        if (Minecraft.getMinecraft().gameSettings.anaglyph) {
            var3 = updateAnaglyph(p_110994_0_);
        }
        TextureUtil.dataBuffer.clear();
        TextureUtil.dataBuffer.put(var3, p_110994_1_, p_110994_2_);
        TextureUtil.dataBuffer.position(0).limit(p_110994_2_);
    }
    
    static void bindTexture(final int p_94277_0_) {
        GL11.glBindTexture(3553, p_94277_0_);
    }
    
    public static int[] readImageData(final IResourceManager p_110986_0_, final ResourceLocation p_110986_1_) throws IOException {
        final BufferedImage var2 = ImageIO.read(p_110986_0_.getResource(p_110986_1_).getInputStream());
        final int var3 = var2.getWidth();
        final int var4 = var2.getHeight();
        final int[] var5 = new int[var3 * var4];
        var2.getRGB(0, 0, var3, var4, var5, 0, var3);
        return var5;
    }
    
    public static int[] updateAnaglyph(final int[] p_110985_0_) {
        final int[] var1 = new int[p_110985_0_.length];
        for (int var2 = 0; var2 < p_110985_0_.length; ++var2) {
            final int var3 = p_110985_0_[var2] >> 24 & 0xFF;
            final int var4 = p_110985_0_[var2] >> 16 & 0xFF;
            final int var5 = p_110985_0_[var2] >> 8 & 0xFF;
            final int var6 = p_110985_0_[var2] & 0xFF;
            final int var7 = (var4 * 30 + var5 * 59 + var6 * 11) / 100;
            final int var8 = (var4 * 30 + var5 * 70) / 100;
            final int var9 = (var4 * 30 + var6 * 70) / 100;
            var1[var2] = (var3 << 24 | var7 << 16 | var8 << 8 | var9);
        }
        return var1;
    }
    
    public static int[] func_147948_a(final int[] p_147948_0_, final int p_147948_1_, final int p_147948_2_, final int p_147948_3_) {
        final int var4 = p_147948_1_ + 2 * p_147948_3_;
        for (int var5 = p_147948_2_ - 1; var5 >= 0; --var5) {
            final int var6 = var5 * p_147948_1_;
            final int var7 = p_147948_3_ + (var5 + p_147948_3_) * var4;
            for (int var8 = 0; var8 < p_147948_3_; var8 += p_147948_1_) {
                final int var9 = Math.min(p_147948_1_, p_147948_3_ - var8);
                System.arraycopy(p_147948_0_, var6 + p_147948_1_ - var9, p_147948_0_, var7 - var8 - var9, var9);
            }
            System.arraycopy(p_147948_0_, var6, p_147948_0_, var7, p_147948_1_);
            for (int var8 = 0; var8 < p_147948_3_; var8 += p_147948_1_) {
                System.arraycopy(p_147948_0_, var6, p_147948_0_, var7 + p_147948_1_ + var8, Math.min(p_147948_1_, p_147948_3_ - var8));
            }
        }
        for (int var5 = 0; var5 < p_147948_3_; var5 += p_147948_2_) {
            final int var6 = Math.min(p_147948_2_, p_147948_3_ - var5);
            System.arraycopy(p_147948_0_, (p_147948_3_ + p_147948_2_ - var6) * var4, p_147948_0_, (p_147948_3_ - var5 - var6) * var4, var4 * var6);
        }
        for (int var5 = 0; var5 < p_147948_3_; var5 += p_147948_2_) {
            final int var6 = Math.min(p_147948_2_, p_147948_3_ - var5);
            System.arraycopy(p_147948_0_, p_147948_3_ * var4, p_147948_0_, (p_147948_2_ + p_147948_3_ + var5) * var4, var4 * var6);
        }
        return p_147948_0_;
    }
    
    public static void func_147953_a(final int[] p_147953_0_, final int p_147953_1_, final int p_147953_2_) {
        final int[] var3 = new int[p_147953_1_];
        for (int var4 = p_147953_2_ / 2, var5 = 0; var5 < var4; ++var5) {
            System.arraycopy(p_147953_0_, var5 * p_147953_1_, var3, 0, p_147953_1_);
            System.arraycopy(p_147953_0_, (p_147953_2_ - 1 - var5) * p_147953_1_, p_147953_0_, var5 * p_147953_1_, p_147953_1_);
            System.arraycopy(var3, 0, p_147953_0_, (p_147953_2_ - 1 - var5) * p_147953_1_, p_147953_1_);
        }
    }
    
    static {
        logger = LogManager.getLogger();
        dataBuffer = GLAllocation.createDirectIntBuffer(4194304);
        missingTexture = new DynamicTexture(16, 16);
        missingTextureData = TextureUtil.missingTexture.getTextureData();
        TextureUtil.field_147958_e = -1;
        TextureUtil.field_147956_f = -1;
        TextureUtil.field_152779_g = -1.0f;
        final int var0 = -16777216;
        final int var2 = -524040;
        final int[] var3 = { -524040, -524040, -524040, -524040, -524040, -524040, -524040, -524040 };
        final int[] var4 = { -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216 };
        final int var5 = var3.length;
        for (int var6 = 0; var6 < 16; ++var6) {
            System.arraycopy((var6 < var5) ? var3 : var4, 0, TextureUtil.missingTextureData, 16 * var6, var5);
            System.arraycopy((var6 < var5) ? var4 : var3, 0, TextureUtil.missingTextureData, 16 * var6 + var5, var5);
        }
        TextureUtil.missingTexture.updateDynamicTexture();
        field_147957_g = new int[4];
    }
}
