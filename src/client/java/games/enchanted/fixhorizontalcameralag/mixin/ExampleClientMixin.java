package games.enchanted.fixhorizontalcameralag.mixin;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class ExampleClientMixin extends Entity {
    @Inject(
        at = @At("HEAD"),
        method = "getViewYRot(F)F",
        cancellable = true
    )
    // use yRot instead of yHeadRot for view rotation
	public void getViewYRot(float d, CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(d == 1.0F ? this.yRotO : Mth.lerp(d, this.yRotO, this.getYRot()));
	}

    public ExampleClientMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    @Unique
    protected void defineSynchedData(SynchedEntityData.Builder builder) {}

    @Override
    @Unique
    protected void readAdditionalSaveData(CompoundTag compoundTag) {}

    @Override
    @Unique
    protected void addAdditionalSaveData(CompoundTag compoundTag) {}
}