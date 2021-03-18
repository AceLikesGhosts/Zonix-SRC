package net.minecraft.client.network;

import net.minecraft.client.multiplayer.*;
import java.net.*;
import net.minecraft.network.status.*;
import org.apache.commons.lang3.*;
import net.minecraft.client.*;
import io.netty.util.concurrent.*;
import com.mojang.authlib.*;
import net.minecraft.network.status.server.*;
import net.minecraft.network.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.network.status.client.*;
import io.netty.bootstrap.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import io.netty.channel.socket.nio.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class OldServerPinger
{
    private static final Splitter field_147230_a;
    private static final Logger logger;
    private final List field_147229_c;
    private static final String __OBFID = "CL_00000892";
    
    public OldServerPinger() {
        this.field_147229_c = Collections.synchronizedList(new ArrayList<Object>());
    }
    
    public void init(final ServerData p_147224_1_) throws UnknownHostException {
        final ServerAddress var2 = ServerAddress.func_78860_a(p_147224_1_.serverIP);
        final NetworkManager var3 = NetworkManager.provideLanClient(InetAddress.getByName(var2.getIP()), var2.getPort());
        this.field_147229_c.add(var3);
        p_147224_1_.serverMOTD = "Pinging...";
        p_147224_1_.pingToServer = -1L;
        p_147224_1_.field_147412_i = null;
        this.func_147224_a(p_147224_1_, var3, var2);
    }
    
    public void func_147224_a(final ServerData p_147224_1_, NetworkManager var3, ServerAddress var2) throws UnknownHostException {
        if (var2 == null) {
            var2 = ServerAddress.func_78860_a(p_147224_1_.serverIP);
        }
        if (var3 == null) {
            var3 = NetworkManager.provideLanClient(InetAddress.getByName(var2.getIP()), var2.getPort());
        }
        final NetworkManager finalVar = var3;
        var3.setNetHandler(new INetHandlerStatusClient() {
            private boolean field_147403_d = false;
            private static final String __OBFID = "CL_00000893";
            
            @Override
            public void handleServerInfo(final S00PacketServerInfo p_147397_1_) {
                final ServerStatusResponse var2 = p_147397_1_.func_149294_c();
                if (var2.func_151317_a() != null) {
                    p_147224_1_.serverMOTD = var2.func_151317_a().getFormattedText();
                }
                else {
                    p_147224_1_.serverMOTD = "";
                }
                if (var2.func_151322_c() != null) {
                    p_147224_1_.gameVersion = var2.func_151322_c().func_151303_a();
                    p_147224_1_.field_82821_f = var2.func_151322_c().func_151304_b();
                }
                else {
                    p_147224_1_.gameVersion = "Old";
                    p_147224_1_.field_82821_f = 0;
                }
                if (var2.func_151318_b() != null) {
                    p_147224_1_.populationInfo = EnumChatFormatting.GRAY + "" + var2.func_151318_b().func_151333_b() + "" + EnumChatFormatting.DARK_GRAY + "/" + EnumChatFormatting.GRAY + var2.func_151318_b().func_151332_a();
                    if (ArrayUtils.isNotEmpty((Object[])var2.func_151318_b().func_151331_c())) {
                        final StringBuilder var3x = new StringBuilder();
                        for (final GameProfile var6 : var2.func_151318_b().func_151331_c()) {
                            if (var3x.length() > 0) {
                                var3x.append("\n");
                            }
                            var3x.append(var6.getName());
                        }
                        if (var2.func_151318_b().func_151331_c().length < var2.func_151318_b().func_151333_b()) {
                            if (var3x.length() > 0) {
                                var3x.append("\n");
                            }
                            var3x.append("... and ").append(var2.func_151318_b().func_151333_b() - var2.func_151318_b().func_151331_c().length).append(" more ...");
                        }
                        p_147224_1_.field_147412_i = var3x.toString();
                    }
                }
                else {
                    p_147224_1_.populationInfo = EnumChatFormatting.DARK_GRAY + "???";
                }
                if (var2.func_151316_d() != null) {
                    final String var7 = var2.func_151316_d();
                    if (var7.startsWith("data:image/png;base64,")) {
                        p_147224_1_.func_147407_a(var7.substring("data:image/png;base64,".length()));
                    }
                    else {
                        OldServerPinger.logger.error("Invalid server icon (unknown format)");
                    }
                }
                else {
                    p_147224_1_.func_147407_a(null);
                }
                finalVar.scheduleOutboundPacket(new C01PacketPing(Minecraft.getSystemTime()), new GenericFutureListener[0]);
                this.field_147403_d = true;
            }
            
            @Override
            public void handlePong(final S01PacketPong p_147398_1_) {
                final long var2 = p_147398_1_.func_149292_c();
                final long var3 = Minecraft.getSystemTime();
                p_147224_1_.pingToServer = var3 - var2;
                finalVar.closeChannel(new ChatComponentText("Finished"));
            }
            
            @Override
            public void onDisconnect(final IChatComponent p_147231_1_) {
                if (!this.field_147403_d) {
                    OldServerPinger.logger.error("Can't ping " + p_147224_1_.serverIP + ": " + p_147231_1_.getUnformattedText());
                    p_147224_1_.serverMOTD = EnumChatFormatting.DARK_RED + "Can't connect to server.";
                    p_147224_1_.populationInfo = "";
                    OldServerPinger.this.func_147225_b(p_147224_1_);
                }
            }
            
            @Override
            public void onConnectionStateTransition(final EnumConnectionState p_147232_1_, final EnumConnectionState p_147232_2_) {
                if (p_147232_2_ != EnumConnectionState.STATUS) {
                    throw new UnsupportedOperationException("Unexpected change in protocol to " + p_147232_2_);
                }
            }
            
            @Override
            public void onNetworkTick() {
            }
        });
        try {
            var3.scheduleOutboundPacket(new C00Handshake(5, var2.getIP(), var2.getPort(), EnumConnectionState.STATUS), new GenericFutureListener[0]);
            var3.scheduleOutboundPacket(new C00PacketServerQuery(), new GenericFutureListener[0]);
        }
        catch (Throwable var4) {
            OldServerPinger.logger.error((Object)var4);
        }
    }
    
    private void func_147225_b(final ServerData p_147225_1_) {
        final ServerAddress var2 = ServerAddress.func_78860_a(p_147225_1_.serverIP);
        ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)NetworkManager.eventLoops)).handler((ChannelHandler)new ChannelInitializer() {
            private static final String __OBFID = "CL_00000894";
            
            protected void initChannel(final Channel p_initChannel_1_) {
                try {
                    p_initChannel_1_.config().setOption(ChannelOption.IP_TOS, (Object)24);
                }
                catch (ChannelException ex) {}
                try {
                    p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, (Object)true);
                }
                catch (ChannelException ex2) {}
                p_initChannel_1_.pipeline().addLast(new ChannelHandler[] { (ChannelHandler)new SimpleChannelInboundHandler() {
                        private static final String __OBFID = "CL_00000895";
                        
                        public void channelActive(final ChannelHandlerContext p_channelActive_1_) throws Exception {
                            super.channelActive(p_channelActive_1_);
                            final ByteBuf var2x = Unpooled.buffer();
                            try {
                                var2x.writeByte(254);
                                var2x.writeByte(1);
                                var2x.writeByte(250);
                                char[] var3 = "MC|PingHost".toCharArray();
                                var2x.writeShort(var3.length);
                                char[] var4 = var3;
                                for (int var5 = var3.length, var6 = 0; var6 < var5; ++var6) {
                                    final char var7 = var4[var6];
                                    var2x.writeChar((int)var7);
                                }
                                var2x.writeShort(7 + 2 * var2.getIP().length());
                                var2x.writeByte(127);
                                var3 = var2.getIP().toCharArray();
                                var2x.writeShort(var3.length);
                                var4 = var3;
                                for (int var5 = var3.length, var6 = 0; var6 < var5; ++var6) {
                                    final char var7 = var4[var6];
                                    var2x.writeChar((int)var7);
                                }
                                var2x.writeInt(var2.getPort());
                                p_channelActive_1_.channel().writeAndFlush((Object)var2x).addListener((GenericFutureListener)ChannelFutureListener.CLOSE_ON_FAILURE);
                            }
                            finally {
                                var2x.release();
                            }
                        }
                        
                        protected void channelRead0(final ChannelHandlerContext p_channelRead0_1_, final ByteBuf p_channelRead0_2_) {
                            final short var3 = p_channelRead0_2_.readUnsignedByte();
                            if (var3 == 255) {
                                final String var4 = new String(p_channelRead0_2_.readBytes(p_channelRead0_2_.readShort() * 2).array(), Charsets.UTF_16BE);
                                final String[] var5 = (String[])Iterables.toArray(OldServerPinger.field_147230_a.split((CharSequence)var4), (Class)String.class);
                                if ("§1".equals(var5[0])) {
                                    final int var6 = MathHelper.parseIntWithDefault(var5[1], 0);
                                    final String var7 = var5[2];
                                    final String var8 = var5[3];
                                    final int var9 = MathHelper.parseIntWithDefault(var5[4], -1);
                                    final int var10 = MathHelper.parseIntWithDefault(var5[5], -1);
                                    p_147225_1_.field_82821_f = -1;
                                    p_147225_1_.gameVersion = var7;
                                    p_147225_1_.serverMOTD = var8;
                                    p_147225_1_.populationInfo = EnumChatFormatting.GRAY + "" + var9 + "" + EnumChatFormatting.DARK_GRAY + "/" + EnumChatFormatting.GRAY + var10;
                                }
                            }
                            p_channelRead0_1_.close();
                        }
                        
                        public void exceptionCaught(final ChannelHandlerContext p_exceptionCaught_1_, final Throwable p_exceptionCaught_2_) {
                            p_exceptionCaught_1_.close();
                        }
                        
                        protected void channelRead0(final ChannelHandlerContext p_channelRead0_1_, final Object p_channelRead0_2_) {
                            this.channelRead0(p_channelRead0_1_, (ByteBuf)p_channelRead0_2_);
                        }
                    } });
            }
        })).channel((Class)NioSocketChannel.class)).connect(var2.getIP(), var2.getPort());
    }
    
    public void func_147223_a() {
        final List var1 = this.field_147229_c;
        synchronized (this.field_147229_c) {
            final Iterator var2 = this.field_147229_c.iterator();
            while (var2.hasNext()) {
                final NetworkManager var3 = var2.next();
                if (var3.isChannelOpen()) {
                    var3.processReceivedPackets();
                }
                else {
                    var2.remove();
                    if (var3.getExitMessage() == null) {
                        continue;
                    }
                    var3.getNetHandler().onDisconnect(var3.getExitMessage());
                }
            }
        }
    }
    
    public void func_147226_b() {
        final List var1 = this.field_147229_c;
        synchronized (this.field_147229_c) {
            final Iterator var2 = this.field_147229_c.iterator();
            while (var2.hasNext()) {
                final NetworkManager var3 = var2.next();
                if (var3.isChannelOpen()) {
                    var2.remove();
                    var3.closeChannel(new ChatComponentText("Cancelled"));
                }
            }
        }
    }
    
    static {
        field_147230_a = Splitter.on('\0').limit(6);
        logger = LogManager.getLogger();
    }
}
