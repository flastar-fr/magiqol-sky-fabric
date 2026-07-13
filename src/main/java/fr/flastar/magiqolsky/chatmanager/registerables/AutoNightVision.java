package fr.flastar.magiqolsky.chatmanager.registerables;

import fr.flastar.magiqolsky.chatmanager.config.ChatManagerConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffects;

import static fr.flastar.magiqolsky.utils.CommandUtils.isCommandAvailable;

public class AutoNightVision implements Registerable {
    private static boolean pendingNightVision = false;

    public static void triggerRespawn() {
        pendingNightVision = true;
    }

    @Override
    public void register() {
        ClientEntityEvents.ENTITY_LOAD.register((entity, _) -> {
            if (entity instanceof LocalPlayer player && player.isLocalPlayer()) {
                triggerRespawn();
            }
        });

        ClientPlayConnectionEvents.JOIN.register((_, _, _) -> triggerRespawn());

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!pendingNightVision || client.player == null) return;

            if (!isCommandAvailable(ChatManagerConfig.NV_COMMAND)) {
                return;
            }

            if (!ChatManagerConfig.getConfig().isAutoNightVisionEnabled() ||
                    client.player.hasEffect(MobEffects.NIGHT_VISION)) {
                pendingNightVision = false;
                return;
            }

            client.player.connection.sendUnattendedCommand(ChatManagerConfig.NV_COMMAND, client.screen);
            pendingNightVision = false;
        });
    }
}