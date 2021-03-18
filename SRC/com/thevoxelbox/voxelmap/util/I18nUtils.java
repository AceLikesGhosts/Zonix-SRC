package com.thevoxelbox.voxelmap.util;

import net.minecraft.client.resources.*;
import java.text.*;
import net.minecraft.client.*;
import java.util.*;

public class I18nUtils
{
    public static String getString(final String translateMe) {
        return I18n.format(translateMe, new Object[0]);
    }
    
    public static Collator getLocaleAwareCollator() {
        String mcLocale = "en_US";
        try {
            mcLocale = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
        }
        catch (NullPointerException ex) {}
        final String[] bits = mcLocale.split("_");
        final Locale locale = new Locale(bits[0], (bits.length > 1) ? bits[1] : "");
        return Collator.getInstance(locale);
    }
}
