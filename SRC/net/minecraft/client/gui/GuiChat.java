package net.minecraft.client.gui;

import org.lwjgl.input.*;
import java.net.*;
import java.io.*;
import net.minecraft.client.gui.stream.*;
import tv.twitch.chat.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.event.*;
import net.minecraft.item.*;
import com.google.common.base.*;
import net.minecraft.util.*;
import java.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.nbt.*;
import net.minecraft.stats.*;
import org.apache.commons.lang3.*;
import com.google.common.collect.*;
import org.apache.logging.log4j.*;

public class GuiChat extends GuiScreen implements GuiYesNoCallback
{
    private static final Set field_152175_f;
    private static final Logger logger;
    private String field_146410_g;
    private int field_146416_h;
    private boolean field_146417_i;
    private boolean field_146414_r;
    private int field_146413_s;
    private List field_146412_t;
    private URI field_146411_u;
    protected GuiTextField field_146415_a;
    private String field_146409_v;
    private static final String __OBFID = "CL_00000682";
    
    public GuiChat() {
        this.field_146410_g = "";
        this.field_146416_h = -1;
        this.field_146412_t = new ArrayList();
        this.field_146409_v = "";
    }
    
    public GuiChat(final String p_i1024_1_) {
        this.field_146410_g = "";
        this.field_146416_h = -1;
        this.field_146412_t = new ArrayList();
        this.field_146409_v = "";
        this.field_146409_v = p_i1024_1_;
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.field_146416_h = this.mc.ingameGUI.getChatGUI().func_146238_c().size();
        (this.field_146415_a = new GuiTextField(this.fontRendererObj, 4, this.height - 12, this.width - 4, 12)).func_146203_f(100);
        this.field_146415_a.func_146185_a(false);
        this.field_146415_a.setFocused(true);
        this.field_146415_a.setText(this.field_146409_v);
        this.field_146415_a.func_146205_d(false);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        this.mc.ingameGUI.getChatGUI().resetScroll();
    }
    
    @Override
    public void updateScreen() {
        this.field_146415_a.updateCursorCounter();
    }
    
    @Override
    protected void keyTyped(final char p_73869_1_, final int p_73869_2_) {
        this.field_146414_r = false;
        if (p_73869_2_ == 15) {
            this.func_146404_p_();
        }
        else {
            this.field_146417_i = false;
        }
        if (p_73869_2_ == 1) {
            this.mc.displayGuiScreen(null);
        }
        else if (p_73869_2_ != 28 && p_73869_2_ != 156) {
            if (p_73869_2_ == 200) {
                this.func_146402_a(-1);
            }
            else if (p_73869_2_ == 208) {
                this.func_146402_a(1);
            }
            else if (p_73869_2_ == 201) {
                this.mc.ingameGUI.getChatGUI().func_146229_b(this.mc.ingameGUI.getChatGUI().func_146232_i() - 1);
            }
            else if (p_73869_2_ == 209) {
                this.mc.ingameGUI.getChatGUI().func_146229_b(-this.mc.ingameGUI.getChatGUI().func_146232_i() + 1);
            }
            else {
                this.field_146415_a.textboxKeyTyped(p_73869_1_, p_73869_2_);
            }
        }
        else {
            final String var3 = this.field_146415_a.getText().trim();
            if (var3.length() > 0) {
                this.func_146403_a(var3);
            }
            this.mc.displayGuiScreen(null);
        }
    }
    
