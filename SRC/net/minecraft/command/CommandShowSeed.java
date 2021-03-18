package net.minecraft.command;

import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class CommandShowSeed extends CommandBase
{
    private static final String __OBFID = "CL_00001053";
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender p_71519_1_) {
        return MinecraftServer.getServer().isSinglePlayer() || super.canCommandSenderUseCommand(p_71519_1_);
    }
    
    @Override
    public String getCommandName() {
        return "seed";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.seed.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        final Object var3 = (p_71515_1_ instanceof EntityPlayer) ? ((EntityPlayer)p_71515_1_).worldObj : MinecraftServer.getServer().worldServerForDimension(0);
        p_71515_1_.addChatMessage(new ChatComponentTranslation("commands.seed.success", new Object[] { ((World)var3).getSeed() }));
    }
}
