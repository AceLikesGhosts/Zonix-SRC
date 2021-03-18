package net.minecraft.command;

import net.minecraft.server.*;

public class CommandSetPlayerTimeout extends CommandBase
{
    private static final String __OBFID = "CL_00000999";
    
    @Override
    public String getCommandName() {
        return "setidletimeout";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.setidletimeout.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length == 1) {
            final int var3 = CommandBase.parseIntWithMin(p_71515_1_, p_71515_2_[0], 0);
            MinecraftServer.getServer().func_143006_e(var3);
            CommandBase.func_152373_a(p_71515_1_, this, "commands.setidletimeout.success", var3);
            return;
        }
        throw new WrongUsageException("commands.setidletimeout.usage", new Object[0]);
    }
}
