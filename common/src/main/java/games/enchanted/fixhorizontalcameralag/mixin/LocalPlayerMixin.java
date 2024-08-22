package games.enchanted.fixhorizontalcameralag.mixin;

import com.mojang.authlib.GameProfile;
import games.enchanted.fixhorizontalcameralag.config.ConfigHandler;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer {
    LocalPlayerMixin(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }

    @Redirect(method = "getViewYRot(F)F", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isPassenger()Z"))
    public boolean pretendPlayerIsNotAPassengerWhenGettingViewYRot(LocalPlayer instance) {
        if(ConfigHandler.INSTANCE.isModEnabled.getValue()) {
            return false;
        }
        return this.isPassenger();
    }
}