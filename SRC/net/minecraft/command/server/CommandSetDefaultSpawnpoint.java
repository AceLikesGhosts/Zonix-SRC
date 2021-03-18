package net.minecraft.command.server;

import net.minecraft.command.*;
import net.minecraft.util.*;

public class CommandSetDefaultSpawnpoint extends CommandBase
{
    private static final String __OBFID = "CL_00000973";
    
    @Override
    public String getCommandName() {
        return "setworldspawn";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.setworldspawn.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length == 3) {
            if (p_71515_1_.getEntityWorld() == null) {
                throw new WrongUsageException("commands.setworldspawn.usage", new Object[0]);
            }
            final byte var3 = 0;
            int var4 = var3 + 1;
            final int var5 = CommandBase.parseIntBounded(p_71515_1_, p_71515_2_[var3], -30000000, 30000000);
            final int var6 = CommandBase.parseIntBounded(p_71515_1_, p_71515_2_[var4++], 0, 256);
            final int var7 = CommandBase.parseIntBounded(p_71515_1_, p_71515_2_[var4++], -30000000, 30000000);
            p_71515_1_.getEntityWorld().setSpawnLocation(var5, var6, var7);
            CommandBase.func_152373_a(p_71515_1_, this, "commands.setworldspawn.success", var5, var6, var7);
        }
        else {
            if (p_71515_2_.length != 0) {
                throw new WrongUsageException("commands.setworldspawn.usage", new Object[0]);
            }
            final ChunkCoordinates var8 = CommandBase.getCommandSenderAsPlayer(p_71515_1_).getPlayerCoordinates();
            p_71515_1_.getEntityWorld().setSpawnLocation(var8.posX, var8.posY, var8.posZ);
            CommandBase.func_152373_a(p_71515_1_, this, "commands.setworldspawn.success", var8.posX, var8.posY, var8.posZ);
        }
    }
}
