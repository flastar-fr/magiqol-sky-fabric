package fr.flastar.magiqolsky.shop_items;

import fr.flastar.magiqolsky.shop_items.shop_categories.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ShopItemCreator {
    private HashMap<String, Float> shopItems = null;

    public void createShopsItems() {
        shopItems = new HashMap<>();

        createShopItem(Arrays.asList(Hunter.values()));
        createShopItem(Arrays.asList(Farmer.values()));
        createShopItem(Arrays.asList(Miner.values()));
        createShopItem(Arrays.asList(Lumberjack.values()));
        createShopItem(Arrays.asList(Fisherman.values()));
        createShopItem(Arrays.asList(Cooking.values()));
        createShopItem(Arrays.asList(Botanic.values()));
        createShopItem(Arrays.asList(Blocks.values()));
        createShopItem(Arrays.asList(Dyes.values()));
        createShopItem(Arrays.asList(Miscellaneous.values()));
        createShopItem(Arrays.asList(DungeonObjects.values()));
    }

    private <T extends IShopCategory> void createShopItem(List<T> list) {
        for (T item : list) {
            shopItems.put(item.getID(), item.getPrice());
        }
    }

    public HashMap<String, Float> getShopItems() {
        return shopItems;
    }
}
