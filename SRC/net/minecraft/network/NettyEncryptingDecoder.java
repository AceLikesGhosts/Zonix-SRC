package net.minecraft.network;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;
import javax.crypto.*;

public class NettyEncryptingDecoder extends MessageToMessageDecoder
{
    private final NettyEncryptionTranslator field_150509_a;
    private static final String __OBFID = "CL_00001238";
    
    public NettyEncryptingDecoder(final Cipher p_i45141_1_) {
        this.field_150509_a = new NettyEncryptionTranslator(p_i45141_1_);
    }
    
    protected void decode(final ChannelHandlerContext p_decode_1_, final ByteBuf p_decode_2_, final List p_decode_3_) throws ShortBufferException {
        p_decode_3_.add(this.field_150509_a.func_150503_a(p_decode_1_, p_decode_2_));
    }
    
    protected void decode(final ChannelHandlerContext p_decode_1_, final Object p_decode_2_, final List p_decode_3_) throws ShortBufferException {
        this.decode(p_decode_1_, (ByteBuf)p_decode_2_, p_decode_3_);
    }
}
