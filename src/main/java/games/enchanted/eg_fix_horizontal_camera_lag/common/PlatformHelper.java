package games.enchanted.eg_fix_horizontal_camera_lag.common;

//? if neoforge {
/*import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.fml.loading.LoadingModList;
*///?} else {
import net.fabricmc.loader.api.FabricLoader;
//?}

import java.nio.file.Path;

public class PlatformHelper {
    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    public static boolean isModLoaded(String modId) {
        //? if fabric {
        return FabricLoader.getInstance().isModLoaded(modId);
        //?} else {
        /*return ModList.get().isLoaded(modId);
         *///?}
    }

    /**
     * Checks if a mod with the given id is loaded / going to be loaded. Safe for early loading such as mixin config plugins
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    public static boolean isModLoadedEarly(String modId) {
        //? if fabric {
        return FabricLoader.getInstance().isModLoaded(modId);
        //?} else {
        /*LoadingModList modList = FMLLoader.getCurrent().getLoadingModList();
        return modList.getModFiles().contains(modList.getModFileById(modId));
        *///?}
    }

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    public static boolean isDevelopmentEnvironment() {
        //? if fabric {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
        //?} else {
        /*//? if minecraft: <= 1.21.8 {
        /^return !FMLLoader.isProduction();
        ^///?} else {
        return !FMLLoader.getCurrent().isProduction();
        //?}
         *///?}
    }

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    public static String getEnvironmentName() {
        return isDevelopmentEnvironment() ? "development" : "production";
    }

    /**
     * Returns the path to the config folder
     */
    public static Path getConfigPath() {
        //? if fabric {
        return FabricLoader.getInstance().getConfigDir();
        //?} else {
        /*return FMLPaths.CONFIGDIR.get();
         *///?}
    }
}
