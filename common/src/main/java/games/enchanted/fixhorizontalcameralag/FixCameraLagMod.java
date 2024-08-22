package games.enchanted.fixhorizontalcameralag;

import games.enchanted.fixhorizontalcameralag.platform.Services;

public class FixCameraLagMod {
    public static final String MOD_ID = "eg_fix_horizontal_camera_lag";
    public static final String MOD_NAME = "Fix Horizontal Camera Lag";

    public static void startOfModLoading() {
        FixCameraLagLogging.message("Mod is loading on a {} environment", Services.PLATFORM.getPlatformName());
    }

    public static void endOfModLoading() {
        FixCameraLagLogging.message("Loaded Successfully!");
    }
}