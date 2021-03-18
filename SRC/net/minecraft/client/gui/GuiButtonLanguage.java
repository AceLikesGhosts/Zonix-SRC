package net.minecraft.client.gui;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;

public class GuiButtonLanguage extends GuiButton
{
    private static final String __OBFID = "CL_00000672";
    
    public GuiButtonLanguage(final int p_i1041_1_, final int p_i1041_2_, final int p_i1041_3_) {
        super(p_i1041_1_, p_i1041_2_, p_i1041_3_, 20, 20, "");
    }
    
    @Override
    public void drawButton(final Minecraft p_146112_1_, final int p_146112_2_, final int p_146112_3_) {
        if (this.field_146125_m) {
            p_146112_1_.getTextureManager().bindTexture(GuiButton.field_146122_a);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            final boolean var4 = p_146112_2_ >= this.x && p_146112_3_ >= this.y && p_146112_2_ < this.x + this.width && p_146112_3_ < this.y + this.height;
            int var5 = 106;
            if (var4) {
                var5 += this.height;
            }
            this.drawTexturedModalRect(this.x, this.y, 0, var5, this.width, this.height);
        }
    }
}
