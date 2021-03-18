package net.minecraft.client.gui;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.*;
import java.awt.*;
import java.awt.datatransfer.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import java.util.*;
import org.lwjgl.input.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.*;

public class GuiScreen extends Gui
{
    protected static RenderItem itemRender;
    public boolean initialised;
    protected Minecraft mc;
    public int width;
    public int height;
    protected List buttonList;
    protected List labelList;
    public boolean field_146291_p;
    protected FontRenderer fontRendererObj;
    private GuiButton selectedButton;
    private int eventButton;
    private long lastMouseEvent;
    private int field_146298_h;
    private static final String __OBFID = "CL_00000710";
    
    public GuiScreen() {
        this.buttonList = new ArrayList();
        this.labelList = new ArrayList();
    }
    
    public void drawScreen(final int p_73863_1_, final int p_73863_2_, final float p_73863_3_) {
        for (int var4 = 0; var4 < this.buttonList.size(); ++var4) {
            this.buttonList.get(var4).drawButton(this.mc, p_73863_1_, p_73863_2_);
        }
        for (int var4 = 0; var4 < this.labelList.size(); ++var4) {
            this.labelList.get(var4).func_146159_a(this.mc, p_73863_1_, p_73863_2_);
        }
    }
    
    protected void keyTyped(final char p_73869_1_, final int p_73869_2_) {
        if (p_73869_2_ == 1) {
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
        }
    }
    
    public static String getClipboardString() {
        try {
            final Transferable var0 = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            if (var0 != null && var0.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                return (String)var0.getTransferData(DataFlavor.stringFlavor);
            }
        }
        catch (Exception ex) {}
        return "";
    }
    
    public static void setClipboardString(final String p_146275_0_) {
        try {
            final StringSelection var1 = new StringSelection(p_146275_0_);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(var1, null);
        }
        catch (Exception ex) {}
    }
    
    protected void func_146285_a(final ItemStack p_146285_1_, final int p_146285_2_, final int p_146285_3_) {
        final List var4 = p_146285_1_.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
        for (int var5 = 0; var5 < var4.size(); ++var5) {
            if (var5 == 0) {
                var4.set(var5, p_146285_1_.getRarity().rarityColor + var4.get(var5));
            }
            else {
                var4.set(var5, EnumChatFormatting.GRAY + var4.get(var5));
            }
        }
        this.func_146283_a(var4, p_146285_2_, p_146285_3_);
    }
    
    protected void func_146279_a(final String p_146279_1_, final int p_146279_2_, final int p_146279_3_) {
        this.func_146283_a(Arrays.asList(p_146279_1_), p_146279_2_, p_146279_3_);
    }
    
    protected void func_146283_a(final List p_146283_1_, final int p_146283_2_, final int p_146283_3_) {
        if (!p_146283_1_.isEmpty()) {
            GL11.glDisable(32826);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            int var4 = 0;
            for (final String var6 : p_146283_1_) {
                final int var7 = this.fontRendererObj.getStringWidth(var6);
                if (var7 > var4) {
                    var4 = var7;
                }
            }
            int var8 = p_146283_2_ + 12;
            int var9 = p_146283_3_ - 12;
            int var10 = 8;
            if (p_146283_1_.size() > 1) {
                var10 += 2 + (p_146283_1_.size() - 1) * 10;
            }
            if (var8 + var4 > this.width) {
                var8 -= 28 + var4;
            }
            if (var9 + var10 + 6 > this.height) {
                var9 = this.height - var10 - 6;
            }
            this.zLevel = 300.0f;
            GuiScreen.itemRender.zLevel = 300.0f;
            final int var11 = -267386864;
            this.drawGradientRect(var8 - 3, var9 - 4, var8 + var4 + 3, var9 - 3, var11, var11);
            this.drawGradientRect(var8 - 3, var9 + var10 + 3, var8 + var4 + 3, var9 + var10 + 4, var11, var11);
            this.drawGradientRect(var8 - 3, var9 - 3, var8 + var4 + 3, var9 + var10 + 3, var11, var11);
            this.drawGradientRect(var8 - 4, var9 - 3, var8 - 3, var9 + var10 + 3, var11, var11);
            this.drawGradientRect(var8 + var4 + 3, var9 - 3, var8 + var4 + 4, var9 + var10 + 3, var11, var11);
            final int var12 = 1347420415;
            final int var13 = (var12 & 0xFEFEFE) >> 1 | (var12 & 0xFF000000);
            this.drawGradientRect(var8 - 3, var9 - 3 + 1, var8 - 3 + 1, var9 + var10 + 3 - 1, var12, var13);
            this.drawGradientRect(var8 + var4 + 2, var9 - 3 + 1, var8 + var4 + 3, var9 + var10 + 3 - 1, var12, var13);
            this.drawGradientRect(var8 - 3, var9 - 3, var8 + var4 + 3, var9 - 3 + 1, var12, var12);
            this.drawGradientRect(var8 - 3, var9 + var10 + 2, var8 + var4 + 3, var9 + var10 + 3, var13, var13);
            for (int var14 = 0; var14 < p_146283_1_.size(); ++var14) {
                final String var15 = p_146283_1_.get(var14);
                this.fontRendererObj.drawStringWithShadow(var15, var8, var9, -1);
                if (var14 == 0) {
                    var9 += 2;
                }
                var9 += 10;
            }
            this.zLevel = 0.0f;
            GuiScreen.itemRender.zLevel = 0.0f;
            GL11.glEnable(2896);
            GL11.glEnable(2929);
            RenderHelper.enableStandardItemLighting();
            GL11.glEnable(32826);
        }
    }
    
