package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import java.net.*;
import net.minecraft.network.*;
import net.minecraft.client.multiplayer.*;
import us.zonix.client.util.*;
import org.lwjgl.opengl.*;
import java.util.*;
import com.google.common.base.*;
import io.netty.handler.codec.base64.*;
import javax.imageio.*;
import java.io.*;
import org.apache.commons.lang3.*;
import net.minecraft.client.renderer.texture.*;
import io.netty.buffer.*;
import java.awt.image.*;
import org.apache.logging.log4j.*;
import com.google.common.util.concurrent.*;
import java.util.concurrent.*;

public class ServerListEntryPinned implements GuiListExtended.IGuiListEntry
{
    private static final ResourceLocation pinnedIcon;
    private static final Logger logger;
    private static final ThreadPoolExecutor field_148302_b;
    private final GuiMultiplayer field_148303_c;
    private final Minecraft mc;
    private final ServerData field_148301_e;
    private long field_148298_f;
    private String field_148299_g;
    private DynamicTexture field_148305_h;
    private ResourceLocation field_148306_i;
    private static final String __OBFID = "CL_00000817";
    public boolean running;
    
    protected ServerListEntryPinned(final GuiMultiplayer p_i45048_1_, final ServerData p_i45048_2_) {
        this.field_148303_c = p_i45048_1_;
        this.field_148301_e = p_i45048_2_;
        this.mc = Minecraft.getMinecraft();
        this.field_148306_i = new ResourceLocation("servers/" + p_i45048_2_.serverIP + "/icon");
        this.field_148305_h = (DynamicTexture)this.mc.getTextureManager().getTexture(this.field_148306_i);
    }
    
    @Override
    public void func_148279_a(final int p_148279_1_, final int p_148279_2_, final int p_148279_3_, final int p_148279_4_, final int p_148279_5_, final Tessellator p_148279_6_, final int p_148279_7_, final int p_148279_8_, final boolean p_148279_9_) {
        if (!this.field_148301_e.field_78841_f) {
            this.field_148301_e.field_78841_f = true;
            this.field_148301_e.pingToServer = -2L;
            this.field_148301_e.serverMOTD = "";
            this.field_148301_e.populationInfo = "";
            ServerListEntryPinned.field_148302_b.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        ServerListEntryPinned.this.field_148303_c.func_146789_i().init(ServerListEntryPinned.this.field_148301_e);
                    }
                    catch (UnknownHostException var2) {
                        ServerListEntryPinned.this.field_148301_e.pingToServer = -1L;
                        ServerListEntryPinned.this.field_148301_e.serverMOTD = EnumChatFormatting.DARK_RED + "Can't resolve hostname";
                    }
                    catch (Exception var3) {
                        ServerListEntryPinned.this.field_148301_e.pingToServer = -1L;
                        ServerListEntryPinned.this.field_148301_e.serverMOTD = EnumChatFormatting.DARK_RED + "Can't connect to server.";
                    }
                    ServerListEntryPinned.this.running = true;
                    while (ServerListEntryPinned.this.running) {
                        try {
                            ServerListEntryPinned.this.field_148303_c.func_146789_i().func_147224_a(ServerListEntryPinned.this.field_148301_e, null, null);
                        }
                        catch (UnknownHostException var2) {
                            ServerListEntryPinned.this.field_148301_e.pingToServer = -1L;
                            ServerListEntryPinned.this.field_148301_e.serverMOTD = EnumChatFormatting.DARK_RED + "Can't resolve hostname";
                        }
                        catch (Exception ex) {}
                        try {
                            Thread.sleep(3500L);
                            continue;
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            });
        }
        RenderUtil.drawSquareTexture(ServerListEntryPinned.pinnedIcon, 8.0f, (float)(p_148279_2_ - 32), (float)(p_148279_3_ + 1));
        final boolean var10 = this.field_148301_e.field_82821_f > 5;
        final boolean var11 = this.field_148301_e.field_82821_f < 5;
        final boolean var12 = var10 || var11;
        this.mc.fontRenderer.drawString(this.field_148301_e.serverName, p_148279_2_ + 32 + 3, p_148279_3_ + 1, 16777215);
        final List var13 = this.mc.fontRenderer.listFormattedStringToWidth(this.field_148301_e.serverMOTD, p_148279_4_ - 32 - 2);
        for (int var14 = 0; var14 < Math.min(var13.size(), 2); ++var14) {
            this.mc.fontRenderer.drawString(var13.get(var14), p_148279_2_ + 32 + 3, p_148279_3_ + 12 + this.mc.fontRenderer.FONT_HEIGHT * var14, 8421504);
        }
        final String var15 = var12 ? (EnumChatFormatting.DARK_RED + this.field_148301_e.gameVersion) : this.field_148301_e.populationInfo;
        final int var16 = this.mc.fontRenderer.getStringWidth(var15);
        this.mc.fontRenderer.drawString(var15, p_148279_2_ + p_148279_4_ - var16 - 15 - 2, p_148279_3_ + 1, 8421504);
        byte var17 = 0;
        String var18 = null;
        int var19;
        String var20;
        if (var12) {
            var19 = 5;
            var20 = (var10 ? "Client out of date!" : "Server out of date!");
            var18 = this.field_148301_e.field_147412_i;
        }
        else if (this.field_148301_e.field_78841_f && this.field_148301_e.pingToServer != -2L) {
            if (this.field_148301_e.pingToServer < 0L) {
                var19 = 5;
            }
            else if (this.field_148301_e.pingToServer < 150L) {
                var19 = 0;
            }
            else if (this.field_148301_e.pingToServer < 300L) {
                var19 = 1;
            }
            else if (this.field_148301_e.pingToServer < 600L) {
                var19 = 2;
            }
            else if (this.field_148301_e.pingToServer < 1000L) {
                var19 = 3;
            }
            else {
                var19 = 4;
            }
            if (this.field_148301_e.pingToServer < 0L) {
                var20 = "(no connection)";
            }
            else {
                var20 = this.field_148301_e.pingToServer + "ms";
                var18 = this.field_148301_e.field_147412_i;
            }
        }
        else {
            var17 = 1;
            var19 = (int)(Minecraft.getSystemTime() / 100L + p_148279_1_ * 2 & 0x7L);
            if (var19 > 4) {
                var19 = 8 - var19;
            }
            var20 = "Pinging...";
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(Gui.icons);
        Gui.func_146110_a(p_148279_2_ + p_148279_4_ - 15, p_148279_3_, (float)(var17 * 10), (float)(176 + var19 * 8), 10, 8, 256.0f, 256.0f);
        if (this.field_148301_e.func_147409_e() != null && !this.field_148301_e.func_147409_e().equals(this.field_148299_g)) {
            this.field_148299_g = this.field_148301_e.func_147409_e();
            this.func_148297_b();
            this.field_148303_c.func_146795_p().saveServerList();
        }
        if (this.field_148305_h != null) {
            this.mc.getTextureManager().bindTexture(this.field_148306_i);
            Gui.func_146110_a(p_148279_2_, p_148279_3_, 0.0f, 0.0f, 32, 32, 32.0f, 32.0f);
        }
        final int var21 = p_148279_7_ - p_148279_2_;
        final int var22 = p_148279_8_ - p_148279_3_;
        if (var21 >= p_148279_4_ - 15 && var21 <= p_148279_4_ - 5 && var22 >= 0 && var22 <= 8) {
            this.field_148303_c.func_146793_a(var20);
        }
        else if (var21 >= p_148279_4_ - var16 - 15 - 2 && var21 <= p_148279_4_ - 15 - 2 && var22 >= 0 && var22 <= 8) {
            this.field_148303_c.func_146793_a(var18);
        }
    }
    
