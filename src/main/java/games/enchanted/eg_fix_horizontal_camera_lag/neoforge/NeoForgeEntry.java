//? if neoforge {
/*package games.enchanted.eg_fix_horizontal_camera_lag.neoforge;

import games.enchanted.eg_fix_horizontal_camera_lag.common.ModConstants;
import games.enchanted.eg_fix_horizontal_camera_lag.common.ModEntry;
import games.enchanted.eg_fix_horizontal_camera_lag.common.config.ConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

/^*
 * This is the entry point for your mod's neoforge side.
 ^/
@Mod(value = ModConstants.MOD_ID, dist = Dist.CLIENT)
public class NeoForgeEntry {
    public NeoForgeEntry() {
        ModEntry.init();

        ModLoadingContext.get().registerExtensionPoint(
            IConfigScreenFactory.class, () -> (client, parent) -> ConfigScreen.createConfigScreen(parent)
        );
    }
}
*///?}