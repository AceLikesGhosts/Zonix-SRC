package net.minecraft.network;

import java.net.*;
import com.google.common.base.*;
import net.minecraft.server.*;
import io.netty.channel.*;
import io.netty.util.concurrent.*;
import io.netty.buffer.*;
import org.apache.logging.log4j.*;

public class PingResponseHandler extends ChannelInboundHandlerAdapter
{
    private static final Logger logger;
    private NetworkSystem field_151257_b;
    private static final String __OBFID = "CL_00001444";
    
    public PingResponseHandler(final NetworkSystem p_i45286_1_) {
        this.field_151257_b = p_i45286_1_;
    }
    
    public void channelRead(final ChannelHandlerContext p_channelRead_1_, final Object p_channelRead_2_) {
        final ByteBuf var3 = (ByteBuf)p_channelRead_2_;
        var3.markReaderIndex();
        boolean var4 = true;
        try {
            if (var3.readUnsignedByte() == 254) {
                final InetSocketAddress var5 = (InetSocketAddress)p_channelRead_1_.channel().remoteAddress();
                final MinecraftServer var6 = this.field_151257_b.func_151267_d();
                final int var7 = var3.readableBytes();
                switch (var7) {
                    case 0: {
                        PingResponseHandler.logger.debug("Ping: (<1.3.x) from {}:{}", new Object[] { var5.getAddress(), var5.getPort() });
                        final String var8 = String.format("%s§%d§%d", var6.getMOTD(), var6.getCurrentPlayerCount(), var6.getMaxPlayers());
                        this.func_151256_a(p_channelRead_1_, this.func_151255_a(var8));
                        break;
                    }
                    case 1: {
                        if (var3.readUnsignedByte() != 1) {
                            return;
                        }
                        PingResponseHandler.logger.debug("Ping: (1.4-1.5.x) from {}:{}", new Object[] { var5.getAddress(), var5.getPort() });
                        final String var8 = String.format("§1\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d", 127, var6.getMinecraftVersion(), var6.getMOTD(), var6.getCurrentPlayerCount(), var6.getMaxPlayers());
                        this.func_151256_a(p_channelRead_1_, this.func_151255_a(var8));
                        break;
                    }
                    default: {
                        boolean var9 = var3.readUnsignedByte() == 1;
                        var9 &= (var3.readUnsignedByte() == 250);
                        var9 &= "MC|PingHost".equals(new String(var3.readBytes(var3.readShort() * 2).array(), Charsets.UTF_16BE));
                        final int var10 = var3.readUnsignedShort();
                        var9 &= (var3.readUnsignedByte() >= 73);
                        var9 &= (3 + var3.readBytes(var3.readShort() * 2).array().length + 4 == var10);
                        var9 &= (var3.readInt() <= 65535);
                        var9 &= (var3.readableBytes() == 0);
                        if (!var9) {
                            return;
                        }
                        PingResponseHandler.logger.debug("Ping: (1.6) from {}:{}", new Object[] { var5.getAddress(), var5.getPort() });
                        final String var11 = String.format("§1\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d", 127, var6.getMinecraftVersion(), var6.getMOTD(), var6.getCurrentPlayerCount(), var6.getMaxPlayers());
                        final ByteBuf var12 = this.func_151255_a(var11);
                        try {
                            this.func_151256_a(p_channelRead_1_, var12);
                        }
                        finally {
                            var12.release();
                        }
                        break;
                    }
                }
                var3.release();
                var4 = false;
            }
        }
        catch (RuntimeException var13) {}
        finally {
            if (var4) {
                var3.resetReaderIndex();
                p_channelRead_1_.channel().pipeline().remove("legacy_query");
                p_channelRead_1_.fireChannelRead(p_channelRead_2_);
            }
        }
    }
    
    private void func_151256_a(final ChannelHandlerContext p_151256_1_, final ByteBuf p_151256_2_) {
        p_151256_1_.pipeline().firstContext().writeAndFlush((Object)p_151256_2_).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
    }
    
    private ByteBuf func_151255_a(final String p_151255_1_) {
        final ByteBuf var2 = Unpooled.buffer();
        var2.writeByte(255);
        final char[] var3 = p_151255_1_.toCharArray();
        var2.writeShort(var3.length);
        final char[] var4 = var3;
        for (int var5 = var3.length, var6 = 0; var6 < var5; ++var6) {
            final char var7 = var4[var6];
            var2.writeChar((int)var7);
        }
        return var2;
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
