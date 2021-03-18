package com.thevoxelbox.voxelmap.gui.overridden;

import net.minecraft.client.gui.*;
import com.thevoxelbox.voxelmap.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;

public class GuiOptionSliderMinimap extends GuiButton
{
    public float sliderValue;
    public boolean dragging;
    private MapSettingsManager options;
    private EnumOptionsMinimap idFloat;
    
    public GuiOptionSliderMinimap(final int par1, final int par2, final int par3, final EnumOptionsMinimap par4EnumOptions, final float par6, final MapSettingsManager options) {
        super(par1, par2, par3, 150, 20, "");
        this.sliderValue = 1.0f;
        this.dragging = false;
        this.idFloat = null;
        this.options = options;
        this.idFloat = par4EnumOptions;
        this.sliderValue = par6;
        this.displayString = this.options.getKeyText(par4EnumOptions);
    }
    
    @Override
    public int getHoverState(final boolean par1) {
        return 0;
    }
    
    @Override
    protected void mouseDragged(final Minecraft par1Minecraft, final int par2, final int par3) {
        if (this.field_146125_m) {
            if (this.dragging) {
                this.sliderValue = (float)((par2 - (this.x + 4)) / (this.width - 8));
                if (this.sliderValue < 0.0f) {
                    this.sliderValue = 0.0f;
                }
                if (this.sliderValue > 1.0f) {
                    this.sliderValue = 1.0f;
                }
                this.options.setOptionFloatValue(this.idFloat, this.sliderValue);
                this.displayString = this.options.getKeyText(this.idFloat);
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(this.x + (int)(this.sliderValue * (this.width - 8)), this.y, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.x + (int)(this.sliderValue * (this.width - 8)) + 4, this.y, 196, 66, 4, 20);
        }
    }
    
    @Override
    public boolean mousePressed(final Minecraft par1Minecraft, final int par2, final int par3) {
        if (super.mousePressed(par1Minecraft, par2, par3)) {
            this.sliderValue = (float)((par2 - (this.x + 4)) / (this.width - 8));
            if (this.sliderValue < 0.0f) {
                this.sliderValue = 0.0f;
            }
            if (this.sliderValue > 1.0f) {
                this.sliderValue = 1.0f;
            }
            this.options.setOptionFloatValue(this.idFloat, this.sliderValue);
            this.displayString = this.options.getKeyText(this.idFloat);
            return this.dragging = true;
        }
        return false;
    }
    
    @Override
    public void mouseReleased(final int par1, final int par2) {
        this.dragging = false;
    }
}
