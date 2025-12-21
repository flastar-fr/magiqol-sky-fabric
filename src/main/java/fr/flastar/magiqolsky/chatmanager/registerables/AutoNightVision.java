package fr.flastar.magiqolsky.chatmanager.registerables;

import fr.flastar.magiqolsky.chatmanager.ChatManagerConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.entity.effect.StatusEffects;

public class AutoNightVision implements Registerable {
    private final static int TICK_PER_SECOND = 20;

    private static int ticksUntilRetry = -1;

    public static void triggerRespawnDelay() {
        ticksUntilRetry = TICK_PER_SECOND;
    }

    @Override
    public void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null ||
                    ticksUntilRetry < 0 ||
                    !ChatManagerConfig.getConfig().isAutoNightVisionEnabled() ||
                    client.player.hasStatusEffect(StatusEffects.NIGHT_VISION)) return;

            if (ticksUntilRetry > 0) {
                ticksUntilRetry--;
                return;
            }

            if (client.player.networkHandler != null) {
                client.player.networkHandler.sendCommand(ChatManagerConfig.NV_COMMAND);
            }

            ticksUntilRetry = -1;
        });

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            triggerRespawnDelay();
        });
    }
}
