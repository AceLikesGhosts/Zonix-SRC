package net.minecraft.client.gui;

import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import org.lwjgl.opengl.*;
import com.ibm.icu.text.*;
import net.minecraft.client.renderer.*;
import java.util.*;

public class FontRenderer implements IResourceManagerReloadListener
{
    private static final ResourceLocation[] unicodePageLocations;
    private int[] charWidth;
    public int FONT_HEIGHT;
    public Random fontRandom;
    private byte[] glyphWidth;
    private int[] colorCode;
    private final ResourceLocation locationFontTexture;
    private final TextureManager renderEngine;
    private float posX;
    private float posY;
    private boolean unicodeFlag;
    private boolean bidiFlag;
    private float red;
    private float blue;
    private float green;
    private float alpha;
    private int textColor;
    private boolean randomStyle;
    private boolean boldStyle;
    private boolean italicStyle;
    private boolean underlineStyle;
    private boolean strikethroughStyle;
    public boolean enabled;
    private static final String __OBFID = "CL_00000660";
    
    public FontRenderer(final GameSettings p_i1035_1_, final ResourceLocation p_i1035_2_, final TextureManager p_i1035_3_, final boolean p_i1035_4_) {
        this.charWidth = new int[256];
        this.FONT_HEIGHT = 9;
        this.fontRandom = new Random();
        this.glyphWidth = new byte[65536];
        this.colorCode = new int[32];
        this.enabled = true;
        this.locationFontTexture = p_i1035_2_;
        this.renderEngine = p_i1035_3_;
        this.unicodeFlag = p_i1035_4_;
        p_i1035_3_.bindTexture(this.locationFontTexture);
        for (int var5 = 0; var5 < 32; ++var5) {
            final int var6 = (var5 >> 3 & 0x1) * 85;
            int var7 = (var5 >> 2 & 0x1) * 170 + var6;
            int var8 = (var5 >> 1 & 0x1) * 170 + var6;
            int var9 = (var5 >> 0 & 0x1) * 170 + var6;
            if (var5 == 6) {
                var7 += 85;
            }
            if (p_i1035_1_.anaglyph) {
                final int var10 = (var7 * 30 + var8 * 59 + var9 * 11) / 100;
                final int var11 = (var7 * 30 + var8 * 70) / 100;
                final int var12 = (var7 * 30 + var9 * 70) / 100;
                var7 = var10;
                var8 = var11;
                var9 = var12;
            }
            if (var5 >= 16) {
                var7 /= 4;
                var8 /= 4;
                var9 /= 4;
            }
            this.colorCode[var5] = ((var7 & 0xFF) << 16 | (var8 & 0xFF) << 8 | (var9 & 0xFF));
        }
        this.readGlyphSizes();
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager p_110549_1_) {
        this.readFontTexture();
    }
    
