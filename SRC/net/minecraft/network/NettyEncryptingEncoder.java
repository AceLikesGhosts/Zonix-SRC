package net.minecraft.network;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import javax.crypto.*;

public class NettyEncryptingEncoder extends MessageToByteEncoder
{
    private final NettyEncryptionTranslator field_150750_a;
    private static final String __OBFID = "CL_00001239";
    
    public NettyEncryptingEncoder(final Cipher p_i45142_1_) {
        this.field_150750_a = new NettyEncryptionTranslator(p_i45142_1_);
    }
    
    protected void encode(final ChannelHandlerContext p_encode_1_, final ByteBuf p_encode_2_, final ByteBuf p_encode_3_) throws ShortBufferException {
        this.field_150750_a.func_150504_a(p_encode_2_, p_encode_3_);
    }
    
    protected void encode(final ChannelHandlerContext p_encode_1_, final Object p_encode_2_, final ByteBuf p_encode_3_) throws ShortBufferException {
        this.encode(p_encode_1_, (ByteBuf)p_encode_2_, p_encode_3_);
    }
}
