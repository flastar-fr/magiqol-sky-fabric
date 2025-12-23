package fr.flastar.magiqolsky.chatmanager.registerables;

import fr.flastar.magiqolsky.chatmanager.config.ChatManagerConfig;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.client.MinecraftClient;

import java.util.Arrays;

import static fr.flastar.magiqolsky.chatmanager.config.ChatManagerConfig.BIENVENUE_COMMANDS;
import static fr.flastar.magiqolsky.chatmanager.config.ChatManagerConfig.BIENVENUE_MESSAGE;

public class BetterBienvenue implements Registerable {
    @Override
    public void register() {
        ClientSendMessageEvents.COMMAND.register((command) -> {
            if (!ChatManagerConfig.getConfig().isBetterBienvenueEnabled()) return;

            if (Arrays.stream(BIENVENUE_COMMANDS).anyMatch(command::equalsIgnoreCase)) {
                MinecraftClient client = MinecraftClient.getInstance();
                if (client.player != null) {
                    client.player.networkHandler.sendChatMessage(BIENVENUE_MESSAGE);
                }
            }
        });
    }
}
