package games.enchanted.fixhorizontalcameralag;

import games.enchanted.fixhorizontalcameralag.config.ConfigHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FixCameraLagLogging {
    public static final Logger LOG = LoggerFactory.getLogger(FixCameraLagMod.MOD_NAME);

    private static final String messagePrefix = "[" + FixCameraLagMod.MOD_NAME + "]: ";

    public static void message(String message, Object... args) {
        LOG.info(messagePrefix + message, args);
    }

    public static void error(String message, Object... args) {
        LOG.error(messagePrefix + message, args);
    }
}
