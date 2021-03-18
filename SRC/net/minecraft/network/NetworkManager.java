package net.minecraft.network;

import io.netty.util.*;
import io.netty.channel.nio.*;
import java.util.*;
import com.google.common.collect.*;
import org.apache.commons.lang3.*;
import io.netty.util.concurrent.*;
import io.netty.channel.local.*;
import java.net.*;
import io.netty.bootstrap.*;
import io.netty.handler.timeout.*;
import io.netty.channel.*;
import io.netty.channel.socket.nio.*;
import javax.crypto.*;
import net.minecraft.util.*;
import java.security.*;
import org.apache.logging.log4j.*;
import com.google.common.util.concurrent.*;

public class NetworkManager extends SimpleChannelInboundHandler
{
    private static final Logger logger;
    public static final Marker logMarkerNetwork;
    public static final Marker logMarkerPackets;
    public static final Marker field_152461_c;
    public static final AttributeKey attrKeyConnectionState;
    public static final AttributeKey attrKeyReceivable;
    public static final AttributeKey attrKeySendable;
    public static final NioEventLoopGroup eventLoops;
    public static final NetworkStatistics field_152462_h;
    private final boolean isClientSide;
    private final Queue receivedPacketsQueue;
    private final Queue outboundPacketsQueue;
    private Channel channel;
    private SocketAddress socketAddress;
    private INetHandler netHandler;
    private EnumConnectionState connectionState;
    private IChatComponent terminationReason;
    private boolean field_152463_r;
    private static final String __OBFID = "CL_00001240";
    
    public NetworkManager(final boolean p_i45147_1_) {
        this.receivedPacketsQueue = Queues.newConcurrentLinkedQueue();
        this.outboundPacketsQueue = Queues.newConcurrentLinkedQueue();
        this.isClientSide = p_i45147_1_;
    }
    
    public void channelActive(final ChannelHandlerContext p_channelActive_1_) throws Exception {
        super.channelActive(p_channelActive_1_);
        this.channel = p_channelActive_1_.channel();
        this.socketAddress = this.channel.remoteAddress();
        this.setConnectionState(EnumConnectionState.HANDSHAKING);
    }
    
    public void setConnectionState(final EnumConnectionState p_150723_1_) {
        this.connectionState = (EnumConnectionState)this.channel.attr(NetworkManager.attrKeyConnectionState).getAndSet((Object)p_150723_1_);
        this.channel.attr(NetworkManager.attrKeyReceivable).set((Object)p_150723_1_.func_150757_a(this.isClientSide));
        this.channel.attr(NetworkManager.attrKeySendable).set((Object)p_150723_1_.func_150754_b(this.isClientSide));
        this.channel.config().setAutoRead(true);
        NetworkManager.logger.debug("Enabled auto read");
    }
    
    public void channelInactive(final ChannelHandlerContext p_channelInactive_1_) {
        this.closeChannel(new ChatComponentTranslation("disconnect.endOfStream", new Object[0]));
    }
    
    public void exceptionCaught(final ChannelHandlerContext p_exceptionCaught_1_, final Throwable p_exceptionCaught_2_) {
        ChatComponentTranslation var3;
        if (p_exceptionCaught_2_ instanceof TimeoutException) {
            var3 = new ChatComponentTranslation("disconnect.timeout", new Object[0]);
        }
        else {
            var3 = new ChatComponentTranslation("disconnect.genericReason", new Object[] { "Internal Exception: " + p_exceptionCaught_2_ });
        }
        this.closeChannel(var3);
    }
    
    protected void channelRead0(final ChannelHandlerContext p_channelRead0_1_, final Packet p_channelRead0_2_) {
        if (this.channel.isOpen()) {
            if (p_channelRead0_2_.hasPriority()) {
                p_channelRead0_2_.processPacket(this.netHandler);
            }
            else {
                this.receivedPacketsQueue.add(p_channelRead0_2_);
            }
        }
    }
    
    public void setNetHandler(final INetHandler p_150719_1_) {
        Validate.notNull((Object)p_150719_1_, "packetListener", new Object[0]);
        NetworkManager.logger.debug("Set listener of {} to {}", new Object[] { this, p_150719_1_ });
        this.netHandler = p_150719_1_;
    }
    
    public void scheduleOutboundPacket(final Packet p_150725_1_, final GenericFutureListener... p_150725_2_) {
        if (this.channel != null && this.channel.isOpen()) {
            this.flushOutboundQueue();
            this.dispatchPacket(p_150725_1_, p_150725_2_);
        }
        else {
            this.outboundPacketsQueue.add(new InboundHandlerTuplePacketListener(p_150725_1_, p_150725_2_));
        }
    }
    
    private void dispatchPacket(final Packet p_150732_1_, final GenericFutureListener[] p_150732_2_) {
        final EnumConnectionState var3 = EnumConnectionState.func_150752_a(p_150732_1_);
        final EnumConnectionState var4 = (EnumConnectionState)this.channel.attr(NetworkManager.attrKeyConnectionState).get();
        if (var4 != var3) {
            NetworkManager.logger.debug("Disabled auto read");
            this.channel.config().setAutoRead(false);
        }
        if (this.channel.eventLoop().inEventLoop()) {
            if (var3 != var4) {
                this.setConnectionState(var3);
            }
            this.channel.writeAndFlush((Object)p_150732_1_).addListeners(p_150732_2_).addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        }
        else {
            this.channel.eventLoop().execute((Runnable)new Runnable() {
                private static final String __OBFID = "CL_00001241";
                
                @Override
                public void run() {
                    if (var3 != var4) {
                        NetworkManager.this.setConnectionState(var3);
                    }
                    NetworkManager.this.channel.writeAndFlush((Object)p_150732_1_).addListeners(p_150732_2_).addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
                }
            });
        }
    }
    
