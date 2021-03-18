package net.minecraft.command;

import net.minecraft.util.*;
import net.minecraft.entity.player.*;

public class CommandKill extends CommandBase
{
    private static final String __OBFID = "CL_00000570";
    
    @Override
    public String getCommandName() {
        return "kill";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.kill.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        final EntityPlayerMP var3 = CommandBase.getCommandSenderAsPlayer(p_71515_1_);
        var3.attackEntityFrom(DamageSource.outOfWorld, Float.MAX_VALUE);
        p_71515_1_.addChatMessage(new ChatComponentTranslation("commands.kill.success", new Object[0]));
    }
}
