package net.minecraft.client.stream;

import net.minecraft.client.*;
import net.minecraft.client.shader.*;
import com.google.common.collect.*;
import org.apache.logging.log4j.core.helpers.*;
import java.net.*;
import java.io.*;
import com.google.gson.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.settings.*;
import tv.twitch.*;
import tv.twitch.broadcast.*;
import net.minecraft.client.gui.stream.*;
import net.minecraft.event.*;
import java.util.*;
import tv.twitch.chat.*;
import org.apache.logging.log4j.*;
import net.minecraft.util.*;

public class TwitchStream implements BroadcastController.BroadcastListener, ChatController.ChatListener, IngestServerTester.IngestTestListener, IStream
{
    private static final Logger field_152950_b;
    public static final Marker field_152949_a;
    private final BroadcastController field_152951_c;
    private final ChatController field_152952_d;
    private final Minecraft field_152953_e;
    private final IChatComponent field_152954_f;
    private final Map field_152955_g;
    private Framebuffer field_152956_h;
    private boolean field_152957_i;
    private int field_152958_j;
    private long field_152959_k;
    private boolean field_152960_l;
    private boolean field_152961_m;
    private boolean field_152962_n;
    private boolean field_152963_o;
    private AuthFailureReason field_152964_p;
    private static boolean field_152965_q;
    private static final String __OBFID = "CL_00001812";
    
    public TwitchStream(final Minecraft p_i46389_1_, final String p_i46389_2_) {
        this.field_152954_f = new ChatComponentText("Twitch");
        this.field_152955_g = Maps.newHashMap();
        this.field_152958_j = 30;
        this.field_152959_k = 0L;
        this.field_152960_l = false;
        this.field_152964_p = AuthFailureReason.ERROR;
        this.field_152953_e = p_i46389_1_;
        this.field_152951_c = new BroadcastController();
        this.field_152952_d = new ChatController();
        this.field_152951_c.func_152841_a(this);
        this.field_152952_d.func_152990_a(this);
        this.field_152951_c.func_152842_a("nmt37qblda36pvonovdkbopzfzw3wlq");
        this.field_152952_d.func_152984_a("nmt37qblda36pvonovdkbopzfzw3wlq");
        this.field_152954_f.getChatStyle().setColor(EnumChatFormatting.DARK_PURPLE);
        if (Strings.isNotEmpty((CharSequence)p_i46389_2_) && OpenGlHelper.framebufferSupported) {
            final Thread var3 = new Thread("Twitch authenticator") {
                private static final String __OBFID = "CL_00001811";
                
                @Override
                public void run() {
                    try {
                        final URL var1 = new URL("https://api.twitch.tv/kraken?oauth_token=" + URLEncoder.encode(p_i46389_2_, "UTF-8"));
                        final String var2 = HttpUtil.func_152755_a(var1);
                        final JsonObject var3 = JsonUtils.getJsonElementAsJsonObject(new JsonParser().parse(var2), "Response");
                        final JsonObject var4 = JsonUtils.func_152754_s(var3, "token");
                        if (JsonUtils.getJsonObjectBooleanFieldValue(var4, "valid")) {
                            final String var5 = JsonUtils.getJsonObjectStringFieldValue(var4, "user_name");
                            TwitchStream.field_152950_b.debug(TwitchStream.field_152949_a, "Authenticated with twitch; username is {}", new Object[] { var5 });
                            final AuthToken var6 = new AuthToken();
                            var6.data = p_i46389_2_;
                            TwitchStream.this.field_152951_c.func_152818_a(var5, var6);
                            TwitchStream.this.field_152952_d.func_152998_c(var5);
                            TwitchStream.this.field_152952_d.func_152994_a(var6);
                            Runtime.getRuntime().addShutdownHook(new Thread("Twitch shutdown hook") {
                                private static final String __OBFID = "CL_00001810";
                                
                                @Override
                                public void run() {
                                    TwitchStream.this.func_152923_i();
                                }
                            });
                            TwitchStream.this.field_152951_c.func_152817_A();
                        }
                        else {
                            TwitchStream.this.field_152964_p = AuthFailureReason.INVALID_TOKEN;
                            TwitchStream.field_152950_b.error(TwitchStream.field_152949_a, "Given twitch access token is invalid");
                        }
                    }
                    catch (IOException var7) {
                        TwitchStream.this.field_152964_p = AuthFailureReason.ERROR;
                        TwitchStream.field_152950_b.error(TwitchStream.field_152949_a, "Could not authenticate with twitch", (Throwable)var7);
                    }
                }
            };
            var3.setDaemon(true);
            var3.start();
        }
    }
    
