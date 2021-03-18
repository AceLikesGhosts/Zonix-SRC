package net.minecraft.client.gui.achievement;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.resources.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;
import net.minecraft.stats.*;
import java.util.*;
import net.minecraft.entity.*;

public class GuiStats extends GuiScreen implements IProgressMeter
{
    private static RenderItem field_146544_g;
    protected GuiScreen field_146549_a;
    protected String field_146542_f;
    private StatsGeneral field_146550_h;
    private StatsItem field_146551_i;
    private StatsBlock field_146548_r;
    private StatsMobsList field_146547_s;
    private StatFileWriter field_146546_t;
    private GuiSlot field_146545_u;
    private boolean field_146543_v;
    private static final String __OBFID = "CL_00000723";
    
    public GuiStats(final GuiScreen p_i1071_1_, final StatFileWriter p_i1071_2_) {
        this.field_146542_f = "Select world";
        this.field_146543_v = true;
        this.field_146549_a = p_i1071_1_;
        this.field_146546_t = p_i1071_2_;
    }
    
    @Override
    public void initGui() {
        this.field_146542_f = I18n.format("gui.stats", new Object[0]);
        this.mc.getNetHandler().addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.REQUEST_STATS));
    }
    
    public void func_146541_h() {
        this.buttonList.add(new GuiButton(0, this.width / 2 + 4, this.height - 28, 150, 20, I18n.format("gui.done", new Object[0])));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 160, this.height - 52, 80, 20, I18n.format("stat.generalButton", new Object[0])));
        final GuiButton var1;
        this.buttonList.add(var1 = new GuiButton(2, this.width / 2 - 80, this.height - 52, 80, 20, I18n.format("stat.blocksButton", new Object[0])));
        final GuiButton var2;
        this.buttonList.add(var2 = new GuiButton(3, this.width / 2, this.height - 52, 80, 20, I18n.format("stat.itemsButton", new Object[0])));
        final GuiButton var3;
        this.buttonList.add(var3 = new GuiButton(4, this.width / 2 + 80, this.height - 52, 80, 20, I18n.format("stat.mobsButton", new Object[0])));
        if (this.field_146548_r.getSize() == 0) {
            var1.enabled = false;
        }
        if (this.field_146551_i.getSize() == 0) {
            var2.enabled = false;
        }
        if (this.field_146547_s.getSize() == 0) {
            var3.enabled = false;
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton p_146284_1_) {
        if (p_146284_1_.enabled) {
            if (p_146284_1_.id == 0) {
                this.mc.displayGuiScreen(this.field_146549_a);
            }
            else if (p_146284_1_.id == 1) {
                this.field_146545_u = this.field_146550_h;
            }
            else if (p_146284_1_.id == 3) {
                this.field_146545_u = this.field_146551_i;
            }
            else if (p_146284_1_.id == 2) {
                this.field_146545_u = this.field_146548_r;
            }
            else if (p_146284_1_.id == 4) {
                this.field_146545_u = this.field_146547_s;
            }
            else {
                this.field_146545_u.func_148147_a(p_146284_1_);
            }
        }
    }
    
    @Override
    public void drawScreen(final int p_73863_1_, final int p_73863_2_, final float p_73863_3_) {
        if (this.field_146543_v) {
            this.drawDefaultBackground();
            this.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingStats", new Object[0]), this.width / 2, this.height / 2, 16777215);
            this.drawCenteredString(this.fontRendererObj, GuiStats.field_146510_b_[(int)(Minecraft.getSystemTime() / 150L % GuiStats.field_146510_b_.length)], this.width / 2, this.height / 2 + this.fontRendererObj.FONT_HEIGHT * 2, 16777215);
        }
        else {
            this.field_146545_u.func_148128_a(p_73863_1_, p_73863_2_, p_73863_3_);
            this.drawCenteredString(this.fontRendererObj, this.field_146542_f, this.width / 2, 20, 16777215);
            super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
        }
    }
    
    @Override
    public void func_146509_g() {
        if (this.field_146543_v) {
            (this.field_146550_h = new StatsGeneral()).func_148134_d(1, 1);
            (this.field_146551_i = new StatsItem()).func_148134_d(1, 1);
            (this.field_146548_r = new StatsBlock()).func_148134_d(1, 1);
            (this.field_146547_s = new StatsMobsList()).func_148134_d(1, 1);
            this.field_146545_u = this.field_146550_h;
            this.func_146541_h();
            this.field_146543_v = false;
        }
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return !this.field_146543_v;
    }
    
    private void func_146521_a(final int p_146521_1_, final int p_146521_2_, final Item p_146521_3_) {
        this.func_146531_b(p_146521_1_ + 1, p_146521_2_ + 1);
        GL11.glEnable(32826);
        RenderHelper.enableGUIStandardItemLighting();
        GuiStats.field_146544_g.renderItemIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), new ItemStack(p_146521_3_, 1, 0), p_146521_1_ + 2, p_146521_2_ + 2);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(32826);
    }
    
    private void func_146531_b(final int p_146531_1_, final int p_146531_2_) {
        this.func_146527_c(p_146531_1_, p_146531_2_, 0, 0);
    }
    
    private void func_146527_c(final int p_146527_1_, final int p_146527_2_, final int p_146527_3_, final int p_146527_4_) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiStats.statIcons);
        final float var5 = 0.0078125f;
        final float var6 = 0.0078125f;
        final boolean var7 = true;
        final boolean var8 = true;
        final Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        var9.addVertexWithUV(p_146527_1_ + 0, p_146527_2_ + 18, this.zLevel, (p_146527_3_ + 0) * 0.0078125f, (p_146527_4_ + 18) * 0.0078125f);
        var9.addVertexWithUV(p_146527_1_ + 18, p_146527_2_ + 18, this.zLevel, (p_146527_3_ + 18) * 0.0078125f, (p_146527_4_ + 18) * 0.0078125f);
        var9.addVertexWithUV(p_146527_1_ + 18, p_146527_2_ + 0, this.zLevel, (p_146527_3_ + 18) * 0.0078125f, (p_146527_4_ + 0) * 0.0078125f);
        var9.addVertexWithUV(p_146527_1_ + 0, p_146527_2_ + 0, this.zLevel, (p_146527_3_ + 0) * 0.0078125f, (p_146527_4_ + 0) * 0.0078125f);
        var9.draw();
    }
    
    static {
        GuiStats.field_146544_g = new RenderItem();
    }
    
    abstract class Stats extends GuiSlot
    {
        protected int field_148218_l;
        protected List field_148219_m;
        protected Comparator field_148216_n;
        protected int field_148217_o;
        protected int field_148215_p;
        private static final String __OBFID = "CL_00000730";
        
        protected Stats() {
            super(GuiStats.this.mc, GuiStats.this.width, GuiStats.this.height, 32, GuiStats.this.height - 64, 20);
            this.field_148218_l = -1;
            this.field_148217_o = -1;
            this.func_148130_a(false);
            this.func_148133_a(true, 20);
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
            GuiStats.this.drawDefaultBackground();
        }
        
        @Override
        protected void func_148129_a(final int p_148129_1_, final int p_148129_2_, final Tessellator p_148129_3_) {
            if (!Mouse.isButtonDown(0)) {
                this.field_148218_l = -1;
            }
            if (this.field_148218_l == 0) {
                GuiStats.this.func_146527_c(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 0, 0);
            }
            else {
                GuiStats.this.func_146527_c(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 0, 18);
            }
            if (this.field_148218_l == 1) {
                GuiStats.this.func_146527_c(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 0, 0);
            }
            else {
                GuiStats.this.func_146527_c(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 0, 18);
            }
            if (this.field_148218_l == 2) {
                GuiStats.this.func_146527_c(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 0, 0);
            }
            else {
                GuiStats.this.func_146527_c(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 0, 18);
            }
            if (this.field_148217_o != -1) {
                short var4 = 79;
                byte var5 = 18;
                if (this.field_148217_o == 1) {
                    var4 = 129;
                }
                else if (this.field_148217_o == 2) {
                    var4 = 179;
                }
                if (this.field_148215_p == 1) {
                    var5 = 36;
                }
                GuiStats.this.func_146527_c(p_148129_1_ + var4, p_148129_2_ + 1, var5, 0);
            }
        }
        
        @Override
        protected void func_148132_a(final int p_148132_1_, final int p_148132_2_) {
            this.field_148218_l = -1;
            if (p_148132_1_ >= 79 && p_148132_1_ < 115) {
                this.field_148218_l = 0;
            }
            else if (p_148132_1_ >= 129 && p_148132_1_ < 165) {
                this.field_148218_l = 1;
            }
            else if (p_148132_1_ >= 179 && p_148132_1_ < 215) {
                this.field_148218_l = 2;
            }
            if (this.field_148218_l >= 0) {
                this.func_148212_h(this.field_148218_l);
                GuiStats.this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            }
        }
        
        @Override
        protected final int getSize() {
            return this.field_148219_m.size();
        }
        
        protected final StatCrafting func_148211_c(final int p_148211_1_) {
            return this.field_148219_m.get(p_148211_1_);
        }
        
        protected abstract String func_148210_b(final int p0);
        
        protected void func_148209_a(final StatBase p_148209_1_, final int p_148209_2_, final int p_148209_3_, final boolean p_148209_4_) {
            if (p_148209_1_ != null) {
                final String var5 = p_148209_1_.func_75968_a(GuiStats.this.field_146546_t.writeStat(p_148209_1_));
                GuiStats.this.drawString(GuiStats.this.fontRendererObj, var5, p_148209_2_ - GuiStats.this.fontRendererObj.getStringWidth(var5), p_148209_3_ + 5, p_148209_4_ ? 16777215 : 9474192);
            }
            else {
                final String var5 = "-";
                GuiStats.this.drawString(GuiStats.this.fontRendererObj, var5, p_148209_2_ - GuiStats.this.fontRendererObj.getStringWidth(var5), p_148209_3_ + 5, p_148209_4_ ? 16777215 : 9474192);
            }
        }
        
        @Override
        protected void func_148142_b(final int p_148142_1_, final int p_148142_2_) {
            if (p_148142_2_ >= this.field_148153_b && p_148142_2_ <= this.field_148154_c) {
                final int var3 = this.func_148124_c(p_148142_1_, p_148142_2_);
                final int var4 = this.field_148155_a / 2 - 92 - 16;
                if (var3 >= 0) {
                    if (p_148142_1_ < var4 + 40 || p_148142_1_ > var4 + 40 + 20) {
                        return;
                    }
                    final StatCrafting var5 = this.func_148211_c(var3);
                    this.func_148213_a(var5, p_148142_1_, p_148142_2_);
                }
                else {
                    String var6 = "";
                    if (p_148142_1_ >= var4 + 115 - 18 && p_148142_1_ <= var4 + 115) {
                        var6 = this.func_148210_b(0);
                    }
                    else if (p_148142_1_ >= var4 + 165 - 18 && p_148142_1_ <= var4 + 165) {
                        var6 = this.func_148210_b(1);
                    }
                    else {
                        if (p_148142_1_ < var4 + 215 - 18 || p_148142_1_ > var4 + 215) {
                            return;
                        }
                        var6 = this.func_148210_b(2);
                    }
                    var6 = ("" + I18n.format(var6, new Object[0])).trim();
                    if (var6.length() > 0) {
                        final int var7 = p_148142_1_ + 12;
                        final int var8 = p_148142_2_ - 12;
                        final int var9 = GuiStats.this.fontRendererObj.getStringWidth(var6);
                        Gui.this.drawGradientRect(var7 - 3, var8 - 3, var7 + var9 + 3, var8 + 8 + 3, -1073741824, -1073741824);
                        GuiStats.this.fontRendererObj.drawStringWithShadow(var6, var7, var8, -1);
                    }
                }
            }
        }
        
        protected void func_148213_a(final StatCrafting p_148213_1_, final int p_148213_2_, final int p_148213_3_) {
            if (p_148213_1_ != null) {
                final Item var4 = p_148213_1_.func_150959_a();
                final String var5 = ("" + I18n.format(var4.getUnlocalizedName() + ".name", new Object[0])).trim();
                if (var5.length() > 0) {
                    final int var6 = p_148213_2_ + 12;
                    final int var7 = p_148213_3_ - 12;
                    final int var8 = GuiStats.this.fontRendererObj.getStringWidth(var5);
                    Gui.this.drawGradientRect(var6 - 3, var7 - 3, var6 + var8 + 3, var7 + 8 + 3, -1073741824, -1073741824);
                    GuiStats.this.fontRendererObj.drawStringWithShadow(var5, var6, var7, -1);
                }
            }
        }
        
        protected void func_148212_h(final int p_148212_1_) {
            if (p_148212_1_ != this.field_148217_o) {
                this.field_148217_o = p_148212_1_;
                this.field_148215_p = -1;
            }
            else if (this.field_148215_p == -1) {
                this.field_148215_p = 1;
            }
            else {
                this.field_148217_o = -1;
                this.field_148215_p = 0;
            }
            Collections.sort((List<Object>)this.field_148219_m, this.field_148216_n);
        }
    }
    
    class StatsBlock extends Stats
    {
        private static final String __OBFID = "CL_00000724";
        
        public StatsBlock() {
            this.field_148219_m = new ArrayList();
            for (final StatCrafting var3 : StatList.objectMineStats) {
                boolean var4 = false;
                final int var5 = Item.getIdFromItem(var3.func_150959_a());
                if (GuiStats.this.field_146546_t.writeStat(var3) > 0) {
                    var4 = true;
                }
                else if (StatList.objectUseStats[var5] != null && GuiStats.this.field_146546_t.writeStat(StatList.objectUseStats[var5]) > 0) {
                    var4 = true;
                }
                else if (StatList.objectCraftStats[var5] != null && GuiStats.this.field_146546_t.writeStat(StatList.objectCraftStats[var5]) > 0) {
                    var4 = true;
                }
                if (var4) {
                    this.field_148219_m.add(var3);
                }
            }
            this.field_148216_n = new Comparator() {
                private static final String __OBFID = "CL_00000725";
                
                public int compare(final StatCrafting p_compare_1_, final StatCrafting p_compare_2_) {
                    final int var3 = Item.getIdFromItem(p_compare_1_.func_150959_a());
                    final int var4 = Item.getIdFromItem(p_compare_2_.func_150959_a());
                    StatBase var5 = null;
                    StatBase var6 = null;
                    if (StatsBlock.this.field_148217_o == 2) {
                        var5 = StatList.mineBlockStatArray[var3];
                        var6 = StatList.mineBlockStatArray[var4];
                    }
                    else if (StatsBlock.this.field_148217_o == 0) {
                        var5 = StatList.objectCraftStats[var3];
                        var6 = StatList.objectCraftStats[var4];
                    }
                    else if (StatsBlock.this.field_148217_o == 1) {
                        var5 = StatList.objectUseStats[var3];
                        var6 = StatList.objectUseStats[var4];
                    }
                    if (var5 != null || var6 != null) {
                        if (var5 == null) {
                            return 1;
                        }
                        if (var6 == null) {
                            return -1;
                        }
                        final int var7 = GuiStats.this.field_146546_t.writeStat(var5);
                        final int var8 = GuiStats.this.field_146546_t.writeStat(var6);
                        if (var7 != var8) {
                            return (var7 - var8) * StatsBlock.this.field_148215_p;
                        }
                    }
                    return var3 - var4;
                }
                
                @Override
                public int compare(final Object p_compare_1_, final Object p_compare_2_) {
                    return this.compare((StatCrafting)p_compare_1_, (StatCrafting)p_compare_2_);
                }
            };
        }
        
        @Override
        protected void func_148129_a(final int p_148129_1_, final int p_148129_2_, final Tessellator p_148129_3_) {
            super.func_148129_a(p_148129_1_, p_148129_2_, p_148129_3_);
            if (this.field_148218_l == 0) {
                GuiStats.this.func_146527_c(p_148129_1_ + 115 - 18 + 1, p_148129_2_ + 1 + 1, 18, 18);
            }
            else {
                GuiStats.this.func_146527_c(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 18, 18);
            }
            if (this.field_148218_l == 1) {
                GuiStats.this.func_146527_c(p_148129_1_ + 165 - 18 + 1, p_148129_2_ + 1 + 1, 36, 18);
            }
            else {
                GuiStats.this.func_146527_c(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 36, 18);
            }
            if (this.field_148218_l == 2) {
                GuiStats.this.func_146527_c(p_148129_1_ + 215 - 18 + 1, p_148129_2_ + 1 + 1, 54, 18);
            }
            else {
                GuiStats.this.func_146527_c(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 54, 18);
            }
        }
        
        @Override
        protected void drawSlot(final int p_148126_1_, final int p_148126_2_, final int p_148126_3_, final int p_148126_4_, final Tessellator p_148126_5_, final int p_148126_6_, final int p_148126_7_) {
            final StatCrafting var8 = this.func_148211_c(p_148126_1_);
            final Item var9 = var8.func_150959_a();
            GuiStats.this.func_146521_a(p_148126_2_ + 40, p_148126_3_, var9);
            final int var10 = Item.getIdFromItem(var9);
            this.func_148209_a(StatList.objectCraftStats[var10], p_148126_2_ + 115, p_148126_3_, p_148126_1_ % 2 == 0);
            this.func_148209_a(StatList.objectUseStats[var10], p_148126_2_ + 165, p_148126_3_, p_148126_1_ % 2 == 0);
            this.func_148209_a(var8, p_148126_2_ + 215, p_148126_3_, p_148126_1_ % 2 == 0);
        }
        
        @Override
        protected String func_148210_b(final int p_148210_1_) {
            return (p_148210_1_ == 0) ? "stat.crafted" : ((p_148210_1_ == 1) ? "stat.used" : "stat.mined");
        }
    }
    
    class StatsGeneral extends GuiSlot
    {
        private static final String __OBFID = "CL_00000726";
        
        public StatsGeneral() {
            super(GuiStats.this.mc, GuiStats.this.width, GuiStats.this.height, 32, GuiStats.this.height - 64, 10);
            this.func_148130_a(false);
        }
        
        @Override
        protected int getSize() {
            return StatList.generalStats.size();
        }
        
        @Override
        protected void elementClicked(final int p_148144_1_, final boolean p_148144_2_, final int p_148144_3_, final int p_148144_4_) {
        }
        
        @Override
        protected boolean isSelected(final int p_148131_1_) {
            return false;
        }
        
        @Override
        protected int func_148138_e() {
            return this.getSize() * 10;
        }
        
        @Override
        protected void drawBackground() {
            GuiStats.this.drawDefaultBackground();
        }
        
        @Override
        protected void drawSlot(final int p_148126_1_, final int p_148126_2_, final int p_148126_3_, final int p_148126_4_, final Tessellator p_148126_5_, final int p_148126_6_, final int p_148126_7_) {
            final StatBase var8 = StatList.generalStats.get(p_148126_1_);
            GuiStats.this.drawString(GuiStats.this.fontRendererObj, var8.func_150951_e().getUnformattedText(), p_148126_2_ + 2, p_148126_3_ + 1, (p_148126_1_ % 2 == 0) ? 16777215 : 9474192);
            final String var9 = var8.func_75968_a(GuiStats.this.field_146546_t.writeStat(var8));
            GuiStats.this.drawString(GuiStats.this.fontRendererObj, var9, p_148126_2_ + 2 + 213 - GuiStats.this.fontRendererObj.getStringWidth(var9), p_148126_3_ + 1, (p_148126_1_ % 2 == 0) ? 16777215 : 9474192);
        }
    }
    
    class StatsItem extends Stats
    {
        private static final String __OBFID = "CL_00000727";
        
        public StatsItem() {
            this.field_148219_m = new ArrayList();
            for (final StatCrafting var3 : StatList.itemStats) {
                boolean var4 = false;
                final int var5 = Item.getIdFromItem(var3.func_150959_a());
                if (GuiStats.this.field_146546_t.writeStat(var3) > 0) {
                    var4 = true;
                }
                else if (StatList.objectBreakStats[var5] != null && GuiStats.this.field_146546_t.writeStat(StatList.objectBreakStats[var5]) > 0) {
                    var4 = true;
                }
                else if (StatList.objectCraftStats[var5] != null && GuiStats.this.field_146546_t.writeStat(StatList.objectCraftStats[var5]) > 0) {
                    var4 = true;
                }
                if (var4) {
                    this.field_148219_m.add(var3);
                }
            }
            this.field_148216_n = new Comparator() {
                private static final String __OBFID = "CL_00000728";
                
                public int compare(final StatCrafting p_compare_1_, final StatCrafting p_compare_2_) {
                    final int var3 = Item.getIdFromItem(p_compare_1_.func_150959_a());
                    final int var4 = Item.getIdFromItem(p_compare_2_.func_150959_a());
                    StatBase var5 = null;
                    StatBase var6 = null;
                    if (StatsItem.this.field_148217_o == 0) {
                        var5 = StatList.objectBreakStats[var3];
                        var6 = StatList.objectBreakStats[var4];
                    }
                    else if (StatsItem.this.field_148217_o == 1) {
                        var5 = StatList.objectCraftStats[var3];
                        var6 = StatList.objectCraftStats[var4];
                    }
                    else if (StatsItem.this.field_148217_o == 2) {
                        var5 = StatList.objectUseStats[var3];
                        var6 = StatList.objectUseStats[var4];
                    }
                    if (var5 != null || var6 != null) {
                        if (var5 == null) {
                            return 1;
                        }
                        if (var6 == null) {
                            return -1;
                        }
                        final int var7 = GuiStats.this.field_146546_t.writeStat(var5);
                        final int var8 = GuiStats.this.field_146546_t.writeStat(var6);
                        if (var7 != var8) {
                            return (var7 - var8) * StatsItem.this.field_148215_p;
                        }
                    }
                    return var3 - var4;
                }
                
                @Override
                public int compare(final Object p_compare_1_, final Object p_compare_2_) {
                    return this.compare((StatCrafting)p_compare_1_, (StatCrafting)p_compare_2_);
                }
            };
        }
        
        @Override
        protected void func_148129_a(final int p_148129_1_, final int p_148129_2_, final Tessellator p_148129_3_) {
            super.func_148129_a(p_148129_1_, p_148129_2_, p_148129_3_);
            if (this.field_148218_l == 0) {
                GuiStats.this.func_146527_c(p_148129_1_ + 115 - 18 + 1, p_148129_2_ + 1 + 1, 72, 18);
            }
            else {
                GuiStats.this.func_146527_c(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 72, 18);
            }
            if (this.field_148218_l == 1) {
                GuiStats.this.func_146527_c(p_148129_1_ + 165 - 18 + 1, p_148129_2_ + 1 + 1, 18, 18);
            }
            else {
                GuiStats.this.func_146527_c(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 18, 18);
            }
            if (this.field_148218_l == 2) {
                GuiStats.this.func_146527_c(p_148129_1_ + 215 - 18 + 1, p_148129_2_ + 1 + 1, 36, 18);
            }
            else {
                GuiStats.this.func_146527_c(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 36, 18);
            }
        }
        
        @Override
        protected void drawSlot(final int p_148126_1_, final int p_148126_2_, final int p_148126_3_, final int p_148126_4_, final Tessellator p_148126_5_, final int p_148126_6_, final int p_148126_7_) {
            final StatCrafting var8 = this.func_148211_c(p_148126_1_);
            final Item var9 = var8.func_150959_a();
            GuiStats.this.func_146521_a(p_148126_2_ + 40, p_148126_3_, var9);
            final int var10 = Item.getIdFromItem(var9);
            this.func_148209_a(StatList.objectBreakStats[var10], p_148126_2_ + 115, p_148126_3_, p_148126_1_ % 2 == 0);
            this.func_148209_a(StatList.objectCraftStats[var10], p_148126_2_ + 165, p_148126_3_, p_148126_1_ % 2 == 0);
            this.func_148209_a(var8, p_148126_2_ + 215, p_148126_3_, p_148126_1_ % 2 == 0);
        }
        
        @Override
        protected String func_148210_b(final int p_148210_1_) {
            return (p_148210_1_ == 1) ? "stat.crafted" : ((p_148210_1_ == 2) ? "stat.used" : "stat.depleted");
        }
    }
    
    class StatsMobsList extends GuiSlot
    {
        private final List field_148222_l;
        private static final String __OBFID = "CL_00000729";
        
        public StatsMobsList() {
            super(GuiStats.this.mc, GuiStats.this.width, GuiStats.this.height, 32, GuiStats.this.height - 64, GuiStats.this.fontRendererObj.FONT_HEIGHT * 4);
            this.field_148222_l = new ArrayList();
            this.func_148130_a(false);
            for (final EntityList.EntityEggInfo var3 : EntityList.entityEggs.values()) {
                if (GuiStats.this.field_146546_t.writeStat(var3.field_151512_d) > 0 || GuiStats.this.field_146546_t.writeStat(var3.field_151513_e) > 0) {
                    this.field_148222_l.add(var3);
                }
            }
        }
        
        @Override
        protected int getSize() {
            return this.field_148222_l.size();
        }
        
        @Override
        protected void elementClicked(final int p_148144_1_, final boolean p_148144_2_, final int p_148144_3_, final int p_148144_4_) {
        }
        
        @Override
        protected boolean isSelected(final int p_148131_1_) {
            return false;
        }
        
        @Override
        protected int func_148138_e() {
            return this.getSize() * GuiStats.this.fontRendererObj.FONT_HEIGHT * 4;
        }
        
        @Override
        protected void drawBackground() {
            GuiStats.this.drawDefaultBackground();
        }
        
        @Override
        protected void drawSlot(final int p_148126_1_, final int p_148126_2_, final int p_148126_3_, final int p_148126_4_, final Tessellator p_148126_5_, final int p_148126_6_, final int p_148126_7_) {
            final EntityList.EntityEggInfo var8 = this.field_148222_l.get(p_148126_1_);
            final String var9 = I18n.format("entity." + EntityList.getStringFromID(var8.spawnedID) + ".name", new Object[0]);
            final int var10 = GuiStats.this.field_146546_t.writeStat(var8.field_151512_d);
            final int var11 = GuiStats.this.field_146546_t.writeStat(var8.field_151513_e);
            String var12 = I18n.format("stat.entityKills", var10, var9);
            String var13 = I18n.format("stat.entityKilledBy", var9, var11);
            if (var10 == 0) {
                var12 = I18n.format("stat.entityKills.none", var9);
            }
            if (var11 == 0) {
                var13 = I18n.format("stat.entityKilledBy.none", var9);
            }
            GuiStats.this.drawString(GuiStats.this.fontRendererObj, var9, p_148126_2_ + 2 - 10, p_148126_3_ + 1, 16777215);
            GuiStats.this.drawString(GuiStats.this.fontRendererObj, var12, p_148126_2_ + 2, p_148126_3_ + 1 + GuiStats.this.fontRendererObj.FONT_HEIGHT, (var10 == 0) ? 6316128 : 9474192);
            GuiStats.this.drawString(GuiStats.this.fontRendererObj, var13, p_148126_2_ + 2, p_148126_3_ + 1 + GuiStats.this.fontRendererObj.FONT_HEIGHT * 2, (var11 == 0) ? 6316128 : 9474192);
        }
    }
}
