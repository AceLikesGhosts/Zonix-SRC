package net.minecraft.client.gui;

import net.minecraft.client.network.*;
import net.minecraft.client.resources.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import java.net.*;
import java.util.*;
import us.zonix.client.*;
import org.lwjgl.input.*;
import us.zonix.client.util.*;
import org.lwjgl.opengl.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.awt.*;
import net.minecraft.client.multiplayer.*;
import com.google.common.util.concurrent.*;
import java.util.concurrent.*;
import org.apache.logging.log4j.*;

public class GuiMultiplayer extends GuiScreen implements GuiYesNoCallback
{
    private static final ResourceLocation[] BARS;
    private static final ResourceLocation[] PINNED;
    private static final ResourceLocation PLAY_BUTTON;
    private static final ThreadPoolExecutor serverPingExecutor;
    private static final Logger logger;
    private final OldServerPinger field_146797_f;
    private GuiScreen field_146798_g;
    private ServerSelectionList field_146803_h;
    private ServerList field_146804_i;
    private GuiButton field_146810_r;
    private GuiButton field_146809_s;
    private GuiButton field_146808_t;
    private boolean field_146807_u;
    private boolean field_146806_v;
    private boolean field_146805_w;
    private boolean field_146813_x;
    private String field_146812_y;
    private ServerData field_146811_z;
    private LanServerDetector.LanServerList field_146799_A;
    private LanServerDetector.ThreadLanServerFind field_146800_B;
    private boolean field_146801_C;
    private static final String __OBFID = "CL_00000814";
    public static boolean multiPlayerOpen;
    private boolean initialised;
    private boolean updating;
    private long lastUpdateTime;
    private int scrollAmount;
    private ServerData hoveringData;
    private ServerData selectedData;
    private ServerData deleting;
    private long lastSelectTime;
    private int mousePressY;
    
    public GuiMultiplayer(final GuiScreen p_i1040_1_) {
        this.field_146797_f = new OldServerPinger();
        this.field_146798_g = p_i1040_1_;
    }
    
    @Override
    public void initGui() {
        GuiMultiplayer.multiPlayerOpen = true;
        this.scrollAmount = 0;
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        if (!this.field_146801_C) {
            this.field_146801_C = true;
            (this.field_146804_i = new ServerList(this.mc)).loadServerList();
            this.field_146799_A = new LanServerDetector.LanServerList();
            try {
                (this.field_146800_B = new LanServerDetector.ThreadLanServerFind(this.field_146799_A)).start();
            }
            catch (Exception var2) {
                GuiMultiplayer.logger.warn("Unable to start LAN server detection: " + var2.getMessage());
            }
            (this.field_146803_h = new ServerSelectionList(this, this.mc, this.width, this.height, 32, this.height - 64, 36)).func_148195_a(this.field_146804_i);
        }
        else {
            this.field_146803_h.func_148122_a(this.width, this.height, 32, this.height - 64);
        }
    }
    
