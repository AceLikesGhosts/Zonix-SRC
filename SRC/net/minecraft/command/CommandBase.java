package net.minecraft.command;

import com.google.common.primitives.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import java.util.*;

public abstract class CommandBase implements ICommand
{
    private static IAdminCommand theAdmin;
    private static final String __OBFID = "CL_00001739";
    
    public int getRequiredPermissionLevel() {
        return 4;
    }
    
    @Override
    public List getCommandAliases() {
        return null;
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender p_71519_1_) {
        return p_71519_1_.canCommandSenderUseCommand(this.getRequiredPermissionLevel(), this.getCommandName());
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        return null;
    }
    
    public static int parseInt(final ICommandSender p_71526_0_, final String p_71526_1_) {
        try {
            return Integer.parseInt(p_71526_1_);
        }
        catch (NumberFormatException var3) {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { p_71526_1_ });
        }
    }
    
    public static int parseIntWithMin(final ICommandSender p_71528_0_, final String p_71528_1_, final int p_71528_2_) {
        return parseIntBounded(p_71528_0_, p_71528_1_, p_71528_2_, Integer.MAX_VALUE);
    }
    
    public static int parseIntBounded(final ICommandSender p_71532_0_, final String p_71532_1_, final int p_71532_2_, final int p_71532_3_) {
        final int var4 = parseInt(p_71532_0_, p_71532_1_);
        if (var4 < p_71532_2_) {
            throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[] { var4, p_71532_2_ });
        }
        if (var4 > p_71532_3_) {
            throw new NumberInvalidException("commands.generic.num.tooBig", new Object[] { var4, p_71532_3_ });
        }
        return var4;
    }
    
    public static double parseDouble(final ICommandSender p_82363_0_, final String p_82363_1_) {
        try {
            final double var2 = Double.parseDouble(p_82363_1_);
            if (!Doubles.isFinite(var2)) {
                throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { p_82363_1_ });
            }
            return var2;
        }
        catch (NumberFormatException var3) {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { p_82363_1_ });
        }
    }
    
    public static double parseDoubleWithMin(final ICommandSender p_110664_0_, final String p_110664_1_, final double p_110664_2_) {
        return parseDoubleBounded(p_110664_0_, p_110664_1_, p_110664_2_, Double.MAX_VALUE);
    }
    
    public static double parseDoubleBounded(final ICommandSender p_110661_0_, final String p_110661_1_, final double p_110661_2_, final double p_110661_4_) {
        final double var6 = parseDouble(p_110661_0_, p_110661_1_);
        if (var6 < p_110661_2_) {
            throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[] { var6, p_110661_2_ });
        }
        if (var6 > p_110661_4_) {
            throw new NumberInvalidException("commands.generic.double.tooBig", new Object[] { var6, p_110661_4_ });
        }
        return var6;
    }
    
    public static boolean parseBoolean(final ICommandSender p_110662_0_, final String p_110662_1_) {
        if (p_110662_1_.equals("true") || p_110662_1_.equals("1")) {
            return true;
        }
        if (!p_110662_1_.equals("false") && !p_110662_1_.equals("0")) {
            throw new CommandException("commands.generic.boolean.invalid", new Object[] { p_110662_1_ });
        }
        return false;
    }
    
    public static EntityPlayerMP getCommandSenderAsPlayer(final ICommandSender p_71521_0_) {
        if (p_71521_0_ instanceof EntityPlayerMP) {
            return (EntityPlayerMP)p_71521_0_;
        }
        throw new PlayerNotFoundException("You must specify which player you wish to perform this action on.", new Object[0]);
    }
    
    public static EntityPlayerMP getPlayer(final ICommandSender p_82359_0_, final String p_82359_1_) {
        EntityPlayerMP var2 = PlayerSelector.matchOnePlayer(p_82359_0_, p_82359_1_);
        if (var2 != null) {
            return var2;
        }
        var2 = MinecraftServer.getServer().getConfigurationManager().func_152612_a(p_82359_1_);
        if (var2 == null) {
            throw new PlayerNotFoundException();
        }
        return var2;
    }
    
    public static String func_96332_d(final ICommandSender p_96332_0_, final String p_96332_1_) {
        final EntityPlayerMP var2 = PlayerSelector.matchOnePlayer(p_96332_0_, p_96332_1_);
        if (var2 != null) {
            return var2.getCommandSenderName();
        }
        if (PlayerSelector.hasArguments(p_96332_1_)) {
            throw new PlayerNotFoundException();
        }
        return p_96332_1_;
    }
    
    public static IChatComponent func_147178_a(final ICommandSender p_147178_0_, final String[] p_147178_1_, final int p_147178_2_) {
        return func_147176_a(p_147178_0_, p_147178_1_, p_147178_2_, false);
    }
    
    public static IChatComponent func_147176_a(final ICommandSender p_147176_0_, final String[] p_147176_1_, final int p_147176_2_, final boolean p_147176_3_) {
        final ChatComponentText var4 = new ChatComponentText("");
        for (int var5 = p_147176_2_; var5 < p_147176_1_.length; ++var5) {
            if (var5 > p_147176_2_) {
                var4.appendText(" ");
            }
            Object var6 = new ChatComponentText(p_147176_1_[var5]);
            if (p_147176_3_) {
                final IChatComponent var7 = PlayerSelector.func_150869_b(p_147176_0_, p_147176_1_[var5]);
                if (var7 != null) {
                    var6 = var7;
                }
                else if (PlayerSelector.hasArguments(p_147176_1_[var5])) {
                    throw new PlayerNotFoundException();
                }
            }
            var4.appendSibling((IChatComponent)var6);
        }
        return var4;
    }
    
    public static String func_82360_a(final ICommandSender p_82360_0_, final String[] p_82360_1_, final int p_82360_2_) {
        final StringBuilder var3 = new StringBuilder();
        for (int var4 = p_82360_2_; var4 < p_82360_1_.length; ++var4) {
            if (var4 > p_82360_2_) {
                var3.append(" ");
            }
            final String var5 = p_82360_1_[var4];
            var3.append(var5);
        }
        return var3.toString();
    }
    
    public static double func_110666_a(final ICommandSender p_110666_0_, final double p_110666_1_, final String p_110666_3_) {
        return func_110665_a(p_110666_0_, p_110666_1_, p_110666_3_, -30000000, 30000000);
    }
    
    public static double func_110665_a(final ICommandSender p_110665_0_, final double p_110665_1_, String p_110665_3_, final int p_110665_4_, final int p_110665_5_) {
        final boolean var6 = p_110665_3_.startsWith("~");
        if (var6 && Double.isNaN(p_110665_1_)) {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { p_110665_1_ });
        }
        double var7 = var6 ? p_110665_1_ : 0.0;
        if (!var6 || p_110665_3_.length() > 1) {
            final boolean var8 = p_110665_3_.contains(".");
            if (var6) {
                p_110665_3_ = p_110665_3_.substring(1);
            }
            var7 += parseDouble(p_110665_0_, p_110665_3_);
            if (!var8 && !var6) {
                var7 += 0.5;
            }
        }
        if (p_110665_4_ != 0 || p_110665_5_ != 0) {
            if (var7 < p_110665_4_) {
                throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[] { var7, p_110665_4_ });
            }
            if (var7 > p_110665_5_) {
                throw new NumberInvalidException("commands.generic.double.tooBig", new Object[] { var7, p_110665_5_ });
            }
        }
        return var7;
    }
    
    public static Item getItemByText(final ICommandSender p_147179_0_, final String p_147179_1_) {
        Item var2 = (Item)Item.itemRegistry.getObject(p_147179_1_);
        if (var2 == null) {
            try {
                final Item var3 = Item.getItemById(Integer.parseInt(p_147179_1_));
                if (var3 != null) {
                    final ChatComponentTranslation var4 = new ChatComponentTranslation("commands.generic.deprecatedId", new Object[] { Item.itemRegistry.getNameForObject(var3) });
                    var4.getChatStyle().setColor(EnumChatFormatting.GRAY);
                    p_147179_0_.addChatMessage(var4);
                }
                var2 = var3;
            }
            catch (NumberFormatException ex) {}
        }
        if (var2 == null) {
            throw new NumberInvalidException("commands.give.notFound", new Object[] { p_147179_1_ });
        }
        return var2;
    }
    
    public static Block getBlockByText(final ICommandSender p_147180_0_, final String p_147180_1_) {
        if (Block.blockRegistry.containsKey(p_147180_1_)) {
            return (Block)Block.blockRegistry.getObject(p_147180_1_);
        }
        try {
            final int var2 = Integer.parseInt(p_147180_1_);
            if (Block.blockRegistry.containsID(var2)) {
                final Block var3 = Block.getBlockById(var2);
                final ChatComponentTranslation var4 = new ChatComponentTranslation("commands.generic.deprecatedId", new Object[] { Block.blockRegistry.getNameForObject(var3) });
                var4.getChatStyle().setColor(EnumChatFormatting.GRAY);
                p_147180_0_.addChatMessage(var4);
                return var3;
            }
        }
        catch (NumberFormatException ex) {}
        throw new NumberInvalidException("commands.give.notFound", new Object[] { p_147180_1_ });
    }
    
    public static String joinNiceString(final Object[] p_71527_0_) {
        final StringBuilder var1 = new StringBuilder();
        for (int var2 = 0; var2 < p_71527_0_.length; ++var2) {
            final String var3 = p_71527_0_[var2].toString();
            if (var2 > 0) {
                if (var2 == p_71527_0_.length - 1) {
                    var1.append(" and ");
                }
                else {
                    var1.append(", ");
                }
            }
            var1.append(var3);
        }
        return var1.toString();
    }
    
    public static IChatComponent joinNiceString(final IChatComponent[] p_147177_0_) {
        final ChatComponentText var1 = new ChatComponentText("");
        for (int var2 = 0; var2 < p_147177_0_.length; ++var2) {
            if (var2 > 0) {
                if (var2 == p_147177_0_.length - 1) {
                    var1.appendText(" and ");
                }
                else if (var2 > 0) {
                    var1.appendText(", ");
                }
            }
            var1.appendSibling(p_147177_0_[var2]);
        }
        return var1;
    }
    
    public static String joinNiceStringFromCollection(final Collection p_96333_0_) {
        return joinNiceString(p_96333_0_.toArray(new String[p_96333_0_.size()]));
    }
    
    public static boolean doesStringStartWith(final String p_71523_0_, final String p_71523_1_) {
        return p_71523_1_.regionMatches(true, 0, p_71523_0_, 0, p_71523_0_.length());
    }
    
    public static List getListOfStringsMatchingLastWord(final String[] p_71530_0_, final String... p_71530_1_) {
        final String var2 = p_71530_0_[p_71530_0_.length - 1];
        final ArrayList var3 = new ArrayList();
        final String[] var4 = p_71530_1_;
        for (int var5 = p_71530_1_.length, var6 = 0; var6 < var5; ++var6) {
            final String var7 = var4[var6];
            if (doesStringStartWith(var2, var7)) {
                var3.add(var7);
            }
        }
        return var3;
    }
    
    public static List getListOfStringsFromIterableMatchingLastWord(final String[] p_71531_0_, final Iterable p_71531_1_) {
        final String var2 = p_71531_0_[p_71531_0_.length - 1];
        final ArrayList var3 = new ArrayList();
        for (final String var5 : p_71531_1_) {
            if (doesStringStartWith(var2, var5)) {
                var3.add(var5);
            }
        }
        return var3;
    }
    
    @Override
    public boolean isUsernameIndex(final String[] p_82358_1_, final int p_82358_2_) {
        return false;
    }
    
    public static void func_152373_a(final ICommandSender p_152373_0_, final ICommand p_152373_1_, final String p_152373_2_, final Object... p_152373_3_) {
        func_152374_a(p_152373_0_, p_152373_1_, 0, p_152373_2_, p_152373_3_);
    }
    
    public static void func_152374_a(final ICommandSender p_152374_0_, final ICommand p_152374_1_, final int p_152374_2_, final String p_152374_3_, final Object... p_152374_4_) {
        if (CommandBase.theAdmin != null) {
            CommandBase.theAdmin.func_152372_a(p_152374_0_, p_152374_1_, p_152374_2_, p_152374_3_, p_152374_4_);
        }
    }
    
    public static void setAdminCommander(final IAdminCommand p_71529_0_) {
        CommandBase.theAdmin = p_71529_0_;
    }
    
    public int compareTo(final ICommand p_compareTo_1_) {
        return this.getCommandName().compareTo(p_compareTo_1_.getCommandName());
    }
    
    @Override
    public int compareTo(final Object p_compareTo_1_) {
        return this.compareTo((ICommand)p_compareTo_1_);
    }
}
