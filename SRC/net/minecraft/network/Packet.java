package net.minecraft.network;

import com.google.common.collect.*;
import io.netty.buffer.*;
import java.io.*;
import org.apache.logging.log4j.*;

public abstract class Packet
{
    private static final Logger logger;
    private static final String __OBFID = "CL_00001272";
    
    public static Packet generatePacket(final BiMap p_148839_0_, final int p_148839_1_) {
        try {
            final Class var2 = (Class)p_148839_0_.get((Object)p_148839_1_);
            return (var2 == null) ? null : var2.newInstance();
        }
        catch (Exception var3) {
            Packet.logger.error("Couldn't create packet " + p_148839_1_, (Throwable)var3);
            return null;
        }
    }
    
    public static void writeBlob(final ByteBuf p_148838_0_, final byte[] p_148838_1_) {
        p_148838_0_.writeShort(p_148838_1_.length);
        p_148838_0_.writeBytes(p_148838_1_);
    }
    
    public static byte[] readBlob(final ByteBuf p_148834_0_) throws IOException {
        final short var1 = p_148834_0_.readShort();
        if (var1 < 0) {
            throw new IOException("Key was smaller than nothing!  Weird key!");
        }
        final byte[] var2 = new byte[var1];
        p_148834_0_.readBytes(var2);
        return var2;
    }
    
    public abstract void readPacketData(final PacketBuffer p0) throws IOException;
    
    public abstract void writePacketData(final PacketBuffer p0) throws IOException;
    
    public abstract void processPacket(final INetHandler p0);
    
    public boolean hasPriority() {
        return false;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    
    public String serialize() {
        return "";
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