    public void func_146403_a(final String p_146403_1_) {
        this.mc.ingameGUI.getChatGUI().func_146239_a(p_146403_1_);
        this.mc.thePlayer.sendChatMessage(p_146403_1_);
    }
    
    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        int var1 = Mouse.getEventDWheel();
        if (var1 != 0) {
            if (var1 > 1) {
                var1 = 1;
            }
            if (var1 < -1) {
                var1 = -1;
            }
            if (!GuiScreen.isShiftKeyDown()) {
                var1 *= 7;
            }
            this.mc.ingameGUI.getChatGUI().func_146229_b(var1);
        }
    }
    
    @Override
    protected void mouseClicked(final int p_73864_1_, final int p_73864_2_, final int p_73864_3_) {
        if (p_73864_3_ == 0 && this.mc.gameSettings.chatLinks) {
            final IChatComponent var4 = this.mc.ingameGUI.getChatGUI().func_146236_a(Mouse.getX(), Mouse.getY());
            if (var4 != null) {
                final ClickEvent var5 = var4.getChatStyle().getChatClickEvent();
                if (var5 != null) {
                    if (isShiftKeyDown()) {
                        this.field_146415_a.func_146191_b(var4.getUnformattedTextForChat());
                    }
                    else if (var5.getAction() == ClickEvent.Action.OPEN_URL) {
                        try {
                            final URI var6 = new URI(var5.getValue());
                            if (!GuiChat.field_152175_f.contains(var6.getScheme().toLowerCase())) {
                                throw new URISyntaxException(var5.getValue(), "Unsupported protocol: " + var6.getScheme().toLowerCase());
                            }
                            if (this.mc.gameSettings.chatLinksPrompt) {
                                this.field_146411_u = var6;
                                this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, var5.getValue(), 0, false));
                            }
                            else {
                                this.func_146407_a(var6);
                            }
                        }
                        catch (URISyntaxException var7) {
                            GuiChat.logger.error("Can't open url for " + var5, (Throwable)var7);
                        }
                    }
                    else if (var5.getAction() == ClickEvent.Action.OPEN_FILE) {
                        final URI var6 = new File(var5.getValue()).toURI();
                        this.func_146407_a(var6);
                    }
                    else if (var5.getAction() == ClickEvent.Action.SUGGEST_COMMAND) {
                        this.field_146415_a.setText(var5.getValue());
                    }
                    else if (var5.getAction() == ClickEvent.Action.RUN_COMMAND) {
                        this.func_146403_a(var5.getValue());
                    }
                    else if (var5.getAction() == ClickEvent.Action.TWITCH_USER_INFO) {
                        final ChatUserInfo var8 = this.mc.func_152346_Z().func_152926_a(var5.getValue());
                        if (var8 != null) {
                            this.mc.displayGuiScreen(new GuiTwitchUserMode(this.mc.func_152346_Z(), var8));
                        }
                        else {
                            GuiChat.logger.error("Tried to handle twitch user but couldn't find them!");
                        }
                    }
                    else {
                        GuiChat.logger.error("Don't know how to handle " + var5);
                    }
                    return;
                }
            }
        }
        this.field_146415_a.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
    }
    
    @Override
    public void confirmClicked(final boolean p_73878_1_, final int p_73878_2_) {
        if (p_73878_2_ == 0) {
            if (p_73878_1_) {
                this.func_146407_a(this.field_146411_u);
            }
            this.field_146411_u = null;
            this.mc.displayGuiScreen(this);
        }
    }
    
    private void func_146407_a(final URI p_146407_1_) {
        try {
            final Class var2 = Class.forName("java.awt.Desktop");
            final Object var3 = var2.getMethod("getDesktop", (Class[])new Class[0]).invoke(null, new Object[0]);
            var2.getMethod("browse", URI.class).invoke(var3, p_146407_1_);
        }
        catch (Throwable var4) {
            GuiChat.logger.error("Couldn't open link", var4);
        }
    }
    
    public void func_146404_p_() {
        if (this.field_146417_i) {
            this.field_146415_a.func_146175_b(this.field_146415_a.func_146197_a(-1, this.field_146415_a.func_146198_h(), false) - this.field_146415_a.func_146198_h());
            if (this.field_146413_s >= this.field_146412_t.size()) {
                this.field_146413_s = 0;
            }
        }
        else {
            final int var1 = this.field_146415_a.func_146197_a(-1, this.field_146415_a.func_146198_h(), false);
            this.field_146412_t.clear();
            this.field_146413_s = 0;
            final String var2 = this.field_146415_a.getText().substring(var1).toLowerCase();
            final String var3 = this.field_146415_a.getText().substring(0, this.field_146415_a.func_146198_h());
            this.func_146405_a(var3, var2);
            if (this.field_146412_t.isEmpty()) {
                return;
            }
            this.field_146417_i = true;
            this.field_146415_a.func_146175_b(var1 - this.field_146415_a.func_146198_h());
        }
        if (this.field_146412_t.size() > 1) {
            final StringBuilder var4 = new StringBuilder();
            for (final String var3 : this.field_146412_t) {
                if (var4.length() > 0) {
                    var4.append(", ");
                }
                var4.append(var3);
            }
            this.mc.ingameGUI.getChatGUI().func_146234_a(new ChatComponentText(var4.toString()), 1);
        }
        this.field_146415_a.func_146191_b(this.field_146412_t.get(this.field_146413_s++));
    }
    
    private void func_146405_a(final String p_146405_1_, final String p_146405_2_) {
        if (p_146405_1_.length() >= 1) {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C14PacketTabComplete(p_146405_1_));
            this.field_146414_r = true;
        }
    }
    
    public void func_146402_a(final int p_146402_1_) {
        int var2 = this.field_146416_h + p_146402_1_;
        final int var3 = this.mc.ingameGUI.getChatGUI().func_146238_c().size();
        if (var2 < 0) {
            var2 = 0;
        }
        if (var2 > var3) {
            var2 = var3;
        }
        if (var2 != this.field_146416_h) {
            if (var2 == var3) {
                this.field_146416_h = var3;
                this.field_146415_a.setText(this.field_146410_g);
            }
            else {
                if (this.field_146416_h == var3) {
                    this.field_146410_g = this.field_146415_a.getText();
                }
                this.field_146415_a.setText(this.mc.ingameGUI.getChatGUI().func_146238_c().get(var2));
                this.field_146416_h = var2;
            }
        }
    }
    
    @Override
    public void drawScreen(final int p_73863_1_, final int p_73863_2_, final float p_73863_3_) {
        Gui.drawRect(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
        this.field_146415_a.drawTextBox();
        final IChatComponent var4 = this.mc.ingameGUI.getChatGUI().func_146236_a(Mouse.getX(), Mouse.getY());
        if (var4 != null && var4.getChatStyle().getChatHoverEvent() != null) {
            final HoverEvent var5 = var4.getChatStyle().getChatHoverEvent();
            if (var5.getAction() == HoverEvent.Action.SHOW_ITEM) {
                ItemStack var6 = null;
                try {
                    final NBTBase var7 = JsonToNBT.func_150315_a(var5.getValue().getUnformattedText());
                    if (var7 != null && var7 instanceof NBTTagCompound) {
                        var6 = ItemStack.loadItemStackFromNBT((NBTTagCompound)var7);
                    }
                }
                catch (NBTException ex) {}
                if (var6 != null) {
                    this.func_146285_a(var6, p_73863_1_, p_73863_2_);
                }
                else {
                    this.func_146279_a(EnumChatFormatting.RED + "Invalid Item!", p_73863_1_, p_73863_2_);
                }
            }
            else if (var5.getAction() == HoverEvent.Action.SHOW_TEXT) {
                this.func_146283_a(Splitter.on("\n").splitToList((CharSequence)var5.getValue().getFormattedText()), p_73863_1_, p_73863_2_);
            }
            else if (var5.getAction() == HoverEvent.Action.SHOW_ACHIEVEMENT) {
                final StatBase var8 = StatList.func_151177_a(var5.getValue().getUnformattedText());
                if (var8 != null) {
                    final IChatComponent var9 = var8.func_150951_e();
                    final ChatComponentTranslation var10 = new ChatComponentTranslation("stats.tooltip.type." + (var8.isAchievement() ? "achievement" : "statistic"), new Object[0]);
                    var10.getChatStyle().setItalic(true);
                    final String var11 = (var8 instanceof Achievement) ? ((Achievement)var8).getDescription() : null;
                    final ArrayList var12 = Lists.newArrayList((Object[])new String[] { var9.getFormattedText(), var10.getFormattedText() });
                    if (var11 != null) {
                        var12.addAll(this.fontRendererObj.listFormattedStringToWidth(var11, 150));
                    }
                    this.func_146283_a(var12, p_73863_1_, p_73863_2_);
                }
                else {
                    this.func_146279_a(EnumChatFormatting.RED + "Invalid statistic/achievement!", p_73863_1_, p_73863_2_);
                }
            }
            GL11.glDisable(2896);
        }
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
    
    public void func_146406_a(final String[] p_146406_1_) {
        if (this.field_146414_r) {
            this.field_146417_i = false;
            this.field_146412_t.clear();
            final String[] var2 = p_146406_1_;
            for (int var3 = p_146406_1_.length, var4 = 0; var4 < var3; ++var4) {
                final String var5 = var2[var4];
                if (var5.length() > 0) {
                    this.field_146412_t.add(var5);
                }
            }
            final String var6 = this.field_146415_a.getText().substring(this.field_146415_a.func_146197_a(-1, this.field_146415_a.func_146198_h(), false));
            final String var7 = StringUtils.getCommonPrefix(p_146406_1_);
            if (var7.length() > 0 && !var6.equalsIgnoreCase(var7)) {
                this.field_146415_a.func_146175_b(this.field_146415_a.func_146197_a(-1, this.field_146415_a.func_146198_h(), false) - this.field_146415_a.func_146198_h());
                this.field_146415_a.func_146191_b(var7);
            }
            else if (this.field_146412_t.size() > 0) {
                this.field_146417_i = true;
                this.func_146404_p_();
            }
        }
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    static {
        field_152175_f = Sets.newHashSet((Object[])new String[] { "http", "https" });
        logger = LogManager.getLogger();
    }
}
