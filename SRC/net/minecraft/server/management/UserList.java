package net.minecraft.server.management;

import com.google.common.collect.*;
import com.google.common.base.*;
import com.google.common.io.*;
import org.apache.commons.io.*;
import java.io.*;
import org.apache.logging.log4j.*;
import java.lang.reflect.*;
import java.util.*;
import com.google.gson.*;

public class UserList
{
    protected static final Logger field_152693_a;
    protected final Gson field_152694_b;
    private final File field_152695_c;
    private final Map field_152696_d;
    private boolean field_152697_e;
    private static final ParameterizedType field_152698_f;
    private static final String __OBFID = "CL_00001876";
    
    public UserList(final File p_i1144_1_) {
        this.field_152696_d = Maps.newHashMap();
        this.field_152697_e = true;
        this.field_152695_c = p_i1144_1_;
        final GsonBuilder var2 = new GsonBuilder().setPrettyPrinting();
        var2.registerTypeHierarchyAdapter((Class)UserListEntry.class, (Object)new Serializer(null));
        this.field_152694_b = var2.create();
    }
    
    public boolean func_152689_b() {
        return this.field_152697_e;
    }
    
    public void func_152686_a(final boolean p_152686_1_) {
        this.field_152697_e = p_152686_1_;
    }
    
    public void func_152687_a(final UserListEntry p_152687_1_) {
        this.field_152696_d.put(this.func_152681_a(p_152687_1_.func_152640_f()), p_152687_1_);
        try {
            this.func_152678_f();
        }
        catch (IOException var3) {
            UserList.field_152693_a.warn("Could not save the list after adding a user.", (Throwable)var3);
        }
    }
    
    public UserListEntry func_152683_b(final Object p_152683_1_) {
        this.func_152680_h();
        return this.field_152696_d.get(this.func_152681_a(p_152683_1_));
    }
    
    public void func_152684_c(final Object p_152684_1_) {
        this.field_152696_d.remove(this.func_152681_a(p_152684_1_));
        try {
            this.func_152678_f();
        }
        catch (IOException var3) {
            UserList.field_152693_a.warn("Could not save the list after removing a user.", (Throwable)var3);
        }
    }
    
    public String[] func_152685_a() {
        return (String[])this.field_152696_d.keySet().toArray(new String[this.field_152696_d.size()]);
    }
    
    protected String func_152681_a(final Object p_152681_1_) {
        return p_152681_1_.toString();
    }
    
    protected boolean func_152692_d(final Object p_152692_1_) {
        return this.field_152696_d.containsKey(this.func_152681_a(p_152692_1_));
    }
    
    private void func_152680_h() {
        final ArrayList var1 = Lists.newArrayList();
        for (final UserListEntry var3 : this.field_152696_d.values()) {
            if (var3.hasBanExpired()) {
                var1.add(var3.func_152640_f());
            }
        }
        for (final Object var4 : var1) {
            this.field_152696_d.remove(var4);
        }
    }
    
    protected UserListEntry func_152682_a(final JsonObject p_152682_1_) {
        return new UserListEntry(null, p_152682_1_);
    }
    
    protected Map func_152688_e() {
        return this.field_152696_d;
    }
    
    public void func_152678_f() throws IOException {
        final Collection var1 = this.field_152696_d.values();
        final String var2 = this.field_152694_b.toJson((Object)var1);
        BufferedWriter var3 = null;
        try {
            var3 = Files.newWriter(this.field_152695_c, Charsets.UTF_8);
            var3.write(var2);
        }
        finally {
            IOUtils.closeQuietly((Writer)var3);
        }
    }
    
    static {
        field_152693_a = LogManager.getLogger();
        field_152698_f = new ParameterizedType() {
            private static final String __OBFID = "CL_00001875";
            
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[] { UserListEntry.class };
            }
            
            @Override
            public Type getRawType() {
                return List.class;
            }
            
            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }
    
    class Serializer implements JsonDeserializer, JsonSerializer
    {
        private static final String __OBFID = "CL_00001874";
        
        private Serializer() {
        }
        
        public JsonElement func_152751_a(final UserListEntry p_152751_1_, final Type p_152751_2_, final JsonSerializationContext p_152751_3_) {
            final JsonObject var4 = new JsonObject();
            p_152751_1_.func_152641_a(var4);
            return (JsonElement)var4;
        }
        
        public UserListEntry func_152750_a(final JsonElement p_152750_1_, final Type p_152750_2_, final JsonDeserializationContext p_152750_3_) {
            if (p_152750_1_.isJsonObject()) {
                final JsonObject var4 = p_152750_1_.getAsJsonObject();
                final UserListEntry var5 = UserList.this.func_152682_a(var4);
                return var5;
            }
            return null;
        }
        
        public JsonElement serialize(final Object p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
            return this.func_152751_a((UserListEntry)p_serialize_1_, p_serialize_2_, p_serialize_3_);
        }
        
        public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
            return this.func_152750_a(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
        }
        
        Serializer(final UserList this$0, final Object p_i1141_2_) {
            this(this$0);
        }
    }
}
