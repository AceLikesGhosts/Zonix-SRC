package us.zonix.client.profile;

import java.util.logging.*;
import net.minecraft.client.*;
import java.io.*;
import java.util.*;
import com.google.gson.*;

public final class ProfileManager
{
    public static final Gson PRETTY_GSON;
    private final Map<String, Profile> loadedProfiles;
    private Profile activeConfig;
    private String activeConfigName;
    
    public ProfileManager() {
        this.loadedProfiles = new HashMap<String, Profile>();
    }
    
    public void loadConfig(final String name) {
        final Profile profile = this.loadedProfiles.get(name.toLowerCase());
        if (profile == null) {
            Logger.getGlobal().severe("Invalid config supplied when loading profile (" + name + ")");
            return;
        }
        profile.load();
        this.activeConfigName = profile.getName();
        this.activeConfig = profile;
        this.saveSettings();
        Logger.getGlobal().info("Loaded config " + profile.getName());
    }
    
    public Profile createConfig(final String name) {
        final Profile profile = new Profile(name);
        profile.save();
        this.activeConfigName = profile.getName();
        this.activeConfig = profile;
        this.saveSettings();
        return this.loadedProfiles.put(name.toLowerCase(), profile);
    }
    
    public void saveSettings() {
        final File dir = new File(Minecraft.getMinecraft().mcDataDir, "Zonix");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        final File settings = new File(dir, "settings.json");
        if (!settings.exists()) {
            try {
                settings.createNewFile();
            }
            catch (IOException e) {
                throw new RuntimeException("Error creating settings.json");
            }
        }
        try (final PrintWriter writer = new PrintWriter(settings)) {
            final JsonObject object = new JsonObject();
            if (this.activeConfig != null) {
                object.addProperty("active-config", this.activeConfig.getName());
            }
            else {
                object.addProperty("active-config", "default");
            }
            writer.println(ProfileManager.PRETTY_GSON.toJson((JsonElement)object));
        }
        catch (FileNotFoundException e2) {
            throw new RuntimeException("Error writing settings.json");
        }
    }
    
    public void saveConfig() {
        if (this.activeConfig != null) {
            this.activeConfig.save();
            this.saveSettings();
        }
        else {
            this.createConfig("default");
        }
    }
    
    public void load() {
        final File dir = new File(Minecraft.getMinecraft().mcDataDir, "Zonix/profiles");
        if (!dir.exists()) {
            dir.mkdirs();
            return;
        }
        final File settings = new File(dir.getParentFile(), "settings.json");
        Label_0229: {
            if (!settings.exists()) {
                try {
                    settings.createNewFile();
                    break Label_0229;
                }
                catch (IOException e2) {
                    throw new RuntimeException("Error creating settings.json");
                }
            }
            try (final BufferedReader reader = new BufferedReader(new FileReader(settings))) {
                final JsonObject object = new JsonParser().parse((Reader)reader).getAsJsonObject();
                this.activeConfigName = object.get("active-config").getAsString();
            }
            catch (IOException e2) {
                throw new RuntimeException("Error reading settings.json");
            }
            catch (JsonParseException | IllegalStateException ex2) {
                final RuntimeException ex;
                final RuntimeException e = ex;
                settings.delete();
                throw new RuntimeException("Error parsing settings.json... Resetting.");
            }
        }
        for (final File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.getName().endsWith(".json")) {
                final String name = file.getName().replace(".json", "");
                if (!name.equalsIgnoreCase("settings")) {
                    final Profile profile = new Profile(name);
                    this.loadedProfiles.put(name.toLowerCase(), profile);
                    if (name.equalsIgnoreCase(this.activeConfigName)) {
                        (this.activeConfig = profile).load();
                    }
                }
            }
        }
        this.saveSettings();
    }
    
    public Map<String, Profile> getLoadedProfiles() {
        return this.loadedProfiles;
    }
    
    public Profile getActiveConfig() {
        return this.activeConfig;
    }
    
    public String getActiveConfigName() {
        return this.activeConfigName;
    }
    
    static {
        PRETTY_GSON = new GsonBuilder().setPrettyPrinting().create();
    }
}
