package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.*;

public class GuiControls extends GuiScreen
{
    private static final GameSettings.Options[] field_146492_g;
    private GuiScreen field_146496_h;
    protected String field_146495_a;
    private GameSettings field_146497_i;
    public KeyBinding field_146491_f;
    public long field_152177_g;
    private GuiKeyBindingList field_146494_r;
    private GuiButton field_146493_s;
    private static final String __OBFID = "CL_00000736";
    
    public GuiControls(final GuiScreen p_i1027_1_, final GameSettings p_i1027_2_) {
        this.field_146495_a = "Controls";
        this.field_146491_f = null;
        this.field_146496_h = p_i1027_1_;
        this.field_146497_i = p_i1027_2_;
    }
    
    @Override
    public void initGui() {
        this.field_146494_r = new GuiKeyBindingList(this, this.mc);
        this.buttonList.add(new GuiButton(200, this.width / 2 - 155, this.height - 29, 150, 20, I18n.format("gui.done", new Object[0])));
        this.buttonList.add(this.field_146493_s = new GuiButton(201, this.width / 2 - 155 + 160, this.height - 29, 150, 20, I18n.format("controls.resetAll", new Object[0])));
        this.field_146495_a = I18n.format("controls.title", new Object[0]);
        int var1 = 0;
        for (final GameSettings.Options var5 : GuiControls.field_146492_g) {
            if (var5.getEnumFloat()) {
                this.buttonList.add(new GuiOptionSlider(var5.returnEnumOrdinal(), this.width / 2 - 155 + var1 % 2 * 160, 18 + 24 * (var1 >> 1), var5));
            }
            else {
                this.buttonList.add(new GuiOptionButton(var5.returnEnumOrdinal(), this.width / 2 - 155 + var1 % 2 * 160, 18 + 24 * (var1 >> 1), var5, this.field_146497_i.getKeyBinding(var5)));
            }
            ++var1;
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton p_146284_1_) {
        if (p_146284_1_.id == 200) {
            this.mc.displayGuiScreen(this.field_146496_h);
        }
        else if (p_146284_1_.id == 201) {
            for (final KeyBinding var5 : this.mc.gameSettings.keyBindings) {
                var5.setKeyCode(var5.getKeyCodeDefault());
            }
            KeyBinding.resetKeyBindingArrayAndHash();
        }
        else if (p_146284_1_.id < 100 && p_146284_1_ instanceof GuiOptionButton) {
            this.field_146497_i.setOptionValue(((GuiOptionButton)p_146284_1_).func_146136_c(), 1);
            p_146284_1_.displayString = this.field_146497_i.getKeyBinding(GameSettings.Options.getEnumOptions(p_146284_1_.id));
        }
    }
    
    @Override
    protected void mouseClicked(final int p_73864_1_, final int p_73864_2_, final int p_73864_3_) {
        if (this.field_146491_f != null) {
            this.field_146497_i.setKeyCodeSave(this.field_146491_f, -100 + p_73864_3_);
            this.field_146491_f = null;
            KeyBinding.resetKeyBindingArrayAndHash();
        }
        else if (p_73864_3_ != 0 || !this.field_146494_r.func_148179_a(p_73864_1_, p_73864_2_, p_73864_3_)) {
            super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        }
    }
    
    @Override
    protected void mouseMovedOrUp(final int p_146286_1_, final int p_146286_2_, final int p_146286_3_) {
        if (p_146286_3_ != 0 || !this.field_146494_r.func_148181_b(p_146286_1_, p_146286_2_, p_146286_3_)) {
            super.mouseMovedOrUp(p_146286_1_, p_146286_2_, p_146286_3_);
        }
    }
    
    @Override
    protected void keyTyped(final char p_73869_1_, final int p_73869_2_) {
        if (this.field_146491_f != null) {
            if (p_73869_2_ == 1) {
                this.field_146497_i.setKeyCodeSave(this.field_146491_f, 0);
            }
            else {
                this.field_146497_i.setKeyCodeSave(this.field_146491_f, p_73869_2_);
            }
            this.field_146491_f = null;
            this.field_152177_g = Minecraft.getSystemTime();
            KeyBinding.resetKeyBindingArrayAndHash();
        }
        else {
            super.keyTyped(p_73869_1_, p_73869_2_);
        }
    }
    
    @Override
    public void drawScreen(final int p_73863_1_, final int p_73863_2_, final float p_73863_3_) {
        this.drawDefaultBackground();
        this.field_146494_r.func_148128_a(p_73863_1_, p_73863_2_, p_73863_3_);
        this.drawCenteredString(this.fontRendererObj, this.field_146495_a, this.width / 2, 8, 16777215);
        boolean var4 = true;
        for (final KeyBinding var8 : this.field_146497_i.keyBindings) {
            if (var8.getKeyCode() != var8.getKeyCodeDefault()) {
                var4 = false;
                break;
            }
        }
        this.field_146493_s.enabled = !var4;
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
    
    static {
        field_146492_g = new GameSettings.Options[] { GameSettings.Options.INVERT_MOUSE, GameSettings.Options.SENSITIVITY, GameSettings.Options.TOUCHSCREEN };
    }
}
