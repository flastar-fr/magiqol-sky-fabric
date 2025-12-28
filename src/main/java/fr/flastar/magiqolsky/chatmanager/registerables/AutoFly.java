package fr.flastar.magiqolsky.chatmanager.registerables;

import fr.flastar.magiqolsky.chatmanager.ChatManagerConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;

import java.util.Arrays;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static fr.flastar.magiqolsky.chatmanager.ChatManagerConfig.*;
import static fr.flastar.magiqolsky.utils.CommandUtils.isCommandAvailable;

public class AutoFly implements Registerable {
    private boolean pendingFly;
    private final ScheduledExecutorService scheduler;

    public AutoFly(ScheduledExecutorService scheduler) {
        this.pendingFly = false;
        this.scheduler = scheduler;
    }

    @Override
    public void register() {
        ClientSendMessageEvents.COMMAND.register((command) -> {
            if (Arrays.stream(ISLAND_COMMANDS).anyMatch(command::equalsIgnoreCase)) {
                pendingFly = true;
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!pendingFly || client.player == null) return;
            pendingFly = false;

            if (!isCommandAvailable(FLY_COMMAND)) {
                return;
            }

            if (!ChatManagerConfig.getConfig().isAutoFlyingEnabled()) {
                return;
            }

            if (client.player.isAlive() && !client.player.getAbilities().flying) {
                scheduler.schedule(() -> client.execute(() -> {
                    if (client.player != null && client.player.networkHandler != null) {
                        client.player.networkHandler.sendCommand(FLY_COMMAND);
                    }
                }), TIMEOUT_DELAY, TimeUnit.MILLISECONDS);

            }
        });
    }
}
