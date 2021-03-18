package net.minecraft.command;

import net.minecraft.server.*;
import java.text.*;
import java.io.*;
import net.minecraft.profiler.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class CommandDebug extends CommandBase
{
    private static final Logger logger;
    private long field_147206_b;
    private int field_147207_c;
    private static final String __OBFID = "CL_00000270";
    
    @Override
    public String getCommandName() {
        return "debug";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.debug.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length == 1) {
            if (p_71515_2_[0].equals("start")) {
                CommandBase.func_152373_a(p_71515_1_, this, "commands.debug.start", new Object[0]);
                MinecraftServer.getServer().enableProfiling();
                this.field_147206_b = MinecraftServer.getSystemTimeMillis();
                this.field_147207_c = MinecraftServer.getServer().getTickCounter();
                return;
            }
            if (p_71515_2_[0].equals("stop")) {
                if (!MinecraftServer.getServer().theProfiler.profilingEnabled) {
                    throw new CommandException("commands.debug.notStarted", new Object[0]);
                }
                final long var3 = MinecraftServer.getSystemTimeMillis();
                final int var4 = MinecraftServer.getServer().getTickCounter();
                final long var5 = var3 - this.field_147206_b;
                final int var6 = var4 - this.field_147207_c;
                this.func_147205_a(var5, var6);
                MinecraftServer.getServer().theProfiler.profilingEnabled = false;
                CommandBase.func_152373_a(p_71515_1_, this, "commands.debug.stop", var5 / 1000.0f, var6);
                return;
            }
        }
        throw new WrongUsageException("commands.debug.usage", new Object[0]);
    }
    
    private void func_147205_a(final long p_147205_1_, final int p_147205_3_) {
        final File var4 = new File(MinecraftServer.getServer().getFile("debug"), "profile-results-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + ".txt");
        var4.getParentFile().mkdirs();
        try {
            final FileWriter var5 = new FileWriter(var4);
            var5.write(this.func_147204_b(p_147205_1_, p_147205_3_));
            var5.close();
        }
        catch (Throwable var6) {
            CommandDebug.logger.error("Could not save profiler results to " + var4, var6);
        }
    }
    
    private String func_147204_b(final long p_147204_1_, final int p_147204_3_) {
        final StringBuilder var4 = new StringBuilder();
        var4.append("---- Minecraft Profiler Results ----\n");
        var4.append("// ");
        var4.append(func_147203_d());
        var4.append("\n\n");
        var4.append("Time span: ").append(p_147204_1_).append(" ms\n");
        var4.append("Tick span: ").append(p_147204_3_).append(" ticks\n");
        var4.append("// This is approximately ").append(String.format("%.2f", p_147204_3_ / (p_147204_1_ / 1000.0f))).append(" ticks per second. It should be ").append(20).append(" ticks per second\n\n");
        var4.append("--- BEGIN PROFILE DUMP ---\n\n");
        this.func_147202_a(0, "root", var4);
        var4.append("--- END PROFILE DUMP ---\n\n");
        return var4.toString();
    }
    
    private void func_147202_a(final int p_147202_1_, final String p_147202_2_, final StringBuilder p_147202_3_) {
        final List var4 = MinecraftServer.getServer().theProfiler.getProfilingData(p_147202_2_);
        if (var4 != null && var4.size() >= 3) {
            for (int var5 = 1; var5 < var4.size(); ++var5) {
                final Profiler.Result var6 = var4.get(var5);
                p_147202_3_.append(String.format("[%02d] ", p_147202_1_));
                for (int var7 = 0; var7 < p_147202_1_; ++var7) {
                    p_147202_3_.append(" ");
                }
                p_147202_3_.append(var6.field_76331_c);
                p_147202_3_.append(" - ");
                p_147202_3_.append(String.format("%.2f", var6.field_76332_a));
                p_147202_3_.append("%/");
                p_147202_3_.append(String.format("%.2f", var6.field_76330_b));
                p_147202_3_.append("%\n");
                if (!var6.field_76331_c.equals("unspecified")) {
                    try {
                        this.func_147202_a(p_147202_1_ + 1, p_147202_2_ + "." + var6.field_76331_c, p_147202_3_);
                    }
                    catch (Exception var8) {
                        p_147202_3_.append("[[ EXCEPTION " + var8 + " ]]");
                    }
                }
            }
        }
    }
    
    private static String func_147203_d() {
        final String[] var0 = { "Shiny numbers!", "Am I not running fast enough? :(", "I'm working as hard as I can!", "Will I ever be good enough for you? :(", "Speedy. Zoooooom!", "Hello world", "40% better than a crash report.", "Now with extra numbers", "Now with less numbers", "Now with the same numbers", "You should add flames to things, it makes them go faster!", "Do you feel the need for... optimization?", "*cracks redstone whip*", "Maybe if you treated it better then it'll have more motivation to work faster! Poor server." };
        try {
            return var0[(int)(System.nanoTime() % var0.length)];
        }
        catch (Throwable var2) {
            return "Witty comment unavailable :(";
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        return (p_71516_2_.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, "start", "stop") : null;
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
