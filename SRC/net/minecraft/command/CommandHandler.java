package net.minecraft.command;

import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class CommandHandler implements ICommandManager
{
    private static final Logger logger;
    private final Map commandMap;
    private final Set commandSet;
    private static final String __OBFID = "CL_00001765";
    
    public CommandHandler() {
        this.commandMap = new HashMap();
        this.commandSet = new HashSet();
    }
    
    @Override
    public int executeCommand(final ICommandSender p_71556_1_, String p_71556_2_) {
        p_71556_2_ = p_71556_2_.trim();
        if (p_71556_2_.startsWith("/")) {
            p_71556_2_ = p_71556_2_.substring(1);
        }
        String[] var3 = p_71556_2_.split(" ");
        final String var4 = var3[0];
        var3 = dropFirstString(var3);
        final ICommand var5 = this.commandMap.get(var4);
        final int var6 = this.getUsernameIndex(var5, var3);
        int var7 = 0;
        try {
            if (var5 == null) {
                throw new CommandNotFoundException();
            }
            if (var5.canCommandSenderUseCommand(p_71556_1_)) {
                if (var6 > -1) {
                    final EntityPlayerMP[] var8 = PlayerSelector.matchPlayers(p_71556_1_, var3[var6]);
                    final String var9 = var3[var6];
                    final EntityPlayerMP[] var10 = var8;
                    for (int var11 = var8.length, var12 = 0; var12 < var11; ++var12) {
                        final EntityPlayerMP var13 = var10[var12];
                        var3[var6] = var13.getCommandSenderName();
                        try {
                            var5.processCommand(p_71556_1_, var3);
                            ++var7;
                        }
                        catch (CommandException var15) {
                            final ChatComponentTranslation var14 = new ChatComponentTranslation(var15.getMessage(), var15.getErrorOjbects());
                            var14.getChatStyle().setColor(EnumChatFormatting.RED);
                            p_71556_1_.addChatMessage(var14);
                        }
                    }
                    var3[var6] = var9;
                }
                else {
                    try {
                        var5.processCommand(p_71556_1_, var3);
                        ++var7;
                    }
                    catch (CommandException var17) {
                        final ChatComponentTranslation var16 = new ChatComponentTranslation(var17.getMessage(), var17.getErrorOjbects());
                        var16.getChatStyle().setColor(EnumChatFormatting.RED);
                        p_71556_1_.addChatMessage(var16);
                    }
                }
            }
            else {
                final ChatComponentTranslation var18 = new ChatComponentTranslation("commands.generic.permission", new Object[0]);
                var18.getChatStyle().setColor(EnumChatFormatting.RED);
                p_71556_1_.addChatMessage(var18);
            }
        }
        catch (WrongUsageException var19) {
            final ChatComponentTranslation var16 = new ChatComponentTranslation("commands.generic.usage", new Object[] { new ChatComponentTranslation(var19.getMessage(), var19.getErrorOjbects()) });
            var16.getChatStyle().setColor(EnumChatFormatting.RED);
            p_71556_1_.addChatMessage(var16);
        }
        catch (CommandException var20) {
            final ChatComponentTranslation var16 = new ChatComponentTranslation(var20.getMessage(), var20.getErrorOjbects());
            var16.getChatStyle().setColor(EnumChatFormatting.RED);
            p_71556_1_.addChatMessage(var16);
        }
        catch (Throwable var21) {
            final ChatComponentTranslation var16 = new ChatComponentTranslation("commands.generic.exception", new Object[0]);
            var16.getChatStyle().setColor(EnumChatFormatting.RED);
            p_71556_1_.addChatMessage(var16);
            CommandHandler.logger.error("Couldn't process command: '" + p_71556_2_ + "'", var21);
        }
        return var7;
    }
    
    public ICommand registerCommand(final ICommand p_71560_1_) {
        final List var2 = p_71560_1_.getCommandAliases();
        this.commandMap.put(p_71560_1_.getCommandName(), p_71560_1_);
        this.commandSet.add(p_71560_1_);
        if (var2 != null) {
            for (final String var4 : var2) {
                final ICommand var5 = this.commandMap.get(var4);
                if (var5 == null || !var5.getCommandName().equals(var4)) {
                    this.commandMap.put(var4, p_71560_1_);
                }
            }
        }
        return p_71560_1_;
    }
    
    private static String[] dropFirstString(final String[] p_71559_0_) {
        final String[] var1 = new String[p_71559_0_.length - 1];
        for (int var2 = 1; var2 < p_71559_0_.length; ++var2) {
            var1[var2 - 1] = p_71559_0_[var2];
        }
        return var1;
    }
    
    @Override
    public List getPossibleCommands(final ICommandSender p_71558_1_, final String p_71558_2_) {
        final String[] var3 = p_71558_2_.split(" ", -1);
        final String var4 = var3[0];
        if (var3.length == 1) {
            final ArrayList var5 = new ArrayList();
            for (final Map.Entry var7 : this.commandMap.entrySet()) {
                if (CommandBase.doesStringStartWith(var4, var7.getKey()) && var7.getValue().canCommandSenderUseCommand(p_71558_1_)) {
                    var5.add(var7.getKey());
                }
            }
            return var5;
        }
        if (var3.length > 1) {
            final ICommand var8 = this.commandMap.get(var4);
            if (var8 != null) {
                return var8.addTabCompletionOptions(p_71558_1_, dropFirstString(var3));
            }
        }
        return null;
    }
    
    @Override
    public List getPossibleCommands(final ICommandSender p_71557_1_) {
        final ArrayList var2 = new ArrayList();
        for (final ICommand var4 : this.commandSet) {
            if (var4.canCommandSenderUseCommand(p_71557_1_)) {
                var2.add(var4);
            }
        }
        return var2;
    }
    
    @Override
    public Map getCommands() {
        return this.commandMap;
    }
    
    private int getUsernameIndex(final ICommand p_82370_1_, final String[] p_82370_2_) {
        if (p_82370_1_ == null) {
            return -1;
        }
        for (int var3 = 0; var3 < p_82370_2_.length; ++var3) {
            if (p_82370_1_.isUsernameIndex(p_82370_2_, var3) && PlayerSelector.matchesMultiplePlayers(p_82370_2_[var3])) {
                return var3;
            }
        }
        return -1;
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
