package net.minecraft.client.gui.inventory;

import net.minecraft.inventory.*;
import net.minecraft.item.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import java.util.*;
import org.lwjgl.input.*;
import net.minecraft.entity.player.*;

public abstract class GuiContainer extends GuiScreen
{
    protected static final ResourceLocation field_147001_a;
    protected int field_146999_f;
    protected int field_147000_g;
    public Container field_147002_h;
    protected int field_147003_i;
    protected int field_147009_r;
    private Slot field_147006_u;
    private Slot field_147005_v;
    private boolean field_147004_w;
    private ItemStack field_147012_x;
    private int field_147011_y;
    private int field_147010_z;
    private Slot field_146989_A;
    private long field_146990_B;
    private ItemStack field_146991_C;
    private Slot field_146985_D;
    private long field_146986_E;
    protected final Set field_147008_s;
    protected boolean field_147007_t;
    private int field_146987_F;
    private int field_146988_G;
    private boolean field_146995_H;
    private int field_146996_I;
    private long field_146997_J;
    private Slot field_146998_K;
    private int field_146992_L;
    private boolean field_146993_M;
    private ItemStack field_146994_N;
    private static final String __OBFID = "CL_00000737";
    
    public GuiContainer(final Container p_i1072_1_) {
        this.field_146999_f = 176;
        this.field_147000_g = 166;
        this.field_147008_s = new HashSet();
        this.field_147002_h = p_i1072_1_;
        this.field_146995_H = true;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.mc.thePlayer.openContainer = this.field_147002_h;
        this.field_147003_i = (this.width - this.field_146999_f) / 2;
        this.field_147009_r = (this.height - this.field_147000_g) / 2;
    }
    
