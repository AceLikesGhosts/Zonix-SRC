package net.minecraft.client.gui;

import org.lwjgl.input.*;
import net.minecraft.client.resources.*;
import net.minecraft.world.storage.*;

public class GuiRenameWorld extends GuiScreen
{
    private GuiScreen field_146585_a;
    private GuiTextField field_146583_f;
    private final String field_146584_g;
    private static final String __OBFID = "CL_00000709";
    
    public GuiRenameWorld(final GuiScreen p_i46317_1_, final String p_i46317_2_) {
        this.field_146585_a = p_i46317_1_;
        this.field_146584_g = p_i46317_2_;
    }
    
    @Override
    public void updateScreen() {
        this.field_146583_f.updateCursorCounter();
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, I18n.format("selectWorld.renameButton", new Object[0])));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
        final ISaveFormat var1 = this.mc.getSaveLoader();
        final WorldInfo var2 = var1.getWorldInfo(this.field_146584_g);
        final String var3 = var2.getWorldName();
        (this.field_146583_f = new GuiTextField(this.fontRendererObj, this.width / 2 - 100, 60, 200, 20)).setFocused(true);
        this.field_146583_f.setText(var3);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void actionPerformed(final GuiButton p_146284_1_) {
        if (p_146284_1_.enabled) {
            if (p_146284_1_.id == 1) {
                this.mc.displayGuiScreen(this.field_146585_a);
            }
            else if (p_146284_1_.id == 0) {
                final ISaveFormat var2 = this.mc.getSaveLoader();
                var2.renameWorld(this.field_146584_g, this.field_146583_f.getText().trim());
                this.mc.displayGuiScreen(this.field_146585_a);
            }
        }
    }
    
    @Override
    protected void keyTyped(final char p_73869_1_, final int p_73869_2_) {
        this.field_146583_f.textboxKeyTyped(p_73869_1_, p_73869_2_);
        this.buttonList.get(0).enabled = (this.field_146583_f.getText().trim().length() > 0);
        if (p_73869_2_ == 28 || p_73869_2_ == 156) {
            this.actionPerformed(this.buttonList.get(0));
        }
    }
    
    @Override
    protected void mouseClicked(final int p_73864_1_, final int p_73864_2_, final int p_73864_3_) {
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        this.field_146583_f.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
    }
    
    @Override
    public void drawScreen(final int p_73863_1_, final int p_73863_2_, final float p_73863_3_) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("selectWorld.renameTitle", new Object[0]), this.width / 2, 20, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("selectWorld.enterName", new Object[0]), this.width / 2 - 100, 47, 10526880);
        this.field_146583_f.drawTextBox();
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
}