    @Override
    public void func_152923_i() {
        TwitchStream.field_152950_b.debug(TwitchStream.field_152949_a, "Shutdown streaming");
        this.field_152951_c.func_152851_B();
        this.field_152952_d.func_152993_m();
    }
    
    @Override
    public void func_152935_j() {
        final int var1 = this.field_152953_e.gameSettings.field_152408_R;
        final ChatController.ChatState var2 = this.field_152952_d.func_153000_j();
        if (var1 == 2) {
            if (var2 == ChatController.ChatState.Connected) {
                TwitchStream.field_152950_b.debug(TwitchStream.field_152949_a, "Disconnecting from twitch chat per user options");
                this.field_152952_d.func_153002_l();
            }
        }
        else if (var1 == 1) {
            if ((var2 == ChatController.ChatState.Disconnected || var2 == ChatController.ChatState.Uninitialized) && this.field_152951_c.func_152849_q()) {
                TwitchStream.field_152950_b.debug(TwitchStream.field_152949_a, "Connecting to twitch chat per user options");
                this.func_152942_I();
            }
        }
        else if (var1 == 0) {
            if ((var2 == ChatController.ChatState.Disconnected || var2 == ChatController.ChatState.Uninitialized) && this.func_152934_n()) {
                TwitchStream.field_152950_b.debug(TwitchStream.field_152949_a, "Connecting to twitch chat as user is streaming");
                this.func_152942_I();
            }
            else if (var2 == ChatController.ChatState.Connected && !this.func_152934_n()) {
                TwitchStream.field_152950_b.debug(TwitchStream.field_152949_a, "Disconnecting from twitch chat as user is no longer streaming");
                this.field_152952_d.func_153002_l();
            }
        }
        this.field_152951_c.func_152821_H();
        this.field_152952_d.func_152997_n();
    }
    
    protected void func_152942_I() {
        final ChatController.ChatState var1 = this.field_152952_d.func_153000_j();
        final String var2 = this.field_152951_c.func_152843_l().name;
        if (var1 == ChatController.ChatState.Uninitialized) {
            this.field_152952_d.func_152985_f(var2);
            this.field_152952_d.field_153005_c = var2;
        }
        else if (var1 == ChatController.ChatState.Disconnected) {
            this.field_152952_d.func_152986_d(var2);
        }
        else {
            TwitchStream.field_152950_b.warn("Invalid twitch chat state {}", new Object[] { var1 });
        }
    }
    
    @Override
    public void func_152922_k() {
        if (this.field_152951_c.func_152850_m() && !this.field_152951_c.func_152839_p()) {
            final long var1 = System.nanoTime();
            final long var2 = 1000000000 / this.field_152958_j;
            final long var3 = var1 - this.field_152959_k;
            final boolean var4 = var3 >= var2;
            if (var4) {
                final FrameBuffer var5 = this.field_152951_c.func_152822_N();
                final Framebuffer var6 = this.field_152953_e.getFramebuffer();
                this.field_152956_h.bindFramebuffer(true);
                GL11.glMatrixMode(5889);
                GL11.glPushMatrix();
                GL11.glLoadIdentity();
                GL11.glOrtho(0.0, (double)this.field_152956_h.framebufferWidth, (double)this.field_152956_h.framebufferHeight, 0.0, 1000.0, 3000.0);
                GL11.glMatrixMode(5888);
                GL11.glPushMatrix();
                GL11.glLoadIdentity();
                GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glViewport(0, 0, this.field_152956_h.framebufferWidth, this.field_152956_h.framebufferHeight);
                GL11.glEnable(3553);
                GL11.glDisable(3008);
                GL11.glDisable(3042);
                final float var7 = (float)this.field_152956_h.framebufferWidth;
                final float var8 = (float)this.field_152956_h.framebufferHeight;
                final float var9 = var6.framebufferWidth / (float)var6.framebufferTextureWidth;
                final float var10 = var6.framebufferHeight / (float)var6.framebufferTextureHeight;
                var6.bindFramebufferTexture();
                GL11.glTexParameterf(3553, 10241, 9729.0f);
                GL11.glTexParameterf(3553, 10240, 9729.0f);
                final Tessellator var11 = Tessellator.instance;
                var11.startDrawingQuads();
                var11.addVertexWithUV(0.0, var8, 0.0, 0.0, var10);
                var11.addVertexWithUV(var7, var8, 0.0, var9, var10);
                var11.addVertexWithUV(var7, 0.0, 0.0, var9, 0.0);
                var11.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
                var11.draw();
                var6.unbindFramebufferTexture();
                GL11.glPopMatrix();
                GL11.glMatrixMode(5889);
                GL11.glPopMatrix();
                GL11.glMatrixMode(5888);
                this.field_152951_c.func_152846_a(var5);
                this.field_152956_h.unbindFramebuffer();
                this.field_152951_c.func_152859_b(var5);
                this.field_152959_k = var1;
            }
        }
    }
    
