package net.minecraft.util;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import com.google.common.collect.*;
import java.io.*;
import net.minecraft.network.*;
import org.apache.logging.log4j.*;

public class MessageSerializer extends MessageToByteEncoder
{
    private static final Logger logger;
    private static final Marker field_150797_b;
    private final NetworkStatistics field_152500_c;
    private static final String __OBFID = "CL_00001253";
    
    public MessageSerializer(final NetworkStatistics p_i46391_1_) {
        this.field_152500_c = p_i46391_1_;
    }
    
    protected void encode(final ChannelHandlerContext p_encode_1_, final Packet p_encode_2_, final ByteBuf p_encode_3_) throws IOException {
        final Integer var4 = (Integer)((BiMap)p_encode_1_.channel().attr(NetworkManager.attrKeySendable).get()).inverse().get((Object)p_encode_2_.getClass());
        if (MessageSerializer.logger.isDebugEnabled()) {
            MessageSerializer.logger.debug(MessageSerializer.field_150797_b, "OUT: [{}:{}] {}[{}]", new Object[] { p_encode_1_.channel().attr(NetworkManager.attrKeyConnectionState).get(), var4, p_encode_2_.getClass().getName(), p_encode_2_.serialize() });
        }
        if (var4 == null) {
            throw new IOException("Can't serialize unregistered packet");
        }
        final PacketBuffer var5 = new PacketBuffer(p_encode_3_);
        var5.writeVarIntToBuffer(var4);
        p_encode_2_.writePacketData(var5);
        this.field_152500_c.func_152464_b(var4, var5.readableBytes());
    }
    
    protected void encode(final ChannelHandlerContext p_encode_1_, final Object p_encode_2_, final ByteBuf p_encode_3_) throws IOException {
        this.encode(p_encode_1_, (Packet)p_encode_2_, p_encode_3_);
    }
    
    static {
        logger = LogManager.getLogger();
        field_150797_b = MarkerManager.getMarker("PACKET_SENT", NetworkManager.logMarkerPackets);
    }
}
