package net.minecraft.command.server;

import java.util.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.*;

public class CommandMessage extends CommandBase
{
    private static final String __OBFID = "CL_00000641";
    
    @Override
    public List getCommandAliases() {
        return Arrays.asList("w", "msg");
    }
    
    @Override
    public String getCommandName() {
        return "tell";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.message.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length < 2) {
            throw new WrongUsageException("commands.message.usage", new Object[0]);
        }
        final EntityPlayerMP var3 = CommandBase.getPlayer(p_71515_1_, p_71515_2_[0]);
        if (var3 == null) {
            throw new PlayerNotFoundException();
        }
        if (var3 == p_71515_1_) {
            throw new PlayerNotFoundException("commands.message.sameTarget", new Object[0]);
        }
        final IChatComponent var4 = CommandBase.func_147176_a(p_71515_1_, p_71515_2_, 1, !(p_71515_1_ instanceof EntityPlayer));
        final ChatComponentTranslation var5 = new ChatComponentTranslation("commands.message.display.incoming", new Object[] { p_71515_1_.func_145748_c_(), var4.createCopy() });
        final ChatComponentTranslation var6 = new ChatComponentTranslation("commands.message.display.outgoing", new Object[] { var3.func_145748_c_(), var4.createCopy() });
        var5.getChatStyle().setColor(EnumChatFormatting.GRAY).setItalic(true);
        var6.getChatStyle().setColor(EnumChatFormatting.GRAY).setItalic(true);
        var3.addChatMessage(var5);
        p_71515_1_.addChatMessage(var6);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        return CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getAllUsernames());
    }
    
    @Override
    public boolean isUsernameIndex(final String[] p_82358_1_, final int p_82358_2_) {
        return p_82358_2_ == 0;
    }
}
