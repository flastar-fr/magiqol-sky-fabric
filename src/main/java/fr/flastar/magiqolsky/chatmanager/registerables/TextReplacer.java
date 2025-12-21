package fr.flastar.magiqolsky.chatmanager.registerables;

import fr.flastar.magiqolsky.chatmanager.ChatManagerConfig;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.client.MinecraftClient;

public class TextReplacer implements Registerable {
    private boolean hasBeenChanged = false;

    @Override
    public void register() {
        ClientSendMessageEvents.ALLOW_CHAT.register((message) -> {
            if (hasBeenChanged) {
                hasBeenChanged = false;
                return true;
            }

            if (!ChatManagerConfig.getConfig().isTextReplacementEnabled()) {
                return true;
            }

            String newMessage = ChatManagerConfig.getConfig().applyReplacements(message);

            if (message.equals(newMessage)) {
                return true;
            }

            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null) {
                return true;
            }
            hasBeenChanged = true;
            client.player.networkHandler.sendChatMessage(newMessage);

            return false;
        });
    }
}