    private void readFontTexture() {
        BufferedImage var1;
        try {
            var1 = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(this.locationFontTexture).getInputStream());
        }
        catch (IOException var2) {
            throw new RuntimeException(var2);
        }
        final int var3 = var1.getWidth();
        final int var4 = var1.getHeight();
        final int[] var5 = new int[var3 * var4];
        var1.getRGB(0, 0, var3, var4, var5, 0, var3);
        final int var6 = var4 / 16;
        final int var7 = var3 / 16;
        final byte var8 = 1;
        final float var9 = 8.0f / var7;
        for (int var10 = 0; var10 < 256; ++var10) {
            final int var11 = var10 % 16;
            final int var12 = var10 / 16;
            if (var10 == 32) {
                this.charWidth[var10] = 3 + var8;
            }
            int var13;
            for (var13 = var7 - 1; var13 >= 0; --var13) {
                final int var14 = var11 * var7 + var13;
                boolean var15 = true;
                for (int var16 = 0; var16 < var6 && var15; ++var16) {
                    final int var17 = (var12 * var7 + var16) * var3;
                    if ((var5[var14 + var17] >> 24 & 0xFF) != 0x0) {
                        var15 = false;
                    }
                }
                if (!var15) {
                    break;
                }
            }
            ++var13;
            this.charWidth[var10] = (int)(0.5 + var13 * var9) + var8;
        }
    }
    
    private void readGlyphSizes() {
        try {
            final InputStream var1 = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("font/glyph_sizes.bin")).getInputStream();
            var1.read(this.glyphWidth);
        }
        catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }
    
    private float renderCharAtPos(final int p_78278_1_, final char p_78278_2_, final boolean p_78278_3_) {
        return (p_78278_2_ == ' ') ? 4.0f : (("\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8£\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1ªº¿®¬½¼¡«»\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261±\u2265\u2264\u2320\u2321\u00f7\u2248°\u2219·\u221a\u207f²\u25a0\u0000".indexOf(p_78278_2_) != -1 && !this.unicodeFlag) ? this.renderDefaultChar(p_78278_1_, p_78278_3_) : this.renderUnicodeChar(p_78278_2_, p_78278_3_));
    }
    
    private float renderDefaultChar(final int p_78266_1_, final boolean p_78266_2_) {
        final float var3 = (float)(p_78266_1_ % 16 * 8);
        final float var4 = (float)(p_78266_1_ / 16 * 8);
        final float var5 = p_78266_2_ ? 1.0f : 0.0f;
        this.renderEngine.bindTexture(this.locationFontTexture);
        final float var6 = this.charWidth[p_78266_1_] - 0.01f;
        GL11.glBegin(5);
        GL11.glTexCoord2f(var3 / 128.0f, var4 / 128.0f);
        GL11.glVertex3f(this.posX + var5, this.posY, 0.0f);
        GL11.glTexCoord2f(var3 / 128.0f, (var4 + 7.99f) / 128.0f);
        GL11.glVertex3f(this.posX - var5, this.posY + 7.99f, 0.0f);
        GL11.glTexCoord2f((var3 + var6 - 1.0f) / 128.0f, var4 / 128.0f);
        GL11.glVertex3f(this.posX + var6 - 1.0f + var5, this.posY, 0.0f);
        GL11.glTexCoord2f((var3 + var6 - 1.0f) / 128.0f, (var4 + 7.99f) / 128.0f);
        GL11.glVertex3f(this.posX + var6 - 1.0f - var5, this.posY + 7.99f, 0.0f);
        GL11.glEnd();
        return (float)this.charWidth[p_78266_1_];
    }
    
    private ResourceLocation getUnicodePageLocation(final int p_111271_1_) {
        if (FontRenderer.unicodePageLocations[p_111271_1_] == null) {
            FontRenderer.unicodePageLocations[p_111271_1_] = new ResourceLocation(String.format("textures/font/unicode_page_%02x.png", p_111271_1_));
        }
        return FontRenderer.unicodePageLocations[p_111271_1_];
    }
    
    private void loadGlyphTexture(final int p_78257_1_) {
        this.renderEngine.bindTexture(this.getUnicodePageLocation(p_78257_1_));
    }
    
    private float renderUnicodeChar(final char p_78277_1_, final boolean p_78277_2_) {
        if (this.glyphWidth[p_78277_1_] == 0) {
            return 0.0f;
        }
        final int var3 = p_78277_1_ / '\u0100';
        this.loadGlyphTexture(var3);
        final int var4 = this.glyphWidth[p_78277_1_] >>> 4;
        final int var5 = this.glyphWidth[p_78277_1_] & 0xF;
        final float var6 = (float)var4;
        final float var7 = (float)(var5 + 1);
        final float var8 = p_78277_1_ % '\u0010' * 16 + var6;
        final float var9 = (float)((p_78277_1_ & '\u00ff') / 16 * 16);
        final float var10 = var7 - var6 - 0.02f;
        final float var11 = p_78277_2_ ? 1.0f : 0.0f;
        GL11.glBegin(5);
        GL11.glTexCoord2f(var8 / 256.0f, var9 / 256.0f);
        GL11.glVertex3f(this.posX + var11, this.posY, 0.0f);
        GL11.glTexCoord2f(var8 / 256.0f, (var9 + 15.98f) / 256.0f);
        GL11.glVertex3f(this.posX - var11, this.posY + 7.99f, 0.0f);
        GL11.glTexCoord2f((var8 + var10) / 256.0f, var9 / 256.0f);
        GL11.glVertex3f(this.posX + var10 / 2.0f + var11, this.posY, 0.0f);
        GL11.glTexCoord2f((var8 + var10) / 256.0f, (var9 + 15.98f) / 256.0f);
        GL11.glVertex3f(this.posX + var10 / 2.0f - var11, this.posY + 7.99f, 0.0f);
        GL11.glEnd();
        return (var7 - var6) / 2.0f + 1.0f;
    }
    
    public int drawStringWithShadow(final String p_78261_1_, final float p_78261_2_, final float p_78261_3_, final int p_78261_4_) {
        return this.drawString(p_78261_1_, (int)p_78261_2_, (int)p_78261_3_, p_78261_4_, true);
    }
    
    public int drawStringWithShadow(final String p_78261_1_, final int p_78261_2_, final int p_78261_3_, final int p_78261_4_) {
        return this.drawString(p_78261_1_, p_78261_2_, p_78261_3_, p_78261_4_, true);
    }
    
    public int drawString(final String p_78276_1_, final float p_78276_2_, final float p_78276_3_, final int p_78276_4_) {
        return this.drawString(p_78276_1_, (int)p_78276_2_, (int)p_78276_3_, p_78276_4_, false);
    }
    
    public int drawString(final String p_78276_1_, final int p_78276_2_, final int p_78276_3_, final int p_78276_4_) {
        return this.drawString(p_78276_1_, p_78276_2_, p_78276_3_, p_78276_4_, false);
    }
    
    public int drawString(final String p_85187_1_, final int p_85187_2_, final int p_85187_3_, final int p_85187_4_, final boolean p_85187_5_) {
        GL11.glEnable(3008);
        this.resetStyles();
        int var6;
        if (p_85187_5_) {
            var6 = this.renderString(p_85187_1_, p_85187_2_ + 1, p_85187_3_ + 1, p_85187_4_, true);
            var6 = Math.max(var6, this.renderString(p_85187_1_, p_85187_2_, p_85187_3_, p_85187_4_, false));
        }
        else {
            var6 = this.renderString(p_85187_1_, p_85187_2_, p_85187_3_, p_85187_4_, false);
        }
        return var6;
    }
    
    private String func_147647_b(final String p_147647_1_) {
        try {
            final Bidi var2 = new Bidi(new ArabicShaping(8).shape(p_147647_1_), 127);
            var2.setReorderingMode(0);
            return var2.writeReordered(2);
        }
        catch (ArabicShapingException var3) {
            return p_147647_1_;
        }
    }
    
    private void resetStyles() {
        this.randomStyle = false;
        this.boldStyle = false;
        this.italicStyle = false;
        this.underlineStyle = false;
        this.strikethroughStyle = false;
    }
    
    private void renderStringAtPos(final String p_78255_1_, final boolean p_78255_2_) {
        for (int var3 = 0; var3 < p_78255_1_.length(); ++var3) {
            final char var4 = p_78255_1_.charAt(var3);
            if (var4 == '§' && var3 + 1 < p_78255_1_.length()) {
                int var5 = "0123456789abcdefklmnor".indexOf(p_78255_1_.toLowerCase().charAt(var3 + 1));
                if (var5 < 16) {
                    this.randomStyle = false;
                    this.boldStyle = false;
                    this.strikethroughStyle = false;
                    this.underlineStyle = false;
                    this.italicStyle = false;
                    if (var5 < 0 || var5 > 15) {
                        var5 = 15;
                    }
                    if (p_78255_2_) {
                        var5 += 16;
                    }
                    final int var6 = this.colorCode[var5];
                    this.textColor = var6;
                    GL11.glColor4f((var6 >> 16) / 255.0f, (var6 >> 8 & 0xFF) / 255.0f, (var6 & 0xFF) / 255.0f, this.alpha);
                }
                else if (var5 == 16) {
                    this.randomStyle = true;
                }
                else if (var5 == 17) {
                    this.boldStyle = true;
                }
                else if (var5 == 18) {
                    this.strikethroughStyle = true;
                }
                else if (var5 == 19) {
                    this.underlineStyle = true;
                }
                else if (var5 == 20) {
                    this.italicStyle = true;
                }
                else if (var5 == 21) {
                    this.randomStyle = false;
                    this.boldStyle = false;
                    this.strikethroughStyle = false;
                    this.underlineStyle = false;
                    this.italicStyle = false;
                    GL11.glColor4f(this.red, this.blue, this.green, this.alpha);
                }
                ++var3;
            }
            else {
                int var5 = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8£\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1ªº¿®¬½¼¡«»\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261±\u2265\u2264\u2320\u2321\u00f7\u2248°\u2219·\u221a\u207f²\u25a0\u0000".indexOf(var4);
                if (this.randomStyle && var5 != -1) {
                    int var6;
                    do {
                        var6 = this.fontRandom.nextInt(this.charWidth.length);
                    } while (this.charWidth[var5] != this.charWidth[var6]);
                    var5 = var6;
                }
                final float var7 = this.unicodeFlag ? 0.5f : 1.0f;
                final boolean var8 = (var4 == '\0' || var5 == -1 || this.unicodeFlag) && p_78255_2_;
                if (var8) {
                    this.posX -= var7;
                    this.posY -= var7;
                }
                float var9 = this.renderCharAtPos(var5, var4, this.italicStyle);
                if (var8) {
                    this.posX += var7;
                    this.posY += var7;
                }
                if (this.boldStyle) {
                    this.posX += var7;
                    if (var8) {
                        this.posX -= var7;
                        this.posY -= var7;
                    }
                    this.renderCharAtPos(var5, var4, this.italicStyle);
                    this.posX -= var7;
                    if (var8) {
                        this.posX += var7;
                        this.posY += var7;
                    }
                    ++var9;
                }
                if (this.strikethroughStyle) {
                    final Tessellator var10 = Tessellator.instance;
                    GL11.glDisable(3553);
                    var10.startDrawingQuads();
                    var10.addVertex(this.posX, this.posY + this.FONT_HEIGHT / 2, 0.0);
                    var10.addVertex(this.posX + var9, this.posY + this.FONT_HEIGHT / 2, 0.0);
                    var10.addVertex(this.posX + var9, this.posY + this.FONT_HEIGHT / 2 - 1.0f, 0.0);
                    var10.addVertex(this.posX, this.posY + this.FONT_HEIGHT / 2 - 1.0f, 0.0);
                    var10.draw();
                    GL11.glEnable(3553);
                }
                if (this.underlineStyle) {
                    final Tessellator var10 = Tessellator.instance;
                    GL11.glDisable(3553);
                    var10.startDrawingQuads();
                    final int var11 = this.underlineStyle ? -1 : 0;
                    var10.addVertex(this.posX + var11, this.posY + this.FONT_HEIGHT, 0.0);
                    var10.addVertex(this.posX + var9, this.posY + this.FONT_HEIGHT, 0.0);
                    var10.addVertex(this.posX + var9, this.posY + this.FONT_HEIGHT - 1.0f, 0.0);
                    var10.addVertex(this.posX + var11, this.posY + this.FONT_HEIGHT - 1.0f, 0.0);
                    var10.draw();
                    GL11.glEnable(3553);
                }
                this.posX += (int)var9;
            }
        }
    }
    
    private int renderStringAligned(final String p_78274_1_, int p_78274_2_, final int p_78274_3_, final int p_78274_4_, final int p_78274_5_, final boolean p_78274_6_) {
        if (this.bidiFlag) {
            final int var7 = this.getStringWidth(this.func_147647_b(p_78274_1_));
            p_78274_2_ = p_78274_2_ + p_78274_4_ - var7;
        }
        return this.renderString(p_78274_1_, p_78274_2_, p_78274_3_, p_78274_5_, p_78274_6_);
    }
    
    private int renderString(String p_78258_1_, final int p_78258_2_, final int p_78258_3_, int p_78258_4_, final boolean p_78258_5_) {
        if (p_78258_1_ == null) {
            return 0;
        }
        if (this.bidiFlag) {
            p_78258_1_ = this.func_147647_b(p_78258_1_);
        }
        if ((p_78258_4_ & 0xFC000000) == 0x0) {
            p_78258_4_ |= 0xFF000000;
        }
        if (p_78258_5_) {
            p_78258_4_ = ((p_78258_4_ & 0xFCFCFC) >> 2 | (p_78258_4_ & 0xFF000000));
        }
        this.red = (p_78258_4_ >> 16 & 0xFF) / 255.0f;
        this.blue = (p_78258_4_ >> 8 & 0xFF) / 255.0f;
        this.green = (p_78258_4_ & 0xFF) / 255.0f;
        this.alpha = (p_78258_4_ >> 24 & 0xFF) / 255.0f;
        GL11.glColor4f(this.red, this.blue, this.green, this.alpha);
        this.posX = (float)p_78258_2_;
        this.posY = (float)p_78258_3_;
        this.renderStringAtPos(p_78258_1_, p_78258_5_);
        return (int)this.posX;
    }
    
    public int getStringWidth(final String p_78256_1_) {
        if (p_78256_1_ == null) {
            return 0;
        }
        int var2 = 0;
        boolean var3 = false;
        for (int var4 = 0; var4 < p_78256_1_.length(); ++var4) {
            char var5 = p_78256_1_.charAt(var4);
            int var6 = this.getCharWidth(var5);
            if (var6 < 0 && var4 < p_78256_1_.length() - 1) {
                ++var4;
                var5 = p_78256_1_.charAt(var4);
                if (var5 != 'l' && var5 != 'L') {
                    if (var5 == 'r' || var5 == 'R') {
                        var3 = false;
                    }
                }
                else {
                    var3 = true;
                }
                var6 = 0;
            }
            var2 += var6;
            if (var3 && var6 > 0) {
                ++var2;
            }
        }
        return var2;
    }
    
    public int getCharWidth(final char p_78263_1_) {
        if (p_78263_1_ == '§') {
            return -1;
        }
        if (p_78263_1_ == ' ') {
            return 4;
        }
        final int var2 = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8£\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1ªº¿®¬½¼¡«»\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261±\u2265\u2264\u2320\u2321\u00f7\u2248°\u2219·\u221a\u207f²\u25a0\u0000".indexOf(p_78263_1_);
        if (p_78263_1_ > '\0' && var2 != -1 && !this.unicodeFlag) {
            return this.charWidth[var2];
        }
        if (this.glyphWidth[p_78263_1_] != 0) {
            int var3 = this.glyphWidth[p_78263_1_] >>> 4;
            int var4 = this.glyphWidth[p_78263_1_] & 0xF;
            if (var4 > 7) {
                var4 = 15;
                var3 = 0;
            }
            return (++var4 - var3) / 2 + 1;
        }
        return 0;
    }
    
    public String trimStringToWidth(final String p_78269_1_, final int p_78269_2_) {
        return this.trimStringToWidth(p_78269_1_, p_78269_2_, false);
    }
    
    public String trimStringToWidth(final String p_78262_1_, final int p_78262_2_, final boolean p_78262_3_) {
        final StringBuilder var4 = new StringBuilder();
        int var5 = 0;
        final int var6 = p_78262_3_ ? (p_78262_1_.length() - 1) : 0;
        final int var7 = p_78262_3_ ? -1 : 1;
        boolean var8 = false;
        boolean var9 = false;
        for (int var10 = var6; var10 >= 0 && var10 < p_78262_1_.length() && var5 < p_78262_2_; var10 += var7) {
            final char var11 = p_78262_1_.charAt(var10);
            final int var12 = this.getCharWidth(var11);
            if (var8) {
                var8 = false;
                if (var11 != 'l' && var11 != 'L') {
                    if (var11 == 'r' || var11 == 'R') {
                        var9 = false;
                    }
                }
                else {
                    var9 = true;
                }
            }
            else if (var12 < 0) {
                var8 = true;
            }
            else {
                var5 += var12;
                if (var9) {
                    ++var5;
                }
            }
            if (var5 > p_78262_2_) {
                break;
            }
            if (p_78262_3_) {
                var4.insert(0, var11);
            }
            else {
                var4.append(var11);
            }
        }
        return var4.toString();
    }
    
    private String trimStringNewline(String p_78273_1_) {
        while (p_78273_1_ != null && p_78273_1_.endsWith("\n")) {
            p_78273_1_ = p_78273_1_.substring(0, p_78273_1_.length() - 1);
        }
        return p_78273_1_;
    }
    
    public void drawSplitString(String p_78279_1_, final int p_78279_2_, final int p_78279_3_, final int p_78279_4_, final int p_78279_5_) {
        this.resetStyles();
        this.textColor = p_78279_5_;
        p_78279_1_ = this.trimStringNewline(p_78279_1_);
        this.renderSplitString(p_78279_1_, p_78279_2_, p_78279_3_, p_78279_4_, false);
    }
    
    private void renderSplitString(final String p_78268_1_, final int p_78268_2_, int p_78268_3_, final int p_78268_4_, final boolean p_78268_5_) {
        final List var6 = this.listFormattedStringToWidth(p_78268_1_, p_78268_4_);
        for (final String var8 : var6) {
            this.renderStringAligned(var8, p_78268_2_, p_78268_3_, p_78268_4_, this.textColor, p_78268_5_);
            p_78268_3_ += this.FONT_HEIGHT;
        }
    }
    
    public int splitStringWidth(final String p_78267_1_, final int p_78267_2_) {
        return this.FONT_HEIGHT * this.listFormattedStringToWidth(p_78267_1_, p_78267_2_).size();
    }
    
    public void setUnicodeFlag(final boolean p_78264_1_) {
        this.unicodeFlag = p_78264_1_;
    }
    
    public boolean getUnicodeFlag() {
        return this.unicodeFlag;
    }
    
    public void setBidiFlag(final boolean p_78275_1_) {
        this.bidiFlag = p_78275_1_;
    }
    
    public List listFormattedStringToWidth(final String p_78271_1_, final int p_78271_2_) {
        return Arrays.asList(this.wrapFormattedStringToWidth(p_78271_1_, p_78271_2_).split("\n"));
    }
    
    String wrapFormattedStringToWidth(final String p_78280_1_, final int p_78280_2_) {
        final int var3 = this.sizeStringToWidth(p_78280_1_, p_78280_2_);
        if (p_78280_1_.length() <= var3) {
            return p_78280_1_;
        }
        final String var4 = p_78280_1_.substring(0, var3);
        final char var5 = p_78280_1_.charAt(var3);
        final boolean var6 = var5 == ' ' || var5 == '\n';
        final String var7 = getFormatFromString(var4) + p_78280_1_.substring(var3 + (var6 ? 1 : 0));
        return var4 + "\n" + this.wrapFormattedStringToWidth(var7, p_78280_2_);
    }
    
    private int sizeStringToWidth(final String p_78259_1_, final int p_78259_2_) {
        final int var3 = p_78259_1_.length();
        int var4 = 0;
        int var5 = 0;
        int var6 = -1;
        boolean var7 = false;
        while (var5 < var3) {
            final char var8 = p_78259_1_.charAt(var5);
            Label_0164: {
                switch (var8) {
                    case '\n': {
                        --var5;
                        break Label_0164;
                    }
                    case '§': {
                        if (var5 < var3 - 1) {
                            ++var5;
                            final char var9 = p_78259_1_.charAt(var5);
                            if (var9 != 'l' && var9 != 'L') {
                                if (var9 == 'r' || var9 == 'R' || isFormatColor(var9)) {
                                    var7 = false;
                                }
                            }
                            else {
                                var7 = true;
                            }
                        }
                        break Label_0164;
                    }
                    case ' ': {
                        var6 = var5;
                        break;
                    }
                }
                var4 += this.getCharWidth(var8);
                if (var7) {
                    ++var4;
                }
            }
            if (var8 == '\n') {
                var6 = ++var5;
                break;
            }
            if (var4 > p_78259_2_) {
                break;
            }
            ++var5;
        }
        return (var5 != var3 && var6 != -1 && var6 < var5) ? var6 : var5;
    }
    
    private static boolean isFormatColor(final char p_78272_0_) {
        return (p_78272_0_ >= '0' && p_78272_0_ <= '9') || (p_78272_0_ >= 'a' && p_78272_0_ <= 'f') || (p_78272_0_ >= 'A' && p_78272_0_ <= 'F');
    }
    
    private static boolean isFormatSpecial(final char p_78270_0_) {
        return (p_78270_0_ >= 'k' && p_78270_0_ <= 'o') || (p_78270_0_ >= 'K' && p_78270_0_ <= 'O') || p_78270_0_ == 'r' || p_78270_0_ == 'R';
    }
    
    private static String getFormatFromString(final String p_78282_0_) {
        String var1 = "";
        int var2 = -1;
        final int var3 = p_78282_0_.length();
        while ((var2 = p_78282_0_.indexOf(167, var2 + 1)) != -1) {
            if (var2 < var3 - 1) {
                final char var4 = p_78282_0_.charAt(var2 + 1);
                if (isFormatColor(var4)) {
                    var1 = "§" + var4;
                }
                else {
                    if (!isFormatSpecial(var4)) {
                        continue;
                    }
                    var1 = var1 + "§" + var4;
                }
            }
        }
        return var1;
    }
    
    public boolean getBidiFlag() {
        return this.bidiFlag;
    }
    
    static {
        unicodePageLocations = new ResourceLocation[256];
    }
}
