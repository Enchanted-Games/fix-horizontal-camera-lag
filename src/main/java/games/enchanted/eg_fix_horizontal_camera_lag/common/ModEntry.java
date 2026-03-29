package games.enchanted.eg_fix_horizontal_camera_lag.common;

import games.enchanted.eg_fix_horizontal_camera_lag.common.config.ConfigOptions;

/**
 * This is the entry point for your mod's common code, called by each modloader specific entrypoint.
 */
public class ModEntry {
    public static void init() {
        Logging.info("Mod is loading on a {} environment!", ModConstants.TARGET_PLATFORM);
        ConfigOptions.readConfig();
    }
}
