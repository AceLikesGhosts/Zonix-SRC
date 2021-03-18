package net.minecraft.command;

import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import java.util.*;

public class CommandDefaultGameMode extends CommandGameMode
{
    private static final String __OBFID = "CL_00000296";
    
    @Override
    public String getCommandName() {
        return "defaultgamemode";
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.defaultgamemode.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length > 0) {
            final WorldSettings.GameType var3 = this.getGameModeFromCommand(p_71515_1_, p_71515_2_[0]);
            this.setGameType(var3);
            CommandBase.func_152373_a(p_71515_1_, this, "commands.defaultgamemode.success", new ChatComponentTranslation("gameMode." + var3.getName(), new Object[0]));
            return;
        }
        throw new WrongUsageException("commands.defaultgamemode.usage", new Object[0]);
    }
    
    protected void setGameType(final WorldSettings.GameType p_71541_1_) {
        final MinecraftServer var2 = MinecraftServer.getServer();
        var2.setGameType(p_71541_1_);
        if (var2.getForceGamemode()) {
            for (final EntityPlayerMP var4 : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
                var4.setGameType(p_71541_1_);
                var4.fallDistance = 0.0f;
            }
        }
    }
}
