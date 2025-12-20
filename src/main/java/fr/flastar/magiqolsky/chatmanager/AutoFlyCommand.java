package fr.flastar.magiqolsky.chatmanager;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.client.MinecraftClient;

import java.util.Arrays;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static fr.flastar.magiqolsky.chatmanager.ChatManagerConfig.*;

public class AutoFlyCommand implements RegisterableCommand {
    private boolean pendingFly;
    private ScheduledExecutorService scheduler;

    public AutoFlyCommand(ScheduledExecutorService scheduler) {
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

        ClientEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!ChatManagerConfig.getConfig().isAutoFlyingEnabled()) {
                pendingFly = false;
                return;
            }
            MinecraftClient client = MinecraftClient.getInstance();
            if (pendingFly && entity == client.player && client.player != null && !client.player.getAbilities().flying) {
                scheduler.schedule(() -> client.execute(() -> client.player.networkHandler.sendCommand(FLY_COMMAND)), TIMEOUT_DELAY, TimeUnit.MILLISECONDS);
            }
            pendingFly = false;
        });
    }
}
