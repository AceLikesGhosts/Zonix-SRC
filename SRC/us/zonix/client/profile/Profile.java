package us.zonix.client.profile;

import net.minecraft.client.*;
import us.zonix.client.*;
import us.zonix.client.setting.*;
import us.zonix.client.setting.impl.*;
import java.util.logging.*;
import java.util.*;
import us.zonix.client.module.*;
import java.io.*;
import com.google.gson.*;

public final class Profile
{
    private final String name;
    private boolean enabled;
    
    public void load() {
        final File file = new File(Minecraft.getMinecraft().mcDataDir, "Zonix/profiles/" + this.name + ".json");
        if (!file.exists()) {
            throw new RuntimeException("Can't load empty file.");
        }
        try (final BufferedReader reader = new BufferedReader(new FileReader(file))) {
            final JsonArray array = new JsonParser().parse((Reader)reader).getAsJsonArray();
            for (final JsonElement element : array) {
                final JsonObject object = element.getAsJsonObject();
                final String name = object.get("name").getAsString();
                final IModule module = Client.getInstance().getModuleManager().getModule(name);
                if (module == null) {
                    continue;
                }
                module.setEnabled(object.get("enabled").getAsBoolean());
                module.setX(object.get("x").getAsFloat());
                module.setY(object.get("y").getAsFloat());
                final JsonArray settings = object.getAsJsonArray("settings");
                for (final JsonElement jsonElement : settings) {
                    final JsonObject settingObject = jsonElement.getAsJsonObject();
                    final String settingName = settingObject.get("name").getAsString();
                    final ISetting setting = module.getSettingMap().get(settingName.toLowerCase());
                    if (setting == null) {
                        continue;
                    }
                    final JsonElement value = settingObject.get("value");
                    if (value == null) {
                        continue;
                    }
                    if (value.isJsonNull()) {
                        continue;
                    }
                    if (setting instanceof FloatSetting) {
                        ((FloatSetting)setting).setValue(Float.valueOf(value.getAsFloat()));
                    }
                    else if (setting instanceof ColorSetting) {
                        ((ColorSetting)setting).setValue(Integer.valueOf(value.getAsInt()));
                        final JsonElement chromaElement = settingObject.get("chroma");
                        if (chromaElement == null || chromaElement.isJsonNull()) {
                            continue;
                        }
                        ((ColorSetting)setting).setChroma(chromaElement.getAsBoolean());
                    }
                    else if (setting instanceof BooleanSetting) {
                        ((BooleanSetting)setting).setValue(Boolean.valueOf(value.getAsBoolean()));
                    }
                    else if (setting instanceof StringSetting) {
                        ((StringSetting)setting).setValue(value.getAsString());
                    }
                    else {
                        if (!(setting instanceof TextSetting)) {
                            continue;
                        }
                        ((TextSetting)setting).setValue(value.getAsString());
                    }
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Error loading profile (" + this.name + ")", e);
        }
        catch (JsonParseException e2) {
            Logger.getGlobal().warning("Error loading profile (" + this.name + ") : " + e2.getMessage());
        }
    }
    
    public void save() {
        final JsonArray mods = new JsonArray();
        for (final IModule module : Client.getInstance().getModuleManager().getModules()) {
            final JsonObject object = new JsonObject();
            object.addProperty("name", module.getName());
            object.addProperty("x", (Number)module.getX());
            object.addProperty("y", (Number)module.getY());
            object.addProperty("enabled", Boolean.valueOf(module.isEnabled()));
            final JsonArray settings = new JsonArray();
            for (final ISetting setting : module.getSettingMap().values()) {
                final JsonObject settingObject = new JsonObject();
                settingObject.addProperty("name", setting.getName().toLowerCase());
                if (setting instanceof FloatSetting) {
                    settingObject.addProperty("value", (Number)setting.getValue());
                }
                else if (setting instanceof ColorSetting) {
                    final boolean chroma = ((ColorSetting)setting).isChroma();
                    settingObject.addProperty("chroma", Boolean.valueOf(chroma));
                    ((ColorSetting)setting).setChroma(false);
                    settingObject.addProperty("value", (Number)setting.getValue());
                    ((ColorSetting)setting).setChroma(chroma);
                }
                else if (setting instanceof BooleanSetting) {
                    settingObject.addProperty("value", Boolean.valueOf(setting.getValue()));
                }
                else if (setting instanceof StringSetting || setting instanceof TextSetting) {
                    settingObject.addProperty("value", (String)setting.getValue());
                }
                settings.add((JsonElement)settingObject);
            }
            object.add("settings", (JsonElement)settings);
            mods.add((JsonElement)object);
        }
        final File file = new File(Minecraft.getMinecraft().mcDataDir, "Zonix/profiles/" + this.name + ".json");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                throw new RuntimeException("Error saving profile (" + this.name + ")", e);
            }
        }
        try (final PrintWriter writer = new PrintWriter(file)) {
            final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer.println(gson.toJson((JsonElement)mods));
        }
        catch (FileNotFoundException e2) {
            throw new RuntimeException("Error saving profile (" + this.name + ")", e2);
        }
    }
    
    public Profile(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Profile)) {
            return false;
        }
        final Profile other = (Profile)o;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null) {
            if (other$name == null) {
                return this.isEnabled() == other.isEnabled();
            }
        }
        else if (this$name.equals(other$name)) {
            return this.isEnabled() == other.isEnabled();
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $name = this.getName();
        result = result * 59 + (($name == null) ? 43 : $name.hashCode());
        result = result * 59 + (this.isEnabled() ? 79 : 97);
        return result;
    }
    
    @Override
    public String toString() {
        return "Profile(name=" + this.getName() + ", enabled=" + this.isEnabled() + ")";
    }
}