    @Override
    public boolean func_152936_l() {
        return this.field_152951_c.func_152849_q();
    }
    
    @Override
    public boolean func_152924_m() {
        return this.field_152951_c.func_152857_n();
    }
    
    @Override
    public boolean func_152934_n() {
        return this.field_152951_c.func_152850_m();
    }
    
    @Override
    public void func_152911_a(final Metadata p_152911_1_, final long p_152911_2_) {
        if (this.func_152934_n() && this.field_152957_i) {
            final long var4 = this.field_152951_c.func_152844_x();
            if (!this.field_152951_c.func_152840_a(p_152911_1_.func_152810_c(), var4 + p_152911_2_, p_152911_1_.func_152809_a(), p_152911_1_.func_152806_b())) {
                TwitchStream.field_152950_b.warn(TwitchStream.field_152949_a, "Couldn't send stream metadata action at {}: {}", new Object[] { var4 + p_152911_2_, p_152911_1_ });
            }
            else {
                TwitchStream.field_152950_b.debug(TwitchStream.field_152949_a, "Sent stream metadata action at {}: {}", new Object[] { var4 + p_152911_2_, p_152911_1_ });
            }
        }
    }
    
    @Override
    public boolean func_152919_o() {
        return this.field_152951_c.func_152839_p();
    }
    
    @Override
    public void func_152931_p() {
        if (this.field_152951_c.func_152830_D()) {
            TwitchStream.field_152950_b.debug(TwitchStream.field_152949_a, "Requested commercial from Twitch");
        }
        else {
            TwitchStream.field_152950_b.warn(TwitchStream.field_152949_a, "Could not request commercial from Twitch");
        }
    }
    
    @Override
    public void func_152916_q() {
        this.field_152951_c.func_152847_F();
        this.field_152962_n = true;
        this.func_152915_s();
    }
    
    @Override
    public void func_152933_r() {
        this.field_152951_c.func_152854_G();
        this.field_152962_n = false;
        this.func_152915_s();
    }
    
    @Override
    public void func_152915_s() {
        if (this.func_152934_n()) {
            final float var1 = this.field_152953_e.gameSettings.field_152402_L;
            final boolean var2 = this.field_152962_n || var1 <= 0.0f;
            this.field_152951_c.func_152837_b(var2 ? 0.0f : var1);
            this.field_152951_c.func_152829_a(this.func_152929_G() ? 0.0f : this.field_152953_e.gameSettings.field_152401_K);
        }
    }
    
    @Override
    public void func_152930_t() {
        final GameSettings var1 = this.field_152953_e.gameSettings;
        final VideoParams var2 = this.field_152951_c.func_152834_a(func_152946_b(var1.field_152403_M), func_152948_a(var1.field_152404_N), func_152947_c(var1.field_152400_J), this.field_152953_e.displayWidth / (float)this.field_152953_e.displayHeight);
        switch (var1.field_152405_O) {
            case 0: {
                var2.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_LOW;
                break;
            }
            case 1: {
                var2.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_MEDIUM;
                break;
            }
            case 2: {
                var2.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_HIGH;
                break;
            }
        }
        if (this.field_152956_h == null) {
            this.field_152956_h = new Framebuffer(var2.outputWidth, var2.outputHeight, false);
        }
        else {
            this.field_152956_h.createBindFramebuffer(var2.outputWidth, var2.outputHeight);
        }
        if (var1.field_152407_Q != null && var1.field_152407_Q.length() > 0) {
            for (final IngestServer var6 : this.func_152925_v()) {
                if (var6.serverUrl.equals(var1.field_152407_Q)) {
                    this.field_152951_c.func_152824_a(var6);
                    break;
                }
            }
        }
        this.field_152958_j = var2.targetFps;
        this.field_152957_i = var1.field_152406_P;
        this.field_152951_c.func_152836_a(var2);
        TwitchStream.field_152950_b.info(TwitchStream.field_152949_a, "Streaming at {}/{} at {} kbps to {}", new Object[] { var2.outputWidth, var2.outputHeight, var2.maxKbps, this.field_152951_c.func_152833_s().serverUrl });
        this.field_152951_c.func_152828_a(null, "Minecraft", null);
    }
    
