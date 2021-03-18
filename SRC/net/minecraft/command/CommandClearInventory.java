package net.minecraft.command;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandClearInventory extends CommandBase
{
    private static final String __OBFID = "CL_00000218";
    
    @Override
    public String getCommandName() {
        return "clear";
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.clear.usage";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        final EntityPlayerMP var3 = (p_71515_2_.length == 0) ? CommandBase.getCommandSenderAsPlayer(p_71515_1_) : CommandBase.getPlayer(p_71515_1_, p_71515_2_[0]);
        final Item var4 = (p_71515_2_.length >= 2) ? CommandBase.getItemByText(p_71515_1_, p_71515_2_[1]) : null;
        final int var5 = (p_71515_2_.length >= 3) ? CommandBase.parseIntWithMin(p_71515_1_, p_71515_2_[2], 0) : -1;
        if (p_71515_2_.length >= 2 && var4 == null) {
            throw new CommandException("commands.clear.failure", new Object[] { var3.getCommandSenderName() });
        }
        final int var6 = var3.inventory.clearInventory(var4, var5);
        var3.inventoryContainer.detectAndSendChanges();
        if (!var3.capabilities.isCreativeMode) {
            var3.updateHeldItem();
        }
        if (var6 == 0) {
            throw new CommandException("commands.clear.failure", new Object[] { var3.getCommandSenderName() });
        }
        CommandBase.func_152373_a(p_71515_1_, this, "commands.clear.success", var3.getCommandSenderName(), var6);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        return (p_71516_2_.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, this.func_147209_d()) : ((p_71516_2_.length == 2) ? CommandBase.getListOfStringsFromIterableMatchingLastWord(p_71516_2_, Item.itemRegistry.getKeys()) : null);
    }
    
    protected String[] func_147209_d() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    @Override
    public boolean isUsernameIndex(final String[] p_82358_1_, final int p_82358_2_) {
        return p_82358_2_ == 0;
    }
}
