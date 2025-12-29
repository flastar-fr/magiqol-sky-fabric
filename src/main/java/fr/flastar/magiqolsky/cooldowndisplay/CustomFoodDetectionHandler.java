package fr.flastar.magiqolsky.cooldowndisplay;

import fr.flastar.magiqolsky.config.CustomFoodConfig;
import fr.flastar.magiqolsky.config.data.CustomFood;
import fr.flastar.magiqolsky.cooldowndisplay.gui.CooldownDisplayHud;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static fr.flastar.magiqolsky.utils.IDFromStack.retrieveIDFromStack;

public class CustomFoodDetectionHandler {
    private static final String REGEN_TEXT = "[Régénération]";
    private static final String HEAL_TEXT = "Tu as été régénérer de";

    private static final String JOBS_TEXT = "[Jobs]";
    private static final String XP_BOOST_TEXT_PART1 = "Vous avez gagné un bonus d'expérience";
    private static final String XP_BOOST_TEXT_PART2 = "pour le métier";

    private static final String SHOP_TEXT = "[Shop]";
    private static final String SELL_BOOST_TEXT_PART1 = "Vous avez gagné un bonus";
    private static final String SELL_BOOST_TEXT_PART2 = "de vente pour le métier";

    private static final int SECONDS_IN_MINUTE = 60;

    private static ItemStack lastClickedItem;

    public static void register() {
        ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
            String content = message.getString();

            if (content.contains(REGEN_TEXT) && content.contains(HEAL_TEXT)) {
                handleCustomFoodTriggered();
            }

            if (content.contains(JOBS_TEXT) && content.contains(XP_BOOST_TEXT_PART1) && content.contains(XP_BOOST_TEXT_PART2)) {
                handleXpBoostTriggered(content);
            }

            if (content.contains(SHOP_TEXT) && content.contains(SELL_BOOST_TEXT_PART1) && content.contains(SELL_BOOST_TEXT_PART2)) {
                handleXpBoostTriggered(content);
            }
        });

        UseItemCallback.EVENT.register((player, world, hand) -> {
            if (world.isClient) {
                lastClickedItem = player.getStackInHand(hand).copy();
            }
            return ActionResult.PASS;
        });
    }

    private static void handleCustomFoodTriggered() {
        String foodID = retrieveIDNameFromStack(lastClickedItem);
        int foodCooldown = determineCustomFoodCooldownFromID(foodID);
        CooldownDisplayHud.setCustomFoodCooldown(foodCooldown, lastClickedItem);
    }

    public static void handleXpBoostTriggered(String message) {
        int cooldown = extractCooldownFromMessage(message);
        CooldownDisplayHud.addBoostCooldown(cooldown, lastClickedItem);
    }

    private static int extractCooldownFromMessage(String message) {
        Pattern pattern = Pattern.compile("(\\d+)\\s+minute");
        Matcher matcher = pattern.matcher(message);

        if (matcher.find()) {
            int minutes = Integer.parseInt(matcher.group(1));
            return minutes * SECONDS_IN_MINUTE;
        }

        return 0;
    }

    private static int determineCustomFoodCooldownFromID(String itemID) {
        Map<String, CustomFood> customFoods = CustomFoodConfig.getInstance().getValues();
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
