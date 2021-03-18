package net.minecraft.command.server;

import net.minecraft.entity.player.*;
import net.minecraft.server.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import net.minecraft.scoreboard.*;
import java.util.*;

public class CommandScoreboard extends CommandBase
{
    private static final String __OBFID = "CL_00000896";
    
    @Override
    public String getCommandName() {
        return "scoreboard";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.scoreboard.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length >= 1) {
            if (p_71515_2_[0].equalsIgnoreCase("objectives")) {
                if (p_71515_2_.length == 1) {
                    throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
                }
                if (p_71515_2_[1].equalsIgnoreCase("list")) {
                    this.func_147196_d(p_71515_1_);
                }
                else if (p_71515_2_[1].equalsIgnoreCase("add")) {
                    if (p_71515_2_.length < 4) {
                        throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
                    }
                    this.func_147193_c(p_71515_1_, p_71515_2_, 2);
                }
                else if (p_71515_2_[1].equalsIgnoreCase("remove")) {
                    if (p_71515_2_.length != 3) {
                        throw new WrongUsageException("commands.scoreboard.objectives.remove.usage", new Object[0]);
                    }
                    this.func_147191_h(p_71515_1_, p_71515_2_[2]);
                }
                else {
                    if (!p_71515_2_[1].equalsIgnoreCase("setdisplay")) {
                        throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
                    }
                    if (p_71515_2_.length != 3 && p_71515_2_.length != 4) {
                        throw new WrongUsageException("commands.scoreboard.objectives.setdisplay.usage", new Object[0]);
                    }
                    this.func_147198_k(p_71515_1_, p_71515_2_, 2);
                }
                return;
            }
            else if (p_71515_2_[0].equalsIgnoreCase("players")) {
                if (p_71515_2_.length == 1) {
                    throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
                }
                if (p_71515_2_[1].equalsIgnoreCase("list")) {
                    if (p_71515_2_.length > 3) {
                        throw new WrongUsageException("commands.scoreboard.players.list.usage", new Object[0]);
                    }
                    this.func_147195_l(p_71515_1_, p_71515_2_, 2);
                }
                else if (p_71515_2_[1].equalsIgnoreCase("add")) {
                    if (p_71515_2_.length != 5) {
                        throw new WrongUsageException("commands.scoreboard.players.add.usage", new Object[0]);
                    }
                    this.func_147197_m(p_71515_1_, p_71515_2_, 2);
                }
                else if (p_71515_2_[1].equalsIgnoreCase("remove")) {
                    if (p_71515_2_.length != 5) {
                        throw new WrongUsageException("commands.scoreboard.players.remove.usage", new Object[0]);
                    }
                    this.func_147197_m(p_71515_1_, p_71515_2_, 2);
                }
                else if (p_71515_2_[1].equalsIgnoreCase("set")) {
                    if (p_71515_2_.length != 5) {
                        throw new WrongUsageException("commands.scoreboard.players.set.usage", new Object[0]);
                    }
                    this.func_147197_m(p_71515_1_, p_71515_2_, 2);
                }
                else {
                    if (!p_71515_2_[1].equalsIgnoreCase("reset")) {
                        throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
                    }
                    if (p_71515_2_.length != 3) {
                        throw new WrongUsageException("commands.scoreboard.players.reset.usage", new Object[0]);
                    }
                    this.func_147187_n(p_71515_1_, p_71515_2_, 2);
                }
                return;
            }
            else if (p_71515_2_[0].equalsIgnoreCase("teams")) {
                if (p_71515_2_.length == 1) {
                    throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
                }
                if (p_71515_2_[1].equalsIgnoreCase("list")) {
                    if (p_71515_2_.length > 3) {
                        throw new WrongUsageException("commands.scoreboard.teams.list.usage", new Object[0]);
                    }
                    this.func_147186_g(p_71515_1_, p_71515_2_, 2);
                }
                else if (p_71515_2_[1].equalsIgnoreCase("add")) {
                    if (p_71515_2_.length < 3) {
                        throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
                    }
                    this.func_147185_d(p_71515_1_, p_71515_2_, 2);
                }
                else if (p_71515_2_[1].equalsIgnoreCase("remove")) {
                    if (p_71515_2_.length != 3) {
                        throw new WrongUsageException("commands.scoreboard.teams.remove.usage", new Object[0]);
                    }
                    this.func_147194_f(p_71515_1_, p_71515_2_, 2);
                }
                else if (p_71515_2_[1].equalsIgnoreCase("empty")) {
                    if (p_71515_2_.length != 3) {
                        throw new WrongUsageException("commands.scoreboard.teams.empty.usage", new Object[0]);
                    }
                    this.func_147188_j(p_71515_1_, p_71515_2_, 2);
                }
                else if (p_71515_2_[1].equalsIgnoreCase("join")) {
                    if (p_71515_2_.length < 4 && (p_71515_2_.length != 3 || !(p_71515_1_ instanceof EntityPlayer))) {
                        throw new WrongUsageException("commands.scoreboard.teams.join.usage", new Object[0]);
                    }
                    this.func_147190_h(p_71515_1_, p_71515_2_, 2);
                }
                else if (p_71515_2_[1].equalsIgnoreCase("leave")) {
                    if (p_71515_2_.length < 3 && !(p_71515_1_ instanceof EntityPlayer)) {
                        throw new WrongUsageException("commands.scoreboard.teams.leave.usage", new Object[0]);
                    }
                    this.func_147199_i(p_71515_1_, p_71515_2_, 2);
                }
                else {
                    if (!p_71515_2_[1].equalsIgnoreCase("option")) {
                        throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
                    }
                    if (p_71515_2_.length != 4 && p_71515_2_.length != 5) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
                    }
                    this.func_147200_e(p_71515_1_, p_71515_2_, 2);
                }
                return;
            }
        }
        throw new WrongUsageException("commands.scoreboard.usage", new Object[0]);
    }
    
    protected Scoreboard func_147192_d() {
        return MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
    }
    
    protected ScoreObjective func_147189_a(final String p_147189_1_, final boolean p_147189_2_) {
        final Scoreboard var3 = this.func_147192_d();
        final ScoreObjective var4 = var3.getObjective(p_147189_1_);
        if (var4 == null) {
            throw new CommandException("commands.scoreboard.objectiveNotFound", new Object[] { p_147189_1_ });
        }
        if (p_147189_2_ && var4.getCriteria().isReadOnly()) {
            throw new CommandException("commands.scoreboard.objectiveReadOnly", new Object[] { p_147189_1_ });
        }
        return var4;
    }
    
    protected ScorePlayerTeam func_147183_a(final String p_147183_1_) {
        final Scoreboard var2 = this.func_147192_d();
        final ScorePlayerTeam var3 = var2.getTeam(p_147183_1_);
        if (var3 == null) {
            throw new CommandException("commands.scoreboard.teamNotFound", new Object[] { p_147183_1_ });
        }
        return var3;
    }
    
    protected void func_147193_c(final ICommandSender p_147193_1_, final String[] p_147193_2_, int p_147193_3_) {
        final String var4 = p_147193_2_[p_147193_3_++];
        final String var5 = p_147193_2_[p_147193_3_++];
        final Scoreboard var6 = this.func_147192_d();
        final IScoreObjectiveCriteria var7 = IScoreObjectiveCriteria.field_96643_a.get(var5);
        if (var7 == null) {
            throw new WrongUsageException("commands.scoreboard.objectives.add.wrongType", new Object[] { var5 });
        }
        if (var6.getObjective(var4) != null) {
            throw new CommandException("commands.scoreboard.objectives.add.alreadyExists", new Object[] { var4 });
        }
        if (var4.length() > 16) {
            throw new SyntaxErrorException("commands.scoreboard.objectives.add.tooLong", new Object[] { var4, 16 });
        }
        if (var4.length() == 0) {
            throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
        }
        if (p_147193_2_.length > p_147193_3_) {
            final String var8 = CommandBase.func_147178_a(p_147193_1_, p_147193_2_, p_147193_3_).getUnformattedText();
            if (var8.length() > 32) {
                throw new SyntaxErrorException("commands.scoreboard.objectives.add.displayTooLong", new Object[] { var8, 32 });
            }
            if (var8.length() > 0) {
                var6.addScoreObjective(var4, var7).setDisplayName(var8);
            }
            else {
                var6.addScoreObjective(var4, var7);
            }
        }
        else {
            var6.addScoreObjective(var4, var7);
        }
        CommandBase.func_152373_a(p_147193_1_, this, "commands.scoreboard.objectives.add.success", var4);
    }
    
    protected void func_147185_d(final ICommandSender p_147185_1_, final String[] p_147185_2_, int p_147185_3_) {
        final String var4 = p_147185_2_[p_147185_3_++];
        final Scoreboard var5 = this.func_147192_d();
        if (var5.getTeam(var4) != null) {
            throw new CommandException("commands.scoreboard.teams.add.alreadyExists", new Object[] { var4 });
        }
        if (var4.length() > 16) {
            throw new SyntaxErrorException("commands.scoreboard.teams.add.tooLong", new Object[] { var4, 16 });
        }
        if (var4.length() == 0) {
            throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
        }
        if (p_147185_2_.length > p_147185_3_) {
            final String var6 = CommandBase.func_147178_a(p_147185_1_, p_147185_2_, p_147185_3_).getUnformattedText();
            if (var6.length() > 32) {
                throw new SyntaxErrorException("commands.scoreboard.teams.add.displayTooLong", new Object[] { var6, 32 });
            }
            if (var6.length() > 0) {
                var5.createTeam(var4).setTeamName(var6);
            }
            else {
                var5.createTeam(var4);
            }
        }
        else {
            var5.createTeam(var4);
        }
        CommandBase.func_152373_a(p_147185_1_, this, "commands.scoreboard.teams.add.success", var4);
    }
    
    protected void func_147200_e(final ICommandSender p_147200_1_, final String[] p_147200_2_, int p_147200_3_) {
        final ScorePlayerTeam var4 = this.func_147183_a(p_147200_2_[p_147200_3_++]);
        if (var4 != null) {
            final String var5 = p_147200_2_[p_147200_3_++].toLowerCase();
            if (!var5.equalsIgnoreCase("color") && !var5.equalsIgnoreCase("friendlyfire") && !var5.equalsIgnoreCase("seeFriendlyInvisibles")) {
                throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
            }
            if (p_147200_2_.length == 4) {
                if (var5.equalsIgnoreCase("color")) {
                    throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { var5, CommandBase.joinNiceStringFromCollection(EnumChatFormatting.getValidValues(true, false)) });
                }
                if (!var5.equalsIgnoreCase("friendlyfire") && !var5.equalsIgnoreCase("seeFriendlyInvisibles")) {
                    throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
                }
                throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { var5, CommandBase.joinNiceStringFromCollection(Arrays.asList("true", "false")) });
            }
            else {
                final String var6 = p_147200_2_[p_147200_3_++];
                if (var5.equalsIgnoreCase("color")) {
                    final EnumChatFormatting var7 = EnumChatFormatting.getValueByName(var6);
                    if (var7 == null || var7.isFancyStyling()) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { var5, CommandBase.joinNiceStringFromCollection(EnumChatFormatting.getValidValues(true, false)) });
                    }
                    var4.setNamePrefix(var7.toString());
                    var4.setNameSuffix(EnumChatFormatting.RESET.toString());
                }
                else if (var5.equalsIgnoreCase("friendlyfire")) {
                    if (!var6.equalsIgnoreCase("true") && !var6.equalsIgnoreCase("false")) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { var5, CommandBase.joinNiceStringFromCollection(Arrays.asList("true", "false")) });
                    }
                    var4.setAllowFriendlyFire(var6.equalsIgnoreCase("true"));
                }
                else if (var5.equalsIgnoreCase("seeFriendlyInvisibles")) {
                    if (!var6.equalsIgnoreCase("true") && !var6.equalsIgnoreCase("false")) {
                        throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { var5, CommandBase.joinNiceStringFromCollection(Arrays.asList("true", "false")) });
                    }
                    var4.setSeeFriendlyInvisiblesEnabled(var6.equalsIgnoreCase("true"));
                }
                CommandBase.func_152373_a(p_147200_1_, this, "commands.scoreboard.teams.option.success", var5, var4.getRegisteredName(), var6);
            }
        }
    }
    
    protected void func_147194_f(final ICommandSender p_147194_1_, final String[] p_147194_2_, int p_147194_3_) {
        final Scoreboard var4 = this.func_147192_d();
        final ScorePlayerTeam var5 = this.func_147183_a(p_147194_2_[p_147194_3_++]);
        if (var5 != null) {
            var4.removeTeam(var5);
            CommandBase.func_152373_a(p_147194_1_, this, "commands.scoreboard.teams.remove.success", var5.getRegisteredName());
        }
    }
    
    protected void func_147186_g(final ICommandSender p_147186_1_, final String[] p_147186_2_, int p_147186_3_) {
        final Scoreboard var4 = this.func_147192_d();
        if (p_147186_2_.length > p_147186_3_) {
            final ScorePlayerTeam var5 = this.func_147183_a(p_147186_2_[p_147186_3_++]);
            if (var5 == null) {
                return;
            }
            final Collection var6 = var5.getMembershipCollection();
            if (var6.size() <= 0) {
                throw new CommandException("commands.scoreboard.teams.list.player.empty", new Object[] { var5.getRegisteredName() });
            }
            final ChatComponentTranslation var7 = new ChatComponentTranslation("commands.scoreboard.teams.list.player.count", new Object[] { var6.size(), var5.getRegisteredName() });
            var7.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
            p_147186_1_.addChatMessage(var7);
            p_147186_1_.addChatMessage(new ChatComponentText(CommandBase.joinNiceString(var6.toArray())));
        }
        else {
            final Collection var8 = var4.getTeams();
            if (var8.size() <= 0) {
                throw new CommandException("commands.scoreboard.teams.list.empty", new Object[0]);
            }
            final ChatComponentTranslation var9 = new ChatComponentTranslation("commands.scoreboard.teams.list.count", new Object[] { var8.size() });
            var9.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
            p_147186_1_.addChatMessage(var9);
            for (final ScorePlayerTeam var11 : var8) {
                p_147186_1_.addChatMessage(new ChatComponentTranslation("commands.scoreboard.teams.list.entry", new Object[] { var11.getRegisteredName(), var11.func_96669_c(), var11.getMembershipCollection().size() }));
            }
        }
    }
    
    protected void func_147190_h(final ICommandSender p_147190_1_, final String[] p_147190_2_, int p_147190_3_) {
        final Scoreboard var4 = this.func_147192_d();
        final String var5 = p_147190_2_[p_147190_3_++];
        final HashSet var6 = new HashSet();
        final HashSet var7 = new HashSet();
        if (p_147190_1_ instanceof EntityPlayer && p_147190_3_ == p_147190_2_.length) {
            final String var8 = CommandBase.getCommandSenderAsPlayer(p_147190_1_).getCommandSenderName();
            if (var4.func_151392_a(var8, var5)) {
                var6.add(var8);
            }
            else {
                var7.add(var8);
            }
        }
        else {
            while (p_147190_3_ < p_147190_2_.length) {
                final String var8 = CommandBase.func_96332_d(p_147190_1_, p_147190_2_[p_147190_3_++]);
                if (var4.func_151392_a(var8, var5)) {
                    var6.add(var8);
                }
                else {
                    var7.add(var8);
                }
            }
        }
        if (!var6.isEmpty()) {
            CommandBase.func_152373_a(p_147190_1_, this, "commands.scoreboard.teams.join.success", var6.size(), var5, CommandBase.joinNiceString(var6.toArray(new String[0])));
        }
        if (!var7.isEmpty()) {
            throw new CommandException("commands.scoreboard.teams.join.failure", new Object[] { var7.size(), var5, CommandBase.joinNiceString(var7.toArray(new String[0])) });
        }
    }
    
    protected void func_147199_i(final ICommandSender p_147199_1_, final String[] p_147199_2_, int p_147199_3_) {
        final Scoreboard var4 = this.func_147192_d();
        final HashSet var5 = new HashSet();
        final HashSet var6 = new HashSet();
        if (p_147199_1_ instanceof EntityPlayer && p_147199_3_ == p_147199_2_.length) {
            final String var7 = CommandBase.getCommandSenderAsPlayer(p_147199_1_).getCommandSenderName();
            if (var4.func_96524_g(var7)) {
                var5.add(var7);
            }
            else {
                var6.add(var7);
            }
        }
        else {
            while (p_147199_3_ < p_147199_2_.length) {
                final String var7 = CommandBase.func_96332_d(p_147199_1_, p_147199_2_[p_147199_3_++]);
                if (var4.func_96524_g(var7)) {
                    var5.add(var7);
                }
                else {
                    var6.add(var7);
                }
            }
        }
        if (!var5.isEmpty()) {
            CommandBase.func_152373_a(p_147199_1_, this, "commands.scoreboard.teams.leave.success", var5.size(), CommandBase.joinNiceString(var5.toArray(new String[0])));
        }
        if (!var6.isEmpty()) {
            throw new CommandException("commands.scoreboard.teams.leave.failure", new Object[] { var6.size(), CommandBase.joinNiceString(var6.toArray(new String[0])) });
        }
    }
    
    protected void func_147188_j(final ICommandSender p_147188_1_, final String[] p_147188_2_, int p_147188_3_) {
        final Scoreboard var4 = this.func_147192_d();
        final ScorePlayerTeam var5 = this.func_147183_a(p_147188_2_[p_147188_3_++]);
        if (var5 != null) {
            final ArrayList var6 = new ArrayList(var5.getMembershipCollection());
            if (var6.isEmpty()) {
                throw new CommandException("commands.scoreboard.teams.empty.alreadyEmpty", new Object[] { var5.getRegisteredName() });
            }
            for (final String var8 : var6) {
                var4.removePlayerFromTeam(var8, var5);
            }
            CommandBase.func_152373_a(p_147188_1_, this, "commands.scoreboard.teams.empty.success", var6.size(), var5.getRegisteredName());
        }
    }
    
    protected void func_147191_h(final ICommandSender p_147191_1_, final String p_147191_2_) {
        final Scoreboard var3 = this.func_147192_d();
        final ScoreObjective var4 = this.func_147189_a(p_147191_2_, false);
        var3.func_96519_k(var4);
        CommandBase.func_152373_a(p_147191_1_, this, "commands.scoreboard.objectives.remove.success", p_147191_2_);
    }
    
    protected void func_147196_d(final ICommandSender p_147196_1_) {
        final Scoreboard var2 = this.func_147192_d();
        final Collection var3 = var2.getScoreObjectives();
        if (var3.size() <= 0) {
            throw new CommandException("commands.scoreboard.objectives.list.empty", new Object[0]);
        }
        final ChatComponentTranslation var4 = new ChatComponentTranslation("commands.scoreboard.objectives.list.count", new Object[] { var3.size() });
        var4.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
        p_147196_1_.addChatMessage(var4);
        for (final ScoreObjective var6 : var3) {
            p_147196_1_.addChatMessage(new ChatComponentTranslation("commands.scoreboard.objectives.list.entry", new Object[] { var6.getName(), var6.getDisplayName(), var6.getCriteria().func_96636_a() }));
        }
    }
    
    protected void func_147198_k(final ICommandSender p_147198_1_, final String[] p_147198_2_, int p_147198_3_) {
        final Scoreboard var4 = this.func_147192_d();
        final String var5 = p_147198_2_[p_147198_3_++];
        final int var6 = Scoreboard.getObjectiveDisplaySlotNumber(var5);
        ScoreObjective var7 = null;
        if (p_147198_2_.length == 4) {
            var7 = this.func_147189_a(p_147198_2_[p_147198_3_++], false);
        }
        if (var6 < 0) {
            throw new CommandException("commands.scoreboard.objectives.setdisplay.invalidSlot", new Object[] { var5 });
        }
        var4.func_96530_a(var6, var7);
        if (var7 != null) {
            CommandBase.func_152373_a(p_147198_1_, this, "commands.scoreboard.objectives.setdisplay.successSet", Scoreboard.getObjectiveDisplaySlot(var6), var7.getName());
        }
        else {
            CommandBase.func_152373_a(p_147198_1_, this, "commands.scoreboard.objectives.setdisplay.successCleared", Scoreboard.getObjectiveDisplaySlot(var6));
        }
    }
    
    protected void func_147195_l(final ICommandSender p_147195_1_, final String[] p_147195_2_, int p_147195_3_) {
        final Scoreboard var4 = this.func_147192_d();
        if (p_147195_2_.length > p_147195_3_) {
            final String var5 = CommandBase.func_96332_d(p_147195_1_, p_147195_2_[p_147195_3_++]);
            final Map var6 = var4.func_96510_d(var5);
            if (var6.size() <= 0) {
                throw new CommandException("commands.scoreboard.players.list.player.empty", new Object[] { var5 });
            }
            final ChatComponentTranslation var7 = new ChatComponentTranslation("commands.scoreboard.players.list.player.count", new Object[] { var6.size(), var5 });
            var7.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
            p_147195_1_.addChatMessage(var7);
            for (final Score var9 : var6.values()) {
                p_147195_1_.addChatMessage(new ChatComponentTranslation("commands.scoreboard.players.list.player.entry", new Object[] { var9.getScorePoints(), var9.func_96645_d().getDisplayName(), var9.func_96645_d().getName() }));
            }
        }
        else {
            final Collection var10 = var4.getObjectiveNames();
            if (var10.size() <= 0) {
                throw new CommandException("commands.scoreboard.players.list.empty", new Object[0]);
            }
            final ChatComponentTranslation var11 = new ChatComponentTranslation("commands.scoreboard.players.list.count", new Object[] { var10.size() });
            var11.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
            p_147195_1_.addChatMessage(var11);
            p_147195_1_.addChatMessage(new ChatComponentText(CommandBase.joinNiceString(var10.toArray())));
        }
    }
    
    protected void func_147197_m(final ICommandSender p_147197_1_, final String[] p_147197_2_, int p_147197_3_) {
        final String var4 = p_147197_2_[p_147197_3_ - 1];
        final String var5 = CommandBase.func_96332_d(p_147197_1_, p_147197_2_[p_147197_3_++]);
        final ScoreObjective var6 = this.func_147189_a(p_147197_2_[p_147197_3_++], true);
        final int var7 = var4.equalsIgnoreCase("set") ? CommandBase.parseInt(p_147197_1_, p_147197_2_[p_147197_3_++]) : CommandBase.parseIntWithMin(p_147197_1_, p_147197_2_[p_147197_3_++], 1);
        final Scoreboard var8 = this.func_147192_d();
        final Score var9 = var8.func_96529_a(var5, var6);
        if (var4.equalsIgnoreCase("set")) {
            var9.func_96647_c(var7);
        }
        else if (var4.equalsIgnoreCase("add")) {
            var9.func_96649_a(var7);
        }
        else {
            var9.func_96646_b(var7);
        }
        CommandBase.func_152373_a(p_147197_1_, this, "commands.scoreboard.players.set.success", var6.getName(), var5, var9.getScorePoints());
    }
    
    protected void func_147187_n(final ICommandSender p_147187_1_, final String[] p_147187_2_, int p_147187_3_) {
        final Scoreboard var4 = this.func_147192_d();
        final String var5 = CommandBase.func_96332_d(p_147187_1_, p_147187_2_[p_147187_3_++]);
        var4.func_96515_c(var5);
        CommandBase.func_152373_a(p_147187_1_, this, "commands.scoreboard.players.reset.success", var5);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        if (p_71516_2_.length == 1) {
            return CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, "objectives", "players", "teams");
        }
        if (p_71516_2_[0].equalsIgnoreCase("objectives")) {
            if (p_71516_2_.length == 2) {
                return CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, "list", "add", "remove", "setdisplay");
            }
            if (p_71516_2_[1].equalsIgnoreCase("add")) {
                if (p_71516_2_.length == 4) {
                    final Set var3 = IScoreObjectiveCriteria.field_96643_a.keySet();
                    return CommandBase.getListOfStringsFromIterableMatchingLastWord(p_71516_2_, var3);
                }
            }
            else if (p_71516_2_[1].equalsIgnoreCase("remove")) {
                if (p_71516_2_.length == 3) {
                    return CommandBase.getListOfStringsFromIterableMatchingLastWord(p_71516_2_, this.func_147184_a(false));
                }
            }
            else if (p_71516_2_[1].equalsIgnoreCase("setdisplay")) {
                if (p_71516_2_.length == 3) {
                    return CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, "list", "sidebar", "belowName");
                }
                if (p_71516_2_.length == 4) {
                    return CommandBase.getListOfStringsFromIterableMatchingLastWord(p_71516_2_, this.func_147184_a(false));
                }
            }
        }
        else if (p_71516_2_[0].equalsIgnoreCase("players")) {
            if (p_71516_2_.length == 2) {
                return CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, "set", "add", "remove", "reset", "list");
            }
            if (!p_71516_2_[1].equalsIgnoreCase("set") && !p_71516_2_[1].equalsIgnoreCase("add") && !p_71516_2_[1].equalsIgnoreCase("remove")) {
                if ((p_71516_2_[1].equalsIgnoreCase("reset") || p_71516_2_[1].equalsIgnoreCase("list")) && p_71516_2_.length == 3) {
                    return CommandBase.getListOfStringsFromIterableMatchingLastWord(p_71516_2_, this.func_147192_d().getObjectiveNames());
                }
            }
            else {
                if (p_71516_2_.length == 3) {
                    return CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getAllUsernames());
                }
                if (p_71516_2_.length == 4) {
                    return CommandBase.getListOfStringsFromIterableMatchingLastWord(p_71516_2_, this.func_147184_a(true));
                }
            }
        }
        else if (p_71516_2_[0].equalsIgnoreCase("teams")) {
            if (p_71516_2_.length == 2) {
                return CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, "add", "remove", "join", "leave", "empty", "list", "option");
            }
            if (p_71516_2_[1].equalsIgnoreCase("join")) {
                if (p_71516_2_.length == 3) {
                    return CommandBase.getListOfStringsFromIterableMatchingLastWord(p_71516_2_, this.func_147192_d().getTeamNames());
                }
                if (p_71516_2_.length >= 4) {
                    return CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getAllUsernames());
                }
            }
            else {
                if (p_71516_2_[1].equalsIgnoreCase("leave")) {
                    return CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getAllUsernames());
                }
                if (!p_71516_2_[1].equalsIgnoreCase("empty") && !p_71516_2_[1].equalsIgnoreCase("list") && !p_71516_2_[1].equalsIgnoreCase("remove")) {
                    if (p_71516_2_[1].equalsIgnoreCase("option")) {
                        if (p_71516_2_.length == 3) {
                            return CommandBase.getListOfStringsFromIterableMatchingLastWord(p_71516_2_, this.func_147192_d().getTeamNames());
                        }
                        if (p_71516_2_.length == 4) {
                            return CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, "color", "friendlyfire", "seeFriendlyInvisibles");
                        }
                        if (p_71516_2_.length == 5) {
                            if (p_71516_2_[3].equalsIgnoreCase("color")) {
                                return CommandBase.getListOfStringsFromIterableMatchingLastWord(p_71516_2_, EnumChatFormatting.getValidValues(true, false));
                            }
                            if (p_71516_2_[3].equalsIgnoreCase("friendlyfire") || p_71516_2_[3].equalsIgnoreCase("seeFriendlyInvisibles")) {
                                return CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, "true", "false");
                            }
                        }
                    }
                }
                else if (p_71516_2_.length == 3) {
                    return CommandBase.getListOfStringsFromIterableMatchingLastWord(p_71516_2_, this.func_147192_d().getTeamNames());
                }
            }
        }
        return null;
    }
    
    protected List func_147184_a(final boolean p_147184_1_) {
        final Collection var2 = this.func_147192_d().getScoreObjectives();
        final ArrayList var3 = new ArrayList();
        for (final ScoreObjective var5 : var2) {
            if (!p_147184_1_ || !var5.getCriteria().isReadOnly()) {
                var3.add(var5.getName());
            }
        }
        return var3;
    }
    
    @Override
    public boolean isUsernameIndex(final String[] p_82358_1_, final int p_82358_2_) {
        return p_82358_1_[0].equalsIgnoreCase("players") ? (p_82358_2_ == 2) : (p_82358_1_[0].equalsIgnoreCase("teams") && (p_82358_2_ == 2 || p_82358_2_ == 3));
    }
}
