package net.minecraft.network;

import java.lang.reflect.*;
import net.minecraft.util.*;
import com.mojang.authlib.*;
import java.util.*;
import com.google.gson.*;

public class ServerStatusResponse
{
    private IChatComponent field_151326_a;
    private PlayerCountData field_151324_b;
    private MinecraftProtocolVersionIdentifier field_151325_c;
    private String field_151323_d;
    private static final String __OBFID = "CL_00001385";
    
    public IChatComponent func_151317_a() {
        return this.field_151326_a;
    }
    
    public void func_151315_a(final IChatComponent p_151315_1_) {
        this.field_151326_a = p_151315_1_;
    }
    
    public PlayerCountData func_151318_b() {
        return this.field_151324_b;
    }
    
    public void func_151319_a(final PlayerCountData p_151319_1_) {
        this.field_151324_b = p_151319_1_;
    }
    
    public MinecraftProtocolVersionIdentifier func_151322_c() {
        return this.field_151325_c;
    }
    
    public void func_151321_a(final MinecraftProtocolVersionIdentifier p_151321_1_) {
        this.field_151325_c = p_151321_1_;
    }
    
    public void func_151320_a(final String p_151320_1_) {
        this.field_151323_d = p_151320_1_;
    }
    
    public String func_151316_d() {
        return this.field_151323_d;
    }
    
    public static class MinecraftProtocolVersionIdentifier
    {
        private final String field_151306_a;
        private final int field_151305_b;
        private static final String __OBFID = "CL_00001389";
        
        public MinecraftProtocolVersionIdentifier(final String p_i45275_1_, final int p_i45275_2_) {
            this.field_151306_a = p_i45275_1_;
            this.field_151305_b = p_i45275_2_;
        }
        
        public String func_151303_a() {
            return this.field_151306_a;
        }
        
        public int func_151304_b() {
            return this.field_151305_b;
        }
        
        public static class Serializer implements JsonDeserializer, JsonSerializer
        {
            private static final String __OBFID = "CL_00001390";
            
            public MinecraftProtocolVersionIdentifier deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
                final JsonObject var4 = JsonUtils.getJsonElementAsJsonObject(p_deserialize_1_, "version");
                return new MinecraftProtocolVersionIdentifier(JsonUtils.getJsonObjectStringFieldValue(var4, "name"), JsonUtils.getJsonObjectIntegerFieldValue(var4, "protocol"));
            }
            
            public JsonElement serialize(final MinecraftProtocolVersionIdentifier p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
                final JsonObject var4 = new JsonObject();
                var4.addProperty("name", p_serialize_1_.func_151303_a());
                var4.addProperty("protocol", (Number)p_serialize_1_.func_151304_b());
                return (JsonElement)var4;
            }
            
