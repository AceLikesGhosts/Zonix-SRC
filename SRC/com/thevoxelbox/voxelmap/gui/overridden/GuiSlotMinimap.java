package com.thevoxelbox.voxelmap.gui.overridden;

import net.minecraft.client.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;

public abstract class GuiSlotMinimap
{
    protected final int slotHeight;
    private final Minecraft mc;
    protected int width;
    protected int top;
    protected int bottom;
    protected int right;
    protected int left;
    protected int slotWidth;
    protected int mouseX;
    protected int mouseY;
    protected boolean field_148163_i;
    protected int headerPadding;
    private int height;
    private int scrollUpButtonID;
    private int scrollDownButtonID;
    private float initialClickY;
    private float scrollMultiplier;
    private float amountScrolled;
    private int selectedElement;
    private long lastClicked;
    private boolean showSelectionBox;
    private boolean showTopBottomBG;
    private boolean showSlotBG;
    private boolean hasListHeader;
    private boolean field_148164_v;
    
    public GuiSlotMinimap(final Minecraft par1Minecraft, final int par2, final int par3, final int par4, final int par5, final int par6) {
        this.slotWidth = 220;
        this.field_148163_i = true;
        this.initialClickY = -2.0f;
        this.selectedElement = -1;
        this.showSelectionBox = true;
        this.showTopBottomBG = true;
        this.showSlotBG = true;
        this.field_148164_v = true;
        this.mc = par1Minecraft;
        this.width = par2;
        this.height = par3;
        this.top = par4;
        this.bottom = par5;
        this.slotHeight = par6;
        this.left = 0;
        this.right = par2;
    }
    
    public void func_148122_a(final int p_148122_1_, final int p_148122_2_, final int p_148122_3_, final int p_148122_4_) {
        this.width = p_148122_1_;
        this.height = p_148122_2_;
        this.top = p_148122_3_;
        this.bottom = p_148122_4_;
        this.left = 0;
        this.right = p_148122_1_;
    }
    
    public void setShowSelectionBox(final boolean p_148130_1_) {
        this.showSelectionBox = p_148130_1_;
    }
    
    public void setShowTopBottomBG(final boolean par1) {
        this.showTopBottomBG = par1;
    }
    
    public void setShowSlotBG(final boolean par1) {
        this.showSlotBG = par1;
    }
    
    protected void setHasListHeader(final boolean p_148133_1_, final int p_148133_2_) {
        this.hasListHeader = p_148133_1_;
        this.headerPadding = p_148133_2_;
        if (!p_148133_1_) {
            this.headerPadding = 0;
        }
    }
    
    protected abstract int getSize();
    
    protected abstract void elementClicked(final int p0, final boolean p1, final int p2, final int p3);
    
    protected abstract boolean isSelected(final int p0);
    
    protected int getContentHeight() {
        return this.getSize() * this.slotHeight + this.headerPadding;
    }
    
    protected abstract void drawBackground();
    
    protected abstract void drawSlot(final int p0, final int p1, final int p2, final int p3, final Tessellator p4, final int p5, final int p6);
    
    protected void drawListHeader(final int p_148129_1_, final int p_148129_2_, final Tessellator p_148129_3_) {
    }
    
    protected void func_148132_a(final int p_148132_1_, final int p_148132_2_) {
    }
    
    protected void func_148142_b(final int p_148142_1_, final int p_148142_2_) {
    }
    
    public int func_148124_c(final int p_148124_1_, final int p_148124_2_) {
        final int var3 = this.left + this.width / 2 - this.getListWidth() / 2;
        final int var4 = this.left + this.width / 2 + this.getListWidth() / 2;
        final int var5 = p_148124_2_ - this.top - this.headerPadding + (int)this.amountScrolled - 4;
        final int var6 = var5 / this.slotHeight;
        return (p_148124_1_ < this.getScrollBarX() && p_148124_1_ >= var3 && p_148124_1_ <= var4 && var6 >= 0 && var5 >= 0 && var6 < this.getSize()) ? var6 : -1;
    }
    
    public void registerScrollButtons(final int p_148134_1_, final int p_148134_2_) {
        this.scrollUpButtonID = p_148134_1_;
        this.scrollDownButtonID = p_148134_2_;
    }
    
    private void bindAmountScrolled() {
        int var1 = this.func_148135_f();
        if (var1 < 0) {
            var1 /= 2;
        }
        if (!this.field_148163_i && var1 < 0) {
            var1 = 0;
        }
        if (this.amountScrolled < 0.0f) {
            this.amountScrolled = 0.0f;
        }
        if (this.amountScrolled > var1) {
            this.amountScrolled = (float)var1;
        }
    }
    
