package net.minecraft.command.server;

import net.minecraft.util.*;
import org.apache.commons.lang3.exception.*;
import net.minecraft.command.*;
import com.google.gson.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandMessageRaw extends CommandBase
{
    private static final String __OBFID = "CL_00000667";
    
    @Override
    public String getCommandName() {
        return "tellraw";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.tellraw.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length < 2) {
            throw new WrongUsageException("commands.tellraw.usage", new Object[0]);
        }
        final EntityPlayerMP var3 = CommandBase.getPlayer(p_71515_1_, p_71515_2_[0]);
        final String var4 = CommandBase.func_82360_a(p_71515_1_, p_71515_2_, 1);
        try {
            final IChatComponent var5 = IChatComponent.Serializer.func_150699_a(var4);
            var3.addChatMessage(var5);
        }
        catch (JsonParseException var7) {
            final Throwable var6 = ExceptionUtils.getRootCause((Throwable)var7);
            throw new SyntaxErrorException("commands.tellraw.jsonException", new Object[] { (var6 == null) ? "" : var6.getMessage() });
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        return (p_71516_2_.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getAllUsernames()) : null;
    }
    
    @Override
    public boolean isUsernameIndex(final String[] p_82358_1_, final int p_82358_2_) {
        return p_82358_2_ == 0;
    }
}
