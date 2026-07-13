package fr.flastar.magiqolsky.chatmanager.registerables;

import fr.flastar.magiqolsky.chatmanager.config.ChatManagerConfig;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.client.Minecraft;

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

            Minecraft client = Minecraft.getInstance();
            if (client.player == null) {
                return true;
            }
            hasBeenChanged = true;
            client.player.connection.sendChat(newMessage);

            return false;
        });
    }
}