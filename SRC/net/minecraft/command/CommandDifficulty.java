package net.minecraft.command;

import net.minecraft.server.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import java.util.*;

public class CommandDifficulty extends CommandBase
{
    private static final String __OBFID = "CL_00000422";
    
    @Override
    public String getCommandName() {
        return "difficulty";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.difficulty.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length > 0) {
            final EnumDifficulty var3 = this.func_147201_h(p_71515_1_, p_71515_2_[0]);
            MinecraftServer.getServer().func_147139_a(var3);
            CommandBase.func_152373_a(p_71515_1_, this, "commands.difficulty.success", new ChatComponentTranslation(var3.getDifficultyResourceKey(), new Object[0]));
            return;
        }
        throw new WrongUsageException("commands.difficulty.usage", new Object[0]);
    }
    
    protected EnumDifficulty func_147201_h(final ICommandSender p_147201_1_, final String p_147201_2_) {
        return (!p_147201_2_.equalsIgnoreCase("peaceful") && !p_147201_2_.equalsIgnoreCase("p")) ? ((!p_147201_2_.equalsIgnoreCase("easy") && !p_147201_2_.equalsIgnoreCase("e")) ? ((!p_147201_2_.equalsIgnoreCase("normal") && !p_147201_2_.equalsIgnoreCase("n")) ? ((!p_147201_2_.equalsIgnoreCase("hard") && !p_147201_2_.equalsIgnoreCase("h")) ? EnumDifficulty.getDifficultyEnum(CommandBase.parseIntBounded(p_147201_1_, p_147201_2_, 0, 3)) : EnumDifficulty.HARD) : EnumDifficulty.NORMAL) : EnumDifficulty.EASY) : EnumDifficulty.PEACEFUL;
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        return (p_71516_2_.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, "peaceful", "easy", "normal", "hard") : null;
    }
}
