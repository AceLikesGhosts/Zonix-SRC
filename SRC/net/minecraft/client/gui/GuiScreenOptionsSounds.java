package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;

public class GuiScreenOptionsSounds extends GuiScreen
{
    private final GuiScreen field_146505_f;
    private final GameSettings field_146506_g;
    protected String field_146507_a;
    private String field_146508_h;
    private static final String __OBFID = "CL_00000716";
    
    public GuiScreenOptionsSounds(final GuiScreen p_i45025_1_, final GameSettings p_i45025_2_) {
        this.field_146507_a = "Options";
        this.field_146505_f = p_i45025_1_;
        this.field_146506_g = p_i45025_2_;
    }
    
    @Override
    public void initGui() {
        final byte var1 = 0;
        this.field_146507_a = I18n.format("options.sounds.title", new Object[0]);
        this.field_146508_h = I18n.format("options.off", new Object[0]);
        this.buttonList.add(new Button(SoundCategory.MASTER.getCategoryId(), this.width / 2 - 155 + var1 % 2 * 160, this.height / 6 - 12 + 24 * (var1 >> 1), SoundCategory.MASTER, true));
        int var2 = var1 + 2;
        for (final SoundCategory var6 : SoundCategory.values()) {
            if (var6 != SoundCategory.MASTER) {
                this.buttonList.add(new Button(var6.getCategoryId(), this.width / 2 - 155 + var2 % 2 * 160, this.height / 6 - 12 + 24 * (var2 >> 1), var6, false));
                ++var2;
            }
        }
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton p_146284_1_) {
        if (p_146284_1_.enabled && p_146284_1_.id == 200) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(this.field_146505_f);
        }
    }
    
    @Override
    public void drawScreen(final int p_73863_1_, final int p_73863_2_, final float p_73863_3_) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.field_146507_a, this.width / 2, 15, 16777215);
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
    
    protected String func_146504_a(final SoundCategory p_146504_1_) {
        final float var2 = this.field_146506_g.getSoundLevel(p_146504_1_);
        return (var2 == 0.0f) ? this.field_146508_h : ((int)(var2 * 100.0f) + "%");
    }
    
    class Button extends GuiButton
    {
        private final SoundCategory field_146153_r;
        private final String field_146152_s;
        public float field_146156_o;
        public boolean field_146155_p;
        private static final String __OBFID = "CL_00000717";
        
        public Button(final int p_i45024_2_, final int p_i45024_3_, final int p_i45024_4_, final SoundCategory p_i45024_5_, final boolean p_i45024_6_) {
            super(p_i45024_2_, p_i45024_3_, p_i45024_4_, p_i45024_6_ ? 310 : 150, 20, "");
            this.field_146156_o = 1.0f;
            this.field_146153_r = p_i45024_5_;
            this.field_146152_s = I18n.format("soundCategory." + p_i45024_5_.getCategoryName(), new Object[0]);
            this.displayString = this.field_146152_s + ": " + GuiScreenOptionsSounds.this.func_146504_a(p_i45024_5_);
            this.field_146156_o = GuiScreenOptionsSounds.this.field_146506_g.getSoundLevel(p_i45024_5_);
        }
        
        @Override
        public int getHoverState(final boolean p_146114_1_) {
            return 0;
        }
        
        @Override
        protected void mouseDragged(final Minecraft p_146119_1_, final int p_146119_2_, final int p_146119_3_) {
            if (this.field_146125_m) {
                if (this.field_146155_p) {
                    this.field_146156_o = (p_146119_2_ - (this.x + 4)) / (float)(this.width - 8);
                    if (this.field_146156_o < 0.0f) {
                        this.field_146156_o = 0.0f;
                    }
                    if (this.field_146156_o > 1.0f) {
                        this.field_146156_o = 1.0f;
                    }
                    p_146119_1_.gameSettings.setSoundLevel(this.field_146153_r, this.field_146156_o);
                    p_146119_1_.gameSettings.saveOptions();
                    this.displayString = this.field_146152_s + ": " + GuiScreenOptionsSounds.this.func_146504_a(this.field_146153_r);
                }
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                this.drawTexturedModalRect(this.x + (int)(this.field_146156_o * (this.width - 8)), this.y, 0, 66, 4, 20);
                this.drawTexturedModalRect(this.x + (int)(this.field_146156_o * (this.width - 8)) + 4, this.y, 196, 66, 4, 20);
            }
        }
        
        @Override
        public boolean mousePressed(final Minecraft p_146116_1_, final int p_146116_2_, final int p_146116_3_) {
            if (super.mousePressed(p_146116_1_, p_146116_2_, p_146116_3_)) {
                this.field_146156_o = (p_146116_2_ - (this.x + 4)) / (float)(this.width - 8);
                if (this.field_146156_o < 0.0f) {
                    this.field_146156_o = 0.0f;
                }
                if (this.field_146156_o > 1.0f) {
                    this.field_146156_o = 1.0f;
                }
                p_146116_1_.gameSettings.setSoundLevel(this.field_146153_r, this.field_146156_o);
                p_146116_1_.gameSettings.saveOptions();
                this.displayString = this.field_146152_s + ": " + GuiScreenOptionsSounds.this.func_146504_a(this.field_146153_r);
                return this.field_146155_p = true;
            }
            return false;
        }
        
        @Override
        public void func_146113_a(final SoundHandler p_146113_1_) {
        }
        
        @Override
        public void mouseReleased(final int p_146118_1_, final int p_146118_2_) {
            if (this.field_146155_p) {
                if (this.field_146153_r != SoundCategory.MASTER) {
                    GuiScreenOptionsSounds.this.field_146506_g.getSoundLevel(this.field_146153_r);
                }
                GuiScreenOptionsSounds.this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            }
            this.field_146155_p = false;
        }
    }
}
