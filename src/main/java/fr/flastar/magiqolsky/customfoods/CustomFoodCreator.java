package fr.flastar.magiqolsky.customfoods;

import fr.flastar.magiqolsky.customfoods.config.CustomFoodConfig;
import fr.flastar.magiqolsky.customfoods.model.CustomFood;

import java.util.HashMap;

public class CustomFoodCreator {
    private final HashMap<String, CustomFood> shopItems = new HashMap<>();
    private final CustomFoodConfig customFoodConfig;

    public CustomFoodCreator() {
        this.customFoodConfig = CustomFoodConfig.getInstance();
    }

    public void createCustomFoods() {
        shopItems.clear();
        shopItems.putAll(customFoodConfig.getCustomFoods());
    }

    public HashMap<String, CustomFood> getShopItems() {
        return shopItems;
    }
}
