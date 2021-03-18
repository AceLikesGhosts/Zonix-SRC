package net.minecraft.client.gui;

import net.minecraft.realms.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;

public class GuiSlotRealmsProxy extends GuiSlot
{
    private final RealmsScrolledSelectionList field_154340_k;
    private static final String __OBFID = "CL_00001846";
    
    public GuiSlotRealmsProxy(final RealmsScrolledSelectionList p_i1085_1_, final int p_i1085_2_, final int p_i1085_3_, final int p_i1085_4_, final int p_i1085_5_, final int p_i1085_6_) {
        super(Minecraft.getMinecraft(), p_i1085_2_, p_i1085_3_, p_i1085_4_, p_i1085_5_, p_i1085_6_);
        this.field_154340_k = p_i1085_1_;
    }
    
    @Override
    protected int getSize() {
        return this.field_154340_k.getItemCount();
    }
    
    @Override
    protected void elementClicked(final int p_148144_1_, final boolean p_148144_2_, final int p_148144_3_, final int p_148144_4_) {
        this.field_154340_k.selectItem(p_148144_1_, p_148144_2_, p_148144_3_, p_148144_4_);
    }
    
    @Override
    protected boolean isSelected(final int p_148131_1_) {
        return this.field_154340_k.isSelectedItem(p_148131_1_);
    }
    
    @Override
    protected void drawBackground() {
        this.field_154340_k.renderBackground();
    }
    
    @Override
    protected void drawSlot(final int p_148126_1_, final int p_148126_2_, final int p_148126_3_, final int p_148126_4_, final Tessellator p_148126_5_, final int p_148126_6_, final int p_148126_7_) {
        this.field_154340_k.renderItem(p_148126_1_, p_148126_2_, p_148126_3_, p_148126_4_, p_148126_6_, p_148126_7_);
    }
    
    public int func_154338_k() {
        return super.field_148155_a;
    }
    
    public int func_154339_l() {
        return super.field_148162_h;
    }
    
    public int func_154337_m() {
        return super.field_148150_g;
    }
    
    @Override
    protected int func_148138_e() {
        return this.field_154340_k.getMaxPosition();
    }
    
    @Override
    protected int func_148137_d() {
        return this.field_154340_k.getScrollbarPosition();
    }
}
