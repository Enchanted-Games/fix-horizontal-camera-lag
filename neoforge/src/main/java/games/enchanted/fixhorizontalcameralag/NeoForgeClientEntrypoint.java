package games.enchanted.fixhorizontalcameralag;

import games.enchanted.fixhorizontalcameralag.config.ConfigScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = FixCameraLagMod.MOD_ID, dist = Dist.CLIENT)
public class NeoForgeClientEntrypoint {
    public final IEventBus eventBus;

    public NeoForgeClientEntrypoint(IEventBus bus) {
        this.eventBus = bus;
        FixCameraLagMod.startOfModLoading();

        // register config screen
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> (client, parent) -> ConfigScreen.createConfigScreen(parent));

        FixCameraLagMod.endOfModLoading();
    }
}