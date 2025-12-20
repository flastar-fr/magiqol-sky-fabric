package fr.flastar.magiqolsky.chatmanager;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.client.MinecraftClient;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static fr.flastar.magiqolsky.chatmanager.AutoCommandConfig.*;
import static fr.flastar.magiqolsky.chatmanager.AutoCommandConfigurationButton.registerAutoCommandConfigurationButton;

public class AutoCommand {
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private static boolean pendingFly = false;

    public static void registerAutoCommands() {
        AutoCommandConfig.load();

        registerAutoFlyCommand();

        registerAutoCommandConfigurationButton();
        registerBetterBienvenueCommand();
    }

    public static void registerAutoFlyCommand() {
        ClientSendMessageEvents.COMMAND.register((command) -> {
            if (Arrays.stream(ISLAND_COMMANDS).anyMatch(command::equalsIgnoreCase)) {
                pendingFly = true;
            }
        });

        ClientEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!AutoCommandConfig.getConfig().isAutoFlyingEnabled()) {
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

    public static void registerBetterBienvenueCommand() {
        ClientSendMessageEvents.COMMAND.register((command) -> {
            if (!AutoCommandConfig.getConfig().isBetterBienvenueEnabled()) return;

            if (Arrays.stream(BIENVENUE_COMMANDS).anyMatch(command::equalsIgnoreCase)) {
                MinecraftClient client = MinecraftClient.getInstance();
                if (client.player != null) {
                    client.player.networkHandler.sendChatMessage(BIENVENUE_MESSAGE);
                }
            }
        });
    }
}
