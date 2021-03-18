package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import net.minecraft.client.resources.*;
import java.util.*;
import net.minecraft.client.renderer.*;

public class GuiSnooper extends GuiScreen
{
    private final GuiScreen field_146608_a;
    private final GameSettings field_146603_f;
    private final java.util.List field_146604_g;
    private final java.util.List field_146609_h;
    private String field_146610_i;
    private String[] field_146607_r;
    private List field_146606_s;
    private GuiButton field_146605_t;
    private static final String __OBFID = "CL_00000714";
    
    public GuiSnooper(final GuiScreen p_i1061_1_, final GameSettings p_i1061_2_) {
        this.field_146604_g = new ArrayList();
        this.field_146609_h = new ArrayList();
        this.field_146608_a = p_i1061_1_;
        this.field_146603_f = p_i1061_2_;
    }
    
    @Override
    public void initGui() {
        this.field_146610_i = I18n.format("options.snooper.title", new Object[0]);
        final String var1 = I18n.format("options.snooper.desc", new Object[0]);
        final ArrayList var2 = new ArrayList();
        for (final String var4 : this.fontRendererObj.listFormattedStringToWidth(var1, this.width - 30)) {
            var2.add(var4);
        }
        this.field_146607_r = var2.toArray(new String[0]);
        this.field_146604_g.clear();
        this.field_146609_h.clear();
        this.buttonList.add(this.field_146605_t = new GuiButton(1, this.width / 2 - 152, this.height - 30, 150, 20, this.field_146603_f.getKeyBinding(GameSettings.Options.SNOOPER_ENABLED)));
        this.buttonList.add(new GuiButton(2, this.width / 2 + 2, this.height - 30, 150, 20, I18n.format("gui.done", new Object[0])));
        final boolean var5 = this.mc.getIntegratedServer() != null && this.mc.getIntegratedServer().getPlayerUsageSnooper() != null;
        for (final Map.Entry var7 : new TreeMap(this.mc.getPlayerUsageSnooper().getCurrentStats()).entrySet()) {
            this.field_146604_g.add((var5 ? "C " : "") + var7.getKey());
            this.field_146609_h.add(this.fontRendererObj.trimStringToWidth(var7.getValue(), this.width - 220));
        }
        if (var5) {
            for (final Map.Entry var7 : new TreeMap(this.mc.getIntegratedServer().getPlayerUsageSnooper().getCurrentStats()).entrySet()) {
                this.field_146604_g.add("S " + var7.getKey());
                this.field_146609_h.add(this.fontRendererObj.trimStringToWidth(var7.getValue(), this.width - 220));
            }
        }
        this.field_146606_s = new List();
    }
    
    @Override
    protected void actionPerformed(final GuiButton p_146284_1_) {
        if (p_146284_1_.enabled) {
            if (p_146284_1_.id == 2) {
                this.field_146603_f.saveOptions();
                this.field_146603_f.saveOptions();
                this.mc.displayGuiScreen(this.field_146608_a);
            }
            if (p_146284_1_.id == 1) {
                this.field_146603_f.setOptionValue(GameSettings.Options.SNOOPER_ENABLED, 1);
                this.field_146605_t.displayString = this.field_146603_f.getKeyBinding(GameSettings.Options.SNOOPER_ENABLED);
            }
        }
    }
    
    @Override
    public void drawScreen(final int p_73863_1_, final int p_73863_2_, final float p_73863_3_) {
        this.drawDefaultBackground();
        this.field_146606_s.func_148128_a(p_73863_1_, p_73863_2_, p_73863_3_);
        this.drawCenteredString(this.fontRendererObj, this.field_146610_i, this.width / 2, 8, 16777215);
        int var4 = 22;
        for (final String var8 : this.field_146607_r) {
            this.drawCenteredString(this.fontRendererObj, var8, this.width / 2, var4, 8421504);
            var4 += this.fontRendererObj.FONT_HEIGHT;
        }
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
    
    class List extends GuiSlot
    {
        private static final String __OBFID = "CL_00000715";
        
        public List() {
            super(GuiSnooper.this.mc, GuiSnooper.this.width, GuiSnooper.this.height, 80, GuiSnooper.this.height - 40, GuiSnooper.this.fontRendererObj.FONT_HEIGHT + 1);
        }
        
        @Override
        protected int getSize() {
            return GuiSnooper.this.field_146604_g.size();
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
            GuiSnooper.this.fontRendererObj.drawString(GuiSnooper.this.field_146604_g.get(p_148126_1_), 10, p_148126_3_, 16777215);
            GuiSnooper.this.fontRendererObj.drawString(GuiSnooper.this.field_146609_h.get(p_148126_1_), 230, p_148126_3_, 16777215);
        }
        
        @Override
        protected int func_148137_d() {
            return this.field_148155_a - 10;
        }
    }
}