    private void flushOutboundQueue() {
        if (this.channel != null && this.channel.isOpen()) {
            while (!this.outboundPacketsQueue.isEmpty()) {
                final InboundHandlerTuplePacketListener var1 = this.outboundPacketsQueue.poll();
                this.dispatchPacket(var1.field_150774_a, var1.field_150773_b);
            }
        }
    }
    
    public void processReceivedPackets() {
        this.flushOutboundQueue();
        final EnumConnectionState var1 = (EnumConnectionState)this.channel.attr(NetworkManager.attrKeyConnectionState).get();
        if (this.connectionState != var1) {
            if (this.connectionState != null) {
                this.netHandler.onConnectionStateTransition(this.connectionState, var1);
            }
            this.connectionState = var1;
        }
        if (this.netHandler != null) {
            for (int var2 = 1000; !this.receivedPacketsQueue.isEmpty() && var2 >= 0; --var2) {
                final Packet var3 = this.receivedPacketsQueue.poll();
                var3.processPacket(this.netHandler);
            }
            this.netHandler.onNetworkTick();
        }
        this.channel.flush();
    }
    
    public SocketAddress getSocketAddress() {
        return this.socketAddress;
    }
    
    public void closeChannel(final IChatComponent p_150718_1_) {
        if (this.channel.isOpen()) {
            this.channel.close();
            this.terminationReason = p_150718_1_;
        }
    }
    
    public boolean isLocalChannel() {
        return this.channel instanceof LocalChannel || this.channel instanceof LocalServerChannel;
    }
    
    public static NetworkManager provideLanClient(final InetAddress p_150726_0_, final int p_150726_1_) {
        final NetworkManager var2 = new NetworkManager(true);
        ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)NetworkManager.eventLoops)).handler((ChannelHandler)new ChannelInitializer() {
            private static final String __OBFID = "CL_00001242";
            
            protected void initChannel(final Channel p_initChannel_1_) {
                try {
                    p_initChannel_1_.config().setOption(ChannelOption.IP_TOS, (Object)24);
                }
                catch (ChannelException ex) {}
                try {
                    p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, (Object)true);
                }
                catch (ChannelException ex2) {}
                p_initChannel_1_.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(20)).addLast("splitter", (ChannelHandler)new MessageDeserializer2()).addLast("decoder", (ChannelHandler)new MessageDeserializer(NetworkManager.field_152462_h)).addLast("prepender", (ChannelHandler)new MessageSerializer2()).addLast("encoder", (ChannelHandler)new MessageSerializer(NetworkManager.field_152462_h)).addLast("packet_handler", (ChannelHandler)var2);
            }
        })).channel((Class)NioSocketChannel.class)).connect(p_150726_0_, p_150726_1_).syncUninterruptibly();
        return var2;
    }
    
    public static NetworkManager provideLocalClient(final SocketAddress p_150722_0_) {
        final NetworkManager var1 = new NetworkManager(true);
        ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)NetworkManager.eventLoops)).handler((ChannelHandler)new ChannelInitializer() {
            private static final String __OBFID = "CL_00001243";
            
            protected void initChannel(final Channel p_initChannel_1_) {
                p_initChannel_1_.pipeline().addLast("packet_handler", (ChannelHandler)var1);
            }
        })).channel((Class)LocalChannel.class)).connect(p_150722_0_).syncUninterruptibly();
        return var1;
    }
    
    public void enableEncryption(final SecretKey p_150727_1_) {
        this.channel.pipeline().addBefore("splitter", "decrypt", (ChannelHandler)new NettyEncryptingDecoder(CryptManager.func_151229_a(2, p_150727_1_)));
        this.channel.pipeline().addBefore("prepender", "encrypt", (ChannelHandler)new NettyEncryptingEncoder(CryptManager.func_151229_a(1, p_150727_1_)));
        this.field_152463_r = true;
    }
    
    public boolean isChannelOpen() {
        return this.channel != null && this.channel.isOpen();
    }
    
    public INetHandler getNetHandler() {
        return this.netHandler;
    }
    
    public IChatComponent getExitMessage() {
        return this.terminationReason;
    }
    
    public void disableAutoRead() {
        this.channel.config().setAutoRead(false);
    }
    
    protected void channelRead0(final ChannelHandlerContext p_channelRead0_1_, final Object p_channelRead0_2_) {
        this.channelRead0(p_channelRead0_1_, (Packet)p_channelRead0_2_);
    }
    
    static {
        logger = LogManager.getLogger();
        logMarkerNetwork = MarkerManager.getMarker("NETWORK");
        logMarkerPackets = MarkerManager.getMarker("NETWORK_PACKETS", NetworkManager.logMarkerNetwork);
        field_152461_c = MarkerManager.getMarker("NETWORK_STAT", NetworkManager.logMarkerNetwork);
        attrKeyConnectionState = new AttributeKey("protocol");
        attrKeyReceivable = new AttributeKey("receivable_packets");
        attrKeySendable = new AttributeKey("sendable_packets");
        eventLoops = new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Client IO #%d").setDaemon(true).build());
        field_152462_h = new NetworkStatistics();
    }
    
    static class InboundHandlerTuplePacketListener
    {
        private final Packet field_150774_a;
        private final GenericFutureListener[] field_150773_b;
        private static final String __OBFID = "CL_00001244";
        
        public InboundHandlerTuplePacketListener(final Packet p_i45146_1_, final GenericFutureListener... p_i45146_2_) {
            this.field_150774_a = p_i45146_1_;
            this.field_150773_b = p_i45146_2_;
        }
    }
}
