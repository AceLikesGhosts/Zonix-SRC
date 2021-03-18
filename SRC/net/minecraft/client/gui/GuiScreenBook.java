package net.minecraft.client.gui;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import org.lwjgl.input.*;
import net.minecraft.client.resources.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import io.netty.buffer.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.*;

public class GuiScreenBook extends GuiScreen
{
    private static final Logger logger;
    private static final ResourceLocation field_146466_f;
    private final EntityPlayer field_146468_g;
    private final ItemStack field_146474_h;
    private final boolean field_146475_i;
    private boolean field_146481_r;
    private boolean field_146480_s;
    private int field_146479_t;
    private int field_146478_u;
    private int field_146477_v;
    private int field_146476_w;
    private int field_146484_x;
    private NBTTagList field_146483_y;
    private String field_146482_z;
    private NextPageButton field_146470_A;
    private NextPageButton field_146471_B;
    private GuiButton field_146472_C;
    private GuiButton field_146465_D;
    private GuiButton field_146467_E;
    private GuiButton field_146469_F;
    private static final String __OBFID = "CL_00000744";
    
    public GuiScreenBook(final EntityPlayer p_i1080_1_, final ItemStack p_i1080_2_, final boolean p_i1080_3_) {
        this.field_146478_u = 192;
        this.field_146477_v = 192;
        this.field_146476_w = 1;
        this.field_146482_z = "";
        this.field_146468_g = p_i1080_1_;
        this.field_146474_h = p_i1080_2_;
        this.field_146475_i = p_i1080_3_;
        if (p_i1080_2_.hasTagCompound()) {
            final NBTTagCompound var4 = p_i1080_2_.getTagCompound();
            this.field_146483_y = var4.getTagList("pages", 8);
            if (this.field_146483_y != null) {
                this.field_146483_y = (NBTTagList)this.field_146483_y.copy();
                this.field_146476_w = this.field_146483_y.tagCount();
                if (this.field_146476_w < 1) {
                    this.field_146476_w = 1;
                }
            }
        }
        if (this.field_146483_y == null && p_i1080_3_) {
            (this.field_146483_y = new NBTTagList()).appendTag(new NBTTagString(""));
            this.field_146476_w = 1;
        }
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        ++this.field_146479_t;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        if (this.field_146475_i) {
            this.buttonList.add(this.field_146465_D = new GuiButton(3, this.width / 2 - 100, 4 + this.field_146477_v, 98, 20, I18n.format("book.signButton", new Object[0])));
            this.buttonList.add(this.field_146472_C = new GuiButton(0, this.width / 2 + 2, 4 + this.field_146477_v, 98, 20, I18n.format("gui.done", new Object[0])));
            this.buttonList.add(this.field_146467_E = new GuiButton(5, this.width / 2 - 100, 4 + this.field_146477_v, 98, 20, I18n.format("book.finalizeButton", new Object[0])));
            this.buttonList.add(this.field_146469_F = new GuiButton(4, this.width / 2 + 2, 4 + this.field_146477_v, 98, 20, I18n.format("gui.cancel", new Object[0])));
        }
        else {
            this.buttonList.add(this.field_146472_C = new GuiButton(0, this.width / 2 - 100, 4 + this.field_146477_v, 200, 20, I18n.format("gui.done", new Object[0])));
        }
        final int var1 = (this.width - this.field_146478_u) / 2;
        final byte var2 = 2;
        this.buttonList.add(this.field_146470_A = new NextPageButton(1, var1 + 120, var2 + 154, true));
        this.buttonList.add(this.field_146471_B = new NextPageButton(2, var1 + 38, var2 + 154, false));
        this.func_146464_h();
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    private void func_146464_h() {
        this.field_146470_A.field_146125_m = (!this.field_146480_s && (this.field_146484_x < this.field_146476_w - 1 || this.field_146475_i));
        this.field_146471_B.field_146125_m = (!this.field_146480_s && this.field_146484_x > 0);
        this.field_146472_C.field_146125_m = (!this.field_146475_i || !this.field_146480_s);
        if (this.field_146475_i) {
            this.field_146465_D.field_146125_m = !this.field_146480_s;
            this.field_146469_F.field_146125_m = this.field_146480_s;
            this.field_146467_E.field_146125_m = this.field_146480_s;
            this.field_146467_E.enabled = (this.field_146482_z.trim().length() > 0);
        }
    }
    
    private void func_146462_a(final boolean p_146462_1_) {
        if (this.field_146475_i && this.field_146481_r && this.field_146483_y != null) {
            while (this.field_146483_y.tagCount() > 1) {
                final String var2 = this.field_146483_y.getStringTagAt(this.field_146483_y.tagCount() - 1);
                if (var2.length() != 0) {
                    break;
                }
                this.field_146483_y.removeTag(this.field_146483_y.tagCount() - 1);
            }
            if (this.field_146474_h.hasTagCompound()) {
                final NBTTagCompound var3 = this.field_146474_h.getTagCompound();
                var3.setTag("pages", this.field_146483_y);
            }
            else {
                this.field_146474_h.setTagInfo("pages", this.field_146483_y);
            }
            String var2 = "MC|BEdit";
            if (p_146462_1_) {
                var2 = "MC|BSign";
                this.field_146474_h.setTagInfo("author", new NBTTagString(this.field_146468_g.getCommandSenderName()));
                this.field_146474_h.setTagInfo("title", new NBTTagString(this.field_146482_z.trim()));
                this.field_146474_h.func_150996_a(Items.written_book);
            }
            final ByteBuf var4 = Unpooled.buffer();
            try {
                new PacketBuffer(var4).writeItemStackToBuffer(this.field_146474_h);
                this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload(var2, var4));
            }
            catch (Exception var5) {
                GuiScreenBook.logger.error("Couldn't send book info", (Throwable)var5);
            }
            finally {
                var4.release();
            }
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton p_146284_1_) {
        if (p_146284_1_.enabled) {
            if (p_146284_1_.id == 0) {
                this.mc.displayGuiScreen(null);
                this.func_146462_a(false);
            }
            else if (p_146284_1_.id == 3 && this.field_146475_i) {
                this.field_146480_s = true;
            }
            else if (p_146284_1_.id == 1) {
                if (this.field_146484_x < this.field_146476_w - 1) {
                    ++this.field_146484_x;
                }
                else if (this.field_146475_i) {
                    this.func_146461_i();
                    if (this.field_146484_x < this.field_146476_w - 1) {
                        ++this.field_146484_x;
                    }
                }
            }
            else if (p_146284_1_.id == 2) {
                if (this.field_146484_x > 0) {
                    --this.field_146484_x;
                }
            }
            else if (p_146284_1_.id == 5 && this.field_146480_s) {
                this.func_146462_a(true);
                this.mc.displayGuiScreen(null);
            }
            else if (p_146284_1_.id == 4 && this.field_146480_s) {
                this.field_146480_s = false;
            }
            this.func_146464_h();
        }
    }
    
    private void func_146461_i() {
        if (this.field_146483_y != null && this.field_146483_y.tagCount() < 50) {
            this.field_146483_y.appendTag(new NBTTagString(""));
            ++this.field_146476_w;
            this.field_146481_r = true;
        }
    }
    
    @Override
    protected void keyTyped(final char p_73869_1_, final int p_73869_2_) {
        super.keyTyped(p_73869_1_, p_73869_2_);
        if (this.field_146475_i) {
            if (this.field_146480_s) {
                this.func_146460_c(p_73869_1_, p_73869_2_);
            }
            else {
                this.func_146463_b(p_73869_1_, p_73869_2_);
            }
        }
    }
    
    private void func_146463_b(final char p_146463_1_, final int p_146463_2_) {
        switch (p_146463_1_) {
            case '\u0016': {
                this.func_146459_b(GuiScreen.getClipboardString());
            }
            default: {
                switch (p_146463_2_) {
                    case 14: {
                        final String var3 = this.func_146456_p();
                        if (var3.length() > 0) {
                            this.func_146457_a(var3.substring(0, var3.length() - 1));
                        }
                        return;
                    }
                    case 28:
                    case 156: {
                        this.func_146459_b("\n");
                        return;
                    }
                    default: {
                        if (ChatAllowedCharacters.isAllowedCharacter(p_146463_1_)) {
                            this.func_146459_b(Character.toString(p_146463_1_));
                        }
                        return;
                    }
                }
                break;
            }
        }
    }
    
    private void func_146460_c(final char p_146460_1_, final int p_146460_2_) {
        switch (p_146460_2_) {
            case 14: {
                if (!this.field_146482_z.isEmpty()) {
                    this.field_146482_z = this.field_146482_z.substring(0, this.field_146482_z.length() - 1);
                    this.func_146464_h();
                }
            }
            case 28:
            case 156: {
                if (!this.field_146482_z.isEmpty()) {
                    this.func_146462_a(true);
                    this.mc.displayGuiScreen(null);
                }
            }
            default: {
                if (this.field_146482_z.length() < 16 && ChatAllowedCharacters.isAllowedCharacter(p_146460_1_)) {
                    this.field_146482_z += Character.toString(p_146460_1_);
                    this.func_146464_h();
                    this.field_146481_r = true;
                }
            }
        }
    }
    
    private String func_146456_p() {
        return (this.field_146483_y != null && this.field_146484_x >= 0 && this.field_146484_x < this.field_146483_y.tagCount()) ? this.field_146483_y.getStringTagAt(this.field_146484_x) : "";
    }
    
    private void func_146457_a(final String p_146457_1_) {
        if (this.field_146483_y != null && this.field_146484_x >= 0 && this.field_146484_x < this.field_146483_y.tagCount()) {
            this.field_146483_y.func_150304_a(this.field_146484_x, new NBTTagString(p_146457_1_));
            this.field_146481_r = true;
        }
    }
    
    private void func_146459_b(final String p_146459_1_) {
        final String var2 = this.func_146456_p();
        final String var3 = var2 + p_146459_1_;
        final int var4 = this.fontRendererObj.splitStringWidth(var3 + "" + EnumChatFormatting.BLACK + "_", 118);
        if (var4 <= 118 && var3.length() < 256) {
            this.func_146457_a(var3);
        }
    }
    
    @Override
    public void drawScreen(final int p_73863_1_, final int p_73863_2_, final float p_73863_3_) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiScreenBook.field_146466_f);
        final int var4 = (this.width - this.field_146478_u) / 2;
        final byte var5 = 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.field_146478_u, this.field_146477_v);
        if (this.field_146480_s) {
            String var6 = this.field_146482_z;
            if (this.field_146475_i) {
                if (this.field_146479_t / 6 % 2 == 0) {
                    var6 = var6 + "" + EnumChatFormatting.BLACK + "_";
                }
                else {
                    var6 = var6 + "" + EnumChatFormatting.GRAY + "_";
                }
            }
            final String var7 = I18n.format("book.editTitle", new Object[0]);
            final int var8 = this.fontRendererObj.getStringWidth(var7);
            this.fontRendererObj.drawString(var7, var4 + 36 + (116 - var8) / 2, var5 + 16 + 16, 0);
            final int var9 = this.fontRendererObj.getStringWidth(var6);
            this.fontRendererObj.drawString(var6, var4 + 36 + (116 - var9) / 2, var5 + 48, 0);
            final String var10 = I18n.format("book.byAuthor", this.field_146468_g.getCommandSenderName());
            final int var11 = this.fontRendererObj.getStringWidth(var10);
            this.fontRendererObj.drawString(EnumChatFormatting.DARK_GRAY + var10, var4 + 36 + (116 - var11) / 2, var5 + 48 + 10, 0);
            final String var12 = I18n.format("book.finalizeWarning", new Object[0]);
            this.fontRendererObj.drawSplitString(var12, var4 + 36, var5 + 80, 116, 0);
        }
        else {
            final String var6 = I18n.format("book.pageIndicator", this.field_146484_x + 1, this.field_146476_w);
            String var7 = "";
            if (this.field_146483_y != null && this.field_146484_x >= 0 && this.field_146484_x < this.field_146483_y.tagCount()) {
                var7 = this.field_146483_y.getStringTagAt(this.field_146484_x);
            }
            if (this.field_146475_i) {
                if (this.fontRendererObj.getBidiFlag()) {
                    var7 += "_";
                }
                else if (this.field_146479_t / 6 % 2 == 0) {
                    var7 = var7 + "" + EnumChatFormatting.BLACK + "_";
                }
                else {
                    var7 = var7 + "" + EnumChatFormatting.GRAY + "_";
                }
            }
            final int var8 = this.fontRendererObj.getStringWidth(var6);
            this.fontRendererObj.drawString(var6, var4 - var8 + this.field_146478_u - 44, var5 + 16, 0);
            this.fontRendererObj.drawSplitString(var7, var4 + 36, var5 + 16 + 16, 116, 0);
        }
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
    
    static {
        logger = LogManager.getLogger();
        field_146466_f = new ResourceLocation("textures/gui/book.png");
    }
    
    static class NextPageButton extends GuiButton
    {
        private final boolean field_146151_o;
        private static final String __OBFID = "CL_00000745";
        
        public NextPageButton(final int p_i46316_1_, final int p_i46316_2_, final int p_i46316_3_, final boolean p_i46316_4_) {
            super(p_i46316_1_, p_i46316_2_, p_i46316_3_, 23, 13, "");
            this.field_146151_o = p_i46316_4_;
        }
        
        @Override
        public void drawButton(final Minecraft p_146112_1_, final int p_146112_2_, final int p_146112_3_) {
            if (this.field_146125_m) {
                final boolean var4 = p_146112_2_ >= this.x && p_146112_3_ >= this.y && p_146112_2_ < this.x + this.width && p_146112_3_ < this.y + this.height;
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                p_146112_1_.getTextureManager().bindTexture(GuiScreenBook.field_146466_f);
                int var5 = 0;
                int var6 = 192;
                if (var4) {
                    var5 += 23;
                }
                if (!this.field_146151_o) {
                    var6 += 13;
                }
                this.drawTexturedModalRect(this.x, this.y, var5, var6, 23, 13);
            }
        }
    }
}
