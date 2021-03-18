package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.command.*;
import com.mojang.authlib.*;
import java.util.*;

public class CommandOp extends CommandBase
{
    private static final String __OBFID = "CL_00000694";
    
    @Override
    public String getCommandName() {
        return "op";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.op.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length != 1 || p_71515_2_[0].length() <= 0) {
            throw new WrongUsageException("commands.op.usage", new Object[0]);
        }
        final MinecraftServer var3 = MinecraftServer.getServer();
        final GameProfile var4 = var3.func_152358_ax().func_152655_a(p_71515_2_[0]);
        if (var4 == null) {
            throw new CommandException("commands.op.failed", new Object[] { p_71515_2_[0] });
        }
        var3.getConfigurationManager().func_152605_a(var4);
        CommandBase.func_152373_a(p_71515_1_, this, "commands.op.success", p_71515_2_[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        if (p_71516_2_.length == 1) {
            final String var3 = p_71516_2_[p_71516_2_.length - 1];
            final ArrayList var4 = new ArrayList();
            for (final GameProfile var8 : MinecraftServer.getServer().func_152357_F()) {
                if (!MinecraftServer.getServer().getConfigurationManager().func_152596_g(var8) && CommandBase.doesStringStartWith(var3, var8.getName())) {
                    var4.add(var8.getName());
                }
            }
            return var4;
        }
        return null;
    }
}
