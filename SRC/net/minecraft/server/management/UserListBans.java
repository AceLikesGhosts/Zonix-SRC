package net.minecraft.server.management;

import java.io.*;
import com.google.gson.*;
import com.mojang.authlib.*;
import java.util.*;

public class UserListBans extends UserList
{
    private static final String __OBFID = "CL_00001873";
    
    public UserListBans(final File p_i1138_1_) {
        super(p_i1138_1_);
    }
    
    @Override
    protected UserListEntry func_152682_a(final JsonObject p_152682_1_) {
        return new UserListBansEntry(p_152682_1_);
    }
    
    public boolean func_152702_a(final GameProfile p_152702_1_) {
        return this.func_152692_d(p_152702_1_);
    }
    
    @Override
    public String[] func_152685_a() {
        final String[] var1 = new String[this.func_152688_e().size()];
        int var2 = 0;
        for (final UserListBansEntry var4 : this.func_152688_e().values()) {
            var1[var2++] = ((GameProfile)var4.func_152640_f()).getName();
        }
        return var1;
    }
    
    protected String func_152701_b(final GameProfile p_152701_1_) {
        return p_152701_1_.getId().toString();
    }
    
    public GameProfile func_152703_a(final String p_152703_1_) {
        for (final UserListBansEntry var3 : this.func_152688_e().values()) {
            if (p_152703_1_.equalsIgnoreCase(((GameProfile)var3.func_152640_f()).getName())) {
                return (GameProfile)var3.func_152640_f();
            }
        }
        return null;
    }
    
    @Override
    protected String func_152681_a(final Object p_152681_1_) {
        return this.func_152701_b((GameProfile)p_152681_1_);
    }
}
