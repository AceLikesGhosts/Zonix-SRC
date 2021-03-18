package net.minecraft.server.management;

import com.mojang.authlib.*;
import com.google.gson.*;
import java.util.*;

public class UserListWhitelistEntry extends UserListEntry
{
    private static final String __OBFID = "CL_00001870";
    
    public UserListWhitelistEntry(final GameProfile p_i1129_1_) {
        super(p_i1129_1_);
    }
    
    public UserListWhitelistEntry(final JsonObject p_i1130_1_) {
        super(func_152646_b(p_i1130_1_), p_i1130_1_);
    }
    
    @Override
    protected void func_152641_a(final JsonObject p_152641_1_) {
        if (this.func_152640_f() != null) {
            p_152641_1_.addProperty("uuid", (((GameProfile)this.func_152640_f()).getId() == null) ? "" : ((GameProfile)this.func_152640_f()).getId().toString());
            p_152641_1_.addProperty("name", ((GameProfile)this.func_152640_f()).getName());
            super.func_152641_a(p_152641_1_);
        }
    }
    
    private static GameProfile func_152646_b(final JsonObject p_152646_0_) {
        if (p_152646_0_.has("uuid") && p_152646_0_.has("name")) {
            final String var1 = p_152646_0_.get("uuid").getAsString();
            UUID var2;
            try {
                var2 = UUID.fromString(var1);
            }
            catch (Throwable var3) {
                return null;
            }
            return new GameProfile(var2, p_152646_0_.get("name").getAsString());
        }
        return null;
    }
}
