package net.minecraft.util;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.network.*;
import java.io.*;
import org.apache.logging.log4j.*;

public class MessageDeserializer extends ByteToMessageDecoder
{
    private static final Logger logger;
    private static final Marker field_150799_b;
    private final NetworkStatistics field_152499_c;
    private static final String __OBFID = "CL_00001252";
    
    public MessageDeserializer(final NetworkStatistics p_i1183_1_) {
        this.field_152499_c = p_i1183_1_;
    }
    
    protected void decode(final ChannelHandlerContext p_decode_1_, final ByteBuf p_decode_2_, final List p_decode_3_) throws IOException {
        final int var4 = p_decode_2_.readableBytes();
        if (var4 != 0) {
            final PacketBuffer var5 = new PacketBuffer(p_decode_2_);
            final int var6 = var5.readVarIntFromBuffer();
            final Packet var7 = Packet.generatePacket((BiMap)p_decode_1_.channel().attr(NetworkManager.attrKeyReceivable).get(), var6);
            if (var7 == null) {
                throw new IOException("Bad packet id " + var6);
            }
            var7.readPacketData(var5);
            if (var5.readableBytes() > 0) {
                throw new IOException("Packet was larger than I expected, found " + var5.readableBytes() + " bytes extra whilst reading packet " + var6);
            }
            p_decode_3_.add(var7);
            this.field_152499_c.func_152469_a(var6, var4);
            if (MessageDeserializer.logger.isDebugEnabled()) {
                MessageDeserializer.logger.debug(MessageDeserializer.field_150799_b, " IN: [{}:{}] {}[{}]", new Object[] { p_decode_1_.channel().attr(NetworkManager.attrKeyConnectionState).get(), var6, var7.getClass().getName(), var7.serialize() });
            }
        }
    }
    
    static {
        logger = LogManager.getLogger();
        field_150799_b = MarkerManager.getMarker("PACKET_RECEIVED", NetworkManager.logMarkerPackets);
    }
}
