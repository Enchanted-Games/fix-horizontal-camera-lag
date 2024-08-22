package games.enchanted.fixhorizontalcameralag.config;

import com.google.gson.*;
import games.enchanted.fixhorizontalcameralag.FixCameraLagLogging;
import games.enchanted.fixhorizontalcameralag.FixCameraLagMod;
import games.enchanted.fixhorizontalcameralag.platform.Services;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;

public class ConfigHandler {
    protected final Object2ObjectLinkedOpenHashMap<String, ConfigOption<?>> optionMap = new Object2ObjectLinkedOpenHashMap<>();
    protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    protected final File configFile;

    public static final Path CONFIG_PATH = Services.PLATFORM.getConfigPath().resolve(FixCameraLagMod.MOD_ID + "_config.json");
    public static final ConfigHandler INSTANCE = new ConfigHandler(CONFIG_PATH.toFile());

    public final ConfigOption.BooleanOption isModEnabled = addOption(new ConfigOption.BooleanOption("modEnabled", true));

    public ConfigHandler(File file) {
        this.configFile = file;
    }
    static {
        INSTANCE.load();
    }

    public void load() {
        if(!configFile.exists()) {
            save();
        }
        try (FileReader reader = new FileReader(configFile)) {
            fromJson(JsonParser.parseReader(reader));
        } catch (Exception e) {
            FixCameraLagLogging.error("Error loading config file: '{}'. ", configFile.getAbsolutePath(), e);
        }
        save();
    }

    public void save() {
        try (FileWriter writer = new FileWriter(configFile)) {
            GSON.toJson(toJson(), writer);
        } catch (Exception e) {
            FixCameraLagLogging.error("Error while saving the config file '{}'. ", configFile.getAbsolutePath(), e);
        }
    }

    protected void fromJson(JsonElement json) throws JsonParseException {
        if (!json.isJsonObject()) {
            throw new JsonParseException("Json must be a json object");
        }

        JsonObject jsonObject = json.getAsJsonObject();
        ObjectBidirectionalIterator<Object2ObjectMap.Entry<String, ConfigOption<?>>> configOptionsIterator = optionMap.object2ObjectEntrySet().fastIterator();

        while (configOptionsIterator.hasNext()) {
            Object2ObjectMap.Entry<String, ConfigOption<?>> configEntry = configOptionsIterator.next();
            JsonElement configEntryJsonElement = jsonObject.get(configEntry.getKey());
            if (configEntryJsonElement == null) continue;
            try {
                configEntry.getValue().parseFromJson(configEntryJsonElement);
            } catch (JsonParseException e) {
                FixCameraLagLogging.error("Error while reading config option '{}'. ", configEntry.getKey(), e);
            }
        }
    }

    protected JsonElement toJson() {
        JsonObject jsonObject = new JsonObject();
        ObjectBidirectionalIterator<Object2ObjectMap.Entry<String, ConfigOption<?>>> configOptionsIterator = optionMap.object2ObjectEntrySet().fastIterator();
        while (configOptionsIterator.hasNext()) {
            Object2ObjectMap.Entry<String, ConfigOption<?>> configEntry = configOptionsIterator.next();
            jsonObject.add(configEntry.getKey(), configEntry.getValue().toJson());
        }
        return jsonObject;
    }

    protected <T extends ConfigOption<?>> T addOption(T option) {
        optionMap.put(option.getKey(), option);
        return option;
    }
}
