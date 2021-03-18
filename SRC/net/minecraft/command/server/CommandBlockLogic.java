package net.minecraft.command.server;

import java.text.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.server.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import java.util.*;
import io.netty.buffer.*;

public abstract class CommandBlockLogic implements ICommandSender
{
    private static final SimpleDateFormat field_145766_a;
    private int field_145764_b;
    private boolean field_145765_c;
    private IChatComponent field_145762_d;
    private String field_145763_e;
    private String field_145761_f;
    private static final String __OBFID = "CL_00000128";
    
    public CommandBlockLogic() {
        this.field_145765_c = true;
        this.field_145762_d = null;
        this.field_145763_e = "";
        this.field_145761_f = "@";
    }
    
    public int func_145760_g() {
        return this.field_145764_b;
    }
    
    public IChatComponent func_145749_h() {
        return this.field_145762_d;
    }
    
    public void func_145758_a(final NBTTagCompound p_145758_1_) {
        p_145758_1_.setString("Command", this.field_145763_e);
        p_145758_1_.setInteger("SuccessCount", this.field_145764_b);
        p_145758_1_.setString("CustomName", this.field_145761_f);
        if (this.field_145762_d != null) {
            p_145758_1_.setString("LastOutput", IChatComponent.Serializer.func_150696_a(this.field_145762_d));
        }
        p_145758_1_.setBoolean("TrackOutput", this.field_145765_c);
    }
    
    public void func_145759_b(final NBTTagCompound p_145759_1_) {
        this.field_145763_e = p_145759_1_.getString("Command");
        this.field_145764_b = p_145759_1_.getInteger("SuccessCount");
        if (p_145759_1_.func_150297_b("CustomName", 8)) {
            this.field_145761_f = p_145759_1_.getString("CustomName");
        }
        if (p_145759_1_.func_150297_b("LastOutput", 8)) {
            this.field_145762_d = IChatComponent.Serializer.func_150699_a(p_145759_1_.getString("LastOutput"));
        }
        if (p_145759_1_.func_150297_b("TrackOutput", 1)) {
            this.field_145765_c = p_145759_1_.getBoolean("TrackOutput");
        }
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int p_70003_1_, final String p_70003_2_) {
        return p_70003_1_ <= 2;
    }
    
    public void func_145752_a(final String p_145752_1_) {
        this.field_145763_e = p_145752_1_;
    }
    
    public String func_145753_i() {
        return this.field_145763_e;
    }
    
    public void func_145755_a(final World p_145755_1_) {
        if (p_145755_1_.isClient) {
            this.field_145764_b = 0;
        }
        final MinecraftServer var2 = MinecraftServer.getServer();
        if (var2 != null && var2.isCommandBlockEnabled()) {
            final ICommandManager var3 = var2.getCommandManager();
            this.field_145764_b = var3.executeCommand(this, this.field_145763_e);
        }
        else {
            this.field_145764_b = 0;
        }
    }
    
    @Override
    public String getCommandSenderName() {
        return this.field_145761_f;
    }
    
    @Override
    public IChatComponent func_145748_c_() {
        return new ChatComponentText(this.getCommandSenderName());
    }
    
    public void func_145754_b(final String p_145754_1_) {
        this.field_145761_f = p_145754_1_;
    }
    
    @Override
    public void addChatMessage(final IChatComponent p_145747_1_) {
        if (this.field_145765_c && this.getEntityWorld() != null && !this.getEntityWorld().isClient) {
            this.field_145762_d = new ChatComponentText("[" + CommandBlockLogic.field_145766_a.format(new Date()) + "] ").appendSibling(p_145747_1_);
            this.func_145756_e();
        }
    }
    
    public abstract void func_145756_e();
    
    public abstract int func_145751_f();
    
    public abstract void func_145757_a(final ByteBuf p0);
    
    public void func_145750_b(final IChatComponent p_145750_1_) {
        this.field_145762_d = p_145750_1_;
    }
    
    static {
        field_145766_a = new SimpleDateFormat("HH:mm:ss");
    }
}
