package net.minecraft.util;

import java.util.*;
import com.google.common.collect.*;
import com.google.common.base.*;

public abstract class ChatComponentStyle implements IChatComponent
{
    protected List siblings;
    private ChatStyle style;
    private static final String __OBFID = "CL_00001257";
    
    public ChatComponentStyle() {
        this.siblings = Lists.newArrayList();
    }
    
    @Override
    public IChatComponent appendSibling(final IChatComponent p_150257_1_) {
        p_150257_1_.getChatStyle().setParentStyle(this.getChatStyle());
        this.siblings.add(p_150257_1_);
        return this;
    }
    
    @Override
    public List getSiblings() {
        return this.siblings;
    }
    
    @Override
    public IChatComponent appendText(final String p_150258_1_) {
        return this.appendSibling(new ChatComponentText(p_150258_1_));
    }
    
    @Override
    public IChatComponent setChatStyle(final ChatStyle p_150255_1_) {
        this.style = p_150255_1_;
        for (final IChatComponent var3 : this.siblings) {
            var3.getChatStyle().setParentStyle(this.getChatStyle());
        }
        return this;
    }
    
    @Override
    public ChatStyle getChatStyle() {
        if (this.style == null) {
            this.style = new ChatStyle();
            for (final IChatComponent var2 : this.siblings) {
                var2.getChatStyle().setParentStyle(this.style);
            }
        }
        return this.style;
    }
    
    @Override
    public Iterator iterator() {
        return Iterators.concat((Iterator)Iterators.forArray((Object[])new ChatComponentStyle[] { this }), createDeepCopyIterator(this.siblings));
    }
    
    @Override
    public final String getUnformattedText() {
        final StringBuilder var1 = new StringBuilder();
        for (final IChatComponent var3 : this) {
            var1.append(var3.getUnformattedTextForChat());
        }
        return var1.toString();
    }
    
    @Override
    public final String getFormattedText() {
        final StringBuilder var1 = new StringBuilder();
        for (final IChatComponent var3 : this) {
            var1.append(var3.getChatStyle().getFormattingCode());
            var1.append(var3.getUnformattedTextForChat());
            var1.append(EnumChatFormatting.RESET);
        }
        return var1.toString();
    }
    
    public static Iterator createDeepCopyIterator(final Iterable p_150262_0_) {
        Iterator var1 = Iterators.concat(Iterators.transform((Iterator)p_150262_0_.iterator(), (Function)new Function() {
            private static final String __OBFID = "CL_00001258";
            
            public Iterator apply(final IChatComponent p_apply_1_) {
                return p_apply_1_.iterator();
            }
            
            public Object apply(final Object p_apply_1_) {
                return this.apply((IChatComponent)p_apply_1_);
            }
        }));
        var1 = Iterators.transform(var1, (Function)new Function() {
            private static final String __OBFID = "CL_00001259";
            
            public IChatComponent apply(final IChatComponent p_apply_1_) {
                final IChatComponent var2 = p_apply_1_.createCopy();
                var2.setChatStyle(var2.getChatStyle().createDeepCopy());
                return var2;
            }
            
            public Object apply(final Object p_apply_1_) {
                return this.apply((IChatComponent)p_apply_1_);
            }
        });
        return var1;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (!(p_equals_1_ instanceof ChatComponentStyle)) {
            return false;
        }
        final ChatComponentStyle var2 = (ChatComponentStyle)p_equals_1_;
        return this.siblings.equals(var2.siblings) && this.getChatStyle().equals(var2.getChatStyle());
    }
    
    @Override
    public int hashCode() {
        return 31 * this.style.hashCode() + this.siblings.hashCode();
    }
    
    @Override
    public String toString() {
        return "BaseComponent{style=" + this.style + ", siblings=" + this.siblings + '}';
    }
}
