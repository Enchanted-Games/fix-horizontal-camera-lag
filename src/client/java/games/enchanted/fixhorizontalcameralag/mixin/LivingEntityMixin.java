package games.enchanted.fixhorizontalcameralag.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin extends Entity {
    @Inject(
        at = @At("HEAD"),
        method = "getViewYRot(F)F",
        cancellable = true
    )
    // use yRot instead of yHeadRot for view rotation
	public void getViewYRot(float d, CallbackInfoReturnable<Float> cir) {
        if((Entity)this instanceof Player) {
            cir.setReturnValue(d == 1.0F ? this.yRotO : Mth.lerp(d, this.yRotO, this.getYRot()));
        }
	}

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
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