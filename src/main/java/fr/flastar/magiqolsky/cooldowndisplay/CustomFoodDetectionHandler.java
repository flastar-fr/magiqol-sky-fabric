package fr.flastar.magiqolsky.cooldowndisplay;

import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;

import static fr.flastar.magiqolsky.utils.IDFromStack.retrieveIDFromStack;

public class CustomFoodDetectionHandler {
    private static final String REGEN_TEXT = "[Régénération]";
    private static final String HEAL_TEXT = "Tu as été régénérer de";

    public static void register() {
        ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
            String content = message.getString();

            if (content.contains(REGEN_TEXT) && content.contains(HEAL_TEXT)) {
                handleEffectTriggered();
            }
        });
    }

    private static void handleEffectTriggered() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        ItemStack stack = client.player.getActiveItem();
        String itemID = retrieveIDFromStack(stack); // TODO : here for later use when json with cooldowns is available
        CooldownDisplayHud.foodCooldownTick = 400;
        CooldownDisplayHud.lastStackUsed = stack;
    }
}
