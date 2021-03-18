package net.minecraft.client.multiplayer;

import java.util.concurrent.atomic.*;
import net.minecraft.client.*;
import net.minecraft.client.network.*;
import net.minecraft.network.handshake.client.*;
import io.netty.util.concurrent.*;
import net.minecraft.network.*;
import net.minecraft.network.login.client.*;
import java.net.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;

public class GuiConnecting extends GuiScreen
{
    private static final AtomicInteger field_146372_a;
    private static final Logger logger;
    private NetworkManager field_146371_g;
    private boolean field_146373_h;
    private final GuiScreen field_146374_i;
    private static final String __OBFID = "CL_00000685";
    
    public GuiConnecting(final GuiScreen p_i1181_1_, final Minecraft p_i1181_2_, final ServerData p_i1181_3_) {
        this.mc = p_i1181_2_;
        this.field_146374_i = p_i1181_1_;
        final ServerAddress var4 = ServerAddress.func_78860_a(p_i1181_3_.serverIP);
        p_i1181_2_.loadWorld(null);
        p_i1181_2_.setServerData(p_i1181_3_);
        this.func_146367_a(var4.getIP(), var4.getPort());
    }
    
    public GuiConnecting(final GuiScreen p_i1182_1_, final Minecraft p_i1182_2_, final String p_i1182_3_, final int p_i1182_4_) {
        this.mc = p_i1182_2_;
        this.field_146374_i = p_i1182_1_;
        p_i1182_2_.loadWorld(null);
        this.func_146367_a(p_i1182_3_, p_i1182_4_);
    }
    
    private void func_146367_a(final String p_146367_1_, final int p_146367_2_) {
        GuiConnecting.logger.info("Connecting to " + p_146367_1_ + ", " + p_146367_2_);
        new Thread("Server Connector #" + GuiConnecting.field_146372_a.incrementAndGet()) {
            private static final String __OBFID = "CL_00000686";
            
            @Override
            public void run() {
                InetAddress var1 = null;
                try {
                    if (GuiConnecting.this.field_146373_h) {
                        return;
                    }
                    var1 = InetAddress.getByName(p_146367_1_);
                    GuiConnecting.this.field_146371_g = NetworkManager.provideLanClient(var1, p_146367_2_);
                    GuiConnecting.this.field_146371_g.setNetHandler(new NetHandlerLoginClient(GuiConnecting.this.field_146371_g, GuiConnecting.this.mc, GuiConnecting.this.field_146374_i));
                    GuiConnecting.this.field_146371_g.scheduleOutboundPacket(new C00Handshake(5, p_146367_1_, p_146367_2_, EnumConnectionState.LOGIN), new GenericFutureListener[0]);
                    GuiConnecting.this.field_146371_g.scheduleOutboundPacket(new C00PacketLoginStart(GuiConnecting.this.mc.getSession().func_148256_e()), new GenericFutureListener[0]);
                }
                catch (UnknownHostException var2) {
                    if (GuiConnecting.this.field_146373_h) {
                        return;
                    }
                    GuiConnecting.logger.error("Couldn't connect to server", (Throwable)var2);
                    GuiConnecting.this.mc.displayGuiScreen(new GuiDisconnected(GuiConnecting.this.field_146374_i, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] { "Unknown host" })));
                }
                catch (Exception var3) {
                    if (GuiConnecting.this.field_146373_h) {
                        return;
                    }
                    GuiConnecting.logger.error("Couldn't connect to server", (Throwable)var3);
                    String var4 = var3.toString();
                    if (var1 != null) {
                        final String var5 = var1.toString() + ":" + p_146367_2_;
                        var4 = var4.replaceAll(var5, "");
                    }
                    GuiConnecting.this.mc.displayGuiScreen(new GuiDisconnected(GuiConnecting.this.field_146374_i, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] { var4 })));
                }
            }
        }.start();
    }
    
    @Override
    public void updateScreen() {
        if (this.field_146371_g != null) {
            if (this.field_146371_g.isChannelOpen()) {
                this.field_146371_g.processReceivedPackets();
            }
            else if (this.field_146371_g.getExitMessage() != null) {
                this.field_146371_g.getNetHandler().onDisconnect(this.field_146371_g.getExitMessage());
            }
        }
    }
    
    @Override
    protected void keyTyped(final char p_73869_1_, final int p_73869_2_) {
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 + 50, I18n.format("gui.cancel", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton p_146284_1_) {
        if (p_146284_1_.id == 0) {
            this.field_146373_h = true;
            if (this.field_146371_g != null) {
                this.field_146371_g.closeChannel(new ChatComponentText("Aborted"));
            }
            this.mc.displayGuiScreen(this.field_146374_i);
        }
    }
    
    @Override
    public void drawScreen(final int p_73863_1_, final int p_73863_2_, final float p_73863_3_) {
        this.drawDefaultBackground();
        if (this.field_146371_g == null) {
            this.drawCenteredString(this.fontRendererObj, I18n.format("connect.connecting", new Object[0]), this.width / 2, this.height / 2 - 50, 16777215);
        }
        else {
            this.drawCenteredString(this.fontRendererObj, I18n.format("connect.authorizing", new Object[0]), this.width / 2, this.height / 2 - 50, 16777215);
        }
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
    
    static {
        field_146372_a = new AtomicInteger(0);
        logger = LogManager.getLogger();
    }
}