            public JsonElement serialize(final Object p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
                return this.serialize((MinecraftProtocolVersionIdentifier)p_serialize_1_, p_serialize_2_, p_serialize_3_);
            }
        }
    }
    
    public static class PlayerCountData
    {
        private final int field_151336_a;
        private final int field_151334_b;
        private GameProfile[] field_151335_c;
        private static final String __OBFID = "CL_00001386";
        
        public PlayerCountData(final int p_i45274_1_, final int p_i45274_2_) {
            this.field_151336_a = p_i45274_1_;
            this.field_151334_b = p_i45274_2_;
        }
        
        public int func_151332_a() {
            return this.field_151336_a;
        }
        
        public int func_151333_b() {
            return this.field_151334_b;
        }
        
        public GameProfile[] func_151331_c() {
            return this.field_151335_c;
        }
        
        public void func_151330_a(final GameProfile[] p_151330_1_) {
            this.field_151335_c = p_151330_1_;
        }
        
        public static class Serializer implements JsonDeserializer, JsonSerializer
        {
            private static final String __OBFID = "CL_00001387";
            
            public PlayerCountData deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
                final JsonObject var4 = JsonUtils.getJsonElementAsJsonObject(p_deserialize_1_, "players");
                final PlayerCountData var5 = new PlayerCountData(JsonUtils.getJsonObjectIntegerFieldValue(var4, "max"), JsonUtils.getJsonObjectIntegerFieldValue(var4, "online"));
                if (JsonUtils.jsonObjectFieldTypeIsArray(var4, "sample")) {
                    final JsonArray var6 = JsonUtils.getJsonObjectJsonArrayField(var4, "sample");
                    if (var6.size() > 0) {
                        final GameProfile[] var7 = new GameProfile[var6.size()];
                        for (int var8 = 0; var8 < var7.length; ++var8) {
                            final JsonObject var9 = JsonUtils.getJsonElementAsJsonObject(var6.get(var8), "player[" + var8 + "]");
                            final String var10 = JsonUtils.getJsonObjectStringFieldValue(var9, "id");
                            var7[var8] = new GameProfile(UUID.fromString(var10), JsonUtils.getJsonObjectStringFieldValue(var9, "name"));
                        }
                        var5.func_151330_a(var7);
                    }
                }
                return var5;
            }
            
            public JsonElement serialize(final PlayerCountData p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
                final JsonObject var4 = new JsonObject();
                var4.addProperty("max", (Number)p_serialize_1_.func_151332_a());
                var4.addProperty("online", (Number)p_serialize_1_.func_151333_b());
                if (p_serialize_1_.func_151331_c() != null && p_serialize_1_.func_151331_c().length > 0) {
                    final JsonArray var5 = new JsonArray();
                    for (int var6 = 0; var6 < p_serialize_1_.func_151331_c().length; ++var6) {
                        final JsonObject var7 = new JsonObject();
                        final UUID var8 = p_serialize_1_.func_151331_c()[var6].getId();
                        var7.addProperty("id", (var8 == null) ? "" : var8.toString());
                        var7.addProperty("name", p_serialize_1_.func_151331_c()[var6].getName());
                        var5.add((JsonElement)var7);
                    }
                    var4.add("sample", (JsonElement)var5);
                }
                return (JsonElement)var4;
            }
            
            public JsonElement serialize(final Object p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
                return this.serialize((PlayerCountData)p_serialize_1_, p_serialize_2_, p_serialize_3_);
            }
        }
    }
    
    public static class Serializer implements JsonDeserializer, JsonSerializer
    {
        private static final String __OBFID = "CL_00001388";
        
        public ServerStatusResponse deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
            final JsonObject var4 = JsonUtils.getJsonElementAsJsonObject(p_deserialize_1_, "status");
            final ServerStatusResponse var5 = new ServerStatusResponse();
            if (var4.has("description")) {
                var5.func_151315_a((IChatComponent)p_deserialize_3_.deserialize(var4.get("description"), (Type)IChatComponent.class));
            }
            if (var4.has("players")) {
                var5.func_151319_a((PlayerCountData)p_deserialize_3_.deserialize(var4.get("players"), (Type)PlayerCountData.class));
            }
            if (var4.has("version")) {
                var5.func_151321_a((MinecraftProtocolVersionIdentifier)p_deserialize_3_.deserialize(var4.get("version"), (Type)MinecraftProtocolVersionIdentifier.class));
            }
            if (var4.has("favicon")) {
                var5.func_151320_a(JsonUtils.getJsonObjectStringFieldValue(var4, "favicon"));
            }
            return var5;
        }
        
        public JsonElement serialize(final ServerStatusResponse p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
            final JsonObject var4 = new JsonObject();
            if (p_serialize_1_.func_151317_a() != null) {
                var4.add("description", p_serialize_3_.serialize((Object)p_serialize_1_.func_151317_a()));
            }
            if (p_serialize_1_.func_151318_b() != null) {
                var4.add("players", p_serialize_3_.serialize((Object)p_serialize_1_.func_151318_b()));
            }
            if (p_serialize_1_.func_151322_c() != null) {
                var4.add("version", p_serialize_3_.serialize((Object)p_serialize_1_.func_151322_c()));
            }
            if (p_serialize_1_.func_151316_d() != null) {
                var4.addProperty("favicon", p_serialize_1_.func_151316_d());
            }
            return (JsonElement)var4;
        }
        
        public JsonElement serialize(final Object p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
            return this.serialize((ServerStatusResponse)p_serialize_1_, p_serialize_2_, p_serialize_3_);
        }
    }
}
