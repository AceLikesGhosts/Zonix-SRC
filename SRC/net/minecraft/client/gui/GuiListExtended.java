package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.client.renderer.*;

public abstract class GuiListExtended extends GuiSlot
{
    private static final String __OBFID = "CL_00000674";
    
    public GuiListExtended(final Minecraft p_i45010_1_, final int p_i45010_2_, final int p_i45010_3_, final int p_i45010_4_, final int p_i45010_5_, final int p_i45010_6_) {
        super(p_i45010_1_, p_i45010_2_, p_i45010_3_, p_i45010_4_, p_i45010_5_, p_i45010_6_);
    }
    
    @Override
    protected void elementClicked(final int p_148144_1_, final boolean p_148144_2_, final int p_148144_3_, final int p_148144_4_) {
    }
    
    @Override
    protected boolean isSelected(final int p_148131_1_) {
        return false;
    }
    
    @Override
    protected void drawBackground() {
    }
    
    @Override
    protected void drawSlot(final int p_148126_1_, final int p_148126_2_, final int p_148126_3_, final int p_148126_4_, final Tessellator p_148126_5_, final int p_148126_6_, final int p_148126_7_) {
        this.func_148180_b(p_148126_1_).func_148279_a(p_148126_1_, p_148126_2_, p_148126_3_, this.func_148139_c(), p_148126_4_, p_148126_5_, p_148126_6_, p_148126_7_, this.func_148124_c(p_148126_6_, p_148126_7_) == p_148126_1_);
    }
    
    public boolean func_148179_a(final int p_148179_1_, final int p_148179_2_, final int p_148179_3_) {
        if (this.func_148141_e(p_148179_2_)) {
            final int var4 = this.func_148124_c(p_148179_1_, p_148179_2_);
            if (var4 >= 0) {
                final int var5 = this.field_148152_e + this.field_148155_a / 2 - this.func_148139_c() / 2 + 2;
                final int var6 = this.field_148153_b + 4 - this.func_148148_g() + var4 * this.field_148149_f + this.field_148160_j;
                final int var7 = p_148179_1_ - var5;
                final int var8 = p_148179_2_ - var6;
                if (this.func_148180_b(var4).func_148278_a(var4, p_148179_1_, p_148179_2_, p_148179_3_, var7, var8)) {
                    this.func_148143_b(false);
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean func_148181_b(final int p_148181_1_, final int p_148181_2_, final int p_148181_3_) {
        for (int var4 = 0; var4 < this.getSize(); ++var4) {
            final int var5 = this.field_148152_e + this.field_148155_a / 2 - this.func_148139_c() / 2 + 2;
            final int var6 = this.field_148153_b + 4 - this.func_148148_g() + var4 * this.field_148149_f + this.field_148160_j;
            final int var7 = p_148181_1_ - var5;
            final int var8 = p_148181_2_ - var6;
            this.func_148180_b(var4).func_148277_b(var4, p_148181_1_, p_148181_2_, p_148181_3_, var7, var8);
        }
        this.func_148143_b(true);
        return false;
    }
    
    public abstract IGuiListEntry func_148180_b(final int p0);
    
    public interface IGuiListEntry
    {
        void func_148279_a(final int p0, final int p1, final int p2, final int p3, final int p4, final Tessellator p5, final int p6, final int p7, final boolean p8);
        
        boolean func_148278_a(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
        
        void func_148277_b(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    }
}
