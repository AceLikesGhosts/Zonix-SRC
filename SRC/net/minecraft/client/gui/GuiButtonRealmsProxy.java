package net.minecraft.client.gui;

import net.minecraft.realms.*;
import net.minecraft.client.*;

public class GuiButtonRealmsProxy extends GuiButton
{
    private RealmsButton field_154318_o;
    private static final String __OBFID = "CL_00001848";
    
    public GuiButtonRealmsProxy(final RealmsButton p_i46321_1_, final int p_i46321_2_, final int p_i46321_3_, final int p_i46321_4_, final String p_i46321_5_) {
        super(p_i46321_2_, p_i46321_3_, p_i46321_4_, p_i46321_5_);
        this.field_154318_o = p_i46321_1_;
    }
    
    public GuiButtonRealmsProxy(final RealmsButton p_i1090_1_, final int p_i1090_2_, final int p_i1090_3_, final int p_i1090_4_, final String p_i1090_5_, final int p_i1090_6_, final int p_i1090_7_) {
        super(p_i1090_2_, p_i1090_3_, p_i1090_4_, p_i1090_6_, p_i1090_7_, p_i1090_5_);
        this.field_154318_o = p_i1090_1_;
    }
    
    public int func_154314_d() {
        return super.id;
    }
    
    public boolean func_154315_e() {
        return super.enabled;
    }
    
    public void func_154313_b(final boolean p_154313_1_) {
        super.enabled = p_154313_1_;
    }
    
    public void func_154311_a(final String p_154311_1_) {
        super.displayString = p_154311_1_;
    }
    
    @Override
    public int func_146117_b() {
        return super.func_146117_b();
    }
    
    public int func_154316_f() {
        return super.y;
    }
    
    @Override
    public boolean mousePressed(final Minecraft p_146116_1_, final int p_146116_2_, final int p_146116_3_) {
        if (super.mousePressed(p_146116_1_, p_146116_2_, p_146116_3_)) {
            this.field_154318_o.clicked(p_146116_2_, p_146116_3_);
        }
        return super.mousePressed(p_146116_1_, p_146116_2_, p_146116_3_);
    }
    
    @Override
    public void mouseReleased(final int p_146118_1_, final int p_146118_2_) {
        this.field_154318_o.released(p_146118_1_, p_146118_2_);
    }
    
    public void mouseDragged(final Minecraft p_146119_1_, final int p_146119_2_, final int p_146119_3_) {
        this.field_154318_o.renderBg(p_146119_2_, p_146119_3_);
    }
    
    public RealmsButton func_154317_g() {
        return this.field_154318_o;
    }
    
    @Override
    public int getHoverState(final boolean p_146114_1_) {
        return this.field_154318_o.getYImage(p_146114_1_);
    }
    
    public int func_154312_c(final boolean p_154312_1_) {
        return super.getHoverState(p_154312_1_);
    }
}
