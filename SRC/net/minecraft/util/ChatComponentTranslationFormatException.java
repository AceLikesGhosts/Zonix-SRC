package net.minecraft.util;

public class ChatComponentTranslationFormatException extends IllegalArgumentException
{
    private static final String __OBFID = "CL_00001271";
    
    public ChatComponentTranslationFormatException(final ChatComponentTranslation p_i45161_1_, final String p_i45161_2_) {
        super(String.format("Error parsing: %s: %s", p_i45161_1_, p_i45161_2_));
    }
    
    public ChatComponentTranslationFormatException(final ChatComponentTranslation p_i45162_1_, final int p_i45162_2_) {
        super(String.format("Invalid index %d requested for %s", p_i45162_2_, p_i45162_1_));
    }
    
    public ChatComponentTranslationFormatException(final ChatComponentTranslation p_i45163_1_, final Throwable p_i45163_2_) {
        super(String.format("Error while parsing: %s", p_i45163_1_), p_i45163_2_);
    }
}
