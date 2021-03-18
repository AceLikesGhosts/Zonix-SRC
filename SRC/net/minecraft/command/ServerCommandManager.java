package net.minecraft.command;

import net.minecraft.server.*;
import net.minecraft.command.server.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.rcon.*;
import net.minecraft.util.*;
import java.util.*;

public class ServerCommandManager extends CommandHandler implements IAdminCommand
{
    private static final String __OBFID = "CL_00000922";
    
    public ServerCommandManager() {
        this.registerCommand(new CommandTime());
        this.registerCommand(new CommandGameMode());
        this.registerCommand(new CommandDifficulty());
        this.registerCommand(new CommandDefaultGameMode());
        this.registerCommand(new CommandKill());
        this.registerCommand(new CommandToggleDownfall());
        this.registerCommand(new CommandWeather());
        this.registerCommand(new CommandXP());
        this.registerCommand(new CommandTeleport());
        this.registerCommand(new CommandGive());
        this.registerCommand(new CommandEffect());
        this.registerCommand(new CommandEnchant());
        this.registerCommand(new CommandEmote());
        this.registerCommand(new CommandShowSeed());
        this.registerCommand(new CommandHelp());
        this.registerCommand(new CommandDebug());
        this.registerCommand(new CommandMessage());
        this.registerCommand(new CommandBroadcast());
        this.registerCommand(new CommandSetSpawnpoint());
        this.registerCommand(new CommandSetDefaultSpawnpoint());
        this.registerCommand(new CommandGameRule());
        this.registerCommand(new CommandClearInventory());
        this.registerCommand(new CommandTestFor());
        this.registerCommand(new CommandSpreadPlayers());
        this.registerCommand(new CommandPlaySound());
        this.registerCommand(new CommandScoreboard());
        this.registerCommand(new CommandAchievement());
        this.registerCommand(new CommandSummon());
        this.registerCommand(new CommandSetBlock());
        this.registerCommand(new CommandTestForBlock());
        this.registerCommand(new CommandMessageRaw());
        if (MinecraftServer.getServer().isDedicatedServer()) {
            this.registerCommand(new CommandOp());
            this.registerCommand(new CommandDeOp());
            this.registerCommand(new CommandStop());
            this.registerCommand(new CommandSaveAll());
            this.registerCommand(new CommandSaveOff());
            this.registerCommand(new CommandSaveOn());
            this.registerCommand(new CommandBanIp());
            this.registerCommand(new CommandPardonIp());
            this.registerCommand(new CommandBanPlayer());
            this.registerCommand(new CommandListBans());
            this.registerCommand(new CommandPardonPlayer());
            this.registerCommand(new CommandServerKick());
            this.registerCommand(new CommandListPlayers());
            this.registerCommand(new CommandWhitelist());
            this.registerCommand(new CommandSetPlayerTimeout());
            this.registerCommand(new CommandNetstat());
        }
        else {
            this.registerCommand(new CommandPublishLocalServer());
        }
        CommandBase.setAdminCommander(this);
    }
    
    @Override
    public void func_152372_a(final ICommandSender p_152372_1_, final ICommand p_152372_2_, final int p_152372_3_, final String p_152372_4_, final Object... p_152372_5_) {
        boolean var6 = true;
        if (p_152372_1_ instanceof CommandBlockLogic && !MinecraftServer.getServer().worldServers[0].getGameRules().getGameRuleBooleanValue("commandBlockOutput")) {
            var6 = false;
        }
        final ChatComponentTranslation var7 = new ChatComponentTranslation("chat.type.admin", new Object[] { p_152372_1_.getCommandSenderName(), new ChatComponentTranslation(p_152372_4_, p_152372_5_) });
        var7.getChatStyle().setColor(EnumChatFormatting.GRAY);
        var7.getChatStyle().setItalic(true);
        if (var6) {
            for (final EntityPlayer var9 : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
                if (var9 != p_152372_1_ && MinecraftServer.getServer().getConfigurationManager().func_152596_g(var9.getGameProfile()) && p_152372_2_.canCommandSenderUseCommand(var9) && (!(p_152372_1_ instanceof RConConsoleSource) || MinecraftServer.getServer().func_152363_m())) {
                    var9.addChatMessage(var7);
                }
            }
        }
        if (p_152372_1_ != MinecraftServer.getServer()) {
            MinecraftServer.getServer().addChatMessage(var7);
        }
        if ((p_152372_3_ & 0x1) != 0x1) {
            p_152372_1_.addChatMessage(new ChatComponentTranslation(p_152372_4_, p_152372_5_));
        }
    }
}