    public void func_146794_g() {
        this.buttonList.add(this.field_146810_r = new GuiButton(7, this.width / 2 - 154, this.height - 28, 70, 20, I18n.format("selectServer.edit", new Object[0])));
        this.buttonList.add(this.field_146808_t = new GuiButton(2, this.width / 2 - 74, this.height - 28, 70, 20, I18n.format("selectServer.delete", new Object[0])));
        this.buttonList.add(this.field_146809_s = new GuiButton(1, this.width / 2 - 154, this.height - 52, 100, 20, I18n.format("selectServer.select", new Object[0])));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 50, this.height - 52, 100, 20, I18n.format("selectServer.direct", new Object[0])));
        this.buttonList.add(new GuiButton(3, this.width / 2 + 4 + 50, this.height - 52, 100, 20, I18n.format("selectServer.add", new Object[0])));
        this.buttonList.add(new GuiButton(8, this.width / 2 + 4, this.height - 28, 70, 20, I18n.format("selectServer.refresh", new Object[0])));
        this.buttonList.add(new GuiButton(0, this.width / 2 + 4 + 76, this.height - 28, 75, 20, I18n.format("gui.cancel", new Object[0])));
        this.func_146790_a(this.field_146803_h.func_148193_k());
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        if (this.field_146799_A.getWasUpdated()) {
            final List var1 = this.field_146799_A.getLanServers();
            this.field_146799_A.setWasNotUpdated();
            this.field_146803_h.func_148194_a(var1);
        }
        this.field_146797_f.func_147223_a();
        if (!this.updating && this.lastUpdateTime + 5000L < System.currentTimeMillis()) {
            this.lastUpdateTime = System.currentTimeMillis();
            this.updating = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final List<ServerData> allServers = new LinkedList<ServerData>();
                    allServers.addAll(GuiMultiplayer.this.field_146804_i.pinnedServers);
                    allServers.addAll(GuiMultiplayer.this.field_146804_i.servers);
                    final List<ServerData> pinged = new ArrayList<ServerData>();
                    for (final ServerData serverData : allServers) {
                        GuiMultiplayer.serverPingExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (!GuiMultiplayer.this.initialised) {
                                        GuiMultiplayer.this.func_146789_i().init(serverData);
                                    }
                                    else {
                                        GuiMultiplayer.this.func_146789_i().func_147224_a(serverData, null, null);
                                    }
                                }
                                catch (UnknownHostException e) {
                                    serverData.serverMOTD = EnumChatFormatting.DARK_RED + "Can't resolve hostname";
                                    serverData.pingToServer = -1L;
                                }
                                catch (Exception e2) {
                                    serverData.serverMOTD = EnumChatFormatting.DARK_RED + "Can't connect to server.";
                                    serverData.pingToServer = -1L;
                                }
                                pinged.add(serverData);
                            }
                        });
                    }
                    while (pinged.size() < allServers.size()) {
                        try {
                            Thread.sleep(100L);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    GuiMultiplayer.this.initialised = true;
                    GuiMultiplayer.this.updating = false;
                }
            }).start();
        }
    }
    
    @Override
    public void onGuiClosed() {
        GuiMultiplayer.multiPlayerOpen = false;
        this.field_146803_h.close();
        Keyboard.enableRepeatEvents(false);
        if (this.field_146800_B != null) {
            this.field_146800_B.interrupt();
            this.field_146800_B = null;
        }
        this.field_146797_f.func_147226_b();
    }
    
    @Override
    protected void actionPerformed(final GuiButton p_146284_1_) {
    }
    
    private void func_146792_q() {
        this.mc.displayGuiScreen(new GuiMultiplayer(this.field_146798_g));
    }
    
    @Override
    public void confirmClicked(final boolean p_73878_1_, final int p_73878_2_) {
        final GuiListExtended.IGuiListEntry var3 = (this.field_146803_h.func_148193_k() < 0) ? null : this.field_146803_h.func_148180_b(this.field_146803_h.func_148193_k());
        if (this.field_146807_u) {
            this.field_146807_u = false;
            if (this.deleting != null) {
                this.field_146804_i.servers.remove(this.deleting);
                this.field_146804_i.saveServerList();
            }
            this.mc.displayGuiScreen(this);
        }
        else if (this.field_146813_x) {
            this.field_146813_x = false;
            if (p_73878_1_) {
                this.func_146791_a(this.field_146811_z);
            }
            else {
                this.mc.displayGuiScreen(this);
            }
        }
        else if (this.field_146806_v) {
            this.field_146806_v = false;
            if (p_73878_1_) {
                this.field_146804_i.addServerData(this.field_146811_z);
                this.field_146804_i.saveServerList();
                this.field_146803_h.func_148192_c(-1);
                this.field_146803_h.func_148195_a(this.field_146804_i);
            }
            this.mc.displayGuiScreen(this);
        }
        else if (this.field_146805_w) {
            this.field_146805_w = false;
            this.mc.displayGuiScreen(this);
        }
    }
    
    @Override
    protected void keyTyped(final char p_73869_1_, final int p_73869_2_) {
        final int var3 = this.field_146803_h.func_148193_k();
        final GuiListExtended.IGuiListEntry var4 = (var3 < 0) ? null : this.field_146803_h.func_148180_b(var3);
        if (p_73869_2_ == 63) {
            this.func_146792_q();
        }
        else if (var3 >= 0) {
            if (p_73869_2_ == 200) {
                if (!GuiScreen.isShiftKeyDown()) {
                    if (var3 > 0) {
                        this.func_146790_a(this.field_146803_h.func_148193_k() - 1);
                        this.field_146803_h.func_148145_f(-this.field_146803_h.func_148146_j());
                        if (this.field_146803_h.func_148180_b(this.field_146803_h.func_148193_k()) instanceof ServerListEntryLanScan) {
                            if (this.field_146803_h.func_148193_k() > 0) {
                                this.func_146790_a(this.field_146803_h.getSize() - 1);
                                this.field_146803_h.func_148145_f(-this.field_146803_h.func_148146_j());
                            }
                            else {
                                this.func_146790_a(-1);
                            }
                        }
                    }
                    else {
                        this.func_146790_a(-1);
                    }
                }
            }
            else if (p_73869_2_ == 208) {
                if (isShiftKeyDown()) {
                    if (var3 < this.field_146804_i.countServers() - 1) {
                        this.field_146804_i.swapServers(var3, var3 + 1);
                        this.func_146790_a(var3 + 1);
                        this.field_146803_h.func_148145_f(this.field_146803_h.func_148146_j());
                        this.field_146803_h.func_148195_a(this.field_146804_i);
                    }
                }
                else if (var3 < this.field_146803_h.getSize()) {
                    this.func_146790_a(this.field_146803_h.func_148193_k() + 1);
                    this.field_146803_h.func_148145_f(this.field_146803_h.func_148146_j());
                    if (this.field_146803_h.func_148180_b(this.field_146803_h.func_148193_k()) instanceof ServerListEntryLanScan) {
                        if (this.field_146803_h.func_148193_k() < this.field_146803_h.getSize() - 1) {
                            this.func_146790_a(this.field_146803_h.getSize() + 1);
                            this.field_146803_h.func_148145_f(this.field_146803_h.func_148146_j());
                        }
                        else {
                            this.func_146790_a(-1);
                        }
                    }
                }
                else {
                    this.func_146790_a(-1);
                }
            }
            else if (p_73869_2_ != 28 && p_73869_2_ != 156) {
                super.keyTyped(p_73869_1_, p_73869_2_);
            }
            else {
                this.actionPerformed(this.buttonList.get(2));
            }
        }
        else {
            super.keyTyped(p_73869_1_, p_73869_2_);
        }
    }
    
    private String[] split(final String[] lines, final float width) {
        final List<String> list = new ArrayList<String>();
        for (final String line : lines) {
            StringBuilder builder = new StringBuilder();
            for (final String s : line.split(" ")) {
                final String temp = builder.toString() + " " + s;
                if (Client.getInstance().getRegularFontRenderer().getStringWidth(temp) >= width) {
                    list.add(builder.toString());
                    builder = new StringBuilder();
                }
                if (builder.length() > 0) {
                    builder.append(" ");
                }
                builder.append(s);
            }
            if (builder.length() > 0) {
                list.add(builder.toString());
            }
        }
        return list.toArray(new String[0]);
    }
    
    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        final int scroll = Mouse.getEventDWheel();
        if (scroll == 0) {
            return;
        }
        this.scroll(scroll);
    }
    
    private void scroll(final int scroll) {
        final int before = this.scrollAmount;
        this.scrollAmount += scroll;
        if (this.scrollAmount > 0) {
            this.scrollAmount = 0;
        }
        final ScaledResolution resolution = new ScaledResolution(this.mc);
        final List<ServerData> serverDataList = new LinkedList<ServerData>();
        serverDataList.addAll(this.field_146804_i.servers);
        final float maxY = resolution.getScaledHeight() - 59.0f;
        final float translate = this.scrollAmount / 10.0f;
        float startY = 130.0f + translate;
        boolean move = false;
        for (int i = 0; i < serverDataList.size(); ++i) {
            if (startY + 60.0f > maxY) {
                move = true;
                break;
            }
            startY += 60.0f;
        }
        if (!move) {
            this.scrollAmount = before;
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final ScaledResolution resolution = new ScaledResolution(this.mc);
        this.field_146812_y = null;
        RenderUtil.drawRect(0.0f, 0.0f, (float)resolution.getScaledWidth(), (float)resolution.getScaledHeight(), -16250872);
        RenderUtil.drawRect(0.0f, 0.0f, (float)resolution.getScaledWidth(), 30.0f, -14541283);
        RenderUtil.drawRect(0.0f, (float)resolution.getScaledHeight(), (float)resolution.getScaledWidth(), resolution.getScaledHeight() - 50.0f, -14541283);
        final float pinnedWidth = 70.0f;
        float startX = resolution.getScaledWidth() / 2 - 155.0f;
        int i = 0;
        for (final ServerData serverData : this.field_146804_i.pinnedServers) {
            GL11.glPushMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            RenderUtil.drawSquareTexture(GuiMultiplayer.PINNED[i++], pinnedWidth, 75.0f, startX, 45.0f);
            GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.4f);
            RenderUtil.drawSquareTexture(GuiMultiplayer.PLAY_BUTTON, 10.0f, startX + 24.5f, 93.75f);
            final Color color = serverData.tintColor;
            GL11.glColor4f((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 0.4f);
            RenderUtil.drawSquareTexture(GuiMultiplayer.PLAY_BUTTON, 10.0f, startX + 25.0f, 93.0f);
            GL11.glPopMatrix();
            final int boxMiddle = (int)(startX + pinnedWidth / 2.0f);
            RenderUtil.drawCenteredString(Client.getInstance().getHugeBoldFontRenderer(), serverData.serverName.replace("Zonix ", ""), (float)boxMiddle, 60.0f, -1);
            final String ping = (serverData.pingToServer < 0L) ? "?" : String.valueOf(serverData.pingToServer);
            RenderUtil.drawCenteredString(Client.getInstance().getTinyFontRenderer(), "Ping: " + ping + "MS", (float)boxMiddle, 73.0f, -1);
            String population;
            if (serverData.populationInfo == null) {
                population = "?";
            }
            else {
                population = EnumChatFormatting.getTextWithoutFormattingCodes(serverData.populationInfo.split("/")[0]);
            }
            RenderUtil.drawCenteredString(Client.getInstance().getTinyFontRenderer(), "Players: " + population, (float)boxMiddle, 82.0f, -1);
            startX += pinnedWidth + 10.0f;
        }
        final List<ServerData> serverDataList = new LinkedList<ServerData>();
        serverDataList.addAll(this.field_146804_i.servers);
        final float boxWidth = 300.0f;
        float startY = 130.0f;
        float minX = resolution.getScaledWidth() / 2 - boxWidth / 2.0f;
        final float maxY = resolution.getScaledHeight() - 59.0f;
        RenderUtil.startScissorBox(startY, maxY, 0.0f, (float)resolution.getScaledWidth());
        GL11.glPushMatrix();
        final float currentMaxY = startY + serverDataList.size() * 50.0f + (serverDataList.size() - 1) * 10.0f;
        if (currentMaxY > maxY) {
            final float translate = this.scrollAmount / 10.0f;
            startY += translate;
        }
        else {
            this.scrollAmount = 0;
        }
        this.hoveringData = null;
        for (final ServerData pinnedServer : serverDataList) {
            final FontRenderer fontRenderer = this.mc.fontRenderer;
            if (this.selectedData == pinnedServer) {
                RenderUtil.drawRoundedRect(minX, startY, minX + boxWidth, startY + 48.0f, 10.0, -1457249244);
            }
            fontRenderer.drawString(pinnedServer.serverName, (int)minX + 10, (int)startY + 7, -1);
            if (mouseX >= minX && mouseX <= minX + boxWidth && mouseY >= startY && mouseY <= startY + 50.0f) {
                this.hoveringData = pinnedServer;
            }
            int bars;
            if (pinnedServer.pingToServer < 0L) {
                bars = -1;
            }
            else if (pinnedServer.pingToServer < 100L) {
                bars = 4;
            }
            else if (pinnedServer.pingToServer < 200L) {
                bars = 3;
            }
            else if (pinnedServer.pingToServer < 350L) {
                bars = 2;
            }
            else if (pinnedServer.pingToServer < 500L) {
                bars = 1;
            }
            else {
                bars = 0;
            }
            if (bars != -1) {
                RenderUtil.drawTexture(GuiMultiplayer.BARS[bars], minX + boxWidth - 20.0f, startY + 7.0f, 10.5f, 7.5f);
            }
            final String players = EnumChatFormatting.getTextWithoutFormattingCodes(pinnedServer.populationInfo);
            fontRenderer.drawString(players, (int)(minX + boxWidth - 23.0f - fontRenderer.getStringWidth(players)), (int)(startY + 7.0f), -3421237);
            if (pinnedServer.serverMOTD != null) {
                String[] lines = pinnedServer.serverMOTD.split("\n");
                final float stringWidth = (float)fontRenderer.getStringWidth(pinnedServer.serverMOTD);
                if (stringWidth + 10.0f > boxWidth - 15.0f) {
                    lines = this.split(lines, boxWidth - 15.0f);
                }
                for (int j = 0; j < lines.length && j <= 1; ++j) {
                    fontRenderer.drawString(lines[j], (int)(minX + 10.0f), (int)(startY + 20.0f + 12 * j), -3421237);
                }
            }
            else {
                fontRenderer.drawString("Pinging...", (int)(minX + 10.0f), (int)(startY + 20.0f), -3421237);
            }
            startY += 50.0f;
        }
        GL11.glPopMatrix();
        RenderUtil.endScissorBox();
        if (this.mousePressY != -1 && this.mousePressY != mouseY) {
            this.scroll((this.mousePressY - mouseY) * -10);
            this.mousePressY = mouseY;
        }
        RenderUtil.drawRoundedRect(resolution.getScaledWidth() / 2 - 55.0f, 5.0, resolution.getScaledWidth() / 2 + 55.0f, 25.0, 5.0, -6538948);
        RenderUtil.drawCenteredString(Client.getInstance().getMediumBoldFontRenderer(), "MULTIPLAYER", (float)(resolution.getScaledWidth() / 2), 15.0f, -524289);
        final float buttonHeight = 35.0f;
        final float buttonWidth = 100.0f;
        final float startMinX = minX = resolution.getScaledWidth() / 2 - (buttonWidth + 12.5f) / 2.0f * 3.0f;
        startY = resolution.getScaledHeight() - buttonHeight - 10.0f;
        for (int k = 0; k < 6; ++k) {
            int color2 = -12109771;
            int bColor = -7782075;
            if ((k == 0 || k == 3 || k == 4) && this.selectedData == null) {
                color2 = -12500928;
                bColor = -11382190;
            }
            RenderUtil.drawBorderedRoundedRect(minX, startY, minX + buttonWidth, startY + buttonHeight / 2.0f, 10.0f, 2.0f, bColor, color2);
            String text = null;
            switch (k) {
                case 0: {
                    text = "JOIN SERVER";
                    break;
                }
                case 1: {
                    text = "DIRECT CONNECT";
                    break;
                }
                case 2: {
                    text = "ADD SERVER";
                    break;
                }
                case 3: {
                    text = "EDIT";
                    break;
                }
                case 4: {
                    text = "DELETE";
                    break;
                }
                default: {
                    text = "CANCEL";
                    break;
                }
            }
            RenderUtil.drawCenteredString(Client.getInstance().getRegularBoldFontRenderer(), text, (float)(int)(minX + buttonWidth / 2.0f), (float)((int)(startY + buttonHeight / 4.0f) + 1), -1);
            minX += buttonWidth + 20.0f;
            if (k == 2) {
                startY += buttonHeight / 2.0f + 4.0f;
                minX = startMinX;
            }
        }
        if (this.field_146812_y != null) {
            this.func_146283_a(Lists.newArrayList(Splitter.on("\n").split((CharSequence)this.field_146812_y)), mouseX, mouseY);
        }
    }
    
    public void func_146796_h() {
    }
    
    private void func_146791_a(final ServerData p_146791_1_) {
        if (this.field_146798_g instanceof GuiIngameMenu) {
            if (this.mc.theWorld != null) {
                this.mc.theWorld.sendQuittingDisconnectingPacket();
            }
            this.mc.loadWorld(null);
        }
        this.mc.displayGuiScreen(new GuiConnecting(this, this.mc, p_146791_1_));
    }
    
    public void func_146790_a(final int p_146790_1_) {
        this.field_146803_h.func_148192_c(p_146790_1_);
        final GuiListExtended.IGuiListEntry var2 = (p_146790_1_ < 0) ? null : this.field_146803_h.func_148180_b(p_146790_1_);
        this.field_146809_s.enabled = false;
        this.field_146810_r.enabled = false;
        this.field_146808_t.enabled = false;
        if (var2 != null && !(var2 instanceof ServerListEntryLanScan)) {
            this.field_146809_s.enabled = true;
        }
    }
    
    public OldServerPinger func_146789_i() {
        return this.field_146797_f;
    }
    
    public void func_146793_a(final String p_146793_1_) {
        this.field_146812_y = p_146793_1_;
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (button > 1) {
            return;
        }
        final ServerData selectedData = this.selectedData;
        this.selectedData = null;
        final ScaledResolution resolution = new ScaledResolution(this.mc);
        final float buttonHeight = 35.0f;
        final float buttonWidth = 100.0f;
        float minX;
        final float startMinX = minX = resolution.getScaledWidth() / 2 - (buttonWidth + 12.5f) / 2.0f * 3.0f;
        float startY = resolution.getScaledHeight() - buttonHeight - 10.0f;
        for (int j = 0; j < 6; ++j) {
            if (mouseX >= minX && mouseX <= minX + buttonWidth && mouseY >= startY && mouseY <= startY + buttonHeight / 2.0f) {
                switch (j) {
                    case 0: {
                        if (selectedData != null) {
                            this.func_146791_a(selectedData);
                            break;
                        }
                        break;
                    }
                    case 1: {
                        this.field_146813_x = true;
                        this.field_146811_z = new ServerData("Minecraft Server", "");
                        this.mc.displayGuiScreen(new GuiScreenServerList(this, this.field_146811_z));
                        break;
                    }
                    case 2: {
                        this.field_146806_v = true;
                        this.field_146811_z = new ServerData("Minecraft Server", "");
                        this.mc.displayGuiScreen(new GuiScreenAddServer(this, this.field_146811_z));
                        break;
                    }
                    case 3: {
                        if (selectedData != null) {
                            this.field_146805_w = true;
                            (this.field_146811_z = new ServerData(selectedData.serverName, selectedData.serverIP)).func_152583_a(selectedData);
                            this.mc.displayGuiScreen(new GuiScreenAddServer(this, this.field_146811_z));
                            break;
                        }
                        break;
                    }
                    case 4: {
                        if (selectedData != null) {
                            final String var9 = selectedData.serverName;
                            if (var9 != null) {
                                this.field_146807_u = true;
                                final String var10 = "Are you sure you want to remove this server?";
                                final String var11 = "'" + var9 + "' will be lost forever! (A long time!)";
                                final String var12 = "Delete";
                                final String var13 = "Cancel";
                                final int id = this.field_146803_h.func_148193_k();
                                final GuiYesNo var14 = new GuiYesNo(this, var10, var11, var12, var13, id);
                                this.mc.displayGuiScreen(var14);
                                this.deleting = selectedData;
                            }
                            break;
                        }
                        break;
                    }
                    case 5: {
                        this.mc.displayGuiScreen(this.field_146798_g);
                        break;
                    }
                }
                return;
            }
            minX += buttonWidth + 20.0f;
            if (j == 2) {
                startY += buttonHeight / 2.0f + 4.0f;
                minX = startMinX;
            }
        }
        final float pinnedWidth = 70.0f;
        float startX = resolution.getScaledWidth() / 2 - 155.0f;
        for (final ServerData serverData : this.field_146804_i.pinnedServers) {
            if (mouseX >= startX + 25.0f && mouseX <= startX + 45.0f && mouseY >= 93.0f && mouseY <= 113.0f) {
                this.func_146791_a(serverData);
                return;
            }
            startX += pinnedWidth + 10.0f;
        }
        if (this.hoveringData != null) {
            if (selectedData == this.hoveringData && this.lastSelectTime + 500L > System.currentTimeMillis()) {
                this.func_146791_a(selectedData);
                return;
            }
            this.lastSelectTime = System.currentTimeMillis();
            this.selectedData = this.hoveringData;
        }
        this.mousePressY = mouseY;
    }
    
    @Override
    protected void mouseMovedOrUp(final int mouseX, final int mouseY, final int button) {
        this.mousePressY = -1;
    }
    
    public ServerList func_146795_p() {
        return this.field_146804_i;
    }
    
    static {
        BARS = new ResourceLocation[] { new ResourceLocation("icon/bars/bars_1.png"), new ResourceLocation("icon/bars/bars_2.png"), new ResourceLocation("icon/bars/bars_3.png"), new ResourceLocation("icon/bars/bars_4.png"), new ResourceLocation("icon/bars/bars_5.png") };
        PINNED = new ResourceLocation[] { new ResourceLocation("pinned/red.png"), new ResourceLocation("pinned/green.png"), new ResourceLocation("pinned/blue.jpg"), new ResourceLocation("pinned/pink.png") };
        PLAY_BUTTON = new ResourceLocation("icon/play.png");
        serverPingExecutor = new ScheduledThreadPoolExecutor(5, new ThreadFactoryBuilder().setNameFormat("Server Pinger #%d").setDaemon(true).build());
        logger = LogManager.getLogger();
    }
}