    public int func_148135_f() {
        return this.getContentHeight() - (this.bottom - this.top - 4);
    }
    
    public int getAmountScrolled() {
        return (int)this.amountScrolled;
    }
    
    public boolean func_148141_e(final int p_148141_1_) {
        return p_148141_1_ >= this.top && p_148141_1_ <= this.bottom;
    }
    
    public void scrollBy(final int p_148145_1_) {
        this.amountScrolled += p_148145_1_;
        this.bindAmountScrolled();
        this.initialClickY = -2.0f;
    }
    
    public void actionPerformed(final GuiButton p_148147_1_) {
        if (p_148147_1_.enabled) {
            if (p_148147_1_.id == this.scrollUpButtonID) {
                this.amountScrolled -= this.slotHeight * 2 / 3;
                this.initialClickY = -2.0f;
                this.bindAmountScrolled();
            }
            else if (p_148147_1_.id == this.scrollDownButtonID) {
                this.amountScrolled += this.slotHeight * 2 / 3;
                this.initialClickY = -2.0f;
                this.bindAmountScrolled();
            }
        }
    }
    
    public void drawScreen(final int p_148128_1_, final int p_148128_2_, final float p_148128_3_) {
        this.mouseX = p_148128_1_;
        this.mouseY = p_148128_2_;
        this.drawBackground();
        final int var4 = this.getSize();
        final int var5 = this.getScrollBarX();
        final int var6 = var5 + 6;
        if (p_148128_1_ > this.left && p_148128_1_ < this.right && p_148128_2_ > this.top && p_148128_2_ < this.bottom) {
            if (Mouse.isButtonDown(0) && this.func_148125_i()) {
                if (this.initialClickY == -1.0f) {
                    boolean var7 = true;
                    if (p_148128_2_ >= this.top && p_148128_2_ <= this.bottom) {
                        final int var8 = this.width / 2 - this.getListWidth() / 2;
                        final int var9 = this.width / 2 + this.getListWidth() / 2;
                        final int var10 = p_148128_2_ - this.top - this.headerPadding + (int)this.amountScrolled - 4;
                        final int var11 = var10 / this.slotHeight;
                        if (p_148128_1_ >= var8 && p_148128_1_ <= var9 && var11 >= 0 && var10 >= 0 && var11 < var4) {
                            final boolean var12 = var11 == this.selectedElement && Minecraft.getSystemTime() - this.lastClicked < 250L;
                            this.elementClicked(var11, var12, p_148128_1_, p_148128_2_);
                            this.selectedElement = var11;
                            this.lastClicked = Minecraft.getSystemTime();
                        }
                        else if (p_148128_1_ >= var8 && p_148128_1_ <= var9 && var10 < 0) {
                            this.func_148132_a(p_148128_1_ - var8, p_148128_2_ - this.top + (int)this.amountScrolled - 4);
                            var7 = false;
                        }
                        if (p_148128_1_ >= var5 && p_148128_1_ <= var6) {
                            this.scrollMultiplier = -1.0f;
                            int var13 = this.func_148135_f();
                            if (var13 < 1) {
                                var13 = 1;
                            }
                            int var14 = (this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight();
                            if (var14 < 32) {
                                var14 = 32;
                            }
                            if (var14 > this.bottom - this.top - 8) {
                                var14 = this.bottom - this.top - 8;
                            }
                            this.scrollMultiplier /= (this.bottom - this.top - var14) / var13;
                        }
                        else {
                            this.scrollMultiplier = 1.0f;
                        }
                        if (var7) {
                            this.initialClickY = (float)p_148128_2_;
                        }
                        else {
                            this.initialClickY = -2.0f;
                        }
                    }
                    else {
                        this.initialClickY = -2.0f;
                    }
                }
                else if (this.initialClickY >= 0.0f) {
                    this.amountScrolled -= (p_148128_2_ - this.initialClickY) * this.scrollMultiplier;
                    this.initialClickY = (float)p_148128_2_;
                }
            }
            else {
                while (!this.mc.gameSettings.touchscreen && Mouse.next()) {
                    int var15 = Mouse.getEventDWheel();
                    if (var15 != 0) {
                        if (var15 > 0) {
                            var15 = -1;
                        }
                        else if (var15 < 0) {
                            var15 = 1;
                        }
                        this.amountScrolled += var15 * this.slotHeight / 2;
                    }
                    this.mc.currentScreen.handleMouseInput();
                }
                this.initialClickY = -1.0f;
            }
        }
        this.bindAmountScrolled();
        GL11.glDisable(2896);
        GL11.glDisable(2912);
        final Tessellator var16 = Tessellator.instance;
        if (this.showSlotBG) {
            this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            final float var17 = 32.0f;
            var16.startDrawingQuads();
            var16.setColorOpaque_I(2105376);
            var16.addVertexWithUV(this.left, this.bottom, 0.0, this.left / var17, (this.bottom + (int)this.amountScrolled) / var17);
            var16.addVertexWithUV(this.right, this.bottom, 0.0, this.right / var17, (this.bottom + (int)this.amountScrolled) / var17);
            var16.addVertexWithUV(this.right, this.top, 0.0, this.right / var17, (this.top + (int)this.amountScrolled) / var17);
            var16.addVertexWithUV(this.left, this.top, 0.0, this.left / var17, (this.top + (int)this.amountScrolled) / var17);
            var16.draw();
        }
        final int var18 = this.left + this.width / 2 - this.getListWidth() / 2 + 2;
        final int var19 = this.top + 4 - (int)this.amountScrolled;
        if (this.hasListHeader) {
            this.drawListHeader(var18, var19, var16);
        }
        this.drawSelectionBox(var18, var19, p_148128_1_, p_148128_2_);
        GL11.glDisable(2929);
        final byte var20 = 4;
        if (this.showTopBottomBG) {
            this.overlayBackground(0, this.top, 255, 255);
            this.overlayBackground(this.bottom, this.height, 255, 255);
        }
        GL11.glEnable(3042);
        OpenGlHelper.glBlendFunc(770, 771, 0, 1);
        GL11.glDisable(3008);
        GL11.glShadeModel(7425);
        GL11.glDisable(3553);
        if (this.showTopBottomBG) {
            var16.startDrawingQuads();
            var16.setColorRGBA_I(0, 0);
            var16.addVertexWithUV(this.left, this.top + var20, 0.0, 0.0, 1.0);
            var16.addVertexWithUV(this.right, this.top + var20, 0.0, 1.0, 1.0);
            var16.setColorRGBA_I(0, 255);
            var16.addVertexWithUV(this.right, this.top, 0.0, 1.0, 0.0);
            var16.addVertexWithUV(this.left, this.top, 0.0, 0.0, 0.0);
            var16.draw();
            var16.startDrawingQuads();
            var16.setColorRGBA_I(0, 255);
            var16.addVertexWithUV(this.left, this.bottom, 0.0, 0.0, 1.0);
            var16.addVertexWithUV(this.right, this.bottom, 0.0, 1.0, 1.0);
            var16.setColorRGBA_I(0, 0);
            var16.addVertexWithUV(this.right, this.bottom - var20, 0.0, 1.0, 0.0);
            var16.addVertexWithUV(this.left, this.bottom - var20, 0.0, 0.0, 0.0);
            var16.draw();
        }
        final int var21 = this.func_148135_f();
        if (var21 > 0) {
            int var22 = (this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight();
            if (var22 < 32) {
                var22 = 32;
            }
            if (var22 > this.bottom - this.top - 8) {
                var22 = this.bottom - this.top - 8;
            }
            int var23 = (int)this.amountScrolled * (this.bottom - this.top - var22) / var21 + this.top;
            if (var23 < this.top) {
                var23 = this.top;
            }
            var16.startDrawingQuads();
            var16.setColorRGBA_I(0, 255);
            var16.addVertexWithUV(var5, this.bottom, 0.0, 0.0, 1.0);
            var16.addVertexWithUV(var6, this.bottom, 0.0, 1.0, 1.0);
            var16.addVertexWithUV(var6, this.top, 0.0, 1.0, 0.0);
            var16.addVertexWithUV(var5, this.top, 0.0, 0.0, 0.0);
            var16.draw();
            var16.startDrawingQuads();
            var16.setColorRGBA_I(8421504, 255);
            var16.addVertexWithUV(var5, var23 + var22, 0.0, 0.0, 1.0);
            var16.addVertexWithUV(var6, var23 + var22, 0.0, 1.0, 1.0);
            var16.addVertexWithUV(var6, var23, 0.0, 1.0, 0.0);
            var16.addVertexWithUV(var5, var23, 0.0, 0.0, 0.0);
            var16.draw();
            var16.startDrawingQuads();
            var16.setColorRGBA_I(12632256, 255);
            var16.addVertexWithUV(var5, var23 + var22 - 1, 0.0, 0.0, 1.0);
            var16.addVertexWithUV(var6 - 1, var23 + var22 - 1, 0.0, 1.0, 1.0);
            var16.addVertexWithUV(var6 - 1, var23, 0.0, 1.0, 0.0);
            var16.addVertexWithUV(var5, var23, 0.0, 0.0, 0.0);
            var16.draw();
        }
        this.func_148142_b(p_148128_1_, p_148128_2_);
        GL11.glEnable(3553);
        GL11.glShadeModel(7424);
        GL11.glEnable(3008);
        GL11.glDisable(3042);
    }
    
    public void func_148143_b(final boolean p_148143_1_) {
        this.field_148164_v = p_148143_1_;
    }
    
    public boolean func_148125_i() {
        return this.field_148164_v;
    }
    
    public int getListWidth() {
        return this.slotWidth;
    }
    
    public void setSlotWidth(final int slotWidth) {
        this.slotWidth = slotWidth;
    }
    
    protected void drawSelectionBox(final int p_148120_1_, final int p_148120_2_, final int p_148120_3_, final int p_148120_4_) {
        final int var5 = this.getSize();
        final Tessellator var6 = Tessellator.instance;
        for (int var7 = 0; var7 < var5; ++var7) {
            final int var8 = p_148120_2_ + var7 * this.slotHeight + this.headerPadding;
            final int topFudge = this.showTopBottomBG ? (this.slotHeight - 4) : 0;
            final int bottomFudge = this.showTopBottomBG ? 0 : (this.slotHeight - 4);
            if (var8 + bottomFudge <= this.bottom && var8 + topFudge >= this.top) {
                if (this.showSelectionBox && this.isSelected(var7)) {
                    final int var9 = this.left + (this.width / 2 - this.getListWidth() / 2);
                    final int var10 = this.left + this.width / 2 + this.getListWidth() / 2;
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    GL11.glDisable(3553);
                    var6.startDrawingQuads();
                    var6.setColorOpaque_I(8421504);
                    var6.addVertexWithUV(var9, var8 + topFudge + 2, 0.0, 0.0, 1.0);
                    var6.addVertexWithUV(var10, var8 + topFudge + 2, 0.0, 1.0, 1.0);
                    var6.addVertexWithUV(var10, var8 - 2, 0.0, 1.0, 0.0);
                    var6.addVertexWithUV(var9, var8 - 2, 0.0, 0.0, 0.0);
                    var6.setColorOpaque_I(0);
                    var6.addVertexWithUV(var9 + 1, var8 + topFudge + 1, 0.0, 0.0, 1.0);
                    var6.addVertexWithUV(var10 - 1, var8 + topFudge + 1, 0.0, 1.0, 1.0);
                    var6.addVertexWithUV(var10 - 1, var8 - 1, 0.0, 1.0, 0.0);
                    var6.addVertexWithUV(var9 + 1, var8 - 1, 0.0, 0.0, 0.0);
                    var6.draw();
                    GL11.glEnable(3553);
                }
                this.drawSlot(var7, p_148120_1_, var8, topFudge, var6, p_148120_3_, p_148120_4_);
            }
        }
    }
    
    protected int getScrollBarX() {
        return this.width / 2 + 124;
    }
    
    protected void overlayBackground(final int p_148136_1_, final int p_148136_2_, final int p_148136_3_, final int p_148136_4_) {
        final Tessellator var5 = Tessellator.instance;
        this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final float var6 = 32.0f;
        var5.startDrawingQuads();
        var5.setColorRGBA_I(4210752, p_148136_4_);
        var5.addVertexWithUV(this.left, p_148136_2_, 0.0, 0.0, p_148136_2_ / var6);
        var5.addVertexWithUV(this.left + this.width, p_148136_2_, 0.0, this.width / var6, p_148136_2_ / var6);
        var5.setColorRGBA_I(4210752, p_148136_3_);
        var5.addVertexWithUV(this.left + this.width, p_148136_1_, 0.0, this.width / var6, p_148136_1_ / var6);
        var5.addVertexWithUV(this.left, p_148136_1_, 0.0, 0.0, p_148136_1_ / var6);
        var5.draw();
    }
    
    public void setSlotXBoundsFromLeft(final int p_148140_1_) {
        this.left = p_148140_1_;
        this.right = p_148140_1_ + this.width;
    }
    
    public int getSlotHeight() {
        return this.slotHeight;
    }
}
