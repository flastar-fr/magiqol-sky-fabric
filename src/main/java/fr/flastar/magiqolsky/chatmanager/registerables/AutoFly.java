package fr.flastar.magiqolsky.chatmanager.registerables;

import fr.flastar.magiqolsky.chatmanager.config.ChatManagerConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.client.MinecraftClient;

import java.util.Arrays;

import static fr.flastar.magiqolsky.chatmanager.config.ChatManagerConfig.*;
import static fr.flastar.magiqolsky.utils.CommandUtils.isCommandAvailable;

public class AutoFly implements Registerable {
    private boolean pendingFly = false;

    @Override
    public void register() {
        ClientSendMessageEvents.COMMAND.register((command) -> {
            if (Arrays.stream(ISLAND_COMMANDS).anyMatch(command::equalsIgnoreCase)) {
                pendingFly = true;
            }
        });

        ClientChunkEvents.CHUNK_LOAD.register((world, chunk) -> {
            MinecraftClient client = MinecraftClient.getInstance();

            if (pendingFly && client.player != null) {

                if (chunk.getPos().equals(client.player.getChunkPos())) {

                    client.execute(() -> executeFlyCommand(client));
                }
            }
        });
    }

    private void executeFlyCommand(MinecraftClient client) {
        if (pendingFly && client.player != null &&
                ChatManagerConfig.getConfig().isAutoFlyingEnabled() &&
                isCommandAvailable(FLY_COMMAND)) {

            if (!client.player.getAbilities().flying) {
                client.player.networkHandler.sendCommand(FLY_COMMAND);
            }

            pendingFly = false;
        }
    }
}
