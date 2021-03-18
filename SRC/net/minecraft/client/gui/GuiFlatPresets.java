package net.minecraft.client.gui;

import net.minecraft.client.renderer.entity.*;
import org.lwjgl.input.*;
import net.minecraft.client.resources.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.init.*;
import org.lwjgl.opengl.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.*;

public class GuiFlatPresets extends GuiScreen
{
    private static RenderItem field_146437_a;
    private static final List field_146431_f;
    private final GuiCreateFlatWorld field_146432_g;
    private String field_146438_h;
    private String field_146439_i;
    private String field_146436_r;
    private ListSlot field_146435_s;
    private GuiButton field_146434_t;
    private GuiTextField field_146433_u;
    private static final String __OBFID = "CL_00000704";
    
    public GuiFlatPresets(final GuiCreateFlatWorld p_i46378_1_) {
        this.field_146432_g = p_i46378_1_;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        this.field_146438_h = I18n.format("createWorld.customize.presets.title", new Object[0]);
        this.field_146439_i = I18n.format("createWorld.customize.presets.share", new Object[0]);
        this.field_146436_r = I18n.format("createWorld.customize.presets.list", new Object[0]);
        this.field_146433_u = new GuiTextField(this.fontRendererObj, 50, 40, this.width - 100, 20);
        this.field_146435_s = new ListSlot();
        this.field_146433_u.func_146203_f(1230);
        this.field_146433_u.setText(this.field_146432_g.func_146384_e());
        this.buttonList.add(this.field_146434_t = new GuiButton(0, this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("createWorld.customize.presets.select", new Object[0])));
        this.buttonList.add(new GuiButton(1, this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
        this.func_146426_g();
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void mouseClicked(final int p_73864_1_, final int p_73864_2_, final int p_73864_3_) {
        this.field_146433_u.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
    }
    
    @Override
    protected void keyTyped(final char p_73869_1_, final int p_73869_2_) {
        if (!this.field_146433_u.textboxKeyTyped(p_73869_1_, p_73869_2_)) {
            super.keyTyped(p_73869_1_, p_73869_2_);
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton p_146284_1_) {
        if (p_146284_1_.id == 0 && this.func_146430_p()) {
            this.field_146432_g.func_146383_a(this.field_146433_u.getText());
            this.mc.displayGuiScreen(this.field_146432_g);
        }
        else if (p_146284_1_.id == 1) {
            this.mc.displayGuiScreen(this.field_146432_g);
        }
    }
    
    @Override
    public void drawScreen(final int p_73863_1_, final int p_73863_2_, final float p_73863_3_) {
        this.drawDefaultBackground();
        this.field_146435_s.func_148128_a(p_73863_1_, p_73863_2_, p_73863_3_);
        this.drawCenteredString(this.fontRendererObj, this.field_146438_h, this.width / 2, 8, 16777215);
        this.drawString(this.fontRendererObj, this.field_146439_i, 50, 30, 10526880);
        this.drawString(this.fontRendererObj, this.field_146436_r, 50, 70, 10526880);
        this.field_146433_u.drawTextBox();
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
    
    @Override
    public void updateScreen() {
        this.field_146433_u.updateCursorCounter();
        super.updateScreen();
    }
    
    public void func_146426_g() {
        final boolean var1 = this.func_146430_p();
        this.field_146434_t.enabled = var1;
    }
    
    private boolean func_146430_p() {
        return (this.field_146435_s.field_148175_k > -1 && this.field_146435_s.field_148175_k < GuiFlatPresets.field_146431_f.size()) || this.field_146433_u.getText().length() > 1;
    }
    
    private static void func_146425_a(final String p_146425_0_, final Item p_146425_1_, final BiomeGenBase p_146425_2_, final FlatLayerInfo... p_146425_3_) {
        func_146421_a(p_146425_0_, p_146425_1_, p_146425_2_, null, p_146425_3_);
    }
    
    private static void func_146421_a(final String p_146421_0_, final Item p_146421_1_, final BiomeGenBase p_146421_2_, final List p_146421_3_, final FlatLayerInfo... p_146421_4_) {
        final FlatGeneratorInfo var5 = new FlatGeneratorInfo();
        for (int var6 = p_146421_4_.length - 1; var6 >= 0; --var6) {
            var5.getFlatLayers().add(p_146421_4_[var6]);
        }
        var5.setBiome(p_146421_2_.biomeID);
        var5.func_82645_d();
        if (p_146421_3_ != null) {
            for (final String var8 : p_146421_3_) {
                var5.getWorldFeatures().put(var8, new HashMap());
            }
        }
        GuiFlatPresets.field_146431_f.add(new LayerItem(p_146421_1_, p_146421_0_, var5.toString()));
    }
    
    static {
        GuiFlatPresets.field_146437_a = new RenderItem();
        field_146431_f = new ArrayList();
        func_146421_a("Classic Flat", Item.getItemFromBlock(Blocks.grass), BiomeGenBase.plains, Arrays.asList("village"), new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(2, Blocks.dirt), new FlatLayerInfo(1, Blocks.bedrock));
        func_146421_a("Tunnelers' Dream", Item.getItemFromBlock(Blocks.stone), BiomeGenBase.extremeHills, Arrays.asList("biome_1", "dungeon", "decoration", "stronghold", "mineshaft"), new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(5, Blocks.dirt), new FlatLayerInfo(230, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
        func_146421_a("Water World", Item.getItemFromBlock(Blocks.flowing_water), BiomeGenBase.plains, Arrays.asList("village", "biome_1"), new FlatLayerInfo(90, Blocks.water), new FlatLayerInfo(5, Blocks.sand), new FlatLayerInfo(5, Blocks.dirt), new FlatLayerInfo(5, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
        func_146421_a("Overworld", Item.getItemFromBlock(Blocks.tallgrass), BiomeGenBase.plains, Arrays.asList("village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon", "lake", "lava_lake"), new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(3, Blocks.dirt), new FlatLayerInfo(59, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
        func_146421_a("Snowy Kingdom", Item.getItemFromBlock(Blocks.snow_layer), BiomeGenBase.icePlains, Arrays.asList("village", "biome_1"), new FlatLayerInfo(1, Blocks.snow_layer), new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(3, Blocks.dirt), new FlatLayerInfo(59, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
        func_146421_a("Bottomless Pit", Items.feather, BiomeGenBase.plains, Arrays.asList("village", "biome_1"), new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(3, Blocks.dirt), new FlatLayerInfo(2, Blocks.cobblestone));
        func_146421_a("Desert", Item.getItemFromBlock(Blocks.sand), BiomeGenBase.desert, Arrays.asList("village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon"), new FlatLayerInfo(8, Blocks.sand), new FlatLayerInfo(52, Blocks.sandstone), new FlatLayerInfo(3, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
        func_146425_a("Redstone Ready", Items.redstone, BiomeGenBase.desert, new FlatLayerInfo(52, Blocks.sandstone), new FlatLayerInfo(3, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock));
    }
    
    static class LayerItem
    {
        public Item field_148234_a;
        public String field_148232_b;
        public String field_148233_c;
        private static final String __OBFID = "CL_00000705";
        
        public LayerItem(final Item p_i45022_1_, final String p_i45022_2_, final String p_i45022_3_) {
            this.field_148234_a = p_i45022_1_;
            this.field_148232_b = p_i45022_2_;
            this.field_148233_c = p_i45022_3_;
        }
    }
    
    class ListSlot extends GuiSlot
    {
        public int field_148175_k;
        private static final String __OBFID = "CL_00000706";
        
        public ListSlot() {
            super(GuiFlatPresets.this.mc, GuiFlatPresets.this.width, GuiFlatPresets.this.height, 80, GuiFlatPresets.this.height - 37, 24);
            this.field_148175_k = -1;
        }
        
        private void func_148172_a(final int p_148172_1_, final int p_148172_2_, final Item p_148172_3_) {
            this.func_148173_e(p_148172_1_ + 1, p_148172_2_ + 1);
            GL11.glEnable(32826);
            RenderHelper.enableGUIStandardItemLighting();
            GuiFlatPresets.field_146437_a.renderItemIntoGUI(GuiFlatPresets.this.fontRendererObj, GuiFlatPresets.this.mc.getTextureManager(), new ItemStack(p_148172_3_, 1, 0), p_148172_1_ + 2, p_148172_2_ + 2);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(32826);
        }
        
        private void func_148173_e(final int p_148173_1_, final int p_148173_2_) {
            this.func_148171_c(p_148173_1_, p_148173_2_, 0, 0);
        }
        
        private void func_148171_c(final int p_148171_1_, final int p_148171_2_, final int p_148171_3_, final int p_148171_4_) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GuiFlatPresets.this.mc.getTextureManager().bindTexture(Gui.statIcons);
            final float var5 = 0.0078125f;
            final float var6 = 0.0078125f;
            final boolean var7 = true;
            final boolean var8 = true;
            final Tessellator var9 = Tessellator.instance;
            var9.startDrawingQuads();
            var9.addVertexWithUV(p_148171_1_ + 0, p_148171_2_ + 18, GuiFlatPresets.this.zLevel, (p_148171_3_ + 0) * 0.0078125f, (p_148171_4_ + 18) * 0.0078125f);
            var9.addVertexWithUV(p_148171_1_ + 18, p_148171_2_ + 18, GuiFlatPresets.this.zLevel, (p_148171_3_ + 18) * 0.0078125f, (p_148171_4_ + 18) * 0.0078125f);
            var9.addVertexWithUV(p_148171_1_ + 18, p_148171_2_ + 0, GuiFlatPresets.this.zLevel, (p_148171_3_ + 18) * 0.0078125f, (p_148171_4_ + 0) * 0.0078125f);
            var9.addVertexWithUV(p_148171_1_ + 0, p_148171_2_ + 0, GuiFlatPresets.this.zLevel, (p_148171_3_ + 0) * 0.0078125f, (p_148171_4_ + 0) * 0.0078125f);
            var9.draw();
        }
        
        @Override
        protected int getSize() {
            return GuiFlatPresets.field_146431_f.size();
        }
        
        @Override
        protected void elementClicked(final int p_148144_1_, final boolean p_148144_2_, final int p_148144_3_, final int p_148144_4_) {
            this.field_148175_k = p_148144_1_;
            GuiFlatPresets.this.func_146426_g();
            GuiFlatPresets.this.field_146433_u.setText(GuiFlatPresets.field_146431_f.get(GuiFlatPresets.this.field_146435_s.field_148175_k).field_148233_c);
        }
        
        @Override
        protected boolean isSelected(final int p_148131_1_) {
            return p_148131_1_ == this.field_148175_k;
        }
        
        @Override
        protected void drawBackground() {
        }
        
        @Override
        protected void drawSlot(final int p_148126_1_, final int p_148126_2_, final int p_148126_3_, final int p_148126_4_, final Tessellator p_148126_5_, final int p_148126_6_, final int p_148126_7_) {
            final LayerItem var8 = GuiFlatPresets.field_146431_f.get(p_148126_1_);
            this.func_148172_a(p_148126_2_, p_148126_3_, var8.field_148234_a);
            GuiFlatPresets.this.fontRendererObj.drawString(var8.field_148232_b, p_148126_2_ + 18 + 5, p_148126_3_ + 6, 16777215);
        }
    }
}
