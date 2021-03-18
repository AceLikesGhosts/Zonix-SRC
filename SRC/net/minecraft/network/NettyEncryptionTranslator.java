package net.minecraft.network;

import io.netty.buffer.*;
import io.netty.channel.*;
import javax.crypto.*;

public class NettyEncryptionTranslator
{
    private final Cipher field_150507_a;
    private byte[] field_150505_b;
    private byte[] field_150506_c;
    private static final String __OBFID = "CL_00001237";
    
    protected NettyEncryptionTranslator(final Cipher p_i45140_1_) {
        this.field_150505_b = new byte[0];
        this.field_150506_c = new byte[0];
        this.field_150507_a = p_i45140_1_;
    }
    
    private byte[] func_150502_a(final ByteBuf p_150502_1_) {
        final int var2 = p_150502_1_.readableBytes();
        if (this.field_150505_b.length < var2) {
            this.field_150505_b = new byte[var2];
        }
        p_150502_1_.readBytes(this.field_150505_b, 0, var2);
        return this.field_150505_b;
    }
    
    protected ByteBuf func_150503_a(final ChannelHandlerContext p_150503_1_, final ByteBuf p_150503_2_) throws ShortBufferException {
        final int var3 = p_150503_2_.readableBytes();
        final byte[] var4 = this.func_150502_a(p_150503_2_);
        final ByteBuf var5 = p_150503_1_.alloc().heapBuffer(this.field_150507_a.getOutputSize(var3));
        var5.writerIndex(this.field_150507_a.update(var4, 0, var3, var5.array(), var5.arrayOffset()));
        return var5;
    }
    
    protected void func_150504_a(final ByteBuf p_150504_1_, final ByteBuf p_150504_2_) throws ShortBufferException {
        final int var3 = p_150504_1_.readableBytes();
        final byte[] var4 = this.func_150502_a(p_150504_1_);
        final int var5 = this.field_150507_a.getOutputSize(var3);
        if (this.field_150506_c.length < var5) {
            this.field_150506_c = new byte[var5];
        }
        p_150504_2_.writeBytes(this.field_150506_c, 0, this.field_150507_a.update(var4, 0, var3, this.field_150506_c));
    }
}
