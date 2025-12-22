package fr.flastar.magiqolsky.chatmanager.registerables;

import fr.flastar.magiqolsky.chatmanager.ChatManagerConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffects;

import static fr.flastar.magiqolsky.utils.CommandUtils.isCommandAvailable;

public class AutoNightVision implements Registerable {
    private static boolean pendingNightVision = false;

    public static void triggerRespawn() {
        pendingNightVision = true;
    }

    @Override
    public void register() {
        ClientEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof ClientPlayerEntity player && player.isMainPlayer()) {
                triggerRespawn();
            }
        });

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> triggerRespawn());

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!pendingNightVision || client.player == null) return;

            if (!isCommandAvailable(ChatManagerConfig.NV_COMMAND)) {
                return;
            }

            if (!ChatManagerConfig.getConfig().isAutoNightVisionEnabled() ||
                    client.player.hasStatusEffect(StatusEffects.NIGHT_VISION)) {
                pendingNightVision = false;
                return;
            }

            if (client.player.networkHandler != null) {
                client.player.networkHandler.sendCommand(ChatManagerConfig.NV_COMMAND);
                pendingNightVision = false;
            }
        });
    }
}
