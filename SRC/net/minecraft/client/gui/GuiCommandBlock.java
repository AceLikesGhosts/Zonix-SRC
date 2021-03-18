package net.minecraft.client.gui;

import net.minecraft.command.server.*;
import org.lwjgl.input.*;
import net.minecraft.client.resources.*;
import io.netty.buffer.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import org.apache.logging.log4j.*;

public class GuiCommandBlock extends GuiScreen
{
    private static final Logger field_146488_a;
    private GuiTextField field_146485_f;
    private GuiTextField field_146486_g;
    private final CommandBlockLogic field_146489_h;
    private GuiButton field_146490_i;
    private GuiButton field_146487_r;
    private static final String __OBFID = "CL_00000748";
    
    public GuiCommandBlock(final CommandBlockLogic p_i45032_1_) {
        this.field_146489_h = p_i45032_1_;
    }
    
    @Override
    public void updateScreen() {
        this.field_146485_f.updateCursorCounter();
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(this.field_146490_i = new GuiButton(0, this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.done", new Object[0])));
        this.buttonList.add(this.field_146487_r = new GuiButton(1, this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel", new Object[0])));
        (this.field_146485_f = new GuiTextField(this.fontRendererObj, this.width / 2 - 150, 50, 300, 20)).func_146203_f(32767);
        this.field_146485_f.setFocused(true);
        this.field_146485_f.setText(this.field_146489_h.func_145753_i());
        (this.field_146486_g = new GuiTextField(this.fontRendererObj, this.width / 2 - 150, 135, 300, 20)).func_146203_f(32767);
        this.field_146486_g.func_146184_c(false);
        this.field_146486_g.setText(this.field_146489_h.func_145753_i());
        if (this.field_146489_h.func_145749_h() != null) {
            this.field_146486_g.setText(this.field_146489_h.func_145749_h().getUnformattedText());
        }
        this.field_146490_i.enabled = (this.field_146485_f.getText().trim().length() > 0);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void actionPerformed(final GuiButton p_146284_1_) {
        if (p_146284_1_.enabled) {
            if (p_146284_1_.id == 1) {
                this.mc.displayGuiScreen(null);
            }
            else if (p_146284_1_.id == 0) {
                final PacketBuffer var2 = new PacketBuffer(Unpooled.buffer());
                try {
                    var2.writeByte(this.field_146489_h.func_145751_f());
                    this.field_146489_h.func_145757_a(var2);
                    var2.writeStringToBuffer(this.field_146485_f.getText());
                    this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload("MC|AdvCdm", var2));
                }
                catch (Exception var3) {
                    GuiCommandBlock.field_146488_a.error("Couldn't send command block info", (Throwable)var3);
                }
                finally {
                    var2.release();
                }
                this.mc.displayGuiScreen(null);
            }
        }
    }
    
    @Override
    protected void keyTyped(final char p_73869_1_, final int p_73869_2_) {
        this.field_146485_f.textboxKeyTyped(p_73869_1_, p_73869_2_);
        this.field_146486_g.textboxKeyTyped(p_73869_1_, p_73869_2_);
        this.field_146490_i.enabled = (this.field_146485_f.getText().trim().length() > 0);
        if (p_73869_2_ != 28 && p_73869_2_ != 156) {
            if (p_73869_2_ == 1) {
                this.actionPerformed(this.field_146487_r);
            }
        }
        else {
            this.actionPerformed(this.field_146490_i);
        }
    }
    
    @Override
    protected void mouseClicked(final int p_73864_1_, final int p_73864_2_, final int p_73864_3_) {
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        this.field_146485_f.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        this.field_146486_g.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
    }
    
    @Override
    public void drawScreen(final int p_73863_1_, final int p_73863_2_, final float p_73863_3_) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("advMode.setCommand", new Object[0]), this.width / 2, 20, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("advMode.command", new Object[0]), this.width / 2 - 150, 37, 10526880);
        this.field_146485_f.drawTextBox();
        final byte var4 = 75;
        final byte var5 = 0;
        final FontRenderer var6 = this.fontRendererObj;
        final String var7 = I18n.format("advMode.nearestPlayer", new Object[0]);
        final int var8 = this.width / 2 - 150;
        int var9 = var5 + 1;
        this.drawString(var6, var7, var8, var4 + var5 * this.fontRendererObj.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRendererObj, I18n.format("advMode.randomPlayer", new Object[0]), this.width / 2 - 150, var4 + var9++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRendererObj, I18n.format("advMode.allPlayers", new Object[0]), this.width / 2 - 150, var4 + var9++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
        if (this.field_146486_g.getText().length() > 0) {
            final int var10 = var4 + var9 * this.fontRendererObj.FONT_HEIGHT + 20;
            this.drawString(this.fontRendererObj, I18n.format("advMode.previousOutput", new Object[0]), this.width / 2 - 150, var10, 10526880);
            this.field_146486_g.drawTextBox();
        }
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
    
    static {
        field_146488_a = LogManager.getLogger();
    }
}
