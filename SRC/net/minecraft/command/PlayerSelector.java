package net.minecraft.command;

import net.minecraft.entity.player.*;
import net.minecraft.server.*;
import java.util.regex.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import java.util.*;

public class PlayerSelector
{
    private static final Pattern tokenPattern;
    private static final Pattern intListPattern;
    private static final Pattern keyValueListPattern;
    private static final String __OBFID = "CL_00000086";
    
    public static EntityPlayerMP matchOnePlayer(final ICommandSender p_82386_0_, final String p_82386_1_) {
        final EntityPlayerMP[] var2 = matchPlayers(p_82386_0_, p_82386_1_);
        return (var2 != null && var2.length == 1) ? var2[0] : null;
    }
    
    public static IChatComponent func_150869_b(final ICommandSender p_150869_0_, final String p_150869_1_) {
        final EntityPlayerMP[] var2 = matchPlayers(p_150869_0_, p_150869_1_);
        if (var2 != null && var2.length != 0) {
            final IChatComponent[] var3 = new IChatComponent[var2.length];
            for (int var4 = 0; var4 < var3.length; ++var4) {
                var3[var4] = var2[var4].func_145748_c_();
            }
            return CommandBase.joinNiceString(var3);
        }
        return null;
    }
    
    public static EntityPlayerMP[] matchPlayers(final ICommandSender p_82380_0_, final String p_82380_1_) {
        final Matcher var2 = PlayerSelector.tokenPattern.matcher(p_82380_1_);
        if (!var2.matches()) {
            return null;
        }
        final Map var3 = getArgumentMap(var2.group(2));
        final String var4 = var2.group(1);
        int var5 = getDefaultMinimumRange(var4);
        int var6 = getDefaultMaximumRange(var4);
        int var7 = getDefaultMinimumLevel(var4);
        int var8 = getDefaultMaximumLevel(var4);
        int var9 = getDefaultCount(var4);
        int var10 = WorldSettings.GameType.NOT_SET.getID();
        final ChunkCoordinates var11 = p_82380_0_.getPlayerCoordinates();
        final Map var12 = func_96560_a(var3);
        String var13 = null;
        String var14 = null;
        boolean var15 = false;
        if (var3.containsKey("rm")) {
            var5 = MathHelper.parseIntWithDefault(var3.get("rm"), var5);
            var15 = true;
        }
        if (var3.containsKey("r")) {
            var6 = MathHelper.parseIntWithDefault(var3.get("r"), var6);
            var15 = true;
        }
        if (var3.containsKey("lm")) {
            var7 = MathHelper.parseIntWithDefault(var3.get("lm"), var7);
        }
        if (var3.containsKey("l")) {
            var8 = MathHelper.parseIntWithDefault(var3.get("l"), var8);
        }
        if (var3.containsKey("x")) {
            var11.posX = MathHelper.parseIntWithDefault(var3.get("x"), var11.posX);
            var15 = true;
        }
        if (var3.containsKey("y")) {
            var11.posY = MathHelper.parseIntWithDefault(var3.get("y"), var11.posY);
            var15 = true;
        }
        if (var3.containsKey("z")) {
            var11.posZ = MathHelper.parseIntWithDefault(var3.get("z"), var11.posZ);
            var15 = true;
        }
        if (var3.containsKey("m")) {
            var10 = MathHelper.parseIntWithDefault(var3.get("m"), var10);
        }
        if (var3.containsKey("c")) {
            var9 = MathHelper.parseIntWithDefault(var3.get("c"), var9);
        }
        if (var3.containsKey("team")) {
            var14 = var3.get("team");
        }
        if (var3.containsKey("name")) {
            var13 = var3.get("name");
        }
        final World var16 = var15 ? p_82380_0_.getEntityWorld() : null;
        if (var4.equals("p") || var4.equals("a")) {
            final List var17 = MinecraftServer.getServer().getConfigurationManager().findPlayers(var11, var5, var6, var9, var10, var7, var8, var12, var13, var14, var16);
            return var17.isEmpty() ? new EntityPlayerMP[0] : var17.toArray(new EntityPlayerMP[var17.size()]);
        }
        if (var4.equals("r")) {
            List var17 = MinecraftServer.getServer().getConfigurationManager().findPlayers(var11, var5, var6, 0, var10, var7, var8, var12, var13, var14, var16);
            Collections.shuffle(var17);
            var17 = var17.subList(0, Math.min(var9, var17.size()));
            return var17.isEmpty() ? new EntityPlayerMP[0] : var17.toArray(new EntityPlayerMP[var17.size()]);
        }
        return null;
    }
    
