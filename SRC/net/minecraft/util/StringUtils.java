package net.minecraft.util;

import java.util.regex.*;

public class StringUtils
{
    private static final Pattern patternControlCode;
    private static final String __OBFID = "CL_00001501";
    
    public static String ticksToElapsedTime(final int p_76337_0_) {
        int var1 = p_76337_0_ / 20;
        final int var2 = var1 / 60;
        var1 %= 60;
        return (var1 < 10) ? (var2 + ":0" + var1) : (var2 + ":" + var1);
    }
    
    public static String stripControlCodes(final String p_76338_0_) {
        return StringUtils.patternControlCode.matcher(p_76338_0_).replaceAll("");
    }
    
    public static String stripCtrl(final String s) {
        return s.replaceAll("(?i)§[0-9a-fklmnor]", "");
    }
    
    public static boolean isNullOrEmpty(final String p_151246_0_) {
        return p_151246_0_ == null || "".equals(p_151246_0_);
    }
    
    static {
        patternControlCode = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");
    }
}
