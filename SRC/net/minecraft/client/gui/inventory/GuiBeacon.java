package net.minecraft.client.gui.inventory;

import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import io.netty.buffer.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.*;
import java.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.*;
import net.minecraft.potion.*;

public class GuiBeacon extends GuiContainer
{
    private static final Logger logger;
    private static final ResourceLocation field_147025_v;
    private TileEntityBeacon field_147024_w;
    private ConfirmButton field_147028_x;
    private boolean field_147027_y;
    private static final String __OBFID = "CL_00000739";
    
    public GuiBeacon(final InventoryPlayer p_i1078_1_, final TileEntityBeacon p_i1078_2_) {
        super(new ContainerBeacon(p_i1078_1_, p_i1078_2_));
        this.field_147024_w = p_i1078_2_;
        this.field_146999_f = 230;
        this.field_147000_g = 219;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(this.field_147028_x = new ConfirmButton(-1, this.field_147003_i + 164, this.field_147009_r + 107));
        this.buttonList.add(new CancelButton(-2, this.field_147003_i + 190, this.field_147009_r + 107));
        this.field_147027_y = true;
        this.field_147028_x.enabled = false;
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        if (this.field_147027_y && this.field_147024_w.func_145998_l() >= 0) {
            this.field_147027_y = false;
            for (int var1 = 0; var1 <= 2; ++var1) {
                final int var2 = TileEntityBeacon.field_146009_a[var1].length;
                final int var3 = var2 * 22 + (var2 - 1) * 2;
                for (int var4 = 0; var4 < var2; ++var4) {
                    final int var5 = TileEntityBeacon.field_146009_a[var1][var4].id;
                    final PowerButton var6 = new PowerButton(var1 << 8 | var5, this.field_147003_i + 76 + var4 * 24 - var3 / 2, this.field_147009_r + 22 + var1 * 25, var5, var1);
                    this.buttonList.add(var6);
                    if (var1 >= this.field_147024_w.func_145998_l()) {
                        var6.enabled = false;
                    }
                    else if (var5 == this.field_147024_w.func_146007_j()) {
                        var6.func_146140_b(true);
                    }
                }
            }
            final byte var7 = 3;
            final int var2 = TileEntityBeacon.field_146009_a[var7].length + 1;
            final int var3 = var2 * 22 + (var2 - 1) * 2;
            for (int var4 = 0; var4 < var2 - 1; ++var4) {
                final int var5 = TileEntityBeacon.field_146009_a[var7][var4].id;
                final PowerButton var6 = new PowerButton(var7 << 8 | var5, this.field_147003_i + 167 + var4 * 24 - var3 / 2, this.field_147009_r + 47, var5, var7);
                this.buttonList.add(var6);
                if (var7 >= this.field_147024_w.func_145998_l()) {
                    var6.enabled = false;
                }
                else if (var5 == this.field_147024_w.func_146006_k()) {
                    var6.func_146140_b(true);
                }
            }
            if (this.field_147024_w.func_146007_j() > 0) {
                final PowerButton var8 = new PowerButton(var7 << 8 | this.field_147024_w.func_146007_j(), this.field_147003_i + 167 + (var2 - 1) * 24 - var3 / 2, this.field_147009_r + 47, this.field_147024_w.func_146007_j(), var7);
                this.buttonList.add(var8);
                if (var7 >= this.field_147024_w.func_145998_l()) {
                    var8.enabled = false;
                }
                else if (this.field_147024_w.func_146007_j() == this.field_147024_w.func_146006_k()) {
                    var8.func_146140_b(true);
                }
            }
        }
        this.field_147028_x.enabled = (this.field_147024_w.getStackInSlot(0) != null && this.field_147024_w.func_146007_j() > 0);
    }
    