    public static Map func_96560_a(final Map p_96560_0_) {
        final HashMap var1 = new HashMap();
        for (final String var3 : p_96560_0_.keySet()) {
            if (var3.startsWith("score_") && var3.length() > "score_".length()) {
                final String var4 = var3.substring("score_".length());
                var1.put(var4, MathHelper.parseIntWithDefault(p_96560_0_.get(var3), 1));
            }
        }
        return var1;
    }
    
    public static boolean matchesMultiplePlayers(final String p_82377_0_) {
        final Matcher var1 = PlayerSelector.tokenPattern.matcher(p_82377_0_);
        if (var1.matches()) {
            final Map var2 = getArgumentMap(var1.group(2));
            final String var3 = var1.group(1);
            int var4 = getDefaultCount(var3);
            if (var2.containsKey("c")) {
                var4 = MathHelper.parseIntWithDefault(var2.get("c"), var4);
            }
            return var4 != 1;
        }
        return false;
    }
    
    public static boolean hasTheseArguments(final String p_82383_0_, final String p_82383_1_) {
        final Matcher var2 = PlayerSelector.tokenPattern.matcher(p_82383_0_);
        if (var2.matches()) {
            final String var3 = var2.group(1);
            return p_82383_1_ == null || p_82383_1_.equals(var3);
        }
        return false;
    }
    
    public static boolean hasArguments(final String p_82378_0_) {
        return hasTheseArguments(p_82378_0_, null);
    }
    
    private static final int getDefaultMinimumRange(final String p_82384_0_) {
        return 0;
    }
    
    private static final int getDefaultMaximumRange(final String p_82379_0_) {
        return 0;
    }
    
    private static final int getDefaultMaximumLevel(final String p_82376_0_) {
        return Integer.MAX_VALUE;
    }
    
    private static final int getDefaultMinimumLevel(final String p_82375_0_) {
        return 0;
    }
    
    private static final int getDefaultCount(final String p_82382_0_) {
        return p_82382_0_.equals("a") ? 0 : 1;
    }
    
    private static Map getArgumentMap(final String p_82381_0_) {
        final HashMap var1 = new HashMap();
        if (p_82381_0_ == null) {
            return var1;
        }
        Matcher var2 = PlayerSelector.intListPattern.matcher(p_82381_0_);
        int var3 = 0;
        int var4 = -1;
        while (var2.find()) {
            String var5 = null;
            switch (var3++) {
                case 0: {
                    var5 = "x";
                    break;
                }
                case 1: {
                    var5 = "y";
                    break;
                }
                case 2: {
                    var5 = "z";
                    break;
                }
                case 3: {
                    var5 = "r";
                    break;
                }
            }
            if (var5 != null && var2.group(1).length() > 0) {
                var1.put(var5, var2.group(1));
            }
            var4 = var2.end();
        }
        if (var4 < p_82381_0_.length()) {
            var2 = PlayerSelector.keyValueListPattern.matcher((var4 == -1) ? p_82381_0_ : p_82381_0_.substring(var4));
            while (var2.find()) {
                var1.put(var2.group(1), var2.group(2));
            }
        }
        return var1;
    }
    
    static {
        tokenPattern = Pattern.compile("^@([parf])(?:\\[([\\w=,!-]*)\\])?$");
        intListPattern = Pattern.compile("\\G([-!]?[\\w-]*)(?:$|,)");
        keyValueListPattern = Pattern.compile("\\G(\\w+)=([-!]?[\\w-]*)(?:$|,)");
    }
}
