package net.minecraft.util;

import java.lang.reflect.*;
import java.util.*;
import com.google.gson.*;

public interface IChatComponent extends Iterable
{
    IChatComponent setChatStyle(final ChatStyle p0);
    
    ChatStyle getChatStyle();
    
    IChatComponent appendText(final String p0);
    
    IChatComponent appendSibling(final IChatComponent p0);
    
    String getUnformattedTextForChat();
    
    String getUnformattedText();
    
    String getFormattedText();
    
    List getSiblings();
    
    IChatComponent createCopy();
    
    public static class Serializer implements JsonDeserializer, JsonSerializer
    {
        private static final Gson field_150700_a;
        private static final String __OBFID = "CL_00001263";
        
        public IChatComponent deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
            if (p_deserialize_1_.isJsonPrimitive()) {
                return new ChatComponentText(p_deserialize_1_.getAsString());
            }
            if (p_deserialize_1_.isJsonObject()) {
                final JsonObject var4 = p_deserialize_1_.getAsJsonObject();
                Object var5;
                if (var4.has("text")) {
                    var5 = new ChatComponentText(var4.get("text").getAsString());
                }
                else {
                    if (!var4.has("translate")) {
                        throw new JsonParseException("Don't know how to turn " + p_deserialize_1_.toString() + " into a Component");
                    }
                    final String var6 = var4.get("translate").getAsString();
                    if (var4.has("with")) {
                        final JsonArray var7 = var4.getAsJsonArray("with");
                        final Object[] var8 = new Object[var7.size()];
                        for (int var9 = 0; var9 < var8.length; ++var9) {
                            var8[var9] = this.deserialize(var7.get(var9), p_deserialize_2_, p_deserialize_3_);
                            if (var8[var9] instanceof ChatComponentText) {
                                final ChatComponentText var10 = (ChatComponentText)var8[var9];
                                if (var10.getChatStyle().isEmpty() && var10.getSiblings().isEmpty()) {
                                    var8[var9] = var10.getChatComponentText_TextValue();
                                }
                            }
                        }
                        var5 = new ChatComponentTranslation(var6, var8);
                    }
                    else {
                        var5 = new ChatComponentTranslation(var6, new Object[0]);
                    }
                }
                if (var4.has("extra")) {
                    final JsonArray var11 = var4.getAsJsonArray("extra");
                    if (var11.size() <= 0) {
                        throw new JsonParseException("Unexpected empty array of components");
                    }
                    for (int var12 = 0; var12 < var11.size(); ++var12) {
                        ((IChatComponent)var5).appendSibling(this.deserialize(var11.get(var12), p_deserialize_2_, p_deserialize_3_));
                    }
                }
                ((IChatComponent)var5).setChatStyle((ChatStyle)p_deserialize_3_.deserialize(p_deserialize_1_, (Type)ChatStyle.class));
                return (IChatComponent)var5;
            }
            if (p_deserialize_1_.isJsonArray()) {
                final JsonArray var13 = p_deserialize_1_.getAsJsonArray();
                IChatComponent var14 = null;
                for (final JsonElement var16 : var13) {
                    final IChatComponent var17 = this.deserialize(var16, var16.getClass(), p_deserialize_3_);
                    if (var14 == null) {
                        var14 = var17;
                    }
                    else {
                        var14.appendSibling(var17);
                    }
                }
                return var14;
            }
            throw new JsonParseException("Don't know how to turn " + p_deserialize_1_.toString() + " into a Component");
        }
        
        private void func_150695_a(final ChatStyle p_150695_1_, final JsonObject p_150695_2_, final JsonSerializationContext p_150695_3_) {
            final JsonElement var4 = p_150695_3_.serialize((Object)p_150695_1_);
            if (var4.isJsonObject()) {
                final JsonObject var5 = (JsonObject)var4;
                for (final Map.Entry var7 : var5.entrySet()) {
                    p_150695_2_.add((String)var7.getKey(), (JsonElement)var7.getValue());
                }
            }
        }
        
        public JsonElement serialize(final IChatComponent p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
            if (p_serialize_1_ instanceof ChatComponentText && p_serialize_1_.getChatStyle().isEmpty() && p_serialize_1_.getSiblings().isEmpty()) {
                return (JsonElement)new JsonPrimitive(((ChatComponentText)p_serialize_1_).getChatComponentText_TextValue());
            }
            final JsonObject var4 = new JsonObject();
            if (!p_serialize_1_.getChatStyle().isEmpty()) {
                this.func_150695_a(p_serialize_1_.getChatStyle(), var4, p_serialize_3_);
            }
            if (!p_serialize_1_.getSiblings().isEmpty()) {
                final JsonArray var5 = new JsonArray();
                for (final IChatComponent var7 : p_serialize_1_.getSiblings()) {
                    var5.add(this.serialize(var7, var7.getClass(), p_serialize_3_));
                }
                var4.add("extra", (JsonElement)var5);
            }
            if (p_serialize_1_ instanceof ChatComponentText) {
                var4.addProperty("text", ((ChatComponentText)p_serialize_1_).getChatComponentText_TextValue());
            }
            else {
                if (!(p_serialize_1_ instanceof ChatComponentTranslation)) {
                    throw new IllegalArgumentException("Don't know how to serialize " + p_serialize_1_ + " as a Component");
                }
                final ChatComponentTranslation var8 = (ChatComponentTranslation)p_serialize_1_;
                var4.addProperty("translate", var8.getKey());
                if (var8.getFormatArgs() != null && var8.getFormatArgs().length > 0) {
                    final JsonArray var9 = new JsonArray();
                    for (final Object var13 : var8.getFormatArgs()) {
                        if (var13 instanceof IChatComponent) {
                            var9.add(this.serialize((IChatComponent)var13, var13.getClass(), p_serialize_3_));
                        }
                        else {
                            var9.add((JsonElement)new JsonPrimitive(String.valueOf(var13)));
                        }
                    }
                    var4.add("with", (JsonElement)var9);
                }
            }
            return (JsonElement)var4;
        }
        
        public static String func_150696_a(final IChatComponent p_150696_0_) {
            return Serializer.field_150700_a.toJson((Object)p_150696_0_);
        }
        
        public static IChatComponent func_150699_a(final String p_150699_0_) {
            return (IChatComponent)Serializer.field_150700_a.fromJson(p_150699_0_, (Class)IChatComponent.class);
        }
        
        public JsonElement serialize(final Object p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
            return this.serialize((IChatComponent)p_serialize_1_, p_serialize_2_, p_serialize_3_);
        }
        
        static {
            final GsonBuilder var0 = new GsonBuilder();
            var0.registerTypeHierarchyAdapter((Class)IChatComponent.class, (Object)new Serializer());
            var0.registerTypeHierarchyAdapter((Class)ChatStyle.class, (Object)new ChatStyle.Serializer());
            var0.registerTypeAdapterFactory((TypeAdapterFactory)new EnumTypeAdapterFactory());
            field_150700_a = var0.create();
        }
    }
}