    @Override
    public void drawScreen(final int p_73863_1_, final int p_73863_2_, final float p_73863_3_) {
        this.drawDefaultBackground();
        final int var4 = this.field_147003_i;
        final int var5 = this.field_147009_r;
        this.func_146976_a(p_73863_3_, p_73863_1_, p_73863_2_);
        GL11.glDisable(32826);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)var4, (float)var5, 0.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(32826);
        this.field_147006_u = null;
        final short var6 = 240;
        final short var7 = 240;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0f, var7 / 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        for (int var8 = 0; var8 < this.field_147002_h.inventorySlots.size(); ++var8) {
            final Slot var9 = this.field_147002_h.inventorySlots.get(var8);
            this.func_146977_a(var9);
            if (this.func_146981_a(var9, p_73863_1_, p_73863_2_) && var9.func_111238_b()) {
                this.field_147006_u = var9;
                GL11.glDisable(2896);
                GL11.glDisable(2929);
                final int var10 = var9.xDisplayPosition;
                final int var11 = var9.yDisplayPosition;
                GL11.glColorMask(true, true, true, false);
                this.drawGradientRect(var10, var11, var10 + 16, var11 + 16, -2130706433, -2130706433);
                GL11.glColorMask(true, true, true, true);
                GL11.glEnable(2896);
                GL11.glEnable(2929);
            }
        }
        this.func_146979_b(p_73863_1_, p_73863_2_);
        final InventoryPlayer var12 = this.mc.thePlayer.inventory;
        ItemStack var13 = (this.field_147012_x == null) ? var12.getItemStack() : this.field_147012_x;
        if (var13 != null) {
            final byte var14 = 8;
            final int var11 = (this.field_147012_x == null) ? 8 : 16;
            String var15 = null;
            if (this.field_147012_x != null && this.field_147004_w) {
                var13 = var13.copy();
                var13.stackSize = MathHelper.ceiling_float_int(var13.stackSize / 2.0f);
            }
            else if (this.field_147007_t && this.field_147008_s.size() > 1) {
                var13 = var13.copy();
                var13.stackSize = this.field_146996_I;
                if (var13.stackSize == 0) {
                    var15 = "" + EnumChatFormatting.YELLOW + "0";
                }
            }
            this.func_146982_a(var13, p_73863_1_ - var4 - var14, p_73863_2_ - var5 - var11, var15);
        }
        if (this.field_146991_C != null) {
            float var16 = (Minecraft.getSystemTime() - this.field_146990_B) / 100.0f;
            if (var16 >= 1.0f) {
                var16 = 1.0f;
                this.field_146991_C = null;
            }
            final int var11 = this.field_146989_A.xDisplayPosition - this.field_147011_y;
            final int var17 = this.field_146989_A.yDisplayPosition - this.field_147010_z;
            final int var18 = this.field_147011_y + (int)(var11 * var16);
            final int var19 = this.field_147010_z + (int)(var17 * var16);
            this.func_146982_a(this.field_146991_C, var18, var19, null);
        }
        GL11.glPopMatrix();
        if (var12.getItemStack() == null && this.field_147006_u != null && this.field_147006_u.getHasStack()) {
            final ItemStack var20 = this.field_147006_u.getStack();
            this.func_146285_a(var20, p_73863_1_, p_73863_2_);
        }
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        RenderHelper.enableStandardItemLighting();
    }
    
    private void func_146982_a(final ItemStack p_146982_1_, final int p_146982_2_, final int p_146982_3_, final String p_146982_4_) {
        GL11.glTranslatef(0.0f, 0.0f, 32.0f);
        this.zLevel = 200.0f;
        GuiContainer.itemRender.zLevel = 200.0f;
        GuiContainer.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), p_146982_1_, p_146982_2_, p_146982_3_);
        GuiContainer.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), p_146982_1_, p_146982_2_, p_146982_3_ - ((this.field_147012_x == null) ? 0 : 8), p_146982_4_);
        this.zLevel = 0.0f;
        GuiContainer.itemRender.zLevel = 0.0f;
    }
    
    protected void func_146979_b(final int p_146979_1_, final int p_146979_2_) {
    }
    
    protected abstract void func_146976_a(final float p0, final int p1, final int p2);
    
    private void func_146977_a(final Slot p_146977_1_) {
        final int var2 = p_146977_1_.xDisplayPosition;
        final int var3 = p_146977_1_.yDisplayPosition;
        ItemStack var4 = p_146977_1_.getStack();
        boolean var5 = false;
        boolean var6 = p_146977_1_ == this.field_147005_v && this.field_147012_x != null && !this.field_147004_w;
        final ItemStack var7 = this.mc.thePlayer.inventory.getItemStack();
        String var8 = null;
        if (p_146977_1_ == this.field_147005_v && this.field_147012_x != null && this.field_147004_w && var4 != null) {
            final ItemStack copy;
            var4 = (copy = var4.copy());
            copy.stackSize /= 2;
        }
        else if (this.field_147007_t && this.field_147008_s.contains(p_146977_1_) && var7 != null) {
            if (this.field_147008_s.size() == 1) {
                return;
            }
            if (Container.func_94527_a(p_146977_1_, var7, true) && this.field_147002_h.canDragIntoSlot(p_146977_1_)) {
                var4 = var7.copy();
                var5 = true;
                Container.func_94525_a(this.field_147008_s, this.field_146987_F, var4, (p_146977_1_.getStack() == null) ? 0 : p_146977_1_.getStack().stackSize);
                if (var4.stackSize > var4.getMaxStackSize()) {
                    var8 = EnumChatFormatting.YELLOW + "" + var4.getMaxStackSize();
                    var4.stackSize = var4.getMaxStackSize();
                }
                if (var4.stackSize > p_146977_1_.getSlotStackLimit()) {
                    var8 = EnumChatFormatting.YELLOW + "" + p_146977_1_.getSlotStackLimit();
                    var4.stackSize = p_146977_1_.getSlotStackLimit();
                }
            }
            else {
                this.field_147008_s.remove(p_146977_1_);
                this.func_146980_g();
            }
        }
        this.zLevel = 100.0f;
        GuiContainer.itemRender.zLevel = 100.0f;
        if (var4 == null) {
            final IIcon var9 = p_146977_1_.getBackgroundIconIndex();
            if (var9 != null) {
                GL11.glDisable(2896);
                this.mc.getTextureManager().bindTexture(TextureMap.locationItemsTexture);
                this.drawTexturedModelRectFromIcon(var2, var3, var9, 16, 16);
                GL11.glEnable(2896);
                var6 = true;
            }
        }
        if (!var6) {
            if (var5) {
                Gui.drawRect(var2, var3, var2 + 16, var3 + 16, -2130706433);
            }
            GL11.glEnable(2929);
            GuiContainer.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var4, var2, var3);
            GuiContainer.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var4, var2, var3, var8);
        }
        GuiContainer.itemRender.zLevel = 0.0f;
        this.zLevel = 0.0f;
    }
    
    private void func_146980_g() {
        final ItemStack var1 = this.mc.thePlayer.inventory.getItemStack();
        if (var1 != null && this.field_147007_t) {
            this.field_146996_I = var1.stackSize;
            for (final Slot var3 : this.field_147008_s) {
                final ItemStack var4 = var1.copy();
                final int var5 = (var3.getStack() == null) ? 0 : var3.getStack().stackSize;
                Container.func_94525_a(this.field_147008_s, this.field_146987_F, var4, var5);
                if (var4.stackSize > var4.getMaxStackSize()) {
                    var4.stackSize = var4.getMaxStackSize();
                }
                if (var4.stackSize > var3.getSlotStackLimit()) {
                    var4.stackSize = var3.getSlotStackLimit();
                }
                this.field_146996_I -= var4.stackSize - var5;
            }
        }
    }
    
    private Slot func_146975_c(final int p_146975_1_, final int p_146975_2_) {
        for (int var3 = 0; var3 < this.field_147002_h.inventorySlots.size(); ++var3) {
            final Slot var4 = this.field_147002_h.inventorySlots.get(var3);
            if (this.func_146981_a(var4, p_146975_1_, p_146975_2_)) {
                return var4;
            }
        }
        return null;
    }
    
    @Override
    protected void mouseClicked(final int p_73864_1_, final int p_73864_2_, final int p_73864_3_) {
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        final boolean var4 = p_73864_3_ == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100;
        final Slot var5 = this.func_146975_c(p_73864_1_, p_73864_2_);
        final long var6 = Minecraft.getSystemTime();
        this.field_146993_M = (this.field_146998_K == var5 && var6 - this.field_146997_J < 250L && this.field_146992_L == p_73864_3_);
        this.field_146995_H = false;
        if (p_73864_3_ == 0 || p_73864_3_ == 1 || var4) {
            final int var7 = this.field_147003_i;
            final int var8 = this.field_147009_r;
            final boolean var9 = p_73864_1_ < var7 || p_73864_2_ < var8 || p_73864_1_ >= var7 + this.field_146999_f || p_73864_2_ >= var8 + this.field_147000_g;
            int var10 = -1;
            if (var5 != null) {
                var10 = var5.slotNumber;
            }
            if (var9) {
                var10 = -999;
            }
            if (this.mc.gameSettings.touchscreen && var9 && this.mc.thePlayer.inventory.getItemStack() == null) {
                this.mc.displayGuiScreen(null);
                return;
            }
            if (var10 != -1) {
                if (this.mc.gameSettings.touchscreen) {
                    if (var5 != null && var5.getHasStack()) {
                        this.field_147005_v = var5;
                        this.field_147012_x = null;
                        this.field_147004_w = (p_73864_3_ == 1);
                    }
                    else {
                        this.field_147005_v = null;
                    }
                }
                else if (!this.field_147007_t) {
                    if (this.mc.thePlayer.inventory.getItemStack() == null) {
                        if (p_73864_3_ == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
                            this.func_146984_a(var5, var10, p_73864_3_, 3);
                        }
                        else {
                            final boolean var11 = var10 != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
                            byte var12 = 0;
                            if (var11) {
                                this.field_146994_N = ((var5 != null && var5.getHasStack()) ? var5.getStack() : null);
                                var12 = 1;
                            }
                            else if (var10 == -999) {
                                var12 = 4;
                            }
                            this.func_146984_a(var5, var10, p_73864_3_, var12);
                        }
                        this.field_146995_H = true;
                    }
                    else {
                        this.field_147007_t = true;
                        this.field_146988_G = p_73864_3_;
                        this.field_147008_s.clear();
                        if (p_73864_3_ == 0) {
                            this.field_146987_F = 0;
                        }
                        else if (p_73864_3_ == 1) {
                            this.field_146987_F = 1;
                        }
                    }
                }
            }
        }
        this.field_146998_K = var5;
        this.field_146997_J = var6;
        this.field_146992_L = p_73864_3_;
    }
    
    @Override
    protected void mouseClickMove(final int p_146273_1_, final int p_146273_2_, final int p_146273_3_, final long p_146273_4_) {
        final Slot var6 = this.func_146975_c(p_146273_1_, p_146273_2_);
        final ItemStack var7 = this.mc.thePlayer.inventory.getItemStack();
        if (this.field_147005_v != null && this.mc.gameSettings.touchscreen) {
            if (p_146273_3_ == 0 || p_146273_3_ == 1) {
                if (this.field_147012_x == null) {
                    if (var6 != this.field_147005_v) {
                        this.field_147012_x = this.field_147005_v.getStack().copy();
                    }
                }
                else if (this.field_147012_x.stackSize > 1 && var6 != null && Container.func_94527_a(var6, this.field_147012_x, false)) {
                    final long var8 = Minecraft.getSystemTime();
                    if (this.field_146985_D == var6) {
                        if (var8 - this.field_146986_E > 500L) {
                            this.func_146984_a(this.field_147005_v, this.field_147005_v.slotNumber, 0, 0);
                            this.func_146984_a(var6, var6.slotNumber, 1, 0);
                            this.func_146984_a(this.field_147005_v, this.field_147005_v.slotNumber, 0, 0);
                            this.field_146986_E = var8 + 750L;
                            final ItemStack field_147012_x = this.field_147012_x;
                            --field_147012_x.stackSize;
                        }
                    }
                    else {
                        this.field_146985_D = var6;
                        this.field_146986_E = var8;
                    }
                }
            }
        }
        else if (this.field_147007_t && var6 != null && var7 != null && var7.stackSize > this.field_147008_s.size() && Container.func_94527_a(var6, var7, true) && var6.isItemValid(var7) && this.field_147002_h.canDragIntoSlot(var6)) {
            this.field_147008_s.add(var6);
            this.func_146980_g();
        }
    }
    
    @Override
    protected void mouseMovedOrUp(final int p_146286_1_, final int p_146286_2_, final int p_146286_3_) {
        final Slot var4 = this.func_146975_c(p_146286_1_, p_146286_2_);
        final int var5 = this.field_147003_i;
        final int var6 = this.field_147009_r;
        final boolean var7 = p_146286_1_ < var5 || p_146286_2_ < var6 || p_146286_1_ >= var5 + this.field_146999_f || p_146286_2_ >= var6 + this.field_147000_g;
        int var8 = -1;
        if (var4 != null) {
            var8 = var4.slotNumber;
        }
        if (var7) {
            var8 = -999;
        }
        if (this.field_146993_M && var4 != null && p_146286_3_ == 0 && this.field_147002_h.func_94530_a(null, var4)) {
            if (isShiftKeyDown()) {
                if (var4 != null && var4.inventory != null && this.field_146994_N != null) {
                    for (final Slot var10 : this.field_147002_h.inventorySlots) {
                        if (var10 != null && var10.canTakeStack(this.mc.thePlayer) && var10.getHasStack() && var10.inventory == var4.inventory && Container.func_94527_a(var10, this.field_146994_N, true)) {
                            this.func_146984_a(var10, var10.slotNumber, p_146286_3_, 1);
                        }
                    }
                }
            }
            else {
                this.func_146984_a(var4, var8, p_146286_3_, 6);
            }
            this.field_146993_M = false;
            this.field_146997_J = 0L;
        }
        else {
            if (this.field_147007_t && this.field_146988_G != p_146286_3_) {
                this.field_147007_t = false;
                this.field_147008_s.clear();
                this.field_146995_H = true;
                return;
            }
            if (this.field_146995_H) {
                this.field_146995_H = false;
                return;
            }
            if (this.field_147005_v != null && this.mc.gameSettings.touchscreen) {
                if (p_146286_3_ == 0 || p_146286_3_ == 1) {
                    if (this.field_147012_x == null && var4 != this.field_147005_v) {
                        this.field_147012_x = this.field_147005_v.getStack();
                    }
                    final boolean var11 = Container.func_94527_a(var4, this.field_147012_x, false);
                    if (var8 != -1 && this.field_147012_x != null && var11) {
                        this.func_146984_a(this.field_147005_v, this.field_147005_v.slotNumber, p_146286_3_, 0);
                        this.func_146984_a(var4, var8, 0, 0);
                        if (this.mc.thePlayer.inventory.getItemStack() != null) {
                            this.func_146984_a(this.field_147005_v, this.field_147005_v.slotNumber, p_146286_3_, 0);
                            this.field_147011_y = p_146286_1_ - var5;
                            this.field_147010_z = p_146286_2_ - var6;
                            this.field_146989_A = this.field_147005_v;
                            this.field_146991_C = this.field_147012_x;
                            this.field_146990_B = Minecraft.getSystemTime();
                        }
                        else {
                            this.field_146991_C = null;
                        }
                    }
                    else if (this.field_147012_x != null) {
                        this.field_147011_y = p_146286_1_ - var5;
                        this.field_147010_z = p_146286_2_ - var6;
                        this.field_146989_A = this.field_147005_v;
                        this.field_146991_C = this.field_147012_x;
                        this.field_146990_B = Minecraft.getSystemTime();
                    }
                    this.field_147012_x = null;
                    this.field_147005_v = null;
                }
            }
            else if (this.field_147007_t && !this.field_147008_s.isEmpty()) {
                this.func_146984_a(null, -999, Container.func_94534_d(0, this.field_146987_F), 5);
                for (final Slot var10 : this.field_147008_s) {
                    this.func_146984_a(var10, var10.slotNumber, Container.func_94534_d(1, this.field_146987_F), 5);
                }
                this.func_146984_a(null, -999, Container.func_94534_d(2, this.field_146987_F), 5);
            }
            else if (this.mc.thePlayer.inventory.getItemStack() != null) {
                if (p_146286_3_ == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
                    this.func_146984_a(var4, var8, p_146286_3_, 3);
                }
                else {
                    final boolean var11 = var8 != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
                    if (var11) {
                        this.field_146994_N = ((var4 != null && var4.getHasStack()) ? var4.getStack() : null);
                    }
                    this.func_146984_a(var4, var8, p_146286_3_, var11 ? 1 : 0);
                }
            }
        }
        if (this.mc.thePlayer.inventory.getItemStack() == null) {
            this.field_146997_J = 0L;
        }
        this.field_147007_t = false;
    }
    
    private boolean func_146981_a(final Slot p_146981_1_, final int p_146981_2_, final int p_146981_3_) {
        return this.func_146978_c(p_146981_1_.xDisplayPosition, p_146981_1_.yDisplayPosition, 16, 16, p_146981_2_, p_146981_3_);
    }
    
    protected boolean func_146978_c(final int p_146978_1_, final int p_146978_2_, final int p_146978_3_, final int p_146978_4_, int p_146978_5_, int p_146978_6_) {
        final int var7 = this.field_147003_i;
        final int var8 = this.field_147009_r;
        p_146978_5_ -= var7;
        p_146978_6_ -= var8;
        return p_146978_5_ >= p_146978_1_ - 1 && p_146978_5_ < p_146978_1_ + p_146978_3_ + 1 && p_146978_6_ >= p_146978_2_ - 1 && p_146978_6_ < p_146978_2_ + p_146978_4_ + 1;
    }
    
    protected void func_146984_a(final Slot p_146984_1_, int p_146984_2_, final int p_146984_3_, final int p_146984_4_) {
        if (p_146984_1_ != null) {
            p_146984_2_ = p_146984_1_.slotNumber;
        }
        this.mc.playerController.windowClick(this.field_147002_h.windowId, p_146984_2_, p_146984_3_, p_146984_4_, this.mc.thePlayer);
    }
    
    @Override
    protected void keyTyped(final char p_73869_1_, final int p_73869_2_) {
        if (p_73869_2_ == 1 || p_73869_2_ == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
            this.mc.thePlayer.closeScreen();
        }
        this.func_146983_a(p_73869_2_);
        if (this.field_147006_u != null && this.field_147006_u.getHasStack()) {
            if (p_73869_2_ == this.mc.gameSettings.keyBindPickBlock.getKeyCode()) {
                this.func_146984_a(this.field_147006_u, this.field_147006_u.slotNumber, 0, 3);
            }
            else if (p_73869_2_ == this.mc.gameSettings.keyBindDrop.getKeyCode()) {
                this.func_146984_a(this.field_147006_u, this.field_147006_u.slotNumber, GuiScreen.isCtrlKeyDown() ? 1 : 0, 4);
            }
        }
    }
    
    protected boolean func_146983_a(final int p_146983_1_) {
        if (this.mc.thePlayer.inventory.getItemStack() == null && this.field_147006_u != null) {
            for (int var2 = 0; var2 < 9; ++var2) {
                if (p_146983_1_ == this.mc.gameSettings.keyBindsHotbar[var2].getKeyCode()) {
                    this.func_146984_a(this.field_147006_u, this.field_147006_u.slotNumber, var2, 2);
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public void onGuiClosed() {
        if (this.mc.thePlayer != null) {
            this.field_147002_h.onContainerClosed(this.mc.thePlayer);
        }
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        if (!this.mc.thePlayer.isEntityAlive() || this.mc.thePlayer.isDead) {
            this.mc.thePlayer.closeScreen();
        }
    }
    
    static {
        field_147001_a = new ResourceLocation("textures/gui/container/inventory.png");
    }
}