    @Override
    protected void actionPerformed(final GuiButton p_146284_1_) {
        if (p_146284_1_.id == -2) {
            this.mc.displayGuiScreen(null);
        }
        else if (p_146284_1_.id == -1) {
            final String var2 = "MC|Beacon";
            final ByteBuf var3 = Unpooled.buffer();
            try {
                var3.writeInt(this.field_147024_w.func_146007_j());
                var3.writeInt(this.field_147024_w.func_146006_k());
                this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload(var2, var3));
            }
            catch (Exception var4) {
                GuiBeacon.logger.error("Couldn't send beacon info", (Throwable)var4);
            }
            finally {
                var3.release();
            }
            this.mc.displayGuiScreen(null);
        }
        else if (p_146284_1_ instanceof PowerButton) {
            if (((PowerButton)p_146284_1_).func_146141_c()) {
                return;
            }
            final int var5 = p_146284_1_.id;
            final int var6 = var5 & 0xFF;
            final int var7 = var5 >> 8;
            if (var7 < 3) {
                this.field_147024_w.func_146001_d(var6);
            }
            else {
                this.field_147024_w.func_146004_e(var6);
            }
            this.buttonList.clear();
            this.initGui();
            this.initialised = true;
            this.updateScreen();
        }
    }
    
    @Override
    protected void func_146979_b(final int p_146979_1_, final int p_146979_2_) {
        RenderHelper.disableStandardItemLighting();
        this.drawCenteredString(this.fontRendererObj, I18n.format("tile.beacon.primary", new Object[0]), 62, 10, 14737632);
        this.drawCenteredString(this.fontRendererObj, I18n.format("tile.beacon.secondary", new Object[0]), 169, 10, 14737632);
        for (final GuiButton var4 : this.buttonList) {
            if (var4.func_146115_a()) {
                var4.func_146111_b(p_146979_1_ - this.field_147003_i, p_146979_2_ - this.field_147009_r);
                break;
            }
        }
        RenderHelper.enableGUIStandardItemLighting();
    }
    
    @Override
    protected void func_146976_a(final float p_146976_1_, final int p_146976_2_, final int p_146976_3_) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiBeacon.field_147025_v);
        final int var4 = (this.width - this.field_146999_f) / 2;
        final int var5 = (this.height - this.field_147000_g) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147000_g);
        GuiBeacon.itemRender.zLevel = 100.0f;
        GuiBeacon.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), new ItemStack(Items.emerald), var4 + 42, var5 + 109);
        GuiBeacon.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), new ItemStack(Items.diamond), var4 + 42 + 22, var5 + 109);
        GuiBeacon.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), new ItemStack(Items.gold_ingot), var4 + 42 + 44, var5 + 109);
        GuiBeacon.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), new ItemStack(Items.iron_ingot), var4 + 42 + 66, var5 + 109);
        GuiBeacon.itemRender.zLevel = 0.0f;
    }
    
    static {
        logger = LogManager.getLogger();
        field_147025_v = new ResourceLocation("textures/gui/container/beacon.png");
    }
    
    static class Button extends GuiButton
    {
        private final ResourceLocation field_146145_o;
        private final int field_146144_p;
        private final int field_146143_q;
        private boolean field_146142_r;
        private static final String __OBFID = "CL_00000743";
        
        protected Button(final int p_i1077_1_, final int p_i1077_2_, final int p_i1077_3_, final ResourceLocation p_i1077_4_, final int p_i1077_5_, final int p_i1077_6_) {
            super(p_i1077_1_, p_i1077_2_, p_i1077_3_, 22, 22, "");
            this.field_146145_o = p_i1077_4_;
            this.field_146144_p = p_i1077_5_;
            this.field_146143_q = p_i1077_6_;
        }
        
        @Override
        public void drawButton(final Minecraft p_146112_1_, final int p_146112_2_, final int p_146112_3_) {
            if (this.field_146125_m) {
                p_146112_1_.getTextureManager().bindTexture(GuiBeacon.field_147025_v);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                this.field_146123_n = (p_146112_2_ >= this.x && p_146112_3_ >= this.y && p_146112_2_ < this.x + this.width && p_146112_3_ < this.y + this.height);
                final short var4 = 219;
                int var5 = 0;
                if (!this.enabled) {
                    var5 += this.width * 2;
                }
                else if (this.field_146142_r) {
                    var5 += this.width * 1;
                }
                else if (this.field_146123_n) {
                    var5 += this.width * 3;
                }
                this.drawTexturedModalRect(this.x, this.y, var5, var4, this.width, this.height);
                if (!GuiBeacon.field_147025_v.equals(this.field_146145_o)) {
                    p_146112_1_.getTextureManager().bindTexture(this.field_146145_o);
                }
                this.drawTexturedModalRect(this.x + 2, this.y + 2, this.field_146144_p, this.field_146143_q, 18, 18);
            }
        }
        
        public boolean func_146141_c() {
            return this.field_146142_r;
        }
        
        public void func_146140_b(final boolean p_146140_1_) {
            this.field_146142_r = p_146140_1_;
        }
    }
    
    class CancelButton extends Button
    {
        private static final String __OBFID = "CL_00000740";
        
        public CancelButton(final int p_i1074_2_, final int p_i1074_3_, final int p_i1074_4_) {
            super(p_i1074_2_, p_i1074_3_, p_i1074_4_, GuiBeacon.field_147025_v, 112, 220);
        }
        
        @Override
        public void func_146111_b(final int p_146111_1_, final int p_146111_2_) {
            GuiScreen.this.func_146279_a(I18n.format("gui.cancel", new Object[0]), p_146111_1_, p_146111_2_);
        }
    }
    
    class ConfirmButton extends Button
    {
        private static final String __OBFID = "CL_00000741";
        
        public ConfirmButton(final int p_i1075_2_, final int p_i1075_3_, final int p_i1075_4_) {
            super(p_i1075_2_, p_i1075_3_, p_i1075_4_, GuiBeacon.field_147025_v, 90, 220);
        }
        
        @Override
        public void func_146111_b(final int p_146111_1_, final int p_146111_2_) {
            GuiScreen.this.func_146279_a(I18n.format("gui.done", new Object[0]), p_146111_1_, p_146111_2_);
        }
    }
    
    class PowerButton extends Button
    {
        private final int field_146149_p;
        private final int field_146148_q;
        private static final String __OBFID = "CL_00000742";
        
        public PowerButton(final int p_i1076_2_, final int p_i1076_3_, final int p_i1076_4_, final int p_i1076_5_, final int p_i1076_6_) {
            super(p_i1076_2_, p_i1076_3_, p_i1076_4_, GuiContainer.field_147001_a, 0 + Potion.potionTypes[p_i1076_5_].getStatusIconIndex() % 8 * 18, 198 + Potion.potionTypes[p_i1076_5_].getStatusIconIndex() / 8 * 18);
            this.field_146149_p = p_i1076_5_;
            this.field_146148_q = p_i1076_6_;
        }
        
        @Override
        public void func_146111_b(final int p_146111_1_, final int p_146111_2_) {
            String var3 = I18n.format(Potion.potionTypes[this.field_146149_p].getName(), new Object[0]);
            if (this.field_146148_q >= 3 && this.field_146149_p != Potion.regeneration.id) {
                var3 += " II";
            }
            GuiScreen.this.func_146279_a(var3, p_146111_1_, p_146111_2_);
        }
    }
}
