package fr.flastar.magiqolsky.cooldowndisplay;

import fr.flastar.magiqolsky.MagiQoLSky;
import fr.flastar.magiqolsky.customfoods.model.CustomFood;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;

import java.util.Map;

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
        String foodID = retrieveIDNameFromStack(stack);
        int foodCooldown = determineCustomFoodCooldownFromID(foodID);
        CooldownDisplayHud.setCooldown(foodCooldown, stack);
    }

    private static int determineCustomFoodCooldownFromID(String itemID) {
        Map<String, CustomFood> customFoods = MagiQoLSky.customFoodCreator.getShopItems();
        if (!customFoods.containsKey(itemID)) return 0;
        CustomFood food = customFoods.get(itemID);

        return food.cooldown();
    }

    private static String retrieveIDNameFromStack(ItemStack stack) {
        String foodID = retrieveIDFromStack(stack);
        String[] splitted = foodID.split(":");

        if (splitted.length > 1) {
            return splitted[1];
        } else {
            return foodID;
        }
    }
}
