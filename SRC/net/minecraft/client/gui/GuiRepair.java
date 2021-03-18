package net.minecraft.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.resources.*;
import org.apache.commons.io.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.inventory.*;
import java.util.*;
import net.minecraft.item.*;

public class GuiRepair extends GuiContainer implements ICrafting
{
    private static final ResourceLocation field_147093_u;
    private ContainerRepair field_147092_v;
    private GuiTextField field_147091_w;
    private InventoryPlayer field_147094_x;
    private static final String __OBFID = "CL_00000738";
    
    public GuiRepair(final InventoryPlayer p_i46381_1_, final World p_i46381_2_, final int p_i46381_3_, final int p_i46381_4_, final int p_i46381_5_) {
        super(new ContainerRepair(p_i46381_1_, p_i46381_2_, p_i46381_3_, p_i46381_4_, p_i46381_5_, Minecraft.getMinecraft().thePlayer));
        this.field_147094_x = p_i46381_1_;
        this.field_147092_v = (ContainerRepair)this.field_147002_h;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        Keyboard.enableRepeatEvents(true);
        final int var1 = (this.width - this.field_146999_f) / 2;
        final int var2 = (this.height - this.field_147000_g) / 2;
        (this.field_147091_w = new GuiTextField(this.fontRendererObj, var1 + 62, var2 + 24, 103, 12)).func_146193_g(-1);
        this.field_147091_w.func_146204_h(-1);
        this.field_147091_w.func_146185_a(false);
        this.field_147091_w.func_146203_f(40);
        this.field_147002_h.removeCraftingFromCrafters(this);
        this.field_147002_h.addCraftingToCrafters(this);
    }
    
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
        this.field_147002_h.removeCraftingFromCrafters(this);
    }
    
    @Override
    protected void func_146979_b(final int p_146979_1_, final int p_146979_2_) {
        GL11.glDisable(2896);
        GL11.glDisable(3042);
        this.fontRendererObj.drawString(I18n.format("container.repair", new Object[0]), 60, 6, 4210752);
        if (this.field_147092_v.maximumCost > 0) {
            int var3 = 8453920;
            boolean var4 = true;
            String var5 = I18n.format("container.repair.cost", this.field_147092_v.maximumCost);
            if (this.field_147092_v.maximumCost >= 40 && !this.mc.thePlayer.capabilities.isCreativeMode) {
                var5 = I18n.format("container.repair.expensive", new Object[0]);
                var3 = 16736352;
            }
            else if (!this.field_147092_v.getSlot(2).getHasStack()) {
                var4 = false;
            }
            else if (!this.field_147092_v.getSlot(2).canTakeStack(this.field_147094_x.player)) {
                var3 = 16736352;
            }
            if (var4) {
                final int var6 = 0xFF000000 | (var3 & 0xFCFCFC) >> 2 | (var3 & 0xFF000000);
                final int var7 = this.field_146999_f - 8 - this.fontRendererObj.getStringWidth(var5);
                final byte var8 = 67;
                if (this.fontRendererObj.getUnicodeFlag()) {
                    Gui.drawRect(var7 - 3, var8 - 2, this.field_146999_f - 7, var8 + 10, -16777216);
                    Gui.drawRect(var7 - 2, var8 - 1, this.field_146999_f - 8, var8 + 9, -12895429);
                }
                else {
                    this.fontRendererObj.drawString(var5, var7, var8 + 1, var6);
                    this.fontRendererObj.drawString(var5, var7 + 1, var8, var6);
                    this.fontRendererObj.drawString(var5, var7 + 1, var8 + 1, var6);
                }
                this.fontRendererObj.drawString(var5, var7, var8, var3);
            }
        }
        GL11.glEnable(2896);
    }
    
    @Override
    protected void keyTyped(final char p_73869_1_, final int p_73869_2_) {
        if (this.field_147091_w.textboxKeyTyped(p_73869_1_, p_73869_2_)) {
            this.func_147090_g();
        }
        else {
            super.keyTyped(p_73869_1_, p_73869_2_);
        }
    }
    
    private void func_147090_g() {
        String var1 = this.field_147091_w.getText();
        final Slot var2 = this.field_147092_v.getSlot(0);
        if (var2 != null && var2.getHasStack() && !var2.getStack().hasDisplayName() && var1.equals(var2.getStack().getDisplayName())) {
            var1 = "";
        }
        this.field_147092_v.updateItemName(var1);
        this.mc.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("MC|ItemName", var1.getBytes(Charsets.UTF_8)));
    }
    
    @Override
    protected void mouseClicked(final int p_73864_1_, final int p_73864_2_, final int p_73864_3_) {
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        this.field_147091_w.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
    }
    
    @Override
    public void drawScreen(final int p_73863_1_, final int p_73863_2_, final float p_73863_3_) {
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
        GL11.glDisable(2896);
        GL11.glDisable(3042);
        this.field_147091_w.drawTextBox();
    }
    
    @Override
    protected void func_146976_a(final float p_146976_1_, final int p_146976_2_, final int p_146976_3_) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiRepair.field_147093_u);
        final int var4 = (this.width - this.field_146999_f) / 2;
        final int var5 = (this.height - this.field_147000_g) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147000_g);
        this.drawTexturedModalRect(var4 + 59, var5 + 20, 0, this.field_147000_g + (this.field_147092_v.getSlot(0).getHasStack() ? 0 : 16), 110, 16);
        if ((this.field_147092_v.getSlot(0).getHasStack() || this.field_147092_v.getSlot(1).getHasStack()) && !this.field_147092_v.getSlot(2).getHasStack()) {
            this.drawTexturedModalRect(var4 + 99, var5 + 45, this.field_146999_f, 0, 28, 21);
        }
    }
    
    @Override
    public void sendContainerAndContentsToPlayer(final Container p_71110_1_, final List p_71110_2_) {
        this.sendSlotContents(p_71110_1_, 0, p_71110_1_.getSlot(0).getStack());
    }
    
    @Override
    public void sendSlotContents(final Container p_71111_1_, final int p_71111_2_, final ItemStack p_71111_3_) {
        if (p_71111_2_ == 0) {
            this.field_147091_w.setText((p_71111_3_ == null) ? "" : p_71111_3_.getDisplayName());
            this.field_147091_w.func_146184_c(p_71111_3_ != null);
            if (p_71111_3_ != null) {
                this.func_147090_g();
            }
        }
    }
    
    @Override
    public void sendProgressBarUpdate(final Container p_71112_1_, final int p_71112_2_, final int p_71112_3_) {
    }
    
    static {
        field_147093_u = new ResourceLocation("textures/gui/container/anvil.png");
    }
}