    @Override
    public void func_152914_u() {
        if (this.field_152951_c.func_152819_E()) {
            TwitchStream.field_152950_b.info(TwitchStream.field_152949_a, "Stopped streaming to Twitch");
        }
        else {
            TwitchStream.field_152950_b.warn(TwitchStream.field_152949_a, "Could not stop streaming to Twitch");
        }
    }
    
    @Override
    public void func_152900_a(final ErrorCode p_152900_1_, final AuthToken p_152900_2_) {
    }
    
    @Override
    public void func_152897_a(final ErrorCode p_152897_1_) {
        if (ErrorCode.succeeded(p_152897_1_)) {
            TwitchStream.field_152950_b.debug(TwitchStream.field_152949_a, "Login attempt successful");
            this.field_152961_m = true;
        }
        else {
            TwitchStream.field_152950_b.warn(TwitchStream.field_152949_a, "Login attempt unsuccessful: {} (error code {})", new Object[] { ErrorCode.getString(p_152897_1_), p_152897_1_.getValue() });
            this.field_152961_m = false;
        }
    }
    
    @Override
    public void func_152898_a(final ErrorCode p_152898_1_, final GameInfo[] p_152898_2_) {
    }
    
    @Override
    public void func_152891_a(final BroadcastController.BroadcastState p_152891_1_) {
        TwitchStream.field_152950_b.debug(TwitchStream.field_152949_a, "Broadcast state changed to {}", new Object[] { p_152891_1_ });
        if (p_152891_1_ == BroadcastController.BroadcastState.Initialized) {
            this.field_152951_c.func_152827_a(BroadcastController.BroadcastState.Authenticated);
        }
    }
    
    @Override
    public void func_152895_a() {
        TwitchStream.field_152950_b.info(TwitchStream.field_152949_a, "Logged out of twitch");
    }
    
    @Override
    public void func_152894_a(final StreamInfo p_152894_1_) {
        TwitchStream.field_152950_b.debug(TwitchStream.field_152949_a, "Stream info updated; {} viewers on stream ID {}", new Object[] { p_152894_1_.viewers, p_152894_1_.streamId });
    }
    
    @Override
    public void func_152896_a(final IngestList p_152896_1_) {
    }
    
    @Override
    public void func_152893_b(final ErrorCode p_152893_1_) {
        TwitchStream.field_152950_b.warn(TwitchStream.field_152949_a, "Issue submitting frame: {} (Error code {})", new Object[] { ErrorCode.getString(p_152893_1_), p_152893_1_.getValue() });
        this.field_152953_e.ingameGUI.getChatGUI().func_146234_a(new ChatComponentText("Issue streaming frame: " + p_152893_1_ + " (" + ErrorCode.getString(p_152893_1_) + ")"), 2);
    }
    
    @Override
    public void func_152899_b() {
        this.func_152915_s();
        TwitchStream.field_152950_b.info(TwitchStream.field_152949_a, "Broadcast to Twitch has started");
    }
    
    @Override
    public void func_152901_c() {
        TwitchStream.field_152950_b.info(TwitchStream.field_152949_a, "Broadcast to Twitch has stopped");
    }
    