    private void func_148297_b() {
        if (this.field_148301_e.func_147409_e() == null) {
            this.mc.getTextureManager().func_147645_c(this.field_148306_i);
            this.field_148305_h = null;
        }
        else {
            final ByteBuf var2 = Unpooled.copiedBuffer((CharSequence)this.field_148301_e.func_147409_e(), Charsets.UTF_8);
            final ByteBuf var3 = Base64.decode(var2);
            BufferedImage var4 = null;
            Label_0219: {
                try {
                    var4 = ImageIO.read((InputStream)new ByteBufInputStream(var3));
                    Validate.validState(var4.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
                    Validate.validState(var4.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
                    break Label_0219;
                }
                catch (Exception var5) {
                    ServerListEntryPinned.logger.error("Invalid icon for server " + this.field_148301_e.serverName + " (" + this.field_148301_e.serverIP + ")", (Throwable)var5);
                    this.field_148301_e.func_147407_a(null);
                }
                finally {
                    var2.release();
                    var3.release();
                }
                return;
            }
            if (this.field_148305_h == null) {
                this.field_148305_h = new DynamicTexture(var4.getWidth(), var4.getHeight());
                this.mc.getTextureManager().loadTexture(this.field_148306_i, this.field_148305_h);
            }
            var4.getRGB(0, 0, var4.getWidth(), var4.getHeight(), this.field_148305_h.getTextureData(), 0, var4.getWidth());
            this.field_148305_h.updateDynamicTexture();
        }
    }
    
    @Override
    public boolean func_148278_a(final int p_148278_1_, final int p_148278_2_, final int p_148278_3_, final int p_148278_4_, final int p_148278_5_, final int p_148278_6_) {
        this.field_148303_c.func_146790_a(p_148278_1_);
        if (Minecraft.getSystemTime() - this.field_148298_f < 250L) {
            this.field_148303_c.func_146796_h();
        }
        this.field_148298_f = Minecraft.getSystemTime();
        return false;
    }
    
    @Override
    public void func_148277_b(final int p_148277_1_, final int p_148277_2_, final int p_148277_3_, final int p_148277_4_, final int p_148277_5_, final int p_148277_6_) {
    }
    
    public ServerData func_148296_a() {
        return this.field_148301_e;
    }
    
    static {
        pinnedIcon = new ResourceLocation("icon/pin.png");
        logger = LogManager.getLogger();
        field_148302_b = new ScheduledThreadPoolExecutor(5, new ThreadFactoryBuilder().setNameFormat("Server Pinger #%d").setDaemon(true).build());
    }
}
