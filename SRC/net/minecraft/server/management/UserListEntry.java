package net.minecraft.server.management;

import com.google.gson.*;

public class UserListEntry
{
    private final Object field_152642_a;
    private static final String __OBFID = "CL_00001877";
    
    public UserListEntry(final Object p_i1146_1_) {
        this.field_152642_a = p_i1146_1_;
    }
    
    protected UserListEntry(final Object p_i1147_1_, final JsonObject p_i1147_2_) {
        this.field_152642_a = p_i1147_1_;
    }
    
    Object func_152640_f() {
        return this.field_152642_a;
    }
    
    boolean hasBanExpired() {
        return false;
    }
    
    protected void func_152641_a(final JsonObject p_152641_1_) {
    }
}