    @Override
    public void func_152892_c(final ErrorCode p_152892_1_) {
        if (p_152892_1_ == ErrorCode.TTV_EC_SOUNDFLOWER_NOT_INSTALLED) {
            final ChatComponentTranslation var2 = new ChatComponentTranslation("stream.unavailable.soundflower.chat.link", new Object[0]);
            var2.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://help.mojang.com/customer/portal/articles/1374877-configuring-soundflower-for-streaming-on-apple-computers"));
            var2.getChatStyle().setUnderlined(true);
            final ChatComponentTranslation var3 = new ChatComponentTranslation("stream.unavailable.soundflower.chat", new Object[] { var2 });
            var3.getChatStyle().setColor(EnumChatFormatting.DARK_RED);
            this.field_152953_e.ingameGUI.getChatGUI().func_146227_a(var3);
        }
        else {
            final ChatComponentTranslation var2 = new ChatComponentTranslation("stream.unavailable.unknown.chat", new Object[] { ErrorCode.getString(p_152892_1_) });
            var2.getChatStyle().setColor(EnumChatFormatting.DARK_RED);
            this.field_152953_e.ingameGUI.getChatGUI().func_146227_a(var2);
        }
    }
    
    @Override
    public void func_152907_a(final IngestServerTester p_152907_1_, final IngestServerTester.IngestTestState p_152907_2_) {
        TwitchStream.field_152950_b.debug(TwitchStream.field_152949_a, "Ingest test state changed to {}", new Object[] { p_152907_2_ });
        if (p_152907_2_ == IngestServerTester.IngestTestState.Finished) {
            this.field_152960_l = true;
        }
    }
    
    public static int func_152948_a(final float p_152948_0_) {
        return MathHelper.floor_float(10.0f + p_152948_0_ * 50.0f);
    }
    
    public static int func_152946_b(final float p_152946_0_) {
        return MathHelper.floor_float(230.0f + p_152946_0_ * 3270.0f);
    }
    
    public static float func_152947_c(final float p_152947_0_) {
        return 0.1f + p_152947_0_ * 0.1f;
    }
    
    @Override
    public IngestServer[] func_152925_v() {
        return this.field_152951_c.func_152855_t().getServers();
    }
    
    @Override
    public void func_152909_x() {
        final IngestServerTester var1 = this.field_152951_c.func_152838_J();
        if (var1 != null) {
            var1.func_153042_a(this);
        }
    }
    
    @Override
    public IngestServerTester func_152932_y() {
        return this.field_152951_c.func_152856_w();
    }
    
    @Override
    public boolean func_152908_z() {
        return this.field_152951_c.func_152825_o();
    }
    
    @Override
    public int func_152920_A() {
        return this.func_152934_n() ? this.field_152951_c.func_152816_j().viewers : 0;
    }
    
