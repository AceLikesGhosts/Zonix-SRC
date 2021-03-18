package net.minecraft.network;

import io.netty.channel.nio.*;
import net.minecraft.server.*;
import io.netty.bootstrap.*;
import io.netty.channel.socket.nio.*;
import io.netty.handler.timeout.*;
import net.minecraft.server.network.*;
import java.io.*;
import java.net.*;
import net.minecraft.client.network.*;
import io.netty.channel.local.*;
import io.netty.channel.*;
import java.util.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import net.minecraft.network.play.server.*;
import io.netty.util.concurrent.*;
import net.minecraft.crash.*;
import org.apache.logging.log4j.*;
import com.google.common.util.concurrent.*;

public class NetworkSystem
{
    private static final Logger logger;
    private static final NioEventLoopGroup eventLoops;
    private final MinecraftServer mcServer;
    public volatile boolean isAlive;
    private final List endpoints;
    private final List networkManagers;
    private static final String __OBFID = "CL_00001447";
    
    public NetworkSystem(final MinecraftServer p_i45292_1_) {
        this.endpoints = Collections.synchronizedList(new ArrayList<Object>());
        this.networkManagers = Collections.synchronizedList(new ArrayList<Object>());
        this.mcServer = p_i45292_1_;
        this.isAlive = true;
    }
    
    public void addLanEndpoint(final InetAddress p_151265_1_, final int p_151265_2_) throws IOException {
        final List var3 = this.endpoints;
        synchronized (this.endpoints) {
            this.endpoints.add(((ServerBootstrap)((ServerBootstrap)new ServerBootstrap().channel((Class)NioServerSocketChannel.class)).childHandler((ChannelHandler)new ChannelInitializer() {
                private static final String __OBFID = "CL_00001448";
                
                protected void initChannel(final Channel p_initChannel_1_) {
                    try {
                        p_initChannel_1_.config().setOption(ChannelOption.IP_TOS, (Object)24);
                    }
                    catch (ChannelException ex) {}
                    try {
                        p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, (Object)true);
                    }
                    catch (ChannelException ex2) {}
                    p_initChannel_1_.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(30)).addLast("legacy_query", (ChannelHandler)new PingResponseHandler(NetworkSystem.this)).addLast("splitter", (ChannelHandler)new MessageDeserializer2()).addLast("decoder", (ChannelHandler)new MessageDeserializer(NetworkManager.field_152462_h)).addLast("prepender", (ChannelHandler)new MessageSerializer2()).addLast("encoder", (ChannelHandler)new MessageSerializer(NetworkManager.field_152462_h));
                    final NetworkManager var2 = new NetworkManager(false);
                    NetworkSystem.this.networkManagers.add(var2);
                    p_initChannel_1_.pipeline().addLast("packet_handler", (ChannelHandler)var2);
                    var2.setNetHandler(new NetHandlerHandshakeTCP(NetworkSystem.this.mcServer, var2));
                }
            }).group((EventLoopGroup)NetworkSystem.eventLoops).localAddress(p_151265_1_, p_151265_2_)).bind().syncUninterruptibly());
        }
    }
    
    public SocketAddress addLocalEndpoint() {
        final List var2 = this.endpoints;
        final ChannelFuture var3;
        synchronized (this.endpoints) {
            var3 = ((ServerBootstrap)((ServerBootstrap)new ServerBootstrap().channel((Class)LocalServerChannel.class)).childHandler((ChannelHandler)new ChannelInitializer() {
                private static final String __OBFID = "CL_00001449";
                
                protected void initChannel(final Channel p_initChannel_1_) {
                    final NetworkManager var2 = new NetworkManager(false);
                    var2.setNetHandler(new NetHandlerHandshakeMemory(NetworkSystem.this.mcServer, var2));
                    NetworkSystem.this.networkManagers.add(var2);
                    p_initChannel_1_.pipeline().addLast("packet_handler", (ChannelHandler)var2);
                }
            }).group((EventLoopGroup)NetworkSystem.eventLoops).localAddress((SocketAddress)LocalAddress.ANY)).bind().syncUninterruptibly();
            this.endpoints.add(var3);
        }
        return var3.channel().localAddress();
    }
    
    public void terminateEndpoints() {
        this.isAlive = false;
        for (final ChannelFuture var2 : this.endpoints) {
            var2.channel().close().syncUninterruptibly();
        }
    }
    
    public void networkTick() {
        final List var1 = this.networkManagers;
        synchronized (this.networkManagers) {
            final Iterator var2 = this.networkManagers.iterator();
            while (var2.hasNext()) {
                final NetworkManager var3 = var2.next();
                if (!var3.isChannelOpen()) {
                    var2.remove();
                    if (var3.getExitMessage() != null) {
                        var3.getNetHandler().onDisconnect(var3.getExitMessage());
                    }
                    else {
                        if (var3.getNetHandler() == null) {
                            continue;
                        }
                        var3.getNetHandler().onDisconnect(new ChatComponentText("Disconnected"));
                    }
                }
                else {
                    try {
                        var3.processReceivedPackets();
                    }
                    catch (Exception var5) {
                        if (var3.isLocalChannel()) {
                            final CrashReport var4 = CrashReport.makeCrashReport(var5, "Ticking memory connection");
                            final CrashReportCategory var6 = var4.makeCategory("Ticking connection");
                            var6.addCrashSectionCallable("Connection", new Callable() {
                                private static final String __OBFID = "CL_00001450";
                                
                                @Override
                                public String call() {
                                    return var3.toString();
                                }
                            });
                            throw new ReportedException(var4);
                        }
                        NetworkSystem.logger.warn("Failed to handle packet for " + var3.getSocketAddress(), (Throwable)var5);
                        final ChatComponentText var7 = new ChatComponentText("Internal server error");
                        var3.scheduleOutboundPacket(new S40PacketDisconnect(var7), (GenericFutureListener)new GenericFutureListener() {
                            private static final String __OBFID = "CL_00001451";
                            
                            public void operationComplete(final Future p_operationComplete_1_) {
                                var3.closeChannel(var7);
                            }
                        });
                        var3.disableAutoRead();
                    }
                }
            }
        }
    }
    
    public MinecraftServer func_151267_d() {
        return this.mcServer;
    }
    
    static {
        logger = LogManager.getLogger();
        eventLoops = new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty IO #%d").setDaemon(true).build());
    }
}
