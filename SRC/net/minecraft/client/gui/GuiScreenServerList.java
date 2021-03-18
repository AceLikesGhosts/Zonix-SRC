package net.minecraft.client.gui;

import net.minecraft.client.multiplayer.*;
import org.lwjgl.input.*;
import net.minecraft.client.resources.*;

public class GuiScreenServerList extends GuiScreen
{
    private final GuiScreen field_146303_a;
    private final ServerData field_146301_f;
    private GuiTextField field_146302_g;
    private static final String __OBFID = "CL_00000692";
    
    public GuiScreenServerList(final GuiScreen p_i1031_1_, final ServerData p_i1031_2_) {
        this.field_146303_a = p_i1031_1_;
        this.field_146301_f = p_i1031_2_;
    }
    
    @Override
    public void updateScreen() {
        this.field_146302_g.updateCursorCounter();
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, I18n.format("selectServer.select", new Object[0])));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
        (this.field_146302_g = new GuiTextField(this.fontRendererObj, this.width / 2 - 100, 116, 200, 20)).func_146203_f(128);
        this.field_146302_g.setFocused(true);
        this.field_146302_g.setText(this.mc.gameSettings.lastServer);
        this.buttonList.get(0).enabled = (this.field_146302_g.getText().length() > 0 && this.field_146302_g.getText().split(":").length > 0);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        this.mc.gameSettings.lastServer = this.field_146302_g.getText();
        this.mc.gameSettings.saveOptions();
    }
    
    @Override
    protected void actionPerformed(final GuiButton p_146284_1_) {
        if (p_146284_1_.enabled) {
            if (p_146284_1_.id == 1) {
                this.field_146303_a.confirmClicked(false, 0);
            }
            else if (p_146284_1_.id == 0) {
                this.field_146301_f.serverIP = this.field_146302_g.getText();
                this.field_146303_a.confirmClicked(true, 0);
            }
        }
    }
    
    @Override
    protected void keyTyped(final char p_73869_1_, final int p_73869_2_) {
        if (this.field_146302_g.textboxKeyTyped(p_73869_1_, p_73869_2_)) {
            this.buttonList.get(0).enabled = (this.field_146302_g.getText().length() > 0 && this.field_146302_g.getText().split(":").length > 0);
        }
        else if (p_73869_2_ == 28 || p_73869_2_ == 156) {
            this.actionPerformed(this.buttonList.get(0));
        }
    }
    
    @Override
    protected void mouseClicked(final int p_73864_1_, final int p_73864_2_, final int p_73864_3_) {
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        this.field_146302_g.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
    }
    
    @Override
    public void drawScreen(final int p_73863_1_, final int p_73863_2_, final float p_73863_3_) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("selectServer.direct", new Object[0]), this.width / 2, 20, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("addServer.enterIp", new Object[0]), this.width / 2 - 100, 100, 10526880);
        this.field_146302_g.drawTextBox();
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
}
