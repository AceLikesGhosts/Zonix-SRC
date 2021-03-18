package net.minecraft.client.gui;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.resources.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.gen.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class GuiCreateFlatWorld extends GuiScreen
{
    private static RenderItem field_146392_a;
    private final GuiCreateWorld field_146385_f;
    private FlatGeneratorInfo field_146387_g;
    private String field_146393_h;
    private String field_146394_i;
    private String field_146391_r;
    private Details field_146390_s;
    private GuiButton field_146389_t;
    private GuiButton field_146388_u;
    private GuiButton field_146386_v;
    private static final String __OBFID = "CL_00000687";
    
    public GuiCreateFlatWorld(final GuiCreateWorld p_i1029_1_, final String p_i1029_2_) {
        this.field_146387_g = FlatGeneratorInfo.getDefaultFlatGenerator();
        this.field_146385_f = p_i1029_1_;
        this.func_146383_a(p_i1029_2_);
    }
    
    public String func_146384_e() {
        return this.field_146387_g.toString();
    }
    
    public void func_146383_a(final String p_146383_1_) {
        this.field_146387_g = FlatGeneratorInfo.createFlatGeneratorFromString(p_146383_1_);
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.field_146393_h = I18n.format("createWorld.customize.flat.title", new Object[0]);
        this.field_146394_i = I18n.format("createWorld.customize.flat.tile", new Object[0]);
        this.field_146391_r = I18n.format("createWorld.customize.flat.height", new Object[0]);
        this.field_146390_s = new Details();
        this.buttonList.add(this.field_146389_t = new GuiButton(2, this.width / 2 - 154, this.height - 52, 100, 20, I18n.format("createWorld.customize.flat.addLayer", new Object[0]) + " (NYI)"));
        this.buttonList.add(this.field_146388_u = new GuiButton(3, this.width / 2 - 50, this.height - 52, 100, 20, I18n.format("createWorld.customize.flat.editLayer", new Object[0]) + " (NYI)"));
        this.buttonList.add(this.field_146386_v = new GuiButton(4, this.width / 2 - 155, this.height - 52, 150, 20, I18n.format("createWorld.customize.flat.removeLayer", new Object[0])));
        this.buttonList.add(new GuiButton(0, this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("gui.done", new Object[0])));
        this.buttonList.add(new GuiButton(5, this.width / 2 + 5, this.height - 52, 150, 20, I18n.format("createWorld.customize.presets", new Object[0])));
        this.buttonList.add(new GuiButton(1, this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
        final GuiButton field_146389_t = this.field_146389_t;
        final GuiButton field_146388_u = this.field_146388_u;
        final boolean b = false;
        field_146388_u.field_146125_m = b;
        field_146389_t.field_146125_m = b;
        this.field_146387_g.func_82645_d();
        this.func_146375_g();
    }
    
    @Override
    protected void actionPerformed(final GuiButton p_146284_1_) {
        final int var2 = this.field_146387_g.getFlatLayers().size() - this.field_146390_s.field_148228_k - 1;
        if (p_146284_1_.id == 1) {
            this.mc.displayGuiScreen(this.field_146385_f);
        }
        else if (p_146284_1_.id == 0) {
            this.field_146385_f.field_146334_a = this.func_146384_e();
            this.mc.displayGuiScreen(this.field_146385_f);
        }
        else if (p_146284_1_.id == 5) {
            this.mc.displayGuiScreen(new GuiFlatPresets(this));
        }
        else if (p_146284_1_.id == 4 && this.func_146382_i()) {
            this.field_146387_g.getFlatLayers().remove(var2);
            this.field_146390_s.field_148228_k = Math.min(this.field_146390_s.field_148228_k, this.field_146387_g.getFlatLayers().size() - 1);
        }
        this.field_146387_g.func_82645_d();
        this.func_146375_g();
    }
    
    public void func_146375_g() {
        final boolean var1 = this.func_146382_i();
        this.field_146386_v.enabled = var1;
        this.field_146388_u.enabled = var1;
        this.field_146388_u.enabled = false;
        this.field_146389_t.enabled = false;
    }
    
    private boolean func_146382_i() {
        return this.field_146390_s.field_148228_k > -1 && this.field_146390_s.field_148228_k < this.field_146387_g.getFlatLayers().size();
    }
    
    @Override
    public void drawScreen(final int p_73863_1_, final int p_73863_2_, final float p_73863_3_) {
        this.drawDefaultBackground();
        this.field_146390_s.func_148128_a(p_73863_1_, p_73863_2_, p_73863_3_);
        this.drawCenteredString(this.fontRendererObj, this.field_146393_h, this.width / 2, 8, 16777215);
        final int var4 = this.width / 2 - 92 - 16;
        this.drawString(this.fontRendererObj, this.field_146394_i, var4, 32, 16777215);
        this.drawString(this.fontRendererObj, this.field_146391_r, var4 + 2 + 213 - this.fontRendererObj.getStringWidth(this.field_146391_r), 32, 16777215);
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
    
    static {
        GuiCreateFlatWorld.field_146392_a = new RenderItem();
    }
    
    class Details extends GuiSlot
    {
        public int field_148228_k;
        private static final String __OBFID = "CL_00000688";
        
        public Details() {
            super(GuiCreateFlatWorld.this.mc, GuiCreateFlatWorld.this.width, GuiCreateFlatWorld.this.height, 43, GuiCreateFlatWorld.this.height - 60, 24);
            this.field_148228_k = -1;
        }
        
        private void func_148225_a(final int p_148225_1_, final int p_148225_2_, final ItemStack p_148225_3_) {
            this.func_148226_e(p_148225_1_ + 1, p_148225_2_ + 1);
            GL11.glEnable(32826);
            if (p_148225_3_ != null) {
                RenderHelper.enableGUIStandardItemLighting();
                GuiCreateFlatWorld.field_146392_a.renderItemIntoGUI(GuiCreateFlatWorld.this.fontRendererObj, GuiCreateFlatWorld.this.mc.getTextureManager(), p_148225_3_, p_148225_1_ + 2, p_148225_2_ + 2);
                RenderHelper.disableStandardItemLighting();
            }
            GL11.glDisable(32826);
        }
        
        private void func_148226_e(final int p_148226_1_, final int p_148226_2_) {
            this.func_148224_c(p_148226_1_, p_148226_2_, 0, 0);
        }
        
        private void func_148224_c(final int p_148224_1_, final int p_148224_2_, final int p_148224_3_, final int p_148224_4_) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GuiCreateFlatWorld.this.mc.getTextureManager().bindTexture(Gui.statIcons);
            final float var5 = 0.0078125f;
            final float var6 = 0.0078125f;
            final boolean var7 = true;
            final boolean var8 = true;
            final Tessellator var9 = Tessellator.instance;
            var9.startDrawingQuads();
            var9.addVertexWithUV(p_148224_1_ + 0, p_148224_2_ + 18, GuiCreateFlatWorld.this.zLevel, (p_148224_3_ + 0) * 0.0078125f, (p_148224_4_ + 18) * 0.0078125f);
            var9.addVertexWithUV(p_148224_1_ + 18, p_148224_2_ + 18, GuiCreateFlatWorld.this.zLevel, (p_148224_3_ + 18) * 0.0078125f, (p_148224_4_ + 18) * 0.0078125f);
            var9.addVertexWithUV(p_148224_1_ + 18, p_148224_2_ + 0, GuiCreateFlatWorld.this.zLevel, (p_148224_3_ + 18) * 0.0078125f, (p_148224_4_ + 0) * 0.0078125f);
            var9.addVertexWithUV(p_148224_1_ + 0, p_148224_2_ + 0, GuiCreateFlatWorld.this.zLevel, (p_148224_3_ + 0) * 0.0078125f, (p_148224_4_ + 0) * 0.0078125f);
            var9.draw();
        }
        
        @Override
        protected int getSize() {
            return GuiCreateFlatWorld.this.field_146387_g.getFlatLayers().size();
        }
        
        @Override
        protected void elementClicked(final int p_148144_1_, final boolean p_148144_2_, final int p_148144_3_, final int p_148144_4_) {
            this.field_148228_k = p_148144_1_;
            GuiCreateFlatWorld.this.func_146375_g();
        }
        
        @Override
        protected boolean isSelected(final int p_148131_1_) {
            return p_148131_1_ == this.field_148228_k;
        }
        
        @Override
        protected void drawBackground() {
        }
        
        @Override
        protected void drawSlot(final int p_148126_1_, final int p_148126_2_, final int p_148126_3_, final int p_148126_4_, final Tessellator p_148126_5_, final int p_148126_6_, final int p_148126_7_) {
            final FlatLayerInfo var8 = GuiCreateFlatWorld.this.field_146387_g.getFlatLayers().get(GuiCreateFlatWorld.this.field_146387_g.getFlatLayers().size() - p_148126_1_ - 1);
            final Item var9 = Item.getItemFromBlock(var8.func_151536_b());
            final ItemStack var10 = (var8.func_151536_b() == Blocks.air) ? null : new ItemStack(var9, 1, var8.getFillBlockMeta());
            final String var11 = (var10 != null && var9 != null) ? var9.getItemStackDisplayName(var10) : "Air";
            this.func_148225_a(p_148126_2_, p_148126_3_, var10);
            GuiCreateFlatWorld.this.fontRendererObj.drawString(var11, p_148126_2_ + 18 + 5, p_148126_3_ + 3, 16777215);
            String var12;
            if (p_148126_1_ == 0) {
                var12 = I18n.format("createWorld.customize.flat.layer.top", var8.getLayerCount());
            }
            else if (p_148126_1_ == GuiCreateFlatWorld.this.field_146387_g.getFlatLayers().size() - 1) {
                var12 = I18n.format("createWorld.customize.flat.layer.bottom", var8.getLayerCount());
            }
            else {
                var12 = I18n.format("createWorld.customize.flat.layer", var8.getLayerCount());
            }
            GuiCreateFlatWorld.this.fontRendererObj.drawString(var12, p_148126_2_ + 2 + 213 - GuiCreateFlatWorld.this.fontRendererObj.getStringWidth(var12), p_148126_3_ + 3, 16777215);
        }
        
        @Override
        protected int func_148137_d() {
            return this.field_148155_a - 70;
        }
    }
}