    protected void mouseClicked(final int p_73864_1_, final int p_73864_2_, final int p_73864_3_) {
        if (p_73864_3_ == 0) {
            for (int var4 = 0; var4 < this.buttonList.size(); ++var4) {
                final GuiButton var5 = this.buttonList.get(var4);
                if (var5.mousePressed(this.mc, p_73864_1_, p_73864_2_)) {
                    (this.selectedButton = var5).func_146113_a(this.mc.getSoundHandler());
                    this.actionPerformed(var5);
                }
            }
        }
    }
    
    protected void mouseMovedOrUp(final int p_146286_1_, final int p_146286_2_, final int p_146286_3_) {
        if (this.selectedButton != null && p_146286_3_ == 0) {
            this.selectedButton.mouseReleased(p_146286_1_, p_146286_2_);
            this.selectedButton = null;
        }
    }
    
    protected void mouseClickMove(final int p_146273_1_, final int p_146273_2_, final int p_146273_3_, final long p_146273_4_) {
    }
    
    protected void actionPerformed(final GuiButton p_146284_1_) {
    }
    
    public void setWorldAndResolution(final Minecraft p_146280_1_, final int p_146280_2_, final int p_146280_3_) {
        this.mc = p_146280_1_;
        this.fontRendererObj = p_146280_1_.fontRenderer;
        this.width = p_146280_2_;
        this.height = p_146280_3_;
        this.buttonList.clear();
        this.initGui();
        this.initialised = true;
    }
    
    public void initGui() {
    }
    
    public void handleInput() {
        if (Mouse.isCreated()) {
            while (Mouse.next()) {
                this.handleMouseInput();
            }
        }
        if (Keyboard.isCreated()) {
            while (Keyboard.next()) {
                this.handleKeyboardInput();
            }
        }
    }
    
    public void handleMouseInput() {
        final int var1 = Mouse.getEventX() * this.width / this.mc.displayWidth;
        final int var2 = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        final int var3 = Mouse.getEventButton();
        if (Mouse.getEventButtonState()) {
            if (this.mc.gameSettings.touchscreen && this.field_146298_h++ > 0) {
                return;
            }
            this.eventButton = var3;
            this.lastMouseEvent = Minecraft.getSystemTime();
            this.mouseClicked(var1, var2, this.eventButton);
        }
        else if (var3 != -1) {
            if (this.mc.gameSettings.touchscreen && --this.field_146298_h > 0) {
                return;
            }
            this.eventButton = -1;
            this.mouseMovedOrUp(var1, var2, var3);
        }
        else if (this.eventButton != -1 && this.lastMouseEvent > 0L) {
            final long var4 = Minecraft.getSystemTime() - this.lastMouseEvent;
            this.mouseClickMove(var1, var2, this.eventButton, var4);
        }
    }
    
    public void handleKeyboardInput() {
        if (Keyboard.getEventKeyState()) {
            this.keyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
        }
        this.mc.func_152348_aa();
    }
    
    public void updateScreen() {
        this.initialised = true;
    }
    
    public void onGuiClosed() {
    }
    
    public void drawDefaultBackground() {
        this.func_146270_b(0);
    }
    
    public void func_146270_b(final int p_146270_1_) {
        if (this.mc.theWorld != null) {
            if (!(this instanceof GuiContainer)) {
                this.drawGradientRect(0, 0, this.width, this.height, -1072689136, -804253680);
            }
        }
        else {
            this.func_146278_c(p_146270_1_);
        }
    }
    
    public void func_146278_c(final int p_146278_1_) {
        GL11.glDisable(2896);
        GL11.glDisable(2912);
        final Tessellator var2 = Tessellator.instance;
        this.mc.getTextureManager().bindTexture(GuiScreen.optionsBackground);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final float var3 = 32.0f;
        var2.startDrawingQuads();
        var2.setColorOpaque_I(4210752);
        var2.addVertexWithUV(0.0, this.height, 0.0, 0.0, this.height / var3 + p_146278_1_);
        var2.addVertexWithUV(this.width, this.height, 0.0, this.width / var3, this.height / var3 + p_146278_1_);
        var2.addVertexWithUV(this.width, 0.0, 0.0, this.width / var3, p_146278_1_);
        var2.addVertexWithUV(0.0, 0.0, 0.0, 0.0, p_146278_1_);
        var2.draw();
    }
    
    public boolean doesGuiPauseGame() {
        return true;
    }
    
    public void confirmClicked(final boolean p_73878_1_, final int p_73878_2_) {
    }
    
    public static boolean isCtrlKeyDown() {
        return Minecraft.isRunningOnMac ? (Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220)) : (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157));
    }
    
    public static boolean isShiftKeyDown() {
        return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
    }
    
    static {
        GuiScreen.itemRender = new RenderItem();
    }
}
