package games.enchanted.fixhorizontalcameralag;

import net.fabricmc.api.ClientModInitializer;

public class FabricClientEntrypoint implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FixCameraLagMod.startOfModLoading();
        FixCameraLagMod.endOfModLoading();
    }
}
