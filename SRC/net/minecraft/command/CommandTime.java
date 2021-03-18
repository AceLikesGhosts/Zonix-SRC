package net.minecraft.command;

import java.util.*;
import net.minecraft.server.*;
import net.minecraft.world.*;

public class CommandTime extends CommandBase
{
    private static final String __OBFID = "CL_00001183";
    
    @Override
    public String getCommandName() {
        return "time";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.time.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length > 1) {
            if (p_71515_2_[0].equals("set")) {
                int var3;
                if (p_71515_2_[1].equals("day")) {
                    var3 = 1000;
                }
                else if (p_71515_2_[1].equals("night")) {
                    var3 = 13000;
                }
                else {
                    var3 = CommandBase.parseIntWithMin(p_71515_1_, p_71515_2_[1], 0);
                }
                this.setTime(p_71515_1_, var3);
                CommandBase.func_152373_a(p_71515_1_, this, "commands.time.set", var3);
                return;
            }
            if (p_71515_2_[0].equals("add")) {
                final int var3 = CommandBase.parseIntWithMin(p_71515_1_, p_71515_2_[1], 0);
                this.addTime(p_71515_1_, var3);
                CommandBase.func_152373_a(p_71515_1_, this, "commands.time.added", var3);
                return;
            }
        }
        throw new WrongUsageException("commands.time.usage", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        return (p_71516_2_.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, "set", "add") : ((p_71516_2_.length == 2 && p_71516_2_[0].equals("set")) ? CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, "day", "night") : null);
    }
    
    protected void setTime(final ICommandSender p_71552_1_, final int p_71552_2_) {
        for (int var3 = 0; var3 < MinecraftServer.getServer().worldServers.length; ++var3) {
            MinecraftServer.getServer().worldServers[var3].setWorldTime(p_71552_2_);
        }
    }
    
    protected void addTime(final ICommandSender p_71553_1_, final int p_71553_2_) {
        for (int var3 = 0; var3 < MinecraftServer.getServer().worldServers.length; ++var3) {
            final WorldServer var4 = MinecraftServer.getServer().worldServers[var3];
            var4.setWorldTime(var4.getWorldTime() + p_71553_2_);
        }
    }
}
