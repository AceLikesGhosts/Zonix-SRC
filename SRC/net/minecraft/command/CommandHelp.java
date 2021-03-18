package net.minecraft.command;

import net.minecraft.util.*;
import net.minecraft.event.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.*;
import java.util.*;

public class CommandHelp extends CommandBase
{
    private static final String __OBFID = "CL_00000529";
    
    @Override
    public String getCommandName() {
        return "help";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.help.usage";
    }
    
    @Override
    public List getCommandAliases() {
        return Arrays.asList("?");
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        final List var3 = this.getSortedPossibleCommands(p_71515_1_);
        final byte var4 = 7;
        final int var5 = (var3.size() - 1) / var4;
        final boolean var6 = false;
        int var7;
        try {
            var7 = ((p_71515_2_.length == 0) ? 0 : (CommandBase.parseIntBounded(p_71515_1_, p_71515_2_[0], 1, var5 + 1) - 1));
        }
        catch (NumberInvalidException var10) {
            final Map var8 = this.getCommands();
            final ICommand var9 = var8.get(p_71515_2_[0]);
            if (var9 != null) {
                throw new WrongUsageException(var9.getCommandUsage(p_71515_1_), new Object[0]);
            }
            if (MathHelper.parseIntWithDefault(p_71515_2_[0], -1) != -1) {
                throw var10;
            }
            throw new CommandNotFoundException();
        }
        final int var11 = Math.min((var7 + 1) * var4, var3.size());
        final ChatComponentTranslation var12 = new ChatComponentTranslation("commands.help.header", new Object[] { var7 + 1, var5 + 1 });
        var12.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
        p_71515_1_.addChatMessage(var12);
        for (int var13 = var7 * var4; var13 < var11; ++var13) {
            final ICommand var14 = var3.get(var13);
            final ChatComponentTranslation var15 = new ChatComponentTranslation(var14.getCommandUsage(p_71515_1_), new Object[0]);
            var15.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + var14.getCommandName() + " "));
            p_71515_1_.addChatMessage(var15);
        }
        if (var7 == 0 && p_71515_1_ instanceof EntityPlayer) {
            final ChatComponentTranslation var16 = new ChatComponentTranslation("commands.help.footer", new Object[0]);
            var16.getChatStyle().setColor(EnumChatFormatting.GREEN);
            p_71515_1_.addChatMessage(var16);
        }
    }
    
    protected List getSortedPossibleCommands(final ICommandSender p_71534_1_) {
        final List var2 = MinecraftServer.getServer().getCommandManager().getPossibleCommands(p_71534_1_);
        Collections.sort((List<Comparable>)var2);
        return var2;
    }
    
    protected Map getCommands() {
        return MinecraftServer.getServer().getCommandManager().getCommands();
    }
}