    @Override
    public void func_152903_a(final ChatMessage[] p_152903_1_) {
        final ChatMessage[] var2 = p_152903_1_;
        for (int var3 = p_152903_1_.length, var4 = 0; var4 < var3; ++var4) {
            final ChatMessage var5 = var2[var4];
            this.func_152939_a(var5.userName, var5);
            if (this.func_152940_a(var5.modes, var5.subscriptions, this.field_152953_e.gameSettings.field_152409_S)) {
                final ChatComponentText var6 = new ChatComponentText(var5.userName);
                final ChatComponentTranslation var7 = new ChatComponentTranslation("chat.stream." + (var5.action ? "emote" : "text"), new Object[] { this.field_152954_f, var6, EnumChatFormatting.getTextWithoutFormattingCodes(var5.message) });
                if (var5.action) {
                    var7.getChatStyle().setItalic(true);
                }
                final ChatComponentText var8 = new ChatComponentText("");
                var8.appendSibling(new ChatComponentTranslation("stream.userinfo.chatTooltip", new Object[0]));
                for (final IChatComponent var10 : GuiTwitchUserMode.func_152328_a(var5.modes, var5.subscriptions, null)) {
                    var8.appendText("\n");
                    var8.appendSibling(var10);
                }
                var6.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, var8));
                var6.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.TWITCH_USER_INFO, var5.userName));
                this.field_152953_e.ingameGUI.getChatGUI().func_146227_a(var7);
            }
        }
    }
    
    private void func_152939_a(final String p_152939_1_, final ChatMessage p_152939_2_) {
        ChatUserInfo var3 = this.field_152955_g.get(p_152939_1_);
        if (var3 == null) {
            var3 = new ChatUserInfo();
            var3.displayName = p_152939_1_;
            this.field_152955_g.put(p_152939_1_, var3);
        }
        var3.subscriptions = p_152939_2_.subscriptions;
        var3.modes = p_152939_2_.modes;
        var3.emoticonSet = p_152939_2_.emoticonSet;
        var3.nameColorARGB = p_152939_2_.nameColorARGB;
    }
    
    private boolean func_152940_a(final HashSet p_152940_1_, final HashSet p_152940_2_, final int p_152940_3_) {
        return !p_152940_1_.contains(ChatUserMode.TTV_CHAT_USERMODE_BANNED) && (p_152940_1_.contains(ChatUserMode.TTV_CHAT_USERMODE_ADMINSTRATOR) || p_152940_1_.contains(ChatUserMode.TTV_CHAT_USERMODE_MODERATOR) || p_152940_1_.contains(ChatUserMode.TTV_CHAT_USERMODE_STAFF) || p_152940_3_ == 0 || (p_152940_3_ == 1 && p_152940_2_.contains(ChatUserSubscription.TTV_CHAT_USERSUB_SUBSCRIBER)));
    }
    
    @Override
    public void func_152904_a(final ChatUserInfo[] p_152904_1_, final ChatUserInfo[] p_152904_2_, final ChatUserInfo[] p_152904_3_) {
        ChatUserInfo[] var4 = p_152904_2_;
        for (int var5 = p_152904_2_.length, var6 = 0; var6 < var5; ++var6) {
            final ChatUserInfo var7 = var4[var6];
            this.field_152955_g.remove(var7.displayName);
        }
        var4 = p_152904_3_;
        for (int var5 = p_152904_3_.length, var6 = 0; var6 < var5; ++var6) {
            final ChatUserInfo var7 = var4[var6];
            this.field_152955_g.put(var7.displayName, var7);
        }
        var4 = p_152904_1_;
        for (int var5 = p_152904_1_.length, var6 = 0; var6 < var5; ++var6) {
            final ChatUserInfo var7 = var4[var6];
            this.field_152955_g.put(var7.displayName, var7);
        }
    }
    
    @Override
    public void func_152906_d() {
        TwitchStream.field_152950_b.debug(TwitchStream.field_152949_a, "Chat connected");
    }
    
    @Override
    public void func_152905_e() {
        TwitchStream.field_152950_b.debug(TwitchStream.field_152949_a, "Chat disconnected");
        this.field_152955_g.clear();
    }
    
    @Override
    public void func_152902_f() {
    }
    
    @Override
    public boolean func_152927_B() {
        return this.field_152952_d.func_152991_c() && this.field_152952_d.field_153005_c.equals(this.field_152951_c.func_152843_l().name);
    }
    
    @Override
    public String func_152921_C() {
        return this.field_152952_d.field_153005_c;
    }
    
    @Override
    public ChatUserInfo func_152926_a(final String p_152926_1_) {
        return this.field_152955_g.get(p_152926_1_);
    }
    
    @Override
    public void func_152917_b(final String p_152917_1_) {
        this.field_152952_d.func_152992_g(p_152917_1_);
    }
    
    @Override
    public boolean func_152928_D() {
        return TwitchStream.field_152965_q && this.field_152951_c.func_152858_b();
    }
    
    @Override
    public ErrorCode func_152912_E() {
        return TwitchStream.field_152965_q ? this.field_152951_c.func_152852_P() : ErrorCode.TTV_EC_OS_TOO_OLD;
    }
    
    @Override
    public boolean func_152913_F() {
        return this.field_152961_m;
    }
    
    @Override
    public void func_152910_a(final boolean p_152910_1_) {
        this.field_152963_o = p_152910_1_;
        this.func_152915_s();
    }
    
    @Override
    public boolean func_152929_G() {
        final boolean var1 = this.field_152953_e.gameSettings.field_152410_T == 1;
        return this.field_152962_n || this.field_152953_e.gameSettings.field_152401_K <= 0.0f || var1 != this.field_152963_o;
    }
    
    @Override
    public AuthFailureReason func_152918_H() {
        return this.field_152964_p;
    }
    
    static {
        field_152950_b = LogManager.getLogger();
        field_152949_a = MarkerManager.getMarker("STREAM");
        try {
            if (Util.getOSType() == Util.EnumOS.WINDOWS) {
                System.loadLibrary("avutil-ttv-51");
                System.loadLibrary("swresample-ttv-0");
                System.loadLibrary("libmp3lame-ttv");
                if (System.getProperty("os.arch").contains("64")) {
                    System.loadLibrary("libmfxsw64");
                }
                else {
                    System.loadLibrary("libmfxsw32");
                }
            }
            TwitchStream.field_152965_q = true;
        }
        catch (Throwable var1) {
            TwitchStream.field_152965_q = false;
        }
    }
}
