package fr.flastar.magiqolsky.mixin;

import fr.flastar.magiqolsky.screens.data.KillTrackerData;
import fr.flastar.magiqolsky.MagiQoLSky;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class CountMobsKilledMixin {
    @Inject(method = "onRemove", at = @At("HEAD"))
    private void checkKill(Entity.RemovalReason reason, CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;

        if (entity.getId() == KillTrackerData.lastTargetId) {
            long timeSinceAttack = System.currentTimeMillis() - KillTrackerData.lastAttackTime;

            if (reason == Entity.RemovalReason.DISCARDED && timeSinceAttack < 1000) {
                MagiQoLSky.LOGGER.info("Kill confirmed : " + KillTrackerData.lastMobName);
                KillTrackerData.lastTargetId = -1;
            }
        }
    }
}
