//? if fabric {
package games.enchanted.eg_fix_horizontal_camera_lag.fabric;

import games.enchanted.eg_fix_horizontal_camera_lag.common.ModEntry;
import net.fabricmc.api.ModInitializer;

public class FabricEntry implements ModInitializer {
    @Override
    public void onInitialize() {
        ModEntry.init();
    }
}
//?}