package net.minecraft.command.server;

import net.minecraft.command.*;
import net.minecraft.server.*;
import net.minecraft.util.*;

public class CommandListPlayers extends CommandBase
{
    private static final String __OBFID = "CL_00000615";
    
    @Override
    public String getCommandName() {
        return "list";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.players.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        p_71515_1_.addChatMessage(new ChatComponentTranslation("commands.players.list", new Object[] { MinecraftServer.getServer().getCurrentPlayerCount(), MinecraftServer.getServer().getMaxPlayers() }));
        p_71515_1_.addChatMessage(new ChatComponentText(MinecraftServer.getServer().getConfigurationManager().func_152609_b(p_71515_2_.length > 0 && "uuids".equalsIgnoreCase(p_71515_2_[0]))));
    }
}
