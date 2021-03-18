package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.command.*;
import com.mojang.authlib.*;
import java.util.*;

public class CommandDeOp extends CommandBase
{
    private static final String __OBFID = "CL_00000244";
    
    @Override
    public String getCommandName() {
        return "deop";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.deop.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length != 1 || p_71515_2_[0].length() <= 0) {
            throw new WrongUsageException("commands.deop.usage", new Object[0]);
        }
        final MinecraftServer var3 = MinecraftServer.getServer();
        final GameProfile var4 = var3.getConfigurationManager().func_152603_m().func_152700_a(p_71515_2_[0]);
        if (var4 == null) {
            throw new CommandException("commands.deop.failed", new Object[] { p_71515_2_[0] });
        }
        var3.getConfigurationManager().func_152610_b(var4);
        CommandBase.func_152373_a(p_71515_1_, this, "commands.deop.success", p_71515_2_[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        return (p_71516_2_.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getConfigurationManager().func_152606_n()) : null;
    }
}
