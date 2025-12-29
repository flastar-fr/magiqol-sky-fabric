package fr.flastar.magiqolsky.cooldowndisplay;

import fr.flastar.magiqolsky.config.BoostPotionConfig;
import fr.flastar.magiqolsky.config.CustomFoodConfig;
import fr.flastar.magiqolsky.config.data.BoostPotion;
import fr.flastar.magiqolsky.config.data.CustomFood;
import fr.flastar.magiqolsky.cooldowndisplay.gui.CooldownDisplayHud;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

import java.util.Date;
import java.util.Map;

import static fr.flastar.magiqolsky.utils.IDFromStack.retrieveIDFromStack;

public class CustomFoodDetectionHandler {
    private static final int SECONDS_IN_MINUTE = 60;
    private static Date lastPotionUsed;

    public static void register() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            if (world.isClient) {
                ItemStack currentStack = player.getStackInHand(hand).copy();
                handleClickedItem(currentStack);
            }
            return ActionResult.PASS;
        });
    }

    public static void handleClickedItem(ItemStack lastClickedItem) {
        String itemID = retrieveIDNameFromStack(lastClickedItem);
        boolean isCustomFood = CustomFoodConfig.getInstance().getValues().containsKey(itemID);
        boolean isBoostPotion = BoostPotionConfig.getInstance().getValues().containsKey(itemID);

        if (isCustomFood) {
            handleCustomFoodTriggered(lastClickedItem);
            return;
        }
        if (isBoostPotion) {
            boolean isPotionAskingUse = lastPotionUsed == null || lastPotionUsed.getTime() + 5000 < new Date().getTime();
            if (isPotionAskingUse) {
                lastPotionUsed = new Date();
                return;
            }
            BoostPotion boostPotion = BoostPotionConfig.getInstance().getValues().get(itemID);
            handleXpBoostTriggered(boostPotion.cooldown() * SECONDS_IN_MINUTE, lastClickedItem);
        }
    }

    private static void handleCustomFoodTriggered(ItemStack currentStack) {
        String foodID = retrieveIDNameFromStack(currentStack);
        int foodCooldown = determineCustomFoodCooldownFromID(foodID);
        CooldownDisplayHud.setCustomFoodCooldown(foodCooldown, currentStack);
    }

    public static void handleXpBoostTriggered(int cooldown, ItemStack currentStack) {
        CooldownDisplayHud.addBoostCooldown(cooldown, currentStack);
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
