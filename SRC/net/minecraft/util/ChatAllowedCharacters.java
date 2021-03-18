package net.minecraft.util;

public class ChatAllowedCharacters
{
    public static final char[] allowedCharacters;
    private static final String __OBFID = "CL_00001606";
    
    public static boolean isAllowedCharacter(final char p_71566_0_) {
        return p_71566_0_ != '§' && p_71566_0_ >= ' ' && p_71566_0_ != '\u007f';
    }
    
    public static String filerAllowedCharacters(final String p_71565_0_) {
        final StringBuilder var1 = new StringBuilder();
        for (final char var5 : p_71565_0_.toCharArray()) {
            if (isAllowedCharacter(var5)) {
                var1.append(var5);
            }
        }
        return var1.toString();
    }
    
    static {
        allowedCharacters = new char[] { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' };
    }
}
