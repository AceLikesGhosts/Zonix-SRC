package com.thevoxelbox.voxelmap.util;

import net.minecraft.client.*;
import net.minecraft.util.*;

public class ChatUtils
{
    public static void chatInfo(final String s) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(s));
    }
}
