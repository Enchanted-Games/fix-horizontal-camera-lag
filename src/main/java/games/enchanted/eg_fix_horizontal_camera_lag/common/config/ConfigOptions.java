package games.enchanted.eg_fix_horizontal_camera_lag.common.config;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import games.enchanted.eg_fix_horizontal_camera_lag.common.Logging;
import games.enchanted.eg_fix_horizontal_camera_lag.common.ModConstants;
import games.enchanted.eg_fix_horizontal_camera_lag.common.PlatformHelper;
import games.enchanted.eg_fix_horizontal_camera_lag.common.config.option.BoolOption;
import games.enchanted.eg_fix_horizontal_camera_lag.common.config.option.ConfigOption;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConfigOptions {
    public static final ConfigOption<Boolean> MOD_ENABLED_OPTION;

    private static final List<ConfigOption<?>> OPTIONS = new ArrayList<>();

    static {
        MOD_ENABLED_OPTION = registerOption(new BoolOption(
            true,
            true,
            "mod_enabled"
        ));
    }

    private static <T> ConfigOption<T> registerOption(ConfigOption<T> option) {
        OPTIONS.add(option);
        return option;
    }

    private static final String FILE_NAME = ModConstants.MOD_ID + ".json";

    private static File getConfigFile() {
        return PlatformHelper.getConfigPath().resolve(FILE_NAME).toFile();
    }

    public static void saveIfAnyDirtyOptions() {
        if(OPTIONS.stream().noneMatch(ConfigOption::isDirty)) return;
        for (ConfigOption<?> option : OPTIONS) {
            if(option.isDirty()) option.applyPendingValue();
        }
        saveConfig();
    }

    public static void saveConfig() {
        JsonObject root = new JsonObject();

        for (ConfigOption<?> option : OPTIONS) {
            root.add(option.getJsonKey(), option.toJson());
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String encodedJson = gson.toJson(root);

        try (FileWriter writer = new FileWriter(getConfigFile())) {
            writer.write(encodedJson);
        } catch (IOException e) {
            Logging.error("Failed to write config file '{}', {}", FILE_NAME, e);
        }
    }

    public static void readConfig() {
        Gson gson = new Gson();
        JsonObject decodedConfig = new JsonObject();

        try {
            JsonReader jsonReader = gson.newJsonReader(new FileReader(getConfigFile()));
            jsonReader.setStrictness(Strictness.LENIENT);
            decodedConfig = JsonParser.parseReader(jsonReader).getAsJsonObject();
        } catch (JsonParseException e) {
            Logging.error("Failed to parse config file '{}', {}", FILE_NAME, e);
        } catch (FileNotFoundException e) {
            Logging.info("Config file '{}' not found", FILE_NAME);
            saveConfig();
        }

        for (ConfigOption<?> option : OPTIONS) {
            option.fromJson(decodedConfig);
        }
    }

    public static void resetAndSaveAllOptions() {
        for (ConfigOption<?> option : OPTIONS) {
            option.resetToDefault(true);
        }
        saveConfig();
    }

    public static void clearAllPendingValues() {
        for (ConfigOption<?> option : OPTIONS) {
            option.clearPendingValue();
        }
    }
}
