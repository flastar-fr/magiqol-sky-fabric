package fr.flastar.magiqolsky.mixin;

import fr.flastar.magiqolsky.MagiQoLSky;
import fr.flastar.magiqolsky.screens.data.KillTrackerData;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientAttackMixin {
    @Inject(method = "attackEntity", at = @At("HEAD"))
    private void onAttack(PlayerEntity player, Entity target, CallbackInfo ci) {
        KillTrackerData.lastTargetId = target.getId();
        KillTrackerData.lastAttackTime = System.currentTimeMillis();

        Box scanArea = target.getBoundingBox().expand(2.0, 4.0, 2.0);
        List<DisplayEntity.TextDisplayEntity> texts = target.getWorld().getEntitiesByClass(
                DisplayEntity.TextDisplayEntity.class, scanArea, e -> true
        );

        for (DisplayEntity.TextDisplayEntity t : texts) {
            String content = t.getText().getString();
            MagiQoLSky.LOGGER.info(content);
            if (content.contains("Niv.")) { // TODO : not working properly -> only recognize damage viewer
                KillTrackerData.lastMobName = content;
                break;
            }
        }
    }
}
